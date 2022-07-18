<%-- Copyright (c) 2012 by Bionova Oy --%>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <meta name="format-detection" content="telephone=no"/>
    <asset:javascript src="jquery.autocomplete.js?v=${grailsApplication.metadata.'app.version'}"/>
</head>
<body>

<div class="container section">
    <div class="sectionheader">
        <h2>Remap</h2>
    </div>

    <div class="sectionbody well">
        <strong>Training data to be remapped:</strong>
        <ul class="resultRow">
            <li><strong>ApplicationId:</strong> ${trainingData.applicationId}</li>
            <li><strong>ImportMapperId:</strong> ${trainingData.importMapperId}</li>
            <li><strong>TrainingData:</strong> ${trainingData.trainingData}</li>
            <li><strong>Resource:</strong> ${trainingData.resource?.nameEN}</li>
            <li><strong>ResourceId:</strong> ${trainingData.resourceId}</li>
            <li><strong>ProfileId:</strong> ${trainingData.profileId}</li>
            <li><strong>TrainingComment:</strong> ${trainingData.trainingComment}</li>
            <li><strong>OriginatingSystem:</strong> ${trainingData.originatingSystem}</li>
            <li><strong>User:</strong> ${trainingData.username}</li>
            <li><strong>Created:</strong> <g:formatDate date="${trainingData.time}" format="dd.MM.yyyy HH:mm:ss" /></li>
        </ul>
        <g:form action="save" useToken="true">
            <g:hiddenField name="id" value="${trainingData?.id}"/>

            <div class="clearfix"></div>
            <div class="column_left">

                <div class="input-append">
                    <input type="text" name="resourceId" class="autocomplete" value=""
                           class="input-xlarge" autocomplete="off" placeholder="${message(code: "typeahead.info")}" id="autocomplete" /><a tabindex="-1" title="" class="add-on showAllResources"><i class="icon-chevron-down"></i></a>
                    <input type="hidden" name="resourceAndProfile" id="resourceAndProfile" />
                </div>

            </div>

            <div class="clearfix"></div>

            <opt:submit name="save" value="Remap" class="btn btn-primary"/>
            <opt:link action="list" class="btn"><g:message code="cancel" /></opt:link>
        </g:form>
    </div>
</div>

<script type="text/javascript">
    var loadingImg = "<img src=\"/app/assets/animated_loading_icon.gif\" alt=\"\" style=\"height: 16px; padding: 0\" />";

    $(document).ready(function() {
        var object = document.getElementById("autocomplete");

        if (object) {
            $(object).devbridgeAutocomplete({
                serviceUrl: '/app/jsonresources', groupBy: 'category',
                minChars: 3,
                deferRequestBy: 1000,
                params: {remap: 'true', applicationId: 'LCA', importMapperId: '${trainingData.importMapperId}'},
                ajaxSettings: {
                    success: function(data) {
                        setFlashForAjaxCall(JSON.parse(data))
                    },
                    error: function(data) {
                        setFlashForAjaxCall(JSON.parse(data))
                    }
                },
                formatResult: function (suggestion) {
                    return formatAutocompleteRows(suggestion, null, null, null);
                },
                formatGroup: function (suggestion, category) {
                    return formatAutocompleteGroups(suggestion, category);
                },
                onSelect: function (suggestion) {
                    handleAutocompleteOnSelect(suggestion, $(this), null, null, null, true);
                },
                onSearchStart: function (query) {
                   var showAllButton =  $(this).siblings('a:first');
                   $(showAllButton).children().removeClass("icon-chevron-down");
                   $(showAllButton).children().empty();
                   $(showAllButton).children().append(loadingImg);
                },
                onSearchComplete: function (query, suggestions) {
                   var showAllButton =  $(this).siblings('a:first');
                   $(showAllButton).children().empty();
                   $(showAllButton).children().addClass("icon-chevron-down");
                   var options = {
                      minChars: 5,
                      deferRequestBy: 1000
                   };
                   $(this).devbridgeAutocomplete().setOptions(options);
                }
            });
        }

        $(".showAllResources").on('click', function (event) {
            var object = $(this).siblings('input:text:first');

            if (object) {
                $(object).val("");
                var options = {
                    minChars: 0
                };
                $(object).devbridgeAutocomplete().clearCache();
                $(object).devbridgeAutocomplete().setOptions(options);
                $(object).trigger('focus');
            }
        });
    });
</script>
</body>
</html>



