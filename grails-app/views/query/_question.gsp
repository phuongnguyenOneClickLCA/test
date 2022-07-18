<div class="control-group queryquestion sectionbody${section?.sectionId}${hideQuestionByLCAModel ? " removeClicks" : ""}"
     style="${inputType && !expandable ? 'margin-bottom: 10px;' : '0'}${ifChosen ? ' margin-left: 20px;' : ''}${isSectionExpandable ? " display: none;" : ""}"
    ${question?.compatibleLcaModels ? "data-compatiblelcamodels=\"${question.compatibleLcaModels.join(",")}\"" : ""}>
    <g:if test="${inputType == 'text'}">
        <g:render template="/query/inputtext"/>
    </g:if>
    <g:elseif test="${inputType == 'textarea'}">
        <g:render template="/query/inputtextarea"/>
    </g:elseif>
    <g:elseif test="${inputType == 'slider'}">
        <g:render template="/query/inputslider"/>
    </g:elseif>
    <g:elseif test="${inputType == 'select'}">
        <g:render template="/query/inputselect"/>
    </g:elseif>
    <g:elseif test="${inputType == 'checkbox' || inputType == 'radio'}">
        <g:render template="/query/inputcheckboxradio"/>
    </g:elseif>
    <g:elseif test="${inputType == 'hidden'}">
        <input type="hidden" name="${fieldName}${question?.defaultResourceId ? '.' + question?.defaultResourceId : ''}"
               value="${question?.defaultValue}"/>
    </g:elseif>
    <g:elseif test="${inputType == 'file'}">
        <g:render template="/query/inputfile"/>
    </g:elseif>
    <g:elseif test="${'html'.equals(inputType)}">
        <g:render template="/query/inputhtml"/>
    </g:elseif>
    %{--Else branch is basically just for showing only a help as a question label (section with only one question--}%
    <g:else>
        <label>${question?.localizedQuestion}</label>
    </g:else>
</div>
