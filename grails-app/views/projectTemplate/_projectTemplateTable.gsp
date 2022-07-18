<%@ page expressionCodec="html" %>
<%@ page import="com.bionova.optimi.core.Constants; com.bionova.optimi.core.domain.mongo.ProjectTemplate" %>
<g:set var="newModalId" value="addTemplateModalContainer"/>
<g:set var="rowListId" value="${Constants.TEMPLATE_LIST_BODY}"/>
<g:set var="editTemplateModalId" value="${Constants.EDIT_TEMPLATE_OPTION_CONTAINER_ID}"/>
<div>
    <div id="${newModalId}" class="modal fade hide modalProJectTemplate">
        <g:if test="${isMainUser}">
            <g:render template="/projectTemplate/projectTemplateModal" model="[licenses                 : licenses,
                                                                               buildingTypeResourceGroup: buildingTypeResourceGroup,
                                                                               countriesResourceGroup   : countriesResourceGroup,
                                                                               accountId                : account?.id?.toString(),
                                                                               appendRowTo              : rowListId,
                                                                               modalContainerId         : newModalId,
                                                                               availableIndicators      : availableIndicators,
                                                                               edit                     : false,
                                                                               template                 : (ProjectTemplate) null,
                                                                               isPublic                 : isPublicTable,
                                                                               entityClassResources     : entityClassResources,
                                                                               createNew                : true]"/>
        </g:if>
    </div>
    <div id="${editTemplateModalId}" class="modal fade hide modalProJectTemplate"></div>
    <div class="sectionheader">
        <div class="pull-left" style="display: flex">
            <button type="button" class="sectionexpander" style="margin-top: 0" >
                <i id="quickStartTemplateExpandHeader" class="icon icon-chevron-down expander"></i>
            </button>

            <h2 class="h2expander"
                onclick="toggleSection('quickStartTemplateExpandHeader', 'projectTemplateList')">
                ${message(code: 'templates')}
            </h2>
        </div>
    <g:if test="${isMainUser}">
        <div class="pull-right">
            <a href="javascript:" class="btn btn-primary" onclick="$('#${newModalId}').modal()">${message(code: 'add')}</a>
        </div>
    </g:if>
    </div>
    <div id="projectTemplateList">
        <table id="projectTemplateTable" class="table table-striped">
            <thead>
                <tr>
                    <th>${message(code: 'templateName')}</th>
                    <th>
                        ${message(code: 'defaultNames')}
                        <span class="triggerPopoverTemplateTable" data-content="${message(code: 'projectTemplate.name.help')}">
                            <i class="icon-question-sign"></i>
                        </span>
                    </th>
                    <th>${message(code: 'importMapper.class')}</th>
                    <th>
                        ${message(code: 'entity.show.designs_tools')}
                        <span class="triggerPopoverTemplateTable" data-content="${message(code: 'projectTemplate.tools.help')}">
                            <i class="icon-question-sign"></i>
                        </span>
                    </th>
                    <th>
                        ${message(code: 'defaults')}
                        <span class="triggerPopoverTemplateTable" data-content="${message(code: 'projectTemplate.defaults.help')}">
                            <i class="icon-question-sign"></i>
                        </span>
                    </th>
                    <th></th>
                </tr>
            </thead>
            <tbody id="${rowListId}">
                <g:each in="${templates}" var="template">
                    <g:render template="/projectTemplate/projectTemplateRow" model="[template: template, accountId: account?.id?.toString(), isPublic: isPublicTable, isMainUser: isMainUser]"/>
                </g:each>
            </tbody>
        </table>
    </div>
</div>
<script>
    $(document).ready(function(){
        // Align modal when it's displayed
        $('#${newModalId}').on('show.bs.modal', function(){
            alignModal('${newModalId}')
            maskSelectWithSelect2(true, '${message(code: 'select')}', 'maskWithSelect2AllowClear' )
        })
        $('#${editTemplateModalId}').on('show.bs.modal', function(){
            alignModal('${editTemplateModalId}')
            maskSelectWithSelect2(true, '${message(code: 'select')}', 'maskWithSelect2AllowClear' )
        })
        // Align modal when user resize the window
        $(window).on("resize", function(){
            alignModal('${newModalId}')
            alignModal('${editTemplateModalId}')
        })
        // trigger all the qMark popovers manually.
        keepPopoverOnHover('.triggerPopoverTemplateTable', 150, 450)
        <g:if test="${isPublicTable}">
        sortBigTable('#projectTemplateTable', true, 10)
        </g:if>
    });
</script>