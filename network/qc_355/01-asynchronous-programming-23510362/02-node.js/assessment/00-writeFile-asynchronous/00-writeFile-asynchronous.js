/*
=======================
00-writeFile-asynchronous.js
=======================
Student ID:
Comment (Required):

=======================
*/
const fs = require("fs");
const output_dir = "./output/";
let finished = [];

const writeFiles = function () {
  for (let i = 0; i < 10; i++) {
    const name = `0${i}-file-output.txt`;
    fs.writeFile(output_dir + name, "Hello World", function (err) {
      if (err) {
        console.log(err);
      } else {
        console.log("file " + name + " Finished!");
        finished.push(name);

        if (finished.length == 10) {
          console.log("All files finished");
        }
      }
    });
  }
}

writeFiles();