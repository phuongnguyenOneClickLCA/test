package com.bionova.optimi.xml.fecEPD

import ch.qos.logback.classic.Logger
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.QuestionAnswerChoice
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.service.DomainClassService
import com.bionova.optimi.core.service.OptimiResourceService
import com.bionova.optimi.core.service.QueryService
import com.bionova.optimi.core.service.QuestionService
import com.bionova.optimi.core.service.UserService
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.util.OptimiStringUtils
import com.bionova.optimi.xml.fecEPD.EPDC
import org.grails.datastore.mapping.model.PersistentProperty
import org.slf4j.LoggerFactory

import javax.xml.bind.JAXBContext
import javax.xml.bind.Unmarshaller

class ImportXML {
    QueryService queryService
    UserService userService
    DomainClassService domainClassService
    OptimiStringUtils optimiStringUtils
    QuestionService questionService
    OptimiResourceService optimiResourceService

    Logger log = LoggerFactory.getLogger(ImportXML.class)

    public ImportXML(QueryService queryService, UserService userSevice, DomainClassService domainClassService,
                     OptimiStringUtils optimiStringUtils, QuestionService questionService, OptimiResourceService optimiResourceService){
        this.queryService = queryService
        this.userService = userSevice
        this.domainClassService = domainClassService
        this.optimiStringUtils = optimiStringUtils
        this.questionService = questionService
        this.optimiResourceService = optimiResourceService
    }

    Map <String, Object> createResource(File xmlFile, String accountId, String fileName, String classificationQuestionId, String classificationParameterId, String epdcFilePath) {
        EPDC epdc = getFecEpdFromXml(xmlFile)

        if (!epdc) {
            xmlFile.delete()
            throw new Exception("${message(code: "epdc_format_error")}")
        }

        User user = userService.getCurrentUser()
        Query query = queryService.getQueryByQueryId("iniesXmlEPDParameters", true)
        Map<String, String> resourceImpactAndIndicatorCode = [:]
        Map<String, String> resourceStageAndPhaseCode = [:]
        Map<String, Map<String, Object>> resourceImpacts = [:]
        Map<String, String> xmlUnitId = [:]
        Map <String, Object> returnable = [:]
        List<String> missingIndicatorCodes = []
        List<String> missingStages = []
        String returnableMessage = ""

        if (query) {
            resourceImpactAndIndicatorCode = query.resourceImpactAndIndicatorCode
            resourceStageAndPhaseCode = query.resourceStageAndPhaseCode
            xmlUnitId = query.xmlUnitId
            Map<String, String> resourceParameters = [:]
            if (classificationQuestionId && classificationParameterId) {
                List<QuestionAnswerChoice> classificationParams = questionService.getQuestion(query, classificationQuestionId)?.choices
                resourceParameters = classificationParams?.find({
                    classificationParameterId.equals(it.answerId)
                })?.resourceParameters
            }
            if (epdc) {
                String epdcNameString = optimiStringUtils.removeSingleAndDoubleQuotes(epdc.name)
                String resourceId = optimiStringUtils.removeDotsAndCommas(epdcNameString?.replaceAll(" ", "") + '-' + epdc.productionDate + '-' + accountId + "-iniesResource")
                Resource resource = Resource.findByResourceId(resourceId)
                if (resource) {
                    resource.nameEN = epdcNameString
                    resource.nameFR = epdcNameString
                    resource.privateDatasetAccountId = accountId
                    resource.privateDataset = Boolean.TRUE
                    resource.environmentDataSourceType = Resource.CONSTANTS.PRIVATE.value()
                    resource.importFile = optimiStringUtils.removeSingleAndDoubleQuotes(fileName)
                    resource.defaultProfile = Boolean.TRUE
                    resource.active = Boolean.TRUE
                    resource.serviceLife = epdc.TLD
                    resource.serviceLifeSource = epdcNameString
                    resource.epdcFilePath = epdcFilePath

                    if (xmlUnitId && epdc.unitId != null) {
                        resource.unitForData = xmlUnitId.get(epdc.unitId.toString())
                    }

                    if (resourceParameters) {
                        List<PersistentProperty> resourcePersistentProperties = domainClassService.getPersistentPropertiesForDomainClass(Resource.class)
                        List<String> persistingListProperties = resourcePersistentProperties?.findAll({
                            it.type?.equals(List)
                        })?.collect({ it.name })
                        List<String> persistingRegularProperties = resourcePersistentProperties?.findAll({
                            !it.type?.equals(List)
                        })?.collect({ it.name })

                        resourceParameters.each { String key, String value ->
                            if (persistingRegularProperties?.contains(key) && value != null) {
                                String sanitizedValue = optimiStringUtils.removeSingleAndDoubleQuotes(value)
                                DomainObjectUtil.callSetterByAttributeName(key, resource, sanitizedValue)
                            }
                            if (persistingListProperties?.contains(key) && value != null) {
                                DomainObjectUtil.callSetterByAttributeName(key, resource, value?.tokenize(",")?.toList())
                            }

                        }
                    }


                    Map<String, Object> MapOfImpacts = [:]

                    epdc.indicators?.each {
                        it.indicator.each {
                            String impactCategory = resourceImpactAndIndicatorCode.get(it.indicatorCode)
                            if (impactCategory) {
                                it?.phases?.each {
                                    it.phase?.each {
                                        String stage = resourceStageAndPhaseCode?.get(it.phaseCode)
                                        if (stage) {
                                            Map<String, Double> impactCategoryAndStageValue = [:]
                                            impactCategoryAndStageValue.put(impactCategory, it.value?.replaceAll(",", ".")?.replaceAll("\\s","")?.toDouble())

                                            if (impactCategoryAndStageValue && !impactCategoryAndStageValue.isEmpty()) {
                                                if (!MapOfImpacts.get(stage)) {
                                                    MapOfImpacts.put(stage, impactCategoryAndStageValue)
                                                } else {
                                                    MapOfImpacts.put(stage, MapOfImpacts.get(stage) + impactCategoryAndStageValue)

                                                }
                                            }
                                        } else {
                                            missingStages.add(it.phaseCode)
                                        }
                                    }
                                }
                            } else {
                                missingIndicatorCodes.add(it.indicatorCode)
                            }
                        }
                    }
                    MapOfImpacts?.each { String stage, Object key ->
                        Map<String, Object> impacts = [:]
                        Resource.allowedImpactCategories?.each { String impact ->
                            Object value = key?.get(impact)
                            if (value != null) {
                                if ("A1-A3".equals(stage)) {
                                    DomainObjectUtil.callSetterByAttributeName(impact, resource, value)
                                }
                                impacts.put(impact, value)
                            }
                        }
                        if (!impacts.isEmpty()) {
                            resourceImpacts.put(stage, impacts)
                        }
                    }
                    resource.impacts = resourceImpacts
                    resource = optimiResourceService.generateDynamicValues(resource)
                    if (!resource.firstUploadTime) {
                        resource.firstUploadTime = new Date()
                    }
                    if (user) {
                        resource.privateDatasetImportedByUserId = user.id.toString()
                    }
                    resource.merge(flush: true, failOnError: true)
                    log.info("updated inies resource ${resource?.nameEN} -- with properties ${resource.properties}")
                    returnableMessage = "updated inies resource ${resource?.nameEN} -- with id ${resource.resourceId}"
                } else {
                    resource = new Resource()
                    resource.resourceId = resourceId
                    resource.nameEN = epdcNameString
                    resource.nameFR = epdcNameString
                    resource.privateDatasetAccountId = accountId
                    resource.privateDataset = Boolean.TRUE
                    resource.environmentDataSourceType = Resource.CONSTANTS.PRIVATE.value()
                    resource.importFile = optimiStringUtils.removeSingleAndDoubleQuotes(fileName)
                    resource.defaultProfile = Boolean.TRUE
                    resource.active = Boolean.TRUE
                    resource.serviceLife = epdc.TLD
                    resource.serviceLifeSource = epdcNameString
                    resource.epdcFilePath = epdcFilePath

                    if (xmlUnitId && epdc.unitId != null) {
                        resource.unitForData = xmlUnitId.get(epdc.unitId.toString())
                    }

                    if (resourceParameters) {
                        List<PersistentProperty> resourcePersistentProperties = domainClassService.getPersistentPropertiesForDomainClass(Resource.class)
                        List<String> persistingListProperties = resourcePersistentProperties?.findAll({
                            it.type?.equals(List)
                        })?.collect({ it.name })
                        List<String> persistingRegularProperties = resourcePersistentProperties?.findAll({
                            !it.type?.equals(List)
                        })?.collect({ it.name })

                        resourceParameters.each { String key, String value ->
                            if (persistingRegularProperties?.contains(key) && value != null) {
                                String sanitizedValue = optimiStringUtils.removeSingleAndDoubleQuotes(value)
                                DomainObjectUtil.callSetterByAttributeName(key, resource, sanitizedValue)
                            }
                            if (persistingListProperties?.contains(key) && value != null) {
                                DomainObjectUtil.callSetterByAttributeName(key, resource, value?.tokenize(",")?.toList())
                            }

                        }
                    }
                    Map<String, Object> MapOfImpacts = [:]

                    epdc.indicators?.each {
                        it.indicator.each {
                            String impactCategory = resourceImpactAndIndicatorCode.get(it.indicatorCode)
                            if (impactCategory) {
                                it?.phases?.each {
                                    it.phase?.each {
                                        String stage = resourceStageAndPhaseCode?.get(it.phaseCode)
                                        if (stage) {
                                            Map<String, Double> impactCategoryAndStageValue = [:]
                                            impactCategoryAndStageValue.put(impactCategory, it.value?.replaceAll(",", ".")?.replaceAll("\\s","")?.toDouble())

                                            if (impactCategoryAndStageValue && !impactCategoryAndStageValue.isEmpty()) {
                                                if (!MapOfImpacts.get(stage)) {
                                                    MapOfImpacts.put(stage, impactCategoryAndStageValue)
                                                } else {
                                                    MapOfImpacts.put(stage, MapOfImpacts.get(stage) + impactCategoryAndStageValue)

                                                }
                                            }
                                        } else {
                                            missingStages.add(it.phaseCode)
                                        }
                                    }
                                }
                            } else {
                                missingIndicatorCodes.add(it.indicatorCode)
                            }
                        }
                    }
                    MapOfImpacts?.each { String stage, Object key ->
                        Map<String, Object> impacts = [:]
                        Resource.allowedImpactCategories?.each { String impact ->
                            Object value = key?.get(impact)
                            if (value != null) {
                                if ("A1-A3".equals(stage)) {
                                    DomainObjectUtil.callSetterByAttributeName(impact, resource, value)
                                }
                                impacts.put(impact, value)
                            }
                        }
                        if (!impacts.isEmpty()) {
                            resourceImpacts.put(stage, impacts)
                        }
                    }
                    resource.impacts = resourceImpacts
                    resource = optimiResourceService.generateDynamicValues(resource)
                    if (!resource.firstUploadTime) {
                        resource.firstUploadTime = new Date()
                    }
                    if (user) {
                        resource.privateDatasetImportedByUserId = user.id.toString()
                    }
                    resource.save(flush: true, failOnError: true)
                    log.info("Created inies resource ${resource?.nameEN} -- with properties ${resource.resourceId} ")
                    returnableMessage = "Created inies resource ${resource?.nameEN} -- with id ${resource.resourceId} "
                }

            }
        }
        if (missingStages && !missingStages.isEmpty()) {
            returnable.put("missingStages", missingStages)
        }
        if (missingIndicatorCodes && !missingIndicatorCodes.isEmpty()) {
            returnable.put("missingIndicatorCodes", missingIndicatorCodes)

        }
        if (returnableMessage) {
            returnable.put("returnableMessage", returnableMessage)
        }
        return returnable
    }

    EPDC getFecEpdFromXml(File xmlFile) {
        EPDC epdc

        if (xmlFile) {
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(EPDC.class)
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller()
                epdc = unmarshaller.unmarshal(xmlFile)
            } catch (Exception e) {
                log.error("Error in creating EPDC from XML:", e)
            }
        }
        return epdc
    }
}
