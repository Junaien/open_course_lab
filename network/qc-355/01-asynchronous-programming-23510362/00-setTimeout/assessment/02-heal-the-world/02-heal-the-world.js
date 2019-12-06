/*
=======================
02-heal-the-world.js
=======================
Student ID: 23510362
Comment (Required):

- 1) we need to wrap function print("Heal the world") inside an anonymous function
- by doing 1) we avoid immediate invokation of print("Heal the world")
=======================
*/

const print = function(message) {
	console.log(message);
}

setTimeout(function () {print("Heal the world");}, 3000);
console.log();