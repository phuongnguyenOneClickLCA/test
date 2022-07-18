<%-- Copyright (c) 2012 by Bionova Oy --%>
<!doctype html>
<html xmlns="http://www.w3.org/1999/html">
  <head>
	<meta name="layout" content="main"/>
      <meta name="format-detection" content="telephone=no"/>
  </head>
  <body>
    <g:set var="indicatorService" bean="indicatorService"/>
    <g:set var="licenseTemplateService" bean="licenseTemplateService"/>
    <div class="container">
        <div class="screenheader">
            <sec:ifAnyGranted roles="ROLE_SUPER_USER">
                <div class="pull-right hide-on-print">
                    <opt:link controller="license" action="licenseTemplate" class="btn btn-primary"><g:message code="add" /> Template</opt:link>
                    <opt:link controller="license" action="form" class="btn btn-primary"><g:message code="add" /> License</opt:link>
                </div>
            </sec:ifAnyGranted>
            <h1><g:message code="admin.license.header" /></h1>
        </div>
    </div>
    <div class="container section">
        <a href="javascript:" onclick="showTrClassForced('all');"><g:message code="show.all" /></a><br />
        <a href="javascript:" onclick="showTrClassForced('commercial'); hideTrClassForced('noncommercial');" id="showCommercial"><g:message code="admin.license.show.commercial" /></a><br />
        <a href="javascript:" onclick="showTrClassForced('noncommercial'); hideTrClassForced('commercial');" id="showNonCommercial"><g:message code="admin.license.show.noncommercial" /></a><br />

    <sec:ifAnyGranted roles="ROLE_SALES_VIEW">
        <div style="margin-top: 10px;">
        <strong><g:message code="admin.license.active_per_entity_class" />:</strong><br />
        <g:each in="${activeLicensesPerEntityClass}">
            ${it.key}: ${it.value}<br />
        </g:each>
        </div>
    </sec:ifAnyGranted>
  <sec:ifAnyGranted roles="ROLE_SUPER_USER">
      <div class="getusersPerLicense" style="margin-top:10px;">
          <i>Exports only enabled users</i><br />
          <a href="javascript:" class="btn btn-primary" id="moreSettingsBtn"><i id="toggler" class="icon icon-white icon-plus"></i> <i class="fa fa-address-book"></i> Get building entity users by params (super user)</a>

          <div id="moreSettings" class="hidden">
              <div style="margin-top: 5px">
                  <label for="entityClassSelect"><strong>Entity class:</strong></label>
                  <select class="singleSelect" name="entityClass" id="entityClassSelect">
                      <option value=""></option>
                      <g:each in="${activeLicensesPerEntityClass.keySet()}" var="entityClass">
                          <option value="${entityClass}">${entityClass}</option>
                      </g:each>
                  </select>
              </div>
              <div style="margin-top: 5px">
                  <label for="licenseTypeSelect"><strong>License Type:</strong></label>
                  <select class="singleSelect" name="licenseType" id="licenseTypeSelect">
                      <option value=""></option>
                      <g:each in="${activeLicenses.collect({it.type})?.unique()}" var="licenseType">
                          <option value="${licenseType}">${licenseType}</option>
                      </g:each>
                  </select>
              </div>
              <div style="margin-top: 5px">
                  <label for="activeSelect"><strong>Active:</strong></label>
                  <select class="singleSelect" name="active" id="activeSelect">
                      <option value=""></option>
                      <option value="true">True</option>
                      <option value="false">False</option>
                  </select>
              </div>
              <div style="margin-top: 5px">
                  <label for="featureIdSelect"><strong>License template (license has all selected templates):</strong></label>
                  <select class="multiSelect" data-tableId="licenseTemplateTable" data-paramName="licenseTemplateId" name="licenseTemplateIdSelect" id="licenseTemplateIdSelect" multiple>
                      <g:each in="${licenseTemplates}" var="licenseTemplate">
                          <option value="${licenseTemplate?.id?.toString()}">${licenseTemplate?.name}</option>
                      </g:each>
                  </select>
              </div>
              <div style="margin-top: 5px">
                  <label for="featureIdSelect"><strong>Feature:</strong></label>
                  <g:select class="singleSelect" data-tableId="featureTable" data-paramName="featureId" name="featureIdSelect" from="${features}"
                            optionKey="id" optionValue="${{ it.name }}" noSelection="['': '']"/>
              </div>
              <div style="margin-top: 5px">
                <label for="indicatorIdSelect"><strong>Indicator:</strong></label>
                  <select class="multiSelect" data-tableId="indicatorIdOperatingTable" data-paramName="indicatorId" name="indicatorIdSelect" id="indicatorIdSelect" multiple>
                      <g:each in="${indicators?.sort({indicatorService.getLocalizedName(it).toLowerCase()})}" var="indicator">
                          <option value="${indicator?.indicatorId}">${indicatorService.getLocalizedName(indicator)}</option>
                      </g:each>
                  </select>
              </div>
              <div style="margin-top: 5px">
                  <table>
                      <tr>
                          <td><input type="checkbox" name="getManagers" value="true" id="getManagers"></td>
                          <td><label for="getManagers"><strong>Get users from projects covered by license instead of license users</strong></label></td>
                      </tr>
                  </table>
              </div>
              <a href="javascript:" class="btn btn-primary" style="margin-top: 5px;" id="getUsersByparams" onclick="getLicensedUsersByType();">Get users</a>
              <div id="putShitHere"></div>
          </div>
      </div>
  </sec:ifAnyGranted>
    </div>

  <div class="container section" style="margin-top: 30px;">
        <div class="sectionbody">
            <sec:ifAnyGranted roles="ROLE_SALES_VIEW">
            <h2><g:link action="licenseTiers">License tiers</g:link></h2>
            <h2><g:link action="licenseFeatures">License features</g:link></h2>
            <h2>License templates</h2>
            <table class="table table-striped table-condensed" id="licenceTemplatesTable">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Indicators</th>
                    <th>Features</th>
                    <th>Workflows</th>
                    <th>Used in</th>
                </tr>
                </thead>
                <tbody>
                <g:each in="${licenseTemplates}" var="licenseTemplate">
                    <tr>
                        <td><g:link action="licenseTemplate" id="${licenseTemplate.id}">${licenseTemplate.name}</g:link></td>
                        <td>${licenseTemplate.licensedIndicatorIds?.size() ?: 0}</td>
                        <td>${licenseTemplate.licensedFeatureIds?.size() ?: 0}</td>
                        <td>${licenseTemplate.licensedWorkFlowIds?.size() ?: 0}</td>
                        <td><g:link action="licenseTemplateLicenses" params="[id: licenseTemplate.id]" target="_blank">${licenseTemplateService.getUsedInLicensesCount(licenseTemplate.id) ?: 0}</g:link></td>
                    </tr>
                </g:each>
                </tbody>
            </table>
            </sec:ifAnyGranted>

  <h2 style="margin-top: 30px;"><g:message code="admin.license.active_licenses" /></h2>
		  <table class="table table-striped table-condensed" id="activeLicencesTable">
		    <thead>
			  <tr>
                <th>ADMIN_Organizations</th>
				<th><g:message code="admin.license.name" /></th>
                <th><g:message code="admin.license.type" /></th>
                  <th>Contract #</th>
                  <th>Client #</th>
                  <th><g:message code="admin.renewal.date"/></th>
				<th><g:message code="admin.license.validity" /></th>
				<th><g:message code="admin.license.licensedEntities" /> / <g:message code="admin.maximumEntitites"/></th>
                  <th><g:message code="admin.license.compatibleEntityClasses" /></th>
                  <th><g:message code="admin.license.licensedIndicators" /></th>
                  <th><g:message code="admin.license.licensedFeatures" /></th>
                  <th>Floating users</th>
                  <th>No. users</th>

				<th class="no-sort">&nbsp;</th>
			  </tr>
			</thead>
  			<tbody>    
		    <g:each in="${activeLicenses}" var="license">
			  <tr class="all ${'Production'.equals(license.type) ? 'commercial' : 'noncommercial'}">
                <td>
                    <g:each in="${license.accounts}" var="account">
                        <g:link controller="account" action="form" id="${account.id}">${account.companyName}</g:link><br />
                    </g:each>
                </td>
                <td>
                    <opt:link controller="license" action="form" params="[id: license.id]">${license.name}</opt:link>
				</td>
                <td class="text-center"><g:message code="license.type.${license.type}" /></td>
                  <td>${license.contractNumberForUI}</td>
                  <td>${license.clientNumber ?: ""}</td>
                  <td class="text-center">
                      <span class="hidden">${license.nextRenewalDate?.format("MMdd")}</span>
                      ${"autoRenewal".equals(license.renewalStatus) ? "${license.renewalDate} (${license.daysToNextRenewal})" : "${license.renewalStatus ?: ""}"}
                  </td>
				<td class="text-center">
                    <span class="hidden">${license?.validUntil?.format("yyyyMMdd")}</span><g:formatDate date="${license.validUntil}" format="dd.MM.yyyy" />
                </td>
				<td class="text-center">${license.maxEntities ? "${license.licensedEntityCount} / ${license.maxEntities}" : "${license.licensedEntityCount}"}</td>
                  <td class="text-center">${license.compatibleEntityClasses}</td>
                  <td class="text-center">${license.licensedIndicatorCount}</td>
                  <td class="text-center">${license.licensedFeatureCount}</td>
                  <td class="text-center">${license.isFloatingLicense ? license.floatingLicenseMaxUsers : ''}</td>
                  <td class="text-center">${license.licenseUserIds?.size() ?: ''}</td>

				<td>
                  <sec:ifAnyGranted roles="ROLE_SUPER_USER">
                     <a href="javascript:" onclick="window.open('${createLink(action: "changeHistory", id: license.id)}', '_blank', 'width=2048, height=1024, scrollbars=1');"><i class="fas fa-search-plus"></i></a>
                  </sec:ifAnyGranted>
                  <sec:ifAnyGranted roles="ROLE_SYSTEM_ADMIN">
				  <opt:link controller="license" action="remove" id="${license.id}" class="btn btn-danger" onclick="return modalConfirm(this);"
				    data-questionstr="${message(code:'admin.license.delete_question')}" data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}" data-titlestr="${message(code:'admin.license.delete_confirm.header')}"><g:message code="delete" /></opt:link>
                  </sec:ifAnyGranted>
                </td>
			  </tr>
			</g:each>
            </tbody>
          </table>
          <div id="inactiveLicenses" style="margin-top: 30px;">
            <h2><g:message code="admin.license.inactive_licenses" /></h2>
            <table class="table table-striped table-condensed" id="inactiveLicensesTable">
                <thead>
                <tr>
                    <th>ADMIN_Organizations</th>
                    <th><g:message code="admin.license.name" /></th>
                    <th><g:message code="admin.license.type" /></th>
                    <th>Contract #</th>
                    <th>Client #</th>
                    <th><g:message code="admin.license.validity" /></th>
                    <th><g:message code="admin.license.licensedEntities" /> / <g:message code="admin.maximumEntitites"/></th>
                    <th><g:message code="admin.license.compatibleEntityClasses" /></th>
                    <th><g:message code="admin.license.licensedIndicators" /></th>
                    <th><g:message code="admin.license.licensedFeatures" /></th>
                    <th>Floating users</th>
                    <th>No. users</th>

                    <th class="no-sort">&nbsp;</th>
                </tr>
                </thead>
                <tbody>
                <g:each in="${expiredLicenses}" var="license">
                    <tr class="all ${'Production'.equals(license.type) ? 'commercial' : 'noncommercial'}">
                      <td>  <g:each in="${license.accounts}" var="account">
                            <g:link controller="account" action="form" id="${account.id}">${account.companyName}</g:link><br />
                        </g:each>
                        <td>
                            <opt:link controller="license" action="form" params="[id: license.id]">${license.name}</opt:link>
                        </td>
                        <td class="text-center"><g:message code="license.type.${license.type}" /></td>
                        <td>${license.contractNumberForUI}</td>
                        <td>${license.clientNumber ?: ""}</td>
                        <td class="text-center">
                            <span class="hidden">${license?.validUntil?.format("yyyyMMdd")}</span>
                            <g:if test="${license.expired}">
                                <g:message code="expired.upperCase" />
                            </g:if>
                            (<g:formatDate date="${license?.validUntil}" format="dd.MM.yyyy" />)
                        </td>
                        <td class="text-center">${license.maxEntities ? "${license.licensedEntityCount} / ${license.maxEntities}" : "${license.licensedEntityCount}"}</td>
                        <td class="text-center">${license.compatibleEntityClasses}</td>
                        <td class="text-center">${license.licensedIndicatorCount}</td>
                        <td class="text-center">${license.licensedFeatureCount}</td>
                        <td class="text-center">${license.isFloatingLicense ? license.floatingLicenseMaxUsers : ''}</td>
                        <td class="text-center">${license.licenseUserIds?.size() ?: 'N/A'}</td>

                        <td>
                            <sec:ifAnyGranted roles="ROLE_SYSTEM_ADMIN">
                                <opt:link controller="license" action="remove" id="${license.id}" class="btn btn-danger" onclick="return modalConfirm(this);"
                                          data-questionstr="${message(code:'admin.license.delete_question')}" data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}" data-titlestr="${message(code:'admin.license.delete_confirm.header')}"><g:message code="delete" /></opt:link>
                            </sec:ifAnyGranted>
                        </td>
                    </tr>
                </g:each>
                </tbody>
            </table>
          </div>
          <%-- FROZEN LICENZE --%>
          <g:if test="${frozenLicenses}" >
            <div id="inactiveLicenses" style="margin-top: 30px;">
                <h2><g:message code="admin.license.frozen_licenses" /></h2>
                <table class="table table-striped table-condensed" id="frozenLicensesTable">
                    <thead>
                    <tr>
                        <th>ADMIN_Organizations</th>
                        <th><g:message code="admin.license.name" /></th>
                        <th><g:message code="admin.license.type" /></th>
                        <th>Contract #</th>
                        <th>Client #</th>
                        <th><g:message code="admin.license.validity" /></th>
                        <th><g:message code="admin.license.licensedEntities" /> / <g:message code="admin.maximumEntitites"/></th>
                        <th><g:message code="admin.license.compatibleEntityClasses" /></th>
                        <th><g:message code="admin.license.licensedIndicators" /></th>
                        <th><g:message code="admin.license.licensedFeatures" /></th>
                        <th class="no-sort">&nbsp;</th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${frozenLicenses}" var="license">
                        <tr class="all ${'Production'.equals(license.type) ? 'commercial' : 'noncommercial'}">
                            <td><g:each in="${license.accounts}" var="account">
                                <g:link controller="account" action="form" id="${account.id}">${account.companyName}</g:link><br />
                            </g:each>
                            <td>
                                <opt:link controller="license" action="form" params="[id: license.id]">${license.name}</opt:link>
                            </td>
                            <td class="text-center"><g:message code="license.type.${license.type}" /></td>
                            <td>${license.contractNumberForUI}</td>
                            <td>${license.clientNumber ?: ""}</td>
                            <td class="text-center">
                                <span class="hidden">${license?.validUntil?.format("yyyyMMdd")}</span>
                                <g:if test="${license.expired}">
                                    <g:message code="expired.upperCase" />
                                </g:if>
                                (<g:formatDate date="${license?.validUntil}" format="dd.MM.yyyy" />)
                            </td>
                            <td class="text-center">${license.maxEntities ? "${license.licensedEntityCount} / ${license.maxEntities}" : "${license.licensedEntityCount}"}</td>
                            <td class="text-center">${license.compatibleEntityClasses}</td>
                            <td class="text-center">${license.licensedIndicatorCount}</td>
                            <td class="text-center">${license.licensedFeatureCount}</td>
                            <td>
                                <sec:ifAnyGranted roles="ROLE_SYSTEM_ADMIN">
                                    <opt:link controller="license" action="remove" id="${license.id}" class="btn btn-danger" onclick="return modalConfirm(this);"
                                              data-questionstr="${message(code:'admin.license.delete_question')}" data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}" data-titlestr="${message(code:'admin.license.delete_confirm.header')}"><g:message code="delete" /></opt:link>
                                </sec:ifAnyGranted>
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
          </g:if>
          <%-- FROZEN LICENZE --%>

	    </div>
	</div>


  <script type="text/javascript">
      $('#moreSettingsBtn').on('click', function () {
          var userAgent = navigator.userAgent;
          var form = $('#moreSettings');
          var toggler = "#toggler";
          $(toggler).toggleClass('icon-plus icon-minus');

          if (userAgent.toLowerCase().indexOf("mac") >= 0) {
              if ($(toggler).hasClass('icon-plus')) {
                  $(form).addClass('hidden');
              } else {
                  $(form).removeClass('hidden');
              }
          } else {
              if ($(toggler).hasClass('icon-plus')) {
                  $(form).slideUp().addClass('hidden');
              } else {
                  $(form).slideDown().removeClass('hidden');
              }
          }
      });

      $(function () {
          var allSelects = $('.singleSelect');
          if (allSelects) {
              $(allSelects).select2({
                  placeholder:'<g:message code="select"/>',
                  allowClear: true
              }).maximizeSelect2Height();
          }
          var multiSelects = $('.multiSelect');
          if (multiSelects) {
              $(multiSelects).select2({
                  placeholder:'<g:message code="select"/>'
              }).maximizeSelect2Height();
          }
      });
      $(function () {
          sortableTable('#activeLicencesTable');
          sortableTable('#inactiveLicensesTable');
      });
      function getLicensedUsersByType() {
          if (!$("#getUsersByparams").attr('disabled')) {
              const entityClass = $('#entityClassSelect').val();
              const licenseType = $('#licenseTypeSelect').val();
              const active = $('#activeSelect').val();
              const featureId = $('#featureIdSelect').val();
              const indicatorIds = $('#indicatorIdSelect').val();
              const licenseTemplateId = $('#licenseTemplateIdSelect').val();
              const getManagers = $('#getManagers').is(':checked');
              const json = {
                  entityClass : entityClass,
                  licenseType : licenseType,
                  active: active,
                  featureId: featureId,
                  indicatorIds: indicatorIds,
                  getManagers: getManagers,
                  licenseTemplateIds: licenseTemplateId
              };

              $.ajax({
                  url: '/app/sec/license/getLicenseTypeUsers',
                  type:"POST",
                  data: JSON.stringify(json),
                  contentType: "application/json; charset=utf-8",
                  beforeSend: function() {
                      $('#putShitHere').empty().append('<i style="margin-left:45px; margin-top:10px;" class="fa fa-2x fa-circle-o-notch fa-spin"></i>');
                      $("#getUsersByparams").attr("disabled", true);
                  },
                  success: function (data) {
                      $('#putShitHere').empty().append(data);
                      $("#getUsersByparams").attr("disabled", false);
                  }
              });
          }
      }
  </script>

  </body>
</html>