/* 03-fs-multiple-files-continuation-passing-style.js */
const fs = require("fs");
const input_dir = "./input/";
const files = fs.readdirSync(input_dir);
const read_file_one = function (){
    let file_data = [];
	fs.readFile(`${input_dir}${files[0]}`, "utf8", function (err,data){
		if(err){
			console.log(err);
		}
		else{
            file_data.push(data);
			read_file_two(file_data);
		}
	} );
}
const read_file_two = function (file_data){
	fs.readFile(`${input_dir}${files[1]}`, "utf8", function (err,data){
		if(err){
			console.log(err);
		}
		else{
            file_data.push(data);
			read_file_three(file_data);
		}
	} );
}
const read_file_three = function(file_data){
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
read_file_one();