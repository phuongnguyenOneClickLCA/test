<g:if test="${additionalCalculationParams}">
    <g:each in="${additionalCalculationParams}" var="additionalQuestion">
        <g:hiddenField name="${mainQuestionId}_additionalParams" data-additionalQuestionId="${additionalQuestion.key}" value="${additionalQuestion.value}"/>
    </g:each>
</g:if>