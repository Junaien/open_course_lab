/*
=======================
00-hello-world.js
=======================
Student ID: 23510362
Comment (Required):

- calling setTimeout(print, 5000) will add print function call to callback queue when time is up
- since javascript execution stack will be empty, it will pick up print function through event loop and call it
=======================
*/

const print = function () {
	console.log("Hello World");
}

setTimeout(print, 5000);
console.log();