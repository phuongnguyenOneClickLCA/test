<g:if test="${resources}">
<g:form name="deleteResourcesForm" action="deleteResources">
    <div style="margin-top: 10px; margin-bottom: 10px;">
        <a href="javascript:" class="btn btn-primary" onclick="selectAll();">Valitse kaikki</a>

        <a href="javascript:" onclick="if (confirm('delete duplicates')) { deleteDubs();}" class="btn btn-danger"> Delete duplicate resources </a>
        <a href="javascript:" class="btn btn-danger" onclick="if (confirm('Delete selected resources?')) { submitForm('deleteResourcesForm'); }">${message(code: 'delete')}</a>
    </div>
    <table class="resource" id="resulttable">
        <tbody id="resourceList">
        <g:if test="${resources}"><%--
    --%><tr id="resourceListHeading" class="duplicateRow">
            <th>ResourceId</th><th>ProfileId</th><th>Active</th><th>Virtual</th><th>Virtual parts</th><th>Show data</th><th>Stage count</th><th>Stages</th><th>See instances</th><th>DefaultProfile</th><th>Density</th></th><th>ApplicationId</th><th>Groups</th><th>Name</th><th>StaticFullName</th><th>Unit</th><th>GWP100</th><th>ServiceLife</th><th>Mass</th><th>Energy</th><th>Import file</th><sec:ifAllGranted
                roles="ROLE_SYSTEM_ADMIN"><th>&nbsp;</th></sec:ifAllGranted>
            </tr><%--
    --%><g:each in="${resources}" var="resource"><%--
    --%><tr class="${resource.resourceGroup ? join(in: resource.resourceGroup, delimiter: ' ') : ''}">
            <td>${resource.resourceId}</td><td>${resource.profileId}</td><td>${resource.active}</td>
            <td>${resource.virtual ? 'true' : 'false'}</td>
            <td>
                <g:if test="${resource.compositeParts}">
                    <g:each in="${resource.compositeParts}" var="compositePart">
                        ResourceId: ${compositePart?.resourceId}<br/>ProfileId: ${compositePart?.profileId}<br/>Quantity: ${compositePart?.quantity}
                    </g:each>
                </g:if>
            </td>
            <td><a href="javascript:;"
                   onclick="window.open('${createLink(action: "showData", params: [resourceUUID: resource.id])}', '_blank', 'width=1024, height=768, scrollbars=1');">Data</a>
            </td>
            <g:if test="${resource.impacts}">
                <td>
                    ${resource.impacts.keySet().toList().size()}
                </td>
                <td>
                    <g:join in="${resource.impacts.keySet()}" delimiter=", "/>
                </td>
            </g:if>
            <g:else>
                <td>&nbsp;</td><td>&nbsp;</td>
            </g:else>
            <td><a href="javascript:;"
                   onclick="window.open('${createLink(action: "resourceUsage", params: [resourceId: resource.resourceId, profileId: resource.profileId])}', '_blank', 'width=1024, height=768, scrollbars=1');">See use</a>
            </td><td>${resource.defaultProfile}</td>
            <td>${resource.density}</td><td>${resource.applicationId}</td><td>${resource.resourceGroup}</td><td>${resource.localizedName}</td><td>${resource.staticFullName}</td><td>${resource.unitForData} (${resource.combinedUnits})</td><td>${resource.impactGWP100_kgCO2e}</td><td>${resource.serviceLife}</td><td>${resource.massConversionFactor}</td><td>${resource.energyConversionFactor}</td><td>${resource.importFile}</td>
            <sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN">
                <td>
                    <input type="checkbox" name="id" value="${resource.id}" class="deleteCheckbox"/>
                </td>
            </sec:ifAllGranted>
        </tr><%--
   --%></g:each><%--
   --%></g:if><%--
--%></tbody></table>
</g:form>
</g:if>
<g:else>
    <span class="warningRed">No duplicates found!</span>
</g:else>
<script type="text/javascript">
    function selectAll() {
        var checkboxes = document.getElementsByClassName('deleteCheckbox');

        if (checkboxes) {
            $(checkboxes).each(function () {
                if ($(this).prop('checked')) {
                    $(this).prop('checked', false);
                } else {
                    $(this).prop('checked', true);
                }
            });
        }
    }
    function deleteDubs() {
        $.ajax({
            type:'POST',
            url:'/app/sec/admin/import/deleteDuplicates'
        });
    }

</script>