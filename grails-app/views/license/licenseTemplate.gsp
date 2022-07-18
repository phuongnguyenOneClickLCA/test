<%
    def userService = grailsApplication.mainContext.getBean("userService")
%>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="format-detection" content="telephone=no"/>
</head>

<body>
<g:set var="indicatorService" bean="indicatorService"/>
<g:set var="licenseTemplateService" bean="licenseTemplateService"/>
<g:set var="workFlowService" bean="workFlowService"/>

<div class="container">
    <div class="screenheader">
        <h1>
            <g:if test="${licenseTemplate?.id}">
                Modify LicenseTemplate
            </g:if>
            <g:else>
                Add LicenseTemplate
            </g:else>
        </h1>
    </div>
</div>

<div class="container section" style="border-bottom: 1px solid #E2E2E2; margin-bottom: 20px;">
    <div class="sectionbody">
    <sec:ifAnyGranted roles="ROLE_SUPER_USER">
        <div class="row-fluid row-bordered">
                <h3><g:message code="admin.license.features.title"/></h3>
                <g:form action="saveTemplate" useToken="true">
                    <g:hiddenField name="id" value="${licenseTemplate?.id}"/>

                    <fieldset>
                        <div class="column_left bordered">
                            <div class="control-group">
                                <label for="name" class="control-label"><g:message code="entity.name" /></label>
                                <div class="controls"><strong><opt:textField name="name" value="${licenseTemplate?.name}" class="input-xlarge" /></strong></div>
                            </div>


                            <div class="control-group">
                                <label for="indicatorIdSelect" class="control-label">
                                    <strong>DESIGN - <g:message code="admin.license.licensedIndicators"/></strong>
                                </label>
                                <g:if test="${!preventSalesViewEdit}">
                                    <div class="controls">
                                        <g:select data-tableId="indicatorIdTable" data-paramName="indicatorId" name="indicatorIdSelect" disabled="${disabled}" from="${indicators?.findAll({it.indicatorUse?.equalsIgnoreCase("design")})?.sort({indicatorService.getLocalizedName(it).toLowerCase()})}"
                                                  optionKey="indicatorId" optionValue="${{ indicatorService.getLocalizedName(it) }}"
                                                  noSelection="['': '']"/>
                                    </div>
                                </g:if>
                            </div>

                            <table id="indicatorIdTable" class="table table-condensed">
                                <tbody id="indicatorIdTableBody">
                                <g:each in="${licenseTemplateService.getLicensedIndicators(licenseTemplate?.licensedIndicatorIds)?.findAll({it.indicatorUse?.equalsIgnoreCase("design")})?.sort({indicatorService.getLocalizedName(it).toLowerCase()})}" var="indicator">
                                    <tr>
                                        <td style='width: 300px;'>
                                            ${indicatorService.getLocalizedName(indicator)}${indicator.deprecated ? ' (' + message(code: 'deprecated') + ')' : ''}
                                            <input type="hidden" name="indicatorId" value="${indicator.indicatorId}"/>
                                        </td>
                                        <td>
                                            <a class="btn btn-danger">Remove</a>
                                        </td>
                                    </tr>
                                </g:each>
                                </tbody>
                            </table>

                            <div class="control-group">
                                <label for="indicatorIdOperatingSelect" class="control-label">
                                <strong>OPERATING - <g:message code="admin.license.licensedIndicators"/></strong>
                                </label>
                                <g:if test="${!preventSalesViewEdit}">
                                    <div class="controls">
                                        <g:select data-tableId="indicatorIdOperatingTable" data-paramName="indicatorId" name="indicatorIdOperatingSelect" disabled="${disabled}" from="${indicators?.findAll({it.indicatorUse?.equalsIgnoreCase("operating")})?.sort({indicatorService.getLocalizedName(it).toLowerCase()})}"
                                                  optionKey="indicatorId" optionValue="${{ indicatorService.getLocalizedName(it) }}"
                                                  noSelection="['': '']"/>
                                    </div>
                                </g:if>
                            </div>

                            <table id="indicatorIdOperatingTable" class="table table-condensed">
                                <tbody>
                                <g:each in="${licenseTemplateService.getLicensedIndicators(licenseTemplate?.licensedIndicatorIds)?.findAll({it.indicatorUse?.equalsIgnoreCase("operating")})?.sort({indicatorService.getLocalizedName(it).toLowerCase()})}" var="indicator">
                                    <tr>
                                        <td style='width: 300px;'>
                                            ${indicatorService.getLocalizedName(indicator)}${indicator.deprecated ? ' (' + message(code: 'deprecated') + ')' : ''}
                                            <input type="hidden" name="indicatorId" value="${indicator.indicatorId}"/>
                                        </td>
                                        <td>
                                            <a class="btn btn-danger">Remove</a>
                                        </td>
                                    </tr>
                                </g:each>
                                </tbody>
                            </table>


                            <div class="control-group">
                                <label for="featureIdSelect" class="control-label">
                                    <strong><g:message code="admin.license.licensedFeatures"/></strong>
                                </label>
                                <g:if test="${!preventSalesViewEdit}">
                                    <div class="controls">
                                        <g:select data-tableId="featureTable" data-paramName="featureId" name="featureIdSelect" disabled="${disabled}" from="${features}"
                                                  optionKey="id" optionValue="${{ it.formattedName }}" noSelection="['': '']"/>
                                    </div>
                                </g:if>

                                <table id="featureTable" class="table table-condensed">
                                    <tbody>
                                    <g:each in="${licenseTemplateService.getLicensedFeatures(licenseTemplate?.licensedFeatureIds)}" var="feature">
                                        <tr>
                                            <td style='width: 300px;'>
                                                ${feature.formattedName}
                                                <input type="hidden" name="featureId" value="${feature.id}"/>
                                            </td>
                                            <td>
                                            <g:if test="${!disabled}">
                                                <a class="btn btn-danger">Remove</a>
                                            </g:if>
                                            </td>
                                        </tr>
                                    </g:each>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                        <div class="column_right bordered">
                            <div class="control-group" style="width: 400px;">
                                <label for="workflowIdSelect" class="control-label">
                                    <strong>WorkFlows</strong>
                                </label>
                                <g:if test="${!preventSalesViewEdit}">
                                    <div class="controls">
                                        <g:select data-tableId="workflowIdTable" data-paramName="workFlowId" name="workflowIdSelect" disabled="${disabled}" from="${workFlows}"
                                                  optionKey="workFlowId" optionValue="${{ workFlowService.getLocName(it.name) }}"
                                                  noSelection="['': '']"/>
                                    </div>
                                </g:if>
                            </div>

                            <table id="workflowIdTable" class="table table-condensed">
                                <tbody id="workflowIdTableBody">
                                <g:each in="${licenseTemplateService.getLicensedWorkFlows(licenseTemplate?.licensedWorkFlowIds)}" var="workFlow">
                                    <tr>
                                        <td style='width: 300px;'>
                                            ${workFlowService.getLocName(workFlow.name)}
                                            <input type="hidden" name="workFlowId" value="${workFlow.workFlowId}"/>
                                        </td>
                                        <td>
                                            <a class="btn btn-danger">Remove</a>
                                        </td>
                                    </tr>
                                </g:each>
                                </tbody>
                            </table>
                        </div>
                        <div class="column_right bordered">
                            <div class="control-group" style="width: 400px;">
                                <strong>Apply project templates</strong>
                                <div class="controls">
                                    <g:if test="${userService.getSuperUser(user)}">
                                        <g:if test="${compatibleProjectTemplates}">
                                            <g:set var="linkedTemplateIds"
                                                   value="${licenseTemplate?.publicProjectTemplateIds}"/>
                                            <g:set var="linkedTemplates"
                                                   value="${linkedTemplateIds ? compatibleProjectTemplates?.findAll({ linkedTemplateIds.contains(it.id?.toString()) }) : []}"/>
                                            <g:set var="linkedDefaultTemplateId"
                                                   value="${licenseTemplate?.defaultPublicProjectTemplateId}"/>
                                            <g:set var="licenseTemplateId" value="${licenseTemplate?.id?.toString()}"/>
                                            <g:hiddenField name="licenseId" value="${licenseTemplateId}"/>
                                            <g:hiddenField name="isPublic" value="true"/>
                                            <g:hiddenField name="redirectTo" value="license.form"/>
                                            <g:each in="${compatibleProjectTemplates}" var="projectTemplate">
                                                <g:set var="projectTemplateId"
                                                       value="${projectTemplate?.id?.toString()}"/>
                                                <g:set var="isChecked"
                                                       value="${licenseTemplate?.publicProjectTemplateIds?.contains(projectTemplateId)}"/>
                                                <div class="flex">
                                                    <opt:greenSwitch style="margin: 0" checked="${isChecked}"
                                                                     data-templateId="${projectTemplateId}"
                                                                     data-templateName="${projectTemplate?.name}"
                                                                     name="applyProjectTemplate"
                                                                     value="${isChecked ? projectTemplateId : ''}"
                                                                     onclick="handleApplyTemplateCheckBoxClick(this, 'applyDefaultProjectTemplate-${licenseTemplateId}')"/>
                                                    <div class="fiveMarginLeft">${projectTemplate?.name}</div>
                                                </div>
                                            </g:each>
                                            <div class="fiveMarginVertical">
                                                <b>Default project template</b>
                                                <select id="applyDefaultProjectTemplate-${licenseTemplateId}"
                                                        class="maskWithSelect2AllowClear"
                                                        name="applyDefaultProjectTemplate">
                                                    <option></option>
                                                    <g:each in="${linkedTemplates}" var="projectTemplate">
                                                        <g:set var="projectTemplateId"
                                                               value="${projectTemplate?.id?.toString()}"/>
                                                        <option id="${projectTemplateId}"
                                                                value="${projectTemplateId}" ${linkedDefaultTemplateId == projectTemplateId ? 'selected' : ''}>${projectTemplate?.name}</option>
                                                    </g:each>
                                                </select>
                                            </div>
                                        </g:if>
                                        <g:else>
                                            <g:if test="${!licenseTemplate}">
                                                <div>Compatible public quick start templates can be applied once the license template is created.</div>
                                            </g:if>
                                            <g:else>
                                                <div>No public templates are compatible with this license template. Please update the indicators and save template to check again.</div>
                                            </g:else>
                                        </g:else>
                                    </g:if>
                                    <g:else>
                                        <div>Only available for super user.</div>
                                    </g:else>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    <opt:submit name="saveTemplate" disabled="${disabled}" value="Save template" class="btn btn-primary"/>
                    <opt:link action="list" class="btn"><g:message code="cancel"/></opt:link>
                    <opt:link controller="license" action="removeTemplate" class="btn btn-danger" id="${licenseTemplate?.id}"
                              onclick="return modalConfirm(this);"
                              data-questionstr="Are you sure you want to remove licenseTemplate ${licenseTemplate?.name}? It and its attributes will be removed from all linked licenses!"
                              data-truestr="${message(code: 'delete')}" data-falsestr="${message(code: 'cancel')}"
                              data-titlestr="Removing license template"><g:message
                            code="delete"/></opt:link>
                </g:form>
        </div>
    </sec:ifAnyGranted>
    </div>
</div>
<script type="text/javascript">

    $(function () {
        var allSelects = $('select');
        if (allSelects) {
            $(allSelects).select2({
                width: '100%'
            }).maximizeSelect2Height();
        }
    });

    $('table').on('click', '.btn-danger', function(e){
        $(this).closest('tr').remove()
    });

    $('#indicatorIdSelect,#indicatorIdOperatingSelect,#featureIdSelect,#workflowIdSelect').on('select2:select', function (e) {
        var name = e.params.data.text;
        var id = e.params.data.id;
        var tableId = $(this).attr('data-tableId');
        var paramName = $(this).attr('data-paramName');
        var optionToRemove = $(this).find(":selected");
        $(this).val([]).trigger('change');
        optionToRemove.remove();
        var $table = $('#'+tableId);
        $table.append("<tr class='highlighted'><td style='width: 300px;'>"+name+"<input type=\"hidden\" name="+paramName+" value="+id+" /></td><td><a class=\"btn btn-danger\">Remove</a></td></tr>");
        sortTable($table);
        setTimeout(function () {
            $('.highlighted').removeClass('highlighted');
        }, 2000);
    });

    function sortTable(table) {
        var $table = table;
        var $rows = $('tbody > tr', $table);
        $rows.sort(function(a, b) {
            var keyA = $('td',a).text().toLowerCase();
            var keyB = $('td',b).text().toLowerCase();
            return (keyA > keyB) ? 1 : 0;
        });
        $rows.each(function(index, row){
            $table.append(row);
        });
    }
</script>
</body>
</html>
