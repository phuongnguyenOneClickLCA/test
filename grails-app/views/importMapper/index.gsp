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
<g:uploadForm action="fileToSession" useToken="true" name="theForm" method="post" novalidate="novalidate">

    <div class="container">
        <g:if test="${outdatedPluginError}">${outdatedPluginError}</g:if>

        <div class="screenheader">
        <opt:breadcrumbsNavBar indicator="${indicator}" parentEntity="${entity}" childEntity="${childEntity}" specifiedHeading="${message(code: 'import_data')}"/>
            <div id="stepsHeader">
            <g:if test="${showSteps}">
                <opt:breadcrumbs current="${1}"/>
            </g:if>
            </div>
            <h1 style="display: inline;">${header}</h1>
            <div class="pull-right hide-on-print" id="actions">
                <g:if test="${entityId}">
                    <opt:link controller="entity" action="show" params="[entityId: entityId]" class="btn btn-block importmapper-headerbuttons" onclick="return modalConfirm(this);"
                          data-questionstr="${message(code: 'importMapper.index.back')}"
                          data-truestr="${message(code: 'yes')}" data-falsestr="${message(code: 'no')}"
                          data-titlestr="${message(code: 'back')}">
                    <g:message code="back" /></opt:link>
                </g:if>
                <g:else>
                    <opt:link controller="main" removeEntityId="true" class="btn btn-block importmapper-headerbuttons" onclick="return modalConfirm(this);"
                              data-questionstr="${message(code: 'importMapper.index.back')}"
                              data-truestr="${message(code: 'yes')}" data-falsestr="${message(code: 'no')}"
                              data-titlestr="${message(code: 'back')}">
                        <g:message code="back" /></opt:link>
                </g:else>
                <g:if test="${importFileExists}">
                    <g:link controller="importMapper" action="downloadExcelFile" class="btn btn-primary" elementId="alreadyInSessionExcel"><g:message code="results.expand.download_excel" /></g:link>
                    <a class="btn btn-primary" href="javascript:" onclick="sendToAnotherUserSwal()"><g:message code="sendMeData.heading"/></a>
                </g:if>
                <opt:submit name="save" onclick="clearMessages();"
                            value="${message(code: 'continue')}" class="btn btn-primary btn-block importmapper-headerbuttons${noLicense ? ' removeClicks' : ''}" id="save"
                            disabled="disabled" />
            </div>
        </div>
    </div>

    <div class="container section">
        <div class="sectionbody importmapper">
            <opt:spinner/>


            <g:if test="${importMapperFound && indicatorsFound}">
                <g:hiddenField name="applicationId" value="${applicationId}"/>
                <g:hiddenField name="importMapperId" value="${importMapperId}"/>
                <input type="hidden" name="newDesign" id="newDesignHidden" value=""/>

                <div id="left" style="width: 100%;">
                    <g:if test="${!importFileExists}">
                    <div class ="container-fluid center-block" style="margin-left:50px !important; margin-right: 50px !important;">
                        <div class="pull-left">
                            <h2><g:message code="importMapper.index.choose_data"/></h2>
                            <div class="control-group" style="margin-bottom: 30px;">
                                <label class="control-label">
                                    <strong><g:message code="importMapper.index.choose_file"/></strong>
                                    <g:if test="${appHelpUrl}">&nbsp;
                                        <a href="${appHelpUrl}" target="_blank" class="helpLink"><i
                                                class="fa fa-question margin-right-5"></i><g:message
                                                code="help.instructions"/></a>
                                    </g:if>
                                </label>
                                <input type="file" ${noLicense ? 'disabled=\"disabled\"' : ''} name="importFile"
                                       id="importMapperFileUpload" onchange="removeBorder(this);" class="input-xlarge" value=""/>
                                <g:if test="${fileMaxSize}">
                                    (max. ${fileMaxSize} KB)
                                </g:if>
                            </div>
                        </div>
                    </div>
                    </g:if>


                    <div class="container-fluid center-block" style="margin-left:50px !important; margin-right: 50px !important; padding-left:25px;">
                        <h2><g:message code="importMapper.index.choose_target"/></h2>
                    </div>

                    <div class="container-fluid center-block borderbox"
                         style="margin-left:50px !important; margin-right: 50px !important; padding-left:25px; padding-top:10px; ${bimModelChecker? 'max-width:850px;' : ''}">

                        <div>
                            <div class="control-group" style="float: left; margin-left: 15px;">
                                <label><strong><g:message code="importMapper.index.where_to_import"/></strong></label>
                                <select style="width: 175px;" name="entityId" id="entityId" class="autocompletebox" ${noLicense ? 'disabled=\"disabled\"' : ''}>
                                    <g:each in="${entities}" var="entity">
                                        <option value="${entity.get("_id")}" ${entityId && entityId.toString().equals(entity.get("_id").toString()) || userDefaults?.get("entityId")?.equals(entity.get("_id").toString()) ? ' selected=\"selected\"' : ''}>${entity.get("name")}</option>
                                    </g:each>
                                </select>
                            </div>
                        </div>

                        <div class="control-group" id="designs" style="float: left;">
                            <label id="newDesign">
                                <g:if test="${'operating'.equals(indicatorUse)}">
                                    <strong><g:message code="importMapper.index.choose_period"/></strong>
                                </g:if>
                                <g:else>
                                    <strong><g:message code="importMapper.index.choose_design"/></strong>
                                </g:else>
                            </label>
                            <select style="width: 175px;" name="childEntityId" id="designId" ${noLicense ? 'disabled=\"disabled\"' : ''}></select>
                        </div>

                        <div class="control-group" style="float: left;">
                            <label><strong><g:message code="importMapper.index.what_to_calculate"/></strong></label>
                            <g:if test="${fixedIndicator && !indicatorId}">
                                <input type="hidden" name="indicatorId"
                                       value="${fixedIndicator.indicatorId}"/> ${fixedIndicator.localizedName}
                            </g:if>
                            <g:else>
                                <select style="width: 175px;"  name="indicatorId"
                                        id="indicatorId" ${noLicense ? 'disabled=\"disabled\"' : ''} onchange="getFilters();"></select>
                            </g:else>
                        </div>

                        <div class="control-group" style="float: left;">
                            <g:if test="${bimModelChecker}">
                                <label>&nbsp;</label>
                                <g:hiddenField name="bimModelChecker" value="${bimModelChecker}" />
                            </g:if>
                            <g:else>
                                <label><strong><g:message code="importMapper.index.import_mode"/></strong>
                                    <a href="#" class="importMapperHelpPopover" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${message(code: 'importMapper.index.fastAndSilent.info')}"><i class="icon-question-sign"></i></a>
                                </label>
                                <input style="width: 25px; !important; height: 17px; !important;" ${noLicense ? 'disabled=\"disabled\"' : ''} class="fastAndSilentCheckbox" type="checkbox" name="fastAndSilent" /> <g:message code="importMapper.index.fastAndSilent" />
                            </g:else>
                        </div>

                        <div class="control-group" style="float: left;">

                            <label><strong><g:message code="importMapper.unitSystem" /></strong>
                                <a href="#" class="importMapperHelpPopover" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${message(code: 'importMapper.unitSystem.info')}"><i class="icon-question-sign"></i></a>
                            </label>
                            <g:if test="${imperial}">
                                <input style="width: 25px; !important; height: 17px; !important;" checked="checked" ${noLicense ? 'disabled=\"disabled\"' : ''} type="checkbox" name="convertToImperial" id="convertToImperial" /> <g:message code="importMapper.unitSystem.toImperial" />
                            </g:if>
                            <g:else>
                                <input style="width: 25px; !important; height: 17px; !important;" checked="checked" type="checkbox" name="convertToMetric" ${noLicense ? 'disabled=\"disabled\"' : ''} id="convertToMetric" /> <g:message code="importMapper.unitSystem.toMetric" />
                            </g:else>
                            <opt:submit name="go" value="${message(code: 'importMapper.import.go')}"
                                        onclick="clearMessages();"
                                        class="btn btn-primary btn-block pull-right${noLicense ? ' removeClicks' : ''}"
                                        style="width: 80px !important; margin-left:30px;" id="go"/>

                        </div>
                    </div>

                    <div class="clearfix"></div>

                    <div class="container-fluid text-center" style="padding-top:20px; padding-bottom: 20px;">
                        <a href="javascript:" class="btn btn-primary" id="moreSettingsBtn"><i id="toggler" class="icon icon-white icon-plus"></i> <g:message code="importMapper.index.more_settings"/></a>
                    </div>
                </div>

                <div style="width: 100%;" id="moreSettings" class="hidden">
                    <div class="container-fluid center-block " style="margin-left:25px !important; margin-right: 25px !important;  padding-bottom:10px;">
                        <h2><g:message code="importMapper.index.settings"/></h2>
                    </div>

                    <div>
                        <div class="container-fluid center-block borderbox" id="moreWrapper"
                             style="margin-left:50px !important; margin-right: 50px !important; padding-bottom:10px; padding-top:10px;">
                            <div class="control-group pull-left">
                                <label class="checkbox" for="removeEmptyData"><g:message code="importMapper.index.remove_empty_data"/></label>
                                <input style="width: 25px; !important; height: 17px; !important; margin-left: 15px;" type="checkbox" checked="checked" name="removeEmptyData" id="removeEmptyData" ${noLicense ? "disabled=\"disabled\"" : ""} />
                                <a href="#" class="importMapperHelpPopover" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="<g:message code="importMapper.index.remove_empty_data_desc"/>"><i class="icon-question-sign"></i></a>
                            </div>

                            <div class="control-group pull-left">
                                <label><g:message code="importMapper.index.filters"/></label>
                                <g:if test="${presetFilters}">
                                    <select name="filters" ${noLicense ? 'disabled=\"disabled\"' : ''} id="filters">
                                        <g:each in="${presetFilters}" var="presetFilter">
                                            <option value="${presetFilter.presetFilterId}">${presetFilter.localizedName}</option>
                                        </g:each>
                                    </select>
                                </g:if>
                            </div>

                            <g:if test="${collapseUsed}">
                                <div class="control-group pull-left">
                                    <label class="checkbox" for="combine"><g:message code="importMapper.index.manual_combine"/></label>
                                    <input style="width: 25px; !important; height: 17px; !important; margin-left: 15px;" class="disableFastAndSilent" type="checkbox" name="combine" id="combine" ${noLicense ? "disabled=\"disabled\"" : ""} />
                                    <a href="#" class="importMapperHelpPopover" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="<g:message code="importMapper.index.manual_combine_desc"/>"><i class="icon-question-sign"></i></a>
                                </div>
                            </g:if>

                            <div class="control-group pull-left">
                                <label class="checkbox" for="incrementalImport"><g:message code="importMapper.index.incremental_import"/></label>
                                <input style="width: 25px; !important; height: 17px; !important; margin-left: 15px;" class="disableFastAndSilent" type="checkbox" name="incrementalImport" id="incrementalImport" ${noLicense ? "disabled=\"disabled\"" : ""} />
                                <a href="#" class="importMapperHelpPopover" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="<g:message code="importMapper.index.incremental_import_desc"/>"><i class="icon-question-sign"></i></a>
                            </div>

                            <div class="control-group pull-left">
                                <label class="checkbox" for="temporaryCalculation"><g:message code="importMapper.index.temporary_calculation"/></label>
                                <input style="width: 25px; !important; height: 17px; !important; margin-left: 15px;" class="disableFastAndSilent" type="checkbox" name="temporaryCalculation" id="temporaryCalculation" ${noLicense ? "disabled=\"disabled\"" : ""} />
                                <a href="#" class="importMapperHelpPopover" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="<g:message code="importMapper.index.temporary_calculation_desc"/>"><i class="icon-question-sign"></i></a>
                            </div>
                        </div>
                    </div>
                </div>
                </br>
            </g:if>
        </div>
    </div>
    <div class="modal hide modalDesignScope" id="modifyModalDiv"></div>


</g:uploadForm>

<script type="text/javascript">
    $('#moreSettingsBtn').on('click', function () {
        var userAgent = navigator.userAgent;
        var form = $('#moreSettings');
        var toggler = "#toggler";
        $(toggler).toggleClass('icon-plus icon-minus');

        if (userAgent.toLowerCase().indexOf("mac") >= 0) {
            if ($(toggler).hasClass('icon-plus')) {
                $(form).addClass('hidden');
            } else {
                $(form).removeClass('hidden');
            }
        } else {
            if ($(toggler).hasClass('icon-plus')) {
                $(form).slideUp().addClass('hidden');
            } else {
                $(form).slideDown().removeClass('hidden');
            }
        }
    });
    $(function(){
        $.widget("ui.combobox", {
            _create: function () {
                var required = this.element.attr("required");

                if (required) {
                    this.wrapper = $("<div>")
                        .insertBefore(this.element)
                        .attr('class', 'input-append redBorder')
                }
                else {
                    this.wrapper = $("<div>")
                        .insertBefore(this.element)
                        .attr('class', 'input-append');
                }
                this._createAutocomplete();
                this._createShowAllButton();
            },

            _createAutocomplete: function (args) {
                var selected = this.element.children(":selected"),
                    value = selected.val(),
                    label = selected.text(),
                    elementname = this.element.attr('name');

                this.element.attr('name', '');
                this.element.css('display', 'none');

                var placecholder = this.element.attr('data-autocomplete-placeholder');
                var id = this.element.attr("id");

                this.hiddenvalue = $("<input>")
                    .appendTo(this.wrapper)
                    .attr("type", "hidden")
                    .attr("name", elementname)
                    .val(value);

                this.input = $("<input>")
                    .appendTo(this.wrapper)
                    .val(label)
                    .attr("title", "")
                    .attr("type", "text")
                    .attr("placeholder", placecholder)
                    .attr("onchange", onchange)
                    .attr("id", id)
                    .attr("style", "width: 175px;")
                    .addClass("input-xlarge")
                    .autocomplete({
                        delay: 1,
                        minLength: 0,
                        source: $.proxy(this, "_source"),
                        change: function (event, ui) {
                            getChildren();
                            getIndicators();
                        }
                    })
                    .removeClass('ui-autocomplete-input')
                    .tooltip({
                        tooltipClass: "ui-state-highlight"
                    });

                if (!value) {
                    this.input.val("");
                }

                this._on(this.input, {
                    autocompleteselect: function (event, ui) {
                        ui.item.option.selected = true;
                        this.hiddenvalue.val(ui.item._value);
                        this._trigger("select", event, {
                            item: ui.item.option
                        });
                    },

                    autocompletechange: "_removeIfInvalid"
                });
            },

            _createShowAllButton: function () {
                var wasOpen = false;
                var input = this.input;

                var showall = $("<a>")
                    .attr("tabIndex", -1)
                    //.attr("title", "Show All Items")
                    .tooltip()
                    .appendTo(this.wrapper)
                    .button({
                        icons: {
                            primary: "ui-icon-triangle-1-s"
                        },
                        text: false
                    })
                    .removeClass("ui-corner-all")
                    .addClass("add-on")
                    .append('<i class="icon-chevron-down"></i>')
                    .on('mousedown', function () {
                        wasOpen = input.autocomplete("widget").is(":visible");
                    }).on('click', function () {
                        input.trigger('focus');

                        if (wasOpen) {
                            return false;
                        }

                        input.autocomplete("search", "");
                    });
            },

            _source: function (request, response) {
                /*var matcher = new RegExp($.ui.autocomplete.escapeRegex(request.term), "i");
                response(this.element.children("option").map(function () {
                    var text = $(this).text();
                    if (this.value && ( !request.term || matcher.test(text) ))
                        return {
                            label: $.trim(text),
                            value: $.trim(text),
                            _value: $(this).val(),
                            option: this
                        };
                }));*/
                var matcher = new RegExp($.ui.autocomplete.escapeRegex(request.term), "i")
                var src = this.element.children("option").map(function () {
                    var text = $(this).text();
                    if (this.value && ( !request.term || matcher.test(text) ))
                        return {
                            label: $.trim(text),
                            value: $.trim(text),
                            _value: $(this).val(),
                            option: this
                        };
                })
                var results = $.ui.autocomplete.filter(src, request.term);

                response(results.slice(0, 50));
            },

            _removeIfInvalid: function (event, ui) {

                if (ui.item) {
                    return;
                }

                var value = this.input.val(),
                    valueLowerCase = value.toLowerCase(), valid = false;

                if (jQuery.trim(value) == '') {
                    this.hiddenvalue.val('');
                }

                this.element.children("option").each(function () {
                    if ($(this).text().toLowerCase() === valueLowerCase) {
                        this.selected = valid = true;
                        return false;
                    }
                });

                if (valid) {
                    return;
                }

                this.input
                    .val("")
                    .attr("title", value + " didn't match any item")
                    .tooltip("open");

                this.hiddenvalue.val('');

                this.element.val("");
                this._delay(function () {
                    this.input.tooltip("close").attr("title", "");
                }, 2500);
                this.input.data("ui-autocomplete").term = "";
            },

            _destroy: function () {
                this.wrapper.remove();
                this.element.show();
            }
        });
        getChildren();
        getIndicators();
        getFilters();
    });

    $("#theForm").on('submit', function () {

        var notSubmit = checkModalRequiredValue()

        if(notSubmit) {
            //e.preventDefault()
            return false
        } else {
            $("#loadingSpinner").show();
            $("#left").hide();
            $("#right").hide();
            document.getElementById("save").disabled = true;
            document.getElementById("go").disabled = true;
            var excelDownloadButton = $("#alreadyInSessionExcel");
            $(excelDownloadButton).addClass("disabled");
            disableLink(excelDownloadButton);

            $("#modifyModalDiv").modal("hide");
            hideSettingsAndHandleSteps()
            return true
        }

    });

    $(function () {
        $(".autocompletebox").combobox();

        if (${noLicense}) {
            $("#entityId").prop("disabled", true);
            setTimeout(function () {
                noLicenseSwal();
            }, 250);
        }
    });

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
            } else if (result.dismiss === swal.DismissReason.cancel) {
                // function when cancel button is clicked (send data to another user)
                <g:if test="${importFileExists}">
                    sendToAnotherUserSwal();
                </g:if>
            }
        });
    }

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

    $('#go,#save').on("click", function() {
        var designId = $('#designId').val();
        var newDesign = $('#designId option:selected').text() === "New design";

        if ($("#importMapperFileUpload").length) {
            if (!$("#importMapperFileUpload").val()) {
                var warning = " <div class=\"alert alert-error hide-on-print\">\n" +
                    "               <button data-dismiss=\"alert\" class=\"close\" type=\"button\">×</button>\n" +
                    "           <strong>${message(code: 'importMapper.file_missing')}</strong>\n" +
                    "           </div>";
                $('#messageContent').empty().append(warning);
                return false
            } else {
                if ("newDesign" === designId || "newPeriod" === designId && newDesign) {
                    newDesignModalIM();
                    return false
                } else {
                    hideSettingsAndHandleSteps();
                }
            }
        } else {
            if ("newDesign" === designId || "newPeriod" === designId && newDesign) {
                newDesignModalIM();
                return false
            } else {
                hideSettingsAndHandleSteps();
            }
        }
    });

    function hideSettingsAndHandleSteps() {
        $('#moreSettings').hide();
        var	steps = $(".step");
        var isFilteringManual = "manual" === $("#filters").val();
        var isCombiningManual = $("#combine").is(':checked');

        if (isFilteringManual||isCombiningManual) {
            $(steps[1]).addClass('current');
            $(steps[0]).removeClass('current').addClass('completed');
        } else {
            $(steps[1]).addClass('current');
            $(steps[2]).addClass('current');
            $(steps[0]).removeClass('current').addClass('completed');
        }
    }

    function importFileWithNewDesign(designInfo) {
        var newDesignDiv = designInfo;
        var name = $(newDesignDiv).find('#newDesignName');
        var ribaStage = $(newDesignDiv).find('#newDesignRibaStage');

        if ($(name).length) {
            if ($(name).val() && (!$(ribaStage).length||($(ribaStage).length && $(ribaStage).val()))) {
                $('#addIndicatorsDesign').modal('hide');
                hideSettingsAndHandleSteps();
                $('#theForm').trigger('submit');
            } else {
                if (!$(name).val()) {
                    $(name).addClass('redBorder');
                }
                if (!$(ribaStage).val()) {
                    $(ribaStage).addClass('redBorder');
                }
            }
        }

    }

    function getChildren() {
        $('#designId').children().remove();
        var id = $('[name=entityId]').val();

        if (id) {
            $.ajax({
                async: false, type: 'POST',
                data: 'entityId=' + id + '&childEntityId=${childEntityId}',
                url: '/app/sec/importMapper/designsForEntity',
                success: function (data, textStatus) {
                    if (data.output) {
                        $('#designId').append(data.output);
                        var newDesignName = $('#designId option:first-child').text();
                        $('#newDesignHidden').val(newDesignName);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {

                }
            });
        }
    }

    function getIndicators() {
        $('#indicatorId').children().remove();
        var id = $('[name=entityId]').val();

        if (id) {
            $.ajax({
                async: false, type: 'POST',
                data: 'entityId=' + id + '&indicatorId=${indicatorId}',
                url: '/app/sec/importMapper/indicatorsForEntity',
                success: function (data, textStatus) {

                    if (data.output) {
                        $('#indicatorId').append(data.output);
                        $('#save').removeAttr('disabled');
                    } else {
                        $('#indicatorId').append('<option value=\"\"><g:message code="importMapper.index.no_indicators_found" /></option>');
                        $('#save').attr('disabled', 'disabled');
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {

                }
            });
        }
    }

    function clearMessages() {
        $('.alert').hide();
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

    function getFilters() {
        $('#filters').children().remove();
        var indicatorId = $('[name=indicatorId]').val();

        if (indicatorId) {
            $.ajax({
                async: false, type: 'POST',
                data: 'indicatorId=' + indicatorId,
                url: '/app/sec/importMapper/presetFiltersByIndicator',
                success: function (data, textStatus) {
                    if (data.output) {
                        $('#filters').append(data.output);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {

                }
            });
        }
    }

    $(document).ready(function () {
        if ($('#indicatorId').val() && $('#entityId').val()) {
            $('#save').removeAttr('disabled');
        }

        $('#indicatorId', '#entityId').on('change', function () {
            if ($('#indicatorId').val() && $('#entityId').val()) {
                $('#save').removeAttr('disabled');
            } else {
                $('#save').attr('disabled', 'disabled');
            }
        });

        var helpPopSettings = {
            placement: 'top',
            template: '<div class="popover popover-fade" style="display: block; max-width: 150px;"><div class="arrow"></div><div class="popover-content"></div></div>'
        };
        $(".importMapperHelpPopover[rel=popover]").popover(helpPopSettings)
    });

    $(".disableFastAndSilent").on('change', function () {disableFastAndSilent()});

    function disableFastAndSilent(){

        var isChecked = $("#combine").prop('checked')  || $("#incrementalImport").prop('checked')  || $("#temporaryCalculation").prop('checked');

        var fastSilentCheckbox =  $("[name=fastAndSilent]");

        if(isChecked){
            fastSilentCheckbox.attr('disabled', 'disabled');
            fastSilentCheckbox.attr('checked', false);
        } else{
            fastSilentCheckbox.removeAttr('disabled');
        }
    }

    function newDesignModalIM(){

        let indicatorId = $('#indicatorId').val();
        let entityId = $('[name=entityId]').val();
        addNewDesignModalIM(entityId, 'theForm', null, indicatorId, true);
    }

    $(".unitSystemCheckbox").on('click', function() {
        if (!$(this).is(":checked")) {
            $(".unitSystemCheckbox").prop("checked", false);
        } else {
            $(".unitSystemCheckbox").prop("checked", false);
            $(this).prop("checked", true);
        }
    });

</script>
</body>
</html>



