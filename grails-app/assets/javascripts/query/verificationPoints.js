// this module is dependent on 360optimi.js

function fetchVerificationPointsModal(entityId, childEntityId, indicatorId, queryId, jumpToPoint, loadingText) {
    Swal.fire({
        title: jumpToPoint,
        text: loadingText + '...',
        onOpen: function () {
            Swal.showLoading()
        },
        customClass: 'swal-wide'
    })
    let queryParams = ''
    queryParams += '&entityId=' + entityId
    queryParams += '&childEntityId=' + childEntityId
    queryParams += '&indicatorId=' + indicatorId
    queryParams += '&queryId=' + queryId

    $.ajax({
        type: 'POST',
        data: queryParams,
        url: '/app/sec/query/getVerificationPointsModal',
        async: true,
        success: function (data) {
            if (data.output) {
                if (data.output === 'error') {
                    somethingWrongErrorSwal()
                } else {
                    Swal.fire({
                        title: jumpToPoint,
                        html: data.output,
                        // footer: data.footer,
                        customClass: {
                            container: 'swal-groupEdit', // this class set z-index of swal to 900
                            popup: 'swal-wide'
                        },
                        showCancelButton: false,
                        showConfirmButton: false,
                        closeOnCancel: true
                    })
                    $('.vPointJumpSelect').select2({
                        matcher: matchStart
                    })
                    let optgroupState = {}
                    prettifySelect2Optgroup('.vPointJumpSelect', optgroupState, 'black')
                    keepPopoverOnHover('.triggerPopover')
                }
            }
        },
        error: function (data) {
            somethingWrongErrorSwal()
        }
    })
}

function showVerificationPoints(doHighlight = true) {
    const vPointContainer = $('.VPointContainer')
    if (vPointContainer.length > 0) {
        vPointContainer.removeClass('hide')
        if (doHighlight) {
            highLightElementTimeOut(vPointContainer)
        }
    }
}

function getVpointOption(vPointSelect) {
    return $(vPointSelect).find('option[value="' + $(vPointSelect).val() + '"]')
}

function getVpointJumpUrl(vPointSelect) {
    return getVpointOption(vPointSelect).attr('data-url')
}

function getVpointQueryId(vPointSelect) {
    return getVpointOption(vPointSelect).attr('data-queryid')
}

function getVpointDisplayText(vPointSelect) {
    return getVpointOption(vPointSelect).text()
}

function openVpointNewTab(selectSomethingMsg) {
    const url = getVpointJumpUrl('#vPointJumpSelect')
    if (url) {
        window.open(url, '_blank');
    } else {
        Swal.showValidationMessage(selectSomethingMsg)
    }

}

function openVpointThisTab(currentQueryId, selectSomethingMsg){
    const url = getVpointJumpUrl('#vPointJumpSelect')
    if (url) {
        const queryIdOfSelected = getVpointQueryId('#vPointJumpSelect')
        if (currentQueryId && queryIdOfSelected === currentQueryId) {
            // handle smooth slide within current tab
            let regex = /&scrollToVP=(\w*-*)*/
            let target = regex.exec(url)[0].replace('&scrollToVP=', '#')
            Swal.close()
            scrollToElement(target)
            setTimeout(function() {
                showVerificationPoints(false)
                highlightQuestionOrSection(target)
            }, 1500)
        } else {
            window.location.href = url
        }
    } else {
        Swal.showValidationMessage(selectSomethingMsg)
    }

}

function handleVPointChange(vPointSelect) {
    const url = getVpointJumpUrl(vPointSelect)
    const displayText = getVpointDisplayText(vPointSelect)
    const jumpLink = $('#jumpToVpointLink')
    const jumpLinkContainer = $(jumpLink).parent()
    if (url) {
        jumpLink.text(displayText)
        jumpLink.attr('href', url)
        if (!isVisible(jumpLinkContainer)) {
            jumpLinkContainer.removeClass('hide').fadeIn()
        }
        // reset the validation msg in case it is shown
        Swal.resetValidationMessage();
    } else {
        if (isVisible(jumpLinkContainer)) {
            jumpLinkContainer.fadeOut()
            jumpLink.attr('href', '')
        }
    }
}

/**
 * scroll to verification point and hight light the whole section / question
 * @param target
 */
function scrollToVerificationPoint(target) {
    const scrollRun = scrollToElement(target)
    if (scrollRun) {
        setTimeout(function() {
            highlightQuestionOrSection(target)
        }, 1500)
    } else {
        // highlight right away if did not scroll
        highlightQuestionOrSection(target)
    }
}