<div class="modal hide fade bigModal" id="${modalId}">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h2><g:message code="entity.modify_indicators" args="[selectedIndicatorsSize, indicatorsSize]" /></h2>
    </div>
    <div class="modal-body">
        <div class="clearfix"></div>
        <div class="tab-content">
            <div class="tab-pane active" id="tab-details">
                <g:if test="${indicators || notLicensedIndicators}">
                    <g:form action="#" useToken="true">
                        <g:hiddenField name="entityId" value="${entity?.id}" />
                        <g:hiddenField name="indicatorUse" value="${indicatorUse}" />
                        <fieldset>
                            <div class="control-group">
                                <div class="controls">
                                    <table class="indicators">
                                        <g:each in="${indicators}" var="indicator">
                                            <tr>
                                                <td>
                                                    <opt:checkBox entity="${entity}" name="indicatorIds" value="${indicator.indicatorId}" checked="${entity.indicatorIds?.contains(indicator.indicatorId)}" />
                                                    <strong>${indicator.localizedName}</strong>
                                                    <g:abbr value="${indicator.localizedPurpose}" maxLength="100" />
                                                    <g:if test="${indicator.localizedPurpose?.size() > 100}">
                                                        <a href="javascript:;" rel="popover" data-content="${indicator.localizedPurpose}"><g:message code="see_all" /></a>
                                                    </g:if>
                                                </td>
                                            </tr>
                                        </g:each>

                                        <g:if test="${notLicensedIndicators}">
                                            <tr><td>&nbsp;</td></tr>
                                            <tr><td><g:message code="entity.not_licensed_indicators" /></td></tr>
                                            <g:each in="${notLicensedIndicators}" var="indicator">
                                                <tr>
                                                    <td>
                                                        <opt:checkBox name="notLicensedIndicatorIds" disabled="disabled" />
                                                        <strong>${indicator.localizedName}</strong>
                                                        <g:abbr value="${indicator.localizedPurpose}" maxLength="100" />
                                                        <g:if test="${indicator.localizedPurpose?.size() > 100}">
                                                            <a href="javascript:;" rel="popover" data-content="${indicator.localizedPurpose}"><g:message code="see_all" /></a>
                                                        </g:if>
                                                    </td>
                                                </tr>
                                            </g:each>
                                        </g:if>
                                    </table>
                                </div>
                            </div>
                        </fieldset>
                        <div class="modal-footer">
                            <opt:actionSubmit entity="${entity}" action="addIndicators" value="${message(code:'save')}" class="btn btn-primary"/>
                            <a href="#" class="btn" data-dismiss="modal"><g:message code="cancel" /></a>
                        </div>
                    </g:form>
                </g:if>
                <g:else>
                    <div class="alert alert-success"><g:message code="entity.no_indicators" /></div>
                </g:else>
            </div>
        </div>
    </div>
</div>