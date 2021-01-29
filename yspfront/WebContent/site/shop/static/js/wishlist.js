function addOnWishList(id,name){

    const domain    = "http://localhost:80/YouShopPretty/webapi";
    const whishlist = "whishList";

    if(document.cookie.includes('YSPsessionId')){
        let token = document.cookie.split(";").filter(e => e.includes("YSPsessionId"))[0].replace('YSPsessionId=','');
        let user  = JSON.parse(atob(token.split('.')[1]));

        let wishList = {
            userId   : user.UserId,
            products : `{id : ${name}}`
        };

        $.ajax({
            url: `${domain}/whishList/userid/${user.UserId}`,
            type: 'get',
            success: function(data, textStatus, XMLHttpRequest){
                console.log("GET wishList status : " + textStatus);
                if(textStatus == "nocontent"){
                    $.ajax({
                        url: `${domain}/whishList`,
                        type: 'post',
                        contentType: "application/json",
                        dataType: 'json',
                        data: JSON.stringify(wishList),
                        success: function(response){
                            alert(`${name} a été Ajouté POST`);
                        },
                        error : function(a,b,errorThrow){
                            console.log("error AJAX POST");
                            alert(`${name} n'a pas pu être Ajouté POST`);
                            console.log(errorThrow);
                        }
                    });
                }
                else{
                    console.log("Contient des produits")
                }
            },
            error: function(){
                console.log("error AJAX GET");
            }
        });
    }
    else{
        alert("vous devez vous connecter pour ajouter cet article");
    }
}


// let product = JSON.parse(data.products);
// product.id  = name;
// wishList = {
//     userId   : user.UserId,
//     products : product
// } 
// $.ajax({
//     url: `${domain}/${whishlist}`,
//     type: 'put',
//     contentType: "application/json",
//     dataType: 'json',
//     data: JSON.stringify(wishList),
//     success: function(response){
//         alert(`${name} a été Ajouté PUT`);
//     },
//     error : function(){
//         console.log("error AJAX POST");
//         alert(`${name} n'a pas pu être Ajouté PUT`);
//     }
// });