<!doctype html>
<html>
<head>
    <g:set var="applicationService" bean="applicationService"/>
    <meta name="layout" content="main"/>
    <meta name="format-detection" content="telephone=no"/>
</head>

<body>
<div class="container">
    <div class="screenheader">
        <h1><g:message code="admin.dataSummary.applications" /></h1>
    </div>
</div>

<div class="container section">
    <div class="sectionbody">
        <sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN">
        <g:uploadForm action="importApplication" accept-charset="UTF-8">
            <div class="column_left">
                <div class="control-group">
                    <label for="applicationFile" class="control-label"><g:message code="admin.dataSummary.choose.json"/></label>

                    <div class="controls"><input type="file" name="applicationFile" id="applicationFile" class="btn" value=""/>
                    </div>
                </div>
            </div>

            <div class="clearfix"></div>
            <opt:submit name="import" value="${message(code: 'import')}" class="btn btn-primary"/>
        </g:uploadForm>
        </sec:ifAllGranted>

        <table class="datasummary">
            <tr><th>Name</th><th>ApplicationId</th><th>Active resources count</th><th>Associated queries</th><th>Associated indicators</th><th>ImportMapper</th><th>Imported</th></tr>
            <g:each in="${applications}" var="application">
                <tr>
                    <td>${application.localizedName}</td>
                    <td>${application.applicationId}</td>
                    <td>${applicationService.getResourceCount(application.applicationId)}</td>
                    <td>
                        <g:set var="appQueries" value="${applicationService.getApplicationQueryIds(application.applicationId)}" />
                        <g:if test="${appQueries}">
                            <a href="javascript:" rel="popover" data-trigger="hover" data-html="true" data-content="${appQueries}">${appQueries.size()}</a>
                        </g:if>
                    </td>
                    <td>
                        <g:set var="appIndicators" value="${applicationService.getApplicationIndicatorIds(application.applicationId)}" />
                        <g:if test="${appIndicators}">
                            <a href="javascript:" rel="popover" data-trigger="hover" data-html="true" data-content="${appIndicators}">${appIndicators.size()}</a>
                        </g:if>
                    </td>
                    <td>
                        <g:if test="${application.importMappers}">
                            ${application.importMappers.collect({it.importMapperId})}
                        </g:if>
                    </td>
                    <td>
                        ${application.importFile}<br />
                        ${application.importTime}
                    </td>
                </tr>
            </g:each>
        </table>
    </div>
</div>
</body>
</html>

