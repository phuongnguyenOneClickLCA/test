<!doctype html><%@ page import="com.bionova.optimi.core.service.ResourceTypeService" %>
<%
    ResourceTypeService resourceTypeService = grailsApplication.mainContext.getBean("resourceTypeService")
%>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<div class="container">
    <div class="screenheader">

    </div>
</div>
<div class="container section">
    <div class="container section">
        <strong><a href="javascript:" class="btn btn-primary" id="showHiddenTableRows" style="margin-bottom: 15px;">Show incomparable resourceType rows</a></strong>
        <table class="resource table table-striped table-condensed" id="resourceList">
            <g:if test="${benchmarkSubTypes}"><%--
            --%><tr id="resourceListHeading">
                <th>ResourceType</th>
                <th>StandardUnit</th>
                <th>Resources</th>
                <g:each in="${impactCategories}" var="impactCategory"><th>${impactCategory}</th></g:each>
            </tr><%--
       --%><g:each in="${benchmarkSubTypes}" var="resourceSubType"><%--
            --%>
                <g:if test="${resourceSubType.resourceAveragesByUpstreamDBClassified}">
                    <g:each in="${resourceSubType.resourceAveragesByUpstreamDBClassified}" var="resourceAveragesByUpstreamDBClassified">
                        <g:if test="${resourceAveragesByUpstreamDBClassified.value}">
                            <tr class="resourceSubType ${resourceSubType.resourceAveragesByUpstreamDBClassified.size() < 2 ? 'hiddenTableRow' : ''}">
                                <td nowrap>${resourceSubType.subType} (${resourceAveragesByUpstreamDBClassified.key?.toString()})</td>
                                <td>${resourceSubType.standardUnit}</td>
                                <td style="text-align: right;">${resourceSubType.resourceAveragesByUpstreamDBClassifiedAmounts?.get(resourceAveragesByUpstreamDBClassified.key?.toString())}</td>
                                <g:each in="${impactCategories}" var="impactCategory">
                                    <td style="text-align: right;"><g:formatNumber number="${resourceTypeService.getAverageByImpactCategory(resourceAveragesByUpstreamDBClassified.key?.toString(), impactCategory, resourceAveragesByUpstreamDBClassified)}" format="0.#E0" /></td>
                                </g:each>
                            </tr>
                        </g:if>
                    </g:each>
                    <tr class="${resourceSubType.resourceAveragesByUpstreamDBClassified.size() < 2 ? 'hiddenTableRow' : ''}">
                        <td nowrap style="border-bottom: 2px solid black;"><strong>${resourceSubType.subType} ((Gabi-Eco)/Eco)</strong></td>
                        <td style="border-bottom: 2px solid black;">&nbsp;</td>
                        <td style="border-bottom: 2px solid black; text-align: right;">${resourceSubType.resourceAveragesByUpstreamDBClassifiedAmounts.values().sum()} / <a href="javascript:" onclick="window.open('${createLink(action: "showResources", params:[resourceType: resourceSubType.resourceType, subType: resourceSubType.subType])}','showResourcesWindow', 'width=1024, height=768, scrollbars=1');">${resourceTypeService.getResourceSubTypeCount(resourceSubType)}</a></td>
                        <g:each in="${impactCategories}" var="impactCategory">
                            <td style="border-bottom: 2px solid black; text-align: right;">${resourceTypeService.getImpactCategoryDifference(impactCategory, resourceSubType.resourceAveragesByUpstreamDBClassified)}</td>
                        </g:each>
                    </tr>
                </g:if>
            <%--
                --%></g:each><%--
            --%></g:if><%--
        --%></table>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        $('.hiddenTableRow').hide();
    });

    $('#showHiddenTableRows').on('click', function (event) {
        if ($(this).text().indexOf("Show") >= 0) {
            $(this).text("Hide incomparable resourceType rows");
        } else {
            $(this).text("Show incomparable resourceType rows");
        }
        $('.hiddenTableRow').toggle();
    });
</script>
</body>
</html>

