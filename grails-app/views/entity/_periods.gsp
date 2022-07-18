<%
    def userService = grailsApplication.mainContext.getBean("userService")
%>
<g:set var="fullListOperatingPeriods" value ="${operatingPeriods?.collect{it}}" />
<g:set var="operatingPeriods" value ="${operatingPeriods?.findAll{ it && !it.deleted }}" />
<g:set var="periodsNotHidden" value ="${operatingPeriods?.findAll{!it.isHiddenDesign} ?: []}" />
<g:set var="isDeletedOperatingPeriodsExists" value ="${fullListOperatingPeriods?.any{it.deleted}}" />
<g:set var="queryService" bean="queryService"/>
<g:set var="indicatorService" bean="indicatorService"/>
<g:if test="${validOperatingLicenses}">
    <opt:renderPopUpTrial stage="getStartedPeriod" operatingPeriods="${operatingPeriods}" selectedDesignIndicators="${selectedDesignIndicators}" entity="${entity}" license="${validOperatingLicenses?.first()}"/>
    <div class="container section">
    <div class="sectionheader">
        <button class="pull-left sectionexpander" data-name="period" >
            <i class="icon icon-chevron-down expander"></i>
            <i class="icon icon-chevron-right collapser"></i>
        </button>
        <div class="sectioncontrols pull-right">
            <g:if test="${periods?.size() >= 1}">
                <div class="btn-group testablePeriodsContainer">
                    <a href="javascript:" onclick="loadTestablePeriodsDropdown('${entity?.id?.toString()}', '${g.message(code: 'graph_no_data_displayed')}');"
                       class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
                        <i class="icon-plus icon-white"></i>
                        ${message(code: "add_test_dataset")}
                    </a>
                    <ul id="testablePeriodsList" class="dropdown-menu"></ul>
                </div>
            </g:if>
            <g:if test="${operatingParameterQueries && operatingPeriods?.size() >= 1}">
            <g:set var="allParamsReady" value="${entity?.getIsAllParameterQueriesReady(operatingParameterQueries.collect({ it.queryId }))}" />
            <div class="btn-group">
                <a href="javascript:" class="btn ${allParamsReady ? 'btn-primary ' : 'btn-danger '} dropdown-toggle" data-toggle="dropdown">
                    <i class="fa fa-cog icon-white" style="font-size: 1.23em;" aria-hidden="true"></i>
                    <g:message code="lcc_parameters"/> <span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                <g:each in="${operatingParameterQueries}" var="query">
                    <li>
                        <opt:link controller="query" action="form" params="[queryId:query?.queryId,projectLevel:true, periodLevelParamQuery:true]">
                            <g:if test="${entity?.queryReady?.get(query.queryId)}">
                                <i class="icon-done"></i>
                                <span class="hide">Ready</span>
                                <g:message code="query_prefix" /> ${query.localizedName}
                            </g:if>
                            <g:else>
                                <i class="icon-incomplete"></i>
                                <span class="hide">Incomplete</span>
                                <g:message code="query_prefix" /> ${query.localizedName}
                            </g:else>
                        </opt:link>
                    </li>
                </g:each>
                </ul>
            </div>
            </g:if>
            <g:if test="${selectedOperatingIndicators && operatingPeriods}">
                <a href="#" rel="popover" id="" data-content="${message(code:'entity.copy_guide')}" class="btn btn-primary hide-on-print" onclick="addNewPeriodModal('${entity.id}', '')">
                    <i class="icon-plus icon-white"></i> <g:message code="entity.add_period" />
                </a>
                <div class="btn-group">
                    <a href="#" class="btn btn-primary dropdown-toggle hide-on-print" data-toggle="dropdown">
                        <g:message code="entity.show.designs_more"/> <span class="caret"></span>
                    </a>
                <ul class="dropdown-menu">
                    <g:if test="${entity?.manageable}">
                        <li>
                            <opt:link controller="entity" action="addIndicators" params="[indicatorUse: 'operating']">
                                <g:message code="entity.show.indicators" />
                            </opt:link>
                        </li>
                    </g:if>
                    <g:else>
                        <li>
                            <a href="#" class="disableNonButtonField" disabled="disabled">
                                <g:message code="entity.show.indicators" />
                            </a>
                        </li>
                    </g:else>
                <%-- showHiddenDesign --%>
                    <g:if test="${operatingPeriods && (periodsNotHidden && (periodsNotHidden.size() != operatingPeriods.size()) || !periodsNotHidden)}">
                        <li>
                            <a class="pull-left" href="javascript:" onclick="$('#showHiddenPeriod').modal()">
                                <i class="fas fa-eye"></i> <g:message code="entity.showAllHiddenPeriod" />
                            </a>
                        </li>
                    </g:if>
                <%-- showHiddenDesign --%>
                    <sec:ifAnyGranted roles="ROLE_SYSTEM_ADMIN">
                        <g:if test="${isDeletedOperatingPeriodsExists}">
                            <li>
                                <a class="pull-left" href="javascript:" onclick="$('#showDeletedPeriod').modal()">
                                    <i class="fas fa-eye"></i> <g:message code="entity.showAllDeletedPeriod" />
                                </a>
                            </li>
                        </g:if>
                    </sec:ifAnyGranted>
                    <g:if test="${compareLicensed}">
                        <li>
                            <opt:link contoller="entity" action="compare" params="[type: 'operating']">
                                <g:message code="entity.compare_periods" />
                            </opt:link>
                        </li>
                    </g:if>
                    <g:if test="${oldGraphsLicensed}">
                        <li>
                            <opt:link controller="operatingPeriod" action="graphs"  >
                                <g:message code="entity.graphs" />
                            </opt:link>
                        </li>
                    </g:if>
                    <g:if test="${benchmarkLicensed}">
                        <li>
                            <a href="javascript:" onclick="$('#operatingBenchmarks').modal();">
                                <g:message code="entity.add_benchmark" />
                            </a>
                        </li>
                    </g:if>
                </ul>
                </div>
            </g:if>
            <g:else>
                <div class="pull-left">
                    <g:message code="entity.show.no_children" />
                </div>
                <a href="javascript:" class="btn btn-primary hide-on-print" onclick="$('#addIndicatorsOperating').modal();preventZeroIndicators('#getStartedSubmitOperating', 'indicatorCheckbox');">
                    <g:message code="start" />
                </a>
            </g:else>
            <g:set var="buttonsGuide" value="${opt.channelMessage(code: 'entity.periods_buttons.guide')}" />
            <g:if test="${buttonsGuide && "ecocompass".equals(channelToken)}">
                <asset:image src="img/infosign.png" rel="popover" data-content="${buttonsGuide}" />
            </g:if>
        </div>
        <g:set var="args">${operatingPeriods ? operatingPeriods.size() : 0}</g:set>
        <h2 class="h2expander" data-name="period" style="margin-left: 15px;">
        <g:message code="entity.show.periods.title" args="[args]" />
        <g:if test="${operatingPeriods && (periodsNotHidden && (periodsNotHidden.size() != operatingPeriods.size()) || !periodsNotHidden)}">
            <span class="help-block-inline">${message(code:'hidden_periods_subtitle', args: [operatingPeriods?.findAll{it.isHiddenDesign}?.size()])}</span>
        </g:if>
    </h2>
    </div>


        <div class="sectionbody">
            <g:if test="${selectedOperatingIndicators && operatingPeriods}">
                <table cellspacing="0" class="table">
                    <thead>
                    <tr>
                        <th class="design"><g:message code="entity.show.indicator"/></th>
                        <th class="design"><g:message code="entity.show.unit"/></th>
                        <g:each in="${periodsNotHidden}" var="operatingPeriod">
                            <th class="design">
                                <div class="btn-group">
                                    <a class="dropdown-toggle btn btn-link" href="#" data-toggle="dropdown"
                                       rel="popover" data-trigger="hover"
                                       data-content="${operatingPeriod.operatingPeriodAndName}${operatingPeriod.targetedNote ? '<br /><br />' + operatingPeriod.targetedNote : ''}"
                                       data-html="true"><g:if test="${operatingPeriod.locked}">
                                        <i class="fas fa-lock"></i></g:if><g:abbr maxLength="20" value="${operatingPeriod.operatingPeriodAndName}"/>
                                        ${operatingPeriod.targetedNote ? ' !' : ''}
                                        <span class="caret"></span>
                                    </a>
                                    <ul class="dropdown-menu">
                                        <g:if test="${userService.isSystemAdmin(user) && !operatingPeriod.enableAsUserTestData}">
                                            <li>
                                                <opt:link controller="entity" action="enableAsUserTestData" params="[entityId: entity.id, childId: operatingPeriod.id]">
                                                    <i class="fa fa-bookmark" aria-hidden="true"></i>
                                                    Enable as test dataset
                                                </opt:link>
                                            </li>
                                        </g:if>
                                        <g:elseif test="${userService.isSystemAdmin(user) && operatingPeriod.enableAsUserTestData}">
                                            <li>
                                                <opt:link controller="entity" action="disableAsUserData"
                                                          params="[entityId: entity.id, childId: operatingPeriod.id]">
                                                    <i class="fa fa-bookmark" aria-hidden="true"></i>
                                                    Disable as test dataset
                                                </opt:link>
                                            </li>
                                        </g:elseif>
                                        <g:if test="${!operatingPeriod.locked}">
                                            <li>
                                                <a href="#" id="${operatingPeriod.id}" onclick="modifyPeriodModal('${operatingPeriod.id}', '${modalFormId}')">
                                                    <i class="icon-tags"></i>
                                                    <g:message code="modify"/>
                                                </a>
                                            </li>
                                            <li>
                                                <opt:link controller="note" action="form" params="[targetEntityId: operatingPeriod.id, type: 'note']">
                                                    <i class="icon-tags"></i>
                                                    <g:message code="note.add"/>
                                                </opt:link>
                                            </li>
                                            <li>
                                                <a href="#" id="${operatingPeriod.id}" onclick="copyPeriodModal('${operatingPeriod.id}', '${modalFormId}')">
                                                    <i class="icon-tags"></i>
                                                    <g:message code="copy"/>
                                                </a>
                                            </li>
                                            <g:if test="${entity?.manageable}">
                                                <li>
                                                    <opt:link controller="operatingPeriod" action="remove"
                                                              id="${operatingPeriod.id}"
                                                              onclick="return modalConfirm(this);"
                                                              data-questionstr="${message(code: 'operatingPeriod.delete.question', args: [operatingPeriod.operatingPeriodAndName])}"
                                                              data-truestr="${message(code: 'delete')}"
                                                              data-falsestr="${message(code: 'cancel')}"
                                                              data-titlestr="${message(code: 'operatingPeriod.delete.header')}">
                                                        <i class="icon-trash"></i>
                                                        <g:message code="delete"/>
                                                    </opt:link>
                                                </li>
                                                <li>
                                                    <opt:link controller="operatingPeriod" action="lock" params="[periodId: operatingPeriod.id]">
                                                        <i class="fas fa-lock"></i>
                                                        <g:message code="design.lock"/>
                                                    </opt:link>
                                                </li>
                                                <sec:ifAnyGranted roles="ROLE_SUPER_USER">
                                                    <li>
                                                        <opt:link controller="operatingPeriod" action="lock" params="[periodId: operatingPeriod.id, superUser: true]">
                                                            <i class="fas fa-lock"></i>
                                                            <g:message code="design.lock.superuser"/>
                                                        </opt:link>
                                                    </li>
                                                </sec:ifAnyGranted>
                                                <li>
                                                    <a class="pointerCursor" onclick="hideChildEntity('${entity?.id}', '${operatingPeriod?.id?.toString()}')">
                                                        <i class="far fa-eye-slash" aria-hidden="true"></i>
                                                        <g:message code="entity.hidePeriod"/>
                                                    </a>
                                                </li>

                                            </g:if>
                                        </g:if>
                                        <g:elseif test="${operatingPeriod.superLocked}">
                                            <li>
                                                <a href="#" class="disabled">
                                                    <i class="icon-tags"></i>
                                                    <g:message code="modify"/>
                                                </a>
                                            </li>
                                            <li>
                                                <opt:link controller="operatingPeriod" action="form" params="[originalOperatingPeriodId: operatingPeriod.id]">
                                                <i class="icon-tags"></i> <g:message code="copy"/>
                                                </opt:link>
                                            </li>
                                            <li>
                                                <a href="#" class="disabled">
                                                    <i class="icon-trash"></i>
                                                    <g:message code="delete"/>
                                                </a>
                                            </li>
                                            <li>
                                                <span class="locked"><g:message code="locked_by_superuser"/>
                                                    <g:formatDate date="${operatingPeriod.lockingTime}" format="dd.MM.yyyy HH:mm"/></span>
                                            </li>
                                            <sec:ifAnyGranted roles="ROLE_SUPER_USER">
                                                <li>
                                                    <opt:link controller="operatingPeriod" action="unlock" params="[periodId: operatingPeriod.id, superUser: true]">
                                                        <i class="fas fa-lock"></i> Superuser unlock
                                                    </opt:link>
                                                </li>
                                            </sec:ifAnyGranted>
                                            <li><a href="#" class="disabled">
                                                    <i class="far fa-eye-slash" aria-hidden="true"></i>
                                                    <g:message code="entity.hidePeriod"/>
                                                </a>
                                            </li>
                                            <li class="divider" style="margin: 5px 1px;"></li>
                                            <li class="nowrap" style="padding-bottom: 0px;">
                                                <a class="enterpriseCheck" style="cursor: pointer;">
                                                    <i class="icon-tags"></i>
                                                    <g:message code="import_from_datasets"/>
                                                    <i class="fa fa-lock pull-right grayLink"></i>
                                                </a>
                                            </li>
                                        </g:elseif>
                                        <g:else>
                                            <li>
                                                <a href="#" class="disabled">
                                                    <i class="icon-tags"></i>
                                                    <g:message code="modify"/>
                                                </a>
                                            </li>
                                            <li>
                                                <opt:link controller="operatingPeriod" action="form" params="[originalOperatingPeriodId: operatingPeriod.id]">
                                                    <i class="icon-tags"></i>
                                                    <g:message code="copy"/>
                                                </opt:link>
                                            </li>
                                            <li>
                                                <a href="#" class="disabled">
                                                    <i class="icon-trash"></i>
                                                    <g:message code="delete"/>
                                                </a>
                                            </li>
                                            <li>
                                                <span class="locked"><g:message code="locked"/>
                                                    <g:formatDate date="${operatingPeriod.lockingTime}" format="dd.MM.yyyy HH:mm"/>
                                                </span>
                                            </li>
                                            <g:if test="${entity?.manageable}">
                                                <li>
                                                    <opt:link controller="operatingPeriod" action="unlock" params="[periodId: operatingPeriod.id]">
                                                        <i class="fas fa-lock"></i>
                                                        <g:message code="design.unlock"/>
                                                    </opt:link>
                                                </li>
                                            </g:if>
                                            <li><a href="#" class="disabled"><i class="far fa-eye-slash"
                                                                                aria-hidden="true"></i> <g:message
                                                    code="entity.hidePeriod"/></a></li>
                                        </g:else>
                                        <g:if test="${jsonExportLicensed}">
                                            <li>
                                                <opt:link controller="entity" action="showjson" id="${operatingPeriod.id}">
                                                    <g:message code="entity.as_json"/>
                                                </opt:link>
                                            </li>
                                        </g:if>
                                    </ul>
                                </div>
                            </th>
                        </g:each>
                        <g:each in="${operatingBenchmarks}" var="benchmark">
                            <th class="design">
                                <div class="btn-group">
                                    <a class="dropdown-toggle btn btn-link" href="#" data-toggle="dropdown"
                                       rel="popover" data-trigger="hover" data-content="${benchmark.name}"><g:abbr
                                            maxLength="20" value="${benchmark.name}"/></a>
                                    <ul class="dropdown-menu">
                                        <li><opt:link controller="entity" action="removeBenchmark" id="${benchmark.id}"
                                                      onclick="return modalConfirm(this);"
                                                      data-questionstr="${message(code: 'entity.remove_benchmark.question', args: [benchmark.name])}"
                                                      data-truestr="${message(code: 'delete')}"
                                                      data-falsestr="${message(code: 'cancel')}"
                                                      data-titlestr="${message(code: 'entity.remove_benchmark.header')}"><i
                                                    class="icon-trash"></i> <g:message code="delete"/></opt:link></li>
                                    </ul>
                                </div>
                            </th>
                        </g:each>
                    </tr>
                    </thead>
                <tbody>
                <g:each in="${selectedOperatingIndicators}" var="indicator">
                    <g:set var="queries" value="${queriesForSelectedOperatingIndicators.get(indicator.indicatorId)}"/>
                    <tr rel="#" class="${"benchmark".equalsIgnoreCase(indicator.visibilityStatus) && !userService.getShowBenchmarkIndicators(user) ? 'hidden': 'isv'}">
                        <td class="design">
                            ${indicatorService.getLocalizedShortName(indicator)}${indicator.deprecated ? ' <span class=\"warningRed\">' + message(code: 'deprecated') + '</span>': ''}
                            <g:if test="${indicator?.indicatorHelpUrl}">&nbsp;
                                <a href="${indicator?.indicatorHelpUrlTranslation}" target="_blank" class="helpLink">
                                    <i class="fa fa-question greenInfoBubble" aria-hidden="true"></i>
                                    <g:if test="${operatingPeriods?.size() < 5}"> &nbsp;
                                        <g:message code="help.instructions" />
                                    </g:if>
                                </a>
                            </g:if>
                        </td>
                        <td class="design">
                            <calc:renderIndicatorUnit indicator="${indicator}" entity="${entity}"/>
                        </td>
                    <g:each in="${periodsNotHidden}" var="operatingPeriod">
                        <td class="design">
                                <div class="btn-group">
                                    <g:set var="scoreValid" value="${calc.areResultsValid(entity: operatingPeriod, indicator: indicator)}" />
                                    <a style="padding-right: 5px; padding-left: 10px" class="dropdown-toggle smallaction smoothTip tooltip--right" href="#" data-tooltip="${message(code: 'input_data_tooltip')}" data-toggle="dropdown"${pageScope.dataMissing ? ' style=\"color: #FF2D41;\"' : ''}>
                                        <g:if test="${scoreValid}">
                                            <calc:renderDisplayResult entity="${operatingPeriod}" indicator="${indicator}" />
                                        </g:if>
                                        <g:else>
                                            <g:message code="recalculate" />
                                        </g:else>
                                        <i class="fa fa-caret-down" aria-hidden="true" ></i>
                                    </a>
                                    <ul class="dropdown-menu">
                                        <g:if test="${showResultReports}">
                                            <li>
                                                <g:if test="${scoreValid}">
                                                    <opt:link controller="operatingPeriod" action="results"
                                                              params="[childEntityId: operatingPeriod.id, indicatorId: indicator.indicatorId]">
                                                        <i class="icon-results" style="font-size: 1.3em; vertical-align: inherit;"></i>
                                                        <g:if test="${indicatorService.getLocalizedResultReportLinkText(indicator)}">
                                                            ${indicatorService.getLocalizedResultReportLinkText(indicator)}
                                                        </g:if>
                                                        <g:else>
                                                            <span style="display: inline-block; margin-left: 3px; clear: both; white-space: nowrap;">
                                                                <strong><g:message code="design.view.results"/></strong>
                                                            </span>
                                                        </g:else>
                                                    </opt:link>
                                                </g:if>
                                                <g:else>
                                                    <opt:link controller="entity" action="doComplexAssessment" params="[childEntityId: operatingPeriod.id, indicatorId: indicator.indicatorId]">
                                                        <i class="icon-alert"></i>
                                                        <g:message code="entity.show.results_expired"/>
                                                    </opt:link>
                                                </g:else>
                                            </li>
                                        </g:if>
                                        <g:else>
                                            <li class="nowrap">
                                                <span class="resultReport_disabled">
                                                    <i class="icon-results"></i> <g:message code="results.no_license"/>
                                                    </span>
                                            </li>
                                        </g:else>
                                        <g:if test="${!entity?.readonly}">
                                            <g:if test="${queries}">
                                                <li class="divider" style="margin: 5px 1px;"></li>
                                                <li class="nowrap">
                                                    <span style="padding: 3px 15px; display: block; clear: both; white-space: nowrap;">
                                                        <asset:image src="img/query_heading_black.png" style="max-width:14px; vertical-align: inherit; padding-right: 4px"/>
                                                        <p style="display: inline; font-weight: bold;"><g:message code="design.data.inputs"/></p>
                                                    </span>
                                                </li>
                                                <g:each in="${queries}" var="query">
                                                    <g:if test="${!operatingParameterQueries.collect({ it.queryId })?.contains(query.queryId)}">
                                                        <li class="nowrap">
                                                            <opt:link controller="query" action="form"
                                                                      params="[indicatorId: indicator.indicatorId, childEntityId: operatingPeriod.id, queryId: query.queryId, entityName: entity.name, childName: operatingPeriod.operatingPeriodAndName]">
                                                                <g:if test="${operatingPeriod.resolveQueryReady(indicator, query)}">
                                                                    <i class="icon-done"></i><span class="hide">Ready</span>
                                                                    <g:message code="query_prefix"/> ${query.localizedName}
                                                                </g:if>
                                                                <g:else>
                                                                    <g:if test="${indicator.isQueryOptional(query.queryId)}">
                                                                        <i class="icon-">&#9675;</i>
                                                                        <g:message code="query_prefix"/> ${query.localizedName}
                                                                    </g:if>
                                                                <g:else>
                                                                        <i class="icon-incomplete"></i>
                                                                        <span class="hide">Incomplete</span>
                                                                        <g:message code="query_prefix"/> ${query.localizedName}
                                                                    </g:else>
                                                                </g:else>
                                                                ${queryService.getLocalizedAppendNameByEntityType(query, entity?.entityClass) ?: ''}
                                                            </opt:link>
                                                        </li>
                                                    </g:if>
                                                </g:each>
                                                <li class="divider" style="margin: 5px 1px;"></li>
                                                <li class="nowrap">
                                                    <span style="padding: 3px 15px; display: block; clear: both; white-space: nowrap;">
                                                        <i class="fas fa-sign-in-alt" style="font-size: 1.3em; vertical-align: inherit; padding-right: 4px"></i>
                                                        <p style="display: inline; font-weight: bold;"><g:message code="import_data"/></p>
                                                    </span>
                                                </li>
                                                <g:set var="importMappers"
                                                       value="${operatingIndicatorIdsWithImportMappers?.get(indicator.indicatorId)}"/>
                                                <g:if test="${importMappers && !operatingPeriod.locked && !entity?.readonlyIndicatorIds?.contains(indicator.indicatorId)}">
                                                    <g:each in="${importMappers}" var="importMapper">
                                                        <li class="nowrap">
                                                            <g:link controller="importMapper" action="main"
                                                                    params="[applicationId: importMapper.applicationId, importMapperId: importMapper.importMapperId, indicatorId: indicator.indicatorId, entityId: entity?.id, childEntityId: operatingPeriod.id]">
                                                                <i class="icon-">○</i> <g:message code="import_from_file"/>
                                                            </g:link>
                                                        </li>
                                                    </g:each>
                                                </g:if>
                                                <g:else>
                                                    <li class="nowrap">
                                                        <a href="javascript:" class="enterpriseCheck">
                                                            <i class="icon-">○</i> <g:message code="import_from_file_not_available_indicator"/>
                                                        </a>
                                                    </li>

                                                </g:else>
                                                <g:if test="${loadQueryFromDefault && !operatingPeriod.locked && !entity?.readonlyIndicatorIds?.contains(indicator.indicatorId)}">
                                                    <li class="divider" style="margin: 5px 1px;"></li>
                                                    <li class="nowrap" style="padding-bottom: 0px;" >
                                                        <a href="javascript:;" onclick="openImportFromDatasets('${operatingPeriod?.id}', '${entity?.id}', null, '${indicator?.indicatorId}', null, '${Boolean.TRUE}', '${message(code:"import_from_dataset.no_design")}', true)">
                                                            <i class="icon-tags"></i> <g:message code="import_from_datasets"/>
                                                        </a>
                                                    </li>
                                                </g:if>
                                                <g:else>
                                                    <li class="divider" style="margin: 5px 1px;"></li>
                                                    <li class="nowrap" style="padding-bottom: 0px;" >
                                                        <a class="enterpriseCheck" style="cursor: pointer;"><i class="icon-tags"></i> <g:message code="import_from_datasets"/>
                                                            <g:if test="${operatingPeriod.locked}">
                                                                <i class="fa fa-lock pull-right grayLink"></i>
                                                            </g:if>
                                                        </a>
                                                    </li>
                                                </g:else>
                                                </g:if>
                                            <g:else>
                                                <li><g:message code="design.no_indicator_queries"/></li>
                                            </g:else>
                                        </g:if>
                                    </ul>
                                </div>
                            </td>
                    </g:each>
                        <g:each in="${operatingBenchmarks}" var="benchmark">
                            <td class="design">
                                <a href="#" class="black"><calc:benchmarkValue benchmark="${benchmark}" indicator="${indicator}" /></a>
                            </td>
                        </g:each>
                    </tr>
                </g:each>
                </tbody>
            </table>
            </g:if>
        </div>
</div>
    <g:if test="${benchmarkLicensed}">
        <g:render template="addbenchmarks" model="[entity: entity, portfolios: operatingPortfolios, type: 'operating', modalId: 'operatingBenchmarks']" />
    </g:if>
    <g:render template="/entity/modal/showHiddenPeriod"/>
    <g:render template="/entity/modal/showDeletedPeriod"/>
</g:if>


<div class="modal hide" id="addIndicatorsOperating">
    <div class="modal-header"><button type="button" class="close" data-dismiss="modal">&times;</button>
        <h2 id="addIndicatorsForOperatingTitle">
            <g:message code="entity.show.indicators"/><g:if test="${!hideEcommerce}"> -  <g:link controller="wooCommerce" action="index" class="primary"><i class="fa fa-shopping-cart"></i> &nbsp; <g:message code="main.navi.view_and_buy"/></g:link></g:if>
        </h2>
    </div>
    <div class="modal-body">
    <table class="indicators">

        <tr><th><g:message code="entity.licensed_indicators" /></th></tr>
        <g:form action="saveIndicators" useToken="true">
            <g:hiddenField name="entityId" value="${entity?.id}" />
            <g:hiddenField name="indicatorUse" value="operating" />
            <g:hiddenField name="newOperatingPeriod" value="true" />

            <g:each in="${licensedOperatignIndicators}" var="indicator">
                <tr style="margin-bottom:20px;" ${"benchmark".equalsIgnoreCase(indicator.visibilityStatus) ? 'class="hidden"': ''}>
                    <td>
                        <g:if test="${!indicator.deprecated}">
                            <input type="checkbox" ${"benchmark".equalsIgnoreCase(indicator.visibilityStatus) || "visibleBenchmark".equalsIgnoreCase(indicator.visibilityStatus) ? 'checked' : ''} name="indicatorIds" value="${indicator.indicatorId}" class=" ${"benchmark".equalsIgnoreCase(indicator.visibilityStatus) || "visibleBenchmark".equalsIgnoreCase(indicator.visibilityStatus) ? '' : 'indicatorCheckbox'}" />
                        </g:if>
                        <g:else>
                            <input type="checkbox" disabled="disabled" />
                        </g:else>
                        <strong>${indicatorService.getLocalizedName(indicator)}</strong>
                        <g:abbr value="${indicatorService.getLocalizedPurpose(indicator)}" maxLength="100" />
                        <g:if test="${indicatorService.getLocalizedPurpose(indicator)?.size() > 100}">
                            <a href="javascript:" rel="popover" data-content="${indicatorService.getLocalizedPurpose(indicator)}"><g:message code="see_all" /></a>
                        </g:if>
                        ${indicator.deprecated ? '<span class=\"warningRed\">' + message(code: 'deprecated') + '</span>': ''}
                    </td>

                </tr>
            </g:each>
            </table>
        </div>
        <div class="modal-footer">
              <div class="btn-group">
         <div class="pull-right">
            <opt:submit entity="${entity}" name="save" value="${message(code: 'start')}" id="getStartedSubmitOperating" onmouseenter="preventZeroIndicators('#getStartedSubmitOperating', 'indicatorCheckbox');" class="btn btn-primary"/>
            </div>
            <div class="pull-left" style="margin-right:10px">
                <a href="#" onclick="toggleAllCheckBoxes('indicatorCheckbox', '#getStartedSubmitOperating');" class="btn btn-primary"><g:message code="result.graph_toggle"/></a>
            </div>
            </div>
        </g:form>

    </div>
</div>
<%-- DIV MODIFY PERIOD MODAL AND FORM--%>
<span id="modifyPeriodModalDivContainer" class="modalDesignScope">
    <g:uploadForm action="save" controller="operatingPeriod" name="${modalFormId}" class="noMargin">
        <div class="modal hide modalDesignScope" id="modifyPeriodModalDiv"></div>
    </g:uploadForm>
</span>