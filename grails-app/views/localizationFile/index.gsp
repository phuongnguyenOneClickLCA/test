<!doctype html>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <h1><g:message code="admin.localization_files.title" /></h1>
    </div>
</div>
<div class="container section">
   <g:uploadForm action="loadLocalizationExcel" useToken="true" method="post">
       <g:message code="admin.localization_files.choose" /> <input type="file" name="localizationExcel" />
       <div class="clearfix"></div>
       <opt:submit name="import" value="${message(code:'import')}" class="btn btn-primary"/>
   </g:uploadForm>
</div>

<div class="container">
    <div class="screenheader">
        <h1>Upload links</h1>
    </div>
</div>
<div class="container section">
    <g:uploadForm action="linkLocalizationExcel" useToken="true" method="post">
        Choose Excel file. Rememeber, this will overwrite existing link localizations <input type="file" name="localizationExcel" />
        <div class="clearfix"></div>
        <opt:submit name="import" value="${message(code:'import')}" class="btn btn-primary"/>
    </g:uploadForm>

    <table class="table table-condensed table-striped">
        <thead>
            <tr><td colspan="99"><b>Active links</b></td></tr>
            <tr>
                <td><b>translationId</b></td>
                <td><b>linkURL</b></td>
                <td><b>nameEN</b></td>
                <td><b>nameES</b></td>
                <td><b>nameSE</b></td>
                <td><b>nameIT</b></td>
                <td><b>nameNO</b></td>
                <td><b>nameFR</b></td>
                <td><b>nameDE</b></td>
                <td><b>nameFI</b></td>
                <td><b>nameNL</b></td>
                <td><b>nameHU</b></td>
                <td><b>nameJP</b></td>
            </tr>
        </thead>
        <tbody>
        <g:each in="${links}" var="link">
            <tr>
                <td>${link.translationId}</td>
                <td><a href="${link.linkURL}" target="_blank">${link.linkURL}</a></td>
                <td>${link.nameEN}</td>
                <td>${link.nameES}</td>
                <td>${link.nameSE}</td>
                <td>${link.nameIT}</td>
                <td>${link.nameNO}</td>
                <td>${link.nameFR}</td>
                <td>${link.nameDE}</td>
                <td>${link.nameFI}</td>
                <td>${link.nameNL}</td>
                <td>${link.nameHU}</td>
                <td>${link.nameJP}</td>
            </tr>
        </g:each>
        </tbody>
    </table>
</div>
</body>
</html>