<%@ page import="com.bionova.optimi.core.domain.mongo.Entity; com.bionova.optimi.core.domain.mongo.Dataset" %>
<g:set var="questionService" bean="questionService" />

<g:set var="dataset"
       value="${questionService?.getDatasetForGsp(question?.questionId, query?.queryId, mainSectionId, null, null, entity, parentEntity, null) as Dataset}"/>
<g:set var="questionAnswers"
       value="${g.questionAnswer(entity: entity, parentEntity: parentEntity, queryId: query?.queryId, sectionId: mainSectionId, questionId: question?.questionId, entityClassResource: entityClassResource, inputType: 'checkboxRadio', dataset: dataset)}"/>
<div style="display: inline-block">
    <label style="display: inline-block">${question?.localizedQuestion}<g:if test="${!question?.optional}"> (<g:message code="mandatory" />)</g:if></label>
    %{-- << verificationPoint >> --}%
    <g:if test="${question?.verificationPoints}">
        <opt:verificationPointIcon question="${question}"/>
    </g:if>
    <g:render template="/query/icons/verifyDatasetIcons" model="[forNotRow: true, dataset: dataset, user: user]"/>
    <span id="${divId}UnlockVerifiedDatasetLink">
      <g:render template="/query/links/unlockVerifiedDatasetLink" model="[forNotRow: true]"/>
    </span>
</div>
<g:if test="${question?.localizedHelp}">
    <g:if test ="${question?.helpType?.equalsIgnoreCase('hoverover')}">
        <span class="icon-question-sign questionHelpHoverover" rel="popover" data-trigger="hover" data-content="${question?.localizedHelp}"></span></g:if>
    <g:else><span class="help-block">${question?.localizedHelp}</span></g:else>
</g:if>
<div class="queryQuestionInput ${dataset?.unlockedFromVerifiedStatus ? 'unlockedVerifiedDatasetStatusBg' : dataset?.verified ? 'verifiedDatasetBackground' : ''}">
    <g:render template="/query/inputs/verifyDatasetInputs" model="[forNotRow: true, dataset: dataset, questionIdPrefix: fieldName, entity: (Entity) entity]"/>
    <g:if test="${filteredResources}">
        <g:each in="${filteredResources}" var="resource">
            <g:set var="ifChosen" value="showHideDiv('${divId}.${resource.resourceId}');" />
            <g:if test="${questionAnswers?.contains(resource.resourceId)}">
                <g:set var="checked" value="checked='checked'" />
            </g:if>
            <g:elseif test="${resource.resourceId == question?.defaultValue && !datasetsFound}">
                <g:set var="checked" value="checked='checked'" />
            </g:elseif>
            <g:else>
                <g:set var="checked" value="" />
            </g:else>
            <label class="checkbox ${dataset?.verified ? 'removeClicks' : ''} ${Dataset.VERIFIABLE_INPUT}"><input type="${inputType}" name="${fieldName}" value="${resource.resourceId}" onclick="${ifChosen}" ${checked}${showMandatory && !questionAnswers && !optional ? ' class=\"unanswered\"' : ''} ${disabledAttribute} />&nbsp;${resource.localizedName} ${resource.unitForData ? '(' + resource.unitForData + ')' : ''}</label>
        </g:each>
    </g:if>
    <g:else>
        <g:if test="${question?.irreversible && questionAnswers && !superUser}">
            <g:each in="${question?.choices}" var="choice">
                <g:if test="${questionAnswers?.contains(choice.answerId)}">
                    <g:set var="checked" value="checked='checked'" />
                </g:if>
                <g:else>
                    <g:set var="checked" value="" />
                </g:else>
                <label class="checkbox ${dataset?.verified ? 'removeClicks' : ''} ${Dataset.VERIFIABLE_INPUT}"><input type="${inputType}" style="opacity:0.5;" onclick="return false;" readonly name="${fieldName}" value="${choice.answerId}" ${checked}${showMandatory && !questionAnswers && !optional ? ' class=\"unanswered\"' : ''} ${disabledAttribute} />&nbsp;${choice.localizedAnswer}</label>
                <g:if test="${choice.localizedHelp}"><span class="help-block">${choice.localizedHelp}</span></g:if>
            </g:each>
        </g:if>
        <g:else>
            <g:each in="${question?.choices}" var="choice">
                <g:if test="${choice.ifChosen || choice.ifChosenSectionIds}">
                    <g:set var="ifChosen" value="showHideDiv('${divId}.${choice.answerId}');" />
                </g:if>
                <g:if test="${questionAnswers?.contains(choice.answerId)}">
                    <g:set var="checked" value="checked='checked'" />
                </g:if>
                <g:elseif test="${choice.answerId == question?.defaultValue && !datasetsFound}">
                    <g:set var="checked" value="checked='checked'" />
                </g:elseif>
                <g:else>
                    <g:set var="checked" value="" />
                </g:else>
                <label class="checkbox ${dataset?.verified ? 'removeClicks' : ''} ${Dataset.VERIFIABLE_INPUT}"><input type="${inputType}" name="${fieldName}" value="${choice.answerId}" onclick="${ifChosen}" ${checked}${showMandatory && !questionAnswers && !optional ? ' class=\"unanswered\"' : ''} ${disabledAttribute} />&nbsp;${choice.localizedAnswer}</label>
                <g:if test="${choice.localizedHelp}"><span class="help-block">${choice.localizedHelp}</span></g:if>
            </g:each>
        </g:else>
    </g:else>
    <g:each in="${additionalQuestions}" var="additionalQuestion">
        <g:if test="${additionalQuestionAnswers}">
            <g:set var="additionalQuestionAnswer" value="${additionalQuestionAnswers.get(additionalQuestion.questionId)}" />
        </g:if>
        <g:render template="/query/additionalquestion" model="[entity:entity, query:query, section:section, mainSectionId:mainSectionId, additionalQuestion:additionalQuestion, additionalQuestionAnswer: additionalQuestionAnswer, mainQuestion:question, modifiable: modifiable, additionalQuestionInputWidth: additionalQuestionInputWidth]" />
    </g:each>
</div>
<g:each in="${question?.choices}" var="choice" status="index">
    <g:set var="display">
        <g:if test="${printable}">
            block
        </g:if>
        <g:else>
            <g:blockOrNone entity="${entity}" queryId="${query?.queryId}" sectionId="${mainSectionId}" questionId="${question?.questionId}" answerId="${choice.answerId}" />
        </g:else>
    </g:set>
    <g:set var="choiceDivId" value="${divId}.${choice.answerId}" />
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