<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="format-detection" content="telephone=no"/>
</head>

<body>
<div class="container">
    <div class="screenheader">
        <h1><g:message code="admin.dataSummary"/> (${indicators?.findAll({it.active}) ? indicators?.findAll({it.active})?.size() : 0})</h1>
    </div>
</div>

<div class="container section">
    <div class="sectionbody">
    <sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN">
        <g:uploadForm action="importIndicator" accept-charset="UTF-8" name="importIndicator">
            <div class="column_left">
                <div class="control-group">
                    <label for="indicatorFile" class="control-label"><g:message code="admin.dataSummary.choose.json"/></label>

                    <div class="controls"><input type="file" name="indicatorFile" id="indicatorFile" class="btn"
                                                 value=""/></div>
                </div>
            </div>

            <div class="clearfix"></div>
            <opt:submit name="import" onclick="doubleSubmitPrevention('importIndicator');" value="${message(code: 'import')}" class="btn btn-primary"/>
            <%--
            <g:actionSubmit value="${message(code: 'cancel')}" action="reset" class="btn"/>
            --%>
        </g:uploadForm>
        <input type="button" class="btn btn-primary" id="showHideHidden" value="${message(code: 'admin.dataSummary.showDisabled')}" onclick="displayTrClass('hiddenItem', 'showHideHidden', '${message(code: 'admin.dataSummary.hideDisabled')}', '${message(code: 'admin.dataSummary.showDisabled')}');" />
    </sec:ifAllGranted>

    <g:if test="${grails.util.Environment.current == grails.util.Environment.PRODUCTION}">
        <sec:ifAnyGranted roles="ROLE_SUPER_USER">
            <div style="color: red; margin-top: 40px; margin-bottom: -20px;"><strong>PROD: "See use" can use a lot of memory, make sure you open one at a time and not during high load</strong></div>
        </sec:ifAnyGranted>
    </g:if>

    <g:if test="${validIndicators}">
        <br><br>
        <h3>Valid Indicators</h3>
        <g:render template="/dataSummary/tableIndicators" model="[indicators: validIndicators]"/>
    </g:if>

    <g:if test="${deprecatedIndicators}">
        <br><br>
        <h3>Deprecated Indicators</h3>
        <g:render template="/dataSummary/tableIndicators" model="[indicators: deprecatedIndicators]"/>
    </g:if>

    </div>
</div>
</body>
</html>

