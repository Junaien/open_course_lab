const http = require('http');
const fs = require('fs');
const url = require('url');
const querystring = require('querystring');
const https = require('https');
const dc = require('./auth/drive_credentials.json');
const auth_endpoint = dc.web.auth_uri;
const crypto = require("crypto");
const {form, item_wrap, html_wrap, flash, fault_page} = require("./template.js");

const nasa_apikey = require('./auth/nasa_apikey.json');
const apod_endpoint = 'https://api.nasa.gov/planetary/apod';
const upload_endpoint = "https://www.googleapis.com/upload/drive/v3/files?uploadType=multipart";
const drive_scope = "https://www.googleapis.com/auth/drive.file";
const host = "http://localhost";
const port = 3000;
let state_tasks = [];

const redirect_for_drive_consent = function(res, task) {
  let state = crypto.randomBytes(20).toString("hex");
  state_tasks.push({state, task: task});

  let uri = querystring.stringify({
        client_id: dc.web.client_id,
        redirect_uri: dc.web.redirect_uris[0],
        response_type: "code",
        scope: drive_scope,
        access_type: 'offline',
        state: state
      });
  res
    .writeHead(302, {Location: auth_endpoint + "?" + uri})
    .end();
}

const get_access_token = function(code, task, res) {
  let post_data = querystring.stringify({
        client_id: dc.web.client_id,
        client_secret: dc.web.client_secret,
        code: code,
        scope: drive_scope,
        grant_type: "authorization_code",
        redirect_uri: dc.web.redirect_uris[0]
  });

  let options = {
      method: "POST",
      headers: {
          "Content-Type": "application/x-www-form-urlencoded",
          "Content-Length": post_data.length
      }
  };

  let access_token_request = https.request(dc.web.token_uri, options, function (access_token_stream) {
    let access_token_obj = "";
    access_token_stream.on("data", (chunk) => access_token_obj += chunk);
    access_token_stream.on("end", function () {
        execute_task(JSON.parse(access_token_obj).access_token, task, res);
    });
  });
  access_token_request.on('error', function(error) { console.log("access token: " + error); });
  access_token_request.end(post_data);
}

const save_to_drive = function(access_token, file_path, content_type) {
  let readStream = fs.createReadStream(file_path);
  readStream.on('error', function(err) { console.log("read image: " + err); });
  readStream.on('open', function() {
    let options = {
      method: "POST",
      headers: {
          "Content-Type": "image/jpeg",
          "Authorization": `Bearer ${access_token}` 
      },
    };
  
    let upload_request = https.request(upload_endpoint, options, function (upload_res) {
      let upload_res_data = "";  
      upload_res.on("data", function (chunk) { upload_res_data += chunk; });
      upload_res.on("end", function() { console.log(upload_res_data); });
    });

    upload_request.on('error', function (e) { console.error("upload request: " + e); });
    readStream.pipe(upload_request);
  });
}

const execute_task = function(access_token, task, res) {
  if (task.type === "search") {
    serve_search(access_token, task.data, res);
  } else {
    res.writeHead(200, {'Content-Type':'text/html'});
    res.end(fault_page(task.data, "not known task"));
  }
}

const serve_search = function(access_token, data, res) {
  const apod_req = https.get(`${apod_endpoint}?${querystring.stringify({api_key: nasa_apikey.apikey, date:data})}`, function(apod_res) {
    let apod_res_data = "";
    apod_res.on('data', function(chunk) { apod_res_data += chunk; });
    apod_res.on('end', function() {
      let apod = JSON.parse(apod_res_data);

      // check if date input is valid
      if (apod.code == 400) {
        console.log(apod);
        res.writeHead(200, {'Content-Type':'text/html'});
        res.end(fault_page(data, apod.msg));
      }

      // request apod image
      const img_req = https.get(apod.url, function(img_res) {
        let new_img = fs.createWriteStream(`./img/${apod.date}.jpg`, {'encoding': null});
          img_res.pipe(new_img);
          new_img.on("finish", function() {
            save_to_drive(access_token, `./img/${apod.date}.jpg`, 'image/jpeg');
            res.end(html_wrap("NASA", form("Astronomy Picture of the Day", date, "Search Date") + item_wrap(apod.title, apod.explanation, `./img/${apod.date}.jpg`, "Saving to Google drive")));
          });
      });
      img_req.on('error', function(error) { console.log("apod image request: " + error); } )
    });
  });
  apod_req.on('error', function(error) { 
    console.log("apod request: " + error); 
  });
}

const new_connection = function (req, res) {
  if (req.url === '/') {
    res.writeHead(200, {'Content-Type':'text/html'});
    res.end(html_wrap("NASA Search", form("Astronomy Picture of the Day", null, "Search Date")));

  } else if(req.url.startsWith('/search')) {
    redirect_for_drive_consent(res, {type:"search", data: url.parse(req.url, true).query.q});

  } else if(req.url.startsWith('/img')) {
    img_stream = fs.createReadStream('.' + req.url);
    img_stream.on('error', function(err) {
      res.writeHead(404);
      res.end();
    });
    img_stream.on('open', function() {
      res.writeHead(200, {'Content-Type':'image/png'});
      img_stream.pipe(res);
    });
  } else if(req.url.startsWith('/receive_code')) {
    // get task
    console.log(req.url);
    let auth_response = url.parse(req.url, true).query;
    console.log(auth_response);
    let index = state_tasks.findIndex((state_task) => state_task.state === auth_response.state);
    let prior = state_tasks.splice(index, 1);

    // execute task with access token
    if (index == -1) {
      res
        .writeHead(302, {Location: host + ":" + port})
        .end();

    } else {
      get_access_token(auth_response.code, prior[0].task, res);    
    }
  } else {
    res.writeHead(404);
    res.end();
  }
}


let server = http.createServer(new_connection);
server.listen(3000, 'localhost');