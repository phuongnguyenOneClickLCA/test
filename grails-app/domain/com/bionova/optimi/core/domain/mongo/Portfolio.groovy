/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.core.domain.mongo


import grails.compiler.GrailsCompileStatic
import org.bson.types.ObjectId

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class Portfolio implements Serializable {
    static mapWith = "mongo"
    ObjectId id
    String entityId
    String entityName
    String type // design or operating
    String licenseId
    Boolean anonymous
    Boolean publishAsBenchmark
    Boolean showEntityLinks = Boolean.TRUE
    String userId
    String lastUpdaterId
    User user
    User lastUpdater
    Date dateCreated
    List entityIds = []
    List indicatorIds = []
    List entityClasses
    String entityClass
    String connectedIndicatorId
    String portfolioId // used as identification for compoundResultTemplate
    Boolean entityDeleted
    List<String> chosenPeriods
    Boolean showOnlySpecificallyAllowed
    Boolean showAverageInsteadOfTotal
    Boolean showOnlyLatestStatus
    String licenseType
    Boolean sortByDesc
    Boolean showOnlyCarbonHeroes

    static constraints = {
        entityId nullable: false
        type nullable: false, blank: false
        entityIds nullable: true
        indicatorIds nullable: true
        anonymous nullable: true
        user nullable: true
        lastUpdater nullable: true
        publishAsBenchmark nullable: true
        showEntityLinks nullable: true
        licenseId nullable: true
        entityClasses nullable: true
        entityClass nullable: false
        connectedIndicatorId nullable: true
        portfolioId nullable: true
        entityDeleted nullable: true
        chosenPeriods nullable: true
        entityName nullable: true
        showOnlySpecificallyAllowed nullable: true
        showAverageInsteadOfTotal nullable: true
        userId nullable: true
        lastUpdaterId nullable: true
        licenseType nullable: true
        sortByDesc nullable:true
        showOnlyCarbonHeroes nullable: true
        showOnlyLatestStatus nullable: true
    }

    static mapping = { entityId index: true }

    def beforeInsert() {
        dateCreated = new Date()
    }
}
