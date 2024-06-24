<%@  include file="/layout/admin.jsp" %>

<%@ page import="java.util.List" %>
<%@ page import="com.example.bibliotheque.bean.*" %>
<%  List<EmpruntNonRendu> listeEmprunt = (List<EmpruntNonRendu>)request.getAttribute("emprunt");  %>


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
    
    <div class="card">
        <div class="card-body">
            <h4>Emprunt non rendu</h4>      

                <div class="table-responsive">
                    <table class="table table-striped table-bordered">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Nom</th>
                                <th>Prenom</th>
                                <th>Type Utilisateur</th>
                                <th>Date de naissance</th>
                                <th>Livre</th>
                                <th>Date Emprunt</th>
                                <th>Date Retour</th>
                            </tr>
                        </thead>


                        <tbody>
                        <% if(listeEmprunt != null){ %>
                            <% for(EmpruntNonRendu emprunt : listeEmprunt){ %>
                                <tr>
                                    <td><%= emprunt.getEmprunt().getIdEmprunt() %></td>
                                    <td><%= emprunt.getUtilisateur().getNom() %></td>
                                    <td><%= emprunt.getUtilisateur().getPrenom() %></td>
                                    <td><%= emprunt.getUtilisateur().getType().getNomType() %></td>
                                    <td><%= emprunt.getUtilisateur().getDateNaissance() %></td>
                                    <td><%= emprunt.getLivre().getTitre() %></td>
                                    <td><%= emprunt.getEmprunt().getDateEmprunt() %></td>
                                    <td><%= emprunt.getEmprunt().getDateRetour() %></td>
                                </tr>
                            <% } %>
                        <% } %>
                        </tbody>
                    </table>
                </div>
        </div>
    </div>
</div>


<%@ include file="/layout/footer.jsp" %>