package com.bionova.optimi.core.taglib

import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Resource
import grails.testing.spring.AutowiredTest
import grails.testing.web.taglib.TagLibUnitTest
import spock.lang.Specification

class IndicatorTagLibSpec extends Specification implements AutowiredTest, TagLibUnitTest<IndicatorTagLib> {

    def setup() {
    }

    def cleanup() {
    }

    void "check assignUnitQuantities method"() {
        given:
        Resource resource1 = new Resource()
        resource1.linkedDatasetManualIds = ["id1"]
        resource1.unitForData = "kg"
        Resource resource2 = new Resource()
        resource2.linkedDatasetManualIds = ["id2"]

        Set<Dataset> datasets = new HashSet<>()
        Dataset dataset1 = new Dataset()
        dataset1.manualId = "id1"
        dataset1.quantity = 100.0
        Dataset dataset2 = new Dataset()
        dataset2.manualId = "id2"
        dataset2.quantity = 15.0
        Dataset dataset3 = new Dataset()

        datasets.add(dataset1)
        datasets.add(dataset2)
        datasets.add(dataset3)

        List<String> rowValues1 = new ArrayList<>()
        List<String> rowValues2 = new ArrayList<>()
        List<String> rowValues3 = new ArrayList<>()

        when:
        tagLib.assignUnitQuantities(resource1, datasets, rowValues1)
        tagLib.assignUnitQuantities(resource2, datasets, rowValues2)
        tagLib.assignUnitQuantities(null, datasets, rowValues3)

        then:
        rowValues1.get(0) == "${dataset1.quantity} ${resource1.unitForData}"
        rowValues2.get(0) == "${dataset2.quantity} "
        rowValues3.get(0) == ""
    }
}
