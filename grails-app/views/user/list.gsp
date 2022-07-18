<%@ page import="groovy.json.JsonBuilder" %>
<%-- Copyright (c) 2012 by Bionova Oy --%>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="format-detection" content="telephone=no"/>
    <asset:stylesheet src="pagination-fix.css"/>
</head>

<body>

<div class="container">
    <div class="screenheader">
        <h4>
            <opt:link controller="main" removeEntityId="true"><g:message code="main"/></opt:link> > <g:message
                    code="user.list.title"/> <br/>
        </h4>

        <h1>
            <i class="layout-icon-user"></i>
            <g:message code="user.list.title"/>
        </h1>
    </div>
</div>

<g:set var="isSuspiciousActTabActive" value="${params.searchSuspiciousUsers as Boolean}" />
<g:set var="isActiveUserTabActive" value="${!isSuspiciousActTabActive}" />
<div class="container section">
    <div class="sectionbody" id="tabs">
        <ul class="nav nav-tabs">
            <li class="navInfo"><a data-toggle="tab" class="load-on-shown${isActiveUserTabActive ? " active" : ""}"
                    href="#activeUserTab">Active users (<span id="activatedUsersCount" class="load-data">
                    <i class='fas fa-circle-notch fa-spin oneClickColorScheme'></i>
                </span>)</a></li>
            <li class="navInfo"><a data-toggle="tab" class="load-on-shown" href="#nonActiveUserTab">Non active users (<span id="notActivatedUsersCount" class="load-data">
                <i class='fas fa-circle-notch fa-spin oneClickColorScheme'></i>
            </span>)</a></li>
            <li class="navInfo"><a data-toggle="tab" class="load-on-shown${isSuspiciousActTabActive ? " active" : ""}"
                    href="#suspiciousActTab">Suspicious Activities (<span id="usersWithSuspiciousActivityCount" class="load-data ">
                    <i class='fas fa-circle-notch fa-spin oneClickColorScheme'></i>
                </span>)</a>
            </li>
            <li class="navInfo">
                <a href="#blacklistTab" class="load-on-shown" data-toggle="tab">Blacklist</a>
            </li>
            <sec:ifAnyGranted roles="ROLE_SYSTEM_ADMIN">
                <li class="navInfo">
                    <a href="#roleMigrationMappingTab" class="load-on-shown" data-toggle="tab">Role migration mapping</a>
                </li>
            </sec:ifAnyGranted>
            <li class="navInfo">
                <a href="#usersWithEnhancedPrivilegesTab" class="load-on-shown" data-toggle="tab"><g:message code="user.list.usersWithEnhancedPrivileges" default="Users with enhanced privileges"/>
                (<span id="usersWithEnhancedPrivilegesCount" class="load-data">
                    <i class='fas fa-circle-notch fa-spin oneClickColorScheme'></i>
                </span>)</a>
            </li>
        </ul>


        <div class="tab-content">
            <div class="tab-pane" id="activeUserTab">
                <g:render template="tabs/activeUserTab" model="[activatedUsersCount: activatedUsersCount]"/>
            </div>
            <div class="tab-pane" id="nonActiveUserTab">
                <opt:spinner spinnerId="loadingSpinner-nonActiveUserTab"/>
            </div>
            <div class="tab-pane" id="suspiciousActTab">
                <opt:spinner spinnerId="loadingSpinner-suspiciousActTab"/>
            </div>
            <div class="tab-pane" id="blacklistTab">
                <opt:spinner spinnerId="loadingSpinner-blacklistTab"/>
            </div>
            <sec:ifAnyGranted roles="ROLE_SYSTEM_ADMIN">
                <div class="tab-pane" id="roleMigrationMappingTab">
                    <opt:spinner spinnerId="loadingSpinner-roleMigrationMappingTab"/>
                </div>
            </sec:ifAnyGranted>
            <div class="tab-pane" id="usersWithEnhancedPrivilegesTab">
                <opt:spinner spinnerId="loadingSpinner-usersWithEnhancedPrivilegesTab"/>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function () {

        $("span.load-data").each( function() {
            loadElementData($(this));
        });

        $('a[data-toggle="tab"].load-on-shown').on('shown', function (e) {
            let activatedTab = e.target;
            let tabSelector = $(activatedTab).attr("href");
            let tabElement = $(tabSelector);

            if (tabSelector == "#activeUserTab") {
                loadActiveUsers($("#activatedUserList"));
            } else {
                loadTabData(tabElement);
            }
        })

        $("#startDate,#endDate").datepicker({
            dateFormat: 'dd.mm.yy',
            changeMonth: true,
            changeYear: true
        }).on('keyup', function (e) {
            if (e.keyCode == 8 || e.keyCode == 46) {
                $.datepicker._clearDate(this);
            }
        });

        $("#activeUserTab").on("click", ".pagination a", function(e) {
            let dataKind = $(e.target).attr("action");
            showHideDiv(dataKind + "Table");
            $("#loadingSpinner-" + dataKind).show();
        });

        $("#activatedUsersSearch").on("change", function(e) {
            loadActiveUsers($("#activatedUserList"));
        })

        $('a[data-toggle="tab"].active').tab('show');

        function loadActiveUsers(element) {
            let dataKind = element.attr("id");
            let actionName = dataKind;
            let spinnerElement = $("#loadingSpinner-" + dataKind);
            let activatedUsersSearchParam = $("#activatedUsersSearch").val();
            let params = { activatedUsersSearch: activatedUsersSearchParam };

            showHideDiv(dataKind + "Table");
            spinnerElement.show();

            $.ajax({
                type: 'GET',
                url: '${g.createLink(action: "index")}'.replace(/index$/, actionName),
                data: params,
                success: function (data) {
                    element.html(data);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    element.html("${g.message(code: "error.general")}");
                    console.log("ajax error: " + XMLHttpRequest.status + " " + errorThrown);
                },
                complete: function () {
                    spinnerElement.hide();
                }
            });
        }

        function loadTabData(element) {
            let dataKind = element.attr("id");
            let actionName = dataKind;
            let spinnerElement = $("#loadingSpinner-" + dataKind);

            if (spinnerElement.is(":hidden")) {
                spinnerElement.show();

                $.ajax({
                    type: 'GET',
                    url: '${g.createLink(action: "index")}'.replace(/index$/, actionName),
                    data: ${new org.grails.web.json.JSONObject(params).toString()},
                    success: function (data) {
                        element.html(data);
                        showHideDiv(dataKind+"Content");
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        element.html("${g.message(code: "error.general")}");
                        console.log("ajax error: " + XMLHttpRequest.status + " " + errorThrown);
                    },
                    complete: function () {
                        spinnerElement.hide();
                    }
                });
            }
        }

        function loadElementData(element) {
            let elementId = element.attr("id");
            let actionName = elementId;

            $.ajax({
                type: 'GET',
                url: '${g.createLink(action: "index")}'.replace(/index$/, actionName),
                success: function (data) {
                    element.html(data);
                    $("." + elementId).html(data);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    element.html('<span class="label label-important">${g.message(code: "dataLoadingFeature.error")}</span>');
                    console.log("ajax error: " + XMLHttpRequest.status + " " + errorThrown);
                }
            });
        }
    });

    function initializeDisabledUserTable() {
        $("#blacklistUserTable").dataTable({
            "order": [[1, 'asc']],
            "bPaginate": true,
            "sPaginationType": "full_numbers",
            "bProcessing": true,
            "bDestroy": true,
            "bDeferRender": true
        });
    }

    function initializeUsersWithEnhancedPrivilegesTable() {
        $("#usersWithEnhancedPrivilegesTable").dataTable({
            "order": [[1, 'asc']],
            "bPaginate": true,
            "sPaginationType": "full_numbers",
            "bProcessing": true,
            "bDestroy": true,
            "bDeferRender": true
        });
    }
</script>
</body>
</html>