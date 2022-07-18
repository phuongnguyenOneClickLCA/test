package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import org.bson.types.ObjectId

@GrailsCompileStatic
class PrivateClassificationList implements Serializable {

    static mapWith = "mongo"
    ObjectId id
    String name
    String questionId
    Map<String, String> answers
    String accountId
    String linkedEntityFileId

    static constraints = {
        name nullable: true
        questionId nullable: true
        answers nullable: true
        accountId nullable: true
        linkedEntityFileId nullable: true
    }
}
