<!doctype html>
<html>
<head>
<meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
    <div class="container">
        <div class="screenheader">
            <h1>Backups</h1>
        </div>
    </div>
    <div class="container section">
	    <div class="sectionbody">
          <ul>
              <g:each in="${backups?.keySet()}" var="key">
                <li><opt:link controller="backups" action="downloadBackup" params="[fileName: key]" removeEntityId="true">${key}</opt:link></li>
              </g:each>
          </ul>
	    </div>
    </div>
</body>
</html>

