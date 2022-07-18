<div class='sourceListingTable${showDiv ? "" : " style='display: none"}'>
    <h2><span>${message(code: 'results.sources')}</span></h2>
    <g:if test="${isResourceAggregationDisallowed}">
        <g:set var="style" value=""/>
    </g:if>
    <g:else>
        <g:set var="style" value="table-striped table-condensed"/>
    </g:else>
    <table class="table ${style} table-cellvaligntop tableForSourceListing">
        <thead>
        <tr>
            <th>${message(code: 'resource.name')}</th>
            <g:each in="${headers}" var="header">
                <th>${header.key}${header.value}</th>
            </g:each>
        </tr>
        </thead>
        <tbody>
        <g:each in="${values}" var="rowValues">
            <g:set var="rowStyle" value=""/>
            <g:set var="optimiResourceService" bean="optimiResourceService"/>
            <g:each in="${resourcesToIterate}" var="res">
            <g:if test="${rowValues[0]?.equalsIgnoreCase(optimiResourceService.getLocalizedName(res)) && isResourceAggregationDisallowed}">
                <g:if test="${res?.isConstruction}"><g:set var="rowStyle" value="constructionRow"/></g:if>
                <g:elseif test="${res?.isConstituent}"><g:set var="rowStyle" value="constituentRow"/></g:elseif>
            </g:if>
            </g:each>
            <tr class="${rowStyle}">
                <g:each in="${rowValues}" var="cellValue">
                    <td>${cellValue}</td>
                </g:each>
            </tr>
        </g:each>
        </tbody>
    </table>
</div>