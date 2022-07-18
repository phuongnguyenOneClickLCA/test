// NOTE: this javascript is dependent on 360optimi.js and flashTemplates.js
// should we move these to a constants.js ?
const VERIFY_DATASET_LOCK_ICONS = '.verifyDatasetLock'
const NEUTRAL_DATASET_ICONS = '.neutralDataset'
const UNLOCKED_DATASET_ICONS = '.unverifiedDataset'
const VERIFY_SECTION_LINK = '.verifyDatasetsInSectionLink'
const NEUTRALIZE_SECTION_LINK = '.neutralizeDatasetsInSectionLink'
const QUERY_SECTION_TARGET = '.querysection'
const QUERY_QUESTION_TARGET = '.queryquestion'
const QUESTION_INPUT_TARGET_NOT_ROW = '.queryQuestionInput'
const VERIFIABLE_INPUTS = '.verifiableDatasetInput'
const VERIFY_DATASET_ICONS_CONTAINER = '.verifyDatasetIconContainer'
const VERIFY_DATASET_ITEM_IN_DROPDOWN = '.verifyDatasetLi'

/**
 * Activate dataset verification feature.
 * Display all links and icon to verify / neutralize datasets
 * @param $link the link to activate feature
 * @param message localized message informing user the mode is activated
 */
function activateDatasetVerification($link, message) {
    disableButton($link)
    // all links to verify section
    $('.verifyDatasetsInSectionIconContainer').each(function () {
        const $sectionLink = $(this)
        const sectionHasVerifiedDatasets = $sectionLink.attr('data-sectionHasVerifiedDataset') === 'true'
        if (sectionHasVerifiedDatasets) {
            // show neutralize section link
            $sectionLink.find(NEUTRALIZE_SECTION_LINK).fadeIn()
        } else {
            // show verify section link
            $sectionLink.find(VERIFY_SECTION_LINK).fadeIn()
        }
        showAppropriateIconEachDataset($sectionLink)
        const $section = $($sectionLink).closest(QUERY_SECTION_TARGET)
        // verified datasets have displayed verified icon but inactivated functionality, now activate it
        activateOnclick($section.find(VERIFY_DATASET_LOCK_ICONS))
        $section.find(VERIFY_DATASET_ITEM_IN_DROPDOWN).show()
        // set info alert that verification mode is activated
        permInfoAlert(message)
    })
}

/**
 * Check the verify dataset icon container where we store info about whether dataset is verified or unlocked from verified status
 * @param $sectionLink the verify / neutralize section link container
 */
function showAppropriateIconEachDataset($sectionLink) {
    $sectionLink.closest(QUERY_SECTION_TARGET).find(VERIFY_DATASET_ICONS_CONTAINER).each(function () {
        const $dataset = $(this)
        if (!isDatasetUnlockedFromVerifiedStatus($dataset)) {
            if (isDatasetVerified($dataset)) {
                $dataset.find(VERIFY_DATASET_LOCK_ICONS).fadeIn()
            } else {
                $dataset.find(NEUTRAL_DATASET_ICONS).fadeIn()
            }
        }
    })
}

/**
 * Neutralize all datasets in section, display the corresponding verify/lock icon.
 * @param $btn
 */
function neutralizeDatasetsInSection($btn) {
    $($btn).hide()
    showSiblingElement($btn, VERIFY_SECTION_LINK)
    $($btn).closest(QUERY_SECTION_TARGET).find(VERIFY_DATASET_ICONS_CONTAINER).each(function() {
        if (!isDatasetUnlockedFromVerifiedStatus($(this))) {
            $(this).find(VERIFY_DATASET_LOCK_ICONS).click()
        }
    })
    triggerFormChanged()
}

/**
 *  Verify all datasets (except the ones that have been unlocked from verified status) in section, display the corresponding neutralize icon.
 * @param $btn
 */
function verifyDatasetsInSection($btn) {
    $($btn).hide()
    showSiblingElement($btn, NEUTRALIZE_SECTION_LINK)
    $($btn).closest(QUERY_SECTION_TARGET).find(VERIFY_DATASET_ICONS_CONTAINER).each(function() {
        if (!isDatasetUnlockedFromVerifiedStatus($(this))) {
            $(this).find(NEUTRAL_DATASET_ICONS).click()
        }
    })
    triggerFormChanged()
}

/**
 *  Neutralize and unlock a resource based dataset, if resource row is a construction, it would neutralize also the construction's constituents
 *  Logic for resource rows are a lot different than the others
 * @param $btn the neutralize link / button element
 * @param isParentConstructionResource true if row is a construction resource (not constituent)
 * @param uniqueConstructionIdentifier
 */
function neutralizeResourceRow($btn, isParentConstructionResource, uniqueConstructionIdentifier) {
    const $row = $($btn).closest('tr')
    showNeutralIcon($row)
    toggleVerifiedBackground($row, false)
    toggleIsVerifiedInputs($row, false)
    toggleUnlockVerifiedDatasetLink($row, false)
    toggleVerifiedIconInNameCellResourceRow($row,false)
    toggleStatusResourceRowDropDownItems($row, false)
    toggleResourceRowDraggability($row, false)
    if (isParentConstructionResource) {
        doVerificationForConstructionConstituents($($btn).closest('tbody'), uniqueConstructionIdentifier, false)
    }
    triggerFormChanged()
}

/**
 * Verify and lock a resource based dataset, if resource row is a construction, it would verify also the construction's constituents
 * Logic for resource rows are a lot different than the others
 * @param $btn the verify link / button element
 * @param isParentConstructionResource true if row is a construction resource (not constituent)
 * @param uniqueConstructionIdentifier
 */
function verifyAndLockResourceRow($btn, isParentConstructionResource, uniqueConstructionIdentifier, verifiedProductFlag=false) {
    const $row = $($btn).closest('tr')
    showVerifiedIcon($row)
    toggleVerifiedBackground($row, true)
    toggleIsVerifiedInputs($row, true)
    toggleVerifiedIconInNameCellResourceRow($row,true)
    toggleStatusResourceRowDropDownItems($row, true, verifiedProductFlag)
    toggleResourceRowDraggability($row, true)
    if (isParentConstructionResource && uniqueConstructionIdentifier) {
        doVerificationForConstructionConstituents($($btn).closest('tbody'), uniqueConstructionIdentifier, true, verifiedProductFlag)
    }
    triggerFormChanged()
}

/**
 * Neutralize row in questiontable
 * @param $btn
 */
function neutralizeNonResourceRow($btn) {
    if (isActivated($btn)) {
        const $row = $($btn).closest('tr')
        showNeutralIcon($row)
        toggleVerifiedBackground($row, false)
        toggleIsVerifiedInputs($row, false)
        toggleUnlockVerifiedDatasetLink($row, false)
        enableVisuallyElements($row, VERIFIABLE_INPUTS)
        triggerFormChanged()
    }
}

function verifyAndLockNonResourceRow($btn) {
    const $row = $($btn).closest('tr')
    showVerifiedIcon($row)
    toggleVerifiedBackground($row, true)
    toggleIsVerifiedInputs($row, true)
    disableVisuallyElements($row, VERIFIABLE_INPUTS)
    triggerFormChanged()
}

/**
 * Used for icons that are not in the same row as input
 * @param $btn
 * @param removeInputBg we have to add the background to some text input (e.g. textarea), hence this method need to remove it there as well
 */
function neutralizeDatasetNotRow($btn, removeInputBg) {
    if (isActivated($btn)) {
        const $question = $($btn).closest(QUERY_QUESTION_TARGET)
        const $questionInputContainer = $question.find(QUESTION_INPUT_TARGET_NOT_ROW)
        enableVisuallyElements($questionInputContainer, VERIFIABLE_INPUTS)
        showNeutralIcon($question)
        toggleVerifiedBackground($questionInputContainer, false)
        toggleIsVerifiedInputs($questionInputContainer, false)
        toggleUnlockVerifiedDatasetLink($question, false)
        if (removeInputBg) {
            toggleVerifiedBackground($($questionInputContainer).find(VERIFIABLE_INPUTS), false)
        }
        triggerFormChanged()
    }
}

/**
 * Used for icons that are not in the same row as input
 * @param $btn
 * @param addInputBg we have to add the background to some text input (e.g. textarea), hence this method need to add it there as well
 */
function verifyAndLockDatasetNotRow($btn, addInputBg) {
    const $question = $($btn).closest(QUERY_QUESTION_TARGET)
    const $questionInputContainer = $question.find(QUESTION_INPUT_TARGET_NOT_ROW)
    showVerifiedIcon($question)
    toggleVerifiedBackground($questionInputContainer, true)
    toggleIsVerifiedInputs($questionInputContainer, true)
    disableVisuallyElements($questionInputContainer, VERIFIABLE_INPUTS)
    if (addInputBg) {
        toggleVerifiedBackground($($questionInputContainer).find(VERIFIABLE_INPUTS), true)
    }
    triggerFormChanged()
}

/**
 * Update value of hidden input _isVerified_ within boundary element (can be a row).
 * Also add/remove the entityId to hidden input _verifiedFromEntityId_
 * Also update value of hidden input _isUnlockedFromVerifiedStatus_ to false if verifying the dataset
 * Update data-isVerifiedDataset in the verify dataset icons container so activateDatasetVerification show the correct icon.
 * @param $parent a boundary element to run this method (can be a row)
 * @param doVerify
 */
function toggleIsVerifiedInputs($parent, doVerify) {
    if ($parent) {
        const $verifiedInput = $($parent).find('input[data-isVerified="true"]')
        const $verifiedFromEntityIdInput = $($parent).find('input[data-isVerifiedFromEntityId="true"]')
        if (doVerify) {
            const entityId = $verifiedInput.attr('data-verifiedFromEntityId')
            $verifiedInput.val('true')
            $verifiedFromEntityIdInput.val(entityId)
            $($parent).find('input[data-isUnlockedFromVerifiedStatus="true"]').val('false')
            $($parent).find(VERIFY_DATASET_ICONS_CONTAINER).attr('data-isVerifiedDataset', 'true')
        } else {
            $verifiedInput.val('false')
            $verifiedFromEntityIdInput.val('')
            $($parent).find(VERIFY_DATASET_ICONS_CONTAINER).attr('data-isVerifiedDataset', 'false')
        }
    }
}

/**
 * Verified and neutral icons are siblings elements
 * @param $main
 * @param selector
 */
function showSiblingElement($main, selector) {
    if ($main) {
        $($main).siblings(selector).fadeIn()
    }
}

/**
 * Switch verified and neutral icon in resource row name cell
 * @param $row
 * @param showVerifiedIcon
 */
function toggleVerifiedIconInNameCellResourceRow($row, showVerifiedIcon) {
    if ($row) {
        if (showVerifiedIcon) {
            $($row).find('.neutralIconWithoutFunctionality').hide()
            $($row).find('.verifiedIconWithoutFunctionality').fadeIn()
        } else {
            $($row).find('.verifiedIconWithoutFunctionality').hide()
            $($row).find('.neutralIconWithoutFunctionality').fadeIn()
        }
    }
}

/**
 * Show / hide the link to unlock a verified dataset
 * @param $row
 * @param show
 */
function toggleUnlockVerifiedDatasetLink($row, show) {
    if ($row) {
        if (show) {
            $($row).find('.unlockVerifiedDatasetLink').fadeIn()
        } else {
            $($row).find('.unlockVerifiedDatasetLink').fadeOut()
        }
    }
}

/**
 * Add / Remove a class in the resource row name cell to mark which rows are verified and can be dragged. Check draggableRows()
 * @param $row
 * @param add
 */
function toggleResourceRowDraggability($row, add) {
    if ($row) {
        if (add) {
            $($row).find('.resourceNameContainer').removeClass('handle')
        } else {
            $($row).find('.resourceNameContainer').addClass('handle')
        }
    }
}

/**
 * Enable / disable links in dropdown for resource row, update data-datasetIsVerified
 * Items can be disabled by verified or locked status
 * @param $row
 * @param doDisable
 */
function toggleStatusResourceRowDropDownItems($row, doDisable, verifiedProductFlag=false) {
    const $dropdown = $($row).find('.resourceRowEditDropdown')
    const $dropDownItems = $dropdown.find('li')
    //sw-1882
    const copyDatasetId= '#' + $dropDownItems.attr("id")
    if (doDisable) {
        $dropdown.attr('data-datasetIsVerified', 'true')
        // disable all links in dropdown, except Lock row and unlock a verified dataset
        $dropDownItems.addClass('removeClicks').filter('.lockDatasetLi, .unlockVerifiedDatasetLi, .verifyDatasetLi, .removeResourceRowButton').removeClass('removeClicks');
        //sw-1882 : enabled copy option for verified building product
        if (verifiedProductFlag === true) {
            $dropDownItems.filter(copyDatasetId).removeClass('removeClicks');
        }
        $dropDownItems.find('.removeResourceRowButton').addClass('notRemoveable');
    } else {
        $dropdown.attr('data-datasetIsVerified', 'false')
        // only enable dropdown items if dataset is not locked
        if ($dropdown.attr('data-datasetIsLocked') !== 'true'){
            $dropDownItems.removeClass('removeClicks');
            $dropDownItems.find('.removeResourceRowButton').removeClass('notRemoveable');
        }
    }
}

/**
 * Toggle the verify / not verify of the construction constituents
 * @param $questionTBody
 * @param uniqueConstructionIdentifier
 * @param doVerify
 */
function doVerificationForConstructionConstituents($questionTBody, uniqueConstructionIdentifier, doVerify, verifiedProductFlag = false) {
    const targetIcons = doVerify ? NEUTRAL_DATASET_ICONS : VERIFY_DATASET_LOCK_ICONS
    if ($questionTBody && uniqueConstructionIdentifier) {
        $($questionTBody).find(targetIcons).each(function () {
            const uniqueIdentifier = $(this).attr('data-uniqueConstructionIdentifier')
            const isConstituent = $(this).attr('data-isParentConstructionResource') !== 'true' && uniqueIdentifier
            if (isConstituent && uniqueIdentifier === uniqueConstructionIdentifier) {
                // trigger dataset verification for constituents programmatically
                if (doVerify) {
                    verifyAndLockResourceRow(this, false, uniqueConstructionIdentifier, verifiedProductFlag)
                } else {
                    neutralizeResourceRow(this, false, uniqueConstructionIdentifier)
                }
            }
        })
    }
}

/**
 * Unlocking the verified status of construction constituents
 * @param $questionTBody
 * @param uniqueConstructionIdentifier
 */
function unlockVerifiedStatusOfConstructionConstituents($questionTBody, uniqueConstructionIdentifier) {
    if ($questionTBody && uniqueConstructionIdentifier) {
        $($questionTBody).find(VERIFY_DATASET_LOCK_ICONS).each(function () {
            const uniqueIdentifier = $(this).attr('data-uniqueConstructionIdentifier')
            const isConstituent = $(this).attr('data-isParentConstructionResource') !== 'true' && uniqueIdentifier
            if (isConstituent && uniqueIdentifier === uniqueConstructionIdentifier) {
                const $row = $(this).closest('tr')
                toggleUnlockVerifiedBackground($row, true)
                unlockVerifiedStatusInput($row)
                showUnlockedIcon($row)
            }
        })
    }
}

/**
 * Show the unlocked from verified status icon, hide others
 * @param $parent a boundary element to run this method (can be a row)
 */
function showUnlockedIcon($parent) {
    if ($parent) {
        $parent.find(VERIFY_DATASET_LOCK_ICONS).hide()
        $parent.find(NEUTRAL_DATASET_ICONS).hide()
        $parent.find(UNLOCKED_DATASET_ICONS).fadeIn()
    }
}

/**
 * Show the verified icon, hide others
 * @param $parent a boundary element to run this method (can be a row)
 */
function showVerifiedIcon($parent) {
    if ($parent) {
        $parent.find(UNLOCKED_DATASET_ICONS).hide()
        $parent.find(NEUTRAL_DATASET_ICONS).hide()
        $parent.find(VERIFY_DATASET_LOCK_ICONS).fadeIn()
    }
}

/**
 * Show the neutral icon, hide others
 * @param $parent a boundary element to run this method (can be a row)
 */
function showNeutralIcon($parent) {
    if ($parent) {
        $parent.find(UNLOCKED_DATASET_ICONS).hide()
        $parent.find(VERIFY_DATASET_LOCK_ICONS).hide()
        $parent.find(NEUTRAL_DATASET_ICONS).fadeIn()
    }
}

/**
 * Verified icon has onclick (for superuser) to neutralize answer, check if user has activated the feature to run the onclick
 * Use alongside with {@see #activateOnclick}
 * @param $btn
 * @returns {boolean}
 */
function isActivated($btn) {
    return $($btn).attr('data-isActivated') === 'true'
}

/**
 * Enable the onclick of button. To use this, need to use alongside with {@see #isActivated}
 * Also update the hover message
 * @param $btn
 */
function activateOnclick($btn) {
    $($btn).attr('data-isActivated', 'true')
    if (!$($btn).hasClass('verifiedIconWithoutFunctionality')) {
        $($btn).removeClass('defaultCursor')
    }
    const newMsg = $($btn).attr('data-activatedHelp')
    // only change for those that has newMsg
    if (newMsg) {
        switchHoverMessage($btn, newMsg)
    }
}

/**
 *  Add / remove verified background
 *  Also remove background showing that dataset was verified but is not unlocked if we're adding the verified bg
 * @param $target
 * @param add
 */
function toggleVerifiedBackground($target, add) {
    if (add) {
        $($target).removeClass('unlockedVerifiedDatasetStatusBg').addClass('verifiedDatasetBackground')
    } else {
        $($target).removeClass('verifiedDatasetBackground')
    }
}

/**
 * Add / remove background showing that dataset was verified but is not unlocked
 * @param $target
 * @param add
 */
function toggleUnlockVerifiedBackground($target, add) {
    if (add) {
        $($target).removeClass('verifiedDatasetBackground').addClass('unlockedVerifiedDatasetStatusBg')
    } else {
        $($target).removeClass('unlockedVerifiedDatasetStatusBg')
    }
}

/**
 * Update value of hidden input _isUnlockedFromVerifiedStatus_ in $parent to true.
 * @param $parent a boundary element to run this method (can be a row)
 */
function unlockVerifiedStatusInput($parent) {
    if ($parent) {
        $($parent).find('input[data-isUnlockedFromVerifiedStatus="true"]').val('true')
        toggleIsVerifiedInputs($parent, false)
    }
}

/**
 * Check if dataset is verified
 * @param $iconContainer
 * @returns {boolean}
 */
function isDatasetVerified($iconContainer) {
    return $($iconContainer).attr('data-isVerifiedDataset') === 'true'
}

/**
 * Check if dataset is unlocked from verified status
 * @param $iconContainter
 * @returns {boolean}
 */
function isDatasetUnlockedFromVerifiedStatus($iconContainter) {
    return $($iconContainter).attr('data-isUnlockedFromVerifiedStatus') === 'true'
}

/**
 * Update the unlock from verified status to icon container so in case superuser unlock a dataset and then activate
 * verification mode, it would not show any other icon. {@see #showAppropriateIconEachDataset}
 * @param $parent boundary element
 */
function updateUnlockStatusToIconContainer($parent) {
    $($parent).find(VERIFY_DATASET_ICONS_CONTAINER).attr('data-isUnlockedFromVerifiedStatus', 'true')
}

/**
 * Unlock the verified status of dataset
 * @param $btn
 * @param isRow
 * @param unlockWarning
 * @param confirmText
 * @param cancelText
 * @param isResourceRow
 * @param uniqueConstructionIdentifier for unlocking verified status of construction constituents
 * @param addInputBg we have to add the background to some text input (e.g. textarea), hence need to add bg there as well
 */
function unlockVerifiedDataset($btn, isRow, unlockWarning, confirmText, cancelText, addInputBg, isResourceRow, uniqueConstructionIdentifier) {
    Swal.fire({
        icon: 'warning',
        text: unlockWarning,
        showCancelButton: true,
        confirmButtonText: confirmText,
        cancelButtonText: cancelText
    }).then(function (result) {
        if (result.isConfirmed) {
            const $parent = isRow ? $($btn).closest('tr') : $($btn).closest(QUERY_QUESTION_TARGET).find(QUESTION_INPUT_TARGET_NOT_ROW)
            const $grandParent = isRow ? $parent : $($btn).closest(QUERY_QUESTION_TARGET)
            toggleUnlockVerifiedBackground($parent, true)
            unlockVerifiedStatusInput($parent)
            toggleUnlockVerifiedDatasetLink($grandParent, false)
            showUnlockedIcon($grandParent)
            enableVisuallyElements($parent, VERIFIABLE_INPUTS)
            updateUnlockStatusToIconContainer($parent)

            if (addInputBg) {
                toggleUnlockVerifiedBackground($($parent).find(VERIFIABLE_INPUTS), true)
            }

            /* commented for now. Management decided not to need this for resource row
            if (isResourceRow) {
                toggleStatusResourceRowDropDownItems($parent, false)
                unlockVerifiedStatusOfConstructionConstituents($($btn).closest('tbody'), uniqueConstructionIdentifier)
            }
             */
            triggerFormChanged()
            $($btn).remove()
        }
    })
}

/**
 * Add corresponding background to table rows in detailed report view
 * if the row is from verified or unlockedFromVerifiedStatus dataset.
 */
function highlightRowsInResultsDetailedView() {
    $(VERIFY_DATASET_LOCK_ICONS).each(function() {
        // add verified bg
        toggleVerifiedBackground($(this).closest('tr'), true)
    })

    $(NEUTRAL_DATASET_ICONS).each(function() {
        // add unlocked bg
        toggleUnlockVerifiedBackground($(this).closest('tr'), true)
    })
}

/**
 * Replace the resourceId in the inputs name for dataset verification.
 * @param rowId
 * @param resourceId
 */
function updateResourceIdForDatasetVerificationInRow(rowId, resourceId) {
    const $row = $('#' + rowId)
    if ($row) {
        const searchTargets = ['_isVerified_', '_isUnlockedFromVerifiedStatus_', '_verifiedFromEntityId_']

        $($row).find('input[type="hidden"]').each(function() {
            const $input = $(this)
            const nameAttr = $input.attr('name')
            searchTargets.forEach(function(target) {
                if (nameAttr.includes(target)) {
                    const prefix = nameAttr.split(target)[0]
                    const newName = prefix + target + '.' + resourceId
                    $($input).attr('name', newName)
                }
            })
        })
    }
}