/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.core.service

import com.bionova.optimi.calculation.cache.EolProcessCache
import com.bionova.optimi.construction.Constants
import com.bionova.optimi.core.Constants.QueryCalculationMethod
import com.bionova.optimi.core.domain.mongo.Account
import com.bionova.optimi.core.domain.mongo.AccountImages
import com.bionova.optimi.core.domain.mongo.CalculationResult
import com.bionova.optimi.core.domain.mongo.Construction
import com.bionova.optimi.core.domain.mongo.CostStructure
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.DatasetImportFields
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.EntityFile
import com.bionova.optimi.core.domain.mongo.EolProcess
import com.bionova.optimi.core.domain.mongo.Feature
import com.bionova.optimi.core.domain.mongo.ImportMapper
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.License
import com.bionova.optimi.core.domain.mongo.NmdElement
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.QueryLastUpdateInfo
import com.bionova.optimi.core.domain.mongo.QuerySection
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.QuestionAnswerChoice
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ResourceType
import com.bionova.optimi.core.domain.mongo.SpecialRule
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.data.ResourceCache
import com.bionova.optimi.frenchTools.FrenchConstants
import com.bionova.optimi.util.JavaScriptEngineManager
import com.bionova.optimi.util.UnitConversionUtil
import com.mongodb.BasicDBObject
import grails.core.GrailsApplication
import grails.plugin.springsecurity.annotation.Secured
import grails.web.servlet.mvc.GrailsParameterMap
import groovy.transform.CompileStatic
import org.apache.commons.lang.StringUtils
import org.bson.Document
import org.bson.types.ObjectId
import org.grails.plugins.web.taglib.RenderTagLib
import org.grails.web.servlet.mvc.GrailsWebRequest
import org.grails.web.util.WebUtils
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.core.io.ByteArrayResource

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession
import java.text.DecimalFormat

/**
 * @author Pasi-Markus Mäkelä
 */
class DatasetService {

    static transactional = "mongo"

    def questionAnswerChoiceService
    def indicatorService
    def taskService
    def queryService
    def newCalculationServiceProxy
    def questionService
    def loggerUtil
    def messageSource
    def optimiResourceService
    def configurationService
    def channelFeatureService
    def userService
    def applicationService
    def errorMessageUtil
    def fileUtil
    def costStructureService
    def unitConversionUtil
    def constructionService
    def domainClassService
    def resourceTypeService
    def localeResolverUtil
    def eolProcessService
    def stringUtilsService
    def accountImagesService
    FlashService flashService
    GrailsApplication grailsApplication
    def groovyPageRenderer
    def resourceFilterCriteriaUtil
    def licenseService
    def productDataListService
    def querySectionService
    def valueReferenceService
    def entityService
    def importMapperService
    NmdElementService nmdElementService
    def importMapperTrainingDataService
    ResourceService resourceService
    def assetResourceLocator


    @Secured(["ROLE_AUTHENTICATED"])
    def Dataset getUniqueDataset(Entity entity, String queryId, String sectionId, String questionId, String resourceId, String copiedUniqueConstructionIdentifier = null) {
        if (resourceId) {
            if (copiedUniqueConstructionIdentifier) {
                return entity?.datasets?.find({ d ->
                    d.queryId == queryId && d.sectionId == sectionId && d.questionId == questionId && d.resourceId == resourceId && d.uniqueConstructionIdentifier == copiedUniqueConstructionIdentifier
                })
            } else {
                return entity?.datasets?.find({ d ->
                    d.queryId == queryId && d.sectionId == sectionId && d.questionId == questionId && d.resourceId == resourceId
                })
            }
        } else {
            if (copiedUniqueConstructionIdentifier) {
                return entity?.datasets?.find({ d ->
                    d.queryId == queryId && d.sectionId == sectionId && d.questionId == questionId && d.uniqueConstructionIdentifier == copiedUniqueConstructionIdentifier
                })
            } else {
                return entity?.datasets?.find({ d ->
                    d.queryId == queryId && d.sectionId == sectionId && d.questionId == questionId
                })
            }
        }
    }

    @Secured(["ROLE_AUTHENTICATED"])
    Dataset getUniqueDatasetByEntityAndManualId(Entity entity, String manualId) {
        return entity?.datasets?.find({ it.manualId == manualId })
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getDatasetsForResources(Entity entity, queryId, sectionId, questionId, List resourceIds) {
        return entity?.datasets?.findAll({ d ->
            d.queryId == queryId && d.sectionId == sectionId && d.questionId == questionId && resourceIds?.contains(d.resourceId)
        })
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getDatasetsByEntity(Entity entity) {
        return entity?.datasets
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getDatasetsByEntityAndQueryIdAndSectionId(Entity entity, queryId, sectionId) {
        return entity?.datasets?.findAll({ d ->
            d.queryId == queryId && d.sectionId == sectionId
        })
    }

    Set<Dataset> getDatasetsByEntityAndQueryIdAndSectionIds(Entity entity, String queryId, List<String> sectionIds) {
        if (entity && queryId && sectionIds) {
            return entity?.datasets?.findAll({ Dataset d ->
                d.queryId == queryId && sectionIds.contains(d.sectionId)
            })
        }
        return []
    }

    List<Dataset> getDatasetsByEntityQueryIdSectionIdAndQuestion(Entity entity, queryId, sectionId, questionId) {
        List<Dataset> datasets = []

        if (entity && queryId && sectionId && questionId) {
            Set<Dataset> foundDatasets = entity?.datasets?.findAll({ d ->
                d.queryId.equals(queryId) && d.sectionId.equals(sectionId) && d.questionId.equals(questionId)
            })

            if (foundDatasets) {
                datasets = new ArrayList<Dataset>(foundDatasets)
            }
        }

        return datasets
    }

    @CompileStatic
    Dataset findMatchedDataset(Set<Dataset> datasets, Dataset targetDataset) {
        if (!targetDataset || !datasets) {
            return null
        }

        return datasets.find{ Dataset d ->
            d.queryId == targetDataset.queryId && d.sectionId == targetDataset.sectionId && d.questionId == targetDataset.questionId
        }
    }

    /**
     * Attach the answer from the first dataset with the second dataset and add it to the second dataset
     *
     * @param from
     * @param to
     */
    @CompileStatic
    void mergeAnswerOfTwoDatasets(Dataset from, Dataset to) {
        if (!from || !to) {
            return
        }

        String head = getAnswerFromDataset(from) ?: ''
        String tail = getAnswerFromDataset(to) ?: ''

        if (head && tail) {
            to.answerIds[0] = "${head} ${tail}"
        } else if (head) {
            to.answerIds = [head]
        }
    }

    String getAnswerIdOfDatasetByEntityQueryIdSectionIdQuestionId(Entity entity, String queryId, String sectionId, String questionId) {
        String answerId = ''
        if (entity && queryId && sectionId && questionId) {
            answerId = entity.datasets?.find { d ->
                d.queryId == queryId && d.sectionId == sectionId && d.questionId == questionId
            }?.answerIds?.getAt(0)
        }
        return answerId
    }


    @Secured(["ROLE_TASK_AUTHENTICATED"])
    Set<Dataset> getDatasetsByEntityAndQueryId(Entity entity, queryId) {
        if (entity && queryId) {
            // loggerUtil.debug(log, "Start of getDatasetsByEntityAndQueryId")
            Set<Dataset> datasets = entity?.datasets
            Set<Dataset> foundDatasets = datasets?.findAll({ d ->
                d.queryId == queryId
            })
            return foundDatasets
        }
    }

    @Secured(["ROLE_TASK_AUTHENTICATED"])
    Set<Dataset> getDatasetsByEntityAndQueryIdsList(Entity entity, List<String> queryIdsList) {
        if (entity && queryIdsList) {
            // loggerUtil.debug(log, "Start of getDatasetsByEntityAndQueryId")
            Set<Dataset> datasets = entity?.datasets
            Set<Dataset> foundDatasets = datasets?.findAll({ d ->
                queryIdsList.contains(d.queryId)
            })
            return foundDatasets
        }
    }


    @Secured(["ROLE_TASK_AUTHENTICATED"])
    List<Dataset> getDatasetsByEntityAndQueryIdAsDataset(Entity entity, queryId) {
        if (entity && queryId) {
            // loggerUtil.debug(log, "Start of getDatasetsByEntityAndQueryId")
            List<Dataset> datasets = entity?.datasets?.toList()
            List<Dataset> foundDatasets = datasets?.findAll({ d ->
                d.queryId == queryId
            })
            return foundDatasets
        }
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def copyDatasetsAndFiles(Entity originalEntity, Entity targetEntity) {
        if (originalEntity && targetEntity) {
            List<String> filePersistentProperties = domainClassService.getPersistentPropertyNamesForDomainClass(EntityFile.class)?.findAll({
                !"entityId".equals(it)
            })

            originalEntity.getFiles()?.each { EntityFile entityFile ->
                EntityFile copyFile = new EntityFile()
                copyFile.properties = entityFile.properties.subMap(filePersistentProperties)
                copyFile.entityId = targetEntity.id.toString()
                copyFile.save(flush: true)
            }
            def originalDatasets = originalEntity.datasets
            targetEntity.datasets = originalDatasets
            def originalQueryReady = originalEntity.queryReady
            def originalQueryReadyPerIndicator = originalEntity.queryReadyPerIndicator
            def defaults = originalEntity.defaults

            if (originalQueryReady) {
                targetEntity.queryReady = new LinkedHashMap<String, Boolean>(originalQueryReady)
            }
            if (originalQueryReadyPerIndicator) {
                targetEntity.queryReadyPerIndicator = new LinkedHashMap<String, Map<String, Boolean>>(originalQueryReadyPerIndicator)
            }
            if (defaults) {
                targetEntity.defaults = new LinkedHashMap<String, String>(defaults)
            }
            return targetEntity.merge(flush: true, failOnError: true)
        }
    }
    /**
     *
     * @param originalEntity
     * @param targetEntity
     * @param baseEntity
     * @param shareOfDesigns
     * @return
     *
     * Copies all the files and datasets from all the design to newly created average design
     */
    @Secured(["ROLE_AUTHENTICATED"])
    Entity copyDatasetsAndFilesForAverageDesign(Entity originalEntity, Entity targetEntity, Entity baseEntity , Map<String, Double> shareOfDesigns) {
        if (originalEntity && targetEntity && baseEntity && shareOfDesigns) {
            copyFiles(originalEntity, targetEntity)
            copyDatasets(baseEntity, targetEntity, originalEntity, shareOfDesigns)
            return targetEntity.merge(flush: true, failOnError: true)
        }
    }
    /**
     *
     * @param originalEntity
     * @param targetEntity
     *
     * Copy files to current design to new design
     */
    void copyFiles(Entity originalEntity, Entity targetEntity){
        List<String> filePersistentProperties = domainClassService.getPersistentPropertyNamesForDomainClass(EntityFile.class)?.findAll({ String it ->
            ! com.bionova.optimi.core.Constants.ENTITY_ID.equals(it)
        })
        originalEntity.getFiles()?.each { EntityFile entityFile ->
            EntityFile copyFile = new EntityFile()
            copyFile.properties = entityFile.properties.subMap(filePersistentProperties)
            copyFile.entityId = targetEntity.id.toString()
            copyFile.save(flush: true)
        }
    }
    /**
     *
     * @param baseEntity
     * @param targetEntity
     * @param originalEntity
     * @param shareOfDesigns
     *
     * copy datasets from current design to new design
     * if current design is the base design copy all datasets to new design
     * else copy datasets from epdMaterials_preVerified and epdManufacturing_preVerified queries (A1-3 stages)
     */
    void copyDatasets(Entity baseEntity, Entity targetEntity, Entity originalEntity, Map<String, Double> shareOfDesigns) {
        Set<Dataset> originalDatasets = []
        Set<Dataset> datasetsToAdjustAllocation = []
        Double shareOfOriginalEntity
        if (originalEntity && baseEntity && targetEntity && shareOfDesigns) {

            if (shareOfDesigns.containsKey(originalEntity.id.toString())) {
                shareOfOriginalEntity = shareOfDesigns.get(originalEntity.id.toString())
            }
            if (shareOfOriginalEntity) {
                //case 1 all entities except base entity
                originalDatasets = returnFilteredDatasets(originalEntity, baseEntity)
                assignUniqueManualIdForAllDatasets(originalDatasets)
                if (originalEntity.id != baseEntity.id) {
                    adjustAllocationOfDatasets(originalDatasets, shareOfOriginalEntity)
                    targetEntity.datasets.addAll(originalDatasets)
                } else {
                    //case 2 base entity - the design in the entity page on which user clicked to create the avg design
                    datasetsToAdjustAllocation = datasetFilter(originalDatasets)
                    originalDatasets.removeAll(datasetsToAdjustAllocation)
                    adjustAllocationOfDatasets(datasetsToAdjustAllocation, shareOfOriginalEntity)
                    originalDatasets.addAll(datasetsToAdjustAllocation)
                    setDefaultsForAvgDesign(baseEntity, targetEntity)
                    targetEntity.datasets.addAll(originalDatasets)
                }
            }
        }
    }
    /**
     * we need only datasets from materials and manufacturing from designs other than the base entity
     * @param targetEntity
     */
    void removeOtherDatasets(Entity targetEntity) {
        Set<Dataset> datasetsToRemove = findRequiredDatasets(targetEntity)
        if (datasetsToRemove) {
            targetEntity.datasets?.removeAll(datasetsToRemove)
        }
    }
    /**
     * find RequiredDatasets from mentioned queries
     * @param entity
     * @return
     */
    Set<Dataset> findRequiredDatasets(Entity entity) {
        return entity.datasets?.findAll({ Dataset it ->
            !([com.bionova.optimi.core.Constants.EPDMATERIALS_PREVERIFIED,
               com.bionova.optimi.core.Constants.EPDMANUFACTURING_PREVERIFIED].contains(it?.queryId?.toString()))
        })
    }
    /**
     * assigns unique manualIds for all datasets going to the new average design , to avoid issues in calculation due
     * to duplicate manual ids
     * @param originalDatasets
     */
    void assignUniqueManualIdForAllDatasets(Set<Dataset> originalDatasets) {
        originalDatasets?.each { Dataset it ->
            if (it && (it.queryId.equals(com.bionova.optimi.core.Constants.EPDMATERIALS_PREVERIFIED)
                    || it.queryId.equals(com.bionova.optimi.core.Constants.EPDMANUFACTURING_PREVERIFIED))) {
                it.manualId = new ObjectId()
            }
        }
    }
    /**
     *
     * @param originalEntity
     * @param baseEntity
     * @return
     *
     * if the current entity is the base entity (the design from which user clicked the create avg design option)
     * then return all datasets
     * for any other design , send datasets from epdMaterials_preVerified and epdManufacturing_preVerified queries (A1-3 stages)
     */
    Set<Dataset> returnFilteredDatasets(Entity originalEntity, Entity baseEntity){
        return originalEntity.id != baseEntity.id ? datasetFilter(originalEntity.datasets) : originalEntity.datasets
    }
    /**
     *
     * @param datasetsToFilter
     * @return
     *
     * return datasets from epdMaterials_preVerified and epdManufacturing_preVerified queries (A1-3 stages)
     */
    Set<Dataset> datasetFilter(Set<Dataset> datasetsToFilter) {
        return datasetsToFilter?.findAll({ Dataset it -> [com.bionova.optimi.core.Constants.EPDMATERIALS_PREVERIFIED,
                                                          com.bionova.optimi.core.Constants.EPDMANUFACTURING_PREVERIFIED].contains(it?.queryId?.toString()) })
    }
    /**
     *
     * @param datasetsToAdjustAllocation
     * @param DoubleshareOfOriginalEntity
     *
     * set allocation value in each dataset using the share entered by user for each design
     */
    void adjustAllocationOfDatasets(Set<Dataset> datasetsToAdjustAllocation, Double shareOfOriginalEntity) {
        datasetsToAdjustAllocation.each { Dataset it ->
            setAverageAllocationFactor(it, shareOfOriginalEntity)
        }
    }

    /**
     *
     * @param datasetToAdjustAllocation
     * @param shareOfOriginalEntity
     *
     * set allocation value in each dataset
     * formula : (allocation share entered by user for this design * existing allocation value of the dataset)/100
     */
    void setAverageAllocationFactor(Dataset datasetToAdjustAllocation, double shareOfOriginalEntity) {
        Double currentAllocation
        double newAllocationValue
        if(datasetToAdjustAllocation.additionalQuestionAnswers?.get(com.bionova.optimi.core.Constants.PRODUCT_ALLOCATION)){
            currentAllocation = DomainObjectUtil.convertStringToDouble(datasetToAdjustAllocation.additionalQuestionAnswers.get(com.bionova.optimi.core.Constants.PRODUCT_ALLOCATION))
            newAllocationValue = (currentAllocation * shareOfOriginalEntity) / 100
            datasetToAdjustAllocation.additionalQuestionAnswers.put(com.bionova.optimi.core.Constants.PRODUCT_ALLOCATION, newAllocationValue?.toString())
        }
    }
    /**
     *
     * @param baseEntity
     * @param targetEntity
     *
     * Setting default values from base entity to target entity
     * base entity = (the design from which user clicked the create avg design option)
     */
    void setDefaultsForAvgDesign(Entity baseEntity, Entity targetEntity) {
        Map originalQueryReady = baseEntity.queryReady
        Map<String, Map<String, Boolean>> originalQueryReadyPerIndicator = baseEntity.queryReadyPerIndicator
        Map<String, String> defaults = baseEntity.defaults

        if (originalQueryReady) {
            targetEntity.queryReady = new LinkedHashMap<String, Boolean>(originalQueryReady)
        }
        if (originalQueryReadyPerIndicator) {
            targetEntity.queryReadyPerIndicator = new LinkedHashMap<String, Map<String, Boolean>>(originalQueryReadyPerIndicator)
        }
        if (defaults) {
            targetEntity.defaults = new LinkedHashMap<String, String>(defaults)
        }
    }

    def copyDatasetsByQueryId(String fromEntityId, String targetEntityId, String indicatorId, String queryId, Boolean calculate = false, Boolean mergeDataset = false) {
        Integer copyOk = 0
        if (fromEntityId && targetEntityId && queryId && indicatorId) {
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
            Entity fromEntity = Entity.get(fromEntityId)
            Entity targetEntity = Entity.get(targetEntityId)

            def toCopyDatasets = getDatasetsByEntityAndQueryId(fromEntity, queryId)
            def toDeleteDatasets = getDatasetsByEntityAndQueryId(targetEntity, queryId)
            List<ImportMapper> importMappers = applicationService.getApplicationByApplicationId("IFC")?.importMappers
            Integer skipCombineRowLimit = null
            if (indicator?.maxRowLimitPerDesign) {
                skipCombineRowLimit = indicator?.maxRowLimitPerDesign
            } else if(importMappers){
                skipCombineRowLimit = importMappers[0].skipCombineRowLimit
            }
            if (toCopyDatasets) {
                if(mergeDataset) {
                    Integer totalRowFound = toCopyDatasets.size() + (toDeleteDatasets?.size() ?: 0)
                    if(skipCombineRowLimit && totalRowFound && (totalRowFound < skipCombineRowLimit)){
                        if (targetEntity.datasets) {
                            toCopyDatasets.each { Dataset d1 ->
                                if (toDeleteDatasets.find({it.manualId?.equalsIgnoreCase(d1?.manualId)})){
                                    d1.manualId = new ObjectId().toString()
                                }
                                if (!((getQuestion(d1)?.preventDoubleEntries||["text","textarea","slider","checkbox","radio","file"].contains(getQuestion(d1)?.inputType)) && toDeleteDatasets.find({it.questionId?.equalsIgnoreCase(d1.questionId)}))) {
                                    // Dont add dataset if user selects merge and question prevents double entries
                                    targetEntity.datasets.add(d1)
                                }
                            }
                            targetEntity.datasets.unique({it.manualId})
                        } else {
                            targetEntity.datasets = toCopyDatasets
                        }

                        if (calculate) {
                            Entity parentEntity = targetEntity.parentById

                            if (parentEntity) {
                                List<Indicator> indicatorsToSave = indicatorService.getIndicatorsWithSameQuery(parentEntity, queryId)

                                indicatorsToSave?.each { Indicator ind ->
                                    targetEntity = newCalculationServiceProxy.calculate(null, ind.indicatorId, parentEntity, targetEntity)
                                }
                            }
                        }
                        targetEntity.merge(flush: true)
                        copyOk = 1
                    } else if (totalRowFound && (totalRowFound < skipCombineRowLimit)){
                        copyOk = 2
                    }
                }else {
                    if (toDeleteDatasets) {
                        targetEntity.datasets.removeAll(toDeleteDatasets)
                    }

                    if (targetEntity.datasets) {
                        targetEntity.datasets.addAll(toCopyDatasets)
                    } else {
                        targetEntity.datasets = toCopyDatasets
                    }

                    if (calculate) {
                        Entity parentEntity = targetEntity.parentById

                        if (parentEntity) {
                            List<Indicator> indicatorsToSave = indicatorService.getIndicatorsWithSameQuery(parentEntity, queryId)

                            indicatorsToSave?.each { Indicator ind ->
                                targetEntity = newCalculationServiceProxy.calculate(null, ind.indicatorId, parentEntity, targetEntity)
                            }
                        }
                    }
                    targetEntity.merge(flush: true)
                    copyOk = 1
                }
            } else {
                copyOk = 3
            }
        }
        return copyOk
    }

    /**
     *
     * @param fromEntityId the entityId to copy dataset from
     * @param targetEntityId the entityId to copy dataset to
     * @param indicatorId
     * @param queryIdsList
     * @param calculate
     * @param mergeDataset
     * @param overwriteExistingDataset
     * @param targetEntity can pass in the entity (if need to run this method multiple times without db call & save)
     * @param fromEntity can pass in the entity (if need to run this method multiple times without db call & save)
     * @param indicator
     * @param runEcoinventCheck
     */
    void copyDatasetsByQueryIdsList(String fromEntityId, String targetEntityId, String indicatorId, List<String> queryIdsList,
                                    Boolean calculate = false, Boolean mergeDataset = false, Boolean overwriteExistingDataset = false,
                                    Entity targetEntity = null, Entity fromEntity = null, Indicator indicator = null, Boolean runEcoinventCheck = null) {
        if (fromEntityId && targetEntityId && queryIdsList && indicatorId) {
            if (!indicator) {
                indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
            }

            if (!fromEntity) {
                fromEntity = Entity.get(fromEntityId)
            }

            if (!targetEntity) {
                targetEntity = Entity.get(targetEntityId)
            }

            Set<Dataset> toDeleteDatasets = getDatasetsByEntityAndQueryIdsList(targetEntity, queryIdsList)
            Set<Dataset> toCopyDatasets = getDatasetsByEntityAndQueryIdsList(fromEntity, queryIdsList)

            if (runEcoinventCheck) {
                boolean removedSomeDatasets = removeUnauthorizedEcoinventResourceDatasets(toCopyDatasets)
                if (removedSomeDatasets) {
                    flashService.setWarningAlert(stringUtilsService.getLocalizedText('copy.unauthorized.dataset.notSuccessful'))
                }
            }

            List<ImportMapper> importMappers = applicationService.getApplicationByApplicationId("IFC")?.importMappers
            Integer skipCombineRowLimit = null
            if (indicator?.maxRowLimitPerDesign) {
                skipCombineRowLimit = indicator?.maxRowLimitPerDesign
            } else if (importMappers) {
                skipCombineRowLimit = importMapperService.getLimitAccordingToUsedLicenseFeature(targetEntityId, indicator, importMappers[0].skipCombineRowLimit)
            }
            if (toCopyDatasets) {
                if (overwriteExistingDataset) {
                    if (targetEntity.datasets) {
                        List<String> questionsToGoTrough = toCopyDatasets.collect({ it.questionId }).unique()

                        if (questionsToGoTrough) {
                            queryIdsList.each { String queryId ->
                                questionsToGoTrough.each { String questionId ->
                                    Set<Dataset> datasetsFromTemplate = toCopyDatasets.findAll({ it.questionId == questionId && it.queryId == queryId })
                                    // copy if dataset exists in template and the existing ones must actually have words
                                    if (datasetsFromTemplate && datasetsFromTemplate.find({ (it.answerIds?.getAt(0) as String)?.trim() })) {
                                        targetEntity.datasets.removeIf({ it.questionId == questionId && it.queryId == queryId })
                                        targetEntity.datasets.addAll(datasetsFromTemplate)
                                    }
                                }
                            }
                        }

                    } else {
                        targetEntity.datasets = toCopyDatasets
                    }
                    if (calculate) {
                        Entity parentEntity = targetEntity.parentById

                        if (parentEntity) {
                            List<Indicator> indicatorsToSave = indicatorService.getIndicatorsWithSameQueries(parentEntity, queryIdsList)

                            indicatorsToSave?.each { Indicator ind ->
                                targetEntity = newCalculationServiceProxy.calculate(null, ind.indicatorId, parentEntity, targetEntity)
                            }
                        }
                    }
                    if (targetEntity.validate()) {
                        targetEntity.merge(flush: true)
                        flashService.setFadeSuccessAlert(stringUtilsService.getLocalizedText('query.copy_answers.ok'))
                    }
                } else if (mergeDataset) {
                    Integer totalRowFound = toCopyDatasets.size() + (toDeleteDatasets?.size() ?: 0)
                    if (skipCombineRowLimit && totalRowFound && (totalRowFound < skipCombineRowLimit)) {
                        if (targetEntity.datasets) {
                            toCopyDatasets.each { Dataset d1 ->
                                if (toDeleteDatasets.find({ it.manualId?.equalsIgnoreCase(d1?.manualId) })) {
                                    d1.manualId = new ObjectId().toString()
                                }
                                if (!((getQuestion(d1)?.preventDoubleEntries || ["text", "textarea", "slider", "checkbox", "radio", "file"].contains(getQuestion(d1)?.inputType)) && toDeleteDatasets.find({ it.questionId?.equalsIgnoreCase(d1.questionId) }))) {
                                    // Dont add dataset if user selects merge and question prevents double entries or question is text question
                                    targetEntity.datasets.add(d1)
                                }
                            }
                            targetEntity.datasets.unique({ it.manualId })
                        } else {
                            targetEntity.datasets = toCopyDatasets
                        }

                        if (calculate) {
                            Entity parentEntity = targetEntity.parentById

                            if (parentEntity) {
                                List<Indicator> indicatorsToSave = indicatorService.getIndicatorsWithSameQueries(parentEntity, queryIdsList)

                                indicatorsToSave?.each { Indicator ind ->
                                    targetEntity = newCalculationServiceProxy.calculate(null, ind.indicatorId, parentEntity, targetEntity)
                                }
                            }
                        }
                        if (targetEntity.validate()) {
                            targetEntity.merge(flush: true)
                            flashService.setFadeSuccessAlert(stringUtilsService.getLocalizedText('query.copy_answers.ok'))
                        }
                    } else if (totalRowFound && (totalRowFound >= skipCombineRowLimit)) {
                        flashService.setFadeErrorAlert(stringUtilsService.getLocalizedText('query.copy_answers.too_many_rows'))
                    }
                } else {
                    if (toDeleteDatasets) {
                        targetEntity.datasets.removeAll(toDeleteDatasets)
                    }
                    if (targetEntity.datasets) {
                        targetEntity.datasets.addAll(toCopyDatasets)
                    } else {
                        targetEntity.datasets = toCopyDatasets
                    }

                    if (calculate) {
                        Entity parentEntity = targetEntity.parentById

                        if (parentEntity) {
                            List<Indicator> indicatorsToSave = indicatorService.getIndicatorsWithSameQueries(parentEntity, queryIdsList)

                            indicatorsToSave?.each { Indicator ind ->
                                targetEntity = newCalculationServiceProxy.calculate(null, ind.indicatorId, parentEntity, targetEntity)
                            }
                        }
                    }

                    if (targetEntity.validate()) {
                        try {
                            targetEntity.merge(flush: true)
                            flashService.setFadeSuccessAlert(stringUtilsService.getLocalizedText('query.copy_answers.ok'))
                        } catch (IllegalArgumentException iaex) {
                            log.error("Error while saving an entity: ", iaex)
                            flashService.setFadeErrorAlert(stringUtilsService.getLocalizedText('query.copy_answers.error'))
                        }
                    } else {
                        log.error("Entity has errors: ${targetEntity.errors?.addAllErrors()}")
                    }
                }
            } else {
                flashService.setFadeErrorAlert(stringUtilsService.getLocalizedText('query.copy_answers.no_data_found'))
            }
        }
    }

    /**
     * Remove datasets that are linked with Ecoinvent resources that user is not authorized to access (if any)
     *
     * @param datasets
     * @return
     */
    boolean removeUnauthorizedEcoinventResourceDatasets(Set<Dataset> datasets) {
        boolean removedSome = false
        try {
            if (datasets) {
                boolean hasResourceDataset = datasets?.find { it.resourceId } ? true : false

                if (hasResourceDataset) {
                    optimiResourceService.loadResourceToDatasets(datasets)
                    ResourceCache resourceCache = ResourceCache.init(datasets.toList())
                    boolean hasEcoinventResourceDataset = datasets?.find { optimiResourceService.isEcoinventResource(resourceCache.getResource(it)) } ? true : false

                    if (hasEcoinventResourceDataset) {
                        Account account = userService.getAccount(userService.getCurrentUser(true))
                        List<String> licensedFeatureIds = licenseService.getLicensedFeatureIdsFromAccount(account)
                        boolean hasUncappedLicense = licenseService.hasEcoinventUncappedLicensed(licensedFeatureIds)

                        if (!hasUncappedLicense) {
                            List<String> resourceIdsInDataList = productDataListService.getResourceIdsInDataList(licensedFeatureIds, account)

                            datasets.removeIf { Dataset d ->
                                Resource resource = resourceCache.getResource(d)
                                if (resource) {
                                    boolean isEcoinventResource = optimiResourceService.isEcoinventResource(resource)

                                    if (isEcoinventResource) {
                                        boolean isAuthorized = optimiResourceService.isAuthorizedEcoinventResourceForUser(resource, hasUncappedLicense, resourceIdsInDataList)

                                        if (!isAuthorized) {
                                            removedSome = true
                                            return true // remove
                                        }
                                    }
                                }
                                return false // keep
                            }
                        }
                    }
                }
            }
        } catch (e) {
            loggerUtil.error(log, "Error in removeUnauthorizedEcoinventResourceDatasets", e)
            flashService.setErrorAlert("Error in removeUnauthorizedEcoinventResourceDatasets: ${e.getMessage()}", true)
        }
        return removedSome
    }

    /*
     *  To remove unneeded empty resource datasets (main question) from datasets caused by gsp rendering
     */

    List<Dataset> removeUnneededDuplicatesFromResourceDatasets(datasets) {
        // loggerUtil.debug(log, "Start of removeUnneededDuplicatesFromResourceDatasets")

        def cleanedDatasets = []
        datasets?.each { Dataset dataset ->
            def duplicates = datasets.findAll({ d ->
                d.queryId == dataset.queryId && d.sectionId == dataset.sectionId &&
                        d.questionId == dataset.questionId
            })

            if (duplicates?.size() > 1) {
                // Here we pick up datasets with valid resource, eg. has resourceId
                def datasetsWithResourceId = duplicates.findAll({ Dataset d -> d.resourceId != null })
                // It still might be the case that dataset we are handling is eg. dummy dataset
                // only holding resource and unit for real answers, so resourceId has to be
                // checked also here
                if (datasetsWithResourceId && dataset.resourceId) {
                    cleanedDatasets.add(dataset)
                }
            } else {
                cleanedDatasets.add(dataset)
            }
        }
        //loggerUtil.debug(log, "End of removeUnneededDuplicatesFromResourceDatasets")
        return cleanedDatasets
    }

    Entity preHandleDatasets(Entity entity, List<Dataset> datasets, List<Indicator> indicators, Boolean handleAdditionalQuestions,
                          Boolean storeUsedUnitSystem, Boolean temporaryCalculation, Boolean checkOnlyGeneratedFromImportDatasets = Boolean.FALSE) {
        if (!entity) {
            loggerUtil.error(log, "Error in saving dataset because entity is null")
            flashService.setErrorAlert("Error in saving dataset because entity is null", true)
            return
        }

        try {
            List<String> uniqueQueryIds = datasets?.collect({ it.queryId })?.unique()
            def saveableDatasets = []
            boolean answersFound
            User user = userService.getCurrentUser()
            if (!datasets?.isEmpty()) {
                String unitSystem = user?.unitSystem

                datasets?.each { Dataset dataset ->
                    if (storeUsedUnitSystem) {
                        dataset.usedUnitSystem = unitSystem ? unitSystem : UnitConversionUtil.UnitSystem.METRIC.value
                    }
                    answersFound = false

                    if (StringUtils.isNotBlank(dataset.answerIds?.get(0)) || dataset.resourceId || !dataset.monthlyAnswers?.isEmpty() || !dataset.quarterlyAnswers?.isEmpty()) {
                        answersFound = true
                    }

                    if (answersFound) {
                        saveableDatasets.add(dataset)
                    }
                }
            }
            indicators?.each { Indicator indicator ->
                if ("complex" != indicator?.assessmentMethod) {
                    saveableDatasets?.each { Dataset dataset ->
                        resolvePoints(dataset, indicator)
                    }
                }
            }
            Collection<Dataset> finalDatasets = entity.datasets

            if (!saveableDatasets?.toList()?.isEmpty()) {
                def handledDatasets = []
                uniqueQueryIds?.each { String queryId ->
                    finalDatasets = removeExistingDatasets(finalDatasets, queryId, checkOnlyGeneratedFromImportDatasets)

                    if (entity.quarterlyInputEnabledByQuery?.get(queryId)) {
                        handledDatasets.addAll(handleQuarterlyAnswers(saveableDatasets.findAll({ Dataset dataset -> dataset.queryId?.equals(queryId) })))
                    } else if (entity.monthlyInputEnabledByQuery?.get(queryId)) {
                        handledDatasets.addAll(handleMonthlyAnswers(saveableDatasets.findAll({ Dataset dataset -> dataset.queryId?.equals(queryId) })))
                    }
                }
                if (!handledDatasets.isEmpty()) {
                    saveableDatasets = handledDatasets
                }
                saveableDatasets = resolveResources(saveableDatasets)
                def formattedDatasets = []
                Dataset formattedDataset

                for (Dataset dataset in saveableDatasets) {

                    if (dataset.answerIds && !dataset.monthlyAnswers && !dataset.quarterlyAnswers) {
                        // We have to nullify the quantity first so that existing quantities aren't eg doubled
                        dataset.quantity = null

                        for (answer in dataset.answerIds) {
                            def formattedAnswer = answer.toString().replace(',', '.')

                            if (formattedAnswer.isNumber()) {
                                dataset.quantity = dataset.quantity != null ? dataset.quantity + formattedAnswer.toDouble() : formattedAnswer.toDouble()
                            }
                        }
                    }
                    // We have to create copy, otherwise numeric answer is back to null. Don't know the reason for this.
                    formattedDataset = new Dataset()
                    formattedDataset = dataset
                    formattedDatasets.add(formattedDataset)
                }
                if (handleAdditionalQuestions) {
                    formattedDatasets = resolveAdditionalQuestionsWithoutQuantity(formattedDatasets)
                    formattedDatasets = resolveAdditionalQuestions(formattedDatasets)
                }
                addDefaultProfilesIfMissing(formattedDatasets)
                if (finalDatasets) {
                    finalDatasets.addAll(formattedDatasets)
                } else {
                    finalDatasets = formattedDatasets
                }
            } else {
                uniqueQueryIds?.each { String queryId ->
                    finalDatasets = removeExistingDatasets(finalDatasets, queryId, checkOnlyGeneratedFromImportDatasets)
                }
            }
            handleMapToResource(entity, finalDatasets)
            if (entity.automaticCostCalculationLicensed || indicators?.collect({ it.indicatorId })?.contains(com.bionova.optimi.core.Constants.COMPARE_INDICATORID)) {
                handleLCCostValues(entity, finalDatasets)
            }
            handleThicknessesAndAreas(entity, finalDatasets)
            handleVolumeFractions(entity, finalDatasets)

            if (!finalDatasets) {
                entity.datasets = [new Dataset()]
            } else {
                List<Question> allQuestions = queryService.getQueryByQueryId(com.bionova.optimi.core.Constants.ADDITIONAL_QUESTIONS_QUERY_ID, true).getAllQuestions()
                List<Question> relatedQuestionAdditionalQuestions = allQuestions?.findAll({ Question q -> q.relatedQuestionId })
                List<String> relatedQuestionIds = relatedQuestionAdditionalQuestions?.collect({ it.questionId })

                finalDatasets.each { Dataset dataset ->
                    if (!dataset.manualId) {
                        dataset.manualId = new ObjectId().toString()
                    }

                    Map<String, String> questionsToAdd = [:]

                    dataset.additionalQuestionAnswers?.each { String questionId, answer ->
                        if (relatedQuestionIds?.contains(questionId) && answer != null) {
                            Question aq = relatedQuestionAdditionalQuestions.find({ it.questionId == questionId })
                            QuestionAnswerChoice questionAnswerChoice = aq.choices?.find({ answer.equals(it.answerId) })

                            if (answer && aq && aq.relatedQuestionId && questionAnswerChoice && questionAnswerChoice.relatedQuestionAnswerId) {
                                Question relatedaq = allQuestions.find({ it.questionId == aq.relatedQuestionId && it.choices })

                                if (relatedaq) {
                                    QuestionAnswerChoice relatedQuestionAnswerChoice = relatedaq.choices.find({ questionAnswerChoice.relatedQuestionAnswerId.equals(it.answerId) })

                                    if (relatedQuestionAnswerChoice) {
                                        questionsToAdd.put((relatedaq.questionId), relatedQuestionAnswerChoice.answerId)
                                    }
                                }
                            }
                        }
                    }

                    if (questionsToAdd) {
                        dataset.additionalQuestionAnswers.putAll(questionsToAdd)
                    }
                }
                entity.datasets = finalDatasets
            }
            if (!temporaryCalculation) {
                entity = entity.merge(flush: true)
            }
        } catch (Exception e) {
            loggerUtil.error(log, "Error in prehandling datasets", e)
            flashService.setErrorAlert("Error in prehandling datasets ${e.getMessage()}", true)
        }
        return entity
    }

    Entity saveDatasets(Entity entity, List<Dataset> datasets, String queryId, String indicatorId,
                                boolean handleAdditionalQuestions, HttpSession session,
                                Boolean storeUsedUnitSystem, Boolean temporaryCalculation,
                                Boolean skipCalculation, Map<String, String> defaults, Boolean projectLevel = Boolean.FALSE,
                                String originalDatasetId = null, List<String> changeSimilarDatasets = null,
                                Boolean notRemoveAll = Boolean.FALSE, Boolean resultsOutOfDate = Boolean.FALSE) {
        if (!entity) {
            loggerUtil.error(log, "Error in saving dataset because entity is null")
            flashService.setErrorAlert("Error in saving dataset because entity is null", true)
            return null
        }

        try {
            if (entity.queryLastUpdateInfos) {
                QueryLastUpdateInfo queryLastUpdateInfo = entity.queryLastUpdateInfos.get(queryId)

                if (queryLastUpdateInfo) {
                    queryLastUpdateInfo.usernames.add(0, userService?.getCurrentUser()?.name)
                    queryLastUpdateInfo.updates.add(0, new Date())

                    if (queryLastUpdateInfo.updates.size() > 3) {
                        queryLastUpdateInfo.updates.remove(queryLastUpdateInfo.updates.size() - 1)
                        queryLastUpdateInfo.usernames.remove(queryLastUpdateInfo.updates.size() - 1)
                    }
                    entity.queryLastUpdateInfos.put(queryId, queryLastUpdateInfo)
                } else {
                    entity.queryLastUpdateInfos.put(queryId, new QueryLastUpdateInfo(usernames: [userService?.getCurrentUser()?.name], updates: [new Date()]))
                }
            } else {
                entity.queryLastUpdateInfos = [(queryId): new QueryLastUpdateInfo(usernames: [userService?.getCurrentUser()?.name], updates: [new Date()])]
            }
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
            def saveableDatasets = []
            boolean answersFound
            User user = userService.getCurrentUser()

            Collection<Dataset> finalDatasets = entity.datasets

            if (!datasets?.isEmpty()) {
                String unitSystem = user?.unitSystem

                datasets?.each { Dataset dataset ->
                    if (projectLevel) {
                        dataset.projectLevelDataset = Boolean.TRUE
                    }

                    if (storeUsedUnitSystem) {
                        dataset.usedUnitSystem = unitSystem ? unitSystem : UnitConversionUtil.UnitSystem.METRIC.value
                    }
                    answersFound = false

                    if (StringUtils.isNotBlank(dataset.answerIds?.get(0)) || dataset.resourceId || !dataset.monthlyAnswers?.isEmpty() || !dataset.quarterlyAnswers?.isEmpty()) {
                        answersFound = true
                    }

                    if (answersFound) {
                        saveableDatasets.add(dataset)
                        if ("complex" != indicator?.assessmentMethod) {
                            resolvePoints(dataset, indicator)
                        }
                    }

                    if (notRemoveAll) {
                        // replace only the datasets needed to be saved, not all existing datasets. Get a list of datasets that should not be removed
                        finalDatasets.removeIf({ dataset.queryId == it.queryId && dataset.sectionId == it.sectionId && dataset.questionId == it.questionId })
                    }
                }
            }

            if (!saveableDatasets?.toList()?.isEmpty()) {
                if (notRemoveAll) {
                    // add the list of datasets that should not be removed
                    saveableDatasets.addAll(finalDatasets)
                }
                finalDatasets = removeExistingDatasets(finalDatasets, queryId, false, indicator?.hideQuestions)

                if (entity.quarterlyInputEnabledByQuery?.get(queryId)) {
                    saveableDatasets = handleQuarterlyAnswers(saveableDatasets)
                } else if (entity.monthlyInputEnabledByQuery?.get(queryId)) {
                    saveableDatasets = handleMonthlyAnswers(saveableDatasets)
                }
                saveableDatasets = resolveResources(saveableDatasets)
                def formattedDatasets = []
                Dataset formattedDataset

                for (Dataset dataset in saveableDatasets) {
                    if (dataset.answerIds && !dataset.monthlyAnswers && !dataset.quarterlyAnswers) {
                        // We have to nullify the quantity first so that existing quantities aren't eg doubled
                        dataset.quantity = null

                        for (answer in dataset.answerIds) {
                            def formattedAnswer = answer.toString().replace(',', '.')

                            if (formattedAnswer.isNumber()) {
                                dataset.quantity = dataset.quantity != null ? dataset.quantity + formattedAnswer.toDouble() : formattedAnswer.toDouble()
                            }
                        }
                    }
                    // We have to create copy, otherwise numeric answer is back to null. Don't know the reason for this.
                    formattedDataset = new Dataset()
                    formattedDataset = dataset
                    if (dataset.projectLevelDataset) {
                        formattedDataset.projectLevelDataset = dataset.projectLevelDataset
                    }
                    formattedDatasets.add(formattedDataset)
                }


                if (handleAdditionalQuestions) {
                    formattedDatasets = resolveAdditionalQuestionsWithoutQuantity(formattedDatasets)
                    formattedDatasets = resolveAdditionalQuestions(formattedDatasets)
                }
                addDefaultProfilesIfMissing(formattedDatasets)

                if (finalDatasets) {
                    finalDatasets.addAll(formattedDatasets)
                } else {
                    finalDatasets = formattedDatasets
                }
            } else {
                finalDatasets = removeExistingDatasets(finalDatasets, queryId, false, indicator?.hideQuestions)
            }
            handleMapToResource(entity, finalDatasets)

            if (entity.automaticCostCalculationLicensed || entity.isMaterialSpecifierEntity) {
                handleLCCostValues(entity, finalDatasets)
            }

            if (defaults) {
                handleDefaults(defaults, finalDatasets, entity)
            }
            handleVolumeFractions(entity, finalDatasets)

            if (!finalDatasets) {
                entity.datasets = [new Dataset()]
            } else {
                Dataset replacedDataset

                if (originalDatasetId && changeSimilarDatasets) {
                    replacedDataset = finalDatasets.find({ originalDatasetId.equals(it.manualId) })
                }
                List<Question> allQuestions = queryService.getQueryByQueryId(com.bionova.optimi.core.Constants.ADDITIONAL_QUESTIONS_QUERY_ID, true).getAllQuestions()
                List<Question> relatedQuestionAdditionalQuestions = allQuestions?.findAll({ Question q -> q.relatedQuestionId })
                List<String> relatedQuestionIds = relatedQuestionAdditionalQuestions?.collect({ it.questionId })
                Boolean useInches = indicator?.useThicknessInInches

                finalDatasets.each { Dataset dataset ->
                    if (!dataset.manualId) {
                        dataset.manualId = new ObjectId().toString()
                    } else {
                        if (replacedDataset && changeSimilarDatasets.contains(dataset.manualId)) {
                            dataset.resourceId = replacedDataset.resourceId
                            dataset.profileId = replacedDataset.profileId
                            if (replacedDataset.additionalQuestionAnswers?.get("profileId")) {
                                dataset.additionalQuestionAnswers["profileId"] = replacedDataset.additionalQuestionAnswers.get("profileId")
                            } else {
                                dataset.additionalQuestionAnswers?.remove("profileId")
                            }
                        }
                    }

                    Map<String, String> questionsToAdd = [:]

                    dataset.additionalQuestionAnswers?.each { String questionId, answer ->
                        if (relatedQuestionIds?.contains(questionId) && answer != null) {
                            Question aq = relatedQuestionAdditionalQuestions.find({ it.questionId == questionId })
                            QuestionAnswerChoice questionAnswerChoice = aq.choices?.find({ answer.equals(it.answerId) })

                            if (answer && aq && aq.relatedQuestionId && questionAnswerChoice && questionAnswerChoice.relatedQuestionAnswerId) {
                                Question relatedaq = allQuestions.find({ it.questionId == aq.relatedQuestionId && it.choices })

                                if (relatedaq) {
                                    QuestionAnswerChoice relatedQuestionAnswerChoice = relatedaq.choices.find({ questionAnswerChoice.relatedQuestionAnswerId.equals(it.answerId) })

                                    if (relatedQuestionAnswerChoice) {
                                        questionsToAdd.put((relatedaq.questionId), relatedQuestionAnswerChoice.answerId)
                                    }
                                }
                            }
                        }
                    }

                    if (questionsToAdd) {
                        dataset.additionalQuestionAnswers.putAll(questionsToAdd)
                    }

                    // Convert thickness to match in both inches and mm
                    if (useInches) {
                        String thickness_in = dataset.additionalQuestionAnswers?.get("thickness_in")
                        Double answerDouble = DomainObjectUtil.isNumericValue(thickness_in) ? DomainObjectUtil.convertStringToDouble(thickness_in) : null

                        if (answerDouble != null) {
                            String thickness_mm = (answerDouble * 25.4).toString()
                            dataset.additionalQuestionAnswers.put("thickness_mm", thickness_mm)
                        } else {
                            dataset.additionalQuestionAnswers?.remove("thickness_mm")
                        }
                    } else {
                        String thickness_mm = dataset.additionalQuestionAnswers?.get("thickness_mm")
                        Double answerDouble = DomainObjectUtil.isNumericValue(thickness_mm) ? DomainObjectUtil.convertStringToDouble(thickness_mm) : null

                        if (answerDouble != null) {
                            String thickness_in = (answerDouble * 0.0393700787).toString()
                            dataset.additionalQuestionAnswers.put("thickness_in", thickness_in)
                        } else {
                            dataset.additionalQuestionAnswers?.remove("thickness_in")
                        }
                    }
                }
                entity.datasets = finalDatasets
            }

            if (projectLevel || indicator?.nonNumericResult ||
                    queryId.equals(configurationService.getConfigurationValue(null, Constants.ConfigName.BASIC_QUERY_ID.toString()))) {
                resolveQueryReadiness(indicator, entity, queryId)
            }

            if (!temporaryCalculation) {
                entity = entity.merge(flush: true)
            }

            if(skipCalculation) {
                resolveQueryReadiness(indicator, entity, queryId)
                log.debug("Skipping calculations. Results out of date: ${resultsOutOfDate}")
                entity.resultsOutOfDate = resultsOutOfDate
                entity = entity.save(flush: true, failOnError: true)
            }
        } catch (Exception e) {
            loggerUtil.error(log, "Error in saving datasets", e)
            flashService.setErrorAlert("Error in saving datasets: ${e.getMessage()}", true)
        }
        return entity
    }

    private void handleDefaults(Map<String, String> defaults, Collection<Dataset> datasets, Entity entity) {
        if (defaults && datasets && entity) {
            defaults.each { String valueToSet, String defaultValue ->
                if (defaultValue) {
                    if (entity.defaults) {
                        entity.defaults.put((valueToSet), defaultValue)
                    } else {
                        entity.defaults = [(valueToSet): defaultValue]
                    }
                } else {
                    if (entity.defaults) {
                        entity.defaults.remove(valueToSet)
                    }
                }

                if (Constants.DEFAULT_SERVICELIFE.toString() == valueToSet) {
                    if (!defaultValue) {
                        datasets.each {
                            if (it.additionalQuestionAnswers) {
                                if ("default".equals(it.additionalQuestionAnswers.get("serviceLife"))) {
                                    it.additionalQuestionAnswers.remove("serviceLife")
                                }
                            }
                        }
                    }
                } else if (Constants.DEFAULT_TRANSPORT.toString() == valueToSet) {
                    if (!defaultValue) {
                        datasets.each {
                            if (it.additionalQuestionAnswers) {
                                com.bionova.optimi.core.Constants.TransportDistanceQuestionId.list().each { String transportQuestionId ->
                                    if ("default".equals(it.additionalQuestionAnswers.get(transportQuestionId))) {
                                        it.additionalQuestionAnswers.remove(transportQuestionId)
                                    }
                                }
                            }
                        }
                    }
                } else {
                    loggerUtil.error(log, "ENTITY LEVEL DEFAULTS: Unknown default value to set: ${valueToSet}")
                    flashService.setErrorAlert("ENTITY LEVEL DEFAULTS: Unknown default value to set: ${valueToSet}", true)
                }
            }
        }
    }

    private void handleVolumeFractions(Entity child, Collection<Dataset> datasets) {
        if (child && datasets && datasets.find({ it.additionalQuestionAnswers?.get("volumeFraction") && it.additionalQuestionAnswers?.get("specialRuleId") })) {
            List<SpecialRule> specialRules = applicationService.getApplicationByApplicationId("LCA")?.specialRules

            if (specialRules) {
                List<Dataset> splitDatasets = []
                ResourceCache resourceCache = ResourceCache.init(datasets.toList())
                for (Dataset dataset: datasets) {
                    String volumeFraction = dataset.additionalQuestionAnswers?.get("volumeFraction")
                    String specialRuleId = dataset.additionalQuestionAnswers?.get("specialRuleId")
                    Resource resource = resourceCache.getResource(dataset)

                    // IF dataset is saved form query page it has lost the original answer so the original answer is what is in the quantity input
                    // BUT if datasets are saved and calculated from e.g. the parameters query so mechanically it has persisted the original answer from the query page calculation
                    if (resource && !dataset.originalAnswer && dataset.answerIds && !dataset.answerIds.isEmpty() && specialRuleId && volumeFraction && volumeFraction.replace(",", ".").isNumber()) {
                        SpecialRule specialRule = specialRules.find({ specialRuleId.equals(it.specialRuleId) })

                        if (specialRule) {
                            Map<String, String> replacementResourceIdAndProfileId = specialRule.mappingByDataproperty?.get(resource.dataProperties?.getAt(0))

                            if (replacementResourceIdAndProfileId) {
                                String resourceId = replacementResourceIdAndProfileId.resourceId
                                String profileId = replacementResourceIdAndProfileId.profileId
                                Double percentage = volumeFraction.replace(",", ".").toDouble()
                                Double originalQuantity = dataset.quantity
                                Double splitQuantity = 0

                                if (percentage && originalQuantity) {
                                    dataset.originalAnswer = "${dataset.answerIds[0].toString()}"
                                    splitQuantity = (percentage / 100.0) * originalQuantity
                                    originalQuantity = ((100 - percentage) / 100.0) * originalQuantity
                                }

                                if (originalQuantity && splitQuantity) {
                                    Dataset splitDataset = createCopy(dataset)
                                    dataset.quantity = originalQuantity
                                    dataset.answerIds = ["${originalQuantity}"]
                                    splitDataset.quantity = splitQuantity
                                    splitDataset.answerIds = ["${splitQuantity}"]
                                    splitDataset.manualId = new ObjectId().toString()
                                    splitDataset.resourceId = resourceId
                                    splitDataset.profileId = profileId

                                    if (splitDataset.additionalQuestionAnswers) {
                                        splitDataset.additionalQuestionAnswers.put("profileId", profileId)
                                        splitDataset.additionalQuestionAnswers.remove("specialRuleId")
                                        splitDataset.additionalQuestionAnswers.remove("volumeFraction")
                                    } else {
                                        splitDataset.additionalQuestionAnswers = [profileId: profileId]
                                    }
                                    splitDataset.noQueryRender = Boolean.TRUE
                                    dataset.connectedDatasetManualId = splitDataset.manualId
                                    splitDatasets.add(splitDataset)
                                }
                            } else {
                                loggerUtil.error(log, "Volume fraction handling error: No applicable replacement for dataProperty: ${resource.dataProperties?.getAt(0)} for specialRule: ${specialRuleId}")
                                flashService.setErrorAlert("Volume fraction handling error: No applicable replacement for dataProperty: ${resource.dataProperties?.getAt(0)} for specialRule: ${specialRuleId}", true)
                            }
                        } else {
                            loggerUtil.error(log, "Volume fraction handling error: No specialRule found from LCA application with specialRuleId: ${specialRuleId}")
                            flashService.setErrorAlert("Volume fraction handling error: No specialRule found from LCA application with specialRuleId: ${specialRuleId}", true)
                        }
                    }
                }
                if (!splitDatasets.isEmpty()) {
                    datasets.addAll(splitDatasets)
                }
            }
        }
    }


    private void handleThicknessesAndAreas(Entity child, Collection<Dataset> datasets) {
        if (child && datasets) {
            ResourceCache resourceCache = ResourceCache.init(datasets.toList())
            for (Dataset dataset: datasets) {
                Resource resource = resourceCache.getResource(dataset)

                if (resource) {
                    if (!resource.allowVariableThickness) {
                        dataset.area_m2 = null

                        if (resource.defaultThickness_mm != null) {
                            dataset.additionalQuestionAnswers["thickness_mm"] = resource.defaultThickness_mm
                        } else {
                            dataset.additionalQuestionAnswers?.remove("thickness_mm")
                        }

                        if (resource.defaultThickness_in != null) {
                            dataset.additionalQuestionAnswers["thickness_in"] = resource.defaultThickness_in
                        } else {
                            dataset.additionalQuestionAnswers?.remove("thickness_in")
                        }
                    }

                    if (resource.construction || (dataset.area_m2 != null && !resource.combinedUnits?.contains("m2"))) {
                        dataset.area_m2 = null
                    }

                    if (resource.construction || (dataset.volume_m3 != null && !resource.combinedUnits?.contains("m3"))) {
                        dataset.volume_m3 = null
                    }

                    if (resource.construction || (dataset.mass_kg != null && !resource.combinedUnits?.contains("kg"))) {
                        dataset.mass_kg = null
                    }
                }
            }
        }
    }

    private void handleLCCostValues(Entity child, Collection<Dataset> datasets) {
        Entity parent = child?.parentById

        if (child && parent && datasets) {
            ResourceCache resourceCache = ResourceCache.init(datasets as List)
            List<Indicator> lccIndicatorsInEntity = indicatorService.getIndicatorsByEntityAndIndicatorUse(parent.id, Constants.IndicatorUse.DESIGN.toString())?.findAll({ it.isCostCalculationAllowed }) ?: []
            if (child.isMaterialSpecifierEntity) {
                Indicator compareIndicator = indicatorService.getIndicatorByIndicatorId(com.bionova.optimi.core.Constants.COMPARE_INDICATORID, true)
                if (compareIndicator) {
                    lccIndicatorsInEntity.add(compareIndicator)
                }
            }
            if (lccIndicatorsInEntity && !lccIndicatorsInEntity.isEmpty()) {
                String costCalculationMethod = parent.costCalculationMethod
                Double labourCostCraftsman = 0
                Double labourCostWorker = 0
                Double finalMultiplier = 0
                Double currencyMultiplier = 0

                lccIndicatorsInEntity.each { Indicator indicator ->
                    if (indicator.labourCostWorkerValueReference) {
                        labourCostWorker = valueReferenceService.getDoubleValueForEntity(indicator.labourCostWorkerValueReference,
                                child, null, null, resourceCache) ?: 0
                    }

                    if (indicator.labourCostCraftsmanValueReference) {
                        labourCostCraftsman = valueReferenceService.getDoubleValueForEntity(indicator.labourCostCraftsmanValueReference,
                                child, null, null, resourceCache) ?: 0
                    }

                    if (indicator.countryIndexValueReference) {
                        finalMultiplier = valueReferenceService.getDoubleValueForEntity(indicator.countryIndexValueReference,
                                child, null, null, resourceCache) ?: 0
                    }

                    if (indicator.currencyExchangeValueReference) {
                        currencyMultiplier = valueReferenceService.getDoubleValueForEntity(indicator.currencyExchangeValueReference,
                                child, null, null, resourceCache) ?: 0
                    }
                }

                for (Dataset dataset: datasets) {
                    Resource resource = resourceCache.getResource(dataset)

                    if (resource) {
                        Double defaultDensity = resource.density
                        Double defaultThickness = resource.defaultThickness_mm
                        Double defaultThickness_in = resource.defaultThickness_in
                        Double resourceTypeCostMultiplier = resource.resourceTypeCostMultiplier
                        Double massConversionFactor = resource.massConversionFactor
                        String resourceSubType = resource.resourceSubType
                        String unitForData = resource.unitForData
                        Double userGivenQuantity = dataset.quantity
                        String userGivenUnit = dataset.userGivenUnit
                        String userGivenCustomCostTotalMultiplier
                        Double userGivenThickness = getThickness(dataset.additionalQuestionAnswers)

                        if (dataset.userSetCost && dataset.additionalQuestionAnswers?.get("costPerUnit")) {
                            userGivenCustomCostTotalMultiplier = dataset.additionalQuestionAnswers.get("costPerUnit")
                        }
                        Double costPerUnit
                        Double totalCost

                        if (resourceSubType && (!((("m2".equalsIgnoreCase(userGivenUnit) && !defaultThickness) || ("sq ft".equalsIgnoreCase(userGivenUnit) && !defaultThickness_in && !defaultThickness)) && !userGivenThickness)) || (userGivenCustomCostTotalMultiplier && !dataset.userSetTotalCost)) {
                            CostStructure costStructure = costStructureService.getCostStructureByResourceSubType(resourceSubType)
                            Double cost = resolveCostFromParams(costStructure, userGivenUnit, unitForData, userGivenCustomCostTotalMultiplier, resource.allowVariableThickness, currencyMultiplier, finalMultiplier,
                                    userGivenQuantity, labourCostWorker, labourCostCraftsman, userGivenThickness, defaultThickness, defaultThickness_in, defaultDensity, massConversionFactor, resourceTypeCostMultiplier,
                                    costCalculationMethod, resource.costConstruction_EUR, resource.cost_EUR)

                            if (costStructure && userGivenQuantity && cost) {
                                costPerUnit = cost

                                if (userGivenQuantity) {
                                    totalCost = cost * userGivenQuantity
                                }
                            } else if (userGivenCustomCostTotalMultiplier && userGivenQuantity && cost) {
                                totalCost = cost * userGivenQuantity
                            }
                        }

                        if (costPerUnit && !dataset.userSetCost) {
                            if (dataset.additionalQuestionAnswers) {
                                dataset.additionalQuestionAnswers.put("costPerUnit", costPerUnit.round(2))
                            } else {
                                dataset.additionalQuestionAnswers = [costPerUnit: costPerUnit.round(2)]
                            }
                        }

                        /*if (totalCost && !dataset.userSetTotalCost) {
                            if (dataset.additionalQuestionAnswers) {
                                dataset.additionalQuestionAnswers.put("totalCost", totalCost.round(0).intValue())
                            } else {
                                dataset.additionalQuestionAnswers = [totalCost: totalCost.round(0).intValue()]
                            }
                        } */


                        if (!dataset.userSetTotalCost) {
                            if (totalCost) {
                                if (dataset.additionalQuestionAnswers) {
                                    dataset.additionalQuestionAnswers.put("totalCost", totalCost.round(0).intValue())
                                } else {
                                    dataset.additionalQuestionAnswers = [totalCost: totalCost.round(0).intValue()]
                                }
                            } else if (!userGivenQuantity) {
                                if (dataset.additionalQuestionAnswers) {
                                    dataset.additionalQuestionAnswers.put("totalCost", 0)
                                } else {
                                    dataset.additionalQuestionAnswers = [totalCost: 0]
                                }
                            }
                        }


                    }
                }
            }
        }
    }

    Double resolveCostFromParams(CostStructure costStructure, String userGivenUnit, String unitForData, String userGivenCustomCostTotalMultiplier, Boolean allowVariableThickness, Double currencyMultiplier, Double finalMultiplier, Double userGivenQuantity,
                              Double labourCostWorker, Double labourCostCraftsman, Double userGivenThickness, Double defaultThickness, Double defaultThickness_in, Double defaultDensity, Double massConversionFactor, Double resourceTypeCostMultiplier,
                              String costCalculationMethod, Double costConstruction_EUR, Double cost_EUR) {
        Double cost
        Boolean resourceBasedCost = "materialsSpecificCost".equals(costCalculationMethod)

        if (costStructure && userGivenUnit) {
            Double materialCost = costStructure.materialCost

            if (currencyMultiplier && materialCost != null) {
                materialCost = materialCost * currencyMultiplier
            }

            if (finalMultiplier && materialCost != null) {
                materialCost = materialCost * finalMultiplier
            }

            if (userGivenCustomCostTotalMultiplier) {
                try {
                    cost = Double.valueOf(userGivenCustomCostTotalMultiplier.replaceAll(",", ".").trim())
                } catch (Exception e) {
                    loggerUtil.warn(log, "Could not convert user given quantity to double", e)
                    flashService.setWarningAlert("Could not convert user given quantity to double", true)
                }
            } else if (resourceBasedCost && costConstruction_EUR) {
                cost = costConstruction_EUR
            } else if (materialCost != null && costStructure.labourUnitsWorker != null && costStructure.labourUnitsCraftsman != null) {
                cost = materialCost + (costStructure.labourUnitsWorker * labourCostWorker) + (costStructure.labourUnitsCraftsman * labourCostCraftsman)
            } else if (cost_EUR) {
                cost = cost_EUR
            }

            Boolean isImperial = com.bionova.optimi.core.Constants.IMPERIAL_UNITS.contains(userGivenUnit.toLowerCase())
            String resolvedUserGivenUnit = isImperial ? unitConversionUtil.transformImperialUnitToEuropeanUnit(userGivenUnit.toLowerCase()) : userGivenUnit

            if (!userGivenCustomCostTotalMultiplier && !(resourceBasedCost && cost)) {
                // If costStructure in m2 then apply thickness_mm from costStructure, otherways from resource
                // In do conversion then resource defaultthickness needs to be also from the costStructure.thickness_mm instead

                Double resolvedThickness
                // use user given thickness in m2 to m2 conversion
                if ("m2".equalsIgnoreCase(costStructure.costUnit) && !"m2".equalsIgnoreCase(resolvedUserGivenUnit)) {
                    resolvedThickness = costStructure.thickness_mm ?: userGivenThickness != null ? userGivenThickness : defaultThickness != null ? defaultThickness : defaultThickness_in != null ? defaultThickness_in * 25.4 : null
                } else {
                    resolvedThickness = userGivenThickness != null ? userGivenThickness : defaultThickness != null ? defaultThickness : defaultThickness_in != null ? defaultThickness_in * 25.4 : null
                }
                Double resolvedDensity

                // hackily resolve correct denstiy and cost
                if ("m3".equalsIgnoreCase(costStructure.costUnit) && ["kg", "ton"].contains(resolvedUserGivenUnit)) {
                    resolvedDensity = costStructure.density ?: defaultDensity
                } else if ("m3".equalsIgnoreCase(costStructure.costUnit) && ["m2", "m3"].contains(resolvedUserGivenUnit) && costStructure.density && defaultDensity && cost) {
                    cost = cost * (defaultDensity / costStructure.density)
                    resolvedDensity = defaultDensity
                } else {
                    resolvedDensity = defaultDensity
                }
                cost = unitConversionUtil.doConversion(cost, resolvedThickness, resolvedUserGivenUnit, null, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, resolvedDensity, costStructure.costUnit, allowVariableThickness, costStructure, null, costStructure.thickness_mm ?: defaultThickness)
            }
            Double thicknessRatio = defaultThickness != null && costStructure.thickness_mm != null ? defaultThickness / costStructure.thickness_mm : 1

            if (!cost) {
                if (costStructure.costUnit.equals(unitForData) && ["kg", "ton"].contains(resolvedUserGivenUnit) && massConversionFactor) {
                    cost = (materialCost + (costStructure.labourUnitsWorker * labourCostWorker) + (costStructure.labourUnitsCraftsman * labourCostCraftsman)) * thicknessRatio

                    if (cost) {
                        cost = cost / massConversionFactor
                        if ("ton".equals(resolvedUserGivenUnit)) {
                            cost = cost * 1000
                        }
                    }
                } else if ("kg".equals(costStructure.costUnit) && resolvedUserGivenUnit.equals(unitForData) && massConversionFactor) {
                    cost = (materialCost + (costStructure.labourUnitsWorker * labourCostWorker) + (costStructure.labourUnitsCraftsman * labourCostCraftsman)) * massConversionFactor * thicknessRatio
                }
            }

            if (resourceTypeCostMultiplier) {
                if (cost != null && !userGivenCustomCostTotalMultiplier) {
                    cost = cost * resourceTypeCostMultiplier
                }
            }

            if (cost && isImperial) {
                // transform european cost to imperial
                cost = unitConversionUtil.doConversion(cost, null, resolvedUserGivenUnit, null, Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, null, userGivenUnit)
            }
        } else if (userGivenCustomCostTotalMultiplier) {
            try {
                cost = Double.valueOf(userGivenCustomCostTotalMultiplier.replaceAll(",", ".").trim())
            } catch (Exception e) {
                loggerUtil.warn(log, "Could not convert user given quantity to double", e)
                flashService.setWarningAlert("Could not convert user given quantity to double: ${e.getMessage()}", true)
            }
        } else if (resourceBasedCost && costConstruction_EUR) {
            cost = costConstruction_EUR
            Boolean isImperial = com.bionova.optimi.core.Constants.IMPERIAL_UNITS.contains(userGivenUnit.toLowerCase())

            if (isImperial) {
                String resolvedUserGivenUnit = unitConversionUtil.transformImperialUnitToEuropeanUnit(userGivenUnit.toLowerCase())
                cost = unitConversionUtil.doConversion(cost, null, resolvedUserGivenUnit, null, Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, null, userGivenUnit)
            }
        } else if(cost_EUR) {
            cost = cost_EUR
        }
        return cost
    }

    private resolveQueryReadiness(Indicator indicator, Entity entity, String queryId) {
        if (entity && queryId) {
            return entity.resolveQueryReady(indicator, queryId)
        }
    }

    private void handleMapToResource(Entity entity, Collection<Dataset> datasets) {
        List<License> validLicenses = licenseService.getValidLicensesForEntity(entity)
        List<Feature> featuresAvailableForEntity = entityService.getFeatures(validLicenses)

        if (("component".equals(entity?.getParentById()?.entityClass) || entityService.isComponentAbilityLicensed(featuresAvailableForEntity)) && datasets) {
            Question question

            datasets.each { Dataset d ->
                question = questionService.getQuestion(d.queryId, d.questionId)

                if (question?.mapToResourceType) {
                    d.mapToResource = Boolean.TRUE
                    d.mapToResourceType = question.mapToResourceType
                }
            }
        }
    }

    private List<Dataset> handleMonthlyAnswers(List<Dataset> datasets) {
        // DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("FI"))
        DecimalFormat df = userService.getDefaultDecimalFormat()
        def resolvedDatasets = []
        datasets?.each { Dataset dataset ->
            if (dataset.monthlyAnswers) {
                def maxAnswers = 0

                dataset.monthlyAnswers.each { String month, List answers ->
                    if (answers && answers.size() > maxAnswers) {
                        maxAnswers = answers.size()
                    }
                }

                if (maxAnswers) {
                    for (int i = 1; i < maxAnswers; i++) {
                        Dataset anotherRow = createCopy(dataset)

                        if (dataset.tempSeqNrs) {
                            try {
                                anotherRow.seqNr = dataset.tempSeqNrs[i].toInteger()
                            } catch (Exception e) {

                            }
                        }

                        dataset.monthlyAnswers.each { String month, List answers ->
                            if (answers) {
                                def formattedValue = answers[i].toString()?.replaceAll(",", ".")
                                anotherRow.monthlyAnswers.put(month, formattedValue)

                            }
                        }
                        Double sumOfMonthly

                        anotherRow.monthlyAnswers?.each { String key, String value ->
                            if (value) {
                                def formattedValue = value.replace(',', '.')

                                if (formattedValue.isNumber()) {
                                    sumOfMonthly = sumOfMonthly ? sumOfMonthly + formattedValue.toDouble() : formattedValue.toDouble()
                                }
                            }
                        }
                        if (sumOfMonthly != null) {
                            anotherRow.quantity = sumOfMonthly
                            anotherRow.answerIds = [df.format(sumOfMonthly)]
                        } else {
                            anotherRow.quantity = null
                            anotherRow.answerIds = ["0"]
                        }

                        dataset.tempAdditionalQuestionAnswers?.each { key, value ->
                            if (value instanceof String[]) {
                                try {
                                    def existing = anotherRow.additionalQuestionAnswers

                                    if (existing) {
                                        existing.put(key, value[i])
                                    } else {
                                        existing = [(key): value[i]]
                                    }
                                    anotherRow.additionalQuestionAnswers = existing
                                } catch (Exception e) {

                                }
                            }
                        }
                        resolvedDatasets.add(anotherRow)
                    }
                }
                def originalsMonthlyAnswers = [:]

                dataset.monthlyAnswers.each { String month, List answers ->
                    if (answers) {
                        def formattedValue = answers[0]?.toString()?.replace(',', '.')
                        if (formattedValue.isNumber()) {
                            formattedValue.toDouble()
                        }
                        originalsMonthlyAnswers.put(month, formattedValue)
                    }
                }

                if (dataset.tempSeqNrs) {
                    try {
                        dataset.seqNr = dataset.tempSeqNrs[0].toInteger()
                    } catch (Exception e) {

                    }
                }

                dataset.monthlyAnswers = originalsMonthlyAnswers
                Double sumOfMonthly

                dataset.monthlyAnswers?.each { String key, String value ->
                    if (value) {
                        def formattedValue = value.replace(',', '.')

                        if (formattedValue.isNumber()) {
                            sumOfMonthly = sumOfMonthly ? sumOfMonthly + formattedValue.toDouble() : formattedValue.toDouble()
                        }
                    }
                }

                if (sumOfMonthly != null) {
                    dataset.quantity = sumOfMonthly
                    dataset.answerIds = [df.format(sumOfMonthly)]
                } else {
                    dataset.quantity = null
                    dataset.answerIds = ["0"]
                }
                resolvedDatasets.add(dataset)
            } else {
                resolvedDatasets.add(dataset)
            }
        }
        return resolvedDatasets
    }

    private List<Dataset> handleQuarterlyAnswers(List<Dataset> datasets) {
        // DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("FI"))
        DecimalFormat df = userService.getDefaultDecimalFormat()
        def resolvedDatasets = []

        datasets?.each { Dataset dataset ->
            if (dataset.quarterlyAnswers) {
                def maxAnswers = 0

                dataset.quarterlyAnswers.each { String quarter, List answers ->
                    if (answers && answers.size() > maxAnswers) {
                        maxAnswers = answers.size()
                    }
                }

                if (maxAnswers) {
                    for (int i = 1; i < maxAnswers; i++) {
                        Dataset anotherRow = createCopy(dataset)

                        dataset.quarterlyAnswers.each { String quarter, List answers ->
                            if (answers) {
                                anotherRow.quarterlyAnswers.put(quarter, answers[i])
                            }
                        }
                        Double sumOfQuarters

                        anotherRow.quarterlyAnswers?.each { String key, String value ->
                            if (value) {
                                def formattedValue = value.replace(',', '.')

                                if (formattedValue.isNumber()) {
                                    sumOfQuarters = sumOfQuarters ? sumOfQuarters + formattedValue.toDouble() : formattedValue.toDouble()
                                }
                            }
                        }

                        if (sumOfQuarters != null) {
                            anotherRow.quantity = sumOfQuarters
                            anotherRow.answerIds = [df.format(sumOfQuarters)]
                        }

                        dataset.tempAdditionalQuestionAnswers?.each { key, value ->
                            if (value instanceof String[]) {
                                try {
                                    def existing = anotherRow.additionalQuestionAnswers

                                    if (existing) {
                                        existing.put(key, value[i])
                                    } else {
                                        existing = [(key): value[i]]
                                    }
                                    anotherRow.additionalQuestionAnswers = existing
                                } catch (Exception e) {

                                }
                            }
                        }
                        resolvedDatasets.add(anotherRow)
                    }
                }
                def originalQuarterlyAnswers = [:]

                dataset.quarterlyAnswers.each { String quarter, List answers ->
                    if (answers) {
                        originalQuarterlyAnswers.put(quarter, answers[0])
                    }
                }
                dataset.quarterlyAnswers = originalQuarterlyAnswers
                Double sumOfQuarters

                dataset.quarterlyAnswers?.each { String key, String value ->
                    if (value) {
                        def formattedValue = value.replace(',', '.')

                        if (formattedValue.isNumber()) {
                            sumOfQuarters = sumOfQuarters ? sumOfQuarters + formattedValue.toDouble() : formattedValue.toDouble()
                        }
                    }
                }

                if (sumOfQuarters != null) {
                    dataset.quantity = sumOfQuarters
                    dataset.answerIds = [df.format(sumOfQuarters)]
                }
                resolvedDatasets.add(dataset)
            } else {
                resolvedDatasets.add(dataset)
            }
        }
        return resolvedDatasets
    }

    private List<Dataset> resolveResources(List<Dataset> datasets) {
        def resolvedDatasets = []
        // def resourceIds = optimiResourceService.getAllResources()?.collect { it.resourceId }

        datasets?.each { Dataset dataset ->
            if (!dataset.resourceId) {
                def resourceId = optimiResourceService.getResourceWithParams(dataset.answerIds[0], null, Boolean.TRUE, Boolean.FALSE)?.resourceId

                //6684 && !datasets.find({ Dataset d -> d.resourceId == resourceId })
                if (resourceId) {
                    dataset.resourceId = resourceId
                }
                // This a fix for task 787
            } else if (!dataset.tempAdditionalQuestionAnswers && dataset.resourceId && dataset.answerIds?.size() > 1) {
                for (int i = 1; i < dataset.answerIds.size(); i++) {
                    Dataset anotherRow = createCopy(dataset)
                    anotherRow.answerIds = [dataset.answerIds[i]]
                    resolvedDatasets.add(anotherRow)
                }
                dataset.answerIds = [dataset.answerIds[0]]
            }
            resolvedDatasets.add(dataset)
        }
        //loggerUtil.debug(log, "End of resolveResources")
        return resolvedDatasets
    }

    public void addDefaultProfilesIfMissing(List<Dataset> datasets) {
        datasets?.each { Dataset d ->
            if (d.resourceId && !d.profileId) {
                String defaultProfile = optimiResourceService.getDefaultProfileForResource(d.resourceId)

                if (defaultProfile) {
                    d.profileId = defaultProfile

                    if (d.additionalQuestionAnswers) {
                        d.additionalQuestionAnswers.put("profileId", defaultProfile)
                    } else {
                        d.additionalQuestionAnswers = [profileId: defaultProfile]
                    }
                }
            }
        }
    }

    public List<Dataset> resolveAdditionalQuestionsWithoutQuantity(List<Dataset> datasets) {
        def resolvedDatasets = []
        datasets?.each { Dataset dataset ->
            if (dataset.resourceId && (dataset.tempAdditionalQuestionAnswers || dataset.tempUserGivenUnits || dataset.tempSeqNrs || dataset.tempPersistedOriginalClass ||
                    dataset.tempUserSetCosts || dataset.tempUserSetTotalCosts || dataset.tempConstructionValues || dataset.tempConstructionIdentifiers || dataset.tempConstructionIds ||
                    dataset.tempDatasetImportFieldsId || dataset.tempDatasetManualIds || dataset.tempIgnoreWarnings || dataset.tempLocked || dataset.tempNmdProfileSetString || dataset.tempGroupingDatasetName) &&
                    !dataset.answerIds) {
                def copyAmount = 0
                dataset.tempAdditionalQuestionAnswers?.each { key, values ->
                    if (values instanceof String[]) {
                        if (!copyAmount || (copyAmount && copyAmount < values.length)) {
                            copyAmount = values.length
                        }
                    }
                }
                if (!copyAmount) {
                    copyAmount = dataset.tempUserGivenUnits?.size()
                }

                if (copyAmount) {
                    for (int i = 0; i < copyAmount; i++) {
                        Dataset copy = createCopy(dataset)

                        if (dataset.tempAdditionalQuestionAnswers) {
                            dataset.tempAdditionalQuestionAnswers.each { key, values ->
                                if (copy.tempAdditionalQuestionAnswers) {
                                    try {
                                        copy.tempAdditionalQuestionAnswers.put(key, values[i])
                                    } catch (IndexOutOfBoundsException e) {
                                        // No need to do anything, because all additional questions might not have answers
                                    }
                                } else {
                                    try {
                                        copy.tempAdditionalQuestionAnswers = [(key): values[i]]
                                    } catch (IndexOutOfBoundsException e) {
                                        // No need to do anything, because all additional questions might not have answers
                                    }
                                }
                            }
                        }

                        if (dataset.tempDatasetManualIds && dataset.tempDatasetManualIds.getAt(i)) {
                            copy.manualId = dataset.tempDatasetManualIds.getAt(i)
                        }

                        if (dataset.tempSeqNrs && dataset.tempSeqNrs?.getAt(i)?.isNumber()) {
                            copy.seqNr = dataset.tempSeqNrs?.getAt(i)?.toInteger()
                        }

                        if (dataset.tempConstructionValues && dataset.tempConstructionValues?.getAt(i)?.isNumber()) {
                            copy.constructionValue = dataset.tempConstructionValues?.getAt(i)
                        }

                        if (dataset.tempConstructionIdentifiers && dataset.tempConstructionIdentifiers?.getAt(i)) {
                            copy.uniqueConstructionIdentifier = dataset.tempConstructionIdentifiers?.getAt(i)
                        }

                        if (dataset.tempConstructionIds && dataset.tempConstructionIds?.getAt(i)) {
                            copy.parentConstructionId = dataset.tempConstructionIdentifiers?.getAt(i)
                        }

                        if (dataset.tempPersistedOriginalClass && dataset.tempPersistedOriginalClass?.getAt(i)) {
                            copy.persistedOriginalClass = dataset.tempPersistedOriginalClass?.getAt(i)
                        }

                        if (dataset.tempDatasetImportFieldsId && dataset.tempDatasetImportFieldsId?.getAt(i)) {
                            copy.datasetImportFieldsId = dataset.tempDatasetImportFieldsId?.getAt(i)
                        }

                        if (dataset.tempUserSetCosts && !dataset.tempUserSetCosts.isEmpty()) {
                            try {
                                copy.userSetCost = "true".equals(dataset.tempUserSetCosts?.getAt(i)) ? Boolean.TRUE : Boolean.FALSE
                            } catch (Exception e) {
                            }
                        }

                        if (dataset.tempUserSetTotalCosts && !dataset.tempUserSetTotalCosts.isEmpty()) {
                            try {
                                copy.userSetTotalCost = "true".equals(dataset.tempUserSetTotalCosts?.getAt(i)) ? Boolean.TRUE : Boolean.FALSE
                            } catch (Exception e) {
                            }
                        }

                        if (dataset.tempIgnoreWarnings && !dataset.tempIgnoreWarnings.isEmpty()) {
                            try {
                                copy.ignoreWarning = "true".equals(dataset.tempIgnoreWarnings?.getAt(i)) ? Boolean.TRUE : Boolean.FALSE
                            } catch (Exception e) {
                            }
                        }

                        if (dataset.tempLocked && !dataset.tempLocked.isEmpty()) {
                            try {
                                copy.locked = "true".equals(dataset.tempLocked?.getAt(i)) ? Boolean.TRUE : Boolean.FALSE
                            } catch (Exception e) {
                            }
                        }

                        if (dataset.tempUserGivenUnits) {
                            try {
                                copy.userGivenUnit = dataset.tempUserGivenUnits?.getAt(i)
                            } catch (Exception e) {

                            }
                        }

                        if (dataset.tempNmdProfileSetString && dataset.tempNmdProfileSetString?.getAt(i)) {
                            copy.nmdProfileSetString = dataset.tempNmdProfileSetString?.getAt(i)
                        }

                        if (dataset.tempGroupingDatasetName && dataset.tempGroupingDatasetName?.getAt(i)) {
                            copy.groupingDatasetName = dataset.tempGroupingDatasetName?.getAt(i)
                        }
                        resolvedDatasets.add(copy)
                    }
                } else {
                    resolvedDatasets.add(dataset)
                }
            } else {
                resolvedDatasets.add(dataset)
            }
        }
        return resolvedDatasets
    }

    private List<Dataset> resolveUserGivenUnits(List<Dataset> datasets) {
        def resolvedDatasets = []

        datasets?.each { Dataset dataset ->
            if (dataset.tempUserGivenUnits) {
                dataset.tempUserGivenUnits?.each { String userGivenUnit ->
                    Dataset copy = createCopy(dataset)
                    copy.userGivenUnit = userGivenUnit
                }
            } else {
                resolvedDatasets.add(dataset)
            }
        }
        //loggerUtil.debug(log, "Start of resolveAdditionalQuestions")
        return resolvedDatasets
    }

    List<Dataset> resolveAdditionalQuestions(List<Dataset> datasets) {
        def resolvedDatasets = []
        ResourceCache resourceCache = ResourceCache.init(datasets)
        datasets?.each { Dataset dataset ->
            if (dataset.tempAdditionalQuestionAnswers || dataset.tempUserGivenUnits || dataset.tempSeqNrs || dataset.tempUserSetCosts || dataset.tempPersistedOriginalClass ||
                    dataset.tempUserSetTotalCosts || dataset.tempConstructionValues || dataset.tempConstructionIdentifiers || dataset.tempConstructionIds || dataset.tempDatasetImportFieldsId ||
                    dataset.tempIgnoreWarnings || dataset.tempLocked || dataset.tempNmdProfileSetString || dataset.tempGroupingDatasetName || dataset.tempConstituentStatus || dataset.tempVerifiedStatuses ||
                    dataset.tempUnlockedFromVerifiedStatuses || dataset.tempVerifiedFromEntityIds) {
                Integer index = 0
                def formattedAnswer
                def answerIds = dataset.answerIds

                if (!answerIds && dataset.resourceId) {
                    answerIds = [dataset.resourceId]
                }

                answerIds?.each { answer ->
                    Dataset copy = createCopy(dataset)
                    formattedAnswer = answer.replace(",", ".")
                    copy.answerIds = [answer]

                    if (formattedAnswer?.isNumber()) {
                        copy.quantity = formattedAnswer?.toDouble()
                    } else {
                        copy.quantity = null
                    }

                    if (dataset.tempAdditionalQuestionAnswers) {
                        dataset.tempAdditionalQuestionAnswers.each { key, value ->
                            String additionalQuestionValue

                            try {
                                if (value instanceof String[]) {
                                    additionalQuestionValue = value[index]?.toString()?.trim()


                                    if ("profileId".equals(key)) {
                                        copy.profileId = additionalQuestionValue
                                    } else if ("thickness_mm".equals(key) || "thickness_in".equals(key)) {
                                        Double valueAsDouble

                                        if (additionalQuestionValue && DomainObjectUtil.isNumericValue(additionalQuestionValue)) {
                                            valueAsDouble = DomainObjectUtil.convertStringToDouble(additionalQuestionValue)
                                        }

                                        if (valueAsDouble == null) {
                                            additionalQuestionValue = "thickness_mm".equals(key) ?
                                                    resourceCache.getResource(dataset)?.defaultThickness_mm?.toString() :
                                                    resourceCache.getResource(dataset)?.defaultThickness_in?.toString()
                                        }
                                    }
                                } else {
                                    additionalQuestionValue = value?.toString()?.trim()

                                    if ("profileId".equals(key)) {
                                        copy.profileId = additionalQuestionValue
                                    } else if ("thickness_mm".equals(key) || "thickness_in".equals(key)) {
                                        Double valueAsDouble

                                        if (additionalQuestionValue && DomainObjectUtil.isNumericValue(additionalQuestionValue)) {
                                            valueAsDouble = DomainObjectUtil.convertStringToDouble(additionalQuestionValue)
                                        }

                                        if (valueAsDouble == null) {
                                            additionalQuestionValue = "thickness_mm".equals(key) ?
                                                    resourceCache.getResource(dataset)?.defaultThickness_mm?.toString() :
                                                    resourceCache.getResource(dataset)?.defaultThickness_in?.toString()
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                log.trace("Cannot resolve an additional question", e)
                                additionalQuestionValue = ""
                            }

                            if (copy.additionalQuestionAnswers) {
                                copy.additionalQuestionAnswers.put((key), additionalQuestionValue)
                            } else {
                                copy.additionalQuestionAnswers = [(key): additionalQuestionValue]
                            }
                        }
                    }

                    if (dataset.tempUserGivenUnits) {
                        try {
                            copy.userGivenUnit = dataset.tempUserGivenUnits.getAt(index)
                        } catch (Exception e) {

                        }
                    }

                    if (dataset.tempSeqNrs) {
                        try {
                            copy.seqNr = dataset.tempSeqNrs.getAt(index)?.toInteger()
                        } catch (Exception e) {

                        }
                    }

                    if (dataset.tempConstructionValues) {
                        try {
                            copy.constructionValue = dataset.tempConstructionValues.getAt(index)
                        } catch (Exception e) {

                        }
                    }

                    if (dataset.tempConstructionIdentifiers) {
                        try {
                            copy.uniqueConstructionIdentifier = dataset.tempConstructionIdentifiers.getAt(index)
                        } catch (Exception e) {

                        }
                    }

                    if (dataset.tempConstructionIds) {
                        try {
                            copy.parentConstructionId = dataset.tempConstructionIds.getAt(index)
                        } catch (Exception e) {

                        }
                    }

                    if (dataset.tempPersistedOriginalClass) {
                        try {
                            copy.persistedOriginalClass = dataset.tempPersistedOriginalClass.getAt(index)
                        } catch (Exception e) {

                        }
                    }

                    if (dataset.tempDatasetImportFieldsId) {
                        try {
                            copy.datasetImportFieldsId = dataset.tempDatasetImportFieldsId.getAt(index)
                        } catch (Exception e) {

                        }
                    }

                    if (dataset.tempNmdProfileSetString && dataset.tempNmdProfileSetString?.getAt(index)) {
                        copy.nmdProfileSetString = dataset.tempNmdProfileSetString?.getAt(index)
                    }

                    if (dataset.tempGroupingDatasetName && dataset.tempGroupingDatasetName?.getAt(index)) {
                        copy.groupingDatasetName = dataset.tempGroupingDatasetName?.getAt(index)
                    }

                    if (dataset.tempUserSetCosts && !dataset.tempUserSetCosts.isEmpty()) {
                        try {
                            copy.userSetCost = "true".equals(dataset.tempUserSetCosts?.getAt(index)) ? Boolean.TRUE : Boolean.FALSE
                        } catch (Exception e) {
                        }
                    }

                    if (dataset.tempUserSetTotalCosts && !dataset.tempUserSetTotalCosts.isEmpty()) {
                        try {
                            copy.userSetTotalCost = "true".equals(dataset.tempUserSetTotalCosts?.getAt(index)) ? Boolean.TRUE : Boolean.FALSE
                        } catch (Exception e) {
                        }
                    }

                    if (dataset.tempIgnoreWarnings && !dataset.tempIgnoreWarnings.isEmpty()) {
                        try {
                            copy.ignoreWarning = "true".equals(dataset.tempIgnoreWarnings?.getAt(index)) ? Boolean.TRUE : Boolean.FALSE
                        } catch (Exception e) {
                        }
                    }

                    if (dataset.tempLocked && !dataset.tempLocked.isEmpty()) {
                        try {
                            copy.locked = "true".equals(dataset.tempLocked?.getAt(index)) ? Boolean.TRUE : Boolean.FALSE
                        } catch (Exception e) {
                        }
                    }

                    if (dataset.tempConstituentStatus && !dataset.tempConstituentStatus.isEmpty()) {
                        try {
                            copy.unlinkedFromParentConstruction = "true".equals(dataset.tempConstituentStatus?.getAt(index)) ? Boolean.TRUE : Boolean.FALSE
                        } catch (Exception e) {
                        }
                    }

                    if (dataset.tempVerifiedStatuses && !dataset.tempVerifiedStatuses.isEmpty()) {
                        try {
                            copy.verified = "true".equals(dataset.tempVerifiedStatuses?.getAt(index)) ? Boolean.TRUE : Boolean.FALSE
                        } catch (Exception e) {
                        }
                    }

                    if (dataset.tempUnlockedFromVerifiedStatuses && !dataset.tempUnlockedFromVerifiedStatuses.isEmpty()) {
                        try {
                            copy.unlockedFromVerifiedStatus = "true".equals(dataset.tempUnlockedFromVerifiedStatuses?.getAt(index)) ? Boolean.TRUE : Boolean.FALSE
                        } catch (Exception e) {
                        }
                    }

                    if (dataset.tempVerifiedFromEntityIds && !dataset.tempVerifiedFromEntityIds.isEmpty()) {
                        try {
                            copy.verifiedFromEntityId = dataset.tempVerifiedFromEntityIds.getAt(index)
                        } catch (Exception e) {
                        }
                    }

                    if (dataset.tempDatasetManualIds) {
                        try {
                            copy.manualId = dataset.tempDatasetManualIds.getAt(index)
                        } catch (Exception e) {

                        }
                    }

                    resolvedDatasets.add(copy)
                    index += 1
                }
            } else {
                resolvedDatasets.add(dataset)
            }
        }
        return resolvedDatasets
    }

    /**
     * Removes existing datasets
     * @param datasets - list of datasets for removal
     * @param queryId - dataset queryId - only datasets with this queryId will be removed
     * @param removeOnlyGeneratedFromImportDatasets - if it is true then only datasets with generatedFromImport flag will be removed
     * @param skipQuestionsPerSection - map of sectionId to set of questionIds -
     *        if it is set then datasets with these questionIds for the section will NOT be removed
     *        if list of questionIds is empty then whole section will NOT be removed
     * @return adjusted list of datasets
     */
    public Collection<Dataset> removeExistingDatasets(Collection<Dataset> datasets, String queryId, Boolean removeOnlyGeneratedFromImportDatasets = Boolean.FALSE, Map<String, List<String>> skipQuestionsPerSection = [:]) {
        if (datasets) {
            def isDatasetToRemove = { Dataset dataset ->
                boolean remove = false

                if (dataset.queryId == queryId) {

                    remove = removeOnlyGeneratedFromImportDatasets ? dataset.generatedFromImport : true

                    if (remove && skipQuestionsPerSection) {
                        List<String> skipQuestions = skipQuestionsPerSection[dataset.sectionId]
                        if (skipQuestions) {
                            remove = !skipQuestions.contains(dataset.questionId)
                        } else if (skipQuestions?.isEmpty()) {
                            remove = false
                        }
                    }
                }
                return remove
            }
            datasets.removeIf({ isDatasetToRemove(it) })
        }
        return datasets
    }

    private void resolvePoints(Dataset dataset, Indicator indicator) {
        //loggerUtil.debug(log, "Start of resolvePoints")
        def points
        QuestionAnswerChoice questionAnswerChoice
        def calculationMethod = indicator?.indicatorQueries?.find({ iq -> iq.queryId == dataset.queryId })?.queryCalculationMethod

        if (QueryCalculationMethod.POINTS.toString() == calculationMethod) {
            dataset.answerIds?.each { answerId ->
                questionAnswerChoice = questionAnswerChoiceService.getAnswerChoiceByQueryIdAndSectionIdAndAnswerId(dataset.queryId, dataset.sectionId, answerId)

                if (questionAnswerChoice) {
                    points = points ? points + questionAnswerChoice.pointsFactor : questionAnswerChoice.pointsFactor
                }
            }
        } else if (QueryCalculationMethod.SUM.toString() == calculationMethod) {
            def formattedAnswer

            dataset.answerIds?.each { answer ->
                formattedAnswer = answer.replace(",", ".")

                if (formattedAnswer?.isNumber() && formattedAnswer?.toDouble() > 0) {
                    def numericValue = formattedAnswer.toDouble()
                    points = points ? points + numericValue : numericValue
                }
            }
        }
        dataset.points = points
    }

    Entity saveDatasetsFromParameters(GrailsParameterMap parameterMap, HttpServletRequest request, String entityId,
                                      HttpSession session, Map<String, String> defaults, Boolean projectLevel = Boolean.FALSE,
                                      String originalDatasetId = null, List<String> changeSimilarDatasets = null,
                                      Indicator indicator = null, Boolean skipCalculation = Boolean.FALSE,
                                      Boolean resultsOutOfDate = Boolean.FALSE) {
        // loggerUtil.debug(log, "Start of saveDatasetsFromParameters (the main method in DatasetService)")
        // loggerUtil.info(log, "Save datasets from parameters")
        Entity entity

        if (entityId) {
            try {
                entity = Entity.get(entityId)
                def queryId = request.getParameter("queryId")

                if (!entityId) {
                    throw new RuntimeException("Missing required parameter entityId in saving datasets")
                }

                if (!queryId) {
                    throw new RuntimeException("Missing required parameter queryId in saving datasets")
                }
                def paramNames = parameterMap?.collect({ it.key.toString() })
                def datasets = []
                def sectionId
                def questionId
                def additionalQuestionId
                def tokenized
                def resourceId
                List<String> seqNos

                def month
                def quarter
                def indicatorId = request.getParameter("indicatorId")
                List<String> tempUserSetCost
                List<String> tempUserSetTotalCost
                List<String> userGivenUnits
                List<String> constructionValues
                List<String> constructionIdentifiers
                List<String> constructionIds
                List<String> persistedClass
                List<String> tempDatasetImportFieldsId
                List<String> tempDatasetIds
                List<String> tempIgnoreWarnings
                List<String> tempLocked
                List<String> tempNmdProfileSetString
                List<String> tempGroupingDatasetName
                List<String> tempVerifiedStatuses
                List<String> tempUnlockedFromVerifiedStatuses
                List<String> tempVerifiedFromEntityIds
                List<String> tempConstituentStatus

                paramNames?.each {
                    if (!Constants.NON_SAVEABLE_DATASET_PARAMETERS.contains(it)) {
                        if (it.contains(".")) {
                            tokenized = it.tokenize(".")
                            sectionId = tokenized[0]
                            questionId = tokenized[1]

                            if (questionId.contains("_manualId_") && request.getParameterValues(it)) {
                                tempDatasetIds = request.getParameterValues(it).toList()
                                questionId = questionId.split("_manualId_")[0]
                            } else {
                                tempDatasetIds = null
                            }

                            if (questionId.contains("_unit_")) {
                                if (request.getParameterValues(it).toList()) {
                                    userGivenUnits = request.getParameterValues(it).toList()
                                } else {
                                    userGivenUnits = null
                                }
                                userGivenUnits = request.getParameterValues(it).toList()
                                questionId = questionId.split("_unit_")[0]
                            } else {
                                userGivenUnits = null
                            }

                            if (questionId.contains("_seqNo_")) {
                                if (request.getParameterValues(it)) {
                                    seqNos = request.getParameterValues(it).toList()
                                } else {
                                    seqNos = null
                                }
                                questionId = questionId.split("_seqNo_")[0]
                            } else {
                                seqNos = null
                            }

                            if (questionId.contains("_ignoreWarning_")) {
                                if (request.getParameterValues(it)) {
                                    tempIgnoreWarnings = request.getParameterValues(it).toList()
                                } else {
                                    tempIgnoreWarnings = null
                                }
                                questionId = questionId.split("_ignoreWarning_")[0]
                            } else {
                                tempIgnoreWarnings = null
                            }

                            if (questionId.contains("_locked_")) {
                                if (request.getParameterValues(it)) {
                                    tempLocked = request.getParameterValues(it).toList()
                                } else {
                                    tempLocked = null
                                }
                                questionId = questionId.split("_locked_")[0]
                            } else {
                                tempLocked = null
                            }

                            if (questionId.contains("_constructionValue_")) {
                                if (request.getParameterValues(it).toList()) {
                                    constructionValues = request.getParameterValues(it).toList()
                                } else {
                                    constructionValues = null
                                }
                                questionId = questionId.split("_constructionValue_")[0]

                            } else {
                                constructionValues = null
                            }

                            if (questionId.contains("_uniqueConstructionIdentifier_")) {
                                if (request.getParameterValues(it).toList()) {
                                    constructionIdentifiers = request.getParameterValues(it).toList()
                                } else {
                                    constructionIdentifiers = null
                                }
                                questionId = questionId.split("_uniqueConstructionIdentifier_")[0]

                            } else {
                                constructionIdentifiers = null
                            }

                            if (questionId.contains("_isUnlinkedFromParentConstruction_")) {
                                if (request.getParameterValues(it).toList()) {
                                    tempConstituentStatus = request.getParameterValues(it).toList()
                                } else {
                                    tempConstituentStatus = null
                                }
                                questionId = questionId.split("_isUnlinkedFromParentConstruction_")[0]

                            } else {
                                tempConstituentStatus = null
                            }

                            if (questionId.contains("_parentConstructionId_")) {
                                if (request.getParameterValues(it).toList()) {
                                    constructionIds = request.getParameterValues(it).toList()
                                } else {
                                    constructionIds = null
                                }
                                questionId = questionId.split("_parentConstructionId_")[0]

                            } else {
                                constructionIds = null
                            }

                            if (questionId?.contains("_additional_")) {
                                additionalQuestionId = questionId.split("_additional_")[1]
                                questionId = questionId.split("_additional_")[0]
                            } else {
                                additionalQuestionId = null
                            }

                            if (questionId?.contains("_userSetCost_") && request.getParameterValues(it)) {
                                String costType = questionId.split("_userSetCost_")[1]
                                questionId = questionId.split("_userSetCost_")[0]

                                if ("costPerUnit".equals(costType)) {
                                    tempUserSetCost = request.getParameterValues(it).toList()
                                } else if ("totalCost".equals(costType)) {
                                    tempUserSetTotalCost = request.getParameterValues(it).toList()
                                }
                            } else {
                                tempUserSetCost = null
                                tempUserSetTotalCost = null
                            }

                            if (questionId?.contains("_persistedClass_") && request.getParameterValues(it)) {
                                persistedClass = request.getParameterValues(it).toList()
                                questionId = questionId.split("_persistedClass_")[0]
                            } else {
                                persistedClass = null
                            }

                            if (questionId?.contains("_datasetImportFieldsId_")) {
                                if (request.getParameterValues(it).toList()) {
                                    tempDatasetImportFieldsId = request.getParameterValues(it).toList()
                                } else {
                                    tempDatasetImportFieldsId = null
                                }
                                questionId = questionId.split("_datasetImportFieldsId_")[0]
                            } else {
                                tempDatasetImportFieldsId = null
                            }

                            if (questionId.contains("_nmdProfileSetString_") && request.getParameterValues(it)) {
                                tempNmdProfileSetString = request.getParameterValues(it).toList()
                                questionId = questionId.split("_nmdProfileSetString_")[0]
                            } else {
                                tempNmdProfileSetString = null
                            }

                            if (questionId.contains("_groupingDatasetName_") && request.getParameterValues(it)) {
                                tempGroupingDatasetName = request.getParameterValues(it).toList()
                                questionId = questionId.split("_groupingDatasetName_")[0]
                            } else {
                                tempGroupingDatasetName = null
                            }

                            if (questionId.contains("_isVerified_")) {
                                if (request.getParameterValues(it).toList()) {
                                    tempVerifiedStatuses = request.getParameterValues(it).toList()
                                } else {
                                    tempVerifiedStatuses = null
                                }
                                questionId = questionId.split("_isVerified_")[0]
                            } else {
                                tempVerifiedStatuses = null
                            }

                            if (questionId.contains("_isUnlockedFromVerifiedStatus_")) {
                                if (request.getParameterValues(it).toList()) {
                                    tempUnlockedFromVerifiedStatuses = request.getParameterValues(it).toList()
                                } else {
                                    tempUnlockedFromVerifiedStatuses = null
                                }
                                questionId = questionId.split("_isUnlockedFromVerifiedStatus_")[0]
                            } else {
                                tempUnlockedFromVerifiedStatuses = null
                            }

                            if (questionId.contains("_verifiedFromEntityId_")) {
                                if (request.getParameterValues(it).toList()) {
                                    tempVerifiedFromEntityIds = request.getParameterValues(it).toList()
                                } else {
                                    tempVerifiedFromEntityIds = null
                                }
                                questionId = questionId.split("_verifiedFromEntityId_")[0]
                            } else {
                                tempVerifiedFromEntityIds = null
                            }

                            if (tokenized[2]) {
                                resourceId = tokenized[2].trim()
                            } else {
                                resourceId = null
                            }

                            if (tokenized[3]) {
                                if (tokenized[3].contains("Q")) {
                                    quarter = tokenized[3].trim()
                                } else {
                                    month = tokenized[3].trim()
                                }
                            } else {
                                month = null
                            }
                            Dataset dataset = datasets?.find({ Dataset d ->
                                d.queryId == queryId && d.sectionId == sectionId &&
                                        d.questionId == questionId && d.resourceId == resourceId
                            })

                            if (!dataset) {
                                dataset = new Dataset()
                                dataset.queryId = queryId
                                dataset.sectionId = sectionId
                                dataset.questionId = questionId
                                dataset.resourceId = resourceId
                                datasets.add(dataset)
                            }

                            if (additionalQuestionId) {
                                if (dataset.tempAdditionalQuestionAnswers) {
                                    dataset.tempAdditionalQuestionAnswers.put((additionalQuestionId), request.getParameterValues(it))
                                } else {
                                    dataset.tempAdditionalQuestionAnswers = [(additionalQuestionId): request.getParameterValues(it)]
                                }
                            } else if (userGivenUnits && !dataset.tempUserGivenUnits) {
                                dataset.tempUserGivenUnits = new ArrayList<String>(userGivenUnits)
                            } else if (seqNos && !dataset.tempSeqNrs) {
                                dataset.tempSeqNrs = new ArrayList<String>(seqNos)
                            } else if (tempUserSetCost || tempUserSetTotalCost) {
                                if (tempUserSetCost && !dataset.tempUserSetCosts) {
                                    dataset.tempUserSetCosts = tempUserSetCost
                                } else if (tempUserSetTotalCost && !dataset.tempUserSetTotalCosts) {
                                    dataset.tempUserSetTotalCosts = tempUserSetTotalCost
                                }
                            } else if (constructionValues && !dataset.tempConstructionValues) {
                                dataset.tempConstructionValues = new ArrayList<String>(constructionValues)
                            } else if (constructionIdentifiers && !dataset.tempConstructionIdentifiers) {
                                dataset.tempConstructionIdentifiers = new ArrayList<String>(constructionIdentifiers)
                            } else if (constructionIds && !dataset.tempConstructionIds) {
                                dataset.tempConstructionIds = new ArrayList<String>(constructionIds)
                            } else if (persistedClass && !dataset.tempPersistedOriginalClass) {
                                dataset.tempPersistedOriginalClass = new ArrayList<String>(persistedClass)
                            } else if (tempDatasetImportFieldsId && !dataset.tempDatasetImportFieldsId) {
                                dataset.tempDatasetImportFieldsId = new ArrayList<String>(tempDatasetImportFieldsId)
                            } else if (tempDatasetIds && !dataset.tempDatasetManualIds) {
                                dataset.tempDatasetManualIds = new ArrayList<String>(tempDatasetIds)
                            } else if (tempIgnoreWarnings && !dataset.tempIgnoreWarnings) {
                                dataset.tempIgnoreWarnings = new ArrayList<String>(tempIgnoreWarnings)
                            } else if (tempLocked && !dataset.tempLocked) {
                                dataset.tempLocked = new ArrayList<String>(tempLocked)
                            } else if (tempNmdProfileSetString && !dataset.tempNmdProfileSetString) {
                                dataset.tempNmdProfileSetString = new ArrayList<String>(tempNmdProfileSetString)
                            } else if (tempGroupingDatasetName && !dataset.tempGroupingDatasetName) {
                                dataset.tempGroupingDatasetName = new ArrayList<String>(tempGroupingDatasetName)
                            } else if (tempConstituentStatus && !dataset.tempConstituentStatus) {
                                dataset.tempConstituentStatus = new ArrayList<String>(tempConstituentStatus)
                            } else if (tempVerifiedStatuses != null && dataset.tempVerifiedStatuses == null) {
                                dataset.tempVerifiedStatuses = new ArrayList<String>(tempVerifiedStatuses)
                            } else if (tempUnlockedFromVerifiedStatuses != null && dataset.tempUnlockedFromVerifiedStatuses == null) {
                                dataset.tempUnlockedFromVerifiedStatuses = new ArrayList<String>(tempUnlockedFromVerifiedStatuses)
                            } else if (tempVerifiedFromEntityIds != null && dataset.tempVerifiedFromEntityIds == null) {
                                dataset.tempVerifiedFromEntityIds = new ArrayList<String>(tempVerifiedFromEntityIds)
                            } else {
                                if (quarter) {
                                    dataset.quarterlyAnswers.put(quarter, request.getParameterValues(it)?.toList())
                                } else if (month) {
                                    dataset.monthlyAnswers.put(month, request.getParameterValues(it)?.toList())
                                } else {
                                    dataset.answerIds = request.getParameterValues(it)
                                }
                            }
                        }
                    }
                }

                if (!projectLevel || (projectLevel && !entity?.parentEntityId)) {
                    // To not save files from project level to child entities, only the parent
                    fileUtil.handleFiles(entity, request, datasets)
                }
                List<Dataset> queryDefaultDataset = queryDefaultDataset(entity, indicator, queryId)
                if (queryDefaultDataset && queryDefaultDataset.size() > 0) {
                    datasets.addAll(queryDefaultDataset)
                }
                datasets = removeUnneededDuplicatesFromResourceDatasets(datasets)
                entity = saveDatasets(entity, datasets, queryId, indicatorId, true, request.getSession(false), true, false,
                        skipCalculation, defaults, projectLevel, originalDatasetId, changeSimilarDatasets, false, resultsOutOfDate)
                String basicQueryId = queryService.getBasicQuery()?.queryId
                boolean isPublic = entity.datasets?.find({ d -> d.queryId == basicQueryId && d.questionId == "public" }) ? true : false
                entity.isPublic = isPublic

                if (session) {
                    String channelToken = channelFeatureService.getChannelFeature(session)?.token

                    if (channelToken) {
                        if (entity.published) {
                            entity.published.put(channelToken, isPublic ? Boolean.TRUE : Boolean.FALSE)
                        } else {
                            entity.published = [(channelToken): isPublic ? Boolean.TRUE : Boolean.FALSE]
                        }
                    }
                }

                entity = entity.merge(flush: true, failOnError: true)
            } catch (Exception e) {
                errorMessageUtil.setErrorMessage(messageSource.getMessage("error.optimisticLocking", null, LocaleContextHolder.getLocale()))
                loggerUtil.warn(log, "Error in saving datasets for entity ${entity?.name}", e)
                flashService.setErrorAlert("Error in saving datasets for entity ${entity?.name}: ${e.getMessage()}", true)
                entity = Entity.get(entityId)
            }
        } else {
            loggerUtil.error(log, "Error in saving datasets. Could not find entity with id: ${entityId}")
            flashService.setErrorAlert("Error in saving datasets. Could not find entity with id: ${entityId}", true)
        }
        return entity
    }

    private List<Dataset> queryDefaultDataset(Entity design, Indicator indicator, String queryId) {
        List<Dataset> defaultDataFromQuery = []
        try {
            if (design && indicator?.calculateByQueryDefaultIfAbsent && queryId) {
                List<String> queryToBeHandled = indicator.calculateByQueryDefaultIfAbsent
                queryToBeHandled?.each { String queryIdToCheck ->
                    Boolean noDtaForQueryExist = (design.datasets && !design.datasets.find({ queryIdToCheck.equalsIgnoreCase(it.queryId) })) || !design.datasets
                    log.info("noDtaForQueryExist ${noDtaForQueryExist}")
                    if (noDtaForQueryExist && !queryId.equalsIgnoreCase(queryIdToCheck)) {
                        Query queryToCheck = queryService.getQueryByQueryId(queryIdToCheck, true)
                        if (queryToCheck) {
                            queryToCheck.sections?.each { QuerySection section ->
                                section?.questions?.each { Question question ->
                                    if (question.defaultValue && question.defaultValue.isNumber() && question.quantity) {
                                        Dataset dataset = new Dataset()
                                        dataset.queryId = queryIdToCheck
                                        dataset.sectionId = section.sectionId
                                        dataset.questionId = question.questionId
                                        dataset.quantity = question.defaultValue.toDouble()
                                        dataset.answerIds = [question.defaultValue]
                                        defaultDataFromQuery.add(dataset)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            loggerUtil.error(log, "Error in handling queryDefaultDataset.", e)
            flashService.setErrorAlert("Error in  handling queryDefaultDataset. Issue: ${e.getMessage()}", true)
        }
        return defaultDataFromQuery

    }

    Construction saveDatasetsForConstructions(GrailsParameterMap parameterMap, HttpServletRequest request, String constructionId, HttpSession session = null, String queryId) {
        Construction construction

        if (constructionId) {
            try {
                construction = Construction.get(constructionId)

                if (construction.queryLastUpdateInfos) {
                    QueryLastUpdateInfo queryLastUpdateInfo = construction.queryLastUpdateInfos.get(queryId)

                    if (queryLastUpdateInfo) {
                        queryLastUpdateInfo.usernames.add(0, userService?.getCurrentUser()?.name)
                        queryLastUpdateInfo.updates.add(0, new Date())

                        if (queryLastUpdateInfo.updates.size() > 3) {
                            queryLastUpdateInfo.updates.remove(queryLastUpdateInfo.updates.size() - 1)
                            queryLastUpdateInfo.usernames.remove(queryLastUpdateInfo.updates.size() - 1)
                        }
                        construction.queryLastUpdateInfos.put(queryId, queryLastUpdateInfo)
                    } else {
                        construction.queryLastUpdateInfos.put(queryId, new QueryLastUpdateInfo(usernames: [userService?.getCurrentUser()?.name], updates: [new Date()]))
                    }
                } else {
                    construction.queryLastUpdateInfos = [(queryId): new QueryLastUpdateInfo(usernames: [userService?.getCurrentUser()?.name], updates: [new Date()])]
                }

                def paramNames = parameterMap?.findAll({ !"resourceIds".equals(it.key) })?.collect({
                    it.key.toString()
                })
                List<Dataset> datasets = []
                def sectionId
                def questionId
                def additionalQuestionId
                def tokenized
                def resourceId
                List<String> seqNos
                // only if we need this.  def indicatorId = "constructionFakeIndicator"
                List<String> tempUserSetCost
                List<String> tempUserSetTotalCost
                List<String> userGivenUnits
                List<String> tempDatasetIds
                List<String> persistedClass
                List<String> tempDatasetImportFieldsId
                List<String> tempGroupingDatasetName

                paramNames?.each {
                    if (!Constants.NON_SAVEABLE_DATASET_PARAMETERS.contains(it)) {

                        if (it.contains(".")) {
                            tokenized = it.tokenize(".")
                            sectionId = tokenized[0]
                            questionId = tokenized[1]

                            if (questionId.contains("_manualId_") && request.getParameterValues(it)) {
                                tempDatasetIds = request.getParameterValues(it).toList()
                                questionId = questionId.split("_manualId_")[0]
                            } else {
                                tempDatasetIds = null
                            }

                            if (questionId.contains("_unit_")) {
                                if (request.getParameterValues(it).toList()) {
                                    userGivenUnits = request.getParameterValues(it).toList()
                                } else {
                                    userGivenUnits = null
                                }
                                userGivenUnits = request.getParameterValues(it).toList()
                                questionId = questionId.split("_unit_")[0]
                            } else {
                                userGivenUnits = null
                            }

                            if (questionId.contains("_seqNo_")) {
                                if (request.getParameterValues(it)) {
                                    seqNos = request.getParameterValues(it).toList()
                                } else {
                                    seqNos = null
                                }
                                questionId = questionId.split("_seqNo_")[0]
                            } else {
                                seqNos = null
                            }

                            if (questionId?.contains("_additional_")) {
                                additionalQuestionId = questionId.split("_additional_")[1]
                                questionId = questionId.split("_additional_")[0]
                            } else {
                                additionalQuestionId = null
                            }

                            if (questionId?.contains("_userSetCost_") && request.getParameterValues(it)) {
                                String costType = questionId.split("_userSetCost_")[1]
                                questionId = questionId.split("_userSetCost_")[0]

                                if ("costPerUnit".equals(costType)) {
                                    tempUserSetCost = request.getParameterValues(it).toList()
                                } else if ("totalCost".equals(costType)) {
                                    tempUserSetTotalCost = request.getParameterValues(it).toList()
                                }
                            } else {
                                tempUserSetCost = null
                                tempUserSetTotalCost = null
                            }

                            if (questionId?.contains("_persistedClass_") && request.getParameterValues(it)) {
                                persistedClass = request.getParameterValues(it).toList()
                                questionId = questionId.split("_persistedClass_")[0]
                            } else {
                                persistedClass = null
                            }

                            if (questionId?.contains("_datasetImportFieldsId_")) {
                                if (request.getParameterValues(it).toList()) {
                                    tempDatasetImportFieldsId = request.getParameterValues(it).toList()
                                } else {
                                    tempDatasetImportFieldsId = null
                                }
                                questionId = questionId.split("_datasetImportFieldsId_")[0]
                            } else {
                                tempDatasetImportFieldsId = null
                            }

                            if (questionId.contains("_groupingDatasetName_") && request.getParameterValues(it)) {
                                tempGroupingDatasetName = request.getParameterValues(it).toList()
                                questionId = questionId.split("_groupingDatasetName_")[0]
                            } else {
                                tempGroupingDatasetName = null
                            }

                            if (tokenized[2]) {
                                resourceId = tokenized[2].trim()
                            } else {
                                resourceId = null
                            }
                            Dataset dataset = datasets?.find({ Dataset d ->
                                d.queryId == queryId && d.sectionId == sectionId &&
                                        d.questionId == questionId && d.resourceId == resourceId
                            })

                            if (!dataset) {
                                dataset = new Dataset()
                                dataset.queryId = queryId
                                dataset.sectionId = sectionId
                                dataset.questionId = questionId
                                dataset.resourceId = resourceId
                                datasets.add(dataset)
                            }

                            if (additionalQuestionId) {
                                if (dataset.tempAdditionalQuestionAnswers) {
                                    dataset.tempAdditionalQuestionAnswers.put((additionalQuestionId), request.getParameterValues(it))
                                } else {
                                    dataset.tempAdditionalQuestionAnswers = [(additionalQuestionId): request.getParameterValues(it)]
                                }
                            } else if (userGivenUnits && !dataset.tempUserGivenUnits) {
                                dataset.tempUserGivenUnits = new ArrayList<String>(userGivenUnits)
                            } else if (seqNos && !dataset.tempSeqNrs) {
                                dataset.tempSeqNrs = new ArrayList<String>(seqNos)
                            } else if (tempUserSetCost || tempUserSetTotalCost) {
                                if (tempUserSetCost && !dataset.tempUserSetCosts) {
                                    dataset.tempUserSetCosts = tempUserSetCost
                                } else if (tempUserSetTotalCost && !dataset.tempUserSetTotalCosts) {
                                    dataset.tempUserSetTotalCosts = tempUserSetTotalCost
                                }
                            } else if (tempDatasetIds && !dataset.tempDatasetManualIds) {
                                dataset.tempDatasetManualIds = new ArrayList<String>(tempDatasetIds)
                            } else if (tempGroupingDatasetName && !dataset.tempGroupingDatasetName) {
                                dataset.tempGroupingDatasetName = new ArrayList<String>(tempGroupingDatasetName)
                            } else {
                                dataset.answerIds = request.getParameterValues(it)
                            }

                        }
                    }
                }
                datasets = removeUnneededDuplicatesFromResourceDatasets(datasets)
                saveDataSetsForConstruction(construction, datasets, queryId, true, request.getSession(false), true)
            } catch (Exception e) {
                loggerUtil.error(log, "Error in saving construction", e)
                flashService.setErrorAlert("Error in saving construction: ${e.getMessage()}", true)
            }
        }
        return construction

    }

    public void convertDatasetToMetric(Dataset dataset, Double constructionMultiplier, Double divider = null) {
        if (dataset) {
            String unit = dataset.userGivenUnit
            Double quantity = dataset.quantity

            if (unit && quantity && unitConversionUtil.isImperialUnit(unit)) {
                String targetUnit = unitConversionUtil.transformImperialUnitToEuropeanUnit(unit)
                Double convertedValue = unitConversionUtil.doConversion(quantity, null, unit, null, null, null, null, null, null, targetUnit)

                if (convertedValue) {
                    if (constructionMultiplier) {
                        convertedValue = convertedValue * constructionMultiplier
                    }
                    if (divider) {
                        convertedValue = convertedValue / divider
                    }
                    dataset.quantity = convertedValue
                    dataset.answerIds = ["${convertedValue}"]
                    dataset.userGivenUnit = targetUnit
                    dataset.imperialUnit = unit
                    dataset.imperialQuantity = quantity
                }
            } else {
                Double fixedQuantity = quantity
                if (quantity && divider) {
                    fixedQuantity = quantity / divider
                }
                dataset.quantity = fixedQuantity
                dataset.answerIds = ["${fixedQuantity}"]
                dataset.imperialUnit = null
                dataset.imperialQuantity = null
            }
        }
    }

    private Construction saveDataSetsForConstruction(Construction construction, List<Dataset> datasets, String queryId,
                                                     boolean handleAdditionalQuestions, HttpSession session = null,
                                                     Boolean storeUsedUnitSystem = Boolean.TRUE,
                                                     Boolean skipCalculation = Boolean.FALSE) {

        try {
            def saveableDatasets = []
            boolean answersFound
            User user = userService.getCurrentUser()

            if (!datasets?.isEmpty()) {
                String unitSystem = user?.unitSystem

                datasets?.each { Dataset dataset ->
                    if (storeUsedUnitSystem) {
                        dataset.usedUnitSystem = unitSystem ? unitSystem : UnitConversionUtil.UnitSystem.METRIC.value
                    }
                    answersFound = false

                    if (StringUtils.isNotBlank(dataset.answerIds?.get(0)) || dataset.resourceId || !dataset.monthlyAnswers?.isEmpty() || !dataset.quarterlyAnswers?.isEmpty()) {
                        answersFound = true
                    }

                    if (answersFound) {
                        saveableDatasets.add(dataset)
                    }
                }
            }
            Collection<Dataset> finalDatasets = []
            if (!saveableDatasets?.toList()?.isEmpty()) {
                saveableDatasets = resolveResources(saveableDatasets)

                def formattedDatasets = []
                Dataset formattedDataset

                for (Dataset dataset in saveableDatasets) {
                    if (dataset.answerIds && !dataset.monthlyAnswers && !dataset.quarterlyAnswers) {
                        // We have to nullify the quantity first so that existing quantities aren't eg doubled
                        dataset.quantity = null

                        for (answer in dataset.answerIds) {
                            def formattedAnswer = answer.toString().replace(',', '.')

                            if (formattedAnswer.isNumber()) {
                                dataset.quantity = dataset.quantity != null ? dataset.quantity + formattedAnswer.toDouble() : formattedAnswer.toDouble()
                            }
                        }
                    }
                    // We have to create copy, otherwise numeric answer is back to null. Don't know the reason for this.
                    formattedDataset = new Dataset()
                    formattedDataset = dataset
                    formattedDatasets.add(formattedDataset)
                }

                if (handleAdditionalQuestions) {
                    formattedDatasets = resolveAdditionalQuestionsWithoutQuantity(formattedDatasets)
                    formattedDatasets = resolveAdditionalQuestions(formattedDatasets)
                }
                addDefaultProfilesIfMissing(formattedDatasets)

                if (finalDatasets) {
                    finalDatasets.addAll(formattedDatasets)
                } else {
                    finalDatasets = formattedDatasets
                }
            } else {
                finalDatasets = removeExistingDatasets(finalDatasets, queryId)
            }

            if (!finalDatasets) {
                construction.datasets = []
            } else {
                Double constructionMultiplier = unitConversionUtil.getConstructionMultiplierForUnitPair(construction)
                finalDatasets.each { Dataset dataset ->
                    if (!dataset.manualId) {
                        dataset.manualId = new ObjectId().toString()
                    }
                    // CONSTRUCTION/CONSTITUEN CONVERSION TO IMPERIAL IS DYNAMIC SO THEY SHOULD ALWAYS BE IN METRIC
                    convertDatasetToMetric(dataset, constructionMultiplier)
                }
                // Construction can only have valid datasets
                construction.datasets = finalDatasets.findAll({ it.resourceId })
            }
            construction = construction.merge(flush: true, failOnError: true)

        } catch (Exception e) {
            loggerUtil.error(log, "Error in saving datasets", e)
            flashService.setErrorAlert("Error in saving datasets ${e.getMessage()}", true)
        }
        return construction
    }

    DatasetImportFields getDatasetImportFieldsById(String id) {
        DatasetImportFields datasetImportFields

        if (id) {
            datasetImportFields = DatasetImportFields.findById(DomainObjectUtil.stringToObjectId(id))
        }
        return datasetImportFields
    }

    List<DatasetImportFields> getDatasetImportFieldsByIdList(List<String> idList) {
        List<DatasetImportFields> datasetImportFields

        if (idList) {
            datasetImportFields = DatasetImportFields.findAllByIdInList(idList.collect{ DomainObjectUtil.stringToObjectId(it) })
        }
        return datasetImportFields
    }

    void upDatasetImportFieldsByIdAndValueMap(Map<String,Map<String,String>> map) {
        try {
            if (map) {
                map.each { String id, Map<String, String> valueMap ->
                    if (id && valueMap) {
                        DatasetImportFields datasetImportFields = DatasetImportFields.findById(DomainObjectUtil.stringToObjectId(id))
                        if (datasetImportFields?.importFields) {
                            Map<String, String> lastImportMap = datasetImportFields.importFields.last()

                            valueMap.each { String key, Object value ->
                                lastImportMap.put(key, value.toString())
                            }
                            datasetImportFields.merge(flush:true, failOnError:true)
                        }
                    }
                }

            }

        } catch (Exception e) {
            loggerUtil.error(log, "Error in upDatasetImportFieldsByIdAndValueMap for datasets", e)
            flashService.setErrorAlert("Error in upDatasetImportFieldsByIdAndValueMap for datasets ${e.getMessage()}", true)
        }
    }

    List<Document> getInactiveDatasetImportFieldsById() {
        List<Document> datasetImportFields = DatasetImportFields.collection.find([:], [_id: 1])?.toList()
        List<Document> inactives = []
        datasetImportFields?.each { Document d ->
            Document result = Entity.collection.findOne(["datasets.datasetImportFieldsId": d._id.toString()], [_id: 1])

            if (!result) {
                inactives.add(d)
            }
        }
        return inactives
    }

    String getGroupingTypeOfDataset(Indicator indicator, List<QuerySection> sections, Dataset dataset, String fallbackString, Question groupingQuestion, Map<String, List<String>> indicatorAddQs = null, boolean fallbackToSection = false, List<Document> nmdElementList = null) {
        String groupingType = fallbackString
        List<String> addQsForThisQuery = indicatorAddQs?.get(dataset.queryId)
        if (dataset.groupingType) {
            groupingType = dataset.groupingType
        } else {
            if (groupingQuestion && ((!groupingQuestion?.privateClassificationQuestion && addQsForThisQuery?.contains(groupingQuestion.questionId) && !fallbackToSection) || (groupingQuestion.privateClassificationQuestion && addQsForThisQuery?.contains(com.bionova.optimi.core.Constants.ORGANIZATION_CLASSIFICATION_ID)))) {
                String userAnswer = dataset.additionalQuestionAnswers?.get(groupingQuestion.questionId)
                //sw-1597
                NmdElement nmdElement
                if(nmdElementList && userAnswer && groupingQuestion.questionId == com.bionova.optimi.core.Constants.SFB_CODE_QUESTIONID){
                     nmdElement = nmdElementList.find{it.elementId == Integer.parseInt(userAnswer)}
                }
                else if(userAnswer && groupingQuestion.questionId == com.bionova.optimi.core.Constants.SFB_CODE_QUESTIONID){
                     nmdElement = nmdElementService.getNmdElement(Integer.parseInt(userAnswer))
                }
                if (nmdElement) {
                    userAnswer = nmdElement.code + ' ' + nmdElement.name
                }
                if (groupingQuestion.choices && userAnswer) {
                    // .find() threw ConcurrentModificationException, .collect() to fix, but why?
                    groupingType = groupingQuestion.choices?.collect({ it })?.find({
                        userAnswer?.equalsIgnoreCase(it.answerId)
                    })?.localizedAnswer ?: fallbackString

                } else {
                    if (userAnswer) {
                        groupingType = userAnswer
                    } else {
                        groupingType = fallbackString
                    }

                }
            } else if (getQuestion(dataset)) {
                groupingType = questionService.getLocalizedQuestionShort(getQuestion(dataset)) ?: fallbackString
            } else if (dataset.sectionId) {
                groupingType = resolveSectionLocalizedName(sections, dataset.sectionId, fallbackString)
            }
        }
        return groupingType
    }

    String resolveSectionLocalizedName(List<QuerySection> sections, String sectionId, String fallbackString) {
        if (!sections || !sectionId) {
            return fallbackString
        }
        String sectionName = querySectionService.getLocalizedShortName(sections.find {
            it.sectionId?.equals(sectionId)
        })
        return sectionName ?: fallbackString
    }

    String getRenderGroupingDatasetRow(Dataset d, Question question, List<Question> additionalQuestions, Resource countryResource, Entity entity, Indicator indicator, String sectionId, EolProcessCache eolProcessCache) {
        String tableData = ""

        if (d) {
            tableData = "<tr id=\"groupingRow${d.manualId}\" data-manualId=\"${d.manualId}\" class=\"highlightHover\">"
            tableData = tableData + "<td>${optimiResourceService.getLocalizedName(getResource(d))}</td>"

            if (question?.quantity) {
                tableData = tableData + "<td class=\"right-align\">${getRenderGroupingDatasetQuantity(d, sectionId, question?.questionId, indicator)}</td><td>${getRenderGroupingDatasetUnit(d, sectionId, question?.questionId, indicator)}</td>"
            }

            Resource resource = getResource(d)
            ResourceType resourceType
            ResourceType resourceSubType

            if (resource) {
                resourceType = resource.getResourceTypeObject()
                resourceSubType = resourceService.getSubType(resource)
            }

            additionalQuestions?.each { Question additionalQuestion ->
                int nameMaxLength = additionalQuestion.inputWidth ? (additionalQuestion.inputWidth / 4) <= 3 ? 3 : (additionalQuestion.inputWidth / 4) : 20 // Magic

                String answer = d?.additionalQuestionAnswers?.get(additionalQuestion.questionId)

                if (answer) {
                    if (additionalQuestion.questionId == com.bionova.optimi.core.Constants.LOCAL_COMP_QUESTIONID && answer == "noLocalCompensation") {
                        tableData = tableData + "<td class=\"right-align\">${getLocaleMessage('notApplied', null, locale)}</td>"
                    } else if (additionalQuestion.questionId == com.bionova.optimi.core.Constants.EOL_QUESTIONID) {
                        EolProcess eolProcess

                        if(eolProcessCache){
                            eolProcess = eolProcessService.getEolProcessForDataset(d, resource, resourceSubType, countryResource, false, null, eolProcessCache)
                        } else {
                            //can't implement cache for renderRowGroupingRow, stay as it is now
                            eolProcess = eolProcessService.getEolProcessForDataset(d, resource, resourceSubType, countryResource)
                        }

                        tableData = tableData + "<td class=\"right-align\">" +
                                "${stringUtilsService.abbr(eolProcessService.getLocalizedName(eolProcess), nameMaxLength, true)}</td>"
                    } else if (additionalQuestion.questionId == com.bionova.optimi.core.Constants.LOCAL_COMP_ENERGY_PROFILE_QUESTIONID) {
                        String energyProfileString = ""
                        if (answer && answer.tokenize(".")?.size() == 2) {
                            energyProfileString = "${answer.tokenize(".")[1]}"
                        }
                        tableData = tableData + "<td class=\"right-align\">${energyProfileString}</td>"
                    } else if (additionalQuestion.resourceGroups) {
                        tableData = tableData + "<td class=\"right-align\">${stringUtilsService.abbr(optimiResourceService.getLocalizedName(Resource.findByResourceIdAndActive(answer, true)), nameMaxLength, true)}</td>"
                    } else if (additionalQuestion.choices) {
                        tableData = tableData + "<td class=\"right-align\">${stringUtilsService.abbr(additionalQuestion.choices.find({ it.answerId?.equals(answer) })?.localizedAnswer, nameMaxLength, true)}</td>"
                    } else if (additionalQuestion.isTransportQuestion) {
                        if ("default".equals(answer)) {
                            String transportString = entity?.getDefaultTransportFromSubType(resource)
                            tableData = tableData + "<td class=\"right-align\">${transportString}</td>"
                        } else {
                            tableData = tableData + "<td class=\"right-align\">${answer}</td>"
                        }
                    } else if (additionalQuestion.questionId == "serviceLife") {
                        String serviceLife = "${newCalculationServiceProxy.getServiceLife(d, resource, resourceType, entity, indicator)?.toInteger() ?: ""}"

                        if ("-1" == serviceLife) {
                            tableData = tableData + "<td class=\"right-align\">${getLocaleMessage("resource.serviceLife.as_building", null, locale)}</td>"
                        } else {
                            tableData = tableData + "<td class=\"right-align\">${serviceLife}</td>"
                        }
                    } else {
                        tableData = tableData + "<td class=\"right-align\">${stringUtilsService.abbr(answer, nameMaxLength, true)}</td>"
                    }
                } else {
                    tableData = tableData + "<td class=\"right-align\"></td>"
                }
            }
            tableData = tableData + "<td class=\"right-align\"><a href=\"javascript:\" onclick=\"removeGroupingRow('groupingRow${d.manualId}','${d.manualId}')\">${getLocaleMessage("remove_from_group", null, getLocale())}</a></td>"
            tableData = tableData + "</tr>"
        }
        return tableData
    }

    String getRenderGroupingDatasetHeader(Question question, Query query, QuerySection section, List<Question> additionalQuestions,
                                          Entity parentEntity, Entity design, Indicator indicator, String existingParentConstructionDatasetId,
                                          Boolean createConstructionsAllowed = Boolean.FALSE, Boolean publicGroupAllowed = Boolean.FALSE) {
        String tableConstructionRow = ""
        try {
            if (query && question && section && parentEntity && design && indicator && existingParentConstructionDatasetId && additionalQuestions) {
                User user = userService.getCurrentUser()
                Account account = userService.getAccount(user)
                Question showInMappingQuestion = additionalQuestions.find({ it.showInMapping })
                Question privateClassificationQuestion = additionalQuestions.find({ it.questionId == com.bionova.optimi.core.Constants.ORGANIZATION_CLASSIFICATION_ID })
                Dataset constructionDataset = design.datasets.find({ it.manualId == existingParentConstructionDatasetId })
                List<AccountImages> brandImages = []
                Resource constructionAsResource = getResource(constructionDataset)
                Query constructionCreationQuery = queryService.getQueryByQueryId(Query.QUERYID_CONSTRUCTION_CREATOR, true)
                List<String> unitForConstruction = queryService.getAllowedUnitsByQueryAndUserSettings(constructionCreationQuery, user)
                List<ResourceType> constructionResourceTypes = resourceTypeService.getResourceSubTypesByResourceType("construction")
                if (user.internalUseRoles) {
                    brandImages = accountImagesService.getAllAccountImagesByType(com.bionova.optimi.core.Constants.AccountImageType.BRANDINGIMAGE.toString())
                } else {
                    brandImages = accountImagesService.getAccountImagesByAccountIdAndType(account?.id?.toString(), com.bionova.optimi.core.Constants.AccountImageType.BRANDINGIMAGE.toString())
                }
                String idForNewHeading = "${question?.questionId}TempHead"
                RenderTagLib g = grailsApplication.mainContext.getBean('org.grails.plugins.web.taglib.RenderTagLib')

                tableConstructionRow = g.render(template: "/query/rowGroupingConstruction", model: [question                     : question, unitForConstruction: unitForConstruction, entity: design,
                                                                                                    constructionResourceTypes    : constructionResourceTypes, user: user, query: query,
                                                                                                    parentEntity                 : parentEntity, showInMappingQuestion: showInMappingQuestion,
                                                                                                    brandImages                  : brandImages, constructionAsResource: constructionAsResource, idForNewHeading: idForNewHeading,
                                                                                                    privateClassificationQuestion: privateClassificationQuestion, existingParentConstructionDatasetId: existingParentConstructionDatasetId,
                                                                                                    indicator                    : indicator, createConstructionsAllowed: createConstructionsAllowed, publicGroupAllowed: publicGroupAllowed,
                                                                                                    account                      : account, mainSectionId: section?.sectionId, constructionDataset: constructionDataset])
            }

        } catch (Exception e) {
            loggerUtil.error(log, "Error in getRenderGroupingDatasetHeader", e)
            flashService.setErrorAlert("Error in getRenderGroupingDatasetHeader: ${e.getMessage()}", true)
        }
        return tableConstructionRow
    }

    /**
     * Render the quantity text link and hidden input for grouping dataset
     * @param dataset
     * @return
     */
    String getRenderGroupingDatasetQuantity(Dataset dataset, String sectionId, String questionId, Indicator indicator) {
        return groovyPageRenderer.render(template: "/query/inputs/quantityAndUnit/groupingRow/quantityGroupingRow", model: [quantityAnswer                 : dataset?.answerIds?.size() > 0 ? dataset?.answerIds[0] : '',
                                                                                                                            dataset                        : dataset,
                                                                                                                            allowEditingConstituentQuantity: indicator?.allowEditingConstituentQuantity,
                                                                                                                            resource                       : getResource(dataset),
                                                                                                                            fieldName                      : "${sectionId}.${questionId}"])
    }

    /**
     * Render the unit text link and hidden select for grouping dataset
     * @param dataset
     * @return
     */
    String getRenderGroupingDatasetUnit(Dataset dataset, String sectionId, String questionId, Indicator indicator) {
        Resource resource = getResource(dataset)
        return groovyPageRenderer.render(template: "/query/inputs/quantityAndUnit/groupingRow/unitGroupingRow", model: [useUserGivenUnit               : unitConversionUtil.useUserGivenUnit(resource, dataset),
                                                                                                                        allowEditingConstituentQuantity: indicator?.allowEditingConstituentQuantity,
                                                                                                                        resource                       : resource,
                                                                                                                        unitFieldName                  : "${sectionId}.${questionId}_unit_${resource?.resourceId ? '.' + resource?.resourceId : ''}",
                                                                                                                        dataset                        : dataset])
    }

    private getLocaleMessage(String key, def args = null, Locale locale) {
        String value = key
        try {
            value = messageSource.getMessage(value, args, locale)
        } catch (Exception e) {
            // No localization found with key
        }
        return value
    }

    private Locale getLocale() {
        GrailsWebRequest webRequest = WebUtils.retrieveGrailsWebRequest()
        HttpServletRequest request = webRequest.getRequest()
        HttpServletResponse response = webRequest.getResponse()
        HttpSession session = webRequest.getSession()
        return localeResolverUtil.resolveLocale(session, request, response)
    }

    // Use this to sum of multiple  additional answer of in multiple questions with same queryId, sectionId, questionId
    Double getSumAllAdditionalAnswerFromDatasets(Set<Dataset> datasets, String queryId, String sectionId, String questionId, String addQuestionId) {
        Double total = 0.0

        if (datasets && queryId && sectionId && questionId && addQuestionId) {
            try {
                total = datasets.findAll({
                    queryId == it.queryId && sectionId == it.sectionId && questionId == it.questionId && DomainObjectUtil.isNumericValue(it.additionalQuestionAnswers?.get(addQuestionId)?.toString())
                })?.sum({DomainObjectUtil.convertStringToDouble(it.additionalQuestionAnswers?.get(addQuestionId)?.toString())}) ?: 0.0
            } catch (e) {
                loggerUtil.warn(log, "ERROR: Exception for getSumAllAdditionalAnswerFromDesign, queryId ${queryId}, sectionId ${sectionId}, questionId ${questionId}, additionalQuestionId ${addQuestionId}", e)
                flashService.setErrorAlert("Error in getSumAllAdditionalAnswerFromDesign, queryId ${queryId}, sectionId ${sectionId}, questionId ${questionId}, additionalQuestionId ${addQuestionId}: ${e.getMessage()}", true)
            }
        }
        return total
    }

    // Use this to sum of multiple answer in multiple datasets with same queryId, sectionId, questionId
    Double getSumAllAnswerFromDatasets(Set<Dataset> datasets, String queryId = null, String sectionId = null, String questionId = null) {
        Double total = 0.0

        if (datasets) {
            try {
                if (queryId && sectionId && questionId) {
                    datasets = datasets.findAll { queryId == it.queryId && sectionId == it.sectionId && questionId == it.questionId }
                }
                total = datasets.findAll {
                    getAnswerFromDataset(it)?.replace(',', '.')?.isDouble()
                }?.sum { getAnswerFromDataset(it)?.replace(',', '.')?.toDouble() }
            } catch (e) {
                loggerUtil.warn(log, "ERROR: Exception for getSumAllAnswerFromDatasets, queryId ${queryId}, sectionId ${sectionId}, questionId ${questionId}", e)
                flashService.setErrorAlert("Error in getSumAllAnswerFromDatasets, queryId ${queryId}, sectionId ${sectionId}, questionId ${questionId}: ${e.getMessage()}", true)
            }
        }
        return total
    }

    @CompileStatic
    String getAdditionalAnswerFromDataset(Dataset dataset, String additionalQuestionId) {
        return dataset?.additionalQuestionAnswers?.get(additionalQuestionId)?.toString()
    }

    @CompileStatic
    Double getAdditionalAnswerFromDatasetAsDouble(Dataset dataset, String additionalQuestionId) {
        return DomainObjectUtil.convertStringToDouble(getAdditionalAnswerFromDataset(dataset, additionalQuestionId), true)
    }

    @CompileStatic
    Integer getAdditionalAnswerFromDatasetAsInteger(Dataset dataset, String additionalQuestionId) {
        return getAdditionalAnswerFromDatasetAsDouble(dataset, additionalQuestionId)?.toInteger()
    }

    /**
     * Get the main answer from dataset
     * @param dataset
     * @param excludeAnswers answers that are not valid (optional)
     * @return
     */
    @CompileStatic
    String getAnswerFromDataset(Dataset dataset, List<String> excludeAnswers = null) {
        if (!dataset?.answerIds) {
            return null
        }

        String answer = dataset.answerIds[0]?.toString()

        if (!answer || excludeAnswers?.contains(answer)) {
            return null
        }

        return answer
    }

    @CompileStatic
    Double getAnswerFromDatasetAsDouble(Dataset dataset, List<String> excludeAnswers = null) {
        String answer = getAnswerFromDataset(dataset, excludeAnswers)?.trim()?.replace(',', '.')
        if (answer?.isDouble()) {
            return answer.toDouble()
        } else {
            return null
        }
    }


/*
    getFECAreaByZoneAndQuestionId:
    @parameter: datasets: design dataset
    @parameter: questionId: questionId for area (SDP)
    @parameter: key: if key valid then return sum of all areas with that key, otherwise return sum of area with that questionId
*/

    Double getFECAreaByZoneAndQuestionId(Set<Dataset> datasets, String queryId, String sectionId, String questionId, String zoneId = null, String addQId = null) {
        Double totalArea = 0.0
        List<Dataset> foundDatasets = []
        try {
            if (datasets && questionId) {
                if (zoneId) {
                    foundDatasets = datasets?.findAll({ it && it.questionId == questionId && it.queryId == queryId && it.sectionId == sectionId && it.additionalQuestionAnswers?.get(FrenchConstants.BUILDING_ZONES_FEC_QUESTIONID)?.toString() == zoneId })?.toList()
                } else {
                    foundDatasets = datasets?.findAll({ it && it.questionId == questionId && it.queryId == queryId && it.sectionId == sectionId })?.toList()
                }
            }
            if (foundDatasets) {
                if (addQId && addQId == FrenchConstants.FEC_ADDITIONAL_QUESTIONID_SRT) {
                    List<Dataset> datasetsWithValidSrtAnswer = foundDatasets.findAll{ DomainObjectUtil.isNumericValue(it.additionalQuestionAnswers?.get(addQId)?.toString()) }
                    totalArea = datasetsWithValidSrtAnswer?.sum{ DomainObjectUtil.convertStringToDouble(it.additionalQuestionAnswers?.get(addQId)?.toString()) } ?: 0.0
                } else {
                    totalArea = foundDatasets?.sum({ it.quantity }) ?: 0.0
                }
            }
        } catch (e) {
            loggerUtil.error(log, "Error in getFECAreaByZoneAndQuestionId:", e)
            flashService.setErrorAlert("Error in getFECAreaByZoneAndQuestionId: ${e.getMessage()}", true)
        }

        return totalArea
    }
    /*
    getValidFECZones:
    @parameter: datasets: design dataset
    @parameter: addQ: additionalQuestion where zone value is located
    @parameter: zonesForImporting: Boolean if true then consider all datasets , if false then consider dataset in FEC_QUERYID only
    @parameter: takeRawList: Boolean if true take raw list for error check in query page or sometime in calculation based on resultCategory.allowUnassignedZone
    @parameter: ignoreZeroZone: = true then return valid not null zones and ignore FEC_UNASSIGNED_ZONE value (used for XML service)
    @parameter: ignoreZeroZone = false then execute this logic: prioritize other valid zones first and only allow FEC_UNASSIGNED_ZONE
                zone when no other zones exist. Do not allow both FEC_UNASSIGNED_ZONE and valid zones in the same list
    */

    List<String> getValidFECZones(Set<Dataset> datasets, String addQ = null, Boolean ignoreZeroZone = Boolean.FALSE,
                                  Boolean zonesForImporting = Boolean.FALSE, Boolean takeRawList = Boolean.FALSE, String queryIdFEC = null) {
        List<String> zoneList = []
        String addQId = addQ ?: FrenchConstants.BUILDING_ZONES_FEC_QUESTIONID
        queryIdFEC = queryIdFEC ?: FrenchConstants.PROJECT_DESCRIPTION_FEC_QUERYID
        try {
            if (datasets) {
                if (zonesForImporting) {
                    zoneList = datasets?.findAll({ it.additionalQuestionAnswers?.get(addQId) })?.collect({ it.additionalQuestionAnswers?.get(addQId) as String })?.flatten()?.toList() ?: []
                } else {
                    zoneList = datasets?.findAll({ it.queryId == queryIdFEC && it.additionalQuestionAnswers?.get(addQId) })?.collect({ it.additionalQuestionAnswers?.get(addQId) as String })?.flatten()?.toList() ?: []
                }
                if (!takeRawList && zoneList?.size() > 0) {
                    zoneList = zoneList?.unique()
                    if (ignoreZeroZone || zoneList.size() > 1) {
                        zoneList = zoneList.findAll({ !FrenchConstants.FEC_UNASSIGNED_ZONE.equals(it) }) ?: []
                    }
                }

            }
        } catch (e) {
            loggerUtil.error(log, "Error in getValidFECZones:", e)
            flashService.setErrorAlert("Error in getValidFECZones: ${e.getMessage()}", true)
        }
        return zoneList
    }

    Double getValueForZone(Set<Dataset> zoneDataSets, CalculationResult calculationResult) {
        Double valueForZone
        List<String> manualIdsList = []
        try {
            manualIdsList = zoneDataSets?.collect({ it.manualId })?.toList()
            valueForZone = calculationResult?.calculationResultDatasets?.findAll({ manualIdsList?.contains(it.key) })?.collect({ it.value.result && !it.value.result.isNaN() && !it.value.result.isInfinite() ? it.value.result : 0 })?.sum()
        } catch (e) {
            loggerUtil.error(log, "Error while getting value for zone datasets:", e)
            flashService.setErrorAlert("Error while getting value for zone datasets: ${e.getMessage()}", true)
        }
        return valueForZone
    }

    public Set<Dataset> getZoneDataSets(String zoneId, Set<Dataset> designDataSet, String calculateForUniqueAnswersOnly = null) {
        Set<Dataset> zoneDataSets
        String addQId = calculateForUniqueAnswersOnly ?: FrenchConstants.BUILDING_ZONES_FEC_QUESTIONID
        try {
            zoneDataSets = designDataSet.findAll({ it.additionalQuestionAnswers?.get(addQId)?.toString() == zoneId.toString() })
        } catch (e) {
            loggerUtil.error(log, "Error while getting value for zone datasets:", e)
            flashService.setErrorAlert("Error while getting value for zone datasets: ${e.getMessage()}", true)
        }
        return zoneDataSets
    }

    /**
     * Get the dataset for question.
     * @param questionId
     * @param linkedQuestions
     * @param sendMeData
     * @param entity
     * @param parentEntity
     * @param resourceId
     * @return
     */
    Dataset getQuestionAnswerDataset(String questionId, String queryId, String sectionId, List<Question> linkedQuestions, Boolean sendMeData, Entity entity, Entity parentEntity, String resourceId) {
        Dataset dataset = null
        try {
            if (sendMeData) {
                Question linkedQuestion = linkedQuestions?.find({ questionId?.equals(it.mapToResourceParameter) })

                dataset = getUniqueDataset(entity, linkedQuestion?.queryId, linkedQuestion?.sectionId, linkedQuestion?.questionId, resourceId)
                if (!dataset) {
                    dataset = getUniqueDataset(parentEntity, linkedQuestion?.queryId, linkedQuestion?.sectionId, linkedQuestion?.questionId, resourceId)
                }
            } else {
                dataset = getUniqueDataset(entity ?: parentEntity, queryId, sectionId, questionId, resourceId)
            }
        } catch (e) {
            loggerUtil.error(log, "Error in getQuestionAnswerDataset for questionId $questionId, entityId ${entity?.id}, parentEntityId ${parentEntity?.id}, resourceId $resourceId", e)
            flashService.setErrorAlert("Error in getQuestionAnswerDataset for questionId $questionId, entityId ${entity?.id}, parentEntityId ${parentEntity?.id}, resourceId $resourceId: ${e.getMessage()}", true)
        }

        return dataset
    }

    /**
     * Dataset can be verified or unlockedFromVerifiedStatus.
     * This method generates the appropriate icon for it.
     * Note: icon doesn't have any functions (no onclick)
     * @param dataset
     * @return html icon as string
     */
    String generateVerifiedIconFromDataset(Dataset dataset) {
        String icon = ''
        if (dataset) {
            if (dataset.unlockedFromVerifiedStatus) {
                icon = groovyPageRenderer.render(template: "/query/icons/unverifiedIcon", model: [dataset: dataset])
            } else if (dataset.verified) {
                icon = groovyPageRenderer.render(template: "/query/icons/verifiedIcon", model: [dataset: dataset])
            }
        }
        return icon
    }

    /**
     * Sets {@link Dataset#parentConstructionDataset} (which is transient) for a {@link Dataset} if the latter represents
     * a construction constituent.
     *
     * @param dataset which a {@link Dataset#parentConstructionDataset} is determined for
     * @param datasets to be searhed for the parent {@link Dataset} (construction)
     */
    void setParentConstructionDataset(Dataset dataset, Set<Dataset> datasets) {
        if (dataset?.constituent) {
            Dataset parentDataset = datasets?.find { it?.uniqueConstructionIdentifier == dataset.uniqueConstructionIdentifier && it?.construction }
            if (parentDataset) {
                dataset.parentConstructionDataset = parentDataset
            }
        }
    }

    /**
     * Logic is moved from _resourcerow.gsp.
     * @param dataset
     * @param useUserGivenUnit a map from unitConversionUtil.useUserGivenUnit()
     * @param constructionUnit
     * @return
     */
    String getUnitToShow(Dataset dataset, Map<Boolean, List<String>> useUserGivenUnit, String constructionUnit) {
        /*
            Original logic on gsp
              <g:set var="unitToShow"/>
              <g:if test="${parentConstructionId || constructionResource}">
                <g:set var="unitToShow" value="${constructionUnit}"/>
              </g:if>
              <g:else>
                <g:each in="${useUserGivenUnit}" var="use">
                <g:set var="unitToShow" value="${use.value[0]}"/>
                </g:each>
              </g:else>
         */
        String unitToShow = ''
        if (dataset?.parentConstructionId || dataset?.construction) {
            unitToShow = constructionUnit ?: ''
        } else {
            useUserGivenUnit?.each { Boolean use, List<String> units ->
                if (units?.size() > 0) {
                    unitToShow = units[0]
                }
            }
        }
        return unitToShow
    }

    Dataset getDefaultLocalCompensationCountryDataset(Entity parentEntity) {
        return parentEntity?.datasets?.find({ com.bionova.optimi.core.Constants.LCA_PARAMETERS_QUERYID.equals(it.queryId) && com.bionova.optimi.core.Constants.CALC_DEFAULTS_SECTIONID.equals(it.sectionId) && com.bionova.optimi.core.Constants.LOCAL_COMP_TARGET_QUESTIONID.equals(it.questionId) })
    }

    String getDefaultLocalCompensationCountry(Entity parentEntity) {
        return getDefaultLocalCompensationCountryDataset(parentEntity)?.answerIds?.getAt(0)
    }

    String getDefaultLocalCompensationMethodVersion(Entity parentEntity) {
        return parentEntity?.datasets?.find({ com.bionova.optimi.core.Constants.LCA_PARAMETERS_QUERYID.equals(it.queryId) && com.bionova.optimi.core.Constants.CALC_DEFAULTS_SECTIONID.equals(it.sectionId) && com.bionova.optimi.core.Constants.LOCAL_COMP_METHOD_VERSION_QUESTIONID.equals(it.questionId) })?.answerIds?.getAt(0)
    }

    int getUniteUfFromDataset(Dataset dataset) {
        int uniteUf = 27 // "-"

        if (dataset && dataset.userGivenUnit) {
            String unit = unitConversionUtil.transformImperialUnitToEuropeanUnit(dataset.userGivenUnit)?.trim()

            if (unit) {
                uniteUf = FrenchConstants.UNITE_UFS.get(unit.toLowerCase()) ?: 27
            }
        }
        return uniteUf
    }

    String getValueFromDataset(Entity entity, String queryId, String sectionId, String questionId) {
        List<Dataset> datasets = null
        String value = null

        if (entity && queryId && sectionId && questionId) {
            datasets = getDatasetsByEntityQueryIdSectionIdAndQuestion(entity, queryId, sectionId, questionId)
        }

        if (!datasets && entity.parentEntityId) {
            Entity parent = entity.getParentById()
            datasets = getDatasetsByEntityQueryIdSectionIdAndQuestion(parent, queryId, sectionId, questionId)
        }

        if (datasets) {
            Dataset dataset = datasets.find({
                it.queryId == queryId && it.sectionId == sectionId && it.questionId == questionId
            })
            value = dataset.answerIds[0]
        }
        return value
    }

    @CompileStatic
    String getZoneOfDataset(Dataset dataset) {
        return dataset?.additionalQuestionAnswers?.get(FrenchConstants.BUILDING_ZONES_FEC_QUESTIONID)?.toString()
    }

    @CompileStatic
    String getLotOfDataset(Dataset dataset) {
        return dataset?.additionalQuestionAnswers?.get(FrenchConstants.LOTS_FRANCE_EC_QUESTIONID)?.toString()
    }

    @CompileStatic
    String getSousLotOfDataset(Dataset dataset) {
        return dataset?.additionalQuestionAnswers?.get(FrenchConstants.SUB_SECTIONS_FEC_QUESTIONID)?.toString()
    }

    /**
     * For french tool, to manually set the zone to dataset
     * @param dataset
     * @param zoneId
     */
    void setZoneOfDataset(Dataset dataset, String zoneId) {
        if (!dataset || !zoneId) {
            return
        }
        if (dataset.additionalQuestionAnswers) {
            dataset.additionalQuestionAnswers.put(FrenchConstants.BUILDING_ZONES_FEC_QUESTIONID, zoneId)
        } else {
            dataset.additionalQuestionAnswers = [(FrenchConstants.BUILDING_ZONES_FEC_QUESTIONID): zoneId]
        }
    }

    /**
     * Put the dataset to datasetsByKey map with the common key
     * @param dataset
     * @param datasetsByKey must be instantiated before passing to this method
     */
    void groupDatasetSameKey(Dataset dataset, String key, Map<String, Set<Dataset>> datasetsByKey) {
        if (!dataset || datasetsByKey == null || !key) {
            return
        }

        Set<Dataset> datasetsSameKey = datasetsByKey.get(key)
        if (!datasetsSameKey) {
            datasetsSameKey = new HashSet<Dataset>()
        }
        datasetsSameKey.add(dataset)
        datasetsByKey.put(key, datasetsSameKey)
    }

    /**
     * Put the dataset to datasetsByKey map with the common key
     * @param dataset
     * @param datasetsByKey must be instantiated before passing to this method
     */
    void groupDatasetSameKey(Dataset dataset, Integer key, Map<Integer, Set<Dataset>> datasetsByKey) {
        if (!dataset || datasetsByKey == null || key == null) {
            return
        }

        Set<Dataset> datasetsSameKey = datasetsByKey.get(key)
        if (!datasetsSameKey) {
            datasetsSameKey = new HashSet<Dataset>()
        }
        datasetsSameKey.add(dataset)
        datasetsByKey.put(key, datasetsSameKey)
    }

    /**
     * Check if dataset is for a specific zone or all zones (for French tools)
     * @param dataset
     * @param zoneId
     * @return
     */
    boolean isDatasetForZone(Dataset dataset, String zoneId) {
        if (dataset && zoneId) {
            String zoneOfDataset = getZoneOfDataset(dataset)
            return !zoneOfDataset || zoneId == zoneOfDataset || FrenchConstants.FEC_UNASSIGNED_ZONE == zoneOfDataset
        }
        return false
    }

    /**
     * This method is not completed, add new fields that needs to be copied
     * @param original
     * @return
     */
    Dataset copyDataset(Dataset original) {
        if (!original) {
            return null
        }

        Dataset copy = new Dataset()
        copy.manualId = UUID.randomUUID().toString()
        copy.queryId = original.queryId
        copy.sectionId = original.sectionId
        copy.questionId = original.questionId
        copy.resourceId = original.resourceId
        copy.profileId = original.profileId
        copy.answerIds = original.answerIds
        copy.quantity = original.quantity
        if (original.additionalQuestionAnswers) {
            copy.additionalQuestionAnswers = new LinkedHashMap<String, Object>(original.additionalQuestionAnswers)
        }
        return copy
    }

    String getLocalizedLabel(Dataset dataset) {
        if(!dataset) {
            return
        }

        Resource r = getResource(dataset)

        String label = "${optimiResourceService.getLocalizedName(r) ?: ''}" +
                "${r?.technicalSpec ? ', ' + r?.technicalSpec : ''}" +
                "${r?.commercialName ? ', ' + r?.commercialName : ''}" +
                "${r?.manufacturer ? ' (' + r?.manufacturer + ')' : ''}"

        return label
    }

    Map<String, Object> getAdditionalQuestionAnswersForReport(Entity childEntity, Indicator indicator, Dataset dataset) {
        Map<String, Object> resolvedAnswers = new LinkedHashMap<String, Object>()

        Resource resource = getResource(dataset) // no point to use ResourceCache here
        Question question = getQuestion(dataset)
        Map<String, Object> additionalQuestionAnswers = dataset.additionalQuestionAnswers

        if (additionalQuestionAnswers) {
            additionalQuestionAnswers.each { String key, Object value ->
                if (key.equals("serviceLife")) {
                    resolvedAnswers.put(key, newCalculationServiceProxy.getServiceLife(dataset, resource, childEntity, indicator)?.toString())
                } else if (com.bionova.optimi.core.Constants.TransportDistanceQuestionId.list().contains(key)) {
                    if ("default".equals(value?.toString())) {
                        resolvedAnswers.put(key, childEntity?.getDefaultTransportFromSubType(resource))
                    } else {
                        resolvedAnswers.put(key, value)
                    }
                } else {
                    resolvedAnswers.put(key, value)
                }
            }
        }

        if (indicator && question) {
            List<Question> singlecheckboxQuestions = question.getAdditionalQuestions(indicator, null, null, null)?.
                    findAll({ Question q -> "singlecheckbox".equals(q?.inputType) })

            if (singlecheckboxQuestions) {
                singlecheckboxQuestions.each {
                    if (!resolvedAnswers.get(it.questionId)) {
                        resolvedAnswers.put(it.questionId, "false")
                    }
                }
            }
        }
        return resolvedAnswers
    }

    /**
     * For handling the list of transport questions ids that needed to populate default values.
     * We only populate default for transport distance leg 2 if the Transport scenario in LCA params is UK/RICS.
     * @param parentEntity
     * @param transportationQuestionsForPopulatingDefaults
     */
    List<String> handlePopulatingDefaultsForTransportationLeg2(Entity parentEntity, List<String> transportationQuestionsForPopulatingDefaults) {
        if (!parentEntity || !transportationQuestionsForPopulatingDefaults) {
            return transportationQuestionsForPopulatingDefaults
        }

        String defaultTransportValueFromLcaParams = parentEntity.defaults?.get(Constants.DEFAULT_TRANSPORT)

        if (!defaultTransportValueFromLcaParams) {
            // return as is since there's no defaults in parent entity for some reason
            return transportationQuestionsForPopulatingDefaults
        }

        // make a copy since quite often list of transport question might be from a static constant
        List<String> returnable = new ArrayList<String>(transportationQuestionsForPopulatingDefaults)

        if (defaultTransportValueFromLcaParams == Constants.DEFAULT_TRANSPORT_RICS) {
            // add if missing in list
            if (!returnable.contains(com.bionova.optimi.core.Constants.TransportDistanceQuestionId.TRANSPORTDISTANCE_KMLEG2.toString())) {
                returnable.add(com.bionova.optimi.core.Constants.TransportDistanceQuestionId.TRANSPORTDISTANCE_KMLEG2.toString())
            }
        } else {
            // remove leg 2 if LCA param transport default is not UK/RICS
            returnable.remove(com.bionova.optimi.core.Constants.TransportDistanceQuestionId.TRANSPORTDISTANCE_KMLEG2.toString())
        }

        return returnable
    }

    Map<String, Object> getBasicQueryAnswersForReport(Entity entity) {

        Map<String, Object> resolvedAnswers = [:]

        Query basicQuery = queryService.getBasicQuery()
        if (!basicQuery) {
            return resolvedAnswers
        }

        Set<Dataset> basicQueryDatasets = entity.datasets?.findAll({ basicQuery.queryId.equals(it.queryId) })
        List<Question> allQuestionsBasicQuery = basicQuery.allQuestions

        basicQueryDatasets?.each {Dataset d ->

            String questionName = allQuestionsBasicQuery?.find({it.questionId?.equalsIgnoreCase(d.questionId)})?.localizedQuestion
            String name = optimiResourceService.getLocalizedName(optimiResourceService.getResourceWithParams(d.answerIds[0], null, null))
            String userAnswer = name ?: d.answerIds[0].toString()

            resolvedAnswers.put(questionName, userAnswer)
        }
        return resolvedAnswers
    }

    Document getResourceAsDocument(Dataset dataset) {
        if (dataset?.resourceId) {
            return optimiResourceService.getResourceAsDocument(dataset.resourceId, dataset.profileId)
        } else {
            return null
        }
    }

    boolean getDatasetResourceActive(Dataset dataset) {
        if (dataset?.resourceId) {
            Integer active = Resource.collection.count([resourceId:dataset.resourceId,profileId:dataset.profileId,active:true])?.intValue()

            if (active) {
                return Boolean.TRUE
            } else {
                return Boolean.FALSE
            }
        } else {
            return Boolean.FALSE
        }
    }

    //TODO: can be very slow in a loop, please use ResourceCache/ResourceTypeCache LOCAL instances till we have a better solution
    @Deprecated
    /**
     * This method is temporary. There will be changes in the future
     */
    Resource getResource(Dataset dataset) {
        if (dataset) {
            if(log.isTraceEnabled()) {
                log.trace("Fetching resource ${dataset.resourceId}")
            }
            if (dataset.resourceId) {
                if (dataset.calculationResourceId && dataset.calculationResourceId.equals(dataset.resourceId) && (!dataset.profileId || dataset.profileId.equals(dataset.calculationProfileId))) {
                    return dataset.calculationResource
                } else {
                    //makes a DB call if resource was not initialized yet, think of it twice before using it!
                    Resource resource = optimiResourceService.getResourceWithParams(dataset.resourceId, dataset.profileId, Boolean.TRUE)
                    dataset.setCalculationResource(resource)
                    return resource
                }
            }
        }
        return null
    }


    Dataset createCopy(Dataset dataset) {
        Dataset copy = new Dataset()
        copy.queryId = dataset.queryId
        copy.sectionId = dataset.sectionId
        copy.questionId = dataset.questionId
        copy.points = dataset.points
        copy.numericAnswer = dataset.numericAnswer
        copy.quantity = dataset.quantity
        copy.resourceId = dataset.resourceId
        copy.calculationResource = dataset.calculationResource
        copy.calculationResourceId = dataset.calculationResourceId
        copy.calculationProfileId = dataset.calculationProfileId
        copy.profileId = dataset.profileId
        copy.lastUpdater = dataset.lastUpdater
        copy.lastUpdated = dataset.lastUpdated

        if (dataset.additionalQuestionAnswers) {
            copy.additionalQuestionAnswers = new LinkedHashMap<String, Object>(dataset.additionalQuestionAnswers)
        }

        if (dataset.answerIds) {
            copy.answerIds = dataset.answerIds.clone()
        }
        copy.result = dataset.result
        copy.occurrencePeriods = dataset.occurrencePeriods
        copy.mapToResource = dataset.mapToResource
        copy.mapToResourceType = dataset.mapToResourceType
        copy.manualId = dataset.manualId

        if (dataset.monthlyAnswers) {
            copy.monthlyAnswers.putAll(dataset.monthlyAnswers)
        }

        if (dataset.projectLevelDataset) {
            copy.projectLevelDataset = Boolean.TRUE
        }

        /*
        if (this.tempPointsByCalculationRule) {
            copy.tempPointsByCalculationRule = new LinkedHashMap<String, Double>(this.tempPointsByCalculationRule)
        }*/

        if (dataset.quarterlyAnswers) {
            copy.quarterlyAnswers.putAll(dataset.quarterlyAnswers)
        }
        copy.suggestedResourceMatch = dataset.suggestedResourceMatch

        if (dataset.importDisplayFields) {
            copy.importDisplayFields = new LinkedHashMap<String, String>(dataset.importDisplayFields)
        }

        if (dataset.importMapperPartialQuantification) {
            copy.importMapperPartialQuantification = Boolean.TRUE
        }

        if (dataset.trainingData) {
            copy.trainingData = new LinkedHashMap<String, String>(dataset.trainingData)
        }

        if (dataset.trainingMatchData) {
            copy.trainingMatchData = new LinkedHashMap<String, String>(dataset.trainingMatchData)
        }

        if (dataset.importMapperCompositeMaterial) {
            copy.importMapperCompositeMaterial = Boolean.TRUE
        }
        copy.userGivenUnit = dataset.userGivenUnit
        copy.percentageOfTotal = dataset.percentageOfTotal
        copy.shareOfTotal = dataset.shareOfTotal
        copy.usedUnitSystem = dataset.usedUnitSystem
        copy.userSetCost = dataset.userSetCost
        copy.userSetTotalCost = dataset.userSetTotalCost
        copy.constructionValue = dataset.constructionValue
        copy.imperialUnit = dataset.imperialUnit
        copy.imperialQuantity = dataset.imperialQuantity

        if (dataset.allowMapping) {
            copy.allowMapping = Boolean.TRUE
        }
        copy.dataForCollapser = dataset.dataForCollapser
        copy.originalClass = dataset.originalClass
        copy.ifcMaterialValue = dataset.ifcMaterialValue
        copy.persistedOriginalClass = dataset.persistedOriginalClass
        copy.area_m2 = dataset.area_m2
        copy.volume_m3 = dataset.volume_m3
        copy.mass_kg = dataset.mass_kg
        copy.importWarning = dataset.importWarning
        copy.specialRuleShare = dataset.specialRuleShare
        copy.categoryMaterialImport = dataset.categoryMaterialImport
        copy.specialRuleId = dataset.specialRuleId

        copy.layerUValue = dataset.layerUValue
        copy.flexibleResourceIdList = dataset.flexibleResourceIdList
        copy.groupId = dataset.groupId
        copy.scalingType = dataset.scalingType
        copy.verified = dataset.verified
        copy.unlockedFromVerifiedStatus = dataset.unlockedFromVerifiedStatus
        copy.verifiedFromEntityId = dataset.verifiedFromEntityId

        return copy
    }

    def getAnswer(Dataset dataset) {
        Question question = getQuestion(dataset)
        String inputType = question?.inputType
        def answers

        if (inputType && dataset) {
            if ("text".equals(inputType) || "textarea".equals(inputType) ||
                    ("select".equals(inputType) && !question.multipleChoicesAllowed) || "hidden".equals(inputType) ||
                    "radio".equals(inputType)) {
                answers = dataset.answerIds?.get(0)
            } else if ("singlecheckbox".equals(inputType)) {
                answers = dataset.answerIds ? dataset.answerIds.get(0) : "false"
            } else {
                answers = dataset.answerIds
            }
        }
        return answers
    }

    Double getThickness(Map<String, Object> additionalQuestionAnswers) {
        Double thickness

        if (additionalQuestionAnswers) {
            String value = additionalQuestionAnswers.get("thickness_mm")
            boolean usingInches = false

            if (!value) {
                value = additionalQuestionAnswers.get("thickness_in")

                if (value) {
                    usingInches = true
                }
            }
            if (value != null) {
                if (value instanceof Double) {
                    thickness = value
                } else if (DomainObjectUtil.isNumericValue(value)) {
                    thickness = DomainObjectUtil.convertStringToDouble(value)
                }

                if (thickness && usingInches) {
                    thickness = thickness * 25.4
                }
            }
        }
        return thickness
    }

    Question getQuestion(Dataset dataset) {
        if (dataset) {
            if (!dataset.resolvedQuestion && dataset.queryId && dataset.questionId) {
                dataset.resolvedQuestion = queryService.getQueryByQueryId(dataset.queryId, Boolean.TRUE)?.getAllQuestions()?.find({ Question q -> dataset.questionId.equals(q.questionId) })
            }
            return dataset.resolvedQuestion
        }
        return null
    }

    List<Document> getTrainingData(String applicationId, String importMapperId, Indicator indicator, Entity parentEntity, Dataset dataset) {
        return importMapperTrainingDataService.getTrainingDataForDataset(dataset, applicationId, importMapperId, null, null, indicator, parentEntity)?.findAll({ it.get("resourceId") })?.unique({ it.get("resourceId") })
    }

    // Mainly for BREEAM UK task 3425
    String getAdditionalQuestionAnswersAsString(List<Question> additionalQuestions, Map<String, Object> additionalQuestionAnswers) {
        String answers

        if (additionalQuestions && !additionalQuestions.isEmpty() &&
                additionalQuestionAnswers?.subMap(additionalQuestions.collect({ it.questionId }))) {

            additionalQuestions?.each { Question question ->
                String answer = additionalQuestionAnswers?.get(question.questionId)

                if (answer) {
                    if ("select".equals(question.inputType) || "checkbox".equals(question.inputType) ||
                            "radio".equals(question.inputType)) {
                        if (question.isResourceQuestion) {
                            answer = optimiResourceService
                                    .getLocalizedName(optimiResourceService.getResourceWithParams(answer, null, null))
                        } else {
                            answer = question.choices?.find({ answer.equals(it.answerId) })?.localizedAnswer
                        }
                    }

                    if (answer) {
                        answers = answers ? answers + " " + answer.toLowerCase() : answer.toLowerCase()
                    }
                }
            }
        }
        return answers
    }

    Map<String, Double> getMonthlyQuantities(Map<String, Object> monthlyAnswers) {
        Map<String, Double> quantities = [:]

        if (monthlyAnswers) {
            monthlyAnswers.each { String month, String value ->
                if (value && DomainObjectUtil.isNumericValue(value)) {
                    quantities.put(month, DomainObjectUtil.convertStringToDouble(value))
                }
            }
        }
        return quantities
    }

    List<String> getClassRemapChoices(String existingClass, List<String> classChoices) {
        List<String> remapChoices
        if (existingClass && classChoices && !classChoices.isEmpty()) {
            remapChoices = new ArrayList<String>(classChoices)
            remapChoices.remove(existingClass)
        }
        return remapChoices
    }

    String getLastUpdaterName(String lastUpdater) {
        String username = null

        if (lastUpdater) {
            username = userService.getUserAsDocumentById(lastUpdater, new BasicDBObject([username:1]))?.username
        }
        return username
    }

    /**
     *
     * @param sourceQuery - Query from entity
     * @param targetQueryId - Query where some question was changed and need to update values in sourceQuery
     * @param childEntity - Design entity where need to update values
     */
    Set<Dataset> recalculateDatasetsByAdditionalJsScripts(Query sourceQuery, String targetQueryId, Entity childEntity){
        Map<String, List<String>> linkedJavascriptRules = sourceQuery.linkedJavascriptRules
        Set<Dataset> datasetsForRecalculation = childEntity.datasets.findAll {Dataset dataset ->
            dataset.queryId == sourceQuery.queryId
        }

        Set<Dataset> updatedDatasets = []

        for (QuerySection section in sourceQuery.sections){
            Set<Dataset> sectionDatasets = datasetsForRecalculation.findAll {it.sectionId == section.sectionId}

            if (!sectionDatasets){
                continue
            }

            for (Question question in section.questions){
                String scriptNameForCalculation = linkedJavascriptRules.find{question.questionId in it.value}?.key
                Set<Dataset> datasetsForParticularQuestion = sectionDatasets.findAll{it.questionId == question.questionId}
                Map<String, List<String>> paramsForTargetQuery = question.javascriptAdditionalCalcParams?.get(targetQueryId)

                if (paramsForTargetQuery && datasetsForParticularQuestion && scriptNameForCalculation) {
                    Map<String, Object> additionalCalculationParamsForQuestion = questionService.getAdditionalCalculationParamsForQuestion(question, childEntity)
                    def fileResource = assetResourceLocator.findAssetForURI("additionalCalculationScripts/${scriptNameForCalculation}.js")

                    if (fileResource) {
                        for (Dataset dataset in datasetsForParticularQuestion) {
                            Map<String, String> updatedAdditionalQuestionAnswers = [:]
                            Map<String, String> calculationParams = (additionalCalculationParamsForQuestion + dataset.additionalQuestionAnswers).each { it.value = it.value.toString() }
                            calculationParams.put("quantity", dataset.quantity.toString())
                            JavaScriptEngineManager javaScriptEngineManager = new JavaScriptEngineManager(fileResource.getInputStream())
                            //this is polyglot map, should be converted
                            Map calculationResults = javaScriptEngineManager.completeJSMethod(scriptNameForCalculation, dataset.resourceId, calculationParams)

                            calculationResults.each {
                                updatedAdditionalQuestionAnswers.put(it.key, it.value.toString())
                            }

                            dataset.additionalQuestionAnswers.putAll(updatedAdditionalQuestionAnswers)
                            updatedDatasets.add(dataset)
                        }
                    }
                }
            }
        }

        return updatedDatasets
    }
    /**
     * can add this to cache logic later if required after proper testing
     * @param dataset
     * @param resource
     */
    void setLinkedDatasetManualIds(Dataset dataset, Resource resource) {
        if (dataset.manualId) {
            if (resource.linkedDatasetManualIds) {
                if (!resource.linkedDatasetManualIds.contains(dataset.manualId)) {
                    resource.linkedDatasetManualIds.add(dataset.manualId)
                }
            } else {
                resource.linkedDatasetManualIds = [dataset.manualId]

            }
        }
    }
}



