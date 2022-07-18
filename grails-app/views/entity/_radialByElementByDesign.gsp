<%def company = grailsApplication.mainContext.getBean("companyConfiguration") %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<g:set var="indicatorService" bean="indicatorService"/>
<g:set var="radialElemChartName" value="${indicatorService.getLocalizedRenameReportVisualisation(indicator, "radialElemChart") ?: message(code:"radial_byElem_byDesign", args: [groupedBy])}"/>
<div class="containerForLifeCycle" id="radialElemChart"></div>
<script type="text/javascript">
    var title = "${radialElemChartName}"
    var calculationRulesMapRadial = ${calculationRulesMap as grails.converters.JSON}
    var mapAbsoluteValRadial = ${mapAbsoluteValues as grails.converters.JSON}
    var unitsMapToRuleRadial = ${unitsMapToRule as grails.converters.JSON}
    var breakDownlifeCycleChart = new Highcharts.chart({
        chart: {
            renderTo: 'radialElemChart',
            type: 'area',
            polar: true,
            height:600
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
        pane: {
            startAngle: 0,
            endAngle: 360,
            size: "98%"
        },
        series: ${graphDatasets as grails.converters.JSON},
        yAxis: {
            gridLineInterpolation: 'polygon',
            lineWidth: 0,
            endOnTick:false,
            maxPadding:0,
            minPadding:0,
            startOnTick:true,
            tickmarkPlacement:"on"
        },
        xAxis: {
            categories: ${graphLabels},
            tickmarkPlacement: 'on',
            lineWidth: 0
        },
        tooltip: {
            formatter: function () {
                var shortName = this.x;
                var name = calculationRulesMapStackedMat[this.x];

                return this.points.reduce(function (s, point) {
                    var keyRule = point.x
                    var name = s
                    var string = point.series.name
                    var absoluteVal = mapAbsoluteValRadial[string][point.point.index]  > 0.01 ? Math.round(mapAbsoluteValRadial[string][point.point.index] * 100)/100 : mapAbsoluteValRadial[string][point.point.index]
                    var value = Math.round(point.y * 100)/100
                    var unitForRule = unitsMapToRuleRadial[keyRule]
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
        responsive: {
            rules: [{
                condition: {
                    maxWidth: 800
                },
                chartOptions: {
                    legend: {
                        align: 'center',
                        verticalAlign: 'bottom',
                        layout: 'horizontal'
                    },
                    pane: {
                        size: '98%'
                    }
                }
            }]
        }
    })
    $(function () {
        $('[rel="popover"]').popover({
            placement : 'top',
            template: '<div class="popover" ><div class="arrow"></div><div class="popover-content" style="border-style: solid; border-color: whitesmoke; border-width: 1px"></div></div>'
        });
        $("#radialSwapper").dropdown()
    })
</script>
