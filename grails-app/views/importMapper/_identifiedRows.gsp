<g:set var="optimiResourceService" bean="optimiResourceService"/>
<g:if test="${datasets}">
    <g:set var="showOclIdWarning" value="${false}"/>
    <g:set var="resourceCache" value="${com.bionova.optimi.data.ResourceCache.init(datasets)}"/>
    <g:each in="${datasets}" var="dataset">
        <g:set var="isOclIdPresent" value="${dataset?.recognizedFromShort?.trim()?.equalsIgnoreCase(
                g.message(code: 'importMapper.trainingData.recognized.weigth6.short')?.trim()
        )}"/>
        <g:if test="${!showOclIdWarning && isOclIdPresent}">
            <g:set var="showOclIdWarning" value="${true}"/>
        </g:if>

        <g:set var="datasetResource" value="${resourceCache.getResource(dataset)}"/>
        <g:set var="detailsLink" value="${dataset.importDisplayFields?.get(detailsKey)}"/>
        <tr id="identified${dataset.manualId}" data-materialkey="${detailsLink}"
            class="${!dataset.answerIds?.get(0) ? 'notQuantified' : ''}">
            <input type="hidden" class="noTrainingDataset" value="true" name="noTrainingDataset.${dataset.manualId}"
                   id="noTrainingDataset${dataset.manualId}"/>
            <g:set var="resourceName" value="${optimiResourceService.getUiLabel(datasetResource)}"/>
            <td class="tdalignmiddle" style="min-width: 280px !important;"><%--
                            --%><input type="hidden" name="questionId.${dataset.manualId}"
                                       value="${dataset.questionId}"/><%--
                            --%><span data-content="${detailsLink ?: dataset.ifcMaterialValueOrg?.toLowerCase() ?: ''}"
                                      rel="popover" data-placement="top" data-trigger="hover">
                <g:abbr value="${org.springframework.web.util.HtmlUtils.htmlUnescape(detailsLink ?: dataset.ifcMaterialValueOrg?.toLowerCase() ?: "")}"
                        maxLength="40"/>
            </span>
                <g:if test="${!dataset.allowMapping && dataset.quantity && dataset.userGivenUnit}">
                    <a href="javascript:" class="pull-right" rel="popover" data-trigger="hover" data-html="true"
                       data-content="${message(code: 'importMapper.resolver.generic_datapoint_warning')}"
                       id="${dataset.manualId}genericDataWarning">
                        <asset:image src="img/icon-warning.png" style="max-width:16px"/>
                    </a>
                </g:if><%--
                            --%><g:if test="${dataset.allImportDisplayFields}">&nbsp;
                <a href="javascript:" id="${dataset.manualId}info"
                   onclick="openCombinedDisplayFieldsResolver('${dataset.manualId}', '${dataset.manualId}info')"
                   class="pull-right"><i class="far fa-file-alt fa-lg greenInfoBubble" aria-hidden="true"></i></a>
            </g:if><%--
                            --%><queryRender:importMapperBimCheckerErrors dataset="${dataset}"/><%--

                        --%></td>
            <td class="tdalignmiddle">
                <span class="setHiddenValueForSort hidden"></span>
                <g:set var="ifcClass" value="${dataset.trainingData?.get("CLASS")?.toUpperCase()}"/>
                <g:set var="classRemapChoices" value="${classRemapChoice?.allowedClassRemappings}"/>
                <g:if test="${ifcClass && classRemapChoices}">
                    <select data-datasetManualId="${dataset.manualId}" class="classRemapSelect"
                            name="classRemapSelectDropdown">
                        <g:if test="${!classRemapChoices*.toUpperCase().contains(ifcClass)}">
                            <option value="${ifcClass}" selected><g:message code="importMapper.OTHER"/></option>
                        </g:if>
                        <g:each in="${(List<String>) classRemapChoices}" var="choise">
                            <option value="${choise}" ${choise.toUpperCase().equals(ifcClass) ? 'selected' : ''}>${choise.toUpperCase()}</option>
                        </g:each>
                    </select>
                </g:if>
                <g:elseif test="${ifcClass}">
                    <g:abbr value="${ifcClass}" maxLength="20"/>
                </g:elseif>
            </td>
            <td class="tdalignmiddle">
                <span class="setHiddenValueForSort hidden"></span>
                <input type="text" class="resolverCommentField"
                       value="${dataset.additionalQuestionAnswers?.get("comment")}" name="comment.${dataset.manualId}"/>
            </td>

            <g:if test="${additionalQuestions}"><%--
                            --%><g:each in="${additionalQuestions}" var="additionalQuestion"
                                        status="additionalQuestionIndex"><g:if
                        test="${!additionalQuestion.inGroupedQuestions()}"><%--
                                --%><g:if
                            test="${additionalQuestion?.questionId && dataset.additionalQuestionAnswers?.get(additionalQuestion?.questionId)}"><%--
                                    --%><g:set var="additionalQuestionAnswer"
                                               value="${dataset.additionalQuestionAnswers?.get(additionalQuestion.questionId)}"/><%--
                                --%></g:if><%--
                                --%><g:else><%--
                                    --%><g:set var="additionalQuestionAnswer" value=""/><%--
                                --%></g:else><%--
                                --%><g:render template="/query/additionalquestion"
                                              model="[indicator               : indicator,
                                                      additionalQuestionAnswer: additionalQuestionAnswer,
                                                      entity                  : entity,
                                                      query                   : query,
                                                      mainSectionId           : dataset.sectionId,
                                                      additionalQuestion      : additionalQuestion,
                                                      mainQuestion            : query.getQuestionsBySection(dataset.sectionId)?.find({ it.questionId == dataset.questionId }), resource: datasetResource, resourceId: dataset.resourceId,
                                                      allowVariableThickness  : datasetResource?.allowVariableThickness, transportUnit: additionalQuestions.find({ it.questionId.contains('transportDistance') })?.localizedUnit, disableThickness: dataset.userGivenUnit && !'m2'.equalsIgnoreCase(dataset.userGivenUnit),
                                                      datasetId               : dataset.manualId,
                                                      trId                    : 'identified' + dataset.manualId, importMapperAdditionalQuestion: true, tableFormat: true]"/><%--
                            --%></g:if></g:each><%--
                        --%></g:if>

            <td class="tdalignmiddle">
                <g:set var="thickness"
                       value="${dataset.additionalQuestionAnswers?.get("thickness_mm") ? formatNumber(number: dataset.additionalQuestionAnswers.get("thickness_mm"), format: "###.##") : ''}"/>
                <g:if test="${dataset.quantity == null}">
                    <input type="text" value="" name="quantity.${dataset.manualId}" id="quantity${dataset.manualId}"
                           class="numeric input-mini"/>
                </g:if>
                <g:else>
                    <g:set var="formattedQuantity"
                           value="${dataset.quantity > 10 ? formatNumber(number: dataset.quantity, format: "#") : formatNumber(number: dataset.quantity, format: "###.##")}"/><%--
                               --%><span class="quantityContainer hidden"><%--
                                --%>${formattedQuantity} ${dataset.userGivenUnit ?: ''}${dataset.area_m2 ? " / " + "${formatNumber(number: dataset.area_m2, format: "#")} M2" : ''}${thickness ? ' / ' + thickness + ' mm' : ''}<%--
                               --%></span>
                    <g:if test="${dataset.area_m2 || dataset.volume_m3 || dataset.mass_kg}">
                        <select data-datasetManualId="${dataset.manualId}" class="resolvedUnitSelect"
                                name="resolvedUnitSelectDropdown">
                            <option selected="selected" data-quantity="${dataset.quantity}"
                                    value="${dataset.userGivenUnit ?: ''}">${formattedQuantity} ${dataset.userGivenUnit ?: ''}</option>
                            <g:if test="${dataset.area_m2}">
                                <option data-quantity="${dataset.area_m2}"
                                        value="m2">${formatNumber(number: dataset.area_m2, format: "#")} m2${thickness ? ' / ' + thickness + ' mm' : ''}</option>
                            </g:if>
                            <g:if test="${dataset.volume_m3}">
                                <option data-quantity="${dataset.volume_m3}"
                                        value="m3">${formatNumber(number: dataset.volume_m3, format: "#")} m3</option>
                            </g:if>
                            <g:if test="${dataset.mass_kg}">
                                <option data-quantity="${dataset.mass_kg}"
                                        value="kg">${formatNumber(number: dataset.mass_kg, format: "#")} kg</option>
                            </g:if>
                        </select>
                    </g:if>
                    <g:else>
                        ${formattedQuantity} ${dataset.userGivenUnit ?: ''}${thickness ? ' / ' + thickness + ' mm' : ''}
                    </g:else>
                </g:else>
            </td>
            <td class="tdalignmiddle" ${dataset.percentageOfTotal && dataset.percentageOfTotal > 1 ? ' style=\"font-weight: bold;\"' : ''}>
                ${dataset.percentageOfTotal != null ? formatNumber(number: dataset.percentageOfTotal, format: "###.##") + ' %' : ''}</td>
            <g:set var="datasetResourceInfo" value="${UUID.randomUUID().toString()}"/>
            <td id="tooltipCell${dataset.manualId}" class="tdalignmiddle">
                <span class="setHiddenValueForSort hidden">${optimiResourceService.getUiLabel(datasetResource)}</span>

                <div class="input-append" id="identifiedMapping${dataset.manualId}">
                    <g:set var="showDataForUnitText" value="${(dataset?.userGivenUnit) ?
                            message(code: "importMapper.showDataForUnit", args: [dataset?.userGivenUnit]) : null}"/>
                    <input type="text" value="${optimiResourceService.getUiLabel(datasetResource)}"
                           class="autocomplete mediumAutocompleteWidth remapAlreadyMapped ${isOclIdPresent ? 'retrainForThisUser' : ''}"
                           name="input.${dataset.manualId}"
                           data-unit="${dataset.userGivenUnit}"
                           placeholder="${message(code: 'importMappertypeahead.info')}"
                           class="input-xlarge"
                           autocomplete="off"
                           data-hasUserInput="false"
                           data-accountId="${account?.id ?: ""}"
                           data-indicatorId="${indicator?.indicatorId}"
                           data-resourceGroups="${resourceGroups}"
                           data-targetBubble="${datasetResourceInfo}info"
                           data-parentId="${entity?.id}"
                           data-entityId="${entityId}"
                           data-queryId="${dataset.queryId}"
                           data-sectionId="${dataset.sectionId}"
                           data-questionId="${dataset.questionId}"
                           data-applicationId="${applicationId}"
                           data-datasetId="${dataset.manualId}"
                           data-importMapperId="${importMapperId}"
                           onchange="removeBorder(this, '${inputWidth}');"
                           oninput="setHasUserInput(this);"
                           id="autocomplete${dataset.manualId}" ${disabledAttribute}>
                    <a tabindex="-1" href="javascript:" class="add-on showAllResources"
                       onclick="showAllImportMapperResources(this, '${resourceGroups}', true, '${showDataForUnitText}');hoverDetails('${idOfDetails}');">
                        <i class="icon-chevron-down"></i>
                    </a>
                    <input type="hidden" class="userGivenUnitContainer" name="userGivenUnit.${dataset.manualId}"
                           id="userGivenUnit${dataset.manualId}" value="${dataset.userGivenUnit}"/>
                    <input type="hidden" class="userGivenQuantityContainer" name="userGivenQuantity.${dataset.manualId}"
                           id="userGivenQuantity${dataset.manualId}" value="${dataset.quantity}"/>
                    <input type="hidden" class="resourceIdContainer identifiedResourceIdContainer"
                           name="identifiedResourceId.${dataset.manualId}"
                           id="identifiedResourceId${dataset.manualId}" value="${dataset.resourceId}"/>
                    <input type="hidden" class="resourceUnitsContainer"
                           value="${datasetResource?.combinedUnits?.join(",")}"/>
                </div>

            </td>
            <td class="tdalignmiddle">
                <opt:renderDataCardBtn
                        targetBubbleId="${datasetResourceInfo}info"
                        indicatorId="${indicator?.indicatorId}"
                        resourceId="${dataset?.resourceId}"
                        questionId="${dataset?.questionId}"
                        profileId="${dataset?.profileId ? dataset.profileId : datasetResource?.profileId}"
                        childEntityId="${entity?.id}" showGWP="true" infoId="${datasetResourceInfo}"/>
            </td>
            <td class="tdalignmiddle mappingBasis">${dataset.recognizedFromShort} <i
                    class="fa fa-question-circle ${dataset.recognizedFrom && dataset.recognizedFrom.size() > 0 ? '' : 'hidden'}"
                    rel="popover" data-trigger="hover" data-content="${dataset.recognizedFrom}"></i></td>
            <td class="tdalignmiddle"><input style="margin-right: 30px;" type="checkbox"
                                             name="composite.${dataset.manualId}"/><a href="javascript:"
                                                                                      onclick="removeElementById('identified${dataset.manualId}');
                                                                                      hideResolverTable('identifiedTable');
                                                                                      recalculateIdentified();"><strong>${message(code: 'delete')}</strong>
            </a></td>
            <td class="tdalignmiddle">
                <g:if test="${dataset.allowMapping}">
                    <input style="${isOclIdPresent ? '' : 'display: none;'}" type="checkbox"
                           onclick="resolveAllowMappingForRow(this, 'rememberMapping.${dataset.manualId}')"
                           class="rememberMappingCheckbox" value="true" checked/>
                    <input type="hidden" class="mappingDisabledByUser" id="rememberMapping.${dataset.manualId}"
                           name="rememberMapping.${dataset.manualId}" value="true"/>
                </g:if>
                <g:else>
                    <input type="hidden" class="mappingDisabledByUser" id="rememberMapping.${dataset.manualId}"
                           name="rememberMapping.${dataset.manualId}" value="false"/>
                </g:else>
            </td>
            <td class="tdalignmiddle">&nbsp;</td>
        </tr>
    </g:each>
    <script type="text/javascript">
        $(document).ready(function () {
            if ("${showOclIdWarning}" === "true") {
                enableSaveButtonForIdentifiedData();
                document.getElementById('memorizeWarning').removeAttribute("hidden");
            }
        });
    </script>
</g:if>