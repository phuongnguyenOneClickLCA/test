package com.bionova.optimi.core.domain.mongo

import com.mongodb.BasicDBObject
import org.apache.commons.lang.time.DateUtils
import org.bson.types.ObjectId

class TemporaryImportData {

    static mapWith = "mongo"

    ObjectId id
    byte[] importFile
    String importFileName
    Date importDate
    String importMapperId
    String indicatorId
    ObjectId licenseId
    String userId
    List<String> errorMessages
    Entity temporaryEntity
    Map<Integer, Integer> importDataPerStep
    String ipAddress
    String extension
    String originatingSystem
    String pluginVersion // plugin version sent at least from revit plugin
    Boolean apiCalculation
    Integer rowCounts // get row counts for import file
    String parentEntityId // Project where imported
    String childEntityName // Design / period where imported

    String tiedToUserId // If given only this user can load the file for importMapper

    static constraints = {
        importFile nullable: true
        importFileName nullable: true
        errorMessages nullable: true
        temporaryEntity nullable: true
        licenseId nullable: true
        importDataPerStep nullable: true
        importMapperId nullable: true
        indicatorId nullable: true
        ipAddress nullable: true
        extension nullable: true
        importDate nullable: true
        originatingSystem nullable: true
        userId nullable: true
        parentEntityId nullable: true
        childEntityName nullable: true
        pluginVersion nullable: true
        tiedToUserId nullable: true
        apiCalculation nullable: true
        rowCounts nullable: true
    }

    static transients = [
            'calculationResults',
            'license',
            'expiresIn',
            'username',
            'parentEntity',
            'parentEntityName',
            'parentEntityCountry',
            "isExpired"
    ]

    transient userService
    transient entityService

    static embedded = ['temporaryEntity']

    def beforeInsert() {
        importDate = new Date()
    }

    def getUsername() {
        String username

        if (userId) {
            username = userService.getUserAsDocumentById(userId, new BasicDBObject([username:1]))?.username
        }
        return username
    }

    def beforeDelete() {
        removeCalculationResults()
    }

    def getLicense() {
        if (licenseId) {
            return License.read(licenseId)
        } else {
            return null
        }
    }

    def getCalculationResults() {
        List<CalculationResult> calculationResults

        if (temporaryEntity) {
            List<ObjectId> objectIds = temporaryEntity.calculationResults?.findAll()?.id

            if (objectIds) {
                calculationResults = temporaryEntity.getCalculationResultObjectsByObjectIds(objectIds)
            }
        }

        return calculationResults
    }

    def removeCalculationResults() {
        List<CalculationResult> calculationResults = getCalculationResults()

        if (calculationResults) {
            calculationResults.each {
                CalculationResult.collection.remove(["_id": it.id])
            }
        }
    }

    def getExpiresIn() {
        Date expires
        if (importDate) {
            expires = DateUtils.addDays(importDate, 30)
        }
        return expires
    }

    def getIsExpired() {
        Boolean expired = Boolean.FALSE
        Date now = new Date()
        Date expires = getExpiresIn()

        if (expires) {
            if (now.after(expires)) {
                expired = Boolean.TRUE
            }
        } else {
            expired = Boolean.TRUE
        }
        return expired
    }

    def getParentEntity() {
        return entityService.getEntityById(parentEntityId)
    }

    def getParentEntityName() {
        return entityService.getEntityById(parentEntityId)?.name
    }

    def getParentEntityCountry() {
        return entityService.getEntityById(parentEntityId)?.countryForPrioritization
    }
}
