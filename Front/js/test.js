let products = {
    "1" :   {
                "id" : 1,
                "quantity" : 15,
                "price" : 12
            },
    "2" :   {
                "id" : 2,
                "quantity" : 23,
                "price" : 8
            }
};
user_id = cookie.getId();

let req = new XMLHttpRequest();
req.open("POST", "url");

let wishlist = {
    "user_id": user_id,
    "producst" : JSON.stringify(products)
}    
console.log(JSON.stringify(products));