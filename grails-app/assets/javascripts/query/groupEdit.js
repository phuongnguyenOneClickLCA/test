// NOTE: this js is dependent on 360optimi.js
const GROUP_EDITABLE_INPUT_HTML_TAGS = 'input, textarea, select'
const ADDITIONAL_QUESTION_AUTOCOMPLETE_INPUT = '.additionalQuestionAutocomplete'
const LOCAL_COMP_QUESTIONID = 'applyLocalComps'
const NO_LOCAL_COMP = 'noLocalCompensation'
const SERVICE_LIFE_QUESTIONID = 'serviceLife'
const CONFIRM_BUTTON_ID = 'groupEditConfirmButton';

function fetchGroupEditModal(groupEditable, selectedQueryRows, queryId, entityId, indicatorId, title, loadingText, localizedSave, localizedCancel, somethingWentWrong, selectSomethingMsg) {
    if (groupEditable) {
        const displayTitle = '<div class="oneClickColorScheme" style="margin-left: -30px;"><i class="fas fa-object-group fiveMarginRight size-24"></i>' + title + '</div>'
        Swal.fire({
            title: displayTitle,
            text: loadingText + '...',
            onOpen: function () {
                Swal.showLoading()
            },
            customClass: 'swal-wide'
        })
        let queryParams = 'queryId=' + queryId + '&indicatorId=' + indicatorId + '&entityId=' + entityId
        let allRowsToUpdate = [...selectedQueryRows]
        queryParams = extractInfoForGroupEdit(queryParams, selectedQueryRows, allRowsToUpdate)
        $.ajax({
            type: 'POST',
            data: queryParams,
            url: '/app/sec/query/getGroupEditContent',
            async: true,
            success: function (data) {
                if (data.output) {
                    Swal.fire({
                        title: displayTitle,
                        html: data.output,
                        customClass: {
                            container: 'swal-groupEdit', // this class set z-index of swal to 900
                            popup: 'groupEditHtmlContainer',
                            actions: 'zIndexZero'
                        },
                        onOpen: initGroupEditModal,
                        preConfirm: function () {
                            return new Promise(function (resolve, reject) {
                                Swal.showLoading()
                                // set timeout to show loading
                                setTimeout(function() {
                                    const okToSave = handleGroupEdit(allRowsToUpdate, data)
                                    if (!okToSave) {
                                        Swal.showValidationMessage(selectSomethingMsg)
                                        Swal.hideLoading()
                                        reject()
                                    } else {
                                        triggerFormChanged()
                                        resolve()
                                    }
                                }, 100)
                            })
                        },
                        reverseButtons: true,
                        closeOnConfirm: false,
                        showCancelButton: true,
                        closeOnCancel: true,
                        confirmButtonText: localizedSave,
                        confirmButtonColor: '#8DC73F',
                        cancelButtonText: localizedCancel,
                        cancelButtonColor: '#CDCDCD',
                        allowOutsideClick: false,
                        allowEscapeKey: false,
                        allowEnterKey: false
                    }).then((result) => {
                        if (result.isConfirmed) {
                            const triggerCalcValueIsTrue = castStringToBoolean($(TRIGGER_CALCULATION_ELEMENT_ID).val())

                            if (!triggerCalcValueIsTrue) {
                                triggerCalculationIfNeededForAnyElement($('.groupEditModal .groupEditAnswer:visible'));
                            }
                        }
                    })
                    triggerPopoverOnHover('[rel="popover"]')
                }
            },
            error: function (data) {
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: somethingWentWrong
                })
            }
        })
    }
}

/**
 * Populate query parameters for ajax and allRowsToUpdate array from the selectedQueryRows
 * @param {String} queryParams
 * @param {Array} selectedQueryRows
 * @param {Array} allRowsToUpdate
 * @returns {String}
 */
function extractInfoForGroupEdit(queryParams, selectedQueryRows, allRowsToUpdate) {
    if ($(selectedQueryRows).length > 0) {
        $(selectedQueryRows).each(function () {
            const $row = $(this)
            const manualId = $($row).attr('data-manualIdForMoving')
            const resourceUUID = $($row).attr('data-resourceUUID')
            const questionId = $($row).attr('data-questionId')
            queryParams += '&questionId=' + questionId
            queryParams += '&sectionId=' + $($row).attr('data-sectionId')
            queryParams += '&resourceUUID=' + resourceUUID
            queryParams += '&manualIdsPerQuestion=' + manualId + '.' + questionId
            queryParams += '&manualIdsPerResourceUUID=' + manualId + '.' + resourceUUID
            if (isParentConstructionRow($row)) {
                const parentResourceUUID = resourceUUID
                // get info from constituents as well (as they can't be selected on UI)
                const uniqueConstructionIdentifier = $($row).attr('data-constructionrow')
                $('tr[data-dragmewithparent="' + uniqueConstructionIdentifier +'"]').each(function() {
                    const constituent = $(this)
                    allRowsToUpdate.push(constituent)
                    const constituentManualId = $(constituent).attr('data-manualIdForMoving')
                    const constituentResourceUUID = $(constituent).attr('data-resourceUUID')
                    queryParams += '&manualIdsPerQuestion=' + constituentManualId + '.' + questionId
                    queryParams += '&manualIdsPerResourceUUID=' + constituentManualId + '.' + constituentResourceUUID
                    queryParams += '&constituentsPerConstruction=' + constituentResourceUUID + '.' + parentResourceUUID
                    queryParams += '&constituentResourceUUID=' + constituentResourceUUID
                })
            }
        })
    }
    return queryParams
}

function lockGroupEditModal() {
    $('.groupEditModal').addClass('removeClicks')
}

/**
 * If the checkbox / switch of row is selected, that row is selected for group editing
 * @param row
 * @returns {boolean}
 */
function isSelectedForGroupEditing(row) {
    return $(row).attr('data-isActivated') === 'true'
}

function toggleRowActivationStatus(row, doActivate) {
    $(row).attr('data-isActivated', doActivate)
}

function toggleCloseBtn(row, doShow) {
    if (row) {
        if (doShow) {
            $(row).find('.closeTdGroupEdit').fadeIn().removeClass('forceHide').css('padding-bottom','15px')
        } else {
            $(row).find('.closeTdGroupEdit').hide()
        }
    }
}

function toggleClickToEditLink(row, doShow) {
    if (row) {
        if (doShow) {
            $(row).find('.clickToEdit').fadeIn().removeClass('forceHide')
        } else {
            $(row).find('.clickToEdit').hide()
        }
    }
}

function toggleAnswerInput(row, doShow) {
    if (row) {
        const groupEditAnswerElement = $(row).find('.groupEditAnswer');
        if (doShow) {
            // display the answer input td, also click on any link to show the "real" input if it has.
            groupEditAnswerElement.fadeIn().removeClass('forceHide').css('display','flex');

            let linkToReplace = groupEditAnswerElement.find('a');
            if (linkToReplace.length > 0) {
                linkToReplace.click();
                updateAutocomplete($(groupEditAnswerElement).find(ADDITIONAL_QUESTION_AUTOCOMPLETE_INPUT));
            }
            enableConfirmButtonIfNeeded(groupEditAnswerElement);
        } else {
            groupEditAnswerElement.hide()
            disableConfirmButtonIfNeeded();
        }
    }
}

function initGroupEditModal() {

    let confirmButton = Swal.getConfirmButton();
    let groupEditAnswerElements = $('.groupEditModal .groupEditAnswer');

    $(confirmButton).attr("id", CONFIRM_BUTTON_ID);
    disableButton(confirmButton);

    groupEditAnswerElements.addClass("skipTriggerCalculationOnChange");
    groupEditAnswerElements.on("change", GROUP_EDITABLE_INPUT_HTML_TAGS, function(event) {
        if (!$(this).is('select')) {
            // for field validation, confirm button should be disabled if some field has error
            $(this).trigger("blur");
        }
        toggleConfirmButtonForElement(event.delegateTarget);
    });
}

function modalHasErrors() {
    return $('.groupEditModal .groupEditAnswer:visible').hasClass("answerError");
}

function elementHasErrors(element) {
    return $(element).hasClass("answerError");
}

function toggleConfirmButtonForElement(element) {

    if (hasValueForAnyChildrenField(element) && !elementHasErrors(element)) {
        enableConfirmButtonIfNeeded(element);
    } else {
        disableConfirmButtonIfNeeded();
    }
}

function enableConfirmButtonIfNeeded(groupEditAnswerElement) {
    let confirmButton = $("#" + CONFIRM_BUTTON_ID);

    if (!confirmButton.attr("disabled")) {
        return;
    }

    if (hasValueForAnyChildrenField(groupEditAnswerElement) && !modalHasErrors()) {
        enableButton(confirmButton);
    }
}

function disableConfirmButtonIfNeeded() {
    let visibleAnswerElements = $('.groupEditModal .groupEditAnswer:visible');
    let disable = true;
    let confirmButton = $("#" + CONFIRM_BUTTON_ID);

    if (confirmButton.attr("disabled")) {
        return;
    }

    if (!modalHasErrors()) {
        visibleAnswerElements.each(function (index, answerElement) {
            if (disable && hasValueForAnyChildrenField(answerElement)) {
                disable = false;
                return disable;
            }
        });
    }

    if (disable) {
        disableButton(confirmButton);
    }
}

function hasValueForAnyChildrenField(parentElement) {

    let hasValue = false;
    let inputElements = $(parentElement).find(GROUP_EDITABLE_INPUT_HTML_TAGS + ", " + ADDITIONAL_QUESTION_AUTOCOMPLETE_INPUT);
    inputElements.each(function(index, field) {
        if ($(field).val() && hasNotIgnoredValue(field)) {
            hasValue = true;
            return false;
        }
    })
    return hasValue;
}

/**
 * Checks whether "Localisation" field (applyLocalComps) has value "Not applied" (noLocalCompensation)
 * This value is ignored and it is not saved.
 * @param input
 * @returns {boolean}
 */
function hasNotIgnoredValue(input) {
    const inputName = $(input).attr('name');
    const additionalQuestionId = getAdditionalQuestionIdFromInputName(inputName);

    let hasIgnoredValue = false;

    if (additionalQuestionId && additionalQuestionId === LOCAL_COMP_QUESTIONID) {
        const answer = $(input).val();
        if (answer === NO_LOCAL_COMP) {
            hasIgnoredValue = true;
        }
    }

    return !hasIgnoredValue;
}

/**
 * Overwrites onSelect for autocomplete:
 * - removed triggerFormChanged and triggerCalculationIfNeededForAutocompleteSelect
 * - added toggleConfirmButtonForElement
 * @param autocompleteElement
 */
function updateAutocomplete(autocompleteElement) {
    if (autocompleteElement && autocompleteElement.length > 0) {
        let autocompleteOptions = {
            onSelect: function (suggestion) {
                handleAutocompleteOnSelect(suggestion, $(this), null, null, null, null, true);
                toggleConfirmButtonForElement(autocompleteElement.closest(".groupEditAnswer"));
            }
        };
        autocompleteElement.devbridgeAutocomplete().setOptions(autocompleteOptions);
    }
}

function handleClickToEdit(link, doActivate) {
    const $row = $(link).closest('.groupEditRow')
    if (doActivate) {
        toggleRowActivationStatus($row, true)
        toggleClickToEditLink($row, false)
        toggleCloseBtn($row, true)
        toggleAnswerInput($row, true)

        // reset the validation msg in case it is shown
        Swal.resetValidationMessage();
    } else {
        toggleRowActivationStatus($row, false)
        toggleClickToEditLink($row, true)
        toggleCloseBtn($row, false)
        toggleAnswerInput($row, false)
        toggleCloseBtn($row, false)
    }
}

/**
 * Handle group editing after user clicks ok
 * this take the input, that are selected for editing on modal, row by row >>> extracts the input + text >>> update the input +text to selected query rows
 * @param allRowsToUpdate
 * @param response res from ajax call
 * @return {boolean}
 */
function handleGroupEdit(allRowsToUpdate, response) {
    const groupEditRows = $('.groupEditModal').find('.groupEditRow')
    let needSave = false
    groupEditRows.each(function () {
        // each group edit row is an additional question that can be group edited
        const groupEditRow = $(this)
        if (isSelectedForGroupEditing(groupEditRow)) {
            let additionalQuestionIdToUpdate = []
            let newAnswers = []
            let newTexts = []
            let newHoverTexts = []
            $(groupEditRow).find('.groupEditAnswer').find(GROUP_EDITABLE_INPUT_HTML_TAGS).each(function () {
                // each input will be an item in all lists
                getGroupUpdateContents($(this), additionalQuestionIdToUpdate, newAnswers, newTexts, newHoverTexts)
            })

            if (additionalQuestionIdToUpdate.length > 0) {
                updateToQueryRows(allRowsToUpdate, additionalQuestionIdToUpdate, newAnswers, response.manualIdsWithPermanentServiceLifeMap, newTexts, newHoverTexts, response.nameMaxLength)
            }
            needSave = true
        }
    })
    return needSave
}

/**
 * Get the contents (answers) in the inputs (only those that are for an additional question) on group edit modal
 * There are input type text + checkbox, text area, select.
 *
 * @param input
 * @param {Array} additionalQuestionIdToUpdate
 * @param {Array} newAnswers
 * @param {Array} newTexts
 * @param {Array} newHoverTexts
 */
function getGroupUpdateContents(input, additionalQuestionIdToUpdate, newAnswers, newTexts, newHoverTexts) {
    const inputName = $(input).attr('name')
    const additionalQuestionId = getAdditionalQuestionIdFromInputName(inputName)
    // only extract info from inputs that can extract the additionalQuestionId from name attribute (autocomplete inputs do not have name attr, they have in the hidden one)
    if (additionalQuestionId) {
        if (additionalQuestionId === LOCAL_COMP_QUESTIONID) {
            getNewLocalCompAnswer(input, additionalQuestionIdToUpdate, newAnswers, newTexts, newHoverTexts)
        } else if (isCheckBoxInput(input)) {
            const newAnswer = getCheckBoxAdditionalAnswer(input)
            if (newAnswer === true || newAnswer === false) {
                additionalQuestionIdToUpdate.push(additionalQuestionId)
                newAnswers.push(newAnswer)
                newTexts.push(null)
                newHoverTexts.push(getHoverText(input))
            }
        } else if (isSelectInput(input)) {
            const newAnswer = $(input).val()
            if (newAnswer) {
                const newText = $(input).find('option[value="' + newAnswer + '"]').text()
                additionalQuestionIdToUpdate.push(additionalQuestionId)
                newAnswers.push(newAnswer)
                newTexts.push(newText)
                newHoverTexts.push(getHoverText(input))
            }
        } else if (isAutocompleteAdditionalQuestionHiddenInput(input)){
            const newAnswer = $(input).val()
            additionalQuestionIdToUpdate.push(additionalQuestionId)
            newAnswers.push(newAnswer)
            getNewTextAndHoverTextFromAutocompleteQuestion(input, additionalQuestionId, newTexts, newHoverTexts)
        } else {
            // text and textarea questions
            const newAnswer = $(input).val()
            if (newAnswer) {
                additionalQuestionIdToUpdate.push(additionalQuestionId)
                newAnswers.push(newAnswer)
                newTexts.push(newAnswer)
                newHoverTexts.push(newAnswer)
            }
        }
    }
}


/**
 * Autocomplete typeahead select input has a sibling hidden input
 * @param input
 * @returns {boolean}
 */
function isAutocompleteAdditionalQuestionHiddenInput(input) {
    return $(input).hasClass('additionalQuestionAutocompleteHiddenInput')
}

/**
 *
 * @param input
 * @param {String} targetQuestionId
 * @param {Array} newTexts
 * @param {Array} newHoverTexts
 */
function getNewTextAndHoverTextFromAutocompleteQuestion(input, targetQuestionId, newTexts, newHoverTexts) {
    if (input) {
        let text = null
        const parentTd = $(input).closest('td')
        $(parentTd).find(ADDITIONAL_QUESTION_AUTOCOMPLETE_INPUT).each(function () {
            const additionalQuestionId = $(this).attr('data-additionalQuestionId')
            // run check, in case one td has multiple autocomplete
            if (additionalQuestionId === targetQuestionId) {
                text = $(this).val()
            }
        })
        newTexts.push(text)
        // text is taken as hover text as well
        newHoverTexts.push(text)
    }
}

function getHoverText() {
    if ($(this).attr('data-content') !== undefined) {
        return $(this).attr('data-content')
    }
    return null
}

/**
 * Update the additional answer on group edit modal to the selected rows in query.
 * These lists are compiled in {@see getGroupUpdateContents}
 * @param {Array} allRowsToUpdate
 * @param {Array} additionalQuestionIdToUpdate
 * @param {Array} newAnswers
 * @param {Array} newTexts
 * @param {Array} newHoverTexts
 * @param {Object} nameMaxLengthMap an object with key: the additional question id, and value is the max length that the name of the text for this question can be.
 * @param {Object} manualIdsWithPermanentServiceLifeMap an object with key: the question id (that has permanent service life), and value: list of manualIds.
 */
function updateToQueryRows(allRowsToUpdate, additionalQuestionIdToUpdate, newAnswers, manualIdsWithPermanentServiceLifeMap, newTexts, newHoverTexts, nameMaxLengthMap) {
    $(allRowsToUpdate).each(function () {
        const queryRow = $(this)
        $(queryRow).removeClass("existingResourceRow");
        $(queryRow).find(GROUP_EDITABLE_INPUT_HTML_TAGS).each(function () {
            const input = $(this)
            const additionalQuestionIdOfQueryInput = getAdditionalQuestionIdFromInputName($(input).attr('name'))
            // look for the inputs that we need to update answer
            if (additionalQuestionIdOfQueryInput && additionalQuestionIdToUpdate.includes(additionalQuestionIdOfQueryInput)) {
                // all infos of a input to be updated are at the same position in the corresponding lists
                // now find the answer to be updated for this input on query
                const index = additionalQuestionIdToUpdate.indexOf(additionalQuestionIdOfQueryInput)
                if (index !== -1 && isEditableInputAddQ(input, additionalQuestionIdOfQueryInput)) {
                    // update the answer
                    const newAnswer = newAnswers[index]
                    if (newAnswer !== null && newAnswer !== '') {
                        // update input value
                        if (isCheckBoxInput(input)) {
                            handleUpdateAdditionalCheckBoxAnswer(input, newAnswer)
                        } else if (additionalQuestionIdOfQueryInput === SERVICE_LIFE_QUESTIONID) {
                            handleUpdateServiceLifeAnswer(input, newAnswer, manualIdsWithPermanentServiceLifeMap, queryRow)
                        } else if (additionalQuestionIdOfQueryInput === LOCAL_COMP_QUESTIONID) {
                            handleUpdateLocalCompAnswer(input, newAnswer)
                        } else {
                            $(input).val(newAnswer)
                        }

                        if (isSelectInput(input)) {
                            // need to trigger change for select
                            $(input).change()
                        }

                        // update text and hover text if necessary
                        if (isHiddenInput(input) && hasTextLinkForHiddenInputAddQ(input, additionalQuestionIdOfQueryInput)) {
                            updateTextLinkAndHoverText(input, additionalQuestionIdOfQueryInput, newTexts[index], newHoverTexts[index], nameMaxLengthMap)
                        } else if (isAutocompleteAdditionalQuestionHiddenInput(input)) {
                            // update text of type ahead questions on query that is not a text link
                            updateTextAutocompleteQuestion(input, additionalQuestionIdOfQueryInput, newTexts[index])
                        }
                    }
                }
            }
        })
    })
}

/**
 * For service life additional question, check in the map (sent from backend) if the row (on query) belongs to a question
 * that is with permanent service life. If that's the case, cannot update serviceLife, else we can.
 * @param input the additional answer input
 * @param newAnswer
 * @param manualIdsWithPermanentServiceLifeMap map from backend that shows which manualIds belong to a question with permanent service life
 * @param queryRow the resource row on query
 */
function handleUpdateServiceLifeAnswer(input, newAnswer, manualIdsWithPermanentServiceLifeMap, queryRow) {
    if (queryRow && manualIdsWithPermanentServiceLifeMap && newAnswer !== null && newAnswer !== '') {
        let canUpdate = true
        const questionIdOfRow = $(queryRow).attr('data-questionId')

        if (manualIdsWithPermanentServiceLifeMap[questionIdOfRow]) {
            const manualIdsWithPermanentServiceLife = manualIdsWithPermanentServiceLifeMap[questionIdOfRow]
            const manualIdOfRow = $(queryRow).attr('data-manualIdForMoving')

            if (manualIdOfRow && manualIdsWithPermanentServiceLife.includes(manualIdOfRow)) {
                canUpdate = false
            }
        }

        if (canUpdate) {
            $(input).val(newAnswer)
        }
    }
}

/**
 * Only get new answer for local comp answer (addQid: "applyLocalComps") if new answer is not 'noLocalCompensation'
 * @param input
 * @param additionalQuestionIdToUpdate
 * @param newAnswers
 * @param newTexts
 * @param newHoverTexts
 */
function getNewLocalCompAnswer(input, additionalQuestionIdToUpdate, newAnswers, newTexts, newHoverTexts) {
    if (input) {
        const newAnswer = $(input).val()
        if (newAnswer !== NO_LOCAL_COMP) {
            const newText = $(input).find('option[value="' + newAnswer + '"]').text()
            additionalQuestionIdToUpdate.push(LOCAL_COMP_QUESTIONID)
            newAnswers.push(newAnswer)
            newTexts.push(newText)
            newHoverTexts.push(getHoverText(input))
        }
    }
}

/**
 * Only update the local comp answer (addQid: "applyLocalComps") if resource can be applied local comp (its current answer is not 'noLocalCompensation')
 * if a resource cannot apply local comp, there would be no input for its local comp energy profile ('compensationEnergyProfile') question
 * so we don't care
 * newAnswer extracted from {@see getNewLocalCompAnswer}
 * @param input
 * @param newAnswer
 */
function handleUpdateLocalCompAnswer(input, newAnswer) {
    if (input && newAnswer !== null && newAnswer !== '') {
        const currentLocalCompAnswer = $(input).val()
        if (newAnswer !== NO_LOCAL_COMP) {
            // there are cases that answer is 'noLocalCompensation' but there's a text link next to it >>> still can apply local comp
            const canUpdate = (currentLocalCompAnswer === NO_LOCAL_COMP && hasTextLinkForHiddenInputAddQ(input, LOCAL_COMP_QUESTIONID)) || currentLocalCompAnswer !== NO_LOCAL_COMP
            if (canUpdate) {
                $(input).val(newAnswer)
            }
        }
    }
}

/**
 * If selected rows has one locked dataset, cannot group edit
 * @param $selectedRows
 * @returns {boolean}
 */
function isAbleToGroupEdit($selectedRows) {
    const count = $($selectedRows).length
    if (count > 0) {
        for (let i = 0; i < count; i++) {
            const $row = $($selectedRows)[i]
            if (isLockedDataset($row)) {
                return false
            }
        }
    }
    return true
}


/**
 * Update the new text and hover text to the link next to hidden input.
 * The new text and hover text was extracted in {@see getGroupUpdateContents}
 * Also cut the text so it fits the input width (the max length get from backend)
 * @param input
 * @param {String} targetQuestionId
 * @param {String} newText
 * @param {String} newHoverText
 * @param {Object} nameMaxLengthMap an object with key: the additional question id, and value is the max length that the name of the text for this question can be.
 */
function updateTextLinkAndHoverText(input, targetQuestionId, newText, newHoverText, nameMaxLengthMap) {
    if (input && targetQuestionId && newText !== null) {
        const parentTd = $(input).closest('td')
        $(parentTd).find('a.lockableDatasetQuestion').each(function () {
            const textLink = $(this)

            // this check is for the case that one td has multiple text links for different inputs >>> make sure we update the right one
            const additionalQuestionId = $(textLink).attr('data-additionalquestionid')
            if (additionalQuestionId === targetQuestionId) {
                $(textLink).text(abbreviateTextBasedOnQuestionInputWidth(newText, targetQuestionId, nameMaxLengthMap))

                // update hover text if it has
                if ($(textLink).attr('data-content') && newHoverText !== null) {
                    $(textLink).attr('data-content', newHoverText)
                    switchHoverMessage(textLink, newHoverText)
                }
            }

        })
    }
}

/**
 *  Update text of the autocomplete
 *  The newText was extracting in {@see getNewTextAndHoverTextFromAutocompleteQuestion}
 * @param input
 * @param {String} targetQuestionId
 * @param {String} newText
 */
function updateTextAutocompleteQuestion(input, targetQuestionId, newText) {
    if (input && targetQuestionId) {
        const parentTd = $(input).closest('td')
        $(parentTd).find(ADDITIONAL_QUESTION_AUTOCOMPLETE_INPUT).each(function () {
            const additionalQuestionId = $(this).attr('data-additionalquestionid')
            // this check is for the case that one td has multiple autocomplete for different questions >>> make sure we update the right one
            if (additionalQuestionId === targetQuestionId) {
                $(this).val(newText)
            }
        })
    }
}

/**
 * Abbreviate text so that it fits the question input width. Number of max length for each question is calculated in backend
 * @param {String} text
 * @param {String} targetQuestionId
 * @param {Object} nameMaxLengthMap a map with key: questionId, and value is the max length of the text for that question
 * @returns {String}
 */
function abbreviateTextBasedOnQuestionInputWidth(text, targetQuestionId, nameMaxLengthMap) {
    if (nameMaxLengthMap && targetQuestionId) {
        const maxLength = nameMaxLengthMap[targetQuestionId]
        if (maxLength) {
            text = text.substring(0, maxLength)
        }
    }
    return text
}