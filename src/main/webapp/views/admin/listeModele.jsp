<%@  include file="/layout/admin.jsp" %>

<%@ page import="java.util.List" %>
<%@ page import="com.example.bibliotheque.bean.*" %>
<%  List<Modele> listeModele = (List<Modele>)request.getAttribute("modele");  %>

<script src="/assets/plugins/common/common.min.js"></script>
<script src="/assets/js/custom.min.js"></script>
<script src="/assets/js/settings.js"></script>
<script src="/assets/js/gleek.js"></script>
<script src="/assets/js/styleSwitcher.js"></script>

<script src="/assets/plugins/tables/js/jquery.dataTables.min.js"></script>
<script src="/assets/plugins/tables/js/datatable-init/datatable-basic.min.js"></script>

<div class="container-fluid">
    
<div class="row">
    <div class = "col-lg-2"></div>
    <div class="col-lg-8">
        <% 
            String error = (String)request.getAttribute("error");
            if(error != null){ %>
            <div class="alert alert-danger"><%= error %></div>
        <% } %>
        <div class="card">
            <div class="card-body">
                <h4>Liste des modeles</h4>

                    <div class="table-responsive">
                        <table class="table table-striped table-bordered">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Livre</th>
                                    <th>Auteur</th>
                                    <th></th>
                                </tr>
                            </thead>


                            <tbody>
                            <% if(listeModele != null){ %>
                                <% for(Modele modele : listeModele){ %>
                                    <tr>
                                        <td><%= modele.getIdModele() %></td>
                                        <td><a href="/detailModele?id=<%= modele.getIdModele() %>" style="text-decoration:none;"><%= modele.getNomModele() %></a></td>
                                        <td><%= modele.getAuteur() %></td>
                                        <td><a href="/livreAjout?id=<%= modele.getIdModele() %>"><button class="btn btn-primary btn-sm">Ajouter Exemplaire</button></a></td>
                                    </tr>
                                <% } %>
                            <% } %>
                            </tbody>
                        </table>
                    </div>
            </div>
        </div>
    </div>
    <div class = "col-lg-2"></div>
    </div>
</div>

<%@  include file="/layout/footer.jsp" %>

