<%-- Copyright (c) 2012 by Bionova Oy --%>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
        <meta name="format-detection" content="telephone=no"/>
    </head>
	<body>
        <div class="container">
            <h4>
                <opt:link controller="main" removeEntityId="true"><g:message code="main" /></opt:link> >
                <opt:link controller="entity" action="show" id="${entity?.id}">${entity?.name}</opt:link> >
                <g:message code="${task?.id ? 'modify_task' : 'assign_task' }" />
            </h4>
            <div class="screenheader">

                <g:render template="/entity/basicinfoView"/>
            </div>
        </div>

        <div class="container">
            <div class="screenheader"><h1><g:message code="${task?.id ? 'modify_task' : 'assign_task' }" /></h1></div>
        </div>

        <div class="container section">
            <div class="sectionbody">
            <g:form action="save" name="taskForm">
              <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
              <g:hiddenField name="id" value="${task?.id}"/>
              <g:hiddenField name="entityId" value="${entity?.id}" />
              <g:hiddenField name="childEntityId" value="${childEntity?.id}" />
              <g:hiddenField name="queryId" value="${query?.queryId}" />
              <g:hiddenField name="indicatorId" value="${indicatorId}" />
              <g:if test="${fromController}">
                <g:hiddenField name="fromController" value="${fromController}" />
              </g:if>  
              <g:if test="${fromQuery}">
                <g:hiddenField name="fromQuery" value="true" />
              </g:if>
              <div class="clearfix"></div>
	          <div class="column_left">


              <g:if test="${entity}">
                  <div class="control-group">
                      <h4><strong><g:message code="task.target" /></strong> ${entity.name}${childEntity ? ', ' + childEntity.childName : ''}${query ? ', ' + query?.localizedName : ''}</h4>
                  </div>
              </g:if>
		        <div class="control-group">
		          <label for="email" class="control-label"><g:message code="task.email" /></label>
                  <g:if test="${query}"><i><g:message code="task.email.note" /></i></g:if>
                  <div class="controls">
                      <opt:textField entity="${entity}" name="email" value="${task?.email}" class="input-xlarge"/>
                  </div>
		        </div>

		        <div class="control-group">
		          <label for="deadline" class="control-label"><g:message code="task.deadline" /></label>
		          <div class="controls"><div class="input-append"><label><opt:textField entity="${entity}" name="deadline" value="${formatDate(date: task?.deadline, format: 'dd.MM.yyyy')}" class="input-xlarge datepicker" /><span class="add-on"><i class="icon-calendar"></i></span></label></div></div>
		        </div>

		        <div class="control-group">
                  <label class="checkbox">
		            <opt:checkBox entity="${entity}" name="autoReminder" checked="${task?.autoReminder ? true : false}" id="autoReminder" />
		            <g:message code="task.autoReminder" />
	              </label>
		        </div>

                <div class="control-group" style="padding-left: 10px;${!task?.autoReminder ? 'display: none;' : ''}" id="reminderSettings">
                    <label class="checkbox">
                        <opt:checkBox entity="${entity}" name="ccToDeleguer" id="ccToDeleguer" checked="${task?.ccToDeleguer ? true : false}" />
                        <g:message code="task.cc_me" />
                    </label>

                    <label class="control-label"><g:message code="task.reminder_amount" /></label>
                    <opt:select name="reminderAmount" from="${[1,2,3,4]}" value="${task?.reminderAmount}" />

                    <label class="control-label"><g:message code="task.reminder_frequency" /></label>
                    <opt:select name="reminderFrequency" from="${reminderFrequencies}" optionKey="${{it.key}}" optionValue="${{it.value}}" value="${task?.reminderFrequency}" />
                  </div>

                  <div class="control-group">
		          <label for="notes" class="control-label"><g:message code="entity.show.task.description" /></label>
		          <div class="controls"><opt:textArea entity="${entity}" name="notes" value="${task?.notes}" class="input-xlarge" /></div>
		        </div>

		        <g:if test="${task?.id}">
		          <div class="control-group">
		            <label for="performerNotes" class="control-label"><g:message code="task.performerNotes" /></label>
		            <div class="controls"><opt:textArea entity="${entity}" name="performerNotes" value="${task?.performerNotes}" class="input-xlarge" /></div>
		          </div>
		        </g:if>
		        
		        <g:if test="${task?.id}">
		          <div class="control-group">
		            &nbsp;
		            <div class="controls">
		              <label class="checkbox"><opt:checkBox entity="${entity}" name="completed" value="true" checked="${task?.completed}" /> <g:message code="task.completed" /></label>
		            </div>
	              </div>
	            </g:if>		        
		    </div>
	  
  	        <div class="clearfix"></div>
	
	        <opt:submit entity="${entity}" name="save" value="${message(code:'save')}" class="btn btn-primary" formId="taskForm" /> 
	        <opt:link action="cancel" class="btn" params="[entityId:entity?.id, queryId:query?.queryId, indicatorId:indicatorId, fromQuery:fromQuery, fromController:fromController]"><g:message code="cancel" /></opt:link>
	        <g:if test="${task?.removable}">
			  <opt:link action="remove" id="${task.id}" params="[childEntityId:childEntity?.id, queryId:query?.queryId, indicatorId:indicatorId]" class="btn btn-danger"  onclick="return modalConfirm(this);" 
			    data-questionstr="${message(code:'task.delete')}" data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}" data-titlestr="${message(code:'task.delete_confirm.header')}"><g:message code="delete" /></opt:link>
			</g:if>
	      </g:form>
          </div>
        </div>
        <script type="text/javascript">
            $(document).ready(function() {
                $('#autoReminder').on('click', function (event) {
                    var isChecked = $(this).is(':checked');
                    var element = document.getElementById("reminderSettings");

                    if(isChecked) {
                        element.style.display = 'block';
                    } else {
                        element.style.display = 'none';
                    }
                });

                $("#deadline").datepicker({
                    dateFormat: 'dd.mm.yy',
                    minDate: 0,
                    maxDate: 60
                });
            });


        </script>

    </body>
</html>



