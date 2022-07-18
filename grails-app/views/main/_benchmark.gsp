<div class="container section" id="benchmarkSection" style="border: 1px solid #d8d8d8; margin-bottom: 10px; max-width: 1156px !important; padding-left: 10px; padding-bottom: 10px;">
   <%-- intentionally hidden, bring back in patch after release!. --%>
    <g:if test="${benchmarkName}"><h3 style="padding-top: 20px; padding-left: 35px;">${benchmarkName}</h3></g:if>
    <g:if test="${indicators}">
        <div id="graphContainer">
            <g:each in="${indicators}" var="indicator" status="indicatorIndex">
            <div class="benchmarkBody${indicatorIndex > 0 ? ' notFirst' : ''}" id="${indicator.indicatorId}">
                <div class="benchmarkIndicator" style="float: left;">
                    <div style="float: right; padding-right: 20px; padding-left: 40px;">
                        <span style="padding-right: 10px;"><g:message code="main.choose_unit" />:</span>
                        <select class="unitSelect smallSelect" data-indicatorId="${indicator.indicatorId}">
                            <optimi:renderDenominators entities="${childEntitiesByIndicator?.get(indicator.indicatorId)}" indicator="${indicator}" />
                        </select>
                        <%--
                        <button type="button" class="close_black" onclick="hideById('${indicator.indicatorId}'); $('#choose${indicator.indicatorId}').removeClass('active');">×</button>
                        --%>
                    </div>
                    <span style="margin-left: 30px;">
                        <span style="padding-right: 10px;"><g:message code="design.graph_choose" />:</span>
                        <select class="smallSelect indicatorSelect" id="indicatorSelect${indicator.indicatorId}">
                            <g:each in="${indicators}" var="innerIndicator">
                                <option value="${innerIndicator.indicatorId}">${innerIndicator.localizedName}</option>
                            </g:each>
                        </select>
                    </span>
                </div>
                <div style="float: right; padding-right: 45px; padding-top: 10px;">
                    <div id="hideGraph"><strong><a href="javascript:;" onclick="$('#resulttable').removeClass('table-entities'); hideById('benchmarkSection');<%--hideById('hideGraph'); showById('showGraph');--%>">Hide graph</a></strong></div>
                    <%--
                    <div id="showGraph" style="display: none;"><strong><a href="javascript:;" onclick="$('#resulttable').addClass('table-entities'); showById('graphContainer'); hideById('showGraph'); showById('hideGraph');">Show graph</a></strong></div>
                    --%>
                </div>
                <div class="sectionbody" style="padding-bottom: 0; width: 99%; margin: 0 auto;" id="${indicator.indicatorId}content">
                    <g:if test="${'design'.equals(indicator.indicatorUse)}">
                        <g:render template="/portfolio/designByIndicator" model="[indicator: indicator, graphModel: graphModelByIndicator?.get(indicator.indicatorId),
                                                                                  maxScore: maxScoreByIndicator?.get(indicator.indicatorId), mainPage: true]" />
                    </g:if>
                    <g:else>
                        <g:render template="/portfolio/operatingByIndicator" model="[indicator: indicator, graphModel: graphModelByIndicator?.get(indicator.indicatorId),
                                                                                     maxScore: maxScoreByIndicator?.get(indicator.indicatorId), operatingPeriods: chosenPeriodsByIndicators?.get(indicator.indicatorId), mainPage: true]" />
                    </g:else>
                </div>
            </div>
        </g:each>
        </div>
    </g:if>
</div>

