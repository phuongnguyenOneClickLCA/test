<%-- Copyright (c) 2012 by Bionova Oy --%>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <h1><g:message code="admin.defaultManager.title" /></h1>
    </div>
</div>

<div class="container section">
    <div class="sectionbody">
        <g:if test="${applications}">
        <table class="table table-striped table-condensed table-data data-list">
            <thead>
            <tr>
                <th>Application name</th>
                <th>ApplicationId</th>
                <th>Default values</th>
                <th>&nbsp;</th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${applications}" var="application">
                <tr>
                    <td>${application.localizedName}</td>
                    <td>${application.applicationId}</td>
                    <td>
                        <g:each in="${application.defaultValues}" var="applicationDefault">
                            <g:each in="${applicationDefault.defaultValueSets}" var="defaultValueSet">
                                <g:link action="form" params="[applicationId: application.applicationId, applicationDefaultId: applicationDefault.defaultValueId, defaultValueSetId: defaultValueSet.defaultValueSetId]">${defaultValueSet.localizedName}</g:link><br />
                            </g:each>
                        </g:each>
                    </td>
                </tr>
            </g:each>
            </tbody>
        </table>
        </g:if>
        <g:else>
            <g:message code="admin.defaultManager.no_applications" />
        </g:else>
    </div>
</div>
</body>
</html>