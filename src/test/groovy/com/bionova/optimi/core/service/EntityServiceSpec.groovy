package com.bionova.optimi.core.service

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.ChildEntity
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Feature
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.License
import com.bionova.optimi.core.domain.mongo.User
import grails.testing.services.ServiceUnitTest
import grails.testing.spring.AutowiredTest
import spock.lang.Specification
import spock.lang.Unroll

class EntityServiceSpec extends Specification implements ServiceUnitTest<EntityService>, AutowiredTest {

    private LicenseService licenseService
    private IndicatorService indicatorService
    private UserService userService

    def setup() {
        licenseService = Mock(LicenseService)
        service.licenseService = licenseService
        indicatorService = Mock(IndicatorService)
        service.indicatorService = indicatorService
        userService = Mock(UserService)
        service.userService = userService
    }


    def cleanup() {
    }
    void "number of designs should be greater than 1"() {
        given: "mock"
        Entity parentEntity = new Entity()
        and:
        Map<String, Boolean> testMap = new HashMap<String, Boolean>()
        testMap.put(Feature.ALLOW_AVERAGE_DESIGN_CREATION, Boolean.TRUE)

        when:"feature is available and design size >1"
        licenseService.featuresAllowedByCurrentUserOrProject(parentEntity, [Feature.ALLOW_AVERAGE_DESIGN_CREATION]) >> testMap
        then:"average design creation should be enabled"
        assert service.checkIfAverageDesignFeatureEnabled(2, parentEntity)
        assert !service.checkIfAverageDesignFeatureEnabled(1, parentEntity)
        assert !service.checkIfAverageDesignFeatureEnabled(null, parentEntity)
        assert !service.checkIfAverageDesignFeatureEnabled(null, null)
        assert !service.checkIfAverageDesignFeatureEnabled(1, null)
        assert !service.checkIfAverageDesignFeatureEnabled(2, null)
    }

    void "average design creation license feature should be available"() {
        given: "mock"
        Entity parentEntity = new Entity()
        and:"features allowed map doesn't contain average creation feature"
        Map<String, Boolean> testMap = new HashMap<String, Boolean>()
        testMap.put("something", Boolean.TRUE)

        when:
        licenseService.featuresAllowedByCurrentUserOrProject(parentEntity, [Feature.ALLOW_AVERAGE_DESIGN_CREATION]) >> testMap
        then:"average design creation should be disabled"
        assert !service.checkIfAverageDesignFeatureEnabled(6, parentEntity)
        assert !service.checkIfAverageDesignFeatureEnabled(2, parentEntity)
        assert !service.checkIfAverageDesignFeatureEnabled(1, parentEntity)
        assert !service.checkIfAverageDesignFeatureEnabled(null, parentEntity)
        assert !service.checkIfAverageDesignFeatureEnabled(null, null)
        assert !service.checkIfAverageDesignFeatureEnabled(1, null)
        assert !service.checkIfAverageDesignFeatureEnabled(2, null)
    }

    void "empty features allowed map shouldn't break the functionality"() {
        given: "mock"
        Entity parentEntity = new Entity()

        when:"features allowed map is empty"
        licenseService.featuresAllowedByCurrentUserOrProject(parentEntity, [Feature.ALLOW_AVERAGE_DESIGN_CREATION]) >> Collections.EMPTY_MAP
        then:"average design creation should be disabled"
        assert !service.checkIfAverageDesignFeatureEnabled(5, parentEntity)
        assert !service.checkIfAverageDesignFeatureEnabled(2, parentEntity)
        assert !service.checkIfAverageDesignFeatureEnabled(1, parentEntity)
        assert !service.checkIfAverageDesignFeatureEnabled(null, parentEntity)
        assert !service.checkIfAverageDesignFeatureEnabled(null, null)
        assert !service.checkIfAverageDesignFeatureEnabled(1, null)
        assert !service.checkIfAverageDesignFeatureEnabled(2, null)
    }

    // TODO: add more tests for isCompareAvailableForCurrentUser
    void "isCompareAvailableForCurrentUser"() {
        given:
        Entity parentEntity = Mock()
        ChildEntity childEntity = Mock()
        ChildEntity materialSpecifierChildEntity = Mock()

        Indicator indicator1 = Mock()
        Indicator indicator2 = Mock()

        List<Indicator> selectedIndicators = [indicator1, indicator2]

        User user = Mock()
        License license = Mock()

        when:
        Boolean isCompareAvailableForCurrentUser = service.isCompareAvailableForCurrentUser(parentEntity, selectedIndicators)

        then:
        1 * parentEntity.childEntities >> [childEntity, materialSpecifierChildEntity]
        1 * childEntity.entityClass >> Constants.EntityClass.DESIGN.type.toString()
        1 * materialSpecifierChildEntity.entityClass >> Constants.EntityClass.MATERIAL_SPECIFIER.type.toString()
        1 * userService.getCurrentUser() >> user
        1 * licenseService.getValidLicensesForEntity(_) >> [license]
        1 * licenseService.getLicensedFeatures(_) >> [Feature.COMPARE_MATERIAL]
        1 * licenseService.featuresAllowedByCurrentUserOrProject(parentEntity, [Feature.COMPARE_MATERIAL], user, _, _) >> [(Feature.COMPARE_MATERIAL): true] // Map<String, Boolean> featuresAllowed
        1 * indicatorService.addToCompareAllowedForIndicators(selectedIndicators) >> true

        isCompareAvailableForCurrentUser == true
    }

    void "isCompareAvailableForCurrentUser when feature is not allowed"() {
        given:
        Entity parentEntity = Mock()
        ChildEntity childEntity = Mock()
        ChildEntity materialSpecifierChildEntity = Mock()

        Indicator indicator1 = Mock()
        Indicator indicator2 = Mock()

        List<Indicator> selectedIndicators = [indicator1, indicator2]

        User user = Mock()
        License license = Mock()

        when:
        Boolean isCompareAvailableForCurrentUser = service.isCompareAvailableForCurrentUser(parentEntity, selectedIndicators)

        then:
        1 * parentEntity.childEntities >> [childEntity, materialSpecifierChildEntity]
        1 * childEntity.entityClass >> Constants.EntityClass.DESIGN.type.toString()
        1 * materialSpecifierChildEntity.entityClass >> Constants.EntityClass.MATERIAL_SPECIFIER.type.toString()
        1 * userService.getCurrentUser() >> user
        1 * licenseService.getValidLicensesForEntity(_) >> [license]
        1 * licenseService.getLicensedFeatures(_) >> [Feature.COMPARE_MATERIAL]
        1 * licenseService.featuresAllowedByCurrentUserOrProject(parentEntity, [Feature.COMPARE_MATERIAL], user, _, _) >> [(Feature.COMPARE_MATERIAL): false] // Map<String, Boolean> featuresAllowed
        0 * indicatorService.addToCompareAllowedForIndicators(selectedIndicators)

        isCompareAvailableForCurrentUser == false
    }

    void "determineFirstQueryId"() {
        given:
        Entity entity = Mock()

        Indicator indicator1 = Mock()
        Indicator indicator2 = Mock()

        List<Indicator> indicators = [indicator1, indicator2]

        when:
        String firstQueryId = service.determineFirstQueryId(entity, indicators)

        then:
        1 * indicatorService.getProjectLevelQueryIdsForIndicators(_) >> ["LCAParametersQuery", "LCCParametersQuery"]
        entity.queryReady >> ["LCAParametersQuery": false, "LCCParametersQuery": false]

        and:
        firstQueryId == "LCAParametersQuery"
    }

    @Unroll
    void "determineFirstQueryIdFromProjectLevelQueryIds"() {
        given:
        Entity entity = new Entity()
        entity.queryReady = queryReadyMap

        expect:
        service.determineFirstQueryIdFromProjectLevelQueryIds(entity, projectLevelQueryIds) == firstQueryId

        where:
        projectLevelQueryIds | queryReadyMap | firstQueryId
        ["LCAParametersQuery", "LCCParametersQuery"] | ["LCAParametersQuery": false, "LCCParametersQuery": false] | "LCAParametersQuery"
        ["LCAParametersQuery", "LCCParametersQuery"] | ["LCAParametersQuery": true, "LCCParametersQuery": false] | "LCCParametersQuery"
        ["LCAParametersQuery", "LCCParametersQuery"] | ["LCAParametersQuery": true, "LCCParametersQuery": true] | null

        ["LCAParametersQuery", "LCCParametersQuery"] | ["LCAParametersQuery": true] | "LCCParametersQuery"
        ["LCAParametersQuery", "LCCParametersQuery"] | ["OtherParametersQuery": true] | "LCAParametersQuery"

        ["LCCParametersQuery", "LCAParametersQuery"] | ["LCAParametersQuery": false, "LCCParametersQuery": false] | "LCCParametersQuery"

        ["LCAParametersQuery", "LCCParametersQuery"] | null | "LCAParametersQuery"
        ["LCAParametersQuery", "LCCParametersQuery"] | [:]  | "LCAParametersQuery"
        null | ["LCAParametersQuery": false, "LCCParametersQuery": false] | null
        []   | ["LCAParametersQuery": false, "LCCParametersQuery": false] | null

    }

    void "getProjectLevelQueryIdsForCurrentUser"() {
        given:
        Entity entity = Mock()

        when:
        service.getProjectLevelQueryIdsForCurrentUser(entity)

        then:
        1 * entity.parentEntityId >> null
        1 * indicatorService.getSelectedIndicatorsForCurrentUser(_)
        1 * indicatorService.getProjectLevelQueryIdsForIndicators(_)
    }

    void "getIndicatorsByProjectLevelQueryIdsForCurrentUser"() {
        given:
        Entity entity = Mock()

        when:
        service.getIndicatorsByProjectLevelQueryIdsForCurrentUser(entity)

        then:
        1 * entity.parentEntityId >> null
        1 * indicatorService.getSelectedIndicatorsForCurrentUser(_, _)
        1 * indicatorService.groupIndicatorsByProjectLevelQueryIds(_)
    }

}
