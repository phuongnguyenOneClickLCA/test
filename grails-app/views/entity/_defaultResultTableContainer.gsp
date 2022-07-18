<div id="defaultTableContainer-${reportItemId}">
    <g:form name="reportParamsForm-${reportItemId}" class="hidden">
        <g:hiddenField name="indicatorId" value="${indicatorId}"/>
        <g:hiddenField name="entityId" value="${entityId}"/>
        <g:hiddenField name="reportItemId" value="${reportItemId}"/>
        <g:hiddenField name="hideBreakdown" value="${hideBreakdown}"/>
        <g:hiddenField name="parentEntityId" value="${parentEntityId}"/>
        <g:hiddenField name="collapseThis" value="${collapseThis}"/>
        <g:hiddenField name="hideEmpty" value="${hideEmpty}"/>
        <g:hiddenField name="hideLinkAndExplain" value="${hideLinkAndExplain}"/>
    </g:form>
    <div class="table">
        <div class="clearfix ${reportItemId}" ${collapseThis ? " style=\"display: none;\"" : ""}>
            <opt:spinner spinnerId="loadingSpinner-reportItem-${reportItemId}" />
        </div>
    </div>
</div>
<g:if test="${!collapseThis}">
<script defer>
    $(function () {
        loadReportItem('${reportItemId}');
    });
</script>
</g:if>