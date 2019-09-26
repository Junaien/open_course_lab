/* 02-event-object.js */
const keydownHandler = function(event){
    console.log(event);
    console.log(event.key);
}
document.addEventListener("keydown", keydownHandler);