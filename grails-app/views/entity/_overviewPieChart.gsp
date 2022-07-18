<%def company = grailsApplication.mainContext.getBean("companyConfiguration") %>
<%@ page import="com.bionova.optimi.core.Constants" %>
<g:set var="indicatorService" bean="indicatorService"/>
<g:set var="byStageSixPackName"
       value="${calculationRule.localizedName + " " + unit + " - "}${indicatorService.getLocalizedRenameReportVisualisation(indicator, "byStageSixPack") ?: message(code: "byStage")}"/>
<g:set var="byClassSixPackName"
       value="${calculationRule.localizedName + " " + unit + " - "}${indicatorService.getLocalizedRenameReportVisualisation(indicator, "byClassSixPack") ?: message(code: "byClass")}"/>
<g:set var="byMaterialSixPackName"
       value="${calculationRule.localizedName + " " + unit + " - "}${indicatorService.getLocalizedRenameReportVisualisation(indicator, "byMaterialSixPack") ?: message(code: "byType")}"/>
<g:set var="massByClassSixPackName"
       value="${message(code: "mass") + " " + unitForMass + " - "}${indicatorService.getLocalizedRenameReportVisualisation(indicator, "massByClassSixPack") ?: message(code: "byClass")}"/>
<g:set var="byClassSixPackTitle"
       value="${selectedClassificationName ? calculationRule.localizedName + " " + unit + " - " + selectedClassificationName : byClassSixPackName}"/>
<g:set var="massByClassSixPackTitle"
       value="${selectedClassificationName ? message(code: "mass") + " " + unitForMass + " - " + selectedClassificationName : massByClassSixPackName}"/>
<div class="container section" id="focalDivFourPack">
    <div class="text-center">
        <h2 style="display: inline-block"><g:message code="overview_chart_title"/> ${calculationRule.localizedName}</h2>
    </div>

    <div class="container ">
        <div class="card">
            <ul class="nav nav-tabs" role="tablist">
                <li role="presentation" class="navInfo active">
                    <a id="pie" href="javascript:"><g:message code="pie_chart"/></a>
                </li>
                <li role="presentation" class="navInfo">
                    <a id="bar" href="javascript:"><g:message code="bar_chart"/></a>
                </li>
                <li role="presentation" class="navInfo">
                    <a id="column" href="javascript:"><g:message code="column_chart"/></a>
                </li>
                <li role="presentation" class="navInfo">
                    <a id="treemap" href="javascript:"><g:message code="treeMap_chart"/></a>
                </li>
                <li>
                    <i class="icon-question-sign epc-container-number" rel="popover" data-trigger="hover"
                       data-html="true" data-content="${message(code: 'switch_chart_type')}"></i>
                </li>

            </ul>
        </div>
    </div>

    <div class="containerForLifeCycle">
        <div class="chart-div borderbox-light active" name="byStage" id="byStageSixPack"></div>

        <div class="chart-div borderbox-light" name="byClass" id="byClassSixPack"></div>

        <div class="chart-div borderbox-light" name="byType" id="byMaterialSixPack"></div>

        <div class="chart-div borderbox-light" name="massByClass" id="massByClassSixPack"></div>
    </div>

    <div class="table-div-parent">
        <div class="table-controller text-center">
            <p><strong><g:message code="result.show_data_table"/>:</strong>
                <label style="display:inline-block; margin-left:5px; cursor:pointer;"><input type="checkbox" name="tableDataResultCat" onclick="toggleTable(this)"/>${byStageSixPackName}
                </label>
                <label style="display:inline-block; margin-left:5px; cursor:pointer;"><input type="checkbox" name="tableDataClassification" onclick="toggleTable(this)"/>${byClassSixPackTitle}
                </label>
                <label style="display:inline-block; margin-left:5px; cursor:pointer;"><input type="checkbox" name="tableDataType" onclick="toggleTable(this)"/>${byMaterialSixPackName}
                </label>
                <label style="display:inline-block; margin-left:5px; cursor:pointer;"><input type="checkbox" name="tableDataResultCatForMass" onclick="toggleTable(this)"/>${massByClassSixPackTitle}
                </label>
            </p>
        </div>

        <div class="table-div">
            <div class="hide show-on-print" id="tableDataResultCat">
                <br/>
                <h4>${byStageSixPackName}</h4>
                <br/>
                <table class="table table-striped table-condensed ">
                    <thead>
                    <tr>
                        <th><g:message code="item"/></th>
                        <th class='number'><g:message code="value"/></th>
                        <th><g:message code="entity.show.unit"/></th>
                        <th class='number'><g:message code="percentage"/> %</th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:if test="${formattedResultTableForResultCategory.size() > 0}">
                        <g:each var="item" in="${formattedResultTableForResultCategory}">
                            <tr>
                                <td>${item.key}</td>
                                <td class='number'>${item.value.get('value')}</td>
                                <td>${unit}</td>
                                <td class='number'>${item.value.get('percentage')} %</td>
                            </tr>
                        </g:each>
                    </g:if>
                    </tbody>
                </table>
            </div>

            <div id="tableDataClassification" class="hide show-on-print">
                <br/>
                <h4>${byClassSixPackTitle}</h4>
                <br/>
                <table class="table table-striped table-condensed ">
                    <thead>
                    <tr>
                        <th><g:message code="item"/></th>
                        <th class='number'><g:message code="value"/></th>
                        <th><g:message code="entity.show.unit"/></th>
                        <th class='number'><g:message code="percentage"/> %</th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:if test="${formattedResultTableForClassification.size() > 0}">
                        <g:each var="item" in="${formattedResultTableForClassification}">
                            <tr>
                                <td>${item.key}</td>
                                <td class='number'>${item.value.get('value')}</td>
                                <td>${unit}</td>
                                <td class='number'>${item.value.get('percentage')} %</td>
                            </tr>
                        </g:each>
                    </g:if>
                    </tbody>
                </table>

            </div>

            <div id="tableDataType" class="hide show-on-print">
                <br/>
                <h4>${byMaterialSixPackName}</h4>
                <br/>
                <table class="table table-striped table-condensed ">
                    <thead>
                    <tr>
                        <th><g:message code="item"/></th>
                        <th class='number'><g:message code="value"/></th>
                        <th><g:message code="entity.show.unit"/></th>
                        <th class='number'><g:message code="percentage"/> %</th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:if test="${formattedResultTableForTypes.size() > 0}">
                        <g:each var="item" in="${formattedResultTableForTypes}">
                            <tr>
                                <td>${item.key}</td>
                                <td class='number'>${item.value.get('value')}</td>
                                <td>${unit}</td>
                                <td class='number'>${item.value.get('percentage')} %</td>
                            </tr>
                        </g:each>
                    </g:if>
                    </tbody>
                </table>

            </div>

            <div id="tableDataResultCatForMass" class="hide show-on-print">
                <br/>
                <h4>${massByClassSixPackTitle}</h4>
                <br/>
                <table class="table table-striped table-condensed ">
                    <thead>
                    <tr>
                        <th><g:message code="item"/></th>
                        <th class='number'><g:message code="value"/></th>
                        <th><g:message code="entity.show.unit"/></th>
                        <th class='number'><g:message code="percentage"/> %</th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:if test="${formattedResultTableMassByClassification.size() > 0}">
                        <g:each var="item" in="${formattedResultTableMassByClassification}">
                            <tr>
                                <td>${item.key}</td>
                                <td class='number'>${item.value.get('value')}</td>
                                <td>${unitForMass}</td>
                                <td class='number'>${item.value.get('percentage')} %</td>
                            </tr>
                        </g:each>
                    </g:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<script>
    Highcharts.setOptions({
        colors: ${colorTemplate},
    });

    var pieChartsSixPack = {}

    $(function () {
        $(function () {
            $('[rel="popover"]').popover({
                placement: 'top',
                template: '<div class="popover" ><div class="arrow"></div><div class="popover-content" style="border-style: solid; border-color: whitesmoke; border-width: 1px"></div></div>'
            });
        })

        drawPieChart('byStageSixPack', ${pieGraphDatasetForStages}, '${byStageSixPackName}', null, '${unit}');
        drawPieChart('byClassSixPack', ${pieGraphDatasetForClassification}, '${byClassSixPackName}', '${byClassSixPackTitle.replace("'","\\'")}', '${unit}');
        drawPieChart('byMaterialSixPack', ${pieGraphDatasetForTypes}, '${byMaterialSixPackName}', null, '${unit}', ${pieDrillDownSubType});
        drawPieChart('massByClassSixPack', ${massGraphDatasetForClassification}, '${massByClassSixPackName}', '${massByClassSixPackTitle.replace("'","\\'")}', '${unitForMass}')


        $('#pie').on('click', function (event) {
            var tab = $(this).parent()
            $(tab).addClass('active')
            $(tab).siblings().removeClass('active')
            $.each(pieChartsSixPack, function (key, chartItem) {
                updateChartBasedOnTypeAndDrillDown("pie", key, chartItem, renderUnitByChartId(key));
            })

        });

        $('#bar').on('click', function (event) {
            var tab = $(this).parent()
            $(tab).addClass('active')
            $(tab).siblings().removeClass('active')

            $.each(pieChartsSixPack, function (key, chartItem) {
                updateChartBasedOnTypeAndDrillDown("bar", key, chartItem, renderUnitByChartId(key));
            })
        });

        $('#column').on('click', function (event) {
            var tab = $(this).parent()
            $(tab).addClass('active')
            $(tab).siblings().removeClass('active')
            $.each(pieChartsSixPack, function (key, chartItem) {
                updateChartBasedOnTypeAndDrillDown("column", key, chartItem, renderUnitByChartId(key));
            })
        });

        $('#treemap').on('click', function (event) {
            var tab = $(this).parent()
            $(tab).addClass('active')
            $(tab).siblings().removeClass('active')
            $.each(pieChartsSixPack, function (key, chartItem) {
                updateChartBasedOnTypeAndDrillDown("treemap", key, chartItem, renderUnitByChartId(key));
            })
        });

    })

    function drawPieChart(containerId, dataset, title, classification, unit, drilldown) {
        var renderTo = containerId
        if (drilldown) {
            pieChartsSixPack[renderTo] = new Highcharts.chart({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false,
                    type: 'pie',
                    marginBottom: 100,
                    renderTo: renderTo,
                },
                title: {
                    text: title,
                    style: {
                        color: '#333333',
                        fontWeight: 'bold',
                        fontSize: '16px',
                        fontFamily: 'Arial'
                    },
                    useHTML: true
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
                subtitle: {
                    text: '${message(code:"drilldown_chart_help")}',
                    style: {
                        color: '#333333',
                        fontFamily: 'Arial'
                    }
                },
                credits: {
                    enabled: false
                },
                tooltip: {
                    pointFormat: '{point.name}: <b>{point.y} / {point.percentage:.1f}%</b>',
                    valueSuffix: unit,
                    valueDecimals: 2,
                    shared: false,
                    useHTML: true
                },
                legend: {
                    align: 'center',
                    verticalAlign: 'bottom',
                    floating: false,
                    labelFormatter: function () {
                        if (this.percentage) {
                            return this.name + " - " + this.percentage.toFixed(1) + "%"
                        }
                    },
                    itemStyle: {
                        fontWeight: 'normal',
                        fontFamily: 'Arial'
                    },
                    itemWidth: 250,
                    width: '100%',
                    useHTML: true
                },

                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        showInLegend: true,
                        size: 220
                    },
                    bar: {
                        showInLegend: false,
                    },
                    column: {
                        showInLegend: false,
                    },
                    series: {
                        dataLabels: {
                            enabled: false
                        }
                    }
                },
                series: [{
                    name: title,
                    colorByPoint: true,
                    data: dataset
                }],
                drilldown: {
                    drillUpButton: {
                        position: {
                            verticalAlign: 'right',
                            x: 0,
                            y: -10
                        }
                    },
                    series: drilldown
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
                                    onclick: function () {
                                        this.downloadXLSX();
                                    }
                                }
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
                        legend: {
                            align: 'center',
                            verticalAlign: 'top',
                            itemWidth: undefined,
                            width: 500,
                            margin: 50,
                            x: 50
                        }
                    }
                }
            });
        } else {
            var newTitle = classification ? classification : title;
            pieChartsSixPack[renderTo] = new Highcharts.chart({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false,
                    type: 'pie',
                    marginBottom: 100,
                    renderTo: renderTo
                },
                title: {
                    text: newTitle,
                    style: {
                        color: '#333333',
                        fontWeight: 'bold',
                        fontSize: '16px',
                        fontFamily: 'Arial'
                    },
                    useHTML: true
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
                    pointFormat: '{point.name}: <b>{point.y} / {point.percentage:.1f}%</b>',
                    valueSuffix: unit,
                    valueDecimals: 2,
                    shared: false,
                    useHTML: true
                },
                legend: {
                    align: 'center',
                    verticalAlign: 'bottom',
                    floating: false,
                    labelFormatter: function () {
                        var name = this.name
                        if (this.percentage) {
                            name = this.name + " - " + this.percentage.toFixed(1) + "%"
                        }
                        return name
                    },
                    itemStyle: {
                        fontWeight: 'normal',
                        fontFamily: 'Arial'

                    },
                    itemWidth: 250,
                    width: '100%',
                    useHTML: true
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        showInLegend: true,
                        size: 220
                    },
                    series: {
                        dataLabels: {
                            enabled: false
                        }
                    }
                },
                series: [{
                    name: newTitle,
                    colorByPoint: true,
                    data: dataset
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
                                    onclick: function () {
                                        this.downloadXLSX();
                                    }
                                }
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
                        legend: {
                            align: 'center',
                            verticalAlign: 'top',
                            itemWidth: undefined,
                            width: 500,
                            margin: 50,
                            x: 50
                        }
                    }
                }
            });
        }
    }


    function updateChartBasedOnTypeAndDrillDown(chartType, chartId, chartItem, unitForThisChart) {
        var dataset = renderDatasetBasedOnType(chartType, chartId);
        var drilldown;
        var showYAxis = chartType == "pie" ? false : true
        var showXAxis = chartType == "pie" ? false : true
        var showDataLabels = chartType == "treemap" ? true : false
        if (chartId == "byMaterialSixPack" && chartType == "pie") {
            drilldown = ${pieDrillDownSubType};
        } else if (chartId == "byMaterialSixPack" && chartType != "treemap") {
            drilldown = ${drillDownSubType};
        }
        if (chartType == "treemap") {
            chartItem.drillUp()
            chartItem.update({
                chart: {
                    type: chartType,
                    marginBottom: 0,

                },
                legend: {
                    display: false,
                },
                plotOptions: {
                    bar: {
                        showInLegend: false,
                    },
                    column: {
                        showInLegend: false,
                    },
                    treemap: {
                        showInLegend: false
                    }
                },
                subtitle: {
                    text: '',
                    style: {
                        color: '#333333',
                        fontFamily: 'Arial'
                    }
                },
                xAxis: {
                    visible: showXAxis,
                    max: null
                },
                yAxis: {
                    visible: showYAxis,
                    max: null
                },
                tooltip: {
                    pointFormatter: function () {
                        var percentage = this.value / this.series.tree.val * 100
                        var name = "<strong>" + this.name + "</strong>: " + this.value.toFixed(2) + " " + unitForThisChart + " / " + percentage.toFixed(2) + "%"
                        return name
                    },

                },
                dataLabels: {
                    enabled: showDataLabels,
                    style: {
                        color: 'black',
                        textOutline: 0,
                        fontWeight: "normal"
                    }
                },
                series: [{
                    type: chartType,
                    fillOpacity: 0.5,
                    data: dataset,
                    colorByPoint: !showDataLabels,
                    layoutAlgorithm: 'squarified',
                    levels: [{
                        level: 1,
                        dataLabels: {
                            enabled: showDataLabels,
                            align: 'left',
                            verticalAlign: 'top',
                            style: {
                                color: 'black',
                                textOutline: 0,
                                fontWeight: 'bold'
                            }
                        }
                    },
                        {
                            level: 2,
                            dataLabels: {
                                enabled: showDataLabels,
                                style: {
                                    color: 'black',
                                    textOutline: 0,
                                    fontWeight: 'normal'
                                }
                            }
                        }],
                }],

            });

        } else {
            if (drilldown) {
                chartItem.drillUp()
                chartItem.update({
                    chart: {
                        type: chartType,
                        marginBottom: 100,
                    },
                    legend: {
                        labelFormatter: function () {
                            var name = this.name
                            if (chartType == "pie") {
                                if (this.percentage) {
                                    name = this.name + " - " + this.percentage.toFixed(1) + "%"
                                }
                            }
                            return name
                        }
                    },
                    plotOptions: {
                        bar: {
                            showInLegend: false,
                        },
                        column: {
                            showInLegend: false,
                        }
                    },
                    xAxis: {
                        visible: showXAxis,
                        type: 'category',
                        max: null
                    },
                    yAxis: {
                        visible: showYAxis,
                        max: null
                    },
                    tooltip: {
                        pointFormatter: function () {
                            var name = this.name + ": <b>" + this.y + " " + unitForThisChart + "</b>"
                            if (chartType == "pie") {
                                if (this.percentage) {
                                    name = this.name + ": <b>" + this.y + " " + unitForThisChart + " / " + Math.round(this.percentage) + "%" + "</b>"
                                }
                            }
                            return name
                        }
                    },
                    drilldown: {
                        series: drilldown
                    },
                    subtitle: {
                        text: '${message(code:"drilldown_chart_help")}',
                        style: {
                            color: '#333333',
                            fontFamily: 'Arial'
                        }
                    },
                    series: [{
                        type: chartType,
                        data: dataset,
                        fillOpacity: 1,
                        layoutAlgorithm: undefined,
                        allowDrillToNode: undefined,
                        alternateStartingDirection: undefined,
                        dataLabels: showDataLabels,
                        levels: undefined,
                        colorByPoint: !showDataLabels,

                    }],

                });
            } else {
                chartItem.update({
                    chart: {
                        type: chartType,
                        marginBottom: 100,
                    },
                    legend: {
                        labelFormatter: function () {
                            var name = this.name
                            if (chartType == "pie") {
                                name = this.name + " - " + this.percentage.toFixed(1) + "%"
                            }
                            return name
                        }
                    },
                    plotOptions: {
                        bar: {
                            showInLegend: false,
                        },
                        column: {
                            showInLegend: false,
                        },
                        treemap: {
                            showInLegend: false
                        }
                    },
                    xAxis: {
                        visible: showXAxis,
                        type: 'category',
                        max: null
                    },
                    yAxis: {
                        visible: showYAxis,
                        max: null
                    },
                    tooltip: {
                        pointFormatter: function () {
                            var name = this.name + ": <b>" + this.y + " " + unitForThisChart + "</b>"
                            if (chartType == "pie") {
                                if (this.percentage) {
                                    name = this.name + ": <b>" + this.y + " " + unitForThisChart + " / " + Math.round(this.percentage) + "%" + "</b>"
                                }
                            }
                            return name
                        }
                    },
                    dataLabels: {
                        enabled: showDataLabels,
                    },
                    series: [{
                        type: chartType,
                        colorByPoint: !showDataLabels,
                        fillOpacity: 1,
                        layoutAlgorithm: undefined,
                        allowDrillToNode: undefined,
                        alternateStartingDirection: undefined,
                        data: dataset,
                        levels: undefined
                    }],

                });
            }
        }
    }

    function renderDatasetBasedOnType(chartType, chartId) {
        var dataset
        if (chartType == "pie") {
            if (chartId == "byStageSixPack") {
                dataset =
                ${pieGraphDatasetForStages}
            } else if (chartId == "byClassSixPack") {
                dataset =
                ${pieGraphDatasetForClassification}
            } else if (chartId == "byMaterialSixPack") {
                dataset =
                ${pieGraphDatasetForTypes}
            } else {
                dataset =
                ${massGraphDatasetForClassification}
            }
        } else if (chartType == "treemap") {
            if (chartId == "byStageSixPack") {
                dataset =
                ${treeChartDatasetForResultCategory}
            } else if (chartId == "byClassSixPack") {
                dataset =
                ${treeChartDatasetForClassification}
            } else if (chartId == "byMaterialSixPack") {
                dataset =
                ${treeChartDatasetsForTypes}
            } else {
                dataset =
                ${massTreeChartDatasetForClassification}
            }
        } else {
            if (chartId == "byStageSixPack") {
                dataset =
                ${graphDatasetForStages}
            } else if (chartId == "byClassSixPack") {
                dataset =
                ${graphDatasetForClassification}
            } else if (chartId == "byMaterialSixPack") {
                dataset =
                ${graphDatasetForTypes}
            } else {
                dataset =
                ${massGraphDatasetForClassification}
            }
        }

        return dataset
    }

    function renderUnitByChartId(chartId) {
        var unit = "${unit}"
        if (chartId == "massByClassSixPack") {
            unit = "${unitForMass}"
        }
        return unit
    }
</script>
