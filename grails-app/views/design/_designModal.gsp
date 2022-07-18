<%@ page import="com.bionova.optimi.core.Constants" %>
<g:hiddenField name="id" value="${design?.id}"/>
<g:hiddenField name="parentEntityId" value="${entity?.id}" />
<g:hiddenField name="ifcImport" value="${ifcImport}" />

<%-- ??????????????????????????????????????????????????? --%>
<g:hiddenField name="newDesign" value="true"/>
<g:if test="${originalEntityId}"><g:hiddenField name="originalEntityId" value="${originalEntityId}" /></g:if>
<%-- ??????????????????????????????????????????????????? --%>

<%-- Scope from design to modify or the originalDesign to copy --%>
<g:set var="scope" value="${design?.scope ?: originalEntity?.scope}"/>

<g:if test="${!onlyModalBody}">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h2>
            <g:if test="${design?.id}">
                <g:message code="design.modifyDesign" />
            </g:if>
            <g:else>
                <g:if test="${isCopy}">
                    <g:message code="design.copyDesign" />
                </g:if>
                <g:else>
                    <g:message code="design.createDesign" />
                </g:else>
            </g:else>
        </h2>
    </div>
</g:if>

<div class="modal-body">
    <%-- Column Design Information --%>
    <div class="column_left" style="width: 300px;">
        <h4><g:message code="design.modal.header1"/></h4>
        <tmpl:/design/designInformationColumn />
    </div>

    <%-- Column Scope --%>
    <g:if test="${entityClass == Constants.EntityClass.BUILDING.toString() || (entityClass == Constants.EntityClass.PRODUCT.toString() && allowedScopes.size() > 0)}">
            <div class="column_right bordered">
                <h4><g:message code="${(entityClass == Constants.EntityClass.BUILDING.toString() ? "design.modal.header2" : "design.modal.header3")}"/></h4>
                <tmpl:/entity/scopeTemplate scope="${scope}" parentEntity="${entity}"/>
            </div>
    </g:if>

</div>

<g:if test="${!onlyModalBody}">
    <div class="modal-footer">
        <a class="btn btn-default" href="#" data-dismiss="modal"><g:message code="cancel" /></a>
        <opt:submit parentEntity="${entity}" style="pointer-events: none;" entity="${design}" name="saveDesign" value="${design?.id ? message(code:'save') : message(code:'add')}" class="btn btn-primary preventDoubleSubmit disabled" formId="${formId}" />
</div>
</g:if>
<script>

    $('[rel="popover"]').popover({
        template: '<div class="popover"><div class="arrow"></div><div class="popover-content"></div></div>'
    });

    $(function() {
        //hidden selects should not be affected by maximizing!
        const allSelects = $('select').not('.maskWithSelect2, .maskWithSelect2AllowClear, :hidden');
        if (allSelects) {
            $(allSelects).select2({
                placeholder:'<g:message code="select"/>',
                containerCssClass:":all:"
            }).maximizeSelect2Height();
        }
    })

</script>