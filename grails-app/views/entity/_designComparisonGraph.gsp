<g:set var="classifications" value="${classificationList}"/>
<g:set var="indicatorService" bean="indicatorService"/>
<g:set var="elemCompChartContainerName" value="${indicator?.localizedName +" - "+calculationRule?.localizedName +", "+ unit +" - "} ${indicatorService.getLocalizedRenameReportVisualisation(indicator, "elemCompChartContainer") ?: message(code: "compare_elements_and_stages")}"/>
<%def company = grailsApplication.mainContext.getBean("companyConfiguration") %>
<g:set var="categories" value="${resultCategories.collect({it.resultCategory + " " + it.localizedShortName})}"/>
<div class="clearfix"></div>
<div class="text-center inline-block" style="width: 100%"><h2>${elemCompChartContainerName} </h2> <span class="legend_info" rel="popover" data-trigger="hover" data-html="true" data-content="${message(code: 'legend_help_graph')}"><i class="icon-question-sign"></i></span></div>
<div class="controller hide-on-print" id="elemCompController">
    <div>
        <div>
            <div style="display: inline">
                <p><span class="bold">${g.message(code: 'select_elements_to_show')}: </span>
                    <a href="javascript:;" onclick="toggleSelectForClass('classificationNames',true)">${message(code:"deselect_all")}</a> / <a href="javascript:;" onclick="toggleSelectForClass('classificationNames')">${message(code:"ifc.select.all")}</a>
                </p>
            </div>
            <g:each in="${classifications}" var="classification">
                <label class="col-4"><input type="checkbox" id="${classification}" onchange="reDrawByClass()" name="classificationNames" checked> ${classification}</label>
            </g:each>
        </div>

    </div>
</div>
<div id="elemCompChartContainer" style="width:100%; height:500px;"></div>
    <script type="text/javascript">
        dataForChartByDesign.set("elemCompChartContainer",${mapByCategoryThenType as grails.converters.JSON})
        $('#elementCompareDropdown').dropdown();
        $('#elementCompIndicatorDropdown').dropdown();
        var dataset = [];
        <g:each in="${mapByCategoryThenType}" var="mapForCategory">
        <g:set var="categoryAsKey" value="${mapForCategory.key}"/>
        <g:set var="designResult" value="${mapForCategory.value}"/>
        var item = {
            name: '${categoryAsKey}',
            type: 'column',
            data: ${designResult?.collect({it.value?.collect({it.value})?.sum()}) as grails.converters.JSON}
        };
        dataset.push(item);
        </g:each>

        var elementComparisonChart =  new Highcharts.chart('elemCompChartContainer', {
            title: {
                text: '',
            },
            xAxis: {
                categories:${designNames as grails.converters.JSON},
            },
            credits: {
                enabled: false
            },
            lang: {
                noData: "${message(code: 'graph_no_data_displayed')}"
            },
            yAxis: {
                title:{
                    text:"${unit}"
                }
            },
            series: dataset,
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
        function reDrawByDesign(){
            var designElement = []
            $.each($("input[name='designName']:checked"), function(){
                designElement.push($(this).attr("id"))
            });
            elementComparisonChart.xAxis[0].setCategories(designElement);
            reDrawByClass()
        }
        function reDrawByClass(){
            // elementComparisonChart.series.find(it => it.name == categoryName).data (or can be setData)
            var classElement = []
            var designElement = []
            $.each($("input[name='classificationNames']:checked"), function(){
                classElement.push($(this).attr("id"))
            });
            $.each($("input[name='designName']:checked"), function(){
                designElement.push($(this).attr("id"))
            });
            var chartData = ${mapByCategoryThenType as grails.converters.JSON};
            var designName = ${designNames as grails.converters.JSON};
            for (var category in chartData) {
                var valueMap = chartData[category]
                if (valueMap) {
                    var newDataset = []
                    for(var design in valueMap){
                        if(designElement.indexOf(design) != -1){
                            var valueSubMap = valueMap[design]
                            var result = 0
                            for (var clas in valueSubMap){
                                if(classElement.indexOf(clas) != -1){
                                    result += valueSubMap[clas]
                                }
                            }
                            newDataset.push(result)
                        }
                    }
                    elementComparisonChart.series.find(it => it.name == category).setData(newDataset)
                }
            }
        }
        function toggleSelectForClass(typeName, uncheck) {
            if(uncheck){
                $("input[name="+typeName+"]").prop('checked', false);
            } else {
                $("input[name="+typeName+"]").prop('checked', true);
            }
            reDrawByDesign()
        }
    </script>
