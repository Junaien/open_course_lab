/* 01-fs-correct */
const fs = require("fs");
const input_dir = "./input/";

let next_task = function(input){
	console.log(input);
}

fs.readFile(`${input_dir}00-file-input.txt`, "utf8", function (err,data){
	if(err){
		console.log(err);
	}
    else{
        next_task(data); //Continuation Passing Style
	}
} );
