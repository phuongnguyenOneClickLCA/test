<%@ page expressionCodec="html" %>
<%@ page import="com.bionova.optimi.core.domain.mongo.Question; org.apache.commons.collections4.CollectionUtils; org.bson.Document; com.bionova.optimi.core.domain.mongo.Resource; com.bionova.optimi.core.domain.mongo.Indicator; com.bionova.optimi.core.Constants" %>
<g:set var="templateId" value="${template?.id?.toString() ?: 'new'}"/>
<g:set var="templateModalForm" value="projectTemplateForm-${templateId}"/>
<g:set var="optimiResourceService" bean="optimiResourceService"/>
<g:set var="indicatorService" bean="indicatorService"/>
<div id="${templateModalForm}" class="modal-content">
    <div class="modal-header">
        <h2 style="display: inline">
            <asset:image src="resultreport.png" class="queryIcon"/>
            ${edit ? message(code: 'editTemplate') : message(code: 'newTemplate')}
        </h2>
    </div>

    <div class="modal-body projectTemplateModalBody">
        <g:hiddenField id="edit-${templateId}-modal" name="edit" value="${edit}"/>
        <g:hiddenField id="accountId-${templateId}-modal" name="accountId" value="${accountId}"/>
        <g:hiddenField id="templateId-${templateId}-modal" name="templateId" value="${template?.id?.toString() ?: ''}"/>
        <g:hiddenField id="isPublic-${templateId}-modal" name="isPublic" value="${isPublic}"/>
        %{-- Template name --}%
        <div style="display: flex">
            <span class="fiveMarginRight" style="padding-top: 5px">
                <b>${message(code: 'templateName')}:</b>
            </span>
            <div>
                <input id="templateName-${templateId}" name="templateName" type="text" value="${template?.name}"/>
                <div class="hidden"><p class="warningRed"></p></div>
            </div>
        </div>
        %{-- Project name --}%
        <div style="display: flex">
            <span class="fiveMarginRight" style="padding-top: 5px">
                <b>${message(code: 'projectName')}:</b>
                <span class="triggerPopoverTemplateModal" data-content="${message(code: 'projectTemplate.name.help')}">
                    <i class="icon-question-sign"></i>
                </span>
            </span>
            <div>
                <input id="projectName-${templateId}" name="projectName" type="text" value="${template?.projectName}"/>
                <div class="hidden"><p class="warningRed"></p></div>
            </div>
        </div>
        %{-- Design name --}%
        <div style="display: flex">
            <span class="fiveMarginRight" style="padding-top: 5px">
                <b>${message(code: 'designName')}:</b>
                <span class="triggerPopoverTemplateModal" data-content="${message(code: 'projectTemplate.name.help')}">
                    <i class="icon-question-sign"></i>
                </span>
            </span>
            <div>
                <input id="designName-${templateId}" name="designName" type="text" value="${template?.designName}"/>
                <div class="hidden"><p class="warningRed"></p></div>
            </div>
        </div>
        %{-- Entity classes --}%
        <div style="display: flex; margin-bottom: 5px">
            <span class="fiveMarginRight" style="padding-top: 5px">
                <b>${message(code: 'importMapper.class')}:</b>
            </span>
            <label>
                <select id="entityClass-${templateId}" class="maskWithSelect2" name="entityClass"
                        onchange="handleEntityClassChangeTemplateModal(this, '#${templateId}${Constants.TOOL_LIST_POSTFIX}', '#${templateId}${Constants.OPEN_INDICATOR_SELECT_POSTFIX}', '#${templateId}${Constants.DEFAULT_BLD_TYPE_POSTFIX}', '#${templateId}${Constants.APPLY_LCA_DEFAULTS_POSTFIX}', '${templateId}${Constants.ENTITY_TEMPLATE_SELECT_POSTFIX}', '#${templateId}${Constants.PRODUCT_TYPE_POSTFIX}')">
                    <option></option>
                    <g:set var="selectedEntityClass"
                           value="${template?.compatibleEntityClass ?: Constants.EntityClass.BUILDING.type}"/>
                    <g:each in="${(List<Resource>) entityClassResources}" var="entityClassResource">
                        <option value="${entityClassResource?.resourceId}" ${entityClassResource?.resourceId?.equalsIgnoreCase(selectedEntityClass) ? 'selected' : ''}>${optimiResourceService.getLocalizedName(entityClassResource)}</option>
                    </g:each>
                </select>
            </label>
        </div>
        %{-- Select default building type --}%
        <div style="display: flex">
            <span class="fiveMarginRight" style="padding-top: 5px">
                <b>${message(code: 'default.building.type')}:</b>
            </span>
            <label>
                <select id="${templateId}${Constants.DEFAULT_BLD_TYPE_POSTFIX}" class="maskWithSelect2AllowClear" name="defaultBuildingType">
                    <option></option>
                    <g:each in="${(List<Document>) buildingTypeResourceGroup}" var="buildingType">
                        <option value="${buildingType?.resourceId}" ${buildingType?.resourceId == template?.buildingTypeResourceId ? 'selected' : ''}>${buildingType?.localizedName}</option>
                    </g:each>
                </select>
            </label>
        </div>
        %{-- Select default country --}%
        <div style="display: flex" class="tenMarginVertical">
            <span class="fiveMarginRight" style="padding-top: 5px">
                <b>${message(code: 'default.country')}:</b>
            </span>
            <label>
                <select id="defaultCountry-${templateId}" class="maskWithSelect2AllowClear" name="defaultCountry">
                    <option></option>
                    <g:each in="${(List<Document>) countriesResourceGroup}" var="country">
                        <option value="${country?.resourceId}" ${country?.resourceId == template?.countryResourceId ? 'selected' : ''}>${country?.localizedName}</option>
                    </g:each>
                </select>
            </label>
        </div>
        <g:if test="${productTypeQuestion}">
            %{-- Select product type --}%
            <div style="display: flex" class="tenMarginVertical">
                <span class="fiveMarginRight" style="padding-top: 5px">
                    <b>${message(code: 'default.product.type')}:</b>
                </span>
                <label>
                    <select id="${templateId}${Constants.PRODUCT_TYPE_POSTFIX}" class="maskWithSelect2AllowClear" name="productType">
                        <option></option>
                        <g:each in="${((Question) productTypeQuestion)?.choices}" var="choice">
                            <option value="${choice?.answerId}" ${choice?.answerId == template?.productType ? 'selected' : ''}>${choice?.localizedAnswer}</option>
                        </g:each>
                    </select>
                </label>
            </div>
        </g:if>
        %{-- Apply LCA defaults --}%
        <div style="display: flex; margin-top: 10px">
            <span class="fiveMarginRight">
                <b>${message(code: 'apply.lca.default')}:</b>
            </span>
            <opt:greenSwitch id="${templateId}${Constants.APPLY_LCA_DEFAULTS_POSTFIX}" name="applyLcaDefaults" value="true" checked="${template?.applyLcaDefaults ? true : false}"/>
        </div>
        %{-- Allow changes --}%
        <div style="display: flex; margin-top: 10px">
            <span class="fiveMarginRight">
                <b>${message(code: 'allow.changes')}:</b>
                <span class="triggerPopoverTemplateModal" data-content="${message(code: 'projectTemplate.lock.help')}">
                    <i class="icon-question-sign"></i>
                </span>
            </span>
            <opt:greenSwitch id="allowChange-${templateId}" name="allowChange" value="true" checked="${template?.allowChange ? true : false}"/>
        </div>
        %{-- Mandatory template (always applied if set) --}%
        <g:if test="${isPublic}">
            <div style="display: flex; margin-top: 10px">
                <span class="fiveMarginRight">
                    <b>${message(code: 'mandatory.template')}:</b>
                    <span class="triggerPopoverTemplateModal" data-content="${message(code: 'mandatory.template.help')}">
                        <i class="icon-question-sign"></i>
                    </span>
                </span>
                <opt:greenSwitch id="isMandatory-${templateId}" name="isMandatory" value="true" checked="${template?.isMandatory ? true : false}"/>
            </div>
        </g:if>
        %{-- Tools list --}%
        <div id="${templateId}${Constants.TOOL_LIST_POSTFIX}">
            <div style="padding-top: 5px">
                <a href="javascript:" class="just_black"
                   onclick="toggleDivWithPlusMinusSign(this, '#toolListPTModal-${templateId}')">
                    <b>${message(code: 'entity.show.designs_tools')}</b>
                    <i class="fas fa-minus-square"></i>
                </a>
            </div>
            <div id="toolListPTModal-${templateId}" style="padding-left: 5px">
                <g:if test="${indicatorsByEntityClass}">
                    <g:each in="${(Map<String, List<Indicator>>) indicatorsByEntityClass}" var="sameEntityClassIndicators">
                        <g:set var="entityClass" value="${(String) sameEntityClassIndicators.key}"/>
                        <g:set var="indicators" value="${(List<Indicator>) sameEntityClassIndicators.value}"/>
                        <table id="toolListTablePTModal-${templateId}-${entityClass}" data-entityClass="${entityClass}" class="indicators hide">
                            <g:if test="${indicators?.size() > 0}">
                                <g:each in="${(List<Indicator>) indicators}" var="indicator">
                                    <g:if test="${!Constants.COMPARE_INDICATORID.equalsIgnoreCase(indicator?.indicatorId) && !indicator?.deprecated}">
                                        <g:set var="benchmarkIndicator" value="${"benchmark".equals(indicator?.visibilityStatus) || "visibleBenchmark".equals(indicator?.visibilityStatus)}" />
                                        <tr class="${benchmarkIndicator ? "hidden" : ""}">
                                            <td>
                                                <opt:checkBox name="indicatorIds" value="${indicator?.indicatorId}"
                                                              id="${templateId}-${indicator?.indicatorId}"
                                                              data-isUsingLCAParameters="${indicator?.isUsingLCAParameters}"
                                                              data-isBenchmarkIndicator="${benchmarkIndicator}"
                                                              data-compatibleIndicatorIds="${indicator.compatibleIndicatorIds ? indicator.compatibleIndicatorIds.join(",") : ""}"
                                                              class="${benchmarkIndicator ? 'hidden' : 'enabledIndicatorIdsForDesign'}"
                                                              checked="${template?.useIndicatorIds?.contains(indicator?.indicatorId) || benchmarkIndicator}"
                                                              onclick="handleProjectTemplateIndicatorCheckBoxClick(this, '${templateId}${Constants.OPEN_INDICATOR_SELECT_POSTFIX}', '${indicator?.indicatorId}', '${indicatorService.getLocalizedName(indicator)}', '${templateId}${Constants.TEMPLATE_EXTENDED_OPTIONS_POSTFIX}', '${templateId}${Constants.TOOL_LIST_POSTFIX}' , '${message(code: "design.conflicting_indicators")}', '${templateId}${Constants.ENTITY_TEMPLATE_SELECT_POSTFIX}', true, '${entityClass}')"/>
                                                <strong>${indicatorService.getLocalizedName(indicator)}</strong>
                                                <g:abbr value="${indicatorService.getLocalizedPurpose(indicator)}" maxLength="100"/>
                                                <g:if test="${indicatorService.getLocalizedPurpose(indicator)?.size() > 100}">
                                                    <a href="javascript:" class="triggerPopoverTemplateModal" data-content="${indicatorService.getLocalizedPurpose(indicator)}">
                                                        <g:message code="see_all"/>
                                                    </a>
                                                </g:if>
                                            </td>
                                        </tr>
                                    </g:if>
                                </g:each>
                            </g:if>
                        </table>
                    </g:each>
                </g:if>
            </div>
        </div>
        <g:render template="/projectTemplate/projectTemplateOptions" model="[template           : template,
                                                                             copyEntitiesMapped : copyEntitiesMapped,
                                                                             basicQuery         : basicQuery,
                                                                             indicatorCompatible: indicatorCompatible,
                                                                             templateModalForm  : templateModalForm,
                                                                             licenses           : licenses]"/>
    </div>

    <div class="modal-footer">
        <a class="btn btn-default" href="javascript:" data-dismiss="modal"><g:message code="cancel"/></a>
        <div class="pull-right">
            <a id="save-${templateId}" href="javascript:" class="btn btn-primary"
               onclick="handleSubmitProjectTemplateModal(this, '${templateModalForm}', '${appendRowTo}', ${edit}, '${modalContainerId}','${Constants.PROJECT_TEMPLATE_TO_LICENSE_TABLE_ID}', 'secondaryOutput', runOnSuccess${templateId}, '${message(code: 'unknownError')}', '${edit ? message(code: 'edit.template.success') : message(code: 'create.template.success')}', '${message(code: 'applyLcaDefaults.required')}', '${templateId}${Constants.TOOL_LIST_POSTFIX}', '${templateId}${Constants.APPLY_LCA_DEFAULTS_POSTFIX}', 'allowChange-${templateId}', 'designName-${templateId}', '${message(code: 'designName.required.template')}', checkInputForAllowChange${templateId}, checkInputForMandatoryTemplate${templateId})">
                ${edit ? message(code: 'save') : message(code: 'create')}
            </a>
        </div>
    </div>
</div>
<script>
    $(function() {
        maskSelectWithSelect2(false, '${message(code: 'select')}', 'maskWithSelect2' )
        keepPopoverOnHover('.triggerPopoverTemplateModal', 150, 450)
        $("#designName-${templateId}").trigger('change')
        <g:if test="${edit}">
        // run resolveIndicatorConflict on edit template modal to disable tools that are incompatible with the selected tools
        resolveIndicatorConflict('${message(code: "design.conflicting_indicators")}', '${templateId}${Constants.TOOL_LIST_POSTFIX}')
        </g:if>
        showCorrectToolListOnTemplateModal('#entityClass-${templateId}', '#${templateId}${Constants.TOOL_LIST_POSTFIX}')
        showBuildingTypeOnTemplateModal('#entityClass-${templateId}', '#${templateId}${Constants.DEFAULT_BLD_TYPE_POSTFIX}')
        showApplyDefaultLCAOnTemplateModal('#entityClass-${templateId}', '#${templateId}${Constants.APPLY_LCA_DEFAULTS_POSTFIX}')
        showProductTypeOnTemplateModal('#entityClass-${templateId}', '#${templateId}${Constants.PRODUCT_TYPE_POSTFIX}')
        disableSaveBtn${templateId}()
    })

    // we have validation of design name => show on frontend
    $("#designName-${templateId}, #projectName-${templateId}, #templateName-${templateId}").on("keyup change",function () {
        const templateNameOk = validateName($("#templateName-${templateId}"), '${message(code:"invalid_character")}')
        const projectNameOk = validateName($("#projectName-${templateId}"), '${message(code:"invalid_character")}', null, true)
        const designNameOk = validateName($("#designName-${templateId}"), '${message(code:"invalid_character")}', null, true)
        if (designNameOk && projectNameOk && templateNameOk) {
            enableSaveBtn${templateId}()
        } else {
            disableSaveBtn${templateId}()
        }
    })

    $('#${templateModalForm} input, #${templateModalForm} select').not("#designName-${templateId}, #projectName-${templateId}, #templateName-${templateId}").on('change', function () {
        if (isSubmitDisabled($('#save-${templateId}'))) {
            enableSaveBtn${templateId}()
            $("#designName-${templateId}").trigger('change')
        }
    })

    function enableSaveBtn${templateId}() {
        enableSubmit(null, 'save-${templateId}')
    }

    function disableSaveBtn${templateId}() {
        disableSubmit($('#save-${templateId}'))
    }

    function runOnSuccess${templateId}() {
        // clear the modal if it's add new template modal
        <g:if test="${createNew}">
        clearAllInputFromForm('${templateModalForm}')
        // check carbon Hero tool checkbox
        checkCheckbox('#${templateModalForm} input[value="xBenchmarkEmbodied"]')
        disableSaveBtn${templateId}()
        // run validate to add red border
        validateName($("#templateName-${templateId}"), '${message(code:"invalid_character")}')
        // set entity class back to building
        $('#entityClass-${templateId}').val('${Constants.EntityClass.BUILDING.type}').change()
        </g:if>
    }

    function checkInputForAllowChange${templateId}() {
        let ok = true
        if (isCheckboxChecked(null, 'allowChange-${templateId}') === false) {
            let countryOk = true
            if (!$('#defaultCountry-${templateId}').val()) {
                countryOk = false
                ok = false
            }
            // default bld type must be selected for buildings
            let bldTypeOk = true
            if ($('#entityClass-${templateId}').val() === '${Constants.EntityClass.BUILDING.type}' && !$('#${templateId}${Constants.DEFAULT_BLD_TYPE_POSTFIX}').val()) {
                bldTypeOk = false
                ok = false
            }

            let productTypeOk = true
            if ($('#entityClass-${templateId}').val() === '${Constants.EntityClass.PRODUCT.type}' && !$('#${templateId}${Constants.PRODUCT_TYPE_POSTFIX}').val()) {
                productTypeOk = false
                ok = false
            }

            if (!ok) {
                let missing = ''
                if (!bldTypeOk) {
                    missing += '<b>${message(code: "default.building.type")}</b>'
                }
                if (!countryOk) {
                    if (missing !== '') {
                        missing += ' ${message(code: "and")} '
                    }
                    missing += '<b>${message(code: "default.country")}</b>'
                }
                if (!productTypeOk) {
                    if (missing !== '') {
                        missing += ' ${message(code: "and")} '
                    }
                    missing += '<b>${message(code: "default.product.type")}</b>'
                }
                errorSwal('warning', '', missing + ' ${message(code:"allowChange.invalid")}', true)
            }
        }
        return ok
    }

    var mandatoryCheckPassed${templateId} = false
    function checkInputForMandatoryTemplate${templateId}() {
        let ok = mandatoryCheckPassed${templateId}
        if (!ok && isCheckboxChecked(null, 'isMandatory-${templateId}') === true) {
            if (isCheckboxChecked(null, 'allowChange-${templateId}') === true) {
                Swal.fire({
                    html:"${message(code: 'mandatory.template.invalidMsg')}",
                    icon: "warning",
                    confirmButtonText: 'Yes',
                    confirmButtonColor: '#73AC00',
                    cancelButtonText: 'Go back',
                    showCancelButton: true,
                    reverseButtons: true,
                    showLoaderOnConfirm: true,
                }).then(result => {
                    if (result.value) {
                        // submit again and bypass this swal check
                        mandatoryCheckPassed${templateId} = true
                        $('#save-${templateId}').click()
                    }
                })
            } else {
                ok = true
            }
        } else {
            ok = true
        }
        return ok
    }
</script>
