<%@ page import="org.apache.commons.lang.StringUtils" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <h1>Resources in status report ${resources ? '(' + resources.size() + ')' : ''}${pcrShow ? ", PCR: " + resources?.first()?.pcr : ""}</h1>
    </div>
</div>
<div class="container section">
    <g:if test="${resources}">
        <g:if test="${pcrShow}">
            <table class="resource" id="pcrresulttable">
                <tbody id="pcrresourceList">
                <tr id="pcrresourceListHeading">
                    <th>ResourceId</th><th>ProfileId</th><th>ResourceSubType</th><th>epdProgram</th><th>epdNumber</th><th>pcr</th><th>pcrQuality</th><th>dataProperties</th>
                </tr><%--
          --%><g:each in="${resources}" var="resource"><%--
              --%><tr>
                    <td>${resource.resourceId}</td>
                    <td>${resource.profileId}</td>
                    <td>${resource.resourceSubType}</td>
                    <td>${resource.epdProgram}</td>
                    <td>${resource.epdNumber}</td>
                    <td>${resource.pcr}</td>
                    <td>${resource.pcrQuality}</td>
                    <td>${resource.dataProperties}</td>
                </tr><%--
          --%></g:each><%--
    --%></tbody></table>
        </g:if>
        <g:else>
            <table class="resource" id="resulttable">
                <tbody id="resourceList">
                <tr id="resourceListHeading">
                    <th>ResourceId</th><th>ProfileId</th><th>Active</th><th>ResourceSubType</th><th>Show data</th><th>Stage count</th><th>Stages</th><th>See instances</th><th>DefaultProfile</th><th>Density</th><th>ApplicationId</th><th>Groups</th><th>Name</th><th>Unit</th><th>GWP100</th><th>ServiceLife</th><th>Mass</th><th>Energy</th><th>Import file</th><th>Areas</th><th>Resource Group</th><th>UpstreamDB</th><sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN"><th>&nbsp;</th></sec:ifAllGranted>
                </tr><%--
          --%><g:each in="${resources}" var="resource"><%--
              --%><tr class="${resource.resourceGroup ? join(in: resource.resourceGroup, delimiter: ' ') : ''}">
                    <td>${resource.resourceId}</td><td>${resource.profileId}</td><td>${resource.active}</td>
                    <td>${resource.resourceSubType}</td>
                    <td><a href="javascript:" onclick="window.open('${createLink(action: "showData", params: [resourceUUID: resource.id])}', '_blank', 'width=1024, height=768, scrollbars=1');">Data</a></td>
                    <g:if test="${resource.impacts}">
                        <td>
                            ${resource.impacts.keySet().toList().size()}
                        </td>
                        <td>
                            <g:join in="${resource.impacts.keySet()}" delimiter=", " />
                        </td>
                    </g:if>
                    <g:else>
                        <td>&nbsp;</td><td>&nbsp;</td>
                    </g:else>
                    <td><a href="javascript:" onclick="window.open('${createLink(action: "resourceUsage", params: [resourceId: resource.resourceId, profileId: resource.profileId])}', '_blank', 'width=1024, height=768, scrollbars=1');">See use</a></td><td>${resource.defaultProfile}</td>
                    <td>${resource.density}</td><td>${resource.applicationId}</td><td>${resource.resourceGroup}</td><td>${resource.localizedName}</td><td>${resource.unitForData}</td><td>${resource.impactGWP100_kgCO2e}</td><td>${resource.serviceLife}</td><td>${resource.massConversionFactor}</td><td>${resource.energyConversionFactor}</td><td>${resource.importFile}</td><td>${resource.areas}</td><td>${resource.resourceGroup}</td><td>${resource.upstreamDB}</td>
                    <sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN">
                        <td>
                            <g:link action="deleteResource" class="btn btn-danger" id="${resource.id}" params="[deleteRedirect: deleteRedirect]" onclick="return modalConfirm(this);" data-questionstr="${message(code:'admin.import.resource.delete_question')}"
                                    data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}" data-titlestr="${message(code:'admin.import.resource.delete_title')}"><g:message code="delete" /></g:link>
                        </td>
                    </sec:ifAllGranted>
                </tr><%--
          --%></g:each><%--
    --%></tbody></table>
        </g:else>
    </g:if>
</div>
</body>
</html>

