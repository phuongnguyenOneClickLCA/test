<%--
  Created by IntelliJ IDEA.
  User: trang
  Date: 17.4.2020
  Time: 12.18
--%>

<html xmlns="http://www.w3.org/1999/html">
<%@ page import="com.bionova.optimi.core.service.ResourceTypeService" %>
<%
    ResourceTypeService resourceTypeService = grailsApplication.mainContext.getBean("resourceTypeService")
%>
<head>
    <meta name="layout" content="main"/>
    <meta name="format-detection" content="telephone=no"/>
</head>

<body>
<div class="container">
    <g:if test="${epdRequest}">
        <div>
            <g:if test="${fromLink}">
                <div>
                    <a class="btn btn-default" href="${createLink(base: "$request.scheme://$request.serverName:$request.serverPort$request.contextPath",
                            controller: "epdRequest", action: "yourEpdRequestList",
                            params: [externalLink: true,manualId:epdRequest.manualId])}"><g:message code="back"/> </a>
                </div>
            </g:if>

            <div class="text-center">
                <h1><g:message code="static_epdRequest.heading"/></h1>
                <p><g:message code="static_epdRequest.subheading1"/></p><br/>
                <p><g:message code="static_epdRequest.subheading2"/></p>
                <div class="margin-bottom-10">
                    <a class="btn btn-primary" href="https://www.oneclicklca.com/pre-verified-epd-generator/" target="_blank"><g:message code="learn_more.epd_generator"/> </a>
%{--                    // Deprecate code, to be removed by the end of feature 0.5.0 release--}%
%{--                    <a class="btn btn-primary" href="https://www.oneclicklca.com/request-epds/" target="_blank"><g:message code="learn_more.epd_request"/> </a>--}%
                    <a class="btn btn-primary" href="https://${request.serverName}/app/?&channelToken=oneclicklca"><g:message code="log_in_to"/> </a>
                </div>
            </div>
        </div>
        <table class="table table-condensed table-striped">
            <g:set var="senderEmail" value="${user?.username ?: epdRequest.senderEmail}"/>
            <g:set var="senderName" value="${user?.name}"/>
            <g:set var="senderPhone" value="${user?.phone}"/>
            <g:set var="sendingOrganisation" value="${epdRequest.sendingOrganisation}"/>
            <tbody>
            <tr>
                <th><g:message code="send_me_data.sender"/></th>
                <td>
                    <strong><g:message code="entity.name"/></strong>: ${senderName} <br/>
                    <strong><g:message code="account.email"/></strong>: ${senderEmail} <br/>
                    <strong><g:message code="user.organization"/></strong>: ${sendingOrganisation} <br/>
                    <strong><g:message code="user.phone"/></strong>: ${senderPhone} <br/>
                </td>
            </tr>
            <tr>
                <th><g:message code="manufacturer_name"/></th><td><strong><g:message code="entity.name"/></strong>: ${epdRequest.manufacturer}<br><strong><g:message code="account.email"/></strong>: ${epdRequest.recipientEmail}</td>
            </tr>
            <tr>
                <th><g:message code="product_type"/></th><td>${resourceTypeService.getLocalizedName(subTypeObj)}</td>
            </tr>
            <tr>
                <th><g:message code="entity.show.task.description"/></th><td>${epdRequest.productDescription}</td>
            </tr>
            <tr>
                <th><g:message code="send_me_data.estimated_demand"/></th><td>${epdRequest.estimatedDemand}</td>
            </tr>
            <tr>
                <th><g:message code="send_me_data.compliance"/></th><td><g:message code="send_me_data.compliance_opt${epdRequest.complianceStandard}"/></td>
            </tr>
            <tr>
                <th><g:message code="send_me_data.compliance_other"/></th><td>${epdRequest.complianceStandardOther}</td>
            </tr>
            <tr>
                <th>${message(code:"send_me_data.tech_required_heading").toLowerCase().capitalize()}</th>
                <td>
                    <ul>
                        <li class="${epdRequest.thirdPartyVerified ? '':'hidden'}" id="thirdPartyVerified"><g:message code="send_me_data.thirdPartyVerified"/></li>
                        <li class="${epdRequest.privateEPDVerified ? '':'hidden'}" id="privateEPDVerified"><g:message code="send_me_data.privateEPDVerified"/></li>
                        <li class="${epdRequest.productCarbonVerified ? '':'hidden'}" id="productCarbonVerified"><g:message code="send_me_data.productCarbonVerified"/></li>
                        <li class="${epdRequest.productSpecific ? '':'hidden'}" id="productSpecific"><g:message code="send_me_data.productSpecific"/></li>
                        <li class="${epdRequest.projectSpecific ? '':'hidden'}" id="projectSpecific"><g:message code="send_me_data.projectSpecific"/></li>
                    </ul>
                </td>
            </tr>
            <tr>
                <th><g:message code="country_manufacture"/></th><td>${countries.find({it.resourceId == epdRequest.countryForSendData}).nameEN}</td>
            </tr>
            <tr>
                <th><g:message code="project_description"/></th><td>${basicQueryTable}</td>
            </tr>
            <tr>
                <th><g:message code="send_me_data.deadline"/></th><td><g:formatDate date="${epdRequest.deadline}" format="dd.MM.yyyy" /></td>
            </tr>
            <tr>
                <th><g:message code="send_me_data.dateCreated"/></th><td><g:formatDate date="${epdRequest.dateAdded}" format="dd.MM.yyyy" /></td>
            </tr>
            </tbody>
        </table>
    </g:if>
    <g:else>
        <div class="text-center"><h2><g:message code="send_me_data.request_unavailable"/></h2></div>
    </g:else>

</div>


</body>
</html>