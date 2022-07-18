<g:set var="indicatorService" bean="indicatorService"/>
<g:set var="sankeyChartName" value="${indicatorService.getLocalizedRenameReportVisualisation(indicator, "sankeyChartContainer") ?: message(code: "sankey_chart_title") + ", " + calculationRule?.localizedName}"/>
<%def company = grailsApplication.mainContext.getBean("companyConfiguration") %>
<a class="btn btn-default pull-right" class="dropdown-toggle"
   onclick="$('#sankeyController').toggleClass('hidden')"><g:message code="sankey_configure_chart"/></a>
<div class="clearfix"></div>

<div class="controller hidden pull-right" id="sankeyController">
    <div class="text-center"><h5><g:message code="sankey_configure_chart"/></h5></div>

    <div>
        <div class="col-3 padding-200"><p class="bold"><g:message code="sankey_select_grouping"/></p>
            <label for="byStage"><input type="checkbox" id="byStage" name="groupings" onchange="reDrawGrouping(this)"
                                        value="byStage" checked>  ${message(code: 'byStage')}</label>
            <label for="byClass"><input type="checkbox" id="byClass" name="groupings" onchange="reDrawGrouping(this)"
                                        value="byClass" checked>  <g:message
                    code="byClass"/>: ${selectedClassificationName}</label>
            <label for="byType"><input type="checkbox" id="sankey_material_types" name="groupings"
                                       onchange="reDrawGrouping(this)" value="byType"
                                       checked>  ${message(code: 'sankey_material_types')}</label>
            <label for="bySubType"><input type="checkbox" id="sankey_material_subtypes" name="groupings"
                                          onchange="reDrawGrouping(this)"
                                          value="bySubType">  ${message(code: 'sankey_material_subtypes')}</label>
        </div>

        <div class="col-3 padding-200">
            <p class="bold"><g:message code="sankey_select_rounding"/></p>
            <input type="number" id="rounding" placeholder="rounding" onkeyup="reDrawRounding(this)" onchange="reDrawRounding(this)" value="2">
        </div>

        <div class="col-3 padding-200">
            <p class="bold"><g:message code="sankey_select_grouping_items"/></p>
            <g:each in="${groupingElements}" var="groupingSet">
                <g:set var="group" value="${(String) groupingSet.key}"/>
                <p class="bold"><g:message code="${group}" dynamic="true"/></p>
                <g:each in="${(List<String>) groupingSet.value}" var="item">
                    <label><input type="checkbox" class="${group}" id="${item}" onchange="reDrawGroupingItems(this)" name="groupingItems" ${"sankey_material_subtypes".equalsIgnoreCase(group) ? "disabled" : "checked"}>  ${item}
                    </label></label>
                </g:each>
            </g:each>
        </div>
    </div>
</div>

<div id="sankeyChartContainer" class="containerForLifeCycle" style="width:100%; height:500px; clear:both"></div>
<script>
    var arrCatVsClass =
    ${arrCatVsClass as grails.converters.JSON}
    var arrClassVsType =
    ${arrClassVsType as grails.converters.JSON}
    var arrCatVsType =
    ${arrCatVsType as grails.converters.JSON}
    var arrTypeVsSubType = ${arrTypeVsSubType as grails.converters.JSON};
    var arrClassVsSubType = ${arrClassVsSubType as grails.converters.JSON};
    var unit = '${unit}';
    var rounding = $("#rounding").val() ? $("#rounding").val() : 2
    var groupingItems = []
    var myData = arrCatVsClass.concat(arrClassVsType);
    var dataRemoved = []

    myData.sort(sortArray);
    var myChart = new Highcharts.chart('sankeyChartContainer', {
        title: {
            text: '${sankeyChartName}',
            style: {
                color: '#333333',
                fontWeight: 'bold',
                fontSize: '20px',
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
            categories: null
        },
        yAxis: {
            categories: null
        },
        series: [{
            keys: ['from', 'to', 'weight'],
            data: myData,
            type: 'sankey',
            linkOpacity: 0.5,
            colors: ['rgba(127,196,204,1)', 'rgba(247,119,0,1)', 'rgba(119, 213, 109, 1)', 'rgba(115,49,70,1)', 'rgba(0,128,0,1)', 'rgba(243,153,131,1)', 'rgba(144, 127, 204, 1)', 'rgba(58, 82, 156, 1)', 'rgba(255, 204, 0, 1)', 'rgba(67,67,72,1)', 'rgba(128, 122,0,1)', 'rgba(219,28,17,1)'],
            dataLabels: {
                nodeFormatter: function () {
                    var name = this.point.name
                    if (this.point.name.length > 30) {
                        name = name.substring(0, 30).concat("...")
                    }
                    return name
                },
                allowOverlap: false,
                enabled: true,
                style: {
                    color: '#000',
                    fontWeight: 'bold',
                    fontSize: '12px',
                    textOutline: false
                },
            },
            tooltip: {
                pointFormatter: function () {
                    var weight = round(this.weight, rounding);
                    var formatter = this.from + " → " + this.to + ": <b> " + weight + " " + unit + "</b><br/>";
                    return formatter;
                },
                nodeFormatter: function () {
                    var weight = round(this.sum, rounding);
                    var formatter = this.name + ": <b> " + weight + " " + unit + "</b><br/>";
                    return formatter;
                },
                useHTML: true
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
                            onclick: function () {
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

    $(function () {
        $.each($("input[name='groupingItems']:checked"), function () {
            groupingItems.push($(this).val())
        })
    })

    function sortArray(a, b) {
        return b[2] - a[2];
    }

    function round(value, rounding) {
        var multiplier = Math.pow(10, rounding || 0);
        return Math.round(value * multiplier) / multiplier;
    }

    function reDrawGrouping(element) {
        var hasStage = $("#byStage").is(':checked');
        var hasClass = $("#byClass").is(':checked');
        var hasType = $("#sankey_material_types").is(':checked');
        var hasSubType = $("#sankey_material_subtypes").is(':checked');
        var rounding = $("#rounding").val() ? $("#rounding").val() : 2

        if (hasStage && hasClass && hasType && !hasSubType) {
            myData = arrCatVsClass.concat(arrClassVsType);
            sortAndDraw(myData, rounding)

        } else if (hasStage && hasType && hasSubType && !hasClass) {
            myData = arrCatVsType.concat(arrTypeVsSubType);
            sortAndDraw(myData, rounding, true)
        } else if (hasClass && hasStage && hasSubType && !hasType) {
            myData = arrCatVsClass.concat(arrClassVsSubType);
            sortAndDraw(myData, rounding, true)
        } else if (hasClass && hasType && hasSubType && !hasStage) {
            myData = arrClassVsType.concat(arrTypeVsSubType);
            sortAndDraw(myData, rounding, true)
        } else if (hasStage && hasClass && hasType && hasSubType) {
            myData = arrCatVsClass.concat(arrClassVsType).concat(arrTypeVsSubType);
            sortAndDraw(myData, rounding, true)
        } else {
            alert("Please select at least 3 groups before continue");
            $(element).attr("checked", "checked");
        }
        var elementId = $(element).attr("id");
        if ($(element).is(':checked')) {
            $("." + elementId).prop('disabled', false).prop("checked", true);
        } else {
            $("." + elementId).removeAttr("checked");
            $("." + elementId).prop('disabled', true);
        }


    }

    function sortAndDraw(newData, rounding, resetCheckbox) {
        newData.sort(sortArray)
        var oldChart = myChart
        oldChart.series[0].update({
            data: newData,
            tooltip: {
                pointFormatter: function () {
                    var weight = round(this.weight, rounding);
                    var formatter = this.from + " → " + this.to + ": <b> " + weight + " " + unit + "</b><br/>";
                    return formatter;
                },
                nodeFormatter: function () {
                    var weight = round(this.sum, rounding);
                    var formatter = this.name + ": <b> " + weight + " " + unit + "</b><br/>";
                    return formatter;
                },
            }
        })
        if (resetCheckbox) {
            $.each($("input[name='groupingItems']"), function () {
                $(this).attr("checked", "checked");
            })
            dataRemoved = []
        }
    }

    function reDrawGroupingItems(element) {
        var isElementChecked = $(element).is(":checked")
        var id = $(element).attr("id")
        var newData = myData
        if (!isElementChecked) {
            myData.forEach(function (item) {
                if (item[0] == id || item[1] == id) {
                    dataRemoved.push(item)
                    newData = newData.filter(itemT => itemT != item)
                }

            })
            myData = newData
            sortAndDraw(newData, rounding);
        } else {
            dataRemoved.forEach(function (item) {
                if (item[0] == id || item[1] == id) {
                    newData.push(item)
                    dataRemoved = dataRemoved.filter(itemT => itemT != item)
                }
            })
            myData = newData
            sortAndDraw(newData, rounding);
        }
    }

    function reDrawRounding(element) {
        var roundingValue = $(element).val()
        if (roundingValue) {
            sortAndDraw(myData, roundingValue)
        }
    }


</script>