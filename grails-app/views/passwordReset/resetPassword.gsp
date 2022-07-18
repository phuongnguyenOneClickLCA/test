<!doctype html>
<html>
<head>
<meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
    <div class="container section">
        <div class="sectionbody">
            <g:form controller="passwordReset" action="savePassword" useToken="true">
                <g:hiddenField name="id" value="${user?.id}" />
                <g:hiddenField name="t" value="${t}" />
                <label for="password"><g:message code="user.password" /></label><g:passwordField name="password" value="" /><br /><br />
                <label for="passwordAgain"><g:message code="user.passwordAgain" /></label><g:passwordField name="passwordAgain" value=""/><br /><br />
                <opt:submit name="submit" value="${message(code:'save')}" class="btn btn-primary" />
            </g:form>
        </div>
    </div>
</body>
</html>