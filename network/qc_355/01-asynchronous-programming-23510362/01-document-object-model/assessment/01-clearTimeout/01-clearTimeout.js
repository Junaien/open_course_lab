/*
=======================
01-clearTimeout.js
=======================
Student ID:
Comment (Required):

=======================
*/
let btn00 = document.getElementById('btn00');
let btn01 = document.getElementById('btn01');
let timerIDs = []; 

const btn00_click_handler = function () {
	timerIDs.push(setTimeout(function () {console.log("Delayed Hello");} ,10000));
}

const btn01_click_handler = function () {
	timerIDs.forEach(clearTimeout);
	console.log("All remain timers canceled");
	timerIDs = [];
}

btn00.addEventListener("click", btn00_click_handler);
btn01.addEventListener("click", btn01_click_handler);




