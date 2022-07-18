<g:set var="indicatorService" bean="indicatorService"/>
<g:set var="chartByMatAndImpactStacked" value="${indicatorService.getLocalizedRenameReportVisualisation(indicator, "chartByMatAndImpactStacked") ?: message(code:"chart_impact_mat_stacked")}"/>
<%def company = grailsApplication.mainContext.getBean("companyConfiguration") %>
<div class="text-center inline-block" style="width: 100%"><h2>${chartByMatAndImpactStacked}</h2><i class="fas fa-question-circle" style="vertical-align: top; padding: 12px" rel="popover" data-trigger="hover" data-html="true" data-content="${message(code:'stacked_column_help')}"></i></div>
<div id="chartByMatAndImpactStacked" class="containerForLifeCycle" style="width:100%; height:600px;"></div>
<script type="text/javascript">
    Highcharts.setOptions({
        colors: ${colorTemplate} });
    var calculationRulesMapStackedMat = ${calculationRulesMap as grails.converters.JSON}
    var mapAbsoluteValStackedMat= ${mapAbsoluteValues as grails.converters.JSON}
    var unitsMapToRuleStackedMat = ${unitsMapToRule as grails.converters.JSON}
    var stackedByMat = new Highcharts.chart({
        chart: {
            renderTo: 'chartByMatAndImpactStacked',
            type: 'column'
        },
        lang: {
            noData: "${message(code: 'graph_no_data_displayed')}"
        },
        title: {
            text: "",
            style: {
                color: '#333333',
                fontWeight: 'bold',
                fontSize: '20px',
                fontFamily: 'Arial'
            }
        },
        series: ${graphDatasets as grails.converters.JSON},
        plotOptions: {
            column: {
                stacking: 'normal',
            }
        },
        yAxis: {
            title: {
                enabled: true,
                text: "${unit}",
            },
            reversedStacks: false
        },
        xAxis: {
            categories: ${graphLabels}
        },
        tooltip: {
            formatter: function () {
                var shortName = this.x;
                var name = calculationRulesMapStackedMat[this.x];

                return this.points.reduce(function (s, point) {
                    var keyRule = point.x
                    var name = s
                    var string = point.series.name
                    var absoluteVal = mapAbsoluteValStackedMat[string][point.point.index] > 0.01 ? Math.round(mapAbsoluteValStackedMat[string][point.point.index] * 100)/100 : mapAbsoluteValStackedMat[string][point.point.index]
                    var value = Math.round(point.y * 100)/100
                    var unitForRule = unitsMapToRuleStackedMat[keyRule]
                    return name + "<br/> " +string+ ": <strong>" + absoluteVal + " " + unitForRule + " / " +value + "%</strong>";
                },'<b>' + (shortName === name ? name : shortName + " " + name) + '</b>');
            },
            shared: true,
        },
        legend: {
            align: 'center',
            verticalAlign: 'top',
            itemStyle: {
                fontWeight: 'normal',
                fontFamily: 'Arial'
            },

        },
        credits: {
            enabled: false
        },
        exporting: {
            enabled: true,
            sourceWidth: 1000,
            sourceHeight: 500,
            buttons: {
                contextButton: {
                    enabled: true,
                    menuItems: [
                        'downloadPNG',
                        'downloadJPEG',
                        'downloadPDF',
                        'downloadSVG',
                        'separator',
                        'downloadCSV',
                        'downloadXLS',
                        {
                            text: 'Download XLSX',
                            onclick: function() {
                                this.downloadXLSX();
                            }
                        },
                        'viewData'
                    ]
                }
            },
            csv: {
                // dateFormat: '%Y%m'
            },
            xlsx: {
                worksheet: {
                    autoFitColumns: true,
                    categoryColumnTitle: 'Month',
                    dateFormat: 'yyyy-mm',
                    name: 'Chart data'
                },
                workbook: {
                    fileProperties: {
                        Author:  "${company.name}",
                        Company:  "${company.name}",
                        CreatedDate: new Date(Date.now())
                    }
                }
            },
            chartOptions: {
                title: {
                    text:"${chartByMatAndImpactStacked}"
                }
            }
        },
    })
</script>