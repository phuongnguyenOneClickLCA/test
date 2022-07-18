package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.CarbonDesignerRegion
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.ValueReference
import com.bionova.optimi.util.UnitConversionUtil
import grails.gorm.transactions.Transactional

@Transactional
class CarbonDesignerRegionService {

    UnitConversionUtil unitConversionUtil

    Double getDesignGIFA(Entity design, String unitSystem, CarbonDesignerRegion carbonDesignerRegion) {
        Boolean isImperial = UnitConversionUtil.UnitSystem.IMPERIAL.value.equals(unitSystem)
        Double gifa = 0

        if (carbonDesignerRegion) {
            ValueReference grossInternalFloorAreaResource = carbonDesignerRegion.grossInternalFloorAreaResource
            if (design && grossInternalFloorAreaResource && grossInternalFloorAreaResource.resourceId) {
                Dataset gifaDataset = getAreaDataset(design, grossInternalFloorAreaResource)

                if (gifaDataset) {
                    if (unitConversionUtil.isImperialUnit(gifaDataset.userGivenUnit)) {
                        if (isImperial) {
                            gifa = gifaDataset.quantity
                        } else {
                            gifa = unitConversionUtil.doConversion(gifaDataset.quantity, null, gifaDataset.userGivenUnit, null, null, null, null, null, null, unitConversionUtil.transformImperialUnitToEuropeanUnit(gifaDataset.userGivenUnit))
                        }
                    } else {
                        if (isImperial) {
                            gifa = unitConversionUtil.doConversion(gifaDataset.quantity, null, gifaDataset.userGivenUnit, null, null, null, null, null, null, unitConversionUtil.europeanUnitToImperialUnit(gifaDataset.userGivenUnit))
                        } else {
                            gifa = gifaDataset.quantity
                        }
                    }
                }
            }
        }
        return gifa
    }

    Double getDesignGFA(Entity design, String unitSystem, Entity parent, CarbonDesignerRegion carbonDesignerRegion) {
        Boolean isImperial = UnitConversionUtil.UnitSystem.IMPERIAL.value.equals(unitSystem)
        Double gfa = 0

        if (carbonDesignerRegion) {
            ValueReference grossFloorAreaResource = carbonDesignerRegion.grossFloorAreaResource
            if (design && grossFloorAreaResource && grossFloorAreaResource.resourceId) {
                Dataset gfaDataset = getAreaDataset(design, grossFloorAreaResource)

                if (gfaDataset) {
                    if (unitConversionUtil.isImperialUnit(gfaDataset.userGivenUnit)) {
                        if (isImperial) {
                            gfa = gfaDataset.quantity
                        } else {
                            gfa = unitConversionUtil.doConversion(gfaDataset.quantity, null, gfaDataset.userGivenUnit, null, null, null, null, null, null, unitConversionUtil.transformImperialUnitToEuropeanUnit(gfaDataset.userGivenUnit))
                        }
                    } else {
                        if (isImperial) {
                            gfa = unitConversionUtil.doConversion(gfaDataset.quantity, null, gfaDataset.userGivenUnit, null, null, null, null, null, null, unitConversionUtil.europeanUnitToImperialUnit(gfaDataset.userGivenUnit))
                        } else {
                            gfa = gfaDataset.quantity
                        }
                    }
                }
            }

            if (!gfa && parent) {
                gfa = parent.datasets?.find{it.questionId.equals("grossSurface")}?.quantity ?: 0
            }
        }
        return gfa
    }

    Double getDesignHeatedArea(Entity design, String unitSystem, CarbonDesignerRegion carbonDesignerRegion) {
        Boolean isImperial = UnitConversionUtil.UnitSystem.IMPERIAL.value.equals(unitSystem)
        Double heatedArea = 0

        if (carbonDesignerRegion) {
            ValueReference heatedAreaResource = carbonDesignerRegion.heatedAreaResource
            if (design && heatedAreaResource && heatedAreaResource.resourceId) {
                Dataset heatedAreaDataset = getAreaDataset(design, heatedAreaResource)

                if (heatedAreaDataset) {
                    if (unitConversionUtil.isImperialUnit(heatedAreaDataset.userGivenUnit)) {
                        if (isImperial) {
                            heatedArea = heatedAreaDataset.quantity
                        } else {
                            heatedArea = unitConversionUtil.doConversion(heatedAreaDataset.quantity, null, heatedAreaDataset.userGivenUnit, null, null, null, null, null, null, unitConversionUtil.transformImperialUnitToEuropeanUnit(heatedAreaDataset.userGivenUnit))
                        }
                    } else {
                        if (isImperial) {
                            heatedArea = unitConversionUtil.doConversion(heatedAreaDataset.quantity, null, heatedAreaDataset.userGivenUnit, null, null, null, null, null, null, unitConversionUtil.europeanUnitToImperialUnit(heatedAreaDataset.userGivenUnit))
                        } else {
                            heatedArea = heatedAreaDataset.quantity
                        }
                    }
                }
            }
        }
        return heatedArea
    }

    Dataset getAreaDataset(Entity design, ValueReference valueReference) {
        Dataset dataset

        if (valueReference.queryId && valueReference.sectionId && valueReference.questionId) {
            dataset = design.datasets?.find({it.questionId == valueReference.questionId && it.sectionId == valueReference.sectionId && it.queryId == valueReference.queryId && it.resourceId == valueReference.resourceId})
        } else if (valueReference.questionId) {
            dataset = design.datasets?.find({it.questionId == valueReference.questionId && it.resourceId == valueReference.resourceId})
        }
        return dataset
    }
}
