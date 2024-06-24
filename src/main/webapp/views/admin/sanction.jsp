<%@  include file="/layout/admin.jsp" %>

<%@ page import="java.util.List" %>
<%@ page import="com.example.bibliotheque.bean.*" %>
<%  List<Sanction> listeSanction = (List<Sanction>)request.getAttribute("sanction");  %>

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
            <h4>Liste sanction</h4>      

                <div class="table-responsive">
                    <table class="table table-striped table-bordered">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Nom</th>
                                <th>Prenom</th>
                                <th>Type Utilisateur</th>
                                <th>Debut Sanction</th>
                                <th>Fin Sanction</th>
                            </tr>
                        </thead>


                        <tbody>
                        <% if(listeSanction != null){ %>
                            <% for(Sanction sanction : listeSanction){ %>
                               <tr>
                                    <td><%= sanction.getIdSanction() %></td>
                                    <td><%= sanction.getUtilisateur().getNom() %></td>
                                    <td><%= sanction.getUtilisateur().getPrenom() %></td>
                                    <td><%= sanction.getUtilisateur().getType().getNomType() %></td>
                                    <td><%= sanction.getDebutSanction() %></td>
                                    <td><%= sanction.getFinSanction() %></td>
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