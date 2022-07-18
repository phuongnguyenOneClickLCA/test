package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.frenchTools.helpers.RsetMapping
import grails.testing.services.ServiceUnitTest
import grails.testing.spring.AutowiredTest
import org.bson.types.ObjectId
import spock.lang.Specification

class XmlServiceSpec extends Specification implements ServiceUnitTest<XmlService>, AutowiredTest {
    def setup() {
    }

    def cleanup() {
    }

    void "addDesignForParcelleIfNeeded"() {
        given:
        Entity designForParcelle = new Entity()
        designForParcelle.id = new ObjectId()
        Entity design1 = new Entity()
        design1.id = new ObjectId()
        Entity design2 = new Entity()
        design2.id = new ObjectId()

        List<Entity> allDesigns = [designForParcelle, design1, design2]
        String designIdForParcelle = designForParcelle.id.toString()

        expect:
        RsetMapping userRsetMapping = new RsetMapping(designIdForParcelle: designIdForParcelle)
        List<Entity> result1 = [designForParcelle, design1]
        service.addDesignForParcelleIfNeeded(userRsetMapping, result1, allDesigns)
        // list already have the design for parcelle, should not add anything
        assert result1 == [designForParcelle, design1]

        List<Entity> result2 = [design1]
        service.addDesignForParcelleIfNeeded(userRsetMapping, result2, allDesigns)
        // should add designForParcelle to list
        assert result2 == [design1, designForParcelle]
    }

    void "getDesignsFromBatimentMapping"() {
        given:
        Entity design = new Entity()
        design.id = new ObjectId()
        Entity design1 = new Entity()
        design1.id = new ObjectId()
        Entity design2 = new Entity()
        design2.id = new ObjectId()

        Map<String, Integer> batimentIndexMappings = [
                (design.id.toString()) : 1,
                (design1.id.toString()): 2
        ]

        expect:
        service.getDesignsFromBatimentMapping(batimentIndexMappings, [design, design1, design2]) == [design, design1]
        service.getDesignsFromBatimentMapping(batimentIndexMappings, [design, design2]) == [design]
        service.getDesignsFromBatimentMapping(batimentIndexMappings, [design2]) == []
        service.getDesignsFromBatimentMapping(batimentIndexMappings, []) == []
    }
}
