<g:if test="${allowEditingConstituentQuantity}">
    <span class="fakeLink groupClickQNU" onclick="changeLinkToInputGroupingRow(this, '.isQuantity', '.groupClickQNU')">
        ${quantityAnswer ?: message(code: 'query.question.expand')}
    </span>
    <g:render template="/query/inputs/quantityAndUnit/resource/quantityInput"
              model="[dataset       : dataset, groupingRow: true, allowEditingConstituentQuantity: allowEditingConstituentQuantity,
                      quantityAnswer: quantityAnswer,
                      resource      : resource, datasetId: dataset?.manualId, fieldName: fieldName,
                      isQuantity    : true]"/>
</g:if>
<g:else>
    <span>${quantityAnswer}</span>
</g:else>

