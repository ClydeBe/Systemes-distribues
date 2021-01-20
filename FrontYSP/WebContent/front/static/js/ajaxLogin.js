const apiDomain = "http://localhost:80/YouShopPretty/webapi";

const submit = document.getElementById("signin");
const usernameField = document.getElementById("username");
const passwordField = document.getElementById("password");
const feedback = document.getElementById("feedback");

submit.addEventListener("click", (e) => {
    e.preventDefault();
    const username = usernameField.value;
    const password = passwordField.value;
    const logUser = {
        username: username,
        password: password
    }
    // console.log(logUser)
    // const logUrl = `${apiDomain}/account/signin`;
    // var logRequest = new XMLHttpRequest();
    // logRequest.onreadystatechange = function () {
    //     if (this.status === 200) {
    //         window.location.replace("/home.html");
    //     }
    //     else {
    //         const feedback = document.getElementById("feedback");
    //         feedback.style.display = "block";
    //     }
    // }
    // logRequest.open('POST', logUrl);
    // logRequest.send(JSON.stringify(logUser));
    
    let token = "Basic " + btoa(`${username}:${password}`);

    $.ajax({
            url: `${apiDomain}/account/signin`,
            type: 'post',
            contentType: "application/json",
            beforeSend: function(xhr){xhr.setRequestHeader('Authorization', token);},
            dataType: 'json',
            data: JSON.stringify(logUser),
        success: function (data, textStatus, request) {
            let bearerToken = request.getResponseHeader('Authorization');
            console.log(bearerToken)
            let toStore = bearerToken.replace("Bearer ", "");
            document.cookie = `JWT=Bearer ${toStore}`;
            window.location.replace("/home.html");
            },
            error : function(){
                feedback.style.display = "block";
            }
        });
        
    
});

