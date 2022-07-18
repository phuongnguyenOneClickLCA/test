<g:set var="notPassedResources" value="${feedback?.get("notPassedResources")}" />
<g:set var="notPassedCriteria" value="${feedback?.get("notPassedCriteria")}" />
<g:set var="passedResources" value="${feedback?.get("passedResources")}" />
<g:set var="nonShallPass" value="${feedback?.get("nonShallPass")}" />
<g:set var="showOnlyNumbers" value="${feedback?.get("showOnlyNumbers")}" />
<g:set var="ruleId" value="${feedback?.get("ruleId")}" />
<g:set var="manuallyVerifiedResources" value="${notPassedResources?.findAll({ it.dataManuallyVerified })}" />
<g:set var="unverifiedResources" value="${notPassedResources?.findAll({ !it.dataManuallyVerified })}" />
<g:if test="${!resourceTableOnly}">
    <tr style='border-right-style:hidden; border-left-style:hidden;'><td colspan="16">&nbsp;</td></tr>
    <tr>
        <td colspan="16">
            <strong style="float: left;">${feedback?.get("ruleName")}</strong>
            <g:if test="${unverifiedResources}">
                <g:submitButton style="float: right;" name="resourcesFor${ruleId}" value="Download Excel" class="btn btn-primary" />
            </g:if>
        </td>
    </tr>
    <g:if test="${passedResources}">
        <tr>
            <td colspan="16">
                <g:if test="${nonShallPass}">
                    <strong>Resources with errors: ${passedResources.size()}<br /></strong>
                </g:if>
                <g:else>
                    <strong>Resources passed the rule: ${passedResources.size()}<br /></strong>
                </g:else>
            </td>
        </tr>
    </g:if>

    <g:if test="${manuallyVerifiedResources || unverifiedResources}">
        <tr>
            <td colspan="16">
                <g:if test="${nonShallPass}">
                    <strong>Resources passed the rule: ${unverifiedResources.size()}<br /></strong>
                </g:if>
                <g:else>
                    <strong>Resources with errors: ${unverifiedResources.size()}<br /></strong>
                </g:else>
                <g:if test="${showOnlyNumbers}">
                    <g:each in="${unverifiedResources}" var="resource">
                        <input type="hidden" value="${resource.resourceId}" name="${ruleId}">
                    </g:each>
                </g:if>
                <g:else>
                    <g:each in="${unverifiedResources}" var="resource">
                        <input type="hidden" value="${resource.resourceId}" name="${ruleId}">
                        ${resource.resourceId} / ${resource.profileId} <a href="javascript:" onclick="window.open('${createLink(action: "showData", controller: "import", params: [resourceUUID: resource.id])}', '_blank', 'width=1024, height=768, scrollbars=1');"><i class="fas fa-search-plus"></i></a> :
                        <g:if test="${resource.missingRequiredFilterFields}">
                            Missing required fields: ${resource.missingRequiredFilterFields},
                        </g:if>
                        <g:if test="${resource.missingRequiredFilterValues}">
                            Missing required values: ${resource.missingRequiredFilterValues},
                        </g:if>
                        <g:if test="${resource.illegalFilterValues}">
                            Has illegal values: ${resource.illegalFilterValues},
                        </g:if>
                        <g:if test="${resource.failingRequireIdenticalValues}">
                            ${resource.failingRequireIdenticalValues},
                        </g:if>
                        <g:if test="${resource.failingMaxAgeFilterValues}">
                            Failing age check: ${resource.failingMaxAgeFilterValues},
                        </g:if>
                        <g:if test="${resource.failingFilterRules}">
                            Failing filterRules check: ${resource.failingFilterRules},
                        </g:if>
                        <g:if test="${resource.failingVerifyValues}">
                            Failing verifyValues check: ${resource.failingVerifyValues},
                        </g:if>
                        <g:if test="${resource.failingActiveResourceIds}">
                            Failing activeResourceIds check: ${resource.failingActiveResourceIds},
                        </g:if>
                        <g:if test="${resource.nonActiveResourceType}">
                            Failing activeResourceType check,
                        </g:if>
                        <g:if test="${resource.nonActiveSubType}">
                            Failing activeResourceSubType check
                        </g:if>
                        <g:if test="${nonShallPass}">
                            nonShallPass: true, passes the rule
                        </g:if>
                        <br />
                    </g:each>
                </g:else>
            </td>
        </tr>
        <tr>
            <td colspan="16">
                <g:if test="${nonShallPass}">
                    <strong>Resources with verified unusual values passed the rule: ${manuallyVerifiedResources.size()}<br /></strong>
                </g:if>
                <g:else>
                    <strong>Resources with verified unusual values: ${manuallyVerifiedResources.size()}<br /></strong>
                </g:else>
                <g:if test="${!showOnlyNumbers}">
                    <g:each in="${manuallyVerifiedResources}" var="resource">
                        ${resource.resourceId} / ${resource.profileId} <a href="javascript:" onclick="window.open('${createLink(action: "showData", controller: "import", params: [resourceUUID: resource.id])}', '_blank', 'width=1024, height=768, scrollbars=1');"><i class="fas fa-search-plus"></i></a> :
                        <g:if test="${resource.missingRequiredFilterFields}">
                            Missing required fields: ${resource.missingRequiredFilterFields},
                        </g:if>
                        <g:if test="${resource.missingRequiredFilterValues}">
                            Missing required values: ${resource.missingRequiredFilterValues},
                        </g:if>
                        <g:if test="${resource.illegalFilterValues}">
                            Has illegal values: ${resource.illegalFilterValues},
                        </g:if>
                        <g:if test="${resource.failingRequireIdenticalValues}">
                            ${resource.failingRequireIdenticalValues},
                        </g:if>
                        <g:if test="${resource.failingMaxAgeFilterValues}">
                            Failing age check: ${resource.failingMaxAgeFilterValues},
                        </g:if>
                        <g:if test="${resource.failingFilterRules}">
                            Failing filterRules check: ${resource.failingFilterRules},
                        </g:if>
                        <g:if test="${resource.failingVerifyValues}">
                            Failing verifyValues check: ${resource.failingVerifyValues},
                        </g:if>
                        <g:if test="${resource.failingActiveResourceIds}">
                            Failing activeResourceIds check: ${resource.failingActiveResourceIds},
                        </g:if>
                        <g:if test="${resource.nonActiveResourceType}">
                            Failing activeResourceType check,
                        </g:if>
                        <g:if test="${resource.nonActiveSubType}">
                            Failing activeResourceSubType check
                        </g:if>
                        <g:if test="${nonShallPass}">
                            nonShallPass: true, passes the rule
                        </g:if>
                        <br />
                    </g:each>
                </g:if>
            </td>
        </tr>
    </g:if>

    <g:if test="${notPassedCriteria}">
        <tr>
            <td colspan="16"><strong>${notPassedCriteria}</strong>

            </td>
        </tr>
    </g:if>
</g:if>
<g:if test="${resources}">
    <tr>
  <th>ResourceId</th><th>ProfileId</th><th>Active</th><th>Show data</th><th>Stage count</th><th>Stages</th><th>See instances</th><th>DefaultProfile</th><th>Density</th><th>ApplicationId</th><th>Groups</th><th>Name</th><th>Unit</th><th>GWP100</th><th>ServiceLife</th><th>Mass</th><th>Energy</th><th>Import file</th>
        <g:if test="${allowEdit}">
            <th>Activate / Deactivate</th><th>Delete</th>
        </g:if>
</tr>
    <g:each in="${resources}" var="resource">
        <tr class="${resource.applicationId? 'hasApplicationId ' + resource.applicationId : 'noApplicationId'} ${resource.resourceGroup ? join(in: resource.resourceGroup, delimiter: ' ') : ''}">
            <td>${resource.resourceId}</td><td>${resource.profileId}</td><td>${resource.active}</td>
            <td><a href="javascript:" onclick="window.open('${createLink(action: "showData", params: [resourceUUID: resource.id])}', '_blank', 'width=1024, height=768, scrollbars=1');">Data</a></td>
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
            <td>${resource.density}</td><td>${resource.applicationId}</td><td>${resource.resourceGroup}</td><td>${resource.localizedName}</td><td>${resource.unitForData}</td><td>${resource.impactGWP100_kgCO2e}</td><td>${resource.serviceLife}</td><td>${resource.massConversionFactor}</td><td>${resource.energyConversionFactor}</td><td>${resource.importFile}</td>
            <g:if test="${allowEdit}">
                <td>
                    <g:if test="${resource.active}">
                        <g:link action="deactivateResource" class="btn btn-danger" id="${resource.id}" onclick="return modalConfirm(this);" data-questionstr="Deactivate resource?"
                                data-truestr="Deactivate" data-falsestr="${message(code:'cancel')}" data-titlestr="Deactivate resource?">Deactivate</g:link>
                    </g:if>
                    <g:else>
                        <g:link action="activateResource" class="btn btn-primary" id="${resource.id}" onclick="return modalConfirm(this);" data-questionstr="Activate resource?"
                                data-truestr="Activate" data-falsestr="${message(code:'cancel')}" data-titlestr="Activate resource?">Activate</g:link>
                    </g:else>
                </td>
                <td>
                    <g:link action="deleteResource" class="btn btn-danger" id="${resource.id}" onclick="return modalConfirm(this);" data-questionstr="${message(code:'admin.import.resource.delete_question')}"
                            data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}" data-titlestr="${message(code:'admin.import.resource.delete_title')}"><g:message code="delete" /></g:link>
                </td>
            </g:if>
        </tr>
    </g:each>
</g:if>
