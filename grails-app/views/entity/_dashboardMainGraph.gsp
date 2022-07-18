<%@ page import="org.apache.commons.lang.StringUtils" %>

<div id="graphWrapper" class="container section">
    <div>
        <input type="hidden" value="${indicator?.indicatorId}" id="indicatorHidden">
        <input type="hidden" value="${childEntity?.id}" id="childEntityHidden">

    </div>


    <canvas id="myChart" style="width: 650px; height: 350px;"></canvas>
    <div id="js-legend"  class="chart-legend-dashboard"></div>

</div>


<script type="text/javascript">
    $(function () {
        $('#indicatorAndDesign').empty().append("${indicator?.localizedName}  - ${childEntity?.name}");
        var data = ${graphDatasets};
        var noDataForIndicator = false;
        var bars = ${drawBars};
        var button = '#drawBarLine';

        if ($.isEmptyObject(data)) {
            $("#myChart").replaceWith("<div class=\"error\"><span>${message(code: 'graphs.data_datacalculate')} ${indicator?.localizedShortName} ${message(code: 'graphs.data_recalculate')}</span></div>")
        } else {
            var value = '<g:message code="design.graph_bars"/>';
            $(button).attr("value", value);
            $(button).attr("onclick", "drawBars();");

            if (bars) {
                button = '#drawBarLine';
                $(button).hide();
                var barGraphData = {
                    labels:${graphLabels},
                    datasets: ${graphDatasets}
                };
                var optionsBars = {
                    segmentShowStroke: false,
                    animateRotate: true,
                    animateScale: false,
                    datasetFill: true,
                    responsive: false,
                    bezierCurve: true,
                    maintainAspectRatio: true,
                    scaleOverride: true,
                    scaleSteps: 10,
                    scaleStepWidth: 10,
                    scaleStartValue: 0,
                    scaleLabel: "${"<%= value %>%"}",
                    tooltipTemplate: "${"<%= datasetLabel %> - <%= value + ' %' %>"}",
                    multiTooltipTemplate: "${"<%= datasetLabel %> - <%= value + ' %' %>"}",
                    legendTemplate: "${"<ul class=\\\"<%=name.toLowerCase()%>-legend\\\"></br> </br></br> <% for (var i=0; i<datasets.length; i++){%><li class=\\\"legend-item\\\" ><input type=\\\"checkbox\\\" class=\\\"selectLegend\\\" checked onchange=\\\"updateDataset(\\\$(this), window.chart, '<%=datasets[i].label%>')\\\" style=\\\"display:inline-block;width:14px;height:14px;margin:-1px 4px 0 0;vertical-align:middle;cursor:pointer; margin-bottom:4.8px;\\\"></input><span style=\\\"background-color:<%=datasets[i].strokeColor%>\\\"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>"}"
                };
                var canvas = document.getElementById("myChart").getContext("2d");
                window.chart = new Chart(canvas).Bar(barGraphData, optionsBars);
                document.getElementById('js-legend').innerHTML = window.chart.generateLegend();
                window.chart.store = new Array();
            } else {
                $(button).show();
                var graphData = {
                    labels:${graphLabels},
                    datasets:${graphDatasets}
                };
                var options = {
                    segmentShowStroke: false,
                    animateRotate: true,
                    animateScale: false,
                    datasetFill: true,
                    responsive: false,
                    bezierCurve: true,
                    maintainAspectRatio: true,
                    scaleOverride: true,
                    scaleSteps: 10,
                    scaleStepWidth: 10,
                    scaleStartValue: 0,
                    scaleLabel: "${"<%= value %>%"}",
                    tooltipTemplate: "${"<%= datasetLabel %> - <%= value + ' %' %>"}",
                    multiTooltipTemplate: "${"<%= datasetLabel %> - <%= value + ' %' %>"}",
                    legendTemplate: "${"<ul class=\\\"<%=name.toLowerCase()%>-legend\\\"></br> </br></br> <% for (var i=0; i<datasets.length; i++){%><li class=\\\"legend-item\\\" ><input type=\\\"checkbox\\\" class=\\\"selectLegend\\\" checked onchange=\\\"updateDataset(\\\$(this), window.chart, '<%=datasets[i].label%>')\\\" style=\\\"display:inline-block;width:14px;height:14px;margin:-1px 4px 0 0;vertical-align:middle;cursor:pointer; margin-bottom:4.8px;\\\"></input><span style=\\\"background-color:<%=datasets[i].strokeColor%>\\\"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>"}"
                };
                var ctx = document.getElementById("myChart").getContext("2d");
                window.chart = new Chart(ctx).Line(graphData, options);
                document.getElementById('js-legend').innerHTML = window.chart.generateLegend();
                window.chart.store = new Array();
            }
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
    function drawBars() {
        $(document.getElementById("myChart")).replaceWith('<canvas id="myChart" style="width: 650px; height: 400px;"></canvas>');
        var button = '#drawBarLine';
        var value = '<g:message code="design.graph_lines"/>';
        $(button).attr("value", value);
        $(button).attr("onclick", "drawLines();");


        var graphData = {
            labels:${graphLabels},
            datasets:${graphDatasets}
        };
        var options = {
            segmentShowStroke: false,
            animateRotate: true,
            animateScale: false,
            datasetFill: true,
            responsive: false,
            bezierCurve: true,
            maintainAspectRatio: true,
            scaleOverride: true,
            scaleSteps: 10,
            scaleStepWidth: 10,
            scaleStartValue: 0,
            scaleLabel: "${"<%= value %>%"}",
            tooltipTemplate: "${"<%= datasetLabel %> - <%= value + ' %' %>"}",
            multiTooltipTemplate: "${"<%= datasetLabel %> - <%= value + ' %' %>"}",
            legendTemplate: "${"<ul class=\\\"<%=name.toLowerCase()%>-legend\\\"></br> </br></br> <% for (var i=0; i<datasets.length; i++){%><li class=\\\"legend-item\\\" ><input type=\\\"checkbox\\\" class=\\\"selectLegend\\\" checked onchange=\\\"updateDataset(\\\$(this), window.chart, '<%=datasets[i].label%>')\\\" style=\\\"display:inline-block;width:14px;height:14px;margin:-1px 4px 0 0;vertical-align:middle;cursor:pointer; margin-bottom:4.8px;\\\"></input><span style=\\\"background-color:<%=datasets[i].strokeColor%>\\\"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>"}"
        };
        var ctx = document.getElementById("myChart").getContext("2d");
        window.chart = new Chart(ctx).Bar(graphData, options);
        document.getElementById('js-legend').innerHTML = window.chart.generateLegend();
        window.chart.store = new Array();

    }
    function drawLines() {
        $(document.getElementById("myChart")).replaceWith('<canvas id="myChart" style="width: 650px; height: 400px;"></canvas>');
        var button = '#drawBarLine'
        var value = '<g:message code="design.graph_bars"/>'
        $(button).attr("value", value);
        $(button).attr("onclick", "drawBars();");


        var graphData = {
            labels:${graphLabels},
            datasets:${graphDatasets}
        };
        var options = {
            segmentShowStroke: false,
            animateRotate: true,
            animateScale: false,
            datasetFill: true,
            responsive: false,
            bezierCurve: true,
            maintainAspectRatio: true,
            scaleOverride: true,
            scaleSteps: 10,
            scaleStepWidth: 10,
            scaleStartValue: 0,
            scaleLabel: "${"<%= value %>%"}",
            tooltipTemplate: "${"<%= datasetLabel %> - <%= value + ' %' %>"}",
            multiTooltipTemplate: "${"<%= datasetLabel %> - <%= value + ' %' %>"}",
            legendTemplate: "${"<ul class=\\\"<%=name.toLowerCase()%>-legend\\\"></br> </br></br> <% for (var i=0; i<datasets.length; i++){%><li class=\\\"legend-item\\\" ><input type=\\\"checkbox\\\" class=\\\"selectLegend\\\" checked onchange=\\\"updateDataset(\\\$(this), window.chart, '<%=datasets[i].label%>')\\\" style=\\\"display:inline-block;width:14px;height:14px;margin:-1px 4px 0 0;vertical-align:middle;cursor:pointer; margin-bottom:4.8px;\\\"></input><span style=\\\"background-color:<%=datasets[i].strokeColor%>\\\"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>"}"
        };
        var ctx = document.getElementById("myChart").getContext("2d");
        window.chart = new Chart(ctx).Line(graphData, options);
        document.getElementById('js-legend').innerHTML = window.chart.generateLegend();
        window.chart.store = new Array();

    }

</script>