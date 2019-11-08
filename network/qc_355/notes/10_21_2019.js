// 1 2 3 4 5 ... 97 98 99
// wait for 3 files to finish, then conver them into a item

const fs = require("fs");
const input_dir = "./input";
const output_dir = "./output/";
const input_files = fs.readdirSync(input_dir);



for() {
	fs.readFile(file, encoding, callback)
}

// runing multiple tasks ayncronously can be done using a for loop
// runing multiple tasks syncronously can be done using callback chain



let file_data = [];
let files_read = 0;
const batch_files = 5;

const readFiles = function() {
	for (let index = 0; index < input_files.length; index++) {
		fs.readFile(`${input_dir}${input_files[index]}`, "utf8", readFinished);
	}
}

const readFinished = function(err, data) {
	if (err) {
		console.log(err);
	} else {
		file_data.push(data);
		console.log(file_data);
		files_read ++;
		if(files_read % batch_files === 0 | files_read === input_files.length) {
			writeFiles(Math.ceil(files_read / batch_files) - 1);
		}
	}
}

const writeFile = function(file_number) {
	let filename = `${String(file_number.padStart(2, '0'))}-0i-file-output.txt`;
	let data = file_data.slice(file_number * batch_files, file_number * batch_files + batch_files);
	fs.writeFile(filename, data, "utf8", function(err) {
		console.log("Finished writing file i");
	})
}