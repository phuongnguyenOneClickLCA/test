<%-- Copyright (c) 2012 by Bionova Oy --%>
<%@ page import="grails.converters.JSON; static com.bionova.optimi.core.service.SimulationToolService.conversionUnit as conversionUnit" %>
<%@ page import="static com.bionova.optimi.core.service.SimulationToolService.conversionUnitFactor as conversionUnitFactor" %>
<%@ page import="com.bionova.optimi.core.service.ConstructionService" %>
<%@ page import="com.bionova.optimi.core.service.IndicatorService" %>
<%
    ConstructionService constructionService = grailsApplication.mainContext.getBean("constructionService")
    IndicatorService indicatorService = grailsApplication.mainContext.getBean("indicatorService")
%>
<g:set var="defaultTextSelect" value="${g.message(code: "notApplied")}"/>
<g:set var="passivhausId" value="${"passivhusId"}"/>


<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
        <meta name="format-detection" content="telephone=no"/>
        <asset:javascript src="jquery.autocomplete.js?v=${grailsApplication.metadata.'app.version'}"/>

        <style>

            input.numeric {
                width: 50px;
                margin: 0;
                padding: 2px 2px 2px 2px;
            }


            .div-input > label {
                display: inline;

            }

            td{
                vertical-align: middle;
            }

            h2{
                margin-bottom: 15px;
            }

            h4 {
                  margin-bottom: 13px;
              }

            select{
                background: white !important;
            }

            .hiddenByBuildingType {
                display: none !important;
            }

        </style>
	</head>
	<body>


    <%-- PATH SECTION --%>
    <div class="container"><h4>
        <sec:ifLoggedIn>
                <opt:link controller="main" removeEntityId="true">
                    <g:message code="main" />
                </opt:link>
                >
                <opt:link controller="entity" action="show" id="${entityId}" >
                    <g:abbr value="${entity?.name}" maxLength="20" />
                </opt:link>
                >  <g:abbr value="${design?.name}" maxLength="38" />
                >  <g:message code="simulationTool.firstPage.heading" />

        </sec:ifLoggedIn>
        </h4>
    </div>

    <%-- PATH SECTION --%>

    <g:form name="simulationForm" controller="design" action="carbonDashboard" params="[entityId:entityId, designId: designId, indicatorId: indicatorId, sessionId: sessionId]">

        <div id="main_body" class="section body">

            <div class="container" style="border-bottom: 1px lightgrey solid; margin-bottom: 15px; display: flex; ">

                <h1  style="font-size: 25px;">
                    <img style="width: 50px;" src="/app/assets/simulationTool_icon/simulationTool_icon.png">
                    <g:message code="simulationTool.firstPage.heading" />
                </h1>
                <%-- Column Button Create --%>

                <div style="margin-left: auto;">
                    <button id="div-submit" type="button" class="btn btn-primary disabled buttonCreate" onclick="sendForm()" disabled>
                        ${message(code:'simulationTool.firstPage.button.createSimulation')}
                    </button>
                </div>
            </div>

            <div class="container">
                <label><g:message code="simulationTool.firstPage.subHeading" /></label>
                <br>
            </div>

            <div class="container">
                <div class="alert alert-info hidden onchangeUpdateTip">
                    <i class="fas fa-info pull-left" style="font-size: large; margin-right: 8px;"></i>
                    <p style="margin: 0 0 3px;">${g.message(code:"help_float_tip7")} <a href="javascript:;" style="color: #002752" ${(!designLocked) ? "onclick='userclicksnext(); onChangeUpdatePopup()'" : "disabled"} >
                    <g:message code="simulationTool.firstPage.button.calculateAreas" /></a></p>
                </div>
            </div>

                <div class="container">

                    <%-- First Column of the page --%>
                    <div class="column_left bordered-right">

                        <%-- Building parameters Section --%>
                        <div class="scopeSection">
                            <h2><g:message code="simulationTool.projectScope" /></h2>

                            <h4><g:message code="simulationTool.buildingParameters" /></h4>

                    <%--    <g:set var="disableGroup" value="${enabledBuildingElement ? !enabledBuildingElement.contains("foundationsGroup") : false}"/>  --%>
                            <label id="label-foundationsGroup" class="checkbox">
                                <g:checkBox onclick="onShow('foundations', this.checked)" name="foundationsCheck"
                                            value="${mappedScopes?.contains("foundationsGroup") ?: false}"/>
                                <g:message code="foundationsGroup"/>
                            </label>

                            <label id="label-groundSlabGroup" class="checkbox">
                                <g:checkBox onclick="onShow('groundSlab', this.checked)"  name="groundSlabCheck"
                                            value="${mappedScopes?.contains("groundSlabGroup") ?: false}"/>
                                <g:message code="groundSlabGroup"/>
                            </label>

                            <label id="label-structureGroup" class="checkbox">
                                <g:checkBox onclick="onShow('structure', this.checked)" name="structureCheck"
                                            value="${mappedScopes?.contains("structureGroup") ?: false}"/>
                                <g:message code="structureGroup" />
                            </label>

                            <label id="label-enclosureGroup" class="checkbox">
                                <g:checkBox onclick="onShow('enclosure', this.checked)" name="enclosureCheck"
                                            value="${mappedScopes?.contains("enclosureGroup") ?: false}"/>
                                <g:message code="enclosureGroup" />
                            </label>

                            <%--<label id="label-internalWallsGroup" class="checkbox">
                                <g:checkBox onclick="onShow('internalWalls', this.checked)" name="internalWallsCheck"
                                            value="${mappedScopes?.contains("internalWallsGroup") ?: false}"/>
                                <g:message code="internalWallsGroup" />
                            </label>--%>

                            <label id="label-finishesGroup" class="checkbox">
                                <g:checkBox onclick="onShow('finishes', this.checked)" name="finishesCheck"
                                            value="${mappedScopes?.contains("finishesGroup") ?: false}"/>
                                <g:message code="finishesGroup" />
                            </label>

                            <label id="label-servicesGroup" class="checkbox">
                                <g:checkBox onclick="onShow('services', this.checked)" name="servicesCheck"
                                            value="${mappedScopes?.contains("servicesGroup") ?: false}"/>
                                <g:message code="servicesGroup" />
                            </label>

                            <label id="label-externalAreasGroup" class="checkbox">
                                <g:checkBox onclick="onShow('externalAreas', this.checked)" name="externalAreasCheck"
                                            value="${mappedScopes?.contains("externalAreasGroup") ?: false}"/>
                                <g:message code="externalAreasGroup" />
                            </label>

                            <label id="label-defaultsGroup" class="checkbox">
                                <g:checkBox data-defaultsAvailable="${defaultsAvailableForTool ? "true" : "false"}" onclick="onShow('defaults', this.checked)" name="defaultsCheck" value="${false}"/>
                                <g:message code="defaultsGroup" />
                            </label>
                        </div>

                        <br>

                        <%-- Building size and nr of floors Section --%>
                        <div class="sizeAndFloorSection">

                            <h4><g:message code="simulationTool.buildingSection" /></h4>
                            <div class="input-append">
                                <input id="regionReference" class="help-on-change-select" name="regionReference" type="text" defaultOption="${defaultRegion}" defaultOptionName="${defaultRegionName}" style="width: 257px;" onclick="showAllOptions();" onchange="onChangeUpdatePopup();" />
                                <a tabindex="-1" class="add-on showAllResources help-on-change-select" onclick="showAllOptions();" >
                                    <i class="icon-chevron-down"></i>
                                </a>
                            </div>

                            <div class="controls">
                                <label for="buildingType" class="control-label"><strong><g:message code="simulationTool.buildingType" /></strong></label>
                                <g:select name="buildingType" class="help-on-change-select${buildingTypeResources.contains(defaultBuildingType)? "" : " redBorder" }" from="${buildingTypeResources}" value="${defaultBuildingType}" optionKey="${{it.resourceId}}" optionValue="${{it.localizedName}}" noSelection="['' : '']" onchange="showEnergySection();" required="true"/>
                            </div>

                            <div class="table-floor">
                                <table>
                                    <tr>
                                        <td><label for="grossFloorArea" class="control-label"><g:message code="simulationTool.grossFloorArea" /> <a href="#" class="infopopover" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${message(code:"simulationTool.gfa_help_info")}">
                                            <i class="icon-question-sign"></i></a></label></td>
                                        <td><input class="numeric help-on-change${grossArea?.intValue() ? "" : " redBorder"}" type="number" min="1" id ="grossFloorArea" name="grossFloorArea" value="${grossArea?.intValue() ?: ""}"/></td>
                                        <td><label for="grossFloorArea">${conversionUnit(unitSystem, "m2")}</label></td>

                                    </tr>
                                    <tr>
                                        <td><label for="aboveGroundFloors" class="control-label"><g:message code="simulationTool.aboveGroundFloors" /></label></td>
                                        <td><input class="numeric help-on-change${numberFloor ? "" : " redBorder"}" onkeypress="return event.charCode >= 48 && event.charCode <= 57" type="number" min="1" id="aboveGroundFloors" name="aboveGroundFloors" value="${numberFloor ?: ""}" /></td>
                                    </tr>

                                    <g:if test="${customAssessmentPeriod}">
                                        <tr>
                                            <td>
                                                <label for="assessmentPeriod" class="control-label"><g:message
                                                        code="simulationTool.calculationPeriod"/>
                                                    <a href="#" class="infopopover" data-toggle="dropdown" rel="popover"
                                                       data-trigger="hover"
                                                       data-content="${message(code: "simulationTool.assessmentPeriod_help_info")}">
                                                        <i class="icon-question-sign"></i></a>
                                                </label>
                                            </td>
                                            <td><input class="numeric ${assessmentPeriod != null ? "" : " redBorder"}"
                                                       onkeypress="return event.charCode >= 48 && event.charCode <= 57"
                                                       type="number" min="1" id="assessmentPeriod"
                                                       name="assessmentPeriod"
                                                       value="${assessmentPeriod != null ? assessmentPeriod.intValue() : ""}"/>
                                            </td>
                                            <td><label for="assessmentPeriod"><g:message
                                                    code="simulationTool.years"/></label></td>
                                        </tr>
                                    </g:if>
                                    <g:if test="${!hideSurfaceDeReference}">
                                        <tr>
                                        <td><label for="surfaceDeReference" class="control-label"><g:message
                                                code="simulationTool.surfaceDeReference"/>
                                            <a href="#" class="infopopover" data-toggle="dropdown" rel="popover"
                                               data-trigger="hover"
                                               data-content="${message(code: "simulationTool.surfaceDeReference_help_info")}">
                                                <i class="icon-question-sign"></i></a>
                                        </label></td>
                                        <td><input class="numeric redBorder"
                                                   onkeypress="return event.charCode >= 48 && event.charCode <= 57"
                                                   type="number" min="1" id="surfaceDeReference"
                                                   name="surfaceDeReference"/></td>
                                        <td><label for="surfaceDeReference">${conversionUnit(unitSystem, "m2")}</label>
                                        </td>
                                        </tr>
                                    </g:if>
                                    <tr class="${hideSurfaceDeScombles ? "hiddenByBuildingType" : ""}" id="surfaceDeScomblesRow">
                                        <td><label for="surfaceDeScombles" class="control-label"><g:message
                                                code="simulationTool.surfaceDeScombles"/>
                                            <a href="#" class="infopopover" data-toggle="dropdown" rel="popover"
                                               data-trigger="hover"
                                               data-content="${message(code: "simulationTool.surfaceDeScombles_help_info")}">
                                                <i class="icon-question-sign"></i></a>
                                        </label></td>
                                        <td><input class="numeric redBorder allowZero"
                                                   onkeypress="return event.charCode >= 48 && event.charCode <= 57"
                                                   type="number" min="0" id="surfaceDeScombles"
                                                   name="surfaceDeScombles"/></td>
                                        <td><label for="surfaceDeScombles">${conversionUnit(unitSystem, "m2")}</label>
                                        </td>
                                    </tr>
                                    <tr class="${hideNombreDeLogement ? "hiddenByBuildingType" : ""}" id="nombreDeLogementRow" >
                                        <td><label for="nombreDeLogement" class="control-label"><g:message
                                                code="simulationTool.nombreDeLogement"/>
                                            <a href="#" class="infopopover" data-toggle="dropdown" rel="popover"
                                               data-trigger="hover"
                                               data-content="${message(code: "simulationTool.nombreDeLogement_help_info")}">
                                                <i class="icon-question-sign"></i></a>
                                        </label></td>
                                        <td><input class="numeric redBorder"
                                                   onkeypress="return event.charCode >= 48 && event.charCode <= 57"
                                                   type="number" min="1" id="nombreDeLogement" name="nombreDeLogement"/>
                                        </td>
                                    </tr>
                                    <tr onclick="showUndergroundFloors(this)">
                                        <td><i style="display:inline-block; margin-bottom: 15px; cursor:pointer; font-size:15px;" class="fa fa-plus fa-1x primary btnRemove"></i>
                                            <a  href="javascript:" class="expanded primary btnRemove" style="display:inline-block; margin-left:5px; font-weight:bold; cursor:pointer;" onclick=""> <g:message code="simulationTool.addUndergroundFloors" /></a>
                                        </td>
                                    </tr>
                                    <tr class="hidden toHide">
                                        <td><label for="undergroundHeatedFloors" class="control-label"><g:message code="simulationTool.undergroundHeatedFloors" /></label></td>
                                        <td><input class="numeric underground help-on-change" type="text" id ="undergroundHeatedFloors" name="undergroundHeatedFloors" value="0" /></td>
                                    </tr>
                                    <tr class="hidden toHide">
                                        <td><label for="undergroundUnheatedFloors" class="control-label"><g:message code="simulationTool.undergroundUnheatedFloors" /></label></td>
                                        <td><input class="numeric underground help-on-change" type="text" id ="undergroundUnheatedFloors" name="undergroundUnheatedFloors" value="0" /></td>
                                    </tr>

                                    <tr id="headingEarthquakeZoneContainer" class="earthquakeZoneContainer hidden toHide ${hideEarthquakeZone ? "hiddenByBuildingType" : ""}">
                                        <td colspan="2">
                                            <label for="isEarthquakeZone" class="control-label"><strong><g:message code="simulationTool.isEarthquakeZone" /></strong>
                                                <a href="#" class="infopopover" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${message(code:"simulationTool.isEarthquakeZone.info")}">
                                                    <i class="icon-question-sign"></i>
                                                </a>
                                            </label>
                                        </td>
                                    </tr>
                                    <tr id="mediumEarthquakeZoneContainer" class="earthquakeZoneContainer hidden toHide ${hideEarthquakeZone ? "hiddenByBuildingType" : ""}">
                                        <td>
                                            <label for="isEarthquakeZoneMedium" class="control-label"><g:message code="simulationTool.isEarthquakeZoneMedium" />
                                                <a href="#" class="infopopover" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${message(code:"simulationTool.isEarthquakeZoneMedium.info")}">
                                                    <i class="icon-question-sign"></i>
                                                </a>
                                            </label>
                                        </td>
                                        <td><input type="checkbox" id="isEarthquakeZoneMedium" name="isEarthquakeZoneMedium" value="true" class="isEarthquakeZoneMedium earthquakeCheckbox help-on-change-checkbox"></td>
                                    </tr>
                                    <tr id="highEarthquakeZoneContainer" class="earthquakeZoneContainer hidden toHide ${hideEarthquakeZone ? "hiddenByBuildingType" : ""}">
                                        <td>
                                            <label for="isEarthquakeZoneHigh" class="control-label"><g:message code="simulationTool.isEarthquakeZoneHigh" />
                                                <a href="#" class="infopopover" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${message(code:"simulationTool.isEarthquakeZoneHigh.info")}">
                                                    <i class="icon-question-sign"></i>
                                                </a>
                                            </label>
                                        </td>
                                        <td><input type="checkbox" id="isEarthquakeZoneHigh" name="isEarthquakeZoneHigh" value="true" class="isEarthquakeZoneHigh earthquakeCheckbox help-on-change-checkbox"></td>
                                    </tr>

                                    <g:if test="${foundationsConstructions}">
                                        <tr class="hidden toHide">
                                            <td colspan="2"><label class="control-label"><strong><g:message code="simulationTool.foundationsDefault" /></strong></label></td>
                                        </tr>
                                        <tr class="hidden toHide">
                                            <td colspan="2">
                                                <select id="foundationsDefault" name="foundationsDefault">
                                                    <option value=""></option>
                                                    <g:each in="${foundationsConstructions}" var="foundationsConstruction">
                                                        <option class="foundationOptions"
                                                                value="${foundationsConstruction.constructionId}"
                                                                data-regionReferenceId="${foundationsConstruction.regionReferenceId ?: ""}"${defaultRegion && foundationsConstruction.regionReferenceId && foundationsConstruction.regionReferenceId.indexOf(defaultRegion) == -1 ? " style=\"display: none;\"" : ""}>
                                                            ${constructionService.getLocalizedLabel(foundationsConstruction)}
                                                        </option>
                                                    </g:each>
                                                </select>
                                            </td>
                                        </tr>
                                    </g:if>

                                    <g:if test="${hasPrivateData}">
                                        <tr id="privateDataContainer" class="hidden toHide">
                                            <td><label for="allowPrivateConstructions" class="control-label"><g:message code="simulationTool.showPrivateConstructions" /></label></td>
                                            <td><g:checkBox name="allowPrivateConstructions" value="${true}" /></td>
                                        </tr>
                                    </g:if>
                                </table>
                            </div>

                            <%-- Energy Section --%>
                            <div id="div_energy" class="sizeAndFloorSection hidden">
                                <h4><g:message code="simulationTool.energySection" /></h4>

                                <div class="controls">
                                    <label for="energyScenario" class="control-label"><g:message code="simulationTool.scenario" /></label>
                                    <g:select name="energyScenario"  from="${[]}" optionKey="${{}}" optionValue="${{}}" onchange="onChangeEnergyScenario()"/>
                                </div>

                                <div class="controls">
                                    <label for="alternativeScenario" class="control-label"><g:message code="simulationTool.alternativeScenario" /></label>
                                    <g:select name="alternativeScenario"  from="${[]}" optionKey="${{}}" optionValue="${{}}" onchange="onChangeEnergyScenario()"/>
                                </div>

                            <div class="table-floor">
                                <table id="energyScenarioParams"><%--
                                    <tr class="hidden">
                                        <td><label class="control-label"><g:message code="simulationTool.heatedArea" /></label></td>
                                        <td><input class="numeric" type="text" id ="heatedArea" name="heatedArea"/></td>
                                        <td><label class="control-label">m<sup>2</sup></label></td>
                                    </tr>
                                --%><tr class="${passivhausId} hidden">
                                        <td><label for="dut" class="control-label"><g:message code="simulationTool.dut" /></label></td>
                                        <td><input class="numeric" type="text" id ="dut" name="dut" value="26.7"/></td>
                                        <td><label class="control-label"><g:message code="simulationTool.celsius" /></label></td>
                                </tr>

                                    <tr class="${passivhausId} hidden">
                                        <td><label for="averageTemperature" class="control-label"><g:message code="simulationTool.averageTemperature" /></label></td>
                                        <td><input class="numeric" type="text" id ="averageTemperature" name="averageTemperature" value="6.3" /></td>
                                        <td><label class="control-label"><g:message code="simulationTool.celsius" /></label></td>
                                    </tr>
                                </table>
                            </div>
                            </div>

                            <g:if test="${allowedLCCIndicatorsForProject}">
                                <div id="div_lcc">
                                    <h4><g:message code="simulationTool.LCC" /></h4>

                                    <div class="controls">
                                        <label for="lccIndicatorId" class="control-label"><g:message code="simulationTool.LCC.tool" /></label>
                                        <select id="lccIndicatorId" name="lccIndicatorId">
                                            <option value="">${message(code: 'notApplied')}</option>
                                            <g:each in="${allowedLCCIndicatorsForProject}" var="indicator">
                                                <option value="${indicator.indicatorId}" ${indicatorId?.equalsIgnoreCase(indicator.indicatorId) ? "selected" : ''}>${indicatorService.getLocalizedName(indicator)}</option>
                                            </g:each>
                                        </select>
                                    </div>
                                </div>
                            </g:if>

                        </div>

                        <%-- DIV BUTTON --%>
                        <div style="margin-top: 20px;">
                            <opt:link controller="entity" action="show" params="[entityId:entityId]" class="btn" >
                                <g:message code="cancel"/>
                            </opt:link>

                            <a class="btn btn-primary"  ${(!designLocked) ? "onclick='userclicksnext(); onChangeUpdatePopup()'" : "disabled"} >
                                <g:message code="simulationTool.firstPage.button.calculateAreas" />
                            </a>
                            <button id="div-submit2" type="button" class="btn btn-primary disabled buttonCreate" onclick="sendForm()" disabled>
                                ${message(code:'simulationTool.firstPage.button.createSimulation')}
                            </button>
                        </div>

                    </div>

                    <%-- Column Table Results --%>
                    <div class="column_right_cd" id="test">

                        <h2><g:message code="simulationTool.buildingDimensions" /></h2>

                        <div id="entityImage" class="image-cd ${buildingTypeImageClass ?  "" : "hidden"}" >
                            <i id="buildingTypeImage" class="entitytype ${buildingTypeImageClass ?: ""}"></i>
                        </div>

                        <div id="building_dimensions_div"></div>

                        <%-- Column Upload Template --%>
                        <div class="column_left" style="margin-top: 15px">
                            <div id="uploadFile_div">
                                <%--
                                <label style=" max-width:200px; word-break: break-word">
                                    <g:message code="simulationTool.help_text_upload_file" />
                                </label>
                                --%>
                                <a class="btn btn-primary" onclick="showHideDivWithI(this, 'upload_div')" style="margin: 5px 0px 15px 0px">
                                    <i class="fa fa-plus"></i>
                                    <g:message code="simulationTool.uploadTemplate_heading"/>
                                </a>
                                <a href="#" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${g.message(code: "simulationTool.help_text_upload_file")}">
                                    <i class="icon-question-sign"></i> 
                                </a>
                                <div id="upload_div" class="hide">
                                    <div class="control-group">
                                        <opt:link controller="util" action="downloadAreasTemplate" >
                                            <g:message code="simulationTool.downloadTemplate"/></button>
                                        </opt:link>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label for="file" class="control-label"><g:message code="simulationTool.chooseFile" /></label>
                                        <div class="controls"><input type="file" name="xlsFile" id="file" class="btn" value="" /></div>
                                    </div>

                                    <a class="btn btn-primary" onclick="uploadTemplate()"><g:message code="simulationTool.uploadTemplate"/></a>
                                    <i id="uploadResult"></i><i id="errorResult" style="color: #ee1600"></i>
                                </div>
                            </div>
                        </div>

                    </div>
                    <div class="column_left" id="column_areas_div"></div>

                </g:form>

            </div>
        </div>

        <div id="spinner" class="loading-spinner hidden"><div class="image">
            <div><h1 id="title_spinner"><g:message code="simulationTool.spinner.createSimulation"/></h1></div>
            <svg class="loadingSVG" version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg"  x="0px" y="0px"
                 width="270px" height="270px" viewBox="0 0 32 32" style="enable-background:new 0 0 32 32;" xml:space="preserve">
                <g><path class="arrowSpinningSvg mm-o-syncing-queue" d="M24.5,10.4L22.2,11l5,5L29,9.2l-2.4,0.7C24.4,6.1,20.4,3.8,16,3.8c-5.4,0-10.1,3.4-11.7,8.6
                    c-0.2,0.5,0.1,1.1,0.7,1.3c0.1,0,0.2,0,0.3,0c0.4,0,0.8-0.3,1-0.7c1.3-4.3,5.2-7.2,9.7-7.2C19.5,5.8,22.7,7.5,24.5,10.4z"/>
                </g>
            </svg>
            <p class="working"><g:message code="loading.working"/>.</p>
        </div></div>


    <script>

        var persistInputs = [];
        var designNetFloorArea = ${designNetFloorArea};
        var designHeatedArea = ${heatedArea};
        var earthquakeZones = ${defaultEarthquakeZones};
        let surfaceDeScomblesAllowedBuildingTypes = ${surfaceDeScomblesAllowedBuildingTypes ? surfaceDeScomblesAllowedBuildingTypes as JSON : []};
        let nombreDeLogementAllowedBuildingTypes = ${nombreDeLogementAllowedBuildingTypes ? nombreDeLogementAllowedBuildingTypes as JSON : []};
        var moreParamsExpanded = false;

        //document.querySelectorAll("a").forEach(el => el.onmouseover = () => {alert("test")})
        var sessionId = "${sessionId}";

        $(document).ready(() => resetForms());

        // Input Field Numeric Class Validation
        $('.numeric').on('input propertychange', numericInputValidation);
        $('.numeric').popover({
            content:"${message(code:'enter_nozero_noletter')}",
            placement:'right',
            template: '<div class="popover"><div class="arrow"></div><div class="popover-content indicatorHelp"></div></div>',
            trigger: 'manual',
            html:true
        });

        //*************************************************************
        //Change Building Type dropdown menu for every Region Reference
        //*************************************************************
        var buildingTypeByRegion = ${buildingTypeByRegion ?: [:]};
        var energyScenarioByRegionAndBuilding = ${(energyScenarioByRegionAndBuilding) ? energyScenarioByRegionAndBuilding as grails.converters.JSON : []};
        var defaultChoisesByRegion = ${defaultChoisesByRegion ? defaultChoisesByRegion : [:]};
        var comatibleEnergyScenarioPerRegion = ${comatibleEnergyScenarioPerRegion ?: [:]};
        var defaultEnergyScenariosPerBuildingType = ${defaultEnergyScenariosPerBuildingType ?: [:]};
        var alternativeDefaultEnergyScenariosPerBuildingType = ${alternativeDefaultEnergyScenariosPerBuildingType ?: [:]};

        buildSelectCD("#buildingType", buildingTypeByRegion["${defaultRegion}"], "${defaultBuildingType}", "", true);

        $("#regionReference").on('change', function () {
            buildSelectCD("#buildingType", buildingTypeByRegion[$(this).val()],"${defaultBuildingType}", "", true);
        });

        function findObjectValueByKey(array, key) {
            for (var i = 0; i < array.length; i++) {
                if (array[i].hasOwnProperty(key)) {
                    return array[i][key];
                }
            }
            return null;
        }

        function validEnergyScenarios(regionId, buildingId){
            let compatibleEnergyScenarioList = [];
            let energyScenarioList = defaultChoisesByRegion[regionId];
            let compatibleEnergyScenarios = comatibleEnergyScenarioPerRegion[regionId];

            if (energyScenarioList) {
                energyScenarioList.forEach(function(energyScenario) {
                    let energyScenarioKey = Object.keys(energyScenario)[0];
                    let compatibleBuildingTypes = findObjectValueByKey(compatibleEnergyScenarios, energyScenarioKey);

                    if (compatibleBuildingTypes && Array.isArray(compatibleBuildingTypes) && compatibleBuildingTypes.length) {
                        if (compatibleBuildingTypes.indexOf(buildingId) > -1) {
                            compatibleEnergyScenarioList.push(energyScenario);
                        }
                    } else {
                        compatibleEnergyScenarioList.push(energyScenario);
                    }
                });
            }

            return compatibleEnergyScenarioList
        }

        function onChangeEnergyScenario() {

            let currVal = $("#energyScenario").val()
            let rows = $("#" + "energyScenarioParams").find("tr")

            switch(currVal){
                case "${passivhausId}" : $(rows).removeClass("hidden"); break;
                default : $(rows).addClass("hidden"); break;
            }

        }

        function buildSelectCD(elementId, listValues, defaultValue, defaultText, ifOneSelect = false) {

            $(elementId).empty();
            
            let option = $('<option></option>').attr("value", "").text(defaultText);
            $(elementId).append(option);
            if(listValues) {
                listValues.forEach(el => {
                    option = $('<option></option>').attr("value", Object.keys(el)).text(Object.values(el));
                    if(defaultValue && defaultValue == Object.keys(el)){option.attr("selected", true)}
                    $(elementId).append(option);
                });
            }
            if(ifOneSelect){
                //If there is only one option (the other one is the void val)
                let options =  $(elementId).children("option")
                if(options && options.length == 2){
                    $(options[1]).attr("selected", true)
                }
            }
            $(elementId).trigger("change"); // TRIGGERS showEnergySection if elementId is buildingType
        }

        function getDefaultForPrimaryEnergy(energyScenarioList, regionId, buildingId) {
            var defaultEnergyScenario = null;
            var defaultEnergyScenarios = defaultEnergyScenariosPerBuildingType[regionId];

            if (energyScenarioList) {
                for (var i = 0; i < energyScenarioList.length; i++) {
                    var energyScenario = energyScenarioList[i];
                    var energyScenarioKey = Object.keys(energyScenario)[0];
                    var compatibleBuildingTypes = findObjectValueByKey(defaultEnergyScenarios, energyScenarioKey);

                    if (Array.isArray(compatibleBuildingTypes)) {
                        if (compatibleBuildingTypes.length === 0||compatibleBuildingTypes.indexOf(buildingId) > -1) {
                            defaultEnergyScenario = energyScenarioKey;
                            break;
                        }
                    }
                }
            }
            return defaultEnergyScenario
        }

        function getDefaultForAlternativeEnergy(energyScenarioList, regionId, buildingId) {
            var defaultEnergyScenario = null;
            var defaultEnergyScenarios = alternativeDefaultEnergyScenariosPerBuildingType[regionId];

            if (energyScenarioList) {
                for (var i = 0; i < energyScenarioList.length; i++) {
                    var energyScenario = energyScenarioList[i];
                    var energyScenarioKey = Object.keys(energyScenario)[0];
                    var compatibleBuildingTypes = findObjectValueByKey(defaultEnergyScenarios, energyScenarioKey);

                    if (Array.isArray(compatibleBuildingTypes)) {
                        if (compatibleBuildingTypes.length === 0||compatibleBuildingTypes.indexOf(buildingId) > -1) {
                            defaultEnergyScenario = energyScenarioKey;
                            break;
                        }
                    }
                }
            }
            return defaultEnergyScenario
        }

        function showEnergySection() {
            let buildingType = $("#buildingType").val();
            if ($("#buildingType").val()) {
                $("#buildingType").removeClass("redBorder");
            } else {
                $("#buildingType").addClass("redBorder");
            }

            if (surfaceDeScomblesAllowedBuildingTypes.includes(buildingType)) {
                $('#surfaceDeScomblesRow').removeClass("hiddenByBuildingType");
            } else {
                $('#surfaceDeScomblesRow').addClass("hiddenByBuildingType");
            }

            if (nombreDeLogementAllowedBuildingTypes.includes(buildingType)) {
                $('#nombreDeLogementRow').removeClass("hiddenByBuildingType");
            } else {
                $('#nombreDeLogementRow').addClass("hiddenByBuildingType");
            }
            //let regionReference = $("#regionReference").val() changed
            let regionReference = $("#regionReference").attr("defaultOption");
            let energyScenarioList;
            if(buildingType && regionReference){
                energyScenarioList = validEnergyScenarios(regionReference, buildingType)
            }
            
            $(".earthquakeCheckbox").prop('checked', false);

            if (earthquakeZones.length) {
                var atleastOneZone = false;
                for (let i = 0; i < earthquakeZones.length; i++) {
                    var earthquakeZone = earthquakeZones[i];

                    if (Array.isArray(earthquakeZone.compatibleBuildingTypes) && (earthquakeZone.compatibleBuildingTypes.length === 0||earthquakeZone.compatibleBuildingTypes.indexOf(buildingType) > -1)) {
                        atleastOneZone = true;
                        $('#'+earthquakeZone.zone+'EarthquakeZoneContainer').removeClass("hiddenByBuildingType");
                    } else {
                        $('#'+earthquakeZone.zone+'EarthquakeZoneContainer').addClass("hiddenByBuildingType");
                    }
                }

                if (atleastOneZone) {
                    $('#headingEarthquakeZoneContainer').removeClass("hiddenByBuildingType");
                } else {
                    $('#headingEarthquakeZoneContainer').addClass("hiddenByBuildingType");
                }
            }

            if(energyScenarioList && energyScenarioList.length > 0){
                var defaultForPrimaryEnergy = getDefaultForPrimaryEnergy(energyScenarioList, regionReference, buildingType);
                var defaultForAlternativeEnergy = getDefaultForAlternativeEnergy(energyScenarioList, regionReference, buildingType);

                buildSelectCD("#energyScenario", energyScenarioList, defaultForPrimaryEnergy, "${defaultTextSelect}");
                buildSelectCD("#alternativeScenario", energyScenarioList, defaultForAlternativeEnergy, "${defaultTextSelect}");
                $("#div_energy").removeClass("hidden");
            }else{
                $("#div_energy").addClass("hidden");

            }
            changeBuildingTypeImage(buildingType);
        }

        function changeBuildingTypeImage(buildingType){
            //Building dimension values
            onChangeUpdatePopup();

            let img = document.getElementById("buildingTypeImage")
            let url = "/app/sec/util/getBuildingTypeImageClass"
            let json = {buildingType: buildingType}

            if(img){
                let successCallBack = (json) => {
                    if(json && json.className) {
                        $(img).parent().removeClass("hidden");
                        img.className = json.className
                    }
                };

                fetch(url, {
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    method: "POST",
                    body: JSON.stringify(json)
                }).then(
                    data => data.json()
                ).then(
                    json => successCallBack(json)
                ).catch(
                    error => console.log(error)
                )
            }

        }
        //********************************************************

        <%-- The method reset all the forms in the page --%>
        function resetForms() {
            Array.from(document.forms).forEach(el => el.reset());
        }

        //*************************************************
        //It will hides the main body and shows the spinner

        function showSpinner(){
            $("#main_body").addClass("hidden");
            $("#spinner").removeClass("hidden");

            $(this).off( "click" );
        }

        function hideSpinner(){
            $("#main_body").removeClass("hidden");
            $("#spinner").addClass("hidden");
        }
        //*************************************************

        // Input Field Numeric Class Validation
        function numericInputValidation(){
            let val = $(this).val();
            if (isNaN(val)) {
                val = val.replace(",",".")
                val = val.replace(/[^0-9\.]/g, '');
                if (val.split('.').length > 2) {
                    val = val.replace(/\.+$/, '');
                }
            }
            if (!$(this).hasClass("underground")&&!$(this).hasClass("allowZero")) {
                val = val.replace(/^0+/, '');
            }
            $(this).val(val);

            if (val) {
                $(this).popover('destroy');
                $(this).removeClass('redBorder');
                onChangeUpdatePopup()
            } else {
                $(this).addClass('redBorder');
                if (!$(this).hasClass("underground")) {
                    $(this).popover('show')
                }
            }
        }

        function onChangeUpdatePopup() {
            $(".help-on-change").on("keyup change", function(){
                if ($(".carbonTable").length) {
                    $(".onchangeUpdateTip").removeClass("hidden")
                }
            });
            $(".help-on-change-checkbox").on("click", function(){
                if ($(".carbonTable").length) {
                    $(".onchangeUpdateTip").removeClass("hidden")
                }
            });
            $(".help-on-change-select").on("change", function(){
                if ($(".carbonTable").length) {
                    $(".onchangeUpdateTip").removeClass("hidden")
                }
            });
        }

        async function userclicksnext() {

            <%-- Checkbox values --%>
            var foundationsCheck = $('#foundationsCheck').is(":checked");
            var groundSlabCheck = $('#groundSlabCheck').is(":checked");
            var enclosureCheck = $('#enclosureCheck').is(":checked");
            var structureCheck = $('#structureCheck').is(":checked");
            var finishesCheck = $('#finishesCheck').is(":checked");
            var servicesCheck = $('#servicesCheck').is(":checked");
            var externalAreasCheck = $('#externalAreasCheck').is(":checked");
            var defaultsCheck = $('#defaultsCheck').is(":checked");

            <%-- Dropdown Menu values --%>
            var buildingType = $('#buildingType').val();

            <%-- Number Input Filed values --%>
            var grossFloorArea = $('#grossFloorArea').val();
            var aboveGroundFloors = $('#aboveGroundFloors').val();
            var undergroundHeatedFloors = $('#undergroundHeatedFloors').val();
            var undergroundUnheatedFloors = $('#undergroundUnheatedFloors').val();

            var isEarthquakeZoneMedium = $('#isEarthquakeZoneMedium').is(":checked");
            var isEarthquakeZoneHigh = $('#isEarthquakeZoneHigh').is(":checked");

            var regionReferenceId = $('#regionReference').attr("defaultOption");


            var heightBuilding = $('#heightBuilding').val();
            var widthBuinding = $('#widthBuinding').val();
            var depthBuilding = $('#depthBuilding').val();
            var internalFloorHeight = $('#internalFloorHeight').val();
            var maxSpanWithoutColumns_m = $('#maxSpanWithoutColumns_m').val();
            var loadBearingShare = $('#loadBearingShare').val();
            var numberStaircases = $('#numberStaircases').val();
            var totalFloors = $('#totalFloors').val();
            var shapeEfficiencyFactor = $('#shapeEfficiencyFactor').val();
            var totalNetArea = $('#totalNetArea').val();

            var floorThickness_m = $('#floorThickness_m').val();
            var envelopeThickness_m = $('#envelopeThickness_m').val();
            var roofShapeEfficiencyFactor = $('#roofShapeEfficiencyFactor').val();


            var queryString = 'foundationsCheck=' + foundationsCheck  + '&groundSlabCheck=' + groundSlabCheck
                            + '&enclosureCheck=' + enclosureCheck + '&structureCheck=' + structureCheck
                            + '&finishesCheck=' + finishesCheck + '&externalAreasCheck=' + externalAreasCheck
                            + "&defaultsCheck=" + defaultsCheck + '&buildingType=' + buildingType
                            + '&grossFloorArea=' + grossFloorArea + '&aboveGroundFloors=' + aboveGroundFloors
                            + '&undergroundHeatedFloors=' + undergroundHeatedFloors + '&undergroundUnheatedFloors=' + undergroundUnheatedFloors
                            + '&isEarthquakeZoneMedium=' + isEarthquakeZoneMedium + '&isEarthquakeZoneHigh=' + isEarthquakeZoneHigh + '&entityId=' + "${entityId}" + '&indicatorId=' + "${indicatorId}" + '&servicesCheck=' + servicesCheck + '&regionReferenceId=' + regionReferenceId
                            + '&heightBuilding=' + heightBuilding + '&widthBuinding=' + widthBuinding + '&depthBuilding=' + depthBuilding
                            + '&internalFloorHeight=' + internalFloorHeight + '&maxSpanWithoutColumns_m=' + maxSpanWithoutColumns_m + '&floorThickness_m=' + floorThickness_m + '&envelopeThickness_m=' + envelopeThickness_m + '&roofShapeEfficiencyFactor=' + roofShapeEfficiencyFactor
                            + '&numberStaircases=' + numberStaircases + '&totalFloors=' + totalFloors + '&shapeEfficiencyFactor=' + shapeEfficiencyFactor
                            + '&totalNetArea=' + totalNetArea + '&loadBearingShare=' + loadBearingShare + '&sessionId=' + '${sessionId}' + '&designNetFloorArea=' + designNetFloorArea + '&designHeatedArea=' + designHeatedArea;

            <%-- Div that contains the template of the table from the controller --%>
            var building_dimensions_div = $('#building_dimensions_div');
            var column_areas_div = $('#column_areas_div');
            var uploadFile_div = $('#uploadFile_div');
            <%--Bind update message when onchange value --%>
            $(".onchangeUpdateTip").addClass("hidden");


            $.ajax({
                type: 'POST',
                data: queryString,
                url: '/app/sec/util/earlyPhaseToolValidCalculation',
                success: function (data) {
                    column_areas_div.empty();
                    if (data !== "error") {
                        if(data && data.data){
                            building_dimensions_div.empty();
                            uploadFile_div.hide()
                            if(data.data.column_areas_div) column_areas_div.append(data.data.column_areas_div)
                            if(data.data.building_dimensions_div) building_dimensions_div.append(data.data.building_dimensions_div)
                            showPersistInputs()
                            if (moreParamsExpanded) {
                                $('#showMoreBuildingDimensions').trigger('click');
                            }
                            $(".buttonCreate").removeClass("disabled").attr("disabled", false);

                            $("#grossFloorArea").removeClass("redBorder");
                            $("#aboveGroundFloors").removeClass("redBorder");
                            $("#buildingType").removeClass("redBorder");

                        }
                    } else {
                        $("#totalNetArea").text("");
                        $("<div id='alert' class='alert alert-warning hide-on-print'>\n" +
                            "  <i class=\"fas fa-exclamation pull-left\" style=\"font-size: large; margin-right: 8px;\"></i>\n" +
                            "  <i data-dismiss=\"alert\" class=\"fas fa-circle-notch fa-spin close\" style=\"top: 0px;\"></i>\n" +
                            "  <strong>Missing parameters</strong>\n" +
                            "</div>").prependTo("#messageContent");
                        setTimeout( () => $("#alert").fadeOut(), 7000);

                        //Set Border Red To missig parameters void
                        if(!$("#grossFloorArea").val()){ $("#grossFloorArea").addClass("redBorder"); }
                        else{ $("#grossFloorArea").removeClass("redBorder"); }

                        if(!$("#aboveGroundFloors").val()){ $("#aboveGroundFloors").addClass("redBorder"); }
                        else{ $("#aboveGroundFloors").removeClass("redBorder");}

                        if(!$("#buildingType").val()){ $("#buildingType").addClass("redBorder"); }
                        else{ $("#buildingType").removeClass("redBorder"); }
                    }
                }
            });

        }

        function showPersistInputs(){
            if(Array.isArray(persistInputs)){
                persistInputs.forEach(function(input) {
                    $("." + input).trigger('click');

                    if ("widthBuinding" === input && persistInputs.indexOf('depthBuilding') === -1) {
                        $('.depthBuilding').closest('td').prepend("<i style='color: orange; font-size: 12px;' class='fa fa-exclamation-triangle depthWarning' data-content='${g.message(code: 'simulationTool.depth.warning')}'></i>")

                        $('.depthWarning').popover({
                            placement : 'top',
                            template: '<div class="popover" style=" display: block; max-width: 350px;" ><div class="arrow"></div><div class="popover-content" style="border-style: solid; border-color: whitesmoke; border-width: 1px"></div></div>',
                            trigger: 'hover',
                            html: true,
                            container: 'body'
                        });
                    }
                });
            }
        }

        function hideBuildingDimension(){

            if(!($("#grossFloorArea").val() && $("#aboveGroundFloors").val() && $("#buildingType").val())){
                $(".hideBuildingDimension").hide()
            }
        }

        function sumNumberFloor(){

            var a = $("#aboveGroundFloors").val();
            var b = $("#undergroundHeatedFloors").val();
            var c = $("#undergroundUnheatedFloors").val();
            var sum = new Number(a) + new Number(b) + new Number(c);
            if(sum > 0 ){
                $("#sumFloors").text(sum);
            } else {
                $("#sumFloors").text("");
            }
        }

        function onShow(element, check) {

            if (check && check === true) {
                $("."+ element).removeClass("hidden");
            }else{
                $("."+ element).addClass("hidden");
            }
        }

        function setScope() {

            let foundationsCheck =      $('#foundationsCheck').is(":checked");
            let groundSlabCheck =       $('#groundSlabCheck').is(":checked");
            let enclosureCheck =        $('#enclosureCheck').is(":checked");
            let structureCheck =        $('#structureCheck').is(":checked");
            let finishesCheck =         $('#finishesCheck').is(":checked");
            let servicesCheck =         $('#servicesCheck').is(":checked");
            let externalAreasCheck =    $('#externalAreasCheck').is(":checked");
            let defaultsCheck =         $('#defaultsCheck').is(":checked");

            if(!foundationsCheck){
                $('.foundationsGroupInput').val(0);
            }

            if(!groundSlabCheck){
                $('.groundSlabGroupInput').val(0);
            }

            if(!structureCheck){
                $('.structureGroupInput').val(0);
            }

            if(!enclosureCheck){
                $('.enclosureGroupInput').val(0);
            }

            if(!finishesCheck){
                $('.finishesGroupInput').val(0);
            }

            if(!servicesCheck){
                $('.servicesGroupInput').val(0);
            }

            if (!externalAreasCheck) {
                $('.externalAreasGroupInput').val(0);
            } else {
                $('.externalAreasGroupInput:hidden').val(0);
            }

            if (!defaultsCheck) {
                $('.defaultsGroupInput').val(0);
            } else {
                $('.defaultsGroupInput:hidden').val(0);
            }
        }

        function sendForm() {
            let missingParams = false;
            let buildingType = $("#buildingType").val();
            if ($('#assessmentPeriod').length && !$('#assessmentPeriod').val()) {
                $("#assessmentPeriod").addClass("redBorder");
                missingParams = true;
            } else if ($('#surfaceDeReference').length && !$('#surfaceDeReference').val()) {
                $("#surfaceDeReference").addClass("redBorder");
                missingParams = true;
            } else if ($('#surfaceDeScombles').length && !$('#surfaceDeScombles').val() && surfaceDeScomblesAllowedBuildingTypes.includes(buildingType)) {
                $("#surfaceDeScombles").addClass("redBorder");
                missingParams = true;
            } else if ($('#nombreDeLogement').length && !$('#nombreDeLogement').val() && nombreDeLogementAllowedBuildingTypes.includes(buildingType)) {
                $("#nombreDeLogement").addClass("redBorder");
                missingParams = true;
            } else {
                if (!$('#totalNetArea').length) {
                    $("#simulationForm").append('<input type=\"hidden\" id=\"totalNetArea\" name=\"totalNetArea\" value=\"'+$('#totalNetAreaLink').attr('data-quantity')+'\" />');
                }
                setScope();
                showSpinner();
                let defaultVal =  $("#regionReference").attr("defaultOption");
                $("#regionReference").val(defaultVal);
                $("#simulationForm").trigger('submit');
                checkConnectionCD("${sessionId}")
            }
            if (missingParams) {
                $("<div id='alert' class='alert'>\n" +
                    "  <button data-dismiss='alert' class='close' type='button'>×</button>\n" +
                    "  <strong>Missing parameters</strong>\n" +
                    "</div>").prependTo("#messageContent");
                setTimeout( () => $("#alert").fadeOut(), 3000);
                return false
            }
        }

        function errorServerReply(event){
            errorSwal('warning', "${g.message(code : "popup.noServer.title")}", "${g.message(code : "popup.noServer.body")}",  event)
        }

        function showUndergroundFloors(element){

            let toHide = $(element).siblings(".toHide")
            let i = $(element).find("i")

            if(toHide.hasClass("hidden")){
                toHide.removeClass("hidden")
                $(i).removeClass('fa fa-plus').addClass('fa fa-minus')

            }else {
                toHide.addClass("hidden")
                $(i).removeClass('fa fa-minus').addClass('fa fa-plus')
            }

        }


        //******************************************************************************************
        //Upload template function
        //******************************************************************************************
        function uploadTemplate() {

            const input = document.getElementById('file');
            const file = input && input.files ? input.files[0] : null
            const regionId = $("#regionReference").attr("defaultOption")

            const url = "/app/sec/util/uploadSimulationToolAreasTemplate"

            if(file){

                if(file.size > (30*1024)){
                    Swal.fire({icon: "warning", title: "${g.message(code: "simulationTool.fileToBig")}"})
                    return
                }

                const formData = new FormData();
                formData.append('file', file);
                formData.append('regionId', regionId);
                formData.append('indicatorId', '${indicatorId}');

                fetch(url, {
                    method: 'POST',
                    body: formData
                }).then(
                    response => response.json()
                ).then(
                    data => setValues(data)
                ).catch(
                    error => errorCallbackTemplate(error) // Handle the error response object
                );
            }else{
                //Add red border to input file field
                $("#file").addClass("redBorder")
            }

        }


        function setValues(json){

            const map = json && json.data ? json.data : null
            const errorMessage = json && json.error ? json.error : null

            if(errorMessage) showErrorMessage(errorMessage)

            if(map){

                const errorMess = map["error"]

                if(errorMess){
                    showErrorMessage(errorMess)
                    delete map["error"]
                }

                var building_dimensions_div = $('#building_dimensions_div');
                var column_areas_div = $('#column_areas_div');
                building_dimensions_div.empty()
                column_areas_div.empty()

                if(map.building_dimensions_div) building_dimensions_div.append(map.building_dimensions_div)
                if(map.column_areas_div) column_areas_div.append(map.column_areas_div)
                let gfa = isNaN(map.gfa) ? 1 : map.gfa

                $('#grossFloorArea').val(gfa)
                setAllEnabledScope()
                $(".buttonCreate").removeClass("disabled").attr("disabled", false);

            }

            feedbackResult("uploadResult", "icon-done")
            $("#file").removeClass("redBorder")

        }

        function showErrorMessage(errorMessage){

            let html =
                "<div id='alert' class='alert'>\n" +
                "<button data-dismiss='alert' class='close' type='button'>×</button>\n"+
                "<strong>" + errorMessage + "</strong>\n"+
                "</div>"

            $(html).prependTo("#messageContent");

        }

        function errorCallbackTemplate(error) {
            console.log(error)
            feedbackResult("errorResult", "fa fa-times")
        }

        function feedbackResult(elementId, classes){

            $("#" + elementId).removeClass().addClass(classes)
            $("#" + elementId).show()
            $("#" + elementId).fadeOut(1500)
        }

        function showHideDivWithI(element, divIdControlled){

            var div = $('#' + divIdControlled)
            var i = $(element).children("i")[0]
            i = $(i)

            if(!i || !div) return null

            var isHidden = div.hasClass("hide")

            if(isHidden){
                div.removeClass("hide")
                i.removeClass("fa-plus")
                i.addClass("fa-minus")
            } else {
                div.addClass("hide")
                i.removeClass("fa-minus")
                i.addClass("fa-plus")
            }
        }
        //******************************************************************************************

        function setAllEnabledScope(){
            let foundationsCheck = setGroupIfEnabled("foundationsCheck")
            let groundSlabCheck = setGroupIfEnabled("groundSlabCheck")
            let enclosureCheck = setGroupIfEnabled("enclosureCheck")
            let structureCheck = setGroupIfEnabled("structureCheck")
            let finishesCheck = setGroupIfEnabled("finishesCheck")
            let servicesCheck = setGroupIfEnabled("servicesCheck")
            let externalAreasCheck = setGroupIfEnabled("externalAreasCheck")
            let defaultsCheck = setGroupIfEnabled("defaultsCheck")

            onShow('foundations', foundationsCheck);
            onShow('groundSlab', groundSlabCheck);
            onShow('enclosure', enclosureCheck);
            onShow('structure', structureCheck);
            onShow('finishes', finishesCheck);
            onShow('services', servicesCheck);
            onShow('externalAreas', externalAreasCheck);
            onShow('defaults', defaultsCheck);
        }

        function setGroupIfEnabled(groupId){

            let group = $('#'+ groupId)
            let isDisabled = group.attr("disabled")

            if(!isDisabled){
                group.prop('checked', true);
            }

            return !isDisabled
        }

        //***************************************************************************
        createAutocomplete()

        function createAutocomplete(){

            let list = ${regionOptions ?: []}

            if(list && list.length > 0){
                $("#regionReference").devbridgeAutocomplete({
                    minChars: 0,
                    //noSuggestionNotice: energyType.defaultOptionName,
                    lookup: list,
                    onSearchStart: function(query){
                        let val = new String($(this).val())
                        let pttrn = /^\s*/;
                        let start = val.match(pttrn)[0].length
                        val = val.substring(start)
                        $(this).val(val)
                    },
                    formatResult: function (suggestion) {
                        let img = new Image(25)
                        img.src = suggestion.data.country
                        let div = document.createElement("div")
                        let text = document.createTextNode(" " + suggestion.value);
                        div.appendChild(img)
                        div.appendChild(text)
                        return div.innerHTML;
                    },
                    onSelect: function (suggestion) {
                        $(this).attr("defaultOption", suggestion.data.resourceId)
                        $(this).attr("defaultOptionName", suggestion.value)
                        var listOfAllowedChoises = suggestion.data.allowedConstructionGroups;

                        $('.areaInputRow').each(function () {
                            var inputrelevantForSelected = false;
                            for (var i = 0; i < listOfAllowedChoises.length; i++) {
                                var listing = listOfAllowedChoises[i];

                                if ($(this).hasClass(listing+"Input")) {
                                    inputrelevantForSelected = true;
                                    break;
                                }
                            }

                            if (!inputrelevantForSelected) {
                                $(this).hide();
                            } else {
                                $(this).show();
                            }
                        });
                        designNetFloorArea = suggestion.data.designGIFA;
                        designHeatedArea = suggestion.data.designHeatedArea;
                        var hideEarthquakeZone = suggestion.data.hideEarthquakeZone;
                        earthquakeZones = suggestion.earthquakeZones;
                        var earthquakeZoneContainer = $(".earthquakeZoneContainer");

                        if (hideEarthquakeZone) {
                            if (earthquakeZoneContainer.is(":visible")) {
                                earthquakeZoneContainer.addClass("hiddenByBuildingType");
                            }
                        } else {
                            earthquakeZoneContainer.removeClass("hiddenByBuildingType");
                        }

                        const surfaceDeScombles = suggestion.data.surfaceCombles;
                        const nombreDeLogement = suggestion.data.nombreDeLogement;

                        let buildingType = $("#buildingType").val();
                        if (surfaceDeScombles) {
                            surfaceDeScomblesAllowedBuildingTypes = surfaceDeScombles.showQuestionForBuildingTypes
                            if (surfaceDeScomblesAllowedBuildingTypes.includes(buildingType)) {
                                $('#surfaceDeScomblesRow').removeClass("hiddenByBuildingType");
                            } else {
                                $('#surfaceDeScomblesRow').addClass("hiddenByBuildingType");
                            }
                        }

                        if (nombreDeLogement) {
                            nombreDeLogementAllowedBuildingTypes = nombreDeLogement.showQuestionForBuildingTypes
                            if (nombreDeLogementAllowedBuildingTypes.includes(buildingType)) {
                                $('#nombreDeLogementRow').removeClass("hiddenByBuildingType");
                            } else {
                                $('#nombreDeLogementRow').addClass("hiddenByBuildingType");
                            }
                        }

                        $('.foundationOptions').each(function () {
                            if ($(this).attr("data-regionReferenceId").includes(suggestion.data.resourceId)) {
                                $(this).show();
                            } else {
                                $(this).hide();
                            }
                        });
                        enableScopesAndSo(suggestion.data.resourceId);
                        buildSelectCD("#buildingType", buildingTypeByRegion[$(this).attr("defaultOption")],"${defaultBuildingType}", "", true);
                    }
                });
                $("#regionReference").on('blur', function () {
                    let name = $(this).attr("defaultOptionName");
                    $(this).val(name)
                })
            }
        }

        function showAllOptions(type){
            setTimeout( () => {
                $("#regionReference").val("").devbridgeAutocomplete("onValueChange");
                $("#regionReference").trigger('focus');
            }, 100)
        }

        //Disable and enable scopes
        var enabledBuildingElementByRegion = ${enabledBuildingElementByRegion ?: [:]};
        var selectedBuildingElementByRegion = ${selectedBuildingElementByRegion ?: [:]};
        var mappedScopes = ${mappedScopes ? mappedScopes as grails.converters.JSON : []};

        $(document).ready(function() {
            let name = $("#regionReference").attr("defaultOptionName")
            $("#regionReference").val(name);
            enableScopesAndSo("${defaultRegion}")

            $(".earthquakeCheckbox").on('click', function() {
                if (!$(this).is(":checked")) {
                    $(".earthquakeCheckbox").prop("checked", false);
                } else {
                    $(".earthquakeCheckbox").prop("checked", false);
                    $(this).prop("checked", true);
                }
            });

            var helpPopSettings = {
                placement: 'top',
                template: '<div class="popover popover-fade" style="display: block; max-width: 300px;"><div class="arrow"></div><div style="font-weight: normal !important;" class="popover-content"></div></div>'
            };
            $(".infopopover[rel=popover]").popover(helpPopSettings)
        });

        function enableScopesAndSo(regionId){
            let checkGroups = ["foundationsGroup", "groundSlabGroup", "structureGroup", "enclosureGroup", "internalWallsGroup", "finishesGroup", "servicesGroup", "externalAreasGroup", "defaultsGroup"];
            let enabledScopes = enabledBuildingElementByRegion ? enabledBuildingElementByRegion[regionId] : null;
            let selectedScopes = selectedBuildingElementByRegion ? selectedBuildingElementByRegion[regionId] : null;
            if(Array.isArray(enabledScopes)){
                checkGroups.forEach(e => {
                    if(enabledScopes.find(i => i === e)){
                        enableScopeLabel(e, selectedScopes, mappedScopes)
                    } else {
                        disableScopeLabel(e)
                    }
                })
            }

        }

        function enableScopeLabel(groupId, selected, mapped) {
            let label = $("#label-" + groupId);
            label.removeClass("disabled");
            if ((selected && selected.includes(groupId))||(mappedScopes && mappedScopes.includes(groupId))) {
                label.find("input").prop('checked', true).prop('disabled', false);
            } else {
                label.find("input").prop('checked', false).prop('disabled', false);
            }
            label.show();
        }

        function disableScopeLabel(groupId) {
            let label = $("#label-" + groupId);
            label.find("input").prop('checked', false).attr("disabled", "true");
            label.addClass("disabled");
            label.hide();
        }
    </script>
    </body>
</html>



