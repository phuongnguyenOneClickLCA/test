<%@ page import="com.bionova.optimi.core.service.ResourceTypeService" %>
<%@ page import="com.bionova.optimi.core.service.OptimiResourceService" %>
<%
    ResourceTypeService resourceTypeService = grailsApplication.mainContext.getBean("resourceTypeService")
    OptimiResourceService optimiResourceService = grailsApplication.mainContext.getBean("optimiResourceService")
%>
<g:if test="${subType}">
    <g:set var="groupingKey" value="${subType.subType}"/>
    <g:set var="groupingName" value="${resourceTypeService.getLocalizedName(subType)}"/>
</g:if>
<g:elseif test="${classKey}">
    <g:set var="groupingKey" value="${classKey.replaceAll("[^A-Za-z0-9]", "")}"/>
    <g:set var="groupingName" value="${classKey}"/>

</g:elseif>
<g:if test="${groupingKey && groupingName}">
    <div class="container group_container" id="${groupingKey}_container">
        <button class="close pull-right" onclick="hideDivAndUncheck('${groupingKey}')">x</button>

        <div><h2>${groupingName}</h2> <a class="pull-right" href="#" onclick="$(this).scrollTop()"><i class="fa fa-anchor"></i> <g:message code="scroll_top"/></a> </div>

        <div class="card">
            <ul class="nav nav-tabs" role="tablist">
                <li role="presentation" class="navInfo active">
                    <a id="${groupingKey}LifeCycle" onclick="toggleActiveTab(this)" href="javascript:"><g:message
                            code="life_cycle_impact"/></a>
                </li>
                <li role="presentation" class="navInfo">
                    <a id="${groupingKey}Cost" onclick="toggleActiveTab(this)" href="javascript:"><g:message
                            code="life_cycle_impact_and_cost"/></a>
                </li>
                <li role="presentation" class="navInfo">
                    <a id="${groupingKey}Data" onclick="toggleActiveTab(this)" href="javascript:"><g:message
                            code="see_data"/></a>
                </li>
            </ul>
        </div>

        <div class="mainGraphContainer">
            <div id="${groupingKey}LifeCycle_container" class="chart-container">
                <div style="text-align: center"><h3><g:message code="life_cycle_impact"/>, ${calculationRule?.localizedName} ${unitCarbon}</h3></div>
                <div id="${groupingKey}LifeCycleChart">
                </div>
            </div>
            <div id="${groupingKey}Cost_container" class="chart-container hidden">
                <div style="text-align: center"><h3><g:message code="life_cycle_impact_and_cost"/></h3></div>
                <div id="${groupingKey}CostChart">
                </div>
            </div>
            <div id="${groupingKey}Data_container" class="chart-container hidden">
                <div style="text-align: center"><h3>${message(code:"data_summary")}</h3></div>
                <div>
                    <table class="table table-condensed table-striped">
                        <thead>
                        <tr>
                            <th><g:message code="resource.name"/></th>
                            <th><g:message code="query.quantity"/></th>
                            <g:each in="${stagesWithScores}" var="category">
                                <th>${category} <br> (${unitCarbon})</th>
                            </g:each>
                            <th>${message(code:"finance_cost")} <br> (${unitCost})</th>
                            <th>${message(code:"total_cost")} <br> (${unitCost})</th>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${resourceList}" var="resource">
                            <g:set var="resourceVal"
                                   value="${byResourceByCategory?.find { it.key?.contains(resource?.staticFullName) } ?: byResourceByCategory?.find { it.key?.contains(optimiResourceService.getLocalizedName(resource)) }}"/>
                            <g:if test="${resourceVal}">
                                <g:set var="random" value="${UUID.randomUUID().toString()}"/>
                                <tr>
                                    <td>
                                        ${abbr(value: resourceVal?.key, maxLength: 200) }
                                        <opt:renderDataCardBtn indicatorId="${indicator?.indicatorId}" resourceId="${resource?.resourceId}" profileId="${resource?.profileId}" childEntityId="${entity?.id}" showGWP="true" infoId="${random}" />

                                    </td>
                                    <td>
                                        ${resourceAndUnitQuantity?.get(resource.resourceId)}
                                    </td>
                                    <g:each in="${resourceVal?.value}" var="impact">
                                        <g:if test="${stagesWithScores?.contains(impact?.key)}">
                                            <td>${formatNumber(number:impact?.value, format:"###.##")}</td>
                                        </g:if>
                                    </g:each>
                                    <g:set var="financeCost" value="${byResourceByCategoryFinanceCost?.get(resource.resourceId)?.toDouble() ? formatNumber(number:byResourceByCategoryFinanceCost?.get(resource.resourceId), format:"###.##") : 0.0}"/>
                                    <td>${financeCost}</td>
                                    <g:set var="totalCost" value="${byResourceByCategoryFinanceCostAndTotal?.get(resource.resourceId)?.toDouble() ? formatNumber(number:byResourceByCategoryFinanceCostAndTotal?.get(resource.resourceId), format:"###.##") : 0.0}"/>
                                    <td>${totalCost}</td>
                                </tr>
                            </g:if>

                        </g:each>
                        </tbody>
                    </table>

                </div>

            </div>
        </div>

    </div>


    <script type="text/javascript">
        $(document).ready(function () {
            var imageURL = new URL("/app/assets/img/watermarkTransparent.png", document.baseURI).href
            renderHighchartStackedColumn('${groupingKey}', "${groupingKey}LifeCycleChart", '', ${datasetCarbon ? datasetCarbon as grails.converters.JSON : [] }, ${chartCategoriesCarbon as grails.converters.JSON},"${unitCarbon}", "${message(code: 'graph_no_data_displayed')}",imageURL)
            renderHighchartStackedColumn('${groupingKey}', "${groupingKey}CostChart", '', ${datasetCarbon ? datasetCarbon as grails.converters.JSON : [] }, ${chartCategoriesCarbon as grails.converters.JSON},"${unitCarbon}", "${message(code: 'graph_no_data_displayed')}",imageURL, ${datasetFinanceCost ? datasetFinanceCost as grails.converters.JSON : []}, ${datasetTotalCost ? datasetTotalCost as grails.converters.JSON : []}, "${unitCost}")
        })
    </script>

</g:if>

