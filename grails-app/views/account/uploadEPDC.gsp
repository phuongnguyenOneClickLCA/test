<%-- Copyright (c) 2012 by Bionova Oy --%>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<%@ page import="com.bionova.optimi.core.service.OptimiResourceService" %>
<%
    OptimiResourceService optimiResourceService = grailsApplication.mainContext.getBean("optimiResourceService")
%>
<div class="container">
    <div class="screenheader">
        <h4>
            <opt:link controller="main" removeEntityId="true"><g:message code="main" /></opt:link> > <opt:link action="form" params="[id:account?.id]" ><g:message code="account.title_page"/> </opt:link> > Upload custom EPD
        </h4>

      <div class="pull-left">  <h1>
          <g:message code="account.upload_xml_for_company"/>: ${account?.companyName ?: "Unnamed company"}
        </h1></div>

       </div>
    </div>
</div>
<div class="container section">
    <div class="pull-left">
        <g:uploadForm controller="xmlHandler" action="uploadEPDCXmlForAccount">
            <g:hiddenField name="accountId" value="${account?.id}"/>
            <g:hiddenField name="classificationParameterId" value="${classificationParameterId}" />
            <g:hiddenField name="classificationQuestionId" value="${classificationQuestionId}" />

            <div class="clearfix"></div>
            <div class="column_left">
                <div class="control-group">
                    <div id="selectFile" class="btn btn-primary"><g:message code="btn.select_xml" /></div>
                    <input type="file" name="xmlFile" id="uploadXMLFile" value="" accept="text/xml" style="display: none" />
                    <div style="display: inline-block" id="fileName"><g:message code="no_selected_files" /></div>
                </div>
            </div>
            <div class="clearfix"></div>
            <opt:submit name="import" value="${message(code:'import')}" class="btn btn-primary"/>
            <span id="helpForm" class="fa fa-question greenInfoBubble longcontent" rel="popover" data-trigger="click" data-html= "true" style="padding:7px;" data-content="<g:message code="help.organization_form"/>"></span>
        </g:uploadForm>
    </div>
</div>
<div class="container section">
            <g:if test="${resources}">
                <table class="resource table table-striped table-condensed" id="resourceTable">
                    <thead>
                    <tr id="resourceListHeading">
                        <th><g:message code="resource.name"/></th>
                        <th><g:message code="privateDatasets.resource_full_name"/></th>
                        <th><g:message code="resource.profile"/></th>
                        <th><g:message code="privateDatasets.gwp"/></th>
                        <th><g:message code="privateDatasets.file_source"/></th>
                        <g:if test="${editable}">
                            <th><g:message code="btn.enable_disable"/></th>
                            <th><g:message code="delete"/></th>
                            <g:if test="${dataProperties}">
                                <th style="width: 150px;"><g:message code="privateDatasets.add_remove_dataProperties"/></th>
                            </g:if>
                        </g:if>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${resources}" var="resource">
                        <tr class="resourceRow">
                            <td>
                                ${optimiResourceService.getLocalizedName(resource)}
                                <sec:ifAnyGranted roles="ROLE_DATA_MGR">
                                    <queryRender:renderProfilePopup resourceUUID="${resource?.id}" />
                                </sec:ifAnyGranted>
                                <opt:renderDataCardBtn resourceId="${resource?.resourceId}" profileId="${resource?.profileId}" infoId="${resource?.id}info"/>
                            </td>
                            <td>${resource?.staticFullName}</td>
                            <td>${resource?.unitForData}</td>
                            <td>${resource?.profileId}</td>
                            <td>${resource?.impactGWP100_kgCO2e}</td>
                            <td>${resource?.importFile}</td>
                            <g:if test="${editable}">
                                <td>
                                    <g:link action="deletePrivateDataset" class="btn btn-danger" id="${resource.id}" params="[resourceId:resource.id, accountId: account?.id, privateEpdDelete:true, classificationParameterId:classificationParameterId, classificationQuestionId:classificationQuestionId]" onclick="return modalConfirm(this);" data-questionstr="${message(code:'admin.import.resource.delete_question')}"
                                            data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}" data-titlestr="${message(code:'admin.import.resource.delete_title')}"><g:message code="delete" /></g:link>
                                </td>
                                <g:if test="${dataProperties}">
                                    <td style="width: 150px;">
                                        <div class="btn-group inliner">
                                            <a href="javascript:" class="dropdown dropdown-toggle bold" data-toggle="dropdown"><g:message code="privateDatasets.dataProperties"/> <span class="caret"></span></a>
                                            <ul class="dropdown-menu">
                                                <li class="parent_menu"><a href="#"><asset:image src="img/icon-warning.png" style="max-width:16px"/> <g:message code="privateDatasets.warning_on_dataProps_change"/></a></li>
                                                <g:set var="unlistedDataProperties" value="${resource.dataProperties?.findAll({!dataProperties.contains(it)})}" />
                                                <g:if test="${unlistedDataProperties}">
                                                    <g:each in="${unlistedDataProperties}" var="dataProperty">
                                                        <li class="sub_menu"><opt:link action="changeResourceDataProperties" style="color: red" params="[remove: true, resourceId: resource.resourceId, dataProperty: dataProperty, accountId: accountId, classificationParameterId:classificationParameterId, classificationQuestionId:classificationQuestionId]"><g:message code="privateDatasets.remove_resource"/> ${dataProperty}</opt:link></li>
                                                    </g:each>
                                                </g:if>
                                                <g:each in="${dataProperties}" var="dataProperty">
                                                    <g:if test="${resource.dataProperties?.contains(dataProperty)}">
                                                        <li class="sub_menu"><opt:link action="changeResourceDataProperties" style="color: red" params="[remove: true, resourceId: resource.resourceId, dataProperty: dataProperty, accountId: accountId, classificationParameterId:classificationParameterId, classificationQuestionId:classificationQuestionId]"><g:message code="privateDatasets.remove_resource"/> ${dataProperty}</opt:link></li>
                                                    </g:if>
                                                    <g:else>
                                                        <li class="sub_menu"><opt:link action="changeResourceDataProperties" params="[dataProperty: dataProperty, resourceId: resource.resourceId, accountId: accountId, classificationParameterId:classificationParameterId, classificationQuestionId:classificationQuestionId]"><g:message code="privateDatasets.add_resource"/> ${dataProperty}</opt:link></li>
                                                    </g:else>
                                                </g:each>
                                            </ul>
                                        </div>
                                    </td>
                                </g:if>
                            </g:if>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </g:if>
</div>
<script type="text/javascript">
    $(function () {
        var popOverSettings = {
            placement: 'top',
            container: 'body',
            html: true,
            template: '<div class="popover popover-fade" style=" display: block; max-width: 180px;"><div class="arrow"></div><div class="popover-content"></div></div>'
        };
        $(".longcontent[rel=popover]").popover(popOverSettings);
        $('.dropdown-toggle').dropdown();
    });

    $('#selectFile').click(function() {
        $('#uploadXMLFile').trigger('click');
        $('#uploadXMLFile').change(function() {
            var filename = $('#uploadXMLFile').val();
            if (filename.substring(3,11) == 'fakepath') filename = filename.substring(12) // Remove c:\fake at beginning from localhost chrome
            $('#fileName').html(filename);
        });
    });
</script>
</body>
</html>