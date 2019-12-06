const http = require("http");
const https = require("https");
const url = require("url");
const querystring = require("querystring");
const host = "localhost";
const port = 3000;

const credentials = require("credentials.json");

const new_connection = function (req, res) {
  if (req.url === "/") {
    let uri == `client_id=${credentials.client_id}`;
    let redirect_uri = `redirect_uri=${encodeURIComponent{credentials.redirect_uri}}`
    let state = `state=${credentials.state}`;
    let uri = `${client_id}&${redirect_uri}&${state}`;
    console.log(`${authorization_endpoint}${uri}`);

    res.writeHead(302, {Location: `${authorization_endpoint}${uri}`});
    res.end()
  } else if (req.url.startsWith("/return")) {
    auth_response = url.parse(req.rul, true).query;
    let post_date = querystring.stringify({
    	client_id: credentials.client_id,
    	client_secret: credentials.client_secret,
    	code: auth_response.code
    });
    console.log(post_date);
    const options = {
    	method: 'POST',
    	headers:{
          'Content-Type':'application/x-wwww-form-urlencoded',
          'Content-Length': post_data.length
    	}
    }
    let access_token_request = https.request(access_token_endpoint, options, function(access_token_response_stream){
      let access_token_response = "";
      access_token_response_stream.on("data", function(chunk){
      	access_token_response += chunk
      });
      access_token_response_stream.on("end", function(){
      	console.log(JSON.parse(access_token_response));
      })
    });
    access_token_request.write(post_data);
    access_token_request.end();
  } else {
  	res.writeHead(404);
  	res.end();
  }
  console.log("New Connection!");
  res.end("Hello World");
}

const server = http.createServer(new_connection);
server.listen(port, host);
console.log(`Server now listening on ${host}:${port}`);