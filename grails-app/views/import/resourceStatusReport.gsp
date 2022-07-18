<%@ page import="com.bionova.optimi.core.domain.mongo.Resource" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <h1>Database status report - <g:formatDate date="${new Date()}" format="dd.MM.yyyy HH:mm"/> (${grails.util.Environment.current})</h1>
    </div>
</div>
<div class="container section">
    <g:set var="kpiReportService" bean="kpiReportService"/>
    <h2>1. Database KPIs</h2>
    <table class="resource" >
        <tbody>
        <g:if test="${databaseKPIs}">
            <g:each status="i" in="${databaseKPIs}" var="kpi">
                <tr><td><g:if test="${kpi.key == "totalPartial"}"><strong>${i+1}. ${kpiReportService.getResolveKpiDisplayName(kpi.key)}</strong></g:if><g:else>${i+1}. ${kpiReportService.getResolveKpiDisplayName(kpi.key)}</g:else></td>
                    <td>
                        <g:if test="${kpi.value}">
                            <g:if test="${kpi.key == "costStructures"}">
                                <g:link controller="costStructure" action="form" >${kpi.value}</g:link>
                            </g:if>
                            <g:elseif test="${["bionovaConstructions","otherConstructions"].contains(kpi.key)}">
                                <opt:link controller="construction" action="constructionsManagementPage">${kpi.value}</opt:link>
                            </g:elseif>
                            <g:else>
                                <g:if test="${kpi.key == "LCA-BMAT"}">
                                    <a href="javascript:" style="color: #6b9f00;" onclick="window.open('${createLink(action: 'resourceStatusList', params: [applicationId: "LCA", resourceGroup: "BMAT", noConstructions: true] )}', '_blank', 'width=1024, height=768, scrollbars=1');">${kpi.value}</a>
                                </g:if>
                                <g:elseif test="${kpi.key == "LCA-BTECH"}">
                                    <a href="javascript:" style="color: #6b9f00;" onclick="window.open('${createLink(action: 'resourceStatusList', params: [applicationId: "LCA", resourceGroup: "buildingTechnology", noConstructions: true] )}', '_blank', 'width=1024, height=768, scrollbars=1');">${kpi.value}</a>
                                </g:elseif>
                                <g:elseif test="${kpi.key == "LCA-OTHER"}">
                                    <a href="javascript:" style="color: #6b9f00;" onclick="window.open('${createLink(action: 'resourceStatusList', params: [applicationId: "LCA", skipResourceGroup: ["buildingTechnology", "BMAT"], noConstructions: true] )}', '_blank', 'width=1024, height=768, scrollbars=1');">${kpi.value}</a>
                                </g:elseif>
                                <g:elseif test="${kpi.key == "BMAT+BTECH"}">
                                    <a href="javascript:" style="color: #6b9f00;" onclick="window.open('${createLink(action: 'resourceStatusList', params: [applicationId: "LCA", resourceGroup: ["buildingTechnology", "BMAT"], noConstructions: true] )}', '_blank', 'width=1024, height=768, scrollbars=1');">${kpi.value}</a>
                                </g:elseif>
                                <g:elseif test="${kpi.key == "generic"}">
                                    <a href="javascript:" style="color: #6b9f00;" onclick="window.open('${createLink(action: 'resourceStatusList', params: [applicationId: "LCA", area: "LOCAL"] )}', '_blank', 'width=1024, height=768, scrollbars=1');">${kpi.value}</a>
                                </g:elseif>
                                <g:elseif test="${kpi.key.toString().startsWith("total")}">
                                    <g:if test="${kpi.key == "totalPartial"}">
                                        <strong>${kpi.value}</strong>
                                    </g:if>
                                    <g:else>
                                        ${kpi.value}
                                    </g:else>
                                </g:elseif>
                                <g:elseif test="${kpi.key == "private"}">
                                    <a href="javascript:" style="color: #6b9f00;" onclick="window.open('${createLink(action: 'resourceStatusList', params: [environmentDataSourceType: "private"] )}', '_blank', 'width=1024, height=768, scrollbars=1');">${kpi.value}</a>
                                </g:elseif>
                                <g:elseif test="${kpi.key == "inactive"}">
                                    <a href="javascript:" style="color: #6b9f00;" onclick="window.open('${createLink(action: 'resourceStatusList', params: [inactive: true] )}', '_blank', 'width=1024, height=768, scrollbars=1');">${kpi.value}</a>
                                </g:elseif>
                                <g:elseif test="${kpi.key == "staticDBs"}">
                                    <a href="javascript:" style="color: #6b9f00;" onclick="window.open('${createLink(action: 'resourceStatusList', params: [epdProgram: ["IMPACT","OKOBAUDAT","Quartz","KBOB","ZAG","Earthsure","FPInnovations"], noConstructions: true] )}', '_blank', 'width=1024, height=768, scrollbars=1');">${kpi.value}</a>
                                </g:elseif>
                                <g:else>
                                    <a href="javascript:" style="color: #6b9f00;" onclick="window.open('${createLink(action: 'resourceStatusList', params: [applicationId: kpi.key, noConstructions: true] )}', '_blank', 'width=1024, height=768, scrollbars=1');">${kpi.value}</a>
                                </g:else>
                            </g:else>
                        </g:if>
                        <g:else>
                            0
                        </g:else>
                    </td>
                </tr>
            </g:each>
        </g:if>
        </tbody>
    </table>

    <h2>2. Count of unique EPD numbers in active resources per EPD program for applicationId LCA</h2>
    <table class="resource">
        <thead>
            <tr>
                <th>epdProgram</th>
                <th>Total</th>
                <th>Unique</th>
            </tr>
        </thead>
        <tbody>
        <g:if test="${uniqueEPDForLCA}">
            <g:each in="${uniqueEPDForLCA}">
                <tr><td>${it.key}</td>
                    <td>
                        <g:if test="${it.value}">
                            <a href="javascript:;" style="color: #6b9f00;" onclick="window.open('${createLink(action: 'resourceStatusList',params: [applicationId: it.value[0].applicationId, epdProgram: it.value[0].epdProgram] )}', '_blank', 'width=1024, height=768, scrollbars=1');">${it.value.size()}</a>
                        </g:if>
                        <g:else>
                            0
                        </g:else>
                    </td>
                    <td>
                        <g:set var="dataContent" value="" />
                        <g:each in="${uniqueEPDNumberForLCA.get(it.key)}" var="epdNumber"><g:set var="dataContent" value="${dataContent + epdNumber + "<br />"}" /></g:each>
                        <a href="#" id="uniqueEpdPopover" data-toggle="popover" title="Unique epdNumbers" data-content="${dataContent}">${uniqueEPDNumberForLCA.get(it.key)?.size()}</a>
                    </td>
                </tr>
            </g:each>
        </g:if>
        </tbody>
    </table>

    <h2>3. Count of active resources per applicationid and unlinked</h2>
    <table class="resource" >
        <tbody>
            <g:if test="${resourcesPerApplication}">
                <g:each in="${resourcesPerApplication}">
                    <tr><td>${it.key}</td>
                        <td>
                            <g:if test="${it.value != null}">
                                <a href="javascript:;" style="color: #6b9f00;" onclick="window.open('${createLink(action: 'resourceStatusList', params: [applicationId: it.key] )}', '_blank', 'width=1024, height=768, scrollbars=1');">${it.value}</a>
                            </g:if>
                            <g:else>
                                0
                            </g:else>
                        </td>
                    </tr>
                </g:each>
            </g:if>
        </tbody>
    </table>

    <h2>4. Count of active resources per applicationId and unlinked, grouped by resource group</h2>
    <table class="resource">
        <tbody>
        <g:if test="${resourcesPerResourceGroupAndUnlinked}">
            <g:each in="${resourcesPerResourceGroupAndUnlinked}">
                <tr><td>${it.key}</td>
                    <td>
                        <table>
                            <g:each in="${it.value}" var="countByResourceGroup">
                                <g:if test="${countByResourceGroup.value}">
                                    <tr>
                                        <td>${countByResourceGroup.key}</td>
                                        <td>
                                            <a href="javascript:;" style="color: #6b9f00;" onclick="window.open('${createLink(action: 'resourceStatusList', params: [applicationId: it.key, resourceGroup: countByResourceGroup.key] )}', '_blank', 'width=1024, height=768, scrollbars=1');">${countByResourceGroup.value}</a>
                                        </td>
                                    </tr>
                                </g:if>
                            </g:each>
                        </table>
                    </td>
                </tr>
            </g:each>
        </g:if>
        </tbody>
    </table>
    <h2>5. Count of active LCA resources per country (areas) and unlinked. Can be under multiple areas</h2>
    <table class="resource">
        <tbody>
        <g:if test="${countOfResourcesByCountry}">
            <g:each in="${countOfResourcesByCountry}">
                <g:if test="${it.value}">
                    <tr><td>${it.key}</td>
                        <td>
                            <a href="javascript:" style="color: #6b9f00;" onclick="window.open('${createLink(action: 'resourceStatusList', params: [area: it.key] )}', '_blank', 'width=1024, height=768, scrollbars=1');">${it.value}</a>
                        </td>
                    </tr>
                </g:if>
            </g:each>
        </g:if>
        </tbody>
    </table>

    <h2>6. Count of active resources by dataproperties and unlinked</h2>
    <table class="resource">
        <tbody>
        <g:if test="${resourcesWithDataProperties}">
            <g:each in="${resourcesWithDataProperties}">
                <tr><td>${it.key}</td>
                    <td>
                        <g:if test="${it.value}">
                            <a href="javascript:;" style="color: #6b9f00;" onclick="window.open('${createLink(action: 'resourceStatusList', params: [dataProperties: it.key] )}', '_blank', 'width=1024, height=768, scrollbars=1');">${it.value.size()}</a>
                        </g:if>
                        <g:else>
                            0
                        </g:else>
                    </td>
                </tr>
            </g:each>
        </g:if>
        </tbody>
    </table>

    <h2>7. Count of active resources by enabledPurposes</h2>
    <table class="resource">
        <tbody>
        <g:if test="${resourcesWithEnabledPurposes}">
            <g:each in="${resourcesWithEnabledPurposes}">
                <tr><td>${it.key}</td>
                    <td>
                        <g:if test="${it.value}">
                            <a href="javascript:;" style="color: #6b9f00;" onclick="window.open('${createLink(action: 'resourceStatusList', params: [enabledPurposes: it.key] )}', '_blank', 'width=1024, height=768, scrollbars=1');">${it.value.size()}</a>
                        </g:if>
                        <g:else>
                            0
                        </g:else>
                    </td>
                </tr>
            </g:each>
        </g:if>
        </tbody>
    </table>

    <h2>8. Count of active resources by upstreamDB and unlinked for applicationId LCA</h2>
    <table class="resource">
        <tbody>
        <g:if test="${countOfResourcesByUpstreamDB}">
            <g:each in="${countOfResourcesByUpstreamDB}">
                <tr><td>${it.key}</td>
                    <td>
                        <g:if test="${it.value}">
                            <a href="javascript:;" style="color: #6b9f00;" onclick="window.open('${createLink(action: 'resourceStatusList', params: [upstreamDB: it.key] )}', '_blank', 'width=1024, height=768, scrollbars=1');">${it.value}</a>
                        </g:if>
                        <g:else>
                            0
                        </g:else>
                    </td>
                </tr>
            </g:each>
        </g:if>
        </tbody>
    </table>

    <h2>9. Active data by PCR <a href="javascript:" onclick="togglePCRTable()">(Show table)</a></h2>
    <div id="pcrTable" class="hidden">
        <table class="resource">
            <tbody>
            <g:if test="${databasePCRs}">
                <g:each in="${databasePCRs}">
                    <tr><td>${it.key}</td>
                        <td>
                            <g:if test="${it.value}">
                                <a href="javascript:" style="color: #6b9f00;" onclick="window.open('${createLink(action: 'resourceStatusList', params: [applicationId: "LCA", pcr: it.key] )}', '_blank', 'width=1024, height=768, scrollbars=1');">${it.value}</a>
                            </g:if>
                            <g:else>
                                0
                            </g:else>
                        </td>
                    </tr>
                </g:each>
            </g:if>
            </tbody>
        </table>
    </div>

    <h2>10. Unique import files for all resources in database</h2>
    <table class="resource">
        <tbody>
        <g:if test="${importFilesResourceAmounts}">
            <g:each in="${importFilesResourceAmounts}">
                <tr><td>${it.key}</td>
                    <td>
                        <g:if test="${it.value}">
                            <a href="javascript:" style="color: #6b9f00;" onclick="window.open('${createLink(action: 'resourceStatusList', params: [importFile: it.key] )}', '_blank', 'width=1024, height=768, scrollbars=1');">${it.value}</a>
                        </g:if>
                        <g:else>
                            0
                        </g:else>
                    </td>
                </tr>
            </g:each>
        </g:if>
        </tbody>
    </table>

    <h2>11. Search for newly added resources in given time period</h2>
    <div>
        <div class="controls">
            <table>
                <thead><tr><td><strong>Start date</strong></td><td></td><td><strong>End date</strong></td></tr></thead>
                <tbody>
                    <tr>
                        <td>
                            <g:textField name="startDate" id="startDate"
                                         value="${formatDate(date: new Date(), format: 'dd.MM.yyyy')}"
                                         class="input-xlarge datepicker" readonly="true"
                                         style="cursor: pointer;"/><span class="add-on"><i class="icon-calendar"></i></span>
                        </td>
                        <td> - </td>
                        <td>
                            <g:textField name="endDate" id="endDate"
                                         value="${formatDate(date: new Date(), format: 'dd.MM.yyyy')}"
                                         class="input-xlarge datepicker" readonly="true"
                                         style="cursor: pointer;"/><span class="add-on"><i class="icon-calendar"></i></span>
                        </td>
                    </tr>
                    <tr><td colspan="3"><a class="btn btn-primary" id="searchResourcesByDate">Search resources</a></td></tr>
                </tbody>
            </table>


            <div id="appendSearchedResources"></div>
        </div>
    </div>

    <h2>12. Run all LCA filterRules</h2>
    <a class="btn btn-primary" id="runFilter">Run filterRules</a>
    <div>
        <g:form action="getFailingResourcesForRule">
            <table class="resource" id="resulttable">
                <tbody id="resourceList">
                </tbody>
            </table>
        </g:form>
    </div>

    <opt:spinner/>
</div>
<script>
    $(document).ready(function(){
        $('[data-toggle="popover"]').popover({
            placement: 'bottom',
            html: true
        });

        $('#runFilter').on('click', function (event) {
            if (!$(this).attr('disabled')) {
                var applicationId = "LCA";


                if (applicationId) {
                    var queryString = 'applicationId=' + applicationId + '&allRules=true';

                    document.getElementById('resourceList').innerHTML = '';
                    $('#searchFieldContent').hide();
                    $.ajax({async: true, type:'POST',
                        data: queryString,
                        url:'/app/sec/admin/import/runApplicationFilterRule',
                        beforeSend: function() {
                            $("#loadingSpinner").show();
                            $("#searchResourcesByDate").attr("disabled", true);
                            $("#applicationId").attr("disabled", true);
                            $("#filterRule").attr("disabled", true);
                            $("#runFilter").attr("disabled", true);
                        },
                        success: function(data, textStatus) {
                            $("#loadingSpinner").hide();
                            $("#searchResourcesByDate").attr("disabled", false);
                            $("#applicationId").attr("disabled", false);
                            $("#filterRule").attr("disabled", false);
                            $("#runFilter").attr("disabled", false);
                            if (data.output) {
                                $('#resourceList').append(data.output);
                                $('#searchFieldContent').show();
                            }
                        },
                        error:function(XMLHttpRequest,textStatus,errorThrown){
                        }
                    });
                }
            }
        });

        $('#searchResourcesByDate').on('click', function (event) {
            if (!$(this).attr('disabled')) {
                var startDate = $("#startDate").val();
                var endDate = $("#endDate").val();
                var queryString = 'startDate=' + startDate + '&endDate=' + endDate;

                $.ajax({async: true, type:'POST',
                    data: queryString,
                    url:'/app/sec/admin/import/searchResourcesByDate',
                    beforeSend: function() {
                        appendLoader("appendSearchedResources");
                        $("#searchResourcesByDate").attr("disabled", true);
                        $("#applicationId").attr("disabled", true);
                        $("#filterRule").attr("disabled", true);
                        $("#runFilter").attr("disabled", true);
                    },
                    success: function(data, textStatus) {
                        $("#appendSearchedResources").empty();
                        $("#searchResourcesByDate").attr("disabled", false);
                        $("#applicationId").attr("disabled", false);
                        $("#filterRule").attr("disabled", false);
                        $("#runFilter").attr("disabled", false);
                        if (data.output) {
                            $('#appendSearchedResources').append(data.output);
                        }
                    },
                    error:function(XMLHttpRequest,textStatus,errorThrown){
                    }
                });
            }
        });
    });

    function togglePCRTable() {
        var table = $('#pcrTable');

        if ($(table).is(":visible")) {
            $(table).slideUp().addClass('hidden');
        } else {
            $(table).slideDown().removeClass('hidden');
        }

    }

    $("#startDate").datepicker({
        dateFormat: 'dd.mm.yy',
        changeMonth: true,
        changeYear: true,
        minDate: new Date(2019, 8, 28)
    });

    $("#endDate").datepicker({
        dateFormat: 'dd.mm.yy',
        changeMonth: true,
        changeYear: true,
        minDate: new Date(2019, 8, 28)
    });
    $('a#uniqueEpdPopover').on('click', function(e) {e.preventDefault(); return true;});
</script>
</body>
</html>
