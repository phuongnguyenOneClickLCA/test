<!DOCTYPE html>
<%-- MAIN SCREEN --%>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<div class="container screenheader">
    <h2>Trial projects</h2>
</div>
<div class="container section">
    <div class="sectionbody container">
        <ul class="nav nav-tabs">
            <li class="navInfo active" name="groupByUsername" onclick="showHideChildDiv2('trailuserMainDiv', this)"><a href="#">Grouped by users (${trialUsers ? trialUsers.size() : 0})</a></li>
            <li class="navInfo" name="groupByLicense" onclick="showHideChildDiv2('trailuserMainDiv',this)"><a href="#">Grouped by licenses </a></li>
          </ul>
        <div id="trailuserMainDiv">
            <div id="groupByUsername" style="display: block">
                <g:if test="${trialUsers}">
                    <button class="btn btn-primary" onclick="$('.inactiveEntity').toggleClass('hidden');">Show/Hide inactive</button>

                    <table class="table sortTableTable">
                        <thead>
                        <tr>
                            <th>No</th>
                            <th>Email address</th>
                            <th>Date signed up</th>
                            <th>Authenticated by email?</th>
                            <th>Last logged in</th>
                            <th>Trial counts</th>
                            <th>Trial infra. counts </th>
                            <th>Number of Licenses</th>
                            <th>Licenses names</th>
                            <th>Active projects / All Projects</th>
                            <th>Entities names</th>
                            <th>Number of Designs</th>
                            <th>Number of design completed</th>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${(Map) usersMapWithEntities}" var="userAndDetails" status="i">
                            <g:set var="user" value="${(String) userAndDetails?.key}"/>
                            <g:set var="details" value="${(Map) userAndDetails?.value}"/>
                            <tr class="${"0".equalsIgnoreCase(details?.get("entityActiveCount")) ? 'inactiveEntity hidden':'' }">
                                <td>${i}</td>
                                <td>${user}</td>
                                <td>${details?.get("signedUp")?.format("dd.MM.yyyy")}</td>
                                <td>${details?.get("isAuthenticated")}</td>
                                <td>${details?.get("lastLogin")?.format("dd.MM.yyyy")}</td>
                                <td>${details?.get("trialCount")}</td>
                                <td>${details?.get("trialCountInfra")}</td>
                                <td>${details?.get("licenseCount")}</td>
                                <td>${details?.get("licenseNames")}</td>
                                <td>${details?.get("entityActiveCount")} / ${details?.get("entityCount")}</td>
                                <td>
                                    <g:each in="${(Map) userEntitiesWithIds?.get(user)}" var="entity">
                                        <g:if test="${details?.get("entityIdsWithLicenseExpired")?.contains(entity.value)}">
                                            <g:link controller="entity" action="show" params="[entityId: entity?.value]">${entity.key} - INACTIVE TRIAL</g:link><br />
                                        </g:if>
                                        <g:else>
                                            <g:link controller="entity" action="show" params="[entityId: entity?.value]">${entity?.key}</g:link><br />
                                        </g:else>
                                    </g:each>
                                </td>
                                <td>${details?.get("designCount")}</td>
                                <td>${details?.get("designCompletedCount")}</td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </g:if>
            </div>
            <div id="groupByLicense" style="display: none">
                <g:if test="${trialLicenses}">
                    <g:each in="${trialLicenses}" var="license">
                        <g:set var="licensedEntities" value="${license?.licensedEntities}" />
                        <g:set var="activeEntityIds" value="${licensedEntities?.findAll({(license.getChangeHistory(it)?.dateAdded?.plus(license.trialLength) - currentDate) > 0})?.collect({ it.id.toString() })}" />
                        <h3><opt:link action="form" id="${license.id}">${license.name}</opt:link> Active: ${activeEntityIds.size()}, Inactive: ${licensedEntities ? licensedEntities.size() - activeEntityIds.size() : 0}, <a href="javascript:" onclick="showAll('${license.id.toString()}')">Show inactive</a></h3>
                        <table style="${activeEntityIds.size() == 0 ? 'display: none;' : ''}" class="${license.id.toString()} table sortTableTable">
                            <thead>
                            <tr><th>Project name</th><th>Creator</th><th>Date created</th><th>Date added to license</th><th>Active trial</th></tr>
                            </thead>
                            <tbody>
                            <g:each in="${licensedEntities}" var="entity">
                                <g:set var="active" value="${activeEntityIds.find({ it.equals(entity.id.toString()) })}" />
                                <tr style="${!active ? 'display: none;' : ''}" class="${license.id.toString()}"><td><opt:link controller="entity" action="show" params="[entityId:entity.id, name: entity?.name]">${entity.name}</opt:link></td><td>${entity.creatorById?.username ?: 'no creator found probably removed account'}</td>
                                    <td><span class="hidden">${entity.dateCreated?.format("yyyyMMdd")}</span><g:formatDate date="${entity.dateCreated}" format="dd.MM.yyyy" /></td>
                                    <td><span class="hidden">${license.getChangeHistory(entity)?.dateAdded?.format("yyyyMMdd")}</span><g:formatDate date="${license.getChangeHistory(entity)?.dateAdded}" format="dd.MM.yyyy" /></td>
                                    <td>${active ? "ACTIVE" : "FALSE"}</td>
                                </tr>
                            </g:each>
                            </tbody>
                        </table>
                    </g:each>
                </g:if>
                <g:else>
                    <h4>Error retrieving licenses </h4>
                </g:else>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    function showAll(id) {
        $('.' + id).fadeIn();
    }

    $(window).on('load', function () {
        $('.sortTableTable').each(function () {
           $(this).dataTable({
               "bFilter": false,
               "bPaginate": false,
               "aoColumnDefs": [{
                   "bSortable": false,
                   "aTargets": ["no-sort"]
               }]
           });

        });
    });

    function showHideChildDiv2(parentDiv, childTabController){
        var parentDivId = "#" + parentDiv;
        var childDivId = "#" + $(childTabController).attr("name");
        $(parentDivId).children().hide();
        $(childDivId).show();
    }

</script>
</body>
</html>