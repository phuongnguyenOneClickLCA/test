package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.ImportMapper
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.Feature
import com.bionova.optimi.core.domain.mongo.License
import grails.testing.services.ServiceUnitTest
import grails.testing.spring.AutowiredTest
import spock.lang.Specification

import javax.servlet.http.HttpSession

class ImportMapperServiceSpec extends Specification implements ServiceUnitTest<ImportMapperService>, AutowiredTest {

    EntityService entityService

    def setup() {
        entityService = Mock(EntityService)
        service.entityService = entityService
    }

    def cleanup() {
    }

    void checkCorrectUnitRemapping() {
        given:
        ImportMapper importMapper = new ImportMapper()
        importMapper.mandatoryUnitRemappings = new HashMap<String, String>() {{
            put("kpl", "pcs");
            put("j-m", "m");
            put("tn", "ton");
        }};

        expect:
        service.remapUnitQuantityType(importMapper, "kpl") == "pcs"
        service.remapUnitQuantityType(importMapper, "J-m") == "m"
        service.remapUnitQuantityType(importMapper, "test") == "test"
        service.remapUnitQuantityType(importMapper, null) == null
        service.remapUnitQuantityType(null, "tn") == "tn"

    }

    void "check getting of limit"() {
        given:
        String entityId = "entity_id"

        Indicator indicator = Mock()
        Entity entity = Mock()
        HttpSession session = Mock()
        License license1 = Mock()
        License license2 = Mock()
        License license3 = Mock()

        GroovySpy(Feature, global: true)

        when:
        Integer result = service.getLimitAccordingToUsedLicenseFeature(entityId, indicator, 400, session)

        then:
        1 * entityService.getEntityById(entityId, session) >> entity
        1 * entity.getLicensesForIndicator(indicator) >> [license1, license2, license3]
        1 * license1.getLicensedFeatureIds() >> ["id_1"]
        1 * license2.getLicensedFeatureIds() >> ["id_2", "id_3"]
        1 * license3.getLicensedFeatureIds() >> ["id_4", "id_5"]
        1 * Feature.withCriteria (_ as Closure) >> ["featureId1", "featureId2", "featureId3", "1000rowImportLimit", "featureId5"]
        0 * _

        and:
        assert result == 1000, "Limit should be 1000."
    }

    void "check getting of default limit"() {
        given:
        String entityId = "entity_id"

        Indicator indicator = Mock()
        Entity entity = Mock()
        HttpSession session = Mock()
        License license1 = Mock()
        License license2 = Mock()
        License license3 = Mock()

        GroovySpy(Feature, global: true)

        when:
        Integer result = service.getLimitAccordingToUsedLicenseFeature(entityId, indicator, 400, session)

        then:
        1 * entityService.getEntityById(entityId, session) >> entity
        1 * entity.getLicensesForIndicator(indicator) >> [license1, license2, license3]
        1 * license1.getLicensedFeatureIds() >> ["id_1"]
        1 * license2.getLicensedFeatureIds() >> ["id_2", "id_3"]
        1 * license3.getLicensedFeatureIds() >> ["id_4", "id_5"]
        1 * Feature.withCriteria (_ as Closure) >> ["featureId1", "featureId2", "featureId3", "featureId4", "featureId5"]
        0 * _

        and:
        assert result == 400, "Limit should be 400."
    }

    void "check getting of maximum limit"() {
        given:
        String entityId = "entity_id"

        Indicator indicator = Mock()
        Entity entity = Mock()
        HttpSession session = Mock()
        License license1 = Mock()
        License license2 = Mock()
        License license3 = Mock()

        GroovySpy(Feature, global: true)

        when:
        Integer result = service.getLimitAccordingToUsedLicenseFeature(entityId, indicator, 400, session)

        then:
        1 * entityService.getEntityById(entityId, session) >> entity
        1 * entity.getLicensesForIndicator(indicator) >> [license1, license2, license3]
        1 * license1.getLicensedFeatureIds() >> ["id_1"]
        1 * license2.getLicensedFeatureIds() >> ["id_2", "id_3"]
        1 * license3.getLicensedFeatureIds() >> ["id_4", "id_5"]
        1 * Feature.withCriteria (_ as Closure) >> ["1000rowImportLimit", "featureId2", "featureId3", "2000rowImportLimit", "featureId5"]
        0 * _

        and:
        assert result == 2000, "Limit should be 2000."
    }
}