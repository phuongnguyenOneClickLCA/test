<%def company = grailsApplication.mainContext.getBean("companyConfiguration") %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<g:set var="indicatorService" bean="indicatorService"/>
<g:set var="lifeCycleChartName" value="${indicatorService.getLocalizedRenameReportVisualisation(indicator, "placeholderChartForLifeCycle")}"/>
<g:set var="lifeCycleChartNameDefault" value="${indicator?.indicatorUse?.equalsIgnoreCase('operating') ? message(code: "results.resultchart_heading_operating") : message(code: "results.resultchart_heading")}"/>
<div id="graph${indicator.indicatorId}" class="containerForLifeCycle">
    <div id="placeholderlifeCycleChart" style="position: relative;float: left; padding-left: 10px; padding-right: 10px; padding-bottom: 30px; width: 100%; height: 600px">
        <canvas id="resultsPerLifeCycleStages${childEntity.id}"></canvas>
    </div>
    <div style="clear:both; padding-top:20px; margin-left:20px;"></div>
</div>
    <script type="text/javascript">
        var calculationRulesMapByStage = ${calculationRulesMap as grails.converters.JSON}
        var mapAbsoluteValByStage = ${mapAbsoluteValues as grails.converters.JSON}
        var unitsMapToRuleByStage = ${unitsMapToRule as grails.converters.JSON}
        var resultLifeCycleChart = new Highcharts.chart({
            chart: {
                renderTo: 'placeholderlifeCycleChart',
                type: 'column'
            },
            title: {
                text: '${lifeCycleChartName ?: lifeCycleChartNameDefault}',
                style: {
                    color: '#333333',
                    fontWeight: 'bold',
                    fontSize: '20px',
                    fontFamily: 'Arial'
                }
            },
            series: ${graphDatasets},
            yAxis: {
                title: {
                    enabled: true,
                    text: "${message(code: 'percentage')}",
                },
            },
            xAxis: {
                categories: ${graphLabels as grails.converters.JSON}
            },
            tooltip: {
                formatter: function () {
                    return this.points.reduce(function (s, point) {
                        var keyRule = point.series.name
                        var name = s
                        var fullName = calculationRulesMapByStage[keyRule]
                        var string = keyRule === fullName ? keyRule : keyRule + " " + calculationRulesMapByStage[keyRule]
                        var absoluteVal = mapAbsoluteValByStage[keyRule][point.point.index] > 0.01 ? Math.round(mapAbsoluteValByStage[keyRule][point.point.index] * 100)/100 : mapAbsoluteValByStage[keyRule][point.point.index]
                        var value = Math.round(point.y * 100)/100
                        var unitForRule = unitsMapToRuleByStage[keyRule]
                        return name + "<br/> " +string+ ": <strong>" + absoluteVal + " " + unitForRule + " / " +value + "%</strong>";
                    }, '<b>' + this.x + '</b>');
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
                }
            },
        })
    </script>

