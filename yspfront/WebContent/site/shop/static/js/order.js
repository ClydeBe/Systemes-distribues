function fct_order(){
    const domain   = "http://localhost:80/YouShopPretty/webapi";
    const order    = "order";
    const products = "products";

    if(document.cookie.includes('YSPsessionId')){

        let token = document.cookie.split(";").filter(e => e.includes("YSPsessionId"))[0].replace('YSPsessionId=','');
        let user  = JSON.parse(atob(token.split('.')[1]));
        console.log(user);

        if(document.cookie.includes('YSPcart')){
            
            let token_cart = document.cookie.split(";").filter(e => e.includes("YSPcart"))[0].replace('YSPcart=',''); 
            let cart_list  = atob(token_cart).split(",");

            let p =""; 

            for(i = 0; i < cart_list.length; i++){
               let article = cart_list[i].split("§");
               p = p  + "!" + article[0] + ":" + $(`#${i}`).val();
            }
            p = p.substring(1, p.length);

            let order_user = {
                userId      : user.UserId,
                products    : p ,
                isProcessed : false  
            };
            console.log(order_user);
            let status;

            $.ajax({
                url: `${domain}/${order}`,
                type: 'post',
                contentType: "application/json",
                dataType: 'json',
                data: JSON.stringify(order_user),
                success: function(data, textStatus, XMLHttpRequest){
                    console.log(textStatus);
                },
            });

            alert("Your order will be manage soon");
            document.cookie = "YSPcart=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
            window.location.replace("/yspfront/site/shop/index.html");


        }

    }
}

// for(i = 0; i < cart_list.length; i++){
//     let article  = cart_list[i].split("§");
//     let quantity = article[4] - $(`#${i}`).val();  

//     let product = {
//         id : article[0],
//         quantity : quantity
//     }
    
//     $.ajax({
//         url: `${domain}/${products}`,
//         type: 'put',
//         contentType: "application/json",
//         dataType: 'json',
//         data: JSON.stringify(product),
//         success: function(data, textStatus, XMLHttpRequest){
            
//         },
//     });
// }