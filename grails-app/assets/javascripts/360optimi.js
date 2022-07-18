//Global variable that you can push any ajaxRequest into and do with it as you please.
var xhrPool = [];
var autocompleteZIndex = 1051;

Array.prototype.contains = function (element) {
    return this.indexOf(element) > -1;
};

// set the flash alerts for all ajax calls success
$(document).ajaxSuccess(function (event, jqXHR, ajaxOptions, data) {
    setFlashForAjaxCall(data)
})

// set the flash alerts for all ajax calls error
$(document).ajaxError(function (event, jqXHR, ajaxOptions, data) {
    setFlashForAjaxCall(data)
})

function goBack() {
    window.history.back()
}

function disableLink(linkObject) {
    $(linkObject).on('click', function (event) {
        if ($(linkObject).hasClass("disabled")) {
            event.preventDefault();
        }
        $(linkObject).addClass("disabled");
    });
}

function fullScreenPopup(theURL, showWarning) {
    var width = screen.width.toString();
    var height = screen.height.toString();

    if (showWarning) {
        var win = window.open("", 'breakdown', 'width=' + width + 'px, height=' + height + 'px,toolbar=0,menubar=0,location=0,status=0,scrollbars=1,resizable=1,left=100,top=100');
        win.document.write("<div id='lcaCheckerAlert' style='padding: 8px 35px 8px 14px;\n" +
            "    text-shadow: 0 1px 0 rgba(255, 255, 255, 0.5);\n" +
            "    background-color: #fcf8e3;\n" +
            "    border: 1px solid #fbeed5;\n" +
            "    -webkit-border-radius: 4px;\n" +
            "    -moz-border-radius: 4px;\n" +
            "    border-radius: 4px;\n" +
            "    -webkit-border-radius: 4px;\n" +
            "    -moz-border-radius: 4px;\n" +
            "    border-radius: 4px;\n" +
            "    font-family: \"Helvetica Neue\", Helvetica, Arial, sans-serif;\n" +
            "    color: #c09853;\n" +
            "    max-width:1200px;\n" +
            "    margin:auto;\n" +
            "    margin-bottom:-150px;'>\n" +
            "  <strong>" + showWarning + "</strong>\n" +
            "</div>");
        win.location = theURL;
    } else {
        window.open(theURL, 'breakdown', 'width=' + width + 'px, height=' + height + 'px,toolbar=0,menubar=0,location=0,status=0,scrollbars=1,resizable=1,left=100,top=100');
    }

}

function taskPopup(theURL) {
    window.open(theURL, 'breakdown', 'width=800px, height=600px,toolbar=0,menubar=0,location=0,status=0,scrollbars=1,resizable=1,left=0,top=0')
}

function selectShowHideDiv(obj, questionId) {
    var no = obj.options[obj.selectedIndex].index;
    var count = obj.options.length;
    var elementId;
    var element;

    for (i = 0; i < count; i++) {
        elementId = questionId + i;
        element = document.getElementById(elementId);

        if (element) {
            element.style.display = 'none';
            var collection = element.getElementsByTagName("input");

            for (var x = 0; x < collection.length; x++) {
                if (collection[x].type.toLowerCase() == "checkbox" || collection[x].type.toLowerCase() == "radio") {
                    collection[x].checked = false;
                    collection[x].disabled = true;
                } else if (collection[x].type.toLowerCase() == "text" || collection[x].type.toLowerCase() == "textarea") {
                    collection[x].disabled = true;
                    collection[x].value = "";
                }
            }
            collection = element.getElementsByTagName("select");

            for (var x = 0; x < collection.length; x++) {
                collection[x].disabled = true;
            }
        }
    }
    elementId = questionId + no;
    element = document.getElementById(elementId);

    if (element) {
        element.style.display = 'block';
        collection = element.getElementsByTagName("input");

        for (var x = 0; x < collection.length; x++) {
            if (collection[x].type.toLowerCase() == "checkbox" || collection[x].type.toLowerCase() == "radio" ||
                collection[x].type.toLowerCase() == "text" || collection[x].type.toLowerCase() == "textarea") {
                collection[x].disabled = false;
            }
        }
        collection = element.getElementsByTagName("select");

        for (var x = 0; x < collection.length; x++) {
            collection[x].disabled = false;
        }
    }

    //  12544
    if ("calculationDefaults.lcaModel" === questionId) {
        var val = $(obj).val();

        $.each($("[data-compatiblelcamodels!=''][data-compatiblelcamodels]"), function () {
            var compatibleModel = $(this).attr("data-compatiblelcamodels").split(',');

            if (compatibleModel && compatibleModel.indexOf(val) > -1) {
                $(this).removeClass("removeClicks")
            } else {
                if (!$(this).hasClass("removeClicks")) {
                    $(this).addClass("removeClicks")
                }
            }
        });
    } else if ("calculationDefaults.localCompensationMethodVersion" === questionId) {
        if ("00" === $(obj).val()) {
            $("#targetCountryquestion").parent().parent().addClass("removeClicks");
        } else {
            $("#targetCountryquestion").parent().parent().removeClass("removeClicks");
        }
    }
}

function showHideDiv(divId) {
    // on the checkbox
    var element = document.getElementById(divId);

    if (element) {
        if (element.style.display == 'none') { // if it is checked, make it
            // visible, if not, hide it
            element.style.display = 'block';
            element.style.float = 'left';
        } else {
            element.style.display = 'none';
        }
    }
}

function showHidePortfolioIndicator(divId, showTable) {
    // on the checkbox
    var element = document.getElementById(divId);

    if (element) {
        var chooseId = '#choose' + divId;
        if (element.style.display === 'none') { // if it is checked, make it
            // visible, if not, hide it
            element.style.display = 'block';
            $(chooseId).addClass('active');

            if (showTable === "true") {
                $('#table' + divId).show();
                $('#graph' + divId).hide();
                $('#' + divId + 'graph').removeClass('active');
                $('#' + divId + 'table').addClass('active');
                $('.additionalRuleHeader').removeClass('hidden');
                $('#' + divId + 'ribastageHeader').removeClass('hidden');
            }
        } else {
            element.style.display = 'none';
            $(chooseId).removeClass('active');
        }
    }
}


function showHideClass(className, event) {
    // on the checkbox
    var element = document.getElementsByClassName(className);

    if (element) {
        for (i = 0; i < element.length; i++) {
            if (element[i].style.display == 'none') { // if it is checked, make it
                // visible, if not, hide it
                element[i].style.display = '';
            } else {
                element[i].style.display = 'none';
            }
        }
        if (event) {
            event.stopPropagation()
        }
    }
}

function toggleSmoothtip(elementId) {
    var tooltipContainer = $('#' + elementId);
    if (tooltipContainer.length) {
        tooltipContainer.toggleClass('smoothTip')
    }
}

function showHideDivAndDisable(divId, inputName) {
    var element = document.getElementById(divId);

    if (element) {
        if (element.style.display == 'none') { // if it is checked, make it
            // visible, if not, hide it
            element.style.display = 'block';
            element.style.float = 'left';
            $("input[name='" + inputName + "']").prop('disabled', false);
        } else {
            element.style.display = 'none';
            $("input[name='" + inputName + "']").attr("disabled", "disabled");
        }
    }
}

function showHiddenPortfolios(textIdToHide, textIdToShow, rowClassToShow) {
    textIdToHide = '#' + textIdToHide;
    textIdToShow = '#' + textIdToShow;
    rowClassToShow = '.' + rowClassToShow;
    $(rowClassToShow).css('display', '');
    $(textIdToHide).hide();
    $(textIdToShow).show();
}

function hideHiddenPortfolios(textIdToHide, textIdToShow, rowClassToShow) {
    textIdToHide = '#' + textIdToHide;
    textIdToShow = '#' + textIdToShow;
    rowClassToShow = '.' + rowClassToShow;
    $(rowClassToShow).css('display', 'none');
    $(textIdToHide).hide();
    $(textIdToShow).show();
}

function showHidePasswordFields(divId) {
    // on the checkbox
    var element = document.getElementById(divId);

    if (element.style.display == 'none') { // if it is checked, make it
        // visible, if not, hide it
        element.style.display = 'block';
        $("#password").removeAttr('disabled');
        $("#passwordAgain").removeAttr('disabled');
    } else {
        element.style.display = 'none';
        $("#password").attr('disabled', 'disabled');
        $("#passwordAgain").attr('disabled', 'disabled');
    }
}

function show(id) {
    var element = document.getElementById(id);
    element.style.display = 'block';
}

function hide(id) {
    var element = document.getElementById(id);
    element.style.display = 'none';
}

function togglePlusMinus(linkId, divClass, changeHtml) {
    var link = document.getElementById(linkId);
    // link.preventDefault();
    divClass = '.' + divClass;
    //             element.style.display = 'none';
    if ($(divClass).css("display") == "none") {
        $(divClass).css("display", "")
        link.innerHTML = '<strong>-</strong>';
    } else {
        $(divClass).css("display", "none");
        link.innerHTML = '<strong>+</strong>';
    }
}

function showHideChart(divId1, divId2) { // This gets executed when the user clicks
    document.getElementById(divId1).style.visibility = "visible";
    document.getElementById(divId2).style.visibility = "hidden";
}

function toggleVisiblity(elementId) {
    var element = document.getElementById(elementId);

    if (element.style.visibility == "visible") {
        element.style.visibility = "hidden";
    } else {
        element.style.visibility = "visible";
    }
}

function toggleDisplay(elementId) {
    var element = document.getElementById(elementId);

    if (element.style.display == "block") {
        element.style.display = "none";
    } else {
        element.style.display = "block";
    }
}

function hideCompletely(elementId) {
    var element = document.getElementById(elementId);

    if (element.style.visibility == "visible") {
        element.style.visibility = "hidden";
    }
}

function masterAlert(elementId, alertText) {
    var checkboxElement = document.getElementById(elementId)

    if (checkboxElement.checked) {
        alert(alertText);
    }
}

function modalConfirm(sender, dynamicValue) {
    let href = $(sender).attr('href');
    let true_str = $(sender).attr('data-truestr');
    let false_str = $(sender).attr('data-falsestr');
    let title_str = $(sender).attr('data-titlestr');
    let question_str = $(sender).attr('data-questionstr');
    let question2_str = $(sender).attr('data-questionstr2');

    let dynamicDataValue = $('#' + dynamicValue).val();

    if (dynamicDataValue) {
        href = href + "?" + dynamicValue + "=" + dynamicDataValue;
    }

    Swal.fire({
        title: title_str,
        html: question_str,
        icon: 'warning',
        allowOutsideClick: false,
        showCancelButton: true,
        confirmButtonColor: '#da4f49',
        confirmButtonText: true_str,
        cancelButtonText: false_str,

    }).then((result) => {
        if (result.isConfirmed && question2_str != null && question2_str !== '') {
            Swal.fire({
                title: title_str,
                html: question2_str,
                icon: 'error',
                allowOutsideClick: false,
                showCancelButton: true,
                confirmButtonColor: '#da4f49',
                confirmButtonText: true_str,
                cancelButtonText: false_str,
            }).then((result2) => {
                if (result2.isConfirmed){
                    window.location.href = href
                }
            })
        } else if (result.isConfirmed){
            window.location.href = href
        }
    })

    return false;
}

/* Comment out functions since it's not used anywhere. Can remove after few months. Hung 04.2021
NOTE: enableSubmit has a duplicated function
function enableSubmit(checkboxId, submitId) {
    var submit = document.getElementById(submitId);
    var checkbox = document.getElementById(checkboxId);
    submit.disabled = true;

    if (checkbox.checked == true) {
        submit.disabled = false;
    }

    if (checkbox.checked == false) {
        submit.enabled = false;
    }
}

function enableDisableAddButton(buttonId, select) {
    var button = document.getElementById(buttonId);

    if (button && select) {
        var value = select.options[select.selectedIndex].value;

        if (value) {
            button.disabled = false;
        } else {
            button.disabled = true;
        }
    }
}
*/
function submitForm(formId) {
    $("#" + formId).trigger('submit');
}

if (typeof removeResource != 'function') {
    function removeResource(id, datasetId, entityId, indicatorId, uniqueConstructionIdentifier, sourceListingId) {
        closeSourceListing(sourceListingId);
        var trId = '#' + id;
        $('input[type=submit]').prop('disabled', false);
        var trIdInput = trId + " :input";
        var subResourcesOfConstruction = $('*[data-uniqueConstructionIdentifier="' + uniqueConstructionIdentifier + '"]');
        var queryString = 'datasetId=' + datasetId + '&entityId=' + entityId + '&indicatorId=' + indicatorId;
        if ($(trId).attr('isacopy')) {
            queryString = 'datasetId=' + $(trId).find('[data-manualIdContainer="true"]').val() + '&entityId=' + entityId + '&indicatorId=' + indicatorId;
        }

        $(trIdInput).each(function () {
            if (!"userGivenUnit" === this.className) {
                $(this).trigger('change');
            }
        });
        $(trId).remove();
        if (uniqueConstructionIdentifier) {
            $(subResourcesOfConstruction).each(function () {
                $(this).parent().parent().remove()
            });
        }
        var currentResources = $("#currentResources");
        var totalResources = $("#totalResources");

        if (currentResources) {
            currentResources.html($('.resourceTable > tbody > tr').length);
        }
        if (totalResources) {
            totalResources.html(parseInt($("#totalResources").html()) - 1);
        }
        triggerFormChanged();
        switchOnTriggerCalculationFlag();
    }
}

if (typeof removeBorder != 'function') {
    function removeBorder(object, inputWidth, inputHeight) {
        if (object) {
            $(object).removeAttr("style");
            $(object).prop('required', false);
            $(object).removeClass("redBorder");

            if (inputWidth) {
                inputWidth = inputWidth + 'px';
                $(object).css('width', inputWidth);
            }

            if (inputHeight) {
                inputHeight = inputHeight + 'px';
                $(object).css('height', inputHeight);
            }
        }
    }
}

function showOrHideVerificationNumber(object, sectionId, iniesIdentifier ) {
    var divId="toggle".concat(sectionId)
    if(object.value === iniesIdentifier){
        var status = document.getElementById(divId);
        status.classList.remove("hidden")
    }
    else {
        var status = document.getElementById(divId);
        status.classList.add("hidden")
    }
}

if (typeof changeInputNameAndUnit != 'function') {
    function changeInputNameAndUnit(trId, selectId, textId, namePrefix, additionalQuestionNames) {
        var selectInput = "#" + selectId;
        var textInput = '#' + textId;
        var resourceId = $(selectInput).val().split(" ")[0];
        var textName = namePrefix + '.' + resourceId;
        $(textInput).attr("name", textName);

        // First removing existing unit
        var tr = '#' + trId;
        $(tr).find("span").remove();
        var unit;

        if ($(selectInput).val().split(" ")[1]) {
            unit = $(selectInput).val().split(" ")[1]
            $(textInput).after(' <span class=\"add-on\">' + unit + '</span>');
        }

        // Renaming additonalQuestion inputs
        if (additionalQuestionNames) {
            var theNames = additionalQuestionNames.split(",");

            for (var i = 0; i < theNames.length; i++) {
                var currentName = theNames[i];

                if (currentName) {
                    var nameParts = currentName.split(".");

                    if (nameParts.length == 3) {
                        var ind = currentName.lastIndexOf(".");
                        currentName = currentName.substring(0, ind);
                    }
                    var additionalFieldId = '#' + currentName.replace(".", "\\.");
                    var newName = currentName + '.' + resourceId;
                    $(tr).find(additionalFieldId).attr("name", newName);
                }
            }
        }
    }
}

function hideElementByClassOrId(className, id = null) {
    let selector = ''
    if (className) {
        selector = '.' + className;
    }
    if (id) {
        selector = '#' + id
    }
    if (selector) {
        $(selector).hide();
    }
}

function showElementByClassOrId(className, id = null) {
    let selector = ''
    if (className) {
        selector = '.' + className;
    }
    if (id) {
        selector = '#' + id
    }
    if (selector) {
        $(selector).show();
    }
}

function toggleElement(selector, duration = 150) {
    if (selector) {
        $(selector).toggle({duration: duration});
    }
}

function toggleContentOfElement(selector, text1, text2, duration = 150) {
    if (selector) {
        const $target = $(selector)
        const currentText = $target.text()
        const swapText = currentText === text1 ? text2 : text1
        $target.fadeOut(duration, function() {
            $(this).empty().text(swapText)
            $(this).fadeIn(duration)
        });
    }
}

function removeElementByClass(className) {
    if (className) {
        className = '.' + className;
        $(className).remove();
    }
}

function removeElementByClassWithConfirm(element, className) {
    if ($('#modalconfirm').length < 1) {
        var modalContStr = '<div class="modal hide fade" id="modalconfirm"></div>';
        $("body").append(modalContStr);
    }
    var onclick = "removeElementByClass('" + className + "');";
    var title_str = $(element).attr('data-titlestr');
    var true_str = $(element).attr('data-truestr');
    var false_str = $(element).attr('data-falsestr');
    var question_str = $(element).attr('data-questionstr');

    var $modalWin = $('#modalconfirm');

    var modalInnerStr = '<div class="modal-header"><button type="button" class="close" data-dismiss="modal">&times;</button>';
    modalInnerStr += '<h2>' + title_str + '</h2>';
    modalInnerStr += '</div>';
    modalInnerStr += '<div class="modal-body"><p>' + question_str
        + '</p></div>';
    modalInnerStr += '<div class="modal-footer"><a href="javascript: return;" onclick="'
        + onclick
        + '" class="btn btn-danger" data-dismiss="modal">'
        + true_str
        + '</a> <a href="javascript: return;" class="btn" data-dismiss="modal">'
        + false_str + '</a></div>';

    $modalWin.html(modalInnerStr);
    $modalWin.modal();
    return false;
}

function removeClassFromParent(object, className) {
    var valueOfSelect = $(object).val();

    if (valueOfSelect) {
        var tr = $(object).closest('tr');
        $(tr).removeClass(className);
    }
}


function numericInputCheck() {
    $('.numeric').on('input propertychange', function () {
        var start = this.selectionStart;
        end = this.selectionEnd;
        var val = $(this).val();
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
            var dotsAndCommas = ((val.match(/\./g) || []).length + (val.match(/,/g) || []).length);

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
}

function displayTrClass(trClass, buttonId, hideMessage, showMessage) {
    var button = '#' + buttonId;
    var value = hideMessage;
    $(button).attr("value", value);
    $(button).attr("onclick", "hideTrClass('" + trClass + "', '" + buttonId + "', '" + showMessage + "', '" + hideMessage + "');");
    var classToShow = '.' + trClass;
    $(classToShow).css('display', 'table-row');
}

function hideTrClass(trClass, buttonId, showMessage, hideMessage) {
    var button = '#' + buttonId;
    var value = showMessage;
    $(button).attr("value", value);
    $(button).attr("onclick", "displayTrClass('" + trClass + "', '" + buttonId + "', '" + hideMessage + "', '" + showMessage + "');");
    var classToHide = '.' + trClass;
    $(classToHide).css('display', 'none');
}

function showClass(trClass, buttonId, hideMessage, showMessage) {
    var button = '#' + buttonId;
    var value = hideMessage;
    $(button).attr("value", value);
    $(button).attr("onclick", "hideClass('" + trClass + "', '" + buttonId + "', '" + showMessage + "', '" + hideMessage + "');");
    var classToShow = '.' + trClass;
    $(classToShow).css('visibility', 'visible');
}


function hideClass(trClass, buttonId, showMessage, hideMessage) {
    var button = '#' + buttonId;
    var value = showMessage;
    $(button).attr("value", value);
    $(button).attr("onclick", "showClass('" + trClass + "', '" + buttonId + "', '" + hideMessage + "', '" + showMessage + "');");
    var classToHide = '.' + trClass;
    $(classToHide).css('visibility', 'hidden');
}

function showTrClassForced(trClass) {
    var classToShow = '.' + trClass;
    $(classToShow).css('display', 'table-row');
}

function hideTrClassForced(trClass) {
    var classToHide = '.' + trClass;
    $(classToHide).css('display', 'none');
}

function hideById(id, tickboxParentId) {
    var idToHide = '#' + id;
    $(idToHide).css('display', 'none');
    if (tickboxParentId) {
        $(tickboxParentId).removeClass("active");
        $(tickboxParentId).children().attr("checked", false);

    }

}

function showById(id) {
    var idToShow = '#' + id;
    $(idToShow).css('display', 'inline');
}

function showByIdBlock(id) {
    var idToShow = '#' + id;
    $(idToShow).css('display', 'block');
}

function hideEmptyRows() {
    hideTrClassForced('noResult');
    $("input[name='hideEmpty']").val(true);
}

function showMonthly() {
    var denomClass = '.totalRowDenom';
    var graphColumnSpace = '.graphColumnSpace';
    var monthlyClass = '.monthly';
    var clarificationClass = '.clarification';
    var toBoldClass = '.to-bold-result';
    var aExplanationClass = '.monthly-explanation-a';
    var explanationClass = '.monthly-explanation';
    $(monthlyClass).css('display', 'table-cell');
    $(explanationClass).css('display', 'table-cell');
    $(aExplanationClass).css('display', 'inline');
    $(toBoldClass).css('font-weight', 'bold');
    var quarterlyClass = '.quarterly';
    $(quarterlyClass).css('display', 'none');
    var quarterlyExplanationClassA = '.quarterly-explanation-a';
    $(quarterlyExplanationClassA).css('display', 'none');
    var quarterlyExplanationClass = '.quarterly-explanation';
    $(quarterlyExplanationClass).css('display', 'none');
    $(denomClass).css('display', 'none');
    $(graphColumnSpace).css('display', 'none')


}

function showQuarterly() {
    var quarterlyClass = '.quarterly';
    var quarterlyHeading = '.hideIfQuarterly';
    var graphColumnSpace = '.graphColumnSpace';
    var denomClass = '.totalRowDenom';
    var clarificationClass = '.clarification';
    var toBoldClass = '.to-bold-result';
    var aExplanationClass = '.quarterly-explanation-a';
    var explanationClass = '.quarterly-explanation';
    $(quarterlyClass).css('display', 'table-cell');
    $(explanationClass).css('display', 'table-cell');
    $(aExplanationClass).css('display', 'inline');
    $(clarificationClass).css('display', 'none');
    $(toBoldClass).css('font-weight', 'bold');
    var monthlyClass = '.monthly';
    var monthlyExplanationClassA = '.monthly-explanation-a';
    var monthlyExplanationClass = '.monthly-explanation';
    $(monthlyClass).css('display', 'none');
    $(monthlyExplanationClassA).css('display', 'none');
    $(monthlyExplanationClass).css('display', 'none');
    $(denomClass).css('display', 'none');
    $(quarterlyHeading).css('display', 'none');
    $(graphColumnSpace).css('display', 'none')

}

function showAnnual() {
    var denomClass = '.totalRowDenom';
    var graphColumnSpace = '.graphColumnSpace';
    var monthlyClass = '.monthly';
    var clarificationClass = '.clarification';
    var aExplanationClass = '.monthly-explanation-a';
    var explanationClass = '.monthly-explanation';
    $(monthlyClass).css('display', 'none');
    $(explanationClass).css('display', 'none');
    $(aExplanationClass).css('display', 'none');
    $(clarificationClass).css('display', 'none');

    var quarterlyClass = '.quarterly';
    aExplanationClass = '.quarterly-explanation-a';
    explanationClass = '.quarterly-explanation';
    $(quarterlyClass).css('display', 'none');
    $(explanationClass).css('display', 'none');
    $(aExplanationClass).css('display', 'none');
    $(denomClass).css('display', '');
    $(graphColumnSpace).css('display', '')
    $(graphColumnSpace).attr('colspan', '1')

}


function printDiv(divId) {
    var printContents = document.getElementById(divId).innerHTML;
    var originalContents = document.body.innerHTML;

    document.body.innerHTML = printContents;

    window.print();

    document.body.innerHTML = originalContents;
}

function monthlyData() {
    $('.quantity').attr('disabled', 'true');
    $('.monthly_data').removeClass('hidden');
}

function disableonbeforeunload() {
    window.onbeforeunload = null;
}

// Disabled Save button until form has changes function
/*$(document).ready(function() {
    $('#queryForm').on('input change', function() { //Checks form for any changes
        $('.enabledOnChange').attr('disabled', false).css("pointer-events", "auto"); //if there are changes, enable the save buttons
        $('.disabledSaveBtn').popover("destroy"); //Disable popover for disabled buttons
    });
})*/ //-Moved to query/form.gsp


var myFormSubmitted = false;

function doubleSubmitPrevention(formId, keepPositionOnReload, button, htmlconfirmtext) {
    var myForm = document.getElementById(formId);
    var myForms = this.document.forms;
    //disable breadcrumb while saving queryForm
    if (htmlconfirmtext) {
        if ($("#queryForm").data("changed")) {
            var r = confirm(htmlconfirmtext);
            if (!r) {
                event.preventDefault();
                return false
            }
        }
    }
    $("#breadcrumbForQuery > a").addClass("just_black").removeAttr('href');
    clickAndDisable('queryNavButton', false, true);
    clickAndDisable('brand', false, true);
    clickAndDisable('updateScope', false, true);
    clickAndDisable('preventDoubleSubmit', true, true);
    myForm.onsubmit = function (e) {
        if (keepPositionOnReload) {
            $('<i id="quantity_share_spinner" style="margin-right: 4px" class="fas fa-circle-notch fa-spin"></i>').prependTo(button)

            e.preventDefault();

            // Multipart Request with FormData
            $.ajax({
                url: '/app/sec/query/saveQueryFormAjax',
                type: 'POST',
                data: new FormData($(myForm).get(0)),
                processData: false,
                contentType: false,
                success: function (data) {
                    myFormSubmitted = true;
                    window.onbeforeunload = null;
                    location.reload();
                }
            });
        } else {
            var myForms_length = myForms.length;

            for (var i = 0; i < myForms_length; i++) {
                var elements = myForms[i].elements;
                var elements_length = elements.length;

                for (var j = 0; j < elements_length; j++) {
                    var element = elements[j];

                    if (element.type == "submit" || element.type == "button" || element.type == "reset") {
                        if (myFormSubmitted) {
                            element.disabled = true;
                        }
                    }
                }
            }

            if (myFormSubmitted) {
                return false;
            }
            myFormSubmitted = true;
            return true;
        }

    };
}

function submitQuery(targetQueryId, formId, enableScope, renderHiddenInput, projectLevelWarning, disableOnBeforeUnload,
                     keepPositionOnReloadElem, rowLimit, injectFromXml, skipCalculation, runAjaxFormSubmit) {
    var theForm = $('#' + formId);
    var rowLimitExceeded = false;
    var rowLimitError = "";

    if (rowLimit && formChanged) {
        var indicatorId = $("#indicatorId").val();
        var designId = $("#childEntityId").val();
        var currentQueryId = $("#queryId").val();
        var currentRows = $('.queryResourceRow').length;

        if (!currentRows) {
            currentRows = 0;
        }

        $.ajax({
            type: 'POST',
            async: false,
            data: 'designId=' + designId + '&indicatorId=' + indicatorId + '&currentQueryId=' + currentQueryId + '&currentRows=' + currentRows,
            url: '/app/sec/util/maxRowLimitExceeded',
            success: function (data, textStatus) {
                if (data.exceeded) {
                    rowLimitExceeded = true;
                    rowLimitError = data.errorMessage;
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
            }
        });
    }

    var groupingContainer = $('.rowGroupingContainer');
    if ($(groupingContainer).length && $(groupingContainer).css("display") != "none") {
        var warningOk, warningText = null;
        warningText = $('#LocalizedGroupingWarning').val();
        warningOk = $('#LocalizedGroupingOk').val();

        Swal.fire({
            icon: "warning",
            text: warningText,
            confirmButtonText: warningOk,
            confirmButtonColor: '#d33',
            showCancelButton: false,
            allowOutsideClick: false,
            showLoaderOnConfirm: true,
            reverseButtons: true
        })
        return false;
    }

    if (rowLimitExceeded) {
        var container = $("#maxRowLimitExceededWarningContainer");
        $(container).empty();
        $("<div id='alert' class='alert alert-error'>\n" +
            "  <button data-dismiss='alert' class='close' type='button'>×</button>\n" +
            "  <strong>" + rowLimitError + "</strong>\n" +
            "</div>").prependTo(container);
        return false;
    } else {
        if (enableScope) {
            openOverlay('myOverlay');
            appendLoader('splitViewOverLay');
        }

        $('input[name="targetQueryId"]').remove()
        $('input[name="enableScopeSectionId"]').remove()
        $('input[name="injectFromXml"]').remove()
        $('input[name="skipCalculation"]').remove()

        if (targetQueryId) {
            $(theForm).append('<input type=\"hidden\" name=\"targetQueryId\" value=\"' + targetQueryId + '\" />');
        }

        if (renderHiddenInput) {
            $(theForm).append('<input type=\'hidden\' name=\"' + renderHiddenInput + '\" value=\'true\'/>');
        }

        if (enableScope) {
            $(theForm).append('<input type=\"hidden\" name=\"enableScopeSectionId\" value=\"' + enableScope + '\" />');
        }

        if (injectFromXml) {
            // there can be duplicated, remove the existing
            $(theForm).append('<input type=\"hidden\" name=\"injectFromXml\" value=\"' + injectFromXml + '\" />');
        }

        // Consider removing this skipCalculation if when time allows for analysis, as it seems not needed anymore. It was made for quick save btn
        if (skipCalculation) {
            $(theForm).append('<input type=\"hidden\" name=\"skipCalculation\" value=\"' + skipCalculation + '\" />');
        }

        let enableFormSubmit

        if (projectLevelWarning) {
            enableFormSubmit = doubleSubmitPrevention(formId, false, null, projectLevelWarning);
        } else if (keepPositionOnReloadElem) {
            enableFormSubmit = doubleSubmitPrevention('queryForm', true, keepPositionOnReloadElem);
        } else {
            enableFormSubmit = doubleSubmitPrevention(formId);
        }

        if (enableFormSubmit == false) {
            return false
        }

        $("#querybtn").off('click').attr('disabled', true);

        // Note: we have a hack that when we click query nav tab to change query, it should run the  $(theForm).trigger('submit'); to trigger the form submit to "save" action on query controller
        if (runAjaxFormSubmit) {
            // this method is on query/form.gsp
            ajaxDesignFormSubmit()
        } else {
            $(theForm).trigger('submit');
            myFormSubmitted = true;
        }

        if (renderHiddenInput) {
            $('input[name=\"' + renderHiddenInput + '\"]').remove()
        }

        return true
    }
}

$(document).ready(function () {
    $("#searchField").on('keyup', function () {
        // Retrieve the input field text and reset the count to zero
        var filter = $("#searchField").val();

        $("#resulttable tbody > tr").each(function () {
            if (filter && filter.length > 1) {
                // If the list item does not contain the text phrase fade it out
                if ($(this).text().search(new RegExp(filter, "i")) < 0) {
                    $(this).fadeOut();
                } else {
                    $(this).show();
                }
            } else {
                $(this).show();
            }
        });
    });
});

function setResultFormatting(radioButtonId) {
    if (radioButtonId) {
        radioButtonId = "#" + radioButtonId;
        var resultFormatting = $(radioButtonId).val();

        $.ajax({
            type: 'POST', data: 'resultFormatting=' + resultFormatting, url: '/app/resultformatting',
            success: function (data, textStatus) {

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
            }
        });

    }
}

function accountRequestToJoin(accountId) {
    if (accountId) {
        $.ajax({
            type: 'POST', data: 'id=' + accountId, url: '/app/sec/account/requestToJoin',
            success: function (data, textStatus) {
                $('#possibleAccounts').html(data.output);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
            }
        });
    }
}

$('.fastAndSilentCheckbox').on('change', function () {
    if ($(this).is(':checked')) {
        $("#moreWrapper :input").attr("disabled", true);
    } else {
        $("#moreWrapper :input").attr("disabled", false);
    }
});

$('.temporaryCalculationCheckbox').on('change', function () {
    if ($(this).is(':checked')) {
        $("#entityId").attr("disabled", true);
        $("#designId").attr("disabled", true);
    } else {
        $("#entityId").attr("disabled", false);
        $("#designId").attr("disabled", false);
    }
});

$('[rel="tooltip_datasources"]').popover({
    placement: 'top',
    template: '<div class="popover dataSourcesToolTip"><div class="arrow"></div><div class="popover-content dataSourcesToolTip"></div></div>',
    trigger: 'hover',
    html: true
});


$('[rel="tooltip_dashBoardPie"]').popover({
    placement: 'bottom',
    template: '<div class="popover"><div class="arrow"></div><div class="popover-content dataSourcesToolTip"></div></div>',

    trigger: 'hover',
    html: true
});

$('[rel="popover_floathelp"]').popover({
    placement: 'left',
    template: '<div class="popover"><div class="arrow"></div><div class="popover-content dataSourcesToolTip"></div></div>',
    trigger: 'hover',
    html: true
});

$('[rel="popover_bottom"]').popover({
    placement: 'bottom',
    template: '<div class="popover" style="color:black !important;"><div class="arrow"></div><div class="popover-content"></div></div>',
    trigger: 'hover',
    html: true
});

function copyFullNameToClipBoard(elementId) {
    var $temp = $("<input>");
    $("body").append($temp);
    $temp.val(document.getElementById(elementId).value).select();
    document.execCommand("copy");
    $temp.remove();
}

function copyText(element) {
    var $temp = $("<input>");
    $("body").append($temp);
    $temp.val(document.getElementById(element).value).select();
    document.execCommand("copy");
    $temp.remove();
}

function copyHref(element) {
    var $temp = $("<input>");
    $("body").append($temp);
    $temp.val($(element).attr('href')).select();
    document.execCommand("copy");
    $temp.remove();
}

function appendDatasetId(manualId) {
    $("<input type='hidden' value='" + manualId + "' />")
        .attr("id", "deleteDatasetManualId")
        .attr("name", "deleteDatasetManualId")
        .appendTo(".dropGroupedByForm");
}

function showLCAParameters(portfolioId, entityId, infoId, missingParamsText) {
    var infoIdvar = infoId;
    if ($('#popover' + infoIdvar).is(':visible')) {
        closeSourceListing(infoId);
        stopBubblePropagation(event);
    } else {
        destroyInfoPopovers();
        var queryString = 'entityId=' + entityId + '&portfolioId=' + portfolioId;
        $.ajax({
            type: 'POST',
            data: queryString,
            url: '/app/sec/design/getLCAQueryDatasetsForShowing',
            success: function (data) {
                $('#' + infoIdvar).popover({
                    placement: 'right',
                    template: '<div id="popover' + infoIdvar + '" class="popover importmapperPopover" ><div class="arrow"></div><div class="popover-content" style="max-width: 500px; max-height: 500px; overflow: auto"></div></div>',
                    trigger: 'manual',
                    html: true,
                    content: data && data.output.indexOf('<tr>') > -1 ? data.output : missingParamsText,
                    container: 'body'
                })
            },
            complete: function () {
                $('#' + infoIdvar).popover('show');
            }
        });
    }
}

function storePreviousUnitForRow(elem) {
    $(elem).attr('data-currentunit', $(elem).val());
}

function convertQuantity(elem, resourceId, changeView, calculatedMass, quantity, userGivenUnit) {
    var unitSelect = $(elem);
    var row = unitSelect.closest('tr');
    var quantityInput = row.find('.isQuantity');
    var massInput = row.find('.isMass')
    var currentUnit = unitSelect.attr("data-currentunit");
    var targetUnit = unitSelect.val();

    var convertedQuantity = "0";

    var thickness_mmString = row.find("input[name*='additional_thickness_mm.']").val();
    var thickness_inString = row.find("input[name*='additional_thickness_in.']").val();
    var thickness_mm;
    var thickness_in;

    if (thickness_mmString) {
        thickness_mm = parseFloat(thickness_mmString.replace(/,/g, '.'));
    }

    if (thickness_inString) {
        thickness_in = parseFloat(thickness_inString.replace(/,/g, '.'));
    }

    if (currentUnit && targetUnit && targetUnit !== currentUnit && resourceId) {
        $.ajax({
            async: false,
            data: 'resourceId=' + resourceId + '&currentUnit=' + currentUnit + '&targetUnit=' + targetUnit + '&quantity=' + quantityInput.val() + '&thickness_mm=' + thickness_mm + '&thickness_in=' + thickness_in,
            url: '/app/sec/util/convertResourceRowQuantity',
            type: 'GET',
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                if (data) {
                    if ("error" !== data.output) {
                        convertedQuantity = data.output;
                        removeBorder(quantityInput);
                        toggleInputOnChangeWarning(elem)
                    } else {
                        toggleInputOnChangeWarning(elem, true)
                    }
                }
            }
        });
        if(targetUnit == userGivenUnit){
            if(calculatedMass && quantity && quantity > 0){
                var massToDisplay = calculatedMass / quantity
                massInput.text(massToDisplay).trigger('input')
                row.find("input[name*='additional_massPerUnit_kg.']").val(massToDisplay)
            }
        }else if(targetUnit == "ton"){
            massInput.text(1000.0).trigger('input')
            row.find("input[name*='additional_massPerUnit_kg.']").val(1000)
        }else if(targetUnit == "kg"){
            massInput.text(1.0).trigger('input')
            row.find("input[name*='additional_massPerUnit_kg.']").val(1)
        }else{
            massInput.text('-').trigger('input')
            row.find("input[name*='additional_massPerUnit_kg.']").val('')
        }
        quantityInput.val(convertedQuantity).trigger('input');
    }
    unitSelect.blur(); // so following click can trigger new store on focus

    if (changeView) {
        // User changes unit dynamically on change page
        var q = ""
        if (convertedQuantity) {
            q = convertedQuantity.replace(",", ".")
        }

        $('#changeTableSelect').attr({
            "data-unit": targetUnit,
            "data-originalUnit": targetUnit,
            "data-originalQuantity": q
        });
    }
}

function handleVolumeFraction(elem, resourceId, queryId, sectionId, questionId) {
    var selectedUnit = $(elem).val();
    var row = $(elem).closest('tr');
    var span = row.find('.specialRuleSpan');

    if (span.length && selectedUnit && resourceId) {
        $.ajax({
            type: 'POST',
            data: 'resourceId=' + resourceId + '&selectedUnit=' + selectedUnit + '&queryId=' + queryId + '&sectionId=' + sectionId + '&questionId=' + questionId,
            url: '/app/sec/util/rerenderVolumeFractionLink',
            beforeSend: function () {
                $(span).empty();
                $(span).append("<i class=\"fas fa-circle-notch fa-spin oneClickColorScheme\"></i>")
            },
            success: function (data) {
                $(span).empty();
                $(span).append(data.output)
            }
        });
    }
}

function openQuestionChoises(elem, additionalQuestionId, resourceId, mainQuestionAnswer, indicatorId, queryId) {
    var infoIdvar = $(elem).attr("id");
    if ($('#popover' + infoIdvar).is(':visible')) {
        closeSourceListing(infoIdvar);
        stopBubblePropagation(event);
    } else {
        destroyInfoPopovers();
        var queryString = 'additionalQuestionId=' + additionalQuestionId + '&resourceId=' + resourceId + '&indicatorId=' + indicatorId + '&queryId=' + queryId;

        if (mainQuestionAnswer) {
            queryString = queryString + "&mainQuestionAnswer=" + mainQuestionAnswer
        }

        $.ajax({
            type: 'POST',
            data: queryString,
            url: '/app/sec/util/getAdditionalQuestionChoices',
            success: function (data) {
                $('#' + infoIdvar).popover('destroy');
                $('#' + infoIdvar).popover({
                    placement: "top",
                    template: '<div id="popover' + infoIdvar + '" class="popover showCalculationFormulaPopover"><button type="button" class="close hideSourceListing" onclick="closeSourceListing(\'' + infoIdvar + '\'); stopBubblePropagation(event); $(this).parent().hide()" data-dismiss="popover" aria-hidden="true">&times;</button><div class="arrow"></div><div class="popover-content"></div></div>',
                    trigger: 'manual',
                    html: true,
                    content: data.output,
                    container: 'body'
                })
            },
            complete: function () {
                $('#' + infoIdvar).popover('show');
            }
        });
    }
}

function openSourceListing(indicatorId, resourceId, questionId, profileId, entityId, showGWP, infoId, missingParamsText,
                                  modalPopover, queryId, sectionId, datasetId, placement, queryPage, disabled, manualId, customName, productDataListPage) {
    if (resourceId) {
        let queryString = 'indicatorId=' + indicatorId + '&questionId=' + questionId + '&profileId=' + profileId + '&resourceId=' + resourceId + '&entityId=' + entityId + '&showGWP=' + showGWP + '&infoId=' + infoId;
        if (queryId) {
            queryString = queryString + '&queryId=' + queryId;
        }
        if (sectionId && datasetId) {
            queryString = queryString + '&sectionId=' + sectionId + '&datasetId=' + datasetId;
        }
        if (queryPage) {
            queryString = queryString + '&queryPage=' + queryPage + '&disabled=' + disabled;
        }

        if (manualId) {
            queryString = queryString + '&manualId=' + manualId;
        }

        if (customName) {
            queryString = queryString + '&customName=' + customName;
        }

        if (productDataListPage) {
            queryString = queryString + '&productDataListPage=' + productDataListPage
        }

        $.ajax({
            type: 'POST',
            data: queryString,
            url: '/app/rendersourceListing',
            success: function (data) {
                if ($(`#modal-${infoId}`).length) {
                    $(`#modal-${infoId}`).remove()
                }
                $('body').append(data.output)
            },
        });
    } else {
        const position = dynamicPositionForPopover(infoId);
        $('#' + infoId).popover({
            placement: position,
            template: '<div id="popover' + infoId + '" class="popover importmapperPopoverWithImage' + modalPopover + '"><button type=\"button\" class=\"close hideSourceListing\" onclick=\"closeSourceListing(\'' + infoId + '\');stopBubblePropagation(event);\" data-dismiss=\"popover\" aria-hidden="true">&times;</button><div class="arrow"></div><div class="popover-content"></div></div>',
            trigger: 'manual',
            html: true,
            content: missingParamsText,
            container: 'body'
        }).popover('show');
    }
}

//    This method is deprecated, however, is kept for reference. It can be removed after a few months. November 2020 - Hung
// function openSourceListing(indicatorId, resourceId, questionId, profileId, entityId, showGWP, infoId, missingParamsText,
//                            modalPopover, queryId, sectionId, datasetId, placement, queryPage, disabled, manualId, customName, productDataListPage) {
//     var isDisabled = disabled ? disabled : "";
//     var infoIdvar = infoId;
//     if ($('#popover' + infoIdvar).is(':visible')) {
//         closeSourceListing(infoId);
//         stopBubblePropagation(event);
//     } else {
//         var position = dynamicPositionForPopover(infoId);
//         if (resourceId) {
//             var uniqueConstructionIdentifier = resourceId + guid();
//             destroyInfoPopovers();
//
//             var queryString = 'indicatorId=' + indicatorId + '&questionId=' + questionId + '&profileId=' + profileId + '&resourceId=' + resourceId + '&entityId=' + entityId + '&showGWP=' + showGWP + '&infoIdvar=' + infoIdvar;
//             if (queryId) {
//                 queryString = queryString + '&queryId=' + queryId;
//             }
//             if (sectionId && datasetId) {
//                 queryString = queryString + '&sectionId=' + sectionId + '&datasetId=' + datasetId;
//             }
//             if (queryPage) {
//                 queryString = queryString + '&queryPage=' + queryPage + '&disabled=' + disabled;
//             }
//
//             if (manualId) {
//                 queryString = queryString + '&manualId=' + manualId;
//             }
//
//             if (customName) {
//                 queryString = queryString + '&customName=' + customName;
//             }
//
//             if (productDataListPage) {
//                 queryString = queryString + '&productDataListPage=' + productDataListPage
//             }
//
//             $.ajax({
//                 type: 'POST',
//                 data: queryString,
//                 url: '/app/rendersourceListing',
//                 success: function (data) {
//                     $('#' + infoId).popover('destroy');
//                     $('#' + infoId).popover({
//                         placement: position,
//                         template: '<div id="popover' + infoIdvar + '" class="popover importmapperPopoverWithImage ' + modalPopover + '"><button type=\"button\" class=\"close hideSourceListing\" onclick=\"closeSourceListing(\'' + infoIdvar + '\'); stopBubblePropagation(event); $(this).parent().hide()\" data-dismiss=\"popover\" aria-hidden="true">&times;</button><div class="arrow"></div><div class="popover-content"></div></div>',
//                         trigger: 'manual',
//                         html: true,
//                         content: data,
//                         container: 'body'
//                     });
//                 },
//                 complete: function () {
//                     $('#' + infoId).popover('show');
//                     $("[rel=popover]").popover({})
//                 }
//             });
//         } else {
//             destroyInfoPopovers();
//             $('#' + infoIdvar).popover({
//                 placement: position,
//                 template: '<div id="popover' + infoIdvar + '" class="popover importmapperPopoverWithImage' + modalPopover + '"><button type=\"button\" class=\"close hideSourceListing\" onclick=\"closeSourceListing(\'' + infoIdvar + '\');stopBubblePropagation(event);\" data-dismiss=\"popover\" aria-hidden="true">&times;</button><div class="arrow"></div><div class="popover-content"></div></div>',
//                 trigger: 'manual',
//                 html: true,
//                 content: missingParamsText,
//                 container: 'body'
//             });
//             $('#' + infoIdvar).popover('show');
//
//         }
//     }
// }

function updateSourceListing(indicatorId, resourceId, questionId, profileId, entityId, showGWP, infoId) {
    var infoBubble = "#" + infoId;
    $(infoBubble).popover('destroy');
    $(infoBubble).attr('data-profileid', profileId);
    $(infoBubble).attr('data-resourceId', resourceId);
    $(infoBubble).attr("onclick", "openSourceListing('" + indicatorId + "','" + resourceId + "','" + questionId + "', '" + profileId + "','" + entityId + "', '" + showGWP + "', '" + infoId + "')");
}


function openCollapserData(grouperId, datasetIds) {
    var targetBubble = grouperId;
    var position = dynamicPositionForPopover(targetBubble);
    if ($('#popover' + targetBubble).is(':visible')) {
        closeSourceListing(targetBubble);
        stopBubblePropagation(event);
    } else {
        var queryString = 'grouperId=' + grouperId + '&datasetIds=' + datasetIds;
        $.ajax({
            type: 'POST',
            data: queryString,
            url: '/app/rendercollapserdata',
            success: function (data) {
                $('#' + targetBubble).popover({
                    placement: position,
                    template: '<div id="popover' + targetBubble + '" class="popover importmapperPopover"><button type=\"button\" class=\"close hideSourceListing\" onclick=\"closeSourceListing(\'' + targetBubble + '\')\" data-dismiss=\"popover\" aria-hidden="true"><i class="fa fa-times" aria-hidden="true"></i></button><div class="arrow"></div><div class="popover-content"></div></div>',
                    trigger: 'manual',
                    html: true,
                    content: data.output
                })
            },
            complete: function () {
                $('#' + targetBubble).popover('show');
            }
        });
    }
}

function openCombinedDisplayFields(datasetId, infoId, entityId, queryId, sectionId, questionId) {
    if ($('#popover' + infoId).is(':visible')) {
        closeSourceListing(infoId);
        stopBubblePropagation(event);
    } else {
        var position = dynamicPositionForPopover(infoId)
        $.ajax({
            type: 'POST',
            data: 'datasetId=' + datasetId + '&entityId=' + entityId + '&queryId=' + queryId + '&sectionId=' + sectionId + '&questionId=' + questionId,
            url: '/app/rendercombineddisplayfields',
            success: function (data) {
                $('#' + infoId).popover('destroy');
                $('#' + infoId).popover({
                    placement: position,
                    template: '<div id="popover' + infoId + '" class="popover importmapperPopover"><button type=\"button\" class=\"close hideSourceListing\" onclick=\"closeSourceListing(\'' + infoId + '\'); stopBubblePropagation(event);\" data-dismiss=\"popover\" aria-hidden="true">&times;</button><div class="arrow"></div><div class="popover-content"></div></div>',
                    trigger: 'manual',
                    html: true,
                    content: data.output
                })
            },
            complete: function () {
                $('#' + infoId).popover('show');
            }
        });
    }
}

function openCombinedDisplayFieldsResolver(datasetId, infoId, dontShowRecognizedFrom) {
    if ($('#popover' + infoId).is(':visible')) {
        closeSourceListing(infoId);
        stopBubblePropagation(event);
    } else {
        var position = dynamicPositionForPopover(infoId)
        $.ajax({
            type: 'POST',
            data: 'datasetId=' + datasetId + '&importMapper=true&dontShowRecognizedFrom=' + dontShowRecognizedFrom,
            url: '/app/rendercombineddisplayfields',
            success: function (data) {
                $('#' + infoId).popover('destroy');
                $('#' + infoId).popover({
                    placement: position,
                    template: '<div id="popover' + infoId + '" class="popover importmapperPopover"><button type=\"button\" class=\"close hideSourceListing\" onclick=\"closeSourceListing(\'' + infoId + '\'); stopBubblePropagation(event);\" data-dismiss=\"popover\" aria-hidden="true">&times;</button><div class="arrow"></div><div class="popover-content"></div></div>',
                    trigger: 'manual',
                    html: true,
                    content: data.output
                })
            },
            complete: function () {
                $('#' + infoId).popover('show');
            }
        });
    }
}


function deleteIfcDataset(element, manualId, amountContainer, datasetClass) {
    var container = $('#' + amountContainer);
    if ($(element).length && manualId) {
        var row = $(element).closest('tr');
        console.log("ROW" + row)
        if (row.length) {
            $.ajax({
                type: 'POST',
                data: 'datasetId=' + manualId + '&datasetClass=' + datasetClass,
                url: '/app/deleteifcdataset',
                success: function (data) {
                    row.hide();
                    row.after('<tr></tr>').hide();
                    container.text(parseInt(container.text()) - 1);
                }
            });
        }
    }
}

function openGroupedByData(grouperId, datasetClass) {
    var targetBubble = grouperId;
    var dClass = datasetClass;
    var queryString = 'grouperId=' + targetBubble + '&datasetClass=' + dClass;

    $.ajax({
        type: 'POST',
        data: queryString,
        url: '/app/rendergroupedbydata',
        success: function (data) {
            $('#' + targetBubble).popover('destroy');
            $('#' + targetBubble).popover({
                placement: 'bottom',
                template: '<div class="popover importmapperPopover"><button type=\"button\" class=\"close hideSourceListing\" onclick=\"closeSourceListing(\'' + targetBubble + '\')\" data-dismiss=\"popover\" aria-hidden="true"><i class="fa fa-times" aria-hidden="true"></i></button><div class="arrow"></div><h4 class="popover-title"></h4><div class="popover-content"></div></div>',
                trigger: 'manual',
                html: true,
                content: data.output,
                title: "Data for class: " + dClass
            })
        },
        complete: function () {
            $('#' + targetBubble).popover('show');
        }
    });
}

function openDenyMappingDatasets(target) {
    var targetBubble = target;
    var queryString = 'grouperId=' + targetBubble;

    $.ajax({
        type: 'POST',
        data: queryString,
        url: '/app/renderdenieddatasets',
        success: function (data) {
            $('#' + targetBubble).popover({
                placement: 'bottom',
                template: '<div class="popover importmapperPopover"><button type=\"button\" class=\"close hideSourceListing\" onclick=\"closeSourceListing(\'' + targetBubble + '\')\" data-dismiss=\"popover\" aria-hidden="true"><i class="fa fa-times" aria-hidden="true"></i></button><div class="arrow"></div><h4 class="popover-title"></h4><div class="popover-content"></div></div>',
                trigger: 'manual',
                html: true,
                content: data.output,
                title: "Too generic material definitions: "
            })
        },
        complete: function () {
            $('#' + targetBubble).popover('show');
        }
    });
}

function openSystemTrainingData(target, fileName, applicationId) {
    var targetBubble = target;
    var file = fileName;
    var queryString = 'grouperId=' + targetBubble + '&fileName=' + file + '&applicationId=' + applicationId;

    $.ajax({
        type: 'POST',
        data: queryString,
        url: '/app/rendersystemtrainingdata',
        success: function (data) {
            $('#' + targetBubble).popover({
                placement: 'bottom',
                template: '<div class="popover sourceListing importmapperPopover"><button type=\"button\" class=\"close hideSourceListing\" onclick=\"closeSourceListing(\'' + targetBubble + '\')\" data-dismiss=\"popover\" aria-hidden="true"><i class="fa fa-times" aria-hidden="true"></i></button><div class="arrow"></div><h4 class="popover-title"></h4><div class="popover-content"></div></div>',
                trigger: 'manual',
                html: true,
                content: data.output,
                title: "Data for file: " + file
            })
        },
        complete: function () {
            $('#' + targetBubble).popover('show');
        }
    });
}

function closeSourceListing(infoIdvar) {
    $('#' + infoIdvar).popover('hide');
}

// // CLOSE SOURCELISTING POPOVERS ON DOCUMENT CLICK
// TODO: REL-713 this miracle 'click' handler is super slow and Query page with many resource rows stops responding
// $(document).on('click', function (e) {
//     $('[data-original-title]:not(.serviceLifeButton):not(.isQuantity):not(.tableHeadingPopover):not(.autocomplete)').each(function () {
//         if (!$(this).is(e.target) && $(this).has(e.target).length === 0 && $('.popover').has(e.target).length === 0) {
//             (($(this).popover('hide').data('bs.popover') || {}).inState || {}).click = false;
//         }
//     });
// });

function infoHover() {
    $(this).addClass('infoBubbleAsset')
}

function useDefaults(entityId) {
    if (entityId) {
        var idToAppend = '#useDefaults' + entityId;

        $.ajax({
            type: 'POST', data: 'entityId=' + entityId, url: '/app/sec/query/setUseDefaults',
            success: function (data, textStatus) {
                $(idToAppend).empty();
                $(idToAppend).append(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
            }
        });
    }
}

var options;
var existingResourceGroups;

function initResourceSelects(resourceGroups) {
    var resourceSelects = document.getElementsByClassName("resourceSelect");

    if (resourceSelects) {
        var indicatorId = $(resourceSelects[0]).attr("data-indicatorId");
        var queryId = $(resourceSelects[0]).attr("data-queryId");

        for (var i = 0; i < resourceSelects.length; ++i) {
            var object = resourceSelects[i];
            getResourcesForQuestion(object, resourceGroups, indicatorId, queryId);
        }
    }
}

function getResourcesForQuestion(object, resourceGroups, indicatorId, queryId) {
    if (object) {
        if (resourceGroups) {
            var resourceGroupArray

            if (resourceGroups.indexOf(',') !== -1) {
                resourceGroupArray = resourceGroups.split(",");
            } else {
                resourceGroupArray = [resourceGroups];
            }

            if (resourceGroupArray) {
                if (!existingResourceGroups || existingResourceGroups.sort() !== resourceGroupArray.sort()) {
                    existingResourceGroups = resourceGroupArray;
                    var arrayLength = existingResourceGroups.length;
                    var queryString;

                    for (var i = 0; i < arrayLength; i++) {
                        if (i == 0) {
                            queryString = 'resourceGroup=' + existingResourceGroups[i];
                        } else {
                            queryString = queryString + '&resourceGroup=' + existingResourceGroups[i];
                        }
                    }

                    if (queryString) {
                        var unit = $(object).attr("data-userUnit");

                        if (unit) {
                            queryString = queryString + '&unit=' + unit;
                        }

                        if (indicatorId) {
                            queryString = queryString + '&indicatorId=' + indicatorId;
                        }

                        if (queryId) {
                            queryString = queryString + '&queryId=' + queryId;
                        }

                        $.ajax({
                            type: 'POST', data: queryString, url: '/app/resources',
                            success: function (data, textStatus) {
                                if (data.output) {
                                    $(object).children().remove();
                                    $(object).append(data.output);
                                    options = data.output;
                                }
                            },
                            error: function (XMLHttpRequest, textStatus, errorThrown) {
                            }
                        });
                    }
                } else {
                    $(object).children().remove();
                    $(object).append(options);
                }
            }
        } else {
            var entityId = $(object).attr("data-entityId");
            var indicatorId = $(object).attr("data-indicatorId");
            var queryId = $(object).attr("data-queryId");
            var sectionId = $(object).attr("data-sectionId");
            var questionId = $(object).attr("data-questionId");
            $.ajax({
                type: 'POST',
                data: 'entityId=' + entityId + '&indicatorId=' + indicatorId + '&queryId=' + queryId + '&sectionId=' + sectionId + '&questionId=' + questionId,
                url: '/app/resources',
                success: function (data, textStatus) {
                    if (data.output) {
                        $(object).children().remove();
                        $(object).append(data.output);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                }
            });
        }
    }
}

function addCompositeRow(parentRowClass, datasetId, resourceGroups, detailsKey, indicatorId, parentId) {
    var parentRow = '.' + parentRowClass;

    if (parentRow && datasetId) {
        $.ajax({
            type: 'POST',
            data: 'datasetId=' + datasetId + '&resourceGroups=' + resourceGroups + '&detailsKey=' + detailsKey + '&indicatorId=' + indicatorId,
            url: '/app/addcompositerow',
            success: function (data, textStatus) {
                $(parentRow).last().after(data.output);

                $('[rel="popover"]').popover({
                    placement: 'top'
                });

                initImportMapperAutocompletes(resourceGroups, parentId);
                filterImportMapperMaps(resourceGroups, parentId);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
            }
        });
    }
}

function acknowledgeUpdate(updateId, modalId) {
    if (updateId && modalId) {
        $.ajax({
            type: 'POST', data: 'id=' + updateId, url: '/app/sec/user/acknowledgeUpdate',
            success: function (data, textStatus) {
                var toHide = '#' + modalId;
                $(toHide).hide();
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
            }
        });
    }
}


function renderPreviousOperatingPeriods(parentEntityId, operatingPeriodId, indicatorId, queryId, sectionId, questionId, targetTableId) {
    if (parentEntityId && operatingPeriodId && queryId && sectionId && questionId && targetTableId) {
        var queryString = 'parentEntityId=' + parentEntityId + '&entityId=' + operatingPeriodId + '&indicatorId=' + indicatorId + '&queryId=' + queryId + '&sectionId=' + sectionId + '&questionId=' + questionId + '&targetTableId=' + targetTableId;
        $.ajax({
            type: 'POST',
            data: queryString,
            url: '/app/previousperiods',
            success: function (data, textStatus) {
                var innerTable = $('#innerTable' + targetTableId);
                if (innerTable) {
                    $(innerTable).remove();
                }
                var targetTable = document.getElementById(targetTableId)
                $(targetTable).show();
                $(targetTable).append(data.output);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
            }
        });
    }
}

function unitChangeWarning() {
    $.ajax({
        type: 'POST', url: '/app/unitchangewarning',
        success: function (data, textStatus) {
            if (data) {
                $('#unitChangeWarning').modal();
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}

function copyEntityAnswers(targetEntityId, entityId, queryId, indicatorId) {
    if (targetEntityId && entityId && queryId && indicatorId) {
        $.ajax({
            type: 'POST',
            data: 'targetEntityId=' + targetEntityId + '&entityId=' + entityId + '&indicatorId=' + indicatorId +
                '&queryId=' + queryId, url: '/app/copyentityanswers',
            success: function (data, textStatus) {
                $('body').append(data.output);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
            }
        });
    }
}

function showOtherDesigns(element, parentEntityId, designId, indicatorId, queryId, sectionId, questionId, resourceTableId, modifiabled) {
    var isModifiabled = modifiabled ? modifiabled : ''
    if (parentEntityId && designId && queryId && sectionId && questionId && resourceTableId) {
        $.ajax({
            type: 'POST',
            data: 'id=' + designId + '&parentEntityId=' + parentEntityId + '&indicatorId=' + indicatorId + '&queryId=' +
                queryId + '&sectionId=' + sectionId + '&questionId=' + questionId + '&resourceTableId=' + resourceTableId + '&modifiabled=' + isModifiabled,
            url: '/app/otherdesignsdropdown',
            success: function (data, textStatus) {
                element.parent().find("ul").remove();
                element.prop("onclick", null);
                element.removeAttr("onclick");
                element.parent().append(data.output);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
            }
        });
    }
}

function renderDesignResults(designId, indicatorId, queryId, sectionId, questionId, targetTableId, currentDesignIdTxT, modifiabled) {
    var currentDesignId = currentDesignIdTxT ? currentDesignIdTxT : ''
    if (designId && queryId && sectionId && questionId && targetTableId) {
        targetTableId = "#" + targetTableId;

        $.ajax({
            type: 'POST',
            data: 'designId=' + designId + '&indicatorId=' + indicatorId + '&queryId=' + queryId + '&sectionId=' + sectionId + '&questionId=' + questionId + '&currentDesignId=' + currentDesignId + '&modifiabled=' + modifiabled,
            url: '/app/designresults',
            success: function (data, textStatus) {
                $(targetTableId).find("h4").remove();
                $(targetTableId).find("table").remove();
                $(targetTableId).fadeIn();
                $(targetTableId + 'Row').fadeIn();
                $(targetTableId).append(data.output);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
            }
        });
    }
}

// Click on icon if it is (+)
function expandAnswerSection(iconId) {
    if ($(iconId).hasClass('fa-plus')) {
        $(iconId).trigger('click')
    }
}

/* comment out since it's not in use anywhere 19.02.2021. Remove after few months
function copyAnswerFromRset(entityId, indicatorId, queryId, sectionId, questionId, textFieldId) {
    if (entityId && indicatorId && queryId && sectionId && questionId && textFieldId) {
        textFieldId = "#" + textFieldId.replace(".", "\\.");

        $.ajax({
            type: 'POST',
            data: 'entityId=' + entityId + '&indicatorId=' + indicatorId + '&queryId=' + queryId + '&sectionId=' + sectionId + '&questionId=' + questionId,
            url: '/app/getrsetvalue',
            success: function (data, textStatus) {
                $(textFieldId).val(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
            }
        });
    }
}
*/

function toggleHiddenInput(checkbox, hiddenInputClass) {
    var hiddenClass = hiddenInputClass.replace(/\./g, '\\.');
    var checkBox = $(checkbox);
    var input = $(checkBox).closest('td').find('.' + hiddenClass);

    if ($(input).length && $(checkBox).length) {
        if ($(checkBox).is(":checked")) {
            $(checkBox).prop('checked', true);
            $(input).attr("disabled", "disabled");
        } else {
            $(checkBox).prop('checked', false);
            $(input).prop('disabled', false);
        }
    }
}

function enableReadButton(injectXmlButton, questionId, fecEpdInjectButton) {
    if (injectXmlButton) {
        $('#rsetReadButton' + questionId).removeClass("removeClicks")
    }

    if (fecEpdInjectButton) {
        $('#fecEpdReadButton').attr("disabled", false);
    }
}

/**
 * Enables or disabled the button to allow import form RSEE;
 * one parcelle switch must be checked to pass validation
 * @param questionId
 * @param renderParcelleCheckbox means it's FEC project
 */
function enableInjectFromRsetButton(questionId, renderParcelleCheckbox) {
    var buttonElement = $('#injectAnswerFromRsetButton' + questionId)[0];
    if ($(".parcelle-switch:checked").length === 1 || !renderParcelleCheckbox) {
        buttonElement.removeAttribute("disabled");
    } else {
        buttonElement.setAttribute("disabled", true);
    }
}

function readFromRset(that, parentEntityId, queryId, formId) {
    if (parentEntityId) {
        $('<i id="quantity_share_spinner" style="margin-right: 4px" class="fas fa-circle-notch fa-spin"></i>').prependTo(that);
        $(that).addClass('removeClicks');
        doubleSubmitPrevention(formId);
        var jqueryFormId = '#' + formId;
        $(jqueryFormId).append("<input type='hidden' name='save' value='true'>");

        $.ajax({
            url: '/app/sec/query/saveQueryFormAjax',
            type: 'POST',
            data: new FormData($(jqueryFormId).get(0)),
            processData: false,
            contentType: false,
            success: function (data) {
                myFormSubmitted = true;
                window.onbeforeunload = null;
                location.reload();
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                window.onbeforeunload = window.scrollTo(0, 0);
                window.location.reload(true);
            }
        });
    }
}

function injectAnswerFromRset(questionId, warningText, queryId) {
    var rseeButtonElement = $("#injectAnswerFromRsetButton" + questionId);
    if (queryId) {
        $('<i id="quantity_share_spinner" style="margin-right: 4px" class="fas fa-circle-notch fa-spin"></i>').prependTo(rseeButtonElement);
        rseeButtonElement[0].setAttribute("disabled", "true");
        submitQuery(queryId, 'queryForm', null, 'save', warningText, null, null, null, true)
    }
}

function injectAnswerFromFecEpd(that, parentEntityId, entityId, indicatorId, queryId, formId) {
    if (entityId && indicatorId) {
        $('<i id="quantity_share_spinner" style="margin-right: 4px" class="fas fa-circle-notch fa-spin"></i>').prependTo(that);
        $(that).addClass('removeClicks');
        doubleSubmitPrevention(formId);
        var jqueryFormId = '#' + formId;
        $(jqueryFormId).append("<input type='hidden' name='save' value='true'>");
        $.ajax({
            url: '/app/sec/query/saveQueryFormAjax',
            type: 'POST',
            data: new FormData($(jqueryFormId).get(0)),
            processData: false,
            contentType: false,
            success: function (data) {
                $.ajax({
                    async: false, type: 'POST',
                    data: 'entityId=' + entityId + '&indicatorId=' + indicatorId + '&parentEntityId=' + parentEntityId + '&queryId=' + queryId,
                    url: '/app/readfecepd',
                    success: function (data, textStatus) {

                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        window.onbeforeunload = null;
                        location.reload();
                    }
                });
                window.onbeforeunload = null;
                location.reload();
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                window.onbeforeunload = null;
                location.reload();
            }
        });
    }
}

/*function getQueryFilterChoices(elementId, filterAttribute, filterName, indicatorId, queryId, questionId) {
    if (elementId && filterAttribute && filterName && queryId && questionId) {
        var element = document.getElementById(elementId + 'queryFilter');
        $(element).children().remove();
        var resetId = '#' + elementId + 'reset';

        $.ajax({
            type: 'POST',
            data: 'resourceAttribute=' + filterAttribute + '&filterName=' + filterName + '&indicatorId=' + indicatorId + '&queryId=' + queryId + '&questionId=' + questionId,
            url: '/app/filterchoices',
            beforeSend: function () {
                $("#" + elementId + "loading").show();
                $("#" + elementId + "queryFilterSpan").hide();
            },
            success: function (data, textStatus) {
                $("#" + elementId + "loading").hide();
                $("#" + elementId + "queryFilterSpan").show();
                $(element).show();
                $(element).append(data);
                $(element).attr("data-resourceAttribute", filterAttribute);
                filterBoxToSelect2(elementId, filterName);
                $(resetId).show();
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
            }
        });

    }
}*/

function quickFilterChoices(tdId, ulId, filterAttribute, filterName, indicatorId, queryId, entityId, constructionPage, callingDomain, resourceGroupsImportMapper, parentId, classificationParamId, productDataListPage) {
    if (filterAttribute && filterName && queryId) {
        var queryString = 'resourceAttribute=' + filterAttribute + '&filterName=' + filterName + '&indicatorId=' + indicatorId + '&queryId=' + queryId + '&entityId=' + entityId + '&parentEntityId=' + parentId;

        if (constructionPage) {
            queryString = queryString + '&constructionPage=' + constructionPage;

            if (classificationParamId !== "null") {
                queryString = queryString + '&classificationParamId=' + classificationParamId;
            }
        }

        if (productDataListPage) {
            queryString = queryString + '&productDataListPage=' + productDataListPage;

            if (classificationParamId !== "null") {
                queryString = queryString + '&classificationParamId=' + classificationParamId;
            }
        }

        //This is to prevent getting user saved filters for split
        if (callingDomain === "splitOrChange") {
            queryString = queryString + '&splitFilters=true';
        }
        var listPerFilter = $('#' + ulId);
        var tdForFilter = $('#' + tdId);
        $(listPerFilter).children().remove();
        $.ajax({
            type: 'POST',
            data: queryString,
            url: '/app/getquickfilters',
            success: function (data) {
                $(listPerFilter).append(data.output);
                if (listPerFilter && listPerFilter.val()) {
                    $(tdForFilter).addClass('filterChosenHighlight');
                }
                /*if (callingDomain === "query") {
                    filterQueryBasedOnUser();
                } else if (callingDomain === "importMapper") {
                    filterImportMapperBasedOnUser(resourceGroupsImportMapper, parentId);
                } else if (callingDomain === "remapAutoComplete") {
                    filterMapAndRemapAutocompletesBasedOnUser();
                } else if (callingDomain === "splitOrChange") {
                    filterChangeOrSplitAutoCompletes();
                } else if (callingDomain === 'rulesetMapping') {
                    filterMapAndRemapAutocompletes();
                }*/
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
            }
        });

    }
}

function nestedFilterChoices(ulId, filterAttribute, resourceTypeId, queryId, indicatorId, splitPage, childEntityId, country, parentDropdown) {
    if (ulId && filterAttribute && (resourceTypeId || country)) {
        var select2Box = $('.nestedSelectContainer');
        var queryString = 'resourceAttribute=' + filterAttribute + '&childEntityId=' + childEntityId;
        if (queryId && indicatorId) {
            queryString = queryString + '&queryId=' + queryId + '&indicatorId=' + indicatorId;
        }
        if (country) {
            queryString = queryString + '&countryId=' + country
        }
        if (resourceTypeId) {
            queryString = queryString + '&resourceTypeId=' + resourceTypeId
        }

        var filterSelect;

        if (splitPage) {
            filterSelect = $('.quickFilterSelectSplit :selected');
            queryString = queryString + '&splitPage=true'
        } else {
            filterSelect = $('.quickFilterSelect :selected');
        }
        var chosenAttributes = $(filterSelect).map(function () {
            var returnable = $(this).data("attribute");
            if (returnable && returnable.indexOf("resourceType") < 0) {
                return returnable
            }

        }).get();
        var chosenAttributeValues = $(filterSelect).map(function () {
            var parentAttribute = $(this).data("attribute");
            if (!$(this).val() == "" && (parentAttribute && parentAttribute.indexOf("resourceType") < 0)) {
                return $(this).attr("data-resourceAttributeValue");
            }
        }).get();

        if (chosenAttributes && chosenAttributeValues) {
            queryString = queryString + '&chosenAttributes=' + chosenAttributes + '&chosenAttributeValues=' + chosenAttributeValues
        }

        var listPerFilter = $('#' + ulId);
        $.each(xhrPool, function (idx, jqXHR) {
            jqXHR.abort();
        });
        $.ajax({
            type: 'POST',
            data: queryString,
            url: '/app/getnestedfilters',
            beforeSend: function (jqXHR, settings) {
                xhrPool.push(jqXHR);
            },
            success: function (data) {
                $(listPerFilter).empty().append(data.output);
                if ($(parentDropdown).is(':visible')) {
                    $(select2Box).removeClass('hidden');
                } else {
                    // User already selected from or closed parent dropdown
                    $(select2Box).addClass('hidden');
                }

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
            }
        });
    }

}


function filterBoxToSelect2(elementId, filterName) {
    var element = document.getElementById(elementId + 'queryFilter');
    $(element).select2({
        maximumSelectionLength: 4,
        minimumResultsForSearch: Infinity,
        placeholder: filterName


    }).maximizeSelect2Height();
}

function quickFiltersSelect2(placeholderText, classList) {
    var $select = classList ? $(classList) : $('.quickFilterSelect');
    $select.select2({
        maximumSelectionLength: 4,
        minimumResultsForSearch: 0,
        placeholder: placeholderText,
        allowClear: true,
        matcher: matchCustom,
        templateResult: function formatOptions(data, container) {
            var option;
            if (data.element) {
                $(container).attr("data-resourceTypeId", $(data.element).attr("data-resourceTypeId"));
                $(container).attr("data-resourceType", $(data.element).attr("data-resourceType"));
                $(container).attr("data-subType", $(data.element).attr("data-subType"));
                $(container).attr("data-attribute", $(data.element).attr("data-attribute"));
                $(container).attr("data-resourceAttributeValue", $(data.element).attr("data-resourceAttributeValue"));
                $(container).attr("data-haschildren", $(data.element).attr("data-haschildren"));
                $(container).attr("data-isoCode", $(data.element).attr("data-isoCode"));

                if ($(data.element).attr("data-hidden")) {
                    $(container).addClass("hidden");
                }

                if (data.element.hasAttribute("data-isoCode")) {
                    var isoUrl = "/static/isoflags/" + $(data.element).attr("data-isoCode") + '.png';
                    var fallbackUrl = "/app/assets/isoflags/" + $(data.element).attr("data-isoCode") + '.png';
                    if (data.text == "One Click LCA") {
                        isoUrl = "/static/isoflags/ocl.png";
                        fallbackUrl = "/app/assets/isoflags/ocl.png";
                    }
                    option = $('<span style="white-space: nowrap; width: 180px;overflow:visible"><img src="' + isoUrl + '" onerror="this.onerror=null;this.src=\'' + fallbackUrl + '\'" class="flagIso" style="width:15px;"/>' + data.text + '</span>');
                } else if (data.element.hasAttribute("data-environmentDataSourceType")) {
                    option = $('<span><img src="/app/assets/img/generic_resource.png" class="flagIso" style="width:15px;"/>' + data.text + '</span>');
                } else if (data.element.hasAttribute("data-environmentDataSourceTypePrivate")) {
                    option = $('<span><i class="far fa-eye-slash" aria-hidden="true"></i>  ' + data.text + '</span>');
                } else if (data.element.hasAttribute("data-environmentDataSourceTypePlant")) {
                    option = $('<span><i class="fa fa-industry" aria-hidden="true"></i>  ' + data.text + '</span>');
                } else if (data.element.hasAttribute("data-quintileEmission")) {
                    option = $('<span style="text-align: left;" class="co2CloudContainer"><i class="fa fa-cloud ' + $(data.element).attr("data-quintileEmission") + '"></i><br/>' + data.text + '</span>');
                } else if (data.element.hasAttribute("data-resourceAttributeValue") && $(data.element).attr("data-resourceAttributeValue") == 'construction') {
                    option = $('<span style="white-space: nowrap; width: 180px;overflow:visible"><img src="/app/assets/constructionTypes/structure.png" class="constructionType"/>' + data.text + '</span>');
                }else if(data.element.hasAttribute("data-resourceAttributeValue") && $(data.element).attr("data-multiPartValue") == 'true') {
                    option = $('<span style="white-space: nowrap; width: 180px;overflow:visible"><img src="/app/assets/constructionTypes/structure.png" class="constructionType"/>' + data.text + '</span>');
                }else {
                    option = data.text
                }
                return option
            }

        },
        templateSelection: function formatSelection(data, container) {
            var option;
            if (data.element) {
                if (data.element.hasAttribute("data-isoCode")) {
                    var isoUrl = "/static/isoflags/" + $(data.element).attr("data-isoCode") + '.png';
                    var fallbackUrl = "/app/assets/isoflags/" + $(data.element).attr("data-isoCode") + '.png';
                    if (data.text == "One Click LCA") {
                        isoUrl = "/static/isoflags/ocl.png";
                        fallbackUrl = "/app/assets/isoflags/ocl.png";
                    }
                    option = $('<span><img src="' + isoUrl + '" onerror="this.onerror=null;this.src=\'' + fallbackUrl + '\'" class="flagIso" style="width:15px;"/>' + data.text + '</span>');
                } else if (data.element.hasAttribute("data-environmentDataSourceType")) {
                    option = $('<span><img src="/app/assets/img/generic_resource.png" class="flagIso" style="width:15px;"/>' + data.text + '</span>');
                } else if (data.element.hasAttribute("data-quintileEmission")) {
                    option = $('<span class="co2CloudContainer"><i style="margin-top: 5px;" class="fa fa-cloud ' + $(data.element).attr("data-quintileEmission") + '"></i></span>');
                } else {
                    option = data.text;
                }
            } else {
                option = data.text;
            }
            return option

        }
    }).on('select2:unselecting', function () {
        $(this).data('unselecting', true);
    }).on('select2:opening', function (e) {
        if ($(this).data('unselecting')) {
            $(this).removeData('unselecting');
            e.preventDefault();
        }
    }).on('select2:select', function (e) {
        $(this.parentElement.parentElement).addClass('filterChosenHighlight');
    }).on('select2:unselect', function (e) {
        $(this.parentElement.parentElement).removeClass('filterChosenHighlight');
    }).on('select2:close', function (e) {
        $select.find(".subTypeResource.forSelect2").addClass("hidden").attr("data-hidden", true)
    }).maximizeSelect2Height();
    var $label = $select.parents('.form-group').find('label');
    $label.on('click', function (e) {
        $(this).parents('.form-group').find('select').trigger('focus').select2('focus');
    });

    // Trigger search
    $select.on('keydown', function (e) {
        var $select = $(this), $select2 = $select.data('select2'), $container = $select2.$container;

        // Unprintable keys
        if (typeof e.which === 'undefined' || $.inArray(e.which, [0, 8, 9, 12, 16, 17, 18, 19, 20, 27, 33, 34, 35, 36, 37, 38, 39, 44, 45, 46, 91, 92, 93, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 123, 124, 144, 145, 224, 225, 57392, 63289]) >= 0) {
            return true;
        }

        // Already opened
        if ($container.hasClass('select2-container--open')) {
            return true;
        }

        $select.select2('open');

        // Default search value
        var $search = $select2.dropdown.$search || $select2.selection.$search,
            query = $.inArray(e.which, [13, 40, 108]) < 0 ? String.fromCharCode(e.which) : '';
        if (query !== '') {
            $search.val(query).trigger('keyup');
        }
    });

    $select.on("change", function () {
        var nestedSelect = $('.nestedSelectContainer');
        $(nestedSelect).addClass('hidden');

    });

    function matchCustom(params, data) {
        var result
        // If there are no search terms, return all of the data
        if ($.trim(params.term) === '') {
            result = data
            return data;
        }

        // Do not display the item if there is no 'text' property
        if (typeof data.text === 'undefined') {
            result = null
            return null;
        }

        // `params.term` should be the term that is used for searching
        // `data.text` is the text that is displayed for the data object
        if (data.text.toLowerCase().indexOf(params.term.toLowerCase()) > -1) {
            var modifiedData = $.extend({}, data, true);
            // You can return modified objects from here
            // This includes matching the `children` how you want in nested data sets
            $(modifiedData.element).removeClass("hidden").removeAttr("data-hidden")
            return modifiedData;
        }
        // Return `null` if the term should not be displayed
        return null;
    }

}

function collapseTable(elem, ids, level2) {
    var icon = 'icon-chevron-right icon-chevron-down'
    if (level2) {
        icon = 'fa-minus fa-plus'
    }
    var sectionExpander = $(elem).find(".collapserIcon").toggleClass(icon);
    var elementClasses = ids.split(".");

    $.each(elementClasses, function (i) {
        $('.' + this).fadeToggle(200);
    });
}

function renderPageRenderTimes(showPopup, backEndTime, gspRender, domTime, windowTime) {
    var time1 = parseFloat(backEndTime);
    var time2 = parseFloat(gspRender);
    var time3 = parseFloat(domTime);
    var time4 = parseFloat(windowTime);

    if (time1 && time2 && time3 && time4) {
        var overall = (time1 + time2 + time3 + time4).toFixed(1);
        if (overall > 2 && showPopup === "true") {
            $("<div id='renderingTimesAlert' class='alert alert-info'>\n" +
                "  <button data-dismiss='alert' class='close' type='button'>×</button>\n" +
                "  <strong>Backend param handling took: " + time1.toFixed(1) + "s, GSP param handling took: " + time2.toFixed(1) + "s, Dom ready: " + time3.toFixed(1) + "s, Window loaded: " + time4.toFixed(1) + "s, Overall: " + overall + "s.</strong>\n" +
                "</div>").prependTo("#messageContent");
            setTimeout(function () {
                $("#renderingTimesAlert").fadeOut();
            }, 5000);
        }
        $("#mainContentRenderTime").html("Backend param handling took: " + time1.toFixed(1) + "s, GSP param handling took: " + time2.toFixed(1) + "s, Dom ready: " + time3.toFixed(1) + "s, Window loaded: " + time4.toFixed(1) + "s, Overall: " + overall + "s.");
    }
}

function select2theSingleSelects(placeHolder) {
    $('.nonTypeaheadSelect').select2({
        maximumSelectionLength: 4,
        minimumResultsForSearch: Infinity,
        placeholder: placeHolder,
        containerCssClass: ":all:"
    }).maximizeSelect2Height();
    $('.nonTypeaheadSelect').on("select2:select", function () {
        $('.nonTypeaheadSelect').val('')
    });
}

function filterData(buttonStatusText, warningTitle, warningText, resetText, cancelText) {
    var userAgent = navigator.userAgent;
    var quickFilters = $('.quickFilters');
    var quickFitlerCaret = $('#quickFilterCaret');
    var quickFilterOptions = $('#quickFilterOptions');
    var quickFilterTds = $('.quickFilterTableTd');
    var anyFiltersChosen = $('.filterChosenHighlight');
    var filterButtonStatusText = $('#filterButtonText');

    // this is to fix a bug in safari that it does not support sliding..
    if (userAgent.toLowerCase().indexOf("mac") >= 0) {
        if ($(quickFilterOptions).is(":hidden")) {
            $(quickFilterOptions).removeClass('hidden');
            $(quickFitlerCaret).removeClass('caret');
            $(quickFitlerCaret).addClass('fa fa-caret-up');
            $(quickFilters).trigger('click');
        } else {
            $(quickFilterOptions).addClass('hidden');
            $(quickFitlerCaret).removeClass('fa fa-caret-up');
            $(quickFitlerCaret).addClass('caret');
            Swal.fire({
                title: warningTitle,
                text: warningText,
                icon: "warning",
                confirmButtonText: resetText,
                cancelButtonText: cancelText,
                confirmButtonColor: "red",
                showCancelButton: true,
                reverseButtons: true
            }).then(result => {
                if (result.value) {
                    $('.quickFilterSelect').val([]).trigger('change');
                    $('#dataHits').empty();
                    $(quickFilterTds).removeClass('filterChosenHighlight');
                    $('.dataHitsButton').addClass('hidden');
                    $(quickFilterOptions).slideUp("slow");
                    $(quickFitlerCaret).removeClass('fa fa-caret-up');
                    $(quickFitlerCaret).addClass('caret');
                }
            });
        }
    } else {
        if ($(quickFilterOptions).is(":hidden")) {
            $(filterButtonStatusText).text(resetText);
            $(quickFilterOptions).slideDown("slow");
            $(quickFilterOptions).removeClass('hidden');
            $(quickFitlerCaret).removeClass('caret');
            $(quickFitlerCaret).addClass('fa fa-caret-up');
            $(quickFilters).trigger('click');
        } else {
            if (quickFilterTds && anyFiltersChosen.length > 0) {
                Swal.fire({
                    title: warningTitle,
                    icon: "warning",
                    text: warningText,
                    confirmButtonText: resetText,
                    cancelButtonText: cancelText,
                    confirmButtonColor: "#ff6666",
                    showCancelButton: true,
                    reverseButtons: true
                }).then(result => {
                    if (result.value) {
                        $('.quickFilterSelect').val([]).trigger('change');
                        $('#dataHits').empty();
                        $(quickFilterTds).removeClass('filterChosenHighlight');
                        $('.dataHitsButton').addClass('hidden');
                        $(quickFilterOptions).slideUp("slow");
                        $(quickFitlerCaret).removeClass('fa fa-caret-up');
                        $(quickFitlerCaret).addClass('caret');
                        $(filterButtonStatusText).text(buttonStatusText);
                    }
                });
            } else {
                $(quickFilterTds).removeClass('filterChosenHighlight');
                $(quickFilterOptions).slideUp("slow");
                $(quickFitlerCaret).removeClass('fa fa-caret-up');
                $(quickFitlerCaret).addClass('caret');
                $(filterButtonStatusText).text(buttonStatusText);

            }
        }
    }

}

$('#hideQuickFilters').on('click', function () {
    var quickFilters = $('.quickFilters');
    var quickFilterOptions = $('#quickFilterOptions');
    if ($(quickFilterOptions).is(":hidden")) {
        $(quickFilterOptions).slideDown("slow");
        $(quickFilterOptions).removeClass('hidden');

    } else {
        $(quickFilterOptions).slideUp("slow");
    }
    $(quickFilters).trigger('click');
    $(quickFilters).removeAttr('onclick')
});

$('#resetAllFilters').on('click', function () {
    $('.quickFilterSelect').val([]).trigger('change');
    $('#dataHits').empty();
    var quickFilterTds = $('.quickFilterTableGroup');
    $(quickFilterTds).removeClass('highlighted');
    $(quickFilterTds).addClass('warningHighlight');
    $('.dataHitsButton').addClass('hidden');

    setTimeout(function () {
        $(quickFilterTds).removeClass('warningHighlight');
    }, 2000);

});

function setSelectValWithNestedOption(selectId, value, selectVal, resourceTypeVal, isArea) {
    var target = $('#' + selectId);
    if (isArea) {
        $(target).find('.stateForCountry').remove();
        $(target).append("<option disabled='disabled' data-attribute='areas' data-resourceAttributeValue='" + resourceTypeVal + "' class='stateForCountry' data-state='" + selectVal + "' data-areas='" + resourceTypeVal + "' selected='selected' value='" + selectVal + "'>" + value + "</option>").trigger('change');
    } else {
        $(target).find('.subTypeResource').remove();
        $(target).append("<option disabled='disabled' data-attribute='resourceType' data-resourceAttributeValue='" + resourceTypeVal + "' class='subTypeResource' data-subType='" + selectVal + "' data-resourceType='" + resourceTypeVal + "' selected='selected' value='" + selectVal + "'>" + value + "</option>").trigger('change');
    }
    $(target).parent().parent().addClass('filterChosenHighlight')
}

function handleClonedRow(clonedRow, originalTableRow, uniqueConstructionIdentifier, construction, constituent, insertAfter) {
    var randomIdForRow = guid();
    var randomIdForDataset = guid();

    var oldId = $(clonedRow).attr("id");
    $(clonedRow).attr("id", randomIdForRow);
    $(clonedRow).removeClass("existingResourceRow");

    if (uniqueConstructionIdentifier) {

        // HANDLE COPYING OF CONSTRUCTION, CHANGES REFERENCES TO CONSTRUCTION IDENTIFIERS WITH REGEX ON OUTER HTML

        var oldIdentifier = $(clonedRow).find('[data-uniqueConstructionIdentifierContainer="true"]').val();
        var re1 = new RegExp(oldIdentifier, "g");
        var re2 = new RegExp(oldId, "g");

        if (construction) {
            var constituentToggler = $(clonedRow).find('.constituentToggler');
            constituentToggler.attr("onclick", "toggleUnderLyingConstructions('" + randomIdForRow + "constructionToggler', '" + uniqueConstructionIdentifier + "row');");

            var firstChild = $(constituentToggler).children().first();


            $(firstChild).attr("id", randomIdForRow + "constructionToggler");

            $(clonedRow).attr("data-parentidentifier", uniqueConstructionIdentifier);
            $(clonedRow).attr("data-constructionrow", uniqueConstructionIdentifier + "row");

            $(clonedRow).children().each(function () {
                if (this.nodeType === 1) {
                    var outerHtml = this.outerHTML;
                    outerHtml = outerHtml.replace(re1, uniqueConstructionIdentifier);
                    outerHtml = outerHtml.replace(re2, randomIdForRow);
                    this.outerHTML = outerHtml;
                }
            });

        } else if (constituent) {
            $(clonedRow).attr("data-dragmewithparent", uniqueConstructionIdentifier + "row");

            $(clonedRow).children().each(function () {
                if (this.nodeType === 1) {
                    var outerHtml = this.outerHTML;
                    outerHtml = outerHtml.replace(re1, uniqueConstructionIdentifier);
                    outerHtml = outerHtml.replace(re2, randomIdForRow);
                    this.outerHTML = outerHtml;
                }
            });

            $(clonedRow).removeClass(oldIdentifier + "row");
            $(clonedRow).addClass(uniqueConstructionIdentifier + "row");
        }
    }

    //Sets input values to match original row

    $(originalTableRow).find('input[type=text],select').each(function (i, obj) {
        $(clonedRow).find('input[type=text],select').eq(i).val($(obj).val());
    });

    if (insertAfter) {
        clonedRow.insertAfter(insertAfter);
    } else {
        clonedRow.insertAfter(originalTableRow);
    }
    $(clonedRow).children().each(function () {
        this.id = randomIdForRow;
        $(this).children().each(function () {
            $(this).attr('data-parentRowId', randomIdForRow)
        });
    });
    var infoBubble = clonedRow.find('.infoBubble');
    var infoIcon = clonedRow.find('.greenInfoBubble');
    $(infoBubble).attr('disabled', true).addClass('removeClicks');
    $(infoIcon).addClass('disabled_questionmark');
    $(infoIcon).removeAttr('onclick').removeAttr('onmouseenter').removeAttr('onmouseleave');

    var newObjectId = mongoObjectIdmongoObjectId();

    $(clonedRow).attr("data-manualidformoving", newObjectId);
    $(clonedRow).find('[data-manualIdContainer="true"]').val(newObjectId);
    $(clonedRow).find('[data-datasetid]').each(function () {
        $(this).attr('data-datasetid', randomIdForDataset)
    })
    $(clonedRow).attr("isACopy", true);

    $(clonedRow).find('.isQuantity').popover({
        placement: 'top',
        template: '<div class="popover"><div class="arrow"></div><div class="popover-content"></div></div>'
    });
    $(clonedRow).find('.just_black').popover({
        placement: 'top',
        template: '<div class="popover"><div class="arrow"></div><div class="popover-content"></div></div>'
    });
    $(clonedRow).find('.serviceLifeButton').popover({
        placement: 'top',
        template: '<div class="popover"><div class="arrow"></div><div class="popover-content"></div></div>'
    });
    $(clonedRow).find('.dropdown').dropdown();
    $(clonedRow).find('.splitChangeLi').remove();
    $(clonedRow).find('.dropdown-menu').children().each(function () {
        $(this).children().each(function () {
        }).attr('data-parentRowId', randomIdForRow)
    });
    $(clonedRow).find('.carbonValue').empty().append("-");
    $(clonedRow).find('.carbonPercentageValue').empty();

    $("#createVariant"+randomIdForRow).addClass("removeClicks");
}

function openRenameResourceModal(tr, entityId, indicatorId, queryId, sectionId, questionId, fieldName, resourceId, resourceName) {

    var queryString = 'parentTableId=' + tr + '&entityId=' + entityId +
        '&indicatorId=' + indicatorId + '&queryId=' + queryId + '&sectionId=' + sectionId + '&questionId=' + questionId + '&resourceName=' + resourceName + '&resourceId=' + resourceId + '&fieldName=' + fieldName;

    $.ajax({
        url: '/app/sec/productDataLists/openChangeResourceNameModal',
        data: queryString,
        type: 'POST',
        success: function (data) {
            openOverlay("myOverlay");
            document.body.className += ' ' + 'noScroll';
            $('#changeResourceNameOverlay').empty().addClass('hidden').append(data).fadeIn('slow').removeClass('hidden');

        }
    });

}

function copyTableRow(tr, verifiedProductFlag, constructionId, entityId, indicatorId, queryId, sectionId, questionId, fieldName, copiedResourceId) {
    var originalTableRow = document.getElementById(tr);
    if (constructionId) {
        var parentIdentifier = $(originalTableRow).attr("data-constructionrow");
        var constituentsToCopy = $('.' + parentIdentifier);

        if (constituentsToCopy.length) {
            var arrayConstituents = Array.from(constituentsToCopy);
            var uniqueConstructionIdentifier = constructionId + guid();
            var clonedConstructionRow = $(originalTableRow).clone();
            handleClonedRow(clonedConstructionRow, originalTableRow, uniqueConstructionIdentifier, true, false, arrayConstituents[arrayConstituents.length - 1]);
            //SW-1882
            if (verifiedProductFlag) {
                removeVerifiedStatus(clonedConstructionRow)
            }

            arrayConstituents.reverse().forEach(function (constituentRow) {
                var clonedConstituentRow = $(constituentRow).clone();
                handleClonedRow(clonedConstituentRow, constituentRow, uniqueConstructionIdentifier, false, true, clonedConstructionRow);
                //SW-1882
                if (verifiedProductFlag) {
                    removeVerifiedStatus(clonedConstituentRow)
                }
            });
        }

        /*

        Old way of copying construction was not copying additional questions, now handled completely with JS, If new explodes revert

        var uniqueConstructionIdentifier = constructionId + guid();
        var resourceTableId = $(originalTableRow).parent().parent().attr('id');
        var selectId = resourceTableId + "Select";
        addResourceFromSelect(entityId, null, null, null, indicatorId, queryId, sectionId, questionId, resourceTableId, fieldName, null, null, null, uniqueConstructionIdentifier, null, copiedResourceId);
        addConstructionResources(constructionId, entityId, null, null, null, indicatorId, queryId, sectionId, questionId, resourceTableId, fieldName, null, null, null, uniqueConstructionIdentifier, null);*/
    } else {
        var clonedRow = $(originalTableRow).clone();
        handleClonedRow(clonedRow, originalTableRow);
        //SW-1882
        if (verifiedProductFlag) {
            removeVerifiedStatus(clonedRow)
        }
        numericInputCheck();
    }
    switchOnTriggerCalculationFlag();
    triggerFormChanged();
    initAdditionalQuestionAutocompletes();
    trackChangesInResourceRows();
}

/**
 * SW-1882
 * this will remove the verified status for buildingProduct resource
 * copied from another verified buildingProduct resource
 */

function removeVerifiedStatus(clonedRow) {

    $(clonedRow).find('.verifyDatasetIconContainer').remove();
    $(clonedRow).removeClass('verifiedDatasetBackground')
    var trId = '#' + $(clonedRow).attr("id")
    $(trId).children().find('[data-isverified="true"]').val('false')
    $(trId).children().find('[data-isverifiedfromentityid="true"]').val('')
}

function guid() {
    function s4() {
        return Math.floor((1 + Math.random()) * 0x10000)
            .toString(16)
            .substring(1);
    }

    return s4() + s4() + '-' + s4() + '-' + s4() + '-' +
        s4() + '-' + s4() + s4() + s4();
}

function resourcesFilteredPopOver(resourceBoxId, filterPopOverContent) {
    $('#resourceBox' + resourceBoxId).popover('destroy');
    var popover = $('#resourceBox' + resourceBoxId).popover({
        placement: 'top',
        html: true,
        content: filterPopOverContent,
        template: "<div class=\"popover\"><div class=\"arrow\"></div><div class=\"helpNote\"><div class=\"popover-content helpNote\" style=\"padding:6px !important;\"></div></div></div>",
        trigger: "manual"

    });
    popover.popover('show');
    setTimeout(function () {
        popover.popover('hide');
    }, 5000);
}

function saveUserFilters(changeView, splitView, constructionView, productDataListView, classificationQuestion, classificationParam) {
    var filterSelected;

    if (changeView || splitView) {
        filterSelected = $('.quickFilterSelectSplit :selected');
    } else {
        filterSelected = $('.quickFilterSelect :selected');
    }

    var resourceAttribute = $(filterSelected).map(function () {
        return $(this).data("attribute");
    }).get();
    var resourceAttributeValue = $(filterSelected).map(function () {
        return $(this).attr("data-resourceAttributeValue");
    }).get();
    var resourceType = $(filterSelected).map(function () {
        return $(this).attr("data-resourceType");
    }).get();
    var subType = $(filterSelected).map(function () {
        return $(this).attr("data-subType");
    }).get();
    var stateForCountry = $(filterSelected).map(function () {
        return $(this).attr("data-state");
    }).get();

    var splitOrChangeFilter = false;

    if (changeView || splitView) {
        splitOrChangeFilter = true;
    }

    var constructionFilter = false;

    if (constructionView) {
        constructionFilter = true;
    }

    var productDataListFilter = false;

    if (productDataListView) {
        productDataListFilter = true;
    }

    var classificationQuestionId = null;

    if (classificationQuestion) {
        classificationQuestionId = classificationQuestion;
    }

    var classificationParamId = null;

    if (classificationParam) {
        classificationParamId = classificationParam;
    }

    var resourceSelectObjects;
    if (changeView) {
        resourceSelectObjects = document.getElementsByClassName("changeAutocomplete");
    } else if (splitView) {
        resourceSelectObjects = document.getElementsByClassName("splitComplete");
    } else {
        resourceSelectObjects = document.getElementsByClassName("autocomplete");
    }

    for (var i = 0; i < resourceSelectObjects.length; i++) {
        var resourceSelectObject = resourceSelectObjects[i];
        $(resourceSelectObject).devbridgeAutocomplete().clear();
    }

    var queryString = '&resourceAttribute=' + resourceAttribute + '&resourceAttributeValue=' + resourceAttributeValue + '&stateForCountry=' + stateForCountry +
        '&resourceType=' + resourceType + '&subType=' + subType + '&splitOrChangeFilter=' + splitOrChangeFilter + '&constructionFilter=' + constructionFilter +
        '&productDataListFilter=' + productDataListFilter + '&classificationQuestionId=' + classificationQuestionId + '&classificationParamId=' + classificationParamId;
    $.ajax({
        url: '/app/saveuserfilters',
        type: 'POST',
        data: queryString
    })
}

function copyMappingToUnidentifiedRow(datasetId, checkbox) {
    var row = $('#' + datasetId);

    if (row.length) {
        if ($(checkbox).is(':checked')) {
            row.addClass('applyMapping');
        } else {
            row.removeClass('applyMapping');
        }
    }
}

function selectAllMappingCheckboxes() {
    var checkBoxes = ".mappingCheckbox";

    if ($("#selectBtn").hasClass("select")) { // if selectBtn has class: select proceed
        $(checkBoxes).each(function () { // check each checkbox and run this function
            if (!$(this).prop("checked")) { //if the box does NOT have the tag "checked"
                $(this).prop("checked", true).trigger('change'); //turn on checkbox and trigger the change
            }
        });
        $("#selectBtn").removeClass("select").addClass("deselect"); //remove select class and add deselect class
    } else if ($("#selectBtn").hasClass("deselect")) { // if none of the above applies and selectBtn has a class called: deselect, proceed with below
        $(checkBoxes).each(function () { // check each checkbox and run this function
            if ($(this).prop("checked")) { //if the box DOES have the tag "checked"
                $(this).prop("checked", false).trigger('change'); //turn off checkbox and trigger the change
            }
        });
        $("#selectBtn").removeClass("deselect").addClass("select"); //remove deselect class and add select class - creating a loop
    }

}

function formatAutocompleteGroups(suggestion, category) {
    if (category) {
        if (suggestion.subType || !suggestion.data.count) {
            return '<div style="margin-top: 3px; margin-bottom: 3px;" class="autocomplete-group"><strong style="margin-left: 28px;">' + category + '</strong></div>';
        } else if (suggestion.data.count || suggestion.data.info) {
            if (suggestion.data.weight == 8) {
                return '<div style="margin-top: 3px; margin-bottom: 3px;" class="autocomplete-group"><strong style="margin-left: 35px;"><i class="fas fa-star"></i> ' + category + ' (' + suggestion.data.count + ')</strong> - ' + suggestion.data.info + '</div>';
            }
            return '<div style="margin-top: 3px; margin-bottom: 3px;" class="autocomplete-group"><strong style="margin-left: 35px;">' + category + ' (' + suggestion.data.count + ')</strong> - ' + suggestion.data.info + '</div>';
        }
    } else {
        return ''
    }
}

function formatAutocompleteRows(suggestion, entity, indicator, question, autocompleteValue, query, queryPage) {

    var entityId = entity ? entity : "";
    var indicatorId = indicator ? indicator : "";
    var questionId = suggestion.questionId ? suggestion.questionId : "";
    var queryId = query ? query : "null";
    var returnable;

    var message = "no information available";
    var infoId = guid() + "info";
    var additionalFlag = "";
    var fromQueryPage = queryPage ? queryPage : '';
    var dataSourceImg = ""
    if (suggestion.img2) {
        additionalFlag = '<img src="' + suggestion.img2 + '" width="20" alt="kuva" onerror="this.onerror=null;this.src=\'/app/assets/isoflags/globe.png\'" class="flagIso"/>'
    }
    if (suggestion.dataSource) {
        if (suggestion.dataSource == "private") {
            dataSourceImg = "<i class='far fa-eye-slash fiveMarginRight' aria-hidden='true'></i>"
        } else if (suggestion.dataSource == "plant") {
            dataSourceImg = "<i class='fa fa-industry fiveMarginRight' aria-hidden='true'></i>"
        }
    }
    var expiredDataPointIcon = "";
    if (suggestion.expiredDataPoint) expiredDataPointIcon = "<img src='/app/assets/img/iconExpiredBig.png' class='expiredIcon'/>";

    var warning = "";

    if (suggestion.resourceQualityWarning) {
        if ("WARN" === suggestion.resourceQualityWarning) {
            warning = "<img src='/app/assets/img/icon-warning.png' style='width:15px; padding-left: 2px; padding-bottom: 5px'>";
        } else if ("BLOCK" === suggestion.resourceQualityWarning) {
            warning = "<i style='color: red; margin-left: 3px; font-size: 1.25em;' class='far fa-times-circle'></i>";
        }
    } else if (suggestion.warning) {
        warning = "<img src='/app/assets/img/icon-warning.png' style='width:15px; padding-left: 2px; padding-bottom: 5px'>";
    }


    if (suggestion.subType) {
        returnable = '<div style="cursor: pointer;"><b>' + suggestion.vvalue + '</b></div>';
    } else {
        if (suggestion.data) {
            var resourceId = suggestion.data.resource;
            var profileId;
            if (resourceId && resourceId.indexOf(".") !== -1) {
                profileId = resourceId.split(".")[1];
                resourceId = resourceId.split(".")[0];
            }

            if (!resourceId && autocompleteValue) {
                resourceId = autocompleteValue;
                resourceId = resourceId.split(" ")[0];

                if (resourceId && resourceId.indexOf(".") !== -1) {
                    profileId = resourceId.split(".")[1];
                    resourceId = resourceId.split(".")[0];
                }
            }

            var removeOrReportMappingLink = "";

            if (suggestion.trainingDataId && suggestion.trainingDataRemoveOrReport && suggestion.datasetId) {
                removeOrReportMappingLink = ' <span> - <a href="javascript:" class="margin-left-5 copySourceListingName" onclick=\'removeOrReportMapping(this, "' + suggestion.trainingDataId + '", "' + suggestion.datasetId + '");stopBubblePropagation(event);\'>' + suggestion.trainingDataRemoveOrReport + '</a></span>'
            }

            var reportedAsBad = "";
            if (suggestion.reportedAsBad) {
                reportedAsBad = ' <span><i class="icon-alert" rel="popover" data-trigger="hover" data-content="' + suggestion.reportedAsBad + '"></i></span>'
            }
            var addToCompare = "";
            if (fromQueryPage && suggestion.addToCompare == "true") {
                addToCompare = '<span rel="popover" data-trigger="hover" data-content="Check to add material to data comparison list"><input type="checkbox" onclick="stopBubblePropagation(event);" class="resourceToCompare" onchange="addToCompareResource(this)" data-target="' + questionId + 'CompareButton" value="' + resourceId + '.' + profileId + '.' + questionId + '.' + queryId + '"/></span> '
            }
            var multipartImg = ""
            if(suggestion.multipartImage){
                multipartImg = suggestion.multipartImage
            }
            var brandImage = ""
            if(suggestion.brandImage){
                brandImage = suggestion.brandImage
            }
            if (suggestion.isUser) {
                if (suggestion.constructionImage) {
                    returnable = '<div style="cursor: pointer;"><img src="' + suggestion.img + '" width="20" alt="kuva" class="flagIso"/>' + additionalFlag + suggestion.constructionImage + dataSourceImg + suggestion.vvalue + ' &nbsp; ' + suggestion.battery + expiredDataPointIcon + warning + brandImage + ' <a href="javascript:" class="infoBubble" id="' + infoId + '" onclick=\'openSourceListing("' + indicatorId + '", "' + resourceId + '", "' + questionId + '","' + profileId + '", "' + entityId + '", "' + true + '", "' + infoId + '", "' + message + '", "modalPopover","' + queryId + '", null, null, "top",' + fromQueryPage + ');stopBubblePropagation(event);\'><i class="fa fa-question greenInfoBubble"></i></a>' + removeOrReportMappingLink + reportedAsBad + '</div>';
                } else {
                    if (suggestion.img) {
                        returnable = '<div style="cursor: pointer;">' + addToCompare + '<img src="' + suggestion.img + '" width="20" alt="kuva" class="flagIso"/>' + additionalFlag + multipartImg + suggestion.genericResourceImg + dataSourceImg + suggestion.vvalue + ' &nbsp; ' + suggestion.battery + expiredDataPointIcon + warning + brandImage +  ' <a href="javascript:" class="infoBubble" id="' + infoId + '" onclick=\'openSourceListing("' + indicatorId + '", "' + resourceId + '", "' + questionId + '","' + profileId + '", "' + entityId + '", "' + true + '", "' + infoId + '", "' + message + '", "modalPopover","' + queryId + '", null, null, "top", ' + fromQueryPage + ');stopBubblePropagation(event);\'><i class="fa fa-question greenInfoBubble"></i></a>' + removeOrReportMappingLink + reportedAsBad + '</div>';
                    } else {
                        returnable = '<div style="cursor: pointer;"> ' + suggestion.vvalue + '</div>';
                    }
                }
            } else {
                if (suggestion.constructionImage) {
                    returnable = '<div style="cursor: pointer;" ><img src="' + suggestion.img + '" width="20" alt="kuva" class="flagIso"/>' + additionalFlag + suggestion.constructionImage + dataSourceImg + suggestion.vvalue + ' &nbsp; ' + suggestion.battery + expiredDataPointIcon + warning + removeOrReportMappingLink + reportedAsBad + brandImage + '</div>';
                } else {
                    if (suggestion.img) {
                        returnable = '<div style="cursor: pointer;"><img src="' + suggestion.img + '" width="20" alt="kuva" class="flagIso"/>' + additionalFlag + multipartImg + suggestion.genericResourceImg + dataSourceImg + suggestion.vvalue + ' &nbsp; ' + suggestion.battery + expiredDataPointIcon + warning + removeOrReportMappingLink + reportedAsBad + brandImage + '</div>';
                    } else {
                        returnable = '<div style="cursor: pointer;"> ' + suggestion.vvalue + '</div>';
                    }
                }
            }

        } else {
            returnable = '<div style="cursor: pointer;"> ' + suggestion.vvalue + '</div>';
        }
    }

    return returnable;
}

function removeOrReportMapping(element, trainingDataId, autocompleteId) {
    if (trainingDataId) {
        var elem = $(element);
        var autocomplete = $("#autocomplete" + autocompleteId);

        $.ajax({
            async: false, type: 'POST',
            data: 'trainingDataId=' + trainingDataId,
            url: '/app/sec/importMapper/removeOrReportTrainingData',
            success: function (data, textStatus) {
                $(elem).parent().html('').append(data.returnable.status);

                if ("true" === data.returnable.remove) {
                    $(autocomplete).attr("data-alwaysShowFullDB", true);
                    $(autocomplete).blur();
                    var userQuery = $(autocomplete).val();

                    if ($(autocomplete).attr("data-hasUserInput") === "false") {
                        userQuery = "";
                    }

                    setTimeout(function () {
                        $(autocomplete).val('+importMapperShowAll__' + userQuery).devbridgeAutocomplete("onValueChange");
                        $(autocomplete).val(userQuery);
                        $(autocomplete).trigger('focus');
                    }, 750);
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
            }
        });
    }
}

function handleAutocompleteNextSkip(element, suggestion) {
    var originalQuery = suggestion.originalQuery;
    var searchedWith = suggestion.searchedWith;
    $(element).val("");
    $(element).blur();

    if (originalQuery) {
        var list = originalQuery.split("__");
        if (list.length >= 3) {
            if (!list[2]) {
                list[2] = "null";
            }
            if (list.length === 4) {
                list[3] = suggestion.nextSkip;
            } else {
                list.push(suggestion.nextSkip)
            }
        }
        originalQuery = list.join("__");
    } else {
        // Straight up showing one subtype etc.
        originalQuery = "+skipShow__" + suggestion.nextSkip;
        if (searchedWith) {
            originalQuery = originalQuery + "__" + searchedWith;
        }
    }

    setTimeout(function () {
        $(element).val(originalQuery).devbridgeAutocomplete("onValueChange");
        $(element).val("");
        $(element).trigger('focus');
    }, 150);
}

function handleAutocompleteOnSelect(suggestion, element, importMapper, construction, split, remapper, additionalQuestionSelect) {
    if (suggestion.notFound) {
        $(element).val("");
        return false;
    } else if (suggestion.subType) {
        $(element).val("");
        $(element).blur();
        setTimeout(function () {
            var subType = suggestion.subType;
            var searchedWith = suggestion.searchedWith;
            $(element).val('+subtypesearch__' + subType + '__' + searchedWith).devbridgeAutocomplete("onValueChange");
            $(element).val("");
            $(element).trigger('focus');
        }, 150);
        materialAdded = true
    } else if (importMapper) {
        if (suggestion.nextSkip) {
            handleAutocompleteNextSkip(element, suggestion);
        } else {
            if (suggestion.importMapperShowAll === "true") {
                var searchedWith = suggestion.searchedWith;
                if ($(element).attr("data-hasUserInput") === "false") {
                    searchedWith = "";
                }
                $(element).attr("data-alwaysShowFullDB", true);
                $(element).val("");
                $(element).blur();
                setTimeout(function () {
                    $(element).val('+importMapperShowAll__' + searchedWith).devbridgeAutocomplete("onValueChange");
                    $(element).val(searchedWith);
                }, 150);
            } else {
                var row = $(element).closest('tr');
                var resourceId = $(row).find('.resourceIdContainer');
                var parsedResourceId = suggestion.data.resource;
                var units = suggestion.units;
                var profileId;

                if (parsedResourceId.indexOf(".") != -1) {
                    profileId = parsedResourceId.split(".")[1];
                    parsedResourceId = parsedResourceId.split(".")[0];

                }
                $(resourceId).val(parsedResourceId);
                var div = $(element).closest('div');
                $(div).attr('class', 'input-append');
                removeClassFromParent(element, 'notIdentified');
                $(element).removeClass("redBorder");
                var detailsObject = $(row).find(".detailsLink")[0];
                $(row).find(".resourceUnitsContainer").val(units);
                var idOfDetailsObject = $(detailsObject).attr("id");
                unhoverDetails(idOfDetailsObject);
                if ($(element).attr("data-targetBubble")) {
                    updateSourceListing($(element).attr("data-indicatorId"), parsedResourceId, $(element).attr("data-questionId"), profileId, $(element).attr("data-entityId"), true, $(element).attr("data-targetBubble"));
                }
                var suggestSameMappingForAlreadyIdentified = true;
                var weight = suggestion.weight;
                var weightShort = suggestion.weightShort;
                var mappingElem = $(element).closest("td").siblings('.mappingBasis')
                if ($(mappingElem).length > 0) {
                    $(mappingElem).text(weightShort);
                    if (weight.length > 0) {
                        var newPopover = ' <i class="fa fa-question-circle" rel="popover" data-content="' + weight + '" data-trigger="hover"></i>'
                        $(mappingElem).append(newPopover);
                        $("[rel='popover']").popover({
                            placement: "top",
                            template: '<div class="popover"><div class="arrow"></div><div class="popover-content"></div></div>'
                        })
                    }
                }
                if ($(element).hasClass('remapAlreadyMapped')) {
                    var hiddenField = $('#identifiedResourceId' + $(element).attr('data-datasetId'));

                    if (hiddenField.length) {
                        if ($(element).hasClass('retrainForThisUser')) {
                            suggestSameMappingForAlreadyIdentified = false;
                        } else {
                            $(element).addClass('retrainForThisUser');
                        }
                    }
                }

                var status = row.find('.mappingStatus');
                $(element).val(suggestion.vvalue);
                if (status.length) {
                    // This material is unidentified
                    $('#notIdentifiedSaveMappings').attr('disabled', false);
                    status.text('');
                    status.append("<i class=\"icon-done\" style=\"margin-top: 10px; margin-left: 10px; margin-right: -10px;\"></i>");
                    $(element).addClass('notIdentifiedSaveMapping');
                    applySameMapping($(element).val(), row, parsedResourceId, profileId, units);
                } else if (suggestSameMappingForAlreadyIdentified) {
                    $(row).find('.noTrainingDataset').remove();
                    $(row).find('.rememberMappingCheckbox').show();
                    $(element).addClass('retrainForThisUser');
                    applySameMapping($(element).val(), row, parsedResourceId, profileId, units, true);
                }

                if(row.find('.identifiedResourceIdContainer').length) {
                    // This material is identified but was changed
                    $('#notIdentifiedSaveMappings').attr('disabled', false);
                }
            }
        }
    } else if (construction) {
        var resourceId = $(element).siblings('input:hidden:first');
        var parsedResourceId = suggestion.data.resource;

        if (parsedResourceId.indexOf(".") !== -1) {
            parsedResourceId = parsedResourceId.split(".")[0];

        }
        $(resourceId).val(parsedResourceId);
        addResourcesToConstructions(parsedResourceId, $(element).attr('data-resourceTableId'), $(element).attr('data-queryId'), $(element).attr('data-questionId'), $(element).attr('data-sectionId'));
    } else if (remapper) {
        var resourceAndProfileId = $(element).siblings('input:hidden:first');
        $(resourceAndProfileId).val(suggestion.data.resource);
        $(element).val(suggestion.vvalue);
        /* REL-304 deprecate nmd3ElementId
    } else if (suggestion.nmd3ElementId) {
        var uniqueConstructionIdentifier = suggestion.constructionId + guid();
        openNMDProductAddModal(suggestion.nmd3ElementId, suggestion.constructionId, uniqueConstructionIdentifier, $(element).attr("data-entityId"), $(element).attr("data-selectId"),
            $(element).attr("data-indicatorId"), $(element).attr("data-queryId"), $(element).attr("data-sectionId"), $(element).attr("data-questionId"), $(element).attr("data-resourceTableId"), $(element).attr("data-fieldName"));
     */
    } else if (suggestion.resourceQualityWarning && !split) {
        var parsedResourceId = suggestion.data.resource;
        var profileId;

        if (parsedResourceId.indexOf(".") != -1) {
            profileId = parsedResourceId.split(".")[1];
            parsedResourceId = parsedResourceId.split(".")[0];
        }
        openResourceQualityWarningSwal(parsedResourceId, profileId, $(element).attr("data-queryId"),
            $(element).attr("data-sectionId"), $(element).attr("data-questionId"),
            $(element).attr("data-entityId"), $(element).attr("data-selectId"),
            $(element).attr("data-indicatorId"),
            $(element).attr("data-resourceTableId"), $(element).attr("data-fieldName"), suggestion.productDataListId,
            suggestion.constructionId, suggestion.constructionId + guid(), suggestion.vvalue, element, split);
    } else if (suggestion.nextSkip) {
        handleAutocompleteNextSkip(element, suggestion);
    } else if (additionalQuestionSelect) {
        var parsedResourceId = suggestion.data.resource;
        var profileId;

        if (parsedResourceId.indexOf(".") != -1) {
            profileId = parsedResourceId.split(".")[1];
            parsedResourceId = parsedResourceId.split(".")[0];
        }
        $(element).parent().find('.additionalQuestionAutocompleteHiddenInput').val(parsedResourceId);
        $(element).val(suggestion.vvalue);
    } else {
        var singleResource = $(element).attr("data-singleresourceselect");
        $(element).attr("data-resourceId", suggestion.data.resource);
        $(element).prop('required', false);

        if ("true" === singleResource) {
            var resourceId = $(element).siblings('input:hidden:first');
            var parsedResourceId = suggestion.data.resource;
            var profileId;

            if (parsedResourceId.indexOf(".") != -1) {
                profileId = parsedResourceId.split(".")[1];
                parsedResourceId = parsedResourceId.split(".")[0];
            }
            $(resourceId).val(parsedResourceId);
            $(element).trigger('change');
            $(element).val(suggestion.vvalue);
            updateSourceListing($(element).attr("data-indicatorId"), parsedResourceId, $(element).attr("data-questionId"), profileId, $(element).attr("data-entityId"), true, $(element).attr("data-targetBubble"));
        } else if (suggestion.constructionId) {
            var uniqueConstructionIdentifier = suggestion.constructionId + guid();
            addResourceFromSelect($(element).attr("data-entityId"), $(element).attr("data-quarterlyInputEnabled"),
                $(element).attr("data-monthlyInputEnabled"), $(element).attr("data-selectId"), $(element).attr("data-indicatorId"),
                $(element).attr("data-queryId"), $(element).attr("data-sectionId"), $(element).attr("data-questionId"), $(element).attr("data-resourceTableId"),
                $(element).attr("data-fieldName"), $(element).attr("data-preventDoubleEntries"), $(element).attr("data-showGWP"),
                $(element).attr("data-doubleEntryWarning"), uniqueConstructionIdentifier, $(element).attr('data-accountId'));
            if (split) {
                $(element).val(suggestion.vvalue);
                $("#" + $(element).attr("data-resourceTableId")).find("input[data-parentuniqueconstructionidentifier='" + uniqueConstructionIdentifier + "']").prop("readonly", false)
            } else {
                addConstructionResources(suggestion.constructionId, $(element).attr("data-entityId"), $(element).attr("data-quarterlyInputEnabled"),
                    $(element).attr("data-monthlyInputEnabled"), $(element).attr("data-selectId"), $(element).attr("data-indicatorId"),
                    $(element).attr("data-queryId"), $(element).attr("data-sectionId"), $(element).attr("data-questionId"), $(element).attr("data-resourceTableId"),
                    $(element).attr("data-fieldName"), $(element).attr("data-preventDoubleEntries"), $(element).attr("data-showGWP"),
                    $(element).attr("data-doubleEntryWarning"), uniqueConstructionIdentifier, $(element).attr('data-accountId'));
            }
        } else {
            addResourceFromSelect($(element).attr("data-entityId"), $(element).attr("data-quarterlyInputEnabled"),
                $(element).attr("data-monthlyInputEnabled"), $(element).attr("data-selectId"), $(element).attr("data-indicatorId"),
                $(element).attr("data-queryId"), $(element).attr("data-sectionId"), $(element).attr("data-questionId"), $(element).attr("data-resourceTableId"),
                $(element).attr("data-fieldName"), $(element).attr("data-preventDoubleEntries"), $(element).attr("data-showGWP"),
                $(element).attr("data-doubleEntryWarning"), "", $(element).attr('data-accountId'), "", $(element).attr('data-originalDatasetAnswers'),
                $(element).attr('data-persistChangedManualId'), null, null, null, suggestion.productDataListId);

            if (split) {
                $(element).val(suggestion.vvalue);
            }
        }
    }
    destroyInfoPopovers();
}

function openResourceQualityWarningSwal(resourceId, profileId, queryId, sectionId, questionId, entityId, selectId,
                                        indicatorId, resourceTableId, fieldName, productDataListId,
                                        constructionId, uniqueConstructionIdentifier, vvalue, element, split) {
    var warningType, qualityWarningText, yes, cancel, heading;

    $.ajax({
        async: false, type: 'GET', dataType: "json",
        data: 'resourceId=' + resourceId + '&profileId=' + profileId + '&questionId=' + questionId + '&entityId='
            + entityId + '&indicatorId=' + indicatorId  + '&sectionId=' + sectionId  + '&queryId=' + queryId,
        url: '/app/sec/util/resourceQualityWarning',
        success: function (data, textStatus) {
            warningType = data.warningType
            qualityWarningText = data.warning;
            yes = data.localizedYes;
            cancel = data.localizedCancel;
            heading = data.localizedHeading;
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });

    if (qualityWarningText) {
        if ("WARN" === warningType) {
            Swal.fire({
                title: heading,
                html: qualityWarningText,
                icon: "warning",
                confirmButtonText: yes,
                cancelButtonText: cancel,
                confirmButtonColor: "red",
                showCancelButton: true,
                reverseButtons: true,
                showLoaderOnConfirm: true,
                allowOutsideClick: false
            }).then(result => {
                if (!result.dismiss) {
                    if (constructionId) {
                        addResourceFromSelect(entityId, null, null, selectId, indicatorId,
                            queryId, sectionId, questionId, resourceTableId, fieldName, null, true,
                            null, uniqueConstructionIdentifier, null, null,
                            null, null, resourceId, null, null, productDataListId, null);

                        if (split) {
                            $(element).val(vvalue);
                            $("#" + resourceTableId).find("input[data-parentuniqueconstructionidentifier='" + uniqueConstructionIdentifier + "']").prop("readonly", false)
                        } else {
                            addConstructionResources(constructionId, entityId, null, null, selectId, indicatorId,
                                queryId, sectionId, questionId, resourceTableId, fieldName, null, true,
                                null, uniqueConstructionIdentifier, null);
                        }
                    } else {
                        addResourceFromSelect(entityId, null, null, selectId, indicatorId,
                            queryId, sectionId, questionId, resourceTableId, fieldName, null, true,
                            null, null, null, null,
                            null, null, resourceId, null, null, productDataListId, null);
                    }
                }
            });
        } else if ("BLOCK" === warningType) {
            Swal.fire({
                title: heading,
                html: qualityWarningText,
                icon: "warning",
                confirmButtonText: cancel,
                confirmButtonColor: "gray",
                showCancelButton: false,
                allowOutsideClick: false
            }).then(result => {
                // bye
            });
        }
    }
}

function removeElementById(elementId) {
    if (elementId) {
        elementId = "#" + elementId;
        $(elementId).remove();
    }
}

function renderResourceProfiles(resourceSelectId, profileSelectId) {
    if (resourceSelectId && profileSelectId) {
        resourceSelectId = "#" + resourceSelectId;
        profileSelectId = "#" + profileSelectId;
        var resourceId = $(resourceSelectId).val();

        if (resourceId) {
            $.ajax({
                async: false, type: 'POST',
                data: 'resourceId=' + resourceId,
                url: '/app/profiles',
                success: function (data, textStatus) {
                    $(profileSelectId).children().remove();
                    $(profileSelectId).append(data.output);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                }
            });
        } else {
            $(profileSelectId).children().remove()
        }
    }
}

/**
 * Check the numeric value if it is within the minAllowed and maxAllowed (if any)
 * @param input
 * @param inputLabel
 * @param minAllowed
 * @param maxAllowed
 * @param inputWidth
 * @param {boolean} resetToBoundary true if need to reset value to the closest boundary (min or max)
 * @param {string} resetMsg localized msg telling value is reset
 */
function checkValue(input, inputLabel, minAllowed, maxAllowed, inputWidth, resetToBoundary, resetMsg) {
    if (input.value) {
        var value = Number(input.value.replace(",", "."));
        var alertMessage;

        if (isNaN(value)) {
            alertMessage = getValidationNotNumericMsg(inputLabel);
        } else {
            if (isDefined(minAllowed) && isDefined(maxAllowed) && (value < minAllowed || value > maxAllowed)) {
                alertMessage = getValidationNotInLimitsMsg(inputLabel, minAllowed, maxAllowed);
            } else if (isDefined(minAllowed) && value < minAllowed) {
                alertMessage = getValidationMinValueMsg(inputLabel, minAllowed);
            } else if (isDefined(maxAllowed) && value > maxAllowed) {
                alertMessage = getValidationMaxValueMsg(inputLabel, maxAllowed);
            }
        }

        if (inputWidth) {
            inputWidth = inputWidth + 'px';
            $(input).css('width', inputWidth);
        }

        if (alertMessage) {
            if (resetToBoundary) {
                alertMessage = resetValueToBoundary(input, value, minAllowed, maxAllowed, alertMessage, resetMsg)
            }

            $(input).css('border', '1px solid red');
            var td = $(input).closest('td');

            $(td).addClass('valueConstraintTip valueConstraintTip--right answerError');
            $(td).attr('data-tooltip', alertMessage);
            $(input).off('click').on('click', function (e) {
                $(td).removeClass('valueConstraintTip valueConstraintTip--right answerError');
                $(td).removeAttr('data-tooltip');
            });

        } else {
            input.value = value
            $(input).css('border', '');
        }
    }
}

/**
 * Reset value to the boundary limit (min or max)
 * @param input
 * @param value
 * @param minAllowed
 * @param maxAllowed
 * @param alertMessage
 * @param resetMsg
 * @returns {string} alert msg with added reset msg
 */
function resetValueToBoundary(input, value, minAllowed, maxAllowed, alertMessage, resetMsg) {
    if (!isDefined(value)) {
        // nothing to run
        return alertMessage
    }

    if (isDefined(minAllowed) && value < minAllowed) {
        $(input).val(minAllowed)
        alertMessage += '. ' + resetMsg
    } else if (isDefined(maxAllowed) && value > maxAllowed) {
        $(input).val(maxAllowed)
        alertMessage += '. ' + resetMsg
    }
    return alertMessage
}

function addLanguageInput(languageSelect, inputName, inputType, divToAdd) {
    var language = document.getElementById(languageSelect).value;
    divToAdd = "#" + divToAdd;
    var inputnameAndLanguage = '#' + inputName + '\\[' + language + '\\]';

    if (!($(inputnameAndLanguage).length)) {
        if ("textField" == inputType) {
            $.ajax({
                type: 'POST', data: 'language=' + language + '&name=' + inputName, url: '/app/languagetextfield',
                success: function (data, textStatus) {
                    $(divToAdd).append(data.output);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                }
            });
        } else if ("textArea" == inputType) {
            $.ajax({
                type: 'POST', data: 'language=' + language + '&name=' + inputName, url: '/app/languagetextarea',
                success: function (data, textStatus) {
                    $(divToAdd).append(data.output);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {

                }
            });
        }
    } else {
        alert("Language already given");
    }
}

var delay = (function () {
    var timer = 0;
    return function (callback, ms) {
        clearTimeout(timer);
        timer = setTimeout(callback, ms);
    };
})();

var loadingImg = "<img src=\"/app/assets/animated_loading_icon.gif\" alt=\"\" style=\"height: 16px; padding: 0\" />";
var ib = 0;

function showAllAdditionalQuestionResources(showAllButton) {
    if (showAllButton) {
        var object = $(showAllButton).siblings('input:text:first');

        if (object) {
            var options = {
                minChars: 0
            };
            $(object).devbridgeAutocomplete().setOptions(options);
            $(object).val("").devbridgeAutocomplete("onValueChange");
        }

        $(showAllButton).blur(function () {
            $(object).blur();
        });
    }

}

function initAdditionalQuestionAutocompletes() {
    var autocompletes = document.getElementsByClassName("additionalQuestionAutocomplete");

    if (autocompletes) {
        for (var i = 0; i < autocompletes.length; i++) {
            var object = autocompletes[i];
            var entityId = $(object).attr("data-entityId");
            var indicatorId = $(object).attr("data-indicatorId");
            var queryId = $(object).attr("data-queryId");
            var sectionId = $(object).attr("data-sectionId");
            var questionId = $(object).attr("data-questionId");
            var additionalQuestionId = $(object).attr("data-additionalQuestionId");
            $(object).devbridgeAutocomplete({
                serviceUrl: '/app/sec/util/getAdditionalQuestionResourcesAsJson',
                groupBy: 'category',
                minChars: 3,
                deferRequestBy: 1000,
                containerId: questionId + "AutocompleteSuggestions",
                params: {
                    queryId: queryId,
                    sectionId: sectionId,
                    indicatorId: indicatorId,
                    questionId: questionId,
                    entityId: entityId,
                    additionalQuestionId: additionalQuestionId
                },
                formatResult: function (suggestion) {
                    return formatAutocompleteRows(suggestion, entityId, indicatorId, questionId, null, queryId);
                },
                onSelect: function (suggestion) {
                    handleAutocompleteOnSelect(suggestion, $(this), null, null, null, null, true);
                    triggerFormChanged();

                    if (typeof triggerCalculationIfNeededForAutocompleteSelect == 'function') {
                        triggerCalculationIfNeededForAutocompleteSelect(this)
                    }
                },
                onSearchStart: function (query) {
                    var showAllButton = $(this).siblings('a:first');
                    $(showAllButton).children().removeClass("icon-chevron-down");
                    $(showAllButton).children().empty();
                    $(showAllButton).children().append(loadingImg);
                },
                zIndex: autocompleteZIndex,

                onSearchComplete: function (query, suggestions) {
                    var showAllButton = $(this).siblings('a:first');
                    var dataHitbutton = $(showAllButton).siblings('a:first');
                    dataHitbutton.removeClass('hidden');
                    $(showAllButton).children().empty();
                    $(showAllButton).children().addClass("icon-chevron-down");

                    var options = {
                        minChars: 3,
                        deferRequestBy: 1000,
                        noCache: true
                    };
                    var that = $(this).devbridgeAutocomplete().suggestionsContainer;
                    if ($(that).length) {
                        $(that).scrollTop(0);
                    }
                    $(this).devbridgeAutocomplete().setOptions(options);
                },
                ajaxSettings: {
                    success: function(data) {
                        setFlashForAjaxCall(JSON.parse(data))
                    },
                    error: function(data) {
                        setFlashForAjaxCall(JSON.parse(data))
                    }
                }
            });
        }
    }
}

function initQueryAutocompletes() {
    initResourceSelects();
    var autocompletes = document.getElementsByClassName("autocomplete");

    if (autocompletes) {
        for (var i = 0; i < autocompletes.length; i++) {
            var object = autocompletes[i];
            var entityId = $(object).attr("data-entityId");
            var indicatorId = $(object).attr("data-indicatorId");
            var queryId = $(object).attr("data-queryId");
            var sectionId = $(object).attr("data-sectionId");
            var questionId = $(object).attr("data-questionId");
            var accountId = $(object).attr("data-accountId");
            var fromQueryPage = "true";
            $(object).devbridgeAutocomplete({
                serviceUrl: '/app/jsonresources',
                groupBy: 'category',
                minChars: 3,
                deferRequestBy: 1000,
                containerId: questionId + "AutocompleteSuggestions",
                params: {
                    queryId: queryId,
                    sectionId: sectionId,
                    indicatorId: indicatorId,
                    questionId: questionId,
                    entityId: entityId,
                    privateDatasetAccountId: accountId
                },
                ajaxSettings: {
                    success: function(data) {
                        setFlashForAjaxCall(JSON.parse(data))
                    },
                    error: function(data) {
                        setFlashForAjaxCall(JSON.parse(data))
                    }
                },
                formatResult: function (suggestion) {
                    return formatAutocompleteRows(suggestion, entityId, indicatorId, questionId, null, queryId, fromQueryPage);
                },
                formatGroup: function (suggestion, category) {
                    return formatAutocompleteGroups(suggestion, category);
                },
                onSelect: function (suggestion) {
                    handleAutocompleteOnSelect(suggestion, $(this));
                },
                onSearchStart: function (query) {
                    var showAllButton = $(this).siblings('a:first');
                    $(showAllButton).children().removeClass("icon-chevron-down");
                    $(showAllButton).children().empty();
                    $(showAllButton).children().append(loadingImg);
                },
                zIndex: autocompleteZIndex,

                onSearchComplete: function (query, suggestions) {
                    var showAllButton = $(this).siblings('a:first');
                    var dataHitbutton = $(showAllButton).siblings('a:first');
                    dataHitbutton.removeClass('hidden');
                    $(showAllButton).children().empty();
                    $(showAllButton).children().addClass("icon-chevron-down");

                    if (suggestions.length > 1) {
                        var showInfoText = suggestions[0].showInfoText;

                        var infotextcontainerid = $(this).attr("data-infotextcontainerid");

                        if (showInfoText) {
                            $('#' + infotextcontainerid).empty().removeClass("hidden").append("<span class=\"alert\" style=\"color: black !important;\">" + showInfoText + "</span>")
                        }

                        var totalAmount = suggestions[0].totalAmountOfResources;
                        if (totalAmount) {
                            $(dataHitbutton).append('&nbsp;' + totalAmount);
                            setTimeout(function () {
                                $(dataHitbutton).empty();
                            }, 3000);
                        } else {
                            $(dataHitbutton).append('&nbsp;' + suggestions.length);
                            setTimeout(function () {
                                $(dataHitbutton).empty();
                            }, 3000);
                        }
                    } else {
                        $(dataHitbutton).addClass('hidden');
                    }
                    var options = {
                        minChars: 3,
                        deferRequestBy: 1000,
                        noCache: true
                    };
                    var quickFilters = $('#quickFilterOptions');
                    if (suggestions.length == 1) {
                        options = {
                            minChars: 0,
                            deferRequestBy: 1000,
                            autoSelectFirst: true
                        };

                        var notFound = $('.notFoundMessage');
                        if (notFound.length > 0) {
                            var valueFilter = $(".filterChosenHighlight")
                            $(notFound).addClass('warningHighlightPermanent');
                            if (valueFilter.length > 0) {
                                $(quickFilters).addClass('warningHighlightPermanent');
                            } else {
                                $(quickFilters).removeClass('warningHighlightPermanent');
                            }
                        } else {
                            $(notFound).removeClass('warningHighlightPermanent');
                            $(quickFilters).removeClass('warningHighlightPermanent');
                        }
                    } else {
                        $(quickFilters).removeClass('warningHighlightPermanent');
                    }
                    var that = $(this).devbridgeAutocomplete().suggestionsContainer;
                    if ($(that).length) {
                        $(that).scrollTop(0);
                    }
                    $(this).devbridgeAutocomplete().setOptions(options);
                    $("[rel=popover]").popover()
                }
            });
        }
    }
}

function stopBubblePropagation(event) {
    if (event) {
        event.stopPropagation();
    }
}

/**
 * Currently only available for nmd 3 constructions, otherwise return 1 (same as no factor)
 */
function getConstructionConversionFactor(quantityElement, currentUnit) {
    if (quantityElement && currentUnit) {
        const sourceUnit = $(quantityElement).attr('data-constructionSourceUnitConversionFactor')
        const factor = $(quantityElement).attr('data-constructionUnitConversionFactor')
        if (sourceUnit && factor && currentUnit !== sourceUnit) {
            // only multiply the factor for constituents if unit is not same as sourceUnit
            return parseFloat(factor)
        }
    }
    return 1 // no factor
}

function multiplyConstructionResources(uniqueConstructionIdentifier, element, unitFieldId) {
    var thisElem = $(element);
    var row = thisElem.closest('tr');
    var quantitys = $('*[data-uniqueConstructionIdentifier="' + uniqueConstructionIdentifier + '"]').filter('.isQuantity');
    var quantityInput = row.find('.isQuantity');
    var userInput = parseFloat($(quantityInput).val().replace(",", "."));
    var scalingType = $(row.find('.scalingType')).val();

    var density = quantityInput.attr('data-defaultdensity');
    var thickness_mm = parseFloat(row.find("input[name*='additional_thickness_mm.']").val());
    var unit = row.find('#' + unitFieldId).val();

    // constructionConversionFactor is 1 if there's no factor, currently only few NMD 3.0 constructions might have it
    const constructionConversionFactor = getConstructionConversionFactor(element, unit)

    if (unit && $.inArray(unit, ["cu yd", "cu ft", "sq ft", "lbs", "ft"]) > -1) {
        $.ajax({
            async: false,
            data: 'userGivenUnit=' + unit + '&quantity=' + userInput + '&thickness_mm=' + thickness_mm + '&density=' + density,
            url: '/app/sec/construction/convertImperialMultiplierToEuropean',
            type: 'GET',
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                if (data.output) {
                    userInput = parseFloat(data.output);
                }
            }
        });
    }

    if (scalingType) {
        // NMD 2.3
        var scalingTypeInt = parseInt(scalingType);
        var productScalingFactor = null;

        if (2 === scalingTypeInt) {
            var difference = parseFloat(row.find('.difference').val());
            var difference2 = parseFloat(row.find('.difference2').val());
            var defaultThickness1 = parseFloat(row.find('.defaultThickness_mm1').val());
            var defaultThickness2 = parseFloat(row.find('.defaultThickness_mm2').val());
            var dimension1_NMD = parseFloat(row.find('.dimension1_NMD').val());
            var dimension2_NMD = parseFloat(row.find('.dimension2_NMD').val());
            var userGivenDimension1_NMD = parseFloat(row.find("input[name*='additional_dimension1_NMD.']").val());
            var userGivenDimension2_NMD = parseFloat(row.find("input[name*='additional_dimension2_NMD.']").val());
            var thickness_mm2 = parseFloat(row.find("input[name*='additional_thickness_mm2.']").val());

            if (isNaN(difference)) {
                difference = 0
            }

            if (isNaN(difference2)) {
                difference2 = 0
            }

            if (isNaN(defaultThickness1)) {
                defaultThickness1 = 0
            }

            if (isNaN(defaultThickness2)) {
                defaultThickness2 = 0
            }

            if (isNaN(dimension1_NMD)) {
                dimension1_NMD = 0
            }

            if (isNaN(dimension2_NMD)) {
                dimension2_NMD = 0
            }

            if (isNaN(thickness_mm)) {
                thickness_mm = 0
            }

            if (isNaN(thickness_mm2)) {
                thickness_mm2 = 0
            }

            if (userGivenDimension1_NMD || userGivenDimension2_NMD || dimension1_NMD || dimension2_NMD) {
                if (dimension2_NMD) {
                    productScalingFactor = ((userGivenDimension1_NMD - difference) / (dimension1_NMD - difference) * (userGivenDimension2_NMD - difference2) / (dimension2_NMD - difference2))
                } else if (dimension1_NMD) {
                    productScalingFactor = ((userGivenDimension1_NMD - difference) / (dimension1_NMD - difference))
                }
            } else {
                if (defaultThickness2) {
                    productScalingFactor = ((thickness_mm - difference) / (defaultThickness1 - difference) * (thickness_mm2 - difference2) / (defaultThickness2 - difference2))
                } else if (defaultThickness1) {
                    productScalingFactor = ((thickness_mm - difference) / (defaultThickness1 - difference))
                }
            }

            if (!isFinite(productScalingFactor)) {
                productScalingFactor = 0;
            }
        } else if (1 === scalingTypeInt || 3 === scalingTypeInt) {
            productScalingFactor = 1;
        }

        for (var j = 0; j < quantitys.length; j++) {
            var totalSf;
            const constituentQuantityInput = $(quantitys[j]);
            // if a constituent's quanty and unit is edited separately by user, it is unlinked from parent construction hence its quantity is not updated when parent quantity changes
            if ($(constituentQuantityInput).attr('data-isUnlinkedFromParentConstruction') !== 'true') {
                var componentScalingFactor = parseFloat($(constituentQuantityInput).closest('tr').find("input[name*='additional_componentScalingFactor']").val());

                if (!isNaN(componentScalingFactor)) {
                    totalSf = componentScalingFactor * productScalingFactor;
                }

                const constructionQty = parseFloat($(constituentQuantityInput).attr('data-constructionquantity'))
                let calculatedQuantity = constructionQty ? constructionQty * userInput : 0;

                if (totalSf) {
                    calculatedQuantity *= totalSf;
                }

                if (calculatedQuantity) {
                    $(constituentQuantityInput).val(calculatedQuantity).trigger('input');
                } else {
                    $(constituentQuantityInput).val(0).trigger('input');
                }
            }
        }
    } else {
        for (var i = 0; i < quantitys.length; i++) {
            const constituentQuantityInput = $(quantitys[i]);
            // if a constituent's quanty and unit is edited separately by user, it is unlinked from parent construction hence its quantity is not updated when parent quantity changes
            if ($(constituentQuantityInput).attr('data-isUnlinkedFromParentConstruction') !== 'true') {
                var value = javascriptRound(parseFloat($(constituentQuantityInput).attr('data-constructionquantity')) * userInput * constructionConversionFactor);
                const isConstituentQtyLink = isConstituentQuantityLink(constituentQuantityInput)
                if (value) {
                    $(constituentQuantityInput).val(value).trigger('input');
                    if (isConstituentQtyLink) {
                        updateQuantityTextConstituent(constituentQuantityInput, value)
                    }
                } else {
                    $(constituentQuantityInput).val(0).trigger('input');
                    if (isConstituentQtyLink) {
                        updateQuantityTextConstituent(constituentQuantityInput, 0)
                    }
                }
            }
        }
    }
}

function isConstituentQuantityLink(element) {
    return $(element).attr('data-isHiddenTextInput') === 'true'
}

function updateQuantityTextConstituent($constituentQuantityInput, value) {
    $($constituentQuantityInput).siblings('.isQuantityLinkConstituent').find('.quantityTextConstituent').text(value)
}

function javascriptRound(d) {

    if (!d) {
        return 0
    }

    d = new Number(d)

    if (d >= 10) {
        return roundToDecimals(d, 0)
    }
    if (d >= 1 && d < 10) {
        return roundToDecimals(d, 1)
    }
    if (d < 1 && d >= 0.1) {
        return roundToDecimals(d, 2)
    }
    if (d < 0.1 && d >= 0) {
        return d
    }

    if (d < 0) {
        return roundToDecimals(d, 2)
    }

}

function roundToDecimals(value, decimals) {
    return Number(Math.round(value + 'e' + decimals) + 'e-' + decimals);
}

/*
        Scaling multiplier calculator

        Y = calculated multiplier
        X = thickness_mm, user editable
        A = SchalingsFormuleA
        B = SchalingsFormuleB
        C = SchalingsFormuleC
*/

/* Method moved to backend
function nmd3ScalingCalculation(SchalingsFormuleID, X1, X2, X1Default, X2Default, A1, A2, B1, B2, C) {
    var Y = 0;

    if (SchalingsFormuleID) {
        switch (SchalingsFormuleID) {
            case 1:
                // Linear
                //Lineair: f(x) = A*X+ C
                if (X2Default) {
                    Y = A1 * ((X1*X2) / (X1Default * X2Default)) + C
                    // Y = A1 * X1 + A2 * X2 + C;- old formula
                    // for the 2-dimensional case(when x2Default is available) X = (X1.X2) / (X1Default.X2Default)
                } else {
                    //for the 1-dimensional case X = X1 / X1Default
                       Y = A1 * (X1/X1Default) + C
                    // Y = A1 * X1 + C;- old formula
                }
                break;
            case 2:
                // Power of a point
                //Macht: f(x) = A*X^B+ C
                if (X2Default) {
                    Y = A1 * Math.pow(((X1*X2) / (X1Default * X2Default)) , B1) + C
                    //Y = A1 * X1 ^ B1 + A2 * X2 ^ B2 + C;- old formula
                } else {
                    Y = A1 * Math.pow((X1/X1Default) , B1) + C;
                    //Y = A1 * X1 ^ B1 + C;- old formula
                }
                break;
            case 3:
                // Logarithmic
                //f(x) = A*ln(X)+ C
                if (X2Default) {
                    //Y = A1 * Math.log(X1) + A2 * Math.log(X2) + C;- old formula
                    Y = A1 * Math.log((X1*X2) / (X1Default * X2Default)) + C;
                } else {
                    //Y = A1 * Math.log(X1) + C;- old formula
                    Y = A1 * Math.log(X1 / X1Default) + C;
                }
                break;
            case 4:
                // Exponential
                //Exponentieel: f(x) = A*e^(B*X)+ C
                if (X2Default) {
                   // Y = A1 * Math.exp(B1 * X1) + A2 * Math.exp(B2 * X2) + C;- old formula
                    Y = A1 * Math.exp(B1 * (X1*X2) / (X1Default * X2Default)) + C;
                } else {
                    //Y = A1 * Math.exp(B1 * X1) + C;- old formula
                    Y = A1 * Math.exp(B1 * X1 / X1Default) + C;
                }
                break;
            default:
                // SchalingsFormuleID out of scope
                Y = 0;
                break;
        }
    }

    if (isNaN(Y) || !isFinite(Y)) {
        Y = 0;
    }
    return Y

}
*/
function showCalculationFormula(elem, entityId, datasetId, indicatorId, resultCategoryId, calculationRuleId, customWidth) {
    if (entityId && datasetId && indicatorId && resultCategoryId && calculationRuleId) {
        if ($('#popover' + datasetId + resultCategoryId + calculationRuleId).is(':visible')) {
            $(elem).popover('hide');
            stopBubblePropagation(event);
        } else {
            $('.showCalculationFormulaPopover').remove();
            $(elem).addClass('loadingCursor');
            if (!$(elem).attr("data-hasPop")) {
                var template;
                var position = dynamicPositionForPopover(elem, true)
                if (customWidth) {
                    template = '<div id="popover' + datasetId + resultCategoryId + calculationRuleId + '" class="popover showCalculationFormulaPopover" style="color:black !important; width: ' + customWidth + 'px; max-width: ' + customWidth + 'px;"><div class="arrow"></div><div class="popover-content"></div></div>'
                } else {
                    template = '<div id="popover' + datasetId + resultCategoryId + calculationRuleId + '" class="popover showCalculationFormulaPopover" style="color:black !important;"><div class="arrow"></div><div class="popover-content"></div></div>'
                }

                $.ajax({
                    url: '/app/sec/util/getCalculationFormula',
                    data: 'entityId=' + entityId + '&datasetId=' + datasetId + '&indicatorId=' + indicatorId + '&resultCategoryId=' + resultCategoryId + '&calculationRuleId=' + calculationRuleId,
                    success: function (data) {
                        $(elem).popover({
                            content: data.output,
                            trigger: 'manual',
                            html: true,
                            placement: position,
                            template: template,
                            container: 'body'
                        });
                        $(elem).removeClass('loadingCursor');
                        $(elem).attr('data-hasPop', true);
                        $(elem).popover('show');
                    }
                });
            } else {
                $(elem).removeClass('loadingCursor');
                $(elem).popover('show');
            }
        }
    }
}

var globalNmdConstituentCounter = 0;

function addNmdConstructionResources(constructionId, entityId, quarterlyInputEnabled, monthlyInputEnabled, selectId, indicatorId, queryId, sectionId,
                                     questionId, resourceTableId, fieldName, preventDoubleEntries, showGWP,
                                     doubleEntryWarning, uniqueConstructionIdentifier, accountId, addParts, date) {
    var whereToAppend = '#' + resourceTableId;


    var selectedProductRows = [];

    if (addParts) {
        var componentRows = $('#NMDProductAddModalBody').find('.nmd3ComponentRow').not('.satisfiedNmdRow');
        var partialRows = $('#NMDProductAddModalBody').find('.nmd3PartialComponentRow').not('.satisfiedNmdRow');

        for (var j = 0; j < componentRows.length; j++) {
            var componentObject = {};
            var componentRow = componentRows[j];
            var select = $(componentRow).find('.kikkareselector');
            var selectedOption = $(componentRow).find('.kikkareselector option:selected');
            var selectedOptionValue = $(selectedOption).val();

            if ($(selectedOption).length && $(selectedOption).val() && $('.partialProfileSet' + selectedOptionValue).length) {
                componentObject["productId"] = selectedOptionValue;
                componentObject["resourceId"] = $(select).attr('data-resourceId');
                componentObject["profileId"] = $(select).attr('data-profileId');
                componentObject["isPartial"] = false;
                selectedProductRows.push(componentObject)
            }
        }

        for (var x = 0; x < partialRows.length; x++) {
            var partialObject = {};
            var partialRow = partialRows[x];
            var productId = $(partialRow).attr("data-productId");

            if ($(partialRow).length && $('.partialComponentProfileSet' + productId).length) {
                // THIS ELEMENT IS A <tr>
                partialObject["productId"] = $(partialRow).attr('data-productId');
                partialObject["resourceId"] = $(partialRow).attr('data-resourceId');
                partialObject["profileId"] = $(partialRow).attr('data-profileId');
                partialObject["isPartial"] = true;
                selectedProductRows.push(partialObject)
            }
        }
    } else {
        var totalProductRows = $('#NMDProductAddModalBody').find('.nmd3TotalRow');

        for (var i = 0; i < totalProductRows.length; i++) {
            var totalObject = {};
            var row = totalProductRows[i];

            if ($(row).find('.nmd3TotalProductCheckbox').is(':checked')) {
                // THIS ELEMENT IS A <tr>
                totalObject["productId"] = $(row).attr('data-productId');
                totalObject["resourceId"] = $(row).attr('data-resourceId');
                totalObject["profileId"] = $(row).attr('data-profileId');
                totalObject["isPartial"] = false;
                selectedProductRows.push(totalObject)
            }
        }
    }

    if (selectedProductRows.length) {
        globalNmdConstituentCounter = 0;

        $.each(selectedProductRows, function (index) {
            var nmdProfileSetString = null; // CONTAINS: ProfielSetID:X1:X2:date,ProfielSetID:X1:X2:date,....
            var resourceId = this["resourceId"];
            var profileId = this["profileId"];
            var productId = this["productId"];
            var isPartial = this["isPartial"];
            var profileSetClass;

            if (addParts) {
                if (isPartial) {
                    profileSetClass = "partialComponentProfileSet" + productId;
                } else {
                    profileSetClass = "partialProfileSet" + productId;
                }
            } else {
                profileSetClass = "profileSet" + productId;
            }

            var profileSetRows = $('#NMDProductAddModalBody').find('.' + profileSetClass);

            $.each(profileSetRows, function (asd, partialRow) {
                var profileSetId = $(partialRow).attr('data-profileSetId');
                var x1 = parseInt($(partialRow).find('.nmd3X1').val());
                var x2 = parseInt($(partialRow).find('.nmd3X2').val());

                if (isNaN(x1) || !isFinite(x1)) {
                    x1 = 0;
                }
                if (isNaN(x2) || !isFinite(x2)) {
                    x2 = 0;
                }

                if (profileSetId) {
                    if (nmdProfileSetString) {
                        nmdProfileSetString = nmdProfileSetString + "," + profileSetId + ":" + x1 + ":" + x2 + ":" + date
                    } else {
                        nmdProfileSetString = profileSetId + ":" + x1 + ":" + x2 + ":" + date
                    }
                }
            });

            console.log("ADDING CONSTITUENT: " + resourceId + ", nmdProfileSetString: " + nmdProfileSetString);

            if (resourceId) {
                var queryString = 'quarterlyInputEnabled=' + quarterlyInputEnabled + '&preventDoubleEntries=' + preventDoubleEntries + '&entityId=' + entityId + '&monthlyInputEnabled=' + monthlyInputEnabled + '&resourceId=' + resourceId + '&profileId=' + profileId + '&indicatorId=' + indicatorId + '&queryId=' + queryId + '&sectionId=' + sectionId + '&questionId=' + questionId + '&fieldName=' + fieldName + '&showGWP=' + showGWP + '&newResourceOnSelect=true'
                    + '&privateDatasetAccountId=' + accountId + '&constructionId=' + constructionId + '&uniqueConstructionIdentifier=' + uniqueConstructionIdentifier + '&parentConstructionId=' + constructionId + '&nmdProfileSetString=' + nmdProfileSetString;
                var previousSeqNro = $(whereToAppend).find("tr").last().attr('data-sequenceNro');
                if (previousSeqNro === "undefined") {
                    previousSeqNro = null;
                } else {
                    previousSeqNro = Math.abs(previousSeqNro) + 1
                }

                if (previousSeqNro) {
                    queryString = 'quarterlyInputEnabled=' + quarterlyInputEnabled + '&preventDoubleEntries=' + preventDoubleEntries + '&entityId=' + entityId + '&monthlyInputEnabled=' + monthlyInputEnabled + '&resourceId=' + resourceId + '&profileId=' + profileId + '&indicatorId=' + indicatorId + '&queryId=' + queryId + '&sectionId=' + sectionId + '&questionId=' + questionId + '&fieldName=' + fieldName + '&showGWP=' + showGWP + '&newResourceOnSelect=true'
                        + '&privateDatasetAccountId=' + accountId + '&constructionId=' + constructionId + '&uniqueConstructionIdentifier=' + uniqueConstructionIdentifier + '&previousSeqNro=' + previousSeqNro + '&parentConstructionId=' + constructionId + '&nmdProfileSetString=' + nmdProfileSetString;
                }
                $.ajax({
                    type: 'POST',
                    data: queryString,
                    url: '/app/addresource',
                    success: function (data, textStatus) {
                        $(whereToAppend).append(data.output);
                        globalNmdConstituentCounter = globalNmdConstituentCounter + 1;

                        if (globalNmdConstituentCounter === selectedProductRows.length) {
                            $(whereToAppend).find("input[data-parentuniqueconstructionidentifier='" + uniqueConstructionIdentifier + "']").prop("readonly", false);
                            var constituentToggler = $(whereToAppend).find('.constituentToggler');
                            $(constituentToggler).css('pointer-events', '');
                            $(constituentToggler).css('opacity', '1');
                            $('.dropdown').dropdown();
                        }
                        enableCalculationOnQueryForm()
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                    }
                });
            }
        });
    }
}


var globalConstituentCounter = 0;

function addConstructionResources(constructionId, entityId, quarterlyInputEnabled, monthlyInputEnabled, selectId, indicatorId, queryId, sectionId,
                                  questionId, resourceTableId, fieldName, preventDoubleEntries, showGWP,
                                  doubleEntryWarning, uniqueConstructionIdentifier, accountId, copiedDesignId, copiedUniqueConstructionIdentifier, dummyGroupCopy) {
    var queryString = 'constructionId=' + constructionId;
    if(globalConstructionResourceUUID){
        uniqueConstructionIdentifier = globalConstructionResourceUUID
        globalConstructionResourceUUID = null
    }
    if (copiedDesignId) {
        queryString = queryString + '&copiedDesignId=' + copiedDesignId + '&copiedUniqueConstructionIdentifier=' + copiedUniqueConstructionIdentifier;
    }

    var whereToAppend = '#' + resourceTableId;
    $.ajax({
        data: queryString,
        url: '/app/constructionresources',
        type: 'GET',
        dataType: "json",
        async: false,
        contentType: "application/json;charset=utf-8",
        success: function (constructionData) {
            globalConstituentCounter = 0;
            $.each(constructionData.datasetsAsJson, function (index) {
                var resourceId = this.resourceId;
                var profileId = this.profileId;
                var datasetManualId = this.manualId;

                if (resourceId && datasetManualId) {
                    var queryString = 'quarterlyInputEnabled=' + quarterlyInputEnabled + '&preventDoubleEntries=' + preventDoubleEntries + '&entityId=' + entityId + '&monthlyInputEnabled=' + monthlyInputEnabled + '&resourceId=' + resourceId + '&profileId=' + profileId + '&indicatorId=' + indicatorId + '&queryId=' + queryId + '&sectionId=' + sectionId + '&questionId=' + questionId + '&fieldName=' + fieldName + '&showGWP=' + showGWP + '&newResourceOnSelect=true'
                        + '&privateDatasetAccountId=' + accountId + '&constructionId=' + constructionId + '&uniqueConstructionIdentifier=' + uniqueConstructionIdentifier + '&datasetManualId=' + datasetManualId;
                    var previousSeqNro = $(whereToAppend).find("tr").last().attr('data-sequenceNro');
                    if (previousSeqNro === "undefined") {
                        previousSeqNro = null;
                    } else {
                        previousSeqNro = Math.abs(previousSeqNro) + 1
                    }

                    if (previousSeqNro) {
                        queryString = 'quarterlyInputEnabled=' + quarterlyInputEnabled + '&preventDoubleEntries=' + preventDoubleEntries + '&entityId=' + entityId + '&monthlyInputEnabled=' + monthlyInputEnabled + '&resourceId=' + resourceId + '&profileId=' + profileId + '&indicatorId=' + indicatorId + '&queryId=' + queryId + '&sectionId=' + sectionId + '&questionId=' + questionId + '&fieldName=' + fieldName + '&showGWP=' + showGWP + '&newResourceOnSelect=true'
                            + '&privateDatasetAccountId=' + accountId + '&constructionId=' + constructionId + '&uniqueConstructionIdentifier=' + uniqueConstructionIdentifier + '&datasetManualId=' + datasetManualId + '&previousSeqNro=' + previousSeqNro;
                        previousSeqNro = null;
                    }
                    if (copiedDesignId && copiedDesignId != "null") {
                        queryString = queryString + '&copiedDesignId=' + copiedDesignId + '&copiedUniqueConstructionIdentifier=' + copiedUniqueConstructionIdentifier

                        if (dummyGroupCopy) {
                            queryString = queryString + '&originalId=' + datasetManualId
                        }
                    }

                    $.ajax({
                        type: 'POST',
                        data: queryString,
                        url: '/app/addresource',
                        async: false,
                        success: function (data, textStatus) {
                            globalConstituentCounter = globalConstituentCounter + 1;
                            let tbodyToAppend = $(whereToAppend).find("tbody");
                            if (tbodyToAppend && tbodyToAppend.length > 0){
                                tbodyToAppend.append(data.output);
                            } else {
                                $(whereToAppend).append(data.output);
                            }

                            if (globalConstituentCounter === constructionData.datasetsAsJson.length) {
                                $(whereToAppend).find("input[data-parentuniqueconstructionidentifier='" + uniqueConstructionIdentifier + "']").prop("readonly", false);
                                var constituentToggler = $(whereToAppend).find('.constituentToggler');
                                $(constituentToggler).css('pointer-events', '');
                                $(constituentToggler).css('opacity', '1');
                                $('.dropdown').dropdown();
                                handleMaterialsSpecificCost(uniqueConstructionIdentifier)
                            }
                            enableCalculationOnQueryForm()
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                        }
                    });
                }
            });
        }
    });
}

function handleMaterialsSpecificCost(uniqueConstructionIdentifier) {
    if ($("input[data-materialsSpecificCost='" + uniqueConstructionIdentifier + "']").length) {
        $('.constituenCostLink' + uniqueConstructionIdentifier).trigger('click');
        $("input[data-constituent='costPerUnit." + uniqueConstructionIdentifier + "']").val("0").trigger('input');
        $("input[data-constituent='totalCost." + uniqueConstructionIdentifier + "']").val("0").trigger('input');
    }
}

function addMultipleResources(resourceIds, tableId, entityId, indicatorId, queryId, sectionId, questionId, fieldName) {
    var resourceIdsList = resourceIds.split(',');
    var whereToAppend = tableId;
    var resourceTableHeader = tableId + "header";

    if (resourceIdsList.length) {
        $(resourceTableHeader).show();
        $.each(resourceIdsList, function (i) {
            addResourceFromSelect(entityId, null, null, null, indicatorId, queryId, sectionId,
                questionId, whereToAppend, fieldName, null, true,
                null, null, null, this, null)
        });
        triggerFormChanged();
    }
}
function create_UUID(){
    var dt = new Date().getTime();
    var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = (dt + Math.random()*16)%16 | 0;
        dt = Math.floor(dt/16);
        return (c=='x' ? r :(r&0x3|0x8)).toString(16);
    });
    return uuid;
}
var globalConstructionResourceUUID
function addResourceFromSelect(entityId, quarterlyInputEnabled, monthlyInputEnabled, selectId, indicatorId, queryId, sectionId,
                               questionId, resourceTableId, fieldName, preventDoubleEntries, showGWP,
                               doubleEntryWarning, uniqueConstructionIdentifier, accountId, copiedResourceId, originalDatasetAnswers,
                               persistChangedManualId, resourceIdtxt, copiedDesignId, copiedDatasetId, productDataListId, resConstructionId, groupParentConstructionQty) {
    if(resConstructionId){
         uniqueConstructionIdentifier = resConstructionId + create_UUID();
        globalConstructionResourceUUID = uniqueConstructionIdentifier
    }
    formChanged = true
    var object = document.getElementById(selectId);
    var resourceId = resourceIdtxt ? resourceIdtxt : $(object).attr("data-resourceId") ? $(object).attr("data-resourceId") : null;
    var profileId;
    var isCopyAnswer = false;
    if (copiedDesignId && copiedDesignId != 'null') {
        isCopyAnswer = true;
    }
    if (copiedResourceId) {
        resourceId = copiedResourceId;
    }
    if ($(object).val() || copiedResourceId || resourceId || $(object).hasClass('resourceSelect')) {
        resourceTableId = "#" + resourceTableId;

        if (resourceId && resourceId.indexOf(".") != -1) {
            profileId = resourceId.split(".")[1];
            resourceId = resourceId.split(".")[0];
        }
        if (!resourceId) {
            resourceId = $(object).val();
            resourceId = resourceId.split(" ")[0];

            if (resourceId && resourceId.indexOf(".") != -1) {
                profileId = resourceId.split(".")[1];
                resourceId = resourceId.split(".")[0];
            }
        }
        var duplicateFound = false;
        if (preventDoubleEntries === "true" || preventDoubleEntries === true) {
            $(resourceTableId).find('input:hidden').each(function () {
                if ($(this).val() === resourceId) {
                    duplicateFound = true;
                    return false;
                }
            });
        }

        if (!duplicateFound) {
            var whereToAppend = resourceTableId;
            var resourceTableHeader = resourceTableId + "header";
            $(resourceTableHeader).show();
            var previousSeqNro = $(whereToAppend).find("tr").last().attr('data-sequenceNro');
            if (previousSeqNro === "undefined") {
                previousSeqNro = null;
            }

            var queryString = 'quarterlyInputEnabled=' + quarterlyInputEnabled + '&preventDoubleEntries=' + preventDoubleEntries + '&entityId=' + entityId + '&monthlyInputEnabled=' + monthlyInputEnabled + '&resourceId=' + resourceId + '&profileId=' + profileId + '&indicatorId=' + indicatorId + '&queryId=' + queryId + '&sectionId=' + sectionId + '&questionId=' + questionId + '&fieldName=' + fieldName + '&showGWP=' + showGWP + '&newResourceOnSelect=true'
                + '&privateDatasetAccountId=' + accountId;

            if (uniqueConstructionIdentifier) {
                queryString = queryString + '&uniqueConstructionIdentifier=' + uniqueConstructionIdentifier;
            }

            if (previousSeqNro) {
                queryString = queryString + '&previousSeqNro=' + previousSeqNro;
            }

            if ('#changeTable' === whereToAppend || $(whereToAppend).hasClass('splitTable')) {
                queryString = queryString + '&splitPage=true';
            }

            var originalQuantity = $(object).attr('data-originalQuantity');
            var originalDataSetId = $(object).attr('data-originalId') ? $(object).attr('data-originalId') : copiedDatasetId ? copiedDatasetId : null;
            var originalUnit = $(object).attr('data-originalUnit');

            if ('#changeTable' === whereToAppend) {
                var originalThickness = $(object).attr('data-originalThickness');
                queryString = queryString + '&originalQuantity=' + originalQuantity + '&originalUnit=' + originalUnit + '&originalThickness=' + originalThickness + '&originalDatasetAnswers=' + originalDatasetAnswers + '&persistChangedManualId=' + persistChangedManualId;
            }

            if ($(whereToAppend).hasClass('splitTable')) {
                queryString = queryString + '&splitRowId=' + $(whereToAppend).attr('data-splitRowId') + '&originalQuantity=' + originalQuantity + '&originalId=' + originalDataSetId + '&originalUnit=' + originalUnit;
            }
            if (isCopyAnswer) {
                queryString = queryString + '&copiedDesignId=' + copiedDesignId;
                if (originalDataSetId) {
                    queryString = queryString + '&originalId=' + originalDataSetId
                }
            }

            if (productDataListId) {
                queryString = queryString + '&productDataListId=' + productDataListId;
            }
            if (groupParentConstructionQty) {
                queryString = queryString + '&groupParentConstructionQty=' + groupParentConstructionQty;
            }

            $.ajax({
                type: 'POST',
                async: false,
                data: queryString,
                url: '/app/addresource',
                success: function (data, textStatus) {
                    if ($(whereToAppend).hasClass('splitTable') || '#changeTable' === whereToAppend) {
                        $(whereToAppend).find("tr").remove();
                    }
                    let tbodyToAppend = $(whereToAppend).find("tbody");
                    if (tbodyToAppend && tbodyToAppend.length > 0){
                        tbodyToAppend.append(data.output);
                    } else {
                        $(whereToAppend).append(data.output);
                    }

                    if (whereToAppend === "#changeTable") {
                        renderSourceListingToAnyElement(resourceId, entityId + 'thisHasToBeUniqueId', indicatorId,
                            questionId, profileId, entityId, showGWP, null, null, null, null,
                            null, null, null, null,"#newSourceListing");
                        var quant = $(whereToAppend).find('.isQuantity');
                        $(quant).each(function () {
                            if ($(this).attr('data-isParentConstruction')) {
                                var elem = this;
                                setTimeout(function () {
                                    $(elem).trigger('input');
                                }, 1000);
                            }
                        });
                        $('#submitChangeOrSplit').removeClass('removeClicks');
                    }

                    if (isCopyAnswer) {
                        var quant = $(whereToAppend).find('.isQuantity');
                        $(quant).each(function () {
                            if ($(this).attr('data-isParentConstruction')) {
                                var elem = this;
                                setTimeout(function () {
                                    $(elem).trigger('input');
                                }, 1000);
                            }
                        });
                    }

                    if ($(whereToAppend).hasClass('splitTable')) {
                        function splitUnitMissMatch(unit) {
                            var $share = $(unit).closest('tr').find('[data-share]');
                            var alert = $(whereToAppend).find('.shareHeader').attr('data-content');
                            if ($(unit).val() !== originalUnit) {
                                if (unit.is(':visible')) {
                                    $(unit).popover({
                                        placement: 'top',
                                        template: '<div class="popover"><button data-dismiss="popover" class="close" onclick="$(this).parent().remove()">&times;</button><div class="arrow"></div><div class="popover-content"></div></div>',
                                        html: true,
                                        trigger: 'manual',
                                        content: alert
                                    });
                                    $(unit).popover('show');
                                    $share.attr('disabled', 'disabled');
                                    setTimeout(function (e) {
                                        $(unit).popover('hide');
                                    }, 4500);
                                } else {
                                    var parent = $(unit).parent();
                                    $(parent).popover({
                                        placement: 'top',
                                        template: '<div class="popover"><button data-dismiss="popover" class="close" onclick="$(this).parent().remove()">&times;</button><div class="arrow"></div><div class="popover-content"></div></div>',
                                        html: true,
                                        trigger: 'manual',
                                        content: alert
                                    });
                                    $(parent).popover('show');
                                    $share.attr('disabled', 'disabled');
                                    setTimeout(function (e) {
                                        $(parent).popover('hide');
                                    }, 4500);
                                }

                            } else {
                                $share.removeAttr('disabled');
                                $(unit).popover('hide');
                            }
                        }

                        var unit;
                        if ($(whereToAppend).find('.userGivenUnitContainer').find('select').length !== 0) {
                            unit = $(whereToAppend).find('.userGivenUnitContainer').find('select')
                        }
                        if (unit === null || unit === undefined || unit === "undefined") {
                            unit = $(whereToAppend).find('.userGivenUnitContainer').find('input:hidden');
                        }
                        $(unit).on('change', function () {
                            splitUnitMissMatch(unit);
                        });
                        if ($(unit).val() !== originalUnit) {
                            splitUnitMissMatch(unit);
                        }
                    }

                    if ($(whereToAppend).find("tbody").find("tr").length === 2) {
                        $(whereToAppend).stupidtable().on("aftertablesort", function (event, data) {
                            var sorter = "<span class='sorter'> <i class=\"fa fa-sort\" aria-hidden=\"true\"></i></span>";
                            var sortableColumns = $('[data-sort]');
                            var th = $(this).find("th");
                            th.find(".arrow").remove();
                            $(sortableColumns).find(".sorter").remove();
                            $(sortableColumns).append(sorter);

                            var dir = $.fn.stupidtable.dir;
                            var arrow = data.direction === dir.ASC ? "<span class='arrow'> <i class=\"fa fa-caret-down\" aria-hidden=\"true\"></i></span>" : "<span class='arrow'> <i class=\"fa fa-caret-up\" aria-hidden=\"true\"></i></span>";
                            th.eq(data.column).find('.sorter').remove();
                            th.eq(data.column).append(arrow);
                            $(this).children().children().each(function () {
                                if (this.hasAttribute('data-constructionRow')) {
                                    $('[data-dragmewithparent="' + this.getAttribute("data-constructionRow") + '"]').insertAfter($(this));
                                }
                            });
                        });

                    }
                    var tbody = $('tbody', whereToAppend);
                    reSequenceStuff(tbody);
                    var newResourceRow = $('.newResourceRow');
                    $(newResourceRow).addClass("highlighted-new");

                    var parkingFactor = $("select[id*='parkingAvailabilityFactor']");
                    if ($(parkingFactor).length) {
                        $(newResourceRow).addClass("loadingFeatureResource");
                        calculateParkingAvailabilityFactor($(parkingFactor), "loadingFeatureResource");
                    }
                    $('[rel="popover"]').popover({
                        placement: 'top',
                        template: '<div class="popover"><div class="arrow"></div><div class="popover-content"></div></div>'
                    });
                    $('[rel="popover_materialWarning"]').popover({
                        placement: 'top',
                        template: '<div class="popover"><button data-dismiss="popover" class="close" onclick="$(this).parent().remove()">&times;</button><div class="arrow"></div><div class="popover-content"></div></div>',
                        html: true
                    });
                    $('.materialServiceWarning').addClass("warningHighlight");
                    var popWarn = $('[data-popthis="true"]');

                    $(popWarn).popover('show');

                    setTimeout(function () {
                        $(newResourceRow).removeClass('highlighted-new');
                        $(newResourceRow).addClass('highlighted-background');
                        $(newResourceRow).removeClass('newResourceRow');
                    }, 1000);

                    setTimeout(function () {
                        $('.materialServiceWarning').removeClass('materialServiceWarning warningHighlight');
                        $(popWarn).removeAttr('data-popthis');
                        $(popWarn).removeClass('popoverThisWithWarning');
                    }, 3500);

                    $('.numeric').on('input propertychange', function () {
                        var start = this.selectionStart;
                        end = this.selectionEnd;
                        var val = $(this).val();
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
                            var dotsAndCommas = ((val.match(/\./g) || []).length + (val.match(/,/g) || []).length);

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

                    if (uniqueConstructionIdentifier) {
                        var parentAdditionalQuestions = $('[data-inherit]');
                        if (parentAdditionalQuestions.length) {
                            $(parentAdditionalQuestions).each(function () {
                                var identifier = $(this).attr('data-inherit');
                                $(this).children().on('change', function () {
                                    var elementType = this.nodeName;
                                    inheritSelectValueToChildren(identifier, $(this).val(), elementType);
                                });
                            });
                        }
                    }
                    $(whereToAppend).find('.dropdown').dropdown();
                    $(whereToAppend).find('tr[id^="null"]').removeClass("existingResourceRow")
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                }
            });
            materialAdded = true
            enableCalculationOnQueryForm()
            triggerFormChanged();
            initAdditionalQuestionAutocompletes();
        } else if (doubleEntryWarning) {
            Swal.fire("", doubleEntryWarning, "warning");
        }
    }
}

function switchOnTriggerCalculationFlag(){
    $("#triggerCalculation").val("true")
}

function showAllQueryResources(showAllButton, resourceBoxId, filterPopOverContent, split) {
    if (showAllButton) {
        var object = $(showAllButton).siblings('input:text:first');

        if (split) {
            object = $(showAllButton).siblings('input:text:last');
        }

        if (object) {
            var options = {
                minChars: 0
            };
            $(object).devbridgeAutocomplete().setOptions(options);
            $(object).val("").devbridgeAutocomplete("onValueChange");
        }

        $(showAllButton).blur(function () {
            $(object).blur();
        });
    }
}

function showAllDataMappingResources(showAllButton) {
    if (showAllButton) {
        var object = $(showAllButton).siblings('input:text:first');

        if (object) {
            var options = {
                minChars: 0
            };
        }
        $(object).devbridgeAutocomplete().setOptions(options);
        $(object).val("").devbridgeAutocomplete("onValueChange");

        $(showAllButton).blur(function () {
            $(object).blur();
        });
    }
}

function initSystemMappingAutocompletes() {
    var autocompletes = document.getElementsByClassName("autocomplete");

    if (autocompletes) {
        for (var i = 0; i < autocompletes.length; i++) {
            var object = autocompletes[i];
            var div = $(object).closest('div');

            if (!$(object).val()) {
                var div = $(object).closest('div');
                $(div).attr('class', 'input-append redBorder');
            }

            $(object).devbridgeAutocomplete({
                serviceUrl: '/app/jsonresources', groupBy: 'category',
                minChars: 3,
                deferRequestBy: 1000,
                params: {
                    resourcesForMapping: true
                },
                ajaxSettings: {
                    success: function(data) {
                        setFlashForAjaxCall(JSON.parse(data))
                    },
                    error: function(data) {
                        setFlashForAjaxCall(JSON.parse(data))
                    }
                },
                onSelect: function (suggestion) {
                    var resourceId = $(this).siblings('input:hidden:first');
                    var parsedResourceId = suggestion.data.resource;

                    if (parsedResourceId.indexOf(".") != -1) {
                        parsedResourceId = parsedResourceId.split(".")[0];

                    }
                    $(resourceId).val(parsedResourceId);
                    var div = $(this).closest('div');
                    $(div).attr('class', 'input-append');
                    removeClassFromParent(this, 'notIdentified');
                    var detailsObject = $(this).closest('tr').find(".detailsLink")[0];
                    var idOfDetailsObject = $(detailsObject).attr("id");
                    unhoverDetails(idOfDetailsObject);
                },
                zIndex: autocompleteZIndex,

                onSearchStart: function (query) {
                    var showAllButton = $(this).siblings('a:first');
                    $(showAllButton).children().removeClass("icon-chevron-down");
                    $(showAllButton).children().empty();
                    $(showAllButton).children().append(loadingImg);
                },
                onSearchComplete: function (query, suggestions) {
                    var showAllButton = $(this).siblings('a:first');
                    $(showAllButton).children().empty();
                    $(showAllButton).children().addClass("icon-chevron-down");
                    var options = {
                        minChars: 3,
                        deferRequestBy: 1000
                    };

                    if (suggestions.length == 1) {
                        options = {
                            minChars: 3,
                            deferRequestBy: 1000,
                            autoSelectFirst: true
                        };
                    }
                    var that = $(this).devbridgeAutocomplete().suggestionsContainer;
                    if ($(that).length) {
                        $(that).scrollTop(0);
                    }
                    $(this).devbridgeAutocomplete().setOptions(options);
                },
                onHide: function (container) {
                    var detailsObject = $(this).closest('tr').find(".detailsLink")[0];
                    var idOfDetailsObject = $(detailsObject).attr("id");
                    unhoverDetails(idOfDetailsObject);
                }
            });
        }
    }
}

function initImportMapperAutocompletes(resourceGroups, parentId) {
    initResourceSelects();
    var autocompletes = document.getElementsByClassName("autocomplete");

    if (autocompletes) {
        for (var i = 0; i < autocompletes.length; i++) {
            var object = autocompletes[i];
            var indicatorId = $(object).attr("data-indicatorId");
            var queryId = $(object).attr("data-queryId");
            var entityId = $(object).attr("data-entityId");
            var sectionId = $(object).attr("data-sectionId");
            var questionId = $(object).attr("data-questionId");
            var unit = $(object).attr("data-unit");
            var div = $(object).closest('div');
            var accountId = $(object).attr('data-accountId');
            var datasetId, applicationId, importMapperId;

            if ($(object).hasClass("remapAlreadyMapped")) {
                datasetId = $(object).attr("data-datasetId");
                applicationId = $(object).attr("data-applicationId");
                importMapperId = $(object).attr("data-importMapperId");
            } else {
                datasetId = null;
                applicationId = null;
                importMapperId = null;
            }

            if (!$(object).val()) {
                var div = $(object).closest('div');
                $(div).attr('class', 'input-append redBorder');
            }

            $(object).devbridgeAutocomplete({
                serviceUrl: '/app/jsonresources', groupBy: 'category',
                minChars: 3,
                deferRequestBy: 1000,
                noCache: true,
                params: {
                    indicatorId: indicatorId,
                    queryId: queryId,
                    sectionId: sectionId,
                    questionId: questionId,
                    unit: unit,
                    entityId: entityId,
                    resourceGroups: resourceGroups,
                    privateDatasetAccountId: accountId,
                    datasetId: datasetId,
                    applicationId: applicationId,
                    importMapperId: importMapperId,
                    isImportMapper: true
                },
                ajaxSettings: {
                    success: function(data) {
                        setFlashForAjaxCall(JSON.parse(data))
                    },
                    error: function(data) {
                        setFlashForAjaxCall(JSON.parse(data))
                    }
                },
                formatResult: function (suggestion) {
                    return formatAutocompleteRows(suggestion, entityId, indicatorId, questionId);
                },
                formatGroup: function (suggestion, category) {
                    return formatAutocompleteGroups(suggestion, category);
                },
                onSelect: function (suggestion) {
                    handleAutocompleteOnSelect(suggestion, $(this), true);
                },
                zIndex: autocompleteZIndex,

                onSearchStart: function (query) {
                    var alwaysShowFullDB = $(this).attr("data-alwaysShowFullDB");
                    if (alwaysShowFullDB) {
                        query["alwaysShowFullDB"] = true;
                    }
                    var showAllButton = $(this).siblings('a:first');
                    $(showAllButton).children().removeClass("icon-chevron-down");
                    $(showAllButton).children().empty();
                    $(showAllButton).children().append(loadingImg);
                },
                onSearchComplete: function (query, suggestions) {
                    var showAllButton = $(this).siblings('a:first');
                    $(showAllButton).children().empty();
                    $(showAllButton).children().addClass("icon-chevron-down");

                    var options = {
                        minChars: 3,
                        deferRequestBy: 1000
                    };

                    if (suggestions.length == 1) {
                        options = {
                            minChars: 3,
                            deferRequestBy: 1000,
                            autoSelectFirst: true
                        };
                    }
                    var that = $(this).devbridgeAutocomplete().suggestionsContainer;
                    if ($(that).length) {
                        $(that).scrollTop(0);
                    }
                    $(this).devbridgeAutocomplete().setOptions(options);
                    $('[rel="popover"]').popover({
                        placement: 'top',
                        template: '<div class="popover"><div class="arrow"></div><div class="popover-content"></div></div>'
                    });
                },
                onHide: function (container) {
                    var detailsObject = $(this).closest('tr').find(".detailsLink")[0];
                    var idOfDetailsObject = $(detailsObject).attr("id");
                    unhoverDetails(idOfDetailsObject);
                }
            });
        }
    }
}

function initImportMapperRemapAutocompletes(resourceGroups, parentId, dynamicAutocompletes) {
    initResourceSelects();
    var autocompletes;

    if (dynamicAutocompletes) {
        autocompletes = document.getElementsByClassName("remapDynamicAutocomplete");
    } else {
        autocompletes = document.getElementsByClassName("remapAutocomplete");
    }

    if (autocompletes && (resourceGroups || skipResourceTypes)) {
        for (var i = 0; i < autocompletes.length; i++) {
            var object = autocompletes[i];
            if (dynamicAutocompletes && object) {
                $(object).removeClass('remapDynamicAutocomplete');
            }
            var indicatorId = $(object).attr("data-indicatorId");
            var queryId = $(object).attr("data-queryId");
            var sectionId = $(object).attr("data-sectionId");
            var questionId = $(object).attr("data-questionId");
            var unit = $(object).attr("data-unit");
            var div = $(object).closest('div');
            var entityId = $(object).attr("data-entityId");
            var accountId = $(object).attr("data-accountId");
            $(div).attr('class', 'input-append redBorder');

            $(object).devbridgeAutocomplete({
                serviceUrl: '/app/jsonresources', groupBy: 'category',
                minChars: 3,
                deferRequestBy: 1000,
                noCache: true,
                params: {
                    indicatorId: indicatorId,
                    queryId: queryId,
                    sectionId: sectionId,
                    questionId: questionId,
                    unit: unit,
                    entityId: entityId,
                    resourceGroups: resourceGroups,
                    privateDatasetAccountId: accountId,
                    isImportMapper: true
                },
                ajaxSettings: {
                    success: function(data) {
                        setFlashForAjaxCall(JSON.parse(data))
                    },
                    error: function(data) {
                        setFlashForAjaxCall(JSON.parse(data))
                    }
                },
                onSelect: function (suggestion) {
                    handleAutocompleteOnSelect(suggestion, $(this), true);
                },
                zIndex: autocompleteZIndex,

                formatResult: function (suggestion) {
                    return formatAutocompleteRows(suggestion, entityId, indicatorId, questionId);
                },
                formatGroup: function (suggestion, category) {
                    return formatAutocompleteGroups(suggestion, category);
                },
                onSearchStart: function (query) {
                    var showAllButton = $(this).siblings('a:first');
                    $(showAllButton).children().removeClass("icon-chevron-down");
                    $(showAllButton).children().empty();
                    $(showAllButton).children().append(loadingImg);
                },
                onSearchComplete: function (query, suggestions) {
                    var showAllButton = $(this).siblings('a:first');
                    $(showAllButton).children().empty();
                    $(showAllButton).children().addClass("icon-chevron-down");
                    var options = {
                        minChars: 3,
                        deferRequestBy: 1000
                    };

                    if (suggestions.length === 1) {
                        options = {
                            minChars: 3,
                            deferRequestBy: 1000,
                            autoSelectFirst: true
                        };
                    }
                    var that = $(this).devbridgeAutocomplete().suggestionsContainer;
                    if ($(that).length) {
                        $(that).scrollTop(0);
                    }
                    $(this).devbridgeAutocomplete().setOptions(options);
                    $('[rel="popover"]').popover({
                        placement: 'top',
                        template: '<div class="popover"><div class="arrow"></div><div class="popover-content"></div></div>'
                    });
                },
                onHide: function (container) {
                    var detailsObject = $(this).closest('tr').find(".detailsLink")[0];
                    var idOfDetailsObject = $(detailsObject).attr("id");
                    unhoverDetails(idOfDetailsObject);
                }
            });
        }
    }
}

function getImportMapperAutocompleteParams(autocomplete) {
    var params = {};
    var entityId = $(autocomplete).attr('data-entityId');
    var indicatorId = $(autocomplete).attr("data-indicatorId");
    var queryId = $(autocomplete).attr("data-queryId");
    var sectionId = $(autocomplete).attr("data-sectionId");
    var questionId = $(autocomplete).attr("data-questionId");
    var applicationId = $(autocomplete).attr("data-applicationId");
    var datasetId = $(autocomplete).attr("data-datasetId");
    var unit = $(autocomplete).attr("data-unit");
    var importMapperId = $(autocomplete).attr("data-importMapperId");
    var filtered = $(autocomplete).attr("data-filtered");
    var accountId = $(autocomplete).attr("data-accountId");
    var resourceGroups = $(autocomplete).attr("data-resourceGroups");
    params["indicatorId"] = indicatorId;
    params["queryId"] = queryId;
    params["sectionId"] = sectionId;
    params["questionId"] = questionId;
    params["unit"] = unit;
    params["resourceGroups"] = resourceGroups;
    params["datasetId"] = datasetId;
    params["applicationId"] = applicationId;
    params["importMapperId"] = importMapperId;
    params["entityId"] = entityId;
    params["privateDatasetAccountId"] = accountId;
    params["isImportMapper"] = true;
    // showDataForUnitText: showDataForUnitText, TODO
    return params;
}

function showAllImportMapperResources(showAllButton, resourceGroups, dontClearSelect, showDataForUnitText) {
    if (showAllButton) {
        var object = $(showAllButton).siblings('input:text:first');

        if (object) {
            if (!dontClearSelect) {
                $(object).val("");
            }

            var params = getImportMapperAutocompleteParams(object);
            var options = {
                minChars: 0,
                params: params
            };
            $(object).devbridgeAutocomplete().setOptions(options);
            if (dontClearSelect) {
                $(object).devbridgeAutocomplete("onValueChange");
            } else {
                $(object).val("").devbridgeAutocomplete("onValueChange");
            }

            $(showAllButton).blur(function () {
                $(object).blur();
            });
        }
    }
}

function hoverDetails(idOfDetails) {
    if (idOfDetails) {
        idOfDetails = '#' + idOfDetails;
        $(idOfDetails).trigger('mouseover');
    }
}

function unhoverDetails(idOfDetails) {
    if (idOfDetails) {
        idOfDetails = '#' + idOfDetails;
        $(idOfDetails).trigger('mouseout');
    }
}

function loadImage(idOfPlacement, entityId, entityClass, thumbImage) {
    if (idOfPlacement && entityId && entityClass) {
        var queryString = 'entityId=' + entityId + '&entityClass=' + entityClass;

        if (thumbImage) {
            queryString = queryString + '&thumbImage=' + thumbImage
        }
        $.ajax({
            type: 'POST', data: queryString, url: '/app/entityimage',
            success: function (data, textStatus) {
                idOfPlacement = '#' + idOfPlacement;
                $(idOfPlacement).append(data.output);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {

            }
        });
    }
}

function changeImage(id, inputFieldId, inputFieldName, thisIsBranding) {
    var imageContainer = '#' + id;
    var previewContainerId = "uploadPreview" + inputFieldId
    $(imageContainer).empty();
    $(imageContainer).append('<img id="' + previewContainerId + '" style="width: 200px;" />');
    $(imageContainer).append('<input type="file" name=\"' + inputFieldName + ' \" class="companyImages" id=\"' + inputFieldId + '\" onchange="previewImage(' + inputFieldId + ', ' + previewContainerId + ');">');
    $('#' + inputFieldId).trigger('click');
}

function previewImage(inputFieldId, previewContainerId) {
    var oFReader = new FileReader();
    oFReader.readAsDataURL(inputFieldId.files[0]);
    oFReader.onload = function (oFREvent) {
        document.getElementById(previewContainerId).src = oFREvent.target.result;
    };
}

function compareResults(entityId, otherEntityId, indicatorId, rules, parentEntityId, reportItemIds) {


    if (entityId && otherEntityId && indicatorId && parentEntityId) {
        var calcRuleQueryString = "";
        var reportItemIdString = "";
        var reportItemId = "";
        var rule = "";
        var arrayOfRules = rules.split(",");
        $.each(arrayOfRules, function (val, key) {
            rule = key.valueOf(arrayOfRules);
            calcRuleQueryString = calcRuleQueryString ? calcRuleQueryString + '&calculationRule=' + rule : '&calculationRule=' + rule;
        });
        compareChartsAlongSide(parentEntityId, entityId, otherEntityId, indicatorId);

        var arrayOfReportItems = reportItemIds.split(",");

        if (reportItemIds !== null && 'null' !== reportItemIds) {
            var reportItemCompareTableFound = false;

            $.each(arrayOfReportItems, function (i, item) {
                var queryString = 'entityId=' + entityId + '&otherEntityId=' + otherEntityId + '&indicatorId=' + indicatorId + calcRuleQueryString + '&parentEntityId=' + parentEntityId + '&reportItemId=' + arrayOfReportItems[i];
                if (queryString) {
                    renderMultiDataWarningForCompare(queryString)
                }
                var resultTableBodyForSwapping = $('#resultRowsBody' + item);

                if ($(resultTableBodyForSwapping).length) {
                    $.ajax({
                        type: 'POST', data: queryString, url: '/app/renderresultcomparepercategory',
                        success: function (data, textStatus) {
                            $(resultTableBodyForSwapping).empty();
                            $(resultTableBodyForSwapping).append(data.output).hide().fadeIn(1800);
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                        }
                    });
                    reportItemCompareTableFound = true;
                }

                var totalTableBodyId = $('#totalResultsTbody' + item);
                var compareTotalBody = $('#compareTotalTbody' + item);

                if ($(totalTableBodyId).length) {
                    $.ajax({
                        type: 'POST', data: queryString, url: '/app/sec/util/renderResultCompare',
                        success: function (data, textStatus) {
                            if ($(compareTotalBody).length) {
                                $(compareTotalBody).remove();
                            }
                            $(data.output).insertAfter(totalTableBodyId).hide().fadeIn(1800);
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {

                        }
                    });
                    reportItemCompareTableFound = true;
                }
            });

            if (!reportItemCompareTableFound) {
                var queryStringFailover = 'entityId=' + entityId + '&otherEntityId=' + otherEntityId + '&indicatorId=' + indicatorId + calcRuleQueryString + '&parentEntityId=' + parentEntityId;
                if (queryStringFailover) {
                    renderMultiDataWarningForCompare(queryStringFailover)
                }
                var resultTableBodyForSwappingFailover = $('#resultRowsBody');

                if ($(resultTableBodyForSwappingFailover).length) {
                    $.ajax({
                        type: 'POST', data: queryStringFailover, url: '/app/renderresultcomparepercategory',
                        success: function (data, textStatus) {
                            $(resultTableBodyForSwappingFailover).empty();
                            $(resultTableBodyForSwappingFailover).append(data.output).hide().fadeIn(1800);
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {

                        }
                    });
                }

                var totalTableBodyIdFailover = $('#totalResultsTbody');
                var compareTotalBodyFailover = $('#compareTotalTbody');

                if ($(compareTotalBodyFailover).length) {
                    $.ajax({
                        type: 'POST', data: queryStringFailover, url: '/app/sec/util/renderResultCompare',
                        success: function (data, textStatus) {
                            if ($(compareTotalBodyFailover).length) {
                                $(compareTotalBodyFailover).remove();
                            }
                            $(data.output).insertAfter(totalTableBodyIdFailover).hide().fadeIn(1800);
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {

                        }
                    });
                }
            }
        } else {
            var queryString = 'entityId=' + entityId + '&otherEntityId=' + otherEntityId + '&indicatorId=' + indicatorId + calcRuleQueryString + '&parentEntityId=' + parentEntityId;
            if (queryString) {
                renderMultiDataWarningForCompare(queryString)
            }
            var resultTableBodyForSwapping = $('#resultRowsBody');

            if ($(resultTableBodyForSwapping).length) {
                $.ajax({
                    type: 'POST', data: queryString, url: '/app/renderresultcomparepercategory',
                    success: function (data, textStatus) {
                        $(resultTableBodyForSwapping).empty();
                        $(resultTableBodyForSwapping).append(data.output).hide().fadeIn(1800);
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {

                    }
                });
            }

            var totalTableBodyId = $('#totalResultsTbody');
            var compareTotalBody = $('#compareTotalTbody');

            if ($(compareTotalBody).length) {
                $.ajax({
                    type: 'POST', data: queryString, url: '/app/sec/util/renderResultCompare',
                    success: function (data, textStatus) {
                        if ($(compareTotalBody).length) {
                            $(compareTotalBody).remove();
                        }
                        $(data.output).insertAfter(totalTableBodyId).hide().fadeIn(1800);
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {

                    }
                });
            }
        }
    }
}

function compareChartsAlongSide(parentEntityId, entityId, otherEntityId, indicatorId) {
    var calculationRuleId = $('#currentRuleInUse').val();
    var lifeCycleImpactsChartDiv = $('#lifeCycleImpactsChartWrapper');
    $(lifeCycleImpactsChartDiv).empty();
    initializeLifeCycleImpactsChart(entityId, indicatorId, calculationRuleId, true);
    initializeLifeCycleImpactsChart(otherEntityId, indicatorId, calculationRuleId, true);
}

function renderMultiDataWarningForCompare(queryString) {

    $.ajax({
        url: '/app/multidataajaxwarningforcompare',
        data: queryString,
        success: function (data, textStatus) {
            if (data.output) {
                const warning = " <div class=\"alert hide-on-print\">\n" +
                    "               <button data-dismiss=\"alert\" class=\"close\" type=\"button\">×</button>\n" +
                    "           <strong>" + data.output + "</strong>\n" +
                    "           </div>";
                $('#messageContent').empty().append(warning)
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("ajax call failed " + errorThrown)
        }
    });
}

function addToQueryString(linkObjectId, parameterName, parameterValue) {
    linkObjectId = '#' + linkObjectId;
    var url = $(linkObjectId).attr("href");
    var query = url.indexOf('?');
    var anchor = url.indexOf('#');
    if (query == url.length - 1) {
        // Strip any ? on the end of the URL
        url = url.substring(0, query);
        query = -1;
    }
    return (anchor > 0 ? url.substring(0, anchor) : url)
        + (query > 0 ? "&" + parameterName + "=" + parameterValue : "?" + parameterName + "=" + parameterValue)
        + (anchor > 0 ? url.substring(anchor) : "");
}

function removeResolverRowByClassWithConfirm(element, className, tableId, containerId) {
    if ($('#modalconfirm').length < 1) {
        var modalContStr = '<div class="modal hide fade" id="modalconfirm"></div>';
        $("body").append(modalContStr);
    }
    var onclick = "removeElementByClass('" + className + "'); removeResolverContainer('" + tableId + "', '" + containerId + "'); recalculateUnidentified();";
    var title_str = $(element).attr('data-titlestr');
    var true_str = $(element).attr('data-truestr');
    var false_str = $(element).attr('data-falsestr');
    var question_str = $(element).attr('data-questionstr');

    var $modalWin = $('#modalconfirm');

    var modalInnerStr = '<div class="modal-header"><button type="button" class="close" data-dismiss="modal">&times;</button>';
    modalInnerStr += '<h2>' + title_str + '</h2>';
    modalInnerStr += '</div>';
    modalInnerStr += '<div class="modal-body"><p>' + question_str
        + '</p></div>';
    modalInnerStr += '<div class="modal-footer"><a href="javascript: return;" onclick="'
        + onclick
        + '" class="btn btn-danger" data-dismiss="modal">'
        + true_str
        + '</a> <a href="javascript: return;" class="btn" data-dismiss="modal">'
        + false_str + '</a></div>';

    $modalWin.html(modalInnerStr);
    $modalWin.modal();
    return false;
}

function removeResolverContainer(tableId, containerId) {
    tableId = '#' + tableId + ' >tbody >tr';
    containerId = '#' + containerId;
    var rowCount = $(tableId).length;

    if (!rowCount) {
        $(containerId).remove();
    }
}

function hideResolverTable(tableId) {
    var rows = '#' + tableId + ' >tbody >tr';
    var rowCount = $(rows).length;

    if (!rowCount) {
        $('#' + tableId).hide();
    }
}

function selectAllCheckbox(element, checkBoxClass) {
    $(element).on('change', function () {
        $(checkBoxClass).prop('checked', $(this).prop("checked"));
    });
}

function selectAllCombineCheckbox(checkBoxClass) {
    var checkBoxes = "." + checkBoxClass;
    $(checkBoxes).each(function () {
        if (!$(this).prop("checked")) {
            $(this).prop("checked", true);
        }
    });
}

function detectmob() {
    if (window.innerWidth <= 800) {
        return true;
    } else {
        return false;
    }
}

// strips html tags from user names

$('#userName').on('keyup', function () {
    this.value = this.value.replace(/<(.|\n)*?>/g, '');
});


$('#userName').on('keyup', function () {
    this.value = this.value.replace(/<(.|\n)*?>/g, '');
});

function scrollToCompare() {
    $('html,body').animate({
            scrollTop: $(".table").offset().top
        },
        'slow');

}

function dataTableAttachment() {
    $('#attachmentTable').dataTable({
        "bFilter": false,
        "bPaginate": false,
        "aoColumnDefs": [{
            "bSortable": false,
            "aTargets": ["no-sort"]
        }]
    });
}

function sortableTable(tableId, paginate) {
    if (!paginate) {
        paginate = false
    }
    $(tableId).dataTable({
        "bFilter": false,
        "bPaginate": paginate,
        "sPaginationType": "full_numbers",
        "aoColumnDefs": [{
            "bSortable": false,
            "aTargets": ["no-sort"]
        }]
    });

}

function changeExpandText(textElemId, text) {
    var popover = $('#' + textElemId).data('popover');
    popover.options.content = text.value;
}


function toggleEmptyAttributes(elem, elem2, e) {
    var e = window.event || e //make sure event is defined in Firefox browser
    stopBubblePropagation(e);
    var emptyAttributes = $(elem);
    var plusMinus = $(elem2);
    if (!emptyAttributes.is(":visible")) {
        $(emptyAttributes).slideDown().removeClass('hidden');
        $(plusMinus).toggleClass('icon-plus icon-minus');
    } else {
        $(emptyAttributes).slideUp().addClass;
        $(plusMinus).toggleClass('icon-minus icon-plus');
    }
}


function submitOrShow(indicatorSelect, designInfo, form, indicatorHeading, designHeading, entityClass, lcaParamsReady, localizedNext) {

    var checkBox = $('.indicatorCheckboxDesign').not('input[value="xBenchmarkEmbodied"]').not('input[value="xBenchmarkEmbodiedA1-A3"]').not('input[value="xBenchmarkOperating"]');
    if ($(this).is(':disabled') || !$(checkBox).is(":checked")) {
        return false;
    } else {
        var parentClass = entityClass;
        var indicatorDiv = indicatorSelect;
        var newDesignDiv = designInfo;
        var indicatorHeader = indicatorHeading;
        var newDesignHeader = designHeading;

        if (parentClass && (typeof parentClass === 'string' || parentClass instanceof String)) {
            parentClass = parentClass.toLowerCase()
        }

        if ("building" === parentClass || "infrastructure" === parentClass || "buildingproduct" === parentClass) {
            var rowsUsingLCAParams = $('#indicatorsForNewDesign > tbody > tr[data-isusinglcaparameters="true"]').length;

            var isUsingLCAParams = false;
            if (!lcaParamsReady && rowsUsingLCAParams > 0) {
                isUsingLCAParams = true;
            }

            if ($(indicatorDiv).is(':visible')) {

                filterSelectLCA();

                $('#toggleNewIndicators').hide();
                $('#backToIndicatorSelect').show();
                $(indicatorDiv).fadeOut();
                $(newDesignDiv).fadeIn();
                $(indicatorHeader).hide();
                $(newDesignHeader).fadeIn();

                if (isUsingLCAParams) {
                    $('#getStartedSubmitDesigns').text(localizedNext);
                } else {
                    $('#getStartedSubmitDesigns').text("OK").addClass('preventDoubleSubmit');
                }
            } else if ($(newDesignDiv).is(':visible')) {
                var name = $(newDesignDiv).find('#name');
                var ribaStage = $(newDesignDiv).find('#ribaStage');

                if ($(name).length) {
                    var isSumbit = ($(name).val() && $(ribaStage).length && $(ribaStage).val()) || ($(name).val() && !$(ribaStage).length);
                    if (isSumbit) {
                        if (isUsingLCAParams) {
                            $('#backToNewDesign').show();
                            $('#backToIndicatorSelect').hide();
                            $(newDesignDiv).fadeOut();
                            $('#useDefaultLCAParamsBody').fadeIn();
                            $(newDesignHeader).hide();
                            $('#useDefaultLCAParamsTitle').fadeIn();
                            $('#submitDesignLCAParamsDefault').fadeIn();
                            $('#submitDesignLCAParamsEdit').fadeIn();
                            $('#getStartedSubmitDesigns').hide();
                        } else {
                            doubleSubmitPrevention('getStartedIndicatorFormDesign');
                            $(form).trigger('submit');
                        }
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
        } else {
            doubleSubmitPrevention('getStartedIndicatorFormDesign');
            $(form).trigger('submit');
        }
    }
}

function submitIndicators(form) {
    var checkBox = $('.indicatorCheckboxDesign').not('input[value="xBenchmarkEmbodied"]').not('input[value="xBenchmarkEmbodiedA1-A3"]').not('input[value="xBenchmarkOperating"]');
    if ($(this).is(':disabled') || !$(checkBox).is(":checked")) {
        return false;
    } else {
        doubleSubmitPrevention('getStartedIndicatorFormDesign');
        $(form).trigger('submit');
    }

}

function submitAndHandleLCAParams(form, lcaParamHandling) {
    if (lcaParamHandling === "defaults") {
        $(form).append('<input type=\"hidden\" id=\"lcaParamHandling\" name=\"lcaParamHandling\" value=\"true\" />');
    }
    ;
    doubleSubmitPrevention('getStartedIndicatorFormDesign');
    $(form).trigger('submit');
}

function replaceAll(str, find, replace) {
    return str.replace(new RegExp(find, 'g'), replace);
}

function filterSelectLCA(lcaParamsReady, localizedNext) {
    //If the div-scope is not present dont do anything
    //because there aren't any of the following element
    if (!($("#div-scope").length)) return null;

    var indicatorList = [];
    var checkedIndicators = [];

    $('*[id*=enabledIndicatorIdsForDesign]').each(function () {
        var indicator = $(this).val();

        if (indicator) {
            indicatorList.push(indicator);

            if ($(this).is(':checked')) {
                checkedIndicators.push(indicator);
            }
        }
    });

    var select = $("#scopeId")
    var selectLabel = $("#scopeLabelId")

    var options = select.find("option")
    if (options) {
        var atLeastOne = false
        var defaultSuggested = false

        options.each(function () {
            var o = $(this);

            if (o.val()) {
                var compList = o.attr("data-compatibleindicatorlist");

                if (compList) {
                    compList = JSON.parse(replaceAll(compList, "'", "\""))
                }

                var isCompatible
                if (compList && Array.isArray(compList)) {
                    isCompatible = indicatorList.some(function (i) {
                        return compList.includes(i)
                    })
                }

                if (isCompatible) {
                    o.show();
                    var isSelected = checkedIndicators.some(function (i) {
                        return compList.includes(i)
                    });

                    if (!defaultSuggested && isSelected) {
                        if (checkedIndicators.length === 1) {
                            select.val(o.val());
                        }
                        defaultSuggested = true;
                    }
                    if (!atLeastOne) {
                        atLeastOne = true;
                        //Show Select Section
                        select.show();
                        selectLabel.show();
                    }
                } else {
                    o.hide()
                }
            }
        });

        if (!atLeastOne) {
            //Hide Select Section
            select.hide();
            selectLabel.hide();
            defaultCheckScope();
        } else {
            select.change();
        }

        if (!lcaParamsReady) {
            var rowsUsingLCAParams = $('#indicatorsForNewDesign > tbody > tr[data-isusinglcaparameters="true"]').length;

            var isUsingLCAParams = false;
            if (!lcaParamsReady && rowsUsingLCAParams > 0) {
                isUsingLCAParams = true;
            }

            if (isUsingLCAParams) {
                $('#getStartedSubmitDesigns').text(localizedNext);
            } else {
                $('#getStartedSubmitDesigns').text("OK").addClass('preventDoubleSubmit');
            }
        }
    }
}

function backToNewIndicators(indicatorSelect, designInfo, indicatorHeading, designHeading, buttonText, nextButton) {
    $('#toggleNewIndicators').show();
    $('#backToIndicatorSelect').hide();
    $(nextButton).removeClass('preventDoubleSubmit disableAssert');
    $(indicatorSelect).fadeIn();
    $(designInfo).fadeOut();
    $(designHeading).hide();
    $(indicatorHeading).fadeIn();
}

function backToNewDesign(useLCAParamsBody, designInfo, useLCAParamsHeading, designHeading, buttonText, overOneIndicator) {
    $('#backToNewDesign').hide();
    if (overOneIndicator) {
        $('#backToIndicatorSelect').show();
    }
    $('#getStartedSubmitDesigns').show().removeClass('preventDoubleSubmit');
    $('#submitDesignLCAParamsDefault').hide();
    $('#submitDesignLCAParamsEdit').hide();
    $(useLCAParamsBody).fadeOut();
    $(designInfo).fadeIn();
    $(designHeading).fadeIn();
    $(useLCAParamsHeading).hide();
}

function appendToNewDesignCreation(checkBox, localizedText, noresolve, disabled, hideAppend, createHiddenInput) {
    var indicatorId = $(checkBox).val();
    var whereToAppend = $("#indicatorsForNewDesign > tbody");
    var disabledString = disabled ? " disabled='" + disabled + "' " : "";
    var classForRow = hideAppend ? 'hidden' : '';
    if ($(checkBox).is(":checked")) {
        var compatibles = $(checkBox).attr("data-compatibleIndicatorIds");
        var clone = $(checkBox).closest('tr').clone();
        var name = $(clone).find('.indicatorNameContainer').text();
        var isUsingLCAParameters = $(clone).attr('data-isUsingLCAParameters');
        var newRow = "<tr class='" + classForRow + "' data-isUsingLCAParameters='" + isUsingLCAParameters + "' id='newDesignIndicator" + indicatorId + "'><td><input type='checkbox'" + disabledString + " " +
            "checked='checked' style='float: left' onclick=\"compatibleIndicators(this,'" + localizedText + "')\" " +
            "data-compatibleIndicatorIds='" + compatibles + "' class='enabledIndicatorIdsForDesign' name='enabledIndicatorIdsForDesign' " +
            "id='" + indicatorId + "enabledIndicatorIdsForDesign' value='" + indicatorId + "' /></td><td style='word-wrap: break-word; max-width: 250px;'>" +
            "<label for='" + indicatorId + "enabledIndicatorIdsForDesign' class='control-label' style='float: left; margin-left: 5px;'>" +
            "<strong>" + name + "</strong></label>";
        if (createHiddenInput) {
            newRow += "<input type='hidden' name='enabledIndicatorIdsForDesign' value='" + indicatorId + "' /></td></tr>"
        } else {
            newRow += "</td></tr>"

        }
        $(newRow).appendTo(whereToAppend);

        if (!noresolve) {
            resolveIndicatorConflict(localizedText);
        }
    } else {
        $(whereToAppend).find("#newDesignIndicator" + indicatorId).remove();
        if (!noresolve) {
            resolveIndicatorConflict(localizedText);
        }
    }
}

function preventZeroIndicators(submitId, checkBoxClass) {
    var submitButton = submitId;
    var checkBox = $('.' + checkBoxClass).not('input[value="xBenchmarkEmbodied"]').not('input[value="xBenchmarkEmbodiedA1-A3"]').not('input[value="xBenchmarkOperating"]');
    if (!$(checkBox).is(":checked")) {
        $(submitButton).attr("disabled", "disabled");
    } else {
        $(submitButton).removeAttr('disabled');
    }
    $(checkBox).on('change', function () {
        if (!$(checkBox).is(":checked")) {
            $(submitButton).attr("disabled", "disabled");
        } else {
            $(submitButton).removeAttr('disabled');

        }
    });
}

function toggleAllCheckBoxes(checkBoxClass, submitId, designs) {
    checkBoxClass = '.' + checkBoxClass;

    if (designs) {
        var uncheckAll = $(checkBoxClass).is(":checked");
        $.each($(checkBoxClass), function (idx, checkBox) {
            if (uncheckAll) {
                $(checkBox).prop('checked', false);
            } else {
                $(checkBox).prop('checked', true);
            }
            appendToNewDesignCreation(checkBox, designs, true)
        });
        resolveIndicatorConflict(designs);

        if (uncheckAll) {
            $(submitId).attr("disabled", "disabled");
        } else {
            $(submitId).prop('disabled', false);
        }
    } else {
        if ($(checkBoxClass).is(":checked")) {
            $(checkBoxClass).prop('checked', false);
            $(submitId).attr("disabled", "disabled");
        } else {
            $(checkBoxClass).prop('checked', true);
            $(submitId).prop('disabled', false);
        }
    }
}

/**
 * Show the quantity input text field and unit select for construction constituent after warning
 * Also update attribute data-isUnlinkedFromParentConstruction in quantity input so its quantity will not be calculated when parent construction quantity changes
 * @param $link
 * @param isAlreadyUnlinked if true will not show the warning swal
 * @param warning
 * @param localizedOk
 * @param localizedCancel
 */
function changeConstituentQuantityLinkToTextField($link, isAlreadyUnlinked, warning, localizedOk, localizedCancel) {
    if (isAlreadyUnlinked) {
        showConstituentQuantityNUnitInput($link)
    } else {
        Swal.fire({
            icon: "warning",
            text: warning,
            confirmButtonText: localizedOk,
            confirmButtonColor: '#8DC73F',
            cancelButtonText: localizedCancel,
            cancelButtonColor: '#CDCDCD',
            showCancelButton: true,
            allowOutsideClick: false,
            showLoaderOnConfirm: true,
            reverseButtons: true
        }).then(function (result) {
            if (result.isConfirmed) {
                showConstituentQuantityNUnitInput($link)
            } else if (result.isDisMissed) {
                return false
            }
        });
    }
}

function showConstituentQuantityNUnitInput($link) {
    const $parentTd = $($link).closest('td')
    $($link).remove()
    const $quantityInput = $($parentTd).find('.isQuantity')
    $quantityInput.attr('data-isHiddenTextInput', 'false').attr('data-isUnlinkedFromParentConstruction', 'true')
    $quantityInput.fadeIn().removeClass('hide')
    $($parentTd).find('.userGivenUnitContainer').fadeIn().removeClass('hide')
    unlinkConstituentFromConstruction($parentTd)
}

/**
 * * Update the value of _isUnlinkedFromParentConstruction_ input (in the same td) to 'true' to save to backend
 * @param $parentTd
 */
function unlinkConstituentFromConstruction($parentTd) {
    $($parentTd).find('.isUnlinkedFromParentConstructionInput').val('true')
}

/**
 * Hide the text link and show the input (text or select) by a selector
 * @param $link
 * @param selector
 * @param groupClick
 */
function changeLinkToInputGroupingRow ($link, selector, groupClick) {
    const $parentTd = $($link).closest('td')
    // show the input / select
    $($parentTd).find(selector).fadeIn().removeClass('hide')
    const $row = $($link).closest('tr')
    $($link).remove()
    $($row).find(groupClick).click()
}

/**
 * Update the clone input in resource row.
 * When user changes quantity/unit of a constituent inside the grouping container, we update the same input/select field
 * of the corresponding resource dataset in query, as those will be sent to backend, NOT the ones in grouping container
 * @param $input
 */
function updateParallelInputInQuestion($input) {
    const name = $($input).attr('name')
    const datasetId = $($input).attr('data-datasetid')
    const value = $($input).val()
    const elementType = $($input).get(0).tagName.toLowerCase() // select OR input
    const $target = $($input).closest('.queryquestion').find(elementType + '[name="' + name + '"]').not('.inGroupingRow').filter(elementType + '[data-datasetid="' + datasetId + '"]')
    if (elementType === 'select') {
        // for unit select: need to focus to set current unit before change
        $target.focus()
    }
    $target.val(value).change()
    unlinkConstituentFromConstruction($($target).closest('td'))
    formChanged = true // triggerFormChanged() doesn't work
}

/**
 * Check if element is child of '.groupEditRow' (on group edit modal)
 * @param $elem
 * @returns {boolean}
 */
function isOnGroupEditModal($elem) {
    return $($elem).closest('.groupEditRow').length > 0;
}

function changeAdditionalQuestionLinkToSelect(linkelem, additionalQuestionId, resourceId, queryId, sectionId, questionId,
                                              answer, parentEntityId, inheritToChildren, importMapperDatasetId, prepend,
                                              mainQuestionAnswer, autocomplete, indicatorId) {
    $(linkelem).popover('destroy');
    var parentTd = $(linkelem).parent();

    var queryString = 'additionalQuestionId=' + additionalQuestionId + '&resourceId=' + resourceId + '&queryId=' + queryId +
        '&sectionId=' + sectionId + '&questionId=' + questionId + '&answer=' + answer + '&parentEntityId=' + parentEntityId +
        '&mainQuestionAnswer=' + mainQuestionAnswer + '&indicatorId=' + indicatorId;

    if (importMapperDatasetId) {
        queryString = queryString + '&importMapperDatasetId=' + importMapperDatasetId;
    }

    if (autocomplete) {
        queryString = queryString + '&autocomplete=true';
    }

    if (isOnGroupEditModal(linkelem)) {
        queryString += '&overrideAutocompleteWidthClass=additionalQuestionAutocompleteGroupEdit'
    }
    removeExistingResourceRowClass(linkelem)
    $(linkelem).remove();

    $.ajax({
        type: 'POST',
        async: false,
        data: queryString,
        url: '/app/sec/util/getAdditionalQuestionSelect',
        beforeSend: function () {
            $(parentTd).append("<i class=\"fas fa-circle-notch fa-spin oneClickColorScheme\"></i>");
            $(parentTd).find(".genericLocalComp").remove();
            if (inheritToChildren) {
                var childrenAdditionalQuestions = $('*[data-identifierForInheritance="' + inheritToChildren + '"]');

                if ($(childrenAdditionalQuestions).length) {
                    $(childrenAdditionalQuestions).find('.serviceLifeButton').trigger('click');
                }
            }
        },
        success: function (data) {
            $(parentTd).find(".fa-circle-notch").remove();

            if (data.nameToRemove) {
                $(parentTd).find("[name='" + data.nameToRemove.replace(".", "\\.") + "']").remove(); // kikkare
            }
            if (prepend) {
                $(parentTd).prepend(data.toRender);
            } else {
                $(parentTd).append(data.toRender);
            }

            if (inheritToChildren) {
                var parentAdditionalQuestions = $('[data-inherit]');
                if (parentAdditionalQuestions.length) {
                    $(parentAdditionalQuestions).each(function () {
                        var identifier = $(this).attr('data-inherit');
                        $(this).children().on('change', function () {
                            var elementType = this.nodeName;
                            inheritSelectValueToChildren(identifier, $(this).val(), elementType);
                        });
                    });
                }
            }

            if (autocomplete) {
                initAdditionalQuestionAutocompletes();
            } else if (typeof addTriggerCalcEventListenerToChildElement == 'function') {
                addTriggerCalcEventListenerToChildElement(parentTd, 'select', 'change')
            }
        }, error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("draw failed for aq select");
        }
    });
}

function removeExistingResourceRowClass(linkelem) {
    $(linkelem).closest('tr').removeClass("existingResourceRow")
}

function changeLocalCompsLinkToSelect(linkelem) {
    var select = $(linkelem).closest('tr').find(".localCompsSelect");
    $(linkelem).popover('destroy');
    $(linkelem).remove();
    $(select).fadeIn();
}

function renderLcaChecker(entityId, indicatorId) {
    var queryString = 'entityId=' + entityId + '&indicatorId=' + indicatorId;

    $.ajax({
        type: 'POST',
        async: true,
        data: queryString,
        url: '/app/sec/entity/resultsLcaCheckerDoughnut',
        success: function (data) {
            if (data.output) {
                $('#lcaCheckerNoGraphContainer').append(data.output.template);

                if (data.output.link) {
                    $("<div id='lcaCheckerAlert' class='alert alert-success'>\n" +
                        "  <button data-dismiss='alert' class='close' type='button'>×</button>\n" +
                        "  <strong>" + data.output.link + "</strong>\n" +
                        "</div>").prependTo("#lcaCheckerAlertDiv");
                }
            }
        }, error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("draw failed for top pie chart");
        }
    });
}

function drawTopPieChart(entityId, indicatorId, calculationRuleId, resulTableClick) {
    var queryString = 'entityId=' + entityId + '&indicatorId=' + indicatorId + '&calculationRuleId=' + calculationRuleId;
    var $topPieGraph = $('#topPieGraph');
    var $topPieGraphWrapper = $('#topPieGraphWrapper');
    var $topDoughnutWrapper = $('#topDoughnutWrapper');
    $topPieGraph.removeClass('warningHighlight');

    $.ajax({
        type: 'POST',
        data: queryString,
        url: '/app/sec/entity/resultsPieGraph',
        beforeSend: function () {
            $topPieGraph.empty()
        },
        success: function (data) {
            if ($topPieGraphWrapper.hasClass('hidden')) {
                $topDoughnutWrapper.addClass('hidden');
                $topDoughnutWrapper.removeAttr('style');
                $topPieGraphWrapper.fadeIn().removeClass('hidden');
            }
            $topPieGraph.append(data.output);
            if (resulTableClick) {
                $('html, body').animate({
                    scrollTop: (0)
                }, 1000);
                setTimeout(function () {
                    $topPieGraph.addClass('warningHighlight');

                }, 1000);

            }
        }, error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("draw failed for top pie chart");
        }
    });
}

function drawResourceSubTypeBars(entityId, indicatorId, calculationRuleId) {
    var queryString = 'entityId=' + entityId + '&indicatorId=' + indicatorId + '&calculationRuleId=' + calculationRuleId;
    var $topPieGraph = $('#resourceSubTypeBars');
    $.ajax({
        type: 'POST',
        data: queryString,
        url: '/app/sec/entity/resourceSubTypeBars',
        beforeSend: function () {
            $topPieGraph.empty()
        },
        success: function (data) {
            $topPieGraph.append(data.output);
        }, error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("draw failed for top pie chart");
        }
    });
}

function drawResourceTypeImpactBars(entityId, indicatorId, calculationRuleId) {
    var queryString = 'entityId=' + entityId + '&indicatorId=' + indicatorId + '&calculationRuleId=' + calculationRuleId;
    var $topPieGraph = $('#resourceTypeImpactsPieChart');
    $.ajax({
        type: 'POST',
        data: queryString,
        url: '/app/sec/entity/resourceTypeImpactBars',
        beforeSend: function () {
            $topPieGraph.empty()
        },
        success: function (data) {
            $topPieGraph.append(data.output);
        }, error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("draw failed for top pie chart");
        }
    });
}

function drawBuildingElementBars(entityId, indicatorId, calculationRuleId) {
    var queryString = 'entityId=' + entityId + '&indicatorId=' + indicatorId + '&calculationRuleId=' + calculationRuleId;
    var $topPieGraph = $('#buildingElementsImpactCategories');
    $.ajax({
        type: 'POST',
        data: queryString,
        url: '/app/sec/entity/buildingElementBars',
        beforeSend: function () {
            $topPieGraph.empty()
        },
        success: function (data) {
            $topPieGraph.append(data.output);
        }, error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("draw failed for top pie chart");
        }
    });
}

function getHighImpactResourcesOnly(childEntityId, indicatorId, calculationRuleId,
                                parentEntityId, accountId, fromPlanetaryReport, callback) {
    var query
    if (childEntityId && indicatorId && calculationRuleId && parentEntityId && accountId) {
        query = '&indicatorId=' + indicatorId + '&childEntityId=' + childEntityId + '&calculationRuleId=' +
            calculationRuleId + "&parentEntityId=" + parentEntityId + "&accountId=" + accountId
    } else if (childEntityId && indicatorId && calculationRuleId && parentEntityId) {
        query = '&indicatorId=' + indicatorId + '&childEntityId=' + childEntityId + '&calculationRuleId=' +
            calculationRuleId + "&parentEntityId=" + parentEntityId
    }
    if (fromPlanetaryReport) {
        query += "&fromPlanetaryReport=" + fromPlanetaryReport
    }

    if (query) {
        appendLoaderToButton('highImpactResourcesHeading');
        $.ajax({
            type: 'POST',
            data: query,
            async: true,
            url: '/app/sec/entity/highestImpactResourcesOnly',
            success: function (data) {
                if (data.output.highestMaterial) {
                    $('.highHimpacts').empty().append(data.output.highestMaterial);
                    callback.call();
                }
            }
        });
    }
}

function getHighImpactResources(childEntityId, indicatorId, calculationRuleId,
                                parentEntityId, accountId, fromPlanetaryReport) {
    var query
    if (childEntityId && indicatorId && calculationRuleId && parentEntityId && accountId) {
        query = '&indicatorId=' + indicatorId + '&childEntityId=' + childEntityId + '&calculationRuleId=' + calculationRuleId + "&parentEntityId=" + parentEntityId + "&accountId=" + accountId
    } else if (childEntityId && indicatorId && calculationRuleId && parentEntityId) {
        query = '&indicatorId=' + indicatorId + '&childEntityId=' + childEntityId + '&calculationRuleId=' + calculationRuleId + "&parentEntityId=" + parentEntityId
    }
    if (fromPlanetaryReport) {
        query += "&fromPlanetaryReport=" + fromPlanetaryReport
    }

    var async = true;
    if (fromPlanetaryReport) {
        async = false;
    }

    if (query) {
        $.ajax({
            type: 'POST',
            data: query,
            async: async,
            url: '/app/sec/entity/highestImpactResources',
            success: function (data) {
                if (fromPlanetaryReport && data.output.impactForPlanetary) {
                    $("#gwpTonnes").text(data.output.impactForPlanetary.gwpTonnes)
                    $("#gwpAnnual").text(data.output.impactForPlanetary.gwpAnnual)
                    $("#socialCostOfImpact").text(data.output.impactForPlanetary.socialCostOfImpact)
                    $("#tree").text(data.output.impactForPlanetary.tree)
                    $("#car").text(data.output.impactForPlanetary.car)
                    $("#flight").text(data.output.impactForPlanetary.flight)
                    $("#laundry").text(data.output.impactForPlanetary.laundry)
                }
                if (data.output.quickInfoChart) {
                    $('.impactsInTextFormat').empty().append(data.output.quickInfoChart);
                }
            }
        });
    }
}

function checkOverflow(el) {
    var curOverflow = el.style.overflow;
    if (!curOverflow || curOverflow === "visible")
        el.style.overflow = "hidden";

    var isOverflowing = el.clientWidth < el.scrollWidth
        || el.clientHeight < el.scrollHeight;

    el.style.overflow = curOverflow;

    return isOverflowing;
}

function sustainablealternatives(resourceId, area, subType, connectedBenchmark, projectCountry, entityId, indicatorId) {
    if (resourceId && area && subType && connectedBenchmark && projectCountry) {
        var body = $('#sustainableAlternativesBody' + resourceId);
        var modal = $('#sustainableAlternativesModal' + resourceId);

        var queryString = "resourceId=" + resourceId + "&area=" + area + "&subType=" + subType + "&connectedBenchmark=" + connectedBenchmark + "&projectCountry=" + projectCountry + "&entityId=" + entityId + "&indicatorId=" + indicatorId;
        $.ajax({
            type: "POST",
            url: "/app/sustainablealternatives",
            data: queryString,
            success: function (data) {
                $(body).empty().append(data.output);
                $(modal).modal();
            }
        });
    }
}

function addResourcesToConstructions(resourceId, resourceTableId, queryId, questionId, sectionId, preventDoubleEntries, doubleEntryWarning, productDataListPage) {
    var fieldName = sectionId + "." + questionId;
    var constructionPage = true;

    var previousSeqNro = $('#' + resourceTableId).find("tr").last().attr('data-sequenceNro');
    if (previousSeqNro === "undefined") {
        previousSeqNro = null;
    }

    var queryString = "resourceId=" + resourceId + "&queryId=" + queryId + '&questionId=' + questionId + '&sectionId=' + sectionId + '&fieldName=' + fieldName + '&constructionPage=' + constructionPage;

    if (previousSeqNro) {
        queryString = "resourceId=" + resourceId + "&queryId=" + queryId + '&questionId=' + questionId + '&sectionId=' + sectionId + '&fieldName=' + fieldName + '&constructionPage=' + constructionPage + '&previousSeqNro=' + previousSeqNro;
    }
    queryString += productDataListPage ? '&productDataListPage=' + productDataListPage : ''

    resourceTableId = "#" + resourceTableId;

    if (resourceId && resourceId.indexOf(".") != -1) {
        resourceId = resourceId.split(".")[0];
    }
    if (!resourceId) {
        resourceId = $(this).val();
        resourceId = resourceId.split(" ")[0];

        if (resourceId && resourceId.indexOf(".") != -1) {
            resourceId = resourceId.split(".")[0];
        }
    }

    var duplicateFound = false;
    if (preventDoubleEntries === "true" || preventDoubleEntries === true) {
        $(resourceTableId).find('input:hidden').each(function () {
            if ($(this).val() === resourceId) {
                duplicateFound = true;
                return false;
            }
        });
    }

    if (!duplicateFound) {
        $.ajax({
            url: '/app/addresource',
            type: "POST",
            data: queryString,
            success: function (data) {
                var wheretoappend = $(resourceTableId)
                $(wheretoappend).append(data.output);
                $(wheretoappend).find('.dropdown').dropdown();

                $('#header').removeClass('hidden');

                var currentResources = $("#currentResources");
                var totalResources = $("#totalResources");

                if (currentResources) {
                    currentResources.html($('.resourceTable > tbody > tr').length);
                }
                if (totalResources) {
                    totalResources.html(parseInt($("#totalResources").html()) + 1);
                }


            }

        })
    } else if (doubleEntryWarning) {
        Swal.fire("", doubleEntryWarning, "warning");
    }

}

function toggleUnderLyingConstructions(elem, rowClass) {
    var rows = $('.' + rowClass);
    var toggler = $('#' + elem);

    $(rows).each(function () {
        if ($(toggler).hasClass("fa-minus-square")) {
            $(this).fadeOut();
        } else {
            $(this).fadeIn().removeClass('hidden')
        }
    });
    $(toggler).toggleClass('fa-minus-square fa-plus-square');
}

function toggleUnderLyingVirtuals(elem, rowClass) {
    var rows = $('.' + rowClass);
    var toggler = $('#' + elem);

    $(rows).each(function () {
        if ($(toggler).hasClass("fa-minus-square")) {
            $(this).fadeOut();
        } else {
            $(this).fadeIn().removeClass('hidden')
        }
    });
    $(toggler).toggleClass('fa-minus-square fa-plus-square');
}

function deleteConstruction(constructionId, accountId, warningTitle, warningText, successText, yes, back) {
    Swal.fire({
        title: warningTitle,
        text: warningText,
        icon: "warning",
        confirmButtonText: yes,
        cancelButtonText: back,
        confirmButtonColor: "red",
        showCancelButton: true,
        reverseButtons: true,
        showLoaderOnConfirm: true,
        preConfirm: function () {
            return new Promise(function (resolve) {
                $.ajax({
                    type: 'POST',
                    url: '/app/sec/construction/delete',
                    data: 'id=' + constructionId + '&accountId=' + accountId,
                    success: function (data, textStatus) {
                        resolve()
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        swal.showValidationMessage("An error ocurred: " + errorThrown)
                    }
                });
            })
        },
        allowOutsideClick: false
    }).then(result => {
        if (result.value) {
            Swal.fire({
                icon: 'success',
                title: successText,
                html: ''
            }).then(function () {
                location.reload();
            });
        }
    });

}

function disableConstructions(warningTitle, warningText, successText, yes, back) {
    Swal.fire({
        title: warningTitle,
        text: warningText,
        icon: "warning",
        confirmButtonText: yes,
        cancelButtonText: back,
        confirmButtonColor: "red",
        showCancelButton: true,
        reverseButtons: true,
        showLoaderOnConfirm: true,
        preConfirm: function () {
            return new Promise(function (resolve) {
                var checkBoxes = $('[data-checkbox]:checkbox:checked');
                var i = 0;

                if (checkBoxes.length) {
                    $(checkBoxes).each(function () {
                        if ($(this).prop('checked')) {
                            var constructionId = $(this).attr('data-constructionId');
                            var accountId = $(this).attr('data-accountId') ? $(this).attr('data-accountId') : "";

                            $.ajax({
                                type: 'POST',
                                url: '/app/sec/construction/disable',
                                data: 'id=' + constructionId + '&accountId=' + accountId,
                                success: function (data) {
                                    if (data) {
                                        i++;
                                    }
                                    if (i === checkBoxes.length) {
                                        resolve();
                                    }
                                }
                            });
                        }
                    });
                } else {
                    resolve();
                }

            })
        },
        allowOutsideClick: false
    }).then(result => {
        if (result.value) {
            Swal.fire({
                icon: 'success',
                title: successText,
                html: ''
            }).then(function () {
                location.reload();
            });
        }
    });

}

function deleteConstructions(warningTitle, warningText, successText, yes, back) {
    Swal.fire({
        title: warningTitle,
        text: warningText,
        icon: "warning",
        confirmButtonText: yes,
        cancelButtonText: back,
        confirmButtonColor: "red",
        showCancelButton: true,
        reverseButtons: true,
        showLoaderOnConfirm: true,
        preConfirm: function () {
            return new Promise(function (resolve) {
                var checkBoxes = $('[data-checkbox]:checkbox:checked');
                var i = 0;

                if (checkBoxes.length) {
                    $(checkBoxes).each(function () {
                        if ($(this).prop('checked')) {
                            var constructionId = $(this).attr('data-constructionId');
                            var accountId = $(this).attr('data-accountId') ? $(this).attr('data-accountId') : "";

                            $.ajax({
                                type: 'POST',
                                url: '/app/sec/construction/delete',
                                data: 'id=' + constructionId + '&accountId=' + accountId,
                                success: function (data) {
                                    if (data) {
                                        i++;
                                    }
                                    if (i === checkBoxes.length) {
                                        resolve();
                                    }
                                }
                            });
                        }
                    });
                } else {
                    resolve();
                }

            })
        },
        allowOutsideClick: false
    }).then(result => {
        if (result.value) {
            Swal.fire({
                icon: 'success',
                title: successText,
                html: ''
            }).then(function () {
                location.reload();
            });
        }
    });
}

function removeConstructionsFromGroup(warningTitle, warningText, successText, yes, back) {
    Swal.fire({
        title: warningTitle,
        text: warningText,
        icon: "warning",
        confirmButtonText: yes,
        cancelButtonText: back,
        confirmButtonColor: "red",
        showCancelButton: true,
        reverseButtons: true,
        showLoaderOnConfirm: true,
        preConfirm: function () {
            return new Promise(function (resolve) {
                var checkBoxes = $('[data-checkbox]:checkbox:checked');
                var i = 0;

                if (checkBoxes.length) {
                    $(checkBoxes).each(function () {
                        if ($(this).prop('checked')) {
                            var constructionId = $(this).attr('data-constructionId');
                            $.ajax({
                                type: 'POST',
                                url: '/app/sec/construction/removeConstructionFromGroup',
                                data: 'id=' + constructionId,
                                success: function (data) {
                                    if (data) {
                                        i++;
                                    }
                                    if (i === checkBoxes.length) {
                                        resolve();
                                    }
                                }
                            });
                        }
                    });
                } else {
                    resolve();

                }


            })
        },
        allowOutsideClick: false
    }).then(result => {
        if (result.value) {
            Swal.fire({
                icon: 'success',
                title: successText,
                html: ''
            }).then(function () {
                location.reload();
            });
        }
    });

}

function addConstructionsToGroup(warningTitle, warningText, successText, yes, back, group) {
    Swal.fire({
        title: warningTitle,
        text: warningText,
        icon: "warning",
        confirmButtonText: yes,
        cancelButtonText: back,
        confirmButtonColor: "red",
        showCancelButton: true,
        reverseButtons: true,
        showLoaderOnConfirm: true,
        preConfirm: function () {
            return new Promise(function (resolve) {
                var checkBoxes = $('[data-checkbox]');
                var i = 0;

                $(checkBoxes).each(function () {
                    if ($(this).prop('checked')) {
                        var constructionId = $(this).attr('data-constructionId');
                        $.ajax({
                            type: 'POST',
                            url: '/app/sec/construction/addConstructionToGroup',
                            data: 'id=' + constructionId + '&group=' + group,
                            success: function (data) {
                                if (data) {
                                    i++;
                                }
                                if (i === checkBoxes.length) {
                                    resolve();
                                }
                            }
                        });
                    }
                });
            })
        },
        allowOutsideClick: false
    }).then(result => {
        if (result.value) {
            Swal.fire({
                icon: 'success',
                title: successText,
                html: ''
            }).then(function () {
                location.reload();
            });
        }
    });

}

function deleteConstructionGroup(groupId, warningTitle, warningText, successText, yes, back) {
    Swal.fire({
        title: warningTitle,
        text: warningText,
        icon: "warning",
        confirmButtonText: yes,
        cancelButtonText: back,
        confirmButtonColor: "red",
        showCancelButton: true,
        reverseButtons: true,
        showLoaderOnConfirm: true,
        preConfirm: function () {
            return new Promise(function (resolve) {
                $.ajax({
                    type: 'POST',
                    url: '/app/sec/construction/deleteConstructionGroup',
                    data: 'id=' + groupId,
                    success: function (data, textStatus) {
                        resolve()
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        swal.showValidationMessage("An error ocurred: " + errorThrown)
                    }
                });
            })
        },
        allowOutsideClick: false
    }).then(result => {
        if (result.value) {
            Swal.fire({
                icon: 'success',
                title: successText,
                html: ''
            }).then(function () {
                location.reload();
            });
        }
    });

}

function sortableResourceTables() {
    $('.sortMe').each(function (event) {
        if ($(this).find("tbody").find("tr").length > 0) {
            $(this).stupidtable();
            $(this).on("aftertablesort", function (event, data) {
                var sorter = "<span class='sorter'> <i class=\"fa fa-sort\" aria-hidden=\"true\"></i></span>";
                var sortableColumns = $('[data-sort]');
                var th = $(this).find("th");
                th.find(".arrow").remove();
                $(sortableColumns).find(".sorter").remove();
                $(sortableColumns).append(sorter);

                var dir = $.fn.stupidtable.dir;
                var arrow = data.direction === dir.ASC ? "<span class='arrow'> <i class=\"fa fa-caret-down\" aria-hidden=\"true\"></i></span>" : "<span class='arrow'> <i class=\"fa fa-caret-up\" aria-hidden=\"true\"></i></span>";
                th.eq(data.column).find('.sorter').remove();
                th.eq(data.column).append(arrow);
                $(this).children().children().each(function () {
                    if (this.hasAttribute('data-constructionRow')) {
                        $('[data-dragmewithparent="' + this.getAttribute("data-constructionRow") + '"]').insertAfter($(this));
                    }
                });

            });
        }
    });
}

function sortablePortfolioTables() {
    $('.sortMe').each(function (event) {
        if ($(this).find("tbody").find("tr").length > 0) {
            $(this).stupidtable({
                "portfolioResult": function (a, b) {
                    var aNum = parseFloat(a.replace(/\s/g, ''));
                    var bNum = parseFloat(b.replace(/\s/g, ''));

                    if (isNaN(aNum) && isNaN(bNum)) {
                        return 0
                    } else if (isNaN(aNum)) {
                        return 1
                    } else if (isNaN(bNum)) {
                        return -1
                    } else {
                        return aNum - bNum
                    }
                }
            });
            $(this).on("aftertablesort", function (event, data) {
                var sorter = "<span class='sorter'> <i class=\"fa fa-sort\" aria-hidden=\"true\"></i></span>";
                var sortableColumns = $('[data-sort]');
                var th = $(this).find("th");
                th.find(".arrow").remove();
                $(sortableColumns).find(".sorter").remove();
                $(sortableColumns).find(".periodContainer").append(sorter);
                var dir = $.fn.stupidtable.dir;
                var arrow = data.direction === dir.ASC ? "<span class='arrow'> <i class=\"fa fa-caret-down\" aria-hidden=\"true\"></i></span>" : "<span class='arrow'> <i class=\"fa fa-caret-up\" aria-hidden=\"true\"></i></span>";
                th.eq(data.column).find('.sorter').remove();
                th.eq(data.column).find(".periodContainer").append(arrow);
            });
        }
    });
}

function appendPortfolioSortignHeads() {
    var arrow = "<span class='sorter'> <i class=\"fa fa-sort\" aria-hidden=\"true\"></i></span>";
    $('.sorter').remove();
    $(".arrow").remove();
    $('[data-sort]').find(".periodContainer").append(arrow);
}

function appendSortignHeads() {
    const sortableColumns = $('[data-sort]');
    $(sortableColumns).find(".sorter").remove();
    const arrow = "<span class='sorter'> <i class=\"fa fa-sort\" aria-hidden=\"true\"></i></span>";
    $(sortableColumns).append(arrow);
}

function removeGroupingRow(id, manualId) {
    if (id) {
        var row = $("#" + id);

        if ($(row).length) {
            var tbody = $(row).parent();
            $(row).remove();

            if ($(tbody).children().length < 1) {
                $(tbody).append("<tr class=\"hiddenDummyRow\"><td colspan=\"99\" class=\"hiddenDummyTd\"><div class=\"dragNDrop\">Drag and drop desired materials</div></td></tr>");
                $(tbody).removeClass('dragNDropTbody');
            }
            $("[data-manualidformoving='" + manualId + "']").removeClass('inGrouping');
        }
    }
}

var dragulas = [];

function reinitDragginContainerForGrouping(entityId, indicatorId, queryId, sectionId, questionId) {
    $.each(dragulas, function (key, dragula) {
        // Todo: only destroy the dragula user is currently grouping?
        dragula.destroy();
    });

    $('.dragginContainer').each(function (event) {
        var resourceRowContainer = this.id;
        var groupingContainerId = $(this).attr("data-questionId") + "RowGroupingContainerTableBody";

        var drake = dragula([document.getElementById(resourceRowContainer), document.getElementById(groupingContainerId)], {
            copy: function (el, source) {
                return source === document.getElementById(resourceRowContainer)
            },
            accepts: function (el, target) {
                return target !== document.getElementById(resourceRowContainer)
            },
            copySortSource: false,
            revertOnSpill: true,
            removeOnSpill: false,
            mirrorContainer: document.body,
            ignoreInputTextSelection: true,
            moves: function (el, container, handle) {
                return handle.classList.contains('handle');
            }
        }).on('drag', function (el) {
            el.classList.remove("highlighted");
            el.className += ' cursorMove';
            $('.popover').remove();
        }).on('drop', function (el, container) {
            el.classList.remove("cursorMove");
            $('.popover').remove();
            $('.cursorMove').removeClass('cursorMove');
            renderGroupViewRow(el, container, entityId, indicatorId, queryId, sectionId, questionId)
        }).on('shadow', function (el, container) {
            if ($(container).find('.hiddenDummyRow').length) {
                $(el).hide();
            } else {
                $(el).children().first().attr("colspan", "99");
            }
        });
        dragulas.push(drake);
    });
}

function draggableRows() {
    $.each(dragulas, function (key, dragula) {
        dragula.destroy();
    });
    $('.dragginContainer').each(function (event) {
        var drake = dragula([document.getElementById(this.id)], {
            copy: false,
            copySortSource: false,
            revertOnSpill: true,
            removeOnSpill: false,
            mirrorContainer: document.body,
            ignoreInputTextSelection: true,
            moves: function (el, container, handle) {
                return handle.classList.contains('handle');
            }
        }).on('drag', function (el) {
            el.classList.remove("highlighted");
            el.className += ' cursorMove'
            $('.popover').remove();
        }).on('drop', function (el, container) {
            el.className += ' highlighted';
            el.classList.remove("cursorMove");
            reSequenceStuff(container);
            setTimeout(function () {
                removeClass(el, 'highlighted');
            }, 500);
        });
        dragulas.push(drake);
    });
}

function reSequenceStuff(container) {
    var i = 0;
    $(container).children().each(function () {
        if (this.hasAttribute('data-constructionRow')) {
            $('[data-dragmewithparent="' + this.getAttribute("data-constructionRow") + '"]').insertAfter($(this));
        }

    });

    $(container).children().each(function () {
        $(this).children().children().each(function () {
            if ($(this).attr('data-persistedSeqNro')) {
                this.value = i
            }
        });
        this.setAttribute("data-sequenceNro", i);
        i++;
    });
}

function removeClass(element, className) {
    $(element).removeClass(className);
}

function initializeLifeCycleImpactsChart(childEntityId, indicatorId, calculationRuleId, comingFromCompare) {
    var queryString = "childEntityId=" + childEntityId + "&indicatorId=" + indicatorId + "&calculationRuleId=" + calculationRuleId;
    if (comingFromCompare) {
        queryString = queryString + '&comingFromCompare=' + comingFromCompare;
    }
    var lifeCycleImpactsChartDiv = $('#lifeCycleImpactsChartWrapper');
    var actualChart = $('#individualChartContainer' + childEntityId);
    $('#loadingSpinner').show();

    $.ajax({
        type: "POST",
        url: '/app/sec/entity/drawLifeCycleImpactsChart',
        data: queryString,
        success: function (data) {
            $('#loadingSpinner').hide();
            if (!comingFromCompare) {
                $(lifeCycleImpactsChartDiv).empty().append(data).fadeOut().fadeIn();
            } else {
                $(lifeCycleImpactsChartDiv).append(data).fadeOut().fadeIn();
            }

        }
    });
}

function renderAllBreakdowns(childEntityId, indicatorId, calculationRuleId) {

    var queryString = "childEntityId=" + childEntityId + "&indicatorId=" + indicatorId + "&calculationRuleId=" + calculationRuleId + '&allCategoryBreakDowns=true';
    var hiddenChartDiv = $('#allBreakDowns');
    $(hiddenChartDiv).children().hide();
    $('#loadingSpinner').show();
    $.ajax({
        type: "POST",
        url: '/app/sec/entity/drawLifeCycleImpactsChart',
        data: queryString,
        success: function (data) {
            $(hiddenChartDiv).children().show();
            $('#loadingSpinner').hide();
            $(hiddenChartDiv).append(data).fadeOut().fadeIn();
        }
    });
}

/**
 * check value of input with class 'lockDataset' in row to determine if dataset is locked
 * @param $row
 * @returns {boolean}
 */
function isLockedDataset($row) {
    return $($row).find('.lockDataset').val() === 'true'
}

/**
 * Lock / unlock resource row
 * Also disable / enable dropdown items of resource row. HUOM! We also have dataset verification that also disables / enables these items. Check method toggleStatusResourceRowDropDownItems()
 * @param linkElement
 * @param uniqueConstructionIdentifier
 * @param lockText
 * @param unlockText
 */
function toggleRowLocked(linkElement, uniqueConstructionIdentifier, lockText, unlockText) {
    var row = $(linkElement).closest('tr');
    var dropDown = $(linkElement).parent().parent();

    if ($(row).length) {
        var lockDataset = $(row).find('.lockDataset');

        if ($(lockDataset).length) {
            var isLocked = isLockedDataset(row);
            var newLockState = isLocked ? false : true;
            $(lockDataset).val(newLockState);

            var nameContainer = $(row).find('.resourceRowNameCell');
            var dropDownListItems = $(dropDown).find('li');

            if (newLockState === true) {
                dropDown.attr('data-datasetIsLocked', 'true')
                $(nameContainer).prepend("<i class='fa fa-lock queryDatasetRowLocked' aria-hidden='true'></i>");
                // Retarded way of not applying stuff to this list item
                dropDownListItems.addClass('removeClicks').filter('.lockDatasetLi, .unlockVerifiedDatasetLi, .verifyDatasetLi').removeClass('removeClicks');
                dropDownListItems.find('.removeResourceRowButton').addClass('notRemoveable');
                var lockedQuestions = $(row).find('.lockableDatasetQuestion');
                $(lockedQuestions).removeClass("lockableDatasetQuestion");
                $(lockedQuestions).addClass("lockedDatasetQuestion");
                $(linkElement).html('<i class="fa fa-unlock"></i> ' + unlockText);
            } else {
                dropDown.attr('data-datasetIsLocked', 'false')
                $(nameContainer).find('.fa-lock').not('.verifiedDatasetIcon').remove();
                // dropdown items can be disabled by verified or locked status of dataset. Need to make sure both are off before enabling the items
                if (dropDown.attr('data-datasetIsVerified') !== 'true') {
                    dropDownListItems.removeClass('removeClicks');
                    dropDownListItems.find('.removeResourceRowButton').removeClass('notRemoveable');
                }
                // The delete resource link can be enabled always
                dropDownListItems.filter('.removeResourceLi').removeClass('removeClicks')
                var lockedQuestions = $(row).find('.lockedDatasetQuestion');
                $(lockedQuestions).removeClass("lockedDatasetQuestion");
                $(lockedQuestions).addClass("lockableDatasetQuestion");
                $(linkElement).html('<i class="fa fa-lock"></i> ' + lockText);
            }

            if (uniqueConstructionIdentifier) {
                var constituents = $('[data-dragMeWithParent=\"' + uniqueConstructionIdentifier + 'row\"');

                $.each(constituents, function () {
                    var lockConstituent = $(this).find('.lockDataset');

                    if ($(lockConstituent).length) {
                        $(lockConstituent).val(newLockState);
                        var constituentRow = $(this).closest('tr');

                        var constituentNameContainer = $(constituentRow).find('.resourceRowNameCell');

                        if (newLockState === true) {
                            var lockedConstituentQuestions = $(constituentRow).find('.lockableDatasetQuestion');
                            $(lockedConstituentQuestions).removeClass("lockableDatasetQuestion");
                            $(lockedConstituentQuestions).addClass("lockedDatasetQuestion");
                            $(constituentNameContainer).prepend("<i class='fa fa-lock queryDatasetRowLocked' aria-hidden='true'></i>");
                        } else {
                            var lockedConstituentQuestions = $(constituentRow).find('.lockedDatasetQuestion');
                            $(lockedConstituentQuestions).removeClass("lockedDatasetQuestion");
                            $(lockedConstituentQuestions).addClass("lockableDatasetQuestion");
                            $(constituentNameContainer).find('.fa-lock').not('.verifiedDatasetIcon').remove();
                        }
                    }
                });
            }
        }
        triggerFormChanged();
    }
}

function triggerFormChanged() {
    var queryForm = $("#queryForm");

    if ($(queryForm).length) {
        formChanged = true;
        if (!$("#formChanged").length) {
            $(queryForm).append('<input type=\"hidden\" id=\"formChanged\" name=\"formChanged\" value=\"true\" />');
        }
        $(queryForm).trigger('change');
    }
    var firstQueryInputHelp = $("#firstQueryInputHelp");
    var hiddenTextHelp = $("#hiddenHelp").text();

    if ($(firstQueryInputHelp).length > 0 && hiddenTextHelp.length > 0) {
        $(firstQueryInputHelp).removeClass("help_finger large").addClass("help_finger_up pull-right medium");
        $(firstQueryInputHelp).text(hiddenTextHelp);
    }
}

function demolishConstruction(uniqueConstructionIdentifier, trId) {
    var row = $('#' + trId);
    $(row).removeClass("existingResourceRow")
    var tbody = $(row).parent();

    if (row) {
        $(row).effect('explode', {pieces: 15}, 1000).remove();
    }
    var subDatasetsOfConstruction = $('[data-dragMeWithParent=\"' + uniqueConstructionIdentifier + 'row\"');

    $(subDatasetsOfConstruction).each(function () {
        $(this).fadeIn(2000).removeClass('hidden');
        $(this).removeAttr('data-dragmewithparent');
        $(this).removeClass('existingResourceRow');
        $(this).children().each(function () {
            $(this).children().each(function () {
                if ($(this).attr('data-persisted')) {
                    $(this).val("");
                }
                $(this).removeAttr('data-uniqueConstructionIdentifier');
                $(this).removeAttr('readonly');
                if ($(this).is('a')) {
                    $(this).removeClass('hidden');
                }
            });
        });
    });
    reSequenceStuff(tbody);
    triggerFormChanged();
    switchOnTriggerCalculationFlag();
}
function editConstruction(uniqueConstructionIdentifier, trId,entityId, indicatorId, queryId, sectionId, questionId,createConstructionsAllowed,publicGroupAllowed) {
    var parentRow = $('#' + trId);
    var manualIds = [];
    var tbody = $(parentRow).parent();
    var rows = $(tbody).children("tr").not(parentRow);
    $.each($(rows), function (i, row) {
        if ($(row).attr("data-dragmewithparent") == $(parentRow).attr("data-constructionrow")) {
            manualIds.push($(row).attr("data-manualidformoving"));
            $(row).removeClass("selection-background");
        }

    });

    openGroupingContainer(entityId, indicatorId, queryId, sectionId, questionId, manualIds,parentRow,createConstructionsAllowed,publicGroupAllowed)
}

function saveCanvasBase64ToUserSession(canvasId, chartExportSetting) {

    var canvasAsBase64 = svgToImage(canvasId, chartExportSetting);
    if (canvasAsBase64) {
        canvasAsBase64 = encodeURIComponent(canvasAsBase64);

        $.ajax({
            url: '/app/sec/fileExport/saveCanvasBase64ToUserSession',
            data: 'canvasId=b64' + canvasId + '&data=' + canvasAsBase64,
            type: 'POST',
            async: false,
            success: function (data) {
            }
        })
    }

}

function isMicrosoftBrowser() {

    if ((navigator.userAgent.indexOf("MSIE") != -1) || (!!document.documentMode == true)) {
        return true
    }

    if (window.navigator.userAgent.indexOf("Edge") > -1 || !!navigator.userAgent.match(/Trident\/7\./)) { //   "/Edge/.test(navigator.userAgent)" --> regualr expression
        return true
    }

    return false
}

function downloadChart(element, canvasId, title) {

    var url = canvasToImage(canvasId, "white");

    if (isMicrosoftBrowser()) {
        var html = "<p>Right-click on image below and Save-Picture-As</p><br><br>";
        html += "<img src='" + url + "' alt='from canvas'/>";
        var tab = window.open();
        tab.document.write(html);
    } else {
        element.href = url;
        element.id = "dLink";
        if (title) {
            element.download = title + ".png";
        } else {
            element.download = "chart.png";
        }
    }

}

function canvasToImage(canvasId, backgroundColor, canvasObj) {
    var canvasElement
    if (canvasId) {
        canvasElement = document.getElementById(canvasId);
    } else {
        canvasElement = canvasObj
    }
    if (canvasElement) {
        var context = canvasElement.getContext('2d');
        canvas = context.canvas;
//cache height and width
        var w = canvas.width;
        var h = canvas.height;

        var data;

//get the current ImageData for the canvas.
        data = context.getImageData(0, 0, w, h);

//store the current globalCompositeOperation
        var compositeOperation = context.globalCompositeOperation;

//set to draw behind current content
        context.globalCompositeOperation = "destination-over";

//set background color
        context.fillStyle = backgroundColor;

//draw background / rect on entire canvas
        context.fillRect(0, 0, w, h);

//get the image data from the canvas
        var imageData = this.canvas.toDataURL("image/png");

//clear the canvas
        context.clearRect(0, 0, w, h);

//restore it with original / cached ImageData
        context.putImageData(data, 0, 0);

//reset the globalCompositeOperation to what it was
        context.globalCompositeOperation = compositeOperation;

//return the Base64 -> blob encoded data url string
        return imageData;

    }
}

//get svg and convert svg to base64
function svgToImage(svgContainerID, chartExportSetting, typeImage) {
    try {
        var chart = $("#" + svgContainerID).highcharts();
        if (chart) {
            if (chartExportSetting) {
                chart.drillUp()
                chart.update(chartExportSetting)
            }
            var svgAsString = chart.getSVG();
            var base64Data
            if (typeImage == "png") {
                base64Data = svgToCanvasToImage(svgAsString, svgContainerID)
            } else {
                var svgAsElemB64 = utoa(svgAsString);
                var b64Start = 'data:image/svg+xml;base64,';
                base64Data = b64Start + svgAsElemB64
            }
            return base64Data
        } else {
            return ""
        }
    } catch (e) {
        throw 'Something is wrong, catch me in parent method!'
    }
}

//Fix string contains an invalid character error of btoa function
function utoa(data) {
    return btoa(unescape(encodeURIComponent(data)));
}

function svgToCanvasToImage(svgString, svgContainerID) {
    var canvas = document.createElement("canvas")
    canvas.width = 1100;
    canvas.height = 500;
    $("#png-container").append(canvas)
    var status = drawInlineSVG(canvas.getContext("2d"), svgString, function () {
        var canvasAsBase64 = encodeURIComponent(canvas.toDataURL("image/png"));
        $.ajax({
            async: false,
            url: '/app/sec/fileExport/saveCanvasToUserSession',
            data: 'canvasId=' + svgContainerID + '&data=' + canvasAsBase64,
            type: 'POST',
            success: function (data) {
                canvas.width = 0;
                canvas.height = 0;
            }
        })
    });
    return status
}

function drawInlineSVG(ctx, rawSVG, callback) {

    var svg = new Blob([rawSVG], {type: "image/svg+xml;charset=utf-8"}),
        domURL = self.URL || self.webkitURL || self,
        url = domURL.createObjectURL(svg),
        img = new Image;

    img.onload = function () {
        ctx.drawImage(this, 0, 0);
        domURL.revokeObjectURL(url);
        callback(this);
    };

    img.src = url;
}

// usage:

function changeUserUniSystem(value, userId) {
    if (value && userId) {
        $.ajax({
            url: '/app/sec/user/changeUnitSystem',
            data: 'value=' + value + '&userId=' + userId,
            type: 'POST',
            success: function (data) {
            }
        })
    }
}

function changeUserLocale(value, userId) {
    if (value && userId) {
        $.ajax({
            url: '/app/sec/user/changeLocale',
            data: 'value=' + value + '&userId=' + userId,
            type: 'POST',
            success: function (data) {
            }
        })
    }
}

function nextModal(button, modalId, newModalId, parent, lastChild, headerForLoginModal, settingsSet) {
    var newModal = $(newModalId);
    var oldModal = $(modalId);
    var header = $('.headerForLoginModal');
    if (settingsSet) {
        userSetSettings();
    }
    if (parent) {
        $('.modal-backdrop').remove();
        $(newModal).modal(
            {
                backdrop: 'static',
                keyboard: false
            }
        );

    } else {
        $(newModal).removeClass('hidden');
    }
    if (headerForLoginModal) {
        $(header).empty().append(headerForLoginModal)
    }

    if (lastChild) {
        $(button).hide();
    }
    $(modalId).replaceWith(newModal);
}

function changeUsername(elem, userId) {
    var inputField = $("#newUserUsername");
    var value = inputField.val();

    if (value && userId) {
        $(elem).css('opacity', '0.5');
        $(elem).css('pointer-events', 'none');

        var json = {value: value, userId: userId};

        $.ajax({
            url: '/app/sec/user/changeUserName',
            data: JSON.stringify(json),
            contentType: "application/json; charset=utf-8",
            type: 'POST',
            success: function (data) {
                if (data.output === "ok") {
                    $("#newUserUsername").css('border', '1px solid lightgreen');
                } else {
                    inputField.css('border', '1px solid red');
                    $(inputField).popover('destroy');
                    $(inputField).popover({
                        placement: 'top',
                        template: '<div class="popover importmapperPopover" ><div class="arrow"></div><div class="popover-content" style="max-width: 500px; max-height: 500px; overflow: auto"></div></div>',
                        trigger: 'manual',
                        html: true,
                        content: data.output,
                        container: 'body'
                    });
                    $(inputField).popover('show');
                    setTimeout(function () {
                        $(inputField).popover('hide');
                    }, 4000);
                }
                $(elem).css('opacity', '1');
                $(elem).css('pointer-events', '');
            }
        });
    }
}

function appendLoader(elementId) {
    var loader = $("#localizedSpinner"); // on main.gsp, this is to have it localized with g:message
    var copy = loader.clone();
    copy.css("display", ""); // clone hidden element and display
    var element = document.getElementById(elementId);
    $(element).empty().append(copy);
}

function killLoginPrompt() {
    $.ajax({
        url: '/app/sec/main/killPrompt',
        type: 'POST'
    });
}

function userSetSettings() {
    $.ajax({
        url: '/app/sec/user/userHasSetSettings',
        type: 'POST'
    });
}

function renderDatasetInformation(renderLocation, datasetManualId, queryId, sectionId, questionId, entityId, constructionId) {

    if (datasetManualId && renderLocation) {
        var queryString = "";

        if (entityId && queryId && sectionId && questionId) {
            queryString = 'datasetManualId=' + datasetManualId + '&queryId=' + queryId + '&sectionId=' + queryId + '&questionId=' + questionId + '&entityId=' + entityId;
        }
        if (constructionId) {
            queryString = 'datasetManualId=' + datasetManualId + '&constructionId=' + constructionId;
        }
        queryString += '&ajaxCall=true'
        $.ajax({
            url: '/app/sec/util/renderDatasetInformation',
            data: queryString,
            type: 'POST',
            success: function (data) {
                $(renderLocation).append(data.output);
            }
        });
    }
}

function validateNMDThicknesses(elem) {
    var start = elem.selectionStart;
    end = elem.selectionEnd;
    var val = parseInt($(elem).val());
    var min = parseInt($(elem).attr("data-min"));
    var max = parseInt($(elem).attr("data-max"));

    if (!isNaN(val)) {
        var corrected = false;
        if (!isNaN(min)) {
            if (min > val) {
                $(elem).val(min);
                corrected = true;
            }
        }

        if (!isNaN(max)) {
            if (max < val) {
                $(elem).val(max);
                corrected = true;
            }
        }

        if (!corrected) {
            $(elem).val(val);
        }
    } else {
        $(elem).val("");
    }
    elem.setSelectionRange(start, end);
}

/* REL-304 we do not need this function anymore
function openNMDProductEditModal(elem, rowId, datasetId, resourceId) {
    var head = $('#NMDProductEditModalHeading');
    var body = $('#NMDProductEditModalBody');
    var modal = $('#NMDProductEditModal');
    var error = $('#NMDProductEditModalError');
    var nmdProfileSetString = $(elem).closest('tr').find("input[name*='_nmdProfileSetString_']").val();

    var queryString = "resourceId=" + resourceId + "&rowId=" + rowId + "&nmdProfileSetString=" + nmdProfileSetString;

    $.ajax({
        type: "POST",
        url: '/app/sec/nmdApi/getEditModal',
        data: queryString,
        beforeSend: function () {
            $(head).empty();
            appendLoader('NMDProductEditModalBody');
            $(modal).modal({
                backdrop: 'static',
                keyboard: false
            });
        },
        success: function (data) {
            if (data) {
                $(head).empty().append(data.heading);
                $(body).empty().append(data.body);
            }
        }
    });
}
 */

/* REL-304 deprecate nmd3ElementId >>> no need for this openNMDProductAddModal any more
// $(element).attr("data-entityId"), $(element).attr("data-selectId"),$(element).attr("data-indicatorId"), $(element).attr("data-queryId"), $(element).attr("data-sectionId"), $(element).attr("data-questionId"), $(element).attr("data-resourceTableId"), $(element).attr("data-fieldName")
function openNMDProductAddModal(nmd3ElementID, constructionId, uniqueConstructionIdentifier, entityId, selectId, indicatorId, queryId, sectionId, questionId, resourceTableId, fieldName) {
    var head = $('#NMDProductAddModalHeading');
    var body = $('#NMDProductAddModalBody');
    var modal = $('#NMDProductAddModal');
    var error = $('#NMDProductAddModalError');

    var queryString = "nmd3ElementID=" + nmd3ElementID + "&constructionId=" + constructionId + "&uniqueConstructionIdentifier=" + uniqueConstructionIdentifier + "&entityId=" + entityId + "&selectId=" + selectId + "&indicatorId=" + indicatorId + "&queryId=" + queryId + "&sectionId=" + sectionId + "&questionId=" + questionId + "&resourceTableId=" + resourceTableId + "&fieldName=" + fieldName;

    $.ajax({
        type: "POST",
        url: '/app/sec/nmdApi/getAddModal',
        data: queryString,
        beforeSend: function () {
            $(head).empty();
            appendLoader('NMDProductAddModalBody');
            $(modal).modal({
                backdrop: 'static',
                keyboard: false
            });
        },
        success: function (data) {
            if (data) {
                $(head).empty().append(data.heading);
                $(body).empty().append(data.body);
            }
        }
    });
}
*/
function addNmdConstruction(mirrorResourceId, constructionId, uniqueConstructionIdentifier, entityId, selectId, indicatorId, queryId, sectionId, questionId, resourceTableId, fieldName, date) {
    var error = $('#NMDProductAddModalError');
    var totalProductRows = $('#NMDProductAddModalBody').find('.nmd3TotalRow');
    var totalProductSelected = $(totalProductRows).find('.nmd3TotalProductCheckbox').is(':checked');

    if (totalProductRows.length && totalProductSelected) {
        addResourceFromSelect(entityId, false, false, selectId, indicatorId, queryId, sectionId, questionId, resourceTableId, fieldName, false, true, "", uniqueConstructionIdentifier, "", "", "", "", mirrorResourceId);
        addNmdConstructionResources(constructionId, entityId, false, false, selectId, indicatorId, queryId, sectionId, questionId, resourceTableId, fieldName, false, true, "", uniqueConstructionIdentifier, "", false, date);
        $('#NMDProductAddModal').modal('toggle');
    } else {
        var componentRows = $('#NMDProductAddModalBody').find('.nmd3ComponentRow').not('.satisfiedNmdRow');
        var partialRows = $('#NMDProductAddModalBody').find('.nmd3PartialComponentRow').not('.satisfiedNmdRow');
        var atleastOneComponentSelected = false;

        if (partialRows.length) {
            atleastOneComponentSelected = true;
        } else {
            for (var j = 0; j < componentRows.length; j++) {
                var componentRow = componentRows[j];
                var selectedOption = $(componentRow).find('.kikkareselector option:selected').val();

                if (selectedOption && $('.partialProfileSet' + selectedOption).length) {
                    atleastOneComponentSelected = true;
                    break;
                }
            }
        }

        if (atleastOneComponentSelected) {
            addResourceFromSelect(entityId, false, false, selectId, indicatorId, queryId, sectionId, questionId, resourceTableId, fieldName, false, true, "", uniqueConstructionIdentifier, "", "", "", "", mirrorResourceId);
            addNmdConstructionResources(constructionId, entityId, false, false, selectId, indicatorId, queryId, sectionId, questionId, resourceTableId, fieldName, false, true, "", uniqueConstructionIdentifier, "", true, date);
            $('#NMDProductAddModal').modal('toggle');
        } else {
            $(error).empty();
            $("<div id='floatingLicenseError' class='alert alert-error'><button data-dismiss='alert' class='close' type='button'>×</button><strong>Nothing to add, please select a total product or partial products for your construction.</strong></div>").prependTo(error);
            $(error).show();
            setTimeout(function () {
                $(error).fadeOut();
            }, 3000);
            return false;
        }
    }
}

function updateNmdConstruction(rowId, productId, date) {
    var row = document.getElementById(rowId);

    if ($(row).length && productId) {
        var nmdProfileSetStringInput = $(row).find("input[name*='_nmdProfileSetString_']");

        if ($(nmdProfileSetStringInput).length) {
            var nmdProfileSetString = "";
            var profileSetClass = "profileSet" + productId;
            var profileSetRows = $('#NMDProductEditModalBody').find('.' + profileSetClass);

            $.each(profileSetRows, function (asd, partialRow) {
                var profileSetId = $(partialRow).attr('data-profileSetId');
                var x1 = parseInt($(partialRow).find('.nmd3X1').val());
                var x2 = parseInt($(partialRow).find('.nmd3X2').val());

                if (isNaN(x1) || !isFinite(x1)) {
                    x1 = 0;
                }
                if (isNaN(x2) || !isFinite(x2)) {
                    x2 = 0;
                }

                if (profileSetId) {
                    if (nmdProfileSetString) {
                        nmdProfileSetString = nmdProfileSetString + "," + profileSetId + ":" + x1 + ":" + x2 + ":" + date
                    } else {
                        nmdProfileSetString = profileSetId + ":" + x1 + ":" + x2 + ":" + date
                    }
                }
            });
            $(nmdProfileSetStringInput).val(nmdProfileSetString);
            $('#NMDProductEditModal').modal('toggle');
        }
    }
}


function openFloatingLicenseModal(licenseId, rerender, checkInCurrentUser, reloadOnFloatingStatusChange, reload) {
    var head = $('#floatingLicenseModalHeading');
    var body = $('#floatingLicenseModalBody');
    var modal = $('#floatingLicenseModal');
    var error = $('#floatingLicenseModalError');
    var errorFromCheckIn;

    var queryString = "licenseId=" + licenseId;

    if (reloadOnFloatingStatusChange) {
        queryString = queryString + '&reloadOnFloatingStatusChange=true';
    }

    $.ajax({
        type: "POST",
        url: '/app/sec/license/getFloatingLicenseForModal',
        data: queryString,
        beforeSend: function () {
            if (rerender) {
                appendLoader('floatingLicenseModalBody');
            }

            if (checkInCurrentUser === false || checkInCurrentUser === true) {
                $.ajax({
                    type: "POST",
                    async: false,
                    url: '/app/sec/license/toggleCurrentUserInFloatingLicense',
                    data: "licenseId=" + licenseId + "&checkInCurrentUser=" + checkInCurrentUser,
                    success: function (data) {
                        if (data !== "ok") {
                            errorFromCheckIn = data;
                        }
                    }
                });
            }
        },
        success: function (data) {
            if (data) {
                if (rerender) {
                    if (reload && !errorFromCheckIn) {
                        location.reload(true);
                    } else {
                        setTimeout(function () {
                            $(head).empty().append(data.heading);
                            $(body).empty().append(data.body);
                            $('.dropdown-toggle').dropdown();
                            if (errorFromCheckIn) {
                                $(error).empty();
                                $("<div id='floatingLicenseError' class='alert alert-error'><button data-dismiss='alert' class='close' type='button'>×</button><strong>" + errorFromCheckIn + "</strong></div>").prependTo(error);
                                $(error).show();
                                setTimeout(function () {
                                    $(error).fadeOut();
                                }, 3000);
                            }
                        }, 500);
                    }
                } else {
                    $(head).empty().append(data.heading);
                    $(body).empty().append(data.body);
                    $(modal).modal();
                    $('.dropdown-toggle').dropdown();
                    if (errorFromCheckIn) {
                        $(error).empty();
                        $("<div id='floatingLicenseError' class='alert alert-error'><button data-dismiss='alert' class='close' type='button'>×</button><strong>" + errorFromCheckIn + "</strong></div>").prependTo(error);
                        $(error).show();
                        setTimeout(function () {
                            $(error).fadeOut();
                        }, 3000);
                    }
                }
            }
        }
    });
}

function openIndicatorBenchmarks(indicatorId, childEntityId, parentEntityType, childName, parentName, countryForPrioritization, benchmarkId) {
    var head = $('#indicatorBenchmarkModalHeading');
    var body = $('#indicatorBenchmarkModalBody');
    var modal = $('#indicatorBenchmarkModal');

    var queryString = "childEntityId=" + childEntityId + "&indicatorId=" + indicatorId + "&parentEntityType=" + parentEntityType + "&countryForPrioritization=" + countryForPrioritization + "&benchmarkId=" + benchmarkId;
    $.ajax({
        type: "POST",
        url: '/app/sec/util/indicatorBenchmarks',
        data: queryString,
        success: function (data) {
            if (data.output) {
                $(head).empty().append("Benchmark for " + parentName + " - " + childName);
                $(body).empty().append(data.output);
                $(modal).modal();
            }
        }
    });
}

function openDesignLockModal(entityId, designId, designName, superUser) {
    var modal = $('#lockEntityModal');
    $('#lockEntityModalEntityId').val(entityId);
    $('#lockEntityModalDesignId').val(designId);
    $('#lockEntityModalSuperUser').val(superUser);
    $('#lockEntityModalEntityName').html(designName);
    $(modal).modal();
}

function openDesignSelector(indicatorId, parentEntityId) {
    var head = $('#chooseDesignsModalHeading');
    var body = $('#chooseDesignsModalBody');
    var modal = $('#chooseDesignsModal');

    var queryString = "parentEntityId=" + parentEntityId + "&indicatorId=" + indicatorId;
    $.ajax({
        type: "POST",
        url: "/app/compiledreportdesigns",
        data: queryString,
        success: function (data) {
            if (data.output.htmltable) {
                $(head).empty().append(data.output.heading);
                $(body).empty().append(data.output.htmltable);
                $(modal).modal();
            }
        }
    });
}

function rsetFormvalidateAndSubmit(formId) {
    $("#" + formId).submit();
    $('#chooseDesignsModal').modal('toggle')
}

/**
 * Uncheck and disable other checkboxes for choosing which design to be the parcelle during RSEE export
 *
 * (Currently only available for RE2020)
 * @param checkbox
 */
function uncheckOtherParcelleCheckbox(checkbox) {
    const isChecked = isCheckboxChecked(checkbox)
    const rsetTable = $(checkbox).closest('table')
    $(rsetTable).find('.parcelleContainer input[type="checkbox"]').not(checkbox).each(function () {
        if (isChecked) {
            uncheckSwitch($(this), true)
        } else {
            enableSwitch($(this))
        }
    })
}

function resolveBatiments(that, parentEntityId, questionId, designId) {
    let selectedBatiments = [];
    const row = $(that).closest('tr');
    const batimentIndex = $(that).val();
    const batimentSelect = '.batimentSelect' + questionId

    // Get all selected
    $(batimentSelect).each(function () {
        var val = $(this).val();
        if (val) {
            selectedBatiments.push(val);
        }
    });

    // Clear all disabled
    $(batimentSelect).each(function () {
        $("option", this).each(function () {
            $(this).attr("disabled", false);
        });
    });

    var target = $('#downloadButtonContainer');
    var zoneMappingContainer = $(row).find('.zoneMappingContainer');
    // Disable selected
    if (selectedBatiments.length) {
        $(batimentSelect).each(function () {
            var selected = $(this).val();
            $("option", this).each(function () {
                if (selectedBatiments.indexOf(this.value) > -1 && this.value !== selected) {
                    $(this).attr("disabled", true);
                }
            });
        });

        if (batimentIndex) {
            $(zoneMappingContainer).html("<i class=\"fas fa-circle-notch fa-spin oneClickColorScheme\"></i>");

            const json = {
                frenchTool: true,
                parentId: parentEntityId,
                questionId: questionId,
                entityId: designId,
                batimentIndex: batimentIndex
            }

            $.ajax({
                type: "POST",
                url: "/app/compiledreportdownloadlink",
                data: JSON.stringify(json),
                contentType: "application/json; charset=utf-8",
                success: function (data) {
                    if (data) {
                        target.html(data.button);
                        zoneMappingContainer.html(data.zonesSelect);

                        if (data.triggerChange === true) {
                            $('.zoneSelect').trigger("change");
                        }
                    } else {
                        target.html('');
                        zoneMappingContainer.html('');
                    }
                }
            });
        } else {
            zoneMappingContainer.html('');
        }
    } else {
        target.html('');
        zoneMappingContainer.html('');
    }
}

function resolveZones(that) {
    var selectedZones = [];

    // Get all selected
    $('.zoneSelect').each(function () {
        var val = $(this).find('option:selected').attr("data-value");
        if (val) {
            selectedZones.push(val);
        }
    });

    // Clear all disabled
    $('.zoneSelect').each(function () {
        $("option", this).each(function () {
            $(this).attr("disabled", false);
        });
    });

    // Disable selected
    if (selectedZones.length) {
        $('.zoneSelect').each(function () {
            var selected = $(this).val();

            $("option", this).each(function () {
                if (selectedZones.indexOf($(this).attr("data-value")) > -1 && this.value !== selected) {
                    $(this).attr("disabled", true);
                }
            });
        });
    }
}

function createDownloadLinkFromChecked() {
    var designCheckBoxes = $('.designCheckbox');
    var target = $('#downloadButtonContainer');
    var selectedIds = [];
    var indicatorId;
    var parentId;

    $.each(designCheckBoxes, function () {
        if ($(this).is(':checked')) {
            selectedIds.push($(this).attr('data-designId'));
            indicatorId = $(this).attr('data-indicatorId');
            parentId = $(this).attr('data-parentId')
        }
    });

    if (!$.isEmptyObject(selectedIds)) {
        var json = {
            designIds: selectedIds,
            indicatorId: indicatorId,
            parentId: parentId
        }

        $.ajax({
            type: "POST",
            url: "/app/compiledreportdownloadlink",
            dataType: 'json',
            data: JSON.stringify(json),
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                if (data && data.text) {
                    target.html(data.text);
                } else {
                    target.html('');
                }
            }
        });
    }
}

function splitDataset(queryString, dynamicallyAdded) {
    var appendLoc = $('#appendSplitsHere');
    numberOfSplitMaterials = numberOfSplitMaterials + 1;
    $.ajax({
        url: '/app/sec/util/splitDataset',
        data: queryString,
        type: "POST",
        success: function (data) {
            $(appendLoc).append(data.output);
            $('#moreSplits').slideDown().removeClass('hidden');
            $('.numberOfSplitMaterial').each(function () {
                if ($(this).is(':empty')) {
                    $(this).append(numberOfSplitMaterials);
                }
            });
            initSplitAutoCompletes(true, dynamicallyAdded);
        }
    });
}

function initSplitAutoCompletes(split, splitDynamicallyAdded) {
    var resourceSelectObjects;
    if (split) {
        if (splitDynamicallyAdded) {
            resourceSelectObjects = document.getElementsByClassName("splitCompleteDynamic");
        } else {
            resourceSelectObjects = document.getElementsByClassName("splitComplete");
        }
    } else {
        resourceSelectObjects = document.getElementsByClassName("changeAutocomplete");
    }

    for (var i = 0; i < resourceSelectObjects.length; i++) {
        var resourceSelectObject = resourceSelectObjects[i];
        if (resourceSelectObject) {
            if (splitDynamicallyAdded && resourceSelectObject) {
                $(resourceSelectObject).removeClass('splitCompleteDynamic');
                $(resourceSelectObject).addClass('splitComplete');
            }
            var entityId = $(resourceSelectObject).attr("data-entityId");
            var indicatorId = $(resourceSelectObject).attr("data-indicatorId");
            var queryId = $(resourceSelectObject).attr("data-queryId");
            var sectionId = $(resourceSelectObject).attr("data-sectionId");
            var questionId = $(resourceSelectObject).attr("data-questionId");
            //var unit = $(resourceSelectObject).attr("data-unit"); Moved to onSearchStart as user can change dynamically
            var accountId = $(resourceSelectObject).attr('data-accountId');
            var resourceAttribute = $(resourceSelectObject).map(function () {
                return $(this).data("attribute");
            }).get();
            var resourceAttributeValue = $(resourceSelectObject).map(function () {
                if (!$(this).val() == "") {
                    return $(this).attr("data-resourceAttributeValue");
                }
            }).get();
            var resourceType = $(resourceSelectObject).map(function () {
                return $(this).attr("data-resourceType");
            }).get();
            var subType = $(resourceSelectObject).map(function () {
                return $(this).attr("data-subType");
            }).get();
            $(resourceSelectObject).attr('filtered', 'true');

            $(resourceSelectObject).devbridgeAutocomplete({
                serviceUrl: '/app/jsonresources', groupBy: 'category',
                minChars: 3,
                deferRequestBy: 1000,
                noCache: true,
                params: {
                    queryId: queryId,
                    sectionId: sectionId,
                    indicatorId: indicatorId,
                    questionId: questionId,
                    entityId: entityId,
                    resourceAttribute: resourceAttribute.toString(),
                    resourceAttributeValue: resourceAttributeValue.toString(),
                    resourceType: resourceType,
                    subType: subType,
                    privateDatasetAccountId: accountId,
                    useSplitFilters: true
                },
                ajaxSettings: {
                    success: function(data) {
                        setFlashForAjaxCall(JSON.parse(data))
                    },
                    error: function(data) {
                        setFlashForAjaxCall(JSON.parse(data))
                    }
                },
                formatResult: function (suggestion) {
                    return formatAutocompleteRows(suggestion, entityId, indicatorId, questionId);
                },
                formatGroup: function (suggestion, category) {
                    return formatAutocompleteGroups(suggestion, category);
                },
                onSelect: function (suggestion) {
                    handleAutocompleteOnSelect(suggestion, $(this), null, null, true)
                },
                zIndex: autocompleteZIndex,

                onSearchStart: function (query) {
                    $('#dataHits').empty();
                    var showAllButton = $(this).siblings('a:first');
                    var dataHitbutton = $(showAllButton).siblings('a:first');
                    dataHitbutton.empty();
                    $(showAllButton).children().removeClass("icon-chevron-down");
                    $(showAllButton).children().empty();
                    $(showAllButton).children().append(loadingImg);
                    var unit = $(this).attr("data-unit");
                    if (unit) {
                        query["unit"] = unit;
                    }
                },
                onSearchComplete: function (query, suggestions) {
                    var showAllButton = $(this).siblings('a:first');
                    var dataHitbutton = $(showAllButton).siblings('a:first');
                    dataHitbutton.removeClass('hidden');
                    $(showAllButton).children().empty();
                    $(showAllButton).children().addClass("icon-chevron-down");
                    if (suggestions.length > 1) {
                        $(dataHitbutton).append('&nbsp;' + suggestions.length);
                        setTimeout(function () {
                            $(dataHitbutton).empty();
                        }, 3000);
                    } else {
                        $(dataHitbutton).addClass('hidden')
                    }
                    var options = {
                        minChars: 3,
                        deferRequestBy: 1000,
                        noCache: true
                    };

                    if (suggestions.length === 1) {
                        options = {
                            minChars: 3,
                            deferRequestBy: 1000,
                            autoSelectFirst: true,
                            noCache: true
                        };
                        var notFound = $('.notFound');
                        var quickFilters = $('#quickFilterOptionsSplit');

                        if (notFound.length > 0) {
                            $(notFound).addClass('warningHighlight');
                            $(quickFilters).addClass('warningHighlight');
                            setTimeout(function () {
                                $(quickFilters).removeClass('warningHighlight');
                                $(notFound).removeClass('warningHighlight');

                            }, 1000);
                        }

                    }
                    var that = $(this).devbridgeAutocomplete().suggestionsContainer;
                    if ($(that).length) {
                        $(that).scrollTop(0);
                    }
                    $(this).devbridgeAutocomplete().setOptions(options);

                }

            });
        }
    }
}

function splitValueChanging(originalShareElement, originalQuantity, originalQuantityElement, thisElement, event) {

    var quantityToAlter = $('[data-splitRowId="' + $(thisElement).attr('data-linkedSplitQuantity') + '"]');
    var originalDatasetQuantity = $('[data-splitRowId="' + originalQuantityElement + '"]');
    var originalDataset = false;
    var totalPoolElement = $('#totalSplitPool');
    var arrayOfShares = [];
    var arrayOfSplits = [];

    originalQuantity = parseFloat(originalQuantity);

    var value = parseFloat($(thisElement).val());
    if (isNaN(value)) {
        value = 0;
    }

    totalPool = totalPool - value;


    if ($(thisElement).attr('data-originalDataset')) {
        originalDataset = true
    }

    calculateShareAndQuantity(originalQuantity, value, quantityToAlter);

    if (!originalDataset) {

        if (totalPool <= 0) {
            totalPool = 0
        }
        calculateShareAndQuantity(originalQuantity, totalPool, originalDatasetQuantity);

        $(originalShareElement).toggleClass('warningHighlight');
        $(originalDatasetQuantity).toggleClass('warningHighlight');
    }

    $('[data-share]').each(function () {
        var floatValue = parseFloat($(this).val());
        if (!isNaN(floatValue)) {
            arrayOfShares.push(floatValue);

            if (!$(this).attr('data-originalDataset')) {
                arrayOfSplits.push(floatValue)
            }
        }
    });


    var sumOfShares = arrayOfShares.reduce(getSum);
    var sumOfSplits = arrayOfSplits.reduce(getSum);
    var leftFromSplits = 100 - sumOfSplits;

    if (leftFromSplits < 0) {
        leftFromSplits = 0
    }

    if (!originalDataset) {
        $(originalShareElement).val(leftFromSplits);

        totalPool = leftFromSplits;
        calculateShareAndQuantity(originalQuantity, totalPool, originalDatasetQuantity);
        $(originalShareElement).toggleClass('warningHighlight');
        $(originalDatasetQuantity).toggleClass('warningHighlight');
    }

    setTimeout(function () {
        var arrayOfSharesAgain = [];
        $('[data-share]').each(function () {
            var floatValue = parseFloat($(this).val());
            arrayOfSharesAgain.push(floatValue);
        });
        sumOfShares = arrayOfSharesAgain.reduce(getSum);
        $(totalPoolElement).empty().append(sumOfShares);

        if (sumOfShares > 100) {
            totalPoolElement.parent().css('color', 'red');
        } else {
            totalPoolElement.parent().css('color', 'black');
        }

    }, 750);

}

function getSum(total, num) {
    return total + num;
}

function calculateShareAndQuantity(quantity, share, quantityElement) {
    var quantityFloat = parseFloat(quantity);
    var shareFloat = parseFloat(share);
    var newQuantity = quantity * share / 100;

    $(quantityElement).val(newQuantity);
    $(quantityElement).trigger('input');
}

/**
 * @param {number} number
 * @param {string} type can be 'height' or 'width'
 */
function convertProportionOfScreenToPixel(number, type) {
    if (type && number && number <=1) {
        number = type === 'height' ? number * screen.height : number * screen.width
    }
    return number
}

/**
 * Render the data card at any place.
 * The data card's CSS position: fixed, and by default will appear on the right of the screen (CSS - left: 70%, top: 20%).
 * The data card default width: 500px, default height: 0.5 * screen height. Minimum width: 450px, minimum height: 0.3 * screen height.
 *
 * To set the location and/or size of the data card, use the optional params. Use number of pixel or proportion of screen corresponding width/height.
 * **Note**: values can't be 0, if value to set position as pixel, use value higher than 1
 *
 * @param resourceId
 * @param infoId
 * @param indicatorId (optional)
 * @param questionId (optional)
 * @param profileId (optional)
 * @param childEntityId (optional)
 * @param showGWP (optional)
 * @param queryId (optional)
 * @param sectionId (optional)
 * @param datasetId (optional)
 * @param queryPage (optional)
 * @param disabled (optional)
 * @param manualId (optional)
 * @param customName (optional)
 * @param productDataListPage (optional)
 * @param appendTo (optional) selector of the element to append data card to
 * @param cardPosition (optional) default is 'fixed', can use 'absolute' as well
 * @param topCoordinate (optional)
 * @param leftCoordinate (optional)
 * @param width (optional) min 450 or equivalent
 * @param height (optional) min 0.3 screenHeight or equivalent
 * @param zIndex (optional) default: 100
 * @param hideImage (optional)
 */
function renderSourceListingToAnyElement(resourceId, infoId, indicatorId, questionId, profileId,
                                         childEntityId, showGWP, queryId, sectionId, datasetId,
                                         queryPage, disabled, manualId, customName, productDataListPage,
                                         appendTo, cardPosition, topCoordinate, leftCoordinate, width,
                                         height, zIndex,  hideImage) {
    if (resourceId) {
        let queryString = 'resourceId=' + resourceId
        queryString += infoId ? '&infoId=' + infoId : ''
        queryString += indicatorId ? '&indicatorId=' + indicatorId : ''
        queryString += questionId ? '&questionId=' + questionId : ''
        queryString += profileId ? '&profileId=' + profileId : ''
        queryString += childEntityId ? '&entityId=' + childEntityId : ''
        queryString += showGWP ? '&showGWP=' + showGWP : ''
        queryString += queryId ? '&queryId=' + queryId : ''
        queryString += sectionId ? '&sectionId=' + sectionId : ''
        queryString += datasetId ? '&datasetId=' + datasetId : ''
        queryString += queryPage ? '&queryPage=' + queryPage : ''
        queryString += '&disabled=' + disabled
        queryString += manualId ? '&manualId=' + manualId : ''
        queryString += customName ? '&customName=' + customName : ''
        queryString += productDataListPage ? '&productDataListPage=' + productDataListPage : ''
        queryString += cardPosition ? '&cardPosition=' + cardPosition : ''
        queryString += topCoordinate ? '&topCoordinate=' + topCoordinate : ''
        queryString += leftCoordinate ? '&leftCoordinate=' + leftCoordinate : ''
        queryString += width ? '&initWidth=' + width : ''
        queryString += height ? '&initHeight=' + height : ''
        queryString += zIndex ? '&zIndex=' + zIndex : ''
        queryString += hideImage ? '&hideImage=' + hideImage : ''

        $.ajax({
            type: 'POST',
            data: queryString,
            url: '/app/rendersourceListing',
            success: function (data) {
                if(appendTo) {
                    $(appendTo).empty().append(data.output);
                } else {
                    if ($(`#modal-${infoId}`).length) {
                        $(`#modal-${infoId}`).remove()
                    }
                    $('body').append(data.output)
                }
            }
        });
    }
}

function submitSplitOrChange(originalSplitRowId, skipCalculation) {
    var originalQuantityForSplit = $('[data-splitRowId="' + originalSplitRowId + '"]');

    if (originalQuantityForSplit && $(originalQuantityForSplit).val() <= 0) {
        $('#originResource').remove();
    }
    window.onbeforeunload = null;
    clearSplitFilter();
    if (skipCalculation) {
        $('#splitOrChangeForm').append('<input type=\"hidden\" name=\"skipCalculation\" value=\"' + skipCalculation + '\" />');
    }
    $('#splitOrChangeForm').trigger('submit');
    appendLoader('splitViewOverLay');
}

function renderSplitOrChangeView(queryString, skipCalculation) {
    document.body.className += ' ' + 'noScroll';

    openOverlay("myOverlay");
    appendLoader('splitViewOverLay');
    if (formChanged) {
        var frm = $('#queryForm');
        if (skipCalculation) {
            frm.append('<input type=\"hidden\" name=\"skipCalculation\" value=\"' + skipCalculation + '\" />');
        }
        $.ajax({
            url: '/app/sec/query/saveQueryFormAjax',
            type: 'POST',
            data: new FormData($(frm).get(0)),
            processData: false,
            contentType: false,
            success: function (data) {
                if (data) {
                    $.ajax({
                        url: '/app/sec/util/splitOrChangeDataset',
                        data: queryString,
                        type: 'POST',
                        success: function (data) {
                            $('#splitViewOverLay').empty().addClass('hidden').append(data.output).fadeIn('slow').removeClass('hidden');
                        }
                    });
                }
            }
        });
    } else {
        $.ajax({
            url: '/app/sec/util/splitOrChangeDataset',
            data: queryString,
            type: 'POST',
            success: function (data) {
                $('#splitViewOverLay').empty().addClass('hidden').append(data.output).fadeIn('slow').removeClass('hidden');
            }
        });
    }

}

function renderSustainableAlternativesAnywhere(targetElementId, resourceName, resourceId, area, subType, connectedBenchmark, projectCountry, entityId, indicatorId, splitView) {
    if (resourceId && area && subType && connectedBenchmark && projectCountry) {
        var queryString = "resourceId=" + resourceId + "&area=" + area + "&subType=" + subType + "&connectedBenchmark=" + connectedBenchmark + "&projectCountry=" + projectCountry + "&entityId=" + entityId + "&indicatorId=" + indicatorId;
        if (splitView) {
            queryString = queryString + '&splitView=' + splitView
        }
        $.ajax({
            type: "POST",
            url: "/app/sustainablealternatives",
            data: queryString,
            success: function (data) {
                $(targetElementId).append(data.output);
            }
        });
    }
}

function appendSustainableResourceToSelect(resource) {
    var object = $('#changeTableSelect');
    $(object).val(resource);
    addResourceFromSelect($(object).attr("data-entityId"), $(object).attr("data-quarterlyInputEnabled"),
        $(object).attr("data-monthlyInputEnabled"), $(object).attr("data-selectId"), $(object).attr("data-indicatorId"),
        $(object).attr("data-queryId"), $(object).attr("data-sectionId"), $(object).attr("data-questionId"), $(object).attr("data-resourceTableId"),
        $(object).attr("data-fieldName"), $(object).attr("data-preventDoubleEntries"), $(object).attr("data-showGWP"),
        $(object).attr("data-doubleEntryWarning"), "", "", "");
}

function highLightElementTimeOut(element) {
    $(element).addClass('highlighted');

    setTimeout(function () {
        $(element).removeClass('newResourceRow highlighted');
    }, 2000);
}

function highLightNewGroup(elementClass) {
    $(elementClass).addClass('highlighted-new');

    setTimeout(function () {
        $(elementClass).removeClass('highlighted-new');
        $(elementClass).addClass('highlighted-background');
        $(elementClass).removeClass('newResourceRow');
    }, 1000);
}

function closeOverlay(elementId) {
    document.getElementById(elementId).style.height = "0%";
}

function openOverlay(elementId) {
    document.getElementById(elementId).style.height = "100%";
}

// Function to close Resource Popovers, Split/Replace Overlay and Copy Data Modal on Escape press
$(document).on('keyup', function (e) {
    if (e.keyCode == 27) { //checks if the keyup event matches keycode: escape
        cancelSplitOrChange('myOverlay', 'splitViewOverLay'); //this code always fires but will close the split/replace window if it is open
        if (!swal.isVisible()) { //this code checks if a sweet alert is open (this is to prevent key presses on the sweet alert affecting the Copy Data Modal loaded beneath it)
            closeImportDiv(); //Closes the Copy Data/Import Data Modal if there is no sweetalert active
        }

        $('.popover').each(function () { //Iterates over each element with the class popover and runs the below functions
            var popoverId = $(this)[0].id; //Stores the ID of the popover element
            var trimmed = popoverId.replace("popover", ""); //Trims part of the ID to make it pass into the below function correctly
            closeSourceListing(trimmed); //Closes the resource popovers
        });
    }
});

function closeImportDiv() {
    $('#importFromEntities').remove();
    document.body.classList.remove('noScroll');
    closeOverlay("myOverlay");
}

function cancelSplitOrChange(overLayId, overLayContentId) {
    $(overLayContentId).empty();
    $('#splitOrChangeTemplate').remove();
    clearSplitFilter();
    document.body.classList.remove('noScroll');
    closeOverlay(overLayId)
}

function removeSplitMaterial(element) {
    $(element).fadeOut();
    setTimeout(function () {
        $(element).remove()
    }, 1000);

    numberOfSplitMaterials = numberOfSplitMaterials - 1

}

function getUserLicenseInformation(element, userId) {
    var queryString = 'id=' + userId;
    $.ajax({
        url: '/app/sec/user/renderUserLicenseInformation',
        data: queryString,
        type: 'POST',
        success: function (request) {
            $(element).popover({
                placement: 'top',
                template: '<div class="popover"><button type="button" class="close hideTip" data-dismiss="popover" onclick="$(this).parent().remove()" aria-hidden="true">&times;</button><div class="arrow"></div><div class="popover-content"></div></div>',
                trigger: 'manual',
                html: true,
                content: request.output
            });
        },
        complete: function () {
            $(element).popover('show');
        }
    });


}

function sortBigTable(tableId, paginate, itemsPerPage) {
    $(tableId).dataTable({
        "bFilter": true,
        "bPaginate": paginate,
        "sPaginationType": "full_numbers",
        "bProcessing": true,
        "bDestroy": true,
        "bDeferRender": true,
        "iDisplayLength": itemsPerPage,
    });
}

function trackInputChanges() {
    var inputs = document.getElementsByTagName('input');
    var selects = document.getElementsByTagName('select');
    $(inputs).each(function () {
        $(this).on('change', function () {
            setHiddenSortValues();
        });
    });

    $(selects).each(function () {
        $(this).on('change', function () {
            setHiddenSortValues();
        });
    });

}

function setHiddenSortValues() {
    var hiddenValues = $('.setHiddenValueForSort');
    $(hiddenValues).each(function () {
        var val = $(this).next().val();
        if (val) {
            $(this).empty().append(val);
            $(this).parent().updateSortVal(val)
        }
    });
}

function sortableImportTables() {
    setHiddenSortValues();
    $('.sortMe').each(function (event) {
        if ($(this).find("tbody").find("tr").length > 0) {
            $(this).stupidtable();
            $(this).on("aftertablesort", function (event, data) {
                var sorter = "<span class='sorter'> <i class=\"fa fa-sort\" aria-hidden=\"true\"></i></span>";
                var sortableColumns = $('[data-sort]');
                var th = $(this).find("th");
                th.find(".arrow").remove();
                $(sortableColumns).find(".sorter").remove();
                $(sortableColumns).append(sorter);

                var dir = $.fn.stupidtable.dir;
                var arrow = data.direction === dir.ASC ? "<span class='arrow'> <i class=\"fa fa-caret-down\" aria-hidden=\"true\"></i></span>" : "<span class='arrow'> <i class=\"fa fa-caret-up\" aria-hidden=\"true\"></i></span>";
                th.eq(data.column).find('.sorter').remove();
                th.eq(data.column).append(arrow);
            });
        }
    });
}

function formatNumberBylocale(locale, value) {

    if (value) {
        var num = parseFloat(value);
        var returnable;
        if (locale) {
            returnable = num.toLocaleString(locale, {localeMatcher: "lookup"});
        }
        if (returnable) {
            return returnable
        } else {
            return value
        }
    } else {
        return 0
    }
}

function clearSplitFilter() {
    $.ajax({
        url: '/app/sec/util/clearSplitFilter',
        type: 'POST'
    });
}

function trackChangesInResourceRows() {
    var rows = document.getElementsByTagName("tr");
    $(rows).each(function () {
        var row = this;
        var inputs = $(row).find("input, textarea");
        var selects = $(row).find("select").not(".quickFilterSelect");
        var carbonDataId = $(row).attr('id') + "carbonData";
        $(inputs).on('input', function (event) {
            // Only empty carbonData on input if not comment field
            if (this.id.indexOf("comment") < 0) {
                emptyCarbonData(carbonDataId);
                $(this).closest('tr').find('.totalAndSocialCostTest').html("-");
                $(this).closest('tr').find('.totalAndSocialCostHidden').val("-");
            }
            triggerCalculationIfNeeded(this)
            formChanged = true
        });
        $(selects).on('change', function (event) {
            emptyCarbonData(carbonDataId);
            triggerCalculationIfNeeded(this)
            formChanged = true
        });

    });
}

function emptyCarbonData(carbonDataElementId) {
    const carbonData = document.getElementById(carbonDataElementId);
    $(carbonData).find('.carbonValue').empty().append("-");
    $(carbonData).find('.carbonPercentageValue').empty()
}

function appendTemplateSecure(elementId, templateUrl) {
    var url = '/app/sec/' + templateUrl;
    var target = document.getElementById(elementId);
    if (target) {
        $.ajax({
            url: url,
            type: 'POST',
            success: function (data, textStatus) {
                $(target).empty().append(data);
            }
        });
    } else {
        return false
    }
}

var mongoObjectIdmongoObjectId = function () {
    var timestamp = (new Date().getTime() / 1000 | 0).toString(16);
    return timestamp + 'xxxxxxxxxxxxxxxx'.replace(/[x]/g, function () {
        return (Math.random() * 16 | 0).toString(16);
    }).toLowerCase();
};

function publishEPD() {
    Swal.fire({
        title: 'Publishing EPD',
        text: 'Are you sure you have completed your EPD? The process will incur charges which may increase with iterations',
        icon: "warning",
        confirmButtonText: "Verify & Publish",
        cancelButtonText: "Cancel",
        confirmButtonColor: "#84bb3c",
        allowOutsideClick: true,
        showCancelButton: true,
        reverseButtons: true
    }).then(function () {
        console.log("Ok")
    });
}

function saveAsPrivateDataset(localizedTitle, saveBtn, cancelBtn) {
    Swal.fire({
        title: localizedTitle,
        input: 'text',
        type: "question",
        confirmButtonText: saveBtn,
        cancelButtonText: cancelBtn,
        confirmButtonColor: "#84bb3c",
        allowOutsideClick: true,
        showCancelButton: true,
        reverseButtons: true,
        showLoaderOnConfirm: true,
        preConfirm: function () {
            return new Promise(function (resolve) {
                setTimeout(function () {
                    resolve();
                }, 1500);
            })
        }
    }).then(result => {
        if (result.value) {
            Swal.fire({
                icon: 'success',
                title: 'Dataset saved as private dataset and can be used within your organisation',
                confirmButtonText: "OK",
                confirmButtonColor: "#84bb3c"
            })
        }

    });
}

function generateWordDoc(childEntityId, indicatorId, arrayOfRules, arrayOfCanvasIds, templateString, reportGenerationRuleId, errorMsg) {
    try {
        const showAllGraph = $("#all_graphs")

        if (showAllGraph) {
            toggleActiveTab(showAllGraph, true)
        }

        openOverlay("wordDocGenOverlay");
        appendLoader('wordDocGenOverlayContent');
        // loop the breakdown charts to be drawn
        if (arrayOfRules) {
            arrayOfRules = arrayOfRules.split(",");
            const arrayOfRulesSize = arrayOfRules.length;

            if (arrayOfRulesSize > 1) {
                const ruleInUse = $("#currentRuleInUse").val();
                const selectedClassification = $("#currentClassifcation").val();

                if ($("#showAllBreakDowns").is(":visible")) {
                    drawAllBreakdown(childEntityId, indicatorId, "allBreakDowns", ruleInUse, selectedClassification);
                }
            }

            saveCanvases(arrayOfCanvasIds, childEntityId, indicatorId, templateString, reportGenerationRuleId);
        }
    } catch (e) {
        closeOverlay("wordDocGenOverlay");
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: "Something went wrong!",
        })
    }
}
async function drawAllBreakdown(entityId, indicatorId, chartDivId,ruleInUse,selectedClassification){
    $("#showAllBreakDowns").hide()
    var promise = new Promise((resolve, reject) => {
        var jSon = {
            entityId: entityId,
            indicatorId: indicatorId,
            calculationRuleId: ruleInUse,
            chartDivId: chartDivId,
            allCategoryBreakdown: true,
            selectedClassification: selectedClassification,
        }
        $("#loadingSpinner").show()

        if (entityId && indicatorId && chartDivId) {
            $.ajax({
                type: "POST",
                data: JSON.stringify(jSon),
                contentType: "application/json; charset=utf-8",
                url: '/app/sec/entity/redrawResultCharts',
                success: function (data) {
                    if (data) {
                        var chartDivId = data.output.chartDiv
                        if (data.output.chartDisplay) {
                            $("#" + chartDivId).append(data.output.chartDisplay)
                            $("#loadingSpinner").hide()
                            resolve("DONE")

                        }
                    }
                },
                error: function (error) {
                    console.log(error)
                }
            })
        }
    });

    var result = await promise; // wait until the promise resolves (*)

    console.log(result); // "done!"
}

function saveCanvases(arrayOfCanvasIds, childEntityId, indicatorId, templateString, reportGenerationRuleId) {
    if (arrayOfCanvasIds) {
        const elementIds = arrayOfCanvasIds.split(",");

        $(elementIds).each(function () {
            svgToImage(this, null, "png");
        });
    }

    let location = generateFileAndDownload(childEntityId, indicatorId, templateString, reportGenerationRuleId)

    setTimeout(function () {
        window.location = location;
        closeOverlay('wordDocGenOverlay')
    }, 2000);
}

function generateFileAndDownload(childEntityId, indicatorId, templateString, reportGenerationRuleId) {
    const queryString = '?childEntityId=' + childEntityId + '&indicatorId=' + indicatorId + '&templateId=' + templateString + '&reportGenerationRuleId=' + reportGenerationRuleId;
    return "/app/sec/fileExport/createWordDoc" + queryString;
}

function destroyInfoPopovers() {
    $('.hideSourceListing').trigger('click')
}

function findAndShowBrokenConstructions() {
    var rows = document.getElementsByTagName("tr");
    $(rows).each(function () {
        var parentRowIdentifier = $(this).attr('data-dragmewithparent');
        if (parentRowIdentifier) {
            var parentRow = $('[data-constructionrow=\"' + parentRowIdentifier + '\"]');
            if (!parentRow.length) {
                $(this).fadeIn().removeClass('hidden');
                $(this).removeAttr('data-dragmewithparent');
                $(this).children().each(function () {
                    $(this).children().each(function () {
                        if ($(this).attr('data-persisted')) {
                            $(this).val("");
                        }
                        $(this).removeAttr('data-uniqueConstructionIdentifier');
                        $(this).removeAttr('readonly');
                        if ($(this).is('a')) {
                            $(this).removeClass('hidden');
                        }
                    });
                });
            }
        }

    });
}

function showBreakDownByStage(elem, queryString) {
    const hasPop = $(elem).attr("data-hasPop");
    $(elem).popover('destroy');

    if (!hasPop || hasPop == "false") {
        $(elem).addClass('loadingCursor');

        $.ajax({
            url: '/app/sec/query/carbonDataBreakdown',
            data: queryString,
            success: function (data) {
                $(elem).popover({
                    content: data.output,
                    trigger: 'manual',
                    html: true,
                    position: 'right',
                    template: '<div class="popover" style="color:black !important;"><div class="arrow"></div><div class="popover-content"></div></div>'
                });
                $(elem).removeClass('loadingCursor');
                $(elem).attr('data-hasPop', true);
                $(elem).popover('show');
            }
        });

        $(elem).removeClass('loadingCursor');
    } else {
        $(elem).attr('data-hasPop', false);
    }
}

function deleteConfigurations(warningTitle, warningText, successText, yes, back) {
    Swal.fire({
        title: warningTitle,
        text: warningText,
        icon: "warning",
        confirmButtonText: yes,
        cancelButtonText: back,
        confirmButtonColor: "red",
        showCancelButton: true,
        reverseButtons: true,
        showLoaderOnConfirm: true,
        preConfirm: function () {
            return new Promise(function (resolve) {
                var checkBoxes = $('[data-checkbox]:checked');
                var i = 0;
                $(checkBoxes).each(function () {
                    if ($(this).prop('checked')) {
                        var configurationId = $(this).attr('data-configurationId');
                        $.ajax({
                            type: 'POST',
                            url: '/app/sec/admin/helpConfiguration/delete',
                            data: 'id=' + configurationId,
                            success: function (data) {
                                if (data) {
                                    i++;
                                }
                                if (i === checkBoxes.length) {
                                    resolve();
                                }
                            }
                        });
                    }
                });
            })
        },
        allowOutsideClick: false
    }).then(result => {
        if (result.value) {
            Swal.fire({
                icon: 'success',
                title: successText,
                html: ''
            }).then(function () {
                location.reload();
            });
        }
    });
}

function moveRows(rows, targetQuestionId, targetSectionId, queryId, entityId, indicatorId) {
    var targetTableId = targetSectionId + targetQuestionId + 'Resources';
    var movedRows = [];
    $(rows).each(function () {
        if ($(this).attr('data-manualIdForMoving')) {
            movedRows.push(this);

            //If row is a construction move also the constituents under it
            if ($(this).attr('data-parentidentifier')) {
                var childrenOfConstruction = $('[data-dragMeWithParent=\"' + $(this).attr('data-parentidentifier') + 'row\"');
                if (childrenOfConstruction) {
                    $(childrenOfConstruction).each(function () {
                        movedRows.push(this);
                    });
                }
            }
        }
    });

    if (movedRows.length > 0) {
        openOverlay('loaderOverlay');
        appendLoader('loaderOverlayContent');

        var manualIdString = "";

        $(movedRows).each(function () {
            manualIdString = manualIdString ? manualIdString + '&manualId=' + $(this).attr('data-manualIdForMoving') : '&manualId=' + $(this).attr('data-manualIdForMoving')
        });

        if (formChanged) {
            var frm = $('#queryForm');
            $.ajax({
                url: '/app/sec/query/saveQueryFormAjax',
                type: 'POST',
                data: new FormData($(frm).get(0)),
                processData: false,
                contentType: false,
                success: function () {
                    var queryString = 'targetQuestionId=' + targetQuestionId + '&queryId=' + queryId + '&entityId=' + entityId + '&indicatorId=' + indicatorId + manualIdString;
                    $.ajax({
                        url: '/app/sec/query/moveDatasetToAnotherQuestion',
                        data: queryString,
                        success: function (data) {
                            var targetTable = document.getElementById(targetTableId);
                            if ($(targetTable).is(':visible')) {
                                $(targetTable).append(movedRows);
                                window.onbeforeunload = null;
                                location.reload();

                            } else {
                                var headerRow = document.getElementById(targetTableId + 'header');
                                $(headerRow).show();
                                $(targetTable).append(movedRows);
                                window.onbeforeunload = null;
                                location.reload();

                            }
                        }
                    });
                }
            });
        } else {
            var queryString = 'targetQuestionId=' + targetQuestionId + '&queryId=' + queryId + '&entityId=' + entityId + manualIdString;
            $.ajax({
                url: '/app/sec/query/moveDatasetToAnotherQuestion',
                data: queryString,
                success: function () {
                    var targetTable = document.getElementById(targetTableId);
                    if ($(targetTable).is(':visible')) {
                        $(targetTable).append(movedRows);
                        window.onbeforeunload = null;
                        location.reload();

                    } else {
                        var headerRow = document.getElementById(targetTableId + 'header');
                        $(headerRow).show();
                        $(targetTable).append(movedRows);
                        window.onbeforeunload = null;
                        location.reload();

                    }
                }
            });
        }
    }
}

function moveDatasetToAnotherQuestion(originalTableId, targetQuestionId, targetSectionId, queryId, entityId, indicatorId) {
    var originalTable = document.getElementById(originalTableId);
    var rows = originalTable.getElementsByTagName("tr");
    var targetTableId = targetSectionId + targetQuestionId + 'Resources';
    var movedRows = [];
    $(rows).each(function () {
        var checkbox = $(this).find('.moveResourceCheckBox');

        if ($(this).attr('data-manualIdForMoving') && $(checkbox).is(':checked')) {
            movedRows.push(this);
            if ($(checkbox).attr('data-parentConstruction')) {
                var childrenOfConstruction = $('[data-dragMeWithParent=\"' + $(checkbox).attr('data-constructionIdentifierMoving') + 'row\"');
                if (childrenOfConstruction) {
                    $(childrenOfConstruction).each(function () {
                        movedRows.push(this);
                    });
                }

            }
        }

    });

    if (movedRows.length > 0) {
        openOverlay('loaderOverlay');
        appendLoader('loaderOverlayContent');

        var manualIdString = "";

        $(movedRows).each(function () {
            manualIdString = manualIdString ? manualIdString + '&manualId=' + $(this).attr('data-manualIdForMoving') : '&manualId=' + $(this).attr('data-manualIdForMoving')
        });

        if (formChanged) {
            var frm = $('#queryForm');
            $.ajax({
                url: '/app/sec/query/saveQueryFormAjax',
                type: 'POST',
                data: new FormData($(frm).get(0)),
                processData: false,
                contentType: false,
                success: function (data) {
                    var queryString = 'targetQuestionId=' + targetQuestionId + '&queryId=' + queryId + '&entityId=' + entityId + '&indicatorId=' + indicatorId + manualIdString;
                    $.ajax({
                        url: '/app/sec/query/moveDatasetToAnotherQuestion',
                        data: queryString,
                        success: function () {
                            var targetTable = document.getElementById(targetTableId);
                            if ($(targetTable).is(':visible')) {
                                $(targetTable).append(movedRows);
                                window.onbeforeunload = null;
                                location.reload();

                            } else {
                                var headerRow = document.getElementById(targetTableId + 'header');
                                $(headerRow).show();
                                $(targetTable).append(movedRows);
                                window.onbeforeunload = null;
                                location.reload();

                            }
                        }
                    });
                }
            });
        } else {
            var queryString = 'targetQuestionId=' + targetQuestionId + '&queryId=' + queryId + '&entityId=' + entityId + manualIdString;
            $.ajax({
                url: '/app/sec/query/moveDatasetToAnotherQuestion',
                data: queryString,
                success: function () {
                    var targetTable = document.getElementById(targetTableId);
                    if ($(targetTable).is(':visible')) {
                        $(targetTable).append(movedRows);
                        window.onbeforeunload = null;
                        location.reload();

                    } else {
                        var headerRow = document.getElementById(targetTableId + 'header');
                        $(headerRow).show();
                        $(targetTable).append(movedRows);
                        window.onbeforeunload = null;
                        location.reload();

                    }
                }
            });
        }
    }

}

function showResourceMovers() {
    $('.moveResourceCheckBox').each(function () {
        if ($(this).hasClass('visibilityNone')) {
            $(this).removeClass('visibilityNone');
        } else {
            $(this).addClass('visibilityNone').fadeIn().parent().addClass('highlighted');
        }
    });
    $('.moveResourcesContainer').removeClass('visibilityNone').fadeIn().addClass('highlighted');
}

function hideResourceMovers() {
    $('.moveResourceCheckBox').each(function () {
        if (!$(this).hasClass('visibilityNone')) {
            $(this).addClass('visibilityNone').fadeIn().parent().addClass('highlighted');
        }
    });
    $('.moveResourcesContainer').each(function () {
        if (!$(this).hasClass('visibilityNone')) {
            $(this).addClass('visibilityNone').fadeIn().addClass('highlighted');
        }
    });
}

function showInfo() {
    $('.generalInfoBtn').on('click', function () {
        var boxId = $(this).attr("data-targetBox");
        var box = document.getElementById(boxId);
        var parentElem = $(this).parent()
        var childElem = $(box).children().children()
        $('.info-section').addClass('hidden')
        $(parentElem).siblings().removeClass('active');

        if ($(box).length) {
            if (!$(boxId).is(':visible')) {
                $(box).removeClass('hidden');
                $(parentElem).addClass('active');
                $(childElem).show();

            }

        }

    })

}

function showLcaCheckerModal() {
    var $topPieGraphWrapper = $('#topPieGraphWrapper');
    var $topDoughnutWrapper = $('#topDoughnutWrapper');

    if ($topDoughnutWrapper.hasClass('hidden')) {
        $topPieGraphWrapper.addClass('hidden');
        $topPieGraphWrapper.removeAttr('style');
        $topDoughnutWrapper.fadeIn().removeClass('hidden');
    }
    $('#lcaCheckerModal').modal();
}


function drawStructurePieChart(entityId, indicatorId) {
    var queryString = 'entityId=' + entityId + '&indicatorId=' + indicatorId;
    var $struturesPieChart = $('#structurePieWrapper');
    $.ajax({
        type: 'POST',
        data: queryString,
        url: '/app/sec/entity/structurePieChart',
        beforeSend: function () {
            $struturesPieChart.empty()
        },
        success: function (data) {
            $struturesPieChart.append(data.output);
        }, error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("draw failed for top pie chart");
        }
    });
}

function drawCircularGraph(entityId, indicatorId, width, height) {
    if (!width && !height) {
        width = "420";
        height = "182.9";
    }
    var queryString = 'entityId=' + entityId + '&indicatorId=' + indicatorId + '&width=' + width + '&height=' + height;
    var $circularChart = $('#circularChartWrapper');
    $.ajax({
        type: 'POST',
        data: queryString,
        url: '/app/sec/entity/graphCircular',
        beforeSend: function () {
            $circularChart.empty()
        },
        success: function (data) {
            $circularChart.append(data);
        }, error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("draw failed for top pie chart");
        }
    });
}

function expandSourceListingAttribute(elem, e, hiddenLink) {
    var e = window.event || e //make sure event is defined in Firefox browser
    stopBubblePropagation(e);
    var fullText = $(elem).attr('data-attributeFullValue');
    $(elem).empty().append(fullText);
    if (hiddenLink) {
        $(hiddenLink).show()
    }
}

function drawPieGraph(entityId, indicatorId) {
    $.ajax({
        type: 'POST',
        data: 'entityId=' + entityId + '&indicatorId=' + indicatorId,
        url: '/app/sec/entity/entityPieGraph',
        beforeSend: function () {
            $('.pieContainer').remove();
        },
        success: function (data, textStatus) {
            if (data.output) {
                $('#pieWrapper').append(data.output);
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}

function appendIndicatorBenchmark(indicatorId, childEntityId, parentEntityType, childName, parentName, countryForPrioritization, benchmarkId, forResultPage) {
    var queryString = "childEntityId=" + childEntityId + "&indicatorId=" + indicatorId + "&parentEntityType=" + parentEntityType + "&countryForPrioritization=" + countryForPrioritization + "&benchmarkId=" + benchmarkId + "&mainEntity=true";
    if (!forResultPage) {
        var header = $('#resultSectionHeader');
        var sectionHeader = document.getElementById("resulGraphHeader");
    }
    var element = $('#benchmarkEntityPageWrapper');
    $.ajax({
        type: "POST",
        url: '/app/sec/util/indicatorBenchmarks',
        data: queryString,
        success: function (data) {
            if (data.output) {
                $(element).empty().append(data.output);
                if (sectionHeader != null && !forResultPage) {
                    $(header).empty().append(' - Design: ' + childName);
                    sectionHeader.addEventListener("click", function () {
                        if (!$(sectionHeader).hasClass("collapsed")) {
                            $('#resultSectionHeader').empty().append(' - Design: ' + childName);
                        } else {
                            $('#resultSectionHeader').empty()

                        }

                    })
                }


            }
        }
    });
}

function inheritSelectValueToChildren(uniqueConstructionIdentifier, value, type) {
    var childrenAdditionalQuestions = $('*[data-identifierForInheritance="' + uniqueConstructionIdentifier + '"]');
    if (childrenAdditionalQuestions) {
        var child = $(childrenAdditionalQuestions).find(type);

        console.log("Inheriting value: " + value);
        console.log("Inheriting type: " + type);
        console.log("To children:");
        console.log($(child));

        if (child) {
            $(child).val(value).trigger('change');
        }
    }
}


function showPortFolioSourceListing(elementId, classValue) {
    if (classValue) {
        var allListings = document.getElementsByClassName(classValue);
        for (var i = 0; i < allListings.length; i++) {
            var listing = allListings[i];

            if (listing) {
                $(listing).fadeOut('slow');
                listing.style.display = 'none';
            }
        }
    }
    var sourceListingTable = document.getElementById(elementId);
    if (sourceListingTable) {
        $(sourceListingTable).fadeIn('slow');
        sourceListingTable.style.display = 'block';
    }
}

function drawPieGraphHotel(entityId, indicatorId) {
    $.ajax({
        type: 'POST',
        data: 'entityId=' + entityId + '&indicatorId=' + indicatorId,
        url: '/app/sec/entity/entityPieGraphHotel',
        beforeSend: function () {
            $('.pieContainerHotel').remove();
        },
        success: function (data, textStatus) {

            if (data.output) {
                $('#pieWrapperHotel').append(data.output);
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}

function drawCompChartByStage(entityId, indicatorId, calculationRuleId, designSelected) {
    if (!calculationRuleId) {
        calculationRuleId = null
    }
    ;
    if (!designSelected) {
        designSelected = null
    }
    ;
    $.ajax({
        type: 'POST',
        data: 'entityId=' + entityId + '&indicatorId=' + indicatorId + '&calculationRuleId=' + calculationRuleId + '&designSelected=' + designSelected,
        url: '/app/sec/entity/stageMainCompChart',
        beforeSend: function () {
            $('#mainCompGraphWrapper').remove();
            appendLoader('pendingStage');
        },
        success: function (data, textStatus) {
            if (data) {
                $('#pendingStage').empty()
                $('#compGraphContent').append(data);
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }

    });

}

function drawCompChartByStructure(entityId, indicatorId, calculationRuleId, designSelected) {
    if (!calculationRuleId) {
        calculationRuleId = null
    }
    ;
    if (!designSelected) {
        designSelected = null
    }
    ;
    $.ajax({
        type: 'POST',
        data: 'entityId=' + entityId + '&indicatorId=' + indicatorId + '&calculationRuleId=' + calculationRuleId + '&designSelected=' + designSelected,
        url: '/app/sec/entity/structureMainCompChart',
        beforeSend: function () {
            $('#mainStrucCompGraphWrapper').remove();
            appendLoader('pendingStructure');
        },
        success: function (data, textStatus) {
            if (data) {
                $('#pendingStructure').empty()
                $('#strucGraphContent').append(data);
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }

    });

}

function redrawStructureChart(entityId, indicatorId, calculationRuleId) {
    var selectedDesign = [];
    $.each($("input[name='design']:checked"), function () {
        selectedDesign.push($(this).val());
    })
    if (!(selectedDesign.length > 1)) {
        alert("Please choose at least two designs for visualisation")
    } else {
        drawCompChartByStructure(entityId, indicatorId, calculationRuleId, selectedDesign.toString())
    }
}

function redrawStageChart(entityId, indicatorId, calculationRuleId) {
    var selectedDesign = [];
    $.each($("input[name='groupDesign']:checked"), function () {
        selectedDesign.push($(this).val());
    });
    if (!(selectedDesign.length > 1)) {
        alert("Please choose at least two designs for visualisation")
    } else {
        drawCompChartByStage(entityId, indicatorId, calculationRuleId, selectedDesign.toString())
    }
}

function clickAndDisable(linkClass, addDisableClass, dontRemoveOnclicks) {
    const links = $("." + linkClass);

    if (links.length) {
        if (!dontRemoveOnclicks) {
            links.prop("onclick", null).off("click");
        }

        if (addDisableClass) {
            links.addClass('removeClicks');
        }
    }
}

function drawCompChartByGroup(entityId, indicatorId, calculationRuleId, designSelected) {
    if (!calculationRuleId) {
        calculationRuleId = null;
    }
    if (!designSelected) {
        designSelected = null;
    }
    $.ajax({
        type: 'POST',
        data: 'entityId=' + entityId + '&indicatorId=' + indicatorId + '&calculationRuleId=' + calculationRuleId + '&designSelected=' + designSelected,
        url: '/app/sec/entity/groupMainCompChart',
        beforeSend: function () {
            $('#mainGroupCompGraphWrapper').remove();
            appendLoader('pendingGroup');
        },
        success: function (data, textStatus) {
            if (data) {
                $('#pendingGroup').empty()
                $('#groupGraphContent').append(data);
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }

    });
}

function redrawGroupChart(entityId, indicatorId, calculationRuleId) {
    var selectedDesign = [];
    $.each($("input[name='compDesign']:checked"), function () {
        selectedDesign.push($(this).val());
    })
    if (!(selectedDesign.length > 1)) {
        alert("Please choose at least two designs for visualisation")
    } else {
        drawCompChartByGroup(entityId, indicatorId, calculationRuleId, selectedDesign.toString())
    }
}


function drawGraph(entityId, indicatorId) {
    $.ajax({
        type: 'POST',
        data: 'entityId=' + entityId + '&indicatorId=' + indicatorId,
        url: '/app/sec/entity/graphByIndicator',
        beforeSend: function () {
            $('#graphWrapper').remove();
            $('.loading-spinner').removeClass("hidden");
        },
        complete: function () {
            $(".loading-spinner").addClass("hidden");
            $("#graphDropdown").popover('disable');
        },
        success: function (data, textStatus) {
            if (data.output) {
                $('#compChartPercentage').append(data.output);
            } else {
                $('.nograph').show();

            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}


function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
}


// Get dynamic positioning for popover by calculate relative positon of caller in viewport
function getViewportOffset($e) {
    var $window = $(window),
        scrollLeft = $window.scrollLeft(),
        scrollTop = $window.scrollTop(),
        offset = $e.offset(),
        rect1 = {x1: scrollLeft, y1: scrollTop, x2: scrollLeft + $window.width(), y2: scrollTop + $window.height()},
        rect2 = {x1: offset.left, y1: offset.top, x2: offset.left + $e.width(), y2: offset.top + $e.height()};
    return {
        left: offset.left - scrollLeft,
        top: offset.top - scrollTop,
        insideViewport: rect1.x1 < rect2.x2 && rect1.x2 > rect2.x1 && rect1.y1 < rect2.y2 && rect1.y2 > rect2.y1
    };
}

function dynamicPositionForPopover(elemId, isFullElement) {
    var element = isFullElement ? $(elemId) : $("#" + elemId)

    var viewportOffset = getViewportOffset(element);
    var position = "right";
    var windowWidth = $(window).width()
    if (viewportOffset.left < 400) {
        if (viewportOffset.top < 200) {
            position = "bottom";
        } else if (viewportOffset.top > 500) {
            position = "top";
        }
    } else if (viewportOffset.left >= 400) {
        if (viewportOffset.top < 400) {
            if (windowWidth - viewportOffset.left < 300) {
                position = "left";
            } else {
                position = "bottom";
            }
        } else {
            if (windowWidth - viewportOffset.left < 300) {
                position = "left";
            } else {
                position = "top";
            }
        }
    }

    return position

}


//*****************************************************************************************************
//Function used for the ignoreWarning Attribute od a dataset (related to implausible thickness warning)
//*****************************************************************************************************

function hideWarningQuery(warningThicknessId) {
    var element = $("#" + warningThicknessId);
    var rowId = $(element).attr('data-rowId');
    var questionId = $(element).attr('data-questionId');
    var sectionId = $(element).attr('data-sectionId');
    var row = $("#" + rowId);

    if ($(row).length) {
        row.find(".ignoreWarningsHiddenInput").val("true");
        element.popover('hide');
        element.addClass('hidden');
    }
}

function hideWarningImportMapper(warningThicknessId, manualId) {

    var json = {manualId: manualId};
    var url = '/app/sec/importMapper/ignoreWarning';

    var errorCallback = function (data) {
        console.log(data)
    }

    var successCallback = function (data) {
        console.log(data)
        var element = $("#" + warningThicknessId)
        element.popover('hide');
        element.addClass('hidden');
    };

    $.ajax({
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify(json),
        contentType: "application/json; charset=utf-8",
        url: url,
        success: successCallback,
        error: errorCallback
    });

}

function ignoreWarningPopover() {

    var popoverTemplate = [
        '<div class="popover">',
        '<div class="arrow"></div>',
        '<div class="popover-content"></div>',
        '</div>'].join('');


    $('.thicknessWarningPopover').popover(
        {
            trigger: 'manual',
            html: true,
            animation: false,
            template: popoverTemplate,
            placement: 'top',
        }
    ).on('mouseenter', function () {
        var _this = this;
        $(this).popover('show');
        $('.popover').on('mouseleave', function () {
            $(_this).popover('hide');
        });
    }).on('mouseleave', function () {
        var _this = this;
        setTimeout(function () {
            if (!$('.popover:hover').length) {
                $(_this).popover('hide');
            }
        }, 300);
    });

}

//*****************************************************************************************************

function logNewUserData() {
    var epochMillisOfClient = (new Date).getTime();
    var screenWidth = $(window).width();
    var screenHeight = $(window).height();
    var queryString = 'epochMillisOfClient=' + epochMillisOfClient + '&screenWidth=' + screenWidth + '&screenHeight=' + screenHeight;

    $.ajax({
        type: 'POST',
        data: queryString,
        url: '/app/sec/user/storeSecurityData',
        success: function (data, textStatus) {
            if (data && data.output) {
                console.log("Security data: " + data.output)
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });

}

function disableWecomeTrial(userId) {
    var user = userId.toString();
    if (user != null) {
        console.log('sending data');
        $.ajax({
            data: 'user=' + user,
            url: '/app/sec/user/disableWecomeTrial',
            success: function (data, textStatus) {
                console.log(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(textStatus);
            }
        });
        console.log('data sent');
    }
    ;

}

function disableTrialGuidance(userId) {
    var user = userId.toString();
    if (user != null) {
        console.log('sending data');
        $.ajax({
            data: 'user=' + user,
            url: '/app/sec/user/disableTrialGuidance',
            success: function (data, textStatus) {
                console.log(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(textStatus);
            }
        });
        console.log('data sent');
    }
    ;

}

function requestImportTrial(userId, localizedMessage) {
    var user = userId.toString()
    var message = localizedMessage
    if (user != null) {
        $.ajax({
            data: 'user=' + user,
            url: '/app/sec/entity/requestImportTrial',
            success: function (data, textStatus) {
                alert(message)
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(textStatus);
            }
        });
        console.log('data sent');
    }
}

function disableEnableDropdown(listId, dropdownId) {
    var list = $('#' + listId);
    var dropdown = $('#' + dropdownId);
    if ($(list).find('li').length < 2) {
        $(dropdown).prop('disabled', true);
        $(dropdown).addClass('disabled');
        $(dropdown).addClass('clickOff');
        $(dropdown).removeAttr("href");
        $(dropdown).popover("disable");

    } else {
        $(dropdown).prop('disabled', false);

    }
}

function expandWarningUpstreamDB(element, longMessage, shortMessage) {
    $(element).next().removeClass('hidden');
}

function setHasUserInput(elem) {
    $(elem).attr("data-hasUserInput", true);
    $(elem).removeAttr("oninput");
}

function doCostCalculation(elem, costCalcFailedMessage, $labourCostCraftsman, $labourCostWorker, $finalMultiplier, $currencyMultiplier, $currency, $totalCurrency, $commaThousandSeparation, $costCalculationMethod) {
    var thisElem = $(elem);
    var row = thisElem.closest('tr');
    var construction = row.attr('data-parentidentifier');
    var constituent = thisElem.attr('data-constituent');
    var quantityInput = row.find('.isQuantity');
    var userGivenQuantity = quantityInput.val();

    if (construction || constituent) {
        setTimeout(function () {
            if (construction) {
                var costPerUnitTotalInput = row.find('.costPerUnitTotal');
                var totalCostTotalInput = row.find('.totalCostTotal');

                if (costPerUnitTotalInput.length && totalCostTotalInput.length) {
                    var userInputFloat = parseFloat(userGivenQuantity);

                    if (userInputFloat && userInputFloat > 0) {
                        var totalCostTotal = 0;
                        var constituenWithoutData = false;
                        $('*[data-constituent="totalCost.' + construction + '"]').each(
                            function () {
                                var tooltip = $(this).closest('tr').find('.lccalcTooltip').length;
                                if (tooltip) {
                                    constituenWithoutData = true;
                                }
                                totalCostTotal += parseFloat($(this).val());
                            }
                        );
                        if (constituenWithoutData && !costPerUnitTotalInput.parent().find('.lccalcTooltip').length) {
                            costPerUnitTotalInput.parent().prepend("<div class='lccalcTooltip fiveMarginRight'><i style='color: orange; font-size: 14px;' class='fa fa-exclamation-triangle smoothTip tooltip--right' data-tooltip='" + costCalcFailedMessage + "'></i></div>");
                        }

                        if ($commaThousandSeparation) {
                            costPerUnitTotalInput.text((totalCostTotal / userInputFloat).toFixed(2));
                        } else {
                            costPerUnitTotalInput.text((totalCostTotal / userInputFloat).toFixed(2).replace('.', ','));
                        }

                        if ($commaThousandSeparation) {
                            totalCostTotalInput.text(totalCostTotal.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                        } else {
                            totalCostTotalInput.text(totalCostTotal.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " "));
                        }
                    } else {
                        costPerUnitTotalInput.text("0");
                        totalCostTotalInput.text("0");
                    }
                }
            } else if (constituent) {
                var total = 0;
                var constructionId = constituent.substr(constituent.indexOf('.') + 1);
                var constructionRow = $('*[data-parentidentifier="' + constructionId + '"]');

                var costPerUnitTotal = constructionRow.find('.costPerUnitTotal');
                var totalCostInput = constructionRow.find('.totalCostTotal');

                if (costPerUnitTotal.length && totalCostInput.length) {
                    var constructionInputFloat = parseFloat(constructionRow.find('.isQuantity').val());

                    if (constructionInputFloat && constructionInputFloat > 0) {
                        $('*[data-constituent="totalCost.' + constructionId + '"]').each(
                            function () {
                                total += parseFloat($(this).val());
                            }
                        );

                        if ($commaThousandSeparation) {
                            constructionRow.find('.costPerUnitTotal').text((total / constructionInputFloat).toFixed(2));
                        } else {
                            constructionRow.find('.costPerUnitTotal').text((total / constructionInputFloat).toFixed(2).replace('.', ','));
                        }

                        if ($commaThousandSeparation) {
                            constructionRow.find('.totalCostTotal').text(total.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                        } else {
                            constructionRow.find('.totalCostTotal').text(total.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " "));
                        }
                    }
                }
            }
        }, 500);
    }

    var targetTotalCostTotal = row.find('.totalCostTotal');
    var targetTotalCost = row.find('.totalCostTest');
    var targetCostPerUnit = row.find('.costPerUnitTest');
    var targetTotalCostHidden = row.find('.totalCostHidden');
    var targetCostPerUnitHidden = row.find('.costPerUnitHidden');
    var userGivenCustomCostTotalMultiplier;

    if (!targetCostPerUnit.length && (targetTotalCost.length || targetTotalCostTotal.length)) {
        userGivenCustomCostTotalMultiplier = row.find('.userGivenCustomCostTotalMultiplier').val();
    }

    if (!userGivenCustomCostTotalMultiplier) {
        userGivenCustomCostTotalMultiplier = quantityInput.attr('data-defaultUnitCost');
    }
    var userGivenUnit = row.find('.userGivenUnit').val();

    if (!userGivenUnit) {
        userGivenUnit = row.find('.userGivenUnitContainer').text();
    }

    var userGivenThickness = row.find("input[id$='additional_thickness_mm']").val();

    if (!userGivenThickness) {
        userGivenThickness = row.find("input[id$='additional_thickness_in']").val();

        if (userGivenThickness) {
            userGivenThickness = parseFloat(userGivenThickness.replace(",", "."));
            if (userGivenThickness) {
                userGivenThickness = userGivenThickness * 25.4
            }
        }
    }
    var defaultThickness = quantityInput.attr('data-defaultThickness');
    var defaultThickness_in = quantityInput.attr('data-defaultThickness_in');
    var costConstruction = quantityInput.attr('data-costConstruction');
    var defaultTargetCostPerUnit = targetCostPerUnit.text();

    if (targetCostPerUnit.length || (targetTotalCost.length || targetTotalCostTotal.length)) {
        if (userGivenQuantity) {
            $.ajax({
                data: 'userGivenUnit=' + userGivenUnit + '&userGivenQuantity=' + userGivenQuantity + '&subType=' + quantityInput.attr('data-resourcesubtype') +
                    '&defaultDensity=' + quantityInput.attr('data-defaultDensity') + '&defaultThickness=' + defaultThickness + '&defaultThickness_in=' + defaultThickness_in + '&userGivenThickness=' + userGivenThickness +
                    '&userGivenCustomCostTotalMultiplier=' + userGivenCustomCostTotalMultiplier + '&costCalculationMethod=' + $costCalculationMethod + '&costConstruction=' + costConstruction +
                    '&labourCostCraftsman=' + $labourCostCraftsman + '&labourCostWorker=' + $labourCostWorker + '&finalMultiplier=' + $finalMultiplier +
                    '&defaultTargetCostPerUnit=' + defaultTargetCostPerUnit + '&currencyMultiplier=' + $currencyMultiplier + '&unitForData=' + quantityInput.attr('data-unitForData') +
                    '&resourceTypeCostMultiplier=' + quantityInput.attr('data-resourceTypeCostMultiplier') + '&allowVariableThickness=' + quantityInput.attr('data-allowVariableThickness') + '&massConversionFactor=' + quantityInput.attr('data-massconversionfactor'),
                type: 'GET',
                url: '/app/calculatecost',
                dataType: "json",
                contentType: "application/json;charset=utf-8",
                success: function (data) {
                    if (data.calculatedCost.uncalculable) {
                        if (targetCostPerUnit.length && userGivenQuantity !== "0" && !targetCostPerUnit.parent().find('.lccalcTooltip').length && targetCostPerUnit.text() !== "-") {
                            targetCostPerUnit.parent().prepend("<div class='lccalcTooltip fiveMarginRight'><i style='color: orange; font-size: 14px;' class='fa fa-exclamation-triangle smoothTip tooltip--right' data-tooltip='" + costCalcFailedMessage + "'></i></div>");
                        }

                        if (targetCostPerUnit.length) {
                            targetCostPerUnit.text("0");
                            targetCostPerUnitHidden.val("0");

                            if (targetTotalCost.length) {
                                targetTotalCost.text("0");
                                targetTotalCostHidden.val("0");
                            } else if (targetTotalCostTotal.length) {
                                targetTotalCostTotal.text("0");
                            }
                        }
                    } else {
                        if (targetCostPerUnit.parent().find('.lccalcTooltip').length) {
                            targetCostPerUnit.parent().find('.lccalcTooltip').remove();
                        }
                        if (targetCostPerUnit.length && data.calculatedCost.costPerUnit) {
                            targetCostPerUnit.text(data.calculatedCost.costPerUnit);
                            targetCostPerUnitHidden.val(data.calculatedCost.costPerUnitHidden);
                        }

                        if (targetTotalCost.length && data.calculatedCost.costTotal) {
                            targetTotalCost.text(data.calculatedCost.costTotal);
                            targetTotalCostHidden.val(data.calculatedCost.costTotalHidden);
                        } else if (targetTotalCostTotal.length && data.calculatedCost.costTotal) {
                            targetTotalCostTotal.text(data.calculatedCost.costTotal);
                        }

                        if (data) {
                            triggerFormChanged();
                        }
                    }

                    if (data.calculatedCost.convertedUnit) {
                        if ($currency) {
                            row.find('.costStructureUnit').text(' ' + $currency + ' / ' + data.calculatedCost.convertedUnit);
                            row.find('.totalCostCurrency').text(' ' + $totalCurrency);
                        } else {
                            row.find('.costStructureUnit').text(' ' + data.calculatedCost.convertedUnit);
                        }
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                }
            });
        } else if (userGivenUnit) {
            $.ajax({
                data: 'userGivenUnit=' + userGivenUnit + '&userGivenQuantity=0' + '&subType=' + quantityInput.attr('data-resourcesubtype') +
                    '&defaultDensity=' + quantityInput.attr('data-defaultDensity') + '&defaultThickness=' + defaultThickness + '&defaultThickness_in=' + defaultThickness_in + '&userGivenThickness=' + userGivenThickness +
                    '&userGivenCustomCostTotalMultiplier=' + userGivenCustomCostTotalMultiplier + '&costCalculationMethod=' + $costCalculationMethod + '&costConstruction=' + costConstruction +
                    '&labourCostCraftsman=' + $labourCostCraftsman + '&labourCostWorker=' + $labourCostWorker + '&finalMultiplier=' + $finalMultiplier +
                    '&defaultTargetCostPerUnit=' + defaultTargetCostPerUnit + '&currencyMultiplier=' + $currencyMultiplier + '&unitForData=' + quantityInput.attr('data-unitForData') +
                    '&resourceTypeCostMultiplier=' + quantityInput.attr('data-resourceTypeCostMultiplier') + '&allowVariableThickness=' + quantityInput.attr('data-allowVariableThickness') + '&massConversionFactor=' + quantityInput.attr('data-massconversionfactor'),
                type: 'GET',
                url: '/app/calculatecost',
                dataType: "json",
                contentType: "application/json;charset=utf-8",
                success: function (data) {
                    if (data.calculatedCost.uncalculable) {
                        if (targetCostPerUnit.length && userGivenQuantity !== "0" && !targetCostPerUnit.parent().find('.lccalcTooltip').length) {
                            targetCostPerUnit.parent().prepend("<div class='lccalcTooltip fiveMarginRight'><i style='color: orange; font-size: 14px;' class='fa fa-exclamation-triangle smoothTip tooltip--right' data-tooltip='" + costCalcFailedMessage + "'></i></div>");
                        }

                        if (targetCostPerUnit.length) {
                            targetCostPerUnit.text("0");
                            targetCostPerUnitHidden.val("0");

                            if (targetTotalCost.length) {
                                targetTotalCost.text("0");
                                targetTotalCostHidden.val("0");
                            } else if (targetTotalCostTotal.length) {
                                targetTotalCostTotal.text("0");
                            }
                        }
                    } else {
                        if (targetCostPerUnit.parent().find('.lccalcTooltip').length) {
                            targetCostPerUnit.parent().find('.lccalcTooltip').remove();
                        }
                        if (targetCostPerUnit.length && data.calculatedCost.costPerUnit) {
                            targetCostPerUnit.text(data.calculatedCost.costPerUnit);
                            targetCostPerUnitHidden.val(data.calculatedCost.costPerUnitHidden);
                        }

                        if (data) {
                            triggerFormChanged();
                        }
                    }

                    if (data.calculatedCost.convertedUnit) {
                        if ($currency) {
                            row.find('.costStructureUnit').text(' ' + $currency + ' / ' + data.calculatedCost.convertedUnit);
                            row.find('.totalCostCurrency').text(' ' + $totalCurrency);
                        } else {
                            row.find('.costStructureUnit').text(' ' + data.calculatedCost.convertedUnit);
                        }
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                }
            });

            if ($currency) {
                row.find('.costStructureUnit').text(' ' + $currency + ' / ' + userGivenUnit);
                row.find('.totalCostCurrency').text(' ' + $totalCurrency);
            } else {
                row.find('.costStructureUnit').text(' ' + userGivenUnit);
            }
        }
    } else if (userGivenUnit) {
        if ($currency) {
            row.find('.costStructureUnit').text(' ' + $currency + ' / ' + userGivenUnit);
            row.find('.totalCostCurrency').text(' ' + $totalCurrency);
        } else {
            row.find('.costStructureUnit').text(' ' + userGivenUnit);
        }
    }
}

function rerenderEnergyProfiles(elem, additionalQuestionId, resourceId, queryId, sectionId, questionId, inheritToChildrent, importMapperAdditionalQuestion, changeInputResourceId, indicatorId) {
    var thisElem = $(elem);
    var row = thisElem.closest('tr');
    var countryResourceId = thisElem.val();

    var profileSelect = row.find('.compensationEnergyProfileSelect');
    $(profileSelect).popover('destroy');
    var parentTd = $(profileSelect).parent();
    $(profileSelect).remove();

    var queryString = "countryResourceId=" + countryResourceId + "&resourceId=" + resourceId + "&queryId=" + queryId + "&sectionId=" + sectionId + "&questionId=" + questionId + "&additionalQuestionId=" + additionalQuestionId + "&indicatorId=" + indicatorId;

    if (changeInputResourceId) {
        queryString = queryString + "&changeInputResourceId=true";
    }
    $.ajax({
        type: 'POST',
        async: false,
        data: queryString,
        url: '/app/sec/util/getEnergyProfileSelect',
        beforeSend: function () {
            $(parentTd).append("<i class=\"fas fa-circle-notch fa-spin oneClickColorScheme\"></i>");
            /*if (inheritToChildren) {
                var childrenAdditionalQuestions = $('*[data-identifierForInheritance="' + inheritToChildren + '"]');

                if ($(childrenAdditionalQuestions).length) {
                    $(childrenAdditionalQuestions).find('.serviceLifeButton').trigger('click');
                }
            }*/
        },
        success: function (data) {

            setTimeout(function () {
                $(parentTd).find(".fa-circle-notch").remove();

                if (data.nameToRemove) {
                    $(parentTd).find("[name='" + data.nameToRemove.replace(".", "\\.") + "']").remove(); // kikkare
                }
                $(parentTd).append(data.toRender);
            }, 500);


            /*if (inheritToChildren) {
                var parentAdditionalQuestions = $('[data-inherit]');
                if (parentAdditionalQuestions.length) {
                    $(parentAdditionalQuestions).each(function () {
                        var identifier = $(this).attr('data-inherit');
                        $(this).children().on('change', function () {
                            var elementType = this.nodeName;
                            inheritSelectValueToChildren(identifier, $(this).val(), elementType);
                        });
                    });
                }
            }*/
        }, error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("draw failed for aq select");
        }
    });


}

function calculateParkingAvailabilityFactor(elem, customResourceRowClass) {
    var parkingConstraintFactor = parseFloat($(elem).val());

    if (parkingConstraintFactor) {
        var elementToIterate;

        if (customResourceRowClass) {
            elementToIterate = $('.' + customResourceRowClass);
        } else {
            elementToIterate = $('#travelMixAndDistancestravelAmountAndModeMixResourcesPermanentquery_resource_body > tr');
        }

        $(elementToIterate).each(function () {
            var tripType = $(this).find(".resourceEolProcessingType").val();
            var carShare = $(this).find("input[id*='carShare']");
            var transferableCarTransport = null;
            var nonCommuteTripImpact = null;

            if (tripType && tripType !== "commute") {
                nonCommuteTripImpact = 1 - ((1 - parkingConstraintFactor) / 4);
            }

            if (carShare.length) {
                var hiddenShare = parseFloat($(carShare).closest('td').find(".persistingHiddenDefaultAnswer").val());

                if (hiddenShare) {
                    var newShare = null;

                    if (nonCommuteTripImpact) {
                        newShare = hiddenShare * nonCommuteTripImpact;
                        carShare.val(Math.round(hiddenShare * nonCommuteTripImpact * 100) / 100);
                    } else {
                        newShare = hiddenShare * parkingConstraintFactor;
                        carShare.val(Math.round(hiddenShare * parkingConstraintFactor * 100) / 100);
                    }
                    transferableCarTransport = hiddenShare - newShare;
                }

                if (transferableCarTransport != null) {
                    var busShare = $(this).find("input[id*='publicBusShare']");

                    if (busShare.length) {
                        var hiddenBusShare = parseFloat($(busShare).closest('td').find(".persistingHiddenDefaultAnswer").val());
                        busShare.val(Math.round((transferableCarTransport * (hiddenBusShare / (100 - hiddenShare)) + hiddenBusShare) * 100) / 100);
                    }

                    var railShare = $(this).find("input[id*='publicRailShare']");

                    if (railShare.length) {
                        var hiddenRailShare = parseFloat($(railShare).closest('td').find(".persistingHiddenDefaultAnswer").val());
                        railShare.val(Math.round((transferableCarTransport * (hiddenRailShare / (100 - hiddenShare)) + hiddenRailShare) * 100) / 100);
                    }

                    var walkShare = $(this).find("input[id*='walkBikeShare']");

                    if (walkShare.length) {
                        var hiddenWalkShare = parseFloat($(walkShare).closest('td').find(".persistingHiddenDefaultAnswer").val());
                        walkShare.val(Math.round((transferableCarTransport * (hiddenWalkShare / (100 - hiddenShare)) + hiddenWalkShare) * 100) / 100);
                    }
                }
            }
        });

        if (customResourceRowClass) {
            $(elementToIterate).removeClass(customResourceRowClass);
        }
    }
}

function openImportFromDatasets(targetEntityId, entityId, queryId, indicatorId, queryName, mainPageCheck, noDesignFoundMessage, alwaysFetch = false) {
    console.log("Child/Design ID:" + " " + targetEntityId + "\n" + "Parent ID:" + " " + entityId + "\n" + "Query ID:" + " " + queryId + "\n" + "Indicator ID:" + " " + indicatorId + "\n" + "Query Name: " + " " + queryName + "\n" + "Main Page Check: " + " " + mainPageCheck);
    if (targetEntityId && entityId && indicatorId) {
        var queryString = 'targetEntityId=' + targetEntityId + '&entityId=' + entityId + '&indicatorId=' + indicatorId;
        if (mainPageCheck) {
            queryString = queryString + '&mainPageCheck=' + mainPageCheck;
        }
        if (queryId) {
            queryString = queryString + '&queryId=' + queryId;
        }
        if (queryName) {
            queryString = queryString + '&queryName=' + queryName;
        }
        if (!alwaysFetch && $("#importFromEntitiesContent").length > 0) {
            $("#importFromEntities").modal({backdrop: 'static', keyboard: false})
        } else {
            $.ajax({
                type: 'POST',
                data: queryString,
                url: '/app/sec/util/importDatasetsFromEntities',
                success: function (data, textStatus) {
                    if (data.output.length > 0) {
                        $("#importFromEntities").empty().append(data.output).modal({ backdrop: 'static', keyboard: false })
                    } else {
                        Swal.fire({
                            text: noDesignFoundMessage,
                            icon: "warning",
                            confirmButtonText: 'OK',
                            showCancelButton: false
                        });
                    }

                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                }
            });
        }

    }
}


function noImportLicenseFound() {
    $("#noImportLicenseModal").removeClass("hide");
}

function dataLoadingFeaturePromise(resolve, queryId, entityId, indicatorId, definingSectionId, definingQuestionId, targetSectionId, targetQuestionId, quantityResolutionSectionId, quantityResolutionQuestionId, dataLoadingDatasets, buttonName, loadResourceParameterDecimals) {
    var errorMessage;

    if (queryId && definingSectionId && definingQuestionId) {
        var fieldName = targetSectionId + '.' + targetQuestionId;
        var targetSelect = $('#' + definingSectionId + '\\.' + definingQuestionId);

        if (!targetSelect.length) {
            targetSelect = $('#' + definingSectionId + definingQuestionId + 'ResourcesSelect');
        }
        var whereToAppend = '#' + targetSectionId + targetQuestionId + 'Resources';
        var header = $(whereToAppend + 'header');

        var quantityResolutionValue;
        if (quantityResolutionSectionId && quantityResolutionQuestionId) {
            var quantityResolutionSelect = $('#' + quantityResolutionSectionId + '\\.' + quantityResolutionQuestionId);

            if (quantityResolutionSelect.length && quantityResolutionSelect.val()) {
                quantityResolutionValue = quantityResolutionSelect.val()
            }
        }

        if (targetSelect.length && targetSelect.val()) {
            if ((targetSectionId && targetQuestionId && $(whereToAppend).length) || (!targetSectionId && !targetSectionId)) {
                $.ajax({
                    data: 'queryId=' + queryId + '&filterValue=' + targetSelect.val(),
                    type: 'GET',
                    url: '/app/dataloadingfeatureresources',
                    dataType: "json",
                    contentType: "application/json;charset=utf-8"
                }).done(function (data) {
                    if (data.resources.length) {
                        $('#' + targetSectionId + targetQuestionId + 'Resources > tbody > tr').each(function () {
                            $(this).remove();
                        });

                        if (header.length && header.is(':hidden')) {
                            header.show();
                        }

                        $.each(data.resources, function (index) {
                            var resourceId = this.resourceId;
                            var profileId = this.profileId;

                            if ($(whereToAppend).length) {
                                $.ajax({
                                    async: false, type: 'POST',
                                    data: 'preventDoubleEntries=' + false + '&entityId=' + entityId + '&resourceId=' + resourceId + '&profileId=' + profileId + '&indicatorId=' + indicatorId + '&queryId=' + queryId + '&sectionId=' + targetSectionId + '&questionId=' + targetQuestionId + '&fieldName=' + fieldName + '&showGWP=' + false + '&newResourceOnSelect=true' + '&quantityResolutionValue=' + quantityResolutionValue,
                                    url: '/app/addresource',
                                    success: function (data) {
                                        $(whereToAppend).append(data.output);
                                        $('.newResourceRow').addClass("highlighted loadingFeatureResource");
                                        setTimeout(function () {
                                            $('.newResourceRow').removeClass('newResourceRow highlighted');
                                        }, 2000);
                                        $('[rel="popover"]').popover({
                                            placement: 'top',
                                            template: '<div class="popover"><div class="arrow"></div><div class="popover-content"></div></div>'
                                        });

                                        $('.numeric').on('input propertychange', function () {
                                            var start = this.selectionStart;
                                            end = this.selectionEnd;
                                            var val = $(this).val();
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
                                                var dotsAndCommas = ((val.match(/\./g) || []).length + (val.match(/,/g) || []).length);

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

                                        $(whereToAppend).find('.dropdown').dropdown();

                                        enableCalculationOnQueryForm()
                                    },
                                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                                    }
                                });
                            }

                            if (index === 0 && dataLoadingDatasets.length) {
                                $.ajax({
                                    type: 'POST',
                                    data: {
                                        datasets: JSON.stringify(dataLoadingDatasets),
                                        resourceId: resourceId,
                                        profileId: profileId,
                                        loadResourceParameterDecimals: loadResourceParameterDecimals
                                    },
                                    url: '/app/dataloadingfeatureotherquestionvalue',
                                    success: function (data) {
                                        if (data.answers.length) {
                                            $.each(data.answers, function () {
                                                var sectionId = this.sectionId;
                                                var questionId = this.questionId;
                                                var otherQuestionAnswer = this.otherQuestionAnswer;
                                                var element = $('#' + sectionId + '\\.' + questionId);

                                                if (element.length && !element.prop('readonly') && !element.is(':disabled')) {
                                                    element.val(otherQuestionAnswer);
                                                }

                                            })
                                        }
                                    },
                                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                                    }
                                });
                            }
                        });

                        var parkingFactor = $("select[id*='parkingAvailabilityFactor']");
                        if ($(parkingFactor).length) {
                            calculateParkingAvailabilityFactor($(parkingFactor), "loadingFeatureResource")
                        }
                    } else {
                        errorMessage = "DataLoadingFeature " + buttonName + ": No resources found for filterValue: " + targetSelect.val();
                    }
                });
            } else {
                errorMessage = "DataLoadingFeature " + buttonName + ": Could not find target table to append resources in: queryId: " + queryId + ", targetSectionId: " + targetSectionId + ", targetQuestionId: " + targetQuestionId;
            }
        } else if (!targetSelect.length) {
            errorMessage = "DataLoadingFeature " + buttonName + ": Could not find target select to load resources from: queryId: " + queryId + ", definingSectionId: " + definingSectionId + ", definingQuestionId: " + definingQuestionId;
        } else {
            errorMessage = "DataLoadingFeature " + buttonName + ": No selection."
        }
    } else {
        errorMessage = "DataLoadingFeature " + buttonName + ": Missing mandatory parameters: indicatorId: " + indicatorId + ", entityId: " + entityId + ", queryId: " + queryId + ", definingSectionId: " + definingSectionId + ", definingQuestionId: " + definingQuestionId;
    }

    if (errorMessage) {
        if (errorMessage.indexOf("No selection") === -1) {
            $.ajax({
                type: 'POST',
                data: 'errorMessage=' + errorMessage,
                url: '/app/logjserror',
                success: function (data) {
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                }
            });
        }
        swal.showValidationMessage("Could not load defaults")
    } else {
        setTimeout(function () {
            resolve()
        }, 1000);
    }
}

function openAccountSetting(accountId) {
    if (accountId) {

    }
}

function executeCopyConstructionFromOtherDesign(entityId, quarterlyInputEnabled, monthlyInputEnabled, selectId, indicatorId, queryId, sectionId,
                                                questionId, resourceTableId, fieldName, preventDoubleEntries, showGWP,
                                                doubleEntryWarning, constructionId, accountId, copiedResourceId, originalDatasetAnswers,
                                                persistChangedManualId, resourceIdtxt, copiedDesignId, copiedDatasetId, copiedUniqueConstructionIdentifier,
                                                dummyGroupCopy, groupParentConstructionQty) {

    var uniqueConstructionIdentifier = constructionId + guid();
    addResourceFromSelect(entityId, quarterlyInputEnabled, monthlyInputEnabled, selectId, indicatorId, queryId, sectionId,
        questionId, resourceTableId, fieldName, preventDoubleEntries, showGWP,
        doubleEntryWarning, uniqueConstructionIdentifier, accountId, copiedResourceId, originalDatasetAnswers,
        persistChangedManualId, resourceIdtxt, copiedDesignId, copiedDatasetId, null, null, groupParentConstructionQty);
    addConstructionResources(constructionId, entityId, quarterlyInputEnabled, monthlyInputEnabled, selectId, indicatorId, queryId, sectionId,
        questionId, resourceTableId, fieldName, preventDoubleEntries, showGWP,
        doubleEntryWarning, uniqueConstructionIdentifier, accountId, copiedDesignId, copiedUniqueConstructionIdentifier, dummyGroupCopy);
}

function commaPrevent(element) {
    var string = $(element).val();
    string.replace(",", ".");
    $(element).val().replace(",", ".")
}

function assertAppendIndicatorToNewDesign(message, overOneIndicator, button) {
    if (!$(button).hasClass("disableAssert")) {
        var checkbox = $(".indicatorCheckboxDesign");
        var hideAppend = !overOneIndicator
        $('#indicatorsForNewDesign > tbody ').children('tr').not('.defaultHiddenIndicator').remove();
        var i = 0;
        $(checkbox).each(function (item) {
            if ($(this).is(":checked")) i++;
        })

        if (i <= 1) {
            $(checkbox).each(function (item) {
                if ($(this).is(":checked")) {
                    appendToNewDesignCreation($(this), message, null, true, hideAppend, true);
                }
            })
            $("#indicatorHelps_label").hide();
        } else {
            $(checkbox).each(function (item) {
                if ($(this).is(":checked")) {
                    appendToNewDesignCreation($(this), message, null, false, hideAppend);
                }
            })
            $("#indicatorHelps_label").show();

        }
        $(button).addClass("disableAssert")
    }


}

//**********************************************************************
//WORKFLOW
//**********************************************************************

//saveWorkFlow call to backend

function saveWorkFlow() {
    var json = {
        jsonWorkFlow: jsonWorkFlow
    };
    var url = '/app/sec/util/saveWorkFlow';
    $.ajax({
        type: 'POST',
        async: false,
        dataType: 'json',
        data: JSON.stringify(json),
        contentType: "application/json; charset=utf-8",
        url: url,
        success: function success(data) {
            if (data && data.workFlow) {
                jsonWorkFlow = data.workFlow;
            }

            if (data && data.error) {
            } //console.log(data.error)
            //console.log(data);

        }
    });
}

function onChangeIndicatorWorkFlow(element) {
    var selectedId = $(element).val();
    jsonWorkFlow.defaultIndicatorId = selectedId;
    saveWorkFlow();
    updateWorkFlowContent();
}

function onChangeWorkFlowId(element, localizedSave, localizedClear, resetWorlflow) {
    var selectedId = $(element).val();
    jsonWorkFlow.defaultWorkFlowId = selectedId;
    saveWorkFlow();
    updateWorkFlowContent(localizedSave, localizedClear, resetWorlflow);
}

function updateWorkFlowContent(localizedSave, localizedClear, resetWorlflow) {
    var workFlowId = jsonWorkFlow.defaultWorkFlowId;
    var workFlowList = jsonWorkFlow.workFlowList;
    if (!workFlowList) {
        return
    }

    buildSelectWorkFlow("#workFlowId", jsonWorkFlow);

    var workFlow = workFlowList.find(function (w) {
        return w.workFlowId == workFlowId;
    });
    var divContent = $("#workFlowContent");
    divContent.empty();
    var disableCheckBox = workFlow.workFlowType == "onBoarding" ? true : false
    workFlow.stepList.forEach(function (step) {
        var divSection = document.createElement('div');
        var checkbox = document.createElement('input');
        var helpDiv = document.createElement('div');
        var noteOpenButton = document.createElement('i');
        var noteField = document.createElement('textarea');
        var noteSaveButton = document.createElement("button");
        var noteRemoveButton = document.createElement("button");
        var noteDiv = document.createElement('div');

        noteDiv.id = "noteDiv" + step.stepId;
        noteDiv.classList.add("workFlowHelp", "hidden");
        checkbox.type = "checkbox";
        checkbox.name = "name";
        divSection.classList.add("floatingProgressInner");
        $(checkbox).prop('checked', step.checked);
        $(checkbox).prop("disabled", disableCheckBox)
        checkbox.id = step.stepId;
        $(checkbox).on("click", function () {
            updateWorkFlowModel(checkbox, step.stepId)
        });
        helpDiv.classList.add("workFlowHelp", "hidden");
        helpDiv.id = "help" + step.stepId;
        helpDiv.innerHTML = step.localizedHelp;


        var label = document.createElement('label');
        label.id = "helpOpen" + step.stepId;
        $(label).on("click", function () {
            openHelpWorkFlow(step.stepId, true)
        });
        $(label).html('<span class="fa fa-plus oneClickColorScheme"></span>' + "  " + step.localizedName);
        if (step.checked) {
            $(label).addClass("checkedItemWorkFlow");
        }
        //noteButton & noteField initialize
        noteField.setAttribute("type", "text");
        noteField.value = step.note;
        var noteFieldId = "content" + step.stepId;
        noteField.classList.add("workFlowStepNote");
        noteField.id = noteFieldId;
        noteSaveButton.classList.add("btn");
        if (noteField.value) {
            noteSaveButton.classList.add("btn-primary", "saveNoteButton");
        } else {
            noteSaveButton.classList.add("btn-default", "saveNoteButton");
            noteSaveButton.setAttribute("disabled", true);
        }
        noteSaveButton.innerText = localizedSave;
        $(noteSaveButton).on("click", function () {
            updateNoteWorkFlow(noteFieldId, step.stepId, noteSaveButton)
        });

        $(noteField).on("keyup", function () {
            toggleDisableClass(noteField, noteSaveButton)
        });

        noteOpenButton.id = "button" + noteFieldId;
        if (step.note) {
            noteOpenButton.classList.add("fa-sticky-note", "fas", "noteButton");
        } else {
            noteOpenButton.classList.add("fa-sticky-note", "far", "noteButton");
        }
        noteRemoveButton.innerText = localizedClear;

        $(noteRemoveButton).on("click", function () {
            removeNote(noteFieldId, step.stepId);
            toggleDisableClass(noteField, noteSaveButton);
        });

        noteRemoveButton.classList.add("btn", "btn-default", "saveNoteButton");
        $(noteOpenButton).on("click", function () {
            openNoteWorkFlow(step.stepId)
        });
        divSection.appendChild(label);
        divSection.appendChild(checkbox);
        divSection.appendChild(noteOpenButton);
        noteDiv.appendChild(noteField);
        noteDiv.appendChild(noteSaveButton);
        noteDiv.appendChild(noteRemoveButton);
        divContent.append(divSection).append(noteDiv).append(helpDiv);

        if (step.isExpanded) {
            openHelpWorkFlow(step.stepId, false)
        }
    })
    if (disableCheckBox) {
        var btnContainer = document.createElement("div");
        btnContainer.classList.add("centralizeWrapper");
        var resetBtnOnboarding = document.createElement("button");
        resetBtnOnboarding.id = "resetWorkflowBtn"

        resetBtnOnboarding.innerText = resetWorlflow;
        $(resetBtnOnboarding).on("click", function () {
            resetWorlflowAndReload(workFlow)
        })
        resetBtnOnboarding.classList.add("btn", "btn-primary");
        btnContainer.appendChild(resetBtnOnboarding)
        divContent.append(btnContainer)

    }
}

function buildSelectWorkFlow(elementId, jsonWorkFlow) {
    if (jsonWorkFlow) {
        var defaultWorkFlowId = jsonWorkFlow.defaultWorkFlowId;
        var workFlowList = jsonWorkFlow.workFlowList;
        $(elementId).empty();

        if (workFlowList) {
            workFlowList.forEach(function (el) {
                var option = $('<option></option>').attr("value", el.workFlowId).text(el.localizedName);

                if (el.workFlowId === defaultWorkFlowId) {
                    option.prop("selected", true);
                }

                $(elementId).append(option);
            });
        }
    }
}

function updateWorkFlowModel(element, stepId, disableTickBox) {
    var workFlowId = jsonWorkFlow.defaultWorkFlowId;
    var workFlowList = jsonWorkFlow.workFlowList;
    if (!Array.isArray(workFlowList)) return;
    var workFlow = workFlowList.find(function (w) {
        return w.workFlowId == workFlowId;
    });
    if (!Array.isArray(workFlow.stepList)) return;
    var step = workFlow.stepList.find(function (step) {
        return step.stepId == stepId;
    });
    var helpOpen = "#helpOpen" + stepId; //checkbox

    if (element) {
        var bool = $(element).is(':checked');
        step.checked = bool;
        $(helpOpen).toggleClass("checkedItemWorkFlow");
        if (bool && disableTickBox) {
            step.disabled = disableTickBox
        }
    } //Persistence opening help


    var helpSection = $("#help" + stepId);
    var isExpanded = !helpSection.hasClass("hidden");
    step.isExpanded = isExpanded;
    saveWorkFlow();
}

function updateNoteWorkFlow(element, stepId, button) {
    var workFlowId = jsonWorkFlow.defaultWorkFlowId;
    var workFlow = jsonWorkFlow.workFlowList.find(function (w) {
        return w.workFlowId == workFlowId;
    });
    var step = workFlow.stepList.find(function (step) {
        return step.stepId == stepId;
    });
    var contentStep = $("#content" + stepId);
    step.note = $("#" + element).val();

    if (button) {
        var doneIcon = $("<i>", {
            class: "icon-done right clearBoth"
        }).insertAfter($(contentStep)).delay(1000).fadeOut('fast');
        $("#button" + element).removeClass("far fa-sticky-note").addClass("fas fa-sticky-note");
    } else {
        $("#button" + element).removeClass("fas fa-sticky-note").addClass("far fa-sticky-note");
    }
    saveWorkFlow();
}

function openHelpWorkFlow(stepId, save) {
    var helpSectionId = "#help" + stepId;
    var helpOpenerId = "#helpOpen" + stepId;
    $(helpSectionId).toggleClass("hidden");
    $(helpOpenerId).children().first().toggleClass('fa fa-plus').toggleClass('fa fa-minus');

    if (save) {
        updateWorkFlowModel(null, stepId);
    }
}

function openNoteWorkFlow(stepId) {
    var noteSectionId = $("#noteDiv" + stepId);
    $(noteSectionId).toggleClass("hidden");
}

function toggleDisableClass(noteField, saveButton) {
    if ($(noteField).val()) {
        $(saveButton).removeClass("btn-default").addClass("btn-primary").prop('disabled', false);
    } else {
        $(saveButton).removeClass("btn-primary").addClass("btn-default").attr("disabled", true);
    }
}

function removeNote(noteFieldId, stepId) {
    $("#" + noteFieldId).val("");
    updateNoteWorkFlow(noteFieldId, stepId);
}

function showHideDivHelpWorkFlow(tab) {
    var element = $("#" + tab + "Div");
    var header = $("#" + tab + "Header");

    if (element) {
        $(element).show();
        $(element).siblings().hide();
        $(header).parent().removeClass("unselectedHeader").addClass("selectedHeader");
        $(header).parent().siblings().addClass("unselectedHeader").removeClass("selectedHeader");
        setTabFrame(tab);
    }
}

function resetWorlflowAndReload(workFlow) {
    var i = 0
    while (i < workFlow.stepList.length) {
        var step = workFlow.stepList[i]
        var stepObj = $("#" + step.stepId)
        if ($(stepObj).is(":checked") && !step.disableReset) {
            $(stepObj).prop("checked", false)
            updateWorkFlowModel($(stepObj), step.stepId, true)
        }
        i++
        if (i == workFlow.stepList.length - 1) {
            window.location.reload()
        }
    }
}

function closeNavProg(toHideNavId) {
    var nav = document.getElementById(toHideNavId);
    var toggler = document.getElementById("sideTogglerProgress");
    var mainContent = document.getElementById("mainContent");
    var guidanceHeader = document.getElementById("progressHeader");
    var warningMessageDiv = document.getElementById("messageContent");
    var querySaveButton = document.getElementById("saveAndResultButtonDiv");


    if (nav && !nav.classList.contains("hiddenSideBar")) {
        nav.style.width = "0";
        nav.style.padding = "0";
        $(mainContent).removeClass('homePageFixedWidth');
        $(warningMessageDiv).removeClass('padding-update homePageFixedWidth')
        $(querySaveButton).removeClass("fixedToRight");

        $(mainContent).css({
            "margin-left": "10px",
            "width": "auto",
            "overflow": "auto"
        });

        $("body").css({
            "width": "auto",
            "overflow-y": "scroll"
        });
        $(toggler).removeClass('hidden');
        $(toggler).css({
            "margin-left": "0",
            "width": "auto"
        });
        nav.classList.add('hiddenSideBar');
        if (guidanceHeader) {
            setTimeout(function () {
                guidanceHeader.style.display = "none";
            }, 500);
        }
        setTabFrame();
    }
}

function toggleNavProg(id, tab) {
    var nav = document.getElementById("myProgressNav");
    var toggler = document.getElementById("sideTogglerProgress");
    var mainContent = document.getElementById("mainContent");
    var guidanceHeader = document.getElementById("progressHeader");
    var querySaveButton = document.getElementById("saveAndResultButtonDiv");
    var warningMessageDiv = document.getElementById("messageContent");

    if (nav && nav.classList.contains("hiddenSideBar")) {
        $(nav).css({
            "width": "330px",
            "padding": "30px"
        });
        if ($(".sideBarChartWrapper#lifeCycleChart").length > 0) {
            var chart = $(".sideBarChartWrapper#lifeCycleChart").highcharts().setSize(297, 400)
        }
        $(querySaveButton).addClass("fixedToRight");
        $(warningMessageDiv).addClass('padding-update')

        if ($(window).width() < 1500) {
            $("body").css({
                "width": "130vw"
            });
        }
        $(mainContent).css({
            "margin-left": "380px",
            "overflow": "auto",
            "width": "auto"
        });
        nav.classList.remove('hiddenSideBar');
        guidanceHeader.style.display = "inline-block";

        if (toggler) {
            $(toggler).css({
                "margin-left": "0",
                "width": "0"
            });
            toggler.classList.add('hidden');
        }
    }
    showHideDivHelpWorkFlow(tab);
    setWidthFrame();
}

//**********************************************************************
//FRAME STATUS
//**********************************************************************


function setDefaultTabFrame() {
    if (frameStatus) {
        var isOpen = frameStatus.isOpen;
        var tab = frameStatus.selectedTab;
        var toggle; //Adding exception for mobile screen

        if (isOpen && tab && window.screen.width > 600) {
            switch (tab) {
                case "${TAB_WORKFLOW}":
                    toggle = 'sideTogglerHelp';
                    break;

                case "${TAB_HELP}":
                    toggle = 'sideTogglerProgress';
                    break;

                case "${TAB_RESULTBAR}":
                    toggle = 'sideTogglerProgress';
                    break;
            }

            toggleNavProg(toggle, tab);
        }
    }
}

function setTabFrame(tab) {
    if (frameStatus) {
        if (tab) {
            frameStatus.isOpen = true;
            frameStatus.selectedTab = tab;
        } else {
            frameStatus.isOpen = false;
        }
        saveFrameStatus();
    }
} //saveFrameStatus call to backend


function saveFrameStatus() {
    if (frameStatus) {
        var json = {
            frameStatus: frameStatus
        };
        var url = '/app/sec/util/saveFrameStatus';
        $.ajax({
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(json),
            contentType: "application/json; charset=utf-8",
            url: url,
            success: function success(data) {
                if (data && data.frameStatus) {
                    frameStatus = data.frameStatus;
                }

                if (data && data.error) {//console.log(data.error)
                }
            }
        });
    }
}

$("select.dropdown").prop('disabled', function () {
    //console.log($("select.dropdown"));
    return $('option', this).length < 2;
}); //Make workflow resizable relatively with mainbody content ********************************

var MAX_WIDTH_FRAME = 3600;
var MIN_WIDTH_FRAME = 300;
var holdingFrame = false;
$(function () {
    var min = MIN_WIDTH_FRAME;
    var max = MAX_WIDTH_FRAME;
    var mainmin = 200;
    $('#split-bar').on('mousedown', function (e) {
        e.preventDefault();
        $(document).on('mousemove', function (e) {
            holdingFrame = true;
            e.preventDefault();
            var x = e.pageX - $('#myProgressNav').offset().left;

            if (x > min && x < max && e.pageX < $(window).width() - mainmin) {
                if ($(".sideBarChartWrapper#lifeCycleChart").length > 0) {
                    var chart = $(".sideBarChartWrapper#lifeCycleChart").highcharts().setSize(x, 400)
                }
                $('#myProgressNav').css("width", x);

                if ($(window).width() > 1500) {
                    $('#mainContent').css("margin-left", x + 50);
                }
            }
        });
    });
    $(document).on('mouseup', function (e) {
        if (holdingFrame) {
            saveWidthFrame("myProgressNav");
            holdingFrame = false;
        }

        $(document).off('mousemove');
    });
});

function saveWidthFrame(divId) {
    var widthFrame = $('#' + divId).width();
    var width = new Number(widthFrame);

    if (width !== NaN && frameStatus && frameStatus.isOpen) {
        frameStatus.width = width;
        saveFrameStatus();
    }
}

function setWidthFrame() {
    if (frameStatus && frameStatus.isOpen) {
        var min = MIN_WIDTH_FRAME;
        var max = MAX_WIDTH_FRAME;
        var divId = "myProgressNav";
        var widthFrame = frameStatus.width;

        if (!widthFrame || widthFrame < min) {
            widthFrame = min;
        }

        if (widthFrame > max) {
            widthFrame = max;
        }

        $('#' + divId).width(widthFrame);

        if ($(window).width() > 1500) {
            $('#mainContent').css("margin-left", widthFrame + 50);
        }
    }
}

function openStepNote(elemId) {
    var element = $("#" + elemId);

    if ($(element).hasClass("hidden")) {
        $(element).removeClass("hidden");
    } else {
        $(element).addClass("hidden");
    }
}

//**********************************************************************
//**************************************************************
//Scope Section function
//**************************************************************
// Add and Modify Design Modal


function modifyDesignModal(designId, formId) {
    var json = {
        designId: designId,
        formId: formId
    };
    var url = "/app/sec/util/modifyDesignModal";
    ajaxCallForDesignModal(url, json);
}

function modifyPeriodModal(operatingPeriodId, formId) {
    var json = {
        operatingPeriodId: operatingPeriodId,
        modifyPeriod: true,
        formId: formId
    };
    var url = "/app/sec/util/modifyPeriodModal";
    ajaxCallForPeriodModal(url, json);
}

function addNewDesignModal(entityId, formId, element, indicatorId, appendTo) {
    //element params is the link and so "this". It' used to prevent multiple pressing of the button/link
    var json = {
        entityId: entityId,
        formId: formId,
        indicatorId: indicatorId
    };
    var url = "/app/sec/util/addNewDesignModal";
    ajaxCallForDesignModal(url, json, element, appendTo);
}

function addNewPeriodModal(entityId, formId, element, indicatorId) {
    //element params is the link and so "this". It' used to prevent multiple pressing of the button/link
    var json = {
        entityId: entityId,
        formId: formId,
        indicatorId: indicatorId
    };
    var url = "/app/sec/util/addNewPeriodModal";
    ajaxCallForPeriodModal(url, json, element);
}

function addNewDesignModalIM(entityId, formId, element, indicatorId, preventSubmit) {
    //element params is the link and so "this". It' used to prevent multiple pressing of the button/link
    var json = {
        entityId: entityId,
        formId: formId,
        indicatorId: indicatorId
    };
    var url = "/app/sec/util/addNewDesignModal";
    ajaxCallForDesignModal(url, json, element, preventSubmit);
} //Copy Design Modal


function copyDesignModal(originalEntityId, formId) {
    var json = {
        originalEntityId: originalEntityId,
        formId: formId,
        copy: true
    };
    var url = "/app/sec/util/modifyDesignModal";
    ajaxCallForDesignModal(url, json);
}

function copyPeriodModal(operatingPeriodId, formId) {
    var json = {
        operatingPeriodId: operatingPeriodId,
        formId: formId,
        copyPeriod: true
    };
    var url = "/app/sec/util/modifyPeriodModal";
    ajaxCallForPeriodModal(url, json);
}

function toggleNmdElements(elem) {
    var checkBoxes = $('.nmd3TotalProductCheckbox');
    $(checkBoxes).not(elem).prop('checked', false);

    var row = $(elem).closest('tr');
    var name = $(row).find('.totalProductName').html();

    if ($(checkBoxes).is(':checked')) {
        $('.nmd3ComponentRow').addClass("satisfiedNmdRow");
        $('.nmd3ComponentPartRow').addClass("satisfiedNmdRow");
        $('.nmd3ComponentSelect').addClass("satisfiedNmdRow");
        $('.nmd3PartialComponentRow').addClass("satisfiedNmdRow");
        $('.nmd3PartialComponentPartRow').addClass("satisfiedNmdRow");
        $('.coveredBy').empty().append('<span class="elementCoveringProducts"><div class="elementCoveringProductTOTAL margin-bottom-10">' + name + '</div></span>');
    } else {
        $('.nmd3ComponentRow').removeClass("satisfiedNmdRow");
        $('.nmd3ComponentPartRow').removeClass("satisfiedNmdRow");
        $('.nmd3ComponentSelect').removeClass("satisfiedNmdRow");
        $('.nmd3PartialComponentRow').removeClass("satisfiedNmdRow");
        $('.nmd3PartialComponentPartRow').removeClass("satisfiedNmdRow");
        $('.coveredBy').empty().append('Niet gedekt');
        resolveSatisfiedRows();
    }
}

function deletePartialComponentRows(elem, productId) {
    $(elem).closest("tr").remove();
    $('.partialComponentProfileSet' + productId).remove();
    $('.elementCoveringProducts').remove();
    $('.coveredByMultipleWarning').empty();
    $('.nmd3ComponentRow').removeClass("satisfiedNmdRow");
    $('.nmd3ComponentPartRow').removeClass("satisfiedNmdRow");
    resolveSatisfiedRows();
    resolveEmptyCoveredByFields();
}

function resolveEmptyCoveredByFields() {
    $.each($('.coveredBy'), function () {
        if ($(this).is(":empty")) {
            $(this).html("Niet gedekt");
        }
    });
}

function resolveSatisfiedRows() {
    var nmd3ParitalComponentRows = $('.nmd3PartialComponentRow');

    if ($(nmd3ParitalComponentRows).length) {
        for (var i = 0; i < $(nmd3ParitalComponentRows).length; i++) {
            var nmd3ParitalComponentRow = nmd3ParitalComponentRows[i];
            var coveredElementIdsString = $(nmd3ParitalComponentRow).attr("data-coveredelementids");

            if (coveredElementIdsString) {
                var coveredElementIds = coveredElementIdsString.split(",");

                for (var j = 0; j < coveredElementIds.length; j++) {
                    var coveredElementId = coveredElementIds[j];
                    var coveredRow = $("#nmd3ComponentRow" + coveredElementId);

                    if ($(coveredRow).length && !$(coveredRow).hasClass("satisfiedNmdRow")) {
                        $(coveredRow).addClass("satisfiedNmdRow");
                        $('.partialElementId' + coveredElementId).addClass("satisfiedNmdRow");

                        var coveredBy = $(coveredRow).find('.coveredBy');

                        var name = $(nmd3ParitalComponentRow).find('.partialProductName').html();
                        var productId = $(nmd3ParitalComponentRow).attr('data-productId');

                        if ($(coveredBy).find('.elementCoveringProducts').length) {
                            $(coveredBy).append('<span class="elementCoveringProducts"><div class="elementCoveringProduct' + productId + ' margin-bottom-10">' + name + '</div></span>');
                            $(coveredRow).find('.coveredByMultipleWarning').empty().append('<i style="color: orange; font-size: 14px;" class="fa fa-exclamation-triangle"></i> <strong>Covered by multiple products!</strong>');
                        } else {
                            $(coveredBy).empty().append('<span class="elementCoveringProducts"><div class="elementCoveringProduct' + productId + ' margin-bottom-10">' + name + '</div></span>');
                        }
                    }
                }
            }
        }
    }
}

function addNonTotalProductAndSatisfy(elem) {
    var productId = $(elem).val();
    var row = $(elem).closest('tr');
    var productClass = "partialProfileSet" + productId;

    $.ajax({
        type: 'POST',
        data: 'productId=' + productId,
        url: '/app/sec/nmdApi/loadPartialProfileSets',
        beforeSend: function () {
            $('.nmd3TotalProductCheckbox').addClass("removeClicks");
            $('.kikkareselector').addClass("removeClicks");
            $('.nmd3ComponentSelect').addClass("removeClicks");
            $('#addNmdConstructionButton').addClass("removeClicks");
            $(".nmd3NoProductsFound").remove();
            $("<tr class='nmd3LoaderRow'><td><i class='fas fa-circle-notch fa-spin oneClickColorScheme' aria-hidden='true'></i></td><td colspan='3'> <span class='loadingDots'>Producten laden.</span></td></tr>").insertAfter(row);
        },
        success: function (data) {
            $(elem).val($("#nonTotalSelector option:first").val());
            var coveredElementIds = data.coveredElementIds;
            $(".nmd3LoaderRow").remove();
            $(data.toRender).insertAfter(row);
            $('.kikkareselector').removeClass("removeClicks");
            $('.nmd3TotalProductCheckbox').removeClass("removeClicks");
            $('.nmd3ComponentSelect').removeClass("removeClicks");
            $('#addNmdConstructionButton').removeClass("removeClicks");

            if (coveredElementIds.length) {
                for (var i = 0; i < coveredElementIds.length; i++) {
                    var coveredElementId = coveredElementIds[i];
                    var coveredRow = $("#nmd3ComponentRow" + coveredElementId);

                    if ($(coveredRow).length) {
                        if (!$(coveredRow).hasClass("satisfiedNmdRow")) {
                            $(coveredRow).addClass("satisfiedNmdRow");
                        }
                        if (data.coveredBy) {
                            var coveredBy = $(coveredRow).find('.coveredBy');
                            if ($(coveredBy).find('.elementCoveringProducts').length) {
                                $(coveredBy).append(data.coveredBy);
                                $(coveredRow).find('.coveredByMultipleWarning').empty().append('<i style="color: orange; font-size: 14px;" class="fa fa-exclamation-triangle"></i> <strong>Covered by multiple products!</strong>');
                            } else {
                                $(coveredBy).empty().append(data.coveredBy);
                            }
                        }
                    }
                }
            }
        }
    });
}

function showLinkedProductRowAndSatisfy(elem, elementId, token) {
    var elementClass = "partialElementId" + elementId;
    var productId = $(elem).val();
    var row = $(elem).closest('tr');

    $(".partialProfileSet" + productId).remove();

    $.ajax({
        type: 'POST',
        data: 'elementId=' + elementId + '&productId=' + productId,
        url: '/app/sec/nmdApi/loadProfileSets',
        beforeSend: function () {
            $('.nmd3TotalProductCheckbox').addClass("removeClicks");
            $('.kikkareselector').addClass("removeClicks");
            $('.nmd3ComponentSelect').addClass("removeClicks");
            $('#addNmdConstructionButton').addClass("removeClicks");
            $(".nmd3NoProductsFound").remove();
            $('.' + elementClass).hide();
            $("<tr class='nmd3LoaderRow'><td><i class='fas fa-circle-notch fa-spin oneClickColorScheme' aria-hidden='true'></i></td><td colspan='3'> <span class='loadingDots'>Producten laden.</span></td></tr>").insertAfter(row);
        },
        success: function (data) {
            $(".nmd3LoaderRow").remove();
            $(data.toRender).insertAfter(row);
            $(elem).attr("data-resourceId", data.resourceId);
            $(elem).attr("data-profileId", data.profileId);
            $('.kikkareselector').removeClass("removeClicks");
            $('.nmd3TotalProductCheckbox').removeClass("removeClicks");
            $('.nmd3ComponentSelect').removeClass("removeClicks");
            $('#addNmdConstructionButton').removeClass("removeClicks");
        }
    });
}

function renderLicensesMenu(reloadOnFloatingStatusChange, allowChangeFloatingStatus) {
    var menu = $('#licensesMenu');

    if ($(menu).length && !$(menu).hasClass("alreadyLoading")) {
        $.ajax({
            type: 'POST',
            data: 'reloadOnFloatingStatusChange=' + reloadOnFloatingStatusChange + '&allowChangeFloatingStatus=' + allowChangeFloatingStatus,
            url: '/app/sec/util/getLicenseMenu',
            beforeSend: function () {
                $(menu).addClass("alreadyLoading");
            },
            success: function (data) {
                setTimeout(function () {
                    $(menu).empty().append(data.output);
                }, 500)
            }
        });

    }
}

function waitToStartNewProjectModal(warningTitle, warningText, timeout, modalId, appendToId, entityClass, forPlanetary = false, errorMsg = '') {
    let timerInterval;

    Swal.fire({
        title: warningTitle,
        html: warningText,
        icon: 'warning',
        timer: timeout * 1000,
        timerProgressBar: true,
        allowOutsideClick: false,
        showConfirmButton: false,
        onOpen: function () {
            Swal.showLoading();
            const b = Swal.getHtmlContainer().querySelector('b');
            timerInterval = setInterval(() => {
                b.textContent = (Swal.getTimerLeft() / 1000).toFixed();
            }, 1000);
        },
        onClose: function () {
            clearInterval(timerInterval);
        }
    }).then((result) => {
        if (result.dismiss === Swal.DismissReason.timer) {
            console.log('Warning popup was closed by the timer.');
            startNewProjectModal(modalId, appendToId, entityClass, forPlanetary, errorMsg);
        }
    });
}

function startNewProjectModal(modalId, appendToId, entityClass, forPlanetary = false, errorMsg = '') {
    let $modal = $('#' + modalId)
    // open modal with spinner
    let loader = $('#localizedSpinner'); // loading spinner on main.gsp with localized
    let copy = loader.clone().css('margin-top', ($modal.height() - 270) / 2).show();// clone hidden element and display, also align center vertically (spinner height is currently 270px)
    $('#' + appendToId).empty().append(copy)
    $modal.modal({
        backdrop: 'static',
        keyboard: false
    });

    const json = {
        entityClass : entityClass,
        planetaryEntity: forPlanetary
    }
    ajaxCallForProjectModal('/app/sec/projectTemplate/startNewProjectModal', json, appendToId, errorMsg)
}

function ajaxCallForProjectModal(url, json, appendToId, errorMsg) {
    const successCallback = (data) => {
        if (appendToId) {
            appendSmoothly(appendToId, null, data.output)
        }
    };
    const errorCallback = () => {
        errorSwal('error', 'Oops...', errorMsg)
    };

    ajaxForJsonMain(url, json, successCallback, errorCallback);
}

function ajaxForQueryString(url, queryString, successCallback, errorCallback, isAsync) {
    const async = isAsync !== undefined ? isAsync : true;
    $.ajax({
        type: 'POST',
        data: queryString,
        url: url,
        async: async,
        success: successCallback,
        error: errorCallback
    });
}

function ajaxCallForDesignModal(url, json, element, preventSubmit) {
    if (element) {
        element.style['pointer-events'] = 'none';
    }

    var successCallback = function successCallback(data) {
        $("#modifyModalDiv").empty().append(data.output.template).modal(); //enable pressing of the button/link

        if (element) {
            element.style['pointer-events'] = 'auto';
        }

        if (preventSubmit) {
            validateSubmit();
        }
    };

    var errorCallback = function errorCallback(data) {
        console.log("Error decide what show it");
        console.log(data); //enable pressing of the button/link

        if (element) {
            element.style['pointer-events'] = 'auto';
        }
    };

    ajaxForJsonMain(url, json, successCallback, errorCallback);
}

function ajaxCallForPeriodModal(url, json, element, preventSubmit) {
    if (element) {
        element.style['pointer-events'] = 'none';
    }

    var successCallback = function successCallback(data) {
        $("#modifyPeriodModalDiv").empty().append(data.output.template).modal(); //enable pressing of the button/link

        if (element) {
            element.style['pointer-events'] = 'auto';
        }

        if (preventSubmit) validateSubmit();
    };

    var errorCallback = function errorCallback(data) {
        console.log("Error decide what show it");
        console.log(data); //enable pressing of the button/link

        if (element) {
            element.style['pointer-events'] = 'auto';
        }
    };

    ajaxForJsonMain(url, json, successCallback, errorCallback);
}

function ajaxForJsonMain(url, json, successCallback, errorCallback) {
    var async = arguments.length > 4 && arguments[4] !== undefined ? arguments[4] : true;
    $.ajax({
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify(json),
        contentType: "application/json; charset=utf-8",
        url: url,
        async: async,
        success: successCallback,
        error: errorCallback
    });
}

function ajaxGeneralNoJson(url, json, successCallback, errorCallback, isAsync) {
    var async = isAsync !== undefined ? isAsync : true;
    $.ajax({
        type: 'POST',
        data: JSON.stringify(json),
        contentType: "application/json; charset=utf-8",
        url: url,
        async: async,
        success: successCallback,
        error: errorCallback
    });
}

function checkSubmitButton() {
    var div = $("#modifyModalDiv");

    if (div) {
        var notSubmit = checkModalRequiredValue();

        if (notSubmit) {
            div.find("#save").attr('disabled', 'disabled');
        } else {
            div.find("#save").removeAttr('disabled');
        }
    }
}


function checkModalRequiredValue() {
    var designId = $('#designId').val();
    var newDesign = $('#designId option:selected').text() === "New design";
    var modalDiv = $("#modifyModalDiv");
    var ribaStage;
    var nameDesign;

    if (modalDiv) {
        var ribaStageField = modalDiv.find("#ribaStage");

        if ($(ribaStageField).length) {
            ribaStage = $(ribaStageField).val()
        } else {
            ribaStage = "ok"
        }
        nameDesign = modalDiv.find("#name").val();
    }

    return newDesign && (designId == "newDesign" || designId == "newPeriod") && !(ribaStage && nameDesign);
}

function validateSubmit() {
    var div = $("#modifyModalDiv");

    if (div) {
        div.find("#save").attr('disabled', 'disabled');
        div.find("#ribaStage").on('change', function () {
            return checkSubmitButton();
        });
        div.find("#name").on('keyup', function () {
            return checkSubmitButton();
        });
    }
}

//***************************************************************************************
// CARBON DESIGNER
//****************************************************************************************


function checkConnectionCD(sessionId) {
    var millseconds = 2 * 60 * 1000;
    window.setInterval(function () {
        validateCD(sessionId);
    }, millseconds);
} //Check if in the Carbon Designer the user is connect and Validate the Session


function validateCD(sessionId) {
    var json = {
        sessionId: sessionId
    };
    var url = '/app/sec/util/validateSession';

    var successCallback = function successCallback(data) {
        console.log("Connected ...");
    };

    ajaxForJson(url, json, successCallback, errorServerReply);
}

function ajaxForJson(url, json, successCallback, errorCallback) {
    var async = arguments.length > 4 && arguments[4] !== undefined ? arguments[4] : true;
    $.ajax({
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify(json),
        contentType: "application/json; charset=utf-8",
        url: url,
        async: async,
        success: successCallback,
        error: errorCallback
    });
}

function errorSwal(type, title, body, htmlBody = false) {
    var event = arguments.length > 3 && arguments[3] !== undefined ? arguments[3] : null;

    if (event) {
        console.log(event);
    }
    if (htmlBody) {
        Swal.fire({
            icon: type,
            title: title,
            html: body
        });
    } else {
        Swal.fire({
            icon: type,
            title: title,
            text: body
        });
    }
}


function greyout(sectionId) {
    var div = sectionId ? $("#" + sectionId).parent() : null;

    if (div) {
        div.find(" :input").prop("disabled", true);
        var aList = div.find("a").not(".updateScope");
        aList.addClass("disabled disabled_link");
    }
}

//**********************************************************************
//SCOPE AND LCA CHECKER
//**********************************************************************


function updateProjectScope(entityId, sectionId) {
    var json = {
        entityId: entityId,
        sectionId: sectionId
    };
    var url = '/app/sec/util/updateProjectScope';

    var successCallback = function successCallback(data) {
        location.reload();
    };

    var errorCallback = function errorCallback() {
    };

    ajaxForJson(url, json, successCallback, errorCallback);
}

function showLCACK() {
    var divId = "lcaCheckerNoGraphContainer";
    var div = $('#' + divId);
    div.removeClass("hide");
    $(document).ready(function () {
        document.getElementById("scopeLcaChecker-div").scrollIntoView(true);
    });
}

//********************************************************************
//EVALUATE COMPATIBLE INDICATOR FUNCTIONS
//********************************************************************
//Initialize the IndicatorList in the Design Info Column


function resolveIndicatorConflict(localizedText, checkDivId) {
    if (!checkDivId) {
        checkDivId = 'indicator-list-div'
    }
    var allCheckboxes = allNotHiddenCheckDiv(checkDivId);

    if (allCheckboxes) {
        var checkBox = allCheckboxes.find(function (c) {
            return c.checked;
        });

        if (checkBox) {
            compatibleIndicators(checkBox, localizedText, checkDivId);
        }
    }
}

function setSelectdIndicator(selectedIndicatorId) {
    var allCheckboxes = allNotHiddenCheckDiv("indicator-list-div");

    if (allCheckboxes && selectedIndicatorId) {
        allCheckboxes.forEach(function (c) {
            if ($(c).val() === selectedIndicatorId) {
                c.checked = true;
            } else {
                c.checked = false;
            }
        });
    }
}

function getCompatibleList(compatibleAttr) {
    var compatibles = compatibleAttr;

    if (compatibles) {
        compatibles = compatibles.replace(" ", "");
        if (compatibles) compatibles = compatibles.split(',');
    }

    return compatibles;
} // Resolve Compatible and Incompatible Indicator


function compatibleIndicators(selectedCheckbox, localizedText, customDivId = '') {
    if (!customDivId) {
        customDivId = "indicator-list-div"
    }
    var allCheckboxes = allNotHiddenCheckDiv(customDivId);

    if (!allCheckboxes) {
        return null;
    }

    if (selectedCheckbox && selectedCheckbox.checked) {
        var compatibles = $(selectedCheckbox).attr("data-compatibleIndicatorIds");
        compatibles = getCompatibleList(compatibles);
        if (compatibles && compatibles.length > 0) {
            // if this box has a compatible list, disable all the ones that are not compatible
            allCheckboxes.forEach(function (c) {
                var indicatorId = $(c).val();

                if ($(selectedCheckbox).val() !== indicatorId) {
                    var isCompatible = false;
                    isCompatible = compatibles.some(function (i) {
                        return indicatorId === i;
                    });

                    if (!isCompatible) {
                        disableCheckBoxAndShowPopover(c, localizedText);
                    }
                }
            });
        } else {
            // if the selected box doesn't have compatible list, start checking all other boxes that are still enabled
            const selectedIndicatorId = $(selectedCheckbox).val();
            allCheckboxes.forEach(function (box) {
                if (!$(box).prop('disabled') && $(box).val() !== selectedIndicatorId) {
                    const $enabledBox = $(box)
                    const compatibleListOfEnabledBox = getCompatibleList($enabledBox.attr("data-compatibleIndicatorIds"))
                    let isCompatible = true;
                    // if the enabled box has a compatible list and the selected one is not in it => this box should be disabled
                    if (compatibleListOfEnabledBox.length && !compatibleListOfEnabledBox.includes(selectedIndicatorId)) {
                        isCompatible = false
                    }

                    if (!isCompatible) {
                        disableCheckBoxAndShowPopover($enabledBox, localizedText);
                    }
                }
            });
        }
    } else {
        const checkedBoxList = allCheckboxes.filter(box => box.checked)

        if (checkedBoxList.length > 0) {
            allCheckboxes.forEach(function (box) {
                if ($(box).prop('disabled')) {
                    // start evaluating if this disabled box should be enabled
                    const $disabledBox = $(box)
                    const compatibleListOfDisabledBox = getCompatibleList($disabledBox.attr("data-compatibleIndicatorIds"))
                    let isCompatible = true
                    checkedBoxList.forEach(function(checkedBox) {
                        if (isCompatible) {
                            // if the disabled box has a compatible list and the checked one is not compatible => should stay disabled
                            if (compatibleListOfDisabledBox.length && !compatibleListOfDisabledBox.includes($(checkedBox).val())) {
                                isCompatible = false
                            }
                            if (isCompatible) {
                                const compatibleListOfCheckedBox = getCompatibleList($(checkedBox).attr("data-compatibleIndicatorIds"))
                                // if the checked box has a compatible list and the disabled one is not compatible => should stay disabled
                                if (compatibleListOfCheckedBox.length && !compatibleListOfCheckedBox.includes($disabledBox.val())) {
                                    isCompatible = false
                                }
                            }
                        }
                    })

                    if (isCompatible) {
                        enableCheckBoxAndRemovePopover($disabledBox)
                    }
                }
            })
        } else {
            reEnableCheckbox(allCheckboxes)
        }
    }
}

function reEnableCheckbox(allCheckboxes) {
    if (allCheckboxes) {
        var isCheckedOne = allCheckboxes.find(function (c) {
            return c.checked && c.value !== "xBenchmarkEmbodied";
        });

        allCheckboxes.forEach(function (c) {
            return enableCheckBoxAndRemovePopover(c);
        });

    }
}

function initCdDataIncompatiblePopover() {
    $('.cdDataIncompatiblePopover').popover({
        trigger: 'hover',
        placement: 'top',
        template: '<div class="popover popover-fade" style="display: block; max-width: 300px;"><div class="arrow"></div><div style="font-weight: normal !important;" class="popover-content"></div></div>'
    });
}

function disableCheckBoxAndShowPopover(checkBox, localizedText) {
    var row = $(checkBox).closest('tr');

    if (!localizedText) localizedText = "";
    $(checkBox).prop("disabled", true);
    $(checkBox).prop("checked", false);

    $(row).addClass("disabled");
    $(row).popover('destroy');
    $(row).popover({
        container: 'body',
        trigger: 'hover',
        content: localizedText,
        placement: 'right',
        template: '<div class="indicatorinfopopover popover popover-fade" style=" display: block; max-width: 250px;"><div class="arrow"></div><div class="popover-content"></div></div>'
    });
}

function enableCheckBoxAndRemovePopover(checkBox) {
    $(checkBox).prop("disabled", false);
    var row = $(checkBox).closest('tr').removeClass("disabled");
    $(row).popover('destroy');
} //Return all the not hidden checkbox in a div


function allNotHiddenCheckDiv(divId) {
    //Not hidden check box (fox example xbenchmarck)
    if (divId) {
        var div = document.getElementById(divId);
        var allCheckboxes;
        if (div) allCheckboxes = div.querySelectorAll("input[type=checkbox]:not(.hidden)");
        if (allCheckboxes && allCheckboxes.length > 0) {
            allCheckboxes = Array.from(allCheckboxes);
            return allCheckboxes;
        } else {
            return null;
        }
    }
}

function showHideChildDiv(parentDiv, childTabController, childId = null) {
    let parentDivId = '#' + parentDiv;
    let childDivId
    if (!childId) {
        childDivId = '#' + $(childTabController).attr("name");
    } else {
        childDivId = '#' + childId;
    }
    $(parentDivId).children().hide();
    $(childDivId).show();
}

function showActiveTab() {
    var childDivId = '#' + $('.navInfo.active').attr("name");
    $(childDivId).show();
}

function toggleCollapsibleImpact() {
    document.getElementsByClassName("collapsibleImpact")[0].click();
}

function toggleExpandSection(element, shortcutExpand,showThisButton) {
    var section = $(element).parent();
    $(section).find('.sectioncontrols').fadeToggle(200);
    $(section).find('.sectionbody').fadeToggle(200);
    $(section).toggleClass('collapsed');
    if (shortcutExpand) {
        scrollToElement(element, true);
    }
    if(showThisButton){
        $(showThisButton).toggleClass("hidden")
    }
}

function toggleTable(element) {
    var tableName = $(element).attr("name")
    var toHide = $("#" + tableName)
    var i = $(element).find("i")

    if (toHide.hasClass("hide")) {
        toHide.removeClass("hide")
        $(i).removeClass('fa fa-plus').addClass('fa fa-minus')
        $(element).removeClass('btn-primary').addClass('btn-default')

    } else {
        toHide.addClass("hide")
        $(i).removeClass('fa fa-minus').addClass('fa fa-plus')
        $(element).removeClass('btn-default').addClass('btn-primary')

    }
}

function toggleMultipleElements(controller) {
    $(controller).parent().siblings().removeClass("hidden");
    $(controller).hide()
}

function editUpdatePublish(updateId, modalDiv) {
    var url = "/app/sec/updatePublish/modifyPublishModal"
    var json = {updateId: updateId}

    $.ajax({
        type: "POST",
        data: JSON.stringify(json),
        contentType: "application/json; charset=utf-8",
        url: url,
        success: function (r) {
            $(modalDiv).modal().empty().append(r.output)
        },
        error: function (error) {
            console.log(error)
        }
    });
}

function validateName(element, errorMessage, button, allowEmpty) {
    var regex = /^[a-zA-Z0-9\söÖäÄåÅæÆøØáéíóúýðþßéàèùâêîôûçëïü!?/@#%^&*)(+=.,\-_]+$/g;
    var hasOnlyValidChar = (allowEmpty && !$(element).val()) || regex.test($(element).val());
    var hasAnyCharacter = allowEmpty || ($(element).val() ? $(element).val().trim().length > 0 : false)
    var buttonElement = $('#' + button);
    let nameOk = false

    var output = "";
    if (!hasOnlyValidChar && hasAnyCharacter) {
        var items = $(element).val().split("");
        var invalidChar = []
        $(items).each(function (i) {
            if (!items[i].match(regex)) {
                invalidChar.push(items[i])
            }
        })
        var textAdd = errorMessage + ": " + invalidChar.filter(onlyUnique);

        $(element).addClass('redBorder');
        $(element).next().removeClass("hidden").find("p").text(textAdd);
        if ($(buttonElement).length && !$(buttonElement).hasClass("disabled")) {
            $(buttonElement).addClass('disabled').css("pointer-events", 'none')
        }
    } else if (!hasAnyCharacter) {
        $(element).addClass('redBorder');
        $(element).next().addClass('hidden');
        if ($(buttonElement).length && !$(buttonElement).hasClass("disabled")) {
            $(buttonElement).addClass('disabled').css("pointer-events", 'none')
        }
    } else {
        $(element).removeClass('redBorder');
        $(element).next().addClass('hidden');
        if ($(buttonElement).length) {
            $(buttonElement).removeClass('disabled').css("pointer-events", 'auto')
        }
        nameOk = true
    }

    function onlyUnique(value, index, self) {
        return self.indexOf(value) === index;
    }
    return nameOk
}

function toggleWarningText(elementId, showText) {
    $('#' + elementId).addClass("hidden");
}

function renderResultGraphs(entityId, indicatorId,calculationRuleId, selectedClassification,defaultFallbackClassification) {
    var chartsToRender = $("#chartsToRender").val()
    var spinningArrow = $("#loadingSpinner_overview");
    var allChartsWrapper = $("#allChartsWrapper");
    var bubbleChartContainer = $("#bubbleChartTypeSubtype");
    var sankeyChartContainer = $("#sankeyWrapper");
    var overViewChartContainer = $("#overviewPieChart");
    var annualChartContainer = $("#annualAccumulationContainer");
    var breakDownChartContainer = $("#allBreakDowns");
    var circularChartContainer = $("#circularChartWrapper");
    var lifeCycleStageChart = $("#lifeCycleStageChart");
    var treeMapChart = $("#treeMapWrapper");
    var radialByDesignByElement = $("#radialByDesignByElementWrapper")
    var epdStackedChart = $("#chartByImpactCategoryStackedWrapper")
    var epdStackedChart2 = $("#chartByMatAndImpactWrapper")
    var btnGroupResultClassList = $("#btnGroupResultClassList")
    var jSon = {
        entityId: entityId,
        indicatorId: indicatorId,
        calculationRuleId: calculationRuleId,
        selectedClassification: selectedClassification,
        defaultFallbackClassification:defaultFallbackClassification,
        chartsToRender:chartsToRender,
    }

    $(spinningArrow).show()
    $(allChartsWrapper).hide()

    if (entityId && indicatorId) {
        $.ajax({
            type: "POST",
            data: JSON.stringify(jSon),
            contentType: "application/json; charset=utf-8",
            url: '/app/sec/entity/renderResultGraphs',
            success: function (data) {
                $(spinningArrow).hide()
                $(allChartsWrapper).show()
                if (data.output.overviewChart) {
                    $(overViewChartContainer).empty().append(data.output.overviewChart)
                }
                if (data.output.bubbleChart) {
                    $(bubbleChartContainer).empty().append(data.output.bubbleChart)
                }
                if (data.output.sankeyChart) {
                    $(sankeyChartContainer).empty().append(data.output.sankeyChart)
                }
                if (data.output.annualChart) {
                    $(annualChartContainer).empty().append(data.output.annualChart)
                }
                if (data.output.lifeCycleBreakdownChart) {
                    $(breakDownChartContainer).empty().append(data.output.lifeCycleBreakdownChart)
                    $("#showAllBreakDowns").show()
                }
                if (data.output.circularChart) {
                    $(circularChartContainer).empty().append(data.output.circularChart)
                }
                if (data.output.lifeCycleChart) {
                    $(lifeCycleStageChart).empty().append(data.output.lifeCycleChart)
                }
                if (data.output.treeMapChart) {
                    $(treeMapChart).empty().append(data.output.treeMapChart)
                }
                if (data.output.chartRadialByElementByDesign) {
                    $(radialByDesignByElement).empty().append(data.output.chartRadialByElementByDesign)
                }
                if (data.output.chartByImpactCategoryStacked) {
                    $(epdStackedChart).empty().append(data.output.chartByImpactCategoryStacked)
                }
                if (data.output.chartByMatAndImpactStacked) {
                    $(epdStackedChart2).empty().append(data.output.chartByMatAndImpactStacked)
                }
                if(data.output.btnGroupResultClassList){
                    $(btnGroupResultClassList).empty().append(data.output.btnGroupResultClassList)
                }
            },
            error: function (error) {
                console.log(error)
            }

        })
    }
}

function renderQueryResultGraphs(indicatorId, entityId, calculationRuleId, onPageLoad) {
    if(document.getElementById('myProgressNav').className.includes('hiddenSideBar') && onPageLoad) {
        //hidden on page load
        return
    }
    if(document.getElementById('controller')) {
        //already rendered
        return
    }
    var jSon = {
        entityId: entityId,
        indicatorId: indicatorId,
        calculationRuleId: calculationRuleId,
        querySideGraph: true
    }
    if (entityId && indicatorId) {
        $.ajax({
            type: "POST",
            data: JSON.stringify(jSon),
            contentType: "application/json; charset=utf-8",
            url: '/app/sec/entity/renderResultGraphs',
            success: function (data) {
                if (data.output.querySideGraph) {
                    $("#sideChartWrapper").empty().append(data.output.querySideGraph)
                }
            },
            error: function (error) {
                console.log(error)
            }
        })
    }
}

function renderSourceListing(indicatorId, parentEntityId, entityId, countryNameEN, projectCountry, showDiv) {

    if (!entityId || !indicatorId) {
        return;
    }

    let data = {
        indicatorId: indicatorId,
        parentEntityId: parentEntityId,
        entityId: entityId,
        countryNameEN: countryNameEN,
        projectCountry: projectCountry,
        showDiv:showDiv
    };

    $.ajax({
        type: "POST",
        data: JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        url: '/app/sec/entity/renderSourceListing',
        success: function (data) {
            $("#dataSourcesContainer").html(data.output);
        },
        error: function (error) {
            console.log(error);
            var response = jQuery.parseJSON(error.responseText);
            $("#dataSourcesContainer").html(response.output);
        }
    });
}

function renderResourceTableWithBenchmark(subtypeId, value, benchmarkToShow, unit, resourceIdList, area, containerId, element) {
    var jSon = {
        subtypeId: subtypeId,
        value: value,
        benchmarkToShow: benchmarkToShow,
        unit: unit,
        area: area,
        resourceIdList: resourceIdList
    }
    if (element) {
        $("#benchmarkTableOverview").find("td").removeClass("highlighted-background")
        $(element).parent().addClass("highlighted-background")
    }
    if (subtypeId && benchmarkToShow && containerId) {

        $("#loadingSpinnerBenchmark").removeClass("hidden")
        $.ajax({
            type: "POST",
            data: JSON.stringify(jSon),
            contentType: "application/json; charset=utf-8",
            url: '/app/sec/resourceType/filterResourcesBySubTypeAndBenchmark',
            success: function (data) {
                if (data.output) {
                    $("#loadingSpinnerBenchmark").addClass("hidden")
                    $("#" + containerId).empty().append(data.output)
                }
            },
            error: function (error) {
                console.log(error)
            }
        })
    }
}

function toggleFavoriteMaterial(resourceId, element, userId) {

    if (resourceId && userId) {
        var url = '/app/sec/user/toggleFavoriteMaterial'
        var icon = $(element).find("i")
        $(icon).toggleClass("far fas yellowScheme")
        $.ajax({
            type: "POST",
            data: 'resourceId=' + resourceId + '&userId=' + userId,
            url: url,
            success: function (data, textStatus) {
                console.log("Success")
                console.log(data)

            },
            error: function (error, textStatus) {
                $(icon).toggleClass("far fas yellowScheme")
                console.log(error + ": " + textStatus)
            }

        })
    }
}

function openGroupingContainer(entityId, indicatorId, queryId, sectionId, questionId, manualIds, existingParentConstruction, createConstructionsAllowed,publicGroupAllowed) {
    closeAllGroupingContainers();
    $('.groupingButtonContainer').addClass('removeClicks');
    reinitDragginContainerForGrouping(entityId, indicatorId, queryId, sectionId, questionId);
    var spinnerContainer = $('#' + questionId + 'RowGroupingSpinnerContainer');
    $(spinnerContainer).addClass("groupingSpinningContainer");
    appendLoader(questionId + 'RowGroupingSpinnerContainer');
    var groupingContainer = $('#' + questionId + 'RowGroupingContainer');
    $(groupingContainer).show().addClass("display-flex");
    scrollToView(spinnerContainer)

    var tableHead = $('#' + questionId + 'RowGroupingContainerTableHead');
    var tableBody = $('#' + questionId + 'RowGroupingContainerTableBody');
    var constructionTable = $('#' + questionId + 'RowGroupingConstructionTable');
    var containerTable = $('#' + questionId + 'RowGroupingContainerTable');
    var tableContainer = $('#' + questionId + 'RowGroupingTableContainer');
    var existingParentConstructionDatasetId = ""
    $(tableContainer).css('width', '');
    $(constructionTable).hide();
    $(containerTable).hide();
    $(tableHead).empty();

    if (manualIds) {
        $(tableBody).empty();
    }
    if(existingParentConstruction){
        existingParentConstructionDatasetId = $(existingParentConstruction).attr("data-manualidformoving");
    }

    var json = {
        entityId: entityId,
        indicatorId: indicatorId,
        queryId: queryId,
        sectionId: sectionId,
        questionId: questionId,
        manualIds: manualIds,
        createConstructionsAllowed:createConstructionsAllowed,
        existingParentConstructionDatasetId:existingParentConstructionDatasetId,
        publicGroupAllowed:publicGroupAllowed
    };

    const groupPopOverSettings = {
        placement: 'top',
        container: 'body',
        html: true,
        template: '<div class="popover popover-fade" style=" display: block; max-width: 300px;"><div class="arrow"></div><div class="popover-content"></div></div>'
    };
    if (formChanged) {
        var frm = $('#queryForm');
        $.ajax({
            url: '/app/sec/query/saveQueryFormAjax',
            type: 'POST',
            data: new FormData($(frm).get(0)),
            processData: false,
            contentType: false,
            success: function (data) {
                formChanged = false;
                if (data) {
                    $.ajax({
                        type: "POST",
                        data: JSON.stringify(json),
                        contentType: "application/json; charset=utf-8",
                        url: '/app/sec/util/renderRowGroupingView',
                        success: function (data, textStatus) {
                            if (data) {
                                $(spinnerContainer).empty().removeClass("groupingSpinningContainer");

                                $(containerTable).show();
                                $(tableContainer).css("width", "100%");
                                $(tableHead).empty().append(data.tableHeading);
                                $(constructionTable).find("#"+questionId+"defaultConstructionRow").show()
                                if(data.tableConstructionRow){
                                    $(constructionTable).find("#"+questionId+"defaultConstructionRow").hide()
                                    // remove all table heads except for the default one in case grouping container is opened repeatedly
                                    $(constructionTable).children().not("#" + questionId + "defaultConstructionRow").remove()
                                    $(constructionTable).append(data.tableConstructionRow)
                                }
                                $(constructionTable).show();
                                if (manualIds) {
                                    $(tableBody).empty().append(data.tableData);
                                    if (!$(tableBody).hasClass('dragNDropTbody')) {
                                        $(tableBody).addClass('dragNDropTbody')
                                    }

                                    $.each(manualIds, function (asd, manualId) {
                                        $("[data-manualidformoving='" + manualId + "']").addClass('inGrouping')
                                    });
                                }
                                triggerPopoverWithSettings(groupingContainer, groupPopOverSettings)
                                numericInputCheck()
                            }
                        },
                        error: function (error, textStatus) {

                        }
                    })
                }
            }
        });
    } else {
        $.ajax({
            type: "POST",
            data: JSON.stringify(json),
            contentType: "application/json; charset=utf-8",
            url: '/app/sec/util/renderRowGroupingView',
            success: function (data, textStatus) {
                if (data) {
                    $(spinnerContainer).empty().removeClass("groupingSpinningContainer");
                    $(constructionTable).find("#"+questionId+"defaultConstructionRow").show()
                    if(data.tableConstructionRow){
                        $(constructionTable).find("#"+questionId+"defaultConstructionRow").hide()
                        // remove all table heads except for the default one in case grouping container is opened repeatedly
                        $(constructionTable).children().not("#" + questionId + "defaultConstructionRow").remove()
                        $(constructionTable).append(data.tableConstructionRow)
                    }
                    $(constructionTable).show();
                    $(containerTable).show();
                    $(tableContainer).css("width", "100%");
                    $(tableHead).empty().append(data.tableHeading);

                    if (manualIds) {
                        $(tableBody).empty().append(data.tableData);
                        if (!$(tableBody).hasClass('dragNDropTbody')) {
                            $(tableBody).addClass('dragNDropTbody')
                        }
                        $.each(manualIds, function (asd, manualId) {
                            $("[data-manualidformoving='" + manualId + "']").addClass('inGrouping')
                        });
                    }
                    triggerPopoverWithSettings(groupingContainer, groupPopOverSettings)
                    numericInputCheck()
                }
            },
            error: function (error, textStatus) {

            }
        })
    }
}

function closeAllGroupingContainers() {
    $.each($(".rowGroupingContainer"), function () {
        closeGroupingContainer($(this).attr('id'));
    });
}

function closeGroupingContainer(elemId,idForTempHead,defaultHeader,constructionTableBody) {
    var container = $('#' + elemId);
    if(idForTempHead){
        $("#"+idForTempHead).remove();
    }
    if(defaultHeader){
        $("#"+defaultHeader).show();
    }

    if ($(container).length && $(container).is(":visible")) {
        $(container).hide();
        var rows = $(container).find('.highlightHover');

        $.each(rows, function (asd, row) {
            $("[data-manualidformoving='" + $(row).attr('data-manualId') + "']").removeClass('inGrouping')
        });

    }
    // this code block need to be after the previous loop to remove class inGrouping
    if (constructionTableBody) {
        const tbody = $("#" + constructionTableBody)
        $(tbody).empty()
        $(tbody).append("<tr class=\"hiddenDummyRow\"><td colspan=\"99\" class=\"hiddenDummyTd\"><div class=\"dragNDrop\">Drag and drop desired materials</div></td></tr>");
        $(tbody).removeClass('dragNDropTbody');
    }
    $('.groupingButtonContainer').removeClass('removeClicks');

}

function renderGroupViewRow(el, container, entityId, indicatorId, queryId, sectionId, questionId) {
    var manualId = $(el).attr('data-manualidformoving');
    var i = $(el).index();
    $(el).remove();

    if (formChanged) {
        var spinnerContainer = $('#' + questionId + 'RowGroupingSpinnerContainer');
        $(spinnerContainer).addClass("groupingSpinningContainer");
        appendLoader(questionId + 'RowGroupingSpinnerContainer');
        var groupingContainer = $('#' + questionId + 'RowGroupingContainer');
        $(groupingContainer).show().addClass("display-flex");
        var tableHead = $('#' + questionId + 'RowGroupingContainerTableHead');
        var tableBody = $('#' + questionId + 'RowGroupingContainerTableBody');
        var constructionTable = $('#' + questionId + 'RowGroupingConstructionTable');
        var containerTable = $('#' + questionId + 'RowGroupingContainerTable');
        var tableContainer = $('#' + questionId + 'RowGroupingTableContainer');
        $(tableContainer).css('width', '');
        $(constructionTable).hide();
        $(containerTable).hide();
        $(tableHead).hide();
        $(tableBody).hide();
    }

    var rows = $('#' + questionId + 'RowGroupingContainerTable > tbody > tr').not('.hiddenDummyRow');

    var manualIds = [];

    $.each(rows, function (idx, row) {
        if ($(row).attr('data-manualidformoving')) {
            manualIds.push($(row).attr('data-manualidformoving'));
        }
    });

    var json = {
        entityId: entityId,
        indicatorId: indicatorId,
        queryId: queryId,
        sectionId: sectionId,
        questionId: questionId,
        manualId: manualId,
        alreadyRenderedDatasets: manualIds
    };

    if (formChanged) {
        var frm = $('#queryForm');
        $.ajax({
            url: '/app/sec/query/saveQueryFormAjax',
            type: 'POST',
            data: new FormData($(frm).get(0)),
            processData: false,
            contentType: false,
            success: function (data) {
                formChanged = false;
                if (data) {
                    $.ajax({
                        type: "POST",
                        url: '/app/sec/util/renderRowGroupingRow',
                        data: JSON.stringify(json),
                        contentType: "application/json; charset=utf-8",
                        success: function (data) {
                            if (data) {
                                $(spinnerContainer).empty().removeClass("groupingSpinningContainer");
                                $(tableContainer).css('width', '100%');
                                $(constructionTable).show();
                                $(containerTable).show();
                                $(tableHead).show();
                                $(tableBody).show();

                                if (data.error) {
                                    var errorContainer = $('#' + questionId + 'GroupingError');
                                    errorContainer.empty();
                                    $("<div id='" + questionId + "dragNdropError' class='alert alert-error'>\n" +
                                        "  <button data-dismiss='alert' class='close' type='button'>×</button>\n" +
                                        "  <strong>" + data.error + "</strong>\n" +
                                        "</div>").prependTo(errorContainer);
                                } else {
                                    $(container).find('.hiddenDummyRow').remove();
                                    if (!$(container).hasClass('dragNDropTbody')) {
                                        $(container).addClass('dragNDropTbody')
                                    }
                                    if ($(rows).length) {
                                        var x = i - 1;
                                        if (x < 0) {
                                            $(rows).eq(0).before(data.tableData);
                                        } else {
                                            $(rows).eq(i - 1).after(data.tableData);
                                        }
                                    } else {
                                        $('#' + questionId + 'RowGroupingContainerTableBody').append(data.tableData);
                                    }
                                    $("[data-manualidformoving='" + manualId + "']").addClass('inGrouping');
                                    numericInputCheck()
                                }
                            }
                        }
                    });
                }
            }
        });
    } else {
        $.ajax({
            type: "POST",
            url: '/app/sec/util/renderRowGroupingRow',
            data: JSON.stringify(json),
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                if (data) {
                    if (data.error) {
                        var errorContainer = $('#' + questionId + 'GroupingError');
                        errorContainer.empty();
                        $("<div id='" + questionId + "dragNdropError' class='alert alert-error'>\n" +
                            "  <button data-dismiss='alert' class='close' type='button'>×</button>\n" +
                            "  <strong>" + data.error + "</strong>\n" +
                            "</div>").prependTo(errorContainer);
                    } else {
                        $(container).find('.hiddenDummyRow').remove();
                        if (!$(container).hasClass('dragNDropTbody')) {
                            $(container).addClass('dragNDropTbody')
                        }
                        if ($(rows).length) {
                            var x = i - 1;
                            if (x < 0) {
                                $(rows).eq(0).before(data.tableData);
                            } else {
                                $(rows).eq(i - 1).after(data.tableData);
                            }
                        } else {
                            $('#' + questionId + 'RowGroupingContainerTableBody').append(data.tableData);
                        }
                        $("[data-manualidformoving='" + manualId + "']").addClass('inGrouping');
                        numericInputCheck()
                    }
                }
            }
        });
    }
}

/**
 * Validate the group before creating construction
 * @param indicatorId
 * @param questionId
 * @param sectionId
 * @param queryId
 * @param entityId
 * @param button
 * @param localizedOk
 * @param localizedCancel
 * @param existingParentConstructionDatasetId
 */
function validateGroup(indicatorId, questionId, sectionId, queryId, entityId, button, localizedOk, localizedCancel,existingParentConstructionDatasetId) {
    var regex = /^[a-zA-Z0-9\söÖäÄåÅæÆøØ!?/@#%^&*)(+=.,\-_]+$/g;
    var regex2 = /^[a-zA-Z0-9\söÖäÄåÅæÆøØ!?/@#%^&*)(+=.,\-_]+$/g; // javascript regex works weird if tested on multiple?
    var valid = true;

    var constructionNameInput = $('*[data-target="'+ questionId + 'AssemblyName"]:visible');
    var constructionQuantityInput = $('*[data-target="' + questionId + 'AssemblyQuantity"]:visible');
    var constructionUnitInput = $('*[data-target="' + questionId + 'AssemblyUnit"]:visible');
    var constructionCommentInput = $('*[data-target="' + questionId + 'AssemblyComment"]:visible');

    var constructionName = $(constructionNameInput).val();
    var constructionQuantity = $(constructionQuantityInput).val();
    var constructionUnit = $(constructionUnitInput).val();
    var constructionComment = $(constructionCommentInput).val();


    if (!(constructionName && constructionName.length && regex.test(constructionName))) {
        valid = false;
        $(constructionNameInput).addClass('redBorder');
    } else {
        $(constructionNameInput).removeClass('redBorder');
    }

    if (constructionComment) {
        if (regex2.test(constructionComment)) {
            $(constructionCommentInput).removeClass('redBorder');
        } else {
            valid = false;
            $(constructionCommentInput).addClass('redBorder');
        }
    }

    if (!(constructionQuantity && constructionQuantity.length && !isNaN(parseFloat(constructionQuantity.replace(/,/g, '.'))))) {
        valid = false;
        $(constructionQuantityInput).addClass('redBorder');
    } else {
        $(constructionQuantityInput).removeClass('redBorder');
    }

    if (!(constructionUnit && constructionUnit.length)) {
        valid = false;
        $(constructionUnitInput).addClass('redBorder');
    } else {
        $(constructionUnitInput).removeClass('redBorder');
    }

    var errorContainer = $('#' + questionId + 'GroupingError');
    errorContainer.empty();

    var rows = $('#'  + questionId + 'RowGroupingContainerTableBody > tr:visible');
    var manualIds = [];

    $.each(rows, function (idx, row) {
        if ($(row).attr('data-manualId')) {
            manualIds.push($(row).attr('data-manualId'));
        }
    });

    if (valid && manualIds.length > 0) {
        var creationMethodElem = $('*[data-target="'  + questionId + 'GroupCreationMethod"]:visible');
        var subType = $('*[data-target="'  + questionId + 'GroupSubType"]:visible').val();
        var publicConstruction, accountId = null;
        if ($(creationMethodElem).length) {
            var option = $(creationMethodElem).children("option:selected");
            var creationMethod = $(option).val();

            if ("private" === creationMethod) {
                accountId = $(option).attr("data-accountId");
            } else if ("public" === creationMethod) {
                publicConstruction = true;
                accountId = $(option).attr("data-accountId");
            }
        }


        var showInMappingQuestionId = $('*[data-target="' + questionId + 'ShowInMappingQuestionId"]').val();
        var showInMappingQuestionAnswer = $('*[data-target="' + questionId + 'ShowInMappingQuestion"]:visible').val();

        var privateClassificationQuestionId = $('*[data-target="' + questionId + 'PrivateClassificationQuestionId"]:visible').val();
        var privateClassificationQuestionAnswer = $('*[data-target="' + questionId + 'PrivateClassificationQuestion"]:visible').val();

        var brandImageId = $('*[data-target="brandImageId"]:visible').val();
        var productDescription = $('*[data-target="constructionDescription"]:visible').val();

        var json = {
            entityId: entityId,
            manualIds: manualIds,
            queryId: queryId,
            sectionId: sectionId,
            questionId: questionId,
            constructionName: constructionName,
            constructionQuantity: constructionQuantity,
            constructionUnit: constructionUnit,
            constructionComment: constructionComment,
            indicatorId: indicatorId,
            accountId: accountId,
            publicConstruction: publicConstruction,
            subType: subType,
            showInMappingQuestionId: showInMappingQuestionId,
            showInMappingQuestionAnswer: showInMappingQuestionAnswer,
            privateClassificationQuestionId: privateClassificationQuestionId,
            privateClassificationQuestionAnswer: privateClassificationQuestionAnswer,
            brandImageId: brandImageId,
            existingParentConstructionDatasetId:existingParentConstructionDatasetId,
            productDescription: productDescription
        };

        if (accountId || publicConstruction) {
            var warningTitle, warningText = null;
            if (publicConstruction) {
                warningTitle = "Public construction";
                warningText = $('#' + questionId + 'LocalizedPublicWarning').val();
            } else {
                warningTitle = "Private construction";
                warningText = $('#' + questionId + 'LocalizedPrivateWarning').val();
            }

            Swal.fire({
                title: warningTitle,
                icon: "warning",
                text: warningText,
                confirmButtonText: localizedOk,
                confirmButtonColor: '#8DC73F',
                cancelButtonText: localizedCancel,
                cancelButtonColor: '#CDCDCD',
                showCancelButton: true,
                allowOutsideClick: false,
                showLoaderOnConfirm: true,
                reverseButtons: true
            }).then(function (result) {
                if(result.isConfirmed) {
                    submitCreateConstruction(button, json, questionId, true)
                } else if(result.isDisMissed) {
                    return false
                }

            });
        } else {
            submitCreateConstruction(button, json, questionId, true)
        }
    } else {
        var errorMsg;

        if (manualIds.length <= 0) {
            errorMsg = "Add at least one material into the group. Group could not be created.";
        } else {
            errorMsg = "Missing or invalid mandatory parameters. Group could not be created.";
        }
        $("<div id='" + questionId + "dragNdropError' class='alert alert-error'>\n" +
            "  <button data-dismiss='alert' class='close' style=\"top: 0px;\" type='button'>×</button>\n" +
            "  <strong>" + errorMsg + "</strong>\n" +
            "</div>").prependTo(errorContainer);
    }
}

/**
 * When a new / edited construction is submitted, if user has modified some constituent quantity / unit or whatever,
 * we need to save the query before creating the construction, since the createConstructionFromParams() action
 * combine the constituent datasets of design into the construction.
 * @param button
 * @param json
 * @param questionId
 */
function submitCreateConstruction(button, json, questionId, skipCalculation) {
    myFormSubmitted = true;
    $("#breadcrumbForQuery > a").addClass("just_black").removeAttr('href');
    clickAndDisable('queryNavButton', false, true);
    clickAndDisable('brand', false, true);
    clickAndDisable('preventDoubleSubmit', true, true);
    $('<i style="margin-right: 5px;" class="fas fa-circle-notch fa-spin"></i>').prependTo(button);
    $(button).addClass('disabledSaveOnLoad');

    if (formChanged) {
        const queryForm = $('#queryForm');
        disableInputsInGroupingRowBody(button)
        if (skipCalculation) {
            queryForm.append('<input type=\"hidden\" name=\"skipCalculation\" value=\"' + skipCalculation + '\" />');
        }
        $.ajax({
            url: '/app/sec/query/saveQueryFormAjax',
            type: 'POST',
            data: new FormData($(queryForm).get(0)),
            processData: false,
            contentType: false,
            success: function (data) {
                formChanged = false;
                createConstructionAjax(json, questionId)
            }
        })
    } else {
        createConstructionAjax(json, questionId)
    }
}

/**
 * Disable all the inputs in grouping container, since these inputs are duplicates of the resource rows (constituents)
 * and we do not send these inputs to backend.
 * The value of these inputs have been updated to the corresponding input in resource row on change. {@see #updateParallelInputInQuestion}
 * @param button
 */
function disableInputsInGroupingRowBody(button) {
    $(button).closest('.rowGroupingContainer').find('.dragNDropTbody').find('input.inGroupingRow, select.inGroupingRow').prop('disabled', 'disabled')
}

/**
 * Send ajax to create construction
 * @param json
 * @param questionId
 */
function createConstructionAjax(json, questionId) {
    $.ajax({
        type: "POST",
        url: '/app/sec/util/createConstructionFromParams',
        data: JSON.stringify(json),
        contentType: "application/json; charset=utf-8",
        success: function (data) {
            closeGroupingContainer(questionId + 'RowGroupingContainer', questionId + 'TempHead', questionId + 'defaultConstructionRow', questionId + 'RowGroupingContainerTableBody')
            window.onbeforeunload = null;
            location.reload();
        }
    });
}

function toggleActiveTab(element, showAll) {
    var tab = $(element).parent()
    $(tab).addClass('active')
    $(tab).siblings().removeClass('active')
    if (showAll) {
        $(".mainGraphContainer > .chartContainer").removeClass("hidden")
    } else {
        var chartId = "#" + $(element).attr("id") + "_container"
        $(chartId).siblings().addClass("hidden");
        $(chartId).removeClass("hidden");
    }
}

function renderMainEntityCharts(entityId, indicatorId, calculationRuleId, indicatorIdList, selectedClassification , event) {
    if(entityId && indicatorId && indicatorIdList){
        var url = '/app/sec/entity/renderMainEntityGraphs'
        var jSon = {
            entityId: entityId,
            indicatorId: indicatorId,
            calculationRuleId: calculationRuleId,
            indicatorIdList: indicatorIdList,
            selectedClassification : selectedClassification
        }
        var loadingSpinner = $("#loadingSpinner_overview")
        var totalChart = $("#totalChart");
        var stageClassChart = $("#stageClassChart");
        var classGroupChart = $("#classGroupChart");
        var stageChart = $("#stageChart");
        var elementChart = $("#elementChart");
        var btnGroup = $("#designsNameContainer");
        var allChartsWrapper = $("#allChartsWrapper");
        var indicatorNameWrap = $("#indicatorNameWrap");
        var classificationList = $("#classificationListContainer");
        $(loadingSpinner).show()
        $(allChartsWrapper).hide()
        var popOverSetting = {
            placement: 'right',
            container: 'body',
            html: true,
            template: '<div class="popover popover-fade" style=" display: block; max-width: 180px;"><div class="arrow"></div><div class="popover-content"></div></div>'
        }

        $.ajax({
            type: "POST",
            data: JSON.stringify(jSon),
            contentType: "application/json; charset=utf-8",
            url: url,
            success: function (data, textStatus, xhr) {
                if (data) {
                    $(loadingSpinner).hide()
                    $(allChartsWrapper).show()
                    if(data.output.btnGroup){
                        $(btnGroup).empty().append(data.output.btnGroup)
                    }
                    if(data.output.classificationList){
                        $(classificationList).empty().append(data.output.classificationList)
                    }
                    if (data.output.totalChart) {
                        $(totalChart).empty().append(data.output.totalChart)
                    }
                    if (data.output.stageClassChart) {
                        $(stageClassChart).empty().append(data.output.stageClassChart)
                    }
                    if (data.output.stageChart) {
                        $(stageChart).empty().append(data.output.stageChart)
                    }
                    if (data.output.classGroupChart) {
                        $(classGroupChart).empty().append(data.output.classGroupChart)
                    }
                    if (data.output.elementChart) {
                        $(elementChart).empty().append(data.output.elementChart)
                    }
                    if (data.output.chartMainTitle) {
                        $(indicatorNameWrap).empty().text(data.output.chartShortTitle)
                        $(indicatorNameWrap).attr("data-content", data.output.chartMainTitle).popover({content: data.output.chartMainTitle})
                    }
                    if (data.output.isOperatingPeriod) {
                        $(".forOperating").removeClass("hidden");
                        $(".forDesign").addClass("hidden");
                    } else {
                        $(".forOperating").addClass("hidden");
                        $(".forDesign").removeClass("hidden");
                    }
                    $(".legend_info").popover(popOverSetting)
                }

            },
            error: function (error) {
                console.log(error)
            }

        })
        $("#selectToolAndRule").removeClass("open");
        stopBubblePropagation(event)
    }
}

var dataForChartByDesign = new Map()
var maxValueForCategory
var mapByRuleByDesign
var chartIdList = ["groupMainComparisonGraph", "stageMainComparisonGraph", "elemCompChartContainer", "mainEntClassGroupGraph", "totalResultChart"]

function toggleDesign(event, title, content) {
    var charts = Highcharts.charts
    var designElement = []
    $.each($("input[name='designName']:checked"), function () {
        designElement.push($(this).attr("id"))
    });
    var noDesigns = $("input[name='designName'][type='checkbox']").length
    $("#designNumbers").text(designElement.length + " / " + noDesigns)
    if (designElement.length > 10) {
        swal.fire({
            title: title,
            text: content,
            showCancelButton: false,
            showConfirmButton: false,
            showLoaderOnConfirm: true,
            timer: 500,
            onOpen: function () {
                return new Promise(function (resolve) {
                    swal.showLoading()
                    resolve()
                })
            },
        }).then(function (resolve) {
            swal.fire({
                title: "loading data",
                preConfirm: new Promise(function (resolve) {
                    charts.forEach(function (chartItem) {
                        if (chartIdList.indexOf(chartItem.renderTo.id) != -1) {
                            toggleDesignToChart(chartItem, designElement, event)
                        }
                    });
                    resolve()
                }),
                allowOutsideClick: false
            })
        }).then(function (result) {
            swal.close()
        });
    } else {
        charts.forEach(function (chartItem) {
            if (chartIdList.indexOf(chartItem.renderTo.id) != -1) {
                toggleDesignToChart(chartItem, designElement, event)
            }
        });
    }


}

function toggleDesignToChart(chartItem, designElement, event) {
    var chartId = chartItem.renderTo.id;
    var chartFullDataset = dataForChartByDesign.get(chartId)

    if (chartId == "elemCompChartContainer") {
        chartItem.xAxis[0].setCategories(designElement);
        var classElement = []
        $.each($("input[name='classificationNames']:checked"), function () {
            classElement.push($(this).attr("id"))
        });
        for (var category in chartFullDataset) {
            var valueMap = chartFullDataset[category]
            if (valueMap) {
                var newDataset = []
                for (var design in valueMap) {
                    if (designElement.indexOf(design) != -1) {
                        var valueSubMap = valueMap[design]
                        var result = 0
                        for (var clas in valueSubMap) {
                            if (classElement.indexOf(clas) != -1) {
                                result += valueSubMap[clas]
                            }
                        }
                        newDataset.push(result)
                    }
                }
                if (chartItem.series.find(it => it.name == category)) {
                    chartItem.series.find(it => it.name == category).setData(newDataset)
                }
            }
        }
    } else if (chartId == "mainEntClassGroupGraph") {
        var legendItem = []
        chartItem.series.forEach(function (set) {
            if (designElement.indexOf(set.name) != -1) {
                set.setVisible(true)
            } else {
                set.setVisible(false)
            }
        })
    } else if (chartId == "totalResultChart") {
        calibrateMaxValuePerRule(chartFullDataset, chartItem, designElement)
    } else {
        chartItem.xAxis[0].setCategories(designElement);
        var legendItem = []
        chartItem.series.forEach(function (set) {
            legendItem.push(set.name)
        })
        legendItem.forEach(function (item) {
            var newDataset = []
            for (design in chartFullDataset) {
                if (designElement.indexOf(design) != -1) {
                    var valueMap = chartFullDataset[design]
                    newDataset.push(valueMap[item])
                }
            }
            if (chartItem.series.find(it => it.name == item)) {
                chartItem.series.find(it => it.name == item).setData(newDataset)
            }
        })
    }
    if (event) {
        event.stopPropagation()
    }

}

function returnPercentageFromList(chartFullDataset, chartItem, designElement) {
    designElement.forEach(function (designItem) {
        var map = chartFullDataset[designItem]
        var newDataset = []
        for (var subMap in map) {
            var percentage = map[subMap] / maxValueForCategory.get(subMap) * 100
            newDataset.push(percentage)
        }
        if (chartItem.series.find(it => it.name == designItem)) {
            chartItem.series.find(it => it.name == designItem).setData(newDataset)
        }
    })
    chartItem.series.forEach(function (set) {
        if (designElement.indexOf(set.name) != -1) {
            set.setVisible(true)
        } else {
            set.setVisible(false)
        }
    })


}

function calibrateMaxValuePerRule(chartFullDataset, chartItem, designElement) {
    var newMaxResult = new Map()
    for (var rule in mapByRuleByDesign) {
        var resultDesign = mapByRuleByDesign[rule]
        var highestScoreForRule = 0
        for (var design in resultDesign) {
            if (designElement.indexOf(design) != -1 && resultDesign[design] > highestScoreForRule) {
                highestScoreForRule = resultDesign[design]
            }
        }
        newMaxResult.set(rule, highestScoreForRule)
    }
    maxValueForCategory = newMaxResult
    returnPercentageFromList(chartFullDataset, chartItem, designElement)
}

var intro;
var toggledFunctionToNext = false;
var workflowFinish = true;
var timer;
var timeOut = 2500;
var materialAdded = false;

function initializeOnboardingWorkflow(workflowObject, closeText, nextText) {
    var optionText = []
    intro = introJs('html');
    var i = 0
    var x = 0
    while (i < workflowObject.stepList.length) {
        var step = workflowObject.stepList[i]
        i++
        if (!step.checked) {
            workflowFinish = false
            if (!(x == 0 && step.toNextScreen)) {
                var highlightedElement = null;
                if (step.itemId != null && step.itemId.indexOf("#") != -1) {
                    if ($(step.itemId).length > 0) {
                        highlightedElement = step.itemId
                    }
                } else {
                    if (document.querySelector(step.itemId) && document.querySelector(step.itemId).length > 0) {
                        highlightedElement = document.querySelectorAll(step.itemId)[0]
                    }
                }
                if (highlightedElement || step.itemId == null) {
                    var obj = {
                        element: highlightedElement,
                        intro: step.localizedHelp,
                        itemToCheck: step.stepId,
                        actionToTake: step.actionToTake,
                        observe: step.observe,
                        disableInteraction: !step.actionToTake
                    }
                    optionText.push(obj)
                    x++
                }
            }

            if (step.toNextScreen) {
                break
            }
        }
    }
    intro.setOptions({
        showProgress: true,
        scrollToElement: true,
        showStepNumbers: false,
        exitOnOverlayClick: false,
        showBullets: false,
        doneLabel: closeText,
        skipLabel: closeText,
        nextLabel: nextText,
        hideNext: true,
        tooltipPosition: "top-middle-aligned",
        steps: optionText
    })
    intro.onafterchange(function (targetElem) {
        intro.refresh()
        var currentStep = this._currentStep
        var currentStepOption = this._options.steps[currentStep]
        var itemToCheck = this._options.steps[currentStep].itemToCheck
        var itemObj = $("#" + itemToCheck)
        if (currentStepOption.actionToTake) {
            if (!$('.open').hasClass("keepOpen")) {
                $('.open').addClass("keepOpen")
            }


            //observe is prepared for next improvement
            if (currentStepOption.observe) {
                var elemt2 = $(currentStepOption.observe)
                $(elemt2).on("click", function (elem) {
                    $(elemt2).bind("click", triggerNextStep())
                })
            } else {
                $(currentStepOption.element).on(currentStepOption.actionToTake, function (e) {
                    triggerNextStep()
                    intro.refresh()

                });
            }
            if (currentStepOption.actionToTake == "type") {
                $(currentStepOption.element).find("form").on("submit", function () {
                    triggerNextStep()
                })
                $(currentStepOption.element).find(".close").on("click", function () {
                    intro.exit()
                })
                $(currentStepOption.element).find(".btn-default").on("click", function () {
                    triggerNextStep()
                })


            }
            $('.introjs-nextbutton').addClass("disabledSaveOnLoad");
        } else {
            $('.introjs-nextbutton').removeClass("disabledSaveOnLoad");

        }
        intro.refresh()

    })

    intro.onbeforeexit(function () {
        workflowFinish = true;
        $('body').removeClass('stop-scrolling')
    })
    intro.onexit(function () {
        var openedList = $(".keepOpen")
        if (openedList.length > 0) {
            $(openedList).removeClass("keepOpen")
            triggerNextStep()
        }
        $(".alert").removeClass("hidden")

    })
    intro.oncomplete(function () {
        triggerNextStep()
        workflowFinish = true;
    })

    if (optionText.length > 0) {
        var firstStep = optionText[0]
        if ((firstStep.element && $(firstStep.element).is(":visible")) || firstStep.element == null) {
            $("#messageContent").addClass("hidden")
            $(".alert").addClass("hidden")
            startIntroJs(firstStep)
        }
        return true
    } else {
        return false
    }
}

function triggerNextStep(fromIntroBtn) {
    intro.refresh()
    if ((!workflowFinish && !fromIntroBtn) || fromIntroBtn) {
        var currentStep = intro._currentStep

        var currentStepOption
        if (currentStep) {
            if (fromIntroBtn && currentStep > 0) {
                currentStep = currentStep - 1
            }
            currentStepOption = intro._options.steps[currentStep]
        } else {
            currentStepOption = intro._options.steps[0]
        }
        var itemToCheck = currentStepOption.itemToCheck
        var itemObj = $("#" + itemToCheck)
        $(itemObj).prop('checked', true);
        if (currentStepOption.actionToTake == "keyup") {
            if ($(currentStepOption.element).val().length > 7) {
                $(currentStepOption.element).off("keyup")
                clearTimeout(timer);
                timer = setTimeout(function () {
                    updateWorkFlowModel($(itemObj), itemToCheck, true)
                    if (!fromIntroBtn) {
                        if (currentStep != intro._options.steps.length - 1) {
                            intro.nextStep()
                        } else {
                            intro.exit()
                        }
                    }
                }, timeOut)

            } else {
                return false
            }
        } else {
            updateWorkFlowModel($(itemObj), itemToCheck, true)
            if (!fromIntroBtn) {
                if (currentStep != intro._options.steps.length - 1) {
                    intro.nextStep()
                } else {
                    intro.exit()
                }
            }
        }
    }
    intro.refresh()

}

function startIntroJs(firstStep) {
    intro.start()
    $('body').addClass('stop-scrolling')
    if (firstStep.actionToTake) {
        $('.introjs-nextbutton').addClass("disabledSaveOnLoad");
    }

    $(".dropdown-menu").find("li").on("click", function (elem) {
        $(this).parents().parents().removeClass("keepOpen open")
    })
    $(".introjs-overlay").addClass("removeClicks disabledSaveOnLoad")
    if (!toggledFunctionToNext) {
        $('.introjs-nextbutton').on("click", function () {
            toggledFunctionToNext = true
            triggerNextStep(true)
        })
    }
}

function removeClicks(elem) {
    $(elem).addClass('removeClicks')
}

function renderTablePopOverWithMap(mapObject, hostId, appendAsTable) {
    var detailContentTable = "<table class=\'table table-striped table-condensed small-table-center\'><tbody>"
    for (var property in mapObject) {
        if (mapObject.hasOwnProperty(property) && mapObject[property].length > 0) {
            detailContentTable += "<tr><th>" + property + "</th><td>" + mapObject[property] + "</td></tr>"
        }
    }
    detailContentTable += "</tbody></table>"
    if (appendAsTable) {
        $("#" + hostId).append(detailContentTable)
    } else {
        var popOverSettingsForBenchmarkInfoTab = {
            placement: 'bottom',
            content: detailContentTable,
            container: 'body',
            html: true,
            trigger: "click",
            template: '<div class="popover popover-fade" style=" display: block; max-width: 500px;"><div class="arrow"></div><div class="popover-content"></div></div>'
        };
        $("#" + hostId).popover(popOverSettingsForBenchmarkInfoTab);
    }


}

function turnOffOnboarding(buttonToDisable, localizedMessage) {
    var url = "/app/sec/user/disableOnboardingForUser"
    $.ajax({
        url: url,
        type: "POST",
        success: function (data, textStatus) {
            console.log("Success")
            console.log(data)
            toggleDisableMessagePopover(buttonToDisable, localizedMessage)
        },
        error: function (error, textStatus) {
            console.log(error + ": " + textStatus)
        }
    })
}

function toggleDisableMessagePopover(buttonToDisable, localizedMessage) {
    $("#" + buttonToDisable).prop("disabled", true)
    $('#' + buttonToDisable).popover({
        placement: 'top',
        content: localizedMessage,
        template: '<div class="popover popover-fade"><div class="arrow"></div><div class="popover-content"></div></div>',
        trigger: 'hover',
        html: true
    })
}

function removeEntityFile(fileId) {
    var fileIdElement = '#' + fileId;

    $.ajax({
        type: 'POST', data: 'fileId=' + fileId, url: '/app/removefile',
        success: function (data, textStatus) {
            $(fileIdElement).remove();
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}

function clearMessages(toShowId, toHideId) {
    $('.alert').hide();
    if (toShowId) {
        $(toShowId).show()
    }
    if (toHideId) {
        $(toHideId).hide()
    }
}

function downloadQueryExcel(entityId, childEntityId, queryId, indicatorId) {
    var info = $("#queryExcelDownloadInfo").val();
    var download = $("#queryExcelDownloadButton").val();
    var cancel = $("#queryExcelDownloadCancel").val();
    Swal.fire({
        icon: "warning",
        confirmButtonText: download,
        cancelButtonText: cancel,
        confirmButtonColor: "green",
        showCancelButton: true,
        reverseButtons: true,
        html: info,
        customClass: "swal-medium"
    }).then(result => {
        if (result.value) {
            window.location = "/app/sec/query/generateExcelFromQueryDatasets?entityId=" + entityId + "&childEntityId=" + childEntityId + "&queryId=" + queryId + "&indicatorId=" + indicatorId + ""
        }
    });
}

function toggleInputOnChangeWarning(element, errorWarning) {
    if (errorWarning) {
        $(element).parent().prev(':input').addClass('redBorder warningHighlight');
        setTimeout(function () {
            $(element).parent().prev(':input').removeClass('redBorder warningHighlight');
        }, 1000)
    } else {
        $(element).parent().prev(':input').addClass('filterChosenHighlight');
        setTimeout(function () {
            $(element).parent().prev(':input').removeClass('filterChosenHighlight');
        }, 1000)
    }


}

function exportPDFReport(canvasIdsList) {
    var chartIdAndOptions = {
        byMaterialSixPack: {
            chart: {
                type: "column"
            },
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                visible: true,
                type: 'category',
                max: null
            },
            yAxis: {
                visible: true,
                max: null
            },
            exporting: {
                sourceWidth: 900,
                sourceHeight: 420,
                chartOptions: {
                    legend: {
                        enabled: true
                    }
                }
            },
            plotOptions: {
                series: {
                    dataLabels: {
                        enabled: true,
                        color: '#000000',
                        formatter: function () {
                            return this.y.toFixed(2)
                        }
                    }
                },
            },
            drilldown: {
                activeAxisLabelStyle: {
                    textDecoration: 'none',
                    color: '#696969',

                },
                activeDataLabelStyle: {
                    textDecoration: 'none',
                    color: '#696969',
                },
            }
        },
        byClassSixPack: {
            chart: {
                type: "bar",
                inverted: true
            },
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                visible: true,
                type: 'category',
                max: null
            },
            yAxis: {
                visible: true,
                max: null
            },
            legend: {
                enabled: false
            },
            exporting: {
                sourceWidth: 400,
                sourceHeight: 300,
                chartOptions: {
                    legend: {
                        enabled: false
                    }
                }
            },
            plotOptions: {
                series: {
                    dataLabels: {
                        enabled: true,
                        color: '#696969',
                        formatter: function () {
                            return this.y.toFixed(2)
                        }
                    }
                }
            },
            drilldown: {
                activeAxisLabelStyle: {
                    textDecoration: 'none',
                    color: '#696969',
                },
                activeDataLabelStyle: {
                    textDecoration: 'none',
                    color: '#696969',
                },
            }
        },
        massByClassSixPack: {
            chart: {
                type: "bar",
            },
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                visible: true,
                type: 'category',
                max: null
            },
            yAxis: {
                visible: true,
                max: null
            },
            legend: {
                enabled: false
            },
            exporting: {
                sourceWidth: 400,
                sourceHeight: 300,
                chartOptions: {
                    legend: {
                        enabled: false
                    }
                }
            },
            plotOptions: {
                series: {
                    dataLabels: {
                        enabled: true,
                        color: '#696969',
                        formatter: function () {
                            return this.y.toFixed(2)
                        }
                    }
                }

            },
            drilldown: {
                activeAxisLabelStyle: {
                    textDecoration: 'none',
                    color: '#696969',
                },
                activeDataLabelStyle: {
                    textDecoration: 'none',
                    color: '#696969',
                },
            }
        },
        default: {
            chart: {
                type: "column"
            },
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                visible: true,
                type: 'category',
                max: null
            },
            yAxis: {
                visible: true,
                max: null
            },
            exporting: {
                sourceWidth: 900,
                sourceHeight: 420,
                dataLabels: {
                    enabled: true,
                }
            },
            plotOptions: {
                series: {
                    dataLabels: {
                        enabled: true,
                        color: '#696969',
                        formatter: function () {
                            return this.y.toFixed(2)
                        }
                    }
                }
            },
            drilldown: {
                activeAxisLabelStyle: {
                    textDecoration: 'none',
                    color: '#000000',
                },
                activeDataLabelStyle: {
                    textDecoration: 'none',
                    color: '#696969',
                },
            }
        }
    }
    // cheat to switch chart type
    if (canvasIdsList) {
        var showAllGraph = $("#all_graphs")
        if(showAllGraph){
            toggleActiveTab(showAllGraph,true)
        }
        openOverlay("wordDocGenOverlay");
        appendLoader('wordDocGenOverlayContent');
        canvasIdsList = canvasIdsList.split(",");
        var sizeOfCanvasRendered = 1;
        var canvasesSize = canvasIdsList.length
        $(canvasIdsList).each(function () {
            var chartId = this
            var settingForThis
            for (var key in chartIdAndOptions) {
                if (chartId == key) {
                    settingForThis = chartIdAndOptions[key]
                    break
                } else {
                    settingForThis = chartIdAndOptions["default"]
                }
            }
            saveCanvasBase64ToUserSession(this, settingForThis)
            if (sizeOfCanvasRendered === canvasesSize) {
                exportBenchmarkToBase64("benchmarkTable", "#benchmarkGraphTable")
            } else {
                sizeOfCanvasRendered++
            }
        });
    }
}

function exportBenchmarkToBase64(tableTag, tableId) {
    if (!$(tableId).is(":visible")) {
        $("#resultPageBenchmark").parent().show()
    }
    if ($(tableId).length > 0) {
        html2canvas($(tableId)[0], {scale: 3}).then(function (canvas) {
            var imageData = canvas.toDataURL("image/png");
            if (imageData) {
                imageData = encodeURIComponent(imageData);
                $.ajax({
                    url: '/app/sec/fileExport/saveCanvasBase64ToUserSession',
                    data: 'canvasId=b64' + tableTag + '&data=' + imageData,
                    type: 'POST',
                    async: false,
                    success: function (data) {
                        window.location = document.getElementById("generatePdf").href
                    },
                    error: function (data) {
                        window.location = document.getElementById("generatePdf").href
                        console.log(data)
                    }

                })
            }

        });
    } else {
        window.location = document.getElementById("generatePdf").href
    }

}

function millisToMinutesAndSeconds(millis) {
    let sec = Math.floor(millis / 1000);
    let hrs = Math.floor(sec / 3600);
    sec -= hrs * 3600;
    let min = Math.floor(sec / 60);
    sec -= min * 60;

    sec = '' + sec;
    sec = ('00' + sec).substring(sec.length);

    if (hrs > 0) {
        min = '' + min;
        min = ('00' + min).substring(min.length);
        return hrs + "h " + min + ":" + sec;
    } else {
        return min + " min " + sec + " sec";
    }
}

function showImpactCategories() {

    var showImpactCats = $('#impactCatDiv');
    if (!showImpactCats.is(":visible")) {
        $(showImpactCats).slideDown().removeClass('hidden');
    } else {
        $(showImpactCats).slideUp().addClass('hidden');
    }
}

function showNotificationPopUp() {
    var modalId = "#modalPopUp"

    var modalBody = "#modalPopUpBody"

    $(modalId).modal()
}

function hideNotificationPopUp(id) {

    if ($("#hideNotification").is(":checked")) {
        acknowledgeUpdate(id, 'modalPopUp')
    } else {
        $("#modalPopUp").hide();
    }
}

function sendMeDataSwal(entityId, indicatorId, localizedHeading) {
    var swal1HTML = "";
    var swal2HTML = "";
    var title = "";
    var cancelText = "";
    var continueText = "";
    var missingEmailText = "";
    var sendDataText = "";
    var sendDataSuccessText = "";
    var sessionExpiredText = "";
    var questionAutocompleteChoices = {};
    $.ajax({
        type: 'POST',
        async: false,
        data: 'entityId=' + entityId + '&indicatorId=' + indicatorId,
        url: '/app/sec/sendMeDataRequest/getSendResourceToAnotherUserHTML',
        success: function (data, textStatus) {
            swal1HTML = data.htmlPart1;
            swal2HTML = data.htmlPart2;
            title = data.title;
            cancelText = data.cancelText;
            continueText = data.continueText;
            missingEmailText = data.missingEmailText;
            sendDataText = data.sendDataText;
            sendDataSuccessText = data.sendDataSuccessText;
            sessionExpiredText = data.sessionExpiredText;
            questionAutocompleteChoices = data.questionAutocompleteChoices;
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });

    var email

    swal.mixin({
        title: title,
        confirmButtonColor: '#8DC73F',
        cancelButtonText: cancelText,
        cancelButtonColor: '#CDCDCD',
        showCancelButton: true,
        allowOutsideClick: false,
        reverseButtons: true,
        showLoaderOnConfirm: true,
        showCloseButton: true,
        customClass: 'swal-wide',
        progressSteps: ['1', '2']
    }).queue([{
        html: swal1HTML,
        title: localizedHeading,
        confirmButtonText: continueText,
        preConfirm: function () {
            return new Promise(function (resolve, reject) {
                var emailInput = $('#anotherUserEmail');
                email = $(emailInput).val();
                if (email.length > 0) {
                    var urlParams = '?email=' + encodeURIComponent(email)
                    $.post('/app/sec/sendMeDataRequest/verifySendMeDataAllowedForTarget' + urlParams)
                        .done(function (response) {
                            if (response.error) {
                                Swal.showValidationMessage(response.error.replace('&#64;', '@'))
                                Swal.hideLoading()
                                reject()
                            } else {
                                resolve()
                            }
                        })
                } else {
                    Swal.showValidationMessage(missingEmailText)
                    Swal.hideLoading()
                    reject()
                }
            })
        }
    },
        {
            html: swal2HTML,
            title: localizedHeading,
            confirmButtonText: sendDataText,
            onOpen: function () {
                for (var [key, value] of Object.entries(questionAutocompleteChoices)) {
                    var list = value;
                    if (list && list.length > 0) {
                        $("#" + key + "autocompletequestion").devbridgeAutocomplete({
                            minChars: 0,
                            lookup: list,
                            zIndex: 1000000, // fucking hell :D
                            onSearchStart: function (query) {
                                let val = new String($(this).val())
                                let pttrn = /^\s*/;
                                let start = val.match(pttrn)[0].length
                                val = val.substring(start)
                                $(this).val(val)
                            },
                            formatResult: function (suggestion) {
                                let div = document.createElement("div")
                                let text = document.createTextNode(" " + suggestion.value);
                                div.appendChild(text)
                                return div.innerHTML;
                            },
                            onSelect: function (suggestion) {
                                removeBorder($(this))
                                $('#' + suggestion.data.questionId + 'autocompletequestionHidden').val(suggestion.data.resourceId);
                            }
                        });
                    }
                }
            }, preConfirm: function () {
                var myForm = document.getElementById('sendMeDataForm');
                $("#anotherUserEmail").val(email)
                var formData = new FormData(myForm);
                return fetch('/app/sec/sendMeDataRequest/sendResourceToAnotherUser', {
                    method: "POST",
                    body: formData,
                    redirect: "error"
                })
                    .then(response => {
                        console.log(response)
                        if (response.ok) {
                            return response.json()
                        } else {
                            throw new Error(sessionExpiredText)
                        }
                    })
                    .then(json => {
                        console.log("SEND DATA RESPONSE: ")
                        console.log(json)
                        if (json.error) {
                            throw new Error(json.error)
                        } else {
                            Swal.fire({
                                icon: 'success',
                                title: sendDataSuccessText,
                                allowOutsideClick: false,
                                html: ''
                            })
                        }
                        return true
                    })
                    .catch(error => {
                        if (error == "TypeError: Failed to fetch") {
                            Swal.showValidationMessage(sessionExpiredText)
                        } else {
                            Swal.showValidationMessage(error)
                        }
                    })
            },
        }
    ]);
}

function renderHighchartStackedColumn(subtypeId, chartId, chartName, data, categories, unitCarbon,noGraphText,imageURL, financeCostData, totalCostData, unitCost) {
    var yAxisSetting
    var seriesSetting
    var chartSetting
    if(financeCostData || totalCostData){
        chartSetting = {
            renderTo: chartId,
        };
        yAxisSetting =[{
            title: {
                enabled: true,
                text: "Carbon",
            },
            labels: {
                format: '{value} '+ unitCarbon,
            },
            reversedStacks: false,
            min:0
        },{ // Secondary yAxis
            gridLineWidth: 0,
            title: {
                text: 'Cost',
            },
            labels: {
                format: '{value} '+unitCost,
            }
            ,
            min:0,
            opposite: true
        }]
        data.push(totalCostData)
        data.push(financeCostData)
        seriesSetting = data
    } else {
        yAxisSetting = {
            title: {
                enabled: true,
                text: "Carbon",
            },
            labels: {
                format: '{value} '+ unitCarbon,
            },
            reversedStacks: false
        }
        seriesSetting = data;
        chartSetting = {
            renderTo: chartId,
            type:"column"
        };
    }

    var chart = new Highcharts.chart({
        chart: chartSetting,
        title: {
            text: chartName,
            style: {
                color: '#333333',
                fontWeight: 'bold',
                fontSize: '20px',
                fontFamily: 'Arial'
            },
            useHTML: true
        },
        series: seriesSetting,
        plotOptions: {
            column: {
                stacking: 'normal',
                tooltip:{
                    valueSuffix: unitCarbon,
                    useHTML: true
                },
            },
            line: {
                tooltip:{
                    valueSuffix: unitCost,
                    useHTML: true
                },
            }
        },
        yAxis: yAxisSetting,
        xAxis: {
            categories: categories,
            labels: {
                formatter: function () {
                    var label = this.value
                    if (label.length > 150) {
                        label = this.value.substring(0, 150)
                    }
                    return label
                }
            }
        },
        tooltip: {
            valueSuffix: '',
            valueDecimals: 2,
            shared: true,
            useHTML:true
        },
        legend: {
            align: 'center',
            verticalAlign: 'top',
            itemStyle: {
                fontWeight: 'normal',
                fontFamily: 'Arial'
            },

        },
        credits: {
            enabled: false
        },
        lang: {
            noData: noGraphText
        },
        exporting: {
            enabled: true,
            sourceWidth: 1000,
            sourceHeight: 500,
            buttons: {
                contextButton: {
                    enabled: true,
                    menuItems: [
                        'downloadPNG',
                        'downloadJPEG',
                        'downloadPDF',
                        'downloadSVG',
                        'separator',
                        'downloadCSV',
                        'downloadXLS',
                        {
                            text: 'Download XLSX',
                            onclick: function () {
                                this.downloadXLSX();
                            }
                        },
                        'viewData'
                    ]
                }
            },
            csv: {
                // dateFormat: '%Y%m'
            },
            xlsx: {
                worksheet: {
                    autoFitColumns: true,
                    categoryColumnTitle: 'Month',
                    dateFormat: 'yyyy-mm',
                    name: 'Chart data'
                },
                workbook: {
                    fileProperties: {
                        Author: "One Click LCA.",
                        Company: "One Click LCA.",
                        CreatedDate: new Date(Date.now())
                    }
                }
            },
            enableImages: true,
            chartOptions: {
                chart: {
                    events: {
                        load: function() {
                            var chart = this;
                            chart.renderer.image(imageURL, 700, 0, 300, 187).add().toFront();
                        }
                    }
                }
            }
        }

    })
}

function addToCompareResource(element) {
    var resourceId = $(element).val();
    var compareBtnId = $(element).attr("data-target")
    var compareBtn = $("#" + compareBtnId)
    var formCompare = $("#compareForm");
    var resourceListForm = $(formCompare).find("#resourceList")
    var existResourceIdList = $(resourceListForm).val()
    var value = existResourceIdList ? existResourceIdList.split(",") : []
    if ($(element).is(":checked")) {
        if (value.indexOf(resourceId) === -1) {
            value.push(resourceId);
        }
    } else {
        value.splice(value.indexOf(resourceId), 1)
    }
    var resourceCount = value.length
    if(resourceCount) {
        $(".compareButton").css('pointer-events', 'auto');
    }
    var countSpan = $(".compareBtnCount")
    var compareBtn = $(".compareButton")
    $(".compareButton").attr("data-value", value)
    $(resourceListForm).val(value)
    updateCountNumberCompareBasket(value, countSpan,compareBtn)
    addResourcesToSession($(resourceListForm).val(), $(formCompare).find("#parentEntityId").val())
}

function appendResourceToCompareForm() {
    event.preventDefault();
    var formCompare = $("#compareForm");
    $(".compareBtnCount").text("")
    $(".compareButton").attr("data-value", "").addClass("grayLink").removeClass("highlighted")
    $(".compareButton").css("pointer-events", "none")
    swal.fire({
        title: "Adding...", onOpen: function () {
            swal.showLoading()
        }
    })
    $(formCompare).submit();
}

function addSingleResourceToCompare(resourceId, parentId, originalIndicatorId, profileId) {
    swal.fire({
        title: "Adding...",
        customClass: {
            container: "top-swal"
        },
        onOpen: function () {
            swal.showLoading()
        }
    })
    $.ajax({
        type: 'POST',
        data: 'resourceId=' + resourceId + '&profileId=' + profileId + '&originIndicatorId=' + originalIndicatorId + '&parentEntityId=' + parentId,
        url: '/app/sec/util/addResourceToCompare',
        success: function (data) {
            if (data) {
                var content = ""
                if(data.output.notAddedResources != 0){
                    content ="Resource is already added to compare"
                    Swal.fire({
                        icon: 'info',
                        title: "No resources added",
                        text: content,
                        showConfirmButton: false,
                        timer: 1500,
                        customClass: {
                            container: "top-swal"
                        },
                    })
                } else {
                    Swal.fire({
                        icon: 'success',
                        title: "Sucessfully added resources to compare",
                        showConfirmButton: false,
                        timer: 1500,
                        customClass: {
                            container: "top-swal"
                        },
                    })
                }

                $(".compareButtonTotal").find(".compareBtnCountTotal").text(data.output.datasize)
            } else {
                swal.fire({
                    icon: 'error',
                    title: "Error! Resources were not added to compare",
                    customClass: {
                        container: "top-swal"
                    },
                })
            }
        },
        error: function (e) {
            console.log(e)
        }
    });
}

function addResourcesToSession(listToSave, parentEntityId) {
    var json = {
        resourceList: listToSave,
        parentEntityId: parentEntityId
    }
    var url = "/app/sec/util/saveResourceCompareFromAjax"
    var success = function (data) {
        console.log(data)
    }
    var error = function (e) {
        console.log("ERROR ADDING RESOURCES: " + e.toString())
    }
    ajaxGeneralNoJson(url, json, success, error)
    return false
}

function updateCountNumberCompareBasket(value, countSpan, countBtn) {
    var lengthVal = value.filter(it => it != "").length
    if (lengthVal > 0) {
        $(countSpan).text("(" + lengthVal + ")")
        $(countBtn).removeClass("grayLink").addClass("highlighted-new")
        setTimeout(function(){
            $(countBtn).removeClass("highlighted-new")
        },2000)
    } else {
        $(countSpan).text("")
    }
}

function loadTestableDesignsDropdown(entityId, fallbackMsg) {
    if (0 !== $('#testableDesignsList li').length) {
        return
    }
    $.ajax({
        async: true, type: 'GET', dataType: "json",
        data: 'entityId=' + entityId,
        url: '/app/sec/entity/getTestableDesignsDropdown',
        success: function (data, textStatus) {
            let retriggerDropdowns = false;
            if (data.testableDesigns && $('#testableDesignsList li').length === 0) {
                $('#testableDesignsList').append(data.testableDesigns);
                retriggerDropdowns = true;
            } else if($('#testableDesignsList li').length === 0) {
                $('#testableDesignsList').append('<li>'+ fallbackMsg + '</li>');
            }

            if (retriggerDropdowns) {
                retriggerDropdowns();
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    });
}

function loadTestablePeriodsDropdown(entityId, fallbackMsg) {
    if (0 !== $('#testablePeriodsList li').length) {
        return
    }
    $.ajax({
        async: true, type: 'GET', dataType: "json",
        data: 'entityId=' + entityId,
        url: '/app/sec/entity/getTestablePeriodsDropdown',
        success: function (data, textStatus) {
            let retriggerDropdowns = false;
            if (data.testablePeriods && $('#testablePeriodsList li').length === 0) {
                $('#testablePeriodsList').append(data.testablePeriods);
                retriggerDropdowns = true;
            } else if($('#testablePeriodsList li').length === 0) {
                $('#testablePeriodsList').append('<li>'+ fallbackMsg + '</li>');
            }

            if (retriggerDropdowns) {
                retriggerDropdowns();
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    });
}

function retriggerDropdowns() {
    $('.dropdown-toggle').dropdown();
    $('.dropdown-menu').find('form').on('click', function (e) {
        e.stopPropagation();
    });
}

function removePopoverTitle() {
    $('.popover-title').remove()
}

/**
 * Use this to keep the bootstrap popover while hovering its content, do not use with data-trigger="hover".
 *
 * @param {string} selector - .class OR #id
 * @param {number} delay - in milliseconds to close the popover after hovering
 * @param {number} maxWidth - (optional) in pixels to limit max-width of popover
 * @param {string} template - (optional)
 */
function keepPopoverOnHover(selector, delay, maxWidth = 0, template = '') {
    let $popover
    if (template) {
        $popover = $(selector).popover({
            trigger: 'manual',
            html: true,
            animation: true,
            container: 'body',
            template: template
        })
    } else {
        $popover = $(selector).popover({
            trigger: 'manual',
            html: true,
            animation: true,
            container: 'body'
        })
    }
    $popover
        .on('mouseenter', function () {
            const target = this;
            $(this).popover('show')
            removePopoverTitle()
            const $popover = $(".popover")
            $popover
                .on('mouseleave', function () {
                    $(target).popover('hide')
                })
                .css('z-index', 10000)
            if (maxWidth !== 0) {
                $popover.css('max-width', maxWidth)
            }
        })
        .on('mouseleave', function () {
            const target = this;
            setTimeout(function () {
                if (!$('.popover:hover').length) {
                    $(target).popover("hide");
                }
            }, delay)
        })
}

/**
 * Trigger popover with settings for those elements that has rel="popover"
 * @param $container
 * @param settings
 */
function triggerPopoverWithSettings($container, settings) {
    $($container).find('[rel="popover"]').popover(settings);
}

function triggerPopoverOnHover(selector, zIndex = autocompleteZIndex + 1) {
    $(selector).popover({trigger: 'hover', html: true, animation: true, container: 'body', placement: 'top'}).on('mouseenter', function(){
        $(".popover").css('z-index', zIndex)
    })
}

function showDeprecationNote(val,message){
    //Swal.fire(message,val,"warning");
    Swal.fire({
        icon: 'warning',
        title: val+' will be deprecated',
        text:  message,
        footer: 'For further information contact support through the Help widget'
    })
}
function createPrivateData(acId , resId) {
    //var idToAppend = '.renderCreateSendMeData';

    if (acId && resId) {
        $.ajax({
            type: 'POST',
            data: 'accountId=' + acId+'&resourceId=' + resId ,
            url: '/app/sec/account/createPrivateData',
            success: function (data) {
                window.onbeforeunload = null;
                location.reload();
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
            }
        });
    }
}

    function showCreatePrivateDataSections(val) {
    var idToAppend = '.renderCreateSendMeData';
    var jSon = {
        dataTypeSelected : val
    };
    if(val !='') {
        $.ajax({
            type: 'POST',
            data:  JSON.stringify(jSon),
            url: '/app/sec/sendMeDataRequest/renderQuestionsForSendMeData',
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                var questionAutocompleteChoices = data.outMap.questionChoices
                if(data.outMap.page){
                    $(idToAppend).empty();
                    $(idToAppend).append(data.outMap.page);
                }
                if(questionAutocompleteChoices){
                    for (var [key, value] of Object.entries(questionAutocompleteChoices)) {
                        var list = value;
                        if (list && list.length > 0) {
                            $("#" + key + "autocompletequestion").devbridgeAutocomplete({
                                minChars: 0,
                                lookup: list,
                                zIndex: 1000000, // fucking hell :D
                                onSearchStart: function (query) {
                                    let val = new String($(this).val())
                                    let pttrn = /^\s*/;
                                    let start = val.match(pttrn)[0].length
                                    val = val.substring(start)
                                    $(this).val(val)
                                },
                                formatResult: function (suggestion) {
                                    let div = document.createElement("div")
                                    let text = document.createTextNode(" " + suggestion.value);
                                    div.appendChild(text)
                                    return div.innerHTML;
                                },
                                onSelect: function (suggestion) {
                                    removeBorder($(this))
                                    $('#' + suggestion.data.questionId + 'autocompletequestionHidden').val(suggestion.data.resourceId);
                                }
                            });
                        }
                    }
                }

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
            }
        });
    }
}

function editPrivateDataModal(resourceId, accountId , modalDiv) {
    var url = "/app/sec/account/editPrivateDataset"
    var json = {resourceId: resourceId,
        accountId : accountId
    }

    $.ajax({
        type: "POST",
        data: JSON.stringify(json),
        contentType: "application/json; charset=utf-8",
        url: url,
        success: function (r) {
            $('#privateDataForm').remove();
            $(modalDiv).append(r.output).modal('toggle')
        },
        error: function (error) {
            console.log(error)
        }
    });
}
function sendMeDataSwalNew(entityId, indicatorId, localizedHeading) {
    var swal1HTML = "";
    var swal2HTML = "";
    var title = "";
    var cancelText = "";
    var continueText = "";
    var reviewDataText = "";
    var missingEmailText = "";
    var sendDataText = "";
    var sendDataSuccessText = "";
    var sessionExpiredText = "";
    var questionAutocompleteChoices = {};
    $.ajax({
        type: 'POST',
        async: false,
        data: 'entityId=' + entityId + '&indicatorId=' + indicatorId,
        url: '/app/sec/sendMeDataRequest/getSendResourceToAnotherUserHTML',
        success: function (data, textStatus) {
            swal1HTML = data.htmlPart1;
            swal2HTML = data.htmlPart2;
            title = data.title;
            cancelText = data.cancelText;
            continueText = data.continueText;
            reviewDataText = data.reviewDataText;
            missingEmailText = data.missingEmailText;
            sendDataText = data.sendDataText;
            sendDataSuccessText = data.sendDataSuccessText;
            sessionExpiredText = data.sessionExpiredText;
            questionAutocompleteChoices = data.questionAutocompleteChoices;
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });

    var email

    swal.mixin({
        title: title,
        confirmButtonColor: '#8DC73F',
        cancelButtonText: cancelText,
        cancelButtonColor: '#CDCDCD',
        showCancelButton: true,
        allowOutsideClick: false,
        reverseButtons: true,
        showLoaderOnConfirm: true,
        showCloseButton: true,
        customClass: 'swal-wide',
        progressSteps: ['1', '2','3']
    }).queue([{
        html: swal1HTML,
        title: localizedHeading,
        confirmButtonText: continueText,
        preConfirm: function () {
            return new Promise(function (resolve, reject) {
                var emailInput = $('#anotherUserEmail');
                email = $(emailInput).val();
                if (email.length > 0) {
                    var urlParams = '?email=' + encodeURIComponent(email)
                    $.post('/app/sec/sendMeDataRequest/verifySendMeDataAllowedForTarget' + urlParams)
                        .done(function (response) {
                            if (response.error) {
                                Swal.showValidationMessage(response.error.replace('&#64;', '@'))
                                Swal.hideLoading()
                                reject()
                            } else {
                                resolve()
                            }
                        })
                } else {
                    Swal.showValidationMessage(missingEmailText)
                    Swal.hideLoading()
                    reject()
                }
            })
        }
    },
        {
            html: swal2HTML,
            title: localizedHeading,
            confirmButtonText: continueText,
            onOpen: function () {
                for (var [key, value] of Object.entries(questionAutocompleteChoices)) {
                    var list = value;
                    if (list && list.length > 0) {
                        $("#" + key + "autocompletequestion").devbridgeAutocomplete({
                            minChars: 0,
                            lookup: list,
                            zIndex: 1000000, // fucking hell :D
                            onSearchStart: function (query) {
                                let val = new String($(this).val())
                                let pttrn = /^\s*/;
                                let start = val.match(pttrn)[0].length
                                val = val.substring(start)
                                $(this).val(val)
                            },
                            formatResult: function (suggestion) {
                                let div = document.createElement("div")
                                let text = document.createTextNode(" " + suggestion.value);
                                div.appendChild(text)
                                return div.innerHTML;
                            },
                            onSelect: function (suggestion) {
                                removeBorder($(this))
                                $('#' + suggestion.data.questionId + 'autocompletequestionHidden').val(suggestion.data.resourceId);
                            }
                        });
                    }
                }
            }, preConfirm: function () {
                return new Promise(function (resolve, reject) {
                    var myForm = document.getElementById('sendMeDataForm');
                    $("#anotherUserEmail").val(email)
                    var formData = new FormData(myForm);
                    fetch('/app/sec/sendMeDataRequest/reviewSendMeData', {
                        method: "POST",
                        body: formData,
                        redirect: "error"
                    })
                        .then(response => response.json())
                        .then(json => {
                            if (json.error) {
                                Swal.showValidationMessage(json.error)
                                Swal.hideLoading()
                            } else {
                                Swal.insertQueueStep({
                                    title: localizedHeading,
                                    html: json.htmlPart3,
                                    confirmButtonText: sendDataText,
                                    showCancelButton: true,
                                    cancelButtonText: "Cancel",
                                    allowOutsideClick: false,
                                    preConfirm: function () {

                                        return fetch('/app/sec/sendMeDataRequest/sendDataToAnotherUser', {
                                            method: "POST",
                                            redirect: "error"
                                        })
                                            .then(response => {
                                                console.log(response)
                                                if (response.ok) {
                                                    Swal.fire({
                                                        icon: 'success',
                                                        title: sendDataSuccessText,
                                                        allowOutsideClick: false,
                                                        html: ''
                                                    })
                                                } else {
                                                    Swal.showValidationMessage(sessionExpiredText)
                                                }
                                            })
                                    }
                                })
                                resolve()
                            }
                        })
                        .catch(error => {
                            if (error == "TypeError: Failed to fetch") {
                                Swal.showValidationMessage(sessionExpiredText)
                            } else {
                                Swal.showValidationMessage(error)
                            }
                        })
                })
            },
        },
    ]);
}


// bootstrap modal has default margin top and left to be 1/2 screen size. Use this function if that's the case for your modal and want to center it
function alignModal(modalId) {
    if (modalId) {
        let $modal = $('#' + modalId)
        let modalHeight = $modal.height()
        let modalWidth = $modal.width()
        let windowHeight = $(window).height()
        let windowWidth = $(window).width()
        let originalTop = windowHeight / 2
        let originalLeft = windowWidth / 2
        let marginTop = (windowHeight - modalHeight) / 2 - originalTop
        let marginLeft = (windowWidth - modalWidth) / 2 - originalLeft

        // Applying the top margin on modal to align it vertically center
        $modal.css("margin-top", marginTop);
        // Applying the left margin on modal to align it horizontally center
        $modal.css("margin-left", marginLeft);
    }
}

/**
 * Can be used to visually disable inputs.
 * @param $parent the parent element as the boundary to find children elements to run this method
 * @param selector
 */
function disableVisuallyElements($parent, selector) {
    if ($parent) {
        $($parent).find(selector).addClass('removeClicks')
    }
}

/**
 * Can be used to visually enable inputs.
 * @param $parent the parent element as the boundary to find children elements to run this method
 * @param selector
 */
function enableVisuallyElements($parent, selector) {
    if ($parent) {
        $($parent).find(selector).removeClass('removeClicks')
    }
}

/**
 *  Enable / disable all inputs, selects that are children of a row (or any HTML element).
 *  Can pass in the id or the row element object itself
 * @param rowId
 * @param $row
 */
function toggleInputEntireRow(rowId, $row) {
    if (!$row) {
        $row = $('#' + rowId)
    }
    $($row).find('select, input').each(function() {
        if ($(this).prop('disabled')) {
            if ($(this).attr('data-switch') === 'true') {
                enableSwitch(this)
            } else {
                $(this).prop('disabled', false)
            }
        } else {
            if ($(this).attr('data-switch') === 'true') {
                disableSwitch(this)
            } else {
                $(this).prop('disabled', true)
            }
        }
    })
}

function getAllInputFromFormToQueryParams(formId) {
    let params = new URLSearchParams()

    $('#' + formId + ' input').each(function () {
        const name = $(this).prop('name')
        const type = $(this).prop('type')
        if (type !== 'checkbox' || (type === 'checkbox' && $(this).is(':checked'))) {
            const value = $(this).val()
            if (value) {
                params.append(name, value)
            }
        }
    })

    $('#' + formId + ' select').each(function () {
        const name = $(this).prop('name')
        const value = $(this).val()
        if (name && value) {
            params.append(name, value)
        }
    })

    return params.toString()
}

function clearAllInputFromForm(formId) {
    $('#' + formId + ' input').each(function () {
        const $this = $(this)
        const type = $this.prop('type')
        if (type === 'checkbox' && $this.is(':checked')) {
            // clear checkbox
          $this.prop('checked', false)
        } else if (type === 'text') {
            //clear text
            $this.val('')
        }
    })

    $('#' + formId + ' select').each(function () {
        // clear select
        $(this).val('').trigger('change')
    })
}

function handleSubmitProjectTemplateModal(submitBtnElem, formId, appendTo, replaceContent, closeModal, replaceSecondContent, secondOutputKey, runOnSuccess, errorMsg, successMsg, lcaDefaultsErrorMsg, toolListDivId, applyLcaDefaultCheckBoxId, allowChangeCheckBoxId, designNameInputId, designNameErrorMsg, allowChangeCheckCB, mandatoryTemplateOptionCheckCB) {
    let okForSubmit = true
    let hasToolUsingLcaParam = checkIfIndicatorUsingLcaParametersIsSelected(toolListDivId, 'indicatorIds')

    if (hasToolUsingLcaParam && !$('#'+ applyLcaDefaultCheckBoxId).is(':checked')) {
        Swal.fire({
            icon: 'warning',
            html: lcaDefaultsErrorMsg,
        })
        okForSubmit = false
    }

    if (okForSubmit) {

        const userNotAllowedToChange = !$('#'+ allowChangeCheckBoxId).is(':checked')
        const hasOneToolSet = $('#' + toolListDivId + ' input:checked').length > 0
        const $designInput = $('#' + designNameInputId)
        const designNameNotFilled = !$designInput.val()

        if (userNotAllowedToChange && hasOneToolSet && designNameNotFilled) {
            Swal.fire({
                icon: 'warning',
                html: designNameErrorMsg,
            })
            if (!$designInput.hasClass('redBorder')) {
                $designInput.addClass('redBorder')
            }
            okForSubmit = false
        }
    }

    if (okForSubmit) {
        okForSubmit = allowChangeCheckCB()
    }

    if (okForSubmit) {
        okForSubmit = mandatoryTemplateOptionCheckCB()
    }

    if (okForSubmit) {
        submitFormAjax(submitBtnElem, formId, '/app/sec/projectTemplate/saveProjectTemplate', appendTo, replaceContent, closeModal, replaceSecondContent, secondOutputKey, runOnSuccess, errorMsg, successMsg, getCsrfHeader())
    }
}

function submitFormAjax(submitBtnElem, formId, url, appendTo, replaceContent, closeModal, replaceSecondContent, secondOutputKey, runOnSuccess, errorMsg, successMsg = '', csrfHeader = {}) {
    appendLoaderToButton(null, submitBtnElem)
    disableSubmit(submitBtnElem)
    let queryParams = getAllInputFromFormToQueryParams(formId)

    $.ajax({
        headers: csrfHeader,
        type: 'POST',
        dataType: 'json',
        data: queryParams,
        url: url,
        async: true,
        success: function (data) {
            if (data.output) {
                // remove spinner loader
                removeLoaderFromButton(null, submitBtnElem)
                // enable submit btn
                enableSubmit(submitBtnElem)
                // close the modal
                if (closeModal) {
                    $('#' + closeModal).modal('hide')
                }

                if (data.output === 'error') {
                    Swal.fire({
                        icon: 'error',
                        title: 'Oops...',
                        text: errorMsg,
                    })
                } else {
                    let $target = $('#' + appendTo)
                    if (replaceContent) {
                        // replace with data
                        $target.replaceWith(data.output)
                    } else {
                        // append data
                        $target.append(data.output)
                        $target.parent().dataTable().fnDestroy();
                        $target.parent().dataTable({
                            "sPaginationType": "full_numbers",
                            "oLanguage": {
                                "sLengthMenu": "<span class='spacer'>Show:</span> _MENU_ entries",
                                "sSearch": "<span class='spacer'>Search:</span>"
                            },
                            "iDisplayLength": 10,
                            "aaSorting": [[ 0, "asc" ]]
                        });

                    }
                    if (replaceSecondContent && secondOutputKey) {
                        let secondaryOutput = data[secondOutputKey]
                        if (secondaryOutput) {
                            $('#' + replaceSecondContent).replaceWith(secondaryOutput)
                        }
                    }

                    runOnSuccess()

                    if (successMsg) {
                        // show success swal
                        Swal.fire({
                            icon: 'success',
                            title: successMsg,
                            html: ''
                        })
                    }
                }
            }
        },
        error: function (error) {
            console.log(error)
            // remove spinner loader
            removeLoaderFromButton(null, submitBtnElem)
            // enable submit btn
            enableSubmit(submitBtnElem)
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: errorMsg,
            })
        }
    });
}

function fetchProjectTemplateOptionsModal(submitBtnElem, containerId, templateId, accountId, isPublicTemplate, errorMsg) {
    let $modalContainer = $('#' + containerId)
    appendLoaderToButton(null, submitBtnElem)
    disableSubmit(submitBtnElem)

    $.ajax({
        type: 'POST',
        dataType: 'json',
        data: 'templateId=' + templateId + '&accountId=' + accountId + '&isPublic=' + isPublicTemplate,
        url: '/app/sec/projectTemplate/editProjectTemplateModal',
        async: true,
        success: function (data) {
            // remove spinner loader
            removeLoaderFromButton(null, submitBtnElem)
            // enable submit btn
            enableSubmit(submitBtnElem)

            if (data.output === 'error') {
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: errorMsg,
                })
            } else if (data.output) {
                $modalContainer.empty().append(data.output).modal({backdrop: 'static', keyboard: false})
            }
        },
        error: function (error) {
            console.log(error)
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: errorMsg,
            })
            // remove spinner loader
            removeLoaderFromButton(null, submitBtnElem)
            // enable submit btn
            enableSubmit(submitBtnElem)
        }
    });
}

function handleApplyTemplateCheckBoxClick(box, defaultTemplateSelectId) {
    const isChecked = isCheckboxChecked(box)
    const templateId = $(box).attr('data-templateId')
    const templateName = $(box).attr('data-templateName')
    if (isChecked) {
        $(box).val(templateId)
        const newOption = new Option(templateName, templateId, false, false);
        $('#' + defaultTemplateSelectId).append(newOption)
    } else {
        $(box).val('')
        $('#' + defaultTemplateSelectId + ' option[value="' + templateId + '"]').remove()
    }
}

function handleTemplateToLicenseRowEditClick(rowId, submitBtnId, editBtnElem) {
    toggleInputEntireRow(rowId);
    // show Save btn
    $('#' + submitBtnId).removeClass('hide');
    // hide edit btn
    $(editBtnElem).addClass('hide')
}

function handleTemplateToLicenseRowEditSubmit(rowId, editBtnId, submitBtnElem, errorMsg, invalidMsg, appendLoader = true) {
    if (appendLoader) {
        prependLoaderToButton(null, submitBtnElem)
    }
    // lock row
    toggleInputEntireRow(rowId);
    disableSubmit(submitBtnElem)

    let queryParams = getAllInputFromFormToQueryParams(rowId)
    $.ajax({
        headers: getCsrfHeader(),
        type: 'POST',
        data: queryParams,
        url: '/app/sec/projectTemplate/linkProjectTemplatesToLicense',
        async: true,
        success: function (data) {
            // remove spinner loader
            removeLoaderFromButton(null, submitBtnElem)
            // enable submit btn
            enableSubmit(submitBtnElem)
            if (data.output === 'error') {
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: invalidMsg,
                })
                // unlock row, error happened
                toggleInputEntireRow(rowId);
            } else {
                // show edit button
                $('#' + editBtnId).removeClass('hide')
                // hide submit button
                $(submitBtnElem).addClass('hide')
            }
        },
        error: function (error) {
            console.log(error)
            // remove spinner loader
            removeLoaderFromButton(null, submitBtnElem)
            // enable submit btn
            enableSubmit(submitBtnElem)
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: errorMsg,
            })
        }
    });
}

function showToolListAndCheckBenchMarkTool(indicatorList) {
    $(indicatorList).removeClass('hide')
    $(indicatorList).find('input[data-isBenchmarkIndicator="true"]').each(function() {
        const benchMarkTool = $(this)
        checkCheckbox(benchMarkTool)
    })
}

function hideToolListAndUncheckAllTools(indicatorList) {
    $(indicatorList).addClass('hide')
    const allCheckboxes = $(indicatorList).find('input[name="indicatorIds"]')
    if (allCheckboxes && allCheckboxes.length > 0) {
        const lastIndex = allCheckboxes.length - 1
        let i = 0
        allCheckboxes.each(function() {
            const tool = $(this)
            uncheckCheckbox(tool)
            if (i === lastIndex) {
                // run compatible check for last tool checkbox to remove the incompatible warning and enable checkbox
                compatibleIndicators(tool, null, $(indicatorList).attr('id'))
            }
            i++
        })
    }
}

/**
 * Check the value of entityClass select and show the correct tool list of that entity class
 * uncheck all tools (checkboxes) that are not shown
 * check the benchmark tool (that is hidden) for the shown tool list
 * @param entityClassSelect
 * @param toolListContainer
 */
function showCorrectToolListOnTemplateModal(entityClassSelect, toolListContainer) {
    let selectedEntityClass = $(entityClassSelect).val()
    if (selectedEntityClass) {
        selectedEntityClass = selectedEntityClass.toLowerCase()
        $(toolListContainer).find('table.indicators').each(function() {
            const indicatorList = $(this)
            if ($(indicatorList).attr('data-entityClass') && $(indicatorList).attr('data-entityClass').toLowerCase() === selectedEntityClass) {
                showToolListAndCheckBenchMarkTool(indicatorList)
            } else {
                hideToolListAndUncheckAllTools(indicatorList)
            }
        })
    }
}

/**
 * Check the value of entityClass select and show building type select if is 'building', otherwise hide it and empty value
 * @param entityClassSelect
 * @param bldTypeSelect
 */
function showBuildingTypeOnTemplateModal(entityClassSelect, bldTypeSelect) {
    let selectedEntityClass = $(entityClassSelect).val()
    if (selectedEntityClass) {
        selectedEntityClass = selectedEntityClass.toLowerCase()
        if (selectedEntityClass === 'building') {
            $(bldTypeSelect).closest('div').fadeIn()
        } else {
            // clean the selected option and hide
            $(bldTypeSelect).val('').change()
            $(bldTypeSelect).closest('div').fadeOut()
        }
    }
}

/**
 * Check the value of entityClass select and show Apply Default LCA switch if is 'building' or 'infrastructure' , otherwise hide it and empty value
 * @param entityClassSelect
 * @param applyDefaultLcaSwitch
 */
function showApplyDefaultLCAOnTemplateModal(entityClassSelect, applyDefaultLcaSwitch) {
    let selectedEntityClass = $(entityClassSelect).val()
    if (selectedEntityClass) {
        selectedEntityClass = selectedEntityClass.toLowerCase()
        if (selectedEntityClass === 'building' || selectedEntityClass === 'infrastructure') {
            $(applyDefaultLcaSwitch).closest('div').fadeIn()
        } else {
            // clean the selected option and hide
            uncheckSwitch(applyDefaultLcaSwitch)
            $(applyDefaultLcaSwitch).closest('div').fadeOut()
        }
    }
}

/**
 * Check the value of entityClass select and show product type question if is 'buildingProduct' , otherwise hide it and empty value
 * @param entityClassSelect
 * @param productTypeSelectId
 */
function showProductTypeOnTemplateModal(entityClassSelect, productTypeSelectId) {
    let selectedEntityClass = $(entityClassSelect).val()
    if (selectedEntityClass) {
        if (selectedEntityClass === 'buildingProduct') {
            $(productTypeSelectId).closest('div').fadeIn()
        } else {
            // clean the selected option and hide
            $(productTypeSelectId).val('').change()
            $(productTypeSelectId).closest('div').fadeOut()
        }
    }
}

function handleEntityClassChangeTemplateModal(entityClassSelect, toolListContainer, startWithToolSelect, bldTypeSelect, applyDefaultLcaSwitch, entityTemplateSelectId, productTypeSelectId) {
    showCorrectToolListOnTemplateModal(entityClassSelect, toolListContainer)
    showBuildingTypeOnTemplateModal(entityClassSelect, bldTypeSelect)
    showApplyDefaultLCAOnTemplateModal(entityClassSelect, applyDefaultLcaSwitch)
    showProductTypeOnTemplateModal(entityClassSelect, productTypeSelectId)
    // empty all tools added to start with tool select
    $(startWithToolSelect).empty().append('<option></option>')
    clearEntityTemplateSelect(entityTemplateSelectId)
}

function handleProjectTemplateIndicatorCheckBoxClick(checkboxElem, indicatorSelectId, thisIndicatorId, thisIndicatorName, clearTemplateOptionDivId, toolListDivId, incompatibleWarningText, entityTemplateSelectId, runCompatibleCheck = true) {
    let $checkbox = $(checkboxElem)
    let $select = $('#' + indicatorSelectId)
    let $existingOption = $select.find('option[value="' + thisIndicatorId + '"]')
    if (runCompatibleCheck) {
        compatibleIndicators(checkboxElem, incompatibleWarningText, toolListDivId)
    }
    if ($checkbox.is(':checked')) {
        // add new tool option to the select when checkbox is checked
        if (!$existingOption.length) {
            let newOption = '<option value="' + thisIndicatorId + '">' +  thisIndicatorName + '</option>'
            $select.append(newOption).trigger('change')
        }
    } else {
        // remove the existing tool option in the select when checkbox is unchecked
        if ($existingOption.length) {
            if ($existingOption.is(':selected')) {
                $('#' + clearTemplateOptionDivId).empty()
            }
            $existingOption.remove()
        }
    }
    if (entityTemplateSelectId) {
        clearEntityTemplateSelect(entityTemplateSelectId)
    }
}

/**
 * Clear and destroy the entity templates select on project template modal
 * @param divId
 */
function clearEntityTemplateSelect(divId) {
    if (divId) {
        divId = '#' + divId
        $(divId).find('a').removeClass('hide').fadeIn()
        $(divId).find('select').select2('destroy')
        $(divId).find('.entityTemplatesSelect').empty().hide()
    }
}

// do not remove
function handleProjectTemplateOpenCarbonDesignerSwitchClick(switchElem, expandInputSwitchId, querySelect2Id, disabledQueryHelpText) {
    if (switchElem) {
        if ($(switchElem).is(':checked')) {
            uncheckSwitch('#' + expandInputSwitchId, true)
            clearSelect(querySelect2Id, true)
            if (disabledQueryHelpText) {
                showSmoothly(disabledQueryHelpText)
            }
        } else {
            enableSwitch(null, expandInputSwitchId)
            enableSelect(null, querySelect2Id)
            if (disabledQueryHelpText) {
                hideSmoothly(disabledQueryHelpText)
            }
        }
    }
    toggleValueCheckBox(switchElem)
}

function checkIfIndicatorUsingLcaParametersIsSelected(parentElemId, nameAttributeOfInput) {
    let useLcaParameters = false
    $('#' + parentElemId + ' input[name="' + nameAttributeOfInput + '"]').each(function () {
        // check list of selected tools if there are any tools using lca params
        if ($(this).attr('data-isusinglcaparameters') === 'true' && $(this).is(':checked')) {
            useLcaParameters = true
        }
    })
    return useLcaParameters
}

function checkSelectToEnableSubmit(selectElem, submitBtnId) {
    let $submitBtn = $('#' + submitBtnId)
    // check if a select input has any selected option, if it has => enable submit btn, otherwise, disable it
    if ($(selectElem).find('option:selected').length) {
        if ($submitBtn.attr('disabled')) {
            enableSubmit($submitBtn)
        }
    } else {
        if (!$submitBtn.attr('disabled')) {
            disableSubmit($submitBtn)
        }
    }
}

function prependLoaderToButton(buttonId, buttonElem) {

    if (buttonId) {
        buttonElem = $('#' + buttonId)
    }
    if (buttonElem) {
        $('<i id="quantity_share_spinner" style="margin-right: 4px" class="fas fa-circle-notch fa-spin"></i>').prependTo(buttonElem)
    }
}

function appendLoaderToButton(buttonId, buttonElem) {
    if (buttonId) {
        buttonElem = $('#' + buttonId)
    }

    if (buttonElem) {
        $('<i id="quantity_share_spinner" style="margin-left: 4px" class="fas fa-circle-notch fa-spin"></i>').appendTo(buttonElem)
    }
}

function removeLoaderFromButton(buttonId, buttonElem) {
    if (buttonId) {
        buttonElem = $('#' + buttonId)
    }

    if (buttonElem) {
        $(buttonElem).children('#quantity_share_spinner').remove()
    }
}

function isSubmitDisabled(buttonElem) {
    return $(buttonElem).attr('disabled') === 'disabled'
}

function disableSubmit(buttonElem, buttonId) {
    disableButton(buttonElem, buttonId)
}

function enableSubmit(buttonElem, buttonId) {
    enableButton(buttonElem, buttonId)
}

function disableButton(buttonElem, buttonId) {
    if (buttonId) {
        buttonElem = $('#' + buttonId)
    }
    if (buttonElem) {
        $(buttonElem).attr('disabled', 'disabled').css('pointer-events', 'none')
        if (!$(buttonElem).hasClass('removeClicks')) {
            $(buttonElem).addClass('removeClicks')
        }
    }
}

function enableButton(buttonElem, buttonId) {
    if (buttonId) {
        buttonElem = $('#' + buttonId)
    }
    if (buttonElem) {
        $(buttonElem).removeAttr('disabled').css('pointer-events', 'auto').removeClass('removeClicks')
    }
}

function deleteProjectTemplate(templateId, accountId, isPublicTemplate, warningTitle, warningText, successText, errorMsg, yes, cancel, removeRowId, replaceContent, secondOutputKey) {
    Swal.fire({
        title: warningTitle,
        text: warningText,
        icon: "warning",
        confirmButtonText: yes,
        cancelButtonText: cancel,
        confirmButtonColor: "red",
        showCancelButton: true,
        reverseButtons: true,
        showLoaderOnConfirm: true,
        preConfirm: function () {
            return new Promise(function (resolve, reject) {
                $.ajax({
                    headers: getCsrfHeader(),
                    type: 'POST',
                    url: '/app/sec/projectTemplate/removeProjectTemplate',
                    data: 'templateId=' + templateId + '&accountId=' + accountId + '&isPublic=' + isPublicTemplate,
                    async: true,
                    success: function (data, textStatus) {
                        if (data.output === 'ok') {
                            if (removeRowId) {
                                // remove the row
                                $('#' + removeRowId).remove()
                                // also replace content from secondary output
                                if (replaceContent && secondOutputKey) {
                                    let secondaryOutput = data[secondOutputKey]
                                    if (secondaryOutput) {
                                        $('#' + replaceContent).replaceWith(secondaryOutput)
                                    }
                                }

                                // show success swal
                                Swal.fire({
                                    icon: 'success',
                                    title: successText,
                                    html: ''
                                })
                            }
                        } else if (data.output === 'error') {
                            Swal.fire({
                                icon: 'error',
                                title: 'Oops...',
                                text: errorMsg,
                            })
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        Swal.fire({
                            icon: 'error',
                            title: 'Oops...',
                            text: errorMsg,
                        })
                    }
                })
            })
        },
        allowOutsideClick: false
    })
}

function maskSelectWithSelect2(allowClear = null, placeHolder = null, onlyMaskSelectWithClass = null, selectId = null) {
    let selector = 'select'
    if (onlyMaskSelectWithClass) {
        selector = '.' + onlyMaskSelectWithClass
    } else if (selectId) {
        selector = '#' + selectId
    }
    if (placeHolder) {
        if (allowClear) {
            $(selector).select2({
                containerCssClass:":all:",
                allowClear: true,
                placeholder: placeHolder
            }).maximizeSelect2Height();
        } else {
            $(selector).select2({
                containerCssClass:":all:",
                placeholder: placeHolder
            }).maximizeSelect2Height();
        }
    }
}

function maskAllSelectInDivWithSelect2(divId, allowClear = null, placeHolder = null) {
    let selector = '#' + divId + ' select'
    if (placeHolder) {
        if (allowClear) {
            $(selector).select2({
                containerCssClass:":all:",
                allowClear: true,
                placeholder: placeHolder
            }).maximizeSelect2Height();
        } else {
            $(selector).select2({
                containerCssClass:":all:",
                placeholder: placeHolder
            }).maximizeSelect2Height();
        }
    }
}

function removeBorderOfSelect2(selectId, selectElem) {
    if (selectId) {
        selectElem = $('#' + selectId)
    }
    if (selectElem) {
        const $target = $(selectElem).next().find('.select2-selection')
        if ($target && $target.hasClass('redBorder')) {
            $target.removeClass('redBorder').trigger('change')
        }
    }
}

function addBorderToSelect2(selectId, selectElem) {
    if (selectId) {
        selectElem = $('#' + selectId)
    }

    if (selectElem) {
        const $target = $(selectElem).next().find('.select2-selection')
        if ($target && !$target.hasClass('redBorder')) {
            $target.addClass('redBorder').trigger('change')
        }
    }
}

// we have green component in UITaglib, use this for it
function checkSwitch($switch) {
    checkCheckbox($switch)
}

// we have green component in UITaglib, use this for it
function uncheckSwitch($switch, alsoDisable) {
    uncheckCheckbox($switch)
    if (alsoDisable) {
        disableSwitch($switch)
    }
}

// we have green component in UITaglib, use this for it
function enableSwitch(switchElem, switchId) {
    if (switchId) {
        switchElem = $('#'+ switchId)
    }
    if (switchElem) {
        $(switchElem).prop('disabled', false).css('pointer-events', 'auto').removeClass('removeClicks')
        $(switchElem).next().css('pointer-events', 'auto').removeClass('removeClicks')
    }
}

// we have green component in UITaglib, use this for it
function disableSwitch(switchElem, switchId) {
    if (switchId) {
        switchElem = $('#'+ switchId)
    }
    if (switchElem) {
        $(switchElem).prop('disabled', true).css('pointer-events', 'none').addClass('removeClicks')
        $(switchElem).next().css('pointer-events', 'none').addClass('removeClicks')
    }
}

function checkCheckbox(checkbox, alsoDisable) {
    if (checkbox) {
        let $checkbox = $(checkbox)
        // check
        $checkbox.prop('checked', true)
        if (alsoDisable) {
            disableCheckbox($checkbox, null)
        }
    }
}

function uncheckCheckbox(checkbox, alsoDisable) {
    if (checkbox) {
        let $checkbox = $(checkbox)
        // uncheck
        $checkbox.prop('checked', false)
        if (alsoDisable) {
            disableCheckbox($checkbox, null)
        }
    }
}

function disableCheckbox(checkboxElem, checkboxId) {
    if (checkboxId) {
        checkboxElem = $('#'+ checkboxId)
    }
    if (checkboxElem) {
        $(checkboxElem).prop('disabled', true)
    }
}

function enableCheckbox(checkboxElem, checkboxId) {
    if (checkboxId) {
        checkboxElem = $('#'+ checkboxId)
    }
    if (checkboxElem) {
        $(checkboxElem).prop('disabled', false)
    }
}

function clearSelect(selectId, alsoDisable) {
    if (selectId) {
        let $select = $('#' + selectId)
        // clear selected option for select masked with select2 or normal select
        $select.val('').trigger('change')
        if (alsoDisable) {
            disableSelect($select, null)
        }
    }
}

function disableSelect(selectElem, selectId, alsoTriggerChange = true) {
    if (selectId) {
        selectElem = $('#'+ selectId)
    }
    if (selectElem) {
        $(selectElem).prop('disabled', true)
        if (alsoTriggerChange) {
            $(selectElem).trigger('change')
        }
    }
}

function enableSelect(selectElem, selectId, alsoTriggerChange = true) {
    if (selectId) {
        selectElem = $('#'+ selectId)
    }
    if (selectElem) {
        $(selectElem).prop('disabled', false)
        if (alsoTriggerChange) {
            $(selectElem).trigger('change')
        }
    }
}

function toggleSection(buttonId, id) {
    var section = $("#" + id);
    $(section).fadeToggle(200);
    $(section).toggleClass('collapsed');
    $("#" + buttonId).toggleClass('icon-chevron-right icon-chevron-down');
}

function setValueToInput(inputElem, inputId, value) {
    if (inputId) {
        inputElem = $('#'+ inputId)
    }
    if (inputElem) {
        $(inputElem).val(value)
    }
}

function setValueToSelect(selectElem, selectId, value) {
    if (selectId) {
        selectElem = $('#'+ selectId)
    }
    if (selectElem) {
        $(selectElem).val(value).trigger('change')
    }
}

function enableAllInputInDiv(divElem, divId) {
    if (divId) {
        divElem = $('#'+ divId)
    }
    if (divElem) {
        $(divElem).find('select, input').each(function() {
            const $this = $(this)
            if ($this.prop('disabled')) {
                $this.prop('disabled', false)
            }
            // we have greenSwitch component in taglib
            if ($(this).attr('data-switch') === 'true') {
                enableSwitch(this)
            }
        })
    }
}

function disableAllInputInDiv(divElem, divId) {
    if (divId) {
        divElem = $('#'+ divId)
    }
    if (divElem) {
        $(divElem).find('select, input').each(function() {
            const $this = $(this)
            if (!$this.prop('disabled')) {
                $this.prop('disabled', true)
            }
            // we have greenSwitch component in taglib
            if ($(this).attr('data-switch') === 'true') {
                disableSwitch(this)
            }
        })
    }
}

function clearAllInputInDiv(divElem, divId) {
    if (divId) {
        divElem = $('#'+ divId)
    }
    if (divElem) {
        $(divElem).find('select, input').each(function() {
            $(this).val('')
        })
    }
}

// use this when checkbox is not binded with grails
function toggleValueCheckBox(checkboxElem, checkboxId) {
    if (checkboxId) {
        checkboxElem = $('#'+ checkboxId)
    }
    if (checkboxElem) {
        const isChecked = $(checkboxElem).is(':checked')
        if (isChecked) {
            $(checkboxElem).val('true')
        } else {
            $(checkboxElem).val('false')
        }
    }
}

// returns true if checked, false if unchecked, null if checkbox not found
function isCheckboxChecked(checkboxElem, checkboxId) {
    let isChecked = null
    if (checkboxId) {
        checkboxElem = $('#'+ checkboxId)
    }
    if ($(checkboxElem).length) {
        isChecked = $(checkboxElem).is(':checked')
    }
    return isChecked
}
// fadeOutPace: slow / fast / milisecs
function appendSmoothly(divId, divElem, newContent, fadeOutPace = 'slow') {
    if (divId) {
        divElem = $('#' + divId)
    }

    if (divElem) {
        $(divElem).fadeOut(fadeOutPace, function() {
            $(this).empty().append(newContent)
            $(this).fadeIn('slow')
        });
    }
}

function showSmoothly(id, elem) {
    if (id) {
        elem = $('#' + id)
    }

    if (elem && $(elem).is(':hidden')) {
        $(elem).fadeIn()
    }
}

function hideSmoothly(id, elem) {
    if (id) {
        elem = $('#' + id)
    }

    if (elem && $(elem).is(':visible')) {
        $(elem).fadeOut()
    }
}

/**
 * Concatenate query tab names until all tabs fit in one row
 * @returns {boolean} true if query tabs concatenation was run
 */
function concatenateQueryTabs() {
    const $queryText = $('.queryDisplayedName')

    // concatenate string if tabs get to 2 rows
    if (!queryNavOk()) {
        let longestTextLength = 0
        $queryText.each(function() {
            const len = $(this).text().length
            if (len > longestTextLength) {
                longestTextLength = len
            }
        })
        let runConcat = true
        const startTime = Date.now()
        let loopKilledByTimeout = false
        let limit = longestTextLength
        let concatLength = 0
        // keep trimming query tabs until all tabs are on one row
        while (runConcat) {
            limit--
            concatLength = limit - 3
            $queryText.each(function() {
                // concat only the non-active tabs
                if ($(this).attr('data-activeTab') !== 'true') {
                    const originalText = $(this).attr('data-originalText')
                    // concat text that has length over limit
                    if (originalText.length >= limit) {
                        let subString = originalText.substring(0, concatLength)
                        const lastCharOfSubstring = subString.slice(-1)
                        if (isNonWordCharacter(lastCharOfSubstring)) {
                            // last char is non-word char, remove it along with other non-word characters
                            subString = subString.replace(/[_\W]*$/g, '')
                        } else {
                            // last char is a word char => keep checking if string is cut in the middle of a meaningful word.
                            const nextCharAfterSubString = originalText.slice(concatLength, concatLength + 1)
                            if (!isNonWordCharacter(nextCharAfterSubString)) {
                                // remove the whole word at the end of string and any non-word character before it.
                                subString = subString.replace(/[_\W]+[\w]*$/g, '')
                            }
                        }
                        $(this).text(subString + '...')
                    }
                }
            })
            if (queryNavOk()) {
                runConcat = false
            }

            if (Date.now() - startTime > 3000) {
                runConcat = false
                loopKilledByTimeout = true
            }
        }

        if (loopKilledByTimeout) {
            restoreOriginalQueryTabText()
            return false
        }
        return true
    }
    return false
}

function queryNavOk() {
    // check if all query tabs are on 1 row
    let ok = true
    const navHeight = $('.nav-tabs').height()
    let tabHeight = 0
    $('.navInfo').each(function() {
        tabHeight = $(this).height()
    })

    if (navHeight > tabHeight * 1.2) {
        ok = false
    }
    return ok
}

function isNonWordCharacter(testChar) {
    let bingo = false
    const regex = /[_\W]/g
    if (regex.test(testChar)) {
        bingo = true
    }
    return bingo
}

function restoreOriginalQueryTabText() {
    const $queryText = $('.queryDisplayedName')
    $queryText.each(function() {
        // concat only the non-active tabs
        if ($(this).attr('data-activeTab') !== 'true') {
            const originalText = $(this).attr('data-originalText')
            $(this).text(originalText)
        }
    })
}

/**
 * Scroll page to view element if it is not visible
 * @param element
 * @returns {boolean} true if the scroll ran
 */
function scrollToView(element) {
    let offset = element.offset().top;
    if (!element.is(":visible")) {
        element.css({"visibility": "hidden"}).show();
        offset = element.offset().top;
        element.css({"visibility": "", "display": ""});
    }

    let visible_area_start = $(window).scrollTop();
    let visible_area_end = visible_area_start + window.innerHeight;

    if (offset < visible_area_start || offset > visible_area_end) {
        // Not in view so scroll to it
        $('html,body').animate({scrollTop: offset - window.innerHeight / 3}, 1000);
        return false;
    }
    return true;
}

/**
 * An input can be for a question or additional question
 * @param name
 * @returns {string} the additionalQuestionId if the input is for an addQ, or questionId if input is for a question
 */
function getQuestionIdOrAdditionalQuestionIdFromInputName(name) {
    let result = ''
    if (name && name.includes('.')) {
        const splits = name.split('.')

        if (splits.length > 1) {
            const questionId = splits[1]
            const additionalQuestionId = extractAdditionalQuestionIdFromQuestionIdString(questionId)

            if (additionalQuestionId) {
                result = additionalQuestionId
            } else {
                result = questionId
            }
        }
    }
    return result
}

/**
 * Extract the additional question Id in the name of input
 * Currently it is in format {sectionId}.{questionId}_additional_{additionalQuestionId}.{resourceId}
 * @param name
 * @returns {string}
 */
function getAdditionalQuestionIdFromInputName(name) {
    let additionalQuestionId = ''
    if (name && name.includes('.')) {
        const splits = name.split('.')
        if (splits.length > 1) {
            const questionId = splits[1]
            additionalQuestionId = extractAdditionalQuestionIdFromQuestionIdString(questionId)
        }
    }
    return additionalQuestionId
}

/**
 * Currently name of input is in format {sectionId}.{questionId}_additional_{additionalQuestionId}.{resourceId}
 * So need to pass into this method the second part {questionId}_additional_{additionalQuestionId} and this method
 * @param questionId
 * @returns {string} the additionalQuestionId
 */
function extractAdditionalQuestionIdFromQuestionIdString(questionId) {
    let additionalQuestionId = ''

    if (questionId) {
        const split = questionId.split('_additional_')
        if (split.length > 1) {
            additionalQuestionId = split[1]
        }
    }
    return additionalQuestionId
}

/**
 * A hack to switch message in popover
 * @param $elem
 * @param newMessage
 */
function switchHoverMessage($elem, newMessage) {
    if ($elem) {
        $($elem).on('mouseenter', function () {
            const parent = $(this).parent()
            const popover = parent.find('.popover')
            const offset = ($($elem).width() - $(popover).width()) / 2
            let leftPos = findLeft(this) + offset
            if (leftPos <= 10) {
                leftPos = 10
            }
            popover.css('left', leftPos)
            parent.find('.popover-content').text(newMessage)
        })
    }
}

/**
 * Find relative left position of element to browser
 * @param element
 * @returns {number}
 */
function findLeft(element) {
    return $(element).offset().left
}

/**
 * Click save button on Query page
 */
function clickSaveQuery() {
    $('#save').click()
}

/**
 * Check if row is parent construction
 * @param row
 * @returns {boolean}
 */
function isParentConstructionRow(row) {
    return !!$(row).attr('data-parentIdentifier') // if attr has a value >> true, else false
}

/**
 * Check if input can be edited. We have inputs that can't be edited (decided in backend),
 * those that are not editable are marked with readonly attribute
 * @param input
 * @param addQId
 * @returns {boolean}
 */
function isEditableInputAddQ(input, addQId) {
    if (isHiddenInput(input) && hasTextLinkForHiddenInputAddQ(input, addQId)) {
        // hidden input is editable if it has editable link next to it
        return hasEditableTextLinkForHiddenInputAddQ(input, addQId)
    }
    return isEditable(input)
}

function isCheckBoxInput(input) {
    return $(input).attr('type') === 'checkbox'
}

/**
 * Additional questions are in td, and if it's hidden input then it has a anchor link with text represent in same td with same
 * additional question id
 * @param input
 * @param addQId
 * @returns {boolean}
 */
function hasTextLinkForHiddenInputAddQ(input, addQId) {
    // we take the row to be safe
    return $(input).closest('tr').find('a[data-additionalquestionid="' + addQId + '"]').length > 0
}

/**
 * Additional questions are in td, and if it's hidden input then it has a anchor link with text represent in same td with same
 * additional question id
 * Checks if said link is editable
 * @param input
 * @param addQId
 * @returns {boolean|null}
 */
function hasEditableTextLinkForHiddenInputAddQ(input, addQId) {
    const textLink = $(input).closest('tr').find('a[data-additionalquestionid="' + addQId + '"]')
    if (textLink.length > 0) {
        return isEditable(textLink)
    }
    return false
}

/**
 * This check all possible ways that an element can be visually disabled
 * @param element
 * @returns {null|boolean}
 */
function isEditable(element) {
    if (element) {
        return $(element).get(0).hasAttribute('readonly') !== true &&
            $(element).attr('disabled') !== 'disabled' &&
            $(element).attr('disabled') !== 'true' &&
            $(element).attr('disabled') !== true &&
            !$(element).hasClass('disabled') &&
            !$(element).hasClass('lockedDatasetQuestion') &&
            !$(element).hasClass('removeClicks')
    }
    return null
}

function isHiddenInput(input) {
    return $(input).attr('type') === 'hidden'
}

function isSelectInput(element) {
    return $(element).is('select')
}

/**
 * Backend reads value for additional question checkbox a bit differently.
 * Ultimately, we get value of these boxes by checking if it's checked
 * @param input
 * @returns {null | boolean}
 */
function getCheckBoxAdditionalAnswer(input) {
    if (isCheckBoxInput(input)) {
        return isCheckboxChecked(input)
    }
    return null
}

/**
 * checkbox input for additional questions work so that:
 * if answer is true (visible checkbox is checked), the hiddenCheckbox is disabled
 * if answer is false (visible checkbox is unchecked), the hiddenCheckbox is not disabled
 * so to update the value we want, just decide if should click of the visible box and let {@see toggleHiddenInput} do the work
 *
 * @param input
 * @param {boolean} newAnswer true if checkbox should be checked
 */
function handleUpdateAdditionalCheckBoxAnswer(input, newAnswer) {
    if (isCheckBoxInput(input)) {
        const isChecked = isCheckboxChecked(input)
        const doClick = (newAnswer === true && isChecked === false) || (newAnswer === false && isChecked === true)
        if (doClick) {
            $(input).click()
        }
    }
}

function disableButtonCopyDesign() {
    if ($(".selectDesignToImport").val()) {
        $("#copyAll").attr("disabled", false);
        $("#copyOne").attr("disabled", false);
    } else {
        $("#copyAll").attr("disabled", true);
        $("#copyOne").attr("disabled", true);
    }
}

/**
 * Show / hide a div and toggle the plus / minus sign accordingly
 * @param link the anchor link must have the plus/minus sign as child
 * @param targetSelector the div element to be shown/hidden
 */
function toggleDivWithPlusMinusSign(link, targetSelector) {
    const target = $(targetSelector);
    const icon = $(link).find('i');

    $(target).each(function () {
        if ($(icon).hasClass("fa-minus-square")) {
            $(this).fadeOut();
        } else {
            $(this).fadeIn().removeClass('hide')
        }
    });
    $(icon).toggleClass('fa-minus-square fa-plus-square');
}

/**
 * We save a localized 'Something went wrong' message on layouts/main.gsp
 * Can use to get msg for error ajax response
 * @returns {String}
 */
function getSomethingWentWrongMsg() {
    let msg = $('#somethingWentWrongLocalized').text()
    if (!msg) {
        msg = 'Something went wrong!'
    }
    return msg
}

/**
 * Fire an error "something went wrong" swal
 */
function somethingWrongErrorSwal() {
    Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: getSomethingWentWrongMsg(),
    })
}

function isBenchmarkIndicator(input) {
    return $(input).attr('data-isBenchmarkIndicator') === 'true'
}

/**
 * Extract a list of selected indicatorIds (from checked checkboxes in the tool list) except for the benchmark tools
 * @param $modal
 * @param toolInputName
 * @returns {*[]}
 */
function extractSelectedTools($modal, toolInputName) {
    let selectedTools = []
    if ($modal && toolInputName) {
        $($modal).find('input[type="checkbox"]').filter('[name="' + toolInputName + '"]').each(function () {
            if (isCheckboxChecked(this) && !isBenchmarkIndicator(this)) {
                selectedTools.push($(this).val())
            }
        })
    }
    return selectedTools
}

function extractSelectedEntityClass($modal) {
    return $($modal).find('select[name="entityClass"]').val()
}

/**
 * This is used in 2 different places: while editing a template and while creating a new project
 * @param btn
 * @param templateId
 * @param missingToolWarning warning that at least one tool must be selected
 * @param onNewProjectModal true if it's on new project modal
 * @param entityClass only gets populated if it's on new project modal, otherwise is 'null'
 * @param licenseIds a query string with licenseIds
 */
function fetchEntityTemplatesSelect(btn, templateId, missingToolWarning, onNewProjectModal, entityClass, licenseIds) {
    const selectContainer = $(btn).siblings('.entityTemplatesSelect')
    let selectedTools = []
    let selectedEntityClass

    if (onNewProjectModal) {
        const modalBody = $(btn).closest('#quickStartContent')
        selectedTools = extractSelectedTools(modalBody, 'indicatorIdList')
        selectedEntityClass = entityClass
    } else {
        const modalBody = $(btn).closest('.projectTemplateModalBody')
        selectedTools = extractSelectedTools(modalBody, 'indicatorIds')
        selectedEntityClass = extractSelectedEntityClass(modalBody)
        // enable save
        const $saveBtn = $('#save-' + templateId)
        if (isSubmitDisabled($saveBtn)) {
            enableSubmit($saveBtn)
        }
    }

    if (selectedTools.length > 0 && selectedEntityClass) {
        $(btn).hide()
        selectContainer.empty().append('<i style="margin-right: 4px" class="fas fa-circle-notch fa-spin oneClickColorScheme"></i>').fadeIn()

        $.ajax({
            type: 'POST',
            async: true,
            data: '&templateId=' + templateId + '&indicatorId=' + selectedTools.join('&indicatorId=') + '&entityClass=' + selectedEntityClass + '&licenseIds=' + licenseIds,
            url: '/app/sec/projectTemplate/getEntityTemplateSelect',
            success: function (data) {
                if (data.output) {
                    // replace the current select
                    $(selectContainer).empty().append(data.output)
                }
            },
            error: function () {
                somethingWrongErrorSwal()
                $(btn).fadeIn()
                selectContainer.hide()
            }
        });
    } else {
        Swal.fire({
            icon: 'warning',
            text: missingToolWarning
        })
    }
}

function showElementForEditing(element, id) {
    $(element).hide();
    $('#' + id).hide()
    var editField = $(element).next();
    $(editField).fadeIn().removeClass('hidden');
}

/**
 * Search function of select2 modified. Change search mechanism to searching the children of optgroup instead of the optgroup
 * @param params
 * @param data
 */
function matchStart(params, data) {
    // If there are no search terms, return all of the data
    if ($.trim(params.term) === '') {
        return data;
    }

    // Skip if there is no 'children' property
    if (typeof data.children === 'undefined') {
        return null;
    }

    // `data.children` contains the actual options that we are matching against
    var filteredChildren = [];
    $.each(data.children, function (idx, child) {
        if (child.text.toUpperCase().indexOf(params.term.toUpperCase()) > -1) {
            filteredChildren.push(child);
        }
    });

    // If we matched any of the timezone group's children, then set the matched children on the group
    // and return the group object
    if (filteredChildren.length) {
        var modifiedData = $.extend({}, data, true);
        modifiedData.children = filteredChildren;

        // You can return modified objects from here
        // This includes matching the `children` how you want in nested data sets
        return modifiedData;
    }

    // Return `null` if the term should not be displayed
    return null;
}

/**
 * Prettify the optgroup in select2, can show / hide the options of each optgroup on click
 * @param selector
 * @param optgroupState a global object to store the states
 * @param optGroupTextColor
 */
function prettifySelect2Optgroup(selector, optgroupState, optGroupTextColor) {
    //Set on click event to optgroup label
    $("body").on('click', '.select2-container--open .select2-results__group', function () {
        $(this).siblings().toggle();
        let id = $(this).closest('.select2-results__options').attr('id');
        let index = $('.select2-results__group').index(this);
        optgroupState[id][index] = !optgroupState[id][index];
    })

    $(selector).on('select2:open', function () {
        $('.select2-dropdown--below').css('opacity', 0);
        setTimeout(() => {
            let groups = $('.select2-container--open .select2-results__group');
            let id = $('.select2-results__options').attr('id');
            let childs = $('.select2-results__option');

            $.each(childs, (index, v) => {
                if ($(v).attr("aria-disabled") == "true") {
                    $(v).css({"display": "block", "font-weight": "bold", "padding": "5px", "color": "black"})
                }
            })

            if (!optgroupState[id]) {
                optgroupState[id] = {};
            }
            $.each(groups, (index, v) => {
                $(v).css("color", optGroupTextColor)
                optgroupState[id][index] = optgroupState[id][index] || false;
                optgroupState[id][index] ? $(v).siblings().show() : $(v).siblings().hide();
            })
            $('.select2-dropdown--below').css('opacity', 1);
        }, 0);
    })
}

function isVisible(element) {
    return $(element).is(':visible')
}

/**
 * scroll to an element with id target
 * @param target
 * @param alwaysScroll set to true if always scroll without the check if element is already in viewport
 * @return {boolean} true if the scroll ran
 */
function scrollToElement(target, alwaysScroll = false) {
    if (target) {
        if (alwaysScroll || !isInViewPort(target)) {
            const targetScroll = $(target).offset().top - 150
            $('html,body').animate({
                scrollTop: targetScroll + 'px'
            }, 1500, 'swing');
            return true
        }
    }
    return false
}

function isInViewPort(element) {
    const elementTop = $(element).offset().top;
    const elementBottom = elementTop + $(element).outerHeight();

    const viewportTop = $(window).scrollTop();
    const viewportBottom = viewportTop + $(window).height();

    return elementBottom > viewportTop && elementTop < viewportBottom;
}

/**
 * Highlight the closest question or section as parent of element
 * also
 * @param element
 */
function highlightQuestionOrSection(element) {
    if (element) {
        const question = $(element).closest('.queryquestion')
        if (question.length > 0) {
            highLightElementTimeOut(question)
        } else {
            const section = $(element).closest('.querysection')
            if (section.length > 0) {
                highLightElementTimeOut(section)
            }
        }
    }
}

function isDefined(value) {
    return !(value === undefined || value === null || value === 'null')
}

/**
 * Send ajax to remove a license feature
 * @param id
 * @param name
 */
function deleteFeature(id, name) {
    const html = `
        <div>
            <div class="margin-bottom-2">Removing feature <b>${name}</b>.</div>
            <div>This action <b style="color: #DA4F49">removes permanently</b> the license feature in the database and from all linked licenses or license templates.</div>
        </div> 
    `

    Swal.fire({
        icon: 'warning',
        title: 'Irreversible action',
        html: html,
        confirmButtonText: 'Confirm',
        confirmButtonColor: '#DA4F49',
        customClass: 'swal-medium',
        showCancelButton: true
    }).then((result) => {
        if (result.isConfirmed) {
            Swal.fire({
                icon: 'error',
                title: 'Are you sure?',
                html: 'Many licenses can be affected and this action cannot be undone.',
                confirmButtonText: 'Yes!',
                confirmButtonColor: '#DA4F49',
                showCancelButton: true,
                preConfirm: function () {
                    return new Promise(function (resolve, reject) {
                        Swal.showLoading()
                        $.ajax({
                            url: '/app/sec/license/deleteLicenseFeature',
                            async: true,
                            type: 'POST',
                            data: 'id=' + id,
                            success: function (data) {
                                resolve()
                                location.reload();
                            },
                            error: function (data) {
                                somethingWrongErrorSwal()
                            }
                        });
                    })
                }
            })
        }
    })
}

/**
 * Run validation the design name before importing JSON design. The name must be new and unique, and also do not contains invalid characters
 * @param element
 * @param errorMessage
 * @param buttonId
 * @param existingNamesStr
 * @param duplicateWarningElem
 */
function checkDesignNameForJsonImport(element, errorMessage, buttonId, existingNamesStr, duplicateWarningElem) {
    let nameOk = true
    if (existingNamesStr) {
        const names = existingNamesStr.split(';ocl')
        const userInputName = $(element).val().trim()
        nameOk = names.every(existingName => userInputName !== existingName)
        if (!nameOk) {
            disableButton(null, buttonId)
            $(duplicateWarningElem).removeClass('hidden')
        } else {
            enableButton(null, buttonId)
            $(duplicateWarningElem).addClass('hidden')
        }
    }

    if (nameOk) {
        validateName(element, errorMessage, buttonId, false)
    }
}

function getCsrfHeader() {
    const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");

    return (token && header) ? {[header]: token} : {};
}

/**
 * Check if arr is an array and it's not empty
 * @param arr
 * @returns {boolean}
 */
function isArrayNotEmpty(arr) {
    return Array.isArray(arr) && arr.length > 0
}

function castStringToBoolean(str) {
    if (str && typeof str === 'string') {
        return str.toLowerCase() === 'true'
    }
    return false
}

/**
 * On query form, we have a hidden input field to indicate that calculation need to be triggered.
 * This method check if such input is available, and if it's not enabled => enable it (set its value to true)
 */
function enableCalculationOnQueryForm() {
    if ($(TRIGGER_CALCULATION_ELEMENT_ID).length > 0 && !castStringToBoolean($(TRIGGER_CALCULATION_ELEMENT_ID).val())) {
        console.log("CALCULATION WILL BE TRIGGERED")
        $(TRIGGER_CALCULATION_ELEMENT_ID).val('true')
    }
}

function downloadFile(url, element) {

    if (element) {
        removeClicks(element);
    }

    $.ajax({
        url: url,
        cache: false,
        xhr: function () {
            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 2) {
                    if (xhr.status == 200) {
                        xhr.responseType = "blob";
                    } else {
                        xhr.responseType = "text";
                    }
                }
            };
            return xhr;
        },
        success: function (data, textStatus, jqXHR) {
            let fileName = retrieveFileNameFromXHR(jqXHR);

            //Convert the Byte Data to BLOB object.
            var blob = new Blob([data], { type: data.type });

            if (typeof window.navigator.msSaveBlob !== 'undefined') {
                // IE workaround for "HTML7007: One or more blob URLs were revoked by closing the blob for which they were created. These URLs will no longer resolve as the data backing the URL has been freed."
                window.navigator.msSaveBlob(blob, filename);
            } else {
                var url = window.URL || window.webkitURL;
                link = url.createObjectURL(blob);
                var a = $("<a />");
                a.attr("download", fileName);
                a.attr("href", link);
                $("body").append(a);
                a[0].click();
                $("body").remove(a);
            }

            $(element).removeClass('removeClicks');
        }
    });
}

function retrieveFileNameFromXHR(xhr) {
    let filename = "";
    let disposition = xhr.getResponseHeader('Content-Disposition');

    if (disposition && disposition.indexOf('attachment') !== -1) {
        var filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
        var matches = filenameRegex.exec(disposition);
        if (matches != null && matches[1]) filename = matches[1].replace(/['"]/g, '');
    }
    return filename;
}