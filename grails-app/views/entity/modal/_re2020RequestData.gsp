<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">&times;</button>

    <h2><g:message code="re2020_request_data.header"/></h2>
</div>

<div class="modal-body">

    <div id="messageContentModal">
        <div id="flash-fadeSaveAlert-modal" class="alert alert-success fadetoggle hide-on-print hidden"
             data-fadetoggle-delaytime="5000">
            <i class="far fa-thumbs-up pull-left" style="font-size: large; margin-right: 8px;"></i>
            <i data-dismiss="alert" class="close" style="top: 0px;">×</i>
            <strong><span id="successMessage"></span></strong>
        </div>
    </div>

    <div id="stepModal1" class="hidden modalStep">
        <g:form action="sendDemandeurRequest" controller="epdRequest" name="sendRe2020DemandeurDataForm"
                id="sendRe2020DemandeurDataForm">
            <div class="inline-block"><h4><g:message code="re2020_request_data.form1.header"/></h4></div>

            <p><g:message code="re2020_request_data.form1.description"/></p>

            <table class="table-condensed table-condensed">

                <tr>
                    <td><label><strong><g:message code="user.details.lastname"/></strong></label></td>
                    <td>
                        <g:field name="nom" type="text"
                                 value="${user?.lastname}"
                                 class="mandatory form-control is-invalid input-field-width"
                                 data-parsley-required=""
                                 data-parsley-length="[3, 50]"
                                 data-parsley-error-message="${message(code: 'user.firstname.size.error')}"/>
                    </td>
                </tr>

                <tr>
                    <td><label><strong><g:message code="user.details.firstname"/></strong>
                    </label></td>
                    <td>
                        <g:field name="prenom" type="text"
                                 value="${user?.firstname}"
                                 class="mandatory form-control is-invalid input-field-width"
                                 required=""
                                 data-parsley-required=""
                                 data-parsley-length="[3, 50]"
                                 data-parsley-error-message="${message(code: 'user.firstname.size.error')}"/>
                    </td>
                </tr>

                <tr>
                    <td><label><strong><g:message
                            code="user.details.jobtitle"/></strong></label>
                    </td>
                    <td>
                        <g:field name="metier" type="text"
                                 required=""
                                 class="mandatory form-control is-invalid input-field-width"
                                 data-parsley-required=""
                                 data-parsley-length="[3, 50]"
                                 data-parsley-error-message="${message(code: 'user.jobtitle.size.error')}"/>
                    </td>
                </tr>

                <tr>
                    <td><label><strong><g:message code="user.details.username"/></strong></label></td>
                    <td>
                        <g:field name="mail" type="email" autocomplete="email"
                                 value="${user?.username}"
                                 class="mandatory form-control is-invalid input-field-width"
                                 required=""
                                 data-parsley-required=""
                                 data-parsley-error-message="${message(code: 'user.username.email.invalid')}"/>
                    </td>
                </tr>
                <tr>
                    <td><label><strong><g:message code="user.details.phone"/></strong></label></td>
                    <td>
                        <g:field name="telephone" type="tel" autocomplete="phone"
                                 value="${user?.phone}"
                                 class="mandatory form-control is-invalid input-field-width"
                                 required=""
                                 pattern="^\\+(?:[0-9]●?){4,25}[0-9]\$"
                                 data-parsley-required=""
                                 data-parsley-error-message="${message(code: 'user.phone.size.error')}"/>
                    </td>
                </tr>
                <tr>
                    <td><label><strong><g:message code="user.details.organization"/></strong></label></td>
                    <td>
                        <g:field name="entreprise" type="text"
                                 id="organizationName"
                                 required=""
                                 data-parsley-required=""
                                 data-parsley-length="[3, 50]"
                                 data-parsley-error-message="${message(code: 'user.organizationName.size.error')}"
                                 value="${user?.organizationName}"
                                 class="mandatory form-control is-invalid input-field-width"/>
                    </td>
                </tr>
            </table>
        </g:form>
    </div>

    <div id="stepModal2" class="hidden modalStep">
        <g:form action="sendRe2020DataRequest" controller="epdRequest" name="sendRe2020DataRequestForm"
                id="sendRe2020DataRequestForm">

            <div class="inline-block"><h4><g:message code="re2020_request_data.form2.header"/></h4></div>

            <p><g:message code="re2020_request_data.form2.description"/></p>
            <table class="table-condensed table-condensed table-striped" style="width: 90% !important">
                <tr>
                    <td><label><strong><g:message code="re2020.creationArgument"/></strong></label></td>
                    <td>
                        <g:field name="creationArgument" type="text"
                                 id="creationArgument"
                                 required=""
                                 data-parsley-required=""
                                 data-parsley-length="[3, 50]"
                                 data-parsley-error-message="${message(code: 're2020.error')}"
                                 class="mandatory form-control is-invalid input-field-width"/>
                    </td>
                </tr>
                <tr>
                    <td><label><strong><g:message code="re2020.creationIdentification"/></strong></label></td>
                    <td>
                        <g:field name="creationIdentification" type="text"
                                 id="creationIdentification"
                                 required=""
                                 data-parsley-required=""
                                 data-parsley-length="[3, 50]"
                                 data-parsley-error-message="${message(code: 're2020.error')}"
                                 class="mandatory form-control is-invalid input-field-width"/>
                    </td>
                </tr>
                <tr>
                    <td><label><strong><g:message code="re2020.creationSource"/></strong></label></td>
                    <td>
                        <g:field name="creationSource" type="text"
                                 id="creationSource"
                                 required=""
                                 data-parsley-required=""
                                 data-parsley-length="[3, 50]"
                                 data-parsley-error-message="${message(code: 're2020.error')}"
                                 class="mandatory form-control is-invalid input-field-width"/>
                    </td>
                </tr>
                <tr>
                    <td><label><strong><g:message code="re2020.creationUf"/></strong></label></td>
                    <td>
                        <g:field name="creationUf" type="text"
                                 id="creationUf"
                                 data-parsley-required=""
                                 data-parsley-length="[3, 50]"
                                 data-parsley-error-message="${message(code: 're2020.error')}"
                                 class="mandatory form-control is-invalid input-field-width"/>
                    </td>
                </tr>
                <tr>
                    <td><label><strong><g:message code="re2020.creationLabel"/></strong></label></td>
                    <td>
                        <g:field name="creationLabel" type="text"
                                 id="creationLabel"
                                 required=""
                                 data-parsley-required=""
                                 data-parsley-length="[3, 50]"
                                 data-parsley-error-message="${message(code: 're2020.error')}"
                                 class="mandatory form-control is-invalid input-field-width"/>
                    </td>
                </tr>
                <tr>
                    <td><label><strong><g:message code="re2020.nomenclature"/></strong></label></td>
                    <td>
                        <g:field name="nomenclature" type="radio"
                                 id="hasNomenclature"
                                 value="true"
                                 onClick="displayNomenclatureOption()"
                                 required=""
                                 data-parsley-error-message="${message(code: 're2020.error')}"
                                 class="mandatory form-control is-invalid"/>
                        <label for="hasNomenclature"
                               style="display: initial">${message(code: 're2020.hasNomenclature')}</label><br>
                        <g:field name="nomenclature" type="radio"
                                 id="noNomenclature"
                                 value="false"
                                 onClick="displayNomenclatureOption()"
                                 required=""
                                 data-parsley-error-message="${message(code: 're2020.error')}"
                                 class="mandatory form-control is-invalid"/>
                        <label for="noNomenclature"
                               style="display: initial">${message(code: 're2020.noNomenclature')}</label>
                    </td>
                </tr>

                <tr id="creationRankIdOption" class="hidden">
                    <td><label><strong><g:message code="re2020.creationRankId"/></strong></label></td>
                    <td>
                        <g:field name="creationRankId" type="text"
                                 id="creationRankId"
                                 pattern="[0-9]+"
                                 data-parsley-required=""
                                 data-parsley-length="[3, 50]"
                                 data-parsley-error-message="${message(code: 're2020.error_nomenclature')}"
                                 class="mandatory form-control is-invalid input-field-width"/>
                    </td>
                </tr>

                <tr id="creationRankNameOption" class="hidden">
                    <td><label><strong><g:message code="re2020.creationRankName"/></strong></label></td>
                    <td>
                        <g:field name="creationRankName" type="text"
                                 id="creationRankName"
                                 data-parsley-required=""
                                 data-parsley-length="[3, 50]"
                                 data-parsley-error-message="${message(code: 're2020.error')}"
                                 class="mandatory form-control is-invalid input-field-width"/>
                    </td>
                </tr>
                <tr id="creationRankParentOption" class="hidden">
                    <td><label><strong><g:message code="re2020.creationRankParent"/></strong></label></td>
                    <td>
                        <g:field name="creationRankParent" type="text"
                                 id="creationRankParent"
                                 pattern="[0-9]+"
                                 data-parsley-required=""
                                 data-parsley-length="[3, 50]"
                                 data-parsley-error-message="${message(code: 're2020.error_nomenclature')}"
                                 class="mandatory form-control is-invalid input-field-width"/>
                    </td>
                </tr>
            </table>
        </g:form>
    </div>
</div>

<div class="modal-footer">
    <button class="btn btn-default pull-left" id="cancelBtn" type="button" onclick="closeModal()"><g:message
            code="cancel"/></button>
    <button class="btn btn-primary pull-right hidden" id="confirmDemandeurBtn"
            form="sendRe2020DemandeurDataForm" hidden>
        <i class="fas fa-circle-notch fa-spin hidden" style="margin-right: 5px;" id="confirmDemandeurBtnSpinner"></i>
        <g:message code="continue"/></button>
    <button class="btn btn-primary pull-right hidden" id="confirmDataRequestBtn"
            form="sendRe2020DataRequestForm">
        <i class="fas fa-circle-notch fa-spin hidden" style="margin-right: 5px;" id="confirmDataRequestBtnSpinner"></i>
        <g:message code="send_me_data.btn_send"/>
    </button>
</div>

<script type="text/javascript">
    var currentStep = 1;
    setupForm();

    function setupForm() {
        $(".form-control").on("change keypress", function () {
            $(this).parsley().validate()
        });

        $("#sendRe2020DemandeurDataForm").submit(function (event) {
            // Prevent default behavior
            event.preventDefault();
            $("#confirmDemandeurBtnSpinner").removeClass("hidden");
            $("#confirmDemandeurBtn").attr("disabled", true);

            // Handle sending AJAX request to controller
            $.ajax({
                url: '/app/sec/epdRequest/sendDemandeurRequest',
                type: 'POST',
                data: new FormData($("#sendRe2020DemandeurDataForm").get(0)),
                processData: false,
                contentType: false,
                success: function (data) {

                    if (data.output === "error") {
                        // Button enabled
                        $("#confirmDemandeurBtnSpinner").addClass("hidden");
                        $("#confirmDemandeurBtn").attr("disabled", false);

                        // Display error message
                        let errorMessage = '<div id="flash-fadeErrorAlert-modal" class="alert alert-error fadetoggle hide-on-print" data-fadetoggle-delaytime="5000"><i class="fas fa-times pull-left" style="font-size: large; margin-right: 8px;"></i><i data-dismiss="alert" class="close" style="top: 0px;">×</i><strong>' + data.msg + '</strong></div>';
                        $(errorMessage).appendTo("#messageContentModal");

                    } else {
                        // Success, showing next form
                        $("#stepModal" + currentStep.toString()).addClass("hidden");
                        currentStep++;
                        $("#stepModal" + currentStep.toString()).removeClass("hidden");

                        // Display success message
                        $("#successMessage").text(data.output)
                        $("#flash-fadeSaveAlert-modal").removeClass("hidden");
                        changeBtnVisibility(currentStep);
                    }


                },
                error: function (data) {
                    $("#confirmDemandeurBtnSpinner").addClass("hidden");
                    $("#confirmDemandeurBtn").attr("disabled", false);

                    // Display error message
                    $("#errorMessage").text(data.msg)
                    $("#flash-fadeErrorAlert-modal").removeClass("hidden");
                }
            });

        });

        $("#sendRe2020DataRequestForm").submit(function (event) {
            // Prevent default behavior
            event.preventDefault();
            $("#confirmDataRequestBtnSpinner").removeClass("hidden");
            $("#confirmDataRequestBtn").attr("disabled", true);

            // Handle sending AJAX request to controller
            $.ajax({
                url: '/app/sec/epdRequest/sendRe2020DataRequest',
                type: 'POST',
                data: new FormData($("#sendRe2020DataRequestForm").get(0)),
                processData: false,
                contentType: false,
                success: function (data) {

                    if (data.output === "error") {

                        // Button enabled
                        $("#confirmDataRequestBtnSpinner").addClass("hidden");
                        $("#confirmDataRequestBtn").attr("disabled", false);

                        // Display error message
                        let errorMessage = '<div id="flash-fadeErrorAlert-modal" class="alert alert-error fadetoggle hide-on-print" data-fadetoggle-delaytime="5000"><i class="fas fa-times pull-left" style="font-size: large; margin-right: 8px;"></i><i data-dismiss="alert" class="close" style="top: 0px;">×</i><strong>' + data.msg + '</strong></div>';
                        $(errorMessage).appendTo("#messageContentModal");

                    } else {
                        // Closes modal, flash message is displayed
                        closeModal();
                        $(".alert-success").alert('close')

                    }
                },
                error: function (error, textStatus) {
                    console.log(error + ": " + textStatus);
                    $("#confirmDataRequestBtnSpinner").addClass("hidden");
                    $("#confirmDataRequestBtn").attr("disabled", false);
                }
            });
        });

        if ("${user?.idDemandeur}".length > 0) {
            currentStep = 2;
        }

        $("#stepModal" + currentStep.toString()).removeClass("hidden");
        changeBtnVisibility(currentStep);
    }

    function closeModal() {
        $("#re2020RequestDataModal").modal("hide");
    }

    function changeBtnVisibility(step) {
        if (step === 1) {
            $("#confirmDemandeurBtn").removeClass("hidden");
        } else if (step === 2) {
            $("#confirmDemandeurBtn").addClass("hidden");
            $("#confirmDataRequestBtn").removeClass("hidden");
        }
    }

    function displayNomenclatureOption() {
        let selectedOption = $('input[name="nomenclature"]:checked').attr('id');

        if (selectedOption === "hasNomenclature") {
            $("#creationRankIdOption").removeClass();
            $("#creationRankId").attr("required", true);

            $("#creationRankNameOption").addClass("hidden");
            $("#creationRankParentOption").addClass("hidden");
            $("#creationRankName").attr("required", false);
            $("#creationRankParent").attr("required", false);
        } else if (selectedOption === "noNomenclature") {
            $("#creationRankNameOption").removeClass();
            $("#creationRankParentOption").removeClass();
            $("#creationRankName").attr("required", true);
            $("#creationRankParent").attr("required", true);

            $("#creationRankIdOption").addClass("hidden");
            $("#creationRankId").attr("required", false);
        }
    }

    // function appendDataToSummary() {
    //     var dataValue = $(".sendingParameter")
    //     $(dataValue).each(function (i) {
    //         var item = $(dataValue[i])
    //         var value = $(item).val()
    //         var id = $(item).attr("name")
    //         if ($(item).attr("type") == "checkbox") {
    //             if ($(item).is(":checked")) {
    //                 $("[data-id='" + id + "'").removeClass("hidden")
    //             } else {
    //                 $("[data-id='" + id + "'").addClass("hidden")
    //             }
    //         } else {
    //             if ($(item).is("select")) {
    //                 value = $(item).children("option:selected").text()
    //             }
    //             $("[data-id='" + id + "'").text(value)
    //         }
    //     })
    // }

</script>