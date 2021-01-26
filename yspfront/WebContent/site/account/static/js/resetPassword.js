const apiDomain = "http://localhost:80/YouShopPretty/webapi";
const newPassword1Field = document.getElementById("id_password1");
const newPassword2Field = document.getElementById("id_password2");
const Password1ColorFeedback = document.getElementById("password1ColorFeedback");
const Password2ColorFeedback = document.getElementById("password2ColorFeedback");
const newPassword1Feedback = document.getElementById("password1Feedback");
const newPassword2Feedback = document.getElementById("password2Feedback");
const textFeedback = document.getElementById("textFeedback");
const globalFeedback = document.getElementById("globalFeedback");
const submit = document.getElementById("submit");

newPassword1Field.addEventListener("input", (e)=>{
    const password1 = e.target.value;
    if (password1.length < 8)
    {
        Password1ColorFeedback.classList.add("text-danger");
        Password1ColorFeedback.classList.remove("text-success");
        newPassword1Feedback.innerHTML = `<small><center><p style='color:red'>Le mot de passe doit contenir au moins 8 caractères</p></center></small>`;
        newPassword1Feedback.style.display = "block";
        submit1.disabled = true;
    }
    else
    {
        Password1ColorFeedback.classList.remove("text-danger");
        Password1ColorFeedback.classList.add("text-success");
        newPassword1Feedback.style.display = "none";
        submit1.removeAttribute("disabled");
    }
}
);

newPassword2Field.addEventListener("input", (e)=>{
    const password1 = e.target.value;
    const password2 = newPassword1Field.value
    if (password1 == password2)
    {
        Password2ColorFeedback.classList.remove("text-danger");
        Password2ColorFeedback.classList.add("text-success");
        newPassword2Feedback.style.display = "none";
        submit1.removeAttribute("disabled");
    }
    else
    {
        Password2ColorFeedback.classList.add("text-danger");
        Password2ColorFeedback.classList.remove("text-success");
        newPassword2Feedback.innerHTML = `<small><center><p style='color:red'>Les mots de passes ne correspondent pas</p></center></small>`;
        newPassword2Feedback.style.display = "block";
        submit1.disabled = true;
    }
});

submit.addEventListener('click', (e) => {
    e.preventDefault();
    const password = newPassword1Field.value;
    let request = new XMLHttpRequest();
    const resetToken = new URLSearchParams(window.location.search).get("resetToken");
    const url = `${apiDomain}/account/resetpassword?resetToken=${resetToken}`;
    
    let user = {
        password: password
    };
    request.onreadystatechange = () => {
        if (this.readyState == XMLHttpRequest.DONE) {
            if (this.status == 200) {
                globalFeedback.style.display = "block"
                textFeedback.innerHTML = "Votre mot de passe a bien été modifié"
            }
            else {
                globalFeedback.style.display = "block"
                textFeedback.innerHTML = "Une erreur est survenue. Veuillez vous assuré que le lien de résiliation nest toujours valide"
            }
        }
    }
    request.open("POST", url);
    request.setRequestHeader("Content-Type", "application/json");
    request.send(JSON.stringify(user));
});