<%@ page import="org.apache.commons.lang.StringUtils" %>
<div id="graphWrapper" class="container section">
    <div class="" style="padding-top: 10px;"><h2>${heading}</h2></div>
    <a href="javascript:" class="bold" onclick="$('#graphContentRegular').fadeOut(1).addClass('hidden'); $('#table${indicator.indicatorId}').fadeIn(3000).removeClass('hidden');"> ${message(code: 'result.monthly_table')}  <i class="fa fa-table"></i></a>


    <div id="regularGraphWrapper" class="container" style="padding-top:10px">
        <div style="position: relative; float:left; padding-left: 10px; padding-right: 10px; padding-top: 10px; width: 80vw; height: 60vh ;" class="pull-left">

            <canvas id="myChartRegular" style="width: 850px; height: 400px;"></canvas>
        </div>
        <div class="btn-group pull-left">
            <button id="curvedLineRegular" class="btn btn-small pull-right disabled" style="font-weight: bold" onclick="$(this).attr('disabled',true).addClass('disabled'); $(this).addClass('cursorOff');changeLineCurveRegular(); unDisableStraightRegular();"><g:message code="result.graph_line_curved"/> </button>
            <button id="straightLineRegular" class="btn btn-small primary pull-right" style="font-weight: bold" onclick="changeLineStraightRegular();$(this).attr('disabled',true).addClass('disabled'); $(this).addClass('cursorOff');unDisableCurvedRegular();"><g:message code="result.graph_line_straight"/></button>
            <button id="toggleAllRegular"  class="btn btn-small primary pull-right" style="font-weight: bold" onclick="deSelectAllRegular();" ><g:message code="result.graph_toggle"/></button>
        </div>
        <div id="js-legend-regular" class="chart-legend-design">
        </div>
    </div>

</div>

<script type="text/javascript">
    $(function () {
        var data = ${graphDatasets};

        if ($.isEmptyObject(data)) {
            $("#myChartRegular").replaceWith("<div class=\"error\"><span>${message(code: 'graphs.data_datacalculate')} ${indicator?.localizedShortName} ${message(code: 'graphs.data_recalculate')}</span></div>")
        } else {
            var value = '<g:message code="design.graph_bars"/>';
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
                scaleLabel: "${"<%= value %>"}",
                tooltipTemplate: "${"<%= datasetLabel %> - <%= value %>"}",
                multiTooltipTemplate: "${"<%= datasetLabel %> - <%= value%>"}",
                legendTemplate: "${"<ul class=\\\"<%=name.toLowerCase()%>-legend\\\"></br> </br></br> <% for (var i=0; i<datasets.length; i++){%><li class=\\\"legend-item\\\" ><input type=\\\"checkbox\\\" class=\\\"selectLegendRegular\\\" checked onchange=\\\"updateDataset(\\\$(this), window.chartRegular, '<%=datasets[i].label%>')\\\" style=\\\"display:inline-block;width:14px;height:14px;margin:-1px 4px 0 0;vertical-align:middle;cursor:pointer; margin-bottom:4.8px;\\\"></input><span style=\\\"background-color:<%=datasets[i].strokeColor%>\\\"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>"}"
            };
            var ctx = document.getElementById("myChartRegular").getContext("2d");
            window.chartRegular = new Chart(ctx).Line(graphData, options);
            document.getElementById('js-legend-regular').innerHTML = window.chartRegular.generateLegend();
            window.chartRegular.store = new Array();
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

    function changeLineStraightRegular() {
        $(document.getElementById("myChartRegular")).replaceWith('<canvas id="myChartRegular" style="width: 850px; height: 400px;" width="850" height="550"></canvas>');
        var data = {
            labels: ${graphLabels},
            datasets: ${graphDatasets}
        };
        var options = {
            segmentShowStroke: false,
            animateRotate: true,
            animateScale: false,
            datasetFill: true,
            responsive: false,
            bezierCurve: false,
            maintainAspectRatio: false,
            scaleLabel: "${"<%= value %>"}",
            tooltipTemplate: "${"<%= datasetLabel %> - <%= value %>"}",
            multiTooltipTemplate: "${"<%= datasetLabel %> - <%= value %>"}",
            legendTemplate: "${"<ul class=\\\"<%=name.toLowerCase()%>-legend\\\"></br> </br> </br><% for (var i=0; i<datasets.length; i++){%><li class=\\\"legend-item\\\" ><input type=\\\"checkbox\\\" class=\\\"selectLegendRegular\\\" checked onchange=\\\"updateDataset(\\\$(this), window.chartRegular, '<%=datasets[i].label%>')\\\" style=\\\"display:inline-block;width:14px;height:14px;margin:-1px 4px 0 0;vertical-align:middle;cursor:pointer; margin-bottom:4.8px;\\\"></input><span style=\\\"background-color:<%=datasets[i].strokeColor%>\\\"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>"}"


        };
        var ctx = document.getElementById("myChartRegular").getContext("2d");
        window.chartRegular = new Chart(ctx).Line(data, options);
        document.getElementById('js-legend-regular').innerHTML = window.chartRegular.generateLegend();
        window.chartRegular.store = new Array();
    }
    function changeLineCurveRegular() {
        $(document.getElementById("myChartRegular")).replaceWith('<canvas id="myChartRegular" style="width: 850px; height: 400px;" width="850" height="550"></canvas>');
        var data = {
            labels: ${graphLabels},
            datasets: ${graphDatasets}
        };
        var options = {
            segmentShowStroke: false,
            animateRotate: true,
            animateScale: false,
            datasetFill: true,
            responsive: false,
            bezierCurve: true,
            maintainAspectRatio: false,
            scaleLabel: "${"<%= value %>"}",
            tooltipTemplate: "${"<%= datasetLabel %> - <%= value %>"}",
            multiTooltipTemplate: "${"<%= datasetLabel %> - <%= value %>"}",
            legendTemplate: "${"<ul class=\\\"<%=name.toLowerCase()%>-legend\\\"></br> </br></br><% for (var i=0; i<datasets.length; i++){%><li class=\\\"legend-item\\\" ><input type=\\\"checkbox\\\" class=\\\"selectLegendRegular\\\" checked onchange=\\\"updateDataset(\\\$(this), window.chartRegular, '<%=datasets[i].label%>')\\\" style=\\\"display:inline-block;width:14px;height:14px;margin:-1px 4px 0 0;vertical-align:middle;cursor:pointer; margin-bottom:4.8px;\\\"></input><span style=\\\"background-color:<%=datasets[i].strokeColor%>\\\"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>"}"


        };
        var ctx = document.getElementById("myChartRegular").getContext("2d");
        window.chartRegular = new Chart(ctx).Line(data, options);
        document.getElementById('js-legend-regular').innerHTML = window.chartRegular.generateLegend();
        window.chartRegular.store = new Array();
    }

    function deSelectAllRegular() {
        var deSelector = document.getElementsByClassName("selectLegendRegular");
        $(deSelector).each(function(index) {
            if(($(deSelector).is(':not(:checked)'))){
                changeLineCurveRegular();
            }else if ($(deSelector).attr('checked') == "checked") {
                $(deSelector).prop('checked', false);
                $(deSelector).trigger('change');

            }
            return false;

        })
    }
    function unDisableStraightRegular() {
        var unDisabled = document.getElementById('straightLineRegular');
        $(unDisabled).removeClass('disabled');
        $(unDisabled).attr('disabled', false);
        $(unDisabled).removeClass('cursorOff')
    }
    function unDisableCurvedRegular() {
        var unDisabled = document.getElementById('curvedLineRegular');
        $(unDisabled).removeClass('disabled');
        $(unDisabled).attr('disabled', false);
        $(unDisabled).removeClass('cursorOff')
    }
</script>