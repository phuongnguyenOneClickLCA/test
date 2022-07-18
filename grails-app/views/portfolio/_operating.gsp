<div class="container section">
  <g:if test="${indicators && operatingPeriods}">
      <div class="screenheader">
            <h1><g:message code="portfolio.choose_indicators" /></h1>
            <div class="portfolionav">
                <ul>
                    <g:each in="${indicators}" var="indicator" status="index">
                        <g:set var="showTable" value="${!graphModelByIndicator?.get(indicator.indicatorId)||graphModelByIndicator?.get(indicator.indicatorId)?.size() <= 1}"></g:set>
                        <li class="portfoliolegend" id="choose${indicator.indicatorId}"><input type="checkbox" ${index == 0 ? 'checked="checked"' : ''} onchange="showHidePortfolioIndicator('${indicator.indicatorId}',  '${showTable ? true : false}');<g:if test="${indicator.nonNumericResult}">$('#table${indicator.indicatorId}').show();
                       </g:if>"/> ${indicator.localizedShortName ? indicator.localizedShortName : indicator.localizedName}</li>
                    </g:each>
                </ul>
            </div>

            <div class="pull-right" style="display: inline-block;">
              <div class="btn-group">
                  <a href="#" data-toggle="dropdown" class="btn btn-primary dropdown-toggle"><g:message code="portfolio.send_reminder" /><span class="caret"></span></a>
                  <ul class="dropdown-menu">
                      <g:each in="${operatingPeriods}" var="operatingPeriod">
                          <li>
                              <opt:link action="sendReminder" id="${portfolio?.id}" params="[period: operatingPeriod]">${operatingPeriod}</opt:link>
                          </li>
                      </g:each>
                  </ul>
              </div>
            </div>
      </div>

      <g:each in="${indicators}" var="indicator" status="indicatorIndex">
          <g:set var="ruleToDisplay" value="${indicator?.getResolveCalculationRules(entity)?.find({it.calculationRuleId.equalsIgnoreCase(indicator.displayResult)})}" />
          <g:set var="calculationRuleLocalizedUnit" value="${applicationContext.calculationRuleService.getLocalizedUnit(ruleToDisplay, indicator)}" />
          <g:set var="isLastDivTorender" value="${indicatorIndex + 1 == indicators?.size() ? true : false}" />
          <div class="portfoliobody${indicatorIndex > 0 ? ' notFirst' : ''}" id="${indicator.indicatorId}">
            <div class="sectionheader">
                <div class="sectioncontrols pull-right">
                    <g:set var="showPortfolioSourceListings" value="${operatingPeriods && indicator.portfolioSourceListing && !indicator.hideSourceListing && !portfolio?.showAverageInsteadOfTotal}"/>
                    <select class="unitSelect" data-indicatorId="${indicator.indicatorId}" data-portfolioId="${portfolio.id}">
                        <optimi:renderDenominators entities="${allChildEntities}" indicator="${indicator}" showPortfolioSourceListings="${showPortfolioSourceListings}"/>
                    </select>
                    <button type="button" class="close_black" onclick="hideById('${indicator.indicatorId}', '#choose${indicator.indicatorId}');">×</button>
                </div>
                <h2>${indicator.localizedName}${calculationRuleLocalizedUnit ? " - ${calculationRuleLocalizedUnit}" : ""}<span id="${indicator.indicatorId}portolioTableHeadingDenomName"></span></h2>
            </div>
            <div class="sectionbody" style="padding-bottom: 0; width: 99%; margin: 0 auto;" id="${indicator.indicatorId}content">
                <g:render template="/portfolio/operatingByIndicator" model="[indicator: indicator, entities: entities, entityPeriods: entityPeriods,isLastDivTorender:isLastDivTorender,noHideDiv:false,
                                                                    entitiesByOperatingPeriod: entitiesByOperatingPeriod, showAll: showAllByIndicators?.get(indicator),indicatorIndex:indicatorIndex,indicators:indicators,
                                                                    operatingPeriods: operatingPeriods, graphModel: graphModelByIndicator?.get(indicator.indicatorId),
                                                                    exportLicensed: exportLicensed, maxScore: maxScoreByIndicator?.get(indicator.indicatorId), calculationRuleLocalizedUnit: calculationRuleLocalizedUnit,
                                                                    showThresholds: portfolio?.showAverageInsteadOfTotal, topScore: topScoreByIndicator?.get(indicator.indicatorId),
                                                                    minScore: minScoreByIndicator?.get(indicator.indicatorId), user:user, showPortfolioSourceListings:showPortfolioSourceListings]" />
            </div>
              <g:if test="${showPortfolioSourceListings}">
                  <div class="sourceListingPortfolioWrapper${indicator.indicatorId}">
                      <g:each in="${operatingPeriods}" var="period">
                          <g:renderPortfolioSourceListing operatingPeriod="${period}" indicator="${indicator}" portfolio="${portfolio}" />

                      </g:each>
                  </div>
              </g:if>
          </div>

      </g:each>
  </g:if>
</div>

