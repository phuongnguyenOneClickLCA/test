<g:if test="${datasets && calculationResults}">
    <tr><td colspan="16"><strong>Resources with errors:</strong></td></tr>
    <tr><th style="width: 250px;">ResourceId</th><th style="width: 250px;">ProfileId</th><th>Base category result (${baseCategoryId} / ${calculationRuleId})</th><th>Comparison category result (${comparisonCategoryId} / ${calculationRuleId})</th><th>Share %</th></tr>
    <g:each in="${datasets}" var="dataset">

        <g:if test="${fixedBasedCategory!= null}">
            <g:set var="baseCategoryResult" value="${(Double) fixedBasedCategory}"/>
        </g:if>
        <g:else>
            <g:set var="baseCategoryResult" value="${(Double) calculationResults.findAll({ it.calculationRuleId.equals(calculationRuleId) && it.resultCategoryId.equals(baseCategoryId) })?.find({ it.calculationResultDatasets?.keySet()?.toList()?.contains(dataset.manualId) })?.calculationResultDatasets?.get(dataset.manualId)?.result}" />
        </g:else>

        <g:if test="${fixedComparisonCategory != null}">
            <g:set var="comparisonCategoryResult" value="${(Double) fixedComparisonCategory}"/>
        </g:if>
        <g:else>
            <g:set var="comparisonCategoryResult" value="${(Double) calculationResults.findAll({ it.calculationRuleId.equals(calculationRuleId) && it.resultCategoryId.equals(comparisonCategoryId) })?.find({ it.calculationResultDatasets?.keySet()?.toList()?.contains(dataset.manualId) })?.calculationResultDatasets?.get(dataset.manualId)?.result}" />
        </g:else>

        <g:if test="${baseCategoryResult!=null && comparisonCategoryResult!=null  && (comparisonCategoryResult > baseCategoryResult)}">
            <tr>
                <td style="width: 250px;">${dataset.resourceId} <a href="javascript:" onclick="window.open('${createLink(action: "showData", controller: "import", params: [resourceUUID: dataset.id])}', '_blank', 'width=1024, height=768, scrollbars=1');"><i class="fas fa-search-plus"></i></a></td>
                <td style="width: 250px;">${dataset.profileId}</td>
                <td>${baseCategoryResult < 1 ? baseCategoryResult : baseCategoryResult?.round(2)}</td>
                <td>${comparisonCategoryResult < 1 ? comparisonCategoryResult : comparisonCategoryResult?.round(2)}</td>
                <td><strong>${baseCategoryResult ? (comparisonCategoryResult / baseCategoryResult * 100).round(2) : ""}</strong></td>
            </tr>
        </g:if>
    </g:each>
</g:if>
