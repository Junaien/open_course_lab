/*
=======================
01-writeFile-synchronous.js
=======================
Student ID:
Comment (Required):

=======================
*/
const fs = require("fs");
const output_dir = "./output/";

const writeFiles = function (index) {
	const name = `0${index}-file-output.txt`;
	fs.writeFile(output_dir + name, "Hello World", function () {
      if (index == 10) {
        console.log("All files finished!");
      } else {
      	console.log(name + " finished!");
      	writeFiles(index + 1);
      }
	});
}

writeFiles(0);