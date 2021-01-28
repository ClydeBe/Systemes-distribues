const domain = "http://localhost:80/YouShopPretty/webapi/";
const products = "products";

$(document).ready(function(){

    let idProduct = 1;

    /* GET All Product */
    $.ajax({
        url: `${domain}/${products}`,
        type: 'get',
        success: function(response){
            for(i=0; i < response.length && i<=17; i++){
                let {id,price, name,images} = response[i];
                $('#article-list .container .row').append(`
                <div class="col-lg-2 col-md-3 col-sm-4 col-xs-12" id="product">
                <div class="card">
                    <img src=${images} class="img-fluid" alt="item">
                    <div class="middle">
                        <a href="view-article.html?id=${id}" class="btn btn-prinmary"><i class="fas fa-eye"></i></a>
                        <a onclick="addToWishList(${id})" class="btn btn-prinmary"><i class="far fa-heart"></i></a>
                        <a href="#" class="btn btn-prinmary"><i class="fas fa-cart-plus"></i></a>
                    </div>
                </div>
                <div class="title-article">
                  <strong class="name-article">${name}</strong> 
                  <span id="price">${price}€</span>
                </div>
                <ul>
                    <li><i class="fas fa-star"></i></li>
                    <li><i class="fas fa-star"></i></li>
                    <li><i class="fas fa-star"></i></li>
                    <li><i class="fas fa-star"></i></li>
                    <li><i class="far fa-star"></i></li>
                </ul>
            </div>`)
            }
        },
        error: function(){
            console.log("error AJAX GET");
        }
    });


});


function addToWishList(id){

    if(document.cookie.includes('YSPsessionId')){
        let cookie = getCookie('YSPsessionId');
        //document.cookie.filter(e => e.includes("YSPsessionId"));
        console.log(cookie); 
        console.log(cookie);      
    }
    else{
        alert("vous devez vous connecter pour ajouter cet article");

    }

}

function getCookie(name) {
    var cookieArr = document.cookie.split(";");
    
    for(var i = 0; i < cookieArr.length; i++) {
        var cookiePair = cookieArr[i].split("=");
        if(name == cookiePair[0].trim()) {
            return decodeURIComponent(cookiePair[1]);
            //window.atob(enc);
        }
    }
    return null;
}