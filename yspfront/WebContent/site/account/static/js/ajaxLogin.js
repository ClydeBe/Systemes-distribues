const apiDomain = "http://localhost:86/YouShopPretty/webapi";

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

    let token = "Basic " + btoa(`${username}:${password}`);

    const logUrl = `${apiDomain}/account/signin`;
    var logRequest = new XMLHttpRequest();
    logRequest.onreadystatechange = function () {
        if (this.readyState === 4) {
            if (this.status == 200) {
                window.location.replace("/yspfront/site/shop/index.html");
            }
            else {
                feedback.style.display = "block";
            }
        }
    }
    logRequest.open('POST', logUrl);
    logRequest.setRequestHeader("Authorization", token);
    logRequest.setRequestHeader("Content-Type", "application/json");
    logRequest.send(JSON.stringify(logUser));
    


    // var post = $.ajax({
    //         url: `${apiDomain}/account/signin`,
    //         type: 'post',
    //         contentType: "application/json",
    //         dataType: 'json',
    //         data: JSON.stringify(logUser),
    //     success: function (data, textStatus, response) {
    //         let bearerToken = this.getResponseHeader('Authorization');
    //         console.log(bearerToken)
    //         let toStore = bearerToken.replace("Bearer ", "");
    //         document.cookie = `JWT=Bearer ${toStore}`;
    //         window.location.replace("/yspfront/site/home.html");
    //         },
    //         error : function(){
    //             feedback.style.display = "block";
    //         }
    //     });
        
    
});

