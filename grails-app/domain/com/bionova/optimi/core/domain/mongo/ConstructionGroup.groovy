package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable
import org.bson.types.ObjectId

@GrailsCompileStatic
class ConstructionGroup implements Comparable, Serializable, Validateable {
    static mapWith = "mongo"
    ObjectId id
    String groupId
    String name
    Boolean earlyPhase
    String privateAccountId
    Boolean uneditable

    static constraints = {
        name nullable: false, blank: false, unique: true
        groupId nullable:true
        earlyPhase nullable: true
        privateAccountId nullable: true
        uneditable nullable: true
    }

    def transients = [
            "constructions"
            ]

    @Override
    int compareTo(Object o) {
        return 0
    }

    List<Construction> getConstructions() {
        return Construction.findAllByConstructionGroup(groupId)
    }

    List<Construction> getConstructionsByConstructionIds(List<String> constructionIds) {
        List<Construction> constructions = []

        if (constructionIds) {
            constructions = Construction.findAllByConstructionGroupAndConstructionIdInList(groupId, constructionIds)
        }
        return constructions
    }
}
