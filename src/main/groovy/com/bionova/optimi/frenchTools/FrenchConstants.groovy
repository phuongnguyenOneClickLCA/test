package com.bionova.optimi.frenchTools

import com.bionova.optimi.core.Constants
import com.bionova.optimi.xml.re2020RSEnv.RSEnv.SortieProjet.Batiment.Zone

/**
 * Constants for French tools
 *
 * Note: quite many still are located in {@link Constants}. Can move them here when convenient
 */
class FrenchConstants {

    static final List<String> FEC_TOOLS = ["lcaForFranceECSimplified", "lcaForFranceECComplete"]
    static final List<String> RE2020_TOOLS = ["lcaForRE2020_carbonOnly", "lcaForRE2020"]
    static final String LCA_RE2020 = "lcaForRE2020"
    static final List<String> TOOLS_WITH_PARCELLE = RE2020_TOOLS
    static final List<String> RSET_TOOLS = FEC_TOOLS + RE2020_TOOLS
    static final List<String> RSET_QUESTIONS = [FEC_RSET_QUESTIONID, RE2020_RSET_QUESTIONID]
    static final List<String> FR_TOOLS = RSET_TOOLS + ["lcaForFranceEC_PCE"]

    //queryIds
    static final String FEC_QUERYID_PROJECT_LEVEL = "ProjectLevelQueryFEC"
    static final String PROJECT_DESCRIPTION_FEC_QUERYID = "projectDescriptionFEC"
    static final String CONSTRUCTION_FEC_QUERYID = "constructionFECQuery"
    static final String ENERGY_AND_WATER_FEC_QUERYID = "energyAndWaterFEC"
    static final String EXPORTED_ENERGY_FEC_QUERYID = "exportedEnergyFEC"

    // questionIds
    static final String RE2020_RSET_QUESTIONID = "rsetRe2020"
    static final String NUM_PEBN_QUESTIONID = "num_PEBN"
    static final String VERSION_RSEE_QUESTIONID = "version_RSEE"
    static final String LOGICIEL_NOM_QUESTIONID = "logiciel-nom"
    static final String NOM_MOA_QUESTIONID = "nom_MOA"
    static final String ADRESSE_MOA_QUESTIONID = "adresse_MOA"
    static final String SIRET_MOA_QUESTIONID = "SIRET_MOA"
    static final String PHONE_MOA_QUESTIONID = "phone_MOA"
    static final String MAIL_MOA_QUESTIONID = "mail_MOA"
    static final String TYPE_MOA_QUESTIONID = "type_MOA"
    static final String ID_BAN_MOA_QUESTIONID = "idBAN_MOA"
    static final String DATE_APPEL_MOA_QUESTIONID = "date_appel_MOA"
    static final String ADRESS_MOA_QUESTIONID = "adress_MOA"
    static final String HOUSE_NUMBER_MOA_QUESTIONID = "housenumber_MOA"
    static final String STREET_MOA_QUESTIONID = "street_MOA"
    static final String COMPLEMENT_MOA_QUESTIONID = "complement_MOA"
    static final String ADRESS_NAME_MOA_QUESTIONID = "adress_name_MOA"
    static final String POSTCODE_MOA_QUESTIONID = "postcode_MOA"
    static final String CITY_CODE_MOA_QUESTIONID = "citycode_MOA"
    static final String CITY_MOA_QUESTIONID = "city_MOA"
    static final String DISTRICT_MOA_QUESTIONID = "district_MOA"
    static final String NOM_MOE_QUESTIONID = "nom_MOE"
    static final String ADRESSE_MOE_QUESTIONID = "adresse_MOE"
    static final String PHONE_MOE_QUESTIONID = "phone_MOE"
    static final String MAIL_MOE_QUESTIONID = "mail_MOE"
    static final String ID_BAN_MOE_QUESTIONID = "idBAN_MOE"
    static final String DATE_APPEL_MOE_QUESTIONID = "date_appel_MOE"
    static final String ADRESS_MOE_QUESTIONID = "adress_MOE"
    static final String HOUSE_NUMBER_MOE_QUESTIONID = "housenumber_MOE"
    static final String STREET_MOE_QUESTIONID = "street_MOE"
    static final String COMPLEMENT_MOE_QUESTIONID = "complement_MOE"
    static final String ADRESS_NAME_MOE_QUESTIONID = "adress_name_MOE"
    static final String POSTCODE_MOE_QUESTIONID = "postcode_MOE"
    static final String CITY_CODE_MOE_QUESTIONID = "citycode_MOE"
    static final String CITY_MOE_QUESTIONID = "city_MOE"
    static final String DISTRICT_MOE_QUESTIONID = "district_MOE"
    static final String NOM_BET_QUESTIONID = "nom_BET"
    static final String SIRET_BET_QUESTIONID = "SIRET_BET"
    static final String PHONE_BET_QUESTIONID = "phone_BET"
    static final String MAIL_BET_QUESTIONID = "mail_BET"
    static final String ID_BAN_BET_QUESTIONID = "idBAN_BET"
    static final String DATE_APPEL_BET_QUESTIONID = "date_appel_BET"
    static final String ADRESS_BET_QUESTIONID = "adress_BET"
    static final String HOUSE_NUMBER_BET_QUESTIONID = "housenumber_BET"
    static final String STREET_BET_QUESTIONID = "street_BET"
    static final String COMPLEMENT_BET_QUESTIONID = "complement_BET"
    static final String ADRESS_NAME_BET_QUESTIONID = "adress_name_BET"
    static final String POST_CODE_BET_QUESTIONID = "postcode_BET"
    static final String CITY_CODE_BET_QUESTIONID = "citycode_BET"
    static final String CITY_BET_QUESTIONID = "city_BET"
    static final String DISTRICT_BET_QUESTIONID = "district_BET"
    static final String DATE_ETUDE_QUESTIONID = "date_etude"
    static final String EDITEUR_QUESTIONID = "editeur"
    static final String VERSION_QUESTIONID = "version"
    static final String NOM_BUREAU_CONTROLE_QUESTIONID = "nom_bureau_controle"
    static final String ADRESSE_BUREAU_CONTROLE_QUESTIONID = "adresse_bureau_controle"
    static final String MAIL_BUREAU_CONTROLE_QUESTIONID = "mail_bureau_controle"
    static final String PHONE_BUREAU_CONTROLE_QUESTIONID = "phone_bureau_controle"
    static final String ID_BAN_BUREAU_CONTROLE_QUESTIONID = "idBAN_bureau_controle"
    static final String DATE_APPEL_BUREAU_CONTROLE_QUESTIONID = "date_appel_bureau_controle"
    static final String ADRESS_BUREAU_CONTROLE_QUESTIONID = "adress_bureau_controle"
    static final String HOUSE_NUMBER_BUREAU_CONTROLE_QUESTIONID = "housenumber_bureau_controle"
    static final String STREET_BUREAU_CONTROLE_QUESTIONID = "street_bureau_controle"
    static final String COMPLEMENT_BUREAU_CONTROLE_QUESTIONID = "complement_bureau_controle"
    static final String ADRESS_NAME_BUREAU_CONTROLE_QUESTIONID = "adress_name_bureau_controle"
    static final String POSTCODE_BUREAU_CONTROLE_QUESTIONID = "postcode_bureau_controle"
    static final String CITY_CODE_BUREAU_CONTROLE_QUESTIONID = "citycode_bureau_controle"
    static final String CITY_BUREAU_CONTROLE_QUESTIONID = "city_bureau_controle"
    static final String DISTRICT_BUREAU_CONTROLE_QUESTIONID = "district_bureau_controle"
    static final String ENTREPRISE_NOM_QUESTIONID = "entreprise-nom"
    static final String NOM_QUESTIONID = "nom"
    static final String DESCRIPTION_QUESTIONID = "description"
    static final String NUM_PERMIS_QUESTIONID = "num_permis"
    static final String DATE_DEPOT_PC_QUESTIONID = "date_depot_PC"
    static final String DATE_OBTENTION_PC_QUESTIONID = "date_obtention_PC"
    static final String DATE_OBTENTION_PA_GAZ_QUESTIONID = "date_obtention_pa_gaz"
    static final String DATE_APPROBATION_ZAC_GAZ_QUESTIONID = "date_approbation_ZAC_gaz"
    static final String ID_BAN_OPERATION_QUESTIONID = "idBAN_operation"
    static final String DATE_APPEL_OPERATION_QUESTIONID = "date_appel_operation"
    static final String ADRESS_OPERATION_QUESTIONID = "adress_operation"
    static final String HOUSE_NUMBER_OPERATION_QUESTIONID = "housenumber_operation"
    static final String COMPLEMENT_OPERATION_QUESTIONID = "complement_operation"
    static final String ADRESS_NAME_OPERATION_QUESTIONID = "adress_name_operation"
    static final String CITY_CODE_OPERATION_QUESTIONID = "citycode_operation"
    static final String DISTRICT_OPERATION_QUESTIONID = "district_operation"
    static final String ADRESSE_QUESTIONID = "adresse"
    static final String LIGNE_QUESTIONID = "ligne"
    static final String CODE_POSTAL_QUESTIONID = "code_postal"
    static final String COMMUNE_QUESTIONID = "commune"
    static final String DATE_LIVRAISON_QUESTIONID = "date_livraison"
    static final String GEOTECH_QUESTIONID = "geotech"
    static final String SOL_POLLUTION_QUESTIONID = "sol_pollution"
    static final String ZONE_CLIMATIQUE_QUESTIONID = "zone_climatique"
    static final String ZONE_SISMIQUE_QUESTIONID = "zone_sismique"
    static final String COMMENTAIRE_ACV_QUESTIONID = "commentaire_acv"
    static final String REFERENCE_CADASTRALE_QUESTIONID = "reference_cadastrale"
    static final String NOCC_QUESTIONID = "Nocc"
    static final String USAGE_PRINCIPAL_QUESTIONID = "usagePrincipal"
    static final String COMMENTAIRES_QUESTIONID = "commentaires"
    static final String SREF_QUESTIONID = "sref"
    static final String SREF_BUILDING_QUESTIONID = "srefBuilding"
    static final String NB_NIV_SSOL_QUESTIONID = "nb_niv_ssol"
    static final String NB_NIV_SURFACE_QUESTIONID = "nb_niv_surface"
    static final String LONGITUDE_QUESTIONID = "longitude"
    static final String LATITUDE_QUESTIONID = "latitude"
    static final String NB_PLACE_PARKING_INFRA_QUESTIONID = "nb_place_parking_infra"
    static final String NB_PLACE_PARKING_SUPRA_QUESTIONID = "nb_place_parking_supra"
    static final String NB_PLACE_PARKING_EXT_QUESTIONID = "nb_place_parking_ext"
    static final String COMMENTAIRES_DONNEES_TECHNIQUES_QUESTIONID = "commentaires_donnees_techniques"
    static final String TYPE_STRUCTURE_QUESTIONID = "type_structure"
    static final String ELEMENTS_PREFABRIQUES_QUESTIONID = "elements_prefabriques"
    static final String MATERIAU_PRINCIPAL_QUESTIONID = "materiau_principal"
    static final String MATERIAU_REMPLISSAGE_FACADE_QUESTIONID = "materiau_remplissage_facade"
    static final String MUR_MODE_ISOLATION_QUESTIONID = "mur_mode_isolation"
    static final String MUR_NATURE_ISOLANT_QUESTIONID = "mur_nature_isolant"
    static final String MUR_REVETEMENT_EXT_QUESTIONID = "mur_revetement_ext"
    static final String TYPE_FONDATION_QUESTIONID = "type_fondation"
    static final String TYPE_PLANCHER_QUESTIONID = "type_plancher"
    static final String PLANCHER_MODE_ISOLATION_QUESTIONID = "plancher_mode_isolation"
    static final String PLANCHER_NATURE_ISOLANT_QUESTIONID = "plancher_nature_isolant"
    static final String PLANCHER_NATURE_ESPACE_QUESTIONID = "plancher_nature_espace"
    static final String TYPE_TOITURE_QUESTIONID = "type_toiture"
    static final String TOITURE_MODE_ISOLATION_QUESTIONID = "toiture_mode_isolation"
    static final String TOITURE_NATURE_ISOLANT_QUESTIONID = "toiture_nature_isolant"
    static final String TOITURE_VEGETALISEE_QUESTIONID = "toiture_vegetalisee"
    static final String TOITURE_COUVERTURE_QUESTIONID = "toiture_couverture"
    static final String TYPE_MENUISERIE_QUESTIONID = "type_menuiserie"
    static final String TYPE_PM_RE2020_QUESTIONID = "type_pm_RE2020"
    static final String VECTEUR_ENERGIE_PRINCIPAL_CH_RE2020_QUESTIONID = "vecteur_energie_principal_ch_RE2020"
    static final String VECTEUR_ENERGIE_PRINCIPAL_FR_RE2020_QUESTIONID = "vecteur_energie_principal_fr_RE2020"
    static final String GENERATEUR_PRINCIPAL_CH_RE2020_QUESTIONID = "generateur_principal_ch_RE2020"
    static final String GENERATEUR_CH_LIAISON_QUESTIONID = "generateur_ch_liaison"
    static final String EMETTEUR_PRINCIPAL_CH_RE2020_QUESTIONID = "emetteur_principal_ch_RE2020"
    static final String GENERATEUR_APPOINT_CH_RE2020_QUESTIONID = "generateur_appoint_ch_RE2020"
    static final String GENERATEUR_PRINCIPAL_ECS_QUESTIONID = "generateur_principal_ecs"
    static final String GENERATEUR_PRINCIPAL_FR_RE2020_QUESTIONID = "generateur_principal_fr_RE2020"
    static final String TYPE_VENTILATION_PRINCIPALE_QUESTIONID = "type_ventilation_principale"
    static final String COMMENTAIRES_PROD_ELECTRICITE_QUESTIONID = "commentaires_prod_electricite"
    static final String STOCKAGE_ELECTRICITE_QUESTIONID = "stockage_electricite"
    static final String GESTION_ACTIVE_QUESTIONID = "gestion_active"
    static final String TYPE_ECLAIRAGE_QUESTIONID = "type_eclairage"
    static final String VERSION_RSENV_QUESTIONID = "version_RSEnv"
    static final String PHASE_QUESTIONID = "phase"
    static final String RESEAU_TYPE_QUESTIONID = "reseau_type"
    static final String RESEAU_NOM_QUESTIONID = "reseau_nom"
    static final String RESEAU_LOCALISATION_QUESTIONID = "reseau_localisation"
    static final String RESEAU_CONTENU_CO2_QUESTIONID = "reseau_contenu_co2"
    static final String EMPRISE_AU_SOL_QUESTIONID = "emprise_au_sol"
    static final String DUREE_CHANTIER_QUESTIONID = "duree_chantier"
    static final String USAGE_RE2020_QUESTIONID = "usageRE2020"
    static final String SCOMBLES_QUESTIONID = "Scombles"
    static final String NL_RE2020_QUESTIONID = "NL_RE2020"
    static final String LOTS_FRANCE_EC_QUESTIONID = "lotsFranceEC"
    static final String SUB_SECTIONS_FEC_QUESTIONID = "subSectionsFEC"
    static final String SURFACE_PARCELLE_QUESTIONID = "surface_parcelle"
    static final String SURFACE_ARROSEE_QUESTIONID = "surface_arrosee"
    static final String SURFACE_VEG_QUESTIONID = "surface_veg"
    static final String SURFACE_IMPER_QUESTIONID = "surface_imper"
    static final String ENERGY_USE_RE2020_QUESTIONID = "energyUse_RE2020"
    static final String SDP_QUESTIONID = "sdp"
    static final String PLACE_PARKING_SURF_FEC_QUESTIONID = "placeParkingSurfFEC"
    static final String PLACE_PARKING_SOUTERRAINF_FEC_QUESTIONID = "placeParkingSouterrainfFEC"
    static final String CONSTRUCTION_ENERGY_USE_QUESTIONID = "constructionEnergyUse"
    static final String SITE_OPERATIONS_QUESTIONID = "siteOperations"
    static final String CONSTRUCTION_WATER_USE_QUESTIONID = "constructionWaterUse"
    static final String ENERGY_PER_SDP_QUESTIONID = "energy_per_SDP"
    static final String TOTAL_CONSUMED_WATER_M3_QUESTIONID = "totalConsumedWater_m3"
    static final String EARTH_MASSES_QUESTIONID = "earthMasses"
    static final String TRANSPORT_RESOURCE_ID_FEC_QUESTIONID = "transportResourceIdFEC"
    static final String TRANSPORT_DISTANCE_KM_FEC_QUESTIONID = "transportDistance_kmFEC"
    static final String ENGAGEMENT_IC_CONSTRUCTION_QUESTIONID = "engagement_ic_construction"
    static final String SREF_BAT_EXISTANT_QUESTIONID = "sref_bat_existant"
    static final String MIGEO_QUESTIONID = "migeo"
    static final String MICOMBLES_QUESTIONID = "micombles"
    static final String MISURF_QUESTIONID = "misurf"
    static final String ICCONSTRUCTION_MAXMOYEN_QUESTIONID = "Icconstruction_maxmoyen"
    static final String MCGEO_QUESTIONID = "mcgéo"
    static final String MCCOMBLES_QUESTIONID = "mccombles"
    static final String MCSURF_MOY_QUESTIONID = "mcsurf_moy"
    static final String MCSURF_TOT_QUESTIONID = "mcsurf_tot"
    static final String MCCAT_QUESTIONID = "mccat"
    static final String ICENERGIE_MAXMOYEN_QUESTIONID = "Icenergie_maxmoyen"

    // sectionIds
    static final String DONNEES_ADMINISTRATIVES_SECTIONID = "donneesAdministratives"
    static final String LES_ACTEURS_DU_PROJET_RE2020_SECTIONID = "lesActeursDuProjet_RE2020"
    static final String LES_ACTEURS_DU_PROJET_SECTIONID = "lesActeursDuProjet"
    static final String DESCRIPTION_PROJET_ET_SES_CONTRAINTES_SECTIONID = "descriptionProjetEtSesContraintes"
    static final String DESCRIPTION_BATIMENT_SECTIONID = "descriptionBatiment"
    static final String DESCRIPTION_BATIMENT_RE2020_SECTIONID = "descriptionBatimentRE2020"
    static final String DESCRIPTION_PER_ZONE_SECTIONID = "descriptionPerZone"
    static final String DESCRIPTION_PER_ZONE_RE2020_SECTIONID = "descriptionPerZoneRE2020"
    static final String DESCRIPTION_TECHNIQUE_BATIMENT_SECTIONID = "descriptionTechniqueBatiment"
    static final String ENERGY_AND_WATER_USE_SECTIONID = "energyAndWaterUse"
    static final String EARTH_MASSES_SECTIONID = "earthMasses"
    static final String CONSTRUCTION_MATERIALS_SECTIONID = "constructionMaterials"
    static final String ENERGY_SECTIONID = "energy"
    static final String WATER_SECTIONID = "water"
    static final String EXPORTED_ELECTRICITY_PV_SECTIONID = "exportedElectricityPV"
    static final String EXPORTED_ELECTRICITY_CHP_SECTIONID = "exportedElectricityCHP"

    // calculationRuleIds
    static final String GWP_M2_SREF_CALC_RULEID = "GWP_m2SREF"
    static final String GWP_NOCC_CALC_RULEID = "GWP_Nocc"
    static final String STOCKC_KGC_CALC_RULEID = "stockC_kgC"
    static final String RATIO_GWP_CALC_RULEID = "ratioGWP"
    static final String GWP_TOTAL_CALC_RULEID = "GWP_total"

    // resultCategoryIds
    static final String CONSTRUCTION_RESULT_CATID = "construction"
    static final String ENERGIE_RESULT_CATID = "energie"
    static final String EAU_RESULT_CATID = "eau"
    static final String BATIMENT_RESULT_CATID = "batiment"
    static final String PARCELLE_RESULT_CATID = "parcelle"
    static final String UDD_RESULT_CATID = "UDD"
    static final String DED_LOT3_13_RESULT_CATID = "ded_lot3_13"
    static final String IC_CONSTRUCTION_MAX_RESULT_CATID = "Icconstruction_max"
    static final String IC_ENERGIE_MAX_RESULT_CATID = "Icenergie_max"
    static final String MIGEO_RESULT_CATID = "Migeo"
    static final String MIINFRA_RESULT_CATID = "Miinfra"
    static final String MIVRD_RESULT_CATID = "Mivrd"
    static final String MIDED_RESULT_CATID = "Mided"
    static final String IC_CONSTRUCTION_MAXMOYEN_RESULT_CATID = "Icconstruction_maxmoyen"
    static final String BE_RESULT_CATID = "Be"
    static final String A1_A3_RE2020_RESULT_CAT_ID = "A1-A3-RE2020"
    static final String A1_A3_CONSTRUCTION_RESULT_CAT_ID = "A1-A3-construction"
    static final String COMPOSANTS_CHP_RESULT_CAT_ID = "composants_CHP"
    static final String COMPOSANTS_PV_RESULT_CAT_ID = "composants_PV"
    static final String A4_A5_RE2020_RESULT_CAT_ID = "A4-A5-RE2020"
    static final String CONSTRUCTION_SITE_RESULT_CATID = "constructionSite"
    static final String CONSTRUCTION_SITE_TRANSPORT_RESULT_CATID = "constructionSiteTransport"
    static final String A4_CONSTRUCTION_RESULT_CATID = "A4-construction"
    static final String A5_CONSTRUCTION_RESULT_CATID = "A5-construction"
    static final String B1_B4_RE2020_RESULT_CAT_ID = "B1-B4-RE2020"
    static final String B_C_REFRIGERANT_RESULT_CAT_ID = "B-C_refrigerant"
    static final String B6_RE2020_RESULT_CATID = "B6-RE2020"
    static final String B7_RE2020_RESULT_CATID = "B7-RE2020"
    static final String C1_C4_RE2020_RESULT_CAT_ID = "C1-C4-RE2020"
    static final String C1_CONSTRUCTION_RESULT_CATID = "C1-construction"
    static final String C2_CONSTRUCTION_RESULT_CATID = "C2-construction"
    static final String C3_CONSTRUCTION_RESULT_CATID = "C3-construction"
    static final String C4_CONSTRUCTION_RESULT_CATID = "C4-construction"
    static final String D_RE2020_RESULT_CAT_ID = "D-RE2020"
    static final String D_CONSTRUCTION_RESULT_CATID = "D-construction"
    static final String COMPOSANTS_RESULT_CATID = "composants"
    static final String CHANTIER_RESULT_CATID = "chantier"
    static final String DED_TOTAL_RESULT_CATID = "ded_total"

    // FEC
    static final String PROJET_VERSION_FEC = "1.1.0.0"
    static final String BUILDING_ZONES_FEC_QUESTIONID = "buildingZonesFEC"
    static final String GWP_M2_SDP = "GWP_m2SDP"
    static final String T = "T"
    static final String GES_PCE = "gesPCE"
    static final String CARBON_EMBODIED1 = "carbonEmbodied1"
    static final String CARBONE_VALUE = "carboneValue"
    static final String CARBONE_VALUE2 = "carboneValue2"
    static final String CARBON_EMBODIED2 = "carbonEmbodied2"
    static final String CARBON_EMBODIED1BIS = "carbonEmbodied1bis"
    static final String CARBON_EMBODIED2BIS = "carbonEmbodied2bis"
    static final String EGES = "Eges"
    static final String EGESMAX1 = "Egesmax1"
    static final String EGESMAX2 = "Egesmax2"
    static final String EGES_PCE = "EgesPCE"
    static final String EGES_PCE_MAX1 = "EgesPCE,max1"
    static final String EGES_PCE_MAX2 = "EgesPCE,max2"
    static final String FEC_ADDITIONAL_QUESTIONID_SRT = "SRTFEC"
    static final String FEC_ZONE_AREA_RESOURCEID = "surfaceFEC"
    static final String FEC_RSET_QUESTIONID = "rset"
    static final String FEC_UNASSIGNED_ZONE = "00"
    static final String ZONE_1 = "1"
    static final String FEC_RSET_VERSION = "8.1.0.0"
    static final Integer MAX_LOT_NUMBERS = 14

    static final String DIESEL_FUEL_RESOURCEID = "inies5554"
    static final String EXCAVATED_LAND_RESOURCEID = "iniesEarthC"
    static final String DUMP_TRUCK_TRANSPORT_RESOURCEID = "inies5555"
    static final String IMPORTED_EARTH_RESOURCEID = "iniesEarthB"
    static final List<String> TRANSPORTED_EARTH_RESOURCEIDS = ["inies5548", "iniesEarthA", "iniesEarthB"]

    static final String RE2020_RSET_VERSION = "2022.E1.0.0"
    static final String RE2020_PROJET_VERSION = "2022.D1E1C1"
    static final String RE2020_RSENV_VERSION = "2022.C1.0.0"
    static final String RE2020_DATA_COMP_VERSION = "2022.D1.0.0"

    static final String RE2020_RSET_VERSION_OLD = "2021.E1.0.0"

    static final Integer PERIODE_REFERENCE = 50

    // RE2020
    static final String DEFAULT_OCL_DATA_COMP_ANSWER = "One Click LCA©"
    static final String ZONE_PARCELLE = "parcelle"
    static final String PARCELLE_CHECKBOX_NAME_PREFIX = 'parcelleSelect'
    static final String NETWORK_RE2020 = "networkRE2020"

    // XML
    static final List<Integer> ALL_LOTS = (1..13)
    static final List<String> RSEE_EXPORT_XML = [FRANCE_ENERGIE_CARBONE_XML, RE2020_XML]
    static final String RE2020_XML = "RE2020"
    static final String FRANCE_ENERGIE_CARBONE_XML = "FranceEnergieCarbone"
    static final String REUSED_MATERIAL_ID_FICHE = "27546"
    static final String REUSED_MATERIAL_ID_PRODUIT = "INIES_ICOM20211011_160700"

    static final List<String> EXCLUDE_CALCULATION_RULES = ["carboneValue", "mass", "GWP_m2SDP", "carboneValue2"]
    static final List<String> TYPE_DONNEES = ["FDES", "PEP", "MDEGD_FDES", "MDEGD_PEP", "DES", "Lot forfaitaire", "DED_FDES", "DED_PEP", "Configurateurs", "Composant vide", "DED interpolée", "Composant réemploi"]
    static final Map<String, Integer> TYPE_DONNEES_RE2020 = ["FDES": 1, "PEP": 2, "DED_FDES": 3, "MDEGD_FDES": 3, "DED_PEP": 4, "DES": 5, "Lot forfaitaire": 6, "Configurateurs": 7, "Composant vide": 8, "DED interpolée": 9, "Composant réemploi": 10]
    static final Integer TYPE_DONNEES_IGNORE_REUSED_MATERIAL_RE2020 = 10
    static final Integer TYPE_DONNEES_PRIVATE_MATERIAL_RE2020 = 7
    static final Map<String, Integer> UNITE_UFS = [
            "dm2"  : 1,
            "m2"   : 2,
            "kg"   : 3,
            "m3"   : 4,
            "m"    : 5,
            "mj"   : 6,
            "kwh"  : 7,
            "l"    : 8,
            "g"    : 9,
            "cm"   : 10,
            "ml"   : 11,
            "%"    : 12,
            "unit" : 13,
            "mg"   : 18,
            "mm3"  : 19,
            "g/m2" : 22,
            "m3/UF": 24,
            "kg/m2": 25,
            "kWhep": 26,
            "ton"  : 31,
            "km"   : 37,
            "kwc"  : 46,
            "tonkm": 47,
            "kw"   : 52
    ]

    static final Map<Integer, String> PLACEHOLDER_UNIT_TYPE = [27: "unit"]

    static final Map<String, String> MAP_SOUS_LOTS_TO_IFC = [
            "1.1" : "SITE",
            "1.2" : "SITE",
            "1.3" : "SITE",
            "2.1" : "FOUNDATION",
            "2.2" : "FOUNDATION",
            "2.3" : "FOUNDATION",
            "3.1" : "SLAB",
            "3.2" : "BEAM",
            "3.3" : "EXTERNAL WALL",
            "3.4" : "COLUMN",
            "3.5" : "COLUMN",
            "3.6" : "STAIRS",
            "3.7" : "EXTERNAL WALL",
            "3.8" : "EXTERNAL WALL",
            "4.1" : "ROOF",
            "4.2" : "ROOF",
            "4.3" : "ROOF",
            "5.1" : "INTERNAL WALL",
            "5.2" : "VERTICAL FINISH",
            "5.3" : "HORIZONTAL FINISH",
            "5.4" : "HORIZONTAL FINISH",
            "5.5" : "FINISH",
            "6.1" : "EXTERNAL WALL",
            "6.2" : "WINDOW",
            "6.3" : "EXTERNAL WALL",
            "7.1" : "HORIZONTAL FINISH",
            "7.2" : "VERTICAL FINISH",
            "7.3" : "FINISH",
            "8.1" : "BUILDINGTECH",
            "8.2" : "BUILDINGTECH",
            "8.3" : "BUILDINGTECH",
            "8.4" : "BUILDINGTECH",
            "8.5" : "BUILDINGTECH",
            "9.1" : "BUILDINGTECH",
            "9.2" : "BUILDINGTECH",
            "10.1": "BUILDINGTECH",
            "10.2": "BUILDINGTECH",
            "10.3": "BUILDINGTECH",
            "10.4": "BUILDINGTECH",
            "10.5": "BUILDINGTECH",
            "10.6": "BUILDINGTECH",
            "11.1": "BUILDINGTECH",
            "11.2": "BUILDINGTECH",
            "11.3": "BUILDINGTECH",
            "12"  : "BUILDINGTECH",
            "13"  : "BUILDINGTECH"
    ]

    static final Map<String, String> MAP_LOTS_TO_IFC = [
            "1" : "SITE",
            "2" : "FOUNDATION",
            "3" : "EXTERNAL WALL",
            "4" : "ROOF",
            "5" : "INTERNAL WALL",
            "6" : "EXTERNAL WALL",
            "7" : "FINISH",
            "8" : "BUILDINGTECH",
            "9" : "BUILDINGTECH",
            "10": "BUILDINGTECH",
            "11": "BUILDINGTECH",
            "12": "BUILDINGTECH",
            "13": "BUILDINGTECH",
            "14": "REFRIGERANTS"
    ]

    static final Map<Integer, String> MAP_VALEURS_SEUILS_RESULT_CATEGOR_IDS = [1: "A1", 2: "A2", 3: "carbonEmbodied1a", 4: "carbonEmbodied2a", 5: "m1", 6: "m2", 7: "alpha1", 8: "alpha2"]

    static final List<String> ILCD_OUTPUTS = ["wasteHazardous_kg", "wasteNonHazardous_kg", "wasteRadioactive_kg", "reusableMaterialsOutput_kg", "recyclableMaterialsOutput_kg", "energyMaterialsOutput_kg",
                                              "exportedEnergyOutput_MJ", "waterPollution_m3", "airPollution_m3", "wasteRadioactiveHigh_kg", "particulateMatter_incidence", "ecoToxicityFreshwater_CTUe", "humanToxicityCancer_CTUh",
                                              "humanToxicityNonCancer_CTUh", "potentialSoilQualityIndex"]
    static final List<String> ILCD_INPUTS = ["nonRenewablesUsedAsEnergy_MJ", "nonRenewablesUsedAsMaterial_MJ", "renewablesUsedAsEnergy_MJ", "renewablesUsedAsMaterial_MJ", "recyclingMaterialUse_kg", "renewableRecylingFuelUse_MJ",
                                             "nonRenewableRecylingFuelUse_MJ", "cleanWaterNetUse_m3", "impactPERRT_MJ", "impactPERNRT_MJ", "resourceDepletionWater_m3", "humanToxiHTP_kgDCB", "freshWaterToxiFAETP_kgDCB", "seaWaterToxiMAETP_kgDCB",
                                             "terrestrialToxiTETP_kgDCB", "ionisingRadiation_kgU235eq", "particulateMatter_kgPM25eq", "impactODP_WMO1999_kgCFC11e", "landTransformation_m2", "shadowPrice", "traciNRPE_MJ"]

    static Map<String, Integer> ENERGIE_VECTEUR_REF = [
            "electricity"   : 1,
            "naturalGasFEC" : 2,
            "heatingOilFEC" : 3,
            "woodPelletsFEC": 4,
            "woodLogsFEC"   : 5,
            "crushedWoodFEC": 6,
            "networkFEC"    : 7,
            "coalFEC"       : 8,
    ]

    static final enum ChantierConsommationsRef {
        NINE(9),
        TEN(10),
        ELEVEN(11),
        TWELVE(12),
        THIRTEEN(13)

        private int ref

        ChantierConsommationsRef(Integer ref) { this.ref = ref }

        Integer getRef() { return ref }

        static final Map<String, Integer> RESOURCE_GROUP_REF = [
                "electricityFEC": 1,
                "naturalGasFEC" : 2,
                "heatingOilFEC" : 3,
                "woodPelletsFEC": 4,
                "woodLogsFEC"   : 5,
                "crushedWoodFEC": 6,
                "networkFEC"    : 7,
                "coalFEC"       : 8,
        ]
    }

    static Map<String, Integer> CALC_RULEID_REF_RE2020 = [
            "GWP_noDiscount"                : 1,
            "ODP"                           : 2,
            "AP"                            : 3,
            "EP"                            : 4,
            "POCP"                          : 5,
            "ADP-mineral"                   : 6,
            "ADP-fossil"                    : 7,
            "renewablesUsedAsEnergy_MJ"     : 8,
            "renewablesUsedAsMaterial_MJ"   : 9,
            "renewablesUsedTotal_MJ"        : 10,
            "nonRenewablesUsedAsEnergy_MJ"  : 11,
            "nonRenewablesUsedAsMaterial_MJ": 12,
            "nonRenewablesUsedTotal_MJ"     : 13,
            "recyclingMaterialUse_kg"       : 14,
            "renewableRecylingFuelUse_MJ"   : 15,
            "nonRenewableRecylingFuelUse_MJ": 16,
            "cleanWaterNetUse_m3"           : 17,
            "wasteHazardous_kg"             : 18,
            "wasteNonHazardous_kg"          : 19,
            "wasteRadioactive_kg"           : 20,
            "reusableMaterialsOutput_kg"    : 21,
            "recyclableMaterialsOutput_kg"  : 22,
            "energyMaterialsOutput_kg"      : 23,
            "exportedEnergyOutput_MJ"       : 24
    ]

    /*
        "A1-A3" : 1,
        "A4-A5" : 2,
        "B" : 3,
        "C" : 4,
        "D" : 5,
        "Be" : 6
     */
    static Map<String, String> RESULT_CAT_REF_RE2020_A1_BE = [
            (A1_A3_RE2020_RESULT_CAT_ID)              : "A1-A3",
            (A1_A3_CONSTRUCTION_RESULT_CAT_ID)        : "A1-A3",
            (COMPOSANTS_CHP_RESULT_CAT_ID)            : "A1-A3",
            (COMPOSANTS_PV_RESULT_CAT_ID)             : "A1-A3",
            (A4_A5_RE2020_RESULT_CAT_ID)              : "A4-A5",
            (CONSTRUCTION_SITE_RESULT_CATID)          : "A4-A5",
            (CONSTRUCTION_SITE_TRANSPORT_RESULT_CATID): "A4-A5",
            (A4_CONSTRUCTION_RESULT_CATID)            : "A4-A5",
            (A5_CONSTRUCTION_RESULT_CATID)            : "A4-A5",
            (B1_B4_RE2020_RESULT_CAT_ID)              : "B",
            (B_C_REFRIGERANT_RESULT_CAT_ID)           : "B",
            (B6_RE2020_RESULT_CATID)                  : "B",
            (B7_RE2020_RESULT_CATID)                  : "B",
            (C1_C4_RE2020_RESULT_CAT_ID)              : "C",
            (C1_CONSTRUCTION_RESULT_CATID)            : "C",
            (C2_CONSTRUCTION_RESULT_CATID)            : "C",
            (C3_CONSTRUCTION_RESULT_CATID)            : "C",
            (C4_CONSTRUCTION_RESULT_CATID)            : "C",
            (D_RE2020_RESULT_CAT_ID)                  : "D",
            (D_CONSTRUCTION_RESULT_CATID)             : "D",
            (BE_RESULT_CATID)                         : "Bexp"
    ]

    static Set<String> RESULT_CATEGORIES_FOR_RESULTS = ["A1-A3", "B", "C", "D", "Bexp"]
    static final int BE_REF_DUMMY = 7
    static final int EXPORTED_ENERGY_REF = 8
    static final String ID_FICHE_DUMMY = "0000"
    static final String ID_PRODUIT_DUMMY = "idproduit"
    static final int PARTIE_COMMUNE = 0
    static final int ID_BASE_INIES = 0
    static final BigDecimal TOTAL_ASSESSMENT = BigDecimal.valueOf(50d)

    static final Set<String> COMPOSANT_RESULT_CATEGORIES_RE2020 = [A1_A3_RE2020_RESULT_CAT_ID, COMPOSANTS_CHP_RESULT_CAT_ID, COMPOSANTS_PV_RESULT_CAT_ID, A4_A5_RE2020_RESULT_CAT_ID, B1_B4_RE2020_RESULT_CAT_ID,
                                                                   B_C_REFRIGERANT_RESULT_CAT_ID, C1_C4_RE2020_RESULT_CAT_ID, D_RE2020_RESULT_CAT_ID]
    static final Set<String> ENERGIE_RESULT_CATEGORIES_RE2020 = [B6_RE2020_RESULT_CATID, BE_RESULT_CATID]
    static final Set<String> CHANTIER_RESULT_CATEGORIES_RE2020 = [CONSTRUCTION_SITE_RESULT_CATID, CONSTRUCTION_SITE_TRANSPORT_RESULT_CATID, A4_CONSTRUCTION_RESULT_CATID, A5_CONSTRUCTION_RESULT_CATID,
                                                                  C1_CONSTRUCTION_RESULT_CATID, C2_CONSTRUCTION_RESULT_CATID, C3_CONSTRUCTION_RESULT_CATID, C4_CONSTRUCTION_RESULT_CATID, D_CONSTRUCTION_RESULT_CATID]
    static final Set<String> INDICATEUR_PERF_ENV_CALC_RULE_IDS = [GWP_M2_SREF_CALC_RULEID, GWP_NOCC_CALC_RULEID, STOCKC_KGC_CALC_RULEID, RATIO_GWP_CALC_RULEID, GWP_TOTAL_CALC_RULEID]
    static final Set<String> COEFMOD_ICCONSTRUCTION_RESULT_CATS = [MIINFRA_RESULT_CATID, MIVRD_RESULT_CATID, MIDED_RESULT_CATID]
    static final Set<String> EXPORTED_ENERGY_SECTIONIDS = [EXPORTED_ELECTRICITY_CHP_SECTIONID, EXPORTED_ELECTRICITY_PV_SECTIONID]

    static final String PRIVATE_DATA_SOURCE = "private"

    static final enum IndicateurPerfEnv {
        IC_CONSTRUCTION('icConstruction'),
        IC_CONSTRUCTION_MAX('icConstructionMax'),
        COEF_MOD_ICCONSTRUCTION('coefModIcconstruction'),
        IC_CONSTRUCTION_OCC('icConstructionOcc'),
        IC_ENERGIE('icEnergie'),
        IC_ENERGIE_MAX('icEnergieMax'),
        COEF_MOD_ICENERGIE('coefModIcenergie'),
        IC_ENERGIE_OCC('icEnergieOcc'),
        IC_EAU('icEau'),
        IC_BATIMENT('icBatiment'),
        IC_BATIMENT_OCC('icBatimentOcc'),
        IC_ZONE('icZone'),
        IC_ZONE_OCC('icZoneOcc'),
        IC_PARCELLE('icParcelle'),
        IC_PROJET('icProjet'),
        IC_PROJET_OCC('icProjetOcc'),
        STOCK_C_BATIMENT('stockCBatiment'),
        STOCK_C_PARCELLE('stockCParcelle'),
        STOCK_C_ZONE('stockC'),
        UDD('udd'),
        UDD_ZONE('uddZone'),
        IC_DED('icDed'),
        IC_COMPOSANT('icComposant'),
        IC_CHANTIER('icChantier'),

        private String attribute

        private IndicateurPerfEnv(String attribute) {
            this.attribute = attribute
        }

        String getAttribute() {
            return attribute
        }

        static final Map<String, BigDecimal> createEmptyPerfEnvMap(Boolean forZone = false) {
            /**
             * This map cannot have coefModIcconstruction and coefModIcenergie
             * @see Zone.IndicateurPerfEnv#coefModIcconstruction
             * @see Zone.IndicateurPerfEnv#coefModIcenergie
             */
            Map<String, BigDecimal> map = [
                    (IC_CONSTRUCTION.attribute)    : BigDecimal.ZERO,
                    (IC_CONSTRUCTION_MAX.attribute): BigDecimal.ZERO,
                    (IC_CONSTRUCTION_OCC.attribute): BigDecimal.ZERO,
                    (IC_ENERGIE.attribute)         : BigDecimal.ZERO,
                    (IC_ENERGIE_MAX.attribute)     : BigDecimal.ZERO,
                    (IC_ENERGIE_OCC.attribute)     : BigDecimal.ZERO,
                    (IC_EAU.attribute)             : BigDecimal.ZERO,
                    (IC_PARCELLE.attribute)        : BigDecimal.ZERO,
                    (IC_PROJET.attribute)          : BigDecimal.ZERO,
                    (IC_PROJET_OCC.attribute)      : BigDecimal.ZERO,
                    (UDD.attribute)                : BigDecimal.ZERO,
                    (IC_DED.attribute)             : BigDecimal.ZERO,
                    (IC_COMPOSANT.attribute)       : BigDecimal.ZERO,
                    (IC_CHANTIER.attribute)        : BigDecimal.ZERO,
            ]

            if (forZone) {
                map.put(IC_ZONE.attribute, BigDecimal.ZERO)
                map.put(IC_ZONE_OCC.attribute, BigDecimal.ZERO)
                map.put(STOCK_C_ZONE.attribute, BigDecimal.ZERO)
            } else {
                map.put(IC_BATIMENT.attribute, BigDecimal.ZERO)
                map.put(IC_BATIMENT_OCC.attribute, BigDecimal.ZERO)
                map.put(STOCK_C_BATIMENT.attribute, BigDecimal.ZERO)
                map.put(STOCK_C_PARCELLE.attribute, BigDecimal.ZERO)
            }

            return map
        }
    }

    static final enum ContributeurRefFec {

        BUILDING(1),
        ENERGY(2),
        WATER_REFRIGERANT(3),
        CONSTRUCTION(4)

        private int ref

        ContributeurRefFec(Integer ref) { this.ref = ref }

        Integer getRef() { return ref }
    }

    static final enum SousContributeurRef {

        enum CHANTIER {
            ENERGY(1, "ENERGY SITE RE2020"),
            WATER(2, "WATER SITE RE2020"),
            EARTH_MASS(3, "EARTH MASS RE2020"),
            SITE_MATERIALS(4, "SITE MATERIALS RE2020")

            private int ref
            private String value

            private CHANTIER(Integer ref, String value) {
                this.ref = ref
                this.value = value
            }

            int intValue() {
                return ref
            }

            String toString() {
                return ref.toString()
            }

            static List<Integer> allRefs() {
                [1, 2, 3, 4]
            }

            static List<String> constructionEnergyQuestionIds() {
                ["constructionEnergyUse", "siteOperations"]
            }

            static List<String> constructionWaterQuestionIds() {
                ["constructionWaterUse"]
            }

            static CHANTIER getEnum(int ref) {
                return values().find { it.ref == ref}
            }

            String getValue() {
                return value
            }
        }

        enum ENERGIE {
            // dummy values for now
            BE(8)

            private int ref

            private ENERGIE(Integer ref) {
                this.ref = ref
            }

            int intValue() {
                return ref
            }

            // this doesn't have number 8, it's Be.
            static List<Integer> allRefs() {
                [1, 2, 3, 4, 5, 6, 7]
            }
        }

        enum EAU {
            DRINKING_WATER(1),
            WASTE_WATER(2),
            RAIN_WATER(3)

            private int ref

            private EAU(Integer ref) {
                this.ref = ref
            }

            int intValue() {
                return ref
            }

            String toString() {
                return ref.toString()
            }

            static List<Integer> allRefs() {
                [1, 2, 3]
            }

            static List<String> drinkingWaterResourceIds() {
                ['inies5553']
            }

            static List<String> wasteWaterResourceIds() {
                ['inies5550', 'inies5551']
            }

            static List<String> rainWaterResourceIds() {
                ['inies5550_rainWater', 'inies5551_rainWater']
            }
        }

    }

    // INIES Data request
    static final String ID_DEMANDE = "id_demande"
    static final String ID_DEMANDEUR = "id_demandeur"
    static final String COMPOSANT_VIDE_RESOURCE_ID = "INIES_ICOM20210120_120948_addedForRE2020"
    static final String COMPOSANT_VIDE_ID_FICHE = "25765"
    static final String COMPOSANT_VIDE_ID_PRODUIT = "INIES_ICOM20210120_120948"
    static final int COMPOSANT_VIDE_TYPE_DONNEES = 8

    static final enum ContributeurCategoryRe2020 {
        ENERGY("ENERGY RE2020"),
        WATER("WATER RE2020"),
        SITE("SITE RE2020"),
        EXPORTED_ENERGY("ENERGY EXPORTED RE2020")

        private String value

        ContributeurCategoryRe2020(String value) {
            this.value = value
        }

        String toString() {
            return value.toString()
        }

    }

}
