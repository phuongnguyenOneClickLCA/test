<%-- Copyright (c) 2012 by Bionova Oy --%>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <meta name="format-detection" content="telephone=no"/>
    <g:if test="${supportedFilters}">
        <asset:stylesheet src="filterCombobox.css"/>
    </g:if>
    <asset:javascript src="jquery.autocomplete.js?v=${grailsApplication.metadata.'app.version'}"/>
    <asset:javascript src="stupidtable.min.js" />
    <asset:javascript src="parsley.js"/>
    <g:set var="datasetService" bean="datasetService"/>

</head>
<body>
<g:form action="mapResolvedResources" method="post" name="saveResolver" novalidate="novalidate">
<g:hiddenField name="entityId" value="${entityId}" />
<g:hiddenField name="childEntityId" value="${childEntityId}" />
<g:hiddenField name="queryId" value="${queryId}" />
<g:hiddenField name="indicatorId" value="${indicator?.indicatorId}" />
<g:set var="questionService" bean="questionService" />

<div class="container">
    <g:if test="${warningMessage}">
        <div class="alert alert-warning">
            <button data-dismiss="alert" class="close" type="button">×</button>
            <strong>${warningMessage}</strong>
        </div>
    </g:if>
    <div class="screenheader">
        <h4> <opt:link controller="main" removeEntityId="true"><g:message code="main" /></opt:link>
            <sec:ifLoggedIn>
                <g:if test="${parentEntity}">
                    > <opt:link controller="entity" action="show" id="${parentEntity?.id}">
                    <g:abbr value="${parentEntity?.name}" maxLength="20" />
                </opt:link>
                    <g:if test="${entity}">
                        > <span id="childEntityName">${entity?.operatingPeriodAndName}</span>
                    </g:if>
                </g:if>
            </sec:ifLoggedIn>
            > <g:message code="import_data" /> <br/> </h4>
    <g:if test="${showSteps}">
        <opt:breadcrumbs current="${currentStep}"/>
    </g:if>
        <div class="pull-right hide-on-print">
            <a href="javascript:" class="btn" style="font-weight: normal;" onclick="fullScreenPopup('${createLink(controller: 'importMapper', action: 'listAmbiguousData')}');"><g:message code="entity.details.tab.datasummary" /></a>
            <g:link controller="importMapper" action="cancel" class="btn" style="font-weight: normal;" onclick="return modalConfirm(this);"
                    data-questionstr="${message(code: 'importMapper.index.cancel')}"
                    data-truestr="${message(code: 'yes')}" data-falsestr="${message(code: 'no')}"
                    data-titlestr="Cancel"><g:message code="cancel" /></g:link>
            <g:link controller="importMapper" action="generateExcelFromDatasets" params="[returnUserTo: 'resolver', entityId: entityId, childEntityId: childEntityId]" class="btn btn-primary" style="font-weight: normal;"><g:message code="results.expand.download_excel" /></g:link>
            <a href="javascript:" style="font-weight: normal;" id="notIdentifiedSaveMappings" onclick="saveMappings(this);" class="btn btn-primary" disabled="disabled">
                <g:message code="importMapper.save_mappings" />
            </a>
            <span>
                <img class="sustainedWarning"
                     id="memorizeWarning"
                     rel="popover"
                     hidden
                     data-trigger="hover"
                     data-placement="top"
                     data-content="${g.message(code: 'warning.save_mappings')}"
                     src="/app/assets/img/icon-warning.png"
                     style="max-width:16px;
                     vertical-align: middle;"
                     data-original-title="" title="">
            </span>
            <g:submitButton name="save" value="${message(code: 'continue')}" class="btn btn-primary" id="ifcContinue" onclick="doubleSubmitPrevention('saveResolver');" style="font-weight: normal;" />
        </div>
        <h1>${message(code: 'breadcrumbs.mapping')}</h1>
    </div>
</div>
    <g:if test="${supportedFilters}">
    <div class="container" id="quickFilterOptions" >
        <g:set var="random" value="${UUID.randomUUID().toString()}"/>
        <div class="screenheader">
            <table class="quickFilterTable">
                <tbody>
                <tr>
                <td class="pull-right quickFilterFunnel"><i class="fa fa-filter fa-2x" aria-hidden="true"></i>
                    <g:each in="${supportedFilters}" var="queryFilter">
                        <sec:ifAnyGranted roles="${queryFilter.userClass}">
                            <g:set var="random" value="${UUID.randomUUID().toString()}"/>
                        <td id="${queryFilter.resourceAttribute}td" class="quickFilterTableTd text-center"><div class="btn-group quickFilterTableGroup" id="${queryFilter.resourceAttribute}btnGroup" style="display:inline-block">
                            <span class="quickFilters bold"  id="${queryFilter.resourceAttribute}th"  onclick="quickFilterChoices('${queryFilter.resourceAttribute}td','quickFilter${queryFilter.resourceAttribute}', '${queryFilter.resourceAttribute}', '${queryFilter.localizedName}', '${indicator?.indicatorId}','${query?.queryId}', '${entityId}',null, 'importMapper','${resourceGroups}','${entity?.id}');">${queryFilter.localizedName} </span>
                            <select class="quickFilterSelect" data-resourceAttribute="${queryFilter.resourceAttribute}" style="width:${queryFilter?.filterWidth ? queryFilter.filterWidth : '170'}px;" onchange="saveUserFilters();" id="quickFilter${queryFilter.resourceAttribute}">
                            </select>
                        </div></td>
                        </sec:ifAnyGranted>
                    </g:each>
                    <td id="resetTdForFilter" style="vertical-align: middle"><a href="javascript:" id="resetAllImportFilters"><g:message code="query.reset_filter"/></a></td>
                </tr>
                </tbody></table>
        </div>
    </div>
    </g:if>

    <div class="container section" style="padding: 8px;">
        <i class="fa fa-question warningQuestionMark" style="margin-left: 15px;"></i> <p><g:message code ="importMapper.mapping_help"/></p>
        <p><g:message code="importMapper.resolver.ambiguous_warning" />. <g:message code="importMapper.resolver.units" />.</p>
    </div>

<div class="resolverWrapper">
<g:if test="${identifiedDatasets || notIdentifiedDatasets}">
    <div class="container section" id="identifiedContainer">
        <div class="sectionheader">
            <button class="pull-left sectionexpander"><i class="icon icon-chevron-down collapser"></i><i class="icon icon-chevron-right expander"></i></button>
            <h2 class="h2expander"><i class="fa fa-check" aria-hidden="true" style="color: #6b9f00;"></i> <g:message code="importMapper.resolver.identified_data" />: <span id="identifiedDatasetsAmountContainer">${identifiedDatasets?.size() ?: 0}</span><g:if test="${identifiedPercentual}"> / <g:formatNumber number="${identifiedPercentual}" format="###.##" /> <g:message code="importMapper.resolver.percentage_of_volume" /></g:if></h2>
        </div>

        <div class="sectionbody" style="display:none;">
            <table class="query_resource table-striped sortMe" id="identifiedTable"${!identifiedDatasets ? " style='display: none;min-width: 280px !important'" : 'style="min-width: 280px !important"'}>
                <thead>
                <tr>
                    <th data-sort="string"><g:message code="importMapper.material" /></th>
                    <th data-sort="string"><g:message code="importMapper.class" /></th>
                    <th data-sort="string"><g:message code="attachment.comment" /></th>

                    <g:each in="${additionalQuestions?.findAll({!"hidden".equals(it.inputType)})}" var="additionalQuestion"><%--
                        --%><th ${additionalQuestion.dataSort ? "data-sort='${additionalQuestion.dataSort}'" : "class=\"no-sort\""}>${additionalQuestion.localizedQuestion}</th><%--
                --%></g:each>

                    <th data-sort="int"><g:message code="query.quantity" /></th>
                    <th data-sort="int" ><g:message code="importMapper.resolver.percentage_of_total" /></th>
                    <th data-sort="string"><g:message code="resource.name" /></th>
                    <th>&nbsp;</th>
                    <th>${message(code: "mapping_basis")}</th>
                    <th><g:message code="importMapper.resolver.composite" /></th>
                    <th id="saveMappingsHeading"><g:message code="importMapper.save_mappings" /></th>
                    <th>&nbsp;</th>
                </tr>
                </thead>
                <tbody>
                <g:if test="${identifiedDatasets}">
                    <g:render template="identifiedRows" model="[datasets: identifiedDatasets]"/>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
</g:if>

<g:if test="${notIdentifiedDatasets}">
    <div class="container section" id="notIdentifiedContainer">
        <div class="sectionheader notIdentified">
            <div class="sectioncontrols pull-right">
                <a href="javascript:" data-truestr="${message(code: 'yes')}" data-falsestr="${message(code: 'cancel')}" onclick="return removeResolverRowByClassWithConfirm(this, 'onePercentage', 'notIdentifiedTable', 'notIdentifiedContainer');" class="btn btn-primary" data-titlestr="${message(code: 'warning')}" data-questionstr="${message(code: 'ifc.delete.under_percentage.question')}"><g:message code="importMapper.resolver.delete_all_under_percentage" /></a>
                <a href="javascript:" data-truestr="${message(code: 'yes')}" data-falsestr="${message(code: 'cancel')}" onclick="return removeResolverRowByClassWithConfirm(this, 'smallPercentage', 'notIdentifiedTable', 'notIdentifiedContainer');" class="btn btn-primary" data-titlestr="${message(code: 'warning')}" data-questionstr="${message(code: 'ifc.delete.under_promille.question')}"><g:message code="importMapper.resolver.delete_all_under_promille" /></a>
            </div>
            <button class="pull-left sectionexpander"><i class="icon icon-chevron-down collapser"></i><i class="icon icon-chevron-right expander"></i></button>
            <h2 class="h2expander" style="display: inline;"><i class="fa fa-wrench" aria-hidden="true" style="color: grey;"></i> <g:message code="importMapper.resolver.unidentified_problematic_data" />: <span id="notIdentifiedDatasetsAmountContainer">${notIdentifiedDatasets.size()}</span><g:if test="${notIdentifiedPercentual}"> / <g:formatNumber number="${notIdentifiedPercentual}" format="###.##" /> <g:message code="importMapper.resolver.percentage_of_volume" /></g:if></h2>
            <span style="display: inline; margin-left: 10px;">${message(code:'importMapper.resolver.unidentified_data.info')}</span>
        </div>

        <div class="sectionbody" id="notIdentifiedDatasetsDiv" style="display: none;">
            <table class="query_resource table-striped sortMe" id="notIdentifiedTable" >
                <thead>
                <tr class="upperlevelHeading"><th><h3><g:message code="importMapper.imported_data" /></h3></th><th>&nbsp;</th><th>&nbsp;</th><g:each in="${additionalQuestions?.findAll({!"hidden".equals(it.inputType)})}"><th>&nbsp;</th></g:each><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th><h3><g:message code="importMapper.map_data_to" /></h3></th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th></tr>
                <tr class="notIdentified">
                    <th class="importMapperTableBorderTop importMapperTableBorderLeft" data-sort="string"><g:message code="importMapper.material" /></th>
                    <th class="importMapperTableBorderTop" data-sort="string"><g:message code="importMapper.class" /></th>
                    <th class="importMapperTableBorderTop" data-sort="string"><g:message code="attachment.comment" /></th>

                    <g:each in="${additionalQuestions?.findAll({!"hidden".equals(it.inputType)})}" var="additionalQuestion"><%--
                        --%><th class="importMapperTableBorderTop">${additionalQuestion.localizedQuestion}</th><%--
                --%></g:each>

                    <th class="importMapperTableBorderTop" data-sort="int"><g:message code="query.quantity" /></th>
                    <th class="importMapperTableBorderTop" data-sort="int"><g:message code="importMapper.resolver.percentage_of_total" /></th>
                    <th class="importMapperTableBorderLeft">&nbsp;</th><th class="importMapperTableBorderLeft importMapperTableBorderTop">&nbsp;</th>
                    <th class="importMapperTableBorderTop"><g:message code="importMapper.target_resource" /></th>
                    <th class="importMapperTableBorderTop">&nbsp;</th><th class="importMapperTableBorderTop"><g:message code="importMapper.resolver.composite" /></th>
                    <th class="importMapperTableBorderTop importMapperTableBorderRight">&nbsp;</th>
                </tr>
                </thead>
                <tbody>
                <g:set var="resourceCache" value="${com.bionova.optimi.data.ResourceCache.init(notIdentifiedDatasets)}"/>
                <g:each status="i" in="${notIdentifiedDatasets}" var="dataset">
                    <g:set var="detailsLink" value="${dataset.importDisplayFields?.get(detailsKey)}" />
                    <tr id="notIdentified${dataset.manualId}" data-materialkey="${detailsLink}" data-datasetId="${dataset.manualId}" class="notIdentified${dataset.percentageOfTotal == null || dataset.percentageOfTotal < 0.1 ? ' smallPercentage' : ''}${dataset.percentageOfTotal == null || dataset.percentageOfTotal < 1 ? ' onePercentage' : ''}">
                        <td class="tdalignmiddle importMapperTableBorderLeft" style="min-width: 280px !important"><%--
                        --%><input type="hidden" class="notIdentifiedDatasetId" name="questionId.${dataset.manualId}" value="${dataset.questionId}" /><%--
                        --%>&nbsp;<span data-content="${detailsLink}" rel="popover" data-placement="top" data-trigger="hover"><g:abbr value="${org.springframework.web.util.HtmlUtils.htmlUnescape(detailsLink ?: "")}" maxLength="40" /></span><%--
                        --%><g:if test="${dataset.allImportDisplayFields}">&nbsp;
                                <a href="javascript:" class="openCombinedDisplayFields pull-right"
                                   id="${dataset.manualId}info"
                                   onclick="openCombinedDisplayFieldsResolver('${dataset.manualId}','${dataset.manualId}info', true)">
                                    <i class="far fa-file-alt fa-lg greenInfoBubble" aria-hidden="true"></i>
                                </a>
                        </g:if><%--
                        --%><queryRender:importMapperBimCheckerErrors dataset="${dataset}"/><%--
                    --%></td>
                        <td class="tdalignmiddle">
                            <span class="setHiddenValueForSort hidden"></span>
                            <g:set var="ifcClass" value="${dataset.trainingData?.get("CLASS")?.toUpperCase()}" />
                            <g:set var="classRemapChoices" value="${classRemapChoice?.allowedClassRemappings}" />
                            <g:if test="${ifcClass && classRemapChoices}">
                                <select data-datasetManualId="${dataset.manualId}" class="classRemapSelect" name="classRemapSelectDropdown">
                                    <g:if test="${!classRemapChoices*.toUpperCase().contains(ifcClass)}">
                                        <option value="${ifcClass}" selected>${ifcClass}</option>
                                    </g:if>
                                    <g:each in="${(List<String>) classRemapChoices}" var="choise">
                                        <option value="${choise}" ${choise.toUpperCase().equals(ifcClass) ? 'selected' : ''}>${choise.toUpperCase()}</option>
                                    </g:each>
                                </select>
                            </g:if>
                            <g:elseif test="${ifcClass}">
                                <g:abbr value="${ifcClass}" maxLength="20" />
                            </g:elseif>
                        </td>
                        <td class="tdalignmiddle">
                            <span class="setHiddenValueForSort hidden"></span>
                            <input type="text" style="margin-top: 5px;" class="resolverCommentField" value="${dataset.additionalQuestionAnswers?.get("comment")}" name="comment.${dataset.manualId}" />
                        </td>

                        <g:if test="${additionalQuestions}"><%--
                        --%><g:each in="${additionalQuestions}" var="additionalQuestion" status="additionalQuestionIndex"><g:if test="${!additionalQuestion.inGroupedQuestions()}"><%--
                            --%><g:if test="${additionalQuestion?.questionId && dataset.additionalQuestionAnswers?.get(additionalQuestion?.questionId)}"><%--
                                --%><g:set var="additionalQuestionAnswer" value="${dataset.additionalQuestionAnswers?.get(additionalQuestion.questionId)}" /><%--
                            --%></g:if><%--
                            --%><g:else><%--
                                --%><g:set var="additionalQuestionAnswer" value="" /><%--
                            --%></g:else><%--
                            --%><g:render template="/query/additionalquestion" model="[indicator: indicator, additionalQuestionAnswer:additionalQuestionAnswer, entity: entity, query:query, mainSectionId: dataset?.sectionId,
                                                                                       additionalQuestion: additionalQuestion, mainQuestion: query?.getQuestionsBySection(dataset?.sectionId)?.find({ it.questionId == dataset?.questionId }), resource: dataset?.resource, resourceId: dataset?.resourceId,
                                                                                       allowVariableThickness: dataset?.resource?.allowVariableThickness, transportUnit: additionalQuestions.find({it.questionId?.contains('transportDistance')})?.localizedUnit, disableThickness: dataset?.userGivenUnit && !'m2'.equalsIgnoreCase(dataset?.userGivenUnit),
                                                                                       datasetId: dataset?.manualId,  trId: 'identified' + dataset?.manualId, importMapperAdditionalQuestion: true, tableFormat: true]" /><%--
                        --%></g:if></g:each><%--
                    --%></g:if>

                        <td class="tdalignmiddle"><%--
                        --%><g:set var="thickness" value="${dataset.additionalQuestionAnswers?.get("thickness_mm") ? formatNumber(number:dataset.additionalQuestionAnswers.get("thickness_mm"),format: "###.##") : ''}" />
                        <g:if test="${dataset.quantity == null}">
                            <input type="text" value="" name="quantity.${dataset.manualId}" id="quantity${dataset.manualId}" class="numeric input-mini" />
                        </g:if>
                        <g:else>
                           <g:set var="formattedQuantity" value="${dataset.quantity > 10 ? formatNumber(number:dataset.quantity, format:"#") : formatNumber(number:dataset.quantity, format:"###.##")}" /><%--
                       --%><span class="quantityContainer hidden"><%--
                        --%>${formattedQuantity} ${dataset.userGivenUnit ?: ''}${dataset.area_m2 ? " / " + "${formatNumber(number:dataset.area_m2, format:"#")} M2" : ''}${thickness ? ' / ' + thickness + ' mm': ''}<%--
                       --%></span>
                            <g:if test="${dataset.area_m2||dataset.volume_m3||dataset.mass_kg}">
                                <select data-datasetManualId="${dataset.manualId}" class="resolvedUnitSelect" name="resolvedUnitSelectDropdown">
                                    <option selected="selected" data-quantity="${dataset.quantity}" value="${dataset.userGivenUnit ?: ''}">${formattedQuantity} ${dataset.userGivenUnit ?: ''}</option>
                                    <g:if test="${dataset.area_m2}">
                                        <option data-quantity="${dataset.area_m2}" value="m2">${formatNumber(number:dataset.area_m2, format:"###.##")} m2${thickness ? ' / ' + thickness + ' mm': ''}</option>
                                    </g:if>
                                    <g:if test="${dataset.volume_m3}">
                                        <option data-quantity="${dataset.volume_m3}" value="m3">${formatNumber(number:dataset.volume_m3, format:"###.##")} m3</option>
                                    </g:if>
                                    <g:if test="${dataset.mass_kg}">
                                        <option data-quantity="${dataset.mass_kg}" value="m3">${formatNumber(number:dataset.mass_kg, format:"###.##")} kg</option>
                                    </g:if>
                                </select>
                            </g:if>
                            <g:else>
                                ${formattedQuantity} ${dataset.userGivenUnit ?: ''}${thickness ? ' / ' + thickness + ' mm': ''}
                            </g:else>
                        </g:else><%--
                    --%></td>
                        <td class="tdalignmiddle" style="padding-right: 5px;${dataset.percentageOfTotal && dataset.percentageOfTotal > 1 ? ' font-weight: bold;' : ''}"><%--
                            --%><span class="datasetShareContainer">${dataset.percentageOfTotal != null ? formatNumber(number:dataset.percentageOfTotal, format:"###.##") :''}</span><span>${dataset.percentageOfTotal != null ? '&nbsp;%' : ''}</span><%--
                        --%></td>
                        <td class="importMapperTableBorderLeft">&nbsp;&nbsp;</td>
                        <td class="mappingStatus importMapperTableBorderLeft">
                            <i class="icon-hammer" style="margin-top: 10px; margin-left: 10px; margin-right: -10px;"></i>
                        </td>
                        <g:set var="datasetResourceInfo" value="${UUID.randomUUID().toString()}" />
                        <td class="tdalignmiddle selectNotdefined">
                        <g:set var="q" value="${questions?.find({it.questionId.equals(dataset.questionId)})}"/>
                        <g:set var="resourceGroup" value="${q?.resourceGroups ? q.resourceGroups.join(",") : resourceGroups}"/>
                            <g:if test="${resourceGroup}">
                                <div class="input-append">
                                    <g:set var="showDataForUnitText" value="${(dataset?.userGivenUnit) ? message(code: "importMapper.showDataForUnit", args:[dataset?.userGivenUnit]) : null}" />
                                    <input type="text"
                                           name="resourceId${dataset.manualId}"
                                           class="autocomplete mediumAutocompleteWidth"
                                           data-parentId="${entity?.id}"
                                           data-entityId="${entity?.id}"
                                           data-unit="${dataset.userGivenUnit}"
                                           data-indicatorId="${indicator?.indicatorId}"
                                           data-queryId="${dataset.queryId}"
                                           data-sectionId="${dataset.sectionId}"
                                           data-questionId="${dataset.questionId}"
                                           data-targetBubble="${datasetResourceInfo}info"
                                           placeholder="${message(code:'importMappertypeahead.info')}"
                                           data-resourceGroups="${resourceGroup}"
                                           class="input-xlarge"
                                           autocomplete="off"
                                           data-accountId="${account?.id ?: ""}"
                                           onchange="removeBorder(this, '${inputWidth}');"
                                           id="autocomplete${dataset.manualId}" ${disabledAttribute}>
                                    <a tabindex="-1" title="" href="javascript:" class="add-on showAllResources"
                                       onclick="showAllImportMapperResources(this, '${resourceGroup}', null, '${showDataForUnitText}');">
                                        <i class="icon-chevron-down"></i>
                                    </a>
                                    <input type="hidden" class="resourceIdContainer" name="resourceId.${dataset.manualId}" id="resourceId${dataset.manualId}" />
                                    <input type="hidden" class="userGivenUnitContainer" name="userGivenUnit.${dataset.manualId}" id="userGivenUnit${dataset.manualId}" value="${dataset.userGivenUnit}" />
                                    <input type="hidden" class="userGivenQuantityContainer" name="userGivenQuantity.${dataset.manualId}" id="userGivenQuantity${dataset.manualId}" value="${dataset.quantity}" />
                                    <input type="hidden" class="resourceUnitsContainer" value="" />
                                </div>
                            </g:if>
                            <g:else>
                                <g:set var="resources" value="${questionService.getFilteredResources(parentEntity, indicator, queryId, datasetService.getQuestion(dataset)) ?: resources}" />
                                <g:if test="${dataset.userGivenUnit && resources}">
                                    <g:set var="resources" value="${resources.findAll({(it.combinedUnits && it.combinedUnits.collect({it.toUpperCase()}).contains(dataset.userGivenUnit.toUpperCase()))})}" />
                                </g:if>
                                <select name="resourceId.${dataset.manualId}" id="resourceId${dataset.manualId}" class="autocompletebox${!dataset.resourceId ? ' redBorder' : ''}" required="true">
                                    <g:if test="${resources}">
                                        <option></option>
                                        <g:each in="${resources}" var="resource">
                                            <option value="${resource.resourceId}" ${resource.resourceId.equals(dataset.resourceId) ? ' selected=\"selected\"' : ''}>${resource.uiLabel} (${resource.unitForData})</option>
                                        </g:each>
                                    </g:if>
                                </select>
                            </g:else>
                        </td>
                        <td class="tdalignmiddle">
                            <a href="javascript:" class="infoBubble"
                               rel="resource_sourceListing"
                               id="${datasetResourceInfo}info"
                               data-profileId="${dataset.profileId ? dataset.profileId: resourceCache.getResource(dataset)?.profileId}"
                               onclick="openSourceListing('${indicator?.indicatorId}','${dataset.resourceId}','${dataset.questionId}', '${dataset.profileId ? dataset.profileId: resourceCache.getResource(dataset)?.profileId}', '${entity?.id}','${Boolean.TRUE}','${datasetResourceInfo}info','${message(code: 'resource.sourcelisting_no_information_available')}')">
                                <i class="fa fa-question greenInfoBubble" aria-hidden="true"></i>
                            </a>
                        </td>
                        <td class="tdalignmiddle"><input style="margin-right: 30px;" type="checkbox" name="composite.${dataset.manualId}" /> <a href="javascript:" onclick="removeElementById('notIdentified${dataset.manualId}'); removeResolverContainer('notIdentifiedTable', 'notIdentifiedContainer'); recalculateUnidentified();"><strong>${message(code:'delete')}</strong></a></td>
                        <td class="tdalignmiddle importMapperTableBorderRight">&nbsp;</td>
                    </tr>
                </g:each>
                </tbody>
                <tfoot>
                <tr class="footerRow importMapperTableBorderTop"><th></th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th></tr>
                </tfoot>
            </table>
        </div>
    </div>
</g:if>

<g:if test="${identifiedDescriptiveDatasets || notIdentifiedDescriptiveDatasets}">
    <div class="container section" id="identifiedDescriptiveContainer">
        <div class="sectionheader">
            <button class="pull-left sectionexpander"><i class="icon icon-chevron-down collapser"></i><i class="icon icon-chevron-right expander"></i></button>
            <h2 class="h2expander"><i class="fa fa-check" aria-hidden="true" style="color: #6b9f00;"></i> <g:message code="importMapper.resolver.identified_descriptive_data" />: <span id="identifiedDescriptiveDatasetsAmountContainer">${identifiedDescriptiveDatasets?.size() ?: 0}</span><g:if test="${identifiedDescriptivePercentual}"> / <g:formatNumber number="${identifiedDescriptivePercentual}" format="###.##" /> <g:message code="importMapper.resolver.percentage_of_volume" /></g:if></h2>
        </div>

        <div class="sectionbody" style="display:none;">
            <table class="table table-striped sortMe" id="identifiedDescriptiveTable"${!identifiedDescriptiveDatasets ? " style='display: none;min-width: 280px !important'" : 'style="min-width: 280px !important"'}>
                <thead>
                <tr>
                    <th data-sort="string" style="width: 20%"><g:message code="importMapper.class" /></th>
                    <th data-sort="string"><g:message code="importMapper.content" /></th>
                </tr>
                </thead>
                <tbody>
                <g:if test="${identifiedDescriptiveDatasets}">
                    <g:render template="identifiedDescriptiveRows" model="[datasets: identifiedDescriptiveDatasets]"/>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
</g:if>
<g:if test="${notIdentifiedDescriptiveDatasets}">
    <div class="container section collapsed" id="notIdentifiedDescriptiveContainer">
        <div class="sectionheader notIdentified">
            <button class="pull-left sectionexpander"><i class="icon icon-chevron-down collapser"></i><i class="icon icon-chevron-right expander"></i></button>
            <h2 class="h2expander" style="display: inline;"><i class="fa fa-wrench" aria-hidden="true" style="color: grey;"></i> <g:message code="importMapper.resolver.unidentified_descriptive_data" />: <span id="notIdentifiedDatasetsAmountContainer">${notIdentifiedDescriptiveDatasets.size()}</span><g:if test="${notIdentifiedDescriptivePercentual}"> / <g:formatNumber number="${notIdentifiedDescriptivePercentual}" format="###.##" /> <g:message code="importMapper.resolver.percentage_of_volume" /></g:if></h2>
        </div>

        <div class="sectionbody" id="notIdentifiedDescriptiveDatasetsDiv">
            <table class="table table-striped sortMe" id="notIdentifiedDescriptiveTable" >
                <thead>
                <tr class="notIdentified">
                    <th data-sort="string" style="width: 20%"><g:message code="importMapper.class" /></th>
                    <th data-sort="string"><g:message code="importMapper.content" /></th>
                </tr>
                </thead>
                <tbody>
                <g:each status="i" in="${notIdentifiedDescriptiveDatasets}" var="dataset">
                    <g:set var="ifcClass" value="${dataset.trainingData?.get("CLASS")?.toUpperCase()}"/>
                    <tr id="notIdentified${dataset.manualId}">
                        <td class="tdalignmiddle">
                            <span class="setHiddenValueForSort hidden"></span>
                            <g:set var="classRemapChoices" value="${classRemapChoice?.allowedDescriptiveClassRemappings}" />
                            <g:if test="${ifcClass && classRemapChoices}">
                                <select data-datasetManualId="${dataset.manualId}" class="classRemapSelect" name="classRemapSelectDropdown">
                                    <g:if test="${!classRemapChoices*.toUpperCase().contains(ifcClass)}">
                                        <option value="${ifcClass}" selected><g:message code="importMapper.OTHER"/></option>
                                    </g:if>
                                    <g:each in="${(List<String>) classRemapChoices}" var="choise">
                                        <option value="${choise}" ${choise.toUpperCase().equals(ifcClass) ? 'selected' : ''}>${choise.toUpperCase()}</option>
                                    </g:each>
                                </select>
                            </g:if>
                            <g:elseif test="${ifcClass}">
                                <g:abbr value="${ifcClass}" maxLength="20" />
                            </g:elseif>
                            <g:if test="${dataset.allImportDisplayFields}">&nbsp;<a href="javascript:" id="${dataset.manualId}info" onclick="openCombinedDisplayFieldsResolver('${dataset.manualId}','${dataset.manualId}info')" class="pull-right"><i class="far fa-file-alt fa-lg greenInfoBubble" aria-hidden="true"></i></a></g:if>
                            <queryRender:importMapperBimCheckerErrors dataset="${dataset}"/>
                        </td>
                        <td class="tdalignmiddle" style="min-width: 280px !important;">
                            <input type="hidden" name="questionId.${dataset.manualId}" value="${dataset.questionId}" />
                            <g:set var="answer" value="${dataset.answerIds?.getAt(0)}" />
                            <input type="text" value="${answer ?: ''}" name="answerIds.${dataset.manualId}" id="answerIds${dataset.manualId}" class="input-xlarge"/>
                        </td>
                    </tr>
                </g:each>
            </table>
        </div>
    </div>
</g:if>
</g:form>
</div>

<g:if test="${resourceTypeFilter}">
    <div class="nestedSelectContainer hidden"><ul class="nestedSelect" id="nestedFilter${resourceTypeFilter?.resourceAttribute}"></ul></div>
</g:if>
<div class="modal large hide" id="sendMeDataModal"></div>

<script type="text/javascript">
    $(document).ready(function() {
        ignoreWarningPopover();
        var $parent = $('#saveResolver');

        $parent.on('click', '.hideWarningImportMapper', function() {
            hideWarningImportMapper($(this).attr('data-warningThicknessId'), $(this).attr('data-manualId'))
        });
        trackInputChanges();
        sortableImportTables();
        appendSortignHeads();
        $('.classRemapSelect').select2({
            width : '80%',
            minimumResultsForSearch: -1
        }).on('select2:select', function () {
            var thisElem = $(this);
            var chosenClass = thisElem.val();
            var datasetManualId = thisElem.attr('data-datasetManualId');

            if (chosenClass && datasetManualId) {
                $.ajax({
                    data: 'chosenClass=' + chosenClass + '&datasetManualId=' + datasetManualId + '&parentEntityId=${parentEntity?.id?.toString()}',
                    type: 'POST',
                    url: '/app/changeclass',
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                    }
                });
            }
        });

        $('.resolvedUnitSelect').select2({
            minimumResultsForSearch: -1
        }).on('select2:select', function () {
            var selectedOption = $(this).children("option:selected");
            var selection = $(selectedOption).val();

            var datasetManualId = $(this).attr('data-datasetManualId');
            var row = $(this).closest('tr');
            var autocomplete = $(row).find('.autocomplete');

            if (autocomplete.length) {
                autocomplete.attr("data-unit", selection);
                var params = getImportMapperAutocompleteParams(autocomplete);
                var options = {
                    params: params
                };
                $(autocomplete).devbridgeAutocomplete().setOptions(options);
            }

            var unitsContainer = $(row).find('.resourceUnitsContainer');

            $(row).find('.userGivenQuantityContainer').val($(selectedOption).attr('data-quantity'));
            $(row).find('.userGivenUnitContainer').val(selection);
            if (unitsContainer.length && unitsContainer.val()) {
                var arrayUnits = unitsContainer.val().match( /(?=\S)[^,]+?(?=\s*(,|$))/g );

                if (arrayUnits.indexOf(selection) === -1) {
                    $(autocomplete).val("");
                    $(autocomplete).addClass("redBorder");
                    $(autocomplete).removeClass("notIdentifiedSaveMapping");
                    $(autocomplete).removeClass("retrainForThisUser");
                    $(row).find('.resourceIdContainer').val("incompatible");
                    $(row).find('.resourceUnitsContainer').val("");
                    var status = $(row).find('.mappingStatus');

                    if (status.length) {
                        status.text('');
                        status.append("<i class=\"icon-hammer\" style=\"margin-top: 10px; margin-left: 10px; margin-right: -10px;\"></i>");
                    }
                }
            }
        });

        initImportMapperAutocompletes('${resourceGroups}', '${entity?.id}');
        initImportMapperRemapAutocompletes('${resourceGroups}', '${entity?.id}');
        <g:if test="${supportedFilters}">
        quickFiltersSelect2('${message(code: 'quickFilter.placeholder')}');
        $('.quickFilters').trigger('click');

        $('body').on({
            mouseenter: function () {
                var highlighted_item = $(this);
                if ($(this).attr("id").indexOf("resourceType") !== -1) {
                    var select2Box = $('.nestedSelectContainer');
                    new Tether({
                        element: select2Box,
                        target: highlighted_item,
                        attachment: 'top left',
                        targetAttachment: 'top left',
                        targetOffset:"0, ${resourceTypeFilter?.filterWidth?.toInteger() - 30}px"

                    });
                    var resourceType = $(highlighted_item).attr('data-resourceTypeId');
                    if ($(highlighted_item).attr("data-haschildren") === "true") {
                        nestedFilterChoices('nestedFilter${resourceTypeFilter.resourceAttribute}', '${resourceTypeFilter.resourceAttribute}', resourceType, '${query?.queryId}', '${indicator?.indicatorId}', null, null, null, highlighted_item);
                    }else {
                        $(select2Box).addClass('hidden');
                    }
                }
            }
        }, '.select2-results__option--highlighted');

        $('body').on({
            click: function () {
                var select2Box = $('.nestedSelectContainer');
                $(select2Box).addClass('hidden');

            }
        });

        $('.nestedSelectContainer').on({
            mouseleave: function () {
                var select2Box = $('.nestedSelectContainer');
                $(select2Box).addClass('hidden');
                $('#nestedFilter${resourceTypeFilter.resourceAttribute}').empty();
            }
        });
        </g:if>
        $('.sectionexpander').on('click', function (event) {
            event.preventDefault();
            var section = $(this).parent().parent();
            $(section).toggleClass('collapsed');
            $(section).find('.sectionbody').fadeToggle(1);
        });

        $('.h2expander').on('click', function (event) {
            event.preventDefault();
            var section = $(this).parent().parent();
            $(section).toggleClass('collapsed');
            $(section).find('.sectionbody').fadeToggle(1);
        });

        <g:if test="${expandNotIdentified}">
            $('#notIdentifiedContainer').toggleClass('collapsed').find('.sectionbody').fadeToggle(1);
        </g:if>

        $('#resetAllImportFilters').on("click", function() {
            $('.quickFilterSelect').empty();
            saveUserFilters();
            var quickFilterTds = $('.quickFilterTableGroup');
            $(this).hide();
            $('.quickFilterTableTd').addClass("removeClicks");
            $('.quickFilterTableTd').removeClass('filterChosenHighlight');

            $('#resetTdForFilter').append('<i id="loadingReset" class="fa fa-cog fa-2x fa-spin primary"></i>');
            setTimeout(function () {
                $('.quickFilters').trigger("click");
                $('#loadingReset').remove();
                $('#resetAllImportFilters').show();
                $('.quickFilterTableTd').removeClass("removeClicks");


            },1000);
        });

        <g:if test="${supportedFilters}">
            // Stick the #nav to the top of the window
            var nav = $('#quickFilterOptions');
            var screenheader = $('#screenheader');
            var navHomeY = nav.offset().top;
            var isFixed = false;
            var $w = $(window);
            $w.on('scroll', function () {
                var scrollTop = $w.scrollTop();
                var shouldBeFixed = scrollTop > navHomeY;

                if (shouldBeFixed && !isFixed) {
                    nav.css({
                        position: 'fixed',
                        top: '54px',
                        'z-index': '11',
                        left: nav.offset().left,
                        width: nav.width()
                    });
                    screenheader.css({'margin-top': '0'});
                    isFixed = true;
                }
                else if (!shouldBeFixed && isFixed) {
                    nav.css({
                        position: 'static'
                    });
                    isFixed = false;
                    screenheader.css({'margin-bottom': '0'});
                }
            });

            $(".mainmenu").on('click', function (event) {
                $(window).scrollTop(0);
            });
        </g:if>

        var	steps = $(".step");

        $('#ifcContinue').on("click", function() {
            $.each( steps, function( i ) {
                if (!$(steps[i]).hasClass('current') && !$(steps[i]).hasClass('completed')) {
                    $(steps[i]).addClass('current');
                    $(steps[i - 1]).removeClass('current').addClass('completed');
                    return false;
                }
            })
        });
    });

    function enableSaveButtonForIdentifiedData() {
        var identifiedTableElement = document.getElementById('identifiedTable');
        if(window.getComputedStyle(identifiedTableElement).display !== "none") {
            $('#notIdentifiedSaveMappings').attr('disabled', false);
        }
    }

    var previousClass;

    function selectAll(rowClass) {
        if (!previousClass) {
            previousClass = rowClass;
        }
        var foundRows = document.getElementsByClassName(rowClass);

        if (foundRows) {
            $(foundRows).each(function() {
                 var row = this;

                $('input[class=selectCheckbox]', this).each(function() {
                       if($(this).prop('checked') && previousClass == rowClass) {
                           $(this).prop('checked', false);
                           $(row).removeClass('checked');
                       } else {
                           $(this).prop('checked', true);
                           $(row).addClass('checked');
                       }
                });
            });
        }
        previousClass = rowClass;
    }

    function selectAllComposite(rowClass) {
        var foundRows = document.getElementsByClassName(rowClass);

        if (foundRows) {
            $(foundRows).each(function() {
                var row = this;

                $('input[class=selectCheckbox]', this).each(function() {
                    if(!$(this).prop('checked')) {
                        $(this).prop('checked', true);
                        $(row).addClass('checkedComposite');
                    }
                });
            });
        }
    }

    function toggleClass(checkboxObject) {
        if (checkboxObject) {
            var row = $(checkboxObject).closest('tr');

            if($(checkboxObject).prop('checked')) {
                $(row).addClass('checked');
            } else {
                $(row).removeClass('checked');
            }
        }
    }

    function saveMappings(saveMappingsButton) {
        if (!$(saveMappingsButton).attr('disabled')) {
            var unidentifiedData = $('.notIdentifiedSaveMapping');
            var remapAlreadyMapped = $('.retrainForThisUser');
            var trainingDataCountry = "${trainingDataCountry ?: ''}";

            if (unidentifiedData || remapAlreadyMapped) {
                var datasetsToSave = {};
                var remappedIdentified = {};

                if (unidentifiedData) {
                    unidentifiedData.each(function () {
                        var resourceAndProfileId = {};
                        var row = $(this).closest('tr');

                        if (row) {
                            var resourceId = row.find('.infoBubble').attr('data-resourceId');
                            var profileId = row.find('.infoBubble').attr('data-profileId');
                            var comment = row.find('.resolverCommentField').val();
                            var unit = row.find('.userGivenUnitContainer').val();
                            var quantity = row.find('.userGivenQuantityContainer').val();
                            var queryAndManualid = row.find('.notIdentifiedDatasetId').attr('name');
                            row.remove();
                            var datasetId;
                            $(this).removeClass('notIdentifiedSaveMapping');

                            if (queryAndManualid && queryAndManualid.indexOf('.') >= 0) {
                                datasetId = queryAndManualid.split('.')[1];
                            }

                            if (resourceId && profileId && datasetId) {
                                resourceAndProfileId["resourceId"] = resourceId;
                                resourceAndProfileId["profileId"] = profileId;
                                resourceAndProfileId["comment"] = comment;
                                resourceAndProfileId["unit"] = unit;
                                resourceAndProfileId["quantity"] = quantity;
                                datasetsToSave[datasetId] = resourceAndProfileId;
                            }
                        }
                    });
                }

                if (remapAlreadyMapped) {
                    remapAlreadyMapped.each(function () {
                        if (!$(this).is(':disabled')) {
                            var resourceAndProfileId = {};
                            var row = $(this).closest('tr');

                            if (row) {
                                var rememberMappingCheckbox = row.find('.rememberMappingCheckbox');

                                if ($(rememberMappingCheckbox).length && $(rememberMappingCheckbox).is(":visible") && $(rememberMappingCheckbox).is(":checked")) {
                                    var resourceId = row.find('.infoBubble').attr('data-resourceId');
                                    var profileId = row.find('.infoBubble').attr('data-profileId');
                                    var datasetId = $(this).attr('name');

                                    if (datasetId && datasetId.indexOf('.') >= 0) {
                                        datasetId = datasetId.split('.')[1];

                                        if (resourceId && profileId && datasetId) {
                                            resourceAndProfileId["resourceId"] = resourceId;
                                            resourceAndProfileId["profileId"] = profileId;
                                            remappedIdentified[datasetId] = resourceAndProfileId;
                                        }
                                    }
                                }
                            }
                        }
                    })
                }

                if (!$.isEmptyObject(datasetsToSave) || !$.isEmptyObject(remappedIdentified)) {
                    $.ajax({
                        async: true, type: 'POST',
                        url: "/app/saveimportmappermappings",
                        data: {
                            datasets: JSON.stringify(datasetsToSave),
                            remappedIdentified: JSON.stringify(remappedIdentified),
                            entityId: "${entityId}",
                            childEntityId: "${childEntityId}",
                            trainingDataCountry: trainingDataCountry},
                        success: function (data) {
                            if (data) {
                                $('#identifiedTable tbody').append(data);
                                $('.classRemapSelectIdentified').select2({
                                    minimumResultsForSearch: -1
                                }).on('select2:select', function () {
                                    var thisElem = $(this);
                                    var chosenClass = thisElem.val();
                                    var datasetManualId = thisElem.attr('data-datasetManualId');

                                    if (chosenClass && datasetManualId) {
                                        $.ajax({
                                            data: 'chosenClass=' + chosenClass +
                                                '&datasetManualId=' + datasetManualId +
                                                '&parentEntityId=${parentEntity?.id?.toString()}',
                                            type: 'POST',
                                            url: '/app/changeclass',
                                            error: function (XMLHttpRequest, textStatus, errorThrown) {
                                            }
                                        });
                                    }
                                });
                                $('.resolvedUnitSelectIdentified').select2({
                                    minimumResultsForSearch: -1
                                }).on('select2:select', function () {
                                    var selectedOption = $(this).children("option:selected");
                                    var selection = $(selectedOption).val();

                                    var row = $(this).closest('tr');
                                    var autocomplete = $(row).find('.autocomplete');

                                    if (autocomplete.length) {
                                        autocomplete.attr("data-unit", selection);
                                    }

                                    var unitsContainer = $(row).find('.resourceUnitsContainer');

                                    $(row).find('.userGivenQuantityContainer').val($(selectedOption).attr('data-quantity'));
                                    $(row).find('.userGivenUnitContainer').val(selection);

                                    if (unitsContainer.length && unitsContainer.val()) {
                                        var arrayUnits = unitsContainer.val().match( /(?=\S)[^,]+?(?=\s*(,|$))/g );

                                        if (arrayUnits.indexOf(selection) === -1) {
                                            $(autocomplete).val("");
                                            $(autocomplete).addClass("redBorder");
                                            $(autocomplete).removeClass("notIdentifiedSaveMapping");
                                            $(autocomplete).removeClass("retrainForThisUser");
                                            $(row).find('.resourceIdContainer').val("incompatible");
                                            $(row).find('.resourceUnitsContainer').val("");
                                            var status = $(row).find('.mappingStatus');

                                            if (status.length) {
                                                status.text('');
                                                status.append(
                                                    "<i class=\"icon-hammer\" style=\"margin-top: 10px; margin-left: 10px; margin-right: -10px;\"></i>"
                                                );
                                            }
                                        }
                                    }
                                });
                                initImportMapperAutocompletes('${resourceGroups}', '${entity?.id}');
                                initImportMapperRemapAutocompletes('${resourceGroups}', '${entity?.id}', '${true}');
                                recalculateUnidentified();
                                recalculateIdentified();
                            }
                        }
                    });
                }
                setTimeout(function () {
                    Swal.fire(
                        "${message(code: 'dataLoadingFeature.success')}",
                        "${message(code: 'importMapper.resolver.saveMappings')}",
                        "success"
                    )
                }, 250);
            }
            $(saveMappingsButton).attr('disabled',true)
            disableMemorizeWarning()
        }
    }

    function disableMemorizeWarning() {
        let memorizeWarning = document.getElementById('memorizeWarning');
        if (memorizeWarning.hasAttribute("hidden") === false) {
            memorizeWarning.setAttribute("hidden", 'true');
        }
    }

    function recalculateUnidentified() {
        var rows = $('#notIdentifiedTable > tbody > tr').length;
        $('#notIdentifiedDatasetsAmountContainer').text(parseInt(rows));
        if (parseInt(rows) === 0) {
            $('#notIdentifiedTable').hide();
        }
        recalculateBreadcrumb();
    }

    function recalculateIdentified() {
        var rows = $('#identifiedTable > tbody > tr').length;
        $('#identifiedDatasetsAmountContainer').text(parseInt(rows));
        if (parseInt(rows) > 0 && $('#identifiedTable').is(':hidden')) {
            $('#identifiedTable').show();
        }
        recalculateBreadcrumb();
    }

    function recalculateBreadcrumb() {
        var urows = $('#identifiedTable > tbody > tr').length;
        var irows = $('#notIdentifiedTable > tbody > tr').length;
        var identified = $('#identifiedDatapoints');
        var unidentified = $('#unidentifiedDatapoints');
        if (identified.length) {
            identified.text(parseInt(urows));
        }

        if (unidentified.length) {
            unidentified.text(parseInt(irows));
        }
    }

    function applySameMapping(staticFullName, clickedRow, resourceId, parsedProfileId, allowedUnits, alreadyIdentifiedRows) {
        var value = staticFullName;
        var row = $(clickedRow);
        var parsedResourceId = resourceId;
        var profileId = parsedProfileId;

        var currentlyIdentifiedMaterial = row.attr('data-materialkey');
        var currentlyIdentifiedId = row.attr('id');
        var applyMappingToOtherRowsHtml = "" +
            "<div><h1><g:message code="importMapper.resolver.suggest_mapping_heading"/></h1><g:message code="importMapper.resolver.suggest_mapping_body"/></div><br/><div>" +
            "               <table class='table-striped' style='table-layout: fixed; margin-left: 20px; width: 750px;'>" +
            "                   <thead>" +
            "                       <tr>\n" +
            "                           <th style='text-align: left;'><g:message code="importMapper.material" /></th>\n" +
            "                           <th style='text-align: left;'><g:message code="importMapper.class" /></th>\n" +
            "                           <th style='text-align: left; width: 250px;'><g:message code="attachment.comment" /></th>\n" +
            "                           <th style='text-align: left; width: 115px;'><g:message code="query.quantity" /></th>\n" +
            "                           <th style='width: 95px;'><a id=\"selectBtn\" class=\"btn btn-primary select\" style=\"margin-left: 10px; !important;\" onclick=\"selectAllMappingCheckboxes()\"><g:message code="result.graph_toggle" /></a></th>\n" +
            "                       </tr>" +
            "                   </thead>" +
            "                   <tbody>";

        var showSwal = false;
        var arrayUnits = allowedUnits.match( /(?=\S)[^,]+?(?=\s*(,|$))/g );

        if (alreadyIdentifiedRows) {
            $.each( $('#identifiedTable > tbody > tr'), function() {
                var retrainForThisUser = $(this).find('.retrainForThisUser');

                if (!retrainForThisUser.length) {
                    var thisMaterialKey = $(this).attr('data-materialkey');
                    var thisId = $(this).attr('id');
                    var thisComment = $(this).find('.resolverCommentField').val();
                    var thisQuantity = $(this).find('.quantityContainer').text();
                    var thisClass = $(this).find('.classRemapSelect').val();
                    var thisUnit = $(this).find('.userGivenUnitContainer').val();
                    if (currentlyIdentifiedMaterial === thisMaterialKey && currentlyIdentifiedId !== thisId && arrayUnits && arrayUnits.indexOf(thisUnit) > -1) {
                        showSwal = true;
                        applyMappingToOtherRowsHtml = applyMappingToOtherRowsHtml + "" +
                            "<tr><td>" + thisMaterialKey + "</td><td>" + thisClass + "</td><td>" + thisComment + "</td><td>" + thisQuantity + "</td><td style='text-align: center;'><input style='width: 25px; height: 17px;' type='checkbox' onchange=\"copyMappingToUnidentifiedRow('"+thisId+"',this)\" class='mappingCheckbox' id='"+thisId+"' /></td></tr>";
                    }
                }
            });
        } else {
            $.each( $('#notIdentifiedTable > tbody > tr'), function() {
                var notIdentified = $(this).hasClass('notIdentified');

                if (notIdentified) {
                    var thisMaterialKey = $(this).attr('data-materialkey');
                    var thisId = $(this).attr('id');
                    var thisComment = $(this).find('.resolverCommentField').val();
                    var thisQuantity = $(this).find('.quantityContainer').text();
                    var thisClass = $(this).find('.classRemapSelect').val();
                    var thisUnit = $(this).find('.userGivenUnitContainer').val();

                    if (currentlyIdentifiedMaterial === thisMaterialKey && currentlyIdentifiedId !== thisId && arrayUnits && arrayUnits.indexOf(thisUnit) > -1) {
                        showSwal = true;
                        applyMappingToOtherRowsHtml = applyMappingToOtherRowsHtml + "" +
                            "<tr><td>" + thisMaterialKey + "</td><td>" + thisClass + "</td><td>" + thisComment + "</td><td>" + thisQuantity + "</td><td style='text-align: center;'><input style='width: 25px; height: 17px;' type='checkbox' onchange=\"copyMappingToUnidentifiedRow('"+thisId+"',this)\" class='mappingCheckbox' id='"+thisId+"' /></td></tr>";
                    }
                }
            });
        }
        applyMappingToOtherRowsHtml = applyMappingToOtherRowsHtml + "</tbody></table></div>";

        if (showSwal) {
            setTimeout(function () {
                Swal.fire({
                    html: applyMappingToOtherRowsHtml,
                    icon: "warning",
                    allowOutsideClick: true,
                    customClass: 'swal-wide',
                    showLoaderOnConfirm: true,
                    confirmButtonText: "<g:message code="importMapper.resolver.suggest_mapping_confirm" />",
                    cancelButtonText: "<g:message code="importMapper.resolver.suggest_mapping_cancel" />",
                    confirmButtonColor: "Green",
                    showCancelButton: true,
                    reverseButtons: true,
                    preConfirm: function () {
                        return new Promise(function (resolve) {
                            var toApplyRemap = $('.applyMapping');

                            if (toApplyRemap.length) {
                                $.each(toApplyRemap, function() {
                                    $(this).removeClass('applyMapping');
                                    var autocomplete = $(this).find('.autocomplete');

                                    if (autocomplete.length) {
                                        $(autocomplete).closest('div').removeClass('redBorder');
                                        if (alreadyIdentifiedRows) {
                                            $(autocomplete).addClass('retrainForThisUser');
                                            $(this).closest('tr').find('.rememberMappingCheckbox').show();
                                        } else {
                                            $(this).removeClass('notIdentified');
                                            $(autocomplete).addClass('notIdentifiedSaveMapping');
                                            var hammer = $(this).find('.mappingStatus');
                                            if (hammer.length) {
                                                hammer.text('');
                                                hammer.append("<i class=\"icon-done\" style=\"margin-top: 10px; margin-left: 10px; margin-right: -10px;\"></i>");
                                            }
                                        }

                                        $(autocomplete).val(value);
                                        var thisRowMapping = $(autocomplete).siblings('input:hidden.resourceIdContainer');
                                        $(thisRowMapping).val(parsedResourceId);
                                        $(thisRowMapping).attr('data-profileId', profileId);
                                        if ($(autocomplete).attr("data-targetBubble")) {
                                            updateSourceListing(
                                                $(autocomplete).attr("data-indicatorId"),
                                                parsedResourceId,
                                                $(autocomplete).attr("data-questionId"),
                                                profileId,
                                                $(autocomplete).attr("data-entityId"),
                                                true,
                                                $(autocomplete).attr("data-targetBubble")
                                            );
                                        }
                                    }

                                });
                                resolve("true")
                            } else {
                                resolve("false")
                            }
                        })
                    }
                });
            }, 150);
        }
    }

    function hideAlarm(el){
        var manualId = $(el).getAttribute("data-manualId");
        $("#alert" + manualId).addClass("hidden")
    }

    function resolveAllowMappingForRow(elem, hiddenElemId) {
        var hiddenElement = $('#'+hiddenElemId.replace(".", "\\."));

        if ($(elem).is(":checked")) {
            $(hiddenElement).val("true");
        } else {
            $(hiddenElement).val("false");
        }
    }
    function openSendMeData() {
        var jSon = {
            queryId: $("#queryId").val(),
            entityId: '${parentEntity?.id}',
            indicatorId: '${indicator?.indicatorId}',
            isPlanetaryOnly: '${isPlanetaryOnly}'
        }
        var url ="/app/sec/util/renderSendMeDataModal"
        $.ajax({
            url:url,
            data: JSON.stringify(jSon),
            contentType: "application/json; charset=utf-8",
            type:"POST",
            success: function(data,textStatus){
                $("#sendMeDataModal").empty().append(data.output).modal({backdrop: 'static', keyboard: false})
            },
            error: function(error, textStatus){
                console.log(error+ ": "+ textStatus)
            }

        })
    }

    $('#saveResolver').keydown(function(event) {
        if (event.keyCode === 13) {
            event.preventDefault();
            return false;
        }
    });

</script>
<style>
.select2-dropdown--below {
    top: 35px;
}

.select2-dropdown--above {
    top: -35px;
}
</style>
</body>
</html>



