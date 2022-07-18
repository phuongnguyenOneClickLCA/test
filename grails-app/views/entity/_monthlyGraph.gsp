<%@ page import="org.apache.commons.lang.StringUtils" %>


<div id="graphWrapper" class="container section">
    <div class="text-center" style="padding-top: 10px;"><h2>${heading} ${message(code: 'result.montlhy_chart')} </h2>
    </div>
    <div id="monthlyGraphWrapper" class="container text-center" style="padding-top:10px">
        <div style="position: relative;float: left; padding-left: 10px; padding-right: 10px; padding-top: 10px; width: 80vw; height: 80vh; max-width: 1100px !important; max-height: 600px !important;">
            <canvas id="myChartMonthly"></canvas>
        </div>
        </div>
    </div>

</div>
<script>
    $(document).ready(function () {
        $('.dropdown-toggle').dropdown();
    });
</script>
<script type="text/javascript">
    $(function () {

        var data = ${graphDatasets};

        if ($.isEmptyObject(data)) {
            $("#myChartMonthly").replaceWith("<div class=\"error\"><span>${message(code: 'graphs.data_datacalculate')} ${indicator?.localizedShortName} ${message(code: 'graphs.data_recalculate')}</span></div>")
        } else {
                var options = {
                    tooltips: {
                        mode: 'index',
                        intersect: false
                    },
                    responsive: true,
                    scales: {
                        xAxes: [{
                            stacked: true
                        }],
                        yAxes: [{
                            stacked: true
                        }]
                    },
                    legend: {
                        display: true,
                        position: 'right'
                    },
                    maintainAspectRatio: false
                };
                var ctx = document.getElementById("myChartMonthly").getContext("2d");
                var mychartMonthly = new ChartV2(ctx, {
                    type: 'bar',
                    data: {
                        labels: ${graphLabels},
                        datasets: ${graphDatasets}
                    },
                    options: options

                });

            }

    });

</script>