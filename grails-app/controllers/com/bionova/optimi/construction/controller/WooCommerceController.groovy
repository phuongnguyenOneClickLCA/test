package com.bionova.optimi.construction.controller

import com.bionova.optimi.construction.Constants
import com.bionova.optimi.core.domain.mongo.Account
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.User
import grails.converters.JSON
import org.apache.commons.codec.digest.DigestUtils

/**
 * @author Pasi-Markus Mäkelä
 */
class WooCommerceController extends ExceptionHandlerController {

    def configurationService
    def userService
    def optimiResourceService
    def accountService
    def optimiStringUtils

    def index() {
        User user = userService.getCurrentUser()
        Account account
        String productId = params.productId

        if (params.accountId) {
            account = accountService.getAccount(params.accountId)
        } else {
            account = userService.getAccount(user)

            if (account) {
                flash.fadeSuccessAlert = message(code: 'account.shopping_as', args: [account.companyName])
            }
        }
        String firstname
        String lastname

        if (user && user.name.contains(" ")) {
            firstname = user.name.tokenize(' ')[0]
            lastname = user.name.tokenize(' ')[1]
        } else {
            firstname = user?.name
            lastname = user?.name
        }

        if (accountService.getEcommerceValidates(account)) {
            String wooCommerceUserKey = configurationService.getConfigurationValue(Constants.APPLICATION_ID, "wooCommerceUserKey")
            String wooCommerceReturnUrl = configurationService.getConfigurationValue(Constants.APPLICATION_ID, "wooCommerceReturnUrl")
            String wooCommerceApiUrl = configurationService.getConfigurationValue(Constants.APPLICATION_ID, "wooCommerceApiUrl")
            List<Resource> countries = optimiResourceService.getResourcesByResourceGroupsAndSkipResourceTypes(["world"], null, null, null, null, null)
            List<Resource> states = optimiResourceService.getResourcesByResourceGroupsAndSkipResourceTypes(["US-states", "CA-provinces"], null, null, null, null, null)
            [user                : user, account: account, countries: countries, states: states, wooCommerceApiUrl: wooCommerceApiUrl,
             timestamp           : new Date().time, firstname: firstname, lastname: lastname, wooCommerceUserKey: wooCommerceUserKey,
             wooCommerceReturnUrl: wooCommerceReturnUrl, productId: productId]
        } else if(params.publicShop) {
            String wooCommerceUserKey = configurationService.getConfigurationValue(Constants.APPLICATION_ID, "wooCommerceUserKey")
            String wooCommerceReturnUrl = configurationService.getConfigurationValue(Constants.APPLICATION_ID, "wooCommerceReturnUrl")
            String wooCommerceApiUrl = configurationService.getConfigurationValue(Constants.APPLICATION_ID, "wooCommerceApiUrl")
            [user                : user, wooCommerceApiUrl: wooCommerceApiUrl,
             timestamp           : new Date().time, firstname: firstname, lastname: lastname, wooCommerceUserKey: wooCommerceUserKey,
             wooCommerceReturnUrl: wooCommerceReturnUrl, productId: productId]
        } else {
            flash.fadeSuccessAlert = message(code: "account.ecommerceValidates.false")
            redirect controller: "account", action: "form", id: account?.id
        }
    }

    def recreateHash() {
        String wooCommerceApiKey = configurationService.getConfigurationValue(Constants.APPLICATION_ID, "wooCommerceApiKey")
        String wooCommerceUserKey = params.access_key
        String wooCommerceReturnUrl = params.return_url
        String timestamp = params.timestamp
        String userId = params.user_id
        String productIds = params.product_ids
        String straigthToCheckout = params.straight_to_checkout
        String firstname = params.firstname
        String lastname = params.lastname
        String phone = params.phone
        String email = params.email
        String companyName = params.companyName
        String address1 = params.address1
        String address2 = params.address2
        String address3 = params.address3
        String privateCatalogues = params.private_catalogues

        if (address3) {
            address2 = address2 ? address2 + " " + address3 : address3
        }
        String postcode = params.postcode
        String town = params.town
        String state = params.state
        String country = params.country
        String vatNumber = params.vatNumber
        String hash = "${timestamp}+${userId ? userId : ''}+${productIds ? productIds : ''}+${straigthToCheckout}+${wooCommerceReturnUrl}+${wooCommerceUserKey}+" +
                "${firstname ? firstname : ''}+${lastname ? lastname : ''}+${phone ? phone : ''}+${email ? email : ''}+${companyName ? companyName : ''}+${address1 ? address1 : ''}+" +
                "${address2 ? address2 : ''}+${postcode ? postcode : ''}+${town ? town : ''}+${state ? state : ''}+${country ? country : ''}+${vatNumber ? vatNumber : ''}+" +
                "${privateCatalogues ? privateCatalogues : ''}+${wooCommerceApiKey}"
        log.info("String: ${hash}")
        hash = optimiStringUtils.escapeSingleAndDoubleQuotes(hash)
        hash = DigestUtils.sha256Hex(hash.bytes)
        log.info("Hash: ${hash}")
        render([output: hash, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    private Map getUserAsJson(User user) {
        def returnArray = [:]

        if (user.name.contains(" ")) {
            returnArray.put("firstname", user.name.tokenize(' ')[0])
            returnArray.put("lastname", user.name.tokenize(' ')[1])
        } else {
            returnArray.put("firstname", user.name)
            returnArray.put("lastname", user.name)
        }
        Account account = userService.getAccount(user)
        returnArray.put("company", user.organizationName ? user.organizationName : account?.companyName)
        returnArray.put("vat", account?.vatNumber)
        returnArray.put("address1", account?.addressLine1)
        returnArray.put("address2", account?.addressLine2)
        returnArray.put("city", account?.town)
        returnArray.put("postCode", account?.postcode)
        returnArray.put("country", account?.country)
        returnArray.put("state", account?.state)
        returnArray.put("email", user.username)
        returnArray.put("phone", user.phone)
        return returnArray
    }

}
