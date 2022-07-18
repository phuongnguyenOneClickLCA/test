<%--
  Created by IntelliJ IDEA.
  User: luke
  Date: 10/11/2020
  Time: 09:35
--%>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
    <div class="container">
        <div class="screenheader">
            <h4>Resources by dataProperties in all entities linked to license</h4>
        </div>
    </div>
    <div class="container">
        <table class="resource">
            <tbody>
            <g:if test="${resourcesWithDataProperties}">
                <g:each in="${resourcesWithDataProperties}">
                    <tr><td>${it.key}</td>
                        <td>
                            <g:if test="${it.value}">
                                ${it.value.size()}
                            </g:if>
                            <g:else>
                                0
                            </g:else>
                        </td>
                    </tr>
                </g:each>
            </g:if>
            </tbody>
        </table>
    </div>
</body>
</html>