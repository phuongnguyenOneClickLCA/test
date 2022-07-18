<%@ page import="com.bionova.optimi.core.Constants" %>
<g:if test="${constructionResource && (additionalQuestion?.dataByConstituent||additionalQuestion?.disableEditingForConstruction)}">
    <span class="${additionalQuestion?.questionId}">&nbsp;</span>
</g:if>
<g:else>
    <g:set var="questionIdsToShowExtraSpace" value="[Constants.TRANSPORT_RESOURCE_QUESTIONID, Constants.TRANSPORT_RESOURCE_LEG2_QUESTIONID]"/>
    ${(!constructionResource && questionIdsToShowExtraSpace.contains(additionalQuestion?.questionId)) ? '&nbsp;' : ''}

    <g:set var="additionalfieldName" value="${mainSectionId}.${mainQuestion?.questionId}_additional_${additionalQuestion?.questionId}${resourceId ? '.' + resourceId : ''}" />
    <g:set var="additionalfieldId" value="${random}${mainSectionId}.${mainQuestion?.questionId}_additional_${additionalQuestion?.questionId}" />

    <g:if test="${importMapperAdditionalQuestion}">
        <g:set var="additionalfieldName" value="additional_${additionalQuestion?.questionId}.${datasetId}"/>
        <g:set var="additionalfieldId" value="additional_${additionalQuestion?.questionId}.${datasetId}" />
    </g:if>

    <g:set var="answer" value="${aq.additionalQuestionAnswer(additionalQuestionAnswer: groupedAdditionalQuestionAnswer, additionalQuestion: additionalQuestion, resource: resource, entity: entity)}" />

    <g:if test="${additionalQuestion?.inputType == 'hidden'}">
        <input type="hidden" ${disabledAttribute} name="${additionalfieldName}" id="${additionalfieldId}" value="${answer}" />
    </g:if>
    <g:elseif test="${additionalQuestion?.inputType == 'text' && !(constructionResource && additionalQuestion?.isTransportQuestion && (answer == null || answer.isEmpty()))}">
        <g:set var="hoverOver" value="" />
        <g:if test="${disabledAttribute && 'comment'.equals(additionalQuestion?.questionId)}">
             <g:set var="hoverOver" value="rel='popover' data-trigger='hover' data-content='${answer}'" />
        </g:if>
        <g:if test="${additionalQuestion?.questionId == 'serviceLife' && answer && answer.toString().replace(',', '.').isNumber() && answer.toString().replace(',', '.').toDouble() % 1 == 0}">
           <g:set var="answer" value="${(int) answer.toString().replace(',', '.').toDouble()}" />
        </g:if>
        <g:if test="${additionalQuestion?.specifiedSettingDisplayMode}">
            <g:if test="${answer && answer.toString().replace(',', '.').isNumber() && answer.toString().replace(',', '.').toDouble() == additionalQuestion.specifiedSettingDisplayMode.specifiedSetting}">
                <g:if test="${!disabledAttribute}">
                    <a class="${datasetLocked ? "lockedDatasetQuestion" : "lockableDatasetQuestion"}"
                       href="javascript:" ${disableEditing ? 'readonly' : ''}
                       onclick="changeLinkToTextfield(${additionalQuestion?.onlyNumeric ? "\'numeric\', " : ''}this, '${additionalfieldName}', '${additionalfieldId}'${!additionalQuestion?.optional && showMandatory ? ', \'true\'' : ''}${!additionalQuestion.specifiedSettingDisplayMode.setEmptyWhenClicked ? ', \'' + answer?.toString() + '\'' : ''});"
                       rel="popover" data-trigger="hover" data-html="true"
                       data-content="${additionalQuestion.specifiedSettingDisplayMode.localizedHoverOverText}">
                        ${additionalQuestion.specifiedSettingDisplayMode.localizedDisplayText}
                    </a>
                </g:if>
                <g:else>
                    ${additionalQuestion.specifiedSettingDisplayMode.localizedDisplayText}
                </g:else>
            </g:if>
            <g:else>
                <g:if test="${hoverOver}">
                    <div ${hoverOver}>
                </g:if>
                <input type="text"
                       data-datasetId="${datasetId}" ${disabledAttribute} ${disableEditing ? 'readonly' : ''}
                       class="input-small ${additionalQuestion?.hideValueInQuery && !constructionPage ? 'hidden' : ''}${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"}" ${g.questionInlineStyle(question: additionalQuestion, showMandatory: showMandatory, answer: answer)}
                       name="${additionalfieldName}" id="${additionalfieldId}"
                       value="${answer?.toString()?.replace("\'", "")?.replace("\"", "")}"/>
                <g:if test="${hoverOver}">
                    </div>
                </g:if>
            </g:else>
        </g:if>
        <g:else>
            <g:if test="${additionalQuestion?.questionId == 'serviceLife' && answer && answer.toString().replace(',', '.').isNumber() && answer.toString().replace(',', '.').toDouble() == -1}">
                <input type="hidden" name="${additionalfieldName}" id="${additionalfieldId}" value="-1.0" />
                <g:message code="resource.serviceLife.as_building" />
            </g:if>
            <g:else>
                <g:if test="${hoverOver}">
                    <div ${hoverOver}>
                </g:if>
                <input type="text" ${disabledAttribute}
                       name="${additionalfieldName}" ${g.questionInlineStyle(question: additionalQuestion, showMandatory: showMandatory, answer: answer)}
                       onchange="removeBorder(this${additionalQuestion?.inputWidth ? ', \'' + additionalQuestion?.inputWidth + '\'' : ''});"
                       id="${additionalfieldId}" ${additionalQuestion?.onlyNumeric ? ' onblur=\"checkValue(this, \'' + additionalQuestion?.localizedQuestion + '\', \'' + additionalQuestion?.minAllowed + '\', \'' + additionalQuestion?.maxAllowed + '\', \'' + additionalQuestion?.inputWidth + '\');\"' : ''}
                       class="input-small ${additionalQuestion?.hideValueInQuery && !constructionPage ? 'hidden' : ''}${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"}" ${additionalQuestion?.isThicknessQuestion && resourceId && !allowVariableThickness ? 'disabled=\"disabled\"' : ''}
                       value="${answer}"/>
                <g:if test="${hoverOver}">
                    </div>
                </g:if>
                <g:if test="${additionalQuestion?.localizedUnit && !additionalQuestion?.groupedQuestionIds && (!constructionResource && additionalQuestion?.questionId?.equalsIgnoreCase('transportResourceId'))}">
                    &nbsp;${additionalQuestion?.localizedUnit}
                </g:if>
            </g:else>
        </g:else>
    </g:elseif>
    <g:elseif test="${additionalQuestion?.inputType == 'textarea'}">
        <textarea rows="3" cols="100"
                  data-dataSetId="${datasetId}" ${disableEditing ? 'readonly' : ''} ${disabledAttribute}
                  name="${additionalfieldName}" ${g.questionInlineStyle(question: additionalQuestion, showMandatory: showMandatory, answer: answer)}
                  onchange="removeBorder(this${additionalQuestion?.inputWidth ? ', \'' + additionalQuestion?.inputWidth + '\'' : ''});"
                  id="${additionalfieldId}" ${additionalQuestion?.onlyNumeric ? ' onblur=\"checkValue(this, \'' + additionalQuestion?.localizedQuestion + '\', \'' + additionalQuestion?.minAllowed + '\', \'' + additionalQuestion?.maxAllowed + '\', \'' + additionalQuestion?.inputWidth + '\');\"' : ''}
                  class="input-small ${additionalQuestion?.hideValueInQuery && !constructionPage ? 'hidden' : ''}" ${additionalQuestion?.isThicknessQuestion && resourceId && !allowVariableThickness ? 'disabled=\"disabled\"' : ''}>${answer}</textarea>
        <g:if test="${additionalQuestion?.localizedUnit && !additionalQuestion?.groupedQuestionIds}">
            &nbsp;${additionalQuestion?.localizedUnit}
        </g:if>
    </g:elseif>
    <g:elseif test="${additionalQuestion?.inputType == 'select' && !(constructionResource && additionalQuestion?.questionId?.equalsIgnoreCase('transportResourceId') && (answer == null || answer.isEmpty()))}">
        <aq:renderSelectQuestion additionalQuestion="${additionalQuestion}"
                                 mainAdditionalQuestion="${mainAdditionalQuestion}"
                                 defaultLocalCompCountry="${defaultLocalCompCountry}"
                                 projectCountryResource="${projectCountryResource}"
                                 additionalfieldName="${additionalfieldName}" answer="${answer}"
                                 importMapperAdditionalQuestion="${importMapperAdditionalQuestion}"
                                 datasetId="${datasetId}"
                                 additionalQuestionResources="${additionalQuestionResources?.get(additionalQuestion?.questionId)}"
                                 newResourceRowAdded="${newResourceRowAdded}" datasetLocked="${datasetLocked}"
                                 additionalfieldId="${additionalfieldId}" showMandatory="${showMandatory}"
                                 fullTextExpand="${fullTextExpand}" constructionResource="${constructionResource}"
                                 userGivenAnswer="${groupedAdditionalQuestionAnswer}"
                                 mainAdditionalQuestionAnswer="${mainAdditionalQuestionAnswer}"
                                 localCompensationMethodVersion="${localCompensationMethodVersion}"
                                 uniqueConstructionIdentifier="${uniqueConstructionIdentifier}"
                                 question="${mainQuestion}" indicator="${indicator}" parentEntity="${parentEntity}"
                                 resource="${resource}"
                                 inheritToChildrent="${additionalQuestion?.inheritToChildren && constructionResource ? "${uniqueConstructionIdentifier + additionalQuestion?.questionId}" : ""}"
                                 preventChanges="${preventChanges || disableEditing}" entity="${entity}"
                                 random="${random}" userUnit="${userSavedUnitForCost ?: unitToShow}"
                                 queryId="${query?.queryId}" sectionId="${mainSectionId}"
                                 questionId="${mainQuestion?.questionId}"
                                 forGroupEditRender="${forGroupEditRender}"
                                 showAsDisabled="${additionalQuestionShowAsDisabled?.get(additionalQuestion?.questionId)}"
                                 eolProcessCache="${eolProcessCache}"/>
    </g:elseif>
</g:else>