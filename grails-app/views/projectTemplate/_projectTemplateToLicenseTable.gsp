<%
    def userService = grailsApplication.mainContext.getBean("userService")
%>
<%@ page expressionCodec="html" %>
<div id="${com.bionova.optimi.core.Constants.PROJECT_TEMPLATE_TO_LICENSE_TABLE_ID}" class="container">
    <div class="sectionheader">
        <button type="button" class="pull-left sectionexpander" data-name="productImage">
            <i id="licenseToQuickStartTemplateExpandHeader" class="icon icon-chevron-down expander"></i>
        </button>

        <h2 class="h2expander"
            onclick="toggleSection('licenseToQuickStartTemplateExpandHeader', 'licenseToTemplateList')"
            style="margin-left: 15px;">
            ${message(code: 'link.to.license')}
        </h2>
    </div>
    <div id="licenseToTemplateList" >
        <table id="licenseToTemplateTable" class="table table-striped">
            <thead>
                <tr>
                    <g:if test="${isPublicTable}">
                        <th></th>
                    </g:if>
                    <th>
                        ${message(code: 'license')}
                        <g:if test="${isPublicTable}">
                            <span class="triggerPopoverPublicTemplateToLicense"
                                  data-content="Active licenses that have compatible entity class: 'building'.">
                                <i class="icon-question-sign"></i>
                            </span>
                        </g:if>
                    </th>
                    <th>${message(code: 'apply.templates')}</th>
                    <th>${message(code: 'apply.default.template')}</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <g:each in="${licenses}" var="license">
                    <g:set var="licenseId" value="${license?.id?.toString()}"/>
                    <g:set var="compatibleTemplates" value="${license?.getCompatibleProjectTemplates(templates)}"/>
                    <g:set var="linkedTemplateIds" value="${isPublicTable ? license?.publicProjectTemplateIds : license?.projectTemplateIdsByAccount?.get(accountId)}"/>
                    <g:set var="linkedTemplates" value="${linkedTemplateIds ? compatibleTemplates?.findAll({ linkedTemplateIds.contains(it.id?.toString())} ) : []}"/>
                    <g:set var="linkedDefaultTemplateId" value="${isPublicTable ? license?.defaultPublicProjectTemplateId : license?.defaultProjectTemplateIdByAccount?.get(accountId)}"/>
                    <tr id="${licenseId}" class="projectTemplateRow">
                        <g:hiddenField id="${licenseId}-licenseId" name="licenseId" value="${licenseId}"/>
                        <g:hiddenField id="${licenseId}-isPublic" name="isPublic" value="${isPublicTable}"/>
                        <g:hiddenField id="${licenseId}-accountId" name="accountId" value="${accountId}"/>
                        <g:if test="${isPublicTable}">
                            <td>
                                <a href="/app/sec/license/form/${licenseId}" >
                                    <i class="fas fa-key"></i>
                                </a>
                            </td>
                        </g:if>
                        <td>
                            <g:if test="${!isPublicTable && (userService.getSuperUser(user) || userService.getConsultant(user))}">
                                <opt:link controller="license" action="form" params="[id: licenseId]">${license.name}</opt:link>
                            </g:if>
                            <g:else>
                                ${license?.name}
                            </g:else>
                        </td>
                        <td>
                            <g:if test="${compatibleTemplates}">
                                <g:each in="${compatibleTemplates}" var="template">
                                    <div style="display: flex; min-width: 250px; margin: 5px 0;">
                                        <g:set var="templateId" value="${template?.id?.toString()}"/>
                                        <g:set var="isChecked"
                                               value="${linkedTemplateIds?.contains(templateId)}"/>
                                        <opt:greenSwitch style="margin: 0" checked="${isChecked}"
                                                         data-templateId="${templateId}"
                                                         data-templateName="${template?.name}" disabled="true"
                                                         name="applyTemplate"
                                                         value="${isChecked ? templateId : ''}"
                                                         onclick="handleApplyTemplateCheckBoxClick(this, 'applyDefaultTemplate-${licenseId}')"/>
                                        <div class="fiveMarginLeft">${template?.name}</div>
                                    </div>
                                </g:each>
                            </g:if>
                            <g:else>
                                <div>${message(code: 'cannot.link.license.to.template')}</div>
                            </g:else>
                        </td>
                        <td>
                            <g:if test="${compatibleTemplates}">
                                <label>
                                    <select id="applyDefaultTemplate-${licenseId}" class="maskWithSelect2AllowClear"
                                            name="applyDefaultTemplate" disabled>
                                        <option></option>
                                        <g:each in="${linkedTemplates}" var="template">
                                            <g:set var="templateId" value="${template?.id?.toString()}"/>
                                            <option id="${templateId}" value="${templateId}" ${linkedDefaultTemplateId == templateId ? 'selected' : ''} >${template?.name}</option>
                                        </g:each>
                                    </select>
                                </label>
                            </g:if>
                        </td>
                        <td class="w-10">
                            <g:if test="${compatibleTemplates && isMainUser}">
                                <a id="editLicenseLinkTemplate${licenseId}" href="javascript:" class="btn btn-primary"
                                   onclick="handleTemplateToLicenseRowEditClick('${licenseId}', 'submitLicenseLinkTemplate${licenseId}', this)">
                                    ${message(code: 'edit')}
                                </a>
                                <a id="submitLicenseLinkTemplate${licenseId}" href="javascript:" class="btn btn-primary hide"
                                   onclick="handleTemplateToLicenseRowEditSubmit('${licenseId}', 'editLicenseLinkTemplate${licenseId}', this, '${message(code: 'unknownError')}', '${message(code: 'link.to.license.error')}')">
                                    ${message(code: 'save')}
                                </a>
                            </g:if>
                        </td>
                    </tr>
                </g:each>
            </tbody>
        </table>
    </div>
    <script>
        $(function () {
            <g:if test="${isPublicTable}">
                $('#licenseToTemplateTable').dataTable({
                    "bFilter": true,
                    "bPaginate": true,
                    "sPaginationType": "full_numbers",
                    "bProcessing": true,
                    "bDestroy": true,
                    "bDeferRender": true,
                    "iDisplayLength": 10,
                });
                keepPopoverOnHover('.triggerPopoverPublicTemplateToLicense', 150, 450)
            </g:if>
            <g:else>
                maskSelectWithSelect2(true, '${message(code: 'select')}', 'maskWithSelect2AllowClear')
            </g:else>
        })
    </script>
</div>