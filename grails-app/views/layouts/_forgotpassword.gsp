<div class="modal hide fade" id="forgotpassword">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">×</button>
    <h2><g:message code="index.forgot_password" /></h2>
  </div>
  <div class="modal-body">
    <div class="clearfix"></div>
    <div class="tab-content">
        <g:form controller="passwordReset" action="sendResetPasswordMail" useToken="true">
          <fieldset>
            <div class="control-group">
              <label><g:message code="forgotpassword.email" /></label>
              <div class="controls">
                 <g:textField name="email" class="input-xlarge" />
              </div>
            </div>
          </fieldset>
          <div class="modal-footer"> 
	        <opt:submit name="submit" value="${message(code:'send')}" class="btn btn-primary" />
            <a href="#" class="btn" data-dismiss="modal"><g:message code="cancel" /></a> 
          </div>
        </g:form>
    </div>
  </div>
</div>