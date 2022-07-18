
    <g:if test="${originalDataset && drawOriginalDataset}">
        <div id="originalDataContainer" style="border:1px solid gray; margin-top:5px; border-radius:5px; padding:3px; max-width:60%;">

        <span class="bold">Original material </span>
            <br>
        <table id="originResource" class="query_resource">

        <span>${originalDataset?.resource?.uiLabel}</span>
        <thead>
        <tr id="originTableheader">
            <th>Share %</th>
            <g:if test="${quantity && (!indicator || !indicator.requireMonthly)}">
                <th ${question.isThicknessQuestion ? "colspan='3'" : ""} data-sort="int" ><g:message code="query.quantity" /></th>
            </g:if>
            <g:if test="${additionalQuestions}">
                <g:each in="${additionalQuestions}" var="additionalQuestion">
                    <g:if test="${additionalQuestion?.isThicknessQuestion && !additionalQuestion?.hideValueInQuery && !'hidden'.equals(additionalQuestion?.inputType) && (!additionalQuestion?.licenseKey || additionalQuestion?.isQuestionLicensed(parentEntity ? parentEntity : entity))}">
                        <th ${additionalQuestion?.dataSort ? "data-sort='${additionalQuestion.dataSort}'" : "class=\"no-sort\""}>${additionalQuestion.questionId == 'thickness_mm' || additionalQuestion.questionId == 'thickness_in' ? "&nbsp;"  : additionalQuestion.localizedQuestion}</th>
                    </g:if>
                </g:each>
            </g:if>

        </tr>
        </thead>
        <queryRender:renderResourceRow dataset="${originalDataset}" resource="${resource}" hideAllAnsweredQuestions="${Boolean.TRUE}"
                                       splitRowId="${originalDataset?.manualId}splitOriginal"
                                       splitPage="${Boolean.TRUE}" splitViewAllowed="${Boolean.FALSE}"
                                       showGWP="${showGWP}" unitSystem="${user?.unitSystem}"
                                       multipleChoicesAllowed="${true}" fieldName="${fieldName}"
                                       indicator="${indicator}" resourceTableId="changeTable"
                                       parentEntity="${parentEntity}" entity="${entity}" />
        </table>
        </div>
    </g:if>

    <g:set var="random" value="${UUID.randomUUID().toString()}"/>


    <div id="splitDataContainer${random}" data-splitmaterial="1" style="border:1px solid red; margin-top:5px; border-radius:5px; padding:3px; max-width:60%;">
        <i class="fa fa-times close fa-2x" onclick="removeSplitMaterial($(this).parent());" aria-hidden="true"></i>

        <span class="bold">Split material <span class="numberOfSplitMaterial"></span> </span>
        <br>
        <div class="input-append" id="resourceBox${question?.questionId}${UUID.randomUUID().toString()}">
            <g:set var="selectId"  value="changeTableSelect${UUID.randomUUID().toString()}" />
            <input type="text" name="resourceId"  id="${selectId}" class="autocomplete ${dynamicAdd ? 'splitCompleteDynamic' : 'splitComplete'} resourceSelect"
                   autocomplete="off" data-original-title="" placeholder="${query?.localizedtypeaheadSearchText ? query.localizedtypeaheadSearchText : message(code: "typeahead.info")}"
                   data-splitDataset="true" data-unit="${originalDataset?.userGivenUnit}"
                   data-parentEntityId="${parentEntity?.id}" data-indicatorId="${indicator?.indicatorId}"
                   data-queryId="${query?.queryId}" data-sectionId="${sectionId}" data-entityId="${entity?.id}" data-resourceId="${originalDataset?.resourceId}" data-selectId="${selectId}" data-resourceTableId="splitTable${random}" data-fieldName="${fieldName}" data-preventDoubleEntries="${Boolean.TRUE}" data-doubleEntryWarning="${g.message(code:'query.question.prevent_double_entries')}" data-showGWP="${showGWP}"
                   data-accountId="${accountId}" data-resourceType="${originalDataset?.resource?.resourceType}" data-subType="${originalDataset?.resource?.resourceSubType}" data-originalQuantity="${originalDataset?.originalAnswer?.replaceAll(",", ".")?.trim()?.isNumber() ? originalDataset.originalAnswer.replaceAll(",", ".").trim().toDouble() : originalDataset?.quantity}" data-originalUnit="${originalDataset?.userGivenUnit}" data-originalId="${originalDataset?.manualId}" data-questionId="${question?.questionId}"><a tabindex="-1" id="${selectId}showAll" class="add-on showAllResources " ${disabledAttribute ? "" : "onclick=\"showAllQueryResources(this,'${question?.questionId}','${message(code: 'query.resource.popover')}', 'split');\""} ><i class="icon-chevron-down"></i></a><a href="javascript:" class="dataHitsButton add-on hidden" id="${question?.questionId}${UUID.randomUUID().toString()}dataHits"></a>
        </div>
         <table id="splitTable${random}" data-splitRowId="${random}split" class="splitTable query_resource">
                <thead>
                <tr id="splitTable${random}header" style="display:none;">
                    <th data-content="${message(code: 'unit_missmatch_split')}" class="shareHeader">Share %</th>
                    <g:if test="${quantity && (!indicator || !indicator.requireMonthly)}">
                        <th ${question.isThicknessQuestion ? "colspan='3'" : ""} data-sort="int" ><g:message code="query.quantity" /></th>
                    </g:if>
                    <g:if test="${additionalQuestions}">
                        <g:each in="${additionalQuestions}" var="additionalQuestion">
                            <g:if test="${additionalQuestion?.isThicknessQuestion && !additionalQuestion?.hideValueInQuery && !'hidden'.equals(additionalQuestion?.inputType) && (!additionalQuestion?.licenseKey || additionalQuestion?.isQuestionLicensed(parentEntity ? parentEntity : entity))}">
                                <th ${additionalQuestion?.dataSort ? "data-sort='${additionalQuestion.dataSort}'"  : "class=\"no-sort\""}>${additionalQuestion.questionId == 'thickness_mm' || additionalQuestion.questionId == 'thickness_in' ? "&nbsp;"  : additionalQuestion.localizedQuestion}</th>
                            </g:if>
                        </g:each>
                    </g:if>

                </tr>
                </thead>
            </table>
    </div>

