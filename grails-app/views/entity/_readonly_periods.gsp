<%@ page trimDirectiveWhitespaces="true" %>
<g:set var="indicatorService" bean="indicatorService"/>
<g:if test="${selectedOperatingIndicators}">
    <div class="container section">
        <div class="sectionheader">
            <button class="pull-left sectionexpander" data-name="period"><i class="icon icon-chevron-down expander"></i><i class="icon icon-chevron-right collapser"></i></button>
            <g:set var="args">${operatingPeriods ? operatingPeriods.size() : 0}</g:set>
            <h2 class="h2heading" style="margin-left: 15px;"><%--<i class="layout-icon-graph"></i>--%><g:message code="entity.show.periods.title" args="[args]" /></h2>
        </div>

        <div class="sectionbody">
            <g:if test="${selectedOperatingIndicators && operatingPeriods}">
                <table cellspacing="0" class="table">
                    <thead>
                    <tr>
                        <th class="design"><g:message code="entity.show.indicator" /></th>
                        <th class="design"><g:message code="entity.show.unit" /></th>
                        <g:each in="${operatingPeriods}" var="operatingPeriod">
                            <th class="design">
                                <a class="dropdown-toggle btn btn-link" href="#" rel="popover" data-trigger="hover" data-content="${operatingPeriod.operatingPeriodAndName}${operatingPeriod.targetedNote ? '<br /><br />' + operatingPeriod.targetedNote : ''}" data-html="true"><g:abbr maxLength="20" value="${operatingPeriod.operatingPeriodAndName}" />${operatingPeriod.targetedNote ? ' !' : ''}</a>
                            </th>
                        </g:each>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${selectedOperatingIndicators}" var="indicator">
                        <tr rel="#" class="">
                            <td class="design">${indicatorService.getLocalizedShortName(indicator)}${indicator.deprecated ? ' <span class=\"warningRed\">' + message(code: 'deprecated') + '</span>': ''}</td>
                            <td class="design">${indicatorService.getDisplayUnit(indicator)}</td>
                            <g:each in="${operatingPeriods}" var="operatingPeriod">
                                <td class="design">
                                    <div class="btn-group"><%--
                                    --%><g:set var="scoreValid" value="${calc.areResultsValid(entity: operatingPeriod, indicator: indicator)}" />
                                        <a class="dropdown-toggle smallaction" href="#" data-toggle="dropdown"${pageScope.dataMissing ? ' style=\"color: #FF2D41;\"' : ''}>
                                        <g:if test="${scoreValid}"><calc:renderDisplayResult entity="${operatingPeriod}" indicator="${indicator}" /></g:if><g:else><g:message code="recalculate" /></g:else>
                                        <span class="caret"></span></a>
                                        <ul class="dropdown-menu">
                                          <g:if test="${showResultReports}">
                                              <li>
                                                <opt:link controller="operatingPeriod" action="results" params="[childEntityId: operatingPeriod.id, indicatorId: indicator.indicatorId]"><i class="icon-results"></i>
                                                    <g:if test="${indicatorService.getLocalizedResultReportLinkText(indicator)}">
                                                        ${indicatorService.getLocalizedResultReportLinkText(indicator)}
                                                    </g:if>
                                                    <g:else>
                                                        <g:message code="design.results" />
                                                    </g:else>
                                                </opt:link>
                                            </li>
                                            </g:if><g:else>
                                            <li class="nowrap"><span class="resultReport_disabled"><i class="icon-results"></i> <g:message code="results.no_license"/></span></li>
                                        </g:else>
                                            <g:set var="queriesForIndicator" value="${queriesForSelectedDesignIndicators.get(indicator.indicatorId)}"/>
                                            <g:if test="${queriesForIndicator}"><%--
                                             --%><g:each in="${queriesForIndicator}" var="query"><%--
                                                 --%><li class="nowrap"><%--
                                                     --%><opt:link controller="query" action="form" params="[indicatorId: indicator.indicatorId, childEntityId: operatingPeriod.id, queryId: query.queryId, entityName: entity.name, childName: operatingPeriod.operatingPeriodAndName]"><%--
                                                         --%><g:if test="${operatingPeriod.resolveQueryReady(indicator, query)}"><%--
                                                             --%><i class="icon-done"></i><span class="hide">Ready</span><%--
                                                             --%> <g:message code="query_prefix" /> ${query.localizedName}<%--
                                                         --%></g:if><%--
                                                         --%><g:else><%--
                                                             --%><g:if test="${indicator.isQueryOptional(query.queryId)}"><%--
                                                                 --%><i class="icon-">&#9675;</i><%--
                                                                --%> <g:message code="query_prefix" /> ${query.localizedName}<%--
                                                             --%></g:if><%--
                                                             --%><g:else><%--
                                                                 --%><i class="icon-incomplete"></i><span class="hide">Incomplete</span> <g:message code="query_prefix" /> ${query.localizedName}<%--
                                                             --%></g:else><%--
                                                         --%></g:else><%--
                                                    --%></opt:link><%--
                                                --%></li><%--
                                            --%></g:each><%--
                                        --%></g:if><%--
                                        --%><g:else><%--
                                            --%><li><g:message code="design.no_indicator_queries" /></li><%--
                                        --%></g:else>
                                        </ul>
                                    </div>
                                </td>
                            </g:each>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </g:if>
        </div>
    </div>
</g:if>