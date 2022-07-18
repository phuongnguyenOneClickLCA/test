<%@ page import="static com.bionova.optimi.core.service.SimulationToolService.conversionUnit as conversionUnit" %>
<%@ page import="static com.bionova.optimi.core.service.SimulationToolService.conversionUnitFactor as conversionUnitFactor" %>
<g:set var="simulationToolService" bean="simulationToolService"/>
<style>

    <%-- ???? --%>
    .carbonTable td {
        vertical-align: bottom;
    }

    .tr-background th{
        background: lightgrey;
        text-align: left;
        padding: 2px;
    }

    .buildingTypeTable td {
        <%--text-align: right;--%>
    }

</style>


<script>

    // Input Field Numeric Class Validation
    $('.numeric').on('input propertychange', numericInputValidation);
    <g:if test="${!onUploadTemplate}">
        $("#totalNetArea").text("${isUserGiven.get("totalNetArea") ?: simulationToolService.roundToNumber(totalNetArea, false, true)}");
    </g:if>


    $("#heatedArea").val("${simulationToolService.roundToNumber(heatedArea * conversionUnitFactor(unitSystem, "m2"), false, true)}");
    if(!$("#energyScenario").val()){
        $("#heatedAreaSection").addClass("hidden")
    }

    function createInput(element, id, value, allowZero) {
        var input = document.createElement("INPUT");
        input.setAttribute("type", "text")
        if (allowZero) {
            input.setAttribute("class", "numeric help-on-change allowZero")
        } else {
            input.setAttribute("class", "numeric help-on-change")
        }
        input.setAttribute("id", id)
        input.setAttribute("name", id)
        input.setAttribute("value", value)

        if ("depthBuilding" === id) {
            $('.depthWarning').remove();
        }

        $(element).empty().parent().append(input)
        $('.numeric').on('input propertychange', numericInputValidation);
        $(".help-on-change").on("keyup onchange", function(){
            $(".onchangeUpdateTip").removeClass("hidden")
        })
        if(Array.isArray(persistInputs)){
            if(!persistInputs.find(i => i == id)){
                persistInputs.push(id)
            }
        }

    }

</script>

<%-- Column Building dimensions of the page --%>
<g:if test="${!onUploadTemplate}">
    <div class="scopeSection">

        <%--
        <g:if test="${buildingTypeImageClass}">
            <div class="image-cd">
                <div id="entityImage" class="entityshow-pieInfoImage" style="text-align: center">
                    <i id="buildingTypeImage" class="entitytype ${buildingTypeImageClass}" style="text-align: center"></i>
                </div>
            </div>
        </g:if>
        --%>
        <div class="table-floor" >
            <table class="buildingTypeTable">
                <tr class="hideBuildingDimension">
                    <td><label class="control-label"><g:message code="simulationTool.height"/>
                        <g:set var="localizedInfo" value="${g.message(code: "simulationTool.height.info")}" />
                        <g:if test="${!"simulationTool.height.info".equals(localizedInfo.toString())}">
                            <a href="#" class="cdInfoPopover" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${g.message(code: "simulationTool.height.info")}">
                                <i class="icon-question-sign"></i>
                            </a>
                        </g:if>
                    </label></td>
                    <td>
                        <g:set var="tempValue" value="${isUserGiven.get("heightBuilding") ?: simulationToolService.roundToNumber(heightBuilding * conversionUnitFactor(unitSystem, "m"), false, true)}"/>
                        <a class="heightBuilding" onclick="createInput(this, 'heightBuilding', ${tempValue})">
                            ${tempValue}
                        </a>
                    </td>
                    <td><label class="control-label">${conversionUnit(unitSystem, "m")}</label></td>
                </tr>
                <tr class="hideBuildingDimension">
                    <td><label class="control-label"><g:message code="simulationTool.width"/>
                        <g:set var="localizedInfo" value="${g.message(code: "simulationTool.width.info")}" />
                        <g:if test="${!"simulationTool.width.info".equals(localizedInfo.toString())}">
                            <a href="#" class="cdInfoPopover" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${g.message(code: "simulationTool.width.info")}">
                                <i class="icon-question-sign"></i>
                            </a>
                        </g:if>
                    </label></td>
                    <td>
                        <g:set var="tempValue" value="${isUserGiven.get("widthBuinding") ?: simulationToolService.roundToNumber(widthBuinding * conversionUnitFactor(unitSystem, "m"), false, true)}"/>
                        <g:set var="depth" value="${simulationToolService.roundToNumber(depthBuilding * conversionUnitFactor(unitSystem, "m"), false, true)}"/>
                        <g:if test="${depth > tempValue}">
                            <i style='color: orange; font-size: 12px;' class='fa fa-exclamation-triangle widthWarning' data-content='${g.message(code: 'simulationTool.width.warning')}'></i>
                        </g:if>
                        <a class="widthBuinding" onclick="createInput(this, 'widthBuinding', ${tempValue})">
                            ${tempValue}
                        </a>
                    <td><label class="control-label">${conversionUnit(unitSystem, "m")}</label></td>
                </tr>
                <tr class="hideBuildingDimension">
                    <td><label class="control-label"><g:message code="simulationTool.depth" />
                    <g:set var="localizedInfo" value="${g.message(code: "simulationTool.depth.info")}" />
                    <g:if test="${!"simulationTool.depth.info".equals(localizedInfo.toString())}">
                        <a href="#" class="cdInfoPopover" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${g.message(code: "simulationTool.depth.info")}">
                            <i class="icon-question-sign"></i>
                        </a>
                    </g:if>
                    </label></td>
                    <td>
                        <g:set var="tempValue" value="${isUserGiven.get("depthBuilding") ?: simulationToolService.roundToNumber(depthBuilding * conversionUnitFactor(unitSystem, "m"), false, true)}"/>
                        <a class="depthBuilding" onclick="createInput(this, 'depthBuilding', ${tempValue})">
                            ${tempValue}
                        </a>
                    </td>
                    <td><label class="control-label">${conversionUnit(unitSystem, "m")}</label></td>
                </tr>
                <tr>
                    <td><label class="control-label"><g:message code="simulationTool.internalFloorHeight" />
                    <g:set var="localizedInfo" value="${g.message(code: "simulationTool.internalFloorHeight.info")}" />
                    <g:if test="${!"simulationTool.internalFloorHeight.info".equals(localizedInfo.toString())}">
                        <a href="#" class="cdInfoPopover" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${g.message(code: "simulationTool.internalFloorHeight.info")}">
                            <i class="icon-question-sign"></i>
                        </a>
                    </g:if>
                    </label></td>
                    <td>
                        <g:set var="tempValue" value="${isUserGiven.get("internalFloorHeight") ?: simulationToolService.roundToNumber(internalFloorHeight * conversionUnitFactor(unitSystem, "m"), false, true)}"/>
                        <a class="internalFloorHeight" onclick="createInput(this, 'internalFloorHeight', ${tempValue})">
                            ${tempValue}
                        </a>
                    </td>
                    <td><label class="control-label">${conversionUnit(unitSystem, "m")}</label></td>
                </tr>
                <tr>
                    <td><label class="control-label"><g:message code="simulationTool.maxSpanWithoutColumns_m" />
                    <g:set var="localizedInfo" value="${g.message(code: "simulationTool.maxSpanWithoutColumns_m.info")}" />
                    <g:if test="${!"simulationTool.maxSpanWithoutColumns_m.info".equals(localizedInfo.toString())}">
                        <a href="#" class="cdInfoPopover" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${g.message(code: "simulationTool.maxSpanWithoutColumns_m.info")}">
                            <i class="icon-question-sign"></i>
                        </a>
                    </g:if>
                    </label></td>
                    <td>
                        <g:set var="tempValue" value="${isUserGiven.get("maxSpanWithoutColumns_m") ?: simulationToolService.roundToNumber(maxSpanWithoutColumns_m * conversionUnitFactor(unitSystem, "m"), false, true)}"/>
                        <a class="maxSpanWithoutColumns_m" onclick="createInput(this, 'maxSpanWithoutColumns_m', ${tempValue})">
                            ${tempValue}
                        </a>
                    </td>
                    <td><label class="control-label">${conversionUnit(unitSystem, "m")}</label></td>
                </tr>
                <tr>
                    <td><label class="control-label"><g:message code="simulationTool.loadBearingShare" />
                    <g:set var="localizedInfo" value="${g.message(code: "simulationTool.loadBearingShare.info")}" />
                    <g:if test="${!"simulationTool.loadBearingShare.info".equals(localizedInfo.toString())}">
                        <a href="#" class="cdInfoPopover" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${g.message(code: "simulationTool.loadBearingShare.info")}">
                            <i class="icon-question-sign"></i>
                        </a>
                    </g:if>
                    </label></td>
                    <td>
                        <g:set var="tempValue" value="${isUserGiven.get("loadBearingShare") ?: simulationToolService.roundToNumber(loadBearingShare * 100, false, true)}"/>
                        <a class="loadBearingShare" onclick="createInput(this, 'loadBearingShare', ${tempValue})">
                            ${tempValue}
                        </a>
                    </td>
                    <td>%</td>
                </tr>
                <tr>
                    <td><label class="control-label"><g:message code="simulationTool.numberOfStaircases" />
                    <g:set var="localizedInfo" value="${g.message(code: "simulationTool.numberOfStaircases.info")}" />
                    <g:if test="${!"simulationTool.numberOfStaircases.info".equals(localizedInfo.toString())}">
                        <a href="#" class="cdInfoPopover" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${g.message(code: "simulationTool.numberOfStaircases.info")}">
                            <i class="icon-question-sign"></i>
                        </a>
                    </g:if>
                    </label></td>
                    <td>
                        <g:set var="tempValue" value="${numberStaircases?.toInteger()}"/>
                        <a class="numberStaircases" onclick="createInput(this, 'numberStaircases', ${tempValue})">
                            ${tempValue}
                        </a>
                    </td>
                </tr>
                <tr>
                    <td><label class="control-label"><g:message code="simulationTool.totalFloors" />
                    <g:set var="localizedInfo" value="${g.message(code: "simulationTool.totalFloors.info")}" />
                    <g:if test="${!"simulationTool.totalFloors.info".equals(localizedInfo.toString())}">
                        <a href="#" class="cdInfoPopover" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${g.message(code: "simulationTool.totalFloors.info")}">
                            <i class="icon-question-sign"></i>
                        </a>
                    </g:if>
                    </label></td>
                    <td>
                        <g:set var="tempValue" value="${totalFloors?.toInteger()}"/>
                        <a class="totalFloors" onclick="createInput(this, 'totalFloors', ${tempValue})">
                            ${tempValue}
                        </a>
                </tr>
                <tr>
                    <td><label class="control-label">
                        <g:message code="simulationTool.shapeEfficiencyFactor" />
                        <a href="#" class="cdInfoPopover" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${g.message(code: "shapeEfficiencyFactor.Qmark")}">
                            <i class="icon-question-sign"></i>
                        </a>
                    </label></td>
                    <td>
                        <g:set var="tempValue" value="${isUserGiven.get("shapeEfficiencyFactor") ?: simulationToolService.roundToNumber(shapeEfficiencyFactor, false, true)}"/>
                        <a class="shapeEfficiencyFactor" onclick="createInput(this, 'shapeEfficiencyFactor', ${tempValue})">
                            ${tempValue}
                        </a>
                    </td>
                </tr>
                <tr>
                    <td><label class="control-label"><g:message code="simulationTool.totalArea" />
                    <g:set var="localizedInfo" value="${g.message(code: "simulationTool.totalArea.info")}" />
                    <g:if test="${!"simulationTool.totalArea.info".equals(localizedInfo.toString())}">
                        <a href="#" class="cdInfoPopover" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${g.message(code: "simulationTool.totalArea.info")}">
                            <i class="icon-question-sign"></i>
                        </a>
                    </g:if>
                    </label></td>
                    <td>
                        <g:set var="tempValue" value="${isUserGiven.get("totalNetArea") ?: simulationToolService.roundToNumber(totalNetArea, false, true)}"/>
                        <a class="totalNetArea" data-quantity="${tempValue}" id="totalNetAreaLink" onclick="createInput(this, 'totalNetArea', ${tempValue})">${tempValue}</a>
                    </td>
                    <td><label class="control-label">${conversionUnit(unitSystem, "m2")}</label></td>
                </tr>
                <tr id="heatedAreaSection">
                    <td><label class="control-label"><g:message code="simulationTool.heatedArea" />
                    <g:set var="localizedInfo" value="${g.message(code: "simulationTool.heatedArea.info")}" />
                    <g:if test="${!"simulationTool.heatedArea.info".equals(localizedInfo.toString())}">
                        <a href="#" class="cdInfoPopover" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${g.message(code: "simulationTool.heatedArea.info")}">
                            <i class="icon-question-sign"></i>
                        </a>
                    </g:if>
                    </label></td>
                    <td><input class="numeric" type="text" id="heatedArea" name="heatedArea"/></td>
                    <td><label class="control-label">${conversionUnit(unitSystem, "m2")}</label></td>
                </tr>

                <tr id="showMoreBuildingDimensions" onclick="showMoreBuildingDimensions(this)">
                    <td><i style="display:inline-block; margin-bottom: 15px; cursor:pointer; font-size:15px;" class="fa fa-plus fa-1x primary btnRemove"></i>
                        <a  href="javascript:" class="expanded primary btnRemove" style="display:inline-block; margin-left:5px; font-weight:bold; cursor:pointer;" onclick=""> More parameters</a>
                    </td>
                </tr>
                <tr class="hidden toHide">
                    <td><label class="control-label"><g:message code="simulationTool.floorThickness_m" />
                        <a href="#" class="cdInfoPopover" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${g.message(code: "simulationTool.floorThickness_m.info")}">
                            <i class="icon-question-sign"></i>
                        </a>
                    </label></td>
                    <td>
                        <g:set var="tempValue" value="${isUserGiven.get("floorThickness_m") ?: simulationToolService.roundToNumber(floorThickness_m * conversionUnitFactor(unitSystem, "m"), false, true)}"/>
                        <a class="floorThickness_m" data-quantity="${tempValue}" onclick="createInput(this, 'floorThickness_m', ${tempValue}, true)">${tempValue}</a>
                    </td>
                    <td><label class="control-label">${conversionUnit(unitSystem, "m")}</label></td>
                </tr>

                <tr class="hidden toHide">
                    <td><label class="control-label"><g:message code="simulationTool.envelopeThickness_m" />
                        <a href="#" class="cdInfoPopover" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${g.message(code: "simulationTool.envelopeThickness_m.info")}">
                            <i class="icon-question-sign"></i>
                        </a>
                    </label></td>
                    <td>
                        <g:set var="tempValue" value="${isUserGiven.get("envelopeThickness_m") ?: simulationToolService.roundToNumber(envelopeThickness_m * conversionUnitFactor(unitSystem, "m"), false, true)}"/>
                        <a class="envelopeThickness_m" data-quantity="${tempValue}" onclick="createInput(this, 'envelopeThickness_m', ${tempValue}, true)">${tempValue}</a>
                    </td>
                    <td><label class="control-label">${conversionUnit(unitSystem, "m")}</label></td>
                </tr>

                <tr class="hidden toHide">
                    <td><label class="control-label"><g:message code="simulationTool.roofShapeEfficiencyFactor" />
                        <a href="#" class="cdInfoPopover" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${g.message(code: "simulationTool.roofShapeEfficiencyFactor.info")}">
                            <i class="icon-question-sign"></i>
                        </a>
                    </label></td>
                    <td>
                        <g:set var="tempValue" value="${isUserGiven.get("roofShapeEfficiencyFactor") ?: simulationToolService.roundToNumber(roofShapeEfficiencyFactor, false, true)}"/>
                        <a class="roofShapeEfficiencyFactor" data-quantity="${tempValue}" onclick="createInput(this, 'roofShapeEfficiencyFactor', ${tempValue})">${tempValue}</a>
                    </td>
                    <td><label class="control-label">%</label></td>
                </tr>

                <tr class="hidden toHide">
                    <td><label class="control-label"><g:message code="simulationTool.lenghtDepthRatio" />
                        <a href="#" class="cdInfoPopover" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${g.message(code: "simulationTool.lenghtDepthRatio.info")}">
                            <i class="icon-question-sign"></i>
                        </a>
                    </label></td>
                    <td>${simulationToolService.roundToNumber(lenghtDepthRatio, false, true)}</td>
                </tr>

                <tr class="hidden toHide">
                    <td><label class="control-label"><g:message code="simulationTool.maxBuildingDepth_m" />
                        <a href="#" class="cdInfoPopover" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${g.message(code: "simulationTool.maxBuildingDepth_m.info")}">
                            <i class="icon-question-sign"></i>
                        </a>
                    </label></td>
                    <td>${simulationToolService.roundToNumber(maxBuildingDepth_m * conversionUnitFactor(unitSystem, "m"), false, true)}</td>
                    <td><label class="control-label">${conversionUnit(unitSystem, "m")}</label></td>
                </tr>

                <tr class="hidden toHide">
                    <td><label class="control-label"><g:message code="simulationTool.maxStairCaseDistance_m" />
                        <a href="#" class="cdInfoPopover" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${g.message(code: "simulationTool.maxStairCaseDistance_m.info")}">
                            <i class="icon-question-sign"></i>
                        </a>
                    </label></td>
                    <td>${simulationToolService.roundToNumber(maxStairCaseDistance_m * conversionUnitFactor(unitSystem, "m"), false, true)}</td>
                    <td><label class="control-label">${conversionUnit(unitSystem, "m")}</label></td>
                </tr>

                <tr class="hidden toHide">
                    <td><label class="control-label"><g:message code="simulationTool.doorsToGroundfFloorAreaRatio" />
                        <a href="#" class="cdInfoPopover" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${g.message(code: "simulationTool.doorsToGroundfFloorAreaRatio.info")}">
                            <i class="icon-question-sign"></i>
                        </a>
                    </label></td>
                    <td>${simulationToolService.roundToNumber(doorsToGroundfFloorAreaRatio, false, true)}</td>
                </tr>

                <tr class="hidden toHide">
                    <td><label class="control-label"><g:message code="simulationTool.windowsToAboveGroundAreaRatio" />
                        <a href="#" class="cdInfoPopover" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${g.message(code: "simulationTool.windowsToAboveGroundAreaRatio.info")}">
                            <i class="icon-question-sign"></i>
                        </a>
                    </label></td>
                    <td>${simulationToolService.roundToNumber(windowsToAboveGroundAreaRatio, false, true)}</td>
                </tr>

                <tr class="hidden toHide">
                    <td><label class="control-label"><g:message code="simulationTool.windowsMaximumOfExternalWalls" />
                        <a href="#" class="cdInfoPopover" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${g.message(code: "simulationTool.windowsMaximumOfExternalWalls.info")}">
                            <i class="icon-question-sign"></i>
                        </a>
                    </label></td>
                    <td>${simulationToolService.roundToNumber(windowsMaximumOfExternalWalls, false, true)}</td>
                </tr>

                <tr class="hidden toHide">
                    <td><label class="control-label"><g:message code="simulationTool.balconiesToAboveGroundAreaRatio" />
                        <a href="#" class="cdInfoPopover" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${g.message(code: "simulationTool.balconiesToAboveGroundAreaRatio.info")}">
                            <i class="icon-question-sign"></i>
                        </a>
                    </label></td>
                    <td>${simulationToolService.roundToNumber(balconiesToAboveGroundAreaRatio, false, true)}</td>
                </tr>

                <tr class="hidden toHide">
                    <td><label class="control-label"><g:message code="simulationTool.internalWallsToExternalWallsRatio" />
                        <a href="#" class="cdInfoPopover" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${g.message(code: "simulationTool.internalWallsToExternalWallsRatio.info")}">
                            <i class="icon-question-sign"></i>
                        </a>
                    </label></td>
                    <td>${simulationToolService.roundToNumber(internalWallsToExternalWallsRatio, false, true)}</td>
                </tr>

                <tr class="hidden toHide">
                    <td><label class="control-label"><g:message code="simulationTool.externalPavedAreas" />
                        <a href="#" class="cdInfoPopover" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${g.message(code: "simulationTool.externalPavedAreas.info")}">
                            <i class="icon-question-sign"></i>
                        </a>
                    </label></td>
                    <td>${simulationToolService.roundToNumber(externalPavedAreasRatio, true, false)}</td>
                </tr>
            </table>
        </div>
    </div>
</g:if>
<g:else>
    <div class="scopeSection">
        <div class="table-floor" >
            <table class="buildingTypeTable">
              <tr id="heatedAreaSection">
                <td><label class="control-label"><g:message code="simulationTool.heatedArea" />
                    <g:set var="localizedInfo" value="${g.message(code: "simulationTool.heatedArea.info")}" />
                    <g:if test="${!"simulationTool.heatedArea.info".equals(localizedInfo.toString())}">
                        <a href="#" class="cdInfoPopover" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${g.message(code: "simulationTool.heatedArea.info")}">
                            <i class="icon-question-sign"></i>
                        </a>
                    </g:if>
                </label></td>
                <td><input class="numeric" type="text" id="heatedArea" name="heatedArea"/></td>
                <td><label class="control-label">${conversionUnit(unitSystem, "m2")}</label></td>
              </tr>
            </table>
        </div>
    </div>
</g:else>

<script>

    $(function () {
        $('.cdInfoPopover').popover({
            placement : 'top',
            template: '<div class="popover" style=" display: block; max-width: 350px;" ><div class="arrow"></div><div class="popover-content" style="border-style: solid; border-color: whitesmoke; border-width: 1px"></div></div>'
        });

        $('.widthWarning').popover({
            placement : 'top',
            template: '<div class="popover" style=" display: block; max-width: 350px;" ><div class="arrow"></div><div class="popover-content" style="border-style: solid; border-color: whitesmoke; border-width: 1px"></div></div>',
            trigger: 'hover',
            html: true,
            container: 'body'
        });
    })

    function showMoreBuildingDimensions(element){
        let toHide = $(element).siblings(".toHide")
        let i = $(element).find("i")

        if(toHide.hasClass("hidden")){
            toHide.removeClass("hidden")
            //$(i).attr('class','fa fa-minus')
            $(i).removeClass('fa fa-plus').addClass('fa fa-minus')
            moreParamsExpanded = true;

        }else {
            toHide.addClass("hidden")
            //$(i).attr('class','fa fa-plus')
            $(i).removeClass('fa fa-minus').addClass('fa fa-plus')
            moreParamsExpanded = false;
        }

    }

</script>


