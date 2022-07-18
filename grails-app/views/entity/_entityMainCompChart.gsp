<div id="mainCompGraphWrapper">
<%def company = grailsApplication.mainContext.getBean("companyConfiguration") %>
<g:set var="indicatorService" bean="indicatorService"/>
<g:set var="stageMainComparisonGraphName" value="${indicator?.localizedName +" - "+calculationRule?.localizedName +", "+ unit +" - "} ${indicatorService.getLocalizedRenameReportVisualisation(indicator, "stageMainComparisonGraph") ?: message(code: "byStage")}"/>
<g:if test="${!scoresByDesignAllStage?.isEmpty()}">
    <div class="text-center inline-block" style="width: 100%">
        <h2 class="text-center">${stageMainComparisonGraphName}</h2>
        <span class="legend_info" rel="popover" data-trigger="hover" data-html="true" data-content="${message(code: 'legend_help_graph')}"><i class="icon-question-sign"></i></span>
    </div>
    <div id="stageMainComparisonGraph" style="height: 500px; width: 100%"></div>

    </div>

<script type="text/javascript">
    var totalChartStage
    dataForChartByDesign.set("stageMainComparisonGraph",${mapForRender as grails.converters.JSON})
    $(function () {
        var data = ${graphDatasets as grails.converters.JSON};
        var labels = ${graphLabels};
        totalChartStage = new Highcharts.chart('stageMainComparisonGraph', {
            chart: {
                type: 'column'
            },
            title: {
                text: '',
            },
            xAxis: {
                categories:${graphLabels},
            },
            yAxis: {
                title:{
                    text:"${unit}"
                }
            },
            credits: {
                enabled: false
            },
            lang: {
                noData: "${message(code: 'graph_no_data_displayed')}"
            },
            series: data,
            plotOptions: {
                column: {
                    stacking: 'normal',
                }
            },
            tooltip: {
                valueSuffix: '${unit}',
                valueDecimals: 2,
                shared: false
            },
            legend: {
                align: 'center',
                verticalAlign: 'top',
                itemStyle: {
                    fontWeight: 'normal',
                    fontFamily: 'Arial'
                },

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

    });

</script>
</g:if><g:else>
    <div class="container text-center" style="padding-top: 50px">
    <div class="nograph" style="max-width: 1100px; height: 400px;"><span style="font-size: 20px; font-weight:bold; text-align: center; padding-left: 30px;"><g:message code="no_structure_chart_found"/></span>
        <div>
        <i class="fa fa-ban fa-extraLargeDiv" aria-hidden="true"></i>
        </div>
    </div>
    </div>
    </div>
</g:else>
<script>
    $(function(){
        $('.dropdown-toggle').dropdown();
    });
</script>