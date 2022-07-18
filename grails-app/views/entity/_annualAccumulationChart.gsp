<g:set var="indicatorService" bean="indicatorService"/>
<g:set var="placeholderAnnualAccumulationChart" value="${indicatorService.getLocalizedRenameReportVisualisation(indicator, "placeholderAnnualAccumulationChart") ?: message(code:"results.annualAccumulation_heading")}"/>
<%def company = grailsApplication.mainContext.getBean("companyConfiguration") %>
<div id="graph${indicator.indicatorId}" class="containerForLifeCycle clearfix" style="margin-top: 20px">
    <div id="placeholderAnnualAccumulationChart" style="position: relative;float: left; padding-left: 10px; padding-right: 10px; padding-bottom: 30px; width: 100%;height: 600px">
        <canvas id="annualAccumulationChart${childEntity.id}"></canvas>
    </div>

</div>
<div class="clearfix"></div>
    <script type="text/javascript">
        var annualChart = new Highcharts.chart({
            chart: {
                renderTo: 'placeholderAnnualAccumulationChart',
                type: 'column'
            },
            title: {
                text: '${placeholderAnnualAccumulationChart}',
                style: {
                    color: '#333333',
                    fontWeight: 'bold',
                    fontSize: '20px',
                    fontFamily: 'Arial'
                },
                useHTML: true
            },
            lang: {
                noData: "${message(code: 'graph_no_data_displayed')}"
            },
            legend: {
                align: 'center',
                verticalAlign: 'top',
                itemStyle: {
                    fontWeight: 'normal',
                    fontFamily: 'Arial'
                },
                useHTML: true
            },
            tooltip: {
                valueSuffix: '${unit}',
                valueDecimals: 2,
                shared: true,
                useHTML: true
            },
            yAxis: {
                title: {
                    enabled: true,
                    text: "${unit?.capitalize()}",
                    useHTML: true
                },
                reversedStacks: false,
            },
            xAxis: {
                title: {
                    enabled: true,
                    text: "${message(code: 'year')?.capitalize()}",
                    useHTML: true
                },
                categories: ${labels}
            },
            series: ${datasets},
            credits: {
                enabled: false
            },
            plotOptions: {
                column: {
                    stacking: 'normal',
                }
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
