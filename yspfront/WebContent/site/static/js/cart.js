
$(document).ready(function(){

    // if(document.cookie.includes('YSPsessionId')){
    //     let cart_list = [];

    //     let enc_cart_list = btoa(cart_list);
    
    //     document.cookie   = `YSPcart = ${enc_cart_list}; path=/`;
    // }
    // else{
    //     console.log("vous devez vous connecter");
    // }
})

function addToCart(id,price,name,imageLink,quantity){
    
    if(document.cookie.includes('YSPsessionId')){
        if(quantity != 0){
            let token = document.cookie.split(";").filter(e => e.includes("YSPsessionId"))[0].replace('YSPsessionId=','');
            let user  = atob(token.split('.')[1]);

            if(document.cookie.includes('YSPcart')){
                let token_cart = document.cookie.split(";").filter(e => e.includes("YSPcart"))[0].replace('YSPcart=',''); 
                let cart_list  = atob(token_cart).split(",");
        
                cart_list.unshift(`${id}§${price}§${name}§${imageLink}§${quantity}`);
                let enc_cart_list = btoa(cart_list);
        
               // document.cookie = "YSPcart=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
                document.cookie = `YSPcart = ${enc_cart_list}; path=/`;
        
                $("#nb-items-cart").html(`${cart_list.length}`);
            }
            else{
                let cart_list = [];
                cart_list.unshift(`${id}§${price}§${name}§${imageLink}§${quantity}`);
                let enc_cart_list = btoa(cart_list);
                document.cookie   = `YSPcart = ${enc_cart_list}; path=/`;   
                $("#nb-items-cart").html(`${cart_list.length}`);
            }
            console.log(cart_list);
        }
        else{
            alert("Not in stock");
        }
        
    }
    else{
        alert("vous devez être connecté pour ajouter un produit à votre panier");
    }

}