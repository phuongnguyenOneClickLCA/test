/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.core.domain.mongo

import com.mongodb.BasicDBObject
import org.bson.Document
import org.bson.types.ObjectId

/**
 * @author Pasi-Markus Mäkelä
 */
class Note {
    static mapWith = "mongo"
    ObjectId id
    String comment
    Date dateCreated
    Date lastUpdated
    String creatorId
    String lastUpdaterId
    NoteFile file
    String type // note or attachment
    String targetEntityId
    String previousTargetEntityId
    String entityName

    static constraints = {
        comment (validator: { value, obj ->
            if ("note".equals(obj.type) && !value) {
                return ['note.comment.blank']
            }
        }, nullable: true)
        dateCreated nullable: true
        lastUpdated nullable: true
        file (validator: { value, obj ->
            if ("attachment".equals(obj.type) && (value == null || !value?.data)) {
                return ['note.file.nullable']
            }
        }, nullable: true)
        targetEntityId nullable: true
        entityName nullable: true
        previousTargetEntityId nullable: true
        creatorId nullable: true
        lastUpdaterId nullable: true
    }

    static embedded = ['file']

    static mapping = {
        sort dateCreated: "desc"
        version false
    }

    static transients = [
            "editable",
            "hasFile",
            "lastUpdaterName",
            "creatorName",
            "lastUpdaterDisplayName"
    ]

    transient userService

    def getEditable() {
        User user = userService.getCurrentUser() as User

        if (user?.username == creatorName || userService.getSuperUser(user)) {
            return true
        }
        return false
    }

    def getHasFile() {
        if (file && file.data) {
            return true
        }
        return false
    }

    def beforeUpdate() {
        lastUpdated = new Date()
    }

    def beforeInsert() {
        Date date = new Date()
        dateCreated = date
        lastUpdated = date
    }

    def getLastUpdaterName() {
        String name
        if (lastUpdaterId) {
            name = userService.getUserAsDocumentById(lastUpdaterId, new BasicDBObject([username:1]))?.username
        }
        return name

    }
    def getCreatorName() {
        String name
        if (creatorId) {
            name = userService.getUserAsDocumentById(creatorId, new BasicDBObject([username:1]))?.username
        }
        return name
    }

    def getLastUpdaterDisplayName() {
        String name
        if (lastUpdaterId) {
            Document user = userService.getUserAsDocumentById(lastUpdaterId, new BasicDBObject([name:1, firstname:1, lastname:1]))

            if (user) {
                if (user.name) {
                    name = user.name
                } else {
                    name = user.firstname + " " + user.lastname
                }
            }
        }
        return name
    }
}
