<!doctype html>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <h1>Upload LCA Checker checks</h1>
    </div>
</div>
<div class="container section">
    <sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN">
        <div class="pull-left">
            <g:uploadForm action="uploadLcaCheckerExcel">
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

    <g:if test="${lcaCheckers}">
            <sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN">
                <a id="selectAllCheckboxes" class="btn btn-primary" style="margin-bottom: 5px;" >Select all</a>
                <a href="javascript:" onclick="removeCheckedCheckers();" style="margin-bottom: 5px;"  class="btn btn-danger"> Delete selected</a>
            </sec:ifAllGranted>
        <table class="table table-striped table-condensed data-list">
            <tr id="resourceListHeading">
                <th>&nbsp;</th><th>checkId</th><th>checkName</th><th>checkType</th><th>freeCheck</th><th>Import file</th><sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN"><th>&nbsp;</th></sec:ifAllGranted>
            </tr><%--
        --%><g:each in="${lcaCheckers}" var="lcaChecker"><%--
        --%><tr>
            <td><input style="width: 30px; height: 20px;" type="checkbox" data-checkbox="true" name="checkersToRemove" class="checkersToRemove" value="${lcaChecker.id.toString()}" /></td>
            <td>${lcaChecker.checkId}</td>
            <td>${lcaChecker.checkName}</td>
            <td>${lcaChecker.checkType}</td>
            <td>${lcaChecker.freeCheck ? true : false}</td>
            <td>${lcaChecker.fileName}</td>
            <sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN">
                <td>
                    <g:link action="removeLcaChecker" class="btn btn-danger" id="${lcaChecker.id}" onclick="return modalConfirm(this);" data-questionstr="Are you sure you want to delete lcaChecker ${lcaChecker.checkName}?" data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}" data-titlestr="Deleting lcaChecker"><g:message code="delete" /></g:link>
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

    function removeCheckedCheckers() {
        var checkBoxes = $('[data-checkbox]:checkbox:checked');
        if (checkBoxes) {

            Swal.fire({
                title: "Removing checkers",
                text: "are you sure you want to remove " + checkBoxes.length + " checks",
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
                                        url:'/app/sec/admin/lcaChecker/removeLcaCheckerAjax/',
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
                        title: "Successfully removed" + checkBoxes.length + " checks!",
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