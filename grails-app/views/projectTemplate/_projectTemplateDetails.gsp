<g:set var="optimiResourceService" bean="optimiResourceService"/>
<g:set var="indicatorService" bean="indicatorService"/>
<g:set var="projectTemplateService" bean="projectTemplateService"/>
<div class="templateDetailsBackground">
    %{-- OCL logo--}%
    <g:if test="${template?.isPublic}">
        <div class="pull-right">
            <g:if test="${license?.licenseImage}">
                <opt:displayDomainClassImage imageSource="${license?.licenseImage}" width="240px" height="60px"/>
            </g:if>
            <g:else>
                <img src="https://${com.bionova.optimi.core.Constants.PROD_DOMAIN_NAME}/static/images/log-in page logo.png" alt="" style="max-width: 160px; max-height: 40px;">
            </g:else>
        </div>
    </g:if>
    %{-- Project Name--}%
    <g:if test="${template?.projectName}">
        <div class="fiveMarginVertical">
            <div><b>${message(code: 'projectName')}</b></div>
            <div>
                <i class="fa fa-feather-alt greenTick" aria-hidden="true"></i>
                ${template?.projectName}
            </div>
        </div>
    </g:if>
    %{-- Design Name--}%
    <g:if test="${template?.designName}">
        <div class="fiveMarginVertical">
            <div><b>${message(code: 'designName')}</b></div>
            <div>
                <i class="fa fa-feather-alt greenTick" aria-hidden="true"></i>
                ${template?.designName}
            </div>
        </div>
    </g:if>
    %{-- Tools--}%
    <g:if test="${template?.indicators}">
        <div><b>${message(code: 'entity.show.designs_tools')}</b></div>
        <g:each in="${template?.indicators?.sort({indicatorService.getLocalizedName(it)})}" var="indicator">
            <div style="padding-left: 10px">
                <i class="fa fa-check greenTick" aria-hidden="true"></i>
                <span>${indicatorService.getLocalizedName(indicator)}</span>
            </div>
        </g:each>
    </g:if>
    <table class="tenMarginVertical">
    %{-- Default building type--}%
        <g:if test="${optimiResourceService.getLocalizedName(projectTemplateService.getBuildingTypeResource(template?.buildingTypeResourceId))}">
            <tr class="w-100">
                <td class="noBorder noPadding noBackGround w-25">
                    <b>${message(code: 'default.building.type')}</b>
                </td>
                <td class="noBorder noPadding noBackGround tenPaddingLeft w-75">
                    <span>
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
                    <span>
                        ${optimiResourceService.getLocalizedName(projectTemplateService.getCountryResource(template?.countryResourceId))}
                    </span>
                </td>
            </tr>
        </g:if>
        <g:if test="${template?.productType}">
            <tr class="w-100">
                <td class="noBorder noPadding noBackGround w-25">
                    <b>${message(code: 'default.product.type')}</b>
                </td>
                <td class="noBorder noPadding noBackGround tenPaddingLeft w-75">
                    <span>
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
                    <span>
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
                    <span>
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
                    <span>
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
                    <span>
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
                    <span>
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
                    <span>
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
                    <span>
                        <i class="fa fa-check greenTick" aria-hidden="true"></i>
                    </span>
                </td>
            </tr>
        </g:if>
    </table>
</div>