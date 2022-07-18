<div class="groupEditModal">
    <div class="helpTextGroupEditModal">${message(code: 'groupEdit.help')}</div>
    <g:if test="${additionalQuestions}">
        <g:if test="${selectedResources}">
            <div class="selectedResourcesGroupEdit">
                <a href="javascript:" class="selectedResourcesLink"
                   onclick="toggleDivWithPlusMinusSign(this, '#selectedResourcesGroupEditList')">
                    <i class="fas fa-minus-square"></i>
                    ${message(code: 'selected.resources')} (${selectedCount})
                </a>

                <div id="selectedResourcesGroupEditList" class="alignLeft">
                    <g:each in="${selectedResources}" var="selectedResource">
                        <g:set var="count"
                               value="${resourceOccurrenceCount?.get(selectedResource?.id?.toString()) ?: 0}"/>
                        <g:if test="${selectedResource?.construction}">
                        %{--  Construction resource and its constituents--}%
                            <table class="margin-bottom-2">
                                <tr>
                                    <td class="zeroPadding">
                                        ${count}
                                        <span class="fiveMarginRight">&times;</span>
                                        ${constructionService.renderConstructionTypeImage(selectedResource)}
                                    </td>
                                    <td class="zeroPadding">
                                        <g:render template="/query/resourceName"
                                                  model="[resourceName: optimiResourceService.getResourceDisplayName(selectedResource, false), resource: selectedResource, limit: 50, noHandle: true]"/>
                                        <span class="size-14">
                                            <opt:renderDataCardBtn indicatorId="${indicator?.indicatorId}"
                                                                   resourceId="${selectedResource?.resourceId}"
                                                                   profileId="${selectedResource?.profileId}"
                                                                   childEntityId="${entity?.id}" showGWP="true"
                                                                   manualId="${selectedResource?.isDummy && manualIdsPerResourceUuidMap?.get(selectedResource?.id?.toString())?.size() > 0 ? manualIdsPerResourceUuidMap?.get(selectedResource?.id?.toString())[0] : ''}"
                                                                   leftCoordinate="0.75"/>
                                        </span>
                                    </td>
                                </tr>
                                <g:set var="constituentUuids"
                                       value="${constituentsPerConstructionMap?.get(selectedResource?.id?.toString())}"/>
                                <g:if test="${constituentUuids}">
                                    <g:each in="${constituentUuids}" var="constituentUuid">
                                        <g:set var="constituentResource"
                                               value="${constituentResources?.find({ it.id.toString() == constituentUuid })}"/>
                                        <g:if test="${constituentResource}">
                                            <tr class="margin-bottom-2">
                                                <td class="zeroPadding"></td>
                                                <td class="zeroPadding">
                                                    <g:render template="/query/resourceName"
                                                              model="[resourceName: constituentResource?.uiLabel, resource: constituentResource, limit: 50, noHandle: true]"/>
                                                    <span class="size-14">
                                                        <opt:renderDataCardBtn indicatorId="${indicator?.indicatorId}"
                                                                               resourceId="${constituentResource?.resourceId}"
                                                                               profileId="${constituentResource?.profileId}"
                                                                               childEntityId="${entity?.id}" showGWP="true"
                                                                               leftCoordinate="0.75"/>
                                                    </span>
                                                </td>
                                            </tr>
                                        </g:if>
                                    </g:each>
                                </g:if>
                            </table>
                        </g:if>
                        <g:else>
                        %{--  Normal resource --}%
                            <div class="margin-bottom-2">
                                ${count}
                                <span class="fiveMarginRight">&times;</span>
                                <g:render template="/query/resourceName"
                                          model="[resourceName: optimiResourceService.getResourceDisplayName(selectedResource, false), resource: selectedResource, limit: 50, noHandle: true]"/>
                                <span class="size-14">
                                    <opt:renderDataCardBtn indicatorId="${indicator?.indicatorId}"
                                                           resourceId="${selectedResource?.resourceId}"
                                                           profileId="${selectedResource?.profileId}"
                                                           childEntityId="${entity?.id}" showGWP="true"
                                                           leftCoordinate="0.75"/>
                                </span>
                            </div>
                        </g:else>
                    </g:each>
                </div>
            </div>
        </g:if>
        <table>
            <g:each in="${additionalQuestions}" var="additionalQuestion" status="additionalQuestionIndex">
                <g:if test="${additionalQuestion}">
                    <g:if test="${!additionalQuestion?.hideValueInQuery && 'hidden' != additionalQuestion?.inputType && (!additionalQuestion?.licenseKey || (additionalQuestion?.licenseKey && additionalQuestionLicensed?.get(additionalQuestion?.questionId)))}">
                        <g:set var="mainQuestion"
                               value="${additionalQuestion?.questionId?.equalsIgnoreCase(com.bionova.optimi.construction.Constants.DEFAULT_SERVICELIFE) && questionWithoutPermanentServiceLife ? questionWithoutPermanentServiceLife : questions[0]}"/>

                        <g:if test="${additionalQuestion?.questionId?.equalsIgnoreCase(com.bionova.optimi.core.Constants.LOCAL_COMP_QUESTIONID) && resourceWithLocalCompAvailable}">
                            <g:set var="resource" value="${resourceWithLocalCompAvailable}"/>
                        </g:if>
                        <g:elseif test="${additionalQuestion?.isTransportQuestion && resourceWithSelectableTransport}">
                            <g:set var="resource" value="${resourceWithSelectableTransport}"/>
                        </g:elseif>
                        <g:else>
                            <g:set var="resource" value="${firstNonConstructionResource}"/>
                        </g:else>

                        <tr class="groupEditRow" data-isActivated="false">
                            <td class="groupEditQuestion">${additionalQuestion?.localizedQuestion}</td>
                            <td class="clickToEdit">
                                <a href="javascript:" onclick="handleClickToEdit(this, true)">
                                    ${message(code: 'click.to.edit')}
                                </a>
                            </td>
                            <g:set var="disableEditingThickness" value="${Boolean.FALSE}"/>
                            <g:set var="additionalQuestionAnswer" value=""/>
                            <g:render template="/query/additionalquestion"
                                      model="[indicator                  : indicator, additionalQuestionAnswer: additionalQuestionAnswer, entity: entity, query: query, groupEdit: true, forGroupEditRender: true,
                                              additionalQuestion         : additionalQuestion, mainQuestion: mainQuestion, multipleChoicesAllowed: true, resource: resource, resourceId: resource?.resourceId, removeHiddenCheckboxToggle: true,
                                              allowVariableThickness     : resource?.allowVariableThickness, transportUnit: additionalQuestions.find({ it.questionId.contains('transportDistance') })?.localizedUnit, additionalQuestionShowAsDisabled: additionalQuestionShowAsDisabled,
                                              additionalQuestionResources: additionalQuestionResources, parentEntity: parentEntity, mainSectionId: mainSectionId, localCompensationMethodVersion: localCompensationMethodVersion, defaultLocalCompCountry: defaultLocalCompCountry,
                                              disableEditingThickness    : disableEditingThickness]"/>
                            <td class="forceHide closeTdGroupEdit">
                                <button onclick="handleClickToEdit(this, false)" type="button"
                                        class="close">&times;</button>
                            </td>
                        </tr>
                    </g:if>
                </g:if>
            </g:each>
        </table>
    </g:if>
</div>
