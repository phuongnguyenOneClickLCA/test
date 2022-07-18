<%@ page import="org.apache.commons.lang.StringUtils" %>
<asset:javascript src="portfolio_chart.js"/>

<div id="graphWrapperOperating" class="container section">

    <div class="text-center" style="padding-top: 10px;"><h2>${indicator.localizedName} <g:if test="${denominator}">${denominator.localizedName ? denominator.localizedName?.toLowerCase() : denominator.label ?: denominator.denominatorId}</g:if></h2></div>
    <input type="hidden" id="indicatorIdField" value="${indicator?.indicatorId}">
    <div id="wrapperOperating" class="container">
        <div style="position: relative; float:left; padding-left: 10px; padding-right: 10px; padding-top: 10px; width: 80vw; height: 80vh ; max-width: 1100px !important; max-height: 500px !important;" class="text-center">
            <canvas id="myChartOperating"></canvas>
            <a href="javascript:" style="margin-top:20px;" onclick="downloadChart(this, 'myChartOperating', 'graphByOperatingIndicator');"> <i class="fa fa-download"></i> <g:message code="download_chart"/> </a>

        </div>

    </div>




</div>

<script type="text/javascript">
    $(function () {
        <g:if test="${indicator.denominatorListAsDenominatorObjects && indicator.requireMonthly}">
        $('#denomsOperating').removeClass('hidden');
        $('#operatingDenominatorDropdown').empty().append('      <g:each in="${indicator.denominatorListAsDenominatorObjects?.findAll({it.requireMonthly})}" var="denom">\n' +
            '            <li><a href="javascript:" onclick="drawGraphOperating(\'${entity.id}\',\'${indicator.indicatorId}\', \'${indicator.requireMonthly}\', \'${denom.denominatorId}\');">${denom.localizedName ? denom.localizedName : denom.label ?: denom.denominatorId}</a></li>\n' +
            '        </g:each>');
        </g:if>
        var data = ${graphDatasets};
        var button = '#drawBarLineOperating';
        var isMonthly= ${isMonthly ? isMonthly : 'false'};

        if (isMonthly) {
            if ($.isEmptyObject(data)) {
                $("#myChartOperating").replaceWith("<div class=\"error\"><span>${message(code: 'graphs.data_datacalculate')} ${indicator?.localizedShortName} ${message(code: 'graphs.data_recalculate')}</span></div>")
            } else {
                 button = '#drawBarLineOperating';
                    $(button).hide();
                    var options = {
                        legend: {
                            display: true,
                            position: 'right'
                        },
                        tooltips: {
                            mode: 'index',
                            intersect: false,
                        },
                        responsive: true,
                        maintainAspectRatio: false,
                        scales: {
                            yAxes: [{
                                gridLines: {
                                    display: true
                                }
                            }],
                            xAxes: [{
                                gridLines: {
                                    display: false

                                },
                                ticks: {
                                    fontStyle: 'bold',
                                    fontColor: 'black',
                                    autoSkip: false
                                }
                            }]
                        }
                    };
                    var ctx = document.getElementById("myChartOperating").getContext("2d");
                    var mychart= new ChartV2(ctx, {
                        type: 'bar',
                        data: {
                            labels: ${graphLabels},
                            datasets: ${graphDatasets}
                        },
                        options: options
                    });
            }
        }else {
            if ($.isEmptyObject(data)) {
                $("#myChartOperating").replaceWith("<div class=\"error\"><span>${message(code: 'graphs.data_datacalculate')} ${indicator?.localizedShortName} ${message(code: 'graphs.data_recalculate')}</span></div>")
            } else {
                button = '#drawBarLineOperating';
                $(button).hide();
                var options = {
                    tooltips: {
                        mode: 'index',
                        intersect: false,
                    },
                    responsive: true,
                    maintainAspectRatio: false,
                    scales: {
                        yAxes: [{
                            gridLines: {
                                display: true
                            },
                            ticks: {
                                callback: function(value, index, values){
                                    return value + "%";
                                },
                                min: 0,
                                max: 100,
                                stepSize: 10

                            }
                        }],
                        xAxes: [{
                            gridLines: {
                                display: false

                            },
                            ticks: {
                                fontStyle: 'bold',
                                fontColor: 'black',
                                autoSkip: false
                            }
                        }]
                    }
                };
                var ctx = document.getElementById("myChartOperating").getContext("2d");
                var mychart= new ChartV2(ctx, {
                    type: 'bar',
                    data: {
                        labels: ${graphLabels},
                        datasets: ${graphDatasets},
                    },
                    options: options
                });
            }
        }

    });

</script>