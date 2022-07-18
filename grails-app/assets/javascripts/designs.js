/**
 *
 * @param originalEntityId - the design on which user clicked to find average design
 * @param formId
 */
function averageDesignModal(originalEntityId, formId) {
    var json = {
        originalEntityId: originalEntityId,
        formId: formId
    };
    var url = "/app/sec/util/averageDesignModal";
    ajaxCallForAverageDesignModal(url, json);
}

/**
 *
 * @param url
 * @param json
 * @param element
 */
function ajaxCallForAverageDesignModal(url, json, element) {
    if (element) {
        element.style['pointer-events'] = 'none';
    }

    var successCallback = function successCallback(data) {
        $("#averageDesignModalDiv").empty().append(data.output.template).modal();

        if (element) {
            element.style['pointer-events'] = 'auto';
        }
    };

    var errorCallback = function errorCallback(data) {
        console.log("Error response");
        console.log(data);

        if (element) {
            element.style['pointer-events'] = 'auto';
        }
    };

    ajaxForJsonMain(url, json, successCallback, errorCallback);
}

/**
 *  To get the sum of share and show it in the modal , if share != 100 percentage , then shows error message and
 *  disables the submit button
 */
function designTotalUpdate() {
    let totalPoolElement = $('#totalSplitPool');
    let error_designtotal = $('#error_designtotal')
    let error_designtotal_below100 = $('#error_designtotalbelow')
    let createAverageDesign = $('#createAverageDesign')
    let sumOfShares
    var arrayOfSharesAgain = [];
    $("#originResource :input[type=text]").each(function () {
        let input = parseFloat($(this).val());
        arrayOfSharesAgain.push(input);
    });
    sumOfShares = arrayOfSharesAgain.reduce(getSum);
    $(totalPoolElement).empty().append(sumOfShares);
    if (sumOfShares > 100) {
        totalPoolElement.parent().css('color', 'red');
        $(error_designtotal_below100).hide()
        $(error_designtotal).show()
        $(createAverageDesign).addClass('removeClicksNoOverriding')
    } else if (sumOfShares < 100 || isNaN(sumOfShares)) {
        totalPoolElement.parent().css('color', 'red');
        $(error_designtotal).hide()
        $(error_designtotal_below100).show()
        $(createAverageDesign).addClass('removeClicksNoOverriding')
    } else if (sumOfShares == 100) {
        totalPoolElement.parent().css('color', 'black');
        $(error_designtotal).hide()
        $(error_designtotal_below100).hide()
        $(createAverageDesign).removeClass('removeClicksNoOverriding')
    }
}