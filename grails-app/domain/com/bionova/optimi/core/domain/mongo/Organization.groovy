package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import org.bson.types.ObjectId

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class Organization implements Serializable {
    static mapWith = "mongo"
    ObjectId id
    String name
    Boolean invitationOnly
    List organizationAdminIds = []
    List organizationUserIds = []
    List requestToJoinUserIds = []
    List organizationEntityIds = []
    Date dateCreated

    static constraints = {
        name nullable: false, blank: false, unique: true
        organizationUserIds nullable: true
        organizationAdminIds nullable: true
        requestToJoinUserIds nullable: true
        invitationOnly nullable: true
        organizationEntityIds nullable: true
        dateCreated nullable: true
    }
}
