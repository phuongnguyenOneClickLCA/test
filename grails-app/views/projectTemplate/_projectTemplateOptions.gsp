<%@ page import="com.bionova.optimi.core.Constants" %>
<g:set var="templateId" value="${template?.id?.toString() ?: 'new'}"/>
<g:set var="indicatorService" bean="indicatorService"/>
<g:set var="projectTemplateService" bean="projectTemplateService"/>

%{-- Select a template design --}%
<div style="display: flex" class="tenMarginVertical">
    <span class="fiveMarginRight" style="padding-top: 5px">
        <b>${message(code: 'copy.from.design')}:</b>
        <span class="triggerPopoverTemplateModal"
              data-content="${message(code: 'projectTemplate.designTemplate.help')}">
            <i class="icon-question-sign"></i>
        </span>
    </span>

    <div id="${templateId}${Constants.ENTITY_TEMPLATE_SELECT_POSTFIX}" class="templateSelect">
        <a href="javascript:" class="${template?.childEntityId ? 'hide' : ''}"
           onclick="fetchEntityTemplatesSelect(this, '${templateId}', '${message(code: 'must.select.tool.warning')}', ${onNewProjectModal ?: false}, '${onNewProjectModal && template?.compatibleEntityClass ? template?.compatibleEntityClass : null}', '${licenses?.collect({it.id?.toString()})?.join('&licenseIds=')}')">
            ${message(code: 'show.design.template')}
        </a>

        <div class="entityTemplatesSelect ${template?.childEntityId ? '' : 'hide'}">
            <g:render template="/projectTemplate/entityTemplateSelect"
                      model="[copyEntitiesMapped : copyEntitiesMapped, basicQuery: basicQuery,
                              templateId         : templateId, template: template,
                              indicatorCompatible: indicatorCompatible]"/>
        </div>
    </div>
</div>
%{-- Start with tool --}%
<div style="display: flex" class="tenMarginVertical">
    <span class="fiveMarginRight" style="padding-top: 5px">
        <b>${message(code: 'start.with.tool')}:</b>
    </span>
    <label>
        <select id="${templateId}${Constants.OPEN_INDICATOR_SELECT_POSTFIX}" class="maskWithSelect2AllowClear" name="openIndicator">
            <option></option>
            <g:each in="${projectTemplateService.getIndicators(template?.useIndicatorIds)}" var="indicator">
                <g:if test="${!"benchmark".equals(indicator?.visibilityStatus) && !"visibleBenchmark".equals(indicator?.visibilityStatus)}">
                    <option value="${indicator?.indicatorId}" ${template?.openIndicator == indicator?.indicatorId ? 'selected' : ''}>${indicatorService.getLocalizedName(indicator)}</option>
                </g:if>
            </g:each>
        </select>
    </label>
    <div id="${templateId}-quantity_share_spinner" class="hide greenSpinner" >
        <i style="margin-right: 4px" class="fas fa-circle-notch fa-spin"></i>
    </div>
</div>
<div id="${templateId}${Constants.TEMPLATE_EXTENDED_OPTIONS_POSTFIX}" class="${template?.openIndicator ? '' : 'hide'} tenMarginVertical">
    <g:if test="${template?.openIndicator}">
        <g:render template="/projectTemplate/projectTemplateExtendedOptions" model="[template: template, licenses: licenses]"/>
    </g:if>
</div>
<script>
    // show or hide the show carbon designer, select query, expand inputs when "start with tool" is selected/unselected
    $('#${templateId}${Constants.OPEN_INDICATOR_SELECT_POSTFIX}')
        .on('select2:select', function () {
            const $extendedOptions = $('#${templateId}${Constants.TEMPLATE_EXTENDED_OPTIONS_POSTFIX}')
            disableAllInputInDiv($extendedOptions)
            // disable save btn
            disableSubmit($('#save-${templateId}'))
            let $spinner = $('#${templateId}-quantity_share_spinner')
            $spinner.show()
            const licenseIds = "${licenses?.collect({it.id?.toString()})?.join('&licenseIds=')}"
            let queryParams = getAllInputFromFormToQueryParams('${templateModalForm}')
            queryParams += licenseIds ? '&licenseIds=' + licenseIds : ''
            $.ajax({
                type: 'POST',
                async: true,
                data: queryParams,
                url: '/app/sec/projectTemplate/getProjectTemplateExtendedOptions',
                success: function (data, textStatus) {
                    $spinner.hide()
                    if (data.output === 'error' || textStatus !== 'success') {
                        // display error message
                        $extendedOptions.empty().append('<span class=\"warningRed fiveMarginVertical\">${message(code: 'unknownError')} ${message(code: 'tryAgain')}</span>').show()
                    } else if (data.output) {
                        appendSmoothly(null, $extendedOptions, data.output, 'fast')
                        setTimeout(function() {
                            maskSelectWithSelect2(true, '${message(code: 'select')}', 'maskWithSelect2AllowClear')
                        }, 100)
                    }
                    // enable save btn
                    enableSubmit($('#save-${templateId}'))
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    $spinner.hide()
                    // display error message
                    $extendedOptions.empty().append('<span class=\"warningRed fiveMarginVertical\">${message(code: 'unknownError')} ${message(code: 'tryAgain')}</span>').show()
                    // enable save btn
                    enableSubmit($('#save-${templateId}'))
                }
            });
        })
        .on('select2:unselect', function () {
            // clear extended option when 'select tool' has none tool
            $('#${templateId}${Constants.TEMPLATE_EXTENDED_OPTIONS_POSTFIX}').empty()
        })
        .on('change', function () {
            if (!$(this).val()) {
                // clear extended option when 'select tool' has none tool. This is for the case when select is clear by javascript (when new template is added successfully)
                $('#${templateId}${Constants.TEMPLATE_EXTENDED_OPTIONS_POSTFIX}').empty()
            }
        })
    $(function() {
        maskSelectWithSelect2(true, '${message(code: 'select')}', 'maskWithSelect2AllowClear')
    })
</script>