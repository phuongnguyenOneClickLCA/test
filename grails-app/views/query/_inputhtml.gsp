<g:set var="questionAnswer" value="${g.questionAnswer(entity:entity, parentEntity: parentEntity, queryId:query?.queryId, sectionId:mainSectionId, questionId:question?.questionId, entityClassResource : entityClassResource ,inputType:'textarea')}" />
<div style="display: inline-block">
    <label style="display: inline-block">${question?.localizedQuestion}<g:if test="${!question?.optional}"> (<g:message code="mandatory" />)</g:if></label>
    %{-- << verificationPoint >> --}%
    <g:if test="${question?.verificationPoints}">
        <opt:verificationPointIcon question="${question}"/>
    </g:if>
</div>
<g:if test="${question?.localizedHelp}">
    <g:if test ="${question?.helpType?.equalsIgnoreCase('hoverover')}">
        <span class="icon-question-sign questionHelpHoverover" rel="popover" data-trigger="hover" data-content="${question?.localizedHelp}"></span><div></g:if>
    <g:else><div><span class="help-block"${questionHelpWidth? ' style=\"width:' + questionHelpWidth + '%;\"' : ''}>${question?.localizedHelp}</span></g:else>
</g:if><g:else><div></g:else>
<div class="input-append">
    <textarea rows="3" cols="100" name="${fieldName}" onchange="removeBorder(this);" id="${divId}" class="htmlEditor input-xlarge span3" ${disabledAttribute} ${g.questionInlineStyle(question: question, answer: questionAnswer,showMandatory: showMandatory)} ${checkValue}>${questionAnswer ? questionAnswer : question?.defaultValue}</textarea>
    <g:if test="${additionalQuestions}"><table style="margin-left: 10px; margin-bottom: 20px;"><tr></g:if>
    <g:each in="${additionalQuestions}" var="additionalQuestion">
    <g:if test="${additionalQuestionAnswers}">
    <g:set var="additionalQuestionAnswer" value="${additionalQuestionAnswers.get(additionalQuestion.questionId)}" />
    </g:if>
    <g:render template="/query/additionalquestion" model="[entity:entity, query:query, section:section, inputType: inputType, mainSectionId:mainSectionId, additionalQuestion:additionalQuestion, additionalQuestionAnswer:additionalQuestionAnswer, mainQuestion:question, modifiable: modifiable, additionalQuestionInputWidth: additionalQuestionInputWidth]" />
    </g:each>
    <g:if test="${additionalQuestions}"></tr></table></g:if>
    <g:if test="${showHistory}"><br />
        <input type="button" ${disabledAttribute} class="primary btnRemove" style="margin-left: 30px;  font-weight: bold;" value="${message(code: 'query.previous_answers')}"
               onclick="renderPreviousOperatingPeriods('${parentEntity?.id}', '${entity?.id}', '${indicator?.indicatorId}', '${query?.queryId}', '${mainSectionId}', '${question?.questionId}', '<g:replace value="${divId}History" source="." target="" />');" />
        <div class="alert alert-gray" id="<g:replace value="${divId}History" source="." target="" />" style="display: none; float: right; margin-left: 30px;">
            <button type="button" class="close" onclick="$('#<g:replace value="${divId}History" source="." target="" />').hide();">×</button>
        </div>
    </g:if>
</div>
</div>