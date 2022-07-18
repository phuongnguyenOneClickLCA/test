<%@ page import="com.bionova.optimi.data.ResourceCache" %>
<%
    def userService = grailsApplication.mainContext.getBean("userService")
%>
<!DOCTYPE html>
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
            <h1><strong><g:message code="pdl.editing_list"/></strong> ${productDataList?.name?.encodeAsHTML()}</h1>
        </div>
    </div>
    <br>
    <div class="container">
        <div class="sectionbody">
            <g:if test="${!admin}">
                <ul class="nav nav-tabs" style="margin-bottom: 5px">
                    <li class="navInfo pull-right" name="totalResources"><a href="#" class="boldText" style="font-size: medium" rel="popover" data-trigger="click" data-content="${message(code:"pdl.resource_cap_explanation")}" data-original-title="" title=""> <span id="totalResources">${totalResources}</span> / ${maxResources} ${message(code:"import.import_resource").capitalize()}<i class="fa fa-question-circle" style="padding-left: 5px" ></i></a></li>
                </ul>
            </g:if>
            <div class="wrapperForNewProductDataLists container">
                <div class="productDataListPageWrapper">
                    <div class="sectionheader">
                        <div class="sectioncontrols pull-right inline-block">
                            <g:if test="${admin}"><h2 style="float: left;"><productDataListRender:inactiveDataWarning productDataList="${productDataList}"/> <productDataListRender:missmatchDataWarning productDataList="${productDataList}"/><span id="currentResources">${resourcesInList}</span> <g:message code="pdl.form_resource_count"/> </h2></g:if>
                            <g:if test="${user?.internalUseRoles && admin}">
                                <opt:link action="productListManagementPage" class="btn"><g:message code="back"/></opt:link>
                            </g:if><g:else>
                                <opt:link action="manageLibraries" params="[accountId:privateProductDataListAccountId]" class="btn"><g:message code="back"/></opt:link>
                            </g:else>
                            <g:if test="${!locked}">
                                <g:actionSubmit class="btn btn-primary" disabled="${locked}" action="save" form="productDataListForm" onclick="doubleSubmitPrevention('productDataListForm');" value="Save"/>
                            </g:if>
                        </div>
                        <h2 class="bold"><g:message code="pdl.product_list_comp_mats"/></h2>
                    </div>
            <g:uploadForm controller="productDataLists" action="save" id="productDataListForm" name="productDataListForm">
                <g:hiddenField name="listId" value="${productDataList?.id}"/>
                <g:if test="${supportedFilters}">
                <div class="container" id="quickFilterOptions">
                    <g:set var="random" value="${UUID.randomUUID().toString()}"/>
                    <div class="screenheader">
                        <table class="quickFilterTable"><tbody>
                            <tr>
                            <td class="pull-right quickFilterFunnel"><i class="fa fa-filter fa-2x" aria-hidden="true"></i>
                                <g:each in="${supportedFilters}" var="queryFilter">
                                    <sec:ifAnyGranted roles="${queryFilter.userClass}">
                                        <g:set var="random" value="${UUID.randomUUID().toString()}"/>
                                        <td id="${queryFilter.resourceAttribute}td" class="quickFilterTableTd text-center"><div class="btn-group quickFilterTableGroup" id="${queryFilter.resourceAttribute}btnGroup" style="display:inline-block">
                                            <span class="quickFilters bold"  id="${queryFilter.resourceAttribute}th"  onclick="quickFilterChoices('${queryFilter.resourceAttribute}td','quickFilter${queryFilter.resourceAttribute}', '${queryFilter.resourceAttribute}', '${queryFilter.localizedName}', null,'${queryId}',null, false, null, null, null, '${classificationParamId ?: null}', true)">${queryFilter.localizedName}</span>
                                            <select class="quickFilterSelect" data-resourceAttribute="${queryFilter.resourceAttribute}" style="width:${queryFilter?.filterWidth ? queryFilter.filterWidth : '170'}px;" onchange="saveUserFilters(false, false, false, true, '${classificationQuestionId}', '${classificationParamId}');" id="quickFilter${queryFilter.resourceAttribute}">
                                            </select>
                                        </div></td>
                                    </sec:ifAnyGranted>
                                </g:each>
                                <td style="vertical-align: middle" id="resetTdForFilter"><a href="javascript:" id="resetAllFilters" class="${locked ? "removeClicksLowOpacity" : ""}"><g:message code="query.reset_filter"/></a></td>
                            </tr>
                            </tbody></table>
                    </div>
                </div>
                </g:if>
                <div class="productDataListPageWrapper">
                    <p class="bold"><g:message code="construction.add_materials"/></p>
                    <div class="input-append">
                    <input type="text" class="autocomplete"  placeholder="${message(code: "typeahead.info")}" data-resourceTableId="componentsTable"
                           data-queryId="${queryId}" data-questionId="${questionIdForResources}" data-sectionId="${sectionIdForResources}" ${locked ? 'disabled="true"' : ''} data-preventDoubleEntries="${Boolean.TRUE}" data-doubleentrywarning="${g.message(code:'query.question.prevent_double_entries')}" data-accountId="${account?.id ?: ""}"><a  tabindex="-1" class="add-on showAllResources"><i class="icon-chevron-down"></i></a>
                    </div>
                    <br>
                    <table class="resourceTable query_resource" id="componentsTable" disabled="${locked}">
                        <thead>
                        <tr id="header" class="${!productDataList ? 'hidden' : ''}">
                            <th><g:message code="query.material" /></th>
                            <th><g:message code="query.quantity"/></th>
                            <g:each in="${additionalQuestions?.sort()}" var="additionalQuestion">
                                <g:if test="${!additionalQuestion.isThicknessQuestion}">
                                    <th>${additionalQuestion?.localizedQuestion}</th>
                                </g:if>
                            </g:each>
                        </tr>
                        </thead>
                        <tbody class="dragginContainer" id="constructionResourcesContainer">
                        <g:if test="${productDataList && productDataList.datasets}">
                            <g:set var="resourceCache" value="${ResourceCache.init(productDataList.datasets)}"/>
                            <g:each in="${productDataList.datasets}" var="dataset">
                                <queryRender:renderResourceRow dataset="${dataset}" resource="${resourceCache.getResource(dataset)}" constructionPage="${true}"
                                                               productDataListPage="${true}" resourceTableId="componentsTable" fieldName="${fieldName}"
                                                               multipleChoicesAllowed="${true}"/>
                            </g:each>
                        </g:if>
                        </tbody>
                    </table>
                </div>
                <g:hiddenField name="classificationQuestionId" value="${classificationQuestionId}"/>
                <g:hiddenField name="classificationParamId" value="${classificationParamId}"/>
                <g:hiddenField name="queryId" value="${queryId}" />
                <g:hiddenField name="privateProductDataListAccountId" value="${privateProductDataListAccountId}" />
                <g:hiddenField name="privateProductDataListCompanyName" value="${privateProductDataListCompanyName}" />
                <g:hiddenField name="totalResources" value="${totalResources}"/>
                <g:hiddenField name="maxResources" value="${maxResources}"/>
                <g:hiddenField name="adminOverride" value="${userService.getSuperUser(user)}"/>
                <g:hiddenField name="admin" value="${admin}"/>
            </g:uploadForm>
            </div>
        </div>
    </div>
        <g:if test="${resourceTypeFilter}">
            <div class="nestedSelectContainer hidden"><ul class="nestedSelect" id="nestedFilter${resourceTypeFilter?.resourceAttribute}"></ul></div>
        </g:if>
        <div style="margin-top: 150px;"></div>
    </div>

    <div class="overlay" id="myOverlay">
        <div class="overlay-content" id="changeResourceNameOverlay"></div>
    </div>

    <script type="text/javascript">
        $('#locked').on('change', function () {
            toggleLockedState();
        });

        $('#toggleNewGroup').bind('click', function () {
            var newProductListGroup = $('#addNewProductListGroup');
            var plusMinus = $('#plusMinus');
            if (!newProductListGroup.is(":visible")) {
                $(newProductListGroup).removeClass("hidden").addClass("inline-block");
                $(plusMinus).toggleClass('icon-plus icon-minus');
            } else {
                $(newProductListGroup).removeClass("inline-block").addClass("hidden");
                $(plusMinus).toggleClass('icon-minus icon-plus');
            }
        });

        function toggleLockedState() {
            $(':input').prop("disabled", false);
            $('a').removeClass("disabled_field");
            $('.containerConstructionDatasets').fadeIn().removeClass('hidden');
        }

        $(function () {
             numericInputCheck();
            <g:if test="${locked}">
                $('.existingResourceRow :input').attr('disabled', 'true');
                $('.existingResourceRow a').addClass('disabled_field');
            </g:if>
             <g:if test="${supportedFilters}">
             quickFiltersSelect2('${message(code: 'quickFilter.placeholder')}');

             $('body').on({
                 click: function () {
                     var select2Box = $('.nestedSelectContainer');
                     $(select2Box).addClass('hidden');

                 }
             });
            $('.quickFilters').trigger('click');

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
                                 classificationParamId: "${classificationParamId}",
                                 unit: unit,
                                 constructionPage: false,
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
                                 return formatAutocompleteRows(suggestion, null, null, questionId);
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
                                         $(that).focus();
                                     }, 150);
                                } else {
                                    if (${admin}) {
                                        var counter = parseInt($("#currentResources").html());
                                        console.log("current used" + counter)
                                    } else {
                                        var counter = parseInt($("#totalResources").html());
                                        console.log("total used"  + counter)
                                    }
                                    var total = ${maxResources};
                                    // console.log("Total Resources: " + counter + " / " + total);
                                    var isSU = ${userService.getSuperUser(user) || userService.getDataManager(user)};
                                    // console.log("SU/Data User: " + isSU);
                                    if (isSU && counter === total && total !== 0) {
                                        Swal.fire({
                                            title: 'Exceeded License Resource Cap',
                                            text: "Are you sure you want to add resource?",
                                            icon: 'warning',
                                            showCancelButton: true,
                                            dangerMode: true,
                                            confirmButtonColor: '#3085d6',
                                            cancelButtonColor: '#d33',
                                            confirmButtonText: 'Yes'
                                        }).then(exceedCap => {
                                            if (exceedCap.value) {
                                                $(this).attr("data-resourceId", suggestion.data.resource);
                                                addResourcesToConstructions($(this).attr("data-resourceId").split('.')[0], $(this).attr('data-resourceTableId'), $(this).attr('data-queryId'), $(this).attr('data-questionId'), $(this).attr('data-sectionId'), $(this).attr('data-preventDoubleEntries'), $(this).attr('data-doubleentrywarning'), true);
                                                $(this).val("")
                                            } else {
                                                Swal.fire({
                                                    icon: 'error',
                                                    title: 'Max resources reached',
                                                    text: 'You\'ve exceeded the maximum resources allowed in your license!'
                                                });
                                            }
                                        })
                                    } else if (isSU || counter < total) {
                                        $(this).attr("data-resourceId", suggestion.data.resource);
                                        addResourcesToConstructions($(this).attr("data-resourceId").split('.')[0], $(this).attr('data-resourceTableId'), $(this).attr('data-queryId'), $(this).attr('data-questionId'), $(this).attr('data-sectionId'), $(this).attr('data-preventDoubleEntries'), $(this).attr('data-doubleentrywarning'), true);
                                        $(this).val("")
                                    } else {
                                        console.log("Failed");
                                        Swal.fire({
                                            icon: 'error',
                                            title: 'Max resources reached',
                                            text: 'You\'ve exceeded the maximum resources allowed in your license!'
                                        });
                                    }
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
             $(".showAllResources").click(function(){
                 var object = $(this).siblings('input:text:first');

                 if (object) {
                     $(object).val("");
                     var options = {
                         minChars: 0,
                         deferRequestBy: 1000
                     };
                     $(object).devbridgeAutocomplete().clearCache();
                     $(object).devbridgeAutocomplete().setOptions(options);
                     $(object).focus();
                 }
             });
             $('#resetAllFilters').on("click", function () {
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
    </script>
    </body>
</html>