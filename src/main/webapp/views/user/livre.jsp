<%@  include file="/layout/user.jsp" %>

<%@ page import="java.util.List" %>
<%@ page import="com.example.bibliotheque.bean.*" %>
<%  List<Livre> listeLivre = (List<Livre>)request.getAttribute("livres");  %>

<%-- <style>
    input[type="checkbox"] {
        display: none;
    }

    input[type="checkbox"].card-radio:checked + label{
        background-color: limegreen;
        /*border : #4d7cff;*/
        height:75vh;
        border-radius:8px;
    }

    input[type="checkbox"].finition-radio:checked + label{
        background-color: limegreen;
        height:30vh;
    }

</style> --%>

<div class="container-fluid">
    
    <div class="card">
        <div class="card-body">
            <form action="/emprunt" method="get">

                <div class="row" style="margin-bottom:20px;">
                    <div class="col-lg-7">
                        <% 
                            String error = (String)request.getAttribute("error");
                            if(error != null){ %>
                                <div class="alert alert-danger" style="margin-top:20px;"><%= error %></div>
                        <% } %>
                    </div>

                    <div class="col-lg-2"></div>
                    
                    <div class="col-lg-3">
                        <button type="submit" class="btn mb-1 btn-rounded btn-primary" style="padding:10px 35px;float:right;">Emprunter</button>
                    </div>
                </div>

                <div class="row">

                    <% 
                    if(listeLivre != null){ 
                    for(Livre livre : listeLivre){ %>
                        <div class="col-lg-4">
                                <div class="card">
                                    <div class="card-header bg-white" style="display:flex;align-items:center;justify-content:space-between;">
                                        <div class="info" style="width:70%">
                                            <h2 class="card-title" style="margin-top:10px;font-weight:bold;"><%= livre.getTitre() %></h2>
                                            <h6 class="card-subtitle mb-2 text-muted"><%= livre.getAuteur() %></h6>
                                        </div>

                                        <input type="checkbox" name="livre" value="<%= livre.getIdLivre() %>" style="float:right;">
                                    </div>
                                    
                                    <a href="assets/upload/<%= livre.getCouveture() %>" > <img class="img-fluid" src="assets/upload/<%= livre.getCouveture() %>" alt="" style="object-fit:cover;height:50vh;display:flex;margin:auto;"></a>

                                    

                                    <div class="card-body col-lg-12">

                                        <div class="row" style="padding:0px 15px;margin-bottom:20px;display:flex;align-items:center;justify-content:space-between;">
                                            <div class="edition">
                                                <h5 class="card-subtitle" style="font-weigh:bold;"><%= livre.getLivreEdition() %></h5>
                                            </div>
                                            <div class="date_edition">
                                                <h6 class="card-subtitle" style="font-weigh:bold;"><%= livre.getDateEdition() %></h6>
                                            </div>
                                        </div>

                                        <p class="card-text" style="height: 20vh;text-overflow:ellipsis;font-size:12px;overflow:hidden;"><%= livre.getLivreResume() %></p>
                                        
                                        <div class="row" style="margin-top:15px;padding:0px 10px;column-gap:10px;row-gap:10px;">
                                            <% for(Categorie cate : livre.getCategorieLivre()){ %>
                                                <span class="badge badge-success" style="padding:8px 12px;font-size:12px;color:white;"><%= cate.getNomCategorie() %></span> 
                                            <% } %>
                                            <%-- <span class="badge badge-success" style="padding:8px 12px;font-size:12px;color:white;">Roman</span>
                                            <span class="badge badge-success" style="padding:8px 12px;font-size:12px;color:white;">Passion</span>    --%>
                                        </div>
                                    </div>
                                    
                                    <div class="card-footer">
                                        <button type="button" class="btn mb-1 btn-info float-right button-lecture"><a href="/lecture_place?id=<%= livre.getIdLivre() %>" style="text-decoration:none;color:white;">Lire sur place</a></button>
                                    </div>

                                </div>
                        </div>
                    <% } }%>



                </div>
            </form>
        </div>
    </div>
</div>


<script src="/assets/plugins/common/common.min.js"></script>
<script src="/assets/js/custom.min.js"></script>
<script src="/assets/js/settings.js"></script>
<script src="/assets/js/gleek.js"></script>
<script src="/assets/js/styleSwitcher.js"></script>

<%@ include file="/layout/footer.jsp" %>