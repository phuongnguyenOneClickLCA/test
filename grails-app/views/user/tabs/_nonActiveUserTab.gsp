<%
    def userService = grailsApplication.mainContext.getBean("userService")
%>
<div id="nonActiveUserTabContent" style="display: none;">
    <g:if test="${notActivatedUsers && !notActivatedUsers.isEmpty()}">
        <h4><g:message code="user.list.notActivatedUsers"
                       args="[notActivatedUsers ? notActivatedUsers.size() : 0]"/></h4>
        <table class="table table-striped table-condensed table-data">
            <thead>
            <tr>
                <th><g:message code="entity.name"/></th>
                <th><g:message code="email"/></th>
                <th>User type</th>
                <th><g:message code="user.organization"/></th>
                <th><g:message code="user.registrationMotive"/></th>
                <th><g:message code="user.registrationTime"/></th>
                <th><g:message code="user.language"/></th>
                <sec:ifAnyGranted roles="ROLE_SALES_VIEW">
                    <th>&nbsp;</th>
                </sec:ifAnyGranted>
            </tr>
            </thead>
            <tbody>
            <g:each in="${notActivatedUsers}" var="user">
                <tr>
                    <td>
                        <g:if test="${userService.getSalesView(currentUser)}">
                            <opt:link controller="user" action="form"
                                      params="[id: user._id]">${user.name}</opt:link>
                        </g:if>
                        <g:else>
                            ${user.name}
                        </g:else>
                    </td>
                    <td>${user.username}</td>
                    <td>${user?.type}</td>
                    <td>${user.organizationName}</td>
                    <td>${user.registrationMotive}</td>
                    <td>
                        <g:formatDate date="${user.registrationTime}" format="dd.MM.yyyy HH:mm"/>
                    </td>
                    <td>${user?.language}</td>
                    <sec:ifAnyGranted roles="ROLE_SALES_VIEW">
                        <td>
                            <opt:link action="activateUser" id="${user._id}" class="btn btn-primary"
                                      onclick="return modalConfirm(this);"
                                      data-questionstr="${message(code: 'user.activate_confirm_question', args: [user.username])}"
                                      data-truestr="${message(code: 'yes')}"
                                      data-falsestr="${message(code: 'no')}"
                                      data-titlestr="${message(code: 'user.activate_confirm_header')}"><g:message
                                    code="user.activate"/></opt:link>
                            <sec:ifAnyGranted roles="ROLE_SUPER_USER"><opt:link action="remove"
                                                                                class="btn btn-danger"
                                                                                onclick="return modalConfirm(this);"
                                                                                id="${user?._id}"
                                                                                data-questionstr="${message(code: 'user.list_delete', args: [user.username])}"
                                                                                data-truestr="${message(code: 'delete')}"
                                                                                data-falsestr="${message(code: 'cancel')}"
                                                                                data-titlestr="${message(code: 'user.delete_confirm.header')}"><g:message
                                        code="delete"/></opt:link></sec:ifAnyGranted>
                            <opt:link action="resendActivation" id="${user._id}" class="btn btn-primary"
                                      style="margin-top: 3px;"><g:message
                                    code="user.resend_activation"/></opt:link>
                        </td>
                    </sec:ifAnyGranted>
                </tr>
            </g:each>
            </tbody>
        </table>

        <p>&nbsp;</p>
    </g:if>
</div>
