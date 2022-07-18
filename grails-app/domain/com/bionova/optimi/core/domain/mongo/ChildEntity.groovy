package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import org.bson.types.ObjectId

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
@GrailsCompileStatic
class ChildEntity implements Serializable {
    static mapWith = "mongo"
    ObjectId entityId
    ObjectId parentId
    String entityClass
    Boolean deleted
    Boolean locked
    Boolean superLocked
    Boolean verified // Bionova verified DEPRECATED! ONLY USE superVerified
    Boolean superVerified // Superuser verified
    String operatingPeriodAndName
    Boolean projectLevelEntity // DEPRECATED
    List<String> projectLevelQueries
    Map <String,String> queryAndLinkedIndicator
    Boolean enableAsUserTestData
    Boolean chosenDesign
    static transients = ['entity']
}
