<!doctype html>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <h1>Resource usage</h1>
    </div>
</div>
<div class="container section">
    <table class="resource">
        <tr>
            <th>Entity</th><th>Datasets</th><th>Total count</th><th>Manager/Modifier users</th>
        </tr>
        <g:each in="${entitiesAndDatasets}" var="entityWithDatasets">
            <tr>
                <td>${entityWithDatasets.key}</td>
                <td>
                <g:each in="${entityWithDatasets.value}" var="dataset">
                    ${dataset}<br />
                </g:each>
                </td>
                <td>${entityWithDatasets.value?.size()}</td>
                <td>
                    <g:set var="usernames" value="${managersAndModifiers?.find({it.key?.equals(entityWithDatasets.key)})?.value}" />
                    <g:if test="${usernames}">
                        <g:each in="${usernames}" var="username">
                            ${username}<br/>
                        </g:each>
                    </g:if>
                </td>
            </tr>
        </g:each>
    </table>
</div>
</body>
</html>

