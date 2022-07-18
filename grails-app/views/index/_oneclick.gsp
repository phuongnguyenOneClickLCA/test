<div class="content" id="landingpage">

    <div class="login">
       <span class="logo"> <asset:image src="logo.png"/></span>
        <div class="container">Description text about channel here</div>
        <form method="post" action="${postUrl}">
            <div class="group">
                <span class="labelinputs">${message(code: 'user.username')}</span>

                <input type="email" class="inputs" name="username" id="username"><span class="highlight"></span><span
                    class="bar"></span>
            </div>
            <div class="group">
                <span class="labelinputs">${message(code: 'user.password')}</span>
                <input type="password" name="password" class="inputs" id="password"><span class="highlight"></span><span class="bar"></span></div>
                <input type="submit" class="left btn btn-primary inputs" value="${message(code: 'index.login')}"/>
                <input type="checkbox" name="${rememberMeParameter}" id="remember_me" <g:if test='${hasCookie}'>checked='checked'</g:if>/><g:message code="index.login.remember_me"/>
            <opt:link class="pull-right" action="form"><g:message code="index.register"/></opt:link>
            <a href="#" class="pull-right" data-toggle=".forgotpassword"><g:message code="index.forgot_password"/></a>
        </form>
    </div>
</div>