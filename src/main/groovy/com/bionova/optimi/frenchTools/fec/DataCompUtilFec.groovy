package com.bionova.optimi.frenchTools.fec

import com.bionova.optimi.frenchTools.FrenchConstants
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.service.DatasetService
import com.bionova.optimi.core.service.OptimiResourceService
import com.bionova.optimi.core.service.XmlService
import com.bionova.optimi.util.NumberUtil
import com.bionova.optimi.xml.fecRSEnv.ObjectFactory
import com.bionova.optimi.xml.fecRSEnv.RSEnv
import com.bionova.optimi.xml.fecRSEnv.TAdresse
import groovy.transform.CompileStatic

import javax.xml.datatype.DatatypeFactory
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat

@CompileStatic
class DataCompUtilFec {
    DatasetService datasetService
    XmlService xmlService
    NumberUtil numberUtil
    OptimiResourceService optimiResourceService

    void setDataCompToRSEnv(RSEnv rsEnv, Entity entity, List<Entity> designs, Map<String, Integer> batimentIndexMappings) {
        DecimalFormat decimalFormat = new DecimalFormat()
        decimalFormat.setParseBigDecimal(true)

        RSEnv.DataComp dataComp = new RSEnv.DataComp()
        RSEnv.DataComp.Batiment.Gps gps = new RSEnv.DataComp.Batiment.Gps()
        RSEnv.DataComp.Batiment.Certificateur certificateur = new RSEnv.DataComp.Batiment.Certificateur()
        RSEnv.DataComp.Batiment.Verificateur verificateur = new RSEnv.DataComp.Batiment.Verificateur()
        RSEnv.DataComp.Batiment.SigneQualite signeQualite = new RSEnv.DataComp.Batiment.SigneQualite()

        FrenchStorage store = setDonneesGeneralesAndFewObjOfBatiment(dataComp, entity, decimalFormat, gps, certificateur, verificateur, signeQualite)
        setBatiments(dataComp, designs, batimentIndexMappings, decimalFormat, signeQualite, certificateur, gps, verificateur, store)

        rsEnv.setDataComp(dataComp)
    }

    /**
     *
     * @param dataComp
     * @param entity
     * @param decimalFormat
     * @param gps
     * @param certificateur
     * @param verificateur
     * @param signeQualite
     * @return maquetteNumerique and dureeChantier values in a temporary store object
     */
    private FrenchStorage setDonneesGeneralesAndFewObjOfBatiment(RSEnv.DataComp dataComp, Entity entity, DecimalFormat decimalFormat,
                                                                 RSEnv.DataComp.Batiment.Gps gps, RSEnv.DataComp.Batiment.Certificateur certificateur,
                                                                 RSEnv.DataComp.Batiment.Verificateur verificateur, RSEnv.DataComp.Batiment.SigneQualite signeQualite) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd")
        DatatypeFactory xmlGregorianCalendarInstance = DatatypeFactory.newInstance()

        // Initialize schema objects
        ObjectFactory objectFactory = new ObjectFactory()
        RSEnv.DataComp.DonneesGenerales donneesGenerales = new RSEnv.DataComp.DonneesGenerales()
        RSEnv.DataComp.DonneesGenerales.MaitreOeuvre maitreOuvre = new RSEnv.DataComp.DonneesGenerales.MaitreOeuvre()
        RSEnv.DataComp.DonneesGenerales.MaitreOuvrage maitreOuvrage = new RSEnv.DataComp.DonneesGenerales.MaitreOuvrage()
        RSEnv.DataComp.DonneesGenerales.BureauEtudeAcv bureauEtudeAcv = new RSEnv.DataComp.DonneesGenerales.BureauEtudeAcv()
        RSEnv.DataComp.DonneesGenerales.Logiciel logiciel = new RSEnv.DataComp.DonneesGenerales.Logiciel()
        RSEnv.DataComp.DonneesGenerales.Operation operation = new RSEnv.DataComp.DonneesGenerales.Operation()
        RSEnv.DataComp.DonneesGenerales.Entreprise entreprise = new RSEnv.DataComp.DonneesGenerales.Entreprise()
        BigDecimal dureeChantier = null
        Integer maquetteNumerique = null

        certificateur.adresse = new TAdresse()
        verificateur.adresse = new TAdresse()
        operation.adresse = new TAdresse()
        bureauEtudeAcv.adresse = new TAdresse()
        maitreOuvrage.adresse = new TAdresse()

        // Set values to schema objects
        if (entity.datasets) {
            for (Dataset dataset in entity.datasets) {
                if (dataset.queryId != FrenchConstants.FEC_QUERYID_PROJECT_LEVEL) {
                    continue
                }

                String answer = dataset.answerIds?.size() > 0 ? dataset.answerIds[0] : FrenchConstants.LOGICIEL_NOM_QUESTIONID == dataset.questionId ? FrenchConstants.DEFAULT_OCL_DATA_COMP_ANSWER : null

                if (answer == null || ["0", "-", "_"].contains(answer)) {
                    continue
                }

                if (FrenchConstants.LES_ACTEURS_DU_PROJET_SECTIONID == dataset.sectionId) {
                    if ("nom" == dataset.questionId) {
                        maitreOuvrage.setNom(answer)
                    } else if ("SIRET" == dataset.questionId) {
                        maitreOuvrage.setSIRET(answer)
                    } else if (FrenchConstants.LIGNE_QUESTIONID == dataset.questionId) {
                        maitreOuvrage.adresse.getLigne().add(answer)
                    } else if ("code_postal" == dataset.questionId) {
                        maitreOuvrage.adresse.setCodePostal(answer)
                    } else if ("commune" == dataset.questionId) {
                        maitreOuvrage.adresse.setVille(answer)
                    } else if ("maitre_oeuvre-nom" == dataset.questionId) {
                        maitreOuvre.setNom(answer)
                    } else if ("entreprise-nom" == dataset.questionId) {
                        entreprise.setNom(answer)
                    } else if ("bureau_etude_acv-nom" == dataset.questionId) {
                        bureauEtudeAcv.setNom(answer)
                    } else if ("bureau_etude_acv-adresse-ligne" == dataset.questionId) {
                        bureauEtudeAcv.adresse.getLigne().add(answer)
                    } else if ("bureau_etude_acv-adresse-code_postal" == dataset.questionId) {
                        bureauEtudeAcv.adresse.setCodePostal(answer)
                    } else if ("bureau_etude_acv-adresse-commune" == dataset.questionId) {
                        bureauEtudeAcv.adresse.setVille(answer)
                    } else if ("bureau_etude_acv-SIRET" == dataset.questionId) {
                        bureauEtudeAcv.setSIRET(answer)
                    } else if ("editeur" == dataset.questionId) {
                        logiciel.setEditeur(answer)
                    } else if ("logiciel-nom" == dataset.questionId) {
                        logiciel.setNom(answer)
                    } else if ("version" == dataset.questionId) {
                        logiciel.setVersion(answer)
                    } else if ("type" == dataset.questionId) {
                        maitreOuvrage.setType(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                    }
                } else if (FrenchConstants.DONNEES_ADMINISTRATIVES_SECTIONID == dataset.sectionId) {
                    if ("ligne" == dataset.questionId) {
                        operation.adresse.getLigne().add(answer)
                    } else if ("code_postal" == dataset.questionId) {
                        operation.adresse.setCodePostal(answer)
                    } else if ("commune" == dataset.questionId) {
                        operation.adresse.setVille(answer)
                    } else if ("date_depot_PC" == dataset.questionId) {
                        operation.setDateDepotPC(xmlService.answerToXMLGregorianCalendar(answer, dateFormat, xmlGregorianCalendarInstance, dataset))
                    } else if ("date_obtention_PC" == dataset.questionId) {
                        operation.setDateObtentionPC(xmlService.answerToXMLGregorianCalendar(answer, dateFormat, xmlGregorianCalendarInstance, dataset))
                    } else if ("num_permis" == dataset.questionId) {
                        operation.setNumPermis(answer)
                    } else if ("surface_parcelle" == dataset.questionId) {
                        operation.setSurfaceParcelle(numberUtil.answerToBigDecimal(answer, decimalFormat, dataset, Boolean.TRUE))
                    } else if ("nb_batiment" == dataset.questionId) {
                        operation.setNbBatiment(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                    } else if ("nom" == dataset.questionId) {
                        operation.setNom(answer)
                    } else if ("description" == dataset.questionId) {
                        operation.setDescription(objectFactory.createRSEnvDataCompDonneesGeneralesOperationDescription(answer))
                    } else if ("date_livraison" == dataset.questionId) {
                        operation.setDateLivraison(xmlService.answerToXMLGregorianCalendar(answer, dateFormat, xmlGregorianCalendarInstance, dataset))
                    } else if (FrenchConstants.COMMENTAIRE_ACV_QUESTIONID == dataset.questionId) {
                        operation.setCommentaireAcv(answer)
                    }
                } else if ("descriptionProjetEtSesContraintes" == dataset.sectionId) {
                    if ("altitude" == dataset.questionId) {
                        operation.setAltitude(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                    } else if ("zone_climatique" == dataset.questionId) {
                        operation.setZoneClimatique(answer)
                    } else if ("zone_sismique" == dataset.questionId) {
                        operation.setZoneSismique(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                    } else if ("longitude" == dataset.questionId) {
                        gps.setLongitude(answer)
                    } else if ("latitude" == dataset.questionId) {
                        gps.setLatitude(answer)
                    } else if ("geotech" == dataset.questionId) {
                        operation.setGeotech(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                    } else if ("sol_pollution" == dataset.questionId) {
                        operation.setSolPollution(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                    } else if ("surface_imper" == dataset.questionId) {
                        operation.setSurfaceImper(numberUtil.answerToBigDecimal(answer, decimalFormat, dataset))
                    } else if ("surface_veg" == dataset.questionId) {
                        operation.setSurfaceVeg(numberUtil.answerToBigDecimal(answer, decimalFormat, dataset))
                    } else if ("surface_arrosee" == dataset.questionId) {
                        operation.setSurfaceArrosee(numberUtil.answerToBigDecimal(answer, decimalFormat, dataset))
                    } else if ("duree_chantier" == dataset.questionId) {
                        dureeChantier = numberUtil.answerToBigDecimal(answer, decimalFormat, dataset, Boolean.TRUE)
                    }
                } else if ("identification" == dataset.sectionId) {
                    if ("label" == dataset.questionId) {
                        signeQualite.getLabel().add(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                    } else if ("certification" == dataset.questionId) {
                        signeQualite.getCertification().add(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                    } else if ("demarche_environnementale" == dataset.questionId) {
                        signeQualite.getDemarcheEnvironnementale().add(answer)
                    } else if ("maquette_numerique" == dataset.questionId) {
                        maquetteNumerique = numberUtil.answerToInteger(answer, decimalFormat, dataset)
                    } else if ("nom" == dataset.questionId) {
                        verificateur.setNom(answer)
                    } else if ("ligne" == dataset.questionId) {
                        verificateur.adresse.getLigne().add(answer)
                    } else if ("code_postal" == dataset.questionId) {
                        verificateur.adresse.setCodePostal(answer)
                    } else if ("commune" == dataset.questionId) {
                        verificateur.adresse.setVille(answer)
                    } else if ("date_verification" == dataset.questionId) {
                        verificateur.setDateVerification(xmlService.answerToXMLGregorianCalendar(answer, dateFormat, xmlGregorianCalendarInstance, dataset))
                    } else if ("certificateur_nom" == dataset.questionId) {
                        certificateur.setNom(answer)
                    } else if ("certificateur_adresse_ligne" == dataset.questionId) {
                        certificateur.adresse.getLigne().add(answer)
                    } else if ("certificateur_adresse_code_postal" == dataset.questionId) {
                        certificateur.adresse.setCodePostal(answer)
                    } else if ("certificateur_adresse_ville" == dataset.questionId) {
                        certificateur.adresse.setVille(answer)
                    }
                }
            }
        }

        donneesGenerales.setMaitreOeuvre(maitreOuvre)
        donneesGenerales.setMaitreOuvrage(maitreOuvrage)
        donneesGenerales.setBureauEtudeAcv(bureauEtudeAcv)
        donneesGenerales.setLogiciel(logiciel)
        donneesGenerales.getEntreprise().add(entreprise)
        donneesGenerales.setOperation(operation)
        dataComp.setDonneesGenerales(donneesGenerales)

        return new FrenchStorage(maquetteNumerique: maquetteNumerique, dureeChantier: dureeChantier)
    }

    private void setBatiments(RSEnv.DataComp dataComp, List<Entity> designs, Map<String, Integer> batimentIndexMappings,
                              DecimalFormat decimalFormat, RSEnv.DataComp.Batiment.SigneQualite signeQualite,
                              RSEnv.DataComp.Batiment.Certificateur certificateur, RSEnv.DataComp.Batiment.Gps gps,
                              RSEnv.DataComp.Batiment.Verificateur verificateur, FrenchStorage store) {
        if (!designs) {
            return
        }
        for (Entity design in designs) {
            RSEnv.DataComp.Batiment batiment = new RSEnv.DataComp.Batiment()
            RSEnv.DataComp.Batiment.DonneesTechniques donneesTechniques = new RSEnv.DataComp.Batiment.DonneesTechniques()
            if (design) {
                batiment.setIndex(batimentIndexMappings.get(design.id?.toString()))
                List<String> validZones = datasetService.getValidFECZones(design.datasets)
                Double totalSdpAreaForBuilding = 0d
                Double totalSrtAreaForBuilding = 0d
                if (validZones.contains(FrenchConstants.FEC_UNASSIGNED_ZONE)) {
                    totalSdpAreaForBuilding = datasetService.getFECAreaByZoneAndQuestionId(design.datasets, FrenchConstants.PROJECT_DESCRIPTION_FEC_QUERYID, FrenchConstants.DESCRIPTION_PER_ZONE_SECTIONID, FrenchConstants.SDP_QUESTIONID, FrenchConstants.FEC_UNASSIGNED_ZONE)
                    totalSrtAreaForBuilding = datasetService.getFECAreaByZoneAndQuestionId(design.datasets, FrenchConstants.PROJECT_DESCRIPTION_FEC_QUERYID, FrenchConstants.DESCRIPTION_PER_ZONE_SECTIONID, FrenchConstants.SDP_QUESTIONID, FrenchConstants.FEC_UNASSIGNED_ZONE, FrenchConstants.FEC_ADDITIONAL_QUESTIONID_SRT)
                } else {
                    validZones?.each { zoneId ->
                        totalSdpAreaForBuilding += datasetService.getFECAreaByZoneAndQuestionId(design.datasets, FrenchConstants.PROJECT_DESCRIPTION_FEC_QUERYID, FrenchConstants.DESCRIPTION_PER_ZONE_SECTIONID, FrenchConstants.SDP_QUESTIONID, zoneId)
                        totalSrtAreaForBuilding += datasetService.getFECAreaByZoneAndQuestionId(design.datasets, FrenchConstants.PROJECT_DESCRIPTION_FEC_QUERYID, FrenchConstants.DESCRIPTION_PER_ZONE_SECTIONID, FrenchConstants.SDP_QUESTIONID, zoneId, FrenchConstants.FEC_ADDITIONAL_QUESTIONID_SRT)
                    }
                }
                batiment.setSdp(numberUtil.answerToBigDecimal(totalSdpAreaForBuilding.toString(), decimalFormat, null, Boolean.TRUE))
                batiment.setSrt(numberUtil.answerToBigDecimal(totalSrtAreaForBuilding.toString(), decimalFormat, null, Boolean.TRUE))

                // NbPlaceParkingSsol and NbPlaceParkingSurface are sum of all parking spaces defined in all zones in building description query
                Double totalNbPlaceParkingSurface = datasetService.getSumAllAdditionalAnswerFromDatasets(design.datasets, FrenchConstants.PROJECT_DESCRIPTION_FEC_QUERYID, FrenchConstants.DESCRIPTION_PER_ZONE_SECTIONID, FrenchConstants.SDP_QUESTIONID, FrenchConstants.PLACE_PARKING_SURF_FEC_QUESTIONID)
                Double totalNbPlaceParkingSsol = datasetService.getSumAllAdditionalAnswerFromDatasets(design.datasets, FrenchConstants.PROJECT_DESCRIPTION_FEC_QUERYID, FrenchConstants.DESCRIPTION_PER_ZONE_SECTIONID, FrenchConstants.SDP_QUESTIONID, FrenchConstants.PLACE_PARKING_SOUTERRAINF_FEC_QUESTIONID)
                batiment.setNbPlaceParkingSsol(numberUtil.answerToBigDecimal(totalNbPlaceParkingSsol?.toString(), decimalFormat, null, Boolean.TRUE))
                batiment.setNbPlaceParkingSurface(numberUtil.answerToBigDecimal(totalNbPlaceParkingSurface?.toString(), decimalFormat, null, Boolean.TRUE))

                // Set values to schema objects
                design.datasets?.each { Dataset dataset ->
                    if ("projectDescriptionFEC" == dataset.queryId) {
                        String answer = dataset.answerIds ? dataset.answerIds[0] : null

                        if (answer != null && !["-", "_"].contains(answer)) {
                            if ("descriptionBatiment" == dataset.sectionId) {
                                if ("commentaires" == dataset.questionId) {
                                    batiment.setCommentaires(answer)
                                } else if ("usagePrincipal" == dataset.questionId) {
                                    Integer usage_principal = 1
                                    List<Resource> usage_principal_resource = optimiResourceService.getResourceProfilesByResourceId(answer)
                                    if (usage_principal_resource && usage_principal_resource?.size() > 0) {
                                        usage_principal = numberUtil.answerToInteger(usage_principal_resource[0]?.profileId?.toString(), decimalFormat, null, Boolean.TRUE)
                                    }
                                    batiment.setUsagePrincipal(usage_principal)
                                } else if ("precisions_usage" == dataset.questionId) {
                                    batiment.setPrecisionsUsage(answer)
                                } else if ("cadre_rt" == dataset.questionId) {
                                    batiment.setCadreRt(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                } else if ("nb_unite_fonctionnalite" == dataset.questionId) {
                                    batiment.setNbUniteFonctionnalite(numberUtil.answerToBigDecimal(answer, decimalFormat, dataset))
                                } else if ("nb_occupant" == dataset.questionId) {
                                    batiment.setNbOccupant(numberUtil.answerToBigDecimal(answer, decimalFormat, dataset, Boolean.TRUE))
                                } else if ("shab" == dataset.questionId) {
                                    batiment.setShab(numberUtil.answerToBigDecimal(answer, decimalFormat, dataset))
                                } else if ("surt" == dataset.questionId) {
                                    batiment.setSurt(numberUtil.answerToBigDecimal(answer, decimalFormat, dataset))
                                } else if ("emprise_au_sol" == dataset.questionId) {
                                    batiment.setEmpriseAuSol(numberUtil.answerToBigDecimal(answer, decimalFormat, dataset))
                                } else if ("zone_br" == dataset.questionId) {
                                    batiment.setZoneBr(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                } else if ("nb_niv_ssol" == dataset.questionId) {
                                    batiment.setNbNivSsol(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                } else if ("nb_niv_surface" == dataset.questionId) {
                                    batiment.setNbNivSurface(numberUtil.answerToInteger(answer, decimalFormat, dataset, Boolean.TRUE))
                                } else if ("NbPlacesSurfacePLU" == dataset.questionId) {
                                    batiment.setNbPlaceParkingSurfacePlu(numberUtil.answerToBigDecimal(answer, decimalFormat, dataset))
                                } else if ("NbPlacesSouterrainPLU" == dataset.questionId) {
                                    batiment.setNbPlaceParkingSsolPlu(numberUtil.answerToBigDecimal(answer, decimalFormat, dataset))
                                }
                            } else if ("descriptionTechniqueBatiment" == dataset.sectionId) {
                                if ("commentaires_donnees_techniques" == dataset.questionId) {
                                    donneesTechniques.setCommentairesDonneesTechniques(answer)
                                } else if ("commentaires_structure" == dataset.questionId) {
                                    donneesTechniques.setCommentairesStructure(answer)
                                } else if ("type_structure" == dataset.questionId) {
                                    donneesTechniques.setTypeStructurePrincipale(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                } else if ("elements_prefabriques" == dataset.questionId) {
                                    donneesTechniques.setElementsPrefabriques(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                } else if ("materiau_principal" == dataset.questionId) {
                                    donneesTechniques.setMateriauPrincipal(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                } else if ("facadeType" == dataset.questionId) {
                                    donneesTechniques.setMateriauRemplissageFacade(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                } else if ("mur_mode_isolation" == dataset.questionId) {
                                    donneesTechniques.setMurModeIsolation(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                } else if ("mur_nature_isolant" == dataset.questionId) {
                                    donneesTechniques.setMurNatureIsolant(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                } else if ("mur_revetement_ext" == dataset.questionId) {
                                    donneesTechniques.setMurRevetementExt(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                } else if ("type_fondation" == dataset.questionId) {
                                    donneesTechniques.setTypeFondation(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                } else if ("type_plancher" == dataset.questionId) {
                                    donneesTechniques.setTypePlancher(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                } else if ("plancher_mode_isolation" == dataset.questionId) {
                                    donneesTechniques.setPlancherModeIsolation(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                } else if ("plancher_nature_isolant" == dataset.questionId) {
                                    donneesTechniques.setPlancherNatureIsolant(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                } else if ("plancher_nature_espace" == dataset.questionId) {
                                    donneesTechniques.setPlancherNatureEspace(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                } else if ("type_toiture" == dataset.questionId) {
                                    donneesTechniques.setTypeToiture(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                } else if ("toiture_pente" == dataset.questionId) {
                                    donneesTechniques.setToiturePente(numberUtil.answerToFloat(answer, decimalFormat, dataset))
                                } else if ("toiture_couverture" == dataset.questionId) {
                                    donneesTechniques.setToitureCouverture(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                } else if ("toiture_mode_isolation" == dataset.questionId) {
                                    donneesTechniques.setToitureModeIsolation(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                } else if ("toiture_nature_isolant" == dataset.questionId) {
                                    donneesTechniques.setToitureNatureIsolant(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                } else if ("toiture_vegetalisee" == dataset.questionId) {
                                    donneesTechniques.setToitureVegetalisee(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                } else if ("type_menuiserie" == dataset.questionId) {
                                    donneesTechniques.setTypeMenuiserie(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                } else if ("type_pm" == dataset.questionId) {
                                    donneesTechniques.setTypePm(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                } else if ("vecteur_energie_principal_ch" == dataset.questionId) {
                                    donneesTechniques.setVecteurEnergiePrincipalCh(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                } else if ("vecteur_energie_principal_ecs" == dataset.questionId) {
                                    donneesTechniques.setVecteurEnergiePrincipalEcs(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                } else if ("vecteur_energie_principal_fr" == dataset.questionId) {
                                    donneesTechniques.setVecteurEnergiePrincipalFr(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                } else if ("generateur_principal_ch" == dataset.questionId) {
                                    donneesTechniques.setGenerateurPrincipalCh(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                } else if ("generateur_ch_liaison" == dataset.questionId) {
                                    donneesTechniques.setGenerateurChLiaison(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                } else if ("generateur_principal_ecs" == dataset.questionId) {
                                    donneesTechniques.setGenerateurPrincipalEcs(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                } else if ("generateur_principal_fr" == dataset.questionId) {
                                    donneesTechniques.setGenerateurPrincipalFr(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                } else if ("type_ventilation_principale" == dataset.questionId) {
                                    donneesTechniques.setTypeVentilationPrincipale(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                } else if ("ccmi" == dataset.questionId) {
                                    batiment.setCcmi(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                } else if ("emetteur_principal_ch" == dataset.questionId) {
                                    donneesTechniques.setEmetteurPrincipalCh(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                } else if ("generateur_appoint_ch" == dataset.questionId) {
                                    donneesTechniques.setGenerateurAppointCh(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                } else if ("commentaires_prod_electricite" == dataset.questionId) {
                                    donneesTechniques.setCommentairesProdElectricite(answer)
                                } else if ("stockage_electricite" == dataset.questionId) {
                                    donneesTechniques.setStockageElectricite(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                } else if ("gestion_active" == dataset.questionId) {
                                    donneesTechniques.setGestionActive(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                } else if ("type_eclairage" == dataset.questionId) {
                                    donneesTechniques.setTypeEclairage(numberUtil.answerToInteger(answer, decimalFormat, dataset))
                                }
                            }
                        }
                    }
                }
                batiment.setSigneQualite(signeQualite)
                batiment.setCertificateur(certificateur)
                batiment.setGps(gps)
                batiment.setVerificateur(verificateur)
                batiment.setDonneesTechniques(donneesTechniques)

                if (store?.maquetteNumerique != null) {
                    batiment.setMaquetteNumerique(store.maquetteNumerique)
                }

                if (store?.dureeChantier != null) {
                    batiment.setDureeChantier(store.dureeChantier)
                }
                dataComp.getBatiment().add(batiment)
            }
        }
    }
}
