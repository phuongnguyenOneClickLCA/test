<g:set var="reportVisualItems" value="${allValidIndicatorsAllTypes.collect({it.reportVisualisation})?.flatten()}"/>
<g:set var="drawTotalGraph" value="${reportVisualItems?.contains("entityPageChart")}"/>
<g:set var="entityByStageChart" value="${reportVisualItems?.contains("entityByStageChart")}"/>
<g:set var="entityElementChart" value="${reportVisualItems?.contains("entityElementChart")}"/>
<g:set var="entityCompareElementsChart" value="${reportVisualItems?.contains("entityCompareElementsChart")}"/>
<g:set var="entityByStageElementChart" value="${reportVisualItems?.contains("entityByStageElementChart")}"/>

<g:set var="allValidIndicatorsAllTypesIds" value="${allValidIndicatorsAllTypes.collect({it.indicatorId}) as List<String>}"/>
<g:set var="isOperatingIndicator" value="${indicator?.indicatorUse?.equalsIgnoreCase('operating')}"/>
<g:set var="moreThanOneTab" value="${reportVisualItems?.findAll({com.bionova.optimi.core.Constants.PROJECT_CHARTS_VISUALIZATIONS.contains(it)})?.size() > 1}"/>
<div class="container section chart-div-parent" id="focalDivFourPack">
    <div class="container ">
        <div class="card">
            <ul class="nav nav-tabs" role="tablist">
                <g:if test="${drawTotalGraph}">
                    <li role="presentation" class="navInfo">
                        <a  id="total_results" onclick="toggleActiveTab(this)" href="javascript:" ><g:message code="total_results"/></a>
                    </li>
                </g:if>
                <g:if test="${entityByStageChart}">
                    <li role="presentation" class="navInfo">
                        <a  id="result_by_stage" onclick="toggleActiveTab(this)" href="javascript:" ><span class="forOperating ${isOperatingIndicator ? '' : 'hidden'}"><g:message code='resultCategory.name'/></span><span class="forDesign ${isOperatingIndicator ? 'hidden':''}"><g:message code='byStage'/></span></a>
                    </li>
                </g:if>
                <g:if test="${entityElementChart}">
                    <li role="presentation" class="navInfo">
                        <a  id="result_by_class" onclick="toggleActiveTab(this)" href="javascript:"><g:message code="result_by_class"/></a>
                    </li>
                </g:if>
                <g:if test="${entityCompareElementsChart}">
                    <li role="presentation" class="navInfo">
                        <a  id="result_by_class_group" onclick="toggleActiveTab(this)" href="javascript:"><g:message code="compare_elements"/></a>
                    </li>
                </g:if>
                <g:if test="${entityByStageElementChart}">
                    <li role="presentation" class="navInfo">
                        <a  id="element_compare" onclick="toggleActiveTab(this)" href="javascript:"><span class="forOperating ${isOperatingIndicator ? '' : 'hidden'}"><g:message code='compare_elements_and_scopes'/></span><span class="forDesign ${isOperatingIndicator ? 'hidden':''}"><g:message code="compare_elements_and_stages"/></span></a>
                    </li>
                </g:if>
                <g:if test="${moreThanOneTab}">
                    <li role="presentation" class="navInfo">
                        <a  id="all_graphs" onclick="toggleActiveTab(this, true)" href="javascript:"><g:message code="all_graphs"/></a>
                    </li>
                    <li>
                        <i class="icon-question-sign epc-container-number" rel="popover" data-trigger="hover" data-html="true" data-content="${message(code:'switch_chart_type')}"></i>
                    </li>
                </g:if>
            </ul>
        </div>
    </div>
    <div class="container" id="calculationRunningMessageForCharts" style="display: none">
        <h4><g:message code="entity.calculation.running.graphs"/>.</h4>
    </div>
    <div class="loading-spinner span8" style="margin: 0 auto; width: 100%;" id="loadingSpinner_overview">
        <div class="image">
            <svg class="loadingSVG" version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg"  x="0px" y="0px"
                 width="270px" height="270px" viewBox="0 0 32 32" style="enable-background:new 0 0 32 32;" xml:space="preserve">
                <g>
                    <path class="arrowSpinningSvg mm-o-syncing-queue" d="M24.5,10.4L22.2,11l5,5L29,9.2l-2.4,0.7C24.4,6.1,20.4,3.8,16,3.8c-5.4,0-10.1,3.4-11.7,8.6
        c-0.2,0.5,0.1,1.1,0.7,1.3c0.1,0,0.2,0,0.3,0c0.4,0,0.8-0.3,1-0.7c1.3-4.3,5.2-7.2,9.7-7.2C19.5,5.8,22.7,7.5,24.5,10.4z"/>
                </g>
            </svg>
            <p class="working"><g:message code="loading.working"/>.</p>
        </div>
    </div>
    <div id="allChartsWrapper" style="display: none">
        <div class="mainGraphContainer inline-block">
            <g:if test="${drawTotalGraph}">
                <div id="total_results_container" class="chartContainer">
                    <div class="chart-wrapper" id="totalChart"></div>
                </div>
            </g:if>
            <g:if test="${entityByStageChart}">
                <div id="result_by_stage_container" class="chartContainer ${drawTotalGraph ? 'hidden':'' }">
                    <div class="chart-wrapper" id="stageChart"></div>
                </div>
            </g:if>
            <g:if test="${entityElementChart}">
                <div id="result_by_class_container" class="chartContainer hidden">
                    <div class="chart-wrapper" id="stageClassChart"></div>
                </div>
            </g:if>
            <g:if test="${entityCompareElementsChart}">
                <div id="result_by_class_group_container" class="chartContainer hidden">
                    <div class="chart-wrapper" id="classGroupChart"></div>
                </div>
            </g:if>
            <g:if test="${entityByStageElementChart}">
                <div id="element_compare_container" class="chartContainer hidden">
                    <div class="chart-wrapper" id="elementChart"></div>
                </div>
            </g:if>
        </div>
        <div class="side_controller inline-block">
            <div id="btnGroupsWrapper"></div>

        </div>
    </div>

</div>
<g:if test="${!isCalculationRunning}">
<script type="text/javascript">
    $(function () {
        renderMainEntityCharts('${entity?.id}', '${indicator?.indicatorId}', null, ${allValidIndicatorsAllTypesIds as grails.converters.JSON})
        $("#focalDivFourPack").find("li[role='presentation']").first().addClass("active")
    })
</script>
</g:if>
