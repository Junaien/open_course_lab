const task1 = function() {
	alert("Hello World");
}


// dom example 2 - fine tune selection

// returns the first matching dom element
// document.getElementById();

// document.getElementsByClassName();

let button = document.getElementById("button");
console.log(button)


// example 3 - block even loop,
let i = 0;
while(i < 1000000000) {
	i ++;
}

// click on button while waiting
// print finished first, then print the click callbacks
console.log("finished");


// example 4
// why this even runs
const taskInfinite = function() {
    setTimeout(taskInfinite, 1000); // async
    console.log("hey");
}

taskInfinite();
