/*
=======================
00-mouse-event.js
=======================
Student ID: 23510362
Comment (Required):

- register a callback function for a click event of document object, whenever a click event happens,
  click_handler function will be pushed into callback queue and then called
=======================
*/
const click_handler = function (e) {
	console.log(`x: ${e.x}, y:${e.y}`);
}

document.addEventListener('click', click_handler);