<%-- Copyright (c) 2012 by Bionova Oy --%>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<g:set var="indicatorService" bean="indicatorService"/>
<div id="mainContent">
<div class="container">
    <div class="screenheader">
        <h4> <opt:link controller="main" removeEntityId="true"><g:message code="main" /></opt:link> >
            <sec:ifLoggedIn>
                <g:if test="${parentEntity?.id}">
                    <opt:link controller="entity" action="show" id="${parentEntity?.id}">
                        <g:if test="${entity}">
                            <a href="#" rel="popover" data-trigger="hover" data-content="${parentEntity.name} - ${entity.operatingPeriodAndName}"><g:abbr value="${parentEntity.name}" maxLength="20" /> - <g:abbr value="${entity.operatingPeriodAndName}" maxLength="15" /></a>
                        </g:if>
                        <g:else>
                            <g:abbr value="${parentEntity.name}" maxLength="38" />
                        </g:else>
                    </opt:link>
                </g:if>
                <g:elseif test="${entity?.id}">
                    <opt:link controller="entity" action="show" id="${entity?.id}" >
                        <g:if test="${childEntity}">
                            <a href="#" rel="popover" data-trigger="hover" data-content="${entity.name} - ${childEntity.operatingPeriodAndName}"><g:abbr value="${entity.name}" maxLength="20" /> - <g:abbr value="${childEntity.operatingPeriodAndName}" maxLength="15" /></a>
                        </g:if>
                        <g:else>
                            <g:abbr value="${entity.name}" maxLength="38" />
                        </g:else>
                    </opt:link>
                </g:elseif>
            </sec:ifLoggedIn>
            > <g:if test="${design}">
            ${design.name} > <g:message code="design.modifyDesign"/>
        </g:if><g:else>
            <g:message code="entity.new_design"/> <br/>
        </g:else> </h4>
        <g:render template="/entity/basicinfoView"/>
        <h3><g:message code="${design? design.name : 'new.design'}" dynamic="true"/></h3>
    </div>
</div>

<div class="container section">
    <div class="section body">
        <g:uploadForm action="save" name="designForm">
            <div class="container">
                    <g:hiddenField name="id" value="${design?.id}"/>
                    <g:hiddenField name="parentEntityId" value="${entity?.id}" />
                    <g:hiddenField name="ifcImport" value="${ifcImport}" />
                    <g:if test="${originalEntityId}">
                        <g:hiddenField name="originalEntityId" value="${originalEntityId}" />
                    </g:if>
                    <div class="clearfix"></div>
                    <div class="column_left">
                      <div class="control-group">
                        <label for="name" class="control-label"><g:message code="entity.name" /></label>
                    <div class="controls"><opt:textField entity="${entity}" design="${design}" name="name" value="${design?.name}" class="input-xlarge" /></div>
                    </div>

                    <div class="control-group">
                        <label for="comment" class="control-label"><g:message code="design.comment" /></label>
                        <div class="controls"><opt:textField entity="${entity}" design="${design}" name="comment" value="${design?.comment}" class="input-xlarge" /></div>
                    </div>

                    <g:if test="${showRibaStage}">
                        <div class="control-group">
                            <label for="ribaStage" class="control-label"><g:message code="design.riba_stage" /> <i class="icon-question-sign" id="ribaStageHelp"></i></label>
                            <div class="controls">

                                <g:select style="${design?.ribaStage == null ? 'border: 1px solid red;' : ''}" onchange="removeBorder(this);" name="ribaStage" from="${ribaStages}" optionKey="${{it.key}}"
                                          optionValue="${{it.key == -1 ? "X - ${message(code:it.value)}" : it.key + " - " + message(code:it.value)}}"  value="${design?.component && !design?.ribaStage ? -1 : design?.ribaStage ? design?.ribaStage : currentHighestRiba ? currentHighestRiba : 2}" noSelection="['' : '']" />

                            </div>
                        </div>
                    </g:if>

                    <g:if test="${indicators}">
                        <g:if test="${hideIndicatorSelect}">
                            <g:each in="${indicators}" var="indi">
                                <g:checkBox name="indicatorId" class="hidden" value="${indi.indicatorId}" checked="checked" /><br />
                            </g:each>
                        </g:if>
                        <g:else>
                            <div class="control-group"  >
                                <label for="indicatorId" class="control-label"><strong><g:message code="design.form.indicators"/></strong> <i class="icon-question-sign" id="indicatorHelp"></i></label>
                                <table class="indicators">
                                    <g:each in="${indicators}" var="indi">
                                        <g:set var="benchmarkIndicator" value="${"benchmark".equals(indi?.visibilityStatus) || "visibleBenchmark".equals(indi?.visibilityStatus)}" />
                                        <tr class="${benchmarkIndicator ? "hidden" : ""}"><td><g:checkBox name="indicatorId" onclick="resolveCompatibleIndicators('${message(code: "design.conflicting_indicators")}')" data-compatibleIndicatorIds="${indi.compatibleIndicatorIds ? indi.compatibleIndicatorIds.join(",") : ""}" class="${benchmarkIndicator ? 'hidden' : 'enabledIndicatorIdsForDesign'}" value="${indi.indicatorId}" checked="${benchmarkIndicator||(!design && (!disabledIndicatorsForOriginal||(disabledIndicatorsForOriginal && !disabledIndicatorsForOriginal.contains(indi.indicatorId))))||(design && !design.disabledIndicators?.contains(indi.indicatorId))}" /> ${indicatorService.getLocalizedName(indi)}</td></tr>
                                    </g:each>
                                    <g:each in="${indicators}" var="indicator">
                                        <g:if test="${"benchmark".equalsIgnoreCase(indicator.visibilityStatus) || "visibleBenchmark".equalsIgnoreCase(indicator.visibilityStatus)}">
                                            <tr class="hidden"><td><input type="checkbox" checked="checked" name="enabledIndicatorIdsForDesign" value="${indicator.indicatorId}"/></td></tr>
                                        </g:if>
                                    </g:each>
                                </table>


                            </div>
                        </g:else>
                    </g:if>
                    <div class="clearfix"></div>
                </div>

                <%-- Column Scope --%>
                <div class="column_right bordered">
                    <tmpl:/entity/scopeTemplate parentEntity="${entity}" />
                </div>
            </div>
            <opt:submit entity="${entity}" design="${design}" name="save" value="${design?.id ? message(code:'save') : message(code:'add')}"
                        class="btn btn-primary" formId="designForm" />
            <opt:link action="cancel" class="btn"><g:message code="cancel" /></opt:link>
        </g:uploadForm>
    </div>
    </div>
</div>
</div>
<script>
    $('#indicatorHelp').popover({
        content:"${message(code:'design.form.indicator_help')}",
        placement:'right',
        template: '<div class="popover"><div class="arrow"></div><div class="popover-content indicatorHelp"></div></div>',
        trigger:'hover',
        html:true
    });

    $('#ribaStageHelp').popover({
        content:"${message(code:'riba_stage_help')}",
        placement:'right',
        template: '<div class="popover"><div class="arrow"></div><div class="popover-content ribaHelp"></div></div>',
        trigger:'hover',
        html:true
    });

    $('#designStatusHelp').popover({
        content:"${message(code:'design.status.help')}",
        placement:'right',
        template: '<div class="popover"><div class="arrow"></div><div class="popover-content ribaHelp"></div></div>',
        trigger:'hover',
        html:true
    });

    $('.preventUncheck').on('change', function(e) {
        if ($('.preventUncheck:checked').length == 0 && !this.checked)
            this.checked = true;
    });
    $(function(){
        maskAllSelectInDivWithSelect2('mainContent', false, '${message(code:'select')}')
    })
</script>
</body>
</html>



