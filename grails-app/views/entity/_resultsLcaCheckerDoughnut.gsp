<%
    def userService = grailsApplication.mainContext.getBean("userService")
%>
<div class="modal hide modal bigModal" id="lcaCheckerModal">
    <g:if test="${hasAllRequiredResources}">
        <div class="modal-header text-center"><button type="button" class="close" data-dismiss="modal">&times;</button>
            <h2 id="lcaCheckerModalHeading">${headingText} <g:if test="${!modifiable && userReadOnly}">[ <g:message code="readonly"/> ]</g:if></h2>
        </div>
        <div class="modal-body" style="max-height: 750px;" id="lcaCheckerModalBody">
            <p style="text-align: center;" class="bold">${gradingText}</p>
            <p style="text-align: center;">
                <g:convertUnitToHTML text="${bodyText}"/>
            </p>
            <g:if test="${freeVersion}">
                <p style="text-align: center;" class="bold">${freeVersion}</p>
            </g:if>
            <table class="table-striped table">
                <thead>
                <tr>
                    <th width="20px">No.</th>
                    <th>Check description</th>
                    <th>Project value</th>
                    <th>Threshold value</th>
                    <th>Average value</th>
                    <th>Unit</th>
                    <th>Type</th>
                    <th><g:message code="lcaChecker_validated" /> <i style="font-size: 1.1em !important;" class="fa fa-question greenInfoBubble validateHelp" data-content="${g.message(code: "lcaChecker_validated_info")}"></i></th>
                </tr>
                </thead>
                <tbody>
                <g:set var="i" value="${1}"/>
                <g:if test="${checksFailed && checksFailed.size()>0}">
                    <g:each in="${checksFailed.keySet()}" var="key">
                        <g:set var="checkType" value="${key.checkType}"/>
                        <g:set var="formattedPoints" value="${g.formatNumber(number: key.points ?: 0, format:"0")}"/>
                        <g:set var="formattedScore" value="${key.getFormattedScoreForUser(user, checksFailed.get(key), "0.###")}"/>
                        <g:set var="formattedCheckValue1" value="${key.getFormattedScoreForUser(user, key.checkValue1, "0")}"/>
                        <g:set var="formattedCheckValue2" value="${key.getFormattedScoreForUser(user, key.checkValue2, "0")}"/>
                        <tr class="checksFailed">
                            <td>${i++}</td>
                            <td><strong>${key.checkName}: </strong>${key.errorText}</td> <%--"minimumDatapoints".equals(customCondition)--%>
                            <g:if test="${"special".equals(key.checkCategory)}">
                                <g:if test="${"minimumDatapoints".equals(key.conditionToApply)}">
                                    <td>${formattedScore}</td>
                                    <td>greater than ${formattedCheckValue1}</td>
                                </g:if>
                                <g:elseif test="${"singleMaterialComprisesTooHighShare".equals(key.conditionToApply)}">
                                    <td>${formattedScore}</td>
                                    <td>less than ${formattedCheckValue1}</td>
                                </g:elseif>
                            </g:if>
                            <g:else>
                                <td>${formattedScore}</td>
                                <td>${"minimumValue".equals(checkType) ? "greater than ${formattedCheckValue1}" : "maximumValue".equals(checkType) ? "less than ${formattedCheckValue1}" : ['valueInRange','ratioInRange'].contains(checkType) ? "${formattedCheckValue1} - ${formattedCheckValue2}" : ""}</td>
                            </g:else>
                            <td>${key.averageValue ?: ""}</td>
                            <td>${key.getDisplayUnitForUser(user)}</td>
                            <td><i class="fa fa-times validateHelp" aria-hidden="true" style="color: #ee1600;" id="a${i}"  data-content="${g.message(code: "lcaChecker_errors")}" ></i><asset:image src="img/icon-warning.png" style="max-width:16px" class="validateHelp hidden" data-content="${g.message(code: "lcaChecker_user_validated")}" id="b${i}"/></td>
                            <td><i id="${i}" onclick="suppressWarning(this,'${key.checkId}', '${entityId}', '${indicatorId}', 'checksFailed')" style="font-size: 1.7em;" class="fa fa-toggle-off pointerCursor ${modifiable && (!userReadOnly) ? '': 'disabled_field'}" aria-hidden="true"></i></td>
                        </tr>
                    </g:each>
                </g:if>
                <g:if test="${checksAcceptable && checksAcceptable?.size() > 0 }">
                    <g:each in="${checksAcceptable.keySet()}" var="key">
                        <g:set var="checkType" value="${key.checkType}"/>
                        <g:set var="formattedScore" value="${key.getFormattedScoreForUser(user, checksAcceptable.get(key), "0.###")}"/>
                        <g:set var="formattedCheckValue1" value="${key.getFormattedScoreForUser(user, key.checkValue1, "0")}"/>
                        <g:set var="formattedCheckValue2" value="${key.getFormattedScoreForUser(user, key.checkValue2, "0")}"/>
                        <tr class="checksAcceptable">
                            <td>${i++}</td>
                            <td><strong>${key.checkName}: </strong><g:message code="lcaChecker_warnings_info" /></td>
                            <td>${formattedScore}</td>
                            <td>${"minimumValue".equals(checkType) ? "greater than ${formattedCheckValue1}" : "maximumValue".equals(checkType) ? "less than ${formattedCheckValue1}" : ['valueInRange','ratioInRange'].contains(checkType) ? "${formattedCheckValue1} - ${formattedCheckValue2}" : ""}</td>
                            <td>${key.averageValue ?: ""}</td>
                            <td>${key.getDisplayUnitForUser(user)}</td>
                            <td><asset:image src="img/icon-warning.png" style="max-width:16px" class="validateHelp" data-content="${g.message(code: "lcaChecker_warnings")}"/></td>
                            <td><i onclick="suppressWarning(this,'${key.checkId}', '${entityId}', '${indicatorId}', 'checksAcceptable')" style="font-size: 1.7em;" class="fa fa-toggle-off pointerCursor ${modifiable && (!userReadOnly) ? '': 'disabled_field'}" aria-hidden="true"></i></td>
                        </tr>
                    </g:each>
                </g:if>
                <g:if test="${checksPassed && checksPassed.size() > 0}">
                    <g:each in="${checksPassed.keySet()}" var="key">
                        <g:set var="checkType" value="${key.checkType}"/>
                        <g:set var="formattedPoints" value="${g.formatNumber(number: key.points ?: 0, format:"0")}"/>
                        <g:set var="formattedScore" value="${key.getFormattedScoreForUser(user, checksPassed.get(key), "0.###")}"/>
                        <g:set var="formattedCheckValue1" value="${key.getFormattedScoreForUser(user, key.checkValue1, "0")}"/>
                        <g:set var="formattedCheckValue2" value="${key.getFormattedScoreForUser(user, key.checkValue2, "0")}"/>
                        <tr class="checksPassed">
                            <td>${i++}</td>
                            <td><strong>${key.checkName}</strong></td>
                            <td>${formattedScore}</td>
                            <td>${"minimumValue".equals(checkType) ? "greater than ${formattedCheckValue1}" : "maximumValue".equals(checkType) ? "less than ${formattedCheckValue1}" : ['valueInRange','ratioInRange'].contains(checkType) ? "${formattedCheckValue1} - ${formattedCheckValue2}" : ""}</td>
                            <td>${key.averageValue ?: ""}</td>
                            <td>${key.getDisplayUnitForUser(user)}</td>
                            <td><i class="fa fa-check" aria-hidden="true" style="color: #6b9f00;"></i></td>
                            <td><i style="font-size: 1.7em;" class="fa fa-toggle-on ${modifiable && (!userReadOnly) ? 'oneClickColorScheme disabled_field' : 'disabled_field'} validateHelp" data-content="${g.message(code: "lcaChecker_passed")}" aria-hidden="true"></i></td>
                        </tr>
                    </g:each>
                </g:if>
                <g:if test="${suppressedChecks && suppressedChecks.size() > 0}">
                    <g:each in="${suppressedChecks.keySet()}" var="key">
                        <g:set var="checkType" value="${key.checkType}"/>
                        <g:set var="formattedPoints" value="${g.formatNumber(number: suppressedChecks.get(key).get(0) ?: 0, format:"0")}"/>
                        <g:set var="formattedCheckValue1" value="${key.getFormattedScoreForUser(user, key.checkValue1, "0")}"/>
                        <g:set var="formattedCheckValue2" value="${key.getFormattedScoreForUser(user, key.checkValue2, "0")}"/>
                        <g:set var="rowclass" value="${suppressedChecks.get(key).get(1)}" />
                        <tr class="checksPassed">
                            <td>${i++}</td>
                            <td><strong>${key.checkName}</strong></td>
                            <td>${formattedPoints}</td>
                            <td>${"minimumValue".equals(checkType) ? "greater than ${formattedCheckValue1}" : "maximumValue".equals(checkType) ? "less than ${formattedCheckValue1}" : ['valueInRange','ratioInRange'].contains(checkType) ? "${formattedCheckValue1} - ${formattedCheckValue2}" : ""}</td>
                            <td>${key.averageValue ?: ""}</td>
                            <td>${key.getDisplayUnitForUser(user)}</td>
                            <td><i class="fa fa-times hidden" aria-hidden="true" style="color: #ee1600;" id="a${i}"></i><asset:image src="img/icon-warning.png" style="max-width:16px" class="validateHelp" data-content="${g.message(code: "lcaChecker_user_validated")}" id="b${i}"/></td>
                            <td><i id="${i}" onclick="suppressWarning(this,'${key.checkId}', '${entityId}', '${indicatorId}', '${rowclass}')" style="font-size: 1.7em;" class="fa fa-toggle-on ${modifiable && (!userReadOnly) ? 'oneClickColorScheme pointerCursor' : 'disabled_field'} " aria-hidden="true"></i></td>
                        </tr>
                    </g:each>
                </g:if>

                <g:if test="${internalUser && checksNotApplicable && userService.getSuperUser(user)}">
                    <tr><td colspan="7">Check not applicable</td>
                    </tr>
                    <g:each in="${checksNotApplicable}" var="lcaChecker">
                        <tr>
                            <td>${i++}</td>
                            <td><strong>${lcaChecker.checkName}</strong></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                        </tr>
                    </g:each>
                </g:if>
                </tbody>
            </table>
        </div>
    </g:if>
    <g:else>
        <div class="modal-header text-center"><button type="button" class="close" data-dismiss="modal">&times;</button>
            <h2>${headingText} <g:if test="${!modifiable && userReadOnly}">[ <g:message code="readonly"/> ]</g:if></h2>
        </div>
        <div class="modal-body" style="max-height: 750px;">${queryLink}</div>
    </g:else>
</div>
<script type="text/javascript">
    $(function () {
        $('#lcaCheckerHeader').append(' <i class="fa fa-search oneClickColorScheme" style="cursor: pointer;" onclick="showLcaCheckerModal()" aria-hidden="true">');

        $('.validateHelp').each(function (){
            $(this).popover({
                placement: 'top',
                template: '<div class="popover importmapperPopover modalPopover"><div class="arrow"></div><div class="popover-content dataSourcesToolTip"></div></div>',
                trigger: 'hover',
                html: true,
                container: 'body'
            });
        });
    });


    function suppressWarning(button, lcaCheckId, designId, indicatorId, rowClass) {
        var userVerifiedChecksRow = $('#userVerifiedChecks');
        var row = $(button).closest('tr');

        var iconToggle1 = "#a" + button.id;
        var iconToggle2 = "#b" +button.id;

        $(iconToggle1).toggleClass("hidden");
        $(iconToggle2).toggleClass("hidden");

        if ($(button).hasClass('fa-toggle-off')) {
            $.ajax({
                type: 'POST',
                data: 'lcaCheckId=' + lcaCheckId + '&designId=' + designId + '&indicatorId=' + indicatorId,
                url: '/app/sec/design/disableLcaCheckerCheckForDesign',
                success: function (data) {
                    if (data.output === 'true') {
                        $(button).toggleClass('fa-toggle-off fa-toggle-on');
                        $(button).addClass('oneClickColorScheme');
                        userVerifiedChecksRow.removeClass('hidden');
                        row.insertAfter(userVerifiedChecksRow);
                        row.removeClass(rowClass);
                        row.addClass("userVerified");

                        if (!$('.'+rowClass).length) {
                            $('#'+rowClass+'Header').addClass('hidden');
                        }
                    }
                }
            });
        } else if ($(button).hasClass('fa-toggle-on')) {
            $.ajax({
                type: 'POST',
                data: 'lcaCheckId=' + lcaCheckId + '&designId=' + designId + '&indicatorId=' + indicatorId,
                url: '/app/sec/design/enableLcaCheckerCheckForDesign',
                success: function (data) {
                    if (data.output === 'true') {
                        $(button).toggleClass('fa-toggle-off fa-toggle-on');
                        $(button).removeClass('oneClickColorScheme');
                        var headerRow = $('#'+rowClass+'Header');
                        headerRow.removeClass('hidden');
                        row.insertAfter(headerRow);
                        row.removeClass("userVerified");
                        row.addClass(rowClass);

                        if (!$('.userVerified').length) {
                            $('#userVerifiedChecks').addClass('hidden');
                        }
                    }
                }
            });
        }
    }

    function getColor(percentage) {
        var color = "#D8D4D4";

        if (percentage < 14) {
            color = "#EF1C39"
        } else if (percentage < 29) {
            color = "#F78521"
        } else if (percentage < 43) {
            color = "#F7AC64"
        } else if (percentage < 57) {
            color = "#FFCC00"
        } else if (percentage < 71) {
            color = "#8DC641"
        } else if (percentage < 85) {
            color = "#19B059"
        } else if (percentage <= 100) {
            color = "#00845A"
        } else {
            color = "#D8D4D4"
        }
        return color
    }

    function getLetter(percentage) {
        var letter = "G";

        if (percentage < 14) {
            letter = "G"
        } else if (percentage < 29) {
            letter = "F"
        } else if (percentage < 43) {
            letter = "E"
        } else if (percentage < 57) {
            letter = "D"
        } else if (percentage < 71) {
            letter = "C"
        } else if (percentage < 85) {
            letter = "B"
        } else if (percentage <= 100) {
            letter = "A"
        } else {
            letter = "G"
        }
        return letter
    }


</script>
