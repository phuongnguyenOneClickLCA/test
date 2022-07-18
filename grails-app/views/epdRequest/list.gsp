<%--
  Created by IntelliJ IDEA.
  User: trang
  Date: 17.4.2020
  Time: 12.17
--%>

<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta name="layout" content="main"/>
    <meta name="format-detection" content="telephone=no"/>
</head>
<sec:ifAnyGranted roles="ROLE_SALES_VIEW">

<body>
<div class="container">
    <h1>EPD Requests list</h1>
    <table class="table table-condensed table-striped">
        <thead>
        <tr>
            <th>Sender</th>
            <th>Recipient</th>
            <th>Material subtype</th>
            <th>Country</th>
            <th>Project name</th>
            <th>Deadline</th>
            <th>Created at</th>
            <th>Opened at</th>
            <th>Link to details</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <g:set var="entityService" bean="entityService"/>
        <g:each in="${allEpdRequest}" var="epdRequest">
            <g:set var="entity" value="${entityService.getEntityForShowing(epdRequest.entityId)}"/>
            <tr>
                <td>${epdRequest.senderEmail}</td>
                <td>${epdRequest.recipientEmail}</td>
                <td>${epdRequest.subtypeForSendData}</td>
                <td>${countries.find({it.resourceId == epdRequest.countryForSendData}).nameEN}</td>
                <td><opt:link controller="entity" action="show" id="${epdRequest.entityId}" >${entity?.name}</opt:link></td>
                <td><g:formatDate date="${epdRequest.deadline}" format="dd.MM.yyyy" /></td>
                <td>${epdRequest.dateAdded}</td>
                <td>${epdRequest.lastViewed ?: "Not opened yet"}</td>
                <td><a href="${createLink(base: "$request.scheme://$request.serverName:$request.serverPort$request.contextPath",
                        controller: "epdRequest", action: "staticRequestDetails",
                        params: [epdReqObjectId: epdRequest.id])}">Details</a>
                </td>
                <td><opt:link action="deleteRequest" class="btn btn-danger" id="${epdRequest.id}" onclick="return modalConfirm(this);"
                              data-questionstr="Are you sure you want to delete this request from ${epdRequest?.senderEmail}"
                              data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}" data-titlestr="Deleting request"><g:message
                            code="delete"/></opt:link> </td>
            </tr>
        </g:each>
        </tbody>
    </table>

    <h1>SendMeData Requests list</h1>
    <table class="table table-condensed table-striped">
        <thead>
        <tr>
            <th>Sender</th>
            <th>Recipient</th>
            <th>Type</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${sendMeDataRequests}" var="sendMeDataRequest">
            <tr>
                <td>${sendMeDataRequest.sentByUser?.username}</td>
                <td>${sendMeDataRequest.receivedByUsers?.collect({it.username})?.join(", ")}</td>
                <td>${sendMeDataRequest.type}</td>
            </tr>
        </g:each>
        </tbody>
    </table>
</div>


</body>
</sec:ifAnyGranted>
</html>