<%-- Copyright (c) 2012 by Bionova Oy --%>
<%
    def userService = grailsApplication.mainContext.getBean("userService")
%>
<!doctype html>
<html>
<head>
    <meta http-equiv="refresh" content="60" />
    <meta name="layout" content="main"/>
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <h1>
            Current active users (${activeUsers ? activeUsers.size() : 0}) - updates in: <span id="countdown">60</span> seconds
        </h1>
    </div>
</div>

<div class="container section">
    <div class="sectionbody">
        <table class="table table-striped table-condensed table-data">
            <thead>
            <tr>
                <th><g:message code="entity.name" /></th>
                <th><g:message code="email" /></th>
                <th><g:message code="user.channelToken" /></th>
                <th>Last request made</th>
                <th><g:message code="user.list.header.lastLogin" /></th>
                <th><g:message code="user.list.header.registrationTime" /></th>
                <th>Free user</th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${activeUsers}" var="user">
                <tr>
                    <td><opt:link controller="user" action="form" params="[id: user.id]">${user.name}</opt:link></td>
                    <td>${user.username}</td>
                    <td>${user.channelToken ? user.channelToken : ''}</td>
                    <td><g:formatDate date="${userIdAndLastRequest.get(user.id)}" format="dd.MM.yyyy HH:mm:ss" /></td>
                    <td><g:formatDate date="${user.getLastLogin()}" format="dd.MM.yyyy HH:mm:ss" /></td>
                    <td><g:formatDate date="${user.registrationTime}" format="dd.MM.yyyy HH:mm:ss" /></td>
                    <td>${user.getIsCommercialUser() ? "NO" : "YES"}</td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>
</div>
<script type="text/javascript">
    document.addEventListener("DOMContentLoaded", function() {
        var timeleft = 60;
        var countDownTimer = setInterval(function(){
            timeleft--;
            document.getElementById("countdown").textContent = timeleft;
            if(timeleft <= 0)
                clearInterval(countDownTimer);
        },1000);
    });
</script>
</body>
</html>