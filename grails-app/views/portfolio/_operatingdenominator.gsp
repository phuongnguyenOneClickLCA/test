<%@ page import="org.apache.commons.lang.StringUtils" %>
<g:if test="${!mainPage}">
<div class="portfolioview">
    <ul>
        <li class="${graphModel && !graphModel.isEmpty() ? 'active' : ''}" id="${indicator.indicatorId}graph" ><a href="javascript:;" style="${!graphModel ? 'color: #d8d8d8;' : ''}" onclick="$('#table${indicator.indicatorId}').hide().addClass('hidden'); $('#graph${indicator.indicatorId}').show(); $('#graph${indicator.indicatorId}').removeClass('hidden'); $('#${indicator.indicatorId}table').removeClass('active'); $('#${indicator.indicatorId}graph').addClass('active'); $('.additionalRuleHeader').addClass('hidden'); $('#${indicator.indicatorId}ribastageHeader').addClass('hidden');"><g:message code="portfolio.graph_view" /></a></li>
        <li id="${indicator.indicatorId}table" class="${graphModel && !graphModel.isEmpty() ? '' : 'active'}"><a href="javascript:;" onclick="$('#table${indicator.indicatorId}').show().removeClass('hidden'); $('#graph${indicator.indicatorId}').hide().addClass('hidden'); $('#${indicator.indicatorId}graph').removeClass('active'); $('#${indicator.indicatorId}table').addClass('active'); $('.additionalRuleHeader').removeClass('hidden'); $('#${indicator.indicatorId}ribastageHeader').removeClass('hidden');"><g:message code="portfolio.table_view" /></a></li>
        <li id="${indicator.indicatorId}displayRuleHeader" class="pull-right" style="margin-right:50px;"> ${ruleToDisplay?.localizedName} / ${calculationRuleLocalizedUnit} </li>
    </ul>
</div>
</g:if>

<div id="graph${indicator.indicatorId}" class="graph${graphModel && !graphModel.isEmpty() && !mainPage ? '' : ' hidden'}">
    <g:set var="entitiesWithScores" value="${graphModel?.keySet()?.toList()}" />
    <g:set var="fillColors" value="['rgba(220,220,220,0.2)','rgba(151,187,205,0.2)', 'rgba(151,211,169,0.2)', 'rgba(151,172,166,0.2)', 'rgba(151,145,147,0.2)', 'rgba(151,186,147,0.2)', 'rgba(151,190,78,0.2)']" />
    <g:set var="strokeColors" value="['rgba(220,220,220,1)','rgba(151,187,205,1)', 'rgba(151,211,169,1)', 'rgba(151,172,166,1)', 'rgba(151,145,147,1)', 'rgba(151,186,147,1)', 'rgba(151,190,78,1)']" />
    <g:if test="${entitiesWithScores && !entitiesWithScores.isEmpty()}">
        <div style="float:left; padding-left: 10px;"><canvas id="${indicator.indicatorId}canvas" style="width: ${mainPage ? '950' : '850'}px; height: ${mainPage ? '400' : '550'}px;"></canvas></div>
    <div style="float: left; padding-left: 30px; padding-top: 30px;"><g:each in="${operatingPeriods}" var="operatingPeriod" status="opInd"><label style="font-weight: bold;"><input type="checkbox" checked="checked" value="${operatingPeriod}" class="choosePeriod${indicator.indicatorId}" onchange="updateData${StringUtils.remove(indicator.indicatorId, "-")}();" />${operatingPeriod} <div style="display: inline-block; background-color: ${strokeColors[opInd]}; width: 30px; height: 10px;"></div></label></g:each></div><g:if test="${portfolio?.showAverageInsteadOfTotal}"><div style="float: left; padding-left: 15px; padding-top: 30px;"><g:each in="${operatingPeriods}" var="operatingPeriod"><label style="font-weight: bold;"><input type="checkbox" value="${operatingPeriod}" class="toggleThresholds${indicator.indicatorId}" onchange="threshold${StringUtils.remove(indicator.indicatorId, "-")}('${operatingPeriod}'); updateData${StringUtils.remove(indicator.indicatorId, "-")}();" /><g:message code="portfolio.show_thresholds" /> <i class="icon-question-sign tresholdHelp"></i>  </label></g:each></div></g:if>
        <div style="float: left; padding-left:10px; padding-top: 30px;" id="${indicator.indicatorId}lineDiv"><a href="javascript:" class="btn bold" onclick="drawChartAsLineDenom${StringUtils.remove(indicator.indicatorId, "-")}();"><g:message code="design.graph_lines"/></a></div>
        <div style="float: left; padding-left:10px; padding-top: 30px;" id="${indicator.indicatorId}barDiv" class="hidden"><a href="javascript:" class="btn bold" onclick="drawChartAsBarDenom${StringUtils.remove(indicator.indicatorId, "-")}();"><g:message code="design.graph_bars"/></a></div>
    </g:if>
    <g:else><div class="nograph" style="max-width: ${mainPage ? '1100' : '900'}px;"><strong>${mainPage ? message(code: 'main.graph.no_scores') : message(code: 'portfolio.graph.no_scores')}</strong></div></g:else>
    <g:if test="${entitiesWithScores && !entitiesWithScores.isEmpty()}">
        <script type="text/javascript">
    var theChart${StringUtils.remove(indicator.indicatorId, "-")};
    var chartType = window.my_chart_type;
    $(document).ready(function() {

        $('.tresholdHelp').popover({
            content:"${message (code: 'portfolio.treshold_help')}",
            html:true,
            template: '<div class="popover"><div class="arrow"></div><div class="popover-content"></div></div>',
            trigger:'hover'
        });

        if (chartType === "Bar") {
            $('#${indicator.indicatorId}barDiv').addClass('hidden');
            $('#${indicator.indicatorId}lineDiv').removeClass('hidden');

        }else {
            $('#${indicator.indicatorId}lineDiv').addClass('hidden');
            $('#${indicator.indicatorId}barDiv').removeClass('hidden');

        }
            // defaults to bars

            <g:set var="labels">[<g:each in="${entitiesWithScores}" var="parent">"${parent}", </g:each>]</g:set>
            <g:set var="datasets">
                [<g:each in="${operatingPeriods}" var="operatingPeriod" status="ind">{
            label: "${operatingPeriod}",
            fillColor: "${fillColors[ind]}",
            strokeColor: "${strokeColors[ind]}",
            pointColor: "${strokeColors[ind]}",
            pointStrokeColor: "#fff",
            pointHighlightFill: "#fff",
            pointHighlightStroke: "${strokeColors[ind]}",
            data: [<g:each in="${graphModel?.values()}" var="scores">"${scores.get(operatingPeriod)}", </g:each>]
        }${ind + 1 < operatingPeriods.size() ? ',' : ''}</g:each>]
                </g:set>
        var labels = ${labels};
        var datasets = ${datasets};

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

        //var dataAsJson = JSON.parse(lineChartData);
        //alert("Data: " + dataAsJson);
        var canvas = document.getElementById("${indicator.indicatorId}canvas");
        var ctx = canvas.getContext("2d");

        if (chartType === "Bar") {
            theChart${StringUtils.remove(indicator.indicatorId, "-")} = new Chart(ctx).Bar(lineChartData, {
                responsive: false,
                tooltipFillColor: "rgba(0,0,0,0.8)",
                <g:if test="${maxScore && maxScore > 10}">
                scaleOverride: true,
                scaleStartValue: 0,
                scaleStepWidth: ${(int) Math.ceil(maxScore * 0.10)},
                scaleSteps: 10,
                </g:if>
                <g:if test="${indicator?.requireMonthly}">
                scaleLabel: function (valuePayload) {
                    var label = formatNumberBylocale("${user?.localeString}", valuePayload.value) + " ${indicatorDisplayUnit}"
                    return label },
                </g:if><g:else>
                scaleLabel: function (valuePayload) { return formatNumberBylocale("${user?.localeString}", valuePayload.value) },
                </g:else>                tooltipTemplate: "${"<%=label%>: <%= formatNumberBylocale('${user?.localeString}', value) %>"}",                tooltipFillColor: "rgba(0,0,0,0.8)",
                tooltipTemplate: "${"<%=label%>: <%= formatNumberBylocale('${user?.localeString}', value) %>"}",
                multiTooltipTemplate: "${"<%= datasetLabel %> - <%= formatNumberBylocale('${user?.localeString}', value) %>"}"
            });
        } else {
            theChart${StringUtils.remove(indicator.indicatorId, "-")} = new Chart(ctx).Line(lineChartData, {
                responsive: false,
                tooltipFillColor: "rgba(0,0,0,0.8)",
                <g:if test="${maxScore && maxScore > 10}">
                scaleOverride: true,
                scaleStartValue: 0,
                scaleStepWidth: ${(int) Math.ceil(maxScore * 0.10)},
                scaleSteps: 10,
                </g:if>
                <g:if test="${indicator?.requireMonthly}">
                scaleLabel: function (valuePayload) {
                    var label = formatNumberBylocale("${user?.localeString}", valuePayload.value) + " ${indicatorDisplayUnit}"
                    return label },
                </g:if><g:else>
                scaleLabel: function (valuePayload) { return formatNumberBylocale("${user?.localeString}", valuePayload.value) },
                </g:else>                tooltipTemplate: "${"<%=label%>: <%= formatNumberBylocale('${user?.localeString}', value) %>"}",                tooltipFillColor: "rgba(0,0,0,0.8)",
                tooltipTemplate: "${"<%=label%>: <%= formatNumberBylocale('${user?.localeString}', value) %>"}",
                multiTooltipTemplate: "${"<%= datasetLabel %> - <%= formatNumberBylocale('${user?.localeString}', value) %>"}"
            });
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
        var chartType =  window.my_chart_type;
        var thresholds = $('.toggleThresholds${indicator.indicatorId}');
        var chosenPeriod;
        var drawThresholds;

        thresholds.each(function(index, element) {
            if ($(element).is(":checked") && $(element).val()) {
                chosenPeriod = $(element).val();
                drawThresholds = true;
            }
        });

        <g:set var="labels">[<g:each in="${entitiesWithScores}" var="parent">"${parent}", </g:each>]</g:set>
            <g:set var="datasets">
                [<g:each in="${operatingPeriods}" var="operatingPeriod" status="ind">{
            label: "${operatingPeriod}",
            fillColor: "${fillColors[ind]}",
            strokeColor: "${strokeColors[ind]}",
            pointColor: "${strokeColors[ind]}",
            pointStrokeColor: "#fff",
            pointHighlightFill: "#fff",
            pointHighlightStroke: "${strokeColors[ind]}",
            data: [<g:each in="${graphModel?.values()}" var="scores">"${scores.get(operatingPeriod)}", </g:each>]
        }${ind + 1 < operatingPeriods.size() ? ',' : ''}</g:each>]
               </g:set>
        var labels = ${labels};
        var datasets = ${datasets};

        if (drawThresholds && datasets && chosenPeriod) {
            chartType = "lineBar";
            <g:each in="${operatingPeriods}" var="operatingPeriod" status="index">
                if (chosenPeriod === "${operatingPeriod}") {
                    datasets.push({type:"line", label: "Max ${operatingPeriod}", fillColor: "rgba(255, 0, 0, 0)", strokeColor: "rgba(255, 0, 0, 0.2)", pointColor: "rgba(255, 0, 0, 0.8)", pointStrokeColor: "#fff", pointHighlightFill: "#fff", pointHighlightStroke: "rgba(255, 0, 0, 1)", data: [<g:set var="graphMean" value="${calc.graphMean(graphModel: graphModel, operatingPeriod: operatingPeriod)}"/><g:each in="${graphModel?.values()}">"<g:formatNumber number="${calc.graphMax(maxValue: topScore?.get(operatingPeriod), meanValue: graphMean)}" format="0.###" />", </g:each>]});
                    datasets.push({type:"line", label: "Mean ${operatingPeriod}", fillColor: "rgba(255, 165, 0, 0)", strokeColor: "rgba(255, 165, 0, 0.2)", pointColor: "rgba(255, 165, 0, 0.8)", pointStrokeColor: "#fff", pointHighlightFill: "#fff", pointHighlightStroke: "rgba(255, 165, 0, 1)", data: [<g:set var="graphMean" value="${calc.graphMean(graphModel: graphModel, operatingPeriod: operatingPeriod)}"/><g:each in="${graphModel?.values()}">"${graphMean}", </g:each>]});
                    datasets.push({type:"line", label: "Min ${operatingPeriod}", fillColor: "rgba(0, 0, 255, 0)", strokeColor: "rgba(0, 0, 255, 0.2)", pointColor: "rgba(0, 0, 255, 0.8)", pointStrokeColor: "#fff", pointHighlightFill: "#fff", pointHighlightStroke: "rgba(0, 0, 255, 1)", data: [<g:set var="graphMin" value="${calc.graphMin(graphModel: graphModel, operatingPeriod: operatingPeriod, minScore: minScore?.get(operatingPeriod))}"/><g:each in="${graphModel?.values()}" var="valuesByPeriod">"${graphMin}", </g:each>]});
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
            if (chartType === "Bar") {
                theChart${StringUtils.remove(indicator.indicatorId, "-")} = new Chart(ctx).Bar(lineChartData, {
                    tooltipFillColor: "rgba(0,0,0,0.8)",
                    responsive: false,
                    <g:if test="${maxScore && maxScore > 10}">
                    scaleOverride: true,
                    scaleStartValue: 0,
                    scaleStepWidth: ${(int) Math.ceil(maxScore * 0.10)},
                    scaleSteps: 10,
                    </g:if>
                    tooltipFillColor: "rgba(0,0,0,0.8)",
                    <g:if test="${indicator?.requireMonthly}">
                    scaleLabel: function (valuePayload) {
                        var label = formatNumberBylocale("${user?.localeString}", valuePayload.value) + " ${indicatorDisplayUnit}"
                        return label },
                    </g:if><g:else>
                    scaleLabel: function (valuePayload) { return formatNumberBylocale("${user?.localeString}", valuePayload.value) },
                    </g:else>                tooltipTemplate: "${"<%=label%>: <%= formatNumberBylocale('${user?.localeString}', value) %>"}",                    tooltipTemplate: "${"<%=label%>: <%= formatNumberBylocale('${user?.localeString}', value) %>"}",
                    multiTooltipTemplate: "${"<%= datasetLabel %> - <%= formatNumberBylocale('${user?.localeString}', value) %>"}"
                });
            }else if (chartType === "lineBar") {
                theChart${StringUtils.remove(indicator.indicatorId, "-")} = new Chart(ctx).LineBar(lineChartData, {
                    tooltipFillColor: "rgba(0,0,0,0.8)",
                    responsive: false,
                    <g:if test="${maxScore && maxScore > 10}">
                    scaleOverride: true,
                    scaleStartValue: 0,
                    scaleStepWidth: ${(int) Math.ceil(maxScore * 0.10)},
                    scaleSteps: 10,
                    </g:if>
                    tooltipFillColor: "rgba(0,0,0,0.8)",
                    <g:if test="${indicator?.requireMonthly}">
                    scaleLabel: function (valuePayload) {
                        var label = formatNumberBylocale("${user?.localeString}", valuePayload.value) + " ${indicatorDisplayUnit}"
                        return label },
                    </g:if><g:else>
                    scaleLabel: function (valuePayload) { return formatNumberBylocale("${user?.localeString}", valuePayload.value) },
                    </g:else>                tooltipTemplate: "${"<%=label%>: <%= formatNumberBylocale('${user?.localeString}', value) %>"}",                    tooltipTemplate: "${"<%=label%>: <%= formatNumberBylocale('${user?.localeString}', value) %>"}",
                    multiTooltipTemplate: "${"<%= datasetLabel %> - <%= formatNumberBylocale('${user?.localeString}', value) %>"}",
                    scaleShowHorizontalLines: true,
                    scaleShowVerticalLines: true,
                    drawLinesToEdges:true
                });
            }else {
                theChart${StringUtils.remove(indicator.indicatorId, "-")} = new Chart(ctx).Line(lineChartData, {
                    tooltipFillColor: "rgba(0,0,0,0.8)",
                    responsive: false,
                    <g:if test="${maxScore && maxScore > 10}">
                    scaleOverride: true,
                    scaleStartValue: 0,
                    scaleStepWidth: ${(int) Math.ceil(maxScore * 0.10)},
                    scaleSteps: 10,
                    </g:if>
                    <g:if test="${indicator?.requireMonthly}">
                    scaleLabel: function (valuePayload) {
                        var label = formatNumberBylocale("${user?.localeString}", valuePayload.value) + " ${indicatorDisplayUnit}"
                        return label },
                    </g:if><g:else>
                    scaleLabel: function (valuePayload) { return formatNumberBylocale("${user?.localeString}", valuePayload.value) },
                    </g:else>                tooltipTemplate: "${"<%=label%>: <%= formatNumberBylocale('${user?.localeString}', value) %>"}",                    tooltipFillColor: "rgba(0,0,0,0.8)",
                    tooltipTemplate: "${"<%=label%>: <%= formatNumberBylocale('${user?.localeString}', value) %>"}",
                    multiTooltipTemplate: "${"<%= datasetLabel %> - <%= formatNumberBylocale('${user?.localeString}', value) %>"}"
                });
            }

        }
        $(canvas).width('${mainPage ? '950' : '850'}px;');
        $(canvas).height('${mainPage ? '400' : '550'}px;');
    }

    function drawChartAsLineDenom${StringUtils.remove(indicator.indicatorId, "-")}() {
        window.my_chart_type = "Line";
        $('#${indicator.indicatorId}lineDiv').addClass('hidden');
        $('#${indicator.indicatorId}barDiv').removeClass('hidden');
            <g:set var="labels">[<g:each in="${entitiesWithScores}" var="parent">"${parent}", </g:each>]</g:set>
                <g:set var="datasets">
            [<g:each in="${operatingPeriods}" var="operatingPeriod" status="ind">{
            label: "${operatingPeriod}",
            fillColor: "${fillColors[ind]}",
            strokeColor: "${strokeColors[ind]}",
            pointColor: "${strokeColors[ind]}",
            pointStrokeColor: "#fff",
            pointHighlightFill: "#fff",
            pointHighlightStroke: "${strokeColors[ind]}",
            data: [<g:each in="${graphModel?.values()}" var="scores">"${scores.get(operatingPeriod)}", </g:each>]
        }${ind + 1 < operatingPeriods.size() ? ',' : ''}</g:each>]
            </g:set>
        var labels = ${labels};
        var datasets = ${datasets};

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
                tooltipFillColor: "rgba(0,0,0,0.8)",
                <g:if test="${maxScore && maxScore > 10}">
                scaleOverride: true,
                scaleStartValue: 0,
                scaleStepWidth: ${(int) Math.ceil(maxScore * 0.10)},
                scaleSteps: 10,
                </g:if>
            <g:if test="${indicator?.requireMonthly}">
            scaleLabel: function (valuePayload) {
                var label = formatNumberBylocale("${user?.localeString}", valuePayload.value) + " ${indicatorDisplayUnit}"
                return label },
            </g:if><g:else>
            scaleLabel: function (valuePayload) { return formatNumberBylocale("${user?.localeString}", valuePayload.value) },
            </g:else>                tooltipTemplate: "${"<%=label%>: <%= formatNumberBylocale('${user?.localeString}', value) %>"}",
            tooltipFillColor: "rgba(0,0,0,0.8)",
            tooltipTemplate: "${"<%=label%>: <%= formatNumberBylocale('${user?.localeString}', value) %>"}",
            multiTooltipTemplate: "${"<%= datasetLabel %> - <%= formatNumberBylocale('${user?.localeString}', value) %>"}"
        });
    }
    function drawChartAsBarDenom${StringUtils.remove(indicator.indicatorId, "-")}() {
        window.my_chart_type = "Bar";
        $('#${indicator.indicatorId}barDiv').addClass('hidden');
        $('#${indicator.indicatorId}lineDiv').removeClass('hidden');

        <g:set var="labels">[<g:each in="${entitiesWithScores}" var="parent">"${parent}", </g:each>]</g:set>
            <g:set var="datasets">
            [<g:each in="${operatingPeriods}" var="operatingPeriod" status="ind">{
            label: "${operatingPeriod}",
            fillColor: "${fillColors[ind]}",
            strokeColor: "${strokeColors[ind]}",
            pointColor: "${strokeColors[ind]}",
            pointStrokeColor: "#fff",
            pointHighlightFill: "#fff",
            pointHighlightStroke: "${strokeColors[ind]}",
            data: [<g:each in="${graphModel?.values()}" var="scores">"${scores.get(operatingPeriod)}", </g:each>]
        }${ind + 1 < operatingPeriods.size() ? ',' : ''}</g:each>]
            </g:set>
        var labels = ${labels};
        var datasets = ${datasets};

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

        //var dataAsJson = JSON.parse(lineChartData);
        //alert("Data: " + dataAsJson);
        threshold${StringUtils.remove(indicator.indicatorId, "-")}(null);
        theChart${StringUtils.remove(indicator.indicatorId, "-")}.destroy();

        var canvas = document.getElementById("${indicator.indicatorId}canvas");
        var ctx = canvas.getContext("2d");
        theChart${StringUtils.remove(indicator.indicatorId, "-")} = new Chart(ctx).Bar(lineChartData, {
            responsive: false,
            tooltipFillColor: "rgba(0,0,0,0.8)",
            <g:if test="${maxScore && maxScore > 10}">
            scaleOverride: true,
            scaleStartValue: 0,
            scaleStepWidth: ${(int) Math.ceil(maxScore * 0.10)},
            scaleSteps: 10,
            </g:if>
            <g:if test="${indicator?.requireMonthly}">
            scaleLabel: function (valuePayload) {
                var label = formatNumberBylocale("${user?.localeString}", valuePayload.value) + " ${indicatorDisplayUnit}"
                return label },
            </g:if><g:else>
            scaleLabel: function (valuePayload) { return formatNumberBylocale("${user?.localeString}", valuePayload.value) },
            </g:else>                tooltipTemplate: "${"<%=label%>: <%= formatNumberBylocale('${user?.localeString}', value) %>"}",            tooltipFillColor: "rgba(0,0,0,0.8)",
            multiTooltipTemplate: "${"<%= datasetLabel %> - <%= value %>"}"
        });
    }
</script>
    </g:if>
</div>

<g:if test="${!mainPage}">
    <div style="clear: both;"></div>
    <table  cellspacing="0" class="table table-condensed table-cellvalignmiddle portfolioTable sortMe" style="margin-bottom: 0; ${graphModel && !graphModel.isEmpty() ? ' display:none;' : ''}" id="table${indicator.indicatorId}">
        <thead>
        <tr>
            <th><g:message code="entity.operatingPeriod" /></th>
            <g:each in="${operatingPeriods}" var="operatingPeriod">
                <th data-sort="portfolioResult"><span class="periodContainer">${operatingPeriod}</span><br />
                    <g:if test="${exportLicensed}">
                        <g:link controller="portfolio" action="exportReport" id="${portfolio?.id}" params="[period: operatingPeriod]"><g:message code="export_to_excel" /></g:link><br />
                    </g:if>
                    <g:link style="font-weight: normal;" controller="portfolio" action="sendReminder" id="${portfolio?.id}" params="[period: operatingPeriod]"><g:message code="portfolio.send_reminder" /></g:link>
                </th>
            </g:each>
        </tr>
        </thead>
            <tbody>
                <g:each in="${realties}" var="realty" status="entityIndex">
                    <tr${!realty.indicators?.collect{it.id}?.contains(indicator.id) ? ' style=\"display: none;\" class=\"hiddenPortfolio' + indicator.indicatorId + '\"' : ''}>
                        <g:set var="lcaCheckerResult" value="${realty.lcaCheckerResult}" />
                        <td>
                            <g:if test="${realty.nameVisibleInPortfolio}">
                                <g:link controller="entity" action="show" params="[entityId: realty.id]">${realty.operatingPFName ? realty.operatingPFName : realty.shortName}${lcaCheckerResult ? ", ${lcaCheckerResult.letter} (${lcaCheckerResult.percentage} %)" : ""}</g:link>
                            </g:if>
                            <g:else>
                                <g:if test="${portfolio?.anonymous && !realty.nameVisibleInPortfolio}">
                                    <g:message code="portfolio.target" args="[entityIndex + 1]" />${lcaCheckerResult ? ", ${lcaCheckerResult.letter} (${lcaCheckerResult.percentage} %)" : ""} ${realty.operatingPFAnon ? ", " + realty.operatingPFAnon : ""}
                                </g:if>
                                <g:else>
                                    ${realty.operatingPFName ? realty.operatingPFName : realty.shortName}${lcaCheckerResult ? ", ${lcaCheckerResult.letter} (${lcaCheckerResult.percentage} %)" : ""}
                                </g:else>
                            </g:else>
                        </td>
                        <g:each in="${operatingPeriods}" var="operatingPeriod">
                            <td>
                                <g:if test="${realtyPeriods?.get(realty)?.get(operatingPeriod)}">
                                    <g:if test="${dynamicDenominator}">
                                        <g:set var="denominScore" value="${formattedTableScores?.get(realtyPeriods?.get(realty)?.get(operatingPeriod)?.id)}" />
                                        <g:if test="${realty.nameVisibleInPortfolio}">
                                            <opt:link controller="operatingPeriod" action="results" id="${realty?.id}" params="[childEntityId: realtyPeriods?.get(realty)?.get(operatingPeriod)?.id, indicatorId: indicator.indicatorId, isPortfolio: true]" target="_blank">
                                                ${denominScore ? denominScore : message(code: 'portfolio.denominator_missing')}
                                            </opt:link>
                                        </g:if>
                                        <g:else>
                                            ${denominScore ? denominScore : message(code: 'portfolio.denominator_missing')}
                                        </g:else>
                                        <%--${denominatorDifferencesToStandardDeviation?.get(denominator)?.get(realtyPeriods?.get(realty)?.get(operatingPeriod)?.id)}--%>
                                    </g:if>
                                    <g:else>
                                        <g:set var="denominScore" value="${denominatorScores?.get(realtyPeriods?.get(realty)?.get(operatingPeriod)?.id)}" />
                                        <g:if test="${realty.nameVisibleInPortfolio}">
                                            <opt:link controller="operatingPeriod" action="results" id="${realty?.id}" params="[childEntityId: realtyPeriods?.get(realty)?.get(operatingPeriod)?.id, indicatorId: indicator.indicatorId, isPortfolio: true]" target="_blank">
                                                ${denominScore ? denominScore : message(code: 'portfolio.denominator_missing')}
                                            </opt:link>
                                        </g:if>
                                        <g:else>
                                            ${denominScore ? denominScore : message(code: 'portfolio.denominator_missing')}
                                        </g:else>
                                        <%--${denominatorDifferencesToStandardDeviation?.get(denominator)?.get(realtyPeriods?.get(realty)?.get(operatingPeriod)?.id)}--%>
                                    </g:else>
                                </g:if>
                                <g:else>
                                    <g:message code="portfolio.period_missing" />
                                </g:else>
                            </td>
                        </g:each>
                    </tr>
                </g:each>
                <%--
                <tr>
                    <th><g:message code="portfolio.aritmetic_mean" /></th>
                    <g:each in="${operatingPeriods}" var="operatingPeriod">
                        <th>
                            <g:averageByNonDynamicDenominator indicator="${indicator}" denominator="${denominator}" denominatorScoresAsDouble="${denominatorScoresAsDouble}" entities="${selectedOpObjects?.findAll({it.operatingPeriod == operatingPeriod})}" />
                        </th>
                    </g:each>
                </tr>
                <tr class="deviation">
                    <td><g:message code="portfolio.standard_deviation" /></td>
                    <g:each in="${operatingPeriods}" var="operatingPeriod">
                        <td>
                            <g:standardDeviationByNonDynamicDenominator entities="${selectedOpObjects?.findAll({it.operatingPeriod == operatingPeriod})}" indicator="${indicator}" denominator="${denominator}" denominatorScoresAsDouble="${denominatorScoresAsDouble}" />
                        </td>
                    </g:each>
                </tr>
                --%>
            </tbody>
        <tfoot>
        <g:if test="${showAllLink}">
            <tr>
                <td colspan="2">
                    <span id="show${indicator.indicatorId}"><g:message code="portfolio.showAll.info" /> <a href="javascript:;" id="showAll${indicator.indicatorId}" onclick="showHiddenPortfolios('show${indicator.indicatorId}', 'hide${indicator.indicatorId}', 'hiddenPortfolio${indicator.indicatorId}');"><g:message code="show.all" /></a></span>
                    <span id="hide${indicator.indicatorId}" style="display: none;"><g:message code="portfolio.hideAll.info" /> <a href="javascript:;" id="showAll${indicator.indicatorId}" onclick="hideHiddenPortfolios('hide${indicator.indicatorId}', 'show${indicator.indicatorId}', 'hiddenPortfolio${indicator.indicatorId}');"><g:message code="hide" /></a></span>
                </td>
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
    function setupToggle(trigger, target, hideMessage, showMessage) {
        var state = true;      // true means button says "show", target is hidden
        var $el = $(trigger);

        function update() {
            $el.attr('value', state ? showMessage : hideMessage);
            $(target).css('visibility', state ? 'hidden' : 'visible');
        }

        update();  // set initial state

        $el.on('click', function() {
            state = !state;   // on click, flick state and refresh
            update();
        });
    }

    setupToggle('#showHideDeviation', '.deviation', '${message(code: 'portfolio.hide_variance')}', '${message(code: 'portfolio.show_variance')}');
</script>
</g:if>

