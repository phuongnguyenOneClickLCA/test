<div class="table">
    <table class="table table-striped table-condensed table-cellvaligntop nowrap wrapth${reportItemId ? " " + reportItemId : ""}"${collapseThis ? " style=\"display: none;\"" : ""} id="resultTable">
        <calc:renderResultHeadingObject indicator="${indicator}" calculationRules="${calculationRules}" useShortNames="${useShortNames}" resultCategories="${resultCategories}"  entity="${childEntity}" hideLinkAndExplain="${hideLinkAndExplain}" />
        <tbody id="resultRowsBody${reportItemId}">
         <calc:renderResultRows entity="${childEntity}" parentEntity="${parentEntity}" indicator="${indicator}"
                                calculationRules="${calculationRules}" resultCategories="${resultCategories}"
                                hideLinkAndExplain="${hideLinkAndExplain}" hideEmpty="${hideEmpty}" reportItemId="${reportItemId}"
                                expandableCategories="${expandableCategories}" allResultCategories="${allResultCategories}"
                                resultFormatting="${resultFormatting}" resultsForIndicator="${resultsForIndicator}" />
    </tbody>
        <tbody id="totalResultsTbody${reportItemId}">
        <g:if test="${!hideTotals && !indicator.showAverageInsteadOfTotal}">
            <calc:renderTotalResultRows entity="${childEntity}" parentEntity="${parentEntity}" indicator="${indicator}"
                                        calculationRules="${calculationRules}" resultCategories="${resultCategories}"
                                        hideClarification="${hideClarificationText}" reportItemId="${reportItemId}"
                                        resultFormatting="${resultFormatting}"/>
        </g:if>
        <g:elseif test="${!hideTotals && indicator.showAverageInsteadOfTotal}">
            <calc:renderAverageResultRows entity="${childEntity}" indicator="${indicator}"
                                          calculationRules="${calculationRules}" resultCategories="${resultCategories}"
                                          hideClarification="${hideClarificationText}" reportItemId="${reportItemId}"
                                          resultFormatting="${resultFormatting}" resultsForIndicator="${resultsForIndicator}"/>
        </g:elseif>
        </tbody>
        <tbody>
        <g:if test="${showDenominators}">
        <calc:renderResultPerDenominator indicator="${indicator}" calculationRules="${calculationRules}" entity="${childEntity}"
                                         denominators="${indicator?.denominatorListAsDenominatorObjects}" colSpan="${colSpan}"
                                         resultFormatting="${resultFormatting}"/>
        </g:if>
        </tbody>
    </table>
</div>
<g:if test="${renderMonthlyView}">
<script type="text/javascript" defer="defer">
    $(function () {
        showMonthly();
    });
</script>
</g:if>