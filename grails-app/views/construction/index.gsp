<%
    def userService = grailsApplication.mainContext.getBean("userService")
%>
<!DOCTYPE html>
<%@ page import="com.bionova.optimi.core.service.ConstructionService" %>
<%
    ConstructionService constructionService = grailsApplication.mainContext.getBean("constructionService")
%>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<g:set var="isSystemAdminOrDataManagerOrSuperUser" value="${userService.isSystemAdminOrSuperUserOrDataManagerOrDeveloper(user)}"/>
<g:if test="${group}">
    <constructionRender:missMatchDataAlert group="${group}" />
    <constructionRender:inactiveDataAlert group="${group}" />

</g:if>
<div class="container">
    <div class="screenheader">
        <h4>
            <opt:link controller="main" removeEntityId="true"><g:message code="main" /></opt:link> > <opt:link controller="account" action="form" params="[id:account?.id]" ><g:message code="account.title_page"/> </opt:link> > <g:message code="construction.manage"/><br/>
        </h4>
        <h1><g:message code="construction.manage"/> <g:if test="${group}">in ${group.name} </g:if><g:elseif test="${ungrouped}"> ungrouped</g:elseif> </h1>
    </div>
</div>
<div class="container">
    <div class="sectionbody">
        <div class="wrapperForNewConstructions container">
            <div class="btn-group">
            <g:if test="${group || account}">
                <a href="javascript:" class="dropdown-toggle btn btn-primary" data-toggle="dropdown"><i class="icon icon-white icon-plus"></i>  <g:message code="construction.add_new"/></a>
                <ul class="dropdown-menu" style="max-width:48%">
                    <g:if test="${classificationParams}">
                        <g:each in="${classificationParams}" var="classificationParam">
                            <sec:ifAnyGranted roles="${classificationParam.userClass ?: "ROLE_AUTHENTICATED"}">
                                <li><opt:link action="form" params="[classificationQuestionId:classificationParameterQuestion.questionId, classificationParamId:classificationParam?.answerId, accountId:account?.id, group:group?.id]">${classificationParam?.getLocalizedAnswer()}${classificationParam?.userClass && classificationParam?.userClass != "ROLE_AUTHENTICATED" ? " (Superuser)" : ""}</opt:link></li>
                            </sec:ifAnyGranted>
                        </g:each>
                    </g:if>
                </ul>
                <g:if test="${isSystemAdminOrDataManagerOrSuperUser}">
                    <a href="javascript:" class="btn btn-primary" id="importConstructions"><g:message code="import"/> <i id="toggler" class="icon icon-white icon-plus"></i></a>
                </g:if>
                <span id="helpForm" class="success"><g:message code="help.organization_form"/></span>
            </g:if>
                <g:if test="${!account}">
                    <opt:link action="constructionsManagementPage" class="btn pull-right"><g:message code="back"/></opt:link>
                </g:if>

            </div>
            <br>
            <g:if test="${account || isSystemAdminOrDataManagerOrSuperUser}">
                <g:uploadForm action="importConstructions" name="importingForm" class="hidden">
                    <g:hiddenField name="group" value="${group?.id}"/>
                    <g:hiddenField name="queryId" value="${queryId}"/>
                    <g:hiddenField name="accountId" value="${account?.id}"/>
                    <g:hiddenField name="accountName" value="${account?.companyName}"/>
                    <div class="clearfix"></div>

                    <div class="column_left">
                        <div class="control-group">
                            <label for="file" class="control-label"><g:message code="admin.import.excel_file"/></label>

                            <div class="controls"><input type="file" name="xlsFile" id="file" class="btn" value=""/>
                            </div>
                        </div>

                        <g:if test="${isSystemAdminOrDataManagerOrSuperUser}">
                            <div class="control-group">
                                <label for="file" class="control-label">
                                    I am uploading NMD constructions
                                    <span class="triggerPopover" style="padding-left: 5px"
                                          data-content="${message(code: 'nmdConstructionUpload.reminder.help')}">
                                        <i class="icon-question-sign"></i>
                                    </span>
                                    <g:checkBox name="isUploadingNmd"/>
                                </label>
                            </div>
                        </g:if>
                    </div>

                    <div class="clearfix"></div>
                    <opt:submit name="import" value="${message(code: 'import')}" class="btn btn-primary"/>
                </g:uploadForm>
            </g:if>
        </div>
        <div class="wrapperForExistingConstructions container">

            <h1><g:message code="construction.existing_constructions"/></h1>
           <div class="btn-group">
               <a href="javascript:" id="selectAll" ${constructions ? 'working' : 'disabled'} class="btn btn-primary"><g:message code="ifc.select.all" /> </a>
               <a href="javascript:" id="disableSelected" ${constructions ? 'working' : 'disabled'} class="btn btn-warning"> <g:message code="btn.disable_selected" /> </a>
               <g:if test="${editable}">
                   <a href="javascript:" id="deleteSelected" ${constructions ? 'working' : 'disabled'} class="btn btn-danger"> <g:message code="ifc.delete.selected"/> </a>
               </g:if>
               <g:if test="${group && userService.getSuperUser(user)}"><a href="javascript:" id="removeSelectedFromGroup" ${constructions ? 'working' : 'disabled'}  class="btn btn-info"> <g:message code="btn.remove_from_group" /> </a> </g:if>
               <g:elseif test="${ungrouped && userService.getSuperUser(user)}"><div class="btn-group inliner"><a href="javascript:" data-toggle="dropdown" class="dropdown-toggle btn btn-info"><g:message code="btn.add_to_group" /></a><ul class="dropdown-menu">
                   <g:each in="${constructionGroups}" var="group">
                       <li><a href="javascript:" data-group="${group?.id}" data-groupName="${group?.name}" class="groupLink">${group.name}</a></li>
                   </g:each>
               </ul> </div></g:elseif>
           </div>


            <table class="table ">
                <thead>
                <tr>
                    <th>#</th>
                    <g:if test="${userService.getSuperUser(user)}"><th>MirrorResourceId/Active</th></g:if>
                    <th><g:message code="query.choose_resource"/></th>
                    <th><g:message code="construction.type_of"/></th>
                    <g:if test="${!account}"><th><g:message code="user.organization"/></th></g:if>
                    <th><g:message code="entity.name"/></th>
                    <th><g:message code="entity.show.unit"/></th>
                    <th><g:message code="construction.data_type"/></th>
                    <th><g:message code="account.country"/></th>
                    <th><g:message code="construction.components"/> <i class="fa fa-list"></i></th>
                    <th><g:message code="main.list.type"/></th>
                    <th style="text-transform: capitalize"><g:message code="active"/> / <g:message code="inactive"/></th>
                    <th colspan="2"><g:message code="status"/></th>
                    <th><g:message message="construction.last_edited"/> <i class="far fa-clock"></i></th>
                    <th><g:message code="import_file"/></th>
                    <g:if test="${editable}"><th>&nbsp;</th><th>&nbsp;</th></g:if>
                </tr>
                </thead>
                <tbody>
                <g:if test="${constructions}">
                    <g:each in="${constructions}" var="construction" status="i">
                        <g:set var="resource" value="${constructionService.getMirrorResourceObject(construction)}"/>
                        <tr id="${construction.id}construction">
                            <td>${i+1}</td>
                            <g:if test="${userService.getSuperUser(user)}"><td class="${construction.locked ? 'activeConstruction' : 'inactiveConstruction'}">${construction?.mirrorResourceId} / ${resource ? resource.active : 'resource does not exist'}</td></g:if>
                            <td class="${construction.locked ? 'activeConstruction' : 'inactiveConstruction'}">
                                <g:checkBox name="delete${construction.id}" data-groupId="${group?.id}" data-checkbox="true" data-constructionId="${construction?.id}" data-accountId="${account?.id ?: ""}" />
                            </td>
                            <td class="${construction.locked ? 'activeConstruction' : 'inactiveConstruction'}">${classificationParams?.find({it.answerId.equals(construction.classificationParamId)})?.localizedAnswer}</td>
                            <g:if test="${!account}">
                                <td class="${construction.locked ? 'activeConstruction' : 'inactiveConstruction'}">${construction.privateConstructionCompanyName ? construction.privateConstructionCompanyName : 'MADE BY ADMIN' }</td>
                            </g:if>
                            <td class="${construction.locked ? 'activeConstruction' : 'inactiveConstruction'}"><constructionRender:inactiveDataWarning construction="${construction}"/> <constructionRender:missmatchDataWarning construction="${construction}"/> ${resource?.staticFullName ? resource.staticFullName : constructionService.getLocalizedName(construction)} <g:if test="${userService.getDataManager(user) || userService.getSuperUser(user)}"><queryRender:renderProfilePopup resourceUUID="${resource?.id}" /></g:if><opt:renderDataCardBtn resourceId="${resource?.resourceId}" profileId="${resource?.profileId}" infoId="${resource?.id}info"/></td>
                            <td class="${construction.locked ? 'activeConstruction' : 'inactiveConstruction'}">${showImperial && construction.imperialUnit ? construction.imperialUnit : construction.unit}</td>
                            <td class="${construction.locked ? 'activeConstruction' : 'inactiveConstruction'}">${construction.environmentDataSourceType}</td>
                            <td class="${construction.locked ? 'activeConstruction' : 'inactiveConstruction'}"><img style="width: 25px;" src="/app/assets/isoflags/${isoCodesForFlags?.get(construction.id?.toString())}.png"/></td>
                            <td class="${construction.locked ? 'activeConstruction' : 'inactiveConstruction'}"><g:each in="${resourceNames?.get(construction.id?.toString())}" var="resourceList" status="index"> ${index < resourceNames?.get(construction?.id?.toString())?.size() && index > 0  ? "," : ""} ${resourceList}  </g:each></td>
                            <td class="${construction.locked ? 'activeConstruction' : 'inactiveConstruction'}"><g:if test="${construction.constructionType && constructionTypePath}"><span><img src="${constructionTypePath}${construction.constructionType}.png" class="constructionType"/> ${construction.constructionType}</span></g:if></td>
                            <td class="${construction.locked ? 'activeConstruction' : 'inactiveConstruction'}" style="text-transform: capitalize">${construction.locked ? '' : message(code: 'inactive')}</td>
                            <td>
                                <g:if test="${construction.locked}">
                                    <g:message code="status.is_locked_published"/>
                                </g:if>
                                <g:else>
                                    <g:message code="draft"/>
                                </g:else>
                            </td>
                            <td>
                                <g:if test="${editable}">
                                    <g:if test="${construction.locked}">
                                        <a href="javascript:" class="btn btn-danger" onclick="changeConstructionStatus(this, '${construction.id.toString()}', '${construction.classificationQuestionId}', '${construction.classificationParamId}', false, true);"><g:message code="btn.return_to_draft"/></a>
                                    </g:if>
                                    <g:else>
                                        <a href="javascript:" class="btn btn-primary" onclick="changeConstructionStatus(this, '${construction.id.toString()}', '${construction.classificationQuestionId}', '${construction.classificationParamId}', true, false);"><g:message code="btn.lock_and_publish"/></a>
                                    </g:else>
                                </g:if>
                            </td>
                            <td class="${construction.locked ? 'activeConstruction' : 'inactiveConstruction'}">${constructionService.getLastEditorUsername(construction.lastEditor)} <br/><g:formatDate date="${construction.edited}" format="HH:mm:ss - dd.MM.yyyy"/></td>
                            <td class="${construction.locked ? 'activeConstruction' : 'inactiveConstruction'}">${construction.importFile}</td>
                            <g:if test="${editable}">
                                <td><opt:link action="form" params="[id:construction.id, classificationQuestionId: construction.classificationQuestionId, classificationParamId: construction.classificationParamId, accountId:account?.id, group:group?.id]" class="btn btn-primary"><g:message code="edit"/></opt:link></td>
                                <td><opt:link action="form" params="[id:construction.id, classificationQuestionId: construction.classificationQuestionId, classificationParamId: construction.classificationParamId, accountId:account?.id, group:group?.id, duplicate:true]" class="btn btn-warning"><g:message code="construction.duplicate"/></opt:link></td>
                            </g:if>
                        </tr>
                    </g:each>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
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
        $('.triggerPopover').popover({
            placement: 'top',
            trigger: 'hover',
        });
    });
    $('#importConstructions').on('click', function () {
        var form = $('#importingForm');
        $('#toggler').toggleClass('icon-plus icon-minus');

        if ($(form).is(":visible")) {
            $(form).slideUp().addClass('hidden');
        } else {
            $(form).slideDown().removeClass('hidden');

        }
    });

    $('#selectAll').on('click', function () {
        $('[data-checkbox]').each(function () {
            if (!$(this).prop('checked')) {
                $(this).prop('checked', true);
            }
        });
    });

    $('.groupLink').each(function () {
        $(this).on('click', function () {
            var amountSelected = 0;
            $('[data-checkbox]').each(function () {
                if ($(this).prop('checked')) {
                    amountSelected++;
                }
           });
           var group = $(this).attr('data-group');
           var groupName =  $(this).attr('data-groupName');
           var addWarning = '${message(code: 'construction.add_add_to_group')} ' + groupName + ' ' + amountSelected +  ' ${message(code: 'construction.constructions')} ';
           var success = '${message(code: 'construction.group.add.successful')} (' + amountSelected + ')';
          addConstructionsToGroup('${message(code: 'warning')}', addWarning ,success,'${message(code: 'yes')}', '${message(code: 'back')}', group)
       });
    });

    $('#removeSelectedFromGroup').on('click', function() {
        var amountSelected = 0;
        $('[data-checkbox]').each(function () {
            if ($(this).prop('checked')) {
                amountSelected++;
            }
        });
        var deleteWarning = '${message(code: 'construction.remove_from_group')} ' + amountSelected + ' ${message(code: 'construction.constructions')} ';
        var success = '${message(code: 'construction.successful_delete')} (' + amountSelected + ')';
        removeConstructionsFromGroup('${message(code: 'warning')}', deleteWarning ,success,'${message(code: 'yes')}', '${message(code: 'back')}')

    });

    $('#deleteSelected').on('click', function () {
        var amountSelected = 0;
        $('[data-checkbox]').each(function () {
            if ($(this).prop('checked')) {
                amountSelected++;
            }
        });
        var deleteWarning = '${message(code: 'construction_deleting_selected')} ' + amountSelected + ' ${message(code: 'construction.constructions')} ';
        var success = '${message(code: 'construction.successful_delete')} (' + amountSelected + ')';
        deleteConstructions('${message(code: 'warning')}', deleteWarning ,success,'${message(code: 'yes')}', '${message(code: 'back')}')
    });

    $('#disableSelected').on('click', function () {
        var amountSelected = 0;
        $('[data-checkbox]').each(function () {
            if ($(this).prop('checked')) {
                amountSelected++;
            }
        });
        var deleteWarning = 'Are you sure you want to disable ' + amountSelected + ' ${message(code: 'construction.constructions')} ';
        var success = 'Successfully disabled all selected constructions (' + amountSelected + ')';
        disableConstructions('${message(code: 'warning')}', deleteWarning ,success,'${message(code: 'yes')}', '${message(code: 'back')}')
    });

    function changeConstructionStatus(button, constructionId, classificationQuestionId, classificationParamId, lock, unlock) {
        $('<i style="margin-right: 5px;" class="fas fa-circle-notch fa-spin"></i>').prependTo(button);
        $('.btn').addClass('removeClicksLowOpacity');
        $.ajax({
            type: 'POST',
            url: '/app/sec/construction/changeStatus',
            data: 'id=' + constructionId + '&classificationQuestionId=' + classificationQuestionId + '&classificationParamId=' + classificationParamId + '&lock=' + lock + '&unlock=' + unlock,
            success: function (data) {
                if (data.output === 'ok') {
                    location.reload();
                }
            }
        });
    }
</script>
</body>
</html>