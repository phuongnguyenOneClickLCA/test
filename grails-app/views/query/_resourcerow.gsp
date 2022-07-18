<%@ page import="com.bionova.optimi.core.Constants" %>
<g:set var="optimiResourceService" bean="optimiResourceService"/>
<g:set var="resourceService" bean="resourceService"/>
<tr id="${trId}" data-questionId="${question?.questionId}" data-queryId="${query?.queryId}"
    data-resourceUUID="${resource?.id?.toString()}"
    data-sectionId="${mainSection?.sectionId}"
    data-manualIdForMoving="${datasetId ? datasetId : manualId}"
    <g:if test="${constructionResource}">
        data-parentIdentifier="${uniqueConstructionIdentifier}"
        data-constructionRow="${uniqueConstructionIdentifier}row"
    </g:if>
    class="queryResourceRow ${dataset?.unlockedFromVerifiedStatus ? 'unlockedVerifiedDatasetStatusBg' : dataset?.verified ? 'verifiedDatasetBackground' : ''} ${newResourceRowAdded && !materialServiceLifeWarning ? parentConstructionId && uniqueConstructionIdentifier ? '' : 'newResourceRow' : 'existingResourceRow'}  ${parentConstructionId && uniqueConstructionIdentifier ? 'hidden' : ''} ${parentConstructionId && uniqueConstructionIdentifier ? uniqueConstructionIdentifier + 'row' : ''}${materialServiceLifeWarning ? ' materialServiceWarning' : ''}${newlyAddedDatasets?.contains(datasetId ?: manualId) ? ' newGroupCreated' : ''}"
    data-sequenceNro="${seqNo}"
    <g:if test="${parentConstructionId && uniqueConstructionIdentifier}">
        data-dragMeWithParent="${uniqueConstructionIdentifier}row"
    </g:if>
>
    <g:set var="resourceName" value="${customDatasetName ?: optimiResourceService.getUiLabel(resource)}"/>
    <g:set var="random" value="${UUID.randomUUID().toString()}"/>
    <td class="${splitPage ? 'hidden' : 'visible'} resourceRowNameCell">
        <g:render template="/query/icons/verifyDatasetIcons"
                  model="[forResourceRowNameCell: true, user: user, dataset: dataset, constructionResource: constructionResource,
                          uniqueConstructionIdentifier: uniqueConstructionIdentifier, verifiedProductFlag: verifiedProductFlag]"/>
        <input type="hidden" name="" value="${resource?.resourceId}"/>
        <g:if test="${dataset?.locked}">
            <i class="fa fa-lock queryDatasetRowLocked" aria-hidden="true"></i>
        </g:if>
        <g:if test="${splitPage}">
            <input type="hidden" name="splittedOrChangedResourceId${resource?.resourceId}"
                   value="${resource?.resourceId}"/>
        </g:if>
        <g:if test="${!parentConstructionId}">
            <span>
                <input type="checkbox" name="move${datasetId ? datasetId : manualId}" data-checkbox="true"
                    <g:if test="${constructionResource}">
                        data-parentConstruction="true"
                    </g:if>
                       data-constructionIdentifierMoving="${uniqueConstructionIdentifier}"
                       class="moveResourceCheckBox visibilityNone"/>
            </span>
        </g:if>
        <input type="hidden" name="${fieldName}" value="${resource?.resourceId}"/>
        <input type="hidden"
               name="${mainSection?.sectionId}.${question?.questionId}_seqNo_${resource?.resourceId ? '.' + resource.resourceId : ''}"
               data-persistedSeqNro="true" value="${seqNo != null ? seqNo : ''}"/>
        <g:set var="questionIdPrefix" value="${mainSection?.sectionId}.${question?.questionId}"/>
        <g:render template="/query/inputs/verifyDatasetInputs" model="[forResourceRow  : true, productDataListPage: productDataListPage,
                                                                       constructionPage: constructionPage,
                                                                       questionIdPrefix: questionIdPrefix, resourceId: resource?.resourceId]"/>
        <input type="hidden" data-manualIdContainer="true"
               name="${mainSection?.sectionId}.${question?.questionId}_manualId_${resource?.resourceId ? '.' + resource.resourceId : ''}"
               value="${datasetId ?: manualId}"/>
        <g:if test="${nmdProfileSetString}">
            <input type="hidden" name="${mainSection?.sectionId}.${question?.questionId}_nmdProfileSetString_${resource?.resourceId ? '.' + resource.resourceId : ''}"
                   data-persisted="true" value="${nmdProfileSetString}"/>
        </g:if>
        <input type="hidden" class="customDatasetName"
               name="${mainSection?.sectionId}.${question?.questionId}_groupingDatasetName_${resource?.resourceId ? '.' + resource.resourceId : ''}"
               data-persisted="true" value="${customDatasetName ?: ''}"/>
        <g:if test="${!constructionPage}"><input type="hidden" data-uniqueConstructionIdentifierContainer="true"
                                                 name="${mainSection?.sectionId}.${question?.questionId}_uniqueConstructionIdentifier_${resource?.resourceId ? '.' + resource.resourceId : ''}"
                                                 data-persisted="true"
                                                 value="${uniqueConstructionIdentifier ?: ''}"/>
        </g:if>
        <g:if test="${!constructionPage}"><input type="hidden"
                                                 name="${mainSection?.sectionId}.${question?.questionId}_constructionValue_${resource?.resourceId ? '.' + resource.resourceId : ''}"
                                                 data-persisted="true" value="${constructionValue ?: ''}"/>
        </g:if>
        <g:if test="${!constructionPage}"><input type="hidden"
                                                 name="${mainSection?.sectionId}.${question?.questionId}_parentConstructionId_${resource?.resourceId ? '.' + resource.resourceId : ''}"
                                                 data-persisted="true" value="${parentConstructionId ?: ''}"/>
        </g:if>
        <input type="hidden"
               name="${mainSection?.sectionId}.${question?.questionId}_datasetImportFieldsId_${resource?.resourceId ? '.' + resource.resourceId : ''}"
               data-persisted=${datasetImportFieldsId ? "true" :"false" }
               value="${datasetImportFieldsId ?: ''}"/>

        <g:if test="${datasetIdFromConstruction}"><input type="hidden"
                                                         id="${mainSection?.sectionId}.${question?.questionId}_additional_datasetIdFromConstruction"
                                                         name="${mainSection?.sectionId}.${question?.questionId}_additional_datasetIdFromConstruction${resource?.resourceId ? '.' + resource.resourceId : ''}"
                                                         value="${datasetIdFromConstruction ?: ''}"/>
        </g:if>
        <g:if test="${!constructionPage}"><input type="hidden" class="ignoreWarningsHiddenInput"
                                                 name="${mainSection?.sectionId}.${question?.questionId}_ignoreWarning_${resource?.resourceId ? '.' + resource.resourceId : ''}"
                                                 value="${ignoreWarning ? true : false}"/>
        </g:if>
        <g:if test="${!constructionPage}"><input type="hidden"
                                                 name="${mainSection?.sectionId}.${question?.questionId}_locked_${resource?.resourceId ? '.' + resource.resourceId : ''}"
                                                 class="lockDataset" value="${dataset?.locked ? true : false}"/>
        </g:if>
        <g:if test="${constructionResource}"><span class="hidden">${resourceName?.encodeAsHTML()}</span>
            <g:if test="${!preventExpand}">
                <a class="constituentToggler" <g:if test="${newResourceRowAdded}">style="opacity: 0.5; pointer-events: none;"</g:if>
                   href="javascript:"
                   onclick="toggleUnderLyingConstructions('${trId}constructionToggler', '${uniqueConstructionIdentifier}row');">
                    <i class="fa fa-plus-square" id="${trId}constructionToggler"></i>
                </a>
            </g:if> ${constructionTypeImage}
        </g:if>
        <g:if test="${resource?.multipart}">${constructionTypeImage}</g:if>
        <g:if test="${persistedClass}">
            <input type="hidden"
                   name="${mainSection?.sectionId}.${question?.questionId}_persistedClass_${resource?.resourceId ? '.' + resource.resourceId : ''}"
                   value="${persistedClass}"/>
        </g:if>

        <g:render template="/query/resourceName" model="[resourceName: resourceName, preventChanges: preventChanges, disabled: disabled, dataset: dataset, resource: resource, limit: 35]"/>

        <g:if test="${user}">
            <opt:renderDataCardBtn infoId="${random}" resourceId="${resource?.resourceId}"
                                   profileId="${profileId ?: resource?.profileId}"
                                   indicatorId="${indicator?.indicatorId}" questionId="${question?.questionId}"
                                   childEntityId="${entity?.id}" showGWP="${showGWP}" queryId="${query?.queryId}"
                                   sectionId="${sectionId ?: ''}" datasetId="${datasetId ?: ''}" queryPage="true"
                                   disabled="${disabledForTaskUser ?: disabled ?: ""}"
                                   manualId="${resource?.isDummy ? datasetId ?: manualId : ''}"
                                   customName="${customDatasetName}" productDataListPage="${productDataListPage}"
                                   constructionPage="${constructionPage}"/>
        </g:if>
        <g:if test="${notInFilter || notInOrgFilter}">
            <a href="javascript:" rel="popover" data-trigger="hover" data-html="true"
               data-content="${message(code: 'query.resource.not_in_filter')}" id="${random}notInFilter">
                <i class="icon-alert"></i>
            </a>
        </g:if>
    %{--(REL2108-27) Added possibility to display custom message--}%
        <g:if test="${inWarningFilter}">
            <a href="javascript:" rel="popover" data-trigger="hover" data-html="true"
               data-content="${warningMessage ? message(code: warningMessage) : message(code: 'resource.query_filter_warning')}"
               id="${random}notInWarningFilter">
                <asset:image src="/img/icon-warning.png" style="width:18px; height: 18px; margin-right: 3px; vertical-align: sub"/>
            </a>
        </g:if>
        <g:if test="${dataIncompatible}">
            <a href="javascript:" rel="popover" data-trigger="hover" data-html="true"
               data-content="${message(code: 'query.resource.incompatible_unit')}" id="${random}dataIncompatible">
                <i class="icon-alert"></i>
            </a>
        </g:if>
        <g:if test="${resourceService.getIsLocalResource(resource?.areas) && dataset?.additionalQuestionAnswers?.get(Constants.LOCAL_COMP_QUESTIONID)?.equals("noLocalCompensation")}">
            <g:set var="localCompsApplicable" value="${resource.isLocalCompsApplicable}"/>
            <g:if test="${localCompsApplicable}">
                <a href="javascript:" rel="popover" data-trigger="hover" data-html="true"
                   data-content="${message(code: 'query.resource.notApplyLocalComps')}"
                   id="${random}notApplyLocalComps">
                    <asset:image src="img/icon-warning.png" style="max-width:16px; margin-right: 3px"/>
                </a>
            </g:if>
        </g:if>
        <g:if test="${inactive}">
            <g:if test="${resource?.construction && resource?.privateDataset}">
                <a href="javascript:" rel="popover" data-trigger="hover" data-html="true"
                   data-content="${message(code: 'pending_approval')}" id="${random}inactive">
                    <asset:image src="inactiveResource.png"/>
                </a>
            </g:if>
            <g:else>
                <a href="javascript:" rel="popover" data-trigger="hover" data-html="true"
                   data-content="${message(code: 'query.resource.inactive')}" id="${random}inactive"><asset:image
                        src="inactiveResource.png"/>
                </a>
            </g:else>
        </g:if>

        <g:if test="${resource?.isExpiredDataPoint}">
            <a href="javascript:" rel="popover" data-trigger="hover" data-html="true"
               data-content="${message(code: 'data_point.expired')}" id="${random}inactive">
                <asset:image src="/img/iconExpiredBig.png" style="width:18px; height: 18px; margin-right: 3px"/>
            </a>
        </g:if>
        <g:if test="${resource?.resourceQualityWarning}">
            <a href="javascript:" rel="popover" data-trigger="hover" data-html="true"
               data-content="${resource?.resourceQualityWarningText}" id="${random}inactive"><asset:image
                    src="/img/icon-warning.png" style="width:18px; height: 18px; margin-right: 3px; vertical-align: sub"/>
            </a>
        </g:if>
        <g:elseif test="${resource?.showWarning}">
            <a href="javascript:" rel="popover" data-trigger="hover" data-html="true"
               data-content="${resource?.warningText}" id="${random}inactive"><asset:image src="/img/icon-warning.png"
                                                                                           style="width:18px; height: 18px; margin-right: 3px"/>
            </a>
        </g:elseif>

        <g:if test="${dataset && bimModelCheckerLicensed}">
            <queryRender:implausibleThicknessRowLevel dataset="${dataset}" rowId="${trId}" isQueryPage="${true}"
                                                      resource="${resource}"/>
        </g:if>
    </td>
    <g:if test="${splitPage && splitRowId}">
        <td>
            <g:if test="${datasetId}">
                <input type="text" value="100" class="input input-small numeric" data-share="true"
                       data-originalDataset="true" data-linkedSplitQuantity="${splitRowId}"
                       oninput="splitValueChanging(originalDatasetElement, '${originalQuantity ? originalQuantity : 0}', '${datasetId}splitOriginal', this, event);"
                       style="width:25px;" id="shareOfDataset${datasetId}" name="shareOfDataset${datasetId}">
            </g:if>
            <g:else>
                <input type="text" value="" class="input input-small numeric" data-share="true"
                       data-linkedSplitQuantity="${splitRowId}"
                       oninput="splitValueChanging(originalDatasetElement, '${originalQuantity ? originalQuantity : 0}', '${originalId}splitOriginal', this, event);"
                       style="width:25px;" name="shareOfDataset">
            </g:else>
        </td>
    </g:if>
    <g:set var="unitFieldId" value="${random}userGivenUnit"/>
    <g:if test="${quantity}">
        <g:set var="checkValue"><g:if
                test="${question?.onlyNumeric}">onblur="checkValue(this, '${question?.localizedQuestion}', '${question?.minAllowed}', '${question?.maxAllowed}','${question?.inputWidth ? ', ' + question?.inputWidth : ''}');"</g:if></g:set>
        <td>
            <g:render template="/query/inputs/quantityAndUnit/resource/quantityAndUnitInput"
                      model="[isConstructionConstituent: parentConstructionId && !constructionResource, allowEditingConstituentQuantity: indicator?.allowEditingConstituentQuantity, fieldName: fieldName,
                              quantity                 : quantity, quantityAnswer: quantityAnswer, dataset: dataset, datasetId: datasetId, resource: resource, resourceId: resource?.resourceId, questionId: question?.questionId, entity: entity,
                              sectionId                : mainSection?.sectionId, useUserGivenUnit: useUserGivenUnit, userGivenUnit: userGivenUnit, originalUnit: originalUnit, unitFieldId: unitFieldId,
                              parentConstructionId     : parentConstructionId, uniqueConstructionIdentifier: uniqueConstructionIdentifier, constructionValue: constructionValue, constructionUnit: constructionUnit, isParentConstructionResource: constructionResource,
                              useResourceUnitCost      : useResourceUnitCost, defaultResourceUnitCostFromResource: defaultResourceUnitCostFromResource, resourceTypeCostMultiplier: resourceTypeCostMultiplier,
                              quarterlyInputEnabled    : quarterlyInputEnabled, monthlyInputEnabled: monthlyInputEnabled, checkValue: checkValue, random: random, changeView: changeView,
                              splitRowId               : splitRowId, splitPage: splitPage, preventChanges: preventChanges, showImperial: showImperial, disabled: disabled, newResourceRowAdded: newResourceRowAdded, qtyForDataLoadFromFile: qtyForDataLoadFromFile]"/>
        </td>
    </g:if>
    <g:if test="${quarterlyInputEnabled && quantity}">
        <g:render template="/query/quarterlydata"
                  model="[fieldName: (fieldName + '.' + resource.resourceId), quarterlyAnswers: quarterlyAnswers, disabled: disabled, datasetId: datasetId]"/>
    </g:if>
    <g:elseif test="${monthlyInputEnabled && quantity}">
        <g:render template="/query/monthlydata"
                  model="[fieldName: (fieldName + '.' + resource.resourceId), monthlyAnswers: monthlyAnswers, disabled: disabled, datasetId: datasetId]"/>
    </g:elseif>
    <g:if test="${additionalQuestions}">
        <g:each in="${additionalQuestions}" var="additionalQuestion" status="additionalQuestionIndex">
            <g:set var="disableEditingThickness" value="${Boolean.FALSE}"/>
            <g:set var="scalingType23" value="${scalingParameters?.get("scalingType")}"/>
            <g:set var="scalingType30" value="${scalingParameters?.get("nmdSchalingsFormuleID")}"/>
            <g:if test="${(!scalingParameters && Constants.NMD_SCALING_ADDQ_ID.contains(additionalQuestion?.questionId)) || (scalingParameters && ([1, 3].contains(scalingType23) || [1, 3].contains(scalingType30)))}"><g:set
                    var="disableEditingThickness" value="${Boolean.TRUE}"/></g:if>
            <g:set var="forceEnableEditingThickness" value="${Boolean.FALSE}"/>
            <g:if test="${(!scalingParameters && Constants.NMD_SCALING_ADDQ_ID.contains(additionalQuestion?.questionId)) || (scalingParameters && (2 == scalingType23 || 2 == scalingType30))}"><g:set
                    var="forceEnableEditingThickness" value="${Boolean.TRUE}"/></g:if>
            <g:if test="${additionalQuestion?.questionId && additionalQuestionAnswers?.get(additionalQuestion?.questionId)}">
                <g:set var="additionalQuestionAnswer"
                       value="${additionalQuestionAnswers?.get(additionalQuestion.questionId)}"/>
            </g:if>
            <g:else>
                <g:set var="additionalQuestionAnswer" value=""/>
            </g:else>
            <g:render template="/query/additionalquestion"
                      model="[sourceListingId        : random, preventChanges: preventChanges, indicator: indicator, additionalQuestionAnswer: additionalQuestionAnswer, entity: entity, query: query, mainSectionId: mainSection?.sectionId,
                              additionalQuestion     : additionalQuestion, mainQuestion: question, multipleChoicesAllowed: multipleChoicesAllowed, resource: resource, resourceId: resource.resourceId, constructionUnit: constructionUnit, unitFieldId: unitFieldId,
                              allowVariableThickness : resource?.allowVariableThickness, transportUnit: additionalQuestions.find({ it.questionId.contains('transportDistance') })?.localizedUnit, disableThickness: disableThickness, additionalQuestionShowAsDisabled: additionalQuestionShowAsDisabled,
                              datasetId              : datasetId ?: manualId, userSetCost: userSetCost, userSetTotalCost: userSetTotalCost, userSavedUnitForCost: userGivenUnit, quantity: quantity, trId: trId, loadResourceParameterDecimals: loadResourceParameterDecimals,
                              constructionResource   : constructionResource, parentConstructionId: parentConstructionId, materialServiceLifeWarning: materialServiceLifeWarning, showImportFields: showImportFields, unitToShow: unitToShow, assestmentPeriod: assestmentPeriod, additionalQuestionResources: additionalQuestionResources,
                              disableEditingThickness: disableEditingThickness, uniqueConstructionIdentifier: uniqueConstructionIdentifier, constructionPage: constructionPage, splitPage: splitPage, hideThickness: hideThickness, forceEnableEditingThickness: forceEnableEditingThickness, newResourceAdded: newResourceRowAdded, disabled: disabled,
                              defaultLocalCompCountry: defaultLocalCompCountry, additionalQuestionInputWidth: additionalQuestionInputWidth, datasetLocked: dataset?.locked, quantityAnswer: quantityAnswer, projectCountryResource: projectCountryResource, inheritConstructionServiceLife: inheritConstructionServiceLife, dataset: dataset, eolProcessCache: eolProcessCache]"/>
        </g:each>
    </g:if>
    <g:if test="${!parentConstructionId}">
        <td>
            <g:if test="${!preventChanges && !disabled && !splitPage}">
                <div class="btn-group">
                    <a href="javascript:" class="dropdown dropdown-toggle bold"
                       data-toggle="dropdown"><g:message code="change"/> <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu resourceRowEditDropdown" data-datasetIsLocked="${dataset?.locked ?: false}"
                        data-datasetIsVerified="${dataset?.verified ?: false}">
                        <g:if test="${!hideCopyRows && !question?.preventDoubleEntries && (resource?.active == true) && !maxRowLimitPerDesignReached}">
                            <g:if test="${constructionResource && !parentConstructionId}">
                                <li ${dataset?.locked || (dataset?.verified && !verifiedProductFlag) ? "class=\"removeClicks\"" : ""} id="copyDatasetLi">
                                    <a href="javascript:" data-parentRowId="${trId}"
                                       class="copyResourceRowButton${dataset?.locked || (dataset?.verified && !verifiedProductFlag) ? " notCopyable" : ""}"
                                       onclick="copyTableRow($(this).attr('data-parentRowId'), ${verifiedProductFlag}, '${resource?.constructionId}',
                                           '${entity?.id}', '${indicator?.indicatorId}', '${query.queryId}','${mainSection?.sectionId}',
                                           '${question?.questionId}', '${fieldName}', '${resource.resourceId}');"><i
                                            class="icon-tags"></i> ${message(code: 'copy')}
                                    </a>
                                </li>
                            </g:if>
                            <g:else>
                                <li ${dataset?.locked || (dataset?.verified && !verifiedProductFlag) ? "class=\"removeClicks\"" : ""} id="copyDatasetLi">
                                    <a href="javascript:" data-parentRowId="${trId}"
                                       class="copyResourceRowButton${parentConstructionId ? ' hidden' : ''}${dataset?.locked || (dataset?.verified && !verifiedProductFlag) ? " notCopyable" : ""}"
                                       onclick="copyTableRow($(this).attr('data-parentRowId'), ${verifiedProductFlag});">
                                        <i class="icon-tags"></i> ${message(code: 'copy')}
                                    </a>
                                </li>
                            </g:else>
                        </g:if>
                        <g:if test="${constructionResource && (unbundable || resource?.isDummy) && !splitPage && groupMaterialsAllowed}">
                            <li ${dataset?.locked || dataset?.verified ? "class=\"removeClicks\"" : ""}
                                <g:if test="${showSaveWarning}">
                                    data-content="${message(code: 'edit.construction.need.save')}"
                                    data-trigger="hover"
                                    rel="popover"
                                </g:if>>
                                <a id="createVariant${trId}" href="javascript:" class="bold ${showSaveWarning ? 'removeClicks' : ''}"
                                   onclick="editConstruction('${uniqueConstructionIdentifier}', '${trId}', '${entity?.id}', '${indicator?.indicatorId}', '${query?.queryId}', '${mainSection?.sectionId}', '${question?.questionId}', ${createConstructionsAllowed}, ${publicGroupAllowed});">
                                    <i class="fas fa-pencil-alt grayLink"></i>
                                    <g:message code="construction.edit"/>
                                </a>
                            </li>
                        </g:if>
                        <g:if test="${constructionResource && (unbundable || resource?.isDummy) && !splitPage}">
                            <li ${dataset?.locked || dataset?.verified ? "class=\"removeClicks\"" : ""}>
                                <a href="javascript:" class="bold"
                                   onclick="demolishConstruction('${uniqueConstructionIdentifier}','${trId}');">
                                    <g:message code="unbundle_construction"/>
                                </a>
                            </li>
                        </g:if>
                        <g:if test="${productDataListPage}">
                            <li ${dataset?.locked || dataset?.verified ? "class=\"removeClicks\"" : ""}>
                                <a href="javascript:" data-parentRowId="${trId}"
                                   onclick="openRenameResourceModal($(this).attr('data-parentRowId'),'${entity?.id}', '${indicator?.indicatorId}', '${query.queryId}','${mainSection?.sectionId}', '${question?.questionId}', '${fieldName}', '${resource.resourceId}', '${optimiResourceService.getUiLabel(resource)}')"><i
                                        class="icon-pencil"></i> ${message(code: 'add').capitalize()} / ${message(code: 'undeprecate').capitalize()} ${message(code: 'pdl.alias')}
                                </a>
                            </li>
                        </g:if>
                        <g:if test="${splitOrChangeLicensed && splitViewAllowed}">
                            <opt:splitOrChangeDataset originalResourceTableId="${resourceTableId}"
                                                      showGWP="${showGWP}" mainSectionId="${mainSection?.sectionId}"
                                                      preventChanges="${preventChanges}"
                                                      parentEntityId="${parentEntity?.id}"
                                                      indicatorId="${indicator?.indicatorId}" datasetManualId="${datasetId}"
                                                      childEntityId="${entity?.id}" queryId="${query?.queryId}"
                                                      locked="${dataset?.locked || dataset?.verified}"
                                                      sectionId="${mainSection?.sectionId}"
                                                      questionId="${question?.questionId}"
                                                      constructionId="${construction?.id}"
                                                      constructionResource="${constructionResource}"/>
                        </g:if>
                        <g:if test="${isManager && !productDataListPage && !constructionPage}">
                            <li class="lockDatasetLi">
                                <a href="javascript:" class="bold" onclick="toggleRowLocked(this, '${uniqueConstructionIdentifier ?: ""}','${message(code: "design.lock")}', '${message(code: "design.unlock")}')">
                                    <g:if test="${dataset?.locked}">
                                        <i class="fa fa-unlock" aria-hidden="true"></i>
                                    </g:if>
                                    <g:else>
                                        <i class="fa fa-lock" aria-hidden="true"></i>
                                    </g:else>
                                    <g:message code="${dataset?.locked ? 'design.unlock' : 'design.lock'}"/>
                                </a>
                            </li>
                        </g:if>
                        <g:if test="${constructionResource}">
                            <li class="removeResourceLi ${dataset?.locked ? "removeClicks" : ""}">
                                <a href="javascript:" data-parentRowId="${trId}"
                                   class="removeResourceRowButton${dataset?.locked ? " notRemoveable" : ""}"
                                   onclick="removeResource($(this).attr('data-parentRowId'), '${datasetId}', '${entity?.id}', '${indicator?.indicatorId}', '${uniqueConstructionIdentifier}', '${random}info');">
                                    <i class="icon-trash"></i> ${message(code: 'delete')}
                                </a>
                            </li>
                        </g:if>
                        <g:else>
                            <li class="removeResourceLi ${dataset?.locked ? "removeClicks" : ""}">
                                <a href="javascript:" data-parentRowId="${trId}"
                                   class="removeResourceRowButton${dataset?.locked ? " notRemoveable" : ""}${parentConstructionId ? ' hidden' : ''}"
                                   onclick="removeResource($(this).attr('data-parentRowId'), '${datasetId}', '${entity?.id}', '${indicator?.indicatorId}', null, '${random}info');">
                                    <i class="icon-trash"></i> ${message(code: 'delete')}
                                </a>
                            </li>
                        </g:else>
                        <g:if test="${!dataset?.unlockedFromVerifiedStatus}">
                            <li class="verifyDatasetLi hide">
                                <g:render template="/query/icons/verifyDatasetIcons"
                                          model="[forResourceRowDropdown: true, user: user, dataset: dataset, constructionResource: constructionResource,
                                                  uniqueConstructionIdentifier: uniqueConstructionIdentifier, verifiedProductFlag: verifiedProductFlag]"/>
                            </li>
                        </g:if>
                    </ul>
                </div>
            </g:if>
        </td>
    </g:if>
<%-- REL-304 comment out this feature, we do not need. Ask Arturs
    <g:elseif test="${parentConstructionId && resource?.importFile == "NMD 3 API" && !preventChanges && !disabled && !splitPage}">
        <td>
            <div class="btn-group">
                <a href="javascript:" class="dropdown dropdown-toggle bold"
                   data-toggle="dropdown"><g:message code="change"/>
                    <span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <li ${dataset?.locked || dataset?.verified ? "class=\"removeClicks\"" : ""}>
                        <a href="javascript:" data-parentRowId="${trId}"
                           onclick="openNMDProductEditModal(this, $(this).attr('data-parentRowId'), '${datasetId ? datasetId : manualId}', '${resource?.resourceId ?: ""}');">
                            <i class="fa fa-wrench"></i> Toon producten
                        </a>
                    </li>
                </ul></div>
        </td>
    </g:elseif>
 --%>
    <g:if test="${scalingParameters}">
        <g:each in="${scalingParameters}" var="scalingParameter">
            <input type="hidden" class="${scalingParameter.key}" value="${scalingParameter.value}"/>
        </g:each>
    </g:if>
    <g:if test="${eolProcessingType}">
        <input type="hidden" class="resourceEolProcessingType" value="${eolProcessingType}"/>
    </g:if>
</tr>