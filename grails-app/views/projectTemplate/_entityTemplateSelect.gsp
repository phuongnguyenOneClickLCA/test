<%@ page import="com.bionova.optimi.core.Constants" %>
<g:if test="${copyEntitiesMapped}">
    <select id="${templateId}-selectDesignToImport" class="selectDesignToImport" onchange="disableButtonCopyDesign()"
            data-allowClear="true"
            data-placeHolder="${message(code: 'select.design')}" name="fromEntityId">
        <g:render template="/query/copiableDesignOptions"
                  model="[copyEntitiesMapped: copyEntitiesMapped, basicQuery: basicQuery, indicatorCompatible: indicatorCompatible, notPrettifyOptGroup: true]"/>
    </select>
    <g:if test="${template?.childEntityId}">
    %{-- Silly hack to select a option with optgroup--}%
        <script>
            $(function() {
                $('#${templateId}-selectDesignToImport').val("${template?.childEntityId}").change()
            })
        </script>
    </g:if>
</g:if>
<g:else>
    <span>${message(code:'design.templates.notFound')}</span>
</g:else>
