<%@ page import="org.apache.commons.lang.StringUtils" %>
<%def company = grailsApplication.mainContext.getBean("companyConfiguration") %>
<g:if test="${!individualCategoryBreakdown}">
    <g:hiddenField name="currentRuleInUse" value="${ruleId}" id="currentRuleInUse"/>
    <div class="containerForLifeCycle" id="${ruleId}individualChartContainer${childEntity?.id}">
</g:if>
    <g:if test="${showClassificationWarning}">
        <div class="clearfix"></div>
        <div class="alert">
            <button type="button" class="close" data-dismiss="alert">×</button>
            <strong><g:message code="grouping_data_unclassified"/> (${countOfUndefined})</strong>
        </div>
    </g:if>
    <g:if test="${upstreamDbWarning}">
        <div class="clearfix"></div>
        <div class="alert">
                <button type="button" class="close" data-dismiss="alert">×</button>
                <strong>${upstreamDbWarning}</strong>
        </div>
    </g:if>
    <div id ="${ruleId}wrapper" style="position: relative; float:left; padding-left: 10px; padding-right: 10px; padding-top: 10px; margin-bottom: 30px; width: 100%;height: 600px" >
        <canvas id="${ruleId}chartForLifeCycle${childEntity?.id}"></canvas>
    </div>
<g:if test="${!individualCategoryBreakdown}">
    </div>
</g:if>
<div class="clearfix"></div>
    <script type="text/javascript">
        $(function () {
            $('#${ruleId}BreakdownSwapper').dropdown();
        })

        var title = "${comingFromCompare ? childEntity?.name + ': ' : ''}${calculationRuleName} (${shortName}) ${groupedBy ? g.message(code: 'results_grouped_by') + ' ' + groupedBy : ''} ${message(code: 'results_breakdown_title')}"
        var allBreakdownChart = new Highcharts.chart({
            chart: {
                renderTo: '${ruleId}wrapper',
                type: 'column'
            },
            lang: {
                noData: "${message(code: 'graph_no_data_displayed')}"
            },
            title: {
                text: title,
                style: {
                    color: '#333333',
                    fontWeight: 'bold',
                    fontSize: '20px',
                    fontFamily: 'Arial'
                },
                useHTML: true
            },
            series: ${graphDatasets},
            plotOptions: {
                column: {
                    stacking: 'normal',
                }
            },
            yAxis: {
                title: {
                    enabled: true,
                    text: "${unit}",
                    useHTML: true
                },
                reversedStacks: false,
            },
            xAxis: {
                categories: ${graphLabels},
                useHTML: true
            },
            tooltip: {
                valueSuffix: '${unit}',
                valueDecimals: 2,
                shared: true,
                useHTML: true
            },
            legend: {
                align: 'center',
                verticalAlign: 'top',
                itemStyle: {
                    fontWeight: 'normal',
                    fontFamily: 'Arial'
                },
                useHTML: true
            },
            credits: {
                enabled: false
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
    </script>
