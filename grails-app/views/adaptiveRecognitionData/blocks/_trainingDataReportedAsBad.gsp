<div id="loader">
  <g:render template="blocks/spinner"/>
</div>

<div class="sectionbody hidden">
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
      <th><g:message code="admin.importMapperTrainingData.subType"
                     default="SubType"/></th> <%-- !!! Subtype needs to be on column 3 ! or change JS below --%>
      <th><g:message code="admin.importMapperTrainingData.applicationId"/></th>
      <th><g:message code="admin.importMapperTrainingData.trainingData"/></th>
      <th><g:message code="admin.importMapperTrainingData.location" default="Location"/></th>
      <th><g:message code="admin.importMapperTrainingData.username" default="Username"/></th>
      <th><g:message code="admin.importMapperTrainingData.reportedBadBy" default="Reported bad by"/></th>
      <th><g:message code="admin.importMapperTrainingData.time"/></th>
      <th class="no-sort">&nbsp;</th>
      <th class="no-sort">&nbsp;</th>
      <th class="no-sort">&nbsp;</th>
    </tr>
    </thead>
    <tbody>
    </tbody>
  </table>
</div>

<script type="text/javascript">
    function initializeRecognitionTables() {
        $('#activeData').dataTable({
            "iDisplayLength": 250,
            "bPaginate": true,
            "sPaginationType": "full_numbers",
            "bProcessing": true,
            "bDestroy": true,
            "bDeferRender": true,
            "aaData": ${new groovy.json.JsonBuilder(trainingDatas)},
            "aoColumns": [
                {
                    mDataProp: 'id', sDefaultContent: "",
                    'fnRender': function (obj, val) {
                        return "<input type=\"checkbox\" data-active=\"true\" class=\"delete\" data-id=\"" + val + "\" id=\"delete" + val + "\"/>";
                    }
                },
                {mDataProp: 'incomingKey', sDefaultContent: ""},
                {
                    mDataProp: 'resourceId', sDefaultContent: "",
                    'fnRender': function (obj, val) {
                        var reportedAsBad = "";

                        if (obj.aData.reportedAsBad) {
                            reportedAsBad = '<i class="icon-alert"></i> Reported Bad</br>';
                        } else {
                            reportedAsBad = '${asset.image(src:"img/icon-warning.png", style:"max-width:16px")} Same mapping as reported bad</br>';
                        }

                        return "" + reportedAsBad + obj.aData.resourceId + " / " + obj.aData.profileId + " <a href=\"javascript:\" onclick=\"window.open('/app/sec/admin/import/showData?resourceId=" + obj.aData.resourceId + "&profileId=" + obj.aData.profileId + "', '_blank', 'width=1024, height=768, scrollbars=1');\"><i class=\"fas fa-search-plus\"></i></a>";
                    }
                },
                {mDataProp: 'resourceSubType', sDefaultContent: ""},
                {
                    mDataProp: 'applicationId', sDefaultContent: "",
                    'fnRender': function (obj, val) {
                        return "" + obj.aData.applicationId + " / " + obj.aData.importMapperId + "";
                    }
                },
                {
                    mDataProp: 'trainingData', sDefaultContent: "",
                    'fnRender': function (obj, val) {
                        return "<a href=\"javascript:\" class=\"tableHeadingPopover\" rel=\"popover\" data-trigger=\"click\" data-content=\"" + JSON.stringify(val).replace(/"/g, "'") + "\">${g.message(code: 'admin.importMapperTrainingData.trainingData', default: 'Training data')}</a>";
                    }
                },
                {mDataProp: 'userLocale', sDefaultContent: ""},
                {mDataProp: 'username', sDefaultContent: ""},
                {mDataProp: 'reportedBadBy', sDefaultContent: ""},
                {
                    mDataProp: 'time', sDefaultContent: "",
                    'fnRender': function (obj, val) {
                        return "<span style=\"display:none;\">" + obj.aData.timeForSort + "</span>" + val + "";
                    }
                },
                {
                    mDataProp: 'removeBadMappingStatusId', sDefaultContent: "",
                    'fnRender': function (obj, val) {
                        if (val) {
                            return "<a href=\"javascript:\" class=\"btn btn-info\" onclick=\"removeBadMappingStatus('" + val + "')\">${g.message(code: 'admin.importMapperTrainingData.button.clear', default: 'Clear')}</a>";
                        } else {
                            return ""
                        }
                    }
                },
                {
                    mDataProp: 'remapId', sDefaultContent: "",
                    'fnRender': function (obj, val) {
                        return "<a href=\"/app/sec/admin/adaptiveRecognitionData/form/" + val + "\" class=\"btn btn-primary\">${g.message(code: 'admin.importMapperTrainingData.button.remap', default: 'Remap')}</a>";
                    }
                },
                {
                    mDataProp: 'deleteId', sDefaultContent: "",
                    'fnRender': function (obj, val) {
                        return "<a href=\"javascript:\" class=\"btn btn-danger\" onclick=\"deleteTrainingData('" + val + "', this)\">${g.message(code: 'admin.importMapperTrainingData.button.delete', default: 'Delete')}</a>";
                    }
                }
            ],
            "aoColumnDefs": [{
                "aTargets": [3],
                "bVisible": false,
                "bSearchable": true
            }]
        });
    }

    function removeBadMappingStatus(trainingDataId) {
        Swal.fire({
            title: "${message(code: 'warning')}",
            text: "Are you sure you want to remove bad mapping status from this training data?",
            icon: "warning",
            confirmButtonText: "${message(code: 'yes')}",
            cancelButtonText: "${message(code: 'back')}",
            confirmButtonColor: "red",
            showCancelButton: true,
            reverseButtons: true,
            showLoaderOnConfirm: true,
            preConfirm: function () {
                return new Promise(function (resolve) {
                    $.ajax({
                        type: 'POST',
                        url: '/app/sec/admin/adaptiveRecognitionData/clearBadMappingStatus',
                        data: 'id=' + trainingDataId,
                        success: function (data) {
                            resolve(data)
                        },
                        error: function (XMLHttpRequest, textStatus) {
                            swal.showValidationMessage("Unable to clear training data: " + textStatus)
                        }
                    });
                })
            },
            allowOutsideClick: false
        }).then(result => {
            if (result.value) {
                $('.sectionbody').addClass('hidden');
                $('#loader').removeClass('hidden');
                location.reload();
            }
        });
    }

    $(function () {
        initializeRecognitionTables();
        //sortBigTable('#activeData', false);
        setTimeout(function () {
            $('#loader').addClass('hidden');
            $('.sectionbody').removeClass('hidden');
            $('#activeData').removeClass('hidden');
        }, 1000);

        var helpPopSettings = {
            placement: 'top',
            template: '<div class="popover popover-fade" style="display: block; max-width: 300px;"><div class="arrow"></div><div style="font-weight: normal !important;" class="popover-content"></div></div>'
        };
        $(".tableHeadingPopover[rel=popover]").popover(helpPopSettings);

        $.fn.dataTableExt.afnFiltering.push(
            function (oSettings, aData, iDataIndex) {
                var valid = false;
                var search = oSettings.oPreviousSearch.sSearch;

                if (search) {
                    if (aData.join().indexOf(search) > -1) {
                        valid = true;
                    }
                } else {
                    valid = true;
                }

                var subType = null;

                if (valid) {
                    if (oSettings.sTableId == "inactiveData") {
                        subType = $('#subtypeInactive').val();
                        if (subType) {
                            if (aData[3] == subType) {
                                valid = true;
                            } else {
                                valid = false;
                            }
                        }
                    } else {
                        subType = $('#subtypeActive').val();
                        if (subType) {
                            if (aData[3] == subType) {
                                valid = true;
                            } else {
                                valid = false;
                            }
                        }
                    }
                }
                return valid;
            }
        );
    });
</script>
