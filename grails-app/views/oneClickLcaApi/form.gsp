<!doctype html>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <h1>
            API emulator
        </h1>
    </div>
</div>
<div class="container section">
    <sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN">
        <div class="column_left" style="margin-right: 20px;">
            <div>
                <h2>Create BearerToken</h2>
                <g:form name="hyväform">

                    <div class="clearfix"></div>
                    <table class="datasummary">
                        <tr><th>username</th></tr>
                        <tr><td><g:textField name="username" value="" required="required"/></td></tr>
                        <tr><th>password</th></tr>
                        <tr><td><g:passwordField name="password" value="" required="required"/></td></tr>
                    </table>
                    <a name="import" id="postCreditsAsJSON" class="btn btn-primary">Login</a>
                </g:form>
            </div>
        </div>
        <div class="column_left bordered">
            <h2>Post file</h2>
        <g:uploadForm controller="importMapper" action="postFile" method="POST">
            <g:hiddenField name="emulation" value="${true}"/>
            <div class="clearfix"></div>

                <div class="control-group">
                    <label for="file" class="control-label"><g:message code="admin.import.excel_file" /></label>
                    <div class="controls"><input type="file" name="importFile" id="file" class="btn" value="" required/></div>
                </div>

                <div class="clearfix"></div>
                <table class="datasummary">
                    <tr><th><label for="fileTokenPost" class="control-label"><strong>FileToken</strong></label></th></tr>
                    <tr><td><input type="text" id="fileTokenPost" name="fileToken" value="testToken${user?.name?.replaceAll(" ","")}" required="required"></td></tr>
                    <tr><th>securityToken</th></tr>
                    <tr><td><g:select name="securityToken" value="" from="${licensedApiImports}" required="required"/></td></tr>
                    <tr><th>bearerToken</th></tr>
                    <tr><td><g:textField name="bearerToken" value="" /></td></tr>
                    <tr><th>APICalculation</th></tr>
                    <tr><td><g:checkBox name="APICalculation" id="APICalculation" value="true" checked="true" style="width: 25px; !important; height: 17px; !important;"/></td></tr>
                    <tr><th>APIResponse</th></tr>
                    <tr><td><g:textField name="APIResponse" value="true" /></td></tr>
                    <tr><th>APIIndicator</th></tr>
                    <tr><td><g:textField name="APIIndicator" value="" /></td></tr>
                    <tr><th>APINoCombine</th></tr>
                    <tr><td><g:checkBox name="APINoCombine" id="APINoCombine" value="true" checked="true" style="width: 25px; !important; height: 17px; !important;"/></td></tr>
                    <tr><th>APINoFiltering</th></tr>
                    <tr><td><g:checkBox name="APINoFiltering" id="APINoFiltering" value="true" checked="true" style="width: 25px; !important; height: 17px; !important;"/></td></tr>
                    <tr><th>importMapperId</th></tr>
                    <tr><td><g:textField name="importMapperId" value="IFCFromSimpleBIM" /></td></tr>
                    <tr><th>applicationId</th></tr>
                    <tr><td><g:textField name="applicationId" value="IFC" /></td></tr>
                </table>
                <opt:submit name="import" value="${message(code:'import')}" class="btn btn-primary"/>
        </g:uploadForm>
        </div>
        <div class="column_right bordered">
            <h2>Get file</h2>
            <g:form controller="importMapper" action="importResults" method="GET">
                <div class="clearfix"></div>
                <table class="datasummary">
                    <tr><th>FileToken</th></tr>
                    <tr><td><g:textField name="fileToken" value="testToken${user?.name?.replaceAll(" ","")}" required="required"/></td></tr>
                    <tr><th>securityToken</th></tr>
                    <tr><td><g:select name="securityToken" value="" from="${licensedApiImports}" required="required"/></td></tr>
                </table>
                <opt:submit name="get" value="Get file" class="btn btn-primary"/>
            </g:form>
        </div>
        <div class="column_right bordered">
            <h2>Multipart to importmapper index</h2>
            <g:uploadForm controller="importMapper" action="index" method="POST">
                <div class="clearfix"></div>

                <div class="control-group">
                    <label for="file" class="control-label"><g:message code="admin.import.excel_file" /></label>
                    <div class="controls"><input type="file" name="importFile" id="importFile" class="btn" value=""/></div>
                </div>

                <div class="clearfix"></div>
                <table class="datasummary">
                    <tr><th>ApplicationId</th></tr>
                    <tr><td><input type="text" name="applicationId" value="IFC"></td></tr>
                    <tr><th>ImportMapperId</th></tr>
                    <tr><td><input type="text" name="importMapperId" value="IFCFromSimpleBIM"></td></tr>
                    <tr><th>ImportFilePath (Either file or this)</th></tr>
                    <tr><td><input type="text" name="importFilePath" placeholder="Server response from POST" value=""></td></tr>
                </table>
                <input type="submit" name="import" value="${message(code:'import')}" class="btn btn-primary">
            </g:uploadForm>
        </div>
    </sec:ifAllGranted>
    <div class="clearfix"></div>

</div>
<script type="text/javascript">

    $('#postCreditsAsJSON').on('click', function (event) {
        var username = $('#username').val();
        var password = $('#password').val();

        var nameAndPassword = {};
        nameAndPassword["username"] = username;
        nameAndPassword["password"] = password;

        $.ajax({
            type: "POST",
            async: true,
            contentType: "application/json",
            url: "/app/api/login",
            data: JSON.stringify(nameAndPassword),
            success: function (data) {
                if(data && data.hasOwnProperty('access_token')) {
                    document.getElementById("bearerToken").value = data.access_token.replace(/(^"|"$)/g, '');
                }
                alert("token in console (F12)");
                console.log(data);
            }
        });
    });
</script>
</body>
</html>

