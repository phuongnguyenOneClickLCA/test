<div class="modal hide modal-large" id="showDeletedDesign" style="margin-bottom: -10px">
    <div class="modal-header"><button type="button" class="close" data-dismiss="modal">&times;</button>
        <h2 id="showHiddenDesignTitle"><g:message code="entity.showAllDeletedDesign" /></h2>
    </div>
    <div class="modal-body" id="showDeletedDesignSelect">
        <g:each in="${fullListDesigns.findAll{it && it.deleted}}" var="design">
            <label><input class="designItem" type="checkbox" value="${design.id}"/> ${design.operatingPeriodAndName}</label>
        </g:each>
    </div>
    <div class="modal-footer">
        <div class="btn-group">
            <div class="pull-right">
                <a href="javascript:" name="save" class="btn btn-primary" onclick="restoreDesigns($('#showDeletedDesignSelect'), '${entity.id}');">${message(code: 'save')}</a>
                <button type="button" class="btn btn-default" data-dismiss="modal">${message(code: 'cancel')}</button>
            </div>
        </div>
    </div>
</div>