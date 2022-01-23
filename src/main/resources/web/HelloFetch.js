//DOM Selection
let pokename = document.getElementById("pokename");
let poketype = document.getElementById("poketype");
let pokenum = document.getElementById("pokenum");
let pokepic = document.getElementById("pokepic");

let button = document.getElementById("btn");
button.addEventListener("click", fetchFunc);

async function fetchFunc() {
    let num = document.getElementById('field').value;

    let response = await fetch("https://pokeapi.co/api/v2/pokemon/" + num);

    if (response.status===200) {
        //it can take awhile to write an object to JSON, so we put the await here to make sure it finishes before continuing
        //otherwise me might log nothing in the next line of code
        let data = await response.json()
        console.log(data);
        renderData(data);
    } else {
        console.log("It got away!");
    }
}

function renderData(data) {
    //the sub-elements of the data are listed in the API on the pokemon website which is how we know
    //what to put after the "." after data.
    pokename.innerText = data.name;
    pokenum.innerText = data.id;
    poketype.innerText = data.types[0].type.name;
    if(data.types[1]) {
        //this will only run if there's actually an object at [1] of the array
        poketype.append(", " + data.types[1].type.name);
    }
    pokepic.setAttribute('src', data.sprites.front_default);
}