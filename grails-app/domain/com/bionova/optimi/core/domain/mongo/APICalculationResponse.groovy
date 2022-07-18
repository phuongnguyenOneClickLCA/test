package com.bionova.optimi.core.domain.mongo

import com.mongodb.BasicDBObject
import org.apache.commons.lang.time.DateUtils
import org.bson.types.ObjectId

class APICalculationResponse {

    static mapWith = "mongo"

    ObjectId id
    byte[] apiFile
    byte[] originalFile
    String originalFileExtension
    String fileToken
    String status
    Map params
    Date dateCreated
    String userId
    String indicatorId
    String ipAddress

    static constraints = {
        apiFile nullable: true
        originalFile nullable: true
        params nullable: true
        dateCreated nullable: true
        fileToken nullable: true
        status nullable: true
        originalFileExtension nullable: true
        ipAddress nullable: true
        userId nullable: true
        indicatorId nullable: true
    }

    static transients = [
            "expiresIn",
            "username",
            "isExpired"
    ]

    transient userService

    def beforeInsert() {
        dateCreated = new Date()
    }

    def getExpiresIn() {
        Date expires
        if (dateCreated) {
            expires = DateUtils.addDays(dateCreated, 30)
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

    def getUsername() {
        String username

        if (userId) {
            username = userService.getUserAsDocumentById(userId, new BasicDBObject([username:1]))?.username
        }
        return username
    }
}
