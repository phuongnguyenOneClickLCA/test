<div class="container section" style="page-break-after: always;">
    <div class="sectionheader hide-on-print">
        <button class="pull-left sectionexpander"><i class="icon icon-chevron-down expander"></i><i class="icon icon-chevron-right collapser"></i></button>
        <h2>${indicator?.localizedName}</h2>
    </div>

<g:if test="${'design'.equals(indicator?.report?.iterateOverEach)}">
    <div class="sectionbody">
    <g:each in="${designs}" var="design" status="index">
        <div class="pageBreak" style="position: relative;${index > 0 ? ' page-break-before: always;' : ''}">
            <opt:backgroundImage indicator="${indicator}" />
            <div style="position: absolute; z-index: 2; margin-top: 90px; width: 595px; word-wrap: break-word;">
                <g:if test="${indicator?.report?.showEntityName}">
                    <h3>${entity?.name}: ${design.name}</h3>
                </g:if>
            <g:if test="${indicator?.report}">
                <calc:renderIndicatorReport entity="${design}" parentEntity="${entity}" indicator="${indicator}" report="${indicator?.report}"   />
            </g:if>
            </div>
        </div>
    </g:each>
    </div>
</g:if>
<g:if test="${'operating'.equals(indicator?.report?.iterateOverEach) || 'operatingPeriod'.equals(indicator?.report?.iterateOverEach)}">
    <div class="sectionbody">
    <g:each in="${operatingPeriods}" var="operatingPeriod" status="index">
        <div class="pageBreak" style="position: relative;${designs || index > 0 ? ' page-break-before: always;' : ''}">
            <opt:backgroundImage indicator="${indicator}" />
            <div style="position: absolute; z-index: 2; margin-top: 90px; width: 595px; word-wrap: break-word;">
            <g:if test="${indicator?.report?.showEntityName}">
                <h3>${entity?.name}: ${operatingPeriod.operatingPeriodAndName}</h3>
            </g:if>
            <g:if test="${indicator?.report}">
                <calc:renderIndicatorReport entity="${operatingPeriod}" parentEntity="${entity}" indicator="${indicator}" report="${indicator?.report}"   />
            </g:if>
            </div>
        </div>
    </g:each>
    </div>
</g:if>
</div>