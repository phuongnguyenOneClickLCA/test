function exportEPDJson(childEntityId, indicatorId){
    let url = '/app/sec/export/exportEPDJson'
    let json = {childEntityId: childEntityId, indicatorId: indicatorId}
    let successCallback = function (data) {
        if (data) {
            let fileName = "epdHubJSON" + ".json"
           // $("#message").text("API call successful. File will be download in a moment.")
            let dataStr = "data:text/json;charset=utf-8," + encodeURIComponent(JSON.stringify(data.response.content));
            let dlAnchorElem = document.getElementById('downloadAnchorElem');
            dlAnchorElem.setAttribute("href", dataStr);
            dlAnchorElem.setAttribute("download", fileName);
            dlAnchorElem.click();
            $(".download").addClass("hidden")

        }
    }
    let errorServerReply = function (e) {
        $("#message").text("Error when downloading the json")
    }

    ajaxForJson(url, json, successCallback, errorServerReply)
}