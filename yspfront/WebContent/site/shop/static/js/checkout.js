$(document).ready(function (){

    if(document.cookie.includes('YSPsessionId')){
        let token_cart = document.cookie.split(";").filter(e => e.includes("YSPcart"))[0].replace('YSPcart=',''); 
        let cart_list  = atob(token_cart).split(",");
        
        $("#nb-items-cart").html(cart_list.length);
        console.log(cart_list);

        if(cart_list.length != 0){
            for(i = 0; i < cart_list.length; i++){
                let article = cart_list[i].split("§");
                console.log(article);
                console.log(article[0]);
                console.log(article[1]);
                console.log(article[2]);
                console.log(article[3]);

                $("#list-items-cart").append(`
                <div class="row mb-4">
                    <div class="col-md-5 col-lg-3 col-xl-3">
                    <div class="view zoom overlay z-depth-1 rounded mb-3 mb-md-0">
                        <img class="rounded  img-fluid w-100"
                        src=${article[3]} alt="Sample">
                    </div>
                    </div>
                    <div class="col-md-7 col-lg-9 col-xl-9">
                    <div>
                        <div class="d-flex justify-content-between">
                        <div>
                            <h5>${article[2]}</h5>
                            <p class="mb-3 text-muted text-uppercase small">Shirt - blue</p>
                            <p class="mb-2 text-muted text-uppercase small">Color: blue</p>
                            <p class="mb-3 text-muted text-uppercase small">Size: M</p>
                        </div>
                        <div>
                            <div class="def-number-input number-input safari_only mb-0 w-100">
                            <input id="${i}" class="quantity form-control" min="1" max="${article[4]}" onclick="computeTotalTwo()" name="quantity" value="1" type="number">
                            </div>
                        </div>
                        </div>
                        <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <a id="remove-to-list" onclick="removeItem(${article[0]})" type="button" class="card-link-secondary small text-uppercase mr-3"><i
                                class="fas fa-trash-alt mr-1"></i> Remove item </a>
                            <a href="#!" type="button" class="card-link-secondary small text-uppercase"><i
                                class="fas fa-heart mr-1"></i> Move to wish list </a>
                        </div>
                        <p class="mb-0"><span><strong>${article[1]}€</strong></span></p>
                        </div>
                    </div>
                    </div>
                </div>
                <hr class="mb-4">
                `);
            }
        }
        else{
            $("#list-item-cart").html(` You don't have any product un tou cart `);
            $("#btn-order").prop('disabled',true);
        }

    }

    $("#total-Amount").html(computeTotal() + "€");
})

function computeTotal(){
    let token_cart = document.cookie.split(";").filter(e => e.includes("YSPcart"))[0].replace('YSPcart=',''); 
    let cart_list  = atob(token_cart).split(",");
    let total = 0;
    let total_TVA =0;
    
    $("#nb-items-cart").html(cart_list.length);
    console.log(cart_list);

    if(cart_list.length != 0){
        for(i = 0; i < cart_list.length; i++){
            let article = cart_list[i].split("§");
            total = total + article[1]*$(`#${i}`).val();
        }
        total_TVA = total +   (total *2)/100;
        total_TVA =  Math.round(total_TVA*100)/100 ;
        $("#total-Amount-tva").html(total_TVA + "€");
        return Math.round(total*100)/100 ;
    }
    else {
        return 0;
    }
}

function computeTotalTwo(){
    let token_cart = document.cookie.split(";").filter(e => e.includes("YSPcart"))[0].replace('YSPcart=',''); 
    let cart_list  = atob(token_cart).split(",");
    let total = 0;
    let total_TVA =0;
    $("#nb-items-cart").html(cart_list.length);
    console.log(cart_list);

    if(cart_list.length != 0){
        for(i = 0; i < cart_list.length; i++){
            let article = cart_list[i].split("§");
            total = total + article[1]*$(`#${i}`).val();
        }
        total     = Math.round(total*100)/100;
        total_TVA = total + (total *2)/100;
        total_TVA = Math.round(total_TVA*100)/100 ;

        $("#total-Amount").html(total + "€");
        $("#total-Amount-tva").html(total_TVA + "€");
    }
    else {
    }

}

function removeItem(id){
    let token_cart = document.cookie.split(";").filter(e => e.includes("YSPcart"))[0].replace('YSPcart=',''); 
    let cart_list  = atob(token_cart).split(",");

    $("#nb-items-cart").html(cart_list.length);
    console.log(cart_list);

    for(i=0;i <= cart_list.length;i++){
        let articles = cart_list[i].split("§");
        if(articles[0] == id){
            delete cart_list[i];
            break;
        }
    }

    let new_cart_list = [];
    let k = 0;

    for(i=0; i <= cart_list.length;i++){
        if(cart_list[i] != null){
            new_cart_list[k] = cart_list[i];
            k = k + 1;
        }
    }

    let enc_cart_list = btoa(new_cart_list);
    document.cookie = `YSPcart = ${enc_cart_list}; path=/`;
    window.location.reload();

}
