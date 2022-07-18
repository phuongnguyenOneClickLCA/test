package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable
import org.apache.commons.codec.digest.DigestUtils
import org.bson.types.ObjectId

@GrailsCompileStatic
class EpdRequest implements Serializable, Validateable{

    static mapWith = "mongo"
    ObjectId id
    String manualId
    String entityId
    String senderEmail
    String sendingOrganisation
    String recipientEmail
    String manufacturer
    String countryForSendData
    String subtypeForSendData
    String productDescription
    String estimatedDemand
    Date deadline
    Date dateAdded
    Date lastViewed
    Integer complianceStandard
    String complianceStandardOther
    Boolean thirdPartyVerified
    Boolean privateEPDVerified
    Boolean productCarbonVerified
    Boolean productSpecific
    Boolean projectSpecific

    static constraints = {
        entityId nullable: false
        manualId nullable: true
        senderEmail nullable: false
        sendingOrganisation nullable: false
        recipientEmail nullable: false
        manufacturer nullable: false
        countryForSendData nullable: false
        subtypeForSendData nullable: false
        productDescription nullable: true
        estimatedDemand nullable: true
        deadline nullable: true
        dateAdded nullable: true
        lastViewed nullable: true
        complianceStandard nullable: true
        complianceStandardOther nullable: true
        thirdPartyVerified nullable: true
        privateEPDVerified nullable: true
        productCarbonVerified nullable: true
        productSpecific nullable: true
        projectSpecific nullable: true
    }
    def beforeInsert() {
        dateAdded = new Date()
        manualId = DigestUtils.sha256Hex(recipientEmail)
    }

    @Override
    public String toString() {
        String result = "entityId: ${entityId}, senderEmail: ${senderEmail}, manualId: ${manualId}, sendingOrganisation: ${sendingOrganisation},recipientEmail: ${recipientEmail},countryForSendData: ${countryForSendData}," +
                "subtypeForSendData: ${subtypeForSendData},productDescription: ${productDescription},estimatedDemand: ${estimatedDemand},deadline: ${deadline},dateAdded: ${dateAdded}, lastViewed: ${lastViewed}, complianceStandard: ${complianceStandard}," +
                "complianceStandardOther: ${complianceStandardOther},thirdPartyVerified: ${thirdPartyVerified},privateEPDVerified: ${privateEPDVerified},productCarbonVerified: ${productCarbonVerified}," +
                "productSpecific: ${productSpecific},projectSpecific: ${projectSpecific},"
        return result
    }

}
