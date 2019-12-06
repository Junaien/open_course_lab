/*
=======================
01-goodbye-world.js
=======================
Student ID: 23510362
Comment (Required):

- Since variable salutation is changed before event loop picks up print_farewell function from callback queue
- when print_farewell is invoked, it prints out the variable after the change
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