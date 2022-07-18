<!doctype html>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<%
    def userService = grailsApplication.mainContext.getBean("userService")
    def optimiResourceService = grailsApplication.mainContext.getBean("optimiResourceService")
%>
<g:if test="${userService.getDataManagerAndAdmin(userService.getCurrentUser())}">

<div class="container">
    <div class="screenheader">
        <h1>
            <g:message code="admin.import.import_resource" />
            ${resources ? '(' + resources.size() + ')' : ''}
        </h1>
    </div>
</div>
<div class="container section">

    <g:if test="${pdfCheckInProgress}">
        <div class="alert alert-success">
            <button class="close" type="button" data-dismiss="alert">×</button>
            <p class="entitySaveMessage">PDF Check now running. You can download the checks results from <g:link action="downloadPDFCheck">here</g:link></p>
        </div>
    </g:if>

    <g:if test="${dryRunNotification}">
        <div style="margin-bottom: 20px;" class="alert">
            <strong>${dryRunNotification}</strong><br />
            <g:link action="clearDryRunResources" class="btn btn-primary"><g:message code="admin.import.dry_run_resources.clear" /></g:link>
        </div>
    </g:if>

        <div>
            <div class="pull-left">
                <g:uploadForm action="uploadExcel">
                    <g:hiddenField name="typeClass" value="${typeClass}"/>
                    <div class="clearfix"></div>

                    <div class="column_left">
                        <div class="control-group">
                            <label for="file" class="control-label">
                                <g:message code="admin.import.excel_file"/>
                            </label>

                            <div class="controls">
                                <input type="file" name="xlsFile" id="file" class="btn" value=""/>
                            </div>
                        </div>

                        <div class="control-group">
                            <label for="file" class="control-label">
                                Dry run to test resources
                                <input type="checkbox" name="dryRun"
                                       value="true" ${dryRun ? ' checked=\"checked\"' : ''}/>
                            </label>
                        </div>

                        <div class="control-group">
                            <label for="isUploadingNmd" class="control-label">
                                I am uploading NMD resources.
                                <span class="triggerPopover" style="padding-left: 5px"
                                      data-content="${message(code: 'nmdResourceUpload.reminder.help')}">
                                    <i class=" icon-question-sign"></i>
                                </span>
                                <g:checkBox name="isUploadingNmd"/>
                            </label>
                        </div>
                    </div>

                    <div class="clearfix"></div>
                    <opt:submit name="import" onclick="removeClicks(this); showPopupMsg();" value="${message(code: 'import')}"
                                class="btn btn-primary"/>
                </g:uploadForm>
            </div>
            <div class="pull-right" style="max-width: 900px">
                <div class="column_right">
                    <label style="width: 200px;" for="setMultipleProfiles" class="control-label">Set multipleProfiles for all resources (ran automatically on upload)</label>
                    <div class="controls"><i class="fa fa-magic fa-5x magicWand" id="setMultipleProfiles" onclick="setMultipleProfiles();"></i></div>
                </div>

                <div class="column_right">
                    <label style="width: 200px;" for="setIsoCountryCodes" class="control-label">Set isoCountryCodes for all resources (ran automatically on upload)</label>
                    <div class="controls"><i class="fa fa-flag fa-5x magicWand" id="setIsoCountryCodes" onclick="setIsoCountryCodes();"></i></div>
                </div>

                <div class="column_right">
                    <label style="width: 200px;" for="setResourceTypes" class="control-label">Set resourceTypes for all resources (ran automatically on upload)</label>
                    <div class="controls"><i class="fa fa-rocket fa-5x magicWand" id="setResourceTypes" onclick="setResourceTypes();"></i></div>
                </div>
            </div>

        <div class="clearfix"></div>

    <g:if test="${!dryRunNotification}">
        <g:if test="${!isProd}">
        <div>
            <g:if test="${inactive}">
                <g:message code="admin.import.show_inactive" /><br />
            </g:if>
            <g:else>
                <g:link action="showInactiveResources"><g:message code="admin.import.show_inactive" /></g:link><br />
            </g:else>
            <g:link action="form" params="[resourceWithMultipleProfiles: true]"><g:message code="admin.import.resourceWithMultipleProfiles" /></g:link><br />
            <g:link action="duplicates">Duplicate resourceId and profileId</g:link><br />
            <g:link action="ungrouped"><g:message code="admin.import.ungrouped_resources" /></g:link><br />
            <g:link action="noprofiles"><g:message code="admin.import.no_profiles_resources" /></g:link><br />
            <g:link action="showResourcesWithoutApp"><g:message code="admin.import.show_without_application" /></g:link><br />
            <g:link action="showDuplicateStaticFullNames">Show duplicate static full names</g:link><br />
            <g:link action="showDuplicateStaticFullNames" params="[exportExcel: true]">Export duplicate static full names resources to Excel</g:link><br />
            <g:link action="showResourcesWithUnusualThickness">Resources with unusual default thickness</g:link><br />
            <g:link action="showWasteAndBenefitWithoutKilogramUnit">Transformation resource unit check</g:link><br />
            <g:link action="showMultipleDefaultProfiles">Resources with multiple default profiles</g:link><br />
            <g:link action="showFailingMassConversionFactor" params="[active: true]">Resources with unitForData m2 failing density*(thickness/1000) == massConversionFactor (5% margin)</g:link> <g:link action="showFailingMassConversionFactor" style="color: orange;" params="[active: false]">(Inactive resources)</g:link><br />
            <g:link action="showThicknessesNotMatching">Resources with defaultThickness_mm and defaultThickness_in not matching</g:link><br />
            <g:link action="showNoDefaultProfile">Resources with no defaultProfile</g:link><br />
        </div>
    </g:if>
        <div>
            <g:form name="checkBrokenDownloadLinks" action="checkBrokenDownloadLinks">
                <select name="downloadLinkEpdProgram" style="height: 24px; width: 150px;">
                    <option value="all">All</option>
                    <g:each in="${epdPrograms}" var="epdProgram">
                        <g:if test="${!"-".equals(epdProgram)}">
                            <option value="${epdProgram}">${epdProgram}</option>
                        </g:if>
                    </g:each>
                </select>
                <g:if test="${!isProd}">
                <a href="javascript:" onclick="$('#checkBrokenDownloadLinks').trigger('submit');">Check for broken downloadLinks</a>
                </g:if>
                <br />
            </g:form>
        </div>
    </g:if>
    <g:link action="runFilterRules" class="btn btn-primary inline">Run filterRules</g:link>
    <p></p>
    <opt:link controller="util" action="dumpAllLcaResources" class="btn btn-primary">Dump Epds</opt:link>
    <p></p>
    <p>
        <h3>Find resources</h3>
        <div class="clearfix"></div>
        <div class="input-append" style="margin-top: 10px;">
            <label for="searchByResourceId"><h5>Find resource by resourceId or database ID:</h5></label>
            <input type="text" id="searchByResourceId" class="input-large" placeholder="resourceId / database ID" /><btn class="btn btn-primary" id="searchByResourceIdButton"><i class="icon-search icon-white"></i></btn>
        </div>

        <div style="margin-top: 15px;"><h5>Find resource by applicationId and resourceGroup:</h5></div>

        <div style="float: left;">
            <label for="applicationId">ApplicationId:</label>
            <select name="applicationId" id="applicationId" style="width: 150px;">
                <option></option>
                <g:each in="${applicationIdAndSupportedResourceGroups}">
                    <option value="${it.key}" data-supportedResourceGroups="${it.value?.join(",") ?: ""}">${it.key}</option>
                </g:each>
            </select>
        </div>

        <div style="float: left; margin-left: 15px;">
            <label for="resourceGroup">ResourceGroup:</label>
            <select name="resourceGroup" id="resourceGroup" style="width: 150px;">
                <option></option>
                <g:each in="${resourceGroups}">
                    <option value="${it}" style="display: none">${it}</option>
                </g:each>
            </select>
        </div>

        <div style="float: left; margin-left: 15px; margin-top: 22px;">
            <a href="javascript:" onclick="findResources(this);" class="btn btn-primary">Find Resources</a>
        </div>
    </p>

    <div class="clearfix"></div>
    <div id="searchFieldContent" class="input-append" style="margin-top: 10px;${!resources ? ' display: none;' : ''}">
        <label for="searchFieldText"><i class="fas fa-search"></i> Search resources in the bottom table</label> <input type="text" id="searchFieldText" class="input-large" placeholder="Search resources" />

        <g:if test="${showDeleteAll}">
            <g:link action="deleteAllNonUsedInactive" class="btn btn-danger">DELETE ALL</g:link>
        </g:if>
    </div>
    <g:if test="${'Duplicates'.equals(resourceType)}">
        <g:render template="duplicateResources" model="[resources: resources]" />
    </g:if>
    <g:else>

        <opt:spinner/>

    <table class="resource" id="resulttable">
        <tbody id="resourceList">
        <g:if test="${unusualThicknessResources}"><%--
          --%><tr id="resourceListHeading">
                <th>Count</th><th>ResourceId</th><th>ProfileId</th><th>Active</th><th>Virtual</th><th>defaultThickness_mm</th><th>defaultThickness_in (mm)</th><th>maxThickness_mm</th><th>minThickness_mm</th><th>Show data</th><th>See instances</th><th>DefaultProfile</th><th>Density</th><th>ApplicationId</th><th>Groups</th><th>Name</th><th>StaticFullName</th><th>Unit</th><th>GWP100</th><th>ServiceLife</th><th>Mass</th><th>Energy</th><th>Import file</th><sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN"><th>&nbsp;</th></sec:ifAllGranted>
            </tr><%--
          --%><g:each in="${unusualThicknessResources}" var="resource" status="i"><%--
              --%><tr class="${resource.resourceGroup ? join(in: resource.resourceGroup, delimiter: ' ') : ''}">
            <td>${i+1}</td>
            <td>${resource.resourceId}</td><td>${resource.profileId}</td><td>${resource.active}</td>
            <td>${resource.virtual ? 'true' : 'false'}</td>
            <td>${resource.defaultThickness_mm}</td>
            <td>${resource.defaultThickness_in ? "${resource.defaultThickness_in.round(2)} (${(resource.defaultThickness_in * 25.4).round(2)} mm)" : ""}</td>
            <g:set var="subType" value="${resource.subType}" />
            <td>${subType?.maxThickness_mm}</td>
            <td>${subType?.minThickness_mm}</td>
            <td><a href="javascript:;" onclick="window.open('${createLink(action: "showData", params: [resourceUUID: resource.id])}', '_blank', 'width=1024, height=768, scrollbars=1');">Data</a></td>
            <td><a href="javascript:;" onclick="window.open('${createLink(action: "resourceUsage", params: [resourceId: resource.resourceId, profileId: resource.profileId])}', '_blank', 'width=1024, height=768, scrollbars=1');">See use</a></td><td>${resource.defaultProfile}</td>
            <td>${resource.density}</td><td>${resource.applicationId}</td><td>${resource.resourceGroup}</td><td>${resource.localizedName}</td><td>${resource.staticFullName}</td><td>${resource.unitForData} (${resource.combinedUnits})</td><td>${resource.impactGWP100_kgCO2e}</td><td>${resource.serviceLife}</td><td>${resource.massConversionFactor}</td><td>${resource.energyConversionFactor}</td><td>${resource.importFile}</td>
            <sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN">
                <td>
                    <g:link action="deleteResource" class="btn btn-danger" id="${resource.id}" params="[deleteRedirect: deleteRedirect]" onclick="return modalConfirm(this);" data-questionstr="${message(code:'admin.import.resource.delete_question')}"
                            data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}" data-titlestr="${message(code:'admin.import.resource.delete_title')}"><g:message code="delete" /></g:link>
                </td>
            </sec:ifAllGranted>
        </tr><%--
          --%></g:each><%--
        --%></g:if><%--
        --%><g:if test="${unusualThicknessVerifiedResources}"><%--
        --%></tbody></table>
        <br>
        <h2>Manually verified unusual thickness resources</h2>
        <table class="resource" id="verifedresulttable">
            <tbody id="verifiedResourceList">
            <tr id="verifedResourceListHeading">
            <th>Count</th><th>Manually Verified</th><th>ResourceId</th><th>ProfileId</th><th>Active</th><th>Virtual</th><th>defaultThickness_mm</th><th>defaultThickness_in (mm)</th><th>maxThickness_mm</th><th>minThickness_mm</th><th>Show data</th><th>See instances</th><th>DefaultProfile</th><th>Density</th><th>ApplicationId</th><th>Groups</th><th>Name</th><th>StaticFullName</th><th>Unit</th><th>GWP100</th><th>ServiceLife</th><th>Mass</th><th>Energy</th><th>Import file</th><sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN"><th>&nbsp;</th></sec:ifAllGranted>
        </tr><%--
          --%><g:each in="${unusualThicknessVerifiedResources}" var="resource" status="i"><%--
              --%><tr class="${resource.resourceGroup ? join(in: resource.resourceGroup, delimiter: ' ') : ''}">
            <td>${i+1}</td>
            <td>${resource.dataManuallyVerified}</td>
            <td>${resource.resourceId}</td><td>${resource.profileId}</td><td>${resource.active}</td>
            <td>${resource.virtual ? 'true' : 'false'}</td>
            <td>${resource.defaultThickness_mm}</td>
            <td>${resource.defaultThickness_in ? "${resource.defaultThickness_in.round(2)} (${(resource.defaultThickness_in * 25.4).round(2)} mm)" : ""}</td>
            <g:set var="subType" value="${resource.subType}" />
            <td>${subType?.maxThickness_mm}</td>
            <td>${subType?.minThickness_mm}</td>
            <td><a href="javascript:;" onclick="window.open('${createLink(action: "showData", params: [resourceUUID: resource.id])}', '_blank', 'width=1024, height=768, scrollbars=1');">Data</a></td>
            <td><a href="javascript:;" onclick="window.open('${createLink(action: "resourceUsage", params: [resourceId: resource.resourceId, profileId: resource.profileId])}', '_blank', 'width=1024, height=768, scrollbars=1');">See use</a></td><td>${resource.defaultProfile}</td>
            <td>${resource.density}</td><td>${resource.applicationId}</td><td>${resource.resourceGroup}</td><td>${resource.localizedName}</td><td>${resource.staticFullName}</td><td>${resource.unitForData} (${resource.combinedUnits})</td><td>${resource.impactGWP100_kgCO2e}</td><td>${resource.serviceLife}</td><td>${resource.massConversionFactor}</td><td>${resource.energyConversionFactor}</td><td>${resource.importFile}</td>
            <sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN">
                <td>
                    <g:link action="deleteResource" class="btn btn-danger" id="${resource.id}" params="[deleteRedirect: deleteRedirect]" onclick="return modalConfirm(this);" data-questionstr="${message(code:'admin.import.resource.delete_question')}"
                            data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}" data-titlestr="${message(code:'admin.import.resource.delete_title')}"><g:message code="delete" /></g:link>
                </td>
            </sec:ifAllGranted>
        </tr><%--
          --%></g:each><%--
        --%></g:if>
        <g:if test="${failingWasteResources||failingBenefitResources}"><%--
          --%><tr id="resourceListHeading">
            <th>ResourceId</th><th>ProfileId</th><th>Active</th><th>Virtual</th><th>UnitForData</th><th>Show data</th><th>See instances</th><th>DefaultProfile</th><th>Density</th><th>ApplicationId</th><th>Groups</th><th>Name</th><th>StaticFullName</th><th>Unit</th><th>GWP100</th><th>ServiceLife</th><th>Mass</th><th>Energy</th><th>Import file</th><sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN"><th>&nbsp;</th></sec:ifAllGranted>
        </tr><tr><td colspan="20"><strong>Failing active waste resources: ${failingWasteResources.size()}</strong></td></tr><%--
          --%><g:each in="${failingWasteResources}" var="resource"><%--
              --%><tr class="${resource.resourceGroup ? join(in: resource.resourceGroup, delimiter: ' ') : ''}">
            <td>${resource.resourceId}</td><td>${resource.profileId}</td><td>${resource.active}</td>
            <td>${resource.virtual ? 'true' : 'false'}</td>
            <td>${resource.unitForData}</td>
            <td><a href="javascript:;" onclick="window.open('${createLink(action: "showData", params: [resourceUUID: resource.id])}', '_blank', 'width=1024, height=768, scrollbars=1');">Data</a></td>
            <td><a href="javascript:;" onclick="window.open('${createLink(action: "resourceUsage", params: [resourceId: resource.resourceId, profileId: resource.profileId])}', '_blank', 'width=1024, height=768, scrollbars=1');">See use</a></td><td>${resource.defaultProfile}</td>
            <td>${resource.density}</td><td>${resource.applicationId}</td><td>${resource.resourceGroup}</td><td>${resource.localizedName}</td><td>${resource.staticFullName}</td><td>${resource.unitForData} (${resource.combinedUnits})</td><td>${resource.impactGWP100_kgCO2e}</td><td>${resource.serviceLife}</td><td>${resource.massConversionFactor}</td><td>${resource.energyConversionFactor}</td><td>${resource.importFile}</td>
            <sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN">
                <td>
                    <g:link action="deleteResource" class="btn btn-danger" id="${resource.id}" params="[deleteRedirect: deleteRedirect]" onclick="return modalConfirm(this);" data-questionstr="${message(code:'admin.import.resource.delete_question')}"
                            data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}" data-titlestr="${message(code:'admin.import.resource.delete_title')}"><g:message code="delete" /></g:link>
                </td>
            </sec:ifAllGranted>
        </tr><%--
          --%></g:each><tr><td colspan="20"><strong>Failing active benefit resources: ${failingBenefitResources.size()}</strong></td></tr><%--
          --%><g:each in="${failingBenefitResources}" var="resource"><%--
              --%><tr class="${resource.resourceGroup ? join(in: resource.resourceGroup, delimiter: ' ') : ''}">
            <td>${resource.resourceId}</td><td>${resource.profileId}</td><td>${resource.active}</td>
            <td>${resource.virtual ? 'true' : 'false'}</td>
            <td>${resource.unitForData}</td>
            <td><a href="javascript:;" onclick="window.open('${createLink(action: "showData", params: [resourceUUID: resource.id])}', '_blank', 'width=1024, height=768, scrollbars=1');">Data</a></td>
            <td><a href="javascript:;" onclick="window.open('${createLink(action: "resourceUsage", params: [resourceId: resource.resourceId, profileId: resource.profileId])}', '_blank', 'width=1024, height=768, scrollbars=1');">See use</a></td><td>${resource.defaultProfile}</td>
            <td>${resource.density}</td><td>${resource.applicationId}</td><td>${resource.resourceGroup}</td><td>${resource.localizedName}</td><td>${resource.staticFullName}</td><td>${resource.unitForData} (${resource.combinedUnits})</td><td>${resource.impactGWP100_kgCO2e}</td><td>${resource.serviceLife}</td><td>${resource.massConversionFactor}</td><td>${resource.energyConversionFactor}</td><td>${resource.importFile}</td>
            <sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN">
                <td>
                    <g:link action="deleteResource" class="btn btn-danger" id="${resource.id}" params="[deleteRedirect: deleteRedirect]" onclick="return modalConfirm(this);" data-questionstr="${message(code:'admin.import.resource.delete_question')}"
                            data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}" data-titlestr="${message(code:'admin.import.resource.delete_title')}"><g:message code="delete" /></g:link>
                </td>
            </sec:ifAllGranted>
        </tr><%--
          --%></g:each><%--
        --%></g:if>
        <g:if test="${resources}"><%--
          --%><tr id="resourceListHeading">
                <th>ResourceId</th><th>ProfileId</th><th>Active</th><th>Virtual</th><th>Virtual parts</th><th>Show data</th><th>Stage count</th><th>Stages</th><th>See instances</th><th>DefaultProfile</th><th>Density</th></th><th>ApplicationId</th><th>Groups</th><th>Name</th><th>StaticFullName</th><th>Unit</th><th>GWP100</th><th>ServiceLife</th><th>Mass</th><th>Energy</th><th>Import file</th><sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN"><th>&nbsp;</th></sec:ifAllGranted>
            </tr><%--
          --%><g:each in="${resources}" var="resource"><%--
              --%><tr class="${resource.resourceGroup ? join(in: resource.resourceGroup, delimiter: ' ') : ''}">
            <td>${resource.resourceId}</td><td>${resource.profileId}</td><td>${resource.active}</td>
            <td>${resource.virtual ? 'true' : 'false'}</td>
            <td>
                <g:if test="${resource.compositeParts}">
                    <g:each in="${resource.compositeParts}" var="compositePart">
                        ResourceId: ${compositePart?.resourceId}<br />ProfileId: ${compositePart?.profileId}<br />Quantity: ${compositePart?.quantity}
                    </g:each>
                </g:if>
            </td>
            <td><a href="javascript:;" onclick="window.open('${createLink(action: "showData", params: [resourceUUID: resource.id])}', '_blank', 'width=1024, height=768, scrollbars=1');">Data</a></td>
            <g:if test="${resource.impacts}">
                <td>
                    ${resource.impacts.keySet().toList().size()}
                </td>
                <td>
                    <g:join in="${resource.impacts.keySet()}" delimiter=", " />
                </td>
            </g:if>
            <g:else>
                <td>&nbsp;</td><td>&nbsp;</td>
            </g:else>
            <td><a href="javascript:;" onclick="window.open('${createLink(action: "resourceUsage", params: [resourceId: resource.resourceId, profileId: resource.profileId])}', '_blank', 'width=1024, height=768, scrollbars=1');">See use</a></td><td>${resource.defaultProfile}</td>
            <td>${resource.density}</td><td>${resource.applicationId}</td><td>${resource.resourceGroup}</td><td>${resource.localizedName}</td><td>${resource.staticFullName}</td><td>${resource.unitForData} (${resource.combinedUnits})</td><td>${resource.impactGWP100_kgCO2e}</td><td>${resource.serviceLife}</td><td>${resource.massConversionFactor}</td><td>${resource.energyConversionFactor}</td><td>${resource.importFile}</td>
            <sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN">
                <td>
                    <g:link action="deleteResource" class="btn btn-danger" id="${resource.id}" params="[deleteRedirect: deleteRedirect]" onclick="return modalConfirm(this);" data-questionstr="${message(code:'admin.import.resource.delete_question')}"
                            data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}" data-titlestr="${message(code:'admin.import.resource.delete_title')}"><g:message code="delete" /></g:link>
                </td>
            </sec:ifAllGranted>
        </tr><%--
          --%></g:each><%--
        --%></g:if><%--
        --%><g:elseif test="${dryRunResources}"><%--
            --%><tr id="resourceListHeading">
                    <th>ResourceId</th><th>ProfileId</th><th>Active</th><th>Virtual</th><th>Virtual parts</th><th>Show data</th><th>Stage count</th><th>Stages</th><th>See instances</th><th>DefaultProfile</th><th>Density</th></th><th>ApplicationId</th><th>Groups</th><th>Name</th><th>StaticFullName</th><th>Unit</th><th>GWP100</th><th>ServiceLife</th><th>Mass</th><th>Energy</th><th>Import file</th>
                </tr><%--
          --%><g:each in="${dryRunResources}" var="resource"><%--
              --%><tr class="${resource.resourceGroup ? join(in: resource.resourceGroup, delimiter: ' ') : ''}">
            <td>${resource.resourceId}</td><td>${resource.profileId}</td><td>${resource.active}</td>
            <td>${resource.virtual ? 'true' : 'false'}</td>
            <td>
                <g:if test="${resource.compositeParts}">
                    <g:each in="${resource.compositeParts}" var="compositePart">
                        ResourceId: ${compositePart?.resourceId}<br />ProfileId: ${compositePart?.profileId}<br />Quantity: ${compositePart?.quantity}
                    </g:each>
                </g:if>
            </td>
            <td><a href="javascript:;" onclick="window.open('${createLink(action: "showData", params: [resourceUUID: resource.id])}', '_blank', 'width=1024, height=768, scrollbars=1');">Data</a></td>
            <g:if test="${resource.impacts}">
                <td>
                    ${resource.impacts.keySet().toList().size()}
                </td>
                <td>
                    <g:join in="${resource.impacts.keySet()}" delimiter=", " />
                </td>
            </g:if>
            <g:else>
                <td>&nbsp;</td><td>&nbsp;</td>
            </g:else>
            <td><a href="javascript:;" onclick="window.open('${createLink(action: "resourceUsage", params: [resourceId: resource.resourceId, profileId: resource.profileId])}', '_blank', 'width=1024, height=768, scrollbars=1');">See use</a></td><td>${resource.defaultProfile}</td>
            <td>${resource.density}</td><td>${resource.applicationId}</td><td>${resource.resourceGroup}</td><td>${optimiResourceService.getLocalizedName(resource)}</td><td>${resource.staticFullName}</td><td>${resource.unitForData}</td><td>${resource.impactGWP100_kgCO2e}</td><td>${resource.serviceLife}</td><td>${resource.massConversionFactor}</td><td>${resource.energyConversionFactor}</td><td>${resource.importFile}</td>
        </tr><%--
          --%></g:each><%--
          --%></g:elseif><%--
          --%><g:elseif test="${searchedForMultipleProfiles}"><%--
                --%><div><strong>No active resources with multipleProfiles found.</strong></div><%--
          --%></g:elseif><%--
    --%></tbody></table>
    </g:else>
</div>

<script type="text/javascript">
    var spinner = "<img id=\"loadingImg\" src=\"/app/assets/animated_loading_icon.gif\" alt=\"\" style=\"height: 50px; padding: 0\" />";
    $(document).ready(function() {
        $("#applicationId").on('change', function (event) {
            event.preventDefault();
            var applicationId = $(this).val();
            $(this).removeClass('redBorder');

            var supportedResourceGroups = getCompatibleList($('option:selected', this).attr("data-supportedResourceGroups"));

            if (applicationId) {
                if (supportedResourceGroups.length) {
                    $("#resourceGroup > option").each(function() {
                        if (supportedResourceGroups.indexOf(this.value) > -1) {
                            $(this).show();
                        } else {
                            $(this).hide();
                        }
                    });
                } else {
                    $("#resourceGroup > option").each(function() {
                        if (this.value) {
                            $(this).hide();
                        }
                    });
                }
            } else {
                $("#resourceGroup > option").each(function() {
                    if (this.value) {
                        $(this).hide();
                    }
                });
            }
        });

        $("#resourceGroup").on('change', function (event) {
            event.preventDefault();
            $(this).removeClass('redBorder');
        });
        $('.triggerPopover').popover({
            placement: 'top',
            trigger: 'hover',
        });
    });

    $("#searchFieldText").on('keyup', function (event) {
        var filter = $("#searchFieldText").val();
        var iterator = 0;
        var rows = $('#resulttable > tbody > tr');
        var rowsLength = rows.length;

        if (filter && filter.length > 1) {
            for (; iterator < rowsLength; iterator++) {
                if (iterator !== 0) {
                    showAndHideTableRows(iterator, filter, rows[iterator]);
                }
            }
        } else {
            for (; iterator < rowsLength; iterator++) {
                if (iterator !== 0) {
                    showAllTableRows(iterator, rows[iterator]);
                }
            }
        }
    });

    $('#searchByResourceId').keyup(function(event) {
        if (event.keyCode === 13) {
            $("#searchByResourceIdButton").click();
        }
    });

    $("#searchByResourceIdButton").on('click', function (event) {
        var resourceId = $("#searchByResourceId").val();

        if (resourceId) {
            //$("#resourceList").find("tr").remove();
            document.getElementById('resourceList').innerHTML = '';
            $('#filterRules').hide();
            $('#searchFieldContent').hide();

            $.ajax({async: true, type: 'POST',
                data: 'resourceId=' + resourceId,
                url: '/app/sec/admin/import/resourcesById',
                beforeSend: function() {
                    $("#loadingSpinner").show();
                    $("#applicationId").attr("disabled", true);
                    $("#resourceGroup").attr("disabled", true);
                },
                success: function (data, textStatus) {
                    $("#loadingSpinner").hide();
                    $("#applicationId").attr("disabled", false);
                    $("#resourceGroup").attr("disabled", false);
                    if (data.output) {
                        $('#searchFieldContent').show();
                        $('#resourceList').append(data.output);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {

                }
            });


        } else {
            $("#resourceList").find("tr").remove();
            $('#filterRules').hide();
        }
    });

    function findResources(buttonElem) {
        var applicationId = $("#applicationId").val();

        if (!applicationId) {
            $("#applicationId").addClass("redBorder");
        }

        var resourceGroup = $("#resourceGroup").val();

        if (!resourceGroup) {
            $("#resourceGroup").addClass("redBorder");
        }

        if (applicationId && resourceGroup) {
            $('#filterRule').children().remove();
            document.getElementById('resourceList').innerHTML = '';
            $('#filterRules').hide();
            $('#searchFieldContent').hide();

            $.ajax({async: true, type: 'POST',
                data: 'applicationId=' + applicationId + '&resourceGroup=' + resourceGroup,
                url: '/app/sec/admin/import/applicationResources',
                beforeSend: function() {
                    $("#loadingSpinner").show();
                    $("#applicationId").attr("disabled", true);
                    $("#resourceGroup").attr("disabled", true);
                    $(buttonElem).addClass("removeClicks");
                },
                success: function (data, textStatus) {
                    $("#loadingSpinner").hide();
                    $("#applicationId").attr("disabled", false);
                    $("#resourceGroup").attr("disabled", false);
                    $(buttonElem).removeClass("removeClicks");
                    if (data.output) {
                        $('#searchFieldContent').show();
                        $('#resourceList').append(data.output);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {

                }
            });
        }
    }

    function showAllTableRows(iterator, tr) {
        $(tr).css('display', '');

        if (iterator % 500 === 0) {
            setTimeout(showAllTableRows, 5);
        }
    }


    function showAndHideTableRows(iterator, filter, tr) {
        if ($(tr).text().search(new RegExp(filter, "i")) < 0) {
            $(tr).css('display', 'none');
        } else {
            $(tr).css('display', '');
        }

        if (iterator % 500 === 0) {
            setTimeout(showAndHideTableRows, 5);
        }
    }

        function setMultipleProfiles() {
            Swal.fire({
                title: "Warning",
                text: "Are you sure you want to set multipleProfiles for all resources?",
                icon: "warning",
                confirmButtonText: "Yes",
                cancelButtonText: "Back",
                confirmButtonColor: "red",
                showCancelButton: true,
                reverseButtons: true,
                showLoaderOnConfirm: true,
                preConfirm: function () {
                    return new Promise(function (resolve) {
                        $.ajax({type: 'POST',
                            url: '/app/sec/admin/import/setMultipleProfiles',
                            success: function (data) {
                                resolve()
                            },
                            error: function (XMLHttpRequest, textStatus) {
                                swal.showValidationMessage("Something went wrong while setting multipleProfiles: " + textStatus)
                            }
                        });
                    })
                  },
                allowOutsideClick: false
            }).then(result => {
                if (result.value) {
                    Swal.fire({
                        icon: 'success',
                        title: 'All done!',
                        html: ''
                    })
                }
            });
        }
        function setIsoCountryCodes() {
            Swal.fire({
                title: "Warning",
                text: "Are you sure you want to set IsoCountryCodes for all resources?",
                icon: "warning",
                confirmButtonText: "Yes",
                cancelButtonText: "Back",
                confirmButtonColor: "red",
                showCancelButton: true,
                reverseButtons: true,
                showLoaderOnConfirm: true,
                preConfirm: function () {
                    return new Promise(function (resolve) {
                        $.ajax({type: 'POST',
                            url: '/app/sec/admin/import/setIsoCountryCodes',
                            success: function (data) {
                                resolve()
                            },
                            error: function (XMLHttpRequest, textStatus) {
                                swal.showValidationMessage("Something went wrong while setting isoCountryCodes: " + textStatus)
                            }
                        });
                    })
                  },
                allowOutsideClick: false
            }).then(result => {
                if (result.value) {
                    Swal.fire({
                        icon: 'success',
                        title: 'All done!',
                        html: ''
                    })
                }
            });
        }

        function setResourceTypes() {
            Swal.fire({
                title: "Warning",
                text: "Are you sure you want to set ResourceTypes for all resources?",
                icon: "warning",
                confirmButtonText: "Yes",
                cancelButtonText: "Back",
                confirmButtonColor: "red",
                showCancelButton: true,
                reverseButtons: true,
                showLoaderOnConfirm: true,
                preConfirm: function () {
                    return new Promise(function (resolve) {
                        $.ajax({type: 'POST',
                            url: '/app/sec/admin/import/setResourceTypes',
                            success: function (data) {
                                resolve()
                            },
                            error: function (XMLHttpRequest, textStatus) {
                                swal.showValidationMessage("Something went wrong while setting resourceTypes: " + textStatus)
                            }
                        });
                    })
                  },
                allowOutsideClick: false
            }).then(result => {
                if (result.value) {
                    Swal.fire({
                        icon: 'success',
                        title: 'All done!',
                        html: ''
                    })
                }
            });
        }

    function showPopupMsg() {
        Swal.fire({
            title: "${message(code: 'remind.user.run.benchmark')}",
            width: 800,
            icon: 'warning',
        })
    }

  </script>
</g:if>
</body>
</html>
