<%def company = grailsApplication.mainContext.getBean("companyConfiguration") %>
<g:set var="indicatorService" bean="indicatorService"/>
<div id="graphWrapper" class="text-center">
    <g:set var="totalResultChartName" value="${indicatorService.getLocalizedRenameReportVisualisation(indicator, "totalResultChart") ?: indicator?.localizedName +" - "+message(code: "total_results")}"/>
    <div class="text-center inline-block">
        <h2>${indicator.localizedName} - ${message(code: "total_results")} </h2>
        <span class="legend_info" rel="popover" data-trigger="hover" data-html="true" data-content="${message(code: 'legend_help_graph')}"><i class="icon-question-sign"></i></span>
    </div>
    <div id="totalResultChart"></div>
</div>
<script type="text/javascript">
    var totalResultChart
    dataForChartByDesign.set("totalResultChart",${scoresByDesigns as grails.converters.JSON})
    Highcharts.setOptions({
        colors: ${colors as grails.converters.JSON}
    });
    $(function () {
        maxValueForCategory = ${maxValueForRule as grails.converters.JSON};
        mapByRuleByDesign = ${mapByRuleByDesign as grails.converters.JSON};
        var calculationRulesMapMain = ${calculationRulesMap as grails.converters.JSON}
        var mapAbsoluteValMain = ${mapAbsoluteValues as grails.converters.JSON}
        var unitsMapToRuleMain = ${unitsMapToRule as grails.converters.JSON}

        var fallbackDesignList = []
        $.each($("input[name='designName']:checked"), function(){
            fallbackDesignList.push($(this).attr("id"))
        });

        totalResultChart = new Highcharts.chart('totalResultChart', {
            chart: {
                type: 'column'
            },
            title: {
                text: ''
            },
            xAxis: {
                categories:${graphLabels},
            },
            yAxis: {
                title:{
                    text:"${yAxisTitle}"
                },
                labels: {
                    format: '{value} %'
                }
            },
            tooltip: {
                formatter: function () {
                    var shortName = this.x;
                    var name = calculationRulesMapMain[this.x];

                    return this.points.reduce(function (s, point) {
                        var keyRule = point.x
                        var name = s
                        var string = point.series.name
                        var absoluteVal = mapAbsoluteValMain[string][point.point.index] > 0.01 ? Math.round(mapAbsoluteValMain[string][point.point.index] * 100)/100 : mapAbsoluteValMain[string][point.point.index]
                        var value = Math.round(point.y * 100)/100
                        var unitForRule = unitsMapToRuleMain[keyRule]
                        return name + "<br/> " +string+ ": <strong>" + absoluteVal + " " + unitForRule + " / " +value + "%</strong>";
                    },'<b>' + (shortName === name ? name : shortName + " " + name) +'</b>');
                },
                shared: true,

            },
            lang: {
                noData: "${message(code: 'graph_no_data_displayed')}"
            },
            credits: {
                enabled: false
            },
            series: ${mapForRender as grails.converters.JSON},
            plotOptions: {
                series: {
                    events: {
                        legendItemClick: function (e) {
                            e.preventDefault()
                            if(fallbackDesignList.indexOf(this.name) != -1){
                                fallbackDesignList.splice(fallbackDesignList.indexOf(this.name),1)
                            } else {
                                fallbackDesignList.push(this.name)
                            }
                            calibrateMaxValuePerRule(${scoresByDesigns as grails.converters.JSON}, totalResultChart, fallbackDesignList)
                        }
                    }
                }
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
            responsive: {
                rules: [{
                    condition: {
                        maxWidth: 700
                    },
                    chartOptions: {
                        legend: {
                            align: 'center',
                            layout: 'horizontal'
                        },
                        yAxis: {
                            labels: {
                                align: 'left',
                                x: 0,
                                y: -5
                            },
                            title: {
                                text: null
                            }
                        },
                        subtitle: {
                            text: null
                        },
                        credits: {
                            enabled: false
                        }
                    }
                }]
            }
        })
    })

</script>