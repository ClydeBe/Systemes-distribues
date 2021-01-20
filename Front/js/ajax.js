const usernameField = document.getElementById("id_username");
const usernameFeedback = document.getElementById("usernameFeedback");
const submit1 = document.getElementById("submit");
const emailField = document.getElementById("id_email");
const emailFeedback = document.getElementById("emailFeedback");
const newPassword1Field = document.getElementById("id_password1");
const newPassword2Field = document.getElementById("id_password2");
const newPassword1Feedback = document.getElementById("password1Feedback");
const newPassword2Feedback = document.getElementById("password2Feedback");

submit1.disabled = true;

usernameField.addEventListener("input", (e) =>{
    const username = e.target.value;
    var request = new XMLHttpRequest();
    var url = "/accounts/validate_username/" + username;
    request.onreadystatechange = function(){
        if (username.length >= 3)
        {
            if (this.readyState == XMLHttpRequest.DONE) {
                if (this.status == 200)
                {
                    usernameField.classList.remove("is-invalid");
                    usernameField.classList.add("is-valid");
                    submit1.removeAttribute("disabled");
                    usernameFeedback.style.display = "none";
                }
                else
                {
                    var response = JSON.parse(this.responseText);
                    usernameFeedback.innerHTML = `<small><center><p style='color:red'>${response.username_error}</p></center></small>`;
                    usernameFeedback.style.display = "block";
                    usernameField.classList.add("is-invalid");
                    usernameField.classList.remove("is-valid");
                    submit1.disabled = true ;
                }
            }
        }
        else
        {
            usernameFeedback.innerHTML = "<small><center><p style='color:red'>Le pseudo doit contenir au moins 3 caractères</p></center></small>";
            usernameFeedback.style.display = "block";
            usernameField.classList.add("is-invalid");
            submit1.disabled = true ;
        }
    }
    request.open("GET", url);
    request.send();
});

emailField.addEventListener("input", (e)=>{
    const email = e.target.value;
    const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    if (re.test(email))
    {
        var request = new XMLHttpRequest();
        var url = "/accounts/validate_email/" + email;
        request.onreadystatechange = function(){
            if (this.readyState == XMLHttpRequest.DONE)
            {
                if (this.status === 200)
                {
                    emailField.classList.remove("is-invalid");
                    emailField.classList.add("is-valid");
                    emailFeedback.style.display = "none";
                    submit1.removeAttribute("disabled");
                }
                else
                {
                    emailField.classList.add("is-invalid");
                    emailField.classList.remove("is-valid");
                    var response = JSON.parse(this.responseText);
                    emailFeedback.innerHTML = `<small><center><p style='color:red'>${response.email_error}</p></center></small>`;
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
        emailField.classList.add("is-invalid");
        emailField.classList.remove("is-valid");
        emailFeedback.innerHTML = "<small><center><p style='color:red'>Entrez une adresse valide</p></center></small>"
        emailFeedback.style.display = "block";
        submit1.disabled = true;
    }
});

newPassword1Field.addEventListener("input", (e)=>{
    const password1 = e.target.value;
    if (password1.length < 8)
    {
        newPassword1Field.classList.add("is-invalid");
        newPassword1Field.classList.remove("is-valid");
        newPassword1Feedback.innerHTML = "<small><center><p style='color:red'>Le mot de passe doit contenir au moins 8 caractères</p></center></small>";
        newPassword1Feedback.style.display = "block";
        submit1.disabled = true;
    }
    else
    {
        newPassword1Field.classList.remove("is-invalid");
        newPassword1Field.classList.add("is-valid");
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
        newPassword2Field.classList.remove("is-invalid");
        newPassword2Field.classList.add("is-valid");
        newPassword2Feedback.style.display = "none";
        submit1.removeAttribute("disabled");
    }
    else
    {
        newPassword2Field.classList.add("is-invalid");
        newPassword2Field.classList.remove("is-valid");
        newPassword2Feedback.innerHTML = "<small><center><p style='color:red'>Les mots de passes ne sont pas identiques</p></center></small>";
        newPassword2Feedback.style.display = "block";
        submit1.disabled = true;
    }
});