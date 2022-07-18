<%
    def userService = grailsApplication.mainContext.getBean("userService")
%>
<%@ page trimDirectiveWhitespaces="true" %>
<g:set var="designs" value ="${designs?.findAll{ it && !it.deleted }}" />
<g:set var="designsNotHidden" value ="${designs?.findAll{!it.isHiddenDesign} ?: []}" />
<g:if test="${selectedDesignIndicators}">
    <div class="container section">
        <div class="sectionheader">
            <div class="sectioncontrols pull-right">
            <g:if test="${designParameterQueries?.size() > 0 && designs.size() >= 1}">
                <div class="btn-group">
                    <g:set var="allParamsReady" value="${entity?.getIsAllParameterQueriesReady(designParameterQueries.findAll({!it.value}).collect({ it?.key?.queryId }))}" />
                    <a href="javascript:" class="btn ${allParamsReady ? 'btn-primary ' : 'btn-danger '} dropdown-toggle" data-toggle="dropdown"><i class="fa fa-cog icon-white" style="font-size: 1.23em;" aria-hidden="true"></i> <g:message code="lcc_parameters"/> <span class="caret"></span> </a>
                    <ul class="dropdown-menu">
                        <g:each in="${designParameterQueries?.keySet()}" var="query">
                            <li><opt:link controller="query" action="form" params="[queryId:query?.queryId,projectLevel:true,designLevelParamQuery:true]">
                            <g:if test="${entity?.queryReady?.get(query.queryId)}">
                                <i class="icon-done"></i><span class="hide">Ready</span><g:message code="query_prefix" /> ${query.localizedName}
                            </g:if>
                            <g:else>
                                <i class="icon-incomplete"></i><span class="hide">Incomplete</span> <g:message code="query_prefix" /> ${query.localizedName}
                            </g:else>
                            </li></opt:link>
                        </g:each>
                    </ul>
                </div>
            </g:if>
            </div>
            <g:set var="args">${designs ? designs.size() : 0}</g:set>
            <h2 class="h2expander" data-name="design" style="margin-left: 15px;">
                <g:message code="entity.show.designs.title" args="[args]" />
                <g:if test="${designs && (designsNotHidden && (designsNotHidden.size() != designs.size()) || !designsNotHidden)}">
                    <span class="help-block-inline">${message(code:'hidden_designs_subtitle', args: [designs?.findAll{it.isHiddenDesign}?.size()])}</span>
                </g:if>
            </h2>
        </div>

        <div class="sectionbody">
            <g:if test="${selectedDesignIndicators && designs}">
                <table cellspacing="0" class="table">
                    <thead>
                    <tr>
                        <th class="design"><g:message code="entity.show.indicator" /></th>
                        <th class="design"><g:message code="entity.show.unit" /></th>
                        <g:each in="${designsNotHidden}" var="design">
                            <th class="design">
                                <a class="dropdown-toggle btn btn-link" href="#" rel="popover" data-trigger="hover" data-content="${design.operatingPeriodAndName}${design.targetedNote ? '<br /><br />' + design.targetedNote : ''}" data-html="true"><g:abbr maxLength="20" value="${design.operatingPeriodAndName}" />${design.targetedNote ? ' !': ''}</a>
                            </th>
                        </g:each>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${selectedDesignIndicators}" var="indicator">
                        <tr rel="#" class="${"benchmark".equalsIgnoreCase(indicator.visibilityStatus) && !userService.getShowBenchmarkIndicators(user) ? 'hidden': 'isv'}">
                            <td class="design">${indicator.localizedShortName ? indicator.localizedShortName : indicator.localizedName}${indicator.deprecated ? ' <span class=\"warningRed\">' + message(code: 'deprecated') + '</span>': ''}</td>
                            <td class="design">${indicator.displayUnit}</td>
                            <g:each in="${designsNotHidden}" var="design">
                                <td class="design">
                                    <div class="btn-group">
                                        <g:set var="scoreValid" value="${calc.areResultsValid(entity: design, indicator: indicator)}" />
                                        <a class="dropdown-toggle smallaction" href="#" data-toggle="dropdown"${pageScope.dataMissing ? ' style=\"color: #FF2D41;\"' : ''}>
                                            <g:if test="${scoreValid}"><calc:renderDisplayResult entity="${design}" indicator="${indicator}" /></g:if><g:else><g:message code="recalculate" /></g:else>
                                            <span class="caret"></span></a>
                                        <ul class="dropdown-menu">
                                            <g:if test="${showResultReports}">
                                            <li>
                                                <opt:link controller="design" action="results" params="[childEntityId: design.id, indicatorId: indicator.indicatorId]"><i class="icon-results"></i>
                                                    <g:if test="${indicator.localizedResultReportLinkText}">
                                                        ${indicator.localizedResultReportLinkText}
                                                    </g:if>
                                                    <g:else>
                                                        <g:message code="design.results" />
                                                    </g:else>
                                                </opt:link>
                                            </li>

                                            <g:set var="queriesForIndicator" value="${queriesForSelectedDesignIndicators.get(indicator.indicatorId)}"/>
                                            <g:if test="${queriesForIndicator}"><%--
                                             --%><g:each in="${queriesForIndicator}" var="query"><%--
                                             --%><g:if test="${!designParameterQueries?.keySet()?.collect({ it.queryId })?.contains(query.queryId)}"><%--
                                                 --%><li class="nowrap"><%--
                                                     --%><opt:link controller="query" action="form" params="[indicatorId: indicator.indicatorId, childEntityId: design.id, queryId: query.queryId, entityName: entity.name, childName: design.name]"><%--
                                                         --%><g:if test="${design.resolveQueryReady(indicator, query.queryId)}"><%--
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
                                                --%></g:if><%--
                                            --%></g:each><%--
                                        --%></g:if><%--
                                        --%><g:else><%--
                                            --%><li><g:message code="design.no_indicator_queries" /></li><%--
                                        --%></g:else>
                                            </g:if>
                                            <g:else>
                                                <li class="nowrap"><a class="no_highlight"><g:message code="entity.licenses_expired"/></a></li>
                                            </g:else>
                                        </ul>
                                    </div>
                                </td>
                            </g:each>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </g:if>
        <%--
        <g:else>
            <div class="well well-large text-center">
                <g:message code="entity.show.no_children" />
                <opt:link controller="design" action="newDesign" class="btn btn-large btn-primary hide-on-print"><g:message code="start" /></opt:link>
            </div>
        </g:else>
        --%>
        </div>
    </div>
</g:if>