package com.bionova.optimi.core.domain.mongo


class CalculationProcess {
    static mapWith = "mongo"

    String entityId
    String parentEntityId
    String indicatorId
    String status
    Date startTime
    Date finishTime

    enum CalculationStatus {
        IN_PROGRESS("IN PROGRESS"),
        ERROR("ERROR"),

        private String name;

        CalculationStatus(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }
    }

    static mapping = {
        entityId index: true
        indicatorId index: true
    }

    static constraints = {
        entityId blank: false, nullable: false
        parentEntityId blank: true, nullable: true
        indicatorId blank: true, nullable: true
        status blank: false, nullable: false
        startTime nullable: false
        finishTime nullable: true
    }
}
