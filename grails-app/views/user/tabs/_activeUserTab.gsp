<div id="activeUserTabContent">
    <h4><g:message code="user.list.activatedUsers"/>
    (<span class="activatedUsersCount">
        <i class='fas fa-circle-notch fa-spin oneClickColorScheme'></i>
    </span>)</h4><opt:link
        class="btn btn-primary" controller="user"
        action="downloadActiveUsers">Dump users to Excel</opt:link><br/>
    <div class="dataTables_filter" id="activatedUserList_filter">
        <label>Search: <g:textField name="activatedUsersSearch" /></label>
    </div>
    <div id="activatedUserList">
        <opt:spinner spinnerId="loadingSpinner-activatedUserList"/>
    </div>
</div>
