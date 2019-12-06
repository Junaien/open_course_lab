// api example
// const http = require("http");
// const https = require("https");

// const url = "https://jobs.github.com/positions.json?description=python&full_time=true&location=ny";


// let server = http.createServer(function(req, res) {
// 	console.log("New request receive");
// 	https.get(url, function(github_res) {
// 	  let job_data = "";

// 	  github_res.on("data", function(chunk) { job_data += chunk; });
// 	  github_res.on("end", function(){
//       let jobs = JSON.parse(job_data);
//       res.end(`<h1>${jobs[0].title}</h1>`);
// 	  });
// 	})
// })

// server.listen(80, '167.172.252.159');
// console.log('Now listening on Port');

// example 2
const http = require("http");
const https = require("https");

const url = "https://data.usajobs.gov/api/search?Keyword=Software";
const options = {
	headers: credentials,
  hostname: "https://data.usajobs.gov",
  path: "/api/search?Keyword=Software"
}

const credentials = require("./credentials.json");

let server = http.createServer(function(req, res){
	https.get(options, function(usa_response){
		let result = JSON.parse(job_data);
		let all_jobs = result.SearchResult.SearchResultItems;
		all_jobs.forEach(function(job){
			let title = job.MatchedObjectDescriptor.PositionTitle;
			let job_url = job.MatchedObjectDescriptor.positionURI;
		})

		res.end(`<h1>${title}: ${job_url}</h1>`)
	  usa_response.setEncoding("utf8");

	  let job_data = "";
	  usa_response.on("data", function(chunk){
	  	job_data += chunk;
	  });

	  usa_response.on("end", function(){
	  	let job = JSON.parse
	  	res.end(end_data);
	  })
	});
});

// rest is not a protocol, rest is a style

