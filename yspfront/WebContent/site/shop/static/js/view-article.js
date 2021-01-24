

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
            let {price, name, imageLink,quantity,description,caracteristics,tags,category} = response;
            $(".price").html(`${price}€`); 
            $(".item-title").html(`${name}`);
            $("#article_image").attr("src",imageLink);
            $("#article-name").append(`${name}`);
            if(quantity > 0){
                $(".quantity").append(`<p class="text-primary"><i class="fas fa-check"></i> ${quantity} In stock</p>`);
            }
            else{
                $(".quantity").append(`<p class="text-danger"><i class="fas fa-times"></i> Not in stock</p>`);
            }

            descript = description.split(",");
            for(let des of descript){
                $(".descirpt").append(`<span id="description_badge" class="badge bg-secondary">${des}</span>`);
            }

            caract = JSON.parse(caracteristics);

            for(let carac in caract){
                $(".caracteristic").append(`<p>${carac} : ${caract[carac]}<p>`);
            }

        },
        error: function(){
            console.log("error AJAX GET");
        }
    });


})



