/* 00-fs-wrong.js - WRONG */
const fs = require("fs");
const input_dir = "./input/";
let file_data;

let next_task = function(input){
	console.log(input);
}

fs.readFile(`${input_dir}00-file-input.txt`, "utf8", function (err,data){
	if(err){
		console.log(err);
	}
    else{
		file_data = data;
	}
} );
next_task(file_data);