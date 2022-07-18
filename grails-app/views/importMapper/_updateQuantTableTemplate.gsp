<%@ page import="com.bionova.optimi.data.ResourceCache" %>
<g:set var="optimiResourceService" bean="optimiResourceService"/>
<g:if test="${datasetsForTable}">
    <table class="table table-condensed">
        <thead>
        <tr>
            <th></th>
            <th>${message(code:"importMapper.material")}</th>
            <th>${!multipleFound ? message(code:"query.old_quantity") : ''}</th>
            <th>${message(code:"query.new_quantity")}</th>
            <th>${message(code:"importMapper.CLASS_HEADER")}</th>
            <th>${message(code:"attachment.comment")}</th>
            <g:if test="${additionalQuestions}">
                <g:each in="${additionalQuestions}" var="question">
                    <th>
                        ${question?.localizedQuestion}
                    </th>
                </g:each>
            </g:if>
            <th>${message(code:"change")}</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <g:set var="resourceCache" value="${ResourceCache.init(datasetsForTable?.toList())}"/>
        <g:each in="${datasetsForTable}" var="dataset">
            <g:if test="${dataset}">
                <g:set var="orgDataset" value="${entityDatasets?.find({ it.manualId == allmatchesCases?.get(dataset.manualId)?.first() })}"/>
                <g:set var="oldVal" value="${orgDataset?.quantity}"/>
                <g:set var="hasMatches" value="${allmatchesCases && allmatchesCases.containsKey(dataset.manualId)}"/>
                <tr>
                    <td></td>
                    <td>
                        <input class="hidden" value="${dataset.manualId}" name="datasetId${dataset.manualId}">
                        <g:set var="resourceName" value="${optimiResourceService.getUiLabel(resourceCache.getResource(dataset))}" />
                        <a href="javascript:" class="just_black" rel="popover" data-trigger="hover" data-content="${resourceName}">
                            ${g.abbr(value: resourceName, maxLength: 100)}
                        </a>
                    </td>
                    <td>
                        <g:if test="${!multipleFound}">
                            <g:if test="${orgDataset}">
                                ${oldVal}
                                <g:if test="${orgDataset.userGivenUnit != null}">
                                    <span class="add-on">${orgDataset.userGivenUnit}</span>
                                </g:if>
                                <g:set var="thickness"
                                       value="${orgDataset.additionalQuestionAnswers?.get("thickness_mm") ? orgDataset.additionalQuestionAnswers.get("thickness_mm") : ''}"/>
                                ${thickness ? '/ ' + thickness + ' mm' : ''}
                            </g:if>
                            <g:else>
                                -
                            </g:else>
                        </g:if>
                    </td>
                    <td>
                        <input type="text" class="numeric input-mini isQuantity" ${locked ? "disabled='true'" : ""}
                               value="${dataset.quantity}" name="userGivenQuantity.${dataset.manualId}"/>
                        <g:if test="${dataset.userGivenUnit != null}">
                            <span class="add-on">${dataset.userGivenUnit}</span>
                        </g:if>
                        <g:set var="thickness"
                               value="${dataset.additionalQuestionAnswers?.get("thickness_mm") ? dataset.additionalQuestionAnswers.get("thickness_mm") : ''}"/>
                        ${thickness ? '/ ' + thickness + ' mm' : ''}
                    </td>
                    <td>
                        ${dataset.trainingData?.get("CLASS")?.toUpperCase()}
                    </td>
                    <td>
                        ${dataset.additionalQuestionAnswers?.get("comment")}
                    </td>
                    <g:if test="${additionalQuestions}">
                        <g:each in="${additionalQuestions}" var="question">
                            <g:if test="${question}">
                                <g:set var="newAnswerChoiceId" value="${dataset.additionalQuestionAnswers?.get(question.questionId)}"/>
                                <td>
                                    ${question?.choices?.find({it.answerId == newAnswerChoiceId})?.localizedAnswer}
                                </td>
                            </g:if>
                        </g:each>
                    </g:if>
                    <td>
                        <g:if test="${identified}">
                            <g:if test="${locked}">
                                ${message(code:"locked")}
                            </g:if>
                            <g:else>
                                <g:if test="${unchanged}">
                                    -
                                </g:if>
                                <g:elseif test="${orgDataset && entityDatasets}">
                                    <g:set var="percentageChange"
                                           value="${oldVal != null ? (dataset.quantity - oldVal) / oldVal * 100 : null}"/>
                                    <g:if test="${percentageChange != null}">
                                        ${percentageChange > 0 ? "+" : ""}${ percentageChange.round(1) } %
                                    </g:if>
                                </g:elseif>

                            </g:else>
                        </g:if>
                    </td>
                    <td>
                        <g:if test="${hasMatches && (!identified || (identified && !unchanged && !locked))}">
                            <a class="btn btn-default" onclick="showHideClass('hiddenRow${dataset.manualId}')">
                                <g:message code="show_match" args="[allmatchesCases.get(dataset.manualId)?.size()]"/>
                                <g:if test="${allmatchesCases.get(dataset.manualId)?.size() > 1}">
                                    <asset:image src="img/icon-warning.png" style="max-width:14px" rel="popover" data-toggle="hover" data-content="${g.message(code: "updateQuant.multiple_matches_found")}"/>
                                </g:if>
                                <span class="caret"></span>
                            </a>
                        </g:if>
                    </td>
                    <td class="tdalignmiddle">
                        <g:if test="${!identified || (identified && !unchanged && !locked) }">
                            <g:set var="datasetClass" value="${dataset?.trainingData?.get("CLASS")?.toUpperCase()}"/>
                            <a href="javascript:" class="btn btn-danger deleteDataset" id="deleteDataset${dataset.manualId}"
                               rel="popover" data-trigger="hover" data-content="${message(code:'importMapper.updateQuant.delete_quant_exp')}"
                               onclick="ingoreIfcDataset(this,'${dataset.manualId}','.hiddenRow${dataset.manualId}')">
                                <g:message code="delete"/>
                            </a>
                        </g:if>
                    </td>
                </tr>
                <g:if test="${hasMatches}">
                    <tr class="hiddenRow${dataset.manualId} table-warning" style="display: none">
                        <th></th>
                        <th>${message(code:"importMapper.material")}</th>
                        <th></th>
                        <th>${message(code:"query.quantity")}</th>
                        <th>${message(code:"importMapper.CLASS_HEADER")}</th>
                        <th>${message(code:"attachment.comment")}</th>
                        <g:if test="${additionalQuestions}">
                            <g:each in="${additionalQuestions}" var="question">
                                <th>
                                    ${question?.localizedQuestion}
                                </th>
                            </g:each>
                        </g:if>
                        <th>${message(code:"change")}</th>
                        <th>${message(code:"update_or_add_material")}
                            <i class="fas fa-question-circle" rel="popover" data-trigger="hover" data-content="${g.message(code: "update_or_add_material.help")}"></i>
                        </th>
                        <th></th>
                    </tr>
                    <g:set var="matchCases" value="${allmatchesCases.get(dataset.manualId)}"/>
                    <g:set var="preSelect" value="${matchCases && matchCases?.size() < 2 ? 'checked' : ''}"/>
                    <g:each in="${matchCases}" var="datasetOrgId" status="i">
                        <g:set var="datasetOrg" value="${entityDatasets?.find({it.manualId == datasetOrgId})}"/>
                        <tr class="hiddenRow${dataset.manualId} table-warning" style="display: none">
                            <td></td>
                            <td>
                                <input class="hidden" value="${datasetOrg.manualId}">
                                <g:set var="resourceName" value="${optimiResourceService.getUiLabel(resourceCache.getResource(datasetOrg))}" />
                                <a href="javascript:" class="just_black" rel="popover" data-trigger="hover" data-content="${resourceName}">
                                    ${g.abbr(value: resourceName, maxLength: 100)}
                                </a>
                            </td>
                            <td></td>
                            <td>
                                ${datasetOrg.quantity}
                                <g:if test="${datasetOrg.userGivenUnit != null}">
                                    <span class="add-on">${datasetOrg.userGivenUnit}</span>
                                </g:if>
                                <g:set var="thickness"
                                       value="${datasetOrg.additionalQuestionAnswers?.get("thickness_mm") ? datasetOrg.additionalQuestionAnswers.get("thickness_mm") : ''}"/>
                                ${thickness ? '/ ' + thickness + ' mm' : ''}
                            </td>
                            <td>
                                ${datasetOrg.persistedOriginalClass?.toUpperCase()}
                            </td>
                            <td>
                                ${datasetOrg.additionalQuestionAnswers?.get("comment") ?: ''}
                            </td>
                            <g:if test="${additionalQuestions}">
                                <g:each in="${additionalQuestions}" var="question">
                                    <g:if test="${question}">
                                        <g:set var="orgAnswerChoiceId" value="${datasetOrg.additionalQuestionAnswers?.get(question.questionId)}"/>
                                        <td>
                                            ${question?.choices?.find({it.answerId == orgAnswerChoiceId})?.localizedAnswer}
                                        </td>
                                    </g:if>
                                </g:each>
                            </g:if>
                            <td>
                                <g:if test="${datasetOrg && entityDatasets}">
                                    <g:set var="percentageChangeSubset"
                                           value="${datasetOrg.quantity != null ? (dataset.quantity - datasetOrg.quantity) / datasetOrg.quantity * 100 : null}"/>
                                    <g:if test="${percentageChangeSubset != null}">
                                        ${percentageChangeSubset > 0 ? "+" : ""}${ percentageChangeSubset.round(1) } %
                                    </g:if>
                                </g:if>
                            </td>
                            <td>
                                <input name="mappingId.${dataset.manualId}"
                                       class="checkbox${datasetOrg.manualId} updateQuantCheckbox" type="radio"
                                       value="${dataset.manualId}.${datasetOrg.manualId}" ${preSelect ? 'checked="checked"':''}
                                />
                            </td>
                            <td></td>
                        </tr>
                    </g:each>
                </g:if>
            </g:if>
        </g:each>
        </tbody>
    </table>
</g:if>