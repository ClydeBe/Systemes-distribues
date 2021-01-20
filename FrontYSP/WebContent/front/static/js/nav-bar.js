$(document).ready(function(){
    navBarRender();
})

$('.nav li').click(function(){
  $(this).addClass('active').siblings().removeClass('active');
})

function navBarRender(){

    $("#navigation").append(`
    <nav class="navbar navbar-expand-lg navbar-dark bg-perso">
    <div class="container">
        <a class="navbar-brand" href="/index.html"><i class="fab fa-shopware fa-3x"></i> <span class="logo"> ZMall</span></a>
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
                <a class="nav-link active" aria-current="page" href="index.html">Home</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="/shop/newArrival.html">New Arrivals</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#">Blog</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="underHundred.html">Under 100€</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#">About Us</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="account/contact.html">Contact Us</a>
              </li>
            </ul>
          </div>
        </div>

            <!-- <a href="#"><i class="fas fa-user fa-2x"></i>  Sign in</a>
            <a href="#"><i class="fas fa-shopping-cart fa-2x"><span class="badge rounded-pill bg-danger"> 0</span></i></a>  
            <a href="#"><i class="far fa-heart fa-2x"></i> <span class="badge rounded-pill bg-warning"> 0</span></a> -->
        <ul class="navbar-nav me-auto mb-2 mb-lg-0 connexion">
          <li class="nav-item">
            <a class="nav-link" href="account/login.html"><i class="fas fa-sign-in-alt fa-2x"></i>  Sign in </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="../account/register.html"><i class="fas fa-door-open fa-2x"></i> Resgister</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
    
    
    `);
}