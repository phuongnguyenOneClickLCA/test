<%@ page import="org.bson.Document; com.bionova.optimi.core.domain.mongo.Construction" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <h1>Resources used in constructions</h1>
    </div>
</div>
<div class="container section">
    <table class="table table-striped" style="margin-bottom: 10px; table-layout: fixed;">
        <tr><th style="width: 150px;">ResourceId</th><th style="width: 150px;">ProfileId</th><th>Constructions</th></tr>

        <g:each in="${resourcesInConstructions}" var="resourceInConstructions">
            <g:set var="resourceId" value="${resourceInConstructions.key.tokenize(".")[0]}" />
            <g:set var="profileId" value="${resourceInConstructions.key.tokenize(".")[1]}" />

            <tr>
                <td style="width: 150px; word-wrap: break-word; white-space: normal;">${resourceId}</td>
                <td style="width: 150px; word-wrap: break-word; white-space: normal;">${profileId == "null" ? "" : profileId}</td>
                <td>
                    <g:each in="${(List<org.bson.Document>) resourceInConstructions.value}" var="construction">
                        <g:link controller="construction" action="form" params="[id:construction._id, classificationQuestionId: construction.classificationQuestionId, classificationParamId: construction.classificationParamId]">${construction.nameEN}</g:link>
                        , <b>Group:</b> ${constructionGroups?.find({it.groupId?.equals(construction.constructionGroup)})?.name}, <b>ImportFile:</b> ${construction.importFile}<br />
                    </g:each>
                </td>
            </tr>
        </g:each>
    </table>
</div>
</body>
</html>