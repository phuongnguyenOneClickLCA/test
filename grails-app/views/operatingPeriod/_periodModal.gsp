<g:hiddenField name="parentEntityId" value="${parentEntity?.id}" />
<g:if test="${isCopyPeriod != true}"><g:hiddenField name="id" value="${operatingPeriod?.id}"/></g:if>
<g:hiddenField name="entityId" value="${operatingPeriod?.id}"/>
<g:if test="${isCopyPeriod == true}"><g:hiddenField name="originalOperatingPeriodId" value="${operatingPeriod?.id}" /></g:if>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">×</button>
    <h2>
        <g:if test="${isModifyPeriod == true}">
            <g:message code="period.modifyPeriod" />
        </g:if>
        <g:else>
            <g:if test="${isCopyPeriod == true}">
                <g:message code="period.copyPeriod" />
            </g:if>
            <g:else>
                <g:message code="period.createPeriod" />
            </g:else>
        </g:else>
    </h2>
</div>
<div class="modal-body">
    <div class="clearfix"></div>
    <div class="column_left">
        <div class="control-group">
            <label for="operatingPeriod" class="control-label"><g:message code="entity.operatingPeriod" /></label>
            <div class="controls">
                <opt:select entity="${entity}" name="operatingPeriod" value="${operatingPeriod?.operatingPeriod ? operatingPeriod.operatingPeriod : originalOperatingPeriod?.operatingPeriod}" from="${choosableYears}" noSelection="['':'']" />
            </div>
        </div>
        <div class="control-group">
            <label for="name" class="control-label"><g:message code="entity.operatingPeriod.name" /></label>
            <div class="controls"><opt:textField entity="${entity}" name="name" value="${operatingPeriod?.name}" class="input-xlarge" /></div>
        </div>
    </div>
    <div class="clearfix"></div>
</div>
<div class="column_right bordered">

</div>
<div class="modal-footer">
    <a class="btn btn-default" href="#" data-dismiss="modal">Cancel</a>
    <opt:submit entity="${parentEntity}" name="save" value="${operatingPeriod?.id ? message(code:'save') : message(code:'add')}" class="btn btn-primary"/>
</div>

<script>

    $('[rel="popover"]').popover({
        template: '<div class="popover"><div class="arrow"></div><div class="popover-content"></div></div>'
    });

</script>