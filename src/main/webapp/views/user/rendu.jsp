<%@  include file="/layout/user.jsp" %>

<%@ page import="java.util.List" %>
<%@ page import="com.example.bibliotheque.bean.*" %>
<% List<Livre> listeLivre = (List<Livre>)request.getAttribute("rendu"); %>
<% String idEmprunt = (String)request.getAttribute("emprunt"); %>

    
<script src="/assets/plugins/common/common.min.js"></script>
<script src="/assets/js/custom.min.js"></script>
<script src="/assets/js/settings.js"></script>
<script src="/assets/js/gleek.js"></script>
<script src="/assets/js/styleSwitcher.js"></script>

<div class="container-fluid col-lg-12">
    <div class="col-lg-2"></div>

    <div class="col-lg-8" style="margin:auto;">
        <form action="/rendre" method="post">
            <div class="card">
                <div class="card-body">
                    <input type = "hidden" name="emprunt" value="<%= idEmprunt %>">
                    
                    <h2>Rendu livres</h2> 

                    <div class="row" style="margin-top:20px;margin-bottom:25px;padding:0px 10px;column-gap:15px;">
                        <% 
                        if(listeLivre != null){ 
                        for(var livre : listeLivre){ %> 
                            <span class="badge badge-success" style="padding:11px 16px;font-size:13px;color:white;"><%= livre.getTitre() %></span> 
                        <% }} %>
                    </div>

                    <div class="form-group">
                        <label style="color:black;">Date Rendu</label>
                        <input type="date" name="date" class="form-control">
                    </div>

                    <div class="button" style="display:flex;align-items:center;justify-content:space-between;margin-top:15px;">
                        <button class="btn btn-danger" style="padding:10px 25px;float:left;"><a style="text-decoration:none;color:white" href="/livre">Retour</a></button>
                        <button type="submit" class="btn btn-info" style="padding:10px 25px;float:right;">Rendre Livre</button>
                    </div>
                </div>
            </div>
        </form>
        <% 
            String error = (String)request.getAttribute("error");
            if(error != null){ %>
                <div class="alert alert-danger" style="margin-top:20px;"><%= error %></div>
        <% } %>
    </div>

    <div class="col-lg-2"></div>
</div>

<%@ include file="/layout/footer.jsp" %>