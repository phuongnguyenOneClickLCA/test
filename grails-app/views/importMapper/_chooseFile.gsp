    <g:uploadForm action="fileToSession" useToken="true" name="theForm" method="post" novalidate="novalidate">
        <div class="screenheader">
            <div class="btn-group pull-right hide-on-print" id="actions">
                <g:if test="${entityId}">
                    <opt:link controller="entity" action="show" params="[entityId: entityId]" class="btn btn-block importmapper-headerbuttons" onclick="return modalConfirm(this);"
                              data-questionstr="${message(code: 'importMapper.index.back')}"
                              data-truestr="${message(code: 'yes')}" data-falsestr="${message(code: 'no')}"
                              data-titlestr="${message(code: 'back')}">
                        <g:message code="back" /></opt:link>
                </g:if>
                <g:else>
                    <opt:link controller="main" removeEntityId="true" class="btn btn-block importmapper-headerbuttons" onclick="return modalConfirm(this);"
                              data-questionstr="${message(code: 'importMapper.index.back')}"
                              data-truestr="${message(code: 'yes')}" data-falsestr="${message(code: 'no')}"
                              data-titlestr="${message(code: 'back')}">
                        <g:message code="back" /></opt:link>
                </g:else>
                <g:if test="${importFileExists}">
                    <g:link controller="importMapper" action="downloadExcelFile" class="btn btn-primary" elementId="alreadyInSessionExcel"><g:message code="results.expand.download_excel" /></g:link>
                    <a class="btn btn-primary" href="javascript:" onclick="sendToAnotherUserSwal()"><g:message code="sendMeData.heading"/></a>
                </g:if>
                <button name="save" onclick="clearMessages('#loadingSpinner','#importPageContent');" type="submit"
                   class="btn btn-primary btn-block" id="save">
                    ${message(code: 'continue')}
                </button>
            </div>
            <h1><g:message code="importMapper.index.choose_data"/></h1>
        </div>
        <g:hiddenField name="applicationId" value="${session?.getAttribute("applicationId")}"/>
        <g:hiddenField name="importMapperId" value="${session?.getAttribute("importMapperId")}"/>
        <g:hiddenField name="entityId" value="${entityId}"/>
        <g:hiddenField name="childEntityId" value="${childEntityId}"/>
        <g:hiddenField name="indicatorId" value="${indicatorId}"/>
        <g:if test="${bimModelChecker}">
            <label>&nbsp;</label>
            <g:hiddenField name="bimModelChecker" value="${bimModelChecker}" />
        </g:if>
        <div id="left" style="width: 100%;">
            <g:if test="${!importFileExists}">
                <div class ="container-fluid center-block" style="margin-left:50px !important; margin-right: 50px !important;">
                    <div class="pull-left">
                        <div class="control-group" style="margin-bottom: 30px;">
                            <label class="control-label">
                                <strong><g:message code="importMapper.index.choose_file"/></strong>
                                <g:if test="${appHelpUrl}">&nbsp;
                                    <a href="${appHelpUrl}" target="_blank" class="helpLink"><i
                                            class="fa fa-question margin-right-5"></i><g:message
                                            code="help.instructions"/></a>
                                </g:if>
                            </label>
                            <input type="file" name="importFile"
                                   id="importMapperFileUpload" onchange="removeBorder(this);" class="input-xlarge" value=""/>
                            <g:if test="${fileMaxSize}">
                                (max. ${fileMaxSize} KB)
                            </g:if>
                        </div>
                    </div>
                </div>
            </g:if>
        </div>
    </g:uploadForm>