package com.bionova.optimi.core.service

import grails.gorm.transactions.Transactional

@Transactional
class TrialStatusService {
    Integer getTimeDifferencesFromProjectCreation(Date timeToCalculate, Date firstProject ){
        Integer differences = null
        if(firstProject && timeToCalculate){
            differences = timeToCalculate.getMinutes() - firstProject.getMinutes()
        }
        return differences
    }
}
