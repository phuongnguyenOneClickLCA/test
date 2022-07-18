<g:set var="questionService" bean="questionService"/>
<g:if test="${question && question.questionId && question.inputType == "select" && question.choices}">
    <label class="control-label fiveMarginVertical">
        ${question?.localizedQuestion}
        <g:if test="${question.help}">
            <a href="#" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${questionService.getLocalizedHelp(question)}">
                <i class="icon-question-sign"></i>
            </a>
        </g:if>
    </label>

    <g:select name="${question.questionId}" class="${question.questionId}" from="${question?.choices ?: [:]}" value="${defaultValue}" optionKey="${{it?.answerId}}" optionValue="${{it?.localizedAnswer}}" disabled="${disabled}"/>
</g:if>



