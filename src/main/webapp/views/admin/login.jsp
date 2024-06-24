
<!DOCTYPE html>
<html class="h-100" lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Bibliotheque</title>
    <link rel="icon" type="image/png" sizes="16x16" href="assets/images/spring.svg">
    <link href="assets/css/style.css" rel="stylesheet">
</head>

<body class="h-100">
    
    <!--*******************
        Preloader start
    ********************-->
    <div id="preloader">
        <div class="loader">
            <svg class="circular" viewBox="25 25 50 50">
                <circle class="path" cx="50" cy="50" r="20" fill="none" stroke-width="3" stroke-miterlimit="10" />
            </svg>
        </div>
    </div>
    <!--*******************
        Preloader end
    ********************-->

    <div class="login-form-bg h-100">
        <div class="container h-100">
            <div class="row justify-content-center h-100">
                <div class="col-xl-6">
                    <div class="form-input-content">
                        <div class="card login-form mb-0">
                            <div class="card-body pt-5">
                                <a class="text-center" href="index.html"> <h4>Bibliotheque Admin</h4></a>
        
                                <form class="mt-5 mb-5 login-input" action="/admin_login" method="POST">
                                    <div class="form-group">
                                        <input type="text" class="form-control" placeholder="Nom" name="nom">
                                    </div>
                                    <div class="form-group">
                                        <input type="text" class="form-control" name="identifiant" placeholder="Identifiant">
                                    </div>
   
                                    <button class="btn login-form__btn submit w-100">Log In</button>
                                </form>

                                <% 
                                    String error = (String)request.getAttribute("error");
                                    if(error != null){ %>
                                            <div class="alert alert-danger" style="margin-top:20px;"><%= error %></div>
                                <% } %>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    

    

    <!--**********************************
        Scripts
    ***********************************-->
    <script src="assets/plugins/common/common.min.js"></script>
    <script src="assets/js/custom.min.js"></script>
    <script src="assets/js/settings.js"></script>
    <script src="assets/js/gleek.js"></script>
    <script src="assets/js/styleSwitcher.js"></script>
</body>
</html>





