<%@ page expressionCodec="html" %>
<%@ page trimDirectiveWhitespaces="true" %>
<g:if test="${entity?.modifiable}">
    <div class="container section">
        <div class="sectionbodygeneral">
            <div class="pull-left" style="margin-left: 18px; margin-top: 6px;">
                <g:if test="${notes}">
                    <g:message code="entity.show.notes.found" />
                </g:if>
                <g:else>
                    <g:message code="entity.show.notes.empty" />
                </g:else>
            </div>
            <div class="pull-right">
                <opt:link controller="note" action="form" params="[type: 'note']" class="btn btn-primary hide-on-print"><g:message code="entity.add_note" /></opt:link>
            </div>
        </div>

        <div class="sectionbodygeneral">
            <g:if test="${notes}">
                <table class="table">
                    <thead>
                    <tr>
                        <th>&nbsp;</th>
                        <th><g:message code="entity.show.task.description" /></th>
                        <th><g:message code="design" /> / <g:message code="operating" /></th>
                        <th><g:message code="entity.show.task.lastUpdater" /></th>
                        <th><g:message code="entity.show.task.lastUpdated" /></th>
                        <th>&nbsp;</th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${notes}" var="note">
                        <g:if test="${note}">
                            <tr>
                                <td><i class="icon-message"></i></td>
                                <td>
                                    <g:if test="${note.comment}">
                                        <opt:link controller="note" action="form" id="${note.id}" params="[type: 'note']">
                                            <g:abbr value="${note.comment}" maxLength="100" />
                                        </opt:link>
                                        <span class="help-block text-justify">
                                            <small>${note.comment}</small>
                                        </span>
                                    </g:if>
                                    <g:else>
                                        <opt:link controller="note" action="form" id="${note.id}" params="[type: 'note']">-</opt:link>
                                    </g:else>
                                </td>
                                <td>${note.entityName}</td>
                                <td>${note.lastUpdaterDisplayName ? note.lastUpdaterDisplayName : note.lastUpdaterName}</td>
                                <td><g:formatDate date="${note?.lastUpdated}" format="dd.MM.yyyy" /></td>
                                <td>
                                    <opt:link controller="note" action="remove" id="${note.id}" params="[type: 'note']" class="btn btn-mini pull-right hide-on-print" onclick="return modalConfirm(this);"
                                              data-questionstr="${message(code:'note.delete.question')}" data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}"
                                              data-titlestr="${message(code:'note.delete.title') }"><i class="icon-trash"></i></opt:link>
                                    <opt:link controller="note" action="form" id="${note.id}" class="btn btn-mini pull-right hide-on-print"><i class="icon-pencil"></i></opt:link>
                                </td>
                            </tr>
                        </g:if>
                    </g:each>
                    </tbody>
                </table>
            </g:if>
        </div>
    </div>
</g:if>