<%@ page import="org.apache.commons.lang.StringUtils" %>
<g:if test="${!mainPage}">
<div class="portfolioview">
    <ul>
        <g:if test="${!indicator.nonNumericResult}">
            <li class="${graphModel && !graphModel.isEmpty() ? 'active' : ''}" id="${indicator.indicatorId}graph" ><a href="javascript:;" style="${!graphModel || graphModel?.get("nonNumeric") ? 'color: #d8d8d8;' : ''}" onclick="$('#table${indicator.indicatorId}').hide().addClass('hidden'); $('#graph${indicator.indicatorId}').show().removeClass('hidden'); $('#${indicator.indicatorId}table').removeClass('active'); $('#${indicator.indicatorId}graph').addClass('active');"><g:message code="portfolio.graph_view" /></a></li>
            <li id="${indicator.indicatorId}table" class="${graphModel && !graphModel.isEmpty() ? '' : 'active'}"><a href="javascript:;" onclick="$('#table${indicator.indicatorId}').show().removeClass('hidden'); $('#graph${indicator.indicatorId}').hide().addClass('hidden'); $('#${indicator.indicatorId}graph').removeClass('active'); $('#${indicator.indicatorId}table').addClass('active'); "><g:message code="portfolio.table_view" /></a></li>
            <li id="${indicator.indicatorId}displayRuleHeader" class="pull-right" style="margin-right:50px;"> ${ruleToDisplay?.localizedName} / ${calculationRuleLocalizedUnit} </li>
        </g:if>
        </ul>
</div>
</g:if>

<g:if test="${!indicator.nonNumericResult}">
<div id="graph${indicator.indicatorId}" class="graph${graphModel && !graphModel.isEmpty() && !mainPage ? '' : ' hidden'}"><%--
--%><g:set var="entitiesWithScores" value="${graphModel?.keySet()?.toList()}" /><%--
--%><g:set var="fillColors" value="['rgba(220,220,220,0.2)','rgba(151,187,205,0.2)', 'rgba(151,211,169,0.2)', 'rgba(151,172,166,0.2)', 'rgba(151,145,147,0.2)', 'rgba(151,186,147,0.2)', 'rgba(151,190,78,0.2)']" /><%--
--%><g:set var="strokeColors" value="['rgba(220,220,220,1)','rgba(151,187,205,1)', 'rgba(151,211,169,1)', 'rgba(151,172,166,1)', 'rgba(151,145,147,1)', 'rgba(151,186,147,1)', 'rgba(151,190,78,1)']" /><%--
--%><g:if test="${entitiesWithScores && entitiesWithScores.size() > 1}"><%--
    --%><div style="float:left; padding-left: 10px;"><canvas id="${indicator.indicatorId}canvas" style="width: ${mainPage ? '950' : '850'}px; height: ${mainPage ? '400' : '550'}px;"></canvas></div>
        <div style="float: left; padding-left: 30px; padding-top: 30px;"><g:each in="${operatingPeriods}" var="operatingPeriod" status="opInd"><label style="font-weight: bold;"><input type="checkbox" checked="checked" value="${operatingPeriod}" class="choosePeriod${indicator.indicatorId}" onchange="updateData${StringUtils.remove(indicator.indicatorId, "-")}();" />${operatingPeriod} <div style="display: inline-block; background-color: ${strokeColors[opInd]}; width: 30px; height: 10px;"></div></label></g:each></div><g:if test="${portfolio?.showAverageInsteadOfTotal}"><div style="float: left; padding-left: 15px; padding-top: 30px;"><g:each in="${operatingPeriods}" var="operatingPeriod"><label style="font-weight: bold;"><input type="checkbox" value="${operatingPeriod}" class="toggleThresholds${indicator.indicatorId}" onchange="threshold${StringUtils.remove(indicator.indicatorId, "-")}('${operatingPeriod}'); updateData${StringUtils.remove(indicator.indicatorId, "-")}();" /><g:message code="portfolio.show_thresholds" /> <i class="icon-question-sign tresholdHelp"></i> </label></g:each></div></g:if><%--
        --%><div style="float: left; padding-left:10px; padding-top: 30px;" id="${indicator.indicatorId}lineDiv"><a href="javascript:" class="btn bold" onclick="drawChartAsLine${StringUtils.remove(indicator.indicatorId, "-")}();"><g:message code="design.graph_lines"/></a></div><%--
        --%><div style="float: left; padding-left:10px; padding-top: 30px;" id="${indicator.indicatorId}barDiv" class="hidden"><a href="javascript:" class="btn bold" onclick="drawChartAsBars${StringUtils.remove(indicator.indicatorId, "-")}();"><g:message code="design.graph_bars"/></a></div><%--
--%></g:if><%--
--%><g:else><div class="nograph" style="max-width: ${mainPage ? '1100' : '900'}px;"><strong><g:if test="${mainPage}">${message(code: "main.graph.no_scores")}</g:if><g:else>${message(code: "portfolio.graph.no_scores")}</g:else></strong></div></g:else><%--
--%><g:if test="${entitiesWithScores && entitiesWithScores.size() > 1}">
    <script type="text/javascript">

    var theChart${StringUtils.remove(indicator.indicatorId, "-")};
    var chartType = window.my_chart_type;

    $(document).ready(function() {
        // defaults to bars

        $('.tresholdHelp').popover({
            content:"${message (code: 'portfolio.treshold_help')}",
            html:true,
            template: '<div class="popover"><div class="arrow"></div><div class="popover-content"></div></div>',
            trigger:'hover'
        });

        if (chartType === "Bar" || !chartType) {
            $('#${indicator.indicatorId}barDiv').addClass('hidden');
            $('#${indicator.indicatorId}lineDiv').removeClass('hidden');

        }else {
            $('#${indicator.indicatorId}lineDiv').addClass('hidden');
            $('#${indicator.indicatorId}barDiv').removeClass('hidden');

        }
            <%--
          --%><g:set var="labels">[<g:each in="${entitiesWithScores}" var="parent">"${parent}", </g:each>]</g:set><%--
          --%><g:set var="datasets">
                [<%--
             --%><g:each in="${operatingPeriods}" var="operatingPeriod" status="ind"><%--
             --%>{
            label: "${operatingPeriod}",
            fillColor: "${fillColors[ind]}",
            strokeColor: "${strokeColors[ind]}",
            pointColor: "${strokeColors[ind]}",
            pointStrokeColor: "#fff",
            pointHighlightFill: "#fff",
            pointHighlightStroke: "${strokeColors[ind]}",
            data: [<%----%><g:each in="${graphModel?.values()}" var="scores"><%----%>"${scores.get(operatingPeriod)}", </g:each><%----%>]
        }${ind + 1 < operatingPeriods.size() ? ',' : ''}<%--
            --%></g:each><%--
          --%>]
                <%--
          --%></g:set><%--
          --%>var labels = ${labels};
        var datasets = ${datasets};
        var randomScalingFactor = function () {
            return Math.round(Math.random() * 100)
        };
        var lineChartData = {
            labels: labels,
            datasets: datasets
        };

        //var dataAsJson = JSON.parse(lineChartData);
        //alert("Data: " + dataAsJson);
        var canvas = document.getElementById("${indicator.indicatorId}canvas");
        var ctx = canvas.getContext("2d");
        if (chartType === "Line") {
            theChart${StringUtils.remove(indicator.indicatorId, "-")} = new Chart(ctx).Line(lineChartData, {
                responsive: false,
                maintainAspectRatio: true,
                tooltipFillColor: "rgba(0,0,0,0.8)",
                <g:if test="${maxScore && maxScore > 10}">
                scaleOverride: true,
                scaleStartValue: 0,
                scaleStepWidth: ${(int) Math.ceil(maxScore * 0.10)},
                scaleSteps: 15,
                </g:if>
                <g:if test="${indicator?.requireMonthly}">
                scaleLabel: function (valuePayload) {
                    var label = formatNumberBylocale("${user?.localeString}", valuePayload.value) + " ${indicatorDisplayUnit}"
                    return label },
                </g:if><g:else>
                scaleLabel: function (valuePayload) { return formatNumberBylocale("${user?.localeString}", valuePayload.value) },
                </g:else>
                tooltipTemplate: "${"<%=label%>: <%= formatNumberBylocale('${user?.localeString}', value) %>"}",
                multiTooltipTemplate: "${"<%= datasetLabel %> - <%= formatNumberBylocale('${user?.localeString}', value) %>"}"
            });
            window.my_chart_type = "Line"
        } else {
            theChart${StringUtils.remove(indicator.indicatorId, "-")} = new Chart(ctx).Bar(lineChartData, {
                responsive: false,
                maintainAspectRatio: true,
                tooltipFillColor: "rgba(0,0,0,0.8)",
                <g:if test="${maxScore && maxScore > 10}">
                scaleOverride: true,
                scaleStartValue: 0,
                scaleStepWidth: ${(int) Math.ceil(maxScore * 0.10)},
                scaleSteps: 15,
                </g:if>
                <g:if test="${indicator?.requireMonthly}">
                scaleLabel: function (valuePayload) {
                    var label = formatNumberBylocale("${user?.localeString}", valuePayload.value) + " ${indicatorDisplayUnit}"
                    return label },
                </g:if><g:else>
                scaleLabel: function (valuePayload) { return formatNumberBylocale("${user?.localeString}", valuePayload.value) },
                </g:else>                tooltipTemplate: "${"<%=label%>: <%= formatNumberBylocale('${user?.localeString}', value) %>"}",
                multiTooltipTemplate: "${"<%= datasetLabel %> - <%= formatNumberBylocale('${user?.localeString}', value) %>"}"
            });
            window.my_chart_type = "Bar"

        }

    });

    function threshold${StringUtils.remove(indicator.indicatorId, "-")}(operatingPeriod) {
        var thresholds = $('.toggleThresholds${indicator.indicatorId}');
        if (thresholds) {
            thresholds.each(function(index, element) {
                if ($(element).val() !== operatingPeriod && $(element).is(":checked")) {
                    $(element).attr('checked', false);
                }
            });
        }
    }

    function updateData${StringUtils.remove(indicator.indicatorId, "-")}() {
        var chartType = window.my_chart_type;
        var thresholds = $('.toggleThresholds${indicator.indicatorId}');
        var chosenPeriod;
        var drawThresholds;

        thresholds.each(function(index, element) {
            if ($(element).is(":checked") && $(element).val()) {
                chosenPeriod = $(element).val();
                drawThresholds = true;
            }
        });

      <g:set var="labels">[<g:each in="${entitiesWithScores}" var="parent">"${parent}", </g:each>]</g:set><%--
      --%><g:set var="datasets">
          [<%--
           --%><g:each in="${operatingPeriods}" var="operatingPeriod" status="ind"><%--
           --%>{
            label: "${operatingPeriod}",
            fillColor: "${fillColors[ind]}",
            strokeColor: "${strokeColors[ind]}",
            pointColor: "${strokeColors[ind]}",
            pointStrokeColor: "#fff",
            pointHighlightFill: "#fff",
            pointHighlightStroke: "${strokeColors[ind]}",
            data: [<%----%><g:each in="${graphModel?.values()}" var="scores"><%----%>"${scores.get(operatingPeriod)}", </g:each><%----%>]
        }${ind + 1 < operatingPeriods.size() ? ',' : ''}<%--
           --%></g:each>
          ]<%--
   --%></g:set><%--
       --%>var labels = ${labels};
        var datasets = ${datasets};

        if (drawThresholds && datasets && chosenPeriod) {
            chartType = "lineBar";
            <g:each in="${operatingPeriods}" var="operatingPeriod" status="index">
                if (chosenPeriod === "${operatingPeriod}") {
                    datasets.push({type:"line", label: "Max ${operatingPeriod}", fillColor: "rgba(255, 0, 0, 0)", strokeColor: "rgba(255, 0, 0, 0.2)", pointColor: "rgba(255, 0, 0, 0.8)", pointStrokeColor: "#fff", pointHighlightFill: "#fff", pointHighlightStroke: "rgba(255, 0, 0, 1)", data: [<%----%><g:set var="graphMean" value="${calc.graphMean(graphModel: graphModel, operatingPeriod: operatingPeriod)}"/><g:each in="${graphModel?.values()}"><%----%>"<g:formatNumber number="${calc.graphMax(maxValue: topScore?.get(operatingPeriod), meanValue: graphMean)}" format="0.###" />", </g:each><%----%>]});
                    datasets.push({type:"line", label: "Mean ${operatingPeriod}", fillColor: "rgba(255, 165, 0, 0)", strokeColor: "rgba(255, 165, 0, 0.2)", pointColor: "rgba(255, 165, 0, 0.8)", pointStrokeColor: "#fff", pointHighlightFill: "#fff", pointHighlightStroke: "rgba(255, 165, 0, 1)", data: [<%----%><g:set var="graphMean" value="${calc.graphMean(graphModel: graphModel, operatingPeriod: operatingPeriod)}"/><g:each in="${graphModel?.values()}"><%----%>"${graphMean}", </g:each><%----%>]});
                    datasets.push({type:"line", label: "Min ${operatingPeriod}", fillColor: "rgba(0, 0, 255, 0)", strokeColor: "rgba(0, 0, 255, 0.2)", pointColor: "rgba(0, 0, 255, 0.8)", pointStrokeColor: "#fff", pointHighlightFill: "#fff", pointHighlightStroke: "rgba(0, 0, 255, 1)", data: [<%----%><g:set var="graphMin" value="${calc.graphMin(graphModel: graphModel, operatingPeriod: operatingPeriod, minScore: minScore?.get(operatingPeriod))}"/><g:each in="${graphModel?.values()}"><%----%>"${graphMin}", </g:each><%----%>]});
                }
            </g:each>
        }

        for (var i = 0; i < datasets.length; i++) {
            $('.choosePeriod${indicator.indicatorId}').each(function () {
                var value = $(this).val();

                if (value == datasets[i]["label"] && !$(this).is(':checked')) {
                    datasets.splice(i, 1);
                }

            });
        }
        theChart${StringUtils.remove(indicator.indicatorId, "-")}.destroy();
        var canvas = document.getElementById("${indicator.indicatorId}canvas");
        var ctx = canvas.getContext("2d");
        var lineChartData = {
            labels: labels,
            datasets: datasets
        };

        if (datasets.length) {
            if (chartType==="Bar") {
                theChart${StringUtils.remove(indicator.indicatorId, "-")} = new Chart(ctx).Bar(lineChartData, {
                    responsive: false,
                    maintainAspectRatio: true,
                    tooltipFillColor: "rgba(0,0,0,0.8)",
                    <g:if test="${maxScore && maxScore > 10}">
                    scaleOverride: true,
                    scaleStartValue: 0,
                    scaleStepWidth: ${(int) Math.ceil(maxScore * 0.10)},
                    scaleSteps: 15,
                    </g:if>
                    <g:if test="${indicator?.requireMonthly}">
                    scaleLabel: function (valuePayload) {
                        var label = formatNumberBylocale("${user?.localeString}", valuePayload.value) + " ${indicatorDisplayUnit}"
                        return label },
                    </g:if><g:else>
                    scaleLabel: function (valuePayload) { return formatNumberBylocale("${user?.localeString}", valuePayload.value) },
                    </g:else>                tooltipTemplate: "${"<%=label%>: <%= formatNumberBylocale('${user?.localeString}', value) %>"}",                    tooltipTemplate: "${"<%=label%>: <%= formatNumberBylocale('${user?.localeString}', value) %>"}",
                    multiTooltipTemplate: "${"<%= datasetLabel %> - <%= formatNumberBylocale('${user?.localeString}', value) %>"}",
                    scaleShowHorizontalLines: true,
                    scaleShowVerticalLines: true
                });
            } else if (chartType==="lineBar") {
                theChart${StringUtils.remove(indicator.indicatorId, "-")} = new Chart(ctx).LineBar(lineChartData, {
                    responsive: false,
                    maintainAspectRatio: true,
                    tooltipFillColor: "rgba(0,0,0,0.8)",
                    <g:if test="${maxScore && maxScore > 10}">
                    scaleOverride: true,
                    scaleStartValue:0,
                    scaleStepWidth: ${(int) Math.ceil(maxScore * 0.10)},
                    scaleSteps: 15,
                    </g:if>
                    <g:if test="${indicator?.requireMonthly}">
                    scaleLabel: function (valuePayload) {
                        var label = formatNumberBylocale("${user?.localeString}", valuePayload.value) + " ${indicatorDisplayUnit}"
                        return label },
                    </g:if><g:else>
                    scaleLabel: function (valuePayload) { return formatNumberBylocale("${user?.localeString}", valuePayload.value) },
                    </g:else>                tooltipTemplate: "${"<%=label%>: <%= formatNumberBylocale('${user?.localeString}', value) %>"}",                    tooltipTemplate: "${"<%=label%>: <%= formatNumberBylocale('${user?.localeString}', value) %>"}",
                    multiTooltipTemplate: "${"<%= datasetLabel %> - <%= formatNumberBylocale('${user?.localeString}', value) %>"}",
                    drawLinesToEdges:true
                });
            } else {
                theChart${StringUtils.remove(indicator.indicatorId, "-")} = new Chart(ctx).Line(lineChartData, {
                    responsive: false,
                    maintainAspectRatio: true,
                    tooltipFillColor: "rgba(0,0,0,0.8)",
                    <g:if test="${maxScore && maxScore > 10}">
                    scaleOverride: true,
                    scaleStartValue: 0,
                    scaleStepWidth: ${(int) Math.ceil(maxScore * 0.10)},
                    scaleSteps: 15,
                    </g:if>
                    <g:if test="${indicator?.requireMonthly}">
                    scaleLabel: function (valuePayload) {
                        var label = formatNumberBylocale("${user?.localeString}", valuePayload.value) + " ${indicatorDisplayUnit}"
                        return label },
                    </g:if><g:else>
                    scaleLabel: function (valuePayload) { return formatNumberBylocale("${user?.localeString}", valuePayload.value) },
                    </g:else>                tooltipTemplate: "${"<%=label%>: <%= formatNumberBylocale('${user?.localeString}', value) %>"}",                    tooltipTemplate: "${"<%=label%>: <%= formatNumberBylocale('${user?.localeString}', value) %>"}",
                    multiTooltipTemplate: "${"<%= datasetLabel %> - <%= formatNumberBylocale('${user?.localeString}', value) %>"}"
                });
            }

        }
        window.myLine = theChart${StringUtils.remove(indicator.indicatorId, "-")};
    }

    function drawChartAsLine${StringUtils.remove(indicator.indicatorId, "-")}() {
        window.my_chart_type = "Line";
        $('#${indicator.indicatorId}lineDiv').addClass('hidden');
        $('#${indicator.indicatorId}barDiv').removeClass('hidden');

            <%--
       --%><g:set var="labels">[<g:each in="${entitiesWithScores}" var="parent">"${parent}", </g:each>]</g:set><%--
          --%><g:set var="datasets">
            [<%--
             --%><g:each in="${operatingPeriods}" var="operatingPeriod" status="ind"><%--
             --%>{
            label: "${operatingPeriod}",
            fillColor: "${fillColors[ind]}",
            strokeColor: "${strokeColors[ind]}",
            pointColor: "${strokeColors[ind]}",
            pointStrokeColor: "#fff",
            pointHighlightFill: "#fff",
            pointHighlightStroke: "${strokeColors[ind]}",
            data: [<%----%><g:each in="${graphModel?.values()}" var="scores"><%----%>"${scores.get(operatingPeriod)}", </g:each><%----%>]
        }${ind + 1 < operatingPeriods.size() ? ',' : ''}<%--
            --%></g:each><%--
          --%>]
            <%--
      --%></g:set><%--
          --%>var labels = ${labels};
        var datasets = ${datasets};
        var randomScalingFactor = function () {
            return Math.round(Math.random() * 100)
        };

        for (var i = 0; i < datasets.length; i++) {
            $('.choosePeriod${indicator.indicatorId}').each(function () {
                var value = $(this).val();

                if (value == datasets[i]["label"] && !$(this).is(':checked')) {
                    datasets.splice(i, 1);
                }

            });
        }
        var lineChartData = {
            labels: labels,
            datasets: datasets
        };


        threshold${StringUtils.remove(indicator.indicatorId, "-")}(null);
        //var dataAsJson = JSON.parse(lineChartData);
        //alert("Data: " + dataAsJson);
        theChart${StringUtils.remove(indicator.indicatorId, "-")}.destroy();
        var canvas = document.getElementById("${indicator.indicatorId}canvas");
        var ctx = canvas.getContext("2d");
        theChart${StringUtils.remove(indicator.indicatorId, "-")} = new Chart(ctx).Line(lineChartData, {
            responsive: false,
            maintainAspectRatio: true,
            tooltipFillColor: "rgba(0,0,0,0.8)",
            <g:if test="${maxScore && maxScore > 10}">
            scaleOverride: true,
            scaleStartValue: 0,
            scaleStepWidth: ${(int) Math.ceil(maxScore * 0.10)},
            scaleSteps: 15,
            </g:if>
            <g:if test="${indicator?.requireMonthly}">
            scaleLabel: function (valuePayload) {
                var label = formatNumberBylocale("${user?.localeString}", valuePayload.value) + " ${indicatorDisplayUnit}"
                return label },
            </g:if><g:else>
            scaleLabel: function (valuePayload) { return formatNumberBylocale("${user?.localeString}", valuePayload.value) },
            </g:else>                tooltipTemplate: "${"<%=label%>: <%= formatNumberBylocale('${user?.localeString}', value) %>"}",            tooltipTemplate: "${"<%=label%>: <%= formatNumberBylocale('${user?.localeString}', value) %>"}",
            multiTooltipTemplate: "${"<%= datasetLabel %> - <%= formatNumberBylocale('${user?.localeString}', value) %>"}"
        });
    }

    function drawChartAsBars${StringUtils.remove(indicator.indicatorId, "-")}() {
        window.my_chart_type = "Bar";
        $('#${indicator.indicatorId}barDiv').addClass('hidden');
        $('#${indicator.indicatorId}lineDiv').removeClass('hidden');

            <%--
       --%><g:set var="labels">[<g:each in="${entitiesWithScores}" var="parent">"${parent}", </g:each>]</g:set><%--
          --%><g:set var="datasets">
            [<%--
             --%><g:each in="${operatingPeriods}" var="operatingPeriod" status="ind"><%--
             --%>{
            label: "${operatingPeriod}",
            fillColor: "${fillColors[ind]}",
            strokeColor: "${strokeColors[ind]}",
            pointColor: "${strokeColors[ind]}",
            pointStrokeColor: "#fff",
            pointHighlightFill: "#fff",
            pointHighlightStroke: "${strokeColors[ind]}",
            data: [<%----%><g:each in="${graphModel?.values()}" var="scores"><%----%>"${scores.get(operatingPeriod)}", </g:each><%----%>]
        }${ind + 1 < operatingPeriods.size() ? ',' : ''}<%--
            --%></g:each><%--
          --%>]
            <%--
      --%></g:set><%--
          --%>var labels = ${labels};
        var datasets = ${datasets};
        var randomScalingFactor = function () {
            return Math.round(Math.random() * 100)
        };

        for (var i = 0; i < datasets.length; i++) {
            $('.choosePeriod${indicator.indicatorId}').each(function () {
                var value = $(this).val();

                if (value == datasets[i]["label"] && !$(this).is(':checked')) {
                    datasets.splice(i, 1);
                }

            });
        }

        var lineChartData = {
            labels: labels,
            datasets: datasets
        };


        threshold${StringUtils.remove(indicator.indicatorId, "-")}(null);
        //var dataAsJson = JSON.parse(lineChartData);
        //alert("Data: " + dataAsJson);
        theChart${StringUtils.remove(indicator.indicatorId, "-")}.destroy();
        var canvas = document.getElementById("${indicator.indicatorId}canvas");
        var ctx = canvas.getContext("2d");
        theChart${StringUtils.remove(indicator.indicatorId, "-")} = new Chart(ctx).Bar(lineChartData, {
            responsive: false,
            maintainAspectRatio: true,
            tooltipFillColor: "rgba(0,0,0,0.8)",
            <g:if test="${maxScore && maxScore > 10}">
            scaleOverride: true,
            scaleStartValue: 0,
            scaleStepWidth: ${(int) Math.ceil(maxScore * 0.10)},
            scaleSteps: 15,
            </g:if>
            <g:if test="${indicator?.requireMonthly}">
            scaleLabel: function (valuePayload) {
                var label = formatNumberBylocale("${user?.localeString}", valuePayload.value) + " ${indicatorDisplayUnit}"
                return label },
            </g:if><g:else>
            scaleLabel: function (valuePayload) { return formatNumberBylocale("${user?.localeString}", valuePayload.value) },
            </g:else>                tooltipTemplate: "${"<%=label%>: <%= formatNumberBylocale('${user?.localeString}', value) %>"}",            tooltipTemplate: "${"<%=label%>: <%= formatNumberBylocale('${user?.localeString}', value) %>"}",
            multiTooltipTemplate: "${"<%= datasetLabel %> - <%= formatNumberBylocale('${user?.localeString}', value) %>"}"
        });    }
</script>
</g:if>
</div>
</g:if>
<g:if test="${!mainPage}">
    <div style="clear: both;"></div>
    <table cellspacing="0" class="table table-condensed table-cellvalignmiddle portfolioTable sortMe" style="margin-bottom: 0; ${graphModel && !graphModel.isEmpty() ? ' display:none;' : ''}" id="table${indicator.indicatorId}">
    <thead>
    <tr>
        <th><g:message code="entity.operatingPeriod" /></th>
        <g:each in="${operatingPeriods}" var="operatingPeriod">
            <th data-sort="portfolioResult"><span class="periodContainer">${operatingPeriod}</span><br />
                <g:if test="${exportLicensed}">
                    <g:link controller="portfolio" action="exportReport" id="${portfolio?.id}" params="[period: operatingPeriod]"><g:message code="export_to_excel" /></g:link><br />
                </g:if>
            </th>
        </g:each>
    </tr>
    </thead>
    <tbody>
    <g:each in="${entities}" var="entity" status="entityIndex">
        <tr${!entity.hasAnyIndicator([indicator]) ? ' style=\"display: none;\" class=\"hiddenPortfolio' + indicator.indicatorId + '\"' : ''}>
            <g:set var="lcaCheckerResult" value="${entity.lcaCheckerResult}" />
            <td>
                <g:if test="${entity.nameVisibleInPortfolio}">
                    <g:link controller="entity" action="show" params="[entityId: entity.id]">${entity.operatingPFName ? entity.operatingPFName : entity.shortName}${lcaCheckerResult ? ", ${lcaCheckerResult.letter} (${lcaCheckerResult.percentage} %)" : ""}</g:link>
                </g:if>
                <g:else>
                    <g:if test="${portfolio?.anonymous}">
                        <g:message code="portfolio.target" args="[entityIndex + 1]" />${lcaCheckerResult ? ", ${lcaCheckerResult.letter} (${lcaCheckerResult.percentage} %)" : ""}${entity.operatingPFAnon ? ", " + entity.operatingPFAnon : ""}
                    </g:if>
                    <g:else>
                        ${entity.operatingPFName ? entity.operatingPFName : entity.shortName}${lcaCheckerResult ? ", ${lcaCheckerResult.letter} (${lcaCheckerResult.percentage} %)" : ""}
                    </g:else>
                </g:else>
            </td>
            <g:each in="${operatingPeriods}" var="operatingPeriod">
                <td>
                    <g:if test="${entity.indicators?.collect{it.id}?.contains(indicator.id)}">
                        <g:if test="${entityPeriods?.get(entity)?.get(operatingPeriod)}">
                            <g:if test="${entityPeriods?.get(entity)?.get(operatingPeriod)?.locked}"><asset:image src="img/lock_icon_simple.png" style="height: 12px; margin-bottom: 3px;" /></g:if>
                            <g:set var="scoreValid" value="${calc.areResultsValid(entity: entityPeriods?.get(entity)?.get(operatingPeriod), indicator: indicator)}" />
                            <g:if test="${entity.nameVisibleInPortfolio}">
                                <g:set var="note" value="${entityPeriods?.get(entity)?.get(operatingPeriod)?.targetedNote}" />
                                <g:if test="${note}">
                                    <g:if test="${scoreValid}">
                                        <opt:link controller="operatingPeriod" action="results" rel="popover" data-trigger="hover" data-content="${note}" id="${entity?.id}" params="[childEntityId: entityPeriods?.get(entity)?.get(operatingPeriod)?.id, indicatorId: indicator.indicatorId, isPortfolio: true]" target="_blank">
                                            <calc:renderDisplayResult entity="${entityPeriods?.get(entity)?.get(operatingPeriod)}" indicator="${indicator}" useAppendValue="true" />
                                        </opt:link>
                                    </g:if>
                                    <g:else>
                                        <opt:link controller="entity" action="doComplexAssessment" rel="popover" data-trigger="hover" data-content="${note}" params="[childEntityId: entityPeriods?.get(entity)?.get(operatingPeriod)?.id, indicatorId: indicator.indicatorId]"><g:message code="recalculate" /> !</opt:link>
                                    </g:else>
                                </g:if>
                                <g:else>
                                    <g:if test="${scoreValid}">
                                        <opt:link controller="operatingPeriod" action="results" id="${entity?.id}" params="[childEntityId: entityPeriods?.get(entity)?.get(operatingPeriod)?.id, indicatorId: indicator.indicatorId, isPortfolio: true]" target="_blank">
                                            <calc:renderDisplayResult entity="${entityPeriods?.get(entity)?.get(operatingPeriod)}" indicator="${indicator}" useAppendValue="true" />
                                        </opt:link>
                                    </g:if>
                                    <g:else>
                                        <opt:link controller="entity" action="doComplexAssessment" params="[childEntityId: entityPeriods?.get(entity)?.get(operatingPeriod)?.id, indicatorId: indicator.indicatorId]"><g:message code="recalculate" /></opt:link>
                                    </g:else>
                                </g:else>
                            </g:if>
                            <g:else>
                                <g:if test="${scoreValid}">
                                    <calc:renderDisplayResult entity="${entityPeriods?.get(entity)?.get(operatingPeriod)}" indicator="${indicator}" useAppendValue="true" />
                                </g:if>
                                <g:else>
                                    <g:message code="recalculate" />
                                </g:else>
                            </g:else>
                        </g:if>
                        <g:else>
                            <g:message code="portfolio.period_missing" />
                        </g:else>
                    </g:if>
                    <g:else>
                        <g:message code="portfolio.no_indicator" />
                    </g:else>
                </td>
            </g:each>
        </tr>
    </g:each>
    </tbody>
    <tfoot>
    <g:if test="${showAll}">
        <tr>
            <td colspan="${operatingPeriods ? 1 + operatingPeriods.size() : 1}">
                <span id="show${indicator.indicatorId}"><g:message code="portfolio.showAll.info" /> <a href="javascript:;" id="showAll${indicator.indicatorId}" onclick="showHiddenPortfolios('show${indicator.indicatorId}', 'hide${indicator.indicatorId}', 'hiddenPortfolio${indicator.indicatorId}');"><g:message code="show.all" /></a></span>
                <span id="hide${indicator.indicatorId}" style="display: none;"><g:message code="portfolio.hideAll.info" /> <a href="javascript:;" id="showAll${indicator.indicatorId}" onclick="hideHiddenPortfolios('hide${indicator.indicatorId}', 'show${indicator.indicatorId}', 'hiddenPortfolio${indicator.indicatorId}');"><g:message code="hide" /></a></span>
            </td>
        </tr>
    </g:if>
    <g:if test="${portfolio?.showAverageInsteadOfTotal}">
        <tr>
            <th><g:message code="mean" /></th>
            <g:each in="${operatingPeriods}" var="operatingPeriod">
                <th><calc:averageScore indicator="${indicator}" entities="${entitiesByOperatingPeriod?.get(operatingPeriod)}" /></th>
            </g:each>
        </tr>
    </g:if>
    <g:else>
        <tr>
            <td>
                <strong>
                    <g:message code="totalScore" />,
                    <g:set var="entityCount" value="${0}" />
                    <g:each in="${entities}" var="entity">
                        <g:if test="${entity.indicators?.collect{it.id}?.contains(indicator.id)}">
                            <g:set var="entityCount" value="${entityCount + 1}" />
                        </g:if>
                    </g:each>
                    <g:message code="portfolio.entity_count" args="[entityCount]" />
                </strong>
            </td>
            <g:each in="${operatingPeriods}" var="operatingPeriod">
                <td><strong><g:link controller="portfolio" action="results" params="[indicatorId: indicator?.indicatorId, operatingPeriod: operatingPeriod]" id="${portfolio?.id}"><calc:totalScore entities="${entitiesByOperatingPeriod?.get(operatingPeriod)}" indicator="${indicator}" isDisplay="${true}" /></g:link></strong></td>
            </g:each>
        </tr>
        <tr>
            <td>
            </td>
            <g:each in="${operatingPeriods}" var="operatingPeriod">
                <td><g:link controller="portfolio" action="results" params="[indicatorId: indicator?.indicatorId, operatingPeriod: operatingPeriod]" id="${portfolio?.id}"><g:message code="show_breakdown"/></g:link></td>
            </g:each>
        </tr>
    </g:else>
    <g:if test="${indicator.allowAverageInPortfolio && !portfolio?.showAverageInsteadOfTotal}">
        <tr>
            <th><g:message code="mean" /></th>
            <g:each in="${operatingPeriods}" var="operatingPeriod">
                <th><calc:averageScore indicator="${indicator}" entities="${entitiesByOperatingPeriod?.get(operatingPeriod)}" /></th>
            </g:each>
        </tr>
    </g:if>

    <g:if test="${showPortfolioSourceListings}">
    <tr>
        <td>&nbsp;</td>
        <g:each in="${operatingPeriods}" var="operatingPeriod">
            <td><a href="javascript:" class="bold" onclick="showPortFolioSourceListing('${operatingPeriod}sourceListingTable${indicator?.id}', 'listing${indicator?.indicatorId}');"><g:message code="results.show_sources"/></a></td>
        </g:each>
    </tr>
    </g:if>
    </tfoot>
    </table>
<script type="text/javascript">
    $(function () {
        var hideDiv = ${isLastDivTorender && !noHideDiv ? true : false}
        if(hideDiv){
            $(".notFirst").hide()
        }
    })
    $('[rel="popover"]').popover({
        placement : 'top',
        template: '<div class="popover"><div class="arrow"></div><div class="popover-content"></div></div>'
    });
</script>
</g:if>