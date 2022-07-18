<%-- Copyright (c) 2012 by Bionova Oy --%>

<!doctype html>
<%-- DEFAULT SYSTEM ERROR PAGE --%>
<html>
	<head>
		<title>
			<g:message code="error.page.title" />
		</title>
		<meta name="layout" content="main">
		<meta name="format-detection" content="telephone=no"/>
	</head>
	<body>
	    <div class="container section">
		    <h2><g:message code="error.header" /></h2>
            <h4><g:message code="error.subtext" /></h4>
            <button class="btn" onclick="goBack();"><i class="icon-chevron-left"></i> <g:message code="back" /></button>
		    <div class="sectionbody">
		        <g:if env="development">
                    <i>${stackTrace}</i>
    	        </g:if>
            </div>
        </div>
	</body>
</html>