package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class DataCardRow implements Serializable {
    static mapWith = "mongo"

    // each row must have either resourceAttribute OR resourceSubTypeAttribute OR customAttribute
    String resourceAttribute // get value from resource
    String resourceSubTypeAttribute // get value from subType
    String customAttribute // use this if some extra code is required to display row
    Map<String, String> name
    String helpLocalized // localization key in excel (optional)
    String licenseKey // define required license to display a row (optional)
    String conditionalHide
    // these are for render, not for config. DO NOT USE IN CONFIGURATION
    Boolean authorizedToDisplay
    String heading // row heading first column
    String help // text (from helpLocalized) display in qMark, second column
    String content // content of the third column

    ConditionalDisplayOnQuery conditionalDisplayOnQuery

    static constraints = {
        resourceAttribute nullable: true
        resourceSubTypeAttribute nullable: true
        customAttribute nullable: true
        name nullable: true
        helpLocalized nullable: true
        licenseKey nullable: true
        conditionalHide nullable: true
        authorizedToDisplay nullable: true
        heading nullable: true
        help nullable: true
        content nullable: true
        conditionalDisplayOnQuery nullable: true
    }

    static embedded = ['conditionalDisplayOnQuery']
}
