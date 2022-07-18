<%@ page trimDirectiveWhitespaces="true" %>
<g:if test="${entity?.modifiable}">
    <g:set var="entityNotes" value="${entity?.notes}" />
    <g:set var="entityFiles" value="${entity?.files}" />

    <g:set var="currentDate" value="${new Date()}" />
<%--
<div class="sectionheader">
    <div class="sectioncontrols pull-right">
        <g:if test="${entity?.users}">
            <div class="btn-group hide-on-print">
                <a href="#" data-toggle="dropdown" class="btn btn-primary dropdown-toggle"><g:message code="assign_task" /><span class="caret"></span></a>
                <ul class="dropdown-menu">
                    <g:each in="${entity?.users}" var="user">
                        <li>
                            <opt:link controller="task" action="form" params="[userId: user.id]"> ${user?.name}</opt:link>
                        </li>
                    </g:each>
                    <li class="divider"></li>
                    <li>
                        <opt:link controller="task" action="form"> <g:message code="task.other_user" /></opt:link>
                    </li>
                </ul>
            </div>
        </g:if>
        <g:else>
            <opt:link controller="task" action="form" class="btn btn-primary hide-on-print"><g:message code="assign_task" /></opt:link>
        </g:else>
        <opt:link controller="note" action="form" class="btn btn-primary hide-on-print">
            <g:message code="entity.add_note" />
        </opt:link>
        <g:set var="buttonsGuide" value="${opt.channelMessage(code: 'entity.tasks_buttons.guide')}" />
        <g:if test="${buttonsGuide}">
            &nbsp;<asset:image src="img/infosign.png" rel="popover" data-content="${buttonsGuide}" />
        </g:if>
    </div>

    <button class="pull-left sectionexpander"><i class="icon icon-chevron-down expander"></i><i class="icon icon-chevron-right collapser"></i></button>
    <g:set var="overDueTasks" value="${0}" />
    <g:each in="${tasks}" var="task">
        <g:if test="${task?.overDue}">
            <g:set var="overDueTasks" value="${overDueTasks + 1}" />
        </g:if>
    </g:each>
    <h2><g:message code="entity.show.task.title" /> <g:if test="${overDueTasks}"><span class="label label-important">${overDueTasks}</span></g:if></h2>
</div>
--%>

    <div class="task-section" style="width: 290px;">
        <table>
            <tbody>
            <tr>
                <td>
                    <strong>Tasks</strong> ${tasks ? '(' + tasks.size() + ')' : ''}<br />
                    <opt:link controller="task" action="form">+ <g:message code="add" /></opt:link>
                <td>
                    <g:if test="${tasks}">
                        <g:if test="${tasks.size() > 2}">
                            <g:each in="${tasks[0..2]}" var="task">
                                <a href="#"${task.overDue ? ' style=\"color: red;\"' : ''}>${task.email} <g:formatDate date="${task.deadline}" format="dd.MM.yyyy" /></a><br />
                                and ${tasks.size() - 2} more
                            </g:each>
                        </g:if>
                        <g:else>
                            <g:each in="${tasks}" var="task">
                                <a href="#"${task.overDue ? ' style=\"color: red;\"' : ''}>${task.email} <g:formatDate date="${task.deadline}" format="dd.MM.yyyy" /></a><br />
                            </g:each>
                        </g:else>
                    </g:if>
                    <g:else>No open tasks</g:else>
                </td>
            </tr>
            <tr>
                <td>
                    <strong>Files</strong> ${entityFiles ? '(' + entityFiles.size() +')' : ''}<br />
                    <opt:link controller="entity" action="form">+ <g:message code="add" /></opt:link>
                </td>
                <td>
                    <g:if test="${entityFiles}">
                        <g:if test="${entityFiles.size() > 2}">
                            <g:each in="${entityFiles[0..2]}" var="entityFile">
                                <a href="#"><g:abbr maxLength="30" value="${entityFile.name}" /></a><br />
                            </g:each>
                        </g:if>
                        <g:else>
                            <g:each in="${entityFiles}" var="entityFile">
                                <a href="#"><g:abbr maxLength="30" value="${entityFile.name}" /></a><br />
                            </g:each>
                        </g:else>
                    </g:if>
                    <g:else>-</g:else>
                </td>
            </tr>
            <tr>
                <td>
                    <strong>Notes</strong> ${entityNotes ? '(' + entityNotes.size() + ')' : ''}<br />
                    <opt:link controller="note" action="form">+ <g:message code="add" /></opt:link>
                </td>
                <td>
                    <g:if test="${entityNotes}">
                        <g:if test="${entityNotes.size() > 2}">
                            <g:each in="${entityNotes[0..2]}" var="note">
                                <a href="#"><g:abbr maxLength="30" value="${note.comment}" /></a><br />
                            </g:each>
                        </g:if>
                        <g:else>
                            <g:each in="${entityNotes}" var="note">
                                <a href="#"><g:abbr maxLength="30" value="${note.comment}" /></a><br />
                            </g:each>
                        </g:else>
                    </g:if>
                    <g:else>-</g:else>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="text-align: right;"><a href="#"><g:message code="show.all" /></a></td>
            </tr>
            </tbody>
        </table>
        <%--
        <g:if test="${completedTasks}">
            <span id="showAllInfo"><g:message code="entity.show.task.completed_hidden" /></span> <a href="#" id="showAllTasks"><g:message code="entity.show.task.show" /></a>
        </g:if>
        --%>
    </div>
</g:if>