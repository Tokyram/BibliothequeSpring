
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Bibliotheque</title>
    
    <link rel="icon" type="image/png" sizes="16x16" href="assets/images/spring.svg">
    <!-- Pignose Calender -->
    <link href="/assets/plugins/pg-calendar/css/pignose.calendar.min.css" rel="stylesheet">
    <!-- Chartist -->
    <link rel="stylesheet" href="/assets/plugins/chartist/css/chartist.min.css">
    <link rel="stylesheet" href="/assets/plugins/chartist-plugin-tooltips/css/chartist-plugin-tooltip.css">
    <!-- Custom Stylesheet -->
    <link href="/assets/css/style.css" rel="stylesheet" asp-append-version="true">

    
</head>
<body>

    <div id="main-wrapper" style="">

        <!--**********************************
            Nav header start
        ***********************************-->
        <div class="nav-header">
            <div class="brand-logo">
                <a href="index.html">
                    <b class="logo-abbr"><img src="assets/images/logo.png" alt=""> </b>
                    <span class="logo-compact"><img src="assets/images/logo-compact.png" alt=""></span>
                    <span class="brand-title">
                        <img src="assets/images/logo-text.png" alt="">
                    </span>
                </a>
            </div>
        </div>
        <!--**********************************
            Nav header end
        ***********************************-->

        <!--**********************************
            Header start
        ***********************************-->
        <div class="header">    
            <div class="header-content clearfix">
                
                <div class="nav-control">
                    <div class="hamburger">
                        <span class="toggle-icon"><i class="icon-menu"></i></span>
                    </div>
                </div>

                <div class="header-right">
                    <ul class="clearfix">
                        
                        <li class="icons dropdown">
                            <div class="user-img c-pointer position-relative"   data-toggle="dropdown">
                                <span class="activity active"></span>
                                <img src="/assets/images/user/1.png" height="40" width="40" alt="">
                            </div>
                            <div class="drop-down dropdown-profile animated fadeIn dropdown-menu">
                                <div class="dropdown-content-body">
                                    <ul>
                                        <li>
                                            <a href="app-profile.html"><i class="icon-user"></i> <span>Profile</span></a>
                                        </li>
                                        <%-- <li>
                                            <a href="javascript:void()">
                                                <i class="icon-envelope-open"></i> <span>Inbox</span> <div class="badge gradient-3 badge-pill gradient-1">3</div>
                                            </a>
                                        </li> --%>
                                        
                                        <hr class="my-2">

                                        <li>
                                            <a href="/logout"><i class="icon-user"></i> <span>Deconnexion</span></a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <!--**********************************
            Header end ti-comment-alt
        ***********************************-->

        <!--**********************************
            Sidebar start
        ***********************************-->
        <div class="nk-sidebar">           
            <div class="nk-nav-scroll">
                <ul class="metismenu" id="menu">
                    <li class="nav-label">Bibliotheque</li>



                    <li class="mega-menu mega-menu-sm">
                        <a class="has-arrow" href="javascript:void()" aria-expanded="false">
                            <i class="fa fa-list"></i><span class="nav-text">Bibliotheque</span>
                        </a>
                        <ul aria-expanded="false">
                            <li><a href="/livre" >Livres</a></li>
                            <li><a href="/lecture">Lecture</a></li>
                            <li><a href="/listeEmprunt">Livres emprunt&eacute;</a></li>        
                        </ul>
                    </li>

                    <hr class="my-2">
                    
                    <li class="mega-menu mega-menu-sm">
                        <a class="has-arrow" href="javascript:void()" aria-expanded="false">
                            <i class="ti-package"></i><span class="nav-text">Recherche</span>
                        </a>
                        <ul aria-expanded="false">
                            <li><a href = "/user_search">Rechercher Livre</a></li>                         
                        </ul>
                    </li>

                </ul>
            </div>
        </div>



        
        <div class="content-body" style="margin-top: 20px;">

                

       
