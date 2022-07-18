package com.bionova.optimi.core.service

import com.bionova.optimi.construction.Constants
import com.bionova.optimi.core.domain.mongo.Construction
import com.bionova.optimi.core.domain.mongo.ConstructionGroup
import com.bionova.optimi.core.domain.mongo.Nmd3ElementOnderdelen
import com.bionova.optimi.core.domain.mongo.Nmd3NLsfBRAWElementen
import com.bionova.optimi.core.domain.mongo.Nmd3ProductenBijElement
import com.bionova.optimi.core.domain.mongo.Nmd3ProductenProfielWaarden
import com.bionova.optimi.core.domain.mongo.Nmd3Profiel
import com.bionova.optimi.core.domain.mongo.Nmd3ProfielMilieuEffecten
import com.bionova.optimi.core.domain.mongo.Nmd3ProfielsetsEnSchalingBijProduct2
import com.bionova.optimi.core.domain.mongo.Nmd3VolledigProductBijProduct
import com.bionova.optimi.core.domain.mongo.NmdUpdate
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.QuestionAnswerChoice
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.core.util.LoggerUtil
import com.bionova.optimi.json.nmd.NmdDeactivateResources
import com.bionova.optimi.json.nmd.NmdNewResources
import com.bionova.optimi.json.nmd.NmdUpdateResponse
import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse
import groovy.json.JsonSlurper
import groovy.time.TimeCategory
import groovy.time.TimeDuration
import org.apache.commons.lang.time.DateUtils
import org.bson.Document
import org.grails.datastore.mapping.model.PersistentProperty
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject

import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.text.SimpleDateFormat
import java.time.Duration

class NmdApiService {

    def constructionService
    def optimiResourceService
    def queryService
    def questionService
    def domainClassService
    def nmdElementService
    def nmdResourceService
    def configurationService
    LoggerUtil loggerUtil
    FlashService flashService


    public static final String NMD_SUCCESS_RESPONSE = "succes"
    public static final String NMD_ACCESS_TOKEN_EXPIRED = "Access_TokenExpired"
    public static final String NMD_NO_DATA_AVAILABLE_FTS = "noDataAvailableFTS"
    public static final String NMD_FALLBACK_DATE = "22-06-2020"

    // This special access token is only for testing, does not expire
    private static final String specialAccess_token = "NPfmpilm8QkZ+qPlaeoq1qgilgnccmeMUKtcj6YAzhg2mdJpruMQOKxByTfIFxOXI7N7Edbf0wZPlIrMIor9/+MZ2lX/tv/4erDEteb7znYrXoymLVmZxkJGVgZq9UbQ"

    private static final String refreshToken = "AuSxApn9FCWnCawvrkAve9e6jfynVXcmNTpZ2ELh9WRI6oODH3Dz1CSqIq2u0dJI6s+LxLnKm22HNF6VCwr6LEotjaiS/TTdyiblddu5EGBA7n71yXZkOf2bLOIfICZf"

    private static final String API_ID = "1"
    private static final String APIVersion = "1.1"
    private static final String APIVersionUpdate = "1.7"

    private static final String authEndpoint = "https://www.milieudatabase-datainvoer.nl/NMD_30_AuthenticationServer/NMD_30_API_Authentication/getToken"
    private static final String NLsfBRAWElemEndpoint = "https://www.milieudatabase-datainvoer.nl/NMD_30_API_v$APIVersion/api/NMD30_Web_API/NLsfB_RAW_Elementen?ZoekDatum={ZoekDatum}"
    private static final String ElementOnderdelenEndpoint = "https://www.milieudatabase-datainvoer.nl/NMD_30_API_v$APIVersion/api/NMD30_Web_API/ElementOnderdelen?ZoekDatum={ZoekDatum}&ElementId={ElementId}"
    private static final String ProductenBijElementEndpoint = "https://www.milieudatabase-datainvoer.nl/NMD_30_API_v$APIVersion/api/NMD30_Web_API/ProductenBijElement?ZoekDatum={ZoekDatum}&ElementId={ElementId}"
    private static final String ProductenProfielWaardenEndpoint = "https://www.milieudatabase-datainvoer.nl/NMD_30_API_v$APIVersion/api/NMD30_Web_API/ProductenProfielWaarden?ZoekDatum={ZoekDatum}&ProductIDs={ProductIDs}&includeNULLs={includeNULLs}"
    private static final String ProfielsetsEnSchalingBijProduct2Endpoint = "https://www.milieudatabase-datainvoer.nl/NMD_30_API_v$APIVersion/api/NMD30_Web_API/ProfielsetsEnSchalingBijProduct2?ZoekDatum={ZoekDatum}&ProductID={ProductID}&includeNULLs=true"
    private static final String VolledigProductBijProductIDEndpoint = "https://www.milieudatabase-datainvoer.nl/NMD_30_API_v$APIVersion/api/NMD30_Web_API/VolledigProductBijProductID?ZoekDatum={ZoekDatum}&ProductID={ProductID}"
    private static final String NMD3GroupName = "Netherlands NMD 3.0 API"
    private static final String NMD3GroupId = "nmd-api-30"

    public static final String ComputeMKI_endPoint = "https://www.milieudatabase-datainvoer.nl/NMD_30_API_v$APIVersion/api/NMD30_Web_API/ComputeMKI"
    public static final String DailyUpdate_endPoint = "https://www.Milieudatabase-datainvoer.nl/NMD_30_API_Production_v$APIVersionUpdate/api/NMD30_Web_API/getUpdatesByDay_2?ZoekDatum={ZoekDatum}&includeNULLs=true"
    public static final String DailyUpdate_endPoint_generic = "https://www.Milieudatabase-datainvoer.nl/NMD_30_API_Production_v$APIVersionUpdate/api/NMD30_Web_API/getUpdatesByDay_4?"

    private String NMD3CurrentValidToken = "" // NMD3 API Can only have one valid token at a time for the refreshToken?


    public String getAccessToken(Boolean getNewToken = Boolean.FALSE) {
        String token = NMD3CurrentValidToken

        if (!token||getNewToken) {

     /* Comment out for now, apply back when testing done
       RestBuilder rest = new RestBuilder()
            MultiValueMap<String, String> grant = new LinkedMultiValueMap<String, String>()
            grant.add("grant_type", "client_credentials")
            RestResponse authResponse = rest.post(authEndpoint) {
                header("refreshToken", refreshToken)
                header("API_ID", API_ID)
                header("Content-Type", "application/x-www-form-urlencoded")
                body(grant)
            }
            token = authResponse?.json?.get("TOKEN")
            NMD3CurrentValidToken = token
*/
            Map<String, String> requestBodyMap = new HashMap<String, String>()
            String authEndpointConfig = configurationService.getConfigurationValue(Constants.ConfigName.NMD_API_AUTH_ENDPOINT.toString())
            String refreshTokenConfig = configurationService.getConfigurationValue(Constants.ConfigName.NMD_REFRESH_TOKEN.toString())
            String apiIdConfig = configurationService.getConfigurationValue(Constants.ConfigName.NMD_API_ID.toString())
            HttpClient client = HttpClient.newHttpClient()
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(authEndpointConfig ?: authEndpoint))
                    .headers("refreshToken", refreshTokenConfig ?: refreshToken, "API_ID", apiIdConfig ?: API_ID, "Content-Type", "application/x-www-form-urlencoded")
                    .timeout(Duration.ofMinutes(1))
                    .POST(HttpRequest.BodyPublishers.ofString("grant_type=client_credentials"))
                    .build()

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString())
            String responseBody = response?.body()
            if (200 == response.statusCode() && responseBody) {
                JSONObject jsonObject = new JSONObject(responseBody)
                token = jsonObject.get("TOKEN")
                NMD3CurrentValidToken = token
            }

        }
        return token
    }
    // TODO: deprecate?
    // use callApi2 instead
    @Deprecated
    public RestResponse callApi(String token, String url, Map<String, Object> variables) {
        RestBuilder rest = new RestBuilder()
        if (!url) {
            url = DailyUpdate_endPoint
        }
        return rest.get(url) {
            if (variables) {
                urlVariables(variables)
            }
            header("Access_Token", token)
        }
    }

    HttpResponse callApi2(String token, Map<String, Object> params) {
        String endpointFromSystemConfig = configurationService.getConfigurationValue(Constants.ConfigName.NMD_API_ENDPOINT.toString())
        String url = endpointFromSystemConfig?.trim() ?: DailyUpdate_endPoint_generic
        if (params) {
            params?.each { key, value ->
                url = "${url}${key}=${value}&"
            }
        }
        HttpClient client = HttpClient.newHttpClient()
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).headers("Access_Token", token).timeout(Duration.ofMinutes(30)).build()
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString())
        return response
    }

    @Deprecated
    def getVolledigProductBijProduct(Integer productId, String zoekDatum = null, String accessToken = null) {
        String token = getAccessToken()
        List<Nmd3VolledigProductBijProduct> nmd3volledigProductBijProduct = []
        String date = zoekDatum ?: new SimpleDateFormat("yyyyMMdd").format(new Date())
        RestResponse elemResponse = callApi(token, VolledigProductBijProductIDEndpoint, [ZoekDatum: date, ProductID: productId])

        if (NMD_ACCESS_TOKEN_EXPIRED.equals(elemResponse?.json?.get("Message"))) {
            token = getAccessToken(Boolean.TRUE)
            elemResponse = callApi(token, VolledigProductBijProductIDEndpoint, [ZoekDatum: date, ProductID: productId])
        }

        if (200 == elemResponse.status && elemResponse.json?.get("results") != "na") {
            JSONArray elemResults = elemResponse.json?.get("results")

            elemResults.each { elemResult ->
                Nmd3VolledigProductBijProduct volledigProductBijProduct = elemResult as Nmd3VolledigProductBijProduct

                if (volledigProductBijProduct) {
                    nmd3volledigProductBijProduct.add(volledigProductBijProduct)
                }
            }
        } else if (500 == elemResponse.status) {
            nmd3volledigProductBijProduct = null
        }
        return nmd3volledigProductBijProduct
    }

    @Deprecated
    def getProfielsetsEnSchalingBijProduct2(Integer productId, String zoekDatum = null, String accessToken = null) {
        String token = getAccessToken()
        List<Nmd3ProfielsetsEnSchalingBijProduct2> nmd3ProfielsetsEnSchalingBijProduct2 = []
        String date = zoekDatum ?: new SimpleDateFormat("yyyyMMdd").format(new Date())
        RestResponse elemResponse = callApi(token, ProfielsetsEnSchalingBijProduct2Endpoint, [ZoekDatum: date, ProductID: productId])

        if (NMD_ACCESS_TOKEN_EXPIRED.equals(elemResponse?.json?.get("Message"))) {
            token = getAccessToken(Boolean.TRUE)
            elemResponse = callApi(token, ProfielsetsEnSchalingBijProduct2Endpoint, [ZoekDatum: date, ProductID: productId])
        }

        if (200 == elemResponse.status && elemResponse.json?.get("results") != "na") {
            JSONArray elemResults = elemResponse.json?.get("results")

            elemResults.each { elemResult ->
                Nmd3ProfielsetsEnSchalingBijProduct2 profielsetsEnSchalingBijProduct2 = elemResult as Nmd3ProfielsetsEnSchalingBijProduct2

                if (profielsetsEnSchalingBijProduct2) {
                    nmd3ProfielsetsEnSchalingBijProduct2.add(profielsetsEnSchalingBijProduct2)
                }
            }
        } else if (500 == elemResponse.status) {
            nmd3ProfielsetsEnSchalingBijProduct2 = null
        }
        return nmd3ProfielsetsEnSchalingBijProduct2
    }

    @Deprecated
    def getNLsfBRAWElementens(String zoekDatum = null, String accessToken = null) {
        String token = getAccessToken()
        List<Nmd3NLsfBRAWElementen> nmd3NLsfBRAWElements = []
        String date = zoekDatum ?: new SimpleDateFormat("yyyyMMdd").format(new Date())
        RestResponse elemResponse = callApi(token, NLsfBRAWElemEndpoint, [ZoekDatum: date])

        if (NMD_ACCESS_TOKEN_EXPIRED.equals(elemResponse?.json?.get("Message"))) {
            token = getAccessToken(Boolean.TRUE)
            elemResponse = callApi(token, NLsfBRAWElemEndpoint, [ZoekDatum: date])
        }

        if (200 == elemResponse.status && elemResponse.json?.get("results") != "na") {
            JSONArray elemResults = elemResponse.json?.get("results")

            elemResults.each { elemResult ->
                Nmd3NLsfBRAWElementen nmd3NLsfBRAWElementen = elemResult as Nmd3NLsfBRAWElementen

                if (nmd3NLsfBRAWElementen) {
                    nmd3NLsfBRAWElements.add(nmd3NLsfBRAWElementen)
                }
            }
        } else if (500 == elemResponse.status) {
            nmd3NLsfBRAWElements = null
        }
        return nmd3NLsfBRAWElements
    }

    @Deprecated
    def getProductenProfielWaardens(Integer productId, String zoekDatum = null, List<Integer> intProductIds = null) {

        String productIds = intProductIds ? intProductIds.toString()?.replace("[", "")?.replace("]", "")?.replaceAll("\\s","") : productId?.toString()

        String token = getAccessToken()
        List<Nmd3ProductenProfielWaarden> productenProfielWaardens = []
        String date = zoekDatum ?: new SimpleDateFormat("yyyyMMdd").format(new Date())
        RestResponse elemResponse = callApi(token, ProductenProfielWaardenEndpoint, [ZoekDatum: date, ProductIDs: productIds, includeNULLs: true])

        if (NMD_ACCESS_TOKEN_EXPIRED.equals(elemResponse?.json?.get("Message"))) {
            token = getAccessToken(Boolean.TRUE)
            elemResponse = callApi(token, ProductenProfielWaardenEndpoint, [ZoekDatum: date, ProductIDs: productIds, includeNULLs: true])
        }

        if (200 == elemResponse.status && elemResponse.json?.get("results") != "na") {
            JSONArray elemResults = elemResponse.json?.get("results")

            elemResults.each { elemResult ->
                Nmd3ProductenProfielWaarden nmd3ProductenProfielWaarden = elemResult as Nmd3ProductenProfielWaarden

                if (nmd3ProductenProfielWaarden) {
                    productenProfielWaardens.add(nmd3ProductenProfielWaarden)
                }
            }
        } else if (500 == elemResponse.status) {
            productenProfielWaardens = null
        }
        return productenProfielWaardens
    }

    @Deprecated
    def getProductenBijElements(Integer elementId, String zoekDatum = null, String accessToken = null) {
        String token = getAccessToken()
        List<Nmd3ProductenBijElement> productenBijElements = []
        String date = zoekDatum ?: new SimpleDateFormat("yyyyMMdd").format(new Date())
        RestResponse elemResponse = callApi(token, ProductenBijElementEndpoint, [ZoekDatum: date, ElementId: elementId])

        if (NMD_ACCESS_TOKEN_EXPIRED.equals(elemResponse?.json?.get("Message"))) {
            token = getAccessToken(Boolean.TRUE)
            elemResponse = callApi(token, ProductenBijElementEndpoint, [ZoekDatum: date, ElementId: elementId])
        }

        if (200 == elemResponse.status && elemResponse.json?.get("results") != "na") {
            JSONArray elemResults = elemResponse.json?.get("results")

            elemResults.each { elemResult ->
                Nmd3ProductenBijElement nmd3ProductenBijElement = elemResult as Nmd3ProductenBijElement

                if (nmd3ProductenBijElement) {
                    productenBijElements.add(nmd3ProductenBijElement)
                }
            }
        } else if (500 == elemResponse.status) {
            productenBijElements = null
        }
        return productenBijElements
    }

    @Deprecated
    def getElementOnderdelens(Integer elementId, String zoekDatum = null, String accessToken = null) {
        String token = getAccessToken()
        List<Nmd3ElementOnderdelen> elementOnderdelens = []
        String date = zoekDatum ?: new SimpleDateFormat("yyyyMMdd").format(new Date())
        RestResponse elemResponse = callApi(token, ElementOnderdelenEndpoint, [ZoekDatum: date, ElementId: elementId])

        if (NMD_ACCESS_TOKEN_EXPIRED.equals(elemResponse?.json?.get("Message"))) {
            token = getAccessToken(Boolean.TRUE)
            elemResponse = callApi(token, ElementOnderdelenEndpoint, [ZoekDatum: date, ElementId: elementId])
        }

        if (200 == elemResponse.status && elemResponse.json?.get("results") != "na") {
            JSONArray elemResults = elemResponse.json?.get("results")

            elemResults.each { elemResult ->
                Nmd3ElementOnderdelen elementOnderdelen = elemResult as Nmd3ElementOnderdelen

                if (elementOnderdelen) {
                    elementOnderdelens.add(elementOnderdelen)
                }
            }
        } else if (500 == elemResponse.status) {
            elementOnderdelens = null
        }
        return elementOnderdelens
    }

    @Deprecated
    def getFaseText(Integer FaseID) {
        String fase

        if (FaseID) {
            switch (FaseID) {
                case 1:
                    fase = "A1-A3"
                    break
                case 2:
                    fase = "A4"
                    break
                case 3:
                    fase = "A5"
                    break
                case 4:
                    fase = "B1"
                    break
                case 5:
                    fase = "B2"
                    break
                case 6:
                    fase = "B3"
                    break
                case 7:
                    fase = "B4"
                    break
                case 8:
                    fase = "B5"
                    break
                case 9:
                    fase = "C1"
                    break
                case 10:
                    fase = "C2"
                    break
                case 11:
                    fase = "C3"
                    break
                case 12:
                    fase = "C4"
                    break
                case 13:
                    fase = "D"
                    break
                default:
                    // Fase out of scope
                    break
            }
        }
        return fase

    }

    @Deprecated
    def createNMDTotalResource(Nmd3ProductenBijElement nmd3ProductenBijElement, String profileDate, Nmd3VolledigProductBijProduct nmd3VolledigProductBijProduct = null) {
        Resource resource
        String unit = convertNMDUnitToOCLCA(nmd3VolledigProductBijProduct ? nmd3VolledigProductBijProduct.getFunctioneleEenheidID() : nmd3ProductenBijElement.getFunctioneleEenheidID())
        String resourceId = "NMD3basic${nmd3VolledigProductBijProduct ? nmd3VolledigProductBijProduct.getProductID() : nmd3ProductenBijElement.getProductID()}".toString()
        Resource existingResource = optimiResourceService.getResourceWithGorm(resourceId)
        String name = nmd3VolledigProductBijProduct ? nmd3VolledigProductBijProduct.getProductNaam() : nmd3ProductenBijElement.getProductNaam()

        if (name) {
            resource = new Resource()
            resource.resourceId = resourceId
            resource.profileId = profileDate
            resource.stage = "A1-A3"
            resource.applicationId = "LCA"
            resource.dataProperties = [com.bionova.optimi.core.Constants.NMD_BASIC]
            resource.active = true
            resource.defaultProfile = true
            resource.nameEN = name
            resource.nameNL = name
            resource.manufacturer = ""
            resource.nmdProductId = nmd3VolledigProductBijProduct ? nmd3VolledigProductBijProduct.getProductID() : nmd3ProductenBijElement.getProductID()

            if (unit) {
                resource.unitForData = unit
                resource.combinedUnits = [unit]
            }
            resource.massConversionFactor = 1D
            resource.physicalPropertiesSource = com.bionova.optimi.core.Constants.NMD
            resource.impactBasis = "unit"
            resource.epdNumber = ""
            resource.resourceGroup = [com.bionova.optimi.core.Constants.NMD_BASIC]
            resource.downloadLink = "-"
            resource.environmentDataSourceType = com.bionova.optimi.core.Constants.MANUFACTURER_EDST
            resource.environmentDataSourceStandard = "EN15804"
            resource.upstreamDB = "-"
            resource.pcr = "-"
            resource.pcrQuality = "Only with EN15804"
            resource.verificationStatus = "Verified"
            resource.epdProgram = com.bionova.optimi.core.Constants.NMD
            resource.environmentDataPeriod = 2019
            resource.importFile = com.bionova.optimi.core.Constants.NMD_3_API
            resource.impactNonLinear = true // TODO: correct?
            resource.staticFullName = optimiResourceService.generateStaticFullName(resource)
            resource.searchString = optimiResourceService.generateSearchString(resource)

            if (resource.validate()) {
                if (existingResource) {
                    Boolean resourcesMatch = resourcesMatch(existingResource, resource)

                    if (resourcesMatch) {
                        resource = null
                        resource = existingResource
                    } else {
                        existingResource.active = false
                        existingResource.save(flush: true)
                        resource = resource.save(flush: true)
                    }
                } else {
                    resource = resource.save(flush: true)
                }
                log.info("NMD3 API, RESOURCE CREATED: ${resourceId} / NMD3, nameEN: ${resource.nameEN}")
                flashService.setFadeInfoAlert("NMD3 API, RESOURCE CREATED: ${resourceId} / NMD3, nameEN: ${resource.nameEN}", true)
            } else {
                loggerUtil.error(log, "NMD3 API Error: Resource did not validate, errors in resource ${resourceId}: ${resource.errors.getAllErrors()}")
                flashService.setErrorAlert("NMD3 API Error: Resource did not validate, errors in resource ${resourceId}: ${resource.errors.getAllErrors()}", true)

            }
        }
        return resource
    }

    @Deprecated
    def createNMDResource(Nmd3ElementOnderdelen nmd3ElementOnderdelen, String profileDate) {
        String CUAS = getCUASFromElementOnderdelen(nmd3ElementOnderdelen)
        String unit = convertNMDUnitToOCLCA(nmd3ElementOnderdelen.getFunctioneleEenheidID())

        String resourceId = "NMD3basic${nmd3ElementOnderdelen.getElementID()}".toString()
        Resource existingResource = optimiResourceService.getResourceWithGorm(resourceId)
        Resource resource

        String elemName = nmd3ElementOnderdelen.getElementNaam()
        String name

        if (elemName) {
            if (CUAS) {
                name = CUAS + ": " + elemName
            } else {
                name = elemName
            }
        }

        if (name) {
            resource = new Resource()
            resource.resourceId = resourceId
            resource.profileId = profileDate
            resource.stage = "A1-A3"
            resource.applicationId = "LCA"
            resource.dataProperties = [com.bionova.optimi.core.Constants.NMD_BASIC]
            resource.active = true
            resource.defaultProfile = true
            resource.nameEN = name
            resource.nameNL = name
            resource.manufacturer = ""
            resource.unitForData = unit
            resource.combinedUnits = [unit]
            resource.massConversionFactor = 1D
            resource.physicalPropertiesSource = com.bionova.optimi.core.Constants.NMD
            resource.impactBasis = "unit"
            resource.epdNumber = ""
            resource.resourceGroup = [com.bionova.optimi.core.Constants.NMD_BASIC]
            resource.downloadLink = "-"
            resource.environmentDataSourceType = com.bionova.optimi.core.Constants.MANUFACTURER_EDST
            resource.environmentDataSourceStandard = "EN15804"
            resource.upstreamDB = "-"
            resource.pcr = "-"
            resource.pcrQuality = "Only with EN15804"
            resource.verificationStatus = "Verified"
            resource.epdProgram = com.bionova.optimi.core.Constants.NMD
            resource.environmentDataPeriod = 2019
            resource.importFile = com.bionova.optimi.core.Constants.NMD_3_API
            resource.impactNonLinear = true // TODO: correct?
            resource.staticFullName = optimiResourceService.generateStaticFullName(resource)
            resource.searchString = optimiResourceService.generateSearchString(resource)

            if (resource.validate()) {
                if (existingResource) {
                    Boolean resourcesMatch = resourcesMatch(existingResource, resource)

                    if (resourcesMatch) {
                        resource = null
                        resource = existingResource
                    } else {
                        existingResource.active = false
                        existingResource.save(flush: true)
                        resource = resource.save(flush: true)
                    }
                } else {
                    resource = resource.save(flush: true)
                }
                log.info("NMD3 API, RESOURCE CREATED: ${resourceId} / NMD3, nameEN: ${resource.nameEN}")
                flashService.setFadeInfoAlert("NMD3 API, RESOURCE CREATED: ${resourceId} / NMD3, nameEN: ${resource.nameEN}", true)
            } else {
                loggerUtil.error(log, "NMD3 API Error: Resource did not validate, errors in resource ${resourceId}: ${resource.errors.getAllErrors()}")
                flashService.setErrorAlert("NMD3 API Error: Resource did not validate, errors in resource ${resourceId}: ${resource.errors.getAllErrors()}", true)
            }
        }
        return resource
    }

    @Deprecated
    private Boolean resourcesMatch(Resource existingResource, Resource resource) {
        Boolean resourcesMatch = Boolean.TRUE

        if (existingResource && resource) {
            if (existingResource.nameEN != resource.nameEN) {
                resourcesMatch = Boolean.FALSE
            } else if (existingResource.unitForData != resource.unitForData) {
                resourcesMatch = Boolean.FALSE
            }
        }
        return resourcesMatch
    }

    @Deprecated
    def convertNMDResourceGroupToOCLCA(Nmd3Profiel nmd3Profiel) {
        String resourceGroup = com.bionova.optimi.core.Constants.NMD_BASIC

        if (nmd3Profiel) {
            Integer fase = nmd3Profiel.getFaseID()

            if (fase && (fase == 2 || fase == 10)) {
                resourceGroup = com.bionova.optimi.core.Constants.TRANSPORTATION_DGBC
            }
        }
        return resourceGroup
    }

    @Deprecated
    def convertNMDServiceLifeToOCLCA(Integer levensduur) {
        Double serviceLife

        if (levensduur) {
            if (levensduur >= 1000) {
                serviceLife = -1
            } else {
                serviceLife = levensduur.toDouble()
            }
        }
        return serviceLife
    }


    def convertNMDUnitToOCLCA(Integer unitID) {
        String unit

        if (unitID) {
            switch (unitID) {
                case 1:
                    unit = "MJ"
                    break
                case 2:
                    unit = "kWh"
                    break
                case 3:
                    unit = "kg"
                    break
                case 4:
                    unit = "kg*jaar"
                    break
                case 5:
                    unit = "m"
                    break
                case 6:
                    unit = "m*jaar"
                    break
                case 7:
                    unit = "m2"
                    break
                case 8:
                    unit = "m2*jaar"
                    break
                case 9:
                    unit = "m2*km"
                    break
                case 10:
                    unit = "m2K/W"
                    break
                case 11:
                    unit = "m2K/W*jaar"
                    break
                case 12:
                    unit = "m2gbo"
                    break
                case 13:
                    unit = "m2gbo*jaar"
                    break
                case 14:
                    unit = "m3"
                    break
                case 15:
                    unit = "m3*jaar"
                    break
                case 16:
                    unit = "mm"
                    break
                case 17:
                    unit = "p"
                    break
                case 18:
                    unit = "p*jaar"
                    break
                case 19:
                    unit = "t*km"
                    break
                case 20:
                    unit = "tkm"
                    break
                case 21:
                    unit = "onbekend"
                    break
                case 22:
                    unit = "Samengesteld"
                    break
                case 23:
                    unit = "kg antimoon"
                    break
                case 24:
                    unit = "kg CO2"
                    break
                case 25:
                    unit = "kg CFK-11"
                    break
                case 26:
                    unit = "kg ethyleen"
                    break
                case 27:
                    unit = "kg SO2"
                    break
                case 28:
                    unit = "kg PO4"
                    break
                case 29:
                    unit = "kg 1,4-dichloorbenzeen"
                    break
                case 30:
                    unit = "nvt"
                    break
                case 31:
                    unit = "Kw"
                    break
                default:
                    // Unit out of scope
                    break
            }
        }
        return unit

    }

    @Deprecated
    def convertNMDImpactsToOCLCA(Nmd3Profiel nmd3Profiel) {
        Map<String, Double> impacts = [:]

        if (nmd3Profiel) {
            Double shadowPrice = 0

            nmd3Profiel.getProfielMilieuEffecten()?.each { Nmd3ProfielMilieuEffecten nmd3ProfielMilieuEffecten ->
                Integer MilieuCategorieID = nmd3ProfielMilieuEffecten.getMilieuCategorieID()
                Double impact = nmd3ProfielMilieuEffecten.getMilieuWaarde()

                if (MilieuCategorieID && impact) {
                    switch (MilieuCategorieID) {
                        case 1:
                            impacts.put("impactADPElements_kgSbe", impact)
                            shadowPrice = shadowPrice + impact * 0.16
                            break
                        case 2:
                            impacts.put("impactADPFossilFuels_MJ", impact)
                            shadowPrice = shadowPrice + impact * 0.16
                            break
                        case 4:
                            impacts.put("impactGWP100_kgCO2e", impact)
                            shadowPrice = shadowPrice + impact * 0.05
                            break
                        case 5:
                            impacts.put("impactODP_kgCFC11e", impact)
                            shadowPrice = shadowPrice + impact * 30
                            break
                        case 6:
                            impacts.put("impactPOCP_kgEthenee", impact)
                            shadowPrice = shadowPrice + impact * 2
                            break
                        case 7:
                            impacts.put("impactAP_kgSO2e", impact)
                            shadowPrice = shadowPrice + impact * 4
                            break
                        case 8:
                            impacts.put("impactEP_kgPO4e", impact)
                            shadowPrice = shadowPrice + impact * 9
                            break
                        case 9:
                            impacts.put("humanToxiHTP_kgDCB", impact)
                            shadowPrice = shadowPrice + impact * 0.09
                            break
                        case 10:
                            impacts.put("freshWaterToxiFAETP_kgDCB", impact)
                            shadowPrice = shadowPrice + impact * 0.09
                            break
                        case 16:
                            impacts.put("renewablesUsedAsEnergy_MJ", impact)
                            break
                        case 17:
                            impacts.put("nonRenewablesUsedAsEnergy_MJ", impact)
                            break
                        case 19:
                            impacts.put("cleanWaterNetUse_m3", impact)
                            break
                        case 20:
                            impacts.put("wasteNonHazardous_kg", impact)
                            break
                        case 21:
                            impacts.put("wasteHazardous_kg", impact)
                            break
                        default:
                            // Impact out of scope
                            break
                    }
                }
            }

            if (shadowPrice) {
                impacts.put("shadowPrice", shadowPrice)
            }
        }
        return impacts

    }

    @Deprecated
    public String getCUASFromElementOnderdelen(Nmd3ElementOnderdelen nmd3ElementOnderdelen) {
        String CUAS

        if (nmd3ElementOnderdelen) {
            Integer CUAS_ID = nmd3ElementOnderdelen.getCUAS_ID()

            if (CUAS_ID) {
                switch (CUAS_ID) {
                    case 1:
                        CUAS = "C"
                        break
                    case 2:
                        CUAS = "U"
                        break
                    case 3:
                        CUAS = "A"
                        break
                    case 4:
                        CUAS = "S"
                        break
                    case 5:
                        CUAS = "T"
                        break
                    case 27:
                        CUAS = "nvt"
                        break
                    case 28:
                        CUAS = "onbekend"
                        break
                    default:
                        // CUAS out of scope
                        break
                }
            }
        }
        return CUAS
    }

    /*
        Scaling multiplier calculator

        Y = calculated multiplier
        X = thickness_mm, user editable
        A = SchalingsFormuleA
        B = SchalingsFormuleB
        C = SchalingsFormuleC
     */

    @Deprecated
    public Double scalingCalculation(Integer SchalingsFormuleID, Integer X, Double A, Double B, Double C) {
        Double Y = 0

        if (SchalingsFormuleID) {
            try {
                switch (SchalingsFormuleID) {
                    case 1:
                        // Linear
                        Y = A * X + C
                        break
                    case 2:
                        // Power of a point
                        Y = A * X ^ B + C
                        break
                    case 3:
                        // Logarithmic
                        Y = A * Math.log(X) + C
                        break
                    case 4:
                        // Exponential
                        Y = A * Math.exp(B * X) + C
                        break
                    default:
                        // SchalingsFormuleID out of scope
                        break
                }
            } catch(Exception e) {
                loggerUtil.error(log, "NMD 3 API Exception: Scaling calculation", e)
                flashService.setErrorAlert("NMD 3 API Exception: Scaling calculation: ${e.getMessage()}", true)

                Y = 0
            }
        }
        return Y

    }

    @Deprecated
    public String getSchalingRangeTextX1(Nmd3ProfielsetsEnSchalingBijProduct2 nmd3ProfielSet) {
        String scalingText

        if (nmd3ProfielSet) {
            if (nmd3ProfielSet.getSchalingMinX1() != null && nmd3ProfielSet.getSchalingMaxX1() != null) {
                scalingText = "(${nmd3ProfielSet.getSchalingMinX1().intValue()} - ${nmd3ProfielSet.getSchalingMaxX1().intValue()}) mm"
            } else if (nmd3ProfielSet.getSchalingMinX1() != null) {
                scalingText = "(> ${nmd3ProfielSet.getSchalingMinX1().intValue()}) mm"
            } else if (nmd3ProfielSet.getSchalingMaxX1() != null) {
                scalingText = "(< ${nmd3ProfielSet.getSchalingMaxX1().intValue()}) mm"
            } else {
                scalingText = "mm"
            }
        }
        return scalingText
    }

    @Deprecated
    public String getSchalingRangeTextX2(Nmd3ProfielsetsEnSchalingBijProduct2 nmd3ProfielSet) {
        String scalingText

        if (nmd3ProfielSet) {
            if (nmd3ProfielSet.getSchalingMinX2() != null && nmd3ProfielSet.getSchalingMaxX2() != null) {
                scalingText = "(${nmd3ProfielSet.getSchalingMinX2().intValue()} - ${nmd3ProfielSet.getSchalingMaxX2().intValue()}) mm"
            } else if (nmd3ProfielSet.getSchalingMinX2() != null) {
                scalingText = "(> ${nmd3ProfielSet.getSchalingMinX2().intValue()}) mm"
            } else if (nmd3ProfielSet.getSchalingMaxX2() != null) {
                scalingText = "(< ${nmd3ProfielSet.getSchalingMaxX2().intValue()}) mm"
            } else {
                scalingText = "mm"
            }
        }
        return scalingText
    }

    ConstructionGroup getNMD3ConstructionGroup() {
        // find by groupId is better, as name can be duplicated
        ConstructionGroup NMD3Group = constructionService.getConstructionGroupByGroupId(NMD3GroupId)

        if (!NMD3Group) {
            NMD3Group = new ConstructionGroup()
            NMD3Group.name = NMD3GroupName
            NMD3Group.uneditable = Boolean.TRUE
            NMD3Group.groupId = NMD3GroupId
            if (NMD3Group.validate()) {
                NMD3Group = NMD3Group.save(flush: true, failOnError: true)
            }
        }
        return NMD3Group
    }

    @Deprecated
    public void apiStressTest() {
        // Just to "stress test" the api so we wont get 500 server error, not really a stress test but shit api so what can you do
        String token = getAccessToken()
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date())

        for (int i = 1; i <= 100; i++) {
            RestResponse elemResponse = callApi(token, NLsfBRAWElemEndpoint, [ZoekDatum: date])

            log.info("ITERATION: ${i} / 100: Response: ${elemResponse?.status}") // Should always be 200, 500 means it crashed

            if (elemResponse?.status == 500) {
                log.info("API went down during stress test")
                flashService.setErrorAlert("API went down during stress test", true)
                break
            } else {
                sleep(2000) // Sleep 2 seconds... very stress
            }
        }
    }

    /**
     * Get the date to start checking for NMD update.
     * Ideally it should be the last date that it was not updated successfully.
     * If there's none, then return today.
     * @param today * @return
     */
    Date getDateToCheckForNmdUpdate(Date today) {
        if (!today) {
            return null
        }

        List<NmdUpdate> unsuccessfulUpdate = NmdUpdate.findAllByStatusOk(false)
        if (unsuccessfulUpdate?.size() > 0) {
            // We take the earliest day update failed and run update again from that day
            return unsuccessfulUpdate.findAll { it.date }?.sort { it.date }?.first()?.date
        }

        String msg = "NMD UPDATE (${today}): NO FAILED UPDATE FOUND"
        log.info(msg)
        flashService.setFadeInfoAlert(msg, true)

        return today
    }

    /**
     * Check mandatory configs in database and also load the cached methods.
     * Must run this before calling NMD api update
     * @return
     */
    boolean checkMandatoryConfigForApiUpdate() {
        if (nmdResourceService.getNmdUnitToOCL() == null) {
            return false
        }
        if (nmdResourceService.getNmdToepassingsIdToApplication() == null) {
            return false
        }
        if (nmdResourceService.getNmdMillieuCategoryIdToOCLImpactName() == null) {
            return false
        }
        if (nmdResourceService.getNmdFaseIdToOCLStages() == null) {
            return false
        }
        if (nmdResourceService.getNmdElementTypes() == null) {
            return false
        }
        return true
    }

    String getNMDDailyUpdate(String startFromDate = null, Boolean onlyUpdateNmdElement = false) {
        Date today = new Date()
        boolean configCheckPassed = checkMandatoryConfigForApiUpdate()
        if (!configCheckPassed) {
            String missingConfigErr = "NMD Update (${today}) cannot run. NMD application is missing one of these necessary configs: nmdUnitToOCL, nmdFaseIdToOCLStages, nmdToepassingsIdToApplication, nmdMillieuCategoryIdToOCLImpactName, nmdElementTypes"
            flashService.setErrorAlert(missingConfigErr)
            loggerUtil.error(log, missingConfigErr)
            return 'error'
        }

        String token = getAccessToken(true)
        TimeDuration duration
        int days = 0 // only today
        String updateStatus = "ok"

        try {
            Date dateToCheck = startFromDate ? Date.parse("dd-MM-yyy", startFromDate) : getDateToCheckForNmdUpdate(today)

            if (dateToCheck == null) {
                // just in case
                String weirdError = "NMD Update (${today}) cannot run. Because we cannot instantiate today's date, or unsuccessful NmdUpdate object doesn't have date"
                flashService.setErrorAlert(weirdError)
                loggerUtil.error(log, weirdError)
                return 'error'
            }

            if (dateToCheck == today) {
                String todayDateString = new SimpleDateFormat("yyyyMMdd").format(today)
                NmdUpdate todayRecord = NmdUpdate.findByDateString(todayDateString)
                if (todayRecord?.statusOk) {
                    String noNeed = "NMD UPDATE (${today}): Everything is up-to-date. No update needed"

                    if (log.infoEnabled) {
                        log.info(noNeed)
                    }
                    flashService.setSuccessAlert(noNeed)
                    return 'ok'
                }
            }

            if (dateToCheck != today) {
                duration = TimeCategory.minus(today, dateToCheck)
                days = duration.days
            }

            String startMsg = "NMD UPDATE (${today}): DURATION ${days} days need to run update"
            if (log.infoEnabled) {
                log.info(startMsg)
            }
            flashService.setFadeInfoAlert(startMsg)

            Query constructionCreationQuery = queryService.getQueryByQueryId(Query.QUERYID_CONSTRUCTION_CREATOR, true)
            QuestionAnswerChoice classificationParam = questionService.getQuestion(constructionCreationQuery, com.bionova.optimi.core.Constants.CONSTRUCTION_CLASSIFICATION_PARAMETERS)?.choices?.find({ com.bionova.optimi.core.Constants.CLASSIFICATION_ID_3B.equals(it.answerId) })

            Map<String, String> resourceParameters = classificationParam?.resourceParameters
            List<PersistentProperty> resourcePersistentProperties = domainClassService.getPersistentPropertiesForDomainClass(Resource.class)
            List<String> persistingListProperties = resourcePersistentProperties?.findAll({ it.type?.equals(List) })?.collect({ it.name })
            List<String> persistingRegularProperties = resourcePersistentProperties?.findAll({ !it.type?.equals(List) })?.collect({ it.name })
            Map<String, List<Map<String, String>>> updateStringForNmdConstructionConfig = nmdResourceService.getUpdateStringOnImportNmd()
            ConstructionGroup nmdApiGroup = getNMD3ConstructionGroup()
            List<String> sendEmail = []

            while (days >= 0) {
                Date newDate = DateUtils.addDays(today, -days)
                String dateString = new SimpleDateFormat("yyyyMMdd").format(newDate)
                // logic before was findByDate <<< this was not really correct, because the Date data type is in milliseconds.
                // So now we change to the dateString, which is only the date
                NmdUpdate newRecord = NmdUpdate.findByDateString(dateString)

                Boolean statusOk = false
                if (!newRecord) {
                    newRecord = new NmdUpdate()
                }

                HttpResponse response = callApi2(token, [ZoekDatum: dateString, includeNULLs: true])
                NmdUpdateResponse jsonObject = response?.body() ? new JsonSlurper().parseText(response.body()) as NmdUpdateResponse : null
                if (NMD_ACCESS_TOKEN_EXPIRED.equalsIgnoreCase(jsonObject?.Message)) {
                    token = getAccessToken(Boolean.TRUE)
                    response = callApi2(token, [ZoekDatum: dateString, includeNULLs: true])
                    jsonObject = response?.body() ? new JsonSlurper().parseText(response.body()) as NmdUpdateResponse : null
                }

                // use JSONObject for logging
                JSONObject resJsonObj = response?.body() ? new JSONObject(response.body()) : null

                if (200 == response?.statusCode() && jsonObject?.Message && !NMD_ACCESS_TOKEN_EXPIRED.equalsIgnoreCase(jsonObject?.Message)) {
                    statusOk = Boolean.TRUE
                    if (NMD_NO_DATA_AVAILABLE_FTS.equalsIgnoreCase(jsonObject.Message)) {
                        if (log.infoEnabled) {
                            log.info("NMD UPDATE: No data for date: ${newDate}. ${resJsonObj}")
                        }
                        flashService.setWarningAlert("NMD UPDATE: No data for date: ${newDate}.")
                    } else {
                        if (log.infoEnabled) {
                            log.info("NMD UPDATE: SUCCESSFUL FOR DATE ${newDate}: ${resJsonObj}")
                        }
                        flashService.setSuccessAlert("NMD UPDATE: SUCCESSFUL FOR DATE ${newDate}")

                        NmdNewResources newResourcesJson = jsonObject.Nieuwe_Publicaties
                        NmdDeactivateResources deactiveResourceJson = jsonObject.Gedeactiveerde_Entiteiten
                        logNewTables(jsonObject, today, newRecord)

                        if (deactiveResourceJson) {
                            if (!onlyUpdateNmdElement) {
                                nmdResourceService.handleDeactiveNMDjsonToDB(deactiveResourceJson, nmdApiGroup, newRecord)
                            }
                            nmdElementService.deactivateNmdElementByApi(deactiveResourceJson, newRecord)
                        }
                        if (newResourcesJson) {
                            nmdElementService.updateNewNmdElementByApi(newResourcesJson, updateStringForNmdConstructionConfig, newRecord, newDate)
                            if (!onlyUpdateNmdElement) {
                                List<Document> nmdElementList = nmdElementService.getNmdElementsAsDocument()
                                nmdResourceService.handleNewNMDjsonToDB(newResourcesJson, resourceParameters, persistingListProperties, persistingRegularProperties,
                                        updateStringForNmdConstructionConfig, nmdElementList, newDate, nmdApiGroup, newRecord)
                            }
                        }
                        persistUpdatesLog(newRecord, today)
                        setRecordHasConfigErrors(newRecord)
                        resolveUnrecognizableValueErrors(newRecord, today)

                        if (newRecord.newTables || newRecord.errorObjectsAsString) {
                            sendEmail.add("<b>For date: ${newDate}</b><br /> ${newRecord.newTables ? "<b>HUOM! New tables:</b><br /> ${newRecord.newTables}" : ''} ${newRecord.errorObjectsAsString ? "<b>Errors:</b><br /> ${newRecord.errorObjectsAsString}" : ''}")
                        }
                    }
                } else {
                    loggerUtil.error(log, "NMD UPDATE (${today}): UPDATE NMD FAILED FOR DATE ${newDate} error: ${resJsonObj}")
                    flashService.setErrorAlert("NMD UPDATE (${today}): UPDATE NMD FAILED FOR DATE ${newDate} error. Check log")
                    updateStatus = "error"
                }
                newRecord.date = newDate
                newRecord.dateString = dateString
                newRecord.statusOk = statusOk
                newRecord.updateDate = today
                if (newRecord.id) {
                    newRecord.merge(flush: true)
                } else {
                    newRecord.save(flush: true)
                }

                if (!statusOk) {
                    // REL-291 if the update is unsuccessful, we stop the process and do not call the next date, since the NMD update is sequential.
                    String stopMsg = "NMD Update (${today}) stopped at date ${newDate}"
                    flashService.setWarningAlert(stopMsg)
                    loggerUtil.error(log, stopMsg)
                    break
                }
                days--
            }

            if (sendEmail) {
                // log as error to send email with the HTML characters, in log this is one line
                log.error(sendEmail.join('<br/>'))
                // log as warn and replace the HTML characters to see it nicely in log
                log.warn(sendEmail.join('\n')?.replaceAll(/<br.{0,3}>/, '\n')?.replaceAll(/<\/{0,3}b>/, ''))
            }
        } catch (e) {
            updateStatus = "error"
            loggerUtil.error(log, "ERROR in getNMDDailyUpdate", e)
            flashService.setErrorAlert("ERROR in getNMDDailyUpdate: ${e.getMessage()}")
        }

        return updateStatus
    }

    void setRecordHasConfigErrors(NmdUpdate updateRecord) {
        if (!updateRecord) {
            return
        }

        if (updateRecord.unitErrors || updateRecord.milieuCategorieIdErrors || updateRecord.toepassingIdErrors || updateRecord.faseIdErrors) {
            updateRecord.hasConfigErrors = true
        }
    }

    /**
     * Runs a check to resolve the unrecognizable values that might have been added to configurations
     * (it should be resolved in the second time the update is run with the updated config)
     * @param updateRecord
     * @param today
     */
    void resolveUnrecognizableValueErrors(NmdUpdate updateRecord, Date today) {
        if (!updateRecord || !today) {
            return
        }

        String news = ''

        if (updateRecord.unitErrors) {
            Set<Integer> knownUnits = nmdResourceService.getKnownNmdUnits()
            if (knownUnits) {
                Set<Integer> fixedUnits = updateRecord.unitErrors.intersect(knownUnits)
                if (fixedUnits) {
                    updateRecord.unitErrors.removeAll(fixedUnits)
                    news += "Following unitIds got fixed: ${fixedUnits}<br/>"
                }
            }
        }

        if (updateRecord.faseIdErrors) {
            Set<Integer> knownFaseIds = nmdResourceService.getKnownFaseIds()
            if (knownFaseIds) {
                Set<Integer> fixedFaseIds = updateRecord.faseIdErrors.intersect(knownFaseIds)
                if (fixedFaseIds) {
                    updateRecord.faseIdErrors.removeAll(fixedFaseIds)
                    news += "Following FaseIDs got fixed: ${fixedFaseIds}<br/>"
                }
            }
        }

        if (updateRecord.milieuCategorieIdErrors) {
            Set<Integer> knownMilieuCategorieIds = nmdResourceService.getKnownMilieuCategorieIds()
            if (knownMilieuCategorieIds) {
                Set<Integer> fixedMilieuCategorieIds = updateRecord.milieuCategorieIdErrors.intersect(knownMilieuCategorieIds)
                if (fixedMilieuCategorieIds) {
                    updateRecord.milieuCategorieIdErrors.removeAll(fixedMilieuCategorieIds)
                    news += "Following MilieuCategorieIds got fixed: ${fixedMilieuCategorieIds}<br/>"
                }
            }
        }

        if (updateRecord.toepassingIdErrors) {
            Set<Integer> knownToepassingIds = nmdResourceService.getKnownToepassingIds()
            if (knownToepassingIds) {
                Set<Integer> fixedToepassingIds = updateRecord.toepassingIdErrors.intersect(knownToepassingIds)
                if (fixedToepassingIds) {
                    updateRecord.toepassingIdErrors.removeAll(fixedToepassingIds)
                    news += "Following MilieuCategorieIds got fixed: ${fixedToepassingIds}<br/>"
                }
            }
        }

        if (news) {
            news = "<b>Update written on ${today}:</b><br />${news}"
            updateRecord.errorObjectsAsString = "${updateRecord.errorObjectsAsString ?: ''}${news}"
            updateRecord.updatedObjectsAsString = "${updateRecord.updatedObjectsAsString ?: ''}${news}"
        }

        // resolve the boolean if all errors have been resolved or do not exists
        if (!updateRecord.unitErrors && !updateRecord.milieuCategorieIdErrors && !updateRecord.toepassingIdErrors && !updateRecord.faseIdErrors) {
            updateRecord.hasConfigErrors = false
        }
    }

    /**
     * Compile the temp errors and updates into the persisted errorObjectsAsString and updatedObjectsAsString
     * @param updateRecord
     * @param today
     */
    void persistUpdatesLog(NmdUpdate updateRecord, Date today) {
        if (!updateRecord || !today) {
            return
        }

        String date = "<b>Update written on ${today}:</b><br />"
        String tempErrors = ''

        if (updateRecord.tempToepassingIdErrors || updateRecord.tempFaseIdErrors || updateRecord.tempMilieuCategorieIdErrors || updateRecord.tempUnitErrors) {
            tempErrors += '<b>Unrecognizable values:</b><br />'
        }

        if (updateRecord.tempToepassingIdErrors) {
            tempErrors += updateRecord.tempToepassingIdErrors
        }

        if (updateRecord.tempFaseIdErrors) {
            tempErrors += updateRecord.tempFaseIdErrors
        }

        if (updateRecord.tempMilieuCategorieIdErrors) {
            tempErrors += updateRecord.tempMilieuCategorieIdErrors
        }

        if (updateRecord.tempUnitErrors) {
            tempErrors += updateRecord.tempUnitErrors
        }

        if (updateRecord.tempElementErrors) {
            String err = '<b>Errors while updating elements:</b><br />'
            tempErrors = "${tempErrors}${err}${updateRecord.tempElementErrors}"
        }

        if (updateRecord.tempDeactivateElementsNotFound) {
            String err = '<b>Elements not found in database for deactivating:</b><br />'
            tempErrors = "${tempErrors}${err}${updateRecord.tempDeactivateElementsNotFound}"
        }

        if (updateRecord.tempResourceErrors) {
            String err = '<b>Errors while updating resources:</b><br />'
            tempErrors = "${tempErrors}${err}${updateRecord.tempResourceErrors}"
        }

        if (updateRecord.tempResourceNoImpacts) {
            String err = '<b>Resources from API without impacts:</b><br />'
            tempErrors = "${tempErrors}${err}${updateRecord.tempResourceNoImpacts}"
        }

        if (updateRecord.tempConstructionErrors) {
            String err = '<b>Errors while updating constructions:</b><br />'
            tempErrors = "${tempErrors}${err}${updateRecord.tempConstructionErrors}"
        }

        if (updateRecord.tempDeactivateConstructionsErrors) {
            String err = '<b>Errors while deactivating constructions:</b><br />'
            tempErrors = "${tempErrors}${err}${updateRecord.tempDeactivateConstructionsErrors}"
        }

        if (updateRecord.tempDeactivateConstructionsNotFound) {
            String err = '<b>Constructions not found in database for deactivating:</b><br />'
            tempErrors = "${tempErrors}${err}${updateRecord.tempDeactivateConstructionsNotFound}"
        }

        if (tempErrors) {
            updateRecord.errorObjectsAsString = "${updateRecord.errorObjectsAsString ?: ''}${date}${tempErrors}"
        }

        if (updateRecord.tempNewUpdates) {
            updateRecord.updatedObjectsAsString = "${updateRecord.updatedObjectsAsString ?: ''}${date}${updateRecord.tempNewUpdates}"
        }
    }

    // log if there are new tables via the API
    void logNewTables(NmdUpdateResponse jsonObject, Date today, NmdUpdate updateRecord) {
        if (!jsonObject || !updateRecord) {
            return
        }

        String newTables = ''
        if (jsonObject.Nieuwe_Publicaties?.NMD_Eenheid_Versies != null || jsonObject.Gedeactiveerde_Entiteiten?.NMD_Eenheid_Versies != null) {
            newTables += "NMD_Eenheid_Versies <br/>"
        }
        if (jsonObject.Nieuwe_Publicaties?.NMD_Schalingsformules_Versies != null || jsonObject.Gedeactiveerde_Entiteiten?.NMD_Schalingsformules_Versies != null) {
            newTables += "NMD_Schalingsformules_Versies <br/>"
        }
        if (jsonObject.Nieuwe_Publicaties?.NMD_Type_Kaart_Versies != null || jsonObject.Gedeactiveerde_Entiteiten?.NMD_Type_Kaart_Versies != null) {
            newTables += "NMD_Type_Kaart_Versies <br/>"
        }
        if (jsonObject.Nieuwe_Publicaties?.NMD_BouwwerkFuncties_Versies != null || jsonObject.Gedeactiveerde_Entiteiten?.NMD_BouwwerkFuncties_Versies != null) {
            newTables += "NMD_BouwwerkFuncties_Versies <br/>"
        }
        if (jsonObject.Nieuwe_Publicaties?.NMD_Fasen_Versies != null || jsonObject.Gedeactiveerde_Entiteiten?.NMD_Fasen_Versies != null) {
            newTables += "NMD_Fasen_Versies <br/>"
        }
        if (jsonObject.Nieuwe_Publicaties?.NMD_Milieucategorie_Versies != null || jsonObject.Gedeactiveerde_Entiteiten?.NMD_Milieucategorie_Versies != null) {
            newTables += "NMD_Milieucategorie_Versies <br/>"
        }
        if (jsonObject.Nieuwe_Publicaties?.NMD_Fasen_Versies != null || jsonObject.Gedeactiveerde_Entiteiten?.NMD_Fasen_Versies != null) {
            newTables += "NMD_Fasen_Versies <br/>"
        }
        if (jsonObject.Nieuwe_Publicaties?.NMD_ToepassingsGebieden_Versies != null || jsonObject.Gedeactiveerde_Entiteiten?.NMD_ToepassingsGebieden_Versies != null) {
            newTables += "NMD_ToepassingsGebieden_Versies <br/>"
        }

        if (newTables) {
            updateRecord.newTables = "${updateRecord.newTables ?: ''}<b>Update written on ${today}:</b><br />${newTables}"
        }
    }

    void addFaultyUnitId(NmdUpdate updateRecord, Integer faultyUnit) {
        if (updateRecord && faultyUnit != null) {
            if (!updateRecord.unitErrors) {
                updateRecord.unitErrors = []
            }

            updateRecord.unitErrors.add(faultyUnit)
        }
    }

    void addFaultyFaseID(NmdUpdate updateRecord, Integer faseId) {
        if (updateRecord && faseId != null) {
            if (!updateRecord.faseIdErrors) {
                updateRecord.faseIdErrors = []
            }
            updateRecord.faseIdErrors.add(faseId)
        }
    }

    void addFaultyMilieuCategorieID(NmdUpdate updateRecord, Integer milieuCategorieId) {
        if (updateRecord && milieuCategorieId != null) {
            if (!updateRecord.milieuCategorieIdErrors) {
                updateRecord.milieuCategorieIdErrors = []
            }
            updateRecord.milieuCategorieIdErrors.add(milieuCategorieId)
        }
    }

    void addFaultyToepassingID(NmdUpdate updateRecord, Integer toepassingId) {
        if (updateRecord && toepassingId != null) {
            if (!updateRecord.toepassingIdErrors) {
                updateRecord.toepassingIdErrors = []
            }
            updateRecord.toepassingIdErrors.add(toepassingId)
        }
    }

    public JSONObject getNMDUpdateByDate(String dateString) {
        String token = getAccessToken(Boolean.TRUE)
        JSONObject responseTxt = null
        try {
            HttpResponse response = callApi2(token, [ZoekDatum: dateString, includeNULLs: true])
            responseTxt = response?.body() ? new JSONObject(response.body()) : null
        } catch (Exception e) {
            responseTxt = new JSONObject()
            responseTxt.put("error": "Error when fetching API for date ${dateString}")
        }
        return responseTxt
    }
}