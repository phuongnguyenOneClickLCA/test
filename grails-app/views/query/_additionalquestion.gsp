<%@ page import="com.bionova.optimi.core.Constants" %>
<g:set var="questionService" bean="questionService"/>

<g:if test="${importMapperAdditionalQuestion || !additionalQuestion?.licenseKey || (additionalQuestion?.licenseKey && additionalQuestionLicensed?.get(additionalQuestion?.questionId))}">
    <g:set var="randomId" value="${UUID.randomUUID().toString()}"/>
    <g:set var="additionalfieldName"
           value="${mainSectionId}.${mainQuestion?.questionId}_additional_${additionalQuestion?.questionId}${resourceId ? '.' + resourceId : ''}"/>
    <g:set var="hideStateSelect"
           value="${("state".equalsIgnoreCase(additionalQuestion.questionId) && ((answer.toString() == "" || answer == null) && !mainQuestion.resources?.find({ it?.resourceId?.equalsIgnoreCase(resourceId) })?.showState))}"/>
    <g:set var="additionalfieldId"
           value="${randomId}${mainSectionId}.${mainQuestion?.questionId}_additional_${additionalQuestion?.questionId}"/>

    <g:if test="${importMapperAdditionalQuestion}">
        <g:set var="additionalfieldName" value="additional_${additionalQuestion?.questionId}.${datasetId}"/>
        <g:set var="additionalfieldId" value="additional_${additionalQuestion?.questionId}.${datasetId}"/>
    </g:if>

    <g:set var="answer"
           value="${aq.additionalQuestionAnswer(additionalQuestionAnswer: additionalQuestionAnswer, additionalQuestion: additionalQuestion, resource: resource)}"/>
    <g:set var="addQDisplayEnabled" value = "true" />

    <g:if test="${additionalQuestion?.additionalQuestionDisplayCriteria}">
        <g:set var ="resourceFilterCriteriaUtilObj"  value="${new com.bionova.optimi.util.ResourceFilterCriteriaUtil()}"/>
        <g:set var ="addQDisplayEnabled"  value="${resourceFilterCriteriaUtilObj?.checkCriteriaMapForResource(resource,null,additionalQuestion?.additionalQuestionDisplayCriteria,true,false)?.booleanValue()}"/>
    </g:if>
    <g:if test="${!addQDisplayEnabled}">
        <td></td>
    </g:if>
    <g:if test="${!'hidden'.equals(additionalQuestion?.inputType) && addQDisplayEnabled}">
        <td
            <g:if test="${constructionResource && (additionalQuestion?.inheritToChildren || resource.getIsAdditionalQuestionInherited(additionalQuestion))}">
                data-inherit="${uniqueConstructionIdentifier + additionalQuestion?.questionId}"
            </g:if>
            <g:if test="${uniqueConstructionIdentifier && parentConstructionId}">
                data-identifierForInheritance="${uniqueConstructionIdentifier + additionalQuestion?.questionId}"
            </g:if>
            class="${additionalQuestion?.hideValueInQuery && !constructionPage || (!additionalQuestion.isThicknessQuestion && splitPage) || hideStateSelect ? ' hidden' : ''}${importMapperAdditionalQuestion ? ' tdalignmiddle' : ''} ${groupEdit ? 'groupEditAnswer forceHide' : ''}"
            ${additionalQuestion?.specifiedSettingDisplayMode?.aligntoRight ? ' style="text-align: right;"' : ''}>
    </g:if>

    <g:if test="${!'hidden'.equals(additionalQuestion?.inputType) && constructionResource && (additionalQuestion?.dataByConstituent||additionalQuestion?.isTransportQuestion) && addQDisplayEnabled}">
            <span class="${additionalQuestion?.questionId}"><g:message code="data_by_constituent"/> &nbsp;</span>
        </td>
    </g:if>
    <g:elseif test="${addQDisplayEnabled}">
        <g:if test="${!multipleChoicesAllowed && !tableFormat}">
            <label>${additionalQuestion?.localizedQuestion}</label>
        </g:if>

        <g:set var="disableThicknessFinalCheck"
               value="${(disableEditingThickness && !forceEnableEditingThickness && (additionalQuestion?.questionId in Constants.THICKNESS_MM_ADDQ_ID && (forceEnableEditingThickness || allowVariableThickness) && !answer))}"/>
        <g:set var="nmd3DimensionDisableCheck"
               value="${disableEditingThickness && additionalQuestion?.questionId in Constants.NMD_SCALING_ADDQ_ID && !answer}"/>
        <g:set var="disableEditing"
               value="${disabledAttribute || (additionalQuestion?.disableEditingForConstruction && constructionResource && !resource.getIsAdditionalQuestionInherited(additionalQuestion)) || (additionalQuestion?.disableEditingForComponent && parentConstructionId) || disableThicknessFinalCheck || nmd3DimensionDisableCheck}"/>
        <g:set var="checkValue"
               value="${aq.setCheckValue(additionalQuestion: additionalQuestion, resource: resource)}"/>

        <g:if test="${additionalQuestion?.inputType == 'hidden'}">
            <g:if test="${answer != null && answer.toString() != ""}">
                <input type="hidden" class="${"specialRuleId".equals(additionalQuestion?.questionId) ? "specialRuleIdHidden" : ""}" name="${additionalfieldName}" id="${additionalfieldId}" value="${answer}" />
                <g:if test="${additionalQuestion?.questionId == 'costPerUnit' && userSetCost}">
                    <input type="hidden" id="userSetCostcostPerUnit" value="${userSetCost ? true : ''}" class="userSetCostcostPerUnit" name="${mainSectionId}.${mainQuestion?.questionId}_userSetCost_${additionalQuestion?.questionId}${resourceId ? '.' + resourceId : ''}">
                </g:if>
                <g:if test="${additionalQuestion?.questionId == 'totalCost' && userSetTotalCost}">
                    <input type="hidden" id="userSetCosttotalCost" value="${userSetTotalCost ? true : ''}" class="userSetCosttotalCost" name="${mainSectionId}.${mainQuestion?.questionId}_userSetCost_${additionalQuestion?.questionId}${resourceId ? '.' + resourceId : ''}">
                </g:if>
            </g:if>
        </g:if>
        <g:elseif test="${additionalQuestion?.inputType == 'span'}">
            <g:if test="${(additionalQuestion.questionId.equalsIgnoreCase("carbonDataImpact") || additionalQuestion.questionId.equalsIgnoreCase("carbonDataImpact_shadowPrice_NL")) && query?.allowedFeatures?.contains("showCarbonData") && user}">
                <g:if test="${constructionResource || resource?.hideConstituentImpacts != true}">
                    <span class="carbonDataImpact" id="${trId}carbonData">
                        <span id="carbonValue${datasetId}" data-manualId="${datasetId}"
                              data-unit="${additionalQuestion?.localizedUnit}" class="carbonValue">
                        </span>
                        <span id="carbonPercentageValue${datasetId}" class="carbonPercentageValue"></span>
                    </span>
                </g:if>
            </g:if>
        </g:elseif>
        <g:elseif test="${additionalQuestion?.inputType == 'text'}">
            <g:set var="fullTextExpand" value="${additionalQuestion?.showFullText ? "rel='popover' data-trigger='click' data-content='${answer}'" : ""}"/>
            <g:if test="${answer && answer.toString().replace(',', '.').isNumber()}">
                <g:if test="${loadResourceParameterDecimals != null}">
                    <g:set var="answer"
                           value="${formatNumber(number: Double.valueOf(answer.toString().replace(',', '.')), type: "number", minFractionDigits: loadResourceParameterDecimals, roundingMode: "HALF_UP")}"/>
                </g:if>
                <g:elseif test="${additionalQuestion?.showAsInteger && (Double.valueOf(answer.toString().replace(',', '.')) % 1) == 0}">
                    <g:set var="answer" value="${Double.valueOf(answer.toString().replace(',', '.')).intValue()}"/>
                </g:elseif>
            </g:if>
            <g:if test="${additionalQuestion?.questionId == "serviceLife"}">
                <aq:renderServiceLifeQuestion additionalQuestion="${additionalQuestion}"
                                              additionalfieldName="${additionalfieldName}" answer="${answer}"
                                              disabledAttribute="${disabledAttribute}" checkValue="${checkValue}"
                                              showMandatory="${showMandatory}"
                                              userSavedUnitForCost="${userSavedUnitForCost}" unitToShow="${unitToShow}"
                                              mainQuestion="${mainQuestion}" disableEditing="${disableEditing}"
                                              datasetId="${datasetId}" datasetLocked="${datasetLocked}"
                                              inheritConstructionServiceLife="${inheritConstructionServiceLife}"
                                              additionalfieldId="${additionalfieldId}"
                                              constructionResource="${constructionResource}"
                                              uniqueConstructionIdentifier="${uniqueConstructionIdentifier}"
                                              materialServiceLifeWarning="${materialServiceLifeWarning}"
                                              random="${random}"
                                              defaultServiceLife="${entity?.defaults?.get(com.bionova.optimi.construction.Constants.DEFAULT_SERVICELIFE)}"
                                              resource="${resource}" queryId="${query?.queryId}"
                                              indicator="${indicator}" userGivenAnswer="${additionalQuestionAnswer}"/>
            </g:if>
            <g:elseif test="${additionalQuestion?.questionId == 'costPerUnit' || additionalQuestion?.questionId == 'totalCost' || additionalQuestion?.questionId == 'totalAndSocialCost'}">
                <aq:renderAutomatedCostQuestion additionalQuestion="${additionalQuestion}" entity="${entity}"
                                                additionalfieldName="${additionalfieldName}" answer="${answer}"
                                                resourceId="${resourceId}" disabledAttribute="${disabledAttribute}"
                                                userSetCost="${userSetCost}" userSetTotalCost="${userSetTotalCost}"
                                                automaticCostLicensed="${automaticCostLicensed}"
                                                userSavedUnitForCost="${userSavedUnitForCost}"
                                                unitToShow="${unitToShow}"
                                                mainSectionId="${mainSectionId}" mainQuestion="${mainQuestion}"
                                                disableEditing="${disableEditing}" datasetId="${datasetId}"
                                                quantityAnswer="${quantityAnswer}"
                                                additionalfieldId="${additionalfieldId}"
                                                showMandatory="${showMandatory}" fullTextExpand="${fullTextExpand}"
                                                random="${random}" constructionResource="${constructionResource}"
                                                uniqueConstructionIdentifier="${uniqueConstructionIdentifier}"
                                                resource="${resource}" indicator="${indicator}"
                                                datasetLocked="${datasetLocked}"
                                                costCalculationMethod="${costCalculationMethod}"/>
            </g:elseif>
            <g:else>
                <aq:renderTextQuestion additionalQuestion="${additionalQuestion}" entity="${entity}"
                                       additionalfieldName="${additionalfieldName}" answer="${answer}"
                                       resourceId="${resourceId}" disabledAttribute="${disabledAttribute}"
                                       userSavedUnitForCost="${userSavedUnitForCost}" disableEditing="${disableEditing}"
                                       datasetId="${datasetId}" additionalfieldId="${additionalfieldId}"
                                       showMandatory="${showMandatory}" fullTextExpand="${fullTextExpand}"
                                       random="${random}" constructionResource="${constructionResource}"
                                       uniqueConstructionIdentifier="${uniqueConstructionIdentifier}"
                                       resource="${resource}" datasetLocked="${datasetLocked}"
                                       checkValue="${checkValue}" trId="${trId}"
                                       allowVariableThickness="${allowVariableThickness}"
                                       forceEnableEditingThickness="${forceEnableEditingThickness}"
                                       hideThickness="${hideThickness}" constructionUnit="${constructionUnit}"
                                       originalUnit="${originalUnit}" preventChanges="${preventChanges}"
                                       disabled="${disabled}"
                                       additionalQuestionInputWidth="${additionalQuestionInputWidth}"
                                       randomId="${randomId}"
                                       constructionPage="${constructionPage}" splitPage="${splitPage}"
                                       disableThickness="${disableThickness}"
                                       unitFieldId="${unitFieldId}"
                                       disableEditingThickness="${disableEditingThickness}" dataset="${dataset}"/>
            </g:else>
        </g:elseif>
        <g:elseif test="${additionalQuestion?.inputType == 'textarea'}">
            <textarea ${additionalQuestion?.uiNumericCheck} data-dataSetId="${datasetId}" rows="3"
                                                            cols="100" ${disableEditing ? 'readonly' : ''}
                                                            name="${additionalfieldName}" ${g.questionInlineStyle(question: additionalQuestion, showMandatory: showMandatory, answer: answer)}
                                                            onchange="removeBorder(this${additionalQuestion?.inputWidth ? ', \'' + additionalQuestion?.inputWidth + '\'' : ''});"
                                                            id="${additionalfieldId}" onblur="${checkValue}"
                                                            class="input-small numeric ${additionalQuestion?.hideValueInQuery && !constructionPage ? 'hidden' : ''}${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"}" ${additionalQuestion?.isThicknessQuestion && resourceId && !allowVariableThickness ? 'disabled=\"disabled\"' : ''}>${answer}</textarea>
            <g:if test="${additionalQuestion?.localizedUnit && !additionalQuestion?.groupedQuestionIds}">
                &nbsp;${additionalQuestion?.localizedUnit}
            </g:if>
            <g:elseif test="${additionalQuestion?.unitAsValueReference && !additionalQuestion?.groupedQuestionIds}">
                &nbsp;${additionalQuestion.unitAsValueReference.getValueForEntity(entity, Boolean.FALSE, Boolean.TRUE) ?: ''}
            </g:elseif>
        </g:elseif>
        <g:elseif test="${additionalQuestion?.inputType == 'singlecheckbox'}">
            <input type="checkbox"
                   data-dataSetId="${datasetId}" ${disabledAttribute || disabledFlag ? "disabled=\"disabled\"" : ""}${answer && answer.equals('true') ? ' checked=\"checked\"' : ''}
                   value="true" onclick="toggleHiddenInput(this, '${random}HiddenCheckbox')"
                   name="${additionalfieldName}" ${g.questionInlineStyle(question: additionalQuestion, showMandatory: showMandatory, answer: answer)}
                   onchange="removeBorder(this${additionalQuestion?.inputWidth ? ', \'' + additionalQuestion?.inputWidth + '\'' : ''});"
                   class="${additionalQuestion?.hideValueInQuery && !constructionPage ? 'hidden' : ''}${disableEditing ? " removeClicks" : ""}${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"}"
                   id="${additionalfieldId}"/>
            <g:if test="${!removeHiddenCheckboxToggle}">
                <input class="${random}HiddenCheckbox" type="hidden" value="false"
                       name="${additionalfieldName}" ${answer == 'true' ? 'disabled="disabled"' : ''}>
            </g:if>
        </g:elseif>
        <g:elseif test="${'toggle' == additionalQuestion?.inputType}">
            <input type="checkbox" name="${additionalfieldName}" ${answer == 'true' ? "checked=\"true\"" : ''}
                   value="true" data-dataSetId="${datasetId}" onclick="toggleHiddenInput(this, '${random}HiddenToggle')"
                   class="toggle${additionalQuestion?.hideValueInQuery && !constructionPage ? ' hidden' : ''}${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"}"
                   id="${additionalfieldId}"
                   style="style: inline-table; width: 45px !important; border-radius: 20px !important;">
            <g:if test="${!removeHiddenCheckboxToggle}">
                <input class="${random}HiddenToggle" type="hidden" value="false"
                       name="${additionalfieldName}" ${answer == 'true' ? 'disabled="disabled"' : ''}>
            </g:if>
        </g:elseif>
        <g:elseif test="${additionalQuestion?.inputType == 'select' && !(constructionResource && additionalQuestion?.questionId?.equalsIgnoreCase('transportResourceId') && (answer == null || answer.isEmpty()))}">
            <aq:renderSelectQuestion additionalQuestion="${additionalQuestion}"
                                     defaultLocalCompCountry="${defaultLocalCompCountry}"
                                     projectCountryResource="${projectCountryResource}"
                                     additionalfieldName="${additionalfieldName}" answer="${answer}"
                                     importMapperAdditionalQuestion="${importMapperAdditionalQuestion}"
                                     datasetId="${datasetId}"
                                     additionalQuestionResources="${additionalQuestionResources?.get(additionalQuestion?.questionId)}"
                                     newResourceRowAdded="${newResourceRowAdded}" datasetLocked="${datasetLocked}"
                                     additionalfieldId="${additionalfieldId}" showMandatory="${showMandatory}"
                                     fullTextExpand="${fullTextExpand}" constructionResource="${constructionResource}"
                                     userGivenAnswer="${additionalQuestionAnswer}" defaultResource="${defaultResource}"
                                     uniqueConstructionIdentifier="${uniqueConstructionIdentifier}"
                                     question="${mainQuestion}" indicator="${indicator}" parentEntity="${parentEntity}"
                                     resource="${resource}"
                                     inheritToChildrent="${additionalQuestion?.inheritToChildren && constructionResource ? "${uniqueConstructionIdentifier + additionalQuestion?.questionId}" : ""}"
                                     preventChanges="${preventChanges || disableEditing}" entity="${entity}"
                                     userUnit="${userSavedUnitForCost ?: unitToShow}" random="${random}"
                                     queryId="${query?.queryId}" sectionId="${mainSectionId}"
                                     questionId="${mainQuestion?.questionId}"
                                     showAsDisabled="${additionalQuestionShowAsDisabled?.get(additionalQuestion?.questionId)}"
                                     lcaModel="${lcaModel}"
                                     forGroupEditRender="${forGroupEditRender}"
                                     localCompensationMethodVersion="${localCompensationMethodVersion}"
                                     eolProcessCache="${eolProcessCache}"/>
        </g:elseif>
        <g:if test="${additionalQuestion.groupedQuestionIds}">
            <g:each in="${groupedQuestionsPerAdditionalQuestion?.get(additionalQuestion.questionId) ?: questionService.getGroupedQuestions(additionalQuestion)}"
                    var="groupedQuestion">
                <g:render template="/query/groupedAdditionalQuestion" model="[
                        additionalQuestion              : groupedQuestion,
                        mainAdditionalQuestion          : additionalQuestion,
                        query                           : query,
                        resourceId                      : resourceId,
                        mainSectionId                   : mainSectionId,
                        mainQuestion                    : mainQuestion,
                        mainAdditionalQuestionAnswer    : additionalQuestionAnswer,
                        groupedAdditionalQuestionAnswer : additionalQuestionAnswers?.get(groupedQuestion.questionId),
                        importMapperAdditionalQuestion  : importMapperAdditionalQuestion,
                        parentConstructionId            : parentConstructionId,
                        disableEditing                  : disableEditing,
                        constructionResource            : constructionResource,
                        constructionPage                : constructionPage,
                        resource                        : resource,
                        userSavedUnitForCost            : userSavedUnitForCost,
                        unitToShow                      : unitToShow,
                        newResourceRowAdded             : newResourceRowAdded,
                        datasetLocked                   : datasetLocked,
                        additionalQuestionShowAsDisabled: additionalQuestionShowAsDisabled,
                        eolProcessCache                 : eolProcessCache]"/>
            </g:each>
        </g:if>
        <g:if test="${!'hidden'.equals(additionalQuestion?.inputType)}">
            <g:if test="${'comment'.equals(additionalQuestion?.questionId) && showImportFields}">&nbsp;
                <a href="javascript:" id="${datasetId}displayField"
                   onclick="openCombinedDisplayFields('${datasetId}', '${datasetId}displayField', '${entity?.id}', '${query?.queryId}', '${mainSectionId}', '${mainQuestion?.questionId}')">
                    <i class="far fa-file-alt fa-lg greenInfoBubble" aria-hidden="true"></i>
                </a>
            </g:if>
        </g:if>
    </g:elseif>
    </td>
    <g:if test="${("state".equalsIgnoreCase(additionalQuestion.questionId)) && countriesWithPlanetary}">
        <td>
            <div class='alert alert-warning hidden' style='margin: 0px 0px 0px 10px !important; display: inline-block; padding: 10px !important; max-width: 500px; white-space: break-spaces'><p>${message(code:'planetary_unavailable')}</p></div>
            <div class='success hidden' style='margin: 0px 0px 0px 10px !important; display: inline-block; padding: 10px !important; max-width: 500px; white-space: break-spaces'><p>${message(code:'planetary_available')}</p></div>
        </td>
    </g:if>
</g:if>