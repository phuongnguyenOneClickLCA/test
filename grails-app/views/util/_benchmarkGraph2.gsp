<g:if test="${indicatorBenchmark && score != null}">
    <div class="text-center">
        <a href="javascript:" class="bold" style="margin-left:30px;padding-top: 20px;" onclick="openIndicatorBenchmarks('${indicator.indicatorId}', '${childEntityId}', '${parentEntityType}', '${designName}', '${parentName}', '${countryForPrioritization}', '${benchmarkId}')"><g:message code="mainpage_benchmark_bar_chart.heading"/></a>
        <span id="long" class="fa fa-question-circle longcontent" style="padding-left: 5px" rel="popover" data-trigger="hover" data-content="<g:message code="benchmark_graph.extra_info"/>"></span>
        <g:set var="formattedScore" value="${score?.round()}"/>
        <g:set var="formattedMin" value="${(indicatorBenchmark.indexMin * planetaryBenchmarkMargin).round()}"/>
        <g:set var="formattedA" value="${(indicatorBenchmark.indexA * planetaryBenchmarkMargin).round()}"/>
        <g:set var="formattedB" value="${(indicatorBenchmark.indexB * planetaryBenchmarkMargin).round()}"/>
        <g:set var="formattedC" value="${(indicatorBenchmark.indexC * planetaryBenchmarkMargin).round()}"/>
        <g:set var="formattedD" value="${(indicatorBenchmark.indexD * planetaryBenchmarkMargin).round()}"/>
        <g:set var="formattedE" value="${(indicatorBenchmark.indexE * planetaryBenchmarkMargin).round()}"/>
        <g:set var="formattedF" value="${(indicatorBenchmark.indexF * planetaryBenchmarkMargin).round()}"/>
        <g:set var="formattedG" value="${(indicatorBenchmark.indexG * planetaryBenchmarkMargin).round()}"/>
        <table border="1" style="margin-left: 10px;border: 1px solid grey" id="benchmarkGraphTable">
            <tr>
                <th>${heading}</th><th width="70px;">kg CO<sub>2</sub>e/m<sup>2</sup></th>
            </tr>
            <tr>
                <td>
                    <div id="colorbars-small">
                        <div class="epc-container-small epc-color-a" style="width: 75px;"><span style="color: #fff;" class="epc-container-number-small">(< ${formattedA})</span><span class="epc-container-letter-small">A</span></div>
                        <div class="epc-container-small epc-color-b" style="width: 105px;"><span style="color: #fff;" class="epc-container-number-small">(${formattedA}-${formattedB})</span><span class="epc-container-letter-small">B</span></div>
                        <div class="epc-container-small epc-color-c" style="width: 135px;"><span class="epc-container-number-small">(${formattedB}-${formattedC})</span><span class="epc-container-letter-small">C</span></div>
                        <div class="epc-container-small epc-color-d" style="width: 160px;"><span class="epc-container-number-small">(${formattedC}-${formattedD})</span><span class="epc-container-letter-small">D</span></div>
                        <div class="epc-container-small epc-color-e" style="width: 195px;"><span class="epc-container-number-small">(${formattedD}-${formattedE})</span><span class="epc-container-letter-small">E</span></div>
                        <div class="epc-container-small epc-color-f" style="width: 225px;"><span class="epc-container-number-small">(${formattedE}-${formattedF})</span><span class="epc-container-letter-small">F</span></div>
                        <div class="epc-container-small epc-color-g" style="width: 255px;"><span class="epc-container-number-small">(> ${formattedF})</span><span class="epc-container-letter-small">G</span></div>
                    </div>
                </td>
                <td style="text-align: center; align-content: center;">
                    <div class="epc-container-arrow-small ${(score < formattedA && score >= formattedMin) || score < formattedMin ? 'arrow-left-small epc-color-a' : ''}"><span style="color: #fff !important;" >${(score < formattedA && score >= formattedMin) || score < formattedMin ? formattedScore : ''}</span></div>
                    <div class="epc-container-arrow-small ${score < formattedB && score >= formattedA ? 'arrow-left-small epc-color-b' : ''}"><span style="color: #fff !important;" >${score < formattedB && score >= formattedA ? formattedScore : ''}</span></div>
                    <div class="epc-container-arrow-small ${score < formattedC && score >= formattedB ? 'arrow-left-small epc-color-c' : ''}">${score < formattedC && score >= formattedB ? formattedScore : ''}</div>
                    <div class="epc-container-arrow-small ${score < formattedD && score >= formattedC ? 'arrow-left-small epc-color-d' : ''}">${score < formattedD && score >= formattedC ? formattedScore : ''}</div>
                    <div class="epc-container-arrow-small ${score < formattedE && score >= formattedD ? 'arrow-left-small epc-color-e' : ''}">${score < formattedE && score >= formattedD ? formattedScore : ''}</div>
                    <div class="epc-container-arrow-small ${score < formattedF && score >= formattedE ? 'arrow-left-small epc-color-f' : ''}">${score < formattedF && score >= formattedE ? formattedScore : ''}</div>
                    <div class="epc-container-arrow-small ${(score < formattedG && score >= formattedF) || score > formattedG ? 'arrow-left-small epc-color-g' : ''}">${(score < formattedG && score >= formattedF) || score > formattedG ? formattedScore : ''}</div>
                </td>
            </tr>
        </table>
        <img id="benchmarkWatermarked2" src="/app/assets/img/watermark.png" class="hidden">
    </div>
    <g:if test="${indicator.benchmarkSettings || isIndicatorBenchmarkLocked}">
        <div class="text-center">
            <p class="bold"><span data-toggle="popover" >${indicatorBenchmark.benchmarkName.take(45)}</span> <i id="benchmarkSourceInfo" rel="popover" class="fa fa-question-circle" style="cursor: pointer"></i></p>
        </div>
    </g:if>
    <g:else>
        <div class="btn-group text-center">
            <a href="javascript:" style="margin-right: 10px !important;" class="dropdown-toggle" id="benchmarkCarbonSelector1" data-toggle="dropdown"><span data-toggle="popover" >${indicatorBenchmark.benchmarkName.take(45)} <span class="caret-middle"></span></span></a><i id="benchmarkSourceInfo" rel="popover" class="fa fa-question-circle" style="cursor: pointer"></i>
            <ul id="benchMarkList1"class="dropdown-menu dropdown-menu-center" style="width:250px"><g:each in="${allBenchmarks}" var="benchMarkObj"><g:if test="${!indicatorBenchmark.benchmarkId?.equals(benchMarkObj.benchmarkId)}">
                <li style="font-size:12px !important; white-space: pre !important; text-align: left !important;"><a style="white-space: normal !important;" href="javascript:" onclick="appendIndicatorBenchmark('${indicatorId}', '${childEntityId}', '${parentEntityType}', '${designName}', '${parentName}', '${countryForPrioritization}', '${benchMarkObj.benchmarkId}')">${benchMarkObj.benchmarkName}</a></li>
            </g:if>
            </g:each> </ul>
            <br/>
            <a href="javascript:" id="downloadBenchmarkGraph" onclick="renderAndDownloadCanvas()"> <i class="fa fa-download"></i> <g:message code="download_chart"/> </a>
        </div>
</g:else>


    <script>
        $(document).ready(function () {
            var popOverSettings = {
                placement: 'bottom',
                container: 'body',
                html: true,
                template: '<div class="popover popover-fade" style=" display: block; max-width: 180px;"><div class="arrow"></div><div class="popover-content"></div></div>'
            };
            $(".longcontent[rel=popover]").popover(popOverSettings);
            var mapForPopUptable = {
                "${message(code: 'carbon_hero.benchmarkSourceInformation')}" : "${indicatorBenchmark.benchmarkSourceInformation}",
                "${message(code: 'carbon_hero.sampleSize')}" : "${indicatorBenchmark.sampleSize}",
                "${message(code: 'entity.show.task.description')}" : "${indicatorBenchmark.benchmarkDescription}",
                "${message(code: 'more_info')}" : "${indicatorBenchmark.externalLink ? '<a href=\''+indicatorBenchmark.externalLink+'\' target=\'_blank\'>'+message(code: "lcaChecker_link")+'</a>' : ''}",
            }
            renderTablePopOverWithMap(mapForPopUptable, "benchmarkSourceInfo")
            $('.dropdown-toggle').dropdown();
            var indicatorName = "${indicatorBenchmark.benchmarkName}";
            if(indicatorName.length > 45){
                $('[data-toggle="popover"]').popover({
                    content:indicatorName,
                    placement: "top",
                    container: "body",
                    template: '<div class="popover"><div class="arrow"></div><div class="popover-content ribaHelp" style="font-size: 12px !important; font-weight: normal !important;"></div></div>',
                    trigger: 'hover',
                    html:'true'
                });

            }
        });

        function renderAndDownloadCanvas() {
            html2canvas($("#benchmarkGraphTable")[0], {scale: 3}).then(function (canvas) {
                var image = document.getElementById("benchmarkWatermarked2");
                var ctx = canvas.getContext("2d");
                ctx.drawImage(image, 330, 50, 500,300);
                var imageData = canvas.toDataURL("image/png");
                var newData = imageData.replace(/^data:image\/png/, "data:application/octet-stream");
                $("#downloadBenchmarkGraph").attr("download", '${indicatorBenchmark.benchmarkName}.png').attr("href", newData);
                $("#downloadBenchmarkGraph").prop("onclick", null);
                var link = document.getElementById('downloadBenchmarkGraph');
                link.click();
            });
        }


    </script>
</g:if>
<g:else>
    <div class="bold entityPieChartWrap" >
        <p style="margin-left: 30px; padding-top: 20px;"><g:message code="no_benchmark_chart_found"/></p>
        <div style="width: 300px"><i class="fa fa-ban fa-align-center fa-fulldiv" aria-hidden="true"></i></div>
    </div>
</g:else>

