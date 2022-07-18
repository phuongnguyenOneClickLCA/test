<%--
  Created by IntelliJ IDEA.
  User: luke
  Date: 01/09/2020
  Time: 12:57
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
</head>

<body>
<div class="container">
    <div class="screenheader">
        <h4>
            <opt:link controller="main" removeEntityId="true"><g:message code="main" /></opt:link> > <opt:link controller="account" action="form" params="[id: account?.id]"><g:message code="account.title_page"/> </opt:link> > <g:message code="pcl.manage_pcl_breadcrumb"/> ${account?.companyName ?: "${message(code: 'pdl.unnamed_company')}"} <br/>
        </h4>
        <div class="pull-left">
            <h1>
                <g:message code="pcl.manage_pcl_breadcrumb"/> ${account?.companyName ?: "${message(code: 'pdl.unnamed_company')}"}
            </h1>
        </div>
    </div>
</div>
<g:if test="${privateClassificationList}">
    <g:set value="${privateClassificationList}" var="privateClassification"/>
</g:if>
<div class="container section">
    <div class="sectionbody">
        <div id="privateClassificationsMainDiv">

            <g:if test="${privateClassificationList}">
                <div class="wrapperForUpdatingPCL container">
                    <h2><g:message code="pcl.update_classification_list"/></h2>
                    <div id="showUploadBtn">
                        <g:uploadForm controller="privateClassificationList" action="uploadNewClassificationList" style="margin: 10px 0 10px;" name="updateClassificationList">
                            <g:hiddenField name="accountId" value="${account?.id}" />
                            <g:hiddenField name="name" value="${privateClassification.getName()}" />
                            <table><tbody>
                            <tr>
                                <td>
                                    <label class="control-label">
                                        <strong><g:message code="pcl.choose_file"/></strong>
                                    </label>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="control-group">
                                        <input type="file" name="importFile"
                                               id="pclFileUpdate" onchange="removeBorder(this);" class="input-large" value=""/>
                                    </div>
                                </td>
                                <td><a style="margin-bottom: 9px" onclick="swalAndSubmit();" class="btn btn-primary">${message(code: 'upload')}</a></td>
                                <td><span id="helpForm" style="margin-bottom: 9px" class="btn btn-primary" rel="popover" data-trigger="click" data-html="true" data-content="<g:message code="pcl.help_description"/>"><i class="fas fa-question"></i></span></td>
                            </tr>
                            </tbody></table>
                        </g:uploadForm>
                    </div>
                </div>
            </g:if>
            <g:else>
                <div class="wrapperForNewPCL container">
                    <h2><g:message code="pcl.add_classification"/></h2>

                    <div id="addNewPCLdiv">
                        <div class="wrapperForTemplate" style="margin: 10px 5px 0 5px;">
                            <g:link action="downloadTemplateFile" params="[accountId: account?.id]">
                                <i class="fas fa-file-download"></i>
                                <g:message code="pcl.download_template"/>
                            </g:link>
                        </div>
                        <g:uploadForm controller="privateClassificationList" action="createNewClassificationList"
                                      style="margin: 10px 0 10px;" name="createNewClassificationList">
                            <g:hiddenField name="accountId" value="${account?.id}"/>
                            <table><tbody>
                            <tr>
                                <td><strong><g:message code="entity.name"/></strong></td>
                                <td>
                                    <label class="control-label">
                                        <strong><g:message code="pcl.choose_file"/></strong>
                                    </label>
                                </td>
                            </tr>
                            <tr>
                                <td><g:textField style="max-width:250px;" name="name" required="true" class="input redBorder"/></td>
                                <td>
                                    <div class="control-group">
                                        <input type="file" name="importFile"
                                               id="pclFileUpload" onchange="removeBorder(this);" class="input-large" value=""/>
                                    </div>
                                </td>
                                <td><opt:submit style="margin-bottom: 9px" class="btn btn-primary" name="submit" value="${message(code: 'upload')}"/></td>
                                <td><span id="helpForm" style="margin-bottom: 9px" class="btn btn-primary longcontent" rel="popover" data-trigger="click" data-html="true" data-content="<g:message code="pcl.help_description"/>"><i class="fas fa-question"></i></span></td>
                            </tr>
                            </tbody></table>
                        </g:uploadForm>
                    </div>
                </div>
            </g:else>
            <div class="wrapperForExistingPCLists container">
                <table class="table PCLTable">
                    <thead><tr><th><g:message code="entity.name"/></th><th><g:message code="pcl.total_classifications"/></th><th>&nbsp;</th></tr></thead>
                    <tbody>
                    <g:if test="${privateClassificationList}">
                        <tr>
                            <td>
                                <span id="classificationName">${privateClassification.getName()}</span> <a href="javascript:" onclick="changeClassificationName(this, '${account?.id}')"><i class="fas fa-pencil-alt"></i></a><span class="hidden inliner"><opt:textField style="max-width:150px;" name="name" value="${privateClassification.getName()}" class="input inliner"/> <a href="javascript:" style="vertical-align: top;" class="btn btn-primary">Save</a></span>
                            </td>
                            <td>${privateClassification.answers.size()} <span href="#" rel="popover" data-toggle="popover" data-trigger="hover" data-content="${message(code: 'pcl.view_answers')}"><a href="javascript:" id="answers" style="margin-bottom: 9px" data-toggle="modal" data-target="#answersModal"><i class="fas fa-search-plus"></i></a></span></td>
                            <td>
                                <g:link controller="privateClassificationList" action="downloadClassificationExcelFile" params="[id:privateClassification.id.toString()]" class="btn btn-primary"><g:message code="pcl.download_excel"/></g:link>
                                <a href="javascript:" class="btn btn-danger" onclick="deleteClassificationList('${privateClassification.getId()}', '${account?.id}','${message(code: 'pcl.delete_pcl_title')}', '${message(code: 'pcl.delete_pcl_text')} ${privateClassification.getName()}?', '${message(code: 'pcl.delete_pcl_success_text')} ${privateClassification.getName()}','${message(code: 'yes')}', '${message(code: 'back')}')"><g:message code="delete"/></a>
                               %{-- <g:link action="deleteClassificationList" params="[id:privateClassification.getId(), accountId:account?.id]" class="btn btn-danger"><g:message code="delete"/></g:link>--}%
                                %{--<a href="javascript:" class="btn btn-danger" onclick="deletePCL('${DummyDataId}','${message(code: 'warning')}', 'Are you sure you want to delete private classifications list: ${DummyDataName} ?', 'Successfully deleted private classifications list: ${DummyDataName}','${message(code: 'yes')}', '${message(code: 'back')}')"><g:message code="delete"/></a>--}%
                            </td>
                        </tr>
                    </g:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <div class="modal fade" id="answersModal" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title"><g:message code="pcl.total_classifications"/></h4>
                </div>
                <div class="modal-body">
                    <table class="table">
                        <thead><tr><th><g:message code="answer"/> Id</th><th><g:message code="answer"/></th></tr></thead>
                        <tbody>
                        <g:if test="${privateClassification}">
                            <g:each in="${privateClassification.answers}" var="answer">
                                <tr><td>${answer.key}</td> <td>${answer.value}</td></tr>
                            </g:each>
                        </g:if>
                        </tbody>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
            </div>

        </div>
    </div>
</div>
<script type="text/javascript">

    function swalAndSubmit() {
        Swal.fire({
            title: "${message(code: 'pcl.update_warning_title')}",
            text: "${message(code: 'pcl.update_warning_text')}",
            icon: "warning",
            confirmButtonText: "${message(code: 'yes')}",
            cancelButtonText: "${message(code: 'back')}",
            confirmButtonColor: "red",
            showCancelButton: true,
            reverseButtons: true,
        }).then((result) => {
            if(result.value) {
                $("#updateClassificationList").submit()
            }
        });
    }

    function changeClassificationName(element, accountId) {
        $(element).hide();
        $("#classificationName").hide()
        var editField = $(element).next();
        $(editField).fadeIn().removeClass('hidden');
        var submit = $('a', editField);
        $(submit).on('click', function () {
            var name = $(this).prev().val();
            $('<i id="validatinSpinner" style="padding-right: 4px" class="fas fa-circle-notch fa-spin"></i>').prependTo(submit)
            $(submit).attr('disabled', 'disabled').off('click')
            $.ajax({
                url: '/app/sec/privateClassificationList/changeClassificationName',
                data: 'name=' + name + '&accountId=' + accountId,
                type: 'POST',
                success: function () {
                    location.reload();
                }
            });
        });
    }

    function deleteClassificationList(id, accountId, warningTitle, warningText, successText, yes, back) {
        Swal.fire({
            title: warningTitle,
            text: warningText,
            icon: "warning",
            confirmButtonText: yes,
            cancelButtonText: back,
            confirmButtonColor: "red",
            showCancelButton: true,
            reverseButtons: true,
            showLoaderOnConfirm: true,
            preConfirm: function () {
                return new Promise(function (resolve) {
                    $.ajax({
                        type: 'POST',
                        url: '/app/sec/privateClassificationList/deleteClassificationList',
                        data: 'id=' + id + '&accountId=' + accountId,
                        success: function () {
                            resolve()
                        }
                    });
                })
            },
        }).then((result) => {
            if (result.value) {
                Swal.fire({
                    icon: 'success',
                    title: successText,
                }).then(function () {
                    location.reload();
                });
            }
        });
    }
</script>
</body>
</html>