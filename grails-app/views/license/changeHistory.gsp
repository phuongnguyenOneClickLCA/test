<%@ page import="org.apache.commons.lang.StringUtils" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <h1>License ${license?.name} change history (last 10 changes)</h1>
    </div>
</div>
<div class="container section">
    <table class="resource" style="margin-bottom: 10px;">
        <tr><th>Date</th><th>User</th><th>Changes</th></tr>
        <g:if test="${changeHistories}">
            <g:each in="${changeHistories}">
                <tr>
                    <td><g:formatDate date="${it.date}" format="dd.MM.yyy HH:mm:ss"/></td>
                    <td>${it.username}</td>
                    <td>
                        <g:each in="${it.changes}" var="change">
                            ${change.key}: ${change.value}<br />
                        </g:each>
                    </td>
                </tr>
            </g:each>
        </g:if>
    </table>
</div>
</body>
</html>

