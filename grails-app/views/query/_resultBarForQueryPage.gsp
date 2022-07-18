<div id="controller" class="btn-group hide-on-print text-center">
    <h3>${calculationRule.localizedName}</h3>
    <a style="display: inline-block" class="dropdown-toggle" id="calculationRuleList" data-toggle="dropdown"><g:message code="result.pie_change_category"/> <span class="caret-middle"></span></a>
    <ul class="dropdown-menu" style="text-align: left !important;">
        <g:each in="${calculationRules}" var="calculationRule1">
            <li> <a href="javascript:" style="font-size: 13px; font-weight: normal;" onclick="renderQueryResultGraphs('${indicator?.indicatorId}','${entity?.id}', '${calculationRule1.calculationRuleId}');">${calculationRule1?.localizedName}</a></li>
        </g:each>
    </ul>
</div>
<g:if test="${drawTexts}">
    <div id="impactIncon" class="text-center"><h1>${impactsText}</h1></div>
</g:if>
<div id="lifeCycleChart" class="sideBarChartWrapper">
</div>
<div id="mostContributeMaterial" class="sideBarChartWrapper">
    <h4 class="text-center"><g:message code="results.contributing_impacts_resource"/> <span id="highestMatHelp" class="fa fa-question-circle" rel="popover" data-trigger="hover" style="margin-top:3px;" data-content="<g:message code="help.highest_material_sidebar"/>"></span></h4>
    <g:if test="${highestImpactMaterialScores && total}">
        <table class="table table-striped table-condensed">
            <thead>
                <tr>
                    <th><g:message code="query.material"/></th>
                    <th class="number"><g:message code="share"/></th>
                </tr>
            </thead>
            <tbody>
            <g:each in="${highestImpactMaterialScores}" status="i" var="materialImpact">
                <g:set var="random" value="${UUID.randomUUID().toString()}"/>
                <g:set var="resource" value="${resourceList.find({it.resourceId == materialImpact.key})}"/>
                <tr>
                    <td>${resource?.localizedName ?: materialImpact.key}
                        <g:if test="${resource}">
                            <opt:renderDataCardBtn indicatorId="${indicator?.indicatorId}" resourceId="${resource?.resourceId}" profileId="${resource?.profileId}" childEntityId="${entity?.id}" showGWP="true" infoId="${random}" />
                        </g:if>
                    </td>
                    <td class="number">${materialImpact?.value ? (materialImpact.value/total*100).round(2) : '' } %</td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </g:if>
</div>
<div id="comparisonChart" class="sideBarChartWrapper">
</div>
<script type="text/javascript">
    Highcharts.setOptions({
        colors: ${colors as grails.converters.JSON}
    });
    $(function () {
        var helpPopSettings = {
            placement: 'top',
            template: '<div class="popover popover-fade" style="display: block; max-width: 300px;"><div class="arrow"></div><div style="font-weight: normal !important;" class="popover-content"></div></div>'
        };
        $("#highestMatHelp").popover(helpPopSettings);
        $('#tonsCabonHelp').popover({
            placement: 'bottom',
            template: '<div class="popover" style="font-size:12px !important; max-width:300px;"><div class="arrow"></div><div class="popover-content"></div></div>',
            html: true,
            trigger: 'hover',
            content:"${message(code: 'tonnes_carbon_help')}"
        });
        $('#calculationRuleList').dropdown();
        var chartLifeCycle = new Highcharts.chart('lifeCycleChart',{
            chart: {
                type: 'pie',
            },
            title: {
                text: '${message(code: "results.resultchart_heading")}',
                style: {
                    color: '#333333',
                    fontWeight: 'bold',
                    fontSize: '14px',
                    fontFamily: 'Arial'
                }
            },
            credits: {
                enabled: false
            },
            lang: {
                noData: "${message(code: 'graph_no_data_displayed')}"
            },
            xAxis: {
                title: {
                    enabled: false
                }
            },
            yAxis: {
                title: {
                    enabled: false
                }
            },
            tooltip: {
                pointFormat: '{point.name}: <b>{point.y} - {point.percentage:.1f}%</b>',
                valueSuffix: '${unit}',
                valueDecimals: 2,
                shared: false
            },
            legend: {
                align: 'center',
                verticalAlign: 'bottom',
                floating: false,
                labelFormatter: function(){
                    return this.name + " - " + this.percentage.toFixed(1) + "%"
                },
                itemStyle: {
                    fontWeight: 'normal',
                    fontFamily: 'Arial'
                },
                width: '100%'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    showInLegend: true
                },
                series: {
                    dataLabels: {
                        enabled: false
                    }
                }
            },
            series: [{
                name: 'Impact category',
                colorByPoint: true,
                data: ${pieGraphDatasetForTypes}
            }],
            exporting: {
                enabled: true,
                sourceWidth: 1000,
                sourceHeight: 500,
                buttons: {
                    contextButton: {
                        menuItems: [
                            'downloadPNG',
                            'downloadJPEG',
                            'downloadPDF',
                            'downloadSVG',
                            'separator',
                            'downloadCSV',
                            'downloadXLS',
                        ]
                    }
                }
            },
            responsive: {
                rules: [{
                    condition: {
                        maxWidth: 500
                    },
                    chartOptions: {
                        legend: {
                            align: 'center',
                            verticalAlign: 'bottom',
                            layout: 'horizontal'
                        },
                        yAxis: {
                            labels: {
                                align: 'left',
                                x: 0,
                                y: -5
                            },
                            title: {
                                text: null
                            }
                        },
                        subtitle: {
                            text: null
                        },
                        credits: {
                            enabled: false
                        }
                    }
                }]
            }
        });


    })
</script>