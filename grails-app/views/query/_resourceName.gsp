<g:if test="${resourceName?.length() > limit}">
    <a href="javascript:"
       class="resourceNameContainer just_black  ${preventChanges || disabled || dataset?.verified || noHandle ? '' : 'handle'}"
       data-content="${resourceName?.encodeAsHTML()}" rel="popover" data-placement="top"
       data-trigger="hover">${g.abbr(value: resourceName, maxLength: g.resourceNameMaxLength(resource: resource, limit: limit))}</a>
</g:if>
<g:else>
    <a href="javascript:"
       class="resourceNameContainer just_black defaultCursor ${preventChanges || disabled || dataset?.verified || noHandle ? '' : 'handle'}">${resourceName?.encodeAsHTML()}</a>
</g:else>