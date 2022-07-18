/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */
package com.bionova.optimi.core.service

import com.bionova.optimi.construction.Constants.SessionAttribute
import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.CalculationResult
import com.bionova.optimi.core.domain.mongo.CalculationRule
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.EntityFile
import com.bionova.optimi.core.domain.mongo.ImportMapper
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.IndicatorXmlMapper
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ResourceType
import com.bionova.optimi.core.domain.mongo.ResultCategory
import com.bionova.optimi.core.domain.mongo.ValueReference
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.core.util.LoggerUtil
import com.bionova.optimi.frenchTools.FrenchConstants
import com.bionova.optimi.frenchTools.helpers.RsetMapping
import com.bionova.optimi.ui.rset.BatimentDto
import com.bionova.optimi.ui.rset.DesignRowRsetTable
import com.bionova.optimi.ui.rset.RSEnvDatasetDto
import com.bionova.optimi.ui.rset.RsetDto
import com.bionova.optimi.ui.rset.ZoneDto
import com.bionova.optimi.ui.rset.ZoneForRender
import com.bionova.optimi.xml.fecEPD.EPDC as FecEPDC
import com.bionova.optimi.xml.fecRSEnv.Projet as FecProjet
import com.bionova.optimi.xml.ilcd.AdministrativeInformationType
import com.bionova.optimi.xml.ilcd.Amount
import com.bionova.optimi.xml.ilcd.ClassType
import com.bionova.optimi.xml.ilcd.ClassificationInformationType
import com.bionova.optimi.xml.ilcd.ClassificationType
import com.bionova.optimi.xml.ilcd.DataEntryByType
import com.bionova.optimi.xml.ilcd.DataSetInformationType
import com.bionova.optimi.xml.ilcd.ExchangeDirectionValues
import com.bionova.optimi.xml.ilcd.ExchangeType
import com.bionova.optimi.xml.ilcd.ExchangesType
import com.bionova.optimi.xml.ilcd.FTMultiLang
import com.bionova.optimi.xml.ilcd.GeographyType
import com.bionova.optimi.xml.ilcd.GlobalReferenceType
import com.bionova.optimi.xml.ilcd.GlobalReferenceTypeValues
import com.bionova.optimi.xml.ilcd.ILCDNamespacePrefixMapper
import com.bionova.optimi.xml.ilcd.LCIAResultType
import com.bionova.optimi.xml.ilcd.LCIAResultsType
import com.bionova.optimi.xml.ilcd.LocationOfOperationSupplyOrProductionType
import com.bionova.optimi.xml.ilcd.NameType
import com.bionova.optimi.xml.ilcd.Other
import com.bionova.optimi.xml.ilcd.ProcessDataSetType
import com.bionova.optimi.xml.ilcd.ProcessInformationType
import com.bionova.optimi.xml.ilcd.PublicationAndOwnershipType
import com.bionova.optimi.xml.ilcd.STMultiLang
import com.bionova.optimi.xml.ilcd.StringMultiLang
import com.bionova.optimi.xml.ilcd.TechnologyType
import com.bionova.optimi.xml.ilcd.TimeType
import com.bionova.optimi.xml.re2020RSEnv.Projet as Re2020Projet
import com.bionova.optimi.xml.fecRSEnv.RSET
import com.bionova.optimi.xml.fecRSEnv.RSEnv as FecRSEnv
import com.bionova.optimi.xml.re2020RSEnv.RSEnv as Re2020RSEnv
import com.bionova.optimi.xml.re2020RSEnv.RSET as Re2020RSET
import com.bionova.optimi.xml.re2020EPD.EPDC as Re2020EPDC
import grails.core.GrailsApplication
import grails.gsp.PageRenderer
import groovy.transform.CompileStatic
import org.apache.commons.collections.CollectionUtils
import org.bson.types.ObjectId
import org.grails.datastore.mapping.core.Session
import org.grails.plugins.web.taglib.ValidationTagLib
import org.xml.sax.SAXParseException

import javax.servlet.http.HttpSession
import javax.xml.XMLConstants
import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller
import javax.xml.bind.Unmarshaller
import javax.xml.datatype.DatatypeFactory
import javax.xml.datatype.XMLGregorianCalendar
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.SchemaFactory
import javax.xml.validation.Validator
import java.text.DateFormat
import java.text.SimpleDateFormat

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 *
 */
class XmlService extends GormCleanerService {

    def queryService
    def userService
    def optimiResourceService
    def optimiStringUtils
    def questionService
    def domainClassService
    def entityService
    def datasetService
    def indicatorService
    def newCalculationServiceProxy
    def fecService
    def re2020Service
    def errorMessageUtil
    def stringUtilsService
    def entityFileService
    GrailsApplication grailsApplication
    LoggerUtil loggerUtil
    FlashService flashService
    PageRenderer groovyPageRenderer
    ResourceService resourceService


    static transactional = "mongo"

    private static final List<String> residentialZones = ['1', '2']
    private static final String FEC_XSD_FILE = "com${File.separator}bionova${File.separator}optimi${File.separator}xml${File.separator}fecEPD${File.separator}FicheConfiguree.xsd"
    private static final String RE2020_XSD_FILE = "com${File.separator}bionova${File.separator}optimi${File.separator}xml${File.separator}re2020EPD${File.separator}FicheConfiguree_v2021_C1_beta2.xsd"

    Map<String, Object> createResource(File xmlFile, String accountId, String fileName, String classificationQuestionId, String classificationParameterId, String epdcFilePath) {
        def fecImportXml

        if (isFecEpdcFile(xmlFile)) {
            fecImportXml = new com.bionova.optimi.xml.fecEPD.ImportXML(queryService,
                    userService, domainClassService, optimiStringUtils, questionService, optimiResourceService)
        } else if (isRe2020EpdcFile(xmlFile)) {
            fecImportXml = new com.bionova.optimi.xml.re2020EPD.ImportXML(queryService,
                    userService, domainClassService, optimiStringUtils, questionService, optimiResourceService)
        } else {
            throw new RuntimeException("XMl file is not compatible nor with FEC nor with RE2020 schema.")
        }

        return fecImportXml.createResource(xmlFile, accountId, fileName, classificationQuestionId, classificationParameterId, epdcFilePath)
    }

    String getRsetBatimentMessage(RsetDto rset) {
        String message = ""

        if (rset) {
            rset.batimentCollection?.each { batiment ->
                boolean hasZones = false
                message = message + "<ul><li>Batiment ${batiment.index}. ${batiment.name}</li>"

                batiment.zoneCollection?.eachWithIndex { zone, i ->
                    if (i == 0) {
                        message = message + "<ul><li>Zone ${zone.index}. ${zone.name}</li>"
                    } else {
                        message = message + "<li>Zone ${zone.index}. ${zone.name}</li>"
                    }
                    hasZones = true
                }

                if (hasZones) {
                    message = message + "</ul>"
                }
                message = message + "</ul>"
            }
        }
        return message
    }

    /**
     * Field rsetMappings in Entity is deprecated. This method transfer the saved mapping to new field
     * @param parent
     * @param doSave
     */
    void transferRsetMappingToNewField() {
        List<Entity> frenchParents = Entity.withCriteria {
            and {
                isNotNull('rsetMappings')
                isNull('frenchRsetMappings')
            }
        }

        if (!frenchParents) {
            return
        }
        log.info("Start transferring RSET mapping from deprecated field to new field for ${frenchParents.size()} projects.")
        Entity.withSession { Session session ->
            for (Entity parent in frenchParents) {
                if (!parent?.rsetMappings) {
                    continue
                }
                Map<String, Map<String, Integer>> batimentZoneIndexMappings = parent.rsetMappings.get(Constants.RsetMapping.BATIMENT_ZONE_INDEX_MAPPINGS.getAttribute())
                Map<String, Integer> batimentIndexMappings = parent.rsetMappings.get(Constants.RsetMapping.BATIMENT_INDEX_MAPPINGS.getAttribute())
                RsetMapping fecRsetMapping = new RsetMapping(questionId: FrenchConstants.FEC_RSET_QUESTIONID, batimentIndexMappings: batimentIndexMappings, batimentZoneIndexMappings: batimentZoneIndexMappings)

                if (parent.frenchRsetMappings) {
                    parent.frenchRsetMappings.add(fecRsetMapping)
                } else {
                    parent.frenchRsetMappings = [fecRsetMapping]
                }

                // This is deprecated
                parent.rsetMappings = null

                log.info("Modified project ${parent.id.toString()}, name: ${parent.name}")
                parent.save()
            }
            session.flush()
        }
    }

    /**
     * Save the mapping from front end to entity
     * @param parent
     * @param batimentMapPerQuestion <questionId, <designId, batimentIndex>>
     * @param zoneMappingPerQuestion <questionId, <batimentIndex, <designId, zoneId.batimentZoneIndex>>>
     * @param parcelleMaping a map from params specifying the designId that user selects for exporting the parcelle from
     * @return
     */
    Entity saveBatimentAndZonesMapping(Entity parent, Map batimentMapPerQuestion, Map zoneMappingPerQuestion, Map parcelleMaping) {
        if (!parent || !batimentMapPerQuestion || !zoneMappingPerQuestion) {
            return parent
        }

        batimentMapPerQuestion.removeAll { (it.key as String)?.contains('.') }
        zoneMappingPerQuestion.removeAll { (it.key as String)?.contains('.') }
        parcelleMaping?.removeAll { (it.key as String)?.contains('.') }
        List<Entity> designsBeforSort = parent?.childrenByChildEntities?.findAll({ !it.deleted })

        // spaghetti is served! :)
        batimentMapPerQuestion.each { String questionId, Map<String, String> batimentMap ->
            // start saving mapping for each question (for different tools)
            Map<String, Integer> batimentIndexMappings = [:]
            Map<String, Map<String, Integer>> batimentZoneIndexMappings = [:]
            // there should always be only one value. Otherwise is bug and recheck frontend
            String designIdForParcelle = parcelleMaping?.get(questionId)

            batimentMap?.each { String designId, String batimentIndex ->
                if (designId && batimentIndex) {
                    batimentIndexMappings.put(designId, batimentIndex.toInteger())
                    def temp = zoneMappingPerQuestion?.get(questionId)?.get(batimentIndex)?.get(designId)
                    List<String> batimentZoneMapping

                    if (temp instanceof String[]) {
                        batimentZoneMapping = temp
                    } else {
                        batimentZoneMapping = [temp]
                    }

                    if (batimentZoneMapping) {
                        batimentZoneMapping.each { something ->
                            if (something && something.contains(".")) {
                                List<String> tokenized = something.tokenize(".")

                                if (tokenized?.size() == 2 && tokenized[0].isNumber() && tokenized[1].isNumber()) {
                                    Map<String, Integer> zoneMapForThisBatiment = batimentZoneIndexMappings.get(designId)
                                    String zoneId = tokenized[0]
                                    String batimentZoneIndex = tokenized[1]

                                    if (zoneMapForThisBatiment) {
                                        zoneMapForThisBatiment.put(zoneId, batimentZoneIndex.toInteger())
                                    } else {
                                        def newZoneMap = [:]
                                        newZoneMap.put(zoneId, batimentZoneIndex.toInteger())
                                        zoneMapForThisBatiment = newZoneMap
                                    }
                                    batimentZoneIndexMappings.put(designId, zoneMapForThisBatiment)
                                }
                            }
                        }
                    }
                }
            }

            // Map design to batiment index based on user selection.
            if (!batimentIndexMappings) {
                designsBeforSort?.eachWithIndex { Entity d, int i ->
                    batimentIndexMappings.put((d.id.toString()), i + 1)
                }
            }

            RsetMapping rsetMapping = parent.frenchRsetMappings?.find { it.questionId == questionId }

            if (!rsetMapping) {
                rsetMapping = new RsetMapping(questionId: questionId, batimentIndexMappings: batimentIndexMappings, batimentZoneIndexMappings: batimentZoneIndexMappings, designIdForParcelle: designIdForParcelle)
                if (parent.frenchRsetMappings == null) {
                    parent.frenchRsetMappings = []
                }
                parent.frenchRsetMappings.add(rsetMapping)
            } else {
                rsetMapping.batimentIndexMappings = batimentIndexMappings
                rsetMapping.batimentZoneIndexMappings = batimentZoneIndexMappings
                rsetMapping.designIdForParcelle = designIdForParcelle
            }

            parent = parent.merge(flush: true)
        }
        return parent
    }


    /**
     * Get RSET mapping table rendering string
     * @param rsetDto
     * @param parent
     * @return
     */
    String getRsetImportMappingTableRenderString(RsetDto rsetDto, Entity parent, boolean isRseeFormat) {
        String renderString = ''
        // compile the render string for Rset import mapping table on project parameters page.
        List<Entity> designs = (entityService.getChildEntities(parent, Constants.EntityClass.DESIGN.getType()) as List<Entity>)?.sort({ it.name?.toLowerCase() })
        boolean batimentsFound = rsetDto?.batimentCollection?.size() > 0

        if (!batimentsFound) {
            renderString = stringUtilsService.getLocalizedText("batiments.notFound")
        } else if (batimentsFound && parent && designs) {
            String parentId = parent.id.toString()
            RsetMapping userRsetMapping = parent.frenchRsetMappings?.find { it.questionId == rsetDto.questionId }
            Map<String, Integer> batimentIndexMappings = userRsetMapping?.batimentIndexMappings
            List<Integer> allSelectedBatimentIndexes = batimentIndexMappings?.collect { it -> it.value as Integer }

            List<DesignRowRsetTable> designRows = []
            Boolean renderParcelleCheckbox = rsetDto.questionId == FrenchConstants.RE2020_RSET_QUESTIONID
            String indicatorId
            ImportMapper importMapper

            if (renderParcelleCheckbox) {
                indicatorId = FrenchConstants.LCA_RE2020

                if (isRseeFormat) {

                    importMapper = parent.designImportMappers?.find({
                        !it.compatibleIndicators || it.compatibleIndicators?.contains(indicatorId)
                    })

                }
            }

            // each design is a row. Each design has the select option to choose the batiment. Once a batiment is chosen, it finds the zones in that batiment
            for (Entity design in designs) {
                if (!design) {
                    continue
                }
                String designId = design.id.toString()
                DesignRowRsetTable designRow = new DesignRowRsetTable(
                        designId: designId,
                        designName: design.name,
                        isSelectedForParcelle: userRsetMapping ? userRsetMapping.designIdForParcelle == designId : designRows.size() == 0
                )

                // check from the mapping in the parent entity if the batiment has been mapped before.
                Integer selectedBatimentIndex = batimentIndexMappings?.get(designId) as Integer
                for (BatimentDto batiment in rsetDto.batimentCollection) {
                    designRow.rsetBatiments.add(new DesignRowRsetTable.RsetBatiment(
                            index: batiment.index,
                            isSelected: batiment.index == selectedBatimentIndex,
                            isDisabled: batiment.index != selectedBatimentIndex && allSelectedBatimentIndexes?.contains(batiment.index),
                            name: batiment.name
                    ))
                }
                // check from the mapping in the parent entity if the zones has been mapped before. If not, the column Zones is empty
                BatimentDto selectedBatiment = rsetDto.batimentCollection?.find { it.index == selectedBatimentIndex }
                designRow.zonesForRender = getZonesForRender(parent, selectedBatiment, designId, rsetDto.questionId)
                designRows.add(designRow)
            }
            renderString = groovyPageRenderer.render(template: "/query/table/rsetMappingTable", model: [designRows            : designRows,
                                                                                                        parentId              : parentId,
                                                                                                        questionId            : rsetDto.questionId,
                                                                                                        renderParcelleCheckbox: renderParcelleCheckbox,
                                                                                                        isRseeFormat          : isRseeFormat,
                                                                                                        indicatorId           : indicatorId,
                                                                                                        importMapper          : importMapper])
        }

        return renderString
    }


    /**
     * Fetches and returns selected building's datasets
     * @param parentEntityId
     * @param batimentIndex
     * @param batimentName
     * @return
     */
    List<RSEnvDatasetDto> getRe2020RseeFormatDatasets(String parentEntityId, Integer batimentIndex, String batimentName, String rseeZoneMapping) {
        EntityFile re2020EntityFile = re2020Service.getRe2020RsetEntityFile(parentEntityId)
        Re2020RSEnv rsenv
        RsetDto rsetDto
        List<RSEnvDatasetDto> rseeFormatDatasets = []

        if (re2020EntityFile) {
            rsenv = re2020Service.getRsenvFromFile(re2020EntityFile)
            rsetDto = convertRsevnToRsetDto(rsenv, rseeZoneMapping)
        }

        if (rsetDto) {
            // get DTOs
            BatimentDto selectedBatiment = rsetDto.batimentCollection.find { batiment -> batiment.index == batimentIndex && batiment.name?.equalsIgnoreCase(batimentName) }
            selectedBatiment.zoneCollection.each { zone ->
                rseeFormatDatasets.addAll(zone.impactGroups.get("buildingMaterials"))
                rseeFormatDatasets.addAll(zone.impactGroups.get("energyDatasets"))
                rseeFormatDatasets.addAll(zone.impactGroups.get("waterDatasets"))
                rseeFormatDatasets.addAll(zone.impactGroups.get("siteDatasets"))
            }
        }
        return rseeFormatDatasets
    }

    /**
     * Fetches and returns selected building's datasets
     * @param parentEntityId
     * @param batimentIndex
     * @param batimentName
     * @return
     */
    List<RSEnvDatasetDto> getFecRseeFormatDatasets(String parentEntityId, Integer batimentIndex, String batimentName) {
        EntityFile fecEntityFile = fecService.getFecRsetEntityFile(parentEntityId)
        FecRSEnv rsenv
        RsetDto rsetDto
        List<RSEnvDatasetDto> rseeFormatDatasets = []

        if (fecEntityFile) {
            rsenv = fecService.getRsenvFromFile(fecEntityFile)
            rsetDto = convertFecRsevnToRsetDto(rsenv)
        }

        if (rsetDto) {
            // get DTOs
            BatimentDto selectedBatiment = rsetDto.batimentCollection.find { batiment -> batiment.index == batimentIndex && batiment.name?.equalsIgnoreCase(batimentName) }
            selectedBatiment.zoneCollection.each { zone ->
                rseeFormatDatasets.addAll(zone.impactGroups.get("buildingMaterials"))
            }
        }
        return rseeFormatDatasets
    }

    /**
     * Get the render string for zones select dropdown in RSET mapping table
     * @param parentEntityId
     * @param questionIdForFrenchRset
     * @param designId
     * @param batimentIndex
     * @return
     */
    String getZonesForSelectedBatimentRenderString(String parentEntityId, String questionIdForFrenchRset, String designId, Integer batimentIndex) {
        if (parentEntityId && questionIdForFrenchRset && designId) {
            Boolean isFec = questionIdForFrenchRset == FrenchConstants.FEC_RSET_QUESTIONID

            def rset = getProjetDataForFrenchImport(parentEntityId, isFec)

            RsetDto rsetDto

            if (rset) {

                if (rset instanceof Re2020RSEnv) {
                    rsetDto = convertRsevnToRsetDto(rset)
                } else if (rset instanceof FecRSEnv) {
                    rsetDto = convertFecRsevnToRsetDto(rset)
                } else {
                    rsetDto = convertToRsetDto(rset)
                }

                if (rsetDto) {
                    BatimentDto selectedBatiment = rsetDto.batimentCollection?.find({ it.index == batimentIndex })

                    if (selectedBatiment) {
                        Entity parentEntity = Entity.findById(DomainObjectUtil.stringToObjectId(parentEntityId))
                        return groovyPageRenderer.render(template: "/query/inputs/rsetZoneMapping", model: [zonesForRender: getZonesForRender(parentEntity, selectedBatiment, designId, questionIdForFrenchRset), questionId: questionIdForFrenchRset, designId: designId])
                    }
                }
            }
        }
        return ''
    }

    /**
     *
     * @param parent
     * @param selectedBatiment
     * @param designId
     * @param questionId the question of the RSET (determins which tool the RSET is for)
     * @return
     */
    List<ZoneForRender> getZonesForRender(Entity parent, BatimentDto selectedBatiment, String designId, String questionId) {
        List<ZoneForRender> zoneRows = []
        List<String> validZones = resolveZonesForRsetImport(selectedBatiment, designId)
        // parcelle doesn't need to be rendered in the dropdown
        validZones?.remove(FrenchConstants.ZONE_PARCELLE)
        // check from the mapping in the parent entity if the zones has been mapped before.
        Map<String, Integer> zoneIndexMappings = parent?.frenchRsetMappings?.find { it.questionId == questionId }?.batimentZoneIndexMappings?.get(designId)
        Set<Integer> allSelectedBatimentZoneIndices = zoneIndexMappings?.collect { it -> it.value as Integer } as Set<Integer>

        validZones?.sort { it.toInteger() }?.each { String zoneId ->
            ZoneForRender row = new ZoneForRender(zoneId: zoneId, batimentIndexAndDesignId: "${selectedBatiment.index}.${designId}")
            Integer selectedBatimentZoneIndex = zoneIndexMappings?.get(zoneId)
            selectedBatiment.zoneCollection?.each { ZoneDto batimentZone ->
                row.rsetZones.add(new ZoneForRender.RsetZone(
                        index: batimentZone.index,
                        name: batimentZone.name,
                        isSelected: batimentZone.index?.equals(selectedBatimentZoneIndex),
                        isDisabled: batimentZone.index != selectedBatimentZoneIndex && allSelectedBatimentZoneIndices?.contains(batimentZone.index),
                        dataValue: batimentZone.index?.toString(),
                        value: "${zoneId}.${batimentZone.index}"
                ))
            }
            zoneRows.add(row)
        }

        return zoneRows
    }

    // Get list of zones in the batiment and design, if the batiment has more zones than design, it will fill the missing zones into the list (without actually created any zone in design)
    List<String> resolveZonesForRsetImport(BatimentDto batiment, String designId) {
        List<String> validZones = []
        if (batiment && designId) {
            try {
                Entity design = entityService.readEntity(designId)
                validZones = datasetService.getValidFECZones(design?.datasets, null, Boolean.TRUE, Boolean.TRUE)

                if (!validZones) {
                    validZones = ["1"]
                }

                List<ZoneDto> batimentZones = batiment.zoneCollection
                // Fill the missing zones if batiments have more zones than design.
                if (batimentZones && batimentZones.size() > validZones.size()) {
                    int sizeDiff = batimentZones.size() - validZones.size()

                    if (sizeDiff > 0) {
                        List<String> newZones = Constants.ALLOWED_BUILDING_ZONES?.findAll({ !validZones.contains(it) })?.subList(0, sizeDiff)
                        if (newZones) {
                            validZones += newZones
                        }
                    }
                }

            } catch (e) {
                loggerUtil.warn(log, "Error in resolveZonesForRsetImport, designId ${designId}:", e)
                flashService.setErrorAlert("Error in resolveZonesForRsetImport, designId ${designId}: ${e.message}", true)
            }
        }
        return validZones
    }

    /**
     * Read some info from RSET file and generate datasets
     * We allow users to upload 2 RSET files (one for FEC and another for RE2020)
     * Need to read both and generate datasets
     * @param parent
     * @param session
     */
    void injectAnswerFromRset(Entity parent, HttpSession session) {
        if (parent) {
            String rsetMessage = ""
            // readDatasets is a Map < designId : List<Dataset> >, it will have a key "projectLevel" with value List<Dataset> for the project.
            // it contains all datasets generated from the RSET capture.
            Map<String, List<Dataset>> readDatasets = new HashMap<String, List<Dataset>>()
            List<String> rsetMessagesAsList = []
            Indicator indicatorForCalc

            if (fecService.isFecToolActivated(parent)) {
                EntityFile fecEntityFile = fecService.getFecRsetEntityFile(parent.id?.toString())

                if (fecEntityFile) {
                    RSET fecRset = fecService.getRsetFromEntityFile(fecEntityFile)

                    if (fecRset) {
                        String selectedFecIndicatorId = FrenchConstants.FEC_TOOLS.find { parent.indicatorIds?.contains(it) } ?: FrenchConstants.FEC_TOOLS.first()
                        Indicator fecInd = indicatorService.getIndicatorByIndicatorId(selectedFecIndicatorId)

                        if (fecInd?.readValuesFromXML) {
                            readRSETToDatasets(fecRset, fecInd.readValuesFromXML, parent, readDatasets, rsetMessagesAsList, FrenchConstants.FEC_RSET_QUESTIONID)
                            indicatorForCalc = fecInd
                        }
                    }
                }
            }

            if (re2020Service.isRe2020ToolActivated(parent)) {
                EntityFile re2020EntityFile = re2020Service.getRe2020RsetEntityFile(parent.id?.toString())

                if (re2020EntityFile) {
                    com.bionova.optimi.xml.re2020RSEnv.RSET re2020Rset = re2020Service.getRsetFromFile(re2020EntityFile)

                    if (re2020Rset) {

                        String selectedRe2020IndicatorId = FrenchConstants.RE2020_TOOLS.find { parent.indicatorIds?.contains(it) } ?: FrenchConstants.RE2020_TOOLS.first()
                        Indicator re2020Ind = indicatorService.getIndicatorByIndicatorId(selectedRe2020IndicatorId)

                        if (re2020Ind?.readValuesFromXML) {
                            readRSETToDatasets(re2020Rset, re2020Ind.readValuesFromXML, parent, readDatasets, rsetMessagesAsList, FrenchConstants.RE2020_RSET_QUESTIONID)
                            // we prioritize re2020 tool (as it's newer) so selecting it for calculation
                            indicatorForCalc = re2020Ind
                        }
                    }
                }
            }

            // datasets extracted from RSET, now save and calculate
            if (readDatasets && indicatorForCalc) {
                List<Dataset> projectLevelDatasets = readDatasets.get(Constants.PROJECT_LEVEL)
                if (projectLevelDatasets) {
                    parent = datasetService.saveDatasets(parent, projectLevelDatasets,
                            FrenchConstants.FEC_QUERYID_PROJECT_LEVEL, indicatorForCalc.indicatorId, true, session,
                            true, false, false, null, true, null,
                            null, true, false)
                    parent = entityService.calculateEntity(parent, indicatorForCalc.indicatorId, FrenchConstants.FEC_QUERYID_PROJECT_LEVEL, false)
                }

                Map<String, List<Dataset>> designLevelDatasetMap = readDatasets.findAll { it.key != Constants.PROJECT_LEVEL }
                if (designLevelDatasetMap) {
                    List<String> targetQueryIds = readDatasets.collect({ it.value })?.findAll({ it.queryId })?.collect({ it.queryId })?.flatten()?.unique() as List<String>
                    List<Indicator> indicators = indicatorService.getIndicatorsWithSameQueries(parent, targetQueryIds)

                    designLevelDatasetMap?.each { String designId, List<Dataset> designDatasets ->
                        Entity design = parent.childrenByChildEntities?.find({ it.id.toString() == designId })
                        if (design && !design.deleted && !design.locked && !design.isMaterialSpecifierEntity) {
                            design = datasetService.preHandleDatasets(design, designDatasets, indicators, Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, Boolean.TRUE)

                            indicators?.each { Indicator ind ->
                                design = newCalculationServiceProxy.calculate(null, ind.indicatorId, parent, design)
                            }
                            design.merge(flush: true)
                        }
                    }
                }
            }
            if (rsetMessagesAsList) {
                rsetMessage += rsetMessagesAsList.join("<br/>")
            }
            session?.setAttribute(SessionAttribute.RSET_MESSAGE.attribute, rsetMessage)
        }
    }

    /**
     * Gets the indicator id for French tools
     * @param parent
     * @return
     */
    String getRseeIndicatorId(Entity parent) {
        if (fecService.isFecToolActivated(parent)) {
            return FrenchConstants.FEC_TOOLS.find { parent.indicatorIds?.contains(it) } ?: FrenchConstants.FEC_TOOLS.first()
        }

        if (re2020Service.isRe2020ToolActivated(parent)) {
            return FrenchConstants.RE2020_TOOLS.find { parent.indicatorIds?.contains(it) } ?: FrenchConstants.RE2020_TOOLS.first()
        }

        return null
    }

    void readRSETToDatasets(def rset, List<IndicatorXmlMapper> readValuesFromXML, Entity parentEntity,
                            Map<String, List<Dataset>> readDatasets, List<String> rsetMessagesAsList, String questionId) {
        if (readDatasets == null) {
            readDatasets = [:]
        }
        if (rsetMessagesAsList == null) {
            rsetMessagesAsList = []
        }

        Map<String, Integer> batimentIndexMappings = parentEntity?.frenchRsetMappings?.find { it.questionId == questionId }?.batimentIndexMappings
        Map<String, Map<String, Integer>> zoneIndexMappings = parentEntity?.frenchRsetMappings?.find { it.questionId == questionId }?.batimentZoneIndexMappings

        // Sw will not read Rset if there's no rsetDto and rsetDto mapping
        if (rset && parentEntity && readValuesFromXML && batimentIndexMappings && zoneIndexMappings) {

            for (IndicatorXmlMapper indicatorXmlMapper in readValuesFromXML) {
                if (indicatorXmlMapper.target && indicatorXmlMapper.source) {
                    boolean sumMultiSources = indicatorXmlMapper.source.size() > 1

                    indicatorXmlMapper.source.each { String xmlMapperSource ->
                        if (xmlMapperSource) {
                            ValueReference targetDataset = indicatorXmlMapper.target
                            List<String> xmlElementsOrAttributes = xmlMapperSource.tokenize('.')
                            if (xmlElementsOrAttributes) {
                                int index = 0
                                def xmlObject = null

                                boolean batimentSpecificQuery = CollectionUtils.containsAny(xmlElementsOrAttributes, Constants.RSET_BATIMENT)
                                boolean zoneSpecificQuery = CollectionUtils.containsAny(xmlElementsOrAttributes, Constants.RSET_ZONE)

                                // NOTE: the xmlObject changes after the loop
                                for (String xmlElementOrAttribute in xmlElementsOrAttributes) {
                                    if (index == 0) {
                                        xmlObject = DomainObjectUtil.callGetterByAttributeName(xmlElementOrAttribute, rset)
                                    } else {
                                        xmlObject = expandXmlObject(xmlObject, xmlElementOrAttribute)
                                        // if the source is per design, have to break the loop at batiments level, enter different loop for each batiment
                                        if (Constants.RSET_BATIMENT.contains(xmlElementOrAttribute)) {
                                            break
                                        }
                                    }
                                    index++
                                }

                                // check if the final data has been captured and if there are multiple data captured, get only one
                                if (indicatorXmlMapper.findOnlyOne && xmlObject instanceof List) {
                                    xmlObject = resolveXmlObjectWithFindOnlyOneCondition(xmlObject, index, xmlElementsOrAttributes)
                                }

                                // enter another loop per batiment, if we're capturing data in batiment level
                                if (batimentSpecificQuery && xmlObject && xmlObject instanceof List) {
                                    List<Integer> targetBatimentIndices = batimentIndexMappings?.collect { it.value } as List<Integer>

                                    // at this point, the xmlObject is a list of batiments. Filter only the mapped batiments
                                    def targetBatimentObjects = xmlObject.findAll({ targetBatimentIndices?.contains(it.index as Integer) })

                                    // loop separately in each batiment, continue looping where we left off
                                    targetBatimentObjects?.each { batimentXmlObject ->
                                        if (batimentXmlObject) {
                                            String mappedDesignId = batimentIndexMappings.find({ it.value == batimentXmlObject.index }).key
                                            def batimentObjectFromRset = rset.entreeProjet?.batimentCollection?.batiment?.find({ it.index == batimentXmlObject.index })
                                            String batimentName = batimentObjectFromRset?.name

                                            // This is a fast hack for the patch, should make this configurable next time if we need more condition like this
                                            if (indicatorXmlMapper.PV != null) {
                                                List<String> xmlAttributeToTypeProdElec = ['sortieProdElecCCollection', 'sortieProdElecC', 'oTypeProdElec']
                                                def batimentXmlObjectCopy = batimentXmlObject
                                                for (String xmlAttribute in xmlAttributeToTypeProdElec) {
                                                    batimentXmlObjectCopy = expandXmlObject(batimentXmlObjectCopy, xmlAttribute)
                                                }
                                                if (batimentXmlObjectCopy && batimentXmlObjectCopy instanceof List) {
                                                    batimentXmlObjectCopy = batimentXmlObjectCopy[0]
                                                }

                                                // we should get the value TypeProdElect at this point
                                                if (indicatorXmlMapper.PV && batimentXmlObjectCopy != 'PV') {
                                                    // skip this batiment and continue the batiment loop if the condition 'PV' is true but value is not 'PV'
                                                    return
                                                } else if (!indicatorXmlMapper.PV && batimentXmlObjectCopy == 'PV') {
                                                    // skip this batiment and continue the batiment loop if the condition 'PV' is false but value is 'PV'
                                                    return
                                                }
                                            }

                                            // NOTE: the batimentXmlObject changes after the loop
                                            int secondIndex = 0
                                            for (String xmlElementOrAttribute in xmlElementsOrAttributes) {
                                                if (secondIndex > index) {
                                                    batimentXmlObject = expandXmlObject(batimentXmlObject, xmlElementOrAttribute)
                                                    // if the source is per zone, have to break the loop at zone level, enter different loop for each zone
                                                    if (Constants.RSET_ZONE.contains(xmlElementOrAttribute)) {
                                                        break
                                                    }
                                                }
                                                secondIndex++
                                            }

                                            // check if the final data has been captured and if there are multiple data captured, get only one
                                            if (indicatorXmlMapper.findOnlyOne && batimentXmlObject instanceof List) {
                                                batimentXmlObject = resolveXmlObjectWithFindOnlyOneCondition(batimentXmlObject, secondIndex, xmlElementsOrAttributes)
                                            }

                                            // enter another loop per zone , if we're capturing data in zone level
                                            if (zoneSpecificQuery && batimentXmlObject && batimentXmlObject instanceof List) {
                                                List<Integer> targetZoneIndices = zoneIndexMappings.get(mappedDesignId)?.collect({ it.value })

                                                // at this point, the batimentXmlObject is a list of zone. Filter only the mapped zones
                                                def targetZoneObjects = batimentXmlObject.findAll({ targetZoneIndices?.contains(it.index as Integer) })

                                                // loop separately in each zone, continue looping where we left off
                                                targetZoneObjects?.each { zoneXmlObject ->
                                                    if (zoneXmlObject) {
                                                        def zoneObjectFromRset = batimentObjectFromRset?.zoneCollection?.zone?.find({ it.index == zoneXmlObject.index })

                                                        // Get zone usage for the sdp comment and check residential
                                                        String zoneUsage = zoneObjectFromRset?.usage
                                                        Boolean residential = residentialZones.contains(zoneUsage)

                                                        if ((residential && indicatorXmlMapper.residential) || (!residential && indicatorXmlMapper.residential == false) || indicatorXmlMapper.residential == null) {
                                                            // get zone name in batiment
                                                            String batimentZoneName = zoneObjectFromRset?.name

                                                            // find zoneId of design in rsetMappings
                                                            Map<String, Integer> zoneMappingForThisBatiment = zoneIndexMappings.get(mappedDesignId)
                                                            String designZoneId = zoneMappingForThisBatiment?.find({ it.value == zoneXmlObject.index })?.key

                                                            // NOTE: the zoneXmlObject changes after the loop
                                                            int thirdIndex = 0
                                                            for (String xmlElementOrAttribute in xmlElementsOrAttributes) {
                                                                if (thirdIndex > secondIndex) {
                                                                    zoneXmlObject = expandXmlObject(zoneXmlObject, xmlElementOrAttribute)
                                                                }
                                                                thirdIndex++
                                                            }

                                                            // check if the final data has been captured and if there are multiple data captured, get only one
                                                            if (indicatorXmlMapper.findOnlyOne && zoneXmlObject instanceof List) {
                                                                zoneXmlObject = resolveXmlObjectWithFindOnlyOneCondition(zoneXmlObject, thirdIndex, xmlElementsOrAttributes)
                                                            }

                                                            // capture data from XML per zone, the readXmlValueFor is still the designId
                                                            targetDataset.readXmlValueFor = mappedDesignId
                                                            handleXmlObjectToAnswer(targetDataset, rsetMessagesAsList, readDatasets, zoneXmlObject, indicatorXmlMapper, xmlMapperSource, sumMultiSources, batimentName, batimentZoneName, designZoneId, zoneUsage)
                                                        }
                                                    }
                                                }
                                            } else {
                                                // capture data from XML per design
                                                targetDataset.readXmlValueFor = mappedDesignId
                                                handleXmlObjectToAnswer(targetDataset, rsetMessagesAsList, readDatasets, batimentXmlObject, indicatorXmlMapper, xmlMapperSource, sumMultiSources, batimentName, null, null, null)
                                            }
                                        }
                                    }
                                } else {
                                    // capture data from XML for project
                                    targetDataset.readXmlValueFor = Constants.PROJECT_LEVEL
                                    handleXmlObjectToAnswer(targetDataset, rsetMessagesAsList, readDatasets, xmlObject, indicatorXmlMapper, xmlMapperSource, sumMultiSources, null, null, null, null)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private def expandXmlObject(def xmlObject, String xmlElementOrAttribute) {
        try {
            if (xmlObject != null && xmlElementOrAttribute) {
                if (xmlObject instanceof List) {
                    List objects = []
                    xmlObject.each {
                        def nestedObject = DomainObjectUtil.callGetterByAttributeName(xmlElementOrAttribute, it)

                        if (nestedObject != null) {
                            objects.add(nestedObject)
                        }
                    }
                    xmlObject = objects.flatten()
                } else {
                    xmlObject = DomainObjectUtil.callGetterByAttributeName(xmlElementOrAttribute, xmlObject)
                }
            }
        } catch (e) {
            loggerUtil.warn(log, "Error in expandXmlObject, xmlElementOrAttribute ${xmlElementOrAttribute}:", e)
            flashService.setErrorAlert("Error in expandXmlObject, xmlElementOrAttribute ${xmlElementOrAttribute}: ${e.message}", true)
        }

        return xmlObject
    }

    def resolveXmlObjectWithFindOnlyOneCondition(def xmlObject, Integer currentIndex, List xmlElementsOrAttributes) {
        try {
            if (xmlObject instanceof List && currentIndex != null && xmlElementsOrAttributes && currentIndex == xmlElementsOrAttributes.size()) {
                // get the first valid xmlObject if the final data has been captured and there are multiple of them
                xmlObject = xmlObject.find({ it != null }).toString()
            }
        } catch (e) {
            loggerUtil.warn(log, "Error in resolveFindOnlyOneCondition, xmlElementsOrAttributes ${xmlElementsOrAttributes}:", e)
            flashService.setErrorAlert("Error in resolveFindOnlyOneCondition, xmlElementsOrAttributes ${xmlElementsOrAttributes}: ${e.message}", true)
        }

        return xmlObject
    }

    private void handleXmlObjectToAnswer(ValueReference targetDataset, List<String> rsetMessagesAsList, Map<String, List<Dataset>> readDatasets, def xmlObject, IndicatorXmlMapper indicatorXmlMapper, String xmlMapperSource, Boolean sumMultiSources, String batimentName, String batimentZoneName, String designZoneId, String zoneUsage) {
        try {
            if (xmlObject != null) {
                String answer = ""

                if (xmlObject instanceof List) {
                    if (!xmlObject.isEmpty()) {
                        if (indicatorXmlMapper.sumAsOneIfMultiple) {
                            if (xmlObject.size() > 1) {
                                try {
                                    answer = xmlObject.sum()
                                } catch (Exception e) {
                                    // Values are not numeric, tostring the answers
                                    int size = xmlObject.size()
                                    int i = 1
                                    xmlObject.each {
                                        answer = "${answer}${it}"
                                        if (size != i) {
                                            answer = "${answer}, "
                                        }
                                        i++
                                    }
                                    answer = answer.trim()
                                }
                            } else {
                                answer = xmlObject.first()
                            }
                            handleAnswerToDataset(targetDataset, rsetMessagesAsList, readDatasets, answer, indicatorXmlMapper, xmlMapperSource, sumMultiSources, batimentName, batimentZoneName, designZoneId, zoneUsage)
                        } else {
                            int i = 0
                            xmlObject.each { xml ->
                                answer = xml
                                handleAnswerToDataset(targetDataset, rsetMessagesAsList, readDatasets, answer, indicatorXmlMapper, xmlMapperSource, sumMultiSources, batimentName, batimentZoneName, designZoneId, zoneUsage)
                                i++
                            }
                        }
                    }
                } else {
                    if (xmlObject instanceof String) {
                        answer = xmlObject
                    } else {
                        answer = '' + xmlObject
                    }
                    handleAnswerToDataset(targetDataset, rsetMessagesAsList, readDatasets, answer, indicatorXmlMapper, xmlMapperSource, sumMultiSources, batimentName, batimentZoneName, designZoneId, zoneUsage)
                }
            }
        } catch (e) {
            loggerUtil.warn(log, "Error in handleXmlObjectToAnswer, target ${targetDataset}, xmlObject ${xmlObject}:", e)
            flashService.setErrorAlert("Error in handleXmlObjectToAnswer, target ${targetDataset}, xmlObject ${xmlObject}: ${e.message}", true)
        }
    }

    private void handleAnswerToDataset(ValueReference targetDataset, List<String> rsetMessagesAsList, Map<String, List<Dataset>> readDatasets, String answer, IndicatorXmlMapper indicatorXmlMapper, String xmlMapperSource, Boolean sumMultiSources, String batimentName, String batimentZoneName, String designZoneId, String zoneUsage) {
        ValidationTagLib g = grailsApplication.mainContext.getBean('org.grails.plugins.web.taglib.ValidationTagLib')
        try {
            boolean notNullAnswer = answer != null && answer != 'null'
            boolean notZeroAnswerForMainAnswer = targetDataset.additionalQuestionId || (!targetDataset.additionalQuestionId && !['0.0', '0'].contains(answer))
            // it's okay to have 0.0 in additional answer

            if (notNullAnswer && notZeroAnswerForMainAnswer && targetDataset && targetDataset.readXmlValueFor && targetDataset.questionId && targetDataset.sectionId && targetDataset.queryId) {

                String messageForThisDataset = ''

                Question rsetQuestion = questionService.getQuestion(targetDataset.queryId, targetDataset.questionId)
                Question rsetAdditionalQuestion = questionService.getQuestion(Constants.ADDITIONAL_QUESTIONS_QUERY_ID, targetDataset.additionalQuestionId)

                if (rsetQuestion) {
                    if (batimentZoneName) {
                        batimentZoneName = batimentZoneName.replace(batimentName, '')
                    }
                    messageForThisDataset = "${batimentName ?: ''} - ${batimentZoneName ?: ''} - ${rsetQuestion.localizedQuestion ?: ''}${rsetAdditionalQuestion?.localizedQuestion ? ' - ' + rsetAdditionalQuestion?.localizedQuestion : ''}: ${answer}"

                    if (targetDataset.resourceId) {
                        Resource resource = Resource.findByResourceId(targetDataset.resourceId)
                        messageForThisDataset += " / ${optimiResourceService.getLocalizedName(resource) ?: ''}${resource?.unitForData ? ' - ' + resource?.unitForData : ''}"
                    }
                }

                if (messageForThisDataset) {
                    rsetMessagesAsList.add(messageForThisDataset)
                }

                if (indicatorXmlMapper?.quantityMultiplier) {
                    try {
                        Double doubleAnswer = DomainObjectUtil.convertStringToDouble(answer)

                        if (doubleAnswer) {
                            answer = doubleAnswer * indicatorXmlMapper?.quantityMultiplier
                        }
                    } catch (e) {
                        loggerUtil.warn(log, "Error in handleAnswerToDataset while casting answer to double with quantityMultiplier. Answer (${answer}) or the quantityMultiplier (${indicatorXmlMapper?.quantityMultiplier}) is not a number, target ${targetDataset}:", e)
                        flashService.setErrorAlert("Error in handleAnswerToDataset while casting answer to double with quantityMultiplier. Answer (${answer}) or the quantityMultiplier (${indicatorXmlMapper?.quantityMultiplier}) is not a number, target ${targetDataset}: ${e.message}", true)
                    }
                }

                // Clean answer
                answer = answer.replaceAll('\\t', '')
                answer = answer.replaceAll('\\n', '')

                // convert zone usage from RSET to addQ answer value in our sw. This answer is captured from the value defined in the config. It's different than the zoneUsage parameter. (Although value should be the same, which doesn't make any senses, I know!)
                if (targetDataset.additionalQuestionId && FrenchConstants.PROJECT_DESCRIPTION_FEC_QUERYID == targetDataset.queryId && FrenchConstants.DESCRIPTION_PER_ZONE_SECTIONID == targetDataset.sectionId && FrenchConstants.SDP_QUESTIONID == targetDataset.questionId && Constants.FEC_ZONE_USAGE_ADDQ_ID == targetDataset.additionalQuestionId) {
                    answer = Constants.FEC_ZONE_USAGE_MAP.get(answer) ?: answer
                }

                Dataset dataset = new Dataset()
                boolean createNewDataset = true
                List<Dataset> foundDatasets = []
                Integer foundDatasetIndex

                if (readDatasets) {
                    // find if there has been datasets for the project or this design
                    foundDatasets = readDatasets.get(targetDataset.readXmlValueFor) ?: []

                    // Now find if there's already a dataset for this question. This is to populate multiple additional answer to one dataset.
                    if (foundDatasets && targetDataset.additionalQuestionId) {
                        if (designZoneId) {
                            // check also the zone if answer is in zone level
                            foundDatasetIndex = foundDatasets.findIndexOf {
                                targetDataset.queryId == it.queryId && targetDataset.sectionId == it.sectionId && targetDataset.questionId == it.questionId && it.additionalQuestionAnswers?.get(FrenchConstants.BUILDING_ZONES_FEC_QUESTIONID) == designZoneId
                            }
                        } else {
                            foundDatasetIndex = foundDatasets.findIndexOf {
                                targetDataset.queryId == it.queryId && targetDataset.sectionId == it.sectionId && targetDataset.questionId == it.questionId
                            }
                        }

                        if (foundDatasetIndex != -1) {
                            dataset = foundDatasets[foundDatasetIndex]
                            createNewDataset = false
                        }
                    }
                }

                if (createNewDataset) {
                    dataset.manualId = new ObjectId().toString()
                    dataset.queryId = targetDataset.queryId
                    dataset.sectionId = targetDataset.sectionId
                    dataset.questionId = targetDataset.questionId
                    dataset.resourceId = targetDataset.resourceId
                    dataset.generatedFromImport = true

                    // Add zone to dataset
                    if (designZoneId) {
                        if (!dataset.additionalQuestionAnswers) {
                            dataset.additionalQuestionAnswers = [:]
                        }
                        dataset.additionalQuestionAnswers.put(FrenchConstants.BUILDING_ZONES_FEC_QUESTIONID, designZoneId)
                    }

                    if (targetDataset.profileId) {
                        dataset.profileId = targetDataset.profileId
                        if (!dataset.additionalQuestionAnswers) {
                            dataset.additionalQuestionAnswers = [:]
                        }
                        dataset.additionalQuestionAnswers.put('profileId', targetDataset.profileId)
                    }

                    if (targetDataset.additionalQuestionId) {
                        if (!dataset.additionalQuestionAnswers) {
                            dataset.additionalQuestionAnswers = [:]
                        }
                        dataset.additionalQuestionAnswers.put(targetDataset.additionalQuestionId, answer)
                    } else {
                        dataset.answerIds = [answer]
                    }
                } else if (targetDataset.additionalQuestionId) {
                    // add more additional question answer to the existing dataset
                    if (!dataset.additionalQuestionAnswers) {
                        dataset.additionalQuestionAnswers = [:]
                    }

                    // sum multiple value if there are multiple sources for one target (dataset)
                    if (sumMultiSources) {
                        Double total = sumMultipleSourcesAsDouble(answer, dataset.additionalQuestionAnswers.get(targetDataset.additionalQuestionId) as String)
                        answer = total != 0 ? total.toString() : answer
                    }

                    dataset.additionalQuestionAnswers.put(targetDataset.additionalQuestionId, answer)
                } else if (dataset.answerIds) {
                    // sum multiple value if there are multiple sources for one target (dataset)
                    if (sumMultiSources) {
                        Double total = sumMultipleSourcesAsDouble(answer, dataset.answerIds.get(0) as String)
                        answer = total != 0 ? total.toString() : answer
                        dataset.answerIds = [answer]
                    }
                }

                /*
                 add comment to dataset for energy query or the 'sdp' question

                 Request from Lorelia:
                     batiment name - zone name - energy comment // for energy query
                     batiment name - zone name - zone usage (Usage #) // for buildingDescription query "queryId" : "projectDescriptionFEC", "sectionId" : "descriptionPerZone", "questionId" : "sdp"
                */
                String fullComment = batimentName ? batimentName + ': ' : ''
                String extraComment = targetDataset.comment ? g.message(code: "${targetDataset.comment}", dynamic: true) : ''
                String batimentZoneInfo = batimentZoneName ? batimentZoneName + ' - ' : ''
                String zoneUsageInfo = zoneUsage ? g.message(code: "xml.fec.zoneUsage") + ' ' + zoneUsage : ''

                if (FrenchConstants.PROJECT_DESCRIPTION_FEC_QUERYID == targetDataset.queryId && FrenchConstants.DESCRIPTION_PER_ZONE_SECTIONID == targetDataset.sectionId && FrenchConstants.SDP_QUESTIONID == targetDataset.questionId) {
                    fullComment += batimentZoneInfo + zoneUsageInfo
                } else {
                    fullComment += batimentZoneInfo + extraComment + '; '
                }

                if (!dataset.additionalQuestionAnswers) {
                    dataset.additionalQuestionAnswers = [:]
                }

                dataset.additionalQuestionAnswers.put('comment', fullComment)

                if (dataset) {
                    if (!createNewDataset && foundDatasetIndex != null) {
                        foundDatasets[foundDatasetIndex] = dataset
                    } else {
                        foundDatasets.add(dataset)
                    }
                    readDatasets.put(targetDataset.readXmlValueFor, foundDatasets)
                }
            }
        } catch (e) {
            loggerUtil.warn(log, "Error in handleAnswerToDataset, target ${targetDataset}:", e)
            flashService.setErrorAlert("Error in handleAnswerToDataset, target ${targetDataset}: ${e.message}", true)
        }
    }

    private Double sumMultipleSourcesAsDouble(String thisAnswer, String previousAnswer) {
        Double doubleAnswer = 0

        if (thisAnswer && thisAnswer.isNumber()) {
            doubleAnswer = thisAnswer?.replace(',', '.')?.toDouble()
        }

        // Sum with previous answer if this is the second source or later
        if (previousAnswer && previousAnswer.isNumber()) {
            doubleAnswer += DomainObjectUtil.convertStringToDouble(previousAnswer)
        }

        return doubleAnswer
    }

    // check if all batiments and zones have been mapped
    Boolean checkReadinessForRseeExport(RsetDto rsetDto, Map<String, Integer> batimentIndexMappings, Map<String, Map<String, Integer>> batimentZoneIndexMappings) {
        Boolean ready = false
        if (rsetDto && batimentZoneIndexMappings && batimentIndexMappings) {
            Set<Integer> mappedBatiments = batimentIndexMappings?.collect { it.value } as Set<Integer>
            Set<Integer> allBatiments = rsetDto.batimentCollection?.collect { it.index } as Set<Integer>

            if (!allBatiments.containsAll(mappedBatiments)) {
                // All batiments must be mapped
                return false
            }

            Boolean zonesReady = true
            if (rsetDto.batimentCollection) {
                for (BatimentDto batiment in rsetDto.batimentCollection) {
                    String mappedDesignId = batimentIndexMappings?.find { it.value == batiment?.index }?.key
                    if (mappedDesignId) {
                        Set<Integer> mappedZones = batimentZoneIndexMappings.get(mappedDesignId)?.collect { it.value } as Set<Integer>
                        Set<Integer> allZones = batiment?.zoneCollection?.collect { it.index } as Set<Integer>

                        if (!allZones?.containsAll(mappedZones)) {
                            // All zones in each batiment must be mapped
                            zonesReady = false
                            break
                        }
                    } else {
                        zonesReady = false
                        break
                    }
                }
            }

            // After all the checks, it's ready
            if (zonesReady) {
                ready = true
            }
        }
        return ready
    }

    @CompileStatic
    XMLGregorianCalendar answerToXMLGregorianCalendar(String answer, DateFormat dateFormat, DatatypeFactory xmlGregorianCalendarInstance, Dataset dataset) {
        try {
            Date date = dateFormat?.parse(answer)
            return date ? xmlGregorianCalendarInstance?.newXMLGregorianCalendar(dateFormat.format(date)) : null
        } catch (Exception e) {
            loggerUtil.warn(log, "answerToXMLGregorianCalendar Exception while handling user given input", e)
            flashService.setErrorAlert("answerToXMLGregorianCalendar Exception while handling user given input ( ${dataset?.queryId} / ${dataset?.sectionId} / ${dataset?.questionId}: ${answer} ): ${e.getMessage()}", true)
            return null
        }
    }

    XMLGregorianCalendar getXMLGregorianCalendarValueFromDataset(Entity entity, String queryId, String sectionId, String questionId) {
        List<Dataset> datasets
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd")
        XMLGregorianCalendar value

        if (entity && queryId && sectionId && questionId) {
            datasets = datasetService.getDatasetsByEntityQueryIdSectionIdAndQuestion(entity, queryId, sectionId, questionId)
        }

        if (!datasets && entity.parentEntityId) {
            Entity parent = entity.getParentById()
            datasets = datasetService.getDatasetsByEntityQueryIdSectionIdAndQuestion(parent, queryId, sectionId, questionId)
        }

        if (datasets && !datasets.isEmpty()) {
            Dataset dataset = datasets.find({
                it.queryId == queryId && it.sectionId == sectionId && it.questionId == questionId
            })
            String answerId = dataset.answerIds.get(0)

            try {
                Date date = dateFormat.parse(answerId)
                value = DatatypeFactory.newInstance().newXMLGregorianCalendar(dateFormat.format(date))
            } catch (Exception e) {
                loggerUtil.warn(log, "Exception while handling user given input", e)
                flashService.setErrorAlert("Exception while handling user given input: ${e.getMessage()} ", Boolean.TRUE)
                return value
            }

        }
        return value
    }

    /**
     * Get map with key is questionId and value is render string
     * A project can have both FEC and RE2020 tools hence need mapping tables for both of them, depending on which tools are enabled
     * @param queryId
     * @param parentEntity
     * @param session
     * @return
     */
    Map<String, String> getRsetTableMappingRender(String queryId, Entity parentEntity, HttpSession session) {
        if (FrenchConstants.FEC_QUERYID_PROJECT_LEVEL != queryId) {
            return null
        }

        Map<String, String> render = new HashMap()
        Boolean isFecToolEnabled = fecService.isFecToolActivated(parentEntity)
        Boolean isRe2020ToolEnabled = re2020Service.isRe2020ToolActivated(parentEntity)

        if (isFecToolEnabled) {
            EntityFile fecEntityFile = fecService.getFecRsetEntityFile(parentEntity?.id?.toString())
            readRsetInfo(parentEntity, session, fecEntityFile, render)
        }

        if (isRe2020ToolEnabled) {
            EntityFile re2020EntityFile = re2020Service.getRe2020RsetEntityFile(parentEntity?.id?.toString())
            readRsetInfo(parentEntity, session, re2020EntityFile, render)
        }

        return render
    }

    /**
     * Checks if file contains RSEnv data
     * @param entityFile
     * @return
     */
    boolean isRsenvFormat(Object rseeObject) {

        if (rseeObject) {
            return rseeObject instanceof Re2020RSEnv || rseeObject instanceof FecRSEnv
        }

        return false
    }

    /**
     * We have 2 different classes for RSET, convert to DTO
     * @param rset
     * @return
     */
    RsetDto convertToRsetDto(def rset) {
        if (rset) {
            RsetDto rsetDto = new RsetDto()
            rset.entreeProjet?.batimentCollection?.batiment?.each { batiment ->
                BatimentDto batimentDto = new BatimentDto(index: batiment.index, name: batiment.name)

                batiment.zoneCollection?.zone?.each { zone ->
                    batimentDto.zoneCollection.add(new ZoneDto(index: zone.index, name: zone.name))
                }
                rsetDto.batimentCollection.add(batimentDto)
            }
            return rsetDto
        }
        return null
    }

    /**
     * Converts RE2020 RSEnv to RsetDto
     * @param rsenv
     * @return
     */
    RsetDto convertRsevnToRsetDto(Re2020RSEnv rsenv, String rseeZoneMapping = null) {
        if (rsenv) {

            Map<String, String> rseeZonesAssignmentMap

            if (rseeZoneMapping) {
                rseeZonesAssignmentMap = new LinkedHashMap<String, String>()

                for (String selectedZone : rseeZoneMapping.split(";")) {
                    String[] mapping = selectedZone.split("\\.")
                    rseeZonesAssignmentMap.put(mapping[1], mapping[0])
                }
            }

            RsetDto rsetDto = new RsetDto()

            rsenv.entreeProjet?.batiment?.each { batiment ->
                BatimentDto batimentDto = new BatimentDto(index: batiment.index, name: batiment.nom)

                batiment.zone?.each { zone ->
                    Map<String, List<RSEnvDatasetDto>> impactGroups

                    // Adding building materials
                    List<RSEnvDatasetDto> buildingMaterials = []
                    String selectedZone

                    if (rseeZonesAssignmentMap?.size() > 0) {
                        selectedZone = rseeZonesAssignmentMap.get(zone.index.toString())
                    } else {
                        selectedZone = zone.index
                    }

                    zone.contributeur?.composant?.lot?.each { materialGroup ->
                        materialGroup.sousLot?.each { materialSubgroup ->
                            materialSubgroup.donneesComposant?.each { material ->
                                String unit

                                if (material.uniteUf) {
                                    if (!(unit = FrenchConstants.PLACEHOLDER_UNIT_TYPE.get(material.uniteUf))) {
                                        unit = FrenchConstants.UNITE_UFS.find { it.value == material.uniteUf }?.key ?: material.uniteUf
                                    }
                                }

                                buildingMaterials.add(new RSEnvDatasetDto(ref: materialSubgroup.ref, name: material.nom, quantity: material.quantite, productId: material.idProduit, unit: unit, comment: material.commentaire, zone: selectedZone))
                            }
                        }
                    }

                    // Adding energy datasets
                    List<RSEnvDatasetDto> energyDatasets = []
                    addNonMaterialImpactsFromRsee(energyDatasets, zone.contributeur?.energie, selectedZone, FrenchConstants.ContributeurCategoryRe2020.ENERGY)

                    // Adding water datasets
                    List<RSEnvDatasetDto> waterDatasets = []
                    addNonMaterialImpactsFromRsee(waterDatasets, zone.contributeur?.eau, selectedZone, FrenchConstants.ContributeurCategoryRe2020.WATER)

                    // Adding site datasets
                    List<RSEnvDatasetDto> siteDatasets = []
                    addNonMaterialImpactsFromRsee(siteDatasets, zone.contributeur?.chantier, selectedZone, FrenchConstants.ContributeurCategoryRe2020.SITE)

                    impactGroups = [buildingMaterials: buildingMaterials, energyDatasets: energyDatasets, waterDatasets: waterDatasets, siteDatasets: siteDatasets]

                    batimentDto.zoneCollection.add(new ZoneDto(index: zone.index, name: "Zone ${zone.index}", impactGroups: impactGroups))
                }
                rsetDto.batimentCollection.add(batimentDto)
            }
            return rsetDto
        }
        return null
    }

    /**
     * Extracts datasets from RSEE except for composant category to create a Rsenv DTO
     * Only for RE2020
     * @param impactList
     * @param impactCategory
     * @param zoneIndex
     */
    void addNonMaterialImpactsFromRsee(List<RSEnvDatasetDto> impactList, def impactCategory, String zoneIndex, FrenchConstants.ContributeurCategoryRe2020 contributeurCategory) {
        impactCategory?.sousContributeur?.each {impactGroup ->
            impactGroup.donneesService?.each {dataset ->
                String unit
                if (dataset.uniteUf) {
                    if (!(unit = FrenchConstants.PLACEHOLDER_UNIT_TYPE.get(dataset.uniteUf))) {
                        unit = FrenchConstants.UNITE_UFS.find { it.value == dataset.uniteUf }?.key ?: dataset.uniteUf
                    }
                }

                String impactClass

                if (contributeurCategory == FrenchConstants.ContributeurCategoryRe2020.SITE) {
                    impactClass = FrenchConstants.SousContributeurRef.CHANTIER.getEnum(impactGroup.ref).getValue()
                } else {
                    if (contributeurCategory == FrenchConstants.ContributeurCategoryRe2020.ENERGY && impactGroup.ref == FrenchConstants.EXPORTED_ENERGY_REF) {
                        impactClass = FrenchConstants.ContributeurCategoryRe2020.EXPORTED_ENERGY.toString()
                    } else {
                        impactClass = contributeurCategory.toString()
                    }
                }

                String energyUseCategory = contributeurCategory == FrenchConstants.ContributeurCategoryRe2020.ENERGY && impactGroup.ref != FrenchConstants.EXPORTED_ENERGY_REF ? impactGroup.ref : null

                impactList.add(new RSEnvDatasetDto(name: dataset.nom, quantity: dataset.quantite, productId: dataset.idProduit, unit: unit, comment: dataset.commentaire, zone: zoneIndex, impactClass: impactClass, energyUseCategory: energyUseCategory))
            }
        }
    }


    /**
     * Converts FEC RSEnv to RsetDto
     * @param rsenv
     * @return
     */
    RsetDto convertFecRsevnToRsetDto(FecRSEnv rsenv) {
        if (rsenv) {
            RsetDto rsetDto = new RsetDto()

            rsenv.entreeProjet?.batiment?.each { batiment ->
                BatimentDto batimentDto = new BatimentDto(index: batiment.index, name: batiment.nom)

                batiment.zone?.each { zone ->
                    Map<String, List<RSEnvDatasetDto>> impactGroups
                    List<RSEnvDatasetDto> buildingMaterials = []

                    zone.contributeur?.each { materialGroup ->
                        materialGroup.lot?.each { materialSubgroup ->
                            materialSubgroup.donneesComposant?.each { material ->
                                String unit
                                if (material.uniteUf) {
                                    if (!(unit = FrenchConstants.PLACEHOLDER_UNIT_TYPE.get(material.uniteUf))) {
                                        unit = FrenchConstants.UNITE_UFS.find { it.value == material.uniteUf }?.key ?: material.uniteUf
                                    }
                                }
                                buildingMaterials.add(new RSEnvDatasetDto(ref: materialSubgroup.ref, name: material.nom, quantity: material.quantite, productId: material.idFiche, unit: unit, comment: material.commentaire, zone: zone.index))
                            }
                        }
                    }

                    impactGroups = [buildingMaterials: buildingMaterials]

                    batimentDto.zoneCollection.add(new ZoneDto(index: zone.index, name: "Zone ${zone.index}", impactGroups: impactGroups))
                }
                rsetDto.batimentCollection.add(batimentDto)
            }
            return rsetDto
        }
        return null
    }

    /**
     * Gets RSEnv if present, otherwise gets RSET class from the entity file
     * @param entityFile
     * @return
     */
    def getRsenvOrRsetFromEntityFile(EntityFile entityFile) {
        if (!entityFile) {
            return null
        }

        def dataFromEntityFile

        if (entityFile.questionId == FrenchConstants.FEC_RSET_QUESTIONID) {
            dataFromEntityFile = fecService.getRsenvFromFile(entityFile)

            if (!dataFromEntityFile?.entreeProjet) {
                dataFromEntityFile = fecService.getRsetFromEntityFile(entityFile)
            }
        } else if (entityFile.questionId == FrenchConstants.RE2020_RSET_QUESTIONID) {
            dataFromEntityFile = re2020Service.getRsenvFromFile(entityFile)

            if (!dataFromEntityFile) {
                dataFromEntityFile = re2020Service.getRsetFromFile(entityFile)
            }
        }
        return dataFromEntityFile
    }

    /**
     * Get the RSET class from the entity file
     * @param entityFile
     * @return
     */
    def getRsetFromEntityFile(EntityFile entityFile) {
        if (!entityFile) {
            return null
        }

        if (entityFile.questionId == FrenchConstants.FEC_RSET_QUESTIONID) {
            return fecService.getRsetFromEntityFile(entityFile)
        } else if (entityFile.questionId == FrenchConstants.RE2020_RSET_QUESTIONID) {
            return re2020Service.getRsetFromFile(entityFile)
        }

        return null
    }

    void readRsetInfo(Entity parentEntity, HttpSession session, EntityFile entityFile, Map<String, String> renderMappingTable) {
        if (entityFile) {
            // rsetDto can be 2 different classes
            def rset = getRsenvOrRsetFromEntityFile(entityFile)

            if (rset) {
                RsetDto rsetDto
                boolean isFec = entityFile.questionId == FrenchConstants.FEC_RSET_QUESTIONID

                if (rset instanceof Re2020RSEnv) {
                    rsetDto = convertRsevnToRsetDto(rset)
                } else if (rset instanceof FecRSEnv) {
                    rsetDto = convertFecRsevnToRsetDto(rset)
                } else {
                    rsetDto = convertToRsetDto(rset)
                }

                if (rsetDto) {
                    rsetDto.questionId = entityFile.questionId
                    String rsetImportMappingTable = getRsetImportMappingTableRenderString(rsetDto, parentEntity, isRsenvFormat(rset))

                    if (rsetImportMappingTable) {
                        renderMappingTable?.put(entityFile.questionId, rsetImportMappingTable)
                    }

                    String foundBatiments = getRsetBatimentMessage(rsetDto)

                    if (foundBatiments) {
                        foundBatiments = "<br /><br />${foundBatiments}"
                        flashService.setWarningAlert(stringUtilsService.getLocalizedText(isFec ? 'fec.rset_info' : 're2020.rset_info', foundBatiments))
                    }
                }

                if (session?.getAttribute(SessionAttribute.RSET_MESSAGE.attribute)) {
                    flashService.setSuccessAlert("${session?.getAttribute(SessionAttribute.RSET_MESSAGE.attribute)}")
                    session.removeAttribute(SessionAttribute.RSET_MESSAGE.attribute)
                } else {
                    String indicatorId = isFec ? FrenchConstants.FEC_TOOLS.find { parentEntity?.indicatorIds?.contains(it) } : FrenchConstants.RE2020_TOOLS.find { parentEntity?.indicatorIds?.contains(it) } ?: FrenchConstants.RSET_TOOLS.first()
                    Indicator ind = indicatorService.getIndicatorByIndicatorId(indicatorId)
                    List<String> rsetMessages = []
                    readRSETToDatasets(rset, ind?.readValuesFromXML, parentEntity, null, rsetMessages, entityFile.questionId)

                    if (rsetMessages) {
                        flashService.setSuccessAlert("${rsetMessages.join("<br />")}")
                    }
                }
            }

        } else {
            String error = errorMessageUtil.getErrorMessageFromSession()

            if (error) {
                flashService.setErrorAlert(error)
            }
        }
    }

    /**
     * Decides if rendering of the RSET import questions on project level query should be skipped
     * @param question
     * @param parent
     * @return
     */
    boolean skipRenderingRsetQuestion(Question question, Entity parent) {
        if (question && parent) {
            if (FrenchConstants.FEC_RSET_QUESTIONID == question.questionId && !fecService.isFecToolActivated(parent)) {
                return true
            } else if (FrenchConstants.RE2020_RSET_QUESTIONID == question.questionId && !re2020Service.isRe2020ToolActivated(parent)) {
                return true
            }
        }
        return false
    }

    /**
     * Filter the designs for RSEE export (both FEC and RE2020 tools)
     * @param batimentIndexMappings
     * @param allDesigns
     * @param userRsetMapping
     * @param indicator
     * @param queryIds
     * @return
     */
    List<Entity> getDesignsForRseeExport(Map<String, Integer> batimentIndexMappings, List<Entity> allDesigns,
                                         RsetMapping userRsetMapping, Indicator indicator, List<String> queryIds) {
        List<Entity> designs = getDesignsFromBatimentMapping(batimentIndexMappings, allDesigns)
        addDesignForParcelleIfNeeded(userRsetMapping, designs, allDesigns)

        // Only export designs that are ready
        return designs.findAll { it.isIndicatorReady(indicator, queryIds) }?.sort { it.name?.toLowerCase() }
    }

    private static List<Entity> getDesignsFromBatimentMapping(Map<String, Integer> batimentIndexMappings, List<Entity> allDesigns) {
        List<Entity> designs = []
        // get the designs from rsetMapping
        batimentIndexMappings?.sort { it.value }?.each { String key, Integer batimentIndex ->
            Entity design = allDesigns?.find { key == it.id.toString() }
            if (design) {
                designs.add(design)
            }
        }
        return designs
    }

    // add design selected for exporting Parcelle if not already included
    private static void addDesignForParcelleIfNeeded(RsetMapping userRsetMapping, List<Entity> selectedDesigns, List<Entity> allDesigns) {
        if (userRsetMapping?.designIdForParcelle && !selectedDesigns.any { it.id?.toString() == userRsetMapping.designIdForParcelle }) {
            Entity designSelectedForParcelle = allDesigns?.find { userRsetMapping.designIdForParcelle == it.id?.toString() }
            if (designSelectedForParcelle) {
                selectedDesigns.add(designSelectedForParcelle)
            }
        }
    }

    boolean isValidXMLSchema(URL xsdFile, File xmlFile) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Validator validator = factory.newSchema(xsdFile).newValidator()
            validator.validate(new StreamSource(new StringReader(xmlFile.text)))
        } catch (SAXParseException e) {
            loggerUtil.debug(log, "XML resource validation error: ", e)
            return false;
        }

        return true;
    }

    boolean isFecEpdcFile(File xmlFile) {
        isValidXMLSchema(this.class.classLoader.getResource(FEC_XSD_FILE), xmlFile)
    }

    boolean isRe2020EpdcFile(File xmlFile) {
        isValidXMLSchema(this.class.classLoader.getResource(RE2020_XSD_FILE), xmlFile)
    }

    def getEpdcFromResource(Resource resource) {
        if (!resource?.epdcFilePath) {
            return null
        }
        File xmlFile = new File(resource?.epdcFilePath)
        if (!xmlFile.exists() || xmlFile.length() == 0) {
            return null
        }

        Class epdcClass
        if (isFecEpdcFile(xmlFile)) {
            epdcClass = FecEPDC
        } else if (isRe2020EpdcFile(xmlFile)) {
            epdcClass = Re2020EPDC
        } else {
            throw new RuntimeException("XMl file is not compatible nor with FEC nor with RE2020 schema.")
        }

        return getEpdcFromXml(xmlFile, epdcClass)
    }

    def getEpdcFromXml(File xmlFile, Class epdcClass) {
        def epdc

        if (xmlFile) {
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(epdcClass)
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller()
                epdc = unmarshaller.unmarshal(xmlFile)
            } catch (Exception e) {
                loggerUtil.error(log, "Error in creating EPDC from XML:", e)
                flashService.setErrorAlert("Error in creating EPDC from XML: ${e.message}", true)
            }
        }
        return epdc
    }


    /**
     * Fetches RSEnv (priority if exists) or RSET data from French import file
     * @param parentEntityId
     * @param isFec
     * @return RSET or RSEnv data for FEC and RE2020 tools
     */
    def getProjetDataForFrenchImport(String parentEntityId, boolean isFec) {
        def rset

        if (isFec) {
            EntityFile fecEntityFile = fecService.getFecRsetEntityFile(parentEntityId)

            if (fecEntityFile) {

                FecProjet fecProjet = fecService.getProjetFromEntityFile(fecEntityFile)

                if (fecProjet) {
                    if (!fecProjet.getRSEnv()?.getEntreeProjet()) {
                        rset = fecProjet.getRSET()

                        if (rset && !rset.getDatasComp()) {
                            rset = null
                        }
                    } else {
                        rset = fecProjet.getRSEnv()
                    }
                }
            }
        } else {
            EntityFile re2020EntityFile = re2020Service.getRe2020RsetEntityFile(parentEntityId)

            if (re2020EntityFile) {

                Re2020Projet re2020Projet = re2020Service.getRe2020ProjetFromFile(re2020EntityFile)

                if (re2020Projet) {
                    rset = re2020Projet.getRSETOrRSEnv()?.find { it instanceof Re2020RSEnv } as Re2020RSEnv

                    if (!rset) {
                        rset = re2020Projet.getRSETOrRSEnv()?.find { it instanceof Re2020RSET } as Re2020RSET
                    }
                }
            }
        }

        return rset
    }
    File getILCDEPDXml(indicator, entity, parentEntity){

        File xmlFile
        List<ResultCategory> mapToResourceCategories = indicator.getResolveResultCategories(parentEntity)?.findAll({it.mapToStage})
        List<CalculationRule> mapToResourceRules = indicator.getResolveCalculationRules(parentEntity)?.findAll({it.mapToImpactCategory})
        List<CalculationResult> calculationResults = entity?.getCalculationResultObjects(indicator.indicatorId, null, null)

        if(mapToResourceCategories && mapToResourceRules && calculationResults) {
            Resource resource = new Resource() // Temp

            List<String> persistingDoubleProperties = domainClassService.getPersistentPropertyNamesForDomainClass(Resource.class, Double)
            List<String> persistingListProperties = domainClassService.getPersistentPropertyNamesForDomainClass(Resource.class, List)
            List<String> booleanProperties = domainClassService.getPersistentPropertyNamesForDomainClass(Resource.class, Boolean)

            indicator.getQueries(parentEntity)?.each { Query q ->
                q.getAllQuestions()?.findAll({ Question question -> question.mapToResourceParameter })?.each { Question question ->
                    Dataset d = entity.datasets?.find({ it.queryId == q.queryId && it.questionId == question.questionId })

                    if (d && d.answerIds) {
                        def resourceAttributeValue = d.answerIds.first()?.toString()

                        try {
                            if (persistingDoubleProperties.contains(question.mapToResourceParameter)) {
                                resourceAttributeValue = resourceAttributeValue.replaceAll(",", ".")?.toDouble()
                            } else if (persistingListProperties.contains(question.mapToResourceParameter)) {
                                resourceAttributeValue = [resourceAttributeValue]
                            } else if (booleanProperties.contains(question.mapToResourceParameter)) {
                                resourceAttributeValue = resourceAttributeValue.toBoolean()
                            }
                            DomainObjectUtil.callSetterByAttributeName(question.mapToResourceParameter, resource, resourceAttributeValue)
                        } catch (Exception e) {
                            // handle unsettable params?
                            loggerUtil.error(log, "RenderILCDEPDXml unsettable params error", e)
                            flashService.setErrorAlert("RenderILCDEPDXml unsettable params error: ${e.getMessage()} ", Boolean.TRUE)
                        }
                    }
                }
            }
            ResourceType resourceType = resource.resourceTypeObject
            ResourceType subType = resourceService.getSubType(resource)
            optimiResourceService.setMagicButtonValues(resource)

            ProcessDataSetType processDataSetType = new ProcessDataSetType()
            processDataSetType.setVersion("1.1") // TODO: What is the right version?

            ProcessInformationType processInformationType = new ProcessInformationType()
            DataSetInformationType dataSetInformationType = new DataSetInformationType()
            dataSetInformationType.setUUID(UUID.randomUUID().toString())
            NameType nameType = new NameType()
            StringMultiLang stringMultiLang = new StringMultiLang()
            stringMultiLang.setValue(resource.nameEN)
            stringMultiLang.setLang("en")
            nameType.getBaseName().add(stringMultiLang)
            dataSetInformationType.setName(nameType)

            if (resourceType && subType) {
                ClassificationInformationType classificationInformationType = new ClassificationInformationType()
                ClassificationType classificationType = new ClassificationType()
                classificationType.setName("OneClickLCA")
                ClassType classType1 = new ClassType()
                classType1.setLevel(0.toBigInteger())
                classType1.setValue(resourceType?.nameEN)
                classificationType.getClazz().add(classType1)
                ClassType classType2 = new ClassType()
                classType2.setLevel(1.toBigInteger())
                classType2.setValue(subType?.nameEN)
                classificationType.getClazz().add(classType2)
                classificationInformationType.getClassification().add(classificationType)
                dataSetInformationType.setClassificationInformation(classificationInformationType)
            }
            /*FTMultiLang generalComment = new FTMultiLang()
        generalComment.setValue("Tähän jotain?")
        generalComment.setLang("en")
        dataSetInformationType.getGeneralComment().add(generalComment)*/
            processInformationType.setDataSetInformation(dataSetInformationType)

            TimeType time = new TimeType()
            time.setReferenceYear(resource.environmentDataPeriod?.toBigInteger())
            time.setDataSetValidUntil(resource.expirationYear?.toBigInteger())
            processInformationType.setTime(time)

            GeographyType geographyType = new GeographyType()
            LocationOfOperationSupplyOrProductionType location = new LocationOfOperationSupplyOrProductionType()
            location.setLocation(resource.isoCodesByAreas?.values()?.first()?.toUpperCase())
            //location.setLocation("FI")
            /*FTMultiLang descriptionOfRestrictions = new FTMultiLang()
        descriptionOfRestrictions.setValue("Not edible")
        descriptionOfRestrictions.setLang("en")
        location.getDescriptionOfRestrictions().add(descriptionOfRestrictions)*/
            geographyType.setLocationOfOperationSupplyOrProduction(location)
            processInformationType.setGeography(geographyType)

            TechnologyType technologyType = new TechnologyType()
            /*FTMultiLang technologyDescriptionAndIncludedProcesses = new FTMultiLang()
        technologyDescriptionAndIncludedProcesses.setValue("See flow chart")
        technologyDescriptionAndIncludedProcesses.setLang("en")
        technologyType.getTechnologyDescriptionAndIncludedProcesses().add(technologyDescriptionAndIncludedProcesses)*/
            FTMultiLang technologicalApplicability = new FTMultiLang()
            technologicalApplicability.setValue(resource.technicalSpec)
            //technologicalApplicability.setValue("resource.technicalSpec")
            technologicalApplicability.setLang("en")
            technologyType.getTechnologicalApplicability().add(technologicalApplicability)
            processInformationType.setTechnology(technologyType)

            processDataSetType.setProcessInformation(processInformationType)

            AdministrativeInformationType administrativeInformationType = new AdministrativeInformationType()
            DataEntryByType dataEntryByType = new DataEntryByType()
            Date timestamp = new Date()
            GregorianCalendar c = new GregorianCalendar()
            c.setTime(timestamp)
            XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c)
            dataEntryByType.setTimeStamp(date2)

            /*GlobalReferenceType referenceToDataSetFormat = new GlobalReferenceType()
        referenceToDataSetFormat.setType(GlobalReferenceTypeValues.PROCESS_DATA_SET) // TODO what is the right type?

        STMultiLang shortDescription = new STMultiLang()
        shortDescription.setLang("en")
        shortDescription.setValue("foo")
        referenceToDataSetFormat.getShortDescription().add(shortDescription)

        GlobalReferenceType referenceToPersonOrEntityEnteringTheData = new GlobalReferenceType()
        referenceToPersonOrEntityEnteringTheData.setType(GlobalReferenceTypeValues.PROCESS_DATA_SET) // TODO what is the right type?
        STMultiLang shortDescription2 = new STMultiLang()
        shortDescription2.setLang("en")
        shortDescription2.setValue("foo2")
        referenceToPersonOrEntityEnteringTheData.getShortDescription().add(shortDescription2)
        dataEntryByType.getReferenceToDataSetFormat().add(referenceToDataSetFormat)
        dataEntryByType.setReferenceToConvertedOriginalDataSetFrom(referenceToPersonOrEntityEnteringTheData)*/

            PublicationAndOwnershipType publicationAndOwnershipType = new PublicationAndOwnershipType()
            publicationAndOwnershipType.setDataSetVersion("00.01.000") // TODO: What???
            GlobalReferenceType referenceToOwnershipOfDataSet = new GlobalReferenceType()
            referenceToOwnershipOfDataSet.setType(GlobalReferenceTypeValues.PROCESS_DATA_SET)
            // TODO what is the right type?
            /*STMultiLang shortDescription3 = new STMultiLang()
        shortDescription3.setLang("en")
        shortDescription3.setValue("foo3")
        referenceToOwnershipOfDataSet.getShortDescription().add(shortDescription3)*/
            publicationAndOwnershipType.setReferenceToOwnershipOfDataSet(referenceToOwnershipOfDataSet)
            publicationAndOwnershipType.setCopyright(true)
            administrativeInformationType.setPublicationAndOwnership(publicationAndOwnershipType)
            administrativeInformationType.setDataEntryBy(dataEntryByType)
            processDataSetType.setAdministrativeInformation(administrativeInformationType)

            ExchangesType exchangesType = new ExchangesType()
            BigInteger dataSetInternalId = 1

            mapToResourceRules?.each { CalculationRule rule ->
                if (FrenchConstants.ILCD_OUTPUTS.contains(rule.mapToImpactCategory) || FrenchConstants.ILCD_INPUTS.contains(rule.mapToImpactCategory)) {
                    ExchangeType exchangeType = new ExchangeType()
                    exchangeType.setDataSetInternalID(dataSetInternalId)
                    exchangeType.setMeanAmount(0D)
                    exchangeType.setExchangeDirection(FrenchConstants.ILCD_OUTPUTS.contains(rule.mapToImpactCategory) ? ExchangeDirectionValues.OUTPUT : ExchangeDirectionValues.INPUT)

                    GlobalReferenceType referenceToFlowDataSet = new GlobalReferenceType()
                    // TODO what is the right type?
                    referenceToFlowDataSet.setType(GlobalReferenceTypeValues.FLOW_DATA_SET)
                    STMultiLang ruleShortDescription = new STMultiLang()
                    ruleShortDescription.setLang("en")
                    ruleShortDescription.setValue(rule.name?.get("EN"))
                    referenceToFlowDataSet.getShortDescription().add(ruleShortDescription)
                    exchangeType.setReferenceToFlowDataSet(referenceToFlowDataSet)

                    Other other = new Other()

                    mapToResourceCategories.each { ResultCategory resultCategory ->
                        Amount resultAmount = new Amount()
                        resultAmount.setModule(resultCategory.mapToStage)
                        resultAmount.setValue(calculationResults?.find({ resultCategory.resultCategoryId.equals(it.resultCategoryId) && rule.calculationRuleId.equals(it.calculationRuleId) })?.result?.toBigDecimal() ?: 0.toBigDecimal())
                        other.getAmount().add(resultAmount)
                    }
                    exchangeType.setOther(other)
                    exchangesType.getExchange().add(exchangeType)
                    dataSetInternalId++
                }
            }
            processDataSetType.setExchanges(exchangesType)

            LCIAResultsType lciaResultsType = new LCIAResultsType()

            mapToResourceRules?.each { CalculationRule rule ->
                if (!FrenchConstants.ILCD_OUTPUTS.contains(rule.mapToImpactCategory) && !FrenchConstants.ILCD_INPUTS.contains(rule.mapToImpactCategory)) {
                    LCIAResultType lciaResultType = new LCIAResultType()
                    GlobalReferenceType referenceToLCIAMethodDataSet = new GlobalReferenceType()
                    // TODO what is the right type?
                    referenceToLCIAMethodDataSet.setType(GlobalReferenceTypeValues.LCIA_METHOD_DATA_SET)
                    STMultiLang ruleShortDescription = new STMultiLang()
                    ruleShortDescription.setLang("en")
                    ruleShortDescription.setValue(rule.name?.get("EN"))
                    referenceToLCIAMethodDataSet.getShortDescription().add(ruleShortDescription)
                    lciaResultType.setReferenceToLCIAMethodDataSet(referenceToLCIAMethodDataSet)
                    Other ruleOther = new Other()

                    mapToResourceCategories.each { ResultCategory resultCategory ->
                        Amount resultAmount = new Amount()
                        resultAmount.setModule(resultCategory.mapToStage)
                        resultAmount.setValue(calculationResults?.find({ resultCategory.resultCategoryId.equals(it.resultCategoryId) && rule.calculationRuleId.equals(it.calculationRuleId) })?.result?.toBigDecimal() ?: 0.toBigDecimal())
                        ruleOther.getAmount().add(resultAmount)
                    }
                    lciaResultType.setOther(ruleOther)
                    lciaResultsType.getLCIAResult().add(lciaResultType)
                }
            }
            processDataSetType.setLCIAResults(lciaResultsType)


            com.bionova.optimi.xml.ilcd.ObjectFactory objectFactory = new com.bionova.optimi.xml.ilcd.ObjectFactory()
            def processDataSet = objectFactory.createProcessDataSet(processDataSetType)

            JAXBContext jaxbContext = JAXBContext.newInstance(ProcessDataSetType.class)

            xmlFile = File.createTempFile("test", ".xml")
            FileOutputStream fileOutputStream = new FileOutputStream(xmlFile)
            Marshaller marshaller = jaxbContext.createMarshaller()
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)

            try {
                marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new ILCDNamespacePrefixMapper())
            } catch (Exception e) {
                loggerUtil.error(log, "RenderILCDEPDXml error", e)
                flashService.setErrorAlert("RenderILCDEPDXml error: ${e.getMessage()} ", Boolean.TRUE)
            }
            marshaller.marshal(processDataSet, fileOutputStream)
        }else {
            return null
        }
        return xmlFile
    }
}