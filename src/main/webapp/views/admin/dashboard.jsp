<%@  include file="/layout/admin.jsp" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.bibliotheque.bean.*" %>
<%  List<String> annee = (List<String>)request.getAttribute("annee");  %>
<%  int utilisateur = (int)request.getAttribute("utilisateur");  %>
<%  int livre = (int)request.getAttribute("livre"); %>

<script src="/assets/plugins/common/common.min.js"></script>
<script src="/assets/js/custom.min.js"></script>
<script src="/assets/js/settings.js"></script>
<script src="/assets/js/gleek.js"></script>
<script src="/assets/js/styleSwitcher.js"></script>

<script src="/assets/plugins/chart.js/Chart.bundle.min.js"></script>
<script src="/assets/js/plugins-init/chartjs-init.js"></script>

<div class="container-fluid">
    <% 
        String error = (String)request.getAttribute("error");
        if(error != null){ %>
        <div class="alert alert-danger"><%= error %></div>
    <% } %>
    <div class="row">
        <div class="col-lg-1"></div>
        <div class="col-lg-6">
            <div class="card">
                <div class="card-body">
                    <h4 class="card-title">Emprunt Livre</h4>
                    <canvas id="pieChart" width="300" height="150"></canvas>

                    <script>
                        $(document).ready(function () {
                            $.ajax({
                                url: '/getStat',
                                type: 'GET',
                                success: function (data) {

                                    console.log(data);

                                    var labels = [];
                                    var values = [];

                                    data.forEach(function (item) {
                                        labels.push(item[1]);
                                        values.push(parseInt(item[2]));
                                    });
                                    var ctx = document.getElementById("pieChart");

                                    var myChart = new Chart(ctx, {
                                        type: 'pie',
                                        data: {
                                            datasets: [{
                                                data: values,
                                                backgroundColor: [
                                                    "#42B3D5",
                                                    "#DCEDC8",
                                                    "#1E3388",
                                                    "#4E58E6"
                                                ],
                                                hoverBackgroundColor: [
                                                    "rgba(66, 179, 213, 0.7)",
                                                    "rgba(220, 237, 200, 0.9)",
                                                    "rgba(30, 51, 136, 0.9)",
                                                    "rgba(70 , 45 , 200 , 0.6)"
                                                ]
                                            }],
                                            labels: labels
                                        },
                                        options: {
                                            responsive: true,
                                        }
                                    });
                                }
                            });
                        });
                    </script>
                </div>
            </div>
        </div>

        <div class="col-lg-4">
            <div class="row">
                    <div class="col-lg-12">
                        <div class="card gradient-3">
                            <div class="card-body">
                                <h3 class="card-title text-white">Utilisateur</h3>
                                <div class="d-inline-block">
                                    <h2 class="text-white"><%= utilisateur %></h2>
                                    <p class="text-white mb-0">Jan - Jun 2024</p>
                                </div>
                                <span class="float-right display-5 opacity-5"><i class="fa fa-users"></i></span>
                            </div>
                        </div>
                    </div>
            </div>

            <div class="row">
                <div class="col-lg-12">
                        <div class="card gradient-4">
                            <div class="card-body">
                                <h3 class="card-title text-white">Nombre de livres</h3>
                                <div class="d-inline-block">
                                    <h2 class="text-white"><%= livre %></h2>
                                    <p class="text-white mb-0">Jan - Jun 2024</p>
                                </div>
                                <span class="float-right display-5 opacity-5"><i class="fa fa-heart"></i></span>
                            </div>
                        </div>
                </div>
            </div>
        </div>
    </div>
   

   <div class="row">
        <div class="col-lg-1"></div>
        <div class="col-lg-10">
            <div class="card">
                <div class="card-body">
                        <div class="form-group">
                            <label>Annee  <span class="text-danger">*</span></label>
                            <select class="form-control" name="annee" id="packSelect">
                                <option></option>

                                    <% if(annee != null){ %>
                                        <%  for(String year : annee){ %>
                                            <option value="<%= year %>"><%= year %></option>
                                        <% } %>
                                    <% } %>
                        
                            </select>
                        </div>

                        <canvas id="singelBarChart" width="500" height="190"></canvas>

                         <script>
                                $(document).ready(function () {

                                    var defaultLabels = [];
                                    var defaultData = [];

                                    initializeChart(defaultLabels, defaultData);

                                    $('#packSelect').change(function () {
                                        var year = $(this).val();
                                        $.ajax({
                                                url: '/getEmpruntStat',
                                            type: 'GET',
                                            data: { year : year },
                                            success: function (data) {
                                                updateChart(data);
                                            }
                                        });
                                    });

                                    function initializeChart(data) {
                                        var ctx = document.getElementById('singelBarChart').getContext('2d');

                                        window.chart = new Chart(ctx, {
                                            type: 'bar',
                                            data: {
                                                labels: [],
                                                datasets: [{
                                                    label: 'Emprunt par mois',
                                                    data: data,
                                                    backgroundColor: 'rgba(30, 51, 136, 0.9)',
                                                    borderColor: 'rgba(30, 51, 136)',
                                                    borderWidth: 1
                                                }]
                                            },
                                            options: {
                                                scales: {
                                                    yAxes: [{
                                                        ticks: {
                                                            beginAtZero: true
                                                        }
                                                    }]
                                                }
                                            }
                                        });
                                    }

                                    function updateChart(data) {
                                        var labels = [];
                                        var chartData = [];

                                        data.forEach(function (item) {
                                            labels.push(item[0]);
                                            chartData.push(parseInt(item[1]));
                                        });

                                        // Mettre à jour le graphique avec les nouvelles données
                                        chart.data.labels = labels;
                                        chart.data.datasets[0].data = chartData;
                                        chart.update();
                                    }
                                });
                        </script>

                </div>
            </div>
        </div>

   </div>


</div>

    


<%@ include file="/layout/footer.jsp" %>