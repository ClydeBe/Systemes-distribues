$(document).ready(function () {

  const domain   = "http://localhost:80/YouShopPretty/webapi";
  const products   = "products";

  $('#sidebarCollapse').on('click', function () {
    $('#sidebar').toggleClass('active');
  });


  if(document.cookie.includes('YSPsessionId')){

    let token = document.cookie.split(";").filter(e => e.includes("YSPsessionId"))[0].replace('YSPsessionId=','');
    let user  = JSON.parse(atob(token.split('.')[1]));

    console.log(user);
    
    $("#username").html(`${user.jti}`);
    
  }

});

$('#product a').on('click', function (e) {
  e.preventDefault();
  //alert("mince");
  $("a").removeClass("active");
//   $(this).addClass('active');
//   $(this).tab('show');
//   $(this).tab('active');

})

