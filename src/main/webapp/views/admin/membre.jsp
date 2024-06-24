<%@  include file="/layout/admin.jsp" %>

<%@ page import="java.util.List" %>
<%@ page import="com.example.bibliotheque.bean.*" %>
<%  List<Utilisateur> listeUtil = (List<Utilisateur>)request.getAttribute("utilsateur");  %>
<%  List<TypeMembre> types = (List<TypeMembre>)request.getAttribute("typeMembre"); %>

<script src="/assets/plugins/common/common.min.js"></script>
<script src="/assets/js/custom.min.js"></script>
<script src="/assets/js/settings.js"></script>
<script src="/assets/js/gleek.js"></script>
<script src="/assets/js/styleSwitcher.js"></script>


<div class="container-fluid">
    <% 
        String error = (String)request.getAttribute("error");
        if(error != null){ %>
        <div class="alert alert-danger"><%= error %></div>
    <% } %>
    
    <div class="col-lg-12">
        <div class="card">
            <div class="card-body">
                <h4>Liste utilisateur</h4>

                    <div class="table-responsive">
                        <table class="table table-striped table-bordered">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Nom</th>
                                    <th>Prenom</th>
                                    <th>Type Utilisateur</th>
                                    <th>Date de naissance</th>
                                    <th>Date Inscription</th>
                                    <th>Adresse</th>
                                </tr>
                            </thead>


                            <tbody>
                            <% if(listeUtil != null){ %>
                                <% for(Utilisateur utilisateur : listeUtil){ %>
                                    <tr>
                                        <td><%= utilisateur.getIdUtilisateur() %></td>
                                        <td><%= utilisateur.getNom() %></td>
                                        <td><%= utilisateur.getPrenom() %></td>
                                        <td><%= utilisateur.getType().getNomType() %></td>
                                        <td><%= utilisateur.getDateNaissance() %></td>
                                        <td><%= utilisateur.getDateInscription() %></td>
                                        <td><%= utilisateur.getAdresse() %></td>
                                    </tr>
                                <% } %>
                            <% } %>
                            </tbody>
                        </table>
                    </div>
            </div>
        </div>
    </div>

    <div class="col-lg-6">
        <div class="card">
            <div class="card-body">
                <h4>Type Membre</h4>

                    <div class="table-responsive">
                        <table class="table table-striped table-bordered">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Nom</th>
                                    <th>Duree Emprunt</th>
                                    <th>Coeeficient de retard</th>
                                </tr>
                            </thead>


                            <tbody>
                            <% if(types != null){ %>
                                <% for(TypeMembre type : types){ %>
                                    <tr>
                                        <td><%= type.getIdType() %></td>
                                        <td><%= type.getNomType() %></td>
                                        <td><%= type.getDureeEmprunt() %></td>
                                        <td><%= type.getCoefficientRetard() %></td>
                                    </tr>
                                <% } %>
                            <% } %>
                            </tbody>
                        </table>
                    </div>
            </div>
        </div>
    </div>


</div>



<%@ include file="/layout/footer.jsp" %>