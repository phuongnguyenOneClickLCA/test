<!doctype html>
<html>
<head>
<meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
    <g:set var="maskingService" bean="maskingService"/>
    <g:set var="indicatorService" bean="indicatorService"/>
    <div class="container">
        <div class="screenheader">
            <h4> <opt:link controller="main" removeEntityId="true"><g:message code="main" /></opt:link> >
                <sec:ifLoggedIn>
                    <g:if test="${parentEntity?.id}">
                        <opt:link controller="entity" action="show" id="${parentEntity?.id}">
                            <g:if test="${entity}">
                                <a href="#" rel="popover" data-trigger="hover" data-content="${parentEntity.name} - ${entity.operatingPeriodAndName}"><g:abbr value="${parentEntity.name}" maxLength="20" /> - <g:abbr value="${entity.operatingPeriodAndName}" maxLength="15" /></a>
                            </g:if>
                            <g:else>
                                <g:abbr value="${parentEntity.name}" maxLength="38" />
                            </g:else>
                        </opt:link>
                    </g:if>
                    <g:elseif test="${entity?.id}">
                        <opt:link controller="entity" action="show" id="${entity?.id}" >
                            <g:if test="${childEntity}">
                                <a href="#" rel="popover" data-trigger="hover" data-content="${entity.name} - ${childEntity.operatingPeriodAndName}"><g:abbr value="${entity.name}" maxLength="20" /> - <g:abbr value="${childEntity.operatingPeriodAndName}" maxLength="15" /></a>
                            </g:if>
                            <g:else>
                                <g:abbr value="${entity.name}" maxLength="38" />
                            </g:else>
                        </opt:link>
                    </g:elseif>
                </sec:ifLoggedIn>
                > <g:message code="entity.users" /> <br/> </h4>
            <g:render template="/entity/basicinfoView"/>
            <h3><g:message code="entity.users" /></h3>
    </div>

    <div class="container section">
        <g:if test="${params.showemail}">
            <p>&nbsp;</p>
            <h4><g:message code="entity.users.register_request.title" /></h4>
            <g:form controller="entity" action="sendRequestToRegister" useToken="true">
                <g:hiddenField name="id" value="${entity?.id}" />
                <div class="control-group">
                    <label for="locale" class="control-label"><g:message code="users.add_user.locale" /></label>
                    <opt:select from="${locales}" name="locale" optionKey="${{it.key}}" optionValue="${{it.value}}" />
                </div>
                <div class="control-group">
                    <label for="message" class="control-label"><g:message code="users.add_user.message" /></label>
                    <opt:textArea entity="${entity}"  name="message" />
                </div>
                <opt:submit entity="${entity}" name="save" value="${message(code: 'entity.request_to_register')}" class="btn btn-primary"/>
            </g:form>
            <p>&nbsp;</p>
        </g:if>

        <div class="sectionbody">
            <div class="control-group" >
                <div>
                    <h4><g:message code="entity.users.add" /></h4>
                    <div class="input-append">
                        <g:form controller="entity" action="addUser" useToken="true">
                            <g:hiddenField name="id" value="${entity?.id}" />

                            <div class="control-group">
                                <label for="email" class="control-label"><g:message code="usersandorganizations.add_user" /></label>
                                <opt:textField entity="${entity}" name="email" />
                            </div>

                            <div class="control-group">
                                <label for="userType" class="control-label"><g:message code="usersandorganizations.add_authorization" /></label>
                                <opt:select entity="${entity}" name="userType" from="${userTypes}" optionValue="${{message(code:it.messageKey)}}" />
                            </div>

                            <opt:submit entity="${entity}" name="save" value="${message(code:'add')}" class="btn btn-primary"/>
                        </g:form>
                    </div>
                </div>
            </div>

            <p style="margin-top: 40px;"><g:message code="usersandorganizations.user_info" /></p>
            <table class="table table-striped table-condensed">
              <tbody>
                <g:each in="${entity?.managers}" var="user">
                  <tr><td>${maskingService.maskSurname(user.name)}</td><td>${maskingService.maskEmail(user.username)}</td><td><g:message code="manager" /></td>
                      <td><g:if test="${user.username.equals(entity.notifiedUsername)}"><g:link action="unsetNotifier" id="${entity?.id}"><g:message code="entity.users.unset.notifier" /></g:link></g:if><g:elseif test="${!entity.notifiedUsername}"><g:link action="setNotifier" id="${entity?.id}" params="[username: user.username]"><g:message code="entity.users.set.notifier" /></g:link></g:elseif></td>
                      <td>&nbsp;</td><td><opt:link controller="entity" action="removeUser" id="${entity?.id}" params="[userId: user.id, userType: 'Manager']" class="btn btn-danger"
                    onclick="return modalConfirm(this);" data-questionstr="${message(code:'usersandorganizations.delete_user', args:[user?.name])}" data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}" data-titlestr="${message(code:'usersandorganizations.delete_user.header')}"><g:message code="delete" /></opt:link></td></tr>
                </g:each>
                <g:each in="${entity?.modifiers}" var="user">
                  <tr><td>${maskingService.maskSurname(user.name)}</td><td>${maskingService.maskEmail(user.username)}</td><td><g:message code="modifier" /></td>
                      <td><g:if test="${user.username.equals(entity.notifiedUsername)}"><g:link action="unsetNotifier" id="${entity?.id}"><g:message code="entity.users.unset.notifier" /></g:link></g:if><g:elseif test="${!entity.notifiedUsername}"><g:link action="setNotifier" id="${entity?.id}" params="[username: user.username]"><g:message code="entity.users.set.notifier" /></g:link></g:elseif></td>
                      <td>
                          <div${!entity?.userIndicators?.get(user?.id?.toString()) ? ' style=\"display:none;\"' : ''} id="modifier${user?.id}">
                              <g:form action="setUserIndicators">
                                  <input type="hidden" name="userId" value="${user?.id}" />
                                  <input type="hidden" name="entityId" value="${entity?.id}" />
                                  <g:each in="${indicators.findAll({!"benchmark".equalsIgnoreCase(it.visibilityStatus)})}" var="indicator">
                                    <input type="checkbox" name="indicatorId" value="${indicator.indicatorId}" ${entity?.userIndicators?.get(user?.id?.toString())?.contains(indicator.indicatorId) ? ' checked=\"checked\"' : ''} /> ${indicatorService.getLocalizedShortName(indicator)}<br />
                                  </g:each>
                                  <g:submitButton name="save" value="${message(code: 'save')}" class="btn btn-primary" />
                              </g:form>
                          </div>
                          <g:if test="${!entity?.userIndicators?.get(user?.id?.toString())}">
                              <a href="javascript:;" onclick="showHideDiv('modifier${user?.id}');"><g:message code="entity.users.limit_rights" /></a>
                          </g:if>
                      </td>
                      <td><opt:link controller="entity" action="removeUser" id="${entity?.id}" params="[userId: user.id, userType: 'Modifier']" class="btn btn-danger"
                            onclick="return modalConfirm(this);" data-questionstr="${message(code:'usersandorganizations.delete_user', args:[user.name])}" data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}" data-titlestr="${message(code:'usersandorganizations.delete_user.header')}"><g:message code="delete" /></opt:link>
                      </td>
                  </tr>
                </g:each>
                <g:each in="${entity?.readonlyUsers}" var="user">
                  <tr>
                      <td>${maskingService.maskSurname(user.name)}</td><td>${maskingService.maskEmail(user.username)}</td>
                      <td><g:message code="readonly" /></td>
                      <td>&nbsp;</td>
                      <td>
                          <div${!entity?.userIndicators?.get(user?.id?.toString()) ? ' style=\"display:none;\"' : ''} id="readonly${user?.id}">
                              <g:form action="setUserIndicators">
                                  <input type="hidden" name="userId" value="${user?.id}" />
                                  <input type="hidden" name="entityId" value="${entity?.id}" />
                                  <g:each in="${indicators.findAll({!"benchmark".equalsIgnoreCase(it.visibilityStatus)})}" var="indicator">
                                      <input type="checkbox" name="indicatorId" value="${indicator.indicatorId}"${entity?.userIndicators?.get(user?.id?.toString())?.contains(indicator.indicatorId) ? ' checked=\"checked\"' : ''} /> ${indicatorService.getLocalizedShortName(indicator)}<br />
                                  </g:each>
                                  <g:submitButton name="save" value="${message(code: 'save')}" class="btn btn-primary" />
                              </g:form>
                          </div>
                          <g:if test="${!entity?.userIndicators?.get(user?.id?.toString())}">
                              <a href="javascript:;" onclick="showHideDiv('readonly${user?.id}');"><g:message code="entity.users.limit_rights" /></a>
                          </g:if>
                      </td>
                      <td><opt:link controller="entity" action="removeUser" id="${entity?.id}" params="[userId: user.id, userType: 'Readonly']" class="btn btn-danger"
                    onclick="return modalConfirm(this);" data-questionstr="${message(code:'usersandorganizations.delete_user', args:[user.name])}" data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}" data-titlestr="${message(code:'usersandorganizations.delete_user.header')}"><g:message code="delete" /></opt:link></td></tr>
                </g:each>
                <g:each in="${entity?.preProvisionedUserRights}">
                    <g:if test="${it.uuid}">
                        <tr><td>${maskingService.maskEmail(it.username)} (invited)</td><td>${it.userRight}</td><td>&nbsp;</td><td>&nbsp;</td><td><opt:link controller="entity" action="removePreProvisionedUser" id="${entity?.id}" params="[uuid: it.uuid]" class="btn btn-danger"
                                                                                                                                                                onclick="return modalConfirm(this);" data-questionstr="${message(code:'usersandorganizations.delete_user', args:[maskingService.maskEmail(it.username)])}" data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}" data-titlestr="${message(code:'usersandorganizations.delete_user.header')}"><g:message code="delete" /></opt:link></td></tr>
                    </g:if>
                    <g:else>
                        <tr><td>${maskingService.maskEmail(it.username)} (invited)</td><td>${it.userRight}</td><td>&nbsp;</td><td>&nbsp;</td><td><opt:link controller="entity" action="removePreProvisionedUser" id="${entity?.id}" params="[username: it.username]" class="btn btn-danger"
                                                                                                                                                                onclick="return modalConfirm(this);" data-questionstr="${message(code:'usersandorganizations.delete_user', args:[maskingService.maskEmail(it.username)])}" data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}" data-titlestr="${message(code:'usersandorganizations.delete_user.header')}"><g:message code="delete" /></opt:link></td></tr>
                    </g:else>
                </g:each>
              </tbody>
            </table>

            <p>&nbsp;</p>
        </div>
      </div>      
	  
      <div class="container section">
          <div class="section body">
               <opt:link action="show" id="${entity?.id}" class="btn"><i class="icon-chevron-left"></i> <g:message code="back" /></opt:link>
          </div>
	</div>
</body>
</html>

