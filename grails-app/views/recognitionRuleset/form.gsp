<%-- Copyright (c) 2012 by Bionova Oy --%>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <g:render template="/entity/basicinfoView"/>
    </div>
</div>

<div class="container">
    <div class="screenheader"><h1>Add ruleset</h1></div>
</div>

<div class="container section">
    <div class="sectionbody">
        <g:uploadForm action="save" useToken="true" name="taskForm">
            <g:hiddenField name="id" value="${recognitionRulesetId}"/>
            <div class="row-fluid row">
                <div class="span4">
                    <h2>Ruleset information</h2>

                    <div class="control-group">
                        <label for="type" class="control-label">Type</label>
                        <div class="controls">
                            <g:if test="${rulesetTypes}">
                                <g:select name="type" from="${rulesetTypes}" optionKey="${{it}}" optionValue="${{it}}" value="${recognitionRuleset?.type}" noSelection="['' : '' ]"/>
                            </g:if>
                        </div>
                    </div>

                    <div class="control-group">
                        <label for="name" class="control-label">Name</label>
                        <div class="controls">
                            <opt:textField name="name" value="${recognitionRuleset?.name}" class="input-xlarge"/>
                        </div>
                    </div>

                    <div class="control-group">
                        <label for="accountId" class="control-label">Account</label>
                        <div class="controls">
                            <g:if test="${allAccounts}">
                                <g:select name="accountId" from="${allAccounts}" optionKey="${{it.id}}" optionValue="${{it.companyName}}" value="${recognitionRuleset?.accountId}" noSelection="['' : '' ]"/>
                            </g:if>
                        </div>
                    </div>

                    <div class="control-group">
                        <label for="country" class="control-label"><g:message code="account.country" /></label>
                        <div class="controls">
                            <g:if test="${countries}">
                                <g:select name="country" from="${countries}" optionKey="${{it.isoCountryCode ? it.isoCountryCode : ''}}" optionValue="${{it.localizedName}}" value="${recognitionRuleset?.country}" noSelection="['' : '' ]"/>
                            </g:if>
                        </div>
                    </div>

                    <div class="control-group">
                        <label for="importMapperId" class="control-label">ImportMapper</label>
                        <div class="controls">
                            <g:if test="${importMapperIds}">
                                <g:select name="importMapperId" from="${importMapperIds}" optionKey="${{it}}" optionValue="${{it}}" value="${recognitionRuleset?.importMapperId}" noSelection="['' : '' ]"/>
                            </g:if>
                        </div>
                    </div>

                    <div class="control-group">
                        <label for="applicationId" class="control-label">Application</label>
                        <div class="controls">
                            <g:if test="${applicationIds}">
                                <g:select name="applicationId" from="${applicationIds}" optionKey="${{it}}" optionValue="${{it}}" value="${recognitionRuleset?.applicationId}" noSelection="['' : '' ]"/>
                            </g:if>
                        </div>
                    </div>
                    <opt:submit name="save" value="${message(code:'save')}" class="btn btn-primary" />
                    <opt:link action="list" class="btn"><g:message code="cancel" /></opt:link>
                </div>
            </div>
        </g:uploadForm>
    </div>
</div>

<script type="text/javascript">

</script>
</body>
</html>



