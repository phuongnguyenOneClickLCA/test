           <g:set var="localizedUnitIndicator" value="${indicator.localizedUnit}"/>
            <h2>${indicator.localizedName}<g:if test="${localizedUnitIndicator}"> (${localizedUnitIndicator})</g:if></h2>

                <p class="graphs">${indicator.localizedPurpose}</p>
                    <div class="span6 chart-container" style="margin-top: 20px;">
                        <div class="spider_chart chart-graph the_chart" id="the_chart"></div>
                    </div>
                    <div class="span4" style="display: none;">
                        <div class="chart-legend" id="chart_controls">
                            <h3><g:message code="graphs.operatingPeriods" /></h3>
                            <div id="container_chart_designs"></div>
                        </div>
                    </div>
            <script type="text/javascript">
                $(document).ready(function() {
                    <g:set var="colors" value="['#75AF00', '#FF8C00', '#1A86D0', '#FF2D41', '#764AB6', '#E1D700', '#00998F',
                '#E14F1F', '#B2B2B2', '#006635', '#C71717', '#063DA6', '#83296B', '#686868', '#823E00', '#FE5C5C',
                '#FFF761', '#91C46E', '#57BFFF', '#C081FF']" />

                    var indicators = {
                        0: "${indicator.localizedShortName}"
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
                                    id: 0,
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
                        dataType: 'norm',
                        data_indicators: indicators,
                        chart_container_id: 'the_chart',
                        design_controls_container_id: 'container_chart_designs',
                        indicator_controls_container_id: 'container_chart_designs'
                    });
                });
            </script>


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
        <g:set var="indicatorAmount" value="${1}" />
        <g:set var="entityAmount" value="${entities ? entities.size() : 0}" />

        var indicators = {
            0 : "${indicator.localizedShortName}"
        };

    <g:set var="colorIndex" value="${0}" />

    var entities = [
        <g:each in="${entities}" var="entity" status="i">
        {
            id: ${i},
            <g:set var="visible" value="${true}" />
            <g:if test="${!(scores.get(indicator.indicatorId)?.get(entity.id))?.isNumber()}">
                <g:set var="visible" value="${false}" />
            </g:if>
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
                <g:if test="${(scores.get(indicator.indicatorId)?.get(entity.id))?.isNumber()}">
                {
                    id: ${j},
                    score: ${scores.get(indicator.indicatorId)?.get(entity.id)},
                    scoreNormalized: ${normalizedScores?.get(indicator.indicatorId)?.get(entity.id)}
                }
                </g:if>
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


