package com.bionova.optimi.core.service

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.APICalculationResponse
import com.bionova.optimi.core.domain.mongo.DatasetImportFields
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.TemporaryImportData
import com.mongodb.client.result.DeleteResult
import grails.util.Environment
import org.apache.commons.lang.time.DateUtils
import org.bson.Document
import org.bson.types.ObjectId
import org.springframework.scheduling.annotation.Scheduled

class ScheduledJobsService {

    static lazyInit = false
    def optimiMailService
    def kpiReportService
    def resourceDumpService
    def nmdApiService
    def taskService
    def licenseService
    def userService
    def queryService
    def importMapperTrainingDataService

    /*
        Jobs run on a single thread, if another job is running it will block another from executing at scheduled time
        Make sure to schedule jobs accordingly
    */

    /*
        // Scheduling test
        @Scheduled(cron = "0/10 * * ? * *") // Every 10 seconds
        void executeEveryTen() {
            log.info("FOO")
        }
    */

    /*
  @ Clean up temporary calculation response everyday at midnight
  */
    @Scheduled(cron = "0 0 0 ? * *")
    void cleanUp() {
        try {
            Date thirtyDaysAgo = DateUtils.addDays(new Date(), -30)

            List<APICalculationResponse> apiCalculationResponses = APICalculationResponse.findAllByDateCreatedIsNullOrDateCreatedLessThanEquals(thirtyDaysAgo)

            if (apiCalculationResponses) {
                apiCalculationResponses.each { APICalculationResponse apiCalculationResponse ->
                    apiCalculationResponse.delete(flush: true)
                }
            }

            List<TemporaryImportData> temporaryImportDatas = TemporaryImportData.findAllByImportDateIsNullOrImportDateLessThanEquals(thirtyDaysAgo)

            if (temporaryImportDatas) {
                temporaryImportDatas.each { TemporaryImportData temporaryImportData ->
                    temporaryImportData.delete(flush: true)
                }
            }

            List<ObjectId> ids = DatasetImportFields.collection.distinct("_id", ObjectId.class)?.toList()
            long deleted = 0
            ids?.each { ObjectId id ->
                Document result = Entity.collection.findOne(["datasets.datasetImportFieldsId": id.toString()], [_id: 1])

                if (!result) {
                    DeleteResult deleteResult = DatasetImportFields.collection.remove(["_id": id])

                    if (deleteResult) {
                        deleted = deleted + deleteResult.getDeletedCount()
                    }
                }
            }
            log.info("ImportDataCleanUp Job: Removed ${deleted} inactive dataset import fields from database.")
        } catch (Exception e) {
            log.error("ImportDataCleanUp Job: ${e}")
        }
    }

    /*
   @ Auto renew applicable licenses and send report about it everyday at 1:00 AM
   */
    @Scheduled(cron = "0 0 1 * * ?")
    void autoRenewLicensesJob() {
        licenseService.autoRenewLicensesJob()
    }

    /*
   @ Send email about report of expired licenses for support team on Sunday 1:15 AM
   */
    @Scheduled(cron = "0 15 1 ? * SUN")
    void expiredLicensesJob() {
        licenseService.expiredLicensesJob()
    }

    /*
   @ Send email about report of new users sign up last 24 hours everyday at 2:45 AM
   */
    @Scheduled(cron = "0 45 2 ? * *")
    def newUserAccountsJob() {
        userService.newUserAccountsJob()
    }

    /*
   @ Send weekly KPI report every Monday at 2:35 AM
   */
    @Scheduled(cron = "0 35 2 ? * MON")
    void sendWeeklyKPIReportJob() {
        kpiReportService.sendWeeklyKPIReportJob()
        kpiReportService.sendWeeklyKPIReportForLicenseDetails()
    }
    /*
   @ Create resources dump (generic & by application) every Sunday at 2:15 AM
   */
    @Scheduled(cron = "0 15 2 ? * SUN")
    void createResourceDumpFile() {
        resourceDumpService.createResourceDumpFile()
        resourceDumpService.createApplicationSpecificDumps()
    }

    /*
   @ Collect indicatorBenchmark dump every Sunday at 02.00 AM
   */
    @Scheduled(cron = "0 0 2 ? * SUN")
    void createIndicatorBenckmarckDumpFile() {
        resourceDumpService.createIndicatorBenckmarckDumpFile()
    }

    /*
    @ Collect specialRules dump every Sunday at 1:45 AM
    */
    @Scheduled(cron = "0 45 1 ? * SUN")
    void createSpecialRulesFile() {
        resourceDumpService.createSpecialRulesFile()
    }
    /*
    @ Find all active tasks and send users reminder everyday at 1.35 AM
    */
    @Scheduled(cron = "0 35 1 * * ?")
    void taskReminderJob() {
        taskService.taskReminderJob()
    }
    /*
     @ Update NMD resources everyday at 3 AM, for PROD creating resources and constructions based on the JSON returned
     */
    @Scheduled(cron = "0 0 3 * * ?")
    void dailyUpdateNMDJob(){
        if (Environment.current == Environment.PRODUCTION && ConfigurationService.serverName in Constants.PROD_DOMAINS) {
            nmdApiService.getNMDDailyUpdate()
        }
    }

    @Scheduled(cron = "0 0 1 ? * *") // Fires at 01:00 AM every day.
    void importMapperTrainingDataCleanupJob(){
        log.info "ImportMapperTrainingDataCleanupJob started."

        long numberOfDeletedRecords = importMapperTrainingDataService.removeImportMapperTrainingDataWithInvalidFields() +
                                      importMapperTrainingDataService.removeInvalidImportMapperTrainingData()

        log.info "ImportMapperTrainingDataCleanupJob finished: ${numberOfDeletedRecords} invalid ImportMapperTrainingData records were deleted."
    }

    /*
    @ Update NMD resources everyday at 3.30 AM, for TEST creating resources and constructions based on the JSON returned
    Disable Dev env's job since its rather redundant and might conflict with Prod. For now admin can manually call update from UI
    ===
    @Scheduled(cron = "0 30 3 * * ?")
    void dailyUpdateNMDJobDEV(){
        if (Environment.current == Environment.DEVELOPMENT && System.getProperty("dontRunNMDApi") == null) {
            nmdApiService.getNMDDailyUpdate()
        }
    }
    */
    /*@Scheduled(cron = "0 15 1 ? * SUN") // At 01:15 AM, only on Sunday
    void deprecatedResultsRemoveJob() {
        log.info("Deprecated results job started.")
        int days = 180
        long now = System.currentTimeMillis()
        Date currentDate = new Date()
        Date xTimeAgo = currentDate.minus(days)
        List<Document> projects = Entity.collection.find([parentEntityId: null, childEntities: [$exists: true], $or: [[designResultsCleared: null], [designResultsCleared: [$lte: xTimeAgo]]]])?.toList()
        String basicQueryId = queryService.getBasicQuery()?.queryId

        if (projects) {
            int i = 1
            int removeCounter = 0
            int count = projects.size()

            projects.each { Document project ->
                boolean remove = false
                boolean isPublic = project.datasets?.find({ d -> d.queryId == basicQueryId && d.questionId == "public" }) ? true : false

                if (project.deleted) {
                    remove = true
                } else if (isPublic) {
                    remove = false
                } else {
                    List<Document> licenses = License.collection.find([$and: [[$or: [[compatibleEntityClasses: [$exists: false]], [compatibleEntityClasses: [$in: [project.entityClass]]]]], [$or: [[licensedEntityIds: [$in: [project._id]]], [licensedEntityIds: [$in: [project._id.toString()]]]]]]])?.toList()

                    if (licenses) {
                        // Planetary license is PRODUCTION license so need to check it here
                        if ((licenses.find({it.planetary}) && licenses.findAll({it.planetary}).size() == licenses.size())||(!licenses.find({Constants.LicenseType.PRODUCTION.toString() == it.type}))) {
                            List ids = project.childEntities?.collect({it._id})

                            if (ids) {
                                int lastUpdated = Entity.collection.count([_id: [$in: ids], lastUpdated: [$lte: xTimeAgo]])?.intValue()

                                if (lastUpdated == ids.size()) {
                                    remove = true
                                }
                            }
                        } else {
                            remove = false
                        }
                    } else {
                        remove = true
                    }
                }

                if (remove) {
                    project.childEntities?.each {
                        Entity.collection.updateOne(["_id": it.entityId], [$unset: [calculationResults: ""]])
                        CalculationResult.collection.remove([entityId: it.entityId.toString()])
                    }
                    Entity.collection.updateOne(["_id": project._id], [$set: [designResultsCleared: currentDate]])
                    removeCounter++
                }
                log.info("Progress: ${i} / ${count}, removeCounter: ${removeCounter}")
                i++
            }
            log.info("Clearing done! It took: ${System.currentTimeMillis() - now} ms")
        }
    }*/

}
