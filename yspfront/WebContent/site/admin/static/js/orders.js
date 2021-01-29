const domain   = "http://localhost:80/YouShopPretty/webapi";
const order    = "order";
const account  = "account";
const search   = "search";
const route    = "yspfront/site"; 
const admin    = "admin";

$(document).ready(function(){
    
        /* GET All Orders */
        $.ajax({
            url: `${domain}/${order}`,
            type: 'get',
            success: function(response){
                console.log(response);
                for(i=0; i < response.length && i<=26; i++){
                    let {id,userId,processed,products} = response[i];
                    $.ajax({
                        url: `${domain}/${account}/${userId}`,
                        type: 'get',
                        success: function(response){
                            let {username,email} = response;
                            $('#content').append(`
                            <div class="shadow-sm p-3 mb-5 bg-white rounded col-lg-12">
                                <div class=" badge badge-info badge-pill p-2 rounded d-inline">
                                    ${i}
                                </div>
                                <div class=" ml-3 d-inline">
                                    ${username}
                                </div>
                                <div class=" ml-3 d-inline">
                                    isProcessed : ${processed}
                                </div>
                                <div class=" ml-3 d-inline">
                                    Email : ${email}
                                </div>
                                <div class="float-right">
                                    <button onclick="ship(${id})" type="button" class="btn btn-success p-2"> <i class="fa fa-paper-plane" aria-hidden="true"></i> Ship </button>
                                    <button onclick="deleteOrder(${id})" type="button" class="btn btn-danger p-2">  <i class="far fa-trash-alt"></i> Delete </button>
                                </div>
                            </div>
                        `);
                        },
                        error: function(){
                            console.log("error AJAX GET");
                        }
                    });
                }
            },
            error: function(){
                console.log("error AJAX GET");
            }
        });

});

function ship(id_order){

    let obj_order = {
        id : id_order,
        isProcessed : true
    };

    $.ajax({
        url: `${domain}/${order}`,
        type: 'put',
        contentType: "application/json",
        dataType: 'json',
        data: JSON.stringify(obj_order),
        success: function(data, textStatus, XMLHttpRequest){
            console.log("Commande Expédié");
        },
        error : function(){
            console.log("error AJAX PUT");
        }
    });

    // let p = products.split("!");

    // for(i = 0; i < p.length; i++){
    //     let pr = p.split(":");
    //     let id = p[0];
    //     let q  = p[1];
    //     $.ajax({
    //         url: `${domain}/${products}/${id}`,
    //         type: 'get',
    //         success: function(response){
    //             let {quantity} = response;
    //             let qt = quantity - q;  

    //             let product = {
    //                 id : id,
    //                 quantity : qt
    //             }

    //             $.ajax({
    //                 url: `${domain}/${products}`,
    //                 type: 'put',
    //                 contentType: "application/json",
    //                 dataType: 'json',
    //                 data: JSON.stringify(product),
    //                 success: function(data, textStatus, XMLHttpRequest){
            
    //                 },
    //             });
    //         },
    //         error: function(){
    //             console.log("error AJAX GET");
    //         }
    //     });
    // }

}

function deleteOrder(id){
    var r = confirm("Voulez vous vraiment supprimer cette commande ?");
    if (r == true) {
        $.ajax({
            url: `${domain}/${order}/${id}`,
            type: 'delete',
            success: function(response){
                console.log('terminé');
                alert("Commande supprimé");
                window.location.reload();
            },
            error : function(){
                console.log("error AJAX DELETE");
            }
        });
    }
}
