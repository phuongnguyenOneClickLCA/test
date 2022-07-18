    <div class="modal" id="copyentities">
        <div class="modal-header">
            <button type="button" class="close" onclick="$('#copyentities').remove();">×</button>
            <h2><g:message code="query.copy_answers.title" /></h2>
        </div>
        <div class="modal-body">
            <p>
                <g:message code="query.copy_answers.notice" />
            </p>
            <div class="clearfix"></div>
            <div class="tab-content">
                <div class="tab-pane active" id="tab-details">
                    <g:form class="form-horizontal" controller="query" action="copyAnswers" id="copyAnwersForm">
                        <g:hiddenField name="targetEntityId" value="${targetEntityId}" />
                        <g:hiddenField name="entityId" value="${entityId}" />
                        <g:hiddenField name="queryId" value="${queryId}" />
                        <g:hiddenField name="indicatorId" value="${indicatorId}" />
                        <g:select name="fromEntityId" from="${toCopyEntities}" optionKey="id" optionValue="${{it.parentName + " - " + it.operatingPeriodAndName}}"  />
                        <g:submitButton id="select" name="${message(code:'select')}" class="btn btn-primary" />
                    </g:form>
                </div>
            </div>
        </div>
    </div>

