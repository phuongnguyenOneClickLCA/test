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
<g:set var="indicatorService" bean="indicatorService"/>
<script type="text/javascript">
    if (!!window.performance && window.performance.navigation.type === 2) {
        // This is to detect user pressing back button on browser and reloading && clearing session properly with reload
        window.stop();
        window.location.reload();
    }
</script>
<g:form action="createDatasets" useToken="true" name="theFormSettings" method="post" novalidate="novalidate">
    <div class="container">
        <g:if test="${outdatedPluginError}">${outdatedPluginError}</g:if>
        <div class="screenheader">
            <h4><opt:link controller="main" removeEntityId="true"><g:message code="main"/></opt:link>
                <sec:ifLoggedIn>
                    <g:if test="${entity}">
                        > <opt:link controller="entity" action="show" id="${entity?.id}">
                        <g:abbr value="${entity?.name}" maxLength="20"/>
                    </opt:link>
                        <g:if test="${childEntity}">
                            > <span id="childEntityName">${childEntity?.operatingPeriodAndName}</span>
                        </g:if>
                    </g:if>
                </sec:ifLoggedIn>
                > <g:message code="import_data"/> <br/></h4>
            <g:if test="${showSteps}">
                <opt:breadcrumbs current="${currentStep}"/>
            </g:if>
            <div class="alert alert-info">
                <i class="fas fa-info pull-left" style="font-size: large; margin-right: 8px;"></i>
                <strong>${message(code: "importMapper.import_setting_rowCounts", args: [rowCounts])}</strong>
            </div>

            <div class="btn-group pull-right hide-on-print" id="actions">
                <g:link action="cancel" class="btn" onclick="return modalConfirm(this);"
                        data-questionstr="${message(code: 'importMapper.index.cancel')}"
                        data-truestr="${message(code: 'yes')}" data-falsestr="${message(code: 'no')}"
                        data-titlestr="Cancel">
                    <g:message code="cancel"/>
                </g:link>
                <g:if test="${importFileExists}">
                    <g:link controller="importMapper" action="downloadExcelFile" class="btn btn-primary"
                            elementId="alreadyInSessionExcel">
                        <g:message code="results.expand.download_excel"/>
                    </g:link>
                    <a class="btn btn-primary" href="javascript:" onclick="sendToAnotherUserSwal()">
                        <g:message code="sendMeData.heading"/>
                    </a>
                </g:if>

                <a href="javascript:" style="font-weight: normal;" class="btn btn-primary" id="resolverOverwriteButton" onclick="submitSettings()">
                    <g:message code="continue"/>
                </a>
            </div>

            <h1>${message(code: 'breadcrumbs.settings')}</h1>
        </div>
    </div>

    <div class="container section">
        <div class="">
            <div class="loading-spinner span8" style="display:none; margin: 0 auto; width: 100%;" id="loadingSpinner">
                <div class="image">
                    <svg class="loadingSVG" version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg"  x="0px" y="0px"
                         width="270px" height="270px" viewBox="0 0 32 32" style="enable-background:new 0 0 32 32;" xml:space="preserve">
                        <g>
                            <path class="arrowSpinningSvg mm-o-syncing-queue" d="M24.5,10.4L22.2,11l5,5L29,9.2l-2.4,0.7C24.4,6.1,20.4,3.8,16,3.8c-5.4,0-10.1,3.4-11.7,8.6
        c-0.2,0.5,0.1,1.1,0.7,1.3c0.1,0,0.2,0,0.3,0c0.4,0,0.8-0.3,1-0.7c1.3-4.3,5.2-7.2,9.7-7.2C19.5,5.8,22.7,7.5,24.5,10.4z"/>
                        </g>
                    </svg>
                    <p class="working"><g:message code="loading.working"/>.</p>
            </div>

        </div>
            <div id="importPageContent">
                    <div class="container section">
                        <div class="sectionbody importmapper">
                            <g:if test="${importMapperFound && indicatorsFound}">
                                <input type="hidden" name="newDesign" id="newDesignHidden" value=""/>

                                <div id="left" style="width: 100%;">
                                    <div><g:message code="importMapper.import_setting_help"/></div>

                                    <div class="container-fluid center-block borderbox"
                                         style="padding-top:10px; ${bimModelChecker ? 'max-width:850px;' : ''}">
                                        <div>
                                            <div>
                                                <div class="control-group" style="float: left; margin-left: 15px;">
                                                    <label><strong><g:message code="importMapper.index.where_to_import"/></strong></label>
                                                    <select style="width: 175px;" name="entityId" id="entityId"
                                                            class="autocompletebox" ${noLicense ? 'disabled=\"disabled\"' : ''}
                                                            ${isRseeImport ? 'disabled=\"true\"' : ''}>
                                                        <g:each in="${entities}" var="entity1">
                                                            <option value="${entity1.id.toString()}"
                                                                ${entityId && entityId.toString().equals(entity1.id.toString()) || userDefaults?.get("entityId")?.equals(entity1.id.toString()) ? ' selected=\"selected\"' : ''}>${entity1.name}</option>
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
                                                        <strong>
                                                            <g:message code="importMapper.index.choose_design"/>
                                                        </strong>
                                                    </g:else>
                                                </label>
                                                <select style="width: 175px;" name="childEntityId"
                                                        id="designId" ${noLicense ? 'disabled=\"disabled\"' : ''}
                                                        ${isRseeImport ? 'disabled=\"true\"' : ''}
                                                        onchange="getIndicators(); updateImportMethod(this)"></select>
                                            </div>

                                            <div class="control-group" style="float: left;">
                                                <label>
                                                    <strong>
                                                        <g:message code="importMapper.index.what_to_calculate"/>
                                                    </strong>
                                                </label>
                                                <g:if test="${fixedIndicator && !indicatorId}">
                                                    <input type="hidden" name="indicatorId" value="${fixedIndicator.indicatorId}"/>
                                                    ${indicatorService.getLocalizedName(fixedIndicator)}
                                                </g:if>
                                                <g:else>
                                                    <select style="width: 175px;" name="indicatorId" id="indicatorId" ${noLicense ? 'disabled=\"disabled\"' : ''}
                                                            ${isRseeImport ? 'disabled=\"true\"' : ''} onchange="getFilters();">
                                                    </select>
                                                </g:else>
                                            </div>

                                            <div class="control-group pull-left">
                                                <label>
                                                    <strong>
                                                        <g:message code="importMapper.index.filters"/>
                                                    </strong>
                                                </label>
                                                <g:if test="${presetFilters}">
                                                    <select name="filters" ${noLicense ? 'disabled=\"disabled\"' : ''} id="filters">
                                                        <g:each in="${presetFilters}" var="presetFilter">
                                                            <option value="${presetFilter.presetFilterId}">${presetFilter.localizedName}</option>
                                                        </g:each>
                                                    </select>
                                                </g:if>
                                            </div>
                                            <div class="control-group pull-left">
                                                <label>
                                                    <strong>
                                                        <g:message code="importMapper.index.incremental_import"/>
                                                        <a href="#" class="importMapperHelpPopover" rel="popover"
                                                           data-trigger="hover" data-content="${message(code:'importMapper.index.incremental_import_desc')}">
                                                            <i class="icon-question-sign"></i>
                                                        </a>
                                                    </strong>
                                                </label>
                                                <select name="incrementalImport" id="incrementalImport" ${noLicense ? "disabled=\"disabled\"" : ""}
                                                        onchange="changeUpdateQuantCheckbox(this)">
                                                    <option value=false data-attribute="replace" >${message(code:"incrementalImport.replace")}</option>
                                                    <option value=true data-attribute="merge" >${message(code:"incrementalImport.merge")}</option>
                                                    <option value=true data-attribute="mergeAndUpdate" >${message(code:"incrementalImport.mergeAndUpdate")}</option>
                                                </select>
                                            </div>
                                        </div>

                                        <div class="clearfix"></div>

                                        <div>
                                            <div class="control-group pull-left">
                                                <span>
                                                    <strong>
                                                    <input style="width: 25px; !important; height: 17px; !important; margin-left: 15px;" type="checkbox" checked="checked" name="removeEmptyData"
                                                           id="removeEmptyData" ${noLicense ? "disabled=\"disabled\"" : ""}/>
                                                    <g:message code="importMapper.index.remove_empty_data"/>
                                                    </strong>
                                                    <a href="#" class="importMapperHelpPopover" data-toggle="dropdown" rel="popover"
                                                       data-trigger="hover" data-content="${message(code:'importMapper.index.remove_empty_data_desc')}">
                                                        <i class="icon-question-sign"></i>
                                                    </a>
                                                </span>
                                            </div>

                                            <div class="control-group pull-left">
                                                <span>
                                                    <strong>
                                                        <g:if test="${imperial}">
                                                            <input style="width: 25px; !important; height: 17px; !important;" checked="checked" ${noLicense ? 'disabled=\"disabled\"' : ''}
                                                                   type="checkbox" name="convertToImperial" id="convertToImperial"/>
                                                            <g:message code="importMapper.unitSystem.toImperial"/>
                                                        </g:if>
                                                        <g:else>
                                                            <input style="width: 25px; !important; height: 17px; !important;" type="checkbox" checked="checked" name="convertToMetric"
                                                                ${noLicense ? 'disabled=\"disabled\"' : ''} id="convertToMetric"/>
                                                            <g:message code="importMapper.unitSystem.toMetric"/>
                                                        </g:else>
                                                    </strong>
                                                    <a href="#" class="importMapperHelpPopover" data-toggle="dropdown" rel="popover"
                                                             data-trigger="hover" data-content="${message(code: 'importMapper.unitSystem.info')}">
                                                        <i class="icon-question-sign"></i>
                                                    </a>
                                                </span>
                                            </div>
                                        </div>

                                    </div>

                                    <div class="clearfix"></div>
                                </div>
                            </g:if>
                        </div>

                    </div>
                    <div class="modal hide modalDesignScope" id="modifyModalDiv"></div>
            </div>

        </div>
    </div>
</g:form>

<script type="text/javascript">
    $(function () {
        $.widget("ui.combobox", {
            _create: function () {
                var required = this.element.attr("required");

                if (required) {
                    this.wrapper = $("<div>")
                        .insertBefore(this.element)
                        .attr('class', 'input-append redBorder')
                } else {
                    this.wrapper = $("<div>")
                        .insertBefore(this.element)
                        .attr('class', 'input-append');
                }
                this._createAutocomplete();
                if (!${isRseeImport}) {
                    this._createShowAllButton();
                }
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

                if (${isRseeImport}) {
                    this.input.attr("disabled", "true");
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
                var matcher = new RegExp($.ui.autocomplete.escapeRegex(request.term), "i")
                var src = this.element.children("option").map(function () {
                    var text = $(this).text();
                    if (this.value && (!request.term || matcher.test(text)))
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

        $("#theFormSettings").on("submit", function () {
            $('select').removeAttr('disabled');
            $('input').removeAttr('disabled');
            var itemChecked = $(".checkBoxContainer").find("input:checked")
            itemChecked = Array.from(itemChecked)
            var setToManual = []
            itemChecked.forEach(function (item) {
                setToManual.push($(item).val())
            })

            if($("input[name='stepsSetting']").length === 0) {
                //16294 should not be duplicated in case submit was prevented.
                $("<input />").attr("type", "hidden").attr("name", "stepsSetting").attr("value", setToManual).appendTo(this);
            }

            var notSubmit = checkModalRequiredValue()

            if (notSubmit) {
                //e.preventDefault()
                return false
            } else {
                $("#loadingSpinner").show();
                $("#importPageContent").hide();
                disableLink($("#alreadyInSessionExcel"));
                $("#modifyModalDiv").modal("hide");
                return true;
            }
        })
        $(".autocompletebox").combobox();
    });

    function newDesignModalIM() {
        let indicatorId = $('#indicatorId').val();
        let entityId = $('[name=entityId]').val();
        addNewDesignModalIM(entityId, 'theFormSettings', null, indicatorId, true);
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
                        getIndicators();
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
        var childId = $('[name=childEntityId]').val();

        if (id) {
            $.ajax({
                async: false, type: 'POST',
                data: 'entityId=' + id + '&indicatorId=${indicatorId}' + '&childEntityId=' + childId,
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

    function sendToAnotherUserSwal() {
        Swal.fire({
            html: "<div style=\"float:left;\"><h1>${message(code: 'sendMeData.heading')?.replaceAll("\"", "'")}</h1><p style=\"text-align: left;\">${message(code: 'sendMeData.info1')?.replaceAll("\"", "'")}</p><br />" +
                "<ul><li class=\"importMapperNoLicenseList\">${message(code: 'sendMeData.bullet1')?.replaceAll("\"", "'")}</li><li class=\"importMapperNoLicenseList\">${message(code: 'sendMeData.bullet2')?.replaceAll("\"", "'")}</li>" +
                "<li class=\"importMapperNoLicenseList\">${message(code: 'sendMeData.bullet3')?.replaceAll("\"", "'")}</li></ul><br />" +
                "<div id='sendToAnotherUserInput' style='margin-top: 5px;'><p style=\"text-align: left;\">${message(code: 'sendMeData.info2')?.replaceAll("\"", "'")}</p>" +
                "<table style=\"width:100%; margin-top: 20px;\"><tr><td style=\"width: 150px;\"><label for=\"anotherUserEmail\"><strong>${message(code: 'sendMeData.emailHeading')?.replaceAll("\"", "'")}</strong></label></td>" +
                "<td><input id='anotherUserEmail' style='width: 85%;' type='text' /></td></tr>" +
                "<tr><td style=\"width: 150px;\"><label for=\"anotherUserNote\"><strong>${message(code: 'sendMeData.noteHeading')?.replaceAll("\"", "'")}</strong></label></td>" +
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
                }).then(function () {
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

    function submitSettings() {
        if(!checkLicense()) {
            return false
        }
        var designId = $('#designId').val();
        if ("newDesign" === designId || "newPeriod" === designId) {
            newDesignModalIM();
            return false
        } else {
            var noOverwrite = $('#incrementalImport').children("option:selected").attr("data-attribute");
            if (noOverwrite === "replace") {
                var indicatorId = $('#indicatorId').val();
                var hasData = false;
                var entityName = "";
                $.ajax({
                    async: false, type: 'POST',
                    data: 'designId=' + designId + '&indicatorId=' + indicatorId,
                    url: '/app/sec/util/checkHasData',
                    success: function (data, textStatus) {
                        hasData = data.hasData;
                        entityName = data.entityName;
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {

                    }
                });

                if (hasData) {
                    var localizedSwalText = "${message(code: "importMapper.data_overwrite.info", args: ["design_name"])}"
                    localizedSwalText = localizedSwalText.replace(/design_name/, entityName);

                    Swal.fire({
                        title: "${message(code: "importMapper.data_overwrite")}",
                        text: localizedSwalText,
                        icon: "warning",
                        confirmButtonText: "${message(code: "overwrite")}",
                        cancelButtonText: "${message(code: "cancel")}",
                        confirmButtonColor: "#bd362f",
                        showCancelButton: true,
                        reverseButtons: true,
                        allowOutsideClick: true
                    }).then(result => {
                        if (result.value) {
                            clearMessages();
                            $("#resolverOverwriteButton").css({'pointer-events': 'none', 'opacity': '0.5'});
                            $("#theFormSettings").trigger('submit');
                        }
                    });
                } else {
                    clearMessages();
                    $("#resolverOverwriteButton").css({'pointer-events': 'none', 'opacity': '0.5'});
                    $("#theFormSettings").trigger('submit');
                }
            } else {
                clearMessages();
                $("#resolverOverwriteButton").css({'pointer-events': 'none', 'opacity': '0.5'});
                $("#theFormSettings").trigger('submit');
            }
        }
    }

    //upload file is allowed but users with no license can only send data to other expert users
    function checkLicense() {
        var noLicense = ${noLicense}
        if (noLicense) {
            $("#entityId").prop("disabled", true);
            setTimeout(function () {
                noLicenseSwal();
            }, 250);
            return false;
        }
        return true;
    }

    function changeUpdateQuantCheckbox(element) {
        var updateQuantCheckbox = $("#stepId9")
        if ($(element).children("option:selected").attr("data-attribute") == "mergeAndUpdate") {
            $(updateQuantCheckbox).attr("checked",true)
        } else {
            $(updateQuantCheckbox).attr("checked",false)
        }
    }

    function updateImportMethod(element) {
        var newChildEntityVal = ["newDesign", "newPeriod"]
        var selectedChildVal = $(element).children("option:selected").val()
        if (newChildEntityVal.contains(selectedChildVal)) {
            $("#incrementalImport").val(false).change().attr("disabled","disabled")
        } else {
            $("#incrementalImport").removeAttr("disabled")
        }
    }

</script>
</body>
</html>



