<opt:breadcrumbsNavBar indicator="${indicator}" parentEntity="${parentEntity ?: entity}" childEntity="${childEntity ?: parentEntity ? entity : null}" step="${0}" entityClassResource="${entityClassResource}"/>

<br/>
<h2 style="display: inline">
    <asset:image src="resultreport.png" class="queryIcon"/>
    <g:if test="${!entity}">
        <g:message code="entity.new_entity_title"/>
    </g:if>
    <g:if test="${parentEntity?.id}">
        ${parentEntity.name}
        <g:if test="${entity}">
            - ${entity.operatingPeriodAndName}
        </g:if>
    </g:if>
    <g:elseif test="${entity?.id}">
        ${entity.name}
        <g:if test="${childEntity}">
            - ${childEntity.operatingPeriodAndName}
        </g:if>
    </g:elseif>
</h2>
<opt:renderPopUpTrial stage="newProject" entity="${entity}"/>
