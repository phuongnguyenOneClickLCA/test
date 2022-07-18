<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<%-- MAIN SCREEN --%>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="format-detection" content="telephone=no"/>
    <asset:javascript src="hideAndRestoreFunctions.js"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <div class="pull-right hide-on-print">
            <opt:link controller="main" removeEntityId="true" class="btn"><i class="icon-chevron-left"></i> <g:message code="back" /></opt:link>
            <g:if test="${entity?.modifiable}">
                <sec:ifAnyGranted roles="ROLE_SUPER_USER">
                    <opt:link action="showjson" class="btn" id="${entity?.id}"><g:message code="entity.as_json" /></opt:link>
                </sec:ifAnyGranted>
                <g:if test="${compoundReportLicensed}">
                    <g:if test="${mainLevelButtons}">
                        <g:each in="${mainLevelButtons}" var="mainLevelButton">
                            <opt:link controller="entity" action="compoundReport" params="[entityId: entity?.id]" class="btn btn-primary">${mainLevelButton}</opt:link>
                        </g:each>
                    </g:if>
                    <g:else>
                        <opt:link controller="entity" action="compoundReport" params="[entityId: entity?.id]" class="btn btn-primary"><g:message code="entity.property_passport" /></opt:link>
                    </g:else>
                </g:if>
                <g:if test="${'portfolio' != entity?.entityClass && entity?.modifiable}">
                    <opt:link controller="entity" action="form" params="[entityId: entity?.id]" class="btn btn-primary"><g:message code="modify" /></opt:link>
                </g:if>
                <g:if test="${entity?.manageable}">
                    <g:if test="${'portfolio' == entity?.entityClass}">
                        <opt:link controller="entity" action="form" params="[entityId: entity?.id]" class="btn btn-primary"><g:message code="modify" /></opt:link>
                    </g:if>
                    <opt:link controller="entity" action="users" id="${entity?.id}" class="btn btn-primary"><i class="icon-user icon-white"></i> <g:message code="entity.users" /></opt:link>
                    <g:if test="${'portfolio' == entity?.entityClass}">
                        <opt:link controller="portfolio" action="form" id="${portfolio?.id}" class="btn btn-primary"><g:message code="entity.portfolio.modify" /></opt:link>
                    </g:if>
                </g:if>
            </g:if>
            <button class="btn btn-primary" onClick="window.print();"><i class="icon-print icon-white"></i> <g:message code="print" /></button>
            <g:set var="buttonsGuide" value="${opt.channelMessage(code: 'entity.buttons_guide')}" />
            <g:if test="${buttonsGuide}">
                <asset:image src="img/infosign.png" rel="popover" data-content="${buttonsGuide}" />
            </g:if>
        </div>
        <h1><i class="layout-icon-buildings"></i> ${entity?.name}</h1>
    </div>
</div>

<div class="container section">
    <div class="${entity.smallImage ? 'entity-info-image-small' : 'entity-info-image'} text-center"><%--
        --%><g:if test="${entity?.hasImage}"><%--
            --%><opt:showImage entity="${entity}"/><%--
        --%></g:if><%--
        --%><g:else><%--
            --%><g:if test="${'ekokompassi'.equals(entity.entityClass)}"><%--
                --%><i class="entityclass-ekokompassi"></i><%--
            --%></g:if><%--
            --%><g:else><%--
                --%><i class="entitytype${entity.smallImage ? '-smaller' : ''} <opt:entityIcon entity="${entity}" />${entity.smallImage ? '-smaller' : ''}"></i><%--
            --%></g:else><%--
        --%></g:else><br />
        <strong><opt:link controller="entity" action="form" params="[entityId: entity?.id]"><g:message code="entity.show.change_image" /></opt:link></strong>
    </div>

    <div class="task-section" style="float: left; width: 500px;">
        <table>
            <tbody>
            <g:if test="${licensesToShow || noValidLicenses}">
                <tr>
                    <th><g:message code="entity.show.licenses" /></th>
                    <td class="editable">
                        <g:if test="${licensesToShow}">
                            <g:set var="licenseAmount" value="${licensesToShow.size()}" />
                            <g:each in="${licensesToShow}" var="license" status="index">
                                <g:if test="${index < (licenseAmount - 1)}">
                                    ${license.name} <g:message code="license.type.${license.type}" />,
                                </g:if>
                                <g:else>
                                    ${license.name} <g:message code="license.type.${license.type}" />
                                </g:else>
                            </g:each>
                        </g:if>
                        <g:elseif test="${expiredLicenses}">
                            <g:set var="licenseAmount" value="${expiredLicenses.size()}" />
                            <g:each in="${expiredLicenses}" var="license" status="index">
                                <g:if test="${index < (licenseAmount - 1)}">
                                    ${license.name} <g:message code="license.type.${license.type}" /> <g:message code="expired.upperCase" />,
                                </g:if>
                                <g:else>
                                    ${license.name} <g:message code="license.type.${license.type}" /> <g:message code="expired.upperCase" />
                                </g:else>
                            </g:each>
                        </g:elseif>
                    </td>
                </tr>
            </g:if>
            <opt:entitySummaryData entity="${entity}" />
            <g:set var="users" value="${entity?.users}" />
            <g:if test="${users}">
                <tr>
                    <th><g:message code="entity.users" /></th>
                    <td${manageable ? ' class=\"editable\"' :''}>
                        <g:if test="${manageable}">
                            <opt:link controller="entity" action="users" id="${entity?.id}" class="black">
                                <g:join in="${users.collect{it.name}}" delimiter=", " />
                            </opt:link>
                            <span class="edit-hover"><opt:link controller="entity" action="users" id="${entity?.id}" class="black"><i class="fas fa-pencil-alt grayLink"></i></opt:link></span>
                        </g:if>
                        <g:else>
                            <g:join in="${users.collect{it.name}}" delimiter=", " />
                        </g:else>
                    </td>
                </tr>
            </g:if>
            </tbody>
        </table>
    </div>

    <g:if test="${entity.entityClassResource?.showTasks && !(trialOrQuoteDesignIndicators || trialOrQuoteOperatingIndicators)}">
        <div style="float: right;">
            <g:render template="/entity/taskconcept" model="[entity: entity, tasks: tasks, completedTasks: completedTasks]" />
        </div>
    </g:if>
</div>

<g:if test="${requestForQuote || requestForTrial}">
    <div class="container">
        <div class="alert alert-info">
            <g:form action="licenseRequestForm">
                <g:hiddenField name="entityId" value="${entity?.id}" />
                <g:if test="${requestForQuote}">
                    <g:hiddenField name="quote" value="true" />
                </g:if>
            <%--
                flash.warningAlert = message(code: "quoteRequest.info", args: [entityClassName]) +
                                        "<p>&nbsp;</p><p>${link(action: 'licenseRequestForm', params: [entityId: entity?.id, quote: true], elementId: 'quoteRequestLink', class: 'btn btn-primary', message(code: 'quoteRequest.submit'))}</p>"
            --%>
                <g:if test="${requestForQuote}">
                    <strong>${message(code: "quoteRequest.info")}</strong>
                </g:if>
                <g:else>
                    <strong>${message(code: "licenseRequest.info")}</strong>
                </g:else>
                <p>&nbsp;</p>
                <g:if test="${requestForQuote}">
                    <g:if test="${trialOrQuoteDesignIndicators}">
                        <div style="display: table-cell;">
                            <strong><g:message code="licenseRequest.design.available_indicators" /></strong>
                            <ul class="unstyled">
                                <g:each in="${trialOrQuoteDesignIndicators}" var="indicator">
                                    <li><input type="checkbox" name="indicator" value="${indicator.localizedName}" /> ${indicator.localizedName}</li>
                                </g:each>
                            </ul>
                        </div>
                    </g:if>
                    <g:if test="${trialOrQuoteOperatingIndicators}">
                        <div style="display: table-cell; padding-left: 30px;">
                            <strong><g:message code="licenseRequest.operating.available_indicators" /></strong>
                            <ul class="unstyled">
                                <g:each in="${trialOrQuoteOperatingIndicators}" var="indicator">
                                    <li><input type="checkbox" name="indicator" value="${indicator.localizedName}" /> ${indicator.localizedName}</li>
                                </g:each>
                            </ul>
                        </div>
                    </g:if>
                    <p>&nbsp;</p>
                </g:if>
                <div style="margin-left: 90%"><opt:submit entity="${entity}" name="save" value="${requestForQuote ? message(code:'quoteRequest.submit') : message(code:'licenseRequest.submit')}" class="btn btn-primary" formId="taskForm" /></div>
            </g:form>
        </div>
    </div>
</g:if>

<g:if test="${entity?.showDesignPhase}">
    <g:if test="${!entity?.modifiable || !validDesignLicenses}">
        <g:render template="/entity/readonly_designs" />
    </g:if>
    <g:else>
        <g:render template="/entity/designs" />
    </g:else>
</g:if>

<g:if test="${entity?.showOperatingPhase}">
    <g:if test="${!entity?.modifiable || !validOperatingLicenses}">
        <g:render template="/entity/readonly_periods" />
    </g:if>
    <g:else>
        <g:render template="/entity/periods" />
    </g:else>
</g:if>

<g:if test="${entity?.showPortfolio}">
    <g:render template="/entity/portfolio" />
</g:if>

<%-- This is here just for adding empty --%>
<div class="container section">
    <div class="sectionbody">
        <p style="height: 100px;">&nbsp;</p>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function() {
        $('.sectionexpander').on('click', function (event) {
            var section = $(this).parent().parent();
            $(section).find('.sectioncontrols').fadeToggle(200);
            $(section).find('.sectionbody').fadeToggle(200);
            $(section).toggleClass('collapsed');

        });

        $('.h2expander').on('click', function (event) {
            var section = $(this).parent().parent();
            $(section).find('.sectioncontrols').fadeToggle(200);
            $(section).find('.sectionbody').fadeToggle(200);
            $(section).toggleClass('collapsed');

        });

        $('#showAllTasks').on('click', function (event) {
            $('.completedTask').css('display', '');
            $(this).hide();
            $('#showAllInfo').hide();
        });

        $('#showAllPortfolios').on('click', function (event) {
            $('.hiddenPortfolio').css('display', '');
            $(this).hide();
            $('#showAllPortfoliosRow').hide();
        });

    });
</script>

</body>
</html>