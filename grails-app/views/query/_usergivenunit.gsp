<g:if test="${singleResource}">
    <g:each in="${useUserGivenUnit}" var="use"><span class="add-on unitContainer"><%--
      --%><g:if test="${use.key}"><%--
          --%><g:if test="${use.value.size() > 1}"><%--
              --%><select ${disabled ? 'disabled=\"disabled\"' : ''} style="width: 55px;" name="${mainSectionId}.${questionId}_unit_${resource?.resourceId ? '.' + resource.resourceId : ''}" onchange="unitChangeWarning();" class="userGivenUnit"><%--
                  --%><g:each in="${use.value}" var="unit"><%--
                      --%><option value="${unit}"${unit.equals(userGivenUnit) ? ' selected=\"selected\"' : ''}>${unit}</option><%--
                  --%></g:each><%--
              --%></select><%--
              --%><g:if test="${disabled}"><%--
                --%><input type="hidden" name="${mainSectionId}.${questionId}_unit_${resource?.resourceId ? '.' + resource.resourceId : ''}" value="${userGivenUnit}" /><%--
              --%></g:if><%--
          --%></g:if><%--
          --%><g:else><%--
              --%><input type="hidden" name="${mainSectionId}.${questionId}_unit_${resource?.resourceId ? '.' + resource.resourceId : ''}" value="${use.value[0]}" />${use.value[0]}<%--
          --%></g:else><%--
      --%></g:if><%--
      --%><g:else>${use.value.size() > 0 ? use.value[0] : ''}</g:else></span><%--
    --%></g:each>
</g:if>

<g:if test="${multipleResources}">
    <g:each in="${useUserGivenUnit}" var="use">
        <g:set var="unitToShow" value="${use.value[0]}"/>
        <g:if test="${use.key}">
            <g:if test="${use.value.size() > 1}">
                <select data-datasetId="${datasetId}" ${preventChanges || disabled ? 'disabled=\"disabled\"' : ''}
                        name="${unitFieldName}" id="${unitFieldId}"
                        onfocus="storePreviousUnitForRow(this)"
                        onchange="toggleThickness(this, ${resource?.allowVariableThickness ? "'true'" : "'false'"});
                        <g:if test="${groupingRow}">
                        updateParallelInputInQuestion(this);
                        </g:if>
                        convertQuantity(this, '${resource?.resourceId ? "${resource.resourceId}" : ''}', '${changeView}', '${dataset?.calculatedMass}', '${dataset?.quantity}', '${dataset?.userGivenUnit}');"
                        class="userGivenUnit${dataset?.locked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"} ${groupingRow ? 'inGroupingRow' : ''}">
                    <g:each in="${use.value}" var="unit">
                        <option value="${unit}" ${showImperial && dataset?.imperialUnit ?
                                dataset.imperialUnit.equals(unit) ? ' selected=\"selected\"' : '' :
                                (originalUnit && unit.toString().equalsIgnoreCase(originalUnit)) || unit.toString().equalsIgnoreCase(userGivenUnit?: constructionUnit) ? ' selected=\"selected\"' : ''}>
                            ${unit}
                        </option>
                    </g:each>
                </select>
                <g:if test="${preventChanges || disabled}">
                    <input type="hidden" name="${unitFieldName}"
                           value="${userGivenUnit ?: constructionUnit}"/>
                </g:if>
            </g:if>
            <g:else>
                <g:set var="unit"
                       value="${isParentConstructionResource ? constructionUnit : use.value.size() > 0 ? use.value[0] : ''}"/>
                <input type="hidden" name="${unitFieldName}" value="${unit}" id="${unitFieldId}"/>
                <queryRender:displayWithSubAndSuperScript unit="${unit}"/>
            </g:else>
        </g:if>
        <g:else>
            <g:set var="unit"
                   value="${isParentConstructionResource ? constructionUnit : use.value.size() > 0 ? use.value[0] : ''}"/>
            <input type="hidden" name="${unitFieldName}" value="${unit}" id="${unitFieldId}"/>
            <queryRender:displayWithSubAndSuperScript unit="${unit}"/>
        </g:else>
    </g:each>
</g:if>
