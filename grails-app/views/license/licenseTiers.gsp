<%
    def userService = grailsApplication.mainContext.getBean("userService")
%>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="format-detection" content="telephone=no"/>
</head>

<body>
<div class="container">
    <div class="screenheader">
        <h1>License tiers</h1>
    </div>
</div>

<div class="container section">
    <div class="sectionbody">
        <table>
            <tr>
                <td colspan="3">
                    <strong><g:message code="admin.configuration.name"/></strong>
                </td>
            </tr>
            <g:if test="${userService.getSuperUser(user)}">
                <g:each in="${tiers}" var="tier">
                    <tr class="flex">
                        <g:form action="saveLicenseTier" useToken="true">
                            <g:hiddenField name="id" value="${tier.id}"/>
                            <td>
                                <g:textField name="name" value="${tier.name}"/>
                            </td>
                            <td>
                                <opt:submit name="save" value="${message(code: 'save')}" class="btn btn-primary"/>
                            </td>
                        </g:form>
                        <g:form action="deleteLicenseTier" useToken="true">
                            <g:hiddenField name="id" value="${tier.id}"/>
                            <td>
                                <opt:submit name="delete" value="${message(code: 'delete')}" class="btn btn-danger"/>
                            </td>
                        </g:form>
                    </tr>
                </g:each>
                <tr>
                    <td colspan="3">
                        <strong><g:message code="add"/></strong>
                    </td>
                </tr>
                <g:form action="saveLicenseTier" useToken="true">
                    <tr class="flex">
                        <td>
                            <g:textField name="name" placeholder="${message(code: 'admin.configuration.name')}"/>
                        </td>
                        <td>
                            <opt:submit name="save" value="${message(code: 'save')}" class="btn btn-primary"/>
                        </td>
                        <td></td>
                    </tr>
                </g:form>
            </g:if>
            <g:else>
                <tr>
                    <td colspan="3">Adding and editing license tiers is a Super User's feature.</td>
                </tr>
                <g:each in="${tiers}" var="tier">
                    <tr>
                        <td colspan="3">
                            <g:textField name="name" class="removeClicks" value="${tier.name}"/>
                        </td>
                    </tr>
                </g:each>
            </g:else>
        </table>
    </div>
</div>
</body>
</html>

