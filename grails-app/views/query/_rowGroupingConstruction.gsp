<%
    def userService = grailsApplication.mainContext.getBean("userService")
%>
<%@ page import="java.text.DateFormat" %>
<g:set var="resourceTypeService" bean="resourceTypeService"/>
<g:set var="questionService" bean="questionService"/>
<thead id="${idForNewHeading ?: question?.questionId + 'defaultConstructionRow'}">
    <tr>
        <th colspan="4"><h4><g:message code="create_a_group"/></h4></th>
    </tr>
    <tr>
        <th>
            <label for="${question?.questionId}AssemblyName">
                <g:message code="group_name"/>:<br/>
                <input maxlength="500" type="text" class="redBorder" oninput="$(this).removeClass('redBorder')"
                       style="max-width: 165px;" data-target="${question?.questionId}AssemblyName"
                       name="${question?.questionId}AssemblyName" value="${constructionDataset?.groupingDatasetName ? "${message(code:'new')?.toUpperCase()} ${constructionDataset?.groupingDatasetName}" : constructionAsResource?.localizedName ? "${message(code:'new')?.toUpperCase()} ${constructionAsResource?.localizedName}" : ''}"/>
            </label>
        </th>
        <th>
            <label for="${question?.questionId}AssemblyQuantity">
                <g:message code="reference_quantity"/>:
                <a href="#" class="infopopover" data-toggle="dropdown" rel="popover" data-trigger="hover"
                   data-content="${message(code: "reference_quantity.info")}">
                    <i class="icon-question-sign"></i>
                </a>
                <br/>
                <input maxlength="50" type="text" class="numeric" style="max-width: 45px; text-align:right;"
                       data-target="${question?.questionId}AssemblyQuantity"
                       name="${question?.questionId}AssemblyQuantity" value="${constructionDataset?.quantity ?: 1}"/>
                <select style="max-width: 47px;" class="redBorder" onchange="$(this).removeClass('redBorder')"
                        data-target="${question?.questionId}AssemblyUnit" name="${question?.questionId}AssemblyUnit">
                    <option value=""></option>
                    <g:if test="${unitForConstruction}">
                        <g:each in="${unitForConstruction}" var="unit">
                            <option value="${unit}" ${constructionDataset?.userGivenUnit?.equalsIgnoreCase(unit) ? 'selected' : ''}>${unit}</option>
                        </g:each>
                    </g:if>
                </select>
            </label>
        </th>
        <th>
            <g:if test="${constructionResourceTypes}">
                <g:set var="subTypeDefault" value="${constructionAsResource?.resourceSubType ?: question?.constructionDefaultSubType}"/>
                <label for="${question?.questionId}GroupSubType">
                    <g:message code="main.list.type"/>:<br/>
                    <select data-target="${question?.questionId}GroupSubType" name="${question?.questionId}GroupSubType"
                            style="max-width: 150px;">
                        <g:each in="${constructionResourceTypes}" var="subType">
                            <option value="${subType?.subType}" ${subTypesubType?.subType?.equals(subTypeDefault) ? "selected=\"selected\"" : ""}>${resourceTypeService.getLocalizedName(subType)}</option>
                        </g:each>
                    </select>
                </label>
            </g:if>
        </th>
        <th>
            <g:set var="commentDefault" value="${constructionDataset?.additionalQuestionAnswers?.get("comment") ?: "Created on ${java.text.DateFormat.getDateInstance(DateFormat.SHORT, userService.getUserLocale(user.localeString))?.format(new Date())}, ${parentEntity?.name}"}"/>
            <label for="${question?.questionId}AssemblyComment">
                <g:message code="attachment.comment"/>:<br/>
                <input maxlength="500" type="text" style="max-width: 140px;" data-target="${question?.questionId}AssemblyComment"
                       name="${question?.questionId}AssemblyComment"
                       value="${commentDefault}"/>
            </label>
        </th>
        <th>
            <g:set var="constructionDescription" value="${constructionDataset?.additionalQuestionAnswers?.get("productDescription")}"/>
            <label for="${constructionAsResource?.productDescription}">
                <g:message code="entity.show.task.description"/>:<br/>
                <input maxlength="500" type="text" style="max-width: 140px;"
                       name="constructionDescription" data-target="constructionDescription"
                       value="${constructionDescription}"/>
            </label>
        </th>
        <g:if test="${showInMappingQuestion}">
            <th>
                <g:set var="defaultShowInMappingQuestionAnswer"
                       value="${constructionDataset?.additionalQuestionAnswers?.get(showInMappingQuestion.questionId) ?: question?.additionalQuestionDefaults?.get(showInMappingQuestion.questionId)}"/>
                <input type="hidden" data-target="${question?.questionId}ShowInMappingQuestionId"
                       value="${showInMappingQuestion.questionId}"/>
                <label for="${question?.questionId}ShowInMappingQuestion">
                    ${showInMappingQuestion.localizedQuestion}:<br/>
                    <select data-target="${question?.questionId}ShowInMappingQuestion"
                            name="${question?.questionId}ShowInMappingQuestion" style="max-width: 150px;">
                        <g:each in="${questionService.getFilteredChoices(parentEntity, question, null, showInMappingQuestion)}" var="choice">
                            <option value="${choice.answerId}" ${defaultShowInMappingQuestionAnswer?.equalsIgnoreCase(choice.answerId) ? "selected=\"selected\"" : ""}${choice.disabled ? " class=\"disabledSelectOption\" disabled=\"disabled\"" : ""}>${choice.localizedAnswer}</option>
                        </g:each>
                    </select>
                </label>
            </th>
        </g:if>
        <g:if test="${brandImages}">
            <th>
                <label for="brandImageId">
                    <g:message code="account.brand_image"/>:
                    <i class="icon-question-sign longcontent" rel="popover" data-trigger="hover"
                       data-content="${message(code: 'constructions.brand_help_info')}"></i>
                    <br/>
                    <g:select name="brandImageId" style="max-width:150px;" data-target="brandImageId" from="${brandImages}"
                              optionKey="${{ it.id ? it.id : 'null' }}"
                              optionValue="${{ it?.name }}"
                              value="${construction?.brandImageId ?: constructionAsResource?.brandImageId ?: !user.internalUseRoles ? account?.defaultBrandingImage : 'null'}"
                              noSelection="['null': '']"/>
                </label>
            </th>
        </g:if>
        <g:if test="${privateClassificationQuestion}">
            <th>
                <g:set var="defaultPrivateClassificationQuestionAnswer"
                       value="${question?.additionalQuestionDefaults?.get(privateClassificationQuestion.questionId)}"/>
                <input type="hidden" data-target="${question?.questionId}PrivateClassificationQuestionId"
                       value="${privateClassificationQuestion.questionId}"/>
                <label for="${question?.questionId}PrivateClassificationQuestion">
                    ${privateClassificationQuestion.localizedQuestion}:<br/>
                    <select data-target="${question?.questionId}PrivateClassificationQuestion"
                            name="${question?.questionId}PrivateClassificationQuestion" style="max-width: 150px;">
                        <g:each in="${questionService.getFilteredChoices(parentEntity, question, null, privateClassificationQuestion)}"
                                var="choice">
                            <option value="${choice.answerId}" ${defaultPrivateClassificationQuestionAnswer?.equals(choice.answerId) ? "selected=\"selected\"" : ""}${choice.disabled ? " class=\"disabledSelectOption\" disabled=\"disabled\"" : ""}>${choice.localizedAnswer}</option>
                        </g:each>
                    </select>
                </label>
            </th>
        </g:if>
        <g:if test="${indicator?.groupingClassificationParamId}">
            <input type="hidden" id="${question?.questionId}LocalizedPublicWarning"
                   data-target="${question?.questionId}LocalizedPublicWarning"
                   value="${message(code: "save_for_public.info")}"/>
            <input type="hidden" id="${question?.questionId}LocalizedPrivateWarning"
                   data-target="${question?.questionId}LocalizedPrivateWarning"
                   value="${message(code: "save_for_account.info")}"/>
            <th>
                <label for="${question?.questionId}GroupCreationMethod"><g:message code="save_for"/>:
                    <g:if test="${account}">
                        <a href="#" class="groupHelpPopover" data-toggle="dropdown" rel="popover" data-trigger="hover"
                           data-html="true"
                           data-content="<b>${message(code: "save_for_project")}:</b> ${message(code: "save_for_project.info")}<br/><br/><b>${message(code: "save_for_account", args: [account.companyName])}:</b> ${message(code: "save_for_account.info")}<br/><br/><g:if
                                   test="${!createConstructionsAllowed}"><img src=&quot;/app/assets/img/infosign_small.png&quot;> ${message(code: "grouping.expert_license_required")}<br/><br/></g:if><b>${message(code: "save_for_public", args: [account.companyName])}:</b> ${message(code: "save_for_public.info")}</br><br/><g:if
                                   test="${!publicGroupAllowed}"><img src=&quot;/app/assets/img/infosign_small.png&quot;>  ${message(code: "grouping.publishing_license_required")}</g:if>"><i
                                class="icon-question-sign"></i></a>
                    </g:if>
                    <g:else>
                        <a href="#" class="groupHelpPopover" data-toggle="dropdown" rel="popover" data-trigger="hover"
                           data-html="true"
                           data-content="<b>${message(code: "save_for_project")}:</b> ${message(code: "save_for_project.info")}<br/><br/><img src=&quot;/app/assets/img/infosign_small.png&quot;> ${message(code: "grouping.account_required")}"><i
                                class="icon-question-sign"></i></a>
                    </g:else>
                    <br/>
                    <select data-target="${question?.questionId}GroupCreationMethod"
                            name="${question?.questionId}GroupCreationMethod" style="max-width: 150px;">
                        <option value="dummy"><g:message code="save_for_project"/></option>
                        <g:if test="${account}">
                            <g:if test="${createConstructionsAllowed}">
                                <option value="private" data-accountId="${account.id.toString()}"><g:message
                                        code="save_for_account" args="[account.companyName]"/></option>
                            </g:if>
                            <g:else>
                                <option value="dummy" class="disabledSelectOption" disabled><g:message
                                        code="save_for_account" args="[account.companyName]"/></option>
                            </g:else>
                            <g:if test="${publicGroupAllowed}">
                                <option value="public" data-accountId="${account.id.toString()}"><g:message
                                        code="save_for_public" args="[account.companyName]"/></option>
                            </g:if>
                            <g:else>
                                <option value="dummy" class="disabledSelectOption" disabled><g:message
                                        code="save_for_public" args="[account.companyName]"/></option>
                            </g:else>
                        </g:if>
                        <g:else>
                            <option value="dummy" class="disabledSelectOption" disabled><g:message code="save_for_account"
                                                                                                   args="[message(code: 'user.details.organization')]"/></option>
                            <option value="dummy" class="disabledSelectOption" disabled><g:message code="save_for_public"
                                                                                                   args="[message(code: 'user.details.organization')]"/></option>
                        </g:else>
                    </select>
                </label>
            </th>
        </g:if>
        <g:else>
            <th>
                <label for="${question?.questionId}GroupCreationMethod"><g:message code="save_for"/>:
                    <g:if test="${account}">
                        <a href="#" class="groupHelpPopover" data-toggle="dropdown" rel="popover" data-trigger="hover"
                           data-html="true"
                           data-content="<b>${message(code: "save_for_project")}:</b> ${message(code: "save_for_project.info")}<br/><br/><b>${message(code: "save_for_account", args: [account.companyName])}:</b> ${message(code: "save_for_account.info")}<br/><br/><b>${message(code: "save_for_public", args: [account.companyName])}:</b> ${message(code: "save_for_public.info")}"><i
                                class="icon-question-sign"></i></a>
                    </g:if>
                    <g:else>
                        <a href="#" class="groupHelpPopover" data-toggle="dropdown" rel="popover" data-trigger="hover"
                           data-html="true"
                           data-content="<b>${message(code: "save_for_project")}:</b> ${message(code: "save_for_project.info")}<br/><br/><b>${message(code: "save_for_account", args: [message(code: 'user.details.organization')])}:</b> ${message(code: "save_for_account.info")}<br/><br/><b>${message(code: "save_for_public", args: [message(code: 'user.details.organization')])}:</b> ${message(code: "save_for_public.info")}"><i
                                class="icon-question-sign"></i></a>
                    </g:else><br/>
                    <select data-target="${question?.questionId}GroupCreationMethod"
                            name="${question?.questionId}GroupCreationMethod" style="max-width: 150px;">
                        <option value="dummy"><g:message code="save_for_project"/></option>
                        <g:if test="${account}">
                            <option value="dummy" class="disabledSelectOption" disabled><g:message code="save_for_account"
                                                                                                   args="[account.companyName]"/></option>
                            <option value="dummy" class="disabledSelectOption" disabled><g:message code="save_for_public"
                                                                                                   args="[account.companyName]"/></option>
                        </g:if>
                        <g:else>
                            <option value="dummy" class="disabledSelectOption" disabled><g:message code="save_for_account"
                                                                                                   args="[message(code: 'user.details.organization')]"/></option>
                            <option value="dummy" class="disabledSelectOption" disabled><g:message code="save_for_public"
                                                                                                   args="[message(code: 'user.details.organization')]"/></option>
                        </g:else>
                    </select>
                </label>
            </th>
        </g:else>
        <th><div style="width: 15px;"></div></th>
        <th><a href="javascript:" style="margin-bottom: 7px;"
               onclick="validateGroup('${indicator?.indicatorId}', '${question?.questionId}', '${mainSectionId}', '${query?.queryId}', '${entity?.id?.toString()}', this, '${message(code: "ok")}', '${message(code: "cancel")}', '${existingParentConstructionDatasetId}')"
               class="btn btn-primary"><g:message code="save"/></a></th>
        <th><a href="javascript:" style="margin-bottom: 7px;"
               onclick="closeGroupingContainer('${question?.questionId}RowGroupingContainer', '${idForNewHeading}', '${question?.questionId}defaultConstructionRow', '${question?.questionId}RowGroupingContainerTableBody');" class="btn"><g:message
                    code="cancel"/></a></th>
        <%--<th><label for="${question?.questionId}AssemblyBuildingPart">Building part: <input maxlength="200" type="text" style="max-width: 120px;" id="${question?.questionId}AssemblyBuildingPart" name="${question?.questionId}AssemblyBuildingPart" /></label></th>--%>
    </tr>
</thead>