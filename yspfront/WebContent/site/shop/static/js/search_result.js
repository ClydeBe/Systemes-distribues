$(document).ready(function(){

    const params       = new URLSearchParams(window.location.search);
    const query_search = params.get("search");
    const category     = params.get("category");  
    

    const domain   = "http://localhost:86/YouShopPretty/webapi";
    const products = `products/search/${query_search}`;
    const route    = "yspfront/site"; 
    const shop     = "shop";
    $.ajax({
        url: `${domain}/${products}`,
        type: 'get',
        success: function(response){
            //console.log(response)
            let {price, name, imageLink} = response;
            if(response.length != 0){
                $("#nbItems").append(`(${response.length})`);
                for(i=0; i < response.length && i<=17; i++){
                        let {id,price, name,imageLink} = response[i];
                        $('#article-list .container .row').append(`
                        <div class="col-lg-2 col-md-3 col-sm-4 col-xs-12" id="product">
                        <div class="card">
                            <img src=${imageLink} class="img-fluid" alt="item">
                        </div>
                        <div class="middle">
                            <a href="/${route}/${shop}/view-article.html?id=${id}" class="btn btn-prinmary"><i class="fas fa-eye"></i></a>
                            <a href="#" class="btn btn-prinmary"><i class="far fa-heart"></i></a>
                            <a href="#" class="btn btn-prinmary"><i class="fas fa-cart-plus"></i></a>
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
                    </div>`);
                }
            }

            else{
                $('#article-list .container .row').append(` <div class="col-lg-12 text-center noresult mt-5">  No result for "${query_search}" </div>`);
                // $('footer').css({
                //     position :'fixed',
                //     width : '100%',
                //     bottom: '0',
                //     left: '0'
                // });

                $('footer').css('margin-top','80px');
            }
        },
        error: function(){
            console.log("error AJAX GET");
        }
    });



});


