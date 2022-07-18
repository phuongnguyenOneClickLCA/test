package com.bionova.optimi.construction.controller

import com.bionova.optimi.core.domain.mongo.Account
import com.bionova.optimi.core.domain.mongo.CalculationRule
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.EntityFile
import com.bionova.optimi.core.domain.mongo.Feature
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.License
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ResourceType
import com.bionova.optimi.core.domain.mongo.ResultCategory
import com.bionova.optimi.core.service.BetieService
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.data.ResourceCache
import com.bionova.optimi.frenchTools.FrenchConstants
import com.bionova.optimi.frenchTools.FrenchStore
import com.bionova.optimi.frenchTools.helpers.RsetMapping
import com.bionova.optimi.ui.rset.RsetDto
import com.bionova.optimi.xml.fecRSEnv.Projet
import com.bionova.optimi.xml.fecRSEnv.RSET
import com.bionova.optimi.xml.fecRSEnv.RSEnv
import org.apache.tools.zip.ZipEntry
import org.apache.tools.zip.ZipOutputStream
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest
import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller

/**
 * @author Pasi-Markus Mäkelä
 */
class XmlHandlerController {

    def entityService
    def indicatorService
    def datasetService
    def loggerUtil
    def xmlService
    def accountService
    def calculationResultService
    def entreeProjetUtilRe2020
    def dataCompUtilRe2020
    def sortieProjetUtilRe2020
    def rsEnvUtilRe2020
    def re2020Service
    def entreeProjetUtilFec
    def dataCompUtilFec
    def sortieProjetUtilFec
    def optimiResourceService
    def domainClassService
    def flashService
    def queryService
    def fecService
    def licenseService
    BetieService betieService

    /*
    Items that cannot have 0 as fallback value
    • RSEnv/entree_projet/batiment/zone/sdp
    • RSEnv/entree_projet/batiment/zone/contributeur/donnees_service/quantite
    • RSEnv/entree_projet/batiment/zone/contributeur/lot/donnees_composant/dve
    • RSEnv/entree_projet/batiment/zone/contributeur/lot/sous_lot/donnees_composant/quantite
    • RSEnv/entree_projet/batiment/zone/contributeur/lot/sous_lot/donnees_composant/dve
    • RSEnv/entree_projet/batiment/zone/contributeur/lot/donnees_composant/quantite
    • RSEnv/data_comp/donnees_generales/operation/surface_parcelle
    • RSEnv/data_comp/batiment/nb_occupant (uniquement pour les usages résidentiels)
    • RSEnv/data_comp/batiment/sdp
    • RSEnv/data_comp/batiment/nb_niv_surface (uniquement pour les usages résidentiels)
    • RSEnv/data_comp/batiment/duree_chantier
    * */
    def renderILCDEPDXml() {
        final String missingParam = "Missing parameters"
        if (params.indicatorId && params.entityId) {
            Entity entity = entityService.getEntityById(params.entityId, session)
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(params.indicatorId, true)
            Entity parentEntity = entity?.parentById
            if (indicator.allowIlcdXmlExport) {
                File xmlFile = xmlService.getILCDEPDXml(indicator, entity, parentEntity)
                if(xmlFile) {
                    render(file: xmlFile, contentType: "application/xml")
                    xmlFile.delete()
                }else{
                    render text: missingParam
                }
            } else {
                render text: missingParam
            }
        } else {
            render text: missingParam
        }
    }

    // same endpoint for rsee export fec and re2020 tools
    def renderRSEnv() {
        Entity parentEntity = entityService.readEntity(params.parentEntityId)
        List<License> validLicenses = licenseService.getValidLicensesForEntity(parentEntity)
        List<Feature> featuresAvailableForEntity = entityService.getFeatures(validLicenses)
        List<Entity> allDesigns = parentEntity?.childrenByChildEntities?.findAll({ !it.deleted })
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(params.indicatorId, true)
        List<CalculationRule> calculationRules = indicator?.getResolveCalculationRules(parentEntity)

        if (indicator && calculationRules) {
            boolean isFecExport = indicator.exportXML == FrenchConstants.FRANCE_ENERGIE_CARBONE_XML
            boolean isRe2020Export = indicator.exportXML == FrenchConstants.RE2020_XML
            EntityFile entityFile = isRe2020Export ? re2020Service.getRe2020RsetEntityFile(parentEntity?.id?.toString()) : isFecExport ? fecService.getFecRsetEntityFile(parentEntity?.id?.toString()) : null

            JAXBContext jaxbContext = isRe2020Export ? JAXBContext.newInstance(com.bionova.optimi.xml.re2020RSEnv.Projet.class) : JAXBContext.newInstance(Projet.class)
            Projet projetFec = isFecExport ? new Projet() : null
            RSEnv rsEnvFec = isFecExport ? new RSEnv() : null
            com.bionova.optimi.xml.re2020RSEnv.Projet projetRe2020 = isRe2020Export ? re2020Service.getRe2020ProjetFromFile(entityFile) : null

            if (projetRe2020) {
                projetRe2020.getRSETOrRSEnv()?.removeIf { it instanceof com.bionova.optimi.xml.re2020RSEnv.RSEnv }
            }

            com.bionova.optimi.xml.re2020RSEnv.RSEnv rsEnvRe2020 = isRe2020Export ? new com.bionova.optimi.xml.re2020RSEnv.RSEnv() : null

            String questionIdForRsetMapping = isFecExport ? FrenchConstants.FEC_RSET_QUESTIONID : FrenchConstants.RE2020_RSET_QUESTIONID
            RsetMapping userRsetMapping = parentEntity.frenchRsetMappings?.find { it.questionId == questionIdForRsetMapping }
            Map<String, Map<String, Integer>> batimentZoneIndexMappings = userRsetMapping?.batimentZoneIndexMappings
            Map<String, Integer> batimentIndexMappings = userRsetMapping?.batimentIndexMappings

            def rset

            if (entityFile) {
                rset = xmlService.getRsetFromEntityFile(entityFile)
                if (rset) {
                    RsetDto rsetDto = xmlService.convertToRsetDto(rset)
                    // check if all batiments and zones have been mapped
                    Boolean readyForExport = xmlService.checkReadinessForRseeExport(rsetDto, batimentIndexMappings, batimentZoneIndexMappings)
                    if (!readyForExport) {
                        flash.errorAlert = "${message(code: 'xml.not_ready_for_export')}"
                        redirect(controller: "entity", action: "show", params: [entityId: params.parentEntityId])
                        return // redirect doesn't stop the code
                    }
                    if (isFecExport && ((RSET) rset).getDatasComp()) {
                        projetFec.setRSET(((RSET) rset))
                    }
                    // RSET for RE2020 exists in the projet during import, hence no need to add again during export
                } else {
                    flash.errorAlert = "${message(code: 'xml.invalidFile_error')}"
                    redirect(controller: "entity", action: "show", params: [entityId: params.parentEntityId])
                    return // redirect doesn't stop the code
                }
            }

            List<String> queryIds = queryService.getQueryIdsByIndicatorAndEntity(indicator, parentEntity, featuresAvailableForEntity)
            List<Entity> designs = xmlService.getDesignsForRseeExport(batimentIndexMappings, allDesigns, userRsetMapping, indicator, queryIds)

            if (!designs) {
                // No designs of this indicator are ready for export. Raise and error alert and stop here
                flash.errorAlert = "${message(code: 'fec.export_warning')}"
                redirect(controller: "entity", action: "show", params: [entityId: params.parentEntityId])
                return // redirect doesn't stop the code
            }

            String projectVersion = null

            for (Entity design in designs) {
                if (design.datasets) {
                    optimiResourceService.initResources(design.datasets?.toList())
                }
            }

            if (isRe2020Export) {
                FrenchStore store = new FrenchStore()
                store.parentEntity = parentEntity
                store.setDesigns(designs)
                store.setIndicator(indicator)
                store.setResultCategories(indicator?.getResolveResultCategories(parentEntity))
                store.setCalculationRule(calculationRules)
                store.parcelle.designId = userRsetMapping.designIdForParcelle
                store.parcelle.isStandAlone = re2020Service.isStandAloneParcelle(designs, userRsetMapping)
                store.batimentIndexMappings = batimentIndexMappings
                store.batimentZoneIndexMappings = batimentZoneIndexMappings
                store.queryIdsWithZoneDataset = queryService.getAllQueryIdsWithZoneDataset(indicator)
                store.resultsPerDesign = calculationResultService.getCalculationResultsPerDesign(designs, indicator.indicatorId)
                re2020Service.setAssessmentPeriodValueToFrenchStore(store)

                dataCompUtilRe2020.setDatasCompToProjet(projetRe2020, store)
                entreeProjetUtilRe2020.setEntreeProjetToRSEnv(rsEnvRe2020, store)
                sortieProjetUtilRe2020.setSortieProjetToRSEnv(rsEnvRe2020, store)
                rsEnvUtilRe2020.setPhase(rsEnvRe2020, parentEntity)

                rsEnvRe2020.setVersion(FrenchConstants.RE2020_RSENV_VERSION)
                projectVersion = FrenchConstants.RE2020_PROJET_VERSION
                projetRe2020.setVersion(projectVersion)
                projetRe2020.getRSETOrRSEnv().add(rsEnvRe2020)
                store = null
            } else if (isFecExport) {
                dataCompUtilFec.setDataCompToRSEnv(rsEnvFec, parentEntity, designs, batimentIndexMappings)
                entreeProjetUtilFec.setEntreeProjetToRSEnv(rsEnvFec, indicator, rset, designs, batimentIndexMappings, batimentZoneIndexMappings)
                sortieProjetUtilFec.setSortieProjetToRSEnv(rsEnvFec, calculationRules, indicator, designs, batimentIndexMappings, batimentZoneIndexMappings)
                projectVersion = FrenchConstants.PROJET_VERSION_FEC
                rsEnvFec.setVersion(projectVersion)
                rsEnvFec.setPhase(datasetService.getValueFromDataset(parentEntity, FrenchConstants.FEC_QUERYID_PROJECT_LEVEL, FrenchConstants.DONNEES_ADMINISTRATIVES_SECTIONID, FrenchConstants.PHASE_QUESTIONID)?.toInteger())
                projetFec.setRSEnv(rsEnvFec)
                projetFec.setNumPEBN("num_PEBN${datasetService.getValueFromDataset(parentEntity, FrenchConstants.FEC_QUERYID_PROJECT_LEVEL, FrenchConstants.DONNEES_ADMINISTRATIVES_SECTIONID, FrenchConstants.NUM_PEBN_QUESTIONID)}")
                projetFec.setVersion(projectVersion)
            }

            File xmlFile = File.createTempFile("projet", ".xml")
            FileOutputStream fileOutputStream = new FileOutputStream(xmlFile)
            Marshaller marshaller = jaxbContext.createMarshaller()
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)

            if (isRe2020Export) {
                marshaller.marshal(projetRe2020, fileOutputStream)
            } else if (isFecExport) {
                marshaller.marshal(projetFec, fileOutputStream)
            }

            List<String> epdcFilesPaths = getEpdcFilePathsFromEntityDatasets(designs)

            response.setContentType('APPLICATION/OCTET-STREAM')
            response.setHeader('Content-Disposition', "Attachment;Filename=RS2E_${projectVersion}_${new Date().format('dd_MM_yyyy HH:mm:ss')}.zip")
            ZipOutputStream zip = new ZipOutputStream(response.outputStream)
            ZipEntry fileEntry = new ZipEntry("RS2E_${projectVersion}.xml")
            zip.putNextEntry(fileEntry)
            zip.write(xmlFile.bytes)

            if (entityFile && isFecExport) {
                ZipEntry entityFileEntry = new ZipEntry("RSET.xml")
                zip.putNextEntry(entityFileEntry)
                zip.write(entityFile.data)
            }

            if (epdcFilesPaths) {
                epdcFilesPaths.each { String filePath ->
                    File epdcXmlFile = new File(filePath)

                    if (epdcXmlFile && epdcXmlFile.exists()) {
                        ZipEntry epdcFileEntry = new ZipEntry(epdcXmlFile.name)
                        zip.putNextEntry(epdcFileEntry)
                        zip.write(epdcXmlFile.bytes)
                    }
                }
            }
            xmlFile.delete()
            zip.flush()
            zip.close()
            fileOutputStream.close()
        } else {
            render text: "Could not create xml, results missing."
        }
    }

    private List<String> getEpdcFilePathsFromEntityDatasets(List<Entity> designs) {
        List<String> filePaths = []

        if (designs) {
            designs.each { Entity design ->
                List<Dataset> datasets = design.datasets?.toList()
                if (datasets) {
                    ResourceCache resourceCache = ResourceCache.init(datasets)
                    for (Dataset d: datasets)
                    {
                        String path = resourceCache.getResource(d)?.epdcFilePath
                        if (path && !filePaths.contains(path)) {
                            filePaths.add(path)
                        }
                    }
                }
            }
        }
        return filePaths
    }

    def uploadEPDCXmlForAccount() {
        String accountId = params.accountId
        String classificationParameterId = params.classificationParameterId
        String classificationQuestionId = params.classificationQuestionId
        Account account = accountService.getAccount(accountId)

        if (account) {
            if (request instanceof MultipartHttpServletRequest) {
                MultipartFile xmlMultiPart = request.getFile("xmlFile")
                if (!xmlMultiPart || xmlMultiPart?.empty) {
                    flash.errorAlert = g.message(code: "account.upload_xml")
                } else {
                    try {
                        String epdcFilePath = "/var/www/static/epdcxml/${accountId}/${xmlMultiPart.getOriginalFilename()}"
                        File xmlFile = new File(epdcFilePath)
                        xmlFile.getParentFile().mkdirs()
                        xmlFile.createNewFile()
                        FileOutputStream fos = new FileOutputStream(xmlFile)
                        fos.write(xmlMultiPart.getBytes())
                        fos.close()

                        Map<String, Object> success = xmlService.createResource(xmlFile, accountId, xmlFile.name, classificationQuestionId, classificationParameterId, epdcFilePath)

                        if (success) {
                            flash.fadeSuccessAlert = success.get("returnableMessage")
                            List<String> missingStages = success.get("missingStages")
                            List<String> missingIndicatorCodes = success.get("missingIndicatorCodes")
                            if (missingStages) {
                                flash.warningAlert = "Undefined impact stages: ${missingStages}"
                            }
                            if (missingIndicatorCodes) {
                                flash.warningAlert = "Undefined impactCategory indicatorCodes: ${missingIndicatorCodes}"

                            }
                        }

                    } catch (Exception e) {
                        flash.errorAlert = "${e.getMessage()}"
                        loggerUtil.error(log, "uploadEPDCXmlForAccount error", e)
                    }
                }
            }
        }
        redirect controller: "account", action: "uploadEPDC", params: [accountId: accountId, classificationParameterId:classificationParameterId, classificationQuestionId:classificationQuestionId]
    }

    def renderBetie() {

        final String BETIE_XML_EXPORT_FILENAME = "betie.xml"
        Entity childEntity = entityService.getEntityById(params.childEntityId)
        Entity entity = childEntity.getParentById()
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(params.indicatorId, true)
        java.util.zip.ZipOutputStream zip
        try{
            response.setContentType('APPLICATION/OCTET-STREAM')
            response.setHeader('Content-Disposition', "Attachment;Filename=BETie.zip")
            zip = new java.util.zip.ZipOutputStream(response.outputStream)
            File xmlFile = File.createTempFile("betie", ".xml")
            betieService.getBetieEpdXML(xmlFile, childEntity, indicator, entity)
            java.util.zip.ZipEntry fileEntry = new java.util.zip.ZipEntry(BETIE_XML_EXPORT_FILENAME)
            zip.putNextEntry(fileEntry)
            if (xmlFile) {
                zip.write(xmlFile.bytes)
                xmlFile.delete()
            }
            zip.flush()
            zip.close()
        }catch (IOException io) {
            loggerUtil.error(log, "IOException ! : ${io}")
            flashService.setErrorAlert("IOException occured while betie export generation : ${io.message}", true)
        }

    }
}

