package com.bionova.optimi.construction.controller

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.Account
import com.bionova.optimi.core.domain.mongo.Benchmark
import com.bionova.optimi.core.domain.mongo.CalculationRule
import com.bionova.optimi.core.domain.mongo.Construction
import com.bionova.optimi.core.domain.mongo.DataCardRow
import com.bionova.optimi.core.domain.mongo.DataCardSection
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.EolProcess
import com.bionova.optimi.core.domain.mongo.Feature
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.IndicatorQuery
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ResourceType
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.util.DomainObjectUtil
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import org.apache.commons.lang3.StringUtils

import javax.servlet.http.HttpServletResponse
import java.text.DecimalFormat

class DataCardController {

    def optimiResourceService
    def utilService
    def constructionService
    def accountImagesService
    def configurationService
    def licenseService
    def questionService
    def eolProcessService
    def resourceFilterCriteriaUtil
    def featureService
    def accountService
    def maskingService
    def stringUtilsService
    def datasetService
    def dataCardService
    def entityService
    def indicatorService
    def userService
    def resourceTypeService
    def resourceService
    def localizationService

    private static final List<String> ATTR_IDS_FOR_DOUBLE_CHECKING = [
            com.bionova.optimi.construction.Constants.DEFAULT_TRANSPORT_BOVERKET_ATTR_ID, com.bionova.optimi.construction.Constants.DEFAULT_TRANSPORT_BOVERKET_LEG2_ATTR_ID
    ]
    private static final List<String> TRANSPORT_RESOURCE_ID_BOVERKET_ATTR_IDS = [
            com.bionova.optimi.construction.Constants.TRANSPORT_RESOURCE_ID_BOVERKET_ATTR_ID, com.bionova.optimi.construction.Constants.TRANSPORT_RESOURCE_ID_BOVERKET_LEG2_ATTR_ID
    ]

    /**
     * This is the API entry point for calling the data card information for external applications
     *
     * Note: This is moved to its own controller and endpoint, as it can be useful in all applications with our data
     * @return
     */
    @Secured(["ROLE_AUTHENTICATED"])
    def getDataCardInfo() {
        Object requests = request.JSON
        Map<String, Object> dataCardMap = renderDatacardForWebApp(requests)
        if (dataCardMap) {
            render(dataCardMap as JSON)
        } else {
            render(HttpServletResponse.SC_PRECONDITION_FAILED)
        }
    }

    /**
     * A heavily modified version of renderSourceListing() datacard method, primarily stripping out the majority
     * of hardcoded html tags and returning mapped objects to be processed by the frontend
     *
     * Note: Not all features of the datacard are re-added and not all sections are converted, todo in CD beta+
     * @param requests
     * @return
     */
    Map<String, Object> renderDatacardForWebApp(Object requests) {

        Map<String, Object> dataCardMap = [:]
        String resourceId = requests.resourceId
        String profileId = requests.profileId
        String indicatorId = requests.indicatorId
        String customDatasetName = requests.customDatasetName
        Resource resource = resourceId ? optimiResourceService.getResourceWithParams(resourceId, null, true) : null
        if (!resource) {
            log.error("No resource found for sourcelisting with params ${requests}")
            return null
        } else {
            String entityId = requests.entityId
            String manualId = requests.manualId
            String questionId = requests.questionId
            String sectionId = requests.sectionId
            String queryId = requests.queryId
            Boolean hideImage = requests.hideImage

            ResourceType subType = resourceService.getSubType(resource)
            User user = userService.getCurrentUser()
            Account account = userService.getAccount(user)
            Boolean isFavorite = user ? userService.getFavoriteMaterials(user)?.containsKey(resourceId + '.' + profileId) : false
            String userLanguage = DomainObjectUtil.mapKeyLanguage ?: "EN"
            Entity entity = entityId ? entityService.getEntityById(entityId, null) : null
            Entity parent = entity?.parentById
            Resource countryResource = parent?.getCountryResource()
            Dataset dummyDataset = resource.isDummy && manualId ? entity?.datasets?.find { it.manualId == manualId } : null
            EolProcess eolProcess = subType ? eolProcessService.getEolProcessForSubType(subType, countryResource) : null
            Question ques = questionService.getQuestion(queryId, questionId)
            if (!sectionId && ques) {
                sectionId = ques.sectionId
            }

            // To understand this block of codes, check ticket 15399 && 15409
            String eolCalculationMethod = parent?.getEOLCalculationMethod() ?: ''
            Map<String, Double> eolImpactsByStages = resource.getResolvedGWPImpactsByStages(Constants.EOL_STAGES)
            boolean useEpdScenario = eolCalculationMethod == '20EPD' && eolImpactsByStages
            boolean useEpdScenarioButNotAvailable = eolCalculationMethod == '20EPD' && !eolImpactsByStages


            Indicator indicator = indicatorId ? indicatorService.getIndicatorByIndicatorId(indicatorId, true) : null
            List<DataCardSection> sourceListing = indicator?.getResolveResourceExtraInformationInfoBubble() ?: utilService.getDefaultConfigurationForDataCard()
            List<String> requiredLicenseKeys = utilService.getResolvedLicenseKeysForSourceListing(sourceListing)
            requiredLicenseKeys.addAll(Feature.EPD_DOWNLOAD, Feature.COMPARE_MATERIAL)
            Map<String, Map> blockAndWarningResourceFilterCriteria = [
                    (IndicatorQuery.WARNING_RESOURCE): ques?.getWarningResourceFilterCriteria(indicator, parent),
                    (IndicatorQuery.BLOCK_RESOURCE)  : ques?.getBlockResourceFilterCriteria(indicator, parent)
            ]
            blockAndWarningResourceFilterCriteria.removeAll{!it.value}

            Map<String, Boolean> featuresAllowed = licenseService.featuresAllowedByCurrentUserOrProject(parent, requiredLicenseKeys)

            boolean epdDownloadLicensed = featuresAllowed?.get(Feature.EPD_DOWNLOAD)
            boolean showPerformanceRanking = resource.benchmark && "OK" == resource.benchmark.status && subType && subType.isSubType && subType.standardUnit && subType.benchmarkResult && indicator?.connectedBenchmarks
            String displayDownloadText = ''
            String epdDownloadLink = ''
            String b64ImageEPD = ''
            String imageDescription = resource.imgDescription

            // Fetch EPD and first page of EPD as image
            if (resource.downloadLink && "-" != resource.downloadLink.trim() && resource.downloadLink.startsWith("http")) {
                displayDownloadText = message(code: 'resource.externalLink')
                epdDownloadLink = resource.downloadLink
            } else if (resourceService.getEpdFile(resource.downloadLink)?.exists()) {
                String epdFilePathPrefix = configurationService.getConfigurationValue(null, "epdFilePathPrefix")
                displayDownloadText = message(code: 'resource.pdfFile_download')
                epdDownloadLink = createLink(controller: 'util', action: 'getEpdFile', params: [resourceId: resource.resourceId, profileId: resource.profileId])
                b64ImageEPD = epdDownloadLicensed && !hideImage ? dataCardService.getEpdFirstPageB64(epdFilePathPrefix, resource) : ''
            }

            Construction construction = resource.construction && resource.constructionId  && (!b64ImageEPD || !resource.imgLink) ? constructionService.getConstruction(resource.constructionId) : null
            String imgLink = !b64ImageEPD ? (resource.imgLink ?: construction?.imgLink ?: '') : ''
            String imgCopyRightText = imgLink == resource.imgLink ? resource.copyrightText : imgLink == construction?.imgLink ? construction?.copyrightText : ''

            // Get Brand
            String b64BrandImage = accountImagesService.getAccountImageAsB64Data(resource?.brandImageId)

            // Check full static name
            String resourceFullName = optimiResourceService.getResourceDisplayName(resource, true, dummyDataset)
            boolean showResourceFilterWarning = false
            String resourceFilterQualityWarning = Resource.QUALITY_WARNING_WARN_TYPE
            Map<String, String> flagMap = [:]
            List<Map<String, Object>> performanceList = []
            List<Map<String, Object>> otherImpactCategories = []
            List<String> warningStrings = []
            Map<String, String> expiredResource = [:]

            String transportationDefault = parent?.defaults?.get(com.bionova.optimi.construction.Constants.DEFAULT_TRANSPORT)
            List additionalQuestionIds = indicator?.indicatorQueries?.findResults { IndicatorQuery indicatorQuery ->
                (indicatorQuery.queryId == queryId) ? indicatorQuery.additionalQuestionIds : null
            }?.flatten()?.unique()

            List<DataCardSection> sections = sourceListing?.each { DataCardSection section ->
                section.authorizedToDisplay = section.licenseKey ? featuresAllowed?.get(section.licenseKey) : true
                // Do not skip looping if not authorized, there might be some logic in processing the customAttributes to show license warning text or reassign row.authorizedToDisplay
                section.heading = section.name?.get(userLanguage) ?: section.name?.get("EN") ?: 'Missing name'
                section.help = section.helpLocalized ? message(code: section.helpLocalized) : ""

                if (section.customRendering) {

                    if (!section.sectionId) {
                        section.customContent = "Empty sectionId with 'customRendering': true is not allowed. Double check configuration or contact a developer"
                    } else if ("description" == section.sectionId) {
                        section.customContent = resource.productDescription ? resource.productDescription : ''
                    } else {
                        section.customContent = "Unrecognized custom sectionId (${section.sectionId}) with 'customRendering': true. Double check configuration or contact a developer"
                    }
                    section.isEmpty = !section.customContent

                } else if (!section.resourceAttributes) {

                    section.customRendering = true
                    section.customContent = "The object with sectionId (${section.sectionId}) has incorrect format. It must have either 'resourceAttributes' or 'customRendering' : true. Double check configuration or contact a developer"

                } else {
                    section.resourceAttributes.each { DataCardRow row ->
                        if (!utilService.checkIfDataCardRowIsForDisplaying(row, additionalQuestionIds)) {
                            return
                        }

                        row.authorizedToDisplay = row.licenseKey ? featuresAllowed?.get(row.licenseKey) : true
                        row.heading  = row.name?.get(userLanguage) ?: row.name?.get("EN") ?: 'Missing name'
                        row.help = row.helpLocalized ? message(code: row.helpLocalized) : ''
                        utilService.handleConditionalHideOnDataCardRow(row, resource)

                        if (!row.resourceAttribute && !row.resourceSubTypeAttribute && !row.customAttribute) {

                            row.content = "Configuration must have either resourceAttribute or resourceSubTypeAttribute or customAttribute for this row."

                        } else if (row.resourceAttribute || row.resourceSubTypeAttribute) {

                            def sourceResource = row.resourceAttribute ? resource : subType
                            String attributeKey = row.resourceAttribute ?: row.resourceSubTypeAttribute
                            def value = sourceResource ? DomainObjectUtil.callGetterByAttributeName(attributeKey, sourceResource) : ''
                            if (value) {
                                value = (value instanceof List<String>) ? value.join(', ') : value
                                value = value != "-" ? value.toString() : ''
                            }
                            row.content = value ? value.toString() : ''

                        } else if (row.customAttribute) {
                            String customAttribute = row.customAttribute
                            String customContent = ''

                            // NOTE: this is in a loop for each row, hence the customContent will take values only from one of the IF statements
                            // To add a qMark with popover, either define row.help (recommended) OR add class triggerPopover to your custom qMark for consistency. The keepPopoverOnHover javascript method is used on the data card to create popover
                            if ('localizedName' == customAttribute) {

                                if (userLanguage && userLanguage == 'EN') {
                                    row.authorizedToDisplay = false
                                } else if (userLanguage) {
                                    String localName = DomainObjectUtil.callGetterByAttributeName("name${userLanguage}", resource)
                                    if (localName) {
                                        customContent = "${localName}"
                                    }
                                }
                            } else if ('subTypeLocalizedName' == customAttribute) {

                                if (userLanguage && subType) {
                                    String localNameSubType = DomainObjectUtil.callGetterByAttributeName("name${userLanguage}", subType)
                                    customContent = localNameSubType ?: ''
                                }

                            } else if ('areas' == customAttribute) {

                                if (resource.areas) {
                                    String isoFileLocalPath = configurationService.getConfigurationValue(com.bionova.optimi.construction.Constants.APPLICATION_ID, "isoFlagLocalUrlPath")
                                    String globe = "${isoFileLocalPath}globe.png"
                                    Map<String, String> isoFlagPaths = [:]
                                    List<String> isoCodes = []

                                    if (resourceService.getIsLocalResource(resource.areas)) {
                                        if (countryResource && countryResource.isoCountryCode) {
                                            isoCodes.add(countryResource.isoCountryCode)
                                        } else if (resource.representativeArea) {
                                            isoCodes = optimiResourceService.getResourceWithParams(resource.representativeArea, null, null)?.isoCodesByAreas?.values()?.toList()
                                        }
                                    } else {
                                        if (!resource?.isoCodesByAreas) {
                                            isoCodes = optimiResourceService.getresourceIsoCodes(resource.areas, resource.additionalArea)?.values()?.toList()
                                        } else {
                                            isoCodes = resource?.isoCodesByAreas?.values()?.toList()
                                        }
                                    }

                                    if (isoCodes) {
                                        isoCodes.collect({ it.toLowerCase() }).each { String isoCountryCode ->
                                            String isoFlag

                                            if (System.getProperty("islocalhost")) {
                                                isoFlag = "${isoFileLocalPath}${isoCountryCode}.png"
                                                if (!asset.assetPathExists(src: "/isoflags/${isoCountryCode}.png")) {
                                                    log.error("missing flag for countrycode ${isoCountryCode}")
                                                    isoFlag = globe
                                                }
                                            } else {
                                                String isoFilePath = configurationService.getConfigurationValue(com.bionova.optimi.construction.Constants.APPLICATION_ID, "isoFlagUrlPath")
                                                String rootUrlPath = configurationService.getConfigurationValue(com.bionova.optimi.construction.Constants.APPLICATION_ID, "rootUrlPath")
                                                def testIsoFilePath = new File("${rootUrlPath}${isoFilePath}${isoCountryCode}.png")
                                                if (testIsoFilePath.exists()) {
                                                    isoFlag = "${isoFilePath}${isoCountryCode}.png"
                                                } else {
                                                    log.error("missing flag for countrycode ${isoCountryCode} with filepath ${testIsoFilePath?.absolutePath} from server")
                                                    isoFlag = "${isoFileLocalPath}${isoCountryCode}.png"
                                                    if (!asset.assetPathExists(src: "/isoflags/${isoCountryCode}.png")) {
                                                        log.error("missing flag for countrycode ${isoCountryCode} from assets")
                                                        isoFlag = globe
                                                    }
                                                }
                                            }
                                            if (isoFlag) {
                                                isoFlagPaths.put(resource?.isoCodesByAreas?.find({
                                                    it.value == isoCountryCode
                                                })?.key?.toLowerCase(), "${isoFlag}")
                                            }
                                        }
                                    }

                                    String areas = resourceService.getIsLocalResource(resource.areas) && countryResource ? countryResource.nameEN : resource.areas.join(", ")
                                    areas.split(", ")?.each { String area ->
                                        flagMap.put(optimiResourceService.getCountryLocalizedName(area)?.capitalize(), "${ isoFlagPaths?.get(area?.toLowerCase() ?: '') ?: '' }")
                                    }
                                }
                            } else if ('customDatasetName' == customAttribute) {
                                if (customDatasetName) {
                                    customContent = customDatasetName
                                } else {
                                    row.authorizedToDisplay = false
                                }
                            } else if ('warning' == customAttribute) {
                                if (blockAndWarningResourceFilterCriteria) {
                                    showResourceFilterWarning = blockAndWarningResourceFilterCriteria.any{key, value ->
                                        Map<String, List<Object>> propertiesWithoutWarningString = value.findAll{
                                            !(it.key in [IndicatorQuery.WARNING_MESSAGE_INDICATOR, IndicatorQuery.BLOCK_MESSAGE_INDICATOR])
                                        }

                                        if(resourceFilterCriteriaUtil.resourceOkAgainstFilterCriteria(resource, null, propertiesWithoutWarningString, true)){
                                            if (key == IndicatorQuery.BLOCK_RESOURCE){
                                                resourceFilterQualityWarning = Resource.QUALITY_WARNING_BLOCK_TYPE
                                            }

                                            return true
                                        } else {
                                            return false
                                        }
                                    }
                                }
                                if (showResourceFilterWarning) {
                                    warningStrings.add(message(code: 'resource.query_filter_warning'))
                                }

                                if (!resource.active) {
                                    warningStrings.add(message(code: 'query.resource.inactive'))
                                }

                                if (resource.resourceQualityWarning && resource.resourceQualityWarningText) {
                                    warningStrings.add(resource.resourceQualityWarningText)
                                } else if (resource.showWarning && resource.warningText) {
                                    // warningText is to be deprecated, resourceQualityWarningText should be used instead
                                    warningStrings.add(resource.warningText)
                                }

                                if (resource.isExpiredDataPoint) {
                                    expiredResource.put(message(code: 'data_point.expired'), message(code: 'expired_icon_detail'))
                                }

                            } else if ('gwpBefore' == customAttribute) {

                                Map<String, Double> gwpImpacts = resource.getResolvedGWPImpacts()
                                if (gwpImpacts) {
                                    String localCompensationCountry = datasetService.getDefaultLocalCompensationCountry(parent)
                                    row.heading += localCompensationCountry && !resource.areas?.contains(localCompensationCountry) ? " ${message(code: 'before_local_compensation')}" : ''
                                    gwpImpacts.each { unit, value ->
                                        if (value) {
                                            int roundingNumber = dataCardService.getRoundingNumber(value)
                                            customContent += "${value.round(roundingNumber)} kg CO2e / ${unit ?: ''}" + System.lineSeparator()
                                        }
                                    }
                                }

                            } else if ('performanceGroup' == customAttribute) {

                                if (subType && showPerformanceRanking) {
                                    customContent = resourceTypeService.getLocalizedName(subType)
                                }

                            } else if ('performanceRanking' == customAttribute) {

                                if (!row.authorizedToDisplay) {
                                    String enterpriseFeatureName = featureService.getFeatureByFeatureId('betaFeatures')?.formattedName ?: ''
                                    customContent = message(code: 'enterprise_feature_warning', args: [enterpriseFeatureName])
                                } else if (showPerformanceRanking) {
                                    Benchmark benchmark = resource.benchmark
                                    Map<String, Integer> validValues = subType?.benchmarkResult?.validValues
                                    row.help = "${message(code: 'resource.ranking_help_1')} <b>${resourceTypeService.getLocalizedName(subType)?.toLowerCase()}</b>. ${message(code: 'resource.ranking_help_2')}"
                                    indicator?.connectedBenchmarks?.each { String connectedBenchmark ->
                                        if (connectedBenchmark) {
                                            Integer quintile = benchmark?.quintiles?.get(connectedBenchmark) ?: benchmark?.co2Quintile
                                            String performanceCloud = ''

                                            if (quintile > 0) {
                                                switch (quintile) {
                                                    case 5:
                                                        performanceCloud = "deepGreenEmissions"
                                                        break
                                                    case 4:
                                                        performanceCloud = "greenEmissions"
                                                        break
                                                    case 3:
                                                        performanceCloud = "yellowEmissions"
                                                        break
                                                    case 2:
                                                        performanceCloud = "orangeEmissions"
                                                        break
                                                    case 1:
                                                        performanceCloud = "redEmissions"
                                                        break
                                                }
                                            }

                                            String performanceRanking

                                            if (benchmark?.values?.get(connectedBenchmark) && "NOT_CALCULABLE" != benchmark?.values?.get(connectedBenchmark)) {
                                                Integer ranking = benchmark?.ranking?.get(connectedBenchmark)
                                                String validValuePerDataProperty = validValues?.get(connectedBenchmark)

                                                if (ranking && validValuePerDataProperty) {
                                                    performanceRanking = "${ranking} / ${validValuePerDataProperty}"
                                                }
                                            }

                                            if (performanceRanking) {
                                                Map<String, Object> performanceOutput
                                                String popupLinkUrl = createLink(controller: "resourceType", action: "benchmarkGraph",
                                                        params: [resourceSubTypeId: subType?.id, resourceId: resource.resourceId, profileId: resource.profileId,
                                                                 benchmarkToShow  : connectedBenchmark, entityId: entityId, indicatorId: indicatorId,
                                                                 stateIdOfProject : parent?.getStateResource()?.resourceId])
                                                Boolean isIniesResource = resource.dataProperties?.contains(Constants.INIES)
                                                performanceOutput = [benchmark         : Constants.BENCHMARKS?.get(connectedBenchmark),
                                                                     performanceRanking: !isIniesResource ? performanceRanking : "",
                                                                     performanceCloud  : !isIniesResource ? performanceCloud : "",
                                                                     popupLink         : popupLinkUrl,
                                                                     popupMessage      : message(code: 'resource.see_full_ranking'),
                                                                     manuallyVerified  : userService.getSuperUser(user) && resource.dataManuallyVerified ? Boolean.TRUE : Boolean.FALSE
                                                ]
                                                performanceList.add(performanceOutput)
                                            }
                                        }
                                    }

                                    if (resource.dataManuallyVerified) {
                                        customContent += "${message(code: 'datapoint_impact_unusual')}."
                                    }
                                } else if (resource.excludeFromBenchmark) {
                                    customContent += "${message(code: 'datapoint_excluded')}"
                                } else if (resource.dataManuallyVerified) {
                                    customContent += "${message(code: 'datapoint_impact_unusual')}"
                                }

                            } else if ('availableUnits' == customAttribute) {

                                if (resource.combinedUnits) {
                                    customContent = resource.combinedUnits.join(', ')
                                } else if (resource.isDummy && dummyDataset?.userGivenUnit) {
                                    customContent = dummyDataset.userGivenUnit
                                }

                            } else if ('transportationDistance' == customAttribute) {

                                customContent = parent?.getDefaultTransportFromSubType(resource)?.toString() ?: ''

                            } else if ('transportationMethod' == customAttribute) {

                                List<Resource> transportResources = resourceService.getTransportResourcesFromSubTypeBasedOnUserUnitSystem(resource) as List<Resource>
                                transportResources?.each { Resource transportResource ->
                                    String name = optimiResourceService.getLocalizedName(transportResource)
                                    customContent += name ? name + ':' : ''
                                    Map<String, Double> gwpImpacts = transportResource?.getResolvedGWPImpacts()
                                    gwpImpacts?.each { unit, value ->
                                        if (value) {
                                            int roundingNumber = dataCardService.getRoundingNumber(value)
                                            customContent += "${value.round(roundingNumber)} kg CO2e / ${unit ?: ''}" + System.lineSeparator()
                                        }
                                    }
                                }

                            } else if ('serviceLifeProjectBased' == customAttribute) {

                                Integer serviceLife = parent ? resourceService.getServiceLifeDefault(parent, resource) : null
                                if (serviceLife == -1) {
                                    customContent = message('code': 'resource.serviceLife.as_building')
                                } else if (serviceLife != null) {
                                    customContent = serviceLife.toString()
                                }

                            } else if ('serviceLifeResource' == customAttribute) {

                                Double serviceLife = resource.serviceLife
                                if (serviceLife == -1) {
                                    customContent = message('code': 'resource.serviceLife.as_building')
                                } else if (serviceLife != null) {
                                    customContent = serviceLife.toString()
                                }

                            } else if ('wasteTreatmentScenario' == customAttribute) {

                                // To understand this, check ticket 15399
                                if (eolCalculationMethod == '20' || useEpdScenarioButNotAvailable) {
                                    customContent = eolProcess ? eolProcessService.getLocalizedName(eolProcess) : ''
                                } else if (useEpdScenario) {
                                    customContent = message('code': 'epdScenario')
                                } else {
                                    row.authorizedToDisplay = false
                                }

                            } else if ('wasteTreatmentMethod' == customAttribute) {
                                //TODO - CD3D Beta - Convert this to a map or object, which contains each rows data
                                // To understand this, check ticket 15399 && 15409
                                if (eolProcess && (eolCalculationMethod == '20' || useEpdScenarioButNotAvailable)) {
                                    String c2 = eolProcess.c2ResourceId ?: ''
                                    String c3 = eolProcess.c3ResourceId ?: ''
                                    String c4 = eolProcess.c4ResourceId ?: ''
                                    String d = eolProcess.dResourceId ?: ''
                                    String cellContent = ''

                                    ['C2': c2, 'C3': c3, 'C4': c4, 'D': d].each { String name, String stageResourceId ->
                                        if (stageResourceId) {
                                            Resource stageResource = optimiResourceService.getResourceWithGorm(stageResourceId)
                                            Map<String, Double> gwpImpacts = stageResource?.getResolvedGWPImpacts() ?: [:]
                                            if (gwpImpacts && stageResource) {
                                                String localName = optimiResourceService.getLocalizedName(stageResource)
                                                // each stage is a mini row with 2 columns
                                                cellContent += "<tr class='noBorder'>"
                                                cellContent += "<td class='noBorder twoPadding alignBaseline'><b>${name}:</b></td>"
                                                cellContent += "<td class='noBorder twoPadding'>"
                                                cellContent += "${localName}: "
                                                gwpImpacts.each { unit, value ->
                                                    if (value) {
                                                        int roundingNumber = dataCardService.getRoundingNumber(value)
                                                        cellContent += "${(value as Double).round(roundingNumber)} kg CO2e / ${unit ?: ''}"
                                                    }
                                                }
                                                cellContent += '</td>'
                                                cellContent += '</tr>'
                                            }
                                        }
                                    }
                                    customContent += cellContent ? '<table><tbody>' + cellContent + '</tbody></table>' : ''
                                } else {
                                    row.authorizedToDisplay = false
                                }

                            } else if ('privateDataset' == customAttribute) {

                                if (resource.privateDataset && resource.privateDatasetAccountId) {
                                    customContent = "${resource.privateDatasetAccountName ?: accountService.getAccount(resource.privateDatasetAccountId)?.companyName}"

                                    if (resource.firstUploadTime) {
                                        customContent += " - ${resource.firstUploadTime.format("dd.MM.yyyy")}"

                                        if (resource.privateDatasetImportedByUserId) {
                                            User uploader = userService.getUserById(DomainObjectUtil.stringToObjectId(resource.privateDatasetImportedByUserId))

                                            if (uploader) {
                                                customContent += " ${maskingService.maskEmail(uploader.username)}"
                                            }
                                        }
                                    }
                                }

                            } else if ('specificCharacteristics' == customAttribute) {

                                resource.specificCharacteristics?.each {
                                    customContent += "${message(code: it)}"
                                }

                            } else if ('otherImpactCategories' == customAttribute) {

                                List<CalculationRule> calculationRules = indicator?.getResolveCalculationRules(parent)
                                List<CalculationRule> impactCatzToShowInDataCard = calculationRules?.findAll { it.mapToImpactCategory }

                                impactCatzToShowInDataCard?.forEach({
                                    if (it) {
                                        Double impactValues = (Double) DomainObjectUtil.callGetterByAttributeName(it.mapToImpactCategory, resource)
                                        if (impactValues) {
                                            int impactRoundingNumber = 2
                                            if (impactValues < 0.0001) {
                                                DecimalFormat format = new DecimalFormat("0.00E0")
                                                impactValues = Double.parseDouble(format.format(impactValues))
                                            } else if (impactValues < 0.1 && impactValues >= 0.0001) {
                                                impactRoundingNumber = 4
                                                impactValues = impactValues.round(impactRoundingNumber)
                                            } else {
                                                impactValues = impactValues.round(impactRoundingNumber)
                                            }
                                            String impactHelp = it.getLocalizedHelp() ?: ''
                                            String qMark = impactHelp ? "<span class=\"triggerPopover qMarkInRowDataCard\" data-content=\"${impactHelp}\"><i class=\"icon-question-sign\"></i></span>" : ''
                                            customContent += "<div class='hasQMarkInRowDataCard'><b>${qMark} ${it.getLocalizedName() ?: ''}:</b> ${impactValues} ${it.getLocalizedUnit() ?: ''} / ${resource.unitForData ?: ''}</div>"
                                            Map<String, Object> impactCategory = [:]
                                            impactCategory = [impactHelp: impactHelp, impactName: it.getLocalizedName(),
                                                              impactValue: impactValues, impactUnit: it.getLocalizedUnit(),
                                                              unitForData: resource.unitForData]
                                            otherImpactCategories.add(impactCategory)
                                        }
                                    }
                                })

                            } else if ('resistanceProperties' == customAttribute) {
                                //TODO - CD3D Beta - Convert this to a list of objects, for each saved content
                                resource.resistanceProperties?.eachWithIndex { it, index ->
                                    if (it != null && it != 'null') {
                                        String propertyPath = configurationService.getConfigurationValue(com.bionova.optimi.construction.Constants.APPLICATION_ID, "resistancePropertiesPath")
                                        File testPath = propertyPath ? new File("/var/www/${propertyPath}${it}.png") : null
                                        if (testPath?.exists()) {
                                            customContent += "${index > 0 ? ', ' : ''} <img src=\"${propertyPath}${it}.png\" class=\"miniFlag\"></img> ${it}"
                                        } else {
                                            String assetPath = "/resistanceProperties/${it}.png"
                                            if (asset?.assetPathExists(src: assetPath)) {
                                                customContent += "${index > 0 ? ', ' : ''} <img src=\"/app/assets/${assetPath}\" class=\"miniFlag\"></img> ${it}"
                                            } else {
                                                customContent += "${index > 0 ? ', ' : ''} ${it.toString()}"
                                            }
                                        }
                                    }
                                }

                            } else if ('leedEpdImpactReduction' == customAttribute) {

                                Benchmark benchmark = resource.benchmark
                                indicator?.connectedBenchmarks?.each { String connectedBenchmark ->
                                    if (connectedBenchmark == "leed_epd_cml" && benchmark.values && benchmark.values.get("leed_epd_cml") && benchmark.values.get("leed_epd_cml") != "NOT_CALCULABLE") {
                                        customContent += "${benchmark.values.get("leed_epd_cml")} ${Integer.valueOf(benchmark.values.get("leed_epd_cml")) >= 3 ? " - ${message(code: 'resource.qualifies')}" : "(${message(code: 'resource.not_qualified')})"}"
                                    } else if (connectedBenchmark == "leed_epd_traci" && benchmark.values && benchmark.values.get("leed_epd_traci") && benchmark.values.get("leed_epd_cml") != "NOT_CALCULABLE") {
                                        customContent += "${benchmark.values.get("leed_epd_traci")} ${Integer.valueOf(benchmark.values.get("leed_epd_traci")) >= 3 ? " - ${message(code: 'resource.qualifies')}" : "(${message(code: 'resource.not_qualified')})"}"
                                    }
                                }

                            } else if ('biogenicCarbonStorage' == customAttribute) {

                                if (resource.biogenicCarbonStorage_kgCO2e) {
                                    int roundingNumber = dataCardService.getRoundingNumber(resource.biogenicCarbonStorage_kgCO2e)
                                    if (("buildingOperatingEnergyAndWater").equalsIgnoreCase(queryId)) {
                                        row.heading = message(code: 'resource.biogenicCarbonContent_kgCO2e') as String
                                        customContent += "${resource.biogenicCarbonStorage_kgCO2e.round(roundingNumber)} kg CO2e / ${resource.unitForData ?: ''}"
                                    } else {
                                        customContent += "${resource.biogenicCarbonStorage_kgCO2e.round(roundingNumber)} kg CO2e / ${resource.unitForData ?: ''}"
                                    }
                                }
                                if (resource.biogenicCarbonStorage_kgCO2e) {
                                    row.help += "${message(code: 'resource.biogenicCarbonStorage_kgCO2e.value_default')}"
                                }
                                if (resource.biogenicCarbonSeparated) {
                                    customContent += "&#10; ${message(code: 'resource.biogenicCarbonStorage_kgCO2e.info')}"
                                }
                                if (resource.biogenicCarbonEstimated) {
                                    customContent += "&#10; ${message(code: 'resource.biogenicCarbonStorage_kgCO2e.estimated')} "
                                }

                            } else if ('datasetType' == customAttribute) {

                                customContent = resource.datasetType ? message(code: resource.datasetType) : ''

                            } else if ('marketDataset' == customAttribute) {

                                customContent = resource.marketDataset ? message(code: 'yes') : message(code: 'no')

                            } else if ('totalWasteAndMaterialOutputs_kg' == customAttribute) {

                                customContent = resource.totalWasteAndMaterialOutputs_kg ? stringUtilsService.roundUpNumbersAsString(resource.totalWasteAndMaterialOutputs_kg.toString(), 3) + ' kg' : ''

                            } else if ('allEnergyUsedTotal_kWh_SUM' == customAttribute) {

                                customContent = resource.allEnergyUsedTotal_kWh_SUM ? stringUtilsService.roundUpNumbersAsString(resource.allEnergyUsedTotal_kWh_SUM.toString(), 3) + ' kWh' : ''

                            } else if ('qMetadata' == customAttribute) {

                                def q = resource.getQMetadata()
                                if (q) {
                                    q = (q * 100).round(2).toString()
                                    customContent = message(code: "qMetadata", args: [q])
                                }

                            } else if ('density' == customAttribute) {

                                customContent = resource.density ? stringUtilsService.roundUpNumbersAsString(resource.density.toString(), 2) + ' kg/m3' : ''

                            } else if ('verificationStatus' == customAttribute) {

                                if (resource.verificationStatus && Constants?.ALLOWED_RESOURCE_VERIFICATION_STATUS?.keySet()?.contains(resource.verificationStatus)) {
                                    customContent = message(code: "resource.verificationStatus.${resource.verificationStatus}") as String
                                    row.help = message(code: "resource.verificationStatus.${resource.verificationStatus}.info") as String
                                }

                            } else if (customAttribute == com.bionova.optimi.construction.Constants.SITE_WASTAGE_ATTR_ID) {
                                customContent = subType && (subType.siteWastage != null) ? stringUtilsService.roundUpNumbersAsString(subType.siteWastage.toString(), 1) + ' %' : ''
                            } else if (customAttribute == com.bionova.optimi.construction.Constants.SITE_WASTAGE_BOVERKET_ATTR_ID) {
                                Double siteWastageBoverket = resource.siteWastageBoverket != null ? resource.siteWastageBoverket : subType?.siteWastageBoverket

                                if (siteWastageBoverket != null) {
                                    customContent = stringUtilsService.roundUpNumbersAsString(siteWastageBoverket.toString(), 1) + ' %'
                                }
                            } else if ('massConversionFactor' == customAttribute) {

                                if (resource.massConversionFactor && resource.unitForData) {
                                    if ( resource.unitForData == 'kg' && resource.massConversionFactor == 1) {
                                        row.authorizedToDisplay = false
                                    } else {
                                        customContent = stringUtilsService.roundUpNumbersAsString(resource.massConversionFactor.toString(), 3) + ' kg/' + resource.unitForData
                                    }
                                }

                            } else if ('thermalLambda' == customAttribute) {

                                customContent = resource.thermalLambda ? stringUtilsService.roundUpNumbersAsString(resource.thermalLambda.toString(), 3) + ' W/m.K' : ''

                            } else if ('defaultThickness_mm' == customAttribute) {

                                customContent = resource.defaultThickness_mm ? stringUtilsService.roundUpNumbersAsString(resource.defaultThickness_mm.toString(), 3) + ' mm' : ''

                            } else if (customAttribute == com.bionova.optimi.construction.Constants.SITE_WASTAGE_REFERENCE_ATTR_ID) {
                                customContent = resource.siteWastage == null ? subType?.siteWastageReference :
                                        message(code: "dataCard.attribute.siteWastageReference.message", default: "Site wastage comes from the datapoint")
                            } else if (customAttribute == com.bionova.optimi.construction.Constants.SITE_WASTAGE_REFERENCE_BOVERKET_ATTR_ID) {
                                customContent = resource.siteWastageBoverket == null ? subType?.siteWastageBoverketReference :
                                        message(code: "dataCard.attribute.siteWastageReference.message", default: "Site wastage comes from the datapoint")
                            } else if (customAttribute == com.bionova.optimi.construction.Constants.DEFAULT_TRANSPORT_BOVERKET_REFERENCE_ATTR_ID) {
                                customContent = resource.defaultTransportBoverket == null ? subType?.defaultTransportBoverketReference :
                                        message(code: "dataCard.attribute.defaultTransportBoverketReference.message", default: "Transportation scenario comes from the datapoint")
                            } else if (customAttribute == com.bionova.optimi.construction.Constants.TRANSPORTATION_DISTANCE_REFERENCE_ATTR_ID) {
                                customContent = transportationDefault ?
                                        DomainObjectUtil.callGetterByAttributeName(transportationDefault + com.bionova.optimi.construction.Constants.REFERENCE, subType) : StringUtils.EMPTY
                            } else if (customAttribute == com.bionova.optimi.construction.Constants.TRANSPORTATION_DISTANCELEG2_ATTR_ID) {
                                customContent = (transportationDefault == com.bionova.optimi.construction.Constants.DEFAULT_TRANSPORT_RICS) ? subType?.defaultTransportRICSleg2?.toString() : StringUtils.EMPTY
                            } else if (customAttribute == com.bionova.optimi.construction.Constants.TRANSPORTATION_METHOD_LEG2_ATTR_ID) {
                                if (transportationDefault == com.bionova.optimi.construction.Constants.DEFAULT_TRANSPORT_RICS) {
                                    String transportResourceId = subType?.transportResourceIdRICSleg2

                                    if (transportResourceId) {
                                        Resource transportResource = optimiResourceService.getResourceByResourceAndProfileId(transportResourceId, null)
                                        customContent = transportResource ? localizationService.getLocalizedProperty(transportResource, "name", "resourceId", transportResourceId) : transportResourceId
                                    } else {
                                        customContent = StringUtils.EMPTY
                                    }
                                } else {
                                    customContent = StringUtils.EMPTY
                                }
                            } else if (customAttribute == com.bionova.optimi.construction.Constants.SERVICE_LIFE_REFERENCE_ATTR_ID) {
                                String serviceLifeDefault = parent?.defaults?.get(com.bionova.optimi.construction.Constants.DEFAULT_SERVICELIFE)

                                if (serviceLifeDefault in [com.bionova.optimi.construction.Constants.SERVICE_LIFE_COMMERCIAL, com.bionova.optimi.construction.Constants.SERVICE_LIFE_TECHNICAL, com.bionova.optimi.construction.Constants.SERVICE_LIFE_RICS]) {
                                    customContent = DomainObjectUtil.callGetterByAttributeName(serviceLifeDefault + com.bionova.optimi.construction.Constants.REFERENCE, subType) ?: StringUtils.EMPTY
                                }
                            } else if (customAttribute in ATTR_IDS_FOR_DOUBLE_CHECKING) {
                                customContent = DomainObjectUtil.callGetterByAttributeName(customAttribute, resource)?.toString() ?:
                                        DomainObjectUtil.callGetterByAttributeName(customAttribute, subType)?.toString()
                            } else if (customAttribute in TRANSPORT_RESOURCE_ID_BOVERKET_ATTR_IDS) {
                                String transportResourceId = DomainObjectUtil.callGetterByAttributeName(customAttribute, resource)?.toString() ?:
                                        DomainObjectUtil.callGetterByAttributeName(customAttribute, subType)?.toString()

                                if (transportResourceId) {
                                    Resource transportResource = optimiResourceService.getResourceByResourceAndProfileId(transportResourceId, null)
                                    customContent = transportResource ? localizationService.getLocalizedProperty(transportResource, "name", "resourceId", transportResourceId) : transportResourceId
                                } else {
                                    customContent = StringUtils.EMPTY
                                }
                            } else {
                                customContent = "Unrecognized custom attribute (${customAttribute}). Contact developer"
                            }
                            row.content = customContent
                        }
                    }
                    section.isEmpty = !section.resourceAttributes.find { DataCardRow row -> row.content }
                }
            }
            dataCardMap = [sections: sections, brandImage: b64BrandImage, resourceFullName: resourceFullName, flagMap: flagMap,
                           b64ImageEPD: b64ImageEPD, imgLink: imgLink, imgCopyRightText: imgCopyRightText, performanceRanking: performanceList,
                           otherImpactCategories: otherImpactCategories, warningStrings: warningStrings, expiredResource: expiredResource,
                           displayDownloadText: displayDownloadText, epdDownloadLink: epdDownloadLink, imageDescription: imageDescription,
                           epdDownloadLicensed: epdDownloadLicensed]
            return dataCardMap
        }

    }
}
