<%
    def userService = grailsApplication.mainContext.getBean("userService")
%>
<!doctype html><%@ page import="com.bionova.optimi.core.service.ResourceTypeService" %>
<%
    ResourceTypeService resourceTypeService = grailsApplication.mainContext.getBean("resourceTypeService")
%>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <h1>
            <g:message code="admin.resourceTypes" /> (active: ${activeResourceTypes ? activeResourceTypes.size() : 0}, inactive: ${inactiveResourceTypes ? inactiveResourceTypes.size() : 0})
        </h1>
    </div>
</div>
<div class="container section">
    <g:set var="currentUser" value="${applicationContext.userService.getCurrentUser()}"/>
    <g:if test="${userService.getDataManagerAndAdmin(currentUser)}">
        <g:uploadForm action="upload">
            <g:hiddenField name="typeClass" value="${typeClass}"/>
            <div class="clearfix"></div>
            <div class="column_left">
                <div class="control-group">
                    <label for="file" class="control-label"><g:message code="import.excel_file" /></label>
                    <div class="controls"><input type="file" name="xlsFile" id="file" class="btn" value="" /></div>
                </div>
            </div>
            <div class="clearfix"></div>
            <opt:submit name="import" value="${message(code:'import')}" class="btn btn-primary"/>
        </g:uploadForm>

        <a class="btn btn-primary" style="margin-bottom: 5px;" onclick="selectAllCheckboxes()">Select all</a>
        <a class="btn btn-primary" style="margin-bottom: 5px;" onclick="deselectAllCheckboxes()">Deselect all</a>
        <a href="javascript:" onclick="removeCheckedCheckers();" style="margin-bottom: 5px;"  class="btn btn-danger"> Delete selected</a>
    </g:if>
    <br>
    <g:if test="${inactiveResourceTypes}">
        <a href="javascript:;" onclick="showHideClass('inactive');">Show also inactive</a>
    </g:if>

    <div class="clearfix"></div>
    <table class="resource" id="resourceList">
        <g:if test="${activeResourceTypes}"><%--
        --%><tr id="resourceListHeading">
                <th></th>
                <th>ResourceType</th><th>SubType</th><th>LocalizedName (Currently: ${locale})</th>
                <th>requiredElectricityKwh</th><th>siteWastage</th>
                <th>StandardUnit</th><th>Active</th><th>Import file</th><th>Import time</th>
                <th>Count (type)</th><th>Conversion to standard unit</th><th>Conversion to kg</th><th>Missing required units</th><th>Unconventional units</th><th>QA Excel</th><sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN"><th>&nbsp;</th></sec:ifAllGranted>
            </tr><%--
        --%><g:each in="${activeResourceTypes.sort({ a, b -> a.resourceType?.toLowerCase() <=> b.resourceType?.toLowerCase() ?: a.subType?.toLowerCase() <=> b.subType?.toLowerCase()})}" var="resourceType"><%--
        --%><tr>
                <td><input type="checkbox" value="${resourceType.id}"/></td>
                <td>${resourceType.resourceType} <a href="javascript:" onclick="window.open('${createLink(action: "showData", controller: "resourceType", params: [id: resourceType.id.toString()])}', '_blank', 'width=1024, height=768, scrollbars=1');"><i class="fas fa-search-plus"></i></a></td>
                <td>${resourceType.subType}</td>
                <td>${resourceTypeService.getLocalizedName(resourceType)}</td>
                <td>${resourceType.requiredElectricityKwh}</td>
                <td>${resourceType.siteWastage}</td>
                <td>${resourceType.standardUnit}</td>
                <td>${resourceType.active}</td>
                <td>${resourceType.importFile}</td>
                <td>${resourceType.importTime}</td>
                <td><a href="javascript:" onclick="window.open('${createLink(action: "showResources", params:[resourceType: resourceType.resourceType, subType: resourceType.subType])}','showResourcesWindow', 'width=1024, height=768, scrollbars=1');">${resourceType.isSubType ? resourceTypeService.getResourceSubTypeCount(resourceType) : resourceTypeService.getResourceCount(resourceType.resourceType)}</a></td>
                <g:set var="fails" value="${resourceTypeService.getCalculateFailsForSubtype(resourceType)}"/>
                <td>
                    <g:if test="${fails.get("conversionFails")}">
                        <span><strong><a href="javascript:" style="color: #FF2D41;" onclick="window.open('${createLink(action: "showResources", params:[resourceType: resourceType.resourceType, subType: resourceType.subType, failsConversion: true, standardUnit: resourceType.standardUnit])}','showResourcesWindow', 'width=1024, height=768, scrollbars=1');">${fails.get("conversionFails")} fails</a></strong></span>
                    </g:if>
                </td>
                <td>
                    <g:if test="${fails.get("conversionFailsKg")}">
                        <span><strong><a href="javascript:" style="color: #FF2D41;" onclick="window.open('${createLink(action: "showResources", params:[resourceType: resourceType.resourceType, subType: resourceType.subType, failsConversionKg: true, standardUnit: "kg"])}','showResourcesWindow', 'width=1024, height=768, scrollbars=1');">${fails.get("conversionFailsKg")} fails</a></strong></span>
                    </g:if>
                </td>
                <td>
                    <g:if test="${fails.get("requiredUnitFails")}">
                        <span><strong><a href="javascript:" style="color: #FF2D41;" onclick="window.open('${createLink(action: "showResources", params:[resourceType: resourceType.resourceType, subType: resourceType.subType, requiredUnitFails: true, standardUnit: resourceType.standardUnit])}','showResourcesWindow', 'width=1024, height=768, scrollbars=1');">${fails.get("requiredUnitFails")} fails</a></strong></span>
                    </g:if>
                </td>
                <td>
                    <g:if test="${fails.get("badUnitFails")}">
                        <span><strong><a href="javascript:" style="color: #FF2D41;" onclick="window.open('${createLink(action: "showResources", params:[resourceType: resourceType.resourceType, subType: resourceType.subType, badUnitFails: true, standardUnit: resourceType.standardUnit])}','showResourcesWindow', 'width=1024, height=768, scrollbars=1');">${fails.get("badUnitFails")} fails</a></strong></span>
                    </g:if>
                </td>
            <td><g:link controller="resourceType" action="generateQAExcel" params="[id: resourceType.id]" class="btn btn-primary"><g:message code="results.expand.download_excel" /></g:link></td>
          <sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN">
              <td>
                  <g:link action="remove" class="btn btn-danger" id="${resourceType.id}" onclick="return modalConfirm(this);" data-questionstr="Are you sure you want to delete resourceType ${resourceTypeService.getLocalizedName(resourceType)}?"
                          data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}" data-titlestr="Deleting resourceType"><g:message code="delete" /></g:link>
              </td>
            </sec:ifAllGranted>
          </tr><%--
      --%></g:each><%--
        --%></g:if><%--
        --%><g:if test="${inactiveResourceTypes}"><%--
            --%><g:each in="${inactiveResourceTypes.sort({ a, b -> a.resourceType?.toLowerCase() <=> b.resourceType?.toLowerCase() ?: a.subType?.toLowerCase() <=> b.subType?.toLowerCase()})}" var="resourceType"><%--
            --%><tr class="inactive" style="display: none;">
                    <td><input type="checkbox" value="${resourceType.id}"/></td>
                    <td>${resourceType.resourceType}</td>
                    <td>${resourceType.subType}</td>
                    <td>${resourceTypeService.getLocalizedName(resourceType)}</td>
                    <td>${resourceType.requiredElectricityKwh}</td>
                    <td>${resourceType.siteWastage}</td>
                    <td>${resourceType.unitCostDenominator}</td>
                    <td>${resourceType.standardUnit}</td>
                    <td>${resourceType.active}</td>
                    <td>${resourceType.importFile}</td>
                    <td>${resourceType.importTime}</td>
                    <td>-</td>
                    <sec:ifAllGranted roles="ROLE_SYSTEM_ADMIN">
                        <td>
                            <g:link action="remove" class="btn btn-danger" id="${resourceType.id}" params="[deleteRedirect: deleteRedirect]" onclick="return modalConfirm(this);" data-questionstr="${message(code:'admin.import.resource.delete_question')}"
                                    data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}" data-titlestr="${message(code:'admin.import.resource.delete_title')}"><g:message code="delete" /></g:link>
                        </td>
                    </sec:ifAllGranted>
                </tr><%--
            --%></g:each><%--
        --%></g:if><%--
    --%></table>
</div>

<script>

    deselectAllCheckboxes()

    function selectAllCheckboxes () {
        var checkBoxes = $('input[type=checkbox]');
        checkBoxes.prop('checked', true);
    }

    function deselectAllCheckboxes () {
        var checkBoxes = $('input[type=checkbox]');
        checkBoxes.prop('checked', false);
    }

    function removeCheckedCheckers() {

        var checkBoxes = $('input[type=checkbox]:checked');
        console.log("Selected checkbox size: " + checkBoxes.length)

        if (checkBoxes && checkBoxes.length) {

            Swal.fire({
                title: "Removing resource type",
                text: "are you sure you want to remove " + checkBoxes.length + " resource type?",
                icon: "warning",
                confirmButtonText: "Yes",
                cancelButtonText: "Back",
                confirmButtonColor: "red",
                showCancelButton: true,
                reverseButtons: true,
                showLoaderOnConfirm: true,
                preConfirm: function () {
                    return new Promise(function (resolve) {
                        var checkBoxes = $('input[type=checkbox]:checked');
                        var i = 0;

                        if (checkBoxes.length) {
                            $(checkBoxes).each(function () {
                                if ($(this).prop('checked')) {
                                    var resourceTypeId = $(this).val();

                                    $.ajax({
                                        type: 'POST',
                                        url:'/app/sec/resourceType/removeFromAjax',
                                        data: 'id=' + resourceTypeId,
                                        success: function (data) {
                                            if (data) {
                                                console.log("removed succ. resourceType: " + resourceTypeId)
                                                i++;
                                            }
                                            if (i === checkBoxes.length) {
                                                resolve();
                                            }

                                        },
                                        error: () => console.log("removed fail. resourceType: " + resourceTypeId)
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
                        title: "Succesfully removed " + checkBoxes.length + " resource type!",
                        html: ''
                    }).then(function () {
                        location.reload();
                    });
                }
            });
        } else {
            Swal.fire({
                icon: 'warning',
                title: "No resource type selected!",
                html: ''
            })

        }
    }

</script>

</body>

</html>



