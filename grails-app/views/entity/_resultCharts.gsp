<g:set var="drawOverview" value="${chartsToRender?.get("overview")}"/>
<g:set var="drawBubble" value="${chartsToRender?.get("bubble")}"/>
<g:set var="drawSankey" value="${chartsToRender?.get("sankey")}"/>
<g:set var="drawTreemap" value="${chartsToRender?.get("treemap")}"/>
<g:set var="drawAnnual" value="${chartsToRender?.get("annual")}"/>
<g:set var="drawRadial" value="${chartsToRender?.get("radial")}"/>
<g:set var="drawStackedStage" value="${chartsToRender?.get("stackedStage")}"/>
<g:set var="drawStackedMat" value="${chartsToRender?.get("stackedMat")}"/>
<g:set var="drawBreakdown" value="${chartsToRender?.get("breakdown")}"/>
<g:set var="drawLifeCycle" value="${chartsToRender?.get("lifeCycle")}"/>
<g:set var="isOperatingIndicator" value="${indicator?.indicatorUse?.equalsIgnoreCase('operating')}"/>
<g:set var="selectedClassKey" value="${allClassificationQsMap?.find({ it?.value?.questionId?.equalsIgnoreCase(groupingQuestion?.questionId) })?.key ?: ''}"/>
<input type="text" class="hidden" id="chartsToRender" value="${chartsToRender?.findAll {it.value}.keySet()}"/>
<div class="container section chart-div-parent" id="focalDivFourPack">
    <div class="container ">
        <div class="card">
            <ul class="nav nav-tabs" role="tablist">
                <g:if test="${drawOverview}">
                    <li role="presentation" class="navInfo">
                        <a  id="overview_results" onclick="toggleActiveTab(this)" href="javascript:" ><g:message code="overview"/></a>
                    </li>
                </g:if>
                <g:if test="${drawBubble}">
                    <li role="presentation" class="navInfo">
                        <a  id="bubble_results" onclick="toggleActiveTab(this)" href="javascript:"><g:message code="bubble"/></a>
                    </li>
                </g:if>
                <g:if test="${drawSankey}">
                    <li role="presentation" class="navInfo">
                        <a  id="sankey_results" onclick="toggleActiveTab(this)" href="javascript:" ><g:message code='sankey'/></a>
                    </li>
                </g:if>
                <g:if test="${drawTreemap}">
                    <li role="presentation" class="navInfo">
                        <a  id="treemap_results" onclick="toggleActiveTab(this)" href="javascript:"><g:message code="treeMap_chart"/></a>
                    </li>
                </g:if>

                <g:if test="${drawLifeCycle}">
                    <li role="presentation" class="navInfo">
                        <a  id="life_cycle_results" onclick="toggleActiveTab(this)" href="javascript:"><g:message code="byStage"/></a>
                    </li>
                </g:if>
                <g:if test="${drawAnnual}">
                    <li role="presentation" class="navInfo">
                        <a  id="annual_results" onclick="toggleActiveTab(this)" href="javascript:"><g:message code="query.annual_data"/></a>
                    </li>
                </g:if>
                <g:if test="${drawRadial}">
                    <li role="presentation" class="navInfo">
                        <a  id="radial_results" onclick="toggleActiveTab(this)" href="javascript:"><g:message code="radial"/></a>
                    </li>
                </g:if>
                <g:if test="${drawStackedStage}">
                    <li role="presentation" class="navInfo">
                        <a  id="stacked_stage_rule_results" onclick="toggleActiveTab(this)" href="javascript:"><g:message code="stacked_stage"/></a>
                    </li>
                </g:if>
                <g:if test="${drawStackedMat}">
                    <li role="presentation" class="navInfo">
                        <a  id="stacked_mat_rule_results" onclick="toggleActiveTab(this)" href="javascript:"><g:message code="stacked_mat"/></a>
                    </li>
                </g:if>
                <g:if test="${drawBreakdown}">
                    <li role="presentation" class="navInfo">
                        <a  id="breakdown_results" onclick="toggleActiveTab(this)" href="javascript:"><g:message code="byClass"/></a>
                    </li>
                </g:if>
                <li role="presentation" class="navInfo">
                    <a  id="all_graphs" onclick="toggleActiveTab(this, true)" href="javascript:"><g:message code="all_graphs"/></a>
                </li>
                <li>
                    <i class="icon-question-sign epc-container-number" rel="popover" data-trigger="hover" data-html="true" data-content="${message(code:'switch_chart_type')}"></i>
                </li>


            </ul>
        </div>
    </div>
    <opt:spinner spinnerId="loadingSpinner_overview" display="true"/>
    <div id="allChartsWrapper" style="display: none">
        <div class="mainGraphContainer inline-block">
            <g:if test="${drawOverview}">
                <div id="overview_results_container" class="chartContainer show-on-print">
                    <div class="chart-wrapper" id="overviewPieChart"></div>
                </div>
            </g:if>
            <g:if test="${drawBubble}">
                <div id="bubble_results_container" class="chartContainer hidden show-on-print">
                    <div class="chart-wrapper" id="bubbleChartTypeSubtype"></div>
                </div>
            </g:if>
            <g:if test="${drawSankey}">
                <div id="sankey_results_container" class="chartContainer hidden show-on-print">
                    <div class="chart-wrapper" id="sankeyWrapper"></div>
                </div>
            </g:if>
            <g:if test="${drawTreemap}">
                <div id="treemap_results_container" class="chartContainer hidden show-on-print">
                    <div class="chart-wrapper" id="treeMapWrapper"></div>
                </div>
            </g:if>
            <g:if test="${drawLifeCycle}">
                <div id="life_cycle_results_container" class="chartContainer hidden show-on-print">
                    <div class="chart-wrapper" id="lifeCycleStageChart"></div>
                </div>
            </g:if>
            <g:if test="${drawAnnual}">
                <div id="annual_results_container" class="chartContainer hidden show-on-print">
                    <div class="chart-wrapper" id="annualAccumulationContainer"></div>
                </div>
            </g:if>

            <g:if test="${drawRadial}">
                <div id="radial_results_container" class="chartContainer hidden show-on-print">
                    <div class="chart-wrapper" id="radialByDesignByElementWrapper"></div>
                </div>
            </g:if>
            <g:if test="${drawStackedStage}">
                <div id="stacked_stage_rule_results_container" class="chartContainer hidden show-on-print">
                    <div class="chart-wrapper" id="chartByImpactCategoryStackedWrapper"></div>
                </div>
            </g:if>
            <g:if test="${drawStackedMat}">
                <div id="stacked_mat_rule_results_container" class="chartContainer hidden show-on-print">
                    <div class="chart-wrapper" id="chartByMatAndImpactWrapper"></div>
                </div>
            </g:if>
            <g:if test="${drawBreakdown}">
                <div id="breakdown_results_container" class="chartContainer hidden show-on-print">
                    <div class="chart-wrapper" id="allBreakDowns"></div>
                    <div class="btn-group text-center">
                        <a href="javascript:" class="btn btn-primary ${graphCalculationRules?.size() > 1 ?'' : 'hidden'}" id="showAllBreakDowns" onclick="drawAllBreakdown('${entity?.id}', '${indicator?.indicatorId}', 'allBreakDowns', $('#currentRuleInUse').val(), $('#currentClassifcation').val())"><i class="fa fa-plus" style="vertical-align:middle;"></i> <g:message code="results.show_all_breakdowns"/></a>
                    </div>
                </div>
            </g:if>
        </div>
        <div class="side_controller inline-block">
            <div id="btnGroupsWrapper"></div>

        </div>
    </div>

</div>
<script defer type="text/javascript">
    $(function () {
        renderResultGraphs('${entity?.id}','${indicator.indicatorId}','${calculationRule?.calculationRuleId}','${selectedClassKey}','${defaultFallbackClassification}')
        $("#focalDivFourPack").find("li[role='presentation']").first().addClass("active")
    })
</script>