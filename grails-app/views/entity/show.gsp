<%@ page expressionCodec="html" %>
<%@ page import="com.bionova.optimi.core.Constants; org.apache.commons.lang.StringEscapeUtils; com.bionova.optimi.core.domain.mongo.Indicator" trimDirectiveWhitespaces="true" %>
<%@ page import="static com.bionova.optimi.core.domain.mongo.FrameStatus.TAB.WORKFLOW as TAB_WORKFLOW" %>
<%@ page import="static com.bionova.optimi.core.domain.mongo.FrameStatus.TAB.HELP as TAB_HELP" %>
<g:set var="designPopupName" value="${"designPopupName"}"/>
<%
    def userService = grailsApplication.mainContext.getBean("userService")
    def indicatorService = grailsApplication.mainContext.getBean("indicatorService")
%>
<!DOCTYPE html>
<%-- MAIN SCREEN --%>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="format-detection" content="telephone=no"/>
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <g:if test="${entity?.showPortfolio}">
        <asset:stylesheet src="portfolio.css"/>
        <asset:javascript src="portfolio_chart.js"/>
        <asset:javascript src="stupidtable.min.js"/>
    </g:if>
    <asset:javascript src="designs.js"/>
    <asset:javascript src="portfolio_chart.js"/>
    <script>
        window.ChartV1 = Chart;
    </script>
    <asset:javascript src="chartJsV2.7.js"/>
    <asset:javascript src="highcharts.js"/>
    <asset:javascript src="highcharts-more.js"/>
    <asset:javascript src="no-data-to-display.js"/>
    <asset:javascript src="drilldown.js"/>
    <asset:javascript src="exporting.js"/>
    <asset:javascript src="export-data.js"/>
    <asset:javascript src="moment.js"/>
    <asset:javascript src="xlsx.full.min.js"/>
    <asset:javascript src="offline-exporting.js"/>
    <asset:javascript src="no-data-to-display.js"/>
    <asset:javascript src="exportxlsx.js"/>
    <asset:javascript src="hideAndRestoreFunctions.js"/>

    <script>
        window.ChartV2 = Chart;
        window.Chart = window.ChartV1;
    </script>
</head>

<body>
<div>
    <div id="mainContent">
        <g:set var="currentUser" value="${userService.getCurrentUser()}"/>
        <g:set var="pageRenderTime" value="${System.currentTimeMillis()}"/>
        <g:set var="channelFeature" value="${session?.channelFeature}"/>
        <g:set var="usableEntityClassesForUser" value="${userService.getUsableEntityClasses(currentUser)}"/>
        <g:set var="usableTrialEntityClassesForUser" value="${userService.getUsableTrialEntityClasses()}"/>

        <g:set var="entityHasBenchmark"
               value="${selectedDesignIndicators.findAll({ it?.isBenchmarkable(entityType) })}"/>
        <div class="overlay" id="loaderOverlay">
            <div class="overlay-content" id="loaderOverlayContent">
            </div>
        </div>

        <div class="container">
            <g:if test="${licensesToShow && !licensesToShow.isEmpty() && entity}">
                <g:set var="firstLicense" value="${licensesToShow.get(0)}"/>
                <g:if test="${!entity.public && !firstLicense.renewalStatus?.equalsIgnoreCase("autoRenewal") || (firstLicense.type?.equalsIgnoreCase("trial") || firstLicense.type?.equalsIgnoreCase("education"))}">
                    <opt:licenseExpiryMessage license="${firstLicense}" entity="${entity}"/>
                </g:if>
            </g:if>
            <g:if test="${newToolsAvailable && entity?.manageable}">
                <opt:newToolsAvailableMessage entityId="${entity?.id}" indicatorUse="${newToolsAvailable}"/>
            </g:if>

            <div class="screenheader"
                 style="border-bottom: 0 !important; margin-bottom: 5px !important; padding-right: 5px;">
                <div class="pull-right hide-on-print">
                    <g:if test="${entity?.modifiable}">
                        <g:if test="${jsonExportLicensed && !userService.getConsultant(currentUser)}">
                            <opt:link action="showjson" class="btn" id="${entity?.id}"><g:message
                                    code="entity.as_json"/></opt:link>
                        </g:if>
                        <g:if test="${reportLicensed && mainLevelReport}">
                            <g:if test="${mainLevelButtons}">
                                <g:each in="${mainLevelButtons}" var="mainLevelButton">
                                    <opt:link controller="entity" action="compoundReport"
                                              params="[entityId: entity?.id]"
                                              class="btn btn-primary">${mainLevelButton}</opt:link>
                                </g:each>
                            </g:if>
                            <g:else>
                                <opt:link controller="entity" action="compoundReport" params="[entityId: entity?.id]"
                                          class="btn btn-primary">Report</opt:link>
                            </g:else>
                        </g:if>
                        <g:if test="${entity?.manageable}">
                            <opt:link controller="entity" action="users" id="${entity?.id}" class="btn btn-primary"><i
                                    class="icon-user icon-white"></i> <g:message
                                    code="entity.users"/> (${entity?.users?.size()})</opt:link>
                        </g:if>

                    %{--                        More actions dropdown--}%
                        <div class="btn-group" style="display:inline-block"><a href="#" id="moreActions"
                                                                               data-toggle="dropdown"
                                                                               style="display:inline-block"
                                                                               class="dropdown-toggle btn btn-primary hide-on-print"><g:message
                                    code="entity.show.designs_more"/> <span class="caret"></span></a>
                            <ul class="dropdown-menu mainDrop">
                                <g:if test="${'portfolio' == entity?.entityClass}">
                                    <li>
                                        <opt:link controller="portfolio" action="form" id="${portfolio?.id}"><g:message
                                                code="entity.portfolio.modify"/></opt:link>
                                    </li>
                                </g:if>
                                <g:if test="${'portfolio' != entity?.entityClass && entity?.modifiable}">
                                    <li>
                                        <opt:link controller="entity" action="form"
                                                  params="[entityId: entity?.id]"><g:message code="modify"/></opt:link>
                                    </li>
                                </g:if>
                                <g:if test="${entity?.manageable}">
                                    <li>
                                    <g:if test="${'portfolio' == entity?.entityClass}">
                                        <opt:link controller="entity" action="form"
                                                  params="[entityId: entity?.id]"><g:message code="modify"/></opt:link>
                                        </li>
                                    </g:if>
                                    <g:if test="${'portfolio' != entity?.entityClass}">
                                        <g:if test="${entity?.archived}">
                                            <sec:ifAnyGranted roles="ROLE_SUPER_USER">
                                                <li><opt:link action="unarchive" id="${entity?.id}"
                                                              params="[superArchive: true]"
                                                              onclick="return modalConfirm(this);"
                                                              data-questionstr="Are you sure you want to unarchive ${entity?.name}?"
                                                              data-truestr="Unarchive"
                                                              data-falsestr="${message(code: 'cancel')}"
                                                              data-titlestr="Unarchiving the project"><i
                                                            class="fas fa-archive"></i> Unarchive (Super user)</opt:link>
                                                </li>
                                            </sec:ifAnyGranted>
                                        </g:if>
                                        <g:else>
                                            <li><opt:link action="archive" id="${entity?.id}"
                                                          onclick="return modalConfirm(this);"
                                                          data-questionstr="${message(code: 'entity.archive_question', args: [entity?.name])}"
                                                          data-truestr="${message(code: 'archive')}"
                                                          data-falsestr="${message(code: 'cancel')}"
                                                          data-titlestr="${message(code: 'entity.archive_confirm.header')}"><i
                                                        class="fas fa-archive"></i> <g:message
                                                        code="archive"/></opt:link></li>
                                            <sec:ifAnyGranted roles="ROLE_SUPER_USER">
                                                <li><opt:link action="archive" id="${entity?.id}"
                                                              params="[superArchive: true]"
                                                              onclick="return modalConfirm(this);"
                                                              data-questionstr="${message(code: 'entity.archive_question', args: [entity?.name])}"
                                                              data-truestr="${message(code: 'archive')}"
                                                              data-falsestr="${message(code: 'cancel')}"
                                                              data-titlestr="${message(code: 'entity.archive_confirm.header')}"><i
                                                            class="fas fa-archive"></i> Super <g:message
                                                            code="archive"/></opt:link></li>
                                            </sec:ifAnyGranted>
                                        </g:else>
                                    </g:if>
                                    <li><opt:link action="remove" id="${entity?.id}"
                                                  onclick="return modalConfirm(this);"
                                                  data-questionstr="${message(code: 'entity.delete_question', args: [entity?.name])}"
                                                  data-questionstr2="${message(code: 'entity.delete.step2Question')}"
                                                  data-truestr="${message(code: 'delete')}"
                                                  data-falsestr="${message(code: 'cancel')}"
                                                  data-titlestr="${message(code: 'entity.delete_confirm.header')}"><g:message
                                                code="delete"/>
                                    </opt:link></li>
                                </g:if>
                                <li><a href="javascript:" onClick="window.print();"><i
                                        class="icon-print"></i> <g:message code="print"/></a></li>
                            </ul>
                        </div>
                    </g:if>
                </div>
                %{--TODO: change to taglib --}%
                <opt:breadcrumbsNavBar parentEntity="${entity}" step="${0}"/>
                <h2 class="h2heading"><i class="fa fa-home"></i> ${entity?.name}</h2>
            </div>
        </div>

        <div class="container">
            <g:set var="showTasks"
                   value="${entity.entityClassResource?.showTasks && !(trialOrQuoteDesignIndicators || trialOrQuoteOperatingIndicators)}"/>
            <div class="container section collapsed">
                <div class="sectionheader">
                    <button class="pull-left sectionexpander" data-name="generalInformation"><i
                            class="icon icon-chevron-down expander"></i><i
                            class="icon icon-chevron-right collapser"></i></button>

                    <h2 class="h2expander" data-name="generalinfo" style="margin-left: 15px;"
%{--                        onclick="toggleContent()"--}%
                    ><g:message
                            code="general_information.heading"/></h2>
                </div>

                <div class="sectionbody" style="display: none;">
                    <div class="card">
                        <ul class="nav nav-tabs" role="tablist">
                            <li role="presentation" class="navInfo${"info".equals(activeTab) ? " active" : ""}">
                                <a id="tabInformation" data-targetBox="info" href="javascript:"
                                   class="generalInfoBtn"><g:message code="entity.show.information"/></a>
                            </li>
                            <li role="presentation" class="navInfo${"tasks".equals(activeTab) ? " active" : ""}">
                                <a id="tabTasks" data-targetBox="tasks" href="javascript:"
                                   class="generalInfoBtn"><g:message
                                        code="entity.show.tasks"/> ${tasks?.size() > 0 ? '(' + tasks.size() + ')' : ''}</a>
                            </li>
                            <li role="presentation" class="navInfo${"attachments".equals(activeTab) ? " active" : ""}">
                                <a id="tabAttachments" data-targetBox="attachments" href="javascript:"
                                   class="generalInfoBtn"><g:message
                                        code="entity.show.attachments"/> ${attachments?.size() > 0 ? '(' + attachments.size() + ')' : ''}</a>
                            </li>
                            <li role="presentation" class="navInfo${"notes".equals(activeTab) ? " active" : ""}">
                                <a id="tabNotes" data-targetBox="notes" href="javascript:"
                                   class="generalInfoBtn"><g:message
                                        code="entity.show.notes"/> ${notes?.size() > 0 ? '(' + notes.size() + ')' : ''}</a>
                            </li>
                        </ul>
                    </div>
                    <g:if test="${drawablePiesPerIndicatorAndPeriod}">
                        <div class="pull-right"
                             style="display: inline-block; max-width: 300px !important; margin-bottom: 20px"
                             id="pieGraphHotelBox">
                            <g:set var="firstIndicator"
                                   value="${(Indicator) drawablePiesPerIndicatorAndPeriod?.keySet()?.toList()?.first()}"/>
                            <div id="pieGraphContentHotel" style="padding-top:8px;display:inline-block; float:right;">
                                <p class="bold pieGraphHeading"
                                   style="margin-left:55px; text-align: center">${firstIndicator.pieGraphHeading}</p>

                                <div class="btn-group" style="display: inline-block; padding-left:120px;"><a
                                        style="text-align: center; padding-bottom: 0px !important;"
                                        class="dropdown-toggle link smallaction" id="pieDropdown" href="#" rel="popover"
                                        data-trigger="hover"
                                        data-content="${indicatorService.getLocalizedName(firstIndicator)}, ${abbr(value: firstReadyOperatingPeriodName ? firstReadyOperatingPeriodName : firstReadyDesignName, maxLength: 30)}"
                                        data-toggle="dropdown">${abbr(value: "${indicatorService.getLocalizedName(firstIndicator)}, ${firstReadyOperatingPeriodName ? firstReadyOperatingPeriodName : firstReadyDesignName}", maxLength: 30)} <span
                                            class="caret-middle"></span></a>
                                    <ul class="dropdown-menu">
                                        <g:each in="${(Map) drawablePiesPerIndicatorAndPeriod}"
                                                var="indicatorIdAndEntities">
                                            <g:set var="indicator" value="${(Indicator) indicatorIdAndEntities.key}"/>
                                            <g:each in="${(List<Map<String, String>>) indicatorIdAndEntities.value}"
                                                    var="entityIdAndName">
                                                <li class="indicators"><a href="javascript:" class="clickMe" onclick="drawPieGraphHotel('${entityIdAndName.get("entityId")}','${indicator.indicatorId}');updateHeading('${indicator.pieGraphHeading}'); $('a#pieDropdown').text(' ${abbr(value: "${indicator.nameStringWithoutQuotes}, ${entityIdAndName.get("name")}", maxLength: 30)}');appendCaret(); appendPopover('${entityIdAndName.get("type")} ${indicator?.nameStringWithoutQuotes} ${entityIdAndName.get("name")}');"/> ${indicatorService.getLocalizedShortName(indicator)} ${entityIdAndName.get("name")}</a></li>
                                            </g:each>
                                        </g:each>
                                    </ul>
                                </div>

                                <div id="pieWrapperHotel"></div>
                            </div>
                        </div>
                    </g:if>
                    <div id="infoTabController" class="container section entityshow-section pull-left"
                         style="border: 0px !important">
                        <div id="info" class="info-section ${activeTab != 'info' ? 'hidden' : ''}">
                            <div class="${entity.smallImage && !'infrastructure'.equalsIgnoreCase(entity.entityClass) ? 'entity-info-image-small' : 'entity-info-image'} text-center">
                                <div id="entityImage" ${drawablePiesPerIndicator ? 'class=\"entityshow-pieInfoImage\"' : ''}></div>
                            </div>

                            <div class="entity-info-body" style="width: auto !important;">
                                <table class="table table-condensed">
                                    <tbody>
                                    <g:if test="${licensesToShow || expiredLicenses}">
                                        <tr style="white-space: normal !important">
                                            <th><g:message code="entity.show.licenses"/></th>
                                            <td style="overflow: visible !important; word-break: break-word !important;"
                                                class="${userService.getSuperUser(currentUser) ? "editable" : ""}">
                                                <g:if test="${userService.getSuperUser(currentUser)}">
                                                    <g:if test="${licensesToShow}">
                                                        <g:set var="licenseAmount" value="${licensesToShow.size()}"/>
                                                        <g:each in="${licensesToShow}" var="license" status="index">
                                                            <g:link class="entity-show" controller="license"
                                                                    action="form" id="${license.id}">
                                                                ${license.name} <g:message
                                                                    code="license.type.${license.type}"/>${index < (licenseAmount - 1) ? ", " : ""}
                                                            </g:link>
                                                        </g:each>
                                                    </g:if>
                                                    <g:elseif test="${expiredLicenses}">
                                                        <g:set var="licenseAmount" value="${expiredLicenses.size()}"/>
                                                        <g:each in="${expiredLicenses}" var="license" status="index">
                                                            <g:link class="entity-show" controller="license"
                                                                    action="form" id="${license.id}">
                                                                ${license.name} <g:message
                                                                    code="license.type.${license.type}"/>${index < (licenseAmount - 1) ? ", " : ""}
                                                            </g:link>
                                                        </g:each>
                                                    </g:elseif>
                                                </g:if>
                                                <g:else>
                                                    <g:if test="${licensesToShow}">
                                                        <g:set var="licenseAmount" value="${licensesToShow.size()}"/>
                                                        <g:each in="${licensesToShow}" var="license" status="index">
                                                            ${license.name} <g:message
                                                                code="license.type.${license.type}"/>${index < (licenseAmount - 1) ? ", " : ""}
                                                        </g:each>
                                                    </g:if>
                                                    <g:elseif test="${expiredLicenses}">
                                                        <g:set var="licenseAmount" value="${expiredLicenses.size()}"/>
                                                        <g:each in="${expiredLicenses}" var="license" status="index">
                                                            ${license.name} <g:message
                                                                code="license.type.${license.type}"/>${index < (licenseAmount - 1) ? ", " : ""}
                                                        </g:each>
                                                    </g:elseif>
                                                </g:else>
                                                <g:if test="${(licensesToShow || expiredLicenses) && !entity?.public}">
                                                    <span id="seperatorLicenseAndStatus">-</span>

                                                    <g:if test="${licensesToShow}">
                                                        <g:each in="${licensesToShow}" var="license" status="index">
                                                            <g:if test="${license.renewalStatus?.equals("fixedPeriod") && !license.trialLength}">
                                                                <span id="licenseValid" class="black"><g:message
                                                                        code="licenseHandling.validity"/> <g:formatDate
                                                                        date="${license.validUntil}"
                                                                        format="dd.MM.yyyy"/></span>
                                                            </g:if>
                                                            <g:elseif
                                                                    test="${("autoRenewal")?.equals(license?.renewalStatus) && !license.trialLength}">
                                                                <span id="licenseRenewAuto" class="black"><g:message
                                                                        code="licenseHandling.renew"/> ${license?.renewalDate}</span>
                                                            </g:elseif>
                                                            <g:elseif test="${license?.trialLength}">
                                                                <g:set var="trialExpiration"
                                                                       value="${license.getChangeHistory(entity)?.dateAdded?.plus(license?.trialLength)}"/>
                                                                <g:if test="${trialExpiration}">
                                                                    <span id="licenseTrialExpiring"
                                                                          class="black"><g:message
                                                                            code="licenseHandling.validity"/> <g:formatDate
                                                                            date="${trialExpiration}"
                                                                            format="dd.MM.yyyy"/></span>
                                                                </g:if>
                                                            </g:elseif>
                                                            <g:else>
                                                                <span id="licenseTrialValid" class="black"><g:message
                                                                        code="licenseHandling.validity"/> <g:formatDate
                                                                        date="${license.validUntil}"
                                                                        format="dd.MM.yyyy"/></span>
                                                            </g:else>
                                                            <g:if test="${license?.licenseImage}">
                                                                <div class="img-holder"
                                                                     style="margin-top:-10px; float:right">
                                                                    <opt:displayDomainClassImage
                                                                            imageSource="${license?.licenseImage}"
                                                                            width="100px" height="100px"
                                                                            elementId="licenseImage"/>
                                                                </div>
                                                            </g:if>
                                                        </g:each>
                                                    </g:if>
                                                    <g:elseif test="${expiredLicenses}">
                                                        <g:each in="${expiredLicenses?.unique()}" var="license"
                                                                status="index">
                                                            <g:if test="${license?.trialLength}">
                                                                <g:set var="expiredTrial"
                                                                       value="${license.getChangeHistory(entity)?.dateAdded?.plus(license?.trialLength)}"/>
                                                                <span id="licenseTrialExpired"
                                                                      class="redWarningColor"><g:message
                                                                        code="expired.upperCase"/> - <g:formatDate
                                                                        date="${expiredTrial}"
                                                                        format="dd.MM.yyyy"/></span>
                                                            </g:if>
                                                            <g:elseif test="${license?.frozen}">
                                                                <span class="redWarningColor"><g:message
                                                                        code="license.frozen"/></span>
                                                            </g:elseif>
                                                            <g:elseif test="${license?.isFloatingLicense}">
                                                                <span class="redWarningColor">Unchecked floating license</span>
                                                            </g:elseif>
                                                            <g:else>
                                                                <span id="licenseExpired"
                                                                      class="redWarningColor"><g:message
                                                                        code="expired.upperCase"/>
                                                                -  <g:formatDate date="${license.validUntil}"
                                                                                 format="dd.MM.yyyy"/></span>
                                                            </g:else>
                                                        </g:each>
                                                    </g:elseif>
                                                </g:if>
                                            </td>
                                        </tr>
                                    </g:if>
                                    <opt:entitySummaryData entity="${entity}"/>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <g:if test="${showTasks}">
                        <div id="generalInfoContainer">
                            <div id="tasks" class="info-section ${activeTab != 'tasks' ? 'hidden' : ''}">
                                <g:render template="/entity/tasks"
                                          model="[entity: entity, tasks: tasks, completedTasks: completedTasks]"/>
                            </div>

                            <div id="attachments" class="info-section ${activeTab != 'attachments' ? 'hidden' : ''}">
                                <g:render template="/entity/attachments"
                                          model="[entity: entity, attachments: attachments]"/>
                            </div>

                            <div id="notes" class="info-section ${activeTab != 'notes' ? 'hidden' : ''}">
                                <g:render template="/entity/notes" model="[entity: entity, notes: notes]"/>
                            </div>
                        </div>
                    </g:if>
                </div>
            </div>
        </div>
        <g:if test="${indicatorBenchmarkLicensed && drawableGraphForCarbonBenchmark && drawableGraphForCarbonBenchmark.size() > 0 && entityHasBenchmark}">
            <div class="container section" id="resulGraphHeader">
                <div class="sectionheader">
                    <button class="pull-left sectionexpander"><i class="icon icon-chevron-down expander"></i><i
                            class="icon icon-chevron-right collapser"></i></button>

                    <div class="sectioncontrols pull-right">
                        <div class="btn-group" style="display: inline-block;">
                            <a id="benchmarkSelector" class="dropdown-toggle btn btn-primary" href="javascript:"
                               data-toggle="dropdown"><g:message code="benchmark_selector.heading"/> <span
                                    class="caret"></span></a>
                            <ul class="dropdown-menu">

                                <g:each in="${(Map) drawableGraphForCarbonBenchmark}" var="indicatorIdAndEntities"
                                        status="i">
                                    <g:set var="indicator" value="${(Indicator) indicatorIdAndEntities?.key}"/>
                                    <g:each in="${(List<Map<String, String>>) indicatorIdAndEntities?.value}"
                                            var="entityIdAndName">
                                        <g:if test="${i == 0}">
                                            <li class="indicators"><a href="javascript:" class="clickMe"
                                                                      onclick="drawPieGraph('${entityIdAndName.get("entityId")}', '${indicator.indicatorId}');
                                                                      $('a#pieDropdown').text(' ${abbr (value: "${indicator.nameStringWithoutQuotes}, ${entityIdAndName.get("name")}", maxLength: 30)}');
                                                                      appendCaret();
                                                                      appendPopover('${entityIdAndName.get("type")} ${indicator?.nameStringWithoutQuotes} ${entityIdAndName.get("name")}');
                                                                      drawStructurePieChart('${entityIdAndName.get("entityId")}', '${indicator?.indicatorId}');
                                                                      appendIndicatorBenchmark('${indicator?.indicatorId}', '${entityIdAndName.get('entityId')}', '${entityType}', '${entityIdAndName.get('name')}', '${entity.nameStringWithoutQuotes}', '${entity.countryResourceResourceId}');">${entityIdAndName.get("nameForShow")}</a>
                                            </li>
                                        </g:if>
                                    </g:each>
                                </g:each>
                            </ul>
                        </div>
                    </div>

                    <h2 class="h2expander" style="margin-left: 15px;"><g:message
                            code="entity_chart_information.heading"/><span id="resultSectionHeader"></span></h2>
                </div>

                <div class="sectionbody" style="min-height: 250px;">
                    <div class="row align-items-start"
                         style="display: flex; flex-direction: row; flex-wrap: wrap; justify-content: center">
                        <div id="mainpageBenchmark" class="col" style="display: flex; ">
                            <div id="benchmarkEntityPageWrapper"></div>
                        </div>

                        <div id="pieGraphContent" class="col" style="display: flex;">
                            <div id="pieWrapper"></div>
                        </div>

                        <div id="circularChart" class="col" style="display: flex;">
                            <div id="structurePieWrapper"></div>
                        </div>
                    </div>
                </div>
            </div>
        </g:if>

        <g:if test="${showLicenseKeyWindow}">
            <g:set var="trialAvailable"
                   value="${autoStartTrials && usableTrialEntityClassesForUser.contains(entity?.entityClass)}"/>
            <div class="container">
                <opt:renderPopUpTrial stage="trialActivationProjectPage" entity="${entity}"/>
                <table id="quotaTable" class="quotaTable table-condensed table">
                    <thead class="quotatableThead">
                    <tr style="padding-top: 30px !important;">
                        <th colspan="3" class="text-center"><g:message code="licenseHandling.no_license"/></th>
                        <th colspan="2" class="text-center"><g:message code="licenseHandling.have_license"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td class="text_at_top text-center">
                            <div class="btn-group">
                                <g:if test="${planetaryLicense && com.bionova.optimi.core.Constants.EntityClass.BUILDING.toString().equals(entity?.entityClass)}">
                                    <opt:link controller="entity" action="activateLicenseByKeyOrId"
                                              params="[entityId: entity?.id, licenseId: planetaryLicense?.id, entityClass: entity?.entityClass]"
                                              class="btn btn-primary"><g:message
                                            code="planetary_license_available"/></opt:link>
                                </g:if>
                                <g:else>
                                    <button class="btn btn-default" disabled><g:message
                                            code="planetary_license_available"/></button>
                                </g:else>
                            </div>
                        </td>
                        <td class="text_at_top text-center">
                            <div class="btn-group">
                                <a href="javascript:" id="autostartTrialBtn"
                                   class="btn btn-primary dropdown-toggle ${trialAvailable ? '' : 'disabledSaveOnLoad'}"
                                   data-toggle="dropdown" rel="popover" data-trigger="hover"
                                   data-content="${trialAvailable ? message(code: 'help_float_tip0b') : message(code: 'trial_done_no_prod')}"><g:message
                                        code="start_a_free_trial"/> <span class="caret"></span></a>
                                <ul class="dropdown-menu autostartTrialDropdown">
                                    <g:set var="showMoreRendered" value="${Boolean.FALSE}"/>
                                    <g:each in="${autoStartTrials}" var="license">
                                        <g:set var="highlightedBackground"
                                               value="${highlightTrialList?.find { it.id == license.id } ? "highlighted-background" : null}"/>
                                        <li ${highlightedBackground ? "" : "style=\"display: none;\""}
                                                class="${highlightedBackground ? highlightedBackground : "hiddenLi"}"><opt:link
                                                action="startFreeTrial"
                                                params="[licenseId: license.id, entityId: entity.id]">${license.name}</opt:link></li>
                                        <g:if test="${!highlightedBackground && !showMoreRendered}"><g:set
                                                var="showMoreRendered" value="${Boolean.TRUE}"/><li
                                                class="showMoreLi"><a href="javascript:"
                                                                      onclick="$('.hiddenLi').fadeIn();
                                                                      $('.showMoreLi').hide();"><g:message
                                                        code="show.all"/> <i class="fa fa-caret-down"></i></a>
                                        </li></g:if>
                                    </g:each>
                                </ul>
                            </div>
                        </td>
                        <td class="text_at_top text-center">
                            <div class="btn-group">
                                <g:if test="${userService.getAccount(user)}">
                                    <opt:link controller="wooCommerce" action="index" class="btn btn-primary"><g:message
                                            code="ecommerce.buy_online"/></opt:link>
                                </g:if><g:else>
                                    <opt:link controller="wooCommerce" action="index" params="[publicShop: true]"
                                              class="btn btn-primary"><g:message
                                            code="ecommerce.buy_online"/></opt:link>
                                </g:else>
                            </div>
                        </td>
                        <td class="text_at_top text-center">
                            <div class="btn-group">
                                <a class="btn btn-primary dropdown-toggle" data-toggle="dropdown"><g:message
                                        code="enter.license.key"/><span class="caret-middle"></span></a>

                                <div class="dropdown-menu pull-right" style="min-width: 500px">
                                    <g:form action="activateLicenseByKeyOrId">
                                        <g:hiddenField name="entityId" value="${entity?.id}"/>
                                        <g:hiddenField name="entityClass" value="${entity?.entityClass}"/>
                                        <span class="inliner">
                                            <g:textField name="licenseKey" value="" class="inline-block"/>
                                            <opt:submit name="save" value="${message(code: 'license.activate_license')}"
                                                        class="btn btn-primary inline-block"
                                                        style="margin-bottom: 9px;"/>
                                        </span>
                                    </g:form>
                                </div>
                            </div>
                        </td>
                        <td class="text_at_top text-center">
                            <div class="btn-group">
                                <g:if test="${managedLicenses}">
                                    <a class="btn btn-primary dropdown-toggle" data-toggle="dropdown"><g:message
                                            code="select.license"/> <span class="caret-middle"></span></a>

                                    <div class="dropdown-menu pull-right" style="min-width: 500px">
                                        <g:form action="activateLicenseByKeyOrId"
                                                style="display:inline-block !important;">
                                            <g:hiddenField name="entityId" value="${entity?.id}"/>
                                            <g:hiddenField name="entityClass" value="${entity?.entityClass}"/>
                                            <span>&nbsp;</span>
                                            <g:select name="licenseId" from="${managedLicenses}" optionKey="id"
                                                      optionValue="${{ it.name }}"/>
                                            <opt:submit name="save" value="${message(code: 'license.activate_license')}"
                                                        class="btn btn-primary btn-white-text"
                                                        style="vertical-align:top;"/>
                                        </g:form>
                                    </div>
                                </g:if>
                                <g:else>
                                    <a class="btn btn-primary dropdown-toggle disabledSaveOnLoad"><g:message
                                            code="select.license"/> <span class="caret-middle"></span></a>
                                </g:else>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="text-center" colspan="1">
                            <span style="padding-left:50px">
                                <g:if test="${planetaryLicense}">
                                    <g:message code="planetary_license_sponsored_by"/>
                                    <div class="img-holder" style="padding-left: 5px">
                                        <opt:displayDomainClassImage imageSource="${planetaryLicense?.licenseImage}"
                                                                     width="100px" height="100px"
                                                                     elementId="licenseImage"/>
                                    </div>

                                </g:if>
                                <g:else>
                                    <g:message code="planetary_license_unavailable"/>
                                </g:else>
                            </span>
                        </td>
                        <td class="text-center" colspan="4"><span style="padding-left:50px; width: 300px"><g:message
                                code="licenseHandling.main_user_activate"/></span></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </g:if>
        <g:elseif test="${showQuoteBar}">
            <div class="container">
                <div class="quotabar_public">
                    <g:set var="buyButtonUrl"
                           value="${createLink(controller: 'wooCommerce', action: 'index', params: [publicShop: true])}"/>
                    <g:message code="licenseHandling.public.quote_text"
                               args="[quoteUrl, webinarUrl, expertUrl, buyButtonUrl]"/>
                </div>
            </div>
        </g:elseif>

        <g:if test="${entity?.showOperatingPhase}">
        %{--HELP DIV WHEN NO DATA--}%
            <g:if test="${!entity?.modifiable || !validOperatingLicenses}">
                <g:render template="/entity/readonly_periods"
                          model="[validOperatingLicenses: validOperatingLicenses, selectedOperatingIndicators: selectedOperatingIndicators, operatingPeriods: operatingPeriods, operatingParameterQueries: operatingParameterQueries]"/>
            </g:if>
            <g:else>
            %{--FLOATING HELP FOR OPERATING PERIODS--}%
                <g:if test="${validOperatingLicenses}">
                    <opt:renderPopUpTrial stage="projectMainPagePeriod" operatingPeriods="${operatingPeriods}"
                                          selectedDesignIndicators="${selectedDesignIndicators}"
                                          channelFeature="${channelFeature}" entity="${entity}"
                                          license="${validOperatingLicenses?.first()}"/>
                </g:if>
            %{--END============FLOATING HELP FOR OPERATING PERIODS--}%
                <g:render template="/entity/periods"
                          model="[validOperatingLicenses: validOperatingLicenses, selectedOperatingIndicators: selectedOperatingIndicators, operatingPeriods: operatingPeriods, operatingParameterQueries: operatingParameterQueries, loadQueryFromDefault: loadQueryFromDefault]"/>
            </g:else>
            <g:if test="${!entity?.showPortfolio && operatingPeriods?.size() >= 1 && selectedOperatingIndicators?.size() >= 1 && operatingDrawableEntityIndicators && drawOldOperatingChart}">
                <div class="container section">
                    <div id="operatingGraphContent" class="graphContainer container section">
                        <g:if test="${operatingDrawableEntityIndicators.size() > 1}">
                            <div id="operatingBtnGrop" class="hidden btn-group pull-right hide-on-print"
                                 style=" margin-top: 40px; margin-right:10px;">
                                <div class="btn-group pull-left" style="display:inline-block;">
                                    <a class="dropdown-toggle btn btn-primary " id="operatingGraphDropdown"
                                       rel="popover" data-trigger="hover"
                                       data-content="<g:message code="design.graph_help"/>" href="#"
                                       data-toggle="dropdown"><g:message code="design.graph_choose"/> <span
                                            class="caret"></span></a>
                                    <ul id="operatingIndicatorDropdown" class="dropdown-menu">
                                        <g:each in="${operatingDrawableEntityIndicators}" var="indicator">
                                            <g:if test="${!indicator?.nonNumericResult}"><li class="indicators"><a
                                                    href="javascript:" class="clickMe"
                                                    onclick="drawGraphOperating('${entity?.id}', '${indicator?.indicatorId}', '${indicator?.requireMonthly}');">${indicatorService.getLocalizedShortName(indicator)}</a>
                                            </li>
                                            </g:if>
                                        </g:each>
                                    </ul>
                                </div>
                                <span id="btnGroup2"></span>

                                <div class="btn-group pull-left">
                                    <a href="javascript:" class="btn btn-primary hidden" id="operatingDrawTotal"
                                       onclick="drawGraphOperating('${entity?.id}', $('#indicatorIdField').val(), true)"><g:message
                                            code="totalScore"/></a>
                                </div>

                                <div class="btn-group pull-left hidden" id="denomsOperating">
                                    <a href="javascript:" class="dropdown-toggle btn btn-small"
                                       data-toggle="dropdown">Choose a denominator <span class="caret"></span></a>
                                    <ul id="operatingDenominatorDropdown" class="dropdown-menu">
                                    </ul>
                                </div>
                            </div>
                        </g:if>
                    </div>
                </div>
            </g:if>
        </g:if>

        <g:if test="${entity?.showDesignPhase}">
            <g:if test="${!entity?.modifiable || !validDesignLicenses}">
                <g:render template="/entity/readonly_designs"/>
            </g:if>
            <g:else>
            %{--FLOATING HELP FOR DESIGN PHASES--}%
                <opt:renderPopUpTrial stage="projectMainPageDesign"
                                      license="${validDesignLicenses ? validDesignLicenses.first() : null}"
                                      designs="${designs}" selectedDesignIndicators="${selectedDesignIndicators}"
                                      parameterQueries="${designParameterQueries?.keySet()?.toList()}"
                                      entity="${entity}" channelFeature="${channelFeature}"/>

            %{--END=====FLOATING HELP FOR DESIGN PHASES--}%
                <g:render template="/entity/designs"/>
                <opt:renderPopUpTrial stage="compareDesignProjectPage" designs="${designs}"
                                      selectedDesignIndicators="${selectedDesignIndicators}"/>
            </g:else>
        </g:if>
        <g:if test="${!entity?.showPortfolio && (selectedDesignIndicators || selectedOperatingIndicators) && (drawableEntityIndicators || operatingDrawableEntityIndicators) && !drawOldOperatingChart}">
            <g:set var="allValidIndicatorsAllTypes"
                   value="${[drawableEntityIndicators, operatingDrawableEntityIndicators]?.flatten()}"/>
            <g:set var="allChildEntities" value="${[designs, operatingPeriods]?.flatten()}"/>
            <div class="section container">
                <div class="sectionheader" onclick="toggleExpandSection(this)"
                     style="position: sticky;top: 0;z-index: 1;">
                    <button class="pull-left sectionexpanderspec"><i class="icon icon-chevron-down expander"></i><i
                            class="icon icon-chevron-right collapser"></i></button>

                    <h2 class="h2expanderspec pull-left" style="margin-left: 15px;"><g:message
                            code="result_visualisation"/> - <span rel="popover" data-trigger="hover" data-content=""
                                                                  id="indicatorNameWrap">${indicatorService.getLocalizedName(drawableEntityIndicators[0])}</span>
                    </h2>

                    <div class="sectioncontrols pull-right" onclick="event.stopPropagation()">
                        <div class="btn-group">
                            <a class="btn btn-primary dropdown-toggle"
                               data-toggle="dropdown">${message(code: "showing")}: <span
                                    id="designNumbers"></span> ${message(code: "graphs.designs").toLowerCase()} <span
                                    class="caret-middle"></span></a>
                            <ul class="dropdown-menu" id="designsNameContainer" style="padding: 7px"></ul>
                        </div>

                        <div class="btn-group" id="classificationListBtnGroup">
                            <a class="btn btn-primary dropdown-toggle"
                               data-toggle="dropdown">${message(code: "classificationList")} <span
                                    class="caret-middle"></span></a>
                            <ul class="dropdown-menu" id="classificationListContainer" style="padding: 7px"></ul>
                        </div>

                        <div class="btn-group" id="selectToolAndRule">
                            <g:set var="hasMoreThanOneGraph"
                                   value="${allValidIndicatorsAllTypes?.collect({ it?.reportVisualisation })?.flatten()?.findAll({ ['entityPageChart', 'entityByStageChart', 'entityElementChart', 'entityCompareElementsChart', 'entityByStageElementChart'].contains(it) })?.size() > 1}"/>
                            <g:set var="hasMoreThanOneRule"
                                   value="${allValidIndicatorsAllTypes?.collect({ it.getGraphCalculationRuleObjects(entity) })?.flatten()?.size() > 1}"/>
                            <g:set var="disableDropdown"
                                   value="${allValidIndicatorsAllTypes?.size() == 1 && (!hasMoreThanOneRule || (hasMoreThanOneRule && !hasMoreThanOneGraph))}"/>
                            <g:set var="indicatorIdList"
                                   value="${allValidIndicatorsAllTypes?.collect({ it.indicatorId })}"/>
                            <a class="btn btn-primary dropdown-toggle ${disableDropdown ? "disabledOnLoad" : ""}"
                               type="button"
                               ${disableDropdown ? "disabled='disabled'" : ""}data-toggle="dropdown"><g:message
                                    code="graph.tools_class_and_impact_categories"/> <span class="caret-middle"></span>
                            </a>
                            <ul class="dropdown-menu placeholder" id="btnClassList"
                                style="padding: 10px; max-height: 500px; overflow: scroll">
                                <g:if test="${allValidIndicatorsAllTypes}">
                                    <g:each in="${allValidIndicatorsAllTypes}" var="indicator">
                                        <g:set var="calculationRules"
                                               value="${indicator.getGraphCalculationRuleObjects(entity)}"/>
                                        <li class="parent_menu"><a
                                                href="#">${indicatorService.getLocalizedName(indicator)}</a></li>
                                        <g:each in="${calculationRules}" var="calculationRule">
                                            <li class="sub_menu"><a href="javascript:"
                                                                    style="font-size: 13px; font-weight: normal;"
                                                                    onclick='renderMainEntityCharts("${entity?.id}", "${indicator?.indicatorId}", "${calculationRule?.calculationRuleId}", ${indicatorIdList as grails.converters.JSON}, null, event);'>${calculationRule?.localizedName}</a>
                                            </li>
                                        </g:each>
                                    </g:each>
                                </g:if>
                            </ul>
                        </div>
                    </div>
                </div>

                <div class="sectionbody" id="mainEntityChartsWrapper">
                    <g:render template="/entity/mainEntityCharts"
                              model="[entity: entity, indicator: allValidIndicatorsAllTypes[0], allValidIndicatorsAllTypes: allValidIndicatorsAllTypes, graphCalculationRules: allValidIndicatorsAllTypes[0]?.getGraphCalculationRuleObjects(entity), designs: designs]"/>
                </div>
            </div>
            <div class="container section">
        </g:if>
        <g:if test="${entity?.showPortfolio}">
            <div class="loading-spinner"><div class="image">
                <svg class="loadingSVG" version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg" x="0px" y="0px"
                     width="270px" height="270px" viewBox="0 0 32 32" style="enable-background:new 0 0 32 32;"
                     xml:space="preserve">
                    <g>
                        <path class="arrowSpinningSvg mm-o-syncing-queue" d="M24.5,10.4L22.2,11l5,5L29,9.2l-2.4,0.7C24.4,6.1,20.4,3.8,16,3.8c-5.4,0-10.1,3.4-11.7,8.6
	c-0.2,0.5,0.1,1.1,0.7,1.3c0.1,0,0.2,0,0.3,0c0.4,0,0.8-0.3,1-0.7c1.3-4.3,5.2-7.2,9.7-7.2C19.5,5.8,22.7,7.5,24.5,10.4z"/>
                    </g>
                </svg>

                <p class="working"><g:message code="loading.working"/>.</p>
            </div></div>

            <div id="portfolioBody">
            </div>
        </g:if>
        <div class="modal hide modal large" id="chooseDesignsModal">
            <div class="modal-header text-center"><button type="button" class="close"
                                                          data-dismiss="modal">&times;</button>

                <h2>Designs in ${entity.name}</h2>

                <p class="bold" id="chooseDesignsModalHeading"></p>

            </div>

            <div class="modal-body" id="chooseDesignsModalBody">

            </div>
        </div>

        <div class="modal hide modal large" id="indicatorBenchmarkModal">
            <div class="modal-header text-center"><button type="button" class="close"
                                                          data-dismiss="modal">&times;</button>

                <h2 id="indicatorBenchmarkModalHeading"></h2>
            </div>

            <div class="modal-body" id="indicatorBenchmarkModalBody"></div>
        </div>

        <div class="modal hide modal large" id="lockEntityModal">
            <div class="modal-header text-center"><button type="button" class="close"
                                                          data-dismiss="modal">&times;</button>

                <h2 id="lockEntityModalHeading"><i class="fa fa-lock" aria-hidden="true"></i> <g:message
                        code="design.lock"/> <span id="lockEntityModalEntityName"></span>?</h2>
            </div>

            <div class="modal-body" id="lockEntityModalBody">
                <g:form controller="design" action="lock" name="lockEntityForm">
                    <input type="hidden" name="entityId" id="lockEntityModalEntityId" value=""/>
                    <input type="hidden" name="designId" id="lockEntityModalDesignId" value=""/>
                    <input type="hidden" name="superUser" id="lockEntityModalSuperUser" value="false"/>
                    <table>
                        <tr>
                            <td><input style="width: 25px; !important; height: 17px; !important; " type="checkbox"
                                       name="persistDefaults" id="persistDefaults" value="true"/></td>
                            <td><label for="persistDefaults"><strong><g:message
                                    code="design.lock.persistDefaults"/></strong> - <g:message
                                    code="design.lock.persistDefaults.info"/>.</label></td>
                        </tr>
                    </table>

                    <div style="text-align: center; margin-top: 15px;">
                        <g:submitButton name="lock" id="lock" value="${message(code: 'design.lock')}"
                                        class="btn btn-primary" onclick="doubleSubmitPrevention('lockEntityForm')"/>
                    </div>
                </g:form>
            </div>
        </div>

        <div class="modal large hide" id="importFromEntities"></div>

        <%-- Overley to not allow the user click --%>
        <div class="overlay" id="wordDocGenOverlay">
            <div class="overlay-content" id="wordDocGenOverlayContent"></div>
        </div>
        <%-- Overley to not allow the user click --%>

        <%-- This is here just for adding empty --%>
        <div class="container section">
            <div>
                <p style="height: 100px;">&nbsp;</p>
            </div>
        </div>


        <div class="overlay" id="myOverlay">
            <div class="overlay-content" id="splitViewOverLay">
            </div>
        </div>

        <script type="text/javascript">
            $(document).ready(function () {
                <g:if test="${indicatorsWithDeprecationNote}">
                <g:set var="indicatorNames" value=""/>
                <g:each in="${indicatorsWithDeprecationNote}" var="indicatorWithNote">
                <g:set var="indicatorNames" value="${indicatorNames+' '+indicatorWithNote.name.get('EN')}"/>
                <g:set var="deprecationMsg" value="${indicatorWithNote.deprecationNote}"/>
                </g:each>
                showDeprecationNote('${indicatorNames}', '${deprecationMsg.get('EN')}');
                </g:if>

                retriggerDropdowns();

                $('.modal').on('hidden.bs.modal', function (e) {
                    $(this).removeData();
                });

                $('.indicatorHelps').popover({
                    content: "${message(code:'design.form.indicator_help')}",
                    placement: 'right',
                    template: '<div class="popover"><div class="arrow"></div><div class="popover-content indicatorHelp"></div></div>',
                    trigger: 'hover',
                    html: true
                });
                var popOverSettings = {
                    placement: 'left',
                    container: 'body',
                    html: true,
                    template: '<div class="popover popover-fade" style=" display: block; max-width: 180px;"><div class="arrow"></div><div class="popover-content"></div></div>'
                };
                var popOverSettings2 = {
                    placement: function (context, source) {
                        var position = $(source).position();
                        if (position.left > 300) {
                            return "left";
                        }

                        if (position.left < 300) {
                            return "right";
                        }

                        if (position.top < 110) {
                            return "bottom";
                        }

                        return "top";
                    },
                    container: '#addIndicatorsDesign',
                    template: '<div class="popover popover-fade" style=" display: block; width: 450px"><div class="arrow"></div><div class="forFirstIndicator popover-content"></div></div>'
                };
                $(".firstIndicator[rel=popover]").popover(popOverSettings2)
                $(".longcontent[rel=popover]").popover(popOverSettings);
                $('.sectionexpander').on('click', function (event) {
                    var section = $(this).parent().parent();
                    var periodOrDesign = $(this).attr('data-name');
                    $(section).find('.sectioncontrols').fadeToggle(200);
                    $(section).find('.sectionbody').fadeToggle(200);
                    $(section).toggleClass('collapsed');

                });
                $('.h2expander').on('click', function (event) {
                    var section = $(this).parent().parent();
                    var periodOrDesign = $(this).attr('data-name');
                    $(section).find('.sectioncontrols').fadeToggle(200);
                    $(section).find('.sectionbody').fadeToggle(200);
                    $(section).toggleClass('collapsed');
                });


             $('#showAllTasks').on('click', function (event) {
                    $('.completedTask').css('display', '');
                    $(this).hide();
                    $('#showAllInfo').hide();
                });


                $('#showAllPortfolios').on('click', function (event) {
                    $('.hiddenPortfolio').css('display', '');
                    $(this).hide();
                    $('#showAllPortfoliosRow').hide();
                });
                loadImage('entityImage', '${entity?.id?.toString()}', '${entity?.entityClass}', false);

                showInfo();
                $('.ribaStageHelps').popover({
                    content: "${message(code:'riba_stage_help')}",
                    placement: 'right',
                    template: '<div class="popover ribaHelpWrapper"><div class="arrow"></div><div class="popover-content ribaHelp"></div></div>',
                    trigger: 'hover',
                    html: true,
                    container: 'body'
                });
                $('.newDesignHelps').popover({
                    content: "${message(code:'new_design_help')}",
                    placement: 'right',
                    template: '<div class="popover ribaHelpWrapper"><div class="arrow"></div><div class="popover-content ribaHelp"></div></div>',
                    trigger: 'hover',
                    html: true,
                    container: 'body'
                });
                $('.conflictingIndicatorsWarn').each(function () {
                    $(this).popover({
                        placement: 'top',
                        template: '<div class="popover importmapperPopover"><div class="arrow"></div><div class="popover-content dataSourcesToolTip"></div></div>',
                        trigger: 'hover',
                        html: true,
                        container: 'body'
                    });
                });
            });

            function toggleContent() {
                var section = $(this).parent().parent();
                var periodOrDesign = $(this).attr('data-name');
                $(section).find('.sectioncontrols').fadeToggle(200);
                $(section).find('.sectionbody').fadeToggle(200);
                $(section).toggleClass('collapsed');
            }
        </script>
        <script type="text/javascript">

            function hideChartOnly() {
                $('#graphContent').remove();
                $('#removeChart').remove();
            }

            function hideOperatingChartOnly() {
                $('#operatingGraphContent').remove();
                $('#removeOperatingChart').remove();
            }

            $(function () {
                disableEnableDropdown('indicatorDropdown', 'graphDropdown');
                disableEnableDropdown('operatingIndicatorDropdown', 'operatingGraphDropdown');
            });


            $(function () {
                $('#graphDropdown[rel="popover"]').popover({
                    placement: 'top',
                    template: '<div class="popover popover-fade" style=" display: block;"><div class="arrow"></div><div class="popover-content"></div></div>'
                });
            });

            $(function () {
                $('#operatingGraphDropdown[rel="popover"]').popover({
                    placement: 'top',
                    template: '<div class="popover popover-fade" style=" display: block;"><div class="arrow"></div><div class="popover-content"></div></div>'
                });
            });

            <g:if test="${operatingDrawableEntityIndicators && drawOldOperatingChart}">
            $(function () {
                var entityId = "${entity?.id}";
                var indicatorId = "${operatingDrawableEntityIndicators[0]?.indicatorId}";
                <g:if test="${!operatingDrawableEntityIndicators[0]?.requireMonthly}">
                $.ajax({
                    type: 'POST',
                    data: 'entityId=' + entityId + '&indicatorId=' + indicatorId,
                    url: '/app/sec/entity/operatingGraphByIndicator',
                    beforeSend: function () {
                        $('#graphWrapperOperating').remove();
                        $('.loading-spinner').removeClass("hidden");
                    },
                    complete: function () {
                        $('#operatingGraphContent').addClass("chartContent");
                        $('#operatingBtnGrop').removeClass("hidden");
                        $(".loading-spinner").addClass("hidden");
                        $("#operatingGraphDropdown").popover('disable');
                    },
                    success: function (data, textStatus) {
                        if (data.output) {
                            $('#operatingGraphContent').append(data.output);
                        } else {
                            $('.nograph').show();

                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                    }
                });
                </g:if><g:else>
                $.ajax({
                    type: 'POST',
                    data: 'entityId=' + entityId + '&indicatorId=' + indicatorId,
                    url: '/app/sec/entity/drawMonthlyOperating',
                    beforeSend: function () {
                        $('#graphWrapperOperating').remove();
                        $('.loading-spinner').removeClass("hidden");
                    },
                    complete: function () {
                        $('#operatingGraphContent').addClass("chartContent");
                        $('#operatingBtnGrop').removeClass("hidden");
                        $(".loading-spinner").addClass("hidden");
                        $("#operatingGraphDropdown").popover('disable');
                    },
                    success: function (data, textStatus) {
                        if (data.output) {
                            $('#operatingGraphContent').append(data.output);
                        } else {
                            $('.nograph').show();

                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                    }
                });
                </g:else>
            });
            </g:if>

            function drawGraphOperating(entityId, indicatorId, monthly, denominatorId) {
                if (!monthly) {
                    $.ajax({
                        type: 'POST',
                        data: 'entityId=' + entityId + '&indicatorId=' + indicatorId,
                        url: '/app/sec/entity/operatingGraphByIndicator',
                        beforeSend: function () {
                            $('#graphWrapperOperating').remove();
                            $('.loading-spinner').removeClass("hidden");
                        },
                        complete: function () {
                            $(".loading-spinner").addClass("hidden");
                            $("#operatingGraphDropdown").popover('disable');
                        },
                        success: function (data, textStatus) {
                            if (data.output) {
                                $('#operatingGraphContent').append(data.output);

                            } else {
                                $('.nograph').show();

                            }
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                        }
                    });
                } else {
                    var queryString = 'entityId=' + entityId + '&indicatorId=' + indicatorId;

                    if (denominatorId && "undefined" !== denominatorId) {
                        queryString = 'entityId=' + entityId + '&indicatorId=' + indicatorId + '&denominatorId=' + denominatorId;
                        $('#operatingDrawTotal').removeClass('hidden');
                    } else {
                        $('#operatingDrawTotal').addClass('hidden');
                    }
                    $.ajax({
                        type: 'POST',
                        data: queryString,
                        url: '/app/sec/entity/drawMonthlyOperating',
                        beforeSend: function () {
                            $('#graphWrapperOperating').remove();
                            $('.loading-spinner').removeClass("hidden");
                        },
                        complete: function () {
                            $(".loading-spinner").addClass("hidden");
                        },
                        success: function (data, textStatus) {
                            if (data.output) {
                                $('#operatingGraphContent').append(data.output);

                            } else {
                                $('.nograph').show();

                            }
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                        }
                    });
                }
            }

            <g:if test="${drawablePiesPerIndicatorAndPeriod && drawablePiesPerIndicatorAndPeriod.size()>0}">
            $(function (entityId, indicatorId) {
                entityId = "${firstReadyOperatingPeriodId ? firstReadyOperatingPeriodId : firstReadyDesignId}";
                indicatorId = "${drawablePiesPerIndicatorAndPeriod.keySet().toList().first().indicatorId}";
                if (!$('#infoTabController').hasClass('entityshow-pieSection')) {
                    $('#infoTabController').addClass('entityshow-pieSection');
                }
                if (!$('#generalInfoContainer').hasClass('entityshow-pieSection')) {
                    $('#generalInfoContainer').addClass('entityshow-pieSection')
                }
                $.ajax({
                    type: 'POST',
                    data: 'entityId=' + entityId + '&indicatorId=' + indicatorId,
                    url: '/app/sec/entity/entityPieGraphHotel',
                    beforeSend: function () {
                        $('.pieContainerHotel').remove();
                    },
                    success: function (data, textStatus) {
                        if (data.output) {
                            $('#pieWrapperHotel').append(data.output);
                        } else {
                            $('.nograph').show();

                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                    }
                });
            });

            function updateHeading(newHeading) {
                var heading = document.getElementsByClassName("pieGraphHeading");
                heading[0].innerHTML = newHeading;
            }

            </g:if>
            <g:if test="${drawableGraphForCarbonBenchmark && drawableGraphForCarbonBenchmark.size()>0 && indicatorBenchmarkLicensed && entityHasBenchmark }">
            <g:if test="${readyChosenDesignId != null && drawablePiesPerIndicator}">
            $(function (entityId, indicatorId) {
                entityId = "${readyChosenDesignId}";
                indicatorId = "${drawablePiesPerIndicator.keySet().toList().first().indicatorId}";
                var entityName = "${readyChosenDesignName}";
                if ($('#infoTabController').hasClass('entityshow-pieSection')) {
                    $('#infoTabController').removeClass('entityshow-pieSection');
                }
                drawPieGraph(entityId, indicatorId);
                drawStructurePieChart(entityId, indicatorId);
                appendIndicatorBenchmark(indicatorId, entityId, '${entityType}', entityName, '${entity.nameStringWithoutQuotes}', '${entity.countryResourceResourceId}');

            });
            </g:if>
            <g:elseif test="${readyDesignHighestRibaStageId != null && drawablePiesPerIndicator}">
            $(function (entityId, indicatorId) {
                entityId = "${readyDesignHighestRibaStageId}";
                indicatorId = "${drawablePiesPerIndicator.keySet().toList().first().indicatorId}";
                var entityName = "${readyDesignHighestRibaStageName}";
                if ($('#infoTabController').hasClass('entityshow-pieSection')) {
                    $('#infoTabController').removeClass('entityshow-pieSection');
                }
                drawPieGraph(entityId, indicatorId);
                drawStructurePieChart(entityId, indicatorId);
                appendIndicatorBenchmark(indicatorId, entityId, '${entityType}', entityName, '${entity.nameStringWithoutQuotes}', '${entity.countryResourceResourceId}');

            });
            </g:elseif><g:elseif test="${drawableGraphForCarbonBenchmark}">
            $(function (entityId, indicatorId) {
                entityId = "${drawableGraphForCarbonBenchmark.values()?.first()?.first()?.get("entityId")}";
                indicatorId = "${drawableGraphForCarbonBenchmark.keySet().toList().first().indicatorId}";
                var entityName = "${drawableGraphForCarbonBenchmark.values()?.first()?.first()?.get("name")}";
                if ($('#infoTabController').hasClass('entityshow-pieSection')) {
                    $('#infoTabController').removeClass('entityshow-pieSection');
                }
                drawPieGraph(entityId, indicatorId);
                drawStructurePieChart(entityId, indicatorId);
                appendIndicatorBenchmark(indicatorId, entityId, '${entityType}', entityName, '${entity.nameStringWithoutQuotes}', '${entity.countryResourceResourceId}');


            });
            </g:elseif>
            </g:if>

            function appendCaret() {
                $('#pieDropdown').append(' <span class="caret-middle"></span>')
            }

            function appendPopover(data) {
                var popover = $('#pieDropdown').popover("destroy").popover({
                    content: data,
                    placement: 'top',
                    template: '<div class="popover"><div class="arrow"></div><div class="popover-content"></div></div></div>'
                });
            }
        </script>
        <g:if test="${entity?.showPortfolio}">
            <script type="text/javascript">
                function renderPortfolio() {
                    <g:if test="${'design' == entity?.portfolio?.type}">
                    $.ajax({
                        type: 'POST', data: 'entityId=${entity.id}', url: '/app/sec/portfolio/renderDesignPortfolio',
                        success: function (data, textStatus) {
                            if (data.output) {
                                $(".loading-spinner").addClass("hidden");
                                $('#portfolioBody').empty();
                                $('#portfolioBody').append(data.output);
                                $('.dropdown-toggle').dropdown();
                            }
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                        }
                    });
                    </g:if>
                    <g:else>
                    $.ajax({
                        type: 'POST', data: 'entityId=${entity.id}', url: '/app/sec/portfolio/renderOperatingPortfolio',
                        success: function (data, textStatus) {
                            if (data.output) {
                                $(".loading-spinner").addClass("hidden");
                                $('#portfolioBody').empty();
                                $('#portfolioBody').append(data.output);
                                $('.dropdown-toggle').dropdown();
                                sortablePortfolioTables();
                                appendPortfolioSortignHeads();
                            }
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                        }
                    });
                    </g:else>
                }

                <g:if test="${'design' == entity?.portfolio?.type}">
                $(document.body).on('change', '.unitSelect', function () {
                    var indicatorId = $(this).attr("data-indicatorId");
                    var contentId = '#' + indicatorId + 'content';
                    var denominatorType = $(this).find('option:selected').attr("data-denominator");
                    var denominatorName = $(this).find('option:selected').text();
                    var unit = encodeURIComponent($(this).val());

                    $.ajax({
                        type: 'POST',
                        data: 'entityId=${entity.id}&indicatorId=' + indicatorId + '&datasetManualIds=' + unit + '&denominatorType=' + denominatorType,
                        url: '/app/sec/portfolio/renderDesignDenominatorScores',
                        success: function (data, textStatus) {
                            if (data.output) {
                                $(contentId).empty();
                                $(contentId).append(data.output);
                                var theTable = '#table' + indicatorId;
                                var theGraph = '#graph' + indicatorId;
                                var theGraphDiv = '#' + indicatorId + 'graph'
                                if ($(theGraphDiv).hasClass('active')) {
                                    $(theTable).hide();
                                    $(theGraph).show()
                                }

                                $('.dropdown-toggle').dropdown();
                                $("#" + indicatorId + "portolioTableHeadingDenomName").text(" / " + denominatorName);
                            }
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                        }
                    });
                });
                </g:if>
                <g:else>
                $(document.body).on('change', '.unitSelect', function () {
                    var indicatorId = $(this).attr("data-indicatorId");
                    var portfolioId = $(this).attr("data-portfolioId");
                    var denominatorType = $(this).find('option:selected').attr("data-denominator");
                    var denominatorName = $(this).find('option:selected').text();
                    var denominatorId = $(this).find('option:selected').attr("data-denominatorId");
                    var showPortFolioSourceListing = $(this).find('option:selected').attr("data-showPortFolioSourceListings");
                    var contentId = '#' + indicatorId + 'content';
                    var unit = encodeURIComponent($(this).val());

                    $.ajax({
                        type: 'POST',
                        data: 'id=' + portfolioId + '&indicatorId=' + indicatorId + '&datasetManualIds=' + unit + '&denominatorType=' + denominatorType + '&denominatorId=' + denominatorId + '&showPortFolioSourceListing=' + showPortFolioSourceListing,
                        url: '/app/sec/portfolio/renderOperatingDenominatorScores',
                        success: function (data, textStatus) {
                            if (data) {
                                var allListings = document.getElementsByClassName('listing' + indicatorId);
                                if (allListings) {
                                    for (var i = 0; i < allListings.length; i++) {
                                        var listing = allListings[i];

                                        if (listing) {
                                            $(listing).fadeOut('slow');
                                            listing.style.display = 'none';
                                        }
                                    }
                                }

                                $(contentId).empty();
                                $(contentId).append(data.output);
                                var theTable = '#table' + indicatorId;
                                var theGraph = '#graph' + indicatorId;
                                var theGraphDiv = '#' + indicatorId + 'graph'
                                if ($(theGraphDiv).hasClass('active')) {
                                    $(theTable).hide();
                                    $(theGraph).show()
                                }
                                $('.dropdown-toggle').dropdown();
                                $("#" + indicatorId + "portolioTableHeadingDenomName").text(" / " + denominatorName);
                                sortablePortfolioTables();
                                appendPortfolioSortignHeads();
                            }
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                        }
                    });
                });
                </g:else>
            </script>
        </g:if>
        <g:if test="${!user?.disableZohoChat}">
            <!-- Start of oneclicklca Zendesk Widget script -->
            <script id="ze-snippet"
                    src="https://static.zdassets.com/ekr/snippet.js?key=788c0e91-3199-4f3d-8472-17fa41abb688"></script>
            <script>
                zE(function () {
                    zE.identify({
                        name: '${user?.name}',
                        email: '${user?.username}'
                    });
                });
                window.zESettings = {
                    webWidget: {
                        <g:if test="${!chatSupportLicensed}">
                        chat: {
                            suppress: true
                        },
                        </g:if>
                        <g:if test="${!userService.getContactSupportAvailable(user)}">
                        contactForm: {
                            suppress: true
                        },
                        </g:if>
                        contactOptions: {
                            enabled: true
                        }
                    }
                };
            </script>
            <!-- End of oneclicklca Zendesk Widget script -->
        </g:if>
        <g:set var="pageRenderTime" value="${(System.currentTimeMillis() - pageRenderTime) / 1000}"/>
        <script type="text/javascript">

            var popOverSettingsDesignName = {
                placement: function (context, source) {
                    var position = $(source).position();
                    if (position.top < 110) {
                        return "bottom";
                    }
                    return "top";
                },
                container: 'body',
                template: '<div class="popover popover-fade designNamePopover" style=" display: block;"><div class="arrow"></div><div class="popover-content designNamePopover"></div></div>'
            };
            $(".${designPopupName}[rel=popover]").popover(popOverSettingsDesignName)


            // THE FOLLOWING NEEDS TO ALWAYS BE LAST IN PAGE RENDER:
            var domReady;
            var windowReady;

            $(document).ready(function () {
                domReady = (Date.now() - timerStart) / 1000;
            });
            $(window).on('load', function () {
                windowReady = ((Date.now() - timerStart) / 1000) - domReady;
                renderPageRenderTimes("${showRenderingTimes ? 'true' : 'false'}", "${secondsTaken}", "${pageRenderTime}", domReady, windowReady)
            });
            // END
        </script>
    </div>
    </div>
</div>
</div>
</body>
</html>