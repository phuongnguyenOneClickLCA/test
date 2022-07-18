package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.Dataset
import grails.testing.services.ServiceUnitTest
import grails.testing.spring.AutowiredTest
import spock.lang.Specification

class NewCalculationServiceSpec extends Specification implements ServiceUnitTest<NewCalculationService>, AutowiredTest {

    def setup() {
    }

    def cleanup() {
    }

    void "filterDatasetsByQuerySectionsAndQuestions"() {
        given:

        String dataQueryId = "buildingMaterialsQuery"
        Set<String> sectionIds = ["verticalStructures", "coreReference"]
        Map<String, Set> limitSectionDataToQuestionIds = ["verticalStructures": ["externalWalls", "columnsLoadBearing"]]
        Map<String, Set> hiddenQuestionsPerSection = ["coreReference": ["hiddenQuestion"]]

        Set<String> emptySectionIds = []
        Set<String> nullSectionIds = null
        Map<String, Set> limitSectionDataToEmptyQuestionIds = ["verticalStructures": []]
        Map<String, Set> limitSectionDataToNullQuestionIds = ["verticalStructures": null]
        Map<String, Set> nullLimitSectionDataToQuestionIds = null
        Map<String, Set> nullHiddenQuestionsPerSection = ["coreReference": null]
        Map<String, Set> emptyHiddenQuestionsPerSection = ["coreReference": []]

        Dataset relevantBySectionDs = new Dataset()
        relevantBySectionDs.queryId = dataQueryId
        relevantBySectionDs.sectionId = "coreReference"
        relevantBySectionDs.questionId = "internalWalls"

        Dataset relevantByQuestionDs = new Dataset()
        relevantByQuestionDs.queryId = dataQueryId
        relevantByQuestionDs.sectionId = "verticalStructures"
        relevantByQuestionDs.questionId = "externalWalls"

        Dataset irrelevantByQueryDs = new Dataset()
        irrelevantByQueryDs.queryId = "consturctionProcessQuery"
        irrelevantByQueryDs.sectionId = "verticalStructures"
        irrelevantByQueryDs.questionId = "externalWalls"

        Dataset irrelevantBySectionDs = new Dataset()
        irrelevantBySectionDs.queryId = dataQueryId
        irrelevantBySectionDs.sectionId = "earthSection"
        irrelevantBySectionDs.questionId = "internalWalls"

        Dataset irrelevantByQuestionDs = new Dataset()
        irrelevantByQuestionDs.queryId = dataQueryId
        irrelevantByQuestionDs.sectionId = "verticalStructures"
        irrelevantByQuestionDs.questionId = "internalWalls"

        Dataset irrelevantByHiddenQuestionDs = new Dataset()
        irrelevantByHiddenQuestionDs.queryId = dataQueryId
        irrelevantByHiddenQuestionDs.sectionId = "coreReference"
        irrelevantByHiddenQuestionDs.questionId = "hiddenQuestion"

        List<Dataset> datasets = [relevantBySectionDs, relevantByQuestionDs, irrelevantByQueryDs, irrelevantBySectionDs, irrelevantByQuestionDs, irrelevantByHiddenQuestionDs]

        List<Dataset> filteredDatasets = service.filterDatasetsByQuerySectionsAndQuestions(datasets, dataQueryId, sectionIds, limitSectionDataToQuestionIds, hiddenQuestionsPerSection)
        List<Dataset> filteredDatasetsWithEmptySections = service.filterDatasetsByQuerySectionsAndQuestions(datasets, dataQueryId, emptySectionIds, limitSectionDataToQuestionIds, hiddenQuestionsPerSection)
        List<Dataset> filteredDatasetsWithNullSections = service.filterDatasetsByQuerySectionsAndQuestions(datasets, dataQueryId, nullSectionIds, limitSectionDataToQuestionIds, hiddenQuestionsPerSection)
        List<Dataset> filteredDatasetsWithEmptyQuestions = service.filterDatasetsByQuerySectionsAndQuestions(datasets, dataQueryId, sectionIds, limitSectionDataToEmptyQuestionIds, hiddenQuestionsPerSection)
        List<Dataset> filteredDatasetsWithNullQuestions = service.filterDatasetsByQuerySectionsAndQuestions(datasets, dataQueryId, sectionIds, limitSectionDataToNullQuestionIds, hiddenQuestionsPerSection)
        List<Dataset> filteredDatasetsWithNullLimit = service.filterDatasetsByQuerySectionsAndQuestions(datasets, dataQueryId, sectionIds, nullLimitSectionDataToQuestionIds, hiddenQuestionsPerSection)
        List<Dataset> filteredDatasetsWithNullHiddenQuestions = service.filterDatasetsByQuerySectionsAndQuestions(datasets, dataQueryId, sectionIds, limitSectionDataToQuestionIds, nullHiddenQuestionsPerSection)
        List<Dataset> filteredDatasetsWithEmptyHiddenQuestions = service.filterDatasetsByQuerySectionsAndQuestions(datasets, dataQueryId, sectionIds, limitSectionDataToQuestionIds, emptyHiddenQuestionsPerSection)

        expect:
        filteredDatasets.size() == 2
        filteredDatasets.contains(relevantByQuestionDs)
        filteredDatasets.contains(relevantBySectionDs)
        filteredDatasetsWithEmptySections.size() == 3
        filteredDatasetsWithEmptySections.containsAll(filteredDatasets)
        filteredDatasetsWithEmptySections.contains(irrelevantBySectionDs)
        filteredDatasetsWithEmptySections == filteredDatasetsWithNullSections
        filteredDatasetsWithEmptyQuestions.size() == 3
        filteredDatasetsWithEmptyQuestions.containsAll(filteredDatasets)
        filteredDatasetsWithEmptyQuestions.contains(irrelevantByQuestionDs)
        filteredDatasetsWithEmptyQuestions == filteredDatasetsWithNullQuestions
        filteredDatasetsWithEmptyQuestions == filteredDatasetsWithNullLimit
        filteredDatasetsWithNullHiddenQuestions.size() == 3
        filteredDatasetsWithNullHiddenQuestions.containsAll(filteredDatasets)
        filteredDatasetsWithNullHiddenQuestions.contains(irrelevantByHiddenQuestionDs)
        filteredDatasetsWithEmptyHiddenQuestions == filteredDatasets.findAll{it.sectionId != "coreReference"}
    }
}
