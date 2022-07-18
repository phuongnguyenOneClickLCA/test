<%
    def userService = grailsApplication.mainContext.getBean("userService")
%>
<div class="wrapperForExistingReferenceLists container">
    <table class="table referenceListsTable">
        <thead><tr><th><g:message code="pdl.reference_lists"/></th><th>${classificationParameterQuestion.localizedQuestion}</th><th>${message(code:"import.import_resource").capitalize()}</th><th>&nbsp;</th></tr></thead>
        <tbody>
        <g:if test="${referenceListCounts}">
            <g:each in="${referenceListCounts}">
                <tr>
                    <td>
                        ${it.key.name}
                    </td>
                    <td>
                        <g:set var="classificationParamId" value="${it.key.classificationParamId}" />
                        ${classificationParams?.find({it.answerId.equals(classificationParamId)})?.localizedAnswer}
                    </td>
                    <td>
                        <productDataListRender:inactiveDataWarning productDataList="${it.key}"/> <productDataListRender:missmatchDataWarning productDataList="${it.key}"/> <g:message code="active"/> ${it.value?.get("actives")} / ${it.value?.get("all")}
                    </td>
                    <td>
                        <g:if test="${userService.getSuperUser(user)}">
                            <opt:link action="form" params="[id:it.key.id, classificationQuestionId: it.key.classificationQuestionId, classificationParamId: it.key.classificationParamId,  accountId: account.id, name: it.key.name, admin:false]" class="btn btn-primary"><g:message code="edit"/></opt:link>
                        </g:if>
                        <g:else>
                            <opt:link action="form" params="[id:it.key.id, classificationQuestionId: it.key.classificationQuestionId, classificationParamId: it.key.classificationParamId,  accountId: account.id, name: it.key.name]" class="btn btn-primary"><g:message code="view"/></opt:link>
                        </g:else>
                        <g:if test="${editable && userService.getSuperUser(user)}">
                            <a href="javascript:" class="btn btn-danger" onclick="removeRefListFromAccount('${it.key?.id}', '${account?.id}', '${message(code: 'warning')}', 'Are you sure you want to remove reference list ${it.key.name} from ${account?.companyName}?', 'Successfully removed reference list ${it.key.name} from ${account?.companyName}','${message(code: 'yes')}', '${message(code: 'back')}')">Remove (SU)</a>
                        </g:if>
                    </td>
                </tr>
            </g:each>
        </g:if>
        </tbody>
    </table>
</div>