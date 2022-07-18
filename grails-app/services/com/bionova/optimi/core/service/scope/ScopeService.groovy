package com.bionova.optimi.core.service.scope

import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.PredefinedScope
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.ScopeToSave
import grails.web.servlet.mvc.GrailsParameterMap
import grails.converters.JSON

interface ScopeService {

    Map getScopeValues(List<Indicator> indicatorList)

    PredefinedScope findByScopeId(String scopeId)

    Map addValueScopeToModel(Map model, List<Indicator> indicatorList)

    Map getLocalizedMap(ScopeToSave scope)

    Map getLocalizedMap(ScopeToSave scope, List<Question> scopeQuestions)

    Boolean isIndicatorCompatible(Indicator indicator, PredefinedScope scope)

    Question getControlledQuestion(String controlledAdditionalQuestionId)

    JSON getJsonScopes(List<PredefinedScope> allowedScopes)

    Entity addIndicatorsToEntity(GrailsParameterMap params, Entity child, Entity parent)

    List<Question> getScopeQuestions()

    GString getPopupTable(String operatingPeriodAndName, ScopeToSave scope, List<Question> scopeQuestions)

    String getScopeRows(ScopeToSave scope, scopeQuestions, String operatingPeriodAndName)

    Map<String, String> getPopupTableList(List<Entity> entityList)

    Map getLocalizedWarningMap(Entity entity, PredefinedScope scope)

    String getScopePercentage(Entity entity, PredefinedScope scope)

    String getLocalizedScope(ScopeToSave scopeToSave)

    Map getMapSectionToProjectScope()

    List getProjectScopeSectionIds()

    String getApplicationId()

    String getDefaultQueryId()

    String getSectionId()

    /**
     * Creates a new list in the scopeService implementation with the record of all
     * individual scope stages included in a certain LCA/EPD scope.
     * Currently only used for EPD report generation
     * @param scopes : list of epdScopeIds included in the current scopeId (see epdScopeList)
     */
    void collectIncludedStages(List<String> scopes)

    /**
     * Checks if @includedStagesForCurrentScope contains a stage
     * @param stage : a string with the stage label (e.g. A1)
     * @return correct string to place in the EPD report depending on
     * whether a stage is included in the corrent scope or not
     */
    String scopeContainsStage(String stage)

    /**
     * Gets the short string message from EPD_application json
     * that corresponds to an EPD scope
     * @param selectedScope
     * @return message to insert in Word report
     */
    String getScopeIdMessage(String selectedScope)

}