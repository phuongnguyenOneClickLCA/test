<%@ page import="groovy.json.JsonBuilder" %>
<%@ page import="static com.bionova.optimi.construction.controller.admin.AdaptiveRecognitionDataController.PAGE_LIMIT_VALUES as PAGE_LIMIT_VALUES" %>

<g:render template="blocks/trainingDataFilters"
          model="[searchFilter: searchFilter, availableSubTypes: availableSubTypes, availableApplications: availableApplications]"/>

<div id="loader" class="hidden">
  <g:render template="blocks/spinner"/>
</div>

<div class="sectionbody">
  <p>&nbsp;</p>
  <h4><g:message code="admin.importMapperTrainingData.title.trainingData" default="Training data ({0})"
                 args="${[totalNumberOfRecords]}"/></h4>
  <table id="activeData" class="table table-striped table-condensed">
    <thead>
    <tr>
      <th class="no-sort">
        <a href="javascript:" class="btn btn-danger" onclick="deleteChecked('activeData');">
          <g:message code="admin.importMapperTrainingData.button.deleteChecked" default="Delete checked"/>
        </a>
        <a href="javascript:" class="btn btn-primary" onclick="checkAll('activeData');">
          <g:message code="admin.importMapperTrainingData.button.checkAll" default="Check all"/>
        </a>
      </th>
      <th><g:message code="admin.importMapperTrainingData.extraData"/></th>
      <th><g:message code="admin.importMapperTrainingData.resourceId"/></th>
      <th><g:message code="admin.importMapperTrainingData.resourceStatus" default="Resource status"/></th>
      <th><g:message code="admin.importMapperTrainingData.resourceSubType"
                     default="Resource sub type"/></th> <%-- !!! Subtype needs to be on column 3 ! or change JS below --%>
      <th><g:message code="admin.importMapperTrainingData.applicationId"/></th>
      <th><g:message code="admin.importMapperTrainingData.trainingData"/></th>
      <th><g:message code="admin.importMapperTrainingData.location" default="Location"/></th>
      <th><g:message code="admin.importMapperTrainingData.username" default="Username"/></th>
      <th><g:message code="admin.importMapperTrainingData.time"/></th>
      <th class="no-sort">&nbsp;</th>
      <th class="no-sort">&nbsp;</th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${trainingDatas}" var="trainingData">
      <tr>
        <td>
          <input type="checkbox" data-active="true" class="delete" data-id="${trainingData.id.toString()}"/>
        </td>
        <td>
          ${trainingData.incomingKey}
        </td>
        <td>
          ${trainingData.resourceId} / ${trainingData.profileId}
          <a href="javascript:"
             onclick="window.open('${g.createLink(controller: "import", action: "showData", params: [resourceId: trainingData.resourceId, profileId: trainingData.profileId])}',
                 '_blank', 'width=1024, height=768, scrollbars=1');">
            <i class="fas fa-search-plus"></i>
          </a>
        </td>
        <td>
          <g:if test="${trainingData.active == null}">
            -
          </g:if>
          <g:else>
            ${trainingData.active ? g.message(code: "admin.importMapperTrainingData.activeStatus", default: "Active") :
                                    g.message(code: "admin.importMapperTrainingData.inactiveStatus", default: "Inactive")}
          </g:else>
        </td>
        <td>
          ${trainingData.resourceSubType}
        </td>
        <td>
          ${trainingData.applicationId} / ${trainingData.importMapperId}
        </td>
        <td>
          <a href="javascript:" class="tableHeadingPopover" rel="popover" data-trigger="click" data-content="${new JsonBuilder(trainingData.trainingData).toPrettyString().replace('"', '\'')}">
            <g:message code="admin.importMapperTrainingData.trainingData" default="Training data"/>
          </a>
        </td>
        <td>
          ${trainingData.userLocale}
        </td>
        <td>
          ${trainingData.username}
        </td>
        <td>
          <span style="display:none;">${trainingData.timeForSort}</span>${trainingData.time}
        </td>
        <td>
          <a href="${g.createLink(controller: 'adaptiveRecognitionData', action: 'form', params: [id: trainingData.remapId])}" class="btn btn-primary">
            <g:message code="admin.importMapperTrainingData.button.remap" default="Remap"/>
          </a>
        </td>
        <td>
          <a href="javascript:" class="btn btn-danger" onclick="deleteTrainingData('${trainingData.deleteId}', this)">
            <g:message code="admin.importMapperTrainingData.button.delete" default="Delete"/>
          </a>
        </td>
      </tr>
    </g:each>
    </tbody>
  </table>

  <div class="paginateButtons">
    <util:remotePaginate controller="adaptiveRecognitionData" action="filter" total="${totalNumberOfRecords}" params="${params}"
                         update="trainingData" max="10"
                         pageSizes="${PAGE_LIMIT_VALUES.collectEntries { int number -> [(number): g.message(code: "pagination.label.resultsPerPage", default: number + " per page", args: [number])] }}"
                         onLoading="onLoading();" onComplete="onComplete();"/>
  </div>
</div>

<script type="text/javascript">
    $(function () {
        var helpPopSettings = {
            placement: 'top',
            template: '<div class="popover popover-fade" style="display: block; max-width: 300px;"><div class="arrow"></div><div style="font-weight: normal !important;" class="popover-content"></div></div>'
        };

        $(".tableHeadingPopover[rel=popover]").popover(helpPopSettings);
    });

    function onLoading() {
        $('div.sectionbody').addClass('hidden');
        $('div#loader').removeClass('hidden');
    }

    function onComplete() {
        $('div.sectionbody').removeClass('hidden');
        $('div#loader').addClass('hidden');
    }
</script>
