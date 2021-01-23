

$(document).ready(function(){

    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");
    const domain = "http://localhost:80/YouShopPretty/webapi/";
    const products = `products/${id}`;

    $.ajax({
        url: `${domain}/${products}`,
        type: 'get',
        success: function(response){
            //console.log(response)
            let {price, name, images} = response;
            $(".price").html(`${price}€`); 
            $(".item-title").html(`${name}`);
            $("#article_image").attr("src",images);
        },
        error: function(){
            console.log("error AJAX GET");
        }
    });


})



