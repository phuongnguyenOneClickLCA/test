<!doctype html>
<html>
<head>
    <asset:stylesheet src="bootstrap.css"/>
    <asset:stylesheet src="layout.css"/>
    <asset:stylesheet src="content.css"/>
    <asset:stylesheet src="jquery.dataTables-custom.css"/>
    <asset:stylesheet src="jquery-ui-1.8.21.custom.css"/>
    <meta name="format-detection" content="telephone=no"/>
    <g:set var="optimiResourceService" bean="optimiResourceService"/>
</head>

<body>

<div class="container section">
    <g:if test="${identifiedDatasets || ambiguousDatasets || denyMappingDatasets || partialQuantificationDatasets}">
        <div class="container section">
            <div class="sectionheader"  id="importSummary">
                <h4> <opt:link controller="main" removeEntityId="true"><g:message code="main" /></opt:link>
                    <sec:ifLoggedIn>
                        <g:if test="${entity}">
                            > <opt:link controller="entity" action="show" id="${entity?.id}">
                            <g:abbr value="${entity?.name}" maxLength="20" />
                        </opt:link>
                            <g:if test="${childEntity}">
                                > <span id="childEntityName">${childEntity?.operatingPeriodAndName}</span>
                            </g:if>
                        </g:if>
                    </sec:ifLoggedIn>
                    > <g:message code="import_data" /> <br/> </h4>
                <div class="pull-right hide-on-print">
                    <input type="button" onclick="window.close();" class="btn" value="${message(code: 'close')}"/>
                </div>
                <h2 class="h2expander" style="display: inline;"><g:message code="importMapper.resolver_summary" args="[datasetSize]" />  </h2>
                <span style="display: inline; margin-left: 10px;" rel="popover" data-content="Show summary and recommended actions for imported data" data-trigger="hover"><i class="fa fa-question warningQuestionMark"></i></span>
            </div>

            <div class="sectionbody">
                <div class="importSummaryTexts">
                    <g:if test="${ambiguousDatasets}">
                        <span><g:message code="importMapper.resolver.unidentified_data" />: ${ambiguousDatasets.size()}  </span>
                        <br/>
                    </g:if>
                    <g:if test="${partialQuantificationDatasets}">
                        <span><g:message code="importMapper.resolver.unquantified_data" />: ${partialQuantificationDatasets.size()}  </span>
                        <br/>
                    </g:if>
                    <g:if test="${identifiedDatasets}">
                        <span><g:message code="importMapper.resolver.identified_data" />: ${identifiedDatasets.size()}  </span>
                        <br/>
                    </g:if>
                    <g:if test="${unquantifiedDatasets}">
                        <span><g:message code="importMapper.resolver.unquantified_data" /> ${unquantifiedDatasets.size()}</span>
                        <br/>
                    </g:if>


                    <g:if test="${denyMappingDatasets && genericDatasetsWarnings && !genericDatasetsWarnings.isEmpty()}">
                        <g:message code="importMapper.resolver_review_generic"/>
                        ${genericDatasetsWarnings.size()} -
                        <g:each status="i" in="${genericDatasetsWarnings.unique()}" var="warningString">
                            <span class="genericDataset"> ${warningString} ${genericDatasetsWarnings.lastIndexOf(warningString) == i ?  "" : genericDatasetsWarnings.size() <= 1 ?  "" : ","}</span>
                        </g:each>
                        <br>
                    </g:if>
                    <span>${warningMessage ? warningMessage + "</br>" : ""}</span>
                    <span>${okMessage}</span>
                </div>
            </div>
        </div>
    </g:if>
    <div class="sectionheader">
        <h2 class="h2expander" style="display: inline;"><g:message code="importMapper.ambiguous_data"/></h2>
    </div>
    <div class="sectionbody">
        <g:if test="${ambiguousDatasets}">
            <table class="table table-cellvaligntop" border="1">
                <thead>
                <tr><th><g:message code="importMapper.material"/></th>
                    <th><g:message code="resource.name"/></th>
                    <th><g:message code="query.quantity"/></th>
                    <th><g:message code="share"/></th>
                    <th><g:message code="note.comment"/></th>
                    <th><g:message code="importMapper.ambiguous_data.problems"/></th>
                </tr>
                </thead>
                <tbody>
                <g:set var="resourceCache" value="${com.bionova.optimi.data.ResourceCache.init(ambiguousDatasets)}"/>
                <g:each in="${ambiguousDatasets}" var="dataset">
                    <tr>
                        <g:set var="idOfDetails" value="${UUID.randomUUID().toString()}"/>
                        <td>
                            <g:set var="details">
                                <g:if test="${dataset.importDisplayFields}">
                                    <g:each in="${dataset.importDisplayFields}">
                                        <strong>${it.key}:</strong> ${it.value}<br/>
                                    </g:each>
                                </g:if>
                            </g:set>
                            <g:set var="detailsLink">${dataset.importDisplayFields?.get(materialKey)}</g:set>
                            <a href="javascript:" rel="popover" data-trigger="hover" class="detailsLink"
                               data-html="true" id="${idOfDetails}" data-content="${details}"><g:abbr value="${detailsLink}" maxLength="200"/></a>
                        </td>
                        <td><%--
                        --%><g:if test="${optimiResourceService.getUiLabel(resourceCache.getResource(dataset))?.length() > 40}"><%--
                            --%><a href="javascript:" class="just_black" rel="popover" data-trigger="hover"
                                   data-content="${resourceName}">${g.abbr(value: resourceName, maxLength: 200)}</a> <%--
                        --%></g:if><%--
                        --%><g:else><%--
                            --%>${optimiResourceService.getUiLabel(resourceCache.getResource(dataset))} <%--
                        --%></g:else><%--
                --%></td>
                        <td>
                            ${formatNumber(number: dataset.quantity, format: "###.##")}
                            <g:if test="${dataset.userGivenUnit != null}"><span
                                    class="add-on">${dataset.userGivenUnit}</span></g:if>
                            <g:set var="thickness"
                                   value="${dataset.additionalQuestionAnswers?.get("thickness_mm") ? dataset.additionalQuestionAnswers.get("thickness_mm") : ''}"/>
                            ${thickness ? '/ ' + thickness + ' mm' : ''}
                        </td>
                        <td${dataset.percentageOfTotal && dataset.percentageOfTotal > 1 ? ' style=\"font-weight: bold;\"' : ''}>${dataset.percentageOfTotal ? formatNumber(number: dataset.percentageOfTotal, format: "###.##") + ' %' : ''}</td>
                        <td>
                            ${dataset.additionalQuestionAnswers?.get("comment")}
                        </td>
                        <td>
                            <g:if test="${dataset.importMapperPartialQuantification}">
                                <g:message code="importMapper.resolver.unquantified_data"/><br/>
                            </g:if>
                            <g:if test="${dataset.importMapperCompositeMaterial}">
                                <g:message code="importMapper.ambiguous_data.composite_material"/><br/>
                            </g:if>
                            <g:if test="${!dataset.resourceId}">
                                <g:message code="importMapper.ambiguous_data.unidentified_material"/>
                            </g:if>
                        </td>
                    </tr>
                </g:each>
                </tbody>
            </table>
        </g:if>
        <g:else>
            <strong>No ambiguous data found</strong>
        </g:else>
    </div>
</div>
</body>
</html>