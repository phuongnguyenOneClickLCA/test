package com.bionova.optimi.frenchTools.re2020

import com.bionova.optimi.core.domain.mongo.CalculationResult
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.service.CalculationResultService
import com.bionova.optimi.core.service.DatasetService
import com.bionova.optimi.core.service.OptimiResourceService
import com.bionova.optimi.core.service.Re2020Service
import com.bionova.optimi.core.service.XmlService
import com.bionova.optimi.frenchTools.FrenchConstants
import com.bionova.optimi.frenchTools.FrenchStore
import com.bionova.optimi.frenchTools.helpers.BatimentArea
import com.bionova.optimi.frenchTools.helpers.DatasetGroup
import com.bionova.optimi.util.NumberUtil
import com.bionova.optimi.xml.re2020RSEnv.Batiment
import com.bionova.optimi.xml.re2020RSEnv.Batiment.Attestations
import com.bionova.optimi.xml.re2020RSEnv.Batiment.ExigencesResultats
import com.bionova.optimi.xml.re2020RSEnv.Batiment.ExigencesResultats.Environnementales
import com.bionova.optimi.xml.re2020RSEnv.DatasComp
import com.bionova.optimi.xml.re2020RSEnv.ObjectFactory
import com.bionova.optimi.xml.re2020RSEnv.Projet
import com.bionova.optimi.xml.re2020RSEnv.TAdresse
import com.bionova.optimi.xml.re2020RSEnv.TOperation
import groovy.transform.CompileStatic

import javax.xml.datatype.DatatypeFactory
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat

/**
 * This class is for exporting DatasComp XML for RE2020
 */
@CompileStatic
class DataCompUtilRe2020 {
    DatasetService datasetService
    XmlService xmlService
    NumberUtil numberUtil
    OptimiResourceService optimiResourceService
    CalculationResultService calculationResultService
    Re2020Service re2020Service

    private static final int BET_REF = 2
    private static final int LOGICIEL_REF = 2
    private static final int ART_95 = 1
    private static final int ART_96 = 1


    void setDatasCompToProjet(Projet projet, FrenchStore store) {

        // User already import a file that has DatasComp, and we keep quite many info from it. If it doesn't have >>> create new
        // in this service, we override many fields in that file (if it has)
        DatasComp datasComp = projet?.getDatasComp() ?: new DatasComp()
        datasComp.setVersion(FrenchConstants.RE2020_DATA_COMP_VERSION)
        Batiment.Gps gps = new Batiment.Gps()

        setDonneesGeneralesAndFewObjOfBatiment(datasComp, gps, store)
        setBatimentCollectionAndGroupDatasetsToStore(datasComp, gps, store)

        projet.setDatasComp(datasComp)
    }

    /**
     *
     * @param datasComp
     * @param gps
     * @param store
     */
    private void setDonneesGeneralesAndFewObjOfBatiment(DatasComp datasComp, Batiment.Gps gps, FrenchStore store) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd")
        DatatypeFactory xmlGregorianCalendarInstance = DatatypeFactory.newInstance()
        DecimalFormat decimalFormat = store.decimalFormat

        // Initialize schema objects
        ObjectFactory objectFactory = new ObjectFactory()
        DatasComp.DonneesGenerales donneesGenerales = datasComp?.getDonneesGenerales() ?: new DatasComp.DonneesGenerales()

        DatasComp.DonneesGenerales.BureauControle bureauControle = donneesGenerales.getBureauControle() ?: new DatasComp.DonneesGenerales.BureauControle()
        if (!bureauControle.adresse) {
            bureauControle.adresse = new TAdresse()
        }

        DatasComp.DonneesGenerales.MaitreOeuvre maitreOuvre = donneesGenerales.getMaitreOeuvre() ?: new DatasComp.DonneesGenerales.MaitreOeuvre()
        if (!maitreOuvre.adresse) {
            maitreOuvre.adresse = new TAdresse()
        }

        DatasComp.DonneesGenerales.MaitreOuvrage maitreOuvrage = donneesGenerales.getMaitreOuvrage() ?: new DatasComp.DonneesGenerales.MaitreOuvrage()
        if (!maitreOuvrage.adresse) {
            maitreOuvrage.adresse = new TAdresse()
        }

        TOperation operation = donneesGenerales.getOperation() ?: new TOperation()
        if (!operation.adresse) {
            operation.adresse = new TAdresse()
        }

        boolean newBetCreated = false
        DatasComp.DonneesGenerales.BET bet = donneesGenerales.getBET()?.find { it.getRef() == BET_REF }
        if (!bet) {
            newBetCreated = true
            bet = new DatasComp.DonneesGenerales.BET()
            bet.setRef(BET_REF)
        }

        if (!bet.adresse) {
            bet.adresse = new TAdresse()
        }

        boolean newLogicielCreated = false
        DatasComp.DonneesGenerales.Logiciel logiciel = donneesGenerales.getLogiciel()?.find { it.getRef() == LOGICIEL_REF }
        if (!logiciel) {
            newLogicielCreated = true
            logiciel = new DatasComp.DonneesGenerales.Logiciel()
            logiciel.setRef(LOGICIEL_REF)
        }

        DatasComp.DonneesGenerales.Entreprise entreprise = donneesGenerales.getEntreprise()?.find { it != null } ?: new DatasComp.DonneesGenerales.Entreprise()


        // Set values to schema objects
        if (store.parentEntity.datasets) {
            for (Dataset dataset in store.parentEntity.datasets) {
                if (FrenchConstants.FEC_QUERYID_PROJECT_LEVEL != dataset.queryId) {
                    continue
                }

                String answer = datasetService.getAnswerFromDataset(dataset, ["0", "-", "_"])
                if (!answer) {
                    continue
                }

                if (FrenchConstants.LES_ACTEURS_DU_PROJET_RE2020_SECTIONID == dataset.sectionId) {
                    switch (dataset.questionId) {
                        case FrenchConstants.NOM_MOA_QUESTIONID:
                            maitreOuvrage.setNom(answer)
                            break
                        case FrenchConstants.SIRET_MOA_QUESTIONID:
                            maitreOuvrage.setSIRET(answer)
                            break
                        case FrenchConstants.ID_BAN_MOA_QUESTIONID:
                            maitreOuvrage.adresse.setId(answer)
                            break
                        case FrenchConstants.DATE_APPEL_MOA_QUESTIONID:
                            maitreOuvrage.adresse.setDateAppel(xmlService.answerToXMLGregorianCalendar(answer, dateFormat, xmlGregorianCalendarInstance, dataset))
                            break
                        case FrenchConstants.ADRESS_MOA_QUESTIONID:
                            maitreOuvrage.adresse.setLabel(answer)
                            break
                        case FrenchConstants.HOUSE_NUMBER_MOA_QUESTIONID:
                            maitreOuvrage.adresse.setHousenumber(answer)
                            break
                        case FrenchConstants.STREET_MOA_QUESTIONID:
                            maitreOuvrage.adresse.setStreet(answer)
                            break
                        case FrenchConstants.COMPLEMENT_MOA_QUESTIONID:
                            maitreOuvrage.adresse.setComplement(answer)
                            break
                        case FrenchConstants.ADRESS_NAME_MOA_QUESTIONID:
                            maitreOuvrage.adresse.setName(answer)
                            break
                        case FrenchConstants.POSTCODE_MOA_QUESTIONID:
                            maitreOuvrage.adresse.setPostcode(answer)
                            break
                        case FrenchConstants.CITY_CODE_MOA_QUESTIONID:
                            maitreOuvrage.adresse.setCitycode(answer)
                            break
                        case FrenchConstants.CITY_MOA_QUESTIONID:
                            maitreOuvrage.adresse.setCity(answer)
                            break
                        case FrenchConstants.DISTRICT_MOA_QUESTIONID:
                            maitreOuvrage.adresse.setDistrict(answer)
                            break
                        case FrenchConstants.PHONE_MOA_QUESTIONID:
                            maitreOuvrage.setTel(answer)
                            break
                        case FrenchConstants.MAIL_MOA_QUESTIONID:
                            maitreOuvrage.setCourriel(answer)
                            break
                        case FrenchConstants.TYPE_MOA_QUESTIONID:
                            if (answer?.isInteger()) {
                                maitreOuvrage.setType(answer.toInteger())
                            }
                            break
                        case FrenchConstants.NOM_MOE_QUESTIONID:
                            maitreOuvre.setNom(answer)
                            break
                        case FrenchConstants.ID_BAN_MOE_QUESTIONID:
                            maitreOuvre.adresse.setId(answer)
                            break
                        case FrenchConstants.DATE_APPEL_MOE_QUESTIONID:
                            maitreOuvre.adresse.setDateAppel(xmlService.answerToXMLGregorianCalendar(answer, dateFormat, xmlGregorianCalendarInstance, dataset))
                            break
                        case FrenchConstants.ADRESS_MOE_QUESTIONID:
                            maitreOuvre.adresse.setLabel(answer)
                            break
                        case FrenchConstants.HOUSE_NUMBER_MOE_QUESTIONID:
                            maitreOuvre.adresse.setHousenumber(answer)
                            break
                        case FrenchConstants.STREET_MOE_QUESTIONID:
                            maitreOuvre.adresse.setStreet(answer)
                            break
                        case FrenchConstants.COMPLEMENT_MOE_QUESTIONID:
                            maitreOuvre.adresse.setComplement(answer)
                            break
                        case FrenchConstants.ADRESS_NAME_MOE_QUESTIONID:
                            maitreOuvre.adresse.setName(answer)
                            break
                        case FrenchConstants.POSTCODE_MOE_QUESTIONID:
                            maitreOuvre.adresse.setPostcode(answer)
                            break
                        case FrenchConstants.CITY_CODE_MOE_QUESTIONID:
                            maitreOuvre.adresse.setCitycode(answer)
                            break
                        case FrenchConstants.CITY_MOE_QUESTIONID:
                            maitreOuvre.adresse.setCity(answer)
                            break
                        case FrenchConstants.DISTRICT_MOE_QUESTIONID:
                            maitreOuvre.adresse.setDistrict(answer)
                            break
                        case FrenchConstants.PHONE_MOE_QUESTIONID:
                            maitreOuvre.setTel(answer)
                            break
                        case FrenchConstants.MAIL_MOE_QUESTIONID:
                            maitreOuvre.setCourriel(answer)
                            break
                        case FrenchConstants.NOM_BET_QUESTIONID:
                            bet.setNom(answer)
                            break
                        case FrenchConstants.SIRET_BET_QUESTIONID:
                            bet.setSIRET(answer)
                            break
                        case FrenchConstants.PHONE_BET_QUESTIONID:
                            bet.setTel(answer)
                            break
                        case FrenchConstants.MAIL_BET_QUESTIONID:
                            bet.setCourriel(answer)
                            break
                        case FrenchConstants.ID_BAN_BET_QUESTIONID:
                            bet.adresse.setId(answer)
                            break
                        case FrenchConstants.DATE_APPEL_BET_QUESTIONID:
                            bet.adresse.setDateAppel(xmlService.answerToXMLGregorianCalendar(answer, dateFormat, xmlGregorianCalendarInstance, dataset))
                            break
                        case FrenchConstants.ADRESS_BET_QUESTIONID:
                            bet.adresse.setLabel(answer)
                            break
                        case FrenchConstants.HOUSE_NUMBER_BET_QUESTIONID:
                            bet.adresse.setHousenumber(answer)
                            break
                        case FrenchConstants.STREET_BET_QUESTIONID:
                            bet.adresse.setStreet(answer)
                            break
                        case FrenchConstants.COMPLEMENT_BET_QUESTIONID:
                            bet.adresse.setComplement(answer)
                            break
                        case FrenchConstants.ADRESS_NAME_BET_QUESTIONID:
                            bet.adresse.setName(answer)
                            break
                        case FrenchConstants.POST_CODE_BET_QUESTIONID:
                            bet.adresse.setPostcode(answer)
                            break
                        case FrenchConstants.CITY_CODE_BET_QUESTIONID:
                            bet.adresse.setCitycode(answer)
                            break
                        case FrenchConstants.CITY_BET_QUESTIONID:
                            bet.adresse.setCity(answer)
                            break
                        case FrenchConstants.DISTRICT_BET_QUESTIONID:
                            bet.adresse.setDistrict(answer)
                            break
                        case FrenchConstants.DATE_ETUDE_QUESTIONID:
                            logiciel.setDateEtude(xmlService.answerToXMLGregorianCalendar(answer, dateFormat, xmlGregorianCalendarInstance, dataset))
                            break
                        case FrenchConstants.EDITEUR_QUESTIONID:
                            logiciel.setEditeur(answer)
                            break
                        case FrenchConstants.LOGICIEL_NOM_QUESTIONID:
                            logiciel.setNom(answer)
                            break
                        case FrenchConstants.VERSION_QUESTIONID:
                            logiciel.setVersion(answer)
                            break
                        case FrenchConstants.NOM_BUREAU_CONTROLE_QUESTIONID:
                            bureauControle.setNom(answer)
                            break
                        case FrenchConstants.ID_BAN_BUREAU_CONTROLE_QUESTIONID:
                            bureauControle.adresse.setId(answer)
                            break
                        case FrenchConstants.DATE_APPEL_BUREAU_CONTROLE_QUESTIONID:
                            bureauControle.adresse.setDateAppel(xmlService.answerToXMLGregorianCalendar(answer, dateFormat, xmlGregorianCalendarInstance, dataset))
                            break
                        case FrenchConstants.ADRESS_BUREAU_CONTROLE_QUESTIONID:
                            bureauControle.adresse.setLabel(answer)
                            break
                        case FrenchConstants.HOUSE_NUMBER_BUREAU_CONTROLE_QUESTIONID:
                            bureauControle.adresse.setHousenumber(answer)
                            break
                        case FrenchConstants.STREET_BUREAU_CONTROLE_QUESTIONID:
                            bureauControle.adresse.setStreet(answer)
                            break
                        case FrenchConstants.COMPLEMENT_BUREAU_CONTROLE_QUESTIONID:
                            bureauControle.adresse.setComplement(answer)
                            break
                        case FrenchConstants.ADRESS_NAME_BUREAU_CONTROLE_QUESTIONID:
                            bureauControle.adresse.setName(answer)
                            break
                        case FrenchConstants.POSTCODE_BUREAU_CONTROLE_QUESTIONID:
                            bureauControle.adresse.setPostcode(answer)
                            break
                        case FrenchConstants.CITY_CODE_BUREAU_CONTROLE_QUESTIONID:
                            bureauControle.adresse.setCitycode(answer)
                            break
                        case FrenchConstants.CITY_BUREAU_CONTROLE_QUESTIONID:
                            bureauControle.adresse.setCity(answer)
                            break
                        case FrenchConstants.DISTRICT_BUREAU_CONTROLE_QUESTIONID:
                            bureauControle.adresse.setDistrict(answer)
                            break
                        case FrenchConstants.MAIL_BUREAU_CONTROLE_QUESTIONID:
                            bureauControle.setCourriel(answer)
                            break
                        case FrenchConstants.PHONE_BUREAU_CONTROLE_QUESTIONID:
                            bureauControle.setTel(answer)
                            break
                        case FrenchConstants.ENTREPRISE_NOM_QUESTIONID:
                            entreprise.setNom(answer)
                            break
                    }
                } else if (FrenchConstants.DONNEES_ADMINISTRATIVES_SECTIONID == dataset.sectionId) {
                    switch (dataset.questionId) {
                        case FrenchConstants.NOM_QUESTIONID:
                            operation.setNom(answer)
                            break
                        case FrenchConstants.DESCRIPTION_QUESTIONID:
                            operation.setDescription(answer)
                            break
                        case FrenchConstants.NUM_PERMIS_QUESTIONID:
                            operation.setNumPermis(answer)
                            break
                        case FrenchConstants.DATE_DEPOT_PC_QUESTIONID:
                            operation.setDateDepotPC(xmlService.answerToXMLGregorianCalendar(answer, dateFormat, xmlGregorianCalendarInstance, dataset))
                            break
                        case FrenchConstants.DATE_OBTENTION_PC_QUESTIONID:
                            operation.setDateObtentionPC(xmlService.answerToXMLGregorianCalendar(answer, dateFormat, xmlGregorianCalendarInstance, dataset))
                            break
                        case FrenchConstants.DATE_OBTENTION_PA_GAZ_QUESTIONID:
                            operation.setDateObtentionPaGaz(xmlService.answerToXMLGregorianCalendar(answer, dateFormat, xmlGregorianCalendarInstance, dataset))
                            break
                        case FrenchConstants.DATE_LIVRAISON_QUESTIONID:
                            operation.setDateLivraison(xmlService.answerToXMLGregorianCalendar(answer, dateFormat, xmlGregorianCalendarInstance, dataset))
                            break
                        case FrenchConstants.DATE_APPROBATION_ZAC_GAZ_QUESTIONID:
                            operation.setDateApprobationZACGaz(xmlService.answerToXMLGregorianCalendar(answer, dateFormat, xmlGregorianCalendarInstance, dataset))
                            break
                        case FrenchConstants.ID_BAN_OPERATION_QUESTIONID:
                            operation.adresse.setId(answer)
                            break
                        case FrenchConstants.DATE_APPEL_OPERATION_QUESTIONID:
                            operation.adresse.setDateAppel(xmlService.answerToXMLGregorianCalendar(answer, dateFormat, xmlGregorianCalendarInstance, dataset))
                            break
                        case FrenchConstants.ADRESS_OPERATION_QUESTIONID:
                            operation.adresse.setLabel(answer)
                            break
                        case FrenchConstants.HOUSE_NUMBER_OPERATION_QUESTIONID:
                            operation.adresse.setHousenumber(answer)
                            break
                        case FrenchConstants.LIGNE_QUESTIONID:
                            operation.adresse.setStreet(answer)
                            break
                        case FrenchConstants.COMPLEMENT_OPERATION_QUESTIONID:
                            operation.adresse.setComplement(answer)
                            break
                        case FrenchConstants.ADRESS_NAME_OPERATION_QUESTIONID:
                            operation.adresse.setName(answer)
                            break
                        case FrenchConstants.CODE_POSTAL_QUESTIONID:
                            operation.adresse.setPostcode(answer)
                            break
                        case FrenchConstants.CITY_CODE_OPERATION_QUESTIONID:
                            operation.adresse.setCitycode(answer)
                            break
                        case FrenchConstants.COMMUNE_QUESTIONID:
                            operation.adresse.setCity(answer)
                            break
                        case FrenchConstants.DISTRICT_OPERATION_QUESTIONID:
                            operation.adresse.setDistrict(answer)
                            break
                        case FrenchConstants.COMMENTAIRE_ACV_QUESTIONID:
                            operation.setCommentaireAcv(answer)
                            break
                        case FrenchConstants.REFERENCE_CADASTRALE_QUESTIONID:
                            TOperation.Cadastre cadastre = new TOperation.Cadastre()
                            cadastre.referenceCadastrale.add(answer)
                            operation.setCadastre(cadastre)
                            break
                    }
                } else if (FrenchConstants.DESCRIPTION_PROJET_ET_SES_CONTRAINTES_SECTIONID == dataset.sectionId) {
                    switch (dataset.questionId) {
                        case FrenchConstants.GEOTECH_QUESTIONID:
                            operation.setGeotech(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                            break
                        case FrenchConstants.SOL_POLLUTION_QUESTIONID:
                            operation.setSolPollution(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                            break
                        case FrenchConstants.ZONE_CLIMATIQUE_QUESTIONID:
                            operation.setZoneClimatique(answer)
                            break
                        case FrenchConstants.ZONE_SISMIQUE_QUESTIONID:
                            operation.setZoneSismique(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                            break
                        case FrenchConstants.LONGITUDE_QUESTIONID:
                            gps.setLongitude(answer)
                            break
                        case FrenchConstants.LATITUDE_QUESTIONID:
                            gps.setLatitude(answer)
                            break
                    }
                }
            }
        }

        donneesGenerales.setMaitreOeuvre(maitreOuvre)
        donneesGenerales.setMaitreOuvrage(maitreOuvrage)
        donneesGenerales.setBureauControle(bureauControle)

        if (newBetCreated) {
            donneesGenerales.getBET().add(bet)
        }

        if (newLogicielCreated) {
            donneesGenerales.getLogiciel().add(logiciel)
        }
        donneesGenerales.getEntreprise().add(entreprise)
        donneesGenerales.setOperation(operation)
        datasComp.setDonneesGenerales(donneesGenerales)
    }

    private void setBatimentCollectionAndGroupDatasetsToStore(DatasComp datasComp, Batiment.Gps gps, FrenchStore store) {
        if (!store.designs) {
            return
        }
        DatasComp.BatimentCollection batimentCollection = datasComp.getBatimentCollection() ?: new DatasComp.BatimentCollection()
        DecimalFormat decimalFormat = store.decimalFormat

        for (Entity design in store.designs) {
            String designId = design.id.toString()
            Map<String, Set<Dataset>> datasetsPerZone = [:]
            BigInteger index = store.batimentIndexMappings.get(designId)?.toBigInteger()
            Batiment batiment = batimentCollection.batiment?.find { it.index } ?: new Batiment()
            Batiment.DonneesTechniques donneesTechniques = batiment.donneesTechniques ?: new Batiment.DonneesTechniques()
            Set<String> validZones = store.batimentZoneIndexMappings.get(designId)?.keySet()
            if (batiment.index == null) {
                batiment.setIndex(index)
            }

            ExigencesResultats exigencesResultats = batiment?.getExigencesResultats() ?: new ExigencesResultats()
            Environnementales environnementales = exigencesResultats?.getEnvironnementales() ?: new Environnementales()
            Attestations attestations = batiment?.getAttestations() ?: new Attestations()

            BatimentArea batimentArea = new BatimentArea(designId)
            Set<Dataset> srefDatasets = []
            Map<String, Set<Dataset>> datasetsPerQuery = new HashMap<>()

            // Set values to schema objects
            if (design.datasets) {
                for (Dataset dataset in design.datasets) {
                    String zoneId = datasetService.getZoneOfDataset(dataset)
                    if (FrenchConstants.PROJECT_DESCRIPTION_FEC_QUERYID == dataset.queryId) {
                        String answer = datasetService.getAnswerFromDataset(dataset, ["-", "_"])

                        if (!answer) {
                            continue
                        }

                        if (FrenchConstants.DESCRIPTION_BATIMENT_SECTIONID == dataset.sectionId) {
                            switch (dataset.questionId) {
                                case FrenchConstants.USAGE_PRINCIPAL_QUESTIONID:
                                    Integer usage_principal = 1
                                    List<Resource> usage_principal_resource = optimiResourceService.getResourceProfilesByResourceId(answer)
                                    if (usage_principal_resource?.size() > 0) {
                                        usage_principal = numberUtil.answerToInteger(usage_principal_resource[0]?.profileId, decimalFormat, dataset, true)
                                    }
                                    batiment.setUsagePrincipal(usage_principal)
                                    break
                                case FrenchConstants.COMMENTAIRES_QUESTIONID:
                                    batiment.setDescription(answer)
                                    break
                                case FrenchConstants.NB_NIV_SSOL_QUESTIONID:
                                    batiment.setNbNivSsol(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                    break
                                case FrenchConstants.NB_NIV_SURFACE_QUESTIONID:
                                    batiment.setNbNivSurface(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                    break
                                case FrenchConstants.NB_PLACE_PARKING_INFRA_QUESTIONID:
                                    batiment.setNbPlaceParkingInfra(numberUtil.answerToBigDecimal(answer, decimalFormat, dataset))
                                    break
                                case FrenchConstants.NB_PLACE_PARKING_SUPRA_QUESTIONID:
                                    batiment.setNbPlaceParkingSupra(numberUtil.answerToBigDecimal(answer, decimalFormat, dataset))
                                    break
                                case FrenchConstants.NB_PLACE_PARKING_EXT_QUESTIONID:
                                    batiment.setNbPlaceParkingExt(numberUtil.answerToBigDecimal(answer, decimalFormat, dataset))
                                    break
                                case FrenchConstants.ENGAGEMENT_IC_CONSTRUCTION_QUESTIONID:
                                    environnementales.setEngagementIcConstruction(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                    break
                                case FrenchConstants.SREF_BAT_EXISTANT_QUESTIONID:
                                    attestations.setSrefBatExistant(datasetService.getAnswerFromDatasetAsDouble(dataset) ?: 0d)
                                    break
                            }
                        } else if (FrenchConstants.DESCRIPTION_PER_ZONE_RE2020_SECTIONID == dataset.sectionId) {
                            switch (dataset.questionId) {
                                case FrenchConstants.SREF_QUESTIONID:
                                    // don't get the areas of the all zones or zones that have not been mapped (not valid)
                                    if (zoneId != FrenchConstants.FEC_UNASSIGNED_ZONE && zoneId in validZones) {
                                        srefDatasets.add(dataset)
                                        batimentArea.setZoneArea(zoneId, datasetService.getAnswerFromDatasetAsDouble(dataset), datasetService.getAdditionalAnswerFromDatasetAsDouble(dataset, FrenchConstants.NOCC_QUESTIONID))
                                    }
                                    break
                            }
                        } else if (FrenchConstants.DESCRIPTION_TECHNIQUE_BATIMENT_SECTIONID == dataset.sectionId) {
                            switch (dataset.questionId) {
                                case FrenchConstants.COMMENTAIRES_DONNEES_TECHNIQUES_QUESTIONID:
                                    donneesTechniques.setCommentaires(answer)
                                    break
                                case FrenchConstants.TYPE_STRUCTURE_QUESTIONID:
                                    donneesTechniques.setTypeStructurePrincipale(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                    break
                                case FrenchConstants.ELEMENTS_PREFABRIQUES_QUESTIONID:
                                    donneesTechniques.setElementsPrefabriques(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                    break
                                case FrenchConstants.MATERIAU_PRINCIPAL_QUESTIONID:
                                    donneesTechniques.setMateriauStructure(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                    break
                                case FrenchConstants.MATERIAU_REMPLISSAGE_FACADE_QUESTIONID:
                                    donneesTechniques.setMateriauRemplissageFacade(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                    break
                                case FrenchConstants.MUR_MODE_ISOLATION_QUESTIONID:
                                    donneesTechniques.setMurModeIsolation(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                    break
                                case FrenchConstants.MUR_NATURE_ISOLANT_QUESTIONID:
                                    donneesTechniques.setMurNatureIsolant(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                    break
                                case FrenchConstants.MUR_REVETEMENT_EXT_QUESTIONID:
                                    donneesTechniques.setMurRevetementExt(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                    break
                                case FrenchConstants.TYPE_FONDATION_QUESTIONID:
                                    donneesTechniques.setTypeFondation(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                    break
                                case FrenchConstants.TYPE_PLANCHER_QUESTIONID:
                                    donneesTechniques.setTypePlancher(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                    break
                                case FrenchConstants.PLANCHER_MODE_ISOLATION_QUESTIONID:
                                    donneesTechniques.setPlancherModeIsolation(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                    break
                                case FrenchConstants.PLANCHER_NATURE_ISOLANT_QUESTIONID:
                                    donneesTechniques.setPlancherNatureIsolant(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                    break
                                case FrenchConstants.PLANCHER_NATURE_ESPACE_QUESTIONID:
                                    donneesTechniques.setPlancherNatureEspace(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                    break
                                case FrenchConstants.TYPE_TOITURE_QUESTIONID:
                                    donneesTechniques.setTypeToiture(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                    break
                                case FrenchConstants.TOITURE_MODE_ISOLATION_QUESTIONID:
                                    donneesTechniques.setToitureModeIsolation(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                    break
                                case FrenchConstants.TOITURE_NATURE_ISOLANT_QUESTIONID:
                                    donneesTechniques.setToitureNatureIsolant(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                    break
                                case FrenchConstants.TOITURE_VEGETALISEE_QUESTIONID:
                                    donneesTechniques.setToitureVegetalisee(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                    break
                                case FrenchConstants.TOITURE_COUVERTURE_QUESTIONID:
                                    donneesTechniques.setToitureCouverture(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                    break
                                case FrenchConstants.TYPE_MENUISERIE_QUESTIONID:
                                    donneesTechniques.setTypeMenuiserie(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                    break
                                case FrenchConstants.TYPE_PM_RE2020_QUESTIONID:
                                    donneesTechniques.setTypePm(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                    break
                                case FrenchConstants.COMMENTAIRES_PROD_ELECTRICITE_QUESTIONID:
                                    donneesTechniques.setCommentairesProdElectricite(answer)
                                    break
                                case FrenchConstants.STOCKAGE_ELECTRICITE_QUESTIONID:
                                    donneesTechniques.setStockageElectricite(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                    break
                                case FrenchConstants.GESTION_ACTIVE_QUESTIONID:
                                    donneesTechniques.setGestionActive(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                    break
                                case FrenchConstants.TYPE_ECLAIRAGE_QUESTIONID:
                                    donneesTechniques.setTypeEclairage(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                    break
                            }
                        }
                    }

                    // populate the datasetsPerDesignPerZone for later use
                    if (zoneId) {
                        datasetService.groupDatasetSameKey(dataset, zoneId, datasetsPerZone)
                    }

                    datasetService.groupDatasetSameKey(dataset, dataset.queryId, datasetsPerQuery)
                }
            }
            batimentArea.sref = datasetService.getSumAllAnswerFromDatasets(srefDatasets)
            batimentArea.nocc = datasetService.getSumAllAdditionalAnswerFromDatasets(srefDatasets, FrenchConstants.PROJECT_DESCRIPTION_FEC_QUERYID, FrenchConstants.DESCRIPTION_PER_ZONE_RE2020_SECTIONID, FrenchConstants.SREF_QUESTIONID, FrenchConstants.NOCC_QUESTIONID)
            batimentArea.setZonePerBatimentRatio()
            re2020Service.handleDatasetsForAllZone(datasetsPerZone, batimentArea)
            store.srefPerDesign.add(batimentArea)

            batiment.setOSREF(batimentArea.sref ? batimentArea.sref.toBigDecimal() : BigDecimal.ZERO)

            DatasetGroup datasetGroup = new DatasetGroup(designId)
            datasetGroup.datasetsPerZone = datasetsPerZone
            datasetGroup.datasetsPerQuery = datasetsPerQuery
            re2020Service.groupEnergyAndWaterDatasets(datasetGroup)
            re2020Service.groupSiteDatasetsPerCategory(datasetGroup)
            re2020Service.groupBeDatasets(datasetGroup, store)
            store.datasetGroups.add(datasetGroup)

            batiment.setNOcc(numberUtil.resultToBigDecimal(batimentArea.nocc))
//            setExigencesResultatsToBatiment(batiment, store.resultsPerDesign?.get(designId))
            batiment.setGps(gps)
            batiment.setDonneesTechniques(donneesTechniques)

            Integer existingIndexInList = batimentCollection.batiment.findIndexOf { it.index == index }
            if (existingIndexInList > -1) {
                batimentCollection.batiment.remove(existingIndexInList)
            }

            // we don't export the design for parcelle in datascomp if it is stand alone
            if (designId == store.parcelle.designId && store.parcelle.isStandAlone) {
                continue
            }
            batimentCollection.batiment.add(batiment)
        }
        datasComp.setBatimentCollection(batimentCollection)
    }

    /* we don't set the exigencesResultats anymore, they are existing in the DatasComp from the file uploaded by user during the import on FEC project level query.
    private void setExigencesResultatsToBatiment(Batiment batiment, List<CalculationResult> designResults) {
        Batiment.ExigencesResultats exigencesResultats = new Batiment.ExigencesResultats()
        Batiment.ExigencesResultats.Environnementales environnementales = new Batiment.ExigencesResultats.Environnementales()
//          JP asked to keep the code, just in case
//        CalculationResult icEnergieMax = calculationResultService.getCalcResultByResultCatAndCalcRule(FrenchConstants.IC_ENERGIE_MAX_RESULT_CATID, FrenchConstants.GWP_TOTAL_CALC_RULEID, designResults)
//        CalculationResult energie = calculationResultService.getCalcResultByResultCatAndCalcRule(FrenchConstants.ENERGIE_RESULT_CATID, FrenchConstants.GWP_M2_SREF_CALC_RULEID, designResults)
//        CalculationResult icConstructionMax = calculationResultService.getCalcResultByResultCatAndCalcRule(FrenchConstants.IC_CONSTRUCTION_MAX_RESULT_CATID, FrenchConstants.GWP_TOTAL_CALC_RULEID, designResults)
//        CalculationResult construction = calculationResultService.getCalcResultByResultCatAndCalcRule(FrenchConstants.CONSTRUCTION_RESULT_CATID, FrenchConstants.GWP_M2_SREF_CALC_RULEID, designResults)
//
//        environnementales.setArt92(icEnergieMax?.result > energie?.result ? 1 : 0)
//        environnementales.setArt93(icConstructionMax?.result > construction?.result ? 1 : 0)
//        environnementales.setArt95(ART_95)
//        environnementales.setArt96(ART_96)
//
//        exigencesResultats.energetiquesOrEnvironnementales.add(environnementales)
        // JP will give a question to fetch data for engagement_ic_construction
        batiment.setExigencesResultats(exigencesResultats)
    }
     */

}
