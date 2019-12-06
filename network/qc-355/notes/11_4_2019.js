// #1
// const fs = require('fs');


// let myReadStream = fs.createReadStream('file.txt', 'utf8');
// let myWriteStream = fs.createWriteStream('out.txt'); // default is utf8

// myReadStream.on('data', function(chunk) {
//   console.log("Received New Chunk of Data: ");
//   myWriteStream.write(chunk);

// });

// // pipe
// myReadStream.pipe(myWriteStream);


// #2
const fs = require('fs');
const http = require('http');
const port = 3000;
const ip_address = "localhost";
let myReadStream = createReadStream('file.txt', "utf8");

// [request] type read stream
// [res] write stream 
let server = http.createServer(function(req, res) {
  console.log(`A new request was made from ${req.remoteAddress}`);
  res.writeHead(200, {'Content-Type': 'text/html'});
  myReadStream.pipe(res)
});

server.listen(port, ip_address);
console.log(`Now listening on port ${port} for `);






