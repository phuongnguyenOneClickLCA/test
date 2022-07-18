/*
 * DEPENDENCIES:
 * - 360optimi.js
 * - constants.js
 */

function addTriggerCalcEventListenerToChildElement(parentElement, selector, event) {
    if (parentElement && selector && event) {
        if (!$(parentElement).hasClass("skipTriggerCalculationOnChange")) {
            $(parentElement).find(selector).on(event, function () {
                triggerCalculationIfNeeded(this)
            })
        }
    }
}

function triggerCalculationIfNeededForAnyElement(parentElements) {
    let isCalculationNeededForAnyElement = false;
    parentElements.each(function(index, parentElement) {
        $(parentElement).find("input, select, textarea").each(function(index, field) {
            if ($(field).val()) {
                isCalculationNeededForAnyElement = isCalculationNeeded(field);
                if (isCalculationNeededForAnyElement) {
                    return false;
                }
            }
        });
        if (isCalculationNeededForAnyElement) {
            return false;
        }
    })

    if (isCalculationNeededForAnyElement) {
        enableCalculationOnQueryForm()
    }
}

/**
 * Used for autocomplete select
 * the real input is the hidden input located next to the select. It is a hidden input with additionalQuestionAutocompleteHiddenInput class
 * @param autocompleteSelectElem
 */
function triggerCalculationIfNeededForAutocompleteSelect(autocompleteSelectElem) {
    triggerCalculationIfNeeded($(autocompleteSelectElem).siblings('.additionalQuestionAutocompleteHiddenInput'))
}

function triggerCalculationIfNeeded(input) {
    const triggerCalcValueIsTrue = castStringToBoolean($(TRIGGER_CALCULATION_ELEMENT_ID).val())

    if (!triggerCalcValueIsTrue && isCalculationNeeded(input)) {
        enableCalculationOnQueryForm()
    }
}

function isCalculationNeeded(input) {
    if (isArrayNotEmpty(DO_NOT_TRIGGER_CALC_QUESTIONIDS_LIST)) {
        const questionId = getQuestionIdOrAdditionalQuestionIdFromInputName($(input).attr('name'))

        if (questionId && DO_NOT_TRIGGER_CALC_QUESTIONIDS_LIST.includes(questionId)) {
            return false
        }
    }
    return true
}