<g:if test="${indicatorBenchmark && score != null}">
    <div>
        <p style="text-align: center;" class="bold" id="indicatorBenchmarkModalSubHeading">Performance metric ${indicatorName}${denominatorName ? " - ${denominatorName}" : ""} </p>
    <g:if test="${indicator.benchmarkSettings || isIndicatorBenchmarkLocked}">
        <div class="dropdown btn-group text-center" style="margin: 5px;" >
       <p class="bold">${indicatorBenchmark.benchmarkName}</p>
        </div>
    </g:if><g:else>
        <div class="dropdown btn-group text-center" style="margin: 5px;" >
            <a href="javascript:" class="btn btn-primary dropdown-toggle" id="benchmarkCarbonSelector1" data-toggle="dropdown">${indicatorBenchmark.benchmarkName} <span class="caret"></span></a>
            <ul id="benchMarkList1"class="dropdown-menu dropdown-menu-center dropdownOverlay" ><g:each in="${allBenchmarks}" var="benchMarkObj"><g:if test="${!indicatorBenchmark.benchmarkId?.equals(benchMarkObj.benchmarkId)}">
                <li style="font-size:12px !important; text-align: left"><a href="javascript:" onclick="openIndicatorBenchmarks('${indicatorId}', '${childEntityId}', '${parentEntityType}', '${designName}', '${parentName}', '${countryForPrioritization}', '${benchMarkObj.benchmarkId}')">${benchMarkObj.benchmarkName}</a></li>
            </g:if>
            </g:each></ul>
        </div>
    </g:else>
        <div class="text-center" style="margin: 5px;">
            <span style="text-align: center;" class="bold"><a target='_blank' rel='noopener noreferrer' href="https://www.oneclicklca.com/construction/carbonheroes/"><g:message code="about_benchmark"/></a></span><br/>
            <p id="benchmarkSourceInformation"></p>
        </div>
        <g:set var="formattedScore" value="${score?.round()}"/>
        <g:set var="formattedMin" value="${(indicatorBenchmark.indexMin * planetaryBenchmarkMargin).round()}"/>
        <g:set var="formattedA" value="${(indicatorBenchmark.indexA * planetaryBenchmarkMargin).round()}"/>
        <g:set var="formattedB" value="${(indicatorBenchmark.indexB * planetaryBenchmarkMargin).round()}"/>
        <g:set var="formattedC" value="${(indicatorBenchmark.indexC * planetaryBenchmarkMargin).round()}"/>
        <g:set var="formattedD" value="${(indicatorBenchmark.indexD * planetaryBenchmarkMargin).round()}"/>
        <g:set var="formattedE" value="${(indicatorBenchmark.indexE * planetaryBenchmarkMargin).round()}"/>
        <g:set var="formattedF" value="${(indicatorBenchmark.indexF * planetaryBenchmarkMargin).round()}"/>
        <g:set var="formattedG" value="${(indicatorBenchmark.indexG * planetaryBenchmarkMargin).round()}"/>
        <table border="2" style="margin-left: 30%;" id="benchmarkGraphTable2">
            <tr>
                <th>${heading}</th><th width="75px;">kg CO<sub>2</sub>e/m<sup>2</sup></th>
            </tr>
            <tr>
                <td>
                    <div id="colorbars">
                        <div class="epc-container epc-color-a" style="width: 110px;"><span style="color: #fff;" class="epc-container-number">(< ${formattedA})</span><span class="epc-container-letter">A</span></div>
                        <div class="epc-container epc-color-b" style="width: 135px;"><span style="color: #fff;" class="epc-container-number">(${formattedA}-${formattedB})</span><span class="epc-container-letter">B</span></div>
                        <div class="epc-container epc-color-c" style="width: 165px;"><span class="epc-container-number">(${formattedB}-${formattedC})</span><span class="epc-container-letter">C</span></div>
                        <div class="epc-container epc-color-d" style="width: 200px;"><span class="epc-container-number">(${formattedC}-${formattedD})</span><span class="epc-container-letter">D</span></div>
                        <div class="epc-container epc-color-e" style="width: 238px;"><span class="epc-container-number">(${formattedD}-${formattedE})</span><span class="epc-container-letter">E</span></div>
                        <div class="epc-container epc-color-f" style="width: 282px;"><span class="epc-container-number">(${formattedE}-${formattedF})</span><span class="epc-container-letter">F</span></div>
                        <div class="epc-container epc-color-g" style="width: 322px;"><span class="epc-container-number">(> ${formattedF})</span><span class="epc-container-letter">G</span></div>
                    </div>
                </td>
                <td style="text-align: center; align-content: center;">
                    <div class="epc-container-arrow ${(score < formattedA && score >= formattedMin) || score < formattedMin ? 'arrow-left epc-color-a' : ''}">${(score < formattedA && score >= formattedMin) || score < formattedMin ? formattedScore : ''}</div>
                    <div class="epc-container-arrow ${score < formattedB && score >= formattedA ? 'arrow-left epc-color-b' : ''}">${score < formattedB && score >= formattedA ? formattedScore : ''}</div>
                    <div class="epc-container-arrow ${score < formattedC && score >= formattedB ? 'arrow-left epc-color-c' : ''}">${score < formattedC && score >= formattedB ? formattedScore : ''}</div>
                    <div class="epc-container-arrow ${score < formattedD && score >= formattedC ? 'arrow-left epc-color-d' : ''}">${score < formattedD && score >= formattedC ? formattedScore : ''}</div>
                    <div class="epc-container-arrow ${score < formattedE && score >= formattedD ? 'arrow-left epc-color-e' : ''}">${score < formattedE && score >= formattedD ? formattedScore : ''}</div>
                    <div class="epc-container-arrow ${score < formattedF && score >= formattedE ? 'arrow-left epc-color-f' : ''}">${score < formattedF && score >= formattedE ? formattedScore : ''}</div>
                    <div class="epc-container-arrow ${(score < formattedG && score >= formattedF) || score > formattedG ? 'arrow-left epc-color-g' : ''}">${(score < formattedG && score >= formattedF) || score > formattedG ? formattedScore : ''}</div>
                </td>
            </tr>
        </table>
        <img id="benchmarkWatermarked" src="/app/assets/img/watermark.png" class="hidden">
    </div>
</g:if>
<g:else>
    <p style="text-align: center;" class="bold" id="indicatorBenchmarkModalSubHeading">${missingDataWarning}</p>
</g:else>
<div class="btn-group text-center" style="margin: 5px" >
    <a href="javascript:" id="downloadBenchmarkGraph2"> <i class="fa fa-download"></i> <g:message code="download_chart"/> </a>
</div>
<script>
    $(document).ready(function () {
        $('.dropdown-toggle').dropdown();
        var mapForPopUptable = {
            "${message(code: 'carbon_hero.benchmarkSourceInformation')}" : "${indicatorBenchmark.benchmarkSourceInformation}",
            "${message(code: 'carbon_hero.sampleSize')}" : "${indicatorBenchmark.sampleSize}",
            "${message(code: 'entity.show.task.description')}" : "${indicatorBenchmark.benchmarkDescription}",
            "${message(code: 'more_info')}" : "${indicatorBenchmark.externalLink ? '<a href=\''+indicatorBenchmark.externalLink+'\' target=\'_blank\'>'+message(code: "lcaChecker_link")+'</a>' : ''}",
        };
        renderTablePopOverWithMap(mapForPopUptable, "benchmarkSourceInformation", true)
        var button =$("#downloadBenchmarkGraph2");
        var targetTable = $("#benchmarkGraphTable2");
        html2canvas(targetTable[0], {scale: 2}).then(function(canvas){
            var image = document.getElementById("benchmarkWatermarked2");
            var ctx = canvas.getContext("2d");

            ctx.drawImage(image, 250, 50, 500,300)
            var imageData = canvas.toDataURL("image/png");
            var newData = imageData.replace(/^data:image\/png/, "data:application/octet-stream");
            $(button).attr("download", '${indicatorBenchmark.benchmarkName}.png').attr("href", newData);
    })
    });
</script>