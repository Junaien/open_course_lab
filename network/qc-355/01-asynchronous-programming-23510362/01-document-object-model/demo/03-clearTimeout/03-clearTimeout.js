/* 03-clearTimeout.js */
/* Sets up event handlers */
const btn00 = document.getElementById('btn00');
const clickHandler = function(){
    if(timer_status === "finished"){
		console.log("Too Late...");
    }
    else if(timer_status === "cancelled"){
        console.log("Already Cancelled");
    }
    else{
        clearTimeout(timer_id);
        console.log("Timer Cancelled");
        timer_status = "cancelled";
    }
}
btn00.addEventListener("click", clickHandler);

/* Starts a 5 second timer */
const timer_ran_out = function(){
    console.log("Timer Ran Out");
    timer_status = "finished";
}

let timer_status = "not-started";
const timer_id = setTimeout(timer_ran_out, 5000);
console.log("Timer Started");

timer_status = "started";
