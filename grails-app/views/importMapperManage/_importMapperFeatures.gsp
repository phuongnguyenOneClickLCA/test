<div id="addFeaturesDiv" class="span4 bordered">
    <h3>Add fields to manipulate importMapper</h3>
    <g:form action="addObjects" useToken="true">
        <g:hiddenField name="id" value="${applicationId}"/>

        <fieldset>
            <div class="control-group">
                <label for="inputWarning" class="control-label">
                    <strong>Warning should be given if material includes following fields:</strong>
                </label>
                <div class="controls">
                    <g:textField name="inputWarning" id="inputWarning" class="input-xlarge"/>
                    <opt:submit name="addWarning" disabled="${disabled}" value="${message(code: 'add')}" class="btn btn-primary" />
                </div>
                <opt:checkBox name="matchAllWarningWariants" checked="false" />
                <i><strong>Match all variants of the value</strong> (*)</i>
                <p>&nbsp;</p>
                <table class="table table-condensed">
                    <tbody>
                    <g:each in="${importMapperWarns}" var="warn">
                        <tr>
                            <td>${warn}</td><td>
                            <g:if test="${!disabled}">
                                <opt:link action="removeImportMapperWarning" id="${applicationId}" params="[importMapperWarning: warn]" class="btn btn-danger"
                                          onclick="return modalConfirm(this);" data-questionstr="Are you sure you want to delete: ${warn} ?" data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}" data-titlestr="${message(code:'admin.license.delete_indicator.header')}"><g:message code="delete" /></opt:link>
                            </g:if>
                        </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>

            <div class="control-group">
                <label for="inputDiscard" class="control-label">
                    <strong>Set should be discarded if material contains following fields:</strong><br/>
                </label>
                <div class="controls">
                    <g:textField id="inputDiscard" class="input-xlarge" name="inputDiscard"/>
                    <opt:submit name="addDiscard" disabled="${disabled}" value="${message(code: 'add')}" class="btn btn-primary" />
                </div>
                <opt:checkBox name="matchAllDiscardWariants" checked="false" />
                <i><strong>Match all variants of the value</strong> (*)</i>
                <p>&nbsp;</p>
                <table class="table table-condensed">
                    <tbody>
                    <g:each in="${importMapperDiscards}" var="discard">
                        <tr>
                            <td>${discard}</td><td>
                            <g:if test="${!disabled}">
                                <opt:link action="removeImportMapperDiscard" id="${applicationId}" params="[importMapperDiscard: discard]" class="btn btn-danger"
                                          onclick="return modalConfirm(this);" data-questionstr="Are you sure you want to delete: ${discard} ?" data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}" data-titlestr="${message(code:'admin.license.delete_feature.header')}"><g:message code="delete" /></opt:link>
                            </g:if>
                        </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </fieldset>
    </g:form>
    <div class="control-group">
        <h3>Upload system training data</h3>
    </div>
</div> 