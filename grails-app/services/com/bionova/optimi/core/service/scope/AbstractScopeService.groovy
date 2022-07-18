package com.bionova.optimi.core.service.scope

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.Application
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.PredefinedScope
import com.bionova.optimi.core.domain.mongo.PredefinedScopeAndQuery
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.QuestionAnswerChoice
import com.bionova.optimi.core.domain.mongo.ReportIncompleteWarning
import com.bionova.optimi.core.domain.mongo.ScopeToSave
import com.bionova.optimi.core.service.ApplicationService
import com.bionova.optimi.core.service.QueryService
import com.bionova.optimi.core.service.SimulationToolService
import grails.converters.JSON
import grails.web.servlet.mvc.GrailsParameterMap

abstract class AbstractScopeService implements ScopeService {

    ApplicationService applicationService
    QueryService queryService
    SimulationToolService simulationToolService

    List<String> includedStagesForCurrentScope

    static enum warning {
        recommendedAlert, requiredAlert
    }

    /**
     * Fetches Scope values from the config files
     * @param indicatorList
     * @return fields to add to the model map
     */
    @Override
    Map getScopeValues(List<Indicator> indicatorList) {
        List<PredefinedScope> predefinedScopes = Application.collection.findOne([applicationId: getApplicationId()], [predefinedScopes: 1])?.predefinedScopes?.collect({it as PredefinedScope})
        List<PredefinedScope> allowedScopes

        allowedScopes = predefinedScopes?.findAll {
            it?.compatibleIndicatorList?.any {
                indicatorId -> indicatorList?.find { it.indicatorId == indicatorId }
            }
        } ?: []

        Query defaultQuery = queryService.getQueryByQueryId(getDefaultQueryId())
        List<Question> scopeQuestions = defaultQuery?.getQuestionsBySection(getSectionId())
        //jsonScope could be null
        JSON jsonScopes = getJsonScopes(allowedScopes)

        return [scopeQuestions: scopeQuestions, allowedScopes: allowedScopes, jsonScopes: jsonScopes]
    }

    @Override
    Map addValueScopeToModel(Map model, List<Indicator> indicatorList) {
        def scopeValues = getScopeValues(indicatorList)

        List<PredefinedScope> allowedScopes = scopeValues.allowedScopes
        List<Question> scopeQuestions = scopeValues.scopeQuestions
        def jsonScopes = scopeValues.jsonScopes

        model.putAll([allowedScopes: allowedScopes, scopeQuestions: scopeQuestions, jsonScopes: jsonScopes])

        return model
    }

    @Override
    Map<String, String> getLocalizedMap(ScopeToSave scope) {
        List<Question> scopeQuestions = getScopeQuestions()

        return getLocalizedMap(scope, scopeQuestions)
    }

    @Override
    Map<String, String> getLocalizedMap(ScopeToSave scope, List<Question> scopeQuestions) {
        String projectTypeLocalized
        String frameTypeLocalized
        String projectScopeLocalized

        if (scope) {
            String projectType = scope.projectType
            String frameType = scope.frameType
            List<String> projectScopeList = scope.lcaCheckerList

            if (scopeQuestions) {
                if (projectType) {
                    projectTypeLocalized = scopeQuestions.find { it.questionId == "projectType" }?.choices?.find { it.answerId == projectType }?.localizedAnswer
                }
                if (frameType) {
                    frameTypeLocalized = scopeQuestions.find { it.questionId == "frameType" }?.choices?.find { it.answerId == frameType }?.localizedAnswer
                }
                if (projectScopeList) {
                    List<QuestionAnswerChoice> projectScopeChoices = scopeQuestions.find { it.questionId == "projectScope" }?.choices

                    projectScopeLocalized = projectScopeList?.collect { s ->
                        projectScopeChoices?.find { it.answerId == s }?.localizedAnswer ?: "undefined"
                    }?.toString()

                    projectScopeLocalized = projectScopeLocalized ? projectScopeLocalized.replaceAll("\\[|\\]", "") : null
                }
            }

        }

        projectTypeLocalized = projectTypeLocalized ? projectTypeLocalized.toLowerCase() : "undefined"
        frameTypeLocalized = frameTypeLocalized ? frameTypeLocalized.toLowerCase() : "undefined"
        projectScopeLocalized = projectScopeLocalized ? projectScopeLocalized.toLowerCase() : "undefined"

        return [projectTypeLocalized: projectTypeLocalized, frameTypeLocalized: frameTypeLocalized, projectScopeLocalized: projectScopeLocalized]
    }

    @Override
    Boolean isIndicatorCompatible(Indicator indicator, PredefinedScope scope) {
        if (scope) {
            String controlledAdditionalQuestionId = scope.incompleteWarning?.controlledAdditionalQuestionId
            if (controlledAdditionalQuestionId) {
                Boolean isCompatible = indicator?.indicatorQueries?.any {
                    it.additionalQuestionIds?.contains(controlledAdditionalQuestionId)
                }
                return isCompatible
            }
        }

        return false
    }

    @Override
    Question getControlledQuestion(String controlledAdditionalQuestionId) {
        Query additionalQuestionsQuery = queryService.getQueryByQueryId(Constants.ADDITIONAL_QUESTIONS_QUERY_ID, true)

        return additionalQuestionsQuery?.getAllQuestions()?.find({ Question q -> q.questionId == controlledAdditionalQuestionId })
    }

    @Override
    JSON getJsonScopes(List<PredefinedScope> allowedScopes) {
        def jsonScopes = allowedScopes?.collectEntries { [(it.scopeId): [frameTypeDefault: it.frameTypeDefault, projectTypeDefault: it.projectTypeDefault, lcaCheckerDefaultList: it.lcaCheckerDefaultList]] }
        jsonScopes = jsonScopes ? jsonScopes as JSON : null

        return jsonScopes
    }

    @Override
    Entity addIndicatorsToEntity(GrailsParameterMap params, Entity child, Entity parent) {
        List<String> disabledIndicatorIds = parent?.getIndicatorIds()

        if (params?.indicatorIdList) {
            List<String> chosenIndicatorIds = params.list("indicatorIdList")

            if (chosenIndicatorIds && disabledIndicatorIds) {
                disabledIndicatorIds.removeAll(chosenIndicatorIds)
            }
        }

        child?.disabledIndicators = disabledIndicatorIds

        return child
    }

    @Override
    GString getPopupTable(String operatingPeriodAndName, ScopeToSave scope, List<Question> scopeQuestions) {
        String tableClass = "table table-striped"
        String scopeRows = getScopeRows(scope, scopeQuestions, operatingPeriodAndName)

        GString stringTable = """
                    <table class='${tableClass}'>
                        $scopeRows
                    </table>"""

        return stringTable
    }

    @Override
    String getScopeRows(ScopeToSave scope, scopeQuestions = null, String operatingPeriodAndName = null) {
        if (!scopeQuestions) {
            scopeQuestions = getScopeQuestions()
        }

        Map<String, String> locMap = getLocalizedMap(scope, scopeQuestions)
        String scopeRows = """
                        <tr><td><strong>${simulationToolService.getLocalizedMessage("popupDesign.projectType")}</strong></td>
                            <td>${locMap.projectTypeLocalized?.encodeAsHTML()}</td>
                        </tr>
                        <tr><td><strong>${simulationToolService.getLocalizedMessage("popupDesign.frameType")}</strong></td>
                            <td>${locMap.frameTypeLocalized?.encodeAsHTML()}</td>
                        </tr>
                        <tr><td><strong>${simulationToolService.getLocalizedMessage("popupDesign.projectScope")}</strong></td>
                            <td>${locMap.projectScopeLocalized?.encodeAsHTML()}</td>
                        </tr>
        """

        if (operatingPeriodAndName) {
            String designName = ""
            designName = """
                        <tr><td><strong>${simulationToolService.getLocalizedMessage("entity.name")}</strong></td>
                            <td>${operatingPeriodAndName?.encodeAsHTML()}</td>
                        </tr>
            """
            scopeRows = designName + scopeRows
        }

        return scopeRows
    }

    @Override
    Map<String, String> getPopupTableList(List<Entity> entityList) {
        List<Question> scopeQuestions = getScopeQuestions()
        def map = entityList?.collectEntries { [(it?.id?.toString() ?: ""): getPopupTable(it?.operatingPeriodAndName, it?.scope, scopeQuestions)] }

        return (map ?: [:])
    }

    @Override
    Map getLocalizedWarningMap(Entity entity, PredefinedScope scope) {
        String recommendedWarning
        String requiredWarning

        def incompleteWarning = scope?.incompleteWarning

        if (incompleteWarning && entity) {

            String controlledQuestionId = incompleteWarning.controlledAdditionalQuestionId
            Question question = controlledQuestionId ? getControlledQuestion(controlledQuestionId) : null

            String recommendedCode = "scope.${warning.recommendedAlert}"
            String requiredCode = "scope.${warning.requiredAlert}"

            String recommendationText = incompleteWarning.localizedRecommended() ?: ""
            String requiredText = incompleteWarning.localizedRequired() ?: ""

            def recommendedValues = incompleteWarning.recommendedValues
            def requiredValues = incompleteWarning.requiredValues

            requiredWarning = getWarningText(entity, requiredValues, requiredText, requiredCode, question)
            recommendedWarning = getWarningText(entity, recommendedValues, recommendationText, recommendedCode, question)

        }

        return [recommendedWarning: recommendedWarning ?: "", requiredWarning: requiredWarning ?: ""]
    }

    @Override
    String getScopePercentage(Entity entity, PredefinedScope scope) {
        ReportIncompleteWarning incompleteWarning = scope?.incompleteWarning
        String percentText = '-'
        if (incompleteWarning && entity) {

            String controlledQuestionId = incompleteWarning.controlledAdditionalQuestionId
            Question question = controlledQuestionId ? getControlledQuestion(controlledQuestionId) : null
            List<String> requiredValues = incompleteWarning.requiredValues
            List<String> missingValues = getMissingValues(entity, requiredValues, question?.questionId)

            if (requiredValues && missingValues != null && question) {
                int valSize = requiredValues.size()
                int missSize = missingValues.size()

                Double percentage = ((valSize - missSize) / valSize * 100)

                if (percentage) {
                    percentText = percentage.round().toString() + "%"
                }
            }

        }
        return percentText
    }

    @Override
    String getLocalizedScope(ScopeToSave scopeToSave) {
        if (!scopeToSave) {
            return null
        }
        PredefinedScope p = findByScopeId(scopeToSave.scopeId)
        return p?.localizedName
    }

    @Override
    Map getMapSectionToProjectScope() {
        List<Question> scopeQuestions = getScopeQuestions()

        def map = scopeQuestions?.find { it.questionId == "projectScope" }?.choices?.collectEntries { [(it?.answerId ?: ""): (it?.materialsQuerySections ?: [(it?.answerId ?: "")])] }

        return map ?: [:]
    }

    @Override
    List getProjectScopeSectionIds() {
        return getScopeQuestions()?.find({ it.questionId == "projectScope" })?.choices?.collect({ it.answerId })
    }

    private String getWarningText(Entity entity, List<String> valueList, String text, String code, Question question) {
        String warning

        if (valueList && text && code && question) {
            int valSize = valueList.size()
            def missingValues = getMissingValues(entity, valueList, question?.questionId)
            if (missingValues) {

                int presentSize = valSize - missingValues.size()
                List<QuestionAnswerChoice> choices = question?.choices
                List<String> missingChoices

                if (choices) {
                    missingChoices = choices.unique({ it.answerId })?.findAll({
                        missingValues.contains(it.answerId)
                    })?.collect({ it.localizedAnswer })
                    String[] args = [presentSize, valSize, text, missingChoices].toArray()
                    warning = simulationToolService.getLocalizedMessage(code, args)
                }

            }
        }

        return warning
    }

    private List<String> getMissingValues(Entity entity, List<String> valueList, String controlledAdditionalQuestionId) {
        if (controlledAdditionalQuestionId) {
            List<Dataset> datasets = entity?.datasets?.findAll({
                it.additionalQuestionAnswers?.keySet()?.
                        contains(controlledAdditionalQuestionId)
            })?.toList()
            List<String> answers = datasets?.collect({
                it?.additionalQuestionAnswers?.
                        get(controlledAdditionalQuestionId)
            })?.flatten()

            if (answers) {
                return valueList - answers
            } else {
                return valueList
            }
        } else {
            return null
        }
    }

    @Override
    PredefinedScope findByScopeId(String scopeId) {
        if (scopeId) {
            Application application = applicationService.getApplicationByApplicationId(getApplicationId())
            return application?.predefinedScopes?.find { it.scopeId == scopeId }
        }

        return null
    }


    @Override
    List<Question> getScopeQuestions() {
        Query defaultQuery = queryService.getQueryByQueryId(getDefaultQueryId())
        List<Question> scopeQuestions = defaultQuery?.getQuestionsBySection(getSectionId())

        return scopeQuestions
    }

    @Override
    String scopeContainsStage(String stage) {
        return includedStagesForCurrentScope.contains(stage) ? Constants.SCOPE_INCLUDED : Constants.SCOPE_EXCLUDED
    }

    @Override
    void collectIncludedStages(List<String> scopes) {
        includedStagesForCurrentScope = new LinkedList<>()
        List<PredefinedScopeAndQuery> scopeStages = Application.collection.findOne([applicationId: getApplicationId()], [predefinedEPDScopesAndQueries: 1]).predefinedEPDScopesAndQueries?.collect({ it as PredefinedScopeAndQuery })

        if (scopes) {
            for (int i = 0; i < scopes.size(); i++) {
                List<String> includedStagesFromScope = scopeStages.find { scopes.get(i) == it?.epdScopeId }?.includedStages

                if (includedStagesFromScope) {
                    includedStagesForCurrentScope.addAll(includedStagesFromScope)
                }

            }
        }
    }

    @Override
    String getScopeIdMessage(String selectedScope) {
        List<PredefinedScope> predefinedScopes = Application.collection.findOne([applicationId: getApplicationId()], [predefinedScopes: 1])?.predefinedScopes

        return predefinedScopes?.find { scope -> scope.scopeId.equalsIgnoreCase(selectedScope) }?.name.EN
    }

}
