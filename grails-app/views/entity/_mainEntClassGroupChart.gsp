<%def company = grailsApplication.mainContext.getBean("companyConfiguration") %>
<g:set var="indicatorService" bean="indicatorService"/>
<div id="mainEntClassGroupWrapper">
    <g:set var="mainEntClassGroupGraphName" value="${indicator?.localizedName +" - "+calculationRule?.localizedName +", "+ unit +" - "} ${indicatorService.getLocalizedRenameReportVisualisation(indicator, "mainEntClassGroupGraph") ?: message(code: "compare_elements")}"/>
    <div>
        <div class="text-center inline-block"style="width: 100%"><h2 class="text-center ">${mainEntClassGroupGraphName} </h2> <span class="legend_info" rel="popover" data-trigger="hover" data-html="true" data-content="${message(code: 'legend_help_graph')}"><i class="icon-question-sign"></i></span></div>
        <div class="btn-group pull-right">
            <a class="btn btn-default dropdown-toggle pointerCursor" data-toggle="dropdown"><g:message code="simulationTool.selectGrouping"/> <span class="caret"></span></a>
            <ul class="dropdown-menu">
                <g:each in="${mapForGroupTypeTitles}" var="titleMap">
                    <g:if test="${!titleMap.value?.isEmpty()}">
                        <li><a id="${titleMap.key}" class="${defaultStatus.equalsIgnoreCase(titleMap.key) ? 'selected' :''}" onclick="changeGroup('${titleMap.key}')" name="groupLogic"><g:message code="${titleMap.key}" dynamic="true"/></a> </li>
                    </g:if>
                </g:each>
            </ul>
        </div>
    </div>
    <div class="clearfix"></div>

    <div id="mainEntClassGroupGraph" style="height: 500px; width: 100%"></div>

</div>



    <script type="text/javascript">
        var classGroupChart
        dataForChartByDesign.set("mainEntClassGroupGraph",${mapByAdditionalQs as grails.converters.JSON})
        var labelMaps = ${mapForGroupTypeTitles as grails.converters.JSON}
        var labelsForChart = ${labelsForChart}
        var groupStatus = "${defaultStatus}"
        var dataForClassGroupChart
        var dataByClass = ${mapByAdditionalQs.collect({[name: it.key, data: it.value.collect({k,v -> v}) as List]}) as grails.converters.JSON}
        var dataByQuestion = ${mapByQueryQuestions.collect({[name: it.key, data: it.value.collect({k,v -> v}) as List]}) as grails.converters.JSON}
        var dataBySection = ${mapBySections.collect({[name: it.key, data: it.value.collect({k,v -> v}) as List]}) as grails.converters.JSON}
        $(function () {
            setChartData()
            classGroupChart = new Highcharts.chart('mainEntClassGroupGraph', {
                chart: {
                    type: 'column'
                },
                title: {
                    text: '',
                },
                xAxis: {
                    categories:labelsForChart,
                },
                yAxis: {
                    title:{
                        text:"${unit}"
                    }
                },
                lang: {
                    noData: "${message(code: 'graph_no_data_displayed')}"
                },
                credits: {
                    enabled: false
                },
                series: dataForClassGroupChart,
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
        });
        $(function(){
            $('.dropdown-toggle').dropdown();
        });
        function setChartData() {
            if(groupStatus == "byClass"){
                dataForClassGroupChart = dataByClass

            }else if(groupStatus == "byQuestion"){
                dataForClassGroupChart = dataByQuestion
            } else {
                dataForClassGroupChart = dataBySection
            }
            labelsForChart = labelMaps[groupStatus]
        }
        function changeGroup(groupType) {
            $("a[name='groupLogic']").removeClass("selected")
            $("#"+groupType).addClass("selected")
            if(groupStatus != groupType){
                groupStatus = groupType
            }
            setChartData()
            classGroupChart.update({
                xAxis: {
                    categories:labelsForChart,
                },
                series:dataForClassGroupChart
            })
        }
    </script>
