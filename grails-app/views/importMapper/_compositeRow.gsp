<tr class="composite${originalDatasetId}" id="composite${dataset.manualId}">
    <td>
        <input type="hidden" name="questionId.${dataset.manualId}" value="${dataset.questionId}" />
        <input type="hidden" name="sectionId.${dataset.manualId}" value="${dataset.sectionId}" />
        <input type="hidden" name="composite.${dataset.manualId}" value="true" />
        <input type="hidden" name="userGivenUnit.${dataset.manualId}" value="${dataset.userGivenUnit}" />
        <g:set var="details">
            <g:if test="${dataset.importDisplayFields}">
                <g:each in="${dataset.importDisplayFields}">
                    <strong>${it.key}:</strong> ${it.value}<br />
                </g:each>
            </g:if>
        </g:set>
        <g:set var="idOfDetails" value="${UUID.randomUUID().toString()}" />
        <g:set var="detailsLink">${dataset.importDisplayFields?.get(detailsKey)}</g:set>
        <a href="javascript:;" rel="popover" data-trigger="hover" data-html="true" id="${idOfDetails}" data-content="${details}"><g:abbr value="${detailsLink}" maxLength="20" /></a>
    </td>
    <td>
        <div class="input-append">
            <input type="text" name="resourceId" class="autocomplete filterThisAutoComplete" data-unit="${dataset.userGivenUnit}" data-indicatorId="${indicatorId}" data-queryId="${dataset.queryId}" data-sectionId="${dataset.sectionId}" data-questionId="${dataset.questionId}"
               placeholder="${query?.localizedtypeaheadSearchText ? query.localizedtypeaheadSearchText : message(code: "typeahead.info")}" class="input-xlarge" autocomplete="off"
                onchange="removeBorder(this, '${inputWidth}');${isQuestionUsedForAdditionalJSCalculation ? 'updateHiddenField(this, \'' + dataset.questionId + '\');' : ''}" id="${selectId}" ${disabledAttribute}><a tabindex="-1" title="" class="add-on showAllResources" onclick="showAllImportMapperResources(this, '${resourceGroups}');"><i class="icon-chevron-down"></i></a>
            <input type="hidden" name="resourceId.${dataset.manualId}" id="resourceId${dataset.manualId}" />
        </div>
    </td>
    <td>
        <g:if test="${dataset.quantity == null}">
            <input type="text" value="" name="quantity.${dataset.manualId}" id="quantity${dataset.manualId}" class="numeric input-mini" />
        </g:if>
        <g:else>
            ${formatNumber(number:dataset.quantity, format:"###.##")}
            <input type="hidden" value="${dataset.quantity}" name="quantity.${dataset.manualId}" id="quantity${dataset.manualId}" />
        </g:else>
        <g:if test="${dataset.userGivenUnit != null}"><span class="add-on">${dataset.userGivenUnit}</span></g:if>
        <g:set var="thickness" value="${dataset.additionalQuestionAnswers?.get("thickness_mm") ? dataset.additionalQuestionAnswers.get("thickness_mm") : ''}" />
        ${thickness ? '/ ' + thickness + ' mm': ''}
    </td>
    <td>
        <input type="text" value="" name="shareOfTotal.${dataset.manualId}" class="numeric input-mini${!dataset.shareOfTotal ? ' redBorder ' : ''}" onchange="removeBorder(this);" />
    </td>
    <td>
        <input type="text" value="${dataset.additionalQuestionAnswers?.get("comment")}" name="comment.${dataset.manualId}" />
    </td>
    <td>
        <a href="javascript:;" onclick="removeElementById('composite${dataset.manualId}');"><strong>${message(code:'delete')}</strong></a>
    </td>
</tr>
