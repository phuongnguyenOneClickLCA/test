<%def company = grailsApplication.mainContext.getBean("companyConfiguration") %>
<div id="mainGroupCompGraphWrapper">
<g:set var="indicatorService" bean="indicatorService"/>
<g:set var="groupMainComparisonGraphName" value="${indicator?.localizedName +" - "+calculationRule?.localizedName +", "+ unit +" - "} ${indicatorService.getLocalizedRenameReportVisualisation(indicator, "groupMainComparisonGraph") ?: message(code: "result_by_class")}"/>
    <g:if test="${drawable}">
        <div>
            <g:if test="${classCount < 1 && showWarningChart}">
                <div class="clearfix"></div>
                <div class="alert">
                    <button type="button" class="close" data-dismiss="alert">×</button>
                    <strong>${message(code: "classification_unselected_warning")}</strong>
                </div>
            </g:if>
            <div class="text-center inline-block" style="width: 100%">
                <h2>${groupMainComparisonGraphName}</h2>
                <span class="legend_info" rel="popover" data-trigger="hover" data-html="true" data-content="${message(code: 'legend_help_graph')}"><i class="icon-question-sign"></i></span>
            </div>
        </div>
        <div id="groupMainComparisonGraph" style="height: 600px;width: 100%"></div>

    </div>



    <script type="text/javascript">
        var totalChartClass
        dataForChartByDesign.set("groupMainComparisonGraph",${mapForRender as grails.converters.JSON})

        $(function () {
            var data = ${graphDatasets};
            var labels = ${graphLabels};
            totalChartClass = new Highcharts.chart('groupMainComparisonGraph', {
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
                    shared: false,

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
        <div class="nograph" style="max-width: 900px; height: 400px;"><span style="font-size: 20px; font-weight:bold; text-align: center; padding-left: 30px;"><g:message code="no_structure_chart_found"/></span>
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