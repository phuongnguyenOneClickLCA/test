<%-- Copyright (c) 2012 by Bionova Oy --%>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <meta name="format-detection" content="telephone=no"/>
    <g:set var="indicatorService" bean="indicatorService"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <h4> <opt:link controller="main" removeEntityId="true"><g:message code="main" /></opt:link>
        <sec:ifLoggedIn>
            <g:if test="${entity?.id}">
                > <opt:link controller="entity" action="show" id="${entity?.id}">
                <g:abbr value="${entity?.name}" maxLength="20" />
            </opt:link>
            </g:if>
        </sec:ifLoggedIn>
        > <g:message code="addIndicator" /> <br/> </h4>

        <g:render template="/entity/basicinfoView"/>
    </div>
</div>

<div class="container section">
    <g:if test="${indicators}">
            <h2><g:message code="entity.show.indicators" /></h2>
        <div class="sectionbody">

            <g:form action="saveIndicators" useToken="true" name="indicatorForm">
                <g:hiddenField name="entityId" value="${entity?.id}" />
                <g:hiddenField name="indicatorUse" value="${indicatorUse}" />
                <div class="pull-right">
                    <a href="#" id="deselectAll" onclick="uncheck('indicatorCheckbox');" class="btn btn-primary"><g:message code="deselect_all" /></a>
                    <opt:submit entity="${entity}" name="save" value="${message(code:'save')}" id="saveIndicatorsButton" class="btn btn-primary"/>
                    <opt:link controller="entity" action="show" class="btn" id="${entity?.id}"><g:message code="cancel" /></opt:link>
                </div>

                <fieldset>
                    <div id="saveIndicators" class="control-group">
                        <div class="controls">
                            <table class="indicators">
                                <tr><th>&nbsp</th></tr>
                                <g:each in="${indicators}" var="indicator">
                                    <tr ${"benchmark".equalsIgnoreCase(indicator.visibilityStatus) ? 'class="hidden"': ''}>
                                        <td>
                                            <g:if test="${!indicator.deprecated || entity.indicatorIds?.contains(indicator.indicatorId)}">
                                                <opt:checkBox entity="${entity}" name="indicatorIds" value="${indicator.indicatorId}" checked="${entity.indicatorIds?.contains(indicator.indicatorId) || "benchmark".equalsIgnoreCase(indicator.visibilityStatus) || "visibleBenchmark".equalsIgnoreCase(indicator.visibilityStatus)}" class="${"benchmark".equalsIgnoreCase(indicator.visibilityStatus) || "visibleBenchmark".equalsIgnoreCase(indicator.visibilityStatus) ? '' : 'indicatorCheckbox preventUncheck'}" />
                                            </g:if>
                                            <g:else>
                                                <input type="checkbox" disabled="disabled" />
                                            </g:else>
                                            <img width="40px" onerror="this.style.display='none'" src="${indicator?.certificationImageResourceId ? '/static/logoCertificate/' + indicator?.certificationImageResourceId[0] + '.png' : '' }"/>
                                            <strong>${indicatorService.getLocalizedName(indicator)}</strong>
                                            <g:abbr value="${indicator.localizedPurpose}" maxLength="100" />
                                            <g:if test="${indicator.localizedPurpose?.size() > 100}">
                                                <a href="javascript:" rel="popover" data-content="${indicator.localizedPurpose}"><g:message code="see_all" /></a>
                                            </g:if>
                                            ${indicator.deprecated ? '<span class=\"warningRed\">' + message(code: 'deprecated') + '</span>': ''}
                                        </td>
                                    </tr>
                                </g:each>

                                <g:if test="${notLicensedIndicators}">
                                    <tr><td>&nbsp;</td></tr>
                                    <tr><th><h3><g:message code="entity.not_licensed_indicators" /></h3></th></tr>
                                    <g:each in="${notLicensedIndicators}" var="indicator">
                                        <tr ${"private".equalsIgnoreCase(indicator.visibilityStatus) || "benchmark".equalsIgnoreCase(indicator.visibilityStatus) || "visibleBenchmark".equalsIgnoreCase(indicator.visibilityStatus)  ? 'class="hidden"': ''}>
                                            <td>
                                                <g:if test="${!indicator.deprecated}">
                                                    <opt:checkBox name="notLicensedIndicatorIds" disabled="disabled" />
                                                    <img width="40px" onerror="this.style.display='none'" src="${indicator?.certificationImageResourceId ? '/static/logoCertificate/' + indicator?.certificationImageResourceId[0] + '.png' : '' }"/>
                                                    <strong>${indicatorService.getLocalizedName(indicator)}</strong>
                                                    <g:abbr value="${indicatorService.getLocalizedPurpose(indicator)}" maxLength="100" />
                                                    <g:if test="${indicatorService.getLocalizedPurpose(indicator)?.size() > 100}">
                                                        <a href="javascript:" rel="popover" data-content="${indicatorService.getLocalizedPurpose(indicator)}"><g:message code="see_all" /></a>
                                                    </g:if>
                                                    <g:if test="${indicator.wooCommerceProductId}">
                                                        <span>&nbsp; &nbsp;<i class="fa fa-chevron-right" aria-hidden="true"></i></span>
                                                        <g:link controller="wooCommerce" action="index" params="[productId: indicator.wooCommerceProductId]" style="text-decoration:none;">   &nbsp;
                                                        <g:message code="ecommerce.buy_product"/>  <i class="fa fa-shopping-cart buyButtonWooCommerce"></i> </g:link>
                                                    </g:if>
                                                </g:if>
                                            </td>
                                        </tr>
                                    </g:each>
                                </g:if>
                            </table>
                        </div>
                    </div>
                    <div id="spinningLoader"></div>
                </fieldset>
            </g:form>
        </div>
    </g:if>
    <g:else>
        <div class="sectionheader">

            <h2><g:message code="entity.show.indicators" /></h2>
        </div>
        <div class="sectionbody">
            <div class="alert alert-success"><g:message code="entity.no_indicators" /></div>
        </div>
    </g:else>

</div>

<script type="text/javascript">
    $('#indicatorForm').on("submit", function() {
        $('#saveIndicatorsButton').attr("disabled", "disabled");
        $('#deselectAll').attr("disabled", "disabled");
        $('.indicatorCheckbox').attr("readOnly", "readOnly");
        $('#saveIndicators').hide();
        appendLoader('spinningLoader');
    });

    $('.preventUncheck').on('change', function(e) {
        preventZeroIndicators('#saveIndicatorsButton', 'indicatorCheckbox');
    });

    function uncheck(checkBoxClass) {
        checkBoxClass = '.' + checkBoxClass;
        $(checkBoxClass).prop('checked', false);
        $(checkBoxClass).attr("disabled", false);
        $(checkBoxClass).closest('tr').css({'pointer-events':'', 'background-color':''});
        preventZeroIndicators('#saveIndicatorsButton', 'indicatorCheckbox');
    }
</script>

</body>
</html>



