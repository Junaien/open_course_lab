const task1 = function() {
	alert("Hello World");
}

document.addEventListener("click", task1);

// dom example 2 - fine tune selection

// returns the first matching dom element
document.getElementById();

document.getElementsByClassName();

let input_list = document.getElementsByTagName("input");
input_list.addEventListener("click", task1);