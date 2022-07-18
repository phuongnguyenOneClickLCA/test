<%@ page import="org.apache.commons.lang.StringUtils" %>
<g:if test="${draw}">
    <p class="bold resultsPieCentering" style="padding-left: 30px;">${calculationRuleName}</p>
    <canvas id="resultsPieChart${childEntity?.id}" width="440" height="250"  class="pie"></canvas>
    <a href="javascript:" class="pull-right" onclick="downloadChart(this, 'resultsPieChart${childEntity.id}');"> <i class="fa fa-download"></i> <g:message code="download_chart"/> </a>
    <div style="clear:both;"></div>


    <script type="text/javascript">
    $(function () {
        var options = {
            responsive: true,
            legend: {
                position: 'right',
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
            }
        };

        // PIE
        // PROPERTY TYPE DISTRIBUTION
        // context
        var ctx =  document.getElementById("resultsPieChart${childEntity?.id}");
        // data
        // Property Type Distribution
        var myChart = new ChartV2(ctx, {
            type: 'pie',
            data: {
                labels:${labels},
                datasets: [
                    {
                        fill: true,
                        backgroundColor: ${backgroundColors},
                        data: ${datasets},
                        borderColor:	${hoverColors},
                    }
                ]
            },
            options: options
        });
    });


</script>
</g:if><g:else>
    <div class="pieContainer entityshow-pie">
        <canvas width="200px;" id="nodata"></canvas>
            <p  class="bold"><g:message code="pie_not_enough_data"/></p>
        </div>
</g:else>
