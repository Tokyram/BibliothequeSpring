<%@  include file="/layout/admin.jsp" %>


<div class="container-fluid">
    <div class="row"> 
        <div class="col-lg-3"></div>
        <div class="col-lg-6">
                    

            
            <div class="card">
                <div class="card-body">
                    <% String error = (String)request.getAttribute("error");
                            if(error != null){ %>
                                <div class="alert alert-danger" style="margin-bottom:10px;"><%= error %></div>
                    <% } %>  

                            
                    <% String success = (String)request.getAttribute("success");
                            if(success != null){ %>
                                <div class="alert alert-success" style="margin-bottom:10px;"><%= success %></div>
                    <% } %>   

                    <div class="basic-form">
                        <h2> Ajouter un modele de livre </h2>

                        <form action="/ajoutModele" method="post" style="margin-top:40px;">
                            
                            <div class="form-group col-lg-12">
                                <label>Titre <span class="text-danger">*</span></label>
                                <input type="text" class="form-control" placeholder="Titre" name="titre">
                            </div>
                            
                            <div class="form-group col-lg-12">
                                <label>Auteur <span class="text-danger">*</span></label>
                                <input type="text" class="form-control" placeholder="Auteur" name="auteur">
                            </div>
                            
                            <div class="col-lg-12">
                                <label>Resume <span class="text-danger">*</span></label>
                                <textarea  class="form-control" placeholder="Resume" name="resume">

                                </textarea>
                            </div>

                            <button type="submit" class="btn btn-primary" style="margin-top:25px;float:right;">Enregistrer Modele</button>                 

                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-lg-3"></div>
    </div>
</div>


<script src="/assets/plugins/common/common.min.js"></script>
<script src="/assets/js/custom.min.js"></script>
<script src="/assets/js/settings.js"></script>
<script src="/assets/js/gleek.js"></script>
<script src="/assets/js/styleSwitcher.js"></script>

<%@ include file="/layout/footer.jsp" %>