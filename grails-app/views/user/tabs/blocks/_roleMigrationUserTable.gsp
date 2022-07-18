<div class="table-responsive">
    <table class="table table-striped table-bordered">
        <thead>
        <tr>
            <th>Username</th>
            <th>Target role</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${roleMigrationMappings}" var="userMapping">
            <tr id="${userMapping.id}">
                <td class="tabledit-view-mode" style="cursor: pointer;">
                    <label>${userMapping.username}</label>
                </td>
                <td class="tabledit-view-mode" style="cursor: pointer;">
                    <select id="targetRole_${userMapping.id}" onchange="updateMapping('${userMapping.id}')">
                        <option value="">--Please choose an option--</option>
                        <g:each in="${roles}" var="role">
                            <option ${role.authority == userMapping.targetRole ? "selected='selected'" : ""} value="${role.authority}">${role.authority}</option>
                        </g:each>
                    </select>
                </td>
                <td class="tabledit-view-mode">
                    <button onclick="deleteMapping('${userMapping.id}')"><i class="fa fa-trash"></i></button>
                </td>
            </tr>
        </g:each>
        </tbody>
    </table>
</div>
