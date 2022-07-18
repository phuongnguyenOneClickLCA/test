/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.transformation.Translatable
import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable
import groovy.transform.TypeCheckingMode
import org.bson.types.ObjectId

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
@GrailsCompileStatic
class QuerySection implements Serializable, Validateable {
    static mapWith = "mongo"
    ObjectId id
    String sectionId
    Boolean skipInResultsView
    @Translatable
    Map<String, String> name
    @Translatable
    Map<String, String> shortName
    @Translatable
    Map<String, String> help
    Boolean helpPopover
    Map<String, Map<String, String>> appendToSectionNameForIndicatorId
    HelpSettings helpSettings
    QuerySectionDefault defaults
    List<QuerySection> sections
    List<Question> questions
    Map<String, List<String>> includeSectionIds
    // Not embedded sections, included sections. Key is queryId, value is list of sectionIds
    // licenseKey, requiredLicenseKeys, disableForLicenseKeys are used in licenseService doFilterByLicenseKeysAndRequiredLicenseKeysAndDisableForLicenseKeys
    String licenseKey
    // only show section if project has one of the license key
    List<String> requiredLicenseKeys
    // hide section if project has one of the license key
    List<String> disableForLicenseKeys
    QuestionTableFormat useTableFormatting
    Boolean hideInPortfolioIfAnonymized //not show this section in portfolios that are anonymized if user has no rights to that entity.
    Boolean expandable
    // sw-1312 when section is not licensed , instead of not rendering the section , create & hide the section if this particular
    //config is present - done for betie issue. so for now use it only with betie productMaterials section
    Boolean hideOnly

    @Deprecated
    String verificationPoint  // pls use verificationPoints
    List<String> verificationPoints
    List<String> compatiblePrivateDataTypes
    Boolean hideHeadText

    /*
        The virtual fields are used for quick starting a project. These are not in configuration directly, but compiled in VirtualQuerySection.groovy
     */
    String virtualSectionId
    @Translatable
    Map<String, String> virtualName
    Boolean overrideMainSection // this is used to make subsection a main section
    //=============

    Boolean hasVerifiedDatasets // not from config

    static embedded = [
            "defaults",
            "sections",
            "questions",
            "helpSettings",
            "useTableFormatting"
    ]

    static constraints = {
        name nullable: true
        help nullable: true
        defaults nullable: true
        sections nullable: true
        includeSectionIds nullable: true
        skipInResultsView nullable: true
        licenseKey nullable: true
        helpSettings nullable: true
        useTableFormatting nullable: true
        hideInPortfolioIfAnonymized nullable: true
        appendToSectionNameForIndicatorId nullable: true
        helpPopover nullable: true
        expandable nullable: true
        verificationPoint nullable: true
        verificationPoints nullable: true
        compatiblePrivateDataTypes nullable: true
        hideHeadText nullable: true
        virtualSectionId nullable: true
        virtualName nullable: true
        overrideMainSection nullable: true
        requiredLicenseKeys nullable: true
        disableForLicenseKeys nullable: true
        hasVerifiedDatasets nullable: true
        hideOnly nullable: true
    }

    // this method does not copy all fields. Add more if needed
    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    QuerySection createCopy() {
        QuerySection copy = new QuerySection()
        copy.sectionId = this.sectionId ? new String(this.sectionId) : null
        copy.expandable = this.expandable ? new Boolean(this.expandable) : null
        copy.name = this.name ? new LinkedHashMap<String, String>(this.name) : null
        copy.help = this.help ? new LinkedHashMap<String, String>(this.help) : null
        copy.questions = this.questions ? new ArrayList<Question>(this.questions) : null
        copy.useTableFormatting = this.useTableFormatting
//        change to below codes if needed
//        if (this.useTableFormatting) {
//            QuestionTableFormat qtf = new QuestionTableFormat()
//            qtf.columnWidths = new ArrayList<String>(this.useTableFormatting.columnWidths)
//            qtf.questionIds = new ArrayList<String>(this.useTableFormatting.questionIds)
//            copy.useTableFormatting = qtf
//        }
        return copy
    }
}