<%
    def company = grailsApplication.mainContext.getBean("companyConfiguration")
    def userService = grailsApplication.mainContext.getBean("userService")
    def stringUtilsService = grailsApplication.mainContext.getBean("stringUtilsService")
%>
<g:set var="currentUser" value="${userService.getCurrentUser()}"/>
<g:set var="timestop" value="${System.currentTimeMillis()}" />
<%@ page import="grails.converters.JSON; com.bionova.optimi.core.domain.mongo.User; grails.util.Holders; grails.util.Environment; com.bionova.optimi.core.Constants" %>
<%@ page import="static com.bionova.optimi.core.domain.mongo.FrameStatus.TAB.WORKFLOW as TAB_WORKFLOW" %>
<%@ page import="static com.bionova.optimi.core.domain.mongo.FrameStatus.TAB.HELP as TAB_HELP" %>
<%@ page import="static com.bionova.optimi.core.domain.mongo.FrameStatus.TAB.RESULTBAR as TAB_RESULTBAR" %>
<%@ page import="com.bionova.optimi.construction.Constants as Const" %>

<%@ page trimDirectiveWhitespaces="true" %>
<g:set var="channelFeature" value="${session?.channelFeature}"/>
<g:set var="termsAndConditionsUrl" value="${session?.termsAndConditionsUrl}"/>
<!doctype html>
<html lang="en">
<head>
    <script type="text/javascript">
        var timerStart = Date.now();
    </script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="format-detection" content="telephone=no"/>
    <title><%--
        --%><g:if test="${channelFeature?.title}"><%--
           --%>${channelFeature.title.get(session?.urlLang) ? channelFeature.title.get(session?.urlLang) : channelFeature.title.get("EN")}<%--
        --%></g:if><%--
        --%><g:else><%--
            --%><g:message code="application.title"/><%--
        --%></g:else><%--
   --%></title><%--
   --%><g:if test="${channelFeature?.shortcutIcon}"><%--
       --%>    <link rel="shortcut icon" href="${channelFeature.shortcutIcon}"/><%--
   --%></g:if><%--
   --%><g:else><%--
       --%>    <link rel="shortcut icon" href="/app/assets/360optimi-square-rgb.png"/><%--
   --%></g:else><%--
   // fetches version from build.gradle
   --%><g:set var="app_version" value="${g.meta(name: "info.app.version")}"/><%--
   --%><asset:stylesheet src="bootstrap.css" />
    <asset:stylesheet src="layout.css"/>
    <asset:stylesheet src="content.css"/>
    <asset:stylesheet src="jquery.dataTables-custom.css"/>
    <asset:stylesheet src="jquery-ui-1.8.21.custom.css"/>
    <asset:stylesheet src="font-awesome.css"/>
    <asset:stylesheet src="select2.min.css"/>
    <asset:stylesheet src="dragula.min.css"/>
    <asset:stylesheet src="tooltipsCustom.scss"/>
    <asset:stylesheet src="introjs.css"/>

    <!--[if lte IE 8]>
    <asset:stylesheet src="ie8-and-down.css"/>
    <![endif]-->
    <asset:javascript src="jquery-3.6.0.min.js"/>
    <asset:javascript src="jquery-ui.js"/>
    <%--<asset:javascript src="jquery-migrate-3.1.0.js"/>--%>
    <asset:javascript src="bootstrap.js"/>
    <asset:javascript src="bootstrap-combobox.js"/>
    <asset:javascript src="select2.full.min.js"/>
    <asset:javascript src="maximize-select2-height.min.js"/>
    <asset:javascript src="tether.min.js" />
    <asset:javascript src="core.js" />
    <asset:javascript src="sweetalert2.all.min.js" />
    <asset:javascript src="gstring.js" />
    <asset:javascript src="html2canvas.min.js"/>
    <asset:javascript src="query/verifyDatasets.js"/>
    <g:set var="userFallback" value="${user ?: currentUser}"/>
    <%--<g:if env="production">
        <g:set var="user" value="${session.getAttribute("loggedInUserObject") as User}"/>
        <g:if test="${channelFeature?.enableHotjarTrack && !user?.disableHotjar}">
            <!-- Hotjar Tracking Code for http://www.360optimi.com/app/ [^] -->
            <asset:javascript src="hotJarTrack.js"/>
        </g:if>
    </g:if>--%>
    <g:layoutHead/>
    <link href="https://kendo.cdn.telerik.com/2021.1.224/styles/kendo.common.min.css" rel="stylesheet"/>
    <link href="https://kendo.cdn.telerik.com/2021.1.224/styles/kendo.metro.min.css" rel="stylesheet"/>
    <script src="https://kendo.cdn.telerik.com/2021.1.224/js/kendo.all.min.js" defer></script>
</head>

<body onload="${entity?.showPortfolio ? 'renderPortfolio();' : ''}">
<div class="navbar navbar-fixed-top hide-on-print" ${channelFeature?.navbarColor ? ' style="background: ' + channelFeature?.navbarColor + '  !important;"' : ''}>
<div class="navbar-inner">
<div class="container">
<button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
    <span class="icon-bar"></span>
    <span class="icon-bar"></span>
    <span class="icon-bar"></span>
</button>
<sec:ifLoggedIn>
    <opt:link controller="main" class="brand" removeEntityId="true">
        <g:if test="${channelFeature}">
            <img src="${channelFeature.logo}" alt="" style="max-width:160px; max-height:40px;"/> %{--static image url--}%
        </g:if>
        <g:else>
            <asset:image src="logo.png" alt="360 Optimi"/>
        </g:else>
    </opt:link>

</sec:ifLoggedIn>
<sec:ifNotLoggedIn>
    <g:if test="${channelFeature}">
        <img src="${channelFeature.logo}" alt=""  style="max-width:160px; max-height:42px;" /> %{--static image url--}%
    </g:if>
    <g:else>
        <asset:image src="logo.png" alt="360 Optimi"/>
    </g:else>
</sec:ifNotLoggedIn>
<div class="nav-collapse collapse">
<g:if test="${!hideNavi}">
<sec:ifLoggedIn>
<g:if test="${showSearch}">
    <form class="navbar-form pull-left">
        <div class="input-append">
            <input type="text" id="searchField"
                   class="input-large${!userService.getSuperUser(currentUser) ? ' main_search' : ''}"
                   placeholder="<g:message code="main.navi.search"/>"/><sec:ifAnyGranted
                roles="ROLE_SUPER_USER"><btn class="btn btn-primary" id="searchByName"><i
                    class="icon-search icon-white"></i></btn></sec:ifAnyGranted>
        </div>
    </form>
</g:if>
<g:if test="${userService.getSuperUser(currentUser) && superuserEnabled && showSearch}">
    <div class="navbar-controls pull-left inline-menu">
        <asset:image src="superman_logo.png" rel="popover_bottom"
                     data-content="${message(code: 'main.superuser.search.enabled')}"/>&nbsp;
    </div>
</g:if>

<div class="navbar-controls pull-left btn-group inliner">
    <g:if test="${!session?.nonactiveAccount}">
        <opt:parentEntityClasses var="entityClasses"/>
        <g:set var="licenseService" bean="licenseService"/>
        <g:set var="autoStartTrialAvai" value="${licenseService.getActiveAutoStartTrials()}"/>
        <g:set var="showTrialForNewUser"
               value="${autoStartTrialAvai && userFallback && userFallback.trialCount < 1 && channelFeature?.enableAutomaticTrial}"/>

        <g:if test="${entityClasses}">
            <g:set var="usableEntityClassesForUser" value="${userService.getUsableEntityClasses(currentUser)}"/>
            <g:set var="usableTrialEntityClassesForUser"
                   value="${userService.getUsableTrialEntityClasses()}"/>
            <g:set var="licenseAvailable" value="${userService.getHasLicenses(currentUser)}"/>
            <div class="btn-group inliner">
                <a id="quickStartuptipfml" href="javascript:"
                   class="btn btn-primary dropdown-toggle"
                   rel="startupTip_bottom" id="mainAddQuickStart"
                   data-toggle="dropdown">
                    <i class="icon-plus icon-white"></i>
                    ${licenseAvailable ? message(code: "add") : !licenseAvailable && showTrialForNewUser ? message(code: "start_a_free_trial") : message(code: "add")}
                    &nbsp;
                    <i class="fa fa-caret-down"></i>
                </a>
                <ul class="dropdown-menu">
                    <g:set var="hidePlanetary"
                           value="${userService.getUsableAndLicensedLicenses(userFallback)?.findAll({ !it.planetary })}"/>
                    <g:set var="numberOfCreatedProjects" value="${session?.getAttribute('numberOfCreatedProjects') ?: 0}"/>
                    <g:if test="${!hidePlanetary}">
                        <g:set var="planetaryEntClass"
                               value="${entityClasses?.find({ "building".equals(it.resourceId) })}"/>
                        <g:if test="${planetaryEntClass}">
                            <li>
                                <a href="javascript:" style="font-weight: bold"
                                    <g:if test="${numberOfCreatedProjects <= Const.MAX_NUMBER_OF_CREATED_PROJECTS}">
                                        onclick="startNewProjectModal('startNewProjectModal', 'startNewProjectModalBody', '${planetaryEntClass.resourceId}', true,'${message(code: "unknownError")}')">
                                    </g:if>
                                    <g:else>
                                        onclick="waitToStartNewProjectModal('${message(code: "warning.waitingBeforePojectCreation.title", default: "Waiting before project creation ...")}', '${message(code: "warning.waitingBeforePojectCreation.text", default: "Unusually large number of projects was created during the session.<br/>Log in again to reset wait.<br/>The window will close in <b></b> seconds.")}', ${(numberOfCreatedProjects - Const.MAX_NUMBER_OF_CREATED_PROJECTS) * 3},'startNewProjectModal', 'startNewProjectModalBody', '${planetaryEntClass.resourceId}', true,'${message(code: "unknownError")}')">
                                    </g:else>
                                    <g:message code="${'planetary_project'}"/>
                                    <g:message code='navbar.license_available'/>
                                </a>
                            </li>
                        </g:if>
                    </g:if>
                    <g:each in="${entityClasses}" var="entityClass">
                        <li>
                            <a href="javascript:"
                               style="${(showTrialForNewUser && usableEntityClassesForUser?.contains(entityClass?.resourceId)) || (usableEntityClassesForUser?.contains(entityClass?.resourceId) && licenseAvailable) || channelFeature?.hideEcommerce || (channelFeature?.enableBuildingForChannel && "building".equalsIgnoreCase(entityClass?.resourceId)) ? 'font-weight: bold' : 'color:gray'}"
                                <g:if test="${numberOfCreatedProjects <= Const.MAX_NUMBER_OF_CREATED_PROJECTS}">
                                    onclick="startNewProjectModal('startNewProjectModal', 'startNewProjectModalBody', '${entityClass.resourceId}', false, '${message(code: "unknownError")}')">
                                </g:if>
                                <g:else>
                                    onclick="waitToStartNewProjectModal('${message(code: "warning.waitingBeforePojectCreation.title", default: "Waiting before project creation ...")}', '${message(code: "warning.waitingBeforePojectCreation.text", default: "Unusually large number of projects was created during the session.<br/>Log in again to reset wait.<br/>The window will close in <b></b> seconds.")}', ${(numberOfCreatedProjects - Const.MAX_NUMBER_OF_CREATED_PROJECTS) * 3}, 'startNewProjectModal', 'startNewProjectModalBody', '${entityClass.resourceId}', false, '${message(code: "unknownError")}')">
                                </g:else>
                                ${entityClass?.localizedName}
                                <g:if test="${!channelFeature?.hideEcommerce}">
                                    ${(usableEntityClassesForUser?.contains(entityClass?.resourceId) && licenseAvailable) || (channelFeature?.enableBuildingForChannel && "building".equalsIgnoreCase(entityClass?.resourceId)) ? g.message(code: 'navbar.license_available') : (showTrialForNewUser && usableTrialEntityClassesForUser?.contains(entityClass?.resourceId)) ? message(code: 'navbar.trial_available') : g.message(code: 'navbar_nolicense.help')}
                                </g:if>
                            </a>
                        </li>
                    </g:each>
                </ul>
            </div>
        </g:if>
        <g:else>
            <opt:link class="btn btn-primary" controller="entity" action="form"
                      params="[new: true, entityClass: 'building']">
                <i class="icon-plus icon-white"></i>
                <g:message code="add"/>
            </opt:link>
        </g:else>
    </g:if>
</div>
    <g:set var="account" value="${userService.getAccountForMenu(currentUser)}" />
    <g:if test="${account?.showLogoInSoftware}">
    <div class="btn-group pull-right">
        <a href="javascript:" data-toggle="dropdown" id="accountSettingsButton" class="dropdown-toggle pull-right" style="vertical-align: middle;"><opt:displayDomainClassImage width="200px" height="50px" imageSource="${account?.branding}"/></a>
        <ul class="dropdown-menu">
            <li><opt:link controller="account" action="form" id="${account?.id}" style="font-weight:bold !important;"><g:message code="account.settings"/> ${account?.companyName}</opt:link></li>
        </ul>
    </div>
    </g:if>
    <g:set var="isDeveloper" value="${userService.isDeveloper(currentUser)}"/>
    <div class="pull-right inliner" id="mainNavigationButtonsRight">
            <div class="btn-group inliner">
                <a href="javascript:" onclick="renderLicensesMenu(${reloadOnFloatingStatusChange ? true : false}, ${userForFloatingLicense ? true : false})" class="headingNaviText dropdown-toggle" style="border-radius:5px !important;" data-toggle="dropdown"><g:message code="entity.show.licenses"/>  <span class="caret caret-middle"></span></a>
                <ul class="dropdown-menu" id="licensesMenu">
                    <li class="parent_menu"><a href="#"><div style="width: 100px;" class="skeletonLine"></div></a></li>
                    <li class="parent_menu"><a href="#"><div style="width: 125px;"  class="skeletonLine"></div></a></li>
                    <li class="parent_menu"><a href="#"><div style="width: 75px;"  class="skeletonLine"></div></a></li>
                </ul>
                <%--<opt:renderLicenseMenu channelFeature="${channelFeature}" user="${user}" account="${account}" userForFloatingLicense="${userForFloatingLicense}" loggedInUserObject="${session?.loggedInUserObject}" reloadOnFloatingStatusChange="${reloadOnFloatingStatusChange}" />--%>
            </div>
        <g:if test="${currentUser?.managedLicenseIds || userService.getDataManager(currentUser) || userService.getSalesView(currentUser) || isDeveloper || userService.getConsultant(currentUser)}">
            <div class="btn-group inliner">
                <a href="javascript:" data-toggle="dropdown" id="superUserDropdown" class="dropdown-toggle headingNaviText"><g:message code="main.navi.manage"/> <span class="caret caret-middle"></span></a>
                <ul class="dropdown-menu">
                    <g:if test="${!userService.getDataManager(currentUser)}">
                        <li class="parent_menu"><a href="#"><g:message code="main.navi.user_management"/></a></li>
                    </g:if>
                    <g:if test="${(userService.getSuperUser(currentUser) || currentUser?.managedLicenseIds || userService.getSalesView(currentUser) || userService.getConsultant(currentUser)) && !isDeveloper}">
                        <li class="sub_menu"><opt:link controller="license" removeEntityId="true"><g:message code="licensing"/></opt:link>
                        </li>
                    </g:if>
                    <sec:ifAnyGranted roles="ROLE_SALES_VIEW">
                        <g:if test="${!userService.getDataManager(currentUser)}">
                            <g:if test="${!isDeveloper && !userService.getConsultant(currentUser)}">
                                <li class="sub_menu"><opt:link controller="user"
                                                               removeEntityId="true">Users</opt:link></li>
                            </g:if>
                            <li class="sub_menu"><opt:link controller="account" action="index" removeEntityId="true">Organizations</opt:link> </li>
                        </g:if>
                        <g:if test="${!userService.getDataManager(currentUser) && !userService.getConsultant(currentUser)}">
%{--                                Deprecate code, to be removed by the end of feature 0.5.0 release--}%
%{--                                <li class="sub_menu"><opt:link controller="account" action="accountCatalogues" removeEntityId="true">Ecommerce catalogues</opt:link></li>--}%
                                <li class="sub_menu"><opt:link controller="license" action="trialProjects" removeEntityId="true">Trial projects</opt:link></li>
                                <li class="sub_menu"><opt:link controller="user" action="trialStatusList" removeEntityId="true">Trial status list</opt:link></li>
                                <li class="sub_menu"><opt:link controller="activeUsers" action="list" removeEntityId="true">Current active users</opt:link></li>
                                <li class="sub_menu"><opt:link controller="updatePublish" action="list" removeEntityId="true">Updates publishing</opt:link></li>
%{--                                Deprecate code, to be removed by the end of feature 0.5.0 release--}%
%{--                                <li class="sub_menu"><opt:link controller="epdRequest" action="list" removeEntityId="true">View all EPD Requests</opt:link></li>--}%
                        </g:if>
                    </sec:ifAnyGranted>
                    <sec:ifAnyGranted roles="ROLE_DEVELOPER">
%{--                            Deprecate code, to be removed by the end of feature 0.5.0 release--}%
%{--                            <li class="sub_menu"><opt:link controller="notification" action="list" removeEntityId="true"><g:message code="admin.notification.list"/> (DEPRECATED)</opt:link></li>--}%
                            <li class="sub_menu"><opt:link controller="channelFeature" action="list" removeEntityId="true"><g:message code="admin.channelFeature.list"/></opt:link></li>
                            <li class="sub_menu"><opt:link controller="dataSummary" removeEntityId="true"><g:message code="admin.dataSummary"/></opt:link></li>
                            <li class="sub_menu"><opt:link controller="dataSummary" action="systemEntities" removeEntityId="true">Test dataset entities</opt:link></li>
                            <li class="sub_menu"><opt:link controller="dataSummary" action="carbonHeroEntities" removeEntityId="true">Carbon Heroes entities</opt:link></li>
                            <li class="sub_menu"><opt:link controller="projectTemplate" removeEntityId="true" action="managePublicTemplate">Public Quick Start Templates</opt:link></li>
                            <li class="parent_menu"><a href="javascript:">API management</a></li>
                            <li class="sub_menu"><opt:link controller="oneClickLcaApi" action="list" removeEntityId="true">API requests</opt:link></li>
                            <li class="sub_menu"><opt:link controller="oneClickLcaApi" action="form" removeEntityId="true">API emulator</opt:link></li>
                            <li class="sub_menu"><opt:link controller="oneClickLcaApi" action="resourceDumps" removeEntityId="true" >Download API parameter sets</opt:link></li>
                    </sec:ifAnyGranted>

                    <sec:ifAnyGranted roles="ROLE_DATA_MGR, ROLE_CONSULTANT">
                        <sec:ifAnyGranted roles="ROLE_DATA_MGR">
                            <li class="parent_menu"><a href="javascript:">Carbon Designer 3D</a></li>
                            <li class="sub_menu"><opt:link controller="carbonDesigner3D" removeEntityId="true" action="buildingTypesUpload">Carbon Designer 3D Building Types Excel</opt:link></li>
                            <li class="sub_menu"><opt:link controller="carbonDesigner3D" removeEntityId="true" action="manageCarbonDesigner3DScenarios">Carbon Designer 3D Public Scenarios</opt:link></li>
                        </sec:ifAnyGranted>
                        <li class="parent_menu"><a href="javascript:">Database maintenance</a></li>
                        <sec:ifAnyGranted roles="ROLE_DATA_MGR">
                            <li class="sub_menu"><opt:link controller="import" action="compareResources" removeEntityId="true">Compare resources</opt:link></li>
                            <li class="sub_menu"><opt:link controller="import" action="form" removeEntityId="true">Active <g:message code="import.import_resource"/></opt:link></li>
                            <li class="sub_menu"><opt:link controller="import" action="calculatedResources" removeEntityId="true">Calculated resources</opt:link></li>
                            <li class="sub_menu"><opt:link controller="resourceType" action="benchmark" removeEntityId="true">Data benchmarks</opt:link></li>
                            <g:if test="${userService.getDataManager(currentUser)}">
                                <li class="sub_menu"><opt:link controller="resourceType" removeEntityId="true"><g:message code="admin.resourceTypes"/></opt:link></li>
                            </g:if>
%{--                            Deprecate code, to be removed by the end of feature 0.5.0 release--}%
%{--                            <li class="sub_menu"><opt:link controller="resourceType" action="upstreamDBAverages" removeEntityId="true">Resourcetype averages (CML)</opt:link></li>--}%
                            <li class="sub_menu"><opt:link controller="import" action="resourceStatusReport" removeEntityId="true">Database status report</opt:link></li>
                            <li class="sub_menu"><opt:link controller="recognitionRuleset" action="list" removeEntityId="true">Recognition Rulesets</opt:link></li>
                            <li class="sub_menu"><opt:link controller="account" removeEntityId="true" action="listPrivateDatasets">Account private datasets</opt:link></li>
                            <li class="sub_menu"><opt:link controller="construction" removeEntityId="true" action="constructionsManagementPage"><g:message code="construction.manage"/></opt:link></li>
%{--                            Deprecate code, to be removed by the end of feature 0.5.0 release--}%
%{--                            <li class="sub_menu"><opt:link controller="userDataRequest" removeEntityId="true" action="index">User data requests</opt:link>--}%
                            <li class="sub_menu"><opt:link controller="dbCleanUp" action="corruptedData" removeEntityId="true">Find corrupted data</opt:link></li>
                            <li class="sub_menu"><opt:link controller="carbonDesigner" removeEntityId="true" action="constructionUpload">Carbon Designer Constructions Excel</opt:link></li>
                        </sec:ifAnyGranted>
                        <sec:ifAnyGranted roles="ROLE_CONSULTANT">
                            <li class="sub_menu"><opt:link controller="productDataLists" removeEntityId="true" action="productListManagementPage">Manage data lists</opt:link></li>
                        </sec:ifAnyGranted>
                    </sec:ifAnyGranted>
                </ul>
            </div>
        <g:if test="${userService.isSystemAdmin(currentUser) || isDeveloper}">
            <div class="btn-group inliner">
                <a href="javascript:" data-toggle="dropdown" id="adminDropdown" class="dropdown-toggle headingNaviText" >Admin <span class="caret caret-middle"></span></a>
                <ul class="dropdown-menu">
                    <li class="parent_menu"><a href="javascript:">Application administration</a></li>
                    <li class="sub_menu"><opt:link controller="helpConfiguration" removeEntityId="true" action="index">Help configurations</opt:link></li>
                    <li class="sub_menu"><opt:link controller="dataSummary" removeEntityId="true"><g:message code="admin.dataSummary"/></opt:link></li>
                    <li class="sub_menu"><opt:link controller="import" action="indicatorBenchmarks" removeEntityId="true">Indicator Benchmarks</opt:link></li>
                    <li class="sub_menu"><opt:link controller="dataSummary" action="queries" removeEntityId="true"><g:message code="admin.dataSummary.queries"/></opt:link></li>
                    <li class="sub_menu"><opt:link controller="dataSummary" action="applications" removeEntityId="true"><g:message code="admin.dataSummary.applications"/></opt:link></li>
                    <li class="sub_menu"><opt:link controller="import" removeEntityId="true"><g:message code="admin.import.import_resource"/></opt:link></li>
                    <li class="sub_menu"><opt:link controller="resourceType" removeEntityId="true"><g:message code="admin.resourceTypes"/></opt:link></li>
                    <li class="sub_menu"><opt:link controller="costStructure" action="form" removeEntityId="true">Cost Structures</opt:link></li>
                    <li class="sub_menu"><opt:link controller="localizationFile" removeEntityId="true"><g:message code="admin.localization_files.title" /></opt:link></li>
                    <li class="sub_menu"><opt:link controller="lcaChecker" removeEntityId="true">LCA Checkers</opt:link></li>
                    <li class="sub_menu"><opt:link controller="eolProcess" removeEntityId="true">EOL Processes</opt:link></li>
                    <li class="sub_menu"><opt:link controller="carbonDesigner" removeEntityId="true" action="constructionUpload">Carbon Designer Constructions Excel</opt:link></li>
                    <li class="sub_menu"><g:link url="/app/monitoring">Monitoring</g:link></li>
                    <li class="sub_menu"><opt:link controller="productDataLists" removeEntityId="true" action="alertTemplatePage">Alert Template Page</opt:link></li>
                    <li class="sub_menu"><opt:link controller="projectTemplate" removeEntityId="true" action="managePublicTemplate">Public Quick Start Templates</opt:link></li>
                    <li class="sub_menu"><opt:link controller="dataSummary" action="manualKpiUpdate" >Manual KPI Update</opt:link></li>

                    <li class="parent_menu"><a href="javascript:">Database administration</a></li>
                    <li class="sub_menu"><a href="http://94.237.116.143/server" target="_blank">Data integration server</a></li>
                    <li class="sub_menu"><opt:link controller="dbCleanUp" action="corruptedData" removeEntityId="true">Find corrupted data</opt:link></li>
                    <li class="sub_menu"><opt:link controller="dataMigration" action="index" removeEntityId="true"><g:message code="admin.dataMigration.title"/></opt:link></li>
                    <li class="sub_menu"><opt:link controller="dbCleanUp" action="index" removeEntityId="true"><g:message code="admin.dbCleanUp.title"/></opt:link></li>
                    <li class="sub_menu"><opt:link controller="massRecalculation" action="index" removeEntityId="true">Mass recalculation</opt:link></li>
                    <li class="sub_menu"><opt:link controller="dataSummary" action="nmdUpdateRecords" >View NMD Update status</opt:link></li>
                    <li class="sub_menu"><opt:link controller="nmdElement" action="list" >NMD Elements Management</opt:link></li>

                    <li class="parent_menu"><a href="#">ADMIN_Technical configuration</a></li>
                    <li class="sub_menu"><opt:link controller="configuration" action="form" removeEntityId="true">System configuration</opt:link></li>
                    <li class="sub_menu"><opt:link controller="logs" removeEntityId="true"><g:message code="admin.logs"/></opt:link></li>
                    <g:if env="development">
                        <li class="sub_menu"><opt:link controller="backups" removeEntityId="true">Backups</opt:link></li>
                        <li class="sub_menu"><opt:link controller="console" removeEntityId="true">Console</opt:link></li>
                    </g:if>
                                            <li class="sub_menu"><opt:link controller="backups"
                                                                               action="zippedBackup"
                                                                               removeEntityId="true"
                                                                               onclick="return modalConfirm(this);"
                                                                               data-questionstr="${message(code: 'admin.backups.zippedBackup.question')}"
                                                                               data-truestr="${message(code: 'yes')}"
                                                                               data-falsestr="${message(code: 'cancel')}"
                                                                               data-titlestr="${message(code: 'admin.backups.zippedBackup.header')}"><g:message
                            code="admin.backups.zippedBackup"/></opt:link></li>
                </ul>
            </div>
        </g:if><%--
        --%></g:if>
        <div class="btn-group inliner">
            <a href="javascript:" data-toggle="dropdown" id="helpDrownDown" class="headingNaviText dropdown-toggle" style="text-transform:uppercase" />
               <i class="fa fa-question-circle"></i> <g:message code="help.instructions"/> <span class="caret caret-middle"></span></a>
            <g:set var="configuration" bean="configurationService"/>
        <ul class="dropdown-menu">
            <li><%--
                                        --%><g:set var="helpUrl"
                                                   value="${channelFeature?.helpUrl ? channelFeature.helpUrl : channelFeature?.helpDocumentUrls?.get(session?.urlLang) ? channelFeature.helpDocumentUrls.get(session?.urlLang) : channelFeature?.helpDocumentUrls?.get("EN")}"/><%--
                                        --%><g:if test="${helpUrl}"><%--
                                            --%><a href="${helpUrl}" target="_blank"><g:message
                    code="main.navi.user_instructions"/></a><%--
                                        --%></g:if><%--
                                        --%><g:else><%--
                                            --%><g:if test="${'FI'.equals(session?.urlLang)}"><%--
                                                --%><a href="${com.bionova.optimi.core.Constants.HELP_CENTER_URL}"
                                                       target="_blank"><g:message
                        code="main.navi.user_instructions"/></a><%--
                                            --%></g:if><%--
                                            --%><g:else><%--
                                                --%><a href="${com.bionova.optimi.core.Constants.HELP_CENTER_URL}"
                                                       target="_blank"><g:message
                        code="main.navi.user_instructions"/></a><%--
                                            --%></g:else><%--
                                        --%></g:else><%--
                                    --%></li>
            <li><a href="${configuration.getByConfigurationName(null, "serviceUpdateLog")?.value ?: "https://www.oneclicklca.com/support/customer-support/updates/service-update-log/"}" target="_blank" ><g:message code="help.service_log"/></a></li>
            <li><a href="https://oneclicklca.zendesk.com/hc/en-us/articles/360015040200" target="_blank"><g:message code="update_log" /></a></li>
            <li><a href="https://www.oneclicklca.com/lca-training-courses/" target="_blank"><g:message code="main.navi.training"/></a></li>
            <li><a href="${termsAndConditionsUrl}" target="_blank"><g:message
                    code="main.navi.terms"/></a></li>
            <g:if test="${channelFeature?.showPoweredBy}">
                <li><a href="https://www.360optimi.com"><asset:image src="powered-by-optimi360.png" alt=""/></a></li>
            </g:if>
        </ul>
        </div>
        <div class="btn-group inliner">
            <a href="javascript:" data-toggle="dropdown" class="dropdown-toggle headingNaviText">
                <i class="icon-user ${Environment.current == Environment.DEVELOPMENT && user?.id?.toString()?.equalsIgnoreCase("57164b067d1ec846f40f4b5b") ? 'banana' : ''}"></i> ${userService.getFirstNameForUi(currentUser.name)} <span class="caret caret-middle"></span><g:if env="production" test="${request.getServerName() in Constants.PROD_DOMAINS}">${userService.getSuperUser(currentUser) && !prodWarning ? ' <span style=\"color: red;\">PROD</span>' : ''}</g:if>
            </a><ul class="dropdown-menu">
            <sec:ifLoggedIn>
                <g:if test="${userService.getSuperUser(currentUser) && showSearch && !superuserEnabled}">
                    <li><opt:link controller="main" action="list"
                                  params="[superuserEnabled: true]"
                                  removeEntityId="true"><g:message
                                code="main.navi.activate_superuser_mode"/></opt:link></li>
                </g:if>
                <li><opt:link controller="user" action="form" removeEntityId="true"
                              params="['id': currentUser?.id]"><g:message
                            code="main.navi.modify_user" args="[currentUser?.name]"/></opt:link></li>
                <g:set var="account" value="${userService.getAccountForMenu(currentUser)}" />
                <g:if test="${account}">
                    <li><g:link controller="account" action="form" id="${account.id}"><g:message code="main.navi.account" args="[account.companyName?.encodeAsHTML()]" /></g:link></li>
                    <g:set var="featureIds" value="${accountLicenses ? accountLicenses.collect({it.licensedFeatureIds})?.flatten()?.unique()?.collect({it.toString()}) : []}" />
                    <g:if test="${featureIds?.contains("59b9740a5d96ff277aa174c2")}">
                        <li><g:link controller="account" action="privateDatasets" params="[accountId: account.id]"><g:message code="account.private_datasets"/> ${account.companyName?.encodeAsHTML() ?: "Unnamed company"}</g:link></li>
                    </g:if>
                    <g:if test="${featureIds?.contains("59e1fe0f5d96ff5181ee9ca6")}">
                        <li><g:link controller="construction" action="index" params="[accountId: account.id]" ><g:message code="account.private_constructions"/> ${account.companyName?.encodeAsHTML() ?: "Unnamed company"}</g:link></li>
                    </g:if>
                </g:if>
                <g:else>
                    <li><g:link controller="account" action="form" params="[join: true]"><g:message code="main.navi.create_account" /></g:link></li>
                </g:else>
                <li><g:link controller="logout" action="index"><g:message
                        code="main.navi.logout"/></g:link></li>
            </sec:ifLoggedIn>
        </ul>
        </div>
    </div>
</sec:ifLoggedIn>
</g:if>
</div>
</div>
</div>
</div>

%{--ADD NEW PROJECT MODAL--}%
<div id="startNewProjectModal" class="modal fade hide modalNewProject">
    <div id="startNewProjectModalBody"></div>
</div>
%{--Localized text, to be used whenever needed--}%
<div id="somethingWentWrongLocalized" class="hide">${message(code: 'unknownError')}</div>

<%-- LATERAL FRAME WORKFLOW AND HELP --%>
<g:set var="userEnableHelpWorkflow" value="${user?.enableFloatingHelp}"/>
<g:set var="showHelp" value="${helpConfigurations && channelFeature?.allowFloatingHelp}"/>
<g:set var="helpConfigurationService" bean="helpConfigurationService"/>
<g:set var="workFlowService" bean="workFlowService"/>

<g:if test="${userEnableHelpWorkflow && !showUpdateBar}">
    <div style="cursor:pointer;" class="floatProgressToggler partOfFloatNav vertical-text hide-on-print">
        <g:if test="${workFlowForEntity}">
            <div onclick="toggleNavProg(this.id, '${TAB_WORKFLOW}')" class="hide-on-print theme-green">
                <span id="sideTogglerProgress" class="hide-on-print padding-5px">
                    <h3 id="sideTogglerHeader"><g:message code="workflow.side_header"/></h3>
                    <i class="fa fa-caret-up white-font showProgressNavArrow hide-on-print"></i>
                </span>
            </div>
        </g:if>
        <g:if test="${showResultBar}">
            <div onclick="toggleNavProg(this.id, '${TAB_RESULTBAR}')" class="hide-on-print theme-blue">
                <span id="sideTogglerResult" class="hide-on-print padding-5px">
                    <h3 id="sideTogglerResultHeader" onclick="renderQueryResultGraphs('${indicator?.indicatorId}', '${entity?.id}');">
                        <g:message code="results"/>
                    </h3>
                    <i class="fa fa-caret-up white-font showProgressNavArrow hide-on-print"></i>
                </span>
            </div>
        </g:if>
        <g:if test="${showHelp}">
            <div onclick="toggleNavProg(this.id, '${TAB_HELP}')" class="hide-on-print theme-dark-gray">
                <span id="sideTogglerHelp" class="hide-on-print padding-5px">
                    <h3 id="sideTogglerHelpHeader" ><g:message code="guidance"/></h3>
                    <i class="fa fa-caret-up white-font showProgressNavArrow hide-on-print"></i>
                </span>
            </div>
        </g:if>

    </div>

    <div id="myProgressNav" class="floatingProgress hiddenSideBar partOfFloatNav hide-on-print" >
        <div id="progressHeader">
            <g:if test="${workFlowForEntity}">
                <div class="selectedHeader">
                    <p id="${TAB_WORKFLOW}Header" onclick="showHideDivHelpWorkFlow('${TAB_WORKFLOW}')"><g:message code="workflow.main_header"/></p>
                </div>
            </g:if>
            <g:if test="${showResultBar}">
                <div class="${!workFlowForEntity ? 'selectedHeader':'unselectedHeader'}">
                    <p id="${TAB_RESULTBAR}Header" onclick="showHideDivHelpWorkFlow('${TAB_RESULTBAR}'); renderQueryResultGraphs('${indicator?.indicatorId}', '${entity?.id}');">
                        <g:message code="results"/>
                    </p>
                </div>
            </g:if>
            <g:if test="${showHelp}">
                <div class="${!workFlowForEntity && !showResultBar ? 'selectedHeader':'unselectedHeader'}">
                    <p id="${TAB_HELP}Header" onclick="showHideDivHelpWorkFlow('${TAB_HELP}')"><g:message code="guidance"/></p>
                </div>
            </g:if>



        </div>

        <div>
            <g:if test="${workFlowForEntity}">
                <div id="${TAB_WORKFLOW}Div">
                    <div class="text-left" style="padding: 5px">
                        <div class="input-append">
                            <label style="font-weight:bold;"><g:message code="select_workFlow"/></label>
                            <g:select class="dropdown" name="workFlowIdDropdown" from="${workFlowForEntity.workFlowList ?: []}"
                                      value="${workFlowForEntity.defaultWorkFlowId ?: ""}" optionValue="${{workFlowService.getLocName(it?.name)}}"
                                      optionKey="${{it?.workFlowId}}" onchange="onChangeWorkFlowId(this, '${message(code: "save")}','${message(code: "clear")}','${message(code: "reset_workflow")}')"
                                      />
                        </div>
                    </div>
                    <div style="padding-bottom: 80px">
                        <div class="input-append" id="workFlowContent"></div>
                    </div>
                </div>
            </g:if>

            <g:if test="${showHelp}">
                <div id="${TAB_HELP}Div" style="padding-bottom: 60px; ${!workFlowForEntity ? 'padding-top: 40px;' : 'padding-top: 40px; display: none'}">
                    <g:each in="${helpConfigurations?.sort({it.contentType})?.reverse()}" var="helpConfig">
                        <g:if test="${helpConfig.contentType.equalsIgnoreCase("video")}">
                            <iframe width="250" height="200" src="${helpConfig.contentURL}" frameborder="0" allowfullscreen></iframe>
                        </g:if>
                        <br>
                        <g:if test="${helpConfig.contentType?.replaceAll(" ", "")?.equalsIgnoreCase("page")}">
                            <div class="floatingProgressInner helpItems"><a href="${helpConfig.contentURL}" target="_blank">${helpConfigurationService.getLocalizedContentName(helpConfig)}</a></div>
                        </g:if>
                    </g:each>
                </div>
            </g:if>
            <g:if test="${showResultBar}">
                <div id="${TAB_RESULTBAR}Div" style="padding-bottom: 60px; ${!workFlowForEntity ? 'padding-top: 10px;' : 'padding-top: 10px; display: none'}">
                    <div id="sideChartWrapper">
                        <div class="loading-spinner span8" style="width: 100%;" id="loadingSpinner_sideBar">
                            <div class="image">
                                <svg class="loadingSVG" version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg"  x="0px" y="0px"
                                     width="270px" height="270px" viewBox="0 0 32 32" style="enable-background:new 0 0 32 32;" xml:space="preserve">
                                    <g>
                                        <path class="arrowSpinningSvg mm-o-syncing-queue" d="M24.5,10.4L22.2,11l5,5L29,9.2l-2.4,0.7C24.4,6.1,20.4,3.8,16,3.8c-5.4,0-10.1,3.4-11.7,8.6
        c-0.2,0.5,0.1,1.1,0.7,1.3c0.1,0,0.2,0,0.3,0c0.4,0,0.8-0.3,1-0.7c1.3-4.3,5.2-7.2,9.7-7.2C19.5,5.8,22.7,7.5,24.5,10.4z"/>
                                    </g>
                                </svg>
                                <p class="working"><g:message code="loading.working"/>.</p>
                            </div>

                        </div>
                    </div>
                </div>
            </g:if>
        </div>
        <div class="hideProgressArrowDiv"> <a onclick="closeNavProg('myProgressNav')" ><i class="fa fa-caret-left hideProgressArrow"></i></a> </div>
        <div id="split-bar"></div>
    </div>
</g:if>
<g:elseif test="${showUpdateBar}">
    <div id="myUpdateNav" class="updateBarFrame partOfFloatNav " >

        <div id="updateBar">
            <g:if test="${sortedUpdateList}">
                <g:set value="${sortedUpdateList.findAll({it?.type == "event"})}" var="eventAndWebinarUpdates"/>
                <g:set value="${sortedUpdateList.findAll({it?.type == "update"})}" var="featureUpdates"/>
                <g:if test="${eventAndWebinarUpdates}">
                    <div id="eventAndWebinarDiv" class="updateWrapperParent">
                        <h3> <i class="fas fa-calendar-alt" aria-hidden="true"></i> <g:message code="events"/></h3>
                        <g:each in="${eventAndWebinarUpdates}" var="ewUpdate" status="i">
                                <div class="updateWrapper ${i < 5 ? '': 'hidden'}">
                                    <h5>${ewUpdate?.title}</h5>
                                    <g:if test="${ewUpdate?.additionalInfo?.size() > 120}">
                                        <p class="text-wrapped inline-block" data-attributeFullValue="${ewUpdate?.additionalInfo}">${stringUtilsService.abbr(ewUpdate?.additionalInfo, 120)} ...<span onclick="expandSourceListingAttribute($(this).closest('p', event), event,$(this).parent().siblings('a'));" class="fakeLink"><i class="fa fa-plus"></i> <g:message code="more"/></span></p>
                                        <g:if test="${ewUpdate?.link}">
                                            <a class="inline-block" href="${ewUpdate?.link}" target="_blank" style="display: none;" ><i class="fas fa-external-link-alt" aria-hidden="true"></i>  <g:message code="index.register"/></a>
                                        </g:if>
                                    </g:if>
                                    <g:else>
                                        <p class="inline-block">${ewUpdate?.additionalInfo}</p>
                                        <g:if test="${ewUpdate?.link}">
                                            <i class="fas fa-external-link-alt inline-block" aria-hidden="true"></i> <a target="_blank" href="${ewUpdate?.link}"> <g:message code="index.register"/></a>
                                        </g:if>
                                    </g:else>
                                </div>
                        </g:each>
                        <g:if test="${eventAndWebinarUpdates.size() > 5 }">
                            <div class="text-center inline-block"><button class="btn btn-primary" onclick="toggleMultipleElements(this)"><i class="fa fa-plus" style="vertical-align:middle;"></i>  <g:message code="show.all"/></button></div>
                        </g:if>
                    </div>
                </g:if>
                <g:if test="${featureUpdates}">
                    <div id="featureDiv" class="updateWrapperParent">
                        <h3> <i class="far fa-newspaper" aria-hidden="true"></i> <g:message code="updates"/></h3>
                        <g:each in="${featureUpdates}" var="fUpdate" status="i">
                                <div class="updateWrapper ${i < 5 ? '' : 'hidden'}">
                                    <h5>${fUpdate?.title}</h5>
                                    <g:if test="${fUpdate?.additionalInfo?.size() > 120}">
                                        <p class="text-wrapped inline-block" data-attributeFullValue="${fUpdate?.additionalInfo}">${stringUtilsService.abbr(fUpdate?.additionalInfo, 120)} ...<span onclick="expandSourceListingAttribute($(this).closest('p', event), event,$(this).parent().siblings('a'));" class="fakeLink"><i class="fa fa-plus"></i> <g:message code="more"/></span></p>
                                        <g:if test="${fUpdate?.link}">
                                            <a class="inline-block" href="${fUpdate?.link}" target="_blank" style="display: none;" ><i class="fas fa-external-link-alt" aria-hidden="true"></i>  <g:message code="more_info"/></a>
                                        </g:if>
                                    </g:if>
                                    <g:else>
                                        <p class="inline-block">${fUpdate?.additionalInfo}</p>
                                        <g:if test="${fUpdate?.link}">
                                            <i class="fas fa-external-link-alt inline-block" aria-hidden="true"></i> <a target="_blank" href="${fUpdate?.link}"> <g:message code="more_info"/></a>
                                        </g:if>
                                    </g:else>
                                </div>
                        </g:each>
                        <g:if test="${featureUpdates.size() > 5 }">
                            <div class="text-center"><button class="btn btn-primary" onclick="toggleMultipleElements(this)"><i class="fa fa-plus" style="vertical-align:middle;"></i>  <g:message code="show.all"/></button></div>
                        </g:if>
                    </div>
                </g:if>
            </g:if>
            <g:if test="${showHelp}">
                <div id="guidanceDiv" style="padding-top: 40px;" class="updateWrapperParent">
                    <h3> <i class="fa fa-book" aria-hidden="true"></i> <g:message code="guidance"/></h3>
                    <g:each in="${helpConfigurations?.sort({it.contentType})?.reverse()}" var="helpConfig">
                        <g:if test="${helpConfig.contentType.equalsIgnoreCase("video")}">
                            <iframe width="250" height="200" src="${helpConfig.contentURL}" frameborder="0" allowfullscreen></iframe>
                        </g:if>
                        <br>
                        <g:if test="${helpConfig.contentType?.replaceAll(" ", "")?.equalsIgnoreCase("page")}">
                            <div class="floatingProgressInner updateWrapper"><a href="${helpConfig.contentURL}" target="_blank"><strong>${helpConfigurationService.getLocalizedContentName(helpConfig)}</strong></a></div>
                        </g:if>
                    </g:each>
                </div>
            </g:if>

        </div>
        <div class="hideProgressArrowDiv close"> <a onclick="closeNavProg('myUpdateNav')" style="text-decoration: none !important; font-weight: bold"> x </a> </div>

    </div>

</g:elseif>
<%-- END LATERAL FRAME WORKFLOW AND HELP --%>

<div class="clearfix"></div>

<div class="modal hide modal large" id="floatingLicenseModal">
    <div class="modal-header text-center"><button id="closeFloatingLicenseModalBtn-x" type="button" class="close" data-dismiss="modal">&times;</button>
        <div id="floatingLicenseModalHeading" style="margin-top: 10px; margin-bottom: 5px;" class="btn-group inliner"></div>
    </div>
    <div class="modal-body" style="display: none;" id="floatingLicenseModalError"></div>
    <div class="modal-body" id="floatingLicenseModalBody"></div>
</div>
<g:if test="${showSearch}">
    <opt:renderPopUpTrial stage="${trialsAvailable ? 'trialsAvailable' : 'noProjectAddHomePage'}" entities="${entities}"/>
</g:if>

<div class="container ${showUpdateBar ? 'padding-update homePageFixedWidth' : ''}" id="messageContent">

    %{-- Special condition alerts  --}%
    <g:if test="${possibleAccounts}">
        <div class="alert alert-success hide-on-print">
            <button type="button" class="close" data-dismiss="alert" style="top: 0px;">×</button>
            <div id="possibleAccounts">
                <strong><g:message code="account.want_join"/></strong><br />
                <g:each in="${possibleAccounts}" var="account">
                    ${account.companyName?.encodeAsHTML()} <a href="javascript:" onclick="accountRequestToJoin('${account.id}');">(request to join)</a><br />
                </g:each>
            </div>
        </div>
    </g:if>

    <g:if test="${prodWarning}">
        <div class="alert alert-error hide-on-print">
            <button type="button" class="close" data-dismiss="alert" style="top: 0px;">×</button>
            <strong>${raw(prodWarning)}</strong>
        </div>
    </g:if>

    <g:hasErrors bean="${entity}">
        <div class="alert alert-danger hide-on-print">
            <button data-dismiss="alert" class="close" type="button" style="top: 0px;">×</button>
            <g:renderErrors bean="${entity}"/>
        </div>
    </g:hasErrors>

    <g:hasErrors bean="${mongoObject}">
        <div class="alert alert-danger hide-on-print">
            <button data-dismiss="alert" class="close" type="button" style="top: 0px;">×</button>
            <g:renderErrors bean="${mongoObject}"/>
        </div>
    </g:hasErrors>

    %{-- Possibly useless as I can't see any code calls for this but I can see session attribute calls, which might call this, so leaving it in just incase --}%
    <g:if test="${request.calculationError}">
        <div class="alert alert-error hide-on-print" >
            <button data-dismiss="alert" class="close" type="button">×</button>
            <strong>${raw(request.calculationError)}</strong>
        </div>
    </g:if>

    <g:if test="${flash.userActivationError}">
        <div id="flash-userActivationError" class="alert alert-error fadetoggle hide-on-print" data-fadetoggle-delaytime="30000">
            <button data-dismiss="alert" class="close" type="button" style="top: 0px;">×</button>
            <strong>${raw(flash.userActivationError)}</strong>
        </div>
    </g:if>

    <g:if test="${session?.importIncomplete}">
        <div class="alert hide-on-print">
            <button data-dismiss="alert" class="close" type="button" style="top: 0px;">×</button>
            <strong>Import incomplete. Please run the ResourceSubType Data benchmarks.</strong>
        </div>
    </g:if>

    %{-- Fade Alerts --}%
    <g:if test="${flash.fadeSaveAlert}">
        <div id="flash-fadeSaveAlert" class="alert alert-success fadetoggle hide-on-print" data-fadetoggle-delaytime="7000">
            <i class="far fa-save pull-left" style="font-size: large; margin-right: 8px;"></i>
            <i data-dismiss="alert" class="fas fa-circle-notch fa-spin close" style="top: 0px;"></i>
            <strong>${raw(flash.fadeSaveAlert)}</strong>
        </div>
    </g:if>
    <g:if test="${flash.fadeSuccessAlert}">
        <div id="flash-fadeSuccessAlert" class="alert alert-success fadetoggle hide-on-print" data-fadetoggle-delaytime="7000">
            <i class="far fa-thumbs-up pull-left" style="font-size: large; margin-right: 8px;"></i>
            <i data-dismiss="alert" class="fas fa-circle-notch fa-spin close" style="top: 0px;"></i>
            <strong>${raw(flash.fadeSuccessAlert)}</strong>
        </div>
    </g:if>
    <g:if test="${flash.fadeInfoAlert}">
        <div id="flash-fadeInfoAlert" class="alert alert-info fadetoggle hide-on-print" data-fadetoggle-delaytime="7000">
            <i class="fas fa-info pull-left" style="font-size: large; margin-right: 8px;"></i>
            <i data-dismiss="alert" class="fas fa-circle-notch fa-spin close" style="top: 0px;"></i>
            <strong>${raw(flash.fadeInfoAlert)}</strong>
        </div>
    </g:if>
    <g:if test="${flash.fadeWarningAlert}">
        <div id="flash-fadeWarningAlert" class="alert alert-warning fadetoggle hide-on-print" data-fadetoggle-delaytime="7000">
            <i class="fas fa-exclamation pull-left" style="font-size: large; margin-right: 8px;"></i>
            <i data-dismiss="alert" class="fas fa-circle-notch fa-spin close" style="top: 0px;"></i>
            <strong>${raw(flash.fadeWarningAlert)}</strong>
        </div>
    </g:if>
    <g:if test="${flash.fadeErrorAlert}">
        <div id="flash-fadeErrorAlert" class="alert alert-error fadetoggle hide-on-print" data-fadetoggle-delaytime="7000">
            <i class="fas fa-times pull-left" style="font-size: large; margin-right: 8px;"></i>
            <i data-dismiss="alert" class="fas fa-circle-notch fa-spin close" style="top: 0px;"></i>
            <strong>${raw(flash.fadeErrorAlert)}</strong>
        </div>
    </g:if>

    %{-- Standard Alerts - with closable button --}%
    <g:if test="${flash.saveAlert}">
        <div id="flash-saveAlert" class="alert alert-success hide-on-print">
            <i class="far fa-save pull-left" style="font-size: large; margin-right: 8px;"></i>
            <button data-dismiss="alert" class="close" style="top: 0px;" type="button">×</button>
            <strong>${raw(flash.saveAlert)}</strong>
        </div>
    </g:if>
    <g:if test="${flash.successAlert}">
        <div id="flash-successAlert" class="alert alert-success hide-on-print">
            <i class="far fa-thumbs-up pull-left" style="font-size: large; margin-right: 8px;"></i>
            <button data-dismiss="alert" class="close" style="top: 0px;" type="button">×</button>
            <strong>${raw(flash.successAlert)}</strong>
        </div>
    </g:if>
    <g:if test="${flash.infoAlert}">
        <div id="flash-infoAlert" class="alert alert-info hide-on-print">
            <i class="fas fa-info pull-left" style="font-size: large; margin-right: 8px;"></i>
            <button data-dismiss="alert" class="close" style="top: 0px;" type="button">×</button>
            <strong>${raw(flash.infoAlert)}</strong>
        </div>
    </g:if>
    <g:if test="${flash.warningAlert}">
        <div id="flash-warningAlert" class="alert alert-warning hide-on-print">
            <i class="fas fa-exclamation pull-left" style="font-size: large; margin-right: 8px;"></i>
            <button data-dismiss="alert" class="close" style="top: 0px;" type="button">×</button>
            <strong>${raw(flash.warningAlert)}</strong>
        </div>
    </g:if>
    <g:if test="${flash.errorAlert}">
        <div id="flash-errorAlert" class="alert alert-error hide-on-print">
            <i class="fas fa-times pull-left" style="font-size: large; margin-right: 8px;"></i>
            <button data-dismiss="alert" class="close" style="top: 0px;" type="button">×</button>
            <strong>${raw(flash.errorAlert)}</strong>
        </div>
    </g:if>

    %{-- Permanent Alerts - with no closable options --}%
    <g:if test="${flash.permSaveAlert}">
        <div id="flash-permSaveAlert" class="alert alert-success hide-on-print">
            <i class="far fa-save pull-left" style="font-size: large; margin-right: 8px;"></i>
            <strong>${raw(flash.permSaveAlert)}</strong>
        </div>
    </g:if>
    <g:if test="${flash.permSuccessAlert}">
        <div id="flash-permSuccessAlert" class="alert alert-success hide-on-print">
            <i class="far fa-thumbs-up pull-left" style="font-size: large; margin-right: 8px;"></i>
            <strong>${raw(flash.permSuccessAlert)}</strong>
        </div>
    </g:if>
    <g:if test="${flash.permInfoAlert}">
        <div id="flash-permInfoAlert" class="alert alert-info hide-on-print">
            <i class="fas fa-info pull-left" style="font-size: large; margin-right: 8px;"></i>
            <strong>${raw(flash.permInfoAlert)}</strong>
        </div>
    </g:if>
    <g:if test="${flash.permWarningAlert}">
        <div id="flash-permWarningAlert" class="alert alert-warning hide-on-print">
            <i class="fas fa-exclamation pull-left" style="font-size: large; margin-right: 8px;"></i>
            <strong>${raw(flash.permWarningAlert)}</strong>
        </div>
    </g:if>
    <g:if test="${flash.permErrorAlert}">
        <div id="flash-permErrorAlert" class="alert alert-error hide-on-print">
            <i class="fas fa-times pull-left" style="font-size: large; margin-right: 8px;"></i>
            <strong>${raw(flash.permErrorAlert)}</strong>
        </div>
    </g:if>

</div>

<div class="loading-spinner span8" style="display:none; margin: 0 auto; width: 100%;" id="localizedSpinner">
    <div class="image">
        <svg class="loadingSVG" version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg"  x="0px" y="0px"
             width="270px" height="270px" viewBox="0 0 32 32" style="enable-background:new 0 0 32 32;" xml:space="preserve">
            <g>
                <path class="arrowSpinningSvg mm-o-syncing-queue" d="M24.5,10.4L22.2,11l5,5L29,9.2l-2.4,0.7C24.4,6.1,20.4,3.8,16,3.8c-5.4,0-10.1,3.4-11.7,8.6
c-0.2,0.5,0.1,1.1,0.7,1.3c0.1,0,0.2,0,0.3,0c0.4,0,0.8-0.3,1-0.7c1.3-4.3,5.2-7.2,9.7-7.2C19.5,5.8,22.7,7.5,24.5,10.4z"/>
            </g>
        </svg>
        <p class="working"><g:message code="loading.working"/>.</p>
    </div>

</div>

<g:if test="${request.majorMessage}">
    <div class="container">
        <div class="well well-large text-center">
            <button type="button" class="close" data-dismiss="alert">&times;</button>
            ${raw(request.majorMessage)}
        </div>
    </div>
</g:if>

<g:if test="${popUpUpdate}">
        <div class="modal modal large" id="modalPopUp">
            <div class="modal-header text-center"><button id="closeNotificationPopupBtn-x" type="button" class="close" data-dismiss="modal">&times;</button>
                <h2>${popUpUpdate?.title}</h2>
            </div>
            <div class="modal-body" id="modalPopUpBody">
                <div>
                    <p>${popUpUpdate?.additionalInfo}</p>
                    <g:if test="${popUpUpdate?.link}">
                        <i class="fas fa-external-link-alt" aria-hidden="true"></i> <a target="_blank" href="${popUpUpdate?.link}"> <g:message code="${popUpUpdate?.type == 'event' ? 'index.register' : 'more_info'}"/></a>
                    </g:if>
                </div>
                <div>

                </div>
            </div>
            <div class="modal-footer">
                <g:checkBox name="hideNotification" checked="${false}" />
                <g:message code="notification.popUp.hideNotification" />
                <a id="closeNotificationPopupBtn" style="margin-left: 3px;" href="#" class="btn btn-primary" data-dismiss="modal" onclick="hideNotificationPopUp('${popUpUpdate?.id}');"><g:message code="close" /></a>
            </div>
        </div>
</g:if>
<div class="modal large hidden" id="workflowStartPop">
    <div class="modal-header text-center"><button type="button" class="close" data-dismiss="modal">&times;</button>
        <h2>${message(code: "onboarding_workflow.header_modal")}</h2>
    </div>
    <div class="modal-body">
        <div>
            <p>${message(code: "onboarding_workflow.body_modal")}</p>
        </div>
    </div>
    <div class="modal-footer">

        <button type="button" class="btn btn-default pull-left" data-dismiss="modal" onclick="turnOffOnboarding('resetWorkflowBtn','${message(code:'enable_onboarding.instruction')}')"><g:message code="user.disableOnboarding" /></button>
        <a style="margin-left: 3px;" href="#" class="btn btn-primary pull-right" data-dismiss="modal" onclick="initializeOnboardingWorkflow(jsonWorkFlow.workFlowList.find(it => it.workFlowId == jsonWorkFlow.defaultWorkFlowId), '${message(code: "exit_workflow")}', '${message(code: "next")}');"><g:message code="start" /></a>
    </div>
</div>

<g:layoutBody/>

<g:if test="${channelFeature?.showPoweredBy && showPoweredBy}">
    <div class="footer"><a href="https://www.360optimi.com"><asset:image src="powered-by-optimi360.png"
                                                                         alt=""/></a></div>
</g:if>
<g:else>
    <div class="footer">
        ${company.name} &copy; &nbsp; copyright ${company.name} LTD | Version: ${app_version}, &nbsp; Database version: 7.6 &nbsp;
        <opt:showEnvironmentBuild/>
        <br/>
        <span style="color: white;" id="mainContentRenderTime"></span>
    </div>
</g:else>

<asset:javascript src="constants.js"/>
<asset:javascript src="layout.js"/>
<asset:javascript src="jquery.dataTables.min.js"/>
<asset:javascript src="jquery.dataTables.bootstrap-mod.js"/>
<asset:javascript src="bootstrap-dropdown.js"/>
<asset:javascript src="bootstrap-collapse.js"/>
<asset:javascript src="bootstrap-affix.js"/>
<asset:javascript src="360optimi.js"/>
<asset:javascript src="flashTemplates.js"/>
<asset:javascript src="intro.js"/>
<%--<asset:javascript src="360optimiES6.js.es6"/>--%>
<asset:javascript src="dragula.min.js" />

<script type="text/javascript">
    <g:if test="${popUpUpdate}">
        $(function(){
            showNotificationPopUp()
        })
    </g:if>

    $('ul.dropdown-menu.autostartTrialDropdown').on('click', ".showMoreLi", function(event){
        event.stopPropagation(); //Always stop propagation
    });


    function getValidationNotNumericMsg(arg){
        var str ="${g.message(code: 'js.question.validation.not_numeric')}";
        var msg = new GString(str);
        msg.addBindings({
            0: arg
        });
        return "" + msg;
    }
    function getValidationNotInLimitsMsg(arg0, arg1, arg2){
        var str ="${g.message(code: 'js.question.validation.not_in_limits')}";
        var msg = new GString(str);
        msg.addBindings({
            0: arg0,
            1: arg1,
            2: arg2
        });
        return "" + msg;
    }
    function getValidationMinValueMsg(arg0, arg1){
        var str ="${g.message(code: 'js.question.validation.min_value')}"
        var msg = new GString(str);
        msg.addBindings({
            0: arg0,
            1: arg1
        });
        return "" + msg;
    }
    function getValidationMaxValueMsg(arg0, arg1){
        var str ="${g.message(code: 'js.question.validation.max_value')}"
        var msg = new GString(str);
        msg.addBindings({
            0: arg0,
            1: arg1
        });
        return "" + msg;
    }

    <g:if test="${queryForm}">
    function toggleThickness (elem, allowVariableThickness) {
        var row = $(elem).closest('tr');
        var xbox = row.find('.xBoxSub');
        var thickness = row.find('.thicknessInput');
        var unitText = row.find('.unitAfterInput');
        var userUnitSelect = row.find('.userGivenUnit');

        if (allowVariableThickness === "true") {
            if (userUnitSelect.val() === 'm3' || userUnitSelect.val() === 'cu ft' || userUnitSelect.val() === 'cu yd') {
                $(thickness).trigger('blur');
                $(thickness).attr('readOnly', 'readOnly');
                $(thickness).addClass('visibilityHidden');
                $(xbox).addClass('visibilityHidden');
                $(unitText).addClass('visibilityHidden');

                if (!$("#formChanged").length) {
                    $("#queryForm").append('<input type=\"hidden\" id=\"formChanged\" name=\"formChanged\" value=\"true\" />');
                }

            } else if (userUnitSelect.val() === 'kg' || userUnitSelect.val() === 'lbs' || userUnitSelect.val() === 'ton') {
                $(thickness).trigger('blur');
                $(thickness).attr('readOnly', 'readOnly');
                $(thickness).addClass('visibilityHidden');
                $(xbox).addClass('visibilityHidden');
                $(unitText).addClass('visibilityHidden');

                setTimeout(function () {
                    $(thickness).removeClass('thicknessChanged');
                }, 2000);
                if (!$("#formChanged").length) {
                    $("#queryForm").append('<input type=\"hidden\" id=\"formChanged\" name=\"formChanged\" value=\"true\" />');
                }

            } else if (userUnitSelect.val() === 'm2' || userUnitSelect.val() === 'sq ft') {
                $(thickness).trigger('blur');
                $(thickness).removeAttr('readOnly');
                $(thickness).addClass('thicknessChanged');
                $(thickness).removeClass('visibilityHidden');
                $(xbox).removeClass('visibilityHidden');
                $(unitText).removeClass('visibilityHidden');

                setTimeout(function () {
                    $(thickness).removeClass('thicknessChanged');
                }, 2000);
                if (!$("#formChanged").length) {
                    $("#queryForm").append('<input type=\"hidden\" id=\"formChanged\" name=\"formChanged\" value=\"true\" />');
                }
            }
        } else {
            if (userUnitSelect.val() === 'm3' || userUnitSelect.val() === 'cu ft' || userUnitSelect.val() === 'cu yd' || userUnitSelect.val() === 'kg'  || userUnitSelect.val() === 'lbs' || userUnitSelect.val() === 'ton') {

                if (!$("#formChanged").length) {
                    $("#queryForm").append('<input type=\"hidden\" id=\"formChanged\" name=\"formChanged\" value=\"true\" />');
                }
            }
        }
    }

   function addResourceRow(entityId, quarterlyInputEnabled, monthlyInputEnabled, selectId, indicatorId, queryId, sectionId, questionId, resourceTableId, fieldName, preventDoubleEntries, showGWP) {
        var object = document.getElementById(selectId);
        var resourceId = $(object).attr("data-resourceId");
        var attr =  $(object).attr('name')
        var profileId

        if ($(object).val() && resourceId || $(object).hasClass('resourceSelect')) {
        if (resourceId && resourceId.indexOf(".") != -1) {
            profileId = resourceId.split(".")[1];
            resourceId = resourceId.split(".")[0];
        }

        if (!resourceId) {
            resourceId = $(object).val();
            resourceId = resourceId.split(" ")[0];

            if (resourceId && resourceId.indexOf(".") != -1) {
                profileId = resourceId.split(".")[1];
                resourceId = resourceId.split(".")[0];
            }
        }

        resourceTableId = "#" + resourceTableId;
        var whereToAppend = resourceTableId + 'header';
        var duplicateFound = false;

        if (preventDoubleEntries) {
            $('input[name="' + fieldName + '"]').each(function () {
                if ($(this).val() == resourceId) {
                    duplicateFound = true;
                }
            });
        }

        if (duplicateFound) {
            Swal.fire("${message(code: 'warning')}","${message(code: 'query.question.prevent_double_entries')}","warning");
        } else {
            var resourceTableHeader = resourceTableId + "header";
            $(resourceTableHeader).show();
            $.ajax({
                type: 'POST',
                data: 'quarterlyInputEnabled=' + quarterlyInputEnabled + '&entityId=' + entityId + '&monthlyInputEnabled=' + monthlyInputEnabled + '&resourceId=' + resourceId + '&profileId=' + profileId + '&indicatorId=' + indicatorId + '&queryId=' + queryId + '&sectionId=' + sectionId + '&questionId=' + questionId + '&fieldName=' + fieldName + '&showGWP=' + showGWP
                + '&newResourceOnSelect=true',
                url: '/app/addresource',
                success: function (data, textStatus) {
                    $(data.output).insertAfter(whereToAppend);
                    $('.newResourceRow').addClass("highlighted");
                    setTimeout(function(){$('.newResourceRow').removeClass('newResourceRow highlighted');}, 2000);
                  $('[rel="popover"]').popover({
                        placement: 'top',
                        template: '<div class="popover"><div class="arrow"></div><div class="popover-content"></div></div>'
                    });

                    $('.numeric').on('input propertychange', function () {
                        var start = this.selectionStart;
                        end = this.selectionEnd;
                        var val = $(this).val();
                        if (val) {
                            $(this).popover('hide');
                        }
                        if (isNaN(val)) {
                            val = val.replace(/[^0-9\.\,\-]/g, '');

                            if (val.split('.').length > 2) {
                                val = val.replace(/\.+$/, '');
                            }

                            if (val.split(',').length > 2) {
                                val = val.replace(/\,+$/, '');
                            }
                            var dotsAndCommas = ((val.match(/\./g) || []).length + (val.match(/,/g) || []).length);

                            if (dotsAndCommas > 1) {
                                if (val.indexOf('.') < val.indexOf(',')) {
                                    val = val.replace('.', '');
                                } else {
                                    val = val.replace(',', '');
                                }
                            }
                        }
                        $(this).val(val);
                        this.setSelectionRange(start, end);
                    });
                    var queryForm = $("#queryForm");

                    if (queryForm && !$("#formChanged").length) {
                        $(queryForm).append('<input type=\"hidden\" id=\"formChanged\" name=\"formChanged\" value=\"true\" />');
                        $(queryForm).trigger('change');
                    }
                    enableCalculationOnQueryForm()
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                }
            });
        }
        }
   }

    $(document).ready(function () {
        $('.input').on('keypress', function (e) {
            if (e.which == 13) {
                $('form#queryForm').trigger('submit');
                return false;
            }
        });

        var queryForm = document.getElementById("queryForm");

        $(queryForm).on('keyup change', 'input, select, textarea, button', function () {
            $('input[type=submit]').prop('disabled', false);

            if (!$("#formChanged").length) {
                $("#queryForm").append('<input type=\"hidden\" id=\"formChanged\" name=\"formChanged\" value=\"true\" />');
            }
        });

        $('.addResourceButton').on('click', function () {
            $('input[type=submit]').prop('disabled', false);

            if (!$("#formChanged").length) {
                $("#queryForm").append('<input type=\"hidden\" id=\"formChanged\" name=\"formChanged\" value=\"true\" />');
            }
        });

        $("#targetQuery").on('change', function () {
            $('input[type=submit]').prop('disabled', false);

            if (!$("#formChanged").length) {
                $("#queryForm").append('<input type=\"hidden\" id=\"formChanged\" name=\"formChanged\" value=\"true\" />');
            }
        });

        $('.numeric').on('input propertychange', function () {
            var start = this.selectionStart;
            end = this.selectionEnd;
            var val = $(this).val();
            if (val) {
                $(this).popover('hide');
            }
            if (isNaN(val)) {
                val = val.replace(/[^0-9\.\,\-]/g, '');

                if (val.split('.').length > 2) {
                    val = val.replace(/\.+$/, '');
                }

                if (val.split(',').length > 2) {
                    val = val.replace(/\,+$/, '');
                }
                var dotsAndCommas = ((val.match(/\./g) || []).length + (val.match(/,/g) || []).length);

                if (dotsAndCommas > 1) {
                    if (val.indexOf('.') < val.indexOf(',')) {
                        val = val.replace('.', '');
                    } else {
                        val = val.replace(',', '');
                    }
                }
            }
            $(this).val(val);
            this.setSelectionRange(start, end);
        });
    });

    if (typeof changeLinkToTextfield != 'function') {
        function changeLinkToTextfield(numeric, linkObject, fieldName, fieldId, addRedBorder, value, prepend, inputLabel, minAllowed, maxAllowed, inputWidth, dataInherit) {
            if (linkObject) {
                var parentTd = $(linkObject).parent();
                var width = inputWidth ? inputWidth : 50;
                removeExistingResourceRowClass(linkObject)

                if (prepend) {
                    $(linkObject).remove();

                    if (fieldId) {
                        $(parentTd).find( 'input#' + fieldId.replace(/(:|\.|\[|\]|,|=|@)/g, "\\$1") + ':hidden' ).remove();
                    } else {
                        $(parentTd).find( 'input:hidden' ).remove();
                    }

                    if (minAllowed||maxAllowed) {
                        $(parentTd).prepend('<input type="text" onblur="checkValue(this, \''+inputLabel+'\', \''+minAllowed+'\', \''+maxAllowed+'\', \''+inputWidth+'\');" onclick="numericInputCheck();" rel="popover" data-trigger="focus" data-html="true" data-content="${message(code: 'query.quantity_type.warning')}" name="' + fieldName + '" id="' + fieldId + '" value="' + (value ? value : '') + '" class="lockableDatasetQuestion input-small '+ numeric +'" style="width: '+width+'px;' + (addRedBorder ? ' border: 1px solid red;' : '') + '" />');
                    } else {
                        $(parentTd).prepend('<input type="text" onclick="numericInputCheck();" rel="popover" data-trigger="focus" data-html="true" data-content="${message(code: 'query.quantity_type.warning')}" name="' + fieldName + '" id="' + fieldId + '" value="' + (value ? value : '') + '" class="lockableDatasetQuestion input-small '+ numeric +'" style="width: 35px;' + (addRedBorder ? ' border: 1px solid red;' : '') + '" />');
                    }
                } else {
                    var unitContainer = $(parentTd).find('.unitAfterInput');
                    $(parentTd).empty();
                    if (minAllowed||maxAllowed) {
                        $(parentTd).append('<input type="text" onblur="checkValue(this, \''+inputLabel+'\', \''+minAllowed+'\', \''+maxAllowed+'\', \''+inputWidth+'\');" onclick="numericInputCheck();" rel="popover" data-trigger="focus" data-html="true" data-content="${message(code: 'query.quantity_type.warning')}" name="' + fieldName + '" id="' + fieldId + '" value="' + (value ? value : '') + '" class="lockableDatasetQuestion input-small '+ numeric +'" style="width: '+width+'px;' + (addRedBorder ? ' border: 1px solid red;' : '') + '" />');
                    } else {
                        $(parentTd).append('<input type="text" onclick="numericInputCheck();" rel="popover" data-trigger="focus" data-html="true" data-content="${message(code: 'query.quantity_type.warning')}" name="' + fieldName + '" id="' + fieldId + '" value="' + (value ? value : '') + '" class="lockableDatasetQuestion input-small '+ numeric +'" style="width: 60px;' + (addRedBorder ? ' border: 1px solid red;' : '') + '" />');
                    }

                    if ($(unitContainer).length) {
                        $(parentTd).append($(unitContainer));
                    }
                }

                var identifier = $(parentTd).attr('data-inherit');

                if (dataInherit && identifier) {
                    var childrenAdditionalQuestions = $('*[data-identifierForInheritance="' + identifier + '"]');

                    if ($(childrenAdditionalQuestions).length) {
                        $(childrenAdditionalQuestions).find('.serviceLifeButton').trigger('click');
                    }

                    $(parentTd).children().on('change', function () {
                        var elementType = this.nodeName;
                        inheritSelectValueToChildren(identifier, $(this).val(), elementType);
                    });
                }

                if (typeof addTriggerCalcEventListenerToChildElement == 'function') {
                    addTriggerCalcEventListenerToChildElement(parentTd, 'input', 'input')
                }

                $('[rel="popover"]').popover({
                    placement: 'top',
                    template: '<div class="popover"><div class="arrow"></div><div class="popover-content"></div></div>'
                });
            }
        }
    }

   if (typeof changeCostToTextfield != 'function') {
       function changeCostToTextfield(linkObject, fieldName, costQuestionId, userSetCost, name, fieldId, addRedBorder, value, uniqueConstructionIdentifier) {
           if (linkObject) {
               var row = $(linkObject).closest('tr');
               var userGivenUnit = row.find('.costStructureUnit').text();
               var totalCostCurrency = row.find('.totalCostCurrency').text();
               var hiddenCostField = row.find('.' + costQuestionId + 'Hidden');
               var constituent = hiddenCostField.attr("data-constituent");
               var constituentField = '';
               var constituentIdentifier = uniqueConstructionIdentifier;

               if (constituent) {
                   constituentField = ' data-constituent="'+constituent+'"'
               }

               if (!userGivenUnit) {
                  userGivenUnit = row.find('.userGivenUnit').val();

                  if (!userGivenUnit) {
                      userGivenUnit = row.find('.userGivenUnitContainer').text();
                  }
               }

               var inputVal;

               if (constituentIdentifier) {
                   inputVal = $(linkObject).text();
               } else {
                   inputVal = $(hiddenCostField).val();
               }

               inputVal = inputVal ? inputVal.replace(/\s/g,'') : ''
               if(inputVal == "null") inputVal = ""

               var parentTd = $(linkObject).parent();
               $(parentTd).empty();
               if ('costPerUnit' === costQuestionId) {
                   $(parentTd).append('<input type="text" name="' + fieldName + '" id="' + fieldId + '"'+ constituentField + '" value="' + (inputVal) + '" class="input-small userGivenCustomCostTotalMultiplier lockableDatasetQuestion" style="width: 50px;' + (addRedBorder ? ' border: 1px solid red;' : '') + '" />');
                   $(parentTd).append('<span class="add-on costStructureUnit">'+ userGivenUnit +'</span>');
               } else if ('totalCost' === costQuestionId) {
                   $(parentTd).append('<input type="text" name="' + fieldName + '" id="' + fieldId + '"'+ constituentField + '" value="' + (inputVal) + '" class="input-small totalCostInput lockableDatasetQuestion" style="width: 50px;' + (addRedBorder ? ' border: 1px solid red;' : '') + '" />');
                   $(parentTd).append('<span class="add-on totalCostCurrency">'+ totalCostCurrency +'</span>');
               }
               $(parentTd).append('<input type="hidden" id="userSetCost' + costQuestionId + '" value="true" class="userSetCost${costQuestionId}" name="' + name + '">');

               if (constituentIdentifier) {
                   $('.constituenCostLink' + constituentIdentifier).trigger('click');
                   $("input[data-constituent='costPerUnit."+constituentIdentifier+"']").val("0").trigger('input');
                   $("input[data-constituent='totalCost."+constituentIdentifier+"']").val("0").trigger('input');
               }
           }
       }
   }
    </g:if>

    $(document).ready(function () {
        $('.dropdown-toggle').dropdown();
        <g:if test="${'en-US'.equals(currentUser?.localeString)}">
        $(".datepicker").datepicker({dateFormat: 'dd.mm.yy', firstDay: 7});
        </g:if>
        <g:else>
        $(".datepicker").datepicker({dateFormat: 'dd.mm.yy', firstDay: 1});
        </g:else>
        $('.combobox').combobox();

        <g:if test="${showPortfolio}">
        $('.notFirst').hide();
        </g:if>
    });


    if (typeof hideTr != 'function') {
        function hideTr(trClass, buttonId) {
            var button = '#' + buttonId;
            var value = "<g:message code="results.show_empty" />";
            $(button).attr("value", value);
            $(button).attr("onclick", "showTr('" + trClass + "', '" + buttonId + "');");
            var classToHide = '.' + trClass;
            $(classToHide).hide();
        }
    }

    if (typeof showTr != 'function') {
        function showTr(trClass, buttonId) {
            var button = '#' + buttonId;
            var value = "<g:message code="results.hide_empty" />";
            $(button).attr("value", value);
            $(button).attr("onclick", "hideTr('" + trClass + "', '" + buttonId + "');");
            var classToShow = '.' + trClass;
            $(classToShow).show();
        }
    }

    if (typeof hidePublic != 'function') {
        function hidePublic(trClass, buttonId) {
            var button = '#' + buttonId;
            var value = "<g:message code="main.show_public" />";
            $(button).html(value);
            $(button).attr("onclick", "showPublic('" + trClass + "', '" + buttonId + "');");
            var classToHide = '.' + trClass;
            $(classToHide).hide();
        }
    }

    if (typeof showPublic != 'function') {
        function showPublic(trClass, buttonId) {
            var button = '#' + buttonId;
            var value = "<g:message code="main.hide_public" />";
            $(button).html(value);
            $(button).attr("onclick", "hidePublic('" + trClass + "', '" + buttonId + "');");
            var classToShow = '.' + trClass;
            $(classToShow).show();
        }
    }

    if (typeof hideSources != 'function') {
        function hideSources(divClass, buttonId) {
            var button = '#' + buttonId;
            var value = "<g:message code="results.show_sources" />";
            $(button).attr("value", value);
            $(button).attr("onclick", "showSources('" + divClass + "', '" + buttonId + "');");
            var divToHide = '.' + divClass;
            $(divToHide).hide();
        }
    }

    if (typeof showSources != 'function') {
        function showSources(divClass, buttonId) {
            var button = '#' + buttonId;
            var value = "<g:message code="results.hide_sources" />";
            $(button).attr("value", value);
            $(button).attr("onclick", "hideSources('" + divClass + "', '" + buttonId + "');");
            var divToShow = '.' + divClass;
            $(divToShow).show();
            $('html, body').animate({
                scrollTop: $(divToShow).offset().top
            }, 2000);
        }
    }

    //**********************************************************************
    //FRAME STATUS
    //**********************************************************************
    <g:if test="${userEnableHelpWorkflow || showUpdateBar}">
        var frameStatus = ${frameStatus ? frameStatus as grails.converters.JSON : 'null'};

        setDefaultTabFrame();

        //**********************************************************************
        //WORKFLOW
        //**********************************************************************

        var jsonWorkFlow = ${workFlowForEntity ? workFlowForEntity as grails.converters.JSON : 'null'};

        <g:if test="${workFlowForEntity}">
            updateWorkFlowContent('${message(code: "save")}','${message(code: "clear")}', '${message(code: "reset_workflow")}');
        </g:if>
    </g:if>
    //**********************************************************************

    function openDataRequest() {
        if (typeof zE !== "undefined") {
            zE('webWidget', 'updateSettings', {
                webWidget: {
                    chat: {
                        suppress: true
                    },
                    helpCenter: {
                        suppress: true
                    },
                    contactOptions: {
                        enabled: true
                    }
                }
            });
            zE('webWidget', 'open');
        }
    }

    <g:if test="${workFlowForEntity}">
        var isMobile = (/Mobi|Android/i.test(navigator.userAgent))
        var allowWorkflowDisableMessage = ${allowWorkflowDisableMessage ? true : false}
        var workflow
        if(!isMobile){
            $(window).on('load', function() {
                setTimeout(function(){
                    if(jsonWorkFlow){
                        var workflowId = jsonWorkFlow.defaultWorkFlowId
                        var disableOnboarding = "${user?.disableOnboarding}"
                        workflow = jsonWorkFlow.workFlowList.find(it => it.workFlowId == workflowId)
                        var firstTimeWorkflow = workflow.stepList.filter(it => it.checked == true).length == 0 ? true : false
                        if(workflow && workflow.workFlowType == "onBoarding" && disableOnboarding != "true"){
                            if(workflow && firstTimeWorkflow && allowWorkflowDisableMessage){
                                $("#workflowStartPop").removeClass("hidden").modal()
                            } else {
                                initializeOnboardingWorkflow(workflow, "${message(code: "exit_workflow")}", "${message(code: "next")}")
                            }
                        } else if(workflow && workflow.workFlowType == "onBoarding"  && disableOnboarding == "true" ){
                            toggleDisableMessagePopover("resetch WorkflowBtn","${message(code:'enable_onboarding.instruction')}")
                        }
                    }
                },100)
            })
        }
    </g:if>

    let userInactiveTime = ${session?.getMaxInactiveInterval()}; //renders out seconds
    let warningTimeout = userInactiveTime > 3600 ? 7200000 : 1800000;
    let timeoutNow;
    let userId = "${currentUser?.id}";
    let warningTimerID,timeoutTimerID;

    function startTimer() {
        warningTimerID = window.setTimeout(warningInactive, warningTimeout);
    }

    function warningInactive() {
        window.clearTimeout(warningTimerID);
        fetch('/app/sessionInformation?userId=' + userId, {
            method: 'GET',
            credentials: 'omit'
        }).then(response =>
            response.json()
        ).then(data => {
            if (data.lastRequest) {
                setFlashForAjaxCall(data)
                if (!userInactiveTime) {
                    userInactiveTime = 3600; // Failover to 1h in seconds
                }
                let lastRequest = new Date().setTime(data.lastRequest);
                let timeDifference = new Date().setTime((lastRequest + (userInactiveTime*1000)) - new Date().getTime());
                let timerInterval;

                timeoutNow = timeDifference;
                if (timeoutNow <= 0) {
                    timeoutNow = 30000 //30 seconds
                }
                Swal.fire({
                    title: '${g.message(code:"inactivity.warning_title")}',
                    html: '${g.message(code:"inactivity.warning_timer")}',
                    allowOutsideClick: false,
                    allowEscapeKey: false,
                    allowEnterKey: false,
                    confirmButtonText: "${message(code: 'inactivity.continue_using')}",
                    confirmButtonColor: '#78b200',
                    timer: timeoutNow,
                    timerProgressBar: true,
                    onBeforeOpen: () => {
                        timerInterval = setInterval(() => {
                            swal.getContent().querySelector('strong')
                                .textContent = millisToMinutesAndSeconds(swal.getTimerLeft())
                        }, 100)
                    },
                    onDestroy: () => {
                        clearInterval(timerInterval)
                    }
                }).then((result) => {
                    clearInterval(timerInterval)
                    if (result.dismiss === swal.DismissReason.timer) {
                        IdleTimeout()
                    } else if (result.isConfirmed) {
                        console.log("User activity noticed, sending to server")
                        var url = '/app/maintainActivity'
                        $.ajax({
                            type: "POST",
                            data: 'userId=' + userId,
                            url: url,
                            success: function(data) {
                                if(data.activitySuccess) {
                                    console.log(data.activitySuccess)
                                }
                            },
                            error: function(error) {
                                console.log(error)
                            }

                        })
                    }
                })
            }
        });
    }

    function resetTimer() {
        window.clearTimeout(warningTimerID);
        startTimer();
    }

    // Logout the user.
    function IdleTimeout() {
        console.log("Session inactivity timer has expired, checking server for last request");

        fetch('/app/sessionInformation?userId=' + userId, {
            method: 'GET',
            credentials: 'omit'
        }).then(response =>
            response.json()
        ).then(data => {
            if (data.expired === "true") {
                setFlashForAjaxCall(data)
                console.log("Logged out, server confirmed session inactivity");
                window.location.href = "${g.createLink(controller: 'logout', action: 'index')}";
            } else {
                console.log("Session is still active, resetting timer")
                resetTimer()
            }
        });
    }

    function setupTimers () {
        document.addEventListener("mousedown", resetTimer, false);
        document.addEventListener("keypress", resetTimer, false);
        document.addEventListener("onscroll", resetTimer, false);
        startTimer();
    }

    $(document).ready(function(){
        setupTimers();
        // Align modal when it's displayed
        $('#startNewProjectModal').on('show.bs.modal', function(){
            alignModal('startNewProjectModal')
        })
        // Align modal when user resize the window
        $(window).on("resize", function(){
            alignModal('startNewProjectModal')
        })
    });
</script>

</body>
</html>