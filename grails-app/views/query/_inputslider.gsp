<%@ page import="com.bionova.optimi.core.domain.mongo.Entity; com.bionova.optimi.core.domain.mongo.Dataset" %>
<g:set var="questionService" bean="questionService" />

<g:set var="dataset"
       value="${questionService?.getDatasetForGsp(question?.questionId, query?.queryId, mainSectionId, null, null, entity, parentEntity, null) as Dataset}"/>
<g:set var="questionAnswer"
       value="${g.questionAnswer(entity: entity, parentEntity: parentEntity, queryId: query?.queryId, sectionId: mainSectionId, questionId: question?.questionId, entityClassResource: entityClassResource, inputType: 'text', dataset: dataset)}" />
<g:set var="value" value="${questionAnswer ? questionAnswer : question?.defaultValue}" />
<g:set var="parameters" value="${question?.sliderParameters}" />
<g:set var="sliderId" value="${mainSectionId}${question?.questionId}slider" />
<g:set var="textId" value="${mainSectionId}${question?.questionId}text" />
<div style="display: inline-block">
    <label style="display: inline-block">${question?.localizedQuestion}<g:if test="${!question?.optional}"> (<g:message code="mandatory" />)</g:if></label>
    %{-- << verificationPoint >> --}%
    <g:if test="${question?.verificationPoints}">
        <opt:verificationPointIcon question="${question}"/>
    </g:if>
</div>
<g:if test="${question?.localizedHelp}">
    <g:if test ="${question?.helpType?.equalsIgnoreCase('hoverover')}">
        <span class="icon-question-sign questionHelpHoverover" rel="popover" data-trigger="hover" data-content="${question?.localizedHelp}"></span><div>
    </g:if>
    <g:else>
        <div>
        <span class="help-block">${question?.localizedHelp}</span>
    </g:else>
</g:if>
<g:else><div></g:else>
<div class="input-append queryQuestionInput">
    <script>
        $(function() {
            $( "#${sliderId}" ).slider({
                value: ${value},
                min: ${parameters?.get("min")},
                max: ${parameters?.get("max")},
                step: ${parameters?.get("increment")},
                slide: function( event, ui ) {
                    $( "#${textId}" ).val( ui.value );
                }
            });
            $( "#${textId}" ).val( $( "#${sliderId}" ).slider( "value" ) );
        });
    </script>
    <g:render template="/query/icons/verifyDatasetIcons" model="[forNotRow: true, dataset: dataset, user: user]"/>
    <g:render template="/query/inputs/verifyDatasetInputs" model="[forNotRow: true, dataset: dataset, questionIdPrefix: fieldName, entity: (Entity) entity]"/>
    <span id="${divId}UnlockVerifiedDatasetLink">
      <g:render template="/query/links/unlockVerifiedDatasetLink" model="[forNotRow: true, unlockVerifiedDatasetLicensed: unlockVerifiedDatasetLicensed, dataset: dataset]"/>
    </span>
    <input type="text" ${g.questionInlineStyle(question: question, answer: questionAnswer,showMandatory: showMandatory)} name="${fieldName}" id="${textId}" ${disabledAttribute} class="slider" onchange="removeBorder(this);"${inputWidth}/>
    <div id="${sliderId}"></div>
    <g:each in="${additionalQuestions}" var="additionalQuestion">
        <g:if test="${additionalQuestionAnswers}">
            <g:set var="additionalQuestionAnswer" value="${additionalQuestionAnswers?.get(additionalQuestion.questionId)}" />
        </g:if>
        <g:render template="/query/additionalquestion" model="[entity:entity, query:query, section:section, mainSectionId:mainSectionId, additionalQuestion:additionalQuestion, additionalQuestionAnswer:additionalQuestionAnswer, mainQuestion:question, modifiable: modifiable, additionalQuestionInputWidth: additionalQuestionInputWidth]" />
    </g:each>
</div>
</div>