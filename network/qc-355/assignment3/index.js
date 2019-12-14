const http = require('http');
const fs = require('fs');
const url = require('url');
const querystring = require('querystring');
const https = require('https');
const credentials = require('./auth/credentials.json');
const authentication_cache = "./auth/authentication-res.json";


const create_access_token_cache = function(spotify_auth) {
  fs.writeFile('./auth/authentication-res.json', JSON.stringify(spotify_auth), (err) => {
    if (err) {console.log("spotify_auth saving error!");}
    else {console.log("spotify_auth cached!");}
  });
};

const create_search_req = function(spotify_auth, user_input, res) {
  const search_endpoint = 'https://api.spotify.com/v1/search?';
  const request_param = {
    "type": "album",
    "q" : user_input.query.q,
    "access_token" : spotify_auth.access_token
  };

  const search_req = https.request(search_endpoint + querystring.stringify(request_param), function (search_res) {
    console.log("request album search result successfully!");
    search_res.setEncoding("utf8");
    let body = "";
    search_res.on("data", function(chunk) {body += chunk;});
    search_res.on("end", function() {
      let albums = JSON.parse(body).albums;
      let downloaded_images = 0;
      // no result found
      if (albums.items.length == 0) {
        generate_webpage(albums, user_input, res);
      }

      // if image exists, simply increment downloaded_images
      for (let i = 0; i < albums.items.length; i++) {
        fs.access("./album-art/" + albums.items[i].id + ".jpg", fs.constants.F_OK, (err) => {
          if (err) {
            // download image
            let image_req = https.get(albums.items[i].images[0].url, function(image_res) {
              console.log(`download albums${albums.items[i].id} image successfully!`);
              let new_img = fs.createWriteStream("./album-art/" + albums.items[i].id + ".jpg", {'encoding': null});
              image_res.pipe(new_img);
              new_img.on("finish", function() {
                downloaded_images++;
                if(downloaded_images === albums.items.length) {
                  generate_webpage(albums, user_input, res);
                }
              });
            });
            image_req.on('error', function(err) {console.log(err);});
          } else {
            // image was cached
            downloaded_images++;
            if(downloaded_images === albums.items.length) {
              generate_webpage(albums, user_input, res);
            }
          }
        });
      }
    });
  });
  search_req.on('error', function (e) {
    console.error(e);
  });
  search_req.end();
};

const generate_webpage = function(albums, user_input, res) {
  res.writeHead(200, {'Content-Type':'text/html'});
  let form = `<form action="search" method="get">
                <fieldset>
                  <legend>Music Album Search</legend>
                  <input id="q" name="q" type="text" value="${user_input.query.q}"/>
                  <input type="submit" value="Search" />
                </fieldset>
              </form>`;
  let content = `<h1>Search result:${user_input.query.q}</h1>`;
  for (let i = 0; i < albums.items.length; i++) {
    content += `<img src="./album-art/${albums.items[i].id}.jpg" alt="broken" height="420" width="420">`;
  }
  let frame =  `<!DOCTYPE html>
                <html>
                  <head>
                    <title>Music Album Search</title>
                    <style>*{font-size:18pt;}</style>
                  </head>
                <body>
                  ${form}
                  ${content}
                </body>
                </html>`;
  res.end(frame);
};

const received_authentication = function(authentication_res, user_input, auth_sent_time, res) {
  authentication_res.setEncoding("utf8");
  let body = "";
  authentication_res.on("data", function(chunk) {body += chunk;});
  authentication_res.on("end", function() {
    let spotify_auth = JSON.parse(body);
    spotify_auth.expiration = auth_sent_time.setSeconds(auth_sent_time.getSeconds() + 3600);
    create_access_token_cache(spotify_auth);
    create_search_req(spotify_auth, user_input, res);
  });
};

const new_connection = function (req, res) {
  if (req.url === '/') {
    res.writeHead(200, {'Content-Type':'text/html'});
    let readStream = fs.createReadStream('./html/search-form.html',"utf8");
    readStream.pipe(res);
  } else if (req.url.startsWith('/favicon.ico')) {
    res.writeHead(404);
    res.end();
  } else if(req.url.startsWith('/album-art')) {
    img_stream = fs.createReadStream('.' + req.url);
    img_stream.on('error', function(err) {
      console.log(err);
      res.writeHead(404);
      res.end();
    });
    res.writeHead(200, {'Content-Type':'image/png'});
    img_stream.pipe(res);
  } else if(req.url.startsWith('/search')) {
    let user_input = url.parse(req.url, true);
    let cache_valid = false;
    let cache_auth = {};
    if(fs.existsSync(authentication_cache)) {
      cached_auth = require(authentication_cache);
      if (new Date(cached_auth.expiration) > Date.now()) {
        cache_valid = true;
      } else {
        console.log('Token Expired');
      }
    }
    if (cache_valid) {
      create_search_req(cached_auth, user_input, res);
    } else {
      const token_endpoint = 'https://accounts.spotify.com/api/token';
      const post_data = querystring.stringify(credentials);
      const options = {
        'method': "post",
        'headers': {
          'Content-Type': 'application/x-www-form-urlencoded',
          'Content-Length': post_data.length
        }
      }

      let auth_sent_time = new Date();
      let authentication_req = https.request(token_endpoint, options, function (authentication_res) {
        console.log("request access token successfully!");
        received_authentication(authentication_res, user_input, auth_sent_time, res);
      });
      authentication_req.on('error', function (e) {
        console.error(e);
      });
      authentication_req.end(post_data);
    }
  }
}

let server = http.createServer(new_connection);
server.listen(3000, 'localhost');