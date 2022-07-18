package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.EntityFile
import com.bionova.optimi.core.domain.mongo.ResultCategory
import com.bionova.optimi.core.util.LoggerUtil
import com.bionova.optimi.frenchTools.FrenchConstants
import com.bionova.optimi.frenchTools.FrenchStore
import com.bionova.optimi.frenchTools.helpers.BatimentArea
import com.bionova.optimi.frenchTools.helpers.DatasetGroup
import com.bionova.optimi.frenchTools.helpers.RsetMapping
import com.bionova.optimi.frenchTools.helpers.ZoneArea
import com.bionova.optimi.xml.re2020RSEnv.Projet
import com.bionova.optimi.xml.re2020RSEnv.RSET
import com.bionova.optimi.xml.re2020RSEnv.RSEnv
import grails.compiler.GrailsCompileStatic
import groovy.transform.TypeCheckingMode
import org.codehaus.groovy.runtime.typehandling.GroovyCastException
import org.springframework.web.multipart.MultipartFile

import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBException
import javax.xml.bind.Unmarshaller


/**
 * Methods in this class should be used for RE2020 tool only
 */
@GrailsCompileStatic
class Re2020Service {

    DatasetService datasetService
    IndicatorService indicatorService
    LoggerUtil loggerUtil
    FlashService flashService
    StringUtilsService stringUtilsService
    ValueReferenceService valueReferenceService

    void handleDatasetsForAllZone(Map<String, Set<Dataset>> datasetsPerZone, BatimentArea batimentArea) {
        if (!datasetsPerZone?.containsKey(FrenchConstants.FEC_UNASSIGNED_ZONE)) {
            return
        }

        // if we can't split the datasets for all zones then delete it.
        if (!batimentArea.allZonesHaveSharedRatio) {
            datasetsPerZone.remove(FrenchConstants.FEC_UNASSIGNED_ZONE)
            return
        }

        for (Dataset datasetForAllZones in datasetsPerZone.get(FrenchConstants.FEC_UNASSIGNED_ZONE)) {
            Double quantityAnswer = datasetForAllZones.quantity != null ? datasetService.getAnswerFromDatasetAsDouble(datasetForAllZones) : null

            for (ZoneArea zoneArea in batimentArea.zoneAreas) {
                Dataset copy = datasetService.copyDataset(datasetForAllZones)
                if (quantityAnswer) {
                    Double sharedQty = quantityAnswer * zoneArea.zonePerBatimentRatio
                    if (sharedQty != null) {
                        copy.quantity = sharedQty
                        copy.answerIds = [sharedQty.toString()]
                    }
                }
                datasetService.groupDatasetSameKey(copy, zoneArea.zoneId, datasetsPerZone)
            }
        }
        datasetsPerZone.remove(FrenchConstants.FEC_UNASSIGNED_ZONE)
    }

    /**
     * Group the water and energy consumption datasets
     * @param datasetGroup
     */
    void groupEnergyAndWaterDatasets(DatasetGroup datasetGroup) {
        Set<Dataset> energyAndWater = datasetGroup?.datasetsPerQuery?.get(FrenchConstants.ENERGY_AND_WATER_FEC_QUERYID)
        if (energyAndWater) {
            for (Dataset dataset in energyAndWater) {
                switch (dataset.sectionId) {
                    case FrenchConstants.ENERGY_SECTIONID:
                        Integer energyUse = datasetService.getAdditionalAnswerFromDataset(dataset, FrenchConstants.ENERGY_USE_RE2020_QUESTIONID)?.toInteger()
                        if (energyUse != null) {
                            datasetService.groupDatasetSameKey(dataset, energyUse, datasetGroup.energyDatasetsPerUse)
                            datasetGroup.energyWaterSiteBeDatasetIds.add(dataset.manualId)
                        }
                        break
                    case FrenchConstants.WATER_SECTIONID:
                        if (dataset.resourceId in FrenchConstants.SousContributeurRef.EAU.drinkingWaterResourceIds()) {
                            datasetService.groupDatasetSameKey(dataset, FrenchConstants.SousContributeurRef.EAU.DRINKING_WATER.intValue(), datasetGroup.waterDatasetsPerType)
                            datasetGroup.energyWaterSiteBeDatasetIds.add(dataset.manualId)
                        } else if (dataset.resourceId in FrenchConstants.SousContributeurRef.EAU.wasteWaterResourceIds()) {
                            datasetService.groupDatasetSameKey(dataset, FrenchConstants.SousContributeurRef.EAU.WASTE_WATER.intValue(), datasetGroup.waterDatasetsPerType)
                            datasetGroup.energyWaterSiteBeDatasetIds.add(dataset.manualId)
                        } else if (dataset.resourceId in FrenchConstants.SousContributeurRef.EAU.rainWaterResourceIds()) {
                            datasetService.groupDatasetSameKey(dataset, FrenchConstants.SousContributeurRef.EAU.RAIN_WATER.intValue(), datasetGroup.waterDatasetsPerType)
                            datasetGroup.energyWaterSiteBeDatasetIds.add(dataset.manualId)
                        }
                        break
                }
            }
        }
    }

    /**
     * Group the construction site datasets
     * @param datasetGroup
     */
    void groupSiteDatasetsPerCategory(DatasetGroup datasetGroup) {
        Set<Dataset> constructionSite = datasetGroup?.datasetsPerQuery?.get(FrenchConstants.CONSTRUCTION_FEC_QUERYID)
        if (constructionSite) {
            for (Dataset dataset in constructionSite) {
                switch (dataset.sectionId) {
                    case FrenchConstants.ENERGY_AND_WATER_USE_SECTIONID:
                        if (dataset.questionId in FrenchConstants.SousContributeurRef.CHANTIER.constructionEnergyQuestionIds()) {
                            datasetService.groupDatasetSameKey(dataset, FrenchConstants.SousContributeurRef.CHANTIER.ENERGY.intValue(), datasetGroup.siteDatasetsPerCategory)
                            datasetGroup.energyWaterSiteBeDatasetIds.add(dataset.manualId)
                        } else if (dataset.questionId in FrenchConstants.SousContributeurRef.CHANTIER.constructionWaterQuestionIds()) {
                            datasetService.groupDatasetSameKey(dataset, FrenchConstants.SousContributeurRef.CHANTIER.WATER.intValue(), datasetGroup.siteDatasetsPerCategory)
                            datasetGroup.energyWaterSiteBeDatasetIds.add(dataset.manualId)
                        }
                        break
                    case FrenchConstants.EARTH_MASSES_SECTIONID:
                        datasetService.groupDatasetSameKey(dataset, FrenchConstants.SousContributeurRef.CHANTIER.EARTH_MASS.intValue(), datasetGroup.siteDatasetsPerCategory)
                        datasetGroup.energyWaterSiteBeDatasetIds.add(dataset.manualId)
                        break
                    case FrenchConstants.CONSTRUCTION_MATERIALS_SECTIONID:
                        datasetService.groupDatasetSameKey(dataset, FrenchConstants.SousContributeurRef.CHANTIER.SITE_MATERIALS.intValue(), datasetGroup.siteDatasetsPerCategory)
                        datasetGroup.energyWaterSiteBeDatasetIds.add(dataset.manualId)
                        break
                }
            }
        }
    }

    /**
     * Group the datasets for result category "Be"
     * @param datasetGroup
     */
    void groupBeDatasets(DatasetGroup datasetGroup, FrenchStore store) {
        ResultCategory beResultCat = store.resultCategories?.find { it.resultCategoryId == FrenchConstants.BE_RESULT_CATID }
        if (!beResultCat || !store.indicator) {
            return
        }

        Map<String, List<String>> queryAndSectionForBe = indicatorService.getQueriesAndSectionsFromResultCategories(store.indicator, [beResultCat], store.resultCategories)
        queryAndSectionForBe?.each { String queryIdForBe, List<String> sectionIdsForBe ->
            Set<Dataset> queryDatasets = datasetGroup?.datasetsPerQuery?.get(queryIdForBe)
            if (queryDatasets) {
                for (Dataset dataset in queryDatasets) {
                    if (dataset.sectionId in sectionIdsForBe) {
                        datasetService.groupDatasetSameKey(dataset, FrenchConstants.EXPORTED_ENERGY_REF, datasetGroup.beDatasetsPerRef)
                        datasetGroup.energyWaterSiteBeDatasetIds.add(dataset.manualId)
                    }
                }
            }
        }
    }

    EntityFile getRe2020RsetEntityFile(String parentEntityId) {
        if (parentEntityId) {
            return EntityFile.findByEntityIdAndQueryIdAndQuestionId(parentEntityId, FrenchConstants.FEC_QUERYID_PROJECT_LEVEL, FrenchConstants.RE2020_RSET_QUESTIONID)
        }
        return null
    }

    boolean isRe2020RsetFileOk(MultipartFile file) {
        RSET rset = getRsetFromFile(null, file)
        if (rset) {
            String entreeVersion = rset.entreeProjet?.getVersion()
            String sortieVersion = rset.sortieProjet?.getVersion()
            if (!((FrenchConstants.RE2020_RSET_VERSION == entreeVersion && FrenchConstants.RE2020_RSET_VERSION == sortieVersion) ||
                    (FrenchConstants.RE2020_RSET_VERSION_OLD == entreeVersion && FrenchConstants.RE2020_RSET_VERSION_OLD == sortieVersion))) {
                flashService.setErrorAlert(stringUtilsService.getLocalizedText('xml.invalid_rset_re2020'))
                return false
            }
        }
        return true
    }

    /**
     * Check in parent entity if FEC tool is activated
     * @param parent
     * @return
     */
    Boolean isRe2020ToolActivated(Entity parent) {
        if (parent?.indicatorIds) {
            return parent.indicatorIds?.any { it in FrenchConstants.RE2020_TOOLS }
        }
        return false
    }

    Projet getRe2020ProjetFromFile(EntityFile entityFile, MultipartFile multipartFile = null) {
        Projet projet = null
        if (entityFile || multipartFile) {
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(Projet.class)
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller()
                File file = File.createTempFile(UUID.randomUUID().toString(), ".xml")
                OutputStream outputStream = new FileOutputStream(file)
                outputStream.write(entityFile ? entityFile.data : multipartFile.bytes)
                outputStream.close()

                File xml = File.createTempFile(UUID.randomUUID().toString(), ".xml")
                Writer writer = new FileWriter(xml)
                writer.write(file.getText("UTF-8"))
                writer.close()
                projet = unmarshaller.unmarshal(xml) as Projet
                xml.delete()
                file.delete()
            } catch (GroovyCastException ce) {
                loggerUtil.error(log, "File is with incorrect format for RE2020 import", ce)
                flashService.setErrorAlert(stringUtilsService.getLocalizedText('re2020.import.incorrect.format'))
                return null
            } catch (JAXBException e) {
                loggerUtil.error(log, "Something wrong with the imported file for RE2020", e)
                flashService.setErrorAlert(stringUtilsService.getLocalizedText('re2020.import.incorrect.format'))
                return null
            } catch (FileNotFoundException fileNotFound) {
                loggerUtil.error(log, "Cannot open a file for RE2020 import", fileNotFound)
                return null
            } catch (IOException ioe) {
                loggerUtil.error(log, "Cannot create a file for RE2020 import", ioe)
                return null
            }

        }
        return projet
    }

    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    RSET getRsetFromFile(EntityFile entityFile, MultipartFile multipartFile = null) {
        return getRe2020ProjetFromFile(entityFile, multipartFile)?.getRSETOrRSEnv()?.find { it instanceof RSET } as RSET
    }


    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    RSEnv getRsenvFromFile(EntityFile entityFile, MultipartFile multipartFile = null) {
        return getRe2020ProjetFromFile(entityFile, multipartFile)?.getRSETOrRSEnv()?.find { it instanceof RSEnv } as RSEnv
    }

    /**
     * Determine if the design selected for exporting parcelle is stand alone parcelle (meaning it is meant to export only parcelle and not the batiment)
     * Logic:
     *  if the design selected for exporting parcelle but it is not mapped for batiments >>> it is stand alone parcelle design
     *  the {@link RsetMapping#batimentIndexMappings} is a map with key: designId
     * @param designs
     * @param userRsetMapping
     * @return
     */
    boolean isStandAloneParcelle(List<Entity> designs, RsetMapping userRsetMapping) {
        if (userRsetMapping?.designIdForParcelle &&
                designs?.any { it.id?.toString() == userRsetMapping?.designIdForParcelle } &&
                !userRsetMapping.batimentIndexMappings?.containsKey(userRsetMapping.designIdForParcelle)) {
            return true
        }
        return false
    }

    void setAssessmentPeriodValueToFrenchStore(FrenchStore store) {
        if (store && store.assessmentValuePerDesign.isEmpty() && store.designs && store.indicator) {
            store.designs.each {
                Double assessment = valueReferenceService.getDoubleValueForEntity(store.indicator.assessmentPeriodValueReference, it) ?: store.indicator.assessmentPeriodFixed ?: null
                store.assessmentValuePerDesign.put(it.id.toString(), assessment)
            }
        }
    }
}
