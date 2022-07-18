package com.bionova.optimi.comparator

import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.service.DatasetService
import groovy.transform.CompileStatic
import grails.util.Holders
import groovy.transform.TypeCheckingMode

/**
 * This comparator is used to compare two {@link Dataset} objects, e.g. for forming a list of materials. It not only sorts
 * the datasets and the resources linked to them in the alphabetical order, but also takes into consideration if
 * the resource behind a dataset belongs to a construction. Such resources are grouped together.
 * The constituent follow the construction they belong to.
 *
 * When using this comparator make sure that both datasets to be compared are not null.
 */
class ResultListDatasetConstructionComparator implements Comparator<Dataset>  {

    DatasetService datasetService

    @CompileStatic(TypeCheckingMode.SKIP)
    ResultListDatasetConstructionComparator() {
        datasetService = Holders.applicationContext.getBean("datasetService")
    }

    @Override
    int compare(Dataset ds1, Dataset ds2) {
        if (!ds1 || !ds2) {
            return 0
        }
        if (ds1.constituent) {
            if (ds2.construction
                    && ds1.uniqueConstructionIdentifier == ds2.uniqueConstructionIdentifier) {
                // ds1 is a constituent of ds2 which is a construction
                return 1
            } else if (ds2.construction
                    && ds1.uniqueConstructionIdentifier != ds2.uniqueConstructionIdentifier) {
                // ds2 is a construction, but ds1 is not its constituent
                return compareTwoDatasets(ds1.parentConstructionDataset, ds2)
            } else if (ds2.constituent && ds1.uniqueConstructionIdentifier == ds2.uniqueConstructionIdentifier) {
                // ds1 and ds2 are constituents of the same construction
                return compareTwoDatasets(ds1, ds2)
            } else if (ds2.constituent && ds1.uniqueConstructionIdentifier != ds2.uniqueConstructionIdentifier) {
                // ds1 and ds2 are constituents of different constructions
                return compareTwoDatasets(ds1.parentConstructionDataset, ds2.parentConstructionDataset)
            } else {
                // ds2 is a regular resource (neither a construction nor a constituent)
                return compareTwoDatasets(ds1.parentConstructionDataset, ds2)
            }
        } else {
            // ds1 is either a construction or a regular resource
            // (neither a construction nor a constituent)
            if (ds1.construction && ds2.constituent
                    && ds1.uniqueConstructionIdentifier == ds2.uniqueConstructionIdentifier) {
                // ds2 is a constituent of ds1 which is a construction
                return -1
            } else if (ds2.constituent) {
                // ds2 is a constituent of a construction, ds1 is a different construction
                // or a regular resource
                return compareTwoDatasets(ds1, ds2.parentConstructionDataset)
            } else {
                return compareTwoDatasets(ds1, ds2)
            }
        }
    }

    /**
     * If both datasets refer to the same resource use {@link Dataset#manualId} for comparison.
     * Otherwise compare the resources.
     */
    private Integer compareTwoDatasets(Dataset ds1, Dataset ds2) {
        if (ds1?.resourceId == ds2?.resourceId) {
            return ds1?.manualId <=> ds2?.manualId
        } else {
            ds1?.resource <=> ds2?.resource
        }
    }
}
