$(document).ready(function () {

    $('#sidebarCollapse').on('click', function () {
        $('#sidebar').toggleClass('active');
    });

});

$('#product a').on('click', function (e) {
  e.preventDefault();
  //alert("mince");
  $("a").removeClass("active");
//   $(this).addClass('active');
//   $(this).tab('show');
//   $(this).tab('active');

})

