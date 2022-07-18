<%-- Copyright (c) 2012 by Bionova Oy --%>
<!doctype html>
<html>
<head>
    <% def emailConfig = grailsApplication.mainContext.getBean("emailConfiguration") %>
    <meta name="layout" content="main">
    <meta name="format-detection" content="telephone=no"/>
    <asset:javascript src="jquery-migrate-3.1.0.min.js"/>
</head>

<body>
<script type="text/javascript">
    if(!!window.performance && window.performance.navigation.type === 2) {
        // This is to detect user pressing back button on browser and reloading && clearing session properly with reload
        window.stop();
        window.location.reload();
    }
</script>
    <div class="container">
        <g:if test="${outdatedPluginError}">${outdatedPluginError}</g:if>
        <div class="screenheader">
        <opt:breadcrumbsNavBar indicator="${indicator}" parentEntity="${entity}" childEntity="${childEntity}" specifiedHeading="${message(code: 'import_data')}"/>
        <div id="stepsHeader">
            <g:if test="${showSteps}">
                <opt:breadcrumbs current="${currentStep}"/>
            </g:if>
        </div>
        </div>
    </div>

    <div class="container section">
        <div class="">
            <opt:spinner/>
            <div id="importPageContent">
                <g:if test="${importMapperFound}">
                    <g:render template="chooseFile"/>
                </g:if>
            </div>

        </div>
    </div>
<script type="text/javascript">
    $(function () {
        $(".autocompletebox").combobox();

    });

    function sendToAnotherUserSwal() {
        Swal.fire({
            html: "<div style=\"float:left;\"><h1>${message(code: 'sendMeData.heading')?.replaceAll("\"", "'")}</h1><p style=\"text-align: left;\">${message(code: 'sendMeData.info1')?.replaceAll("\"", "'")}</p><br />" +
                "<ul><li class=\"importMapperNoLicenseList\">${message(code: 'sendMeData.bullet1')?.replaceAll("\"", "'")}</li><li class=\"importMapperNoLicenseList\">${message(code: 'sendMeData.bullet2')?.replaceAll("\"", "'")}</li>" +
                "<li class=\"importMapperNoLicenseList\">${message(code: 'sendMeData.bullet3')?.replaceAll("\"", "'")}</li></ul><br />" +
                "<div id='sendToAnotherUserInput' style='margin-top: 5px;'><p style=\"text-align: left;\">${message(code: 'sendMeData.info2')?.replaceAll("\"", "'")}</p>"+
                "<table style=\"width:100%; margin-top: 20px;\"><tr><td style=\"width: 150px;\"><label for=\"anotherUserEmail\"><strong>${message(code: 'sendMeData.emailHeading')?.replaceAll("\"", "'")}</strong></label></td>"+
                "<td><input id='anotherUserEmail' style='width: 85%;' type='text' /></td></tr>"+
                "<tr><td style=\"width: 150px;\"><label for=\"anotherUserNote\"><strong>${message(code: 'sendMeData.noteHeading')?.replaceAll("\"", "'")}</strong></label></td>"+
                "<td><textarea id='anotherUserNote' style='resize:vertical; width: 85%;'></textarea></td></tr></table></div></div>",
            confirmButtonText: "Send data",
            confirmButtonColor: '#8DC73F',
            cancelButtonText: "${message(code: "cancel")}",
            cancelButtonColor: '#CDCDCD',
            showCancelButton: true,
            allowOutsideClick: false,
            reverseButtons: true,
            showLoaderOnConfirm: true,
            customClass: 'swal-medium',
            preConfirm: function () {
                var emailInput = $('#anotherUserEmail');
                var email = $(emailInput).val();
                var note = $('#anotherUserNote').val().replace(/\n/g, '<br/>'); // Preserve line breaks as HTML
                return fetch('/app/sec/sendMeDataRequest/sendFileToAnotherUser', {
                    method: "POST",
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        email: email,
                        note: note
                    })
                })
                .then(response => response.json())
                .then(json => {
                    if (json.error) {
                        throw new Error(json.error)
                    }
                    return true
                })
                .catch(error => {
                    Swal.showValidationMessage(error)
                })
            },
        }).then((result) => {
            if (result.value === true) {
                Swal.fire({
                    icon: 'success',
                    title: "${message(code: "email.sent")}",
                    allowOutsideClick: false,
                    html: ''
                }).then(function() {
                    window.location = "/app/sec/main/list";
                });
            } else if (result.dismiss) {
                if (${noLicense}) {
                    noLicenseSwal();
                }
            }
        })
    }

    function noLicenseSwal() {
        Swal.fire({
            html: "<div style=\"float:left;\"><h1>${message(code: 'importMapper.no_license.heading')}</h1><br /><p style=\"text-align: left;\">${message(code: 'importMapper.no_license.body')}<br /><br />" +
                "<ul><li class=\"importMapperNoLicenseList\">${message(code: 'importMapper.no_license.bullet1')} \"${message(code: 'add')}\"</li>" +
                "<li class=\"importMapperNoLicenseList\">${message(code: 'importMapper.no_license.bullet2')} <a href=\"mailto:${emailConfig?.supportEmail}\" target=\"_top\">${message(code: 'contact_support')?.toLowerCase()}</a>.</li>" +
                "<li class=\"importMapperNoLicenseList\">${message(code: 'importMapper.no_license.bullet3')} <a href=\"mailto:${emailConfig?.helloEmail}\" target=\"_top\">${message(code: 'ecommerce.contact_sales')?.toLowerCase()}</a>.</li></ul></p></div>",
            <g:if test="${importFileExists}">
            showCancelButton: true,
            cancelButtonText: "${message(code: "sendMeData.heading")}",
            cancelButtonColor: '#8DC73F',
            </g:if>
            confirmButtonText: "${message(code: "index.back_to_index")}",
            confirmButtonColor: '#CDCDCD',
            customClass: 'swal-medium',
            icon: "warning",
            allowOutsideClick: false
        }).then(result => {
            if (result.value) {
                window.location = "/app/sec/main/list";
            } else if (result.dismiss === Swal.DismissReason.cancel) {
                // function when cancel button is clicked (send data to another user)
                <g:if test="${importFileExists}">
                    sendToAnotherUserSwal();
                </g:if>
            }
        });
    }

    $('#importMapperFileUpload').on('change', function() {
        if ($(this).val()) {
            var validationMessage = "Selected file is not supported! Supported formats are:</br><strong>xls, xlsx, csv, xml, gbxml</strong>";
            var allowedExtensions = ["xls", "xlsx", "csv", "xml", "gbxml"];
            var fileExtension;
            try {
                fileExtension = $(this).val().match(/\.([^\.]+)$/)[1].toLowerCase();
            } catch (err) {
                Swal.fire("${message(code: 'dataLoadingFeature.error')}",validationMessage,"error");
                $(this).val("");
            }

            if (fileExtension && $.inArray(fileExtension, allowedExtensions) === -1) {
                Swal.fire("${message(code: 'dataLoadingFeature.error')}",validationMessage,"error");
                $(this).val("");
            } else {
                $('#messageContent').empty();
            }
        }
    });






</script>
</body>
</html>



