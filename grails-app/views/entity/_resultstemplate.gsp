<g:set var="calculationRuleService" bean="calculationRuleService"/>
<g:set var="indicatorService" bean="indicatorService"/>
<div class="container section">
    <div class="sectionheader">
                <div class="sectioncontrols pull-right"${!differences?.get(indicator.indicatorId) ? ' style=\"display: none\"' : ''}>
                    <%--
                    <a href="javascript:;" onclick="showGraph('${params.entityId}', '${indicator ?.indicatorId}');" class="btn btn-primary"><g:message code="compare.progess_graph" /></a>
                    --%>
                    <g:if test="${indicator?.compareCalculationRule}">
                        <g:if test="${'operating' == childType}">
                            <%--<opt:link controller="entity" action="graphContainer" params="[indicatorId: indicator?.indicatorId, childType: childType, calculationRuleId: indicator.compareCalculationRuleObject?.calculationRuleId]" class="btn btn-primary"><g:message code="compare.progess_as_graphs" /></opt:link> --%>
                        </g:if>
                        <g:else>
                      <%--  <opt:link controller="entity" action="graphContainer" params="[indicatorId: indicator?.indicatorId, childType: childType, calculationRuleId: indicator.compareCalculationRuleObject?.calculationRuleId]" class="btn btn-primary"><g:message code="compare.progress_show_as_graphs" /></opt:link> --%>
                        </g:else>
                        <div class="btn-group" style="display: inline-block;">
                            <a href="#" data-toggle="dropdown" class="btn btn-primary dropdown-toggle">
                            <g:if test="${'operating' == childType}"><g:message code="compare.progress_button.operating" /><span class="caret"></span></g:if>
                            <g:else><g:message code="compare.progress_button.design" /><span class="caret"></span></g:else>
                            </a>
                            <ul class="dropdown-menu">
                            <li><a href="#"><g:message code="compare.percentual_difference" /></a></li>
                        <g:if test="${childEntities?.get(indicator.indicatorId)}">
                            <g:each in="${childEntities?.get(indicator.indicatorId)}" var="entity">
                                <li>
                                <opt:link controller="entity" action="showDifference" id="${entity?.id}" params="[indicatorId: indicator?.indicatorId, type: childType]">
                                    ${entity.operatingPeriodAndName}
                                </opt:link>
                                </li>
                            </g:each>
                        </g:if>
                            </ul>
                        </div>
                    </g:if>
                </div>
        <%--
                    <button class="pull-left sectionexpander"><i class="icon icon-chevron-down expander"></i><i class="icon icon-chevron-right collapser"></i></button>

        --%>
        <g:if test="${differences?.get(indicator.indicatorId)}">
            <button class="pull-left sectionexpander"><i class="icon icon-chevron-down expander"></i><i class="icon icon-chevron-right collapser"></i></button>
        </g:if>
        <g:else>
            <button class="pull-left sectionexpander"><i class="icon icon-chevron-down collapser"></i><i class="icon icon-chevron-right expander"></i></button>
        </g:else>
        <h2 class="h2expander">
            ${indicatorService.getLocalizedName(indicator)}
            <g:set var="compareCalculationRuleObj" value="${indicator?.compareCalculationRuleObject}"/>
            <g:if test="${calculationRuleService.getLocalizedUnit(compareCalculationRuleObj, indicator)}">
                (${calculationRuleService.getLocalizedUnit(compareCalculationRuleObj, indicator)})
            </g:if>
        </h2>
    </div>
        <g:if test="${indicator?.compareCalculationRule && compareView}">
            <div class="sectionbody"${!differences?.get(indicator.indicatorId) ? ' style="display: none"' : ''}>
                <table class="table table-striped table-condensed table-cellvaligntop nowrap wrapth">
                    <thead>
                    <tr>
                        <th style="width: 5%;"><g:message code="resultCategory.name" /></th>
                        <th style="width: 5%;" class="clarification"><g:message code="entity.show.task.description" /></th>
                        <g:if test="${childEntities?.get(indicator.indicatorId)}">
                        <g:each in="${childEntities?.get(indicator.indicatorId)}" var="childEntity">
                            <th class="text-right"${childEntity?.id.equals(benchmark?.id) && differences?.get(indicator?.indicatorId) ? ' style="border-top: 2px solid #000000; border-left: 2px solid #000000; border-right: 2px solid #000000;"' : ''}>
                                ${childEntity?.operatingPeriodAndName}
                                <g:if test="${childEntity?.id.equals(benchmark?.id) && differences?.get(indicator?.indicatorId)}">
                                    (<g:message code="compare.benchmark" />)
                                </g:if>
                            </th>
                        </g:each>
                        </g:if>
                     </tr>
                    </thead>

                    <tbody>
                    <g:each in="${indicator?.getResolveResultCategories(entity)}" var="resultCategory">
                        <tr>
                            <td>${resultCategory?.localizedName}</td>
                            <td class="clarification">
                        <g:if test="${childEntities?.get(indicator.indicatorId)}">
                            <calc:resultCategoryClarificationText resultCategory="${resultCategory}" entity="${childEntities?.get(indicator.indicatorId).first()}" childEntities="${childEntities?.get(indicator.indicatorId)}" />
                        </g:if>
                            </td>
                            <g:each in="${childEntities?.get(indicator.indicatorId) ? childEntities?.get(indicator.indicatorId):''}" var="childEntity">
                                <td class="text-right to-bold-result"${childEntity?.id.equals(benchmark?.id) && differences?.get(indicator?.indicatorId) ? ' style="border-left: 2px solid #000000; border-right: 2px solid #000000;"' : ''}>
                                    <calc:scoreByCalculationRuleAndResultCategory entity="${childEntity}" indicator="${indicator}" calculationRule="${indicator?.compareCalculationRuleObject}" resultCategory="${resultCategory}" />
                                    ${differences?.get(indicator.indicatorId)?.get(childEntity?.id)?.get(resultCategory)}
                                </td>
                            </g:each>
                        </tr>
                    </g:each>
                    <tr>
                        <th>&nbsp;</th><th><g:message code="totalScore" />
                        <g:set var="compareCalculationRuleObj" value="${indicator?.compareCalculationRuleObject}"/>
                        <g:if test="${calculationRuleService.getLocalizedUnit(compareCalculationRuleObj, indicator)}">
                            (${calculationRuleService.getLocalizedUnit(compareCalculationRuleObj, indicator)})
                        </g:if></th>
                        <g:if test="${childEntities?.get(indicator.indicatorId)}">
                        <g:each in="${childEntities?.get(indicator.indicatorId)}" var="childEntity">
                            <th class="text-right"${childEntity.id.equals(benchmark?.id) && totalDifferences?.get(indicator?.indicatorId) ? ' style="border-left: 2px solid #000000; border-right: 2px solid #000000;"' : ''}>
                                <calc:totalScoreByCalculationRule calculationRule="${indicator?.compareCalculationRuleObject}" entity="${childEntity}" indicator="${indicator}"/>
                                ${totalDifferences?.get(indicator?.indicatorId)?.get(childEntity.id)}
                            </th>
                        </g:each>
                        </g:if>
                    </tr>


                    <g:if test="${indicator?.denominatorList && (compoundAnswers || dynamicAnswers)}">
                        <tr>
                            <th colspan="2">&nbsp;</th>
                        <g:if test="${childEntities?.get(indicator.indicatorId)}">
                            <g:each in="${childEntities?.get(indicator.indicatorId)}" var="childEntity">
                                <th${childEntity?.id.equals(benchmark?.id) && totalDifferences?.get(indicator?.indicatorId) ? ' style="border-left: 2px solid #000000; border-right: 2px solid #000000;"' : ''}>&nbsp;</th>
                            </g:each>
                        </g:if>
                        </tr>
                        <tr>
                          <th>&nbsp;</th><th><g:message code="results.denominators" /></th>
                        <g:if test="${childEntities?.get(indicator.indicatorId)}">
                          <g:each in="${childEntities?.get(indicator.indicatorId)}" var="childEntity">
                              <th class="text-right"${childEntity?.id.equals(benchmark?.id) && totalDifferences?.get(indicator?.indicatorId) ? ' style="border-left: 2px solid #000000; solid #000000; border-right: 2px solid #000000;"' : ''}></th>
                          </g:each>
                        </g:if>
                          <g:each in="${compoundAnswers}" var="compoundAnswer" status="index">
                              <tr>
                                  <td>&nbsp;</td><td>${compoundAnswer.key}</td>
                              <g:if test="${childEntities?.get(indicator.indicatorId)}">
                                  <g:each in="${childEntities?.get(indicator.indicatorId)}" var="childEntity">
                                      <g:set var="bottomborder" value="${!dynamicAnswers && index == compoundAnswers.size() -1 ? 'border-bottom: 2px solid #000000;' : ''}" />
                                      <td class="text-right"${childEntity?.id.equals(benchmark?.id) && totalDifferences?.get(indicator?.indicatorId) ? ' style="' + bottomborder + 'border-left: 2px solid #000000; border-right: 2px solid #000000;"' : ''}>
                                          ${compoundAnswer.value?.get(childEntity)}
                                          ${compoundDifferences?.get(compoundAnswer.key)?.get(childEntity?.id)}
                                      </td>
                                  </g:each>
                              </g:if>
                              </tr>
                          </g:each>
                          <g:each in="${dynamicAnswers}" var="dynamicAnswer" status="index">
                              <tr>
                                  <td>&nbsp;</td><td>${dynamicAnswer.key}</td>
                                  <g:if test="${childEntities?.get(indicator.indicatorId)}">
                                  <g:each in="${childEntities?.get(indicator.indicatorId)}" var="childEntity">
                                      <g:set var="bottomborder" value="${index == dynamicAnswers.size() - 1 ? 'border-bottom: 2px solid #000000; ' : ''}" />
                                      <td class="text-right"${childEntity?.id.equals(benchmark?.id) && totalDifferences?.get(indicator?.indicatorId) ? ' style="' + bottomborder + 'border-left: 2px solid #000000; border-right: 2px solid #000000;"' : ''}>
                                          ${dynamicAnswer.value?.get(childEntity)}
                                          ${dynamicDifferences?.get(dynamicAnswer.key)?.get(childEntity?.id)}
                                      </td>
                                  </g:each>
                                  </g:if>
                              </tr>
                          </g:each>
                        </tr>
                    </g:if>
                    </tbody>
                </table>
            </div>
        </g:if>
        <g:else>
            <g:if test="${childEntities?.get(indicator.indicatorId)}">
         <g:each in="${childEntities?.get(indicator.indicatorId)}" var="childEntity">
            <div class="sectionbody"${!differences?.get(indicator.indicatorId) ? ' style=\"display: none\"' : ''}>
            <h3>${childEntity?.operatingPeriodAndName}</h3>
                <h2><span class="hide show-on-print">${indicatorService.getLocalizedResultsHeadline(indicator)}</span></h2>
                <g:if test="${!compareView}">
                    <p>${indicatorService.getLocalizedPurpose(indicator)}</p>
                </g:if>
                <div class="row-fluid">
                    <div class="span12">
                        <g:if test="${indicator.assessmentMethod == 'complex'}">
                            <g:if test="${indicator?.report}">
                                <calc:renderIndicatorReport indicator="${indicator}" report="${indicator.report}" entity="${childEntity}" parentEntity="${entity}" />
                            </g:if>
                            <g:else>
                                <g:render template="/entity/complexresulttable" model="[entity: childEntity, parentEntity: entity, indicator: indicator, hideBreakdown: true, compare: compareView]" />
                            </g:else>
                        </g:if>
                        <g:else>
                            <p><strong><g:message code="results.section_header" /></strong></p>

                            <table class="table table-striped table-condensed table-cellvaligntop">
                                <tbody>
                                <g:each in="${queries}" var="query">
                                    <g:each in="${query.sections}" var="section">
                                        <g:if test="${!section.skipInResultsView}">
                                            <tr rel="#">
                                                <td>${section?.localizedName}</td>
                                                <td>
                                                    <b><g:sectionScore entity="${childEntity}" indicatorId="${indicator?.indicatorId}" queryId="${query.queryId}" sectionId="${section.sectionId}" /></b>
                                                </td>
                                            </tr>
                                        </g:if>
                                    </g:each>
                                </g:each>
                                <tr>
                                    <td colspan="2">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td><g:message code="totalScore" /></td>
                                    <td></td>
                                </tr>
                                </tbody>
                            </table>
                        </g:else>

                    </div>
                </div>

                <g:if test="${additionalInfos}">
                    <div class="accessibility_lower">
                      <p><strong><g:message code="results.additional_info" /></strong></p>
                    <p>
                    <ul>
                        <g:each in="${additionalInfos}" var="additionalInfo">
                            <li>${additionalInfo}</li>
                        </g:each>
                    </ul>
                    </p>
                  </div>
                </g:if>

                <g:renderSourceListing indicator="${indicator}" entity="${childEntity}" />
            </div>
        </g:each>
            </g:if>
    </g:else>
</div>







