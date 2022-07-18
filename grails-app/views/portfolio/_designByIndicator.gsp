<%
    def userService = grailsApplication.mainContext.getBean("userService")
%>
<%@ page import="com.bionova.optimi.util.OptimiStringUtils; org.apache.commons.lang.StringUtils" %>

<g:if test="${!mainPage}">
    <div class="portfolioview">
    <ul>
        <li class="${graphModel && !graphModel.isEmpty() ? 'active' : ''}" id="${indicator.indicatorId}graph" ><a href="javascript:" style="${!graphModel || graphModel?.get("nonNumeric") ? 'color: #d8d8d8;' : ''}" onclick="$('#table${indicator.indicatorId}').hide(); $('#graph${indicator.indicatorId}').show(); $('#${indicator.indicatorId}table').removeClass('active'); $('#${indicator.indicatorId}graph').addClass('active'); $('.additionalRuleHeader').addClass('hidden'); $('#${indicator.indicatorId}ribastageHeader').addClass('hidden');"><g:message code="portfolio.graph_view" /></a></li>
        <li id="${indicator.indicatorId}table" class="${graphModel && !graphModel.isEmpty() ? '' : 'active'}"><a href="javascript:" onclick="$('#table${indicator.indicatorId}').show(); $('#graph${indicator.indicatorId}').hide(); $('#${indicator.indicatorId}graph').removeClass('active'); $('#${indicator.indicatorId}table').addClass('active'); $('.additionalRuleHeader').removeClass('hidden'); $('#${indicator.indicatorId}ribastageHeader').removeClass('hidden');"><g:message code="portfolio.table_view" /></a></li>
        <li id="${indicator.indicatorId}displayRuleHeader" class="pull-right" style="margin-right:50px;"> ${ruleToDisplay?.localizedName} / ${calculationRuleLocalizedUnit} </li>

    </ul>
</div>
</g:if>

<div id="graph${indicator.indicatorId}" class="graph${graphModel && !graphModel.isEmpty() && !mainPage ? '' : ' hidden'}">
    <g:if test="${graphModel && graphModel.size() > 1}">
        <div style="float:left; padding-left: 10px;"><canvas id="${indicator.indicatorId}canvas" style="width: ${mainPage ? '1100' : '900'}px; height: ${mainPage ? '400' : '550'}px;"></canvas></div>
        <div style="float: left; padding-left: 30px; padding-top: 30px;"><g:if test="${portfolio?.showAverageInsteadOfTotal}"><label style="font-weight: bold;"><input type="checkbox" value="toggleThresholds" id="toggleThresholds${indicator.indicatorId}" onchange="updateData${StringUtils.remove(indicator.indicatorId, "-")}();" /><g:message code="portfolio.show_thresholds" /></label></g:if></div>
        <div style="float: left; padding-left:10px; padding-top: 30px;" id="${indicator.indicatorId}lineDiv"><a href="javascript:" class="btn bold" onclick="drawChartAsLine${StringUtils.remove(indicator.indicatorId, "-")}();"><g:message code="design.graph_lines"/></a></div>
        <div style="float: left; padding-left:10px; padding-top: 30px;" id="${indicator.indicatorId}barDiv" class="hidden"><a href="javascript:" class="btn bold" onclick="drawChartAsBars${StringUtils.remove(indicator.indicatorId, "-")}();"><g:message code="design.graph_bars"/></a></div>
    </g:if>
    <g:else><div class="nograph" style="width: ${mainPage ? '1100' : '900'}px;"><strong><g:if test="${mainPage}">${message(code: "main.graph.no_scores")}</g:if><g:else>${message(code: "portfolio.graph.no_scores")}</g:else></strong></div></g:else>
    <g:if test="${graphModel && graphModel.size() > 1}">
        <script type="text/javascript">
    var chartType = window.my_chart_type;
    var theChart${StringUtils.remove(indicator.indicatorId, "-")};

    $(document).ready(function() {

        if (chartType === "Bar" || !chartType) {
            $('#${indicator.indicatorId}barDiv').addClass('hidden');
            $('#${indicator.indicatorId}lineDiv').removeClass('hidden');

        }else {
            $('#${indicator.indicatorId}lineDiv').addClass('hidden');
            $('#${indicator.indicatorId}barDiv').removeClass('hidden');

        }

        <g:set var="labels">${graphModel.keySet().toList()?.collect{ """ "${ new OptimiStringUtils().removeSingleAndDoubleQuotes(it)}" """ }}</g:set>
        <g:set var="datasets">
                [{
            fillColor: "rgba(220,220,220,0.2)",
            strokeColor: "rgba(220,220,220,1)",
            pointColor: "rgba(220,220,220,1)",
            pointStrokeColor: "#fff",
            pointHighlightFill: "#fff",
            pointHighlightStroke: "rgba(220,220,220,1)",
            data: [<g:each in="${graphModel.values()}" var="score">"${score}", </g:each>]
        }
                ]

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
                scaleSteps: 15,
                </g:if>
                scaleLabel: function (valuePayload) { return formatNumberBylocale("${user?.localeString}", valuePayload.value) },
                tooltipTemplate: "${"<%=label%>: <%= formatNumberBylocale('${user?.localeString}', value) %>"}",
                multiTooltipTemplate: "${"<%= datasetLabel %> - <%= formatNumberBylocale('${user?.localeString}', value) %>"}"
            });
            window.my_chart_type = "Line"
        } else {
            theChart${StringUtils.remove(indicator.indicatorId, "-")} = new Chart(ctx).Bar(lineChartData, {
                responsive: false,
                tooltipFillColor: "rgba(0,0,0,0.8)",
                <g:if test="${maxScore && maxScore > 10}">
                scaleOverride: true,
                scaleStartValue: 0,
                scaleStepWidth: ${(int) Math.ceil(maxScore * 0.10)},
                scaleSteps: 15,
                </g:if>
                scaleLabel: function (valuePayload) { return formatNumberBylocale("${user?.localeString}", valuePayload.value) },
                tooltipTemplate: "${"<%=label%>: <%= formatNumberBylocale('${user?.localeString}', value) %>"}",
                multiTooltipTemplate: "${"<%= datasetLabel %> - <%= formatNumberBylocale('${user?.localeString}', value) %>"}"
            });
            window.my_chart_type = "Bar"
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
          <g:set var="labels">${graphModel.keySet().toList()?.collect{ """ "${ new OptimiStringUtils().removeSingleAndDoubleQuotes(it)}" """ }}</g:set>
        <g:set var="datasets">
              [{
                          fillColor: "rgba(220,220,220,0.2)",
                          strokeColor: "rgba(220,220,220,1)",
                          pointColor: "rgba(220,220,220,1)",
                          pointStrokeColor: "#fff",
                          pointHighlightFill: "#fff",
                          pointHighlightStroke: "rgba(220,220,220,1)",
                          data: [<g:each in="${graphModel.values()}" var="score">"${score}", </g:each>]
                      }
              ]
            </g:set>
        var labels = ${labels};
            var datasets = ${datasets};

            if (drawThresholds) {
                chartType = "lineBar";
                var redPercentageThreshold = {type:'line', label: "Max", fillColor: "rgba(255, 0, 0, 0)", strokeColor: "rgba(255, 0, 0, 0.2)", pointColor: "rgba(255, 0, 0, 0.8)", pointStrokeColor: "#fff", pointHighlightFill: "#fff", pointHighlightStroke: "rgba(255, 0, 0, 1)", data: [<%----%><g:each in="${graphModel?.values()}"><%----%>"<g:formatNumber number="${graphModel?.values()?.collect({ it.toDouble() })?.max() * 0.9}" format="0.###" />", </g:each><%----%>]};
                var orangeAverageThreshold = {type:'line', label: "Mean", fillColor: "rgba(255, 165, 0, 0)", strokeColor: "rgba(255, 165, 0, 0.2)", pointColor: "rgba(255, 165, 0, 0.8)", pointStrokeColor: "#fff", pointHighlightFill: "#fff", pointHighlightStroke: "rgba(255, 165, 0, 1)", data: [<%----%><g:each in="${graphModel?.values()}"><%----%>"<g:formatNumber number="${graphModel?.values()?.collect({ it.toDouble() })?.sum() / graphModel?.values()?.collect({ it != "0" ? 1 : 0 })?.sum()}" format="0.###" />", </g:each><%----%>]};
                var blueMinThreshold = {type:'line', label: "Min", fillColor: "rgba(0, 0, 255, 0)", strokeColor: "rgba(0, 0, 255, 0.2)", pointColor: "rgba(0, 0, 255, 0.8)", pointStrokeColor: "#fff", pointHighlightFill: "#fff", pointHighlightStroke: "rgba(0, 0, 255, 1)", data: [<%----%><g:each in="${graphModel?.values()}"><%----%>"<g:formatNumber number="${(graphModel?.values()?.collect({ it.toDouble() })?.sum() / graphModel?.values()?.collect({ it != "0" ? 1 : 0 })?.sum() + graphModel?.values()?.collect({ it.toDouble() })?.min() ?: 0) * 0.5}" format="0.###" />", </g:each><%----%>]};
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
                        scaleSteps: 15,
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
                        scaleSteps: 15,
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
                        scaleSteps: 15,
                        </g:if>
                        scaleLabel: function (valuePayload) { return formatNumberBylocale("${user?.localeString}", valuePayload.value) },
                        tooltipTemplate: "${"<%=label%>: <%= formatNumberBylocale('${user?.localeString}', value) %>"}",
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

            <g:set var="labels">${graphModel.keySet().toList()?.collect{ """ "${ new OptimiStringUtils().removeSingleAndDoubleQuotes(it)}" """ }}</g:set>
        <g:set var="datasets">
            [{
            fillColor: "rgba(220,220,220,0.2)",
            strokeColor: "rgba(220,220,220,1)",
            pointColor: "rgba(220,220,220,1)",
            pointStrokeColor: "#fff",
            pointHighlightFill: "#fff",
            pointHighlightStroke: "rgba(220,220,220,1)",
            data: [<g:each in="${graphModel.values()}" var="score">"${score}", </g:each>]
        }
            ]

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
            scaleSteps: 15,
            </g:if>
            scaleLabel: function (valuePayload) { return formatNumberBylocale("${user?.localeString}", valuePayload.value) },
            tooltipTemplate: "${"<%=label%>: <%= formatNumberBylocale('${user?.localeString}', value) %>"}",
            multiTooltipTemplate: "${"<%= datasetLabel %> - <%= formatNumberBylocale('${user?.localeString}', value) %>"}"
        });
    }

    function drawChartAsBars${StringUtils.remove(indicator.indicatorId, "-")}() {
        window.my_chart_type = "Bar";
        $('#${indicator.indicatorId}barDiv').addClass('hidden');
        $('#${indicator.indicatorId}lineDiv').removeClass('hidden');

            <g:set var="labels">${graphModel.keySet().toList()?.collect{ """ "${ new OptimiStringUtils().removeSingleAndDoubleQuotes(it)}" """ }}</g:set>
        <g:set var="datasets">
            [{
            fillColor: "rgba(220,220,220,0.2)",
            strokeColor: "rgba(220,220,220,1)",
            pointColor: "rgba(220,220,220,1)",
            pointStrokeColor: "#fff",
            pointHighlightFill: "#fff",
            pointHighlightStroke: "rgba(220,220,220,1)",
            data: [<g:each in="${graphModel.values()}" var="score">"${score}", </g:each>]
        }
            ]

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
            scaleSteps: 15,
            </g:if>
            scaleLabel: function (valuePayload) { return formatNumberBylocale("${user?.localeString}", valuePayload.value) },
            tooltipTemplate: "${"<%=label%>:  <%= formatNumberBylocale('${user?.localeString}', value) %>"}",
            multiTooltipTemplate: "${"<%= datasetLabel %> - <%= formatNumberBylocale('${user?.localeString}', value) %>"}"
        });    }
</script>
    </g:if>
</div>

<g:if test="${!mainPage}">
    <g:set var="queryService" bean="queryService"/>
    <g:set var="surfaceDefinitionsShared" value="${queryService.getQueryByQueryId("surfaceDefinitionsShared", true)}"/>
    <div style="clear: both;"></div>
    <table cellspacing="0" class="table table-condensed table-cellvalignmiddle portfolioTable" style="margin-bottom: 0;${graphModel && !graphModel.isEmpty() ? ' display:none;' : ''}" id="table${indicator.indicatorId}">
        <thead>
        <th>Designs</th>
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
        <g:set var="entityIndex" value="${0}" />
        <g:each in="${entityDesigns}" var="item">
            <g:set var="entityIndex" value="${entityIndex + 1}" />
            <g:set var="designIndex" value="${0}" />
            <g:set var="nameVisibleInPortfolio" value="${item.key.nameVisibleInPortfolio}" />
            <g:each in="${item.value}" var="design">
            <g:set var="random" value="${UUID.randomUUID().toString()}"/>
            <g:set var="designIndex" value="${designIndex + 1}" />
            <tr${!item.key.hasAnyIndicator([indicator]) ? ' style=\"display: none;\" class=\"hiddenPortfolio' + indicator.indicatorId + '\"' : ''}>
                <g:set var="lcaCheckerResult" value="${design.getMissingRequiredResources(indicator, null, surfaceDefinitionsShared).isEmpty() ? design.lcaCheckerResult : null}" />
                <th>
                <g:if test="${nameVisibleInPortfolio}">
                    <g:if test="${design.targetedNote}">
                        <g:link controller="entity" action="show" params="[entityId: item.key.id]">${item.key.designPFName ? item.key.designPFName : item.key.shortName}, ${design.anonymousDescription ? design.anonymousDescription : design.name}${lcaCheckerResult ? ", ${lcaCheckerResult.letter} (${lcaCheckerResult.percentage} %)" : ""} !</g:link> <i class="fa fa-question greenInfoBubble infoBubble pointerCursor" id="${random}Parameters" onclick="showLCAParameters('${portfolio?.id}','${design.id}','${random}Parameters','${message(code: 'resource.sourcelisting_no_information_available')}')" style="margin-left: 5px;" aria-hidden="true"></i>
                    </g:if>
                    <g:else>
                        <g:link controller="entity" action="show" params="[entityId: item.key.id]">${item.key.designPFName ? item.key.designPFName : item.key.shortName}, ${design.anonymousDescription ? design.anonymousDescription : design.name}${lcaCheckerResult ? ", ${lcaCheckerResult.letter} (${lcaCheckerResult.percentage} %)" : ""}</g:link> <i class="fa fa-question greenInfoBubble infoBubble pointerCursor" id="${random}Parameters" onclick="showLCAParameters('${portfolio?.id}','${design.id}','${random}Parameters','${message(code: 'resource.sourcelisting_no_information_available')}')" style="margin-left: 5px;" aria-hidden="true"></i>
                    </g:else>
                </g:if>
                <g:else>
                    <g:if test="${portfolio?.anonymous}">
                        <g:if test="${design.anonymousDescription}">
                            <g:message code="portfolio.design_with_anonymousDescription" args="[entityIndex]"/>${lcaCheckerResult ? ", ${lcaCheckerResult.letter} (${lcaCheckerResult.percentage} %)" : ""}, ${design.anonymousDescription} <i class="fa fa-question greenInfoBubble infoBubble pointerCursor" id="${random}Parameters" onclick="showLCAParameters('${portfolio?.id}','${design.id}','${random}Parameters','${message(code: 'resource.sourcelisting_no_information_available')}')" style="margin-left: 5px;" aria-hidden="true"></i>
                        </g:if>
                        <g:else>
                            <g:message code="portfolio.design" args="[entityIndex, designIndex]" />${lcaCheckerResult ? ", ${lcaCheckerResult.letter} (${lcaCheckerResult.percentage} %)" : ""} ${item.key.designPFAnon ? item.key.designPFAnon  : ""} <i class="fa fa-question greenInfoBubble infoBubble pointerCursor" id="${random}Parameters" onclick="showLCAParameters('${portfolio?.id}','${design.id}','${random}Parameters','${message(code: 'resource.sourcelisting_no_information_available')}')" style="margin-left: 5px;" aria-hidden="true"></i>
                        </g:else>
                    </g:if>
                    <g:else>
                        <span>${item.key.designPFName ? item.key.designPFName : item.key.shortName}${lcaCheckerResult ? ", ${lcaCheckerResult.letter} (${lcaCheckerResult.percentage} %)" : ""}, ${design.anonymousDescription ? design.anonymousDescription : design.name}</span> <i class="fa fa-question greenInfoBubble infoBubble pointerCursor" id="${random}Parameters" onclick="showLCAParameters('${portfolio?.id}','${design.id}','${random}Parameters','${message(code: 'resource.sourcelisting_no_information_available')}')" style="margin-left: 5px;" aria-hidden="true"></i>
                    </g:else>
                </g:else>
                    <g:if test="${design.component}">
                        &nbsp; <img src="/app/assets/constructionTypes/structure.png" class='constructionType'/>
                    </g:if>
                </th>
                <g:set var="hasIndicator" value="${item.key.indicators?.collect{it.indicatorId}?.contains(indicator.indicatorId)}"/>
                <td style="${indicator.additionalPortfolioCalculationRules ? "width: 84px;" : ""}">
                <g:if test="${hasIndicator}">
                    <g:if test="${design.superVerified}"><i class="fa fa-star oneClickColorScheme" style="height: 12px; margin-bottom: 3px;"></i></g:if><g:elseif test="${design.locked}"><i class="fas fa-lock"></i> </g:elseif>
                    <g:set var="scoreValid" value="${calc.areResultsValid(entity: design, indicator: indicator)}" />
                    <g:if test="${nameVisibleInPortfolio}">
                        <g:if test="${design.disabledIndicators?.contains(indicator.indicatorId)}">
                            <g:link controller="entity" action="show" style="color: gray;" params="[entityId: item.key.id]" target="_blank">
                                <g:message code="design.indicator.not_in_use" default="Not in use"/>
                            </g:link>
                        </g:if>
                        <g:elseif test="${scoreValid}">
                            <opt:link controller="design" action="results" id="${item.key.id}" params="[childEntityId: design.id, indicatorId: indicator.indicatorId, isPortfolio: true]" target="_blank">
                                <calc:renderDisplayResult entity="${design}" noScientificNumbers="${true}" indicator="${indicator}" isDisplay="true"  useAppendValue="true" />
                            </opt:link>
                        </g:elseif>
                        <g:else>
                            <opt:link controller="entity" action="doComplexAssessment" params="[childEntityId: design.id, indicatorId: indicator.indicatorId]">
                                <g:message code="recalculate" />
                            </opt:link>
                        </g:else>
                    </g:if>
                    <g:else>
                        <g:if test="${design.disabledIndicators?.contains(indicator.indicatorId)}">
                            <g:if test="${userService.getSuperUser(user) ||(!portfolio?.anonymous && nameVisibleInPortfolio)}">
                                <g:link controller="entity" action="show" style="color: gray;" params="[entityId: item.key.id]" target="_blank">
                                    <g:message code="design.indicator.not_in_use" default="Not in use"/>
                                </g:link>
                            </g:if>
                            <g:else>
                                <a href="#" style="color: gray;"><g:message code="design.indicator.not_in_use" default="Not in use"/></a>
                            </g:else>
                        </g:if>
                        <g:elseif test="${scoreValid}">
                            <g:if test="${userService.getSuperUser(user)}">
                                <opt:link controller="design" action="results" id="${item.key.id}" params="[childEntityId: design.id, indicatorId: indicator.indicatorId, isPortfolio: true]" target="_blank">
                                    <calc:renderDisplayResult entity="${design}" noScientificNumbers="${true}" indicator="${indicator}" isDisplay="true"  useAppendValue="true" />
                                </opt:link>
                            </g:if>
                            <g:else>
                                <g:if test="${!portfolio?.anonymous && nameVisibleInPortfolio}">
                                    <opt:link controller="design" action="results" id="${item.key.id}" params="[childEntityId: design.id, indicatorId: indicator.indicatorId, isPortfolio: true]" target="_blank">
                                        <calc:renderDisplayResult entity="${design}" noScientificNumbers="${true}" indicator="${indicator}" isDisplay="true"  useAppendValue="true" />
                                    </opt:link>
                                    </g:if><g:else>
                                    <calc:renderDisplayResult entity="${design}" noScientificNumbers="${true}" indicator="${indicator}" isDisplay="true"  useAppendValue="true" />
                                </g:else>

                                </g:else>
                        </g:elseif>
                        <g:else>
                            <g:message code="recalculate" />
                        </g:else>
                    </g:else>
                </g:if>
                <g:else>
                    <g:message code="portfolio.no_indicator" />
                </g:else>
                </td>
                <g:if test="${indicator.additionalPortfolioCalculationRules}">
                    <g:each in="${indicator.additionalPortfolioCalculationRules}" var="additionalRuleId">
                        <td style="width: 84px;">
                        <g:if test="${hasIndicator}">
                            <calc:portfolioTotalScoreByCalculationRule indicator="${indicator}" entity="${design}" calculationRuleId="${additionalRuleId}" />
                        </g:if>
                        <g:else>
                            <g:message code="portfolio.no_indicator" />
                        </g:else>
                        </td>
                    </g:each>
                </g:if>
            </tr>
            </g:each>
        </g:each>
        <g:if test="${indicator.allowAverageInPortfolio || portfolio?.showAverageInsteadOfTotal}">
        <tr>
            <th><g:message code="mean" /></th>
            <th><calc:averageScore indicator="${indicator}" entityMap="${entityDesigns}" /></th>
            <g:if test="${indicator.additionalPortfolioCalculationRules}">
                <g:each in="${indicator.additionalPortfolioCalculationRules}" var="additionalRuleId">
                    <%--
                    <td style="width: 84px;">
                        <g:if test="${hasIndicator}">
                            <calc:portfolioTotalScoreByCalculationRule indicator="${indicator}" entity="${design}" calculationRuleId="${additionalRuleId}" />
                        </g:if>
                        <g:else>
                            <g:message code="portfolio.no_indicator" />
                        </g:else>
                    </td>
                    --%>
                </g:each>
            </g:if>
        </tr>
        </g:if>
        <g:else>
            <tr>
                <th><g:message code="totalScore" /></th>
                <th><g:link controller="portfolio" action="results" params="[indicatorId: indicator?.indicatorId, operatingPeriod: operatingPeriod]" id="${portfolio?.id}"><calc:totalScore entities="${allDesigns}" indicator="${indicator}" isDisplay="${true}" /></g:link></th>
                <g:if test="${indicator.additionalPortfolioCalculationRules}">
                    <g:each in="${indicator.additionalPortfolioCalculationRules}" var="additionalRuleId">
                        <th>
                             <calc:totalScore entities="${allDesigns}" indicator="${indicator}" isDisplay="${true}" calculationRuleId="${additionalRuleId}" />
                        </th>
                    </g:each>
                </g:if>
            </tr>
            <tr>
                <td></td>
                <td><g:link controller="portfolio" action="results" params="[indicatorId: indicator?.indicatorId, operatingPeriod: operatingPeriod]" id="${portfolio?.id}"><g:message code="show_breakdown"/></g:link></td>
                <g:if test="${indicator.additionalPortfolioCalculationRules}">
                    <g:each in="${indicator.additionalPortfolioCalculationRules}" var="additionalRuleId">
                        <td>
                        </td>
                    </g:each>
                </g:if>
            </tr>
        </g:else>
        <g:if test="${showAllLink}">
        <tr>
            <td colspan="2">
                <span id="show${indicator.indicatorId}"><g:message code="portfolio.showAll.info" /> <a href="javascript:;" id="showAll${indicator.indicatorId}" onclick="showHiddenPortfolios('show${indicator.indicatorId}', 'hide${indicator.indicatorId}', 'hiddenPortfolio${indicator.indicatorId}');"><g:message code="show.all" /></a></span>
                <span id="hide${indicator.indicatorId}" style="display: none;"><g:message code="portfolio.hideAll.info" /> <a href="javascript:;" id="showAll${indicator.indicatorId}" onclick="hideHiddenPortfolios('hide${indicator.indicatorId}', 'show${indicator.indicatorId}', 'hiddenPortfolio${indicator.indicatorId}');"><g:message code="hide" /></a></span>
            </td>
        </tr>
        </g:if>
        </tbody>
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
