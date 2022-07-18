package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.Constants
import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable
import groovy.transform.TypeCheckingMode
import org.bson.types.ObjectId

/**
 * Created by miika on 24.04.2017.
 */
class RecognitionRuleset implements Validateable {
    ObjectId id
    String type
    String name
    String accountId
    String country
    String importMapperId
    String applicationId

    //warn & discard have textMatches, user has importMapperTrainingDatas, mapping has sysTrainingDatas
    List<TextMatch> textMatches
    List<ImportMapperTrainingData> importMapperTrainingDatas
    List<SystemTrainingDataSet> systemTrainingDatas

    static mapWith = "mongo"

    static hasMany = [importMapperTrainingDatas: ImportMapperTrainingData, textMatches: TextMatch, systemTrainingDatas: SystemTrainingDataSet]

    static constraints = {
        accountId nullable: true
        country nullable: true
        importMapperId nullable: true
        systemTrainingDatas nullable: true
        importMapperTrainingDatas nullable: true
        textMatches nullable: true
        applicationId nullable: true
    }

    static transients = [
            "ruleCount",
            "isInactiveResourceMappings",
            "reportedTrainingDataCount"
    ]

    static embedded = [
            "textMatches",
            "systemTrainingDatas"
    ]

    transient importMapperTrainingDataService

    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    String getRuleCount() {
        String count

        if (type) {
            if (type.equals(Constants.RulesetType.DISCARD.toString()) || type.equals(Constants.RulesetType.WARN.toString())) {
                count = "<strong>Active: </strong>" + textMatches ? textMatches?.size() : "0"
            } else if (type.equals(Constants.RulesetType.MAPPING.toString())) {
                Integer i = 0
                Integer j = 0

                systemTrainingDatas.each { SystemTrainingDataSet s ->
                    s.systemMatches?.each { SystemMatch systemMatch ->
                        if (systemMatch.resourceId) {
                            if (systemMatch.active) {
                                i++
                            } else {
                                j++
                            }
                        }
                    }
                }

                count = "<strong>Active: </strong>${i}<br /><strong>Inactive: </strong>${j}"
            } else if (type.equals(Constants.RulesetType.USER.toString())) {
                count = importMapperTrainingDataService.getAllTrainingDataCount() ?: "0"
            }
        }
        return count
    }

    String getReportedTrainingDataCount() {
        String reportedBad = importMapperTrainingDataService.getAllReportedTrainingDataCount()
        return reportedBad ?: "0"
    }

    Boolean getIsInactiveResourceMappings() {
        Boolean inactiveResources = Boolean.FALSE

        if (type.equals(Constants.RulesetType.MAPPING.toString()) && systemTrainingDatas) {
            systemTrainingDatas.find { SystemTrainingDataSet systemTrainingDataSet ->
                systemTrainingDataSet.systemMatches?.find { SystemMatch systemMatch ->
                    if (systemMatch.resourceId && !systemMatch.resource?.active) {
                        inactiveResources = Boolean.TRUE
                        return true
                    }
                }
            }
        }
        return inactiveResources
    }
}
