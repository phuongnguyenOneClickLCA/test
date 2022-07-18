<!doctype html>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>

</head>
<body>
    <div class="container">
  	    <div class="screenheader"><h1>Database listing</h1></div>
    </div>
	 
    <div class="container section">
        <div class="sectionbody">
          <table class="datasummary">
            <tr><th>Classes</th></tr>
            <g:each in="${keys}">
              <tr>
                <td><strong>${it}</strong>: ${domainObjects?.get(it)} </td>
              </tr>
            </g:each>
          </table>
 	    </div>
    </div>
</body>
</html>

