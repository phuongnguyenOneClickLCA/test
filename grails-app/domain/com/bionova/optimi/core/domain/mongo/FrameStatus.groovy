package com.bionova.optimi.core.domain.mongo

class FrameStatus {

    static mapWith = "mongo"

    static enum TAB {WORKFLOW, HELP, RESULTBAR}

    String userId
    Boolean isOpen
    String selectedTab
    Integer width

    static constraints = {
        width nullable: true
    }
}