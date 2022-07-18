<g:set var="unitFieldName"
       value="${splitPage ? "splittedOrChangedUnit${resourceId}" : "${sectionId}.${questionId}_unit_${resourceId ? '.' + resourceId : ''}"}"/>
<span class="hidden">${quantityAnswer ? quantityAnswer : 0}</span>

<g:if test="${isConstructionConstituent && allowEditingConstituentQuantity}">
    %{-- GENERATE A LINK WITH HIDDEN TEXT INPUT AND HIDDEN SELECT UNIT --}%
    <span class="fakeLink ${quantity ? 'isQuantityLinkConstituent' : ''} ${dataset?.locked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"}"
          onclick="changeConstituentQuantityLinkToTextField(this, ${dataset?.unlinkedFromParentConstruction ?: false} , '${message(code: 'query.unlinkQuantity.warning')}', '${message(code: "ok")}', '${message(code: "cancel")}');">
        <span class="fakeMiniTextInput quantityTextConstituent">
            <g:if test="${dataset?.unlinkedFromParentConstruction}">
                <span rel="popover" data-trigger="hover" data-html="true" data-content="${message(code: 'query.unlinkedQuantity')}">
                    <i class="fas fa-unlink"></i>
                </span>
            </g:if>
            ${showImperial && dataset?.imperialQuantity ? dataset?.imperialQuantity : quantityAnswer}
        </span>
        <queryRender:displayWithSubAndSuperScript unit="${constructionUnit}"/>
    </span>
</g:if>

%{-- QUANTITY --}%
<g:render template="/query/inputs/quantityAndUnit/resource/quantityInput"
          model="[isConstructionConstituent : isConstructionConstituent, parentConstructionId: parentConstructionId, allowEditingConstituentQuantity: allowEditingConstituentQuantity,
                  splitRowId                : splitRowId, isParentConstructionResource: isParentConstructionResource, uniqueConstructionIdentifier: uniqueConstructionIdentifier,
                  constructionValue         : constructionValue, newResourceRowAdded: newResourceRowAdded, showImperial: showImperial, dataset: dataset,
                  quantityAnswer            : quantityAnswer, useResourceUnitCost: useResourceUnitCost, defaultResourceUnitCostFromResource: defaultResourceUnitCostFromResource,
                  resourceTypeCostMultiplier: resourceTypeCostMultiplier, resource: resource, splitPage: splitPage, datasetId: datasetId, fieldName: fieldName,
                  isQuantity                : quantity, checkValue: checkValue, quarterlyInputEnabled: quarterlyInputEnabled, monthlyInputEnabled: monthlyInputEnabled, disabled: disabled,
                  preventChanges            : preventChanges, unitFieldId: unitFieldId, qtyForDataLoadFromFile: qtyForDataLoadFromFile]"/>

%{-- UNIT --}%
<span class="add-on userGivenUnitContainer ${isConstructionConstituent && allowEditingConstituentQuantity ? 'hide' : ''}">
    <g:set var="allowMultiUnitsConstruction"
           value="${constructionRender.allowMultipleUnitsInConstruction(resource: resource)}"/>
    <g:if test="${(isConstructionConstituent && !allowEditingConstituentQuantity) || (isParentConstructionResource && !allowMultiUnitsConstruction)}">
        <queryRender:displayWithSubAndSuperScript unit="${constructionUnit}"/>
        <input type="hidden" name="${unitFieldName}" id="${unitFieldId}" value="${constructionUnit}"/>
    </g:if>
    <g:else>
        <g:render template="/query/usergivenunit"
                  model="[multipleResources           : true, useUserGivenUnit: useUserGivenUnit, constructionUnit: constructionUnit,
                          datasetId                   : datasetId, preventChanges: preventChanges, disabled: disabled,
                          isParentConstructionResource: isParentConstructionResource, unitFieldName: unitFieldName, unitFieldId: unitFieldId,
                          random                      : random, showImperial: showImperial, dataset: dataset]"/>
    </g:else>
</span>
