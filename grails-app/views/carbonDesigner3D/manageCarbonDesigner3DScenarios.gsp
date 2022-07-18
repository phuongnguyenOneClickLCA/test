<%@ page import="com.bionova.optimi.core.service.CarbonDesignService" %>
<%@ page import="com.bionova.optimi.core.service.TemplateService" %>


<%--
  Created by IntelliJ IDEA.
  User: luke
  Date: 05/08/2021
  Time: 10:24
--%>

<%
    CarbonDesignService carbonDesignService = grailsApplication.mainContext.getBean("carbonDesignService")
    TemplateService templateService = grailsApplication.mainContext.getBean("templateService")
%>

<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta name="layout" content="main">
    <meta name="format-detection" content="telephone=no"/>
</head>

<body>
<div class="container">
    <div class="screenheader">
        <h4>
            <opt:link controller="main" removeEntityId="true"><g:message code="main" /></opt:link>
        </h4>
        <div class="pull-left">
            <h1>
                Carbon Designer 3D - Public Scenarios
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
                        <g:message code="account.floor_limits"/>
                    </th>
                    <th>
                        <g:message code="account.structural_frame"/>
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
            <g:if test="${publicScenarios}">
                <g:each var="publicScenario" in="${publicScenarios}">
                    <tr>
                        <td>
                            <g:each var="scenarioName" in="${publicScenario.name}">
                                <g:set var="scenarioId" value="scenarioName${scenarioName.value.toString().replace(" ", "").replaceAll("\\p{Punct}","")}"/>
                                <div>
                                    <span id="${scenarioId}"><strong>${scenarioName.key}</strong>: ${scenarioName.value}</span>
                                    <a href="javascript:" onclick="showElementForEditing(this, '${scenarioId}')"><i class="fas fa-pencil-alt"></i></a>
                                    <span class="hidden inliner">
                                        <g:form action="renameScenario" params="[scenarioId: publicScenario.id.toString(), isAdminPage: true]">
                                            <opt:textField style="max-width:50px;" name="locale" value="${scenarioName.key}" class="input inliner"/>
                                            <opt:textField style="max-width:150px;" name="name" value="${scenarioName.value}" class="input inliner"/>
                                            <g:submitButton name="save" value="${message(code: 'save')}" style="vertical-align: top;" class="btn btn-primary" />
                                        </g:form>
                                    </span>
                                </div>
                            </g:each>
                        </td>
                        <g:form action="updateScenario"
                                params="[scenarioId: publicScenario.id.toString(), isAdminPage: true]">
                            <td>
                                <span id="scenarioBuildingType${publicScenario.id}">
                                    <g:each var="buildingTypeId" in="${publicScenario.buildingTypeIds}">
                                        <p style="margin: 0">
                                            ${carbonDesignService.getLocalizedName(buildingTypes.find{ it.buildingTypeId == buildingTypeId })}
                                        </p>
                                    </g:each>
                                </span>
                                <a href="javascript:"
                                   onclick="showElementForEditing(this, 'scenarioBuildingType${publicScenario.id}')"><i
                                        class="fas fa-pencil-alt"></i></a>
                                <span class="hidden inliner">
                                    <g:select multiple="true" from="${buildingTypes}"
                                              value="${publicScenario.buildingTypeIds}" optionKey="buildingTypeId"
                                              optionValue="${{ carbonDesignService.getLocalizedName(it) }}"
                                              name="buildingTypes"/>
                                    <g:submitButton name="save" value="${message(code: 'save')}"
                                                    style="vertical-align: top;" class="btn btn-primary"/>
                                </span>
                            </td>
                            <td>
                                <span id="scenarioRegion${publicScenario.id}">
                                    <g:each var="regionId" in="${publicScenario.regionIds}">
                                        <p style="margin: 0">${regions.find({ it.regionId == regionId })?.localizedName}</p>
                                    </g:each>
                                </span>
                                <a href="javascript:"
                                   onclick="showElementForEditing(this, 'scenarioRegion${publicScenario.id}')"><i
                                        class="fas fa-pencil-alt"></i></a>
                                <span class="hidden inliner">
                                    <g:select multiple="true" from="${regions}" value="${publicScenario.regionIds}"
                                              optionKey="regionId" optionValue="localizedName" name="regions"/>
                                    <g:submitButton name="save" value="${message(code: 'save')}"
                                                    style="vertical-align: top;" class="btn btn-primary"/>
                                </span>
                            </td>
                            <td>
                                <span id="floorLimits${publicScenario.id}" style="display: block">
                                    <g:message code="scenario.min_floors"/>: ${publicScenario.minFloors}
                                    <g:message code="scenario.max_floors"/>: ${publicScenario.maxFloors}
                                </span>
                                <a href="javascript:"
                                   onclick="showElementForEditing(this, 'floorLimits${publicScenario.id}')"><i
                                        class="fas fa-pencil-alt"></i></a>
                                <span class="hidden inliner" style="display: block">
                                    <div>
                                        <g:message code="scenario.min_floors"/>
                                        <g:field name="minFloors" type="number" style="max-width:45px;"
                                                 data-parsley-type="integer"
                                                 min="0"
                                                 value="${publicScenario.minFloors}"/>
                                    </div>

                                    <div>
                                        <g:message code="scenario.max_floors"/>
                                        <g:field name="maxFloors" type="number" style="max-width:45px;"
                                                 data-parsley-type="integer"
                                                 min="0"
                                                 value="${publicScenario.maxFloors}"/>
                                    </div>
                                    <g:submitButton name="save" value="${message(code: 'save')}"
                                                    style="vertical-align: top;" class="btn btn-primary"/>
                                </span>
                            </td>
                            <td>
                                <g:set var="possibleStructuralFrameIds"
                                       value="${templateService.getPossibleStructuralFramesByTemplateId(publicScenario.id.toString())}"/>
                                <span id="structuralFrames${publicScenario.id}">
                                    <g:if test="${publicScenario.structuralFrameIds}">
                                        <g:each var="structuralFrameId" in="${publicScenario.structuralFrameIds}">
                                            <p style="margin: 0">${structuralFrames.find({ structuralFrame -> structuralFrame.structuralFrameId == structuralFrameId }).getLocalizedName()}</p>
                                        </g:each>
                                    </g:if>
                                    <g:else>
                                        <p style="margin: 0">${g.message(code: "scenario.apply_for_all_frames")}</p>
                                    </g:else>
                                </span>
                                <a href="javascript:"
                                   onclick="showElementForEditing(this, 'structuralFrames${publicScenario.id}')"><i
                                        class="fas fa-pencil-alt"></i></a>
                                <span class="hidden inliner">
                                    <g:select multiple="true"
                                              from="${structuralFrames.findAll({ structuralFrame -> possibleStructuralFrameIds.contains(structuralFrame.structuralFrameId) })}"
                                              value="${publicScenario.structuralFrameIds}"
                                              optionKey="structuralFrameId" optionValue="${{it?.localizedName ?: ""}}"
                                              name="structuralFrameIds"
                                              noSelection="${['': g.message(code:"scenario.apply_for_all_frames")]}"
                                    />
                                    <g:submitButton name="save" value="${message(code: 'save')}"
                                                    style="vertical-align: top;" class="btn btn-primary"/>
                                </span>
                            </td>
                        </g:form>
                        <td>
                            <g:if test="${publicScenario.published}">
                                <g:message code="account.published"/>
                            </g:if>
                            <g:else>
                                <g:message code="account.awaiting_approval"/>
                            </g:else>
                        </td>
                        <td>
                            <g:if test="${!publicScenario.published}">
                                <g:link class="btn btn-success" action="publishScenario" params="[scenarioId: publicScenario.id.toString(), isAdminPage: true]"><g:message code="approve"/></g:link>
                            </g:if>
                            <g:else>
                                <g:link class="btn btn-warning" action="unpublishScenario" params="[scenarioId: publicScenario.id.toString(), isAdminPage: true]"><g:message code="account.unpublish"/></g:link>
                            </g:else>
                            <g:link class="btn btn-danger" onclick="return modalConfirm(this);"
                                    action="deleteScenario" params="[scenarioId: publicScenario.id.toString(), isAdminPage: true]"
                                    data-questionstr="Are you sure you want to delete scenario ${publicScenario.getLocalizedName()}?"
                                    data-truestr="${message(code: 'delete')}" data-falsestr="${message(code: 'cancel')}"
                                    data-titlestr="Deleting Scenario">
                                <g:message code="delete"/>
                            </g:link>
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