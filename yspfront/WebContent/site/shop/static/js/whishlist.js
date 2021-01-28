function addOnWishList(id,name){

    const domain    = "http://localhost:80/YouShopPretty/webapi";
    const whishlist = "whishList";

    if(document.cookie.includes('YSPsessionId')){
        let token = document.cookie.split(";").filter(e => e.includes("YSPsessionId"))[0].replace('YSPsessionId=','');
        let user  = JSON.parse(atob(token.split('.')[1]));
    
        let wishList = {
            userId   : user.UserId,
            products : JSON.stringify({id : name})
        }
        
        $.ajax({
            url: `${domain}/${whishlist}/userid/${user.UserId}`,
            type: 'get',
            success: function(data, textStatus, XMLHttpRequest){
                console.log(data);
                if(textStatus == "nocontent"){
                    $.ajax({
                        url: `${domain}/${whishlist}`,
                        type: 'post',
                        contentType: "application/json",
                        dataType: 'json',
                        data: JSON.stringify(wishList),
                        success: function(data, textStatus, XMLHttpRequest){
                            console.log("Produit ajouté POST");
                        },
                  
                    });
                }
                else{
                    console.log("DEBUT PUT");
                    let product  = JSON.parse(data.products);
                    product[id]  = name;
                    wishList = {
                        userId   : user.UserId,
                        products : product
                    } 
                    $.ajax({
                        url: `${domain}/${whishlist}`,
                        type: 'put',
                        contentType: "application/json",
                        dataType: 'json',
                        data: JSON.stringify(wishList),
                        success: function(response){
                            alert(`${name} a été Ajouté PUT`);
                        },
                        error : function(){
                            console.log("error AJAX PUT");
                            alert(`${name} n'a pas pu être Ajouté PUT`);
                        }
                    });
                }
            }
        });
    

    }
    else{
        alert("Vous devez vous connecter");
    }




    // if(document.cookie.includes('YSPsessionId')){
    //     let token = document.cookie.split(";").filter(e => e.includes("YSPsessionId"))[0].replace('YSPsessionId=','');
    //     let user  = JSON.parse(atob(token.split('.')[1]));

    //     let wishList = {
    //         userId   : user.UserId,
    //         products : `{id : ${name}}`
    //     }

    //     console.log(wishList);
    //     $.ajax({
    //         url: `${domain}/${whishlist}/userid/${user.UserId}`,
    //         type: 'get',
    //         success: function(data, textStatus, XMLHttpRequest){
    //             if(textStatus == "nocontent"){
    //                 $.ajax({
    //                     url: `${domain}/${whishlist}`,
    //                     type: 'post',
    //                     contentType: "application/json",
    //                     dataType: 'json',
    //                     data: JSON.stringify(wishList),
    //                     success: function(data, textStatus, XMLHttpRequest){
    //                         console.log(textStatus);
    //                     },
    //                     error : function(data, textStatus, XMLHttpRequest){
    //                         console.log(data + " " + textStatus + " "+ XMLHttpRequest );
    //                         alert(`${name} n'a pas pu être Ajouté`);
    //                     }
    //                 });
    //             }
    //             else{
    //                 console.log(textStatus);
    //             }
    //         },
    //         error: function(){
    //             console.log("error AJAX GET");
    //         }
    //     });
        

    // }
    // else{
    //     alert("Vous devez vous connecter");
    // }

}