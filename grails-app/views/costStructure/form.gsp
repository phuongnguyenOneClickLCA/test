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
            CostStructures
        </h1>
    </div>
</div>
<div class="container section">
    <sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN">
        <g:uploadForm action="upload">
            <div class="clearfix"></div>
            <div class="column_left">
                <div class="control-group">
                    <label for="file" class="control-label"><g:message code="admin.import.excel_file" /></label>
                    <div class="controls"><input type="file" name="xlsFile" id="file" class="btn" value="" /></div>
                </div>
            </div>
            <div class="clearfix"></div>
            <opt:submit name="import" value="${message(code:'import')}" class="btn btn-primary"/>
        </g:uploadForm>
    </sec:ifAllGranted>

    <div class="clearfix"></div>

    <g:if test="${activeSubTypesWithoutACostStructure && !activeSubTypesWithoutACostStructure.isEmpty()}">
        <div class="alert alert-warning">
            <button type="button" class="close" data-dismiss="alert">×</button>
            <strong>Active resourceSubTypes without a Cost Structure:<br/></strong>
            <g:each in="${activeSubTypesWithoutACostStructure}" var="subType">
                ${subType.subType}<br/>
            </g:each>
        </div>
    </g:if>


    <table class="table table-striped table-condensed data-list">
        <g:if test="${costStructures}"><%--
        --%><thead><tr id="resourceListHeading">
            <th>Name</th><th>materialCost</th><th>MaterialCostCurrency</th><th>LabourUnitsWorker</th><th>LabourUnitsCraftsman</th><th>thickness_mm</th><th>density</th><th>CostUnit</th><th>ResourceSubType</th><th>ImportFile</th>
            <sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN"><th>&nbsp;</th></sec:ifAllGranted>
        </tr></thead><tbody><%--
        --%><g:each in="${costStructures}" var="costStructure"><%--
        --%><tr>
            <td>${costStructure.name}</td>
            <td>${costStructure.materialCost}</td>
            <td>${costStructure.materialCostCurrency}</td>
            <td>${costStructure.labourUnitsWorker}</td>
            <td>${costStructure.labourUnitsCraftsman}</td>
            <td>${costStructure.thickness_mm}</td>
            <td>${costStructure.density}</td>
            <td>${costStructure.costUnit}</td>
            <td>${costStructure.resourceSubType}</td>
            <td>${costStructure.importFile}</td>
            <sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN">
                <td>
                    <g:link action="remove" class="btn btn-danger" id="${costStructure.id}" onclick="return modalConfirm(this);" data-questionstr="Are you sure you want to delete costStructure ${costStructure.name}?"
                            data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}" data-titlestr="Deleting costStructure"><g:message code="delete" /></g:link>
                </td>
            </sec:ifAllGranted>
        </tr><%--
        --%></g:each></tbody><%--
    --%></g:if>
    </table>
</div>

</body>
</html>