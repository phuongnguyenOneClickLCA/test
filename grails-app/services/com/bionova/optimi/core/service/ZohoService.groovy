package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.domain.mongo.UserDataRequest
import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse
import grails.web.mapping.LinkGenerator
import org.apache.commons.io.FilenameUtils
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.*
import org.springframework.web.multipart.MultipartFile

import javax.servlet.http.HttpSession

class ZohoService {

    def userService
    LinkGenerator grailsLinkGenerator

    private String OAuthEndPoint = "https://accounts.zoho.eu/oauth/v2/token"
    private String APIEndPoint = "https://desk.zoho.eu/api/v1/tickets"
    private String refresh_token = "1000.1bca7db4f68b6e501dba228189d3ecd0.66fe9299df866fdae08049967ae883be"
    private String client_id = "1000.MHBQ0PBOU7T089197XTB5GC11M1ECK"
    private String client_secret = "f6eed5fb2f19be6412910cefe95b9605ae3ebc7fe7"
    private String scope = "Desk.tickets.CREATE"
    private String scope_update = "Desk.tickets.UPDATE"
    private String grant_type = "refresh_token"
    private String orgId = "20064576101"

    private String contact_APIEndPoint = "https://desk.zoho.eu/api/v1/contacts"
    private String contact_scope = "Desk.contacts.CREATE"
    private String contact_refresh_token = "1000.421fa7837e062fe0d59fad118014e437.f97c3c0b7c688f4c2bab823ddff482a1"

    private String update_refresh_token = "1000.5e53842a1ecf2444d58278385c511947.ee5ec74155e87a287711b049ea1c0ecb"

    private String getZohoOAuthToken(String scope, String refresh_token) {
        RestBuilder rest = new RestBuilder()

        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<String, String>()
        paramMap.add("refresh_token", refresh_token)
        paramMap.add("client_id", client_id)
        paramMap.add("client_secret", client_secret)
        paramMap.add("scope", scope)
        paramMap.add("grant_type", grant_type)

        RestResponse response = rest.post(OAuthEndPoint) {
            accept("application/json")
            contentType("application/x-www-form-urlencoded")
            body(paramMap)
        }

        return response.json.get("access_token")?.toString()
    }

    private String getZohoContactOAuthToken() {
        RestBuilder rest = new RestBuilder()

        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<String, String>()
        paramMap.add("refresh_token", contact_refresh_token)
        paramMap.add("client_id", client_id)
        paramMap.add("client_secret", client_secret)
        paramMap.add("scope", contact_scope)
        paramMap.add("grant_type", grant_type)

        RestResponse response = rest.post(OAuthEndPoint) {
            accept("application/json")
            contentType("application/x-www-form-urlencoded")
            body(paramMap)
        }
        return response.json.get("access_token")?.toString()
    }

    private String createZohoContact(User user) {
        String authToken = getZohoContactOAuthToken()
        String zohoContactId

        if (user && authToken) {
            RestBuilder rest = new RestBuilder()
            RestResponse response = rest.post(contact_APIEndPoint) {
                header("orgId", orgId)
                header("Authorization", "Zoho-oauthtoken ${authToken}".toString())
                contentType("application/json")

                json {
                    lastName = user.name
                    country = userService.getCountryResource(user.country)?.nameEN ?: ""
                    email = user.username
                    phone = user.phone ?: ""
                    ownerId = "12952000000045033"
                }
            }
            zohoContactId = response.json?.get("id")
        }
        return zohoContactId
    }

    RestResponse createZohoTicket(String userId, String name, String mailTo, String subj, String message, String currentEntity,
                                  MultipartFile file, String dataRequestLink, HttpSession session) {
        RestResponse response = null

        if (userId && name && mailTo && subj && message) {
            def maxSize = 30 * 1024 * 1024
            String fileName

            if (file != null && !file.empty && file.getSize() <= maxSize) {
                fileName = uploadZohoPdf(file)
                log.info("File name - Upload Ticket ${file?.getOriginalFilename()}")
            }

            if (fileName) {
                def linkDownload = getLinkForZohoFile(fileName)
                message = message + ". File present at the following link: " + linkDownload
            }

            if (dataRequestLink) {
                message = message + ". Related link: ${dataRequestLink}"
            }
            User user = userService.getUserById(userId)

            if (currentEntity) {
                subj = subj + " (From project " + currentEntity + ")"
            }

            if (user) {
                if (!user.zohoContactId) {
                    user.zohoContactId = createZohoContact(user)
                    user = userService.updateUser(user)
                }
                String authToken = getZohoOAuthToken(scope, refresh_token)

                if (authToken && user.zohoContactId) {
                    RestBuilder rest = new RestBuilder()
                    response = rest.post(APIEndPoint) {
                        header("orgId", orgId)
                        header("Authorization", "Zoho-oauthtoken ${authToken}".toString())
                        contentType("application/json")

                        json {
                            subCategory = "Sub General"
                            productId = ""
                            contactId = user.zohoContactId
                            subject = subj
                            customFields = {
                                mycustomfield = "my custom field"
                            }
                            departmentId = "12952000000007061"
                            channel = "Email"
                            description = message
                            priority = "High"
                            classification = ""
                            assigneeId = "12952000000045440"
                            phone = user.phone
                            category = "general"
                            email = mailTo
                            status = "Open"
                        }
                    }
                    UserDataRequest userDataRequest = new UserDataRequest()
                    userDataRequest.name = name
                    userDataRequest.email = mailTo
                    userDataRequest.message = message
                    userDataRequest.subject = subj
                    userDataRequest.created = new Date()
                    userDataRequest.dataRequestLink = dataRequestLink
                    userDataRequest.entity = currentEntity

                    if (fileName) {
                        userDataRequest.filePath = getFilePathToZohoDataRequest(fileName)
                    }
                    userDataRequest.save(flush: true)
                }
            }
        }
        return response
    }

    def uploadZohoPdf(MultipartFile file) {
        String fileName

        try {
            String extension = FilenameUtils.getExtension(file.getOriginalFilename())
            fileName = Math.random().toString().replaceAll("\\.", "")
            fileName += ".$extension"
            String filePath = getFilePathToZohoDataRequest(fileName)
            file.transferTo(new File(filePath))
        } catch (Exception e) {
            log.error("Error saving file zoho ticket $e")
            return ""
        }
        return fileName
    }

    def getLinkForZohoFile(fileName) {
        String link = grailsLinkGenerator.link(controller: 'util', 'action': 'downloadFile', absolute: true, params: [fileName: fileName])
        return """
            <a href="${link}">
                 Download File
            </a>
            """

    }

    def getFilePathToZohoDataRequest(fileName) {
        if (fileName)
            return "/tmp/$fileName"
        else
            return ""
    }
}
