<%@  include file="/layout/admin.jsp" %>

<%@ page import="java.util.List" %>
<%@ page import="com.example.bibliotheque.bean.*" %>
<%  List<Categorie> listeCate = (List<Categorie>) request.getAttribute("categorie");  %>
<%  List<TypeMembre> listeType = (List<TypeMembre>) request.getAttribute("membre"); %>
<%  String idModele = (String)request.getAttribute("idModele"); %>

<script src="/assets/plugins/common/common.min.js"></script>
<script src="/assets/js/custom.min.js"></script>
<script src="/assets/js/settings.js"></script>
<script src="/assets/js/gleek.js"></script>
<script src="/assets/js/styleSwitcher.js"></script>


<div class="container-fluid col-lg-10">
    <div class="card">
        <div class="card-body">
                
<style>
    .input-file {
        max-width: 190px;
        display: none;
    }

    .labelFile {
        display: flex;
        flex-direction: column;
        justify-content: center;
        width: 100%;
        height: 120px;
        border: 2px dashed #ccc;
        align-items: center;
        text-align: center;
        padding: 5px;
        color: #404040;
        cursor: pointer;
    }

</style>
        
        <% String error = (String)request.getAttribute("error");
                if(error != null){ %>
                    <div class="alert alert-danger" style="margin-bottom:10px;"><%= error %></div>
        <% } %>  

                
        <% String success = (String)request.getAttribute("success");
                if(success != null){ %>
                    <div class="alert alert-success" style="margin-bottom:10px;"><%= success %></div>
        <% } %>   

        <div class="container-fluid">
            <h3 class="card-title" style="margin-bottom:30px;">Ajouter un livre</h3>
            <div class="basic-form">
                <form class="form-valide" action="/add_book" enctype="multipart/form-data" method="post">

                    <div class="form-row">
                        <div class="form-group col-md-12">
                            <label for="file" class="labelFile">
                                <span>
                                    <svg xml:space="preserve"
                                         viewBox="0 0 184.69 184.69"
                                         xmlns:xlink="http://www.w3.org/1999/xlink"
                                         xmlns="http://www.w3.org/2000/svg"
                                         id="Capa_1"
                                         version="1.1"
                                         width="40px"
                                         height="40px">
                                        <g>
                                        <g>
                                        <g>
                                        <path d="M149.968,50.186c-8.017-14.308-23.796-22.515-40.717-19.813
				                            C102.609,16.43,88.713,7.576,73.087,7.576c-22.117,0-40.112,17.994-40.112,40.115c0,0.913,0.036,1.854,0.118,2.834
				                            C14.004,54.875,0,72.11,0,91.959c0,23.456,19.082,42.535,42.538,42.535h33.623v-7.025H42.538
				                            c-19.583,0-35.509-15.929-35.509-35.509c0-17.526,13.084-32.621,30.442-35.105c0.931-0.132,1.768-0.633,2.326-1.392
				                            c0.555-0.755,0.795-1.704,0.644-2.63c-0.297-1.904-0.447-3.582-0.447-5.139c0-18.249,14.852-33.094,33.094-33.094
				                            c13.703,0,25.789,8.26,30.803,21.04c0.63,1.621,2.351,2.534,4.058,2.14c15.425-3.568,29.919,3.883,36.604,17.168
				                            c0.508,1.027,1.503,1.736,2.641,1.897c17.368,2.473,30.481,17.569,30.481,35.112c0,19.58-15.937,35.509-35.52,35.509H97.391
				                            v7.025h44.761c23.459,0,42.538-19.079,42.538-42.535C184.69,71.545,169.884,53.901,149.968,50.186z"
                                                                      style="fill:#010002;"></path>
                                        </g>
                                        <g>
                                        <path d="M108.586,90.201c1.406-1.403,1.406-3.672,0-5.075L88.541,65.078
				                                    c-0.701-0.698-1.614-1.045-2.534-1.045l-0.064,0.011c-0.018,0-0.036-0.011-0.054-0.011c-0.931,0-1.85,0.361-2.534,1.045
				                                    L63.31,85.127c-1.403,1.403-1.403,3.672,0,5.075c1.403,1.406,3.672,1.406,5.075,0L82.296,76.29v97.227
				                                    c0,1.99,1.603,3.597,3.593,3.597c1.979,0,3.59-1.607,3.59-3.597V76.165l14.033,14.036
				                                    C104.91,91.608,107.183,91.608,108.586,90.201z"
                                                                              style="fill:#010002;"></path>
                                              </g>
                                            </g>
                                          </g>
                                    </svg>
                                </span>
                                <p>Ajouter une couverture</p>
                            </label>
                            <input class="input-file" name="file" id="file" type="file" />

                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group col-lg-5">
                            <label>Titre <span class="text-danger">*</span></label>
                            <input type="text" class="form-control" placeholder="Titre" name="titre">
                        </div>

                        <div class="form-group col-lg-5">
                            <label>Auteur <span class="text-danger">*</span></label>
                            <input type="text" class="form-control" placeholder="Auteur" name="auteur">
                        </div>

                        <div class="form-group col-lg-2">
                            <label>Age limite <span class="text-danger">*</span></label>
                            <input type="number" step="1" min="1" class="form-control" name="age">
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group col-lg-5">
                            <label>Edition <span class="text-danger">*</span></label>
                            <input type="text" class="form-control" placeholder="Edition" name="edition">
                        </div>

                        <div class="form-group col-lg-4">
                            <label>Date edition <span class="text-danger">*</span></label>
                            <input type="date" class="form-control" name="date_edition">
                        </div>

                        <div class="form-group col-lg-3">
                            <label>Nombre de pages <span class="text-danger">*</span></label>
                            <input type="number" step="1" min="1" class="form-control" placeholder="Page" name="page">
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group col-lg-4">
                            <label>Collection <span class="text-danger">*</span></label>
                            <input type="text" class="form-control" placeholder="Collection" name="collection">
                        </div>

                        <div class="form-group col-lg-4">
                            <label>Langue <span class="text-danger">*</span></label>
                            <input type="text" class="form-control" placeholder="Langue" name="langue">
                        </div>

                        <div class="form-group col-lg-4">
                            <label>Theme <span class="text-danger">*</span></label>
                            <input type="text" class="form-control" placeholder="Theme" name="theme">
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="col-lg-6" style="column-span:10px;row-span:10px;">
                            <h6 style="font-weight:bold;margin-bottom:20px;">Categorie Livre</h6>

                            <% 
                            if(listeCate != null){ 
                            for(Categorie categorie : listeCate){ %>
                                <label class="checkbox-inline mr-4 text-sm" >
                                <input type="checkbox" name="categorie" style="margin-right:15px;" value="<%= categorie.getIdCategorie() %>"><%= categorie.getNomCategorie() %></label>
                            <% } }%>

                        </div>

                        <div class="col-lg-6 ">
                            <label>Resume <span class="text-danger">*</span></label>
                            <textarea  class="form-control" placeholder="Resume" name="resume">

                            </textarea>
                        </div>
                    </div>

                    <div class="form-row" style="margin-top:40px;">
                        
                        <div class="col-lg-6">
                            <h6 style="font-weight:bold;margin-bottom:20px;">Lecture sur place</h6>

                            <div class="lecture" style="column-span:10px;row-span:10px;">             
                                
                                 <% 
                                 if(listeType != null){ 
                                 for(TypeMembre membre : listeType ){  %>
                                    <label class="checkbox-inline mr-4 text-sm">
                                    <input type="checkbox" name="lecture" style="margin-right:15px;" value="<%= membre.getIdType() %>"><%= membre.getNomType() %></label>
                                <% } }%>
                                
                               
                            </div>   
                            
                        </div>

                        <div class="col-lg-6" style="column-span:10px;row-span:10px;">
                            <h6 style="font-weight:bold;margin-bottom:20px;">Emprunt</h6>

                            <div class="lecture" style="column-span:10px;row-span:10px;">             
                                <% 
                                if(listeType != null){ 
                                for(TypeMembre membre : listeType ){  %>
                                    <label class="checkbox-inline mr-4 text-sm">
                                    <input type="checkbox" name="emprunt" style="margin-right:15px;" value="<%=  membre.getIdType() %>"><%= membre.getNomType() %></label>
                                <% } }%>
                            </div>   
                        </div>

                        <input type="hidden" name="idModele" style="margin-right:15px;" value="<%= idModele %>">

                    
                    </div>



                    <button type="submit" class="btn btn-primary" style="margin-top:25px;float:right;">Enregistrer livre</button>                 

                </form>

            </div>


        </div>




<%@  include file="/layout/footer.jsp" %>