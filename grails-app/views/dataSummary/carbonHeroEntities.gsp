<!doctype html>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
    <g:set var="indicatorService" bean="indicatorService"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <h1>Carbon Heroes entities</h1>
    </div>
</div>
<div class="container section">
    <div class="btn-group pull-left bordered" style="display: inline; margin-bottom: 10px;">
        <g:form controller="design" action="generateIndicatorResultsExcel" name="generateIndicatorResultsExcel">
            <select name="indicatorId" id="indicatorId" style="width: 250px;">
                <g:each in="${benchmarkIndicators}">
                    <option value="${it.indicatorId}">${indicatorService.getLocalizedShortName(it)}</option>
                </g:each>
            </select>
            <label><strong>If no filters are applied, all designs from a project with a carbon hero tagged design in it will be included in the exported file</strong></label>
            <label for="alsoNoResults" class="control-label"><input type="checkbox" id="alsoNoResults" name="checkBoxSelector" value="true"> Include designs with no results</input><span class="fiveMarginLeft hidden-print" rel="popover_right" data-trigger="hover" data-content="otherwise these are excluded"><i class="far fa-question-circle"></i></span></label>
            <label for="carbonHeroes" class="control-label"><input type="checkbox" id="carbonHeroes" name="checkBoxSelector" value="true"> Only include Carbon heroes designs</input><span class="fiveMarginLeft hidden-print" rel="popover_right" data-trigger="hover" data-content="Will only export the Carbon Heroes tagged design - all other designs will be ignored"><i class="far fa-question-circle"></i></span></label>
            <label for="onlyNoCarbonHeroes" class="control-label"><input type="checkbox" id="onlyNoCarbonHeroes" name="checkBoxSelector" value="true"> Only include projects with no Carbon heroes</input><span class="fiveMarginLeft hidden-print" rel="popover_right" data-trigger="hover" data-content="Will skip all projects that have at least one Carbon Heroes tagged design"><i class="far fa-question-circle"></i></span></label>
            <label for="prodWithoutBenchmark" class="control-label"><input type="checkbox" id="prodWithoutBenchmark" name="checkBoxSelector" value="true"> Only include projects with PROD license and no carbon benchmark indicator</input><span class="fiveMarginLeft tableHeadingPopover hidden-print" rel="popover_right" data-trigger="hover" data-content="configuration anomalies (no carbon hero indicator in project)"><i class="far fa-question-circle"></i></span></label>
            <label for="onlyLatest" class="control-label"><input type="checkbox" id="onlyLatest" name="checkBoxSelector" value="true"> Only include latest design</input><span class="fiveMarginLeft tableHeadingPopover hidden-print" rel="popover_right" data-trigger="hover" data-content="Will take the latest stage design, if there is no latest stage design tag - will check for latest design created"><i class="far fa-question-circle"></i></span></label>

            <g:submitButton name="download" style="margin-bottom: 5px;" value="Download" class="btn btn-primary" onclick="doubleSubmitPrevention('generateIndicatorResultsExcel')" />
        </g:form>
    </div>

    <g:if test="${projectsWithCarbonHeroes}">
        <table class="resource pull-left" id="table2">
            <thead>
            <tr>
                <th>Project</th>
                <th>Country</th>
                <th>Type</th>
                <th>Gross Surface</th>
                <th>Design</th>
                <th>Design's riba stage</th>
                <th>Benchmark total</th>
                <th>License </th>
            </tr>
            </thead>
            <tbody>
            <g:set var="entityService" bean="entityService"/>
            <g:set var="licenseService" bean="licenseService"/>
            <g:set var="indicatorService" bean="indicatorService"/>
            <g:set var="configurationService" bean="configurationService"/>
            <g:set var="benchmarkIndicator" value="${indicatorService.getIndicatorByIndicatorId(configurationService.getByConfigurationName(com.bionova.optimi.construction.Constants.APPLICATION_ID,"benchmarkingIndicatorId").value, true)}" />
            <g:each in="${projectsWithCarbonHeroes}" var="parent">
                <g:set var="carbonHeroes" value="${entityService.getCarbonHeroesWithEntityIds(parent.childEntities?.collect({ it.entityId }))}" />
                <g:if test="${carbonHeroes}">
                    <g:set var="parentCountry" value="${parent.datasets?.find({ d -> d.questionId == "country" })}" />
                    <g:set var="parentType" value="${parent.datasets?.find({ d -> d.questionId == "type" })}" />
                    <g:set var="parentSurface" value="${parent.datasets?.find({ d -> d.questionId == "grossSurface" })}" />
                    <g:set var="licenseNames" value="${licenseService.getLicenseNamesForEntityId(parent._id)}" />
                    <g:each in="${carbonHeroes}" var="carbonHero">
                        <g:set var="carbonHeroScore" value="${entityService.getDisplayResultForMainList(carbonHero, benchmarkIndicator)}" />
                        <tr>
                            <td><opt:link controller="entity" action="show" target="_blank" params="[entityId:parent._id.toString()]">${parent.name}</opt:link></td>
                            <td>${parentCountry?.answerIds ? parentCountry.answerIds[0].capitalize() : ""}</td>
                            <td>${parentType?.answerIds ? parentType.answerIds[0] : ""}</td>
                            <td>${parentSurface?.answerIds ? parentSurface.answerIds[0] : "Undefined"} m2</td>
                            <td>${carbonHero.name}</td>
                            <td>${carbonHero.ribaStage}</td>
                            <td>${carbonHeroScore ? carbonHeroScore.round().toString() : "N/A"} kg CO<sub>2</sub>e/m<sup>2</sup></td>
                            <td><g:each in="${licenseNames}" var="license">${license}<br/> </g:each></td>
                        </tr>
                    </g:each>
                </g:if>
            </g:each>
            </tbody>
        </table>
    </g:if>
</div>
<script>
    $("input:checkbox").on('click', function () {
        if ($(this).is(":checked") === true) {
            var group = "input:checkbox[name='" + $(this).attr("name") + "']";
            $(group).prop("checked", false);
            $(this).prop("checked", true);
        } else {
            $(this).is(":checked", false);
        }
    });
</script>
</body>

</html>