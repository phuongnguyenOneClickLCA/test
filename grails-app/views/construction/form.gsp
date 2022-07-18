<%
    def userService = grailsApplication.mainContext.getBean("userService")
%>
<!DOCTYPE html>
<%@ page import="com.bionova.optimi.core.service.ConstructionService" %>
<%@ page import="com.bionova.optimi.core.service.ResourceTypeService" %>
<%@ page import="com.bionova.optimi.data.ResourceCache" %>
<%@ page import="com.bionova.optimi.core.service.OptimiResourceService" %>
<%
    ConstructionService constructionService = grailsApplication.mainContext.getBean("constructionService")
    ResourceTypeService resourceTypeService = grailsApplication.mainContext.getBean("resourceTypeService")
    OptimiResourceService optimiResourceService = grailsApplication.mainContext.getBean("optimiResourceService")
%>
<html>
    <head>
        <meta name="layout" content="main" />
        <meta name="format-detection" content="telephone=no"/>
        <g:if test="${supportedFilters}">
            <asset:stylesheet src="filterCombobox.css"/>
        </g:if>
        <asset:javascript src="jquery.autocomplete.js?v=${grailsApplication.metadata.'app.version'}"/>
    </head>
    <body>
    <div class="container">
    <div class="screenheader">
        <g:if test="${!construction}">
        <h1><g:message code="construction.add_new"/></h1>
        </g:if><g:else>
        <h1><g:message code="construction.editing"/> ${constructionService.getLocalizedName(construction)}</h1>
    </g:else>
    </div>
    </div>
<div class="container">
    <div class="sectionbody">
        <div class="wrapperForNewConstructions container">
            <g:uploadForm action="save" name="constructionForm">
                <g:hiddenField name="id" value="${duplicateConstruction? null : construction?.id}"/>
                <div class="constructionPageWrapper">
                    <div class="sectionheader">
                        <div class="sectioncontrols pull-right">
                            <g:if test="${privateConstructionAccountId}">
                                <opt:link action="index" params="[accountId:privateConstructionAccountId]" class="btn"><g:message code="back"/></opt:link>
                            </g:if><g:else>
                                <opt:link action="index" params="[group: group?.id]" class="btn"><g:message code="back"/></opt:link>
                            </g:else>
                            <sec:ifAllGranted roles="ROLE_DATA_MGR">
                                <a href="javascript:" class="btn btn-primary" onclick="showSuperUserOptions(this)" >Show Superuser options</a>
                            </sec:ifAllGranted>
                            <opt:submit name="save" disabled="${locked}" value="${message(code: 'save')}" class="btn btn-primary" onclick="doubleSubmitPrevention('constructionForm');"/>
                            <g:if test="${locked}">
                                <opt:submit name="unlockandunpublish" value="${message(code: 'btn.return_to_draft')}" class="btn btn-danger" onclick="doubleSubmitPrevention('constructionForm');"/>
                            </g:if>
                            <g:else>
                                <opt:submit name="lockandpublish" value="${message(code: 'btn.lock_and_publish')}" class="btn btn-primary" onclick="doubleSubmitPrevention('constructionForm');"/>
                            </g:else>
                        </div>
                        <h2 class="h2expander bold" style="display: inline; cursor: default !important;"><g:message code="construction.description"/></h2>
                    </div>
                    <table class="constructionsTable">
                        <thead>
                        <tr> <td><p class="bold"><g:message code="createConstruction.regular_fields"/></p> </td></tr>
                        <tr><th><g:message code="entity.name"/></th><th><g:message code="resource.techSpec"/></th><th><g:message code="construction.data_source"/></th><th><g:message code="construction.data_type"/></th><th id="areaTh"><g:message code="account.country"/></th><th><g:message code="main.list.type"/></th><th><g:message code="resource.type"/></th><th><g:message code="account.brand_image"/> <i class="icon-question-sign longcontent" rel="popover" data-trigger="hover" data-content="${message(code: 'constructions.brand_help_info')}"></i></th>
                            </tr></thead>

                        <tbody id="tbody1">
                        <tr>
                            <td><opt:textField name="nameEN" style="max-width:150px;" value="${construction?.properties?.get("nameEN")}" disabled="${locked}" class="input redBorder"/></td>
                            <td><opt:textField name="technicalSpec" style="max-width:150px;" disabled="${locked}" value="${construction?.technicalSpec}" /></td>
                            <td><opt:textField name="environmentDataSource" style="max-width:150px;" disabled="${locked}" value="${construction?.environmentDataSource}" /></td>
                            <td><select name="environmentDataSourceType"  ${locked ? "disabled='true'" : ""} style="max-width:150px;">
                                <option class="hidden"></option>
                                <option ${construction?.environmentDataSourceType?.equalsIgnoreCase(message(code: 'resource.generic')?.toString()) ? 'selected="true"' : ''}><g:message code="resource.generic"/></option>
                                <option ${construction?.environmentDataSourceType?.equalsIgnoreCase(message(code: 'resource.manufacturer')?.toString()) ? 'selected="true"' : ''}><g:message code="resource.manufacturer"/></option>
                            </select></td>
                            <td><g:if test="${countries}">
                                <g:select name="country" disabled="${locked}" style="max-width:150px;" id="countrySelect" from="${countries}"
                                          optionKey="${{ it.resourceId ? it.resourceId : '' }}"
                                          optionValue="${{ optimiResourceService.getLocalizedName(it) }}"
                                          value="${construction?.country}"
                                          noSelection="['': '']"/>
                            </g:if>
                            </td>
                            <td> <g:select name="constructionType" disabled="${locked}" style="max-width:150px;" id="constructionTypeSelect" from="${constructionTypes}"
                                           optionKey="${{ it.value ? it.value?.toLowerCase(): '' }}"
                                           optionValue="${{ it.key }}" value="${construction?.constructionType}"
                                           noSelection="['': '']"/></td>
                            <td><select name="resourceSubType"  ${locked ? "disabled='true'" : ""} style="max-width:150px;">
                                <g:each in="${constructionResourceTypes}" var="subType">
                                    <option value="${subType.subType}" ${subType.subType.equals(resourceSubType) ? "selected=\"selected\"" : ""}>${resourceTypeService.getLocalizedName(subType)}</option>
                                </g:each>
                            </select></td>
                            <td>
                            <g:if test="${brandImages}">
                                <g:select name="brandImageId" disabled="${locked}" style="max-width:150px;" id="brandImageId" from="${brandImages}"
                                          optionKey="${{ it.id ? it.id : 'null'}}"
                                          optionValue="${{ it?.name }}" value="${construction?.brandImageId ?: locked ? 'null' : !user.internalUseRoles? account?.defaultBrandingImage : 'null' }"
                                          noSelection="['null': '']"/>
                            </g:if>
                            </td>

                        </tr>
                        </tbody>
                    </table>
                    <table class="constructionsTable">
                        <thead id="thead3">
                        <tr>
                            <th><g:message code="entity.show.task.description"/>
                            </th>
                        </tr>
                        </thead>
                        <tbody id="tbody3">
                        <tr>
                            <td>
                                <opt:textArea name="productDescription" value="${construction?.productDescription}"
                                              class="input-xlarge span8" rows="5" cols="100" id="productDescription"/>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <table class="constructionsTable">
                        <g:if test="${constructionAdditionalQuestions}">
                            <thead id="thead2">
                            <tr><td><p class="bold"><g:message code="createConstruction.additional_fields"/></p> </td></tr>
                            <tr>
                                <th><g:message code="entity.show.unit"/></th>
                                <g:if test="${constructionThicknessQuestion}">
                                    <th>${constructionThicknessQuestion.localizedQuestion}</th>
                                </g:if>
                                <g:each in="${constructionAdditionalQuestions}" var="additionalQuestion">
                                    <th>${additionalQuestion?.localizedQuestion}</th>
                                </g:each>
                            </tr>
                            </thead>
                            <tbody id="tbody2">
                            <tr>
                                <td><g:if test="${units}">
                                    <g:select style="width:50px;" class="userGivenUnit" name="unit" id="unitSelect" disabled="${locked}" from="${units}"
                                              optionKey="${{it}}"
                                              optionValue="${{it}}" value="${showImperial && construction?.imperialUnit ? construction?.imperialUnit : construction?.unit}"
                                              noSelection="['': '']" onchange="toggleThickness(this, 'true')"/>
                                    <g:if test="${locked}">
                                        <input type="hidden" name="unit" id="unit" value="${showImperial && construction?.imperialUnit ? construction?.imperialUnit : construction?.unit}" />
                                    </g:if>
                                </g:if></td>
                                <g:if test="${constructionThicknessQuestion}">
                                    <td><opt:textField name="${constructionThicknessQuestion.questionId}" disabled="${locked}" value="${construction?.properties?.get(constructionThicknessQuestion.questionId) ? construction?.properties?.get(constructionThicknessQuestion.questionId) : construction?.additionalQuestionAnswers?.get(constructionThicknessQuestion?.questionId) ? construction?.additionalQuestionAnswers?.get(constructionThicknessQuestion?.questionId) : ''}" class="input-small numeric thicknessInput" /></td>
                                </g:if>
                                <g:each in="${constructionAdditionalQuestions}" var="additionalQuestion">
                                    <td>
                                        <g:if test="${additionalQuestion.questionId.equals("transportResourceId") }">
                                            <select name="transportResourceId" style="max-width:100px;" ${locked ? "disabled='true'" : ""} style="max-width:150px;">
                                                <g:each in="${additionalQuestion.getAdditionalQuestionResources(null, null)}" var="value">
                                                    <option value="${value.resourceId}" ${construction?.transportResourceId?.equals(value.resourceId) ? 'selected="true' : ''}>${value.localizedName}</option>
                                                </g:each>
                                            </select>
                                        </g:if><g:elseif test="${additionalQuestion?.inputType == "select" && additionalQuestion?.choices}">
                                        <select name="${additionalQuestion.questionId}" ${locked ? "disabled='true'" : ""} style="max-width:150px;">
                                            <g:each in="${additionalQuestion?.choices}" var="choice">
                                                <option value="${choice.answerId}" ${construction?.additionalQuestionAnswers?.get(additionalQuestion?.questionId)?.toString()?.equalsIgnoreCase(choice.answerId) ? "selected='selected'" : ""}>
                                                    ${choice.localizedAnswer}
                                                </option>
                                            </g:each>
                                        </select>
                                    </g:elseif><g:else>
                                        <opt:textField name="${additionalQuestion.questionId}"  disabled="${locked}" value="${construction?.properties?.get(additionalQuestion.questionId) ? construction?.properties?.get(additionalQuestion.questionId) : construction?.additionalQuestionAnswers?.get(additionalQuestion?.questionId) ? construction?.additionalQuestionAnswers?.get(additionalQuestion?.questionId) : ''}" class="input-small ${additionalQuestion?.onlyNumeric ? 'numeric' : ''}" />
                                    </g:else></td>
                                </g:each>
                            </tr>
                            </tbody>
                        </g:if>
                    </table>
                    <table class="constructionsTable">
                        <thead class="superUserTable" style="display: none;">
                        <tr> <td><h2 class="bold">Super user options</h2> </td></tr>
                        </thead>

                        <thead class="superUserTable" style="display: none;">
                        <tr> <td><p class="bold">Localized fields</p> </td></tr>
                        <g:each in="${languages}" var="lang">
                            <g:if test="${!"EN".equals(lang.resourceId)}">
                                <th><g:message code="entity.name"/>${lang.resourceId}</th>
                            </g:if>
                        </g:each>
                        </thead>
                        <tbody class="superUserTable" style="display: none;">
                        <tr>
                            <g:each in="${languages}" var="lang">
                                <g:if test="${!"EN".equals(lang.resourceId)}">
                                    <td><opt:textField name="name${lang.resourceId}" style="max-width:150px;" value="${construction?.properties?.get("name"+ lang.resourceId)}" disabled="${locked}" class="input"/></td>
                                </g:if>
                            </g:each>
                        </tr>
                        </tbody>

                        <thead class="superUserTable" style="display: none;">
                        <tr><td><p class="bold">Scaling parameters</p></td></tr>
                        <tr>
                            <th>scalingType</th><th>defaultThickness_mm</th><th>defaultThickness_mm2</th><th>difference</th><th>difference2</th>
                        </tr>
                        </thead>
                        <tbody class="superUserTable" style="display: none;">
                        <tr>
                            <td><g:select style="width:50px;" name="scalingType" id="scalingTypeSelect" disabled="${locked}" from="${[1,2,3]}"
                                          optionKey="${{it}}"
                                          optionValue="${{it}}" value="${construction?.scalingType}"
                                          noSelection="['': '']"/>
                            </td>
                            <td><opt:textField name="defaultThickness_mm"  disabled="${locked}" value="${construction?.defaultThickness_mm}" class="input-small numeric" /></td>
                            <td><opt:textField name="defaultThickness_mm2"  disabled="${locked}" value="${construction?.defaultThickness_mm2}" class="input-small numeric" /></td>
                            <td><opt:textField name="difference"  disabled="${locked}" value="${construction?.difference}" class="input-small numeric" /></td>
                            <td><opt:textField name="difference2"  disabled="${locked}" value="${construction?.difference2}" class="input-small numeric" /></td>
                        </tr>
                        </tbody>

                        <thead class="superUserTable" style="display: none;">
                        <tr>
                            <th>Unbundable</th>
                            <th>Prevent expand</th>
                            <g:if test="${showInheritConstructionServiceLife}">
                                <th>Inherit construction serviceLife</th>
                            </g:if>
                        </tr>
                        </thead>
                        <tbody class="superUserTable" style="display: none;">
                        <tr>
                            <td><input type="checkbox" name="unbundable" value="true" ${(construction?.unbundable || !construction) ? "checked=\"checked\"" : ""} id="unbundable" class="${locked ? "removeClicks" : ""}"></td>
                            <td><input type="checkbox" name="preventExpand" value="true" ${construction?.preventExpand ? "checked=\"checked\"" : ""} id="preventExpand" class="${locked ? "removeClicks" : ""}"></td>
                            <g:if test="${showInheritConstructionServiceLife}">
                                <td><input type="checkbox" name="inheritConstructionServiceLife" value="true" ${construction?.inheritConstructionServiceLife ? "checked=\"checked\"" : ""} id="inheritConstructionServiceLife" class="${locked ? "removeClicks" : ""}"></td>
                            </g:if>
                        </tr>
                        </tbody>
                    </table>

                    <h2 class="bold"><g:message code="construction.components_materials"/></h2>

                    <g:if test="${supportedFilters}">
                    <div class="container" id="quickFilterOptions">
                        <g:set var="random" value="${UUID.randomUUID().toString()}"/>
                        <div class="screenheader">
                            <table class="quickFilterTable">
                                <tbody>
                                <tr>
                                <td class="pull-right quickFilterFunnel"><i class="fa fa-filter fa-2x" aria-hidden="true"></i>
                                    <g:each in="${supportedFilters}" var="queryFilter">
                                        <sec:ifAnyGranted roles="${queryFilter.userClass}">
                                            <g:set var="random" value="${UUID.randomUUID().toString()}"/>
                                            <td id="${queryFilter.resourceAttribute}td" class="quickFilterTableTd text-center"><div class="btn-group quickFilterTableGroup" id="${queryFilter.resourceAttribute}btnGroup" style="display:inline-block">
                                                <span class="quickFilters bold"  id="${queryFilter.resourceAttribute}th"  onclick="quickFilterChoices('${queryFilter.resourceAttribute}td','quickFilter${queryFilter.resourceAttribute}', '${queryFilter.resourceAttribute}', '${queryFilter.localizedName}', null,'${queryId}',null, true, null, null, null, '${classificationParamId ?: null}')">${queryFilter.localizedName}</span>
                                                <select class="quickFilterSelect" data-resourceAttribute="${queryFilter.resourceAttribute}" ${locked ? "disabled='true'" : ""} style="width:${queryFilter?.filterWidth ? queryFilter.filterWidth : '170'}px;" onchange="saveUserFilters(false, false, true, false, '${classificationQuestionId}', '${classificationParamId}');" id="quickFilter${queryFilter.resourceAttribute}">
                                                </select>
                                            </div></td>
                                        </sec:ifAnyGranted>
                                    </g:each>
                                    <td style="vertical-align: middle" id="resetTdForFilter"><a href="javascript:" id="resetAllConstructionFilters"><g:message code="query.reset_filter"/></a></td>
                                </tr>
                                </tbody></table>
                        </div>
                    </div>
                </g:if>
                </div>
                <div class="constructionPageWrapper">
                    <p class="bold"><g:message code="construction.add_materials"/></p>
                <div class="input-append">
                <input type="text" class="autocomplete"  placeholder="${query?.localizedtypeaheadSearchText ? query.localizedtypeaheadSearchText : message(code: "typeahead.info")}" data-resourceTableId="componentsTable"
                       data-queryId="${queryId}" data-questionId="${questionIdForResources}" data-sectionId="${sectionIdForResources}" data-accountId="${account?.id ?: ""}" ${locked ? 'disabled="true"' : ''}><a  tabindex="-1" class="add-on showAllResources"><i class="icon-chevron-down"></i></a>
                </div>
                <br>
                <table class="resourceTable query_resource" id="componentsTable" disabled="${locked}">
                    <thead>
                    <tr id="header" class="${!construction ? 'hidden' : ''}">
                        <th><g:message code="query.material" /></th><%--
                    --%><th colspan="3"><g:message code="query.quantity"/></th><%--
                --%><g:each in="${additionalQuestions?.sort()}" var="additionalQuestion"><%--
                    --%><g:if test="${!additionalQuestion.isThicknessQuestion}"><%--
                        --%><th>${additionalQuestion?.localizedQuestion}</th><%--
                    --%></g:if><%--
                --%></g:each><%--
                --%></tr>
                    </thead>
                <tbody class="dragginContainer" id="${section?.sectionId}${question?.questionId}Resources">
                <g:if test="${construction && construction.datasets}">
                    <g:set var="resourceCache" value="${ResourceCache.init(construction.datasets)}"/>
                    <g:each in="${construction.datasets}" var="dataset">
                        <queryRender:renderResourceRow additionalQuestions="${additionalQuestions}" question="${question}" constructionPage="${true}" fieldName="${fieldName}"
                                                       dataset="${dataset}" resource="${resourceCache.getResource(dataset)}" unitSystem="${user?.unitSystem ?: "metric"}"
                                                       multipleChoicesAllowed="${true}" resourceTableId="componentsTable"/>
                    </g:each>
                </g:if>
                </tbody>
                </table>
                </div>

                <g:hiddenField name="classificationQuestionId" value="${classificationQuestionId}"/>
                <g:hiddenField name="classificationParamId" value="${classificationParamId}"/>
                <g:hiddenField name="queryId" value="${queryId}" />
                <g:hiddenField name="privateConstructionAccountId" value="${privateConstructionAccountId}" />
                <g:hiddenField name="privateConstructionCompanyName" value="${privateConstructionCompanyName}" />
                <g:hiddenField name="constructionGroup" value="${construction?.constructionGroup ? construction.constructionGroup : group ? group.groupId : ""}"/>
                <g:hiddenField name="group" value="${group?.id}"/>
            </g:uploadForm>
        </div>
        </div>


    <g:if test="${resourceTypeFilter}">
        <div class="nestedSelectContainer hidden"><ul class="nestedSelect" id="nestedFilter${resourceTypeFilter?.resourceAttribute}"></ul></div>
    </g:if>
    <div style="margin-top: 150px;"></div>
</div>

    <script type="text/javascript">
        $('#locked').on('change', function () {
            toggleLockedState();
        });

        $(function () {
               numericInputCheck();
               draggableRows();
             <g:if test="${locked}">
                 $('.existingResourceRow :input').attr('disabled', 'true');
                 $('.existingResourceRow a').addClass('disabled_field');
             </g:if>
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
                     $('#nestedFilter${resourceTypeFilter.resourceAttribute}').empty();
                 }
             });
             </g:if>


             var autocompletes = document.getElementsByClassName("autocomplete");
             if (autocompletes) {
                 for (var i = 0; i < autocompletes.length; i++) {
                     var object = autocompletes[i];
                     var queryId = $(object).attr("data-queryId");
                     var sectionId = $(object).attr("data-sectionId");
                     var questionId = $(object).attr("data-questionId");
                     var unit = $(object).attr("data-unit");
                     var div = $(object).closest('div');
                     var accountId = $(object).attr("data-accountId");
                     $(object).attr('data-filtered', 'true');

                     if (object) {
                         $(object).devbridgeAutocomplete({
                             serviceUrl: '/app/jsonresources', groupBy: 'category',
                             minChars: 3,
                             deferRequestBy: 1000,
                             params: {
                                 queryId: queryId,
                                 sectionId: sectionId,
                                 questionId: questionId,
                                 unit: unit,
                                 constructionPage: true,
                                 privateDatasetAccountId: accountId
                             },
                             ajaxSettings: {
                                 success: function(data) {
                                     setFlashForAjaxCall(JSON.parse(data))
                                 },
                                 error: function(data) {
                                     setFlashForAjaxCall(JSON.parse(data))
                                 }
                             },
                             formatResult: function (suggestion) {
                                 return formatAutocompleteRows(suggestion, null, null, questionId, null, queryId, false);
                             },
                             onSelect: function (suggestion) {
                                if (suggestion.nextSkip) {
                                     var originalQuery = suggestion.originalQuery;
                                     var searchedWith = suggestion.searchedWith;
                                     $(this).val("");
                                     $(this).blur();

                                     if (originalQuery) {
                                         var list = originalQuery.split("__");

                                         if (list.length === 1) {
                                             originalQuery = "+skipShow__" + suggestion.nextSkip;
                                             if (searchedWith) {
                                                 originalQuery = originalQuery + "__" + searchedWith;
                                             }
                                         } else if (list.length >= 3) {
                                             if (!list[2]) {
                                                 list[2] = "null";
                                             }
                                             if (list.length === 4) {
                                                 list[3] = suggestion.nextSkip;
                                             } else {
                                                 list.push(suggestion.nextSkip)
                                             }
                                             originalQuery = list.join("__");
                                         }
                                     } else {
                                         // Straight up showing one subtype etc.
                                         originalQuery = "+skipShow__" + suggestion.nextSkip;
                                         if (searchedWith) {
                                             originalQuery = originalQuery + "__" + searchedWith;
                                         }
                                     }

                                    var that = $(this);

                                     setTimeout(function () {
                                         $(that).val(originalQuery).devbridgeAutocomplete("onValueChange");
                                         $(that).val("");
                                         $(that).trigger('focus');
                                     }, 150);
                                 } else {
                                    $(this).attr("data-resourceId", suggestion.data.resource);
                                    addResourcesToConstructions($(this).attr("data-resourceId").split('.')[0], $(this).attr('data-resourceTableId'), $(this).attr('data-queryId'), $(this).attr('data-questionId'), $(this).attr('data-sectionId'), suggestion.constructionId);
                                    $(this).val("")
                                }
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
                         minChars: 0,
                         deferRequestBy: 1000
                     };
                     $(object).devbridgeAutocomplete().clearCache();
                     $(object).devbridgeAutocomplete().setOptions(options);
                     $(object).trigger('focus');
                 }
             });

             $('#resetAllConstructionFilters').on("click", function () {
                 $('.quickFilterSelect').empty();
                 var quickFilterTds = $('.quickFilterTableGroup');
                 $(this).hide();
                 var that = this
                 $('.quickFilterTableTd').addClass("removeClicks");
                 $('.quickFilterTableTd').removeClass('filterChosenHighlight');
                 saveUserFilters(false, false, true, false);

                 $('#resetTdForFilter').append('<i id="loadingReset" class="fa fa-cog fa-2x fa-spin primary"></i>');
                 setTimeout(function () {
                     $('.quickFilters').trigger("click");
                     $('#loadingReset').remove();
                     $(that).show();
                     $('.quickFilterTableTd').removeClass("removeClicks");
                 }, 1000);
             });
         });

        function toggleLockedState() {
            $(':input').prop("disabled", false);
            $('a').removeClass("disabled_field");
            $('.containerConstructionDatasets').fadeIn().removeClass('hidden');
        }

        function showSuperUserOptions(elem) {
            if ($('.superUserTable').is(":visible")) {
                $('.superUserTable').hide();
            } else {
                $('.superUserTable').fadeIn();
            }
        }

        function toggleThickness (elem, allowVariableThickness) {
            var row = $(elem).closest('tr');
            var xbox = row.find('.xBoxSub');
            var thickness = row.find('.thicknessInput');
            var unitText = row.find('.unitAfterInput');
            var userUnitSelect = row.find('.userGivenUnit');

            if (allowVariableThickness === "true") {
                if (userUnitSelect.val() === 'm3' || userUnitSelect.val() === 'cu ft' || userUnitSelect.val() === 'cu yd') {
                    $(thickness).trigger('blur');
                    $(thickness).attr('readOnly', 'readOnly');
                    $(thickness).addClass('visibilityHidden');
                    $(xbox).addClass('visibilityHidden');
                    $(unitText).addClass('visibilityHidden');

                    if (!$("#formChanged").length) {
                        $("#queryForm").append('<input type=\"hidden\" id=\"formChanged\" name=\"formChanged\" value=\"true\" />');
                    }

                } else if (userUnitSelect.val() === 'kg'  || userUnitSelect.val() === 'lbs' || userUnitSelect.val() === 'ton') {
                    $(thickness).trigger('blur');
                    $(thickness).attr('readOnly', 'readOnly');
                    $(thickness).addClass('visibilityHidden');
                    $(xbox).addClass('visibilityHidden');
                    $(unitText).addClass('visibilityHidden');

                    setTimeout(function () {
                        $(thickness).removeClass('thicknessChanged');
                    }, 2000);
                    if (!$("#formChanged").length) {
                        $("#queryForm").append('<input type=\"hidden\" id=\"formChanged\" name=\"formChanged\" value=\"true\" />');
                    }

                } else if (userUnitSelect.val() === 'm2' || userUnitSelect.val() === 'sq ft') {
                    $(thickness).trigger('blur');
                    $(thickness).removeAttr('readOnly');
                    $(thickness).addClass('thicknessChanged');
                    $(thickness).removeClass('visibilityHidden');
                    $(xbox).removeClass('visibilityHidden');
                    $(unitText).removeClass('visibilityHidden');

                    setTimeout(function () {
                        $(thickness).removeClass('thicknessChanged');
                    }, 2000);
                    if (!$("#formChanged").length) {
                        $("#queryForm").append('<input type=\"hidden\" id=\"formChanged\" name=\"formChanged\" value=\"true\" />');
                    }
                } else {
                    $(thickness).trigger('blur');
                    $(thickness).attr('readOnly', 'readOnly');
                    $(thickness).addClass('visibilityHidden');
                    $(xbox).addClass('visibilityHidden');
                    $(unitText).addClass('visibilityHidden');

                    if (!$("#formChanged").length) {
                        $("#queryForm").append('<input type=\"hidden\" id=\"formChanged\" name=\"formChanged\" value=\"true\" />');
                    }
                }
            } else {
                if (userUnitSelect.val() === 'm3' || userUnitSelect.val() === 'cu ft' || userUnitSelect.val() === 'cu yd' || userUnitSelect.val() === 'kg'  || userUnitSelect.val() === 'lbs' || userUnitSelect.val() === 'ton') {

                    if (!$("#formChanged").length) {
                        $("#queryForm").append('<input type=\"hidden\" id=\"formChanged\" name=\"formChanged\" value=\"true\" />');
                    }
                }
            }
        }


        if (typeof changeLinkToTextfield != 'function') {
            function changeLinkToTextfield(numeric, linkObject, fieldName, fieldId, addRedBorder, value, prepend) {
                if (linkObject) {
                    var parentTd = $(linkObject).parent();
                    removeExistingResourceRowClass(linkObject)

                    if (prepend) {
                        $(linkObject).remove();
                        $(parentTd).find( 'input:hidden' ).remove();
                        $(parentTd).prepend('<input type="text" onclick="numericInputCheck();" rel="popover" data-trigger="focus" data-html="true" data-content="${message(code: 'query.quantity_type.warning')}" name="' + fieldName + '" id="' + fieldId + '" value="' + (value ? value : '') + '" class="input-small '+ numeric +'" style="width: 35px;' + (addRedBorder ? ' border: 1px solid red;' : '') + '" />');
                    } else {
                        $(parentTd).empty();
                        $(parentTd).append('<input type="text" onclick="numericInputCheck();" rel="popover" data-trigger="focus" data-html="true" data-content="${message(code: 'query.quantity_type.warning')}" name="' + fieldName + '" id="' + fieldId + '" value="' + (value ? value : '') + '" class="input-small '+ numeric +'" style="width: 60px;' + (addRedBorder ? ' border: 1px solid red;' : '') + '" />');
                    }
                    $('[rel="popover"]').popover({
                        placement: 'top',
                        template: '<div class="popover"><div class="arrow"></div><div class="popover-content"></div></div>'
                    });
                }
            }
        }

         if (typeof changeCostToTextfield != 'function') {
             function changeCostToTextfield(linkObject, fieldName, costQuestionId, userSetCost, name, fieldId, addRedBorder, value) {
                 if (linkObject) {
                     var row = $(linkObject).closest('tr');
                     var parentTd = $(linkObject).parent();
                     $(parentTd).empty();
                     $(parentTd).append('<input type="text" name="' + fieldName + '" id="' + fieldId + '" value="' + ($(linkObject).text() ? $(linkObject).text().replace(/\s/g,'') : '') + '" class="input-small userGivenCustomCostTotalMultiplier" style="width: 50px;' + (addRedBorder ? ' border: 1px solid red;' : '') + '" />');
                     if ('costPerUnit' === costQuestionId) {
                         var userGivenUnit = row.find('.userGivenUnit').val();
                         if (!userGivenUnit) {
                             userGivenUnit = row.find('.userGivenUnitContainer').text()
                         }
                         $(parentTd).append('<span class="add-on costStructureUnit"> '+ userGivenUnit +'</span>')
                     }
                     $(parentTd).append('<input type="hidden" id="userSetCost' + costQuestionId + '" value="true" class="userSetCost${costQuestionId}" name="' + name + '">')
                 }
             }
         }

    </script>


    </body>
</html>