/*
=======================
01-writeFile-synchronous.js
=======================
Student ID: 23510362
Comment (Required):

- we will call writeFiles(index + 1) function inside the callback function of fs.writeFile(index)
- in this way, the writing will happens in order
=======================
*/
const fs = require("fs");
const output_dir = "./output/";

const writeFiles = function (index) {
  // append 0 on file name only when index < 10
  const name = (index < 10? "0" : "") + `${index}-file-output.txt`;
  fs.writeFile(output_dir + name, "Hello World", function (err) {
    if (err) {
      console.log(err);
    } else {
      if (index == 10) {
          console.log(name + " finished!");
          console.log("All files finished!");
       } else {
          console.log(name + " finished!");
          writeFiles(index + 1);
       }
    }
  });
}

writeFiles(0);