<%@ page import="com.bionova.optimi.core.Constants as CoreConst" %>
<g:set var="queryService" bean="queryService"/>
<g:set var="indicatorService" bean="indicatorService"/>
<g:set var="valueReferenceService" bean="valueReferenceService"/>
<div class="container">
    <div class="screenheader">
        <div class="pull-right hide-on-print">
        <%--<g:set var="GCCAdemo" value="${entity?.GCCADemoLicensed}" />--%>
            <g:if test="${portfolioEntity}">
                <opt:link controller="entity" action="show" id="${portfolioEntity?.id}"
                          class="btn btn-defaut"><g:message code="back"/></opt:link>
            </g:if>
            <g:elseif test="${entity?.id}">
                <opt:link controller="entity" action="show" id="${entity?.id}" value="back"
                          class="btn btn-defaut"><g:message code="back"/>
                </opt:link>
            </g:elseif>
            <g:if test="${portfolioEntity}">
                <g:if test="${showSourceListingButton(portfolio: portfolio, indicator: indicator)}">
                    <input type="button" value="${g.message(code: 'results.show_sources')}" id="showHideSources"
                           class="btn btn-primary" onclick="showSources('sourceListingTable', 'showHideSources');"/>
                </g:if>

            </g:if>
            <g:else>
                <g:set var="designQueryIds" value="${designQueries*.queryId}" />
                <g:if test="${!isPortfolio}">
                    <g:if test="${indicator?.enableExport}">
                        <g:if test="${childEntity?.isIndicatorReady(indicator, designQueryIds)}">
                            <g:link controller="export" class="btn btn-primary" action="exportWidget"
                                    params="[entityId: entity?.id, childEntityId: childEntity?.id, indicatorId: indicator?.indicatorId]">Generate</g:link>
                        </g:if>
                        <g:else>
                            <a href="#" class="btn">Export</a>
                        </g:else>
                    </g:if>
                    <g:if test="${indicator?.exportType && "pdf".toLowerCase().equals(indicator.exportType.toLowerCase()) && entity?.generatePdfLicensed}">
                        <g:if test="${entity?.modifiable}">
                            <g:set var="templatePath"
                                   value="${valueReferenceService.getValueForEntity(indicator.exportTemplate, childEntity, false)}"/>
                            <g:if test="${templatePath}">
                                <g:link controller="fileExport" action="createPdf"
                                        params="[entityId: childEntity?.id, indicatorId: indicator?.indicatorId]"
                                        class="btn btn-primary" target="_blank">Create PDF</g:link>
                            </g:if>
                            <g:else>
                                <a href="javascript:" class="btn btn-primary" disabled="disabled" rel="popover"
                                   data-trigger="hover"
                                   data-content="No EPD template selected in ${queryService.getQueryByQueryId(indicator.exportTemplate?.queryId, true)?.localizedName}">Create PDF</a>
                            </g:else>
                        </g:if>
                        <g:else>
                            <a href="#" class="btn disabled" disabled="disabled">Create PDF</a>
                        </g:else>
                    </g:if>
                </g:if>
                <g:if test="${allowedQueries && modifiable && !"visibleBenchmark".equalsIgnoreCase(indicator?.visibilityStatus) && !carbonDesignerAndResultsOnlyLicensed}">
                    <div class="btn-group buttonsRowResults" id="buttonsRowResults" style="display: inline-block;">
                        <a href="#" data-toggle="dropdown" class="btn btn-primary dropdown-toggle"><g:message
                                code="data_missing"/> <span class="caret"></span></a>
                        <ul class="dropdown-menu" style="width: 270px">
                            <g:each in="${allowedQueries}" var="query">
                                <li>
                                    <g:link controller="query" action="form"
                                            params="[indicatorId: indicator.indicatorId, childEntityId: childEntity?.id, entityId: entity?.id, queryId: query.queryId]">
                                        <g:if test="${childEntity?.queryReadyPerIndicator?.get(indicator.indicatorId)?.get(query.queryId)}">
                                            <i class="icon-done"></i>
                                        </g:if>
                                        <g:else>
                                            <g:if test="${indicator.isQueryOptional(query.queryId)}">
                                                <i class="icon-">&#9675;</i>
                                            </g:if>
                                            <g:else>
                                                <i class="icon-incomplete"></i>
                                            </g:else>
                                        </g:else>
                                        ${query?.localizedName}<g:if
                                            test="${queryService.getLocalizedAppendNameByEntityType(query, entity?.entityClass)}">${queryService.getLocalizedAppendNameByEntityType(query, entity?.entityClass)}</g:if><g:if
                                            test="${childEntity?.locked || childEntity?.superLocked || indicator?.preventChanges(query)}"><i
                                                class="fa fa-lock pull-right"></i></g:if>
                                    </g:link>
                                </li>
                            </g:each>
                        </ul>
                    </div>
                </g:if>
                <div class="btn-group buttonsRowResults hidableButtonsGroup hide-on-print" id="compareGroup"
                     style="${indicator?.nonNumericResult ? 'display:none;' : 'display: inline-block;'}">
                    <calc:renderResultCompareButton parentEntity="${entity}" entity="${childEntity}"
                                                    indicator="${indicator}"/>
                </div>
                <g:if test="${isCompareDataCompatibleAndLicensed && !carbonDesignerAndResultsOnlyLicensed}">
                    <div class="btn-group hide-on-print inline-block">
                        <opt:renderCompareDataBtn parentEntity="${entity}" params="[parentEntityId:entity?.id]" elementId="compareButton" controller="entity" action="resourcesComparisonEndUser" class="btn btn-primary" target="_blank"/>
                    </div>
                </g:if>


                <div class=" btn-group hide-on-print buttonsRowResults" style="display: inline-block;"><a href="#"
                                                                                                          data-toggle="dropdown"
                                                                                                          class="btn btn-primary dropdown-toggle"><g:message
                            code="entity.show.designs_more"/> <span class="caret"></span></a><ul class="dropdown-menu">
                    <li class="bold">
                        <span style="padding: 3px 15px; display: block; clear: both; white-space: nowrap; font-weight: bold;">
                            <g:message code="results.reports"/>
                        </span>
                    </li>
                    <g:set var="entityType" value="${entity?.typeResourceId}"/>
                    <g:if test="${!indicator?.requireMonthly}">
                    </g:if>
                    <g:set var="preventReportGenerationLicensed" value="${entity?.preventReportGenerationLicensed}"/>
                    <g:set var="expandFeature" value="${indicator?.resolveExpandFeature}"/>
                    <g:if test="${expandFeature}">
                        <g:if test="${preventReportGenerationLicensed}">
                            <li><a href="javascript:" class="enterpriseCheck" rel="popover" data-trigger="hover"
                                   data-content="${message(code: "available_in_commercial_licenses")}">${expandFeature?.localizedLinkDisplayName ?: message(code: 'query.show_detailed_report')}</a>
                            </li>
                        </g:if>
                        <g:else>
                            <li><a href="javascript:"
                                   onclick="fullScreenPopup('${createLink(controller: 'entity', action: 'expandResults', params: [entityId: childEntity?.id,parentId: entity?.id, indicatorId: indicator?.indicatorId])}', '${indicatorService.localizedDurationWarning}');">${expandFeature?.localizedLinkDisplayName ?: message(code: 'query.show_detailed_report')}</a>
                            </li>
                        </g:else>
                    </g:if>

                    <g:if test="${'component'.equals(entity?.entityClass) || isComponentEntity}">
                        <li><opt:link controller="result" action="addDatasetsToResources"
                                      params="[entityId: entity?.id, childEntityId: childEntity?.id, indicatorId: indicator?.indicatorId, entityClass: childEntity?.entityClass]"><g:message
                                    code="query.add_to_resources"/></opt:link></li>
                    </g:if>
                    <g:set var="chartIdsForWord" value=""/>
                    <g:set var="reportGenerationRules"
                           value="${indicator.getReportGenerationRuleFromApplication()}"/>
                    <g:each in="${reportGenerationRules}" var="reportGenerationRule">
                        <g:each in="${reportGenerationRule?.allowedTemplates}">
                            <g:if test="${it.templateId}">
                                <li><g:if test="${indicator?.wordReportGeneration && wordReportGenerationLicensed}">
                                    <a href="javascript:"
                                       onclick="generateWordDoc('${childEntity?.id}', '${indicator?.indicatorId}', '${arrayOfRulesWordGeneration}', '${templatesAndCanvasIdsForReportGenerationMap.get(reportGenerationRule.reportGenerationRuleId)}', '${it.templateId}', '${reportGenerationRule.reportGenerationRuleId}', '${g.message(code: 'unknownError')}');">${it.localizedName}</a>
                                </g:if><g:else>
                                    <a class="grayLink removeClicks" disabled="disabled">${it.localizedName}</a>
                                </g:else>
                                </li>

                            </g:if>
                        </g:each>
                    </g:each>
                    <g:if test="${showAndJumpToVPointLicensed && isIndicatorHavingVerificationPoints}">
                        <li>
                            <a href="javascript:"
                               onclick="fetchVerificationPointsModal('${entity?.id}', '${childEntity?.id}', '${indicator?.indicatorId}', '', '${message(code: "jump.to.vpoints")}', '${message(code: "loading")}')">
                                <span><g:message code="jump.to.vpoints" /></span>
                            </a>
                        </li>
                    </g:if>
                    <g:if test="${indicator?.isPlanetaryIndicator}">
                        <li>
                            <g:if test="${planetaryReportLicensed}">
                            <a href="javascript:;"
                                   onclick="exportPDFReport('byMaterialSixPack,byClassSixPack,massByClassSixPack,${graphCalculationRules?.get(0)?.calculationRuleId}wrapper')">${message(code: "planetary_report")}</a>
                                <opt:link class="hidden" elementId="generatePdf" controller="entity" action="planetaryReport"
                                    params="[designId: childEntity?.id, indicatorId: indicator?.indicatorId, parentId: entity?.id, calculationRuleId: graphCalculationRules?.get(0)?.calculationRuleId]">${message(code: "planetary_report")}</opt:link>
                            </g:if>
                            <g:else>
                            <a class="grayLink removeClicks" disabled="disabled">${message(code: "planetary_report")}</a>
                            </g:else>
                        </li>
                    </g:if>
                <%--<a class="btn btn-primary" href="javascript:" onclick="publishEPD();">Publish EPD</a>--%>
                    <g:if test="${indicator?.allowIlcdXmlExport}">
                        <li><g:if test="${epdIlcdExportLicensed}">
                            <g:link controller="xmlHandler" action="renderILCDEPDXml"
                                    params="[entityId: childEntity?.id, indicatorId: indicator?.indicatorId]"
                                    target="_blank">Download ILCD+EPD</g:link>
                        </g:if><g:else>
                            <a class="grayLink removeClicks" disabled="disabled">Download ILCD+EPD</a>
                        </g:else>
                        </li>
                    </g:if>
                    <g:if test="${indicator?.sendPrivateDataType}">
                        <li><g:if
                                test="${entity?.modifiable  && childEntity?.isIndicatorReady(indicator, designQueryIds)}">
                            <a class="" href="javascript:"
                               onclick="sendMeDataSwalNew('${childEntity?.id?.toString()}', '${indicator?.indicatorId}', '${message(code:"sendMeData.heading")}')"><g:message
                                    code="sendMeData.heading"/></a>
                        </g:if><g:else>
                            <a class="grayLink removeClicks" disabled="disabled"><g:message
                                    code="sendMeData.heading"/></a>
                        </g:else>
                        </li>
                    </g:if>

                <%--<g:if test="${GCCAdemo}">
                    <li><a href="javascript:" onclick="saveAsPrivateDataset('${message(code:'name_dataset')}','${message(code:'save')}','${message(code:'cancel')}');">${message(code:'save_private_dataset')}</a> </li>
                </g:if>--%>
                    <g:if test="${entity?.exportToAutocaseEnabled}">
                        <li><opt:link controller="fileExport" action="autocaseSendFile"
                                      params="[childEntityId: childEntity?.id, indicatorId: indicator?.indicatorId]"><g:message
                                    code="results.export_to_autocase"/></opt:link></li>
                    </g:if>
                    <li class="divider" style="margin: 5px 1px;"></li>
                    <g:if test="${indicator?.allowMonthlyData && (childEntity?.monthlyDataLicensed || childEntity?.quarterlyDataLicensed)}">
                        <li><a href="javascript:;" onclick="showAnnual();"><g:message code="query.annual_data"/></a>
                        </li>
                        <g:if test="${indicator?.isQuarterlyInputEnabledForAnyQuery(childEntity)}">
                            <li><a href="javascript:;" onclick="showQuarterly();"><g:message
                                    code="query.quarterly_data"/></a></li>
                        </g:if>
                        <g:if test="${indicator?.isMonthlyInputEnabledForAnyQuery(childEntity) || indicator?.requireMonthly}">
                            <li><a href="javascript:;" onclick="showMonthly();"><g:message
                                    code="query.monthly_data"/></a></li>
                        </g:if>
                    </g:if>
                    <g:if test="${indicator?.epdHubExportContent}">
                        <li class="bold"><span
                                style="padding: 3px 15px; display: block; clear: both; white-space: nowrap; font-weight: bold;"><g:message
                                    code="${CoreConst.EPDHUB_SUBMISSION}"/></span></li>
                        <li><opt:link elementId="downloadEPDJson" controller="export" action="downloadEPDJson"
                                      params="[childEntityId: childEntity?.id, indicatorId: indicator.indicatorId]">${message(code: CoreConst.DOWNLOAD_DIGITAL_EPD)}</opt:link>
                        </li>
                        <li><opt:link elementId="generateDigitalEPD" controller="export"
                                      action="downloadDigitalEPDContent"
                                      params="[childEntityId: childEntity?.id, indicatorId: indicator.indicatorId]">${message(code: CoreConst.SUBMIT_TO_EPDHUB)}</opt:link>
                        </li>
                        <li class="divider" style="margin: 5px 1px;"></li>
                    </g:if>

                    <g:if test="${indicator.allowXmlExport_BETIE}">
                        <li><opt:link elementId="generateBetieXML" controller="xmlHandler" action="renderBetie"
                                      params="[childEntityId: childEntity?.id, indicatorId: indicator?.indicatorId]">${message(code: CoreConst.EXPORT_BETIE_XML)}</opt:link>
                        </li>
                    </g:if>

                    <g:if test="${formattingFeatures && !indicator?.nonNumericResult}">
                        <li class="bold"><span
                                style="padding: 3px 15px; display: block; clear: both; white-space: nowrap; font-weight: bold;"><g:message
                                    code="results.choose_formatting"/></span></li>
                        <g:each in="${formattingFeatures}" status="index" var="resultFormatting">
                            <li><opt:link controller="util" action="setResultFormatting"
                                          params="[resultFormatting: resultFormatting.key, childEntityId: childEntity?.id, indicatorId: indicator?.indicatorId, type: type]"><g:message
                                        code="results.formatting.${resultFormatting.key}"/></opt:link></li>
                        </g:each>
                    </g:if>
                    <li class="divider" style="margin: 5px 1px;"></li>
                    <li><a href="javascript:;" onclick="window.print();"><i class="icon-print"></i> <g:message
                            code="print"/></a></li>
                    <li><g:if test="${showSourceListingButton(entity: childEntity, indicator: indicator, queryIds: designQueryIds)}">
                        <a href="javascript:" id="showHideSources"
                           onclick="toggleExpandSection($('#dataSourceTable'), true)"><g:message
                                code="results.show_sources"/></a>
                    </g:if></li>
                    <g:if test="${indicator?.indicatorHelpUrl}">
                        <li><a href="${indicator?.indicatorHelpUrlTranslation}" target="_blank" class="helpLink"><i
                                class="fa fa-question margin-right-5"></i> &nbsp; <g:message
                                code="help.instructions"/></a></li>
                    </g:if>
                </ul></div>
                <g:set var="buttonsGuide" value="${opt.channelMessage(code: 'entity.results_buttons.guide')}"/>
                <g:if test="${buttonsGuide}">
                    <asset:image src="img/infosign.png" rel="popover" data-content="${buttonsGuide}"/>
                </g:if>
            </g:else>
        </div>
        %{--TODO: change to taglib --}%
        <opt:breadcrumbsNavBar indicator="${indicator}" parentEntity="${portfolioEntity ?: entity}" childEntity="${operatingPeriod ? null : childEntity}" childName="${operatingPeriod}" indicatorLicenseName="${indicatorLicenseName}" step="${2}"/>
        <br/>
        <br/>

        <div class="container">
            <h2 class="h2heading" style="display:inline-block;">
                <g:if test="${portfolioEntity}">
                    <i class="fa fa-home fa-2x"></i> ${portfolioEntity?.name}${operatingPeriod ? ' - ' + operatingPeriod : ''} ${indicator ? ' - ' + indicatorService.getLocalizedName(indicator) : ''}
                </g:if>
                <g:elseif test="${entity?.id}">
                    <i class="fa fa-home fa-2x"></i>${childEntity.name ? childEntity.name : childEntity.operatingPeriod} ${indicator ? ' - ' + indicatorService.getLocalizedName(indicator) : ''}${indicatorLicenseName ?: ""}   <img
                        class="certificationLogo" onerror="this.style.display = 'none'"
                        src="${indicator?.certificationImageResourceId ? '/static/logoCertificate/' + indicator?.certificationImageResourceId[0] + '.png' : ''}"/>
                </g:elseif>
            </h2>   <a style="display:inline-block; padding-left: 10px" href="javascript:;"
                       class="entityInfoResultReport" rel="popover" data-trigger="click" data-content=""
                       data-html="true"><g:message code="project_basic_info"/></a>
        </div>
    </div>
</div>

