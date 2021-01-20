//import {  } from "./jQuery"

const domain = "http://localhost:80/YouShopPretty/webapi/"

console.log("ok");



let jsonBody = {
    username: "Rantanplan",
    password: "Rantanplan",
    email: "gg@gg.gg"
};
var request = new XMLHttpRequest();
request.open("POST", "http://localhost:80/YouShopPretty/webapi/account");
request.setRequestHeader("Content-Type", "application/json");
request.addEventListener("load", function () {
    if (request.status >= 200 && request.status < 400) {
        // Appelle la fonction callback en lui passant la réponse de la requête
        console.log("Posted and work")
    } else {
        console.error("Some errors");
    }
});
request.addEventListener("error", function () {
    console.error("Erreur réseau avec l'URL ");
});

request.send(JSON.stringify(jsonBody));

// Création d'une requête HTTP
// var req = new XMLHttpRequest();
// // Requête HTTP GET synchrone vers le fichier langages.txt publié localement
// req.open("GET", "http://localhost:95/YouShopPretty/webapi/Account", false);
// // Envoi de la requête
// req.send(null);
// // Affiche la réponse reçue pour la requête
// console.log(req.responseText);