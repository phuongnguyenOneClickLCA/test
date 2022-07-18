<%@ page import="com.bionova.optimi.core.domain.mongo.QuerySection; com.bionova.optimi.core.domain.mongo.Question" %>
<g:if env="development">
    <!-- Start of section template: ${formatDate(date: new Date(), format: 'dd.MM.yyyy HH:mm:ss.SSS')} -->
</g:if>
<g:set var="querySectionService" bean="querySectionService"/>
<g:set var="dataLoadingFromFile" value="${query.dataLoadingFromFile}"/>
<g:set var="outScope" value="${isBuildindMaterialQuery && disabledSections?.contains(section?.sectionId)}"/>
<g:set var="disabledClass" value="${outScope ? "disabled_text" : ""}"/>
<g:set var="entityClass" value="${entity ? entity.entityClass : entityClassResource?.resourceId}"/>
<g:set var="questions" value="${(List<Question>) querySectionService.getQuestionsByEntityClass(entityClass, query?.queryId, section?.questions)}"/>
<g:set var="projectLevelHiddenQuestionsForSection" value="${projectLevelHideableQuestions?.get(section.sectionId)}"/>
<g:set var="questions" value="${questions.findAll{!(it.questionId in projectLevelHiddenQuestionsForSection)}}"/>
<g:set var="isSectionExpandable"
       value="${g.isSectionExpandable(entity: entity, section: section, queryId: query?.queryId, overrideCollapse: sendMeData)}"/>
<g:set var="isLicensed" value="${querySectionService.isSectionLicensed(entity, section)}"/>
<g:if test="${(isLicensed || section?.hideOnly) && (!allowedSections || allowedSections.contains(section?.sectionId))}">
    <g:if test="${section?.hideOnly && !isLicensed}">
        <g:set var="hide" value="hide"/>
    </g:if>
    <g:if test="${isMain}">
        <g:set var="mainSectionId" value="${section?.sectionId}"/>
        <div class="querysection ${hide}"><a id="${mainSectionId}"></a>
    </g:if>
    <g:else>
        <div class="querySubSection ${hide}">
    </g:else>
    <g:if test="${section?.helpSettings && section?.localizedHelp}">
        <div style="display: table-cell;">
    </g:if>
    <g:if test="${section?.localizedName && questions}">
        <g:set var="querySectionService" bean="querySectionService"/>
        <div class="questionhead">
            <div class="questionHeadText">
                <g:if test="${!section?.hideHeadText}">
                    <h3 class="${disabledClass}"
                        style="display:inline;">
                        ${isMain && sectionNumber != null ? sectionNumber + '. ' : ''}
                        ${section.localizedName}${querySectionService.getLocalizedExtraName(section, indicator?.indicatorId)}
                    </h3>
                </g:if>
                <g:if test="${isSectionExpandable}">
                    %{-- << verificationPoint >> --}%
                    <g:if test="${section.verificationPoints}">
                        <opt:verificationPointIcon section="${section}"/>
                    </g:if>
                    <g:render template="/query/icons/verifyDatasetIcons" model="[forSection: true, section: (QuerySection) section]"/>
                    <div>
                        <i style="display:inline-block; margin-bottom: 15px; cursor:pointer; font-size:15px;"
                           onclick="$(this).next().toggleClass('hidden');
                           $(this).toggleClass('fa fa-plus').toggleClass('fa fa-minus');
                           collapseTable(this, 'sectionbody${section?.sectionId}')"
                           class="fa fa-plus fa-1x primary btnRemove"></i>
                        <a href="javascript:" class="expanded primary btnRemove"
                           style="display:inline-block; margin-left:5px; font-weight:bold; cursor:pointer;"
                           onclick="$(this).toggleClass('hidden');
                           $(this).prev().toggleClass('fa fa-minus').toggleClass('fa fa-plus');
                           collapseTable(this, 'sectionbody${section?.sectionId}')">
                            <g:message code="query.question.expand"/>
                        </a>
                    </div>
                </g:if>
                <g:if test="${outScope}">
                    <label class="${disabledClass}"><g:message code="scope.outOfScope"/></label>
                    <g:if test="${entity?.locked || entity?.superLocked || !modifiable}">
                        <a class="removeClicks">
                            <g:message code="scope.enableScope"/>
                        </a>
                    </g:if>
                    <g:else>
                        <a class="updateScope preventDoubleSubmit disabledOnLoad"
                           onclick="submitQuery('${query?.queryId}', 'queryForm', '${section?.sectionId}', null, null, true, null, ${indicator?.maxRowLimitPerDesign});">
                            <g:message code="scope.enableScope"/>
                        </a>
                    </g:else>
                </g:if>
                <g:elseif test="${section.localizedHelp && section.helpPopover}">
                    <span class="fiveMarginLeft tableHeadingPopover hidden-print" href="#" data-toggle="dropdown"
                          rel="popover" data-trigger="hover" data-content="${section.localizedHelp}">
                        <i class="far fa-question-circle"></i>
                    </span>
                </g:elseif>
                <g:if test="${query?.allowedFeatures?.contains("showCarbonData") && !indicator?.hideSectionResult && user && entity?.queryReadyPerIndicator?.get(indicator?.indicatorId)?.get(query?.queryId)}">
                    <div class="sectionImpactContainer" data-sectionId="${section?.sectionId}">
                        <i class="fas fa-circle-notch fa-spin oneClickColorScheme"></i>
                    </div>
                </g:if>
                <g:if test="${!isSectionExpandable}">
                    %{-- << verificationPoint >> --}%
                    <g:if test="${section.verificationPoints}">
                        <opt:verificationPointIcon section="${section}"/>
                    </g:if>
                    <g:render template="/query/icons/verifyDatasetIcons" model="[forSection: true, section: (QuerySection) section]"/>
                </g:if>
            </div>
        </div>
    </g:if>
    <g:if test="${section?.localizedHelp && !section?.helpSettings && !section?.helpPopover && !outScope}">
        <span class="help-block sectionbody${section?.sectionId}"
              style="display: ${isSectionExpandable ? 'none' : 'block'}">
            ${querySectionService.getLocalizedHelp(section)}
        </span>
    </g:if>
    <g:if test="${questions && !outScope}">
        <g:each in="${questions}" var="question" status="index">
            <queryRender:renderQuestion
                    indicator="${indicator}"
                    query="${query}"
                    divId="${divId}"
                    entity="${entity}"
                    parentEntity="${parentEntity}"
                    noDefaults="${noDefaults}"
                    modifiable="${modifiable}"
                    queryId="${query?.queryId}"
                    question="${question}"
                    section="${mainSection && !section.overrideMainSection ? mainSection : section}"
                    questionIndex="${index}"
                    groupMaterialsAllowed="${groupMaterialsAllowed}"
                    lastQuestion="${questions.size() - 1 == index ? true : false}"
                    projectLevelHideableQuestions="${projectLevelHideableQuestions}"
                    additionalQuestionLicensed="${additionalQuestionLicensed}"
                    preventChanges="${preventChanges}"
                    sendMeData="${sendMeData}"
                    linkedQuestions="${linkedQuestions}"
                    checkQuestionType="${checkQuestionType}"
                    dataTypeSelected="${dataTypeSelected}"
                    questions="${questions}"
                    entityClassResource="${entityClassResource}"
                    dataLoadingFromFile="${dataLoadingFromFile}"
                    eolProcessCache="${eolProcessCache}"
                    additionalCalcParamsForCurrentQuery="${additionalCalcParamsForCurrentQuery}"/>
            <g:if test="${dataLoadingFromFile && section.sectionId?.equals(dataLoadingFromFile.filterParametersList?.get("sectionId")) && questions.size() - 1 == index}">
                <asset:javascript src="betie.js"/>
                <a href="javascript:" id="dataLoadingFromFile" data-indicatorId="${indicator?.indicatorId}"
                   data-entityId="${parentEntity?.id}"
                   data-queryId="${query?.queryId}"
                   data-targetSectionId="${dataLoadingFromFile?.loadResourcesTarget?.get("sectionId")}"
                   data-targetQuestionId="${dataLoadingFromFile?.loadResourcesTarget?.get("questionId")}"
                   data-nomatchMsg="${dataLoadingFromFile.noMatchesWarning?.get("EN")}"
                   data-localizedOk="${message(code: "ok")}"
                   data-localizedCancel="${message(code: "cancel")}"
                   data-localizedRecipeTitle="${message(code: "recipe_select_title")}"
                   data-localizedSelect="${message(code: "select_a_recipe")}"
                   data-localizedHelpText="${message(code: "recipe_helpText")}"
                   data-localizedSuccessfulLoadMsg="${message(code: "betie.message.info.successfulLoad", default: "Valid combination. The formulation was loaded successfully! Formulation loaded: ")}"
                   onclick="dataLoadingFromFile('${dataLoadingFromFile.loadingResourcesFile}')"
                   class="btn btn-primary">${dataLoadingFromFile.buttonName?.get("EN")}</a>
            </g:if>
        </g:each>
    </g:if>
    <g:each in="${querySectionService.getIncludedSections(section?.includeSectionIds)}" var="includedSection">
        <g:render template="/query/section"
                  model="[parentEntity: parentEntity, entity: entity, queryId: query?.queryId, mainSectionId: mainSectionId, mainSection: section, isMain: false, section: includedSection, display: true, printable: printable, modifiable: modifiable]"/>
    </g:each>
    <g:each in="${section?.sections}" var="subSection">
        <g:render template="/query/section"
                  model="[parentEntity: parentEntity, entity: entity, queryId: query?.queryId, mainSection: section, mainSectionId: mainSectionId, isMain: false, section: subSection, display: true, printable: printable, modifiable: modifiable]"/>
    </g:each>
    <g:if test="${section?.helpSettings && section?.localizedHelp}">
        </div>
        <div style="display: table-cell; min-width: 20%; max-width: 70%; width: ${section.helpSettings.width}%;">
            <g:if test="${section.helpSettings.expanded}">
                <p style="line-height: 18px; -moz-border-radius: 7px; border-radius: 7px; padding: 5px; background-color: #${section?.helpSettings.bgColour ? section?.helpSettings.bgColour : 'CCEEFF'}; color: #${section.helpSettings.textColour ? section.helpSettings.textColour : '0066CC'};">
                    ${section.localizedHelp}
                </p>
            </g:if>
            <g:else>
                <p style="line-height: 18px; height: 36px; -moz-border-radius: 7px; border-radius: 7px; padding: 5px; overflow: hidden; background-color: #${section?.helpSettings.bgColour ? section?.helpSettings.bgColour : 'CCEEFF'}; color: #${section.helpSettings.textColour ? section.helpSettings.textColour : '0066CC'};">
                    <span id="help${section?.sectionId}span"
                          style="height: 18px; display: inline-block; overflow: hidden;">
                        ${section.localizedHelp}
                    </span>
                    <br/>
                    <a href="javascript:;" id="help${section?.sectionId}">
                        <g:message code="more"/>
                    </a>
                    <script type="text/javascript">
                        $('#help${section?.sectionId}').on('click', function (event) {
                            var p = $(this).parent('p')
                            var helpSpan = $("#help${section?.sectionId}span")
                            if (parseInt(p.css('height')) === 36) {
                                p.css('height', 'auto');
                                helpSpan.css("height", "");
                                helpSpan.css("overflow", "");
                                $(this).text('${message(code: 'less')}')
                            } else {
                                p.css('height', '36px');
                                helpSpan.css("height", "18px");
                                helpSpan.css("overflow", "hidden");
                                $(this).text('${message(code: 'more')}')
                            }
                        });
                    </script>
                </p>
            </g:else>
        </div>
    </g:if>
    </div>
</g:if>

<g:if env="development">
    <!-- End of section template: ${formatDate(date: new Date(), format: 'dd.MM.yyyy HH:mm:ss.SSS')} -->
</g:if>
