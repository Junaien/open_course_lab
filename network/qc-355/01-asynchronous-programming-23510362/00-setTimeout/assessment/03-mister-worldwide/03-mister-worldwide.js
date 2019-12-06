/*
=======================
03-mister-worldwide.js
=======================
Student ID: 23510362
Comment (Required):

- what happens when we run for(let i = 0 ; i < 2000000000 ; i++); 
  is that it blocks Dale! being printed for a fixed amount of time
- So indefinite_print function seems like a indefinite loop, but because the event loop
  mechanism, indefinite_print will not call it self immediately(even when timeout is 0), instead the function itself is 
  pushed into callback queue, and only gets invoked after the execution stack is empty
- so when we call < for(let i = 0 ; i < 2000000000 ; i++) >, 
  it blocks indefinite_print function that is registered in the callback queue waiting to be picked up by the event loop
=======================
*/

const indefinite_print = function() {
	setTimeout(indefinite_print, 1000);
	console.log("!Dale");
}

indefinite_print();