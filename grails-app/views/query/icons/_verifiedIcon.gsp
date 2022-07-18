%{-- Icon without any functions--}%
<a class="verifyDatasetLock ${dataset?.verified ? '' : 'hide'} defaultCursor" href="javascript:"
   rel="popover" data-trigger="hover"
   data-content="${message(code: 'verifiedDataset.help')}">
    <asset:image class="verifiedDatasetIcon" src="img/verifiedIcon.png"/>
    <g:if test="${showText}">
        ${message(code: 'verified')}
    </g:if>
</a>