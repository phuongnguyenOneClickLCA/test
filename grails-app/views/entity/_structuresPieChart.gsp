<%@ page import="org.apache.commons.lang.StringUtils" %>
<g:set var="indicatorService" bean="indicatorService"/>
<g:set var="structuresPieChartCanvasName" value="${indicatorService.getLocalizedRenameReportVisualisation(indicator, "structuresPieChartCanvas") ?: message(code: "structure_pie_chart.heading")}"/>
<asset:javascript src="portfolio_chart.js"/>
<g:if test="${draw}">

<div class="pieContainer structurePieChartWrap text-center">
    <p class="bold" style="text-align: center; padding-bottom: 20px">${structuresPieChartCanvasName}</p>
    <canvas id="structuresPieChartCanvas${childEntity?.id}" class="pie" ></canvas>
    <a href="javascript:" class="text-center" style="margin-top:15px;display: block" onclick="downloadChart(this, 'structuresPieChartCanvas${childEntity?.id}', 'Material use by mass by structure');"> <i class="fa fa-download"></i> <g:message code="download_chart"/> </a>
</div>


<script type="text/javascript">
    $(function(){
        var dataForChart = ${datasets}
        var options = {
            responsive: true,
            legend: {
                display: false,
                position: 'right' ,
                onHover: function (e) {
                    e.target.style.cursor = 'pointer';
                }
            },
            maintainAspectRatio: false,

            scales: {
                yAxes: [{
                    gridLines: {
                        display: false
                    },
                    ticks: {
                        mirror: true,
                        padding: -10,
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
        // PROPERTY TYPE DISTRIBUTION
        // context
        var ctx = document.getElementById("structuresPieChartCanvas${childEntity?.id}");
        // data
        var myChart = new ChartV2(ctx, {
            type: 'horizontalBar',
            data: {
                labels: ${labels},
                datasets: [
                    {
                        fill: false,
                        backgroundColor:'rgba(92, 184, 92, 0.5)',
                        data: ${datasets},
                        borderColor: ${hoverColors}
                    }
                ]
            },
            options: options
        });
    });

</script>
</g:if><g:else>
    <div class="pieContainer bold structurePieChartWrap" >
        <p style="margin-left: 30px; padding-top: 20px;"><g:message code="no_structure_chart_found"/></p>
        <div style="width: 300px"><i class="fa fa-ban fa-align-center fa-fulldiv" aria-hidden="true"></i></div>
    </div>
</g:else>
