<%@ page import="com.bionova.optimi.core.Constants; grails.converters.JSON; com.bionova.optimi.core.domain.mongo.Entity; com.bionova.optimi.core.domain.mongo.Dataset" %>
<g:set var="questionService" bean="questionService" />
<g:set var="dataset"
       value="${questionService?.getDatasetForGsp(question?.questionId, query?.queryId, mainSectionId, linkedQuestions, sendMeData, entity, parentEntity, null) as Dataset}"/>
<g:set var="questionService" bean="questionService" />
<g:if test="${firstQuestion}">
    <div class="control-group sectionbody${section?.sectionId}" style="${inputType ? 'margin-bottom: 10px;' : ''}${ifChosen ? ' margin-left: 20px;' : ''}${isSectionExpandable ? " display: none;" : ""}">
    <table class="table table-condensed tableFormat">
        <tr>
            <th style="width: ${columnWidths[0]};"><g:message code="results.question" /></th>
            <th style="width: ${columnWidths[2]};"><g:message code="answer" /></th>
            <g:each in="${additionalQuestions}" var="additionalQuestion">
               <th style="width: ${additionalQuestion.inputWidth ? additionalQuestion.inputWidth + 'px' : columnWidths[0]};">${additionalQuestion.localizedQuestion}</th>
            </g:each>
        </tr>
</g:if>
        <tr class="queryquestion ${dataset?.unlockedFromVerifiedStatus ? 'unlockedVerifiedDatasetStatusBg' : dataset?.verified ? 'verifiedDatasetBackground' : ''}">
            <td>
                ${question?.localizedQuestion}
                <g:if test="${question?.localizedHelp}">
                    <a href="#" title="${question.localizedHelp}">
                        <i class="icon-question-sign"></i>
                    </a>
                </g:if>
                <g:render template="/query/icons/verifyDatasetIcons" model="[forNonResourceRow: true, user: user, dataset: dataset]"/>
            </td>
            <td>
                <g:render template="/query/inputs/verifyDatasetInputs" model="[forNonResourceRow: true, dataset: dataset, questionIdPrefix: fieldName, entity: (Entity) entity]"/>
                <g:if test="${inputType == 'text'}">
                    <g:set var="questionAnswer"
                           value="${g.questionAnswer(entity: entity, parentEntity: parentEntity, indicatorId: indicator?.indicatorId, queryId: query?.queryId, sectionId: mainSectionId, questionId: question?.questionId, inputType: 'text', sendMeData: sendMeData, linkedQuestions: linkedQuestions, entityClassResource: entityClassResource, dataset: dataset)}"/>
                    <input type="text" name="${fieldName}" id="${divId}"
                           onchange="removeBorder(this${question.inputWidth ? ',' + question.inputWidth : ''}${question.inputHeight ? ',' + question.inputHeight : ''});${isQuestionUsedForAdditionalJSCalculation ? 'updateHiddenField(this, \'' + question.questionId + '\');' : ''}"
                           class="input-xlarge span3 ${question?.onlyNumeric ? 'numeric' : ''} ${dataset?.verified ? 'removeClicks' : ''} ${Dataset.VERIFIABLE_INPUT}" ${disabledAttribute}
                           value="${questionAnswer ? questionAnswer : question?.defaultValue}" ${g.questionInlineStyle(question: question, answer: questionAnswer, showMandatory: showMandatory)} ${question?.uiNumericCheck} ${checkValue} ${question.irreversible && questionAnswer && !superUser ? "readonly" : ""}/>
                    <g:if test="${unit}">
                        <span class="add-on">${unit}</span>
                    </g:if>
                </g:if>
                <g:elseif test="${inputType == 'textarea'}">
                    <g:set var="questionAnswer"
                           value="${g.questionAnswer(entity: entity, parentEntity: parentEntity, queryId: query?.queryId, sectionId: mainSectionId, questionId: question?.questionId, inputType: 'textarea', sendMeData: sendMeData, linkedQuestions: linkedQuestions, entityClassResource: entityClassResource, dataset: dataset)}"/>
                    <g:set var="inlineStyle"
                           value="${g.questionInlineStyle(question: question, answer: questionAnswer, showMandatory: showMandatory)}"/>
                    <textarea${!inlineStyle ? ' rows=\"3\" cols=\"100\"' : ''} name="${fieldName}"
                                                                               onchange="removeBorder(this${question.inputWidth ? ',' + question.inputWidth : ''}${question.inputHeight ? ',' + question.inputHeight : ''});${isQuestionUsedForAdditionalJSCalculation ? 'updateHiddenField(this, \'' + question.questionId + '\');' : ''}"
                                                                               id="${divId}"
                                                                               class="input-xlarge span3 ${dataset?.verified ? 'removeClicks' : ''} ${Dataset.VERIFIABLE_INPUT}" ${inlineStyle}${disabledAttribute} ${checkValue} ${question.irreversible && questionAnswer && !superUser ? "readonly" : ""}>${questionAnswer ? questionAnswer : question?.defaultValue}</textarea>
                </g:elseif>
                <g:elseif test="${inputType == 'select'}">
                    <g:questionAnswer var="questionAnswer"
                                      entity="${entity}" parentEntity="${parentEntity}" queryId="${query?.queryId}" sectionId="${mainSectionId}" questionId="${question?.questionId}" inputType="select" sendMeData="${sendMeData}" linkedQuestions="${linkedQuestions}" entityClassResource="${entityClassResource}"  dataset="${dataset}"/>
                       <g:if test="${question?.showTypeahead}">
                        <div class="input-append" id="resourceBox${question?.questionId}">
                            <input type="text"
                                   class="autocomplete input-xlarge${showMandatory && !resourceDatasets && !optional ? ' redBorder ' : ''} ${dataset?.verified ? 'removeClicks' : ''} ${Dataset.VERIFIABLE_INPUT}"
                                   autocomplete="off" data-original-title=""
                                   placeholder="${message(code: "typeahead.info")}"
                                   onchange="removeBorder(this, null);${isQuestionUsedForAdditionalJSCalculation ? 'updateHiddenField(this, \'' + question.questionId + '\');' : ''}" id="${question?.questionId}autocompletequestion"
                                   value="${questionAnswer ? questionAnswer[0].toString() : ""}">
                            <a tabindex="-1" id="${question?.questionId}showAll"
                               class="add-on showAllResources " ${disabledAttribute || maxRowLimitPerDesignReached ? "" : "onclick=\"showAllQueryResources(this,'${question?.questionId}','${message(code: 'query.resource.popover')}');\""}>
                                <i class="icon-chevron-down"></i>
                            </a>
                            <input type="hidden" id="${question?.questionId}autocompletequestionHidden" name="${fieldName}" value="${questionAnswer ? questionAnswer[0].toString() : ""}">
                            <g:if test="${!sendMeData}">
                                <script>
                                    $(document).ready(function () {
                                        let list = ${questionService.getJsonAutocompleteChoices(question) as grails.converters.JSON ?: []}

                                        if (list && list.length > 0) {
                                            let defaultValue = ${questionAnswer ? "'" + questionAnswer[0].toString() + "'" : "''"};
                                            $("#${question?.questionId}autocompletequestion").devbridgeAutocomplete({
                                                minChars: 0,
                                                lookup: list,
                                                onSearchStart: function (query) {
                                                    let val = new String($(this).val())
                                                    let pttrn = /^\s*/;
                                                    let start = val.match(pttrn)[0].length
                                                    val = val.substring(start)
                                                    $(this).val(val)
                                                },
                                                formatResult: function (suggestion) {
                                                    let div = document.createElement("div")
                                                    let text = document.createTextNode(" " + suggestion.value);
                                                    div.appendChild(text)
                                                    return div.innerHTML;
                                                },
                                                onSelect: function (suggestion) {
                                                    $('#${question?.questionId}autocompletequestionHidden').val(suggestion.data.resourceId);
                                                }
                                            });
                                            if (defaultValue) {
                                                let defaultValueObj = list.filter(x => x.data.resourceId == defaultValue);

                                                if (defaultValueObj) {
                                                    $("#${question?.questionId}autocompletequestion").val(defaultValueObj[0].value);
                                                }
                                            }
                                        }
                                        /*var answerExist = "${questionAnswer ? questionAnswer[0].toString() : ""}"
                                        if(answerExist.length > 0 && answerExist != "null"){
                                            var optionSelect = list.filter(function (elem){
                                                return elem.data.resourceId == answerExist
                                            })
                                            if(optionSelect && optionSelect[0]){
                                                $("#${question?.questionId}autocompletequestion").val(optionSelect[0].value)
                                                removeBorder($("#${question?.questionId}autocompletequestion"))
                                            }
                                        }*/
                                    })

                                </script>
                            </g:if>
                        </div>
                        <span id="${selectId}loadingResource" style="display: none;"><i  class="fas fa-circle-notch fa-spin" aria-hidden="true"></i></span>
                    </g:if>
                    <g:else>
                        <select${preventChanges ? ' disabled=\"disabled\"' : ''} onchange="removeBorder(this);${isQuestionUsedForAdditionalJSCalculation ? 'updateHiddenField(this, \'' + question.questionId + '\');' : ''}
                        ${question?.questionId == Constants.PRIVATE_DATA_VERIFICATION_PROOF ? 'showOrHideVerificationNumber(this,\'' + section.sectionId + '\',\'' + Constants.VerificationProof.INES_IDENTIFIER.getName() + '\');' : ''}"
                                                                                 class="${dataset?.verified ? 'removeClicks' : ''} ${Dataset.VERIFIABLE_INPUT}"
                                                                                 name="${fieldName}" ${g.questionInlineStyle(question: question, answer: questionAnswer, showMandatory: showMandatory)}
                                                                                 id="${divId} ${question.irreversible && questionAnswer && !superUser && !preventChanges ? "disabled=\"disabled\"" : ""}">
                            <g:if test="${!question?.noEmptyOption}">
                                <option value=""></option>
                            </g:if>
                            <g:each in="${questionService.getFilteredChoices(parentEntity, null, null, question)}" var="choice"
                                    status="index">
                                <g:if test="${questionAnswer && questionAnswer.contains(choice.answerId)}">
                                    <g:set var="selected" value="selected='selected'"/>
                                </g:if>
                                <g:elseif test="${!noDefaults && !questionAnswer && choice.answerId == question?.defaultValue}">
                                    <g:set var="selected" value="selected='selected'"/>
                                </g:elseif>
                                <g:elseif test="${!noDefaults && !questionAnswer && question?.defaultValueFromCountryResource && choice.answerId == question?.getCountryResourceDefault(parentEntity)}">
                                    <g:set var="selected" value="selected='selected'"/>
                                </g:elseif>
                                <g:else>
                                    <g:set var="selected" value=""/>
                                </g:else>
                                <option value="${choice.answerId}" ${selected}${choice.disabled ? " class=\"disabledSelectOption\" disabled=\"disabled\"" : ""}>${choice.localizedAnswer}</option>
                            </g:each>
                        </select>
                        <g:if test="${question.irreversible && questionAnswer && !superUser}"><input type="hidden" name="${fieldName}" value="${questionAnswer[0].toString()}" ></g:if>
                    </g:else>
                </g:elseif>

                <g:if test="${showOtherDesigns}">
                    <div class="btn-group" style="display: inline-block;">
                        <a href="javascript:"
                           onclick="showOtherDesigns($(this), '${parentEntity?.id}', '${entity?.id}', '${indicator?.indicatorId}', '${query?.queryId}', '${mainSectionId}', '${question?.questionId}', '<g:replace value="${divId}Other" source="." target="" />', '${disabledAttribute}', true);"
                           rel="popover" data-toggle="dropdown" class="primary dropdown-toggle"
                           style="margin-left: 30px;">
                            <strong><g:message code="query.other_answers"/></strong>
                            <span class="caret caret-middle"></span>
                        </a>
                    </div>
                </g:if>
                %{-- << verificationPoint >> --}%
                <g:if test="${question?.verificationPoints}">
                        <opt:verificationPointIcon question="${question}"/>
                </g:if>
                <span id="${divId}UnlockVerifiedDatasetLink">
                  <g:render template="/query/links/unlockVerifiedDatasetLink" model="[forNonResourceRow: true]"/>
                </span>
            </td>
            <g:each in="${additionalQuestions}" var="additionalQuestion">
                <g:if test="${additionalQuestionAnswers}">
                    <g:set var="additionalQuestionAnswer"
                           value="${additionalQuestionAnswers.get(additionalQuestion.questionId)}"/>
                </g:if>
                <g:render template="/query/additionalquestion"
                          model="[tableFormat: true, entity: entity, query: query, section: section, inputType: inputType, mainSectionId: mainSectionId, additionalQuestion: additionalQuestion, additionalQuestionAnswer: additionalQuestionAnswer, mainQuestion: question, modifiable: modifiable, additionalQuestionInputWidth: additionalQuestionInputWidth]"/>
            </g:each>
            <g:if test="${showHistory}">
                <td class="queryPreviousAnswers">
                    <input type="button" ${disabledAttribute} class="primary btnRemove" style="margin-left: 30px; font-weight: bold;" value="${message(code: 'query.previous_answers')}"
                       onclick="renderPreviousOperatingPeriods('${parentEntity?.id}', '${entity?.id}', '${indicator?.indicatorId}', '${query?.queryId}', '${mainSectionId}', '${question?.questionId}', '<g:replace value="${divId}History" source="." target="" />');" />
                    <div class="alert alert-gray" id="<g:replace value="${divId}History" source="." target="" />" style="display: none; float: left; margin-left: 30px;">
                        <button type="button" class="close" onclick="$('#<g:replace value="${divId}History" source="." target="" />').hide();">×</button>
                    </div>
                </td>
            </g:if>
        </tr>
        <g:if test="${showOtherDesigns}">
            <g:set var="colNum" value="${additionalQuestions.size + 2}"/>
            <tr id="<g:replace value="${divId}OtherRow" source="." target="" />" style="display: none">
                <td colspan="${colNum ?: 2}">
                    <div class="alert alert-gray" id="<g:replace value="${divId}Other" source="." target="" />" style="display: inline-block; border: none; margin-bottom: 0">
                        <button type="button" class="close" onclick="$('#<g:replace value="${divId}OtherRow" source="." target="" />').fadeOut();">×</button>
                    </div>
                </td>
            </tr>
        </g:if>

<g:if test="${lastQuestion}">
    </table>
</div>
</g:if>
