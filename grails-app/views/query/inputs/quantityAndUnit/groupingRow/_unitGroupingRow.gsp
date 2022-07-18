<g:if test="${allowEditingConstituentQuantity}">
    <span class="fakeLink groupClickQNU"
          onclick="changeLinkToInputGroupingRow(this, '.userGivenUnitContainer', '.groupClickQNU')">
        <g:if test="${dataset?.userGivenUnit}">
            <queryRender:displayWithSubAndSuperScript unit="${dataset?.userGivenUnit}"/>
        </g:if>
        <g:else>
            ${message(code: 'query.question.expand')}
        </g:else>
    </span>
    <span class="hide userGivenUnitContainer">
        <g:render template="/query/usergivenunit"
                  model="[multipleResources: true, useUserGivenUnit: useUserGivenUnit, groupingRow: true,
                          datasetId        : dataset?.manualId, unitFieldName: unitFieldName, userGivenUnit: dataset?.userGivenUnit,
                          dataset          : dataset, resource: resource]"/>
    </span>
</g:if>
<g:else>
    <queryRender:displayWithSubAndSuperScript unit="${dataset?.userGivenUnit}"/>
    <input type="hidden" name="${unitFieldName}" value="${dataset?.userGivenUnit}"/>
</g:else>
