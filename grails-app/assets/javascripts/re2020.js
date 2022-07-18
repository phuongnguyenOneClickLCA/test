/**
 * Calls UtilController to render re2020RequestData modal pop-up
 */
function openRe2020RequestData() {
    var url = "/app/sec/util/renderRe2020RequestDataModal"
    $.ajax({
        url: url,
        success: function (data) {
            $("#re2020RequestDataModal").empty().append(data.output).modal({backdrop: 'static', keyboard: false})
        },
        error: function (error, textStatus) {
            console.log(error + ": " + textStatus)
        }

    })
}