package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.transformation.Translatable
import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

@GrailsCompileStatic
class CarbonDesignerRegion implements Serializable, Validateable {

    String regionReferenceId
    @Translatable(alias = "name")
    Map<String,String> nameRegion
    List<String> enabledBuildingElement
    List<String> alwaysSelectedElements
    List<String> countryIds
    @Translatable
    Map<String,String> regionInfo
    List<CDRegionDefaultChoice> changeDefaultChoicesSets
    List<EnergyScenario> allowedEnergyScenarios
    Boolean hideEarthquakeZone
    String licenseKeyToExclusivelyUseThisCDRegion
    List<CDEarthquakeSetting> earthquakeSettings
    String defaultGraphBreakdown

    ValueReference grossFloorAreaResource
    ValueReference grossInternalFloorAreaResource
    ValueReference heatedAreaResource
    ValueReference referenceSurface
    ValueReference surfaceCombles
    ValueReference numberOfAccomodation
    ValueReference buildingUse
    ValueReference srefBuilding

    static embedded = ['changeDefaultChoicesSets', 'allowedEnergyScenarios', 'earthquakeSettings', 'grossFloorAreaResource',
                       'grossInternalFloorAreaResource', 'heatedAreaResource', 'referenceSurface', 'surfaceCombles',
                       'numberOfAccomodation', 'buildingUse', "srefBuilding"]

}
