<!doctype html>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
    <script type="text/javascript">
       $(document).ready(function() {
           $(document).find('.noShow').fadeToggle();
       });
   </script>

</head>
<body>
<g:set var="indicatorService" bean="indicatorService"/>
<div class="container">
    <div class="screenheader">
        <div class="pull-right hide-on-print">
            <opt:link controller="entity" action="show" class="btn hide-on-print"><i class="icon-chevron-left"></i> <g:message code="back" /></opt:link>
            <button class="btn btn-primary" onClick="window.print();"><i class="icon-print icon-white"></i> <g:message code="print" /></button>
        </div>
        <g:render template="/entity/basicinfoView"/>
    </div>
    <g:if test="${nonProdLicenses}">
        <div class="alert">
            <strong>
                <g:message code="license.non_commercial" />
                <g:set var="licenseAmount" value="${nonProdLicenses.size()}" />
                <g:each in="${nonProdLicenses}" var="license" status="index">
                    <g:if test="${index < (licenseAmount - 1)}">
                        ${license.name}, <g:message code="license.type.${license.type}" />, ${session?.loggedInUserObject?.name},
                    </g:if>
                    <g:else>
                        ${license.name}, <g:message code="license.type.${license.type}" />, ${session?.loggedInUserObject?.name}
                    </g:else>
                </g:each>
                ${date}
            </strong>
        </div>
    </g:if>
</div>

<div class="container section" id="summary">
    <div class="sectionheader">
        <button class="pull-left sectionexpander"><i class="icon icon-chevron-down expander"></i><i class="icon icon-chevron-right collapser"></i></button>
        <h2 class="h2expander"><g:message code="graphs.summary" /></h2>
    </div>

    <div class="sectionbody padding-y">
        <div class="row-fluid">
            <g:if test="${indicators?.size() > 2 && showSummary}">
                <div class="span8 chart-container">
                    <div class="btn-toolbar">
                        <div class="btn-group abs-norm">
                            <button class="btn abs active"><g:message code="graphs.absolute" /></button><button class="btn norm"><g:message code="graphs.normalized" /></button>
                        </div>
                    </div>
                    <div class="spider_chart chart-graph the_chart" id="the_chart">
                    </div>
                </div>
            </g:if>
            <g:else>
                <div class="span8">
                    <g:message code="graphs.no_summary" />
                </div>
            </g:else>
            <div class="span4">
            <g:if test="${indicators?.size() > 2 && showSummary}">
                <div class="chart-legend" id="chart_controls">
                    <h3>
                    <g:if test="${'periods' == type}">
                        <g:message code="graphs.operatingPeriods" />
                    </g:if>
                    <g:else>
                        <g:message code="graphs.designs" />
                    </g:else>
                    </h3>
                    <div id="container_chart_designs"></div>
                </div>
                <div class="chart-legend">
                    <p>&nbsp;</p>
                    <div class="column_left"><strong><g:message code="entity.show.designs_tools" /></strong></div><div class="entity_chart_unit"><strong><g:message code="graphs.units" /></strong></div>
                    <div class="clearfix"></div>
                    <g:each in="${indicators}" var="indicator">
                        <label>${indicatorService.getLocalizedName(indicator)}<div class="entity_chart_unit">${indicatorService.getDisplayUnit(indicator)}</div></label>
                    </g:each>
               </div>
            </g:if>
            </div>
        </div>
    </div>
</div>

<g:each in="${indicators}" var="indicator" status="i">
<g:if test="${validScoresByIndicator?.get(indicator)}">
<div class="container section">
    <div class="sectionheader">
        <button class="pull-left sectionexpander"><i class="icon icon-chevron-down expander"></i><i class="icon icon-chevron-right collapser"></i></button>
        <h2 class="h2expander">${indicatorService.getLocalizedName(indicator)}<g:if test="${indicatorService.getDisplayUnit(indicator)}"> (${indicatorService.getDisplayUnit(indicator)})</g:if></h2>
    </div>

    <div class="sectionbody padding-y">
        <p class="graphs">${indicatorService.getLocalizedPurpose(indicator)}</p>
        <div class="row-fluid">
            <div class="span8 chart-container">
                <div class="btn-toolbar">
                    <div class="btn-group abs-norm">
                        <button class="btn abs active"><g:message code="graphs.absolute" /></button><button class="btn norm"><g:message code="graphs.normalized" /></button>
                    </div>
                </div>
                <div class="spider_chart chart-graph the_chart" id="the_chart${i}"></div>
            </div>
            <div class="span4">
                <div class="chart-legend" id="chart_controls${i}">
                    <h3>
                        <g:if test="${'periods' == type}">
                            <g:message code="graphs.operatingPeriods" />
                        </g:if>
                        <g:else>
                            <g:message code="graphs.designs" />
                        </g:else>
                    </h3>
                    <div id="container_chart_designs${i}"></div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        $(document).ready(function() {
            <g:set var="colors" value="['#75AF00', '#FF8C00', '#1A86D0', '#FF2D41', '#764AB6', '#E1D700', '#00998F',
                '#E14F1F', '#B2B2B2', '#006635', '#C71717', '#063DA6', '#83296B', '#686868', '#823E00', '#FE5C5C',
                '#FFF761', '#91C46E', '#57BFFF', '#C081FF']" />

            var indicators = {
             0: "${indicatorService.getLocalizedShortName(indicator)}"
            };

            <g:set var="colorIndex" value="${0}" />
            <g:set var="entityAmount" value="${entities ? entities.size() : 0}" />

            var entities = [
            <g:each in="${entities}" var="entity" status="j">
            {
                id: ${j},
                <g:set var="name" value="${entity.operatingPeriodAndName}" />
                <g:if test="${!(scores.get(indicator.indicatorId)?.get(entity.id))?.isNumber()}">
                    <g:set var="name" value="${abbr (value: name, maxLength: '27') + ' (' + scores.get(indicator.indicatorId)?.get(entity.id) + ')'}" />
                </g:if>
                name: '${name}',
                <g:if test="${(colorIndex + 1) > colors.size()}">
                <g:set var="colorIndex" value="${0}" />
                </g:if>
                color: '${colors[colorIndex]}',
                <g:if test="${(scores.get(indicator.indicatorId)?.get(entity.id))?.isNumber()}">
                visible: true,
                indicators: [
                    {
                        id: ${0},
                        score: ${scores.get(indicator.indicatorId)?.get(entity.id)},
                        scoreNormalized: ${normalizedScores.get(indicator.indicatorId)?.get(entity.id)}
                    }]
                </g:if>
                <g:else>
                visible: false
                </g:else>
            }${j + 1 < entityAmount ? ',' : ''}
            <g:set var="colorIndex" value="${colorIndex + 1}" />
            </g:each>
            ];

            chart = new DesignChart({
                type: 'bar',
                data_designs: entities,
                data_indicators: indicators,
                chart_container_id: 'the_chart${i}',
                design_controls_container_id: 'container_chart_designs${i}',
                indicator_controls_container_id: 'container_chart_designs${i}'
            });
        });
    </script>
</div>
</g:if>
</g:each>

<script type="text/javascript">
    $(document).ready(function() {
        $('.sectionexpander').on('click', function (event) {
            var section = $(this).parent().parent();
            $(section).find('.sectioncontrols').fadeToggle(200);
            $(section).find('.sectionbody').fadeToggle(200);
            $(section).toggleClass('collapsed');

        });

        $('.h2expander').on('click', function (event) {
            var section = $(this).parent().parent();
            $(section).find('.sectioncontrols').fadeToggle(200);
            $(section).find('.sectionbody').fadeToggle(200);
            $(section).toggleClass('collapsed');

        });
        <g:set var="colors" value="['#75AF00', '#FF8C00', '#1A86D0', '#FF2D41', '#764AB6', '#E1D700', '#00998F',
            '#E14F1F', '#B2B2B2', '#006635', '#C71717', '#063DA6', '#83296B', '#686868', '#823E00', '#FE5C5C', '#FFF761',
            '#91C46E', '#57BFFF', '#C081FF']" />
        <g:set var="indicatorAmount" value="${indicators ? indicators.size() : 0}" />
        <g:set var="entityAmount" value="${entities ? entities.size() : 0}" />

        var indicators = {
        <g:each in="${indicators}" var="indicator" status="ind">
          ${ind}: "${indicatorService.getLocalizedShortName(indicator)}"${ind + 1 < indicatorAmount ? ',' : ''}
        </g:each>
        };

        <g:set var="colorIndex" value="${0}" />

        var entities = [
            <g:each in="${entities}" var="entity" status="i">
            {
                id: ${i},
                <g:set var="visible" value="${true}" />
                <g:each in="${indicators}" var="indicator">
                <g:if test="${!(scores.get(indicator.indicatorId)?.get(entity.id))?.isNumber()}">
                <g:set var="visible" value="${false}" />
                </g:if>
                </g:each>
                <g:set var="name" value="${entity.operatingPeriodAndName}" />
                <g:if test="${visible}">
                visible: true,
                </g:if>
                <g:else>
                <g:set var="name" value="${abbr (value: name, maxLength: '27') + ' (' + message(code: "graphs.indicators.incomplete") + ')'}" />
                visible: false,
                </g:else>
                name: '${name}',
                <g:if test="${(colorIndex + 1) > colors.size()}">
                <g:set var="colorIndex" value="${0}" />
                </g:if>
                color: '${colors[colorIndex]}',
                indicators: [
                    <g:each in="${indicators}" var="indicator" status="j">
                    <g:if test="${(scores.get(indicator.indicatorId)?.get(entity.id))?.isNumber()}">
                    {
                        id: ${j},
                        score: ${scores.get(indicator.indicatorId)?.get(entity.id)},
                        scoreNormalized: ${normalizedScores.get(indicator.indicatorId)?.get(entity.id)}
                    }${j + 1 < indicatorAmount ? ',' : ''}
                    </g:if>
                    </g:each>
                ]
            }${i + 1 < entityAmount ? ',' : ''}
            <g:set var="colorIndex" value="${colorIndex + 1}" />
            </g:each>
        ];
        var size = function(obj) {
            var size = 0, key;
            for (key in obj) {
                if (obj.hasOwnProperty(key)) size++;
            }
            return size;
        };
        var type = 'bar';

        if (size(indicators) > 2) {
            type = 'spider';
            var parent = $('#the_chart').siblings('.btn-toolbar');
            $('.bar-spider', parent).removeClass('hide-switcher');
            $('.bar').show();
        } else {
            $('.bar').hide();
        }

        var chart = new DesignChart({
            type: type,
            summary: true,
            data_designs: entities,
            indicator_amount: size(indicators),
            data_indicators: indicators,
            design_controls_container_id: 'container_chart_designs',
            chart_container_id: 'the_chart',
            indicator_controls_container_id: 'container_chart_designs'
        });

        var is_chrome = /chrome/.test( navigator.userAgent.toLowerCase() );

        if(document.URL.indexOf("#")==-1 && is_chrome) {
            url = document.URL+"#";
            location = "#";
            location.reload(true);
        }
    });

    $(window).on('beforeunload', function(){
        $(window).scrollTop(0);
    });
</script>

</body>
</html>

