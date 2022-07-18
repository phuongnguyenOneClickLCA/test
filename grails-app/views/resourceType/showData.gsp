<%@ page import="org.apache.commons.lang.StringUtils" %>
<!doctype html>
<%@ page import="com.bionova.optimi.core.service.ResourceTypeService" %>
<%
    ResourceTypeService resourceTypeService = grailsApplication.mainContext.getBean("resourceTypeService")
%>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <h1>ResourceType "${resourceTypeService.getLocalizedName(resourceType)}" data</h1>
    </div>
</div>
<div class="container section">
    <g:set var="referenceResourceIdFields" value="${showDataReferences?.resourceIdReferences}" />
    <g:set var="referenceEolProcessFields" value="${showDataReferences?.eolProcessReferences}" />
    <g:if test="${properties}">
        <a href="javascript:" onclick="showElementByClassOrId('noValue');">Show empty rows</a>
        <table class="resource">
            <g:each in="${properties}">
                <g:if test="${it.value != null}">
                    <g:if test="${referenceResourceIdFields?.contains(it.key)}">
                        <g:if test="${it.value instanceof List}">
                            <tr><td>${it.key}</td><td>
                                <g:each in="${it.value}" var="listValue">
                                    ${listValue} <a href="javascript:" onclick="window.open('${createLink(action: "showData", controller: "import", params: [resourceId: listValue])}', '_blank', 'width=1024, height=768, scrollbars=1');"><i class="fas fa-search-plus"></i></a><br />
                                </g:each>
                            </td></tr>
                        </g:if>
                        <g:else>
                            <tr><td>${it.key}</td><td>${it.value} <a href="javascript:" onclick="window.open('${createLink(action: "showData", controller: "import", params: [resourceId: it.value])}', '_blank', 'width=1024, height=768, scrollbars=1');"><i class="fas fa-search-plus"></i></a></td></tr>
                        </g:else>
                    </g:if>
                    <g:elseif test="${referenceEolProcessFields?.contains(it.key)}">
                        <g:if test="${it.value instanceof List}">
                            <tr><td>${it.key}</td><td>
                                <g:each in="${it.value}" var="listValue">
                                    ${listValue} <a href="javascript:" onclick="window.open('${createLink(action: "showData", controller: "eolProcess", params: [eolProcessId: listValue])}', '_blank', 'width=1024, height=768, scrollbars=1');"><i class="fas fa-search-plus"></i></a><br />
                                </g:each>
                            </td></tr>
                        </g:if>
                        <g:else>
                            <tr><td>${it.key}</td><td>${it.value} <a href="javascript:" onclick="window.open('${createLink(action: "showData", controller: "eolProcess", params: [eolProcessId: it.value])}', '_blank', 'width=1024, height=768, scrollbars=1');"><i class="fas fa-search-plus"></i></a></td></tr>
                        </g:else>
                    </g:elseif>
                    <g:else>
                        <tr><td>${it.key}</td><td>${it.value}</td></tr>
                    </g:else>
                </g:if>
                <g:else>
                    <tr class="noValue" style="display: none;"><td>${it.key}</td><td>${it.value}</td></tr>
                </g:else>
            </g:each>
        </table>
    </g:if>
</div>
</body>
</html>

