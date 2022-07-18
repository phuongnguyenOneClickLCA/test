<style>
.disabled {
    pointer-events: none;
    cursor: default;
    opacity: 0.6;
}
</style>
<g:set var="queryService" bean="queryService"/>
<ul class="dropdown-menu perIndicator left-align" id="${index == 0 && designIndex == 0 ? 'firstMaterialInput' : ''}">
    <g:if test="${carbonDesignerOnlyLicensed || carbonDesignerAndResultsOnlyLicensed}">
        <g:set var="errorFromCheck" value="${entityService.queryModifiable(entity, design, indicator, validLicenses)}"/>
        <g:set var="allowCarbonDesigner" value="${indicator.allowedCarbonDesignerRegions != null}"/>
        <g:if test="${!errorFromCheck && simulationToolLicensed && allowCarbonDesigner && !entity.readonly && !entity.readonlyIndicatorIds?.contains(indicator.indicatorId)}">
            <g:render template="/entity/carbonDesignerLi" model="[isLocked: isLocked, entity: entity, indicator: indicator, design: design, draftByDesign: draftByDesign, queriesForIndicator: queriesForIndicator]"/>
        </g:if>
        <g:else>
            <li>Carbon Designer not available for this tool</li>
        </g:else>
    </g:if>
    <g:else>
    %{--<li class="nowrap"><span style="padding: 3px 15px; display: block; clear: both; white-space: nowrap;"><i class="icon-results" style="font-size: 1.3em; vertical-align: inherit;"></i><h3 style="display: inline;"><g:message code="design.results"/></h3></span></li>--}%
        <g:if test="${showResultReports}">
            <g:render template="/entity/viewResults"/>
        </g:if>
        <g:else>
            <li class="nowrap disableWhenCalculationRunning ${isCalculationRunningForDesignIndicatorPair ? 'removeClicks' : ''}"><span class="resultReport_disabled"><g:message code="results.no_license"/></span></li>
        </g:else>
        <g:if test="${!"visibleBenchmark".equalsIgnoreCase(indicator?.visibilityStatus) && !entity?.readonly && !design.disabledIndicators?.contains(indicator.indicatorId)}">
            <g:if test="${entity?.manageable}">
                <li class="divider" style="margin: 5px 1px;"></li>
                <li class="nowrap"><span
                        style="padding: 3px 15px; display: block; clear: both; white-space: nowrap;"><asset:image
                            src="img/query_heading_black.png"
                            style="max-width:14px; vertical-align: inherit; padding-right: 4px"/><p
                            style="display: inline; font-weight: bold;"><g:message code="design.data.inputs"/></p>
                </span>
                </li>
            </g:if>
            <g:if test="${queries}">
                <g:if test="${entity?.modifiable}">
                    <g:each in="${queries}" var="query">
                        <g:if test="${!designParameterQueries?.keySet()?.collect({ it?.queryId })?.contains(query?.queryId)}">
                            <li class="nowrap">
                                <opt:link controller="query" action="form"
                                          params="[indicatorId: indicator.indicatorId, childEntityId: design.id, queryId: query?.queryId, entityName: entity.name, childName: design.name]">
                                    <g:if test="${design.queryReadyPerIndicator?.get(indicator.indicatorId)?.get(query?.queryId)}"><i
                                            class="icon-done"></i><span class="hide">Ready</span> ${query.localizedName}
                                        <g:if test="${queryService.getLocalizedAppendNameByEntityType(query, entity?.entityClass)}">
                                            ${queryService.getLocalizedAppendNameByEntityType(query, entity?.entityClass)}
                                        </g:if>
                                        <g:if test="${isLocked || indicator?.preventChanges(query) || entity?.readonlyIndicatorIds?.contains(indicator?.indicatorId)}">
                                            <i class="fa fa-lock pull-right grayLink"></i>
                                        </g:if>
                                    </g:if>
                                    <g:else>
                                        <g:if test="${indicator.isQueryOptional(query?.queryId)}">
                                            <i class="icon-">&#9675;</i>${query.localizedName}
                                            <g:if test="${queryService.getLocalizedAppendNameByEntityType(query, entity?.entityClass)}">
                                                ${queryService.getLocalizedAppendNameByEntityType(query, entity?.entityClass)}
                                            </g:if>
                                            <g:if test="${isLocked || indicator?.preventChanges(query) || entity?.readonlyIndicatorIds?.contains(indicator?.indicatorId)}"><i
                                                    class="fa fa-lock pull-right grayLink"></i></g:if>
                                        </g:if>
                                        <g:else>
                                            <i class="icon-incomplete"></i><span
                                                class="hide">Incomplete</span>${query.localizedName}
                                            <g:if test="${queryService.getLocalizedAppendNameByEntityType(query, entity?.entityClass)}">
                                                ${queryService.getLocalizedAppendNameByEntityType(query, entity?.entityClass)}
                                            </g:if> <span
                                                style="color: #FF2D41">(${g.message(code: "entity.mandatory_answers_missing")})</span>
                                            <g:if test="${isLocked || indicator?.preventChanges(query) || entity?.readonlyIndicatorIds?.contains(indicator?.indicatorId)}"><i
                                                    class="fa fa-lock pull-right grayLink"></i></g:if>
                                        </g:else>
                                    </g:else>
                                </opt:link>
                            </li></g:if>
                    </g:each>
                </g:if>
                <li class="divider" style="margin: 5px 1px;"></li>
                <li class="nowrap"><span style="padding: 3px 15px; display: block; clear: both; white-space: nowrap;"><i
                        class="fas fa-sign-in-alt"
                        style="font-size: 1.3em; vertical-align: inherit; padding-right: 4px"></i>

                    <p style="display: inline; font-weight: bold;"><g:message code="import_data"/></p></span></li>
                <g:set var="errorFromCheck"
                       value="${entityService.queryModifiable(entity, design, indicator, validLicenses)}"/>
                <g:set var="allowCarbonDesigner" value="${indicator.allowedCarbonDesignerRegions != null}"/>
                <g:set var="importMappers" value="${designIndicatorIdsWithImportMappers?.get(indicator.indicatorId)}"/>
                <g:if test="${!errorFromCheck && !design.locked && !design.superLocked && !entity.readonlyIndicatorIds?.contains(indicator.indicatorId)}">
                    <g:if test="${importMappers && importMapperLicensed && entity?.manageable}">
                        <g:each in="${importMappers}" var="importMapper">
                            <li class="nowrap">
                                <g:link controller="importMapper" action="main" class="disableWhenCalculationRunning ${isCalculationRunningForDesignIndicatorPair ? 'removeClicks' : ''}"
                                        params="[applicationId: importMapper.applicationId, importMapperId: importMapper.importMapperId, indicatorId: indicator.indicatorId, entityId: entity?.id, childEntityId: design.id]">
                                    <i class="icon-">○</i> <g:message code="excel_gbxml"/>
                                </g:link>
                            </li>
                        </g:each>
                        <li class="nowrap">
                            <a target='_blank' rel='noopener noreferrer' class="disableWhenCalculationRunning ${isCalculationRunningForDesignIndicatorPair ? 'removeClicks' : ''}"
                               href="https://oneclicklca.zendesk.com/hc/en-us/sections/360004321480"><i
                                    class="icon-">○</i> <g:message code="other_type"/>
                            </a>
                        </li>
                    </g:if>
                    <g:elseif
                            test="${importMappers && !importMapperLicensed && bimModelCheckerLicensed && entity?.manageable}">
                        <g:each in="${importMappers}" var="importMapper">
                            <li class="nowrap">
                                <opt:link controller="importMapper" action="main" class="disableWhenCalculationRunning ${isCalculationRunningForDesignIndicatorPair ? 'removeClicks' : ''}"
                                          params="[applicationId: importMapper.applicationId, importMapperId: importMapper.importMapperId, indicatorId: indicator.indicatorId, entityId: entity?.id, childEntityId: design.id, bimModelChecker: Boolean.TRUE]">
                                    <i class="icon-">○</i> <g:message code="bim_checker_heading"/>
                                </opt:link>
                            </li>
                            <li class="nowrap">
                                <a target='_blank' rel='noopener noreferrer' class="disableWhenCalculationRunning ${isCalculationRunningForDesignIndicatorPair ? 'removeClicks' : ''}"
                                   href="https://oneclicklca.zendesk.com/hc/en-us/sections/360004321480">
                                    <i class="icon-">○</i> <g:message code="other_type"/>
                                </a>
                            </li>
                        </g:each>
                    </g:elseif>
                    <g:elseif test="${importMappers && !importMapperLicensed && entity?.manageable}">
                        <g:if test="${!entity?.public && licenseTypes.contains("Trial")}">
                            <li class="nowrap">
                                <a onclick="requestImportTrial('${user?.id}', '${message(code: "request_import_trial.sent")}')">
                                    <i class="icon-">○</i> <g:message code="request_import_trial"/>
                                </a>
                            </li>
                            <li class="nowrap">
                                <a target='_blank' rel='noopener noreferrer'
                                   href="https://oneclicklca.zendesk.com/hc/en-us/sections/360004321480">
                                    <i class="icon-">○</i> <g:message code="other_type_import_trial"/>
                                </a>
                            </li>
                        </g:if>
                        <g:else>
                            <g:set var="featureName"><g:message code="business"/> - <g:message
                                    code="import_from_file"/></g:set>
                            <li class="nowrap"><a href="javascript:" class="enterpriseCheck"><i class="icon-">○</i>
                                <g:message code="enterprise_feature_warning" args="[featureName]"/></a>
                            </li>
                        </g:else>
                    </g:elseif>
                    <g:else>
                        <li class="nowrap">
                            <a href="javascript:" class="enterpriseCheck"><i class="icon-">○</i> <g:message
                                    code="import_from_file_not_available_indicator"/></a>
                        </li>
                    </g:else>
                </g:if>
                <g:else>
                    <g:if test="${importMappers && importMapperLicensed && entity?.manageable}">
                        <g:each in="${importMappers}" var="importMapper">
                            <li class="nowrap removeClicks">
                                <a rel='noopener noreferrer'>
                                    <i class="icon-">○</i> <g:message code="excel_gbxml"/>
                                </a>
                            </li>
                        </g:each>
                        <li class="nowrap removeClicks">
                            <a rel='noopener noreferrer'><i class="icon-">○</i> <g:message code="other_type"/>
                            </a>
                        </li>
                    </g:if>
                    <g:elseif
                            test="${importMappers && !importMapperLicensed && bimModelCheckerLicensed && entity?.manageable}">
                        <g:each in="${importMappers}" var="importMapper">
                            <li class="nowrap removeClicks">
                                <a rel='noopener noreferrer'>
                                    <i class="icon-">○</i> <g:message code="bim_checker_heading"/>
                                </a>
                            </li>
                            <li class="nowrap removeClicks">
                                <a rel='noopener noreferrer'>
                                    <i class="icon-">○</i> <g:message code="other_type"/>
                                </a>
                            </li>
                        </g:each>
                    </g:elseif>
                    <g:elseif test="${importMappers && !importMapperLicensed && entity?.manageable}">
                        <g:if test="${!entity?.public && licenseTypes.contains("Trial")}">
                            <li class="nowrap removeClicks">
                                <i class="icon-">○</i> <g:message code="request_import_trial"/>
                            </li>
                            <li class="nowrap removeClicks">
                                <a rel='noopener noreferrer'>
                                    <i class="icon-">○</i> <g:message code="other_type_import_trial"/>
                                </a>
                            </li>
                        </g:if>
                        <g:else>
                            <g:set var="featureName"><g:message code="business"/> - <g:message
                                    code="import_from_file"/></g:set>
                            <li class="nowrap"><a href="javascript:" class="enterpriseCheck"><i class="icon-">○</i>
                                <g:message code="enterprise_feature_warning" args="[featureName]"/></a>
                            </li>
                        </g:else>
                    </g:elseif>
                    <g:else>
                        <li class="nowrap">
                            <a href="javascript:" class="enterpriseCheck"><i class="icon-">○</i> <g:message
                                    code="import_from_file_not_available_indicator"/></a>
                        </li>
                    </g:else>
                </g:else>
                <g:if test="${!errorFromCheck && simulationToolLicensed && allowCarbonDesigner && !entity.readonly && !entity.readonlyIndicatorIds?.contains(indicator.indicatorId)}">
                    <li class="divider" style="margin: 5px 1px;"></li>
                    <g:render template="/entity/carbonDesignerLi"
                              model="[isLocked: isLocked, entity: entity, indicator: indicator, design: design, draftByDesign: draftByDesign]"/>
                </g:if>
                <g:if test="${loadQueryFromDefault && !isLocked && !entity.readonlyIndicatorIds?.contains(indicator?.indicatorId)}">
                    <li class="divider" style="margin: 5px 1px;"></li>
                    <li class="nowrap" style="padding-bottom: 0px;"><a href="javascript:;" class="disableWhenCalculationRunning ${isCalculationRunningForDesignIndicatorPair ? 'removeClicks' : ''}"
                                                                       onclick="openImportFromDatasets('${design?.id}', '${entity?.id}', null, '${indicator?.indicatorId}', null, '${Boolean.TRUE}', '${message(code:"import_from_dataset.no_design")}', true)">%{--<i class="icon-">○</i>--}%<i
                                class="icon-tags"></i> <g:message code="import_from_datasets"/></a></li>
                </g:if>
                <g:else>
                    <li class="divider" style="margin: 5px 1px;"></li>
                    <li class="nowrap" style="padding-bottom: 0px;"><a class="enterpriseCheck"
                                                                       style="cursor: pointer;"><i
                                class="icon-tags"></i> <g:message code="import_from_datasets"/>
                        <g:if test="${isLocked}"><i class="fa fa-lock pull-right grayLink"></i></g:if></a></li>
                </g:else>
            </g:if>
            <g:else>
                <li><g:message code="design.no_indicator_queries"/></li>
            </g:else>

        </g:if>
    </g:else>
</ul>
