<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="format-detection" content="telephone=no"/>
</head>

<body>
<div class="container">
    <div class="screenheader">
        <h1>Upload Carbon Designer Entity Type Construction Excel</h1>
    </div>
</div>

<div class="container section">
    <sec:ifAllGranted roles="ROLE_DATA_MGR">
        <div class="pull-left">
            <g:uploadForm action="uploadSimulationToolExcel">
                <div class="clearfix"></div>

                <div class="column_left">
                    <div class="control-group">
                        <label for="file" class="control-label"><g:message code="admin.import.excel_file"/></label>

                        <div class="controls"><input type="file" name="xlsFile" id="file" class="btn" value=""/></div>
                    </div>
                </div>

                <div class="clearfix"></div>
                <opt:submit name="import" value="${message(code: 'import')}" class="btn btn-primary"/>
            </g:uploadForm>
        </div>

        <div class="clearfix"></div>
    </sec:ifAllGranted>


    <g:if test="${entityTypeAllocationList}">
        <sec:ifAllGranted roles="ROLE_DATA_MGR">
            <a id="selectAllCheckboxes" class="btn btn-primary" style="margin-bottom: 5px;">Select all</a>
            <a href="javascript:" onclick="removeCheckedCheckers();" style="margin-bottom: 5px;"
               class="btn btn-danger">Delete selected</a>
        </sec:ifAllGranted>
        <table class="table table-striped table-condensed data-list">
            <tr id="resourceListHeading">
                <th>&nbsp;</th>
                <sec:ifAllGranted roles="ROLE_DATA_MGR"><th>&nbsp;</th></sec:ifAllGranted>
                <th>regionReferenceId</th>
                <th>constructionId</th>
                <th>defaultResources</th>
                <th>materialTypeList</th>
                <th>incompatibleBuildingTypes</th>
                <th>defaultShare</th>
                <th>oneDwellingBuildings</th>
                <th>apartmentBuildings</th>
                <th>industrialBuildings</th>
                <th>offices</th>
                <th>retailBuildings</th>
                <th>hotels</th>
                <th>schoolPrimary</th>
                <th>sportsHalls</th>
                <th>generalEducationBuildings</th>
                <th>socialWelfareBuildings</th>
                <th>prisons</th>
                <th>culturalBuildings</th>
                <th>hospitals</th>
                <th>rowHouses</th>
                <th>dayCareCentres</th>
                <th>otherBuildingsFEC</th>
                <th>singleFamilyHouseFEC</th>
                <th>apartmentBuildingFEC</th>
                <th>dayCareCenterFEC</th>
                <th>primarySchoolFEC</th>
                <th>secondarySchoolDayFEC</th>
                <th>secondarySchoolNightFEC</th>
                <th>researchBuildingsFEC</th>
                <th>campusBuildingFEC</th>
                <th>hotel0NightFEC</th>
                <th>hotel2NightFEC</th>
                <th>hotel3NightFEC</th>
                <th>hotel4NightFEC</th>
                <th>hotel0DayFEC</th>
                <th>hotel3DayFEC</th>
                <th>officeBuildingFEC</th>
                <th>commercialCateringFEC</th>
                <th>restaurant1FEC</th>
                <th>restaurant2FEC</th>
                <th>restaurant3FEC</th>
                <th>commercialBuildingsFEC</th>
                <th>schoolSportsHallFEC</th>
                <th>retirementHomeFEC</th>
                <th>healthcareCenterNightFEC</th>
                <th>healthcareCenterDayFEC</th>
                <th>terminalBuildingsFEC</th>
                <th>industrialBuildings3x8FEC</th>
                <th>industrialBuildings8h18hFEC</th>
                <th>courtFEC</th>
                <th>sportsHallFEC</th>
                <th>courtFEC</th>
                <th>schoolCatering1FEC</th>
                <th>schoolCatering3FEC</th>
                <th>Import file</th>
            </tr>

            <g:each in="${entityTypeAllocationList}" var="entityType">
                <tr>
                    <td><input style="width: 30px; height: 20px;" type="checkbox" data-checkbox="true"
                               name="checkersToRemove" class="checkersToRemove" value="${entityType?.id}"/></td>
                    <sec:ifAllGranted roles="ROLE_DATA_MGR">
                        <td>
                            <g:link action="removeConstructionAllocation" class="btn btn-danger"
                                    id="${entityType?.id?.toString()}" onclick="return modalConfirm(this);"
                                    data-questionstr="Are you sure you want to delete construction allocation ${entityType?.constructionId}?"
                                    data-truestr="${message(code: 'delete')}" data-falsestr="${message(code: 'cancel')}"
                                    data-titlestr="Deleting lcaChecker"><g:message code="delete"/></g:link>
                        </td>
                    </sec:ifAllGranted>
                    <td>${entityType.regionReferenceId}</td>
                    <td>${entityType.constructionId}</td>
                    <td>${entityType.defaultResources}</td>
                    <td>${entityType.materialTypeList}</td>
                    <td>${entityType.incompatibleBuildingTypes}</td>
                    <td>${entityType.defaultShare}</td>
                    <td>${entityType.oneDwellingBuildings}</td>
                    <td>${entityType.apartmentBuildings}</td>
                    <td>${entityType.industrialBuildings}</td>
                    <td>${entityType.offices}</td>
                    <td>${entityType.retailBuildings}</td>
                    <td>${entityType.hotels}</td>
                    <td>${entityType.schoolPrimary}</td>
                    <td>${entityType.sportsHalls}</td>
                    <td>${entityType.generalEducationBuildings}</td>
                    <td>${entityType.socialWelfareBuildings}</td>
                    <td>${entityType.prisons}</td>
                    <td>${entityType.culturalBuildings}</td>
                    <td>${entityType.hospitals}</td>
                    <td>${entityType.rowHouses}</td>
                    <td>${entityType.dayCareCentres}</td>
                    <td>${entityType.otherBuildingsFEC}</td>
                    <td>${entityType.singleFamilyHouseFEC}</td>
                    <td>${entityType.apartmentBuildingFEC}</td>
                    <td>${entityType.dayCareCenterFEC}</td>
                    <td>${entityType.primarySchoolFEC}</td>
                    <td>${entityType.secondarySchoolDayFEC}</td>
                    <td>${entityType.secondarySchoolNightFEC}</td>
                    <td>${entityType.researchBuildingsFEC}</td>
                    <td>${entityType.campusBuildingFEC}</td>
                    <td>${entityType.hotel0NightFEC}</td>
                    <td>${entityType.hotel2NightFEC}</td>
                    <td>${entityType.hotel3NightFEC}</td>
                    <td>${entityType.hotel4NightFEC}</td>
                    <td>${entityType.hotel0DayFEC}</td>
                    <td>${entityType.hotel3DayFEC}</td>
                    <td>${entityType.officeBuildingFEC}</td>
                    <td>${entityType.commercialCateringFEC}</td>
                    <td>${entityType.restaurant1FEC}</td>
                    <td>${entityType.restaurant2FEC}</td>
                    <td>${entityType.restaurant3FEC}</td>
                    <td>${entityType.commercialBuildingsFEC}</td>
                    <td>${entityType.schoolSportsHallFEC}</td>
                    <td>${entityType.retirementHomeFEC}</td>
                    <td>${entityType.healthcareCenterNightFEC}</td>
                    <td>${entityType.healthcareCenterDayFEC}</td>
                    <td>${entityType.terminalBuildingsFEC}</td>
                    <td>${entityType.industrialBuildings3x8FEC}</td>
                    <td>${entityType.industrialBuildings8h18hFEC}</td>
                    <td>${entityType.courtFEC}</td>
                    <td>${entityType.sportsHallFEC}</td>
                    <td>${entityType.courtFEC}</td>
                    <td>${entityType.schoolCatering1FEC}</td>
                    <td>${entityType.schoolCatering3FEC}</td>
                    <td>${entityType.fileName}</td>
                </tr>
            </g:each>
        </table>
    </g:if>
</div>


<script type="text/javascript">
    $("#selectAllCheckboxes").on("click", function () {
        var checkBoxes = $('.checkersToRemove');
        checkBoxes.prop('checked', true);
    });

    function removeCheckedCheckers() {
        var checkBoxes = $('[data-checkbox]:checkbox:checked');

        if (checkBoxes && checkBoxes.length) {

            Swal.fire({
                title: "Removing construction allocation",
                text: "are you sure you want to remove " + checkBoxes.length + " construction allocation?",
                icon: "warning",
                confirmButtonText: "Yes",
                cancelButtonText: "Back",
                confirmButtonColor: "red",
                showCancelButton: true,
                reverseButtons: true,
                showLoaderOnConfirm: true,
                preConfirm: function () {
                    return new Promise(function (resolve) {
                        const ids = [];
                        const checkBoxes = $('[data-checkbox]:checkbox:checked');
                        let i = 0;

                        if (checkBoxes.length) {
                            $(checkBoxes).each(function () {
                                if ($(this).prop('checked')) {
                                    ids[i] = $(this).val();
                                    i++;
                                }
                            });
                            $.ajax({
                                type: 'POST',
                                url: '/app/sec/carbonDesigner/removeConstructionAllocationAjax',
                                data: 'ids=' + ids,
                                success: function (data) {
                                    if (data.output === true) {
                                        resolve();
                                    } else {
                                        swal({
                                            title: "Something went wrong",
                                            type: "error",
                                        })
                                    }
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
                        title: "Successfully removed " + checkBoxes.length + " construction allocation!",
                        html: ''
                    }).then(function () {
                        location.reload();
                    });
                }
            });
        } else {
            Swal.fire({
                icon: 'warning',
                title: "No construction allocation selected!",
                html: ''
            })

        }
    }
</script>
</body>
</html>