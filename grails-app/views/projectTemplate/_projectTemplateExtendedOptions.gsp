<g:set var="templateId" value="${template?.id?.toString() ?: ''}"/>
<g:set var="projectTemplateService" bean="projectTemplateService"/>
<g:set var="allowCarbonDesigner" value="${projectTemplateService.getSelectedIndicator(template?.openIndicator)?.allowedCarbonDesignerRegions != null ?: indicatorFromFetch?.allowedCarbonDesignerRegions != null}"/>
<g:set var="carbonDesignerLicensed" value="${licenses?.collect({ it.licensedFeatures })?.flatten()?.unique({ it?.featureId })?.find({com.bionova.optimi.core.domain.mongo.Feature.CARBON_DESIGNER.equals(it?.featureId)}) ? true : false}" />
%{-- Start with CD --}%
<g:if test="${carbonDesignerLicensed}">
    <div style="display: flex;" class="tenMarginVertical">
        <span class="fiveMarginRight">
            <b>${message(code: 'startWithCarbonDesigner')}:</b>
        </span>
        <opt:greenSwitch id="${templateId}-openCarbonDesigner-modal"
                    name="openCarbonDesigner" value="true" checked="${template?.openCarbonDesigner}"
                    disabled="${!allowCarbonDesigner || template?.openQuery}" data-allowCD="${allowCarbonDesigner}"
                    onclick="handleProjectTemplateOpenCarbonDesignerSwitchClick(this, '${templateId}-expandInputSections', '${templateId}-openQuery-modal', '${templateId}-disabledQueryHelp')"/>
        <g:if test="${allowCarbonDesigner}">
            <div class="fiveMarginHorizontal">
                <p id="${templateId}-disabledCarbonDesignerHelp" class="fiveMarginLeft help-block ${template?.openQuery ? '' : 'hide'}" style="margin: 0">
                    ${message(code: 'openCD.disabled.help')}
                </p>
            </div>
        </g:if>
        <g:else>
            <span class="warningRed fiveMarginHorizontal"> ${message(code: 'deprecated')} </span>
        </g:else>
    </div>
</g:if>
%{-- Start with query --}%
<div style="display: flex" class="tenMarginVertical">
    <span class="fiveMarginRight" style="padding-top: 5px">
        <b>${message(code: 'startWithQuery')}:</b>
    </span>
    <label>
        <select id="${templateId}-openQuery-modal" name="openQuery" class="maskWithSelect2AllowClear" ${template?.openCarbonDesigner && carbonDesignerLicensed ? 'disabled' : ''}>
            <option></option>
            <g:each in="${projectTemplateService.getDesignQueries(licenses, template?.openIndicator) ?: designQueriesFromFetch}" var="query">
                <option value="${query?.queryId}" ${template?.openQuery == query?.queryId ? 'selected' : ''}>${query?.localizedName}</option>
            </g:each>
        </select>
        <p id="${templateId}-disabledQueryHelp" class="fiveMarginLeft fiveMarginHorizontal help-block ${allowCarbonDesigner && template?.openCarbonDesigner && carbonDesignerLicensed ? '' : 'hide'}" style="margin: 0">
            ${message(code: 'openQuery.disabled.help')}
        </p>
    </label>
</div>
%{-- Expand inputs --}%
<div style="display: flex; margin-top: 10px" class="tenMarginVertical">
    <span class="fiveMarginRight">
        <b>${message(code: 'expandInputSection')}:</b>
    </span>
    <opt:greenSwitch id="${templateId}-expandInputSections" disabled="${template?.openCarbonDesigner && carbonDesignerLicensed}"
                name="expandAllInputs" value="true" onclick="toggleValueCheckBox(this)" checked="${template?.expandAllInputs}"/>
</div>
<script>
    // "start with data input" select
    $('#${templateId}-openQuery-modal')
        .on('select2:select', function () {
            // uncheck and disable 'start with CD'
            uncheckSwitch('#${templateId}-openCarbonDesigner-modal', true)
            if ($('#${templateId}-openCarbonDesigner-modal').attr('data-allowCD') === 'true') {
                showCDHelp()
            }
        })
        .on('select2:unselect', function () {
            // enable 'start with CD' if it's supported
            if ($('#${templateId}-openCarbonDesigner-modal').attr('data-allowCD') === 'true') {
                enableSwitch(null, '${templateId}-openCarbonDesigner-modal')
                hideCDHelp()
            }
        })
    $(function() {
        if ($('#${templateId}-openCarbonDesigner-modal').prop('disabled') && $('#${templateId}-openCarbonDesigner-modal').attr('data-allowCD') === 'true') {
            showCDHelp()
        }
        if ($('#${templateId}-openQuery-modal').prop('disabled')) {
            showQueryHelp()
        }
        maskSelectWithSelect2(true,'${message(code: 'select')}', 'maskWithSelect2AllowClear')
    })

    function showCDHelp() {
        showSmoothly('${templateId}-disabledCarbonDesignerHelp')
    }

    function hideCDHelp() {
        hideSmoothly('${templateId}-disabledCarbonDesignerHelp')
    }

    function showQueryHelp() {
        showSmoothly('${templateId}-disabledQueryHelp')
    }

</script>