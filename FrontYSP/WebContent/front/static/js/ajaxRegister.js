const apiDomain = "http://localhost:80/YouShopPretty/webapi";
const usernameField = document.getElementById("id_username");
const usernameFeedback = document.getElementById("usernameFeedback");
const submit1 = document.getElementById("submit");
const emailField = document.getElementById("id_email");
const emailFeedback = document.getElementById("emailFeedback");
const newPassword1Field = document.getElementById("id_password1");
const newPassword2Field = document.getElementById("id_password2");
const newPassword1Feedback = document.getElementById("password1Feedback");
const newPassword2Feedback = document.getElementById("password2Feedback");
const usernameColorFeedback = document.getElementById("usernameColorFeedback");
const emailColorFeedback = document.getElementById("emailColorFeedback");
const Password1ColorFeedback = document.getElementById("password1ColorFeedback");
const Password2ColorFeedback = document.getElementById("password2ColorFeedback");

submit1.disabled = true;

usernameField.addEventListener("input", (e) =>{
    let username = e.target.value;
    let patern = /^[a-z0-9]+$/i;
    var request = new XMLHttpRequest();
    var url = `${apiDomain}/account/validateUsername/${username}`;

        if (username.length >= 3)
        {
            if (username.match(patern)) {
                request.onreadystatechange = function () {
                    if (this.readyState == XMLHttpRequest.DONE) {
                        if (this.status === 200) {
                            usernameColorFeedback.classList.remove("text-danger");
                            usernameColorFeedback.classList.add("text-success");
                            submit1.removeAttribute("disabled");
                            usernameFeedback.style.display = "none";
                        }
                        else {
                            usernameFeedback.innerHTML = `<small><center><p style='color:red'>Ce pseudo est déjà utilisé</p></center></small>`;
                            usernameFeedback.style.display = "block";
                            usernameColorFeedback.classList.add("text-danger");
                            usernameColorFeedback.classList.remove("text-success");
                            submit1.disabled = true;
                        }
                    }
                }
            }
            else {
                usernameFeedback.innerHTML = "<small><center><p style='color:red'>Le pseudo doit être alphanumérique</p></center></small>";
                usernameFeedback.style.display = "block";
                usernameColorFeedback.classList.add("text-danger");
                usernameColorFeedback.classList.remove("text-success");
                submit1.disabled = true ;
            } 
        }
        else
        {
            usernameFeedback.innerHTML = "<small><center><p style='color:red'>Le pseudo doit contenir au moins 3 caractères</p></center></small>";
            usernameFeedback.style.display = "block";
            usernameColorFeedback.classList.add("text-danger");
            usernameColorFeedback.classList.remove("text-success");
            submit1.disabled = true ;
        }
    request.open("GET", url);
    request.send();
});

emailField.addEventListener("input", (e)=>{
    let email = e.target.value;
    const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    if (re.test(email))
    {
        var request = new XMLHttpRequest();
        var url = `${apiDomain}/account/validateEmail/${email}`;
        request.onreadystatechange = function(){
            if (this.readyState == XMLHttpRequest.DONE)
            {
                if (this.status === 200)
                {
                    emailColorFeedback.classList.remove("text-danger");
                    emailColorFeedback.classList.add("text-success");
                    emailFeedback.style.display = "none";
                    submit1.removeAttribute("disabled");
                }
                else
                {
                    emailColorFeedback.classList.add("text-success");
                    emailColorFeedback.classList.remove("text-danger");
                    emailFeedback.innerHTML = `<small><center><p style='color:red'>Un utilisateur avec ce mail existe déjà</p></center></small>`;
                    emailFeedback.style.display = "block";
                    submit1.disabled = true;
                }
            }
        }
        request.open("GET", url);
        request.send();
    }
    else
    {
        emailColorFeedback.classList.add("text-danger");
        emailColorFeedback.classList.remove("text-success");
        emailFeedback.innerHTML = "<small><center><p style='color:red'>Entrez une adresse valide</p></center></small>"
        emailFeedback.style.display = "block";
        submit1.disabled = true;
    }
});

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



//Sending the POST request
submit1.addEventListener("click", (e) => {
    e.preventDefault();
    let user = {
        username: usernameField.value,
        email: emailField.value,
        password: newPassword1Field.value
    };
    const url = `${apiDomain}/account`;
    let request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        if (this.status == 201) {
            //Si l'inscription est validé, on connecte l'utilisateur et recupère son token
            const { username, password } = user;
            const logUser = {
                username: username,
                password: password
            }
            const logUrl = `${apiDomain}/account/signin`;
            var logRequest = new XMLHttpRequest();
            logRequest.onreadystatechange = function () {
                if (this.status == 200) {
                    window.location.replace("/home.html");
                }
            }
            logRequest.open('POST', logUrl);
            logRequest.setRequestHeader("Content-Type", "application/json");
            logRequest.send(JSON.stringify(logUser));
        }
    }
    request.open('POST', url);
    request.setRequestHeader("Content-Type", "application/json");
    request.send(JSON.stringify(user));
})