
<!DOCTYPE html>
<html class="h-100" lang="en">

<head>

    <!-- Google Tag Manager -->
    <script>
        (function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
        new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
        j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
        'https://www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
        })(window,document,'script','dataLayer','GTM-PC9SM6JV');
    </script>
    <!-- End Google Tag Manager -->


    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="Bibliotheque Spring">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Bibliotheque</title>
    <link rel="icon" type="image/png" sizes="16x16" href="assets/images/spring.svg">
    <link href="assets/css/style.css" rel="stylesheet">

     <!-- Google tag (gtag.js) -->
     <script async src="https://www.googletagmanager.com/gtag/js?id=G-XSLKWYJ9TE"></script>
     <script>
        window.dataLayer = window.dataLayer || [];
        function gtag(){dataLayer.push(arguments);}
        gtag('js', new Date());

        gtag('config', 'G-XSLKWYJ9TE');
    </script>
</head>

<body class="h-100">
    
     <!-- Google Tag Manager (noscript) -->
     <noscript>
        <iframe src="https://www.googletagmanager.com/ns.html?id=GTM-PC9SM6JV"
        height="0" width="0" style="display:none;visibility:hidden"></iframe>
    </noscript>
        <!-- End Google Tag Manager (noscript) -->
         
    <!--*******************
        Preloader start
    ********************-->
    <%-- <div id="preloader">
        <div class="loader">
            <svg class="circular" viewBox="25 25 50 50">
                <circle class="path" cx="50" cy="50" r="20" fill="none" stroke-width="3" stroke-miterlimit="10" />
            </svg>
        </div>
    </div> --%>
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
                                <a class="text-center" href="index.html"> <h4>Bibliotheque</h4></a>
        
                                <form class="mt-5 mb-5 login-input" action="/user_login" method="GET">
                                    <div class="form-group">
                                        <input type="text" class="form-control" name="identifiant" placeholder="Identifiant">
                                    </div>
                                    <%-- <div class="form-group">
                                        <input type="password" class="form-control" placeholder="Password">
                                    </div> --%>
                                    <button class="btn login-form__btn submit w-100">Sign In</button>
                                </form>
                                <p class="mt-5 login-form__footer">Dont have account? <a href="/inscription" class="text-primary">Sign Up</a> now</p>

                                <% 
                                    String error = (String)request.getAttribute("error");
                                    if(error != null){ %>
                                            <div class="alert alert-danger"><%= error %></div>
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
    <script async src="assets/plugins/common/common.min.js"></script>
    <script async src="assets/js/custom.min.js"></script>
    <script async src="assets/js/settings.js"></script>
    <script async src="assets/js/gleek.js"></script>
    <script async src="assets/js/styleSwitcher.js"></script>
</body>
</html>





