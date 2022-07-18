/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.core

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
class Constants {
    static final String PORT = "8080"
    static final String CD3DPORT = "8081"
    static final List<String> PROD_DOMAINS = ["oneclicklcaapp.com", "www.360optimi.com"]
    static final String PROD_DOMAIN_NAME = "oneclicklcaapp.com"
    static final String TEST_DOMAIN_NAME = "test.1clicklca.com"
    static final String DEV_DOMAIN_NAME = "1clicklca.com"
    static final String HELP_CENTER_URL = "https://oneclicklca.zendesk.com/hc/en-us"

    static final String ROLE_SYSTEM_ADMIN = "ROLE_SYSTEM_ADMIN"
    static final String ROLE_SUPER_USER = "ROLE_SUPER_USER"
    static final String ROLE_AUTHENTICATED = "ROLE_AUTHENTICATED"
    static final String ROLE_TASK_AUTHENTICATED = "ROLE_TASK_AUTHENTICATED"
    static final String ROLE_SALES_VIEW = "ROLE_SALES_VIEW"
    static final String ROLE_DATA_MGR = "ROLE_DATA_MGR"
    static final String ROLE_CONSULTANT = "ROLE_CONSULTANT"
    static final String ROLE_DEVELOPER = "ROLE_DEVELOPER"
    static final String ADDITIONAL_QUESTIONS_QUERY_ID = "additionalQuestionsQuery"
    static final COLOR_CODES = ['#75AF00', '#FF8C00', '#1A86D0', '#FF2D41', '#764AB6', '#E1D700', '#00998F', '#E14F1F',
                                '#B2B2B2', '#006635', '#C71717', '#063DA6', '#83296B', '#686868', '#823E00', '#FE5C5C', '#FFF761',
                                '#91C46E', '#57BFFF', '#C081FF']
    @Deprecated
    //please use emailConfiguration instead
    static final String SUPPORT_EMAIL = "support@bionova.fi"
    @Deprecated
    static final String SALES_EMAIL = "sales@bionova.fi"
    @Deprecated
    static final String SALESLEAD_EMAIL = "sales-lead-notifications@bionova.fi"
    @Deprecated
    static final String HELLO_EMAIL = "hello@bionova.fi"
    static final String DEV_TEST_ENV = "dev_test"
    static final String PROD_WARN = "THIS IS THE PRODUCTION ENVIRONMENT"
    static final String COMPARE_INDICATORID = "materialSpecifierIndicator"
    static final String ORGANIZATION_CLASSIFICATION_ID = "organizationSpecificClassification"
    static final List<String> MONTHS = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"]
    static final List<String> NO_DIVISION_RULES = ["traciGWP_tn", "GWP_tn", "shadowPrice_NMD30"]

    static final Map<String, String> BENCHMARKS = ["co2_cml"         : "CO2 CML", "co2_traci": "CO2 TRACI", "leed_epd_cml": "LEED EPD CML",
                                                   "leed_epd_traci"  : "LEED EPD TRACI", "co2_inies_a1-c4": "CO2 INIES A1-C4",
                                                   "ecopoints_direct": "Ecopoints Direct", "shadowprice_direct": "Shadowprice Direct", "utilities": "Utilities"]
    static final Map<String, String> BENCHMARKSUNIT = ["co2_cml"         : "kg CO2e", "co2_traci": "kg CO2e", "leed_epd_cml": "kg CO2e",
                                                       "leed_epd_traci"  : "kg CO2e", "co2_inies_a1-c4": "kg CO2e",
                                                       "ecopoints_direct": "kg CO2e", "shadowprice_direct": "Dollar", "utilities": "kWh"]
    static final Map<String, List<String>> UPSTREAMDBCLASSIFIED = ["Ecoinvent"      : ["360optimi", "CRI", "CRI, Environmental Clarity", "ecoinvent", "Ecoinvent", "ecoinvent, DEAM", "ecoinvent, ELCD", "Ecoinvent, ITB", "Ecoinvent, Ostfoldforskning", "ELCD", "INIES", "ITB, Tauron", "US-EI", "USLCI", "USLCI, Ecoinvent", "WAT, Tauron, Ullmann", "ecoinvent, USLCI"],
                                                                   "Ecoinvent, Gabi": ["ecoinvent, GaBi", "Ecoinvent, GaBi", "Ecoinvent, GaBi, ELCD", "GaBi, ecoinvent", "ecoinvent, GaBi, USLCI"],
                                                                   "Gabi"           : ["GaBi", "GABI", "gabi", "Gabi", "World Steel Association"],
                                                                   "Other"          : ["-", "Not documented", "WBCSD-CSI", "Wind in power 2011 - European statistics", "Athena", "Boustead", "unlinked"]]

    static final List<String> REMOVE_FROM_STACKTRACE = ["jlrConstructorNewInstance", "call", "doCall", "invoke", "jlrMethodInvoke",
                                                        "handle", "doDispatch", "doService", "processRequest", "doPost", "doGet", "service",
                                                        "internalDoFilter", "doFilter", "doFilterInternal", "process", "doRun", "run"]
    static final List<String> METRIC_UNITS = ["m", "m2", "m3", "kg", "ton"]
    static final List<String> UNITS_FOR_CALCULATEMASS = ["kg", "ton", "kWh", "MWh", "kBtu"]
    static final List<String> IMPERIAL_UNITS = ["sq ft", "lbs", "cu ft", "cu yd"]
    static final List<String> THICKNESS_REQUIRED_UNITS = ["m2", "sq ft"]
    static final String UNIT_UNIT = "unit"
    static final String UNIT_LITRE = "l"
    static final String UNIT_KG = "kg"
    static final String UNIT_M = "m"
    static final String UNIT_M2 = "m2"
    static final String UNIT_TON = "ton"
    static final String UNIT_SQFT = "sq ft"
    static final List<String> ALL_UNITS = ["m", "m2", "m3", "kg", "ton", "sq ft", "lbs", "cu ft", "cu yd", UNIT_LITRE, UNIT_UNIT]
    static final List<String> PROJECT_CHARTS_VISUALIZATIONS = ['entityPageChart', 'entityByStageChart', 'entityElementChart', 'entityCompareElementsChart', 'entityByStageElementChart']
    static final Map<String, String> SUBANDSUPERSCRIPTRULE = ["CO2": "CO<sub>2</sub>", "m2": "m<sup>2</sup>", "m3": "m<sup>3</sup>", "SO2": "SO<sub>2</sub>", "PO4": "PO<sub>4</sub>", "ft3": "ft<sup>3</sup>", "ft2": "ft<sup>2</sup>"]
    static final List<String> CHART_COLORS = ["rgb(127,196,204)", "rgb(255,164,5)", "rgb(0,128,0)", "rgb(153,63,0)", "rgb(245, 235, 113)", "rgb(158, 148, 82)", "rgb(103, 194, 119)", "rgb(171, 99, 149)", "rgb(128,128,128)", "rgb(148,255,181)", "rgb(41, 82, 63)", "rgb(217, 163, 227)", "rgb(149, 176, 58)", "rgb(32, 70, 128)", "rgb(255,204,153)", "rgb(255,168,187)", "rgb(46, 64, 15)", "rgb(199, 105, 111)", "rgb(123, 91, 166)", "rgb(255,255,128)", "rgb(204, 113, 73)"]

    static final List<String> CHART_COLORS_OPAC = ['rgba(127,196,204,0.7)', 'rgba(247,119,0,0.7)', 'rgba(0,128,0,0.7)', 'rgba(119, 213, 109, 0.7)', 'rgba(115,49,70,0.7)', 'rgba(243,153,131,0.7)', 'rgba(144, 127, 204, 0.7)', 'rgba(58, 82, 156, 0.7)', 'rgba(255, 204, 0, 0.7)', 'rgba(67,67,72,0.7)', 'rgba(128, 122,0,0.7)', 'rgba(219,28,17,0.7)']
    static final List<String> RULES_IGNORE_INGRAPH = ["mass", "mass_tn", "mass_m2"]
    static final String RESOURCE_LOCAL_AREA = "LOCAL"
    static final long DB_MAX_DOCUMENT_SIZE = 16793600
    static final String RESOURCE_DUMP_FILENAME = "resource_dump"
    static final String INDICATOR_BENCHMARK_DUMP_FILENAME = "indicator_benchmarks_dump"
    static final String SPECIAL_RULES_FILENAME = "specialRules"
    static final String INDICATOR_BENCHMARK = "indicator_benchmarks"
    static final List<String> BENCHMARK_VISIBILITY_STATUS = ["benchmark", "visibleBenchmark"]
    //REGEX: p{Sc}: all unicode currency symbol,
    // p{So}: various symbols that is not math symbols, currency sigh or combining chars
    // p{Mn}: a character intended to be combined with another character without taking up extra space (e.g. accents, umlauts, etc.)
    // p{Z}: any kind of whitespace or invisible separator.
    // À-ÿa-zA-Z0-9@: letters and number characters plus @ symbol
    // -()_!? /: singular allowed chars that now belong to group above

    static final String USER_INPUT_REGEX = "[^\\p{Sc}\\p{So}\\p{Mn}\\p{Z}À-ÿa-zA-Z0-9@\\-()_!?\\/ ]+"
    static final String USER_INPUT_REGEX_2 = "[^\\p{Sc}\\p{So}\\p{Mn}\\p{Z}À-ÿa-zA-Z0-9@\\-()_!?:;,.’”\\/ ]+"
    static final String TRIGGER_CALCULATION_PARAMETER_KEY = "triggerCalculation"
    static final String REDIRECT_WITHOUT_CALCULATION_AND_SAVE = "changeQueryWithoutAnyActions"

    static final String TEMPLATE_LIST_BODY = "templateListBody"
    static final String EDIT_TEMPLATE_OPTION_CONTAINER_ID = "editTemplateModalContainer"
    static final String PROJECT_TEMPLATE_TO_LICENSE_TABLE_ID = "projectTemplateToLicenseSection"
    static final String QSPM_OPTIONS_TAB_ID = "quickStartProjectModal-optionsTab"
    static final String QSPM_DESIGN_TAB_ID = "quickStartProjectModal-designTab"
    static final String OPEN_INDICATOR_SELECT_POSTFIX = "-openIndicator-select"
    static final String TOOL_LIST_POSTFIX = "-toolList-modal"
    static final String DEFAULT_BLD_TYPE_POSTFIX = "-defaultBuildingType-modal"
    static final String ENTITY_TEMPLATE_SELECT_POSTFIX = "-selectEntityTemplate"
    static final String APPLY_LCA_DEFAULTS_POSTFIX = "-applyLcaDefaults"
    static final String TEMPLATE_EXTENDED_OPTIONS_POSTFIX = "-options-hideConditionally"
    static final String PRODUCT_TYPE_POSTFIX = "-productType"
    static final String PARAM_EXPAND_ALL_SECTIONS_IN_QUERY = "expandAllInputs"
    static final String BLD_TYPE_SELECT = "basicQuerySectiontypeResourcesSelect"
    static final String PRODUCT_TYPE_SELECT = "basicQuerySection\\\\.productType"
    static final String COUNTRY_SELECT = "basicQuerySectioncountryResourcesSelect"
    static final String PROJECT_NAME_QUICKSTART = "quickstartProjectModal-projectName"
    static final String TEMPLATE_SELECT_QUICKSTART = "quickStartProjectModal-templateId"
    static final String NO = "no"

    // Allowed features
    static final String ADD_TO_COMPARE = "addToCompare"

    // environmentDataSourceType
    static final String MANUFACTURER_EDST = "manufacturer"
    static final String GENERIC_EDST = "generic"

    // construction type
    static final String STRUCTURE_CT = "structure"

    // Areas / countries

    static final String EUROPE = "Europe"
    static final String NETHERLANDS = "netherlands"

    // impact basis
    static final String UNIT_IMPACT_BASIS = "unit"

    //betie
    static final String BETIE_FILTER = "FILTER"
    static final String BETIE_DATA = "DATA"
    static final String BETIE_VAR_DATA = "VARIABLE_DATA"
    static final String BETIE_QUESTIDS_QTY = "QIdsWithQty"
    static final String BETIE_RESOURCEIDS = "resIds"
    static final String BETIE_RECIPENAME = "name"
    static final int BETIE_EXCEL_MULTIPLIER_ID_COLUMN = 2 // starting with 0, index of "multiplierId" column
    // starting with 0, index of the first column in loading recipe feature Excel containint "formulation" data
    static final int BETIE_EXCEL_FIRST_FORMULATION_COLUMN = 3
    // JSON property name for quantity values to be transferred to front-end
    static final String BETIE_JSON_QUANTITY_VALUES_OBJECT_QUANTITY_PROPERTY_NAME = "quantity"
    // JSON property name for multiplierID to be transferred to front-end
    static final String BETIE_JSON_QUANTITY_VALUES_OBJECT_MULTIPLIER_ID_PROPERTY_NAME = "multiplierId"

    // Classification params questionIds
    static final String CONSTRUCTION_CLASSIFICATION_PARAMETERS = "constructionClassificationParameters"
    static final String CLASSIFICATION_ID_3B = "3B"

    //applicationIds
    static final String LCA_APPID = "LCA"

    // questionIds
    static final String SFB_CODE_QUESTIONID = "SfB_NL_3"
    static final String CARBON_IMPACT_ADDITIONAL_QUESTIONID = "carbonDataImpact"
    static final String TRANSPORT_RESOURCE_QUESTIONID = "transportResourceId"
    static final String TRANSPORT_RESOURCE_LEG2_QUESTIONID = "transportResourceIdleg2"
    static final String EOL_QUESTIONID = "eolProcessId"
    static final String LOCAL_COMP_QUESTIONID = "applyLocalComps"
    static final String LOCAL_COMP_TARGET_QUESTIONID = "targetCountry"
    static final String LOCAL_COMP_ENERGY_PROFILE_QUESTIONID = "compensationEnergyProfile"
    static final String LOCAL_COMP_METHOD_VERSION_QUESTIONID = "localCompensationMethodVersion"
    static final String ELEMENT_CATEGORY_QUESTIONID = "elementCategory"
    static final String MASS_PER_UNIT_KG_QUESTIONID = "massPerUnit_kg"
    static final String MASS_PER_UNIT_QUESTIONID = "massPerUnit"
    static final String PUBLISH_FOR_EXPORT_QUESTIONID = "publishForExport"
    static final String QUANTITY_QUESTIONID = "quantity"
    static final String PRODUCT_TYPE_QUESTIONID = "productType"
    static final String DIMENSION1_NMD = "dimension1_NMD"
    static final String DIMENSION2_NMD = "dimension2_NMD"
    static final String ADDITIONAL_FACTOR_NMD30_QUESTIONID = "additionalFactor_NMD30"
    static final String CONSTRUCTION_COMPONENTS3_QUESTIONID_NMD = "constructionComponents3"
    static final String NMD_CATEGORY_ID_QUESTIONID = "nmdCategoryId"
    static final String COMMENT_QUESTIONID = "comment"
    static final String IGNORE_REUSED_MATERIAL_QUESTIONID = "ignoreReusedMaterial"

    // sectionIds
    static final String DECLARED_UNIT_SECTIONID = "declaredUnit"
    static final String CALC_DEFAULTS_SECTIONID = "calculationDefaults"
    static final String CONSTRUCTION_COMPONENTS_SECTIONID = "constructionComponents"

    //queryIds
    static final String LCA_PARAMETERS_QUERYID = "LCAParametersQuery"
    static final String EPD_DECLARED_UNIT_QUERYID = "epdDeclaredUnit_preVerified"
    static final String COMPARE_QUERYID = "materialSpecifier"
    static final String CARBON_DESIGNER_QUERY = "carbonDesignerQuery"
    static final String BUILDING_MATERIALS_QUERYID = "buildingMaterialsQuery"

    // calculationRuleIds
    static final String GWP_CALC_RULEID = "GWP"


    //Keys for Cacheable
    static final String NMD_APPLICATION = "nmdApplication"
    static final String GET_NMD_FASE_ID_TO_OCLSTAGES = "getNmdFaseIdToOCLStages"
    static final String GET_NMD_TOEPASSINGS_ID_TO_APPLICATION = "getNmdToepassingsIdToApplication"
    static final String GET_NMD_MILLIEU_CATEGORY_ID_TO_OCLIMPACT_NAME = "getNmdMillieuCategoryIdToOCLImpactName"
    static final String GET_NMD_ELEMENT_TYPES = "getNmdElementTypes"
    static final String GET_NMD_UNIT_TO_OCL = "getNmdUnitToOCL"
    static final String GET_UPDATE_STRING_ON_IMPORT_NMD = "getUpdateStringOnImportNmd"
    static final String GET_KNOWN_NMD_UNITS = "getKnownNmdUnits"
    static final String GET_KNOWN_FASE_IDS = "getKnownFaseIds"
    static final String GET_KNOWN_TOEPASSING_IDS = "getKnownToepassingIds"
    static final String GET_KNOWN_MILIEU_CATEGORIE_IDS = "getKnownMilieuCategorieIds"
    static final String PERSISTENT_PROPERTIES_DOMAIN = "persistentPropertiesDomain"

    //circular graph's rules
    static final String RENEWABLE_IN_WEIGHTED = "renewabled_weighted"
    static final String RECYCLE_IN_WEIGHTED = "recycled_weighted"
    static final String REUSE_IN_WEIGHTED = "reused_weighted"
    static final String REUSE_OUT_WEIGHTED = "EOL-reuseMaterial_weighted"
    static final String RECYCLE_OUT_WEIGHTED = "EOL-recycling_weighted"
    static final String RECOVERY_OUT_WEIGHTED = "EOL-reuseEnergy_weighted"
    static final String DISPOSAL_OUT_WEIGHTED = "EOL-disposal_weighted"
    static final String NON_RENEWABLE_WEIGHTED = "virginMaterialsUsed_weighted"
    static final String DOWN_CYCLING_WEIGHTED = "EOL-downcycled_weighted"

    static final String RENEWABLE_IN = "renewabled"
    static final String RECYCLE_IN = "recycled"
    static final String REUSE_IN = "reused"
    static final String REUSE_OUT = "EOL-reuseMaterial"
    static final String RECYCLE_OUT = "EOL-recycling"
    static final String RECOVERY_OUT = "EOL-reuseEnergy"
    static final String DISPOSAL_OUT = "EOL-disposal"
    static final String NON_RENEWABLE = "virginMaterialsUsed"
    static final String DOWN_CYCLING = "EOL-downcycled"
    static final String MATERIAL_RECOVERED = "materialRecovered"
    static final String MATERIAL_RETURNED = "materialReturned"
    static final List<String> CIRCULAR_GRAPH_RULE_LISTS = [RENEWABLE_IN, RECYCLE_IN, REUSE_IN, REUSE_OUT, RECYCLE_OUT, RECOVERY_OUT, DISPOSAL_OUT, NON_RENEWABLE, DOWN_CYCLING]
    static final Map<String, List<String>> CIRCULAR_GRAPH_RULE_WEIGHTED_LISTS = [(MATERIAL_RECOVERED): [RENEWABLE_IN_WEIGHTED, RECYCLE_IN_WEIGHTED, REUSE_IN_WEIGHTED, NON_RENEWABLE_WEIGHTED],
                                                                                 (MATERIAL_RETURNED) : [REUSE_OUT_WEIGHTED, RECYCLE_OUT_WEIGHTED, RECOVERY_OUT_WEIGHTED, DISPOSAL_OUT_WEIGHTED, DOWN_CYCLING_WEIGHTED]]

    static final String LOCALIZED_LINK_IDENTIFIER = "TRANSLATIONID="
    static final String LOCALIZED_LINK_REGEX = "TRANSLATIONID=[a-zA-Z0-9_-]+"
    static final String LOCALIZED_LINK_DELIMITER = "=" // Symbol where to cut the identifier and the actual Id

    static final String PROJECT_LEVEL = "projectLevel"

    static final Map<String, String> ALLOWED_RESOURCE_VERIFICATION_STATUS = ['verified': 'Third-party verified (as per ISO 14025)', 'noverification': 'Self declared', 'machineonly': 'Machine verified', 'internalonly': 'Internally verified', 'otherverification': 'Verification (other than ISO 14025)']
    static final List<String> EOL_STAGES = ['C2', 'C3', 'C4', 'D', 'C1-C4', 'C3-C4']
    static final List<String> EPD_VISUAL_IMAGE_QUESTIONIDS = ['brand', 'productImage', 'manufacturingDiagram']
    static final int MAX_COLUMNS = 40

    //GLA discounting
    static final String IMPACTTIMING_FINAL = "final"
    static final String IMPACTTIMING_INITIAL = "initial"
    static final String IMPACTTIMING_EXTERNAL = "external"
    static final String IMPACTTIMING_RECURRING = "recurring"
    static final String IMPACTTIMING_ANNUAL = "annual"

    static final String USEFIXEDCALCULATIONBASIS = "useFixedCalculationBasis"
    static final String SET_MINIMUM_VALUE = "setMinimumValue"
    static final String SET_MAXIMUM_VALUE = "setMaximumValue"
    static final String SET_TO_ZERO_IF_VALUE_IS_NEGATIVE = "setToZeroIfValueIsNegative"
    static final String SET_TO_ZERO_IF_VALUE_IS_POSITIVE = "setToZeroIfValueIsPositive"
    static final String MIN = "min"
    static final String MAX = "max"

    static final String UNDEFINED = "undefined"

    // word report
    static final int FONT_SIZE_WORD_TABLES = 7
    static final String FONT_FAMILY_WORD_TABLES = "Univers Light"
    static final String DEFAULT_EPD_SCOPE = "A13CDScope"
    final static String SCOPE_INCLUDED = "x"
    final static String SCOPE_EXCLUDED = "MND"
    final static String FONT_COLOR_GREEN_HEX = "007d58"
    final static String FONT_COLOR_BLUE_HEX = "0040c7"
    final static String EPD_HUB_REPORT_RULEID = "epdHubToolReport"

    //apply multipliers
    static final String CALCULATE_FRACTIONAL_REPLACEMENTS_ONLY = "calculateFractionalReplacementsOnly"
    static final String APPLY_FRACTIONAL_REPLACEMENT_MULTIPLIER = "applyFractionalReplacementMultiplier"

    //CarbonDesigner3D Constants
    static final String CO2E = "co2e"
    static final String CO2_INTENSITY = "co2Intensity"
    static final String CARBON_SHARE = "carbonShare"
    static final String SHARE = "share"

    //Excel constants
    static final Integer MAX_EXCEL_CELL_SIZE = 32765
    static final Integer INDICATOR_REPORT_HEADING_ROW_NUM = 2

    //average designs for EPD
    static final String ENTITY_ID = "entityId"
    static final String INDICATOR_ID = "indicatorId"
    static final String PRODUCT_ALLOCATION = "coProductAllocation"
    static final String EPDMATERIALS_PREVERIFIED = "epdMaterials_preVerified"
    static final String EPDMANUFACTURING_PREVERIFIED = "epdManufacturing_preVerified"
    static final Integer RIBA_COMPONENTONLY = -1
    static final String VISIBILITY_STATUS_XBENCHMARK = "benchmark"
    static final String X_BENCHMARK_EMBODIED_INDICATORID = "xBenchmarkEmbodied"
    static final Integer MINIMUM_NO_OF_DESIGNS_FOR_AVERAGING = 2

    // FEC tool: this map should not be used to setRef for eges indicateur
    static final Map<String, Integer> FEC_CALC_RULEID_INDICATEUR_REF = [
            'GWP'                           : 1,
            'ODP'                           : 2,
            'AP'                            : 3,
            'EP'                            : 4,
            'POCP'                          : 5,
            'ADP-mineral'                   : 6,
            'ADP-fossil'                    : 7,
            'airPollution_m3'               : 8,
            'waterPollution_m3'             : 9,
            'renewablesUsedAsEnergy_MJ'     : 10,
            'renewablesUsedAsMaterial_MJ'   : 11,
            'renewablesUsedTotal_MJ'        : 12,
            'nonRenewablesUsedAsEnergy_MJ'  : 13,
            'nonRenewablesUsedAsMaterial_MJ': 14,
            'nonRenewablesUsedTotal_MJ'     : 15,
            'energyResourcesUsedTotal_MJ'   : 16,
            'recyclingMaterialUse_kg'       : 17,
            'renewableRecylingFuelUse_MJ'   : 18,
            'nonRenewableRecylingFuelUse_MJ': 19,
            'cleanWaterNetUse_m3'           : 20,
            'wasteHazardous_kg'             : 21,
            'wasteNonHazardous_kg'          : 22,
            'reusableMaterialsOutput_kg'    : 23,
            'recyclableMaterialsOutput_kg'  : 24,
            'energyMaterialsOutput_kg'      : 25,
            'exportedEnergyOutput_MJ'       : 26
    ]

    static final String FEC_ZONE_USAGE_ADDQ_ID = "usageFEC"
    static final Map<String, String> FEC_ZONE_USAGE_MAP = [
            '0' : 'otherBuildingsFEC',
            '1' : 'singleFamilyHouseFEC',
            '2' : 'apartmentBuildingFEC',
            '3' : 'dayCareCenterFEC',
            '4' : 'primarySchoolFEC',
            '5' : 'secondarySchoolDayFEC',
            '6' : 'secondarySchoolNightFEC',
            '7' : 'researchBuildingsFEC',
            '8' : 'campusBuildingFEC',
            '10': 'hotel0NightFEC',
            '11': 'hotel2NightFEC',
            '12': 'hotel3NightFEC',
            '13': 'hotel4NightFEC',
            '14': 'hotel0DayFEC',
            '15': 'hotel3DayFEC',
            '16': 'officeBuildingFEC',
            '17': 'commercialCateringFEC',
            '18': 'restaurant1FEC',
            '19': 'restaurant2FEC',
            '20': 'restaurant3FEC',
            '22': 'commercialBuildingsFEC',
            '24': 'schoolSportsHallFEC',
            '26': 'retirementHomeFEC',
            '27': 'healthcareCenterNightFEC',
            '28': 'healthcareCenterDayFEC',
            '29': 'terminalBuildingsFEC',
            '32': 'industrialBuildings3x8FEC',
            '33': 'industrialBuildings8h18hFEC',
            '34': 'courtFEC',
            '36': 'SportsHallFEC',
            '37': 'schoolCatering1FEC',
            '38': 'schoolCatering3FEC'
    ]

    static final List<String> RSET_BATIMENT = ["sortieBatimentC", "batiment"]
    static final List<String> RSET_ZONE = ["sortieZoneC", "zone"]


    static final List<String> ALLOWED_BUILDING_ZONES = ['1', '2', '3', '4', '5', '6', '7', '8']

    static final String INIES = "INIES"

    static final enum RsetMapping {
        BATIMENT_INDEX_MAPPINGS("batimentIndexMappings"),
        BATIMENT_ZONE_INDEX_MAPPINGS("batimentZoneIndexMappings")


        private String attribute

        private RsetMapping(String attribute) {
            this.attribute = attribute
        }

        public String getAttribute() {
            return attribute
        }
    }

    static final enum FilteringMethod {
        ALL("all"),
        ANY("any"),
        NONE("none"),
        EXISTS("exists"),
        EXPIRED("expired"),
        GREATER_THAN("greaterThan"),
        LESS_THAN("lessThan"),
        PRESENT("present")

        private String method

        private FilteringMethod(String method) {
            this.method = method
        }

        public String getMethod() {
            return method
        }
    }
    static final String PRESENT_IN_DATASET = "presentInDataset"


    static final String EMPTY_STRING = ""

    static final Map<String, String> countryIdToActiveCampaingId = [
            "afghanistan"       : "Afghanistan",
            "albania"           : "Albania",
            "algeria"           : "Algeria",
            "angola"            : "Angola",
            "argentina"         : "Argentina",
            "armenia"           : "Armenia",
            "australia"         : "Australia",
            "austria"           : "Austria",
            "azerbaijan"        : "Azerbaijan",
            "bahrain"           : "Bahrain",
            "bangladesh"        : "Bangladesh",
            "belarus"           : "Belarus",
            "belgium"           : "Belgium",
            "benin"             : "Benin",
            "bolivia"           : "Bolivia",
            "bosniaHerzegovina" : "Bosnia and Herzegovina",
            "botswana"          : "Botswana",
            "brazil"            : "Brazil",
            "brunei"            : "Brunei Darussalam",
            "bulgaria"          : "Bulgaria",
            "cambodia"          : "Cambodia",
            "cameroon"          : "Cameroon",
            "canada"            : "Canada",
            "chile"             : "Chile",
            "china"             : "China",
            "colombia"          : "Colombia",
            "congo"             : "Congo",
            "congoRepublic"     : "Congo, the Democratic Republic of the",
            "costarica"         : "Costa Rica",
            "cotedIvoire"       : "Cote D'Ivoire",
            "croatia"           : "Croatia",
            "cuba"              : "Cuba",
            "cyprus"            : "Cyprus",
            "czechRepublic"     : "Czech Republic",
            "denmark"           : "Denmark",
            "dominicanRepublic" : "Dominican Republic",
            "ecuador"           : "Ecuador",
            "egypt"             : "Egypt",
            "elSalvador"        : "El Salvador",
            "eritrea"           : "Eritrea",
            "estonia"           : "Estonia",
            "ethiopia"          : "Ethiopia",
            "finland"           : "Finland",
            "france"            : "France",
            "gabon"             : "Gabon",
            "georgia"           : "Georgia",
            "germany"           : "Germany",
            "ghana"             : "Ghana",
            "gibraltar"         : "Gibraltar",
            "greece"            : "Greece",
            "guatemala"         : "Guatemala",
            "haiti"             : "Haiti",
            "honduras"          : "Honduras",
            "hongKong"          : "Hong Kong",
            "hungary"           : "Hungary",
            "iceland"           : "Iceland",
            "india"             : "India",
            "indonesia"         : "Indonesia",
            "iran"              : "Iran, Islamic Republic of",
            "iraq"              : "Iraq",
            "ireland"           : "Ireland",
            "israel"            : "Israel",
            "italy"             : "Italy",
            "jamaica"           : "Jamaica",
            "japan"             : "Japan",
            "jordan"            : "Jordan",
            "kazakhstan"        : "Kazakhstan",
            "kenya"             : "Kenya",
            "kuwait"            : "Kuwait",
            "kyrgyzstan"        : "Kyrgyzstan",
            "latvia"            : "Latvia",
            "lebanon"           : "Lebanon",
            "lithuania"         : "Lithuania",
            "luxembourg"        : "Luxembourg",
            "lybia"             : "Libyan Arab Jamahiriya",
            "macedonia"         : "Macedonia, the Former Yugoslav Republic of",
            "madagascar"        : "Madagascar",
            "malaysia"          : "Malaysia",
            "malta"             : "Malta",
            "mauritius"         : "Mauritius",
            "mexico"            : "Mexico",
            "moldova"           : "Moldova, Republic of",
            "mongolia"          : "Mongolia",
            "morocco"           : "Morocco",
            "mozambique"        : "Mozambique",
            "myanmar"           : "Myanmar",
            "namibia"           : "Namibia",
            "nepal"             : "Nepal",
            "netherlands"       : "Netherlands",
            "newZealand"        : "New Zealand",
            "nicaragua"         : "Nicaragua",
            "niger"             : "Niger",
            "nigeria"           : "Nigeria",
            "northKorea"        : "Korea, Democratic People's Republic of",
            "norway"            : "Norway",
            "oman"              : "Oman",
            "pakistan"          : "Pakistan",
            "panama"            : "Panama",
            "papuaNewGuinea"    : "Papua New Guinea",
            "paraguay"          : "Paraguay",
            "peru"              : "Peru",
            "philipines"        : "Philippines",
            "poland"            : "Poland",
            "portugal"          : "Portugal",
            "qatar"             : "Qatar",
            "romania"           : "Romania",
            "russia"            : "Russian Federation",
            "saudiArabia"       : "Saudi Arabia",
            "senegal"           : "Senegal",
            "serbia"            : "Serbia and Montenegro",
            "singapore"         : "Singapore",
            "slovakRepublic"    : "Slovakia",
            "slovenia"          : "Slovenia",
            "southAfrica"       : "South Africa",
            "southKorea"        : "Korea, Republic of",
            "spain"             : "Spain",
            "sriLanka"          : "Sri Lanka",
            "sudan"             : "Sudan",
            "sweden"            : "Sweden",
            "switzerland"       : "Switzerland",
            "syria"             : "Syrian Arab Republic",
            "taiwan"            : "Taiwan, Province of China",
            "tajikistan"        : "Tajikistan",
            "tanzania"          : "Tanzania, United Republic of",
            "thailand"          : "Thailand",
            "togo"              : "Togo",
            "trinidadTobago"    : "Trinidad and Tobago",
            "tunisia"           : "Tunisia",
            "turkey"            : "Turkey",
            "turkmenistan"      : "Turkmenistan",
            "ukraine"           : "Ukraine",
            "unitedArabEmirates": "United Arab Emirates",
            "unitedKingdom"     : "United Kingdom",
            "uruguay"           : "Uruguay",
            "USA"               : "United States",
            "uzbekistan"        : "Uzbekistan",
            "venezuela"         : "Venezuela",
            "vietnam"           : "Viet Nam",
            "yemen"             : "Yemen",
            "zambia"            : "Zambia",
            "zimbabwe"          : "Zimbabwe"
    ]

    static final Map<String, String> DATE_FORMAT = [
            'fi'   : 'dd.MM.yyyy',
            'en-US': 'yyyy.MM.dd',
            'en-GB': 'dd/mm/yyyy'
    ]

    /*
    static final enum UnitSystem {
        METRIC ("Metric", "metric"), IMPERIAL ("Imperial", "imperial"), METRIC_AND_IMPERIAL("Metric and imperial", "both")

        String name
        String value

        private UnitSystem(String name, String value) {
            this.name = name
            this.value = value
        }
    } */
    static final List<String> THICKNESS_MM_ADDQ_ID = ["thickness_mm1", "thickness_mm2"]

    //NMD
    public final static String NMD_UNIT_FALLBACK = 'unit'
    static final Double INVALID_ELPR_Factor = 99999
    static final String UNKNOWN_UNIT = 'unknown'
    static final String NMD_RESOURCEID_PREFIX = "nmd30_"
    static final String DEEL_PRODUCT = "Deelproduct"
    static final String TOTAAL_PRODUCT = "Totaalproduct"
    static final String NMD_3_API = "NMD 3 API"
    static final String NMD_BASIC = "NMD-basic"
    static final String TRANSPORTATION_DGBC = "transportationDGBC"
    static final List<Integer> TRANSPORTATION_DGBC_UNIT_GROUP = [19, 20]
    static final String NMD = "NMD"
    static final String NMD_3_DB_VERSION = "3.0"
    static final String UNNAMED = "Unnamed"
    static final Double CAT3_ADDITIONAL_FACTOR_NMD30 = 1.3
    static final Double DEFAULT_ADDITIONAL_FACTOR_NMD30 = 1
    static final List<String> NMD_SCALING_ADDQ_ID = [DIMENSION1_NMD, DIMENSION2_NMD]
    //SW-1525
    static final String NMD_REUSED_MATERIAL_QUESTIONID = "onvz_HGB_reuse_NMD"
    static final String NMD_REUSE_FACTOR_NMD30 = "nmdReuseFactor"

    static final List<String> INVALID_ATTACHMENT_EXTENSIONS = ['com', 'exe', 'rb', 'js', 'php', 'zip']
    static final int MAX_ATTACHMENT_SIZE = 1024 * 1024 // 1 Mb

    static final String DUMMY_RESOURCE_ID = "dummyGroupResource"

    static final List<String> FORBIDDEN_METHODS = ["getDB", "getAll", "getProperties", "getMethods", "getDeclaredMethods", "getFields", "getDeclaredFields"]

    static final enum NmdCategory {
        ONE("1"),
        TWO("2"),
        THREE("3")

        private String category

        private NmdCategory(String cat) {
            this.category = cat
        }

        String getCategory() {
            return category
        }

        static List<String> preventExpandCategories() {
            return [ONE.category, TWO.category]
        }
    }

    static final enum RequestSessionAttribute {
        CALCULATION_ERROR_KEY("calculation.error.key")

        private String attribute

        private RequestSessionAttribute(String attribute) {
            this.attribute = attribute
        }

        public String getAttribute() {
            return attribute
        }
    }

    static final enum ForwardOrigin {
        NEWDESIGN("design.newDesign"),
        STARTNEWPROJECT("projectTemplate.startNewProject")

        private String origin

        private ForwardOrigin(String origin) {
            this.origin = origin
        }

        public String getOrigin() {
            return origin
        }
    }

    static final enum EOLProcess {
        DO_NOTHING("donothing"),
        REUSE_AS_MATERIAL("reuse"),
        IMPACTS_FROM_EPD("epd")

        private String eolProcess

        private EOLProcess(String eolProcess) {
            this.eolProcess = eolProcess
        }

        public String toString() {
            return eolProcess
        }
    }

    static final enum DesignRIBAStages {
        STRATEGIC(0),
        PREPARATION_AND_BRIEF(1),
        CONCEPT(2),
        DEVELOPED(3),
        TECHNICAL(4),
        CONSTRUCTION(5),
        HANDOVER_AND_CLOSE_OUT(6),
        IN_USE(7)

        private Integer RIBAStage

        private DesignRIBAStages(Integer RIBAStage) {
            this.RIBAStage = RIBAStage
        }

        public Integer toInteger() {
            return RIBAStage
        }

        public static Map<Integer, String> map() {
            return [(STRATEGIC.toInteger())             : "riba_strategic",
                    (PREPARATION_AND_BRIEF.toInteger()) : "riba_prep_prief",
                    (CONCEPT.toInteger())               : "riba_concept",
                    (DEVELOPED.toInteger())             : "riba_developed",
                    (TECHNICAL.toInteger())             : "riba_technical",
                    (CONSTRUCTION.toInteger())          : "riba_construction",
                    (HANDOVER_AND_CLOSE_OUT.toInteger()): "riba_handover",
                    (IN_USE.toInteger())                : "riba_inuse"]
        }

    }

    static final enum FloatingLicense {
        NO("no"),
        SINGLE_COUNTRY("singleCountry"),
        SINGLE_SITE("singleSite"),
        INTERNATIONAL("international")

        String floatingLicense

        private FloatingLicense(String floatingLicense) {
            this.floatingLicense = floatingLicense
        }

        public String toString() {
            return floatingLicense
        }

        public static Map<String, String> map() {
            return [(NO.toString())            : "No",
                    (SINGLE_COUNTRY.toString()): "Floating single country",
                    (SINGLE_SITE.toString())   : "Floating single site",
                    (INTERNATIONAL.toString()) : "Floating international"]
        }
    }

    static final enum UserType {
        STUDENT("Student"),
        BUSINESS("Business"),
        ACADEMIA("Academia")

        private String userType

        private UserType(String userType) {
            this.userType = userType
        }

        public String toString() {
            return userType
        }

        public static Map<String, String> map() {
            return [(STUDENT.toString()) : "student",
                    (BUSINESS.toString()): "business",
                    (ACADEMIA.toString()): "academia"]
        }
    }


    static final enum TransportDistanceQuestionId {
        TRANSPORTDISTANCE_KM("transportDistance_km"),
        TRANSPORTDISTANCE_MI("transportDistance_mi"),
        TRANSPORTDISTANCETRACI_KM("transportDistanceTRACI_km"),
        TRANSPORTTRETEKNISK_KM("transportTreteknisk_km"),
        TRANSPORTDISTANCE_KMAUS("transportDistance_kmAus"),
        TRANSPORTDISTANCE_KMECO("transportDistance_kmECO"),
        TRANSPORTDISTANCE_KMECO36("transportDistance_kmECO36"),
        TRANSPORTDISTANCE_KMECO36LEG2("transportDistance_kmECO36leg2"),
        TRANSPORT_KMECO36_RESOURCEID("transportResourceIdECO36"),
        TRANSPORT_KMECO36LEG2_RESOURCEID("transportResourceIdECO36leg2"),
        TRANSPORTDISTANCE_KMLEG2("transportDistance_kmleg2"),
        TRANSPORTDISTANCE_YM_KM("transportDistanceYM_km")


        String TransportQuestionId

        private TransportDistanceQuestionId(String transportQuestionId) {
            this.transportQuestionId = transportQuestionId
        }

        String toString() {
            return transportQuestionId
        }

        static List<String> list() {
            return [TRANSPORTDISTANCE_KM.toString(), TRANSPORTDISTANCE_MI.toString(),
                    TRANSPORTDISTANCETRACI_KM.toString(), TRANSPORTTRETEKNISK_KM.toString(), TRANSPORTDISTANCE_KMAUS.toString(),
                    TRANSPORTDISTANCE_KMECO.toString(), TRANSPORTDISTANCE_KMECO36.toString(), TRANSPORTDISTANCE_KMECO36LEG2.toString(),
                    TRANSPORTDISTANCE_KMLEG2.toString(), TRANSPORTDISTANCE_YM_KM.toString()]
        }
    }

    static final enum DesignStatus {
        NOT_DEFINED("inProgress"),
        ALLOW_IN_PORTFOLIO("allowAsBenchmark"),
        DO_NOT_ALLOW_IN_PORTFOLIO("rejected")

        private String status

        private DesignStatus(String status) {
            this.status = status
        }

        public String toString() {
            return status
        }

        public static Map<String, String> map() {
            return [(NOT_DEFINED.toString())              : "entity.status.inProgress",
                    (ALLOW_IN_PORTFOLIO.toString())       : "entity.status.allowAsBenchmark",
                    (DO_NOT_ALLOW_IN_PORTFOLIO.toString()): "entity.status.rejected"]
        }

    }

    static final enum CalculationStatus {
        OK(1), RESULTMANIPULATOR_NOK(2), NO_DATA(3), NOT_HANDLED(4), RESOURCEIMPACTS_NOK(5), CALCULATIONRULE_NOK(6)

        private Integer status

        private CalculationStatus(Integer status) {
            this.status = status
        }

        public Integer getStatus() {
            return status
        }

        public boolean equals(Integer status) {
            boolean equals = false

            if (status && status == this.status) {
                equals = true
            }
            return equals
        }
    }

    static final enum LicenseType {
        PRODUCTION("Production"),
        DEMO("Demo"),
        TRIAL("Trial"),
        EDUCATION("Education")

        private final String type

        private LicenseType(String type) {
            this.type = type
        }

        public String toString() {
            return type
        }

        public static List<String> list() {
            return [PRODUCTION.toString(), DEMO.toString(), TRIAL.toString(), EDUCATION.toString()]
        }
    }

    static final enum PortfolioType {
        DESIGN("design"),
        OPERATING("operating")

        private final String type

        private PortfolioType(String type) {
            this.type = type
        }

        public String toString() {
            return type
        }
    }

    static final enum LicenseFeatureClass {
        BUSINESS("Business"),
        EXPERT("Expert"),
        SPECIAL("Special"),
        STARTER("Starter"),
        CSR("CSR"),
        EPD("EPD"),
        GHG("GHG"),
        LCC("LCC"),
        XOBSOLETE("X-OBSOLETE"),
        XPHASEOUT("X-PHASE-OUT")

        private String featureClass

        private LicenseFeatureClass(String featureClass) {
            this.featureClass = featureClass
        }

        String getFeatureClass() {
            return featureClass
        }

        @Override
        String toString() {
            return featureClass
        }

        public static List<String> list() {
            return [BUSINESS.toString(), EXPERT.toString(), SPECIAL.toString(), CSR.toString(), EPD.toString(), GHG.toString(), STARTER.toString(),
                    LCC.toString(), XOBSOLETE.toString(), XPHASEOUT.toString()]
        }
    }

    static final enum LimitFeatureId {
        TWO_THOUSAND_IMPORT_ROW_LIMIT("2000rowImportLimit", 2000),
        ONE_THOUSAND_IMPORT_ROW_LIMIT("1000rowImportLimit", 1000)

        private String featureId
        private int limit

        private LimitFeatureId(String featureId, int limit) {
            this.featureId = featureId
            this.limit = limit
        }

        String getFeatureId() {
            return featureId
        }

        int getLimit() {
            return limit
        }
    }

    static final enum EntityType {
        PARENT("parent"),
        CHILD("child"),

        private final String type

        private EntityType(String type) {
            this.type = type
        }

        String toString() {
            return type
        }
    }

    static final enum EntityClass {
        BUILDING("building"),
        PRODUCT("buildingProduct"),
        DESIGN("design"),
        OPERATING_PERIOD("operatingPeriod"),
        PORTFOLIO("portfolio"),
        INFRASTRUCTURE("infraStructure"),
        CITY_DISTRICT("cityDistrict"),
        CONSTRUCTION_PRODUCT("constructionProduct"),
        ORGANIZATION("organization"),
        MATERIAL_SPECIFIER("materialSpecifier"),
        CARBON_DESIGN("carbonDesign"),
        COMPONENT("component")

        private String type

        private EntityClass(String type) {
            this.type = type
        }

        String getType() {
            return type
        }
        /**
         *
         * @return list of entity classes that are not supported for project template feature
         */
        static List<String> getNotSupportedForTemplate() {
            return [PORTFOLIO.type, ORGANIZATION.type]
        }

        @Override
        String toString() {
            return type
        }
    }

    static final enum ParentEntityClass {
        BUILDING("building"),
        PORTFOLIO("portfolio"),
        CITY_DISTRICT("cityDistrict"),
        CONSTRUCTION_PRODUCT("constructionProduct"),
        ORGANIZATION("organization"),

        private String entityClass

        private ParentEntityClass(String entityClass) {
            this.entityClass = entityClass
        }

        @Override
        public String toString() {
            return entityClass
        }
    }

    static final enum ResourceGroup {
        BUILDING_TYPES("buildingTypes"),
        STAGES_OF_CONSTRUCTION("stagesOfConstruction"),
        FINLAND_CITY("finlandCity"),
        WORLD("world"),
        EUROPE("europe"),
        ENTITY_CLASS("entityClass"),
        SYSTEM_LOCALE("systemLocale")

        private String resourceGroup

        private ResourceGroup(String resourceGroup) {
            this.resourceGroup = resourceGroup
        }

        String toString() {
            return resourceGroup
        }
    }

    static final enum QueryCalculationMethod {
        POINTS("points"),
        SUM("sum")

        private String method

        private QueryCalculationMethod(String method) {
            this.method = method
        }

        String toString() {
            return method
        }
    }

    static final enum QueryCalculationProcess {
        RECURRING_MAINTENANCE("recurringMaintenance"),
        RECURRING_REPLACEMENT("recurringReplacement"),
        TRANSPORT("transport"),
        WASTE("waste"),
        DISPOSAL("disposal"),
        BENEFIT("benefit"),
        REPLACE_RESOURCE("replaceResource"),
        TRANSFORMATION("transformation"),
        EOLTRANSPORT("eolTransport"),
        C2_EOL("C2-EOL"),
        C3_EOL("C3-EOL"),
        C4_EOL("C4-EOL"),
        D_EOL("D-EOL"),
        C1_C4_SUM("C1-C4_SUM"),
        REFRIGERANT("refrigerant")

        private String process

        private QueryCalculationProcess(String process) {
            this.process = process
        }

        String toString() {
            return process
        }
    }

    static final enum QueryCalculationProcessQuestion {
        TRANSPORT_RESOURCE("transportResourceQuestion"),
        TRANSPORT_DISTANCE("transportDistanceQuestion"),
        REFRIGERANT_RESOURCE("refrigerantResourceQuestion"),
        REFRIGERANT_QUANTITY("refrigerantQuantityQuestion")

        private String processQuestion

        private QueryCalculationProcessQuestion(String processQuestion) {
            this.processQuestion = processQuestion
        }

        String toString() {
            return processQuestion
        }
    }

    static final enum ResultCategoryComparisonMethod {
        EQUAL_OR_SMALLER_THAN("eaqualOrSmallerThan")

        private String method

        private ResultCategoryComparisonMethod(String method) {
            this.method = method
        }

        String toString() {
            return method
        }
    }

    static final enum EntityUserType {
        READONLY("Readonly"),
        MODIFIER("Modifier"),
        MANAGER("Manager")

        String userType

        private EntityUserType(String userType) {
            this.userType = userType
        }

        String toString() {
            return userType
        }

        String getMessageKey() {
            return userType?.toLowerCase()
        }
    }

    static final enum OrganizationUserType {
        ADMINISTRATOR("administrator"),
        NORMAL_USER("user"),
        REQUEST_TO_JOIN("requestToJoin")

        String userType

        private OrganizationUserType(String userType) {
            this.userType = userType
        }

        String toString() {
            return userType
        }
    }

    static final enum SystemLocale {
        FI("FI"), EN("EN"), SV("SV"), NO("NO"), RU("RU")

        String locale

        private SystemLocale(String locale) {
            this.locale = locale
        }

        public String toString() {
            return locale
        }
    }

    static final enum RulesetType {
        DISCARD("discard"),
        WARN("warn"),
        MAPPING("mapping"),
        USER("user")

        String rulesetType

        private RulesetType(String type) {
            this.rulesetType = type
        }

        String toString() {
            return rulesetType
        }
    }

    static final enum AccountImageType {
        PRODUCTIMAGE("productImage"),
        MANUFACTURINGDIAGRAM("manufacturingDiagram"),
        BRANDINGIMAGE("brandingImage")

        String accountImageType

        private AccountImageType(String type) {
            this.accountImageType = type
        }

        String toString() {
            return accountImageType
        }
    }

    static final enum ConditionTriggerReportRemoval {
        LICENSEKEY("licenseKey"),
        QUERYVALUE("queryValue")

        String conditionTrigger

        private ConditionTriggerReportRemoval(String type) {
            this.conditionTrigger = type
        }

        String toString() {
            return conditionTrigger
        }
    }

    static final enum InputType {
        SELECT("select"),
        CHECKBOX("checkbox"),
        RADIO("radio"),
        TEXTAREA("textarea"),
        TEXT("text"),
        CHECKBOXRADIO("checkboxRadio")

        String inputType

        private InputType(String type) {
            this.inputType = type
        }

        String toString() {
            return inputType
        }
    }

    static final enum ValueEvaluation {
        EMPTY("empty"),
        NOTEMPTY("notEmpty"),
        POSITIVE("positive"),
        NEGATIVE("negative"),
        EQUAL("equal"),
        NOTEQUAL("notEqual")

        String valueEvaluation

        private ValueEvaluation(String condition) {
            this.valueEvaluation = condition
        }

        String toString() {
            return valueEvaluation
        }
    }

    static final enum BasicQueryVirtualSectionIds {
        MIN_INFO("minimumInfo"),
        PROJECT_DATA("projectData"),
        FIRST_DESIGN("projectTemplate"),
        OPTIONS("quickOptions")

        String virtualSectionId

        private BasicQueryVirtualSectionIds(String type) {
            this.virtualSectionId = type
        }

        String toString() {
            return virtualSectionId
        }
    }

    static final enum UserFilterChoice {
        MARKET_DATASET("marketDataset")

        private final String filter

        private UserFilterChoice(String filter) {
            this.filter = filter
        }

        String getFilter() {
            return filter
        }
    }

    static final enum Unicode {
        MINUS_SIGN("\u2212"),
        HYPHEN("\u2010")

        private final String code

        private Unicode(String code) {
            this.code = code
        }

        String getCode() {
            return code
        }
    }

    static final enum AdditionalQuestionDisplayCriteria {
        RESOURCE_SUBTYPE("resourceSubType"),
        USE_STAGES_SEPERATED("useStagesSeparated")

        private final String criteria

        private AdditionalQuestionDisplayCriteria(String criteria) {
            this.criteria = criteria
        }

        String getCriteria() {
            return criteria
        }
    }

    // use this enum to track what keys we could have in AdditionalQuestionAnswers of dataset
    static final enum AdditionalQuestionAnswers {
        MASS_PER_UNIT_KG(MASS_PER_UNIT_KG_QUESTIONID),
        EOL_PROCESS(EOL_QUESTIONID),
        LOCAL_COMP(LOCAL_COMP_QUESTIONID)

        private final String addQA

        private AdditionalQuestionAnswers(String addQA) {
            this.addQA = addQA
        }

        String getAddQA() {
            return addQA
        }
    }

    static final enum CalculationRuleId {
        GWP_TN("GWP_tn"),
        TRACI_GWP_TN("traciGWP_tn")

        String calculationRuleId

        private CalculationRuleId(String calculationRuleId) {
            this.calculationRuleId = calculationRuleId
        }

        String toString() {
            return calculationRuleId
        }

        String getCalculationRuleId() {
            return calculationRuleId
        }
    }


    static final enum cd3D {
        RESOURCEID("resourceId"),
        PROFILEID("profileId"),
        MANUALID("manualId"),
        RESOURCENAME("resourceName"),
        RESOURCESUBTYPE("resourceSubType"),
        ACTIVE("active"),
        DATAPROPERTIES("dataProperties"),
        COMBINEDUNITS("combinedUnits"),
        MATERIAL("material"),
        ENERGY("energy"),
        CONSTRUCTION("construction"),
        PRIVATECONSTRUCTION("privateConstruction"),
        RESOURCECATEGORY("resourceCategory"),
        COMMENT("comment"),
        THICKNESS_MM("thickness_mm"),
        THICKNESS_IN("thickness_in"),
        THICKNESS("thickness"),
        ALLOWVARIABLETHICKNESS("allowVariableThickness"),
        QUERYID("queryId"),
        SECTIONID("sectionId"),
        QUESTIONID("questionId"),
        METRIC("metric")

        String constant

        private cd3D(String constant) {
            this.constant = constant
        }

        String get() {
            return constant
        }
    }

    static final enum CalculationResultDatasets {
        RESULT("result"),
        RESOURCEID("resourceId"),
        PROFILEID("profileId")

        String key

        private CalculationResultDatasets(String key) {
            this.key = key
        }

        String getKey() {
            return key
        }
    }

    static final enum Unit {
        TONKM("tonkm"),
        TON("ton")

        String unit

        private Unit(String unit) {
            this.unit = unit
        }

        String getUnit() {
            return unit
        }
    }

    static final enum ConditionalDisplayMethod {
        SHOW("show"),
        HIDE("hide")

        String name

        private ConditionalDisplayMethod(String name) {
            this.name = name
        }

        String getName() {
            return name
        }
    }

    static final enum ConditionalDisplayResolveMethod {
        ALL("all"),
        ANY("any"),
        NONE("none")

        String name

        private ConditionalDisplayResolveMethod(String name) {
            this.name = name
        }

        String getName() {
            return name
        }
    }

    static final String SUBMIT_TO_EPDHUB = "submit.to.epd"
    static final String DOWNLOAD_DIGITAL_EPD = "export.epd.json"
    static final String EPDHUB_SUBMISSION = "epd.hub.submission"
    static final String EXPORT_BETIE_XML = "export.betie.xml"
    static final List<String> QUERIES_FOR_PARAMETERS = ["betie_A4A5","betie_Materials"]
    static final String TYPE_OUVRAGE = "type_ouvrage"
    static final String IMPLEMENTATION = "implementation"
    static final String BETIE_A4A5 = "betie_A4A5"
    static final String PROJECT_DESCRIPTION = "projectDescription"
    static final String BETIE_PRODUCTDESCRIPTION = "betie_productDescription"
    static final String QUESTION_NOMFDES = "nom_FDES"
    static final String QUESTION_DUREEVIE = "duree_vie"
    static final String BETIE_PUBLICADDRESS = "https://www.oneclicklcaapp.com/app/?channelToken=betie"
    static final String BETIE = "Betie"
    static final String BETIE_CONFIGURATOR_VERSION = "4.0.0"
    static final String STRING_ZERO = "0"
    static final Integer BETIE_CONFIGURATOR_CODE = 1001
    static final String EXPOSURE_CLASS = "exposureClass_resource"
    static final String BETIE_DATASOURCES_TABLE = "datasourcesTableBetie"
    static final DESCRIPTIVE_DATA_COLUMN_NAME = "CONTENT"

    //sw-2244
    static final String PRIVATE_DATA_VERIFICATION_NUMBER = "verificationNumber"
    static final String PRIVATE_DATA_VERIFICATION_PROOF = "verificationProof"
    static final List<String> PRIVATE_DATA_NUMERIC_CONVERSION_SKIP_LIST = [PRIVATE_DATA_VERIFICATION_NUMBER]
    static final enum VerificationProof {
        INES_IDENTIFIER("iniesIdentifier"),
        VERIFICATION_CERTIFICATE("verificationCertificate")

        String name

        private VerificationProof(String name) {
            this.name = name
        }

        String getName() {
            return name
        }
    }

    // ImportMapper constants
    static final String IMPORT_MAPPER = "importMapper"
    static final String IMPORT_FILENAME = "importFileName"
    static final String IMPORT_FILEPATH = "importFilePath"
    static final String TEMPORARY_IMPORT_DATA = "temporaryImportData"
    static final String ORIGINATING_SYSTEM = "originatingSystem"
    static final String MANUAL = "Manual"
    static final String FILE_EXTENSION = "fileExtension"
    static final String PRESET_FILTER = "presetFilter"
    static final String NO_LICENSE = "noLicense"
    static final String XLS = ".xls"
    static final String XLSX = ".xlsx"

}
