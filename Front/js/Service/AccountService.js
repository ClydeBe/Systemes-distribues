//import {  } from "./jQuery"

const domain = "http://localhost:80/YouShopPretty/webapi/"

console.log("ok");
//Get all users
function get(url = "Account") {
    var request = new XMLHttpRequest();
    request.onreadystatechange = function() {
        if (this.readyState == XMLHttpRequest.DONE && this.status == 200) {
            console.log("Be");
            console.log(JSON.parse(this.responseText));
        }
    };
    request.open("GET", "http://localhost:80/YouShopPretty/webapi/account/3");
    request.send();
}

get();


// Création d'une requête HTTP
// var req = new XMLHttpRequest();
// // Requête HTTP GET synchrone vers le fichier langages.txt publié localement
// req.open("GET", "http://localhost:95/YouShopPretty/webapi/Account", false);
// // Envoi de la requête
// req.send(null);
// // Affiche la réponse reçue pour la requête
// console.log(req.responseText);