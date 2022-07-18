<div class="table-responsive">
    <table id="usersWithEnhancedPrivilegesTable" class="table table-striped table-bordered">
        <thead>
        <tr>
            <th><g:message code="user.field.username" default="Username"/></th>
            <th><g:message code="user.field.roles" default="Roles"/></th>
        </tr>
        </thead>
        <tbody>
        <g:each status="rowIndex" in="${usersWithEnhancedPrivileges}" var="userWithEnhancedPrivileges">
            <tr id="${rowIndex}">
                <td class="tabledit-view-mode" style="cursor: pointer;">
                    <label>${userWithEnhancedPrivileges.username}</label>
                </td>
                <td class="tabledit-view-mode" style="cursor: pointer;">
                    <label>${userWithEnhancedPrivileges.authorities.join("<br>")}</label>
                </td>
            </tr>
        </g:each>
        </tbody>
    </table>
</div>
