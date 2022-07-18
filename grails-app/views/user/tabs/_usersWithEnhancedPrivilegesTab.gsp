<div id="usersWithEnhancedPrivilegesTabContent" style="display: none;">
    <h4><g:message code="user.list.usersWithEnhancedPrivileges" args="[usersWithEnhancedPrivileges.size()]" default="Users with enhanced privileges ({0})"/></h4>
    <div>
        <div id="usersWithEnhancedPrivilegesBlock">
            <g:render template="tabs/blocks/usersWithEnhancedPrivilegesTable" model="[usersWithEnhancedPrivileges: usersWithEnhancedPrivileges]"/>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function () {
        initializeUsersWithEnhancedPrivilegesTable();
    });
</script>
