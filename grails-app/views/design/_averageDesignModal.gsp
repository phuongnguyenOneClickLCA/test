<g:hiddenField name="parentEntityId" value="${entity?.id}" />
<g:hiddenField name="ifcImport" value="${ifcImport}" />
<g:hiddenField name="newDesign" value="true"/>
<g:hiddenField name="averageNewDesign" value="true"/>
<g:if test="${originalEntityId}"><g:hiddenField name="originalEntityId" value="${originalEntityId}" /></g:if>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">&times;</button>

    <h2>
        <g:message code="design.create.average.design"/>
    </h2>
</div>

<div class="modal-body">
    <%-- Column Design Information --%>
    <div class="column_left" style="width: 300px;">
        <h4><g:message code="design.modal.average.header1"/></h4>
        <tmpl:/design/averageDesignInformationColumn/>
    </div>


    <div class="column_right bordered" id="average_modal_right">
        <h4><g:message code="design.modal.average.header2"/></h4>
        <div class="inliner bold" style="margin-top:15px; margin-bottom:5px;"><g:message code="totalScore"/>: <span
                id="totalSplitPool" class="inliner">0</span> <span>%</span><br><span
                id="error_designtotal"><g:message code="design.error.total"/></span><span
                id="error_designtotalbelow"><g:message code="design.error.totalbelow100"/></span>
        </div>
        <tmpl:/design/averageDesignShareColumn parentEntity="${entity}" designs="${designsList}" design="${design}"/>
    </div>

</div>


<div class="modal-footer">
    <a class="btn btn-default" href="#" data-dismiss="modal"><g:message code="cancel"/></a>
    <opt:submit parentEntity="${entity}" entity="${design}" name="createAverageDesign"
                value="${message(code: 'createAverageDesign')}" class="btn btn-primary preventDoubleSubmit removeClicksNoOverriding"
                formId="${formId}"/>
</div>

<script>

    $('[rel="popover"]').popover({
        template: '<div class="popover"><div class="arrow"></div><div class="popover-content"></div></div>'
    });

    $(function() {
        //hidden selects should not be affected by maximizing!
        const allSelects = $('select').not('.maskWithSelect2, .maskWithSelect2AllowClear, :hidden');
        if (allSelects) {
            $(allSelects).select2({
                placeholder:'<g:message code="select"/>',
                containerCssClass:":all:"
            }).maximizeSelect2Height();
        }
    })
    $(document).ready(function () {
        $("#error_designtotal").hide()
        $("#error_designtotalbelow").hide()
    });
</script>