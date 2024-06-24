<%@  include file="/layout/user.jsp" %>

<%@ page import="java.util.List" %>
<%@ page import="com.example.bibliotheque.bean.*" %>
<% List<Livre> listeLivre = (List<Livre>)request.getAttribute("livres"); %>

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
                <h3>Livre a lire sur place</h3>

                <div class="row">

                    <% if(listeLivre != null){ 
                    for(Livre livre : listeLivre){ %>
                        <div class="col-lg-4">
                                <div class="card">
                                    <div class="card-header bg-white" style="display:flex;align-items:center;justify-content:space-between;">
                                        <div class="info" style="width:70%">
                                            <h2 class="card-title" style="margin-top:10px;font-weight:bold;"><%= livre.getTitre() %></h2>
                                            <h6 class="card-subtitle mb-2 text-muted"><%= livre.getAuteur() %></h6>
                                        </div>

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
                                    </div>
                                    
                                    <div class="card-footer">
                                        <button type="button" class="btn mb-1 btn-info float-right button-lecture">Lire</button>
                                    </div>

                                </div>
                        </div>
                    <% } } %>



                </div>
        </div>
    </div>
</div>



<%@ include file="/layout/footer.jsp" %>