<div class="container section">
    <g:if test="${indicators && entityDesigns}">
        <%
            def calculationRuleService = grailsApplication.mainContext.getBean("calculationRuleService")
        %>
        <div class="screenheader">
            <h1><g:message code="portfolio.choose_indicators" /></h1>
            <div class="portfolionav">
                <ul>
                    <g:each in="${indicators}" var="indicator" status="index">
                        <g:set var="showTable" value="${!graphModelByIndicator?.get(indicator.indicatorId)||graphModelByIndicator?.get(indicator.indicatorId).size() <= 1}"></g:set>
                        <li class="portfoliolegend" id="choose${indicator.indicatorId}"><input type="checkbox" ${index == 0 ? 'checked="checked"' : ''} onchange="showHidePortfolioIndicator('${indicator.indicatorId}', '${showTable ? true : false}');"/> ${indicator.localizedShortName ? indicator.localizedShortName : indicator.localizedName}</li>
                    </g:each>
                </ul>
            </div>
        </div>

        <g:each in="${indicators}" var="indicator" status="indicatorIndex">
            <g:set var="ruleToDisplay" value="${indicator?.getResolveCalculationRules(entity)?.find({it.calculationRuleId.equalsIgnoreCase(indicator.displayResult)})}" />
            <g:set var="isLastDivTorender" value="${indicatorIndex +1 == indicators?.size() ? true : false}" />
            <div class="portfoliobody${indicatorIndex > 0 ? ' notFirst' : ''}" id="${indicator.indicatorId}">
                <div class="sectionheader">
                    <div class="sectioncontrols pull-right">
                        <select class="unitSelect" data-indicatorId="${indicator.indicatorId}">
                            <optimi:renderDenominators indicator="${indicator}" entities="${allDesigns}"/>
                        </select>
                        <button type="button" class="close_black" onclick="hideById('${indicator.indicatorId}', '#choose${indicator.indicatorId}');">×</button>
                    </div>
                    <h2>${indicator.localizedName}${ruleToDisplay ? " - ${calculationRuleService.getLocalizedUnit(ruleToDisplay, indicator)}" : ""}<span id="${indicator.indicatorId}portolioTableHeadingDenomName"></span></h2>
                </div>
                <div class="sectionbody" style="padding-bottom: 0; width: 99%; margin: 0 auto;" id="${indicator.indicatorId}content">
                    <g:render template="designByIndicator" model="[indicator: indicator, entityDesigns: entityDesigns, showAllLink: showAllLink,indicatorIndex:indicatorIndex,indicators:indicators,
                                                                   graphModel: graphModelByIndicator?.get(indicator.indicatorId),isLastDivTorender:isLastDivTorender,noHideDiv:false,
                                                                   maxScore: maxScoreByIndicator?.get(indicator.indicatorId), sysAdmin: sysAdmin, user:user, ruleToDisplay:ruleToDisplay]" />
                </div>
            </div>
        </g:each>
    </g:if>
</div>


