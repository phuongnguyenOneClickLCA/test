<li class="nowrap" style="padding-bottom: 0px;">
    <g:if test="${!isLocked}">
        <g:if test="${showResultReports && carbonDesignerAndResultsOnlyLicensed}">
            <g:render template="/entity/viewResults"/>
        </g:if>
        <g:elseif test="${!showResultReports}">
            <li class="nowrap">
                <span class="resultReport_disabled">
                    <g:message code="results.no_license"/>
                </span>
            </li>
        </g:elseif>
        <opt:link controller="design" action="simulationTool" params="[entityId:entity.id, indicatorId: indicator.indicatorId, designId: design.id ]"
                  class="disableWhenCalculationRunning ${isCalculationRunningForDesignIndicatorPair ? 'removeClicks' : ''}">
            <g:set var="isDraftPresent" value="${draftByDesign?.get(design.id?.toString())}" />
            <g:if test="${isDraftPresent}">
                <img style="width: 20px;  margin-left: -5px; overflow: visible;" src="/app/assets/simulationTool_icon/simulationTool_icon_green.png">
                <g:message code="simulationTool.secondPage.heading"/>
            </g:if>
            <g:else>
                <img style="width: 20px;  margin-left: -5px; overflow: visible;" src="/app/assets/simulationTool_icon/simulationTool_icon.png">
                <g:message code="simulationTool.firstPage.heading"/>
            </g:else>
        </opt:link>
    </g:if>
    <g:else>
        <a class="disabled">
            <g:set var="isDraftPresent" value="${draftByDesign?.get(design.id?.toString())}" />
            <g:if test="${isDraftPresent}">
                <img style="width: 20px;  margin-left: -5px; overflow: visible;" src="/app/assets/simulationTool_icon/simulationTool_icon_green.png">
                <g:message code="simulationTool.secondPage.heading"/>
                <g:if test="${isLocked || indicator?.preventChanges(query)}"><i class="fa fa-lock pull-right grayLink"></i></g:if>
            </g:if>
            <g:else>
                <img style="width: 20px;  margin-left: -5px; overflow: visible;" src="/app/assets/simulationTool_icon/simulationTool_icon.png">
                <g:message code="simulationTool.firstPage.heading"/>
                <g:if test="${isLocked || indicator?.preventChanges(query)}"><i class="fa fa-lock pull-right grayLink"></i></g:if>
            </g:else>
        </a>
    </g:else>
</li>