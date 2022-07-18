<%-- Copyright (c) 2012 by Bionova Oy --%>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
        <meta name="format-detection" content="telephone=no"/>
        <style type="text/css">
        @media only screen and (min-width: 1600px) {

            .container, .section {
                width: 90%;
            }

        }

        </style>
    </head>
	<body>

        <div class="container">
            <div class="screenheader"><h1><g:message code="querytask.given_task" /></h1></div>
        </div>

        <div class="container section">
            <div class="sectionbody">
                  <g:form action="savetask" useToken="true" name="taskForm">
                  <g:hiddenField name="id" value="${task?.id}" />
                  <g:hiddenField name="token" value="${token}" />
                  <div class="clearfix"></div>
                  <div class="column_left">

                    <div class="control-group">
                      <label for="email" class="control-label"><g:message code="task.email" /></label>
                      <div class="controls"><g:textField disabled="disabled" name="email" value="${task?.email}" class="input-xlarge"/></div>
                    </div>

                    <div class="control-group">
                      <label for="deadline" class="control-label"><g:message code="task.deadline" /></label>
                      <div class="controls"><div class="input-append"><label><g:textField disabled="disabled" name="deadline" value="${formatDate(date: task?.deadline, format: 'dd.MM.yyyy')}" class="input-xlarge datepicker" /><span class="add-on"><i class="icon-calendar"></i></span></label></div></div>
                    </div>

                    <div class="control-group">
                      <label for="notes" class="control-label"><g:message code="entity.show.task.description" /></label>
                      <div class="controls"><g:textArea disabled="disabled" name="notes" value="${task?.notes}" class="input-xlarge" /></div>
                    </div>

                    <div class="control-group">
                      <label for="performerNotes" class="control-label"><g:message code="task.performerNotes" /></label>
                      <div class="controls"><g:textArea name="performerNotes" value="${task?.performerNotes}" class="input-xlarge" disabled="${task?.id ? false : true}" /></div>
                    </div>

                    <g:if test="${task?.id}">
                      <div class="control-group">
                        &nbsp;
                        <div class="controls">
                          <label class="checkbox"><g:checkBox name="completed" value="true" checked="${task?.completed}" /> <g:message code="task.completed" /></label>
                        </div>
                      </div>
                   </g:if>
                </div>

                <div class="clearfix"></div>

                <opt:submit name="save" value="${message(code:'save')}" class="btn btn-primary" formId="taskForm" />
                <g:actionSubmit value="${message(code:'leave')}" action="leave" class="btn" />
                </g:form>
            </div>
        </div>
    </body>
</html>

