<tr style='border-right-style:hidden; border-left-style:hidden;'><td colspan="16">&nbsp;</td></tr>
<tr>
    <td colspan="16">
        <strong style="float: left;">${purpose}</strong>
    </td>
</tr>
<g:if test="${resources}">
    <tr>
        <td colspan="16">
            <g:if test="${removePurposes}">
                <g:each in="${resources}" var="resource">
                    ${resource.resourceId} / ${resource.profileId} <a href="javascript:" onclick="window.open('${createLink(action: "showData", controller: "import", params: [resourceUUID: resource.id])}', '_blank', 'width=1024, height=768, scrollbars=1');"><i class="fas fa-search-plus"></i></a> :
            Removed automaticallyEnabledPurpose: ${purpose}<br />
                </g:each>
            </g:if>
            <g:else>
                <g:each in="${resources}" var="resource">
                    ${resource.resourceId} / ${resource.profileId} <a href="javascript:" onclick="window.open('${createLink(action: "showData", controller: "import", params: [resourceUUID: resource.id])}', '_blank', 'width=1024, height=768, scrollbars=1');"><i class="fas fa-search-plus"></i></a> :
            Added automaticallyEnabledPurpose: ${purpose}<br />
                </g:each>
            </g:else>
        </td>
    </tr>
</g:if>