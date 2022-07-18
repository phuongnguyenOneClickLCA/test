<!doctype html>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
    <style>
        table tr td {
            border: 1px solid black;
            border-collapse: collapse;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <h1>
            Calculated resources per indicator
        </h1>
    </div>
</div>
<div class="container section">
     <div style="float: left;">
        <ul>
            <g:each in="${resultsPerIndicator}">
            <li style="margin-bottom: 10px;"><strong>${it.key.localizedName} <g:link action="resourcesFromIndicator" params="[indicatorId: it.key.indicatorId]">Download Excel</g:link></strong>
                <table style="margin-left: 10px;">
                    <tr>
                        <th>Entity</th>
                        <th>Design</th>
                        <th>nameEN</th>
                        <th>Areas</th>
                        <th>Download Individual</th>
                    </tr>
                    <g:each in="${it.value}" var="resultPerChild">
                        <tr style="border: 1px solid black; border-collapse: collapse;">
                            <td>${resultPerChild.key?.parentName}</td>
                            <td>${resultPerChild.key?.operatingPeriodAndName}</td>
                            <td>${resultPerChild.key?.datasets?.find({"nameEN".equals(it.questionId)})?.answerIds}</td>
                            <td>${resultPerChild.key?.datasets?.find({"area".equals(it.questionId)})?.answerIds}</td>
                            <td><g:link action="resourceFromResult" params="[entityId: resultPerChild.key?.id, indicatorId: it.key.indicatorId]">${resultPerChild.value}</g:link></td>
                        </tr>
                    </g:each>
                </table>
            </li>
        </g:each>
        </ul>
     </div>
</div>
</body>
</html>
