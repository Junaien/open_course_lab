/*
=======================
02-batch-b.js
=======================
Student ID: 23510362
Comment (Required):

- we maintain an array <output_data> to collect all data asynchronously read from files
  once, in the aftermath of reading a file, we have read b files, we flush the output_data array 
  into a file
- we maintain a variable <cur_ouput_i> so that we know which file we should flush our data into
=======================
*/

const fs = require("fs");
const input_dir = "./input/";
const output_dir = "./output/";
const input_files = fs.readdirSync(input_dir);
const b = 5;
const m = Math.ceil(input_files.length / b);
let cur_ouput_i = 0; 
let output_data = [];

for (let index = 0; index < input_files.length; index++) {
  fs.readFile(`${input_dir}${input_files[index]}`, "utf8", function (err,data){
    if(err){
      console.log(err);
    } else {
      output_data.push(data)
      if (output_data.length >= b || cur_ouput_i == m - 1) {
        // preappend "0" to file name only when n < 10
        const output_path = output_dir + (cur_ouput_i < 10? "0" : "") + cur_ouput_i + "-file-output.txt";
        fs.writeFile(output_path, output_data.join(","), function (err) { 
          if (err) {
            console.log(err);
          }
        });

        output_data = [];
        cur_ouput_i ++;
      }
    }
  } );
};