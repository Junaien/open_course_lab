// const fs = require('fs');

// myWriteStream = fs.createReadStream('file.txt')
// let all_data = "";

// const new_chunk = function(chunk) {
// 	all_data += chunk;
// }

// const consume = function(chunk) {
// 	console.log(all_data);
// }

// myReadStream.on('data', new_chunk);
// myReadStream.on('end', consume);


const http = require('http');
const fs = require('fs');

// const new_connection = function(req, res) {
// 	console.log(`new connection`);
// 	let myReadStream = f.createReadStream('file.txt');
// 	// res.myWriteStream(200, {'Content-Type': 'application/json'});
// 	// res.myWriteStream(200, {'Content-Type': 'text/html'});
// 	// myReadStream.pipe(res);
// }


let myReadStream = f.createReadStream('file.txt');
let all_data = "";

myReadStream.on('data', functiton() {
	all_data += chunk
});