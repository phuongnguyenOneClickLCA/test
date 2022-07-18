package com.bionova.optimi.construction.controller.admin

import com.bionova.optimi.command.ImportMapperTrainingDataSearchFilter
import com.bionova.optimi.construction.controller.AbstractDomainObjectController
import com.bionova.optimi.core.domain.mongo.ImportMapperTrainingData
import com.bionova.optimi.core.service.ResourceService

class AdaptiveRecognitionDataController extends AbstractDomainObjectController {

    private static final int PAGINATION_DEFAULT_OFFSET = 0
    private static final int PAGINATION_DEFAULT_LIMIT = 10
    private static final List<Integer> PAGE_LIMIT_VALUES = [10, 20, 50, 100]

    def importMapperTrainingDataService
    def applicationService
    def flashService
    ResourceService resourceService

    def list() {
        Boolean onlyReportedAsBad = params.boolean('reportedAsBad')
        ImportMapperTrainingDataSearchFilter searchFilter = getImportMapperTrainingDataSearchFilter()

        List trainingDatas = []
        List<String> availableSubTypes = []
        List<String> availableApplications = []
        long totalNumberOfRecords

        if (onlyReportedAsBad) {
            trainingDatas = importMapperTrainingDataService.getAllReportedTrainingData()
            totalNumberOfRecords = trainingDatas.size()
        } else {
            Map filteredTrainingData = importMapperTrainingDataService.getTrainingData(searchFilter)
            trainingDatas = filteredTrainingData.get(importMapperTrainingDataService.PAGINATED_RESULTS_KEY)
            totalNumberOfRecords = filteredTrainingData.get(importMapperTrainingDataService.TOTAL_COUNT_KEY)
            availableSubTypes = resourceService.getResourceSubTypes()
            availableApplications = applicationService.getAllApplicationApplicationIds()
        }
        log.info("TRAINING DATA: ${trainingDatas.size()}")

        [
                trainingDatas: trainingDatas,
                onlyReportedAsBad: onlyReportedAsBad,
                availableSubTypes: availableSubTypes,
                availableApplications: availableApplications,
                totalNumberOfRecords: totalNumberOfRecords,
                searchFilter: searchFilter
        ]
    }

    def filter() {
        ImportMapperTrainingDataSearchFilter searchFilter = getImportMapperTrainingDataSearchFilter()
        Map filteredTrainingData = importMapperTrainingDataService.getTrainingData(searchFilter)

        render template: "blocks/trainingData",
                model: [
                        trainingDatas: filteredTrainingData.get(importMapperTrainingDataService.PAGINATED_RESULTS_KEY),
                        availableSubTypes: resourceService.getResourceSubTypes(),
                        availableApplications: applicationService.getAllApplicationApplicationIds(),
                        totalNumberOfRecords: filteredTrainingData.get(importMapperTrainingDataService.TOTAL_COUNT_KEY),
                        searchFilter: searchFilter
                ]
    }

    def saveInternal() {

    }

    def remove() {
        if (params.id) {
            boolean deleteOk = importMapperTrainingDataService.deleteTrainingData(params.id)

            if (deleteOk) {
                flash.fadeSuccessAlert = message(code: "admin.recognitionData.delete.ok")
            } else {
                flash.fadeErrorAlert = message(code: "admin.recognitionData.delete.error")
            }
        }
        redirect action: "list"
    }

    def form() {
        if (params.id) {
            ImportMapperTrainingData trainingData = importMapperTrainingDataService.getTrainingDataById(params.id)
            [trainingData: trainingData]
        }
    }

    def save() {
        if (params.id && params.resourceAndProfile) {
            String resourceAndProfileId = params.resourceAndProfile
            ImportMapperTrainingData trainingData = importMapperTrainingDataService.getTrainingDataById(params.id)
            String resourceId = resourceAndProfileId.tokenize('.')[0]
            String profileId = resourceAndProfileId.tokenize('.')[1]
            trainingData.resourceId = resourceId
            trainingData.profileId = profileId
            importMapperTrainingDataService.updateTrainingData(trainingData)
            flash.fadeSuccessAlert = "TrainingData remapped successfully"
        }
        redirect action: "list"
    }

    def delete() {
        ImportMapperTrainingData trainingData

        if (params.id) {
            trainingData = importMapperTrainingDataService.getTrainingDataById(params.id)

            if (trainingData) {
                trainingData.delete(flush: true, failOnError: true)
                render text: "success"
            }
        }
    }

    def clearBadMappingStatus() {
        ImportMapperTrainingData trainingData

        if (params.id) {
            trainingData = importMapperTrainingDataService.getTrainingDataById(params.id)

            if (trainingData) {
                trainingData.reportedAsBad = null
                trainingData.reportedAsBadByUser = null
                trainingData.merge(flush: true, failOnError: true)
                render text: "success"
            }
        }
    }

    private ImportMapperTrainingDataSearchFilter getImportMapperTrainingDataSearchFilter() {
        return new ImportMapperTrainingDataSearchFilter([
                offset: params.int("offset", PAGINATION_DEFAULT_OFFSET),
                limit: params.int("max", PAGINATION_DEFAULT_LIMIT),
                applicationId: params.applicationId,
                resourceId: params.resourceId,
                profileId: params.profileId,
                importMapperId: params.importMapperId,
                resourceSubType: params.resourceSubType,
                resourceStatus: params.resourceStatus ? params.resourceStatus.toBoolean() : null
        ])
    }
}
