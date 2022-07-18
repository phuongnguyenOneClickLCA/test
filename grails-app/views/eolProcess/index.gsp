<!doctype html>
<%@ page import="com.bionova.optimi.core.service.EolProcessService" %>
<%
    EolProcessService eolProcessService = grailsApplication.mainContext.getBean("eolProcessService")
%>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <h1>Upload EOL Processes</h1>
    </div>
</div>
<div class="container section">
    <sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN">
        <div class="pull-left">
            <g:uploadForm action="uploadEolProcessExcel">
                <div class="clearfix"></div>
                <div class="column_left">
                    <div class="control-group">
                        <label for="file" class="control-label"><g:message code="admin.import.excel_file" /></label>
                        <div class="controls"><input type="file" name="xlsFile" id="file" class="btn" value="" /></div>
                    </div>
                </div>
                <div class="clearfix"></div>
                <opt:submit name="import" value="${message(code:'import')}" class="btn btn-primary"/>
            </g:uploadForm>
        </div>
        <div class="clearfix"></div>
    </sec:ifAllGranted>

    <sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN">
        <g:if test="${eolProcesses||inactiveEolProcesses}">
            <a id="selectAllCheckboxes" class="btn btn-primary" style="margin-bottom: 5px;" >Select all</a>
            <a href="javascript:" onclick="removeCheckedProcesses();" style="margin-bottom: 5px;"  class="btn btn-danger"> Delete selected</a>
        </g:if>
    </sec:ifAllGranted>

    <h2>Active EOL Processes: ${eolProcesses?.size()}</h2>
    <g:if test="${eolProcesses}">
        <table class="table table-striped table-condensed data-list">
            <tr id="resourceListHeading">
                <th>&nbsp;</th><th>eolProcessId</th><th>eolProcessName</th><th>Import file</th><sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN"><th>&nbsp;</th></sec:ifAllGranted>
            </tr><%--
        --%><g:each in="${eolProcesses}" var="eolProcess"><%--
        --%><tr>
            <td><input style="width: 30px; height: 20px;" type="checkbox" data-checkbox="true" name="checkersToRemove" class="checkersToRemove" value="${eolProcess.id.toString()}" /></td>
            <td>${eolProcess.eolProcessId} <a href="javascript:" onclick="window.open('${createLink(controller: "eolProcess", action: "showData", params: [eolProcessId: eolProcess.eolProcessId])}', '_blank', 'width=1024, height=768, scrollbars=1');stopBubblePropagation(event);"><i class="fas fa-search-plus"></i></a></td>
            <td>${eolProcessService.getLocalizedName(eolProcess)}</td>
            <td>${eolProcess.fileName}</td>
            <sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN">
                <td>
                    <g:link action="removeEolProcess" class="btn btn-danger"
                            id="${eolProcess.id}" onclick="return modalConfirm(this);" data-questionstr="Are you sure you want to delete EOL Process ${eolProcessService.getLocalizedName(eolProcess)}?" data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}" data-titlestr="Deleting EOL Process"><g:message code="delete" /></g:link>
                </td>
            </sec:ifAllGranted>
        </tr>
        </g:each>
        </table>
    </g:if>

    <h2>Inactive EOL Processes: ${inactiveEolProcesses?.size()}</h2>
    <g:if test="${inactiveEolProcesses}">
        <table class="table table-striped table-condensed data-list">
            <tr id="resourceListHeading">
                <th>&nbsp;</th><th>eolProcessId</th><th>eolProcessName</th><th>Import file</th><sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN"><th>&nbsp;</th></sec:ifAllGranted>
            </tr><%--
        --%><g:each in="${inactiveEolProcesses}" var="eolProcess"><%--
        --%><tr>
            <td><input style="width: 30px; height: 20px;" type="checkbox" data-checkbox="true" name="checkersToRemove" class="checkersToRemove" value="${eolProcess.id.toString()}" /></td>
            <td>${eolProcess.eolProcessId} <a href="javascript:" onclick="window.open('${createLink(controller: "eolProcess", action: "showData", params: [eolProcessId: eolProcess.eolProcessId])}', '_blank', 'width=1024, height=768, scrollbars=1');stopBubblePropagation(event);"><i class="fas fa-search-plus"></i></a></td>
            <td>${eolProcessService.getLocalizedName(eolProcess)}</td>
            <td>${eolProcess.fileName}</td>
            <sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN">
                <td>
                    <g:link action="removeEolProcess" class="btn btn-danger"
                            id="${eolProcess.id}" onclick="return modalConfirm(this);"
                            data-questionstr="Are you sure you want to delete EOL Process ${eolProcessService.getLocalizedName(eolProcess)}?"
                            data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}"
                            data-titlestr="Deleting EOL Process"><g:message code="delete" /></g:link>
                </td>
            </sec:ifAllGranted>
        </tr>
        </g:each>
        </table>
    </g:if>
</div>
<script type="text/javascript">
    $("#selectAllCheckboxes").on("click", function() {
        var checkBoxes = $('.checkersToRemove');
        $(checkBoxes).each(function () {
            $(this).prop('checked', true)
        });
    });

    function removeCheckedProcesses() {
        var checkBoxes = $('[data-checkbox]:checkbox:checked');
        if (checkBoxes) {
            Swal.fire({
                title: "Removing EOL Processes",
                text: "are you sure you want to remove " + checkBoxes.length + " EOL Processes",
                icon: "warning",
                confirmButtonText: "Yes",
                cancelButtonText: "Back",
                confirmButtonColor: "red",
                showCancelButton: true,
                reverseButtons: true,
                showLoaderOnConfirm: true,
                preConfirm: function () {
                    return new Promise(function (resolve) {
                        var checkBoxes = $('[data-checkbox]:checkbox:checked');
                        var i = 0;

                        if (checkBoxes.length) {
                            $(checkBoxes).each(function () {
                                if ($(this).prop('checked')) {
                                    var checkerId = $(this).val();
                                    $.ajax({
                                        type: 'POST',
                                        url:'/app/sec/admin/eolProcess/removeEolProcessAjax/',
                                        data: 'id=' + checkerId,
                                        success: function (data) {
                                            if (data) {
                                                i++;
                                            }
                                            if (i === checkBoxes.length) {
                                                resolve();
                                            }
                                        }
                                    });
                                }
                            });
                        } else {
                            resolve();
                        }

                    })
                },
                allowOutsideClick: false
            }).then(result => {
                if (result.value) {
                    Swal.fire({
                        icon: 'success',
                        title: "Succesfully removed" + checkBoxes.length + " EOL Processes!",
                        html: ''
                    }).then(function () {
                        location.reload();
                    });
                }
            });
        } else {

        }
    }
</script>
</body>
</html>