const http = require("http");
const https = require("https");

const url = "https://jobs.github.com/positions.json?description=python&full_time=true&location=ny";


let server = http.createServer(function(req, res) {
	console.log("New request receive");
	https.get(url, function(github_res) {
	  let job_data = "";

	  github_res.on("data", function(chunk) { job_data += chunk; });
	  github_res.on("end", function(){
      let jobs = JSON.parse(job_data);
      res.end(`<h1>${jobs[0].title}</h1>`);
	  });
	})
})

server.listen(80, '167.172.252.159');
console.log('Now listening on Port');

