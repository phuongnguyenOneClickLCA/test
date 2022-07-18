package com.bionova.optimi.core.service

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.Application
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ResourceFilterCriteria
import com.bionova.optimi.core.domain.mongo.ResourcePurpose
import com.mongodb.client.FindIterable
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoCursor
import grails.testing.services.ServiceUnitTest
import grails.testing.spring.AutowiredTest
import org.bson.Document
import spock.lang.Specification


class OptimiResourceServiceSpec extends Specification implements ServiceUnitTest<OptimiResourceService>, AutowiredTest {

    ApplicationService applicationService

    def setup() {
        applicationService = Mock(ApplicationService)
        service.applicationService = applicationService
    }

    def cleanup() {
    }

    void "processApplicationResourcePurpose"() {
        given:
        Application application = Mock()
        ResourcePurpose resourcePurposeCML = Mock()
        ResourcePurpose resourcePurposeTRACI = Mock()
        Boolean removePurposes = false

        MongoCollection mongoResourceCollection = Mock(MongoCollection)
        GroovySpy(Resource, global: true)

        when:
        service.processApplicationResourcePurpose(["CML", "test"], removePurposes)

        then:
        1 * applicationService.getApplicationByApplicationId(Constants.LCA_APPID) >> application
        1 * application.resourcePurposes >> [resourcePurposeCML, resourcePurposeTRACI]
        _ * resourcePurposeCML.attribute >> "CML"
        _ * resourcePurposeTRACI.attribute >> "TRACI"
        1 * resourcePurposeCML.filterConditions >> _
        0 * resourcePurposeTRACI.filterConditions
        1 * Resource.collection >> mongoResourceCollection
        1 * mongoResourceCollection.find(_)
    }

    void "processResourcePurpose"() {
        given:
        ResourcePurpose resourcePurpose = Mock()
        Boolean removePurposes = false

        MongoCollection mongoResourceCollection = Mock(MongoCollection)
        GroovySpy(Resource, global: true)

        when:
        service.processResourcePurpose(resourcePurpose, removePurposes)

        then:
        0 * resourcePurpose.attribute
        1 * resourcePurpose.filterConditions >> _
        1 * Resource.collection >> mongoResourceCollection
        1 * mongoResourceCollection.find(_)
    }

    void "processResourcePurpose removePurposes"() {
        given:
        ResourcePurpose resourcePurpose = Mock()
        Boolean removePurposes = true

        MongoCollection mongoResourceCollection = Mock(MongoCollection)
        GroovySpy(Resource, global: true)

        when:
        service.processResourcePurpose(resourcePurpose, removePurposes)

        then:
        1 * resourcePurpose.attribute >> _
        0 * resourcePurpose.filterConditions
        1 * Resource.collection >> mongoResourceCollection
        1 * mongoResourceCollection.find(_)
    }

    void "processResourcePurpose with resourceDocuments"() {
        given:
        ResourcePurpose resourcePurpose = Mock()
        Boolean removePurposes = false

        MongoCollection mongoResourceCollection = Mock(MongoCollection)
        FindIterable iterable = Mock(FindIterable)
        MongoCursor mongoCursor = Mock(MongoCursor)
        GroovySpy(Resource, global: true)

        Document resourceDocument = Mock()

        when:
        service.processResourcePurpose(resourcePurpose, removePurposes)

        then:
        0 * resourcePurpose.attribute
        1 * resourcePurpose.filterConditions >> _
        2 * Resource.collection >> mongoResourceCollection // 2 - find and update
        1 * mongoResourceCollection.find(_) >> iterable
        1 * iterable.iterator() >> mongoCursor
        2 * mongoCursor.hasNext() >>> [true, false]
        1 * mongoCursor.next() >>> [resourceDocument]
        1 * resourceDocument.get("disableAutomaticPurposes") >> true
        2 * resourceDocument.get("enabledPurposes") >> ["YMTool", "CML", "klimatdeklaration"]
        2 * resourceDocument.get("manuallyEnabledPurposes") >> ["CML", "TRACI"]
        1 * resourcePurpose.validityCondititions >> null
        1 * mongoResourceCollection.updateOne(_, _)
    }

    void "runAdvancedResourceFilterCriteriaForResourceDocuments for empty criteria list"() {
        given:
        Document resourceDocument = Mock()
        List<Document> resourceDocumentList = [resourceDocument]

        when:
        List<Document> filteredDocuments = service.runAdvancedResourceFilterCriteriaForResourceDocuments(resourceDocumentList, null)

        then:
        filteredDocuments == resourceDocumentList
    }

    void "runAdvancedResourceFilterCriteriaForResourceDocuments"() {
        given:
        Document validResourceDocument = Mock()
        Document expiredResourceDocument = Mock()
        List<Document> resourceDocumentList = [validResourceDocument, expiredResourceDocument]

        ResourceFilterCriteria resourceFilterCriteriaExpired = Mock()
        List<ResourceFilterCriteria> resourceFilterCriteriaList = [resourceFilterCriteriaExpired]

        when:
        List<Document> filteredDocuments = service.runAdvancedResourceFilterCriteriaForResourceDocuments(resourceDocumentList, resourceFilterCriteriaList)

        then:
        2 * resourceFilterCriteriaExpired.resolve >> Constants.FilteringMethod.EXPIRED.getMethod()
        1 * validResourceDocument.get("isExpiredDataPoint") >> false
        1 * expiredResourceDocument.get("isExpiredDataPoint") >> true

        and:
        filteredDocuments == [validResourceDocument]
    }

    void "filterResourceDocumentsByResourceFilterCriteriaObjects"() {
        given:
        Document validResourceDocument = Mock()

        ResourceFilterCriteria resourceFilterCriteriaExpired = Mock()
        ResourceFilterCriteria resourceFilterCriteriaExists = Mock()
        ResourceFilterCriteria resourceFilterCriteriaPresent = Mock()
        ResourceFilterCriteria resourceFilterCriteriaAny = Mock()
        ResourceFilterCriteria resourceFilterCriteriaNull = Mock() // means all
        ResourceFilterCriteria resourceFilterCriteriaGt = Mock()

        ResourceFilterCriteria irrelevantResourceFilterCriteria = Mock()

        List<ResourceFilterCriteria> resourceFilterCriteriaList = [
                resourceFilterCriteriaExpired,
                irrelevantResourceFilterCriteria,
                resourceFilterCriteriaExists,
                resourceFilterCriteriaPresent,
                resourceFilterCriteriaAny,
                resourceFilterCriteriaNull,
                resourceFilterCriteriaGt
        ]

        when:
        boolean isValid = service.filterResourceDocumentsByResourceFilterCriteriaObjects(validResourceDocument, resourceFilterCriteriaList)

        then:
        1 * resourceFilterCriteriaExpired.applyCondition >> null
        1 * irrelevantResourceFilterCriteria.applyCondition >> "presentInDataset"
        1 * resourceFilterCriteriaExists.applyCondition >> null
        1 * resourceFilterCriteriaPresent.applyCondition >> null
        1 * resourceFilterCriteriaAny.applyCondition >> null
        1 * resourceFilterCriteriaNull.applyCondition >> null
        1 * resourceFilterCriteriaGt.applyCondition >> null

        0 * irrelevantResourceFilterCriteria.attribute
        0 * irrelevantResourceFilterCriteria.resolve
        0 * irrelevantResourceFilterCriteria.values

        1 * resourceFilterCriteriaExpired.attribute >> null
        1 * resourceFilterCriteriaExpired.resolve >> Constants.FilteringMethod.EXPIRED.getMethod()
        0 * resourceFilterCriteriaExpired.values
        1 * validResourceDocument.get("isExpiredDataPoint") >> false

        1 * resourceFilterCriteriaExists.attribute >> "density"
        1 * resourceFilterCriteriaExists.resolve >> Constants.FilteringMethod.EXISTS.getMethod()
        0 * resourceFilterCriteriaExists.values
        1 * validResourceDocument.containsKey("density") >> true
        1 * validResourceDocument.get("density") >> 1000.0

        1 * resourceFilterCriteriaPresent.attribute >> "brandImageId"
        1 * resourceFilterCriteriaPresent.resolve >> Constants.FilteringMethod.PRESENT.getMethod()
        0 * resourceFilterCriteriaPresent.values
        1 * validResourceDocument.containsKey("brandImageId") >> true
        1 * validResourceDocument.get("brandImageId") >> "test"

        1 * resourceFilterCriteriaAny.attribute >> "areas"
        1 * resourceFilterCriteriaAny.resolve >> Constants.FilteringMethod.ANY.getMethod()
        1 * resourceFilterCriteriaAny.values >> ["bulgaria", "lithuania"]
        1 * validResourceDocument.containsKey("areas") >> true
        1 * validResourceDocument.get("areas") >> "lithuania"

        1 * resourceFilterCriteriaNull.attribute >> "combinedUnits"
        1 * resourceFilterCriteriaNull.resolve >> null
        1 * resourceFilterCriteriaNull.values >> ["m3", "m2"]
        1 * validResourceDocument.containsKey("combinedUnits") >> true
        1 * validResourceDocument.get("combinedUnits") >> ["m2", "m3"]

        1 * resourceFilterCriteriaGt.attribute >> "testGt"
        1 * resourceFilterCriteriaGt.resolve >> Constants.FilteringMethod.GREATER_THAN.getMethod()
        1 * resourceFilterCriteriaGt.values >> [50]
        1 * validResourceDocument.containsKey("testGt") >> true
        1 * validResourceDocument.get("testGt") >> 100

        and:
        isValid == true
    }

    void "filterResourceDocumentsByResourceFilterCriteriaObjects with expired"() {
        given:
        Document expiredResourceDocument = Mock()

        ResourceFilterCriteria resourceFilterCriteriaExpired = Mock()
        ResourceFilterCriteria resourceFilterCriteriaExists = Mock()

        List<ResourceFilterCriteria> resourceFilterCriteriaList = [
                resourceFilterCriteriaExpired,
                resourceFilterCriteriaExists
        ]

        when:
        boolean isValid = service.filterResourceDocumentsByResourceFilterCriteriaObjects(expiredResourceDocument, resourceFilterCriteriaList)

        then:
        1 * resourceFilterCriteriaExpired.attribute >> null
        1 * resourceFilterCriteriaExpired.resolve >> Constants.FilteringMethod.EXPIRED.getMethod()
        0 * resourceFilterCriteriaExpired.values
        1 * expiredResourceDocument.get("isExpiredDataPoint") >> true

        0 * resourceFilterCriteriaExists.attribute >> "density"
        0 * resourceFilterCriteriaExists.resolve >> Constants.FilteringMethod.EXISTS.getMethod()
        0 * resourceFilterCriteriaExists.values

        and:
        isValid == false
    }

    void "filterResourceDocumentsByResourceFilterCriteriaObjects without resourceFilterCriteriaList"() {
        given:
        Document resourceDocument = Mock()

        when:
        boolean isValid = service.filterResourceDocumentsByResourceFilterCriteriaObjects(resourceDocument, null)

        then:
        isValid == true
    }

    void "filterResourceDocumentsByResourceFilterCriteriaObjects without ResourceDocument"() {
        given:
        ResourceFilterCriteria resourceFilterCriteria = Mock()

        when:
        boolean isValid = service.filterResourceDocumentsByResourceFilterCriteriaObjects(null, [resourceFilterCriteria])

        then:
        isValid == true
    }

    void "getResourceDocumentsByResourcePurposeFilterConditions"() {
        given:
        ResourcePurpose resourcePurpose = Mock()

        MongoCollection mongoResourceCollection = Mock(MongoCollection)
        GroovySpy(Resource, global: true)

        when:
        service.getResourceDocumentsByResourcePurposeFilterConditions(resourcePurpose)

        then:
        1 * resourcePurpose.filterConditions >> _
        1 * Resource.collection >> mongoResourceCollection
        1 * mongoResourceCollection.find(_)
    }

    void "getResourceDocumentsWithResourcePurpose"() {
        given:
        ResourcePurpose resourcePurpose = Mock()

        MongoCollection mongoResourceCollection = Mock(MongoCollection)
        GroovySpy(Resource, global: true)

        when:
        service.getResourceDocumentsWithResourcePurpose(resourcePurpose)

        then:
        1 * resourcePurpose.attribute >> _
        1 * Resource.collection >> mongoResourceCollection
        1 * mongoResourceCollection.find(_)
    }

    void "updateResourceDocumentFromResourcePurpose"() {
        given:
        Document resourceDocument = Mock()
        ResourcePurpose resourcePurpose = Mock()
        Boolean removePurposes = false

        MongoCollection mongoResourceCollection = Mock(MongoCollection)
        GroovySpy(Resource, global: true)

        when:
        service.updateResourceDocumentFromResourcePurpose(resourceDocument, resourcePurpose, removePurposes)

        then:
        2 * resourceDocument.get("enabledPurposes") >> ["YMTool", "CML", "klimatdeklaration"]
        2 * resourceDocument.get("automaticallyEnabledPurposes") >> ["YMTool", "CML", "klimatdeklaration"]
        1 * resourceDocument.get("disableAutomaticPurposes") >> false
        1 * resourcePurpose.attribute >> "CML"
        2 * resourceDocument.get("manuallyEnabledPurposes") >> ["CML", "TRACI"]
        1 * Resource.collection >> mongoResourceCollection
        1 * resourceDocument.get("_id") >> "resourceDocumentId"
        1 * mongoResourceCollection.updateOne(["_id": "resourceDocumentId"], [$set: [enabledPurposes: ["YMTool", "CML", "klimatdeklaration", "TRACI"]]])
    }

    void "updateResourceDocumentFromResourcePurpose unchanged updatedFields"() {
        given:
        Document resourceDocument = Mock()
        ResourcePurpose resourcePurpose = Mock()
        Boolean removePurposes = false

        GroovySpy(Resource, global: true)

        when:
        service.updateResourceDocumentFromResourcePurpose(resourceDocument, resourcePurpose, removePurposes)

        then:
        2 * resourceDocument.get("enabledPurposes") >> ["YMTool", "CML", "klimatdeklaration"]
        2 * resourceDocument.get("automaticallyEnabledPurposes") >> ["YMTool", "CML", "klimatdeklaration"]
        1 * resourceDocument.get("disableAutomaticPurposes") >> false
        1 * resourcePurpose.attribute >> "CML"
        1 * resourceDocument.get("manuallyEnabledPurposes") >> null
        0 * Resource.collection
    }

    void "prepareResourceFieldsToUpdateFromResourcePurpose init"() {
        given:
        Document resourceDocument = Mock()
        ResourcePurpose resourcePurpose = Mock()
        Boolean removePurposes = false

        when:
        Map updatedFields = service.prepareResourceFieldsToUpdateFromResourcePurpose(resourceDocument, resourcePurpose, removePurposes)

        then:
        1 * resourceDocument.get("enabledPurposes") >> null
        1 * resourceDocument.get("automaticallyEnabledPurposes") >> null
        1 * resourceDocument.get("disableAutomaticPurposes") >> null
        1 * resourcePurpose.attribute >> "CML"
        1 * resourceDocument.get("manuallyEnabledPurposes") >> null

        and:
        updatedFields.enabledPurposes == ['CML']
        updatedFields.automaticallyEnabledPurposes == ['CML']
    }

    void "prepareResourceFieldsToUpdateFromResourcePurpose init manuallyEnabledPurposes"() {
        given:
        Document resourceDocument = Mock()
        ResourcePurpose resourcePurpose = Mock()
        Boolean removePurposes = false

        when:
        Map updatedFields = service.prepareResourceFieldsToUpdateFromResourcePurpose(resourceDocument, resourcePurpose, removePurposes)

        then:
        1 * resourceDocument.get("enabledPurposes") >> null
        1 * resourceDocument.get("automaticallyEnabledPurposes") >> null
        1 * resourceDocument.get("disableAutomaticPurposes") >> null
        1 * resourcePurpose.attribute >> "CML"
        2 * resourceDocument.get("manuallyEnabledPurposes") >> ["CML", "TRACI"]

        and:
        updatedFields.enabledPurposes == ["CML", "TRACI"]
        updatedFields.automaticallyEnabledPurposes == ["CML"]
    }

    void "prepareResourceFieldsToUpdateFromResourcePurpose manuallyEnabledPurposes"() {
        given:
        Document resourceDocument = Mock()
        ResourcePurpose resourcePurpose = Mock()
        Boolean removePurposes = false

        when:
        Map updatedFields = service.prepareResourceFieldsToUpdateFromResourcePurpose(resourceDocument, resourcePurpose, removePurposes)

        then:
        1 * resourceDocument.get("enabledPurposes") >> ["YMTool", "CML", "klimatdeklaration"]
        1 * resourceDocument.get("automaticallyEnabledPurposes") >> ["YMTool", "CML", "klimatdeklaration"]
        1 * resourceDocument.get("disableAutomaticPurposes") >> false
        1 * resourcePurpose.attribute >> "CML"
        2 * resourceDocument.get("manuallyEnabledPurposes") >> ["CML", "TRACI"]

        and:
        updatedFields.enabledPurposes == ["YMTool", "CML", "klimatdeklaration", "TRACI"]
        updatedFields.automaticallyEnabledPurposes == ["YMTool", "CML", "klimatdeklaration"]
    }

    void "prepareResourceFieldsToUpdateFromResourcePurpose disableAutomaticPurposes"() {
        given:
        Document resourceDocument = Mock()
        ResourcePurpose resourcePurpose = Mock()
        Boolean removePurposes = false

        when:
        Map updatedFields = service.prepareResourceFieldsToUpdateFromResourcePurpose(resourceDocument, resourcePurpose, removePurposes)

        then:
        1 * resourceDocument.get("enabledPurposes") >> ["YMTool", "CML", "klimatdeklaration", "TRACI"]
        1 * resourceDocument.get("automaticallyEnabledPurposes") >> ["YMTool", "CML", "klimatdeklaration", "TRACI"]
        1 * resourceDocument.get("disableAutomaticPurposes") >> true
        0 * resourcePurpose.attribute
        1 * resourceDocument.get("manuallyEnabledPurposes") >> null

        and:
        updatedFields.enabledPurposes == []
        updatedFields.automaticallyEnabledPurposes == []
    }

    void "prepareResourceFieldsToUpdateFromResourcePurpose disableAutomaticPurposes manuallyEnabledPurposes"() {
        given:
        Document resourceDocument = Mock()
        ResourcePurpose resourcePurpose = Mock()
        Boolean removePurposes = false

        when:
        Map updatedFields = service.prepareResourceFieldsToUpdateFromResourcePurpose(resourceDocument, resourcePurpose, removePurposes)

        then:
        1 * resourceDocument.get("enabledPurposes") >> ["YMTool", "CML", "klimatdeklaration", "TRACI"]
        1 * resourceDocument.get("automaticallyEnabledPurposes") >> ["YMTool", "CML", "klimatdeklaration", "TRACI"]
        1 * resourceDocument.get("disableAutomaticPurposes") >> true
        0 * resourcePurpose.attribute
        2 * resourceDocument.get("manuallyEnabledPurposes") >> ["CML", "TRACI"]

        and:
        updatedFields.enabledPurposes == ["CML", "TRACI"]
        updatedFields.automaticallyEnabledPurposes == []
    }

    void "prepareResourceFieldsToUpdateFromResourcePurpose init removePurposes"() {
        given:
        Document resourceDocument = Mock()
        ResourcePurpose resourcePurpose = Mock()
        Boolean removePurposes = true

        when:
        Map updatedFields = service.prepareResourceFieldsToUpdateFromResourcePurpose(resourceDocument, resourcePurpose, removePurposes)

        then:
        1 * resourceDocument.get("enabledPurposes") >> null
        1 * resourceDocument.get("automaticallyEnabledPurposes") >> null
        1 * resourceDocument.get("disableAutomaticPurposes") >> null
        1 * resourcePurpose.attribute >> "CML"
        1 * resourceDocument.get("manuallyEnabledPurposes") >> null

        and:
        updatedFields.enabledPurposes == []
        updatedFields.automaticallyEnabledPurposes == []
    }

    void "prepareResourceFieldsToUpdateFromResourcePurpose init removePurposes manuallyEnabledPurposes"() {
        given:
        Document resourceDocument = Mock()
        ResourcePurpose resourcePurpose = Mock()
        Boolean removePurposes = true

        when:
        Map updatedFields = service.prepareResourceFieldsToUpdateFromResourcePurpose(resourceDocument, resourcePurpose, removePurposes)

        then:
        1 * resourceDocument.get("enabledPurposes") >> null
        1 * resourceDocument.get("automaticallyEnabledPurposes") >> null
        1 * resourceDocument.get("disableAutomaticPurposes") >> null
        1 * resourcePurpose.attribute >> "CML"
        2 * resourceDocument.get("manuallyEnabledPurposes") >> ["CML", "TRACI"]

        and:
        updatedFields.enabledPurposes == ["CML", "TRACI"]
        updatedFields.automaticallyEnabledPurposes == []
    }

    void "prepareResourceFieldsToUpdateFromResourcePurpose removePurposes"() {
        given:
        Document resourceDocument = Mock()
        ResourcePurpose resourcePurpose = Mock()
        Boolean removePurposes = true

        when:
        Map updatedFields = service.prepareResourceFieldsToUpdateFromResourcePurpose(resourceDocument, resourcePurpose, removePurposes)

        then:
        1 * resourceDocument.get("enabledPurposes") >> ["YMTool", "CML", "klimatdeklaration", "TRACI"]
        1 * resourceDocument.get("automaticallyEnabledPurposes") >> ["YMTool", "CML", "klimatdeklaration", "TRACI"]
        1 * resourceDocument.get("disableAutomaticPurposes") >> false
        1 * resourcePurpose.attribute >> "CML"
        1 * resourceDocument.get("manuallyEnabledPurposes") >> null

        and:
        updatedFields.enabledPurposes == ["YMTool", "klimatdeklaration", "TRACI"]
        updatedFields.automaticallyEnabledPurposes == ["YMTool", "klimatdeklaration", "TRACI"]
    }

    void "prepareResourceFieldsToUpdateFromResourcePurpose removePurposes manuallyEnabledPurposes"() {
        given:
        Document resourceDocument = Mock()
        ResourcePurpose resourcePurpose = Mock()
        Boolean removePurposes = true

        when:
        Map updatedFields = service.prepareResourceFieldsToUpdateFromResourcePurpose(resourceDocument, resourcePurpose, removePurposes)

        then:
        1 * resourceDocument.get("enabledPurposes") >> ["YMTool", "CML", "klimatdeklaration", "TRACI"]
        1 * resourceDocument.get("automaticallyEnabledPurposes") >> ["YMTool", "CML", "klimatdeklaration", "TRACI"]
        1 * resourceDocument.get("disableAutomaticPurposes") >> false
        1 * resourcePurpose.attribute >> "CML"
        2 * resourceDocument.get("manuallyEnabledPurposes") >> ["CML", "TRACI"]

        and:
        updatedFields.enabledPurposes == ["YMTool", "klimatdeklaration", "TRACI", "CML"]
        updatedFields.automaticallyEnabledPurposes == ["YMTool", "klimatdeklaration", "TRACI"]
    }

    void "prepareResourceFieldsToUpdateFromResourcePurpose removePurposes disableAutomaticPurposes"() {
        given:
        Document resourceDocument = Mock()
        ResourcePurpose resourcePurpose = Mock()
        Boolean removePurposes = true

        when:
        Map updatedFields = service.prepareResourceFieldsToUpdateFromResourcePurpose(resourceDocument, resourcePurpose, removePurposes)

        then:
        1 * resourceDocument.get("enabledPurposes") >> ["YMTool", "CML", "klimatdeklaration", "TRACI"]
        1 * resourceDocument.get("automaticallyEnabledPurposes") >> ["YMTool", "CML", "klimatdeklaration", "TRACI"]
        1 * resourceDocument.get("disableAutomaticPurposes") >> true
        0 * resourcePurpose.attribute
        1 * resourceDocument.get("manuallyEnabledPurposes") >> null

        and:
        updatedFields.enabledPurposes == []
        updatedFields.automaticallyEnabledPurposes == []
    }

    void "prepareResourceFieldsToUpdateFromResourcePurpose removePurposes disableAutomaticPurposes manuallyEnabledPurposes"() {
        given:
        Document resourceDocument = Mock()
        ResourcePurpose resourcePurpose = Mock()
        Boolean removePurposes = true

        when:
        Map updatedFields = service.prepareResourceFieldsToUpdateFromResourcePurpose(resourceDocument, resourcePurpose, removePurposes)

        then:
        1 * resourceDocument.get("enabledPurposes") >> ["YMTool", "CML", "klimatdeklaration", "TRACI"]
        1 * resourceDocument.get("automaticallyEnabledPurposes") >> ["YMTool", "CML", "klimatdeklaration", "TRACI"]
        1 * resourceDocument.get("disableAutomaticPurposes") >> true
        0 * resourcePurpose.attribute
        2 * resourceDocument.get("manuallyEnabledPurposes") >> ["CML", "TRACI"]

        and:
        updatedFields.enabledPurposes == ["CML", "TRACI"]
        updatedFields.automaticallyEnabledPurposes == []
    }
}
