<%-- Copyright (c) 2012 by Bionova Oy --%>
<%@ page import="groovy.json.JsonBuilder" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="format-detection" content="telephone=no"/>
    <asset:stylesheet src="pagination-fix.css"/>
    <style>
    .pagination .remotepagesizes {
        width: 120px;
    }
    </style>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <h1>
            <g:message code="admin.adaptiveRecognitionDataController.title"/>
            <g:if test="${onlyReportedAsBad}">
                (<g:message code="admin.adaptiveRecognitionDataController.title.reportedAsBad"
                            default="Reported as bad by users"/>)
            </g:if>
        </h1>
    </div>
</div>

<div id="trainingData" class="container section">
    <g:if test="${trainingDatas}">
        <g:if test="${onlyReportedAsBad}">
            <g:render template="blocks/trainingDataReportedAsBad"
                      model="[trainingDatas: trainingDatas, totalNumberOfRecords: totalNumberOfRecords]"/>
        </g:if>
        <g:else>
            <g:render template="blocks/trainingData"
                      model="[trainingDatas: trainingDatas, totalNumberOfRecords: totalNumberOfRecords,
                              availableSubTypes: availableSubTypes, availableApplications: availableApplications,
                              searchFilter: searchFilter]"/>
        </g:else>
    </g:if>
</div>
<script type="text/javascript">
    function deleteTrainingData(trainingDataId, element) {
        var thisElem = $(element);
        var row = thisElem.closest('tr');
        Swal.fire({
            title: "${message(code: 'warning')}",
            text: "Are you sure you want to delete this training data?",
            icon: "warning",
            confirmButtonText: "${message(code: 'yes')}",
            cancelButtonText: "${message(code: 'back')}",
            confirmButtonColor: "red",
            showCancelButton: true,
            reverseButtons: true,
            showLoaderOnConfirm: true,
            preConfirm: function () {
                return new Promise(function (resolve) {
                    $.ajax({type: 'POST',
                        url: '/app/sec/admin/adaptiveRecognitionData/delete',
                        data:'id=' + trainingDataId,
                        success: function (data) {
                            $(row).remove();
                            resolve(data)
                        },
                        error: function (XMLHttpRequest, textStatus) {
                            swal.showValidationMessage("Unable to delete training data: " + textStatus)
                        }
                    });
                })
            },
            allowOutsideClick: false
        }).then(result => {
            if (result.value) {
                Swal.fire({
                    icon: 'success',
                    title: 'Successfully deleted trainingData',
                    html: ''
                })
            }
        });
    }

    function checkAll(tableId) {
        var checkBoxes;
        if (tableId==="activeData") {
            checkBoxes = $('[data-active]');
        } else {
            checkBoxes = $('[data-inactive]');

        }
        if (checkBoxes) {
            $(checkBoxes).each(function () {
                if (!$(this).is(':checked')) {
                    $(this).prop('checked', true)
                }
            });
        }
    }

    function deleteChecked(tableId) {
        var checkBoxes;
        if (tableId==="activeData") {
            checkBoxes = $('[data-active]');
        } else {
            checkBoxes = $('[data-inactive]');

        }

        var index = 0;

        Swal.fire({
            title: "${message(code: 'warning')}",
            text: "Are you sure you want to delete this training data?",
            icon: "warning",
            confirmButtonText: "${message(code: 'yes')}",
            cancelButtonText: "${message(code: 'back')}",
            confirmButtonColor: "red",
            showCancelButton: true,
            reverseButtons: true,
            showLoaderOnConfirm: true,
            preConfirm: function () {
                return new Promise(function (resolve) {
                    $(checkBoxes).each(function () {
                        if ($(this).is(':checked')) {
                            var row = $(this).parent().parent();
                            $.ajax({type: 'POST',
                                url: '/app/sec/admin/adaptiveRecognitionData/delete',
                                data:'id=' + $(this).attr('data-id'),
                                success: function (data) {
                                },
                                error: function (XMLHttpRequest, textStatus, errorThrown) {
                                }
                            });
                        }

                        index++;
                        if (index=== checkBoxes.length) {
                            resolve();
                        }
                    });
                })
            },
            allowOutsideClick: false
        }).then(result => {
            if (result.value) {
                Swal.fire({
                    icon: 'success',
                    title: 'Successfully deleted trainingData',
                    html: ''
                }).then(function () {
                    $('.sectionbody').addClass('hidden');
                    $('#loader').removeClass('hidden');
                    location.reload();
                });
            }
        });
    }
</script>
</body>
</html>