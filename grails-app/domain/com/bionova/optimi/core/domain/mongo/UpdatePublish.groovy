package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable
import org.bson.types.ObjectId

import java.text.SimpleDateFormat

@GrailsCompileStatic
class UpdatePublish implements Serializable, Validateable {
    static mapWith = "mongo"

    ObjectId id
    String type
    String title
    String link
    String additionalInfo
    Date dateAdded
    Date dateExpired
    Date lastUpdated
    List<String> indicatorId
    List<String> licensesLevel
    List<String> licensesType
    List<String> countries
    String userName
    List<String> channelFeatureIds
    Boolean showAsPopUp
    Boolean stickyNote
    Boolean disabling

    static hasMany = [channelFeatureIds: String]

    static constraints = {
        type nullable: false
        title nullable: false
        dateAdded nullable: true
        lastUpdated nullable: true
        link nullable: true
        additionalInfo nullable: true
        dateExpired nullable: true
        indicatorId nullable: true
        licensesLevel nullable: true
        licensesType nullable: true
        countries nullable: true
        userName nullable: true
        channelFeatureIds nullable: true
        showAsPopUp nullable: true
        stickyNote nullable: true
        disabling nullable: true

    }

    def beforeInsert() {
        dateAdded = new Date()
        lastUpdated = dateAdded
    }

    def beforeUpdate() {
        lastUpdated = new Date()
    }

    private static Date getCurrentDate() {
        String date = new Date().format("dd.MM.yyyy")
        return new SimpleDateFormat("dd.MM.yyyy").parse(date)
    }
}
