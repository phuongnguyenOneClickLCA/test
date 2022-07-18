<input type="text"
       data-parentConstructionId="${parentConstructionId}"
       data-splitRowId="${splitRowId ? splitRowId : ""}"
       data-isHiddenTextInput="${isConstructionConstituent}"
    <g:if test="${!isParentConstructionResource}">
        data-isUnlinkedFromParentConstruction="${dataset?.unlinkedFromParentConstruction ?: false}"
        data-uniqueconstructionidentifier="${uniqueConstructionIdentifier}"
        data-constructionquantity="${constructionValue}"
    </g:if>
    <g:if test="${!isConstructionConstituent && isParentConstructionResource}">
        <g:if test="${newResourceRowAdded}">
            readonly
            data-parentuniqueconstructionidentifier="${uniqueConstructionIdentifier}"
        </g:if>
        data-isParentConstruction="true" oninput="multiplyConstructionResources('${uniqueConstructionIdentifier}', this, '${unitFieldId}');"
        <g:if test="${resource?.nmdUnitConversionFactor}">
            data-constructionSourceUnitConversionFactor="${resource?.unitForData}"
            data-constructionUnitConversionFactor="${resource?.nmdUnitConversionFactor}"
        </g:if>
    </g:if>
       onchange="$(this).parent().updateSortVal(this.value);<g:if test="${groupingRow}">updateParallelInputInQuestion(this);</g:if>"
       value="${showImperial && dataset?.imperialQuantity ? dataset?.imperialQuantity : (qtyForDataLoadFromFile?: quantityAnswer)}" ${useResourceUnitCost && defaultResourceUnitCostFromResource != null ? ' data-defaultUnitCost="' + defaultResourceUnitCostFromResource + '"' : ""}${resourceTypeCostMultiplier ? ' data-resourceTypeCostMultiplier="' + resourceTypeCostMultiplier + '"' : ""}
       data-defaultDensity="${resource?.density}"
       data-costConstruction="${resource?.costConstruction_EUR}"
       data-defaultThickness="${resource?.defaultThickness_mm}"
       data-defaultThickness_in="${resource?.defaultThickness_in ?: resource?.defaultThickness_mm ? resource?.defaultThickness_mm / 25.4 : ""}"
       data-allowVariableThickness="${resource?.allowVariableThickness}"
       data-massconversionfactor="${resource?.massConversionFactor}"
       data-unitForData="${resource?.unitForData}"
       data-resourceSubType="${resource?.resourceSubType}"
       data-datasetId="${datasetId}" rel="popover"
       data-trigger="focus"
       data-html="true"
       data-content="${message(code: 'query.quantity_type.warning')}"
       data-resourceId="${resource?.resourceId}"
       name="${splitPage ? "splittedOrChangedQuantity${resource?.resourceId}" : "${fieldName}.${resource?.resourceId}"}"
       style="${quantityMissing}" onfocus="removeBorder(this);"
       class="numeric input-mini ${(isConstructionConstituent && allowEditingConstituentQuantity) || groupingRow ? 'hide' : ''} ${groupingRow ? 'inGroupingRow alignRight' : ''} ${isQuantity ? 'isQuantity' : ''}${dataset?.locked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"}" ${checkValue} ${quarterlyInputEnabled || monthlyInputEnabled || disabled || preventChanges || (isConstructionConstituent && !allowEditingConstituentQuantity) ? 'readonly' : ''}/>

<g:if test="${isConstructionConstituent && allowEditingConstituentQuantity}">
    <input type="hidden" name="${fieldName}_isUnlinkedFromParentConstruction_.${resource?.resourceId}"
           class="isUnlinkedFromParentConstructionInput"
           value="${dataset?.unlinkedFromParentConstruction ?: false}"/>
</g:if>