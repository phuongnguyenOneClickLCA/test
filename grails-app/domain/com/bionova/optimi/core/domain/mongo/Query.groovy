/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.core.domain.mongo

import grails.util.Holders
import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.service.QuerySectionService
import com.bionova.optimi.core.transformation.Translatable
import grails.validation.Validateable
import org.bson.types.ObjectId

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
class Query implements Serializable, Validateable {
    static mapWith = "mongo"
    ObjectId id
    String queryId
    String applicationId
    String importTime
    String importFile
    Boolean active
    Boolean disableHighlight
    @Translatable
    Map<String, String> name
    @Translatable
    Map<String, String> help
    @Translatable
    Map<String, String> purpose
    List<QuerySection> sections
    List<QuerySection> includedSections
    List<EntitySummaryData> summaryData
    Boolean componentQuery
    Boolean allowMonthlyData
    Boolean showResourceUnit
    List<QueryFilter> supportedFilters
    List<Map<String, Map<String, String>>> exportClasses
    List<String> exportSkipParameters
    Boolean hidePreviousAnswers
    Boolean hideCopyRows
    DataLoadingFeature dataLoadingFeature
    Map<String, String> applicationFilters
    Map<String, Map<String, String>> purposeByEntityType
    Map<String, Map<String, String>> helpByEntityType
    Map<String, Map<String, String>> appendNameByEntityType
    List<String> allowedFeatures
    Map<String, String> resourceImpactAndIndicatorCode
    Map<String, String> resourceStageAndPhaseCode
    Boolean subtypeSearch
    @Translatable
    Map<String, String> typeaheadSearchText
    @Translatable
    Map<String, String> noResourceFoundText
    Map<String, String> xmlUnitId
    List<String> privateDataSelfDeclarationAllowedDataProperties

    //Simulation Tool Project
    List<EntityTypeConstructionGroupAllocation> orderedConstructionGroupList
    List<EntityTypeEnergyAllocation> entityTypeEnergyAllocationList
    Integer maxMergeLimit
    List<String> allowedLCCIndicatorIds
    List<CarbonDesignerRegion> carbonDesignerRegions
    List<DimensioningVariable> dimensioningVariables

    //Carbon Designer 3D - Simulation Tool
    List<CarbonDesigner3DRegion> carbonDesigner3DRegions
    List<CarbonDesigner3DElementGroup> elementGroups
    List<CarbonDesigner3DBuildingElements> buildingElements
    List<CarbonDesigner3DBuildingShape> buildingShapes
    List<CarbonDesigner3DMaterialConstituentDefault> materialConstituentDefaults
    List<CarbonDesigner3DStructuralFrame> structuralFrames

    //WORKFLOW Project
    List<WorkFlow> workFlowList

    //BETIE:
    Map<String, Map<String, String>> calculationClassificationList
    List<String> applicableIndicatorForCalculationClass
    Map<String, List<String>>linkedJavascriptRules
    Map<String, List<String>>mapScopesToCD

    //PrivateClassifications:
    String template

    // For quick starting a new project
    List<VirtualQuerySection> virtualSections
    List<String> allowedUnits
    Boolean noCalculationData


    DataLoadingFromFile dataLoadingFromFile
    Boolean displayCopyDataInMainButtonsBar

    static mapping = {
        compoundIndex queryId:1, active:1
        queryId index: true
        version false
    }

    static embedded = ["sections",
                       "summaryData",
                       "includedSections",
                       "supportedFilters",
                       "dataLoadingFeature",
                       "orderedConstructionGroupList",
                       "entityTypeEnergyAllocationList",
                       "workFlowList",
                       "carbonDesignerRegions",
                       "carbonDesigner3DRegions",
                       "elementGroups",
                       "buildingElements",
                       "buildingShapes",
                       "materialConstituentDefaults",
                       "structuralFrames",
                       "dimensioningVariables",
                       "virtualSections",
                       "dataLoadingFromFile"
    ]

    static constraints = {
        sections nullable: true
        active nullable: true
        disableHighlight nullable: true
        help nullable: true
        importTime nullable: true
        importFile nullable: true
        summaryData nullable: true
        componentQuery nullable: true
        allowMonthlyData nullable: true
        applicationId nullable: true
        includedSections nullable: true
        supportedFilters nullable: true
        showResourceUnit nullable: true
        exportClasses nullable: true
        exportSkipParameters nullable: true
        hidePreviousAnswers nullable: true
        hideCopyRows nullable: true
        dataLoadingFeature nullable: true
        applicationFilters nullable: true
        allowedFeatures nullable: true
        resourceImpactAndIndicatorCode nullable: true
        resourceStageAndPhaseCode nullable: true
        subtypeSearch nullable: true
        typeaheadSearchText nullable: true
        noResourceFoundText nullable: true
        xmlUnitId nullable: true
        orderedConstructionGroupList nullable: true
        workFlowList nullable: true
        entityTypeEnergyAllocationList nullable: true
        maxMergeLimit nullable: true
        allowedLCCIndicatorIds nullable: true
        mapScopesToCD nullable: true
        carbonDesignerRegions nullable: true
        dimensioningVariables nullable: true
        calculationClassificationList nullable: true
        applicableIndicatorForCalculationClass nullable: true
        linkedJavascriptRules nullable: true
        template nullable: true
        privateDataSelfDeclarationAllowedDataProperties nullable: true
        virtualSections nullable: true
        allowedUnits nullable: true
        carbonDesigner3DRegions nullable: true
        elementGroups nullable: true
        buildingElements nullable: true
        buildingShapes nullable: true
        materialConstituentDefaults nullable: true
        structuralFrames nullable: true
        noCalculationData nullable: true
        dataLoadingFromFile nullable: true
        displayCopyDataInMainButtonsBar nullable: true
    }

    static transients = [
            "mandatoryQuestions",
            "questionsBySection",
            "allQuestions",
            "allSections",
            "importTimeAsDate"
    ]

    static final String QUERYID_CONSTRUCTION_CREATOR = "constructionsCreatorQuery"

    def getNonMandatoryQuestions(String entityClass) {
        QuerySectionService querySectionService = Holders.getApplicationContext().getBean("querySectionService")
        def questionsMap = [:]

        sections?.each { section ->
            def questions = []

            section.questions?.findAll({it.optional})?.each { Question question ->
                if (!question.entityClasses || question.entityClasses?.contains(entityClass)) {
                    if (!questions.contains(question.questionId)) {
                        questions.add(question.questionId)
                    }
                }
            }

            section.sections?.each { embeddedSection ->
                embeddedSection?.questions?.findAll({it.optional})?.each { Question question ->
                    if (!question.entityClasses || question.entityClasses?.contains(entityClass)) {
                        if (!questions.contains(question.questionId)) {
                            questions.add(question.questionId)
                        }
                    }
                }
            }

            querySectionService.getIncludedSections(section.includeSectionIds)?.each { includedSection ->
                includedSection?.questions?.findAll({it.optional})?.each { Question question ->
                    if (!question.entityClasses || question.entityClasses?.contains(entityClass)) {
                        if (!questions.contains(question.questionId)) {
                            questions.add(question.questionId)
                        }
                    }
                }
            }
            def key = section.sectionId

            if (questions) {
                questionsMap.put((key), questions)
            }
        }
        return questionsMap
    }

    def getMandatoryQuestions(String entityClass) {
        QuerySectionService querySectionService = Holders.getApplicationContext().getBean("querySectionService")
        def questionsMap = [:]

        sections?.each { section ->
            def questions = []

            section.questions?.each { Question question ->
                if ((!question.entityClasses || question.entityClasses?.contains(entityClass)) && !question.optional) {
                    if (!questions.contains(question)) {
                        questions.add(question)
                    }
                }
            }

            section.sections?.each { embeddedSection ->
                embeddedSection?.questions?.each { Question question ->
                    if ((!question.entityClasses || question.entityClasses?.contains(entityClass)) && !question.optional) {
                        if (!questions.contains(question)) {
                            questions.add(question)
                        }
                    }
                }
            }

            querySectionService.getIncludedSections(section.includeSectionIds)?.each { includedSection ->
                includedSection?.questions?.each { Question question ->
                    if ((!question.entityClasses || question.entityClasses?.contains(entityClass)) && !question.optional) {
                        if (!questions.contains(question)) {
                            questions.add(question)
                        }
                    }
                }
            }
            def key = section.sectionId

            if (questions) {
                questionsMap.put((key), questions)
            }
        }
        return questionsMap
    }

    List<Question> getAllQuestions() {
        long start = System.currentTimeMillis()
        List<Question> allQuestions = []

        if (Constants.ADDITIONAL_QUESTIONS_QUERY_ID.equals(queryId)) {
            // AdditionalQuestionsQuery is in memory, cannot set questionids etc. due to ConcurrentModificationException, check getAdditionalQuestionsQuery
            sections?.each { section ->
                section.questions?.each { question ->
                    if (!allQuestions.contains(question)) {
                        allQuestions.add(question)
                    }
                }
            }
        } else {
            sections?.each { section ->
                section.questions?.each { question ->
                    if (!allQuestions.contains(question)) {
                        question.sectionId = section.sectionId
                        allQuestions.add(question)
                    }

                    // the if chosen logic was used earlier on but we likely do not have application for it as of 21 Jan 2019 / PP
                    question.choices?.each { choice ->
                        choice.ifChosen?.each { ifChosenQuestion ->
                            if (!allQuestions.contains(ifChosenQuestion)) {
                                allQuestions.add(ifChosenQuestion)
                            }
                        }

                        choice.getIfChosenSections()?.each { ifChosenSection ->
                            ifChosenSection.questions?.each { ifChosenSectionQuestion ->
                                if (!allQuestions.contains(ifChosenSectionQuestion)) {
                                    allQuestions.add(ifChosenSectionQuestion)
                                }
                            }
                        }
                    }
                }
            }

            allQuestions.each { Question question ->
                question.queryId = this.queryId
            }
        }
        log.trace("getting All Questions ${System.currentTimeMillis() - start}")
        return allQuestions
    }

    List<Question> getQuestionsBySection(sectionId) {
        QuerySectionService querySectionService = Holders.getApplicationContext().getBean("querySectionService")
        List<Question> questions = []

        if (sectionId) {
            QuerySection querySection = sections?.find({ it.sectionId == sectionId })

            querySection?.questions?.each { question ->
                if (!questions.contains(question)) {
                    questions.add(question)
                }

                question.choices?.each { choice ->
                    choice.ifChosen?.each { ifChosenQuestion ->
                        if (!questions.contains(ifChosenQuestion)) {
                            questions.add(ifChosenQuestion)
                        }
                    }

                    choice.getIfChosenSections()?.each { ifChosenSection ->
                        ifChosenSection.questions?.each { ifChosenSectionQuestion ->
                            if (!questions.contains(ifChosenSectionQuestion)) {
                                questions.add(ifChosenSectionQuestion)
                            }
                        }
                    }
                }
            }

            querySection?.sections?.each { embeddedSection ->
                embeddedSection?.questions?.each { question ->
                    if (!questions.contains(question)) {
                        questions.add(question)
                    }

                    question.choices?.each { choice ->
                        choice.ifChosen?.each { ifChosenQuestion ->
                            if (!questions.contains(ifChosenQuestion)) {
                                questions.add(ifChosenQuestion)
                            }
                        }

                        choice.getIfChosenSections()?.each { ifChosenSection ->
                            ifChosenSection.questions?.each { ifChosenSectionQuestion ->
                                if (!questions.contains(ifChosenSectionQuestion)) {
                                    questions.add(ifChosenSectionQuestion)
                                }
                            }
                        }
                    }
                }
            }

            querySectionService.getIncludedSections(querySection?.includeSectionIds)?.each { includedSection ->
                includedSection?.questions?.each { question ->
                    if (!questions.contains(question)) {
                        questions.add(question)
                    }

                    question.choices?.each { choice ->
                        choice.ifChosen?.each { ifChosenQuestion ->
                            if (!questions.contains(ifChosenQuestion)) {
                                questions.add(ifChosenQuestion)
                            }
                        }
                        choice.getIfChosenSections()?.each { ifChosenSection ->
                            ifChosenSection.questions?.each { ifChosenSectionQuestion ->
                                if (!questions.contains(ifChosenSectionQuestion)) {
                                    questions.add(ifChosenSectionQuestion)
                                }
                            }
                        }
                    }
                }
            }
        }

        // AdditionalQuestionsQuery is in memory, cannot set questionids etc. due to ConcurrentModificationException, check getAdditionalQuestionsQuery
        if (!com.bionova.optimi.core.Constants.ADDITIONAL_QUESTIONS_QUERY_ID.equals(this.queryId)) {
            questions.each { Question question ->
                question.queryId = this.queryId
            }
        }
        return questions
    }

    def getAllSections() {
        List<QuerySection> allSections = []

        if (sections) {
            allSections.addAll(sections)
        }

        if (includedSections) {
            allSections.addAll(includedSections)
        }
        return allSections
    }

    def getImportTimeAsDate() {
        Date imported

        if (importTime) {
            imported = new Date().parse("dd.MM.yyyy HH:mm:sss", importTime)
        }
        return imported
    }

}
