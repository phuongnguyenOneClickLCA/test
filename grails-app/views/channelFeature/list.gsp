<%-- Copyright (c) 2012 by Bionova Oy --%>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="format-detection" content="telephone=no"/>
    <g:set var="channelFeatureService" bean="channelFeatureService"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <h1>
            <g:message code="admin.channelFeature.list" />
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
                <th><g:message code="admin.channelFeature.name" /></th>
                <th><g:message code="admin.channelFeature.customer" /></th>
                <th><g:message code="admin.channelFeature.logo" /></th>
                <th><g:message code="admin.channelFeature.loginUrl" /></th>
                <th><g:message code="admin.channelFeature.token" /></th>
                <th><g:message code="admin.channelFeature.defaultChannel" /></th>
                <th><g:message code="admin.channelFeature.lastUpdated" /></th>
                <th><g:message code="admin.channelFeature.lastUpdater" /></th>
                <th>&nbsp;</th>
                <th>&nbsp;</th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${channelFeatures}" var="channelFeature">
                <tr>
                    <td>
                        <opt:link action="form" params="[id: channelFeature.id]">${channelFeature.name}</opt:link>
                    </td>
                    <td>${channelFeature.customer}</td>
                    <td>
                        <g:if test="${channelFeature.logo}">
                            ${channelFeature.logo}: <img src="${channelFeature.logo}" alt="" /> %{--static image url--}%
                        </g:if>
                    </td>
                    <td>
                        <g:if test="${channelFeature.loginUrl}">
                            <a href="${channelFeature.loginUrl}" target="_blank">${channelFeature.loginUrl}</a>
                        </g:if>
                    </td>
                    <td>${channelFeature.token}</td>
                    <td><g:formatBoolean boolean="${channelFeature.defaultChannel}" false="${message(code: 'no')}" true="${message(code: 'yes')}" /></td>
                    <td><g:formatDate date="${channelFeature.lastUpdated}" format="dd.MM.yyyy HH:mm"/></td>
                    <td>${channelFeatureService.getLastUpdater()?.name}</td>
                    <td><opt:link action="applyChannel" params="[token: channelFeature.token]" class="btn btn-primary"><g:message code="apply" /></opt:link></td>
                    <td>
                        <g:if test="${!channelFeature.defaultChannel}">
                            <opt:link action="setDefault" id="${channelFeature.id}" onclick="return modalConfirm(this);" class="btn btn-primary"
                                  data-questionstr="${message(code:'admin.channelFeature.set_default.question', args: [channelFeature.name])}" data-truestr="${message(code:'yes')}" data-falsestr="${message(code:'cancel')}" data-titlestr="${message(code:'admin.channelFeature.set_default.header')}">
                                <g:message code="admin.channelFeature.set_default" />
                            </opt:link>
                        </g:if>
                    </td>
                    <td>
                        <g:if test="${!channelFeature.defaultChannel}">
                            <opt:link action="remove" id="${channelFeature.id}" class="btn btn-danger" onclick="return modalConfirm(this);"
                                  data-questionstr="${message(code:'admin.channelFeature.delete.question', args: [channelFeature.name])}" data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}" data-titlestr="${message(code:'admin.channelFeature.delete.header')}"><g:message code="delete" /></opt:link>
                        </g:if>
                    </td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>