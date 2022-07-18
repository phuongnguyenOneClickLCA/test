<%--
  Created by IntelliJ IDEA.
  User: luke
  Date: 16/05/2021
  Time: 14:03
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="format-detection" content="telephone=no"/>
</head>

<body>
<div class="container">
    <div class="screenheader">
        <h1>Upload Carbon Designer Building Types Excel</h1>
    </div>
</div>

<div class="container section">
    <sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN">
        <div class="pull-left">
            <g:uploadForm action="createBuildingTypesFromExcel">
                <div class="clearfix"></div>

                <div class="column_left">
                    <div class="control-group">
                        <label for="file" class="control-label"><g:message code="admin.import.excel_file"/></label>

                        <div class="controls"><input type="file" name="xlsFile" id="file" class="btn" value=""/></div>
                    </div>
                </div>

                <div class="clearfix"></div>
                <opt:submit name="import" value="${message(code: 'import')}" class="btn btn-primary"/>
            </g:uploadForm>
        </div>

        <div class="clearfix"></div>
    </sec:ifAllGranted>
    <g:if test="${buildingTypes}">
        <table class="table table-striped table-condensed data-list">
            <g:each in="${buildingTypes}" var="buildingType">
                <tr id="resourceListHeading">
                    <th>buildingTypeId</th>
                    <th>imageAndModel</th>
                    <th>nameEN</th>
                    <th>Default data</th>
                </tr>
                <tr>
                    <td>${buildingType.buildingTypeId}</td>
                    <td>${buildingType.imageAndModel}</td>
                    <td>${buildingType.nameEN}</td>
                    <td>
                        <table class="table table-striped table-condensed data-list">
                            <tr id="defaultDataHeading">
                                <th>ParameterId</th>
                                <th>Min</th>
                                <th>Max</th>
                                <th>Default value</th>
                                <th>Region Defaults</th>
                            </tr>
                            <g:each in="${buildingType.defaultData}" var="data">
                                <tr>
                                    <td>${data.parameterId}</td>
                                    <td>${data.minValue}</td>
                                    <td>${data.maxValue}</td>
                                    <td>${data.defaultValue}</td>
                                    <td>
                                        <table class="table table-condensed data-list">
                                            <g:each in="${data.regionDefaultValues}" var="regionData">
                                                <tr id="regionHeading">
                                                    <td>${regionData.key}:</td>
                                                    <td>${regionData.value}</td>
                                                </tr>
                                            </g:each>
                                        </table>
                                    </td>
                                </tr>
                            </g:each>
                        </table>
                    </td>
                </tr>
            </g:each>
        </table>
    </g:if>
</div>
</body>
</html>