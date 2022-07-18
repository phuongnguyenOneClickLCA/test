/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable
import org.bson.types.ObjectId

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class Feature implements Serializable, Validateable {
    static mapWith = "mongo"
    ObjectId id
    String featureId
    String featureClass
    String name
    Boolean userLinked
    String regionReferenceId
    
    static constraints = {
        featureId unique: true
        featureClass nullable: true
        userLinked nullable: true
        regionReferenceId nullable: true
    }

    static transients = ['formattedName']

    // list of all featureIds we have, quite many are used in config.
    static final String ACCOUNT_PRIVATE_CLASSIFICATIONS = "accountPrivateClassifications"
    static final String ACCOUNT_PRIVATE_DATASETS = "accountPrivateDatasets"
    static final String ACCOUNT_PRIVATE_EPDCS = "accountPrivateEpdcs"
    static final String ADDITIONAL_IMPACTS_IN_DATA_CARD = "additionalImpactsInDataCard"
    static final String ALLOW_ADDING_ANY_USER = "allowAddingAnyUser"
    static final String ALLOW_IMPORT_PUBLIC_ENTITIES = "allowImportPublicEntities"
    static final String ALLOW_PUBLIC_ENTITIES = "allowPublicEntities"
    static final String ANNUAL_CHART = "annualChart"
    static final String API_SAMPLE_EXCEL = "apiSampleExcel"
    static final String AUTOMATIC_COST_GENERATION = "automaticCostGeneration"
    static final String BETA_FEATURES = "betaFeatures"
    static final String BETA_GRAPHS_FEATURE = "betaGraphsFeature"
    static final String BENCHMARK = "benchmark"
    static final String BIM_MODEL_CHECKER = "bimModelChecker"
    static final String CARBON_DESIGNER = "simulationTool"
    static final String CARBON_DESIGNER_FINNISH_RESIDENTIAL_BLD_ONLY = "lickey_finlandRegionSimulation_freeVersion"
    static final String CARBON_DESIGNER_ONLY = "carbonDesignerOnly"
    static final String CALCULATION_DETAILS = "calculationDetails"
    static final String CHAT_SUPPORT = "chatSupport"
    static final String COMPARE_MATERIAL = "compareMaterialFeature"
    static final String COMPILED_REPORT = "compiledReport"
    static final String COMPONENT_ABILITY = "componentAbility"
    static final String CREATE_CONSTRUCTIONS = "createConstructions"
    static final String DEBUG = "debugLicenseFeature"
    static final String DOWNLOAD_QUERY_DATA = "downloadQueryData"
    static final String ECOINVENT50 = "ecoinvent50"
    static final String ECOINVENT100 = "ecoinvent100"
    static final String ECOINVENT150 = "ecoinvent150"
    static final String ECOINVENT200 = "ecoinvent200"
    static final String ECOINVENT300 = "ecoinvent300"
    static final String ECOINVENT400 = "ecoinvent400"
    static final String ECOINVENT_INTERNAL_TEST = "ecoinventInternalTest"
    static final String ECOINVENT_UNCAPPED = "ecoinventUncapped"
    static final String EDIT_ORGANISATION_DATA_LISTS = "editOrganisationDataLists"
    static final String EPD_DOWNLOAD = "epdDownload"
    static final String EPD_DECLARE_MODULE_A4 = "epdDeclareModuleA4"
    static final String EPD_DECLARE_MODULE_A5 = "epdDeclareModuleA5"
    static final String EPD_DECLARE_MODULE_B1 = "epdDeclareModuleB1"
    static final String EPD_DECLARE_MODULE_B1_B7 = "epdDeclareModuleB1-B7"
    static final String EPD_ENABLE_BACKGROUND_REPORT_QUERY = "epdEnableBackgroundReportQuery"
    static final String EPD_ENABLE_DESCRIPTION_QUERIES = "epdEnableDescriptionQueries"
    static final String EPD_ILCD_EXPORT = "epdIlcdExport"
    static final String EPD_EN15804A1 = "EPD_EN15804A1"
    static final String EPD_EN15804A2 = "EPD_EN15804A2"
    static final String EPD_EN15804_A2_INIES = "EPD_EN15804A2_INIES"
    static final String EPD_EN15804A2_OPTIONAL = "EPD_EN15804A2_optional"
    static final String EPD_ISO14067_GWP_CATEGORIES = "EPD_ISO14067_GWP_categories"
    static final String EPD_LCA = "EPD_LCA"
    static final String EPD_TRACI = "EPD_TRACI"
    static final String EXPERT_PARAMETERS = "expertParameters"
    static final String EXPORT_TO_AUTOCASE = "exportToAutocase"
    static final String GCCA_DEMO = "GCCADemo"
    static final String GENERATE_PDF = "generatePdf"
    static final String GIVE_TASK = "giveTask"
    static final String GROUP_MATERIALS = "groupMaterials"
    static final String GWP_FOSSIL_ONLY = "gwpFossilOnly"
    static final String IMPORT_ARCHI_CAD = "importArchiCAD"
    static final String IMPORT_DESIGN_BUILDER = "importDesignBuilder"
    static final String IMPORT_IDA_ICE = "importIDA-ICE"
    static final String IMPORT_IDA_ICE_4_8_SP1 = "importIDA_ICE-4.8 SP1"
    static final String IMPORT_IES_VE = "importIES-VE"
    static final String IMPORT_LOOPFRONT = "importLoopfront"
    static final String IMPORT_REVIT = "importRevit"
    static final String IMPORT_RHINO_GRASSHOPPER = "importRhino/Grasshopper"
    static final String IMPORT_RHINO_3D = "importRhino3D"
    static final String IMPORT_SIMPLE_BIM = "importSimpleBim"
    static final String IMPORT_STRU_SOFT = "importStruSoft"
    static final String IMPORT_TEKLA_STRUCTURAL_DESIGNER = "importTekla Structural Designer"
    static final String IMPORT_TEKLA_STRUCTURES = "importTeklaStructures"
    static final String IMPORT_TRIMBLE_CONNECT = "importTrimble Connect"
    static final String IMPORT_FROM_FILE = "importFromFile"
    static final String IFC_FROM_SIMPLE_BIM = "IFCFromSimpleBIM"
    static final String INDICATOR_BENCHMARKS = "indicatorBenchmarks"
    static final String INDICATOR_BENCHMARKS_LOCKED = "indicatorBenchmarksLocked"
    static final String INTL_EPD_REQUIREMENTS = "intlEpdRequirements"
    static final String JSON_EXPORT = "jsonExport"
    static final String LCA_CHECKER = "lcaChecker"
    static final String LOAD_QUERY_FROM_DEFAULT = "loadQueryFromDefault"
    static final String MANAGE_PRIVATE_DATA = "managePrivateData"
    static final String MATERIALS_SPECIFIC_COST = "materialsSpecificCost"
    static final String MONTHLY_DATA = "monthlyData"
    static final String NORGE_EPD_REQUIREMENTS = "norgeEpdRequirements"
    static final String NMD_NL_EPD_REQUIREMENTS = "nmdNlEpdRequirements"
    static final String OLD_SCHOOL_COMPARE = "oldSchoolCompare"
    static final String OLD_SCHOOL_GRAPHS = "oldSchoolGraphs"
    static final String PLANETARY_REPORT = "planetaryReport"
    static final String PORTFOLIO_EXCEL_EXPORT = "portfolioExcelExport"
    static final String PREVENT_MULTIPLE_DESIGNS = "preventMultipleDesigns"
    static final String PREVENT_REPORT_GENERATION = "preventReportGeneration"
    static final String QUARTERLY_DATA = "quarterlyData"
    static final String QUICK_START_TEMPLATE = "customQuickStartTemplate"
    static final String RESULT_SIDEBAR = "resultSidebar"
    static final String RTS_EPD_REQUIREMENTS = "rtsEpdRequirements"
    static final String SANKEY_AND_TREEMAP = "sankeyAndTreemap"
    static final String SEND_ME_DATA = "sendMeData"
    static final String SEND_ME_EPD_REQUEST = "sendMeEpdRequest"
    static final String SEND_PRIVATE_DATA = "sendPrivateData"
    static final String SHOW_AND_NAVIGATE_VERIFICATION_POINTS = "showAndNavigateVerificationPoints"
    static final String SHOW_DATABASE_ID = "showDatabaseId"
    static final String SHOW_OTHER_ANSWERS = "showOtherAnswers"
    static final String SNBPE_MEMBER = "snbpeMember"
    static final String SPLIT_OR_CHANGE = "splitOrChange"
    static final String SUBMIT_PUBLIC_CONSTRUCTIONS = "submitPublicConstructions"
    static final String UL_EPD_REQUIREMENTS = "ulEpdRequirements"
    static final String UNLOCK_VERIFIED_DATASET = "unlockVerifiedDataset"
    static final String WORD_REPORT_GENERATION = "wordReportGeneration"
    static final String WORK_FLOW = "workFlowLicenseId"
    static final String GHG_SCOPE3_CAT6N7 = "ghgScope3Cat6n7"
    static final String GHG_SCOPE3CAT4N6N7N9 = "ghgScope3Cat4n6n7n9"
    static final String GHG_SCOPE3_CAT10N11N12 = "ghgScope3Cat10n11n12"
    static final String GHG_SCOPE3_CAT11N12 = "ghgScope3Cat11n12"
    static final String GHG_SCOPE3_CAT8N13 = "ghgScope3Cat8n13"
    static final String GHG_SCOPE3_CAT8N13N14 = "ghgScope3Cat8n13n14"
    static final String CARBONDESIGNER3D = "carbonDesigner3D"
    static final String CARBONDESIGNER3DSCENARIOS = "carbonDesigner3DScenarios"
    static final String CARBON_DESIGNER_RESULTS_REPORT = "carbonDesignerResultsReport"
    static final String GET_TESTABLE_ENTITIES = "getTestableEntities"
    static final String CARBONDESIGNER3DLITEMODE = "carbonDesigner3DLiteMode"
    static final String ALLOW_AVERAGE_DESIGN_CREATION = "allowAverageDesignCreation"

    static final List<String> ECOINVENT_WITH_CAP = [ECOINVENT_INTERNAL_TEST, ECOINVENT50, ECOINVENT100, ECOINVENT150, ECOINVENT200, ECOINVENT300, ECOINVENT400]
    static final List<String> DYNAMIC_FEATUREIDS = [EPD_LCA, EPD_TRACI, EPD_EN15804A1, EPD_EN15804A2, EPD_EN15804A2_OPTIONAL, EPD_EN15804_A2_INIES]

    String getFormattedName() {
        return "${featureClass ? featureClass + ": " : ""}${name}${userLinked ? " (user-linked)" : ""}"
    }
}
