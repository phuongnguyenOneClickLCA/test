<g:if test="${!projectLevel && !productDataListPage && !constructionPage}">
    <g:if test="${forNonResourceRow || forNotRow}">
        <input type="hidden" name="${questionIdPrefix}_isVerified_"
               data-verifiedFromEntityId="${entity?.id}" data-isVerified="true"
               value="${dataset?.verified ?: false}"/>
        <input type="hidden"
               name="${questionIdPrefix}_isUnlockedFromVerifiedStatus_"
               data-isUnlockedFromVerifiedStatus="true"
               value="${dataset?.unlockedFromVerifiedStatus ?: false}"/>
        <input type="hidden" name="${questionIdPrefix}_verifiedFromEntityId_" data-isVerifiedFromEntityId="true"
               value="${dataset?.verifiedFromEntityId}"/>
    </g:if>

    <g:if test="${forResourceRow}">
        <input type="hidden"
               name="${questionIdPrefix}_isVerified_${resourceId ? '.' + resourceId : ''}"
               data-verifiedFromEntityId="${entity?.id}" data-isVerified="true" value="${dataset?.verified ?: false}"/>
        <input type="hidden"
               name="${questionIdPrefix}_isUnlockedFromVerifiedStatus_${resourceId ? '.' + resourceId : ''}"
               data-isUnlockedFromVerifiedStatus="true" value="${dataset?.unlockedFromVerifiedStatus ?: false}"/>
        <input type="hidden"
               name="${questionIdPrefix}_verifiedFromEntityId_${resourceId ? '.' + resourceId : ''}"
               data-isVerifiedFromEntityId="true" value="${dataset?.verifiedFromEntityId}"/>
    </g:if>
</g:if>