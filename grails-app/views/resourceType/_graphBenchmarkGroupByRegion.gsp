<g:set var="meanAndMedia" value="${(Map) valueBenchmarkByRegion?.get(benchmarkToShow)}"/>
<g:set var="tableHeading" value="${typeOfRegion.collect {it.value}.findAll({it})}"/>
<g:set var="nameColumn" value="${meanAndMedia.collect {it?.value?.collect({it?.key})}.flatten().unique()}"/>
<div style="display: block" >
    <label class="pull-right"><strong><g:message code="select_threshold"/> :</strong>
    <select id="changeCutOff" onchange="adjustCutOff(this)">
        <option value="0" ${cutOffPercentage != null && cutOffPercentage < 5 ? 'selected':''}>No cut off</option>
        <option value="5" ${cutOffPercentage == null || cutOffPercentage == 5 ? 'selected':''}>Cut off 5%</option>
        <option value="10" ${cutOffPercentage !=null && cutOffPercentage > 5 ? 'selected':''}>Cut off 10%</option>
    </select>
    </label>
</div>
<div class="clearfix"></div>
<div class="padding-y" style="display: block;">
    <div class="loading-spinner hidden" style="margin: 0 auto; width: 100%;" id="loadingSpinnerGraph">
        <div class="image">
            <svg class="loadingSVG" version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg"  x="0px" y="0px"
                 width="270px" height="270px" viewBox="0 0 32 32" style="enable-background:new 0 0 32 32;" xml:space="preserve">
                <g>
                    <path class="arrowSpinningSvg mm-o-syncing-queue" d="M24.5,10.4L22.2,11l5,5L29,9.2l-2.4,0.7C24.4,6.1,20.4,3.8,16,3.8c-5.4,0-10.1,3.4-11.7,8.6
	c-0.2,0.5,0.1,1.1,0.7,1.3c0.1,0,0.2,0,0.3,0c0.4,0,0.8-0.3,1-0.7c1.3-4.3,5.2-7.2,9.7-7.2C19.5,5.8,22.7,7.5,24.5,10.4z"/>
                </g>
            </svg>
            <p class="working"><g:message code="loading.working"/>.</p>
        </div>

    </div>
    <div id="graphForBenchmark"></div>
    <div>
        <div class="btn-group">
            <a class="btn btn-primary pull-right" onclick="renderResourceTableWithBenchmark('${subTypeId}',null,'${benchmarkToShow}', '${benchmarkUnit + " /" +subTypeUnit}',null,null,'resourceListByBenchmark')" >${message(code: "show.all")}</a>
        </div>



        <table class="table table-condensed table-striped" id="benchmarkTableOverview">
            <thead>
            <tr>
                <th><g:message code="factor"/></th>
                <th><g:message code="entity.show.unit"/> </th>
                <g:each in="${tableHeading}" var="header">
                    <th>${header}</th>
                </g:each>
            </tr>
            </thead>
            <tbody>
            <g:each in="${nameColumn}" var="resultType">
                <tr>
                    <td>${message(code:resultType)}</td>
                    <td>${resultType == 'sampleSize' ? message(code:'resource.product')?.toLowerCase() : benchmarkUnit + "/" + subTypeUnit}</td>
                    <g:each in="${(Map) meanAndMedia}" var="region">
                        <g:if test="${resultType != 'sampleSize' && localizedNameForRegion?.contains(region?.key)}">
                            <g:set var="resultForRegion" value="${region?.value?.get(resultType)?.round(3) ?: 0}"/>
                            <td>
                                ${resultForRegion} - <a href="javascript:;" onclick="setBenchmarkProductCount('${region.key}', '${resultType}', '${resultForRegion}', this); renderResourceTableWithBenchmark('${subTypeId}',${resultForRegion}, '${benchmarkToShow}', '${benchmarkUnit + " /" +subTypeUnit}',null,null,'resourceListByBenchmark')">${g.message(code: "use_as_benchmark")}</a><br/>
                            </td>
                        </g:if>
                        <g:else>
                            <td>${region?.value?.get(resultType) ?: 0}</td>
                        </g:else>
                    </g:each>
                </tr>
            </g:each>
            </tbody>
            <tr id="benchmarkStata" class="bold"></tr>

        </table>
        <div>
            <div class="loading-spinner hidden" style="margin: 0 auto; width: 100%;" id="loadingSpinnerBenchmark">
                <div class="image">
                    <svg class="loadingSVG" version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg"  x="0px" y="0px"
                         width="270px" height="270px" viewBox="0 0 32 32" style="enable-background:new 0 0 32 32;" xml:space="preserve">
                        <g>
                            <path class="arrowSpinningSvg mm-o-syncing-queue" d="M24.5,10.4L22.2,11l5,5L29,9.2l-2.4,0.7C24.4,6.1,20.4,3.8,16,3.8c-5.4,0-10.1,3.4-11.7,8.6
	c-0.2,0.5,0.1,1.1,0.7,1.3c0.1,0,0.2,0,0.3,0c0.4,0,0.8-0.3,1-0.7c1.3-4.3,5.2-7.2,9.7-7.2C19.5,5.8,22.7,7.5,24.5,10.4z"/>
                        </g>
                    </svg>
                    <p class="working"><g:message code="loading.working"/>.</p>
                </div>

            </div>
            <div id="resourceListByBenchmark"></div>
        </div>


    </div>
</div>

<script type="text/javascript">
    var dataset
    $(function () {
        var noteForCountry = "${sourceForCountry}"
        if(noteForCountry.length > 0){
            $("#noteForCountry").text(noteForCountry)
        }
    })
    <g:set var="graphFor1Benchmark" value="${(Map)valueForGraph.get(benchmarkToShow)}" />
    var imageURL = new URL("/app/assets/img/watermarkTransparent.png", document.baseURI).href
    var benchmarkChart = new Highcharts.chart("graphForBenchmark", {
        chart: {
            events: {
                load: function () {
                    if(this.options.chart.forExport) {
                        this.renderer.image(imageURL, 700, 0, 300, 187).add();
                    }


                }

            }
        },
        colors: ['#EF1C39','#F78521', '#FFCC00','#19B059','#00845A',"#ffffff"],
        series: [
            <g:each in="${(Map)graphFor1Benchmark}" var="dataset">
            <g:set var="dataArray" value="${dataset?.value?.collect({it?.value})}" />
            {
                showInLegend: ${dataset.key != "min"},
                name: '${dataset.key}',
                type: 'column',
                data: ${dataArray}
            },
            </g:each>

        ],
        plotOptions: {
            column: {
                grouping: false,
            }
        },
        lang: {
            noData: "${message(code: 'graph_no_data_displayed')}"
        },
        title: {
            text: '',
            style: {
                fontSize: '1px',
            }
        },
        subtitle: {
            text: '${g.message(code: "subtitle_benchmark_material_graph_help")}'
        },
        credits: {
            enabled: false
        },
        xAxis: {
            title: {
                enabled: false
            },
            categories: ${localizedNameForRegionAsJSON}
        },
        yAxis: {
            title: {
                enabled: false,
            },
            <g:if test="${userResource}">
                plotLines: [{
                    label: {
                        text: "${g.message(code: 'your_material')}: ${userResource?.localizedName} - ${!userResource?.benchmark?.values?.get(benchmarkToShow).equalsIgnoreCase("NOT_CALCULABLE")?userResource?.benchmark?.values?.get(benchmarkToShow)?.toDouble()?.round(2) : "NOT CALCULABLE"} ${subTypeUnit} - ${benchmarkName}",
                    },
                    color: 'red',
                    value: '${userResource?.benchmark?.values?.get(benchmarkToShow)}', // Insert your average here
                    width: '1',
                    padding: 10,
                    zIndex: 5 // To not get stuck below the regular plot lines
                }]
            </g:if>

        },
        tooltip: {
            valueDecimals: 2,
            shared: true,
            valueSuffix: '${benchmarkUnit + " /" +subTypeUnit}',
        },
        legend: {
            align: 'center',
            verticalAlign: 'top',
            floating: false,
            itemStyle: {
                fontWeight: 'normal',
                fontFamily: 'Arial'
            }
            },
        exporting: {
            enabled: true,
            sourceWidth: 1000,
            sourceHeight: 500,
            buttons: {
                contextButton: {
                    menuItems: [
                        'downloadPNG',
                        'downloadJPEG',
                        'downloadSVG'
                    ]
                }
            },
            enableImages: true,
            chartOptions: {
                chart: {
                    events: {
                        load: function() {
                            var chart = this;
                            chart.renderer.image(imageURL, 700, 0, 300, 187).add().toFront();
                        }
                    }
                }
            }
        },
        responsive: {
            rules: [{
                condition: {
                    maxWidth: 500
                },
                chartOptions: {
                    legend: {
                        align: 'center',
                        verticalAlign: 'bottom',
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
    function setBenchmarkProductCount(area, valueType, value, cell){
        //create list of threshold for products number under each mark
        var thresholdList = [1, 0.8,0.7,0.6, 0.5]
        var objectList = ${regionsAndValueListByBenchmark ?  regionsAndValueListByBenchmark.get(benchmarkToShow) as grails.converters.JSON : 'null'} ;
        var regions = ${tableHeading ? tableHeading as grails.converters.JSON : null} ;
        var newTable = "<tr class='appendedData'><th colspan='4'><h2>${g.message(code: 'benchmark_set', args: [subTypeName])} "+value+" ${benchmarkUnitsubTypeUnit} ${benchmarkUnit + " /" +subTypeUnit}</h2></th><th></th><th></th></tr>"
        $("#benchmarkTableOverview").find("td").removeClass("highlighted-background")
        $(cell).parent().addClass("highlighted-background")
        for(var i = 0; i < thresholdList.length; i++){
            var percentageDiff = thresholdList[i] * 100 - 100
            var heading
            var threshold = value * thresholdList[i]

            if(percentageDiff == 0 ){
                heading = "${g.message(code: 'product_below_benchmark')}"
            } else {
                heading = "${g.message(code: 'product_below_percent_benchmark_a')} " + Math.round(percentageDiff)+ " ${g.message(code: 'product_below_percent_benchmark_b')}"

            }
            newTable += "<tr class='appendedData'><td>"+heading+" </td><td>${message(code:'resource.product').toLowerCase()}</td>"
            for(var y = 0; y < regions.length; y ++){
                var country = regions[y]
                var items = objectList[country]
                var itemPassed = filterAndReturnResourceId(items, threshold)
                var amountPassed = itemPassed.length

                if(amountPassed > 0 ){
                    newTable += "<td>"+amountPassed+" - <a href=\"javascript:;\" onclick=\"renderResourceTableWithBenchmark('${subTypeId}',"+threshold+",'${benchmarkToShow}', '${benchmarkUnit + " /" +subTypeUnit}','" +itemPassed+ "','"+country+"' ,'resourceListByBenchmark', this)\">${g.message(code: 'show_products')}</a></td>"
                } else {
                    newTable += "<td>"+amountPassed+"</td>"

                }
            }
            newTable += "</tr>"
        }
        $(".appendedData").remove()
        $(newTable).insertAfter("#benchmarkStata")
    }

    function filterAndReturnResourceId(objectList, threshold){
        var returnList = []
        for (var res in objectList){
            if(objectList[res] <= threshold ){
                returnList.push(res)
            }
        }
        return returnList
    }
    function adjustCutOff(element) {
        var cutOffpercentage = $(element).val()
        var showPercentages = "${showPercentages}";
        var queryString = 'resourceSubTypeId=' + "${subTypeId}" + '&showPercentages=' + showPercentages + '&resourceId=' + "${userResource?.resourceId}" + '&profileId=' + "${userResource?.profileId}" + '&projectCoutryId=' + "${countryForProject?.resourceId}" + '&stateIdOfProject=' + '${stateForProject?.resourceId}';
        queryString += "&benchmarkToShow=" + "${benchmarkToShow}" + "&threshold=" + cutOffpercentage;
        $.ajax({
            type: 'POST',
            data: queryString,
            url: '/app/sec/resourceType/graphBenchmarkGroupByRegion',
            beforeSend: function(){
                $("#loadingSpinnerGraph").removeClass("hidden");
                $("#graphForBenchmark").addClass("hidden");
            },
            success: function (data, textStatus) {
                if (data.output) {
                    $("#graphNew").empty().append(data.output)
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
            }
        });
    }
</script>