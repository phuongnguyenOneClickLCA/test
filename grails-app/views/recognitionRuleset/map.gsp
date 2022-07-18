<%-- Copyright (c) 2012 by Bionova Oy --%>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="format-detection" content="telephone=no"/>
    <asset:javascript src="jquery.autocomplete.js?v=${grailsApplication.metadata.'app.version'}"/>
    <g:if test="${supportedFilters}">
        <asset:stylesheet src="filterCombobox.css"/>
    </g:if>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <h1>
            <i class="fa fa-magic" aria-hidden="true"></i>
            Map stuff for ${recognitionRuleset.name}
        </h1>
    </div>
</div>

<g:if test="${"warn".equalsIgnoreCase(recognitionRuleset.type)}">
    <div class="container section">
        <div class="sectionbody">
            <table class="table table-striped table-condensed table-data">
                <thead>
                <tr>
                    <th>Matching value</th>
                    <th>Comment</th>
                    <th>&nbsp;</th>
                </tr>
                </thead>
                <tbody>
                <tr><th colspan="4">Create new warning for ${recognitionRuleset.name}</th></tr>
                <g:form action="saveTextmatch">
                    <g:hiddenField name="id" value="${recognitionRuleset.id}" />
                    <tr>
                        <td>
                            <g:textField name="textMatch" value="" placeHolder="Give value for matching"/>
                            <opt:checkBox name="matchAllWariants" checked="false" style="margin-left: 15px"/>
                            <i><strong>Match all variants of the value</strong></i>
                        </td>
                        <td>
                            <g:textField name="comment" value="" placeHolder="Give reason for this match"/>
                        </td>
                        <td><g:submitButton name="save" value="${message(code: 'save')}" class="btn btn-primary" /></td>
                    </tr>
                </g:form>
                <tr><th colspan="3">Existing warnings</th></tr>
                <g:each in="${recognitionRuleset.textMatches?.sort({ it?.textMatch })}" var="textMatch">
                    <g:form action="saveTextmatch">
                        <g:hiddenField name="id" value="${recognitionRuleset.id}" />
                        <tr>
                            <td>
                                <g:hiddenField name="existing" value="true" />
                                <g:hiddenField name="oldValue" value="${textMatch.textMatch}" />
                                <g:textField name="textMatch" value="${textMatch.textMatch}" />
                                <opt:checkBox name="matchAllWariants" checked="${textMatch?.matchAllVariants}" style="margin-left: 15px"/>
                                <i><strong>Match all variants of the value</strong></i>
                            </td>
                            <td>
                                <g:textField name="comment" value="${textMatch.comment}" />
                            </td>
                            <td>
                                <g:submitButton name="save" value="${message(code: 'save')}" class="btn btn-primary" />
                                <g:link action="removeTextmatch" params="[id: recognitionRuleset.id, textMatch: textMatch.textMatch]" class="btn btn-danger" onclick="return modalConfirm(this);"
                                        data-questionstr="Are you sure you want to remove warning '${textMatch?.textMatch}'?"
                                        data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}"
                                        data-titlestr="Deleting warning"><g:message code="delete" /></g:link>
                            </td>
                        </tr>
                    </g:form>
                </g:each>
                </tbody>
            </table>
            <p>&nbsp;</p>
        </div>
    </div>
</g:if>
<g:elseif test="${"discard".equalsIgnoreCase(recognitionRuleset.type)}">
    <div class="container section">
        <div class="sectionbody">
            <table class="table table-striped table-condensed table-data">
                <thead>
                <tr>
                    <th>Matching value</th>
                    <th>Comment</th>
                    <th>&nbsp;</th>
                </tr>
                </thead>
                <tbody>
                <tr><th colspan="4">Create new discard for ${recognitionRuleset.name}</th></tr>
                <g:form action="saveTextmatch">
                    <g:hiddenField name="id" value="${recognitionRuleset.id}" />
                    <tr>
                        <td>
                            <g:textField name="textMatch" value="" placeHolder="Give value for matching"/>
                            <opt:checkBox name="matchAllWariants" checked="false" style="margin-left: 15px"/>
                            <i><strong>Match all variants of the value</strong></i>
                        </td>
                        <td>
                            <g:textField name="comment" value="" placeHolder="Give reason for this match"/>
                        </td>
                        <td><g:submitButton name="save" value="${message(code: 'save')}" class="btn btn-primary" /></td>
                    </tr>
                </g:form>
                <tr><th colspan="3">Existing discards</th></tr>
                <g:each in="${recognitionRuleset.textMatches?.sort({ it?.textMatch })}" var="textMatch">
                    <g:form action="saveTextmatch">
                        <g:hiddenField name="id" value="${recognitionRuleset.id}" />
                        <tr>
                            <td>
                                <g:hiddenField name="existing" value="true" />
                                <g:hiddenField name="oldValue" value="${textMatch.textMatch}" />
                                <g:textField name="textMatch" value="${textMatch.textMatch}" />
                                <opt:checkBox name="matchAllWariants" checked="${textMatch?.matchAllVariants}" style="margin-left: 15px"/>
                                <i><strong>Match all variants of the value</strong></i>
                            </td>
                            <td>
                                <g:textField name="comment" value="${textMatch.comment}" />
                            </td>
                            <td>
                                <g:submitButton name="save" value="${message(code: 'save')}" class="btn btn-primary" />
                                <g:link action="removeTextmatch" params="[id: recognitionRuleset.id, textMatch: textMatch.textMatch]" class="btn btn-danger" onclick="return modalConfirm(this);"
                                        data-questionstr="Are you sure you want to remove discard '${textMatch?.textMatch}'?"
                                        data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}"
                                        data-titlestr="Deleting discard"><g:message code="delete" /></g:link>
                            </td>
                        </tr>
                    </g:form>
                </g:each>
                </tbody>
            </table>
            <p>&nbsp;</p>
        </div>
    </div>
</g:elseif>
<g:elseif test="${"mapping".equalsIgnoreCase(recognitionRuleset.type)}">
    <div class="container section">
        <div class="sectionbody">
            <div class="control-group">
                <h3>Upload system training data</h3>
                <g:uploadForm action="uploadTrainingData">
                    <g:hiddenField name="id" value="${recognitionRuleset.id}" />
                    <div class="clearfix"></div>
                    <div class="column_left">
                        <div class="control-group">
                            <label for="file" class="control-label"><g:message code="import.excel_file" /></label>
                            <div class="controls"><input type="file" name="xlsFile" id="file" class="btn" value="" /></div>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                    <opt:submit name="import" value="${message(code:'import')}" class="btn btn-primary"/>
                    <g:checkBox name="overWrite" id="overWrite" value="true" checked="false" style="width: 25px; !important; height: 17px; !important;"/> Overwrite
                </g:uploadForm>

                <g:if test="${supportedFilters}">
                    <div class="container" id="quickFilterOptions">
                        <g:set var="random" value="${UUID.randomUUID().toString()}"/>
                        <div class="screenheader">
                            <table class="quickFilterTable">
                                <tbody>
                                <tr>
                                <td class="pull-right quickFilterFunnel"><i class="fa fa-filter fa-2x" aria-hidden="true"></i>
                                    <g:each in="${supportedFilters}" var="queryFilter">
                                        <g:set var="random" value="${UUID.randomUUID().toString()}"/>
                                        <td id="${queryFilter.resourceAttribute}td" class="quickFilterTableTd text-center"><div class="btn-group quickFilterTableGroup" id="${queryFilter.resourceAttribute}btnGroup" style="display:inline-block">
                                            <span class="quickFilters bold"  id="${queryFilter.resourceAttribute}th"  onclick="quickFilterChoices('${queryFilter.resourceAttribute}td','quickFilter${queryFilter.resourceAttribute}', '${queryFilter.resourceAttribute}', '${queryFilter.localizedName}', null,'${queryId}', null, null, 'rulesetMapping')">${queryFilter.localizedName}</span>
                                            <select class="quickFilterSelect" data-resourceAttribute="${queryFilter.resourceAttribute}" style="width:${queryFilter?.filterWidth ? queryFilter.filterWidth : '170'}px;" onchange="saveUserFilters();" id="quickFilter${queryFilter.resourceAttribute}">
                                            </select>
                                        </div></td>
                                    </g:each>
                                    <td style="vertical-align: middle"><a href="javascript:" id="resetAllMapFilters"><g:message code="query.reset_filter"/></a></td>
                                </tr>
                                </tbody></table>
                        </div>
                    </div>
                </g:if>

                <g:if test="${recognitionRuleset.systemTrainingDatas && recognitionRuleset.systemTrainingDatas.find({ it.systemMatches.find({ !it.resourceId }) })}">
                    <g:form action="mapResourcesToTrainingData" method="post" name="saveEverything" novalidate="novalidate">
                        <g:hiddenField name="id" value="${recognitionRuleset.id}" />
                        <g:if test="${recognitionRuleset.systemTrainingDatas}">
                            <g:submitButton name="save" value="Map everything" class="btn btn-primary" style="font-weight: normal; margin-bottom: 25px;" />
                        </g:if>
                        <g:each in="${recognitionRuleset.systemTrainingDatas.findAll({ it.systemMatches.find({ !it.resourceId }) })}" var="trainingDataSet">
                            <table class="table table-striped table-condensed table-data data-list">
                            <tbody>
                                <g:set var="random" value="${UUID.randomUUID().toString()}" />
                                <tr>
                                    <td><strong>${trainingDataSet.fileName}</strong></td>
                                    <td></td>
                                    <td>
                                        <g:if test="${!disabled}">
                                            <opt:link action="removeTrainingDataSet" id="${recognitionRuleset.id}" params="[fileName: trainingDataSet.fileName]" class="btn btn-danger"
                                                      onclick="return modalConfirm(this);" data-questionstr="Are you sure you want to delete: (${trainingDataSet.fileName}) and all its unmapped training data?" data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}" data-titlestr="${message(code:'admin.license.delete_feature.header')}">Delete unmapped</opt:link>
                                        </g:if>
                                    </td>
                                </tr>

                                <table class="table table-condensedt" border="1">
                                    <thead>
                                    <tr>
                                        <th class="mapTrainingDataTable"><strong>SystemTrainingData</strong></th>
                                        <th><strong>Map to resource</strong></th>
                                        <th><strong>Mapped resource</strong></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <g:set var="equalId" value="" />
                                    <g:each in="${trainingDataSet.systemMatches}" var="systemMatch">
                                        <g:if test="${!systemMatch.resourceId}">
                                            <tr>
                                                <td>
                                                    ${systemMatch.systemTrainingData}
                                                </td>
                                                <td>
                                                    <div class="input-append">
                                                        <input type="text" name="ifcResource${systemMatch.systemTrainingData}" class="autocomplete filterThisAutoComplete input-xlarge" value=""
                                                               autocomplete="off" data-applicationId="${recognitionRuleset.applicationId}" placeholder="${query?.localizedtypeaheadSearchText ? query.localizedtypeaheadSearchText : message(code: "typeahead.info")}" /><a tabindex="-1" title="" class="add-on showAllResources"><i class="icon-chevron-down"></i></a>
                                                        <input type="hidden" name="resourceId.${systemMatch.systemMatchId}" id="resourceAndProfile" />
                                                    </div>
                                                </td>
                                                <td>
                                                    ${systemMatch.resourceId}
                                                    <g:if test="${systemMatch.id}">
                                                        <a href="javascript:;" onclick="window.open('${createLink(controller: "import", action: "showData", params: [resourceUUID: systemMatch.id])}', '_blank', 'width=1024, height=768, scrollbars=1');">
                                                            <i class="fas fa-search-plus"></i>
                                                        </a>
                                                    </g:if><br />
                                                </td>
                                            </tr>
                                        </g:if>
                                    </g:each>
                                    </tbody>
                                </table>
                            </tbody>
                            </table>
                        </g:each>
                    </g:form>
                </g:if>
                <g:if test="${recognitionRuleset.systemTrainingDatas && recognitionRuleset.systemTrainingDatas.find({ it.systemMatches.find({ it.resourceId }) })}">
                    <table class="table table-striped table-condensed table-data data-list">
                        <thead>
                        <tr>
                            <th><strong>Incoming key</strong></th>
                            <th style="width: 750px;"><strong>Mapped resource</strong></th>
                            <th><strong>Import File</strong></th>
                            <th><strong>Remap</strong></th>
                            <th><strong>Delete</strong></th>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${recognitionRuleset.systemTrainingDatas}" var="trainingDataSet">
                            <g:each in="${trainingDataSet.systemMatches.findAll({ it.resourceId })}" var="systemMatch">
                                <g:set var="resource" value="${systemMatch.resource}"/>
                                <tr${!resource?.active ? " style=\"color: red; font-weight:bold;\"" : ""}>
                                <td>
                                    ${systemMatch.systemTrainingData}
                                </td>
                                <td id="name${systemMatch.systemMatchId}" style="width: 750px;">
                                    ${abbr (value: resource?.staticFullName, maxLength: '100')}
                                    <a href="javascript:" onclick="window.open('${createLink(controller: "import", action: "showData", params: [resourceId: systemMatch.resourceId, profileId: systemMatch.profileId])}', '_blank', 'width=1024, height=768, scrollbars=1');">
                                        <i class="fas fa-search-plus"></i>
                                    </a>
                                    <g:set var="random" value="${UUID.randomUUID().toString()}" />
                                    <opt:renderDataCardBtn resourceId="${resource?.resourceId}" profileId="${resource?.profileId}" showGWP="true" infoId="${random}"/>
                                </td>
                                <td id="autocomplete${systemMatch.systemMatchId}" style="display: none; width: 750px;">
                                    <g:form action="remapSystemMatch" method="post" name="saveEverything" novalidate="novalidate">
                                        <g:hiddenField name="recognitionRulesetId" value="${recognitionRuleset.id}" />
                                        <g:hiddenField name="trainingDataFileName" value="${trainingDataSet.fileName}" />
                                        <g:hiddenField name="systemMatchId" value="${systemMatch.systemMatchId}" />
                                        <div class="input-append">
                                        <input type="text" class="remapAutocomplete filterThisAutoComplete input-xlarge" value="" data-applicationId="${recognitionRuleset.applicationId}"
                                               autocomplete="off" placeholder="${query?.localizedtypeaheadSearchText ? query.localizedtypeaheadSearchText : message(code: "typeahead.info")}" /><a tabindex="-1" title="" class="add-on showAllResources"><i class="icon-chevron-down"></i></a>
                                            <input type="hidden" name="resourceId.${systemMatch.systemMatchId}" id="remapResourceAndProfile" />
                                            <g:submitButton name="save" value="Save" class="btn btn-primary" style="font-weight: normal;" />
                                        </div>
                                    </g:form>
                                </td>
                                <td>
                                    ${trainingDataSet.fileName}
                                </td>
                                <td><a href="javascript:" onclick="toggleRemap('autocomplete${systemMatch.systemMatchId}','name${systemMatch.systemMatchId}')" class="btn btn-primary">Remap</a></td>
                                <td><g:link action="deleteSystemMatch" params="[recognitionRulesetId: recognitionRuleset.id, trainingDataFileName: trainingDataSet.fileName, systemMatchId: systemMatch.systemMatchId]" class="btn btn-danger"
                                            onclick="return modalConfirm(this);" data-questionstr="Are you sure you want to delete this systemMatch?" data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}" data-titlestr="Delete systemMatch">Delete</g:link></td>
                                </tr>
                            </g:each>
                        </g:each>
                        </tbody>
                    </table>
                </g:if>
            </div>
        </div>
    </div>
    <g:if test="${resourceTypeFilter}">
        <div class="nestedSelectContainer hidden"><ul class="nestedSelect" id="nestedFilter${resourceTypeFilter?.resourceAttribute}"></ul></div>
    </g:if>
</g:elseif>
<script type="text/javascript">
    var loadingImg = "<img src=\"/app/assets/animated_loading_icon.gif\" alt=\"\" style=\"height: 16px; padding: 0\" />";

    $(document).ready(function() {

        <g:if test="${supportedFilters}">
            quickFiltersSelect2('${message(code: 'quickFilter.placeholder')}');
            $('.quickFilters').trigger('click');

            $('body').on({
                mouseenter: function () {
                    var highlighted_item = $(this);
                    if ($(this).attr("id").indexOf("resourceType") !== -1) {
                        var select2Box = $('.nestedSelectContainer');
                        new Tether({
                            element: select2Box,
                            target: highlighted_item,
                            attachment: 'top left',
                            targetAttachment: 'top left',
                            targetOffset:"0, ${resourceTypeFilter?.filterWidth?.toInteger() - 30}px"

                        });
                        var resourceType = $(highlighted_item).attr('data-resourceTypeId');
                        if ($(highlighted_item).attr("data-haschildren") === "true") {
                            nestedFilterChoices('nestedFilter${resourceTypeFilter.resourceAttribute}', '${resourceTypeFilter.resourceAttribute}', resourceType, null, null, null, null, null, highlighted_item);
                        }else {
                            $(select2Box).addClass('hidden');
                        }
                    }
                }
            }, '.select2-results__option--highlighted');

            $('body').on({
                click: function () {
                    var select2Box = $('.nestedSelectContainer');
                    $(select2Box).addClass('hidden');

                }
            });

            $('.nestedSelectContainer').on({
                mouseleave: function () {
                    var select2Box = $('.nestedSelectContainer');
                    $(select2Box).addClass('hidden');
                }
            });
        </g:if>

        var autocompletes = document.getElementsByClassName("autocomplete");
        var remapAutocompletes = document.getElementsByClassName("remapAutocomplete");

        if (autocompletes) {
            for (var i = 0; i < autocompletes.length; i++) {
                var object = autocompletes[i];
                if (object) {
                    var applicationId = $(object).attr("data-applicationId");
                    $(object).devbridgeAutocomplete({
                        serviceUrl: '/app/jsonresources', groupBy: 'category',
                        minChars: 3,
                        deferRequestBy: 1000,
                        params: {remap: 'true', resourcesForMapping: true, applicationId: applicationId},
                        ajaxSettings: {
                            success: function(data) {
                                setFlashForAjaxCall(JSON.parse(data))
                            },
                            error: function(data) {
                                setFlashForAjaxCall(JSON.parse(data))
                            }
                        },
                        formatResult: function (suggestion) {
                            return formatAutocompleteRows(suggestion, null, null, null, $(object).val());
                        },
                        onSelect: function (suggestion) {
                            handleAutocompleteOnSelect(suggestion, $(this), null, null, null, true);
                        },
                        onSearchStart: function (query) {
                            var showAllButton = $(this).siblings('a:first');
                            $(showAllButton).children().removeClass("icon-chevron-down");
                            $(showAllButton).children().empty();
                            $(showAllButton).children().append(loadingImg);
                        },
                        onSearchComplete: function (query, suggestions) {
                            var showAllButton = $(this).siblings('a:first');
                            $(showAllButton).children().empty();
                            $(showAllButton).children().addClass("icon-chevron-down");
                            var options = {
                                minChars: 3,
                                deferRequestBy: 1000
                            };
                            $(this).devbridgeAutocomplete().setOptions(options);
                        }
                    });
                }
            }
        }

        if (remapAutocompletes) {
            for (var i = 0; i < remapAutocompletes.length; i++) {
                var object = remapAutocompletes[i];
                if (object) {
                    var applicationId = $(object).attr("data-applicationId");
                    $(object).devbridgeAutocomplete({
                        serviceUrl: '/app/jsonresources', groupBy: 'category',
                        minChars: 3,
                        deferRequestBy: 1000,
                        params: {remap: 'true', resourcesForMapping: true, applicationId: applicationId},
                        ajaxSettings: {
                            success: function(data) {
                                setFlashForAjaxCall(JSON.parse(data))
                            },
                            error: function(data) {
                                setFlashForAjaxCall(JSON.parse(data))
                            }
                        },
                        formatResult: function (suggestion) {
                            return formatAutocompleteRows(suggestion, null, null, null, $(object).val());
                        },
                        onSelect: function (suggestion) {
                            handleAutocompleteOnSelect(suggestion, $(this), null, null, null, true);
                        },
                        onSearchStart: function (query) {
                            var showAllButton = $(this).siblings('a:first');
                            $(showAllButton).children().removeClass("icon-chevron-down");
                            $(showAllButton).children().empty();
                            $(showAllButton).children().append(loadingImg);
                        },
                        onSearchComplete: function (query, suggestions) {
                            var showAllButton = $(this).siblings('a:first');
                            $(showAllButton).children().empty();
                            $(showAllButton).children().addClass("icon-chevron-down");
                            var options = {
                                minChars: 3,
                                deferRequestBy: 1000
                            };
                            $(this).devbridgeAutocomplete().setOptions(options);
                        }
                    });
                }
            }
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

        // todo fix this to work properly. Currently it works but not in a "correct" way. It resets the filters by emptying them and redoing them to prevent optimisticlocking.
        $('#resetAllMapFilters').on("click", function() {
            $('.quickFilterSelect').empty();
            saveUserFilters();
            var quickFilterTds = $('.quickFilterTableGroup');
            $('.quickFilterTableTd').removeClass('filterChosenHighlight');
            $(quickFilterTds).addClass('warningHighlight');
            setTimeout(function () {
                $('.quickFilters').trigger("click");
                $(quickFilterTds).removeClass('warningHighlight');
            },1000);
        });
    });

    function toggleRemap(autocompleteId, nameTdId) {
        $('#' + autocompleteId).toggle();
        $('#' + nameTdId).toggle();
    }

</script>
</body>
</html>