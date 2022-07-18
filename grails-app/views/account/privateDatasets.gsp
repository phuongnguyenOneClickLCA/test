<%-- Copyright (c) 2012 by Bionova Oy --%>
<!doctype html>
<%@ page expressionCodec="html" %>
<html>
<head>
    <meta name="layout" content="main">
    <meta name="format-detection" content="telephone=no"/>
    <asset:javascript src="jquery.autocomplete.js?v=${grailsApplication.metadata.'app.version'}"/>
    <g:set var="queryService" bean="queryService"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <h4>
            <opt:link controller="main" removeEntityId="true"><g:message code="main" /></opt:link> > <opt:link action="form" params="[id:account?.id]" ><g:message code="account.title_page"/> </opt:link> > <g:message code="account.private_datasets"/> ${account?.companyName ?: "Unnamed company"}<br/>
        </h4>
      <div class="pull-left">
          <h1>
          <g:message code="account.private_datasets"/> ${account?.companyName ?: "Unnamed company"}
            ${resources ? '(' + resources.size() + ')' : ''}
        </h1></div>
    </div>
</div>
<div class="container section">
    <div id="editPrivateData"></div>
    <sec:ifAnyGranted roles="ROLE_SUPER_USER">
        <div>
            <g:uploadForm action="uploadPrivateDatasetExcel">
                <g:hiddenField name="accountId" value="${account?.id}"/>
                <div class="clearfix"></div>
                <div class="column_left">
                    <div class="control-group">
                        <label for="file" class="control-label"><g:message code="admin.import.excel_file" /><strong> (Super user) </strong></label>
                        <div class="controls"><input type="file" name="xlsFile" id="file" class="btn" value="" /></div>
                    </div>
                </div>

                <div class="clearfix"></div>
                <opt:submit name="import" value="${message(code:'import')}" class="btn btn-primary"/>
                <%--<span id="helpForm" class="fa fa-question greenInfoBubble longcontent" rel="popover" data-trigger="click"  data-html= "true" style="padding:7px;" data-content="<g:message code="help.organization_form"/>"></span>--%>
            </g:uploadForm>
        </div>
    </sec:ifAnyGranted>

    <g:if test="${privateResourceCreationQuery && editable && createPrivateDatasetsAllowed}">
        <g:uploadForm action="createPrivateData" name="privateDataForm">
            <g:hiddenField name="accountId" value="${account?.id?.toString()}"/>
            <div id="validationError"></div>
              <div class="sectionheader">
                <button type="button" class="pull-left sectionexpander" data-name="generalInformation" ><i id="collapseableCreationExpanded" class="icon icon-chevron-right expander"></i></button>

                <g:if test="${editable}">
                    <div class="sectioncontrols pull-right">
                        <a href="javascript:" class="btn btn-primary preventDoubleSubmit" onclick="validateAndSubmitPrivateData('privateDataForm')">${message(code: "save")}</a>
                        <span id="helpForm" class="btn btn-primary longcontent" rel="popover" data-trigger="click"  data-html= "true" data-content="<g:message code="help.organization_form"/>"><i class="fas fa-question"></i></span>
                    </div>
                </g:if>
                <h2 class="h2expander" onclick="toggleSection('collapseableCreationExpanded', 'collapseableCreation')"
                    style="margin-left: 15px;"><g:message code="account.create_private_datasets"/></h2>
               </div>

            <div id="collapseableCreation" class="collapsed" style="display: none;">

                    <g:if test="${privateResourceCreationQuery?.localizedPurpose}">
                        <p><asset:image src="img/infosign_small.png" /> ${queryService.getLocalizedPurpose(privateResourceCreationQuery)}</p>
                    </g:if>

                    <div id="addNewPrivateData">
                        <label for="addNewPrivateData" class="control-label"><strong>Select the type</strong></label>

                       <g:set var="section1" value="${privateResourceCreationQuery?.sections.find({it.sectionId =="privateDataType"})}"/>
                       <g:if test="${privateResourceCreationQuery?.getQuestionsBySection(section1?.sectionId).size() > 0}">
                       <g:set var="question" value="${privateResourceCreationQuery?.getQuestionsBySection(section1?.sectionId).get(0)}"/>
                       </g:if>
                        <select style="max-width: 250px;" id="${section1?.sectionId}.${question?.questionId}" name="${section1?.sectionId}.${question?.questionId}" onchange="showCreatePrivateDataSections($(this).val());">
                           <option value="" selected>select the type</option>

                               <g:each in="${question?.choices}" var="choice">
                                   <option value="${choice.answerId}">${choice.answer.get('EN')}</option>
                               </g:each>

                       </select>
                     <div class="renderCreateSendMeData"></div>
                   </div>
             </div>

        </g:uploadForm>
    </g:if>
</div>

<div class="container section">
    <g:if test="${resources}">
        <div class="sectionheader">
            <button class="pull-left sectionexpander" data-name="generalInformation"><i id="collapseableListExpanded"
                                                                                        class="icon icon-chevron-down expander"></i>
            </button>

            <h2 class="h2expander" onclick="toggleSection('collapseableListExpanded', 'collapseableList')"
                style="margin-left: 15px;"><g:message code="account.private_datasets_header"/></h2>
        </div>

        <div id="collapseableList">
            <g:if test="${resources.find({!it.active})}">
                <table class="resource table table-striped table-condensed" id="resourceTable">
                    <thead>
                    <tr>
                        <th colspan="99"><g:message code="privateDatasets.inactive_datapoints"/></th>
                    </tr>
                    <tr id="inactiveResourceListHeading">
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
                    <g:each in="${resources.findAll({!it.active})}" var="resource">
                        <tr class="resourceRow">
                            <td>${resource.localizedName}</td>
                            <td>
                                ${resource.staticFullName}
                                <sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN">
                                    <queryRender:renderProfilePopup resourceUUID="${resource?.id}" />
                                </sec:ifAllGranted>
                                <opt:renderDataCardBtn resourceId="${resource?.resourceId}" profileId="${resource?.profileId}" infoId="${resource?.id}info"/>
                            </td>
                            <td>${resource.profileId}</td>
                            <td>${resource.impactGWP100_kgCO2e}</td>
                            <td>${resource.importFile}</td>
                            <g:if test="${editable}">
                                <td>
                                    <g:if test="${resource.active}">
                                        <g:link action="disablePrivateDataset" class="btn btn-danger"
                                                params="[resourceId: resource.resourceId, accountId: account?.id]"
                                                onclick="return modalConfirm(this);"
                                                data-questionstr="${message(code: 'disable_dataset')}"
                                                data-truestr="${message(code: 'disable')}"
                                                data-falsestr="${message(code: 'cancel')}"
                                                data-titlestr="${message(code: 'disable')}"><g:message
                                                code="disable"/></g:link>
                                    </g:if>
                                    <g:else>
                                        <g:link action="enablePrivateDataset" class="btn btn-primary"
                                                params="[resourceId: resource.resourceId, accountId: account?.id]"
                                                onclick="return modalConfirm(this);"
                                                data-questionstr="${message(code: 'enable_dataset')}"
                                                data-truestr="${message(code: 'enable')}"
                                                data-falsestr="${message(code: 'cancel')}"
                                                data-titlestr="${message(code: 'enable')}"><g:message
                                                code="enable"/></g:link>
                                    </g:else>
                                </td>
                                <td>
                                    <g:link action="deletePrivateDataset" class="btn btn-danger" id="${resource.id}"
                                            params="[resourceId: resource.id, accountId: account?.id]"
                                            onclick="return modalConfirm(this);"
                                            data-questionstr="${message(code: 'admin.import.resource.delete_question')}"
                                            data-truestr="${message(code: 'delete')}"
                                            data-falsestr="${message(code: 'cancel')}"
                                            data-titlestr="${message(code: 'admin.import.resource.delete_title')}"><g:message
                                            code="delete"/></g:link>
                                </td>
                                <g:if test="${dataProperties}">
                                    <td style="width: 150px;">
                                        <div class="btn-group inliner">
                                            <a href="javascript:" class="dropdown dropdown-toggle bold"
                                               data-toggle="dropdown"><g:message code="privateDatasets.dataProperties"/> <span class="caret"></span></a>
                                            <ul class="dropdown-menu">
                                                <li class="parent_menu"><a href="#"><asset:image
                                                        src="img/icon-warning.png"
                                                        style="max-width:16px"/> <g:message code="privateDatasets.warning_on_dataProps_change"/>
                                                </a></li>
                                                <g:set var="unlistedDataProperties"
                                                       value="${resource.dataProperties?.findAll({ !dataProperties.contains(it) })}"/>
                                                <g:if test="${unlistedDataProperties}">
                                                    <g:each in="${unlistedDataProperties}" var="dataProperty">
                                                        <li class="sub_menu"><opt:link
                                                                action="changeResourceDataProperties" style="color: red"
                                                                params="[remove: true, resourceId: resource.resourceId, dataProperty: dataProperty, accountId: accountId, privateDatasets: true]"><g:message code="privateDatasets.remove_resource"/> ${dataProperty}</opt:link></li>
                                                    </g:each>
                                                </g:if>
                                                <g:each in="${dataProperties}" var="dataProperty">
                                                    <g:if test="${resource.dataProperties?.contains(dataProperty)}">
                                                        <li class="sub_menu"><opt:link
                                                                action="changeResourceDataProperties" style="color: red"
                                                                params="[remove: true, resourceId: resource.resourceId, dataProperty: dataProperty, accountId: accountId, privateDatasets: true]"><g:message code="privateDatasets.remove_resource"/> ${dataProperty}</opt:link></li>
                                                    </g:if>
                                                    <g:else>
                                                        <li class="sub_menu"><opt:link
                                                                action="changeResourceDataProperties"
                                                                params="[dataProperty: dataProperty, resourceId: resource.resourceId, accountId: accountId, privateDatasets: true]"><g:message code="privateDatasets.add_resource"/> ${dataProperty}</opt:link></li>
                                                    </g:else>
                                                </g:each>
                                            </ul>

                                            <g:if test="${!resource.dataProperties}">
                                                <i style="color: orange; font-size: 14px;"
                                                   class="fa fa-exclamation-triangle" href="#" data-toggle="dropdown"
                                                   rel="popover" data-trigger="hover"
                                                   data-content="${message(code: 'privateDatasets.popover_info')}"></i>
                                            </g:if>
                                        </div>
                                    </td>
                                </g:if>
                            </g:if>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </g:if>

            <table class="resource table table-striped table-condensed" id="resourceTable">
                <thead>
                <tr>
                    <th colspan="99"><g:message code="privateDatasets.active_datapoints"/></th>
                </tr>

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
                <g:each in="${resources.findAll({ it.active })}" var="resource">
                    <tr class="resourceRow">
                        <td>${resource.localizedName}</td>
                        <td>
                            ${resource.staticFullName}
                            <sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN">
                                <queryRender:renderProfilePopup resourceUUID="${resource?.id}"/>
                            </sec:ifAllGranted>
                            <opt:renderDataCardBtn resourceId="${resource?.resourceId}" profileId="${resource?.profileId}" infoId="${resource?.id}info"/>
                        </td>
                        <td>${resource.profileId}</td>
                        <td>${resource.impactGWP100_kgCO2e}</td>
                        <td>${resource.importFile}</td>
                        <g:if test="${editable}">
                            <td>
                                <g:if test="${resource.active}">
                                    <g:link action="disablePrivateDataset" class="btn btn-danger"
                                            params="[resourceId: resource.resourceId, accountId: account?.id]"
                                            onclick="return modalConfirm(this);"
                                            data-questionstr="Are you sure you want to disable the dataset?"
                                            data-truestr="${message(code: 'disable')}"
                                            data-falsestr="${message(code: 'cancel')}"
                                            data-titlestr="${message(code: 'disable')}"><g:message
                                            code="disable"/></g:link>
                                </g:if>
                                <g:else>
                                    <g:link action="enablePrivateDataset" class="btn btn-primary"
                                            params="[resourceId: resource.resourceId, accountId: account?.id]"
                                            onclick="return modalConfirm(this);"
                                            data-questionstr="Are you sure you want to enable the dataset?"
                                            data-truestr="${message(code: 'enable')}"
                                            data-falsestr="${message(code: 'cancel')}"
                                            data-titlestr="${message(code: 'enable')}"><g:message
                                            code="enable"/></g:link>
                                </g:else>
                            </td>
                            <td>
                                <g:link action="deletePrivateDataset" class="btn btn-danger" id="${resource.id}"
                                        params="[resourceId: resource.id, accountId: account?.id]"
                                        onclick="return modalConfirm(this);"
                                        data-questionstr="${message(code: 'admin.import.resource.delete_question')}"
                                        data-truestr="${message(code: 'delete')}"
                                        data-falsestr="${message(code: 'cancel')}"
                                        data-titlestr="${message(code: 'admin.import.resource.delete_title')}"><g:message
                                        code="delete"/></g:link>
                            </td>
                            <g:if test="${dataProperties}">
                                <td style="width: 150px;">
                                    <div class="btn-group inliner">
                                        <a href="javascript:" class="dropdown dropdown-toggle bold"
                                           data-toggle="dropdown"><g:message code="privateDatasets.dataProperties"/> <span class="caret"></span></a>
                                        <ul class="dropdown-menu">
                                            <li class="parent_menu"><a href="#"><asset:image src="img/icon-warning.png" style="max-width:16px"/> <g:message code="privateDatasets.warning_on_dataProps_change"/>
                                            </a></li>
                                            <g:set var="unlistedDataProperties"
                                                   value="${resource.dataProperties?.findAll({ !dataProperties.contains(it) })}"/>
                                            <g:if test="${unlistedDataProperties}">
                                                <g:each in="${unlistedDataProperties}" var="dataProperty">
                                                    <li class="sub_menu"><opt:link action="changeResourceDataProperties" style="color: red" params="[remove: true, resourceId: resource.resourceId, dataProperty: dataProperty, accountId: accountId, privateDatasets: true]"><g:message code="privateDatasets.remove_resource"/> ${dataProperty}</opt:link></li>
                                                </g:each>
                                            </g:if>
                                            <g:each in="${dataProperties}" var="dataProperty">
                                                <g:if test="${resource.dataProperties?.contains(dataProperty)}">
                                                    <li class="sub_menu"><opt:link action="changeResourceDataProperties" style="color: red" params="[remove: true, resourceId: resource.resourceId, dataProperty: dataProperty, accountId: accountId, privateDatasets: true]"><g:message code="privateDatasets.remove_resource"/> ${dataProperty}</opt:link></li>
                                                </g:if>
                                                <g:else>
                                                    <li class="sub_menu"><opt:link action="changeResourceDataProperties" params="[dataProperty: dataProperty, resourceId: resource.resourceId, accountId: accountId, privateDatasets: true]"><g:message code="privateDatasets.add_resource"/> ${dataProperty}</opt:link></li>
                                                </g:else>
                                            </g:each>
                                        </ul>

                                        <g:if test="${!resource.dataProperties}">
                                            <i style="color: orange; font-size: 14px;"
                                               class="fa fa-exclamation-triangle" href="#" data-toggle="dropdown"
                                               rel="popover" data-trigger="hover"
                                               data-content="${message(code: 'privateDatasets.popover_info')}"></i>
                                        </g:if>
                                    </div>
                                </td>
                            </g:if>
                        </g:if>
                    </tr>
                </g:each>
                </tbody>
            </table>
        </div>
    </g:if>
</div>
<script type="text/javascript">
    $(function () {
        var popOverSettings = {
            placement: 'top',
            container: 'body',
            html: true,
            template: '<div class="popover popover-fade" style=" display: block; max-width: 220px;"><div class="arrow"></div><div class="popover-content"></div></div>'
        };
        $(".longcontent[rel=popover]").popover(popOverSettings);
        $("a[rel=popover]").popover(popOverSettings);
    });

    $(function () {
        numericInputCheck();
    });

    function validateAndSubmitPrivateData(formId) {
        var regex = /^[a-zA-Z0-9\söÖäÄåÅæÆøØ!?/@#%^&*)(+=.,\-_]+$/g;
        var validationError = $("#validationError");
        validationError.empty();
        var name = $('#descriptiveData\\.nameEN').val();
        // Allow submit with empty name, fails in backend validation
        if (!name || regex.test(name)) {
            $("#" + formId).submit();
            doubleSubmitPrevention(formId)
        } else {
            $("<div id='error' class='alert alert-error'>\n" +
                "  <button data-dismiss='alert' class='close' style=\"top: 0px;\" type='button'>×</button>\n" +
                "  <strong>${message(code: "entity.name")}: ${message(code: "invalid_character")}</strong>\n" +
                "</div>").prependTo(validationError);
        }
    }



    $('#toggleData').bind('click', function () {
        var addNewPrivateData = $('#addNewPrivateData');
        var plusMinus = $('#plusMinus');
        if (!addNewPrivateData.is(":visible")) {
            $(addNewPrivateData).slideDown().removeClass('hidden');
            $(plusMinus).toggleClass('icon-plus icon-minus');
        } else {
            $(addNewPrivateData).slideUp().addClass('hidden');
            $(plusMinus).toggleClass('icon-minus icon-plus');
        }
    });

</script>

</body>
</html>