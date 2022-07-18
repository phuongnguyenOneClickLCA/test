package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.transformation.Translatable
import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class IndicatorExpandFeature implements Validateable {
   static mapWith = "mongo"
    String expandFeaturesId
    List<String> categories
    Boolean showResultCategory
    Boolean groupBySection
    List<String> additionalQuestions
    List<String> sortByAdditionalQuestions
    List<String> totalsByAdditionalQuestions
    List<String> calculationRules
    List<String> additionalFields
    Boolean sortByQuestionId
    Boolean totalsByQuestionId
    Boolean sortByCategoryId
    Boolean totalsByCategoryId
    Boolean showDurationWarning
    @Translatable
    Map<String, String> linkDisplayName

   static constraints = {
       groupBySection nullable: true
       showResultCategory nullable: true
       sortByQuestionId nullable: true
       totalsByQuestionId nullable: true
       linkDisplayName nullable: true
       expandFeaturesId nullable: true
       sortByCategoryId nullable: true
       totalsByCategoryId nullable: true
       showDurationWarning nullable: true
   }

    static transients = []
}
