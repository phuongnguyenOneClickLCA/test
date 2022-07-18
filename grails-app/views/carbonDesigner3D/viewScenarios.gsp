<%@ page import="com.bionova.optimi.core.service.CarbonDesignService" %>
<%--
  Created by IntelliJ IDEA.
  User: luke
  Date: 04/08/2021
  Time: 13:59
--%>
<%
    CarbonDesignService carbonDesignService = grailsApplication.mainContext.getBean("carbonDesignService")
%>
<!doctype html>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta name="layout" content="main">
    <meta name="format-detection" content="telephone=no"/>
</head>

<body>
<div class="container">
    <div class="screenheader">
        <h4>
            <opt:link controller="main" removeEntityId="true"><g:message code="main" /></opt:link> > <opt:link controller="account" action="form" params="[id: account?.id]" ><g:message code="account.title_page"/> </opt:link> > <g:message code="account.organisation_scenarios"/> ${account?.companyName ?: "${message(code: 'pdl.unnamed_company')}"} <br/>
        </h4>
        <div class="pull-left">
            <h1>
                <g:message code="account.organisation_scenarios"/> ${account?.companyName ?: "${message(code: 'pdl.unnamed_company')}"}
            </h1></div>
    </div>
    <div class="container">
        <table class="table scenarioTable">
            <thead>
                <tr>
                    <th>
                        <g:message code="account.scenerio_name"/>
                    </th>
                    <th>
                        <g:message code="account.building_type"/>
                    </th>
                    <th>
                        <g:message code="account.region"/>
                    </th>
                    <th>
                        <g:message code="account.published"/>
                    </th>
                    <th>
                        <g:message code="account.manage"/>
                    </th>
                </tr>
            </thead>
            <tbody>
            <g:if test="${scenarios}">
                <g:each var="scenario" in="${scenarios}">
                    <tr>
                        <td>
                            <g:if test="${editable}">
                                <g:each var="scenarioName" in="${scenario.name}">
                                    <g:set var="scenarioId" value="scenarioName${scenarioName.value.toString().replace(" ", "").replaceAll("\\p{Punct}","")}"/>
                                    <div>
                                        <span id="${scenarioId}">${scenarioName.value}</span>
                                        <a href="javascript:" onclick="showElementForEditing(this, '${scenarioId}')"><i class="fas fa-pencil-alt"></i></a>
                                        <span class="hidden inliner">
                                            <g:form action="renameScenario" params="[scenarioId: scenario.id.toString(), accountId: account?.id?.toString()]">
                                            <opt:textField style="max-width:150px;" name="name" value="${scenarioName.value}" class="input inliner"/>
                                            <g:submitButton name="save" value="${message(code: 'save')}" style="vertical-align: top;" class="btn btn-primary" />
                                            </g:form>
                                        </span>
                                    </div>
                                </g:each>
                            </g:if>
                            <g:else>
                                ${scenario.getLocalizedName()}
                            </g:else>
                        </td>
                        <g:form action="updateScenario"
                                params="[scenarioId: scenario.id.toString(), isAdminPage: false, accountId:account?.id ]">
                            <td>
                                <span id="scenarioBuildingType${scenario.id}">
                                    <g:each var="buildingTypeId" in="${scenario.buildingTypeIds}">
                                        <p style="margin: 0">
                                            ${carbonDesignService.getLocalizedName(buildingTypes.find { it.buildingTypeId == buildingTypeId })}
                                        </p>
                                    </g:each>
                                </span>
                                <a href="javascript:"
                                   onclick="showElementForEditing(this, 'scenarioBuildingType${scenario.id}')"><i
                                        class="fas fa-pencil-alt"></i></a>
                                <span class="hidden inliner">
                                    <g:select multiple="true" from="${buildingTypes}"
                                              value="${scenario.buildingTypeIds}" optionKey="buildingTypeId"
                                              optionValue="${{ carbonDesignService.getLocalizedName(it) }}"
                                              name="buildingTypes"/>
                                    <g:submitButton name="save" value="${message(code: 'save')}"
                                                    style="vertical-align: top;" class="btn btn-primary"/>
                                </span>
                            </td>
                            <td>
                                <span id="scenarioRegion${scenario.id}">
                                    <g:each var="regionId" in="${scenario.regionIds}">
                                        <p style="margin: 0">${regions.find({ it.regionId == regionId })?.localizedName}</p>
                                    </g:each>
                                </span>
                            </td>
                        </g:form>
                        <td>
                            <g:if test="${scenario.published}">
                                <g:message code="account.published"/>
                            </g:if>
                            <g:else>
                                <g:message code="account.awaiting_approval"/>
                            </g:else>
                        </td>
                        <td>
                            <g:if test="${editable}">
                                <g:if test="${!scenario.published}">
                                    <g:link class="btn btn-success" action="publishScenario" params="[scenarioId: scenario.id.toString(), accountId: account.id.toString()]"><g:message code="account.publish"/></g:link>
                                </g:if>
                                <g:else>
                                    <g:link class="btn btn-warning" action="unpublishScenario" params="[scenarioId: scenario.id.toString(), accountId: account.id.toString()]"><g:message code="account.unpublish"/></g:link>
                                </g:else>
                                <g:link class="btn btn-danger" onclick="return modalConfirm(this);"
                                        action="deleteScenario" params="[scenarioId: scenario.id.toString(), accountId: account.id.toString()]"
                                        data-questionstr="Are you sure you want to delete scenario ${scenario.getLocalizedName()}?"
                                        data-truestr="${message(code: 'delete')}" data-falsestr="${message(code: 'cancel')}"
                                        data-titlestr="Deleting Scenario">
                                    <g:message code="delete"/>
                                </g:link>
                            </g:if>
                        </td>
                    </tr>
                </g:each>
            </g:if>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>