<!doctype html>
<%@ page import="com.bionova.optimi.core.service.ResourceTypeService" %>
<%
    ResourceTypeService resourceTypeService = grailsApplication.mainContext.getBean("resourceTypeService")
    def userService = grailsApplication.mainContext.getBean("userService")
%>
<html>
<head>
    <asset:stylesheet src="bootstrap.css" />
    <asset:stylesheet src="layout.css"/>
    <asset:stylesheet src="content.css"/>
    <asset:stylesheet src="jquery.dataTables-custom.css"/>
    <asset:stylesheet src="jquery-ui-1.8.21.custom.css"/>
    <asset:stylesheet src="font-awesome.css"/>
    <asset:stylesheet src="select2.min.css"/>
    <asset:stylesheet src="tooltipsCustom.scss"/>
    <!--[if lte IE 8]>
    <asset:stylesheet src="ie8-and-down.css"/>
    <![endif]-->
    <asset:javascript src="jquery-3.6.0.min.js"/>
    <asset:javascript src="jquery-ui.js"/>
    <%--<asset:javascript src="jquery-migrate-3.1.0.js"/>--%>
    <asset:javascript src="bootstrap.js"/>
    <asset:javascript src="bootstrap-combobox.js"/>
    <asset:javascript src="font-awesome.js"/>
    <asset:javascript src="select2.full.min.js"/>
    <asset:javascript src="360optimi.js"/>
    <asset:javascript src="maximize-select2-height.min.js"/>
    <asset:javascript src="tether.min.js" />
    <asset:javascript src="highcharts.js" />
    <asset:javascript src="no-data-to-display.js"/>
    <asset:javascript src="exporting.js" />
    <asset:javascript src="export-data.js" />
    <asset:javascript src="offline-exporting.js"/>
    <meta name="format-detection" content="telephone=no"/>
    <meta name="layout" content="main"/>

</head>
<style>
    body {
        padding-top:0 !important;
        overflow-x:scroll;
        overflow-y:scroll;
    }
</style>
<body>
<div class="container section">
    <div class="container section">
        <div class="text-center">
            <g:if test="${userResource}">
                <h2 class="headingForBenchmarks text-center"><g:message code="subTypeBenchmarkGraph_heading1" args="[resourceTypeService.getLocalizedName(subType), resourcesAmount, subType?.standardUnit?.toUpperCase(), benchmarkName?.toUpperCase()]" /> <i class="fa fa-question-circle helpBenchmark" rel="popover" data-trigger="hover"></i></h2>
                <p class="headingForBenchmarks text-center"><g:message code="help_benchmark.localcompensation"/></p> <span id="noteForCountry"></span>
            </g:if>
            <g:else>
                <h2 class="headingForBenchmarks text-center"><g:message code="subTypeBenchmarkGraph_heading2" args="[resourceTypeService.getLocalizedName(subType), subType?.standardUnit?.toUpperCase()]" /> <i class="fa fa-question-circle helpBenchmark" rel="popover" data-trigger="hover"></i></h2>
                <p class="headingForBenchmarks text-center"><g:message code="help_benchmark.localcompensation"/></p> <span id="noteForCountry"></span>
            </g:else>
        </div>
        <g:if test="${showNewBenchmark}">
            <div id="graphNew" class="container section"></div>
        </g:if>
        <div class="appendGraphHeadingsHere"></div>

        <sec:ifAnyGranted roles="ROLE_SALES_VIEW">
            <div class="countriesInBenchmark inliner">
                <a href="javascript:" class="btn btn-primary bold" id="toggleAllCountries"><g:message code="benchmark_toggle_country"/></a>
                <g:each in="${countryCodesAndLocalizedName?.keySet()?.toList()}" var="isoCode">
                    <g:if test="${localEnvironment}">
                        <opt:checkBox value="${isoCode}" class="countryCheckbox" name="checkBoxFor${isoCode}"/>  <div class="flagTooltip"><img src="/app/assets/isoflags/${isoCode}.png" onerror="this.onerror=null;this.src='/app/assets/isoflags/globe.png'" class="smallFlag"/><span class="flagTooltipText">${countryCodesAndLocalizedName.get(isoCode)}</span></div>
                    </g:if>
                    <g:else>
                        <opt:checkBox value="${isoCode}" class="countryCheckbox" name="checkBoxFor${isoCode}"/>  <div class="flagTooltip"><img src="${isoFilePath}${isoCode}.png" onerror="this.onerror=null;this.src='/app/assets/isoflags/globe.png'" class="smallFlag"/><span class="flagTooltipText">${countryCodesAndLocalizedName.get(isoCode)}</span></div>
                    </g:else>
                </g:each>
            </div>
            <div id="graphContent" class="container section"></div>
            <div class="hidden" id="adminTable">

                    <div class="inliner buttonsForBenchmarks">
                        <div class="inliner">
                            <sec:ifAnyGranted roles="ROLE_SUPER_USER"><strong><a href="javascript:" class="btn btn-primary" id="showHiddenTableRows">Toggle uncalculable resources</a></strong></sec:ifAnyGranted>
                        </div>

                    </div>
                    <table style="${userService.getSuperUser(user) ? "min-width: 1500px !important;" : ""}" class="resource table table-striped table-condensed" id="resulttable">
                        <g:if test="${resourcesBySubType}">
                            <g:render template="/resourceType/materialsTable" model="[subType: subType, resourcesBySubType: resourcesBySubType, drawableBenchmarks: drawableBenchmarks,
                                                                                      showPercentages: showPercentages, userResource: userResource, benchmarkToShow: benchmarkToShow,
                                                                                      allBenchmarks: allBenchmarks, countryCodesAndLocalizedName: countryCodesAndLocalizedName]" />
                        </g:if>
                    </table>

            </div>
        </sec:ifAnyGranted>
    </div>
</div>
<script type="text/javascript">



    var hiddenDatasets = {};



    $(function () {
        var dropDown = $('#indicatorDropdown');
        if($(dropDown).find('li').length < 1) {
            $('#graphDropdown').prop('disabled', true);
            $('#graphDropdown').addClass('disabled');
            $('#graphDropdown').addClass('clickOff');
            $('#graphDropdown').removeAttr("href");

        }else {
            $('#graphDropdown').prop('disabled', false);

        }
    });

    $(function () {
        $('.hiddenTableRow').hide();
    });

    $(function () {
        $('.helpBenchmark[rel=popover]').popover({
            content:"${message(code:'resource.ranking_help_1')}. ${message(code:'resource.ranking_help_2')}",
            placement:'bottom',
            template: '<div class="popover"><div class="arrow"></div><div class="popover-content ribaHelp" style="font-size: 12px !important; font-weight: normal !important;"></div></div>',
            trigger:'hover',
            html:true
        });
        $('#graphDropdown[rel="popover"]').popover({
            placement: 'top',
            template: '<div class="popover popover-fade" style=" display: block;"><div class="arrow"></div><div class="popover-content"></div></div>'
        });
        $('.expiredIcon').popover({
            content:"${message (code: 'expired_icon_detail')}",
            html:true,
            template: '<div class="popover"><div class="arrow"></div><div class="popover-content"></div></div>',
            trigger:'hover'
        });
    });

    $(function (subTypeId) {
        subTypeId = "${subType?.id}";
        var showPercentages = "${showPercentages}";
        var queryString = 'resourceSubTypeId=' + subTypeId + '&showPercentages=' + showPercentages + '&resourceId=' + "${userResource?.resourceId}" + '&profileId=' + "${userResource?.profileId}" + '&projectCoutryId=' + "${countryOfProject?.resourceId}"+ '&entityId=' + '${entityId}' + '&stateIdOfProject=' + '${stateIdOfProject}';
        <g:each in="${benchmarkToShow}" var="benchmark">
            queryString += "&benchmarkToShow=${benchmark}";
        </g:each>
        <g:if test="${showNewBenchmark}">
        $.ajax({
            type: 'POST',
            data: queryString,
            url: '/app/sec/resourceType/graphBenchmarkGroupByRegion',
            success: function (data, textStatus) {
                if (data.output) {
                    $("#graphNew").append(data.output)
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
            }
        });
        </g:if>
        <sec:ifAnyGranted roles="ROLE_SALES_VIEW">
        function hideChartOnly() {
            $('#graphContent').remove();
            $('#removeChart').remove();
        }

        $('#toggleAllCountries').on('click', function () {
            var checkBoxes = $('.countryCheckbox');
            var anyChecked = [];
            $(checkBoxes).each(function () {
                if ($(this).is(':checked')) {
                    anyChecked.push(this.value)
                }
            });
            if (anyChecked.length) {
                checkBoxes.prop('checked', false)
            } else {
                checkBoxes.prop('checked', true)
            }
            toggleCountry();
        });

        $('.countryCheckbox').on('change', function () {
            toggleCountry()
        });

        function toggleCountry(isoCode) {
            var checkBoxes = $('.countryCheckbox');
            var isoCodes = [];
            $(checkBoxes).each(function () {
                if ($(this).is(':checked')) {
                    isoCodes.push(this.value)
                }
            });

            reDrawChartByCountry(isoCodes);
            reDrawTableByCountry(isoCodes);
        }
        $.ajax({
            type: 'POST',
            data: queryString,
            url: '/app/sec/resourceType/graphByBenchmark',
            beforeSend: function(){
                $('#graphWrapper').remove();
                $('.loading-spinner').removeClass("hidden");
            },
            complete: function() {
                $('#btnGrop').removeClass("hidden");
                $(".loading-spinner").addClass("hidden");
                $("#graphDropdown").popover('disable');
            },
            success: function (data, textStatus) {
                if (data.output) {
                    $('#graphContent').append(data.output);
                }else {
                    $('.nograph').show();

                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
            }
        });
        </sec:ifAnyGranted>

});

function reDrawChartByCountry(isoCodes) {
    if (isoCodes.length > 0) {
        var subTypeId = "${subType?.id}";
            var showPercentages = "${showPercentages}";

            var queryString = 'resourceSubTypeId=' + subTypeId + '&showPercentages=' + showPercentages + '&resourceId=' + "${userResource?.resourceId}" + '&profileId=' + "${userResource?.profileId}" + '&isoCodes=' + isoCodes;

            <g:each in="${benchmarkToShow}" var="benchmark">
                queryString += "&benchmarkToShow=${benchmark}";
            </g:each>
            $.ajax({
                type: 'POST',
                data: queryString,
                url: '/app/sec/resourceType/graphByBenchmark',
                beforeSend: function () {
                    $('#graphWrapper').remove();
                    $('.loading-spinner').removeClass("hidden");
                },
                complete: function () {
                    $('#btnGrop').removeClass("hidden");
                    $(".loading-spinner").addClass("hidden");
                    $("#graphDropdown").popover('disable');
                },
                success: function (data, textStatus) {
                    if (data.output) {
                        $('#graphContent').empty().append(data.output);
                    } else {
                        $('.nograph').show();

                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                }
            });
        } else {
            $('#graphContent').empty();
            $('.nograph').show();

        }


    }

    function reDrawTableByCountry(isoCodes) {
        var subTypeId = "${subType?.id}";
        var resourceid = "${userResource?.resourceId}";
        var queryString ='resourceSubTypeId=' + subTypeId + '&resourceId=' + resourceid + '&profileId=' + "${userResource?.profileId}"+ '&isoCodes=' + isoCodes;
        <g:each in="${benchmarkToShow}" var="benchmark">
            queryString += "&benchmarkToShow=${benchmark}";
        </g:each>

        $.ajax({
            url:'/app/sec/resourceType/reDrawTableByCountry',
            data: queryString,
            success:function (data) {
                $('#resulttable').empty().fadeOut().append(data.output).fadeIn();
                var hiddenRows = $('.hiddenTableRow');
                hiddenRows.toggle();
            }
        })

    }

    $('#showHiddenTableRows').on('click', function (event) {
        var hiddenRows = $('.hiddenTableRow');
        hiddenRows.toggle();
    });
</script>
</body>
</html>

