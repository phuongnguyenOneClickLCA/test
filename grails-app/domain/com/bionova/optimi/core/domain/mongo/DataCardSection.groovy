package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class DataCardSection implements Serializable {
    static mapWith = "mongo"

    String sectionId
    Map<String, String> name
    String helpLocalized // localization key in excel (optional)
    String licenseKey // define required license to display a section (optional)
    Boolean sectionCollapsed // if true, the section will collapse on card open (optional)
    // if true, this section will not render as table, resourceAttributes is not required in this case (optional)
    Boolean customRendering
    List<DataCardRow> resourceAttributes

    // these are for render, not for config. DO NOT USE IN CONFIGURATION
    Boolean authorizedToDisplay
    String heading // section heading
    String help // text (from helpLocalized) display in qMark next to section heading
    String customContent // if customRendering : true
    Boolean isEmpty // true if all rows in this section don't have any content => hide

    static embedded = ['resourceAttributes']

    static constraints = {
        sectionId nullable: true
        sectionCollapsed nullable: true
        name nullable: true
        helpLocalized nullable: true
        sectionCollapsed nullable: true
        licenseKey nullable: true
        customRendering nullable: true
        resourceAttributes nullable: true
        authorizedToDisplay nullable: true
        heading nullable: true
        help nullable: true
        customContent nullable: true
        isEmpty nullable: true
    }
}
