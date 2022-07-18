<%-- Copyright (c) 2012 by Bionova Oy --%>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <meta name="format-detection" content="telephone=no"/>
    <asset:stylesheet src="filterCombobox.css"/>

</head>
<body>
<div class="container">
    <div class="screenheader">
        <h4>
            <opt:link controller="main" removeEntityId="true"><g:message code="main" /></opt:link> > <g:link controller="account" action="form" id="${account.id}" ><g:message code="account.title_page"/></g:link>  > <g:message code="manage_favorite"/> <br/>
        </h4>
        <g:render template="/entity/basicinfoView"/>
    </div>
    <h2><g:message code="manage_favorite"/></h2>
    <table class='table table-striped table-data' id="favoriteMaterials">
        <thead>
        <tr>
            <th style="width: 50px;"><g:message code="remove_favorite"/> </th><th style="width: 50px;"><g:message code="account.country"/></th><th style="word-wrap: break-word; max-width: 50%px;"><g:message code="resource.full_name"/></th><th style="width: 100px"><g:message code="user.username"/></th><th style="width: 100px">${message(code: 'resource.manufacturer')?.capitalize()}</th><th style="width: 100px">${message(code: "resource.pdfFile_download")}</th>
        </tr>
        </thead>
        <tbody>
        <g:if test="${resources.size() > 0}">
            <%
                def resourceService = grailsApplication.mainContext.getBean("resourceService")
            %>
            <g:each in="${resources}" var="resource">
                <g:if test="${resource}">
                    <tr>
                        <td style="text-align: center !important;" class="notCopyable"><a onclick="toggleFavoriteMaterial('${resource?.key.resourceId+"."+resource?.key.profileId}', this, '${user.id}')"><i class="fa-star-size fas fa-star yellowScheme"></i></a></td>
                        <td style="text-align: center !important;" class="notCopyable">
                            <span class="hidden">${resource?.key.isoCodesByAreas?.values()?.first()}</span>
                            <span class="smoothTip tooltip--right" data-tooltip="${countryCodesAndLocalizedName.get(resource?.key.isoCodesByAreas?.values()?.first())}"><img src="${resource?.key.isoFlagPath}" onerror="this.onerror=null;this.src='/app/assets/isoflags/globe.png'" class="mediumFlag"/></span>
                        </td>
                        <td style="word-wrap: break-word; max-width: 200px;">
                            <g:if test="${resource?.key.environmentDataSourceType == 'generic'}">
                                <img src="/app/assets/img/generic_resource.png" class="flagIso" style="width:15px;"/>
                            </g:if>
                            <g:elseif test="${resource?.key.environmentDataSourceType == 'private'}">
                                <i class="far fa-eye-slash" aria-hidden="true"></i>
                            </g:elseif>
                            <g:elseif test="${resource?.key.environmentDataSourceType == 'plant'}">
                                <i class="fa fa-industry" aria-hidden="true"></i>
                            </g:elseif>
                            <g:set var="random" value="${UUID.randomUUID().toString()}"/>
                            ${resource?.key.staticFullName}
                            <sec:ifAnyGranted roles="ROLE_SUPER_USER">
                                <a href="javascript:;" onclick="window.open('${createLink(action: "showData", controller: "import", params: [resourceUUID: resource?.key.id])}', '_blank', 'width=1024, height=768, scrollbars=1');">
                                    <i class="fas fa-search-plus"></i>
                                </a>
                            </sec:ifAnyGranted>
                            <opt:renderDataCardBtn resourceId="${resource?.key.resourceId}" profileId="${resource?.key.profileId}" infoId="${resource?.key.id}info"/>
                        </td>
                        <g:if test="${resource.value}">
                            <td class="text-center notCopyable" >${resource.value}</td>
                        </g:if>
                        <g:else>
                            <td class="text-center notCopyable" ><g:message code="user.user_undefined"/></td>
                        </g:else>
                        <td class="text-center notCopyable" >${resource.key.manufacturer}</td>
                        <g:if test="${downloadEPDLicense}">
                            <g:set var="epdFile" value="${resourceService.getEpdFile(resource.key.downloadLink)}"/>
                            <g:if test="${resource.key.downloadLink && "-" != resource.key.downloadLink.trim() && resource.key.downloadLink.startsWith("http")}">
                                <td class="text-center notCopyable">
                                    <a href="${resource.key.downloadLink}">${message(code: 'resource.externalLink')}</a>
                                </td>
                            </g:if>
                            <g:elseif test="${epdFile && epdFile.exists()}">
                                <td class="text-center notCopyable">
                                    <a href="${createLink(controller: 'util', action: 'getEpdFile', params: [resourceId: resource.key.resourceId, profileId: resource.key.profileId])}">${message(code: 'resource.pdfFile_download')}</a>
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
                                <a href="javascript:" class="enterpriseCheck" style="margin: 1px;" rel="popover" data-trigger="hover" data-content="${message(code:'enterprise_feature_warning', args:[message(code: 'business') + " - Download EPD. "])}${message(code: "account.contact_support")}">${message(code: 'resource.pdfFile_download')}</a>
                            </td>
                        </g:else>
                    </tr>
                </g:if>
            </g:each>
        </g:if>
        </tbody>
    </table>
</div>

</body>
</html>



