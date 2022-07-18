<div class="modal hide fade bigModal" id="importkml">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h2><g:message code="cityDistrict.import_kml" /></h2>
    </div>
    <div class="modal-body">
        <div class="clearfix"></div>
        <div class="tab-content">
            <div class="tab-pane active" id="tab-details">
                <g:uploadForm action="importKmlFile" useToken="true">
                    <g:hiddenField name="entityId" value="${entity?.id}" />
                    <fieldset>
                        <div class="control-group">
                            <div class="controls">
                                <div class="input-append">
                                    <input type="file" name="kmlFile" class="input-xlarge span8" value=""/>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    <div class="modal-footer">
                        <opt:submit entity="${entity}" name="upload" value="${message(code: 'upload')} kml" class="btn btn-primary"/>
                        <a href="#" class="btn" data-dismiss="modal"><g:message code="cancel" /></a>
                    </div>
                </g:uploadForm>
             </div>
        </div>
    </div>
</div>