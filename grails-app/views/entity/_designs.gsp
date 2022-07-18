<%
    def userService = grailsApplication.mainContext.getBean("userService")
%>
<%@ page import="com.bionova.optimi.frenchTools.FrenchConstants;com.bionova.optimi.core.domain.mongo.User; org.springframework.context.i18n.LocaleContextHolder; com.bionova.optimi.core.domain.mongo.Feature; com.bionova.optimi.core.Constants; com.bionova.optimi.core.domain.mongo.License" %>
<g:set var="modalFormId" value="${"designForm2"}"/>
<g:set var="indicatorService" bean="indicatorService"/>
<g:set var="fullListDesigns" value ="${designs?.collect{it}}" />
<g:set var="designs" value ="${designs?.findAll{ it && !it.deleted }}" />
<g:set var="designsNotHidden" value ="${designs?.findAll{!it.isHiddenDesign} ?: []}" />
<g:set var="isDeletedDesignsExists" value ="${fullListDesigns?.any{it.deleted}}" />
<g:set var="allValidIndicatorsAllTypes"
       value="${[drawableEntityIndicators, operatingDrawableEntityIndicators]?.flatten()}"/>
<g:set var="allValidIndicatorsAllTypesIds" value="${allValidIndicatorsAllTypes.collect{it.indicatorId} as List<String>}"/>

<g:if test="${validDesignLicenses}">
    <g:set var="entityService" bean="entityService"/>
    <g:set var="overOneIndicator" value="${licensedDesignIndicators?.findAll({ !"benchmark".equalsIgnoreCase(it.visibilityStatus) })?.size() > 1}" />
    <opt:renderPopUpTrial stage="noParameterWarningProjectPage" designs="${designs}" parameterQueries="${designParameterQueries?.keySet()?.toList()}" entity="${entity}" selectedDesignIndicators="${selectedDesignIndicators}"/>
    <div class="container section">
        <div class="sectionheader">
            <button class="pull-left sectionexpander" data-name="design"><i class="icon icon-chevron-down expander"></i><i class="icon icon-chevron-right collapser"></i></button>

            <div class="sectioncontrols pull-right">
                <g:if test="${designs.size() >= 1 && testableFeatureLicensed}">
                    <div class="btn-group testableDesignsContainer">
                        <a href="#" id="btnTestableDesignsList" onclick="loadTestableDesignsDropdown('${entity?.id?.toString()}', '${g.message(code: 'graph_no_data_displayed')}');"
                           class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
                            <i class="icon-plus icon-white"></i>
                            ${message(code: "add_test_dataset")}
                        </a>
                        <ul id="testableDesignsList" class="dropdown-menu"></ul>
                    </div>
                </g:if>

                <g:if test="${designParameterQueries?.size() > 0 && designs.size() >= 1}">
                    <div class="btn-group">
                        <g:set var="allParamsReady" value="${entity?.getIsAllParameterQueriesReady(designParameterQueries.findAll({!it.value}).collect({ it?.key?.queryId }))}" />
                        <a href="javascript:" class="btn ${allParamsReady ? 'btn-primary ' : 'btn-danger '} dropdown-toggle" data-toggle="dropdown"><i class="fa fa-cog icon-white" style="font-size: 1.23em;" aria-hidden="true"></i> <g:message code="lcc_parameters"/> <span class="caret"></span> </a>
                        <ul class="dropdown-menu">
                        <g:each in="${designParameterQueries.keySet()}" var="query">
                            <li><opt:link controller="query" action="form" params="[queryId:query?.queryId, projectLevel:true, designLevelParamQuery:true, noDefaults: true]">
                                <g:if test="${entity?.queryReady?.get(query?.queryId)}">
                                    <i class="icon-done"></i><span class="hide">Ready</span><g:message code="query_prefix" /> ${query?.localizedName} ${designParameterQueries.get(query) ? message(code:"for_compare_data") : ""}
                                </g:if>
                                <g:else>
                                    <i class="icon-incomplete"></i><span class="hide">Incomplete</span> <g:message code="query_prefix" /> ${query?.localizedName} ${designParameterQueries.get(query) ? message(code:"for_compare_data") : ""} <span style="color: #FF2D41">(${g.message(code: "entity.mandatory_answers_missing")})</span>
                                </g:else>
                            </li></opt:link>
                        </g:each>
                        </ul>
                    </div>
                </g:if>
                <g:if test="${selectedDesignIndicators && designs}">
                    <g:set var="nonBenchmarkIndicators" value="${selectedDesignIndicators?.findAll{!"benchmark".equalsIgnoreCase(it.visibilityStatus)}}" />
                    <g:set var="firstIndicator" value="${nonBenchmarkIndicators && !nonBenchmarkIndicators.isEmpty() ? nonBenchmarkIndicators.first() : null}"/>
                    <g:set var="expandQueryIfOne" value="${designs.size() == 1 && selectedDesignIndicators.findAll{! "benchmark".equalsIgnoreCase(it.visibilityStatus)}?.size() == 1  && !userService.getShowBenchmarkIndicators(user) && firstIndicator && !designs.findAll({it?.isIndicatorReady(firstIndicator)})}" />
                    <g:set var="errorFromCheck" value="${entityService.queryModifiable(entity, null, null, validLicenses, true)}" />
                    <g:if test="${!errorFromCheck}">
                        <g:if test="${!preventMultipleDesignsLicened && designs.size() >= 1}">
                            <a href="javascript:" class="btn btn-primary" onclick="addNewDesignModal('${entity?.id?.toString()}', '${modalFormId}', this)"><i class="icon-plus icon-white"></i> <g:message code="entity.add_design" /></a>
                        </g:if>
                        <g:if test="${compareFeatureLicensed && materialSpecifierQuery}">
                            <opt:renderCompareDataBtn parentEntity="${entity}" params="[indicatorId: Constants.COMPARE_INDICATORID, parentEntityId: entity?.id]" elementId="compareButtonTotal" controller="entity" action="resourcesComparisonEndUser" class="btn btn-primary compareButtonTotal" target="_blank"/>
                        </g:if>
                        <%--<opt:link controller="design" action="form" rel="${session?.showStartupTips ? 'startupTip_top' : ''}" data-content="${message(code:'startup-tip.design_add')}" class="btn btn-primary hide-on-print ${session?.showStartupTips ? 'startupTip' : ''}"><i class="icon-plus icon-white"></i> <g:message code="entity.add_design" /></opt:link>
                        <opt:renderDefaults entity="${entity}" />--%>
                        <g:set var="indicatorsHaveCarbonDesignerRegions" value="${selectedDesignIndicators.find({ it.allowedCarbonDesignerRegions != null})}"/>
                        <g:if test="${carbonDesigner3DLicensed && indicatorsHaveCarbonDesignerRegions}">
                            <div class="btn-group" id="carbonDesigner3d">
                                <a href="#" data-toggle="dropdown" style="display:inline-block" class="dropdown-toggle btn btn-primary hide-on-print">
                                    <img class="carbonDesigner3d__icon" src="/app/assets/simulationTool_icon/simulationTool_icon_white.png" alt="carbon designer icon">
                                    <g:message code="entity.carbon_designer_3d"/>
                                    <span class="caret"></span>
                                </a>
                                <ul  class="dropdown-menu">
                                    <g:each in="${selectedDesignIndicators}" var="indicator" status="index">
                                        <g:set var="allowCarbonDesigner" value="${indicator.allowedCarbonDesignerRegions != null}"/>
                                        <g:if test="${allowCarbonDesigner}">
                                            <li><a href="${carbonDesigner3dUrl}carbondesigner3d/?parentEntityId=${entity?.id}&indicatorId=${indicator?.indicatorId}&projectName=${entity?.name}">${indicator?.localizedName}</a></li>
                                        </g:if>
                                    </g:each>
                                </ul>
                            </div>
                        </g:if>
                        <div class="btn-group"><a href="#" data-toggle="dropdown" style="display:inline-block" class="dropdown-toggle btn btn-primary hide-on-print"><i class="fa fa-wrench"></i>  <g:message code="entity.show.designs_tools"/> <span class="caret"></span></a>
                            <ul  class="dropdown-menu">
                                <g:if test="${entity?.manageable}">
                                    <li><opt:link controller="entity" action="addIndicators" params="[indicatorUse: 'design']"><g:message code="entity.show.indicators" /></opt:link></li>
                                </g:if>
                                <g:else>
                                    <li><a href="#" class="disableNonButtonField" disabled="disabled"><g:message code="entity.show.indicators" /></a></li>
                                </g:else>
                            <%-- showHiddenDesign --%>
                                <g:if test="${designs && (designsNotHidden && (designsNotHidden.size() != designs.size()) || !designsNotHidden)}">
                                    <li>
                                        <a class="pull-left" href="javascript:" onclick="$('#showHiddenDesign').modal()"><i class="fas fa-eye"></i> <g:message code="entity.showAllHiddenDesign" /></a>
                                    </li>
                                </g:if>
                            <%-- showHiddenDesign --%>
                                <sec:ifAnyGranted roles="ROLE_SYSTEM_ADMIN">
                                    <g:if test="${isDeletedDesignsExists}">
                                        <li>
                                            <a class="pull-left" href="javascript:"
                                               onclick="$('#showDeletedDesign').modal()"><i
                                                    class="fas fa-eye"></i> <g:message
                                                    code="entity.showAllDeletedDesign"/></a>
                                        </li>
                                    </g:if>
                                </sec:ifAnyGranted>
                                <g:if test="${userService.getSuperUser(user)}">
                                    <li>
                                        <a class="pull-left" href="javascript:"
                                           onclick="$('#importDesignModal').modal()">
                                            <g:message code="import.design.json"/>
                                        </a>
                                    </li>
                                </g:if>
                                <g:if test="${compareLicensed}">
                                    <li><opt:link controller="entity" action="compare"
                                                  params="[type: 'design']"><g:message
                                                code="entity.compare"/></opt:link></li>
                                </g:if>
                                <g:if test="${oldGraphsLicensed}">
                                    <li><opt:link controller="design" action="graphs"><g:message
                                            code="entity.graphs"/></opt:link></li>
                                </g:if>
%{--                                Deprecate code, to be removed by the end of feature 0.5.0 release--}%
%{--                                <g:if test="${benchmarkLicensed}">--}%
%{--                                    <li><a href="javascript:" onclick="$('#designBenchmarks').modal();"><g:message--}%
%{--                                            code="entity.add_benchmark"/></a></li>--}%
%{--                                </g:if>--}%
                            </ul>
                        </div>
                    </g:if>
                </g:if>
                <g:else>
                    <div class="pull-left"><g:message code="entity.show.no_children" /></div>
                    <g:if test="${overOneIndicator}">
                        <a href="javascript:" class="btn btn-primary hide-on-print" id="getStartedDesignBtn" onclick="$('#addIndicatorsDesign').modal();  preventZeroIndicators('#getStartedSubmitDesigns', 'indicatorCheckboxDesign');"><g:message code="start" /> </a>
                    </g:if>
                    <g:else>
                        <a href="javascript:" class="btn btn-primary hide-on-print" id="getStartedDesignBtn" onclick="filterSelectLCA(${entity?.isLCAParameterQueryReady}, '${message(code: "next")}'); $('#addIndicatorsDesign').modal(); preventZeroIndicators('#getStartedSubmitDesigns', 'indicatorCheckboxDesign');"><g:message code="start" /> </a>
                    </g:else>
                </g:else>
            </div>
            <g:set var="args">${designs ? designs.size() : 0}</g:set>
            <h2 class="h2expander" data-name="design" style="margin-left: 15px;">
                <g:message code="entity.show.designs.title" args="[args]" />
                <g:if test="${designs && (designsNotHidden && (designsNotHidden.size() != designs.size()) || !designsNotHidden)}">
                    <span class="help-block-inline">${message(code:'hidden_designs_subtitle', args: [designs?.findAll{it.isHiddenDesign}?.size()])}</span>
                </g:if>
            </h2>
        </div>

        <div class="sectionbody" style="padding-bottom:0 !important;">
            <g:if test="${selectedDesignIndicators && designs}">
                <g:set var="validLicenseFeatures" value="${validLicenses?.collect({it.licensedFeatures})?.flatten()?.unique({it?.featureId})}" />
                <g:set var="preventReportGenerationLicensed" value="${validLicenseFeatures?.find({Feature.PREVENT_REPORT_GENERATION.equals(it?.featureId)}) ? true : false}" />
                <g:set var="importMapperLicensed" value="${validLicenseFeatures?.find({ Feature.IFC_FROM_SIMPLE_BIM.equals(it?.featureId)|| Feature.IMPORT_FROM_FILE.equals(it?.featureId)}) ? true : false}" />
                <g:set var="bimModelCheckerLicensed" value="${validLicenseFeatures?.find({Feature.BIM_MODEL_CHECKER.equals(it?.featureId)}) ? true : false}" />
                <g:set var="simulationToolLicensed" value="${validLicenseFeatures?.find({ Feature.CARBON_DESIGNER.equals(it?.featureId)}) ? true : false}" />
                <g:set var="EPD_LCA" value="${validLicenseFeatures?.find({Feature.EPD_LCA.equals(it?.featureId)}) ? true : false}" />
                <g:set var="EPD_TRACI" value="${validLicenseFeatures?.find({Feature.EPD_TRACI.equals(it?.featureId)}) ? true : false}" />
                <g:set var="EPD_EN15804A1" value="${validLicenseFeatures?.find({ Feature.EPD_EN15804A1.equals(it?.featureId)}) ? true : false}" />
                <g:set var="EPD_EN15804A2" value="${validLicenseFeatures?.find({Feature.EPD_EN15804A2.equals(it?.featureId)}) ? true : false}" />
                <g:set var="EPD_EN15804A2_optional" value="${validLicenseFeatures?.find({Feature.EPD_EN15804A2.equals(it?.featureId)}) ? true : false}" />
                <g:set var="EPD_EN15804A2_INIES" value="${validLicenseFeatures?.find({Feature.EPD_EN15804A2.equals(it?.featureId)}) ? true : false}" />

                <g:set var="entityCountryResourceId" value="${entity.countryResourceResourceId}" />
                <table cellspacing="0" class="table">
                        <thead>
                        <tr>
                            <th class="design"><g:message code="entity.show.indicator"/></th>
                            <th class="design"><g:message code="entity.show.unit" /></th>

                            <g:each in="${designsNotHidden}" var="design" status="designIndex">
                                <th class="design right-align ${design.chosenDesign ? 'chosenDesign' : ''}">
                                    <div class="btn-group ">
                                        <g:set var="scope" value="${design?.scope}" />
                                        <g:set var="designScopeTable" value="${popupTableDesignMap?.get(design?.id?.toString() ?: "") ?: ""}" />
                                        <g:set var="popupTableDesignUpdate" value="${popupTableDesignUpdateMap?.get(design?.id?.toString() ?: "") ?: ""}" />
                                        <a class="${designPopupName} dropdown-toggle btn btn-link index-${designIndex}" href="#" data-toggle="dropdown" rel="popover" data-trigger="hover" id="${designIndex == 0 ? 'firstDesignDropdown' : ''}" data-content="${design.component ? "<img src='/app/assets/constructionTypes/structure.png' class='constructionType'></img> " : ''}${designScopeTable + popupTableDesignUpdate} ${design.targetedNote ? '<br /><br />' + design.targetedNote.encodeAsHTML() : ''}"  data-html="true">
                                            <g:if test="${design.getConflictingIndicatorsSelected(selectedDesignIndicators)}"><asset:image src="img/icon-warning.png" style="max-width:14px" class="conflictingIndicatorsWarn" data-content="${g.message(code: "design.conflicting_indicators")}"/></g:if>
                                            <g:if test="${design.superVerified}">
                                                <i class="fa fa-check oneClickColorScheme"></i>
                                            </g:if>
                                            <g:elseif test="${design.locked}">
                                                <i class="fas fa-lock"></i>
                                            </g:elseif>${design.component ? "<img src='/app/assets/constructionTypes/structure.png' class='constructionType'></img> " : ''}<g:if test="${designs?.size() <= 3}">${design.operatingPeriodAndName}</g:if><g:else><g:abbr maxLength="20" value="${design.operatingPeriodAndName}" /></g:else>${design.targetedNote.encodeAsHTML() ? ' !' : ''} <span class="caret"></span>
                                        </a>
                                        <script type="text/javascript">
                                            var designInfo_${designIndex} = {
                                                content:"${design.component ? '<img src=\"/app/assets/constructionTypes/structure.png\" class=\"constructionType\"></img> ' : ''}${designScopeTable + popupTableDesignUpdate} ${design.targetedNote ? '<br /><br />' + design.targetedNote.encodeAsHTML() : ''}",
                                                trigger:'hover',
                                                html:true,
                                                placement:'bottom',
                                                container:'body',
                                                template: '<div class="popover popover-fade" style="display: block;"><div class="arrow"></div><div class="popover-content"></div></div>'
                                            };
                                            $("a.index-${designIndex}").popover(designInfo_${designIndex});
                                        </script>
                                        <g:if test="${!entity?.readonly}">
                                            <ul class="dropdown-menu left-align" id="${designIndex == 0 ? 'firstDesignDropdownMenu' : ''}" >
                                                <g:if test="${!design.locked}">
                                                    <li><a onclick="modifyDesignModal('${design?.id?.toString()}', '${modalFormId}')"><i class="icon-tags"></i> <g:message code="modify" /></a></li>
                                                    <li><opt:link controller="note" action="form" params="[targetEntityId: design.id, type: 'note']"><i class="icon-tags"></i> <g:message code="note.add" /></opt:link></li>
                                                    <g:if test="${!preventMultipleDesignsLicened}">
                                                        <li><a onclick="copyDesignModal('${design?.id?.toString()}', '${modalFormId}')"><i class="icon-tags"></i> <g:message code="copy" /></a></li>
                                                    </g:if>
                                                    <g:if test="${enableAverageDesignFeature}">
                                                        <g:if test="${disableAverageFeatureInMenu}">
                                                            <li><a class="enterpriseCheck"><i
                                                                        class="icon-tags"></i> <g:message
                                                                        code="averagedesign.notenabled_for_indicator"/>
                                                            </a></li>
                                                        </g:if>
                                                        <g:else><li><a href="#"
                                                                       onclick="averageDesignModal('${design?.id?.toString()}', '${modalFormId}')"><i
                                                                    class="icon-tags"></i> <g:message
                                                                    code="create.average.design"/></a></li></g:else>
                                                    </g:if>
                                                    <g:if test="${entity?.manageable}">
                                                        <li><opt:link controller="design"
                                                                      action="remove" id="${design.id}"
                                                                      onclick="return modalConfirm(this)"
                                                                      data-questionstr="${message(code:'design.delete.question', args: [design.name])}"
                                                                      data-questionstr2="${message(code:'design.delete.step2Question')}"
                                                                      data-truestr="${message(code:'delete')}"
                                                                      data-falsestr="${message(code:'cancel')}"
                                                                      data-titlestr="${message(code:'design.delete.header') }"><i class="icon-trash redScheme"></i> <g:message code="delete" />
                                                        </opt:link></li>
                                                        <li><a href="javascript:" onclick="openDesignLockModal('${entity.id.toString()}','${design.id.toString()}','${design.name}', false)"><i class="fas fa-lock oneClickColorScheme"></i> <g:message code="design.lock" /></a></li>
                                                    </g:if>
                                                    <g:if test="${entity?.manageable || userService.getConsultant(user)}">
                                                        <sec:ifAnyGranted roles="ROLE_SUPER_USER,ROLE_CONSULTANT"><li><a href="javascript:" onclick="openDesignLockModal('${entity.id.toString()}','${design.id.toString()}','${design.name}', true)"><i class="fas fa-lock oneClickColorScheme"></i> <g:message code="design.lock.superuser" /></a></li></sec:ifAnyGranted>
                                                    </g:if>
                                                    <li><a href="javascript:" class="disabled" onclick="stopBubblePropagation(event)" style="cursor: default;"><i class="fa fa-check" onclick="stopBubblePropagation(event)"></i> Approve as a verified template </a></li>
                                                    <li><a class="pointerCursor" onclick="hideChildEntity('${entity?.id}', '${design?.id?.toString()}')"><i class="far fa-eye-slash" aria-hidden="true"></i> <g:message code="entity.hideDesign" /></a></li>
                                                </g:if>
                                                <g:elseif test="${design.superLocked}">
                                                    <li><a href="#" class="disabled"><i class="icon-tags"></i> <g:message code="modify" /></a></li>
                                                    <g:if test="${!preventMultipleDesignsLicened}">
                                                        <li><a onclick="copyDesignModal('${design?.id?.toString()}', '${modalFormId}')"><i class="icon-tags"></i> <g:message code="copy" /></a></li>
                                                    </g:if>
                                                    <li><a href="#" class="disabled"><i class="icon-trash redScheme"></i> <g:message code="delete" /></a></li>
                                                    <li><span class="locked"> <g:message code="locked_by_superuser" /> <g:formatDate date="${design.lockingTime}" format="dd.MM.yyyy HH:mm" /></span></li>
                                                    <sec:ifAnyGranted roles="ROLE_SUPER_USER,ROLE_CONSULTANT">
                                                        <li><opt:link controller="design" action="unlock" params="[designId: design.id, superUser: true]"><i class="fas fa-unlock redScheme"></i> <g:message code="design.unlock.superuser" /> </opt:link></li>
                                                        <g:if test="${design.superVerified}">
                                                            <li><opt:link controller="design" action="unverify" params="[designId: design.id]"><i class="fa fa-times redScheme"></i> Disapprove as a verified template </opt:link></li>
                                                        </g:if>
                                                        <g:else>
                                                            <li><opt:link controller="design" action="verify" params="[designId: design.id]"><i class="fa fa-check oneClickColorScheme"></i> Approve as a verified template </opt:link></li>
                                                        </g:else>
                                                    </sec:ifAnyGranted>
                                                    <li><a href="#" class="disabled"><i class="far fa-eye-slash" aria-hidden="true"></i> <g:message code="entity.hideDesign" /></a></li>
                                                </g:elseif>
                                                <g:else>
                                                    <li><a href="#" class="disabled"><i class="icon-tags"></i> <g:message code="modify" /></a></li>
                                                    <g:if test="${!preventMultipleDesignsLicened}">
                                                        <li><a onclick="copyDesignModal('${design?.id?.toString()}', '${modalFormId}')"><i class="icon-tags"></i> <g:message code="copy" /></a></li>
                                                    </g:if>
                                                    <li><a href="#" class="disabled"><i class="icon-trash redScheme"></i> <g:message code="delete" /></a></li>
                                                    <li><span class="locked"> <g:message code="locked" /> <g:formatDate date="${design.lockingTime}" format="dd.MM.yyyy HH:mm" /></span></li>
                                                    <g:if test="${entity?.manageable || userService.getConsultant(user)}">
                                                        <li><opt:link controller="design" action="unlock" params="[designId: design.id]"><i class="fas fa-unlock redScheme"></i> <g:message code="design.unlock" /> </opt:link></li>
                                                    </g:if>
                                                    <li><a href="#" class="disabled"><i class="far fa-eye-slash" aria-hidden="true"></i> <g:message code="entity.hideDesign" /></a></li>
                                                </g:else>
                                            <g:if test="${entity?.manageable}">
                                                <g:if test="${design.allowAsBenchmark}">
                                                    <li><opt:link controller="design" action="rejectAsBenchmark" params="[designId: design.id]"><i class="fas fa-times redScheme" aria-hidden="true"></i> <g:message code="design.rejectAsBenchmark" /> </opt:link></li>
                                                </g:if>
                                                <g:else>
                                                    <li><opt:link controller="design" action="allowAsBenchmark" params="[designId: design.id]"><i class="fa fa-check oneClickColorScheme" aria-hidden="true"></i> <g:message code="design.allowAsBenchmark" /> </opt:link></li>
                                                </g:else>
                                                <sec:ifAnyGranted roles="ROLE_SUPER_USER">
                                                    <g:if test="${design.carbonHero}">
                                                        <li><opt:link controller="design" action="unsetAsCarbonHero" params="[designId: design.id]"><i class="fas fa-times redScheme" aria-hidden="true"></i> Remove from Carbon Heroes </opt:link></li>
                                                    </g:if>
                                                    <g:else>
                                                        <li><opt:link controller="design" action="setAsCarbonHero" params="[designId: design.id]"><i class="fa fa-star oneClickColorScheme"></i> Add to Carbon Heroes </opt:link></li>
                                                    </g:else>
                                                </sec:ifAnyGranted>
                                                <g:if test="${design.chosenDesign}">
                                                    <li><opt:link controller="design" action="unSetAsChosen" params="[designId: design.id]"><i class="fas fa-times redScheme" aria-hidden="true"></i> <g:message code="remove_chosen_design"/> </opt:link></li>
                                                </g:if>
                                                <g:else>
                                                    <li><opt:link controller="design" action="setAsChosen" params="[designId: design.id]"><i class="fa fa-star oneClickColorScheme"></i> <g:message code="chosen_design"/> </opt:link></li>
                                                </g:else>
                                            </g:if>
                                            <g:if test="${jsonExportLicensed && !userService.getConsultant(user)}">
                                                <li><opt:link controller="entity" action="showjson" id="${design.id}"><g:message code="entity.as_json" /></opt:link></li>
                                            </g:if>
                                            <g:if test="${userService.getSuperUser(user) && !design.enableAsUserTestData}">
                                            <li><opt:link controller="entity" action="enableAsUserTestData" params="[entityId:entity.id, childId:design.id]"><i class="fa fa-check oneClickColorScheme" aria-hidden="true"></i> Superuser enable for test</opt:link></li>
                                        </g:if><g:elseif test="${userService.getSuperUser(user) && design.enableAsUserTestData}">
                                            <li><opt:link controller="entity" action="disableAsUserData" params="[entityId:entity.id, childId:design.id]"><i class="fas fa-times redScheme" aria-hidden="true"></i> Superuser Disable as test</opt:link></li>
                                        </g:elseif>
                                            </ul>
                                        </g:if>
                                    </div>
                                </th>
                            </g:each>
                            <g:each in="${designBenchmarks}" var="benchmark">
                                <th class="design">
                                	<div class="btn-group">
                                        <a class="dropdown-toggle btn btn-link" href="#" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${benchmark.name}"><g:abbr maxLength="20" value="${benchmark.name}" /> <span class="caret"></span></a>
                                	    <ul class="dropdown-menu">
                                	        <li><opt:link controller="entity" action="removeBenchmark" id="${benchmark.id}" onclick="return modalConfirm(this);"
                                		            data-questionstr="${message(code:'entity.remove_benchmark.question', args: [benchmark.name])}"
                                		            data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}" data-titlestr="${message(code:'entity.remove_benchmark.header') }"><i class="icon-trash redScheme"></i> <g:message code="delete" /></opt:link></li>
                                	    </ul>
                                    </div>
                                </th>
                            </g:each>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${selectedDesignIndicators}" var="indicator" status="index">
                            <g:set var="indicatorLicenseName" value=""/>
                            <g:if test="${indicator.licensedNamequalifiers}">
                                <g:if test="${EPD_LCA}">
                                    <g:set var="indicatorLicenseName" value="${indicatorService.getLocalizedLicensedNameQualifier(indicator, Feature.EPD_LCA)}" />
                                </g:if>
                                <g:elseif test="${EPD_TRACI}">
                                    <g:set var="indicatorLicenseName" value="${indicatorService.getLocalizedLicensedNameQualifier(indicator, Feature.EPD_TRACI)}" />
                                </g:elseif>
                                <g:elseif test="${EPD_EN15804A1}">
                                    <g:set var="indicatorLicenseName" value="${indicatorService.getLocalizedLicensedNameQualifier(indicator, Feature.EPD_EN15804A1)}" />
                                </g:elseif>
                                <g:elseif test="${EPD_EN15804A2}">
                                    <g:set var="indicatorLicenseName" value="${indicatorService.getLocalizedLicensedNameQualifier(indicator, Feature.EPD_EN15804A2)}" />
                                </g:elseif>
                                <g:elseif test="${EPD_EN15804A2_optional}">
                                    <g:set var="indicatorLicenseName" value="${indicatorService.getLocalizedLicensedNameQualifier(indicator, Feature.EPD_EN15804A2_OPTIONAL)}" />
                                </g:elseif>
                                <g:elseif test="${EPD_EN15804A2_INIES}">
                                    <g:set var="indicatorLicenseName" value="${indicatorService.getLocalizedLicensedNameQualifier(indicator, Feature.EPD_EN15804_A2_INIES)}" />
                                </g:elseif>
                            </g:if>
                            <tr rel="#" class="${"benchmark".equalsIgnoreCase(indicator.visibilityStatus) && !userService.getShowBenchmarkIndicators(user) ? 'hidden': 'isv'}">
                                <td class="design">

                                    <g:set var="locale" value="${LocaleContextHolder.getLocale().getLanguage().toUpperCase()}"/>
                                    <g:if test="${compiledReportLicensed && indicator.excelExportCategoryIds && !preventReportGenerationLicensed}">
                                        <div class="btn-group designTdDiv">
                                            <a class="dropdown-toggle multipleExportDropdown" style="text-decoration: none; color: black;" href="#" data-toggle="dropdown">${indicator.localizedShortName ? indicator.localizedShortName : indicator.localizedName}${indicatorLicenseName} <i class="fa fa-caret-down"></i></a> ${indicator.deprecated ? ' <span class=\"warningRed\">' + message(code: 'deprecated') + '</span>': ''}
                                            <ul class="dropdown-menu"><li><a href="javascript:" onclick="openDesignSelector('${indicator.indicatorId}','${entity.id}')"><i class="icon-results"></i> <g:message code="entity.form.report_generation" /></a></li></ul>
                                            <g:if test="${indicator?.indicatorHelpUrl}">&nbsp;<a href="${indicator?.indicatorHelpUrlTranslation}" target="_blank" class="helpLink"><i class="fa fa-question greenInfoBubble" aria-hidden="true"></i> <g:if test="${designs?.size() < 5}"> &nbsp; <g:message code="help.instructions" /></g:if></a></g:if>
                                            <g:if test="${indicator?.useSpecificServiceLifes && entity?.defaults?.get(com.bionova.optimi.construction.Constants.DEFAULT_SERVICELIFE) && entity?.defaults?.get(com.bionova.optimi.construction.Constants.DEFAULT_SERVICELIFE) != "resourceServiceLife"}">&nbsp;<asset:image src="img/icon-warning.png" style="max-width:16px" rel="popover" data-trigger="hover" data-content="${message(code: 'indicator_user_specific_servicelife', args: [indicator.localizedShortName ? indicator.localizedShortName : indicator.localizedName])}"/></g:if>
                                        </div>
                                    </g:if>
                                    <g:elseif test="${indicator.excelExportCategoryIds && preventReportGenerationLicensed}">
                                        <div class="btn-group designTdDiv">
                                            <a class="dropdown-toggle multipleExportDropdown" style="text-decoration: none; color: black;" href="#" data-toggle="dropdown">${indicator.localizedShortName ? indicator.localizedShortName : indicator.localizedName}${indicatorLicenseName} <i class="fa fa-caret-down"></i></a> ${indicator.deprecated ? ' <span class=\"warningRed\">' + message(code: 'deprecated') + '</span>': ''}
                                            <ul class="dropdown-menu"><li><a href="javascript:" class="enterpriseCheck" rel="popover" data-trigger="hover" data-content="${message(code: "available_in_commercial_licenses")}"><i class="icon-results"></i> <g:message code="entity.form.report_generation" /></a></li></ul>
                                            <g:if test="${indicator?.indicatorHelpUrl}">&nbsp;<a href="${indicator?.indicatorHelpUrlTranslation}" target="_blank" class="helpLink"><i class="fa fa-question greenInfoBubble" aria-hidden="true"></i> <g:if test="${designs?.size() < 5}"> &nbsp; <g:message code="help.instructions" /></g:if></a></g:if>
                                            <g:if test="${indicator?.useSpecificServiceLifes && entity?.defaults?.get(com.bionova.optimi.construction.Constants.DEFAULT_SERVICELIFE) && entity?.defaults?.get(com.bionova.optimi.construction.Constants.DEFAULT_SERVICELIFE) != "resourceServiceLife"}">&nbsp;<asset:image src="img/icon-warning.png" style="max-width:16px" rel="popover" data-trigger="hover" data-content="${message(code: 'indicator_user_specific_servicelife', args: [indicator.localizedShortName ? indicator.localizedShortName : indicator.localizedName])}"/></g:if>
                                        </div>
                                    </g:elseif>
                                    <g:elseif
                                            test="${indicator?.exportXML in FrenchConstants.RSEE_EXPORT_XML && !preventReportGenerationLicensed}">
                                        <div class="btn-group designTdDiv">
                                            <a class="dropdown-toggle multipleExportDropdown"
                                               style="text-decoration: none; color: black;" href="#"
                                               data-toggle="dropdown">
                                                ${indicator.localizedShortName ? indicator.localizedShortName : indicator.localizedName}${indicatorLicenseName}
                                                <i class="fa fa-caret-down"></i>
                                            </a>
                                            ${indicator.deprecated ? ' <span class=\"warningRed\">' + message(code: 'deprecated') + '</span>' : ''}
                                            <ul class="dropdown-menu">
                                                <li>
                                                    <a href="${createLink(controller: "xmlHandler", action: "renderRSEnv", params: [parentEntityId: entity.id, indicatorId: indicator.indicatorId])}">
                                                        <i class="fas fa-file-archive"></i>
                                                        <g:message code="results.export_xml"/>
                                                    </a>
                                                </li>
                                            </ul>
                                            <g:if test="${indicator?.indicatorHelpUrl}">
                                                &nbsp;
                                                <a href="${indicator?.indicatorHelpUrlTranslation}" target="_blank"
                                                   class="helpLink">
                                                    <i class="fa fa-question greenInfoBubble" aria-hidden="true"></i>
                                                    <g:if test="${designs?.size() < 5}">
                                                        &nbsp;
                                                        <g:message code="help.instructions"/>
                                                    </g:if>
                                                </a>
                                            </g:if>
                                            <g:if test="${indicator?.useSpecificServiceLifes && entity?.defaults?.get(com.bionova.optimi.construction.Constants.DEFAULT_SERVICELIFE) && entity?.defaults?.get(com.bionova.optimi.construction.Constants.DEFAULT_SERVICELIFE) != "resourceServiceLife"}">&nbsp;<asset:image
                                                    src="img/icon-warning.png" style="max-width:16px" rel="popover"
                                                    data-trigger="hover"
                                                    data-content="${message(code: 'indicator_user_specific_servicelife', args: [indicator.localizedShortName ? indicator.localizedShortName : indicator.localizedName])}"/></g:if>
                                        </div>
                                    </g:elseif>
                                    <g:else>
                                        <div class="designTdDiv">${indicator.localizedShortName ? indicator.localizedShortName : indicator.localizedName}${indicatorLicenseName}${indicator.deprecated ? ' <span class=\"warningRed\">' + message(code: 'deprecated') + '</span>': ''}
                                            <g:if test="${indicator?.indicatorHelpUrl}">&nbsp;<a href="${indicator?.indicatorHelpUrlTranslation}" target="_blank" class="helpLink"><i class="fa fa-question greenInfoBubble" aria-hidden="true"></i> <g:if test="${designs?.size() < 5}"> &nbsp; <g:message code="help.instructions" /></g:if></a></g:if>
                                            <g:if test="${indicator?.useSpecificServiceLifes && entity?.defaults?.get(com.bionova.optimi.construction.Constants.DEFAULT_SERVICELIFE) && entity?.defaults?.get(com.bionova.optimi.construction.Constants.DEFAULT_SERVICELIFE) != "resourceServiceLife"}">&nbsp;<asset:image src="img/icon-warning.png" style="max-width:16px" rel="popover" data-trigger="hover" data-content="${message(code: 'indicator_user_specific_servicelife', args: [indicator.localizedShortName ? indicator.localizedShortName : indicator.localizedName])}"/></g:if>
                                        </div>
                                    </g:else>
                                </td>
                                <td class="design designTdDiv"><calc:renderIndicatorUnit indicator="${indicator}" entity="${entity}"/></td>
                                <g:set var="queries" value="${queriesForSelectedDesignIndicators.get(indicator.indicatorId)}"/>
                                <g:set var="queryIds" value="${queries?.collect({ it?.queryId })}"/>

                                <g:each in="${designsNotHidden}" var="design" status="designIndex">
                                    <g:set var="isCalculationRunningForDesignIndicatorPair" value="${isCalculationRunning &&
                                            calculationProcesses.find{it.entityId == design?.id?.toString() && it.indicatorId == indicator?.indicatorId}}"/>
                                    <g:set var="isLocked" value="${design.locked || design.superLocked}"/>
                                    <td class="design ${design.chosenDesign ? 'chosenDesign' : ''} ${design.chosenDesign && selectedDesignIndicators?.size() == index + 1 ? 'lastChosenTd' : ''}">
                                        <div class="btn-group right-align" >
                                            <g:if test="${design.disabledIndicators?.contains(indicator.indicatorId)}">
                                                    <a style="padding-bottom:10px; padding-right: 10px; color: gray;"  class="smallaction dropdown-toggle" href="#" data-toggle="dropdown" data-tooltip="${message(code: 'design.form.indicator_help_main_entity')}" >
                                                    <g:message code="design.indicator.not_in_use" default="Not in use"/></a>
                                            </g:if>
                                            <g:else>
                                                <g:if test="${design.locked}">
                                                    <a data-name="${design.name}${indicator.indicatorId}input" style="padding-bottom: 10px; padding-right: 5px;"
                                                    class="dropdown-toggle smallaction  smoothTip tooltip--right designElement" data-toggle="dropdown"
                                                    data-tooltip="${message(code: 'view_locked_data_tooltip')}" href="#"
                                                    id="${index == 0 && designIndex == 0 ? 'firstDesignInputBtn' : ''}"
                                                    data-designId="${design.id.toString()}" data-indicatorId="${indicator.indicatorId}"
                                                    ${isCalculationRunningForDesignIndicatorPair ? "data-isCalculationInProgress='true'" : ""}>
                                                </g:if>
                                                <g:else>
                                                    <a data-name="${design.name}${indicator.indicatorId}input" style="padding-bottom: 10px; padding-right: 5px;"
                                                    class="dropdown-toggle smallaction  smoothTip tooltip--right designElement" data-toggle="dropdown"
                                                    data-tooltip="${message(code: 'input_data_tooltip')}" href="#"
                                                    id="${index == 0 && designIndex == 0 ? 'firstDesignInputBtn' : ''}"
                                                    data-designId="${design.id.toString()}" data-indicatorId="${indicator.indicatorId}"
                                                    ${isCalculationRunningForDesignIndicatorPair ? "data-isCalculationInProgress='true'" : ""}>
                                                </g:else>
                                                <g:set var="scoreValid" value="${calc.areResultsValid(entity: design, indicator: indicator, queryIds: queryIds)}" />
                                                <g:if test="${scoreValid && !isCalculationRunningForDesignIndicatorPair}">
                                                    <calc:renderDisplayResult entity="${design}" indicator="${indicator}" useAppendValue="true" noScientificNumbers="true"
                                                                              displayMainPage="true" queryIds="${queryIds}" featuresAvailableForEntity="${featuresAvailableForEntity}"/>
                                                </g:if>
                                                <g:elseif test="${isCalculationRunningForDesignIndicatorPair}">
                                                    <i class='fas fa-circle-notch fa-spin oneClickColorScheme'></i>
                                                </g:elseif>
                                                <g:else>
                                                    <g:message code="recalculate" />
                                                </g:else>
                                                <i class="${isLocked ? 'fas fa-lock' : 'fas fa-pencil-alt'} grayLink"></i> <i class="fa fa-caret-down grayLink" aria-hidden="true" ></i>
                                                </a>
                                            </g:else>
                                            <g:render template="dropdownMenuList" model="[isLocked: isLocked, expandQueryIfOne:expandQueryIfOne,index:index,designIndex:designIndex,scoreValid:scoreValid,queries:queries,designParameterQueries:designParameterQueries,
                                                                                          indicatorBenchmarkLicensed:indicatorBenchmarkLicensed,carbonDesignerOnlyLicensed:carbonDesignerOnlyLicensed,carbonDesignerAndResultsOnlyLicensed:carbonDesignerAndResultsOnlyLicensed,draftByDesign:draftByDesign,
                                                                                          showResultReports:showResultReports,entity:entity,design:design, indicator:indicator, validLicenses:validLicenses,entityCountryResourceId:entityCountryResourceId,
                                                                                          entityType:entityType,simulationToolLicensed:simulationToolLicensed,bimModelCheckerLicensed:bimModelCheckerLicensed,licenseTypes:licenseTypes,
                                                                                          importMapperLicensed:importMapperLicensed,queriesForIndicator:queries, isCalculationRunningForDesignIndicatorPair: isCalculationRunningForDesignIndicatorPair]"/>

                                        </div>
                                   </td>
                            </g:each>
                            <g:each in="${designBenchmarks}" var="benchmark"><%--
                                --%><td class="design"><a href="#" class="black"><calc:benchmarkValue benchmark="${benchmark}" indicator="${indicator}"/></a></td>
                            </g:each>
                        </tr>
                        </g:each>
                        </tbody>
                </table>
            </g:if>
        </div>
    </div>
    <g:if test="${benchmarkLicensed}">
        <g:render template="addbenchmarks"
                  model="[entity: entity, portfolios: designPortfolios, type: 'design', modalId: 'designBenchmarks']"/>
    </g:if>
    <g:render template="/entity/modal/showHiddenDesign"/>
    <g:render template="/entity/modal/showDeletedDesign"/>
    <g:if test="${userService.getSuperUser((User) user)}">
        <g:render template="/entity/modal/importDesign"/>
    </g:if>

</g:if>
<g:render template="/entity/modal/addIndicator"/>

<%-- DIV MODIFY DESIGN MODAL AND FORM--%>
<span id="modifyModalDivContainer" class="modalDesignScope">
<g:uploadForm action="save" controller="design" name="${modalFormId}" class="noMargin">
    <div class="modal hide modalDesignScope" id="modifyModalDiv"></div>
</g:uploadForm>
<g:uploadForm action="saveAverageDesign" controller="design" name="${modalFormId}" class="noMargin">
    <div class="modal hide modalDesignScope" id="averageDesignModalDiv"></div>
</g:uploadForm>
</span>
<script type="text/javascript">
    $(function () {
        maskAllSelectInDivWithSelect2('addIndicatorsDesign', false, '${message(code:'select')}')
    })

<g:if test="${isCalculationRunning}">
    $(document).ready(function() {
        isCalculationRunningFunction();
    });

    const intervalTime = 5000
    let checkMessagesTimeout

    function isCalculationRunningFunction(){
        const designElements = $(".designElement")
        const delimiter = ':'

        $.ajax({
            url: "/app/sec/entity/isCalculationRunningForEntityPage",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({
                entityId: '${entity?.id?.toString()}',
            }),
            type:"POST",
            success: function(data){
                const runningProcessesForParentEntity = data.output

                if (runningProcessesForParentEntity.length > 0){
                    const carbonSpinner = "<i class='fas fa-circle-notch fa-spin oneClickColorScheme'></i>"
                    disableGraphsAndShowMessageWithSpinner()
                    disableOrEnableElement($("#carbonDesigner3d"), true)
                    let elementsForGettingCalculationResults = new Map();

                    designElements.each(function (){
                        let designDropdownElementInCalculation = runningProcessesForParentEntity.filter(obj => {
                            return obj.entityId == $(this).attr("data-designId") && obj.indicatorId == $(this).attr("data-indicatorId")
                        })

                        let spinElement = $(this).find(".fa-spin")

                        if (designDropdownElementInCalculation.length > 0){
                            elementsForDesignDropDown(this, true)

                            if (typeof $(this).attr("data-isCalculationInProgress") === 'undefined' ||
                                $(this).attr("data-isCalculationInProgress") == 'false'){

                                $(this).contents().filter(function () {
                                    return this.nodeType === 3 || this.tagName.toLowerCase() == 'span';
                                }).remove();

                                $(this).prepend(carbonSpinner)
                                $(this).attr("data-isCalculationInProgress", true)
                            }
                        } else {
                            if ($(this).attr("data-isCalculationInProgress") == 'true'){
                                $(this).attr("data-isCalculationInProgress", false)
                                elementsForDesignDropDown(this, false)
                                elementsForGettingCalculationResults.set($(this).attr("data-designId") + delimiter
                                    + $(this).attr("data-indicatorId"), this)
                                $(spinElement).remove()
                            }
                        }
                    });

                    getCalculationResultForDesignEntityAndIndicator(elementsForGettingCalculationResults)
                    checkMessagesTimeout = setTimeout(function(){
                        isCalculationRunningFunction();
                    }, intervalTime);
                } else {
                    let elementsForGettingCalculationResults = new Map();
                    closeFlashAlert('permWarningAlert');
                    renderMainEntityCharts('${entity?.id}', '${allValidIndicatorsAllTypes[0]?.indicatorId}', null, ${allValidIndicatorsAllTypesIds as grails.converters.JSON})
                    $("#messageContent").empty()
                    $("#calculationRunningMessageForCharts").hide();
                    $("#focalDivFourPack").find("li[role='presentation']").first().addClass("active")

                    designElements.each(function (){
                        if ($(this).attr("data-isCalculationInProgress") == 'true'){
                            let spinElement = $(this).find(".fa-spin")
                            $(this).attr("data-isCalculationInProgress", false)
                            elementsForDesignDropDown(this, false)
                            elementsForGettingCalculationResults.set($(this).attr("data-designId") + delimiter
                                + $(this).attr("data-indicatorId"), this)
                            $(spinElement).remove()
                        }
                    });

                    disableOrEnableElement($("#carbonDesigner3d"), false)
                    getCalculationResultForDesignEntityAndIndicator(elementsForGettingCalculationResults)
                }
            },
            error: function(error, textStatus){
                console.log(error+ ": "+ textStatus)
            }
        })
    }

    function disableGraphsAndShowMessageWithSpinner(){
        $("#allChartsWrapper").hide();
        $("#loadingSpinner_overview").show();
        $("#calculationRunningMessageForCharts").show();
    }

    function getCalculationResultForDesignEntityAndIndicator(resultDropdown){
        if (resultDropdown.size > 0) {
            let designAndIndicatorForResultCalculation = Array.from(resultDropdown.keys())

            $.ajax({
                url: "/app/sec/entity/getCalculationResultForDesignEntityAndIndicator",
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify({
                    parentEntityId: '${entity?.id?.toString()}',
                    designAndIndicatorForCalculation: designAndIndicatorForResultCalculation
                }),
                type: "POST",
                success: function (data) {
                    let responseMap = data.output;

                    for (let entry of resultDropdown) {
                        let result = responseMap[entry[0]]

                        if(result.length > 0){
                            $(entry[1]).prepend(result)
                        }
                    }
                }
            })
        }
    }

    function elementsForDesignDropDown(designElement, disable){
        let elements = $(designElement).parent().find("ul").find(".disableWhenCalculationRunning")

        elements.each(function(){
            disableOrEnableElement($(this), disable)
        })
    }

    function disableOrEnableElement(element, disable){
        if (disable){
            $(element).addClass("removeClicks")
        } else {
            $(element).removeClass("removeClicks")
        }
    }
</g:if>
</script>
