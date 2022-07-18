function loadAllReportItems(reportItemIds) {
    if (!reportItemIds) {
        return;
    }
    $.each(reportItemIds, function (i, reportItemId) {
        loadReportItem(reportItemId);
    });
}

function loadReportItem(reportItemId) {
    const reportForm = $("#reportParamsForm-" + reportItemId);
    if (reportForm && reportForm.length > 0) {
        const params = reportForm.serialize();
        loadReportItemWithParams(reportItemId, params);
    }
}

function loadReportItemWithParams(reportItemId, params) {
    if (!reportItemId) {
        return;
    }
    const spinnerId = "loadingSpinner-reportItem-" + reportItemId;
    const containerId = "defaultTableContainer-" + reportItemId;

    let containerElement = document.getElementById(containerId);
    if (!containerElement || containerElement.length < 1) {
        return;
    }

    const spinnerElement = document.getElementById(spinnerId);
    if ($(spinnerElement).is(":visible")) {
        // loading is in progress
        return;
    }
    $(spinnerElement).show();

    $.ajax({
        url: '/app/sec/design/loadReportItem',
        data: params,
        success: function (data) {
            let isReportExpanded = $(containerElement).find("." + reportItemId).is(":visible");
            $(containerElement).html(data.output);
            $("." + reportItemId).toggle(isReportExpanded);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("ajax call failed " + errorThrown)
            $(spinnerElement).hide();
        }
    });
}