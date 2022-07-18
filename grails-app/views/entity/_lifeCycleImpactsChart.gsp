<%@ page import="org.apache.commons.lang.StringUtils" %>
<%def company = grailsApplication.mainContext.getBean("companyConfiguration") %>
<g:set var="indicatorService" bean="indicatorService"/>
<g:set var="lifeCycleChartName" value="${indicatorService.getLocalizedRenameReportVisualisation(indicator, "${ruleId}wrapper")}"/>
<g:set var="lifeCycleChartNameDefaultName" value="${calculationRuleName + " (" + shortName +") "+ message(code: 'results_grouped_by') + ' ' + groupedBy} ${ lifeCycleChartName ? lifeCycleChartName : message(code: 'results_breakdown_title')}"/>
<g:hiddenField name="currentRuleInUse" value="${ruleId}" id="currentRuleInUse"/>
<div class="containerForLifeCycle" id="individualChartContainer${childEntity?.id}">
          <div id="${ruleId}wrapper" style="position: relative; float:left; padding-left: 10px; padding-right: 10px; padding-top: 10px;width: 100%;height: 600px" class="pull-left">
            <canvas id="chartForLifeCycle${childEntity?.id}"></canvas>
        </div>

</div>
    <script>
        var title = "${lifeCycleChartNameDefaultName}"
        var breakDownlifeCycleChart = new Highcharts.chart({
            chart: {
                renderTo: '${ruleId}wrapper',
                type: 'column'
            },
            title: {
                text: title,
                style: {
                    color: '#333333',
                    fontWeight: 'bold',
                    fontSize: '20px',
                    fontFamily: 'Arial'
                }
            },
            series: ${graphDatasets},
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
                valueSuffix: '${unit}',
                valueDecimals: 2,
                shared: true
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
            lang: {
                noData: "${message(code: 'graph_no_data_displayed')}"
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
