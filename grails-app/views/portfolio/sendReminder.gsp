<%-- Copyright (c) 2012 by Bionova Oy --%>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<g:form action="createReminderTasks" useToken="true" class="theForm">

<div class="container">
    <div class="screenheader">
        <div class="pull-right hide-on-print">
            <opt:link controller="entity" action="show" class="btn" id="${portfolio.entityId}"><g:message code="cancel" /></opt:link>
            <g:if test="${confirmWarning}">
                <opt:submit name="save" value="${message(code:'send')}" class="btn btn-primary" />
            </g:if>
            <g:else>
                <opt:submit name="save" value="${message(code:'continue')}" class="btn btn-primary" />
            </g:else>
         </div>
        <h1>${portfolio.entityName} / ${operatingPeriod} / <g:message code="portfolio.send_reminder" /></h1>
    </div>
</div>

<div class="container section">
    <div class="sectionbody">
        <g:if test="${confirmWarning}">
            <h3>${confirmWarning}</h3>
        </g:if>
            <g:if test="${confirm}">
                <g:each in="${params}">
                    <g:if test="${it.value instanceof java.lang.String}">
                        <g:hiddenField name="${it.key}" value="${it.value}" />
                    </g:if>
                    <g:else>
                        <g:each in="${it.value}" var="paramValue">
                            <g:hiddenField name="${it.key}" value="${paramValue}" />
                        </g:each>
                    </g:else>
                </g:each>
                <g:hiddenField name="confirmed" value="true" />
            </g:if>
            <g:else>
                <g:hiddenField name="id" value="${portfolio?.id}"/>
                <g:hiddenField name="portfolioEntityId" value="${portfolio?.entityId}" />
                <g:hiddenField name="period" value="${operatingPeriod}" />
                <g:hiddenField name="confirm" value="${confirm}" />
            </g:else>
            <div class="clearfix"></div>
            <div class="column_left">

                <div class="control-group">
                    <label for="deadline" class="control-label"><g:message code="task.deadline" /></label>
                    <div class="controls"><div class="input-append"><label><opt:textField name="deadline" class="input-xlarge datepicker" value="${params.get("deadline")}" /><span class="add-on"><i class="icon-calendar"></i></span></label></div></div>
                </div>

                <div class="control-group">
                    <label class="checkbox">
                        <opt:checkBox name="autoReminder" id="autoReminder" checked="${params.get("autoReminder") ? true : false}" />
                        <g:message code="task.autoReminder" />
                    </label>
                </div>

                <div class="control-group" style="padding-left: 10px;${!params.get("autoReminder") ? ' display: none;' : ''}" id="reminderSettings">
                    <label class="checkbox">
                        <opt:checkBox name="ccToDeleguer" id="ccToDeleguer" />
                        <g:message code="task.cc_me" />
                    </label>

                    <label class="control-label"><g:message code="task.reminder_amount" /></label>
                    <opt:select name="reminderAmount" from="${[1,2,3,4,5]}" value="${params.get("reminderAmount")}" />

                    <label class="control-label"><g:message code="task.reminder_frequency" /></label>
                    <opt:select name="reminderFrequency" from="${reminderFrequencies}" optionKey="${{it.key}}" optionValue="${{it.value}}" value="${params.get("reminderFrequency")}" />
                </div>

                <div class="control-group">
                    <label for="notes" class="control-label"><g:message code="entity.show.task.description" /></label>
                    <div class="controls"><opt:textArea name="notes" class="input-xlarge" value="${params.get("notes")}" /></div>
                </div>

                <div class="control-group" style="margin-top: 30px;">
                    <label class="control-label"><strong><g:message code="portfolio.send_reminder.choose_entities" /></strong></label>
                    <g:if test="${!allReady}">
                        <table class="column_left">
                            <tr>
                                <td style="width: 40%;">
                                    <label class="checkbox">
                                        <input type="checkbox" id="selectAll" /> <strong><g:message code="ifc.select.all" /></strong>
                                    </label>
                                </td>
                             </tr>
                        </table>
                    </g:if>

                    <table class="table tableFormat nowrap sendReminder">
                    <tr>
                        <th style="width: 3%;">&nbsp;</th><th><g:message code="portfolio.send_reminder.entity" /></th><th style="width: 15%;"><g:message code="status" /></th><th><g:message code="portfolio.send_reminder.user" /></th></tr>
                    </tr>
                    <g:each in="${entities}" var="entity">
                            <tr>
                                <td style="width: 3%;">
                                    <g:if test="${!indicatorsReady.get(entity) && entity.hasOperatingPeriod(operatingPeriod)}">
                                        <input type="checkbox" name="entityId" value="${entity.id}" class="entity"${(!indicatorsReady && !confirm) || params.get("entityId") == entity.id || params.get("entityId")?.contains(entity.id.toString()) ? ' checked=\"checked\"' : ''} />
                                    </g:if>
                                </td>
                                <td>
                                    ${entity.name}
                                </td>
                                <td style="width: 15%;">
                                    <g:if test="${indicatorsReady.get(entity)}">
                                        <g:message code="ready" />
                                    </g:if>
                                    <g:elseif test="${!entity.hasOperatingPeriod(operatingPeriod)}">
                                        <g:message code="portfolio.period_missing"  />
                                    </g:elseif>
                                    <g:else>
                                        <g:message code="portfolio.send_reminder.indicators_not_ready" />
                                    </g:else>
                                </td>
                                <td>
                                     <select name="user.${entity.id}"${indicatorsReady.get(entity) || !entity.hasOperatingPeriod(operatingPeriod) ? ' disabled=\"disabled\"' : ''} class="sendReminder">
                                        <g:if test="${entity.notifiedUsername}">
                                            <option value="${entity.notifiedUsername}"${entity.notifiedUsername == params.get("user." + entity.id) ? ' selected=\"selected\"' : ''}>${entity.notifiedUsername}</option>
                                        </g:if>
                                        <g:each in="${entity.managers}" var="user">
                                            <option value="${user.username}"${user.username == params.get("user." + entity.id) ? ' selected=\"selected\"' : ''}>${user.username}</option>
                                        </g:each>
                                        <g:each in="${entity.modifiers}" var="user">
                                            <option value="${user.username}"${user.username == params.get("user." + entity.id) ? ' selected=\"selected\"' : ''}>${user.username}</option>
                                        </g:each>
                                    </select>
                                </td>
                            </tr>
                    </g:each>
                    </table>
                </div>

            </div>
    </div>
</div>
</g:form>

<script type="text/javascript">
    $(document).ready(function() {
        if ($('.entity').prop('checked')) {
            $('#selectAll').prop('checked', true);
        }

        selectAllCheckbox('#selectAll', '.entity');

        $('#autoReminder').on('click', function (event) {
            var isChecked = $(this).is(':checked');
            var element = document.getElementById("reminderSettings");

            if(isChecked) {
                element.style.display = 'block';
            } else {
                element.style.display = 'none';
            }
        });

        <g:if test="${confirm}">
        $('.theForm input:not(:hidden)').attr('disabled', 'disabled');
        $('.theForm [type="submit"]').removeAttr('disabled')
        $('.theForm select').attr('disabled', 'disabled');
        $('.theForm textarea').attr('disabled', 'disabled');
       </g:if>
     });
</script>
</body>
</html>



