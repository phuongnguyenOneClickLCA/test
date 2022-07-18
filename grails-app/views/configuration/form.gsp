<!doctype html>
<html>
<head>
<meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
    <div class="container">
        <div class="screenheader">
	        <h1><g:message code="admin.configuration" /></h1>
        </div>
    </div>
	 
    <div class="container section">
	    <div class="sectionbody">
            <table class="datasummary">
            <tr><th><g:message code="admin.configuration.name" /></th><th><g:message code="admin.configuration.value" /></th><th>&nbsp;</th></tr>
            <g:each in="${configurations}" var="config">
              <g:form action="save" useToken="true">
              <g:hiddenField name="id" value="${config.id}" />
              <tr>
                <td>
                  <g:textField name="name" value="${config.name}" />
                </td>
                <td>
                  <g:textField name="value" value="${config.value}" />
                </td>
                  <td class="flex">
                      <opt:submit name="save" value="${message(code:'save')}" class="btn btn-primary" />
                </td>
              </tr>
              </g:form>
            </g:each>
            <tr><td colspan="3"><strong><g:message code="add" /></strong></td></tr>
            <g:form action="save" useToken="true">
              <tr>
                <td>
                    <g:textField name="name" placeholder="${message(code: 'admin.configuration.name')}"/>
                </td>
                <td>
                    <g:textField name="value" placeholder="${message(code: 'admin.configuration.value')}"/>
                </td>
                  <td class="flex">
                      <opt:submit name="save" value="${message(code:'save')}" class="btn btn-primary" />
                </td>
              </tr>
            </g:form>
          </table>
	    </div>
    </div>
</body>
</html>

