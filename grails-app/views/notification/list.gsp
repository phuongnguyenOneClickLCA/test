<%-- Copyright (c) 2012 by Bionova Oy --%>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <h1>
            <g:message code="admin.notification.list" />
        </h1>
    </div>
</div>

<div class="container section">
    <div class="sectionbody">
        <div class="well well-controls">
            <opt:link action="form" class="btn btn-primary"><g:message code="add" /></opt:link>
            <div class="clearfix"></div>
        </div>

        <table class="table table-striped table-condensed table-data">
            <thead>
            <tr>
                <th><g:message code="admin.notification.heading" /></th>
                <th><g:message code="admin.notification.text" /></th>
                <th><g:message code="admin.notification.channelFeatures" /></th>
                <th><g:message code="admin.notification.dateCreated" /></th>
                <th><g:message code="admin.notification.lastUpdated" /></th>
                <th><g:message code="admin.notification.enabled" /></th>
                <th>&nbsp;</th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${notifications}" var="notification">
                <tr>
                    <td>
                        <opt:link action="form" params="[id: notification.id]">${notification.localizedHeading}</opt:link>
                    </td>
                    <td><g:abbr maxLength="50" value="${notification.localizedText}" /></td>
                    <td>
                        <g:set var="channelFeatures" value="${notification.channelFeatures?.findAll({it != null})}" />
                      <g:if test="${channelFeatures}">
                        <g:join in="${channelFeatures.collect({it.name})}" delimiter=", "/>
                      </g:if>
                    </td>
                    <td><g:formatDate date="${notification.dateCreated}" format="dd.MM.yyyy HH:mm"/></td>
                    <td><g:formatDate date="${notification.lastUpdated}" format="dd.MM.yyyy HH:mm"/></td>
                    <td><g:formatBoolean boolean="${notification.enabled}" true="${message(code: 'yes')}" false="${message(code: 'no')}" /></td>
                    <td><g:formatBoolean boolean="${notification.isPopUp}" true="${message(code: 'yes')}" false="${message(code: 'no')}" /></td>
                    <td style="width: 30px;">
                        <opt:link action="remove" id="${notification.id}" class="btn btn-danger" onclick="return modalConfirm(this);"
                            data-questionstr="${message(code:'admin.notification.delete.question')}" data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}" data-titlestr="${message(code:'admin.notification.delete.header')}"><g:message code="delete" /></opt:link>
                    </td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>