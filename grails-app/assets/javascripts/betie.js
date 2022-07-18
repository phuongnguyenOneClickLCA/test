function dataLoadingFromFile(filePath) {
    let workbookJson;
    let mandatoryDataInput;
    $( "#dataLoadingFromFile" ).parent( ".querysection" ).attr("id", "dataLoadingFromFileId")
    const button = $("#dataLoadingFromFile");
    const indicatorId = button.attr("data-indicatorId");
    const entityId = button.attr("data-entityId");
    const queryId = button.attr("data-queryId");
    const targetSectionId = button.attr("data-targetSectionId");
    const targetQuestionId = button.attr("data-targetQuestionId");
    const nomatchMsg = button.attr("data-nomatchMsg");
    const localizedOk = button.attr("data-localizedOk");
    const localizedCancel = button.attr("data-localizedCancel");
    const localizedSelect = button.attr("data-localizedSelect");
    const localizedRecipeTitle = button.attr("data-localizedRecipeTitle");
    const localizedHelpText = button.attr("data-localizedHelpText");
    const localizedSuccessfulLoadMsg = button.attr("data-localizedSuccessfulLoadMsg");

    if(!localStorage.getItem('workbookJson') || localStorage.getItem('fileVersion') !== filePath){
        $.ajax({
            data: 'filePath=' + filePath ,
            type: 'GET',
            async: false,
            url: '/app/getRecipesForDataLoadingFromFile',
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            success: function (data) {

                workbookJson = data.workbookJson
                mandatoryDataInput = data.mandatoryDataInput
                localStorage.setItem('workbookJson', JSON.stringify(workbookJson));
                localStorage.setItem('mandatoryDataInput', JSON.stringify(mandatoryDataInput));
                localStorage.setItem('fileVersion', filePath);

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log('there is an error ,'+ errorThrown)
            }
        });
    }else{
        workbookJson = JSON.parse(localStorage.getItem('workbookJson'));
        mandatoryDataInput = JSON.parse(localStorage.getItem('mandatoryDataInput'));
    }
    let dataLoadingFromFileParams = getAllInputFromFormToQueryParams("dataLoadingFromFileId").split('&')
    let map = new Map();
    for (let i = 0; i < dataLoadingFromFileParams.length; i++) {
        let inputKeyValuePair = dataLoadingFromFileParams[i];
        if (inputKeyValuePair.indexOf("_is") === -1) {
            let inputKey = inputKeyValuePair.substring(inputKeyValuePair.indexOf(".") + 1, inputKeyValuePair.indexOf("="));
            if (inputKey.includes(ADDITIONAL_QUESTION_ID_DESIGNATION_TEXT)) {
                inputKey = inputKey.substring(inputKey.indexOf(ADDITIONAL_QUESTION_ID_DESIGNATION_TEXT) + ADDITIONAL_QUESTION_ID_DESIGNATION_TEXT.length);
            }
            let inputValue = inputKeyValuePair.substring(inputKeyValuePair.indexOf("=") + 1, inputKeyValuePair.length);
            map.set(inputKey, inputValue);
        }
    }
    let noMatch = false;
    if (workbookJson && map && checkAllMandatoryInputs(map, mandatoryDataInput)) {
        let recipeList = []
        let tempJson = new Map();
        for (let i = 0; i < workbookJson.length; i++) {
            const keysToRemove = ['resIds', 'QIdsWithQty', 'name']
            $.each(workbookJson[i], function (k, v) {
                if (!keysToRemove.includes(k)) {
                    tempJson.set(k, v)
                }
            });
            if (compareMaps2(map, tempJson)) {
                recipeList.push(workbookJson[i].name)
            }
        }
        if (recipeList.length === 1) {
            addRecipe(recipeList[0], workbookJson, queryId, indicatorId, entityId, targetSectionId, targetQuestionId, map, (localizedSuccessfulLoadMsg + recipeList[0]))
        } else if (recipeList.length > 0) {
            let selectedRecipe;
            swal.fire({
                input: 'select',
                html: `<strong style="font-size:25px;">${localizedRecipeTitle}</strong><span class="recipeQmark" data-content='${localizedHelpText}' rel="popover" data-original-title="" data-trigger="hover"><i class="icon-question-sign recipe-help-size"></span>`,
                inputOptions: recipeList,
                confirmButtonText: localizedOk,
                inputPlaceholder: localizedSelect,
                showCancelButton: true,
                cancelButtonText: localizedCancel,
                confirmButtonColor: '#78b200',
                customClass: "swal-select-textbox",
                inputValidator: (value) => {
                    if (!value)
                        return localizedSelect
                    else
                        return null
                }

            }).then(function (result) {
                if (result.value) {
                    addRecipe(recipeList[result.value], workbookJson, queryId, indicatorId, entityId, targetSectionId, targetQuestionId, map)
                } else if (result.dismiss === 'cancel') {
                    //do nothing
                }
            });
        } else if (recipeList.length === 0) {
            noMatch = true
        }
    } else {
        noMatch = true
    }
    if (noMatch) {
        Swal.fire({
            html: nomatchMsg
        });
    }
}
function compareMaps2(map1, map2) {
    let testVal;
    let flag = false;
    let keys1 = Array.from( map1.keys() );
    let keys2 = Array.from( map2.keys() );
    if (!keys2.every(elem => keys1.includes(elem))) {
        return false;
    }
    for (const [key, val] of map2) {
        testVal = map1.get(key);
        let readiness = val.replace(/[\s]+/g,'').split(',').contains(testVal)
        // in cases of an undefined value, make sure the key
        // actually exists on the object so there are no false positives
        if (!readiness || (testVal === undefined && !map1.has(key))) {
            flag = false;
            return false;
        }else if(readiness || (testVal !== undefined && map1.has(key))){
            flag = true;
        }
    }
    return flag;
}

function dataLoadingFromFileAddResource(resIds,queryId,indicatorId,entityId,targetSectionId,targetQuestionId, successfulLoadMessage = null) {
    let errorMessage;
    let enableSave = false
    if (queryId) {
        let fieldName = targetSectionId + '.' + targetQuestionId;
        let whereToAppend = '#' + targetSectionId + targetQuestionId + 'Resources';
        let header = $(whereToAppend + 'header');
        if (header.length && header.is(':hidden')) {
            header.show();
        }
        swal.fire({
            title: "Adding...", onBeforeOpen: function () {
                swal.showLoading()
            }
        })
        $(whereToAppend).empty();
        $.each(resIds, function (resId, quantity) {
            if ($(whereToAppend).length) {
                $.ajax({
                    async: false, type: 'POST',
                    data: 'preventDoubleEntries=' + false + '&entityId=' + entityId + '&resourceId=' + resId + '&indicatorId=' + indicatorId + '&queryId=' + queryId + '&sectionId=' + targetSectionId + '&questionId=' + targetQuestionId + '&fieldName=' + fieldName + '&showGWP=' + false + '&newResourceOnSelect=true' + '&resQuantity=' + quantity,
                    url: '/app/addresource',
                    success: function (data) {
                        enableSave = true
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
                            const start = this.selectionStart;
                            let end = this.selectionEnd;
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
                                let dotsAndCommas = ((val.match(/\./g) || []).length + (val.match(/,/g) || []).length);

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
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                    }
                });
            }
        });

        if (successfulLoadMessage) {
            swal.fire({
                icon: "success",
                text: successfulLoadMessage,
            })
        } else {
            swal.close()
        }

        if(enableSave){
            enableCalculationOnQueryForm()
            triggerFormChanged();
        }
    }else {
        errorMessage = "DataLoading From File " + buttonName + ": Missing mandatory parameters";
    }
    if(errorMessage){
        Swal.fire({
            html : errorMessage
        });
    }
}

function addRecipe(selectedRecipe, workbookJson, queryId, indicatorId, entityId, targetSectionId, targetQuestionId, map, successfulLoadMessage = null) {
    let resIds = {};
    let QIdsWithQty
    if (selectedRecipe) {
        for (let i = 0; i < workbookJson.length; i++) {
            if (selectedRecipe === workbookJson[i].name) {
                // extracting resourceIds and quantities from workbookJson.resIds (DATA section of Excel)
                // and adding them to final resIds object
                $.each(workbookJson[i].resIds, function (resourceId, quantityWithMultiplierId) {
                    if (quantityWithMultiplierId && quantityWithMultiplierId.quantity != null) {
                        let quantity = getQuantityWithMultiplier(quantityWithMultiplierId.quantity, quantityWithMultiplierId.multiplierId, map);
                        resIds[resourceId] = quantity;
                    }
                });

                // extracting resourceIds and quantities from workbookJson.QIdsWithQty (VARIABLE_DATA section of Excel)
                // and adding them to final resIds object
                QIdsWithQty = workbookJson[i].QIdsWithQty;
                if (QIdsWithQty) {
                    $.each(QIdsWithQty, function (questionId, quantityWithMultiplierId) {
                        if (map.get(questionId) && map.get(questionId) !== undefined
                                && quantityWithMultiplierId && quantityWithMultiplierId.quantity != null) {
                            let quantity = getQuantityWithMultiplier(quantityWithMultiplierId.quantity, quantityWithMultiplierId.multiplierId, map);
                            resIds[map.get(questionId)] = quantity;
                        }
                    });
                }
                break;
            }
        }
    }
    if (resIds) {
        dataLoadingFromFileAddResource(resIds, queryId, indicatorId, entityId, targetSectionId, targetQuestionId, successfulLoadMessage);
    }
}

// Calculates quantity taking into consideration a multiplier, if the latter is provided
function getQuantityWithMultiplier(quantity, multiplierId, map) {
    let multiplierValue = multiplierId ? map.get(multiplierId) : 1;

    // fallback if multiplierValue could not be found (user did not enter necessary data on the page)
    if (!multiplierValue) {
        multiplierValue = multiplierId === "massFibers" ? 0 : 1; // special fallback value for massFibers
    }

    return Math.round(quantity * multiplierValue * 10) / 10; // rounding to maximum 1 decimal place
}

// there are resources that should always come with the recipe and just the qty changes , user should input it , else no match found. eg : gravel , sand
function checkAllMandatoryInputs(map, mandatoryDataInput) {
    if (map && mandatoryDataInput) {
        for (const val of mandatoryDataInput) {
            if (!map.get(val)) {
                return false;
            }
        }
    }
    return true;
}
//Intent is to limit choices user can make based on answers they gave in previous chocies.
function limitChoices(fieldName, mainSectionId) {
    if (!fieldName || !mainSectionId) {
        return
    }
    findExcludeConditionsAndLimit(mainSectionId, fieldName)
}

function findExcludeConditionsAndLimit(mainSectionId, fieldName) {
    let fieldSelected = fieldName + '_betie'
    const fieldSelectedValues = $(`[name^= '${fieldSelected}' ]`);
    let $fieldToHide = $(`[id^='${fieldName}']`)
    for (let i = 0; i < fieldSelectedValues.length; i++) {
        unhideOptions($fieldToHide, fieldSelectedValues[i].id)
        let limitConditions = JSON.parse($(fieldSelectedValues[i]).val())
        for (let j = 0; j < limitConditions.length; j++) {
            let mainSection = mainSectionId + "."
            let fieldJsonObj = limitConditions[j]
            let keyQId = fieldJsonObj.keyQuestionId
            let valQId = fieldJsonObj.valueQuestionId
            let dymKeyId = mainSection + keyQId;
            let keyQuestionIdVal = $(`[id^='${dymKeyId}']`).val()
            if (!keyQuestionIdVal) {
                dymKeyId = mainSectionId + keyQId + "_resourceResourcesSelect";
                keyQuestionIdVal = $(`[id^='${dymKeyId}']`).val()
            }
            let dymValId = mainSection + valQId;
            let valueQuestionIdVal = $(`[id^='${dymValId}']`).val()
            if (!valueQuestionIdVal) {
                dymValId = mainSectionId + valQId + "_resourceResourcesSelect";
                valueQuestionIdVal = $(`[id^='${dymValId}']`).val()
            }
            let exclConditionArray = fieldJsonObj.excludingConditions
            toggleChoicesBetie(exclConditionArray, fieldName, fieldSelectedValues, valueQuestionIdVal, keyQuestionIdVal, i, $fieldToHide)
        }
    }

    const options = $(`[id^='${fieldName}'] option`).not(".hidden").map(function () {
        return $(this).val();
    }).get();
    if (!options.includes($fieldToHide.val())) {
        $fieldToHide.val('')
    }
}

function toggleChoicesBetie(exclConditionArray, fieldName, fieldSelectedValues, valueQuestionIdVal, keyQuestionIdVal, i, $fieldToHide) {
    if (exclConditionArray) {
        for (let k = 0; k < exclConditionArray.length; k++) {
            let excludeCondition = exclConditionArray[k]
            if (excludeCondition) {
                if (Object.keys(excludeCondition).includes(keyQuestionIdVal)) {
                    if (!Object.values(excludeCondition).toString() || flattenArray(Object.values(excludeCondition)).includes(valueQuestionIdVal)) {
                        hideOptions($fieldToHide, fieldSelectedValues[i].id)
                    }
                }
            }
        }
    }
}

function flattenArray(objArray) {
    let flatArry
    flatArry = $.map(objArray, function (n) {
        return n;
    });
    return flatArry;
}

function hideOptions(fieldToToggle, selectedQn) {
    fieldToToggle.find(`option[value='${selectedQn}']`).addClass("hidden");
}

function unhideOptions(fieldToToggle, selectedQn) {
    fieldToToggle.find(`option[value='${selectedQn}']`).removeClass("hidden");
}