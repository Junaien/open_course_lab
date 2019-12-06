/*
=======================
00-writeFile-asynchronous.js
=======================
Student ID: 23510362
Comment (Required):

- the finishing time for each write doesn't have to be in chronological order, 
  so we simply use a for loop to call asyn writeFile function
- to determine the time when all writing finishes, we use an array to record how many writes succeeded already
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