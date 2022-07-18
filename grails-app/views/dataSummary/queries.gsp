<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="format-detection" content="telephone=no"/>
</head>

<body>
<div class="container">
    <div class="screenheader">
        <h1><g:message code="admin.dataSummary.queries"/> (${queries?.find({it.active}) ? queries?.findAll({it.active})?.size() : 0})</h1>
    </div>
</div>

<div class="container section">
    <div class="sectionbody">
        <sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN">
        <g:uploadForm action="importQuery" accept-charset="UTF-8">
            <div class="column_left">
                <div class="control-group">
                    <label for="queryFile" class="control-label"><g:message code="admin.dataSummary.choose.json"/></label>

                    <div class="controls"><input type="file" name="queryFile" id="queryFile" class="btn" value=""/>
                    </div>
                </div>
            </div>

            <div class="clearfix"></div>
            <opt:submit name="import" value="${message(code: 'import')}" class="btn btn-primary"/>
        <%--
        <g:actionSubmit value="${message(code: 'cancel')}" action="reset" class="btn"/>
        --%>
        </g:uploadForm>
        <input type="button" class="btn btn-primary" id="showHideHidden" value="${message(code: 'admin.dataSummary.showDisabled')}" onclick="displayTrClass('hiddenItem', 'showHideHidden', '${message(code: 'admin.dataSummary.hideDisabled')}', '${message(code: 'admin.dataSummary.showDisabled')}');" />
        </sec:ifAllGranted>
        <table class="datasummary">
            <tr><th>Name</th><th>QueryId</th><th>Imported</th><th>Import file</th><th>Active</th><th>See instances</th><sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN"><th>&nbsp;</th><th>&nbsp;</th></sec:ifAllGranted>
            </tr>
            <g:each in="${queries}" var="query">
                <tr${!query.active ? ' style=\"display:none\" class=\"hiddenItem\"':''}>
                    <td>${query.name?.get(language)}</td>
                    <td>${query.queryId}</td>
                    <td>${query.importTime}</td>
                    <td>${query.importFile}</td>
                    <td>${query.active}</td>
                    <td><a href="javascript:;" onclick="window.open('${createLink(action: "queryUsage", params: [queryId: query?.queryId])}', '_blank', 'width=1024, height=768, scrollbars=1');">See use</a></td>
                    <sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN">
                    <td>
                        <g:if test="${query.active}">
                            <opt:link action="inactivateQuery" class="btn" id="${query._id}"><g:message
                                    code="admin.dataSummary.inactivate"/></opt:link>
                        </g:if>
                        <g:else>
                            <opt:link action="activateQuery" class="btn btn-primary" id="${query._id}"><g:message
                                    code="admin.dataSummary.activate"/></opt:link>
                        </g:else>
                    </td>
                    <td>
                        <opt:link action="deleteQuery" class="btn btn-danger" id="${query._id}"
                                  onclick="return modalConfirm(this);"
                                  data-questionstr="Are you sure you wan't to delete query ${query.name?.get(language)}"
                                  data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}" data-titlestr="Deleting the query"><g:message
                                code="delete"/></opt:link>
                    </td>
                    </sec:ifAllGranted>
                </tr>
            </g:each>
        </table>
    </div>
</div>
</body>
</html>

