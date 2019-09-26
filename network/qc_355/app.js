const http = require("http");

// example 1
// const server = http.createServer(
//   function(req, res) {
//     res.end("<h1>Hello World</h1>");
//   }
// );

// server.listen(3000, "localhost");
// console.log("The server is listening on port 3000");


// example 2: grab resources from internet and console it out
// const url = "";
// https.get(url, function(response) {
//   let body = "";
//   response.on("data", function(data) {body += data;});
//   response.on("end", function(end) {
//     let alldata = JSON.parse(body);
//     console.log(alldata);
//   });
// });

// example 3
const server = http.createServer(
  function(req, res) {
    const url = "https://jobs.github.com/positions.json?search=node";
    https.get(url, function(response) {
      let body = "";
      response.on("data", function(data) {body += data;});
      response.on("end", function(end) {
        let alldata = JSON.parse(body);
        res.end(`<p> ${alldata} </p>`);
      });
    });
  }
);

server.listen(3000, "localhost");
