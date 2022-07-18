<%@ page import="com.bionova.optimi.core.domain.mongo.ChildEntity" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
    <asset:stylesheet src="pagination-fix.css"/>
</head>
<body>
<g:set var="indicatorService" bean="indicatorService"/>
<div class="container">
    <div class="screenheader">
        <h1>Indicator use: ${indicatorService.getLocalizedName(indicator)}</h1>
    </div>
</div>
<div class="container section">

    <div style="margin-top: 5px">
        <label for="licenseTypeSelect"><strong>Show users per license Type:</strong></label>
        <select name="licenseType" id="licenseTypeSelect" onchange="getLicensedUsersByType();">
            <option value=""></option>
            <g:each in="${licenses.collect({it.type})?.unique()}" var="licenseType">
                <option value="${licenseType}">${licenseType}</option>
            </g:each>
        </select>
        <div id="appendUsersHere"></div>
    </div>

    <opt:spinner spinnerId="loadingSpinner-indicatorUsageList"/>
    <g:render template="indicatorUsageList" model="[]"/>

    <table id ="table2" class="resource" style="margin-top: 30px;">
        <thead>
        <tr>
            <th>License</th>
            <th>Type</th>
            <th>Valid</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${licenses}" var="license">
            <tr>
                <td><opt:link controller="license" action="form" id="${license.id}" target="_blank">${license.name}</opt:link></td>
                <td>${license.type}</td>
                <td>${license.expired ? 'No' : 'Yes'}</td>
            </tr>
        </g:each>
        </tbody>
    </table>
</div>
<script type="text/javascript">

    $(document).ready(function() {
        sortableTable('#table1');
        sortableTable('#table2');
    })

    function copyInputFieldValueToClipboard(id) {
        var elementId =  id + "users";
        var $temp = $("<input>");
        $("body").append($temp);
        $temp.val(document.getElementById(elementId).value).select();
        document.execCommand("copy");
        $temp.remove();
    }

    function getLicensedUsersByType() {
        if (!$("#licenseTypeSelect").attr('disabled')) {

            var licenseType = $('#licenseTypeSelect').val();

            var indicatorId = "${indicator?.indicatorId}";

            $.ajax({
                url: '/app/sec/admin/dataSummary/getLicenseTypeUsers',
                data: 'licenseType=' + licenseType + '&indicatorId=' + indicatorId,
                beforeSend: function () {
                    $('#appendUsersHere').empty().append('<i style="margin-left:45px; margin-top:10px;" class="fa fa-2x fa-circle-o-notch fa-spin"></i>');
                    $("#licenseTypeSelect").attr("disabled", true);
                },
                success: function (data) {
                    $('#appendUsersHere').empty().append(data.output);
                    $("#licenseTypeSelect").prop('disabled', false);

                }
            });
        }
    }

</script>
</body>
</html>