/*
=======================
01-goodbye-world.js
=======================
Student ID:
Comment (Required):

=======================
*/

let salutation = "Hello";
let name = "En Lin";

const print_farewell = function () {
	console.log(salutation + " " + name);
}

setTimeout(print_farewell, 3000);
salutation = "Goodbye";

console.log();