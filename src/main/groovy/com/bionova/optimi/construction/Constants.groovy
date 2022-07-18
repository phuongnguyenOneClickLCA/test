/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.construction

import com.bionova.optimi.frenchTools.FrenchConstants

/**
 * @author Pasi-Markus Mäkelä
 */
class Constants {

    static final String APPLICATION_ID = "360optimi"
    static final String DEFAULT_LOCALE = "en"
    static final NON_SAVEABLE_DATASET_PARAMETERS = [
            "_action_save",
            "org.grails.web.servlet.mvc.SynchronizerTokensHolder.TOKEN_KEY",
            "org.grails.web.servlet.mvc.SynchronizerTokensHolder.TOKEN_URI",
            "entityId",
            "childEntityId",
            "queryId",
            "indicatorId",
            "resourceId",
            "sectionChooser",
            "save",
            "name",
            "upload",
            "designResults",
            "formChanged",
            "licenseId",
            "entityClass",
            "targetQueryId",
    ]
    static final String DEFAULT_SERVICELIFE = "serviceLife"
    static final String SERVICE_LIFE_COMMERCIAL = "serviceLifeCommercial"
    static final String SERVICE_LIFE_TECHNICAL = "serviceLifeTechnical"
    static final String SERVICE_LIFE_RICS = "serviceLifeRICS"

    static final String DEFAULT_TRANSPORT = "transport"
    static final String DEFAULT_LOCALCOMPCOUNTRY = "localCompCountry"
    static final String UNDEFINED_ADDITIONAL_QUESTION_ANSWER = "undefined"
    static final String PUBLIC_CONSTRUCTION_GROUP = "User created public constructions"
    static final String DEFAULT_TRANSPORT_RICS = "defaultTransportRICS"
    static final String DEFAULT_TRANSPORT_RICS_LEG2 = "defaultTransportRICSleg2"
    static final String TRANSPORT_RESOURCE_ID_RICS = "transportResourceIdRICS"
    static final String TRANSPORT_RESOURCE_ID_RICS_LEG2 = "transportResourceIdRICSleg2"

    static final String SITE_WASTAGE_REFERENCE_ATTR_ID = "siteWastageReference"
    static final String SITE_WASTAGE_REFERENCE_BOVERKET_ATTR_ID = "siteWastageReferenceBoverket"
    static final String DEFAULT_TRANSPORT_BOVERKET_REFERENCE_ATTR_ID = "defaultTransportBoverketReference"
    static final String TRANSPORTATION_DISTANCE_REFERENCE_ATTR_ID = "transportationDistanceReference"
    static final String TRANSPORTATION_DISTANCELEG2_ATTR_ID = "transportationDistanceleg2"
    static final String TRANSPORTATION_METHOD_LEG2_ATTR_ID = "transportationMethodLeg2"
    static final String SERVICE_LIFE_REFERENCE_ATTR_ID = "serviceLifeReference"
    static final String DEFAULT_TRANSPORT_BOVERKET_ATTR_ID = "defaultTransportBoverket"
    static final String DEFAULT_TRANSPORT_BOVERKET_LEG2_ATTR_ID = "defaultTransportBoverketLeg2"
    static final String TRANSPORT_RESOURCE_ID_BOVERKET_ATTR_ID = "transportResourceIdBoverket"
    static final String TRANSPORT_RESOURCE_ID_BOVERKET_LEG2_ATTR_ID = "transportResourceIdBoverketLeg2"
    static final String SITE_WASTAGE_ATTR_ID = "siteWastage"
    static final String SITE_WASTAGE_BOVERKET_ATTR_ID = "siteWastageBoverket"

    static final String REFERENCE = "Reference"

    static final String DEFAULT = "default"

    static final List<String> PARAMETER_QUERYIDS = [
            "LCCProjectParametersQuery",
            com.bionova.optimi.core.Constants.LCA_PARAMETERS_QUERYID,
            FrenchConstants.FEC_QUERYID_PROJECT_LEVEL
    ]

    static final int MAX_NUMBER_OF_CREATED_PROJECTS = 20

    static final enum UrlParameter {
        ENTITY_ID("entityId")

        private String parameter

        private UrlParameter(String parameter) {
            this.parameter = parameter
        }

        public String toString() {
            return parameter
        }
    }

    static final enum ExcelCellHeaders {
        CLASS("CLASS"),
        MATERIAL("MATERIAL"),
        QUANTITY("QUANTITY"),
        QTY_TYPE("QTY_TYPE"),
        SOUS_LOT("SOUS-LOT"),
        ZONE("ZONE"),
        COMMENT("COMMENT"),
        PART_OF_CONSTRUCTION("PART OF CONSTRUCTION"),
        IGNORE("IGNORE"),
        OCLID("OCLID")

        private String name

        private ExcelCellHeaders(String name) {
            this.name = name
        }

        String toString() {
            return name
        }
    }

    static final enum ConfigName {
        LOG_URL("log.url"),
        LOG_PATH("log.path"),
        BASIC_QUERY_ID("basicQueryId"),
        RESULT_TEMPLATE_PATH("resultTemplatePath"),
        ADDITIONAL_QUESTIONS_QUERY_ID("additionalQuestionsQueryId"),
        FRONTPAGE_URL("frontpageUrl"),
        HELP_FILE_URL("helpFileUrl_"),
        CITY_CALCULATION_DEFINITION("cityCalculationDefinition"),
        DEFAULT_CITY_DISTRICT("defaultCityDistrict"),
        ONE_STANDARD_DEVIATION("oneStandardDeviation"),
        EMAIL_LOGGING_IN_DEV("emailLogginInDev"),
        JSON_EXPORT_PATH("jsonExportPath"),
        SHOW_MISSING_DATA_ERRORS("ShowMissingDataErrors"),
        DATA_MIGRATION_ALLOWED("dataMigrationAllowed"),
        WIDGET_EXPORT_PREFIX("widgetExportPrefix"),
        WIDGET_EXPORT_URL_PREFIX("widgetExportUrlPrefix"),
        MAIL_CHIMP_API_KEY("mailChimpApiKey"),
        UNALLOWED_ACCOUNT_EMAILS("unallowedAccountEmails"),
        API_DEFAULT_APPLICATION_ID("APIDefaultApplicationId"),
        API_DEFAULT_IMPORT_MAPPER_ID("APIDefaultImportMapperId"),
        API_DEFAULT_INDICATOR_ID("APIDefaultIndicatorId"),
        COMPOSITE_RESOURCE_ID("importMapperCompositeResourceId"),
        COMPOSITE_PROFILE_ID("importMapperCompositeProfileId"),
        TERMS_CONDITIONS_URL("termsAndConditionsUrl"),
        TWOSTEPAUTH_DAYS("twostepAuthDays"),
        TWOSTEPAUTH_DAYS_START("twostepAuthenticationStartDate"),
        TWOSTEPAUTH_APPLICABLE_DOMAINS("twostepAuthenticationApplicableDomains"),
        LICENSE_TIER("licenseTier"),
        NMD_API_ENDPOINT("nmdApiEndpoint"),
        NMD_API_AUTH_ENDPOINT("nmdApiAuthEndpoint"),
        NMD_REFRESH_TOKEN("nmdRefreshToken"),
        NMD_API_ID("nmdApiId")

        private String name

        private ConfigName(String name) {
            this.name = name
        }

        String toString() {
            return name
        }
    }

    static final enum SessionAttribute {
        ENTITY("entity"),
        DESIGN("design"),
        OPERATING_PERIOD("operatingPeriod"),
        USER("user"),
        @Deprecated
        LOGGED_IN_USER("loggedInUserObject"),
        TYPE_DEFINITION("typeDefinition"),
        USER_ANSWERS("userAnswers"),
        QUERY("query"),
        TASK("task"),
        LANGUAGE("lang"),
        CHOSEN_OPERATING_PERIODS("chosenOperatingPeriods"),
        CHOSEN_INDICATORS("chosenIndicators"),
        TOKEN_OK("tokenOk"),
        REQUEST_URLS("requestUrls"),
        RESULT_FORMATTING("resultFormatting"),
        CHANNEL_FEATURE("channelFeature"),
        CHANNEL_TOKEN("channelToken"),
        CALCULATION_ERROR("calculationError"),
        NMD_ELEMENT_LIST("nmdElementList"),
        FORWARD_ORIGIN("forwardOrigin"),
        OPEN_INDICATORID("openIndicatorId"),
        OPEN_CD("openCarbonDesigner"),
        OPEN_QUERY("openQuery"),
        EXPAND_INPUTS("expandInputs"),
        COPY_DATASETS_FROM_TEMPLATE("copyDatasets"),
        ENABLED_INDICATORIDS("enabledIndicatorIds"),
        FLASH_SCOPE("optimi.flashScope"),
        RSET_MESSAGE("rsetMessage"),
        WORD_DOC_GRAPHS("wordDocGraphs"),
        NUMBER_OF_CREATED_PROJECTS("numberOfCreatedProjects")

        private String attribute

        private SessionAttribute(String attribute) {
            this.attribute = attribute
        }

        String getAttribute() {
            return attribute
        }

        public String toString() {
            return attribute
        }
    }

    static final enum IndicatorUse {
        DESIGN("design"),
        OPERATING("operating")

        String indicatorUse

        private IndicatorUse(String indicatorUse) {
            this.indicatorUse = indicatorUse
        }

        String toString() {
            return indicatorUse
        }
    }
}
