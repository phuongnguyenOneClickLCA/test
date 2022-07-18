<g:set var="indicatorService" bean="indicatorService"/>
<g:set var="byLifeCycleTreeMapName" value="${indicatorService.getLocalizedRenameReportVisualisation(indicator, "byLifeCycleTreeMap") ?: message(code:"treemap_lifecyle_subtype")}"/>
<g:set var="byGroupTreeMap" value="${indicatorService.getLocalizedRenameReportVisualisation(indicator, "byGroupTreeMap") ?: message(code:"treemap_type_subtype")}"/>
<%def company = grailsApplication.mainContext.getBean("companyConfiguration") %>
<div class="clearfix"></div>
<div class="container centerAlign">
    <h2>${message(code: "treeMap_chart")} , ${calculationRule.localizedName}</h2>
    <div class="containerForLifeCycle">
        <div name="byStage" id="byLifeCycleTreeMap"></div>
        <div name="byClass" id="byGroupTreeMap"></div>

    </div>
</div>
<script type="text/javascript">
    var treeMapContainers = {}
    var unitTreeMap = "${unit}"
    $(function () {
        drawTreeMap("byLifeCycleTreeMap");
        drawTreeMap("byGroupTreeMap");
        $('#treeMapDropdown').dropdown();
    })


    function drawTreeMap(containerId){
        var chartDatasets = ${graphDataset}
        var renderTo = containerId
        var title = "${byGroupTreeMap}"
        if(containerId == "byLifeCycleTreeMap"){
            chartDatasets = ${graphDatasetForStage}
            title = "${byLifeCycleTreeMapName}"

        }
        treeMapContainers[renderTo] = new Highcharts.chart({
            chart: {
              renderTo: renderTo
            },
            tooltip: {
                pointFormatter: function() {
                    var percentage = this.value / this.series.tree.val * 100
                    return "<strong>" + this.name + "</strong>: " + this.value.toFixed(2)+" "+unitTreeMap +" - "+ percentage.toFixed(2) + "%"
                },
                valueSuffix: unit,
                valueDecimals: 2,
                useHTML: true
            },
            credits: {
                enabled: false
            },
            lang: {
                noData: "${message(code: 'graph_no_data_displayed')}"
            },
            series: [{
                type: "treemap",
                layoutAlgorithm: 'squarified',
                allowDrillToNode: true,
                fillOpacity: 0.5,
                alternateStartingDirection: true,
                levels: [{
                    level: 1,
                    dataLabels: {
                        enabled: true,
                        align: 'left',
                        verticalAlign: 'top',
                        style: {
                            color: 'black',
                            textOutline: 0
                        },
                        useHTML: true
                    }
                },
                    {
                        level: 2,
                        dataLabels: {
                            enabled: true,
                            style: {
                                color: 'black',
                                textOutline: 0,
                                fontWeight: 'normal'
                            },
                            useHTML: true
                        }
                    }],
                data: chartDatasets,
                legend: {
                    align: 'center',
                    verticalAlign: 'bottom',
                    floating: false,
                    itemStyle: {
                        fontWeight: 'normal',
                        fontFamily: 'Arial'
                    },
                },
            }],
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
            title: {
                style: {
                    color: '#333333',
                    fontWeight: 'bold',
                    fontSize: '16px',
                    fontFamily: 'Arial'
                },
                text: title,
                useHTML: true
            }
        });

    }

</script>