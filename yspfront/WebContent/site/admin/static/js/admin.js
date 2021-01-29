$(document).ready(function () {

  const domain   = "http://localhost:80/YouShopPretty/webapi";
  const products   = "products";

  $('#sidebarCollapse').on('click', function () {
    $('#sidebar').toggleClass('active');
  });

//   $.ajax({
//     url: `${domain}/${products}`,
//     type: 'get',
//     success: function(response){
//         for(i=0; i < response.length && i<=23; i++){
//             let {id,price, name,imageLink} = response[i];
//             $('#edit_product .container .row').append(`
//                 Hello
//             `)
//         }
//     },
//     error: function(){
//         console.log("error AJAX GET");
//     }
// });

});

$('#product a').on('click', function (e) {
  e.preventDefault();
  //alert("mince");
  $("a").removeClass("active");
//   $(this).addClass('active');
//   $(this).tab('show');
//   $(this).tab('active');

})

