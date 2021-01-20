const domain = "http://localhost:80/YouShopPretty/webapi/";
const account = "account";

$(document).ready(function(){
    
    /* OBJECT JS */
    var user = {
        username:"iuer",
        email: "iuerg@makitweb.com",
        password: "shedrhreh"
    }

    var id = 21;

    /* GET */
    $.ajax({
        url: `${domain}/${account}`,
        type: 'get',
        success: function(response){
            console.log(response[0])
            let {username, email, id} = response[0];
            $('#nom').html(id);
        },
        error: function(){
            console.log("error AJAX GET");
        }
    });

    /* POST */

    // TO DO : add a Listener
        $.ajax({
            url: `${domain}/${account}`,
            type: 'post',
            contentType: "application/json",
            dataType: 'json',
            data: JSON.stringify(user),
            success: function(response){
                console.log("terminé")
            },
            error : function(){
                console.log("error AJAX POST");
            }
        });

    /* PUT */

    // TO DO : add a Listener
        $.ajax({
            url: `${domain}/${account}`,
            type: 'put',
            contentType: "application/json",
            dataType: 'json',
            data: JSON.stringify(user),
            success: function(response){
                console.log("Terminé")
            },
            error : function(){
                console.log("error AJAX PUT");
            }
        });
   
    // TO DO : add a Listener

    /* DELETE */
        $.ajax({
            url: `${domain}/${account}/${id}`,
            type: 'delete',
            success: function(response){
                console.log('terminé')
            },
            error : function(){
                console.log("error AJAX DELETE");
            }
        });


})

