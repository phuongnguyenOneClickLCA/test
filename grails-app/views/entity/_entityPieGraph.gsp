<%@ page import="org.apache.commons.lang.StringUtils" %>
<g:set var="indicatorService" bean="indicatorService"/>
<g:set var="property_typesName" value="${indicatorService.getLocalizedRenameReportVisualisation(indicator, "property_types") ?: message(code: "entity_pie_chart.heading")}"/>

<asset:javascript src="portfolio_chart.js"/>
<g:if test="${draw1}">

<div class="pieContainer entityPieChartWrap text-center">
    <p class="bold pieGraphHeading" style="text-align: center; padding-bottom: 20px">${property_typesName}</p>
    <canvas id="property_types${entity?.id}" class="pie"></canvas>
    <a href="javascript:" style="margin-top:15px;display:block " onclick="downloadChart(this, 'property_types${entity?.id}', '${calculationRuleName}');"> <i class="fa fa-download"></i> <g:message code="download_chart"/> </a>

</div>


<script type="text/javascript">
    $(function(){
        var options = {
            responsive: true,
            legend: {
                position: 'right' ,
                onHover: function (e) {
                    e.target.style.cursor = 'pointer';
                }

            },
            tooltips:{
                callbacks: {
                    title: function(tooltipItems, data) {
                        var idx = tooltipItems[0].index;
                        return data.labels[idx];
                    },
                    label: function (tooltipItem, data) {
                        return '';
                    }
                }
            },
            maintainAspectRatio: false

        };

        // PIE
        // PROPERTY TYPE DISTRIBUTION
        // context
        var ctx = document.getElementById("property_types${entity?.id}");
        // data
        var myChart = new ChartV2(ctx, {
            type: 'pie',
            data: {
                labels: ${labels},
                datasets: [
                    {
                        fill: true,
                        backgroundColor: ${backgroundColors},
                        data: ${datasets},
                        borderColor: ${hoverColors}
                    }
                ]
            },
            options: options
        });
    })



    // Property Type Distribution OLD CODE
   /* var propertyTypes = new Chart(ctx).Pie(datasets, options);
    $("#pie_legend").html(propertyTypes.generateLegend());
*/

</script>
</g:if><g:else>
    <div class="pieContainer bold structurePieChartWrap" >
        <p style="margin-left: 30px; padding-top: 20px;"><g:message code="no_entity_chart_found"/></p>
        <div style="width: 300px"><i class="fa fa-ban fa-align-center fa-fulldiv" aria-hidden="true"></i></div>
    </div>

</g:else>
