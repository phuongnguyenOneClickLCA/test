package com.bionova.optimi.core.service

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.CalculationResult
import com.bionova.optimi.core.domain.mongo.CalculationRule
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.QuerySection
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ResultCategory
import com.bionova.optimi.data.ResourceCache
import com.bionova.optimi.security.AESCryption
import com.bionova.optimi.xml.betieEPDC.EPDC
import grails.compiler.GrailsCompileStatic
import groovy.transform.TypeCheckingMode

import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller
import javax.xml.datatype.DatatypeFactory
import javax.xml.datatype.XMLGregorianCalendar

@GrailsCompileStatic(TypeCheckingMode.SKIP)
class BetieService {

    DatasetService datasetService
    WordDocumentService wordDocumentService
    IndicatorService indicatorService
    QuerySectionService querySectionService
    QuestionService questionService
    /**
     *
     * @param xmlFile
     * @param childEntity
     * @param indicator
     * @param parentEntity
     * @return
     */
    EPDC getBetieEpdXML(File xmlFile, Entity childEntity, Indicator indicator, Entity parentEntity) {

        ResourceCache cache = ResourceCache.init(childEntity.datasets as List)
        EPDC epdc = new EPDC()
        setBasicBetieExportParams(epdc, childEntity, indicator, cache)
        setBetieEPDCParameters(indicator, childEntity, epdc, cache)
        setCalculationRuleList(childEntity, parentEntity, indicator, epdc)

        JAXBContext jaxbContext = JAXBContext.newInstance(EPDC.class)
        FileOutputStream fileOutputStream = new FileOutputStream(xmlFile)
        Marshaller marshaller = jaxbContext.createMarshaller()
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)
        marshaller.marshal(epdc, fileOutputStream)
        fileOutputStream.close()

    }
    /**
     *
     * @param epdc
     * @param childEntity
     * @param indicator
     * @param cache
     */
    void setBasicBetieExportParams(EPDC epdc, Entity childEntity, Indicator indicator, ResourceCache cache) {

        final Map<String, Integer> unitForDataMapping = ["m": 38, "m2": 2, "m3": 4]
        epdc.setConfiguratorName(Constants.BETIE)
        epdc.setConfiguratorCode(Constants.BETIE_CONFIGURATOR_CODE)
        epdc.setConfiguratorVersion(Constants.BETIE_CONFIGURATOR_VERSION)
        epdc.setPublicAddress(Constants.BETIE_PUBLICADDRESS)
        epdc.setParentDataBase(BigInteger.ZERO)
        epdc.setParentEPDId(BigInteger.ZERO)
        epdc.setQuantity(BigInteger.ONE)
        epdc.setParentEPDSerialId(Constants.STRING_ZERO)
        epdc.setEPDCId(wordDocumentService.generateUniqueIDForEPD(childEntity.id.toString(), indicatorService.getLocalizedName(indicator)))
        epdc.setName(datasetService.getAnswerIdOfDatasetByEntityQueryIdSectionIdQuestionId(childEntity, Constants.BETIE_PRODUCTDESCRIPTION, Constants.PROJECT_DESCRIPTION, Constants.QUESTION_NOMFDES))
        BigDecimal tld = childEntity.datasets?.find({Dataset it -> it.queryId == Constants.BETIE_A4A5 && it.sectionId == Constants.IMPLEMENTATION && it.questionId == Constants.TYPE_OUVRAGE })?.additionalQuestionAnswers?.get(Constants.QUESTION_DUREEVIE) as BigDecimal
        epdc.setTLD(tld)
        List<Dataset> listOfDataSets = datasetService.getDatasetsByEntityQueryIdSectionIdAndQuestion(childEntity, Constants.BETIE_A4A5, Constants.IMPLEMENTATION, Constants.TYPE_OUVRAGE)
        String unitForData = listOfDataSets?.size() > 0 ? cache.getResource(listOfDataSets.get(0)).unitForData : Constants.EMPTY_STRING
        BigInteger unitId = unitForDataMapping.get(unitForData) ? unitForDataMapping.get(unitForData) : null
        epdc.setUnitId(unitId)
        epdc.setStandard(BigInteger.TWO)
        GregorianCalendar calendar = new GregorianCalendar()
        calendar.setTime(childEntity.dateCreated)
        XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar)
        epdc.setProductionDate(date)
    }
    /**
     *
     * @param indicator
     * @param childEntity
     * @param epdc
     * @param cache
     *
     * Method to set epdc parameters subtags
     */
    void setBetieEPDCParameters(Indicator indicator, Entity childEntity, EPDC epdc, ResourceCache cache) {

        EPDC.Parameters parameters = new EPDC.Parameters()
        List<Query> queriesList = indicator.getQueries(childEntity).findAll({ Query it -> it.queryId in Constants.QUERIES_FOR_PARAMETERS })
        Integer count = 0
        queriesList.each { Query query ->
            query.getSections()?.each { QuerySection querySection ->
                querySectionService.getAllUnderlyingQuestions(childEntity.entityClass, querySection)?.findAll({ Question it -> it.paramUnitId })?.each { Question filteredQuestion ->
                    List<Dataset> foundDatasets = datasetService.getDatasetsByEntityQueryIdSectionIdAndQuestion(childEntity, query.queryId, querySection.sectionId, filteredQuestion.questionId)
                    if (foundDatasets) {
                        boolean nonResourceBasedQuestion = foundDatasets.findAll { Dataset it -> !it.resourceId }?.size() == foundDatasets.size()
                        foundDatasets.each { Dataset dataset ->
                            ++count
                            if (dataset) {
                                if (nonResourceBasedQuestion) {
                                    setParameters(filteredQuestion, filteredQuestion.getLocalizedQuestion(), count, parameters, questionService.getAnswerForNonResourceBasedQuestion(filteredQuestion, dataset))
                                } else {
                                    Resource resource = cache.getResource(dataset)
                                    if (resource) {
                                        if (querySection.sectionId == Constants.IMPLEMENTATION) {
                                            if (filteredQuestion.questionId == Constants.TYPE_OUVRAGE) {
                                                List<Question> addQLists = filteredQuestion.getAdditionalQuestions(indicator, null, filteredQuestion.additionalQuestionIds)?.findAll({ Question it -> it.paramUnitId })
                                                List<String> functionalUnitStrings = addQLists.findAll({ Question q -> q.usedForFunctionalUnit })?.collect({ Question it -> it.questionId })
                                                setFunctionalUnit(epdc, resource, functionalUnitStrings, dataset)
                                                setParameters(filteredQuestion, filteredQuestion.getLocalizedQuestion(), count, parameters, resource.nameEN)
                                                addQLists.each { Question it ->
                                                    ++count
                                                    setParameters(it, it.getLocalizedQuestion(), count, parameters, dataset.additionalQuestionAnswers?.get(it.questionId)?.toString())
                                                }
                                            }
                                        } else {
                                            if (filteredQuestion.questionId == Constants.EXPOSURE_CLASS) {
                                                setParameters(filteredQuestion, filteredQuestion.getLocalizedQuestion(), count, parameters, resource.nameFR)
                                            } else {
                                                setParameters(filteredQuestion, resource.nameEN, count, parameters, AESCryption.encryptWithRandomString(dataset.quantity as String))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        epdc.setParameters(parameters)
    }
    /**
     *
     * @param childEntity
     * @param parentEntity
     * @param indicator
     * @param epdc
     */
    void setCalculationRuleList(Entity childEntity, Entity parentEntity, Indicator indicator, EPDC epdc) {
        List<CalculationRule> calculationRuleList = indicator.getResolveCalculationRules(parentEntity).findAll({ CalculationRule it -> it.mapToIndicatorCode_INIES })
        List<ResultCategory> categoryList = indicator.getResolveResultCategories(parentEntity)
        List<CalculationResult> indicatorResults = childEntity.getCalculationResultObjects(indicator.indicatorId, null, null)
        if (indicatorResults) {
            EPDC.Indicators indicators = new EPDC.Indicators()
            EPDC.Indicators.Indicator epdcIndicator
            EPDC.Indicators.Indicator.Phases phases
            EPDC.Indicators.Indicator.Phases.Phase phase

            calculationRuleList?.each { CalculationRule calculationRule ->
                epdcIndicator = new EPDC.Indicators.Indicator()
                List<CalculationResult> calResultBasedOnRule = indicatorResults?.findAll({ CalculationResult result -> result.calculationRuleId == calculationRule.calculationRuleId })
                epdcIndicator.setIndicatorCode(calculationRule.mapToIndicatorCode_INIES)
                phases = new EPDC.Indicators.Indicator.Phases()
                phases.phase = []
                setPhases(calResultBasedOnRule, categoryList, phase, epdcIndicator, phases)
                indicators.indicator.add(epdcIndicator)
            }
            epdc.setIndicators(indicators)
        }
    }
    /**
     *
     * @param filteredQuestion
     * @param count
     * @param parameters
     * @param value
     *
     * Method to set parameters node of the xml
     */
    void setParameters(Question filteredQuestion, String name, Integer parameterId, EPDC.Parameters parameters, String value) {
        EPDC.Parameters.Parameter parameter = new EPDC.Parameters.Parameter()
        parameter.setName(name)
        parameter.setValue(value)
        parameter.setParameterId(parameterId as BigInteger)
        parameter.setParamUnitId(filteredQuestion.paramUnitId)
        parameters.parameter.add(parameter)

    }
    /**
     *
     * @param calResultBasedOnRule
     * @param categoryList
     * @param phase
     * @param epdcIndicator
     * @param phases
     */
    void setPhases(List<CalculationResult> calResultBasedOnRule, List<ResultCategory> categoryList, EPDC.Indicators.Indicator.Phases.Phase phase, EPDC.Indicators.Indicator epdcIndicator, EPDC.Indicators.Indicator.Phases phases) {
        calResultBasedOnRule.each { CalculationResult calResult ->
            ResultCategory r = categoryList.find({ ResultCategory it -> calResult.resultCategoryId == it.resultCategoryId && it.mapToPhaseCode_INIES })
            if (r) {
                phase = new EPDC.Indicators.Indicator.Phases.Phase()

                r.mapToPhaseCode_INIES.each { String it ->
                    phase.setPhaseCode(it ?: Constants.STRING_ZERO)
                    phase.setValue(calResult.result as String)
                }

                phases.phase.add(phase)
            }
            epdcIndicator.setPhases(phases)
        }
    }
    /**
     *
     * @param epdc
     * @param resource
     * @param functionalUnitStrings
     * @param dataset
     *
     * functionalUnit is a resource param string which has the place holders for addQ answers
     */
    void setFunctionalUnit(EPDC epdc, Resource resource, List<String> functionalUnitStrings, Dataset dataset) {
        functionalUnitStrings.each { String it ->
            if (dataset.additionalQuestionAnswers?.get(it)) {
                resource.functionalUnit = resource.functionalUnit?.replace(it, dataset.additionalQuestionAnswers?.get(it)?.toString())
            }
        }
        epdc.setFunctionalUnit(resource.functionalUnit)
    }
}