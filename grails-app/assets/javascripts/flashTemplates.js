function setFlashForAjaxCall(data) {
    try {
        if (data.flashObjectForAjax) {
            let runFadeToggle = false
            for (const key in data.flashObjectForAjax) {
                if (key) {
                    const message = data.flashObjectForAjax[key]
                    switch (key) {
                        case 'fadeSaveAlert':
                            fadeSaveAlert(message)
                            runFadeToggle = true
                            break;
                        case 'fadeSuccessAlert':
                            fadeSuccessAlert(message)
                            runFadeToggle = true
                            break;
                        case 'fadeInfoAlert':
                            fadeInfoAlert(message)
                            runFadeToggle = true
                            break;
                        case 'fadeWarningAlert':
                            fadeWarningAlert(message)
                            runFadeToggle = true
                            break;
                        case 'fadeErrorAlert':
                            fadeErrorAlert(message)
                            runFadeToggle = true
                            break;
                        case 'saveAlert':
                            saveAlert(message)
                            break;
                        case 'successAlert':
                            successAlert(message)
                            break;
                        case 'infoAlert':
                            infoAlert(message)
                            break;
                        case 'warningAlert':
                            warningAlert(message)
                            break;
                        case 'errorAlert':
                            errorAlert(message)
                            break;
                        case 'permSaveAlert':
                            permSaveAlert(message)
                            break;
                        case 'permSuccessAlert':
                            permSuccessAlert(message)
                            break;
                        case 'permInfoAlert':
                            permInfoAlert(message)
                            break;
                        case 'permWarningAlert':
                            permWarningAlert(message)
                            break;
                        case 'permErrorAlert':
                            permErrorAlert(message)
                            break;
                    }
                }
            }
            if (runFadeToggle) {
                fadetoggleFlashAlert()
            }
        }
    } catch (e) {
        console.log('Error in setFlashForAjaxCall', e)
    }
}

function fadetoggleFlashAlert() {
    $('.fadetoggle').fadeIn(function () {
        let delaytime = $(this).attr('data-fadetoggle-delaytime');
        if (!delaytime) {
            delaytime = 3000
        }
        $(this).delay(delaytime).fadeOut(function () {
            $(this).alert('close')
        })
    });
}

function closeFlashAlert(alertName) {
    const containerSelectorList = ['#messageContent', '.alertMessageContent'];
    containerSelectorList.forEach( function(containerSelector) {
        $(containerSelector + ' #flash-' + alertName).alert('close');
    });
}

function appendToExistingAlert(message, alertName, containerSelector) {
    let appended = false
    const $existing = $(containerSelector + ' #flash-' + alertName)
    if ($existing.length) {
        $existing.each(function(){
            if (!appended) {
                $(this).siblings('strong').append('<br>' + message)
                if ($(this).is(':hidden')) {
                    $(this).fadeIn()
                }
                appended = true
            }
        })
    }
    return appended
}

function setFadeFlashAlert(message, alertClass, iconClass, alertName) {

    const containerSelectorList = ['#messageContent', '.alertMessageContent'];
    containerSelectorList.forEach( function(containerSelector) {
        if (!appendToExistingAlert(message, alertName, containerSelector)) {
            $(containerSelector).append(
                '<div id="flash-' + alertName + '" class="alert ' + alertClass + ' fadetoggle hide-on-print" data-fadetoggle-delaytime="7000">' +
                '   <i class="fas ' + iconClass + ' pull-left" style="font-size: large; margin-right: 8px;"></i>' +
                '   <i data-dismiss="alert" class="fas fa-circle-notch fa-spin close" style="top: 0px;"></i>' +
                '   <strong>' + message + '</strong>' +
                '</div>'
            )
        }
    });
}

function setFlashAlert(message, alertClass, iconClass, alertName) {

    const containerSelectorList = ['#messageContent', '.alertMessageContent'];
    containerSelectorList.forEach( function(containerSelector) {
        if (!appendToExistingAlert(message, alertName, containerSelector)) {
        $(containerSelector).append(
                '<div id="flash-' + alertName + '" class="alert ' + alertClass + '  hide-on-print">' +
                '   <i class="fas ' + iconClass + ' pull-left" style="font-size: large; margin-right: 8px;"></i>' +
                '   <button data-dismiss="alert" class="close" style="top: 0px;" type="button">×</button>' +
                '   <strong>' + message + '</strong>' +
                '</div>'
            )
        }
    });
}

function setPermFlashAlert(message, alertClass, iconClass, alertName) {

    const containerSelectorList = ['#messageContent', '.alertMessageContent'];
    containerSelectorList.forEach( function(containerSelector) {
        if (!appendToExistingAlert(message, alertName, containerSelector)) {
            $(containerSelector).append(
                '<div id="flash-' + alertName + '" class="alert ' + alertClass + ' hide-on-print">' +
                '   <i class="fas ' + iconClass + ' pull-left" style="font-size: large; margin-right: 8px;"></i>' +
                '   <strong>' + message + '</strong>' +
                '</div>'
            )
        }
    });
}

function fadeSaveAlert(message) {
    setFadeFlashAlert(message, 'alert-success', 'fa-save', 'fadeSaveAlert')
}

function fadeSuccessAlert(message) {
    setFadeFlashAlert(message, 'alert-success', 'fa-thumbs-up', 'fadeSuccessAlert')
}

function fadeInfoAlert(message) {
    setFadeFlashAlert(message, 'alert-info', 'fa-info', 'fadeInfoAlert')
}

function fadeWarningAlert(message) {
    setFadeFlashAlert(message, 'alert-warning', 'fa-exclamation', 'fadeWarningAlert')
}

function fadeErrorAlert(message) {
    setFadeFlashAlert(message, 'alert-error', 'fa-times', 'fadeErrorAlert')
}

function saveAlert(message) {
    setFlashAlert(message, 'alert-success', 'fa-save', 'saveAlert')
}

function successAlert(message) {
    setFlashAlert(message, 'alert-success', 'fa-thumbs-up', 'successAlert')
}

function infoAlert(message) {
    setFlashAlert(message, 'alert-info', 'fa-info', 'infoAlert')
}

function warningAlert(message) {
    setFlashAlert(message, 'alert-warning', 'fa-exclamation', 'warningAlert')
}

function errorAlert(message) {
    setFlashAlert(message, 'alert-error', 'fa-times', 'errorAlert')
}

function permSaveAlert(message) {
    setPermFlashAlert(message, 'alert-success', 'fa-save', 'permSaveAlert')
}

function permSuccessAlert(message) {
    setPermFlashAlert(message, 'alert-success', 'fa-thumbs-up', 'permSuccessAlert')
}

function permInfoAlert(message) {
    setPermFlashAlert(message, 'alert-info', 'fa-info', 'permInfoAlert')
}

function permWarningAlert(message) {
    setPermFlashAlert(message, 'alert-warning', 'fa-exclamation', 'permWarningAlert')
}

function permErrorAlert(message) {
    setPermFlashAlert(message, 'alert-error', 'fa-times', 'permErrorAlert')
}