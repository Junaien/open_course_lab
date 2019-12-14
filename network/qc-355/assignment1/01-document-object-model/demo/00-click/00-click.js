/* 00-click.js */
let click_count = 0;
const btn00 = document.getElementById('btn00');
const clickHandler = function(){
    console.log(`Button clicked ${--click_count} times Ah Ah Ah`);
}
btn00.addEventListener("click", clickHandler);