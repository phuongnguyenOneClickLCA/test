<%-- Copyright (c) 2012 by Bionova Oy --%>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<g:form action="combine" name="docombine">
    <g:hiddenField name="entityId" value="${entityId}" />
    <g:hiddenField name="childEntityId" value="${childEntityId}" />
    <g:hiddenField name="queryId" value="${queryId}" />
    <g:hiddenField name="indicatorId" value="${indicator?.indicatorId}" />
    <div class="container">
        <div class="screenheader">
            <h4> <opt:link controller="main" removeEntityId="true"><g:message code="main" /></opt:link>
                <sec:ifLoggedIn>
                    <g:if test="${parentEntity}">
                        > <opt:link controller="entity" action="show" id="${parentEntity?.id}">
                        <g:abbr value="${parentEntity?.name}" maxLength="20" />
                    </opt:link>
                        <g:if test="${entity}">
                            > <span id="childEntityName">${entity?.operatingPeriodAndName}</span>
                        </g:if>
                    </g:if>
                </sec:ifLoggedIn>
                > <g:message code="import_data" /> <br/> </h4>
            <g:if test="${showSteps}">
                <opt:breadcrumbs current="${currentStep}"/>
            </g:if>
                <div class="alert alert-info">
                    <i class="fas fa-info pull-left" style="font-size: large; margin-right: 8px;"></i>
                    <div id="groupingHasInfo" ${grouperGroupsByClass && groupByForCollapse ? "":"style='display:none'"}><strong>${message(code: "importMapper.combiner.info1", args: [datasetsSize]) } <span id="datapointsSpan">${datapointsAfterCombine}</span> ${message (code:"importMapper.combiner.info2")}.</strong>
                        <strong>${message (code:"combinerTable.maxLimit", args:[skipCombineRowLimit])}</strong></div><div id='groupingNoInfo' ${grouperGroupsByClass && groupByForCollapse ? "style='display:none'":""}><strong>${message(code: "importMapper.combiner.nothingToCombine", args:[datasetsSize] )}.</strong>
                    <strong>${message(code: 'combinerTable.maxLimit', args: [skipCombineRowLimit])}</strong></div>
                </div>

            <div class="pull-right hide-on-print">
                <g:link action="cancel" class="btn" onclick="return modalConfirm(this);"
                        data-questionstr="${message(code: 'importMapper.index.cancel')}"
                        data-truestr="${message(code: 'yes')}" data-falsestr="${message(code: 'no')}"
                        data-titlestr="Cancel"><g:message code="cancel" /></g:link>
                <g:link controller="importMapper" elementId="excelDownloadButton" action="generateExcelFromDatasets" params="[returnUserTo: 'dataForCollapser', entityId: entityId, childEntityId: childEntityId]" class="btn btn-primary"><g:message code="results.expand.download_excel" /></g:link>
                <g:submitButton name="combine" value="${message(code: 'continue')}" onclick="doubleSubmitPrevention('docombine');" class="btn btn-primary" />
            </div>

        </div>
        <div class="container">
            <opt:spinner/>
        </div>
    </div>

    <div class="container section">
        <div class="sectionbody" id="combinerContent">
            <div>
                <h2><g:message code="combinePage.heading"/> </h2>
                <p><g:message code="combinePage.subHeading"/></p>
            </div>
            <div class="container-fluid center-block borderbox">
                <div class="collapsernav" %{--style="overflow-x: scroll;"--}%>
                    <div class="table">
                        <div style="vertical-align: middle; border-bottom: 2px solid #dee2e6; background-color: #f5f5f5; padding: 8px;">
                            <strong><g:message code="importMapper.combiner.recommended"/>:</strong>
                        </div>
                        <div class="container">
                            <g:set var="recommendedHeaders" value="${headers.findAll({it.equalsIgnoreCase("CLASS")||jsonGroupers*.toUpperCase()?.contains(it.toUpperCase())||unitIdentifyingField*.toUpperCase()?.contains(it.toUpperCase())})}" />
                            <g:each in="${recommendedHeaders}" var="header">
                                <g:set var="checked" value="${groupByForCollapse?.contains(header)}"/>
                                <g:if test="${"QTY_TYPE".equalsIgnoreCase(header)}">
                                    <div style="width: fit-content; padding: 8px; text-align: left; float: left; height: 25px; /*border: dashed red;*/" id="choose${header}">
                                        <input style="float: left; margin-right: 5px;" type="checkbox" id="${header}checkbox" value="${header}" checked="checked" class="staticGroupingCheckbox" disabled/><label for="${header}checkbox" style="font-size: 1em; float: left;" class="control-label">${header}</label>
                                        <a href="#" class="importMapperHelpPopover"  style="margin-left: 5px; display: inline-block;" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="<g:message code="combinePage.qty_type_tooltip"/>"><i class="icon-question-sign"></i></a>
                                    </div>
                                </g:if>
                                <g:else>
                                    <div style="width: fit-content; padding: 8px; text-align: left; float: left; height: 25px; /*border: dashed red;*/" id="choose${header}"><input style="float: left; margin-right: 5px;" type="checkbox" id="${header}checkbox" value="${header}" class="groupingCheckbox" ${checked ? "checked=\"checked\"" : ""} onchange="reRenderCombiner()"/><label for="${header}checkbox" style="font-size: 1em; float: left;" class="control-label">${header}</label></div>
                                </g:else>
                            </g:each>
                            <g:each in="${groupByForCollapseSpecial}" var="header">
                                <g:set var="checked" value="${groupByForCollapse?.contains(header)}"/>
                                    <div style="width: fit-content; padding: 8px; text-align: left; float: left; height: 25px; /*border: dashed red;*/" id="choose${header}">
                                    <input style="float: left; margin-right: 5px;" type="checkbox" id="${header}checkbox" value="${header}" class="specialGroupingCheckbox" ${checked ? "checked=\"checked\"" : ""} onchange="reRenderCombiner()" /><label for="${header}checkbox" style="font-size: 1em; float: left;" class="control-label">${header}</label>
                                    <g:if test="${!"SOLIDITY".equalsIgnoreCase(header)}">
                                        <input style="width: 50px; height: 13px; margin-left: 5px; text-align: right; float: left;" maxlength="8" type="text" data-header="${header}" class="specialGroupRange numeric" value="${thicknessCombinationRange}" />
                                        <g:if test="${"THICKNESS_IN".equalsIgnoreCase(header)}">
                                            <p style="display: inline-block; margin: 7px 0 0 5px; float: left;">in</p>
                                        </g:if>
                                        <g:else>
                                            <p style="display: inline-block; margin: 7px 0 0 5px; float: left;">mm</p>
                                        </g:else>
                                        <a href="#" class="importMapperHelpPopover"  style="margin: 6px 0 0 5px; display: inline-block;" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="<g:message code="combinePage.specialCombine"/>"><i class="icon-question-sign"></i></a>
                                    </g:if>
                                </div>
                            </g:each>
                            <g:each in="${headers.findAll({!recommendedHeaders.contains(it)})}" var="header">
                                <g:set var="checked" value="${groupByForCollapse?.contains(header)}"/>
                                <div style="width: fit-content; padding: 8px; text-align: left; float: left; height: 25px; /*border: dashed red;*/" id="choose${header}"><input style="float: left; margin-right: 5px;" type="checkbox" id="${header}checkbox" value="${header}" class="groupingCheckbox" ${checked ? "checked=\"checked\"" : ""} onchange="reRenderCombiner()"/><label for="${header}checkbox" style="font-size: 1em; float: left;" class="control-label">${header}</label></div>
                            </g:each>
                        </div>
                    </div>
                </div>
            </div>
            <h2>${message(code:"importMapper.combiner_table_heading")}</h2>

            <div id="combiner" style="margin-top: 25px;">
                <g:render template="combinerTable" model="[datasetsSize: datasetsSize, datapointsAfterCombine: datapointsAfterCombine,
                                                      grouperGroupsByClass: grouperGroupsByClass, skipHeadingsForKey: skipHeadingsForKey,
                                                      groupByForCollapse: groupByForCollapse, skipCombineRowLimit: skipCombineRowLimit]" />
            </div>

            <g:if test="${descriptiveGrouperGroupsByClass}">
                <h2>${message(code:"importMapper.combiner_descriptive_table_heading")}</h2>

                <div id="combiner" style="margin-top: 25px;">
                    <g:render template="descriptiveCombinerTable" model="[datapointsAfterCombine: datapointsAfterCombine,
                                                                          grouperGroupsByClass: descriptiveGrouperGroupsByClass]" />
                </div>

            </g:if>
        </div>
    </div>
</g:form>
<script type="text/javascript">
    $('.numeric').on('input propertychange', numericInputValidation);

    var timeout = null;

    function doDelayedSearch() {
        if (timeout) {
            clearTimeout(timeout);
        }
        timeout = setTimeout(function() {
            reRenderCombiner();
        }, 2000);
    }

    $(function() {
        var clicked = false;

        $("#excelDownloadButton").on('click', function(e) {
            if (clicked) {
                e.preventDefault();
                $(this).css('opacity', 0.7);
            }
        });
        $("#combine").on("click", function () {
            $("#loadingSpinner").show();
            $("#combinerContent").hide()
        });

        var helpPopSettings = {
            placement: 'top',
            template: '<div class="popover popover-fade" style="display: block; max-width: 150px;"><div class="arrow"></div><div class="popover-content"></div></div>'
        };
        $(".importMapperHelpPopover[rel=popover]").popover(helpPopSettings);
    });

    function numericInputValidation(){
        var start = this.selectionStart;
        end = this.selectionEnd;
        var val = $(this).val();
        if (isNaN(val)) {
            val = val.replace(/[^0-9\.\,]/g, '');

            if (val.split('.').length > 2) {
                val = val.replace(/\.+$/, '');
            }

            if (val.split(',').length > 2) {
                val = val.replace(/\,+$/, '');
            }
            var dotsAndCommas = ((val.match(/\./g) || []).length + (val.match(/,/g) || []).length);

            if (dotsAndCommas > 1) {
                if (val.indexOf('.') < val.indexOf(',')) {
                    val = val.replace('.', '');
                } else {
                    val = val.replace(',', '');
                }
            }
        }
        $(this).val(val);
        this.setSelectionRange(start, end);
        doDelayedSearch();
    }

    function reRenderCombiner() {
        var target = $("#combiner");
        var newCombineCriteria = [];
        var specialCombineCriteria = [];
        var specialCombineRange = {};
        $("input:checkbox[class=groupingCheckbox]:checked").each(function () {
            newCombineCriteria.push($(this).val());
        });
        $("input:checkbox[class=staticGroupingCheckbox]:checked").each(function () {
            newCombineCriteria.push($(this).val());
        });
        $("input:checkbox[class=specialGroupingCheckbox]:checked").each(function () {
            specialCombineCriteria.push($(this).val());
        });
        $('.specialGroupRange').each(function () {
            var heading = $(this).attr('data-header');
            specialCombineRange[heading] = $(this).val();
        });


        if (!$.isEmptyObject(newCombineCriteria)||!$.isEmptyObject(specialCombineCriteria)) {
            $.ajax({
                type: "POST",
                url: "/app/sec/importMapper/renderNewCombinerFromCriteria",
                data: {newCombineCriteria: JSON.stringify(newCombineCriteria), specialCombineCriteria: JSON.stringify(specialCombineCriteria), specialCombineRange: JSON.stringify(specialCombineRange)},
                beforeSend: function() {
                    $('.groupingCheckbox').attr("disabled", true);
                    $('.specialGroupingCheckbox').attr("disabled", true);
                    $('.specialGroupRange').attr("disabled", true);
                    $("#loadingSpinner").show();
                    target.hide();
                },
                success: function (data) {
                    $('.groupingCheckbox').attr("disabled", false);
                    $('.specialGroupingCheckbox').attr("disabled", false);
                    $('.specialGroupRange').attr("disabled", false);
                    $("#loadingSpinner").hide();
                    if (data.output) {
                        target.html(data.output).fadeIn();
                    } else {
                        target.html('');
                    }
                }
            });
        }

    }
    function updateDatapointsSpan(button, amount) {
        var datapointsSpan = $("#datapointsSpan");
        var button = $(button);
        var groupingHasInfo = $("#groupingHasInfo")
        var groupingNoInfo = $("#groupingNoInfo")
        if(groupingNoInfo && groupingHasInfo){
            $(groupingHasInfo).show()
            $(groupingNoInfo).hide()
            if ($(datapointsSpan).length && $(button).length) {
                var intAmount = parseInt(amount);
                var current = parseInt(datapointsSpan.text());
                $(datapointsSpan).html(current + intAmount - 1);
                $(button).closest("tr").addClass("hidden")
                $(button).siblings("input").attr("checked",false)
            } else if ($(datapointsSpan).length && amount){
                var intAmount = parseInt(amount);
                $(datapointsSpan).html(intAmount);
            } else if($(datapointsSpan).length){
                $(groupingHasInfo).hide()
                $(groupingNoInfo).show()
            }
        }

    }
</script>
</body>
</html>



