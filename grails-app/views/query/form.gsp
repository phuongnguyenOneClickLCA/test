<%@ page import="com.bionova.optimi.core.Constants; com.bionova.optimi.core.domain.mongo.Indicator; com.bionova.optimi.core.domain.mongo.Entity" %>
<g:set var="valueReferenceService" bean="valueReferenceService"/>
<g:set var="userService" bean="userService"/>
<!doctype html>
<%-- MAIN SCREEN --%>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="format-detection" content="telephone=no"/>
    <asset:javascript src="jquery.autocomplete.js?v=${grailsApplication.metadata.'app.version'}"/>
    <asset:javascript src="stupidtable.min.js" />
    <asset:javascript src="selection.min.js" />
    <asset:javascript src="highcharts.js" />
    <asset:javascript src="no-data-to-display.js"/>
    <asset:javascript src="exporting.js" />
    <asset:javascript src="export-data.js" />
    <asset:javascript src="offline-exporting.js"/>
    <asset:javascript src="parsley.js"/>
    <asset:javascript src="jquery.contextMenu.min.js"/>
    <asset:javascript src="jquery.ui.position.min.js"/>
    <asset:javascript src="query/groupEdit.js"/>
    <asset:javascript src="query/verificationPoints.js"/>
    <asset:javascript src="query/triggerCalculation.js"/>
    <asset:stylesheet src="jquery.contextMenu.min.css"/>

    <g:set var="querySectionService" bean="querySectionService"/>
    <g:set var="queryService" bean="queryService"/>

    <g:each in="${query.linkedJavascriptRules}" var="linkedScript">
        <asset:javascript src="additionalCalculationScripts/${linkedScript.key}.js"/>
    </g:each>

    <g:if test="${supportedFilters}">
    <asset:stylesheet src="filterCombobox.css"/>
    </g:if>
    <g:if test="${!projectLevel}">
    <style type="text/css">
        @media only screen and (min-width: 1600px) {

            .container, .section {
                width: 90%;
            }
            #quickFilterOptions {
                width: 100%;
            }

        }

        body {
            padding-bottom: 110px !important;
        }

    </style>
    </g:if>
</head>

<body>
<g:set var="isLocked" value="${entity && (entity.locked || entity.superLocked)}"/>
<g:set var="pageRenderTime" value="${System.currentTimeMillis()}" />
<queryRender:queryNotReadyAlert parentEntity="${parentEntity}" entity="${entity}" indicator="${indicator}" projectLevel="${projectLevel}" query="${query}"/>
<queryRender:queryPreventChanges preventChanges="${preventChanges}"/>

<div id="mainContent">
<div id="missmatchingDataWarningDiv"></div>
<div id="dataQualityWarningDiv"></div>

<g:uploadForm class="form-horizontal" autocomplete="off" controller="query" action="save" id="queryForm" name="queryForm" novalidate="novalidate">
    <input type="hidden" id="LocalizedGroupingWarning" value="${message(code: "grouping.unsaved_groupings")}" />
    <input type="hidden" id="LocalizedGroupingOk" value="${message(code: "ok")}" />
    <input type="hidden" id="isCalculationRunning" name="${Constants.REDIRECT_WITHOUT_CALCULATION_AND_SAVE}"
           value="${isCalculationRunning}" />
    <input type="hidden" id="triggerCalculation" name="${Constants.TRIGGER_CALCULATION_PARAMETER_KEY}"
           value="false"/>
    <div class="container" id="buttons" style="background-color: #FFFFFF;">
            <g:if test="${!preview}">
                <div class="pull-right hide-on-print">
                  <g:if test="${!newProject}"><opt:link controller="entity" action="show" id="${parentEntity?.id}" class="btn"><g:message code="cancel"/></opt:link></g:if>
                    <g:if test="${projectLevel}">
                        <g:if test="${modifiable}">
                            <a class="btn btn-primary preventDoubleSubmit disabledOnLoad" name="saveBtn"
                               onclick="submitQuery('${query?.queryId}', 'queryForm', null, 'save', '${message(code: "lca_lcc.warning")}');">
                                ${message(code: 'save')}
                            </a>
                        </g:if>
                    </g:if>
                    <g:else>
                        <g:if test="${modifiable && !entity?.locked}">
                            <span class="btn-group" style="display: inline-block;">
                                <span class="disabledSaveBtn" rel="popover" data-trigger="hover"
                                      data-content="${message(code: 'query.form.no.changes')}"
                                      style="display: inline-block;">
                                    <a class="btn btn-primary preventDoubleSubmit disabledOnLoad disabledSaveOnLoad"
                                       id="save"
                                       name="saveBtn"
                                       rel="popover" data-trigger="hover"
                                       data-content="${message(code: 'query_form.save_changes_popup')}"
                                       onclick="submitQuery('${query?.queryId}', 'queryForm', null, 'save',
                                           null, null, null, ${indicator?.maxRowLimitPerDesign ?: 'null'}, null, false, true)">
                                        ${message(code: 'save')}
                                    </a>
                                </span>
                            </span>
                        </g:if>
                    </g:else>
                   <g:if test="${!projectLevel}">
                       <g:if test="${!modifiable||userReadOnlyQuery}">
                           <g:if test="${entity?.entityClass == Constants.EntityClass.OPERATING_PERIOD.toString()}">
                               <opt:link class="btn btn-primary readOnlyResults disabledOnLoad" onclick="clickAndDisable('readOnlyResults')"
                                         rel="popover" data-trigger="hover"
                                         data-content="${message(code: 'query_form.results_popup')}"
                                         controller="operatingPeriod" action="results"
                                         params="[childEntityId: entity?.id, indicatorId: indicator?.indicatorId]">
                                   <i class="icon-results icon-white"></i> ${message(code: 'results')}
                               </opt:link>
                           </g:if>
                           <g:else>
                               <g:if test="${!entity.isMaterialSpecifierEntity}">
                                   <opt:link class="btn btn-primary readOnlyResults preventDoubleSubmit disabledOnLoad"
                                             rel="popover" data-trigger="hover"
                                             data-content="${message(code: 'query_form.results_popup')}"
                                             onclick="clickAndDisable('readOnlyResults')" controller="design" action="results"
                                             params="[childEntityId: entity?.id, indicatorId: indicator?.indicatorId]">
                                       <i class="icon-results icon-white"></i>
                                       ${message(code: 'results')}
                                   </opt:link>
                               </g:if>
                               <g:if test="${loadQueryFromDefault && query?.displayCopyDataInMainButtonsBar}">
                                   <a href="javascript:;" class="btn btn-primary"
                                      onclick="openImportFromDatasets('${entity?.id}', '${parentEntity?.id}', '${query?.queryId}', '${indicator?.indicatorId}', '${query?.localizedName}', '${Boolean.FALSE}', '${message(code:"import_from_dataset.no_design")}')"><i
                                           class="icon-tags"></i> <g:message code="import_generic_fdes"/></a>
                               </g:if>
                               <g:if test="${compareMaterialsAllowed || compareDataAllowedForMatSpecQuery}">
                                   <opt:renderCompareDataBtn parentEntity="${parentEntity}" materialCompareEntity="${compareEntity ?: entity.isMaterialSpecifierEntity ? entity : null}" maxRowLimit="${indicator?.maxRowLimitPerDesign}" querySave="${entity.isMaterialSpecifierEntity}" params="[parentEntityId:parentEntity?.id]" onclick="clickAndDisable('readOnlyResults')" elementId="compareButtonTotal" controller="entity" action="resourcesComparisonEndUser" class="btn btn-primary readOnlyResults disabledOnLoad preventDoubleSubmit compareButtonTotal" target="${entity.isMaterialSpecifierEntity ? '' : '_blank'}"/>
                                </g:if>

                           </g:else>
                       </g:if>
                       <g:else>
                           <g:if test="${!entity.isMaterialSpecifierEntity}">
                               <a class="btn btn-primary preventDoubleSubmit disabledOnLoad" id="resultBtn"
                                  onclick="submitQuery(null, 'queryForm', null, 'results', null, null, null, ${indicator?.maxRowLimitPerDesign});"
                                  rel="popover" data-trigger="hover"
                                  data-content="${message(code: 'query_form.results_popup')}">
                                   <i class="icon-results icon-white"></i> ${message(code: 'results')}
                               </a>
                           </g:if>
                           <g:if test="${loadQueryFromDefault && query?.displayCopyDataInMainButtonsBar}">
                               <a href="javascript:;" class="btn btn-primary"
                                  onclick="openImportFromDatasets('${entity?.id}', '${parentEntity?.id}', '${query?.queryId}', '${indicator?.indicatorId}', '${query?.localizedName}', '${Boolean.FALSE}', '${message(code:"import_from_dataset.no_design")}')"><i
                                       class="icon-tags"></i> <g:message code="import_generic_fdes"/></a>
                           </g:if>
                           <g:if test="${compareMaterialsAllowed || compareDataAllowedForMatSpecQuery}">
                               <opt:renderCompareDataBtn parentEntity="${parentEntity}" materialCompareEntity="${compareEntity ?: entity.isMaterialSpecifierEntity ? entity : null}" maxRowLimit="${indicator?.maxRowLimitPerDesign}" querySave="${entity.isMaterialSpecifierEntity}" params="[parentEntityId:parentEntity?.id]" elementId="compareButtonTotal" controller="entity" action="resourcesComparisonEndUser" class="btn btn-primary readOnlyResults disabledOnLoad preventDoubleSubmit compareButtonTotal" target="${entity.isMaterialSpecifierEntity ? '' : '_blank'}"/>
                           </g:if>
                       </g:else>
                   </g:if>
                    <g:if test="${modifiable && !projectLevel}">
                        <g:if test="${filterOnCriteria}">
                            <div class="btn-group" style="display: inline-block;"><a href="#" rel="popover"
                                                                                     data-trigger="hover"
                                                                                     data-content="${filterOnCriteria.localizedFilterText}"
                                                                                     data-toggle="dropdown"
                                                                                     class="btn btn-primary dropdown-toggle"><g:message
                                        code="filter"/><span class="caret"></span></a>
                                <ul class="dropdown-menu">
                                    <g:each in="${filterOnCriteria.choices}" var="filterChoice">
                                        <li><g:link action="form"
                                                    params="[entityId: parentEntity?.id, childEntityId: entity?.id, queryId: query?.queryId, indicatorId: indicator?.indicatorId, filterOnCriteria: filterChoice]"><g:if
                                                    test="${filterChoice.equals(session?.filterOnCriteria)}"><i
                                                        class="icon-done"></i></g:if> ${filterChoice}</g:link></li>
                                    </g:each>
                                </ul>
                            </div>
                        </g:if>
                        <g:if test="${(allowMonthlyData || allowQuarterlyData) && !indicator?.requireMonthly}">
                            <div class="btn-group" style="display: inline-block;"><a href="#" rel="popover"
                                                                                     data-trigger="hover"
                                                                                     data-toggle="dropdown"
                                                                                     class="btn btn-primary dropdown-toggle"><g:message
                                        code="query.periodicity"/><span class="caret"></span></a>
                                <ul class="dropdown-menu">
                                    <li><g:if test="${!monthlyInputEnabled && !quarterlyInputEnabled}"><a href="#"><i
                                            class="icon-done"></i> <g:message code="query.annual_data"/>
                                    </a></g:if><g:else><opt:link controller="query" action="enableAnnualInput"
                                                                 onclick="return modalConfirm(this);"
                                                                 data-titlestr="${message(code: 'warning')}"
                                                                 data-questionstr="${message(code: 'query.periodicity.warning')}"
                                                                 data-truestr="${message(code: 'ok')}"
                                                                 data-falsestr="${message(code: 'cancel')}"
                                                                 params="[indicatorId: indicator.indicatorId, childEntityId: entity?.id, queryId: query.queryId]"><i
                                                class="icon-"></i> <g:message
                                                code="query.annual_data"/></opt:link></g:else></li>
                                    <g:if test="${allowQuarterlyData}">
                                        <li><g:if test="${quarterlyInputEnabled}"><a href="#"><i
                                                class="icon-done"></i> <g:message code="query.quarterly_data"/>
                                        </a></g:if><g:else><opt:link controller="query" action="enableQuarterlyInput"
                                                                     onclick="return modalConfirm(this);"
                                                                     data-titlestr="${message(code: 'warning')}"
                                                                     data-questionstr="${message(code: 'query.periodicity.warning')}"
                                                                     data-truestr="${message(code: 'ok')}"
                                                                     data-falsestr="${message(code: 'cancel')}"
                                                                     params="[indicatorId: indicator.indicatorId, childEntityId: entity?.id, queryId: query.queryId]"><i
                                                    class="icon-"></i> <g:message
                                                    code="query.quarterly_data"/></opt:link></g:else></li>
                                    </g:if>
                                    <g:if test="${allowMonthlyData}">
                                        <li><g:if test="${monthlyInputEnabled}"><a href="#"><i
                                                class="icon-done"></i> <g:message code="query.monthly_data"/>
                                        </a></g:if><g:else><opt:link controller="query" action="enableMonthlyInput"
                                                                     onclick="return modalConfirm(this);"
                                                                     data-titlestr="${message(code: 'warning')}"
                                                                     data-questionstr="${message(code: 'query.periodicity.warning')}"
                                                                     data-truestr="${message(code: 'ok')}"
                                                                     data-falsestr="${message(code: 'cancel')}"
                                                                     params="[indicatorId: indicator.indicatorId, childEntityId: entity?.id, queryId: query.queryId]"><i
                                                    class="icon-"></i> <g:message
                                                    code="query.monthly_data"/></opt:link></g:else></li>
                                    </g:if>
                                </ul>
                            </div>
                        </g:if>
                        <g:if test="${!entity?.isMaterialSpecifierEntity}">
                            <div class="btn-group hide-on-print" style="display:inline-block;"><a href="#" data-toggle="dropdown" class="btn btn-primary dropdown-toggle"><g:message code="entity.show.designs_more"/> <span class="caret"></span></a>
                                <ul class="dropdown-menu">
                                    <g:if test="${userService.getSuperUser(user)}">
                                        <li>
                                            <a href="javascript:" onclick="activateDatasetVerification(this, '${message(code: "verificationMode.activated")}')">
                                                <asset:image class="verifiedDatasetIcon float-left" src="img/verifiedIcon.png"/>
                                                <span class="textBreakSpace"><g:message code="verifyDatasets" /></span>
                                            </a>
                                        </li>
                                    </g:if>
                                     <g:if test="${showAndJumpToVPointLicensed && isIndicatorHavingVerificationPoints}">
                                        <li>
                                            <a href="javascript:" class="${isQueryHavingVerificationPoints ? '' : 'removeClicks'}"
                                               onclick="showVerificationPoints()">
                                                <span>${isQueryHavingVerificationPoints ? message(code: 'show.vpoints') : message(code: 'no.vPoints')}</span>
                                            </a>
                                        </li>
                                         <li>
                                            <a href="javascript:"
                                               onclick="fetchVerificationPointsModal('${parentEntity?.id}', '${entity?.id}', '${indicator?.indicatorId}', '${query?.queryId}', '${message(code: "jump.to.vpoints")}', '${message(code: "loading")}')">
                                                <span><g:message code="jump.to.vpoints" /></span>
                                            </a>
                                        </li>
                                    </g:if>
                                    <g:if test="${downloadQueryDataLicensed && query.exportClasses}">
                                        <li><a href="javascript:" onclick="downloadQueryExcel('${parentEntity?.id}','${entity?.id}','${query?.queryId}','${indicator?.indicatorId}');"><g:message code="results.expand.download_excel" /></a>
                                            <input type="hidden" id="queryExcelDownloadInfo" value="${message(code: "query.excel_download_info")}">
                                            <input type="hidden" id="queryExcelDownloadButton" value="${message(code: "results.expand.download_excel")}">
                                            <input type="hidden" id="queryExcelDownloadCancel" value="${message(code: "cancel")}">
                                        </li>
                                    </g:if>
%{--                                    Deprecate code, to be removed by the end of feature 0.5.0 release--}%
%{--                                    <g:if test="${userService.getAccount(user) && sendMeEPDRequestLicensed}">--}%
%{--                                        <li><a href="#" class="${isLocked ? "grayLink" : ""}" disabled="${isLocked ? "disabled" : ""}" onclick="openSendMeData()"><i class="fas fa-file-contract"></i> <g:message code="send_me_data"/> <g:if test="${isLocked || preventChanges}"><i class="fa fa-lock grayLink"></i></g:if></a>--}%
%{--                                        </li>--}%
%{--                                    </g:if>--}%
                                    %{--Option to request RE2020 data from French entity--}%
                                    <g:if test="${userService.getAccount(user) && indicator.iniesDataRequestAPI}">
                                        <asset:javascript src="re2020.js"/>
                                        <li><a href="#" class="${isLocked ? "grayLink" : ""}" disabled="${isLocked ? "disabled" : ""}" onclick="openRe2020RequestData()"><i class="fas fa-file-contract"></i> <g:message code="re2020_request_data.message"/> <g:if test="${isLocked || preventChanges}"><i class="fa fa-lock grayLink"></i></g:if></a>
                                        </li>
                                    </g:if>
%{--                                    Deprecate code, to be removed by the end of feature 0.5.0 release--}%
%{--                                    <g:elseif test="${!userService.getAccount(user) && sendMeEPDRequestLicensed}">--}%
%{--                                        <li><a class="grayLink" disabled="disabled" rel="popover" data-trigger="hover" data-content="${message(code:'send_me_data.account_needed' )}"><i class="fas fa-file-contract"></i> <g:message code="send_me_data"/></a>--}%
%{--                                        </li>--}%
%{--                                    </g:elseif>--}%
                                    <li><a href="javascript:" onclick="window.print();"><i class="icon-print"></i> <g:message
                                            code="print"/></a>
                                    </li>
                                    <g:if test="${indicator?.indicatorHelpUrl}">
                                        <li><a href="${indicator?.indicatorHelpUrlTranslation}" target="_blank" class="helpLink"><i
                                                class="fa fa-question margin-right-5"></i> &nbsp; <g:message code="help.instructions"/></a></li>
                                    </g:if>
                                    <g:set var="lastUpdaterPopup"
                                           value="${queryRender.renderLastUpdater(entity: entity, queryId: query?.queryId)}"/>
                                    <g:if test="${lastUpdaterPopup}">
                                        <li class="divider"></li>
                                        <li>${lastUpdaterPopup}</li>
                                    </g:if>
                                </ul>
                            </div>
                        </g:if>
                    </g:if>
                    <g:if test="${supportedFilters && modifiable}">
                        <div class="btn-group hide-on-print" style="display:inline-block">
                            <a href="javascript:" id="filterData"
                               onclick="filterData('${message(code: 'quickFilter.show')}','${message(code: "quickfilter.warnignTitle")}', '${message(code : "quickFilter.warningText")}', '${message(code : "quickFilter.reset")}', '${message(code: "cancel")}');"
                               class="btn btn-primary"><i class="fa fa-filter" aria-hidden="true"></i>  <span id="filterButtonText"><g:message code="quickFilter.reset"/></span> <span id="quickFilterCaret" class="fa fa-caret-up"></span></a>
                        </div>
                    </g:if>

                    <g:if test="${!entity?.isMaterialSpecifierEntity}">
                        <g:set var="allowCarbonDesigner" value="${indicator?.allowedCarbonDesignerRegions != null}"/>
                        <g:if test="${(loadQueryFromDefault ||(importMapperLicensed && foundDesignImportMappers) || foundOperatingImportMappers || (simulationToolLicensed && allowCarbonDesigner) || giveTaskLicensed )&& modifiable && !projectLevel && !isLocked}">
                            <div class="btn-group hide-on-print" style="display:inline-block"><a href="#" rel="popover" data-trigger="hover" data-toggle="dropdown" class="btn btn-primary dropdown-toggle"><i class="fas fa-sign-in-alt white-font" style="font-size: 1.3em; vertical-align: middle;"></i>  <g:message code="import_data"/> <span class="caret"></span></a>
                                <ul class="dropdown-menu"><%--
                            --%><g:if test="${foundDesignImportMappers && importMapperLicensed}"><%--
                                --%><g:each in="${foundDesignImportMappers}" var="foundDesignImportMapper"><%--
                                    --%><li class="disableWhenCalcIsRunning"><g:link controller="importMapper" action="main" params="[applicationId: foundDesignImportMapper.applicationId, importMapperId: foundDesignImportMapper.importMapperId, indicatorId: indicator?.indicatorId, entityId: parentEntity?.id, childEntityId: entity?.id]"><i class="icon-">○</i> <g:message code="excel_gbxml"/></g:link></li><%--
                                --%></g:each><%--
                            --%></g:if><%--
                            --%><g:elseif test="${foundOperatingImportMappers}"><%--
                                --%><g:each in="${foundOperatingImportMappers}" var="foundOperatingImportMapper"><%--
                                    --%><li class="disableWhenCalcIsRunning"><g:link controller="importMapper" action="main" params="[applicationId: foundOperatingImportMapper.applicationId, importMapperId: foundOperatingImportMapper.importMapperId, indicatorId: indicator?.indicatorId, entityId: parentEntity?.id, childEntityId: entity?.id]"><i class="icon-">○</i> <g:message code="excel_gbxml"/></g:link></li><%--
                                --%></g:each><%--
                            --%></g:elseif><%--
                            --%><g:else><%--
                                --%><li><a href="javascript:;" onclick="noImportLicenseFound()"><i class="icon-">○</i> <g:message code="excel_gbxml" /></a></li><%--
                            --%></g:else><%--
                            --%><li class="disableWhenCalcIsRunning"><a target='_blank' rel='noopener noreferrer' href="https://oneclicklca.zendesk.com/hc/en-us/articles/360015040659"><i class="icon-">○</i> <g:message code="other_type" /></a></li>

                                    <g:if test="${simulationToolLicensed && allowCarbonDesigner && !parentEntity.readonly && !parentEntity.readonlyIndicatorIds?.contains(indicator.indicatorId)}">
                                        <li class="divider"></li>
                                        <li class="nowrap disableWhenCalcIsRunning">
                                            <g:if test="${!isLocked}">
                                                <opt:link controller="design" action="simulationTool" params="[entityId:parentEntity.id, indicatorId: indicator.indicatorId, designId: entity.id ]">
                                                    <g:if test="${draftPresent}">
                                                        <img style="width: 20px;  margin-left: -5px; overflow: visible;" src="/app/assets/simulationTool_icon/simulationTool_icon_green.png">
                                                        <g:message code="simulationTool.secondPage.heading"/>
                                                    </g:if>
                                                    <g:else>
                                                        <img style="width: 20px;  margin-left: -5px; overflow: visible;" src="/app/assets/simulationTool_icon/simulationTool_icon.png">
                                                        <g:message code="simulationTool.firstPage.heading"/>
                                                    </g:else>
                                                </opt:link>
                                            </g:if>
                                            <g:else>
                                                <a class="disabled">
                                                    <g:if test="${draftPresent}">
                                                        <img style="width: 20px;  margin-left: -5px; overflow: visible;" src="/app/assets/simulationTool_icon/simulationTool_icon_green.png">
                                                        <g:message code="simulationTool.secondPage.heading"/>
                                                    </g:if>
                                                    <g:else>
                                                        <img style="width: 20px;  margin-left: -5px; overflow: visible;" src="/app/assets/simulationTool_icon/simulationTool_icon.png">
                                                        <g:message code="simulationTool.firstPage.heading"/>
                                                    </g:else>
                                                </a>
                                            </g:else>
                                        </li>
                                    </g:if>
                                    <g:if test="${loadQueryFromDefault}">
                                        <li class="divider"></li>
                                        <li class="disableWhenCalcIsRunning"><a href="javascript:;" onclick="openImportFromDatasets('${entity?.id}', '${parentEntity?.id}', '${query?.queryId}', '${indicator?.indicatorId}', '${query?.localizedName}', '${Boolean.FALSE}', '${message(code:"import_from_dataset.no_design")}')"><i class="icon-tags"></i> <g:message code="import_from_datasets"/></a></li>
                                    </g:if>
                                    <g:if test="${giveTaskLicensed}">
                                        <g:if test="${task}">
                                            <li class="divider"></li>
                                            <li class="bold"><span style="padding: 3px 15px; display: block; clear: both; white-space: nowrap; font-weight: bold;"><i class="icon-">○</i> <g:message code="task.title"/></span></li>
                                            <li>
                                                <opt:link controller="task" action="form" id="${task?.id}"
                                                          params="[entityId: parentEntity?.id, childEntityId: entity?.id, queryId: query?.queryId, indicatorId: indicator?.indicatorId]">
                                                    <g:message code="query.task" args="[task.email, task.formattedDeadline]"/>
                                                </opt:link>
                                            </li>
                                        </g:if>
                                        <g:else>
                                            <li class="divider"></li>
                                            <li>
                                                <opt:link controller="task" action="form" id="${task?.id}"
                                                          params="[entityId: parentEntity?.id, childEntityId: entity?.id, queryId: query?.queryId, indicatorId: indicator?.indicatorId]">
                                                    <i class="icon-">○</i> <g:message code="task.title"/>
                                                </opt:link>
                                            </li>
                                        </g:else>
                                    </g:if>
                                </ul>
                            </div>
                        </g:if>
                        <g:elseif test="${!projectLevel}">
                            <g:set var="featureName"><g:message code="business" /> - <g:message code="import_from_file" /></g:set>
                            <g:if test="${isLocked || preventChanges}"><a href="javascript:" class="btn enterpriseCheck"><g:message code="import_data"/><i class="fa fa-lock grayLink" style="margin-left: 5px"></i></a></g:if>
                            <g:else>
                                <a href="javascript:" rel="popover" data-trigger="hover" class="btn enterpriseCheck" data-content="${message(code: 'enterprise_feature_warning', args:[featureName])}"><g:message code="import_data"/></a>
                            </g:else>
                        </g:elseif>

                    </g:if>
                    <g:else>
                        <div class="btn-group hide-on-print" style="display:inline-block">
                            <a href="#" rel="popover" data-trigger="hover" data-toggle="dropdown" class="btn btn-primary dropdown-toggle"><i class="fas fa-sign-in-alt white-font" style="font-size: 1.3em; vertical-align: middle;"></i>  <g:message code="import_data"/> <span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <g:if test="${loadQueryFromDefault}">
                                <li class="disableWhenCalcIsRunning"><a href="javascript:;" onclick="openImportFromDatasets('${entity?.id}', '${parentEntity?.id}', '${query?.queryId}', '${indicator?.indicatorId}', '${query?.localizedName}', '${Boolean.FALSE}', '${message(code:"import_from_dataset.no_design")}')"><i class="icon-tags"></i> <g:message code="import_from_datasets"/></a></li>
                            </g:if>
                            </ul>
                        </div>
                    </g:else>
                </div>

            %{--TODO: change to taglib --}%
                <opt:breadcrumbsNavBar indicator="${indicator}" parentEntity="${parentEntity}" childEntity="${entity}" indicatorLicenseName="${indicatorLicenseName}" query="${query}" step="${1}"/>
                <g:render template="/entity/basicinfoView"/>
            </g:if>
    <div class="container">
        <opt:renderPopUpTrial projectLevel="${projectLevel}" stage="queryPage" query="${query}" entity="${parentEntity}" currentDesign="${entity}"/>
    </div>
        <div class="screenheader" id="screenheader"  style= "background-color: #ffffff !important;">
            <g:if test="${allowedQueries && allowedQueries.size() > 1 && modifiable && !projectLevel}">
            <div style="height: 10px"></div>
                <ul class="nav nav-tabs " style="width: auto !important; margin-bottom: 5px !important" id="tabsNavigation">
                    <g:set var="showLockIcon" value="${isLocked || preventChanges}"/>
                    <g:each in="${allowedQueries}" var="queryItem">
                        <g:set var="activeTab" value="${queryItem?.queryId?.equals(query.queryId)}" />
                        <li class="navInfo${activeTab ? " active" : ""}">
                            <g:if test="${activeTab}">
                                <a href="javascript:" class="just_black queryNavButton">
                                    <g:if test="${entity?.queryReadyPerIndicator?.get(indicator.indicatorId)?.get(queryItem.queryId)}">
                                        <i class="icon-done"></i>
                                    </g:if>
                                    <g:else>
                                        <g:if test="${!indicator.isQueryOptional(queryItem?.queryId)}">
                                            <i class="icon-incomplete"></i>
                                        </g:if>
                                    </g:else>
                                    <g:set var="queryDisplayedName" value="${queryItem?.localizedName}${queryItem?.localizedAppendNameByEntityType?: ''}"/>
                                    <span class="queryDisplayedName" data-originalText="${queryDisplayedName}"
                                          data-activeTab="${activeTab}">${queryDisplayedName}</span>
                                    <g:if test="${showLockIcon}">
                                        <i class="fa fa-lock pull-right fa-size-medium-pad grayLink"></i>
                                    </g:if>
                                </a>
                            </g:if>
                            <g:else>
                                <g:set var="queryDisplayedName"
                                       value="${queryItem?.localizedName}${queryItem?.localizedAppendNameByEntityType ?: ''}"/>
                                <a href="javascript:" class="just_black queryNavButton disabledOnLoad"
                                   data-queryId="${queryItem?.queryId}"
                                   data-content="${queryDisplayedName}"
                                   onclick="submitQueryWithConfirmation('${queryItem?.queryId}');">
                                    <g:if test="${entity?.queryReadyPerIndicator?.get(indicator.indicatorId)?.get(queryItem.queryId)}">
                                        <i class="icon-done"></i>
                                    </g:if>
                                    <g:else>
                                        <g:if test="${!indicator.isQueryOptional(queryItem?.queryId)}">
                                            <i class="icon-incomplete"></i>
                                        </g:if>
                                    </g:else>
                                    <span class="queryDisplayedName" data-originalText="${queryDisplayedName}"
                                          data-activeTab="${activeTab}">
                                        ${queryDisplayedName}
                                    </span>
                                    <g:if test="${showLockIcon}">
                                        <i class="fa fa-lock pull-right fa-size-medium-pad grayLink"></i>
                                    </g:if>
                                </a>
                            </g:else>
                        </li>
                    </g:each>
                </ul>
        </g:if>
    <g:if test="${supportedFilters}">
        <div id="quickFilterOptions">
            <g:set var="random" value="${UUID.randomUUID().toString()}"/>
            <div class="screenheader">
                <table class="quickFilterTable">
                    <tbody>
                        <tr>
                            <td class="pull-right quickFilterFunnel"><i class="fa fa-filter fa-2x" aria-hidden="true"></i> <br/>
                            <span id="resetTdForFilter" style="vertical-align: middle;"><a href="javascript:" id="resetAllQueryFilters"><g:message code="query.reset_filter"/></a></span>
                            </td>
                            <g:each in="${supportedFilters}" var="queryFilter">
                                <sec:ifAnyGranted roles="${queryFilter.userClass}">
                                    <g:set var="random" value="${UUID.randomUUID().toString()}"/>
                                    <td id="${queryFilter.resourceAttribute}td" class="quickFilterTableTd text-center"><div class="btn-group quickFilterTableGroup" id="${queryFilter.resourceAttribute}btnGroup" style="display:inline-block">
                                    <span class="quickFilters bold" onclick="quickFilterChoices('${queryFilter.resourceAttribute}td','quickFilter${queryFilter.resourceAttribute}', '${queryFilter.resourceAttribute}', '${queryFilter.localizedName}', '${indicator?.indicatorId}','${query?.queryId}', '${entity?.id}', null, 'query', null, '${parentEntity?.id?.toString()}')"> ${queryFilter.localizedName}</span>
                                    <select class="quickFilterSelect" data-resourceAttribute="${queryFilter.resourceAttribute}" style="width:${queryFilter?.filterWidth ? queryFilter?.filterWidth : '100'}px" onchange="saveUserFilters();" id="quickFilter${queryFilter.resourceAttribute}">
                                    </select>
                                </div></td>
                                </sec:ifAnyGranted>
                            </g:each>
                            <td>
                                <span class="btn-group" style="border-left: 2px solid #ddd; margin-left: 10px; margin-right: 10px;
                                    height: 40px; display: inline-block; margin-top: 9px;"></span>
                            </td>
                            <td>
                                <div style="display: inline-block; width: 200px; margin-top: 9px;" id="saveAndResultButtonDiv">
                                    <g:if test="${modifiable && !entity?.locked}">
                                        <span class="btn-group" style="display: inline-block;">
                                            <span class="disabledSaveBtn" rel="popover" data-trigger="hover"
                                                data-content="${message(code: 'query.form.no.changes')}"
                                                style="display: inline-block;">
                                                    <a class="btn btn-primary preventDoubleSubmit disabledOnLoad disabledSaveOnLoad"
                                                       rel="popover"
                                                       name="saveBtn"
                                                       data-trigger="hover"
                                                       data-content="${message(code: 'query_form.save_changes_popup')}"
                                                       onclick="submitQuery('${query?.queryId}', 'queryForm', null, 'save', null,
                                                           null, null, ${indicator?.maxRowLimitPerDesign ?: 'null'}, null, false, true)">
                                                       ${message(code: 'save')}
                                                  </a>
                                            </span>
                                        </span>
                                    </g:if>
                                    <g:if test="${!projectLevel}">
                                        <g:if test="${!modifiable || userReadOnlyQuery}">
                                            <g:if test="${entity?.entityClass == Constants.EntityClass.OPERATING_PERIOD.toString()}">
                                                <opt:link class="btn btn-primary readOnlyResults disabledOnLoad"
                                                          rel="popover" data-trigger="hover"
                                                          data-content="${message(code: 'query_form.results_popup')}"
                                                          onclick="clickAndDisable('readOnlyResults')" controller="operatingPeriod"
                                                          action="results"
                                                          params="[childEntityId: entity?.id, indicatorId: indicator?.indicatorId]">
                                                    <i class="icon-results icon-white"></i> ${message(code: 'results')}
                                                </opt:link>
                                            </g:if>
                                            <g:else>
                                                <g:if test="${loadQueryFromDefault && query?.displayCopyDataInMainButtonsBar}">
                                                    <a href="javascript:;" class="btn btn-primary"
                                                       onclick="openImportFromDatasets('${entity?.id}', '${parentEntity?.id}', '${query?.queryId}', '${indicator?.indicatorId}', '${query?.localizedName}', '${Boolean.FALSE}', '${message(code:"import_from_dataset.no_design")}')"><i
                                                            class="icon-tags"></i> <g:message
                                                            code="import_generic_fdes"/></a>
                                                </g:if>
                                                <g:if test="${entity?.isMaterialSpecifierEntity}">
                                                    <opt:renderCompareDataBtn parentEntity="${parentEntity}"
                                                                              materialCompareEntity="${compareEntity ?:
                                                                                      entity.isMaterialSpecifierEntity ?
                                                                                              entity : null}"
                                                                              maxRowLimit="${indicator?.maxRowLimitPerDesign}"
                                                                              querySave="${entity.isMaterialSpecifierEntity}"
                                                                              params="[parentEntityId:parentEntity?.id]"
                                                                              elementId="renderCompareDataBtn"
                                                                              controller="entity"
                                                                              action="resourcesComparisonEndUser"
                                                                              class="btn btn-primary readOnlyResults disabledOnLoad preventDoubleSubmit"/>
                                                </g:if>
                                            </g:else>
                                        </g:if>
                                        <g:else>
                                            <g:if test="${entity?.isMaterialSpecifierEntity}">
                                                <opt:renderCompareDataBtn parentEntity="${parentEntity}" materialCompareEntity="${compareEntity ?: entity.isMaterialSpecifierEntity ? entity : null}" maxRowLimit="${indicator?.maxRowLimitPerDesign}" querySave="${entity.isMaterialSpecifierEntity}" params="[parentEntityId:parentEntity?.id]" elementId="renderCompareDataBtn" controller="entity" action="resourcesComparisonEndUser" class="btn btn-primary readOnlyResults disabledOnLoad preventDoubleSubmit"/>
                                            </g:if>
                                        </g:else>
                                    </g:if>
                                </div>

                            </td>
                        </tr>
                    </tbody>
                </table>
                <div class="alertMessageContent"></div>
                <g:if test="${constructionCreatedMessage}">
                    <div class="alert alert-success">
                        <button class="close" type="button" data-dismiss="alert">×</button>
                        <p class="entitySaveMessage">${constructionCreatedMessage}</p>
                    </div>
                </g:if>
                <div id="maxRowLimitExceededWarningContainer">
                    <g:if test="${maxRowLimitPerDesignReached}">
                        <div id='alert' class='alert alert-error'>
                           <button data-dismiss='alert' class='close' type='button'>×</button>
                           <strong>${maxRowLimitPerDesignReached}</strong>
                        </div>
                    </g:if>
                </div>
            </div>
        </div>
    </g:if>
        </div>
    </div>

    <div class="container section">
        <div>
                <g:if test="${queryService.getLocalizedhelpByEntityType(query, parentEntity?.entityClass)}">
                    <span class="help-block">${queryService.getLocalizedhelpByEntityType(query, parentEntity?.entityClass)}</span>
                </g:if>
                <g:elseif test="${query?.localizedHelp}"><span class="help-block">${queryService.getLocalizedHelp(query)}</span></g:elseif>
        </div>

        <div id="querySectionBody" class="sectionbody">
            <g:if test="${queryService.getLocalizedPurposeByEntityType(query, parentEntity?.entityClass)}">
                <p><asset:image src="img/infosign_small.png"/> ${queryService.getLocalizedPurposeByEntityType(query, parentEntity?.entityClass)}</p>
            </g:if>
            <g:elseif test="${query?.localizedPurpose}">
                <p><asset:image src="img/infosign_small.png"/> ${queryService.getLocalizedPurpose(query)}</p>
            </g:elseif>

        <%-- SCOPE and Lca Cheker SECTION --%>
        <g:if test="${lcaCheckerAllowed && query?.queryId == "buildingMaterialsQuery"}">
            <tmpl:/query/scopeLcaCheckerDiv entity="${(Entity)entity}" indicator="${(Indicator)indicator}"/>
        </g:if>
        <%-- SCOPE and Lca Cheker SECTION --%>

            <div class="${monthlyInputEnabled ? 'monthly_content' : ''}">
                <g:hiddenField name="entityId" value="${parentEntity?.id}"/>
                <g:hiddenField name="childEntityId" value="${entity?.id}"/>
                <g:hiddenField name="queryId" value="${query?.queryId}"/>
                <g:hiddenField name="indicatorId" value="${indicator?.indicatorId}"/>
                <g:set var="sectionNumber" value="${0}"/>
                <g:each in="${query?.sections}" var="section">
                    <g:set var="hideSectionOnProjectLevel" value="${section && projectLevelHideableQuestions &&
                            projectLevelHideableQuestions.containsKey(section.sectionId) &&
                            !projectLevelHideableQuestions.get(section.sectionId)}"/>
                    %{--if indicator exists, this is design level for query and we check limitations from indicator
                        if it's not, then it's Project level and we should check restrictions from projectLevelHideableQuestions--}%
                    <g:if test="${(indicator && !indicator?.hideSectionTotally(section)) || (!indicator && !hideSectionOnProjectLevel)}">
                        <g:render template="/query/section"
                                  model="[parentEntity                 : parentEntity, entity: entity, query: query, section: section, printable: printable, groupMaterialsAllowed: groupMaterialsAllowed,
                                          display                      : true, isMain: true, sectionNumber: ++sectionNumber, modifiable: modifiable, preventChanges: preventChanges,
                                          projectLevelHideableQuestions: projectLevelHideableQuestions, additionalQuestionLicensed: additionalQuestionLicensed, eolProcessCache: eolProcessCache]"/>
                    </g:if>
                </g:each>
            </div>
        </div>
    </div>
    <g:hiddenField name="projectLevel" value="${projectLevel}"/>
    <g:hiddenField name="newProject" value="${newProject}"/>
    <g:hiddenField name="periodLevelParamQuery" value="${periodLevelParamQuery}"/>
    <g:hiddenField name="designLevelParamQuery" value="${designLevelParamQuery}"/>
</g:uploadForm>
    <g:form action="addResourceToCompare" controller="util" autocomplete="off" name="compareForm" novalidate="novalidate">
        <g:hiddenField name="resourceList" value="${session?.getAttribute(parentEntity?.id?.toString()+"CompareResourceBasket") ?: ''}"/>
        <g:hiddenField name="parentEntityId" value="${parentEntity?.id}"/>
        <g:hiddenField name="originIndicatorId" value="${indicator?.indicatorId}"/>
    </g:form>
<g:if test="${resourceTypeFilter}">
    <div class="nestedSelectContainer hidden"><ul class="nestedSelect" id="nestedFilter${resourceTypeFilter?.resourceAttribute}"></ul></div>
</g:if>

<div class="overlay" id="myOverlay">
    <div class="overlay-content" id="splitViewOverLay">
    </div>
</div>

<div class="overlay" id="loaderOverlay">
    <div class="overlay-content" id="loaderOverlayContent">
    </div>
</div>
<div class="modal hide" id="noImportLicenseModal">
    <div class="modal-header"><button type="button" class="close" id="closeModal" data-dismiss="modal">&times;</button>
        <h2><g:message code="no_import_license_support.header"/></h2>
    </div>
    <div class="modal-body">
        <g:if test="${!importMapperLicensed && modifiable && !projectLevel}">
            <g:message code="no_import_license_support.body"/>
        </g:if>
        <g:else>
            <g:if test="${!modifiable && !projectLevel}">
                <g:message code="no_import_modifiable.body"/>
            </g:if>
            <g:elseif test="${isLocked}">
                <g:message code="no_import_entity_locked.body"/>
            </g:elseif>
            <g:else>
                <g:message code="no_import_indicator_support.body"/>
            </g:else>

        </g:else>

    </div>
</div>

<div class="modal hide bigModal" id="NMDProductEditModal">
    <div class="modal-header text-center"><button type="button" class="close" data-dismiss="modal">&times;</button>
        <div id="NMDProductEditModalHeading" style="margin-top: 10px; margin-bottom: 5px;" class="btn-group inliner"></div>
    </div>
    <div class="modal-body" style="display: none;" id="NMDProductEditModalError"></div>
    <div class="modal-body" style="min-height: 350px;" id="NMDProductEditModalBody"></div>
</div>

<div class="modal hide bigModal" id="NMDProductAddModal">
    <div class="modal-header text-center"><button type="button" class="close" data-dismiss="modal">&times;</button>
        <div id="NMDProductAddModalHeading" style="margin-top: 10px; margin-bottom: 5px;" class="btn-group inliner"></div>
    </div>
    <div class="modal-body" style="display: none;" id="NMDProductAddModalError"></div>
    <div class="modal-body" style="min-height: 350px;" id="NMDProductAddModalBody"></div>
</div>

<ul class="custom-menu">
    <li class="custom-menu-parent"><g:message code="selected" /> (<span id="customMenuSelectedAmount"></span>)</li>
    <g:if test="${groupMaterialsAllowed}">
        <li id="groupRowsAction" class="custom-menu-action groupDisabled" data-action="group"><img src='/app/assets/constructionTypes/structure.png' class='constructionType' style="pointer-events: none;" /> <g:message code="create_a_group" /></li>
    </g:if>
    <li id="deleteRowsAction" class="custom-menu-action" data-action="delete"><i class="icon-trash" style="pointer-events: none;"></i> <g:message code="" /></li>
</ul>
    <div class="modal large hide" id="importFromEntities"></div>
    <div class="modal large hide" id="sendMeDataModal"></div>
    <div class="modal large hide" id="re2020RequestDataModal"></div>


</div>
<script type="text/javascript">
    $(document).ready(function (){
        //== THESE NEED TO RUN FIRST
        <g:if test="${scrollToVP}">
        showVerificationPoints(false)
        scrollToVerificationPoint('#${scrollToVP}')
        </g:if>
        //==
        // for case when some resources are set for the question but required resource is missed
        updateQuestions();

        var compareForm = $("#compareForm")
        var resourceList = $("#resourceList")
        $(compareForm).submit(function(){
            event.preventDefault();
            var formValues= $(this).serialize();
            $.post("/app/sec/util/addResourceToCompare", formValues, function(data){
                // Display the returned data in browser
                if(data){
                    var content = data.output.addedResources + " resources added."
                    if(data.output.notAddedResources != 0){
                        content += data.output.notAddedResources + " resources already existed in comparison list."
                    }
                    Swal.fire({
                        icon: 'success',
                        title: "Sucessfully added resources to compare",
                        text: content,
                        showConfirmButton: false,
                        timer:2000
                    })
                    $(".compareButtonTotal").find(".compareBtnCountTotal").text(data.output.datasize)
                    $(resourceList).val("")
                    addResourcesToSession("", "${parentEntity?.id}")
                } else {
                    swal.fire({
                        icon: 'error',
                        title: "Error! Resources were not added to compare",
                        showConfirmButton: false,
                        timer:1000
                    })
                }

            });
            $(".compareBtnCount").text("")
            $(".compareButton").attr("data-value","").removeClass("highlighted-new highlighted-background").addClass("grayLink")
            return false
        })
        updateCountNumberCompareBasket($(resourceList).val().split(","),$(".compareBtnCount"),$(".compareButton"))
        const queryTabsConcatRan = concatenateQueryTabs()
        if (queryTabsConcatRan) {
            triggerPopoverOnHover('.queryNavButton')
        }

        if ($("#flashSave").length > 0) {
            $("#flashSave").on('click', function () {
                submitQuery('${query?.queryId}', 'queryForm', null, 'save', null, null, null,
                    ${indicator?.maxRowLimitPerDesign});
            });
        }
    })

    $("#closeModal").on('click', function(){
        $("#noImportLicenseModal").addClass("hide");
    });
    $("#closeModalLca").on('click', function(){
        $("#newProjectModalLca").addClass("hide");
        $(".modal-backdrop").addClass("hide")
    });

    $('.preventDoubleSubmit').on('click', function(e) {
        if (e.ctrlKey||e.shiftKey) {
            return false;
        }
    });

    var formChanged = false;

    $(document).ready(function () {

        <g:if test="${query?.allowedFeatures?.contains("multiRowSelector") && modifiable && !userReadOnlyQuery && !preventChanges && !isLocked}">
        // --------- CUSTOM CONTEXT MENU START ----------
        $.contextMenu({
            events: {
                preShow: function() {
                    // verified resource rows cannot be selected
                    var selectedRows = $('.selection-background').not('.verifiedDatasetBackground');
                    var amountOfSelectedRows = $(selectedRows).length;

                    if (amountOfSelectedRows) {
                        return true
                    } else {
                        return false
                    }
                }
            },
            selector: '.container',
            build: function($trigger, e) {
                // this callback is executed every time the menu is to be shown
                // its results are destroyed every time the menu is hidden
                // e is the original contextmenu event, containing e.pageX and e.pageY (amongst other data)
                // verified resource rows cannot be selected
                var selectedRows = $('.selection-background').not('.verifiedDatasetBackground');
                var amountOfSelectedRows = $(selectedRows).length;
                var unableToGroup = null;
                var questionIds = [];

                if (amountOfSelectedRows <= 20) {
                    for (var i = 0; i < amountOfSelectedRows; i++) {
                        var row = selectedRows[i];
                        var questionId = $(row).attr("data-questionId");

                        if ($(row).attr("data-parentidentifier")) {
                            unableToGroup = "${message(code: "ungroupable")}";
                            break;
                        } else if (questionIds.length && questionIds.indexOf(questionId) === -1) {
                            unableToGroup = "${message(code: "ungroupable")}";
                            break;
                        } else {
                            questionIds.push(questionId);
                        }
                    }
                } else {
                    unableToGroup = "${message(code: "ungroupable")} " + amountOfSelectedRows + " / 20";
                }

                var groupingText = "${message(code:"create_a_group")}"
                var groupingClass = "custom-menu-item"

                if (unableToGroup) {
                    groupingText = groupingText + " (" + unableToGroup + ")"
                    groupingClass = "custom-menu-item removeClicks"
                }

                let groupEditText = "${message(code: "groupEdit")}"
                let groupEditClass = "custom-menu-item"
                let ableToGroupEdit = isAbleToGroupEdit(selectedRows)
                if (!ableToGroupEdit) {
                    groupEditText += ' (${message(code: "unableToGroupEdit")})'
                    groupEditClass += ' removeClicks'
                }

                return {
                    callback: function(key, options) {
                        // verified resource rows cannot be selected
                        var rows = $('.selection-background').not('.verifiedDatasetBackground');

                        if ("copy" === key) {
                            $(rows).find('.copyResourceRowButton').not('.notCopyable').trigger('click');
                        } else if ("delete" === key) {
                            $(rows).find('.removeResourceRowButton').not('.notRemoveable').trigger('click');
                        } else if ("group" === key) {
                            <g:if test="${groupMaterialsAllowed}">
                            var manualIds = [];
                            var questionId, sectionId, queryId;

                            $.each($(rows), function (i, row) {
                                manualIds.push($(row).attr("data-manualidformoving"));
                                questionId = $(row).attr("data-questionId");
                                sectionId = $(row).attr("data-sectionId");
                                queryId = $(row).attr("data-queryId");
                                $(row).removeClass("selection-background");
                            });
                            openGroupingContainer("${entity?.id?.toString()}", "${indicator?.indicatorId}", queryId, sectionId, questionId, manualIds);
                            </g:if>
                        } else if (key.startsWith("move-")) {
                            var params = key.split("-")
                            moveRows(rows, params[2], params[1], '${query.queryId}', "${entity?.id?.toString()}", "${indicator?.indicatorId}")
                        } else if ("groupEdit" === key) {
                            fetchGroupEditModal(ableToGroupEdit, selectedRows, '${query.queryId}', '${entity?.id?.toString()}', '${indicator?.indicatorId}', '${message(code: "groupEdit")}', '${message(code: "loading")}', '${message(code: "update.values")}', '${message(code: "cancel")}', '${message(code: "unknownError")}', '${message(code: "groupEdit.selectSomething")}')
                        }
                    },
                    items: {
                        "heading": {name: "${message(code: "selected")} ("+amountOfSelectedRows+")", className: 'custom-menu-item', disabled: true},
                        "copy": {name: "${message(code:"copy")}", icon: "fas fa-tags", className: 'custom-menu-item'},
                        <g:if test="${groupMaterialsAllowed}">
                        "group": {name: groupingText, icon: "fas fa-plus-square", className: groupingClass},
                        </g:if>
                        "fold1": {
                            "name": "${message(code : "move_materials")}",
                            "icon": "fas fa-arrows-alt",
                            "className": "custom-menu-item",
                            "items": {
                                <g:each in="${query.sections}" var="section">
                                <g:if test="${!disabledSections?.contains(section?.sectionId)}">
                                "fold${section.sectionId}": {
                                    "name": "${querySectionService.getLocalizedShortName(section)}",
                                    "className": "custom-menu-item",
                                    "items": {
                                        <g:each in="${section.questions}" var="question">
                                        "move-${section.sectionId}-${question.questionId}": {"name": "${question.localizedQuestion}", "className": "custom-menu-item"},
                                        </g:each>
                                    }
                                },
                                </g:if>
                                </g:each>
                            }
                        },
                        "groupEdit": {name: groupEditText, icon: "fas fa-object-group", className: groupEditClass},
                        "sep1": "---------",
                        "delete": {name: "${message(code:"delete")}", icon: "fas fa-trash-alt", className: 'custom-menu-item'}
                    }
                };
            }
        });
        // --------- CUSTOM CONTEXT MENU END ----------

        document.onkeydown=function(evt){
            var keyCode = evt ? (evt.which ? evt.which : evt.keyCode) : event.keyCode;
            if(keyCode === 13) {
                // Submit form on enter press
                evt ? evt.preventDefault() : event.preventDefault();
            }
        }

        var selection = new Selection({
            // Class for the selection-area
            class: 'selection-area',
            mode: 'touch',
            startareas: ['html'],
            // All elements in this container can be selected
            selectables: ['.dragginContainer > tr'],
            // The container is also the boundary in this case
            boundaries: ['#queryForm'],
            scrollSpeedDivider: 10
        }).on('beforestart', function(inst) {
            // This function can return "false" to abort the selection
            if (inst.oe.button !== 0||$(inst.oe.target).is('.lockableDatasetQuestion, select, span, a, i, input, h1, h2, h3, h4, .sectionImpactContainer, .questionHeadText') || $(inst.oe.target).parents('.sourceListing-modal').length) {
                // To not trigger selection when dragging from resource name or clicking additionalQuestion link or the data card
                return false
            } else {
                // Clear cursor text selection
                var sel = window.getSelection ? window.getSelection() : document.selection;
                if (sel) {
                    if (sel.removeAllRanges) {
                        sel.removeAllRanges();
                    } else if (sel.empty) {
                        sel.empty();
                    }
                }
                return true
            }
        }).on('start', ({inst, selected, oe}) => {
            // Remove class if the user isn't pressing the control key or ⌘ key
            if (!oe.ctrlKey && !oe.metaKey) {

                // Unselect all elements
                for (const el of selected) {
                    el.classList.remove('selection-background');
                    inst.removeFromSelection(el);
                }
                // Clear previous selection
                inst.clearSelection();
            }
        }).on('move', ({changed: {removed, added}}) => {
            // Add a custom class to the elements that where selected.
            for (const el of added) {
                // Dont allow selecting constituents
                if (!el.dataset.dragmewithparent) {
                    el.classList.add('selection-background');
                }
            }

            // Remove the class from elements that where removed
            // since the last selection
            for (const el of removed) {
                el.classList.remove('selection-background');
            }

        }).on('stop', ({inst}) => {
            inst.keepSelection();
        });

        // If the document is clicked somewhere
        $(document).on("mousedown",function(e) {
            // Dont clear selection background if custom context menu is clicked.
            if (!e.ctrlKey && e.button !== 2 && !$(e.target).closest(".context-menu-list").length > 0) {
                $('.selection-background').removeClass("selection-background");
            }
        });

        $(".groupingCheckbox").on('click', function() {
            if (!$(this).is(":checked")) {
                $(".groupingCheckbox").prop("checked", false);
            } else {
                $(".groupingCheckbox").prop("checked", false);
                $(this).prop("checked", true);
            }
        });
        </g:if>

        var popOverSettings = {
            placement: 'bottom',
            container: 'body',
            html: true,
            template: '<div class="popover popover-fade" style=" display: block; max-width: 250px;"><div class="arrow"></div><div class="popover-content"></div></div>'
        };
        $(".questionHelpHoverover").popover(popOverSettings);

        var groupPopOverSettings = {
            placement: 'top',
            container: 'body',
            html: true,
            template: '<div class="popover popover-fade" style=" display: block; max-width: 300px;"><div class="arrow"></div><div class="popover-content"></div></div>'
        };
        $(".groupHelpPopover").popover(groupPopOverSettings);

        <g:if test="${designLevelParamQuery && !newProject}">
        $("#queryForm :input").on('change', function () {
            $("#queryForm").data("changed", true)
        });
        </g:if>
        //START -- enforce closing popover case Parent elem got deleted
        $('body').on('click', function(e){
                if($('.popover:visible') && $(e.target).data('toggle') !== 'popover' && $(e.target).parents('.popover.in').length === 0){
                var elem = $('.popover:visible')
                $(elem).popover('hide')
            }
        });
        //--END

        highLightElementTimeOut('.newResourceRow');
        highLightNewGroup('.newGroupCreated')

        draggableRows();
        $('.dragginContainer').each(function(){
            if ($(this).children().length > 0) {
                reSequenceStuff(this)
            }
        });
        DO_NOT_TRIGGER_CALC_QUESTIONIDS_LIST = [${doNotTriggerCalcQuestionIdsString}]
        trackChangesInResourceRows();

        <g:if test="${!preventChanges && modifiable}">
            initQueryAutocompletes();
            //initAdditionalQuestionAutocompletes();
            select2theSingleSelects('${message(code: 'query.choose_resource')}');

        <g:if test="${dataLoadingFeature}">
            var dataLoadingDatasets = [];
            <g:each in="${dataLoadingFeature.insertLoadedResourceParametersToQuestions}" var="question"> <g:set var="resourceParam" value="${question.keySet().toList().get(0)}"/>
            dataLoadingDatasets.push({resourceParameter: "${resourceParam}", sectionId: "${question.get(resourceParam)?.sectionId}", questionId: "${question.get(resourceParam)?.questionId}"});
            </g:each>

            $('#dataLoadingFeature').on('click', function (event) {
                var button = $(this);
                var indicatorId = button.attr("data-indicatorId");
                var entityId = button.attr("data-entityId");
                var queryId = button.attr("data-queryId");
                var definingSectionId = button.attr("data-definingSectionId");
                var definingQuestionId = button.attr("data-definingQuestionId");
                var targetSectionId = button.attr("data-targetSectionId");
                var targetQuestionId = button.attr("data-targetQuestionId");
                var quantityResolutionSectionId = button.attr("data-quantityResolutionSectionId");
                var quantityResolutionQuestionId = button.attr("data-quantityResolutionQuestionId");

                <g:if test="${existTranportMethod}">
                Swal.fire({
                    title: "${message(code:'warning')}",
                    text: "${dataLoadingFeature.localizedLoadWarning ?: ''}",
                    icon: "warning",
                    confirmButtonText: "${message(code: 'yes')}",
                    cancelButtonText: "${message(code: 'no')}",
                    confirmButtonColor: "red",
                    showCancelButton: true,
                    reverseButtons: true,
                    showLoaderOnConfirm: true,
                    preConfirm: function () {
                        return new Promise(function (resolve) {
                            dataLoadingFeaturePromise(resolve, queryId, entityId, indicatorId, definingSectionId, definingQuestionId, targetSectionId, targetQuestionId, quantityResolutionSectionId, quantityResolutionQuestionId, dataLoadingDatasets, '${dataLoadingFeature.buttonName?.get("EN")}', '${dataLoadingFeature.loadResourceParameterDecimals ?: 2}')
                        }).then(result => {
                            Swal.fire({
                                icon: 'success',
                                title: '${message(code: 'dataLoadingFeature.success')}',
                                html: '${message(code: 'dataLoadingFeature.success.info')}',
                                buttons: false,
                                timer: 1000
                            })
                        }).catch(function(error){
                            Swal.fire({
                                icon: 'error',
                                title: '${message(code: 'dataLoadingFeature.error')}',
                                html: '${message(code: 'dataLoadingFeature.error.info')}'
                            })
                        })
                    }
                });
                </g:if>
                <g:else>
                Swal.fire({
                    title: "${message(code:'info')}",
                    text: "${message(code:'local_transportation_first')}",
                    buttons: false,
                    timer: 3000,
                    customClass: 'myCustomClass',
                    onOpen: function () {
                        return new Promise(function (resolve) {
                            swal.showLoading();
                            dataLoadingFeaturePromise(resolve, queryId, entityId, indicatorId, definingSectionId, definingQuestionId, targetSectionId, targetQuestionId, quantityResolutionSectionId, quantityResolutionQuestionId, dataLoadingDatasets,'${dataLoadingFeature.buttonName?.get("EN")}', '${dataLoadingFeature.loadResourceParameterDecimals ?: 2}')
                        }).then(result => {
                            Swal.fire({
                                icon: 'success',
                                title: '${message(code: 'dataLoadingFeature.success')}',
                                html: '${message(code: 'dataLoadingFeature.success.info')}',
                                buttons: false,
                                timer: 1000
                            })
                        }).catch(function(error){
                            Swal.fire({
                                icon: 'error',
                                title: '${message(code: 'dataLoadingFeature.error')}',
                                html: '${message(code: 'dataLoadingFeature.error.info')}'
                            })
                        })
                    }
                });
                </g:else>
            });
        </g:if>

        var $NMDProductAddModal = $('#NMDProductAddModal');
        var $NMDProductEditModal = $('#NMDProductEditModal');

        $NMDProductAddModal.on('blur', '.nmd3X1, .nmd3X2', function() {
            validateNMDThicknesses(this);
        });
        $NMDProductEditModal.on('blur', '.nmd3X1, .nmd3X2', function() {
            validateNMDThicknesses(this);
        });

        var $parent = $('#queryForm');

        $parent.on('click', '.highlighted-background', function() {
            $(this).removeClass('highlighted-background');
        });

        $parent.on('click', '.newResourceRow', function() {
            $(this).removeClass('newResourceRow');
        });

        $parent.on('click', '.hideWarningQuery', function() {
            hideWarningQuery($(this).attr('data-warningThicknessId'));
        });

        $parent.on('change', "select[id*='parkingAvailabilityFactor']", function() {
            calculateParkingAvailabilityFactor($(this));
        });

        <g:if test="${indicator?.isCostCalculationAllowed}">
        var $commaThousandSeparation = ${commaThousandSeparation ? true : false};
        var $labourCostCraftsman = ${valueReferenceService.getDoubleValueForEntity(indicator?.labourCostCraftsmanValueReference, entity) ?: 0};
        var $labourCostWorker = ${valueReferenceService.getDoubleValueForEntity(indicator?.labourCostWorkerValueReference, entity) ?: 0};
        var $finalMultiplier = ${valueReferenceService.getDoubleValueForEntity(indicator?.countryIndexValueReference, entity) ?: 0};
        var $currencyMultiplier = ${valueReferenceService.getDoubleValueForEntity(indicator?.currencyExchangeValueReference, entity) ?: 0};
        var $currency = "${unitCostCurrency ?: ''}";
        var $totalCurrency = "${totalCostCurrency ?: ''}";
        var $costCalculationMethod = "${costCalculationMethod ?: ''}";

        $(function () {
            $.each($("input[data-isparentconstruction*='true']"), function () {
                var thisElem = $(this);
                var row = thisElem.closest('tr');
                var costPerUnitTotal = row.find('.costPerUnitTotal');
                var totalCostTotal = row.find('.totalCostTotal');

                    if (costPerUnitTotal.length && totalCostTotal.length) {
                        var construction = row.attr('data-parentidentifier');
                        var quantityInput = row.find('.isQuantity');
                        var userGivenQuantity = quantityInput.val();

                        var userInputFloat = parseFloat(userGivenQuantity);
                        if (userInputFloat && userInputFloat > 0) {
                            var totalCostTotalCost = 0;
                            $('*[data-constituent="totalCost.' + construction + '"]').each(
                                function () {
                                    totalCostTotalCost += parseFloat($(this).val());
                                }
                            );
                            if ($commaThousandSeparation) {
                                costPerUnitTotal.text((totalCostTotalCost / userInputFloat).toFixed(2));
                            } else {
                                costPerUnitTotal.text((totalCostTotalCost / userInputFloat).toFixed(2).replace('.',','));
                            }

                            if ($commaThousandSeparation) {
                                totalCostTotal.text(totalCostTotalCost.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                            } else {
                                totalCostTotal.text(totalCostTotalCost.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " "));
                            }
                        }
                    } else if (totalCostTotal.length && !costPerUnitTotal.length) {
                        var totalCostToFormat = totalCostTotal.text();

                        if (totalCostToFormat) {
                            if ($commaThousandSeparation) {
                                totalCostTotal.text(totalCostToFormat.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                            } else {
                                totalCostTotal.text(totalCostToFormat.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " "));
                            }
                        }
                    }
                });
            });

            $parent.on('input','.isQuantity', function() {
                doCostCalculation($(this), '${message(code: 'query.cost_calcalculation_failed')}', $labourCostCraftsman, $labourCostWorker, $finalMultiplier, $currencyMultiplier, $currency, $totalCurrency, $commaThousandSeparation, $costCalculationMethod);
            });

            $parent.on('input', '.totalCostInput', function() {
                var start = this.selectionStart;
                end = this.selectionEnd;
                var val = $(this).val();
                if (val) {
                    $(this).popover('hide');
                }
                if (isNaN(val)) {
                    val = val.replace(/[^0-9\.\,\-]/g, '');

                    if (val.split('.').length > 2) {
                        val = val.replace(/\.+$/, '');
                    }

                    if (val.split(',').length > 2) {
                        val = val.replace(/\,+$/, '');
                    }
                    var dotsAndCommas = ((val.match(/\./g) || []).length + (val.match(/,/g) || []).length);

                    if (dotsAndCommas > 1) {
                        if (val.indexOf('.') < val.indexOf(',')) {
                            val = val.replace('.', '');
                        } else {
                            val = val.replace(',', '');
                        }
                    }
                }
                $(this).val(val);
                this.setSelectionRange(start, end);
                var row = $(this).closest('tr');
                $(row).find(".costPerUnitTest").text('-');
                $(row).find(".costPerUnitHidden").val('-');
                $(row).find(".lccalcTooltip").addClass('hidden');
            });

            $parent.on('input','.userGivenCustomCostTotalMultiplier', function() {
                var start = this.selectionStart;
                end = this.selectionEnd;
                var val = $(this).val();
                if (val) {
                    $(this).popover('hide');
                }
                if (isNaN(val)) {
                    val = val.replace(/[^0-9\.\,\-]/g, '');

                    if (val.split('.').length > 2) {
                        val = val.replace(/\.+$/, '');
                    }

                    if (val.split(',').length > 2) {
                        val = val.replace(/\,+$/, '');
                    }
                    var dotsAndCommas = ((val.match(/\./g) || []).length + (val.match(/,/g) || []).length);

                    if (dotsAndCommas > 1) {
                        if (val.indexOf('.') < val.indexOf(',')) {
                            val = val.replace('.', '');
                        } else {
                            val = val.replace(',', '');
                        }
                    }
                }
                $(this).val(val);
                this.setSelectionRange(start, end);
                doCostCalculation($(this), '${message(code: 'query.cost_calcalculation_failed')}', $labourCostCraftsman, $labourCostWorker, $finalMultiplier, $currencyMultiplier, $currency, $totalCurrency, $commaThousandSeparation, $costCalculationMethod);
            });

            $parent.on('change','.userGivenUnit', function() {
                doCostCalculation($(this), '${message(code: 'query.cost_calcalculation_failed')}', $labourCostCraftsman, $labourCostWorker, $finalMultiplier, $currencyMultiplier, $currency, $totalCurrency, $commaThousandSeparation, $costCalculationMethod);
            });
        </g:if>
        </g:if>

        <g:if test="${supportedFilters}">
            quickFiltersSelect2('${message(code: 'quickFilter.placeholder')}');
            $('body').on({
                mouseenter: function () {
                    var highlighted_item = $(this);
                    if ($(this).attr("id").indexOf("resourceType") !== -1) {
                    var select2Box = $('.nestedSelectContainer');
                    new Tether({
                        element: select2Box,
                        target: highlighted_item,
                        attachment: 'top left',
                        targetAttachment: 'top left',
                        targetOffset:"0, ${resourceTypeFilter?.filterWidth?.toInteger() ? resourceTypeFilter?.filterWidth?.toInteger() - 30: '130'}px"

                    });

                    var resourceType = $(highlighted_item).attr('data-resourceTypeId');
                    var country = $(highlighted_item).attr('data-resourceattributevalue');
                        if ($(highlighted_item).attr("data-haschildren") === "true") {
                            nestedFilterChoices('nestedFilter${resourceTypeFilter.resourceAttribute}', '${resourceTypeFilter.resourceAttribute}', resourceType, '${query?.queryId}', '${indicator?.indicatorId}', null, '${parentEntity?.id?.toString()}', country, highlighted_item);
                        }else {
                            $(select2Box).addClass('hidden');
                        }
                    } else if($(this).attr("id").indexOf("areas") !== -1 ){
                        var select2Box = $('.nestedSelectContainer');
                        new Tether({
                            element: select2Box,
                            target: highlighted_item,
                            attachment: 'top left',
                            targetAttachment: 'top left',
                            targetOffset:"0, ${areaFilter?.filterWidth?.toInteger() ? areaFilter?.filterWidth?.toInteger() - 30: '300'}px"

                        });

                        var resourceType = $(highlighted_item).attr('data-resourceTypeId');
                        var country = $(highlighted_item).attr('data-resourceattributevalue');
                        if ($(highlighted_item).attr("data-haschildren") === "true") {
                            nestedFilterChoices('nestedFilter${resourceTypeFilter.resourceAttribute}', '${areaFilter?.resourceAttribute}', resourceType, '${query?.queryId}', '${indicator?.indicatorId}', null, '${parentEntity?.id?.toString()}', country, highlighted_item);
                        }else {
                            $(select2Box).addClass('hidden');
                        }
                    }
                }
            }, '.select2-results__option--highlighted');

            $('body').on({
                click: function () {
                    var select2Box = $('.nestedSelectContainer');
                    $(select2Box).addClass('hidden');

                }
            });

            $('.nestedSelectContainer').on({
                mouseleave: function () {
                    var select2Box = $('.nestedSelectContainer');
                    $(select2Box).addClass('hidden');
                }
            });

            $('.quickFilters').trigger('click');
            //$('#filterData').trigger('click');

        </g:if>

        $('#resetAllQueryFilters').on("click", function() {
            $('.quickFilterSelect').empty();
            saveUserFilters();
            var quickFilterTds = $('.quickFilterTableGroup');
            $(this).hide();
            $('.quickFilterTableTd').addClass("removeClicks");
            $('.quickFilterTableTd').removeClass('filterChosenHighlight');

            $('#resetTdForFilter').append('<i id="loadingReset" class="fa fa-cog fa-2x fa-spin primary"></i>');
            setTimeout(function () {
                $('.quickFilters').trigger("click");
                $('#loadingReset').remove();
                $('#resetAllQueryFilters').show();
                $('.quickFilterTableTd').removeClass("removeClicks");
            },1000);
        });

        if (!detectmob()) {
            // Stick the #nav to the top of the window
            var nav = $('#screenheader');
            var screenheader = $('#screenheader');
            var navHomeY = nav.offset().top;
            var isFixed = false;
            var $w = $(window);
            var btnGroupToFix = $("#saveAndResultButtonDiv")
            $w.on('scroll', function () {
                var scrollTop = $w.scrollTop();
                var shouldBeFixed = scrollTop > navHomeY;

                if (shouldBeFixed && !isFixed) {
                    nav.css({
                        position: 'fixed',
                        top: 0,
                        'z-index': '99',
                        left: nav.offset().left,
                        width: nav.width()
                    });
                    screenheader.css({'margin-top': '0'});
                    isFixed = true;
                    if($(btnGroupToFix).length > 0){
                        $(btnGroupToFix).css({position: 'fixed', 'margin-top': '-5px'})
                    }
                }
                else if (!shouldBeFixed && isFixed) {
                    nav.css({
                        position: 'static',
                        'z-index': '0',
                        top: navHomeY

                    });
                    if($(btnGroupToFix).length > 0){
                        $(btnGroupToFix).css({position: 'absolute'})
                    }
                    isFixed = false;
                    screenheader.css({'margin-bottom': '0'});
                }
            });
            $(".mainmenu").on('click', function (event) {
                $(window).scrollTop(0);
            });
            $w.on('resize', function () {
                $(nav).attr('style','');
                nav.css({
                    position: 'static'
                });
                isFixed = false;
                screenheader.css({'margin-bottom': '0'});
                $(this).scroll();
            });
        }
        var helpPopSettings = {
            placement: 'top',
            template: '<div class="popover popover-fade" style="display: block; max-width: 300px;"><div class="arrow"></div><div style="font-weight: normal !important;" class="popover-content"></div></div>'
        };
        $(".tableHeadingPopover[rel=popover]").popover(helpPopSettings)
        ignoreWarningPopover();
});
<g:if test="${query?.allowedFeatures?.contains("showCarbonData") && user}">
    $(window).on('load', function () {
        switchOnCarbonSpinners();
        let carbonValues = $('.carbonValue');
        const size = $(carbonValues).length;
        let isCalculationRunning = $("#isCalculationRunning").val() === 'true'
        spinnersOnSaveBtn(isCalculationRunning)

        if (size && !isCalculationRunning) {
            $(document).on('click', function (e) {
                $('span.carbonDataImpact[data-original-title], a.calculationFormula[data-original-title]').each(function () {
                    if (!$(this).is(e.target) && $(this).has(e.target).length === 0 && $('.popover').has(e.target).length === 0) {
                        $(this).popover('hide');
                    }
                });
            });

            let unit = $(carbonValues).first().attr('data-unit');
            let list = $(carbonValues).map(function() {return $(this).attr('data-manualId')}).get().join();
            const queryString = 'childEntityId=${entity.id}' + '&indicatorId=${indicator?.indicatorId}' + '&unit=' + unit + '&rows=' + list;
            getImpactsPerRow(queryString, size, unit)
        } else {
            setTimeout(function () {
                sortableResourceTables();
                appendSortignHeads();
            }, 500);
        }

        getSectionImpacts();
    });

    function getSectionImpacts(){
        $('.sectionImpactContainer').each(function () {
            var elem = this;
            var sectionId = $(this).attr('data-sectionId');
            var queryString = 'entityId=${entity.id}' + '&indicatorId=${indicator?.indicatorId}' + '&sectionId=' + sectionId + '&queryId=${query?.queryId}';
            $.ajax({
                url: '/app/sec/query/getImpactsPerSection',
                async: true,
                data: queryString,
                type: 'POST',
                success: function (data) {
                    $(elem).empty().append(data.output)
                }
            });
        });
    }

    function getImpactsPerRow(queryString, size, unit){
        $.ajax({
            url: '/app/sec/query/getImpactsPerRow',
            data: queryString,
            type: 'POST',
            success: function (data) {
                if (data.carbonDataImpactPerRow) {
                    let index = 0;
                    $.each(data.carbonDataImpactPerRow, function( key, value ) {
                        let carbonSpan = $('#carbonValue' + key);
                        let carbonPercenSpan = $("#carbonPercentageValue" + key)

                        if ($(carbonSpan).length) {
                            let tdForSort = $(carbonSpan).parent().parent();

                            if (value) {
                                let dataForSort = parseFloat(value.replace(/\s/g, '').replace(/\,/g,'.'));
                                if(data.impactUnitMap[key]=="t"){
                                    dataForSort *= 1000;
                                }
                                if (dataForSort) {
                                    $(tdForSort).attr('data-sort-value', dataForSort);
                                } else {
                                    $(tdForSort).attr('data-sort-value', 0);
                                }
                                $(carbonSpan).empty().append(value);
                                const percentageValue = data.carbonDataPercentagePerRow[key]
                                $(carbonPercenSpan).empty().append(percentageValue)
                                $(carbonSpan).parent().addClass('fakeLink');
                                const parentTd = $(carbonSpan).parent();
                                $(parentTd).off('click');
                                $(parentTd).on('click', function () {
                                    showBreakDownByStage(this, 'childEntityId=${entity.id}' + '&indicatorId=${indicator?.indicatorId}' + '&unit=' + unit + '&manualId=' + key);
                                });
                            } else {
                                $(tdForSort).attr('data-sort-value', 0);
                                $(carbonSpan).empty();
                                $(carbonPercenSpan).empty();
                            }
                        }

                        index++;

                        if (index === size) {
                            setTimeout(function () {
                                sortableResourceTables();
                                appendSortignHeads();
                            }, 500);
                        }
                    });
                }
            }
        });
    }
</g:if>
<g:else>
    $(window).on('load', function () {
        setTimeout(function () {
            sortableResourceTables();
            appendSortignHeads();
        }, 500);
    });
</g:else>
    $(window).on('load', function () {
        findAndShowBrokenConstructions();
        disableAllSubmitButtons();
        let enableSaveBtn = ${entity?.resultsOutOfDate.asBoolean()};
        isCalculationRunningFunction(null, enableSaveBtn);
        // for construction children inheritance
        var parentAdditionalQuestions = $('[data-inherit]');
        if (parentAdditionalQuestions.length) {
            $(parentAdditionalQuestions).each(function () {
               var identifier = $(this).attr('data-inherit');
               $(this).children().on('change', function () {
                   var elementType = this.nodeName;
                   inheritSelectValueToChildren(identifier, $(this).val(), elementType);
               });
            });
        }
        $.ajax({
            url: '/app/sec/query/getDataQualityWarning',
            async: true,
            data: "entityId=${entity?.id?.toString()}&indicatorId=${indicator?.indicatorId}&queryId=${queryId}",
            type: 'POST',
            success: function (data) {
                $("#dataQualityWarningDiv").html(data.globalQualityWarning);

                // Add alert icon to the dummy (locally made) constructions with invalid constituents.
                if (data.filteredOutManualIds) {
                    $.each(data.filteredOutManualIds, function (index, filteredOutManualId) {
                        var resourceNameCell = $("tr[data-manualidformoving='" + filteredOutManualId + "'] td.resourceRowNameCell");

                        if (resourceNameCell.length && (resourceNameCell.find("a.localDataQualityWarning").length === 0)) {
                            resourceNameCell.append('<a class="localDataQualityWarning" rel="popover"><i class="icon-alert"></i></a>');
                        }
                    });

                    $("a.localDataQualityWarning[rel=popover]").popover({
                        content: '${g.message(code: 'query.resource.warning.local.filterCriteriaFailure', default: 'Data incompatible with the used calculation tool.')}',
                        trigger: 'hover',
                        html: true,
                        placement: 'top'
                    });
                }
            }
        });
        $.ajax({
            url: '/app/sec/query/getMissmatchingDataWarning',
            async: true,
            data: "entityId=${entity?.id?.toString()}&queryId=${queryId}",
            type: 'POST',
            success: function (data) {
                $("#missmatchingDataWarningDiv").empty().append(data.output)
            }
        });

        $('.disabledOnLoad').removeClass('disabledOnLoad');
        // Disabled Save button until form has changes function (checks if new LCAPara Page and removes)
        $('.disabledOnLoadNav').removeClass('disabledOnLoadNav');
        $('#queryForm').on('input change', function(event) { //Checks form for any changes
            if(!$(event.target).hasClass("quickFilterSelect")){
                enableSaveButtons();
            }
        });

        triggerNotificationMessage()
    });

    function enableSaveButtons() {
        const disabledSaveOnLoad = $('.disabledSaveOnLoad');
        const disablePopoverOnDiv = $('.disabledSaveBtn');
        $(disablePopoverOnDiv, disabledSaveOnLoad).popover('destroy').removeAttr("rel").removeAttr("data-trigger").removeAttr("data-content").removeAttr("data-original-title").removeAttr("title");
        $(disabledSaveOnLoad).removeClass('disabledSaveOnLoad');
    }

    const msg = "${g.message(code: 'query.save_changes_alert')}";
    let isDirty = false;

    function triggerNotificationMessage(){
        $("#queryForm").on('change', function (event) {
            if (!$(event.target).hasClass("quickFilterSelect") && !isDirty) {
                isDirty = true;
            }
        });

        $("#queryForm").on('submit', function () {
            isDirty = false;
            return true;
        });

        window.onbeforeunload = function (e) {
            console.log("isDirty: " + isDirty)

            if (isDirty) {
                return msg;
            }
        };

        $("#datasetImport").on('submit', function (e) {
            if (isDirty) {
                return msg;
            }
        });
    }

    function submitQueryWithConfirmation(queryId) {
        let isCalculationRunning = $("#isCalculationRunning").val() === 'true';
        let submitQueryFunction = function () {
            submitQuery(queryId, 'queryForm',
                null, null, null, null, null, ${indicator?.maxRowLimitPerDesign ?: 'null'}, null, false);
        };

        if (isCalculationRunning && isDirty) {
            Swal.fire({
                title: "${message(code:'entity.calculation.running.datalosing.title')}",
                html: "${message(code: 'entity.calculation.running.datalosing')}",
                icon: "warning",
                dangerMode: true,
                confirmButtonText: "${message(code: 'entity.calculation.running.datalosing.leave')}",
                cancelButtonText: "${message(code: 'entity.calculation.running.datalosing.cancel')}",
                confirmButtonColor: "red",
                cancelButtonColor: "#78b200",
                showCancelButton: true,
                reverseButtons: true,
                allowOutsideClick: false,
                width: '44rem'
            }).then((result) => {
                if (result.isConfirmed) {
                    submitQueryFunction.call();
                }
            });
        } else {
            submitQueryFunction.call();
        }
    }

    let resourceRowsToReplace = null;

    function ajaxDesignFormSubmit(){
        const formData = new FormData(document.forms.namedItem("queryForm"));
        const triggerCalculationValue = $("#triggerCalculation").val()

        if(triggerCalculationValue == 'true'){
            $("#isCalculationRunning").val("true")
            $("#triggerCalculation").val('false')
            disableAllResourcesForUpdate()
            hideResourceMovers()
            switchOnCarbonSpinners()
            permWarningAlert('${g.message(code: "entity.calculation.running")}')
        }

        isDirty = false
        formChanged = false
        $('input[name="save"]').remove();
        spinnersOnSaveBtn(true)
        disableAllSubmitButtons()

        resourceRowsToReplace = $(".queryResourceRow:not(.existingResourceRow)");

        $.ajax({
            url:"${createLink(controller:'query',action:'saveQueryRemotely')}",
            type:'POST',
            data: formData,
            processData: false,  // tell jQuery not to process the data
            contentType: false,
            timeout:600000,
            success:function (req) {
                isCalculationRunningFunction(triggerCalculationValue);
                updateQuestions();
            },
            error: function(error, textStatus){
                console.log(error+ ": "+ textStatus);
                resourceRowsToReplace = null;
                isCalculationRunningFunction(triggerCalculationValue);
            }
        });

        return false
    }

    const intervalTime = 2000
    let checkMessagesTimeout
    let isCarbonPreviouslyCalculated = true

    function isCalculationRunningFunction(triggerCalculation, enableSaveBtn){
        const allSubmitButtons = $('.preventDoubleSubmit');
        const allSaveButtons = $("a[name='saveBtn']");

        $.ajax({
            url: "/app/sec/entity/isCalculationRunning",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({
                entityId: '${entity?.id?.toString() ?: parentEntity?.id?.toString()}',
                indicatorId: '${indicator?.indicatorId}'
            }),
            type:"POST",
            success: function(data,textStatus){
                const isCalculationRunning = data.output

                if(isCalculationRunning){
                    if (isCarbonPreviouslyCalculated){
                        isCarbonPreviouslyCalculated = false
                        $("#isCalculationRunning").val("true")
                        disableAllSubmitButtons()
                        spinnersOnSaveBtn(true)
                        <g:if test="${!projectLevel}">
                        if (triggerCalculation == 'true' && $('.carbonValue').find('.fa-spin').length == 0) {
                            switchOnCarbonSpinners()
                        }
                        </g:if>
                    }

                    checkMessagesTimeout = setTimeout(function(){
                        isCalculationRunningFunction();
                    }, intervalTime);
                } else {
                    $("#isCalculationRunning").val("false")
                    closeFlashAlert('permWarningAlert');
                    <g:if test="${!projectLevel}">

                    if (enableSaveBtn){
                        enableSaveButtons();
                    } else if (!formChanged) {
                        $(allSaveButtons).addClass("disabledSaveOnLoad");
                    }
                    //Tree possbile cases for result update:
                    // 1) When we trigger calculation
                    // 2) When page loaded and response informed that calculation still in progress
                    // 3) When calculation was in progress when we were in controller, but already finished after page opening
                    if (triggerCalculation == 'true' || !isCarbonPreviouslyCalculated ||
                        $('.carbonValue').find('.fa-spin').length > 0){
                        reloadNewAddedResourceRows();

                        let carbonValues = $('.carbonValue');
                        let unit = $(carbonValues).first().attr('data-unit');
                        let list = $(carbonValues).map(function () {
                            return $(this).attr('data-manualId')
                        }).get().join();
                        const queryString = 'childEntityId=${entity?.id}' + '&indicatorId=${indicator?.indicatorId}' + '&unit=' + unit + '&rows=' + list;

                        <g:if test="${query?.allowedFeatures?.contains("showCarbonData") && user}">
                        switchOffCarbonSpinners()
                        getImpactsPerRow(queryString, $(carbonValues).length, unit)
                        getSectionImpacts()
                        </g:if>
                        getCalculationMessageResultAndClearIt()
                    }
                    </g:if>

                    spinnersOnSaveBtn(false)
                    enableAllSubmitButtons()
                    isCarbonPreviouslyCalculated = true
                }
            },
            error: function(error, textStatus){
                console.log(error+ ": "+ textStatus)
                checkMessagesTimeout = setTimeout(function(){
                    isCalculationRunningFunction();
                }, intervalTime);
            }
        })
    }

    function switchOnCarbonSpinners(){
        const carbonSpinner = "<i class='fas fa-circle-notch fa-spin oneClickColorScheme'></i>"

        $('.carbonValue').each(function () {
            $(this).empty().append(carbonSpinner)
        });

        $(".carbonPercentageValue").each(function () {
            $(this).empty();
        });
    }

    function switchOffCarbonSpinners(){
        $(".carbonValue").empty();
        $(".carbonPercentageValue").empty()
    }

    function spinnersOnSaveBtn(enable){
        const carbonSpinner = "<i class='fas fa-circle-notch fa-spin'></i>"

        if (enable) {
            if (!$("a[name='saveBtn']").find(".fa-spin").length){
                $("a[name='saveBtn']").append(carbonSpinner)
            }
        } else {
            $("a[name='saveBtn']").find(".fa-spin").remove()
        }
    }

    function disableAllSubmitButtons(){
        $(".preventDoubleSubmit").each(function () {
            if (!$(this).hasClass("removeClicks")) {
                $(this).addClass("removeClicks")
            }
        });

        $(".disableWhenCalcIsRunning").each(function () {
            if (!$(this).hasClass("disabled")) {
                $(this).addClass("disabled")
                $(this).find("a").addClass("removeClicks")
                $(this).popover({
                    trigger:'hover',
                    content:'${g.message(code: "entity.calculation.running.title")}',
                    placement:'left'
                });
            }
        });
    }

    function enableAllSubmitButtons(){
        $(".preventDoubleSubmit").each(function () {
            $(this).removeClass('removeClicks');
        });

        $(".disableWhenCalcIsRunning").each(function () {
            $(this).removeClass('disabled');
            $(this).find("a").removeClass("removeClicks")
            $(this).popover('destroy').removeAttr("rel").removeAttr("data-trigger")
                .removeAttr("data-content").removeAttr("data-placement").removeAttr("data-container");
        });
    }

    function getCalculationMessageResultAndClearIt() {
        $.ajax({
            url: "/app/sec/entity/getCalculationMessageResultAndClearIt",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({
                entityId: '${entity?.id?.toString()}',
                indicatorId: '${indicator?.indicatorId}'
            }),
            type:"POST",
            success: function(data, textStatus){
                console.log(textStatus)
            },
            error: function(error, textStatus){
                console.log(error+ ": "+ textStatus)
            }
        })
    }

    function disableAllResourcesForUpdate(){
        $(".queryResourceRow:not(.existingResourceRow)").each(function () {
            $(this).addClass("removeClicks")
        });
    }

    function reloadNewAddedResourceRows() {
        const newResourceRows = resourceRowsToReplace || $();
        resourceRowsToReplace = null;
        let datasetIds = []

        newResourceRows.each(function() {
            datasetIds.push($(this).attr('data-manualidformoving'))
        });

        if (datasetIds.length > 0) {
            $.ajax({
                url: "/app/sec/util/getResourceRowsRepresentation",
                contentType: "application/json; charset=utf-8",
                async: false,
                data: JSON.stringify({
                    entityId: '${entity?.id?.toString()}',
                    indicatorId: '${indicator?.indicatorId}',
                    queryId: '${query?.queryId}',
                    datasetIds: Array.from(datasetIds)
                }),
                type: "POST",
                success: function (data, textStatus) {
                    const result = data.output

                    if (Object.keys(result).length > 0) {
                        const resultMap = new Map(Object.entries(result))

                        newResourceRows.each(function () {
                            const manualId = $(this).attr('data-manualidformoving')
                            const resourceRowElement = resultMap.get(manualId)

                            if (resourceRowElement.length > 0) {
                                $(this).replaceWith(resourceRowElement);
                            } else {
                                emptyCarbonData($(this).attr('id'))
                            }
                        });

                        trackChangesInResourceRows();
                    }

                    console.log(textStatus)
                },
                error: function (error, textStatus) {
                    console.log(error + ": " + textStatus)
                }
            })
        }

        datasetIds.forEach(function(item, index){
            const trResourceElement = $('tr[data-manualidformoving="' + item + '"]')
            const quantity = $(trResourceElement).find('.isQuantity');
            $(trResourceElement).find('.dropdown').dropdown();

            $(quantity).each(function () {
                if ($(this).attr('data-isParentConstruction')) {
                    $(this).trigger('input');
                }
            });
            // if resource row - child element of construction
            if ($(trResourceElement).attr('data-dragmewithparent')){
                const constructionRowId = $(trResourceElement).attr('data-dragmewithparent')
                const togglerElem = $("tr[data-constructionrow='" + constructionRowId +"']").find('i[id$="constructionToggler"]')

                if ($(togglerElem).hasClass("fa-minus-square")){
                    $(trResourceElement).removeClass("hidden")
                }
            }
        })
    }


    /**
     * Add red border for non-optional questions when it doesn't have required data
     * and remove the border when it has the required data
     */
    function updateQuestions() {
        $.ajax({
            url: "/app/sec/util/getQuestionsStatus",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({
                entityId: '${entity?.id?.toString()}',
                indicatorId: '${indicator?.indicatorId}',
                queryId: '${query?.queryId}'
            }),
            type: "POST",
            success: function (data, textStatus) {
                const questionsStatuses = data.questionsStatuses;

                if (Object.keys(questionsStatuses).length > 0) {
                    const questionsStatusesMapPerSections = new Map(Object.entries(questionsStatuses));

                    questionsStatusesMapPerSections.forEach(function (questionsStatusesPerSection, sectionId) {
                        const questionsStatusesMap = new Map(Object.entries(questionsStatusesPerSection));

                        questionsStatusesMap.forEach(function (status, questionId) {
                            if (status.hasOwnProperty("highlight")) {
                                let questionElement = $("#resourceBox" + questionId);
                                if (!questionElement.length) {
                                    questionElement = $(document.getElementById(sectionId + '.' + questionId));
                                }
                                if (!questionElement.length) {
                                    questionElement = $(document.getElementById(sectionId + questionId + 'ResourcesSelect'));
                                }
                                questionElement.toggleClass("redBorder", status.highlight);
                            }
                            if (status.hasOwnProperty("alert")) {
                                let alertElement = $(document.getElementById(sectionId + '.' + questionId + 'ResourcesAlert'));
                                if (alertElement && alertElement.length > 0) {
                                    alertElement.html(status.alert);
                                }
                            }
                            if (status.hasOwnProperty("unlockVerifiedDatasetLink")) {
                                let unlockVerifiedDatasetLinkElement = $(document.getElementById(sectionId + '.' + questionId + 'UnlockVerifiedDatasetLink'));
                                if (unlockVerifiedDatasetLinkElement && unlockVerifiedDatasetLinkElement.length > 0) {
                                    unlockVerifiedDatasetLinkElement.html(status.unlockVerifiedDatasetLink);
                                }
                            }
                        });
                    });
                }
            },
            error: function (error, textStatus) {
                console.log(error + ": " + textStatus)
            }
        })
    }

    function openSendMeData() {
        var jSon = {
            queryId: $("#queryId").val(),
            entityId: '${parentEntity.id}',
            indicatorId: '${indicator?.indicatorId}',
            isPlanetaryOnly: '${isPlanetaryOnly}'
        }
        var url ="/app/sec/util/renderSendMeDataModal"
        $.ajax({
            url:url,
            data: JSON.stringify(jSon),
            contentType: "application/json; charset=utf-8",
            type:"POST",
            success: function(data,textStatus){
                $("#sendMeDataModal").empty().append(data.output).modal({backdrop: 'static', keyboard: false})
            },
            error: function(error, textStatus){
                console.log(error+ ": "+ textStatus)
            }

        })
    }

</script>
<g:if test="${!user?.disableZohoChat}">
    <!-- Start of oneclicklca Zendesk Widget script -->
    <script id="ze-snippet" src="https://static.zdassets.com/ekr/snippet.js?key=788c0e91-3199-4f3d-8472-17fa41abb688"> </script>
    <script>
        zE(function() {
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
<g:if test="${showResultBar}">
    <script type="text/javascript">
        $(function () {
            renderQueryResultGraphs('${indicator?.indicatorId}', '${entity?.id}', null, true);
        })
    </script>
</g:if>
<g:render template="/query/unitwarning"/>
<g:set var="pageRenderTime" value="${(System.currentTimeMillis() - pageRenderTime) / 1000}" />
<script type="text/javascript">
    // THE FOLLOWING NEEDS TO ALWAYS BE LAST IN PAGE RENDER:
    var domReady;
    var windowReady;
    $("#queryForm").on("change",function(){
        console.log("FORM CHANGED!")
    })

    $(document).ready(function() {
        domReady = (Date.now() - timerStart) / 1000;
    });
    $(window).on('load', function() {
        windowReady = ((Date.now() - timerStart) / 1000) - domReady;
        renderPageRenderTimes("${showRenderingTimes ? 'true' : 'false'}", "${secondsTaken}", "${pageRenderTime}", domReady, windowReady)
    });
    // END

    var formCompare = $("#compareForm");
    var resourceListForm = $(formCompare).find("#resourceList")
    var existResourceIdList = $(resourceListForm).val()
    var value = existResourceIdList ? existResourceIdList.split(",") : []
    if (!value.length) {
        $(".compareButton").css('pointer-events', 'none');
    }
</script>
</body>
</html>