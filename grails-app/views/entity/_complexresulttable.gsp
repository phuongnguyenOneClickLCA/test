<g:set var="doColspan" value="${true}" />
<table class="nowrap wrapth table table-striped table-condensed table-cellvaligntop" id="resultTable">
    <calc:renderResultHeadingObject indicator="${indicator}" calculationRules="${calculationRules}" useShortNames="${useShortNames}" resultCategories="${resultCategories}"  entity="${childEntity}"/>
   <tbody id="resultRowsBody${reportItemId}">
<calc:renderResultRows entity="${childEntity}" indicator="${indicator}" calculationRules="${calculationRules}" resultCategories="${resultCategories}"/>
  </tbody>
    <tbody id="totalResultsTbody${reportItemId}">

    <g:if test="${indicator.showAverageInsteadOfTotal}">
    <calc:renderAverageResultRows entity="${childEntity}" indicator="${indicator}" calculationRules="${calculationRules}" resultCategories="${resultCategories}" hideClarification="${hideClarificationText}" />
</g:if>
<g:else>
    <calc:renderTotalResultRows entity="${childEntity}" indicator="${indicator}" calculationRules="${calculationRules}" resultCategories="${resultCategories}" hideClarification="${hideClarificationText}"  />
</g:else>
    </tbody>
    <tbody>
<calc:renderResultPerDenominator indicator="${indicator}" calculationRules="${calculationRules}" entity="${childEntity}" denominators="${indicator?.denominatorListAsDenominatorObjects}" colSpan="${colSpan}" />

  <g:if test="${!hideBreakdown && !hideTotals}">
      <tr>
            <g:if test="${hideclarificationText}">
                <td>&nbsp;</td>
            </g:if>
            <td class="graphColumnSpace" colspan="2"></td>
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
          <g:if test="${doColspan}">
            <td colspan="3"></td>
          </g:if>
      </tr>
  </g:if>
  <g:if test="${indicator?.showDiscountRates}">
      <tr>
        <g:if test="${!indicator?.hideResultCategoryLabels}">
            <td>&nbsp;</td>
        </g:if>
        <td><g:discountRateLabels indicator="${indicator}"/></td>
        <g:each in="[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]">
            <th class="monthly">&nbsp;</th>
        </g:each>
        <g:each in="['Q1', 'Q2', 'Q3', 'Q4']">
            <th class="quarterly">&nbsp;</th>
        </g:each>
        <g:each in="${calculationRules}" var="calculationRule">
          <td class="text-right"><g:discountRates calculationRule="${calculationRule}" entity="${entity}" indicator="${indicator}"/></td>
        </g:each>
        <g:if test="${doColspan}">
          <td colspan="3"></td>
        </g:if>
      </tr>
  </g:if>

  <g:if test="${denominatorAnswers || dynamicDenomAnswersByDatasets}">
    <tr class="clarification">
      <g:if test="${!indicator?.hideResultCategoryLabels}">
          <th>&nbsp;</th>
      </g:if>
      <th><g:message code="results.denominators" /></th>
      <th></th>
     <g:each in="${calculationRules}" var="calculationRule">
       <th class="text-right">&nbsp;</th>
     </g:each>
     <g:if test="${doColspan && !hideClarificationText}">
        <th colspan="3"></th>
     </g:if>
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
        <g:if test="${doColspan}">
          <td colspan="3"></td>
        </g:if>
      </tr>
    </g:each>

     <g:each in="${dynamicDenomAnswersByDatasets}" var="answerByDatasetAndRules">
         <tr class="clarification">
             <g:if test="${!indicator?.hideResultCategoryLabels}">
                 <td>&nbsp;</td>
             </g:if>
             <td>
                 ${answerByDatasetAndRules.key.resource?.localizedName}
                 <g:if test="${answerByDatasetAndRules.key.answerIds?.get(0)}">
                     <span style="margin-left: 6px;">${answerByDatasetAndRules.key.answerIds?.get(0)} ${answerByDatasetAndRules.key.resource?.unitForData}</span>
                 </g:if>
             </td>
             <g:if test="${!hideClarificationText}">
                 <td>&nbsp;</td>
             </g:if>
             <g:each in="${calculationRules}" var="calculationRule">
                <td class="text-right">
                  ${answerByDatasetAndRules.value?.get(calculationRule)}
                </td>
            </g:each>
            <g:if test="${doColspan}">
                <td colspan="3"></td>
            </g:if>
         </tr>
     </g:each>
   </g:if>
    </tbody>
</table>

<div class="modal hide fade" id="modalDialog" style="width:700px">
	<div class="modal-header"><button type="button" class="close" data-dismiss="modal">&times;</button>
		<h2 id="chartTitle"></h2>
	</div>
	<div class="well" id="chartArea">
       <div class="row"><div class="pie_chart" id="the_chart"></div>
           <div class="pie_chart" id="the_chart_fake" style="display:none"></div>	 <div id="legend"></div></div>

    </div>
</div>
