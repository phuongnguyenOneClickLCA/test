<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="format-detection" content="telephone=no"/>
    <g:set var="optimiResourceService" bean="optimiResourceService"/>
    <g:set var="workFlowService" bean="workFlowService"/>
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>

<body>
<g:set var="indicatorService" bean="indicatorService"/>
<div class="container">
    <div class="screenheader">
        <h1>
            <g:if test="${license?.id}">
                <g:message code="admin.license.modify"/>
            </g:if>
            <g:else>
                <g:message code="admin.license.add"/>
            </g:else>
        </h1>
    </div>
</div>

<div class="container section" style="border-bottom: 1px solid #E2E2E2; margin-bottom: 20px;">
    <div class="sectionbody">
        <div class="row-fluid row-bordered">
            <div class="span4">
                <h3><g:message code="admin.license.terms.title"/></h3>
                <g:uploadForm action="save" useToken="true">
                    <g:hiddenField name="id" value="${license?.id}"/>
                    <fieldset>
                        <div class="control-group">
                            <label for="newManagerId" class="control-label">
                                <i class="fa fa-plus"></i> Add a new Organization
                            </label>
                            <g:select name="newOrganization" from="${allAccounts}" value="" optionKey="${{it._id?:""}}" optionValue="${{it.companyName}}" noSelection="['':'']" disabled="${disabled}"/>

                            <label for="newManagerId" class="control-label">
                                <strong>ADMIN_Organizations</strong>
                            </label>

                            <div class="controls">
                                <g:if test="${accounts}">
                                    <g:each in="${accounts}">
                                        <g:link controller="account" action="form"
                                                id="${it.id}">${it.companyName}</g:link><br/>
                                    </g:each>
                                </g:if>

                            </div>
                        </div>

                        <div class="control-group">
                            <label for="contractNumber" class="control-label">
                                <strong>Contract number</strong>
                            </label>

                            <div class="controls">
                                <g:textField class="input-xlarge" value="${license?.contractNumber}" name="contractNumber" disabled="${disabled}"/>
                            </div>
                        </div>

                        <div class="control-group">
                            <label for="clientNumber" class="control-label">
                                <strong>Client number</strong>
                            </label>

                            <div class="controls">
                                <g:textField class="input-xlarge" value="${license?.clientNumber}" name="clientNumber" disabled="${disabled}"/>
                            </div>
                        </div>

                        <div class="control-group">
                            <label for="name" class="control-label">
                                <strong><g:message code="admin.license.name"/></strong>
                            </label>

                            <div class="controls">
                                <g:textField class="input-xlarge" value="${license?.name}" name="name"
                                             disabled="${disabled}"/>
                            </div>
                        </div>

                        <div class="control-group">
                            <label for="description" class="control-label">
                                <strong><g:message code="admin.license.description"/></strong>
                            </label>

                            <div class="controls">
                                <g:textArea name="description" value="${license?.description}" style="width:100% !important; min-height:100px;" disabled="${disabled}"/>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label"><strong>License image</strong></label>
                            <div class="controls">
                                <p> optimal size: width="100px" height="100px", higher will be scaled down. Max file size 1 Mb </p>
                                <g:if test="${license?.licenseImage}">
                                    <div id="backgroundImageContainer" class="licenseImage">
                                        <div class="img-holder" style="margin-top:30px;">
                                            <opt:displayDomainClassImage imageSource="${license?.licenseImage}"  elementId="licenseImage"/>
                                            <g:if test="${!disabled}">
                                                <a href="javascript:" class="link btn btn-danger btn-small" onclick="changeImage('backgroundImageContainer', 'licenseImage','licenseImage')">Change image</a>
                                            </g:if>
                                        </div>
                                    </div>
                                </g:if>
                                <g:else>
                                    <input type="file"  name="licenseImage" class="licenseImage" id="licenseImage">
                                </g:else>
                            </div>
                        </div>

                        <div class="control-group">
                            <label for="name" class="control-label">
                                <strong><g:message code="admin.license.project_or_user"/></strong>
                            </label>

                            <div class="controls">
                                <g:if test="${license}">
                                    <g:radio name="based" value="project" checked="${license?.projectBased}" disabled="${disabled}" /> <g:message code="admin.license.project_based" /><br />
                                    <g:radio name="based" value="user" checked="${license?.userBased}" disabled="${disabled}" /> <g:message code="admin.license.user_based" />
                                </g:if>
                                <g:else>
                                    <g:radio name="based" value="project" /> <g:message code="admin.license.project_based" /><br />
                                    <g:radio name="based" value="user" checked="true" /> <g:message code="admin.license.user_based" />
                                </g:else>
                            </div>
                        </div>

                        <div class="control-group" rel="popover" id="licenseKeyControls" data-trigger="manual"
                             data-content="Licensekey generated and copied to clipboard">
                            <label for="description" class="control-label">
                                <strong><g:message code="admin.license.licenseKey"/></strong>
                            </label>

                            <div class="controls">
                                <g:textField class="input-xlarge licenseKeyField" id="licenseKey"
                                             value="${license?.licenseKey}" readonly="readonly" name="licenseKey"/>
                                <g:if test="${!preventSalesViewEdit}">
                                    <i class="fa fa-key copyToClipBoard keygen" id="licenseKeyGen" rel="popover"
                                       data-trigger="hover" data-content="Generate licensekey"
                                       onclick="generateLicenseKey(10, '#licenseKey');
                                       copyText('licenseKey');"></i>
                                </g:if>
                            </div>
                        </div>

                        <div class="control-group">
                            <label for="type" class="control-label">
                                <strong><g:message code="admin.license.type"/></strong>
                            </label>

                            <div class="controls">
                                <g:select name="type" disabled="${disabled}" value="${license?.type}"
                                          from="${licenseTypes}" optionKey="key" optionValue="${{ it.value }}"
                                          noSelection="${['': '']}"/>
                            </div>
                        </div>

                        <div class="control-group" ${!'Trial'.equals(license?.type) && !'Education'.equals(license?.type) ? ' style=\"display: none;\"' : ''}
                             id="lengthForTrial">
                            <label for="type" class="control-label">
                                <strong><g:message code="admin.license.trialLength"/></strong>
                            </label>

                            <div class="controls">
                                <g:textField name="trialLength" value="${license?.trialLength}" disabled="${disabled}" />
                            </div>
                        </div>

                        <div class="control-group">
                            <label for="validFrom" class="control-label">
                                <strong><g:message code="admin.license.validFrom"/></strong>
                            </label>

                            <div class="controls">
                                <label><g:textField disabled="${disabled}" name="validFrom" id="validFrom"
                                                    value="${license?.validFrom ? formatDate(date: license?.validFrom, format: 'dd.MM.yyyy') : validFrom}"
                                                    class="input-xlarge datepicker" readonly="true"
                                                    style="cursor: pointer;"/><span class="add-on"><i
                                        class="icon-calendar"></i></span></label>
                            </div>
                        </div>

                        <div class="control-group">
                            <label for="validUntil" class="control-label">
                                <strong><g:message code="admin.license.validUntil"/></strong>
                            </label>

                            <div class="controls">
                                <label><g:textField disabled="${disabled}" name="validUntil" id="validUntil"
                                                    value="${formatDate(date: license?.validUntil, format: 'dd.MM.yyyy')}"
                                                    class="input-xlarge datepicker" readonly="true"
                                                    style="cursor: pointer;"/><span class="add-on"><i
                                        class="icon-calendar"></i></span></label>
                            </div>
                        </div>

                        <div class="control-group">
                            <label for="renewalStatus" class="control-label">
                                <strong><g:message code="admin.license.renewalStatus"/></strong>
                            </label>

                            <div class="controls">
                                <g:select name="renewalStatus" disabled="${disabled}" value="${license?.renewalStatus}"
                                          from="${com.bionova.optimi.core.domain.mongo.License.renewalStatusChoices}"
                                          optionKey="${{ it }}" optionValue="${{ message(code: 'admin.' + it) }}"
                                          noSelection="${['': '']}"/>
                            </div>
                        </div>

                        <div class="control-group" ${!'autoRenewal'.equals(license?.renewalStatus) ? ' style=\"display: none;\"' : ''}
                             id="renewalDateChoice">
                            <label for="renewalDate" class="control-label">
                                <strong><g:message code="admin.license.renewalDate"/> <g:if
                                        test="${license?.daysToNextRenewal}">(Next renewal: ${license.daysToNextRenewal} days)</g:if></strong>
                            </label>

                            <div class="controls">
                                <label><g:textField disabled="${disabled}" name="renewalDate" id="renewalDate"
                                                    value="${license?.renewalDate ? license?.renewalDate : formatDate(date: new Date(), format: 'dd.MM')}"
                                                    class="input-xlarge datepicker" style="cursor: pointer;"/><span
                                        class="add-on"><i class="icon-calendar"></i></span></label>
                            </div>
                        </div>


                        <div class="control-group">
                            <label for="maxEntities" class="control-label">
                                <strong><g:message code="admin.license.maxEntities"/></strong>
                            </label>

                            <div class="controls">
                                <label><g:textField name="maxEntities" disabled="${disabled}"
                                                    value="${license?.maxEntities}" class="input-xlarge"/></label>
                            </div>
                        </div>

                        <div class="control-group">
                            <label for="compatibleEntityClasses" class="control-label">
                                <strong><g:message code="admin.license.compatibleEntityClasses"/></strong>
                            </label>

                            <div class="controls">
                                <g:select name="compatibleEntityClasses" disabled="${disabled}"
                                          value="${license?.compatibleEntityClasses}" from="${entityClasses}"
                                          multiple="multiple"/>
                            </div>
                        </div>

                        <div class="control-group">
                            <label for="countries" class="control-label">
                                <strong>Countries (optional)</strong>
                            </label>

                            <div class="controls">
                                <select name="countries" id="countrySelect" multiple ${disabled ? 'disabled=\"disabled\"':''}>
                                    <g:each in="${countries}" var="country">
                                        <option value="${country.resourceId}" ${license?.countries?.contains(country.resourceId) ? 'selected' : ''}>${optimiResourceService.getLocalizedName(country)}</option>
                                    </g:each>
                                </select>
                            </div>
                        </div>

                        <div class="control-group">
                            <label for="floatingLicense" class="control-label">
                                <strong>Floating license</strong>
                            </label>

                            <div class="controls">
                                <select name="floatingLicense" id="floatingLicenseSelect" ${disabled ? 'disabled=\"disabled\"':''}>
                                    <g:each in="${floatingLicenses}" var="floatingLicense">
                                        <option value="${floatingLicense.key}" ${license?.floatingLicense?.equals(floatingLicense.key) ? 'selected' : ''}>${floatingLicense.value}</option>
                                    </g:each>
                                </select>
                            </div>

                            <div id="floatingLicenseScopeDiv" class="controls"${'no'.equals(license?.floatingLicense)||!license?.floatingLicense ? ' style=\"display: none;\"' : ''}>
                                <label for="floatingLicenseScope" class="control-label">
                                    <strong>Floating license scope</strong>
                                </label>
                                <g:textField disabled="${disabled}" name="floatingLicenseScope" id="floatingLicenseScope"
                                             value="${license?.floatingLicenseScope ?: ""}"/>
                            </div>

                            <div id="floatingLicenseMaxUsersDiv" class="controls"${'no'.equals(license?.floatingLicense)||!license?.floatingLicense ? ' style=\"display: none;\"' : ''}>
                                <label for="floatingLicenseMaxUsers" class="control-label">
                                    <strong>Floating license max users (mandatory for a floating license)</strong>
                                </label>
                                <input type="number" ${disabled ? "disabled" : ""} name="floatingLicenseMaxUsers" id="floatingLicenseMaxUsers"
                                             value="${license?.floatingLicenseMaxUsers ?: ""}"/>
                            </div>
                        </div>

                        <div class="control-group">
                            <label for="freeForAllEntities" class="control-label">
                                <strong><g:message code="admin.license.freeForAllEntities"/></strong>
                            </label>

                            <div class="controls">
                                <g:checkBox name="freeForAllEntities" disabled="${disabled}"
                                            checked="${license?.freeForAllEntities}"/>
                            </div>
                        </div>

                        <div class="control-group">
                            <label for="freeForAllConditions" class="control-label">
                                <strong>Free for all entities meeting conditions</strong>
                            </label>

                            <div class="controls">
                                <label for="conditionalEntityClassesSelect" class="control-label">
                                    Has entity class
                                </label>
                                <select name="conditionalEntityClasses" id="conditionalEntityClassesSelect" multiple ${disabled ? 'disabled=\"disabled\"':''}>
                                    <g:each in="${entityClasses}" var="entityClass">
                                        <option value="${entityClass}" ${license?.conditionalEntityClasses?.contains(entityClass) ? 'selected' : ''}>${entityClass}</option>
                                    </g:each>
                                </select>
                            </div>

                            <div class="controls">
                                <label for="conditionalEntityClassesSelect" class="control-label">
                                    Has building type
                                </label>
                                <select name="conditionalBuildingTypes" id="conditionalBuildingTypesSelect" multiple ${disabled ? 'disabled=\"disabled\"':''}>
                                    <g:each in="${buildingTypes}" var="buildingType">
                                        <option value="${buildingType}" ${license?.conditionalBuildingTypes?.contains(buildingType) ? 'selected' : ''}>${buildingType}</option>
                                    </g:each>
                                </select>
                            </div>

                            <div class="controls">
                                <label for="conditionalEntityClassesSelect" class="control-label">
                                    Has country
                                </label>
                                <select name="conditionalCountries" id="conditionalCountrySelect" multiple ${disabled ? 'disabled=\"disabled\"':''}>
                                    <g:each in="${countries}" var="country">
                                        <option value="${country.resourceId}" ${license?.conditionalCountries?.contains(country.resourceId) ? 'selected' : ''}>${optimiResourceService.getLocalizedName(country)}</option>
                                    </g:each>
                                </select>
                            </div>
                            <div class="controls">
                                <label for="conditionalEntityClassesSelect" class="control-label">
                                    Has state
                                </label>
                                <select name="conditionalStates" id="conditionalStateSelect" multiple ${disabled ? 'disabled=\"disabled\"':''}>
                                    <g:each in="${states}" var="state">
                                        <option value="${state.resourceId}" ${license?.conditionalStates?.contains(state.resourceId) ? 'selected' : ''}>${state.resourceId}</option>
                                    </g:each>
                                </select>
                            </div>
                        </div>


                        <div class="control-group">
                            <label for="openTrial" class="control-label">
                                <strong>Open trial</strong>
                            </label>

                            <div class="controls">
                                <g:checkBox name="openTrial" disabled="${disabled}"
                                            checked="${license?.openTrial}"/>
                            </div>
                        </div>

                            <div class="control-group">
                                <label for="addon" class="control-label">
                                    <strong>Add-on</strong>
                                </label>

                                <div class="controls">
                                    <g:checkBox name="addon" disabled="${disabled}"
                                                checked="${license?.addon}"/>
                                </div>
                            </div>

                        <div class="control-group">
                            <label for="preventAddToProject" class="control-label">
                                <strong>Prevent from adding to project</strong>
                            </label>

                            <div class="controls">
                                <g:checkBox name="preventAddToProject" disabled="${disabled}"
                                            checked="${license?.preventAddToProject}"/>
                            </div>
                        </div>


                        <div class="control-group">
                            <label for="trainingTaken" class="control-label">
                                <strong>Training taken</strong>
                            </label>

                            <div class="controls">
                                <g:checkBox name="trainingTaken" disabled="${disabled}"
                                            checked="${license?.trainingTaken}"/>
                            </div>
                        </div>

                        <div class="control-group">
                            <label for="planetary" class="control-label">
                                <strong>Non combinable non removable license (Planetary)</strong>
                            </label>

                            <div class="controls">
                                <g:checkBox name="planetary" disabled="${disabled}" checked="${license?.planetary}"/>
                            </div>
                        </div>

                    </fieldset>

                    <g:if test="${!preventSalesViewEdit}">
                        <opt:submit name="save" disabled="${disabled}" value="${message(code: 'save')}" class="btn btn-primary"/>
                    </g:if>
                    <opt:link action="list" class="btn"><g:message code="cancel"/></opt:link>
                    <g:if test="${!preventSalesViewEdit && license}">
                        <g:if test="${license.frozen}">
                            <opt:submit name="unfreeze" disabled="${disabled}" value="Unfreeze" class="btn btn-warning"/>
                        </g:if>
                        <g:else>
                            <opt:submit name="freeze" disabled="${disabled}" value="Freeze" class="btn btn-info"/>
                        </g:else>
                    </g:if>
                    <g:if test="${license?.id && !disabled}">
                        <opt:link controller="license" action="remove" class="btn btn-danger" id="${license?.id}"
                                  onclick="return modalConfirm(this);"
                                  data-questionstr="${message(code: 'admin.license.delete_question')}"
                                  data-truestr="${message(code: 'delete')}" data-falsestr="${message(code: 'cancel')}"
                                  data-titlestr="${message(code: 'admin.license.delete_confirm.header')}"><g:message
                                code="delete"/></opt:link>
                    </g:if>
                </g:uploadForm>
                <g:set var="column1" value="${System.currentTimeMillis()}" />
            </div>

            <g:if test="${license?.id}">
                <div class="span4 bordered">

                    <h3><g:message code="admin.license.features.title"/></h3>
                    <g:form action="applyTier" useToken="true">
                        <g:hiddenField name="licenseId" value="${license.id}"/>

                        <div class="control-group">
                            <label for="tierId" class="control-label">
                                <strong>License Tier</strong>
                            </label>
                            <div class="controls">
                                <g:select name="tierId" disabled="${disabled}" from="${licenseTiers}"
                                          optionKey="id" optionValue="${{ it.name }}"
                                          noSelection="['': '']" value="${license.tierId}"/>
                                <opt:submit name="applyTier" disabled="${disabled}"
                                            value="Save" class="btn btn-primary btn-admin"/>
                            </div>
                            <p>&nbsp;</p>
                        </div>
                    </g:form>

                    <g:form action="applyTemplate" useToken="true">
                        <g:hiddenField name="id" value="${license.id}"/>

                        <div class="control-group">
                            <label for="templateId" class="control-label">
                                <strong>Apply license template</strong>
                            </label>
                            <g:if test="${!preventSalesViewEdit}">
                                <div class="controls">
                                    <g:select name="templateId" disabled="${disabled}" from="${licenseTemplates}"
                                              optionKey="id" optionValue="${{ it.name }}"
                                              noSelection="['': '']"/>
                                    <opt:submit name="applyTemplate" disabled="${disabled}"
                                                value="Apply" class="btn btn-primary btn-admin"/>
                                </div>
                            </g:if>
                            <p>&nbsp;</p>
                        </div>

                        <table class="table table-condensed">
                            <tbody>
                            <g:each in="${license?.licenseTemplates?.sort({it.name.toLowerCase()})}" var="licenseTemplate">
                                <tr>
                                    <td><g:if test="${!preventSalesViewEdit}"><g:link action="licenseTemplate" id="${licenseTemplate.id}">${licenseTemplate.name}</g:link></g:if><g:else>${licenseTemplate.name}</g:else></td><td>
                                    <g:if test="${!disabled}">
                                        <opt:link action="removeTemplateFromLicense" id="${license.id}"
                                                  params="[licenseTemplateId: licenseTemplate.id]"
                                                  class="btn btn-danger"
                                                  onclick="return modalConfirm(this);"
                                                  data-questionstr="Are you sure you want to remove licenseTemplate ${licenseTemplate?.name}? It and its attributes will be removed."
                                                  data-truestr="${message(code: 'delete')}"
                                                  data-falsestr="${message(code: 'cancel')}"
                                                  data-titlestr="Removing license template"><g:message code="delete"/></opt:link>
                                    </g:if>
                                </td>
                                </tr>
                            </g:each>
                            </tbody>
                        </table>
                    </g:form>


                    <g:form action="addObjects" useToken="true">
                        <g:hiddenField name="id" value="${license?.id}"/>
                        <g:set var="licensedIndicators" value="${license?.licensedIndicators}" />

                        <fieldset>
                            <div class="control-group">
                                <label for="indicatorId" class="control-label">
                                    <strong>DESIGN - <g:message code="admin.license.licensedIndicators"/></strong>
                                </label>
                                <g:if test="${!preventSalesViewEdit}">
                                    <div class="controls">
                                        <g:select name="indicatorId" disabled="${disabled}" from="${designIndicators}"
                                                  optionKey="indicatorId" optionValue="${{ indicatorService.getLocalizedName(it) }}"
                                                  noSelection="['': '']"/>
                                        <opt:submit name="addIndicator" disabled="${disabled}"
                                                    value="${message(code: 'add')}" class="btn btn-primary btn-admin"/>
                                    </div>
                                </g:if>
                                <p>&nbsp;</p>
                            </div>

                                <table class="table table-condensed">
                                    <tbody>
                                    <g:each in="${licensedIndicators?.findAll({it.indicatorUse?.equalsIgnoreCase("design")})?.sort({indicatorService.getLocalizedName(it).toLowerCase()})}" var="indicator">
                                        <tr>
                                            <td>${indicatorService.getLocalizedName(indicator)}${indicator.deprecated ? ' (' + message(code: 'deprecated') + ')' : ''}</td><td>
                                            <g:if test="${!disabled}">
                                                <opt:link action="removeIndicator" id="${license?.id}"
                                                          params="[indicatorId: indicator?.indicatorId]"
                                                          class="btn btn-danger"
                                                          onclick="return modalConfirm(this);"
                                                          data-questionstr="${message(code: 'admin.license.delete_indicator.question', args: [indicatorService.getLocalizedName(indicator)])}"
                                                          data-truestr="${message(code: 'delete')}"
                                                          data-falsestr="${message(code: 'cancel')}"
                                                          data-titlestr="${message(code: 'admin.license.delete_indicator.header')}"><g:message
                                                        code="delete"/></opt:link>
                                            </g:if>
                                        </td>
                                        </tr>
                                    </g:each>
                                    </tbody>
                                </table>

                            <div class="control-group">
                                <label for="indicatorIdOperating" class="control-label">
                                <strong>OPERATING - <g:message code="admin.license.licensedIndicators"/> </strong>
                                </label>
                                <g:if test="${!preventSalesViewEdit}">
                                    <div class="controls">
                                        <g:select name="indicatorIdOperating" disabled="${disabled}" from="${operatingIndicators}"
                                                  optionKey="indicatorId" optionValue="${{ indicatorService.getLocalizedName(it) }}"
                                                  noSelection="['': '']"/>
                                        <opt:submit name="addIndicatorOperating" disabled="${disabled}"
                                                    value="${message(code: 'add')}" class="btn btn-primary btn-admin"/>

                                    </div>
                                </g:if>
                            </div>

                            <table class="table table-condensed">
                                <tbody>
                                <g:each in="${licensedIndicators?.findAll({it.indicatorUse?.equalsIgnoreCase("operating")})?.sort({indicatorService.getLocalizedName(it).toLowerCase()})}" var="indicator">
                                    <tr>
                                        <td>${indicatorService.getLocalizedName(indicator)}${indicator.deprecated ? ' (' + message(code: 'deprecated') + ')' : ''}</td><td>
                                        <g:if test="${!disabled}">
                                            <opt:link action="removeIndicator" id="${license?.id}"
                                                      params="[indicatorId: indicator?.indicatorId]"
                                                      class="btn btn-danger"
                                                      onclick="return modalConfirm(this);"
                                                      data-questionstr="${message(code: 'admin.license.delete_indicator.question', args: [indicatorService.getLocalizedName(indicator)])}"
                                                      data-truestr="${message(code: 'delete')}"
                                                      data-falsestr="${message(code: 'cancel')}"
                                                      data-titlestr="${message(code: 'admin.license.delete_indicator.header')}"><g:message
                                                    code="delete"/></opt:link>
                                        </g:if>
                                    </td>
                                    </tr>
                                </g:each>
                                </tbody>
                            </table>

                            <g:set var="readonlyIndicators" value="${license?.readonlyIndicators ?: []}" />
                            <div class="control-group">
                                <label for="indicatorIdOperating" class="control-label">
                                    <strong>Read-only indicators (the indicator needs to also be on the above lists of indicators)</strong>
                                </label>
                                <g:if test="${!preventSalesViewEdit}">
                                    <div class="controls">
                                        <g:select name="readonlyIndicatorId" disabled="${disabled}" from="${license?.readonlyIndicatorIds ? allIndicators.findAll({ !license.readonlyIndicatorIds.contains(it.id.toString()) }) : allIndicators}"
                                                  optionKey="indicatorId" optionValue="${{ indicatorService.getLocalizedName(it) }}"
                                                  noSelection="['': '']"/>
                                        <opt:submit name="addIndicatorReadonly" disabled="${disabled}"
                                                    value="${message(code: 'add')}" class="btn btn-primary btn-admin"/>

                                    </div>
                                </g:if>
                            </div>

                            <table class="table table-condensed">
                                <tbody>
                                <g:each in="${readonlyIndicators?.sort({indicatorService.getLocalizedName(it).toLowerCase()})}" var="indicator">
                                    <tr>
                                        <td>${indicatorService.getLocalizedName(indicator)}${indicator.deprecated ? ' (' + message(code: 'deprecated') + ')' : ''}</td><td>
                                        <g:if test="${!disabled}">
                                            <opt:link action="removeReadonlyIndicator" id="${license?.id}"
                                                      params="[indicatorId: indicator?.indicatorId]"
                                                      class="btn btn-danger"
                                                      onclick="return modalConfirm(this);"
                                                      data-questionstr="${message(code: 'admin.license.delete_indicator.question', args: [indicatorService.getLocalizedName(indicator)])}"
                                                      data-truestr="${message(code: 'delete')}"
                                                      data-falsestr="${message(code: 'cancel')}"
                                                      data-titlestr="${message(code: 'admin.license.delete_indicator.header')}"><g:message
                                                    code="delete"/></opt:link>
                                        </g:if>
                                    </td>
                                    </tr>
                                </g:each>
                                </tbody>
                            </table>

                            <div class="control-group">
                                <label for="featureId" class="control-label">
                                    <strong><g:message code="admin.license.licensedFeatures"/></strong>
                                </label>
                                <g:if test="${!preventSalesViewEdit}">
                                    <div class="controls">
                                        <g:select name="featureId" disabled="${disabled}" from="${features}"
                                                  optionKey="id" optionValue="${{ it.formattedName }}" noSelection="['': '']"/>
                                        <opt:submit name="addFeature" disabled="${disabled}"
                                                    value="${message(code: 'add')}" class="btn btn-primary btn-admin"/>
                                    </div>
                                </g:if>
                                <p>&nbsp;</p>
                                <table class="table table-condensed">
                                    <tbody>
                                    <g:each in="${license?.licensedFeatures}" var="feature">
                                        <tr>
                                            <td>${feature.formattedName} ${feature.featureId}</td><td>
                                            <g:if test="${!disabled}">
                                                <opt:link action="removeFeature" id="${license?.id}"
                                                          params="[featureId: feature?.id]" class="btn btn-danger"
                                                          onclick="return modalConfirm(this);"
                                                          data-questionstr="${message(code: 'admin.license.delete_feature.question', args: [feature.formattedName])}"
                                                          data-truestr="${message(code: 'delete')}"
                                                          data-falsestr="${message(code: 'cancel')}"
                                                          data-titlestr="${message(code: 'admin.license.delete_feature.header')}"><g:message
                                                        code="delete"/></opt:link>
                                            </g:if>
                                        </td>
                                        </tr>
                                    </g:each>
                                    </tbody>
                                </table>
                            </div>

                            <div class="control-group">
                                <label for="workFlowId" class="control-label">
                                    <strong>Licensed WorkFlows</strong>
                                </label>
                                <g:if test="${!preventSalesViewEdit}">
                                    <div class="controls">
                                        <g:select name="workFlowId" disabled="${disabled}" from="${workFlows}"
                                                  optionKey="workFlowId" optionValue="${{ workFlowService.getLocName(it.name) }}" noSelection="['': '']"/>
                                        <opt:submit name="addWorkFlow" disabled="${disabled}"
                                                    value="${message(code: 'add')}" class="btn btn-primary btn-admin"/>
                                    </div>
                                </g:if>
                                <p>&nbsp;</p>
                                <table class="table table-condensed">
                                    <tbody>
                                    <g:each in="${license?.licensedWorkFlows}" var="workFlow">
                                        <tr>
                                            <td>${workFlowService.getLocName(workFlow.name)}</td><td>
                                            <g:if test="${!disabled}">
                                                <opt:link action="removeWorkFlow" id="${license?.id}"
                                                          params="[workFlowId: workFlow?.workFlowId]" class="btn btn-danger"
                                                          onclick="return modalConfirm(this);"
                                                          data-questionstr="${message(code: 'admin.license.delete_feature.question', args: [workFlowService.getLocName(workFlow.name)])}"
                                                          data-truestr="${message(code: 'delete')}"
                                                          data-falsestr="${message(code: 'cancel')}"
                                                          data-titlestr="${message(code: 'admin.license.delete_feature.header')}"><g:message
                                                        code="delete"/></opt:link>
                                            </g:if>
                                        </td>
                                        </tr>
                                    </g:each>
                                    </tbody>
                                </table>
                            </div>

                            <div class="control-group">
                                <label for="apiIndicatorId" class="control-label">
                                    <strong>API Indicator - If users API usage is enabled by this license then this indicator is forced for calculation.</strong>
                                </label>
                                <g:if test="${!preventSalesViewEdit}">
                                    <g:form action="addObjects" useToken="true">
                                        <div class="controls">
                                            <g:select name="apiIndicatorId" disabled="${disabled}" from="${apiIndicators}"
                                                  optionKey="indicatorId" value="${license?.apiIndicatorId}" optionValue="${{ indicatorService.getLocalizedName(it) }}"
                                                  noSelection="['': '']"/>
                                            <opt:submit name="addApiIndicatorId" value="${message(code: 'save')}" class="btn btn-primary btn-admin"/>
                                        </div>
                                    </g:form>
                                </g:if>
                                <p>&nbsp;</p>
                            </div>
                            <sec:ifAnyGranted roles="${com.bionova.optimi.core.Constants.ROLE_SUPER_USER}">
                                <div class="control-group">
                                    <label for="applyTemplate" class="control-label">
                                        <strong>Apply public quickstart templates</strong>
                                    </label>
                                    <g:if test="${compatibleProjectTemplates}">
                                        <g:set var="linkedTemplateIds" value="${license?.publicProjectTemplateIds}"/>
                                        <g:set var="linkedTemplates" value="${linkedTemplateIds ? compatibleProjectTemplates?.findAll({ linkedTemplateIds.contains(it.id?.toString())} ) : []}"/>
                                        <g:set var="linkedDefaultTemplateId" value="${license?.defaultPublicProjectTemplateId}"/>
                                        <g:set var="licenseId" value="${license?.id?.toString()}"/>
                                        <div id="applyTemplateOnLicensePage" class="controls">
                                            <g:hiddenField name="licenseId" value="${licenseId}"/>
                                            <g:hiddenField name="isPublic" value="true"/>
                                            <g:hiddenField name="redirectTo" value="license.form"/>
                                            <g:each in="${compatibleProjectTemplates}" var="template">
                                                <g:set var="templateId" value="${template?.id?.toString()}"/>
                                                <g:set var="isChecked"
                                                       value="${license?.publicProjectTemplateIds?.contains(templateId)}"/>
                                                <div class="flex">
                                                    <opt:greenSwitch style="margin: 0" checked="${isChecked}"
                                                                     data-templateId="${templateId}"
                                                                     data-templateName="${template?.name}" disabled="true"
                                                                     name="applyTemplate"
                                                                     value="${isChecked ? templateId : ''}"
                                                                     onclick="handleApplyTemplateCheckBoxClick(this, 'applyDefaultTemplate-${licenseId}')"/>
                                                    <div class="fiveMarginLeft">${template?.name}</div>
                                                </div>
                                            </g:each>
                                            <div class="fiveMarginVertical">
                                                <b>${message(code: 'apply.default.template')}</b>
                                                <select id="applyDefaultTemplate-${licenseId}" class="maskWithSelect2AllowClear"
                                                        name="applyDefaultTemplate" disabled>
                                                    <option></option>
                                                    <g:each in="${linkedTemplates}" var="template">
                                                        <g:set var="templateId" value="${template?.id?.toString()}"/>
                                                        <option id="${templateId}" value="${templateId}" ${linkedDefaultTemplateId == templateId ? 'selected' : ''} >${template?.name}</option>
                                                    </g:each>
                                                </select>
                                            </div>
                                            <a id="editLicenseLinkTemplateOnLicensePage" href="javascript:" class="btn btn-primary"
                                               onclick="handleTemplateToLicenseRowEditClick('applyTemplateOnLicensePage', 'submitLicenseLinkTemplateOnLicensePage', this)">
                                                ${message(code: 'edit')}
                                            </a>
                                            <a id="submitLicenseLinkTemplateOnLicensePage" href="javascript:" class="btn btn-primary hide"
                                               onclick="handleTemplateToLicenseRowEditSubmit('applyTemplateOnLicensePage', 'editLicenseLinkTemplateOnLicensePage', this, '${message(code: 'unknownError')}', '${message(code: 'link.to.license.error')}')">
                                                ${message(code: 'save')}
                                            </a>
                                        </div>
                                    </g:if>
                                    <g:else>
                                        <div>${message(code: 'cannot.link.license.to.template')}</div>
                                    </g:else>
                                </div>
                            </sec:ifAnyGranted>
                        </fieldset>
                    </g:form>
                </div>
            </g:if>

            <g:if test="${license?.id && (userManagingAllowed||(floatingUserManagingAllowed && license.isFloatingLicense))}">
                <div class="span4 bordered">
                    <g:set var="column3" value="${System.currentTimeMillis()}" />
                    <g:if test="${userManagingAllowed}">
                        <h3><g:message code="admin.license.users.title"/></h3>
                        <fieldset>
                            <g:form action="addObjects" useToken="true">
                                <g:hiddenField name="id" value="${license?.id}"/>
                                <div class="control-group">
                                    <label for="newManagerId" class="control-label">
                                        <strong><g:message code="admin.license.manager"/></strong>
                                    </label>


                                    <div class="controls">
                                        <input type="text" name="newManagerId" value="" id="licenseManagerEmail">
                                        <input type="submit" name="addLicenseManager" value="Add" class="btn btn-primary btn-admin" id="addLicenseManager" ${disabled}>

                                    </div>
                                </div>

                                <div class="control-group">
                                    <label for="freeForAllEntities" class="control-label">
                                        <strong><g:message code="admin.license.licenseManagerCanAddLicenseUserIds"/></strong>
                                    </label>

                                    <div class="controls">
                                        <g:checkBox name="licenseManagerCanAddLicenseUserIds" disabled="${disabled}"
                                                    checked="${license?.licenseManagerCanAddLicenseUserIds}"/>
                                    </div>
                                </div>
                            <%-- <opt:submit disabled="${disabled ? true : false}" name="addUser" value="${message(code: 'add')}" class="btn btn-primary"/>--%>
                                <table class="table table-condensed">
                                    <tbody>
                                    <g:each in="${license?.managers}" var="manager">
                                        <g:if test="${manager}">
                                            <tr>
                                                <td>${manager.username}</td>
                                                <g:if test="${!preventSalesViewEdit || userManagingAllowed}">
                                                    <g:if test="${!disabled}">
                                                    <td>
                                                        <opt:link action="removeLicenseManagerId" id="${license?.id}"
                                                                  params="[userId: manager.id.toString()]" class="btn btn-danger"
                                                                  onclick="return modalConfirm(this);"
                                                                  data-questionstr="${message(code: 'admin.license.remove_user.question', args: [manager.username])}"
                                                                  data-truestr="${message(code: 'delete')}"
                                                                  data-falsestr="${message(code: 'cancel')}"
                                                                  data-titlestr="${message(code: 'admin.license.remove_user.header')}"><g:message
                                                                code="delete"/></opt:link>
                                                    </td>
                                                    </g:if>
                                                </g:if>
                                            </tr>
                                        </g:if>
                                    </g:each>
                                    </tbody>
                                </table>

                                <div class="control-group">
                                    <label for="email" class="control-label">
                                        <strong><g:message code="admin.license.licensedUsers"/></strong>
                                    </label>
                                    <g:if test="${!preventSalesViewEdit || userManagingAllowed}">
                                        <div class="controls">
                                            <g:textField name="email"/>
                                            <g:select name="userType" from="${userTypes}"
                                                      optionValue="${{ message(code: it.messageKey) }}"/>
                                            <opt:submit disabled="${disabled && !userManagingAllowed ? true : false}" name="addUser" value="${message(code: 'add')}" class="btn btn-primary btn-admin"/>
                                        </div>
                                    </g:if>
                                    <p>&nbsp;</p>
                                    <table class="table table-condensed">
                                        <tbody>
                                        <g:each in="${license?.licensedUsers}" var="usersMap">
                                            <tr>
                                                <td>${usersMap.key.username}</td><td>${usersMap.value}</td><td>
                                                <g:if test="${!preventSalesViewEdit || userManagingAllowed}">
                                                    <opt:link action="removeUser" id="${license?.id}"
                                                              params="[userId: usersMap.key.id]" class="btn btn-danger"
                                                              onclick="return modalConfirm(this);"
                                                              data-questionstr="${message(code: 'admin.license.remove_user.question', args: [usersMap.key.username])}"
                                                              data-truestr="${message(code: 'delete')}"
                                                              data-falsestr="${message(code: 'cancel')}"
                                                              data-titlestr="${message(code: 'admin.license.remove_user.header')}"><g:message
                                                            code="delete"/></opt:link>
                                                </g:if>
                                            </td>
                                            </tr>
                                        </g:each>
                                        </tbody>
                                    </table>
                                </div>

                                <div class="control-group">
                                    <label for="email" class="control-label">
                                        <strong><g:message code="admin.license.license_user"/></strong>
                                    </label>
                                    <g:if test="${!preventSalesViewEdit || userManagingAllowed}">
                                        <div class="controls">
                                            <g:textField name="licenseUserEmail"/>
                                            <opt:submit disabled="${disabled && !userManagingAllowed ? true : false}" name="addLicenseUser" value="${message(code: 'add')}"
                                                        class="btn btn-primary btn-admin"/><br />
                                            <g:message code="admin.license.license_user.explanation" /><br />For floating licenses, can check-in to the license, will not apply if not checked in
                                        </div>
                                    </g:if>
                                    <p>&nbsp;</p>
                                    <table class="table table-condensed">
                                        <tbody>
                                        <g:each in="${license?.licenseUsers}" var="user">
                                            <g:if test="${user}">
                                                <tr>
                                                    <td>${user.username}</td>
                                                    <g:if test="${!preventSalesViewEdit || userManagingAllowed}">
                                                        <td>
                                                            <opt:link action="removeLicenseUser" id="${license?.id}"
                                                                      params="[userId: user.id]" class="btn btn-danger"
                                                                      onclick="return modalConfirm(this);"
                                                                      data-questionstr="${message(code: 'admin.license.remove_user.question', args: [user.username])}"
                                                                      data-truestr="${message(code: 'delete')}"
                                                                      data-falsestr="${message(code: 'cancel')}"
                                                                      data-titlestr="${message(code: 'admin.license.remove_user.header')}"><g:message
                                                                    code="delete"/></opt:link>
                                                        </td>
                                                    </g:if>
                                                </tr>
                                            </g:if>
                                        </g:each>
                                        </tbody>
                                    </table>

                                    <g:if test="${licenseInvitations}">
                                        <strong><g:message code="license.invitations" /></strong>
                                        <table class="table table-condensed">
                                            <tbody>
                                            <g:each in="${licenseInvitations}" var="invitation">
                                                <tr>
                                                    <td>${invitation.email}</td>
                                                    <g:if test="${!preventSalesViewEdit}">
                                                        <td>
                                                            <opt:link action="removeInvitedUser" id="${license?.id}"
                                                                      params="[invitationId: invitation.id.toString()]" class="btn btn-danger"
                                                                      onclick="return modalConfirm(this);"
                                                                      data-questionstr="${message(code: 'admin.license.remove_user.question', args: [invitation.email])}"
                                                                      data-truestr="${message(code: 'delete')}"
                                                                      data-falsestr="${message(code: 'cancel')}"
                                                                      data-titlestr="${message(code: 'admin.license.remove_user.header')}"><g:message
                                                                    code="delete"/></opt:link>
                                                        </td>
                                                    </g:if>
                                                </tr>
                                            </g:each>
                                            </tbody>
                                        </table>
                                    </g:if>
                                </div>
                            </g:form>
                        </fieldset>
                    </g:if>

                    <g:if test="${license.isFloatingLicense && floatingUserManagingAllowed}">
                        <div class="control-group">
                            <label for="email" class="control-label">
                                <strong>${message(code:'current.floating.users')} ${license.floatingLicenseCheckedInUsers?.size() ?: 0} / ${license.floatingLicenseMaxUsers}</strong>
                            </label>
                            <table class="table table-condensed">
                                <tbody>
                                <g:each in="${license.floatingLicenseCheckedInUsers}" var="user">
                                    <tr><td>${user.username}</td>
                                        <td>
                                        <opt:link action="checkoutFloatingUser" id="${license.id}"
                                                  params="[userId: user.userId]" class="btn btn-warning"
                                                  onclick="return modalConfirm(this);"
                                                  data-questionstr="Are you sure you want to release user: ${user.username}"
                                                  data-truestr="Release"
                                                  data-falsestr="${message(code: 'cancel')}"
                                                  data-titlestr="Release floating user?">Release</opt:link>
                                        </td>
                                    </tr>
                                </g:each>
                                </tbody>
                            </table>
                        </div>
                    </g:if>
                </div>
            </g:if>
        </div>
    </div>
</div>


<sec:ifAnyGranted roles="ROLE_SALES_VIEW">
    <g:if test="${license?.id}">
        <div class="container section">
            <div class="sectionbody">
                <div class="btn-group">
                    <a href="javascript:;" class="btn btn-primary" id="showEntities" onclick="viewAllEntitiesOfLicense(this)">Show all entities of license <i class="hidden fas fa-circle-notch fa-spin white-font"></i></a>
                    <a href="javascript:;" class="btn btn-primary" id="showRemovedOnes" onclick="viewRemovedEntitiesOfLicense(this)">Show all removed entities of license <i class="hidden fas fa-circle-notch fa-spin white-font"></i></a>
                </div>


                <div class="row-fluid row-bordered" style="display: none;" id="entitiesDiv">
                    <div class="span12">
                        <h3><g:message code="admin.license.entities.title"/></h3>
                        <g:form action="addObjects" useToken="true">
                            <g:hiddenField name="id" value="${license?.id}"/>

                            <fieldset>
                                <div class="control-group">
                                    <label for="entityId" class="control-label">
                                        <strong><g:message code="admin.license.licensedEntities"/></strong>
                                    </label>
                                    <g:if test="${!preventSalesViewEdit}">
                                        <div class="controls">
                                            <select name="entityId" id="entityIds" disabled style="margin-bottom: 0px;">
                                                <g:if test="${!disabled}">
                                                    <option disabled selected>
                                                        <g:message code="admin.license.loading" default="Loading... Please, wait."/>
                                                    </option>
                                                </g:if>
                                            </select>
                                            <opt:submit name="addEntity" value="${message(code: 'add')}" class="btn btn-primary"/>
                                            <a href="javascript:" class="btn btn-info" onclick="window.open('${createLink(action: 'getResourceUsageByEntities', params: [id: license?.id] )}', '_blank', 'width=1024, height=768, scrollbars=1');">Show resource usage</a>
                                        </div>
                                    </g:if>
                                    <p>&nbsp;</p>
                                    <table class="table table-condensed" id="licensedEntitiesList">
                                        <thead>
                                        <tr>
                                            <th><g:message code="admin.license.entity"/></th>
                                            <th>ID</th>
                                            <th><g:message code="admin.license.entityChangeHistory.anniversaryDate"/></th>
                                            <th><g:message code="admin.license.entityChangeHistory.dateAdded"/></th>
                                            <th><g:message code="admin.license.entityChangeHistory.addedBy"/></th>
                                            <g:if test="${com.bionova.optimi.core.Constants.LicenseType.TRIAL.toString().equals(license?.type)}">
                                                <th class="no-sort">Change trial expiry date</th>
                                            </g:if>
                                            <g:if test="${!preventSalesViewEdit}">
                                                <th class="no-sort"><g:message code="delete"/></th>
                                            </g:if>
                                        </tr>
                                        </thead>
                                        <tbody id="licensedEntities">

                                        </tbody>
                                    </table>


                                    <table class="table table-condensed" id="licensedRemovedEntities">
                                        <thead>
                                        <tr><th colspan="8"><g:message code="admin.license.removedEntities"/></th></tr>

                                        <tr>
                                            <th><g:message code="admin.license.entity"/></th>
                                            <th>ID</th>
                                            <th><g:message code="admin.license.entityChangeHistory.anniversaryDate"/></th>
                                            <th><g:message code="admin.license.entityChangeHistory.dateAdded"/></th>
                                            <th><g:message code="admin.license.entityChangeHistory.addedBy"/></th>
                                            <th><g:message code="admin.license.entityChangeHistory.dateRemoved"/></th>
                                            <th><g:message code="admin.license.entityChangeHistory.removedBy"/></th>
                                        </tr>
                                        </thead>
                                        <tbody id="removedEntities">

                                        </tbody>
                                    </table>
                                </div>
                            </fieldset>
                        </g:form>
                    </div>
                </div>
            </div>
        </div>
    </g:if>
</sec:ifAnyGranted>

<script type="text/javascript">
    var isMobile = (/Mobi|Android/i.test(navigator.userAgent))

    $("#licensedEntitiesList").on('draw.dt', function () {
        $(".expiryDatePicker").datepicker({
            dateFormat: 'dd.mm.yy',
            changeMonth: true,
            changeYear: true
        });
    });

    $("body").on('DOMSubtreeModified', "#backgroundImageContainer", function(){
        validateFileSizeAndReload(true) // reload page if change image size > 1MB, to see the current image
    });

    function validateFileSizeAndReload(reloadPage) {
        $("input[type='file']").on("change", function () {
            if(this.files[0].size > 1000000) {
                alert("Please upload file less than 1 MB");
                $(this).val('');
                if (reloadPage) {
                    window.location.reload()
                }
            }
        });
    }

    function generateLicenseKey(length, idOfInput) {
        var licenseKey = $(idOfInput);
        var text = "";
        var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (var i = 0; i < length; i++) {
            text += possible.charAt(Math.floor(Math.random() * possible.length));
        }
        $(licenseKey).val(text);
        $('#licenseKeyControls').popover("show");
        setTimeout(function () {
            $('#licenseKeyControls').popover('hide')
            ;
        }, 1000);
    }

    $(document).ready(function () {
        $('#countrySelect').select2().maximizeSelect2Height();
        $('#conditionalCountrySelect').select2().maximizeSelect2Height();
        $('#conditionalStateSelect').select2().maximizeSelect2Height();
        $('#conditionalBuildingTypesSelect').select2().maximizeSelect2Height();
        $('#conditionalEntityClassesSelect').select2().maximizeSelect2Height();
        $('#newOrganization').select2().maximizeSelect2Height();

        $("#floatingLicenseSelect").on('change', function () {
            var value = $(this).val();

            if ('no' == value) {
                $("#floatingLicenseScopeDiv").hide();
                $("#floatingLicenseMaxUsersDiv").hide();
            } else {
                $("#floatingLicenseScopeDiv").show();
                $("#floatingLicenseMaxUsersDiv").show();
            }
        });

        $("#renewalStatus").on('change', function () {
            var value = $(this).val();

            if ('autoRenewal' == value) {
                $("#renewalDateChoice").show();
            } else {
                $("#renewalDateChoice").hide();
            }
        });

        $("#type").on('change', function () {
            var value = $(this).val();

            if ('Trial' == value || 'Education' == value) {
                $("#lengthForTrial").show();
            } else {
                $("#lengthForTrial").hide();
            }

            if ('Education' == value) {
                $("#joinCodeDiv").show();
            } else {
                $("#joinCodeDiv").hide();
            }
        });
        <g:if test="${!disabled}">
            getChoosableListEntities();
        </g:if>
        if(!isMobile && $('#licensedEntitiesList').length){
            viewAllEntitiesOfLicense($("#showEntities"))
            viewRemovedEntitiesOfLicense($("#showRemovedOnes"))
            $("#showEntities").addClass("hidden")
            $("#showRemovedOnes").addClass("hidden")
        }

        validateFileSizeAndReload(false)
    });
    function getChoosableListEntities(){
        $('#entitiesDiv').show();
        $.ajax({
            async: true, type: 'POST',
            data: 'id=${license?.id?.toString()}',
            url: '/app/sec/license/getChoosableEntities',
            success: function (data, textStatus) {
                if (data.entities) {
                    var selectElement = $('#entityIds');
                    selectElement.removeAttr('disabled');
                    selectElement.children().remove();
                    selectElement.select2({
                        data: data.entities
                    }).maximizeSelect2Height();
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
            }
        });
    }
    function viewAllEntitiesOfLicense(element){
        var loadIcon = $(element).find("i")
        $.ajax({
            async: true, type: 'POST',
            data: 'id=${license?.id?.toString()}',
            url: '/app/sec/license/getLicensedEntities',
            beforeSend: function () {
                $(loadIcon).removeClass("hidden");
            },
            success: function (data, textStatus) {
                $('#licensedEntities').empty();
                if (data) {
                    $('#licensedEntities').append(data);
                    sortableTable('#licensedEntitiesList', true);

                    $(".expiryDatePicker").datepicker({
                        dateFormat: 'dd.mm.yy',
                        changeMonth: true,
                        changeYear: true
                    });
                }
                $(loadIcon).addClass("hidden");
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                $(loadIcon).addClass("hidden");
            }
        });
    }
    function viewRemovedEntitiesOfLicense(element){
        var loadIcon = $(element).find("i")
        $.ajax({
            async: true, type: 'POST',
            data: 'id=${license?.id?.toString()}',
            url: '/app/sec/license/getRemovedEntities',
            beforeSend: function () {
                $(loadIcon).removeClass("hidden");
            },
            success: function (data, textStatus) {
                $('#removedEntities').empty();
                if (data) {
                    $('#removedEntities').append(data);
                    sortableTable('#licensedRemovedEntities',true);
                }
                $(loadIcon).addClass("hidden");
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                $(loadIcon).addClass("hidden");

            }
        });
    }
</script>

<g:if test="${session?.validBrowser && !session?.wrongIE}">
    <script type="text/javascript">

        $(document).ready(function () {
            var validFrom = document.getElementById("validFrom").value;
            var day = validFrom.substring(0, 2);
            var month = validFrom.substring(3, 5);
            var year = validFrom.substring(6, 10);
            var startDate = new Date(year, month - 1, day);
            $("#validUntil").datepicker({
                dateFormat: 'dd.mm.yy',
                changeMonth: true,
                changeYear: true,
                minDate: startDate
            });

            $("#validFrom").datepicker({
                dateFormat: 'dd.mm.yy',
                changeMonth: true,
                changeYear: true,
                onSelect: function (date) {
                    var day = date.substring(0, 2);
                    var month = date.substring(3, 5);
                    var year = date.substring(6, 10);
                    var startDate = new Date(year, month - 1, day);
                    $("#validUntil").datepicker("option", "minDate", startDate);
                }
            });

            $("#renewalDate").datepicker({
                dateFormat: 'dd.mm',
                changeMonth: true,
                changeYear: false
            });
        });

        function changeTrialExpiryDate(entityId, licenseId) {
            if (entityId && licenseId) {
                var newDate = document.getElementById("expiryDate" + entityId).value;
                var saveContainer = $('#saveContainer' + entityId);

                if (newDate) {
                    $.ajax({
                        async: false, type: 'POST',
                        data: 'licenseId=' + licenseId + '&entityId=' + entityId + '&newDate=' + newDate,
                        url: '/app/sec/license/changeTrialExpiryDate',
                        success: function (data, textStatus) {
                            if (data === "ok") {
                                saveContainer.text('');
                                saveContainer.append("<i class=\"icon-done\"></i>");
                            } else {
                                saveContainer.text('');
                                saveContainer.append("<i class=\"fa fa-times redScheme\" aria-hidden=\"true\"></i>");
                            }
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            saveContainer.text('');
                            saveContainer.append("<i class=\"fa fa-times redScheme\" aria-hidden=\"true\"></i>");
                        }
                    });
                }
            }
        }
    </script>
</g:if>
</body>
</html>
