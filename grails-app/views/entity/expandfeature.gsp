<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <meta name="format-detection" content="telephone=no"/>
    <g:if test="${indicator?.report?.appliedStyleSheet}">
        <link rel="stylesheet" type="text/css" href="${indicator?.report?.appliedStyleSheet}" /> %{--static stylesheet url--}%
    </g:if>
    <style type="text/css">
        .container{
            width: 90% !important;
        }
    </style>
</head>
<body>
<g:set var="indicatorService" bean="indicatorService"/>
<div class="container section">

    <g:if test="${errorMessageString}">
        <div class="alert alert-error">
            <button data-dismiss="alert" class="close" type="button">×</button>
            <strong>${errorMessageString}</strong>
        </div>
    </g:if>
    <g:if test="${recommendWarning}">
        <div class="alert alert-warning">
            <button data-dismiss="alert" class="close" type="button">×</button>
            <strong>${recommendWarning}</strong>
        </div>
    </g:if>
    <div class="hide-on-print">
        <opt:breadcrumbsNavBar parentEntity="${parent}" childEntity="${entity}" indicator="${indicator}" specifiedHeading="${indicator?.expandFeatures?.localizedLinkDisplayName ?: message(code: 'query.show_detailed_report')}"/>
    </div>
    <div class="sectionheader">
        <div class="pull-right hide-on-print">
            <g:if test="${indicator.expandFeatures || indicator.applicationExpandFeatures || downloadSummary}">
                <a href="javascript:;" onclick="${showScientificWarning ? "generateResultExcel()" : "downloadExpandFeatureAsExcel(this)"};" class="btn btn-primary"><g:message code="results.expand.download_excel" /></a>
            </g:if>
            <input type="button" onclick="window.close();" class="btn" value="${message(code: 'close')}" />
        </div>

        <h2>${indicatorService.getLocalizedName(indicator)}</h2>
    </div>


    <div class="sectionbody"${indicator?.report?.backgroundImage ? ' style="background-image: url(\'' + indicator.report.backgroundImage +'\');"' : ''}>

        <table class="table table-cellvaligntop" border="1">
            <calc:renderIndicatorExpandFeature entity="${entity}" indicator="${indicator}" downloadSummary="${downloadSummary}" />
            <%--<g:renderResultRows entity="${entity}" indicator="${indicator}" resultCategories="${resultCategories}" expandView="true" calculationRules="${calculationRules}" /> --%>
            <%--<g:renderTotalResultRows doColspan="${true}" entity="${entity}" indicator="${indicator}" resultCategories="${resultCategories}" calculationRules="${calculationRules}" />--%>
        </table>
    </div>

    <script type="text/javascript">
        $(document).ready(function() {
            $('.sectionexpander').on('click', function (event) {
                var section = $(this).parent().parent().parent().next(".calculationTotalResults");
                var parent = $(this).parent()
                $(section).toggleClass('collapsed');
                $(parent).toggleClass('collapsed');
                $(section).find('.resultRow').fadeToggle(1);
            });

            $('.expandAndCollapse').on('click', function (event) {
                var section = $(this).parent().parent().parent().next(".calculationTotalResults");
                var parent = $(this).parent()
                $(section).toggleClass('collapsed');
                $(parent).toggleClass('collapsed');
                $(section).find('.resultRow').fadeToggle(1);
            });
            highlightRowsInResultsDetailedView()
        });

        if (typeof hideNumeric != 'function') {
            function hideNumeric() {
                var button = '#numericOrPercentages';
                var value = "${message(code: 'results.expand.button.results')}";
                $(button).attr("value", value);
                $(button).attr("onclick", "showNumeric();");
                $('.numerical').hide();
                $('.percentages').show();
            }
        }

        if (typeof showNumeric != 'function') {
            function showNumeric() {
                var button = '#numericOrPercentages';
                var value = "${message(code: 'results.expand.button.hotspots')}";
                $(button).attr("value", value);
                $(button).attr("onclick", "hideNumeric();");
                $('.percentages').hide();
                $('.numerical').show();
            }
        }

        if (typeof generateResultExcel != 'function') {
            function generateResultExcel() {
                Swal.fire({
                    icon: "warning",
                    title: '${message(code: "results.expand.download_excel_warning")}',
                    showCancelButton: true,
                    showConfirmButton:false,
                    html:
                    '<g:link controller="fileExport" id="decimalFormat" onclick="swal.closeModal(); return true;" class="btn btn-primary" action="downloadExpandFeatureAsExcel" params="[entityId: entity?.id, indicatorId: indicator?.indicatorId, scientificNotation: false , downloadSummary: downloadSummary]"><g:message code="results.expand.download_excel_as_decimals" /></g:link>' +
                    ' <g:link controller="fileExport" id="scientificFormat" onclick="swal.closeModal(); return true;" class="btn btn-primary" action="downloadExpandFeatureAsExcel" params="[entityId: entity?.id, indicatorId: indicator?.indicatorId, scientificNotation: true , downloadSummary: downloadSummary]"><g:message code="results.expand.download_excel_as_scientific" /></g:link> ',
                    allowOutsideClick: true,
                });
            }
        }

        function downloadExpandFeatureAsExcel(element) {
            downloadFile("${g.createLink(controller: "fileExport", action: "downloadExpandFeatureAsExcel", params: [entityId: entity?.id, indicatorId: indicator?.indicatorId, scientificNotation: false , downloadSummary: downloadSummary])}", element)
        }
    </script>
</body>
</html>