/*
=======================
00-mouse-event.js
=======================
Student ID:
Comment (Required):

=======================
*/
const click_handler = function (e) {
	console.log(`x: ${e.x}, y:${e.y}`);
}

document.addEventListener('click', click_handler);