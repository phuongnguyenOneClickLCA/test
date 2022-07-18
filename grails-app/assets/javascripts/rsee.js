function enableImportFromRseeButton(designId) {
    let selectedOptionIndex = $('#selectDd' + designId)[0].options.selectedIndex;

    if (selectedOptionIndex !== 0) {
        $('#importFromRseeButton' + designId).removeClass("removeClicks")
    } else {
        $('#importFromRseeButton' + designId).addClass("removeClicks")
    }
}

function downloadImportMapperFileFromRsee(parentId, designId, applicationId, importMapperId, indicatorId) {
    let selectedOptionIndex = $('#selectDd' + designId)[0].options.selectedIndex;
    let selection = $(`#selectDd${designId}`)[0].options[selectedOptionIndex];
    let batimentName;
    let optionValue = $('#selectDd' + designId)[0].options[selectedOptionIndex].value;
    let selectedZoneIndex = $(".rsetZonesForDesign" + designId);
    let rseeZoneMapping = "";

    for (let x = 0; x < selectedZoneIndex.length; x++) {
        if (selectedZoneIndex[x].options[selectedZoneIndex[x].options.selectedIndex].value.length > 0) {
            // format: project design zone comes first, rsee zone after the dot
            rseeZoneMapping += selectedZoneIndex[x].options[selectedZoneIndex[x].options.selectedIndex].value + ";";
        }
    }

    if (selection.index === selectedOptionIndex) {
        batimentName = selection.label;
        window.open("/app/sec/importMapper/rseeFileToSession?entityId=" + parentId + "&childEntityId=" + designId + "&batimentIndex=" +
            optionValue + "&batimentName=" + batimentName + "&applicationId=" + applicationId + "&importMapperId=" + importMapperId + "&indicatorId=" + indicatorId + "&rseeZoneMapping=" + rseeZoneMapping + "", "_blank");
    }

}