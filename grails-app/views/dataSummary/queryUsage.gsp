<!doctype html>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <h1>Query use: ${query?.localizedName}</h1>
    </div>
</div>
<div class="container section">
    <table class="resource">
        <tr>
            <th>Entity</th><th>Datasets</th><th>Total count</th>
        </tr>
        <g:each in="${entityNameWithDatasets}" var="entityWithDatasets">
            <tr>
                <td>${entityWithDatasets.key}</td>
                <td>
                    <g:each in="${entityWithDatasets.value}" var="dataset">
                        ${dataset}<br />
                    </g:each>
                </td>
                <td>${entityWithDatasets.value?.size()}</td>
            </tr>
        </g:each>
    </table>
</div>
</body>
</html>

