<form action="/app/sec/account/updatePrivateData" method="post" name="privateDataForm" enctype="multipart/form-data" id="privateDataForm">
    <input type="hidden" value="${resourceId}" name="resourceId">
    <input type="hidden" value="${accountId}" name="accountId">
<div class="modal modalDesignScope">
  <div class="modal-dialog">
      <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">×</button>
          <h2>
              Modify private data
          </h2>
      </div>
      <div class="modal-body">

          <g:if test="${privateResourceCreationQuery?.localizedPurpose}">
              <p><asset:image src="img/infosign_small.png" /> ${privateResourceCreationQuery.localizedPurpose}</p>
          </g:if>
      <g:set var="sectionNumber" value="${0}"/>
      <g:each in="${privateResourceCreationQuery?.sections}" var="section">
          <g:if test="${!section?.sectionId?.equalsIgnoreCase("privateDataType")}">
          <g:render template="/query/section" model="[query: privateResourceCreationQuery, section: section, display: true, isMain: true,
                                                      sectionNumber: ++sectionNumber, modifiable: true, preventChanges: false ,showMandatory : true, entityClassResource : entityClassResource]"/>
          </g:if>
      </g:each>

        </div>
      <div class="modal-footer">
          <a class="btn btn-default" href="#" data-dismiss="modal">Cancel</a>
     <%--    <input type="submit" style="pointer-events: none;" entity="" name="saveData" value="Save" class="btn btn-primary preventDoubleSubmit disabled" formid="privateDataForm" onclick="doubleSubmitPrevention('saveData');" id="saveData">--%>
         <%-- <input type="submit" name="saveData" value="Save" class="btn btn-primary preventDoubleSubmit" onclick="createPrivateData('${resourceId}','${accountId}');" id="saveData">--%>
          <a href="javascript:" class="btn btn-primary preventDoubleSubmit" onclick="validateAndSubmitPrivateData('privateDataForm')">Save</a>
      </div>


</div>
</div>
</form>