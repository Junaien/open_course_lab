/* 01-contextmenu.js */
let red_score = 0;
let blue_score = 0;

const red = document.getElementById('red');
const blue = document.getElementById('blue');

const clickRedHandler = function(){
    console.log(`Red's score is now ${++red_score}`);
}
const contextmenuRedHandler = function(){
    console.log(`Red's score is now ${--red_score}`);
}
const clickBlueHandler = function(){
    console.log(`Blue's score is now ${++blue_score}`);
}
const contextmenuBlueHandler = function(){
    console.log(`Blue's score is now ${--blue_score}`);
}

red.addEventListener("click", clickRedHandler);
red.addEventListener("contextmenu", contextmenuRedHandler);
blue.addEventListener("click", clickBlueHandler);
blue.addEventListener("contextmenu", contextmenuBlueHandler);