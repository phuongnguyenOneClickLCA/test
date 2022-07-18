<g:set var="indicatorService" bean="indicatorService"/>
<g:set var="bubbleChartName" value="${indicatorService.getLocalizedRenameReportVisualisation(indicator, "bubbleChartTypeContainer") ?: message(code:"bubble_chart_type_subtype_title")+", "+ calculationRule?.localizedName}"/>
<%def company = grailsApplication.mainContext.getBean("companyConfiguration") %>
<div class="clearfix"></div>

<div id="bubbleChartTypeContainer" class="containerForLifeCycle" style="width:100%; height:600px;">


</div>
<script type="text/javascript">
    Highcharts.setOptions({
        colors: ${colorTemplate} });
    var bubbleChart = new Highcharts.chart('bubbleChartTypeContainer', {
        chart: {
            type: 'packedbubble',
            height: '600px',
        },
        title: {
            text: '${bubbleChartName}',
            style: {
                color: '#333333',
                fontWeight: 'bold',
                fontSize: '20px',
                fontFamily: 'Arial'
            },
            useHTML: true
        },
        subtitle: {
            text: '${message(code:"bubble_chart_type_subtype_helptext")}',
            style: {
                color: '#333333',
                fontFamily: 'Arial'
            },
            useHTML: true
        },
        lang: {
            noData: "${message(code: 'graph_no_data_displayed')}"
        },
        tooltip:{
            valueSuffix: " ${unit}",
            pointFormatter: function () {
                var formatter = this.name + ": <b> " + this.y + " " + " ${unit}" + "</b><br/>";
                return formatter;
            },
            useHTML: true
        },
        legend: {
            layout: 'vertical',
            backgroundColor: '#FFFFFF',
            align: 'right',
            verticalAlign: 'middle',
            floating: true,
            itemStyle: {
                fontWeight: 'normal',
                fontFamily: 'Arial'
            },
            useHTML: true
        },
        credits: {
            enabled: false
        },
        plotOptions: {
            packedbubble: {
                useSimulation: true,
                minSize: '10',
                maxSize: '200',
                layoutAlgorithm: {
                    bubblePadding: 15,
                    gravitationalConstant: 0.01,
                    splitSeries: false,
                },
                dataLabels: {
                    enabled: true,
                    allowOverlap:false,
                    format: '{point.name}',
                    style: {
                        color: '#000',
                        fontSize: '12px',
                        fontWeight: 'regular',
                        textOutline: false,
                        fontFamily: 'Arial',

                    },
                    filter: {
                        property: 'value',
                        operator: '>',
                        value: ${threshold}
                    }
                }
            }
        },
        series: [${graphDataset}],
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
    });
    $(function(){
        $('#bubbleTypeSubTypeDropDown').dropdown();
    })

</script>