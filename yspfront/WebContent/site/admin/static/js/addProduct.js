$(document).ready(function(){

    const domain   = "http://localhost:80/YouShopPretty/webapi";
    const products = "products";

    // $("#add_product").append(`

    // `)
    $("#submit-addproduct").click(function(){

        let product_name   = $('#product_name').val();
        let description    = $('#description').val();
        let category       = $('#category').val();
        let tags           = $('#tags').val();
        let price          = parseInt($('#price').val());
        let quantity       = parseInt($('#quantity').val());
        let imageLink      = $('#imageLink').val();
        let caracteristics = $('#caracteristics').val();
        
        console.log(price + quantity);
        
        var product = {
            price          :  price,
            quantity       :  quantity,
            description    : `${description}`,
            name           : `${product_name}`,
            tags           : `${tags}`,
            caracteristics : `${caracteristics}`, 
            category       : `${category}`,
            imageLink      : `${imageLink}`,
        }
        console.log(product);

        $.ajax({
            url: `${domain}/${products}`,
            type: 'post',
            contentType: "application/json",
            dataType: 'json',
            data: JSON.stringify(product),
            success: function(response){
                alert(`${product_name} a été Ajouté`);
                $('#product_name').val();
                $('#description').val();
                $('#category').val();
                $('#tags').val();
                parseInt($('#price').val());
                parseInt($('#quantity').val());
                $('#imageLink').val();
                $('#caracteristics').val();
            },
            error : function(){
                console.log("error AJAX POST");
                alert(`${product_name} n'a pas pu être Ajouté`);
            }
        });
        
        
        //window.location.dreplace("/yspfront/site/admin/admin.html");
    });
    

})