<%@  include file="/layout/admin.jsp" %>

<%@ page import="java.util.List" %>
<%@ page import="com.example.bibliotheque.bean.*" %>
<%  Modele modele = (Modele)request.getAttribute("modele");  %>

<script async src="/assets/plugins/common/common.min.js"></script>
<script async src="/assets/js/custom.min.js"></script>
<script async src="/assets/js/settings.js"></script>
<script async src="/assets/js/gleek.js"></script>
<script async src="/assets/js/styleSwitcher.js"></script>

<script async src="/assets/plugins/tables/js/jquery.dataTables.min.js"></script>
<script async src="/assets/plugins/tables/js/datatable-init/datatable-basic.min.js"></script>

<div class="col-lg-12">
    <% 
        String error = (String)request.getAttribute("error");
        if(error != null){ %>
        <div class="alert alert-danger"><%= error %></div>
    <% } %>
    <div class="card">
        <div class="card-body">
            <h3 style="margin-bottom:20px;">Liste des exemplaires de <%= modele.getNomModele() %></h3>

                <div class="table-responsive">
                    <table class="table table-striped table-bordered">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Livre</th>
                                <th>Auteur</th>
                                <th>Edition</th>
                                <th>Collection</th>
                                <th>ISBN</th>
                                <th>Numero Cote</th>
                                <th>Age Limite</th>
                                <th>Nombre de page</th>
                            </tr>
                        </thead>


                        <tbody>
                        <% if(modele.getListeExemplaire() != null){ %>
                            <% for(Livre livre : modele.getListeExemplaire()){ %>
                                <tr>
                                    <td><%= livre.getIdLivre() %></td>
                                    <td><%= livre.getTitre() %></td>
                                    <td><%= livre.getAuteur() %></td>
                                    <td><%= livre.getLivreEdition() %></td>
                                    <td><%= livre.getLivreCollection() %></td>
                                    <td><%= livre.getIsbn() %></td>
                                    <td><%= livre.getNumeroCote() %></td>
                                    <td><%= livre.getAgeLimite() %></td>
                                    <td><%= livre.getNombrePages() %></td>
                                </tr>
                            <% } %>
                        <% } %>
                        </tbody>
                    </table>
                </div>
        </div>
    </div>
</div>

<%@  include file="/layout/footer.jsp" %>




