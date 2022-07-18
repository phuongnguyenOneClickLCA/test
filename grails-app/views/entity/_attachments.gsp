<%@ page expressionCodec="html" %>
<%@ page trimDirectiveWhitespaces="true" %>
<g:if test="${entity?.modifiable}">
    <div class="container section">
        <div class="sectionbodygeneral">
            <div class="pull-left" style="margin-left: 18px; margin-top: 6px;">
                <g:if test="${attachments}">
                    <g:message code="entity.show.attachments.found" />
                </g:if>
                <g:else>
                    <g:message code="entity.show.attachments.empty" />
                </g:else>
            </div>
            <div class="pull-right">
                <opt:link controller="note" action="form" params="[type: 'attachment']" class="btn btn-primary hide-on-print"><g:message code="entity.add_attachment" /></opt:link>
            </div>
        </div>
        <div class="sectionbodygeneral">
            <g:if test="${attachments}">
                <table class="table" id="attachmentTable">
                    <thead>
                    <tr>
                        <th class="no-sort">&nbsp;</th>
                        <th><g:message code="entity.show.task.description" /></th>
                        <th><g:message code="entity.show.task.attachment" /></th>
                        <th><g:message code="entity.show.task.lastUpdater" /></th>
                        <th><g:message code="entity.show.task.lastUpdated" /></th>
                        <th class="no-sort">&nbsp;</th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${attachments}" var="note">
                        <g:if test="${note}">
                            <tr>
                                <td><i class="icon-message"></i></td>
                                <td>
                                    <g:if test="${note.comment}">
                                        <opt:link controller="note" action="form" params="[type: 'attachment']" id="${note.id}">
                                            <g:abbr value="${note.comment}" maxLength="40" />
                                        </opt:link>
                                        <span class="help-block text-justify">
                                            <small>${note.comment}</small>
                                        </span>
                                    </g:if>
                                    <g:else>
                                        <opt:link controller="note" action="form" params="[type: 'attachment']" id="${note.id}">-</opt:link>
                                    </g:else>
                                </td>
                                <td>
                                    <g:if test="${note.hasFile}">
                                        <g:set var="dataContent">
                                            ${note.file.name}
                                            <g:if test="${session?.isIE && note.file?.isOfficeFile}">
                                                <br />(<g:message code="ie.file_warning" />)
                                            </g:if>
                                        </g:set>
                                        <g:link controller="note" action="downloadFile" id="${note.id}" target="_blank">
                                            <g:abbr maxLength="40" value="${note.file.name}" />
                                        </g:link>
                                        <span class="help-block text-justify">
                                            <small>${dataContent}</small>
                                        </span>
                                    </g:if>
                                    <g:else>
                                        -
                                    </g:else>
                                </td>
                                <td>${note.lastUpdaterDisplayName ? note.lastUpdaterDisplayName : note.lastUpdaterName}</td>
                                <td><span class="hidden">${note?.lastUpdated?.format('yyyyMMdd')}</span><g:formatDate date="${note?.lastUpdated}" format="dd.MM.yyyy" /></td>
                                <td>
                                    <opt:link controller="note" action="remove" id="${note.id}" params="[type: 'attachment']" class="btn btn-mini pull-right hide-on-print" onclick="return modalConfirm(this);"
                                              data-questionstr="${message(code:'attachment.delete.question')}" data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}"
                                              data-titlestr="${message(code:'attachment.delete.title') }"><i class="icon-trash"></i></opt:link>
                                    <opt:link controller="note" action="form" id="${note.id}" params="[type: 'attachment']" class="btn btn-mini pull-right hide-on-print"><i class="icon-pencil"></i></opt:link>
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

<script type="text/javascript">
    $(function () {
        dataTableAttachment();
    });
</script>