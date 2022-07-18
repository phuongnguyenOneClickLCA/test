<%@ page import="com.bionova.optimi.core.domain.mongo.Feature" %>
<%-- Copyright (c) 2012 by Bionova Oy --%>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <meta name="format-detection" content="telephone=no"/>
    <asset:javascript src="jquery.autocomplete.js?v=${grailsApplication.metadata.'app.version'}"/>
    <asset:javascript src="stupidtable.min.js" />
    <asset:javascript src="parsley.js"/>
    <g:set var="optimiResourceService" bean="optimiResourceService"/>
</head>
<body>
<g:form action="recognizeDatasets" method="post" novalidate="novalidate">
    <g:hiddenField name="entityId" value="${entityId}" />
    <g:hiddenField name="childEntityId" value="${childEntityId}" />
    <g:hiddenField name="queryId" value="${queryId}" />
    <g:hiddenField name="indicatorId" value="${indicator?.indicatorId}" />
    <g:hiddenField name="skipReview" value="${true}" />

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
            <g:if test="temporaryImportData?.importDataPerStep">
                <g:set var="one" value="${temporaryImportData?.importDataPerStep?.get(2) ?: 0 }"/>
                <g:set var="two" value="${temporaryImportData?.importDataPerStep?.get(4) ?: 0 }"/>
                <g:set var="three" value="${one - two }"/>
                <g:set var="four" value="${temporaryImportData?.importDataPerStep?.get(5) ?: 0 }"/>
                <g:set var="five" value="${two - four}"/>
                <div class="alert alert-info">
                    <i class="fas fa-info pull-left" style="font-size: large; margin-right: 8px;"></i>
                    <strong>${message(code:"importMapper.data_review_rowCounts", args: [one,two,three,four,five])}</strong>
                </div>
            </g:if>
            <div class="btn-group pull-right hide-on-print" id="actions">
                <g:link action="cancel" class="btn" onclick="return modalConfirm(this);"
                        data-questionstr="${message(code: 'importMapper.index.cancel')}"
                        data-truestr="${message(code: 'yes')}" data-falsestr="${message(code: 'no')}"
                        data-titlestr="Cancel"><g:message code="cancel" /></g:link>
                <g:link controller="importMapper" action="generateExcelFromDatasets" params="[returnUserTo: 'resolver', entityId: entityId, childEntityId: childEntityId]" class="btn btn-primary" style="font-weight: normal;"><g:message code="results.expand.download_excel" /></g:link>

                <button name="save" onclick="clearMessages('#loadingSpinner','#importPageContent');" type="submit"
                        class="btn btn-primary btn-block ${noLicense ? ' removeClicks' : ''}" id="save">${message(code: 'continue')}</button>
            </div>
            <h1>${message(code: 'breadcrumbs.data_review')}</h1>
        </div>
    </div>
    <div class="resolverWrapper">
    <g:if test="${resourceDatasets || descriptiveDatasets}">
        <g:if test="${importMapperId?.equalsIgnoreCase(Feature.IFC_FROM_SIMPLE_BIM)}">
            <div class="container section" id="bimModelCheckerContainer">
                <div class="sectionheader">
                    <button class="pull-left sectionexpander"><i class="icon icon-chevron-down collapser"></i><i class="icon icon-chevron-right expander"></i></button>
                    <h2 class="h2expander inliner" id="bimcheckerExpander"> <g:message code="bim_checker_heading" />: <span id="bimHeading"><i class="fas fa-circle-notch fa-spin oneClickColorScheme"></i></span> </h2>
                </div>
                <div class="container" id="bimCheckerContent">

                </div>
            </div>
        </g:if>
        <g:if test="${resourceDatasets}">
        <div class="container section collapsed">
            <div class="sectionheader">
                <button class="pull-left sectionexpander"><i class="icon icon-chevron-down collapser"></i><i class="icon icon-chevron-right expander"></i></button>
                <h2 class="h2expander"><g:message code="importMapper.dataReview.data_handled" args="[resourceDatasets?.size() ?: 0]" /></h2>
            </div>

            <div class="sectionbody">
                <table class="table table-condensed table-striped sortMe" id="identifiedTable"${!resourceDatasets ? " style='display: none;min-width: 280px !important'" : 'style="min-width: 280px !important"'}>
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
                        <th>&nbsp;</th>
                    </tr>
                    </thead>
                    <tbody>
                        <g:each in="${resourceDatasets}" var="dataset">
                            <g:set var="ifcClass" value="${dataset.trainingData?.get("CLASS")?.toUpperCase()}" />
                            <g:set var="resourceCache" value="${com.bionova.optimi.data.ResourceCache.init(datasets)}"/>
                            <g:set var="datasetResource" value="${resourceCache.getResource(dataset)}" />
                            <g:set var="detailsLink" value="${dataset.importDisplayFields?.get(detailsKey)}" />
                            <tr id="identified${dataset.manualId}" data-materialkey="${detailsLink}" class="${!dataset.answerIds?.get(0) ? 'notQuantified' : ''}">
                                <g:set var="resourceName" value="${optimiResourceService.getUiLabel(datasetResource)}" />
                                <td class="tdalignmiddle" style="min-width: 280px !important;">
                                <input type="hidden" name="questionId.${dataset.manualId}" value="${dataset.questionId}" />
                                <span data-content="${detailsLink ?: dataset.ifcMaterialValue ?: ''}" rel="popover" data-placement="top" data-trigger="hover"><g:abbr value="${org.springframework.web.util.HtmlUtils.htmlUnescape(detailsLink ?: dataset.ifcMaterialValue?: "")}" maxLength="40" /></span><g:if test="${!dataset.allowMapping && dataset.quantity && dataset.userGivenUnit}"><a href="javascript:" class="pull-right" rel="popover" data-trigger="hover" data-html="true" data-content="${message(code: 'importMapper.resolver.generic_datapoint_warning')}" id="${dataset.manualId}genericDataWarning"><asset:image src="img/icon-warning.png" style="max-width:16px"/></a></g:if>
                                <g:if test="${dataset.allImportDisplayFields}">&nbsp;<a href="javascript:" id="${dataset.manualId}info" onclick="openCombinedDisplayFieldsResolver('${dataset.manualId}','${dataset.manualId}info')" class="pull-right"><i class="far fa-file-alt fa-lg greenInfoBubble" aria-hidden="true"></i></a></g:if>
                                <queryRender:importMapperBimCheckerErrors dataset="${dataset}"/>
                            </td>
                                <td class="tdalignmiddle">
                                    <span class="setHiddenValueForSort hidden"></span>
                                    <g:set var="classRemapChoices" value="${classRemapChoice?.allowedClassRemappings}" />
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
                                </td>
                                <td class="tdalignmiddle">
                                    <span class="setHiddenValueForSort hidden"></span>
                                    <input type="text" class="resolverCommentField" value="${dataset.additionalQuestionAnswers?.get("comment")}" name="comment.${dataset.manualId}" />
                                </td>

                                <g:if test="${additionalQuestions}">
                                    <g:each in="${additionalQuestions}" var="additionalQuestion" status="additionalQuestionIndex"><g:if test="${!additionalQuestion.inGroupedQuestions()}">
                                        <g:if test="${additionalQuestion?.questionId && dataset.additionalQuestionAnswers?.get(additionalQuestion?.questionId)}">
                                            <g:set var="additionalQuestionAnswer" value="${dataset.additionalQuestionAnswers?.get(additionalQuestion.questionId)}" />
                                        </g:if>
                                        <g:else>
                                            <g:set var="additionalQuestionAnswer" value="" />
                                        </g:else>
                                        <g:render template="/query/additionalquestion" model="[indicator: indicator, additionalQuestionAnswer:additionalQuestionAnswer, entity: entity, query:query, mainSectionId: dataset.sectionId,
                                                                                           additionalQuestion: additionalQuestion, mainQuestion: query.getQuestionsBySection(dataset.sectionId)?.find({ it.questionId == dataset.questionId }), resource: datasetResource, resourceId: dataset.resourceId,
                                                                                           allowVariableThickness: datasetResource?.allowVariableThickness, transportUnit: additionalQuestions.find({it.questionId.contains('transportDistance')})?.localizedUnit, disableThickness: dataset.userGivenUnit && !'m2'.equalsIgnoreCase(dataset.userGivenUnit),
                                                                                           datasetId: dataset.manualId,  trId: 'identified' + dataset.manualId, importMapperAdditionalQuestion: true, tableFormat: true]" />
                                    </g:if></g:each>
                                </g:if>

                                <td class="tdalignmiddle">
                                    <g:set var="thickness" value="${dataset.additionalQuestionAnswers?.get("thickness_mm") ? formatNumber(number:dataset.additionalQuestionAnswers.get("thickness_mm"), format:"###.##" ) : ''}" />
                                    <g:if test="${dataset.quantity == null}">
                                        <input type="text" value="" name="quantity.${dataset.manualId}" id="quantity${dataset.manualId}" class="numeric input-mini" />
                                    </g:if>
                                    <g:else>
                                        <g:set var="formattedQuantity" value="${dataset.quantity > 10 ? formatNumber(number:dataset.quantity, format:"#") : formatNumber(number:dataset.quantity, format:"###.##")}" />
                                        <span class="quantityContainer hidden">
                                            ${formattedQuantity} ${dataset.userGivenUnit ?: ''}${dataset.area_m2 ? " / " + "${formatNumber(number:dataset.area_m2, format:"###.##")} M2" : ''}${thickness ? ' / ' + thickness + ' mm': ''}
                                        </span>
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
                                                    <option data-quantity="${dataset.mass_kg}" value="kg">${formatNumber(number:dataset.mass_kg, format:"###.##")} kg</option>
                                                </g:if>
                                            </select>
                                        </g:if>
                                        <g:else>
                                            ${formattedQuantity} ${dataset.userGivenUnit ?: ''}${thickness ? ' / ' + thickness + ' mm': ''}
                                        </g:else>
                                    </g:else>
                                </td>
                                <td class="tdalignmiddle"${dataset.percentageOfTotal && dataset.percentageOfTotal > 1 ? ' style=\"font-weight: bold;\"' : ''}>
                                    ${dataset.percentageOfTotal != null ? formatNumber(number:dataset.percentageOfTotal, format:"###.##") + ' %' :''}</td>
                                <g:set var="datasetResourceInfo" value="${UUID.randomUUID().toString()}" />
                                <td class="tdalignmiddle"><a href="javascript:" class="btn btn-danger deleteDataset" id="deleteDataset${dataset.manualId}" onclick="deleteIfcDataset(this,'${dataset.manualId}','${ifcClass?.replaceAll("\\s", "")}AmountContainer','${ifcClass}')"><g:message code="delete" /></a></td>

                            </tr>
                        </g:each>
                    </tbody>
                </table>
            </div>
        </div>
        </g:if>
        <g:if test="${descriptiveDatasets}">
        <div class="container section collapsed">
            <div class="sectionheader">
                <button class="pull-left sectionexpander"><i class="icon icon-chevron-down collapser"></i><i class="icon icon-chevron-right expander"></i></button>
                <h2 class="h2expander"><g:message code="importMapper.dataReview.descriptive_data_handled" args="[descriptiveDatasets?.size() ?: 0]" /></h2>
            </div>

            <div class="sectionbody">
                <table class="table table-condensed table-striped sortMe" id="identifiedDescriptiveTable"${!descriptiveDatasets ? " style='display: none;min-width: 280px !important'" : 'style="min-width: 280px !important"'}>
                    <thead>
                    <tr>
                        <th data-sort="string" style="width: 20%"><g:message code="importMapper.class" /></th>
                        <th data-sort="string"><g:message code="importMapper.content" /></th>
                        <th>&nbsp;</th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:if test="${descriptiveDatasets}">
                        <g:each in="${descriptiveDatasets}" var="dataset">
                            <g:set var="ifcClass" value="${dataset.trainingData?.get("CLASS")?.toUpperCase()}" />
                            <tr id="identified${dataset.manualId}">
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
                                <td class="tdalignmiddle"><a href="javascript:" class="btn btn-danger deleteDataset" id="deleteDataset${dataset.manualId}" onclick="deleteIfcDataset(this,'${dataset.manualId}','${ifcClass?.replaceAll("\\s", "")}AmountContainer','${ifcClass}')"><g:message code="delete" /></a></td>
                            </tr>
                        </g:each>
                    </g:if>
                    </tbody>
                </table>
            </div>
        </div>
    </g:if>
    </g:if>
</g:form>
</div>

<script type="text/javascript">
    $(document).ready(function() {
        numericInputCheck();
        ignoreWarningPopover();
        var $parent = $('#saveResolver');

        $parent.on('click', '.hideWarningImportMapper', function() {
            hideWarningImportMapper($(this).attr('data-warningThicknessId'), $(this).attr('data-manualId'))
        });
        trackInputChanges();
        sortableImportTables();
        appendSortignHeads();
        appendTemplateSecure('bimCheckerContent', '${bimcheckerUrl}');

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


    });

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



    function hideAlarm(el){
        var manualId = $(el).getAttribute("data-manualId");
        $("#alert" + manualId).addClass("hidden")
    }


</script>
</body>
</html>



