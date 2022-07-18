<%-- Copyright (c) 2012 by Bionova Oy --%>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <div class="pull-right hide-on-print">
            <opt:link controller="entity" action="show" class="btn hide-on-print"><i class="icon-chevron-left"></i> <g:message code="back" /></opt:link>
        </div>
        <h1><g:message code="defaultManager.entity_defaults" args="[entity.name]" /></h1>
    </div>
</div>

<div class="container section">
    <h2>${application.localizedName} (${application.applicationId}) / ${applicationDefault.localizedName}</h2>
    <g:if test="${applicationDefault?.defaultValueSets}">
        <g:each in="${applicationDefault.defaultValueSets}" var="defaultValueSet">
            <div class="sectionbody">
                <h3>
                    Set of defaults: ${defaultValueSet.localizedName}
                    (${applicationDefault.getLinkedQuestion()?.localizedQuestion} ${applicationDefault.getLinkedQuestion()?.localizedUnit})
                </h3>
                <g:form action="addEntityDefaultValue" useToken="true">
                    <g:hiddenField name="entityId" value="${entity.id}" />
                    <g:hiddenField name="applicationId" value="${application.applicationId}" />
                    <g:hiddenField name="applicationDefaultId" value="${applicationDefault.defaultValueId}" />
                    <g:hiddenField name="defaultValueSetId" value="${defaultValueSet.defaultValueSetId}" />
                    <table class="table table-condensed" style="width: 50%;">
                    <tbody>
                    <g:each in="${applicationDefault.resourceTypes}" var="resourceType">
                        <tr>
                            <td>${resourceType.localizedName}</td>
                            <td>
                                <g:set var="entityValue" value="${entity.getDefaultValueSet(application.applicationId, applicationDefault.defaultValueId, defaultValueSet.defaultValueSetId)?.getFormattedValue(resourceType.resourceType)}" />
                                <g:if test="${entityValue != null}">
                                    <input type="text" name="resourceTypeValue.${resourceType.resourceType}" value="${entityValue}" />
                                </g:if>
                                <g:else>
                                    <input type="text" name="resourceTypeValue.${resourceType.resourceType}" value="${defaultValueSet.getFormattedValue(resourceType.resourceType)}" />
                                </g:else>
                            </td>
                        </tr>
                    </g:each>
                    <tr><td>&nbsp;</td><td><opt:submit name="value" value="${message(code: 'save')}" class="btn btn-primary" /></td></tr>
                    </tbody>
                </table>
            </g:form>
            </div>
        </g:each>
    </g:if>
</div>
</body>
</html>



