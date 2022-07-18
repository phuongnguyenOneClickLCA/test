package com.bionova.optimi.construction.controller

import com.bionova.optimi.configuration.EmailConfiguration
import com.bionova.optimi.construction.controller.admin.CarbonDesignerApiController
import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.CarbonDesign
import com.bionova.optimi.core.domain.mongo.ChildEntity
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.service.CarbonDesignService
import com.bionova.optimi.core.service.CarbonDesigner3DService
import com.bionova.optimi.core.service.EntityService
import com.bionova.optimi.core.service.IndicatorService
import com.bionova.optimi.core.service.UserService
import grails.converters.JSON
import grails.testing.web.controllers.ControllerUnitTest
import org.bson.types.ObjectId
import org.mockito.Mock
import spock.lang.Specification

class CarbonDesignerApiControllerSpec extends Specification implements ControllerUnitTest<CarbonDesignerApiController> {

    //Not quite complete test, does check one case though.
    EntityService entityService
    UserService userService
    EmailConfiguration emailConfiguration
    CarbonDesignService carbonDesignService
    IndicatorService indicatorService

    Closure doWithSpring() {
        { ->
            emailConfiguration(EmailConfiguration)
        }
    }

    //only executed once
    def setup() {

        entityService = Mock(EntityService)
        userService = Mock(UserService)
        controller.entityService = entityService
        controller.userService = userService
        emailConfiguration = Mock(EmailConfiguration)
        carbonDesignService = Mock(CarbonDesignService)
        controller.carbonDesignService = carbonDesignService
        controller.indicatorService = Mock(IndicatorService)
        controller.carbonDesigner3DService=Mock(CarbonDesigner3DService)

    }

    void "user and design are null"() {
        when:
        controller.request.JSON = ["scenarioName": "successTest2", "carbonDesignId": "618e647d6e4cdc0d75c0f244", "public": true, "selectedElementIds": [], "saveEmptyResources": true] as JSON
        controller.request.method = 'PUT'
        controller.createScenario()

        then:
        1 * entityService.getEntityById("618e647d6e4cdc0d75c0f244") >> null
        1 * userService.getCurrentUser(true) >> null

        /*and:
        response.text == '{"message":"No user or carbon design found"}'*/
        response.status == 404
    }

    void "sending empty name to renameCarbonDesign"() {
        given: "a sample carbonId and name"
        String carbonDesignId = "507f191e810c19729de860ea"
        String newCarbonDesignName = ""
        controller.request.JSON = ["carbonDesignId": carbonDesignId, "newCarbonDesignName": ""] as JSON

        and: "newCarbonDesignName is empty"
        newCarbonDesignName.trim().length() <= 0

        when:
        controller.renameCarbonDesign()

        then:
        response.status == 412
    }

    void "sending an already existing design name to renameCarbonDesign"() {
        given:
        CarbonDesign carbonDesign = new CarbonDesign()
        ObjectId objId = new ObjectId("507f191e810c19729de860ea")
        ObjectId parentId = new ObjectId("507f191e810c19729de86579")
        carbonDesign.id = objId
        carbonDesign.name = "unit_test"
        carbonDesign.parentEntityId = parentId
        Entity parent = new Entity()
        parent.id = parentId
        ChildEntity child = new ChildEntity()
        child.operatingPeriodAndName = "unit_test"
        child.entityId = objId
        child.parentId = parentId
        child.entityClass = Constants.EntityClass.CARBON_DESIGN.toString()
        parent.childEntities = [child]
        String newCarbonDesignName = "unit_test"

        and:
        entityService.getEntityById("507f191e810c19729de860ea") >> carbonDesign
        carbonDesignService.nameAlreadyExist(carbonDesign.parentEntityId.toString(), newCarbonDesignName) >> true

        when:
        controller.request.JSON = ["newCarbonDesignName": "unit_test", "carbonDesignId": "507f191e810c19729de860ea"] as JSON
        controller.request.method = 'POST'
        controller.renameCarbonDesign()

        then:
        response.status == 409
    }

    void "sending an empty name to renameCarbonDesign"() {
        when:
        controller.request.JSON = ["newCarbonDesignName": "       ", "carbonDesignId": "507f191e810c19729de860ea"] as JSON
        controller.request.method = 'POST'
        controller.renameCarbonDesign()

        then:
        response.status == 412
    }

    void "sending correct data to renameCarbonDesign"(){
        given:
        String newName="newName"
        String carbonDesignId="507f191e810c19729de860ea"
        ObjectId objId = new ObjectId("507f191e810c19729de860ea")
        CarbonDesign carbonDesign = new CarbonDesign()
        carbonDesign.carbonDesignId=objId
        controller.request.JSON = ["newCarbonDesignName": newName, "carbonDesignId": carbonDesignId] as JSON

        and:
        entityService.getEntityById(carbonDesignId) >> carbonDesign
        carbonDesign.name=newName
        controller.carbonDesignService.rename(carbonDesignId, newName) >> carbonDesign

        when:
        controller.renameCarbonDesign()

        then:
        response.status==200
        response.json.carbonDesignId==carbonDesignId
        response.json.carbonDesignName==newName
    }

    void "sending an indicator id that doesnt exist to getIndicatorByIndicatorId"() {
        given:
        ObjectId objId = new ObjectId("507f191e810c19729de860ea")
        User user = new User()
        user.username = 'name'
        user.id = objId
        String indicatorId = 'fakeId'
        params['indicatorId'] = indicatorId

        and:
        userService.getCurrentUser() >> user
        indicatorService.getIndicatorByIndicatorId(indicatorId) >> null

        when:
        controller.getIndicatorDetails()

        then:
        response.status == 404
    }

    void "sending wrong carbonId to replaceConstituentDatasets"(){
        given:
        params['carbonDesignId'] = 'fakeId'
        params['indicatorId'] = 'fakeId'
        params['constructionManualId'] = 'fakeId'
        ObjectId objId = new ObjectId("507f191e810c19729de860ea")

        when:
        controller.replaceConstituentDatasets()

        then:
        response.status == 404
    }

    void "sending empty name to copyCarbonDesign"(){
        given: "a sample carbonId and name"
        String carbonDesignId="507f191e810c19729de860ea"
        String newCarbonDesignName = ""
        controller.request.JSON = ["carbonDesignId":carbonDesignId,"newCarbonDesignName": ""] as JSON

        and: "newCarbonDesignName is empty"
        newCarbonDesignName.trim().length() <= 0

        when:
        controller.copyCarbonDesign()

        then:
        response.status == 412
    }

    void "sending duplicate name to copyCarbonDesign"(){
        given: "a sample carbonId and name"
        String carbonDesignId="507f191e810c19729de860ea"
        String newCarbonDesignName="newName"
        ObjectId objId = new ObjectId(carbonDesignId)
        ObjectId parentId = new ObjectId("507f191e810c19729de86579")
        CarbonDesign carbonDesign = new CarbonDesign()
        carbonDesign.id = objId
        carbonDesign.name = "unit_test"
        carbonDesign.parentEntityId = parentId
        Entity parent = new Entity()
        parent.id = parentId
        ChildEntity child = new ChildEntity()
        child.operatingPeriodAndName = "newName"
        child.entityId = objId
        child.parentId = parentId
        child.entityClass = Constants.EntityClass.CARBON_DESIGN.toString()
        parent.childEntities = [child]
        controller.request.JSON = ["carbonDesignId":carbonDesignId,"newCarbonDesignName":newCarbonDesignName] as JSON


        and: "parentEntityId childEntities returns true"
        entityService.getEntityById(carbonDesignId) >> carbonDesign
        carbonDesignService.nameAlreadyExist(carbonDesign.parentEntityId.toString(), newCarbonDesignName) >> true

        when:
        controller.copyCarbonDesign()

        then:
        response.status == 409
    }

    void "sending incorrect carbonDesignId to copyCarbonDesign"(){
        given: "a sample carbonId and name"
        String carbonDesignId="507f191e810c19729de860ea"
        String newCarbonDesignName="newName"
        ObjectId objId = new ObjectId(carbonDesignId)
        controller.request.JSON = ["carbonDesignId":carbonDesignId,"newCarbonDesignName":newCarbonDesignName] as JSON
        CarbonDesign carbonDesign=new CarbonDesign()
        carbonDesign.id=objId

        and:"an entity service that cannot find the carbon design"
        entityService.getEntityById(carbonDesignId) >> null

        when:
        controller.copyCarbonDesign()

        then:
        response.status == 400
    }

    void "carbonDesign failed to copy copyCarbonDesign"(){
        given: "a sample carbonId and name"
        String carbonDesignId="507f191e810c19729de860ea"
        String copiedCarbonDesignId="507f1f77bcf86cd799439011"
        String newCarbonDesignName="newName"
        ObjectId objId = new ObjectId(carbonDesignId)
        ObjectId newId = new ObjectId(copiedCarbonDesignId)
        ObjectId parentId = new ObjectId()
        controller.request.JSON = ["carbonDesignId":carbonDesignId,"newCarbonDesignName":newCarbonDesignName] as JSON
        CarbonDesign originalCarbonDesign = new CarbonDesign()
        originalCarbonDesign.id=objId
        originalCarbonDesign.name="sampleName"
        originalCarbonDesign.parentEntityId = parentId
        Entity parent = new Entity()
        parent.id = parentId
        ChildEntity child = new ChildEntity()
        child.operatingPeriodAndName = "newName"
        child.entityId = objId
        child.parentId = parentId
        child.entityClass = Constants.EntityClass.CARBON_DESIGN.toString()
        parent.childEntities = [child]

        and:"an entity service that returns this design"
        entityService.getEntityById(carbonDesignId) >> originalCarbonDesign

        and:"a parentEntity returns false for an already existing name"
        controller.carbonDesignService.nameAlreadyExist(originalCarbonDesign.parentEntityId.toString(), newCarbonDesignName) >> false

        and: "a copy of the carbonDesign is successful"
        CarbonDesign copiedCarbonDesign = new CarbonDesign(originalCarbonDesign.properties)
        copiedCarbonDesign.carbonDesignId = newId
        copiedCarbonDesign.name = newCarbonDesignName
        carbonDesignService.copy(originalCarbonDesign, newCarbonDesignName) >> null

        when:
        controller.copyCarbonDesign()

        then:
        response.status == 412
    }

    void "sending correct data to copyCarbonDesign"(){
        given: "a sample carbonId and name"
        String carbonDesignId="507f191e810c19729de860ea"
        String copiedCarbonDesignId="507f1f77bcf86cd799439011"
        String newCarbonDesignName="newName"
        ObjectId objId = new ObjectId(carbonDesignId)
        ObjectId newId = new ObjectId(copiedCarbonDesignId)
        ObjectId parentId = new ObjectId()
        controller.request.JSON = ["carbonDesignId":carbonDesignId,"newCarbonDesignName":newCarbonDesignName] as JSON
        CarbonDesign originalCarbonDesign = new CarbonDesign()
        originalCarbonDesign.id=objId
        originalCarbonDesign.name="sampleName"
        originalCarbonDesign.parentEntityId = parentId
        Entity parent = new Entity()
        parent.id = parentId
        ChildEntity child = new ChildEntity()
        child.operatingPeriodAndName = "newName"
        child.entityId = objId
        child.parentId = parentId
        child.entityClass = Constants.EntityClass.CARBON_DESIGN.toString()
        parent.childEntities = [child]

        and:"an entity service that returns this design"
        entityService.getEntityById(carbonDesignId) >> originalCarbonDesign

        and:"a parentEntity returns false for an already existing name"
        controller.carbonDesignService.nameAlreadyExist(originalCarbonDesign.parentEntityId.toString(), newCarbonDesignName) >> false

        and: "a copy of the carbonDesign is successful"
        CarbonDesign copiedCarbonDesign = new CarbonDesign(originalCarbonDesign.properties)
        copiedCarbonDesign.carbonDesignId = newId
        copiedCarbonDesign.name = newCarbonDesignName
        carbonDesignService.copy(originalCarbonDesign, newCarbonDesignName) >> copiedCarbonDesign

        when:
        controller.copyCarbonDesign()

        then:
        response.status == 200
        response.json.carbonDesignId == copiedCarbonDesignId
        response.json.carbonDesignName == newCarbonDesignName
    }

    void "sending correct data to createCarbonDesign"(){
        given: "a json request with carbonDesignId and name"
        String carbonDesignId="507f191e810c19729de860ea"
        String carbonDesignName="sampleName"
        ObjectId objId = new ObjectId(carbonDesignId)
        controller.request.JSON = ["carbonDesignId":carbonDesignId,"designName":carbonDesignName] as JSON
        CarbonDesign carbonDesign=new CarbonDesign()
        carbonDesign.id=objId
        carbonDesign.name=carbonDesignName

        and:
        controller.carbonDesignService.createCarbonDesignWithParameters(controller.request.JSON) >> carbonDesign
        carbonDesign.carbonDesignId=objId
        entityService.createEntity(carbonDesign, null, Boolean.FALSE) >> carbonDesign

        when:
        controller.createCarbonDesign()

        then:
        response.status==200
        response.json.carbonDesignId==carbonDesign.carbonDesignId
        response.json.carbonDesignName==carbonDesignName
    }

    void "sending duplicate name to createCarbonDesign"(){
        given: "a json request with carbonDesignId and a duplicate name"
        String carbonDesignId="507f191e810c19729de860ea"
        String carbonDesignName="duplicateName"
        String parentEntityId = new ObjectId().toString()
        ObjectId objId = new ObjectId(carbonDesignId)
        controller.request.JSON = ["carbonDesignId":carbonDesignId,"designName":carbonDesignName, "parentEntityId": parentEntityId] as JSON
        CarbonDesign carbonDesign=new CarbonDesign()
        carbonDesign.id=objId
        carbonDesign.name=carbonDesignName

        and:
        controller.carbonDesignService.nameAlreadyExist(parentEntityId, carbonDesignName) >> true

        when:
        controller.createCarbonDesign()

        then:
        response.status==409
    }

    void "not sending a json to createCarbonDesign"(){
        given:"nothing"

        when:
        controller.createCarbonDesign()

        then:
        response.status==400
    }
}
