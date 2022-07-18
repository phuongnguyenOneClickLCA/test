<%@ page import="com.bionova.optimi.core.domain.mongo.Entity; com.bionova.optimi.core.domain.mongo.Dataset" %>
<div style="display: inline-block">
    <label style="display: inline-block">${question?.localizedQuestion}<g:if test="${!question?.optional}"> (<g:message code="mandatory" />)</g:if></label>
    %{-- << verificationPoint >> --}%
    <g:if test="${question?.verificationPoints}">
        <opt:verificationPointIcon question="${question}"/>
    </g:if>
    <g:render template="/query/icons/verifyDatasetIcons" model="[unsupported: true]"/>
</div>
<div class="queryQuestionInput ${dataset?.unlockedFromVerifiedStatus ? 'unlockedVerifiedDatasetStatusBg' : dataset?.verified ? 'verifiedDatasetBackground' : ''}">
    <g:set var="files" value="" />
    <g:if test="${question?.questionId == 'entityImage' && entity?.hasImage}">
        <g:set var="files" value="${opt.showImage(entity: entity)}"/>
    </g:if>
    <g:else>
        <g:set var="files" value="${queryRender.filesForQuestion(entity: entity ?: parentEntity, queryId: query?.queryId, questionId: question?.questionId, dataset: dataset)}"/>
    </g:else>
    <g:if test="${question?.questionId == 'entityImage' && entity?.hasImage}">
        <div class="img-holder">
            ${files}
            <g:if test="${entity?.id}">
                <g:link controller="entity" action="removeImage" params="[entityId: entity?.id]" class="link btn btn-danger btn-small">Delete image</g:link>
            </g:if>
        </div>
    </g:if>
    <g:else>
        ${files}
    </g:else>

    <div class="input-append">
        <input type="file" ${showMandatory && !files && !optional ? ' style=\"border: 1px solid red;\"' : ''}
               name="${fieldName}" id="${divId}"
               onchange="removeBorder(this);
               enableReadButton(${injectXmlButton}, '${question?.questionId}', ${fecEpdInjectButton})"
               class="input-xlarge ${dataset?.verified ? 'removeClicks' : ''} ${Dataset.VERIFIABLE_INPUT}" ${disabledAttribute}
               value=""/>
        <g:if test="${question?.allowedExtensions}">
            (<g:join in="${question?.allowedExtensions}" delimiter=", "/>)
        </g:if>
        <g:if test="${question?.fileMaxAmount || question?.maximumSize}">
            (max. ${question?.fileMaxAmount ? question?.fileMaxAmount + ' ' + message(code: 'files') : ''}<g:if
                test="${question?.maximumSize}">${question?.fileMaxAmount ? ' / ' : ''} ${question?.formattedMaxSize}</g:if>)
        </g:if>
    </div>
<%-- removed for: 0012093 <opt:submit entity="${entity}" name="upload" value="${message(code:'upload') }" class="btn btn-primary" modifiable="${modifiable}" /> --%>
    <g:if test="${question?.localizedHelp}">
        <g:if test="${question?.helpType?.equalsIgnoreCase('hoverover')}">
            <span class="icon-question-sign questionHelpHoverover" rel="popover" data-trigger="hover"
                  data-content="${question?.localizedHelp}"></span>
        </g:if>
        <g:else><span class="help-block">${question?.localizedHelp}</span></g:else>
    </g:if>
</div>
<g:if test="${injectXmlButton}">
    <a id="rsetReadButton${question?.questionId}" ${disabledAttribute}
       class="btn btn-primary${disableInjectXmlButton ? ' removeClicks' : ''}"
       style="font-weight: bold;" href="javascript:"
       onclick="readFromRset(this, '${parentEntity?.id}', '${query?.queryId}', 'queryForm');">
        ${message(code: 'query.read_rset')}
    </a>
    <g:if test="${rsetImportMappingTable?.get(question?.questionId)}">

        ${rsetImportMappingTable?.get(question?.questionId)}
        <div class="help-block">${message(code: 'query.export_rsee_reminder')}</div>
        <button class="btn btn-primary" id="injectAnswerFromRsetButton${question.questionId}" disabled>
            <a style="font-weight: bold; color: #FFFFFF"
               href="javascript:"
               onclick="injectAnswerFromRset('${question?.questionId}', '${message(code: 'lca_lcc.warning')}', '${query?.queryId}');">
                ${message(code: 'query.import_rset')}
            </a>
        </button>

    </g:if>
    <div class="clearfix"></div>
</g:if>
<g:elseif test="${fecEpdInjectButton}">
    <a id="fecEpdReadButton" ${disabledAttribute} class="btn btn-primary" style="font-weight: bold;" href="javascript:"
       onclick="injectAnswerFromFecEpd(this, '${parentEntity?.id}', '${entity?.id?.toString()}', '${indicator?.indicatorId}', '${query?.queryId}', 'queryForm');" ${disableInjectXmlButton ? 'disabled=\"disabled\"' : ''}>${message(code: 'query.read_rset')}</a>
    <div class="clearfix"></div>
</g:elseif>