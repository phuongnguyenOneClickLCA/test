<%@ page import="com.bionova.optimi.core.Constants" %>
<g:set var="region" value="${area ? area.toString() : message(code: "all")}"/>
<%
    def resourceService = grailsApplication.mainContext.getBean("resourceService")
%>
<h2><g:message code="benchmark_heading.material_table" args="[resourcesAmount, region]"/></h2>
<table class='table table-striped table-data' id="resourceBenchmarkTable">
    <thead>
        <tr>
            <th><g:message code="account.country"/></th><th style="word-wrap: break-word; max-width: 250px;"><g:message code="resource.full_name"/></th><th style="width: 100px">${message(code: 'resource.manufacturer')?.capitalize()}</th><th style="width: 150px" class="text-center" >${message(code: "carbonIntensity")} / ${subType?.standardUnit.toLowerCase()}</th><th>${message(code: "unit")?.capitalize()}</th><th><g:message code="data_points.unit"/></th><th><g:message code="quintiles"/></th><th>${message(code: "resource.pdfFile_download")}</th>
        </tr>
    </thead>
    <tbody>
        <g:each in="${resourcesBySubType}" var="resource">
            <tr>
                <td style="text-align: center !important;" class="notCopyable">
                    <span class="hidden">${resource?.isoCodesByAreas?.values()?.first()}</span>
                    <span class="smoothTip tooltip--right" data-tooltip="${countryCodesAndLocalizedName.get(resource?.isoCodesByAreas?.values()?.first())}"><img src="${resourceService.getIsoFlagPath(resource?.isoCodesByAreas)}" onerror="this.onerror=null;this.src='/app/assets/isoflags/globe.png'" class="mediumFlag"/></span>
                </td>
                <td style="word-wrap: break-word; max-width: 200px;">
                    <g:if test="${resource.environmentDataSourceType == 'generic'}">
                        <img src="/app/assets/img/generic_resource.png" class="flagIso" style="width:15px;"/>
                    </g:if>
                    <g:elseif test="${resource.environmentDataSourceType == 'private'}">
                        <i class="far fa-eye-slash" aria-hidden="true"></i>
                    </g:elseif>
                    <g:elseif test="${resource.environmentDataSourceType == 'plant'}">
                        <i class="fa fa-industry" aria-hidden="true"></i>
                    </g:elseif>
                    <g:set var="random" value="${UUID.randomUUID().toString()}"/>
                     ${resource.staticFullName}
                    <opt:renderDataCardBtn indicatorId="${indicatorId}" resourceId="${resource?.resourceId}" profileId="${resource?.profileId}" childEntityId="${entityId}" showGWP="true" infoId="${random}"/>
                    <sec:ifAnyGranted roles="ROLE_SUPER_USER">
                        <a href="javascript:" onclick="window.open('${createLink(action: "showData", controller: "import", params: [resourceUUID: resource.id])}', '_blank', 'width=1024, height=768, scrollbars=1');">
                            <i class="fas fa-search-plus"></i>
                        </a>
                    </sec:ifAnyGranted>
                </td>
                <td class="text-center notCopyable" >${resource.manufacturer}</td>

                <td class="number notCopyable">
                    <g:set var="result" value="${resource.benchmark?.values?.get(benchmarkToShow)}"/>
                    <g:if test="${result}">
                        ${!"NOT_CALCULABLE".equalsIgnoreCase(result) ? result.toDouble()?.round(3) : result }
                    </g:if>
                </td>
                <td class="number notCopyable">
                    ${!"NOT_CALCULABLE".equalsIgnoreCase(result) ? unit : '' }
                </td>
                <td class="text-center notCopyable">${resource.unitForData}</td>
                <td class="text-center notCopyable">
                    <g:if test="${resource.dataProperties?.contains(Constants.INIES)}">
                        <span class="hidden">0</span>
                        <div class="co2CloudContainer" rel="popover" data-trigger="hover" data-content="${g.message(code: "resource.hint.noEmissions", default: "The carbon cloud is not available for INIES data")}">
                            <g:message code="resource.link.emissions.notAvailable" default="Not available"/>
                        </div>
                    </g:if>
                    <g:else>
                        <span class="hidden">${resource.benchmark?.quintiles?.get(benchmarkToShow)}</span>
                        <opt:emissionCloudForQuintile quintile="${resource.benchmark?.quintiles?.get(benchmarkToShow)}"/><br/>
                    </g:else>
                </td>
                <g:if test="${downloadEPDLicense}">
                    <g:set var="epdFile" value="${resourceService.getEpdFile(resource.downloadLink)}"/>
                    <g:if test="${resource.downloadLink && "-" != resource.downloadLink.trim() && resource.downloadLink.startsWith("http")}">
                        <td class="text-center notCopyable">
                            <a href="${resource.downloadLink}" target="_blank">${message(code: 'resource.externalLink')}</a>
                        </td>
                    </g:if>
                    <g:elseif test="${epdFile && epdFile.exists()}">
                        <td class="text-center notCopyable">
                            <a href="${createLink(controller: 'util', action: 'getEpdFile', params: [resourceId: resource.resourceId, profileId: resource.profileId])}" target="_blank">${message(code: 'resource.pdfFile_download')}</a>
                        </td>
                    </g:elseif>
                    <g:else>
                        <td class="text-center notCopyable">
                            <a href="javascript:" class="enterpriseCheck" style="margin: 1px;" rel="popover" data-trigger="hover" data-content="${message(code: "epd_unavailable")}">${message(code: 'resource.pdfFile_download')}</a>
                        </td>
                    </g:else>
                </g:if>
                <g:else>
                    <td class="text-center notCopyable">
                        <a href="javascript:" class="enterpriseCheck" style="margin: 1px;" rel="popover" data-trigger="hover" data-content="${message(code:'enterprise_feature_warning', args:[message(code: 'business') + " - Download EPD. "])}${g.message(code: "account.contact_support")}">${message(code: 'resource.pdfFile_download')}</a>
                    </td>
                </g:else>
            </tr>
        </g:each>
    </tbody>
</table>
<script type="text/javascript">
    $(function () {
        $("#resourceBenchmarkTable").dataTable({
            "aaSorting": [],
            "bPaginate": false,
            "sPaginationType": "full_numbers",
            "bProcessing": true,
            "bDestroy": true,
            });
    })
    $(function () {
        $('[rel="popover"]').popover({
            placement:'top',
            trigger:'hover',
        });
    });
</script>
