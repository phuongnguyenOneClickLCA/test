<%-- Copyright (c) 2012 by Bionova Oy --%>
<%@ page import="com.bionova.optimi.core.Constants; grails.converters.JSON" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
        <meta name="format-detection" content="telephone=no"/>
    </head>
	<body>
            <div class="container">
                <opt:breadcrumbsNavBar parentEntity="${entity}" specifiedHeading="${'attachment'.equals(type) ? note?.id ? message(code: 'attachment.modify') :  message(code: 'attachment.add') : note?.id ? message(code: 'note.modify') : message(code: 'note.add')}"/>
                <div class="screenheader">
                    <g:render template="/entity/basicinfoView"/>
                </div>
            </div>

	       <div class="container section">
            <div class="sectionheader">
                <h2>
                    <g:if test="${'attachment'.equals(type)}">
                        ${note?.id ? message(code: 'attachment.modify') : message(code: 'attachment.add')}
                    </g:if>
                    <g:else>
                        ${note?.id ? message(code: 'note.modify') : message(code: 'note.add')}
                    </g:else>
                </h2>
            </div>

            <div class="sectionbody">
                <g:uploadForm action="save" onsubmit="return validateForm(this)">
                  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                  <g:hiddenField name="id" value="${note?.id}"/>
                  <g:hiddenField name="entityId" value="${entity?.id}" />
                  <g:hiddenField name="type" value="${type}" />

                  <div class="clearfix"></div>
                  <div class="column_left">

                       <g:if test="${'attachment'.equals(type)}">
                          <div class="control-group">
                              <label for="comment" class="control-label">
                                  <g:if test="${note?.hasFile}">
                                      <g:link action="downloadFile" id="${note.id}">${note.file?.name}</g:link>
                                  </g:if>
                                  <g:else>
                                      <g:message code="note.file" />
                                  </g:else>
                                  <br /><span style="font-size: 0.9em;"><g:message code="note.file.info" /></span>
                              </label>

                              <div class="controls">
                                  <div class="input-append"><input type="file" id="noteFile" name="noteFile" class="input-xlarge span8" value="" /></div>
                              </div>
                          </div>
                       </g:if>

                       <div class="control-group">
                          <label for="comment" class="control-label">${'note'.equals(type) ? message(code: 'note.comment') : message(code: 'attachment.comment')}</label>
                          <div class="controls"><div class="input-append"><opt:textArea entity="${entity}" name="comment" value="${note?.comment}" /></div></div>
                        </div>

                        <g:if test="${entity?.childEntities && 'note'.equals(type )}">
                        <div class="control-group">
                            <label class="control-label"><g:message code="note.link_to" />:</label>
                            <g:set var="designs" value="${entity?.getDesigns()}" />
                            <g:set var="periods" value="${entity?.getOperatingPeriods()}" />
                            <g:if test="${designs || periods}">
                                <div class="controls">
                                    <div class="input-append">
                                        <select name="targetEntityId">
                                            <option value=""></option>
                                            <g:each in="${designs}">
                                                <option value="${it.id.toString()}"${it.id.toString().equals(note?.targetEntityId) || it.id.toString().equals(params.targetEntityId) ? ' selected=\"selected\"' : ''}>${it.operatingPeriodAndName}</option>
                                            </g:each>
                                            <g:each in="${periods}">
                                                <option value="${it.id.toString()}"${it.id.toString().equals(note?.targetEntityId) || it.id.toString().equals(params.targetEntityId) ? ' selected=\"selected\"' : ''}>${it.operatingPeriodAndName}</option>
                                            </g:each>
                                         </select>
                                    </div>
                                </div>
                            </g:if>
                        </div>
                        </g:if>
                  </div>

                  <div class="clearfix"></div>

                  <opt:submit entity="${entity}" id="saveButton" name="save" value="${message(code:'save')}" class="btn btn-primary" />
                  <g:if test="${note?.id}">
                    <opt:link action="remove" id="${note.id}" params="[type: type]" class="btn btn-danger" onclick="return modalConfirm(this);"
                              data-questionstr="${'attachment'.equals(type) ? message(code:'attachment.delete.question') : message(code:'note.delete.question')}" data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}"
                              data-titlestr="${'attachment'.equals(type) ? message(code:'attachment.delete.title') : message(code:'note.delete.title')}"><g:message code="delete" /></opt:link>
                  </g:if>
                  <opt:link controller="entity" action="show" class="btn" id="${entityId}" params="[activeTab: ('attachment'.equals(type) ? 'attachments' : 'notes')]"><g:message code="cancel" /></opt:link>
                </g:uploadForm>
            </div>
        </div>
      </div>


    <script>
        function validateForm(form){
            const validAttachmentExtensions = ${Constants.INVALID_ATTACHMENT_EXTENSIONS as JSON};
            const input = document.getElementById('noteFile');
            if(!input) return true

            const file = input.files ? input.files[0] : null
            let hasFile = ${note ? note.hasFile : false}
            if(!file && !hasFile) {
                Swal.fire({icon: "warning", title: "${g.message(code: "attachment.missingFile")}"})
                return false
            }

            var errorMessages = [];
            var validationResult = true;
            var attachmentExtension = file.name.substring(file.name.lastIndexOf(".") + 1);

            if (validAttachmentExtensions.includes(attachmentExtension)) {
                errorMessages.push("${g.message(code: 'attachment.invalidExtension', default: 'File type is not allowed.')}");
                validationResult = false;
            }

            if (file.size > ${Constants.MAX_ATTACHMENT_SIZE}) {
                errorMessages.push("${g.message(code: 'attachment.fileToBig')}");
                validationResult = false;
            }

            if (validationResult) {
                $("#saveButton").off('click').addClass('removeClicks').addClass('disabled');
            } else {
                Swal.fire({icon: "warning", title: errorMessages.join(" ")});
            }

            return validationResult
        }
    </script>
    </body>

</html>



