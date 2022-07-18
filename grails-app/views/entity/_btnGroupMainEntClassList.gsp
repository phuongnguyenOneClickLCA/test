<g:if test="${allGroupingQuestion}">
    <g:if test="${defaultFallbackClassification}">
        <li class="${!groupingQuestion ? 'active':''}"><a href="javascript:" style="font-size: 13px; font-weight: normal;"  onclick='renderMainEntityCharts("${parent?.id}", "${indicator?.indicatorId}", "${calculationRule?.calculationRuleId}",${indicatorIds as grails.converters.JSON ?: null}, "", event);'>${defaultFallbackClassification}</a>
        </li>
    </g:if>
    <g:each in="${allGroupingQuestion}" var="classPerIndicator">
        <g:set var="classQ" value="${classPerIndicator?.value}"/>
        <g:set var="classId" value="${classPerIndicator?.key}"/>
        <g:if test="${classQ}">
            <li class="${groupingQuestion?.questionId?.equalsIgnoreCase(classQ?.questionId) ? 'active' : ''}"><a href="javascript:" style="font-size: 13px; font-weight: normal;" onclick='renderMainEntityCharts("${parent?.id}", "${indicator?.indicatorId}", "${calculationRule?.calculationRuleId}",${indicatorIds as grails.converters.JSON ?: null}, "${classId}", event);'>${classQ?.localizedQuestion ?: message(code: 'classification')}</a></li>
        </g:if>
    </g:each>
</g:if>
<script type="text/javascript">
    $(function (){
        <g:if test="${defaultFallbackClassification || indicator?.classificationsMap}">
            $("#classificationListBtnGroup").show()
        </g:if>
        <g:else>
            $("#classificationListBtnGroup").hide()
        </g:else>
    })
</script>