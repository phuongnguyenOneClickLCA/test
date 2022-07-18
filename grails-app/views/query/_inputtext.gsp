<%@ page import="com.bionova.optimi.core.domain.mongo.Entity; com.bionova.optimi.core.domain.mongo.Dataset" %>
<g:set var="questionService" bean="questionService" />
<g:set var="dataset"
       value="${questionService?.getDatasetForGsp(question?.questionId, query?.queryId, mainSectionId, null, null, entity, parentEntity, null) as Dataset}"/>
<g:set var="questionAnswer"
       value="${g.questionAnswer(entity: entity, parentEntity: parentEntity, indicatorId: indicator?.indicatorId, queryId: query?.queryId, sectionId: mainSectionId, questionId: question?.questionId, entityClassResource: entityClassResource, inputType: 'text', dataset: dataset)}" />
<g:if test="${!questionAnswer && question?.questionId == "address" && address}"><g:set var="questionAnswer" value="${address}" /></g:if>
<div>
<div id="toggle${section?.sectionId}" class="input-append ${question?.hideValueInQuery ? 'hidden' : ''}">
<div display="inline-block"><label style="display:inline-block;">${question?.localizedQuestion}<g:if test="${!question?.optional}"> (<g:message code="mandatory" />)</g:if></label>
<g:if test="${showOtherDesigns}">
    <div class="btn-group" style="display: inline-block;padding-left: 0 !important;">
        <a href="javascript:" onclick="showOtherDesigns($(this), '${parentEntity?.id}', '${entity?.id}', '${indicator?.indicatorId}', '${query?.queryId}', '${mainSectionId}', '${question?.questionId}', '<g:replace value="${divId}Other" source="." target="" />', '${modifiable}');" rel="popover" data-toggle="dropdown" class="primary dropdown-toggle"><strong> <g:message code="query.other_answers" /></strong> <span class="caret caret-middle"></span></a>
    </div>
    %{-- << verificationPoint >> --}%
    <g:if test="${question?.verificationPoints}">
        <opt:verificationPointIcon question="${question}"/>
    </g:if>
    <g:render template="/query/icons/verifyDatasetIcons" model="[forNotRow: true, user: user, dataset: dataset]"/>
    <span id="${divId}UnlockVerifiedDatasetLink">
      <g:render template="/query/links/unlockVerifiedDatasetLink" model="[forNotRow: true, unlockVerifiedDatasetLicensed: unlockVerifiedDatasetLicensed, dataset: dataset]"/>
    </span>
    </div>
    <div style="display: inline-block">
        <div class="alert alert-gray" id="<g:replace value="${divId}Other" source="." target="" />" style="display: none; border: none">
            <button type="button" class="close" onclick="$('#<g:replace value="${divId}Other" source="." target="" />').fadeOut();">×</button>
        </div>
    </div>
</g:if>
<g:else>
    %{-- << verificationPoint >> --}%
    <g:if test="${question?.verificationPoints}">
        <opt:verificationPointIcon question="${question}"/>
    </g:if>
    <g:render template="/query/icons/verifyDatasetIcons" model="[forNotRow: true, user: user, dataset: dataset]"/>
    <span id="${divId}UnlockVerifiedDatasetLink">
      <g:render template="/query/links/unlockVerifiedDatasetLink" model="[forNotRow: true, unlockVerifiedDatasetLicensed: unlockVerifiedDatasetLicensed, dataset: dataset]"/>
    </span>
    </div>
</g:else>
<g:if test="${question?.localizedHelp}">
    <g:if test="${question?.helpType?.equalsIgnoreCase('hoverover')}">
        <span class="icon-question-sign questionHelpHoverover" rel="popover" data-trigger="hover"
              data-content="${question?.localizedHelp}"></span>
        <g:if test="${expandable && !questionAnswer}">
            <i style="display:inline-block; margin-bottom: 15px; cursor:pointer; font-size:15px;"
               onclick="$(this).next().toggleClass('hidden');
               $(this).next().next().toggleClass('hidden');
               $(this).toggleClass('fa fa-plus').toggleClass('fa fa-minus');"
               class="fa fa-plus fa-1x primary btnRemove"></i>
            <a href="javascript:" class="expanded primary btnRemove"
               style="display:inline-block; margin-left:5px; font-weight:bold; cursor:pointer;"
               onclick="$(this).toggleClass('hidden');
               $(this).next().toggleClass('hidden');
               $(this).prev().toggleClass('fa fa-minus').toggleClass('fa fa-plus');">
                <g:message code="query.question.expand"/>
            </a>
        </g:if>
        <div class="${expandable && !questionAnswer ? 'hidden' : 'visible'}">
    </g:if>
    <g:else>
        <g:if test="${expandable && !questionAnswer}">
            <i style="display:inline-block; margin-bottom: 15px; cursor:pointer; font-size:15px;"
               onclick="$(this).next().toggleClass('hidden');
               $(this).next().next().toggleClass('hidden');
               $(this).toggleClass('fa fa-plus').toggleClass('fa fa-minus');"
               class="fa fa-plus fa-1x primary btnRemove"></i>
            <a href="javascript:" class="expanded primary btnRemove"
               style="display:inline-block; margin-left:5px; font-weight:bold; cursor:pointer;"
               onclick="$(this).toggleClass('hidden');
               $(this).next().toggleClass('hidden');
               $(this).prev().toggleClass('fa fa-minus').toggleClass('fa fa-plus');">
                <g:message code="query.question.expand"/>
            </a>
        </g:if>
        <div class="${expandable && !questionAnswer ? 'hidden' : 'visible'}">
        <span class="help-block" ${questionHelpWidth ? ' style=\"width:' + questionHelpWidth + '%;\"' : ''}>
            ${question?.localizedHelp}
        </span>
    </g:else>
</g:if>
<g:else>
    <g:if test="${expandable && !questionAnswer}">
        <i style="display:inline-block; margin-bottom: 15px; cursor:pointer; font-size:15px;"
           onclick="$(this).next().toggleClass('hidden');
           $(this).next().next().toggleClass('hidden');
           $(this).toggleClass('fa fa-plus').toggleClass('fa fa-minus');"
           class="fa fa-plus fa-1x primary btnRemove"></i>
        <a href="javascript:" class="expanded primary btnRemove"
           style="display:inline-block; margin-left:5px; font-weight:bold; cursor:pointer;"
           onclick="$(this).toggleClass('hidden');
           $(this).next().toggleClass('hidden');
           $(this).prev().toggleClass('fa fa-minus').toggleClass('fa fa-plus');">
            <g:message code="query.question.expand"/>
        </a>
    </g:if>
    <div class="${expandable && !questionAnswer ? 'hidden' : 'visible'}">
</g:else>
<table class="query_resource">
    <tr class="queryQuestionInput ${dataset?.unlockedFromVerifiedStatus ? 'unlockedVerifiedDatasetStatusBg' : dataset?.verified ? 'verifiedDatasetBackground' : ''}">
        <td>
            <g:if test="${additionalQuestions}">
                <label><g:message code="answer"/></label>
            </g:if>
            <g:render template="/query/inputs/verifyDatasetInputs" model="[forNonResourceRow: true, dataset: dataset, questionIdPrefix: fieldName, entity: (Entity) entity]"/>
            <input type="text" name="${fieldName}" id="${divId}"
                   onchange="removeBorder(this${question.inputWidth ? ',' + question.inputWidth : ''}${question.inputHeight ? ',' + question.inputHeight : ''});${isQuestionUsedForAdditionalJSCalculation ? 'updateHiddenField(this, \'' + question.questionId + '\');' : ''}"
                   class="input-xlarge span3 ${question.onlyNumeric ? 'numeric' : ''} ${dataset?.verified ? 'removeClicks' : ''} ${Dataset.VERIFIABLE_INPUT}" ${disabledAttribute}
                   value="${questionAnswer ? questionAnswer : question?.defaultValue}" ${g.questionInlineStyle(question: question, answer: questionAnswer, showMandatory: showMandatory)} ${checkValue} ${question.irreversible && questionAnswer && !superUser ? "readonly" : ""}/>
            <g:if test="${unit}">
                <span class="add-on">${unit}</span>
            </g:if>
            <g:each in="${additionalQuestions}" var="additionalQuestion">
                <g:if test="${additionalQuestionAnswers}">
                    <g:set var="additionalQuestionAnswer"
                           value="${additionalQuestionAnswers.get(additionalQuestion.questionId)}"/>
                </g:if>
                <g:render template="/query/additionalquestion"
                          model="[entity: entity, query: query, section: section, inputType: inputType, mainSectionId: mainSectionId, additionalQuestion: additionalQuestion, additionalQuestionAnswer: additionalQuestionAnswer, mainQuestion: question, modifiable: modifiable, additionalQuestionInputWidth: additionalQuestionInputWidth]"/>
            </g:each>
    <td class="queryPreviousAnswers">
        <g:if test="${showHistory}">
            <td class="queryPreviousAnswers">
                <input type="button" ${disabledAttribute} class="primary btnRemove"
                       style="margin-left: 30px; font-weight: bold;" value="${message(code: 'query.previous_answers')}"
                       onclick="renderPreviousOperatingPeriods('${parentEntity?.id}', '${entity?.id}', '${indicator?.indicatorId}', '${query?.queryId}', '${mainSectionId}', '${question?.questionId}', '<g:replace value="${divId}History" source="." target="" />');"/>

                <div class="alert alert-gray" id="<g:replace value="${divId}History" source="." target=""/>"
                     style="display: none; float: right; margin-left: 30px;">
                    <button type="button" class="close"
                            onclick="$('#<g:replace value="${divId}History" source="." target="" />').hide();">×</button>
                </div>
            </td>
        </g:if>
    </tr>
</table>
</div>
</div>
</div>