<tbody id="resourceList">
<g:set var="resourceService" bean="resourceService"/>
<tr id="resourceListHeading">
    <th>No.</th><th><g:message code="ranking"/></th><th style="word-wrap: break-word; max-width: 200px;"><g:message code="resource.full_name"/></th><th><g:message code="account.country"/></th><th><g:message code="data_points.unit"/></th><th><g:message code="result_subtype.standard_unit" args="[subType?.standardUnit?.toLowerCase()]"/></th><th><g:message code="quintiles"/></th><sec:ifAnyGranted roles="ROLE_SUPER_USER"><th>Change in CO2 <i class="fa fa-question-circle co2changeBenchmark" rel="popover" data-trigger="hover"></i></th><th>Result Per Unit (ADMIN)</th><th>Calculation Params (ADMIN)</th><th>Deviation (ADMIN)</th><th>Status (ADMIN)</th><th style="word-wrap: break-word; max-width: 70px;">Manually verified (ADMIN)</th></sec:ifAnyGranted>
</tr><%--
--%><g:set var="index" value="${(Integer) 1}"/>
<g:each in="${benchmarkToShow && benchmarkToShow.size() == 1 ? resourcesBySubType.sort({ it.benchmark?.ranking?.get(benchmarkToShow.first()) }) : resourcesBySubType.sort({ it.staticFullName })}" var="resource">
    <tr class="${resource?.isoCodesByAreas?.values()?.first()}${!"OK".equals(resource.benchmark?.status) || (benchmarkToShow && benchmarkToShow.size() == 1 && resource.benchmark?.values?.get(benchmarkToShow.first())?.equals("NOT_CALCULABLE")) ? ' hiddenTableRow' : ''}">
        <td>${"OK".equals(resource.benchmark?.status) ? index++ : '&nbsp'}</td>
        <td>
            <g:each in="${drawableBenchmarks}" var="benchmark">
                ${resource.benchmark?.ranking?.get(benchmark)} <br/>
            </g:each>
        </td>
        <td style="word-wrap: break-word; max-width: 200px;">
            <g:set var="random" value="${UUID.randomUUID().toString()}"/>
            <g:set var="staticFullName" value="${g.singleResourceLabel(resource: resource)}" />
            <g:if test="${userResource && userResource.resourceId?.equals(resource.resourceId)}">
                <input type="hidden" id="resourceFullName${resource.id}" value="${staticFullName}">
                <strong>${staticFullName}</strong>
                <g:if test="${resource?.isExpiredDataPoint}">
                    <img src='/app/assets/img/iconExpiredBig.png' class='expiredIcon'/>
                    <g:message code="data_point.expired"/>
                </g:if>
                <opt:renderDataCardBtn indicatorId="${indicatorId}" resourceId="${resource?.resourceId}" profileId="${resource?.profileId}" childEntityId="${entityId}" showGWP="true" infoId="${random}"/>
                <sec:ifAnyGranted roles="ROLE_SUPER_USER">
                    <a href="javascript:" onclick="window.open('${createLink(action: "showData", controller: "import", params: [resourceUUID: resource.id])}', '_blank', 'width=1024, height=768, scrollbars=1');"><i class="fas fa-search-plus"></i></a>
                </sec:ifAnyGranted>
                <input type="hidden" id="userResourceIndex" value="${resourcesBySubType.indexOf(resource)}" />
                <input type="hidden" id="userResourceQuintile" value="${resource.benchmark?.quintiles?.get(benchmarkToShow.first())}" />
            </g:if>
            <g:else>
                <input type="hidden" id="resourceFullName${resource.id}" value="${staticFullName}">
                ${staticFullName}
                <opt:renderDataCardBtn indicatorId="${indicatorId}" resourceId="${resource?.resourceId}" profileId="${resource?.profileId}" childEntityId="${entityId}" showGWP="true" infoId="${random}"/>
                <sec:ifAnyGranted roles="ROLE_SUPER_USER">
                    <a href="javascript:" onclick="window.open('${createLink(action: "showData", controller: "import", params: [resourceUUID: resource.id])}', '_blank', 'width=1024, height=768, scrollbars=1');">
                        <i class="fas fa-search-plus"></i>
                    </a>
                </sec:ifAnyGranted>
            </g:else>
        </td>
        <td style="text-align: center !important;" >
            <span class="smoothTip tooltip--right" data-tooltip="${countryCodesAndLocalizedName.get(resource?.isoCodesByAreas?.values()?.first())}"><img src="${resourceService.getIsoFlagPath(resource?.isoCodesByAreas)}" onerror="this.onerror=null;this.src='/app/assets/isoflags/globe.png'" class="mediumFlag"/></span>
        </td>

        <td>${resource.unitForData}</td>

        <td>
            <g:each in="${drawableBenchmarks}" var="benchmark">
                <g:set var="benchmarkValue" value="${resource.benchmark?.values?.get(benchmark)}"/>
                <g:if test="${benchmarkValue}">
                    ${!"NOT_CALCULABLE".equalsIgnoreCase(benchmarkValue) ? opt.formatNumber(number: benchmarkValue.toDouble()) : benchmarkValue } <br/>
                </g:if>
            </g:each>
        </td>
        <td>
            <g:each in="${drawableBenchmarks}" var="benchmark">
                <b>${allBenchmarks?.get(benchmark) ?: benchmark}:</b> <opt:emissionCloudForQuintile quintile="${resource.benchmark?.quintiles?.get(benchmark)}"/><br/>
            </g:each>
        </td>
        <sec:ifAnyGranted roles="ROLE_SUPER_USER">
            <td>
                <g:each in="${drawableBenchmarks}" var="benchmark">
                    <b>${allBenchmarks?.get(benchmark) ?: benchmark}:</b> <g:if test="${resource.benchmark?.factor?.get(benchmark) != null}"><opt:formatNumber number="${resource.benchmark?.factor?.get(benchmark)}"/></g:if><g:else>Uncalculable</g:else><br/>
                </g:each>
            </td>
            <td>
                <g:each in="${drawableBenchmarks}" var="benchmark">
                    <g:set var="resultByUnitForThisBenchmark" value="${resource.benchmark?.resultByUnit?.get(benchmark)}" />
                    <b>${allBenchmarks?.get(benchmark) ?: benchmark}:</b><br/>
                    <g:each in="${resultByUnitForThisBenchmark}" var="resultByUnit">
                        <b>${resultByUnit.key}:</b> <g:if test="${resultByUnit.value != null}"><opt:formatNumber number="${resultByUnit.value}"/></g:if><g:else>Uncalculable</g:else><br/>
                    </g:each>
                </g:each>
            </td>
            <td><b>thickness_mm: </b>${resource.defaultThickness_mm?.round(2)}<br/><b>density: </b>${resource.density?.round(2)}</td>
            <td>
                <g:each in="${drawableBenchmarks}" var="benchmark">
                    <b>${allBenchmarks?.get(benchmark) ?: benchmark}:</b> <opt:formatNumber number="${resource.benchmark?.deviations?.get(benchmark)}"/><br/>
                </g:each>
            </td>
            <td>${resource.benchmark?.status}</td>
            <td><g:checkBox style="width: 25px; height: 17px;" disabled="disabled" value="${resource.dataManuallyVerified}" name="dataManuallyVerified"/></td>
        </sec:ifAnyGranted></tr><%--
--%></g:each><%--
--%></tbody>
<script>
    $(function () {
        $('.co2changeBenchmark[rel=popover]').popover({
            content:"This is the change in CO2 value between the previous datapoint in the benchmark in order of impacts per standard unit",
            placement:'bottom',
            template: '<div class="popover"><div class="arrow"></div><div class="popover-content ribaHelp" style="font-size: 12px !important; font-weight: normal !important;"></div></div>',
            trigger:'hover',
            html:true
        });
    });
</script>