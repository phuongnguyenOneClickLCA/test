<!doctype html>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <h1><strong>${licenseTemplate?.name}</strong> - LicenseTemplate licenses</h1>
    </div>
</div>
<div class="container section">
    <table class="resource" style="margin-bottom: 10px;">
        <tr><th>Name</th><th>Type</th><th>Active</th></tr>
        <tr><td colspan="3">Active licenses</td></tr>
        <g:each in="${activeLicenses}" var="license">
            <tr class="all ${'Production'.equals(license.type) ? 'commercial' : 'noncommercial'}">
                <td>
                    <opt:link controller="license" action="form" params="[id: license.id]">${license.name}</opt:link>
                </td>
                <td class="text-center"><g:message code="license.type.${license.type}" /></td>
                <td>${license.valid}</td>
            </tr>
        </g:each>
        <tr><td colspan="3">Inactive licenses</td></tr>
        <g:each in="${inactiveLicense}" var="license">
            <tr class="all ${'Production'.equals(license.type) ? 'commercial' : 'noncommercial'}">
                <td>
                    <opt:link controller="license" action="form" params="[id: license.id]">${license.name}</opt:link>
                </td>
                <td class="text-center"><g:message code="license.type.${license.type}" /></td>
                <td>${license.valid}</td>
            </tr>
        </g:each>
    </table>
</div>
</body>
</html>

