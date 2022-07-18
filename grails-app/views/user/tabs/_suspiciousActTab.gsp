<%
    def userService = grailsApplication.mainContext.getBean("userService")
%>
<div id="suspiciousActTabContent" style="display: none">
    <h4><g:message code="user.list.usersWithSuspiciousActivity"
                   args="[usersWithSuspiciousActivity ? usersWithSuspiciousActivity.size() : 0]"/></h4>
    <g:form action="list">
        <div style="margin-top: 30px;">
            <label><h4>Search users with suspicious activity with params</h4></label><br/>
            <table>
                <tbody>
                <tr><td><strong>Suspicious activity start date</strong>
                </td><td></td><td><strong>Suspicious activity end date</strong></td></tr>
                <tr>
                    <td>
                        <g:textField name="startDate" id="startDate"
                                     class="input-xlarge datepicker" readonly="true"
                                     style="cursor: pointer; width: 125px;"/><span class="add-on"><i
                            class="icon-calendar"></i></span>
                    </td>
                    <td>&nbsp;</td>
                    <td>
                        <g:textField name="endDate" id="endDate"
                                     class="input-xlarge datepicker" readonly="true"
                                     style="cursor: pointer; width: 125px;"/><span class="add-on"><i
                            class="icon-calendar"></i></span>
                    </td>
                </tr>
                <tr><td colspan="3"><strong>Search users with suspicious activity higher than:</strong></td>
                </tr>
                <tr><td colspan="3"><input type="text" name="searchSuspicious" style="margin-top: 5px;"/>
                </td></tr>
                <tr><td colspan="3"><g:submitButton name="searchSuspiciousUsers" value="Search"
                                                    class="btn btn-primary"/></td></tr>
                </tbody>
            </table>
        </div>
    </g:form>
    <div><g:if test="${suspiciousUsersFilteredWith}"><h4>${suspiciousUsersFilteredWith}</h4> <opt:link
            class="btn btn-info" action="list" params="[searchSuspiciousUsers: true]">Clear filtering</opt:link></g:if></div>
    <g:if test="${usersWithSuspiciousActivity}">
        <table class="table table-striped table-condensed table-data">
            <thead>
            <tr>
                <th><g:message code="entity.name"/></th>
                <th><g:message code="email"/></th>
                <th>User type</th>
                <th><g:message code="user.duplicate.login"/></th>
                <th><g:message code="user.impossible.travel.speed"/></th>
                <th><g:message code="user.different.login.country"/></th>
                <th><g:message code="user.different.browser"/></th>
                <th><g:message code="user.suspiciousActivity.total"/></th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${usersWithSuspiciousActivity}" var="user">
                <tr class="suspiciousActivityRow">
                    <td>
                        <g:if test="${userService.getSalesView(currentUser)}">
                            <opt:link controller="user" action="form"
                                      params="[id: user.id]">${user.name}</opt:link>
                        </g:if>
                        <g:else>
                            ${user.name}
                        </g:else>
                    </td>
                    <td>${user.username}</td>
                    <td>${user?.type}</td>
                    <td>${user.userSuspiciousActivity.getLoggingInWhenSessionIsValidScore()}</td>
                    <td>${user.userSuspiciousActivity.getPhysicallyImpossibleTravelSpeedScore()}</td>
                    <td>${user.userSuspiciousActivity.getLoggingInFromAnotherCountryScore()}</td>
                    <td>${user.userSuspiciousActivity.getDifferentBrowserScore()}</td>
                    <td class="suspiciousActivityTotal">${user.userSuspiciousActivity.getTotalScore()}</td>
                </tr>
            </g:each>
            </tbody>
        </table>

        <p>&nbsp;</p>
    </g:if>
</div>
<script>
    $(function () {
        $("#startDate,#endDate").datepicker({
            dateFormat: 'dd.mm.yy',
            changeMonth: true,
            changeYear: true
        }).on('keyup', function (e) {
            if (e.keyCode == 8 || e.keyCode == 46) {
                $.datepicker._clearDate(this);
            }
        });
    });
</script>
