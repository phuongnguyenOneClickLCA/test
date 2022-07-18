<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="format-detection" content="telephone=no"/>
</head>

<body>
<div class="container">
    <div class="screenheader">
        <h1>NMD Element data: ${nmdElement?.name}</h1>
    </div>
</div>

<div class="container section">
    <g:if test="${properties}">
        <a href="javascript:" onclick="showElementByClassOrId('noValue');">Show empty rows</a>
        <table class="resource">
            <g:each in="${properties}">
                <g:if test="${it.value != null}">
                    <tr>
                        <td>${it.key}</td>
                        <td>${it.value instanceof List ? '[' + it.value.join(',<br>') + ']' : it.value}</td>
                    </tr>
                </g:if>
                <g:else>
                    <tr class="noValue" style="display: none;">
                        <td>${it.key}</td>
                        <td>${it.value}</td>
                    </tr>
                </g:else>
            </g:each>
        </table>
    </g:if>
</div>
</body>
</html>

