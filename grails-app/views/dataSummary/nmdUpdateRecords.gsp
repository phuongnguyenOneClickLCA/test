<%@ page import="com.bionova.optimi.core.service.NmdApiService" %>
<%--
  Created by IntelliJ IDEA.
  User: trang
  Date: 5.8.2020
  Time: 21.36
--%>
<%-- Copyright (c) 2012 by Bionova Oy --%>
<!doctype html>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta name="layout" content="main"/>
    <meta name="format-detection" content="telephone=no"/>
</head>
<sec:ifAnyGranted roles="ROLE_SALES_VIEW">
    <body>
    <div class="container">
        <div class="screenheader">
            <h1>
                NMD Daily  Update status
            </h1>
        </div>

        <div>
            <input type="text" id="date"><span class="add-on"><i class="icon-calendar"></i></span>

            <p id="message"></p>

            <div class="flex">
                <a href="#" class="btn btn-primary" onclick="downloadJSON()">
                    Download JSON for date
                    <i class="hidden fas fa-circle-notch fa-spin white-font margin-left-2 download"></i>
                </a>
                <a href="#" class="btn btn-primary fiveMarginHorizontal updateApi" onclick="triggerNMDFromDate()">
                    Trigger NMD update from selected date
                    <i class="hidden fas fa-circle-notch fa-spin white-font margin-left-2 triggerFromDate"></i>
                </a>
            </div>
            <a id="downloadAnchorElem" class="hidden"></a>
        </div>

        <div class="tenMarginVertical">
            <a href="#" class="btn btn-primary updateApi" onclick="triggerNMDManually()">
                Trigger NMD update manually
                <i class="hidden fas fa-circle-notch fa-spin white-font margin-left-2 trigger"></i>
            </a>
            <span class="triggerPopover"
                  data-content="Triggers the API update from the last date that it did NOT run successfully">
                <i class="icon-question-sign"></i>
            </span>
        </div>

        <div class="tenMarginVertical">
            <a href="#" class="btn btn-primary updateApi" onclick="triggerNMDFromDate(true)">
                Trigger NMD update manually from ${NmdApiService.NMD_FALLBACK_DATE}
                <i class="hidden fas fa-circle-notch fa-spin white-font margin-left-2 triggerFromScratch"></i>
            </a>
        </div>

        <div class="tenMarginVertical">
            <a href="#" class="btn btn-danger updateApi" onclick="clearNmdUpdate()">
                Clear all Nmd Update!!
                <i class="hidden fas fa-circle-notch fa-spin white-font margin-left-2 clearTrigger"></i>
            </a>
        </div>

        <g:render template="/import/checkNmdElement"/>

        <div class="tenMarginVertical">
            <label for="resourceUuidInput">Check Resource:</label>

            <div class="flex">
                <div class="flex">
                    <input id="resourceUuidInput" type="text" placeholder="Enter resource database Id (UUID)"/>

                    <div>
                        <a href="javascript:" class="btn btn-primary" onclick="openShowData(true)">
                            Show
                        </a>
                    </div>
                </div>

                <div class="flex fiveMarginHorizontal">
                    <input id="resourceId" type="text" placeholder="Enter resourceId"/>
                    <input id="profileId" type="text" placeholder="Enter profileId"/>

                    <div>
                        <a href="javascript:" class="btn btn-primary" onclick="openShowData(false)">
                            Show
                        </a>
                    </div>
                </div>
            </div>
    </div>

        <g:render template="/import/checkConstruction"/>
        <g:render template="/import/testRemovalConfig"/>

        <div class="section collapsed">
            <div class="sectionheader pointerCursor" onclick="toggleExpandSection(this)">
                <div style="display: flex">
                    <a href="javascript:" class="pull-left sectionexpander">
                        <i class="icon icon-chevron-down expander"></i>
                        <i class="icon icon-chevron-right collapser"></i>
                    </a>
                    <span style="display: flex; align-items: center">
                        <div class="h2expanderspec" style="font-size: 14px"><b>Days with new tables</b></div>
                    </span>
                </div>
            </div>

            <div class="sectionbody hide">
                <table class="table table-striped table-condensed">
                    <thead>
                    <tr>
                        <th>Date</th>
                        <th>Last checked</th>
                        <th>New tables</th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:if test="${updateList}">
                        <g:each in="${updateList.reverse()}" var="update" status="i">
                            <g:if test="${update.newTables}">
                                <tr>
                                    <td>${update.date}</td>
                                    <td>${update.updateDate}</td>
                                    <td>
                                        ${update.newTables}
                                    </td>
                                </tr>
                            </g:if>
                        </g:each>
                    </g:if>
                    </tbody>
                </table>
            </div>
    </div>

        <div class="section">
            <div class="sectionheader pointerCursor" onclick="toggleExpandSection(this)">
                <div style="display: flex">
                    <a href="javascript:" class="pull-left sectionexpander">
                        <i class="icon icon-chevron-down expander"></i>
                        <i class="icon icon-chevron-right collapser"></i>
                    </a>
                    <span style="display: flex; align-items: center">
                        <div class="h2expanderspec"
                             style="font-size: 14px"><b>Unrecognizable values</b></div>
                    </span>
                </div>
            </div>

            <div class="sectionbody">
                <div>
                    <span>- Unit:</span>
                    <span>${unrecognizableUnitId ?: ''}</span>
                </div>

                <div>
                    <span>- FaseID:</span>
                    <span>${unrecognizableFaseId ?: ''}</span>
                </div>

                <div>
                    <span>- MilieuCategorieID:</span>
                    <span>${unrecognizableMilieuCategorieId ?: ''}</span>
                </div>

                <div>
                    <span>- ToepassingID:</span>
                    <span>${unrecognizableToepassingId ?: ''}</span>
                </div>
            </div>
        </div>

        <div class="section">
            <div class="sectionheader pointerCursor" onclick="toggleExpandSection(this)">
                <div style="display: flex">
                    <a href="javascript:" class="pull-left sectionexpander">
                        <i class="icon icon-chevron-down expander"></i>
                        <i class="icon icon-chevron-right collapser"></i>
                    </a>
                    <span style="display: flex; align-items: center">
                        <div class="h2expanderspec" style="font-size: 14px"><b>All update details</b></div>
                    </span>
                </div>
            </div>

            <div class="sectionbody">
                <div class="flex">
                    <a class="btn btn-primary" onclick="$('.nmdUpdateDetails').toggleClass('hidden')">
                        Show / Hide all details
                    </a>
                    <a class="btn btn-primary fiveMarginHorizontal"
                       onclick="$('.nmdUpdateErrors').toggleClass('hidden')">
                        Show / Hide all errors
                    </a>
                </div>
                <table class="table table-striped table-condensed">
                    <thead>
                    <tr>
                        <th>Date</th>
                        <th>Update OK?</th>
                        <th>
                            <span>Should update again?</span>
                            <span class="triggerPopover"
                                  data-content="Should run update again for this date because it had a few unrecognizable values (units or impacts or ...) in the last time it was checked">
                                <i class="icon-question-sign"></i>
                            </span>
                        </th>
                        <th>Last checked</th>
                        <th>Details</th>
                        <th>Errors</th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:if test="${updateList}">
                        <g:each in="${updateList.reverse()}" var="update" status="i">
                            <tr>
                                <td>${update.date}</td>
                                <td>${update.statusOk}</td>
                                <td>${update.hasConfigErrors ? 'YES' : ''}</td>
                                <td>${update.updateDate}</td>
                                <td>
                                    <g:if test="${update.updatedObjectsAsString}">
                                        <a class="btn btn-primary"
                                           onclick="$('#detail${i}').toggleClass('hidden')">Show / Hide details</a> <br>
                                        <span class="hidden nmdUpdateDetails" id="detail${i}">
                                            ${update.updatedObjectsAsString}
                                        </span>
                                    </g:if>
                                    <g:else>
                                        No updates for this day
                                    </g:else>
                                </td>
                                <td>
                                    <g:if test="${update.errorObjectsAsString}">
                                        <a class="btn btn-primary"
                                           onclick="$('#error${i}').toggleClass('hidden')">Show / Hide errors</a> <br>
                                        <span class="hidden nmdUpdateErrors" id="error${i}">
                                            ${update.errorObjectsAsString}
                                        </span>
                                    </g:if>
                                    <g:else>
                                        No errors for this day
                                    </g:else>
                                </td>
                            </tr>
                        </g:each>
                    </g:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        $("#date").datepicker({
            dateFormat: 'yy-mm-dd',
        });

        $(function () {
            triggerPopoverOnHover('.triggerPopover')
        })

        function downloadJSON() {
            let date = $("#date").val().replaceAll("-", "")
            let json = {date: date}
            $(".download").removeClass("hidden")

            let url = '/app/sec/nmdApi/getUpdateForDate'

            let successCallback = function (data) {
                if (data) {
                    let fileName = "nmd" + date + ".json"
                    $("#message").text("API call successful. File will be download in a moment.")
                    let dataStr = "data:text/json;charset=utf-8," + encodeURIComponent(JSON.stringify(data.response));
                    let dlAnchorElem = document.getElementById('downloadAnchorElem');
                    dlAnchorElem.setAttribute("href", dataStr);
                    dlAnchorElem.setAttribute("download", fileName);
                    dlAnchorElem.click();
                    $(".download").addClass("hidden")

                }
            }
            let errorServerReply = function (e) {
                $("#message").text("Error from our server when checking for NMD update")
            }

            ajaxForJson(url, json, successCallback, errorServerReply)
        }

        function triggerNMDManually() {
            $(".trigger").removeClass("hidden")
            disableAllApiBtn()

            $.ajax({
                type: 'POST',
                url: '/app/sec/nmdApi/triggerManualUpdate',
                success: function (data, textStatus) {
                    console.log(data)
                    $(".trigger").addClass("hidden")
                    window.location.reload()
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log(textStatus)
                    console.log(XMLHttpRequest)
                    $(".trigger").addClass("hidden")
                    enableAllApiBtn()
                }
            });
        }

        function triggerNMDFromDate(fromScratch) {
            disableAllApiBtn()
            let query = ''
            let dateString = ''
            if (fromScratch) {
                $(".triggerFromScratch").removeClass("hidden")
                query += 'startFromScratch=true'
                dateString = '${NmdApiService.NMD_FALLBACK_DATE}'
            } else {
                $(".triggerFromDate").removeClass("hidden")
                // date is in format yyyy-mm-dd
                let date = $("#date").val().split('-')
                if (date.includes('')) {
                    Swal.fire({
                        icon: 'error',
                        text: 'Please select a date in the calendar.'
                    })
                    enableAllApiBtn()
                    $(".triggerFromDate").addClass("hidden")
                    return
                }
                dateString = date[2] + '-' + date[1] + '-' + date[0] // dd-mm-yyyy
                query += 'date=' + dateString
            }

            Swal.fire({
                html: "This will trigger an NMD update from <b>" + dateString + "</b> til today! Should not run this more than once. Page will refresh when it's done",
                icon: "warning",
                confirmButtonText: "Yes",
                cancelButtonText: "No",
                showCancelButton: true
            }).then(result => {
                if (result.isConfirmed) {
                    $.ajax({
                        type: 'POST',
                        url: '/app/sec/nmdApi/triggerManualUpdate?' + query,
                        success: function (data, textStatus) {
                            window.location.reload()
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            console.log(textStatus)
                            console.log(XMLHttpRequest)
                            if (fromScratch) {
                                $(".triggerFromScratch").addClass("hidden")
                            } else {
                                $(".triggerFromDate").addClass("hidden")
                            }
                            enableAllApiBtn()
                        }
                    });
                } else {
                    if (fromScratch) {
                        $(".triggerFromScratch").addClass("hidden")
                    } else {
                        $(".triggerFromDate").addClass("hidden")
                    }
                    enableAllApiBtn()
                }
            });
        }

        function clearNmdUpdate() {
            $(".clearTrigger").removeClass("hidden")

            Swal.fire({
                text: "This action will clear all the Nmd Update Status records in our database (just the information about daily updates, not the Nmd resources / elements / constructions)",
                icon: "warning",
                confirmButtonText: "Yes",
                cancelButtonText: "No",
                confirmButtonColor: "red",
                showCancelButton: true
            }).then(result => {
                if (result.isConfirmed) {
                    disableAllApiBtn()
                    $.ajax({
                        type: 'POST',
                        url: '/app/sec/nmdApi/clearNmdUpdate',
                        success: function (data, textStatus) {
                            window.location.reload()
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            console.log(textStatus)
                            console.log(XMLHttpRequest)
                            $(".clearTrigger").addClass("hidden")
                            enableAllApiBtn()
                        }
                    });
                } else {
                    $(".clearTrigger").addClass("hidden")
                }
            });
        }

        function openShowData(useUuid) {
            if (useUuid) {
                const uuid = $('#resourceUuidInput').val()
                if (uuid) {
                    window.open('${createLink(controller: "import", action: "showData", params: [resourceUUID: ''])}' + uuid, '_blank', 'width=1024, height=768, scrollbars=1')
                } else {
                    Swal.fire({
                        icon: 'error',
                        text: 'Please input a resource UUID'
                    })
                }
            } else {
                const resourceId = $('#resourceId').val()
                const profileId = $('#profileId').val()
                if (resourceId) {
                    window.open('/app/sec/admin/import/showData?resourceId=' + resourceId + '&profileId=' + profileId, '_blank', 'width=1024, height=768, scrollbars=1')
                } else {
                    Swal.fire({
                        icon: 'error',
                        text: 'Please input at least resourceId '
                    })
                }

            }
        }

        function enableAllApiBtn() {
            enableButton($('.updateApi'))
        }

        function disableAllApiBtn() {
            disableButton($('.updateApi'))
        }
    </script>

    </body>
</sec:ifAnyGranted>
</html>