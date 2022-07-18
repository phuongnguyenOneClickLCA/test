package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.service.UserService
import com.bionova.optimi.core.transformation.Translatable
import grails.compiler.GrailsCompileStatic
import grails.util.Holders
import grails.validation.Validateable
import groovy.transform.TypeCheckingMode
import org.apache.commons.collections.FactoryUtils
import org.apache.commons.collections.MapUtils
import org.bson.types.ObjectId

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class ChannelFeature implements Serializable, Validateable {
    static mapWith = "mongo"
    ObjectId id
    String name
    String customer
    String logo
    String shortcutIcon
    @Translatable
    Map<String, String> title = MapUtils.lazyMap([:], FactoryUtils.constantFactory(''))
    String token
    String loginUrl
    String navbarColor
    @Translatable
    Map<String, String> supportContact = MapUtils.lazyMap([:], FactoryUtils.constantFactory(''))
    String creatorId
    String lastUpdaterId
    Date dateCreated
    Date lastUpdated
    List<String> addableEntityClasses
    List<String> languagesForChannel
    @Translatable(alias = "helpDocumentUrl")
    Map<String, String> helpDocumentUrls = MapUtils.lazyMap([:], FactoryUtils.constantFactory(''))
    Boolean defaultChannel
    Boolean enableHotjarTrack
    Boolean nameAsRegistrationMotive
    Boolean hidePublic
    Boolean showPoweredBy
    List<String> portfolioIds
    String benchmarkName
    Boolean enableQuoteBar
    Boolean enableLicenseKeyBox
    String quoteUrl
    String webinarUrl
    String expertUrl
    String mailChimpListId // DEPRECATED
    String mailChimpSegmentId // DEPRECATED
    String mailChimpNotes // DEPRECATED
    String mailChimpCustomWelcomeListId // DEPRECATED
    Boolean showInLoginPage
    String backgroundImage
    String loginPageCss
    Boolean reversePeriods
    Boolean hideEcommerce
    String trialUrl
    String helpUrl
    Boolean allowFloatingHelp
    Boolean enableAutomaticTrial
    Boolean disableUpdateSideBar
    Boolean disableTwoFactorAuth
    Boolean enableBuildingForChannel


    static constraints = {
        logo nullable: true, blank: true
        token nullable: false, blank: false, unique: true
        name nullable: false, blank: false
        customer nullable: false, blank: false
        dateCreated nullable: true
        lastUpdated nullable: true
        loginUrl nullable: true, blank: true
        addableEntityClasses nullable: true
        languagesForChannel nullable: true
        helpDocumentUrls nullable: true
        navbarColor nullable: true, blank: true
        defaultChannel nullable: true
        supportContact nullable: true
        shortcutIcon nullable: true, blank: true
        nameAsRegistrationMotive nullable: true
        title nullable: true
        hidePublic nullable: true
        showPoweredBy nullable: true
        portfolioIds nullable: true
        benchmarkName nullable: true
        enableQuoteBar nullable: true
        quoteUrl nullable: true
        webinarUrl nullable: true
        expertUrl nullable: true
        mailChimpListId nullable: true
        mailChimpSegmentId nullable: true
        mailChimpNotes nullable: true
        enableHotjarTrack nullable: true
        enableLicenseKeyBox nullable: true
        showInLoginPage nullable:true
        backgroundImage nullable: true, blank: true
        loginPageCss nullable: true, blank: true
        reversePeriods nullable: true
        hideEcommerce nullable: true
        trialUrl nullable: true
        helpUrl nullable: true
        allowFloatingHelp nullable: true
        enableAutomaticTrial nullable: true
        mailChimpCustomWelcomeListId nullable: true
        disableUpdateSideBar nullable: true
        disableTwoFactorAuth nullable: true
        creatorId nullable: true
        lastUpdaterId nullable: true
        enableBuildingForChannel nullable: true
    }

    def beforeInsert() {
        dateCreated = new Date()
        lastUpdated = dateCreated
        name = name?.trim()
        token = token?.trim()
    }

    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    def beforeUpdate() {
        UserService userService = Holders.getApplicationContext().getBean("userService")
        lastUpdated = new Date()
        lastUpdaterId = userService.getCurrentUser(Boolean.TRUE)?.id?.toString()
        name = name?.trim()
        token = token?.trim()
    }
}
