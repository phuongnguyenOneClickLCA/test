<%
    def userService = grailsApplication.mainContext.getBean("userService")
%>
<g:if test="${!projectLevel}">
    <g:if test="${forSection}">
        <span class="verifyDatasetsInSectionIconContainer" data-sectionHasVerifiedDataset="${section?.hasVerifiedDatasets}">
            <a class="verifyDatasetsInSectionLink hide"
               href="javascript:"
               onclick="verifyDatasetsInSection(this)">
                <asset:image class="verifiedDatasetIcon" src="img/verifiedIcon.png"/>
                <g:message code="verifyDatasetsInSection"/>
            </a>
            <a class="neutralizeDatasetsInSectionLink hide" href="javascript:" onclick="neutralizeDatasetsInSection(this)">
                <asset:image class="neutralDatasetIcon" src="img/blankGrayShield.png"/>
                <g:message code="neutralizeDatasetsInSection"/>
            </a>
        </span>
    </g:if>

    <g:if test="${forNonResourceRow}">
        <span class="verifyDatasetIconContainer" data-isVerifiedDataset="${dataset?.verified ?: false}">
            <g:if test="${userService.getSuperUser(user) && !dataset?.unlockedFromVerifiedStatus}">
                <a class="neutralDataset hide"
                   href="javascript:"
                   rel="popover" data-trigger="hover"
                   data-content="${message(code: 'unverifiedDataset.superuser.help')}"
                   onclick="verifyAndLockNonResourceRow(this)">
                    <asset:image class="neutralDatasetIcon" src="img/blankGrayShield.png"/>
                </a>
                <a class="verifyDatasetLock ${dataset?.verified ? '' : 'hide'} defaultCursor"
                   href="javascript:"
                   rel="popover" data-trigger="hover"
                   data-isActivated="false"
                   data-activatedHelp="${message(code: 'verifiedDataset.superuser.help.activated')}"
                   data-content="${message(code: 'verifiedDataset.superuser.help.inactivated')}"
                   onclick="neutralizeNonResourceRow(this)">
                    <asset:image class="verifiedDatasetIcon" src="img/verifiedIcon.png"/>
                </a>
                <g:render template="/query/icons/unverifiedIcon" model="[dataset : dataset]"/>
            </g:if>
            <g:else>
                <g:render template="/query/icons/unverifiedIcon" model="[dataset : dataset]"/>
                <g:render template="/query/icons/verifiedIcon" model="[dataset : dataset]"/>
            </g:else>
        </span>
    </g:if>

    <g:if test="${forResourceRowNameCell}">
        <span class="verifyDatasetIconContainer" data-isVerifiedDataset="${dataset?.verified ?: false}"
              data-isParentConstructionResource="${constructionResource}"
              data-uniqueConstructionIdentifier="${uniqueConstructionIdentifier}">
            <g:if test="${userService.getSuperUser(user) && !dataset?.unlockedFromVerifiedStatus}">
                %{-- Only verify symbol, no onclick--}%
                <a class="neutralDataset neutralIconWithoutFunctionality hide ${(!constructionResource && uniqueConstructionIdentifier) ? 'removeClicks' : ''} defaultCursor"
                   href="javascript:"
                   rel="popover" data-trigger="hover"
                   data-isParentConstructionResource="${constructionResource}"
                   data-uniqueConstructionIdentifier="${uniqueConstructionIdentifier}"
                   data-content="${message(code: 'neutralDataset.resourceRow.superuser.help')}"
                    ${!constructionResource && uniqueConstructionIdentifier ? 'disabled' : ''}>
                    <asset:image class="neutralDatasetIcon" src="img/blankGrayShield.png"/>
                </a>
                <a class="verifyDatasetLock verifiedIconWithoutFunctionality ${dataset?.verified ? '' : 'hide'} ${!constructionResource && uniqueConstructionIdentifier ? 'removeClicks' : ''} defaultCursor"
                   href="javascript:"
                   rel="popover" data-trigger="hover"
                   data-isActivated="false"
                   data-activatedHelp="${message(code: 'verifiedDataset.resourceRow.superuser.help.activated')}"
                   data-content="${message(code: 'verifiedDataset.resourceRow.superuser.help.inactivated')}"
                   data-isParentConstructionResource="${constructionResource}"
                   data-uniqueConstructionIdentifier="${uniqueConstructionIdentifier}"
                    ${dataset?.verified ? 'disabled' : ''}
                    ${!constructionResource && uniqueConstructionIdentifier ? 'disabled' : ''}>
                    <asset:image class="verifiedDatasetIcon" src="img/verifiedIcon.png"/>
                </a>
                <g:render template="/query/icons/unverifiedIcon" model="[dataset : dataset]"/>
            </g:if>
            <g:else>
                <g:render template="/query/icons/unverifiedIcon" model="[dataset: dataset]"/>
                <g:render template="/query/icons/verifiedIcon" model="[dataset: dataset]"/>
            </g:else>
        </span>
    </g:if>

    <g:if test="${forResourceRowDropdown}">
        <g:if test="${userService.getSuperUser(user) && !dataset?.unlockedFromVerifiedStatus}">
            <a class="verifyDatasetIconContainer" data-isVerifiedDataset="${dataset?.verified ?: false}"
               href="javascript:"
               data-isParentConstructionResource="${constructionResource}"
               data-uniqueConstructionIdentifier="${uniqueConstructionIdentifier}">
                %{-- Verify icon and text with onclick, currently in resource row dropdown--}%
                <div class="verifyDatasetLock ${dataset?.verified ? '' : 'hide'} ${(!constructionResource && uniqueConstructionIdentifier) ? 'removeClicks' : ''} doNotColor"
                      data-isParentConstructionResource="${constructionResource}"
                      data-uniqueConstructionIdentifier="${uniqueConstructionIdentifier}"
                    ${!constructionResource && uniqueConstructionIdentifier ? 'disabled' : ''}
                      onclick="neutralizeResourceRow(this, ${constructionResource}, '${uniqueConstructionIdentifier}')">
                    <asset:image class="neutralDatasetIcon float-left iconInDropDownLi"
                                 src="img/blankGrayShield.png"/>
                    <span class="textBreakSpace doNotColor">${message(code: 'neutralizeDataset')}</span>
                </div>
                %{-- Hack: neutralDataset and verifyDatasetLock are flipped in this case --}%
                <div class="neutralDataset hide ${!constructionResource && uniqueConstructionIdentifier ? 'removeClicks' : ''} doNotColor"
                    ${dataset?.verified ? 'disabled' : ''}
                      data-isParentConstructionResource="${constructionResource}"
                      data-uniqueConstructionIdentifier="${uniqueConstructionIdentifier}"
                    ${!constructionResource && uniqueConstructionIdentifier ? 'disabled' : ''}
                      onclick="verifyAndLockResourceRow(this, ${constructionResource}, '${uniqueConstructionIdentifier}', ${verifiedProductFlag})">
                    <asset:image class="verifiedDatasetIcon float-left iconInDropDownLi" src="img/verifiedIcon.png"/>
                    <span class="textBreakSpace doNotColor">${message(code: 'verifyDataset')}</span>
                </div>
            </a>
        </g:if>
    </g:if>

    <g:if test="${forNotRow}">
        <span class="verifyDatasetIconContainer" data-isVerifiedDataset="${dataset?.verified ?: false}">
            <g:if test="${userService.getSuperUser(user) && !dataset?.unlockedFromVerifiedStatus}">
                <a class="neutralDataset hide"
                   href="javascript:"
                   rel="popover" data-trigger="hover"
                   data-content="${message(code: 'unverifiedDataset.superuser.help')}"
                   onclick="verifyAndLockDatasetNotRow(this, ${changeInputBg?: false})">
                    <asset:image class="neutralDatasetIcon" src="img/blankGrayShield.png"/>
                    ${message(code: 'neutral')}
                </a>
                <a class="verifyDatasetLock ${dataset?.verified ? '' : 'hide'} defaultCursor"
                   href="javascript:"
                   rel="popover" data-trigger="hover"
                   data-isActivated="false"
                   data-activatedHelp="${message(code: 'verifiedDataset.superuser.help.activated')}"
                   data-content="${message(code: 'verifiedDataset.superuser.help.inactivated')}"
                   onclick="neutralizeDatasetNotRow(this, ${changeInputBg?: false})">
                    <asset:image class="verifiedDatasetIcon" src="img/verifiedIcon.png"/>
                    ${message(code: 'verified')}
                </a>
                <g:render template="/query/icons/unverifiedIcon" model="[dataset : dataset, showText: true]"/>
            </g:if>
            <g:else>
                <g:render template="/query/icons/unverifiedIcon" model="[dataset : dataset, showText: true]"/>
                <g:render template="/query/icons/verifiedIcon" model="[dataset : dataset, showText: true]"/>
            </g:else>
        </span>
    </g:if>

    <g:if test="${unsupported}">
        <span class="verifyDatasetIconContainer" data-isVerifiedDataset="false">
            <a class="neutralDataset hide defaultCursor"
               href="javascript:"
               rel="popover" data-trigger="hover"
               data-content="${message(code: 'verifyDataset.superuser.unsupported.help')}">
                <asset:image class="neutralDatasetIcon" src="img/blankGrayShield.png"/>
                ${message(code: 'neutral')}
                <span class="warningRed">(${message(code: 'deprecated')})</span>
            </a>
        </span>
    </g:if>
</g:if>
