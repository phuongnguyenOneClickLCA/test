%{-- Icon without any functions--}%
<a class="unverifiedDataset ${dataset?.unlockedFromVerifiedStatus ? '' : 'hide'} defaultCursor" href="javascript:"
   rel="popover" data-trigger="hover"
   data-content="${message(code: 'unverifiedDataset.help')}">
    <asset:image class="unverifiedDatasetIcon" src="img/unverifiedIcon.png"/>
    <g:if test="${showText}">
        <b>${message(code: 'notVerified')}</b>
    </g:if>
</a>