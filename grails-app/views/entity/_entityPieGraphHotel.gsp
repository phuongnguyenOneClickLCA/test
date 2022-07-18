<%@ page import="org.apache.commons.lang.StringUtils" %>

<asset:javascript src="portfolio_chart.js"/>
<g:if test="${draw1}">

    <div class="pieContainerHotel entityPieChartWrap text-center" style="max-width: 300px; display: inline-block">
        <p class="bold pieGraphHeading" style="text-align: center; padding-top: 5px"></p>
        <canvas id="pieGraphWrapperHotel${entity?.id}" class="pie"></canvas>
        <a href="javascript:" style="margin-top:20px;" onclick="downloadChart(this, 'pieGraphWrapperHotel${entity?.id}', 'pieGraphForHotel${calculationRuleName}');"> <i class="fa fa-download"></i> <g:message code="download_chart"/> </a>


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
                maintainAspectRatio: false

            };

            // PIE
            // PROPERTY TYPE DISTRIBUTION
            // context
            var ctx = document.getElementById("pieGraphWrapperHotel${entity?.id}");
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
    <div class="pieContainerHotel">
        <p style="margin-left: 0px; padding-top: 20px; text-align: center; font-weight: bold"><g:message code="no_entity_chart_found"/></p>
        <div style="width: 250px"><i class="fa fa-ban fa-align-center fa-fulldiv" aria-hidden="true"></i></div>
    </div>

</g:else>
