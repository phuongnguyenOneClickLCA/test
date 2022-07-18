<!doctype html>
<html>
<head>
<meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
    <div class="container section">
        <div class="sectionbody">
             ${accountCreatedInfo}

              <g:if test="${session?.username}">
                <opt:link action="resendActivation" class="btn btn-primary"><strong><g:message code="index.reactivation_button" /></strong></opt:link>
              </g:if>
              <g:else>
                <g:if test="${logoutUrl}">
                  <a href="${logoutUrl}" class="btn btn-primary"><strong><g:message code="index.back_to_index" /></strong></a>
                </g:if>
                <g:else>
                  <opt:link action="index" class="btn btn-primary"><strong><g:message code="index.back_to_index" /></strong></opt:link>
                </g:else>
              </g:else>
	    </div>
  </div>
</body>
</html>


                
             