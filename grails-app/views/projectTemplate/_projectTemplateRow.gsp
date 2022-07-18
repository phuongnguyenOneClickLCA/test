<%@ page expressionCodec="html" %>
<%@ page import="com.bionova.optimi.core.domain.mongo.Question; com.bionova.optimi.core.Constants; com.bionova.optimi.core.domain.mongo.Resource" %>
<g:set var="templateId" value="${template?.id?.toString()}"/>
<g:set var="optimiResourceService" bean="optimiResourceService"/>
<g:set var="indicatorService" bean="indicatorService"/>
<g:set var="projectTemplateService" bean="projectTemplateService"/>
<tr id="${templateId}" class="projectTemplateRow">
    <td class="w-10"><b>${template?.name}</b></td>
    <td class="w-25">
        <g:if test="${template?.projectName}">
            <div >
                <div><b>${message(code: 'projectName')}</b></div>
                <div>
                    <i class="fa fa-feather-alt greenTick" aria-hidden="true"></i>
                    ${template?.projectName}
                </div>
            </div>
        </g:if>
        <g:if test="${template?.designName}">
            <div style="margin: 8px 0">
                <div><b>${message(code: 'designName')}</b></div>
                <div>
                    <i class="fa fa-feather-alt greenTick" aria-hidden="true"></i>
                    ${template?.designName}
                </div>
            </div>
        </g:if>
    </td>
    <td>${optimiResourceService.getLocalizedName(((List<Resource>) entityClassResources)?.find{ it.resourceId.equalsIgnoreCase(template?.compatibleEntityClass) })}</td>
    <td>
        %{-- Tools--}%
        <g:each in="${projectTemplateService.getIndicators(template?.useIndicatorIds)?.sort({indicatorService.getLocalizedName(it)})}" var="indicator">
            <div>
                <i class="fa fa-check greenTick" aria-hidden="true"></i>
                <span>${indicatorService.getLocalizedName(indicator)}</span>
            </div>
        </g:each>
    </td>
    <td class="w-25">
        <table>
            %{-- Default building type--}%
            <g:if test="${optimiResourceService.getLocalizedName(projectTemplateService.getBuildingTypeResource(template?.buildingTypeResourceId))}">
                <tr class="w-100">
                    <td class="noBorder noPadding noBackGround w-25">
                        <b>${message(code: 'default.building.type')}</b>
                    </td>
                    <td class="noBorder noPadding noBackGround tenPaddingLeft w-75">
                        <span class="">
                            ${optimiResourceService.getLocalizedName(projectTemplateService.getBuildingTypeResource(template?.buildingTypeResourceId))}
                        </span>
                    </td>
                </tr>
            </g:if>
            %{-- Default country --}%
            <g:if test="${optimiResourceService.getLocalizedName(projectTemplateService.getCountryResource(template?.countryResourceId))}">
                <tr class="w-100">
                    <td class="noBorder noPadding noBackGround w-25">
                        <b>${message(code: 'default.country')}</b>
                    </td>
                    <td class="noBorder noPadding noBackGround tenPaddingLeft w-75">
                        <span class="">
                            ${optimiResourceService.getLocalizedName(projectTemplateService.getCountryResource(template?.countryResourceId))}
                        </span>
                    </td>
                </tr>
            </g:if>
            %{-- Default product Type --}%
            <g:if test="${template?.productType}">
                <tr class="w-100">
                    <td class="noBorder noPadding noBackGround w-25">
                        <b>${message(code: 'default.product.type')}</b>
                    </td>
                    <td class="noBorder noPadding noBackGround tenPaddingLeft w-75">
                        <span class="">
                            ${productTypeQuestion?.choices?.find { it.answerId == template?.productType }?.localizedAnswer}
                        </span>
                    </td>
                </tr>
            </g:if>
            %{-- Start by copying a template --}%
            <g:if test="${template?.childEntityId}">
                <tr class="w-100">
                    <td class="noBorder noPadding noBackGround w-25">
                        <b>${message(code: 'copy.from.design')}</b>
                    </td>
                    <td class="noBorder noPadding noBackGround tenPaddingLeft w-75">
                        <span class="">
                            <projectTemplateRender:getEntityTemplateName template="${template}"/>
                        </span>
                    </td>
                </tr>
            </g:if>
            %{-- Start with tool--}%
            <g:if test="${indicatorService.getLocalizedName(projectTemplateService.getSelectedIndicator(template?.openIndicator))}">
                <tr class="w-100">
                    <td class="noBorder noPadding noBackGround w-25">
                        <b>${message(code: 'start.with.tool')}</b>
                    </td>
                    <td class="noBorder noPadding noBackGround tenPaddingLeft w-75">
                        <span class="">
                            ${indicatorService.getLocalizedName(projectTemplateService.getSelectedIndicator(template?.openIndicator))}
                        </span>
                    </td>
                </tr>
            </g:if>
            %{-- Start with data input--}%
            <g:if test="${projectTemplateService.getSelectedQuery(template?.openQuery)?.localizedName}">
                <tr class="w-100">
                    <td class="noBorder noPadding noBackGround w-25">
                        <b>${message(code: 'startWithQuery')}</b>
                    </td>
                    <td class="noBorder noPadding noBackGround tenPaddingLeft w-75">
                        <span class="">
                            ${projectTemplateService.getSelectedQuery(template?.openQuery)?.localizedName}
                        </span>
                    </td>
                </tr>
            </g:if>
            %{-- Expand all inputs--}%
            <g:if test="${template?.expandAllInputs}">
                <tr class="w-100">
                    <td class="noBorder noPadding noBackGround w-25">
                        <b>${message(code: 'expandInputSection')}</b>
                    </td>
                    <td class="noBorder noPadding noBackGround tenPaddingLeft w-75">
                        <span class="">
                            <i class="fa fa-check greenTick" aria-hidden="true"></i>
                        </span>
                    </td>
                </tr>
            </g:if>
            %{-- Start with CD--}%
            <g:if test="${template?.openCarbonDesigner}">
                <tr class="w-100">
                    <td class="noBorder noPadding noBackGround w-25">
                        <b>${message(code: 'startWithCarbonDesigner')}</b>
                    </td>
                    <td class="noBorder noPadding noBackGround tenPaddingLeft w-75">
                        <span class="">
                            <i class="fa fa-check greenTick" aria-hidden="true"></i>
                        </span>
                    </td>
                </tr>
            </g:if>
            %{-- Apply default LCA params--}%
            <g:if test="${template?.applyLcaDefaults}">
                <tr class="w-100">
                    <td class="noBorder noPadding noBackGround w-25">
                        <b>${message(code: 'apply.lca.default')}</b>
                    </td>
                    <td class="noBorder noPadding noBackGround tenPaddingLeft w-75">
                        <span class="">
                            <i class="fa fa-check greenTick" aria-hidden="true"></i>
                        </span>
                    </td>
                </tr>
            </g:if>
            %{-- Allow user to adjust settings--}%
            <g:if test="${template?.allowChange}">
                <tr class="w-100">
                    <td class="noBorder noPadding noBackGround w-25">
                        <b>${message(code: 'allow.changes')}</b>
                    </td>
                    <td class="noBorder noPadding noBackGround tenPaddingLeft w-75">
                        <span class="">
                            <i class="fa fa-check greenTick" aria-hidden="true"></i>
                        </span>
                    </td>
                </tr>
            </g:if>
        %{-- Mandatory template (always applied if set)--}%
            <g:if test="${template?.isMandatory}">
                <tr class="w-100">
                    <td class="noBorder noPadding noBackGround w-25">
                        <b>${message(code: 'mandatory.template')}</b>
                    </td>
                    <td class="noBorder noPadding noBackGround tenPaddingLeft w-75">
                        <span class="">
                            <i class="fa fa-check greenTick" aria-hidden="true"></i>
                        </span>
                    </td>
                </tr>
            </g:if>
        </table>
    </td>
    <td class="w-5">
        <g:if test="${isMainUser}">
            <a href="javascript:" class="btn btn-primary w-fill-available" style="margin-bottom: 3px"
               onclick="fetchProjectTemplateOptionsModal(this, '${Constants.EDIT_TEMPLATE_OPTION_CONTAINER_ID}', '${templateId}', '${accountId}', '${isPublic}', '${message(code: 'unknownError')}')">
            ${message(code: 'edit')}
            </a>
            <a href="javascript:" class="btn btn-danger w-fill-available"
               onclick="deleteProjectTemplate('${templateId}', '${accountId}', '${isPublic}', '${message(code: 'remove.template.title')}', '${message(code: 'remove.template.warning', args: ["${template.name}"])}', '${message(code: 'remove.template.success')}', '${message(code: 'unknownError')}', '${message(code: 'confirm')}', '${message(code: 'cancel')}', '${templateId}', '${com.bionova.optimi.core.Constants.PROJECT_TEMPLATE_TO_LICENSE_TABLE_ID}', 'secondaryOutput')">
                ${message(code: 'delete')}
            </a>
        </g:if>
    </td>
</tr>