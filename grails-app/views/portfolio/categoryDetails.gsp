<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <meta name="format-detection" content="telephone=no"/>
    <g:if test="${indicator?.report?.appliedStyleSheet}">
        <link rel="stylesheet" type="text/css" href="${indicator?.report?.appliedStyleSheet}" /> %{--static stylesheet url--}%
    </g:if>
    <style type="text/css">

    </style>
</head>
<body>
<g:set var="indicatorService" bean="indicatorService"/>
<g:set var="calculationRuleService" bean="calculationRuleService"/>
<div class="container section">
    <div class="sectionheader">
        <div class="pull-right hide-on-print">
            <input type="button" id="numericOrPercentages" onclick="showNumeric();" class="btn btn-primary" value="${message(code: 'results.expand.button.results')}" />
            <input type="button" id="headings" onclick="hideHeadings();" class="btn btn-primary" value="${message(code: 'results.expand.hide_headings')}"/>
            <input type="button" onclick="window.close();" class="btn" value="${message(code: 'close')}" />
        </div>
        <h2>${indicatorService.getLocalizedName(indicator)}: ${resultCategory?.localizedName}</h2>
    </div>

    <g:if test="${entities}">
        <div class="sectionbody"${indicator?.report?.backgroundImage ? ' style="background-image: url(\'' + indicator.report.backgroundImage +'\');"' : ''}>
            <table class="table table-cellvaligntop" border="1">
                <thead>
                <tr>
                    <%--
                    <th><g:message code="calculationTotalResults.expand.query" /></th>
                    <th><g:message code="calculationTotalResults.expand.section" /></th>
                    <th><g:message code="calculationTotalResults.expand.question" /></th>
                    --%>
                    <th><g:message code="results.expand.resource" /></th>
                    <g:if test="${resultCategory?.process}">
                        <th class="text-right hidden" id="transformProcess"><g:message code="result.expand.transform_process"/></th>
                    </g:if>
                    <th class="text-right"><g:message code="results.expand.input" /></th>
                    <g:each in="${calculationRules?.sort({it.localizedName})}" var="calculationRule">
                        <th class="text-right">${calculationRule.localizedName}<br />${calculationRuleService.getLocalizedUnit(calculationRule, indicator)}</th>
                    </g:each>
                    <th style="text-align: left !important;"><g:message code="results.question.comments" /></th>
                    <th style="text-align: left !important;"><g:message code="main.list.entity" /></th>
                </tr>
                </thead>
                <optimi:renderPortfolioCategoryExpand calculationRules="${calculationRules}" entities="${entities}" indicator="${indicator}" resultCategory="${resultCategory}" />
                <%---this footer must contain the totalresultsforCatery row--%>
                <tfoot id="totalResultsFooter${count}" class="hidden">
                <optimi:renderPortfolioCategoryTotalRow entities="${entities}" indicator="${indicator}" calculationRules="${calculationRules}" resultCategory="${resultCategory}"/>
                </tfoot>
            </table>
        </div>
    </g:if>
    <g:else>
        <div class="sectionbody">
            <div class="alert alert-error">
                <button data-dismiss="alert" class="close" type="button">×</button>
                <strong><g:message code="portfolio_nodatatodisplay" /></strong>
            </div>
        </div>
    </g:else>


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

        function showHeadings() {
            window.history.back();
        }

        function hideHeadings() {
            window.location = window.location.href + "&collapsed=1"
        }

        $(function() {
            if (window.location.search.indexOf("collapsed=") !== -1) {
                showNumeric();
                var button = '#headings';
                var value = "${message(code: 'results.expand.show_headings')}";
                var numericOrPercentages = $('#numericOrPercentages');
                var totalSectionShare = $('.sectionTableRow');
                var totalsRow = $('#totalResultsFooter');
                var transformProcessHead = $('#transformProcess')
                $(button).attr("value", value);
                $(button).attr("onclick", "showHeadings();");
                var section = $(".sectionexpander").parent().parent().parent().next(".calculationTotalResults");
                var parent = $(this).parent();
                totalsRow.removeClass('hidden');
                numericOrPercentages.addClass('hidden');
                transformProcessHead.removeClass('hidden')
                totalSectionShare.addClass('hidden');
                $(section).toggleClass('collapsed');
                $(parent).toggleClass('collapsed');
                $(section).find('.resultRow').fadeToggle(1);
                $('.collapserParent').hide();
            }
        });
    </script>
</body>
</html>