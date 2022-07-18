<%@ page import="static com.bionova.optimi.core.domain.mongo.SimulationToolEnergyTypeBuilder.PRIMARY_HEAT as PRIMARY_HEAT; static com.bionova.optimi.core.domain.mongo.SimulationToolEnergyTypeBuilder.SECONDARY_HEAT as SECONDARY_HEAT; com.bionova.optimi.core.domain.mongo.SimulationToolEnergyTypeBuilder; static com.bionova.optimi.core.service.SimulationToolService.roundToString as roundToString; grails.converters.JSON; com.bionova.optimi.core.domain.mongo.SimulationToolConstituent; com.bionova.optimi.core.domain.mongo.SimulationToolConstruction; groovy.json.JsonBuilder" %>
<%@ page import="static com.bionova.optimi.core.service.SimulationToolService.conversionUnit as conversionUnit" %>
<%@ page import="static com.bionova.optimi.core.service.SimulationToolService.conversionUnitFactor as conversionUnitFactor" %>
<%@ page import="com.bionova.optimi.util.UnitConversionUtil" %>
<%@ page import="com.bionova.optimi.core.service.SimulationToolService" %>
<%@ page import="static com.bionova.optimi.core.service.SimulationToolService.RESET_DEFAULT_MATERIAL as RESET_DEFAULT_MATERIAL" %>
<g:set var="simulationToolService" bean="simulationToolService"/>
<g:set var="indicatorService" bean="indicatorService"/>

<%-- Copyright (c) 2012 by Bionova Oy --%>
<g:set var="initialCo2e"  value="${((modelGroups?.collect{it.co2e?:0}?.sum() ?:0) + (energyTypeList?.collect{!isAlternativeScenarioEnergy ? (it.co2e ?:0) : 0 }?.sum() ?:0))}"/>
<g:set var="initialCost" value="${lccIndicator ? ((modelGroups?.collect{it.totalCost? it.totalCost.round(0) :0D}?.sum() ?:0D) + (energyTypeList?.collect{!isAlternativeScenarioEnergy ? (it.calculatedCost ? it.calculatedCost.round(0) :0D) : 0D}?.sum() ?:0D))?.round(0) : 0}"/>

<g:set var="initialCo2eIntensity"  value="${initialCo2e / grossFloorArea * 1000}"/>
<g:set var="initialCostIntensity"  value="${lccIndicator && initialCost ? initialCost / grossFloorArea : 0}"/>

<g:set var="byConstructionGroup" value ="${"byConstructionGroup"}" />
<g:set var="byBuildingElement" value ="${"byBuildingElement"}" />
<g:set var="byTotal" value ="${"byTotal"}" />
<!doctype html>
<html>
	<head>
        <%--<asset:javascript src="360optimiES6.js.es6"/>--%>
        <asset:javascript src="360optimi.js"/>
        <asset:javascript src="jquery.autocomplete.js?v=${grailsApplication.metadata.'app.version'}"/>

        <script type="text/javascript">
            var timerStart = Date.now();

        </script>

		<meta name="layout" content="main">
        <meta name="format-detection" content="telephone=no"/>

        <style>

            .col_number{
                text-align: right !important;
                width:90px !important;
                min-width: 90px !important;
            }
            .col_text{
                text-align: center !important;
                width:90px !important;
                min-width: 90px !important;
            }

            .col_number-large{
                text-align: right !important;
                min-width:110px !important;
            }

            .col_number-small{
                text-align: right !important;
                width:70px !important;
            }

            .energyautocomplete{
                width:170px !important;
            }

            .col_intensity{
                text-align: right !important;
                width:90px !important;
            }

            input.numeric {
                text-align: right !important;
                width: 40px !important;
            }

            .div-input > label {
                display: inline;
            }

            td{
                vertical-align: middle;
            }

            .div_chart {
                text-align: center;
                margin-bottom: 30px;
            }

            thead tr.first_row th{
                text-align: center;
                height: 10px;
                border: 0px;
                padding: 4px 0px 0px 0px !important;
                margin: 0px;
            }

            thead tr.second_row th{
                background: whitesmoke;
                text-align: left;
            }

            tbody.body_carbon td {
                text-align: left;
            }

            table .first_col {
                text-align: left !important;
                width:550px !important;
            }

            table .first_subCol {
                text-align: left !important;
                max-width:400px !important;
                min-width: 400px !important;
            }
            table .first_subCol_lcc {
                text-align: left !important;
                min-width:280px !important;
                max-width:280px !important;
            }

            table .col_edit {
                text-align:center !important;
                cursor: pointer;
                width: 20px !important;
            }
            i.impactTitle {
                font-style: normal;
                font-weight: bold;
            }

            .hide-row{
                color: white;
                border-color: white;
            }

            tr.head_construction th {
                padding: 4px !important;
            }
            #groupingDropdown > .caret{
                margin-top: 7px;
            }
            #groupingDropdownCost > .caret{
                margin-top: 7px;
            }
            .hide_text{
                padding: 45px;
            }
            td.comment {
                padding-left: 20px !important;
            }



        </style>

        <scritp>
            <asset:javascript src="chartJsV2.7.js"/>
        </scritp>


	</head>

	<body>
<opt:link name="goBack" class="hidden" controller="design" action="simulationTool" params="${[entityId: entityId, designId: designId, indicatorId: indicatorId]}"/>
        <%-- PATH SECTION --%>
        <div class="container">
        <h4>
            <sec:ifLoggedIn>
                <opt:link controller="main" removeEntityId="true">
                    <g:message code="main" />
                </opt:link>
                >
                <opt:link controller="entity" action="show" id="${entityId}" >
                    <g:abbr value="${entity?.name}" maxLength="20" />
                </opt:link>
                > <g:abbr value="${design?.name}" maxLength="38" />
                >
                <g:if test="${!isDraftPresent}">
                    <opt:link controller="design" action="simulationTool" params="${[entityId: entityId, designId: designId, indicatorId: indicatorId]}">
                        <g:message code="simulationTool.firstPage.heading" />
                    </opt:link>
                </g:if>
                <g:else>
                    <g:message code="simulationTool.firstPage.heading" />
                </g:else>
                > <g:message code="simulationTool.secondPage.heading" />
            </sec:ifLoggedIn>
        </h4>
        </div>

        <%-- PATH SECTION --%>
        <div id="main_body" class="container section">

            <div class="section body">

                <div class="container" style="border-bottom: 1px lightgrey solid; margin-bottom: 15px;">
                    <h2 style="font-size: 25px">
                        <img style="width: 50px;" src="/app/assets/simulationTool_icon/simulationTool_icon.png">
                        <g:message code="simulationTool.secondPage.newHeading" />: <g:message code="simulationTool.secondPage.chartTitle" />
                    </h2>
                </div>

                <%-- THE DIV CONTAINER OF CHART --%>
                <div class="container"  style="font-size: 16px; text-align: -moz-center; text-align: -webkit-center; display: inline-block">
                    <table style="display: inline-block"><tr>
                        <td><g:message code="baseline"/> CO<sub>2</sub>e</td><%--
                            <td><i id="initialImpact" class="impactTitle">${roundToString(initialCo2eIntensity / conversionUnitFactor(unitSystem, "m2"))} kg/${conversionUnit(unitSystem, "m2")}</i></td>
                            --%>
                        <td><i id="initialImpact" class="impactTitle"><span id="initialImpactBaseline">${roundToString(initialCo2eIntensity)}</span> kg/m<sup>2</sup> GFA</i></td>
                        <td><g:message code="optimized"/> CO<sub>2</sub>e</td>
                        <td><i id="newImpact" class="impactTitle">-</i> <b>kg/m<sup>2</sup> GFA</b></td>
                        <td><g:message code="simulationTool.carbonSaving"/></td>
                        <td><i id="diffImpact" class="impactTitle">-%</i></td>
                        <td><g:message code="simulationTool.projectLevel"/></td>
                        <td>
                            <i id="projectLevel" class="impactTitle">-</i> <b>${g.message(code : "simulationTool.tons")} CO<sub>2</sub>e</b>
                        </td>
                        <td><a class="pointerCursor" onclick="showRegionInformation()"><g:message code="simulationTool.here"/></a></td>
                    </tr></table>
                    <div class="btn-group pull-right"  style="display: inline-block; margin-top: 3px">
                        <a class="dropdown-toggle pointerCursor" id="groupingDropdown" data-toggle="dropdown"><g:message code="simulationTool.selectGrouping"/><span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a id="${byConstructionGroup}" name="groupLogic" class="selected" href="javascript:" style="font-size: 13px; font-weight: normal;" onclick="reGroupChart('${byConstructionGroup}')"><g:message code="simulationTool.byConstructionGroup"/></a></li>
                            <li><a id="${byBuildingElement}" name="groupLogic" href="javascript:" style="font-size: 13px; font-weight: normal;" onclick="reGroupChart('${byBuildingElement}')"><g:message code="simulationTool.byBuildingElement"/></a></li>
                            <li><a id="${byTotal}" name="groupLogic" href="javascript:" style="font-size: 13px; font-weight: normal;" onclick="reGroupChart('${byTotal}')"><g:message code="simulationTool.byTotal"/></a></li>
                        </ul>
                    </div>
                </div>
                <div class="clearfix"></div>

                <div class="div_chart">
                    <canvas id="myChart"  width="4" height="1" style="margin: fill; padding: auto"></canvas>
                </div>

                <g:if test="${lccIndicator}">
                    <%-- THE DIV CONTAINER OF COST CHART --%>
                    <div class="container"  style="font-size: 16px; text-align: -moz-center; text-align: -webkit-center; display: inline-block">
                        <table style="display: inline-block"><tr>
                            <td><g:message code="simulationTool.initialCost"/></td>
                            <td><i id="initialCost" class="impactTitle"><span id="initialCostBaseline">${roundToString(initialCostIntensity)}</span> ${costUnit}/m<sup>2</sup></i></td>
                            <td><g:message code="simulationTool.optimizedCost"/></td>
                            <td><i id="newCost" class="impactTitle">-</i> <b>${costUnit}/m<sup>2</sup></b></td>
                            <td><g:message code="simulationTool.costSaving"/></td>
                            <td><i id="diffCost" class="impactTitle">-%</i></td>
                            <td><g:message code="simulationTool.projectLevel"/></td>
                            <td>
                                <i id="projectLevelCost" class="impactTitle">-</i> <b>${costUnit}</b>
                            </td>
                        </tr></table>
                    </div>
                    <div class="clearfix"></div>

                    <div class="div_chart">
                        <h2>${indicatorService.getLocalizedShortName(lccIndicator)}</h2>
                        <br>
                        <canvas id="costChart"  width="4" height="1" style="margin: fill; padding: auto"></canvas>
                    </div>
                </g:if>

<%-- DIV SET DEFAULT MATERIAL --%>
<g:if test="${false && materialTypeList}">
                <div>
                    <label style="display: inline-block; margin-right: 10px"><g:message code="simulationTool.labelDefaultMaterial"/></label>
                    <g:select class="constshareinput" from="${materialTypeList}" name="materialTypeSelect" noSelection="${['': g.message(code:"simulationTool.chooseDefaultMaterial") ]}" optionValue="${{g.message(code: it)}}" optionKey="${{it}}" onchange="swalSetDefaultMaterialType()"/>
                </div>
</g:if>
<g:if test="${defaultChoicesSets}">
<div>
    <label for="defaulTypeSelect" style="display: inline-block; margin-right: 10px"><g:message code="simulationTool.labelDefaultMaterial"/></label>
    <g:select class="constshareinput" from="${defaultChoicesSets?: []}" name="defaulTypeSelect" value="${alternativeScenario}" noSelection="${['': g.message(code:"simulationTool.chooseDefaultMaterial") ]}" optionValue="${{it?.localizedName ?: ""}}" optionKey="${{it?.defaultChoiceId ?: ""}}" onchange="swalSetDefaultType(this)"/>
    <a href="#" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${g.message(code: "simulationTool.defaultTypeQmark")}">
        <i class="icon-question-sign"></i>
    </a>
</div>
</g:if>
<%-- DIV SET DEFAULT MATERIAL --%>

                <g:form controller="design" action="nextDashboard" name="nextDashboard" params="[entityId: entityId, designId: designId, indicatorId: indicatorId, sessionId: sessionId]">
                    <%-- TABLES BUILDING MATERIALS --%>
                    <div class="container">
                        <%-- <label><b>Carbon optimisation dashboard</b></label> --%>
                        <g:if test="${modelGroups && modelGroups.size() > 0}">
                            <%-- Caption for all the tables --%>
                            <table id = "table" class="table">
                                <thead>
                                    <tr class="second_row">
                                        <th class="first_subCol${lccIndicator ? '_lcc':'' }" id=""><g:message code="simulationTool.secondPage.buildingElement"/></th>
                                        <th class="col_number"><g:message code="amount"/></th>
                                        <th class="col_number gray-font"><g:message code="share"/></th>
                                        <g:if test="${lccIndicator}"><th class="col_text">${localizedCostText} ${costUnit}</th></g:if>
                                        <th class="col_number"><g:message code="tons"/> CO<sub>2</sub>e</th>
                                        <th class="col_number"><g:message code="carbonShare"/></th>
                                        <th class="col_number gray-font"><g:message code="carbonIntensity"/></th>
                                        <th class="col_number"></th>
                                        <th class="col_number col_edit"></th>
                                    </tr>
                                </thead>
                                <tbody class="body_carbon hide-row">
                                    <tr class="hidden hide-row">
                                    </tr>
                                </tbody>
                            </table>

                            <%-- TABLES CONSTRUCTIONS SECTION --%>

                            <div class="container">
                                <label><g:message code="simulationTool.secondPage.subHeading" /></label>
                                <br>
                            </div>

                            <%-- All the Construction Groups tables --%>
                            <g:set var="intitialImpactDiv" value="${initialCo2e?: 1}"/>
                            <g:each var="group" in="${modelGroups}">
                                <g:set var="dynamicDefaultGroup" value="${group.constructions?.get(0)?.dynamicDefault}" />
                                <%-- This div represents a constructiongGroup and for each construction an hidden element for the datasets. At the moment there is a flex layout--%>
                                <div style="display: flex;">
                                    <%-- constructionGroup div --%>
                                    <div>
                                        <table id = "table${group.oid}" class="table" style="margin: 3px;">

                                            <thead onclick="showOrHide(this)">
                                                <tr class="second_row head_construction">
                                                    <g:set var="groupUnit" value="${group.unit}" />
                                                    <g:set var="groupAmount" value="${roundToString((group.amount?:0) * conversionUnitFactor(unitSystem, groupUnit))}"/>
                                                    <g:set var="groupInitialAmount" value="${roundToString((group.initialAmount?:0) * conversionUnitFactor(unitSystem, groupUnit))}"/>
                                                    <th class="first_subCol${lccIndicator ? '_lcc':'' }"><i class="fa fa-plus"></i>&nbsp;&nbsp;&nbsp; ${group?.name}</th>
                                                    <th class="col_number" ><span id="quantityByGroup${group?.oid}" class="${dynamicDefaultGroup ? "dynamicDefaults" : ""}">${groupAmount} ${(groupAmount != groupInitialAmount)? "[<s>${groupInitialAmount}</s>]" : "" }</span> ${conversionUnit(unitSystem, groupUnit)}</th>
                                                    <th class="col_number hide_text${dynamicDefaultGroup ? "" : " groupShareHeading"}"></th>
                                                    <g:if test="${lccIndicator}"><g:set var="groupTotalCost" value="${group.getTotalCost()}"/><th class="col_number-large"><span class="headingTotalCost" data-group="${group.oid}" data-buildingElement="${constructionGroupList?.find({it.get("groupId")?.equals(group.oid)})?.get("buildingElement")}" data-roundedtotaloriginal="${roundToString(groupTotalCost)}" data-roundedtotal="${roundToString(groupTotalCost)}" id="headingTotalCost${group.oid}">${roundToString(groupTotalCost)}</span> ${costUnit}</th></g:if>
                                                    <th id="co2Group${group?.oid}" class="col_number">${roundToString(group?.co2e)} tn</th>
                                                    <th id="co2ShareGroup${group?.oid}" class="col_number">${roundToString(((group.co2e?:0)/(intitialImpactDiv)*100)?.doubleValue()) + "%"}</th>
                                                    <th class="col_intensity hide_text"><span class="hidden"><g:message code="carbonIntensity"/></span></th>
                                                    <th class="comment hide_text${dynamicDefaultGroup ? " hidden" : ""}" style="text-align: center"></th>
                                                    <th class="col_number col_edit"></th>
                                                </tr>
                                            </thead>
                                            <tbody class="body_carbon tbody_constructions hidden${dynamicDefaultGroup ? ' dynamicDefaultGroup' : ""}" data-groupName="${group.name}">
                                                <%--
                                                <tr><td><g:select name="addConstrSelect${group?.oid}" from="${group.constructions?.findAll{it.share == 0} ?: []}" optionKey="${{it?.constructionId}}" optionValue="${{it?.name}}" onchange="showContructionRow(this)"></g:select></td></tr>
                                                 --%>
                                                <g:each status="i" var="construction" in="${group.constructions}">
                                                    <g:set var="groupCo2e" value="${(group?.co2e && group?.co2e != 0 ) ? group?.co2e : 1 }"/>
                                                    <g:set var="co2Share" value="${((construction?.co2e?:0)/(groupCo2e)*100)?.doubleValue()}"/>
                                                    <g:set var="cId" value="${construction.constructionId}"/>
                                                <%-- Construction Row --%>
                                                    <tr id="constructionRow${cId}">
                                                        <td class="first_subCol${lccIndicator ? '_lcc':'' }" id="name${cId}" title="${construction.name}">
                                                            <g:set var="appendPrivateEye" value="${construction.name?.startsWith("(PRIVATE)")}" />
                                                            <span id="name_span${cId}"><g:if test="${appendPrivateEye}"><i class="far fa-eye-slash" aria-hidden="true"></i> </g:if>${abbr(value: construction.name, maxLength: '90', substringAfter: "(PRIVATE)")}</span>
                                                            <opt:renderDataCardBtn indicatorId="${indicatorId}" resourceId="${construction?.mirrorResourceId}" questionId="${construction?.questionId}" profileId="${construction?.profileId}" childEntityId="${entityId}" showGWP="true" infoId="${cId}" queryId="${construction?.queryId}" sectionId="${construction?.sectionId}"/>
                                                            <span class="rowIncompatible">
                                                            <g:if test="${construction?.isIncompatible}">
                                                                <a href="javascript:" class="cdDataIncompatiblePopover" rel="popover" data-trigger="hover" data-html="true" data-content="${g.message(code: "simulationTool.incompatibleBuilding")}">
                                                                    ${asset.image(src:"img/icon-warning.png", style:"margin-bottom: 5px; max-width:16px")}
                                                                </a>
                                                            </g:if>
                                                            </span>
                                                            <g:if test="${lccIndicator && !construction.userGivenCost}">
                                                                <i style="color: orange; font-size: 14px;" class="lccErrorPopover fa fa-exclamation-triangle" href="#" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${message(code: 'query.cost_calcalculation_failed')}"></i>
                                                            </g:if>
                                                            <span id="showHiddenConstructions${cId}" ></span>
                                                        </td>

<%-- Real value not converted but keeped in metric--%>
                                                        <input type="hidden" id="total${cId}" name="total${cId}" value="${roundToString(construction?.amount)}" onchange="" />
                                                        <g:set var="conversionFactor" value="${conversionUnitFactor(unitSystem, construction?.unit)}"/>
                                                        <g:set var="constructionUnitBySystem" value="${conversionUnit(unitSystem, construction?.unit)}" />
                                                        <g:set var="constructionAmountBySystem" value="${roundToString((construction?.amount?:0) * conversionFactor)}"/>
                                                        <td class="col_number"><span class="rowTotal" id="span_total${cId}">${constructionAmountBySystem}</span> <span class="rowTotal_unit" id="span_total_unit${cId}">${constructionUnitBySystem}</span></td>
                                                        <td class="col_number">
                                                            <i id="quantity_share_spinner${cId}" class='fas fa-circle-notch fa-spin oneClickColorScheme hidden'></i>
                                                            <input class="numeric constshareinput percentageShare${dynamicDefaultGroup ? " hidden" : ""}" type="text" id="quantity_share${construction.constructionId}" name="quantity_share${cId}" value="${((construction.share ?:0) * 100).toInteger()}" onchange="" onblur="onChangeConstructionArea('${cId}', $(this).val(), '${group.oid}')"/>
                                                        </td>
                                                        <g:if test="${lccIndicator}"><td class="col_number-large"><input class="numeric constshareinput costgroup${group.oid}" data-costgroup="${group.oid}" data-calculatedCost="${construction.calculatedCost}" type="text" id="lcc_share${construction.constructionId}" name="lcc_share${cId}" value="${construction.userGivenCost ? construction.userGivenCost : ""}" onblur="onChangeConstructionCost('${cId}', this, '${group.oid}')"/> ${costUnit} / ${constructionUnitBySystem}</td></g:if>
                                                        <td id="co2${cId}" class="col_number">${roundToString(construction.co2e) + " tn"}</td>
                                                        <td id="co2Share${cId}" class="col_number">${roundToString(co2Share) + "%"}</td>
                                                        <td id="co2Int${cId}" class="col_intensity">${roundToString(construction.co2eIntensity / conversionFactor) + " kg"}</td>
                                                        <td class="comment${construction.dynamicDefault ? " hidden" : ""}">
                                                            <input type="text" id="comment${cId}" name="comment${cId}" value="${construction.comment ?: ""}" onblur="loadConstructionComment('${cId}')" style="width: 150px;"/>
                                                        </td>
                                                        <g:if test="${dynamicDefaultGroup}">
                                                            <td class="col_edit"></td>
                                                        </g:if>
                                                        <g:else>
                                                            <g:set var="defaultSize" value="${construction.constituents?.findAll{ it.defaultConstituent && !it.modThickness}?.size() ?:0 }"/>
                                                            <g:if test="${defaultSize == construction?.constituents?.size()}">
                                                                <td class="col_edit"></td>
                                                            </g:if>
                                                            <g:else>
                                                                <td class="col_edit">
                                                                    <a id="edit_link${cId}" onclick="showConstituentsModal('${cId}')" style="text-decoration: underline;" >
                                                                        <g:if test="${construction.share}">
                                                                            <g:message code="edit"/>
                                                                        </g:if>
                                                                        <g:else>
                                                                            <g:message code="view"/>
                                                                        </g:else>
                                                                    </a>
                                                                </td>
                                                            </g:else>
                                                        </g:else>
                                                    </tr>
                                                </g:each>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </g:each>
                        </g:if>
                    </div>

                    <%-- TABLES ENERGY SECTION --%>
                    <div class="container">
                    <%-- <label><b>Carbon optimisation dashboard</b></label> --%>
                        <g:if test="${energyTypeList && energyTypeList.size() > 0}">
                        <%-- Heading  ENERGY SECTION--%>
                            <div class="container">

                                <br>
                            </div>
                            <%-- Caption for all the tables --%>
                            <table id = "tableEnergy" class="table" >
                                <thead onclick="showOrHide(this)">
                                <tr class="second_row">
                                    <th class="first_col"><i class="fa fa-plus"></i>&nbsp;&nbsp;&nbsp; <g:message code="simulationTool.secondPage.energySection"/></th>
                                    <th class="col_number"><g:message code="demand"/> ${conversionUnit(unitSystem, "kWh")}/m<sup>2</sup></th>
                                    <g:if test="${lccIndicator}"><th class="col_number-large">${localizedCostText}<br/><span data-group="energy" id="headingTotalCostenergy">${String.valueOf(formatNumber(number:energyTypeList.collect({it.calculatedCost}).sum(), format:"#,###,###", maxFractionDigits: 0, roundingMode: "HALF_UP")).replaceAll(","," ")}</span> ${costUnit}</th></g:if>
                                    <th class="col_number"><g:message code="conversionEfficiency"/></th>
                                    <th class="col_number-small"><g:message code="demand"/> ${conversionUnit(unitSystem, "kWh")}</th>
                                    <th class="col_number-small"><g:message code="purchased"/> ${conversionUnit(unitSystem, "kWh")}</th>
                                    <th class="col_number-small"><g:message code="tons"/> CO<sub>2</sub>e</th>
                                    <th class="col_number-small"><g:message code="carbonShare"/></th>
                                    <th class="col_edit" style="min-width: 280px !important;"><g:message code="energySupply"/></th>
                                </tr>
                                <tr><td colspan="8" style="margin: 3px 0px 3px 0px;"><g:message code="simulationTool.secondPage.energySectionHeading"/> <b>${roundToString(heatedArea)} m<sup>2</sup></b>.</td> </tr>
                                </thead>

                                <tbody class="hidden">
                                <g:each var="energyType" in="${energyTypeList}">
                                    <tr id="${energyType?.typeId}" class="second_row">
                                        <td><g:message code="simulationTool.${energyType?.typeId}" />
                                            <g:if test="${(energyType?.typeId == PRIMARY_HEAT || energyType?.typeId == SECONDARY_HEAT)}">
                                                <a href="#" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${g.message(code: "${energyType?.typeId}.QMark", dynamic: true)}"><i class="icon-question-sign"></i></a>
                                            </g:if>
                                        </td>
                                        <td class="col_number"><input class="numeric" type="text" id="demand_m2${energyType?.typeId}" value="${simulationToolService.roundToNumber((energyType?.demand_m2?:0)* conversionUnitFactor(unitSystem, "kWh"))}" onblur="updateEnergyType('${energyType?.typeId}')" /></td>
                                        <g:if test="${lccIndicator}"><g:set var="energyTypeTotalCost" value="${energyType.calculatedCost ?: 0}" /><td class="col_number-large"><span class="hidden headingTotalCost" data-roundedtotaloriginal="${roundToString(energyTypeTotalCost)}" data-roundedtotal="${roundToString(energyTypeTotalCost)}" id="headingTotalCost${energyType?.typeId}">${energyTypeTotalCost}</span><input class="numeric constshareinput costgroupenergy" type="text" data-calculatedCost="${energyType?.calculatedCost}" id="lcc_share${energyType?.typeId}" name="lcc_share${energyType?.typeId}" value="${energyType?.userGivenCost}" onblur="onChangeEnergyCost('${energyType?.typeId}', $(this).val())"/> ${costUnit} / ${conversionUnit(unitSystem, "kWh")}</td></g:if>
                                        <td class="col_number"><input class="numeric" type="text" id="effFactor${energyType?.typeId}" value="${simulationToolService.roundToNumber(energyType?.efficiencyFactor)}" onblur="updateEnergyType('${energyType?.typeId}')" /></td>
                                        <td class="col_number-small" id="totalDemand${energyType?.typeId}">${roundToString(energyType?.demand * conversionUnitFactor(unitSystem, "kWh"))}</td>
                                        <td class="col_number-small purchase" id="purchase${energyType?.typeId}">${roundToString(energyType?.purchase * conversionUnitFactor(unitSystem, "kWh"))}</td>
                                        <td class="col_number-small" id="co2e${energyType?.typeId}">${(energyType?.co2e)? roundToString(energyType?.co2e) : "-"}</td>
                                        <g:set var="co2Share" value="${((energyType?.co2e?:0)/(intitialImpactDiv)*100)?.doubleValue()}"/>
                                        <td class="col_number-small" id="co2eShare${energyType?.typeId}">${roundToString(co2Share) + "%"}</td>
                                        <td style="min-width: 280px !important; text-align: center"><%--
                                            <g:select name="defaultOption${energyType?.typeId}" from="${energyType?.options}" value="${energyType?.defaultOption}" optionKey="${{it?.key}}" optionValue="${{it?.value}}" noSelection="['' : '']" onchange="updateEnergyType('${energyType?.typeId}')" required="true"/>
                                            --%>
                                            <div class="input-append">
                                                <input class="energyautocomplete" id="autocomplete${energyType?.typeId}" type="text" defaultOption="${energyType?.defaultOption}"/>
                                                <a tabindex="-1" class="add-on showAllResources " onclick="showAllOptions('${energyType?.typeId}')" >
                                                    <i class="icon-chevron-down"></i>
                                                </a>
                                                <opt:renderDataCardBtn indicatorId="${indicatorId}" resourceId="${energyType?.defaultOption}" childEntityId="${entityId}" showGWP="true" infoId="${energyType?.typeId}" questionId="questionId" profileId="profileId" queryId="queryId" sectionId="sectionId" />
                                        </div>

                                        </td>

                                    </tr>
                                </g:each>

                                </tbody>

                            </table>

                            <br>
                            <g:message code="totalDemand"/>: <b>${roundToString((energyTypeList?.sum({it.demand?.toInteger() ?: 0}) * conversionUnitFactor(unitSystem, "kWh")))} ${conversionUnit(unitSystem, "kWh")} </b>
                            <br><br><br>

                        </g:if>
                    </div>

                    <%-- DIV BUTTONS UNDER TABLE --%>
                    <div style="width: 100%;" >

                        <%-- Leave Button --%>
                        <opt:link controller="entity" action="show" params="[entityId:entityId]" class="btn" ><g:message code="cancel"/></opt:link>
                        <%-- Manage Draft Div Button --%><%--
                        <div class="btn-group">
                            <a href="#" data-toggle="dropdownDraft" class="dropdown-toggle btn hide-on-print">
                                <g:message code="simulationTool.button.draft"/>
                                <i class="far fa-edit" aria-hidden="true"></i>
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li><a onclick="saveDraft()"><g:message code="simulationTool.button.save"/> <i class="fa fa-save"></i></a></li>
                                <li><opt:link controller="util" action="earlyPhaseToolDeleteDraft" params="[entityId:entityId, sessionId: sessionId]">
                                    <g:message code="simulationTool.button.delete"/> <i class="far fa-trash-alt"></i>
                                </opt:link></li>
                            </ul>
                        </div>
                        --%>
                        <opt:link class="btn" controller="util" action="earlyPhaseToolDeleteDraft" params="[entityId:entityId, sessionId: sessionId]" style="margin: 0px 5px 0px 5px;">
                            <g:message code="simulationTool.button.delete"/> <i class="far fa-trash-alt"></i>
                        </opt:link>

                        <div class="btn-group" style="display: inline-block">
                            <a id="saveDraftButton" href="javascript:" class="btn btn-primary" onclick="saveDraft_async()">
                                <g:message code="simulationTool.button.setAsBaseline"/>
                            </a>
                        </div>

                        <%-- Save data to query Div Button --%>
                        <div class="btn-group" style="display: inline-block">
                            <a id="saveQueryButton" href="javascript:" class="btn btn-primary" onclick="validateSharesAndSubmit($(this));">
                                <g:message code="simulationTool.button.saveQuery"/>  <i id='quantity_share_spinner_save' class='fas fa-circle-notch fa-spin white-font hidden'></i>
                            </a>
                        </div>


                    </div>
                    <%-- Hidden Values for the form --%>
                    <input type="hidden" id="simModel" value="" name="simModel">
                    <input type="hidden" id="energyTypeListModel" value="" name="energyTypeListModel">
                    <input type="hidden" id="mergeSimulation" value="" name="mergeSimulation">

                </g:form>
            </div>

            <%-- DIV MODAL --%>
            <div class="modal hide bigModal" id="modal">
                <div class="modal-header text-center"><button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h2 id="header"></h2>
                </div>
                <div class="modal-body" id="modalBody"></div>
            </div>

        </div>

    <%-- DIV MODAL Region Info--%>
    <div class="modal bigModal hide" id="modalRegionInfo">
        <div class="modal-header"><button type="button" class="close" data-dismiss="modal">&times;</button>
        <div class="modal-body" id="modalBodyRegionInfo">
            <div class="column_left">
                <table>
                <tr>
                    <td class="assumption"><b><g:message code="simulationTool.tool"/></b>: </td>
                    <td class="assumption">${indicatorName?:""}</td>
                </tr>
                <tr>
                    <td class="assumption"><b><g:message code="simulationTool.calculationPeriod"/></b>: </td>
                    <td class="assumption">${calculationPeriod ? calculationPeriod + " " + g.message(code : "simulationTool.years") : "-"}</td>
                </tr>
                <tr>
                    <td class="assumption"><b><g:message code="simulationTool.regionReferenceName"/></b>: </td>
                    <td class="assumption">${regionReferenceName}</td>
                </tr>
                <tr>
                    <td class="assumption"><b><g:message code="simulationTool.buildingType"/></b>: </td>
                    <td class="assumption">${buildingName}</td>
                </tr>
                <tr>
                    <td class="assumption"><b><g:message code="simulationTool.surface" /></b>: </td>
                    <td class="assumption">${roundToString((grossFloorArea?.toInteger()?:0) * conversionUnitFactor(unitSystem, "m2")) } ${conversionUnit(unitSystem, "m2")}</td>
                </tr>
                <tr>
                    <td class="assumption"><b><g:message code="simulationTool.aboveGroundFloors" /></b>: </td>
                    <td class="assumption">${simulationSession?.aboveGroundFloors}</td>
                </tr>
                <tr>
                    <td class="assumption"><b><g:message code="simulationTool.undergroundHeatedFloors" /></b>: </td>
                    <td class="assumption">${simulationSession?.undergroundHeatedFloors}</td>
                </tr>
                <tr>
                    <td class="assumption"><b><g:message code="simulationTool.undergroundUnheatedFloors" /></b>: </td>
                    <td class="assumption">${simulationSession?.undergroundUnheatedFloors}</td>
                </tr>
                <tr>
                    <td class="assumption"><b><g:message code="simulationTool.baselineScenarioDashboard" /></b>: </td>
                    <td class="assumption">${defaultChoicesSets?.find({it.defaultChoiceId.equals(baselineScenario)})?.localizedName}</td>
                </tr>
                <tr>
                    <td class="assumption"><b><g:message code="simulationTool.comparisonScenarioDashboard" /></b>: </td>
                    <td class="assumption">${defaultChoicesSets?.find({it.defaultChoiceId.equals(alternativeScenario)})?.localizedName}</td>
                </tr>
                <g:if test="${simulationSession?.isEarthquakeZone}">
                    <tr>
                        <td colspan="2" class="assumption"><b><g:message code="simulationTool.isEarthquakeZone" /></b></td>
                    </tr>
                </g:if>
                    <tr><td colspan="2" > ${regionInfoText}</td></tr>

            </table>

            </div>
            <div class="column_right bordered">
                <table>
                    <tr>
                        <td class="assumption"><b><g:message code="simulationTool.height"/></b>:</td>
                        <td class="assumption">${roundToString((simulationSession?.heightBuilding?:0) * conversionUnitFactor(unitSystem, "m"))}</td>
                        <td> ${conversionUnit(unitSystem, "m")}</td>
                    </tr>
                    <tr>
                        <td class="assumption"><b><g:message code="simulationTool.width"/></b>:</td>
                        <td class="assumption">${roundToString((simulationSession?.widthBuinding?:0) * conversionUnitFactor(unitSystem, "m"))}</td>
                        <td> ${conversionUnit(unitSystem, "m")}</td>
                    </tr>
                    <tr>
                        <td class="assumption"><b><g:message code="simulationTool.depth" /></b>:</td>
                        <td class="assumption">${roundToString((simulationSession?.depthBuilding?:0) * conversionUnitFactor(unitSystem, "m"))}</td>
                        <td> ${conversionUnit(unitSystem, "m")}</td>

                    </tr>
                    <tr>
                        <td class="assumption"><b><g:message code="simulationTool.internalFloorHeight" /></b>:</td>
                        <td class="assumption">${roundToString((simulationSession?.internalFloorHeight?:0) * conversionUnitFactor(unitSystem, "m"))}</td>
                        <td> ${conversionUnit(unitSystem, "m")}</td>
                    </tr>
                    <tr>
                        <td><b><g:message code="simulationTool.maxSpanWithoutColumns_m" /></b>: </td>
                        <td class="assumption">${roundToString((simulationSession?.maxSpanWithoutColumns_m?:0) * conversionUnitFactor(unitSystem, "m"))}</td>
                        <td> ${conversionUnit(unitSystem, "m")}</td>
                    </tr>
                    <tr>
                        <td class="assumption"><b><g:message code="simulationTool.loadBearingShare" /></b>: </td>
                        <td class="assumption">${roundToString((simulationSession?.loadBearingShare ? simulationSession?.loadBearingShare : 0)* 100)}</td>
                        <td> %</td>

                    </tr>
                    <tr>
                        <td class="assumption"><b><g:message code="simulationTool.numberOfStaircases" /></b>:</td>
                        <td class="assumption">${simulationSession?.numberStaircases}</td>
                    </tr>
                    <tr>
                        <td class="assumption"><b><g:message code="simulationTool.totalFloors" /></b>:</td>
                        <td class="assumption">${totalFloors ?: 0}</td>
                    </tr>
                    <tr>
                        <td class="assumption"><b><g:message code="simulationTool.shapeEfficiencyFactor" /></b>:</td>
                        <td class="assumption">${roundToString(simulationSession?.shapeEfficiencyFactor)}</td>
                    </tr>
                    <tr>
                        <td class="assumption"><b><g:message code="simulationTool.totalArea" /></b>:</td>
                        <td class="assumption">${roundToString((simulationSession?.totalNetArea?:0) * conversionUnitFactor(unitSystem, "m2"))}</td>
                        <td> ${conversionUnit(unitSystem, "m2")}</td>
                    </tr>
                    <g:if test="${energyTypeList}">
                        <tr>
                            <td class="assumption"><b><g:message code="simulationTool.heatedArea" /></b>: </td>
                            <td class="assumption">${roundToString((simulationSession?.heatedArea?:0) * conversionUnitFactor(unitSystem, "m2"))}</td>
                            <td> ${conversionUnit(unitSystem, "m2")}</td>

                        </tr>
                    </g:if>
                </table>

                <%--
                <label class="assumption"><b></b>: ${""}</label>
                --%>
            </div>
        </div>
        <div style="text-align: right">
            <button class="btn btn-primary" data-dismiss="modal"><g:message code="ok"/></button>
        </div>
    </div>

    </div>

        <div id="spinner" class="loading-spinner hidden"><div class="image">
            <div><h1 id="title_spinner"><g:message code="simulationTool.spinner.saveSimulation"/></h1></div>
            <svg class="loadingSVG" version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg"  x="0px" y="0px"
                 width="270px" height="270px" viewBox="0 0 32 32" style="enable-background:new 0 0 32 32;" xml:space="preserve">
                <g><path class="arrowSpinningSvg mm-o-syncing-queue" d="M24.5,10.4L22.2,11l5,5L29,9.2l-2.4,0.7C24.4,6.1,20.4,3.8,16,3.8c-5.4,0-10.1,3.4-11.7,8.6
                        c-0.2,0.5,0.1,1.1,0.7,1.3c0.1,0,0.2,0,0.3,0c0.4,0,0.8-0.3,1-0.7c1.3-4.3,5.2-7.2,9.7-7.2C19.5,5.8,22.7,7.5,24.5,10.4z"/>
                </g>
            </svg>
            <p class="working"><g:message code="loading.working"/>.</p>
        </div>
    </div>

<script>

    //******************************************************************
    $(document).ready(function() {
        console.log("DOMready: ", Date.now()-timerStart);

        var helpPopSettings = {
            placement: 'right',
            template: '<div class="popover popover-fade" style="display: block; max-width: 300px;"><div class="arrow"></div><div style="font-weight: normal !important;" class="popover-content"></div></div>'
        };
        $(".lccErrorPopover[rel=popover]").popover(helpPopSettings)
    });

    $(window).on('load', function() {
        console.log("Time loading: ", Date.now()-timerStart);
    });
    //******************************************************************


    var sessionId = "${sessionId}"
    validateSession();

    var oldModel = ${modelGroups as grails.converters.JSON};
    var model = ${modelGroups as grails.converters.JSON};
    var energyTypeList = ${energyTypeList ? energyTypeList as grails.converters.JSON : []};
    <g:if test="${!isAlternativeScenarioEnergy}">
        var oldEnergyTypeList = ${energyTypeList ? energyTypeList as grails.converters.JSON : []};
    </g:if>
    <g:else>
        var oldEnergyTypeList = ${energyTypeList ? energyTypeList.collect {it.co2e = 0; it.heatedArea = 0} as grails.converters.JSON : []};
    </g:else>

//    $("#initialImpact").text(roundingJS(initialCo2eChart) + " tons")

    <%-- Reset all forms --%>
    $(document).ready(() => {
        resetForms();
        //updateEnergyImpact();
        $('#groupingDropdown').dropdown();
    });

    //Validate the Session
    function validateSession(){

        let json = {sessionId: "${sessionId}", entityId :"${entityId}" };

        let url = '/app/sec/util/validateSession';

        let errorCallback = () => {}

        let successCallback = function (data) {

            if(!data || !data.isValid){
                let label = "${g.message(code : "simulationTool.loadingSimulation")}"
                showSpinner(label);
                $("[name=goBack]")[0].click()
            }
        };

        ajaxForJson (url, json, successCallback, errorCallback)

    }

    function validateSharesAndSubmit(element) {
        var percentageShareWarning = "${message(code: "simulationTool.shareWarning")}";
        var percentageShareWarningsFound = false;
        $.each($('.tbody_constructions').not('.dynamicDefaultGroup'), function() {
            var sum = getPercentageShareSumFromBody($(this));

            if (sum !== 100) {
                if (!percentageShareWarningsFound) {
                    percentageShareWarning = percentageShareWarning + " " + $(this).attr("data-groupName");
                    percentageShareWarningsFound = true;
                } else {
                    percentageShareWarning = percentageShareWarning + ", " + $(this).attr("data-groupName");
                }
            }
        });
        $("#quantity_share_spinner_save").removeClass("hidden")

        if (percentageShareWarningsFound) {
            Swal.fire({
                title: "${g.message(code: "simulationTool.swalTitleSaveQuery")}",
                text: percentageShareWarning + ". ${message(code: "simulationTool.continue")}",
                icon: 'warning',
                showCancelButton: true,
                showCloseButton: true,
                reverseButtons: true,
                allowOutsideClick: true, //???
                allowEscapeKey: true,    //???
                confirmButtonColor: '#d62317',
                cancelButtonColor: '#bbbdb7',
                cancelButtonText: '${g.message(code: "yes")}',
                confirmButtonText: '${g.message(code: "no")}',
                onClose: hideSpinnerSmall

            }).then(result => {

                if (result.dismiss === swal.DismissReason.cancel) {
                    // User pressed YES they want to continue
                    <g:if test="${overwriteDirectly}">
                        saveDraftAndOverwrite();
                    </g:if>
                    <g:else>
                        submitFormWithSelectedDesign();
                    </g:else>
                }

            });

        } else {
            <g:if test="${overwriteDirectly}">
                saveDraftAndOverwrite();
            </g:if>
            <g:else>
                submitFormWithSelectedDesign();
            </g:else>
        }

    }
    function hideSpinnerSmall(){

        $("#quantity_share_spinner_save").addClass("hidden")
    }
    <%-- The method reset all the forms in the page --%>
    function resetForms() {
        Array.from(document.forms).forEach(el => el.reset());
    }

    function updateConstructionAreaJson(constructionId, percentage){

        var initialConstruction = findConstruction(oldModel, constructionId);

        var construction = findConstruction(model, constructionId);

        if(percentage > 150) {percentage = 150}
        if(percentage < 0){percentage = 0}

        $("#quantity_share" + constructionId).val(percentage);

        let share = new Number(percentage) / 100

        let actualShare = construction.share
        if(share === actualShare) return null

        let group = findGroupByConstruction(model, constructionId)

        let initialAmountGroup = group.initialAmount

        construction.amount = share * initialAmountGroup;
        construction.share = share

        let initialShare = initialConstruction.share
        let lengthDataset = myChart.data.datasets.length

        loadComment(construction, false)

        let json = {sessionId : "${sessionId}", jsonConstruction : construction}
        return json
        /*
        if(initialShare != share || (initialShare == share && lengthDataset == 2 )){
            let json = {sessionId : "${sessionId}", jsonConstruction : construction}
            return json
        } else {
            return null
        }
        */

    }

    function updateConstructionCostJson(constructionId, cost, areaChanged){
        var construction = findConstruction(model, constructionId);
        $("#lcc_share" + constructionId).val(cost);

        var costInEuropean = cost * conversionUnitFactorJS('${unitSystem}', construction.unit);

        if(!areaChanged && construction && construction.userGivenCost == costInEuropean) return null;

        return {sessionId : "${sessionId}", jsonConstruction : construction, userGivenCost: costInEuropean}
    }

    //To show or hide the edit link for a constuction
    function enableConstructionEdit(constructionId, percentage) {


        if(percentage == 0) {
            $("#edit_link" + constructionId).text("${g.message(code: "view")}")
        } else {
            $("#edit_link" + constructionId).text("${g.message(code: "edit")}")
        }
    }

    function onChangeConstructionArea(constructionId, percentage, group, async = true) {

        let json = updateConstructionAreaJson(constructionId, percentage);

        if(json){
            onUpdateConstruction(constructionId);
            calculateConstructionImpact(json, group, async)
        }
    }

    function onChangeConstructionCost(constructionId, element, costGroup, skipUiUpdates, asyncCall, areaChanged) {

        var json = updateConstructionCostJson(constructionId, $(element).val(), areaChanged);
        if(!json) return null;

        if (!skipUiUpdates) {
            disableInputs();
        }

        var costError = $(element).closest('tr').find('.lccErrorPopover');

        if (costError.length) {
            costError.remove();
        }


        var asyncType = true;

        if (asyncCall === false || asyncCall === true) {
            asyncType = asyncCall;
        }
        $.ajax({
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(json),
            contentType: "application/json; charset=utf-8",
            url: '/app/sec/simulationTool/updateConstructionCost',
            async: asyncType,
            success: function (data, textStatus) {
                if (!skipUiUpdates) {
                    enableInputs();
                }
                var newConstruction = data.simConstruction;
                var calculatedCost = newConstruction.calculatedCost;

                var temp = findConstruction(model, constructionId);
                var costInEuropean = parseFloat(newConstruction.userGivenCost);
                var convertedCost = costInEuropean / conversionUnitFactorJS('${unitSystem}', newConstruction.unit);

                temp.userGivenCost = convertedCost;
                temp.calculatedCost = calculatedCost;
                $(element).attr("data-calculatedCost", calculatedCost);
                if (!skipUiUpdates) {
                    updateCostHeading(costGroup);
                    updateCostChart();
                }
            }
        });
    }

    function updateCostHeading(costGroup, energy) {
        var costGroupInputs = null;

        if (energy) {
            costGroupInputs = $(".costgroupenergy");
        } else {
            costGroupInputs = $(".costgroup" + costGroup);
        }

        var headingSpan = $("#headingTotalCost" + costGroup);

        if (costGroupInputs.length && headingSpan.length) {
            var total = 0;

            $(costGroupInputs).each(function () {
                var row = $(this).closest('tr');
                var totalCalculated = parseFloat($(this).attr("data-calculatedCost"));

                if (totalCalculated) {
                    if (energy && $(row).attr("id") === costGroup) {
                        var roundedAmount = roundingJS(totalCalculated);
                        headingSpan.attr("data-roundedTotal", roundedAmount);
                    }
                    total += totalCalculated;
                }
            });
            var roundedTotal = roundingJS(total);

            if (energy) {
                $("#headingTotalCostenergy").text(roundedTotal.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " "));
            } else {
                headingSpan.attr("data-roundedTotal", roundedTotal);
                headingSpan.text(roundedTotal.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " "));
            }
        }
    }

    function calculateCostOptimization(costData){
        let newImpact = costData.reduce((sum, c) => sum + c, 0);

        let grossFloorArea = new Number(${grossFloorArea});
        grossFloorArea = grossFloorArea ? grossFloorArea : 1
        
        let newIntesityImpact = newImpact / grossFloorArea;
        let projectLevel = (newImpact - initialCostTotal);

        //Percetange of difference between newImpact and initial  Impact in Tons
        var diffImpact = projectLevel/initialCostTotal * 100;

        if(projectLevel < 0){
            $("#diffCost").css('color', 'green');
            $("#projectLevelCost").css('color', 'green');

        }else if(projectLevel > 0){
            $("#diffCost").css('color', 'red');
            $("#projectLevelCost").css('color', 'red');

        }else if (projectLevel == 0){
            $("#diffCost").css('color', 'black');
            $("#projectLevelCost").css('color', 'black');

        }

        newIntesityImpact = roundingJS(newIntesityImpact);
        diffImpact = roundingJS(diffImpact);
        projectLevel = roundingJS(projectLevel);

        //Update View Values
        $("#newCost").text(newIntesityImpact);
        $("#diffCost").text((projectLevel>0 ? "+" : "") + diffImpact + "%");
        $("#projectLevelCost").html((projectLevel>0 ? "+" : "") + projectLevel);

    }

    function updateCostChart() {
        var data = [];
        $(".headingTotalCost").each(function () {
            var amount = parseFloat($(this).attr("data-roundedTotal"));
            data.push(amount)
        });
        calculateCostOptimization(data);

        const dataNew = getCostChartData(statusChart);

        let first = dataNew.datasets[0];
        let second = dataNew.datasets[1];

        costChart.data.datasets[0].data = first;
        costChart.data.labels = dataNew.labels;

        if(equalsArray(first, second)){
            if(costChart.data.datasets.length == 2){
               costChart.data.datasets.splice(1,1)
            }
        } else {
            costChart.data.datasets[1] = addCostDataset(first, second);
        }
        costChart.update();
    }

    function addCostDataset(initialData, newData) {
        var red = 'rgba(255,145,27,0.4)';
        var redBorder = 'rgba(255,145,27, 1)';
        var green = 'rgba(0,174,0,0.2)';
        var greenBorder = 'rgba(0,174,0, 1)';
        var grey = 'rgba(192,192,192,0.3)';
        var greyBorder = 'rgba(192,192,192,1)';

        var background = new Array();
        var border = new Array();

        for (var i in newData) {
            let val = new Number(newData[i] - initialData[i]);

            if(val < 0) {
                background.push(green);
                border.push(greenBorder);
            }
            if(val > 0) {
                background.push(red);
                border.push(redBorder);
            }
            if(val == 0){
                background.push(grey);
                border.push(greyBorder);
            }
        }

        var newDataset = {
            label: '${g.message(code: "optimized")} ${localizedCostText}',
            backgroundColor: background,
            borderColor: border,
            borderWidth: 1,
            data: newData
        };
        return newDataset
    }

    function calculateConstructionImpact(json, group, async = true){

        let url = '/app/sec/util/updateConstructionImpact';

        disableInputs();

        let successCallback = function (data) {
            var newConstruction = data.simConstruction;
            updateConstructionView(newConstruction);
            enableInputs();

            <g:if test="${lccIndicator}">
            onChangeConstructionCost(newConstruction.constructionId, $('#lcc_share' + newConstruction.constructionId), group, false, false, true);
            </g:if>
        };

        ajaxForJson (url, json, successCallback, errorServerReply, async)
    }

    //If bool is true merge, if false save only
    function setMergeOrSaveAndSubmit(bool) {

        //Hidden inputs
        $('#mergeSimulation').val(bool);

        var dummyModel = [];

        model.forEach(group => {
            var newGroup = $.extend(true, {}, group); // Deep clone
            newGroup.constructions = $.grep(group.constructions, function(construction) {
                return construction.share > 0; // Save model with constructions that have a share
            });
            dummyModel.push(newGroup)
        });

        $('#simModel').val(JSON.stringify(dummyModel));
        $("#energyTypeListModel").val(JSON.stringify(energyTypeList));

        let label = "${g.message(code:"simulationTool.spinner.saveSimulation")}";
        showSpinner(label);

        setTimeout(function () {
            $("#nextDashboard").trigger('submit');
        }, 1000)
    }

    function saveDraftAndOverwrite() {

        disableElementFor("#saveQueryButton", 2000);

        let errorCallback = () => { };
        let successCallBack = function (data) {
            if (data && data.output) {
                console.log("SAVE DRAFT RESULT -->" + data.output)
                setMergeOrSaveAndSubmit(${java.lang.Boolean.FALSE})

            } else {
                alert("SAVE DRAFT ERROR")
                console.log("SAVE DRAFT ERROR")
                console.log(data)
            }

        };

        saveDraftByAjax(successCallBack, errorCallback);
    }

    function submitFormWithSelectedDesign() {

        disableElementFor("#saveQueryButton", 2000);

        let errorCallback = ()=>{};
        let successCallBack = function(data) {
            if(data && data.output){
                console.log("SAVE DRAFT RESULT -->" + data.output)
                //**********************************************************************************************
                //The Swall doesnt allow the use of other button so now the merge button is the cancel button
                //**********************************************************************************************

                let save = function(){
                    setMergeOrSaveAndSubmit(${java.lang.Boolean.FALSE})
                }

                let merge = function() {
                    setMergeOrSaveAndSubmit(${java.lang.Boolean.TRUE})
                }

                //**********************************************************

                Swal.fire({
                    title: "${g.message(code: "simulationTool.swalTitleSaveQuery")}",
                    text: "${warningMergeMessage}",
                    icon: 'warning',
                    showCancelButton: ${allowMerge},
                    showCloseButton: true,
                    reverseButtons: true,
                    allowOutsideClick: true, //???
                    allowEscapeKey: true,    //???
                    confirmButtonColor: '#d62317',
                    cancelButtonColor: '#bbbdb7',
                    cancelButtonText: '${g.message(code: "merge")}',
                    confirmButtonText: "${g.message(code: "overwrite")}",
                    onClose: hideSpinnerSmall
                }).then(result => {
                    if (result.value) {
                        save()
                    } else if (result.dismiss === swal.DismissReason.cancel) {
                        merge()
                    }
                });

            } else {
                alert("SAVE DRAFT ERROR")
                console.log("SAVA DRAFT ERROR")
                console.log(data)
            }
        };

        saveDraftByAjax(successCallBack, errorCallback);
    }

    function validate() {

        let test = energyTypeList.filter(it => (!it.defaultOption || it.defaultOption === ""))

        if (test.length === 0) {
            showSpinner();
            return true;
        } else {
            //test.forEach( it => $("#defaultOption" + it.typeId).addClass("redBorder") );
            return false;
        }

    }


    function showConstituentsModal(constructionId) {

        var construction = findConstruction(model, constructionId);

        let json = {sessionId : "${sessionId}", construction : construction}
        
        if ($('#popover' + constructionId).is(':visible')) {
            closeSourceListing(constructionId);
            stopBubblePropagation(event);
        } else {
            $.ajax({
                type: 'POST',
                dataType: 'json',
                data: JSON.stringify(json),
                contentType: "application/json; charset=utf-8",
                url: '/app/sec/util/earlyPhaseToolDatasets',
                success: function (data) {
                    $("#modalBody").empty().append(data.output.template);
                    $("#modal").modal();
                },
                error: errorServerReply
            });
        }
    }
    //**************************************

    //**************************************
    <%-- CARBON CHART --%>

    var labels = ${modelGroups.collect{"\"${it.name}\""} + energyTypeList?.collect{"\"${g.message(code: "simulationTool." + it.typeId)}\""} ?:[]};
    var dataImpact = ${modelGroups.collect{it.co2e} + energyTypeList?.collect{!isAlternativeScenarioEnergy ? (it.co2e ?:0) : 0 } ?:[]};
    var newDataset;
    <g:set var="energyLabel" value="${g.message(code: "simulationTool.chart.energy")}" />
    <g:set var="totalLabel" value="${g.message(code: "simulationTool.chart.total")}" />

    var chartLabels = {
        "${byConstructionGroup}" :  ${modelGroups.collect{"\"${it.name}\""} + energyTypeList?.collect{"\"${g.message(code: "simulationTool." + it.typeId)}\""} ?:[]},
        "${byBuildingElement}": ${constructionGroupList.collect{"\"$it.buildingElementName\""}.unique() + (energyTypeList ? [""" "$energyLabel" """] : [] )},
        "${byTotal}" : ["${totalLabel}"]
    }
    var statusChart = "${defaultGrouping ?: byConstructionGroup}";

    var constructionGroupList = ${constructionGroupList as grails.converters.JSON };
    var buildingElements = ${constructionGroupList.collect{"\"$it.buildingElement\""}?.unique()};
    //Label chart - every word on a line to show all the words
    labels = labels.map(it => it.split(" "))
    labels = labels.map(it => {  if(it.length == 3){
                            it[0] = it[0] + " " + it[1];
                            it[1] = it[2];
                            it.splice(2,1);
                        }
                        return it

    });

    var myChart = initializeChart();

    <g:if test="${lccIndicator}">
    var costChart = initializeCostChart(); // costChart
    </g:if>
    reGroupChart(statusChart)
    function initializeChart(){

        let ctx = document.getElementById("myChart").getContext('2d');
        let myChart = new Chart(ctx, {
            type: 'bar',
            data: {
                //labels: model.map(el => el.name),
                labels: labels,
                datasets: [{
                    label: '${g.message(code: "baseline")} CO2e',
                    //data: model.map(el => el.constructions.reduce((sum, el) => sum + el.amount, 0)),
                    data: dataImpact,
                    backgroundColor: 'rgba(100, 145, 200, 0.2)',
                    borderColor: 'rgba(100, 100, 200, 1)',
                    borderWidth: 1
                }                ]
            },
            options: {
                scales: {
                    xAxes: [{
                        ticks: {
                            autoSkip: false,
                        }
                    }],
                    yAxes: [{
                        scaleLabel: {
                            display: true,
                            labelString: '${g.message(code : "chart.tons")}'
                        },
                        ticks: {
                            beginAtZero:true
                        }
                    }]
                },
                legend: {
                    labels: {fontSize: 12,
                        fontColor: 'black',
                        fillStyle: 'green'}

                },
                tooltips: {
                    callbacks: {
                        label: function(tooltipItem, data) {
                            var label = data.datasets[tooltipItem.datasetIndex].label || '';

                            if (label) {
                                label += ': ';
                            }
                            let impact = new Number(tooltipItem.yLabel)
                            label +=  roundingJS(impact)+  " ${g.message(code: "chart.tons")}";

                            return label;
                        }
                    },
                }
            }
        });

        return myChart
    }

    function initializeCostChart(){
        var data = [];
        $(".headingTotalCost").each(function () {
            var amount = parseFloat($(this).attr("data-roundedtotal"));
            data.push(amount)
        });

        let ctx = document.getElementById("costChart").getContext('2d');
        let myChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: '${localizedCostText}',
                    data: data,
                    backgroundColor: 'rgba(141, 199, 63, 0.2)',
                    borderColor: 'rgba(141, 199, 63, 1)',
                    borderWidth: 1
                }                ]
            },
            options: {
                scales: {
                    xAxes: [{

                    }],
                    yAxes: [{
                        scaleLabel: {
                            display: true,
                            labelString: '${costUnit}'
                        },
                        ticks: {
                            beginAtZero: true,
                            callback: function(label, index, labels) {
                                if (Math.floor(label) === label) {
                                    return label.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ");
                                }
                            }
                        }
                    }]
                },
                legend: {
                    labels: {fontSize: 12,
                        fontColor: 'black',
                        fillStyle: 'green'}

                },
                tooltips: {
                    callbacks: {
                        label: function(tooltipItem, data) {
                            var label = data.datasets[tooltipItem.datasetIndex].label || '';

                            if (label) {
                                label += ': ';
                            }
                            let impact = new Number(tooltipItem.yLabel);
                            var roundedImpact = roundingJS(impact);
                            label += roundedImpact.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ") +  " ${costUnit}";
                            return label;
                        }
                    },
                }
            }
        });
        return myChart
    }

    function addDataset(initialData, newData) {

        var red = 'rgba(255, 10, 10, 0.2)';
        var redBorder = 'rgba(255, 10, 10, 1)';
        var green = 'rgba(99, 255, 132, 0.2)';
        var greenBorder = 'rgba(99, 255, 132, 1)';
        var grey = 'rgba(192,192,192,0.3)';
        var greyBorder = 'rgba(192,192,192,1)';

        var background = new Array()
        var border = new Array()

        background = new Array()
        border = new Array()

        for (var i in newData) {
            let val = new Number(newData[i] - initialData[i])
            if(val < 0) {
                background.push(green);
                border.push(greenBorder);
            }
            if(val > 0) {
                background.push(red);
                border.push(redBorder);
            }
            if(val == 0){
                background.push(grey);
                border.push(greyBorder);
            }
        }

        var newDataset = {
            label: '${g.message(code: "optimized")} CO2e',
            backgroundColor: background,
            borderColor: border,
            borderWidth: 1,
            data: newData
        }

        return newDataset
    }

    function updateChart(){
        console.log("CHART UPDATING")

        const data = getChartData(statusChart);

        let first = data.datasets[0]
        let second = data.datasets[1]

        myChart.data.datasets[0].data = first
        myChart.data.labels = data.labels

        if(equalsArray(first, second)){
            if(myChart.data.datasets.length == 2){
                myChart.data.datasets.splice(1,1)
            }
        }else{
            myChart.data.datasets[1] = addDataset(first, second);
        }
        myChart.update();
    }

    function equalsArray(a,b) {

        if (a === b) return true;
        if (a == null || b == null) return false;
        if (a.length != b.length) return false;

        for (var i = 0; i < a.length; ++i) {
            if (a[i] !== b[i]) return false;
        }
        return true;
    }
    //**************************************

    //**************************************
    // UTILITY METHODS For THE MODEL

    function findConstruction(model, constructionId){

        for (var i in model)
            for (var j in model[i].constructions)
                if(model[i].constructions[j].constructionId === constructionId) return model[i].constructions[j]

        return null;
    }

    function findGroupByConstruction(model, constructionId){

        for (var i in model)
            for (var j in model[i].constructions)
                if(model[i].constructions[j].constructionId === constructionId) return model[i]

        return null;
    }

    function replaceConstruction(newConstruction){

        if(!newConstruction){return false}
        var constructionId = newConstruction.constructionId

        for (var i in model){
            for (var j in model[i].constructions)
                if(model[i].constructions[j].constructionId === constructionId) {
                    model[i].constructions[j] = newConstruction;
                    return true;
                }
        }

        return false

    }

    //it shoud be better add the  constructionId and modify just a group
    //Update the groups [AT moment on all the group]

    //Should add a return false or true for the validation
    function updateGroupsView(){

        model.forEach(group => {
            var quantityByGroupSpan = $("#quantityByGroup" + group.oid);
            if (!$(quantityByGroupSpan).hasClass('dynamicDefaults')) {
                let oid = group.oid
                group.co2e = group.constructions.reduce((sum, c) => sum + c.co2e, 0)
                var co2Group = group.co2e;
                $("#co2Group" + group.oid).text(roundingJS(co2Group) + " tn");
                group.amount = group.constructions.reduce((sum, c) => sum + c.amount, 0);
                let conversionFactor = conversionUnitFactorJS('${unitSystem}', group.unit); //Conversion Unit System
                var amoutGroup = roundingJS(group.amount)
                let oldAmountGroup = roundingJS(group.initialAmount)//oldModel.find(g => g.oid == oid).initialAmount;

                let diffAmount = Math.abs(amoutGroup - oldAmountGroup)

                if(diffAmount >= 1){
                    oldAmountGroup = " [<s>" + roundingJS(oldAmountGroup * conversionFactor) + "</s>]";
                } else {
                    oldAmountGroup = ""
                }
                $(quantityByGroupSpan).html(roundingJS(amoutGroup * conversionFactor) + oldAmountGroup)
            }
        });

        <%-- Amount Quantity for the GROUP --%>

        updateShares();
    }

    //The method update the Model and then the View ---Should add a return false or true for the validation
    function updateConstructionView(newConstruction){

        if(!replaceConstruction(newConstruction)){return null;}
        var constructionId = newConstruction.constructionId

        //*****************************************************
        //Set the hidden input and the construction share quantity
        let conversionFactor = conversionUnitFactorJS('${unitSystem}', newConstruction.unit);
        let amount = roundingJS(newConstruction.amount)
        $("#span_total" + constructionId).text( roundingJS(amount * conversionFactor) )
        //It need to be showed again because when a constr. is updating, the unit is hidden to show the spinner
        $("#span_total_unit" + constructionId).removeClass("hidden")
        var shareInput = $("#quantity_share" + constructionId);
        $(shareInput).removeClass("hidden")
        $("#quantity_share_spinner" + constructionId).addClass("hidden")

        $("#total"+ constructionId).val(amount)

        let share = newConstruction.share
        share = share ? share : 0
        share = roundingToNumberJS(share * 100)
        $(shareInput).val(share);

        var tbody = $(shareInput).closest('tbody');

        var sum = getPercentageShareSumFromBody(tbody);

        if(!tbody.hasClass("hidden")){
            if (sum === 100) {
                $(tbody).closest('table').find('.groupShareHeading').html( "${g.message(code: "share")} " + sum + "%" );
            } else {
                $(tbody).closest('table').find('.groupShareHeading').html( "${g.message(code: "share")} <span style='color: red'>" + sum + "</span>%" );
            }
        }

        //*****************************************************

        <%-- Co2e, Co2e int and Co2e% for the CONSTRUCTION --%>

        var co2 = roundingJS(newConstruction.co2e);
        $("#co2" + constructionId).text(co2 + " tn");

        var newIntencity = parseFloat(newConstruction.co2eIntensity);


        var intesity = 0;

        if(!newIntencity){
            intesity = 0
        } else if (conversionFactor) {
            intesity = roundingJS(newIntencity / conversionFactor);
        }
        $("#co2Int" + constructionId).text(intesity +  " kg");

        updateGroupsView();

        <%-- Add new dataset to the charts --%>
        updateChart();

        calculateImpactOptimization();

        //To show or hide the edit link for a constuction
        enableConstructionEdit(constructionId, share)
    }
    //**************************************


    //**************************************
    // UTILITY METHODS

    function updateShares(){

        var newImpact = getNewImpact()
        newImpact = roundingJS(newImpact)

        //CO2e Share Materials
        for(i in model){
            var constructionGroupId = model[i].oid;
            var co2Group = model[i].co2e;
            var co2ShareGroup = roundingJS(co2Group/newImpact * 100);
            $("#co2ShareGroup" + constructionGroupId).text(co2ShareGroup + "%");

            for(j in model[i].constructions){
                var constructionId = model[i].constructions[j].constructionId;
                var co2 = model[i].constructions[j].co2e;
                var co2Share = roundingJS(co2/co2Group * 100)
                $("#co2Share" + constructionId).text(co2Share +  "%");
            }
        }

        //Co2e Share Energy
        energyTypeList.forEach(e => {
            let co2e = e.co2e
            let co2ShareGroup = roundingJS(co2e/newImpact * 100);
            $("#co2eShare" + e.typeId).text(co2ShareGroup + "%");
        })

    }

    var initialCo2eTotal = ${initialCo2e?:0};
    var initialCostTotal = ${initialCost?:0};

    function calculateImpactOptimization(){

        let newImpact = getNewImpact();

        let projectLevel = (newImpact - initialCo2eTotal);

        //Percetange of difference between newImpact and initial  Impact in Tons
        var diffImpact = projectLevel/initialCo2eTotal * 100;

        if(projectLevel < 0){
            $("#diffImpact").css('color', 'green');
            $("#projectLevel").css('color', 'green');

        }else if(projectLevel > 0){
            $("#diffImpact").css('color', 'red');
            $("#projectLevel").css('color', 'red');

        }else if (projectLevel == 0){
            $("#diffImpact").css('color', 'black');
            $("#projectLevel").css('color', 'black');

        }

        //The Intensity Impact is affected by Imperial Unit
        let newIntesityImpact = getNewIntensityImpact();
        let conversionFactor = conversionUnitFactorJS('${unitSystem}', "m2");

        //newIntesityImpact = roundingJS(newIntesityImpact / conversionFactor)
        newIntesityImpact = roundingJS(newIntesityImpact);
        diffImpact = roundingJS(diffImpact);
        projectLevel = roundingJS(projectLevel);

        //Update View Values
        $("#newImpact").text(newIntesityImpact);
        $("#diffImpact").text((projectLevel>0 ? "+" : "") + diffImpact + "%");
        $("#projectLevel").html((projectLevel>0 ? "+" : "") + projectLevel);

    }

    function getPercentageShareSumFromBody(bodyElement) {
        var percentageShares = $(bodyElement).find('.percentageShare').map(function() {
            var val = parseFloat($(this).val());
            if (val && !isNaN(val)) {
                return $(this).val();
            } else {
                return null
            }
        }).get();
        var sum = percentageShares.reduce((pv,cv)=>{
            return pv + (parseFloat(cv)||0);
        },0);

        if (sum && !isNaN(sum)) {
            return sum
        } else {
            return 0
        }
    }

    function showOrHide(element) {
        let el = $(element);
        let th = el.find("th");
        let tbody = el.next("tbody");

        if(tbody.hasClass("hidden")){
            <%-- Show the body of table --%>
            tbody.removeClass("hidden");
            <%-- Add minus symbol --%>
            el.find("i").removeClass("fa-plus");
            el.find("i").addClass("fa-minus");
            <%-- Show Headind pos 3,6 of table --%>

            // Not really ideal to hard code these...
            if($(th[2]).hasClass("hide_text") && $(th[2]).hasClass("groupShareHeading")){
                var sum = getPercentageShareSumFromBody(tbody);

                if (sum === 100) {
                    $(th[2]).html( "${g.message(code: "share")} " + sum + "%" )
                } else {
                    $(th[2]).html( "${g.message(code: "share")} <span style='color: red'>" + sum + "</span>%" )
                }
            }
            el.find("th.col_intensity").children().removeClass("hidden");
            el.find("th.comment").text("${commentHeading}");
            //if($(th[5]).hasClass("hide_text")){$(th[5]).text( "${g.message(code: "carbonIntensity")}" )}
        } else {
            <%-- Hide the body of table --%>
            tbody.addClass("hidden");
            <%-- Add plus symbol --%>
            el.find("i").removeClass("fa-minus");
            el.find("i").addClass("fa-plus");
            <%-- Hide Headind pos 3,7 of table --%>
            // Not really ideal to hard code these...
            if($(th[2]).hasClass("hide_text")){$(th[2]).html("")}
            el.find("th.col_intensity").children().addClass("hidden");
            el.find("th.comment").text("")

            //if($(th[5]).hasClass("hide_text")){$(th[5]).text("")}
        }
    }

    //***********************************************
    //Show and Hide Spinner
    function showSpinner(label = ""){

        $("#title_spinner").text(label);
        $("#main_body").addClass("hidden");
        $("#spinner").removeClass("hidden");
    }

    function hideSpinner(){
        $("#main_body").removeClass("hidden");
        $("#spinner").addClass("hidden");
    }
    //***********************************************

    // Input Field Numeric Class Validation
    $('.numeric').on('input propertychange', numericInputValidation);

    function numericInputValidation(){

        var start = this.selectionStart;
        end = this.selectionEnd;
        var val = $(this).val();

        if (val) {
            $(this).popover('hide');
        }
        if (isNaN(val)) {
            val = val.replace(",",".")
            val = val.replace(/[^0-9\.]/g, '');

            if (val.split('.').length > 2) {
                val = val.replace(/\.+$/, '');
            }
        }

        $(this).val(val);
        this.setSelectionRange(start, end);
    }

    function roundingJS(d) {

        if(!d){
            return 0
        }

        d = new Number(d)

        if (d >= 10) {
            return round(d, 0)
        }
        if(d >= 1 && d < 10) {
            return  round(d,1)
        }
        if (d < 1 && d >= 0.01) {
            return  round(d,2)
        }
        if (d < 0.01 && d >= 0) {
            return "~" + 0
        }

        if(d < 0) {
            return  round(d, 2)
        }

    }

    function roundingToNumberJS(d) {

        if(!d){
            return 0
        }

        d = new Number(d)

        if (d >= 10) {
            return round(d, 0)
        }
        if(d >= 1 && d < 10) {
            return  round(d,1)
        }
        if (d < 1 && d >= 0.01) {
            return  round(d,2)
        }
        if (d < 0.01 && d >= 0) {
            return 0
        }
        if(d < 0) {
            return  round(d, 2)
        }

    }

    function round(value, decimals) {
        return Number(Math.round(value+'e'+decimals)+'e-'+decimals);
    }

    //**************************************************
    function saveDraft(){

        let successCallBack = function (data) {
            Swal.fire(
                data.output,
                '',
                'success'
            )
        };

        saveDraftByAjax(successCallBack, errorServerReply)


    }

    function saveDraftByAjax(successCallBack, errorCallback){
        var dummyModel = [];

        model.forEach(group => {
            var newGroup = $.extend(true, {}, group);
            newGroup.constructions = $.grep(group.constructions, function(construction) {
                return construction.share > 0;
            });
            dummyModel.push(newGroup)
        });

        let json = { sessionId: "${sessionId}",  jsonSimModel: dummyModel, jsonEnergyTypeList: energyTypeList, lccIndicatorId: "${lccIndicator?.indicatorId}" };
        let url = '/app/sec/util/earlyPhaseToolSaveDraft';

        ajaxForJson(url, json, successCallBack, errorCallback)

    }

    //***********************************************************
    //ENERGY SECTION
    //***********************************************************

    function updateEnergyType(typeId, async = true){

        let demand_m2 = $("#" + typeId).find("#" + "demand_m2" + typeId).val();
        let effFactor = $("#" + typeId).find("#" + "effFactor" + typeId).val();
        let defaultOption =  $("#" + typeId).find("#" + "autocomplete" + typeId).attr("defaultOption");
        let defaultOptionName = $("#" + typeId).find("#" + "autocomplete" + typeId).val();

        demand_m2 = new Number(demand_m2) * conversionUnitFactorJS('${unitSystem}', 'kBtu');
        effFactor = new Number(effFactor);

        let energyType = energyTypeList.find(it => it.typeId === typeId);

        energyType.demand_m2 = demand_m2;
        energyType.efficiencyFactor = effFactor;
        energyType.defaultOption = defaultOption;
        energyType.defaultOptionName = defaultOptionName;

        if(energyType && demand_m2 && effFactor && defaultOption){
            //MAybe add here the rotation
            calculateEnergyImpact(energyType, async);
        } else {
            energyType.co2e = 0;
            energyType.manualId = "";
            drawEnergyType(energyType.typeId);
            updateChart();
            calculateImpactOptimization();
            updateShares();
            <g:if test="${lccIndicator}">
            onChangeEnergyCost(typeId, $("#lcc_share" + typeId).val());
            </g:if>
        }
    }

    function onChangeEnergyCost(typeId, cost) {
        let energyType = energyTypeList.find(it => it.typeId === typeId);
        var purchaseKwh = parseFloat($("#" + "purchase" + typeId).text()) * conversionUnitFactorJS('${unitSystem}', 'kBtu');
        var costFloat = parseFloat(cost);

        if (energyType && purchaseKwh && costFloat) {
            let json = {sessionId: "${sessionId}", jsonEnergyType : energyType, purchaseKwh: purchaseKwh, userGivenCost: costFloat };

            $.ajax({
                type: 'POST',
                dataType: 'json',
                data: JSON.stringify(json),
                contentType: "application/json; charset=utf-8",
                url: '/app/sec/simulationTool/updateEnergyCost',
                async: true,
                success: function (data, textStatus) {
                    var calculatedCost = data.calculatedCost;
                    energyType.userGivenCost = costFloat;

                    $("#lcc_share" + typeId).attr("data-calculatedCost", calculatedCost);

                    updateCostHeading(typeId, true);
                    updateCostChart();
                }
            });
        }
    }



    function drawEnergyType(typeId) {

        let energyType = energyTypeList.find(it => it.typeId === typeId);

        if(energyType) {
            let demand_m2 = energyType.demand_m2;
            let effFactor = energyType.efficiencyFactor;
            let heatedArea = energyType.heatedArea;
            let totalDemand = heatedArea * demand_m2;
            let purchase = purchaseEnergyType(energyType);

            let co2e = energyType.co2e ? roundingJS(energyType.co2e) : "-"
            $("#" + "totalDemand" + typeId).text(roundingJS(totalDemand * conversionUnitFactorJS('${unitSystem}', 'kWh')));
            $("#" + "purchase" + typeId).text(roundingJS(purchase * conversionUnitFactorJS('${unitSystem}', 'kWh')));
            $("#" + "co2e" + energyType.typeId).text(co2e);

            let idQMark = typeId;

            let a = document.getElementById(idQMark);
            a.setAttribute("data-profileId", energyType.defaultOption);
            a.onclick = () =>{
                renderSourceListingToAnyElement(energyType.defaultOption, idQMark, '${indicatorId}', 'questionId', 'profileId', '${entityId}', true, 'queryId', 'sectionId')
            };
        }

    }

    function calculateEnergyImpact(energyType, async){

        let json = {sessionId: "${sessionId}", jsonEnergyType : energyType };

        let url = '/app/sec/util/simulationToolEnergyImpact';

        let successCallback = function (data) {
            energyType.co2e = data.co2e;
            energyType.manualId = data.manualId;
            drawEnergyType(energyType.typeId);
            if(async){ updateChart();}
            calculateImpactOptimization();
            updateShares();
            <g:if test="${lccIndicator}">
            onChangeEnergyCost(energyType.typeId, $("#lcc_share" + energyType.typeId).val());
            </g:if>
        };

        ajaxForJson (url, json, successCallback, errorServerReply);
    }

    function purchaseEnergyType(energyType){
        let demand_m2 = energyType.demand_m2;
        let effFactor = energyType.efficiencyFactor;
        let heatedArea = energyType.heatedArea;
        let totalDemand = heatedArea * demand_m2;
        let purchase

        if(effFactor && effFactor > 0) {
            purchase = totalDemand / effFactor;
        } else {
            console.log("Invalid value of Eff Factor --> " + effFactor)
            purchase = 0;
        }

        return purchase
    }

    function updateEnergyImpact(){

        energyTypeList.forEach(it => {
            updateEnergyType(it.typeId, false);
        });

        updateChart();
    }

    //***********************************************************

    function getNewIntensityImpact(){

        let newImpact = getNewImpact();

        let grossFloorArea = new Number(${grossFloorArea})
        grossFloorArea = grossFloorArea ? grossFloorArea : 1

        newImpact *= 1000 //Convert value to tons --> kg

        let newImpactIntensity = newImpact / grossFloorArea

        return newImpactIntensity

    }

    function getNewImpact(){

        let newImpact = model.reduce((sum, g) => sum + g.co2e, 0);
        let impactEnergy = energyTypeList.reduce((sum, g) => sum + g.co2e, 0);
        newImpact += impactEnergy

        return newImpact
    }

    function createAutocomplete(typeId, list, defaultOptionName){

        let energyType = energyTypeList.find(it => it.typeId === typeId);

        if(list && list.length > 0){
            $("#autocomplete" + typeId ).devbridgeAutocomplete({
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
                    updateEnergyType(typeId)
                }
            });

            $("#autocomplete" + typeId ).blur(function () {
                $(this).val(energyType.defaultOptionName)
            })
        }

        energyType.defaultOptionName = defaultOptionName
        setTimeout( () => {
            $("#" + typeId).find("#autocomplete" + typeId).val(defaultOptionName);
        }, 500)

    }

    function showAllOptions(type){

        setTimeout( () => {
            $("#autocomplete" + type ).val("").devbridgeAutocomplete("onValueChange");
            $("#autocomplete" + type ).trigger('focus');
        }, 100)

    }

    <g:each var="energyType" in="${energyTypeList}">
        getOptionsAjax('${energyType?.typeId}');
    </g:each>

    function getOptionsAjax(typeId){

        let energyType = energyTypeList.find(it => it.typeId === typeId);

        let json = {sessionId: "${sessionId}", energyType : energyType, indicatorId: "${indicatorId}"};

        let url = '/app/sec/util/getOptionsForEnergyType';

        let errorCallback = () => {console.log("ERROR CREATE AUCOMPLETE"); console.log(energyType)}

        let successCallback = function (data) {
            if(data){
                console.log(data)
                createAutocomplete(typeId, data.options, data.defaultOptionName)
            }
        };

        ajaxForJson (url, json, successCallback, errorCallback)

    }

    function errorServerReply(event = null){
        errorSwal('warning', "${g.message(code : "popup.noServer.title")}", "${g.message(code : "popup.noServer.body")}",  event)
    }

    //*********************************************************************************************************************************
    //Conversion Unit System Function
    //*********************************************************************************************************************************

    var conversionUnitTable = ${com.bionova.optimi.core.service.SimulationToolService.conversionUnitTable as grails.converters.JSON}

    function conversionUnitJS(unitSytem, unit) {

        if (unitSytem == '${UnitConversionUtil.UnitSystem.IMPERIAL.value}') {

            let convertedUnit
            if(conversionUnitTable[unit]) convertedUnit = conversionUnitTable[unit].unit
            if (!convertedUnit) { console.log("ERROR Conversion Unit is missing for " + convertedUnit + " --> " + unitSytem) }
            convertedUnit = convertedUnit ? convertedUnit : unit

            return convertedUnit
        } else {
            return unit
        }

    }

    function conversionUnitFactorJS(unitSytem, unit){

        if (unitSytem == '${UnitConversionUtil.UnitSystem.IMPERIAL.value}') {

            let conversionFactor
            if(conversionUnitTable[unit]) {
                conversionFactor = conversionUnitTable[unit].conversionFactor
            }
            if (!conversionFactor) { console.log("ERROR Conversion Unit Factor is missing for " + conversionFactor + " --> " + unitSytem) }
            conversionFactor = conversionFactor ? conversionFactor : 1

            return new Number(conversionFactor)
        }else {
            return 1
        }
    }

    function swalSetDefaultMaterialType() {

        let materialType = $("#materialTypeSelect").val()

        let resetSelect = () => { $("#materialTypeSelect").val("") }

        if(materialType) {
            Swal.fire({
                title: "${g.message(code: "simulationTool.swalTitleDefaultMaterial")}",
                text: "${g.message(code: "simulationTool.swalTextDefaultMaterial")}",
                icon: 'warning',
                showCancelButton: true,
                showCloseButton: true,
                reverseButtons: true,
                confirmButtonColor: '#d62317',
                cancelButtonColor: '#bbbdb7',
                cancelButtonText: '${g.message(code: "no")}',
                confirmButtonText: "${g.message(code: "yes")}"
            }).then(result => {
                if(result.value) {
                    setDefaultMaterialType(materialType)
                } else {
                    resetSelect()
                }
            });

        }
    }

    function setDefaultMaterialType(materialType){

        let label = "${g.message(code: "simulationTool.loadingDefaultMaterial")}"
        showSpinner(label);

        let ajaxList
        if(materialType != '${RESET_DEFAULT_MATERIAL}'){
            ajaxList = setModelDefaultMaterial(materialType)
        } else {
            ajaxList = revertModelDefaultMaterial()
        }

        if(ajaxList && ajaxList.length > 0){
            executeAjaxList(0, ajaxList)
        } else{
            hideSpinner()
        }

    }

    function materialIsPresentInGroup(group, materialType){

        let found
        for (let c of group.constructions){
            found = materialIsPresentInConstruction(c, materialType)
            if(found) {return true}
        }

        return false
    }

    function materialIsPresentInConstruction(construction, materialType){

        let found
        let materialList = construction.materialTypeList
        if(materialList && Array.isArray(materialList)){
            found = materialList.find(m => m == materialType)
            if(found)   {return true}
            else        {return false}
        }

    }

    function setModelDefaultMaterial(materialType) {

        let ajaxList = new Array()

        let a = 0
        model.forEach(group => {
            let isPresent = materialIsPresentInGroup(group, materialType)
            console.log("GroupId: " + group.oid + " " + isPresent)
            if(isPresent){
                group.constructions.forEach(c => {
                    let found = materialIsPresentInConstruction(c, materialType)
                    let ajax
                    if (found){
                        ajax = updateConstructionAreaJson(c.constructionId, 100)
                    }else {
                        ajax = updateConstructionAreaJson(c.constructionId, 0)}
                    if(ajax){
                        ajaxList.push(ajax)
                    }

                })
            }
        })

        return ajaxList

    }

    function revertModelDefaultMaterial(){

        model = JSON.parse(JSON.stringify(oldModel))

        let ajaxList = new Array()

        model.forEach(group => {
            group.constructions.forEach(c => {
                let json = {sessionId : "${sessionId}", jsonConstruction : c}
                ajaxList.push(json)
            })
        })

        return ajaxList
    }


    function succV2 (data, i, arrayList) {

        var newConstruction = data.simConstruction
        updateConstructionView(newConstruction)
        i++
        if(i < arrayList.length) {
            executeAjaxList(i, arrayList)
        } else {
            $('.showHiddenConstructionsButton').trigger('click');
            hideSpinner();
        }
    }

    function executeAjaxList(i, ajaxList){

        let url = '/app/sec/util/updateConstructionImpact';
        let json = ajaxList[i]

        ajaxV2(url, json).done(function(data){
            succV2 (data, i, ajaxList)
            <g:if test="${lccIndicator}">
            var lcc = $('#lcc_share' + json.jsonConstruction.constructionId);
            if (lcc.length) {
                onChangeConstructionCost(json.jsonConstruction.constructionId, $(lcc), $(lcc).attr("data-costgroup"));
            }
            </g:if>
        }).fail(function(data){
            errorServerReply(data)
        })

    }

    function ajaxV2(url, json){

       let ajax =  $.ajax({
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(json),
            contentType: "application/json; charset=utf-8",
            url: url
        })

       return ajax
    }

    function onUpdateConstruction(constructionId){

        if(constructionId){
            let spinner = "<i class='fas fa-circle-notch fa-spin oneClickColorScheme'></i>"

            $("#span_total" + constructionId).empty().append(spinner)
            //Hide the unit of the total
            $("#span_total_unit" + constructionId).addClass("hidden")

            $("#co2" + constructionId).empty().append(spinner)
            $("#co2Share" + constructionId).empty().append(spinner)
            $("#co2Int" + constructionId).empty().append(spinner)

            $("#quantity_share" + constructionId).addClass("hidden")
            $("#quantity_share_spinner" + constructionId).removeClass("hidden")

            onUpdateGlobalValue()
        }

    }

    function onUpdateGlobalValue(){
        let spinner = "<i class='fas fa-circle-notch fa-spin oneClickColorScheme'></i>"

        $("#newImpact").empty().append(spinner)
        $("#diffImpact").empty().append(spinner)
        $("#projectLevel").empty().append(spinner)
    }


    //********************************************************************************************************************
    //Create list of constructions with 0 share
    //********************************************************************************************************************

    hideConstructions()

    function hideConstructions(){

        model.forEach( group => buildGroupStuff(group))

    }

    function buildGroupStuff(group){
        let constructions = group.constructions

        if(Array.isArray(constructions)){
            let share_100 = constructions.filter(c => c.share == 1)
            let share_0 = constructions.filter(c => c.share == 0)

            if(constructions.length > 1 && share_100 && share_100.length == 1 && share_0 && share_0.length == constructions.length - 1 ){
                let construction = share_100[0]
                buildSelectForDefaultShare(construction, constructions, group.oid)
                constructions.forEach(c => {
                    if (c.share == 0) {
                        $("#constructionRow" + c.constructionId).hide()
                        $("#span_total" + c.constructionId).text(0)
                    } else {
                        $("#constructionRow" + c.constructionId).show()
                        /*Do nothing?
                        $("#span_total" + c.constructionId).text(group.amount)
                        console.log("KIKKARE 2: " + group.amount)*/
                    }
                })

            }else {
                $("#showHiddenConstructions" + group.oid).hide()
            }
        }
    }

    function showConstructionRow_v2(groupId){

        if(groupId){
            let group = model.find(g => g.oid === groupId);
            let constructions = group && Array.isArray(group.constructions) ? group.constructions : null;
            if(constructions){
                constructions.forEach( c => {
                    //Destroy select and show the name
                    if (c.name && c.name.startsWith("(PRIVATE)")) {
                        $("#name_span" + c.constructionId).empty().append("<i class='far fa-eye-slash' aria-hidden='true'></i>" + c.name.split("(PRIVATE)")[1])
                    } else {
                        $("#name_span" + c.constructionId).empty().append(c.name)
                    }
                    $("#constructionRow" + c.constructionId).show();
                    $("#showHiddenConstructions" + c.constructionId).hide();
                })
            }

        }

    }

    function buildSelectForDefaultShare(defualtConstruction, constructions, groupId){

        if(defualtConstruction && constructions){

            let default_cId = defualtConstruction.constructionId
            $("#name_span" + default_cId).empty()

            let options = constructions.map(c => ({value : c.constructionId, text : c.name}));
            let selectedOption = constructions.find(c => c.constructionId == default_cId);
            let select = createSelect_CD(null, default_cId, options, groupId)

            $("#name_span" + default_cId).append(select)

            let locText = "${g.message(code: "simulationTool.expandOptions")}"
            let a = document.createElement("a")
            a.style.cursor = "pointer";
            a.onclick = () => showConstructionRow_v2(groupId)
            a.innerHTML = "</br><i class='fa fa-plus'></i>" + " " + locText
            a.className = "showHiddenConstructionsButton";
            $("#showHiddenConstructions" + default_cId).empty().append(a)

            let rowIncompatibleSpan = $('#constructionRow' + default_cId).find(".rowIncompatible");
            if (rowIncompatibleSpan.length) {
                if (selectedOption && selectedOption.isIncompatible) {
                    rowIncompatibleSpan.empty().append('<a href="javascript:" class="cdDataIncompatiblePopover" rel="popover" data-trigger="hover" data-html="true" data-content="${g.message(code: "simulationTool.incompatibleBuilding")}">${asset.image(src:"img/icon-warning.png", style:"margin-bottom: 5px; max-width:16px")}</a>');
                    initCdDataIncompatiblePopover();
                } else {
                    rowIncompatibleSpan.empty();
                }
            }
        }
    }

    function createSelect_CD(id, selectedValue, options, groupId){

        let isDefault = function(a, b){
            return a === b ? true : false
        }

        let select = document.createElement("SELECT")
        select.classList.add("constshareinput")
        select.onchange = () => swapSelectedConstruction(select, selectedValue, groupId)

        options.forEach( o => {
            let isSelected = isDefault(selectedValue, o.value)
            let option = new Option(o.text, o.value, isSelected, isSelected)
            select.add(option)
        })

        return select
    }

    function swapSelectedConstruction(select_this, oldConstructionId, groupId){
        let selectedValue = $(select_this).val();
        onUpdateConstruction(oldConstructionId)
        onUpdateConstruction(selectedValue)
        disableInputs();

        let ajax1 = updateConstructionAreaJson(selectedValue, 100)
        let ajax2 = updateConstructionAreaJson(oldConstructionId, 0)

        let ajaxList = [ajax1, ajax2]

        let lastFunction = () => {

            let group = model.find( g => g.oid === groupId)
            if(group) buildGroupStuff(group)

            updateConstructionView_v2(selectedValue)
            updateConstructionView_v2(oldConstructionId)

            updateGroupsView();
            updateChart();
            calculateImpactOptimization();

            <g:if test="${lccIndicator}">
            onChangeConstructionCost(oldConstructionId, $('#lcc_share' + oldConstructionId), groupId, false, false, true);
            onChangeConstructionCost(selectedValue, $('#lcc_share' + selectedValue), groupId, false, false, true);
            </g:if>

            enableInputs()
        }

        if(ajaxList && ajaxList.length > 0){
            executeAjaxListV3(0, ajaxList, lastFunction)
        }

    }

    //**************************************************************************************
    //CALLBACK HELL
    //**************************************************************************************

    function succV3 (data, i, arrayList, lastFunction) {

        var newConstruction = data.simConstruction
        replaceConstruction(newConstruction)
        i++
        if(i < arrayList.length) {
            executeAjaxListV3(i, arrayList, lastFunction)
        } else {
            if(lastFunction) lastFunction();
        }
    }

    function executeAjaxListV3(i, ajaxList, lastFunction){

        let url = '/app/sec/util/updateConstructionImpact';
        let json = ajaxList[i]

        ajaxV3(url, json).done(function(data){
            succV3 (data, i, ajaxList, lastFunction)
        }).fail(function(data){
            errorServerReply(data)
        })

    }

    function ajaxV3(url, json){

        let ajax =  $.ajax({
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(json),
            contentType: "application/json; charset=utf-8",
            url: url
        })

        return ajax
    }

    //The method update the Model and then the View ---Should add a return false or true for the validation
    function updateConstructionView_v2(constructionId){

        var newConstruction = findConstruction(model, constructionId)

        if(!newConstruction) return null

        //*****************************************************
        //Set the hidden input and the construction share quantity
        let conversionFactor = conversionUnitFactorJS('${unitSystem}', newConstruction.unit);
        let amount = roundingJS(newConstruction.amount)
        $("#span_total" + constructionId).text( roundingJS(amount * conversionFactor) )
        //It need to be showed again because when a constr. is updating, the unit is hidden to show the spinner
        $("#span_total_unit" + constructionId).removeClass("hidden")
        $("#quantity_share" + constructionId).removeClass("hidden")
        $("#quantity_share_spinner" + constructionId).addClass("hidden")

        $("#total"+ constructionId).val(amount)

        let share = newConstruction.share
        share = share ? share : 0
        share = roundingToNumberJS(share * 100)
        $("#quantity_share"+ constructionId).val(share)

        //*****************************************************

        <%-- Co2e, Co2e int and Co2e% for the CONSTRUCTION --%>

        var co2 = roundingJS(newConstruction.co2e);
        $("#co2" + constructionId).text(co2 + " tn");


        var newIntencity = parseFloat(newConstruction.co2eIntensity);
        var intesity = 0;

        if(!newIntencity){
            intesity = 0
        } else if (conversionFactor) {
            intesity = roundingJS(newIntencity / conversionFactor);
        }
        $("#co2Int" + constructionId).text(intesity +  " kg");

        //To show or hide the edit link for a constuction
        enableConstructionEdit(constructionId, share)
    }

    //*********************************************************************************************************************************

    function disableInputs(){
        disableInputsByClass("constshareinput");
        $('.showHiddenConstructionsButton').addClass("removeClicks");
    }

    function enableInputs(){
        setTimeout(() => {
            enableInputsByClass("constshareinput");
            $('.showHiddenConstructionsButton').removeClass("removeClicks");
        }, 100)
    }

    function disableInputsByClass(cssClass){
        if(!cssClass) return null

        let inputs = $("." + cssClass)

        inputs.prop("readonly", true)
        inputs.css( 'pointer-events', 'none' );

    }

    function enableInputsByClass(cssClass){
        if(!cssClass) return null

        let inputs = $("." + cssClass)

        inputs.prop("readonly", false)
        inputs.css( 'pointer-events', 'auto' );
    }

    //-----------------------------------------------------
    function showRegionInformation(){

        let text = "${g.message(code: "simulationTool.secondPage.baselineHeading", args: [indicatorName?:"", calculationPeriod ?: "-", regionReferenceName, (grossFloorArea?.toInteger()?.toString() ?: 0), conversionUnit(unitSystem, "m2"), totalFloors, ""])}"
        let link = "<br/>" + "${regionInfoText ?: ""}"
        let div = "<label style='font-size: 12px !important; font-family: Helvetica Neue, Helvetica, Arial, sans-serif; !important'>"+ text + link + "</label>"
        if(text){
            //$("#modalBodyRegionInfo").empty().append(div);
            $("#modalRegionInfo").modal();
        }

    }

    //*******************************************************************************************************************
    //NEW MaterialType and Energy Scenario changes
    //*******************************************************************************************************************

    var jsonDefaultChoicesSets = ${defaultChoicesSets ? defaultChoicesSets as grails.converters.JSON : 'null'};
    var jsonEnergyScenarioMap = ${energyScenarioMap ? energyScenarioMap as grails.converters.JSON : 'null'};

    async function swalSetDefaultType(select){
        const value = $(select).val()
        let materialType
        let energyScenario
        if(value && jsonDefaultChoicesSets){
            let defaultType = jsonDefaultChoicesSets.find(d => d.defaultChoiceId === value)
            if(defaultType){
                materialType = defaultType.materialType
                energyScenario = defaultType.energyScenario
            }
        }

        let resetSelect = () => { $(select).val("") }

        if(materialType || energyScenario) {
            Swal.fire({
                title: "${g.message(code: "simulationTool.swalTitleDefaultMaterial")}",
                text: "${g.message(code: "simulationTool.swalTextDefaultMaterial")}",
                icon: 'warning',
                showCancelButton: true,
                showCloseButton: true,
                reverseButtons: true,
                confirmButtonColor: '#d62317',
                cancelButtonColor: '#bbbdb7',
                cancelButtonText: '${g.message(code: "no")}',
                confirmButtonText: "${g.message(code: "yes")}"
            }).then(result => {
                if(result.value) {
                    setDefaultMaterialType(materialType)
                } else {
                    resetSelect()
                }
            });
        }

    }

    async function setDefaultType(materialType, energyScenario, isAlternativeScenario){

        let label = "${g.message(code: "simulationTool.loadingDefaultMaterial")}"

        if(isAlternativeScenario) {
            label = "${g.message(code: "simulationTool.loadingAlternativeScenario")}"
        }

        showSpinner(label);

        <g:if test="${!isAlternativeScenarioEnergy}">
            if(energyScenario){
                await changeEnergyScenario(energyScenario)
                //updateEnergyScenarioView()
                updateOneTimeView()
            }
        </g:if>
        <g:else>
            updateOneTimeView()
        </g:else>


        if(materialType) {
            await setDefaultMaterialType_v2(materialType)
        } else {
            hideSpinner()
        }

        console.log("MaterialType:" + materialType + " EnergyScenario: " + energyScenario)
    }

    function updateOneTimeView(){
        updateChart();
        calculateImpactOptimization();
        updateShares();

    }

    async function changeEnergyScenario(energyScenario) {

        let url = '/app/sec/util/simulationToolEnergyImpact';

        if (jsonEnergyScenarioMap && energyTypeList) {
            let eSMap = jsonEnergyScenarioMap[energyScenario]
            if (eSMap) {
                //Calculate Impact for each energyType
                const keys = Object.keys(eSMap)
                for (const key of keys) {
                    let value = eSMap[key]
                    await updateEnergyType2(key, value);
                }

            }

        }

        return null
    }

    async function updateEnergyType2(typeId, energyTypeMap){

        if(!energyTypeList) return null
        let energyType = energyTypeList.find(it => it.typeId === typeId);
        if(!energyType) return null

        let demand_m2 = energyTypeMap.demand_m2 || 0
        let effFactor = energyTypeMap.efficiencyFactor || 0

        $("#" + typeId).find("#" + "demand_m2" + typeId).val(roundingToNumberJS(demand_m2));
        $("#" + typeId).find("#" + "effFactor" + typeId).val(roundingToNumberJS(effFactor));

        demand_m2 = new Number(demand_m2) * conversionUnitFactorJS('${unitSystem}', 'kBtu');
        effFactor = new Number(effFactor);

        energyType.demand_m2 = demand_m2;
        energyType.efficiencyFactor = effFactor;

        let json = {sessionId: "${sessionId}", jsonEnergyType : energyType };
        let url = '/app/sec/util/simulationToolEnergyImpact';

        //If the default option is not selected, not calculate the impact
        let defaultOption = energyType.defaultOption

        try{
            if(defaultOption){
                let result = await fetch(url,{method : "POST", body: JSON.stringify(json)})
                let jsonResult = await result.json()
                energyType.co2e = jsonResult.co2e;
                energyType.manualId = jsonResult.manualId;
                console.log(typeId)
            } else {
                energyType.co2e = 0;
            }

            drawEnergyType(energyType.typeId);

            <g:if test="${lccIndicator}">
            await onChangeEnergyCost_v2(typeId, $("#lcc_share" + typeId).val());
            console.log("Energy cost updated: " + typeId)
            updateCostHeading(typeId, true);
            updateCostChart();
            </g:if>

            return null //Decide What return!!!!!!????????????????

        } catch(e){
            console.log(e)
            return null //Decide What return!!!!!!????????????????
        }

    }

    async function onChangeEnergyCost_v2(typeId, cost) {
        try{
            let energyType = energyTypeList.find(it => it.typeId === typeId);
            var purchaseKwh = parseFloat($("#" + "purchase" + typeId).text()) * conversionUnitFactorJS('${unitSystem}', 'kBtu');
            var costFloat = parseFloat(cost);

            if (energyType && purchaseKwh && costFloat) {

                let url = "/app/sec/simulationTool/updateEnergyCost"
                let json = {sessionId: "${sessionId}", jsonEnergyType : energyType, purchaseKwh: purchaseKwh, userGivenCost: costFloat };
                let result = await fetch(url,{method : "POST", body: JSON.stringify(json)})
                let jsonResult = await result.json()

                let calculatedCost = jsonResult.calculatedCost;
                energyType.userGivenCost = costFloat;
                console.log("ENERGY COST:")
                console.log(calculatedCost)
                $("#lcc_share" + typeId).attr("data-calculatedCost", calculatedCost);
                return null

            }

        }catch(e){
            console.log(e)
            return null //Decide What return!!!!!!????????????????
        }
    }

    async function onChangeConstructionCost_v2(constructionId, element, costGroup, skipUiUpdates) {
        try{

            var json = updateConstructionCostJson(constructionId, $(element).val());
            if(!json) return null

            disableInputs();

            var costError = $(element).closest('tr').find('.lccErrorPopover');

            if (costError.length) {
                costError.remove();
            }
            let url = '/app/sec/simulationTool/updateConstructionCost'

            let result = await fetch(url,{method : "POST", body: JSON.stringify(json)})
            let jsonResult = await result.json()

            if (!skipUiUpdates) {
                enableInputs();
            }
            var newConstruction = jsonResult.simConstruction;
            var calculatedCost = newConstruction.calculatedCost;

            var temp = findConstruction(model, constructionId);
            var costInEuropean = parseFloat(newConstruction.userGivenCost);
            var convertedCost = costInEuropean / conversionUnitFactorJS('${unitSystem}', newConstruction.unit);

            temp.userGivenCost = convertedCost;
            temp.calculatedCost = calculatedCost;
            $(element).attr("data-calculatedCost", calculatedCost);
            if (!skipUiUpdates) {
                updateCostHeading(costGroup);
                updateCostChart();
            }

            return null
        } catch (e) {
            console.log(e)
            return null
        }
    }

    //**********************************************************************************************************************************************

    async function setDefaultMaterialType_v2(materialType){

        let label = "${g.message(code: "simulationTool.loadingDefaultMaterial")}"
        showSpinner(label);

        let ajaxList
        if(materialType != '${RESET_DEFAULT_MATERIAL}'){
            ajaxList = setModelDefaultMaterial(materialType)
        } else {
            ajaxList = revertModelDefaultMaterial()
        }

        await executeAjaxList_syncro(ajaxList)

        hideSpinner()
    }

    async function executeAjaxList_syncro(jsonList){

        if(!Array.isArray(jsonList) && ajaxList.length > 0) return null

        try{
            let url = '/app/sec/util/updateConstructionImpact';
            for( const json of jsonList){
                let response = await fetch(url, {method : "POST", body: JSON.stringify(json)})
                let jsonResult = await response.json()

                var newConstruction = jsonResult.simConstruction
                updateConstructionView(newConstruction)

                $('.showHiddenConstructionsButton').trigger('click');

                <g:if test="${lccIndicator}">
                var lcc = $('#lcc_share' + json.jsonConstruction.constructionId);
                if (lcc.length) {
                    await onChangeConstructionCost_v2(json.jsonConstruction.constructionId, $(lcc), $(lcc).attr("data-costgroup"));
                }
                </g:if>
            }
        } catch (e) {
            errorServerReply(null)
            console.log("Set default materirial fail")
            console.error(e)
        }

    }
    async function saveDraft_async(){

        disableElementFor("#saveDraftButton", 2000);

        try{
            var dummyModel = [];

            model.forEach(group => {
                var newGroup = $.extend(true, {}, group); // Deep clone
                newGroup.constructions = $.grep(group.constructions, function(construction) {
                    return construction.share > 0; // Save model with constructions that have a share
                });
                dummyModel.push(newGroup)
            });

            let json = { sessionId: "${sessionId}",  jsonSimModel: dummyModel, jsonEnergyTypeList: energyTypeList, lccIndicatorId: "${lccIndicator?.indicatorId}" };
            let url = '/app/sec/util/earlyPhaseToolSaveDraft';

            const response = await fetch(url,{method: "POST", body: JSON.stringify(json)})
            const jsonResult = await response.json()

            oldModel = JSON.parse(JSON.stringify(model));
            oldEnergyTypeList = JSON.parse(JSON.stringify(energyTypeList));
            if(myChart.data.datasets.length == 2){
                myChart.data.datasets[0].data = myChart.data.datasets[1].data
                myChart.data.datasets.pop()
                myChart.update();


            }
            <g:if test="${lccIndicator}">
            if(costChart.data.datasets.length == 2) {
                costChart.data.datasets[0].data = costChart.data.datasets[1].data
                costChart.data.datasets.pop()
                costChart.update();

            }
            </g:if>

            resetImpactOptimization();

            Swal.fire(jsonResult.output, '', 'success')

        }catch (e) {
            errorServerReply()
            console.error(e)
        }

    }

    function resetImpactOptimization() {

        initialCo2eTotal = getNewImpact();

        $("#diffImpact").css('color', 'black');
        $("#projectLevel").css('color', 'black');

        //Update View Values

        let newIntesityImpact = getNewIntensityImpact()

        $("#initialImpactBaseline").text(roundingJS(newIntesityImpact));
        $("#newImpact").text("-");
        $("#diffImpact").text("-%")
        $("#projectLevel").html("-")

    }


    //************************************************************************************************************
    // CHART STATUS
    //************************************************************************************************************


    function getChartData(status) {

        let first = oldModel.map(g => g.constructions.reduce((sum, c) => sum + c.co2e, 0));
        let second = model.map(g => g.constructions.reduce((sum, c) => sum + c.co2e, 0));
        let firstEnergy = oldEnergyTypeList ? oldEnergyTypeList.map(it => it.co2e || 0) : []
        let secondEnergy = energyTypeList ? energyTypeList.map(it => it.co2e || 0) : []

        let labels;

        const STATUS1 = "${byConstructionGroup}"
        const STATUS2 = "${byBuildingElement}"
        const STATUS3 = "${byTotal}"

        switch(status){
            case STATUS1 :
                first = first.concat(firstEnergy)
                second = second.concat(secondEnergy)
                labels = chartLabels[STATUS1]
                break;
            case STATUS2 :
                let a = oldModel.map(g => ({buildingElement : constructionGroupList.find(it => it.groupId == g.oid).buildingElement , co2e : g.constructions.reduce((sum, c) => sum + c.co2e, 0)}));
                let b = model.map(g => ({buildingElement : constructionGroupList.find(it => it.groupId == g.oid).buildingElement , co2e : g.constructions.reduce((sum, c) => sum + c.co2e, 0)}));

                first = buildingElements.map(e => a.filter(i => i.buildingElement == e).reduce((sum, c) => sum + c.co2e, 0))
                second = buildingElements.map(e => b.filter(i => i.buildingElement == e).reduce((sum, c) => sum + c.co2e, 0))

                firstEnergy = firstEnergy.reduce((sum, x) => sum + x, 0 )
                secondEnergy = secondEnergy.reduce((sum, x) => sum + x, 0 )

                first.push(firstEnergy)
                second.push(secondEnergy)
                labels = chartLabels[STATUS2]
                break;
            case STATUS3 :
                first = [first.reduce((sum, x) => sum + x, 0 ) + firstEnergy.reduce((sum, x) => sum + x, 0 )]
                second = [second.reduce((sum, x) => sum + x, 0 ) + secondEnergy.reduce((sum, x) => sum + x, 0 )]
                labels = chartLabels[STATUS3]
                break;
            default:
                // if missconfigured
                first = first.concat(firstEnergy)
                second = second.concat(secondEnergy)
                labels = chartLabels[STATUS1]
                break;
        }

        //Label chart - every word on a line to show all the words
        labels = labels.map(it => it.split(" "))
        labels = labels.map(it => {  if(it.length == 3){
            it[0] = it[0] + " " + it[1];
            it[1] = it[2];
            it.splice(2,1);
        }
            return it

        });

        return {datasets: [first, second], labels: labels}

    };

    function getCostChartData(status) {
        let first = [];
        let second = [];
        $(".headingTotalCost").each(function () {
            first.push(parseFloat($(this).attr("data-roundedtotaloriginal")));
            second.push(parseFloat($(this).attr("data-roundedtotal")));
        });

        let labels;

        const STATUS1 = "${byConstructionGroup}";
        const STATUS2 = "${byBuildingElement}";
        const STATUS3 = "${byTotal}";

        switch(status){
            case STATUS1 :
                labels = chartLabels[STATUS1];
                break;
            case STATUS2 :
                let a = {};
                let b = {};

                $(".headingTotalCost").each(function () {
                    var element = $(this).attr("data-buildingElement");
                    var originalAmount = parseFloat($(this).attr("data-roundedtotaloriginal"));
                    var amount = parseFloat($(this).attr("data-roundedtotal"));
                    var existingOriginal = parseFloat(a[element]);
                    if (existingOriginal && $.isNumeric(existingOriginal)) {
                        existingOriginal += originalAmount;
                    } else {
                        existingOriginal = originalAmount;
                    }
                    a[element] = existingOriginal;

                    var existing = parseFloat(b[element]);
                    if (existing && $.isNumeric(existing)) {
                        existing += amount;
                    } else {
                        existing = amount;
                    }
                    b[element] = existing;
                });
                first = Object.values(a);
                second = Object.values(b);
                labels = chartLabels[STATUS2];
                break;
            case STATUS3 :
                first = [first.reduce((sum, x) => sum + x, 0 )];
                second = [second.reduce((sum, x) => sum + x, 0 )];
                labels = chartLabels[STATUS3];
                break;
            default :
                labels = chartLabels[STATUS1];
                break;
        }
        //Label chart - every word on a line to show all the words
        labels = labels.map(it => it.split(" "))
        labels = labels.map(it => {  if(it.length == 3){
            it[0] = it[0] + " " + it[1];
            it[1] = it[2];
            it.splice(2,1);
        }
            return it

        });

        return {datasets: [first, second], labels: labels}
    }


    function reGroupChart(status) {
        if(status){
            var groupingList = $("a[name = 'groupLogic']");
            $(groupingList).removeClass("selected");
            var selectedItemId = "#"+status;
            $(selectedItemId).addClass("selected");



            statusChart = status;
            updateChart();
            <g:if test="${lccIndicator}">
            updateCostChart();
            </g:if>

         }
     }

     //************************************************************************************************************

     function disableElementFor(querySelector, mills){

         let element = $(querySelector)
         element.css( 'pointer-events', 'none' );
         //element.addClass("disabled")
         setTimeout(() => {
             element.css( 'pointer-events', 'auto' );
             //element.removeClass("disabled");
             },
             mills);

     }

     //******************************************************************************************************************
     //Alternative Scenario
     //******************************************************************************************************************
     var alternativeScenarioJson = ${alternativeScenarioJson ?: 'null'}

    runAlternativeScenario(alternativeScenarioJson)

    function runAlternativeScenario(scenarioJson){

        if(scenarioJson){
            let energyScenario = scenarioJson.energyScenario
            let materialType = scenarioJson.materialType

            setDefaultType(materialType, energyScenario, true)
        }


    }


    //******************************************************************************************************************
    //LOad comment from modal to localconstruction
    //******************************************************************************************************************
    function  loadComment(construction, isModal){

        const prefixId = "groupComment"

        if(construction){
            const commentId = "comment" + construction.constructionId
            let mainComment = $("#" + commentId).val()
            if(mainComment)  construction.comment = mainComment
            if(isModal && construction.constituents){

                construction.constituents.forEach(c => {
                    let comment = $("#" + prefixId + c.groupId).val()
                    if(comment) c.comment = comment
                })
            }
        }
    }

    function loadConstructionComment(constructionId) {

        if(constructionId){
            let construction = findConstruction(model, constructionId);
            if(construction) loadComment(construction, false)
        }

    }


</script>

</body>
</html>



