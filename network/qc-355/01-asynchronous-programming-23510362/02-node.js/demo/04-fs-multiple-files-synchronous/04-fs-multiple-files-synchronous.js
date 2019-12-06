/* 04-fs-multiple-files-synchronous.js */
const fs = require("fs");
const input_dir = "./input/";
const files = fs.readdirSync(input_dir);

const readFiles = function (files , index = 0 , file_data = []){
	fs.readFile(`${input_dir}${files[index]}`, "utf8", function (err,data){
		if(err){
			console.log(err);
		}
		else{
			file_data.push(data);
			if(index === files.length - 1){
				//return file_data --don't do this! breaks continuation passing
				next_step(file_data);
			}
			else{
				readFiles(files, ++index, file_data);
			}
		}
	} );
}

const next_step = function(file_data){
	console.log(file_data);
}

readFiles(files);