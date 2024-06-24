<%@  include file="/layout/admin.jsp" %>

<%@ page import="java.util.List" %>
<%@ page import="com.example.bibliotheque.bean.*" %>
<%  List<Categorie> listeCate = (List<Categorie>) request.getAttribute("categorie");  %>


<script src="/assets/plugins/common/common.min.js"></script>
<script src="/assets/js/custom.min.js"></script>
<script src="/assets/js/settings.js"></script>
<script src="/assets/js/gleek.js"></script>
<script src="/assets/js/styleSwitcher.js"></script>


<div class="col-lg-10" style="margin:auto;">
    <% 
        String error = (String)request.getAttribute("error");
        if(error != null){ %>
        <div class="alert alert-danger"><%= error %></div>
    <% } %>
    
    <div class="card">
        <div class="card-body">
            
                <div class="form-row">
                    <div class="form-group col-lg-4">
                        <label>Titre </label>
                        <input type="text" class="form-control" placeholder="Titre" name="titre">
                    </div>

                    <div class="form-group col-lg-4">
                        <label>Auteur </label>
                        <input type="text" class="form-control" placeholder="Auteur" name="auteur">
                    </div>

                    <div class="form-group col-lg-4">
                        <label>Date de publication </label>
                        <input type="date" class="form-control" name="date">
                    </div>

                </div>

                <div class="form-row" style="column-span:10px;row-span:10px;">
                    <h6 style="font-weight:bold;margin-bottom:20px;">Categorie Livre</h6>

                    <div class="categorie">
                    <% for(Categorie categorie : listeCate){ %>
                        <label class="checkbox-inline mr-4 text-sm" >
                        <input type="checkbox" name="categorie" style="margin-right:15px;" value="<%= categorie.getIdCategorie() %>"><%= categorie.getNomCategorie() %></label>
                    <% } %>
                    </div>
                   
                </div>

                <div class="form-row" style="justify-content:end;">
                    <button type="submit" class="btn btn-primary" style="margin-top:20px;">Rechercher Livre</button>
                </div>
            
        

        </div>
    </div>
</div>


<%-- 
<div class="col-lg-12">
    <div class="card">
        <div class="card-body">

        </div>
    </div>
</div> --%>


<%@  include file="/layout/footer.jsp" %>
