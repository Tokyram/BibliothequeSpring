<%@  include file="/layout/admin.jsp" %>

<%@ page import="java.util.List" %>
<%@ page import="com.example.bibliotheque.bean.*" %>
<%  List<Emprunt> listeEmprunt = (List<Emprunt>)request.getAttribute("emprunt");  %>
    
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
            <h4>Emprunt en cours</h4>

                <div class="table-responsive">
                    <table class="table table-striped table-bordered">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Nom</th>
                                <th>Prenom</th>
                                <th>Type Utilisateur</th>
                                <th>Date de naissance</th>
                                <th>Date Emprunt</th>
                                <th>Date Retour</th>
                            </tr>
                        </thead>


                        <tbody>
                        <% if(listeEmprunt != null){ %>
                            <% for(Emprunt emprunt : listeEmprunt){ %>
                                <tr>
                                    <td><%= emprunt.getIdEmprunt() %></td>
                                    <td><%= emprunt.getMembre().getNom() %></td>
                                    <td><%= emprunt.getMembre().getPrenom() %></td>
                                    <td><%= emprunt.getMembre().getType().getNomType() %></td>
                                    <td><%= emprunt.getMembre().getDateNaissance() %></td>
                                    <td><%= emprunt.getDateEmprunt() %></td>
                                    <td><%= emprunt.getDateRetour() %></td>
                                    <td><a href="/detail?id=<%= emprunt.getIdEmprunt() %>"><button class="btn btn-primary btn-sm">Voir details</button> </a></td>
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