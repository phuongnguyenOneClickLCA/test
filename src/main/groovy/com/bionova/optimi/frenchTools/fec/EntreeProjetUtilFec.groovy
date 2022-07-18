package com.bionova.optimi.frenchTools.fec

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.service.StringUtilsService
import com.bionova.optimi.core.service.ValueReferenceService
import com.bionova.optimi.core.service.XmlService
import com.bionova.optimi.data.ResourceCache
import com.bionova.optimi.frenchTools.FrenchConstants
import com.bionova.optimi.core.domain.mongo.Construction
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.service.ConstructionService
import com.bionova.optimi.core.service.DatasetService
import com.bionova.optimi.core.service.FlashService
import com.bionova.optimi.core.service.OptimiResourceService
import com.bionova.optimi.core.util.LoggerUtil
import com.bionova.optimi.util.NumberUtil
import com.bionova.optimi.util.UnitConversionUtil
import com.bionova.optimi.xml.fecEPD.EPDC as FecEPDC
import com.bionova.optimi.xml.re2020EPD.EPDC as Re2020EPDC
import com.bionova.optimi.xml.fecRSEnv.RSET
import com.bionova.optimi.xml.fecRSEnv.RSEnv
import com.bionova.optimi.xml.fecRSEnv.RSEnv.EntreeProjet.Batiment.Zone.Contributeur.DonneesService
import com.bionova.optimi.xml.fecRSEnv.TDonneeComposant
import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

import javax.xml.datatype.DatatypeFactory
import java.text.DecimalFormat

@CompileStatic
class EntreeProjetUtilFec {
    private static Log log = LogFactory.getLog(EntreeProjetUtilFec.class)
    DatasetService datasetService
    LoggerUtil loggerUtil
    OptimiResourceService optimiResourceService
    NumberUtil numberUtil
    XmlService xmlService
    FlashService flashService
    ConstructionService constructionService
    StringUtilsService stringUtilsService
    UnitConversionUtil unitConversionUtil
    ValueReferenceService valueReferenceService
    def newCalculationServiceProxy

    void setEntreeProjetToRSEnv(RSEnv rsEnv, Indicator indicator, RSET rset, List<Entity> designs, Map<String, Integer> batimentIndexMappings, Map<String, Map<String, Integer>> batimentZoneIndexMappings) {
        DecimalFormat decimalFormat = new DecimalFormat()
        decimalFormat.setParseBigDecimal(true)

        List<String> validQueryIds = indicator.indicatorQueries?.findAll { it.additionalQuestionIds?.contains(FrenchConstants.BUILDING_ZONES_FEC_QUESTIONID) }?.collect { it.queryId }
        RSEnv.EntreeProjet entreeProjet = new RSEnv.EntreeProjet()
        Date timestamp = new Date()
        entreeProjet.setDateEtude(DatatypeFactory.newInstance().newXMLGregorianCalendar(timestamp.format("yyyy-MM-dd")))

        for (Entity design in designs) {
            Double assessment = valueReferenceService.getDoubleValueForEntity(indicator?.assessmentPeriodValueReference, design) ?: indicator?.assessmentPeriodFixed ?: null
            Integer batimentIndex = batimentIndexMappings.get(design.id.toString())
            RSEnv.EntreeProjet.Batiment batiment = new RSEnv.EntreeProjet.Batiment()
            String batimentName = rset?.entreeProjet?.batimentCollection?.batiment?.find { it.index == batimentIndex }?.name
            batiment.setNom(batimentName ?: design?.name)
            batiment.setIndex(batimentIndex)
            batiment.setPeriodeReference(FrenchConstants.PERIODE_REFERENCE)

            // Set values to schema objects
            design.datasets?.each { Dataset dataset ->
                if ("otherFECQueries" == dataset.queryId) {
                    String answer = dataset.answerIds && !dataset.answerIds.isEmpty() ? dataset.answerIds.get(0) : null

                    if (answer != null) {
                        if ("selfGeneratedElectricity" == dataset.sectionId) {
                            if ("producedElectricityPV" == dataset.questionId) {
                                batiment.setProductionPv(numberUtil.answerToBigDecimal(answer, decimalFormat, dataset))
                            } else if ("selfUsedElectricityPV" == dataset.questionId) {
                                batiment.setAutoconsommationPv(numberUtil.answerToBigDecimal(answer, decimalFormat, dataset))
                            } else if ("producedElectricityCHP" == dataset.questionId) {
                                batiment.setCogeneration(numberUtil.answerToBigDecimal(answer, decimalFormat, dataset))
                            } else if ("selfUsedElectricityCHP" == dataset.questionId) {
                                batiment.setAutoconsommationCogeneration(numberUtil.answerToBigDecimal(answer, decimalFormat, dataset))
                            }
                        }
                    }
                }
            }
            setZonesToBatiment(batiment, design, validQueryIds, batimentZoneIndexMappings, decimalFormat, assessment, indicator)
            entreeProjet.batiment.add(batiment)
        }
        rsEnv.setEntreeProjet(entreeProjet)
    }

    private void setZonesToBatiment(RSEnv.EntreeProjet.Batiment batiment, Entity design,
                                    List<String> validQueryIds, Map<String, Map<String, Integer>> batimentZoneIndexMappings,
                                    DecimalFormat decimalFormat, Double assessment, Indicator indicator) {
        Integer usage_principal
        BigDecimal sdp
        Set<Dataset> designDatasetsForZones = design.datasets?.findAll { !it.projectLevelDataset && validQueryIds?.contains(it.queryId) }
        Map<String, Integer> zones = batimentZoneIndexMappings.get(design.id.toString())
        // Todo: Not sure what this does but it doesnt seem to make sense
        if (!zones) {
            zones = ["1": 1]
        }
        List<String> validZonesForSdpArea = datasetService.getValidFECZones(design.datasets)
        Set<Dataset> datasetForZeroZoneAsSet
        if (validZonesForSdpArea?.contains(FrenchConstants.FEC_UNASSIGNED_ZONE)) {
            datasetForZeroZoneAsSet = datasetService.getZoneDataSets(FrenchConstants.FEC_UNASSIGNED_ZONE, design.datasets)
        }
        zones.each { String designZoneId, Integer batimentZoneIndex ->
            RSEnv.EntreeProjet.Batiment.Zone zone = new RSEnv.EntreeProjet.Batiment.Zone()
            if (batimentZoneIndex) {
                zone.setIndex(batimentZoneIndex)
            }
            setUsageAndSdpToZone(zone, datasetForZeroZoneAsSet, sdp, usage_principal, decimalFormat, design, designZoneId)
            setBuildingContributeurToZone(zone, designDatasetsForZones, designZoneId, assessment, design, indicator, decimalFormat)
            setConstructionContributeurToZone(zone, designDatasetsForZones, designZoneId)
            setEnergyContributeurToZone(zone, designDatasetsForZones, designZoneId)
            setWaterAndRefrigerantsContributeurToZone(zone, designDatasetsForZones, designZoneId)

            batiment.getZone().add(zone)
        }
    }

    private void setUsageAndSdpToZone(RSEnv.EntreeProjet.Batiment.Zone zone, Set<Dataset> datasetForZeroZoneAsSet, BigDecimal sdp, Integer usage_principal,
                                      DecimalFormat decimalFormat, Entity design, String designZoneId) {
        String usage_principal_txt
        Dataset usage_principal_dataset
        //find dataset that match zoneId. if unassigned zone ("00") exists then take the area for that unassigned zone
        // and duplicate it for all other valid zone when exporting. If unassigned zone does not exist then assign value as per given in the query

        if (datasetForZeroZoneAsSet) {
            sdp = datasetService.getFECAreaByZoneAndQuestionId(datasetForZeroZoneAsSet, FrenchConstants.PROJECT_DESCRIPTION_FEC_QUERYID, FrenchConstants.DESCRIPTION_PER_ZONE_SECTIONID, FrenchConstants.SDP_QUESTIONID, FrenchConstants.FEC_UNASSIGNED_ZONE)
            usage_principal_dataset = datasetForZeroZoneAsSet?.find { it.questionId == FrenchConstants.SDP_QUESTIONID && it.sectionId == FrenchConstants.DESCRIPTION_PER_ZONE_SECTIONID && it.queryId == FrenchConstants.PROJECT_DESCRIPTION_FEC_QUERYID }
            usage_principal_txt = usage_principal_dataset?.additionalQuestionAnswers?.get(Constants.FEC_ZONE_USAGE_ADDQ_ID)
        } else {
            Set<Dataset> datasetForZoneAsSet = datasetService.getZoneDataSets(designZoneId, design.datasets)
            sdp = datasetService.getFECAreaByZoneAndQuestionId(datasetForZoneAsSet, FrenchConstants.PROJECT_DESCRIPTION_FEC_QUERYID, FrenchConstants.DESCRIPTION_PER_ZONE_SECTIONID, FrenchConstants.SDP_QUESTIONID, designZoneId)
            usage_principal_dataset = datasetForZoneAsSet?.find { it.questionId == FrenchConstants.SDP_QUESTIONID && it.sectionId == FrenchConstants.DESCRIPTION_PER_ZONE_SECTIONID && it.queryId == FrenchConstants.PROJECT_DESCRIPTION_FEC_QUERYID }
            usage_principal_txt = usage_principal_dataset?.additionalQuestionAnswers?.get(Constants.FEC_ZONE_USAGE_ADDQ_ID)
        }

        if (sdp != null) {
            zone.setSdp(numberUtil.answerToBigDecimal(sdp.toString(), decimalFormat, null, Boolean.TRUE))
        }

        if (usage_principal_txt) {
            List<Resource> usage_principal_resource = optimiResourceService.getResourceProfilesByResourceId(usage_principal_txt)
            if (usage_principal_resource) {
                usage_principal = numberUtil.answerToInteger(usage_principal_resource[0]?.profileId?.toString(), decimalFormat, usage_principal_dataset, Boolean.TRUE)
            }
            zone.setUsage(usage_principal)
        }
    }

    private void setBuildingContributeurToZone(RSEnv.EntreeProjet.Batiment.Zone zone, Set<Dataset> designDatasetsForZones,
                                               String designZoneId, Double assessment, Entity design, Indicator indicator,
                                               DecimalFormat decimalFormat) {
        RSEnv.EntreeProjet.Batiment.Zone.Contributeur buildingContributeur = new RSEnv.EntreeProjet.Batiment.Zone.Contributeur()
        buildingContributeur.setRef(FrenchConstants.ContributeurRefFec.BUILDING.ref)

        // datasets for this zone, but per lot
        Map<String, Set<Dataset>> groupedDatasets = [:]

        Set<Dataset> datasetForThisZoneAndUnassignedZone = designDatasetsForZones.findAll {
            String additionalQuestionZone = it?.additionalQuestionAnswers?.get(FrenchConstants.BUILDING_ZONES_FEC_QUESTIONID)
            return (additionalQuestionZone?.toString()?.isNumber() && additionalQuestionZone?.toString() == designZoneId) || additionalQuestionZone?.toString() == FrenchConstants.FEC_UNASSIGNED_ZONE
        }

        for (int i = 1; i <= FrenchConstants.MAX_LOT_NUMBERS; i++) {
            Set<Dataset> found = datasetForThisZoneAndUnassignedZone?.findAll {
                String addlotsFranceEC = it?.additionalQuestionAnswers?.get(FrenchConstants.LOTS_FRANCE_EC_QUESTIONID)?.toString()
                return addlotsFranceEC?.isDouble() && addlotsFranceEC?.toDouble()?.intValue() == i
            }


            if (found) {
                groupedDatasets.put(i.toString(), found)
            }
        }

        groupedDatasets.each { String lotNumber, Set<Dataset> datasets ->
            RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Lot lot = new RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Lot()

            if (lotNumber.isInteger()) {
                lot.setRef(lotNumber.toInteger())
            }

            ResourceCache resourceCache = ResourceCache.init(datasets.toList())
            for (Dataset dataset in datasets) {
                Resource resource = resourceCache.getResource(dataset)

                if (!resource) {
                    continue
                }

                if (resource.construction && resource.constructionId) {
                    Construction construction = constructionService.getConstructionWithConstructionId(resource.constructionId)
                    if (construction) {
                        List<Dataset> datasetsForConstruction = construction.datasets
                        ResourceCache cacheConstruction = ResourceCache.init(datasetsForConstruction)
                        Map<Dataset, Resource> resourcesConstituents = datasetsForConstruction.collect { [(it): cacheConstruction.getResource(it)] } as Map<Dataset, Resource>
                        resourcesConstituents?.each { Dataset das, Resource res ->
                            TDonneeComposant donneesComposant = createDonneeComposantFromResource(assessment, das, res, design, indicator, decimalFormat)
                            if (donneesComposant) {
                                lot.getDonneesComposant().add(donneesComposant)
                            }

                        }
                    }

                } else {
                    TDonneeComposant donneesComposant = createDonneeComposantFromResource(assessment, dataset, resource, design, indicator, decimalFormat)
                    if (donneesComposant) {
                        lot.getDonneesComposant().add(donneesComposant)
                    }
                }

                //TODO Lot SousLot

                /*if (dataset.additionalQuestionAnswers?.get("subSectionsFEC")) {
                    Projet.RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Lot.SousLot sousLot = new Projet.RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Lot.SousLot()
                    sousLot.setRef(1) //TODO subsectionsFec number
                    lot.getSousLot().add(sousLot)

                }*/
            }
            buildingContributeur.getLot().add(lot)
        }
        zone.getContributeur().add(buildingContributeur)
    }

    private void setConstructionContributeurToZone(RSEnv.EntreeProjet.Batiment.Zone zone, Set<Dataset> designDatasetsForZones, String designZoneId) {
        RSEnv.EntreeProjet.Batiment.Zone.Contributeur constructionContributeur = new RSEnv.EntreeProjet.Batiment.Zone.Contributeur()
        constructionContributeur.setRef(FrenchConstants.ContributeurRefFec.CONSTRUCTION.ref)

        // get datasets for this zone and the unassigned zone
        Set<Dataset> constructionForZone = designDatasetsForZones?.findAll { it ->
            if (FrenchConstants.CONSTRUCTION_FEC_QUERYID == it.queryId && datasetService.isDatasetForZone(it, designZoneId)) {
                if (FrenchConstants.ENERGY_AND_WATER_USE_SECTIONID == it.sectionId) {
                    return true
                } else if (FrenchConstants.EARTH_MASSES_SECTIONID == it.sectionId) {
                    return true
                }
            }
            return false
        }
        setDonneesServiceToContributeur(constructionContributeur, constructionForZone, 1, FrenchConstants.ContributeurRefFec.CONSTRUCTION)
        zone.getContributeur().add(constructionContributeur)
    }

    private void setEnergyContributeurToZone(RSEnv.EntreeProjet.Batiment.Zone zone, Set<Dataset> designDatasetsForZones, String designZoneId) {
        RSEnv.EntreeProjet.Batiment.Zone.Contributeur energyContributeur = new RSEnv.EntreeProjet.Batiment.Zone.Contributeur()
        energyContributeur.setRef(FrenchConstants.ContributeurRefFec.ENERGY.ref)

        // get datasets for this zone and unassigned zone
        Set<Dataset> energyConsumptionForZone = designDatasetsForZones?.findAll {
            FrenchConstants.ENERGY_PER_SDP_QUESTIONID == it.questionId &&
                    FrenchConstants.ENERGY_SECTIONID == it.sectionId &&
                    FrenchConstants.ENERGY_AND_WATER_FEC_QUERYID == it.queryId &&
                    datasetService.isDatasetForZone(it, designZoneId)
        }
        setDonneesServiceToContributeur(energyContributeur, energyConsumptionForZone, 0, FrenchConstants.ContributeurRefFec.ENERGY)
        zone.getContributeur().add(energyContributeur)
    }

    private void setWaterAndRefrigerantsContributeurToZone(RSEnv.EntreeProjet.Batiment.Zone zone, Set<Dataset> designDatasetsForZones, String designZoneId) {
        RSEnv.EntreeProjet.Batiment.Zone.Contributeur waterAndRefrigerantsContributeur = new RSEnv.EntreeProjet.Batiment.Zone.Contributeur()
        waterAndRefrigerantsContributeur.setRef(FrenchConstants.ContributeurRefFec.WATER_REFRIGERANT.ref)

        // get datasets for this zone and unassigned zone
        Set<Dataset> waterForZone = designDatasetsForZones?.findAll {
            FrenchConstants.TOTAL_CONSUMED_WATER_M3_QUESTIONID == it.questionId &&
                    FrenchConstants.WATER_SECTIONID == it.sectionId &&
                    FrenchConstants.ENERGY_AND_WATER_FEC_QUERYID == it.queryId &&
                    datasetService.isDatasetForZone(it, designZoneId)
        }
        setDonneesServiceToContributeur(waterAndRefrigerantsContributeur, waterForZone, 0, FrenchConstants.ContributeurRefFec.WATER_REFRIGERANT)
        zone.getContributeur().add(waterAndRefrigerantsContributeur)
    }

    private void setDonneesServiceToContributeur(RSEnv.EntreeProjet.Batiment.Zone.Contributeur contributeur,
                                                 Set<Dataset> datasetsForZoneAndBatiment,
                                                 Integer fallBackQuantity, FrenchConstants.ContributeurRefFec contributeurRef) {
        if (!datasetsForZoneAndBatiment) {
            return
        }

        ResourceCache resourceCache = ResourceCache.init(datasetsForZoneAndBatiment.toList())
        for (Dataset dataset in datasetsForZoneAndBatiment) {
            Resource resource = resourceCache.getResource(dataset)
            if (!resource) {
                continue
            }

            DonneesService donneesService = new DonneesService()
            String epdINIES = resource.epdNumberAlt ?: optimiResourceService.getResourceByDbId(resource.id?.toString())?.epdNumberAlt ?: FrenchConstants.ID_PRODUIT_DUMMY

            donneesService.setIdBase(FrenchConstants.ID_BASE_INIES)
            donneesService.setIdProduit(epdINIES)

            if (resource.epdNumber) {
                try {
                    String idFiche = resource.epdNumber?.replaceAll("[^0-9]", "")?.toString()
                    if (!idFiche || "0" == idFiche) {
                        idFiche = FrenchConstants.ID_FICHE_DUMMY
                    }
                    donneesService.setIdFiche(idFiche)
                } catch (Exception e) {
                    loggerUtil.error(log, "XML Generation: Resource: ${resource.resourceId} / ${resource.profileId} has an invalid epdNumber: ${resource.epdNumber}", e)
                    flashService.setErrorAlert("XML Generation: Resource: ${resource.resourceId} / ${resource.profileId} has an invalid epdNumber: ${resource.epdNumber}, ${e.getMessage()} ", Boolean.TRUE)
                }
            } else {
                donneesService.setIdFiche(FrenchConstants.ID_FICHE_DUMMY)
            }
            BigDecimal quantite = calculateQuantite(dataset.quantity, fallBackQuantity)
            donneesService.setQuantite(quantite)
            String commentAddQ = dataset.additionalQuestionAnswers?.get(Constants.COMMENT_QUESTIONID)?.toString()?.replaceAll("[-,\\s.]", "")
            if (commentAddQ) {
                donneesService.setCommentaire(dataset.additionalQuestionAnswers?.get(Constants.COMMENT_QUESTIONID)?.toString())
            }
            donneesService.setUniteUf(datasetService.getUniteUfFromDataset(dataset))

            if (FrenchConstants.ContributeurRefFec.ENERGY == contributeurRef) {
                setEnergieVecteurToDonneesService(donneesService, resource)
                setEnergieUsageToDonneesService(donneesService, dataset)
            } else if (FrenchConstants.ContributeurRefFec.WATER_REFRIGERANT == contributeurRef) {
                setEauTypeToDonneesService(donneesService, dataset)
            } else if (FrenchConstants.ContributeurRefFec.CONSTRUCTION == contributeurRef) {
                setChantierConsommationsToDonneesService(donneesService, dataset, resource, contributeur)
            }

            contributeur.getDonneesService().add(donneesService)
        }
    }

    /**
     * We set the transported landfill resource for ChantierConsommations 12 as donnees service, with data: quantity, unite, and ChantierConsommations 11
     * Exception: if the resource is imported earth, we do not make copy
     * Basically create a copy, change some data and add to contributeur
     * @param contributeur
     * @param dataset
     */
    private void setCopyOfLandfillAsDonneesService(RSEnv.EntreeProjet.Batiment.Zone.Contributeur contributeur, DonneesService donneesService, Dataset dataset) {
        if (!contributeur || !donneesService || dataset?.resourceId == FrenchConstants.IMPORTED_EARTH_RESOURCEID) {
            return
        }

        DonneesService copy = new DonneesService()
        copy.setChantierConsommations(FrenchConstants.ChantierConsommationsRef.ELEVEN.ref)
        copy.setUniteUf(datasetService.getUniteUfFromDataset(dataset))
        copy.setQuantite(calculateQuantite(dataset.quantity))
        copy.setIdBase(donneesService.getIdBase())
        copy.setIdFiche(donneesService.getIdFiche())
        copy.setIdProduit(donneesService.getIdProduit())
        copy.setEnergieUsage(donneesService.getEnergieUsage())
        copy.setEnergieVecteur(donneesService.getEnergieVecteur())
        copy.setEauType(donneesService.getEauType())
        copy.setCommentaire(donneesService.getCommentaire())
        contributeur.getDonneesService().add(copy)
    }

    /**
     * For Earthmasses section, quantity of dataset should be multiplied by the transport distance (REL-625)
     * unit is tonkm
     * Only for ChantierConsommations 12
     * @param donneesService
     * @param dataset
     */
    @CompileStatic(TypeCheckingMode.SKIP)
    private void setEarthWorkQuantitie(DonneesService donneesService, Dataset dataset) {
        if (FrenchConstants.EARTH_MASSES_SECTIONID == dataset?.sectionId && FrenchConstants.DUMP_TRUCK_TRANSPORT_RESOURCEID == datasetService.getAdditionalAnswerFromDataset(dataset, FrenchConstants.TRANSPORT_RESOURCE_ID_FEC_QUESTIONID)) {
            Double transportDistance = datasetService.getAdditionalAnswerFromDatasetAsDouble(dataset, FrenchConstants.TRANSPORT_DISTANCE_KM_FEC_QUESTIONID)
            if (transportDistance != null) {
                Double qty = unitConversionUtil.convertQuantity(datasetService.getResource(dataset), dataset.quantity, datasetService.getThickness(dataset.additionalQuestionAnswers), dataset.userGivenUnit, Constants.Unit.TON.unit)
                if (qty != null && donneesService) {
                    BigDecimal qtyBd = calculateQuantite(qty)
                    qtyBd *= transportDistance
                    donneesService.setQuantite(calculateQuantite(qtyBd))
                    donneesService.setUniteUf(FrenchConstants.UNITE_UFS.get(Constants.Unit.TONKM.unit))
                }
            }

        }
    }

    private void setChantierConsommationsToDonneesService(DonneesService donneesService, Dataset dataset, Resource resource,
                                                          RSEnv.EntreeProjet.Batiment.Zone.Contributeur contributeur) {
        if (FrenchConstants.CONSTRUCTION_ENERGY_USE_QUESTIONID == dataset.questionId) {
            if (dataset.resourceId == FrenchConstants.DIESEL_FUEL_RESOURCEID) {
                donneesService.setChantierConsommations(FrenchConstants.ChantierConsommationsRef.THIRTEEN.ref)
            } else if (resource?.resourceGroup) {
                for (String group in resource.resourceGroup) {
                    if (FrenchConstants.ChantierConsommationsRef.RESOURCE_GROUP_REF.containsKey(group)) {
                        donneesService.setChantierConsommations(FrenchConstants.ChantierConsommationsRef.RESOURCE_GROUP_REF.get(group))
                        return
                    }
                }
            }
        } else if (FrenchConstants.CONSTRUCTION_WATER_USE_QUESTIONID == dataset.questionId) {
            // Reusing the resourceIds defined in the SousContributeur enum
            if (dataset.resourceId in FrenchConstants.SousContributeurRef.EAU.drinkingWaterResourceIds()) {
                donneesService.setChantierConsommations(FrenchConstants.ChantierConsommationsRef.NINE.ref)
            } else if (dataset.resourceId in FrenchConstants.SousContributeurRef.EAU.wasteWaterResourceIds() || dataset.resourceId in FrenchConstants.SousContributeurRef.EAU.rainWaterResourceIds()) {
                donneesService.setChantierConsommations(FrenchConstants.ChantierConsommationsRef.TEN.ref)
            }
        } else if (FrenchConstants.EARTH_MASSES_QUESTIONID == dataset.questionId) {
            if (dataset.resourceId == FrenchConstants.EXCAVATED_LAND_RESOURCEID) {
                donneesService.setChantierConsommations(FrenchConstants.ChantierConsommationsRef.THIRTEEN.ref)
            } else if (dataset.resourceId in FrenchConstants.TRANSPORTED_EARTH_RESOURCEIDS) {
                donneesService.setChantierConsommations(FrenchConstants.ChantierConsommationsRef.TWELVE.ref)
                setEarthWorkQuantitie(donneesService, dataset)
                setCopyOfLandfillAsDonneesService(contributeur, donneesService, dataset)
            }
        }
    }

    private static void setEauTypeToDonneesService(DonneesService donneesService, Dataset dataset) {
        // the ref are same as for SousContributeur in RE2020 hence we use the same enum
        if (dataset.resourceId in FrenchConstants.SousContributeurRef.EAU.drinkingWaterResourceIds()) {
            donneesService.setEauType(FrenchConstants.SousContributeurRef.EAU.DRINKING_WATER.intValue())
        } else if (dataset.resourceId in FrenchConstants.SousContributeurRef.EAU.wasteWaterResourceIds()) {
            donneesService.setEauType(FrenchConstants.SousContributeurRef.EAU.WASTE_WATER.intValue())
        } else if (dataset.resourceId in FrenchConstants.SousContributeurRef.EAU.rainWaterResourceIds()) {
            donneesService.setEauType(FrenchConstants.SousContributeurRef.EAU.RAIN_WATER.intValue())
        }
    }

    private static void setEnergieVecteurToDonneesService(DonneesService donneesService, Resource resource) {
        if (!resource?.resourceGroup) {
            return
        }
        for (String rsGroup in resource.resourceGroup) {
            if (FrenchConstants.ENERGIE_VECTEUR_REF.containsKey(rsGroup)) {
                donneesService.setEnergieVecteur(FrenchConstants.ENERGIE_VECTEUR_REF.get(rsGroup))
                break
            }
        }
    }

    private void setEnergieUsageToDonneesService(DonneesService donneesService, Dataset dataset) {
        if (FrenchConstants.ENERGY_SECTIONID == dataset.sectionId && FrenchConstants.ENERGY_PER_SDP_QUESTIONID == dataset.questionId && FrenchConstants.ENERGY_AND_WATER_FEC_QUERYID == dataset.queryId) {
            String usage = datasetService.getAdditionalAnswerFromDataset(dataset, FrenchConstants.ENERGY_USE_RE2020_QUESTIONID)
            if (usage?.isInteger()) {
                donneesService.setEnergieUsage(usage.toInteger())
            }
        }
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    private static BigDecimal calculateQuantite(Double quantity, Integer fallBackQuantity = 0) {
        return Math.round((quantity ?: fallBackQuantity) * 1000000.0) / 1000000.0
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    private TDonneeComposant createDonneeComposantFromResource(Double assessment, Dataset dataset, Resource resource, Entity design, Indicator indicator, DecimalFormat decinalFormat) {
        TDonneeComposant donneesComposant
        if (dataset && resource && design && indicator) {
            try {
                donneesComposant = new TDonneeComposant()
                Double serviceLifeDouble = newCalculationServiceProxy.getServiceLife(dataset, resource, design, indicator)
                BigDecimal serviceLife = numberUtil.answerToBigDecimal(serviceLifeDouble?.toString(), decinalFormat, dataset)

                if (serviceLife == -1 && assessment) {
                    serviceLife = assessment.toBigDecimal()
                }
                String epdINIES = resource.epdNumberAlt ?: optimiResourceService.getResourceByDbId(resource.id?.toString())?.epdNumberAlt ?: FrenchConstants.ID_PRODUIT_DUMMY

                if (resource.environmentDataSourceType == FrenchConstants.PRIVATE_DATA_SOURCE) {
                    def epdc = xmlService.getEpdcFromResource(resource)
                    setDonneesConfigurateurToDonneeComposant(donneesComposant, epdc)
                }

                donneesComposant.setIdBase(FrenchConstants.ID_BASE_INIES)
                donneesComposant.setIdProduit(epdINIES)

                if (resource.epdNumber) {
                    String idFiche = resource.epdNumber?.replaceAll("[^0-9]", "")?.toString()
                    if (!idFiche || "0".equals(idFiche)) {
                        idFiche = FrenchConstants.ID_FICHE_DUMMY
                    }
                    donneesComposant.setIdFiche(idFiche)
                } else {
                    donneesComposant.setIdFiche(FrenchConstants.ID_FICHE_DUMMY)
                }
                donneesComposant.setNom(stringUtilsService.abbr(resource.nameFR ?: resource.nameEN, 500, true))
                donneesComposant.setQuantite(Math.round((dataset.quantity ?: 1) * 1000000.0) / 1000000.0)
                donneesComposant.setDve(serviceLife ?: FrenchConstants.TOTAL_ASSESSMENT)
                donneesComposant.setTypeDonnees(FrenchConstants.TYPE_DONNEES.indexOf(resource.environmentDataSource) + 1)
                String commentAddQ = dataset.additionalQuestionAnswers?.get(Constants.COMMENT_QUESTIONID)?.toString()?.replaceAll("[-,\\s.]", "")
                if (commentAddQ) {
                    donneesComposant.setCommentaire(dataset.additionalQuestionAnswers?.get(Constants.COMMENT_QUESTIONID)?.toString())
                }
                donneesComposant.setUniteUf(datasetService.getUniteUfFromDataset(dataset))
            } catch (e) {
                loggerUtil.error(log, "createDonneeComposantFromResource error when extracting value from dataset & resource ${e.getMessage()}", e)
                flashService.setErrorAlert("Error in createDonneeComposantFromResource: ${e.getMessage()} ", Boolean.TRUE)
            }

        }
        return donneesComposant
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    private static setDonneesConfigurateurToDonneeComposant(TDonneeComposant donneeComposant, def epdc) {
        if (epdc) {
            donneeComposant.setVersionConfigurateur(epdc.getConfiguratorVersion())
            donneeComposant.setIdBase(epdc.configuratorCode?.toInteger())
            TDonneeComposant.DonneesConfigurateur donneesConfigurateur = new TDonneeComposant.DonneesConfigurateur()
            for (def epdcParameter in epdc.parameters.parameter) {
                if (epdc instanceof Re2020EPDC) {
                    setRe2020EpdcParameterToDonneesConfigurateur(donneesConfigurateur, epdcParameter as Re2020EPDC.Parameters.Parameter)
                } else if (epdc instanceof FecEPDC) {
                    setFecEpdcParameterToDonneesConfigurateur(donneesConfigurateur, epdcParameter as FecEPDC.Parameters.Parameter)
                }
            }
            donneeComposant.setDonneesConfigurateur(donneesConfigurateur)
        }
    }

    @CompileStatic
    private static setRe2020EpdcParameterToDonneesConfigurateur(TDonneeComposant.DonneesConfigurateur donneesConfigurateur, Re2020EPDC.Parameters.Parameter epdcParameter) {
        TDonneeComposant.DonneesConfigurateur.Parametre parametre = new TDonneeComposant.DonneesConfigurateur.Parametre()
        parametre.setNumero(epdcParameter.parameterId?.intValue())
        parametre.setNom(epdcParameter.name)
        parametre.setValeur(epdcParameter.value)
        parametre.setUnite(epdcParameter.paramUnitId?.toString())
        donneesConfigurateur.getParametre().add(parametre)
    }

    @CompileStatic
    private static setFecEpdcParameterToDonneesConfigurateur(TDonneeComposant.DonneesConfigurateur donneesConfigurateur, FecEPDC.Parameters.Parameter epdcParameter) {
        TDonneeComposant.DonneesConfigurateur.Parametre parametre = new TDonneeComposant.DonneesConfigurateur.Parametre()
        parametre.setNumero(epdcParameter.parameterId?.replaceAll("[^0-9]", '')?.toInteger())
        parametre.setNom(epdcParameter.description)
        parametre.setValeur(epdcParameter.value?.toString())
        parametre.setUnite(epdcParameter.unitId?.toString())
        donneesConfigurateur.getParametre().add(parametre)
    }
}
