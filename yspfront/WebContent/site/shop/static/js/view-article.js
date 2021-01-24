

$(document).ready(function(){

    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");
    const domain = "http://localhost:86/YouShopPretty/webapi/";
    const products = `products/${id}`;

    $.ajax({
        url: `${domain}/${products}`,
        type: 'get',
        success: function(response){
            //console.log(response)
            let {price, name, imageLink} = response;
            $(".price").html(`${price}€`); 
            $(".item-title").html(`${name}`);
            $("#article_image").attr("src",imageLink);
            $("#article-name").append(`${name}`);
        },
        error: function(){
            console.log("error AJAX GET");
        }
    });


})



