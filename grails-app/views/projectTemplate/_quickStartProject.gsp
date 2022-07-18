<%@ page import="grails.converters.JSON; com.bionova.optimi.core.Constants" %>
<head>
    <asset:javascript src="jquery.autocomplete.js"/>
</head>
<div  class="modal-dialog">
    <div class="modal-content">
        %{-- HEADER --}%
        <div class="modal-header" style="margin-top: 20px">
            <button style="margin-right: 15px" type="button" class="close" data-dismiss="modal">&times;</button>

            <h2 style="display: inline; margin-left: 15px">
                <asset:image src="resultreport.png" class="queryIcon"/>
                <g:message code="entity.new_entity_title"/>
            </h2>
            <div id="querySectionStepper">
                <div class="demo-section k-content">
                    <nav id="stepperSection"></nav>
                </div>
            </div>
        </div>

        <g:uploadForm controller="projectTemplate" action="startNewProject" id="quickStartProjectForm">
            %{-- BODY --}%
            <div id="projectModalBody" class="modal-body project-Modal">
                <g:hiddenField id="quickStart-selectedTemplateId" name="selectedTemplateId" value=""/>
                <g:hiddenField name="queryId" value="${basicQuery?.queryId}"/>
                <g:hiddenField name="entityClass" value="${entityClassResource?.resourceId}"/>
                <g:hiddenField name="checkMandatory" value="true"/>
                <div id="quickStartContent" class="tab-content">
                    <g:each in="${compatibleVirSections}" var="section" status="index">
                        %{-- FIRST TAB--}%
                        <g:if test="${section.virtualSectionId == Constants.BasicQueryVirtualSectionIds.MIN_INFO.toString()}">
                            <div id="${Constants.BasicQueryVirtualSectionIds.MIN_INFO.toString()}" class="tab-pane ${index == 0 ? 'show active' : ''}">
                                <div class="flex">
                                    %{-- LICENSE --}%
                                    <div id="quickStartProjectModal-availableLicenses">
                                        <label id="quickStart-licenseLabel" for="licenseId">
                                            <strong><g:message code="entity.licenses"/></strong>
                                            <g:if test="${selectMultiLicenses}">
                                                <i class="icon-question-sign licenseHelp"></i>
                                            </g:if>
                                            <a id="quickstart-enterLicenseKeyLabelLink" href="#" class="fiveMarginVertical" onclick="handleEnterLicenseKeyClick(this)">
                                                ${message(code:'enter.license.key')}
                                            </a>
                                        </label>
                                        <div class="input-append">
                                            <span class="freeLicenseHelp"></span>
                                            <select name="licenseId"
                                                    id="quickStartProjectModal-licenseId"
                                                ${selectMultiLicenses ? 'multiple' : ''}>
                                                <g:if test="${!selectMultiLicenses}">
                                                    <option></option>
                                                </g:if>
                                                <g:each in="${licenses}" var="license">
                                                    <g:if test="${license}">
                                                        <option data-freeForAllEntities="${license?.freeForAllEntities}"
                                                                data-planetary="${license?.planetary}"
                                                                data-condiditionalCountries="${license?.conditionalCountries}"
                                                                data-conditionalBuildingTypes="${license?.conditionalBuildingTypes}"
                                                                value="${license?.id?.toString()}">${license?.name}</option>
                                                    </g:if>
                                                </g:each>
                                            </select>
                                        </div>
                                    </div>
                                    %{-- ENTER LICENSE KEY --}%
                                    <div id="quickStart-enterLicenseKey" class="hide enterLicenseKey">
                                        <label><b>${message(code:'enter.license.key')}</b></label>
                                        <div>
                                            <g:textField id="quickStart-licenseKey" name="licenseKey"
                                                         class="inline-block" oninput="validateLicenseKey(this)"/>
                                            <a id="quickStart-licenseKey-submit" href="javascript:"
                                               class="btn btn-primary inline-block fiveMarginLeft removeClicks"
                                               disabled onclick="submitLicenseKey(this)">
                                                ${message(code: 'license.activate_license')}
                                            </a>
                                        </div>
                                    </div>
                                    %{-- SELECT PROJECT TEMPLATE --}%
                                    <div id="quickStartProjectModal-availableTemplates" style="display: none; margin-left: 20px">
                                        <div>
                                            <label for="templateId">
                                                <strong><g:message code="template"/></strong>
                                            </label>

                                            <div>
                                                <div class="input-append">
                                                    <select name="templateId" id="${Constants.TEMPLATE_SELECT_QUICKSTART}">
                                                        <option></option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div id="quickStart-templateDetailsList" class="templateDetailsContainer">
                                            <g:if test="${licenseToTemplateDetailsMap}">
                                                <g:render template="/projectTemplate/detailsList" model="[licenseToTemplateDetailsMap: licenseToTemplateDetailsMap]" />
                                            </g:if>
                                        </div>
                                    </div>
                                </div>
                                %{-- FLOATING LICENSE WARNING --}%
                                <g:if test="${floatingLicenseIdsNeedCheckIn}">
                                    <div id="quickStart-floatingLicenseWarningContainer">
                                        <g:each in="${floatingLicenseIdsNeedCheckIn}" var="floatingLicenseId">
                                            <div id="quickStart-floatingLicenses-${floatingLicenseId}" data-licenseId="${floatingLicenseId}" class="redScheme hide" style="margin-top: 10px">
                                                <span>${message(code: "license.floating.checkIn.required")}</span>
                                                <a href="javascript:" onclick="handleCheckInFloatingLicenseClick('${floatingLicenseId}')">${message(code: "license.floating.error_info")}.</a>
                                            </div>
                                        </g:each>
                                    </div>
                                </g:if>
                                %{-- NAME --}%
                                <div class="querysection" style="border: 0 !important; margin-top: 10px">
                                    <div class="control-group">
                                        <label>
                                            <g:message code="entity.name"/>
                                            (<g:message code="mandatory"/>)
                                        </label>

                                        <div>
                                            <div class="input-append">
                                                <opt:textField modifiable="${modifiable}" entity="${entity}" name="name" id="${Constants.PROJECT_NAME_QUICKSTART}"
                                                               class="input-xlarge nameInput mandatory redBorder"
                                                               onkeyup="validateName(this, '${message(code: "invalid_character")}')"/>
                                                <div class="hidden" data-content="${message(code: 'invalid_character')}">
                                                    <p class="warningRed"></p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div id="quickstartProjectModal-buildingTypeAndCountryContainer">
                                    <g:render template="/query/section"
                                              model="[entity: entity, query: basicQuery, section: section, hideHeadText: true]"/>
                                </div>
                            </div>
                        </g:if>
                        %{-- FIRST DESIGN TAB --}%
                        <g:elseif test="${section.virtualSectionId == Constants.BasicQueryVirtualSectionIds.FIRST_DESIGN.toString()}">
                            <div id="${Constants.BasicQueryVirtualSectionIds.FIRST_DESIGN.toString()}" class="tab-pane ${index == 0 ? 'show active' : ''}">
                                <div id="${Constants.QSPM_DESIGN_TAB_ID}"></div>
                            </div>
                        </g:elseif>
                        %{-- QUICK OPTIONS TAB --}%
                        <g:elseif test="${section.virtualSectionId == Constants.BasicQueryVirtualSectionIds.OPTIONS.toString()}">
                            <div id="${Constants.BasicQueryVirtualSectionIds.OPTIONS.toString()}" class="tab-pane ${index == 0 ? 'show active' : ''}">
                                <div id="${Constants.QSPM_OPTIONS_TAB_ID}" class="tenPadding"></div>
                            </div>
                        </g:elseif>
                        <g:else>
                            <div id="${section.virtualSectionId}" class="tab-pane ${index == 0 ? 'show active' : ''}">
                                <g:render template="/query/section"
                                          model="[entity: entity, query: basicQuery, section: section, hideHeadText: true]"/>
                            </div>
                        </g:else>
                    </g:each>
                </div>
            </div>
            %{-- FOOTER --}%
            <div style="padding: 0px 30px">
                <a id="cancelButtonQuickStart" class="btn btn-default" href="javascript:" data-dismiss="modal"><g:message code="cancel"/></a>
                <a id="prevButtonQuickStart" href="javascript:" class="btn btn-secondary" disabled
                   onclick="previousStepKendo()">
                    <g:message code="back"/>
                </a>
                <a id="nextButtonQuickStart" href="javascript:" class="btn btn-primary" onclick="nextStepKendo()">
                    <g:message code="next"/>
                </a>
                <a id="saveButtonQuickStart" href="javascript:" class="hide">
                    <opt:submit name="save" value="${message(code: 'next')}" class="btn btn-primary quickStartSave"
                                onclick="handleSubmit(this)"/>
                </a>
                <p id="warningMandatoryInputQuickStartModal" class="hide help-block" style="display: inline-block">${message(code: 'help.newProject')} </p>
            </div>
        </g:uploadForm>
    </div>
</div>

<script>
    /*
        stepper is initialized on frontend
        selectedStepIndex and sections are to change the content on form
     */
    var selectedStepIndex = 0
    var sections = ["${compatibleVirSections?.collect({it.virtualSectionId})?.join('","')}"]
    var templateAvailable = false
    var selectedTemplateId = null
    var selectedTemplateObject = null
    var displayedInfoIconId = null
    var templateFetched = false
    var needFetch = false
    var selectedLicenses = []
    var selectedFloatingLicenseNeedCheckIn = []
    var floatingLicenseIdsNeedCheckIn = ${floatingLicenseIdsNeedCheckIn as JSON}
    var nameOk = false
    var bldTypeOk = false
    var countryOk = false
    var productTypeOk = false
    var licenseToTemplate = ${licenseToTemplates as JSON}

    $(document).ready(function () {
        stepper = $("#stepperSection").kendoStepper({
            indicator: true,
            steps: [
                <g:each in="${compatibleVirSections}" var="section" status="index">
                {
                    label: "${section.localizedVirtualName}",
                    <g:if test="${section.virtualSectionId == Constants.BasicQueryVirtualSectionIds.FIRST_DESIGN.toString() || section.virtualSectionId == Constants.BasicQueryVirtualSectionIds.OPTIONS.toString() || (entityClass == Constants.EntityClass.BUILDING.type && section.virtualSectionId == Constants.BasicQueryVirtualSectionIds.PROJECT_DATA.toString())}">
                    enabled: false
                    </g:if>
                },
                </g:each>
            ],
            select: onSelect
        }).getKendoStepper();

        // when click on the nav bar, this method is called
        function onSelect(e) {
            selectedStepIndex = e.step.options.index;
            // fetch design modal from template on click next at step 0 (if conditions fit)
            if (selectedStepIndex === 1 && needFetch && !templateFetched) {
                fetchDesignModalAndOptions()
            }
            showHideChildDiv('quickStartContent', null, sections[selectedStepIndex])
            resolveBtnDisplay()
            checkThirdStep()
        }

        initQueryAutocompletes();
        select2theSingleSelects('${message(code: 'query.choose_resource')}');
        let country = $('#${Constants.COUNTRY_SELECT}');
        if (country.length > 0 && country.val() !== "") {
            country.trigger("change")
        }
    });

    $(function () {
        triggerPopoverForTemplateDetails()
        setLicenseAndTemplateSelectWidth()
        maskAllSelectInDivWithSelect2('${Constants.BasicQueryVirtualSectionIds.PROJECT_DATA.toString()}', false, '${message(code:'select')}')
        maskAllSelectInDivWithSelect2('quickstartProjectModal-buildingTypeAndCountryContainer', false, '${message(code:'select')}')
        maskTemplateSelectWithSelect2()
        // add once a red border to building type and productType on render
        addBorderToSelect2('${Constants.BLD_TYPE_SELECT}')
        addBorderToSelect2('${Constants.PRODUCT_TYPE_SELECT}')

        const $licenseIdSelect = $('#quickStartProjectModal-licenseId');
        <g:if test="${selectMultiLicenses}">
            // if user has licenses, can select multiple licenses
            $licenseIdSelect.select2({
                templateResult : resultState
            }).on('select2:select', function (e) {
                // when a license is selected
                $($licenseIdSelect).select2("close");
                const selected = $licenseIdSelect.find(':selected');

                if ($(selected).length) {
                    $.each(selected, function (index, elem) {
                        // set the project template when a license is selected
                        let licenseId = $(elem).val()
                        if (!selectedLicenses.includes(licenseId)) {
                            setAvailableTemplate(licenseId)
                            selectedLicenses.push(licenseId)
                        }
                    });
                    checkFloatingLicenses()
                }
            }).on('select2:unselect', function (e) {
                // when a license is unselected
                const selected = $licenseIdSelect.find(':selected');
                const $templateSelect = $('#${Constants.TEMPLATE_SELECT_QUICKSTART}')

                // clear the template select and add the selected again.
                if ($(selected).length) {
                    // extract the selected template Id to keep it selected afterwards
                    const selectedTemplateId = $('#${Constants.TEMPLATE_SELECT_QUICKSTART} option:selected').attr('id')

                    const checklist = []
                    $templateSelect.empty().append('<option></option>')

                    $.each(selected, function (index, elem) {
                        // set the project template when a license is removed
                        let licenseId = $(elem).val()
                        if (selectedLicenses && selectedLicenses.includes(licenseId)) {
                            setAvailableTemplate(licenseId)
                            checklist.push(licenseId)
                        }
                    });
                    $('#' + selectedTemplateId).prop("selected", "selected");
                    // trigger change on template id to review whether to show the tabs and enable inputs, in case the license with template have been removed
                    $templateSelect.trigger('change')
                    selectedLicenses = checklist
                } else {
                    $templateSelect.empty().append('<option></option>')
                    clearSelectedLicenses()
                    // trigger change on template id to review whether to show the tabs and enable inputs, in case the license with template have been removed
                    $templateSelect.trigger('change')
                }
                checkFloatingLicenses()

                // hide the template selection if no templates available (with only 1 empty choice)
                if ($templateSelect.children().length === 1) {
                    templateAvailable = false
                    hideTemplateSelect()
                }
            });
        </g:if>
        <g:else>
            // when user don't have licenses, only single license (planetary, trials) can be selected.
            $licenseIdSelect.select2({
                templateResult : resultState,
                allowClear: true,
                placeholder: '${message(code:'select')}'
            }).select2('open').on('select2:select', function (e) {
                // when a license is selected
                const selected = $licenseIdSelect.find(':selected');
                const $templateSelect = $('#${Constants.TEMPLATE_SELECT_QUICKSTART}')
                $templateSelect.empty().append('<option></option>')
                clearSelectedLicenses()
                if ($(selected).length) {
                    $.each(selected, function (index, elem) {
                        // set the project template when a license is selected
                        let licenseId = $(elem).val()
                        if (!selectedLicenses.includes(licenseId)) {
                            setAvailableTemplate(licenseId)
                            selectedLicenses.push(licenseId)
                        }
                    });
                }
                // hide the template selection if no templates available (with only 1 empty choice)
                if ($templateSelect.children().length === 1) {
                    templateAvailable = false
                    // trigger change on template select to review whether to show the tabs and enable inputs, in case the license with template have been removed
                    $templateSelect.trigger('change')
                    hideTemplateSelect()
                }
                checkFloatingLicenses()
            }).on('select2:unselect', function (e) {
                // when a license is unselected. Trigger change on template select to review whether to show the tabs and enable inputs, in case the license with template have been removed
                $('#${Constants.TEMPLATE_SELECT_QUICKSTART}').empty().append('<option></option>').trigger('change')
                clearSelectedLicenses()
                checkFloatingLicenses()
                templateAvailable = false
                hideTemplateSelect()
            })
        </g:else>

        resolveLicenseSelectDisplay($($licenseIdSelect));

        $('.numeric').on('input propertychange', function () {
            const start = this.selectionStart;
            end = this.selectionEnd;
            let val = $(this).val();
            if (val) {
                $(this).popover('hide');
            }
            if (isNaN(val)) {
                val = val.replace(/[^0-9\.\,\-]/g, '');

                if (val.split('.').length > 2) {
                    val = val.replace(/\.+$/, '');
                }

                if (val.split(',').length > 2) {
                    val = val.replace(/\,+$/, '');
                }
                const dotsAndCommas = ((val.match(/\./g) || []).length + (val.match(/,/g) || []).length);

                if (dotsAndCommas > 1) {
                    if (val.indexOf('.') < val.indexOf(',')) {
                        val = val.replace('.', '');
                    } else {
                        val = val.replace(',', '');
                    }
                }
            }
            $(this).val(val);
            this.setSelectionRange(start, end);
        });
        <g:if test="${selectMultiLicenses}">
            // popover for select multi licenses
            $('.licenseHelp').popover({
                content: "${message(code:'entity.form.indicator_help')}",
                    placement: 'right',
                    template: '<div class="popover"><div class="arrow"></div><div class="popover-content indicatorHelp"></div></div>',
                    trigger: 'hover',
                    html: true
                });
        </g:if>
        // popover for free license
        $('.freeLicenseHelp').popover({
            container: 'body',
            content: "${message(code:'freeLicense.available')}",
            placement: 'left',
            template: '<div class="popover" id="freelicense-popup" style="z-index: 10000"><div class="arrow"></div><div class="popover-content"></div></div>',
            trigger: 'manual',
            html: true
        });

        // run check to disable Next btn if mandatory values are not filled
        checkMandatoryInputCondition()
    });

    $('#cancelButtonQuickStart').on('click', function () {
        $('#freelicense-popup').remove();
    });
    // when select a project template
    $('#${Constants.TEMPLATE_SELECT_QUICKSTART}')
        .on('change', function () {
            const $selectedTemplate = $('#${Constants.TEMPLATE_SELECT_QUICKSTART}').find(':selected')
            selectedTemplateId = $selectedTemplate.val()
            const licenseId = $selectedTemplate.attr('data-licenseId')
            const templates = licenseToTemplate[licenseId]
            selectedTemplateObject = selectedTemplateId && templates ? templates.find(template => template.id === selectedTemplateId) : null
            // enable the building type, country, and the template select itself in case they were locked by a template
            enableSelect(null, '${Constants.BLD_TYPE_SELECT}', false)
            enableSelect(null, '${Constants.COUNTRY_SELECT}', false)
            enableSelect(null, '${Constants.PRODUCT_TYPE_SELECT}', false)
            enableSelect(null, '${Constants.TEMPLATE_SELECT_QUICKSTART}', false)

            if (selectedTemplateId && selectedTemplateObject) {
                setProjectDetailsFromProjectTemplate()
                displayTemplateDetailsInfoIcon(licenseId)
            } else {
                hideInfoIcon('clear')
            }
            // empty the fetched design modal from template and the options tab.
            clearFirstDesignTabAndOptionsTab()
            // this check also decides whether to enable the next tabs
            checkMandatoryInputCondition()
            // set templateFetched & optionsFetched to false to trigger a fetch
            templateFetched = false
            // only fetch design modal if template is set with some tools, otherwise it only has details for project
            needFetch = selectedTemplateObject && selectedTemplateObject.useIndicatorIds && selectedTemplateObject.useIndicatorIds.length > 0
        })
        .on('select2:closing', function() {
            // display info icon again when template dropdown closes (in case it was hidden when template options are hovered)
            if (displayedInfoIconId) {
                showInfoIcon()
                // in some case popover is stuck after select
                $(displayedInfoIconId).trigger('mouseleave')
            }
        })

    // When a template option (select2) is hovered => display the template info
    $('body')
        .on('mouseenter', '.select2-results__option', function () {
            const $this = $(this)
            if ($this.attr('id') && $this.attr('id').includes('${Constants.TEMPLATE_SELECT_QUICKSTART}')) {
                // get the combo Id to trigger popover the correct template details (data-comboId set in templateOptionFormat on init
                const comboId = $this.find('p').attr('data-comboId')
                $('#templateDetails-' + comboId).show().trigger('mouseenter')
                if (displayedInfoIconId) {
                    hideInfoIcon()
                }
            }
        })
        .on('mouseleave', '.select2-results__option', function () {
            const $this = $(this)
            if ($this.attr('id') && $this.attr('id').includes('${Constants.TEMPLATE_SELECT_QUICKSTART}')) {
                // remove the template details popover when user move mouse away
                const comboId = $this.find('p').attr('data-comboId')
                $('#templateDetails-' + comboId).hide().trigger('mouseleave')
            }
        })

    // When select country or building type or product type
    $('#${Constants.COUNTRY_SELECT}, #${Constants.BLD_TYPE_SELECT}, #${Constants.PRODUCT_TYPE_SELECT}').on('change', function () {
        checkMandatoryInputCondition()
        if (!selectedLicenses.length && !selectedTemplateObject) {
            openLicenseDropdownIfFreeLicenseAvailable()
        }
    });

    // when project name changes
    $('#${Constants.PROJECT_NAME_QUICKSTART}').on('change', function() {
        checkMandatoryInputCondition()
    })

    // when frame type for project changes
    $('#${Constants.BasicQueryVirtualSectionIds.PROJECT_DATA.toString()} select[name="basicQuerySection.frameType"]').on('select2:select', function() {
        copyFrameTypeToDesign()
    })

    function handleEnterLicenseKeyClick() {
        if (isLicenseKeyInputHidden()) {
            if (isTemplateSelectHidden()) {
                toggleContentOfElement($('#quickstart-enterLicenseKeyLabelLink'), '${message(code:'hide')}', '${message(code:'enter.license.key')}')
                showLicenseKeyInput()
                setEnterLicenseKeyInputWidth()
            } else {
                hideTemplateSelect()
                toggleContentOfElement($('#quickstart-enterLicenseKeyLabelLink'), '${message(code:'hide')}', '${message(code:'enter.license.key')}', 500)
                setTimeout(showLicenseKeyInput, 500)
                setTimeout(setEnterLicenseKeyInputWidth, 500)
            }
        } else {
            hideLicenseKeyInput()
            if (templateAvailable) {
                toggleContentOfElement($('#quickstart-enterLicenseKeyLabelLink'), '${message(code:'enter.license.key')}', '${message(code:'hide')}', 500)
                setTimeout(showTemplateSelect, 500)
            } else {
                toggleContentOfElement($('#quickstart-enterLicenseKeyLabelLink'), '${message(code:'enter.license.key')}', '${message(code:'hide')}')
            }
        }
    }

    function isLicenseKeyInputHidden() {
        return $('#quickStart-enterLicenseKey').is(':hidden')
    }

    function showLicenseKeyInput() {
        $('#quickStart-enterLicenseKey').fadeIn()
    }

    function hideLicenseKeyInput() {
        $('#quickStart-enterLicenseKey').fadeOut()
    }

    function isTemplateSelectHidden() {
        return $('#quickStartProjectModal-availableTemplates').is(':hidden')
    }

    function showTemplateSelect(setFlex = false) {
        const $template = $('#quickStartProjectModal-availableTemplates')
        if (isLicenseKeyInputHidden()) {
            if (setFlex) {
                $template.fadeIn().css('display', 'flex')
            } else {
                $template.fadeIn()
            }
        } else {
            toggleContentOfElement($('#quickstart-enterLicenseKeyLabelLink'), '${message(code:'hide')}', '${message(code:'enter.license.key')}', 500)
            hideLicenseKeyInput()
            setTimeout(function(){
                if (setFlex) {
                    $template.fadeIn().css('display', 'flex')
                } else {
                    $template.fadeIn()
                }
            }, 500)
        }
    }

    function hideTemplateSelect() {
        $('#quickStartProjectModal-availableTemplates').fadeOut()
    }

    function setEnterLicenseKeyInputWidth() {
        const projectNameWidth = parseInt($('.nameInput').css('width')) + 10 // padding and margin
        const licenseWidth = $('#quickStartProjectModal-availableLicenses').width()
        const licKeyMarginLeft = parseInt($('.enterLicenseKey').css('margin-left'))
        const submitWidth = $('#quickStart-licenseKey-submit').outerWidth()
        const submitMarginLeft = parseInt($('.fiveMarginLeft').css('margin-left'))
        const weirdOffset = 14
        const width = projectNameWidth - licenseWidth - licKeyMarginLeft - submitWidth - submitMarginLeft - weirdOffset
        // resize license key input size to match project name input size
        $('#quickStart-licenseKey').css('width', width)
    }

    function setLicenseAndTemplateSelectWidth() {
        // resize license select to match label size
        $('#quickStartProjectModal-licenseId').css('width', $('#quickStart-licenseLabel').width())
        const projectNameWidth = parseInt($('.nameInput').css('width')) + 10 // padding and margin
        const licenseWidth = $('#quickStartProjectModal-availableLicenses').width()
        const templateSelectMarginLeft = parseInt($('#quickStartProjectModal-availableTemplates').css('margin-left'))
        // resize template select to match project name input size
        $('#${Constants.TEMPLATE_SELECT_QUICKSTART}').css('width', projectNameWidth - licenseWidth - templateSelectMarginLeft)
    }

    function maskTemplateSelectWithSelect2() {
        // mask template select and manually set height to match size with license select
        $('#${Constants.TEMPLATE_SELECT_QUICKSTART}').select2({
            templateResult: templateOptionFormat,
            allowClear: true,
            placeholder: '${message(code:'select')}'
        }).next().find('.select2-selection, .select2-selection__rendered').css('height', '32px')
    }

    // method to change content when click previous
    function previousStepKendo() {
        if (selectedStepIndex > 0) {
            stepper.previous()
            selectedStepIndex--
            showHideChildDiv('quickStartContent', null, sections[selectedStepIndex])
            resolveBtnDisplay()
        }
    }

    // method to change content when click next
    function nextStepKendo() {
        let okToGoNext = checkFirstStep()
        if (okToGoNext) {
            okToGoNext = checkThirdStep()
        }
        if (okToGoNext) {
            stepper.next()
            selectedStepIndex++
            // fetch design modal from template on click next at step 0 (if conditions fit)
            if (selectedStepIndex === 1 && needFetch && !templateFetched) {
                fetchDesignModalAndOptions()
            }
            showHideChildDiv('quickStartContent', null, sections[selectedStepIndex])
            resolveBtnDisplay()
        }
    }

    function resolveBtnDisplay() {
        const $prevBtn = $('#prevButtonQuickStart')
        const $saveBtn = $('#saveButtonQuickStart')
        const $nextBtn = $('#nextButtonQuickStart')
        let displaySaveBtn = false

        // display save if it's second step when template isn't applied, or template doesn't allow change, or template isn't set with any tools (needFetch)
        if (selectedStepIndex === 1) {
            if (selectedTemplateObject) {
                if (!selectedTemplateObject.allowChange || (!needFetch)) {
                    displaySaveBtn = true
                }
            } else {
                displaySaveBtn = true
            }
        }
        // display save on last step
        if (selectedStepIndex === 3) {
            displaySaveBtn = true
        }

        // disable previous btn on 1st step and trigger popovers for template details again
        if (selectedStepIndex === 0) {
            disableButton($prevBtn)
            // mask again to set the comboId
            maskTemplateSelectWithSelect2()
        }
        // remove save btn, add next btn
        if (!displaySaveBtn && !$saveBtn.hasClass('hide') && $nextBtn.hasClass('hide')) {
            $saveBtn.addClass('hide')
            $nextBtn.removeClass('hide')
        }
        // enable previous btn from 2nd step
        if (selectedStepIndex > 0) {
            enableButton($prevBtn)
        }
        // add save btn, remove next btn
        if (displaySaveBtn && $saveBtn.hasClass('hide') && !$nextBtn.hasClass('hide')) {
            $saveBtn.removeClass('hide')
            $nextBtn.addClass('hide')
        }

        // check whether next btn should be enabled/disabled
        resolveNextBtnDisplay()
    }

    function checkFirstStep() {
        let checkOk = true
        if (selectedStepIndex === 0){
            checkOk = checkMandatoryInputCondition()
        }
        return checkOk
    }

    function checkThirdStep() {
        let checkOk = true
        if (selectedStepIndex === 2){
            checkOk = checkDesignName()
            if (checkOk) {
                enableNextBtn()
                enableOptionsTab()
            } else {
                disableNextBtn()
                disableOptionsTab()
            }
        }
        return checkOk
    }

    function resolveNextBtnDisplay() {
        if (selectedStepIndex < 2) {
            // enable in case it was disable in step 3 due to missing design name
            enableNextBtn()
        } else {
            checkThirdStep()
        }
    }

    function enableNextBtn() {
        enableButton($('#nextButtonQuickStart'))
    }

    function disableNextBtn() {
        disableButton($('#nextButtonQuickStart'))
    }

    function enableSaveBtn() {
        enableSubmit($('#saveButtonQuickStart input'))
    }

    function disableSaveBtn() {
        disableButton($('#saveButtonQuickStart input'))
    }

    function clearSelectedLicenses() {
        selectedLicenses = []
        selectedFloatingLicenseNeedCheckIn = []
    }

    function checkFloatingLicenses() {
        if (floatingLicenseIdsNeedCheckIn) {
            if (selectedLicenses) {
                selectedFloatingLicenseNeedCheckIn = []
                selectedLicenses.forEach(licenseId => {
                    if (floatingLicenseIdsNeedCheckIn.includes(licenseId)) {
                        selectedFloatingLicenseNeedCheckIn.push(licenseId)
                        showFloatingLicenseWarning(licenseId)
                        preventUserFromGoingToStep2()
                    }
                })
            }
            // hide warning if the inactive floating licenses are not selected
            $('#quickStart-floatingLicenseWarningContainer div').each(function() {
                if ($(this).is(':visible')) {
                    const licenseIdOfWarning = $(this).attr('data-licenseId')
                    if (!selectedFloatingLicenseNeedCheckIn.includes(licenseIdOfWarning)) {
                        $(this).fadeOut()
                        // run checkMandatoryInputCondition to enable next btn
                        checkMandatoryInputCondition()
                    }
                }
            })
        }
    }

    function showFloatingLicenseWarning(selectedLicenseId) {
        const $warning = $('#quickStart-floatingLicenses-' + selectedLicenseId)
        if ($warning.is(':hidden')) {
            $warning.fadeIn()
        }
    }

    function handleCheckInFloatingLicenseClick(licenseId) {
        openFloatingLicenseModal(licenseId)
        $('#cancelButtonQuickStart').click()
    }

    function checkMandatoryInputCondition() {
        const $bldTypeInput = $('#${Constants.BLD_TYPE_SELECT}')
        const $nameInput = $('#${Constants.PROJECT_NAME_QUICKSTART}')
        const $countryInput = $('#${Constants.COUNTRY_SELECT}')
        const $productTypeInput = $('#${Constants.PRODUCT_TYPE_SELECT}')
        bldTypeOk = !!$bldTypeInput.val() || $bldTypeInput.length === 0 // project type other than building doesn't have building type
        nameOk = !!$nameInput.val()
        countryOk = !!$countryInput.val() || $countryInput.length === 0 // portfolio doesn't have country input
        productTypeOk = !!$productTypeInput.val() || $productTypeInput.length === 0 // only product has productType
        const everythingOk = bldTypeOk && nameOk && countryOk && productTypeOk

        if (everythingOk && !selectedFloatingLicenseNeedCheckIn.length) {
            removeBorderOfSelect2(null, $bldTypeInput)
            removeBorderOfSelect2(null, $productTypeInput)
            if ($nameInput.hasClass('redBorder')) {
                $nameInput.removeClass('redBorder')
            }
            hideElementByClassOrId(null, 'warningMandatoryInputQuickStartModal')
            enableOptionalInfoTab()

            if (selectedTemplateObject) {
                // enable tabs if the template allows user to change settings and template are set with some tools
                if (selectedTemplateObject.allowChange && needFetch) {
                    enableFirstDesignTab()
                    // can only save if the design name is filled
                    if (selectedTemplateObject.designName) {
                        enableOptionsTab()
                    } else {
                        disableOptionsTab()
                    }
                } else {
                    disableFirstDesignTab()
                    disableOptionsTab()
                }
            } else {
                disableFirstDesignTab()
                disableOptionsTab()
            }
            enableNextBtn()
            return true
        } else {
            if (!bldTypeOk) {
                addBorderToSelect2(null, $bldTypeInput)
            } else {
                removeBorderOfSelect2(null, $bldTypeInput)
            }
            if (!countryOk) {
                addBorderToSelect2(null, $countryInput)
            } else {
                removeBorderOfSelect2(null, $countryInput)
            }
            if (!productTypeOk) {
                addBorderToSelect2(null, $productTypeInput)
            } else {
                removeBorderOfSelect2(null, $productTypeInput)
            }

            if (!nameOk) {
                $nameInput.addClass('redBorder')
            } else {
                if ($nameInput.hasClass('redBorder')) {
                    $nameInput.removeClass('redBorder')
                }
            }
            if (!everythingOk) {
                showElementByClassOrId(null, 'warningMandatoryInputQuickStartModal')
            }
            preventUserFromGoingToStep2()
            return false
        }
    }

    function preventUserFromGoingToStep2() {
        disableOptionalInfoTab()
        disableFirstDesignTab()
        disableOptionsTab()
        disableNextBtn()
    }

    // check if design name has been filled
    function checkDesignName() {
        return !!$('#${Constants.BasicQueryVirtualSectionIds.FIRST_DESIGN.toString()} input[name="name"]').val();
    }

    // set the project template when a license is selected
    function setAvailableTemplate(licenseId) {
        if (licenseId) {
            const templates = licenseToTemplate[licenseId]

            if (templates) {
                // show the templates selection and add the templates
                if (!templateAvailable) {
                    templateAvailable = true
                    showTemplateSelect(true)
                }
                const $templateSelect = $("#${Constants.TEMPLATE_SELECT_QUICKSTART}")
                let hasDefaultTemplate = false
                templates.map(template => {
                    // add template to select if not exist
                    const selected = template.isDefault ? 'selected' : ''
                    if (selected !== '') {
                        hasDefaultTemplate = true
                    }
                    if (!$('#quickStartProjectModal-' + template.id + licenseId).length) {
                        $templateSelect.append('<option id="quickStartProjectModal-' + template.id + licenseId + '" value="' + template.id + '" data-licenseId="' +licenseId + '" ' + selected +' data-isPublic="'+ template.isPublic +'">' + template.name + '</option>')
                    }
                })

                // Trigger change to set template details if a default template is selected
                $templateSelect.trigger('change')
            }
        }
    }

    function enableOptionsTab() {
        const templateOptionsStep = stepper.steps()[sections.indexOf('${Constants.BasicQueryVirtualSectionIds.OPTIONS.toString()}')]
        if (templateOptionsStep && templateOptionsStep.options && !templateOptionsStep.options.enabled) {
            templateOptionsStep.enable(true)
        }
    }

    function disableOptionsTab() {
        const templateOptionsStep = stepper.steps()[sections.indexOf('${Constants.BasicQueryVirtualSectionIds.OPTIONS.toString()}')]
        if (templateOptionsStep && templateOptionsStep.options && templateOptionsStep.options.enabled) {
            templateOptionsStep.enable(false)
        }
    }

    function enableFirstDesignTab() {
        const firstDesignStep = stepper.steps()[sections.indexOf('${Constants.BasicQueryVirtualSectionIds.FIRST_DESIGN.toString()}')]
        if (firstDesignStep && firstDesignStep.options && !firstDesignStep.options.enabled) {
            firstDesignStep.enable(true)
        }
    }

    function disableFirstDesignTab() {
        const firstDesignStep = stepper.steps()[sections.indexOf('${Constants.BasicQueryVirtualSectionIds.FIRST_DESIGN.toString()}')]
        if (firstDesignStep && firstDesignStep.options && firstDesignStep.options.enabled) {
            firstDesignStep.enable(false)
        }
    }

    function enableOptionalInfoTab() {
        const optionalInfoStep = stepper.steps()[sections.indexOf('${Constants.BasicQueryVirtualSectionIds.PROJECT_DATA.toString()}')]
        if (optionalInfoStep && optionalInfoStep.options && !optionalInfoStep.options.enabled) {
            optionalInfoStep.enable(true)
        }
    }

    function disableOptionalInfoTab() {
        const optionalInfoStep = stepper.steps()[sections.indexOf('${Constants.BasicQueryVirtualSectionIds.PROJECT_DATA.toString()}')]
        if (optionalInfoStep && optionalInfoStep.options && optionalInfoStep.options.enabled) {
            optionalInfoStep.enable(false)
        }
    }

    // this should be run whenever the selection of project template is changed
    function clearFirstDesignTabAndOptionsTab() {
        $('#${Constants.QSPM_DESIGN_TAB_ID}').empty()
        $('#${Constants.QSPM_OPTIONS_TAB_ID}').empty()
    }

    function triggerPopoverForTemplateDetails() {
        keepPopoverOnHover('.templateDetails', 20, 0, '<div class="popover" role="tooltip"><div class="arrow greenTick"></div><div class="popover-content templateDetailsPopover"></div></div>')
    }

    function displayTemplateDetailsInfoIcon(licenseId) {
        // this is set in backend
        displayedInfoIconId = '#templateDetails-' + licenseId + '-' + selectedTemplateId
        // hide all
        $('.templateDetails').each(function() {
            $(this).hide()
            $(this).find('i').hide()
        })
        // show the info icon (details of selected template)
        showInfoIcon()
    }

    function showInfoIcon() {
        $(displayedInfoIconId).show()
        $(displayedInfoIconId).find('i').show()
    }

    function hideInfoIcon(clear) {
        $(displayedInfoIconId).find('i').hide()
        if (clear) {
            displayedInfoIconId = null
        }
    }

    function openLicenseDropdownIfFreeLicenseAvailable() {
        $('#quickStartProjectModal-licenseId').find('option').each(function(){
            if ($(this).attr("data-freeForAllEntities") === 'true') {
                const fail = checkFreeLicense($(this))
                if (!fail) {
                    // open license dropdown if a free license becomes available for show
                    $('#quickStartProjectModal-licenseId').select2('open')
                    $('.freeLicenseHelp').popover('show')
                }
            }
        })
    }

    // select2 mask our options => no data attribute can be extracted when it's hovered => need to set a custom template to embed the licenseId and templateId to select2 option
    function templateOptionFormat(template) {
        if (!template.id) {
            return template.text
        }
        const templateElem = template.element
        const templateId = templateElem.value
        const licenseId = $(templateElem).attr('data-licenseId')
        // set comboId on the select2 options to be able to find it when it's hover => to display the template details on hover
        return $('<p class="templateOption" data-comboId="' + licenseId + '-' + templateId + '">' + template.text +'</p>')
    }

    function setProjectDetailsFromProjectTemplate() {
        if (selectedTemplateObject) {
            if (selectedTemplateObject.projectName) {
                setValueToInput(null, '${Constants.PROJECT_NAME_QUICKSTART}', selectedTemplateObject.projectName)
                // trigger keyup to validate name
                $('#${Constants.PROJECT_NAME_QUICKSTART}').trigger('keyup')
            }
            if (selectedTemplateObject.buildingTypeResourceId) {
                setValueToSelect(null, '${Constants.BLD_TYPE_SELECT}', selectedTemplateObject.buildingTypeResourceId)
                if (selectedTemplateObject.allowChange === false) {
                    disableSelect(null, '${Constants.BLD_TYPE_SELECT}')
                }
            }
            if (selectedTemplateObject.countryResourceId) {
                setValueToSelect(null, '${Constants.COUNTRY_SELECT}', selectedTemplateObject.countryResourceId)
                if (selectedTemplateObject.allowChange === false) {
                    disableSelect(null, '${Constants.COUNTRY_SELECT}')
                }
            }
            if (selectedTemplateObject.productType) {
                setValueToSelect(null, '${Constants.PRODUCT_TYPE_SELECT}', selectedTemplateObject.productType)
                if (selectedTemplateObject.allowChange === false) {
                    disableSelect(null, '${Constants.PRODUCT_TYPE_SELECT}')
                }
            }
            if (selectedTemplateObject.isMandatory) {
                disableSelect(null, '${Constants.TEMPLATE_SELECT_QUICKSTART}', false)
            }
            if (selectedTemplateId) {
                $('#quickStart-selectedTemplateId').val(selectedTemplateId)
            }
        }
    }

    function setDesignDetailsFromProjectTemplate() {
        if (selectedTemplateObject) {
            if (selectedTemplateObject.designName) {
                const $designNameInput = $('#${Constants.QSPM_DESIGN_TAB_ID} input[name="name"]')
                $designNameInput.val(selectedTemplateObject.designName).trigger('keyup')
            }
        }
    }

    function copyFrameTypeToDesign(reverseCopy = false){
        const $frameTypeSelectInProject = $('#${Constants.BasicQueryVirtualSectionIds.PROJECT_DATA.toString()} select[name="basicQuerySection.frameType"]')
        const $frameTypeSelectInDesign = $('#${Constants.QSPM_DESIGN_TAB_ID} select[name="frameType"]')
        if ($frameTypeSelectInDesign.length && $frameTypeSelectInProject.length) {
            if (reverseCopy) {
                // copy from design to project
                $frameTypeSelectInProject.val($frameTypeSelectInDesign.val()).trigger('change')
            } else {
                //  copy from project to design
                $frameTypeSelectInDesign.val($frameTypeSelectInProject.val()).trigger('change')
            }
        }
    }

    function setApplyLCADefaultsFromTemplate() {
        // perform this before form submit
        if (selectedTemplateObject && selectedTemplateObject.applyLcaDefaults !== null) {
            let hasToolUsingLcaParams = checkIfIndicatorUsingLcaParametersIsSelected('${Constants.QSPM_DESIGN_TAB_ID}', 'indicatorIdList')
            if (hasToolUsingLcaParams) {
                $('#${Constants.QSPM_DESIGN_TAB_ID}').append('<input type="hidden" id="quickstartProjectModal-applyLcaDefaults" name="applyLcaDefaults" value="' + selectedTemplateObject.applyLcaDefaults + '"/>')
            }
        }
    }

    function disableToolsSelectOnFirstDesignTab() {
        // disable to wait until the options are fetched, in case user unselect a tool that is the 'start from tool' in template
        $('#${Constants.QSPM_DESIGN_TAB_ID} input[name="indicatorIdList"]').each(function() {
            disableCheckbox(this)
        })
    }

    function enableToolsSelectOnFirstDesignTab() {
        $('#${Constants.QSPM_DESIGN_TAB_ID} input[name="indicatorIdList"]').each(function() {
            enableCheckbox(this)
        })
    }

    function setJqueryForFrameTypeInDesign() {
        // copy frame type from design to project when it changes.
        $('#${Constants.QSPM_DESIGN_TAB_ID} select[name="frameType"]').on('select2:select', function() {
            copyFrameTypeToDesign(true)
        })
    }

    function setJqueryForToolsSelectOnFirstDesignTab() {
        // adjust the options accordingly when the tools for first design is selected/unselected.
        $('#${Constants.QSPM_DESIGN_TAB_ID} input[name="indicatorIdList"]').each(function() {
            const selectFirstToolId = selectedTemplateId + '${Constants.OPEN_INDICATOR_SELECT_POSTFIX}' // from fetched template options
            const templateOptionsDiv = selectedTemplateId + '${Constants.TEMPLATE_EXTENDED_OPTIONS_POSTFIX}' // from fetched template options
            const entityTemplateSelect = selectedTemplateId + '${Constants.ENTITY_TEMPLATE_SELECT_POSTFIX}' // from fetched template options
            const indicatorId = $(this).val()
            const indicatorName = $(this).closest('td').text()

            $(this).on('click', function() {
                handleProjectTemplateIndicatorCheckBoxClick(this, selectFirstToolId, indicatorId, indicatorName, templateOptionsDiv, null, null, entityTemplateSelect, false)
            })
        })
    }

    function setJqueryForDesignName() {
        $('#${Constants.BasicQueryVirtualSectionIds.FIRST_DESIGN.toString()} input[name="name"]').on('change', checkThirdStep)
    }

    function setTemplateFetched() {
        templateFetched = true
        disableToolsSelectOnFirstDesignTab()
        setDesignDetailsFromProjectTemplate()
        copyFrameTypeToDesign()
        setJqueryForFrameTypeInDesign()
        setJqueryForDesignName()
        // submit and next btn were disabled in fetchNewDesignModalForSelectedTemplate
        enableSaveBtn()
        enableNextBtn()
        removeLoaderFromButton('saveButtonQuickStart')
        enableToolsSelectOnFirstDesignTab()
        setJqueryForToolsSelectOnFirstDesignTab()
    }

    function fetchSuccessCB(data) {
        if (data.templateOptions && data.designModal) {
            $('#${Constants.QSPM_OPTIONS_TAB_ID}').empty().append(data.templateOptions)
            $('#${Constants.QSPM_DESIGN_TAB_ID}').empty().append(data.designModal)
            setTemplateFetched()
        } else {
            errorSwal('error', 'Oops...', '${message(code : "unknownError")}')
        }
    }

    function fetchErrorCB() {
        errorSwal('error','${message(code: 'unknownError')}')
        // submit is disabled in fetchNewDesignModalForSelectedTemplate
        enableSaveBtn()
        // remove spinner
        removeLoaderFromButton('saveButtonQuickStart')
    }

    function fetchDesignModalAndOptions() {
        clearFirstDesignTabAndOptionsTab()
        // disable submit and next button to wait for template fetch ajax
        disableSaveBtn()
        disableNextBtn()
        // append spinner in case takes too long to fetch, so user notices.
        appendLoaderToButton('saveButtonQuickStart')
        // add spinner to wait during ajax
        let loader = $('#localizedSpinner'); // loading spinner on main.gsp with localized
        let copy = loader.clone().css('margin-top', ($('#projectModalBody').height() - 270) / 2).show();// clone hidden element and display, also align center vertically (spinner height is currently 270px)
        $('#${Constants.QSPM_DESIGN_TAB_ID}').empty().append(copy)
        $('#${Constants.QSPM_OPTIONS_TAB_ID}').empty().append(copy)

        const entityType = $('#${Constants.BLD_TYPE_SELECT}').val()
        let queryString = '&formId=quickStartProjectForm&templateId=' + selectedTemplateId + '&entityType=' + entityType
        queryString += selectedLicenses ? '&licenseIds=' + selectedLicenses.join('&licenseIds=') : ''
        queryString += selectedTemplateObject && selectedTemplateObject.useIndicatorIds ? '&useIndicatorIds=' + selectedTemplateObject.useIndicatorIds.join('&useIndicatorIds=') : ''

        ajaxForQueryString('/app/sec/projectTemplate/getProjectTemplateDetails', queryString, fetchSuccessCB, fetchErrorCB, true);
    }

    // select2 filter the options according to this every time dropdown opens
    function resultState(data, container) {
        const fails = checkFreeLicense(data.element);

        if (fails) {
            return null
        } else {
            return data.text;
        }
    }

    // We have free licenses that are restricted to building types or country, so check the conditions
    function checkFreeLicense(elem) {
        let fails = false;
        let freeForAllEntities = $(elem).attr("data-freeForAllEntities");

        if ("true" === freeForAllEntities) {
            let country = $('#${Constants.COUNTRY_SELECT}').val();
            let type = $('#${Constants.BLD_TYPE_SELECT}').val();
            let condiditionalCountries = $(elem).attr("data-condiditionalCountries");
            let conditionalBuildingTypes = $(elem).attr("data-conditionalBuildingTypes");

            if (condiditionalCountries) {
                if (country) {
                    if (condiditionalCountries.indexOf(country) === -1) {
                        fails = true;
                    }
                } else {
                    fails = true;
                }
            }

            if (conditionalBuildingTypes) {
                if (type) {
                    if (conditionalBuildingTypes.indexOf(type) === -1) {
                        fails = true;
                    }
                } else {
                    fails = true;
                }
            }
        }
        return fails;
    }

    function resolveLicenseSelectDisplay($elementSelect2) {
        let atleastOneShowable = false;
        for (let i = 0; i < $elementSelect2.children().length; i++) {
            let fails = checkFreeLicense($elementSelect2.children()[i]);

            if (!fails) {
                atleastOneShowable = true;
                $('#quickStartProjectModal-availableLicenses').show();
                break;
            }
        }

        if (!atleastOneShowable) {
            $('#quickStartProjectModal-availableLicenses').hide();
        }
    }

    function showPlanetaryCompatibility(element) {
        let valueOfSelect = $(element).val();
        let statesWithPlanetary = ${statesWithPlanetary as grails.converters.JSON};
        if (statesWithPlanetary && statesWithPlanetary.length > 0) {
            let lastSibling = $(element).parent().siblings()
            if (statesWithPlanetary.indexOf(valueOfSelect) != -1) {
                $(lastSibling).find(".success").removeClass("hidden")
                $(lastSibling).find(".alert-warning").addClass("hidden")
            } else {
                $(lastSibling).find(".success").addClass("hidden")
                $(lastSibling).find(".alert-warning").removeClass("hidden")
            }
        }
    }

    function handleSubmit(submitBtnElem) {
        appendLoaderToButton('saveButtonQuickStart');
        disableSubmit(submitBtnElem);
        disableButton(null, 'prevButtonQuickStart')
        disableButton(null, 'cancelButtonQuickStart')
        if (needFetch) {
            if (templateFetched) {
                setApplyLCADefaultsFromTemplate()
                submitQuickStartForm()
            } else {
                // show swal error in case something's wrong
                errorSwal('error', '${message(code : "popup.noServer.title")}', '${message(code : "tryAgain")}')
                enableSubmit(submitBtnElem)
                enableButton(null, 'prevButtonQuickStart')
                enableButton(null, 'cancelButtonQuickStart')
                removeLoaderFromButton('saveButtonQuickStart')
            }
        } else {
            submitQuickStartForm()
        }
    }

    function submitQuickStartForm() {
        // enable so that grails pick up all inputs
        enableAllInputInDiv(null, 'projectModalBody')
        $('#saveButtonQuickStart').closest('form').submit()
    }

    function validateLicenseKey(inputElem) {
        if ($(inputElem).val()) {
            enableSubmit(null , 'quickStart-licenseKey-submit')
        } else {
            disableSubmit(null, 'quickStart-licenseKey-submit')
        }
    }

    function submitLicenseKey(submitBtnElem) {
        appendLoaderToButton(null, submitBtnElem);
        disableSubmit(submitBtnElem);

        const licenseKey = $('#quickStart-licenseKey').val()
        const entityClass = '${entityClass}'
        if (licenseKey) {
            $.ajax({
                type: 'POST',
                data: 'licenseKey=' + licenseKey + '&entityClass=' + entityClass,
                url: '/app/sec/projectTemplate/getLicenseAndTemplateByLicenseKey',
                async: true,
                success: function (data) {
                    removeLoaderFromButton(null, submitBtnElem)
                    if (data.output === 'error') {
                        errorSwal('error', '', data.error, true)
                    } else {
                        // success
                        // clear license key input and hide
                        $('#quickstart-enterLicenseKeyLabelLink').click()
                        $('#quickStart-licenseKey').val('')
                        if (data.status) {
                            // success swal
                            errorSwal('success', '', data.status, true)
                        }
                        // update template details popovers
                        if (data.templateDetailsList) {
                            $('#quickStart-templateDetailsList').empty().append(data.templateDetailsList)
                        }

                        if (data.licenseToTemplates) {
                            licenseToTemplate = data.licenseToTemplates
                        }

                        if (data.license) {
                            const license = data.license
                            const $licenseSelect = $('#quickStartProjectModal-licenseId')
                            const $existing = $licenseSelect.find('option[value="' + license.id + '"]')
                            // in case user enter licensekey twice
                            if (!$existing.length) {
                                const newOpt = '<option selected data-freeForAllEntities="' + license.freeForAllEntities + '" data-planetary="' + license.planetary + '" data-condiditionalCountries="' + license.conditionalCountries + '" data-conditionalBuildingTypes="' + license.conditionalBuildingTypes + '" value="' + license.id + '">' + license.name + '</option>'
                                $licenseSelect.append(newOpt).trigger('change').trigger('select2:select')
                            } else {
                                $existing.prop('selected', 'selected')
                                $licenseSelect.trigger('change').trigger('select2:select')
                            }
                            // set flag to run licenseKey check n send email
                            $licenseSelect.append('<input type="hidden" name="licenseIdRetrievedByLicenseKey" value="' + license.id + '"/>')
                        }
                        // trigger popover for template details again
                        triggerPopoverForTemplateDetails()
                        // display info icon if it was displayed before
                        if (displayedInfoIconId) {
                            showInfoIcon()
                        }
                    }
                },
                error: function () {
                    enableSubmit(submitBtnElem);
                    removeLoaderFromButton(null, submitBtnElem)
                    errorSwal('error', 'Oops...', '${message(code : "unknownError")}')
                }
            });
        } else {
            errorSwal('warning', '', '${message(code : "licenseKey.missing")}')
        }
    }

</script>