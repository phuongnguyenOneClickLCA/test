<%@ page import="com.bionova.optimi.core.domain.mongo.UserSuspiciousActivity; com.bionova.optimi.core.domain.mongo.User" %>
<%
    def userService = grailsApplication.mainContext.getBean("userService")
%>
<table class="table table-striped table-condensed">
    <thead>
    <tr>
        <th style="text-align: left;"><g:message code="entity.name"/></th>
        <th style="text-align: left;"><g:message code="user.username"/></th>
        <th style="text-align: left;"><g:message code="user.country"/></th>
        <th style="text-align: left;"><g:message code="user.userType"/></th>
        <th style="text-align: left;"><g:message code="user.channelToken"/></th>
        <th style="text-align: left;"><g:message code="user.organization"/></th>
        <th style="text-align: left;"><g:message code="user.details.linkedOrganisations"/></th>
        <th style="text-align: left;"><g:message code="user.registrationMotive"/></th>
        <th style="text-align: left;"><g:message code="user.language"/></th>
        <th style="text-align: left;"><g:message code="user.additional_rights"/></th>
        <th style="text-align: left;"><g:message code="user.list.header.lastLogin"/></th>
        <th style="text-align: left;"><g:message code="user.list.header.registrationTime"/></th>
        <th style="text-align: left;"><g:message code="user.accountLocked"/></th>
        <th style="text-align: left;"><g:message code="user.suspicious.use.score"/></th>
        <th style="text-align: left;">User linked licenses</th>
        <sec:ifAnyGranted roles="ROLE_SUPER_USER">
            <th style="text-align: left;"><g:message code="user.details.state"/></th>
            <th style="text-align: left;">JSON DUMP</th>
        </sec:ifAnyGranted>
        <sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN">
            <th style="text-align: left;">Delete</th>
        </sec:ifAllGranted>
    </tr>
    </thead>
    <tbody id="activatedUserListTable">
        <g:each in="${activatedUsers}" var="user">
            <g:set var="userId" value="${user._id}" />
            <tr>
                <td>
                    <g:if test="${userService.getSalesView(currentUser)}">
                        <g:link controller="user" action="form" id="${userId}">
                            ${user.name}
                        </g:link>
                    </g:if>
                    <g:else>
                        ${user.name}
                    </g:else>
                </td>
                <td>${user.username}</td>
                <td>${user.country}</td>
                <td>${user.type}</td>
                <td>${user.channelToken}</td>
                <td>${user.organizationName}</td>
                <td>
                    <g:each in="${userService.getAccounts(user as User)}" var="account">
                        <div>
                            <g:link controller="account" action="form" id="${account.id}">${account.companyName}</g:link>
                        </div>
                    </g:each>
                </td>
                <td>${user.registrationMotive}</td>
                <td>${user.language}</td>
                <td>
                    <g:set var="authority" value="${user.authorities?.find()?.authority}"/>
                    ${authority == "ROLE_AUTHENTICATED" ? "" : authority}
                </td>
                <td><g:formatDate format="d.M.yyyy" date="${user.logins*.time?.max()}"/></td>
                <td><g:formatDate format="d.M.yyyy" date="${user.registrationTime}"/></td>
                <td>${user.accountLocked ? "Yes" : "No"}</td>
                <td>${userService.getSuspiciousActivityScore(user.userSuspiciousActivity as UserSuspiciousActivity)}</td>
                <td>
                    <g:set var="licenses" value="${userService.getLicenses(user as User)}" />
                    <g:if test="licenses">
                        <a href="javascript:" class="fiveMarginLeft" onclick="getUserLicenseInformation(this, '${userId}');">${licenses.size()}</a>
                    </g:if>
                </td>
                <sec:ifAnyGranted roles="ROLE_SUPER_USER">
                    <td>
                        <g:form action="modifyState" method="POST">
                            <g:hiddenField name="id" value="${userId}"/>
                            <g:submitButton style="margin-bottom: -17px"
                                            name="enabled"
                                            value="${user.enabled ? "Disable" : "Enable"}"
                                            class="btn ${user.enabled ? "btn-danger" : "btn-primary"}"
                            />
                        </g:form>
                    </td>
                    <td><g:link class="btn btn-primary" action="userAsJson" id="${userId}">JSON</g:link></td>
                </sec:ifAnyGranted>
                <sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN">
                    <td>
                        <g:link class="btn btn-danger" action="remove" id="${userId}" elementId="${userId}"
                                onclick="return modalConfirm(this);"
                                data-questionstr="Do you really want to remove this user?"
                                data-truestr="Delete" data-falsestr="Cancel"
                                data-titlestr="Deleting user account">Delete</g:link>
                    </td>
                </sec:ifAllGranted>
            </tr>
        </g:each>
    </tbody>
</table>

<opt:spinner spinnerId="loadingSpinner-activatedUserList"/>

<div class="paginateButtons">
    <util:remotePaginate controller="user" action="activatedUserList" total="${activatedUsersCount}"
                         params="${params}"
                         update="activatedUserList" max="10" pageSizes="[10, 20, 50, 100]"
    />
</div>