<li>
    <g:if test="${scoreValid && !design.disabledIndicators?.contains(indicator?.indicatorId) && !Boolean.TRUE.equals(design.resultsOutOfDate)}">
        <opt:link controller="design" action="results" class="disableWhenCalculationRunning ${isCalculationRunningForDesignIndicatorPair ? 'removeClicks' : ''}"
                  params="[childEntityId: design.id, indicatorId: indicator.indicatorId]">
            <i class="icon-results" style="font-size: 1.3em; vertical-align: inherit;"></i>
            <g:if test="${indicator.localizedResultReportLinkText}">
                ${indicator.localizedResultReportLinkText}
            </g:if>
            <g:else>
                <span style="display: inline-block; clear: both; white-space: nowrap;">
                    <strong><g:message
                        code="design.view.results"/>
                    </strong>
                </span>
                <g:if test="${!design.isIndicatorReady(indicator, queriesForIndicator.collect{it.queryId}) && !carbonDesignerAndResultsOnlyLicensed}">
                    <span style="color: #FF2D41">
                        (${g.message(code: "results.incomplete_queries")})
                    </span>
                </g:if>
            </g:else>
        </opt:link>
    </g:if>
    <g:elseif test="${design.disabledIndicators?.contains(indicator?.indicatorId)}">
        <opt:link controller="design" action="enableIndicatorForDesign" class="disableWhenCalculationRunning ${isCalculationRunningForDesignIndicatorPair ? 'removeClicks' : ''}"
                  params="[id: design.id, indicatorId: indicator.indicatorId]"><g:message
                code="design.enable.indicator"/>
        </opt:link>
    </g:elseif>
    <g:else>
        <opt:link onclick="openOverlay('loaderOverlay'); appendLoader('loaderOverlayContent');"
                  controller="entity" action="doComplexAssessment" class="disableWhenCalculationRunning ${isCalculationRunningForDesignIndicatorPair ? 'removeClicks' : ''}"
                  params="[childEntityId: design.id, indicatorId: indicator.indicatorId]">
            <i class="icon-alert"></i> <g:message code="entity.show.results_expired"/>
        </opt:link>
    </g:else>
</li>