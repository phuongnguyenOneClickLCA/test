<!doctype html>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <h1><g:message code="admin.logs" /></h1>
    </div>
    <div class="container section">
	    <div class="sectionbody">
          <ul>
          <g:each in="${logs?.keySet()}" var="key">
            <li><g:link action="downloadLogFile" params="[filePath: logs?.get(key)]">${key}</g:link></li>
          </g:each>
          </ul>
	    </div>
    </div>
</div>
</body>
</html>

