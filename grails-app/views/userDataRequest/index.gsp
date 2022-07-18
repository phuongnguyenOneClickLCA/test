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
            User data requests
        </h1>
    </div>
</div>

<div class="container section">
    <div class="sectionbody">
        <%--
        UserDataRequest userDataRequest = new UserDataRequest()
                    userDataRequest.email = mailTo
                    userDataRequest.message = message
                    userDataRequest.subject = subj
                    userDataRequest.created = new Date()
                    userDataRequest.dataRequestLink = dataRequestLink
                    userDataRequest.entity = currentEntity

                    if (fileName) {
                        userDataRequest.filePath = getFilePathToZohoDataRequest(fileName)
                    }
                    userDataRequest.save(flush: true)
        --%>
        <table class="table table-striped table-condensed table-data">
            <thead>
            <tr>
                <th>Created</th>
                <th>User</th>
                <th>Email</th>
                <th>Subject</th>
                <th>Entity</th>
                <th>Data request link</th>
                <th>File</th>
                <th>&nbsp;</th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${dataRequests}">
                <tr>
                    <td>
                        <g:formatDate date="${it.created}" format="dd.MM.yyyy HH:mm:ss"/>
                    </td>
                    <td>${it.name}</td>
                    <td>${it.email}</td>
                    <td>${it.subject}</td>
                    <td>${it.entity}</td>
                    <td>${it.dataRequestLink}</td>
                    <td>
                        <g:if test="${it.filePath}"><g:link controller="util" action="downloadFile" target="_blank" params="[filePath: it.filePath]">${it.filePath}</g:link></g:if>
                    </td>
                    <td>
                        <opt:link action="remove" id="${it.id}" class="btn btn-danger"
                                  onclick="return modalConfirm(this);"
                                  data-questionstr="Are you sure you want to delete the user data request?"
                                  data-truestr="${message(code: 'delete')}" data-falsestr="${message(code: 'cancel')}"
                                  data-titlestr="Delete data request"><g:message
                                code="delete"/></opt:link>
                    </td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>