<div class="modal hide modal-large" id="showHiddenDesign" style="margin-bottom: -10px">
    <div class="modal-header"><button type="button" class="close" data-dismiss="modal">&times;</button>
        <h2 id="showHiddenDesignTitle"><g:message code="entity.showAllHiddenDesign" /></h2>
    </div>
    <div class="modal-body" id="showHiddenDesignSelect">
        <g:each in="${designs}" var="design">
            <g:if test="${design && design.isHiddenDesign}">
                <label><input class="designItem" type="checkbox" value="${design.id}"/> ${design.operatingPeriodAndName}</label>
            </g:if>
        </g:each>
    </div>
    <div class="modal-footer">
        <div class="btn-group">
            <div class="pull-right">
                <a href="javascript:" name="save" class="btn btn-primary" onclick="showHiddenDesignByIds($('#showHiddenDesignSelect'));">${message(code: 'save')}</a>
                <button type="button" class="btn btn-default" data-dismiss="modal">${message(code: 'cancel')}</button>
            </div>
        </div>
    </div>
</div>