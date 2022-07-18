package com.bionova.optimi.core.service

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import grails.testing.services.ServiceUnitTest
import grails.testing.spring.AutowiredTest
import spock.lang.Specification

class DatasetServiceSpec extends Specification implements ServiceUnitTest<DatasetService>, AutowiredTest {

    def setup() {
    }

    def cleanup() {
    }
    void "filter and return datasets based on conditions"() {
        given:

        Dataset d1 = new Dataset()
        Dataset d2 = new Dataset()
        Dataset d3 = new Dataset()
        Dataset d4 = new Dataset()


        d1.setQueryId(Constants.EPDMATERIALS_PREVERIFIED)
        d2.setQueryId(Constants.EPDMANUFACTURING_PREVERIFIED)
        d3.setQueryId("basicQuery")
        d4.setQueryId(Constants.EPDMATERIALS_PREVERIFIED)

        Set<Dataset> datasetsToFilter = [d1, d2, d3, d4]
        Set<Dataset> resultSet = [d1, d2, d4]

        Set<Dataset> datasetsToFilter_2 = [d3]
        Set<Dataset> resultSet_2 = [d1, d2, d4]

        expect:
        service.datasetFilter(datasetsToFilter) == resultSet
        service.datasetFilter(datasetsToFilter_2) == Collections.EMPTY_SET
    }

    void "getAdditionalAnswerFromDatasetAsDouble"() {
        given:
        Dataset ds = new Dataset()
        Map<String, String> addQAnswers = new HashMap()

        String addQ = 'testQuestion'
        addQAnswers.put(addQ, '2.0')

        String addQComma = 'testQuestionWithComma'
        addQAnswers.put(addQComma, '2,0')

        String addQWithNonNumberAnswer = 'addQWithNonNumberAnswer'
        String textAnswer = 'nonNumber'
        addQAnswers.put(addQWithNonNumberAnswer, textAnswer)

        ds.additionalQuestionAnswers = addQAnswers

        expect:
        service.getAdditionalAnswerFromDatasetAsDouble(ds, addQ) == 2.0
        service.getAdditionalAnswerFromDatasetAsDouble(ds, addQComma) == 2.0
        service.getAdditionalAnswerFromDatasetAsDouble(ds, addQWithNonNumberAnswer) == null
        service.getAdditionalAnswerFromDatasetAsDouble(ds, 'nonExisting') == null
        service.getAdditionalAnswerFromDatasetAsDouble(null, addQ) == null
    }

    void "getAdditionalAnswerFromDatasetAsInteger"() {
        given:
        Dataset ds = new Dataset()
        Map<String, String> addQAnswers = new HashMap()

        String addQ = 'testQuestion'
        addQAnswers.put(addQ, '2')

        String addQComma = 'testQuestionWithComma'
        addQAnswers.put(addQComma, '2,0')

        String addQWithNonNumberAnswer = 'addQWithNonNumberAnswer'
        String textAnswer = 'nonNumber'
        addQAnswers.put(addQWithNonNumberAnswer, textAnswer)

        ds.additionalQuestionAnswers = addQAnswers

        expect:
        service.getAdditionalAnswerFromDatasetAsInteger(ds, addQ) == 2
        service.getAdditionalAnswerFromDatasetAsInteger(ds, addQComma) == 2
        service.getAdditionalAnswerFromDatasetAsInteger(ds, addQWithNonNumberAnswer) == null
        service.getAdditionalAnswerFromDatasetAsInteger(ds, 'nonExisting') == null
        service.getAdditionalAnswerFromDatasetAsInteger(null, addQ) == null
    }

    void "removeExistingDatasets"() {
        given:
        Collection<Dataset> datasets = []
        String queryIdToRemove = "constructionFECQuery"
        Boolean removeOnlyGeneratedFromImportDatasets = true
        Map<String, List<String>> skipQuestionsPerSection = ["energyAndWaterUse": ["hiddenQuestion1", "hiddenQuestion2"],
                                                             "constructionMaterials" : []]

        Dataset notRemovedDataset = new Dataset()
        notRemovedDataset.with {
            queryId = queryIdToRemove
            generatedFromImport = false
            sectionId = "energyAndWaterUse"
            questionId = "hiddenQuestion1"
        }
        datasets.add(notRemovedDataset)

        Dataset notRemovedDatasetByEmptyQuestionsPerSection = new Dataset()
        notRemovedDatasetByEmptyQuestionsPerSection.with {
            queryId = queryIdToRemove
            generatedFromImport = false
            sectionId = "constructionMaterials"
            questionId = "anotherQuestion"
        }
        datasets.add(notRemovedDatasetByEmptyQuestionsPerSection)

        Dataset notRemovedImportedDataset = new Dataset()
        notRemovedImportedDataset.with {
            queryId = queryIdToRemove
            generatedFromImport = true
            sectionId = "energyAndWaterUse"
            questionId = "hiddenQuestion2"
        }
        datasets.add(notRemovedImportedDataset)

        Dataset notRemovedImportedDatasetByEmptyQuestionsPerSection = new Dataset()
        notRemovedImportedDatasetByEmptyQuestionsPerSection.with {
            queryId = queryIdToRemove
            generatedFromImport = false
            sectionId = "constructionMaterials"
            questionId = "anotherQuestion"
        }
        datasets.add(notRemovedImportedDatasetByEmptyQuestionsPerSection)

        Dataset notRemovedDatasetByQuery = new Dataset()
        notRemovedDatasetByQuery.with {
            queryId = "anotherQuery"
            generatedFromImport = false
            sectionId = "energyAndWaterUse"
            questionId = "hiddenQuestion1"
        }
        datasets.add(notRemovedDatasetByQuery)

        Dataset removedNotImportedDatasetByQuestion = new Dataset()
        removedNotImportedDatasetByQuestion.with {
            queryId = queryIdToRemove
            generatedFromImport = false
            sectionId = "energyAndWaterUse"
            questionId = "anotherQuestion"
        }
        datasets.add(removedNotImportedDatasetByQuestion)

        Collection<Dataset> expectedNotRemovedDatasetsByQuery = [
                notRemovedDatasetByQuery
        ]
        Collection<Dataset> expectedNotRemovedDatasets = [
                notRemovedDataset,
                notRemovedDatasetByEmptyQuestionsPerSection,
                notRemovedImportedDataset,
                notRemovedImportedDatasetByEmptyQuestionsPerSection,
                notRemovedDatasetByQuery
        ]
        Collection<Dataset> expectedNotRemovedDatasetsByQueryForOnlyImportedRemoval = [
                notRemovedDataset,
                notRemovedDatasetByEmptyQuestionsPerSection,
                notRemovedImportedDatasetByEmptyQuestionsPerSection,
                notRemovedDatasetByQuery,
                removedNotImportedDatasetByQuestion
        ]
        Collection<Dataset> expectedNotRemovedDatasetsForOnlyImportedRemoval = [
                notRemovedDataset,
                notRemovedDatasetByEmptyQuestionsPerSection,
                notRemovedImportedDataset,
                notRemovedImportedDatasetByEmptyQuestionsPerSection,
                notRemovedDatasetByQuery,
                removedNotImportedDatasetByQuestion
        ]

        Collection<Dataset> notRemovedDatasetsByQuery = service.removeExistingDatasets(datasets.clone(), queryIdToRemove)
        Collection<Dataset> notRemovedDatasets = service.removeExistingDatasets(datasets.clone(), queryIdToRemove, !removeOnlyGeneratedFromImportDatasets, skipQuestionsPerSection)
        Collection<Dataset> notRemovedDatasetsByQueryForOnlyImportedRemoval = service.removeExistingDatasets(datasets.clone(), queryIdToRemove, removeOnlyGeneratedFromImportDatasets)
        Collection<Dataset> notRemovedDatasetsForOnlyImportedRemoval = service.removeExistingDatasets(datasets.clone(), queryIdToRemove, removeOnlyGeneratedFromImportDatasets, skipQuestionsPerSection)

        expect:
        notRemovedDatasetsByQuery.size() == expectedNotRemovedDatasetsByQuery.size()
        notRemovedDatasetsByQuery.containsAll(expectedNotRemovedDatasetsByQuery)
        notRemovedDatasets.size() == expectedNotRemovedDatasets.size()
        notRemovedDatasets.containsAll(expectedNotRemovedDatasets)
        notRemovedDatasetsByQueryForOnlyImportedRemoval.size() == expectedNotRemovedDatasetsByQueryForOnlyImportedRemoval.size()
        notRemovedDatasetsByQueryForOnlyImportedRemoval.containsAll(expectedNotRemovedDatasetsByQueryForOnlyImportedRemoval)
        notRemovedDatasetsForOnlyImportedRemoval.size() == expectedNotRemovedDatasetsForOnlyImportedRemoval.size()
        notRemovedDatasetsForOnlyImportedRemoval.containsAll(expectedNotRemovedDatasets)
    }

    void "return datasets only a1-a3 queries"() {
        given:
        Entity entity = new Entity()
        entity.datasets = []
        Dataset d1 = new Dataset()
        Dataset d2 = new Dataset()
        Dataset d3 = new Dataset()
        Dataset d4 = new Dataset()
        Dataset d5 = new Dataset()
        d1.queryId = "randomQueryId"
        d2.queryId = ""
        d3.queryId = null
        d4.queryId = Constants.EPDMATERIALS_PREVERIFIED
        d5.queryId = Constants.EPDMANUFACTURING_PREVERIFIED
        Set<Dataset> datasetsForEntity = [d1, d2, d3, d4, d5]
        entity.datasets.addAll(datasetsForEntity)
        Set<Dataset> resultDatasets = new HashSet<>()
        resultDatasets.add(d1)
        resultDatasets.add(d2)
        resultDatasets.add(d3)

        expect:
        service.findRequiredDatasets(entity) == resultDatasets
    }

    void "mergeAnswerOfTwoDatasets"() {
        given:
        Dataset d1 = new Dataset()
        Dataset d2 = new Dataset()
        d1.answerIds = [answer1]
        d2.answerIds = [answer2]

        expect:
        service.mergeAnswerOfTwoDatasets(d1, d2)
        d2.answerIds[0] == result

        where:
        answer1 | answer2 | result
        '1' | '2' | '1 2'
        '1' | '' | '1'
        '' | '2' | '2'
    }

    void "mergeAnswerOfTwoDatasetsWithNullAnswerIds"() {
        given:
        Dataset d1 = new Dataset()
        Dataset d2 = new Dataset()
        d1.answerIds = answerIds1
        d2.answerIds = answerIds2

        expect:
        service.mergeAnswerOfTwoDatasets(d1, d2)
        d2.answerIds?.getAt(0) == result

        where:
        answerIds1 | answerIds2 | result
        ['1'] | null  | '1'
        null  | ['2'] | '2'
        null  | null  | null
    }
}
