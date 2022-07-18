<!doctype html>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
</head>
<style>
    body {
        overflow-x: scroll;
        overflow-y: scroll;
    }
</style>
<body>
<%
    def userService = grailsApplication.mainContext.getBean("userService")
    def resourceTypeService = grailsApplication.mainContext.getBean("resourceTypeService")
%>
<div class="container">
    <div class="screenheader">
        <h1>Data benchmarks</h1>
    </div>
</div>

<div class="container section">
    <g:if test="${userService.getDataManagerAndAdmin(userService.getCurrentUser())}">
        <div style="float: left;">
            <g:form controller="oneClickLcaApi" action="downloadResourceDump">
                <div>
                    <h1>Download resource dump by params:</h1>
                    <label for="upstreamDBClassifiedSelect">upstreamDBClassified</label>
                    <select name="upstreamDBClassified" id="upstreamDBClassifiedSelect">
                        <option value=""></option>
                        <g:each in="${upstreamDBClassified}" var="upstreamDB">
                            <option value="${upstreamDB}">${upstreamDB}</option>
                        </g:each>
                    </select>
                </div>

                <div style="margin-top: 5px;">
                    <label for="impactCategorySelect">Impact categories</label>
                    <select name="impactCategories" id="impactCategorySelect" multiple>
                        <g:each in="${impactCategories}" var="impactCategory">
                            <option value="${impactCategory}">${impactCategory}</option>
                        </g:each>
                    </select>
                </div>
                <div style="margin-top: 5px;">
                    <input type="submit" class="left btn btn-primary" value="Download" />
                </div>
            </g:form>
        </div>
    <div class="clearfix"></div>
    <div>
        <h1>Benchmark ResourceSubTypes (active: ${amountOfActiveResourceSubtypes})</h1>
    </div>
        <g:form action="benchmarkSelected" id="benchmarkSelected" name="benchmarkSelected">
            <div class="clearfix"></div>
                <g:if test="${groupedSubTypes}"><%--
                --%><div class="buttonsdiv"><%--
                    --%><a href="javascript:" name="selectAll" class="btn btn-primary" style="margin-left: 10px; margin-bottom: 5px;" onclick="selectAll('resourceSubType')">Select All</a><%--
                    --%><g:submitButton name="benchmarkSelected" value="Benchmark Selected" class="btn btn-primary" style="margin-left: 5px; margin-bottom: 5px;" onclick="doubleSubmitPrevention('benchmarkSelected');" /><%--
                    --%><g:submitButton name="dataRanges" value="Download data ranges of selected" class="btn btn-info" style="margin-left: 5px; margin-bottom: 5px;" /><%--
                    --%><div class="thresholds" style="margin-left: 400px; float: right;">
                        <span style="display: inline;">
                            <g:if test="${showthresholdColumn}">
                                <opt:link class="btn btn-warning" controller="resourceType" action="benchmark">Clear filtering</opt:link>
                            </g:if>
                            <g:else>
                                <label>
                                    <select name="filterType">
                                        <option value="standardDeviation" selected="selected">Standard deviation (Give number of standard deviations e.g. 3)</option>
                                        <option value="changeInImpact">Change in impacts (Give minimum change of CO2 e.g. 0,5)</option>
                                    </select>
                                </label>
                                <label for="deviationThreshold"></label><input style="width: 80px; margin-left: 5px; margin-top: 3px;" type="text" id="deviationThreshold" name="deviationThreshold">
                                <g:submitButton name="getSubTypesByResourcesFailingGivenThreshold" value="Filter" class="btn btn-primary" style="margin-left: 5px; margin-bottom: 5px;" />
                            </g:else>
                        </span>

                        </div><%--
                --%></div><%--
                --%><g:each in="${groupedSubTypes}">
                    <table class="resource table table-striped table-condensed" id="resourceList">
                    <tr><th colspan="99">SUBTYPE GROUP: ${it.key}</th></tr>
                    <tr id="resourceListHeading">
                        <th>&nbsp;</th><th style="word-wrap: break-word; max-width: 90px;">ResourceType</th>
                        <th style="word-wrap: break-word; min-width: 90px;">SubType</th>
                        <th style="word-wrap: break-word; min-width: 90px;">Name</th>
                        <th style="word-wrap: break-word; min-width: 50px;">Standard Unit</th>
                        <th style="word-wrap: break-word; min-width: 50px;">Active Resources</th>
                        <th style="word-wrap: break-word; min-width: 50px;">Excluded Resources</th>
                        <g:if test="${showthresholdColumn}"><th style="word-wrap: break-word; min-width: 75px;">Failing threshold # outside ${threshold} standard deviations</th></g:if>
                        <th style="word-wrap: break-word; min-width: 300px;">Benchmark results:<br/> min - max - factor <i class="fa fa-question-circle factorBenchmark" rel="popover" data-trigger="hover"></i></th>
                        <th style="word-wrap: break-word; min-width: 150px;">AVERAGE</th>
                        <th style="word-wrap: break-word; min-width: 150px;">MEDIAN</th>
                        <th style="word-wrap: break-word; min-width: 150px;">STDDEV - VARIANCE</th>
                        <th style="word-wrap: break-word; min-width: 300px;">Change in CO2:<br/> min - max (avg) - factor</th><th>Graph</th><th>Export <i class="fa fa-question-circle exportBenchmark" rel="popover" data-trigger="hover"></i></th>
                        <th>Export as Kg <i class="fa fa-question-circle exportBenchmark" rel="popover" data-trigger="hover"></i></th>
                    </tr><%--
                --%><g:each in="${it.value}" var="resourceSubType"><%--
                --%><g:if test="${!showthresholdColumn || (showthresholdColumn && resourceSubType.failingGivenThreshold)}"><%--
                    --%><tr class="resourceSubType">
                          <td><g:checkBox type="checkbox" name="benchmarkSubType" checked="false" value="${resourceSubType.id}" class="selectCheckbox" style="width: 17px; !important; height: 17px; !important;"/></td>
                          <td style="word-wrap: break-word; min-width: 90px;">${resourceSubType.resourceType}</td>
                          <td style="word-wrap: break-word; min-width: 90px;">${resourceSubType.subType}</td>
                          <td style="word-wrap: break-word; min-width: 90px;">${resourceSubType.localizedName}</td>
                          <td>${resourceSubType.standardUnit}</td>
                          <td><a href="javascript:" onclick="window.open('${createLink(action: "showResources", params:[resourceType: resourceSubType.resourceType, subType: resourceSubType.subType, excludeFromBenchmark: false])}','showResourcesWindow', 'width=1024, height=768, scrollbars=1');">${resourceTypeService.getResourceSubTypeCountByExcluded(Boolean.FALSE, resourceSubType)}</a></td>
                          <td><a href="javascript:" onclick="window.open('${createLink(action: "showResources", params:[resourceType: resourceSubType.resourceType, subType: resourceSubType.subType, excludeFromBenchmark: true])}','showResourcesWindow', 'width=1024, height=768, scrollbars=1');">${resourceTypeService.getResourceSubTypeCountByExcluded(Boolean.TRUE, resourceSubType)}</a></td>
                          <g:if test="${showthresholdColumn}"><td><a href="javascript:" onclick="window.open('${createLink(action: "showResources", params:[resourceType: resourceSubType.resourceType, subType: resourceSubType.subType, failingThreshold: threshold, filterType: filterType])}','showResourcesWindow', 'width=1024, height=768, scrollbars=1');">${resourceSubType.failingGivenThreshold}</a></td></g:if>
                          <td style="word-wrap: break-word; min-width: 150px;">
                            <g:if test="${resourceSubType.benchmarkResult}">
                                <g:if test="${threshold}">
                                <g:each in="${benchmarks}" var="benchmark">
                                    <g:if test="${subtypeBenchmarkFailed?.get(resourceSubType.subType)?.contains(benchmark)}">
                                        <g:set var="factor" value="${resourceSubType.benchmarkResult?.factor?.get(benchmark)}"/>
                                        <b><a href="javascript:" onclick="fullScreenPopup('${createLink(action: "benchmarkGraph", params:[resourceSubTypeId: resourceSubType.id, benchmarkToShow: benchmark])}','showGraphWindow', 'width=1024, height=768, scrollbars=1');">${benchmarkNames.get(benchmark)} (${resourceSubType.benchmarkResult?.validValues?.get(benchmark)})</a>:</b> <opt:formatNumber number="${resourceSubType.benchmarkResult?.min?.get(benchmark)}"/> - <opt:formatNumber number="${resourceSubType.benchmarkResult?.max?.get(benchmark)}"/>
                                        <g:if test="${factor}"> -
                                            <g:if test="${factor > 50}">
                                                <span style="color: #FF2D41;"><strong> ${factor}</strong></span>
                                            </g:if>
                                            <g:elseif test="${factor > 10}">
                                                <span style="color: #FF2D41;"> ${factor}</span>
                                            </g:elseif>
                                            <g:else>
                                                <span> ${factor}</span>
                                            </g:else>
                                        </g:if><br/>
                                    </g:if>
                                </g:each>
                                </g:if>
                                <g:else>
                                    <g:each in="${benchmarks}" var="benchmark">
                                    <g:set var="factor" value="${resourceSubType.benchmarkResult?.factor?.get(benchmark)}"/>
                                    <b><a href="javascript:" onclick="fullScreenPopup('${createLink(action: "benchmarkGraph", params:[resourceSubTypeId: resourceSubType.id, benchmarkToShow: benchmark])}','showGraphWindow', 'width=1024, height=768, scrollbars=1');">${benchmarkNames.get(benchmark)} (${resourceSubType.benchmarkResult?.validValues?.get(benchmark)})</a>:</b> <opt:formatNumber number="${resourceSubType.benchmarkResult?.min?.get(benchmark)}"/> - <opt:formatNumber number="${resourceSubType.benchmarkResult?.max?.get(benchmark)}"/> (<opt:formatNumber number="${resourceSubType.benchmarkResult?.average?.get(benchmark)}"/>)
                                    <g:if test="${factor}"> -
                                        <g:if test="${factor > 50}">
                                            <span style="color: #FF2D41;"><strong> ${factor}</strong></span>
                                        </g:if>
                                        <g:elseif test="${factor > 10}">
                                            <span style="color: #FF2D41;"> ${factor}</span>
                                        </g:elseif>
                                        <g:else>
                                            <span> ${factor}</span>
                                        </g:else>
                                    </g:if><br/>
                                    </g:each>
                                </g:else>
                            </g:if>
                        </td>
                        <td style="word-wrap: break-word; min-width: 150px;">
                            <g:if test="${resourceSubType.benchmarkResult}">
                                <g:if test="${threshold}">
                                    <g:each in="${benchmarks}" var="benchmark">
                                        <g:if test="${subtypeBenchmarkFailed?.get(resourceSubType.subType)?.contains(benchmark)}">
                                            <b>${benchmarkNames.get(benchmark)}:</b> <opt:formatNumber number="${resourceSubType.benchmarkResult?.average?.get(benchmark)}"/><br/>
                                        </g:if>
                                    </g:each>
                                </g:if>
                                <g:else>
                                    <g:each in="${benchmarks}" var="benchmark">
                                        <b>${benchmarkNames.get(benchmark)}:</b> <opt:formatNumber number="${resourceSubType.benchmarkResult?.average?.get(benchmark)}"/><br/>
                                    </g:each>
                                </g:else>
                            </g:if>
                        </td>
                        <td style="word-wrap: break-word; min-width: 150px;">
                            <g:if test="${resourceSubType.benchmarkResult}">
                                <g:if test="${threshold}">
                                    <g:each in="${benchmarks}" var="benchmark">
                                        <g:if test="${subtypeBenchmarkFailed?.get(resourceSubType.subType)?.contains(benchmark)}">
                                            <b>${benchmarkNames.get(benchmark)}:</b> <opt:formatNumber number="${resourceSubType.benchmarkResult?.median?.get(benchmark)}"/><br/>
                                        </g:if>
                                    </g:each>
                                </g:if>
                                <g:else>
                                    <g:each in="${benchmarks}" var="benchmark">
                                        <b>${benchmarkNames.get(benchmark)}:</b> <opt:formatNumber number="${resourceSubType.benchmarkResult?.median?.get(benchmark)}"/><br/>
                                    </g:each>
                                </g:else>
                            </g:if>
                        </td>
                        <td style="word-wrap: break-word; min-width: 150px;">
                            <g:if test="${resourceSubType.benchmarkResult}">
                                <g:if test="${threshold}">
                                    <g:each in="${benchmarks}" var="benchmark">
                                        <g:if test="${subtypeBenchmarkFailed?.get(resourceSubType.subType)?.contains(benchmark)}">
                                            <b>${benchmarkNames.get(benchmark)}:</b> <opt:formatNumber number="${resourceSubType.benchmarkResult?.standardDeviation?.get(benchmark)}"/> - <opt:formatNumber number="${resourceSubType.benchmarkResult?.variance?.get(benchmark)}"/><br/>
                                        </g:if>
                                    </g:each>
                                </g:if>
                                <g:else>
                                    <g:each in="${benchmarks}" var="benchmark">
                                        <b>${benchmarkNames.get(benchmark)}:</b> <opt:formatNumber number="${resourceSubType.benchmarkResult?.standardDeviation?.get(benchmark)}"/> - <opt:formatNumber number="${resourceSubType.benchmarkResult?.variance?.get(benchmark)}"/><br/>
                                    </g:each>
                                </g:else>
                            </g:if>
                        </td>
                        <td style="word-wrap: break-word; min-width: 300px;">
                            <g:if test="${resourceSubType.benchmarkResult}">
                                <g:each in="${benchmarks}" var="benchmark">
                                    <g:set var="changesInCO2" value="${resourceSubType.benchmarkResult.changesInCO2?.get(benchmark)}"/>
                                    <b>${benchmarkNames.get(benchmark)}:</b> <opt:formatNumber number="${changesInCO2?.get("min")}"/> - <opt:formatNumber number="${changesInCO2?.get("max")}"/> (<opt:formatNumber number="${changesInCO2?.get("avg")}"/>) - <opt:formatNumber number="${changesInCO2?.get("factor")}"/><br/>
                                </g:each>
                            </g:if>
                        </td>
                        <td style="text-align: center !important;">
                            <g:if test="${resourceSubType.benchmarkResult}">
                                <a href="javascript:" onclick="fullScreenPopup('${createLink(action: "benchmarkGraph", params:[resourceSubTypeId: resourceSubType.id])}','showGraphWindow', 'width=1024, height=768, scrollbars=1');"><i class="far fa-chart-bar fa-3x" aria-hidden="true"></i></a>
                            </g:if>
                        </td>
                        <g:set var="stages" value="${(Map<String, Integer>) resourceTypeService.getAvailableStages(resourceSubType)}" />

                        <td style="text-align: center !important;">
                            <g:if test="${resourceSubType.benchmarkResult}">
                                <g:each in="${stages}" var="stage">
                                    <g:link controller="resourceType" action="generateImpactsExcelFromSubtype" params="[resourceType: resourceSubType.resourceType, subType: resourceSubType.subType, stage: stage.key, impactsAsKg: false]" ><b>${stage.key} (${stage.value})</b></g:link><br/>
                                </g:each>
                                <%--<g:link controller="resourceType" action="generateImpactsExcelFromSubtype" params="[resourceType: resourceSubType.resourceType, subType: resourceSubType.subType]" ><i class="far fa-file-excel fa-3x" aria-hidden="true"></i></g:link>--%>
                            </g:if>
                        </td>
                        <td style="text-align: center !important;">
                            <g:if test="${resourceSubType.benchmarkResult}">
                                <g:each in="${stages}" var="stage">
                                    <g:link controller="resourceType" action="generateImpactsExcelFromSubtype" params="[resourceType: resourceSubType.resourceType, subType: resourceSubType.subType, stage: stage.key, impactsAsKg: true]" ><b>${stage.key} (${stage.value})</b></g:link><br/>
                                </g:each>
                            <%--<g:link controller="resourceType" action="generateImpactsExcelFromSubtype" params="[resourceType: resourceSubType.resourceType, subType: resourceSubType.subType]" ><i class="far fa-file-excel fa-3x" aria-hidden="true"></i></g:link>--%>
                            </g:if>
                        </td>
                        </tr><%--
                --%></g:if><%--
                --%></g:each><%--
        --%></table></g:each><%--
         --%></g:if><%--
        --%></g:form>
    </g:if>
</div>
<script type="text/javascript">
    var previousClass;
    function selectAll(rowClass) {
        if (!previousClass) {
            previousClass = rowClass;
        }
        var foundRows = document.getElementsByClassName(rowClass);

        if (foundRows) {
            $(foundRows).each(function() {
                var row = this;

                $('input[class=selectCheckbox]', this).each(function() {
                    if($(this).prop('checked') && previousClass == rowClass) {
                        $(this).prop('checked', false);
                        $(row).removeClass('checked');
                    } else {
                        $(this).prop('checked', true);
                        $(row).addClass('checked');
                    }
                });
            });
        }
        previousClass = rowClass;
    }

    $(function () {
        $('.factorBenchmark[rel=popover]').popover({
            content:"Red value indicates factor over 10, bolder red indicates factor over 50<br><br>Factor is calculated by dividing max benchmark value with min benchmark value",
            placement:'bottom',
            template: '<div class="popover"><div class="arrow"></div><div class="popover-content ribaHelp" style="font-size: 12px !important; font-weight: normal !important;"></div></div>',
            trigger:'hover',
            html:true
        });
        $('.exportBenchmark[rel=popover]').popover({
            content:"Exports impacts per stage converted to the subtypes standardUnit",
            placement:'bottom',
            template: '<div class="popover"><div class="arrow"></div><div class="popover-content ribaHelp" style="font-size: 12px !important; font-weight: normal !important;"></div></div>',
            trigger:'hover',
            html:true
        });
        var allSelects = $('select');
        if (allSelects) {
            $(allSelects).select2({
                placeholder:'<g:message code="select"/>',
                allowClear: true
            }).maximizeSelect2Height();
        }
    });
</script>
</body>
</html>

