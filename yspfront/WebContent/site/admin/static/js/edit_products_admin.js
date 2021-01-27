const domain   = "http://localhost:80/YouShopPretty/webapi";
const products = "products";
const search   = "search";
const route    = "yspfront/site"; 
const admin    = "admin";

$(document).ready(function(){

    /* GET All Product */
    $.ajax({
        url: `${domain}/${products}`,
        type: 'get',
        success: function(response){
            console.log(response);
            for(i=0; i < response.length && i<=26; i++){
                let {id,price, name,quantity} = response[i];
                $('#all_products').append(`
                    <div class="shadow-sm p-3 mb-5 bg-white rounded col-lg-12">
                        <div class=" badge badge-info badge-pill p-2 rounded d-inline">
                            ${i}
                        </div>
                        <div class=" ml-3 d-inline">
                            ${name}
                        </div>
                        <div class=" ml-3 d-inline">
                            Quantity : ${quantity}
                        </div>
                        <div class=" ml-3 d-inline">
                            Price : ${price} €
                        </div>
                        <div class="float-right">
                            <a href=/${route}/${admin}/edit_product.html?id=${id} class="btn btn-success"> <i class="far fa-edit"></i> Edit </a>
                            <button onclick="Delete(${id})" type="button" class="btn btn-danger">  <i class="far fa-trash-alt"></i> Delete </button>
                        </div>
                    </div>
                `);
            }
        },
        error: function(){
            console.log("error AJAX GET");
        }
    });

    $("#submit-form").click(function(e){
        e.preventDefault();
        const query = $("#search").val();
        console.log(query);
        $.ajax({
            url: `${domain}/${products}/${search}/${query}`,
            type: 'get',
            success: function(response){
                if(response.length != 0){
                    for(i=0; i < response.length && i<=17; i++){
                        let {id,price, name,quantity} = response[i];
                        $("#search_result").html(`
                            <div class="shadow-sm p-3 mb-5 bg-white rounded col-lg-12">
                                <div class=" badge badge-info badge-pill p-2 rounded d-inline">
                                    ${i}
                                </div>
                                <div class=" ml-3 d-inline">
                                    ${name}
                                </div>
                                <div class=" ml-3 d-inline">
                                    Quantity : ${quantity}
                                </div>
                                <div class=" ml-3 d-inline">
                                    Price : ${price} €
                                </div>
                                <div class="float-right">
                                    <a href=/${route}/${admin}/edit_product.html?id=${id} class="btn btn-success"> <i class="far fa-edit"></i> Edit </a>
                                    <button onclick="Delete(${id})" type="button" class="btn btn-danger">  <i class="far fa-trash-alt"></i> Delete </button>
                                </div>
                            </div>
                        `);
                        $("#all_products").css('display','none');
                    }
                }
                else{
                    $("#search_result").html(`No result for ${query} `);
                    $("#all_products").css('display','none');
                }
            },
            error: function(){
                console.log("error AJAX GET");
            }
        });
    });

});


function Delete(id){
    var r = confirm("Voulez vous vraiment supprimer ce produit ?");
    if (r == true) {
        $.ajax({
            url: `${domain}/${products}/${id}`,
            type: 'delete',
            success: function(response){
                console.log('terminé');
                alert("Produit supprimé");
            },
            error : function(){
                console.log("error AJAX DELETE");
            }
        });
    } else {
      
    }
}

// $.ajax({
//     url: `${domain}/${products}`,
//     type: 'get',
//     success: function(response){
//         console.log(response);
//         for(i=0; i < response.length && i<=17; i++){
//             let {id,price, name,quantity} = response[i];
//             $('#edit_product .container .row').append(`
//                 <div class="shadow-sm p-3 mb-5 bg-white rounded col-lg-12">
//                     <div class=" badge badge-info badge-pill p-2 rounded d-inline">
//                         ${i}
//                     </div>
//                     <div class=" ml-3 d-inline">
//                         ${name}
//                     </div>
//                     <div class=" ml-3 d-inline">
//                         Quantity : ${quantity}
//                     </div>
//                     <div class=" ml-3 d-inline">
//                         Price : ${price} €
//                     </div>
//                     <div class="float-right">
//                         <a href=/${route}/${admin}/edit_product.html?id=${id} class="btn btn-success"> <i class="far fa-edit"></i> Edit </a>
//                         <button onclick="Delete(${id})" type="button" class="btn btn-danger">  <i class="far fa-trash-alt"></i> Delete </button>
//                     </div>
//                 </div>
//             `);
//         }
//     },
//     error: function(){
//         console.log("error AJAX GET");
//     }
// });