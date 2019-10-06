/*
=======================
02-keyboard-event.js
=======================
Student ID: 23510362
Comment (Required):

- we store cheatCode in a format of keyCode into an array
- then we register a callback function for a keydown Event on document object
- dataStructure is an array stores the last 10 key pressed (if less than 10 key pressed, we don't discard them)
  every time a keydown event happens, we discard the last 11-th key pressed if any, then compares if the last 10 keys
  matches our secret
=======================
*/


let dataStructure = [];
const cheatCode = [38, 38, 40, 40, 37, 39, 37, 39, 66, 65];
const keydownHandler = function (e) {
	let keyC = e.keyCode;
	dataStructure.push(keyC);
	if (dataStructure.length > cheatCode.length) {
		dataStructure.shift();
	}
	if (JSON.stringify(dataStructure) === JSON.stringify(cheatCode)) {
		console.log("CHEATS ACTIVATED!");
	}
}

document.addEventListener("keydown", keydownHandler);