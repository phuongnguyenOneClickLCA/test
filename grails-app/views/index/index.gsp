<!DOCTYPE html>
<html>
<head>
    <% def emailConfig = grailsApplication.mainContext.getBean("emailConfiguration") %>
	<meta charset="utf-8">
	<title><g:message code="index.title" /></title>
   	<link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Droid+Sans:400,700|Open+Sans:400,600">
	<asset:stylesheet src="landing.css" />
    <asset:javascript src="jquery-3.6.0.min.js"/>
<%--<asset:javascript src="jquery-migrate-3.1.0.js"/>--%>

    <g:if test="${customStylingEnabled}">
    <style>
    html, body {
        position: relative;
        margin: 0;
        border: 0;
        padding: 0;
        width: 100%;
        height: 100%;
        overflow: hidden;
    }
    body {
        background-image:    url('${channel?.backgroundImage}');
        background-size:     cover;                      /* <------ */
        background-repeat:   no-repeat;
        background-position: center center;              /* optional, center the image */
        overflow: auto;
    }
    .redWarningColor {
        color: red;
        font-size: 18px;
    }
    ${channel?.loginPageCss}
    </style>
</g:if><g:elseif test="${account}">
    <style>
    body {
        <opt:base64BackgroundImage imageSource="${account?.backgroundImage}"/>
    }
    a {
        color:${account.fontColor ? account.fontColor : '#43A143'};
    }
    #loginForm {
        background-color:${account.loginBoxColor ? account.loginBoxColor  : '#fafafa'};
        border-color:${account.loginBoxColor ? account.loginBoxColor : '#ebebeb'};
    }
    .login input[type="submit"] {
        background-color:  ${account.submitButtonColor ? account.submitButtonColor : '#8BC34A'};
    }
    .redWarningColor {
        color: red;
        font-size: 18px;
    }
    </style>
</g:elseif>
</head>
  <body>
  <g:set var="localizedLinkService" bean="localizedLinkService"/>
      <div class="content container"  id="landingpage">
          <div class="container" id="messageContent">
              <opt:internetExplorerWarning index="${true}"/>

          %{-- Special condition alerts  --}%
              <g:if test="${prodWarning}">
                  <div class="alert alert-error hide-on-print">
                      <button type="button" class="close" data-dismiss="alert" style="top: 0px;">×</button>
                      <strong>${raw(prodWarning)}</strong>
                  </div>
              </g:if>

              <g:hasErrors bean="${entity}">
                  <div class="alert alert-danger hide-on-print">
                      <button data-dismiss="alert" class="close" type="button" style="top: 0px;">×</button>
                      <g:renderErrors bean="${entity}"/>
                  </div>
              </g:hasErrors>

              <g:hasErrors bean="${mongoObject}">
                  <div class="alert alert-danger hide-on-print">
                      <button data-dismiss="alert" class="close" type="button" style="top: 0px;">×</button>
                      <g:renderErrors bean="${mongoObject}"/>
                  </div>
              </g:hasErrors>

          %{-- Possibly useless as I can't see any code calls for this but I can see session attribute calls, which might call this, so leaving it in just incase --}%
              <g:if test="${request.calculationError}">
                  <div class="alert alert-error hide-on-print" >
                      <button data-dismiss="alert" class="close" type="button">×</button>
                      <strong>${raw(request.calculationError)}</strong>
                  </div>
              </g:if>

              <g:if test="${flash.userActivationError}">
                  <div class="alert alert-error fadetoggle hide-on-print" data-fadetoggle-delaytime="30000">
                      <button data-dismiss="alert" class="close" type="button" style="top: 0px;">×</button>
                      <strong>${raw(flash.userActivationError)}</strong>
                  </div>
              </g:if>

              <g:if test="${session?.importIncomplete}">
                  <div class="alert hide-on-print">
                      <button data-dismiss="alert" class="close" type="button" style="top: 0px;">×</button>
                      <strong>Import incomplete. Please run the ResourceSubType Data benchmarks.</strong>
                  </div>
              </g:if>

          %{-- Fade Alerts --}%
              <g:if test="${flash.fadeSaveAlert}">
                  <div class="alert alert-success fadetoggle hide-on-print" data-fadetoggle-delaytime="7000">
                      <i class="far fa-save pull-left" style="font-size: large; margin-right: 8px;"></i>
                      <i data-dismiss="alert" class="fas fa-circle-notch fa-spin close" style="top: 0px;"></i>
                      <strong>${raw(flash.fadeSaveAlert)}</strong>
                  </div>
              </g:if>
              <g:if test="${flash.fadeSuccessAlert}">
                  <div class="alert alert-success fadetoggle hide-on-print" data-fadetoggle-delaytime="7000">
                      <i class="far fa-thumbs-up pull-left" style="font-size: large; margin-right: 8px;"></i>
                      <i data-dismiss="alert" class="fas fa-circle-notch fa-spin close" style="top: 0px;"></i>
                      <strong>${raw(flash.fadeSuccessAlert)}</strong>
                  </div>
              </g:if>
              <g:if test="${flash.fadeInfoAlert}">
                  <div class="alert alert-info fadetoggle hide-on-print" data-fadetoggle-delaytime="7000">
                      <i class="fas fa-info pull-left" style="font-size: large; margin-right: 8px;"></i>
                      <i data-dismiss="alert" class="fas fa-circle-notch fa-spin close" style="top: 0px;"></i>
                      <strong>${raw(flash.fadeInfoAlert)}</strong>
                  </div>
              </g:if>
              <g:if test="${flash.fadeWarningAlert}">
                  <div class="alert alert-warning fadetoggle hide-on-print" data-fadetoggle-delaytime="7000">
                      <i class="fas fa-exclamation pull-left" style="font-size: large; margin-right: 8px;"></i>
                      <i data-dismiss="alert" class="fas fa-circle-notch fa-spin close" style="top: 0px;"></i>
                      <strong>${raw(flash.fadeWarningAlert)}</strong>
                  </div>
              </g:if>
              <g:if test="${flash.fadeErrorAlert}">
                  <div class="alert alert-error fadetoggle hide-on-print" data-fadetoggle-delaytime="7000">
                      <i class="fas fa-times pull-left" style="font-size: large; margin-right: 8px;"></i>
                      <i data-dismiss="alert" class="fas fa-circle-notch fa-spin close" style="top: 0px;"></i>
                      <strong>${raw(flash.fadeErrorAlert)}</strong>
                  </div>
              </g:if>

          %{-- Standard Alerts - with closable button --}%
              <g:if test="${flash.saveAlert}">
                  <div class="alert alert-success hide-on-print">
                      <i class="far fa-save pull-left" style="font-size: large; margin-right: 8px;"></i>
                      <button data-dismiss="alert" class="close" style="top: 0px;" type="button">×</button>
                      <strong>${raw(flash.saveAlert)}</strong>
                  </div>
              </g:if>
              <g:if test="${flash.successAlert}">
                  <div class="alert alert-success hide-on-print">
                      <i class="far fa-thumbs-up pull-left" style="font-size: large; margin-right: 8px;"></i>
                      <button data-dismiss="alert" class="close" style="top: 0px;" type="button">×</button>
                      <strong>${raw(flash.successAlert)}</strong>
                  </div>
              </g:if>
              <g:if test="${flash.infoAlert}">
                  <div class="alert alert-info hide-on-print">
                      <i class="fas fa-info pull-left" style="font-size: large; margin-right: 8px;"></i>
                      <button data-dismiss="alert" class="close" style="top: 0px;" type="button">×</button>
                      <strong>${raw(flash.infoAlert)}</strong>
                  </div>
              </g:if>
              <g:if test="${flash.warningAlert}">
                  <div class="alert alert-warning hide-on-print">
                      <i class="fas fa-exclamation pull-left" style="font-size: large; margin-right: 8px;"></i>
                      <button data-dismiss="alert" class="close" style="top: 0px;" type="button">×</button>
                      <strong>${raw(flash.warningAlert)}</strong>
                  </div>
              </g:if>
              <g:if test="${flash.errorAlert}">
                  <div class="alert alert-error hide-on-print">
                      <i class="fas fa-times pull-left" style="font-size: large; margin-right: 8px;"></i>
                      <button data-dismiss="alert" class="close" style="top: 0px;" type="button">×</button>
                      <strong>${raw(flash.errorAlert)}</strong>
                  </div>
              </g:if>

          %{-- Permanent Alerts - with no closable options --}%
              <g:if test="${flash.permSaveAlert}">
                  <div class="alert alert-success hide-on-print">
                      <i class="far fa-save pull-left" style="font-size: large; margin-right: 8px;"></i>
                      <strong>${raw(flash.permSaveAlert)}</strong>
                  </div>
              </g:if>
              <g:if test="${flash.permSuccessAlert}">
                  <div class="alert alert-success hide-on-print">
                      <i class="far fa-thumbs-up pull-left" style="font-size: large; margin-right: 8px;"></i>
                      <strong>${raw(flash.permSuccessAlert)}</strong>
                  </div>
              </g:if>
              <g:if test="${flash.permInfoAlert}">
                  <div class="alert alert-info hide-on-print">
                      <i class="fas fa-info pull-left" style="font-size: large; margin-right: 8px;"></i>
                      <strong>${raw(flash.permInfoAlert)}</strong>
                  </div>
              </g:if>
              <g:if test="${flash.permWarningAlert}">
                  <div class="alert alert-warning hide-on-print">
                      <i class="fas fa-exclamation pull-left" style="font-size: large; margin-right: 8px;"></i>
                      <strong>${raw(flash.permWarningAlert)}</strong>
                  </div>
              </g:if>
              <g:if test="${flash.permErrorAlert}">
                  <div class="alert alert-error hide-on-print">
                      <i class="fas fa-times pull-left" style="font-size: large; margin-right: 8px;"></i>
                      <strong>${raw(flash.permErrorAlert)}</strong>
                  </div>
              </g:if>
          </div>
          <div class="login ${helpPageBullets ? "maxwidth800" : ''}">
                  <div class="formContainer">
              <form method="post" action="${postUrl}" style="margin-bottom: 10px;" id="loginForm" name="loginForm">
                  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                  <div class="center">
                      <div class="logos">
                          <div class="logo">
                              <g:if test="${channel}"><img
                                      src="${channel?.logo}"/></g:if><g:else>
                              <asset:image src="OCL_logo.png"/>
                          </g:else>
                          </div>
                          <div class="logo">
                              <g:if test="${account?.branding}">
                                  <opt:displayDomainClassImage imageSource="${account?.branding}" id="brandingImage" width="200px" height="40px"/>
                              </g:if>
                          </div>
                      </div>
                  </div>
                  <g:hiddenField name="accountKey" value="${account?.loginKey}"/>
                  <g:hiddenField name="channelToken" value="${channelToken}" />
                  <g:hiddenField name="language" value="${language}"/>
                  <div class="group">
                      <span class="labelinputs">${message(code: 'user.username')}</span>
                      <input type="email" class="inputs" value="${populateUserName ? populateUserName : ''}" name="username" id="username"><span class="highlight"></span><span
                          class="bar emailGroup"></span>
                  <opt:link  action="form"><g:message code="index.new_user"/>!</opt:link>

              </div>
                  <div class="passwordGroup">
                      <span class="labelinputs">${message(code: 'user.password')}</span>
                      <input type="password" name="password" class="inputs" id="password"><span class="highlight"></span><span class="bar"></span></div>
                  <a href="javascript:" class="" onclick="forgotPassword();"><g:message code="index.forgot_password"/></a>
          <input type="submit" class="left btn btn-primary inputs" value="${message(code: 'index.login')}" onclick="doubleSubmitPrevention('loginForm')"/>
                  <input type="checkbox" name="${rememberMeParameter}" id="remember_me" <g:if test='${hasCookie}'>checked='checked'</g:if>/><g:message code="index.login.remember_me"/>
                  <br />
                  &nbsp;
                  <table style="white-space: nowrap;">
                      <tr>
                          <td><opt:link controller="index" class="leftMostFlag" params="[lang:'en',channelToken: channelToken, accountKey: account?.loginKey, applicationId: applicationId]"><asset:image src="flags/flag_en.jpg" alt="360&deg; Optimi" /> English</opt:link></td>
                          <td><opt:link controller="index" class="flex-flag" params="[lang:'fr',channelToken: channelToken, accountKey: account?.loginKey, applicationId: applicationId]"><asset:image src="flags/flag_fr.png" alt="360&deg; Optimi" /> Français</opt:link></td>
                          <td><opt:link controller="index" class="flex-flag" params="[lang:'de',channelToken: channelToken, accountKey: account?.loginKey,applicationId: applicationId]"><asset:image src="flags/flag_de.png" alt="360&deg; Optimi" /> Deutsch</opt:link></td>
                      </tr>
                      <tr>
                          <td><opt:link controller="index" class="leftMostFlag" params="[lang:'es',channelToken: channelToken, accountKey: account?.loginKey,applicationId: applicationId]"><asset:image src="isoflags/es.png" style="width:20px; height:12px;" alt="360&deg; Optimi" /> Español</opt:link></td>
                          <td><opt:link controller="index" class="flex-flag" params="[lang:'fi',channelToken: channelToken, accountKey: account?.loginKey, applicationId:applicationId]"><asset:image src="flags/flag_fi.jpg" alt="360&deg; Optimi" /> Suomi</opt:link></td>
                          <td><opt:link controller="index" class="flex-flag" params="[lang:'no',channelToken: channelToken, accountKey: account?.loginKey,applicationId: applicationId]"><asset:image src="isoflags/no.png" style="width:20px; height:12px;" alt="360&deg; Optimi" /> Norsk</opt:link></td>
                      </tr>
                      <tr>
                          <td><opt:link controller="index" class="leftMostFlag" params="[lang:'nl',channelToken: channelToken, accountKey: account?.loginKey,applicationId: applicationId]"><asset:image src="isoflags/nl.png" style="width:20px; height:12px;" alt="360&deg; Optimi" /> Nederlands</opt:link></td>
                          <td><opt:link controller="index" class="flex-flag" params="[lang:'se',channelToken: channelToken, accountKey: account?.loginKey,applicationId: applicationId]"><asset:image src="isoflags/se.png" style="width:20px; height:12px;" alt="360&deg; Optimi" /> Svenska</opt:link></td>
                          <td><opt:link controller="index" class="flex-flag" params="[lang:'it',channelToken: channelToken, accountKey: account?.loginKey,applicationId: applicationId]"><asset:image src="isoflags/it.png" style="width:20px; height:12px;" alt="360&deg; Optimi" /> Italiano</opt:link></td>
                      </tr>
                      <tr>
                          <td><opt:link controller="index" class="leftMostFlag" params="[lang:'hu',channelToken: channelToken, accountKey: account?.loginKey,applicationId: applicationId]"><asset:image src="isoflags/hu.png" style="width:20px; height:12px;" alt="360&deg; Optimi" /> Magyar</opt:link></td>
                          <td><opt:link controller="index" class="leftMostFlag" params="[lang:'jp',channelToken: channelToken, accountKey: account?.loginKey,applicationId: applicationId]"><asset:image src="isoflags/jp.png" style="width:20px; height:12px;" alt="360&deg; Optimi" /> 日本</opt:link></td>
                      </tr>
                  </table>
              </form>
              <div class="forgotpassword hidden">
                  <div class="triangle-container">
                      <a href="javascript:" class="login-triangle" onclick="forgotPassword();"></a>
                  </div>
                  <div class="container">
                      <g:form controller="passwordReset" action="sendResetPasswordMail">
                          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                          <div>
                              <g:message code="forgotpassword.email" />
                              <g:textField name="email" class="left" />
                              <opt:submit name="submit" value="${message(code:'send')}" class="left btn btn-primary" />
                              <a href="javascript:" class="left btn"  onclick="forgotPassword();"><g:message code="cancel" /></a>
                          </div>
                      </g:form>
                  </div>
              </div>
                  </div>
              <g:if test="${helpPageBullets}">
                  <div class="helpPageBullets">
                      <h2>First time here? Read on!</h2>
                      <g:if test="${helpPageText}">
                          <div class="helpPageText">${abbr(maxLength: 200, value: helpPageText)}</div>
                            </g:if>
                      <ul>
                          <g:each in="${helpPageBullets}" var="bullet">
                              <g:if test="${emailConfig?.supportEmail?.equals(bullet.value)}">
                                  <li class="supportMailLink">${bullet.key}  <a href="mailto:${bullet.value}?Subject=Support%20request%20">${bullet.value}</a></li>
                              </g:if><g:else>
                              <li><a href="${localizedLinkService.getTransformStringIdsToUrl(bullet.value)}">${bullet.key}</a></li>
                          </g:else>
                          </g:each>
                      </ul>
                  </div>
              </g:if>
              <div class="clearAll"></div>
          </div>
        <g:if test="${channelFeaturesForAnotherApplicationLogin.size() > 1 && channelFeaturesForAnotherApplicationLogin && !account}">
            <div class="otherApps"> <p class="bodyText"><g:message code="index.another_application"/></p></div>
            <div class="flex-container" id="channels">
              <g:set var="currentUrl" value="${request?.getRequestURL()}" />
              <g:each in="${channelFeaturesForAnotherApplicationLogin}" var="channelFeature">
                  <div class="flex-item">
                      <g:if test="${!channelFeature?.equals(channel)}">
                      <a  href="${channelFeature?.loginUrl ? channelFeature.loginUrl : currentUrl + "?&channelToken=" + channelFeature?.token}"><img src="${channelFeature.logo}"/></a>
                      </g:if>
                  </div>
              </g:each>
          </div>
        </g:if>
      </div>


  <script type="text/javascript">
      var myFormSubmitted = false;

      $(function() {
          $('input[name="username"]').focus()
      })

      function forgotPassword() {
          var passwordForgetBox = $('.forgotpassword');
          if ($(passwordForgetBox).is(":hidden")) {
              $(passwordForgetBox).slideDown("slow");
              $(passwordForgetBox).removeClass('hidden');
              $('html,body').animate({
                      scrollTop: $(passwordForgetBox).offset().top
                  },
                  'slow');

          }else {
            $(passwordForgetBox).slideUp( "slow" );
          }
      }

      function doubleSubmitPrevention(formId) {
          var myForm = document.getElementById(formId);
          var myForms = this.document.forms;

          myForm.onsubmit = function () {
              var myForms_length = myForms.length;

              for (var i = 0; i < myForms_length; i++) {
                  var elements = myForms[i].elements;
                  var elements_length = elements.length;

                  for (var j = 0; j < elements_length; j++) {
                      var element = elements[j];

                      if (element.type == "submit" || element.type == "button" || element.type == "reset") {
                          if (myFormSubmitted) {
                              element.disabled = true;
                          }
                      }
                  }
              }

              if (myFormSubmitted) {
                  return false;
              }
              myFormSubmitted = true;
              return true;
          };
      }
  </script>
  </body>
</html>

