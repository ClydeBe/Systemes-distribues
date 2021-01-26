const apiDomain = "http://localhost:80/YouShopPretty/webapi"
const emailField = document.getElementById("id_email")
const globalFeedback = document.getElementById("globalFeedback");
const submit = document.getElementById("submit");

submit.addEventListener("click", (e) => {
    e.preventDefault();
    const email = emailField.value;
    const url = `${apiDomain}/account/reset/${email}`;
    let request = new XMLHttpRequest();
    request.open("POST", url);
    request.send();
    globalFeedback.style.display = "block"
});