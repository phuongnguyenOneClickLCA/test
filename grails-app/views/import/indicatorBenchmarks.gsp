<!doctype html>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <h1>
            Indicator Benchmarks
            ${indicatorBenchmarks ? '(' + indicatorBenchmarks.size() + ')' : ''}
        </h1>
    </div>
</div>

<div class="container section">
    <sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN">
            <div class="pull-left">
                <g:uploadForm action="uploadIndicatorBenchmarks">
                    <div class="clearfix"></div>
                    <div class="column_left">
                        <div class="control-group">
                            <label for="file" class="control-label"><g:message code="admin.import.excel_file" /></label>
                            <div class="controls"><input type="file" name="xlsFile" id="file" class="btn" value="" /></div>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                    <opt:submit name="import" value="${message(code:'import')}" class="btn btn-primary"/>
                </g:uploadForm>
            </div>
        <div class="clearfix"></div>
    </sec:ifAllGranted>

    <g:if test="${indicatorBenchmarks}">
        <g:form action="deleteSelectedIndicatorBenchmarks">
            <sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN">
                <a id="selectAllCheckboxes" class="btn btn-primary" style="margin-bottom: 5px;" >Select all</a>
                <g:submitButton name="deleteSelected" style="margin-bottom: 5px;" value="Delete selected" class="btn btn-danger" />
            </sec:ifAllGranted>

            <table class="table table-striped table-condensed data-list">
                <tr id="resourceListHeading">
                    <th>Select</th>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Areas (areas)</th>
                    <th>Building Types (buildingTypes)</th>
                    <th style="white-space: nowrap">Index value</th>
                    <th>Applied Denominator (appliedDenominator)</th>
                    <th>Source Information (benchmarkSourceInformation)</th>
                    <th>Description (benchmarkDescription)</th>
                    <th>Sample size (sampleSize)</th>
                    <th>External link(externalLink)</th>
                    <th>Date (benchmarkDate)</th>
                    <th>Benchmark Type (benchmarkType)</th>
                    <sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN"><th>file source</th><th>&nbsp;</th></sec:ifAllGranted>
                </tr><%--
            --%><g:each in="${indicatorBenchmarks}" var="indicatorBenchmark"><%--
            --%><g:if test="${indicatorBenchmark}">
                <tr>
                <td><input style="width: 30px; height: 20px;" type="checkbox" name="indicatorBenchmarksToRemove" class="indicatorBenchmarksToRemove" value="${indicatorBenchmark.id.toString()}" /></td>
                <td>${indicatorBenchmark.benchmarkId}</td>
                <td>${indicatorBenchmark.benchmarkName}</td>
                <td>${indicatorBenchmark.areas}</td>
                <td>${indicatorBenchmark.buildingTypes}</td>
                <td>
                    <strong>G</strong>: ${indicatorBenchmark.indexG?.round(2)}<br/>
                    <strong>F</strong>: ${indicatorBenchmark.indexF?.round(2)}<br/>
                    <strong>D</strong>: ${indicatorBenchmark.indexD?.round(2)}<br/>
                    <strong>C</strong>: ${indicatorBenchmark.indexC?.round(2)}<br/>
                    <strong>B</strong>: ${indicatorBenchmark.indexB?.round(2)}<br/>
                    <strong>A</strong>: ${indicatorBenchmark.indexA?.round(2)}<br/>
                    <strong>Min</strong>: ${indicatorBenchmark.indexMin?.round(2)}<br/>
                    <strong>Average</strong>: ${indicatorBenchmark.average?.round(2)}<br/>
                </td>
                <td>${indicatorBenchmark.appliedDenominator}</td>
                <td>${indicatorBenchmark.benchmarkSourceInformation}</td>
                <td>${indicatorBenchmark.benchmarkDescription}</td>
                <td>${indicatorBenchmark.sampleSize}</td>
                <td>${indicatorBenchmark.externalLink}</td>
                <td>${indicatorBenchmark.benchmarkDate}</td>
                <td>${indicatorBenchmark.benchmarkType}</td>
                <td>${indicatorBenchmark.fileName}</td>
            </tr>
            </g:if>
            </g:each>
            </table>
        </g:form>
    </g:if>
</div>
<script type="text/javascript">
    $("#selectAllCheckboxes").on("click", function() {
        var checkBoxes = $('.indicatorBenchmarksToRemove');
        $(checkBoxes).each(function () {
            $(this).prop('checked', true)
        });
    });
</script>
</body>
</html>
