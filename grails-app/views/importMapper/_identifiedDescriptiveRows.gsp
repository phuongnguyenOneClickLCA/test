<g:set var="optimiResourceService" bean="optimiResourceService"/>
<g:if test="${datasets}">
    <g:each in="${datasets}" var="dataset">
        <g:set var="ifcClass" value="${dataset.trainingData?.get("CLASS")?.toUpperCase()}"/>
        <tr id="identified${dataset.manualId}">
            <td class="tdalignmiddle">
                <span class="setHiddenValueForSort hidden"></span>
                <g:set var="ifcClass" value="${dataset.trainingData?.get("CLASS")?.toUpperCase()}"/>
                <g:set var="classRemapChoices" value="${classRemapChoice?.allowedDescriptiveClassRemappings}"/>
                <g:if test="${ifcClass && classRemapChoices}">
                    <select data-datasetManualId="${dataset.manualId}" class="classRemapSelect"
                            name="classRemapSelectDropdown">
                        <g:if test="${!classRemapChoices*.toUpperCase().contains(ifcClass)}">
                            <option value="${ifcClass}" selected><g:message code="importMapper.OTHER"/></option>
                        </g:if>
                        <g:each in="${(List<String>) classRemapChoices}" var="choise">
                            <option value="${choise}" ${choise.toUpperCase().equals(ifcClass) ? 'selected' : ''}>${choise.toUpperCase()}</option>
                        </g:each>
                    </select>
                </g:if>
                <g:elseif test="${ifcClass}">
                    <g:abbr value="${ifcClass}" maxLength="20"/>
                </g:elseif>
                <g:if test="${dataset.allImportDisplayFields}">&nbsp;<a href="javascript:" id="${dataset.manualId}info" onclick="openCombinedDisplayFieldsResolver('${dataset.manualId}','${dataset.manualId}info')" class="pull-right"><i class="far fa-file-alt fa-lg greenInfoBubble" aria-hidden="true"></i></a></g:if>
                <queryRender:importMapperBimCheckerErrors dataset="${dataset}"/>
            </td>
            <td class="tdalignmiddle" style="min-width: 280px !important;">
                <input type="hidden" name="questionId.${dataset.manualId}" value="${dataset.questionId}" />
                <g:set var="answer" value="${dataset.answerIds?.getAt(0)}" />
                <input type="text" value="${answer ?: ''}" name="answerIds.${dataset.manualId}" id="answerIds${dataset.manualId}" class="input-xlarge"/>
            </td>
        </tr>
    </g:each>
</g:if>