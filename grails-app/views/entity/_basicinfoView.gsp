<h2 class="h2heading">
    <sec:ifLoggedIn>
        <g:if test="${parentEntity?.id}">
            <g:if test="${showIfcButtons}">
                <g:message code="importMapper.resolver.importing_data"/>
            </g:if>
                <g:if test="${!showIfcButtons}">
                    <asset:image src="query_heading.png" class="queryIcon"/>
                </g:if>
                <g:if test="${entity}">
                    ${entity.operatingPeriodAndName}
                </g:if>
                <g:else>
                    ${parentEntity.name}
                </g:else>
        </g:if>
        <g:elseif test="${entity?.id}">
            <asset:image src="query_heading.png" class="queryIcon"/>
                <g:if test="${childEntity}">
                    ${childEntity.operatingPeriodAndName}
                </g:if>
                <g:else>
                    ${entity.name}
                </g:else>
        </g:elseif>
        <g:elseif test="${temporaryImportData}">
          <g:abbr value="${temporaryImportData?.temporaryEntity?.name}" maxLength="50" />
        </g:elseif>
        <g:else>
            <img class="certificationLogo" onerror="this.style.display='none'" src="${indicator?.certificationImageResourceId ? '/static/logoCertificate/' + indicator?.certificationImageResourceId[0] + '.png' : '' }"/>
        </g:else>
    </sec:ifLoggedIn>
    <sec:ifNotLoggedIn>
        <g:if test="${parentEntity?.id}">
            <g:if test="${entity}">
                ${entity.operatingPeriodAndName}
            </g:if>
            <g:else>
               ${parentEntity.name}
            </g:else>
        </g:if>
        <g:elseif test="${entity?.id}">
            <g:if test="${childEntity}">
               ${childEntity.operatingPeriodAndName}
            </g:if>
            <g:else>
                <g:abbr value="${entity.name}" maxLength="38" />
            </g:else>
        </g:elseif>
        <g:elseif test="${temporaryImportData}">
            <g:abbr value="${temporaryImportData?.temporaryEntity?.name}" maxLength="50" />
        </g:elseif>
    </sec:ifNotLoggedIn>
    <g:if test="${showIfcButtons}">
        <div class="pull-right hide-on-print">
            <a href="javascript:" class="btn" style="font-weight: normal;" onclick="fullScreenPopup('${createLink(controller: 'importMapper', action: 'listAmbiguousData')}');"><g:message code="entity.details.tab.datasummary" /></a>
            <g:link controller="importMapper" action="cancel" class="btn" style="font-weight: normal;" onclick="return modalConfirm(this);"
                    data-questionstr="${message(code: 'importMapper.index.cancel')}"
                    data-truestr="${message(code: 'yes')}" data-falsestr="${message(code: 'no')}"
                    data-titlestr="Cancel"><g:message code="cancel" /></g:link>
            <g:link controller="importMapper" action="generateExcelFromDatasets" params="[returnUserTo: 'resolver', entityId: entityId, childEntityId: childEntityId]" class="btn btn-primary" style="font-weight: normal;"><g:message code="results.expand.download_excel" /></g:link>
            <a href="javascript:" style="font-weight: normal;" id="notIdentifiedSaveMappings" onclick="saveMappings(this);" class="btn btn-primary" disabled="disabled"><g:message code="importMapper.save_mappings" /></a>
            <g:if test="${showMergeWarning}">
                <a href="javascript:" style="font-weight: normal;" class="btn btn-primary" id="resolverOverwriteButton" onclick="showResolverOverwriteWarning()"><g:message code="continue"/></a>
            </g:if>
            <g:else>
                <g:submitButton name="save" value="${message(code: 'continue')}" class="btn btn-primary" id="ifcContinue" onclick="doubleSubmitPrevention('saveResolver');" style="font-weight: normal;" />
            </g:else>
        </div>
    </g:if>
</h2>

