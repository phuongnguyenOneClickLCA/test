package com.bionova.optimi.core.service

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.Construction
import com.bionova.optimi.core.domain.mongo.ConstructionGroup
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.NmdElement
import com.bionova.optimi.core.domain.mongo.NmdUpdate
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.json.nmd.NmdDeactivateResources
import com.bionova.optimi.json.nmd.NmdNewResources
import com.bionova.optimi.json.nmd.NmdProduct
import com.bionova.optimi.json.nmd.NmdProductElementProfielSet
import com.bionova.optimi.json.nmd.NmdProductProduct
import com.bionova.optimi.json.nmd.NmdProfiel
import com.bionova.optimi.json.nmd.NmdProfielMilieueffect
import com.bionova.optimi.json.nmd.NmdProfielSet
import grails.gorm.transactions.Transactional
import grails.plugin.cache.Cacheable
import org.bson.Document
import org.bson.types.ObjectId

@Transactional
class NmdResourceService {

    def loggerUtil
    def flashService
    def optimiResourceService
    def optimiStringUtils
    def nmdElementService
    def constructionService
    def applicationService
    def stringUtilsService
    def nmdApiService

    public final static String DEFAULT_MILLIEUCATEGORYID = '4'
    public final static String DEFAULT_FASEID = '1'

    /**
     * Handle the resource and constructions update from NMD API
     * Must run config check before executing this method {@link NmdApiService#checkMandatoryConfigForApiUpdate}
     * @param json
     * @param resourceParameters
     * @param persistingListProperties
     * @param persistingRegularProperties
     * @param updateStringConfig
     * @param nmdElementList
     * @param nmdUpdateTime
     * @param nmdApiGroup
     * @param updateRecord
     * @return
     */
    String handleNewNMDjsonToDB(NmdNewResources json, Map<String, String> resourceParameters, List<String> persistingListProperties,
                                List<String> persistingRegularProperties, Map<String, List<Map<String, String>>> updateStringConfig,
                                List<Document> nmdElementList, Date nmdUpdateTime, ConstructionGroup nmdApiGroup, NmdUpdate updateRecord) {
        String resourceRecordsAsString = "<b>NEW UPDATES:</b> <br />"
        try {
            if (!json) {
                return ''
            }

            // Need to collect from LazyMap (from JSON Slurper) to the exact type
            Set<NmdProduct> productVersies = json.NMD_Product_Versies?.findAll { it != null && !it.DatumInActief } as Set<NmdProduct>
            List<NmdProductProduct> productProductVersies = json.NMD_Product_Product_Versies?.findAll { it != null && !it.DatumInActief }
            List<NmdProductElementProfielSet> productElementProfielset = json.NMD_Product_Element_ProfielSet_Versies?.findAll { it != null && !it.DatumInActief }
            Set<NmdProfielSet> profielsetVersies = json.NMD_ProfielSets_Versies?.findAll { it != null && !it.DatumInActief } as Set<NmdProfielSet>
            List<NmdProfiel> profielVersies = json.NMD_Profiel_Versies?.findAll { it != null && !it.DatumInActief }
            List<NmdProfielMilieueffect> profielMilieueffectVersies = json.NMD_ProfielMilieueffect_Versies?.findAll { it != null && !it.DatumInActief }

            Integer resourceCount = 0
            Integer constructionCount = 0
            //handle resource update
            if (profielsetVersies) {
                int i = 1
                int size = profielsetVersies?.size()
                Map<Integer, List<NmdProfielMilieueffect>> profielMilieueffectVersiesMap = getProfielMilieueffectMap(profielMilieueffectVersies)
                log.info("NMD RESOURCE UPDATE: Handling: ${size} Resources")

                for (NmdProfielSet resourceJson in profielsetVersies) {
                    if (!resourceJson) {
                        i++
                        continue
                    }

                    String resourceId = Constants.NMD_RESOURCEID_PREFIX + resourceJson.ProfielSetID?.toString()
                    Resource resourceNmd = optimiResourceService.getResourceByResourceAndProfileId(resourceId, Resource.NMD30)
                    if (!resourceNmd) {
                        resourceNmd = new Resource()
                        resourceNmd.resourceId = resourceId
                        resourceNmd.profileId = Resource.NMD30
                    }
                    String resourceName = optimiStringUtils.removeInvalidCharacters(resourceJson.ProfielSetNaam)
                    resourceNmd.nameEN = resourceName ?: Constants.UNNAMED
                    resourceNmd.nameNL = resourceName ?: Constants.UNNAMED
                    resourceNmd.applicationId = Constants.LCA_APPID
                    resourceNmd.dataProperties = [Constants.NMD_BASIC]
                    resourceNmd.defaultProfile = Boolean.TRUE
                    resourceNmd.impactBasis = Constants.UNIT_IMPACT_BASIS
                    resourceNmd.massConversionFactor = 1
                    resourceNmd.nmdCategoryId = resourceJson.CategorieID?.toString()
                    resourceNmd.defaultThickness_mm = resourceJson.SchalingsMaat_X1 ?: 0
                    resourceNmd.defaultThickness_mm2 = resourceJson.SchalingsMaat_X2 ?: 0
                    resourceNmd.serviceLife = resourceJson.Levensduur ?: 0
                    // REL-355 don't set to 0 if some values are missing from API, except for the thicknesses and service life
                    resourceNmd.nmdSchalingsFormuleID = resourceJson.SchalingsFormuleID
                    resourceNmd.nmdSchalingsFormuleA1 = resourceJson.SchalingsFormuleA1
                    resourceNmd.nmdSchalingsFormuleA2 = resourceJson.SchalingsFormuleA2
                    resourceNmd.nmdSchalingsFormuleB1 = resourceJson.SchalingsFormuleB1
                    resourceNmd.nmdSchalingsFormuleB2 = resourceJson.SchalingsFormuleB2
                    resourceNmd.nmdSchalingsFormuleC = resourceJson.SchalingsFormuleC
                    resourceNmd.nmdSchalingMinX1 = resourceJson.SchalingMinX1
                    resourceNmd.nmdSchalingMinX2 = resourceJson.SchalingMinX2
                    resourceNmd.nmdSchalingMaxX1 = resourceJson.SchalingMaxX1
                    resourceNmd.nmdSchalingMaxX2 = resourceJson.SchalingMaxX2
                    resourceNmd.importFile = Constants.NMD_3_API
                    resourceNmd.nmdUpdateTime = nmdUpdateTime

                    extractElementInfoForNmdResource(resourceJson, productElementProfielset, resourceNmd)
                    extractUnitForNmdResource(resourceJson, resourceNmd, resourceId, updateRecord)
                    Map<String, Map<String, Double>> impacts = getImpactsForNmdResource(resourceJson, profielVersies, profielMilieueffectVersiesMap, resourceNmd, updateRecord)

                    if (impacts?.size() > 0) {
                        resourceNmd.active = true
                        resourceNmd.impacts = impacts
                        if (!resourceNmd.id) {
                            resourceRecordsAsString = "${resourceRecordsAsString}NEW "
                        } else {
                            resourceRecordsAsString = "${resourceRecordsAsString}UPDATE "
                        }

                        // we define in config some characters to be replaced in some resource attribute
                        optimiResourceService.updateStringFieldsInResource(updateStringConfig, resourceNmd)

                        if (resourceNmd && resourceNmd.validate()) {
                            resourceNmd = optimiResourceService.saveResource(resourceNmd)
                            deactivateDuplicatedNmdResources(resourceNmd)
                            resourceCount++
                            resourceRecordsAsString = "${resourceRecordsAsString}RESOURCE ${resourceCount}: ${resourceNmd.id} resourceId: ${resourceNmd.resourceId}  - Name ${resourceNmd.nameEN} <br />"
                        } else {
                            updateRecord?.tempResourceErrors = "${updateRecord?.tempResourceErrors ?: ''} Failed to save resourceId ${resourceId} - name: ${resourceName}. ${resourceNmd.getErrors()?.getAllErrors()} <br />"
                        }
                    } else {
                        updateRecord?.tempResourceNoImpacts = "${updateRecord?.tempResourceNoImpacts ?: ''} ${resourceId} - name: ${resourceName}.<br />"
                    }
                    log.info("NMD Resource update prog: ${i} / ${size}")
                    i++
                }
            }
            //handle construction update
            if (productVersies) {
                int i = 1
                int size = productVersies?.size()
                log.info("NMD RESOURCE UPDATE: Handling: ${size} Constructions.")

                for (NmdProduct product in productVersies) {
                    if (!product) {
                        i++
                        continue
                    }

                    boolean isOuderProduct = productProductVersies?.any { it.OuderProductID == product.ProductID }

                    // capture either OuderProduct (parent product) or IsElementDekkend: true (Totaal product). Otherwise skip!
                    if (!isOuderProduct && !product.IsElementDekkend) {
                        i++
                        continue
                    }

                    String constructionId = product.ProductID?.toString()

                    // REL-291 always disable the existing construction in database (if any) and create a new one
                    // many constructions with same constructionId can co-exists. Hence need to deactivate them all.
                    Boolean existingConstructionsDeactivated = constructionService.deactivateConstructionsById(constructionId, nmdApiGroup)

                    Construction newNMDConstruction = new Construction()
                    newNMDConstruction.constructionId = constructionId
                    newNMDConstruction.nmdProductId = product.ProductID
                    String constructionName = product.ProductNaam
                    newNMDConstruction.nameEN = optimiStringUtils.removeInvalidCharacters(constructionName) ?: Constants.UNNAMED
                    newNMDConstruction.nmdCategoryId = product.CategorieID?.toString()
                    newNMDConstruction.serviceLife = product.Levensduur
                    newNMDConstruction.nmdElementId = productElementProfielset?.find({ it.ProductID == product.ProductID })?.ElementID?.toString()
                    newNMDConstruction.country = Constants.NETHERLANDS
                    newNMDConstruction.classificationQuestionId = Constants.CONSTRUCTION_CLASSIFICATION_PARAMETERS
                    newNMDConstruction.classificationParamId = Constants.CLASSIFICATION_ID_3B
                    newNMDConstruction.representativeArea = Constants.EUROPE
                    newNMDConstruction.constructionType = Constants.STRUCTURE_CT
                    newNMDConstruction.importFile = Constants.NMD_3_API
                    newNMDConstruction.nmdUpdateTime = nmdUpdateTime
                    newNMDConstruction.nmdDatabaseVersion = Constants.NMD_3_DB_VERSION
                    newNMDConstruction.environmentDataSourceType = newNMDConstruction.nmdCategoryId == Constants.NmdCategory.THREE.category ? Constants.GENERIC_EDST : newNMDConstruction.nmdCategoryId ? Constants.MANUFACTURER_EDST : null
                    newNMDConstruction.preventExpand = false
                    newNMDConstruction.locked = Boolean.TRUE
                    newNMDConstruction.nmdReuseAvailable = product.ONVZ_HGB_Toegestaan
                    newNMDConstruction.nmdReuseFactor = product.ONVZ_HGB_Factor
                    newNMDConstruction.hideConstituentImpacts = newNMDConstruction.nmdCategoryId in [Constants.NmdCategory.ONE.category, Constants.NmdCategory.TWO.category]

                    Double additionalFactor = newNMDConstruction.nmdCategoryId == Constants.NmdCategory.THREE.category ? Constants.CAT3_ADDITIONAL_FACTOR_NMD30 : Constants.DEFAULT_ADDITIONAL_FACTOR_NMD30
                    Map<String, Object> additionalQuestionAnswers = [:]
                    additionalQuestionAnswers.put(Constants.ADDITIONAL_FACTOR_NMD30_QUESTIONID, additionalFactor)
                    additionalQuestionAnswers.put(Constants.NMD_CATEGORY_ID_QUESTIONID, newNMDConstruction.nmdCategoryId)
                    newNMDConstruction.additionalQuestionAnswers = additionalQuestionAnswers

                    extractProductType(product, newNMDConstruction)
                    extractProductApplication(product, newNMDConstruction, updateRecord)
                    extractUnitForNewNmdConstruction(product, nmdElementList, newNMDConstruction, updateRecord)
                    extractConstituentsOfProduct(product, isOuderProduct, productProductVersies, productElementProfielset, profielsetVersies, newNMDConstruction, updateRecord)
                    constructionService.addConstructionGroupToConstruction(newNMDConstruction, nmdApiGroup)

                    // we define in config some characters to be replaced in some construction attribute
                    constructionService.updateStringFieldsInConstruction(updateStringConfig, newNMDConstruction)

                    resourceRecordsAsString += existingConstructionsDeactivated ? "UPDATE " : "NEW "
                    if (newNMDConstruction.validate()) {
                        newNMDConstruction = newNMDConstruction.save(flush: true)
                    } else {
                        updateRecord?.tempConstructionErrors = "${updateRecord?.tempConstructionErrors ?: ''} Failed to save constructionId ${constructionId} - name: ${newNMDConstruction.nameEN}. ${newNMDConstruction.getErrors()?.getAllErrors()} <br />"
                    }
                    // Discussed w Arturs and decided to always update the mirror resource ticket REL-293
                    if (newNMDConstruction.id) {
                        // REL-291 we always create new mirror resource now since we always create new construction
                        newNMDConstruction = createNmdMirrorResource(newNMDConstruction, resourceParameters, persistingListProperties, persistingRegularProperties, updateRecord)
                    }
                    constructionCount++
                    resourceRecordsAsString = "${resourceRecordsAsString} Construction ${constructionCount}:${newNMDConstruction.id} nmdId: ${newNMDConstruction.constructionId} - Name ${newNMDConstruction.nameEN} resourceIds: ${newNMDConstruction.resourceIds} IsElementDekkend: ${product.IsElementDekkend}  <br />"
                    log.info("NMD Construction update prog: ${i} / ${size}")
                    i++
                }
            }
        } catch (e) {
            loggerUtil.error(log, "ERROR in handleNewNMDjsonToDB", e)
        }
        updateRecord?.tempNewUpdates = "${updateRecord?.tempNewUpdates ?: ''}${resourceRecordsAsString}"
        return resourceRecordsAsString
    }

    Map<Integer, List<NmdProfielMilieueffect>> getProfielMilieueffectMap(List<NmdProfielMilieueffect> profielMilieueffectVersies) {
        Map<Integer, List<NmdProfielMilieueffect>> profielMilieueffectVersiesMap = [:]

        // Map these for performance
        profielMilieueffectVersies?.each {
            if (it?.ProfielID) {
                List<NmdProfielMilieueffect> existing = profielMilieueffectVersiesMap.get(it.ProfielID)

                if (existing) {
                    existing.add(it)
                } else {
                    existing = [it]
                }
                profielMilieueffectVersiesMap.put((it.ProfielID), existing)
            }
        }
        return profielMilieueffectVersiesMap
    }

    Map<String, Map<String, Double>> getImpactsForNmdResource(NmdProfielSet resourceJson, List<NmdProfiel> profielVersies, Map<Integer, List<NmdProfielMilieueffect>> profielMilieueffectVersiesMap, Resource resourceNmd, NmdUpdate updateRecord) {
        if (!resourceJson || profielVersies?.isEmpty() || profielMilieueffectVersiesMap?.isEmpty() || !resourceNmd) {
            return null
        }

        List<NmdProfiel> resourceImpacts = profielVersies.findAll { it.ProfielSetID == resourceJson.ProfielSetID }
        Map<String, String> nmdFaseIdToOCLStages = getNmdFaseIdToOCLStages()
        Map<String, String> nmdMillieuCategoryIdToOCLImpactName = getNmdMillieuCategoryIdToOCLImpactName()

        Map<String, Map<String, Double>> impacts = [:]
        if (resourceImpacts) {
            resourceImpacts.each { map ->
                Integer faseId = map.FaseID
                if (nmdFaseIdToOCLStages?.containsKey(faseId?.toString())) {
                    String stageId = nmdFaseIdToOCLStages?.get(faseId?.toString())
                    List<NmdProfielMilieueffect> impactsPerStage = profielMilieueffectVersiesMap.get(map.ProfielID)
                    Map<String, Double> impactValueMap = [:]

                    impactsPerStage?.each { NmdProfielMilieueffect impactMap ->
                        Double value = impactMap.Waarde ?: 0
                        if (impactMap.MilieuCategorieID) {
                            String impactName = nmdMillieuCategoryIdToOCLImpactName?.get(impactMap.MilieuCategorieID?.toString())
                            if (impactName) {
                                impactValueMap.put(impactName, value)
                                if ("A1-A3".equals(stageId)) {
                                    try {
                                        DomainObjectUtil.callSetterByAttributeName(impactName, resourceNmd, value, true)
                                    } catch (NoSuchMethodException e) {
                                        loggerUtil.error(log, "oops, can't save impact ${impactName} due to:", e)
                                        flashService.setErrorAlert("oops, can't save impact ${impactName} due to: ${e.getMessage()}", true)
                                    }

                                }
                            } else {
                                updateRecord?.tempMilieuCategorieIdErrors = "${updateRecord?.tempMilieuCategorieIdErrors ?: ''} resourceId ${resourceNmd?.resourceId} - MilieuCategorieID ${impactMap.MilieuCategorieID} <br />"
                                nmdApiService.addFaultyMilieuCategorieID(updateRecord, impactMap.MilieuCategorieID)
                            }
                        }
                    }
                    impacts.put(stageId, impactValueMap)
                } else {
                    updateRecord?.tempFaseIdErrors = "${updateRecord?.tempFaseIdErrors ?: ''} resourceId ${resourceNmd?.resourceId} - FaseID ${faseId} <br />"
                    nmdApiService.addFaultyFaseID(updateRecord, faseId)
                }
            }
        } else {
            String impactNameDefault = nmdMillieuCategoryIdToOCLImpactName?.get(DEFAULT_MILLIEUCATEGORYID)
            Double valueDefault = 0
            DomainObjectUtil.callSetterByAttributeName(impactNameDefault, resourceNmd, valueDefault, true)
            String stageIdDefault = nmdFaseIdToOCLStages?.get(DEFAULT_FASEID)
            impacts.put(stageIdDefault, [(impactNameDefault): valueDefault])
        }

        return impacts
    }

    void extractElementInfoForNmdResource(NmdProfielSet resourceJson, List<NmdProductElementProfielSet> productElementProfielset, Resource resourceNmd) {
        if (!resourceJson || productElementProfielset?.isEmpty() || !resourceNmd) {
            return
        }

        NmdProductElementProfielSet pep = productElementProfielset.find { it.ProfielSetID == resourceJson.ProfielSetID }
        if (pep?.ElementID != null) {
            NmdElement element = nmdElementService.getNmdElement(pep.ElementID)
            resourceNmd.nmdElementCode = element?.code ?: ""
            resourceNmd.nmdElementnaam = element?.name ?: ""
            resourceNmd.nmdElementId = pep.ElementID.toString()
        }
    }

    void extractUnitForNmdResource(NmdProfielSet resourceJson, Resource resourceNmd, String resourceId, NmdUpdate updateRecord) {
        if (!resourceJson || !resourceNmd) {
            return
        }
        Integer unitId = resourceJson.FunctioneleEenheidID
        Integer scalingUnitId = resourceJson.EenheidID_SchalingsMaat
        Map<String, String> nmdUnitToOcl = getNmdUnitToOCL()
        if (unitId != null) {
            if (nmdUnitToOcl?.containsKey(unitId.toString())) {
                resourceNmd.unitForData = nmdUnitToOcl?.get(unitId.toString())
                if (Constants.TRANSPORTATION_DGBC_UNIT_GROUP.contains(unitId)) {
                    resourceNmd.resourceGroup = [Constants.TRANSPORTATION_DGBC]
                } else {
                    resourceNmd.resourceGroup = [Constants.NMD_BASIC]
                }
            } else {
                // weird unit, add fallback unit and log!
                resourceNmd.unitForData = Constants.NMD_UNIT_FALLBACK
                updateRecord?.tempUnitErrors = "${updateRecord?.tempUnitErrors ?: ''} ResourceId ${resourceId} - UnitId ${unitId} <br />"
                nmdApiService.addFaultyUnitId(updateRecord, unitId)
            }
        }
        if (scalingUnitId && scalingUnitId > 0) {
            resourceNmd.nmdSchalingUnit = nmdUnitToOcl?.get(scalingUnitId.toString())
        }
    }

    void extractConstituentsOfProduct(NmdProduct product, Boolean isOuderProduct, List<NmdProductProduct> productProductVersies,
                                      List<NmdProductElementProfielSet> productElementProfielset, Set<NmdProfielSet> profielsetVersies,
                                      Construction newNMDConstruction, NmdUpdate updateRecord) {
        List<String> resourceIds = []
        List<Dataset> datasets = []
        Set<Integer> kindElementIds = []
        // "kind" is "child" in dutch
        List<NmdProductElementProfielSet> kindPeps = getKindProductElementProfielsOfProduct(product, isOuderProduct, productProductVersies, productElementProfielset, updateRecord)
        Map<String, String> nmdUnitToOcl = getNmdUnitToOCL()

        for (NmdProductElementProfielSet kindProductElementProfiel in kindPeps) {
            if (!kindProductElementProfiel) {
                // to be safe
                continue
            }

            kindElementIds.add(kindProductElementProfiel.ElementID)

            // capture nothing else for free riders and the ones without ProfielSetID (same as no resourceId)
            if (isFreeRider(kindProductElementProfiel) || kindProductElementProfiel.ProfielSetID == null) {
                continue
            }

            String resourceId = Constants.NMD_RESOURCEID_PREFIX + kindProductElementProfiel.ProfielSetID

            Dataset newConstituent = new Dataset()
            newConstituent.resourceId = resourceId
            newConstituent.profileId = Resource.NMD30
            newConstituent.queryId = Query.QUERYID_CONSTRUCTION_CREATOR
            newConstituent.sectionId = Constants.CONSTRUCTION_COMPONENTS_SECTIONID
            newConstituent.questionId = Constants.CONSTRUCTION_COMPONENTS3_QUESTIONID_NMD
            newConstituent.manualId = new ObjectId().toString()
            newConstituent.quantity = kindProductElementProfiel.Hoeveelheid
            newConstituent.answerIds = newConstituent.quantity != null ? [newConstituent.quantity?.toString()] : null

            Integer userGivenUnit = profielsetVersies?.find { it?.ProfielSetID == kindProductElementProfiel.ProfielSetID }?.FunctioneleEenheidID
            if (userGivenUnit != null) {
                if (nmdUnitToOcl?.containsKey(userGivenUnit.toString())) {
                    newConstituent.userGivenUnit = nmdUnitToOcl?.get(userGivenUnit.toString())
                } else {
                    // weird unit, fallback to 'unit' and log!
                    updateRecord?.tempUnitErrors = "${updateRecord?.tempUnitErrors ?: ''} child product (constituent, resourceId: ${resourceId}, parent productID: ${product.ProductID}) - UnitId ${userGivenUnit} <br />"
                    nmdApiService.addFaultyUnitId(updateRecord, userGivenUnit)
                    newConstituent.userGivenUnit = Constants.NMD_UNIT_FALLBACK
                }
            }

            Map<String, Object> additionalQuestionAnswers = [:]
            Double serviceLife = profielsetVersies?.find { it?.ProfielSetID == kindProductElementProfiel.ProfielSetID }?.Levensduur
            if (serviceLife) {
                additionalQuestionAnswers.put("serviceLife", serviceLife)
                additionalQuestionAnswers.put("profileId", Resource.NMD30)
            }
            newConstituent.additionalQuestionAnswers = additionalQuestionAnswers
            resourceIds.add(resourceId)
            datasets.add(newConstituent)
        }

        if (resourceIds) {
            newNMDConstruction.resourceIds = resourceIds
        }
        if (datasets) {
            newNMDConstruction.datasets = datasets
        }
        if (kindElementIds) {
            newNMDConstruction.nmdKindElementIds = kindElementIds.toList().sort()
        }

    }

    boolean isFreeRider(NmdProductElementProfielSet productElementProfiel) {
        if (!productElementProfiel) {
            return false
        }
        if (productElementProfiel.Hoeveelheid == -1 || productElementProfiel.ProfielSetID == -1) {
            return true
        }
        return false
    }

    List<NmdProductElementProfielSet> getKindProductElementProfielsOfProduct(NmdProduct product, Boolean isOuderProduct, List<NmdProductProduct> productProductVersies,
                                                                             List<NmdProductElementProfielSet> productElementProfielset, NmdUpdate updateRecord) {
        List<NmdProductElementProfielSet> kindPeps = []

        if (!product?.IsElementDekkend && isOuderProduct) {
            List<Integer> kindProductIDs = productProductVersies?.findAll({ it.OuderProductID == product.ProductID })?.collect({ it.KindProductID })
            kindProductIDs?.each { Integer kindProductId ->
                List<NmdProductElementProfielSet> childPeps = productElementProfielset?.findAll { it.ProductID == kindProductId }
                if (childPeps) {
                    kindPeps.addAll(childPeps)
                }
            }
        } else if (product?.IsElementDekkend) {
            kindPeps = productElementProfielset?.findAll { it.ProductID == product.ProductID }
            // must always have kindPeps from this productElementProfielset table, if not then it's an error from NMD side >>> log!
            if (!kindPeps) {
                updateRecord?.tempConstructionErrors = "${updateRecord?.tempConstructionErrors ?: ''} Product (${product.ProductID}) IsElementDekkend ${product.IsElementDekkend}, but it doesn't have any constituents resourceIds in NMD_Product_Element_ProfielSet_Versies <br/>"
            }
        }
        return kindPeps
    }

    void extractProductType(NmdProduct product, Construction newNMDConstruction) {
        String productType = null
        if (product?.IsElementDekkend != null) {
            if (product.IsElementDekkend) {
                productType = Constants.TOTAAL_PRODUCT
            } else {
                productType = Constants.DEEL_PRODUCT
            }
        }
        newNMDConstruction?.nmdProductType = productType
    }

    void extractProductApplication(NmdProduct product, Construction newNMDConstruction, NmdUpdate updateRecord) {
        Integer toePassingId = product?.ToepassingID
        if (toePassingId != null && newNMDConstruction) {
            String application = getNmdToepassingsIdToApplication()?.get(toePassingId?.toString())
            if (application) {
                newNMDConstruction.nmdProductApplication = application
            } else {
                // we have an id that we don't know about, put it in directly
                newNMDConstruction.nmdProductApplication = toePassingId?.toString()
                updateRecord?.tempToepassingIdErrors = "${updateRecord?.tempToepassingIdErrors ?: ''} ProductID: ${product.ProductID} - ToepassingID: ${toePassingId}<br />"
                nmdApiService.addFaultyToepassingID(updateRecord, toePassingId)
            }
        }
    }

    /**
     * Get the unit and alternative unit from API response
     * @param product an object in NMD_Product_Versies table
     * @param nmdElementList
     * @param newNMDConstruction
     * @param updateRecord
     */
    void extractUnitForNewNmdConstruction(NmdProduct product, List<Document> nmdElementList, Construction newNMDConstruction, NmdUpdate updateRecord) {
        Integer unitId = product?.FunctioneleEenheidID
        Map<String, String> nmdUnitToOcl = getNmdUnitToOCL()
        String mainUnit = unitId ? nmdUnitToOcl?.get(unitId.toString()) : null

        if (unitId != null && mainUnit == null) {
            // log unit that we don't recognize
            updateRecord?.tempUnitErrors = "${updateRecord?.tempUnitErrors ?: ''} ProductID: ${product?.ProductID} - unitId: ${unitId}<br />"
            nmdApiService.addFaultyUnitId(updateRecord, unitId)
        }

        // look for alternative unit from nmdElement (object from NMD_Element_Versies table)
        if (newNMDConstruction?.nmdElementId?.isNumber()) {
            Document nmdElement = nmdElementList?.find { newNMDConstruction.nmdElementId?.toInteger() == it.elementId }
            newNMDConstruction.nmdElementCode = nmdElement?.code ?: ""
            newNMDConstruction.nmdElementnaam = nmdElement?.name ?: ""

            Integer alternativeUnitId = nmdElement?.unitId
            boolean hasAlternativeUnit = false

            if (alternativeUnitId) {
                if (mainUnit == null) {
                    // failover to the alternative unit from element. Nmd construction has only 1 unit
                    mainUnit = nmdUnitToOcl?.get(alternativeUnitId.toString())
                } else {
                    // nmd construction has multiple units if we can find a conversion factor
                    Double conversionFactor = product?.ELPR_Factor

                    if (conversionFactor && conversionFactor != Constants.INVALID_ELPR_Factor) {
                        String alternativeUnit = nmdUnitToOcl?.get(alternativeUnitId.toString())

                        if (alternativeUnit && alternativeUnit != mainUnit) {
                            newNMDConstruction.combinedUnits = [mainUnit, alternativeUnit]
                            newNMDConstruction.nmdUnitConversionFactor = conversionFactor
                            newNMDConstruction.nmdAlternativeUnit = alternativeUnit
                            hasAlternativeUnit = true
                        }
                    }
                }

                if (!nmdUnitToOcl?.containsKey(alternativeUnitId.toString())) {
                    // log unit that we don't recognize
                    updateRecord?.tempUnitErrors = "${updateRecord?.tempUnitErrors ?: ''} ElementID: ${nmdElement?.elementId} - unitId: ${alternativeUnitId}<br />"
                    nmdApiService.addFaultyUnitId(updateRecord, alternativeUnitId)
                }
            }

            if (!hasAlternativeUnit) {
                // construction from API doesn't have alternative unit, update it to our db
                newNMDConstruction.combinedUnits = null
                newNMDConstruction.nmdUnitConversionFactor = null
                newNMDConstruction.nmdAlternativeUnit = null
            }
        }

        newNMDConstruction?.unit = mainUnit ?: Constants.NMD_UNIT_FALLBACK
    }

    String handleDeactiveNMDjsonToDB(NmdDeactivateResources json, ConstructionGroup nmdApiGroup, NmdUpdate updateRecord = null) {
        if (!json) {
            return ''
        }
        String updateString = ''
        List<NmdProduct> productVersies = json.NMD_Product_Versies?.findAll { it != null }
        try {
            if (productVersies) {
                updateString = "<b>DEACTIVATE CONSTRUCTION:</b> <br />"
                Integer constructionCount = 0
                productVersies.each { NmdProduct product ->
                    String constructionId = product.ProductID?.toString()
                    List<Construction> constructions = constructionService.getConstructionsByConstructionId(constructionId)
                    if (constructions) {
                        for (Construction constructionToDeactivate in constructions) {
                            constructionService.addConstructionGroupToConstruction(constructionToDeactivate, nmdApiGroup)
                            Boolean deactivateSuccess = constructionService.deactivateConstruction(constructionToDeactivate)
                            if (deactivateSuccess) {
                                constructionCount++
                                updateString = "${updateString} Construction ${constructionCount}: ${constructionToDeactivate?.id?.toString()} - Name ${constructionToDeactivate.nameEN} <br />"
                            } else {
                                updateRecord.tempDeactivateConstructionsErrors = "${updateRecord.tempDeactivateConstructionsErrors ?: ''} FAILED WHILE SAVING ${constructionToDeactivate.nameEN} ID: ${constructionToDeactivate?.id?.toString()} <br />"
                            }
                        }
                    } else {
                        updateRecord.tempDeactivateConstructionsNotFound = "${updateRecord.tempDeactivateConstructionsNotFound ?: ''} CONSTRUCTIONID / ProductID ${constructionId} NAME: ${product.ProductNaam} <br />"
                    }
                }
            }
        } catch (e) {
            loggerUtil.error(log, "ERROR in handleDeactiveNMDjsonToDB", e)
        }

        updateRecord?.tempNewUpdates = "${updateRecord?.tempNewUpdates ?: ''}${updateString}"
        return updateString
    }

    /**
     * For creating NMD construction mirror resource from NMD API
     * @param construction
     * @param resourceParameters
     * @param persistingListProperties
     * @param persistingRegularProperties
     * @param updateRecord
     * @return
     */
    Construction createNmdMirrorResource(Construction construction, Map<String, String> resourceParameters, List<String> persistingListProperties, List<String> persistingRegularProperties, NmdUpdate updateRecord) {
        if (!construction) {
            return null
        }

        construction.importFile = Constants.NMD_3_API

        try {
            constructionService.createConstructionResource(construction, resourceParameters, persistingListProperties, persistingRegularProperties, true)
        } catch (e) {
            loggerUtil.warn(log, "ERROR in createNmdMirrorResource for construction: ${construction.id} NMDId:${construction.nmdElementId}", e)
            updateRecord?.tempConstructionErrors = "${updateRecord?.tempConstructionErrors ?: ''} Failed to create mirror resource for construction constructionId ${construction.constructionId} - name: ${construction.nameEN}. ${e.message} <br />"
        }

        return construction
    }

    /**
     * Get config for updating some characters in NMD resource / construction on import (via excel or API)
     * @return config
     */
    @Cacheable(Constants.GET_UPDATE_STRING_ON_IMPORT_NMD)
    Map<String, List<Map<String, String>>> getUpdateStringOnImportNmd() {
        return applicationService.getNmdApplication()?.updateStringOnImport
    }

    /**
     * Get config translating Nmd units to OCL unit
     * @return config
     */
    @Cacheable(Constants.GET_NMD_UNIT_TO_OCL)
    Map<String, String> getNmdUnitToOCL() {
        return applicationService.getNmdApplication()?.nmdUnitToOCL
    }

    /**
     * Get config translating FaseID to stages
     * @return config
     */
    @Cacheable(Constants.GET_NMD_FASE_ID_TO_OCLSTAGES)
    Map<String, String> getNmdFaseIdToOCLStages() {
        return applicationService.getNmdApplication()?.nmdFaseIdToOCLStages
    }

    /**
     * Get config translating ToepassingsID to product application
     * @return config
     */
    @Cacheable(Constants.GET_NMD_TOEPASSINGS_ID_TO_APPLICATION)
    Map<String, String> getNmdToepassingsIdToApplication() {
        return applicationService.getNmdApplication()?.nmdToepassingsIdToApplication
    }

    /**
     * Get config translating MillieuCategoryId to impacts
     * @return config
     */
    @Cacheable(Constants.GET_NMD_MILLIEU_CATEGORY_ID_TO_OCLIMPACT_NAME)
    Map<String, String> getNmdMillieuCategoryIdToOCLImpactName() {
        return applicationService.getNmdApplication()?.nmdMillieuCategoryIdToOCLImpactName
    }

    @Cacheable(Constants.GET_NMD_ELEMENT_TYPES)
    List<String> getNmdElementTypes() {
        return applicationService.getNmdApplication()?.nmdElementTypes
    }

    /**
     * Append the category of NMD resource to name
     * @param resource
     * @param name
     * @return
     */
    String appendCategoryAndNmdCodeToName(Document resource, String name) {
        if (!resource) {
            return name
        }
        String nmdCategory = resource.nmdCategoryId // only NMD resources have this
        String nmdCode = null

        if (resource.nmdElementId?.isInteger()) {
            NmdElement nmdElement = nmdElementService.getNmdElement(resource.nmdElementId?.toInteger())
            if (nmdElement) {
                nmdCode = nmdElement.code
            }
        }

        return appendCategorieAndCodeToName(nmdCategory, name, nmdCode)
    }

    String appendCategoryAndNmdCodeToName(Resource resource, String name) {
        if (!resource) {
            return name
        }
        String nmdCategory = resource.nmdCategoryId // only NMD resources have this
        String nmdCode = null

        if (resource.nmdElementId?.isNumber()) {
            NmdElement nmdElement = nmdElementService.getNmdElement(resource.nmdElementId?.toInteger())
            if (nmdElement) {
                nmdCode = nmdElement.code
            }
        }

        return appendCategorieAndCodeToName(nmdCategory, name, nmdCode)
    }

    String appendCategorieAndCodeToName(String nmdCategory, String name, String elementCode) {
        if (!name) {
            return name
        }

        if (nmdCategory || elementCode) {
            name += ' ('
        }

        if (elementCode) {
            name = "${name}${stringUtilsService.getLocalizedText('element')}: ${elementCode}"
        }

        if (nmdCategory) {
            if (elementCode) {
                name += ', '
            }
            name = "${name}${stringUtilsService.getLocalizedText('category')} ${nmdCategory}"
        }

        if (nmdCategory || elementCode) {
            name += ')'
        }

        return name
    }

    /**
     * Generally we do not need to do this, however we do have duplicated NMD resources for unknown reasons, hence run a check to deactivate the duplicates if any
     * @param resourceNmd
     */
    void deactivateDuplicatedNmdResources(Resource resourceNmd) {
        if (!resourceNmd?.id) {
            return
        }

        ObjectId onlyActiveId = resourceNmd.id
        List<Document> duplicates = Resource.collection.find([resourceId: resourceNmd.resourceId, profileId: resourceNmd.profileId, active: true], [_id: 1, active: 1])?.toList()

        if (!duplicates) {
            return
        }

        List<ObjectId> toDeactivate = duplicates.collect { it._id } as List<ObjectId>
        toDeactivate.remove(onlyActiveId)

        if (!toDeactivate) {
            return
        }

        Resource.collection.updateMany([_id: [$in: toDeactivate]], [$set: [active: false]])
    }

    @Cacheable(Constants.GET_KNOWN_NMD_UNITS)
    Set<Integer> getKnownNmdUnits() {
        return getNmdUnitToOCL()?.keySet()?.findAll { it?.isInteger() }?.collect { it.toInteger() }
    }

    @Cacheable(Constants.GET_KNOWN_FASE_IDS)
    Set<Integer> getKnownFaseIds() {
        return getNmdFaseIdToOCLStages()?.keySet()?.findAll { it?.isInteger() }?.collect { it.toInteger() }
    }

    @Cacheable(Constants.GET_KNOWN_TOEPASSING_IDS)
    Set<Integer> getKnownToepassingIds() {
        return getNmdToepassingsIdToApplication()?.keySet()?.findAll { it?.isInteger() }?.collect { it.toInteger() }
    }

    @Cacheable(Constants.GET_KNOWN_MILIEU_CATEGORIE_IDS)
    Set<Integer> getKnownMilieuCategorieIds() {
        return getNmdMillieuCategoryIdToOCLImpactName()?.keySet()?.findAll { it?.isInteger() }?.collect { it.toInteger() }
    }
}
