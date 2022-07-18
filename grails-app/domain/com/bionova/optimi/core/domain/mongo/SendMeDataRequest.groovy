package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable
import groovy.transform.TypeCheckingMode

@GrailsCompileStatic
class SendMeDataRequest implements Serializable, Validateable {

    static mapWith = "mongo"

    String sentByUserId
    List<String> receivedByUserIds
    String type // bill of materials (importmapper), Product LCA, Project LCA, etc..

    String designId
    String indicatorId
    String resourceId
    String profileId

    static constraints = {
        resourceId nullable: true
        profileId nullable: true
        designId nullable: true
        indicatorId nullable: true
    }

    transient userService

    static transients = ['sentByUser', 'receivedByUsers']

    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    User getSentByUser() {
        return userService.getUserById(sentByUserId)
    }

    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    List<User> getReceivedByUsers() {
        return userService.getUsersByIds(receivedByUserIds)
    }
}