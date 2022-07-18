package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.transformation.Translatable
import grails.compiler.GrailsCompileStatic

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class DataLoadingFeature {
    static mapWith = "mongo"
    @Translatable(alias = "name")
    Map<String, String> buttonName
    @Translatable(alias = "loadWarning")
    Map<String, String> loadResourceWarning
    Map<String, String> filterParameterDefinition
    Map<String, String> loadResourcesTarget
    List<String> loadedResourcesRequiredResourceGroups
    String loadResourcesOrderedByValue
    String loadResourceDefaultQuantityFromValue
    Integer loadResourceParameterDecimals
    List<Map<String, ValueReference>> insertLoadedResourceParametersToQuestions

    //för helvete
    Map<String, String> loadQuantityResolutionUserChosenVariable
    String loadQuantityResolutionMatchingResourceVariable
    List<Map<String, Object>> loadQuantityResolutionValues

    static constraints = {
        buttonName nullable: true
        loadResourceWarning nullable: true
        filterParameterDefinition nullable: true
        loadResourcesTarget nullable: true
        loadedResourcesRequiredResourceGroups nullable: true
        loadResourcesOrderedByValue nullable: true
        loadResourceDefaultQuantityFromValue nullable: true
        loadResourceParameterDecimals nullable: true
        insertLoadedResourceParametersToQuestions nullable: true
        loadQuantityResolutionUserChosenVariable nullable: true
        loadQuantityResolutionMatchingResourceVariable nullable: true
        loadQuantityResolutionValues nullable: true
    }

    static transients = []
}
