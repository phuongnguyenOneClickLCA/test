<%@ page import="org.apache.commons.lang.StringUtils" %>
<g:if test="${!mainPage}">
<div class="portfolioview">
    <ul>
        <li class="${graphModel && !graphModel.isEmpty() ? 'active' : ''}" id="${indicator.indicatorId}graph" ><a href="javascript:;" style="${!graphModel ? 'color: #d8d8d8;' : ''}" onclick="$('#table${indicator.indicatorId}').hide().addClass('hidden'); $('#graph${indicator.indicatorId}').show().removeClass('hidden'); $('#${indicator.indicatorId}table').removeClass('active'); $('#${indicator.indicatorId}graph').addClass('active'); $('.additionalRuleHeader').addClass('hidden'); $('#${indicator.indicatorId}ribastageHeader').addClass('hidden');"><g:message code="portfolio.graph_view" /></a></li>
        <li id="${indicator.indicatorId}table" class="${graphModel && !graphModel.isEmpty() ? '' : 'active'}"><a href="javascript:;" onclick="$('#table${indicator.indicatorId}').show().removeClass('hidden'); $('#graph${indicator.indicatorId}').hide().addClass('hidden'); $('#${indicator.indicatorId}graph').removeClass('active'); $('#${indicator.indicatorId}table').addClass('active'); $('.additionalRuleHeader').removeClass('hidden'); $('#${indicator.indicatorId}ribastageHeader').removeClass('hidden');"><g:message code="portfolio.table_view" /></a></li>
        <li id="${indicator.indicatorId}displayRuleHeader" class="pull-right" style="margin-right:50px;"> ${ruleToDisplay?.localizedName} / ${calculationRuleLocalizedUnit} </li>
    </ul>
</div>
</g:if>

<div id="graph${indicator.indicatorId}" class="graph${graphModel && !graphModel.isEmpty() && !mainPage ? '' : ' hidden'}">
<g:if test="${graphModel && !graphModel.isEmpty()}">
    <div style="float:left; padding-left: 10px;"><canvas id="${indicator.indicatorId}canvas" style="width: 900px; height: ${mainPage ? '400' : '550'}px;"></canvas></div>
    <div style="float: left; padding-left: 30px; padding-top: 30px;"><g:if test="${portfolio?.showAverageInsteadOfTotal}"><label style="font-weight: bold;"><input type="checkbox" value="toggleThresholds" id="toggleThresholds${indicator.indicatorId}" onchange="updateData${StringUtils.remove(indicator.indicatorId, "-")}();" /><g:message code="portfolio.show_thresholds" /></label></g:if></div>
    <div style="float: left; padding-left:10px; padding-top: 30px;" id="${indicator.indicatorId}lineDiv"><a href="javascript:" class="btn bold" onclick="drawChartAsLineDenom${StringUtils.remove(indicator.indicatorId, "-")}();"><g:message code="design.graph_lines"/></a></div>
    <div style="float: left; padding-left:10px; padding-top: 30px;" id="${indicator.indicatorId}barDiv" class="hidden"><a href="javascript:" class="btn bold" onclick="drawChartAsBarDenom${StringUtils.remove(indicator.indicatorId, "-")}();"><g:message code="design.graph_bars"/></a></div>
</g:if>
<g:else><div class="nograph" style="max-width: ${mainPage ? '1100' : '900'}px;"><strong>${mainPage ? message(code: 'main.graph.no_scores') : message(code: 'portfolio.graph.no_scores')}</strong></div></span></g:else>
<g:if test="${graphModel && !graphModel.isEmpty()}">
    <script type="text/javascript">
    var theChart${StringUtils.remove(indicator.indicatorId, "-")};
    var chartType = window.my_chart_type;
    $(document).ready(function() {

        if (chartType === "Bar") {
            $('#${indicator.indicatorId}barDiv').addClass('hidden');
            $('#${indicator.indicatorId}lineDiv').removeClass('hidden');

        }else {
            $('#${indicator.indicatorId}lineDiv').addClass('hidden');
            $('#${indicator.indicatorId}barDiv').removeClass('hidden');

        }

            <g:set var="labels">[<g:each in="${graphModel.keySet().toList()}" var="design">"${design}", </g:each>]</g:set>
                <g:set var="datasets">
                [{
            fillColor: "rgba(220,220,220,0.2)",
            strokeColor: "rgba(220,220,220,1)",
            pointColor: "rgba(220,220,220,1)",
            pointStrokeColor: "#fff",
            pointHighlightFill: "#fff",
            pointHighlightStroke: "rgba(220,220,220,1)",
            data: [<g:each in="${graphModel.values()}" var="score">"${score}", </g:each>]
        }]

            </g:set>
        var labels = ${labels};
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
                tooltipFillColor: "rgba(0,0,0,0.8)",
                <g:if test="${maxScore && maxScore > 10}">
                scaleOverride: true,
                scaleStartValue: 0,
                scaleStepWidth: ${(int) Math.ceil(maxScore * 0.10)},
                scaleSteps: 10,
                </g:if>
                scaleLabel: function (valuePayload) { return formatNumberBylocale("${user?.localeString}", valuePayload.value) },
                tooltipTemplate: "${"<%=label%>: <%= formatNumberBylocale('${user?.localeString}', value) %>"}",
                multiTooltipTemplate: "${"<%= datasetLabel %> - <%= formatNumberBylocale('${user?.localeString}', value) %>"}"
            });
        } else {
            theChart${StringUtils.remove(indicator.indicatorId, "-")} = new Chart(ctx).Bar(lineChartData, {
                responsive: false,
                tooltipFillColor: "rgba(0,0,0,0.8)",
                <g:if test="${maxScore && maxScore > 10}">
                scaleOverride: true,
                scaleStartValue: 0,
                scaleStepWidth: ${(int) Math.ceil(maxScore * 0.10)},
                scaleSteps: 10,
                </g:if>
                scaleLabel: function (valuePayload) { return formatNumberBylocale("${user?.localeString}", valuePayload.value) },
                tooltipTemplate: "${"<%=label%>: <%= formatNumberBylocale('${user?.localeString}', value) %>"}",
                multiTooltipTemplate: "${"<%= datasetLabel %> - <%= formatNumberBylocale('${user?.localeString}', value) %>"}"
            });

        }

    });

    function updateData${StringUtils.remove(indicator.indicatorId, "-")}() {
        var chartType = window.my_chart_type;
        var thresHoldsForThisIndicator = $('#toggleThresholds${indicator.indicatorId}');
            var drawThresholds;
            if ($(thresHoldsForThisIndicator).is(':checked')) {
                drawThresholds = true;
            } else {
                drawThresholds = false;
            }
          <g:set var="labels">[<g:each in="${graphModel.keySet().toList()}" var="design">"${design}", </g:each>]</g:set>
              <g:set var="datasets">
              [{
                          fillColor: "rgba(220,220,220,0.2)",
                          strokeColor: "rgba(220,220,220,1)",
                          pointColor: "rgba(220,220,220,1)",
                          pointStrokeColor: "#fff",
                          pointHighlightFill: "#fff",
                          pointHighlightStroke: "rgba(220,220,220,1)",
                          data: [<g:each in="${graphModel.values()}" var="score">"${score}", </g:each>]
                      }]</g:set>
        var labels = ${labels};
            var datasets = ${datasets};

            if (drawThresholds) {
                chartType = "lineBar";
                var redPercentageThreshold = {type:'line',label: "Max", fillColor: "rgba(255, 0, 0, 0)", strokeColor: "rgba(255, 0, 0, 0.2)", pointColor: "rgba(255, 0, 0, 0.8)", pointStrokeColor: "#fff", pointHighlightFill: "#fff", pointHighlightStroke: "rgba(255, 0, 0, 1)", data: [<%----%><g:each in="${graphModel?.values()}"><%----%>"<g:formatNumber number="${graphModel?.values()?.collect({ it.toDouble() })?.max() * 0.9}" format="0.###" />", </g:each><%----%>]};
                var orangeAverageThreshold = {type:'line',label: "Mean", fillColor: "rgba(255, 165, 0, 0)", strokeColor: "rgba(255, 165, 0, 0.2)", pointColor: "rgba(255, 165, 0, 0.8)", pointStrokeColor: "#fff", pointHighlightFill: "#fff", pointHighlightStroke: "rgba(255, 165, 0, 1)", data: [<%----%><g:each in="${graphModel?.values()}"><%----%>"<g:formatNumber number="${graphModel?.values()?.collect({ it.toDouble() })?.sum() / graphModel?.values()?.collect({ it != "0" ? 1 : 0 })?.sum()}" format="0.###" />", </g:each><%----%>]};
                var blueMinThreshold = {type:'line',label: "Min", fillColor: "rgba(0, 0, 255, 0)", strokeColor: "rgba(0, 0, 255, 0.2)", pointColor: "rgba(0, 0, 255, 0.8)", pointStrokeColor: "#fff", pointHighlightFill: "#fff", pointHighlightStroke: "rgba(0, 0, 255, 1)", data: [<%----%><g:each in="${graphModel?.values()}"><%----%>"<g:formatNumber number="${(graphModel?.values()?.collect({ it.toDouble() })?.sum() / graphModel?.values()?.collect({ it != "0" ? 1 : 0 })?.sum() + graphModel?.values()?.collect({ it.toDouble() })?.min() ?: 0) * 0.5}" format="0.###" />", </g:each><%----%>]};
                if (datasets) {
                    datasets.push(redPercentageThreshold);
                    datasets.push(orangeAverageThreshold);
                    datasets.push(blueMinThreshold);
                }
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
                        scaleSteps: 10,
                        </g:if>
                        scaleLabel: function (valuePayload) { return formatNumberBylocale("${user?.localeString}", valuePayload.value) },
                        tooltipTemplate: "${"<%=label%>: <%= formatNumberBylocale('${user?.localeString}', value) %>"}",
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
                        scaleSteps: 10,
                        </g:if>
                        scaleLabel: function (valuePayload) { return formatNumberBylocale("${user?.localeString}", valuePayload.value) },
                        tooltipTemplate: "${"<%=label%>: <%= formatNumberBylocale('${user?.localeString}', value) %>"}",
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
                        scaleSteps: 10,
                        </g:if>
                        scaleLabel: function (valuePayload) { return formatNumberBylocale("${user?.localeString}", valuePayload.value) },
                        tooltipTemplate: "${"<%=label%>: <%= formatNumberBylocale('${user?.localeString}', value) %>"}",
                        multiTooltipTemplate: "${"<%= datasetLabel %> - <%= formatNumberBylocale('${user?.localeString}', value) %>"}"
                    });
                }
            }
            window.myLine = theChart${StringUtils.remove(indicator.indicatorId, "-")};
        }

    function drawChartAsLineDenom${StringUtils.remove(indicator.indicatorId, "-")}() {
        window.my_chart_type = "Line";
        $('#${indicator.indicatorId}lineDiv').addClass('hidden');
        $('#${indicator.indicatorId}barDiv').removeClass('hidden');

            <g:set var="labels">[<g:each in="${graphModel.keySet().toList()}" var="design">"${design}", </g:each>]</g:set>
                <g:set var="datasets">
            [{
            fillColor: "rgba(220,220,220,0.2)",
            strokeColor: "rgba(220,220,220,1)",
            pointColor: "rgba(220,220,220,1)",
            pointStrokeColor: "#fff",
            pointHighlightFill: "#fff",
            pointHighlightStroke: "rgba(220,220,220,1)",
            data: [<g:each in="${graphModel.values()}" var="score">"${score}", </g:each>]
        }]
            </g:set>
        var labels = ${labels};
        var datasets = ${datasets};
        var randomScalingFactor = function () {
            return Math.round(Math.random() * 100)
        };
        var lineChartData = {
            labels: labels,
            datasets: datasets
        };

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
            scaleLabel: function (valuePayload) { return formatNumberBylocale("${user?.localeString}", valuePayload.value) },
            tooltipTemplate: "${"<%=label%>: <%= formatNumberBylocale('${user?.localeString}', value) %>"}",
            multiTooltipTemplate: "${"<%= datasetLabel %> - <%= formatNumberBylocale('${user?.localeString}', value) %>"}"
        });
    }

    function drawChartAsBarDenom${StringUtils.remove(indicator.indicatorId, "-")}() {
        window.my_chart_type = "Bar";
        $('#${indicator.indicatorId}barDiv').addClass('hidden');
        $('#${indicator.indicatorId}lineDiv').removeClass('hidden');

            <g:set var="labels">[<g:each in="${graphModel.keySet().toList()}" var="design">"${design}", </g:each>]</g:set>
                <g:set var="datasets">
            [{
            fillColor: "rgba(220,220,220,0.2)",
            strokeColor: "rgba(220,220,220,1)",
            pointColor: "rgba(220,220,220,1)",
            pointStrokeColor: "#fff",
            pointHighlightFill: "#fff",
            pointHighlightStroke: "rgba(220,220,220,1)",
            data: [<g:each in="${graphModel.values()}" var="score">"${score}", </g:each>]
        }]
            </g:set>
        var labels = ${labels};
        var datasets = ${datasets};
        var randomScalingFactor = function () {
            return Math.round(Math.random() * 100)
        };
        var barChartData = {
            labels: labels,
            datasets: datasets
        };

        theChart${StringUtils.remove(indicator.indicatorId, "-")}.destroy();
        var canvas = document.getElementById("${indicator.indicatorId}canvas");
        var ctx = canvas.getContext("2d");
        theChart${StringUtils.remove(indicator.indicatorId, "-")}.destroy();
        theChart${StringUtils.remove(indicator.indicatorId, "-")} = new Chart(ctx).Bar(barChartData, {
            responsive: false,
            maintainAspectRatio: true,
            tooltipFillColor: "rgba(0,0,0,0.8)",
            <g:if test="${maxScore && maxScore > 10}">
            scaleOverride: true,
            scaleStartValue: 0,
            scaleStepWidth: ${(int) Math.ceil(maxScore * 0.10)},
            scaleSteps: 10,
            </g:if>
            scaleLabel: function (valuePayload) { return formatNumberBylocale("${user?.localeString}", valuePayload.value) },
            tooltipTemplate: "${"<%=label%>: <%= formatNumberBylocale('${user?.localeString}', value) %>"}",
            multiTooltipTemplate: "${"<%= datasetLabel %> - <%= formatNumberBylocale('${user?.localeString}', value) %>"}"
        });    }

</script>
</g:if>
</div>

<g:if test="${!mainPage}">
    <g:set var="queryService" bean="queryService"/>
    <g:set var="surfaceDefinitionsShared" value="${queryService.getQueryByQueryId("surfaceDefinitionsShared", true)}"/>
    <div style="clear: both;"></div>
                <table  cellspacing="0" class="table table-condensed table-cellvalignmiddle portfolioTable" style="margin-bottom: 0; ${graphModel && !graphModel.isEmpty() ? ' display:none;' : ''}" id="table${indicator.indicatorId}">
                    <thead>
                        <th>Designs</th>
                        <th id="${indicator.indicatorId}ribastageHeader" class="ribaleStagePortfolioHeader hidden"><g:message code="design.riba_stage" /></th>
                    <g:if test="${indicator.additionalPortfolioCalculationRules}">
                    <g:each in="${indicator.additionalPortfolioCalculationRules.reverse()}" var="additionalRuleId">
                        <th class="hidden additionalRuleHeader" style="width: 84px;"><span class="smoothTip tooltip--left" data-tooltip="${calc.renderCalculationRuleInfoById(indicator: indicator, calculationRuleId: additionalRuleId)}">${abbr(maxLength: 5, value:additionalRuleId)}</span></th>
                    </g:each>
                        <th id="${indicator.indicatorId}displayRuleHeader"><span class="smoothTip tooltip--left" data-tooltip="${ruleToDisplay?.localizedName} / ${calculationRuleLocalizedUnit}">${ruleToDisplay?.calculationRuleId}</span></th>
                    </g:if>
                    <g:else>
                        <th id="${indicator.indicatorId}displayRuleHeader"> ${ruleToDisplay?.localizedName} / ${calculationRuleLocalizedUnit} </th>
                    </g:else>

                    </thead>
                    <tbody>
                    <g:each in="${entityDesigns}" var="item" status="i">
                        <g:if test="${item.value}">
                            <g:each in="${item.value}" var="design" status="j">
                                <g:set var="random" value="${UUID.randomUUID().toString()}"/>
                                <tr${!item.key.hasAnyIndicator([indicator]) ? ' style=\"display: none;\" class=\"hiddenPortfolio' + indicator.indicatorId + '\"' : ''}>
                                    <g:set var="lcaCheckerResult" value="${design.getMissingRequiredResources(indicator, null, surfaceDefinitionsShared).isEmpty() ? design.lcaCheckerResult : null}" />
                                    <th>
                                        <g:if test="${item.key.nameVisibleInPortfolio}">
                                            <g:link controller="entity" action="show" params="[entityId: item.key.id]">${item.key.designPFName ? item.key.designPFName : item.key.shortName}, ${design.name}${lcaCheckerResult ? ", ${lcaCheckerResult.letter} (${lcaCheckerResult.percentage} %)" : ""}</g:link> <i class="fa fa-question greenInfoBubble infoBubble pointerCursor" id="${random}Parameters" onclick="showLCAParameters('${portfolio?.id}','${design.id}','${random}Parameters','${message(code: 'resource.sourcelisting_no_information_available')}')" style="margin-left: 5px;" aria-hidden="true"></i>
                                        </g:if>
                                        <g:else>
                                            <g:if test="${portfolio?.anonymous}">
                                                <g:message code="portfolio.design" args="[i + 1, j + 1]" />${lcaCheckerResult ? ", ${lcaCheckerResult.letter} (${lcaCheckerResult.percentage} %)" : ""} ${item.key.designPFAnon ? item.key.designPFAnon  : ""} <i class="fa fa-question greenInfoBubble infoBubble pointerCursor" id="${random}Parameters" onclick="showLCAParameters('${portfolio?.id}','${design.id}','${random}Parameters','${message(code: 'resource.sourcelisting_no_information_available')}')" style="margin-left: 5px;" aria-hidden="true"></i>
                                            </g:if>
                                            <g:else>
                                                ${item.key.designPFName ? item.key.designPFName : item.key.shortName}, ${design.name}${lcaCheckerResult ? ", ${lcaCheckerResult.letter} (${lcaCheckerResult.percentage} %)" : ""} <i class="fa fa-question greenInfoBubble infoBubble pointerCursor" id="${random}Parameters" onclick="showLCAParameters('${portfolio?.id}','${design.id}','${random}Parameters','${message(code: 'resource.sourcelisting_no_information_available')}')" style="margin-left: 5px;" aria-hidden="true"></i>
                                            </g:else>
                                        </g:else>
                                        <g:if test="${design.component}">
                                            &nbsp; <img src="/app/assets/constructionTypes/structure.png" class='constructionType'/>
                                        </g:if>
                                    </th>
                                    <td style="width: 84px;">
                                        <g:if test="${design?.ribaStage}"><strong>${design.ribaStage}</strong>
                                            ${message(code:com.bionova.optimi.core.Constants.DesignRIBAStages.map().get(design.ribaStage))}
                                        </g:if><g:else>
                                            &nbsp;
                                        </g:else>
                                    </td>
                                    <td style="${indicator.additionalPortfolioCalculationRules ? "width: 84px;" : ""}">
                                        <g:if test="${dynamicDenominator}">
                                            <g:set var="denominScore" value="${formattedForTableScores?.get(design.id)}" />
                                            <g:if test="${item.key.nameVisibleInPortfolio}">
                                                <opt:link controller="design" action="results" id="${item.key.id}" params="[childEntityId: design.id, indicatorId: indicator.indicatorId, isPortfolio: true]" target="_blank">
                                                    ${denominScore ? denominScore : message(code: 'portfolio.denominator_missing')}
                                                </opt:link>
                                            </g:if>
                                            <g:else>
                                                ${denominScore ? denominScore : message(code: 'portfolio.denominator_missing')}
                                            </g:else>
                                            <%--${dynamicDenominatorDifferencesToStandardDeviation?.get(denominator)?.get(design.id)}--%>
                                        </g:if>
                                        <g:else>
                                            <g:set var="denominScore" value="${formattedForTableScores?.get(design.id)}" />
                                            <g:if test="${item.key.nameVisibleInPortfolio}">
                                                <opt:link controller="design" action="results" id="${item.key.id}" params="[childEntityId: design.id, indicatorId: indicator.indicatorId, isPortfolio: true]" target="_blank">
                                                    ${denominScore ? denominScore : message(code: 'portfolio.denominator_missing')}
                                                </opt:link>
                                            </g:if>
                                            <g:else>
                                                ${denominScore ? denominScore : message(code: 'portfolio.denominator_missing')}
                                            </g:else>
                                            ${denominatorDifferencesToStandardDeviation?.get(design.id)}
                                        </g:else>
                                    </td>
                                    <g:if test="${indicator.additionalPortfolioCalculationRules}">
                                        <g:set var="denomAdditionalScores" value="${formattedForTableScoresAdditionalRules?.get(design.id.toString())}" />
                                        <g:each in="${indicator.additionalPortfolioCalculationRules}" var="additionalRuleId">
                                            <td style="width: 84px;">
                                                <g:if test="${denomAdditionalScores && denomAdditionalScores.get(additionalRuleId)}">
                                                    ${denomAdditionalScores.get(additionalRuleId)}
                                                </g:if>
                                                <g:else>
                                                    <g:message code="portfolio.denominator_missing" />
                                                </g:else>
                                            </td>
                                        </g:each>
                                    </g:if>
                                </tr>
                            </g:each>
                        </g:if>
                    </g:each>
                    <g:if test="${showAllLink}">
                        <tr>
                                <td id="show${indicator.indicatorId}"><g:message code="portfolio.showAll.info" /> <a href="javascript:;" id="showAll${indicator.indicatorId}" onclick="showHiddenPortfolios('show${indicator.indicatorId}', 'hide${indicator.indicatorId}', 'hiddenPortfolio${indicator.indicatorId}');"><g:message code="show.all" /></a></td>
                                <td id="hide${indicator.indicatorId}" style="display: none;"><g:message code="portfolio.hideAll.info" /> <a href="javascript:;" id="showAll${indicator.indicatorId}" onclick="hideHiddenPortfolios('hide${indicator.indicatorId}', 'show${indicator.indicatorId}', 'hiddenPortfolio${indicator.indicatorId}');"><g:message code="hide" /></a></td>
                        </tr>
                    </g:if>
                    <%--
                    <tr>
                        <th><g:message code="portfolio.aritmetic_mean" /></th>
                        <th>
                            <g:if test="${dynamicDenominator}">
                                <g:averageByDynamicDenominator resource="${denominator}" entities="${designs}" dynamicDenominatorScoresAsDouble="${dynamicDenominatorScoresAsDouble}" indicator="${indicator}" />
                            </g:if>
                            <g:else>
                                <g:averageByNonDynamicDenominator indicator="${indicator}" entities="${designs}" denominatorScoresAsDouble="${denominatorScoresAsDouble}" denominator="${denominator}" />
                            </g:else>
                        </th>
                    </tr>
                    <tr class="deviation">
                        <td><g:message code="portfolio.standard_deviation" /></td>
                        <td>
                            <g:if test="${dynamicDenominator}">
                                <g:standardDeviationByDynamicDenominator resource="${denominator}" indicator="${indicator}" dynamicDenominatorScoresAsDouble="${dynamicDenominatorScoresAsDouble}" entities="${designs}" />
                            </g:if>
                            <g:else>
                                <g:standardDeviationByNonDynamicDenominator entities="${designs}" indicator="${indicator}" denominator="${denominator}" denominatorScoresAsDouble="${denominatorScoresAsDouble}" />
                            </g:else>
                        </td>
                    </tr>
                    --%>
                    </tbody>
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
