<%@  include file="/layout/user.jsp" %>

<%@ page import="com.example.bibliotheque.bean.*" %>
<%  String idUtilisateur = (String)request.getAttribute("idUtilisateur");  %>
<%  String nom = (String)request.getAttribute("nom");  %>
<%  String prenom = (String)request.getAttribute("prenom");  %>

    
<script src="/assets/plugins/common/common.min.js"></script>
<script src="/assets/js/custom.min.js"></script>
<script src="/assets/js/settings.js"></script>
<script src="/assets/js/gleek.js"></script>
<script src="/assets/js/styleSwitcher.js"></script>

<div class="container-fluid">
    <div class="card">
        <div class="card-body">
            <h3 style="text-alig:center;">Bonjour <%= nom %> &nbsp; <%= prenom %> !! Bienvenue dans la bibliotheque </h3>

                <div class="row" style="margin-top:40px;">
                    <div class="col-lg-4"></div>

                    <div class="col-lg-4">
                        <div class="card gradient-3">
                            <div class="card-body">
                                <h3 class="card-title text-white"></h3>
                                <div class="d-inline-block">
                                    <p class="text-white mb-0">Votre identifiant :</p>
                                    <h1 class="text-white" style="margin-top:20px;"><%= idUtilisateur %></h1>
                                </div>
                                <span class="float-right display-5 opacity-5"><i class="fa fa-users"></i></span>
                            </div>
                        </div>    
                    </div>
                </div>
                        


        </div>
    </div>
</div>

    


<%@ include file="/layout/footer.jsp" %>

