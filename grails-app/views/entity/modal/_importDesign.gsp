<%@ page import="com.bionova.optimi.core.domain.mongo.Entity" %>
<div class="modal hide modal" id="importDesignModal">
    <div class="modal-header text-center">
        <button type="button" class="close" data-dismiss="modal">&times;</button>

        <h2><g:message code="import.design"/></h2>
    </div>

    <div class="modal-body" id="importDesignModalBody">
        <g:uploadForm action="importJson" accept-charset="UTF-8" name="importJson">
            <g:hiddenField name="parentEntityId" value="${entity?.id}"/>
            <div>

                <b><g:message code="import.design.name"/>:</b>
                <input type="text" name="designName"
                       onblur="checkDesignNameForJsonImport(this, '${message(code:"invalid_character")}', 'importDesignSubmit', '${((List<Entity>) designs)?.collect { it.name }?.join(';ocl')}', '#duplicatedDesignNameWarning')"/>

                <div class="hidden">
                    <p class="redScheme"></p>
                </div>
            </div>

            <div id="duplicatedDesignNameWarning" class="hidden redScheme">
                <g:message code="import.design.name.duplicated"/>
            </div>


            <div>
                <div class="control-group">

                    <div class="controls">
                        <input type="file" name="entityFile" id="entityFile" class="btn" value=""/>
                    </div>
                </div>
            </div>

            <div><g:message code="import.design.reminder"/></div>

            <div class="clearfix"></div>
            <button type="button" class="btn btn-default" data-dismiss="modal">${message(code: 'cancel')}</button>
            <opt:submit id="importDesignSubmit" name="import" onclick="doubleSubmitPrevention('entityFile');"
                        value="${message(code: 'import')}"
                        class="btn btn-primary removeClicks"/>
        </g:uploadForm>
    </div>
</div>