<div id="blacklistTabContent" style="display: none">
    <h4>Blacklist user and email address</h4>
    <sec:ifAnyGranted roles="ROLE_SALES_VIEW">
        <div class="container-fluid center-block borderbox">
            <g:form action="addToBlackList">
                <label><h5>Enter full email to disable current users or the suffix only to also prevent future registration</h5>
                </label>
                <input class="input-large" name="prefix"> @ <input class="input-large redBorder"
                                                                   name="suffix">
                <g:submitButton name="add" value="Add" class="btn btn-primary"/>
            </g:form>
        </div>
    </sec:ifAnyGranted>
    <table class="table table-striped table-condensed table-data">
        <thead>
        <tr><h4>Blacklist Rules</h4></tr>
        <tr>
            <th>Email</th>
            <th>Time added</th>
            <th>Added by</th>
            <sec:ifAnyGranted roles="ROLE_SALES_VIEW">
                <th></th>
            </sec:ifAnyGranted>
        </tr>
        <tbody>
        <g:each in="${allBlackListFound}" var="blackList">
            <tr>
                <td>${blackList.prefix ? blackList.prefix : "*"}@${blackList.suffix ? blackList.suffix : "N/A"}</td>
                <td>${blackList.timeAdded}</td>
                <td>${blackList.ruleAddedBy}</td>
                <sec:ifAnyGranted roles="ROLE_SALES_VIEW">
                    <td><opt:link action="deleteBlacklistRule" id="${blackList.id}" class="btn btn-danger"
                                  onclick="return modalConfirm(this)"
                                  data-questionstr="Removing this rule will enable all the accounts that match this criteria."
                                  data-truestr="${message(code: 'delete')}"
                                  data-falsestr="${message(code: 'cancel')}"
                                  data-titlestr="Are you sure">Delete</opt:link></td>
                </sec:ifAnyGranted>
            </tr>
        </g:each>
        </tbody>
    </table>
    <table id="blacklistUserTable" class="table table-striped table-condensed table-data"
           style="width: 90vw">
        <thead>
        <tr><h4>Affected / Disabled users</h4></tr>
        <tr>
            <th>Email</th>
            <th>Time of disabling</th>
            <th>Disabled by</th>
            <th>Reason</th>
            <sec:ifAnyGranted roles="ROLE_SALES_VIEW">
                <th></th>
            </sec:ifAnyGranted>
        </tr>
        </thead>
        <tbody>
        <g:each in="${disabledUsers}" var="user">
            <tr>
                <td>${user.username}</td>
                <td>${user.userDisabled?.time}</td>
                <td>${user.userDisabled?.disabledBy}</td>
                <td>${user.userDisabled?.reason}</td>
                <sec:ifAnyGranted roles="ROLE_SALES_VIEW">
                    <td><opt:link action="removeUserFromBlacklist" id="${user.id}" class="btn btn-primary"
                                  onclick="return modalConfirm(this)"
                                  data-questionstr="This will remove user from blacklist and enable the account. To disable again, please type the specific email"
                                  data-truestr="Enable" data-falsestr="${message(code: 'cancel')}"
                                  data-titlestr="Are you sure">Enable</opt:link></td>
                </sec:ifAnyGranted>
            </tr>
        </g:each>
        </tbody>
    </table>
</div>
<script type="text/javascript">
    $(function () {
        initializeDisabledUserTable();
    });
</script>