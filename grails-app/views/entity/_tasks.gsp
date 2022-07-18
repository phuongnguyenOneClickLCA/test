<%@ page expressionCodec="html" %>
<%@ page trimDirectiveWhitespaces="true" %>
<g:if test="${entity?.modifiable}">
    <div class="container section">
        <g:set var="currentDate" value="${new Date()}" />
        <g:set var="overDueTasks" value="${0}" />
        <g:each in="${tasks}" var="task">
            <g:if test="${task?.overDue}">
                <g:set var="overDueTasks" value="${overDueTasks + 1}" />
            </g:if>
        </g:each>

        <div class="sectionbodygeneral">
            <div class="pull-left" style="margin-left: 18px; margin-top: 6px;">
                <g:if test="${tasks}">
                    <g:message code="entity.show.tasks.found" />
                </g:if>
                <g:else>
                    <g:message code="entity.show.tasks.none" />
                </g:else>
            </div>
            <div class="pull-right">
                <g:if test="${entity?.users}">
                    <div class="btn-group hide-on-print">
                        <a href="#" data-toggle="dropdown" class="btn btn-primary dropdown-toggle"><i class="icon-plus icon-white"></i> <g:message code="assign_task" />&nbsp;<span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <g:each in="${entity?.users}" var="user">
                                <li>
                                    <opt:link controller="task" action="form" params="[userId: user.id]"> ${user?.name}</opt:link>
                                </li>
                            </g:each>
                            <li class="divider"></li>
                            <li>
                                <g:if test="${entity.getFeatures()?.collect({it.featureName})?.contains("Enterprise")}">
                                    <opt:link controller="task" action="form"> <g:message code="task.other_user" /></opt:link>
                                </g:if><g:else>
                                <g:set var="args" value="${message(code: 'enterprise')} - ${message(code: 'task.other_user')}"  />
                                    <a href="javascript:" class="enterpriseCheck" data-trigger="hover" rel="popover" data-content="${message(code: 'enterprise_feature_warning', args:[args])}"> <g:message code="task.other_user" /></a>
                                </g:else>
                            </li>
                        </ul>
                    </div>
                </g:if>
                <g:else>
                    <opt:link controller="task" action="form" class="btn btn-primary hide-on-print"><i class="icon-plus icon-white"></i> <g:message code="assign_task" /></opt:link>
                </g:else>
            </div>
        </div>

        <div class="sectionbodygeneral">
            <g:if test="${tasks || completedTasks}">
                <table class="table">
                    <thead>
                    <tr>
                        <th>&nbsp;</th>
                        <th><g:message code="entity.show.task.description" /></th>
                        <th><g:message code="entity.show.task.target_query" /></th>
                        <th><g:message code="entity.show.task.performer" /></th>
                        <th><g:message code="entity.show.task.deadline" /></th>
                        <th><g:message code="entity.show.task.lastUpdater" /></th>
                        <th><g:message code="entity.show.task.lastUpdated" /></th>
                        <th>&nbsp;</th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${tasks}" var="task">
                        <g:if test="${task}">
                            <tr>
                                <td>
                                    <g:if test="${task.deadline < currentDate}">
                                        <i class="icon-alert"></i>
                                    </g:if>
                                    <g:else>
                                        <i class="icon-hammer"></i>
                                    </g:else>
                                </td>
                                <td>
                                    <opt:link controller="task" action="form" id="${task.id}">
                                        <g:abbr value="${task.notes}" maxLength="40" />
                                    </opt:link>
                                    <span class="help-block text-justify">
                                        <small>${task.notes}</small>
                                    </span>
                                </td>
                                <td>
                                    <g:if test="${task.targetQueryId && task.targetEntityId && task.targetIndicatorId}">
                                        <opt:link controller="query" action="form" params="[indicatorId: task.targetIndicatorId, childEntityId: task.targetEntityId, queryId: task.targetQueryId]">
                                            ${task.targetQuery?.localizedName}
                                        </opt:link>
                                    </g:if>
                                    <g:else>
                                        -
                                    </g:else>
                                </td>
                                <td>${task.email}</td>
                                <td><g:formatDate date="${task.deadline}" format="dd.MM.yyyy" /></td>
                                <td>${task.lastUpdater?.name}</td>
                                <td><g:formatDate date="${task.lastUpdated}" format="dd.MM.yyyy" /></td>
                                <td>
                                    <g:if test="${task.taskLink}">
                                    <a href="javascript:;" style="color: #6b9f00;" onclick="taskPopup('${createLink(controller: 'task', action: 'taskLink', params: [taskId: task.id])}')"><g:message code="task.taskLink" /></a>
                                    </g:if>
                                    <opt:link controller="task" action="remove" id="${task.id}" class="btn btn-mini pull-right hide-on-print" onclick="return modalConfirm(this);"
                                              data-questionstr="${message(code:'task.delete')}"
                                              data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}" data-titlestr="${message(code:'task.delete_confirm.header') }"><i class="icon-trash"></i></opt:link>
                                    <opt:link controller="task" action="form" id="${task.id}" class="btn btn-mini pull-right hide-on-print"><i class="icon-pencil"></i></opt:link>
                                </td>
                            </tr>
                        </g:if>
                    </g:each>
                    <g:each in="${completedTasks}" var="task">
                        <g:if test="${task}">
                            <tr style="display: none;" class="completedTask">
                                <td>
                                    <i class="icon-done"></i>
                                </td>
                                <td>
                                    <opt:link controller="task" action="form" id="${task.id}" rel="popover_bottom" data-content="${task.notes}"><g:abbr value="${task.notes}" maxLength="40" /></opt:link>
                                </td>
                                <td>
                                    <g:if test="${task.targetQueryId && task.targetEntityId && task.targetIndicatorId}">
                                        <opt:link controller="query" action="form" params="[indicatorId: task.targetIndicatorId, childEntityId: task.targetEntityId, queryId: task.targetQueryId]">
                                            ${task.targetQuery?.localizedName}
                                        </opt:link>
                                    </g:if>
                                    <g:else>
                                        -
                                    </g:else>
                                </td>
                                <td>${task.email}</td>
                                <td><g:formatDate date="${task.deadline}" format="dd.MM.yyyy" /></td>
                                <td>${task.lastUpdater?.name}</td>
                                <td><g:formatDate date="${task.lastUpdated}" format="dd.MM.yyyy" /></td>
                                <td>
                                    <opt:link controller="task" action="remove" id="${task.id}" class="btn btn-mini pull-right hide-on-print" onclick="return modalConfirm(this);"
                                              data-questionstr="${message(code:'task.delete')}"
                                              data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}" data-titlestr="${message(code:'task.delete_confirm.header') }"><i class="icon-trash"></i></opt:link>
                                    <opt:link controller="task" action="form" id="${task.id}" class="btn btn-mini pull-right hide-on-print"><i class="icon-pencil"></i></opt:link>
                                </td>
                            </tr>
                        </g:if>
                    </g:each>
                    </tbody>
                </table>
                <g:if test="${completedTasks}">
                    <span id="showAllInfo"><g:message code="entity.show.task.completed_hidden" /></span> <a href="#" id="showAllTasks"><g:message code="show.all" /></a>
                </g:if>
            </g:if>
        </div>
    </div>
</g:if>