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
        <h1>Resource data</h1>
    </div>
</div>
<div class="container section">
    <g:if test="${resources}">
        <table class="resource" id="resourceList">
            <tr id="resourceListHeading">
                <th>ResourceId</th><th>ProfileId</th><th>Active</th><th>Virtual</th><th>Virtual parts</th><th>Show data</th><th>Stage count</th><th>Stages</th><th>See instances</th><th>DefaultProfile</th><th>Density</th></th><th>ApplicationId</th><th>Groups</th><th>Name</th><th>Unit</th><th>GWP100</th><th>ServiceLife</th><th>Mass</th><th>Energy</th><th>Import file</th><sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN"><th>&nbsp;</th></sec:ifAllGranted>
            </tr><%--
          --%><g:each in="${resources}" var="resource"><%--
              --%><tr class="${resource.resourceGroup ? join(in: resource.resourceGroup, delimiter: ' ') : ''}">
                <td>${resource.resourceId}</td><td>${resource.profileId}</td><td>${resource.active}</td>
                <td>${resource.virtual ? 'true' : 'false'}</td>
                <td>
                    <g:if test="${resource.compositeParts}">
                        <g:each in="${resource.compositeParts}" var="compositePart">
                            ResourceId: ${compositePart?.resourceId}<br />ProfileId: ${compositePart?.profileId}<br />Quantity: ${compositePart?.quantity}
                        </g:each>
                    </g:if>
                </td>
                <td><a href="javascript:;" onclick="window.open('${createLink(action: "showData", id: resource.id)}', '_blank', 'width=1024, height=768, scrollbars=1');">Data</a></td>
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
                <td><a href="javascript:;" onclick="window.open('${createLink(action: "resourceUsage", params: [resourceId: resource.resourceId, profileId: resource.profileId])}', '_blank', 'width=1024, height=768, scrollbars=1');">See use</a></td><td>${resource.defaultProfile}</td>
                <td>${resource.density}</td><td>${resource.applicationId}</td><td>${resource.resourceGroup}</td><td>${resource.localizedName}</td><td>${resource.unitForData}</td><td>${resource.impactGWP100_kgCO2e}</td><td>${resource.serviceLife}</td><td>${resource.massConversionFactor}</td><td>${resource.energyConversionFactor}</td><td>${resource.importFile}</td>
                <td>
                    <g:if test="${resource.defaultProfile && resources.size() > 1}">
                        <g:link class="btn btn-danger" controller="util" action="setDefaultProfileFalse" id="${resource.id}" onclick="return modalConfirm(this);" data-questionstr="This change must also be reflected in imported data, are you sure?"
                                data-truestr="${message(code:'yes')}" data-falsestr="${message(code:'cancel')}" data-titlestr="Set default profile false">Set default profile false</g:link>
                    </g:if>
                    <g:else>
                        <input type="button" class="btn btn-danger" disabled="disabled" value="Set default profile false" />
                    </g:else>
                </td>
            </tr><%--
          --%></g:each><%--
    --%></table>
     </g:if>
</div>
</body>
</html>