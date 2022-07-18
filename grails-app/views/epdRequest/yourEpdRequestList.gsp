<%--
  Created by IntelliJ IDEA.
  User: trang
  Date: 6.5.2020
  Time: 12.33
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="format-detection" content="telephone=no"/>
</head>

<body>
<div class="container">
    <h1><g:message code="your_epd_requests_list.heading"/></h1>
    <table class="table table-condensed table-striped">
        <thead>
        <tr>
            <th>No.</th>
            <th><g:message code="send_me_data.sender"/></th>
            <th><g:message code="product_type"/></th>
            <th><g:message code="country_manufacture"/></th>
            <th><g:message code="main.list.entity"/></th>
            <th><g:message code="send_me_data.deadline"/></th>
            <th><g:message code="send_me_data.dateCreated"/></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <g:set var="entityService" bean="entityService"/>
        <g:each in="${epdRequests}" var="epdRequest" status="i">
            <g:if test="${epdRequest}">
                <g:set var="entity" value="${entityService.getEntityForShowing(epdRequest.entityId)}"/>
                <g:set var="currentDate" value="${new Date()}"/>
                <tr class="${epdRequest.deadline && epdRequest.deadline < currentDate ? 'grayLink': ''}">
                    <td>${i+1}</td>
                    <td>${epdRequest.senderEmail}</td>
                    <td>${subTypeObjs?.find({it?.subType == epdRequest?.subtypeForSendData})?.nameEN}</td>
                    <td>${countries?.find({it?.resourceId == epdRequest?.countryForSendData})?.nameEN}</td>
                    <td>${entity?.name}</td>
                    <td><g:formatDate date="${epdRequest.deadline}" format="dd.MM.yyyy" /><g:if test="${epdRequest.deadline && epdRequest.deadline < currentDate}"><br/><g:message code="send_me_data.request_expired"/> </g:if></td>
                    <td><g:formatDate date="${epdRequest.dateAdded}" format="dd.MM.yyyy" /></td>
                    <td>
                        <g:if test="${epdRequest.deadline && epdRequest.deadline < currentDate}">
                            <a class="btn-default btn" href="${createLink(base: "$request.scheme://$request.serverName:$request.serverPort$request.contextPath",
                                    controller: "epdRequest", action: "staticRequestDetails",
                                    params: [epdReqObjectId: epdRequest.id,externalLink:fromLink])}"><g:message code="results.expand"/></a>
                        </g:if>
                        <g:else>
                            <a class="btn-primary btn" href="${createLink(base: "$request.scheme://$request.serverName:$request.serverPort$request.contextPath",
                                    controller: "epdRequest", action: "staticRequestDetails",
                                    params: [epdReqObjectId: epdRequest.id,externalLink:fromLink])}"><g:message code="results.expand"/></a>
                        </g:else>

                    </td>
                </tr>
            </g:if>

        </g:each>
        </tbody>
    </table>
</div>

</body>
</html>