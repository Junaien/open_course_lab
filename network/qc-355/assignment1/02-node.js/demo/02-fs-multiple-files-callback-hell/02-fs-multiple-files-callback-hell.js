/* 02-fs-multiple-files-callback-hell.js */
const fs = require("fs");
const input_dir = "./input/";
const files = fs.readdirSync(input_dir);	// Array of filenames (strings) in input_dir
let file_data = [];
fs.readFile(`${input_dir}${files[0]}`, "utf8", function (err,data){
	if(err){
		console.log(err);
	}
    else{
        file_data.push(data);
		fs.readFile(`${input_dir}${files[1]}`, "utf8", function (err,data){
            if(err){
                console.log(err);
            }
            else{
                file_data.push(data);
                fs.readFile(`${input_dir}${files[2]}`, "utf8", function (err,data){ 
                    if(err){
                        console.log(err);
                    }
                    else{
                        file_data.push(data);
                        console.log(file_data)
                    }
                });
            }
        } );
	}
} );