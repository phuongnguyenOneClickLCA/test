<%@ page import="static com.bionova.optimi.core.service.SimulationToolService.conversionUnit as conversionUnit" %>
<%@ page import="static com.bionova.optimi.core.service.SimulationToolService.conversionUnitFactor as conversionUnitFactor" %>
<g:set var="simulationToolService" bean="simulationToolService"/>
<style>

    <%-- ???? --%>
    .carbonTable td {
        vertical-align: bottom;
    }

    .tr-background th{
        background: lightgrey;
        text-align: left;
        padding: 2px;
    }

    .buildingTypeTable td {
        <%--text-align: right;--%>
    }

</style>


<script>

    onShow('foundations', ${foundationsCheck});
    onShow('groundSlab', ${groundSlabCheck});
    onShow('enclosure', ${enclosureCheck});
    onShow('structure', ${structureCheck});
    onShow('finishes', ${finishesCheck});
    onShow('services', ${servicesCheck});
    onShow('externalAreas', ${externalAreasCheck});
    onShow('defaults', ${defaultsCheck})

</script>

<h2><g:message code="simulationTool.buildingStructure" /></h2>

<label><g:message code="simulationTool.editArea" /></label>

<table class="carbonTable">
    <%--FOUNDATION SECTION style="display: none;" --%>
    <g:set var="lastRenderedHeading" value="${null}" />
    <g:each in="${orderedConstructionGroupList}" var="constructionGroup">
        <g:if test="${!lastRenderedHeading||(lastRenderedHeading && !lastRenderedHeading.equals(constructionGroup.buildingElement))}">
            <g:set var="lastRenderedHeading" value="${constructionGroup.buildingElement}" />
            <tr class="${constructionGroup.classForUi} tr-background">
                <th colspan="3"><g:message code="${constructionGroup.buildingElement}" dynamic="true"/></th>
            </tr>
        </g:if>

        <tr class="${constructionGroup.classForUi} areaInputRow ${constructionGroup.groupId}Input"${allowedGroupsForRegion?.contains(constructionGroup.groupId) ? "" : " style=\"display: none;\""}>
            <td><label for="${constructionGroup.groupId}"><%--
            --%><g:if test="${constructionGroup.localizedName}"><%--
                --%>${constructionGroup.localizedName}<%--
            --%></g:if><%--
            --%><g:else><%--
                --%><g:message code="${constructionGroup.groupId}" dynamic="true"/><%--
            --%></g:else><%--
            --%><g:if test="${constructionGroup.questionMarkCode}"><%--
                --%><a style="margin-left: 3px;" href="#" class="cdInfoPopover" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${g.message(code: "${constructionGroup.questionMarkCode}", dynamic: true)}"><%--
                    --%><i class="icon-question-sign"></i><%--
                --%></a><%--
            --%></g:if><%--
        --%></label>
            </td>
            <td><input class="numeric ${constructionGroup.buildingElement}Input allowZero" type="text" id="${constructionGroup.groupId}" name="${constructionGroup.groupId}"  value="${simulationToolService.roundToNumber((calculatedAreas.get(constructionGroup.groupId)?:0) * conversionUnitFactor(unitSystem, constructionGroup.unit))}"/></td>
            <td><label for="${constructionGroup.groupId}">${conversionUnit(unitSystem, constructionGroup.unit)}</label></td>
        </tr>
    </g:each>
</table>

<script>

    $(function () {
        $('.cdInfoPopover').popover({
            placement : 'top',
            template: '<div class="popover" style=" display: block; max-width: 350px;" ><div class="arrow"></div><div class="popover-content" style="border-style: solid; border-color: whitesmoke; border-width: 1px"></div></div>'
        });
    })

</script>