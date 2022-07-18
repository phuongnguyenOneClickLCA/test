<div class="dataTables_filter" style="height: 160px;">
  <div class="tab-content">
    <div class="column_left middle-margins">
      <fieldset>
        <div class="control-group">
          <label for="resourceId" class="control-label bold">
            <g:message code="admin.importMapperTrainingData.label.resouceId" default="Resource Id"/>:
          </label>
          <div class="controls">
            <g:textField class="input-xlarge" value="${searchFilter.resourceId}" name="resourceId"/>
          </div>
        </div>

        <div class="control-group">
          <label for="resourceStatus" class="control-label bold">
            <g:message code="admin.importMapperTrainingData.label.resouceStatus" default="Resource status"/>:
          </label>
          <div class="controls">
            <select id="resourceStatus" name="resourceStatus" class="input-xlarge">
              <option value="" ${searchFilter.resourceStatus == null ? "selected" : ""}>
                <g:message code="admin.importMapperTrainingData.allStatuses" default="All"/>
              </option>
              <option value="true" ${searchFilter.resourceStatus == true ? "selected" : ""}>
                <g:message code="admin.importMapperTrainingData.activeStatus" default="Active"/>
              </option>
              <option value="false" ${searchFilter.resourceStatus == false ? "selected" : ""}>
                <g:message code="admin.importMapperTrainingData.inactiveStatus" default="Inactive"/>
              </option>
            </select>
          </div>
        </div>
      </fieldset>

      <a href="javascript:" class="btn btn-primary" onclick="filter('trainingData');">
        <g:message code="admin.importMapperTrainingData.button.filter" default="Filter"/>
      </a>
      <a href="javascript:" class="btn" onclick="clearFilters();">
        <g:message code="admin.importMapperTrainingData.button.clearFilters" default="Clear filters"/>
      </a>
    </div>

    <div class="column_left middle-margins">
      <fieldset>
        <div class="control-group">
          <label for="profileId" class="control-label bold">
            <g:message code="admin.importMapperTrainingData.label.profileId" default="Profile Id"/>:
          </label>
          <div class="controls">
            <g:textField class="input-xlarge" value="${searchFilter.profileId}" name="profileId"/>
          </div>
        </div>

        <g:if test="${availableSubTypes}">
          <div class="control-group">
            <label for="resourceSubType" class="control-label bold">
              <g:message code="admin.importMapperTrainingData.label.resourceSubType" default="Resource sub type"/>:
            </label>
            <div class="controls">
              <g:select name="resourceSubType" class="input-xlarge" from="${availableSubTypes}" value="${searchFilter.resourceSubType}"
                        noSelection="${['': g.message(code: 'admin.importMapperTrainingData.select.selectOne', default: 'Select one...')]}"/>
            </div>
          </div>
        </g:if>
      </fieldset>
    </div>

    <div class="column_left middle-margins">
      <fieldset>
        <div class="control-group">
          <label for="importMapperId" class="control-label bold">
            <g:message code="admin.importMapperTrainingData.label.importMapperId" default="Import mapper Id"/>:
          </label>
          <div class="controls">
            <g:textField class="input-xlarge" value="${searchFilter.importMapperId}" name="importMapperId"/>
          </div>
        </div>

        <g:if test="${availableApplications}">
          <div class="control-group">
            <label for="applicationId" class="control-label bold">
              <g:message code="admin.importMapperTrainingData.label.applicationId" default="Application Id"/>:
            </label>
            <div class="controls">
              <g:select name="applicationId" class="input-xlarge" from="${availableApplications}" value="${searchFilter.applicationId}"
                        noSelection="${['': g.message(code: 'admin.importMapperTrainingData.select.selectOne', default: 'Select one...')]}"/>
            </div>
          </div>
        </g:if>
      </fieldset>
    </div>
  </div>
</div>

<script>
  function clearFilters() {
      $('select#applicationId')[0].selectedIndex = 0;
      $('input#resourceId').val("");
      $('input#profileId').val("");
      $('input#importMapperId').val("");
      $('select#resourceStatus')[0].selectedIndex = 0;
      $('select#resourceSubType')[0].selectedIndex = 0;

      return false;
  }

  function filter(elementId) {
      let spinner = $('div#loader');
      let element = $('#' + elementId);
      let params = {
          applicationId:  $('select#applicationId').val(),
          resourceId: $('input#resourceId').val(),
          profileId: $('input#profileId').val(),
          importMapperId: $('input#importMapperId').val(),
          resourceStatus: $('select#resourceStatus').val(),
          resourceSubType: $('select#resourceSubType').val()
      };

      spinner.removeClass('hidden');
      $('div.sectionbody').addClass('hidden');

      $.ajax({
          type: 'POST',
          url: '${g.createLink(action: "filter")}',
          data: params,
          success: function (data) {
              element.html(data);
          },
          error: function (XMLHttpRequest, textStatus, errorThrown) {
              element.html("${g.message(code: "error.general")}");
              console.log("ajax error: " + XMLHttpRequest.status + " " + errorThrown);
          },
          complete: function () {
              spinner.addClass('hidden');
          }
      });
  }
</script>
