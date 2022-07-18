<!doctype html>
<html>
<head>
    <meta name="layout" content="main" />
</head>
<body>
<div class="container section">
    <sec:ifAnyGranted roles="ROLE_SUPER_USER">
        <div class="column_left">
            <div class="container">
                <h1>API requests, collection size: ${apiResponseSize}</h1>
            </div>
            <div class="clearfix"></div>
            <table class="table table-striped table-condensed data-list">
            <thead>
                <tr id="resourceListHeading">
                    <th>Database id</th><th>Parameters</th><th>Download results</th><th>Download original file</th><th>Filetoken</th><th>IndicatorId</th><th>Username</th><th>Status</th><th>Timestamp</th><th>IP address</th><%--<sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN"><th>&nbsp;</th></sec:ifAllGranted>--%>
                </tr>
            </thead>
            <tbody>
            <g:if test="${APICalculationResponses}">
            <g:each in="${APICalculationResponses}" var="apiCalculationResponse">
                <tr>
                    <td>${apiCalculationResponse.id}</td>
                    <td>
                        <div class="resourceImpactTooltip"><i class="fa fa-list fa-2x" aria-hidden="true"></i>
                            <span class="resourceImpactTooltipText" style="padding:8px; word-wrap: break-word;">
                                <g:each in="${apiCalculationResponse.params}" var="param">
                                    ${param.key}: ${param.value}<br/>
                                </g:each>
                            </span>
                        </div>
                    </td>
                    <td>
                        <g:if test="${apiCalculationResponse.apiFile}">
                            <g:link controller="oneClickLcaApi" action="getFileFromApiResponse" params="[id: apiCalculationResponse.id.toString(), results: true]" class="btn btn-primary">Results</g:link>
                        </g:if>
                    </td>
                    <td>
                        <g:if test="${apiCalculationResponse.originalFile}">
                            <g:link controller="oneClickLcaApi" action="getFileFromApiResponse" params="[id: apiCalculationResponse.id.toString(), original: true]" class="btn btn-primary">Original</g:link>
                        </g:if>
                    </td>
                    <td>${apiCalculationResponse.fileToken}</td>
                    <td>${apiCalculationResponse.indicatorId}</td>
                    <td>${apiCalculationResponse.username}</td>
                    <td>${apiCalculationResponse.status}</td>
                    <td>
                        <g:if test="${apiCalculationResponse.dateCreated}">
                            <g:formatDate format="HH:mm:ss dd.MM.yyyy" date="${apiCalculationResponse.dateCreated}"/>
                        </g:if>
                    </td>
                    <td>${apiCalculationResponse.ipAddress}</td>
                    <%--<sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN">
                        <td>
                            <g:link action="remove" class="btn btn-danger" id="${apiCalculationResponse.id}" onclick="return modalConfirm(this);" data-questionstr="Are you sure you want to delete apiCalculationResponse ${apiCalculationResponse.fileToken}?"
                                    data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}" data-titlestr="Deleting apiCalculationResponse"><g:message code="delete" /></g:link>

                        </td>
                    </sec:ifAllGranted>--%>
                </tr>
            </g:each>
            </g:if>
            </tbody>
            </table>

            <div class="container">
                <h1>Temporary Import object for API requests (automatically removed if not maintaining importMapper compatibility)</h1>
            </div>
            <div class="clearfix"></div>
            <table class="table table-striped table-condensed data-list">
                <thead>
                <tr>
                    <th>Originating System</th><th>Download</th><th style="max-width: 250px;">File name</th><th>Import date</th><th>Country</th><th style="min-width: 200px;">Project imported</th><th style="min-width: 200px;">Desing imported</th><th>IndicatorId</th><th>Username</th><th>IP Address</th>
                </tr>
                </thead>
                <tbody>
                <g:if test="${apiCalcTemporaryImportDatas}">
                    <g:each in="${apiCalcTemporaryImportDatas}" var="temporaryImportData">
                        <tr>
                            <td>${temporaryImportData.originatingSystem}</td>
                            <td>
                                <g:if test="${temporaryImportData.importFile}">
                                    <g:link controller="oneClickLcaApi" action="getFileFromTemporaryImportData" params="[id: temporaryImportData.id.toString()]" class="btn btn-primary">Original</g:link>
                                </g:if>
                            </td>
                            <td style="max-width: 250px; word-wrap:break-word;">${temporaryImportData.importFileName}</td>
                            <td>
                                <g:if test="${temporaryImportData.importDate}">
                                    <g:formatDate format="HH:mm:ss dd.MM.yyyy" date="${temporaryImportData.importDate}"/>
                                </g:if>
                            </td>
                            <td>${temporaryImportData.parentEntityCountry}</td>
                            <td style="min-width: 200px; word-wrap:break-word;">
                                <g:if test="${temporaryImportData.parentEntityId}">
                                    <opt:link controller="entity" action="show" id="${temporaryImportData.parentEntityId}" target="_blank">${temporaryImportData.parentEntityName}</opt:link>
                                </g:if>
                            </td>
                            <td style="min-width: 200px; word-wrap:break-word;">${temporaryImportData.childEntityName}</td>
                            <td>${temporaryImportData.indicatorId}</td>
                            <td>${temporaryImportData.username}</td>
                            <td>${temporaryImportData.ipAddress}</td>
                        </tr>
                    </g:each>
                </g:if>
                </tbody>
            </table>
        </div>

        <h1>Manual imports, collection size: ${temporaryImportDataSize}</h1>
        <div class="column_left">
            <table class="table table-striped table-condensed data-list">
                <thead>
                <tr>
                    <th>Originating System</th><th>Download</th><th style="max-width: 250px;">File name</th><th>Import date</th><th>Country</th><th style="min-width: 200px;">Project imported</th><th style="min-width: 200px;">Desing imported</th><th>IndicatorId</th><th>Username</th><th>IP Address</th>
                </tr>
                </thead>
                <tbody>
                <g:if test="${temporaryImportDatas}">
                    <g:each in="${temporaryImportDatas}" var="temporaryImportData">
                        <tr>
                            <td>${temporaryImportData.originatingSystem}</td>
                            <td>
                                <g:if test="${temporaryImportData.importFile}">
                                    <g:link controller="oneClickLcaApi" action="getFileFromTemporaryImportData" params="[id: temporaryImportData.id.toString()]" class="btn btn-primary">Original</g:link>
                                </g:if>
                            </td>
                            <td style="max-width: 250px; word-wrap:break-word;">${temporaryImportData.importFileName}</td>
                            <td>
                                <g:if test="${temporaryImportData.importDate}">
                                    <g:formatDate format="HH:mm:ss dd.MM.yyyy" date="${temporaryImportData.importDate}"/>
                                </g:if>
                            </td>
                            <td>${temporaryImportData.parentEntityCountry}</td>
                            <td style="min-width: 200px; word-wrap:break-word;">
                                <g:if test="${temporaryImportData.parentEntityId}">
                                    <opt:link controller="entity" action="show" id="${temporaryImportData.parentEntityId}" target="_blank">${temporaryImportData.parentEntityName}</opt:link>
                                </g:if>
                            </td>
                            <td style="min-width: 200px; word-wrap:break-word;">${temporaryImportData.childEntityName}</td>
                            <td>${temporaryImportData.indicatorId}</td>
                            <td>${temporaryImportData.username}</td>
                            <td>${temporaryImportData.ipAddress}</td>
                        </tr>
                    </g:each>
                </g:if>
                </tbody>
            </table>
        </div>
    </sec:ifAnyGranted>
    <div class="clearfix"></div>
</div>
</body>
</html>

