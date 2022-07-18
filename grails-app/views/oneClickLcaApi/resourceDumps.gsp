<!doctype html>
<html>
<head>
    <meta name="layout" content="main" />
</head>
<body>
<div class="container">
    <div class="screenheader">
        <h1>Download API parameter sets</h1>
    </div>
</div>
<div class="container section">
<sec:ifAnyGranted roles="ROLE_SUPER_USER">
     <div style="float: left; margin-bottom: 300px;">
         <div>
            <ul>
                <li><opt:link controller="oneClickLcaApi" removeEntityId="true" action="manualMappingJSON">Revit manual mapping JSON</opt:link></li>
                <li><opt:link controller="oneClickLcaApi" removeEntityId="true" action="manualSpecialRulesJSON">Revit Special rules</opt:link></li>
                <li><opt:link controller="oneClickLcaApi" removeEntityId="true" action="dumpIndicatorBenchmarks">Revit IndicatorBenchmark Dump</opt:link></li>
                <li>
                    <g:form action="getResourceLibrary" name="getResourceLibrary" id="getResourceLibrary">
                        <a onclick="$('#getResourceLibrary').trigger('submit');" style="cursor: pointer;" id="getResourceLibrarySubmit">Resource library download based on dataCategory</a>
                        <select id="dataCategory" name="dataCategory">
                            <option value=""></option>
                            <option value="fullResourceList">fullResourceList</option>
                            <g:each in="${dataCategories}">
                                <option value="${it}">${it}</option>
                            </g:each>
                        </select>
                    </g:form>
                </li>
            </ul>
         </div>
</sec:ifAnyGranted>
</div>
</body>
</html>
