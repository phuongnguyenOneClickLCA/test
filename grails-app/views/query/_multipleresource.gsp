<%@ page import="com.bionova.optimi.data.ResourceCache" %>
<g:if test="${showHistory}">
    <div class="alert alert-gray" id="${resourceTableId}History" style="display: none;">
        <button type="button" class="close" onclick="$('#${resourceTableId}History').hide();"${!modifiable ? ' disabled=\"disabled\"' : ''}>×</button>
    </div>
</g:if>
<g:if test="${filterError}">
    ${filterError}
</g:if>
<g:set var="iconUrl" value="${resource(dir: 'img', file: 'infosign_small.png')}" />
<g:if test="${!preventChanges}">
    <tr id="${trId}">
        <td>
            <g:if test="${showTypeahead}">
                <div class="input-append${showMandatory && !resourceDatasets && !optional ? ' redBorder ' : ''}" id="resourceBox${question?.questionId}">
                    <input type="text" name="resourceId"  class="autocomplete" onblur="toggleWarningText('${trId}infoText',false)"
                           class="input-xlarge" autocomplete="off" data-original-title="" placeholder="${query?.localizedtypeaheadSearchText ? query.localizedtypeaheadSearchText : message(code: "typeahead.info")}"
                           data-parentEntityId="${parentEntity?.id}" data-indicatorId="${indicator?.indicatorId}" maxlength="10000" data-infotextcontainerid="${trId}infoText"
                           data-queryId="${query?.queryId}" data-sectionId="${mainSection?.sectionId}" data-entityId="${entity?.id}" data-quarterlyInputEnabled="${quarterlyInputEnabled ? 'true' : 'false'}" data-monthlyInputEnabled="${monthlyInputEnabled ? 'true' : 'false'}" data-selectId="${selectId}" data-resourceTableId="${resourceTableId}" data-fieldName="${fieldName}" data-preventDoubleEntries="${preventDoubleEntries}" data-doubleEntryWarning="${g.message(code:'query.question.prevent_double_entries')}" data-showGWP="${showGWP}"
                           data-accountId="${account?.id ?: ""}" data-questionId="${question?.questionId}" onchange="removeBorder(this, '${inputWidth}');" id="${selectId}" ${disabledAttribute ?: maxRowLimitPerDesignReached ? "disabled=\"disabled\"" : ""}><a tabindex="-1" id="${selectId}showAll" class="add-on showAllResources " ${disabledAttribute||maxRowLimitPerDesignReached ? "" : "onclick=\"showAllQueryResources(this,'${question?.questionId}','${message(code: 'query.resource.popover')}');\""} ><i class="icon-chevron-down"></i></a><a href="javascript:" class="dataHitsButton add-on hidden" id="${question.questionId}dataHits"></a>

                </div>
                <span id="${selectId}loadingResource" style="display: none;"><i  class="fas fa-circle-notch fa-spin" aria-hidden="true"></i></span>

            </g:if>
            <g:else>
                <select ${disabledAttribute ?: maxRowLimitPerDesignReached ? "disabled=\"disabled\"" : ""}${inputWidth} data-entityId="${parentEntity?.id}" data-indicatorId="${indicator?.indicatorId}" data-queryId="${query?.queryId}" data-sectionId="${mainSection?.sectionId}" data-questionId="${question?.questionId}"
                                                          class="resourceSelect ${showMandatory && !resourceDatasets && !optional ? 'redBorder ' : ''} nonTypeaheadSelect" name="resourceId" onchange="removeBorder(this, '${inputWidthForBorder}'); addResourceFromSelect('${entity?.id}', '${quarterlyInputEnabled ? 'true' : 'false'}', '${monthlyInputEnabled ? 'true' : 'false'}', '${selectId}', '${indicator?.indicatorId}', '${query?.queryId}', '${mainSection?.sectionId}', '${question?.questionId}', '${resourceTableId}', '${fieldName}', ${preventDoubleEntries}, ${showGWP}, '${g.message(code:"query.question.prevent_double_entries")}'); " id="${selectId}"
                                                          data-autocomplete-placeholder="${message(code:'query.choose_resource_and_click')}" onblur="toggleWarningText('${trId}infoText',false)">
                </select>
            </g:else>
        </td>
        <td id="${trId}infoText" class="hidden"></td>

        <g:if test="${showHistory}">
            <td colspan="3">
            <input type="button" ${disabledAttribute ?: maxRowLimitPerDesignReached ? "disabled=\"disabled\"" : ""} class="primary btnRemove" style="font-weight: bold;" value="${message(code: 'query.previous_answers')}"
                   onclick="renderPreviousOperatingPeriods('${parentEntity?.id}', '${entity?.id}', '${indicator?.indicatorId}', '${query?.queryId}', '${mainSection?.sectionId}', '${question?.questionId}', '${resourceTableId}History');" />
            </td>
        </g:if>
        <td>
            <g:if test="${allQuestionsForMoving && modifiable && !preventChanges}">
            <div class="btn-group inliner moveResourcesContainer visibilityNone"><a href="javascript:" class="dropdown-toggle" rel="popover" data-trigger="hover" data-content="${message(code: 'move_resource_popover')}" data-toggle="dropdown"><strong><g:message code="move_resources"/></strong> <span class="caret caret-middle"></span></a>
            <ul class="dropdown-menu">
                <g:each in="${allQuestionsForMoving?.findAll({!question?.questionId?.equalsIgnoreCase(it.questionId)})}" var="questionForMoving">
                    <li><a href="javascript:" onclick="moveDatasetToAnotherQuestion('${resourceTableId}','${questionForMoving?.questionId}', '${questionForMoving?.sectionId}', '${query?.queryId}', '${entity?.id}', '${indicator?.indicatorId}')">${questionForMoving?.localizedQuestion}</a></li>
                </g:each>
            </ul>
        </div>
            </g:if>
        </td>
        <g:if test="${showTypeahead && modifiable}">
            <td id="${fieldName}ResourcesAlert">
            <queryRender:mandatoryResource entity="${entity}" indicator="${indicator}" query="${query}" queryId="${query?.queryId}" tableId="${resourceTableId}"
                                           questionId="${question?.questionId}" sectionId="${mainSection?.sectionId}" fieldName="${fieldName}" />
            </td>
        </g:if>
    </tr>
</g:if>
<g:if test="${questionsWithAdditionalCalculationScripts?.get(question?.questionId)}">
    <tr>
        <td><g:additionalCalculationParams question="${question}" entity="${entity}"/></td>
    </tr>
    <script>
        $( document ).ready(function() {
            $("#${resourceTableId}").on("change", "input[type='text'], select", function() {
                let obligatoryInputElements = $(this).closest("tr").find(":input[type=text]:not([readonly='readonly']), :input[type=hidden][data-questionid]");
                let obligatorySelectElements = $(this).closest("tr").find("select");
                obligatoryInputElements = $.merge(obligatoryInputElements, obligatorySelectElements);

                let numberFilledElements = obligatoryInputElements.filter(function () {
                    return $.trim($(this).val()).length > 0
                }).length

                if(obligatoryInputElements.length == numberFilledElements){
                    let inputParameters = {}
                    let resourceId = obligatoryInputElements[0].getAttribute('data-resourceId')
                    let additionalParamsForJavascript = $("input[name='${question?.questionId}_additionalParams']")
                    //get addQ values from table
                    for (const obj of obligatoryInputElements) {
                        if (obj.getAttribute('data-questionid') != null){
                            inputParameters[obj.getAttribute('data-questionid')] = obj.value;
                        } else if (obj.classList.contains('isQuantity')){
                            inputParameters['quantity'] = obj.value;
                        }

                    }
                    //get value from hidden fields which was requested in javascriptAdditionalCalcParams of a question
                    for (const obj of additionalParamsForJavascript) {
                        inputParameters[obj.getAttribute('data-additionalQuestionId')] = obj.value;
                    }

                    let calculationResult = ${questionsWithAdditionalCalculationScripts.get(question?.questionId)}(resourceId, inputParameters)
                    let outputElements = $(this).closest("tr").find(":input[type=text][readonly='readonly']")

                    for (const obj of outputElements) {
                        obj.value = calculationResult.get(obj.getAttribute('data-questionid'));
                    }
                }
            });
        });
    </script>
</g:if>

<table class="query_resource sortMe" id="${resourceTableId}">
   <thead>
    <tr id="${resourceTableId}header"${!resourceDatasets ? ' style=\"display: none;\"' : ''}>
        <th data-sort="string"><g:message code="query.material" /></th>
    <g:if test="${quantity && (!indicator || !indicator.requireMonthly)}">
        <th ${question.isThicknessQuestion ? "colspan='3'" : ""} data-sort="int" ><g:message code="query.quantity" /></th>
    </g:if>
    <g:elseif test="${quantity && (indicator && indicator.requireMonthly)}">
        <th><g:message code="totalScore" /></th>
    </g:elseif>
    <g:if test="${quarterlyInputEnabled && quantity}">
        <g:each in="['Q1', 'Q2', 'Q3', 'Q4']">
            <th class="monthly_data">${it}</th>
        </g:each>
    </g:if>
    <g:elseif test="${monthlyInputEnabled && (quantity || (indicator && indicator.requireMonthly))}">
        <g:each in="[1,2,3,4,5,6,7,8,9,10,11,12]">
            <th class="monthly_data"><g:message code="month.${it}" /></th>
        </g:each>'
    </g:elseif>
    <g:each in="${additionalQuestions}" var="additionalQuestion">
        <g:if test="${!additionalQuestion?.hideValueInQuery && !'hidden'.equals(additionalQuestion?.inputType) && (!additionalQuestion?.licenseKey || (additionalQuestion?.licenseKey && additionalQuestionLicensed?.get(additionalQuestion?.questionId)))}">
            <th ${additionalQuestion?.dataSort ? "data-sort='${additionalQuestion.dataSort}'" : "class=\"no-sort\""}>
                ${additionalQuestion?.isThicknessQuestion  ? "&nbsp;" : (additionalQuestion?.questionId == 'carbonDataImpact' && query.allowedFeatures?.contains("showCarbonData")) ? "<div class='co2CloudContainer smoothTip tooltip--right' data-tooltip=\"${message(code: 'carbon_cloud_info')}\" ><i class='fa fa-cloud queryImpactCloud'></i></div> CO<sub>2</sub>e" : additionalQuestion.localizedQuestion}
                ${additionalQuestion?.localizedHelp ? "&nbsp;<span class=\"tableHeadingPopover\" href=\"#\" data-toggle=\"dropdown\" rel=\"popover\" data-trigger=\"hover\" data-content=\"${additionalQuestion?.localizedHelp}\"><i class=\"far fa-question-circle\"></i></span>" : ""}
            </th>
        </g:if>
    </g:each>
    </tr>
   </thead><tbody class="dragginContainer" data-questionId="${question?.questionId}" id="${trId}query_resource_body">
<g:if test="${resourceDatasets}">
    <g:each in="${resourceDatasets?.sort({it.seqNr})}" var="dataset" status="dIndex">
        <g:if test="${!dataset.noQueryRender}">
            <queryRender:renderResourceRow
                splitViewAllowed="${query?.allowedFeatures?.contains("splitOrChangeView")}"
                dataset="${dataset}" monthlyInputEnabled="${monthlyInputEnabled}" preventChanges="${preventChanges}"
                resource="${resourceCache?.getResource(dataset)}"
                quarterlyInputEnabled="${quarterlyInputEnabled}"
                queryTask="${queryTask}" showGWP="${showGWP}" unitSystem="${unitSystem}" multipleChoicesAllowed="${true}"
                fieldName="${fieldName}" indicator="${indicator}"
                resourceTableId="${resourceTableId}" parentEntity="${parentEntity}" entity="${entity}"
                modifiable="${modifiable}" groupMaterialsAllowed="${groupMaterialsAllowed}" createConstructionsAllowed = "${createConstructionsAllowed}" publicGroupAllowed="${publicGroupAllowed}"
                question="${question}" section="${mainSection}" query="${query}" additionalQuestions="${additionalQuestions}"
                splitOrChangeLicensed="${splitOrChangeLicensed}"
                basicQuery="${basicQuery}" additionalQuestionInputWidth="${additionalQuestionInputWidth}"
                eolProcessCache="${eolProcessCache}" resourceCache="${resourceCache}"/>
        </g:if>
    </g:each>
</g:if>
</tbody>
</table>