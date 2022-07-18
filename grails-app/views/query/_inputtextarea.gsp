<%@ page import="com.bionova.optimi.core.domain.mongo.Entity; com.bionova.optimi.core.domain.mongo.Dataset" %>
<g:set var="questionService" bean="questionService" />
<g:set var="dataset"
       value="${questionService?.getDatasetForGsp(question?.questionId, query?.queryId, mainSectionId, null, null, entity, parentEntity, null) as Dataset}"/>
<g:set var="questionAnswer"
       value="${g.questionAnswer(entity: entity, parentEntity: parentEntity, queryId: query?.queryId, sectionId: mainSectionId, questionId: question?.questionId, entityClassResource: entityClassResource, inputType: 'textarea', dataset: dataset)}"/>
<div style="display: inline-block">
    <div style="display: inline-block">
        <label>
            ${question?.localizedQuestion}
            <g:if test="${!question?.optional}">
                <g:message code="mandatory"/>
            </g:if>
        </label>
    </div>
    <g:if test="${showOtherDesigns}">
        <div class="btn-group" style="display: inline-block; padding-left: 0 !important;">
            <a href="javascript:"
               onclick="showOtherDesigns($(this), '${parentEntity?.id}', '${entity?.id}', '${indicator?.indicatorId}', '${query?.queryId}', '${mainSectionId}', '${question?.questionId}', '<g:replace value="${divId}Other" source="." target="" />', '${modifiable}');"
               rel="popover" data-toggle="dropdown" class="primary dropdown-toggle">
                <strong><g:message code="query.other_answers"/></strong>
                <span class="caret caret-middle"></span>
            </a>
        </div>

        <div class="alert alert-gray" id="<g:replace value="${divId}Other" source="." target=""/>"
             style="display: none; border:none">
            <button type="button" class="close"
                    onclick="$('#<g:replace value="${divId}Other" source="." target="" />').fadeOut();">×</button>
        </div>
    </g:if>
    %{-- << verificationPoint >> --}%
    <g:if test="${question?.verificationPoints}">
        <opt:verificationPointIcon question="${question}"/>
    </g:if>
    <g:render template="/query/icons/verifyDatasetIcons" model="[forNotRow: true, dataset: dataset, user: user, changeInputBg: true]"/>
    <span id="${divId}UnlockVerifiedDatasetLink">
      <g:render template="/query/links/unlockVerifiedDatasetLink" model="[forNotRow: true, changeInputBg: true]"/>
    </span>
</div>
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
            <a href="javascript:;" class="expanded primary btnRemove"
               style="display:inline-block; margin-left:5px; font-weight:bold; cursor:pointer;"
               onclick="$(this).toggleClass('hidden');
               $(this).next().toggleClass('hidden');
               $(this).prev().toggleClass('fa fa-minus').toggleClass('fa fa-plus');">
                <g:message code="query.question.expand"/>
            </a>
        </g:if>
        <div class="${expandable && !questionAnswer ? 'hidden' : 'visible'}"><div>

    </g:if>
    <g:else>
        <g:if test="${expandable && !questionAnswer}">
            <i style="display:inline-block; margin-bottom: 15px; cursor:pointer; font-size:15px;"
               onclick="$(this).next().toggleClass('hidden');
               $(this).next().next().toggleClass('hidden');
               $(this).toggleClass('fa fa-plus').toggleClass('fa fa-minus');"
               class="fa fa-plus fa-1x primary btnRemove"></i>
            <a href="javascript:;" class="expanded primary btnRemove"
               style="display:inline-block; margin-left:5px; font-weight:bold; cursor:pointer;"
               onclick="$(this).toggleClass('hidden');
               $(this).next().toggleClass('hidden');
               $(this).prev().toggleClass('fa fa-minus').toggleClass('fa fa-plus');"><g:message
                    code="query.question.expand"/></a>
        </g:if>
        <div class="${expandable && !questionAnswer ? 'hidden' : 'visible'}">
        <div>
        <span class="help-block"${questionHelpWidth ? ' style=\"width:' + questionHelpWidth + '%;\"' : ''}>
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
        <a href="javascript:;" class="expanded primary btnRemove"
           style="display:inline-block; margin-left:5px; font-weight:bold; cursor:pointer;"
           onclick="$(this).toggleClass('hidden');
           $(this).next().toggleClass('hidden');
           $(this).prev().toggleClass('fa fa-minus').toggleClass('fa fa-plus');">
            <g:message code="query.question.expand"/>
        </a>
    </g:if>
    <div class="${expandable && !questionAnswer ? 'hidden' : 'visible'}"><div>

</g:else>
<div class="input-append">
    <div class="queryQuestionInput ${dataset?.unlockedFromVerifiedStatus ? 'unlockedVerifiedDatasetStatusBg' : dataset?.verified ? 'verifiedDatasetBackground' : ''}">
        <g:render template="/query/inputs/verifyDatasetInputs" model="[forNotRow: true, dataset: dataset, questionIdPrefix: fieldName, entity: (Entity) entity]"/>
        <g:set var="inlineStyle"
               value="${g.questionInlineStyle(question: question, answer: questionAnswer, showMandatory: showMandatory)}"/>
        <textarea${!inlineStyle ? ' rows=\"3\" cols=\"100\"' : ''} name="${fieldName}"
                                                                   onchange="removeBorder(this${question.inputWidth ? ',' + question.inputWidth : ''}${question.inputHeight ? ',' + question.inputHeight : ''});${isQuestionUsedForAdditionalJSCalculation ? 'updateHiddenField(this, \'' + question.questionId + '\');' : ''}"
                                                                   id="${divId}"
                                                                   class="input-xlarge span3 ${question.onlyNumeric ? 'numeric' : ''} ${dataset?.verified ? 'removeClicks' : ''} ${Dataset.VERIFIABLE_INPUT} ${dataset?.unlockedFromVerifiedStatus ? 'unlockedVerifiedDatasetStatusBg' : dataset?.verified ? 'verifiedDatasetBackground' : ''}" ${inlineStyle}${disabledAttribute} ${checkValue} ${question.irreversible && questionAnswer && !superUser ? "readonly" : ""}>${questionAnswer ? questionAnswer : question?.defaultValue}</textarea>
        <g:if test="${additionalQuestions}">
            <table style="margin-left: 10px; margin-bottom: 20px;">
            <tr>
        </g:if>
        <g:each in="${additionalQuestions}" var="additionalQuestion">
        <g:if test="${additionalQuestionAnswers}">
        <g:set var="additionalQuestionAnswer" value="${additionalQuestionAnswers.get(additionalQuestion.questionId)}" />
        </g:if>
        <g:render template="/query/additionalquestion" model="[entity:entity, query:query, section:section, inputType: inputType, mainSectionId:mainSectionId, additionalQuestion:additionalQuestion, additionalQuestionAnswer:additionalQuestionAnswer, mainQuestion:question, modifiable: modifiable, additionalQuestionInputWidth: additionalQuestionInputWidth]" />
        </g:each>
        <g:if test="${additionalQuestions}">
            </tr>
            </table>
        </g:if>
    </div>
    <g:if test="${showHistory}">
        <input type="button" ${disabledAttribute} class="primary btnRemove"
               style="margin-left: 30px; font-weight: bold;" value="${message(code: 'query.previous_answers')}"
               onclick="renderPreviousOperatingPeriods('${parentEntity?.id}', '${entity?.id}', '${indicator?.indicatorId}', '${query?.queryId}', '${mainSectionId}', '${question?.questionId}', '<g:replace value="${divId}History" source="." target="" />');"/>

        <div class="alert alert-gray" id="<g:replace value="${divId}History" source="." target=""/>"
             style="display: none; float: right; margin-left: 30px;">
            <button type="button" class="close"
                    onclick="$('#<g:replace value="${divId}History" source="." target="" />').hide();">
                ×
            </button>
        </div>
    </g:if>
</div>
</div>
</div>