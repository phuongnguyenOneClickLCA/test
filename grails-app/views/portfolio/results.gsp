<%-- Copyright (c) 2012 by Bionova Oy --%>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <meta name="format-detection" content="telephone=no"/>
    <g:if test="${indicator?.report?.appliedStyleSheet}">
        <asset:stylesheet src="${indicator?.report?.appliedStyleSheet}"/>
    </g:if>
    <g:set var="indicatorService" bean="indicatorService"/>
</head>
<body>

<g:render template="/entity/results_info" model="[portfolioEntity: portfolioEntity, operatingPeriod: operatingPeriod, portfolio: portfolio, indicator: indicator]"/>

<div class="container section">
    <div class="sectionheader">
        <h2>${indicatorService.getLocalizedName(indicator)}</h2>
    </div>

    <g:set var="date" value="${formatDate(date: new Date(), format: 'dd.MM.yyyy HH:mm')}" />

    <div class="sectionbody"${indicator?.report?.backgroundImage ? ' style="background-image: url(\'' + indicator.report.backgroundImage +'\');"' : ''}>
            <h2><span class="hide show-on-print">${indicatorService.getLocalizedResultsHeadline(indicator)}</span></h2>
            <p>${indicatorService.getLocalizedPurpose(indicator)}</p>

            <div class="row-fluid">
                <div class="span12">
                    <table class="table table-striped table-condensed table-cellvaligntop nowrap">
                        <tbody>
                              <optimi:renderPortFolioResults indicator="${indicator}" portfolio="${portfolio}" operatingPeriod="${operatingPeriod}" />
                        </tbody>
                    </table>
                </div>
            </div>
        <g:renderPortfolioSourceListing operatingPeriod="${operatingPeriod}" indicator="${indicator}" portfolio="${portfolio}" />

    </div>
</div>
</body>
</html>



