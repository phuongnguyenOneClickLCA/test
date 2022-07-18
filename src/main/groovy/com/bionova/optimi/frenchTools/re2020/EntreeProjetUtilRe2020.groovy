package com.bionova.optimi.frenchTools.re2020

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.service.DatasetService
import com.bionova.optimi.core.service.OptimiResourceService
import com.bionova.optimi.core.service.Re2020Service
import com.bionova.optimi.core.service.StringUtilsService
import com.bionova.optimi.core.service.XmlService
import com.bionova.optimi.data.ResourceCache
import com.bionova.optimi.frenchTools.FrenchConstants
import com.bionova.optimi.frenchTools.FrenchStore
import com.bionova.optimi.frenchTools.helpers.BatimentArea
import com.bionova.optimi.frenchTools.helpers.DatasetGroup
import com.bionova.optimi.frenchTools.helpers.DonneeComposantPerLotSousLot
import com.bionova.optimi.frenchTools.helpers.DonneeComposantPerSousLot
import com.bionova.optimi.frenchTools.helpers.ZoneArea
import com.bionova.optimi.util.NumberUtil
import com.bionova.optimi.xml.fecEPD.EPDC as FecEPDC
import com.bionova.optimi.xml.re2020EPD.EPDC
import com.bionova.optimi.xml.re2020EPD.EPDC as Re2020EPDC
import com.bionova.optimi.xml.re2020RSEnv.RSEnv
import com.bionova.optimi.xml.re2020RSEnv.RSEnv.EntreeProjet.Batiment.Zone
import com.bionova.optimi.xml.re2020RSEnv.TCalculetteChantier
import com.bionova.optimi.xml.re2020RSEnv.TCalculetteEau
import com.bionova.optimi.xml.re2020RSEnv.TDonneeComposant
import com.bionova.optimi.xml.re2020RSEnv.TDonneesService
import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode

import javax.xml.datatype.DatatypeFactory
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat

// FOR RE2020
class EntreeProjetUtilRe2020 {
    DatasetService datasetService
    NumberUtil numberUtil
    XmlService xmlService
    Re2020Service re2020Service
    StringUtilsService stringUtilsService
    def newCalculationServiceProxy
    OptimiResourceService optimiResourceService

    private static final int PERIODE_REFERENCE = 50
    private static final BigDecimal TOTAL_ASSESSMENT = BigDecimal.valueOf(50d)
    // dummy values
    private static final String NONE = "None"


    void setEntreeProjetToRSEnv(RSEnv rsEnv, FrenchStore store) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd")
        DatatypeFactory xmlGregorianCalendarInstance = DatatypeFactory.newInstance()
        RSEnv.EntreeProjet entreeProjet = new RSEnv.EntreeProjet()
        Date timestamp = new Date()
        entreeProjet.setDateEtude(DatatypeFactory.newInstance().newXMLGregorianCalendar(timestamp.format("yyyy-MM-dd")))
        setReseauToEntreeProjet(entreeProjet, dateFormat, xmlGregorianCalendarInstance, store)

        for (Entity design in store.designs) {
            String designId = design.id.toString()
            boolean hasParcelle = designId == store.parcelle.designId
            // set batiment if design does not have Parcelle, or the design has parcelle but it also have data for batiment from mapping.
            boolean setBatiment = !hasParcelle || (hasParcelle && !store.parcelle.isStandAlone)
            if (setBatiment) {
                setBatimentToEntreeProjet(entreeProjet, designId, design, store)
            }
            if (hasParcelle) {
                setParcelleToEntreeProjet(entreeProjet, designId, design, store)
            }
        }
        rsEnv.setEntreeProjet(entreeProjet)
    }

    private void setParcelleToEntreeProjet(RSEnv.EntreeProjet entreeProjet, String designId, Entity design, FrenchStore store) {
        RSEnv.EntreeProjet.Parcelle parcelle = new RSEnv.EntreeProjet.Parcelle()
        RSEnv.EntreeProjet.Parcelle.Contributeur contributeur = new RSEnv.EntreeProjet.Parcelle.Contributeur()
        RSEnv.EntreeProjet.Parcelle.Contributeur.Composant composant = new RSEnv.EntreeProjet.Parcelle.Contributeur.Composant()
        RSEnv.EntreeProjet.Parcelle.Contributeur.Chantier chantier = new RSEnv.EntreeProjet.Parcelle.Contributeur.Chantier()
        RSEnv.EntreeProjet.Parcelle.Contributeur.Eau eau = new RSEnv.EntreeProjet.Parcelle.Contributeur.Eau()

        parcelle.setSurfaceParcelle(store.parcelle.surfaceParcelle)
        parcelle.setSurfaceArrosee(store.parcelle.surfaceArrosee)
        parcelle.setSurfaceImper(store.parcelle.surfaceImper)
        parcelle.setSurfaceVeg(store.parcelle.surfaceVeg)

        Set<Dataset> datasetsOfParcelle = store.datasetGroups.find { it.designId == designId }?.datasetsPerZone?.get(FrenchConstants.ZONE_PARCELLE)
        Double assessment = store.assessmentValuePerDesign.get(designId)
        if (datasetsOfParcelle) {
            for (Dataset dataset in datasetsOfParcelle) {
                switch (dataset.queryId) {
                    case Constants.BUILDING_MATERIALS_QUERYID:
                        TDonneeComposant donneeComposant = createDonneeComposant(dataset, design, assessment, store)
                        if (donneeComposant) {
                            composant.getDonneesComposant().add(donneeComposant)
                        }
                        break
                    case FrenchConstants.CONSTRUCTION_FEC_QUERYID:
                        TDonneesService donneesService = createDonneeService(dataset)
                        if (donneesService) {
                            chantier.getDonneesService().add(donneesService)
                        }
                        break
                    case FrenchConstants.ENERGY_AND_WATER_FEC_QUERYID:
                        if (FrenchConstants.WATER_SECTIONID == dataset.sectionId) {
                            TDonneesService donneesService = createDonneeService(dataset)
                            if (donneesService) {
                                eau.getDonneesService().add(donneesService)
                            }
                        }
                        break

                }
            }
        }
        contributeur.setComposant(composant)
        contributeur.setChantier(chantier)
        contributeur.setEau(eau)
        parcelle.setContributeur(contributeur)
        entreeProjet.setParcelle(parcelle)
    }

    private void setReseauToEntreeProjet(RSEnv.EntreeProjet entreeProjet, SimpleDateFormat dateFormat,
                                         DatatypeFactory xmlGregorianCalendarInstance, FrenchStore store) {

        DecimalFormat decimalFormat = store.decimalFormat
        DatasetGroup reseauDatasetGroup = store.datasetGroups?.find { it.datasetsPerQuery != null && it.datasetsPerQuery.get(FrenchConstants.ENERGY_AND_WATER_FEC_QUERYID) != null }
        Set<Dataset> reseauQueryDatasets = reseauDatasetGroup?.datasetsPerQuery?.get(FrenchConstants.ENERGY_AND_WATER_FEC_QUERYID)
        Resource reseauResource = reseauQueryDatasets?.find{
            FrenchConstants.ENERGY_SECTIONID == it.sectionId && FrenchConstants.ENERGY_PER_SDP_QUESTIONID == it.questionId && it.calculationResource?.resourceGroup?.contains(FrenchConstants.NETWORK_RE2020)
        }?.calculationResource

        if (reseauResource) {
            RSEnv.EntreeProjet.Reseau reseau = new RSEnv.EntreeProjet.Reseau()
            reseau.setIdentifiant(reseauResource.epdNumber)
            reseau.setType(reseauResource.physicalPropertiesSource as int)
            reseau.setNom(reseauResource.nameFR)
            reseau.setLocalisation(reseauResource.technicalSpec)
            reseau.setContenuCo2(reseauResource.impactGWP100_kgCO2e as float)
            entreeProjet.getReseau().add(reseau)
        }

        if (store.parentEntity?.datasets) {
            for (Dataset dataset in store.parentEntity.datasets) {
                if (FrenchConstants.FEC_QUERYID_PROJECT_LEVEL == dataset.queryId) {
                    String answer = datasetService.getAnswerFromDataset(dataset, ["-", "_"])

                    if (!answer) {
                        continue
                    }

                    if (FrenchConstants.LES_ACTEURS_DU_PROJET_RE2020_SECTIONID == dataset.sectionId) {
                        switch (dataset.questionId) {
                            case FrenchConstants.DATE_ETUDE_QUESTIONID:
                                entreeProjet.setDateEtude(xmlService.answerToXMLGregorianCalendar(answer, dateFormat, xmlGregorianCalendarInstance, dataset))
                                break
                        }
                    } else if (FrenchConstants.DESCRIPTION_PROJET_ET_SES_CONTRAINTES_SECTIONID == dataset.sectionId) {
                        switch (dataset.questionId) {
                            case FrenchConstants.SURFACE_ARROSEE_QUESTIONID:
                                store.parcelle.surfaceArrosee = numberUtil.answerToInteger(answer, decimalFormat, dataset)
                                break
                            case FrenchConstants.SURFACE_VEG_QUESTIONID:
                                store.parcelle.surfaceVeg = numberUtil.answerToInteger(answer, decimalFormat, dataset)
                                break
                            case FrenchConstants.SURFACE_IMPER_QUESTIONID:
                                store.parcelle.surfaceImper = numberUtil.answerToInteger(answer, decimalFormat, dataset)
                                break
                        }
                    } else if (FrenchConstants.DONNEES_ADMINISTRATIVES_SECTIONID == dataset.sectionId) {
                        switch (dataset.questionId) {
                            case FrenchConstants.SURFACE_PARCELLE_QUESTIONID:
                                store.parcelle.surfaceParcelle = numberUtil.answerToInteger(answer, decimalFormat, dataset)
                                break
                        }
                    }
                }
            }
        }
    }

    @CompileStatic
    private void setBatimentToEntreeProjet(RSEnv.EntreeProjet entreeProjet, String designId, Entity design, FrenchStore store) {
        DecimalFormat decimalFormat = store.decimalFormat
        RSEnv.EntreeProjet.Batiment batiment = new RSEnv.EntreeProjet.Batiment()
        Integer batimentIndex = store.batimentIndexMappings.get(designId)
        batiment.setNom(design.name)
        batiment.setIndex(batimentIndex)
        batiment.setPeriodeReference(FrenchConstants.PERIODE_REFERENCE)
        BatimentArea batimentArea = store.srefPerDesign.find { it.designId == designId }
        BigDecimal sref = batimentArea?.sref?.toBigDecimal() ?: BigDecimal.ZERO
        batiment.setSref(sref)

        for (Dataset dataset in store.datasetGroups?.find { it.designId == designId }?.datasetsPerQuery?.get(FrenchConstants.PROJECT_DESCRIPTION_FEC_QUERYID)) {
            if (FrenchConstants.DESCRIPTION_BATIMENT_SECTIONID == dataset.sectionId) {
                String answer = datasetService.getAnswerFromDataset(dataset)

                if (!answer) {
                    continue
                }

                switch (dataset.questionId) {
                    case FrenchConstants.EMPRISE_AU_SOL_QUESTIONID:
                        batiment.setEmpriseAuSol(numberUtil.answerToBigDecimal(answer, decimalFormat, dataset))
                        break
                    case FrenchConstants.DUREE_CHANTIER_QUESTIONID:
                        batiment.setDureeChantier(numberUtil.answerToBigDecimal(answer, decimalFormat, dataset))
                        break
                }
            }
        }
        setZonesToBatiment(batiment, design, designId, store, batimentArea)
        entreeProjet.getBatiment().add(batiment)
    }

    @CompileStatic
    private void setZonesToBatiment(RSEnv.EntreeProjet.Batiment batiment, Entity design, String designId,
                                    FrenchStore store, BatimentArea batimentArea) {
        Map<String, Integer> zones = store.batimentZoneIndexMappings.get(design.id.toString())
        Double assessment = store.assessmentValuePerDesign.get(designId)
        DatasetGroup datasetGroup = store.datasetGroups.find { it.designId == designId }

        zones?.each { String designZoneId, Integer batimentZoneIndex ->
            // parcelle zone is not included here
            if (designZoneId != FrenchConstants.ZONE_PARCELLE) {
                Zone zone = new Zone()

                if (batimentZoneIndex) {
                    zone.setIndex(batimentZoneIndex)
                }
                Set<Dataset> datasetsOfThisZone = datasetGroup?.datasetsPerZone?.get(designZoneId)
                ZoneArea zoneArea = batimentArea?.zoneAreas?.find { it.zoneId == designZoneId }

                if (datasetsOfThisZone) {
                    setSrefRelatedFieldsToZone(zone, datasetsOfThisZone, store.decimalFormat, designZoneId, zoneArea)
                    setCalculetteEauToZone(zone)
                    setCalculetteChantierToZone(zone)
                    setContributeurToZone(zone, designZoneId, datasetsOfThisZone, design, assessment, store, datasetGroup)
                }
                batiment.getZone().add(zone)
            }
        }
    }

    private void setContributeurToZone(Zone zone, String zoneId, Set<Dataset> datasetsOfThisZone, Entity design,
                                       Double assessment, FrenchStore store, DatasetGroup datasetGroup) {
        Map<String, Set<Dataset>> buildingDatasetsPerLot = [:]
        for (Dataset dataset in datasetsOfThisZone) {
            if (Constants.BUILDING_MATERIALS_QUERYID == dataset.queryId) {
                String lotId = datasetService.getLotOfDataset(dataset)
                datasetService.groupDatasetSameKey(dataset, lotId, buildingDatasetsPerLot)
            }
        }

        Zone.Contributeur contributeur = new Zone.Contributeur()
        Zone.Contributeur.Composant composant = new Zone.Contributeur.Composant()
        Zone.Contributeur.Chantier chantier = new Zone.Contributeur.Chantier()
        Zone.Contributeur.Energie energie = new Zone.Contributeur.Energie()
        Zone.Contributeur.Eau eau = new Zone.Contributeur.Eau()

        setLotAndSousLotToComposant(composant, buildingDatasetsPerLot, design, assessment, store)

        // for chantier, only site materials get to set the donnee composant, all others are to be set donnee service
        datasetGroup?.siteDatasetsPerCategory?.each { Integer categoryRef, Set<Dataset> siteDatasetsSameCategory ->
            setSousContributeurToChantier(chantier, categoryRef, categoryRef == FrenchConstants.SousContributeurRef.CHANTIER.SITE_MATERIALS.intValue(), siteDatasetsSameCategory, store, design, assessment, zoneId)
        }

        datasetGroup?.energyDatasetsPerUse?.each { Integer energyUseRef, Set<Dataset> eneryDatasetsSameUse ->
            setSousContributeurToEnergie(energie, energyUseRef, eneryDatasetsSameUse, zoneId)
        }

        datasetGroup?.waterDatasetsPerType?.each { Integer waterTypeRef, Set<Dataset> waterDatasetsSameType ->
            setSousContributeurToEau(eau, waterTypeRef, waterDatasetsSameType, zoneId)
        }

        contributeur.setComposant(composant)
        contributeur.setChantier(chantier)
        contributeur.setEnergie(energie)
        contributeur.setEau(eau)
        zone.setContributeur(contributeur)
    }
    /**
     * Set sous contributeur to eau, zone level
     * @param eau
     * @param ref
     * @param datasetsPerBatiment datasets of entire batiment (design) that have already been grouped, now just need to select those that belong to zone
     * @param zoneId
     */
    @CompileStatic
    private void setSousContributeurToEau(Zone.Contributeur.Eau eau,
                                          Integer ref, Set<Dataset> datasetsPerBatiment, String zoneId) {
        if (!zoneId || !datasetsPerBatiment) {
            return
        }
        Zone.Contributeur.Eau.SousContributeur sousContributeur = new Zone.Contributeur.Eau.SousContributeur()
        sousContributeur.setRef(ref)

        boolean hasData = false
        if (datasetsPerBatiment) {
            for (Dataset dataset in datasetsPerBatiment) {
                if (datasetService.getZoneOfDataset(dataset) != zoneId) {
                    continue
                }

                hasData = true
                TDonneesService donneesService = createDonneeService(dataset)
                sousContributeur.getDonneesService().add(donneesService)
            }
        }

        if (!hasData) {
            // don't set sousContributeur if it doesn't have any inner data
            return
        }
        eau.getSousContributeur().add(sousContributeur)
    }

    /**
     * Set sous contributeur to energie, zone level
     * @param energie
     * @param ref
     * @param datasetsPerBatiment datasets of entire batiment (design) that have already been grouped, now just need to select those that belong to zone
     * @param zoneId
     */
    @CompileStatic
    private void setSousContributeurToEnergie(Zone.Contributeur.Energie energie,
                                              Integer ref, Set<Dataset> datasetsPerBatiment, String zoneId) {
        if (!zoneId || !datasetsPerBatiment) {
            return
        }

        Zone.Contributeur.Energie.SousContributeur sousContributeur = new Zone.Contributeur.Energie.SousContributeur()
        sousContributeur.setRef(ref)
        boolean hasData = false
        for (Dataset dataset in datasetsPerBatiment) {
            if (datasetService.getZoneOfDataset(dataset) != zoneId) {
                continue
            }

            hasData = true
            TDonneesService donneesService = createDonneeService(dataset)
            sousContributeur.getDonneesService().add(donneesService)
        }

        if (!hasData) {
            // don't set sousContributeur if it doesn't have any inner data
            return
        }
        energie.getSousContributeur().add(sousContributeur)
    }

    /**
     * Set sous contributeur to chantier, zone level
     * @param chantier
     * @param ref
     * @param createComposant
     * @param datasetsPerBatiment datasets of entire batiment (design) that have already been grouped, now just need to select those that belong to zone
     * @param store
     * @param design
     * @param assessment
     * @param zoneId
     */
    @CompileStatic
    private void setSousContributeurToChantier(Zone.Contributeur.Chantier chantier, Integer ref,
                                               Boolean createComposant, Set<Dataset> datasetsPerBatiment, FrenchStore store,
                                               Entity design, Double assessment, String zoneId) {
        if (!zoneId || !datasetsPerBatiment) {
            return
        }

        Zone.Contributeur.Chantier.SousContributeur sousContributeur = new Zone.Contributeur.Chantier.SousContributeur()
        sousContributeur.setRef(ref)

        boolean hasData = false

        for (Dataset dataset in datasetsPerBatiment) {
            if (datasetService.getZoneOfDataset(dataset) != zoneId) {
                continue
            }

            hasData = true
            if (createComposant) {
                TDonneeComposant donneeComposant = createDonneeComposant(dataset, design, assessment, store)
                sousContributeur.getDonneesComposant().add(donneeComposant)
            } else {
                TDonneesService donneesService = createDonneeService(dataset)
                sousContributeur.getDonneesService().add(donneesService)
            }
        }

        if (!hasData) {
            // don't set sousContributeur if it doesn't have any inner data
            return
        }

        chantier.getSousContributeur().add(sousContributeur)
    }

    /**
     * <contributeur>
     *     <composant>
     *         <lot>
     *             <donnees_composant></donnees_composant>
     *         </lot>
     *     </composant>
     * </contributeur>
     *
     * <contributeur>
     *     <composant>
     *         <lot>
     *             <sous_lot>
     *                 <donnees_composant></donnees_composant>
     *             </sous_lot>
     *         </lot>
     *     </composant>
     * </contributeur>
     */
    private void setLotAndSousLotToComposant(Zone.Contributeur.Composant composant,
                                             Map<String, Set<Dataset>> datasetsPerLot, Entity design, Double assessment, FrenchStore store) {

        List<Zone.Contributeur.Composant.Lot> allLots = []
        List<DonneeComposantPerLotSousLot> allDonneeComposantPerLotSousLots = []

        datasetsPerLot?.each { String lotId, Set<Dataset> datasetsSameLot ->
            if (lotId.isInteger()) {
                Zone.Contributeur.Composant.Lot lot = new Zone.Contributeur.Composant.Lot()
                DonneeComposantPerLotSousLot dcPerLotSousLot = new DonneeComposantPerLotSousLot(lotId: lotId, donneeComposantPerSousLots: [])
                lot.setRef(Integer.valueOf(lotId))

                // one dataset can have one lot and another sous lot, hence need to combine all datasets with same sous lot as well (each would be a donneeComposant)
                ResourceCache resourceCache = ResourceCache.init(datasetsSameLot.toList())
                for (Dataset dataset in datasetsSameLot) {
                    if (resourceCache.getResource(dataset)?.construction) {
                        /* No need to export for construction level, keep code just in case
                        Construction construction = constructionService.getConstructionWithConstructionId(dataset.resource.constructionId)
                        if (construction) {
                            List<Dataset> constituentDatasets = construction.datasets
                            newCalculationServiceProxy.initResources(constituentDatasets)
                            for (Dataset constituent in constituentDatasets) {
                                TDonneeComposant donneeComposant = createDonneeComposant(dataset, design, indicator, decimalFormat, assessment)
                                if (donneeComposant) {
                                    setDonneesComposantToSousLot(lot, dataset, donneeComposant)
                                    lot.getDonneesComposant().add(donneeComposant)
                                }
                            }
                        }
                         */
                    } else {
                        TDonneeComposant donneeComposant = createDonneeComposant(dataset, design, assessment, store)
                        String sousLotId = datasetService.getSousLotOfDataset(dataset)

                        if (!donneeComposant) {
                            continue
                        }
                        // DO NOT REMOVE THE NEXT LINE. WE TEMPORARILY DEACTIVATE THIS FOR NOW. REL-624
                        // lot.getDonneesComposant().add(donneeComposant)

                        if (!sousLotId) {
                            continue
                        }

                        Integer existingSousLot = dcPerLotSousLot.donneeComposantPerSousLots?.findIndexOf { it.sousLotId == sousLotId }
                        if (existingSousLot >= 0) {
                            dcPerLotSousLot.donneeComposantPerSousLots[existingSousLot].donneeComposants.add(donneeComposant)
                        } else {
                            DonneeComposantPerSousLot dcPerSousLot = new DonneeComposantPerSousLot(sousLotId: sousLotId)
                            dcPerSousLot.donneeComposants = [donneeComposant]
                            dcPerLotSousLot.donneeComposantPerSousLots.add(dcPerSousLot)
                        }
                    }
                }
                allLots.add(lot)
                allDonneeComposantPerLotSousLots.add(dcPerLotSousLot)
            }
        }

        if (allDonneeComposantPerLotSousLots) {
            for (DonneeComposantPerLotSousLot dcPerLotSousLot in allDonneeComposantPerLotSousLots) {
                Zone.Contributeur.Composant.Lot lot = allLots.find { dcPerLotSousLot.lotId?.toInteger() == it.getRef() }
                if (!dcPerLotSousLot.donneeComposantPerSousLots) {
                    continue
                }

                for (DonneeComposantPerSousLot dcPerSousLot in dcPerLotSousLot.donneeComposantPerSousLots) {
                    Zone.Contributeur.Composant.Lot.SousLot sousLot = new Zone.Contributeur.Composant.Lot.SousLot()
                    sousLot.setRef(dcPerSousLot.sousLotId)
                    if (dcPerSousLot.donneeComposants) {
                        sousLot.getDonneesComposant().addAll(dcPerSousLot.donneeComposants)
                    }
                    lot.getSousLot().add(sousLot)
                }
            }
        }

        composant.getLot().addAll(allLots)
    }

    private static String getIdFicheFromResource(Resource resource) {
        String idFiche = FrenchConstants.ID_FICHE_DUMMY
        String epdNo = resource?.epdNumberAlt ?: resource?.epdNumber
        if (epdNo) {
            epdNo = epdNo.replaceAll("[^0-9]", '')?.toString()

            if (epdNo && '0' != epdNo) {
                idFiche = epdNo
            }
        }
        return idFiche
    }

    private TDonneesService createDonneeService(Dataset dataset) {
        Resource resource = datasetService.getResource(dataset)

        if (!resource) {
            return null
        }

        TDonneesService donneesService = new TDonneesService()
        String idFiche = getIdFicheFromResource(resource)

        if (resource.environmentDataSourceType == FrenchConstants.PRIVATE_DATA_SOURCE) {
            def epdc = xmlService.getEpdcFromResource(resource)
            if(epdc && epdc.configuratorCode) {
                donneesService.setIdBase(epdc.configuratorCode.toInteger())
            }
            else{
                donneesService.setIdBase(FrenchConstants.ID_BASE_INIES)
            }

            if (epdc?.getEPDCId()) {
                donneesService.setIdProduit(epdc.getEPDCId())
                idFiche += epdc.getEPDCId()
            }
        } else {
            donneesService.setIdBase(FrenchConstants.ID_BASE_INIES)
        }
        String name = stringUtilsService.abbr(resource.nameFR ?: resource.nameEN, 500, true)
        donneesService.setNom(name)
        donneesService.setIdProduit(resource.epdNumber?.replaceAll("[^0-9]", '')?.toString() ?: FrenchConstants.ID_PRODUIT_DUMMY)
        donneesService.setIdFiche(idFiche)
        donneesService.setUniteUf(datasetService.getUniteUfFromDataset(dataset))
        donneesService.setQuantite(Math.round((dataset.quantity ?: 1) * 1000000.0) / 1000000.0)

        String commentAddQ = dataset.additionalQuestionAnswers?.get(Constants.COMMENT_QUESTIONID)?.toString()?.replaceAll("[-,\\s.]", "")
        if (commentAddQ) {
            donneesService.setCommentaire(dataset.additionalQuestionAnswers?.get(Constants.COMMENT_QUESTIONID)?.toString())
        } else {
            donneesService.setCommentaire(NONE)
        }
        return donneesService
    }


    private TDonneeComposant createDonneeComposant(Dataset dataset, Entity design, Double assessment, FrenchStore store) {
        Resource resource = datasetService.getResource(dataset)

        if (!resource) {
            return null
        }

        TDonneeComposant donneeComposant = new TDonneeComposant()

        String idFiche = getIdFicheFromResource(resource)

        boolean isPrivateResource = resource.environmentDataSourceType == FrenchConstants.PRIVATE_DATA_SOURCE
        boolean isComposantVide = resource.resourceId == FrenchConstants.COMPOSANT_VIDE_RESOURCE_ID

        if (isPrivateResource) {
            Re2020EPDC epdc = xmlService.getEpdcFromResource(resource) as EPDC

            if (epdc?.getEPDCId()) {
                idFiche = epdc.getEPDCId()
                donneeComposant.setIdFicheMere(epdc.parentEPDId.toString())
                donneeComposant.setIdProduit(idFiche)
            }
            setDonneesConfigurateurToDonneeComposant(donneeComposant, epdc)
        } else {
            donneeComposant.setIdBase(FrenchConstants.ID_BASE_INIES)
            donneeComposant.setIdFicheMere(null)
            donneeComposant.setIdProduit(isComposantVide ? FrenchConstants.COMPOSANT_VIDE_ID_PRODUIT : resource.epdNumber?.replaceAll("[^0-9]", '')?.toString() ?: FrenchConstants.ID_PRODUIT_DUMMY)
        }

        donneeComposant.setIdFiche(isComposantVide ? FrenchConstants.COMPOSANT_VIDE_ID_FICHE : idFiche ?: FrenchConstants.ID_FICHE_DUMMY)
        String name = stringUtilsService.abbr(resource.nameFR ?: resource.nameEN, 500, true)
        donneeComposant.setNom(name)
        donneeComposant.setUniteUf(datasetService.getUniteUfFromDataset(dataset))
        Double serviceLifeDouble = newCalculationServiceProxy.getServiceLife(dataset, resource, design, store.indicator)
        BigDecimal serviceLife = numberUtil.answerToBigDecimal(serviceLifeDouble?.toString(), store.decimalFormat, dataset)
        if (serviceLife == -1 && assessment) {
            serviceLife = assessment.toBigDecimal()
        }
        donneeComposant.setDve(serviceLife ?: FrenchConstants.TOTAL_ASSESSMENT)
        donneeComposant.setQuantite(Math.round((dataset.quantity ?: 1) * 1000000.0) / 1000000.0)

        setTypeDonneesToDonneeComposant(donneeComposant, dataset, resource, isPrivateResource, isComposantVide)

        String commentAddQ = dataset.additionalQuestionAnswers?.get(Constants.COMMENT_QUESTIONID)?.toString()?.replaceAll("[-,\\s.]", "")
        if (commentAddQ) {
            donneeComposant.setCommentaire(dataset.additionalQuestionAnswers?.get(Constants.COMMENT_QUESTIONID)?.toString())
        } else {
            donneeComposant.setCommentaire(NONE)
        }

        donneeComposant.setPerfUfFille(null)

        return donneeComposant
    }

    @CompileStatic
    private setTypeDonneesToDonneeComposant(TDonneeComposant donneeComposant, Dataset dataset, Resource resource, boolean isPrivateResource, boolean isComposantVide) {
        // if dataset has addQ ignoreReusedMaterial true then type = 10, otherwise get value from map
        boolean isIgnoredReusedMaterial = Boolean.valueOf(datasetService.getAdditionalAnswerFromDataset(dataset, Constants.IGNORE_REUSED_MATERIAL_QUESTIONID))
        int typeDonnees

        if (isComposantVide) {
            typeDonnees = FrenchConstants.COMPOSANT_VIDE_TYPE_DONNEES
        } else if (isIgnoredReusedMaterial) {
            typeDonnees = FrenchConstants.TYPE_DONNEES_IGNORE_REUSED_MATERIAL_RE2020
        } else if (isPrivateResource) {
            typeDonnees = FrenchConstants.TYPE_DONNEES_PRIVATE_MATERIAL_RE2020
        } else {
            typeDonnees = FrenchConstants.TYPE_DONNEES_RE2020.get(resource.environmentDataSource)
        }

        donneeComposant.setTypeDonnees(typeDonnees)

        if (isIgnoredReusedMaterial) {
            donneeComposant.setIdProduit(FrenchConstants.REUSED_MATERIAL_ID_PRODUIT)
            donneeComposant.setIdFiche(FrenchConstants.REUSED_MATERIAL_ID_FICHE)
        }
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

    /**
     *
     * @param zone
     * @param datasetsOfThisZone
     * @param decimalFormat
     * @param designZoneId
     * @param zoneArea a DTO that hold info about areas of the zone. This is set in {@link BatimentArea#setZoneArea} which is executed in the loop in {@link DataCompUtilRe2020#setBatimentCollectionAndGroupDatasetsToStore}
     */
    @CompileStatic
    private void setSrefRelatedFieldsToZone(Zone zone, Set<Dataset> datasetsOfThisZone,
                                            DecimalFormat decimalFormat, String designZoneId, ZoneArea zoneArea) {
        zone.setSref(zoneArea?.sref?.toBigDecimal())
        Dataset srefDatasetForZone = datasetsOfThisZone?.find { it.queryId == FrenchConstants.PROJECT_DESCRIPTION_FEC_QUERYID && it.sectionId == FrenchConstants.DESCRIPTION_PER_ZONE_RE2020_SECTIONID && it.questionId == FrenchConstants.SREF_QUESTIONID && datasetService.getZoneOfDataset(it) == designZoneId }

        if (!srefDatasetForZone) {
            return
        }

        // projectDescriptionFEC > descriptionPerZoneRE2020> sref > usageRE2020
        String usage = datasetService.getAdditionalAnswerFromDataset(srefDatasetForZone, FrenchConstants.USAGE_RE2020_QUESTIONID)
        List<Resource> usage_principal_resource = optimiResourceService.getResourceProfilesByResourceId(usage)
        if (usage_principal_resource) {
            zone.setUsage(numberUtil.answerToInteger(usage_principal_resource[0]?.profileId?.toString(), decimalFormat, srefDatasetForZone, Boolean.TRUE))
        }

        // projectDescriptionFEC > descriptionPerZoneRE2020> sref > Scombles
        String scombles = datasetService.getAdditionalAnswerFromDataset(srefDatasetForZone, FrenchConstants.SCOMBLES_QUESTIONID)
        zone.setScombles(numberUtil.answerToBigDecimal(scombles, decimalFormat, srefDatasetForZone))

        // projectDescriptionFEC > descriptionPerZoneRE2020> sref > Nocc
        String nocc = datasetService.getAdditionalAnswerFromDataset(srefDatasetForZone, FrenchConstants.NOCC_QUESTIONID)
        zone.setNOcc(numberUtil.answerToBigDecimal(nocc, decimalFormat, srefDatasetForZone))

        // projectDescriptionFEC > descriptionPerZoneRE2020> sref > NL_RE2020
        String nbLogement = datasetService.getAdditionalAnswerFromDataset(srefDatasetForZone, FrenchConstants.NL_RE2020_QUESTIONID)
        zone.setNbLogement(numberUtil.answerToInteger(nbLogement, decimalFormat, srefDatasetForZone))
    }

    @CompileStatic
    private static void setCalculetteEauToZone(Zone zone) {
        // nothing to set yet for now so set dummy values
        TCalculetteEau calculetteEau = new TCalculetteEau()
        BigDecimal zeroBd = BigDecimal.valueOf(0)
        calculetteEau.setReseauCollecte(1)
        calculetteEau.setReseauAssainissement(1)
        calculetteEau.setConsoEpUsageInt(zeroBd)
        calculetteEau.setFEquipement(zeroBd)
        calculetteEau.setTauxPuisSup8M(zeroBd)
        calculetteEau.setQPluviometrie(zeroBd)
        calculetteEau.setSurfBatVeg(zeroBd)
        calculetteEau.setSurfParkImperNc(zeroBd)
        calculetteEau.setSurfToiture(zeroBd)

        TCalculetteEau.Toilettes toilettes = new TCalculetteEau.Toilettes()
        toilettes.setTauxSeches(zeroBd)
        toilettes.setTaux3L6L(zeroBd)
        toilettes.setTaux2L4L(zeroBd)
        toilettes.setTauxUrinoirs(zeroBd)
        toilettes.setTauxSeches(zeroBd)
        toilettes.setTauxUrinoirs(zeroBd)

        TCalculetteEau.RegulateurDebit regulateurDebit = new TCalculetteEau.RegulateurDebit()
        regulateurDebit.setTauxLavabo(zeroBd)
        regulateurDebit.setTauxEvier(zeroBd)
        regulateurDebit.setTauxDouche(zeroBd)

        calculetteEau.setToilettes(toilettes)
        calculetteEau.setRegulateurDebit(regulateurDebit)

        zone.setCalculetteEau(calculetteEau)

    }

    @CompileStatic
    private static void setCalculetteChantierToZone(Zone zone) {
        // nothing to set yet for now so set dummy values
        BigDecimal zeroBd = BigDecimal.valueOf(0)
        TCalculetteChantier calculetteChantier = new TCalculetteChantier()
        calculetteChantier.setTerresEvacuees(zeroBd)
        calculetteChantier.setTerresExcavees(zeroBd)
        calculetteChantier.setTerresImportees(zeroBd)
        calculetteChantier.setDistImportTerres(zeroBd)
        calculetteChantier.setDistTraitementTerres(zeroBd)


        TCalculetteChantier.Grue grue = new TCalculetteChantier.Grue()
        grue.setDureeEteAvec(0)
        grue.setDureeEteSans(0)
        grue.setDureeHivAvec(0)
        grue.setDureeHivSans(0)

        calculetteChantier.setGrue(grue)
        zone.setCalculetteChantier(calculetteChantier)
    }
}
