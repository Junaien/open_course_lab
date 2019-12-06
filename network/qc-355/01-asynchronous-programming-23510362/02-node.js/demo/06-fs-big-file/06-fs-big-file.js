/* 06-fs-big-file */
const fs = require("fs");
const input_dir = "./input/";
const files = fs.readdirSync(input_dir);
let file_data = Array(files.length);	//Creates ${files.length} Empty Slots
let files_read = 0;
const readFiles = function(){
    for(let index = 0; index < files.length; index++){
        fs.readFile(`${input_dir}${files[index]}`, "utf8", function (err, data){
            if(err){
                console.log(err);
            }
            else{
				file_data[index] = data;
				console.log(file_data.map(function(x) {return x.length}));
				files_read++;
                if(files_read === files.length){
                    console.log("Finished!");
                }
				console.log("------------------------------");
            }
        } );
    }
}
readFiles();