$(document).ready(function(){
    navBarRender();
    recupererCookie("JWTsessionId");
})


function recupererCookie(nom) {
  nom = nom + "=";
  var liste = document.cookie.split(';');
  for (var i = 0; i < liste.length; i++) {
      var c = liste[i];
      while (c.charAt(0) == ' ') c = c.substring(1, c.length);
      if (c.indexOf(nom) == 0) return c.substring(nom.length, c.length);
  }
  return null;
}

function navBarRender(){
  var route   = "yspfront/site"; 
  var shop    = "shop";
  var account = "account";

    $("#navigation").append(`
    <nav class="navbar navbar-expand-lg navbar-dark bg-perso">
    <div class="container">
        <a class="navbar-brand" href=/${route}/${shop}/index.html><i class="fab fa-shopware fa-3x"></i> <span class="logo"> ZMall</span></a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">

        <div class="row">
          <div class="col-lg-12">
            <form class="d-flex search input-group me-2">
              <select class="form-select category-drop" aria-label="Default select example">
                 <option selected>--All Categories</option>
                 <option value="1">One</option>
                 <option value="2">Two</option>
                 <option value="3">Three</option>
              </select>
            <input class="form-control" id="search" type="search" placeholder="Search" aria-label="Search">
            <button class="btn btn-outline-success" type="submit">Search</button>
          </form>
          </div>
          <div class="col-lg-12">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
              <li class="nav-item first">
                <a class="nav-link active" aria-current="page" href=/${route}/${shop}/index.html>Home</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href=/${route}/${shop}/newArrival.html>New Arrivals</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#">Blog</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href=/${route}/${shop}/underHundred.html>Under 100€</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#">About Us</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href=/${route}/${account}/contact.html>Contact Us</a>
              </li>
            </ul>
          </div>
        </div>

          <!-- <a href="#"><i class="fas fa-user fa-2x"></i>  Sign in</a>
          <a href="#"><i class="fas fa-shopping-cart fa-2x"><span class="badge rounded-pill bg-danger"> 0</span></i></a>  
          <a href="#"><i class="far fa-heart fa-2x"></i> <span class="badge rounded-pill bg-warning"> 0</span></a> -->
          <ul class="navbar-nav me-auto mb-2 mb-lg-0 connexion">
          <span id="user-activity"></span>
        </ul>
      </div>
    </div>
  </nav>
    
    
    `);

  console.log(document.cookie.includes('YSPsessionId'));

  if(document.cookie.includes('YSPsessionId')){
      
      $("#user-activity").html(`     
      <li class="nav-item d-inline-block">
        <a class="nav-link" href=/${route}/${account}/login.html><i class="fas fa-shopping-cart"></i>  Cart </a>
      </li>
      <li class="nav-item d-inline-block">
        <a class="nav-link" href=/${route}/${account}/register.html><i class="far fa-heart"></i> WishList</a>
      </li>
      <li class="nav-item d-inline-block">
        <a class="nav-link" href=/${route}/${account}/register.html><i class="fas fa-users"></i></a>
      </li>
  `);

  }
  else{
    $("#user-activity").html(`     
    <li class="nav-item d-inline-block">
      <a class="nav-link" href=/${route}/${account}/login.html><i class="fas fa-sign-in-alt fa-2x"></i>  Sign in </a>
    </li>
    <li class="nav-item d-inline-block">
      <a class="nav-link" href=/${route}/${account}/register.html><i class="fas fa-door-open fa-2x"></i> Resgister</a>
    </li>
  `);

  }

}