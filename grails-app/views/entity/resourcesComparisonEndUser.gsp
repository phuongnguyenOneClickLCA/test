<%--
  Created by IntelliJ IDEA.
  User: trang
  Date: 8.9.2020
  Time: 15.27
--%>

<%@ page import="org.apache.commons.lang.StringEscapeUtils; com.bionova.optimi.core.domain.mongo.Indicator" trimDirectiveWhitespaces="true" %>
<%@ page import="static com.bionova.optimi.core.domain.mongo.FrameStatus.TAB.WORKFLOW as TAB_WORKFLOW" %>
<%@ page import="static com.bionova.optimi.core.domain.mongo.FrameStatus.TAB.HELP as TAB_HELP" %>
<%@ page import="com.bionova.optimi.core.service.ResourceTypeService" %>
<%
    def userService = grailsApplication.mainContext.getBean("userService")
    ResourceTypeService resourceTypeService = grailsApplication.mainContext.getBean("resourceTypeService")
%>
<!DOCTYPE html>
<%-- MAIN SCREEN --%>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="format-detection" content="telephone=no"/>
    <asset:javascript src="highcharts.js"/>
    <asset:javascript src="highcharts-more.js"/>
    <asset:javascript src="no-data-to-display.js"/>
    <asset:javascript src="drilldown.js"/>
    <asset:javascript src="exporting.js"/>
    <asset:javascript src="export-data.js"/>
    <asset:javascript src="moment.js"/>
    <asset:javascript src="xlsx.full.min.js"/>
    <asset:javascript src="offline-exporting.js"/>
    <asset:javascript src="no-data-to-display.js"/>
    <asset:javascript src="exportxlsx.js"/>
</head>

<body>
<g:set var="currentUser" value="${userService.getCurrentUser()}"/>
<g:set var="pageRenderTime" value="${System.currentTimeMillis()}"/>
<g:set var="channelFeature" value="${session?.channelFeature}"/>
<g:set var="usableEntityClassesForUser" value="${userService.getUsableEntityClasses(currentUser)}"/>
<g:set var="usableTrialEntityClassesForUser" value="${userService.getUsableTrialEntityClasses()}"/>
<g:set var="pageHeading" value="${message(code: "compare_resources")}"/>
<div>
    <div id="mainContent">
        <g:set var="pageRenderTime" value="${System.currentTimeMillis()}"/>
        <div class="container">
        <opt:breadcrumbsNavBar specifiedHeading="${pageHeading}" parentEntity="${parentEntity}" />
        <div class="pull-right">
            <a href="#" onclick="window.close('','_parent','');" class="btn btn-defaut"><g:message code="close"/></a>
            <opt:link controller="query" class="btn btn-primary" action="form"
                      params="[indicatorId: indicator?.indicatorId, childEntityId: entity?.id, entityId: parentEntity?.id, queryId: 'materialSpecifier']">${message(code: "edit_data")}</opt:link>
        </div>


        <h2 class="h2heading">
            <i class="fa fa-home fa-2x"></i>  ${pageHeading}
        </h2>
    </div>
        <g:if test="${subtypes || byResourceByClass}">
            <div class="container">

                <div class="container group_container">
                    <g:if test="${subtypes}">
                        <div class="padding-5px">
                            <span class="bold"><g:message code="group_by_subtype"/></span><br>
                            <g:each in="${subtypes}" var="subType" status="i">
                                <g:set var="value" value="${resourcesBySubTypes?.get(subType?.subType)}"/>
                                <g:if test="${subType && value}">
                                    <label class="col-4"><input type="checkbox" id="${subType?.subType}" class="subTypeCheckBox"
                                                                onchange="displayResultForGroup(this)"
                                                                value="${subType?.subType}"  ${value?.size() > 1 ? 'checked="checked"' : ''}/>  ${resourceTypeService.getLocalizedShortName(subType)} (${value?.size()})
                                    </label>
                                </g:if>

                            </g:each>
                        </div>
                    </g:if>
                    <div class="clearfix"></div>
                    <g:if test="${byResourceByClass}">
                        <div class="padding-5px">
                            <g:if test="${allAddQGrouping && allAddQGrouping?.size() > 0}">
                                <span class="bold"><g:message code="group_by_class"/>: <g:each in="${allAddQGrouping}" var="addQ" status="i">${addQ.localizedQuestion}${i+1 == allAddQGrouping.size() ? "" :", "}</g:each>
                                </span><br>
                            </g:if>

                            <g:each in="${byResourceByClass}" var="mapPerAddQ" status="i">
                                <g:set var="key" value="${(String) mapPerAddQ?.key}"/>
                                <g:set var="value" value="${mapPerAddQ?.value}"/>
                                <g:if test="${key && value}">
                                    <label class="col-4"><input type="checkbox" id="${key?.replaceAll("[^A-Za-z0-9]", "")}"
                                                                class="subTypeCheckBox"
                                                                onchange="displayResultForGroup(this)"
                                                                value="${key?.replaceAll("[^A-Za-z0-9]", "")}" ${value?.size() > 1 ? 'checked="checked"' : ''} />  ${key} (${value?.size()})
                                    </label>
                                </g:if>

                            </g:each>
                        </div>
                    </g:if>

                </div>


                <g:if test="${subtypes}">
                    <div id="charts_container">
                        <g:each in="${subtypes}" var="subType" status="i">
                            <g:set var="hiddenClass" value="${i > 0}"/>
                            <g:set var="chartCategoriesCarbon" value="${graphLabelsForSubtypeCarbon?.get(subType?.subType)}"/>
                            <g:if test="${chartCategoriesCarbon && subType}">
                                <g:render template="containerChartCompare"
                                          model="[entity: entity, calculationRule:calculationRule,datasetCarbon: graphDatasetForSubtypeCarbon?.get(subType?.subType),datasetFinanceCost:graphDatasetForSubtypeFinanceCost?.get(subType?.subType),datasetTotalCost: graphDatasetForSubtypeTotalCost?.get(subType?.subType), subType: subType, chartCategoriesCarbon: chartCategoriesCarbon, byResourceByCategory: byResourceByCategory,byResourceByCategoryFinanceCostAndTotal:byResourceByCategoryFinanceCostAndTotal,byResourceByCategoryFinanceCost:byResourceByCategoryFinanceCost, resourceList: resourcesBySubTypes?.get(subType?.subType), stagesWithScores: categoriesWithScores, indicator: indicator, unitCarbon:unitCarbon, unitCost:unitCost,resourceAndUnitQuantity:resourceAndUnitQuantity]"/>
                            </g:if>
                           </g:each>

                    </div>
                </g:if>
                <g:if test="${byResourceByClass}">
                    <div id="charts_container">
                        <g:each in="${byResourceByClass}" var="mapPerAddQ" status="i">
                            <g:set var="key" value="${(String) mapPerAddQ?.key}"/>
                            <g:set var="value" value="${(List) mapPerAddQ?.value}"/>
                            <g:set var="chartCategoriesCarbon" value="${graphLabelsForClassCarbon?.get(key)}"/>
                            <g:if test="${chartCategoriesCarbon && key && value}">
                                <g:render template="containerChartCompare"
                                          model="[entity: entity, calculationRule:calculationRule,datasetCarbon: graphDatasetForClassCarbon?.get(key),datasetFinanceCost:graphDatasetForClassCarbonFinanceCost?.get(key), datasetTotalCost:graphDatasetForClassCarbonTotalCost?.get(key),classKey: key,chartCategoriesCarbon: chartCategoriesCarbon, byResourceByCategory: byResourceByCategory,byResourceByCategoryFinanceCostAndTotal:byResourceByCategoryFinanceCostAndTotal,byResourceByCategoryFinanceCost:byResourceByCategoryFinanceCost, resourceList: byResourceByClass?.get(key), stagesWithScores: categoriesWithScores, indicator: indicator, unitCarbon:unitCarbon, unitCost:unitCost,resourceAndUnitQuantity:resourceAndUnitQuantity]"/>
                            </g:if>
                        </g:each>

                    </div>
                </g:if>

            </div>
        </g:if>
        <g:else>
            <div class="container text-center">
                <h3>${message(code:"no_data.add_compare")}</h3>
            </div>
        </g:else>

    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        $(".subTypeCheckBox").each(function () {
            displayResultForGroup(this,true)
        })
    })

    function displayResultForGroup(element, disableScrolling) {
        var subtype = $(element).val()
        var chartId = "#" + subtype + "_container"
        if ($(element).is(":checked")) {
            $(chartId).removeClass("hidden")
            if(!disableScrolling){
                scrollToElement($(chartId), true)
            }
        } else {
            $(chartId).addClass("hidden")
        }
    }

    function hideDivAndUncheck(checkboxId) {
        var checkbox = $("#" + checkboxId)
        var div = $("#" + checkboxId + "_container")
        $(checkbox).prop("checked", false)
        $(div).addClass("hidden")
    }
</script>
</body>
</html>