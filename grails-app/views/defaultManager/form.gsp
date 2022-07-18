<%-- Copyright (c) 2012 by Bionova Oy --%>
<!doctype html>
<%@ page import="com.bionova.optimi.core.service.ResourceTypeService" %>
<%
    ResourceTypeService resourceTypeService = grailsApplication.mainContext.getBean("resourceTypeService")
%>
<html>
<head>
    <meta name="layout" content="main">
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<g:form action="addDefaultValueSetValue" useToken="true">
    <g:hiddenField name="applicationId" value="${application?.applicationId}" />
    <g:hiddenField name="applicationDefaultId" value="${applicationDefault?.defaultValueId}" />
    <g:hiddenField name="defaultValueSetId" value="${defaultValueSet?.defaultValueSetId}" />
    <div class="container">
        <div class="screenheader">
            <div class="pull-right hide-on-print">
                <opt:link action="index" class="btn hide-on-print"><i class="icon-chevron-left"></i> <g:message code="back" /></opt:link>
                <opt:submit name="value" value="${message(code: 'save')}" class="btn btn-primary" />
            </div>
            <h1><g:message code="admin.defaultManager.title" /></h1>
        </div>
    </div>

    <div class="container section">
        <h2>${application.localizedName} (${application.applicationId}) / ${defaultValueSet?.localizedName}</h2>
        <g:if test="${defaultValueSet}">
                <div class="sectionbody">
                        <table class="table table-condensed" style="width: 50%;">
                            <tbody>
                            <g:each in="${applicationDefault.resourceTypes}" var="resourceType">
                                <tr>
                                    <td>${resourceTypeService.getLocalizedName(resourceType)}</td>
                                    <td>
                                        <input type="text" name="resourceTypeValue.${resourceType.resourceType}" value="${defaultValueSet.getDefaultValueSetValueByResourceType(resourceType.resourceType)?.formattedValue}" />
                                    </td>
                                </tr>
                            </g:each>
                            </tbody>
                        </table>
                </div>
        </g:if>
    </div>
</g:form>
</body>
</html>



