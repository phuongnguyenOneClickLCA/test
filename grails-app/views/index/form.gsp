<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="format-detection" content="telephone=no"/>
    %{--<asset:javascript src="bootstrap.bundle.js"/>--}%
    <asset:javascript src="parsley.js"/>
    <asset:javascript src="countryCallingCodes.js"/>
    <asset:stylesheet src="bootstrap-4.4.1.css" />
    <style>
    .parsley-custom-error-message {
        max-width: 480px;
        padding-top: 4px;
        color: #dc3545;
    }

    span.select2-selection.select2-selection--single {
        outline: none;
    }

    ul {
        margin: 0 0 0px 25px;
    }

    input[type="text"], input[type="password"], input[type="email"] {
        min-width: 220px;
        width: 220px;
    }

    body {
        font-size: 12px;
    }

    h1 {
        font-weight: bold;
        font-size: 20px;
    }
    .navbar .container {
        display: block;
        justify-content: normal;
    }
    .navbar {
        padding: 2px 20px;
    }
    .navbar-inner {
        margin-right: auto;
        margin-left: auto;
    }
    .email {
        position: absolute !important;
        top: -9999px !important;
        left: -9999px !important;
    }
    </style>
</head>

<body>
<div class="container">
    <div class="screenheader">
        <div class="pull-right hide-on-print">
            <g:link action="form" params="[lang: 'en']"><asset:image src="isoflags/gb.png" style="width:25px; height:17px;" /></g:link> |
            <g:link action="form" params="[lang: 'fr']"><asset:image src="isoflags/fr.png" style="width:25px; height:17px;" /></g:link> |
            <g:link action="form" params="[lang: 'de']"><asset:image src="isoflags/de.png" style="width:25px; height:17px;" /></g:link> |
            <g:link action="form" params="[lang: 'es']"><asset:image src="isoflags/es.png" style="width:25px; height:17px;" /></g:link> |
            <g:link action="form" params="[lang: 'fi']"><asset:image src="isoflags/fi.png" style="width:25px; height:17px;" /></g:link> |
            <g:link action="form" params="[lang: 'no']"><asset:image src="isoflags/no.png" style="width:25px; height:17px;" /></g:link> |
            <g:link action="form" params="[lang: 'nl']"><asset:image src="isoflags/nl.png" style="width:25px; height:17px;" /></g:link> |
            <g:link action="form" params="[lang: 'se']"><asset:image src="isoflags/se.png" style="width:25px; height:17px;" /></g:link> |
            <g:link action="form" params="[lang: 'it']"><asset:image src="isoflags/it.png" style="width:25px; height:17px;" /></g:link> |
            <g:link action="form" params="[lang: 'hu']"><asset:image src="isoflags/hu.png" style="width:25px; height:17px;" /></g:link> |
            <g:link action="form" params="[lang: 'jp']"><asset:image src="isoflags/jp.png" style="width:25px; height:17px;" /></g:link>
        </div>
    </div>
</div>

<div class="container section" style="padding-left: 20%;">
    <h1><g:message code="index.create_account"/></h1>
    <div class="">
        <div>
            <g:if test="${errors}">
                <div class="alert alert-error alert-dismissible hide-on-print" role="alert">
                    <i class="fas fa-times pull-left" style="font-size: large; margin-right: 8px; margin-top: 2px;"></i>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h5 style="line-height: normal" class="alert-error"><g:message code="results.incomplete_queries"/></h5>
                    <strong>${errors}</strong>
                </div>
            </g:if>

            <g:form controller="index" name="registerForm" id="registerForm" data-parsley-validate="" data-parsley-trigger="change focusin focusout">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <g:hiddenField name="language" value="${user?.language ? user.language : language}"/>

                <label for="name"><g:message code="resource.full_name"/></label><br/>
                <div class="d-flex" >
                    <g:textField name="name" id="name" type="text"
                                 value="${user?.name}"
                                 class="mandatory form-control is-invalid"
                                 data-parsley-required=""
                                 data-parsley-length="[6, 50]"
                                 data-parsley-error-message="${message(code: 'user.name.size.error')}"
                                 style="height: 28px; width: 220px"
                    />
                    <div class="error-class"></div>
                </div>

                <label for="username"><g:message code="user.details.username"/></label><br/>
                <div class="d-flex" >
                    <g:field name="username" type="email" autocomplete="email"
                         value="${user?.username}"
                         class="mandatory form-control is-invalid"
                         data-parsley-required=""
                         data-parsley-error-message="${message(code: 'user.username.email.invalid')}"
                         style="height: 28px; width: 220px"/>
                </div>

                <div id="email-div" class="d-flex" > %{--this is a honeypot trap for spambots, its made to look like a form element, hidden from our users and validated to be null--}%
                    <g:field name="email" type="email" autocomplete="off"
                             value=""
                             class="form-control is-valid email"
                             data-parsley-error-message="${message(code: 'user.username.email.invalid')}"
                             style="height: 28px; width: 220px"/>
                </div>

                <div id="typeSelect2div" >
                    <label for="type"><g:message code="user.type"/></label><br/>
                    <g:select style="width:220px;" name="type" id="typeSelect" from="${userTypes}"
                              class="mandatory"
                              onchange="toggleStudent(this); removeSelect2Border(this);"
                              optionKey="${{it.key}}"
                              optionValue="${{message(code:it.value)}}"
                              noSelection="['' : '']"
                    />
                    <div id="studentInfo" style="display: none; margin-top: 3px;">${message(code: "register.studentLicense")} <a href="https://www.oneclicklca.com/support/faq-and-guidance/get-one-click-lca/student-license/" target="_blank">${message(code: "account.here")}</a>.</div>
                </div>
                <br/>

                <label id="organizationNameLabel" for="organizationName"><g:message code="user.details.organization"/></label><br/>
                <div class="d-flex" >
                    <g:textField
                        name="organizationName"
                        id="organizationName"
                        data-parsley-required=""
                        data-parsley-length="[3, 50]"
                        data-parsley-error-message="${message(code: 'user.organizationName.size.error')}"
                        value="${user?.organizationName}"
                        class="mandatory form-control is-invalid"
                        style="height: 28px; width: 220px"/>
                </div>

                <label for="countryCallingCodes"><g:message code="user.details.phone"/></label><br/>
                <div class="d-flex" >
                    <select id="countryCallingCodes" style="width: 100px;" onchange="updatePhoneNumber()"></select>
                    <input type="text" id="phoneNumber"
                           class="form-control"
                           data-parsley-type="number"
                           data-parsley-length="[0, 25]"
                           data-parsley-length-message="${message(code: 'user.phoneNumber.size.error')}"
                           data-parsley-type-message="${message(code: 'registration.invalid_char_in_phone_input')}"
                           value="${user?.phone}"
                           onchange="updatePhoneNumber()"
                           style="height: 28px; width: 220px; margin-left: 5px"/>
                    <g:hiddenField id="hiddenPhone" name="phone" value=""/>
                </div>

                <div id="countrySelect2div">
                    <label for="country"><g:message code="account.country"/></label><br/>
                    <g:select name="country" style="width:220px;" id="countrySelect" from="${countries}"
                              class="mandatory"
                              optionKey="${{ it.resourceId }}"
                              optionValue="${{ it.get(localizedName) }}"
                              onchange="removeSelect2Border(this)"
                              noSelection="['': '']"
                    />
                <div class='alert alert-warning hidden' style='margin: 0px 0px 0px 10px !important; display: inline-block; padding: 10px !important; max-width: 500px; white-space: break-spaces'><p>${message(code:'planetary_unavailable')}</p></div>
                <div class='success hidden' style='margin: 0px 0px 0px 10px !important; display: inline-block; padding: 10px !important;max-width: 500px; white-space: break-spaces'><p>${message(code:'planetary_available')}</p></div>
                </div>
                <br/>

                <label for="password"><g:message code="user.details.password"/></label><br/>
                <div class="d-flex" >
                    <g:passwordField
                        name="password" type="password" id="password" value=""
                        data-parsley-required=""
                        data-parsley-minlength="8"
                        data-parsley-uppercase="1"
                        data-parsley-lowercase="1"
                        data-parsley-number="1"
                        data-parsley-error-message="${message(code: 'user.invalidPassword')}"
                        class="mandatory form-control is-invalid"
                        style="height: 28px; width: 220px"
                    />
                </div>

                <label for="passwordAgain" style="margin-top: 0px;"><g:message code="user.details.passwordAgain"/></label><br/>
                <div class="d-flex" >
                    <g:passwordField
                        name="passwordAgain" type="password" id="passwordAgain" value=""
                        data-parsley-required=""
                        data-parsley-equalto="#password"
                        data-parsley-error-message="${message(code: 'user.passwordAgain.user.no.matching.passwords')}"
                        data-parsley-minlength="8"
                        class="mandatory form-control is-invalid"
                        style="height: 28px; width: 220px"
                    />
                </div>

                <div class="consent control-group">
                    <br/>
                    <label for="consent" class="bold control-label"><g:message code="user_consent_label"/></label>
                    <br/>
                    <opt:radioButton name="consent" id="yesConsent" class="consentRadioButton"  onclick="checkMandatoryFields();" value="${Boolean.TRUE}" />
                    <g:message code="user_consent_yes"/>
                    <br/>
                    <opt:radioButton name="consent" id="noConsent" class="consentRadioButton" onclick="checkMandatoryFields();" value="${Boolean.FALSE}" />
                    <g:message code="user_consent_no"/>
                </div>
                <div>
                    <p><g:message code="index.terms" args="[termsAndConditionsUrl]"/></p>
                </div>
                <g:actionSubmit value="${message(code: 'index.register')}" action="createAccount" class="btn btn-primary g-recaptcha" style="background-color: #689c00; font-size: 12px; padding: 4px 10px 4px;"
                                id="register" disabled="disabled"/>
                <g:link action="cancel">
                    <button type="button" style="font-size: 12px; padding: 4px 10px 4px;" class="btn"><g:message code="cancel"/></button>
                </g:link>
            </g:form>
        </div>
    </div>
</div>

<script type="text/javascript">

    window.Parsley.on('field:validated', function(e) {
        if (e.validationResult.constructor!==Array) {
            this.$element.closest('.form-control').removeClass('is-invalid').addClass('is-valid');
        } else {
            this.$element.closest('.form-control').removeClass('is-valid').addClass('is-invalid');
        }
    });

    window.Parsley.addValidator('uppercase', {
        requirementType: 'number',
        validateString: function(value, requirement) {
            var uppercases = value.match(/[A-Z]/g) || [];
            return uppercases.length >= requirement;
        }
    });

    window.Parsley.addValidator('lowercase', {
        requirementType: 'number',
        validateString: function(value, requirement) {
            var lowercases = value.match(/[a-z]/g) || [];
            return lowercases.length >= requirement;
        }
    });

    window.Parsley.addValidator('number', {
        requirementType: 'number',
        validateString: function(value, requirement) {
            var numbers = value.match(/[0-9]/g) || [];
            return numbers.length >= requirement;
        }
    });


    function toggleStudent(elem) {
        var value = $(elem).val();
        var label = $("#organizationNameLabel")

        var textStudent = "${g.message(code : "user.details.educationalInstitute")}";
        var textOrg = "${g.message(code : "user.details.organization")}";

        if (value === "Student") {
            $('#studentInfo').show();
            label.text(textStudent)
        } else {
            $('#studentInfo').hide();
            label.text(textOrg)
        }
    }


    $(document).ready(function () {
        loadCountryCallingCodes('#countryCallingCodes')

        var typeSelect = $("#typeSelect");
        var countrySelect = $("#countrySelect");
        var $parent = $("#registerForm");

        $parent.on('input change','.mandatory', function() {
            checkMandatoryFields()
        });

        typeSelect.select2({
            placeholder:'<g:message code="select"/>',
            minimumResultsForSearch: -1/*,
            allowClear: true*/ //Can't see a reason for this, as you can simply select a different option and its mandatory to pick one
        }).maximizeSelect2Height();
        countrySelect.select2({
            placeholder:'<g:message code="select"/>'
        }).maximizeSelect2Height();

        $('#typeSelect2div .select2-container').css('border', '1px solid #dc3545');
        $('#countrySelect2div .select2-container').css('border', '1px solid #dc3545');
        $(countrySelect).on("change",function () {
            var valueOfSelect = $(countrySelect).val()
            var countriesWithPlanetary = ${countriesWithPlanetary as grails.converters.JSON};
            if(countriesWithPlanetary && countriesWithPlanetary.length > 0){
                var parentDiv = $(countrySelect).parent()
                $(parentDiv).find().remove()
                if(countriesWithPlanetary.indexOf(valueOfSelect) != -1){
                    $(parentDiv).find(".success").removeClass("hidden")
                    $(parentDiv).find(".alert-warning").addClass("hidden")
                } else {
                    $(parentDiv).find(".success").addClass("hidden")
                    $(parentDiv).find(".alert-warning").removeClass("hidden")
                }
            }
        })
        $(window).keydown(function (event) {
            if (event.keyCode == 13) {
                event.preventDefault();
                return false;
            }
        });

        <g:if test="${validationFail}" >
            $("#registerForm").parsley().validate()
        </g:if>
    });

    function removeSelect2Border(elem) {
        $(elem).siblings(".select2-container").css('border', '1px solid #28a745');
    }

    function checkMandatoryFields() {
        var mandatoryFields = $('.mandatory');
        var consentFields = $('.consentRadioButton');
        var registerbtn = $('#register');
        var empty = false;

        for (var i = 0; i < mandatoryFields.length; i++) {
            var mandatoryField = mandatoryFields[i];

            if ($(mandatoryField).val() === '') {
                empty = true;
            }
        }

        if (empty) {
            registerbtn.attr('disabled', 'disabled');
        } else {
            var oneConsentSelected = false;

            for (var j = 0; j < consentFields.length; j++) {
                var consentField = consentFields[j];

                if ($(consentField).is(':checked')) {
                    oneConsentSelected = true;
                    break;
                }
            }

            if (oneConsentSelected) {
                registerbtn.removeAttr('disabled');
            }
        }
    }

    function validatePassword(){

        let pass = $("#password").val()

        if(pass) {
            let isLongEnough = pass.length >= 8
            let isContainNumber = pass.match(/.*\d.*!/)
            let isContainingUpperCase = pass.match(/.*[a-z].*!/)
            let isContainingLowerCase = pass.match(/.*[A-Z].*!/)

            return isLongEnough && isContainingUpperCase && isContainingLowerCase
        } else {
            return false
        }

    }

</script>
</body>
</html>


                
             