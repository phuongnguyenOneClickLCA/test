<!doctype html>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <h1>Run filterRules</h1>
    </div>
</div>
<div class="container section">
<sec:ifAllGranted roles="ROLE_DATA_MGR">

    <g:if test="${dryRunNotification}">
        <div style="margin-bottom: 20px;" class="alert">
            <strong>${dryRunNotification}</strong><br />
            <g:link action="clearDryRunResources" class="btn btn-primary"><g:message code="admin.import.dry_run_resources.clear" /></g:link>
        </div>
    </g:if>

    <div style="float: left; margin-bottom: 600px;">
        <label for="applicationId">Application:</label>
        <select name="applicationId" id="applicationId" style="width: 150px;">
            <option></option>
            <g:each in="${applicationIds}">
                <option ${it == "LCA" ? 'selected' : ''} value="${it}">${it}</option>
            </g:each>
        </select>

        <div id="filterRules" style="margin-top: 10px;" class="input-append">
            <label for="filterRule">Application filterRules:</label><select multiple name="filterRule" id="filterRule" style="width: 150px;"></select><br/>
            <a class="btn btn-primary" style="margin-top: 5px;" id="runFilter">Run filter</a>
        </div>

        <div class="container">
            <div class="screenheader">
                <h1>Run resource purposes</h1>
            </div>
        </div>

        <div id="resourcePurposes" style="margin-top: 10px;" class="input-append">
            <label for="resourcePurpose">LCA Application resourcePurposes:</label><select multiple name="resourcePurpose" id="resourcePurpose" style="width: 150px;"></select><br/>
            <input style="float:left; margin-top: 10px; width: 25px; !important; height: 17px; !important;" type="checkbox" id="removePurposes" name="removePurposes" value="true">
            <label style="margin-left: 5px; margin-top: 10px;" for="removePurposes">Remove selected purposes</label>
            <a class="btn btn-primary" style="margin-top: 5px;" id="runResourcePurposes">Run resource purposes</a>
        </div>

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
    <div class="clearfix"></div>


</sec:ifAllGranted>
</div>

<script type="text/javascript">
    $(document).ready(function() {
        var $select = $('#applicationId');
        $('#filterRule').select2({
            width: 550,
            data: ${new groovy.json.JsonBuilder(lcaRules)},
            templateResult: function formatOptions(data, container) {
                if(typeof data.children != 'undefined') {

                    var s = $(data.element).find('option').length - $(data.element).find('option:selected').length;
                    // My optgroup element
                    var el = $('<span class="pointerCursor hightLightOnHover my_select2_optgroup'+(s ? '' : ' my_select2_optgroup_selected')+'">'+data.text+'</span>');

                    // Click event
                    el.click(function() {
                        // Select all optgroup child if there aren't, else deselect all
                        $('#filterRule').find('optgroup[label="' + $(this).text() + '"] option').prop(
                            'selected',
                            $(data.element).find('option').length - $(data.element).find('option:selected').length
                        );
                        // Trigger change event + close dropdown
                        $('#filterRule').change();
                        $('#filterRule').select2('close');
                    });

                    // Hover events to properly manage display
                    el.mouseover(function() {
                        $('li.select2-results__option--highlighted').removeClass('select2-results__option--highlighted');
                    });
                    el.hover(function() {
                        el.addClass('my_select2_optgroup_hovered');
                    }, function() {
                        el.removeClass('my_select2_optgroup_hovered');
                    });
                    return el;
                }
                return data.text;
            }
        }).maximizeSelect2Height();



        $select.select2().maximizeSelect2Height();
        $select.on('change', function (event) {
            event.preventDefault();
            var applicationId = $(this).val();
            $('#filterRule').children().remove();

            if (applicationId) {
                $('#filterRules').hide();
                $('#searchFieldContent').hide();

                $.ajax({
                    async: false, type: 'POST',
                    data: 'applicationId=' + applicationId,
                    url: '/app/sec/admin/import/applicationFilters',
                    success: function (data, textStatus) {
                        if (data) {
                            $('#filterRules').show();
                            console.log(data.rules);
                            //$('#filterRule').append(data);
                            $('#filterRule').select2({
                                width: 550,
                                data: data.rules,
                                templateResult: function formatOptions(data, container) {
                                    if(typeof data.children != 'undefined') {

                                        var s = $(data.element).find('option').length - $(data.element).find('option:selected').length;
                                        // My optgroup element
                                        var el = $('<span class="pointerCursor hightLightOnHover my_select2_optgroup'+(s ? '' : ' my_select2_optgroup_selected')+'">'+data.text+'</span>');

                                        // Click event
                                        el.click(function() {
                                            // Select all optgroup child if there aren't, else deselect all
                                            $('#filterRule').find('optgroup[label="' + $(this).text() + '"] option').prop(
                                                'selected',
                                                $(data.element).find('option').length - $(data.element).find('option:selected').length
                                            );
                                            // Trigger change event + close dropdown
                                            $('#filterRule').change();
                                            $('#filterRule').select2('close');
                                        });

                                        // Hover events to properly manage display
                                        el.mouseover(function() {
                                            $('li.select2-results__option--highlighted').removeClass('select2-results__option--highlighted');
                                        });
                                        el.hover(function() {
                                            el.addClass('my_select2_optgroup_hovered');
                                        }, function() {
                                            el.removeClass('my_select2_optgroup_hovered');
                                        });
                                        return el;
                                    }
                                    return data.text;
                                }
                            }).maximizeSelect2Height();
                        } else {
                            $('#filterRules').hide();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {

                    }
                });
            } else {
                $('#filterRules').hide();
            }
        });

        $('#runFilter').on('click', function (event) {
            if (!$(this).attr('disabled')) {
                var applicationId = $('#applicationId').val();
                var ruleId = $('#filterRule').val();

                if (applicationId && ruleId) {
                    var queryString = 'applicationId=' + applicationId;

                    $.each(ruleId, function (val, attr) {
                        queryString = queryString + '&ruleId=' + attr;
                    });

                    queryString = queryString + '&includeIds=true';

                    document.getElementById('resourceList').innerHTML = '';
                    $('#searchFieldContent').hide();
                    $.ajax({async: true, type:'POST',
                        data: queryString,
                        url:'/app/sec/admin/import/runApplicationFilterRule',
                        beforeSend: function() {
                            $("#loadingSpinner").show();
                            $("#applicationId").attr("disabled", true);
                            $("#filterRule").attr("disabled", true);
                            $("#runFilter").attr("disabled", true);
                            $('#runResourcePurposes').attr("disabled", true);
                            $('#resourcePurpose').attr("disabled", true);
                        },
                        success: function(data, textStatus) {
                            $("#loadingSpinner").hide();
                            $("#applicationId").attr("disabled", false);
                            $("#filterRule").attr("disabled", false);
                            $("#runFilter").attr("disabled", false);
                            $('#runResourcePurposes').attr("disabled", false);
                            $('#resourcePurpose').attr("disabled", false);
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

        $('#resourcePurpose').select2({
            width: 550,
            data: ${new groovy.json.JsonBuilder(lcaResourcePurposes)},
            templateResult: function formatOptions(data, container) {
                if(typeof data.children != 'undefined') {

                    var s = $(data.element).find('option').length - $(data.element).find('option:selected').length;
                    // My optgroup element
                    var el = $('<span class="pointerCursor hightLightOnHover my_select2_optgroup'+(s ? '' : ' my_select2_optgroup_selected')+'">'+data.text+'</span>');

                    // Click event
                    el.click(function() {
                        // Select all optgroup child if there aren't, else deselect all
                        $('#resourcePurpose').find('optgroup[label="' + $(this).text() + '"] option').prop(
                            'selected',
                            $(data.element).find('option').length - $(data.element).find('option:selected').length
                        );
                        // Trigger change event + close dropdown
                        $('#resourcePurpose').change();
                        $('#resourcePurpose').select2('close');
                    });

                    // Hover events to properly manage display
                    el.mouseover(function() {
                        $('li.select2-results__option--highlighted').removeClass('select2-results__option--highlighted');
                    });
                    el.hover(function() {
                        el.addClass('my_select2_optgroup_hovered');
                    }, function() {
                        el.removeClass('my_select2_optgroup_hovered');
                    });
                    return el;
                }
                return data.text;
            }
        }).maximizeSelect2Height();


        $('#runResourcePurposes').on('click', function (event) {
            if (!$(this).attr('disabled')) {
                var ruleId = $('#resourcePurpose').val();

                if (ruleId) {
                    var removePurposes = $('#removePurposes').is(":checked");
                    var queryString = 'removePurposes=' + removePurposes;

                    $.each(ruleId, function (val, attr) {
                        queryString = queryString + '&resourcePurpose=' + attr;
                    });

                    document.getElementById('resourceList').innerHTML = '';
                    $('#searchFieldContent').hide();
                    $.ajax({async: true, type:'POST',
                        data: queryString,
                        <g:set var="configurationService" bean="configurationService"/>
                        <g:if test="${configurationService.getConfigurationValue("useOldRunApplicationResourcePurpose")?.toBoolean()}">
                        url:'/app/sec/admin/import/runApplicationResourcePurposeOld',
                        </g:if>
                        <g:else>
                        url:'/app/sec/admin/import/runApplicationResourcePurpose',
                        </g:else>
                        beforeSend: function() {
                            $("#loadingSpinner").show();
                            $("#applicationId").attr("disabled", true);
                            $("#filterRule").attr("disabled", true);
                            $("#runFilter").attr("disabled", true);
                            $('#runResourcePurposes').attr("disabled", true);
                            $('#resourcePurpose').attr("disabled", true);
                        },
                        success: function(data, textStatus) {
                            $("#loadingSpinner").hide();
                            $("#applicationId").attr("disabled", false);
                            $("#filterRule").attr("disabled", false);
                            $("#runFilter").attr("disabled", false);
                            $('#runResourcePurposes').attr("disabled", false);
                            $('#resourcePurpose').attr("disabled", false);
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
    });


  </script>
</body>
</html>
