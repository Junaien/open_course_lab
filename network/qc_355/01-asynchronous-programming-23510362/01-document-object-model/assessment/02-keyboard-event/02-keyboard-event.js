/*
=======================
02-keyboard-event.js
=======================
Student ID:
Comment (Required):

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