$(document).ready(function(){

    const params       = new URLSearchParams(window.location.search);
    const id           = params.get("id");
    
    const domain   = "http://localhost:80/YouShopPretty/webapi";
    const products = "products";

    // GET the product
    $.ajax({
        url: `${domain}/${products}/${id}`,
        type: 'get',
        success: function(response){
            let {price, name, imageLink,description,category,tags,quantity} = response;
            console.log(response);
            $('#id').val(`${id}`);
            $('#product_name').val(`${name}`);
            $('#description').val(`${description}`);
            $('#category').val(`${category}`);
            $('#tags').val(`${tags}`);
            $('#price').val(`${price}`);
            $('#quantity').val(`${quantity}`);
            $('#imageLink').val(`${imageLink}`);

        },
        error: function(){
            console.log("error AJAX GET");
        }
    });

    $("#submit-product").click(function(){
        let product_name = $('#product_name').val();
        let description  = $('#description').val();
        let category     = $('#category').val();
        let tags         = $('#tags').val();
        let price        = $('#price').val();
        let quantity     = $('#quantity').val();
        let imageLink    = $('#imageLink').val();

        let product = {
            id          : `${id}`,
            name        : `${product_name}`,
            description : `${description}`,
            category    : `${category}`,
            tags        : `${tags}`,
            price       : `${price}`,
            quantity    : `${quantity}`,
            imageLink   : `${imageLink}`,
        }

        $("#content").append(`
            <div class="text-center">
                <div class="spinner-border" role="status">
                    <span class="visually-hidden">Loading...</span>
                </div>
            </div>
        `);
            // PUT the modified product
        $.ajax({
            url: `${domain}/${products}`,
            type: 'put',
            contentType: "application/json",
            dataType: 'json',
            data: JSON.stringify(product),
            success: function(response){
                console.log("Terminé")
            },
            error : function(){
                console.log("error AJAX PUT");
            }
        });

        alert(` Modification effectué sur ${product_name}`)
        window.location.replace("/yspfront/site/admin/admin.html");
    })


});


