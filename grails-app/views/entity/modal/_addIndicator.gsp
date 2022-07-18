<%@ page import="com.bionova.optimi.core.Constants" %>

<g:if test="${selectedDesignIndicators && designs}">
</g:if>
<g:elseif test="${!selectedDesignIndicators && designs}">
    <div class="modal hide modalDesignScope" id="addIndicatorsDesign" style="margin-bottom: -10px">
        <div class="modal-header"><button type="button" class="close" data-dismiss="modal">&times;</button>

            <h2 id="addIndicatorsForDesignTitle">
                <g:message code="entity.show.indicators"/>
                <g:if test="${!hideEcommerce}">
                    -
                    <g:link controller="wooCommerce" action="index" class="primary">
                        <i class="fa fa-shopping-cart"></i>
                        &nbsp;
                        <g:message code="main.navi.view_and_buy"/>
                    </g:link>
                </g:if>
            </h2>
        </div>

        <div class="modal-body">
            <g:form action="saveIndicators" useToken="true" name="getStartedIndicatorFormDesign"
                    id="getStartedIndicatorFormDesign">
                <g:hiddenField name="entityId" value="${entity?.id}"/>
                <g:hiddenField name="indicatorUse" value="design"/>
                <g:set var="indicatorBenchmarkIds" value="${allBenchmarks?.collect({ it?.id?.toString() })}"/>
                <div id="newDesignIndicatorSelect">
                    <table class="indicators" id="indicatorsForEntity">
                        <tbody>
                        <tr><th><g:message code="entity.licensed_indicators"/></th></tr>
                        <g:each in="${licensedDesignIndicators}" var="indicator">
                            <tr id="newDesignIndicator${indicator.indicatorId}"
                                data-isUsingLCAParameters="${indicator.isUsingLCAParameters}"
                                style="margin-bottom:20px;" ${"benchmark".equalsIgnoreCase(indicator.visibilityStatus) || "visibleBenchmark".equalsIgnoreCase(indicator.visibilityStatus) ? 'class="hidden"' : ''}>
                                <td>
                                    <g:if test="${!indicator.deprecated}">
                                        <g:if test="${overOneIndicator}">
                                            <input data-compatibleIndicatorIds="${indicator.compatibleIndicatorIds ? indicator.compatibleIndicatorIds?.join(",") : ""}"
                                                   type="checkbox" ${"benchmark".equalsIgnoreCase(indicator.visibilityStatus) || "visibleBenchmark".equalsIgnoreCase(indicator.visibilityStatus) || indicator?.certificationImageResourceId?.contains(certificationForEntity) ? 'checked' : ''}
                                                   name="indicatorIds" value="${indicator.indicatorId}"
                                                   class=" ${"benchmark".equalsIgnoreCase(indicator.visibilityStatus) || "visibleBenchmark".equalsIgnoreCase(indicator.visibilityStatus) ? '' : 'indicatorCheckboxDesign'}"/>
                                        </g:if>
                                        <g:else>
                                            <input data-compatibleIndicatorIds="${indicator.compatibleIndicatorIds ? indicator.compatibleIndicatorIds?.join(",") : ""}"
                                                   type="checkbox" checked name="indicatorIds"
                                                   value="${indicator.indicatorId}"
                                                   class=" ${"benchmark".equalsIgnoreCase(indicator.visibilityStatus) || "visibleBenchmark".equalsIgnoreCase(indicator.visibilityStatus) ? '' : 'indicatorCheckboxDesign'}"/>
                                        </g:else>
                                    </g:if>
                                    <g:else>
                                        <input type="checkbox" disabled="disabled"/>
                                    </g:else>
                                    <img width="40px" onerror="this.style.display = 'none'"
                                         src="${indicator?.certificationImageResourceId ? '/static/logoCertificate/' + indicator?.certificationImageResourceId[0] + '.png' : ''}"/>
                                    <span class="indicatorNameContainer">
                                        <strong>${indicator.localizedName}</strong>
                                    </span>
                                    <g:abbr value="${indicator.localizedPurpose}" maxLength="100"/>
                                    <g:if test="${indicator.localizedPurpose?.size() > 100}">
                                        <a href="javascript:" class="firstIndicator" rel="popover"
                                           data-content="${indicator.localizedPurpose}">
                                            <g:message code="see_all"/>
                                        </a>
                                    </g:if>
                                    ${indicator.deprecated ? '<span class=\"warningRed\">' + message(code: 'deprecated') + '</span>' : ''}
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </g:form>
        </div>

        <div class="modal-footer">
            <div class="btn-group">
                <div class="pull-right">
                    <a href="javascript:" name="save" class="btn btn-primary" id="getStartedSubmitDesigns"
                       onclick="submitIndicators('#getStartedIndicatorFormDesign');"
                       onmouseenter="preventZeroIndicators('#getStartedSubmitDesigns', 'indicatorCheckboxDesign');">
                        ${message(code: 'next')}
                    </a>
                </div>

                <div class="pull-left" style="margin-right:10px;">
                    <a href="#" id="toggleNewIndicators" style="${overOneIndicator ? '' : 'display: none;'}"
                       onclick="toggleAllCheckBoxes('indicatorCheckboxDesign', '#getStartedSubmitDesigns', '${message(code: "design.conflicting_indicators")}');
                       preventZeroIndicators('#getStartedSubmitDesigns', 'indicatorCheckboxDesign') "
                       class="btn btn-primary">
                        <g:message code="result.graph_toggle"/>
                    </a>
                </div>
            </div>
        </div>
    </div>
</g:elseif>
<g:else>
    <div class="modal hide modalDesignScope" id="addIndicatorsDesign" style="margin-bottom: -10px">
        <div class="modal-header"><button type="button" class="close" data-dismiss="modal">&times;</button>

            <h2 id="addIndicatorsForDesignTitle" style="${overOneIndicator ? '' : 'display: none;'}">
                <g:message code="entity.show.indicators"/>
                <g:if test="${!hideEcommerce}">
                    -
                    <g:link controller="wooCommerce" action="index" class="primary">
                        <i class="fa fa-shopping-cart"></i>
                        &nbsp; <g:message code="main.navi.view_and_buy"/>
                    </g:link>
                </g:if>
            </h2>

            <h2 id="newDesignDesignTitle" style="${overOneIndicator ? 'display: none;' : ''}">
                <g:message code="design.createDesign"/>
            </h2>

            <h2 id="useDefaultLCAParamsTitle" style="display: none;"><g:message code="default_lca_parameters"/></h2>
        </div>

        <div class="modal-body">
            <g:form action="saveIndicators" useToken="true" name="getStartedIndicatorFormDesign"
                    id="getStartedIndicatorFormDesign">
                <g:hiddenField name="entityId" value="${entity?.id}"/>
                <g:hiddenField name="indicatorUse" value="design"/>
                <g:hiddenField name="newDesign" value="true"/>
                <g:set var="indicatorBenchmarkIds" value="${allBenchmarks?.collect({ it?.id?.toString() })}"/>
                <div id="newDesignIndicatorSelect" style="${overOneIndicator ? '' : 'display: none;'}">
                    <table class="indicators">
                        <tr><th><g:message code="entity.licensed_indicators"/></th></tr>
                        <g:each in="${licensedDesignIndicators}" var="indicator">
                            <tr id="newDesignIndicator${indicator.indicatorId}"
                                data-isUsingLCAParameters="${indicator.isUsingLCAParameters}"
                                style="margin-bottom:20px;" ${"benchmark".equalsIgnoreCase(indicator.visibilityStatus) || "visibleBenchmark".equalsIgnoreCase(indicator.visibilityStatus) ? 'class="hidden"' : ''}>
                                <td>
                                    <g:if test="${!indicator.deprecated}">
                                        <g:if test="${overOneIndicator}">
                                            <input onclick="appendToNewDesignCreation(this, '${message(code: "design.conflicting_indicators")}')"
                                                   data-compatibleIndicatorIds="${indicator.compatibleIndicatorIds ? indicator.compatibleIndicatorIds?.join(",") : ""}"
                                                   type="checkbox" ${"benchmark".equalsIgnoreCase(indicator.visibilityStatus) || "visibleBenchmark".equalsIgnoreCase(indicator.visibilityStatus) || indicator?.certificationImageResourceId?.contains(certificationForEntity) ? 'checked' : ''}
                                                   name="indicatorIds" value="${indicator.indicatorId}"
                                                   class=" ${"benchmark".equalsIgnoreCase(indicator.visibilityStatus) || "visibleBenchmark".equalsIgnoreCase(indicator.visibilityStatus) ? '' : 'indicatorCheckboxDesign'}"/>
                                        </g:if>
                                        <g:else>
                                            <input onclick="appendToNewDesignCreation(this, '${message(code: "design.conflicting_indicators")}')"
                                                   data-compatibleIndicatorIds="${indicator.compatibleIndicatorIds ? indicator.compatibleIndicatorIds?.join(",") : ""}"
                                                   type="checkbox" checked name="indicatorIds"
                                                   value="${indicator.indicatorId}"
                                                   class=" ${"benchmark".equalsIgnoreCase(indicator.visibilityStatus) || "visibleBenchmark".equalsIgnoreCase(indicator.visibilityStatus) ? '' : 'indicatorCheckboxDesign'}"/>
                                        </g:else>
                                    </g:if>
                                    <g:else>
                                        <input type="checkbox" disabled="disabled"/>
                                    </g:else>
                                    <img width="40px" onerror="this.style.display = 'none'"
                                         src="${indicator?.certificationImageResourceId ? '/static/logoCertificate/' + indicator?.certificationImageResourceId[0] + '.png' : ''}"/>
                                    <span class="indicatorNameContainer">
                                        <strong>${indicator.localizedName}</strong>
                                    </span>
                                    <g:abbr value="${indicator.localizedPurpose}" maxLength="100"/>
                                    <g:if test="${indicator.localizedPurpose?.size() > 100}">
                                        <a href="javascript:" class="firstIndicator" rel="popover"
                                           data-content="${indicator.localizedPurpose}">
                                            <g:message code="see_all"/>
                                        </a>
                                    </g:if>
                                    ${indicator.deprecated ? '<span class=\"warningRed\">' + message(code: 'deprecated') + '</span>' : ''}
                                </td>

                            </tr>
                        </g:each>
                    </table>
                </div>

                <div id="newDesignDesignInfo" class="row newDesignModalContainer"
                     style="${overOneIndicator ? 'display: none;' : ''}">
                    <div class="column_left col" style="width: 300px">
                        <h4><g:message code="design.modal.header1"/></h4>

                        <div class="control-group">
                            <label for="name" class="control-label">
                                <g:message code="entity.name"/>
                                <i class="icon-question-sign newDesignHelps"></i>
                            </label>

                            <div class="controls">
                                <opt:textField name="name" class="input-xlarge redBorder"
                                               placeholder="${message(code: 'entity.new_design')}"
                                               onkeyup="validateName(this, '${message(code: "invalid_character")}', 'getStartedSubmitDesigns')"
                                               required="required"/>
                                <div class="hidden"><p class="warningRed"></p></div>
                            </div>
                        </div>

                        <div class="control-group">
                            <label for="comment" class="control-label"><g:message code="design.comment"/></label>

                            <div class="controls"><opt:textField class="input-xlarge" name="comment"/></div>
                        </div>

                        <g:if test="${showRibaStage}">
                            <div class="control-group">
                                <label for="ribaStage" class="control-label">
                                    <g:message code="design.riba_stage"/>
                                    <i class="icon-question-sign ribaStageHelps"></i>
                                </label>

                                <div class="controls">
                                    <g:select onchange="removeBorder(this)" name="ribaStage" from="${ribaStages}"
                                              optionKey="${{ it.key }}"
                                              optionValue="${{ it.key == -1 ? "X - ${message(code: it.value)}" : it.key + " - " + message(code: it.value) }}"
                                              value="${design?.component && !design?.ribaStage ? -1 : design?.ribaStage ? design?.ribaStage : currentHighestRiba ? currentHighestRiba : 2}"
                                              required="required"/>
                                </div>
                            </div>
                        </g:if>
                        <g:if test="${basicQuery?.applicableIndicatorForCalculationClass?.intersect(licensedDesignIndicators?.collect({ it.indicatorId })) && basicQuery?.calculationClassification}">
                            <div class="control-group">
                                <label for="calculationClassification" class="control-label">
                                    <g:message code="calculationClassification_title"/>
                                    <i class="icon-question-sign calculationClassification-help"></i>
                                </label>

                                <div class="controls">
                                    <g:select name="calculationClassification"
                                              from="${basicQuery?.calculationClassification}" optionKey="${{ it.key }}"
                                              optionValue="${{ basicQuery.getLocalizedCalcuationClassficationName(it.key) }}"
                                              value="${design?.calculationClassficationId ?: ''}"/>
                                </div>
                            </div>
                        </g:if>

                        <div class="control-group" id="indicator-list-div">
                            <label id="indicatorHelps_label" class="control-label">
                                <strong><g:message code="design.form.indicators"/></strong>
                                <i class="icon-question-sign indicatorHelps"></i>
                            </label>
                            <table class="indicators" id="indicatorsForNewDesign">
                                <tbody>
                                <g:each in="${licensedDesignIndicators}" var="indicator">
                                    <g:if test="${"benchmark".equalsIgnoreCase(indicator.visibilityStatus) || "visibleBenchmark".equalsIgnoreCase(indicator.visibilityStatus)}">
                                        <tr class="hidden defaultHiddenIndicator">
                                            <td>
                                                <input class="hidden" type="checkbox" checked="checked"
                                                       name="enabledIndicatorIdsForDesign"
                                                       value="${indicator.indicatorId}"/>
                                            </td>
                                        </tr>
                                    </g:if>
                                    <g:elseif
                                            test="${!overOneIndicator && !("benchmark".equalsIgnoreCase(indicator.visibilityStatus) || "visibleBenchmark".equalsIgnoreCase(indicator.visibilityStatus))}">
                                        <tr class="defaultHiddenIndicator"
                                            data-isUsingLCAParameters="${indicator.isUsingLCAParameters}">
                                            <td>
                                                <input id='${indicator.indicatorId}enabledIndicatorIdsForDesign'
                                                       data-compatibleIndicatorIds="${indicator.compatibleIndicatorIds ? indicator.compatibleIndicatorIds.join(",") : ""}"
                                                       type="checkbox" disabled checked name="indicatorIds"
                                                       value="${indicator.indicatorId}"
                                                       class='indicatorCheckboxDesign'/>
                                                <img width="40px" onerror="this.style.display = 'none'"
                                                     src="${indicator?.certificationImageResourceId ? '/static/logoCertificate/' + indicator?.certificationImageResourceId[0] + '.png' : ''}"/>
                                                <span class="indicatorNameContainer">
                                                    <strong>${indicator.localizedName}</strong>
                                                </span>
                                                <i class="firstIndicator fa fa-question-circle" data-trigger="hover"
                                                   rel="popover" data-content="${indicator.localizedPurpose}"></i>
                                                ${indicator.deprecated ? '<span class=\"warningRed\">' + message(code: 'deprecated') + '</span>' : ''}
                                            </td>
                                        </tr>
                                    </g:elseif>
                                </g:each>
                                </tbody>
                            </table>
                        </div>
                    </div>

                <%-- Column Scope --%>
                    <g:if test="${Constants.EntityClass.BUILDING.toString().equalsIgnoreCase(entity?.entityClass ?: "") || (Constants.EntityClass.PRODUCT.toString().equalsIgnoreCase(entity?.entityClass ?: "") && allowedScopes.size() > 0)}">
                        <div class="column_right bordered col">
                            <h4><g:message code="design.modal.header2"/></h4>
                            <tmpl:/entity/scopeTemplate scope="${null}" parentEntity="${entity}"/>
                        </div>
                    </g:if>
                </div>

                <div id="useDefaultLCAParamsBody" style="display: none;">
                    <g:message code="default_lca_parameters.info"/>
                </div>
            </g:form>
        </div>

        <div class="modal-footer">
            <div class="btn-group">
                <div class="pull-right">
                    <a href="javascript:" name="save" class="btn btn-primary preventDoubleSubmit" style="display: none;"
                       onclick="submitAndHandleLCAParams('#getStartedIndicatorFormDesign', 'edit')"
                       id="submitDesignLCAParamsEdit">
                        <g:message code="default_lca_parameters.edit"/>
                    </a>
                    <a href="javascript:" name="save" class="btn btn-primary preventDoubleSubmit" style="display: none;"
                       onclick="submitAndHandleLCAParams('#getStartedIndicatorFormDesign', 'defaults')"
                       id="submitDesignLCAParamsDefault">
                        <g:message code="default_lca_parameters.use"/>
                    </a>
                    <a href="javascript:" name="save" class="btn btn-primary" id="getStartedSubmitDesigns"
                       onclick="assertAppendIndicatorToNewDesign('${message(code: "design.conflicting_indicators")}', ${overOneIndicator}, $(this));
                       submitOrShow('#newDesignIndicatorSelect', '#newDesignDesignInfo', '#getStartedIndicatorFormDesign', '#addIndicatorsForDesignTitle', '#newDesignDesignTitle', '${entity?.entityClass}', ${entity?.isLCAParameterQueryReady}, '${message(code: "next")}');"
                       onmouseenter="preventZeroIndicators('#getStartedSubmitDesigns', 'indicatorCheckboxDesign');">
                        ${message(code: 'next')}
                    </a>
                </div>

                <div class="pull-left" style="margin-right:10px;">
                    <a href="#" id="toggleNewIndicators" style="${overOneIndicator ? '' : 'display: none;'}"
                       onclick="toggleAllCheckBoxes('indicatorCheckboxDesign', '#getStartedSubmitDesigns', '${message(code: "design.conflicting_indicators")}');
                       preventZeroIndicators('#getStartedSubmitDesigns', 'indicatorCheckboxDesign') "
                       class="btn btn-primary">
                        <g:message code="result.graph_toggle"/>
                    </a>
                    <a href="#" id="backToIndicatorSelect" style="display: none;"
                       onclick="backToNewIndicators('#newDesignIndicatorSelect', '#newDesignDesignInfo', '#addIndicatorsForDesignTitle', '#newDesignDesignTitle', '${message(code: 'start')}', '#getStartedSubmitDesigns')"
                       class="btn btn-default">
                        <g:message code="back"/>
                    </a>
                    <a href="#" id="backToNewDesign" style="display: none;"
                       onclick="backToNewDesign('#useDefaultLCAParamsBody', '#newDesignDesignInfo', '#useDefaultLCAParamsTitle', '#newDesignDesignTitle', '${message(code:"next")}', ${overOneIndicator})"
                       class="btn btn-default">
                        <g:message code="back"/>
                    </a>
                </div>
            </div>
        </div>
    </div>
</g:else>