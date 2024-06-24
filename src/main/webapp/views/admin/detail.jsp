<%@  include file="/layout/admin.jsp" %>

<%@ page import="java.util.List" %>
<%@ page import="com.example.bibliotheque.bean.*" %>
<%  List<Livre> listeLivre = (List<Livre>)request.getAttribute("livre");  %>

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
    
    <div class="col-lg-10">
        <div class="card">
            <div class="card-body">
                <h4>Livre emprunte</h4>

                <div class="table-responsive">
                    <table class="table table-striped table-bordered">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Titre</th>
                                <th>Auteur</th>
                                <th>Collection</th>
                                <th>Isbn</th>
                                <th>Numero Cote</th>
                            </tr>
                        </thead>


                        <tbody>
                        <% if(listeLivre != null){ %>
                            <% for(Livre livre : listeLivre){ %>
                                <tr>
                                    <td><%= livre.getIdLivre() %></td>
                                    <td><%= livre.getTitre() %></td>
                                    <td><%= livre.getAuteur() %></td>
                                    <td><%= livre.getLivreCollection() %></td>
                                    <td><%= livre.getIsbn() %></td>
                                    <td><%= livre.getNumeroCote() %></td>
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