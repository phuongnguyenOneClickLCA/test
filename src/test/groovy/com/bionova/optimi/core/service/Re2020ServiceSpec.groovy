package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.frenchTools.helpers.RsetMapping
import grails.testing.services.ServiceUnitTest
import grails.testing.spring.AutowiredTest
import org.bson.types.ObjectId
import spock.lang.Specification

class Re2020ServiceSpec extends Specification implements ServiceUnitTest<Re2020Service>, AutowiredTest {

    def setup() {
    }

    def cleanup() {
    }

    void "isStandAloneParcelle"() {
        given:
        Entity designForParcelle = new Entity()
        designForParcelle.id = new ObjectId()
        Entity design1 = new Entity()
        design1.id = new ObjectId()
        Entity design2 = new Entity()
        design2.id = new ObjectId()

        List<Entity> selectedDesigns = [designForParcelle, design1, design2]
        String designIdForParcelle = designForParcelle.id.toString()

        RsetMapping mappingHasParcelle = new RsetMapping(
                designIdForParcelle: designIdForParcelle,
                batimentIndexMappings: [(designForParcelle.id.toString()): 1,
                                        (design1.id.toString())          : 2]
        )

        RsetMapping mappingWithoutParcelle = new RsetMapping(
                designIdForParcelle: designIdForParcelle,
                batimentIndexMappings: [(design1.id.toString()): 1]
        )

        expect:
        service.isStandAloneParcelle(selectedDesigns, mappingHasParcelle) == Boolean.FALSE
        service.isStandAloneParcelle(selectedDesigns, mappingWithoutParcelle) == Boolean.TRUE
    }
}
