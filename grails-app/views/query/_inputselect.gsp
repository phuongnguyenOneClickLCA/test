<%@ page import="com.bionova.optimi.core.domain.mongo.Entity; com.bionova.optimi.core.domain.mongo.Dataset" %>
<g:set var="questionService" bean="questionService"/>
<g:set var="dataset"
       value="${questionService?.getDatasetForGsp(question?.questionId, query?.queryId, mainSectionId, null, null, entity, parentEntity, null) as Dataset}"/>
<g:questionAnswer var="questionAnswers"
                  entity="${entity}" parentEntity="${parentEntity}" queryId="${query?.queryId}" sectionId="${mainSectionId}" questionId="${question?.questionId}" inputType="select" entityClassResource="${entityClassResource}" dataset="${dataset}"  />
<g:if test="${!questionAnswers && question?.questionId == "country" && country}"><g:set var="questionAnswers" value="${[country, state]}" /></g:if>
<g:elseif test="${!questionAnswers && question?.questionId == "type" && buildingType}"><g:set var="questionAnswers" value="${[buildingType]}" /></g:elseif>
<g:set var="multipleChoicesAllowed" value="${question?.defineMultipleChoicesAllowed(section) ?: false}"/>
<g:set var="singleResourceQuestion" value="${!multipleChoicesAllowed && question?.resourceGroups}"/>
<div><div style="display: inline-block;z-index: 11"><label style="display:inline-block;">${question?.localizedQuestion}<g:if test="${!question?.optional}"> (<g:message code="mandatory" />)</g:if></label>
<g:if test="${question?.localizedHelp && question?.helpType?.equalsIgnoreCase('hoverover')}">
    <span class="fiveMarginLeft tableHeadingPopover hidden-print" href="#" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${question.localizedHelp}"><i class="far fa-question-circle"></i></span>
</g:if>
<g:if test="${showOtherDesigns}">
    <div class="btn-group" style="display: inline-block; padding-left: 0 !important;">
        <a href="javascript:" onclick="showOtherDesigns($(this), '${parentEntity?.id}', '${entity?.id}', '${indicator?.indicatorId}', '${query?.queryId}', '${mainSectionId}', '${question?.questionId}', '<g:replace value="${divId}Other" source="." target="" />', '${modifiable}');" rel="popover" data-toggle="dropdown" class="primary dropdown-toggle" > <i class="fas fa-exchange-alt"></i> <strong><g:message code="query.other_answers" /></strong> <span class="caret caret-middle"></span></a>
    </div>
    <g:if test="${groupMaterialsAllowed && modifiable && !preventChanges}">
        <div class="btn-group groupingButtonContainer" style="display: inline-block; padding-left: 0 !important; margin-left:8px;">
            <a href="javascript:" class="groupDisabled primary" onclick="openGroupingContainer('${entity?.id?.toString()}','${indicator?.indicatorId}','${query?.queryId}','${mainSectionId}', '${question?.questionId}')" rel="popover" data-toggle="dropdown"> <i class="fas fa-plus-square"></i> <strong><g:message code="create_a_group" /></strong></a>
        </div>
    </g:if>
    <g:if test="${allQuestionsForMoving && modifiable && !preventChanges}">
        <div class="btn-group" style="display: inline-block; padding-left: 0 !important; margin-left: 15px">
            <a href="javascript:" onclick="showResourceMovers();" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${g.message(code: "move_materials_long")}"> <i class="fas fa-arrows-alt"></i> <strong><g:message code="move_materials" /></strong></a>
        </div>
    </g:if>
    <g:if test="${compareMaterialsAllowed && modifiable && !preventChanges}">
        <div class="btn-group compareButtonQuery"  style="display: inline-block; padding-left: 0 !important;">
        <a href="javascript:" id="${question?.questionId}CompareButton" onclick="appendResourceToCompareForm(this)" rel="popover" data-placement="bottom" data-trigger="hover" data-html="true" data-content="${message(code: 'compare_data_help')}" class="grayLink compareButton" data-value=""> <i class="fas fa-balance-scale"></i> <strong><g:message code="add_to_compare" /> <span class="compareBtnCount" id="${question?.questionId}CompareButtonCount"></span></strong></a>
        </div>
    </g:if>
    %{-- << verificationPoint >> --}%
    <g:if test="${question?.verificationPoints}">
        <opt:verificationPointIcon question="${question}"/>
    </g:if>
    <g:if test="${!question?.resourceGroups || singleResourceQuestion}">
        <g:render template="/query/icons/verifyDatasetIcons" model="[forNotRow: true, dataset: dataset, user: user]"/>
        <span id="${divId}UnlockVerifiedDatasetLink">
          <g:render template="/query/links/unlockVerifiedDatasetLink" model="[forNotRow: true, unlockVerifiedDatasetLicensed: unlockVerifiedDatasetLicensed, dataset: dataset]"/>
        </span>
    </g:if>
    <div>
        <div style="display: inline-block">
            <div class="alert alert-gray" id="<g:replace value="${divId}Other" source="." target="" />" style="display: none; border: none;">
                <button type="button" class="close" onclick="$('#<g:replace value="${divId}Other" source="." target="" />').fadeOut();">×</button>
            </div>
        </div>
    </div>
</g:if>
<g:else>
    <g:if test="${groupMaterialsAllowed && modifiable && !preventChanges}">
        <div class="btn-group groupingButtonContainer" style="display: inline-block; padding-left: 0 !important; margin-left:15px;">
            <a href="javascript:" class="groupDisabled" onclick="openGroupingContainer('${entity?.id?.toString()}','${indicator?.indicatorId}','${query?.queryId}','${mainSectionId}', '${question?.questionId}')" rel="popover" data-toggle="dropdown" class="primary" > <i class="fas fa-plus-square"></i> <strong><g:message code="create_a_group" /></strong></a>
        </div>
    </g:if>
    <g:if test="${allQuestionsForMoving && modifiable && !preventChanges}">
        <div class="btn-group" style="display: inline-block; padding-left: 0 !important; margin-left: 15px">
            <a href="javascript:" onclick="showResourceMovers();" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${g.message(code: "move_materials_long")}"> <i class="fas fa-arrows-alt"></i> <strong><g:message code="move_materials" /></strong></a>
        </div>
    </g:if>
    <g:if test="${compareMaterialsAllowed && modifiable && !preventChanges}">
        <div class="btn-group compareButtonQuery"  style="display: inline-block; padding-left: 0 !important;">
        <a href="javascript:" id="${question?.questionId}CompareButton" onclick="appendResourceToCompareForm(this)" rel="popover" data-placement="bottom" data-trigger="hover" data-html="true" data-content="${message(code: 'compare_data_help')}" class="grayLink compareButton" data-value=""> <i class="fas fa-balance-scale"></i> <strong><g:message code="add_to_compare" /> <span class="compareBtnCount" id="${question?.questionId}CompareButtonCount"></span></strong></a>
        </div>
    </g:if>
    %{-- << verificationPoint >> --}%
    <g:if test="${question?.verificationPoints}">
        <opt:verificationPointIcon question="${question}"/>
    </g:if>
    <g:if test="${!question?.resourceGroups || singleResourceQuestion}">
        <g:render template="/query/icons/verifyDatasetIcons" model="[forNotRow: true, dataset: dataset, user: user]"/>
        <span id="${divId}UnlockVerifiedDatasetLink">
          <g:render template="/query/links/unlockVerifiedDatasetLink" model="[forNotRow: true, unlockVerifiedDatasetLicensed: unlockVerifiedDatasetLicensed, dataset: dataset]"/>
        </span>
    </g:if>
</g:else>

<g:if test="${groupMaterialsAllowed && modifiable && !preventChanges}">
    <div class="alert alert-gray rowGroupingContainer" id="${question?.questionId}RowGroupingContainer" style="display: none; width: 100%;">
        <div id="${question?.questionId}RowGroupingTableContainer">
            <div id="${question?.questionId}GroupingError"></div>
            <button type="button" class="close" onclick="closeGroupingContainer('${question?.questionId}RowGroupingContainer','${question?.questionId}TempHead','${question?.questionId}defaultConstructionRow', '${question?.questionId}RowGroupingContainerTableBody');">×</button>
            <table id="${question?.questionId}RowGroupingConstructionTable" class="left_alignment" style="display: none">
                <g:render template="rowGroupingConstruction" model="[question:question,unitForConstruction:unitForConstruction,query: query,
                                                                     constructionResourceTypes:constructionResourceTypes,user:user,entity: entity,
                                                                     parentEntity:parentEntity,showInMappingQuestion:showInMappingQuestion,
                                                                     brandImages:brandImages,construction:construction,privateClassificationQuestion:privateClassificationQuestion,
                                                                     indicator:indicator,createConstructionsAllowed:createConstructionsAllowed,
                                                                     account:account,mainSectionId:mainSectionId,publicGroupAllowed:publicGroupAllowed]" />
                </table>
            <table id="${question?.questionId}RowGroupingContainerTable" class="left_alignment" style="display: none; min-height: 50px; width: 100%;">
                <thead id="${question?.questionId}RowGroupingContainerTableHead"></thead>
                <tbody id="${question?.questionId}RowGroupingContainerTableBody">
                <tr class="hiddenDummyRow"><td colspan="99" class="hiddenDummyTd"><div class="dragNDrop"><g:message code="drag_n_drop" /></div></td></tr>
                </tbody>
            </table>
        </div>
        <div id="${question?.questionId}RowGroupingSpinnerContainer" class="groupingSpinningContainer"></div>
    </div>
</g:if>

<div>
<g:if test="${question?.localizedHelp && !question?.helpType?.equalsIgnoreCase('hoverover')}">
    <i id="toggleAnswerSectionIcon-${mainSectionId}-${question.questionId}" style="display:inline-block; margin-bottom: 15px; cursor:pointer; font-size:15px;" onclick="$(this).next().toggleClass('hidden');$(this).next().next().toggleClass('hidden');$(this).toggleClass('fa fa-plus').toggleClass('fa fa-minus');" class="${expandable && !questionAnswers ? 'fa fa-plus fa-1x primary btnRemove' : 'hidden'}"></i>
    <a  href="javascript:;" class="${expandable && !questionAnswers  ? 'expanded primary btnRemove' : 'hidden'}" style="display:inline-block; margin-left:5px; font-weight:bold; cursor:pointer;" onclick="$(this).toggleClass('hidden');$(this).next().toggleClass('hidden'); $(this).prev().toggleClass('fa fa-minus').toggleClass('fa fa-plus');"><g:message code="query.question.expand"/></a><div id="${question.questionId}question" class="${expandable && !questionAnswers ? 'hidden' : 'visible'}">
    <span id="correct" class="help-block"${questionHelpWidth? ' style=\"width:' + questionHelpWidth + '%;\"' : ''}>${questionService.getLocalizedHelp(question)}</span>
</g:if><g:else>
    <i id="toggleAnswerSectionIcon-${mainSectionId}-${question.questionId}" style="display:inline-block; margin-bottom: 15px; cursor:pointer; font-size:15px;" onclick="$(this).next().toggleClass('hidden');$(this).next().next().toggleClass('hidden');$(this).toggleClass('fa fa-plus').toggleClass('fa fa-minus');" class="${expandable && !questionAnswers ? 'fa fa-plus fa-1x primary btnRemove' : 'hidden'}"></i>
    <a  href="javascript:;" class="${expandable && !questionAnswers  ? 'expanded primary btnRemove' : 'hidden'}" style="display:inline-block; margin-left:5px; font-weight:bold; cursor:pointer;" onclick="$(this).toggleClass('hidden');$(this).next().toggleClass('hidden'); $(this).prev().toggleClass('fa fa-minus').toggleClass('fa fa-plus');"><g:message code="query.question.expand"/></a><div id="${question.questionId}question" class="${expandable && !questionAnswers ? 'hidden' : 'visible'}">
</g:else>

    <table class="query_resource">
        <tr class="queryQuestionInput ${(!question?.resourceGroups || singleResourceQuestion) && dataset?.unlockedFromVerifiedStatus ? 'unlockedVerifiedDatasetStatusBg' : (!question?.resourceGroups || singleResourceQuestion) && dataset?.verified ? 'verifiedDatasetBackground' : ''}">
        <td>
            <g:if test="${question?.resourceGroups}">
            <queryRender:renderResourceQuestion questionAnswers="${questionAnswers}" allowQuarterlyData="${allowQuarterlyData}"
            allowMonthlyData="${allowMonthlyData}" indicator="${indicator}" divId="${divId}" entity="${entity}" modifiable="${modifiable}"
            parentEntity="${parentEntity}" query="${query}" question="${question}" mainSectionId="${mainSectionId}" section="${section}"
            noDefaults="${noDefaults}" additionalQuestionLicensed="${additionalQuestionLicensed}" groupMaterialsAllowed="${groupMaterialsAllowed}"
            preventChanges="${preventChanges}" additionalQuestionInputWidth="${additionalQuestionInputWidth}"
            maxRowLimitPerDesignReached="${maxRowLimitPerDesignReached}" isPlanetary="${isPlanetary}"
            eolProcessCache="${eolProcessCache}" additionalCalcParamsForCurrentQuery="${additionalCalcParamsForCurrentQuery}"/>${resourceQuestion}
            </g:if>
            <g:else>
                <g:if test="${additionalQuestions}"><label>&nbsp;</label></g:if>
                <g:set var="choices" value="${questionService.getFilteredChoices(parentEntity, null, null, question)}"/>
                <g:if test="${dataLoadingFromFile}">
                    <g:each in="${question?.getanswerIdLimitConditionMap()}" var="betieCondition">
                        <g:hiddenField id="${betieCondition?.key}" name="${fieldName}_betie" value="${betieCondition?.value}"/>
                    </g:each>
                </g:if>
                <select name="${fieldName}"
                        class="${dataset?.verified ? 'removeClicks' : ''} ${Dataset.VERIFIABLE_INPUT}"
                        id="${fieldName}" ${g.questionInlineStyle(question: question, answer: questionAnswers, showMandatory: showMandatory)} ${disabledAttribute}
                        onchange="selectShowHideDiv(this, '${divId}');removeBorder(this, '${inputWidthForBorder}');"
                        onfocus="if('${dataLoadingFromFile}')limitChoices('${fieldName}','${mainSectionId}');"
                        ${inputWidth} ${question.irreversible && questionAnswers && !superUser ? "disabled=\"disabled\"" : ""}>
                    <g:if test="${!question?.noEmptyOption}">
                        <option value=""></option>
                    </g:if>
                    <g:each in="${choices}" var="choice" status="index">
                        <g:if test="${questionAnswers && questionAnswers.contains(choice.answerId)}">
                            <g:set var="selected" value="selected='selected'"/>
                        </g:if>
                        <g:elseif
                                test="${!noDefaults && !questionAnswers && choice.answerId == question?.defaultValue}">
                            <g:set var="selected" value="selected='selected'"/>
                        </g:elseif>
                        <g:elseif
                                test="${!noDefaults && !questionAnswers && question?.defaultValueFromCountryResource && choice.answerId == question?.getCountryResourceDefault(parentEntity)}">
                            <g:set var="selected" value="selected='selected'"/>
                        </g:elseif>
                        <g:else>
                            <g:set var="selected" value=""/>
                        </g:else>
                        <option value="${choice.answerId}" ${selected}${choice.disabled ? " class=\"disabledSelectOption\" disabled=\"disabled\"" : ""}>${choice.localizedAnswer}</option>
                    </g:each>
                </select>
                <g:if test="${question.irreversible && questionAnswers && !superUser}">
                    <input type="hidden" name="${fieldName}" value="${questionAnswers[0].toString()}">
                </g:if>
                <g:render template="/query/inputs/verifyDatasetInputs" model="[forNonResourceRow: true, dataset: dataset, questionIdPrefix: fieldName, entity: (Entity) entity]"/>
                </td>
                <g:each in="${additionalQuestions}" var="additionalQuestion">
                    <g:if test="${additionalQuestionAnswers}">
                        <g:set var="additionalQuestionAnswer"
                               value="${additionalQuestionAnswers?.get(additionalQuestion.questionId)}"/>
                    </g:if>
                    <g:else>
                        <g:set var="additionalQuestionAnswer" value=""/>
                    </g:else>
                    <g:render template="/query/additionalquestion"
                              model="[entity: entity, query: query, section: section, inputType: inputType, mainSectionId: mainSectionId, additionalQuestion: additionalQuestion, additionalQuestionAnswer: additionalQuestionAnswer, mainQuestion: question, modifiable: modifiable, additionalQuestionInputWidth: additionalQuestionInputWidth]"/>
                </g:each>
            </g:else>
        </tr>
    </table>
</div>
</div>
</div>
</div>
<g:if test="${question?.choices && question?.choices?.find({it.ifChosenSections||it.ifChosen})}">
    <g:each in="${question?.choices}" var="choice" status="index">
        <g:set var="choiceDivId" value="${divId}${index + 1}" />
        <g:set var="display">
            <g:if test="${printable}">
                block
            </g:if>
            <g:else>
                <g:blockOrNone entity="${entity}" queryId="${query?.queryId}" sectionId="${mainSectionId}" questionId="${question?.questionId}" answerId="${choice.answerId}" />
            </g:else>
        </g:set>
        <div style="display: ${display}; margin-left: 15px; margin-top: 10px;" id="${choiceDivId}">
            <g:each in="${choice.ifChosenSections}" var="ifChosenSection">
                <g:if test="${printable}">
                    <strong>${choice.localizedAnswer}: </strong>
                </g:if>
                <g:render template="/query/section" model="[entity:entity, query:query, section:ifChosenSection, isMain:false, mainSectionId:mainSectionId, choice:choice, divId:divId, display:display, modifiable: modifiable]" />
            </g:each>
            <g:each in="${choice.ifChosen}" var="ifChosenQuestion">
                <g:if test="${printable}">
                    <strong>${choice.localizedAnswer}: </strong>
                </g:if>
                <queryRender:renderQuestion
                        indicator="${indicator}"
                        query="${query}"
                        divId="${divId}"
                        entity="${entity}"
                        parentEntity="${parentEntity}"
                        modifiable="${modifiable}"
                        queryId="${query?.queryId}"
                        question="${ifChosenQuestion}"
                        section="${mainSection ? mainSection : section}"
                        additionalCalcParamsForCurrentQuery="${additionalCalcParamsForCurrentQuery}"/>
            </g:each>
        </div>
    </g:each>
</g:if>
