<%
    def userService = grailsApplication.mainContext.getBean("userService")
%>
<!doctype html>
<%@ page expressionCodec="html" %>
<html>
<head>
    <% def emailConfig = grailsApplication.mainContext.getBean("emailConfiguration") %>
    <meta name="layout" content="main"/>
    <meta name="format-detection" content="telephone=no"/>
    <g:set var="optimiResourceService" bean="optimiResourceService"/>
</head>

<body>
<div class="container">
    <g:set var="indicatorService" bean="indicatorService"/>
    <opt:breadcrumbsNavBar specifiedHeading="${user?.id ? message(code: 'user.profile') : message(code: 'add')}"/>

    <div class="screenheader">
        <h1>
            <i class="layout-icon-user"></i>
            <g:if test="${user?.id}">
                <g:message code="user.profile"/>
            </g:if>
            <g:else>
                <g:message code="add"/>
            </g:else>
        </h1>
    </div>

    <div>

        <div class="container section">
            <div class="sectionbody">
                <g:form action="save">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <g:hiddenField name="id" value="${user?.id}"/>
                    <sec:ifAnyGranted roles="ROLE_SYSTEM_ADMIN">
                        <g:if test="${user?.activationDeadline}">
                            <p>Manually override activation deadline by setting this to empty</p>
                            <opt:link action="removeActivationDeadline" params="[userId: user?.id]"
                                      class="btn btn-danger bold" style="vertical-align: top;">Clear</opt:link>
                            <g:textField name="activationDeadline" disabled="true" value="${user.activationDeadline}"/>
                        </g:if>
                    </sec:ifAnyGranted>
                    <div class="clearfix"></div>

                    <div class="tab-content">
                        <div class="column_left">
                            <fieldset>
                                <g:if test="${ownAccount}">
                                    <div class="control-group">
                                        <label for="name" class="control-label">
                                            <g:message code="resource.full_name"/>
                                        </label>

                                        <div class="controls">
                                            <g:textField
                                                    disabled="${!ownAccount && !userService.getSuperUser(currentUser) ? "disabled" : "false"}"
                                                    class="input-xlarge" id="userName" value="${user?.name}"
                                                    name="name"/> <span class="label label-success hide">Saved</span>
                                        </div>
                                    </div>

                                    <div class="control-group">
                                        <label for="phone" class="control-label">
                                            <g:message code="user.details.phone"/>
                                        </label>

                                        <div class="controls">
                                            <g:textField
                                                    disabled="${!ownAccount && !userService.getSuperUser(currentUser) ? "disabled" : "false"}"
                                                    class="input-xlarge" type="tel" value="${user?.phone}" name="phone"
                                                    pattern="^\\+(?:[0-9]●?){4,25}[0-9]\$"
                                                    title="${message(code: 'user.phone.size.error')}"/>
                                        </div>
                                    </div>

                                    <div class="control-group">
                                        <label for="username" class="control-label">
                                            <g:message code="user.details.username"/>
                                        </label>

                                        <div class="controls">
                                            <g:textField class="input-xlarge" value="${user?.username}" name="username"
                                                         disabled="disabled"/><br/>
                                            <g:message code="user.emailValidated"/> <g:formatBoolean
                                                    boolean="${user?.emailValidated}" false="${message(code: 'no')}"
                                                    true="${message(code: 'yes')}"/>
                                        </div>
                                    </div>
                                </g:if>
                                <g:else>
                                    <div class="control-group">
                                        <label for="name" class="control-label">
                                            <g:message code="resource.full_name"/>
                                        </label>

                                        <div class="controls">
                                            <a href="javascript:" onclick="showPersonalData()"
                                               class="btn personalDataBtn">See personal data</a>
                                            <g:textField disabled="${!userService.getSuperUser(currentUser) ? "disabled" : "false"}"
                                                         class="input-xlarge personalData" style="display: none"
                                                         id="userName" value="${user?.name}" name="name"/> <span
                                                class="label label-success hide">Saved</span>
                                        </div>
                                    </div>

                                    <div class="control-group">
                                        <label for="phone" class="control-label">
                                            <g:message code="user.details.phone"/>
                                        </label>

                                        <div class="controls">
                                            <a href="javascript:" onclick="showPersonalData()"
                                               class="btn personalDataBtn">See personal data</a>
                                            <g:textField disabled="${!userService.getSuperUser(currentUser) ? "disabled" : "false"}"
                                                         class="input-xlarge personalData" style="display: none"
                                                         value="${user?.phone}" name="phone"/>
                                        </div>
                                    </div>

                                    <div class="control-group">
                                        <label for="username" class="control-label">
                                            <g:message code="user.details.username"/>
                                        </label>

                                        <div class="controls">
                                            <a href="javascript:" onclick="showPersonalData()"
                                               class="btn personalDataBtn">See personal data</a>
                                            <g:textField class="input-xlarge personalData" value="${user?.username}"
                                                         style="display: none" name="username"
                                                         disabled="disabled"/><br/>
                                            <span style="display: none;" class="personalData"><g:message
                                                    code="user.emailValidated"/> <g:formatBoolean
                                                    boolean="${user?.emailValidated}" false="${message(code: 'no')}"
                                                    true="${message(code: 'yes')}"/></span>
                                        </div>
                                    </div>
                                </g:else>
                                <div class="control-group">
                                    <label for="registrationMotive" class="control-label">
                                        <g:message code="user.registrationMotive"/>
                                    </label>

                                    <div class="controls">
                                        <g:if test="${user?.registrationMotive}">
                                            <g:textField name="registrationMotive" value="${user?.registrationMotive}"
                                                         disabled="disabled"/>
                                        </g:if>
                                        <g:else>
                                            <g:textField name="registrationMotive" value="" disabled="disabled"/>
                                        </g:else>
                                    </div>
                                </div>

                                <div class="control-group" style="margin-top: 10px;">
                                    <label for="type"><g:message code="user.type"/></label><g:select
                                        style="width:220px;" name="type" id="typeSelect" from="${userTypes}"
                                        value="${g.message(code: user?.type)}"
                                        optionKey="${{ it.key }}"
                                        optionValue="${{ message(code: it.value) }}"
                                        noSelection="['': '']"/>
                                </div><br/>

                                <div class="control-group">
                                    <label for="registrationMotive" class="control-label">
                                        <g:message code="user.details.organization"/>
                                    </label>

                                    <div class="controls">
                                        <g:textField
                                                disabled="${!ownAccount && !userService.getSuperUser(currentUser) ? "disabled" : "false"}"
                                                name="organizationName" value="${user?.organizationName}"/>
                                    </div>
                                </div>

                                <g:if test="${linkedAccounts}">
                                    <div class="control-group">
                                        <div><g:message code="user.details.linkedOrganisations"/>:</div>
                                        <g:each in="${linkedAccounts}" var="account">
                                            <g:link controller="account" action="form"
                                                    params="[id: account.id]">- ${account.companyName}</g:link><br/>
                                        </g:each>
                                    </div>
                                </g:if>

                                <div class="control-group">
                                    <label for="country">
                                        <g:message code="account.country"/>
                                    </label>

                                    <div class="controls">
                                        <g:select
                                                disabled="${!ownAccount && !userService.getSuperUser(currentUser) ? "disabled" : "false"}"
                                                value="${user?.country}" name="country" id="countrySelect"
                                                from="${countries}"
                                                optionKey="${{ it.resourceId }}"
                                                optionValue="${{ it.get(localizedName) }}"
                                                noSelection="['': '']"/>
                                    </div>
                                </div>

                                <div class="control-group">
                                    <label for="language" class="control-label">
                                        <g:message code="user.language"/>
                                    </label>

                                    <div class="controls">
                                        <g:select
                                                disabled="${!ownAccount && !userService.getSuperUser(currentUser) ? "disabled" : "false"}"
                                                name="language" value="${user?.language}" from="${languages}"
                                                optionKey="resourceId" optionValue="${{ optimiResourceService.getLocalizedName(it) }}"
                                                noSelection="['': message(code: 'select')]"/>
                                    </div>
                                </div>

                                <div class="control-group">
                                    <label for="unitSystem" class="control-label">
                                        <g:message code="user.unitSystem"/>
                                    </label>

                                    <div class="controls">
                                        <g:select
                                                disabled="${!ownAccount && !userService.getSuperUser(currentUser) ? "disabled" : "false"}"
                                                name="unitSystem" value="${user?.unitSystem}" from="${unitSystems}"
                                                optionKey="${{ it.value }}"
                                                optionValue="${{ message(code: 'unit_system.' + it.value) }}"/>
                                    </div>
                                </div>

                                <div class="control-group">
                                    <label for="localeString" class="control-label">
                                        <g:message code="user.localeString"/>
                                    </label>

                                    <div class="controls">
                                        <g:select
                                                disabled="${!ownAccount && !userService.getSuperUser(currentUser) ? "disabled" : "false"}"
                                                name="localeString" value="${user?.localeString}" from="${userLocales}"
                                                optionKey="${{ it.value.toLanguageTag() }}"
                                                optionValue="${{ it.key }}"/>
                                    </div>
                                </div>

                                <div class="control-group">
                                    <label class="control-label"></label>

                                    <div class="controls">
                                        <label class="checkbox">
                                            <g:checkBox
                                                    disabled="${!ownAccount && !userService.getSuperUser(currentUser) ? "disabled" : "false"}"
                                                    name="enableFloatingHelp" value="${user?.enableFloatingHelp}"/>
                                            <g:message code="user.showHelpFloat"/>
                                        </label>
                                    </div>

                                    <div class="controls">
                                        <label class="checkbox">
                                            <g:checkBox
                                                    disabled="${!ownAccount && !userService.getSuperUser(currentUser) ? "disabled" : "false"}"
                                                    name="disableZohoChat" value="${user?.disableZohoChat}"/>
                                            <g:message code="user.disableZohoChat"/>
                                        </label>
                                    </div>

                                    <div class="controls">
                                        <label class="checkbox">
                                            <g:checkBox
                                                    disabled="${!ownAccount && !userService.getSuperUser(currentUser) ? "disabled" : "false"}"
                                                    name="disableOnboarding" value="${user?.disableOnboarding}"/>
                                            <g:message code="user.disableOnboarding"/>
                                        </label>
                                    </div>
                                </div>
                            </fieldset>

                            <fieldset>
                                <g:if test="${user?.id && (ownAccount || userService.getSuperUser(currentUser))}">
                                    <p><a href="javascript:" onclick="showHidePasswordFields('passwordDiv');"><g:message
                                            code="user.change_password"/></a></p>
                                </g:if>
                                <div class="control-group" style="display: ${user?.id ? 'none' : 'block'};"
                                     id="passwordDiv">
                                    <div class="controls">
                                        <p><label><g:message code="user.password"/></label> <g:passwordField
                                                value="${user?.password}" class="input-xlarge" name="password"
                                                disabled="disabled"/></p>

                                        <p><label><g:message code="user.passwordAgain"/></label> <g:passwordField
                                                value="${user?.passwordAgain}" class="input-xlarge" name="passwordAgain"
                                                disabled="disabled"/></p>
                                    </div>
                                </div>
                            </fieldset>

                            <div class="clearfix"></div>
                            <opt:submit disabled="${!ownAccount && !userService.getSuperUser(currentUser) ? true : false}" name="save"
                                        value="${message(code: 'save')}" class="btn btn-primary"/>
                            <opt:link action="cancel" class="btn"><g:message code="cancel"/></opt:link>
                            <sec:ifAnyGranted roles="ROLE_SYSTEM_ADMIN">
                                <opt:link action="remove" class="btn btn-danger" onclick="return modalConfirm(this);"
                                          id="${user?.id}"
                                          data-questionstr="${message(code: ownAccount ? 'user.delete' : 'user.list_delete', args: [user?.username])}"
                                          data-truestr="${message(code: 'delete')}"
                                          data-falsestr="${message(code: 'cancel')}"
                                          data-titlestr="${message(code: 'user.delete_confirm.header')}"><g:message
                                        code="delete"/></opt:link>
                            </sec:ifAnyGranted>
                            <g:if test="${user?.testRobot || hasDebugLicense}">
                                <opt:link action="permanentEntityDeletion" class="btn btn-danger"
                                          params="[hasDebugLicense: hasDebugLicense]"
                                          onclick="return modalConfirm(this);" id="${user?.id}"
                                          data-questionstr="Are you sure you want to permanently delete all the entities you have created?"
                                          data-truestr="${message(code: 'delete')}"
                                          data-falsestr="${message(code: 'cancel')}"
                                          data-titlestr="Permanent entity deletion">Delete all my entities</opt:link>
                            </g:if>
                        </div>

                        <div class="column_left middle-margins">
                        <fieldset>
                            <g:if test="${user?.id && (userService.isSystemAdmin(currentUser) || currentUser?.allowAdmin)}">
                                <div class="control-group">
                                    <label for="authorities" class="control-label">
                                        Authorities
                                    </label>

                                    <div class="controls">
                                        <g:set var="authority" value="${user?.authorities?.toArray()[0]}"/>
                                        <g:select name="authorities" from="${authorities}" optionKey="id"
                                                  optionValue="${{ it.authority }}" value="${authority?.id}"
                                                  noSelection="${[null: '']}"/>
                                    </div>
                                </div>

                                <div class="control-group">
                                    <div class="controls">
                                        <label class="checkbox">
                                            Allow switching back to admin
                                            <g:checkBox name="allowAdmin" value="${user?.allowAdmin}"/>
                                        </label>
                                    </div>
                                </div>
                            </g:if>

                            <g:if test="${userService.getSuperUser(currentUser)}">
                                <g:if test="${user?.id}">
                                    <div class="control-group">
                                        <label class="control-label"></label>

                                        <div class="controls">
                                            <label class="checkbox">
                                                <g:checkBox id="emailTokenAuthEnabled" value="${true}"
                                                            checked="${user?.emailTokenAuthEnabled ? true : false}"
                                                            name="emailTokenAuthEnabled"/>
                                                Force 2-factor authentication
                                            </label>
                                        </div>
                                    </div>

                                    <div class="control-group">
                                        <label class="control-label"></label>

                                        <div class="controls">
                                            <label class="checkbox">
                                                <g:checkBox id="disablePeriodicalEmailTokenAuth" value="${true}"
                                                            checked="${user?.disablePeriodicalEmailTokenAuth ? true : false}"
                                                            name="disablePeriodicalEmailTokenAuth"/>
                                                Disable periodical 2-factor authentication
                                            </label>
                                        </div>
                                    </div>
                                </g:if>
                            </g:if>
                            <g:else>
                                <g:if test="${user?.id}">
                                    <g:if test="${user?.emailTokenAuthEnabled}">
                                        <input type="hidden" name="emailTokenAuthEnabled" value="true">
                                    </g:if>
                                    <g:if test="${user?.disablePeriodicalEmailTokenAuth}">
                                        <input type="hidden" name="disablePeriodicalEmailTokenAuth" value="true">
                                    </g:if>
                                </g:if>
                            </g:else>
                            <sec:ifAnyGranted roles="ROLE_SYSTEM_ADMIN">
                                <g:if test="${user?.id}">
                                    <div class="control-group">
                                        <label class="control-label"></label>

                                        <div class="controls">
                                            <label class="checkbox">
                                                <g:checkBox id="enabled" value="${user?.enabled}" name="enabled"/>
                                                <g:message code="user.details.state"/>
                                            </label>
                                        </div>
                                    </div>

                                    <div class="control-group">
                                        <label class="control-label">
                                            <g:message code="user.loginCount"/>: ${user.getLoginCount()}
                                        </label>
                                    </div>

                                    <div class="control-group">
                                        <label class="control-label">
                                            <g:message code="user.browserAndOs"/>:   ${user?.browserAndOs}
                                        </label>
                                    </div>

                                    <g:if test="${managedAccounts}">
                                        <div class="control-group">
                                            <label class="control-label">
                                                <g:message
                                                        code="admin.user.mainAccounts"/>: ${managedAccounts.collect({ it.companyName })}
                                            </label>
                                        </div>
                                    </g:if>

                                    <g:if test="${basicAccounts}">
                                        <div class="control-group">
                                            <label class="control-label">
                                                <g:message
                                                        code="admin.user.basicAccounts"/>:   ${basicAccounts.collect({ it.companyName })}
                                            </label>
                                        </div>
                                    </g:if>
                                </g:if>
                                </fieldset>

                            </sec:ifAnyGranted>

                            <sec:ifAnyGranted roles="ROLE_SALES_VIEW">
                                <div class="control-group">
                                    <div class="controls">
                                        <label class="checkbox">
                                            User consent for marketing campaigns.
                                            <g:checkBox name="consent" disabled="disabled" value="${user?.consent}"/>
                                        </label>
                                    </div>

                                    <%--<label class="control-label"></label>
                                    <div class="controls">
                                        <label class="checkbox">
                                            <g:checkBox disabled="${!ownAccount && !currentUser?.superUser ? "disabled" : "false"}" id="disableHotjar" value="${user?.disableHotjar}" name="disableHotjar"/>
                                            <g:message code="user.disable_hotjar" />
                                        </label>
                                    </div>--%>

                                    <div class="controls">
                                        <label class="checkbox">
                                            <g:checkBox
                                                    disabled="${!ownAccount && !userService.getSuperUser(currentUser) ? "disabled" : "false"}"
                                                    id="enableDevDebugNotifications"
                                                    value="${user?.enableDevDebugNotifications}"
                                                    name="enableDevDebugNotifications"/>
                                            Enable Debug Notifications
                                        </label>
                                    </div>

                                    <div class="controls">
                                        <label class="checkbox">
                                            <g:checkBox
                                                    disabled="${!ownAccount && !userService.getSuperUser(currentUser) ? "disabled" : "false"}"
                                                    id="hideBenchmarkIndicators"
                                                    value="${user?.hideBenchmarkIndicators}"
                                                    name="hideBenchmarkIndicators"/>
                                            Hide benchmark indicators (for internal user)
                                        </label>
                                    </div>
                                </div>
                            </sec:ifAnyGranted>


                            <fieldset>
                                <div class="control-group">
                                    <label class="control-label">
                                        <g:message code="user.list.header.entities"/>
                                    </label>

                                    <div class="controls">
                                        <g:each in="${userService.getAllowedParentEntities(user)}" var="entity">
                                            <g:link controller="entity" action="show"
                                                    params="[entityId: entity.id]">${entity.name ? entity.name + " - Type: " + entity?.entityClass : 'No name' + " - Type: " + entity?.entityClass}</g:link><br/>
                                        </g:each>
                                    </div>
                                </div>
                            </fieldset>

                            <sec:ifAnyGranted roles="ROLE_SUPER_USER">
                                <fieldset>
                                    <div class="control-group">
                                        <label class="control-label">
                                            User licenses
                                        </label>

                                        <div class="controls">
                                            <g:each in="${userService.getUsableAndLicensedLicenses(user)}" var="license">
                                                <opt:link controller="license" action="form"
                                                          params="[id: license.id]">${license.name}</opt:link><br/>
                                            </g:each>
                                        </div>
                                    </div>
                                </fieldset>
                            </sec:ifAnyGranted>


                                <fieldset>
                                    <div class="control-group">
                                        <label class="control-label">
                                            <g:message code="account.title_page"/>
                                        </label>

                                        <div class="controls">
                                            <g:each in="${linkedAccounts}" var="account">
                                                <g:link controller="account" action="form"
                                                        params="[id: account.id]">${account.companyName}</g:link><br/>
                                            </g:each>
                                        </div>
                                    </div>
                                </fieldset>


                            <g:if test="${apiIndicator}">
                                <fieldset>
                                    <div class="control-group">
                                        <div class="controls">
                                            <strong>API Indicator:</strong>${indicatorService.getLocalizedName(apiIndicator)} <span id="long"
                                                                                                               class="fa fa-question-circle"
                                                                                                               style="padding-left: 5px"
                                                                                                               rel="popover"
                                                                                                               data-trigger="hover"
                                                                                                               data-content="This is based on your license. If you wish to use a different indicator for API calculations contact ${emailConfig?.supportEmail}"></span>
                                        </div>
                                    </div>
                                </fieldset>
                            </g:if>
                        </div>

                        <sec:ifAnyGranted roles="ROLE_SALES_VIEW">
                            <div class="column-right">
                                <fieldset>
                                    <g:if test="${user?.emailTokenAuths}">
                                        <div class="control-group">
                                            <label class="control-label">
                                                <strong>User 2-factor authentications</strong>:<br/><br/>
                                                <g:each in="${user.emailTokenAuths}" var="date">
                                                    Authenticated: <g:formatDate date="${date}"
                                                                                 format="dd.MM.yyyy HH:mm"/><br/>
                                                </g:each>
                                            </label>
                                        </div>
                                    </g:if>
                                </fieldset>

                                <fieldset>
                                    <g:if test="${user?.logins}">
                                        <div class="control-group">
                                            <label class="control-label">
                                                <strong>User login information (Latest 20 logins):</strong><br/><br/>
                                                <g:each in="${user.logins}" var="login">
                                                    ${login.toString()}<br/>
                                                </g:each>
                                            </label>
                                        </div>
                                    </g:if>
                                </fieldset>

                                <fieldset>
                                    <g:if test="${user?.securityDatasets}">
                                        <div class="control-group">
                                            <label class="control-label">
                                                User security information<br/>
                                                <g:each in="${user.securityDatasets}">
                                                    ${it.key}: ${it.value}<br/>
                                                </g:each>
                                            </label>
                                        </div>
                                    </g:if>
                                </fieldset>

                                <fieldset>
                                    <g:if test="${user?.userSuspiciousActivity}">
                                        <div class="control-group">
                                            <div>
                                                <sec:ifAnyGranted roles="ROLE_SUPER_USER">
                                                    <label for="clearSuspicious"><b>Clear suspicious activity older than (days):</b>
                                                    </label> <input type="text" id="clearSuspicious"
                                                                    name="clearSuspicious"
                                                                    style="margin-top: 9px; width: 50px;"/>
                                                    <opt:link id="${user?.id?.toString()}"
                                                              action="clearSuspiciousActivity" class="btn btn-danger"
                                                              onclick="return modalConfirm(this, 'clearSuspicious');"
                                                              data-titlestr="${message(code: 'warning')}"
                                                              data-questionstr="Are you sure you want to remove suspicious activity information?"
                                                              data-truestr="${message(code: 'ok')}"
                                                              data-falsestr="${message(code: 'cancel')}">Clear</opt:link> <b
                                                        style="font-size: 1.5em">/</b> <opt:link
                                                        id="${user?.id?.toString()}" params="[clearAll: true]"
                                                        action="clearSuspiciousActivity" class="btn btn-danger"
                                                        onclick="return modalConfirm(this);"
                                                        data-titlestr="${message(code: 'warning')}"
                                                        data-questionstr="Are you sure you want to remove suspicious activity information?"
                                                        data-truestr="${message(code: 'ok')}"
                                                        data-falsestr="${message(code: 'cancel')}">Clear all suspicious activity</opt:link>

                                                </sec:ifAnyGranted>
                                                <g:if test="${user.userSuspiciousActivity.suspiciousActivityClearedBy}">
                                                    <label><b>Suspicious acitivity cleared:</b></label>
                                                    <g:each in="${user.userSuspiciousActivity.suspiciousActivityClearedBy}"
                                                            var="it" status="i">
                                                        <g:formatDate
                                                                date="${new java.text.SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").parse(it.key)}"
                                                                format="dd.MM.yyyy HH:mm:ss"/>: ${it.value}<br/>
                                                    </g:each>
                                                </g:if>
                                            </div>

                                            <label class="control-label">
                                                <h2>User's suspicious activity</h2>

                                                <h3><g:message code="user.duplicate.login"/></h3>
                                                <g:if test="${user.userSuspiciousActivity.loggingInWhenSessionIsValid}">
                                                    <g:each in="${user.userSuspiciousActivity.loggingInWhenSessionIsValid}"
                                                            var="it" status="i">
                                                        <g:formatDate date="${it}" format="dd.MM.yyyy HH:mm:ss"/><br/>
                                                    </g:each>
                                                </g:if>
                                                <g:else>
                                                    None
                                                </g:else>
                                                <h3>1. <g:message code="user.impossible.travel.speed"/></h3>
                                                <g:if test="${user.userSuspiciousActivity.physicallyImpossibleTravelSpeed}">
                                                    <g:each in="${user.userSuspiciousActivity.physicallyImpossibleTravelSpeed}"
                                                            var="it" status="i">
                                                        <g:formatDate
                                                                date="${new java.text.SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").parse(it.key)}"
                                                                format="dd.MM.yyyy HH:mm:ss"/>: From ${it.value[0]} to ${it.value[1]}<br/>
                                                    </g:each>
                                                </g:if>
                                                <g:else>
                                                    None
                                                </g:else>
                                                <h3>2. <g:message code="user.different.login.country"/></h3>
                                                <g:if test="${user.userSuspiciousActivity.loggingInFromAnotherCountry}">
                                                    <g:each in="${user.userSuspiciousActivity.loggingInFromAnotherCountry}"
                                                            var="it" status="i">
                                                        <g:formatDate
                                                                date="${new java.text.SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").parse(it.key)}"
                                                                format="dd.MM.yyyy HH:mm:ss"/>: From ${it.value[0]} to ${it.value[1]}<br/>
                                                    </g:each>
                                                </g:if>
                                                <g:else>
                                                    None
                                                </g:else>
                                                <h3><g:message code="user.different.browser"/></h3>
                                                <g:if test="${user.userSuspiciousActivity.differentBrowser}">
                                                    <g:each in="${user.userSuspiciousActivity.differentBrowser}">
                                                        <g:formatDate
                                                                date="${new java.text.SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").parse(it.key)}"
                                                                format="dd.MM.yyyy HH:mm:ss"/>: Previous ${it.value[0]} to ${it.value[1]}<br/>
                                                    </g:each>
                                                </g:if>
                                                <g:else>
                                                    None
                                                </g:else>
                                            </label>
                                        </div>
                                    </g:if>
                                </fieldset>
                                <fieldset>
                                    <div class="control-group">
                                        <label class="control-label">
                                            <strong>Trial status for user (showing time differences from first project created)</strong>
                                        </label>
                                        <opt:renderTrialStatus userList="${[user]}"/>

                                    </div>
                                </fieldset>
                            </div>
                        </sec:ifAnyGranted>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>

<script>

    function showPersonalData() {
        Swal.fire({
            title: 'Show personal data',
            text: "${development ? "All personal data is obfuscated and can't be processed." : "Processing personal data is only allowed for purposes defined in the Privacy Policy. Please confirm that such purpose is in place. Data processing activity may be subject to audit."}",
            icon: "warning",
            confirmButtonText: "OK",
            confirmButtonColor: "#84bb3c",
            <g:if test="${!development}">
            cancelButtonText: "Cancel",
            showCancelButton: true,
            reverseButtons: true,
            </g:if>
            allowOutsideClick: true
        }).then(result => {
            if (result.value) {
                <g:if test="${!development}">
                $('.personalDataBtn').hide();
                $('.personalData').fadeIn();
                </g:if>
            }
        });
    }
</script>
</body>

</html>
