<%@ page import="com.bionova.optimi.core.domain.mongo.Entity; com.bionova.optimi.core.domain.mongo.Dataset" %>
<g:set var="questionService" bean="questionService" />
<g:set var="dataset"
       value="${questionService?.getDatasetForGsp(question?.questionId, query?.queryId, mainSectionId, null, null, entity, parentEntity, null) as Dataset}"/>
<g:if test="${showHistory}"><div class="alert alert-gray" id="${resourceTableId}History" style="display: none;">
        <button type="button" class="close" onclick="$('#${resourceTableId}History').hide();">×</button>
    </div>
</g:if>
<g:if test="${filterError}">
    ${filterError}
</g:if>
<table class="query_resource" id="${resourceTableId}">
    <script type="text/javascript">
        if(typeof changeInputNameAndUnit${textId.replace("-","_")} != 'function') {
            function changeInputNameAndUnit${textId.replace("-","_")}(trId, selectId, textId, namePrefix, additionalQuestionNames, countriesWithState) {
                var selectInput = "#" + selectId;
                var textInput = '#' + textId;
                var resourceId = $(selectInput).val().split(" ")[0];
                var textName = namePrefix + '.' + resourceId;
                $(textInput).attr("name", textName);
                // First removing existing unit
                var tr = '#' + trId;
                $(tr).find('.unitContainer').remove();
                var unit;

                updateResourceIdForDatasetVerificationInRow(trId, resourceId)

                if ($(selectInput).val().split(" ")[1]) {
                    unit = $(selectInput).val().split(" ")[1]

                    if (unit && unit != 'null') {
                        $(textInput).after(' <span class=\"add-on unitContainer\">' + unit + '</span>');
                    }
                }

                // Renaming additonalQuestion inputs
                if (additionalQuestionNames) {
                    var theNames = additionalQuestionNames.split(",");

                    for (var i = 0; i < theNames.length; i++) {
                        var currentName = theNames[i];

                        if (currentName) {
                            var nameParts = currentName.split(".");

                            if (nameParts.length == 3) {
                                var ind = currentName.lastIndexOf(".");
                                currentName = currentName.substring(0, ind);
                            }
                            var additionalFieldId = '#' + currentName.split('.').join('\\.');
                            var additionalField = $( "[name^='"+currentName.split('.').join('\\.')+"']" );

                            var newName = currentName + '.' + resourceId;
                            $(additionalField).attr("name", newName);
                            <g:if test="${resources}">
                                    <g:set var="questionResources" value="${resources}" />

                            if (additionalFieldId.indexOf("thickness_mm") != -1 || additionalFieldId.indexOf("thickness_in") != -1) {
                                var resources = {};
                                <g:each in="${questionResources}" var="resource">
                                     resources['${resource?.resourceId}'] = ${resource.allowVariableThickness};

                               </g:each>
                                if (!resources[resourceId]) {
                                    $(additionalField).attr("disabled", "disabled");
                                    $(additionalField).val("");
                                } else {
                                    $(additionalField).prop('disabled', false);
                                }
                            } else if(additionalFieldId.indexOf("profileId") != -1) {
                                $(additionalField).children().remove()
                                $(additionalField).prop('disabled', false);

                                $.ajax({async:false, type:'POST',
                                    data:'resourceId='+resourceId,
                                    url:'/app/profiles',
                                    success:function(data,textStatus){
                                        $(additionalField).append(data.output);
                                    },
                                    error:function(XMLHttpRequest,textStatus,errorThrown){}});
                            } else if( additionalFieldId.indexOf("state") != -1){
                                $(".stateOption").addClass("hidden")
                                $(selectInput).parent().next().addClass("hidden");
                                $(selectInput).parent().next().find("select").val('');
                                $(selectInput).parent().next().find("select").attr('disabled', true);
                                var valueOfSelect = $(selectInput).val()
                                var countriesWithState = ${countriesWithState};
                                var countriesWithPlanetary = ${countriesWithPlanetary as grails.converters.JSON};
                                if(countriesWithPlanetary && countriesWithPlanetary.length > 0){
                                    var lastSibling = $(selectInput).parent().siblings()
                                    if(countriesWithPlanetary.indexOf(valueOfSelect) != -1){
                                        $(lastSibling).find(".success").removeClass("hidden")
                                        $(lastSibling).find(".alert-warning").addClass("hidden")
                                    } else {
                                        $(lastSibling).find(".success").addClass("hidden")
                                        $(lastSibling).find(".alert-warning").removeClass("hidden")
                                    }
                                }
                                if(countriesWithState && countriesWithState[valueOfSelect]){
                                    $("."+countriesWithState[valueOfSelect]).removeClass("hidden");
                                    $(selectInput).parent().next().removeClass("hidden");
                                    $(selectInput).parent().next().find("select").prop('disabled', false);
                                    if($(selectInput).parent().next().find('option[selected="selected"]').attr("class").split(' ')[2]!='hidden'){
                                        $(selectInput).parent().next().find('option[selected="selected"]').prop('selected', true);
                                    }else{
                                        $(selectInput).parent().next().find('option[selected="selected"]').removeAttr('selected')
                                        $(selectInput).parent().next().find("select").append(new Option("Select", ""));
                                        $(selectInput).parent().next().find('option[value=""]').prop('selected', true);
                                    }
                                }

                            }
                            </g:if>
                        }
                    }
                }
            }
        }

        function updateHiddenField(obj, fieldId){
            $("[data-additionalQuestionId="+fieldId +"]").val($(obj).val())
            const divWithResourceTables = $("[data-additionalQuestionId="+fieldId +"]").closest("div")
            const resourceTableWithInputs = $(divWithResourceTables).find("table[id$='Resources']")
            $(resourceTableWithInputs).find("input[type='text'], select").trigger("change")
        }
    </script>
    <tr id="${trId}">
        <td${question.defaultValue && question.showDefaultResourceOnly ? " style=\"vertical-align:bottom\"" : ''}>
            <g:if test="${questionAnswers && !questionAnswers.isEmpty()}"><g:set var="defaultFromUser" value="${questionAnswers[0]}" /></g:if>
            <g:else><g:set var="defaultFromUser" value="${questionService.getDefaultValueFromUserAttribute(question?.defaultValueFromUser)}" /></g:else>
            <g:set var="resourceId" value="" />
            <g:set var="profileId" value="" />
            <g:set var="staticFullName" value="" />
            <g:each in="${resources}" var="resource">
                <g:if test="${resourceDatasets?.collect{it?.resourceId}?.contains(resource?.resourceId)}">
                    <g:set var="resourceId" value="${resource?.resourceId}" />
                    <g:set var="profileId" value="${resource?.profileId}" />
                    <g:set var="staticFullName" value="${g.singleResourceLabel(indicator: indicator, resource: resource, query: query)}" />
                </g:if>
                <g:elseif test="${!noDefaults && resource?.resourceId == defaultFromUser && !resourceId}">
                    <g:set var="resourceId" value="${resource?.resourceId}" />
                    <g:set var="profileId" value="${resource?.profileId}" />
                    <g:set var="staticFullName" value="${g.singleResourceLabel(indicator: indicator, resource: resource, query: query)}" />

                </g:elseif>
                <g:elseif test="${!noDefaults && resource?.resourceId == question?.defaultValue && !resourceId}">
                    <g:set var="resourceId" value="${resource?.resourceId}" />
                    <g:set var="profileId" value="${resource?.profileId}" />
                    <g:set var="staticFullName" value="${g.singleResourceLabel(indicator: indicator, resource: resource, query: query)}" />
                </g:elseif>
                <g:elseif test="${!noDefaults && !resourceId && question?.defaultValueFromCountryResource && resource?.resourceId == question?.getCountryResourceDefault(parentEntity)}">
                    <g:set var="resourceId" value="${resource?.resourceId}" />
                    <g:set var="profileId" value="${resource?.profileId}" />
                    <g:set var="staticFullName" value="${g.singleResourceLabel(indicator: indicator, resource: resource, query: query)}" />
                </g:elseif>
            </g:each>

            <g:set var="allowVariableThickness" value="" />
            <g:if test="${resourceId}">
                <g:set var="allowVariableThickness" value="${resources?.find({it?.resourceId.equals(resourceId)})?.allowVariableThickness}" />
            </g:if>
            <g:set var="additionalfieldId" value="" />
            <g:each in="${additionalQuestions}" var="additionalQuestion">
                <g:if test="${'profileId' == additionalQuestion.questionId}">
                    <g:set var="additionalFieldId"><g:replace value="${mainSection?.sectionId}.${question?.questionId}_additional_${additionalQuestion?.questionId}" source="." target="" /></g:set>
                </g:if>
            </g:each>
             <g:if test="${additionalQuestions}"><label>&nbsp;</label></g:if>
            <g:if test="${question.defaultValue && question.showDefaultResourceOnly}">
                <div style="display:table-cell; vertical-align:middle">
                    <g:if test="${resources && resources.find({ question.defaultValue.equals(it.resourceId)})}">
                        <p ${inputWidth}><g:resourceLabel indicator="${indicator}" resource="${resources.find({ question.defaultValue.equals(it.resourceId)})}" query="${query}"/></p>
                    </g:if>
                    <g:else>
                        <g:message code="query.no_valid_resources" />
                    </g:else>
                </div>
            </g:if>
            <g:else>

                <g:if test="${showTypeahead}">
                    <div class="input-append${showMandatory && !resourceDatasets && !optional ? ' redBorder ' : ''}" id="resourceBox${question?.questionId}">
                        <input type="text" value="${staticFullName}"  class="autocomplete" class="input-xlarge" autocomplete="off" data-targetBubble="${selectId}info"
                               placeholder="${query?.localizedtypeaheadSearchText ? query.localizedtypeaheadSearchText : message(code: "typeahead.info")}"
                               data-parentEntityId="${parentEntity?.id}" data-indicatorId="${indicator?.indicatorId}" data-singleresourceselect="true"
                               data-queryId="${query?.queryId}" data-sectionId="${mainSection?.sectionId}" data-entityId="${entity?.id}"
                               data-quarterlyInputEnabled="${quarterlyInputEnabled ? 'true' : 'false'}" data-monthlyInputEnabled="${monthlyInputEnabled ? 'true' : 'false'}"
                               data-selectId="${selectId}" data-resourceTableId="${resourceTableId}" data-fieldName="${fieldName}"
                               data-accountId="${account?.id ?: ""}" data-questionId="${question?.questionId}"
                               onchange="changeInputNameAndUnit${textId.replace("-","_")}('${trId}', '${selectId}', '${textId}', '${fieldName}', '${formattedAdditionalQuestionNames ? formattedAdditionalQuestionNames.trim() : additionalQuestionNames}');" ${disabledAttribute}/>
                        <a tabindex="-1" id="${selectId}showAll" class="add-on showAllResources " ${disabledAttribute ? "" : "onclick=\"showAllQueryResources(this,'${question?.questionId}','${message(code: 'query.resource.popover')}');\""} ><i class="icon-chevron-down"></i></a>
                        <a href="javascript:" class="dataHitsButton add-on hidden" id="${question.questionId}dataHits"></a>
                        <input type="hidden" name="${fieldName}" id="${selectId}" value="${resourceId}" />
                    </div>
                    <span id="${selectId}loadingResource" style="display: none;"><i  class="fas fa-circle-notch fa-spin" aria-hidden="true"></i></span>
                    <span class="infoBubbleContainer">
                        <g:if test="${resource?.isExpiredDataPoint}">
                            <img src='/app/assets/img/iconExpiredBig.png' class='expiredIcon'/>
                            <g:message code="data_point.expired"/>
                        </g:if>
                        <opt:renderDataCardBtn indicatorId="${indicator?.indicatorId}" resourceId="${resourceId}" profileId="${profileId}" childEntityId="${entity?.id}" showGWP="true" infoId="${selectId}" queryPage="true"/>
                    </span>
                </g:if>
                <g:else>
                    <select ${inputWidth}
                            class="${showTypeahead ? 'autocompletebox' : ''} ${dataset?.verified ? 'removeClicks' : ''} ${Dataset.VERIFIABLE_INPUT}"
                            data-autocomplete-placeholder="${message(code: 'query.choose_resource')}"
                            name="${fieldName}" ${showMandatory && !resourceDatasets && !optional ? ' style=\"border: 1px solid red;\"' : ''}  ${showTypeahead && showMandatory && !resourceDatasets && !optional ? 'required="true"' : ''}
                            id="${selectId}" ${disabledAttribute}
                            onchange="${question?.questionId == com.bionova.optimi.core.Constants.LOCAL_COMP_TARGET_QUESTIONID ? "rerenderEnergyProfiles(this, '${com.bionova.optimi.core.Constants.LOCAL_COMP_ENERGY_PROFILE_QUESTIONID}', '${resourceId}', '${query?.queryId}', '${mainSectionId}', '${question?.questionId}', null, null, true, '${indicator?.indicatorId}');" : "changeInputNameAndUnit${textId.replace("-","_")}('${trId}', '${selectId}', '${textId}', '${fieldName}', '${formattedAdditionalQuestionNames ? formattedAdditionalQuestionNames.trim() : additionalQuestionNames}'); renderResourceProfiles('${selectId}', '${additionalFieldId}');"}
                            removeBorder(this${(question.inputWidth) ? ', \'' + (question.inputWidth) + '\'' : ''});${isQuestionUsedForAdditionalJSCalculation ? 'updateHiddenField(this, \'' + question.questionId + '\');' : ''}
                            removeElementById('<g:replace value="${mainSectionId}${question?.questionId}profileHidden" source="." target="" />');">
                    <g:if test="${resources}">
                          <g:if test="${showTypeahead}">
                            <option value="" selected="selected"></option>
                          </g:if>
                          <g:else>
                            <option value=""><g:message code="query.choose_resource" /></option>
                          </g:else>

                          <g:each in="${resources}" var="resource">
                            <g:if test="${resourceDatasets?.collect{it?.resourceId}?.contains(resource?.resourceId)}">
                              <g:set var="resource" value="${resource}" />
                              <g:set var="quantityName" value="${fieldName}.${resource?.resourceId}" />
                            </g:if>
                              <g:elseif test="${!noDefaults && defaultFromUser && resource?.resourceId == defaultFromUser}">
                                  <g:set var="resource" value="${resource}" />
                                  <g:set var="quantityName" value="${fieldName}.${defaultFromUser}" />
                                  <g:set var="defaultResource" value="${resource}"/>
                              </g:elseif>
                            <g:elseif test="${!noDefaults && resource?.resourceId == question.defaultValue}">
                              <g:set var="resource" value="${resource}" />
                              <g:set var="quantityName" value="${fieldName}.${question.defaultValue}" />
                            </g:elseif>
                            <option value="${resource?.resourceId}${quantity ? ' ' + resource?.unitForData : ''}"${resourceId?.equals(resource?.resourceId) ? ' selected="selected"' : ''}><g:resourceLabel indicator="${indicator}" resource="${resource}" query="${query}"/></option>
                          </g:each>
                    </g:if>
                    <g:else>
                        <option value=""><g:message code="query.no_valid_resources" /></option>
                    </g:else>
                    </select>
                </g:else>
            </g:else>
                <g:set var="profileIdHidden" data-autocomplete-placeholder="${message(code:'query.choose_resource')}" value=""/>
                 <g:if test="${!additionalQuestions?.collect({it.questionId})?.contains("profileId")}">
                     <g:each in="${resourceDatasets}" var="dataset" status="dIndex">
                         <g:if test="${dataset?.profileId && !additionalQuestions?.collect({it.questionId})?.contains("profileId")}">
                             <g:set var="profileIdHidden">
                                 <input type="hidden" name="${dataset?.sectionId}.${dataset?.questionId}_additional_profileId.${dataset?.resourceId}" id="<g:replace value="${mainSection?.sectionId}${question?.questionId}profileHidden " source="." target="" />" value="${dataset.profileId}" />
                             </g:set>
                         </g:if>
                     </g:each>
                 </g:if>
            <g:render template="/query/inputs/verifyDatasetInputs" model="[forResourceRow: true, dataset: dataset, questionIdPrefix: fieldName, entity: (Entity) entity, resourceId : resourceId]"/>
        </td>
        <g:if test="${quantity}">
            <g:if test="${question.defaultValue && !quantityName}">
                <g:set var="quantityName" value="${fieldName}.${question.defaultValue}" />
            </g:if>
            <g:set var="quantityAnswer" value="" />
            <g:set var="dataset" value="${null}" />
            <g:each in="${resourceDatasets}" var="dataset" status="dIndex">
                <g:set var="quantityAnswer" value="${dataset.quantity != null ? dataset.answerIds[0] : ''}" />
                <g:set var="dataset" value="${dataset}" />
            </g:each>
            <td>
                <g:set var="checkValue"><g:if test="${question?.onlyNumeric}"> onblur="checkValue(this, '${question?.localizedQuestion}', '${question?.minAllowed}', '${question?.maxAllowed}'${question?.inputWidth ? ', ' + question.inputWidth : ''});"</g:if></g:set>
                <label>${indicator && indicator.requireMonthly ? message(code: 'totalScore') : message(code: 'query.quantity')}</label>
                <g:if test="${monthlyInputEnabled || disabled || preventChanges}">
                    <input type="text" disabled="disabled" value="${quantityAnswer}" onchange="removeBorder(this);updateHiddenField(this, '${question.questionId}')" class="quantity numeric input-mini"${showMandatory && !optional && !quantityAnswer ? ' style=\"border: 1px solid red;\"' : ''}${checkValue} />
                    <input type="hidden" ${enabledAttribute} id="${textId}" value="${quantityAnswer}" name="${quantityName}" class="quantity numeric input-mini" ${checkValue} />
                </g:if>
                <g:else>
                    <input type="text" ${enabledAttribute} id="${textId}" value="${quantityAnswer}" name="${quantityName}" onchange="removeBorder(this);updateHiddenField(this, '${question.questionId}')" class="quantity numeric input-mini"${showMandatory && !optional && !quantityAnswer ? ' style=\"border: 1px solid red;\"' : ''}${checkValue} />
                </g:else>
            <queryRender:userGivenUnitForSingleResourceQuestion disabled="${preventChanges}" resource="${resource}" dataset="${dataset}" mainSectionId="${mainSection?.sectionId}" questionId="${question?.questionId}" />
                <g:if test="${monthlyInputEnabled || (indicator && indicator.requireMonthly)}">
                    <td style="padding: 0; vertical-align: top;"><table><tr>
                        <g:each in="[1,2,3,4,5,6,7,8,9,10,11,12]">
                            <th class="monthly_data"><g:message code="month.${it}" /></th>
                        </g:each>
                        </tr>
                        <tr><g:render template="/query/monthlydata" model="[fieldName: quantityName, monthlyAnswers: dataset?.monthlyAnswers, disabled: disabled]" /></tr></table>
                    </td>
                </g:if>
            </td>
        </g:if>
        <g:each in="${additionalQuestions}" var="additionalQuestion">
            <g:set var="additionalQuestionAnswer" value="" />
            <g:set var="resource" value="${null}" />
            <g:each in="${resourceDatasets}" var="dataset" status="dIndex">
                <g:set var="additionalQuestionAnswer" value="${dataset?.additionalQuestionAnswers?.get(additionalQuestion.questionId)}" />
                <g:if test="${'profileId' == additionalQuestion.questionId && dataset?.profileId}">
                    <g:set var="additionalQuestionAnswer" value="${dataset.profileId}" />
                </g:if>
                <g:set var="resource" value="${dataset?.resource}" />
            </g:each>
            <g:if test="${"state".equals(additionalQuestion?.questionId) && !additionalQuestionAnswer && questionAnswers && !questionAnswers.isEmpty() && questionAnswers.size() > 1}">
                <g:set var="additionalQuestionAnswer" value="${questionAnswers[1]}" />
            </g:if>
            <g:render template="/query/additionalquestion" model="[entity:entity, query:query, section:section, mainSectionId:mainSection?.sectionId, additionalQuestion:additionalQuestion, additionalQuestionAnswer: additionalQuestionAnswer, mainQuestion:question, resourceId:resourceId, resource: resource, allowVariableThickness:allowVariableThickness, preventChanges: preventChanges, defaultResource: defaultResource]" />
        </g:each>
        <td>
            <g:if test="${showHistory}">
                <input type="button" ${enabledAttribute} class="primary btnRemove" style="font-weight: bold;"  value="${message(code: 'query.previous_answers')}"
                    onclick="renderPreviousOperatingPeriods('${parentEntity?.id}', '${entity?.id}', '${indicator?.indicatorId}', '${query?.queryId}', '${mainSection?.sectionId}', '${question?.questionId}', '${resourceTableId}History');" />
            </g:if>
        </td>
        <g:if test="${renderDataLoadingFeature && !preventChanges}">
            <td><g:if test="${additionalQuestions}"><label>&nbsp;</label></g:if>
                <a href="javascript:" id="dataLoadingFeature" data-indicatorId="${indicator?.indicatorId}" data-entityId="${parentEntity?.id}"
                   data-queryId="${query?.queryId}" data-definingSectionId="${renderDataLoadingFeature?.filterParameterDefinition?.get("sectionId")}"
                   data-definingQuestionId="${renderDataLoadingFeature?.filterParameterDefinition?.get("questionId")}" data-targetSectionId="${renderDataLoadingFeature?.loadResourcesTarget?.get("sectionId")}"
                   data-targetQuestionId="${renderDataLoadingFeature?.loadResourcesTarget?.get("questionId")}" data-quantityResolutionSectionId="${renderDataLoadingFeature?.loadQuantityResolutionUserChosenVariable?.get("sectionId")}"
                   data-quantityResolutionQuestionId="${renderDataLoadingFeature?.loadQuantityResolutionUserChosenVariable?.get("questionId")}" class="btn btn-primary">${renderDataLoadingFeature.localizedName}</a>
            </td>
        </g:if>
    </tr>
</table>
