<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <h1>Editing construction group ${constructionGroup?.name}</h1>
    </div>
</div>
<div class="container">
    <div class="sectionbody">
        <div class="wrapperForNewConstructions container">

            <g:uploadForm controller="construction" action="editConstructionGroup" name="editConstructionGroup">
                <g:hiddenField name="id" value="${constructionGroup.id}"/>
                <table><tbody>
                <tr><td><opt:textField style="max-width:150px;" name="name" value="${constructionGroup?.name}" class="input redBorder"/></td>
                    <td><opt:submit value="save" name="submit" class="btn btn-primary "/></td>
                   <td><opt:link action="constructionsManagementPage" class="btn pull-right"><g:message code="back"/></opt:link></td></tr>
                </tbody></table>

            </g:uploadForm>

        </div>

</div>
</body>
</html>