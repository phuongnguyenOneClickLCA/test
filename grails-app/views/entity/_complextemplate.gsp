<table class="table table-striped table-condensed table-cellvaligntop nowrap wrapth" id="resultTable">
    <tbody>

    <g:if test="${!hideBreakdown && !hideTotals}">
        <tr>
            <g:if test="${!indicator?.hideResultCategoryLabels}">
                <td>&nbsp;</td>
            </g:if>
            <td class="clarification">&nbsp;</td>
            <g:if test="${!hideClarificationText}">
                <td class="clarification">&nbsp;</td>
            </g:if>
            <td class="monthly-explanation" colspan="13"><%--
        --%><g:if test="${"FI" == session?.urlLang}"><%--
            --%>* Kuukausitason yhteistuloksiin lasketaan vain kuukausitasolla annetut tiedot. Tämän vuoksi niiden summa voi poiketa koko vuoden tuloksesta.<%--
        --%></g:if><%--
        --%><g:else><%--
           --%>* Monthly totals only consider the data provided on a monthly basis. Because of this, their total may differ from the total result for the whole year.<%--
        --%></g:else><%--
        --%></td>
            <td class="quarterly-explanation" colspan="5"><%--
        --%><g:if test="${"FI" == session?.urlLang}"><%--
            --%>* Vuosineljännetason yhteistuloksiin lasketaan vain vuosineljänneksittäin annetut tiedot. Tämän vuoksi niiden summa voi poiketa koko vuoden tuloksesta.<%--
        --%></g:if><%--
        --%><g:else><%--
           --%>* Quarterly totals only consider the data provided on a quarter basis. Because of this, their total may differ from the total result for the whole year.<%--
        --%></g:else><%--
        --%></td>
            <g:each in="${calculationRules}" var="calculationRule" status="index">
                <td class="text-right"></td>
            </g:each>
            <td colspan="3">&nbsp;</td>
        </tr>
    </g:if>
   <%-- TODO DEPRICATE THIS, WHEN THE NEW LCC IS USED EVERYWHERE --%>
    <g:if test="${indicator?.showDiscountRates}">
        <tr>
            <g:if test="${!indicator?.hideResultCategoryLabels}">
                <td>&nbsp;</td>
            </g:if>
            <td><g:discountRateLabels indicator="${indicator}"/></td>
            <td class="clarification">&nbsp;</td>
            <g:each in="[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]">
                <th class="monthly">&nbsp;</th>
            </g:each>
            <g:each in="['Q1', 'Q2', 'Q3', 'Q4']">
                <th class="quarterly">&nbsp;</th>
            </g:each>
            <g:each in="${calculationRules}" var="calculationRule">
                <td class="text-right"><g:discountRates calculationRule="${calculationRule}" entity="${entity}" indicator="${indicator}"/></td>
            </g:each>
             <td colspan="3"></td>
        </tr>
    </g:if>

    <g:if test="${denominatorAnswers || dynamicDenomAnswersByDatasets}">
        <tr class="clarification">
            <g:if test="${!indicator?.hideResultCategoryLabels}">
                <th>&nbsp;</th>
            </g:if>
            <th><g:message code="results.denominators" /></th>
            <g:if test="${!hideClarificationText}">
                <th></th>
            </g:if>
            <g:each in="${calculationRules}" var="calculationRule">
                <th class="text-right"></th>
            </g:each>
            <th colspan="3"></th>
        </tr>
        <g:each in="${denominatorAnswers}" var="answer">
            <tr class="clarification">
                <g:set var="index" value="${0}" />
                <g:each in="${calculationRules}" var="calculationRule">
                    <g:each in="${answersByRule?.get(answer.key)}" var="answerByRule">
                        <g:if test="${index == 0}">
                            <g:if test="${!indicator?.hideResultCategoryLabels}">
                                <td>&nbsp;</td>
                            </g:if>
                            <td>${answer.key}</td>
                            <g:if test="${!hideClarificationText}">
                                <td>${answerByRule.value[0]}</td>
                            </g:if>
                        </g:if>
                        <td class="text-right">${answerByRule.value[1]}</td>
                    </g:each>
                    <g:set var="index" value="${index + 1}" />
                </g:each>
                 <td colspan="3"></td>
            </tr>
        </g:each>

        <g:each in="${dynamicDenomAnswersByDatasets}" var="answerByDatasetAndRules">
            <g:if test="${answerByDatasetAndRules}">
            <tr class="clarification">
                <g:if test="${!indicator?.hideResultCategoryLabels}">
                    <td>&nbsp;</td>
                </g:if>
                <td>${answerByDatasetAndRules.key.resource?.localizedName}</td>
                <td>
                    <g:if test="${answerByDatasetAndRules.key.answerIds && !answerByDatasetAndRules.key.answerIds?.isEmpty()}">
                        ${answerByDatasetAndRules.key.answerIds[0]} ${answerByDatasetAndRules.key.resource?.unitForData}
                    </g:if>
                </td>
                <g:each in="${calculationRules}" var="calculationRule">
                    <td class="text-right">
                        ${answerByDatasetAndRules.value?.get(calculationRule)}
                    </td>
                </g:each>
                <td colspan="3"></td>
            </tr>
            </g:if>
        </g:each>
    </g:if>


    </tbody>
</table>

<div class="modal hide fade" style="width: 700px;" id="modalDialog${idForGraphs}">
    <div class="modal-header"><button type="button" class="close" data-dismiss="modal">&times;</button>
        <h2 id="chartTitle${idForGraphs}"></h2>
    </div>
    <div class="well" id="chartArea${idForGraphs}">
        <div class="row"><div class="pie_chart" id="the_chart${idForGraphs}"></div> <div id="legend${idForGraphs}"></div></div>
        <div class="pie_chart" id="the_chart_fake${idForGraphs}" style="display:none"></div>
    </div>
</div>

<g:if test="${additionalInfos}">
    <div class="accessibility_lower">
      <p><strong><g:message code="results.additional_info" /></strong></p>
    <p>
    <ul>
        <g:each in="${additionalInfos}" var="additionalInfo">
            <li>${additionalInfo}</li>
        </g:each>
    </ul>
    </p>
  </div>
</g:if>

<%--
<g:renderSourceListing entity="${entity}" indicator="${indicator}" />
--%>




