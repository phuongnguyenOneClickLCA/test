<g:set var="indicatorService" bean="indicatorService"/>
<table class="datasummary">
    <tr>
        <th>Name</th>
        <th>IndicatorId</th>
        <th>Queries</th>
        <th>Imported</th>
        <th>Deprecated Date</th>
        <th>Import file</th>
        <sec:ifAnyGranted roles="ROLE_SUPER_USER">
            <th>See instances</th>
        </sec:ifAnyGranted>
        <sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN">
            <th>&nbsp;</th>
            <th>&nbsp;</th>
            <th>&nbsp;</th>
        </sec:ifAllGranted>
    </tr>
    <g:each in="${indicators}" var="indicator">
        <tr${!indicator.active ? ' style=\"display:none\" class=\"hiddenItem\"':''}>
            <td>${indicatorService.getLocalizedName(indicator)}<%-- ${raw(indicator?.deprecated ? ' <span class=\"warningRed\">' + message(code: 'deprecated') + '</span>': '')} --%>
            </td>
            <td>${indicator?.indicatorId}</td>
            <td>${indicator?.indicatorQueries?.collect { it.queryId }}</td>
            <td>${indicator?.importTime}</td>
            <td>${indicator?.deprecated ? (indicator?.deprecatedDate ? "${formatDate(date: indicator.deprecatedDate, format: 'dd.MM.yy')}" : "No Available" ) : " " }</td>
            <td>${indicator?.importFile}</td>
            <sec:ifAnyGranted roles="ROLE_SUPER_USER">
                <td><a href="javascript:" onclick="window.open('${createLink(action: "indicatorUsage", params: [indicatorId: indicator?.indicatorId])}', '_blank', 'width=1024, height=768, scrollbars=1');">See use</a></td>
            </sec:ifAnyGranted>
            <sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN">
                <td>
                    <g:if test="${indicator?.active}">
                        <opt:link action="inactivateIndicator" class="btn" id="${indicator.id}" onclick="return modalConfirm(this);"
                                  data-questionstr="Are you sure you wan't to disable indicator ${indicatorService.getLocalizedName(indicator)}"
                                  data-truestr="${message(code:'disable')}" data-falsestr="${message(code:'cancel')}" data-titlestr="Disabling the indicator"><g:message
                                code="admin.dataSummary.inactivate" /></opt:link>
                    </g:if>
                    <g:else>
                        <opt:link action="activateIndicator" class="btn btn-primary" id="${indicator.id}" onclick="return modalConfirm(this);"
                                  data-questionstr="Are you sure you wan't to enable indicator ${indicatorService.getLocalizedName(indicator)}"
                                  data-truestr="${message(code:'enable')}" data-falsestr="${message(code:'cancel')}" data-titlestr="Enabling the indicator"><g:message
                                code="admin.dataSummary.activate"/></opt:link>
                    </g:else>
                </td>
                <td>
                    <g:if test="${indicator?.deprecated}">
                        <g:link action="undeprecateIndicator" class="btn" id="${indicator.id}" onclick="return modalConfirm(this);"
                                data-questionstr="Are you sure you wan't to reset indicator ${indicatorService.getLocalizedName(indicator)}"
                                data-truestr="${message(code:'undeprecate')}" data-falsestr="${message(code:'cancel')}" data-titlestr="Reseting the indicator"><g:message
                                code="undeprecate" /></g:link>
                    </g:if>
                    <g:else>
                        <g:link action="deprecateIndicator" class="btn btn-primary" id="${indicator.id}" onclick="return modalConfirm(this);"
                                data-questionstr="Are you sure you wan't to deprecate indicator ${indicatorService.getLocalizedName(indicator)}"
                                data-truestr="${message(code:'deprecate')}" data-falsestr="${message(code:'cancel')}" data-titlestr="Deprecating the indicator"><g:message
                                code="deprecate"/></g:link>
                    </g:else>
                </td>                    <td>
                <opt:link action="deleteIndicator" class="btn btn-danger" id="${indicator.id}" onclick="return modalConfirm(this);"
                          data-questionstr="Are you sure you wan't to delete indicator ${indicatorService.getLocalizedName(indicator)}"
                          data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}" data-titlestr="Deleting the indicator"><g:message
                        code="delete"/></opt:link>
            </td>
            </sec:ifAllGranted>
        </tr>

    </g:each>
</table>