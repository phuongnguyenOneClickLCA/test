<%
    def userService = grailsApplication.mainContext.getBean("userService")
%>
<!doctype html>
<%@ page expressionCodec="html" %>
<%-- MAIN SCREEN --%>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="format-detection" content="telephone=no"/>
    <g:if test="${showPortfolio}">
        <asset:stylesheet src="portfolio.css"/>
        <asset:javascript src="portfolio_chart.js"/>
    </g:if>
    <g:set var="optimiResourceService" bean="optimiResourceService"/>
</head>
<body>
<div id="mainContent" class="${showUpdateBar ? 'homePageFixedWidth' : ''}">
<g:set var="pageRenderTime" value="${System.currentTimeMillis()}" />
    <div id="entities"><%--
    --%><opt:internetExplorerWarning /><%--
    <g:if test="${showPortfolio}">
        ${portfolioHtml}
    </g:if><%--
--%><table class="table table-entities" id="resulttable">
    <thead>
    <tr role="row">
        <th role="columnheader" tabindex="0" rowspan="1" colspan="1" width="40">&nbsp;</th>
        <th role="columnheader" tabindex="0" aria-controls="building-search" rowspan="1" colspan="1"><g:message code="main.list.entity" /></th>
        <th role="columnheader" tabindex="0" aria-controls="building-search" rowspan="1" colspan="1" class="text-center"><g:message code="main.list.certification"/></th>
        <th role="columnheader" tabindex="0" aria-controls="building-search" rowspan="1" colspan="1"><g:message code="main.list.carbonPerf"/> <i class="icon-question-sign carbonPerfHelp"></i></th>
        <th role="columnheader" tabindex="0" aria-controls="building-search" rowspan="1" colspan="1"><g:message code="main.list.content" /></th>
       </tr>
    </thead>
    <tbody id="resultBody">
   <g:set  var="certificationList" value="${entityWithCertificationList}"/>
    <g:set var="benchmarkScores" value="${entityWithCarbonBenchmarkList}"/>
    <opt:renderPopUpTrial stage="noProjectDemoHomePage" entities="${entities}"/>
   <g:each in="${entities}" var="entity" status="index"><%--
    --%><tr class="notPublic">
        <td><%--
        --%><span class="thumb" id="thumb${index}"><%--
            --%><g:if test="${entity.hasImage}"><%--
-               --%>${raw(thumbImages?.get(entity.id.toString()))}<%--
-           --%></g:if><%--
-           --%><g:else><%--
                --%><g:if test="${'infrastructure'.equalsIgnoreCase(entity.entityClass)}">
                      <asset:image src="img/infraroad.png"/>
                    </g:if><%--
-               --%><g:if test="${'ekokompassi'.equals(entity.entityClass)}"><%--
-                   --%><i class="entityclass-small-ekokompassi"></i><%--
-               --%></g:if><%--
-               --%><g:else><%--
-                    --%><i class="entitytype-small <opt:entityIcon entity="${entity}" />-small"></i><%--
-               --%></g:else><%--
-            --%></g:else><%--
        --%></span>
        </td>
        <td class="maininfo">
            <opt:link class="entityLink" onclick="clickAndDisable('entityLink')" controller="entity" action="show" params="[entityId: entity.id, name: entity?.name, showToolsNote: true]">
                <g:if test="${entity?.archived}">
                    <i style="color: black" class="fas fa-lock"></i>
                </g:if>
                ${entity?.name}
            </opt:link>
            <opt:entityDisplayName basicQuery="${basicQuery}" entity="${entity}" />
        </td>
        <td class="text-center center-block"><g:if test="${certificationList?.keySet()?.contains(entity.id)}"><img width="60px" src='${certificationList?.get(entity.id)?.get("img")}' alt='${certificationList?.get(entity.id)?.get("name")}' onerror="this.style.display='none'"/> </g:if></td>
        <td class="text-center"><g:if test="${benchmarkScores?.keySet()?.contains(entity.id)}"><span class='${benchmarkScores?.get(entity.id)?.get("index")}index'>${benchmarkScores?.get(entity.id)?.get("index")}</span> <br /> ${benchmarkScores?.get(entity.id)?.get("score")} kg CO<sub>2</sub>e/m<sup>2</sup> </g:if></td>
        <td><%--
            --%><g:set var="designCount" value="${entity.getChildCount('design')}" /><%--
            --%><g:if test="${designCount}"><%--
                --%><g:message code="main.list.desigCount" args="[designCount]"/> <br><%--
            --%></g:if><%--
            --%><g:set var="operatingCount" value="${entity.getChildCount('operatingPeriod')}" /><%--
            --%><g:if test="${operatingCount}"><%--
                --%><g:message code="main.list.operatingCount" args="[operatingCount]"/><%--
            --%></g:if><%--
    --%></td>
        </tr><%--
--%></g:each><%--

--%><g:each in="${publicEntities}" var="entity" status="index"><%--
    --%>
        <tr class="public">
            <td><%--
        --%><span class="thumb" id="thumb${index}"><%--
            --%><g:if test="${entity.hasImage}"><%--
-               --%>${raw(thumbImages?.get(entity.id.toString()))}<%--
-           --%></g:if><%--
-           --%><g:else><%--
            --%><g:if test="${'infrastructure'.equalsIgnoreCase(entity.entityClass)}">
                <asset:image src="img/infraroad.png"/>
                   </g:if><%--
-               --%><g:if test="${'ekokompassi'.equals(entity.entityClass)}"><%--
-                   --%><i class="entityclass-small-ekokompassi"></i><%--
-               --%></g:if><%--
-               --%><g:else><%--
-                    --%><i class="entitytype-small <opt:entityIcon entity="${entity}" />-small"></i><%--
-               --%></g:else><%--
-            --%></g:else><%--
        --%></span>
        </td>
        <td class="maininfo">
            <opt:link class="entityLink" onclick="clickAndDisable('entityLink')" controller="entity" action="show" params="[entityId: entity.id, name: entity?.name]">
                <g:if test="${entity?.archived}">
                    <i style="color: black" class="fas fa-lock"></i>
                </g:if>
                ${entity?.name}
            </opt:link>
            <opt:entityDisplayName basicQuery="${basicQuery}" entity="${entity}" />
        </td>
            <td class="text-center center-block"><g:if test="${certificationList?.keySet()?.contains(entity.id)}"><img width="60px" src='${certificationList?.get(entity.id)?.get("img")}' alt='${certificationList?.get(entity.id)?.get("name")}' onerror="this.style.display='none'"/> </g:if></td>
            <td class="text-center"><g:if test="${benchmarkScores?.keySet()?.contains(entity.id)}"><span class='${benchmarkScores?.get(entity.id)?.get("index")}index'>${benchmarkScores?.get(entity.id)?.get("index")}</span> <br /> ${benchmarkScores?.get(entity.id)?.get("score")} kg CO<sub>2</sub>e/m<sup>2</sup> </g:if></td>

        <td><%--
            --%><g:set var="designCount" value="${entity.getChildCount('design')}" /><%--
            --%><g:if test="${designCount}"><%--
                --%><g:message code="main.list.desigCount" args="[designCount]" /><%--
            --%></g:if><%--
            --%><g:set var="operatingCount" value="${entity.getChildCount('operatingPeriod')}" /><%--
            --%><g:if test="${operatingCount}"><%--
                --%><g:message code="main.list.operatingCount" args="[operatingCount]" /><%--
            --%></g:if><%--
    --%></td>
    </tr><%--
--%></g:each><%--
--%></tbody>
</table><%--
--%><g:if test="${entities && !allFound}"><%--
    --%><div class="loading-spinner"><div class="image">
    <svg class="loadingSVG" version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg"  x="0px" y="0px"
         width="270px" height="270px" viewBox="0 0 32 32" style="enable-background:new 0 0 32 32;" xml:space="preserve">
        <g>
            <path class="arrowSpinningSvg mm-o-syncing-queue" d="M24.5,10.4L22.2,11l5,5L29,9.2l-2.4,0.7C24.4,6.1,20.4,3.8,16,3.8c-5.4,0-10.1,3.4-11.7,8.6
	c-0.2,0.5,0.1,1.1,0.7,1.3c0.1,0,0.2,0,0.3,0c0.4,0,0.8-0.3,1-0.7c1.3-4.3,5.2-7.2,9.7-7.2C19.5,5.8,22.7,7.5,24.5,10.4z"/>
        </g>
    </svg>
    <p class="working"><g:message code="loading.working"/>.</p>
</div></div><%--
--%></g:if><%--
    --%><div class="loading-spinner" id="byNameSpinner" style="display: none;">        <div class="image">
    <svg class="loadingSVG" version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg"  x="0px" y="0px"
         width="270px" height="270px" viewBox="0 0 32 32" style="enable-background:new 0 0 32 32;" xml:space="preserve">
        <g>
            <path class="arrowSpinningSvg mm-o-syncing-queue" d="M24.5,10.4L22.2,11l5,5L29,9.2l-2.4,0.7C24.4,6.1,20.4,3.8,16,3.8c-5.4,0-10.1,3.4-11.7,8.6
	c-0.2,0.5,0.1,1.1,0.7,1.3c0.1,0,0.2,0,0.3,0c0.4,0,0.8-0.3,1-0.7c1.3-4.3,5.2-7.2,9.7-7.2C19.5,5.8,22.7,7.5,24.5,10.4z"/>
        </g>
    </svg>
    <p class="working"><g:message code="loading.working"/>.</p>
</div></div><%--
--%></div>
<g:if test="${(session?.showLoginPrompt || session?.testLoginPrompt) && !hideEcommerceFeatures}">
<div class="modal hide modal large startUpModalDiv" id="loginPopUpModal">
    <div class="modal-header text-center">
        <h2 class="headerForLoginModal"><g:message code="loginPrompt.confirm_settings"/></h2>
    </div>

<div class="modal-body startUpModal" id="startUpModal">
    <div class="text-left">
        <div class="control-group">
            <g:message code="detected_region"/>  
            <span id="userLangSpan">
                <asset:image src="/isoflags/${user?.language?.toLowerCase() && user.language.equalsIgnoreCase("en") ? "gb" : user?.language?.toLowerCase()}.png"
                             style="max-width:35px;" id="userLangImg"/>
                ${optimiResourceService.getLocalizedName(languages.find({it.resourceId?.equalsIgnoreCase(user?.language)}))}
            </span>
        <div class="btn-group inliner">
        <a href="javascript:" class="dropdown-toggle"  data-toggle="dropdown">(<g:message code="change_language"/>)</a>
        <ul class="dropdown-menu modalDropDown">
            <g:each in="${languages}" var="lang">
            <li><opt:link controller="user" action="changeLanguage" onclick="appendLoader('startUpModal');" params="[lang:lang.resourceId, language:optimiResourceService.getLocalizedName(lang), userId:user?.id, skipEmailActivateModal:true]">${optimiResourceService.getLocalizedName(lang)}</opt:link></li>
            </g:each>
        </ul>
        </div>
        </div>
        <div class="controls">
            <g:message code="user.unitSystem" />
                <g:select name="unitSystem" class="loginFormSelect" value="${user?.unitSystem}" from="${unitSystems}"  onchange="changeUserUniSystem(this.value,  '${user.id}');" optionKey="${{it.value}}" optionValue="${{message(code: 'unit_system.' + it.value)}}" />
             </div>
             <div class="controls">
                <g:message code="user.localeString" />
                <g:select name="localeString" class="loginFormSelect" value="${user?.localeString}" from="${userLocales}" onchange="changeUserLocale(this.value,  '${user.id}');" optionKey="${{it.value.toLanguageTag()}}" optionValue="${{it.key}}" />
            </div>
        </div>
    </div>

    <div class="modal-footer">
        <g:if test="${userService.getAllowedEntities(user)}">
            <a href="javascript:" id="nextButtonForModal" class="smoothButton nextButton" onclick="killLoginPrompt();$('#loginPopUpModal').modal('hide'); "><g:message code="confirm"/></a>
        </g:if><g:else>
        <a href="javascript:" id="nextButtonForModal" onclick="nextModal(this,'#startUpModal', '#loginPopUpModalEmail', true);" class="smoothButton text-center nextButton" class="smoothButton nextButton"><g:message code="next"/></a>
    </g:else>
        <asset:image src="OCL_logo.png" class="pull-right"/>
    </div>
</div>
    <div class="modal hide modal large startUpModalDiv" id="loginPopUpModalEmail">
        <div class="modal-header text-center">
            <h2 class="headerForLoginModal"><g:message code="confirm_email_address" /><p class="bold" style="font-size: 12px !important;"><g:message code="emaiL_was_sent_to"/> ${user?.username}</p></h2>
        </div>
        <div class="emailConfirmation modal-body startUpModal" id="startUpModal0">
            <div class="container-fluid">
                <p><g:message code="email_confirmation_help"/></p>
            </div>
            <div class="container-fluid">
                <div class="controls">
                    <g:message code="misspelled_email"/> <a href="javascript:" onclick="showEmailForm(this);"><g:message code="update_it"/></a>
                    <input id="newUserUsername" type="text" name="username" style="display: none;" value="${user?.username}">
                    <a id="newUserUsernameSave" href="javascript:" onclick="changeUsername(this, '${user?.id}')" style="margin-bottom: 9px; display: none;" class="btn btn-primary" >${message(code: 'save')}</a>
                </div>
                <div class="controls">
                    <g:message code="email_not_received" />
                    <opt:link controller="index" action="resendActivationInSoftware"><g:message code="resend_confirmation" /></opt:link>
                </div>
            </div>
        </div>
        <div class="modal-footer">
            <div class="inliner">
                <a class="smoothButton text-center nextButton" data-dismiss="modal" href="javascript:"><g:message code="next"/></a>
                <asset:image class="pull-right" src="OCL_logo.png"/>
            </div>
        </div>
    </div>
</g:if>
<script type="text/javascript">
    $(function () {
        var popOverSetting = {
            placement: 'right',
            container: 'body',
            html: true,
            template: '<div class="popover popover-fade" style=" display: block; max-width: 180px; z-index: 99999999999999999;"><div class="arrow"></div><div class="popover-content"></div></div>'
        };
        $(".helpStart[data-toggle='popover']").popover(popOverSetting);

        $('.carbonPerfHelp').popover({
            content:"${message(code:'carbon_perf_help')}",
            placement:'bottom',
            template: '<div class="popover ribaHelpWrapper"><div class="arrow"></div><div class="popover-content ribaHelp"></div></div>',
            trigger:'hover',
            html:true,
            container:'body'
        });

        <g:if test="${publicEntities}">
        $('#resultBody tr.notPublic:last').after("<tr role=\"row\" style=\"background-color:#F5F5F5; \"><th><td class=\"bold\"><g:message code="main.list.public_entitities"/></td><td></td><td></td><td class=\"\"><a href=\"javascript:;\" id=\"showHidePublic\" onclick=\"hidePublic('public', 'showHidePublic');\"><g:message code="main.hide_public" /></a></td></th><th colspan=\"3\"></th></tr>");
        </g:if>
       <g:if test="${(session?.showLoginPrompt || session?.testLoginPrompt) && !hideEcommerceFeatures}">

        $('#loginPopUpModal').modal({
            backdrop: 'static',
            keyboard: false
        });
        $('.loginFormSelect').select2({
            minimumResultsForSearch: Infinity
        });

        $('.startUpUl').children().each(function () {
            if ($(this).data('entityclass')==="building") {
                $(this).addClass('highLightForLi')
            }
        });
        </g:if>
        <g:elseif test="${userForFloatingLicense && showFloatingLicense}">
            <g:set var="floatingLicenses" value="${userService.getUsableValidFloatingLicenses(userForFloatingLicense)}" />
            <g:if test="${floatingLicenses}">
                openFloatingLicenseModal('${floatingLicenses.first().id.toString()}');
            </g:if>
        </g:elseif>
    });
    function showEmailForm(elem) {
        $(elem).hide();
        $("#newUserUsername").fadeIn();
        $("#newUserUsernameSave").fadeIn();
    }
    $('html').on('keypress', function(e) {
        if(e.keyCode == 13) {
            return false;
        }
    });
    $(document).ready(function() {
        logNewUserData();

        <g:if test="${entities && !allFound}">
        $.ajax({type:'POST', data:'superuserEnabled=${superuserEnabled ? 'true' : 'false'}', url:'/app/sec/main/entities',
            success:function(data,textStatus){
                $(".loading-spinner").addClass("hidden");
                $('#resultBody').append(data.output);
            },
            error:function(XMLHttpRequest,textStatus,errorThrown){
            }
        });
        </g:if>
        <g:if test="${showPortfolio}">
        $(document.body).on('change', '.indicatorSelect', function () {
            $('.benchmarkBody').hide();
            var chosenIndicatorId = $(this).val();

            if (chosenIndicatorId) {
                var indicatorSelect = "#indicatorSelect" + chosenIndicatorId;
                $(indicatorSelect).val(chosenIndicatorId);
                chosenIndicatorId = '#' + chosenIndicatorId;
                $(chosenIndicatorId).show();
            }
        });

        $(document.body).on('change', '.unitSelect', function () {
            var indicatorId = $(this).attr("data-indicatorId");
            var contentId = '#' + indicatorId + 'content';
            var unit = encodeURIComponent($(this).val());
            var denominatorType = $(this).find('option:selected').attr("data-denominator");
            var denominatorId = $(this).find('option:selected').attr("data-denominatorId");


            $.ajax({type: 'POST',
                    data: 'indicatorId=' + indicatorId + '&unit=' + unit + '&denominatorType=' + denominatorType + '&denominatorId=' + denominatorId,
                    url: '/app/sec/main/renderIndicatorDenominator',
                success: function (data, textStatus) {
                    if (data.output) {
                        $(contentId).empty();
                        $(contentId).append(data.output);
                        var theTable = '#table' + indicatorId;
                        $(theTable).hide();
                        $('.dropdown-toggle').dropdown();
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                }
            });
        });
        </g:if>
    });

    <sec:ifAnyGranted roles="ROLE_SUPER_USER">
        $(document).ready(function() {
            $('#searchByName').on('click', function (event) {
                if (!$(this).attr("disabled")) {
                    $(this).attr("disabled", "disabled");
                    var val = $('#searchField').val();
                    var element = document.getElementById('byNameSpinner');

                    if (val && val.length > 2) {
                        $("#resultBody").find("tr").remove();
                        showHideDiv('byNameSpinner');
                        element.style.float = '';

                        $.ajax({type: 'POST', data: 'name=' + val.escapeHTML(), url: '/app/sec/main/entitiesByName',
                            success: function (data, textStatus) {
                                showHideDiv('byNameSpinner');
                                $('#searchByName').attr('disabled', false);

                                element.style.float = '';

                                if (data.output) {
                                    $('#resultBody').append(data.output);
                                }
                            },
                            error: function (XMLHttpRequest, textStatus, errorThrown) {
                                showHideDiv('byNameSpinner');
                                $('#searchByName').attr('disabled', false);
                                var element = document.getElementById('byNameSpinner');
                                element.style.float = '';
                            }
                        });
                    } else {
                        $('#searchByName').attr('disabled', false);
                        $('#searchField').val('Give at least 3 chars!');
                    }
                }
            });

        });
    </sec:ifAnyGranted>
</script>
<g:if test="${!user?.disableZohoChat}">
    <!-- Start of oneclicklca Zendesk Widget script -->
    <script id="ze-snippet" src="https://static.zdassets.com/ekr/snippet.js?key=788c0e91-3199-4f3d-8472-17fa41abb688"> </script>
    <script>
        zE(function() {
            zE.identify({
                name: '${user?.name}',
                email: '${user?.username}'
            });
        });
        window.zESettings = {
            webWidget: {
                <g:if test="${!chatSupportLicensed}">
                chat: {
                    suppress: true
                },
                </g:if>
                <g:if test="${!userService.getContactSupportAvailable(user)}">
                contactForm: {
                    suppress: true
                },
                </g:if>
                contactOptions: {
                    enabled: true
                }
            }
        };
    </script>
    <!-- End of oneclicklca Zendesk Widget script -->
</g:if>
<g:set var="pageRenderTime" value="${(System.currentTimeMillis() - pageRenderTime) / 1000}" />
<script type="text/javascript">
    // THE FOLLOWING NEEDS TO ALWAYS BE LAST IN PAGE RENDER:
    var domReady;
    var windowReady;

    $(document).ready(function() {
        domReady = (Date.now() - timerStart) / 1000;
    });
    $(window).on('load', function() {
        windowReady = ((Date.now() - timerStart) / 1000) - domReady;
        renderPageRenderTimes("${showRenderingTimes ? 'true' : 'false'}", "${secondsTaken}", "${pageRenderTime}", domReady, windowReady)
    });
    // END
</script>
</div>
</body>
</html>
