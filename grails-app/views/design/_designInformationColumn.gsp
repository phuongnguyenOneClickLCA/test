<g:set var="indicatorHelp" value="${message(code:'design.form.indicator_help')}"/>
<g:set var="ribaStageHelp" value="${message(code:'riba_stage_help')}"/>
<g:set var="designStatusHelp" value="${message(code:'design.status.help')}"/>
<g:set var="newDesignHelps" value="${message(code:'new_design_help')}"/>
<g:set var="localizedText" value="${message(code: "design.conflicting_indicators")}" />
<g:set var="language" value="${org.springframework.context.i18n.LocaleContextHolder.getLocale().getLanguage().toUpperCase()}"/>


<g:if test="${design?.id || originalEntity}">
    <div class="control-group">
        <label for="name" class="control-label"><g:message code="entity.name" /></label>
        <div class="controls">
            <opt:textField entity="${entity}" design="${design}" name="name" value="${isCopy ? message(code: 'copy_of') +" "+ originalEntity?.name : design?.name}" class="input-xlarge ${originalEntity?.name || design?.name ? '' : 'redBorder'}" onkeyup="validateName(this, '${message(code:"invalid_character")}', 'saveDesign')" required="true" />
            <div class="hidden"><p class="warningRed"></p></div>

        </div>
    </div>

    <div class="control-group">
        <label for="comment" class="control-label"><g:message code="design.comment" /></label>
        <div class="controls"><opt:textField entity="${entity}" design="${design}" name="comment" value="${design?.comment}" class="input-xlarge" /></div>
    </div>

    <g:if test="${showRibaStage}">
        <div class="control-group">
            <label for="ribaStage" class="control-label">
                <g:message code="design.riba_stage" />
                <a href="#" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${ribaStageHelp}" data-placement="right" data-html="true">
                    <i class="icon-question-sign"></i>
                </a>
            </label>
            <div class="controls">

                <g:select name="ribaStage"
                          from="${ribaStages}"
                          optionKey="${{it.key}}"
                          optionValue="${{it.key == -1 ? "X - ${message(code:it.value)}" : it.key + " - " + message(code:it.value)}}"
                          value="${design?.component && design?.ribaStage == null ? -1 : design?.ribaStage != null ? design?.ribaStage :  originalEntity?.ribaStage!= null ?  originalEntity?.ribaStage : currentHighestRiba != null ? currentHighestRiba : 2}"
                          required="true" />

            </div>
        </div>
    </g:if>
    <g:if test="${showCalculationClassification}">
        <div class="control-group">
            <label for="calculationClassficationId" class="control-label"><g:message code="calculationClassification_title" /> <i class="icon-question-sign calculationClassification-help"></i></label>
            <div class="controls">
                <g:select name="calculationClassficationId" from="${basicQuery?.calculationClassificationList}" optionKey="${{it.key}}" optionValue="${{it.value?.get(language)}}"  value="${design?.calculationClassficationId ?: ''}" noSelection="['' : '']"/>
            </div>
        </div>
    </g:if>

    <g:if test="${indicators}">
        <g:set var="isMoreIndicators" value="${indicators?.findAll{!"benchmark".equals(it?.visibilityStatus) && !"visibleBenchmark".equals(it?.visibilityStatus)}?.size() > 1}" />
        <g:if test="${hideIndicatorSelect}">
            <g:each in="${indicators}" var="indi">
                <g:checkBox id="indicatorIdList-${indi.indicatorId}" name="indicatorIdList" class="hidden" value="${indi.indicatorId}" checked="checked" /><br />
            </g:each>
        </g:if>
        <g:else>
            <div class="control-group" id="indicator-list-div">
                <g:if test="${isMoreIndicators}">
                <label for="indicatorIdList" class="control-label">
                    <strong><g:message code="design.form.indicators"/></strong>
                    <a href="#" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${indicatorHelp}" data-placement="right" data-html="true">
                        <i class="icon-question-sign"></i>
                    </a>
                </label>
                </g:if>
                <table class="indicators">
                    <g:each in="${indicators}" var="indi">
                        <g:set var="benchmarkIndicator" value="${"benchmark".equals(indi?.visibilityStatus) || "visibleBenchmark".equals(indi?.visibilityStatus)}" />
                        <tr class="${benchmarkIndicator ? "hidden" : ""}">
                            <td><g:checkBox id="indicatorIdList-${indi.indicatorId}" name="indicatorIdList" onclick="compatibleIndicators(this, '${localizedText}')"
                                            data-compatibleIndicatorIds="${indi.compatibleIndicatorIds ? indi.compatibleIndicatorIds.join(",") : ""}"
                                            class="${benchmarkIndicator ? 'hidden' : 'enabledIndicatorIdsForDesign'}" value="${indi.indicatorId}"
                                            checked="${benchmarkIndicator||(!design && (!disabledIndicatorsForOriginal||(disabledIndicatorsForOriginal && !disabledIndicatorsForOriginal.contains(indi.indicatorId))))||(design && !design.disabledIndicators?.contains(indi.indicatorId))}"
                                            disabled="${!isMoreIndicators}"/>
                                ${indi.localizedName}
                            </td>
                        </tr>
                    </g:each>
                </table>
            </div>
        </g:else>
    </g:if>

</g:if>
<g:else>
    <div class="control-group">
        <label for="name" class="control-label"><g:message code="entity.name" />
            <a href="#" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${newDesignHelps}" data-placement="right" data-html="true">
                <i class="icon-question-sign"></i>
            </a>
        </label>
        <div class="controls">
            <opt:textField name="name" placeholder="${message(code: 'entity.new_design')}" class="input-xlarge redBorder" onkeyup="validateName(this, '${message(code:"invalid_character")}', 'saveDesign')" required="true" />
            <div class="hidden"><p class="warningRed"></p></div>
        </div>
    </div>

    <div class="control-group">
        <label for="comment" class="control-label"><g:message code="design.comment" /></label>
        <div class="controls"><opt:textField class="input-xlarge" name="comment" /></div>
    </div>

    <g:if test="${showRibaStage}">
        <div class="control-group">
            <label for="ribaStage" class="control-label">
                <g:message code="design.riba_stage" />
                <a href="#" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${ribaStageHelp}" data-placement="right" data-html="true">
                    <i class="icon-question-sign"></i>
                </a>
            </label>
            <div class="controls">
                <g:select name="ribaStage"
                          from="${ribaStages}"
                          optionKey="${{it.key}}"
                          optionValue="${{it.key == -1 ? "X - ${message(code:it.value)}" : it.key + " - " + message(code:it.value)}}"
                          value="${currentHighestRiba ? currentHighestRiba : 2}"
                          required="true"/>
            </div>
        </div>
    </g:if>
    <g:if test="${showCalculationClassification}">
        <div class="control-group">
            <label for="calculationClassficationId" class="control-label"><g:message code="calculationClassification_title" /> <i class="icon-question-sign calculationClassification-help"></i></label>
            <div class="controls">
                <g:select name="calculationClassficationId" from="${basicQuery?.calculationClassificationList}" optionKey="${{it.key}}" optionValue="${{it.value?.get(language)}}"  value="${design?.calculationClassficationId ?: ''}" noSelection="['' : '']"/>
            </div>
        </div>
    </g:if>
    <div class="control-group" id="indicator-list-div">
        <g:if test="${selectedDesignIndicators?.findAll{!"benchmark".equals(it?.visibilityStatus) && !"visibleBenchmark".equals(it?.visibilityStatus)}?.size() > 1}">
        <label class="control-label">
            <strong><g:message code="design.form.indicators"/></strong>
            <a href="#" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${indicatorHelp}" data-placement="right" data-html="true">
                <i class="icon-question-sign"></i>
            </a>
        </label>
        </g:if>
        <table class="indicators">
            <g:each in="${selectedDesignIndicators}" var="indi">
                <g:set var="benchmarkIndicator" value="${"benchmark".equals(indi?.visibilityStatus) || "visibleBenchmark".equals(indi?.visibilityStatus)}" />
                <tr class="${benchmarkIndicator ? "hidden" : ""}">
                    <td>
                        <g:checkBox id="indicatorIdList-${indi.indicatorId}" name="indicatorIdList" onclick="compatibleIndicators(this, '${localizedText}')" data-isUsingLCAParameters="${indi.isUsingLCAParameters}"
                                    data-compatibleIndicatorIds="${indi.compatibleIndicatorIds ? indi.compatibleIndicatorIds.join(",") : ""}"
                                    class="${benchmarkIndicator ? 'hidden' : 'enabledIndicatorIdsForDesign'}" value="${indi.indicatorId}"
                                    checked="true" />
                        ${indi.localizedName}
                    </td>
                </tr>
            </g:each>
        </table>
    </div>
</g:else>

<script type="text/javascript">
    $("input, select").on("change",function () {
            validateName($("#name"), '${message(code:"invalid_character")}', 'saveDesign')
    })
    <g:if test="${isCopy}">
    $(function () {
        validateName($("#name"), '${message(code:"invalid_character")}', 'saveDesign')
    })
    </g:if>

    <g:if test="${indicatorId}">
        setSelectdIndicator('${indicatorId}')
    </g:if>

    resolveIndicatorConflict('${localizedText}')

</script>





