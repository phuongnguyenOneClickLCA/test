/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.core.domain.mongo

import grails.databinding.BindingFormat
import grails.validation.Validateable
import org.bson.types.ObjectId

import java.text.SimpleDateFormat

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
class Task implements Serializable, Validateable {
    static mapWith = "mongo"
    ObjectId id
    Date dateCreated
    Date lastUpdated
    Date completionDate
    Date deadline
    User deleguer
    User lastUpdater
    String email
    String targetQueryId
    String targetIndicatorId
    String notes
    String performerNotes
    String taskLink
    Boolean completed = false
    String targetEntityId
    Boolean autoReminder
    Boolean ccToDeleguer
    Boolean notifyOnceCompleted
    Integer reminderAmount
    Integer reminderFrequency
    Integer remindersSent
    Date lastReminder

    static mapping = {
        version false
    }

    static belongsTo = [deleguer: User]

    static constraints = {
        email(validator: { val, obj ->
            if (val && !obj.validateForEmail()) {
                return ['entity.add_user.not_allowed']
            }
        }, nullable: false, blank: false, email: true)
        notes nullable: true, blank: true
        performerNotes nullable: true, blank: true
        deadline(validator: { val, obj ->
            if (val && !obj.id) {
                !getCurrentDate().after(val)
            }
        }, nullable: false)
        targetEntityId nullable: true
        targetQueryId nullable: true
        targetIndicatorId nullable: true
        deleguer nullable: true
        lastUpdater nullable: true
        completionDate nullable: true
        autoReminder nullable: true
        notifyOnceCompleted nullable: true
        taskLink nullable: true, blank: true
        ccToDeleguer nullable: true
        reminderAmount nullable: false
        reminderFrequency nullable: false
        lastReminder nullable: true
        remindersSent nullable: true
    }

    static transients = [
            "targetQuery",
            "targetIndicator",
            "dueDays",
            "overDue",
            "removable",
            "targetEntity",
            "visible",
            "formattedDeadline"
    ]

    transient userService
    transient licenseService

    private static Date getCurrentDate() {
        def date = new Date().format("dd.MM.yyyy")
        return new SimpleDateFormat("dd.MM.yyyy").parse(date)
    }

    public Boolean validateForEmail() {
        List<License> licenses = []

        if (targetEntity?.isPortfolio) {
            License l = licenseService.getLicenseById(targetEntity.portfolio?.licenseId)
            if (l) {
                licenses.add(l)
            }
        } else {
            licenses = targetEntity?.licenses
        }
        User user

        try {
            user = User.collection.findOne([username: email], ["_id": 1,"username": 1]) as User
        } catch (Exception e) {
            user = null
        }

        if (!licenses || (licenses && !licenses.find({ it?.allowedForUser(user)}))) {
            return Boolean.FALSE
        } else {
            return Boolean.TRUE
        }
    }

    def getFormattedDeadline() {
        def formattedDeadline

        if (deadline) {
            formattedDeadline = new SimpleDateFormat("dd.MM.yyyy").format(deadline)
        }
        return formattedDeadline
    }

    def getTargetEntity() {
        if (targetEntityId) {
            return Entity.get(targetEntityId)
        }
    }

    def beforeUpdate() {
        lastUpdated = new Date()
    }

    def beforeInsert() {
        Date date = new Date()
        dateCreated = date
        lastUpdated = date
    }

    def getTargetQuery() {
        def query

        if (targetQueryId) {
            query = Query.findByQueryId(targetQueryId)
        }
        return query
    }

    def getTargetIndicator() {
        def indicator

        if (targetIndicatorId) {
            indicator = Indicator.findByIndicatorId(targetIndicatorId)
        }
        return indicator
    }

    def getDueDays() {
        def dueDays

        if (deadline) {
            dueDays = deadline - new Date()
        }
        return dueDays
    }

    def getOverDue() {
        def overDue = false

        if (deadline && !completed) {
            overDue = deadline < new Date()
        }
        return overDue
    }

    def getRemovable() {
        def user = userService.getCurrentUser()
        return user?.username?.equals(deleguer?.username)
    }

    def getVisible() {
        return !targetEntity?.readonly
    }
    def getLastUpdater() {
        try {
            return userService.getUserById(lastUpdater.id)
        } catch (Exception e) {
            return null
        }
    }
}
