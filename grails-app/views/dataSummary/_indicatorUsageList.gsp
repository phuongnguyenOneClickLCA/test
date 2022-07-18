<div id="indicatorUsageList">
<table class="resource" id="table1">
    <thead>
    <tr>
        <th>Entity</th>
        <th>Has results</th>
        <th>Type Licenses</th>
        <th>Active Licenses</th>
        <th>Inactive Licenses</th>
        <th style="white-space: nowrap;">User</th>
        <th style="white-space: nowrap;">Level of rights</th>
        <th style="white-space: nowrap;">Design / period Status</th>
        <th>Creation date</th>
        <th>Last updated</th>
        <th>Last updater</th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${entities}" var="entity">
        <g:set var="validLicenses" value="${projectIdAndLicenses.get(entity._id.toString())?.get("valid")}" />
        <g:set var="expiredLicenses" value="${projectIdAndLicenses.get(entity._id.toString())?.get("invalid")}" />
        <tr>
            <g:set var="childEntities" value="${projectIdAndChildren.get(entity._id.toString())}" />
            <td><opt:link controller="entity" action="show" target="_blank" params="[entityId:entity._id]">${entity.operatingPeriod ?: entity.name}</opt:link></td>
            <td>${childEntities.find({it.hasResults}) ? true : false}</td>
            <td>${validLicenses?.collect{it.type}?.unique() ?: ""}</td>
            <td>
                <g:each in="${validLicenses?.sort({ it.type })}" var="license">
                    <strong>${license.type}: </strong> <opt:link controller="license" action="form" id="${license.id}" target="_blank">${license.name}</opt:link><br/>
                </g:each>
            </td>
            <td>
                <g:each in="${expiredLicenses?.sort({ it.type })}" var="license">
                    <strong>${license.type}: </strong><opt:link controller="license" action="form" id="${license.id}" target="_blank">${license.name}</opt:link><br/>
                </g:each>
            </td>
            <g:set var="managers" value="${entity.managerUsernames}" />
            <g:set var="modifier" value="${entity.modifiersUsernames}" />
            <g:set var="readonly" value="${entity.readonlyUsernames}" />
            <td style="white-space: nowrap;">
                <g:each in="${managers}" var="user"><%--
                        --%><strong>${user}</strong><br/><%--
                    --%></g:each><%--
                    --%><g:each in="${modifier}" var="user"><%--
                        --%><strong>${user}</strong><br/><%--
                    --%></g:each><%--
                    --%><g:each in="${readonly}" var="user"><%--
                        --%><strong>${user}</strong><br/><%--
                    --%></g:each>
            </td>
            <td style="white-space: nowrap;">
                <g:each in="${managers}" var="user"><%--
                        --%><g:message code="manager" /><br/><%--
                    --%></g:each><%--
                    --%><g:each in="${modifier}" var="user"><%--
                        --%><g:message code="modifier" /><br/><%--
                    --%></g:each><%--
                    --%><g:each in="${readonly}" var="user"><%--
                        --%><g:message code="readonly" /><br/><%--
                    --%></g:each>
            </td>
            <td style="white-space: nowrap;"><%--
                    --%><strong>Allow in portfolio:</strong> ${childEntities?.findAll({ it.allowAsBenchmark })?.size()}<br/><%--
                    --%><strong>Total designs: ${childEntities?.findAll({"design".equals(it?.entityClass)})?.size()}</strong><br/><%--
                    --%><strong>Total periods: ${childEntities?.findAll({"operatingPeriod".equals(it?.entityClass)})?.size()}</strong><%--
                --%></td>
            <td><g:formatDate date="${entity.dateCreated}" format="dd.MM.yyyy"/></td>
            <td><g:formatDate date="${entity.lastUpdated}" format="dd.MM.yyyy"/></td>
            <td>${entity.lastUpdaterUsername}</td>
        </tr>
    </g:each>
    </tbody>

</table>

<div class="paginateButtons">
    <util:remotePaginate controller="dataSummary" action="indicatorUsagePaging" total="${totalIndicatorUsage}"
                         params="${params}"
                         update="indicatorUsageList" max="10" pageSizes="[10, 20, 50, 100]"
    />
</div>
</div>

<opt:spinner spinnerId="loadingSpinner-indicatorUsageList"/>
<script type="text/javascript">


    $(".pagination li").click(function() {
        console.log("click")
        $("#indicatorUsageList").css('display', 'none');
        $("#loadingSpinner-indicatorUsageList").show();
        setTimeout(function () {
            $("#indicatorUsageList").css('display', 'block');
            $("#loadingSpinner-indicatorUsageList").hide();
        }, 4000)
    });

</script>