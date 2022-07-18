package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.service.DatasetService
import com.bionova.optimi.data.ResourceCache
import grails.compiler.GrailsCompileStatic
import grails.util.Holders
import grails.validation.Validateable
import groovy.transform.TypeCheckingMode
import org.bson.types.ObjectId

@GrailsCompileStatic
class ProductDataList implements Comparable, Serializable, Validateable {

    static mapWith = "mongo"
    ObjectId id
    String listId
    String name
    String databaseType
    List <String> resourceIds
    List<Dataset> datasets
    List<String> dataProperties
    Map <String, Object> additionalQuestionAnswers
    String classificationParamId
    String classificationQuestionId
    String privateProductDataListAccountId
    String privateProductDataListCompanyName
    String privateAccountId
    String importFile

    Boolean uneditable

    static constraints = {
        resourceIds nullable: true
        listId nullable:true
        name nullable:true
        databaseType nullable:true
        datasets nullable: true
        dataProperties nullable: true
        additionalQuestionAnswers nullable: true
        classificationParamId nullable: true
        classificationQuestionId nullable: true
        privateProductDataListAccountId nullable: true
        privateProductDataListCompanyName nullable: true
        privateAccountId nullable: true
        importFile nullable: true
        uneditable nullable: true

    }

    static hasMany = [datasets: Dataset]
    static embedded = ["datasets"]

    def transients = [
            "missMatchedData",
            "inactiveData",
            "activeData"
    ]
    transient unitConversionUtil

    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    Boolean getMissMatchedData() {
        Boolean missMatch = Boolean.FALSE
        List<Dataset> datasets = datasets
        List<Dataset> incompatibleUnitDatasets = []

        if (datasets) {
            ResourceCache resourceCache = ResourceCache.init(datasets)
            for (Dataset d : datasets) {
                Resource resource = resourceCache.getResource(d)

                if (resource) {
                    List<String> compatibleUnits = unitConversionUtil.getCompatibleUnitsForResource(resource)

                    if (d.userGivenUnit && compatibleUnits && !compatibleUnits*.toLowerCase()?.contains(d.userGivenUnit.toLowerCase()) && !d.uniqueConstructionIdentifier) {
                        incompatibleUnitDatasets.add(d)
                    }
                }
            }
        }

        if (incompatibleUnitDatasets && !incompatibleUnitDatasets.isEmpty()) {
            missMatch = Boolean.TRUE
        }

        return missMatch
    }

    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    List<Dataset> getInactiveData() {
        DatasetService datasetService = Holders.getApplicationContext().getBean("datasetService")
        List<Dataset> inactiveDatasets = []
        if (datasets) {
            inactiveDatasets = datasets.findAll({ !datasetService.getDatasetResourceActive(it) && it.resourceId })
        }
        return inactiveDatasets
    }

    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    List<Dataset> getActiveData() {
        DatasetService datasetService = Holders.getApplicationContext().getBean("datasetService")
        List<Dataset> activeDatasets = []
        if (datasets) {
            activeDatasets = datasets.findAll({ datasetService.getDatasetResourceActive(it) })
        }
        return activeDatasets
    }

    @Override
    int compareTo(Object o) {
        return 0
    }
}
