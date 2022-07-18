<%@ page import="org.apache.commons.lang.StringUtils" %>
<asset:javascript src="portfolio_chart.js"/>
<div id="graphWrapper" class="container section">

    <div id="wrapper" class="container" style="padding-top:10px">

        <div class="inliner benchmarkLegendWrapper">
            <g:if test="${!graphDatasets.equals("[]")}">

                <div class="inliner percentsButton">
                    <sec:ifAnyGranted roles="ROLE_SALES_VIEW">
                        <a class="btn btn-primary pull-right margin-left-5" onclick="$('#adminTable').toggleClass('hidden')" >Show/hide admin's table</a>
                    </sec:ifAnyGranted>
                    <g:link  action="benchmarkGraph" class="btn btn-primary" params="[benchmarkToShow: benchmarkToShow, profileId: userResource?.profileId, entityId: entityId, resourceId: userResource?.resourceId, percentages: showPercentages ? '' : true, resourceSubTypeId: subTypeId,stateIdOfProject:stateIdOfProject, projectCoutryId:  countryOfProject?.resourceId]"><strong><g:message code="show"/> ${showPercentages ? "<i class=\"fa fa-hashtag percentage\" aria-hidden=\"true\"></i>" : "<i class=\"fa fa-percent percentage\" aria-hidden=\"true\"></i>"}</strong></g:link>
                </div>
            </g:if>

            <div id="js-legend" class="chart-legend-benchmark">
            </div>
        </div>
        <g:if test="${!graphDatasets.equals("[]")}">
            <a href="javascript:" class="pull-left" onclick="downloadChart(this, 'myChart');"><i class="fa fa-download"></i> <g:message code="download_chart"/> </a>

            <div style="float:left; margin-left: 10px; margin-right: 10px; width: ${customGraphWidth ? customGraphWidth : 850}px;">
                <canvas id="myChart" style="width: ${customGraphWidth ? customGraphWidth : 850}px; height: 550px;"></canvas>
            </div>
        </g:if>
    </div>

</div>

<script type="text/javascript">
    $(function () {
        $('.appendGraphHeadingsHere').empty().append($('.percentsButton'), $('#js-legend'));
        var data = ${graphDatasets};
        var bars = ${drawBars};
        var showPercentages = ${showPercentages};
        var button = '#drawBarLine';

        if (data.length) {
            var value = '<g:message code="design.graph_bars"/>';
            $(button).attr("value", value);
            $(button).attr("onclick", "drawBars();");
            $(button).show();
            var graphData = {
                labels:${graphLabels},
                datasets:${graphDatasets}
            };
            var options = {
                animation : false,
                scaleBeginAtZero: true,
                segmentShowStroke: false,
                animateRotate: true,
                animateScale: false,
                datasetFill: true,
                responsive: false,
                bezierCurve: true,
                maintainAspectRatio: true,
                scaleOverride: ${showPercentages},
                scaleSteps: 10,
                scaleStepWidth: ${showPercentages ? 10 : scaleStepWidth ? scaleStepWidth : 10},
                scaleLabel: "${" <%= value %>"}${showPercentages ? "%" : ''}",
                scaleStartValue: 0,
                tooltipTemplate: "${"<%= label %> <%= datasetLabel %> - <%= value %>"}${showPercentages ? "%" : ''}",
                multiTooltipTemplate: "${" <%= datasetLabel %> - <%= value %>"}${showPercentages ? "%" : ''}",
                legendTemplate: "${"<ul class=\\\"<%=name.toLowerCase()%>-legend\\\"><br/><% for (var i=0; i<datasets.length; i++){%><li class=\\\"legend-item\\\" ><input type=\\\"checkbox\\\" class=\\\"selectLegend\\\" checked onchange=\\\"updateDataset(\\\$(this), window.chart, '<%=datasets[i].label%>')\\\" style=\\\"display:inline-block;width:14px;height:14px;margin:-1px 4px 0 0;vertical-align:middle;cursor:pointer; margin-bottom:4.8px;\\\"></input><span style=\\\"background-color:<%=datasets[i].strokeColor%>\\\"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>"}"
            };
            var ctx = document.getElementById("myChart").getContext("2d");
            window.chart = new Chart(ctx).Line(graphData, options);
            document.getElementById('js-legend').innerHTML = window.chart.generateLegend();
            window.chart.store = new Array();
        }
    });

    function updateDataset(target, chart, label) {
        var store = chart.store;
        var exists = false;
        for (var i = 0; i < store.length; i++) {
            if (store[i][0] === label) {
                exists = true;
                chart.datasets.push(store.splice(i, 1)[0][1]);
                target.fadeTo("slow", 1);
            }
        }
        if (!exists) {
            for (var i = 0; i < chart.datasets.length; i++) {
                if (chart.datasets[i].label === label) {
                    chart.store.push([label, chart.datasets.splice(i, 1)[0]]);
                    target.fadeTo("slow", 0.33);
                }
            }
        }
        chart.update();
    }
    function deSelectAll() {
        var deSelector = document.getElementsByClassName("selectLegend");
        $(deSelector).each(function (index) {
            if ($(deSelector).attr('checked') == "checked") {
                $(deSelector).prop('checked', false);
                $(deSelector).trigger('change');

            }
            return false;

        })
    }

    $(function () {
        var userResource = '${userResource?.resourceId}';
        if (!userResource) {
            var mean = document.getElementsByClassName("selectLegend");
            for (var i = 0; i < mean.length; i++) {
                if (mean[i].checked && mean[i].outerHTML.indexOf('${g.message(code: 'subTypeBenchmarkGraph_mean_for_benchmark')}') > -1) {
                    mean[i].checked = false;
                    mean[i].onchange();
                }
            }
        }
    });

    $(function () {
        var chart = window.chart;
        var userResource = $('#userResourceIndex').val();
        var quintile = $('#userResourceQuintile').val();
        if (chart && userResource && quintile) {
            var color = quintile === "5" ? "#33a800" : quintile === "4" ? "#76CD02" : quintile === "3" ? "#D5F707" : quintile === "2" ? "#FEB005" : quintile === "1" ? "#D93D00" : '';
            if (color) {
                chart.datasets[0].points[userResource].fillColor = color;
                chart.update();
            }
        }
    });


</script>