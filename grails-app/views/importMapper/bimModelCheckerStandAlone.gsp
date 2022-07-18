<%-- Copyright (c) 2012 by Bionova Oy --%>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
    <g:hiddenField name="entityId" value="${entityId}" />
    <g:hiddenField name="childEntityId" value="${childEntityId}" />
    <div class="container">
        <g:if test="${warningMessage}">
            <div class="alert alert-warning">
                <button data-dismiss="alert" class="close" type="button">×</button>
                <strong>${warningMessage}</strong>
            </div>
        </g:if>
        <div class="screenheader">
            <h4> <opt:link controller="main" removeEntityId="true"><g:message code="main" /></opt:link>
                <sec:ifLoggedIn>
                    <g:if test="${parentEntity}">
                        > <opt:link controller="entity" action="show" id="${parentEntity?.id}">
                        <g:abbr value="${parentEntity?.name}" maxLength="20" />
                    </opt:link>
                        <g:if test="${entity}">
                            > <span id="childEntityName">${entity?.operatingPeriodAndName}</span>
                        </g:if>
                    </g:if>
                </sec:ifLoggedIn>
                > <g:message code="import_data" /> <br/> </h4>
            <h2 class="h2expander"> <i class="fas fa-file-alt oneClickColorScheme"></i> <g:message code="bim_checker_heading" /> -- <span id="bimHeading"></span></h2>
            <div class="btn-group pull-right">
                <g:link controller="importMapper" action="cancel" class="btn" style="font-weight: normal;"><g:message code="back"/></g:link>
                <g:link controller="entity" action="show" class="btn btn-primary" params="[entityId:entityId]"><g:message code="main"/></g:link>
            </div>
        </div>
    </div>

    <div class="container" id="bimCheckerContent">

    </div>

<script type="text/javascript">
    $(document).ready(function() {
            appendTemplateSecure('bimCheckerContent', '${bimcheckerUrl}');
        });
</script>
</body>
</html>



