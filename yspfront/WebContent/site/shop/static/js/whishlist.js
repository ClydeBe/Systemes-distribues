function addOnWishList(id,name){

    const domain    = "http://localhost:80/YouShopPretty/webapi";
    const whishlist = "whishList";
    let token 
    let user
    let wishList

    if(document.cookie.includes('YSPsessionId')){
        token = document.cookie.split(";").filter(e => e.includes("YSPsessionId"))[0].replace('YSPsessionId=','');
        user  = JSON.parse(atob(token.split('.')[1]));

        wishList = {
            userId   : user.UserId,
            products : JSON.stringify({id : name})
        }

        console.log(wishList);
        $.ajax({
            url: `${domain}/${whishlist}/userid/${user.UserId}`,
            type: 'get',
            statusCode: {
                204: (data, textStatus, xhr) => {
                    //JUST POST IT
                    $.ajax({
                        type: "post",
                        url: `${domain}/${whishlist}`,
                        data: JSON.stringify(whishlist),
                        dataType: "json",
                        success: function (response) {
                            alert("POSTED");
                        },
                        error: (response) => {
                            console.log(response);
                        }
                    });
                    );
                },
                204: (data, textStatus, xhr) => {
                    //GET, modify and PUT
                    $.ajax({
                        type: "get",
                        url: `${domain}/${whishlist}`,
                        dataType: "json",
                        success: function (data, textStatus, xhr) {
                            let prod = JSON.parse(JSON.parse(data).products)
                            prod[id] = name;
                            whishlist = {
                                userId: user.UserId,
                                products = prod
                            }

                        }
                    });
                }
            }
        });
        

    }
    else{
        location.href("/yspfront/site/account/login.html")
    }
}