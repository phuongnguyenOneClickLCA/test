<g:if test="${unlockVerifiedDatasetLicensed && !dataset?.unlockedFromVerifiedStatus && dataset?.verified}">
    <g:if test="${forNonResourceRow}">
    <a href="javascript:" class="bold unlockVerifiedDatasetLink inline-block"
       onclick="unlockVerifiedDataset(this, true, '${message(code: 'unlockVerifiedDatasetWarning')}', '${message(code: 'yes')}', '${message(code: 'cancel')}', ${changeInputBg ?: false})">
        <asset:image class="unverifiedDatasetIcon float-left" src="img/unverifiedIcon.png"/>
        <span>${message(code: 'unlockVerifiedDataset')}</span>
    </a>
    </g:if>

    <g:if test="${forNotRow}">
        <a href="javascript:" class="bold unlockVerifiedDatasetLink inline-block"
           onclick="unlockVerifiedDataset(this, false, '${message(code: 'unlockVerifiedDatasetWarning')}', '${message(code: 'yes')}', '${message(code: 'cancel')}', ${changeInputBg ?: false})">
            <asset:image class="unverifiedDatasetIcon" src="img/unverifiedIcon.png"/>
            <span >${message(code: 'unlockVerifiedDataset')}</span>
        </a>
    </g:if>
</g:if>