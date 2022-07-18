<g:if test="${zonesForRender}">
    <g:each in="${zonesForRender}" var="row">
        <p class="flexBaseline">
            <select class="zoneSelect rsetZonesForDesign${designId}" style="max-width: 150px; margin-top: 5px;"
                    name="zoneMapping.${questionId}.${row.batimentIndexAndDesignId}"
                    onchange="resolveZones(this);
                    enableInjectFromRsetButton('${questionId}', ${renderParcelleCheckbox})">
                <option></option>
                <g:each in="${row.rsetZones}" var="rsetZone">
                    <option data-value="${rsetZone.dataValue}" value="${rsetZone.value}"
                        ${rsetZone.isSelected ? "selected=\"selected\"" : ''}
                        ${rsetZone.isDisabled ? 'disabled' : ''}>
                        ${rsetZone.name}
                    </option>
                </g:each>
            </select>
            <i class="fas fa-caret-right fiveMarginHorizontal"></i>
            <span class="projectZonesForDesign${designId}">&nbsp;Zone ${row.zoneId}</span>
        </p>
    </g:each>
</g:if>
