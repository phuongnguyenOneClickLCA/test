package com.bionova.optimi.core.service

import com.bionova.optimi.configuration.EmailConfiguration
import com.bionova.optimi.construction.Constants
import com.bionova.optimi.core.domain.mongo.ChannelFeature
import com.bionova.optimi.core.domain.mongo.TrialStatus
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.util.LoggerUtil
import grails.async.Promise
import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse
import grails.util.Environment
import groovy.json.JsonBuilder
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.i18n.LocaleContextHolder

import javax.servlet.http.HttpSession
import java.text.SimpleDateFormat

import static grails.async.Promises.task

class ActiveCampaignService {

    def configurationService
    def optimiMailService
    def messageSource
    def userService
    LoggerUtil loggerUtil
    FlashService flashService

    @Autowired
    EmailConfiguration emailConfiguration

    private static final String ApiToken = "a3f1a0214f89e74efb6d7a16f74a72c98bbb988b9b1baf50ec1acac0cb5215283add3270"
    private static final String ApiVersion = "3"

    private static final String ContactEndpoint = "https://bionova.api-us1.com/api/$ApiVersion/contacts"
    private static final String ContactSyncOrCreateEndpoint = "https://bionova.api-us1.com/api/$ApiVersion/contact/sync"
    private static final String AccountEndPoint = "https://bionova.api-us1.com/api/$ApiVersion/accounts"
    private static final String FieldValueEndPoint = "https://bionova.api-us1.com/api/$ApiVersion/fieldValues"
    private static final String ContactTagEndPoint = "https://bionova.api-us1.com/api/$ApiVersion/contactTags"
    private static final String ContactTagDeleteEndPoint = "https://bionova.api-us1.com/api/$ApiVersion/contactTags/{id}"
    private static final String AccountContactsEndPoint = "https://bionova.api-us1.com/api/$ApiVersion/accountContacts"
    private static final String AccountSearchEndPoint = "https://bionova.api-us1.com/api/$ApiVersion/accounts?search={organizationName}"

    // Custom fields
    static final String ACCOUNT_CREATED_DATE_FIELD_ID = 66
    static final String COUNTRY_FIELD_ID = 88
    static final String PERSON_ROLE_FIELD_ID = 89
    static final String ACCOUNT_ACTIVATION_LINK_FIELD_ID = 95
    static final String LANGUAGE_FIELD_ID = 97
    static final String TRIAL_LICENSE_NAME_FIELD_ID = 98
    static final String TRIAL_ENTITY_FIELD_ID = 99
    static final String CONSENT_TO_COMMUNICATION_FIELD_ID = 105
    static final String INVITING_USER_FIELD_ID = 109
    static final String ENTITY_FIELD_ID = 110
    static final String LINK_TO_EPD_REQUEST_FIELD_ID = 129
    static final String TAG_ID_VALUE_FOR_EPD_FIELD_ID = 130
    static final String EPD_REQUEST_SENDER_FIELD_ID = 132
    static final String EPD_REQUEST_PROJECT_NAME_FIELD_ID = 133

    // Tags
    static final String USER_COMMERCIAL_TAG_ID = 25 // when user is added to commercial license
    static final String USER_STUDENT_TAG_ID = 26 // when user type is student
    static final String USER_TRIAL_TAG_ID = 27 // when user activates trial license
    static final String ACTION_ACCOUNT_CREATED_TAG_ID = 28 // to send registration email
    static final String TRIAL_ACTIVE_FIRST_PROJECT_TAG_ID = 189 // user with active trial created the first project
    static final String TRIAL_ACTIVE_FIRST_DESIGN_TAG_ID = 191 // user with active trial created the first design
    static final String TRIAL_ACTIVE_FIRST_QUERY_INPUT_TAG_ID = 192 // user with active trial input the first query
    static final String TRIAL_ACTIVE_SECOND_DESIGN_TAG_ID = 193 // user with active trial created the second design
    static final String TRIAL_ACTIVATED_TAG_ID = 194 // user with active trial activated the license
    static final String TRIAL_STUCK_PARAMETER_QUERY_TAG_ID = 200 // user with active trial stucks at the parameter query
    static final String TRIAL_COMPLETED_TAG_ID = 203 // user completed the trial
    static final String USER_INVITED_TAG_ID = 218 // invited user to paid account
    static final String ACTION_IMPORT_FEATURE_REQUEST_TAG_ID = 219
    static final String USER_MALTA_CHANNEL_TAG_ID = 243 // user is using the Malta channel
    static final String SEND_AUTHENTICATION_TOKEN_TAG_ID = 273
    static final String EPD_REQUEST_RECEIVER_TAG_ID = 288 // to send EPD request email
    static final String USER_PLANETARY_TAG_ID = 303
    static final String ACTION_ACCOUNT_OCL_ACTIVATED = 369

    static final String CONSENT_TO_COMMUNICATION_MSG = "Yes, contact me a few times a year for newsletters and special offers (you can opt out at any time)."
    static final String NO_CONSENT_TO_COMMUNICATION_MSG = "No, thank you. I only want to receive essential service notices."

    /**  NOT IN USE:
     *   TRIAL Active – Parameter query:	190	- User with active trial input the parameter query
     *   TRIAL Stuck – Trial Activated:	196	- User with active trial stucks at the license
     *   TRIAL Stuck – First project:	197	- User with active trial stucks at the first project
     *   TRIAL Stuck – First design: 	199	- User with active trial stucks at the first design
     *   TRIAL Stuck – First query input:	201	- User with active trial stucks at the first query input
     *   TRIAL Stuck – Second design:	202	- User with active trial stucks at the second design
     *   TRIAL Inactive – First project:	204	- User failed trial at the license
     *   TRIAL Inactive – First design:	205	- User failed trial at the first design
     *   TRIAL Inactive – Parameter query:	206	- User failed trial at the parameter query
     *   TRIAL Inactive – First query input:	207	- User failed trial at the first query input
     *   TRIAL Inactive – Second designs:	208	- User failed trial at the second design
     *   CONSENT_FOR_MARKETING: 102 - not sure if still use
     */
    private boolean isActiveCampaignAllowed(){
        boolean allowed = configurationService.getConfigurationValue(Constants.APPLICATION_ID, "activeCampaingEnabled") == "true" ? true : false
        return  allowed
    }

    def createContact(User user, String activationLink, ChannelFeature channelFeature, HttpSession session) {
        Boolean createContactOk = Boolean.FALSE
        String accountId

        if (user && isActiveCampaignAllowed()) {
            if (user.organizationName) {
                accountId = getActiveCampaignAccountIdForUser(user, null ,null)
            }

            try {

                Map jsonMap = [contact:[email:user.username, phone: user.phone, firstName: user.name]]
                JsonBuilder jsonasd = new JsonBuilder(jsonMap)

                Map<String, String> userInfoForField = [(COUNTRY_FIELD_ID.toString()): com.bionova.optimi.core.Constants.countryIdToActiveCampaingId.get(user.country) ?: 'N/A',
                                                        (ACCOUNT_ACTIVATION_LINK_FIELD_ID.toString()) : activationLink,
                                                        (LANGUAGE_FIELD_ID.toString()) : user.language ?: 'English',
                                                        (PERSON_ROLE_FIELD_ID.toString()) : user.type ?: 'None',
                                                        (CONSENT_TO_COMMUNICATION_FIELD_ID.toString()) : user.consent ? CONSENT_TO_COMMUNICATION_MSG : NO_CONSENT_TO_COMMUNICATION_MSG,
                                                        (ACCOUNT_CREATED_DATE_FIELD_ID.toString()) : new SimpleDateFormat("yyyy-MM-dd").format(user.registrationTime)
                ]
                RestBuilder rest = new RestBuilder()
                RestResponse response = rest.post(ContactSyncOrCreateEndpoint) {
                    header("Api-Token", ApiToken)
                    contentType("application/json")
                    json jsonasd.toString()
                }

                if (201 == response.status || 200 == response.status) {
                    log.info("ActiveCampaing: ${user.username} contact created successfully. Response status: ${response?.status}, json: ${response?.responseEntity?.body?.toString()}")

                    JSONObject contactJson = response.json?.get("contact")
                    String contactId = contactJson?.get("id")

                    if (contactId) {
                        createContactOk = Boolean.TRUE

                        if (accountId) {
                            RestResponse accountContactResponse = rest.post(AccountContactsEndPoint) {
                                header("Api-Token", ApiToken)
                                contentType("application/json")
                                json {
                                    accountContact = {
                                        contact = "${contactId}".toString()
                                        account = "${accountId}".toString()
                                    }
                                }
                            }
                            if (accountContactResponse.status == 201) {
                                log.info("ActiveCampaing: Contact and account linked successfully: Response status: ${accountContactResponse?.status}, json: ${accountContactResponse?.responseEntity?.body?.toString()}")
                            } else {
                                log.warn("ActiveCampaing: Error in creating accountContact ${user.username}. Response status: ${accountContactResponse?.status}, json: ${accountContactResponse?.responseEntity?.body?.toString()}")
                                flashService.setErrorAlert("ActiveCampaing: Error in creating contact ${user.username}. Response status: ${response?.status}, json: ${response?.responseEntity?.body?.toString()}", true)
                            }
                        }
                        //save activeCampaignId to user on registration
                        user.activeCampaignId = contactId


                        if("student".equalsIgnoreCase(user.type)){
                            user.activeCampaignUserTypeValue = USER_STUDENT_TAG_ID
                            user.activeCampaignUserTypeTagId = updateUniqueTagForUser(contactId, user.activeCampaignUserTypeValue)
                        }
                        userService.updateUser(user)

                        if(userInfoForField){
                            Promise promise = task {
                                updateFieldsForUser(user, contactId, userInfoForField)
                                List<String> tagToAddToAccount = [ACTION_ACCOUNT_CREATED_TAG_ID]
                                //Trigger add tag for activation email and user type
                                Boolean ok = addTagForUser(contactId, tagToAddToAccount)
                                if(ok){
                                    log.info("ActiveCampaing: Tags added successfully to account")
                                } else {
                                    log.error("ActiveCampaing: Error in adding tags to account")
                                    flashService.setErrorAlert("ActiveCampaing: Error in adding tags to account", true)
                                }
                            }
                            promise.onError { Throwable err ->
                                log.error("ActiveCampaing: An error occured ${err.message}")
                                flashService.setErrorAlert("ActiveCampaing: An error occured ${err.message}", true)
                            }

                        }

                    } else {
                        createContactOk = Boolean.FALSE
                    }
                    return createContactOk
                } else {
                    log.warn("ActiveCampaing: Error in creating contact ${user.username}. Response status: ${response?.status}, json: ${response?.responseEntity?.body?.toString()}")
                    flashService.setErrorAlert("ActiveCampaing: Error in creating contact ${user.username}. Response status: ${response?.status}, json: ${response?.responseEntity?.body?.toString()}", true)
                    createContactOk = sendRegistrationMail(user, activationLink, channelFeature)
                    return createContactOk

                }
            } catch (Exception e) {
                log.error("ActiveCampaing: CreateContact error: ${e}")
                createContactOk = Boolean.FALSE
                return createContactOk
            }
        } else {
            log.error("ActiveCampaing: No user object given?")
            createContactOk = Boolean.FALSE
            return createContactOk
        }
    }
    //For EPD REQUEST CONTACT creation and sending email, since no user object is created in the SW, tag ID need to be saved on AC for this feature to be resend in the future for the same recipient
    def createContactAndSendEmailForEpdRequest(String recipientEmail, String linkToRequest, String senderInfo, String projectInfo) {
        Boolean success = Boolean.FALSE
        if(isActiveCampaignAllowed()){
            Map jsonMap = [contact: [email: recipientEmail, firstName: recipientEmail]]
            JsonBuilder jsonasd = new JsonBuilder(jsonMap)

            Map<String, String> userInfoForField = [(LINK_TO_EPD_REQUEST_FIELD_ID.toString())     : linkToRequest,
                                                    (EPD_REQUEST_SENDER_FIELD_ID.toString())      : senderInfo,
                                                    (EPD_REQUEST_PROJECT_NAME_FIELD_ID.toString()): projectInfo]
            RestBuilder rest = new RestBuilder()
            RestResponse response = rest.post(ContactSyncOrCreateEndpoint) {
                header("Api-Token", ApiToken)
                contentType("application/json")
                json jsonasd.toString()
            }
            if (201 == response.status || 200 == response.status) {
                log.info("ActiveCampaing: ${recipientEmail} contact created successfully. Response status: ${response?.status}, json: ${response?.responseEntity?.body?.toString()}")
                flashService.setFadeSuccessAlert("ActiveCampaing: ${recipientEmail} contact created successfully. Response status: ${response?.status}, json: ${response?.responseEntity?.body?.toString()}", true)

                JSONObject contactJson = response.json?.get("contact")
                String contactId = contactJson?.get("id")

                if (contactId) {
                    success = Boolean.TRUE
                    String idOfEpdReqTag
                    if(200== response.status){
                        RestBuilder restForFullContact = new RestBuilder()
                        RestResponse responseFullContact = restForFullContact.get(ContactEndpoint+"/"+contactId+"/fieldValues") {
                            header("Api-Token", ApiToken)
                        }
                        idOfEpdReqTag = responseFullContact.json?.get("fieldValues")?.find({it.get("field") == TAG_ID_VALUE_FOR_EPD_FIELD_ID})?.get("value")
                    }
                    if(userInfoForField){
                        Promise promise = task {
                            updateFieldsForUser(null, contactId, userInfoForField)
                        }
                        promise.onError { Throwable err ->
                            log.error("ActiveCampaing: An error occured ${err.message}")
                            flashService.setErrorAlert("ActiveCampaing: An error occured ${err.message}", true)
                            success = Boolean.FALSE
                        }
                        //Trigger add tag for send epd request
                        Boolean ok = updateUniqueTagForUser(contactId, EPD_REQUEST_RECEIVER_TAG_ID,idOfEpdReqTag,true,TAG_ID_VALUE_FOR_EPD_FIELD_ID )
                        if(ok){
                            log.info("ActiveCampaing: Tags re-added successfully to account")
                            flashService.setFadeSuccessAlert("ActiveCampaing: Tags re-added successfully to account", true)
                        } else {
                            log.error("ActiveCampaing: Error in re-adding tags to account")
                            flashService.setErrorAlert("ActiveCampaing: Error in re-adding tags to account", true)
                            success = Boolean.FALSE
                        }
                    }
                    return success
                } else {
                    success = Boolean.FALSE
                }
                return success
            } else {
                log.error("ActiveCampaing: Error in creating contact ${recipientEmail}. Response status: ${response?.status}, json: ${response?.responseEntity?.body?.toString()}")
                flashService.setErrorAlert("ActiveCampaing: Error in creating contact ${recipientEmail}. Response status: ${response?.status}, json: ${response?.responseEntity?.body?.toString()}", true)
                return success
            }
        }
        log.error("ActiveCampaing disabled")
        flashService.setErrorAlert("ActiveCampaing disabled", true)
        return success

    }

    private String getActiveCampaignAccountIdForUser(User user, String userEmail = null, String organizationName) {
        String activecampaingOrgId = null
        if(isActiveCampaignAllowed()){
            if (user && user.organizationName) {
                try {
                    RestBuilder rest = new RestBuilder()
                    RestResponse response = rest.get(AccountSearchEndPoint) {
                        header("Api-Token", ApiToken)
                        urlVariables([organizationName: user.organizationName])
                    }

                    if (response.status == 200) {
                        JSONArray accounts = response.json?.get("accounts")

                        if (accounts) {
                            for (JSONObject account in accounts) {
                                String accountName = account.get("name")

                                if (accountName && accountName.equalsIgnoreCase(user.organizationName)) {
                                    activecampaingOrgId = account.get("id")
                                    break
                                }
                            }
                        }
                    }

                    if (!activecampaingOrgId && user.organizationName) {
                        RestResponse createAccountResponse = rest.post(AccountEndPoint) {
                            header("Api-Token", ApiToken)
                            contentType("application/json")
                            json {
                                account = {
                                    name = "${user.organizationName}".toString()
                                }
                            }
                        }

                        if (createAccountResponse.status == 201) {
                            JSONObject account = createAccountResponse.json?.get("account")

                            if (account) {
                                activecampaingOrgId = account.get("id")
                            }
                        }
                    }
                } catch (Exception e) {
                    loggerUtil.error(log, "Error in getting active campaign", e)
                    flashService.setErrorAlert("Error in getting active campaign: ${e.getMessage()}", true)
                }
            } else if (organizationName){
                try {
                    RestBuilder rest = new RestBuilder()
                    RestResponse response1 = rest.get(AccountSearchEndPoint) {
                        header("Api-Token", ApiToken)
                        urlVariables([organizationName: organizationName])
                    }

                    if (response1.status == 200) {
                        JSONArray accounts = response1.json?.get("accounts")

                        if (accounts) {
                            for (JSONObject account in accounts) {
                                String accountName = account.get("name")

                                if (accountName && accountName.equalsIgnoreCase(organizationName)) {
                                    activecampaingOrgId = account.get("id")
                                    break
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    loggerUtil.error(log, "Error in getting active campaign", e)
                    flashService.setErrorAlert("Error in getting active campaign: ${e.getMessage()}", true)
                }
            }
        }


        return activecampaingOrgId
    }

    private boolean sendRegistrationMail(User user, registerUrl, ChannelFeature channelFeature, String userEmail = null) {
        boolean ok = false
        def locale = LocaleContextHolder.getLocale()
        def emailTo = user? user.username : userEmail
        def emailSubject
        def emailFrom
        def replyToAddress
        def emailBody

        try {
            emailSubject = messageSource.getMessage('index.registration_mail.subject', null, locale)
            emailFrom = messageSource.getMessage('email.support', null, locale)
            replyToAddress = messageSource.getMessage('email.support', null, locale)
            emailBody = messageSource.getMessage('index.registration_mail.body', [registerUrl, channelFeature?.loginUrl].toArray(), locale)
        } catch (Exception e) {
            log.error("${e}")
        }

        if (emailTo && emailFrom && replyToAddress && emailSubject && emailBody) {
            optimiMailService.sendMail({
                to emailTo
                from emailFrom
                replyTo replyToAddress
                subject emailSubject
                body emailBody
            }, emailTo)
            ok = true
        }
        return ok
    }

    String updateTrialStatusTagForUser(User user, String newTag, HttpSession session = null, String trialProjectLink = null){
        String newTagId
        if(isActiveCampaignAllowed()){
            if(user && user.activeCampaignId && newTag){
                TrialStatus trialStatus = user.trialStatus
                String oldTag = trialStatus.tagStatus
                if(!newTag.equalsIgnoreCase(oldTag)){
                    String tagId = trialStatus.tagId
                    newTagId = updateUniqueTagForUser(user.activeCampaignId, newTag, tagId)
                }
            }
            if(TRIAL_ACTIVATED_TAG_ID.equalsIgnoreCase(newTag) && session){
                Boolean addTagOCLTrial = updateUserType(user, "trial", session, null, null, null, null, null, trialProjectLink)
            }
        }

        return newTagId
    }

    def addTagForUser(String contactId, List<String> newTags, Boolean saveTagIdToAC = Boolean.FALSE, String fieldToSaveTagId = null){
        Boolean ok = Boolean.TRUE
        if(contactId && newTags && isActiveCampaignAllowed()){
            RestBuilder rest = new RestBuilder()
            newTags.each {String tag ->
                Map jsonMap = [contactTag:[contact: contactId, tag : tag]]
                JsonBuilder jsonObject = new JsonBuilder(jsonMap)
                RestResponse responseUpdate = rest.post(ContactTagEndPoint) {
                    header("Api-Token", ApiToken)
                    contentType("application/json")
                    json jsonObject.toString()
                }
                log.info("ACTIVE CAMPAIGN RESPONSE: ${responseUpdate.json}")
                flashService.setFadeSuccessAlert("ACTIVE CAMPAIGN RESPONSE: ${responseUpdate.json}", true)
                if(responseUpdate.status != 201){
                    ok = Boolean.FALSE
                } else {
                    if(saveTagIdToAC && fieldToSaveTagId){
                        Map<String, String> userInfoForField = [(fieldToSaveTagId): "${responseUpdate.json?.get("contactTag")?.get("id")}"]
                        updateFieldsForUser(null, contactId, userInfoForField)

                    }
                }
                sleep(300)
            }
        }
        return ok
    }
    def updateUniqueTagForUser(String contactId, String newTagValue, String tagIdToDelete = null, Boolean saveTagIdToAC = Boolean.FALSE, String fieldToSaveTagId = null){
        String newTagId
        if(contactId && newTagValue && isActiveCampaignAllowed()){
            RestBuilder rest = new RestBuilder()
            if(tagIdToDelete){
                RestResponse responseDelete = rest.delete(ContactTagDeleteEndPoint){
                    header("Api-Token", ApiToken)
                    contentType("application/json")
                    urlVariables([id: tagIdToDelete])
                }
            }
            Map jsonMap = [contactTag:[contact: contactId, tag : newTagValue]]
            JsonBuilder jsonObject = new JsonBuilder(jsonMap)
            RestResponse responseUpdate = rest.post(ContactTagEndPoint) {
                header("Api-Token", ApiToken)
                contentType("application/json")
                json jsonObject.toString()
            }

            if(responseUpdate.status == 201 || responseUpdate.status == 200 ){
                newTagId = responseUpdate.json?.get("contactTag")?.get("id")
                if(saveTagIdToAC && fieldToSaveTagId){
                    Map<String, String> userInfoForField = [(fieldToSaveTagId): "${newTagId}"]
                    updateFieldsForUser(null, contactId, userInfoForField)

                }
                log.info("ActiveCampaigning: update tag successfully")
                flashService.setFadeSuccessAlert("ActiveCampaigning: update tag successfully", true)
            }else {
                log.error("ActiveCampaigning: error update trial tags. Error status ${responseUpdate.status} . Error body ${responseUpdate.json}")
                flashService.setErrorAlert("ActiveCampaigning: error update trial tags. Error status ${responseUpdate.status} . Error body ${responseUpdate.json}", true)

                if (TRIAL_ACTIVATED_TAG_ID.equals(newTagValue)) {
                    User user = userService.getCurrentUser()

                    if (user && Environment.current == Environment.PRODUCTION) {
                        String userCountry = user.country ? userService.getCountryResource(user.country)?.nameEN : ""
                        String htmlBody = "" +
                                "Name: ${user.name}<br/>" +
                                "Email: ${user.username}<br/>" +
                                "Consent: ${user.consent ? "TRUE" : "FALSE"}<br/>" +
                                "Phone: ${user.phone}<br/>" +
                                "Organisation: ${user.organizationName}<br/>" +
                                "Country: ${userCountry}"

                        optimiMailService.sendMail({
                            to emailConfiguration.salesEmail, emailConfiguration.helloEmail
                            from emailConfiguration.contactEmail
                            subject("PROD: ActiveCampaigning adding tag failed, User ${user.name} (${user.username}), ${userCountry} activated a trial project")
                            html htmlBody
                        }, "${emailConfiguration.salesEmail},${emailConfiguration.helloEmail}")
                    }
                }
            }
        }
        return newTagId
    }

    def updateFieldsForUser(User user, String contactId, Map<String, String> fieldsAndValue){
        if((user || (!user && contactId && contactId != "null")) && fieldsAndValue && isActiveCampaignAllowed()){
            if(!contactId){
                contactId = user?.activeCampaignId ?: getActiveCampaignContactIdForUser(user)
            }
            sleep(1000)
            fieldsAndValue.each {k,v ->
                try {
                    RestBuilder rest = new RestBuilder()
                    Map jsonMap = [fieldValue:[contact: contactId, field: k, value: v]]
                    JsonBuilder jsonObject = new JsonBuilder(jsonMap)

                    RestResponse response = rest.post(FieldValueEndPoint) {
                        header("Api-Token", ApiToken)
                        contentType("application/json")
                        json jsonObject.toString()
                    }
                    sleep(300)
                } catch(Exception e) {
                    loggerUtil.error(log, "Active Campaign: Failed to update field for user ${user}. Contact Id : ${contactId}, field ${k}  - value ${v}", e)
                    flashService.setErrorAlert("Active Campaign: Failed to update field for user ${user}. Contact Id : ${contactId}, field ${k}  - value ${v}: ${e.getMessage()}", true)
                }
            }
        }
    }
    def getActiveCampaignContactIdForUser(User user, String userEmailOnly = null) {
        String contactId
        String userEmail = user?.username ?: userEmailOnly
        if (userEmail && isActiveCampaignAllowed()) {
            try {
                Map jsonMap = [contact: [email: userEmail]]
                JsonBuilder jsonify = new JsonBuilder(jsonMap)

                RestBuilder rest = new RestBuilder()
                RestResponse response = rest.post(ContactSyncOrCreateEndpoint) {
                    header("Api-Token", ApiToken)
                    contentType("application/json")
                    json jsonify.toString()
                }

                if (201 == response.status || 200 == response.status) {
                    JSONObject contactJson = response.json?.get("contact")
                    contactId = contactJson?.get("id")
                } else {
                    flashService.setErrorAlert("ActiveCampaing: Error in retrieving  accountContact ${userEmail}. Response status: ${response?.status}, json: ${response?.responseEntity?.body?.toString()}", true)
                }
            } catch (Exception e) {
                log.error("ActiveCampaing: Error in retrieving  accountContact ${userEmail}: ${e}.")
                loggerUtil.error(log, "ActiveCampaing: Error in retrieving  accountContact ${userEmail}", e)
                flashService.setErrorAlert("ActiveCampaing: Error in retrieving  accountContact ${userEmail}: ${e.getMessage()}", true)
            }
        }
        return contactId
    }
    def requestImportTrial(User user){
        Boolean ok = Boolean.FALSE
        if(isActiveCampaignAllowed()){
            String contactId = user.activeCampaignId ?: getActiveCampaignContactIdForUser(user)
            if(contactId) {
                ok = addTagForUser(contactId, [ACTION_IMPORT_FEATURE_REQUEST_TAG_ID])
                if(!ok){
                    String htmlBody = "" +
                            "Name: ${user.name}<br/>" +
                            "Organization: ${user.organizationName}<br/>"+
                            "Email: ${user.username}<br/>" +
                            "Consent: ${user.consent ? "TRUE" : "FALSE"}<br/>" +
                            "Phone: ${user.phone}"
                    optimiMailService.sendMail({
                        async true
                        to emailConfiguration.salesLeadEmail
                        from emailConfiguration.contactEmail
                        subject("${Environment.current == Environment.PRODUCTION ? "PROD: " : "DEV: "}Import Feature Trial License Request")
                        html htmlBody
                    }, emailConfiguration.salesLeadEmail)
                    ok = Boolean.TRUE
                }
            }
        }

        return ok
    }
    def updateUserType(User user, String typeOfUser, HttpSession session = null, String userEmail = null, String organizationName = null, String adder = null, String entityName = null, String registrationLink = null, String trialProjectLink = null) {
        Boolean ok = Boolean.FALSE
        if(isActiveCampaignAllowed()){
            if(user){
                userEmail = user.username
            }
            //Get contactId previously saved or create new contact
            String contactId = user?.activeCampaignId ?: getActiveCampaignContactIdForUser(null, userEmail)
            if(contactId){
                if(typeOfUser){
                    String oldUserTagId  = user?.activeCampaignUserTypeTagId
                    String oldUserTypeValue = userService.getActiveAutoStartTrials()
                    String newUserTypeValue
                    switch (typeOfUser){
                        case "student":
                            newUserTypeValue = USER_STUDENT_TAG_ID
                            break
                        case "commercial":
                            newUserTypeValue = USER_COMMERCIAL_TAG_ID
                            break
                        case "trial":
                            newUserTypeValue = USER_TRIAL_TAG_ID
                            break
                    }
                    if(!newUserTypeValue.equalsIgnoreCase(oldUserTypeValue)){
                        String newTagId = updateUniqueTagForUser(contactId,newUserTypeValue, oldUserTagId)
                        if(user && session){
                            user.activeCampaignId = contactId
                            user.activeCampaignUserTypeTagId = newTagId
                            user.activeCampaignUserTypeValue = newUserTypeValue
                            userService.updateUser(user)
                        }
                        ok = Boolean.TRUE
                    }
                } else if (!typeOfUser && user && !user.activeCampaignId){
                    user.activeCampaignId = contactId
                    userService.updateUser(user)
                    ok = Boolean.TRUE
                }
                //ADDING FIELD VALUE FOR DIFFERENT PURPOSES AND ADD TO USER ACCOUNT ACTIVE CAMPAIGN
                Map<String, String> mapFieldUpdate = [:]
                if(adder){
                    mapFieldUpdate.put(INVITING_USER_FIELD_ID, adder)
                }
                if(entityName){
                    mapFieldUpdate.put(ENTITY_FIELD_ID, entityName) // NAME OR LINK TO PROJECT
                }
                if(registrationLink){
                    mapFieldUpdate.put(ACCOUNT_ACTIVATION_LINK_FIELD_ID, registrationLink)
                }
                if (trialProjectLink) {
                    mapFieldUpdate.put(TRIAL_ENTITY_FIELD_ID, trialProjectLink)
                }
                updateFieldsForUser(null, contactId, mapFieldUpdate)
                List<String> tagToAddToAccount = []
                if(!user){
                    tagToAddToAccount.push(USER_INVITED_TAG_ID)
                }
                addTagForUser(contactId, tagToAddToAccount)
                if(organizationName){
                    String accountId = getActiveCampaignAccountIdForUser(null, null, organizationName)
                    if(accountId){
                        updateUserAccount(contactId, accountId)
                    }
                }
            } else {
                log.warn("Contact Id missing. Update failed")
                flashService.setErrorAlert("Contact Id missing. Update failed", true)
            }
        }

        return ok
    }
    def updateUserAccount(String contactId, String accountId){
        if(isActiveCampaignAllowed()){
            try {
                if (contactId) {
                    if (accountId) {
                        RestBuilder rest = new RestBuilder()
                        RestResponse accountContactResponse = rest.post(AccountContactsEndPoint) {
                            header("Api-Token", ApiToken)
                            contentType("application/json")
                            json {
                                accountContact = {
                                    contact = "${contactId}".toString()
                                    account = "${accountId}".toString()
                                }
                            }
                        }

                        if (accountContactResponse.status == 201 || accountContactResponse.status == 200 ) {
                            log.info("ActiveCampaing: Contact and account linked successfully: Response status: ${accountContactResponse?.status}, json: ${accountContactResponse?.responseEntity?.body?.toString()}")
                            flashService.setFadeSuccessAlert("ActiveCampaing: Contact and account linked successfully: Response status: ${accountContactResponse?.status}, json: ${accountContactResponse?.responseEntity?.body?.toString()}", true)
                        } else {
                            log.warn("ActiveCampaing: Error in creating accountContact with contact Id ${contactId}. Response status: ${accountContactResponse?.status}, json: ${accountContactResponse?.responseEntity?.body?.toString()}")
                            flashService.setErrorAlert("ActiveCampaing: Error in creating accountContact with contact Id ${contactId}. Response status: ${accountContactResponse?.status}, json: ${accountContactResponse?.responseEntity?.body?.toString()}", true)
                        }
                    }
                } else {
                    log.warn("ActiveCampaing Update User error 1: Error in updating contact with contact Id ${contactId}.")
                    flashService.setErrorAlert("ActiveCampaing Update User error 1: Error in updating contact with contact Id ${contactId}.", true)
                }
            } catch(Exception e){
                log.error("ActiveCampaing Update User error 2: Error in updating contact with contact Id ${contactId}. Exception: ${e}")
            }
        }
    }
}

