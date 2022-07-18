<g:if test="${isCompatible}">
    <g:if test="${requiredWarning}">
        <div class="alert alert-error alert-scope">
            <i class="fas fa-times pull-left" style="font-size: large; margin-right: 8px;"></i>
            <button data-dismiss="alert" class="close" type="button">×</button>
            <strong>${requiredWarning}</strong>
        </div>
    </g:if>
    <g:if test="${recommendWarning}">
        <div class="alert alert-warning alert-scope">
            <i class="fas fa-exclamation pull-left" style="font-size: large; margin-right: 8px;"></i>
            <button data-dismiss="alert" class="close" type="button">×</button>
            <strong>${recommendWarning}</strong>
        </div>
    </g:if>
    <g:if test="${successScopeCompleted}">
        <div class="alert alert-success alert-scope">
            <i class="far fa-thumbs-up pull-left" style="font-size: large; margin-right: 8px;"></i>
            <button data-dismiss="alert" class="close" type="button">×</button>
            <strong>${successScopeCompleted}</strong>
        </div>
    </g:if>
</g:if>
<g:elseif test="${scopeMessageWarning}">
    <div class="alert alert-warning alert-scope">
        <i class="fas fa-exclamation pull-left" style="font-size: large; margin-right: 8px;"></i>
        <button data-dismiss="alert" class="close" type="button">×</button>
        <strong>${scopeMessageWarning}</strong>
    </div>
</g:elseif>


