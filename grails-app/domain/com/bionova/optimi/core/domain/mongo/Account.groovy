package com.bionova.optimi.core.domain.mongo

import grails.util.Holders
import com.bionova.optimi.construction.Constants
import com.bionova.optimi.core.service.ConfigurationService
import org.bson.types.ObjectId

class Account {

    ObjectId id
    String companyName
    String addressLine1
    String addressLine2
    String addressLine3
    String postcode
    String town
    String state
    String country
    String companyWebsite
    String vatNumber
    Boolean vatValid
    String vatNote
    String otherCompanyIdentification
    List<String> userIds
    List<String> mainUserIds
    List<String> requestToJoinUserIds
    List<String> licenseIds
    List<String> catalogueSlugs
    Date dateOfVatValidation
    String emailForm
    Boolean emailFormValidationOverride // internal user can choose to ignore validating accounts emailForm attr
    byte[] branding
    byte[] backgroundImage
    Boolean showLogoInSoftware
    String loginKey
    String loginBoxColor
    String submitButtonColor
    String fontColor
    String visibleLoginUrl
    Double carbonCost
    String unitCarbonCost
    List<Map<String, Object>> privateEPDs
    Map<String, String> favoriteMaterialIdAndUserId
    List<String> linkedProductDataListIds
    String defaultProductImage
    String defaultManufacturingDiagram
    String defaultBrandingImage
    String organizationPhoneNr
    String organizationEmail
    String organizationContactPerson
    List<String> projectTemplateIds

    static mapWith = "mongo"
    static hasMany = [userIds: String, mainUserIds: String]
    static constraints = {
        companyName unique: true
        addressLine2 nullable: true
        postcode nullable: true
        state nullable: true
        companyWebsite nullable: true
        vatNumber nullable: true
        otherCompanyIdentification nullable: true
        userIds nullable: true
        mainUserIds nullable: true
        licenseIds nullable: true
        vatValid nullable: true
        vatNote nullable: true
        dateOfVatValidation nullable: true
        addressLine3 nullable: true
        emailForm(validator: { val, obj ->
            if (!obj.validateEmailForm()) {
                return ['account.emailForm.invalid']
            }
        }, nullable: true)
        branding nullable: true
        backgroundImage nullable: true
        loginKey nullable: true
        loginBoxColor nullable: true
        submitButtonColor nullable: true
        fontColor nullable: true
        showLogoInSoftware nullable: true
        catalogueSlugs nullable: true
        visibleLoginUrl nullable: true
        requestToJoinUserIds nullable: true
        privateEPDs nullable: true
        carbonCost nullable: true
        unitCarbonCost nullable: true
        favoriteMaterialIdAndUserId nullable: true
        linkedProductDataListIds nullable: true
        emailFormValidationOverride nullable: true
        defaultProductImage nullable: true
        defaultManufacturingDiagram nullable: true
        defaultBrandingImage nullable: true
        organizationPhoneNr nullable: true
        organizationEmail nullable: true
        organizationContactPerson nullable: true
        projectTemplateIds nullable: true
    }

    private boolean validateEmailForm() {
        ConfigurationService configurationService = Holders.getApplicationContext().getBean("configurationService")
        boolean valid = true

        if (!emailFormValidationOverride) {
            String invalidEmailsAsString = configurationService.getByConfigurationName(Constants.APPLICATION_ID,
                    Constants.ConfigName.UNALLOWED_ACCOUNT_EMAILS.toString())?.value

            if (invalidEmailsAsString) {
                List<String> invalidEmails = invalidEmailsAsString.tokenize(',')

                if (emailForm) {
                    String mailEnding = emailForm.minus("@")
                    if (mailEnding && invalidEmails.find({ mailEnding.equalsIgnoreCase(it.trim()) })) {
                        valid = false
                    }

                } else {
                    valid = false
                }
            }
        }
        return valid
    }
}
