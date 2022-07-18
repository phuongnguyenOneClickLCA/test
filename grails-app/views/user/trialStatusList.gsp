<!doctype html>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
</head>
<body>
<sec:ifAnyGranted roles="ROLE_SALES_VIEW">
    <div class="container">
        <h4>
            <opt:link controller="main" removeEntityId="true"><g:message code="main" /></opt:link> > Trial Status List
        </h4>

        <h2>Trial Status List</h2>
        <g:link controller="fileExport" action="dumpUserTrialToExcel" class="btn btn-primary pull-right"><g:message code="results.expand.download_excel" /></g:link>
        <h5>Time shown is the difference in minutes from the time the action take place comparing to the time where first project were created </h5>
            <opt:renderTrialStatus userList="${userList}" sortData="true"/>
    </div>
</sec:ifAnyGranted>

</body>

</html>