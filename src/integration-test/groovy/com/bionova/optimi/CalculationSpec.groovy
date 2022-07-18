package com.bionova.optimi

import com.bionova.optimi.core.domain.mongo.Entity
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.github.fge.jsonpatch.diff.JsonDiff
import grails.testing.mixin.integration.Integration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import spock.lang.Specification
import spock.lang.Unroll

@Integration
@Rollback
@SpringBootTest
class CalculationSpec extends Specification {

    def newCalculationServiceProxy
    def entityService
    def entitySerializationService

    ObjectMapper mapper

    def setup() {
        mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT)
    }

    def cleanup() {
    }

    @Unroll
    void "running calculation #name"() {
        expect:
        String entityJSON = ""
        Entity.withTransaction {
            Entity parentEntity = entityService.readEntity(childEntityId)?.getParentById()
            newCalculationServiceProxy.calculate(childEntityId, indicatorId, parentEntity)
            Entity entity = Entity.get(childEntityId)
            entityJSON = mapper.writeValueAsString(entitySerializationService.convertToDto(entity))
        }
        JsonNode patchNode = JsonDiff.asJson(mapper.readTree(entityJSON), mapper.readTree(new File(filePath)))
        patchNode.isEmpty()

        where:
        name                                                    | childEntityId              | indicatorId                   | filePath
        "DEMO - New BREEAM UK NC 2018 office in London"         | "5b2a39d28e202b04127ce21c" | "lcaForBREEAMuk"              | "src/integration-test/resources/lcaForBREEAMuk.json"
        "DEMO - Precast Concrete Element EPD"                   | "6139b7776f220c33932b82d7" | "rtsEPDGenerator_preVerified" | "src/integration-test/resources/rtsEPDGenerator.json"
        "UNIT CONVERSION test DO NOT EDIT > unitM2ButImperial"  | "612c58a55e90760d7845f8a1" | "LCC_LCA_Automated"           | "src/integration-test/resources/LCC_LCA_Automated-612c58a55e90760d7845f8a1.json"
        "UNIT CONVERSION test DO NOT EDIT > 1"                  | "5badc82d8e202b0e1f9d9fbd" | "globalLifeCycleCarbon"       | "src/integration-test/resources/globalLifeCycleCarbon-5badc82d8e202b0e1f9d9fbd.json"
        "UNIT CONVERSION test DO NOT EDIT > 10"                 | "5badd7ff8e202b0e1f9dc5f2" | "LCC_LCA_Automated"           | "src/integration-test/resources/LCC_LCA_Automated-5badd7ff8e202b0e1f9dc5f2.json"
        "Load testing re2020 (200 rows and 10 design) > test1"  | "617f85f2cc95e94e51e01bc6" | "lcaForRE2020"                | "src/integration-test/resources/lcaForRE2020-617f85f2cc95e94e51e01bc6.json"
        "Load testing re2020 (200 rows and 10 design) > test10" | "617f8b1ecc95e94e51e0337c" | "lcaForRE2020"                | "src/integration-test/resources/lcaForRE2020-617f8b1ecc95e94e51e0337c.json"
        "Load test construction > 100 construction"             | "6180e430cc95e94e51eed5c4" | "lcaForBREEAMuk"              | "src/integration-test/resources/lcaForBREEAMuk-6180e430cc95e94e51eed5c4.json"
        "Load Testing French > test1"                           | "6165703096a66b670c4675c4" | "lcaForFranceECSimplified"    | "src/integration-test/resources/lcaForFranceECSimplified-6165703096a66b670c4675c4.json"
        "load Testing GLA > test200"                            | "6045ab1449b179115e495b60" | "lcaForRICSandGLA"            | "src/integration-test/resources/lcaForRICSandGLA-6045ab1449b179115e495b60.json"
        "Load Testing Entity NMD > 198 Resources"               | "6188e61bd75ba94dd221e91e" | "lcaForBepalingsmetode_NMD30" | "src/integration-test/resources/lcaForBepalingsmetode_NMD30-6188e61bd75ba94dd221e91e.json"
    }
}
