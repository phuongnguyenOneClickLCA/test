<g:set var="datasetService" bean="datasetService"/>
<div class="wrapperForSplitAndChange container" id="splitOrChangeTemplate">
<g:if test="${splitView||changeView}">
        <div class="container">
            <a href="javascript:" class="closeOverlay pull-right" onclick="cancelSplitOrChange('myOverlay', 'splitViewOverLay');">&times;</a>

            <h1><g:if test="${splitView}">${message(code:'split_material')}:</g:if><g:elseif test="${changeView}">${message(code: 'change_material')}:</g:elseif> ${dataset?.resource?.staticFullName}</h1>
        </div>
        <g:if test="${supportedFilters}">
            <div class="container filterElementWrapper" id="quickFilterOptionsSplit">
                <g:set var="random" value="${UUID.randomUUID().toString()}"/>
                <div class="screenheader">
                    <table class="quickFilterTable">
                        <tbody>
                        <tr>
                            <td class="pull-right quickFilterFunnel"><i class="fa fa-filter fa-2x" aria-hidden="true"></i>
                            </td>
                            <g:each in="${supportedFilters}" var="queryFilter">
                                <sec:ifAnyGranted roles="${queryFilter.userClass}">
                                    <g:set var="random" value="${UUID.randomUUID().toString()}"/>
                                    <td id="${queryFilter.resourceAttribute}tdSplit" class="quickFilterTableTd text-center"><div class="btn-group quickFilterTableGroup" id="${queryFilter.resourceAttribute}btnGroup" style="display:inline-block">
                                        <span class="quickFiltersSplit bold" onclick="quickFilterChoices('${queryFilter.resourceAttribute}tdSplit','quickFilterSplit${queryFilter.resourceAttribute}', '${queryFilter.resourceAttribute}', '${queryFilter.localizedName}', '${indicatorId}','${query?.queryId}', '${childEntityId}', null, 'splitOrChange', null, '${parentEntity?.id}')"> ${queryFilter.localizedName}</span>
                                        <select class="quickFilterSelectSplit" data-resourceAttribute="${queryFilter.resourceAttribute}" style="width:${queryFilter?.filterWidth ? queryFilter.filterWidth : '160'}px;" onchange="saveUserFilters(${changeView ? true : false}, ${splitView ? true : false});" id="quickFilterSplit${queryFilter.resourceAttribute}">
                                        </select>
                                    </div></td>
                                </sec:ifAnyGranted>
                            </g:each>
                            <td id="resetTdForFilterSplit" style="vertical-align: middle"><a href="javascript:" id="resetAllSplitFilters"><g:message code="query.reset_filter"/></a></td>
                        </tr>
                        </tbody></table>
                </div>
            </div>
        </g:if>

        <div class="container section" id="splitViewContainerHolder">
            <div class="row">
                <div class="span5 splitViewBox">
                    <h1><g:message code="split_view_original_replaced"/>:</h1>
                    <span>${dataset?.resource?.uiLabel}<g:if test="${dataset?.datasetImportFieldsId}">&nbsp;<a href="javascript:" id="${dataset?.manualId}displayFieldSplit" onclick="openCombinedDisplayFields('${dataset?.manualId}','${dataset?.manualId}displayFieldSplit','${entity?.id}','${query?.queryId}','${mainSectionId}','${question?.questionId}')"><i class="far fa-file-alt fa-lg greenInfoBubble" aria-hidden="true"></i></a></g:if></span>
                    <table class="query_resource sortMe" id="${resourceTableId}">
                        <thead>
                        <tr id="${resourceTableId}header">
                            <g:if test="${quantity && (!indicator || !indicator.requireMonthly)}">
                                <th ${question.isThicknessQuestion ? "colspan='3'" : ""} data-sort="int" ><g:message code="query.quantity" /></th>
                            </g:if>
                            <g:each in="${additionalQuestions}" var="additionalQuestion">
                                <g:if test="${additionalQuestion?.isThicknessQuestion && !additionalQuestion?.hideValueInQuery && !'hidden'.equals(additionalQuestion?.inputType) && (!additionalQuestion?.licenseKey || additionalQuestion?.isQuestionLicensed(parentEntity ? parentEntity : entity))}">
                                    <th ${additionalQuestion?.dataSort ? "data-sort='${additionalQuestion.dataSort}'"  : "class=\"no-sort\""}>${additionalQuestion.questionId == 'thickness_mm' || additionalQuestion.questionId == 'thickness_in' ? "&nbsp;"  : additionalQuestion.localizedQuestion}</th>
                                </g:if>
                            </g:each>
                        </tr>
                        </thead><tbody class="dragginContainer" id="${dataset?.manualId}query_resource_body">
                    <queryRender:renderResourceRow splitPage="${Boolean.TRUE}" changeView="${changeView}" preventChanges="${!changeView}"
                                                   modifiable="${changeView}" dataset="${dataset}" resource="${resource}" splitViewAllowed="${Boolean.FALSE}"
                                                   disableThickness="${Boolean.TRUE}" showGWP="${showGWP}" unitSystem="${user?.unitSystem}"
                                                   multipleChoicesAllowed="${true}" fieldName="${fieldName}" indicator="${indicator}"
                                                   resourceTableId="${resourceTableId}" parentEntity="${parentEntity}" entity="${entity}" />
                    </tbody>
                    </table>

                    <div id="originalSourceListing" style="position: relative;"></div>

                    <g:if test="${!changeableSimilarDatasets.isEmpty()}">
                        <h4 style="margin-top: 10px;"><a href="javascript:" id="changeSimilarBtn" style="text-decoration : none;"><i id="changeSimilarToggler" class='fa fa-plus oneClickColorScheme'></i> <g:message code ="change_similar_row"/></a><asset:image src="img/icon-warning.png" style="max-width:20px"/></h4>
                        <div class="hidden" id="changeSimilarDiv">
                            <p>Found similar resource rows on your query. Check these if you want to apply the same change to multiple rows at the same time.</p>
                            <div style="overflow-y:scroll; height:300px;">
                                <table class="table table-striped table-condensed">
                                    <g:each in="${changeableSimilarDatasets}" var="dataset">
                                        <g:set var="uilable" value="${dataset.resource?.uiLabel}" />
                                        <tr>
                                            <td><input type="checkbox" name="changeSimilarDataset" value="${dataset.manualId}" class="changeSimilarDataset" style="width: 15px; !important; height: 15px; !important;"></td>
                                            <td>${uilable?.length() > 40 ? g.abbr(value: uilable, maxLength: 40) : uilable}</td>
                                            <td>${dataset.quantity} ${dataset.userGivenUnit}</td>
                                            <td>${dataset.additionalQuestionAnswers?.get("comment") ?: ""}<g:if test="${dataset.datasetImportFieldsId}">&nbsp;<a href="javascript:" id="${dataset.manualId}displayFieldSplitPage" onclick="openCombinedDisplayFields('${dataset.manualId}','${dataset.manualId}displayFieldSplitPage','${entity?.id}','${dataset.queryId}','${dataset.sectionId}','${dataset.questionId}')"><i class="far fa-file-alt fa-lg greenInfoBubble" aria-hidden="true"></i></a></g:if></td>
                                        </tr>
                                    </g:each>
                                </table>
                            </div>
                        </div>
                    </g:if>
                </div>
                <div class="span1">
                    <div class="text-center" style="margin-top:20px;">
                        <i class="fa fa-arrow-right oneClickColorScheme fa-4x" aria-hidden="true"></i>
                    </div>
                </div>

                <div class="hidden span6 splitViewBox" id="splitOrChangeView">
                    <div class="splitOrChangeView">
                        <g:form action="splitOrChangeDatasetForm" name="splitOrChangeForm" id="splitOrChangeForm">
                            <g:hiddenField name="originalDatasetId" value="${dataset?.manualId}" />
                            <g:hiddenField name="entityId" value="${parentEntityId}" />
                            <g:hiddenField name="queryId" value="${query?.queryId}" />
                            <g:hiddenField name="childEntityId" value="${childEntityId}" />
                            <g:hiddenField name="indicatorId" value="${indicatorId}" />
                            <g:hiddenField name="sectionId" value="${section?.sectionId}" />
                            <g:hiddenField name="questionId" value="${question?.questionId}" />
                            <g:if test="${changeView}">
                                <input type="hidden" id="changeView" value="true" name="changeView">
                            </g:if>
                            <g:elseif test="${splitView}">
                                <input type="hidden" id="splitView" value="true" name="splitView">
                            </g:elseif>
                            <h1><g:message code="split_new_material"/>:</h1>
                            <div class="input-append" id="resourceBox${question?.questionId}Split">
                                <g:set var="selectId"  value="changeTableSelect" />
                                <input type="text" name="resourceId" id="${selectId}" class="autocomplete changeAutocomplete resourceSelect"
                                       autocomplete="off" ${changeView ? "data-persistChangedManualId=\"${dataset?.manualId}\"" : ""} data-original-title="" placeholder="${query?.localizedtypeaheadSearchText ? query.localizedtypeaheadSearchText : message(code: "typeahead.info")}"
                                       data-parentEntityId="${parentEntity?.id}" data-unit="${dataset.userGivenUnit}" data-indicatorId="${indicator?.indicatorId}" data-originalDatasetAnswers="${changeView ? dataset?.manualId : ''}"
                                       data-queryId="${query?.queryId}" data-resourceId="${dataset?.resourceId}" data-sectionId="${mainSectionId}" data-entityId="${entity?.id}" data-selectId="${selectId}" data-resourceTableId="changeTable" data-fieldName="${fieldName}" data-preventDoubleEntries="${preventDoubleEntries}" data-doubleEntryWarning="${g.message(code:'query.question.prevent_double_entries')}" data-showGWP="${showGWP}"
                                       data-accountId="${account?.id ?: ""}" data-originalQuantity="${dataset.originalAnswer?.replaceAll(",", ".")?.trim()?.isNumber() ? dataset.originalAnswer.replaceAll(",", ".").trim().toDouble() : dataset.quantity}" data-resourceType="${dataset?.resource?.resourceType}" data-subType="${dataset?.resource?.resourceSubType}" data-originalThickness="${datasetService.getThickness(dataset?.additionalQuestionAnswers)}" data-originalUnit="${dataset?.userGivenUnit}" data-questionId="${question?.questionId}"><a tabindex="-1" id="${selectId}showAll" class="add-on showAllResources " ${disabledAttribute ? "" : "onclick=\"showAllQueryResources(this,'${question?.questionId}','${message(code: 'query.resource.popover')}');\""} ><i class="icon-chevron-down"></i></a><a href="javascript:" class="dataHitsButton add-on hidden" id="${question.questionId}dataHits"></a>
                            </div>

                            <table id="changeTable" class="query_resource">
                                <thead>
                                <tr id="changeTableheader" style="display:none;">
                                    <g:if test="${quantity && (!indicator || !indicator.requireMonthly)}">
                                        <th ${question.isThicknessQuestion ? "colspan='3'" : ""} data-sort="int" ><g:message code="query.quantity" /></th>
                                    </g:if>
                                    <g:each in="${additionalQuestions}" var="additionalQuestion">
                                        <g:if test="${additionalQuestion?.isThicknessQuestion && !additionalQuestion?.hideValueInQuery && !'hidden'.equals(additionalQuestion?.inputType) && (!additionalQuestion?.licenseKey || additionalQuestion?.isQuestionLicensed(parentEntity ? parentEntity : entity))}">
                                            <th ${additionalQuestion?.dataSort ? "data-sort='${additionalQuestion.dataSort}'"  : "class=\"no-sort\""}>${additionalQuestion.questionId == 'thickness_mm' || additionalQuestion.questionId == 'thickness_in' ? "&nbsp;"  : additionalQuestion.localizedQuestion}</th>
                                        </g:if>
                                    </g:each>
                                </tr>
                                </thead>
                            </table>
                            <g:if test="${changeView}">
                                <div id="newSourceListing">
                                </div>
                                <g:if test="${dataset?.construction}">
                                    <div id="preserveConstructionInfoDiv">
                                        <hr class="divider" style="margin: 5px 1px;">
                                        <input style="margin-left: 8px;" type="checkbox" id="preserveConstructionInfo" name="preserveConstructionInfo" value="true"><label style="display: inline-block; margin-left: 5px; vertical-align: text-top;" for="preserveConstructionInfo"><g:message code="splitandchange.preserve_info"/></label><br>
                                    </div>
                                </g:if>
                            </g:if>

                            <g:if test="${splitView}">
                                <div id="appendSplitsHere">
                                </div>
                                <div class="inliner bold" style="margin-top:15px; margin-bottom:5px;"><g:message code="totalScore"/>: <span id="totalSplitPool" class="inliner">0</span> <span>%</span> </div>
                                <div>
                                    <a href="javascript:" class="hidden hightLightOnHover" id="moreSplits"><i class="fa fa-plus fa-2x oneClickColorScheme"></i> </a>
                                </div>
                            </g:if>
                        </g:form>
                    </div>
                </div>
            </div>
        </div>
        <div class="buttonsContainer container text-center">
            <div class="btn-group">
                <a href="javascript:" id="submitChangeOrSplit" class="btn btn-primary removeClicks" onclick="submitSplitOrChange('${dataset?.manualId}splitOriginal', true);"><g:message code="save"/></a>
                <a href="javascript:" class="btn" onclick="cancelSplitOrChange('myOverlay', 'splitViewOverLay');"><g:message code="cancel"/></a>
            </div>
        </div>
        <g:if test="${resourceTypeFilter}">
            <div class="nestedSelectContainer hidden"><ul class="nestedSelect" id="nestedFilter${resourceTypeFilter?.resourceAttribute}"></ul></div>
        </g:if>
        <asset:javascript src="layout.js"/>
        <asset:javascript src="jquery.dataTables.min.js"/>
        <asset:javascript src="jquery.dataTables.bootstrap-mod.js"/>
        <asset:javascript src="bootstrap-dropdown.js"/>
        <asset:javascript src="bootstrap-collapse.js"/>
        <asset:javascript src="bootstrap-affix.js"/>
        <asset:javascript src="360optimi.js"/>
        <asset:javascript src="dragula.min.js" />

        <script type="text/javascript">
            var originalDatasetElement = "#shareOfDataset${dataset?.manualId}";
            var numberOfSplitMaterials = 0;
            var totalPool = 100;

            $('.changeSimilarDataset').on('click', function() {
                const manualId = $(this).val();
                if ($(this).is(':checked')) {
                    $("#splitOrChangeForm").append('<input type="hidden" id="'+manualId+'changeSimilar" name="changeSimilarDataset" value="'+manualId+'" />');
                } else {
                    $("#"+manualId+"changeSimilar").remove();
                }
            });

            $('#changeSimilarBtn').on('click', function () {
                var newConstructionGroup = $('#changeSimilarDiv');
                var plusMinus = $('#changeSimilarToggler');
                if (!newConstructionGroup.is(":visible")) {
                    $(newConstructionGroup).slideDown().removeClass('hidden');
                    $(plusMinus).toggleClass('fa-plus fa-minus');
                } else {
                    $(newConstructionGroup).slideUp().addClass('hidden');
                    $(plusMinus).toggleClass('fa-minus fa-plus');
                }
            });


            $(function (){
                var $parent = $('#splitOrChangeForm');

                $parent.on('click', '.highlighted-background', function() {
                    $(this).removeClass('highlighted-background');
                });

                $parent.on('click', '.newResourceRow', function() {
                    $(this).removeClass('newResourceRow');
                });

                $('#splitOrChangeView').fadeIn().removeClass('hidden');
                renderSourceListingToAnyElement("${dataset?.resourceId}", "${dataset?.manualId}info", "${indicator?.indicatorId}", "${question?.questionId}", "${dataset?.profileId}", "${childEntityId}", "${showGWP}", null, null, null, null, null, null, null, null, "#originalSourceListing", 'absolute', 250, 280, 490, 350);
                <g:if test="${splitView}">
                $('#submitChangeOrSplit').removeClass('removeClicks');
                var queryString = 'originalDatasetId=${dataset?.manualId}' + '&drawOriginalDataset=true' + '&queryId=${query?.queryId}' + '&childEntityId=${childEntityId}' + '&parentEntityId=${parentEntityId}' + '&indicatorId=${indicator?.indicatorId}' + '&questionId=${question?.questionId}' + '&sectionId=${section?.sectionId}' + '&accountId=${account?.id ?: ''}';
                var resourceBox =  $('#resourceBox${question?.questionId}Split');
                $(resourceBox).hide();
                splitDataset(queryString);

                $('#moreSplits').on('click', function () {
                    var queryString = 'originalDatasetId=${dataset?.manualId}'+ '&queryId=${query?.queryId}' + '&childEntityId=${childEntityId}' + '&parentEntityId=${parentEntityId}' + '&indicatorId=${indicator?.indicatorId}' + '&questionId=${question?.questionId}' + '&sectionId=${section?.sectionId}' + '&dynamicAdd=true' + '&accountId=${account?.id ?: ''}';
                    splitDataset(queryString, true);
                });
                </g:if>
                <g:else>
                initSplitAutoCompletes();
                </g:else>

                <g:if test="${supportedFilters}">
                quickFiltersSelect2('${message(code: 'quickFilter.placeholder')}', '.quickFilterSelectSplit');
                $('.quickFiltersSplit').trigger('click');

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
                                nestedFilterChoices('nestedFilter${resourceTypeFilter.resourceAttribute}', '${resourceTypeFilter.resourceAttribute}', resourceType, '${query?.queryId}', '${indicator?.indicatorId}', true, '${parentEntityId}', null, highlighted_item);
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
            });

            $('#resetAllSplitFilters').on("click", function() {
                $('.quickFilterSelectSplit').empty();
                $(this).hide();
                $('.quickFilterTableTd').addClass("removeClicks");
                $('.quickFilterTableTd').removeClass('filterChosenHighlight');

                $('#resetTdForFilterSplit').append('<i id="loadingReset" class="fa fa-cog fa-2x fa-spin primary"></i>');
                saveUserFilters(${changeView ? true : false}, ${splitView ? true : false});
                setTimeout(function () {
                    $('.quickFiltersSplit').trigger("click");
                    $('#loadingReset').remove();
                    $('#resetAllSplitFilters').show();
                    $('.quickFilterTableTd').removeClass("removeClicks");


                },1000);
            });
        </script>
</g:if>
<g:else>
    Unable to render split or change at this time.
</g:else>
</div>


