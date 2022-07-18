<div class="modal hide fade bigModal" id="${modalId}">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">×</button>
    <h2><g:message code="entity.add_benchmark.header" /></h2>
  </div>
  <div class="modal-body">
    <div class="clearfix"></div>
    <div class="tab-content">
	  <div class="tab-pane active" id="tab-details">
        <g:if test="${portfolios}">
        <g:form action="#" useToken="true">
          <g:hiddenField name="entityId" value="${entity?.id}" />
          <g:hiddenField name="type" value="${type}"/>
          <fieldset>
            <div class="control-group">
              <div class="controls">
                <table class="indicators">
                  <g:each in="${portfolios}" var="portfolio">
                    <tr>
                      <td>
                        <opt:checkBox entity="${entity}" name="portfolioId" value="${portfolio.id}" checked="false" /> 
                        <strong>${portfolio.entityName}</strong>
                      </td>
                    </tr>
                  </g:each>
                </table>
              </div>
            </div>
          </fieldset>
          <div class="modal-footer"> 
	        <opt:actionSubmit entity="${entity}" action="addBenchmarks" value="${message(code:'save')}" class="btn btn-primary"/> 
            <a href="#" class="btn" data-dismiss="modal"><g:message code="cancel" /></a> 
          </div>
        </g:form>
        </g:if>
        <g:else>
          <div class="alert alert-success"><g:message code="entity.no_portfolios" /></div>
        </g:else>
      </div>
    </div>
  </div>
</div>