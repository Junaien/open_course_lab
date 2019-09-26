/*
=======================
03-mister-worldwide.js
=======================
Student ID:
Comment (Required):

=======================
*/

const indefinite_print = function() {
	setTimeout(indefinite_print, 1000);
	console.log("!Dale");
}

indefinite_print();