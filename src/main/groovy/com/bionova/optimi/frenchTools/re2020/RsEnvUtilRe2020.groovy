package com.bionova.optimi.frenchTools.re2020

import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.service.DatasetService
import com.bionova.optimi.frenchTools.FrenchConstants
import com.bionova.optimi.util.NumberUtil
import com.bionova.optimi.xml.re2020RSEnv.RSEnv

import java.text.DecimalFormat

class RsEnvUtilRe2020 {
    DatasetService datasetService
    NumberUtil numberUtil

    void setPhase(RSEnv rsEnv, Entity parentEntity) {
        DecimalFormat decimalFormat = new DecimalFormat()
        decimalFormat.setParseBigDecimal(true)
        String answer = datasetService.getValueFromDataset(parentEntity, FrenchConstants.FEC_QUERYID_PROJECT_LEVEL, FrenchConstants.DONNEES_ADMINISTRATIVES_SECTIONID, FrenchConstants.PHASE_QUESTIONID)
        rsEnv.setPhase(numberUtil.answerToInteger(answer, decimalFormat, null))
    }

}
