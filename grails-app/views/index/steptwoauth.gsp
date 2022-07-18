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
        body {
            background: url('${channel?.backgroundImage}') no-repeat center center fixed;
        }
        ${channel?.loginPageCss}
        </style>
    </g:if>
</head>
<body>
<div class="content container" id="landingpage">
    <div class="container" id="messageContent">
        <g:if test="${flash.fadeSuccessAlert}">
            <div class="alert alert-success fadetoggle hide-on-print" data-fadetoggle-delaytime="10000">
                <i data-dismiss="alert" class="fas fa-circle-notch fa-spin close" style="top: 0px;"></i>
                <strong>${raw(flash.fadeSuccessAlert)}</strong>
            </div>
        </g:if>
        <g:if test="${flash.fadeErrorAlert}">
            <div class="alert alert-error fadetoggle hide-on-print" data-fadetoggle-delaytime="7000">
                <i data-dismiss="alert" class="fas fa-circle-notch fa-spin close" style="top: 0px;"></i>
                <strong>${raw(flash.fadeErrorAlert)}</strong>
            </div>
        </g:if>
    </div>
    <div class="login">
        <div class="formContainer">
            <g:form method="post" controller="index" action="authenticateByEmailToken" style="margin-bottom: 10px;">
                <div class="center">
                    <div class="logos">
                        <div class="logo">
                            <g:if test="${channel}"><img
                                    src="${channel?.logo}"/></g:if><g:else>
                            <asset:image src="OCL_logo.png"/>
                        </g:else>
                        </div>
                    </div>
                </div>
                <div class="group">
                    <h2>${message(code: 'twostepauth.heading')}</h2>
                    <input type="password" class="inputs" name="email_token_response" id="email_token_response"><span class="highlight"></span><span
                        class="bar emailGroup"></span>
                    <span class="labelinputs">${message(code: 'twostepauth.body1')}</span>
                    <input type="submit" name="submit" class="left btn btn-primary inputs" style="margin-bottom: 15px;" onclick="clickAndDisableAuth('inputs');" value="${message(code: 'index.login')}"/>
                    <span class="labelinputs">${message(code: 'twostepauth.body2')} <a href="mailto:${emailConfig?.supportEmail}">${emailConfig?.supportEmail}</a>.</span>
                    <input type="submit" name="resend" class="left btn btn-primary inputs" style="margin-bottom: 15px;" onclick="clickAndDisableAuth('inputs');" value="${message(code: 'twostepauth.resend')}"/>
                    <span >${message(code: 'twostepauth.body3')}</span>

                </div>
            </g:form>
        </div>
        <div class="clearAll"></div>
    </div>
</div>
</body>
<script>
    function clickAndDisableAuth(linkClass) {
        var links = $("." + linkClass);
        if (links.length) {
            links.prop("onclick", null).off("click");
            links.css('pointer-events', 'none');
        }
    }
</script>
</html>

