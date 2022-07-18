<%@ page import="org.apache.commons.lang.StringUtils" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <h1>Resources with resourceType: ${resourceType} (${resources.size()})</h1>
        <g:if test="${(showFailsConversion||showFailsConversionKg) && standardUnit}"><h1>Failing unit conversion to standardUnit: ${standardUnit}</h1></g:if>
        <g:if test="${requiredUnitFails}"><h1>Does not contain all requiredUnits: ${requiredUnits}</h1></g:if>
        <g:if test="${badUnitFails}"><h1>Contains avoidableUnits: ${avoidableUnits}</h1></g:if>
        <g:if test="${threshold}"><h1>Benchmark result is failing <g:if test="${"standardDeviation".equals(filterType)}">deviation</g:if><g:elseif test="${"changeInImpact".equals(filterType)}">change in impact</g:elseif> threshold of ${threshold}</h1></g:if>
    </div>
</div>
<div class="container section">

    <g:if test="${resources}">

        <g:if test="${!showFailsConversion && !showFailsConversionKg}">
            <p>
            <div style="float: left; margin-bottom: 10px;">
                Show by dataProperties:
                <select name="dataProperty" id="dataProperty" style="width: 150px;" onchange="showResourcesByDataProperty()">
                    <option>Show all</option>
                    <g:each in="${dataProperties.unique().sort()}">
                        <g:if test="${!it.isEmpty() && it != "-"}">
                            <option value="${it}">${it}</option>
                        </g:if>
                    </g:each>
                </select>
            </div>

            <div style="float: left; margin-left: 15px; margin-bottom: 10px;">
                Show by unitForData:
                <select name="unitForData" id="unitForData" style="width: 150px;" onchange="showResourcesByUnitForData()">
                    <option>Show all</option>
                    <g:each in="${unitsForData.unique().sort()}">
                        <option value="${it}">${it}</option>
                    </g:each>
                </select>
            </div>
            </p>
        </g:if>

        <table class="resource table table-striped table-condensed" id="resulttable">
            <tbody id="resourceList">
            <tr id="resourceListHeading">
                <th style="display:none">dataproperty</th><th>nameEN</th><th>ResourceId</th><th>ProfileId</th><th>unitForData</th><g:if test="${filterType}"><th>Standard deviations</th></g:if><th>Combined units</th><th>impactNonLinear</th><th>staticFullName</th><th>upstreamDB</th><th>impactGWP100_kgCO2e</th><th>impactODP_kgCFC11e</th><th>impactAP_kgSO2e</th><th>impactEP_kgPO4e</th><th>impactPOCP_kgEthenee</th>
                <th>impactADPElements_kgSbe</th><th>impactADPFossilFuels_MJ</th><th>traciGWP_kgCO2e</th><th>traciODP_kgCFC11e</th><th>traciAP_kgSO2e</th><th>traciEP_kgNe</th><th>traciPOCP_kgO3e</th>
                <th>traciNRPE_MJ</th><th>renewablesUsedAsEnergy_MJ</th><th>renewablesUsedAsMaterial_MJ</th><th>nonRenewablesUsedAsEnergy_MJ</th><th>nonRenewablesUsedAsMaterial_MJ</th><th>recyclingMaterialUse_kg</th><th>renewableRecylingFuelUse_MJ</th>
                <th>nonRenewableRecylingFuelUse_MJ</th><th>cleanWaterNetUse_m3</th><th>wasteHazardous_kg</th><th>wasteNonHazardous_kg</th><th>wasteRadioactive_kg</th><th>Import file</th><g:if test="${threshold}"><th>Failing benchmark</th></g:if>
            </tr><%--
        --%><g:each in="${resources}" var="resource"><%--
        --%><tr><td style="display:none">${resource.dataProperties}</td><td>${resource.nameEN} <sec:ifAnyGranted roles="ROLE_DATA_MGR"><a href="javascript:" onclick="window.open('${createLink(action: "showData", controller: "import", params: [resourceUUID: resource.id])}', '_blank', 'width=1024, height=768, scrollbars=1');"><i class="fas fa-search-plus"></i></a></sec:ifAnyGranted></td>
                <td>${resource.resourceId}</td><td>${resource.profileId}</td><td>${resource.unitForData}</td>
                <g:if test="${filterType}">
                    <td>
                    <g:if test="${'standardDeviation'.equals(filterType)}">
                        <g:each in="${resource.benchmark.deviations}" var="deviation">
                            <b>${benchmarkNames.get(deviation.key)}:</b> <opt:formatNumber number="${deviation.value}"/><br/>
                        </g:each>
                    </g:if>
                    <g:else>
                        <g:each in="${resource.benchmark.factorDeviations}" var="deviation">
                            <b>${benchmarkNames.get(deviation.key)}:</b> <opt:formatNumber number="${deviation.value}"/><br/>
                        </g:each>
                    </g:else>
                    </td>
                </g:if>
                <td>${resource.combinedUnits}</td><td>${resource.impactNonLinear != null ? resource.impactNonLinear ? "true" : "false" : "null"}</td><td>${resource.staticFullName}</td><td>${resource.upstreamDB}</td><td>${resource.impactGWP100_kgCO2e}</td>
                <td>${resource.impactODP_kgCFC11e}</td><td>${resource.impactAP_kgSO2e}</td><td>${resource.impactEP_kgPO4e}</td><td>${resource.impactPOCP_kgEthenee}</td>
                <td>${resource.impactADPElements_kgSbe}</td><td>${resource.impactADPFossilFuels_MJ}</td><td>${resource.traciGWP_kgCO2e}</td><td>${resource.traciODP_kgCFC11e}</td>
                <td>${resource.traciAP_kgSO2e}</td><td>${resource.traciEP_kgNe}</td><td>${resource.traciPOCP_kgO3e}</td><td>${resource.traciNRPE_MJ}</td>
                <td>${resource.renewablesUsedAsEnergy_MJ}</td><td>${resource.renewablesUsedAsMaterial_MJ}</td><td>${resource.nonRenewablesUsedAsEnergy_MJ}</td><td>${resource.nonRenewablesUsedAsMaterial_MJ}</td>
                <td>${resource.recyclingMaterialUse_kg}</td><td>${resource.renewableRecylingFuelUse_MJ}</td><td>${resource.nonRenewableRecylingFuelUse_MJ}</td><td>${resource.cleanWaterNetUse_m3}</td>
                <td>${resource.wasteHazardous_kg}</td><td>${resource.wasteNonHazardous_kg}</td><td>${resource.wasteRadioactive_kg}</td><td>${resource.importFile}</td>
                <g:if test="${threshold}">
                <td>
                    <g:each in="${benchmarks}" var="benchmark">
                        <g:if test="${resource.benchmark?.deviations?.get(benchmark) != null && resource.benchmark.deviations.get(benchmark) > threshold}">
                            <b>${benchmark}:</b> <g:formatNumber number="${resource.benchmark.deviations.get(benchmark)}" format="0.##" /><br/>
                        </g:if>
                    </g:each>
                </td>
                </g:if>
            </tr><%--
        --%></g:each><%--
    --%></tbody></table>
    </g:if>
</div>

<script type="text/javascript">
    $(function () {
        $("#unitForData").select2({
            minimumResultsForSearch: Infinity
        }).maximizeSelect2Height();
        $("#dataProperty").select2({
            minimumResultsForSearch: Infinity
        }).maximizeSelect2Height();
    });

    function showResourcesByDataProperty() {
        var filterRawDP = document.getElementById("dataProperty");
        var filterDP = filterRawDP.value.replace('[[]]','');

        if (filterDP && filterDP != "Show all") {
            $("#resourceList").find("tr").remove();

            $.ajax({async: false, type: 'POST',
                data: 'dataproperty=' + filterDP,
                url: '/app/sec/resourceType/resourcesByDataProperty',
                success: function (data, textStatus) {
                    if (data.output) {
                        $('#resourceList').append(data.output);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {

                }
            });
        } else {
            $("#resourceList").find("tr").remove();

            $.ajax({async: false, type: 'POST',
                url: '/app/sec/resourceType/resourcesFromSession',
                success: function (data, textStatus) {
                    if (data.output) {
                        $('#resourceList').append(data.output);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {

                }
            });
        }
    }

    function showResourcesByUnitForData() {
        var filterRaw = document.getElementById("unitForData");
        var filter = filterRaw.value;
        var table = document.getElementById("resulttable");
        var tr = table.getElementsByTagName("tr");
        for (i = 0; i < tr.length; i++) {
            var td = tr[i].getElementsByTagName("td")[4];

            if (td) {
                var resourceUnitForData = td.innerHTML;

                if (filter == "Show all") {
                    tr[i].style.display = "";
                } else if (filter == "Empty field" && resourceUnitForData == "") {
                    tr[i].style.display = "";
                } else {

                    if (resourceUnitForData == filter) {
                        tr[i].style.display = "";
                    } else {
                        tr[i].style.display = "none";
                    }
                }
            }
        }
    }
</script>

</body>
</html>