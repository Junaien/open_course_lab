const url = require("url");
let jobs_url = 'babab'
let server = http.createServer(function(req, res){
  if(req.url === "/" ){
  	fs.createReadStream("form.html").pipe(res);
  }else if(req.url.startsWith("/search")){
  	console.log(url.parse(jobs_url, true)).query.title; # parse query
  	let jobs_url = "dsadsadsadasdsadsa"
    res.end("Differencet Content")
  }
})