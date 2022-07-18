<%@ page import="org.apache.commons.lang.StringUtils" %>
<g:if test="${percentageScores}">
<div class="pieContainer entityshow-pie">
    <p class="bold"><g:message code="results.contributing_impacts"/> <g:message code="results.material_types"/> (${calculationRuleName}) - ${resultCategory} </p>
    <canvas id="resourceTypeImpactBarWrapper${childEntity?.id}" class="bar"></canvas>
    <a href="javascript:" class="text-center" onclick="downloadChart(this, 'resourceTypeImpactBarWrapper${childEntity?.id}', 'Most contributing material types (${calculationRuleName}) - ${resultCategory}');"> <i class="fa fa-download"></i> <g:message code="download_chart"/> </a>


</div>
    <script type="text/javascript">
        $(function(){
            var dataForChart = [];
            var labelForChart = [];
            <g:each in="${percentageScores}">
            dataForChart.push(${it.value});
            labelForChart.push("${it.key}");
            </g:each>

            var options = {
                responsive: true,
                legend: {
                    display: false,
                    position: 'right' ,
                    onHover: function (e) {
                        e.target.style.cursor = 'pointer';
                    }
                },
                maintainAspectRatio: true,

                scales: {
                    yAxes: [{
                        gridLines: {
                            display: false
                        },
                        ticks: {
                            mirror: true,
                            padding: -10,
                            fontStyle: 'bold',
                            fontColor: 'black',
                            callback: function(value, index, values){
                                return value + " - " + dataForChart[index].toString() + "%"
                            }
                        }
                    }],
                    xAxes: [{
                        gridLines: {

                            lineWidth: 0.5
                        },
                        ticks: {
                            callback: function(value, index, values){
                                return value + "%";
                            },
                            min: 0,
                            suggestedMax: 60,
                            stepSize: 20
                        }
                    }]
                }
            }
            // PIE
            // P}ROPERTY TYPE DISTRIBUTION
            // context
            var ctx = document.getElementById("resourceTypeImpactBarWrapper${childEntity?.id}");
            // data
            var myChart = new ChartV2(ctx, {
                type: 'horizontalBar',
                data: {
                    labels: labelForChart,
                    datasets:[{
                        label: "progress",
                        data:  dataForChart,
                        backgroundColor: 'rgba(92, 184, 92, 0.5)',
                        fill: false
                    }]

                },
                options: options
            });
        });

    </script>
</g:if>