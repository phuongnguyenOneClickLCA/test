package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.Account
import com.bionova.optimi.core.domain.mongo.AccountCatalogue
import com.bionova.optimi.core.domain.mongo.Construction
import com.bionova.optimi.core.domain.mongo.License
import com.bionova.optimi.core.domain.mongo.ProjectTemplate
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.taglib.UiTagLib
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.core.util.LoggerUtil
import com.mongodb.BasicDBObject
import grails.core.GrailsApplication
import grails.plugin.springsecurity.annotation.Secured
import grails.web.servlet.mvc.GrailsParameterMap
import org.bson.Document
import org.bson.types.ObjectId
import org.grails.plugins.web.taglib.ValidationTagLib

class AccountService {

    LicenseService licenseService
    UserService userService
    LoggerUtil loggerUtil
    FlashService flashService
    static transactional = "mongo"

    GrailsApplication grailsApplication

    List<Account> getAccounts() {
        return Account.list()?.sort({ it.companyName.toLowerCase() })
    }

    Account getAccounts(String loginKey) {
        if (!loginKey) {
            return null
        }
        return Account.findByLoginKey(loginKey)
    }

    def getAccountsForRender() {
        UiTagLib opt = grailsApplication.mainContext.getBean('com.bionova.optimi.core.taglib.UiTagLib')
        ValidationTagLib g = grailsApplication.mainContext.getBean('org.grails.plugins.web.taglib.ValidationTagLib')

        List<Document> accountsForRender = []
        List<Document> accounts = Account.collection.find([:], [_id                        : 1, addressLine1: 1, addressLine2: 1, addressLine3: 1,
                                                                postcode                   : 1, town: 1, state: 1, country: 1, loginKey: 1, visibleLoginUrl: 1, branding: 1,
                                                                companyName                : 1, licenseIds: 1, mainUserIds: 1, userIds: 1,
                                                                favoriteMaterialIdAndUserId: 1])?.toList()
        Boolean privateDatasetColumnSortable = true
        accounts.each { Document d ->
            String addressLine1 = d.addressLine1 ? "${d.addressLine1}, " : ''
            String addressLine2 = d.addressLine2 ? "${d.addressLine2}, " : ''
            String addressLine3 = d.addressLine3 ? "${d.addressLine3}, " : ''
            String postcode = d.postcode ? "${d.postcode}, " : ''
            String town = d.town ? "${d.town}, " : ''
            String state = d.state ? "${d.state}, " : ''
            String address = addressLine1 + addressLine2 + addressLine3 + postcode + town + state

            d.address = address.substring(0, address.length() - 2)?.encodeAsHTML() // remove the last ", "
            d._id = d._id.toString()
            d.loginKey = d.visibleLoginUrl ? "<input type=\"button\" class=\"btnRemove\" value=\"${d.visibleLoginUrl?.encodeAsHTML()}\" id=\"${d._id}loginKey\"/> <i class=\"far fa-clipboard copyToClipBoard\" onclick=\"copyText('${d._id}loginKey');\">" : ""
            d.brandingImg = d.branding ? opt.displayDomainClassImage(imageSource: d.branding.getData(), width: "80px") : ""
            d.deleteBtn = '<a href="/app/sec/account/delete/' + d._id + '" class="btn btn-danger" onclick="return modalConfirm(this);" data-questionstr="Are you sure you want to delete account: ' + d.companyName?.encodeAsHTML() + '" data-truestr="' + g.message(code: 'delete') + '" data-falsestr="' + g.message(code: 'cancel') + '" data-titlestr="Deleting the account">' + g.message(code: 'delete') + '</a>'
            d.companyName = '<a href="/app/sec/account/form/' + d._id + '">' + d.companyName?.encodeAsHTML() + '</a>' // do not move this line above d.deleteBtn = ...
            d.licensesCount = d.licenseIds ? d.licenseIds.size() : 0
            d.linkedUsersCount = (d.mainUserIds ? d.mainUserIds.size() : 0) + (d.userIds ? d.userIds.size() : 0)
            d.favoriteMaterialsCount = d.favoriteMaterialIdAndUserId ? d.favoriteMaterialIdAndUserId.size() : 0
            d.visibleLoginUrl = d.visibleLoginUrl?.encodeAsHTML()
            d.addressLine1 = d.addressLine1?.encodeAsHTML()
            d.addressLine2 = d.addressLine2?.encodeAsHTML()
            d.addressLine3 = d.addressLine3?.encodeAsHTML()
            d.postcode = d.postcode?.encodeAsHTML()
            d.town = d.town?.encodeAsHTML()
            d.state = d.state?.encodeAsHTML()

            BasicDBObject constructionQuery = new BasicDBObject()
            constructionQuery.put("privateConstructionAccountId", d._id)
            d.privateConstructionsCount = Construction.collection.countDocuments(constructionQuery)

            d.privateDatasetsCount = 0
            Integer newPrivateDatasetsCount = 0
            List<Document> privateDatasets = Resource.collection.find([privateDatasetAccountId: d._id], [isNewPrivateDataset: 1])?.toList()
            privateDatasets?.each { dataset ->
                if (dataset.isNewPrivateDataset) {
                    newPrivateDatasetsCount++
                } else {
                    d.privateDatasetsCount++
                }
            }
            if (newPrivateDatasetsCount > 0) {
                d.privateDatasetsCount = "$d.privateDatasetsCount + $newPrivateDatasetsCount"
                privateDatasetColumnSortable = false
            }
            accountsForRender.add(d)
        }
        return [accountsForRender: accountsForRender, privateDatasetColumnSortable: privateDatasetColumnSortable]
    }

    List<Account> getAccountsByIds(List<String> ids, Map<String, Object> projection = null) {
        List<Account> accounts = []

        if (ids) {
            if (projection) {
                accounts = Account.collection.find([_id: [$in: DomainObjectUtil.stringsToObjectIds(ids)]], projection)?.collect({ it as Account })
            } else {
                accounts = Account.collection.find([_id: [$in: DomainObjectUtil.stringsToObjectIds(ids)]])?.collect({ it as Account })
            }
        }
        return accounts
    }

    Account getAccount(String id) {
        Account account

        if (id) {
            account = Account.get(DomainObjectUtil.stringToObjectId(id))
        }
        return account
    }

    Account getAccountByUser(User user) {
        Account account
        if (user) {
            account = Account.collection.findOne([$or: [[mainUserIds: user.id.toString()], [userIds: user.id.toString()]]]) as Account
        }
        return account
    }

    List<Account> getAllAccountsForUser(User user) {
        List<Account> accounts
        if (user) accounts = Account.collection.find([$or: [[mainUserIds: user.id.toString()], [userIds: user.id.toString()]]])?.toList()?.collect({ it as Account })
        return accounts
    }

    Map<String, List<Document>> getAllUserAccountMapping() {

        Map<String, List<Document>> userAccountMapping = [:]
        List<Document> accounts = Account.collection.find([:], [companyName: 1, mainUserIds: 1, userIds: 1])?.toList()

        accounts?.each { account ->
            account.userIds?.each { String userId ->
                def existing = userAccountMapping.get(userId) ?: []
                existing.add(account)
                userAccountMapping.put((userId), existing)
            }

            account.mainUserIds?.each { String userId ->
                def existing = userAccountMapping.get(userId) ?: []
                existing.add(account)
                userAccountMapping.put((userId), existing)
            }
        }

        return userAccountMapping
    }

    Account getAccountByLoginKey(String loginKey) {
        return getAccounts(loginKey)
    }

    Account saveAccount(Account account) {
        if (account && !account.hasErrors()) {
            if (account.id) {
                account = account.merge(flush: true, failOnError: true)
            } else {
                account = account.save(flush: true, failOnError: true)
            }
        }
        return account
    }

    List<Account> getAccountsForForm() {
        List<Account> accounts
        def result = Account.collection.find([:], [companyName: 1, licenseIds: 1, userIds: 1, mainUserIds: 1])

        if (result) {
            accounts = result.collect({ it as Account })
        }
        return accounts
    }

    List<Account> getManagedOrBasicAccountsForUser(User user, Map projection = null) {
        List<Account> accounts

        if (user) {
            def result

            if (projection) {
                result = Account.collection.findOne([mainUserIds: user.id.toString()], projection)
                if(!result){
                    result = Account.collection.findOne([userIds: user.id.toString()], projection)
                }
            } else {
                result = Account.collection.findOne([mainUserIds: user.id.toString()], [companyName : 1,licenseIds  : 1, addressLine1: 1, addressLine2: 1,
                                                                                        addressLine3: 1, email: 1, postcode: 1, town: 1, state: 1, country: 1, vatNumber: 1, catalogueSlugs: 1, showLogoInSoftware: 1, branding: 1, defaultBrandingImage: 1, defaultProductImage: 1, defaultManufacturingDiagram: 1, favoriteMaterialIdAndUserId: 1, mainUserIds: 1])
                if(!result) {
                    result = Account.collection.findOne([userIds: user.id.toString()], [companyName : 1,licenseIds  : 1, addressLine1: 1, addressLine2: 1,
                                                                                        addressLine3: 1, email: 1, postcode: 1, town: 1, state: 1, country: 1, vatNumber: 1, catalogueSlugs: 1, showLogoInSoftware: 1, branding: 1, defaultBrandingImage: 1, defaultProductImage: 1, defaultManufacturingDiagram: 1, favoriteMaterialIdAndUserId: 1, mainUserIds: 1])
                }
            }
            if (result) {
                accounts = [result as Account]
            }
        }
        return accounts
    }

    List<Account> getManagedAccountsForUser(User user, Map projection = null) {
        List<Account> accounts

        if (user) {
            def result

            if (projection) {
                result = Account.collection.findOne([mainUserIds: user.id.toString()], projection)
            } else {
                result = Account.collection.findOne([mainUserIds: user.id.toString()], [companyName : 1,
                                                                                        licenseIds  : 1, addressLine1: 1, addressLine2: 1,
                                                                                        addressLine3: 1, email: 1, postcode: 1, town: 1, state: 1, country: 1, vatNumber: 1, catalogueSlugs: 1, showLogoInSoftware: 1, branding: 1, favoriteMaterialIdAndUserId: 1, mainUserIds: 1])
            }
            if (result) {
                accounts = [result as Account]
            }
        }
        return accounts
    }


    User addMainUser(String userId, String accountId) {
        User user

        if (userId && accountId) {
            def result = User.collection.findOne(["_id": DomainObjectUtil.stringToObjectId(userId)])

            if (result) {
                Account account = getAccount(accountId)

                if (account && !account.mainUserIds?.contains(userId)) {
                    if (account.mainUserIds) {
                        account.mainUserIds.add(userId)
                    } else {
                        account.mainUserIds = [userId]
                    }
                    account.merge(flush: true)
                    user = result as User
                }
            }
        }
        return user
    }

    User addUser(String userId, String accountId) {
        User user

        if (userId && accountId) {
            def result = User.collection.findOne(["_id": DomainObjectUtil.stringToObjectId(userId)])

            if (result) {
                Account account = getAccount(accountId)

                if (account && !account.userIds?.contains(userId)) {
                    if (account.userIds) {
                        account.userIds.add(userId)
                    } else {
                        account.userIds = [userId]
                    }
                    account.merge(flush: true)
                    user = result as User
                }
            }
        }
        return user
    }

    License addLicense(String licenseId, String accountId) {
        License license

        if (licenseId && accountId) {
            def result = License.collection.findOne(["_id": DomainObjectUtil.stringToObjectId(licenseId)])

            if (result) {
                Account account = getAccount(accountId)

                if (account && !account.licenseIds?.contains(licenseId)) {
                    if (account.licenseIds) {
                        account.licenseIds.add(licenseId)
                    } else {
                        account.licenseIds = [licenseId]
                    }
                    account.merge(flush: true)
                    license = result as License
                }
            }
        }
        return license
    }

    List<Account> getBasicAccountsForUser(User user, Map projection = null) {
        List<Account> accounts

        if (user) {
            def result

            if (projection) {
                result = Account.collection.findOne([userIds: user.id.toString()], projection)
            } else {
                result = Account.collection.findOne([userIds: user.id.toString()], [companyName : 1,
                                                                                    licenseIds  : 1, addressLine1: 1, addressLine2: 1,
                                                                                    addressLine3: 1, email: 1, postcode: 1, town: 1, state: 1, country: 1, vatNumber: 1, catalogueSlugs: 1, showLogoInSoftware: 1, branding: 1, favoriteMaterialIdAndUserId: 1])
            }

            if (result) {
                accounts = [result as Account]
            }
        }
        return accounts
    }

    List<Account> getAccountsForLicense(String licenseId) {
        List<Account> accounts

        if (licenseId) {
            def result = Account.collection.find(["licenseIds": ["\$in": [licenseId]]])

            if (result) {
                accounts = result.collect({ it as Account })
            }
        }
        return accounts
    }

    @Secured(["ROLE_AUTHENTICATED"])
    Account delete(Account account) {
        try {

            if (account) {
                account
                getUsers(account.userIds)?.each { User user ->
                    if (user) {
                        if (account.userIds.contains(user.id)) {
                            account.userIds.remove(user?.id)

                        }
                        if (account?.mainUserIds?.contains(user.id))
                            account.mainUserIds.remove(user?.id)
                    }
                }
                account = account.delete(flush: true)
            }
        } catch (Exception e) {
            loggerUtil.error(log, "Error in deleting account ${account?.companyName}: ${e}")
            flashService.setErrorAlert("Error in deleting account ${account?.companyName} : ${e.getMessage()}", true)
        }
        return account
    }

    List<Account> getAccountsByEmailSuffix(String email) {
        List<Account> accounts
        email = email?.substring(email?.indexOf("@"))

        if (email) {
            BasicDBObject query = new BasicDBObject()
            query.put("emailForm", email)
            accounts = Account.collection.find([query])?.collect({ it as Account })
        }
        return accounts
    }

    boolean addProjectTemplateIdToAccount(String accountId, String templateId) {
        boolean saveOk = false

        try {
            if (accountId) {
                Account account = Account.findById(DomainObjectUtil.stringToObjectId(accountId))
                if (account) {
                    if (!account.projectTemplateIds) {
                        account.projectTemplateIds = new ArrayList<String>()
                    }
                    account.projectTemplateIds.add(templateId)
                    saveOk = account.save(flush: true, failOnError: true) ? true : false
                }
            }
        } catch (e) {
            flashService.setErrorAlert("Error in adding project template to account: ${e.getMessage()}", true)
            loggerUtil.error(log, "Error in adding project template to account", e)
        }

        return saveOk
    }

    Boolean removeProjectTemplateIdFromAccount(String accountId, String templateId) {
        boolean removeOk = false

        try {
            if (accountId) {
                Account account = Account.findById(DomainObjectUtil.stringToObjectId(accountId))
                if (account && account.projectTemplateIds) {
                    account.projectTemplateIds.remove(templateId)
                    removeOk = account.save(flush: true, failOnError: true) ? true : false
                }
            }
        } catch (e) {
            flashService.setErrorAlert("Error in removing project template from account: ${e.getMessage()}", true)
            loggerUtil.error(log, "Error in removing project template from account", e)
        }

        return removeOk
    }

    /**
     * We have issue that the number cannot be parsed in some languages. Hence we parse it manually.
     * This is a quick fix for release.
     * Probably should change this to a more global solution if this happens again multiple time in other domain classes.
     * @param params
     */
    void parseNumberInParamsForAccount(GrailsParameterMap params) {
        try {
           if (params.carbonCost) {
               params.carbonCost = params.double('carbonCost')
           }
        } catch (e) {
            flashService.setErrorAlert("Error in parseNumberInParams from accountService: ${e.getMessage()}", true)
            loggerUtil.error(log, "Error in parseNumberInParams from accountService", e)
        }
    }

    List<License> getLicenses(List<String> licenseIds) {
        if (licenseIds) {
            licenseService.getLicensesByIds(licenseIds)?.sort({ it.name.toLowerCase() })
        } else {
            return null
        }
    }

    List<License> getMainLicenses(User user, List<String> licenseIds) {
        def ids = []
        if (user) {
            if (user.managedLicenseIds) {
                ids.addAll(user.managedLicenseIds)
            }

            if (user.usableLicenseIds) {
                ids.addAll(user.usableLicenseIds)
            }
        }

        if (licenseIds) {
            ids.addAll(licenseIds)
        }
        return licenseService.getLicensesByIds(ids, false)
    }

    List<Document> getMainLicensesAsJSON(User user, List<String> licenseIds) {
        def ids = []
        if (user) {
            if (user.managedLicenseIds) {
                ids.addAll(user.managedLicenseIds)
            }

            if (user.usableLicenseIds) {
                ids.addAll(user.usableLicenseIds)
            }
        }

        if (licenseIds) {
            ids.addAll(licenseIds)
        }
        return licenseService.getLicensesAsJSONByIds(ids, false)
    }

    List<User> getUsers(List<String> userIds) {
        if (userIds) {
            userService.getUsersByIds(userIds)?.sort({ it.username.toLowerCase() })
        } else {
            return null
        }
    }

    List<User> getMainUsers(List<String> mainUserIds) {
        if (mainUserIds) {
            userService.getUsersByIds(mainUserIds)?.sort({ it.username.toLowerCase() })
        } else {
            return null
        }
    }

    List<User> getRequestToJoinUsers(List<String> requestToJoinUserIds) {
        if (requestToJoinUserIds) {
            userService.getUsersByIds(requestToJoinUserIds)?.sort({ it.username.toLowerCase() })
        } else {
            return null
        }

    }

    List<AccountCatalogue> getCatalogues(List<String> catalogueSlugs) {
        List<AccountCatalogue> catalogues

        if (catalogueSlugs) {
            catalogues = AccountCatalogue.collection.find(["slug": ["\$in": catalogueSlugs]])?.collect({
                it as AccountCatalogue
            })
        }
        return catalogues
    }

    String getCatalogueSlugsForForm(List<String> catalogueSlugs) {
        String slugs

        if (catalogueSlugs) {
            catalogueSlugs.each {
                slugs = slugs ? slugs + ",${it}" : it
            }
        }
        return slugs
    }

    boolean getEcommerceValidates(Account account) {
        boolean validates = false

        if (account) {
            if (account.companyName && account.addressLine1 && account.country) {
                validates = true
            }
        }
        return validates
    }

    List<ProjectTemplate> getProjectTemplates(List<String> projectTemplateIds) {
        List<ProjectTemplate> templates = []
        try {
            if (projectTemplateIds) {
                BasicDBObject query = new BasicDBObject()
                query.put("_id", [$in: projectTemplateIds.collect({new ObjectId(it)})])
                query.put("deleted", false)
                templates = ProjectTemplate.collection.find(query).toList().collect({it as ProjectTemplate})
            }
        } catch (e) {
            loggerUtil.error(log, "Error occurred in getProjectTemplates", e)
            flashService.setErrorAlert("Error occurred in getting the project templates: ${e.getMessage()}", true)
        }

        return templates
    }
}
