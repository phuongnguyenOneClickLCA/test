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
        <h4> <opt:link controller="main" removeEntityId="true"><g:message code="main" /></opt:link>
            <sec:ifLoggedIn>
                <g:if test="${entity}">
                    > <opt:link controller="entity" action="show" id="${entity?.id}">
                    <g:abbr value="${entity?.name}" maxLength="20" />
                </opt:link>
                    <g:if test="${childEntity}">
                        > <span id="childEntityName">${childEntity?.operatingPeriodAndName}</span>
                    </g:if>
                </g:if>
            </sec:ifLoggedIn>
            > <g:message code="import_data" /> <br/> </h4>
        <h1 style="display: inline;">${temporaryImportData?.temporaryEntity?.name}</h1>
        <div class="pull-right hide-on-print">
            <g:link controller="importMapper" action="generateExcelFromDatasets" params="[returnUserTo: 'temporaryCalculations']" class="btn btn-primary"><g:message code="results.expand.download_excel" /></g:link>
            <g:link action="cancel" class="btn" >Finish!</g:link>
        </div>
        <br/><h2 style="display: inline;">Your calculation results are now ready to be downloaded!</h2>
    </div>
</div>
<div class="container section">
    <g:link controller="importMapper" action="generateExcelFromTempCalculationResult" class="btn btn-primary">Download Calculation Results</g:link>
</div>

</body>
</html>



