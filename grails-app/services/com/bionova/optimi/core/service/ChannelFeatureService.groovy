package com.bionova.optimi.core.service

import com.bionova.optimi.configuration.DomainConfiguration
import com.bionova.optimi.construction.Constants
import com.bionova.optimi.core.domain.mongo.ChannelFeature
import com.bionova.optimi.core.domain.mongo.Portfolio
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.util.DomainObjectUtil
import grails.plugin.cache.CacheEvict
import grails.plugin.cache.Cacheable
import grails.plugin.springsecurity.annotation.Secured
import org.springframework.beans.factory.annotation.Autowired

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

/**
 * @author Pasi-Markus Mäkelä
 */
class ChannelFeatureService {

    /**
     * A flag defining if the channel feature login URLs have already been updated.
     * It is there to prevent the update logic to be executed more than once after the start of the application.
     */
    static boolean channelFeatureLoginUrlsUpdated = false

    def userService
    def optimiResourceService
    def configurationService

    @Autowired
    DomainConfiguration domainConfiguration

    ChannelFeature getChannelFeature(HttpSession session) {
        ChannelFeature channelFeature

        if (session?.getAttribute(Constants.SessionAttribute.CHANNEL_FEATURE.toString())) {
            channelFeature = (ChannelFeature) session?.getAttribute(Constants.SessionAttribute.CHANNEL_FEATURE.toString())
        }

        if (!channelFeature) {
            channelFeature = getDefaultChannel()
        }
        return channelFeature
    }

    boolean applyChannelFeature(HttpSession session, String channelToken) {
        boolean applyOk = false

        if (session && channelToken) {
            ChannelFeature channelFeature = getChannelFeatureByToken(channelToken)

            if (channelFeature) {
                session.setAttribute(Constants.SessionAttribute.CHANNEL_TOKEN.toString(), channelToken)
                session.setAttribute(Constants.SessionAttribute.CHANNEL_FEATURE.toString(), channelFeature)
                applyOk = true
            }
        }
        return applyOk
    }

    @CacheEvict("channelFeature")
    ChannelFeature setChannelAsDefault(ChannelFeature channelFeature) {
        if (channelFeature) {
            ChannelFeature defaultChannel = ChannelFeature.findByDefaultChannel(Boolean.TRUE)

            if (defaultChannel && !defaultChannel.equals(channelFeature)) {
                defaultChannel.defaultChannel = Boolean.FALSE
                defaultChannel.merge(flush: true)
            }
            channelFeature.defaultChannel = Boolean.TRUE
            channelFeature = channelFeature.merge(flush: true)

            List<ChannelFeature> otherDefaultChannels = ChannelFeature.
                    findAllByDefaultChannelAndNameNotEqual(Boolean.TRUE, channelFeature.name)

            if (otherDefaultChannels) {
                otherDefaultChannels.each {
                    it.defaultChannel = null
                    it.merge(flush: true)
                }
            }
        }
        return channelFeature
    }

    @Cacheable("channelFeature")
    ChannelFeature getDefaultChannel() {
        return ChannelFeature.findByDefaultChannel(Boolean.TRUE)
    }

    ChannelFeature getChannelFeatureById(String id) {
        ChannelFeature channelFeature

        if (id) {
            channelFeature = ChannelFeature.get(id)
        }
        return channelFeature
    }

    ChannelFeature getChannelFeatureByToken(String token) {
        ChannelFeature channelFeature

        if (token) {
            channelFeature = ChannelFeature.findByToken(token)
        } else {
            try {
                channelFeature = ChannelFeature.findByDefaultChannel(true)
            } catch (e) {
                e.printStackTrace()
            }
        }
        return channelFeature
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    List<ChannelFeature> getAllChannelFeatures() {
        List<ChannelFeature> channelFeatures = ChannelFeature.list()
        return channelFeatures
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    ChannelFeature saveChannelFeature(ChannelFeature channelFeature) {
        if (channelFeature && !channelFeature.hasErrors()) {
            User user = userService.getCurrentUser(Boolean.TRUE)

            if (channelFeature.id) {
                channelFeature.lastUpdaterId = user?.id?.toString()
                channelFeature = channelFeature.merge()
            } else {
                channelFeature.creatorId = user?.id?.toString()
                channelFeature.lastUpdaterId = user?.id?.toString()
                channelFeature = channelFeature.save()
            }

            if (channelFeature.defaultChannel) {
                List<ChannelFeature> otherDefaultChannels = ChannelFeature.
                        findAllByDefaultChannelAndNameNotEqual(Boolean.TRUE, channelFeature.name)

                if (otherDefaultChannels) {
                    otherDefaultChannels.each {
                        it.defaultChannel = null
                        it.merge(flush: true)
                    }
                }
            }
        }
        return channelFeature
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    void removeChannelFeature(id) {
        ChannelFeature channelFeature = getChannelFeatureById(id)

        if (channelFeature) {
            channelFeature.delete()
        }
    }

    /**
     * Process the loginUrl from db so that it keeps the request server domain name
     * @param loginUrl
     * @param request
     * @return
     */
    String processLoginUrl(String loginUrl, HttpServletRequest request) {
        if (loginUrl && request){
            String serverName = request.getServerName()
            String serverUrl = request.getScheme() + '://' + serverName

            if (System.getProperty("islocalhost") || serverName?.contains('localhost')) {
                serverUrl += ':' + com.bionova.optimi.core.Constants.PORT
            }

            if (loginUrl.contains('http') || loginUrl.contains('https')) {
                //if loginUrl is still the full link >> we replace the domain name in link with the request domain
                // replace http://something.com and https://something.com with the serverUrl
                loginUrl = loginUrl.replaceAll(/^http\w?:\/\/.*\.com/, serverUrl)
            } else {
                // otherwise it is the path, then just need to attach the serverUrl
                loginUrl = serverUrl + loginUrl
            }
        }
        return loginUrl
    }

    /**
     * Updates the login URLs of all channel features that the login URLs refer to the server the application is currently running on.
     *
     * The following logic applies:
     * - if the application is running on any PROD server -> no update happens, all login URLs remain the same (the PROD ones)
     * - if the application is running on a local machine -> only the login URL of the default channel is updated to a localhost one.
     *   The login URLs of all other features remain the same to handle the case when the application running locally is connected to
     *   one of the remote databases (TEST, DEV, CONFIG, etc).
     * - if the application is running on any remote machine other than PROD (which is TEST, DEV, CONFIG, etc) -> the login URLs are
     *   replaced with the domain name of the server the application is currently running on
     *
     * For determining which server is a PROD one and which is not the logic refers to the application configuration:
     *   application.yml -> onceclicklca -> domain
     *
     * @param channelFeatures a list of channel features to be updated
     */
    void updateAllChannelFeatureLoginUrls(List<ChannelFeature> channelFeatures, HttpServletRequest request) {
        if (channelFeatureLoginUrlsUpdated) {
            return
        }

        if (channelFeatures == null) {
            if (log.isWarnEnabled()) {
                log.warn("Not channel feature list passed. This is not expected!")
            }
            return
        }

        // If the current server is PROD -> no need to replace anything
        if (configurationService.serverName.contains(domainConfiguration.currentProdDomain)) {
            channelFeatureLoginUrlsUpdated = true
            return
        }

        if (System.getProperty("islocalhost")) {
            updateDefaultChannelFeaturUrlForLocalhost(channelFeatures, request.serverPort)
        } else {
            updateAllChannelFeatureLoginUrlsForOtherDomains(channelFeatures)
        }

        channelFeatureLoginUrlsUpdated = true
    }

    private void updateDefaultChannelFeaturUrlForLocalhost(List<ChannelFeature> channelFeatures, int localPort) {
        if (log.isDebugEnabled()) {
            log.debug("Updating default channel feature login URL for localhost")
        }

        ChannelFeature defaultChannelFeature = channelFeatures.find { it.defaultChannel }

        if (defaultChannelFeature == null) {
            if (log.warnEnabled) {
                log.warn("No default channel feature found. This is not expected!")
            }
            return
        }

        String defaultChannelFeatureProdDomain = domainConfiguration.prodDomains.find { defaultChannelFeature.loginUrl.contains(it) }
        if (defaultChannelFeatureProdDomain != null) {
            String portString = localPort ? ":${localPort}" : ""
            defaultChannelFeature.loginUrl = "http://localhost${portString}/app?channelToken=oneclicklca"
            defaultChannelFeature.merge(flush: true)
        }
    }

    private void updateAllChannelFeatureLoginUrlsForOtherDomains(List<ChannelFeature> channelFeatures) {
        if (log.isDebugEnabled()) {
            log.debug("Updating channel feature login URLs. The domain name of the server the application is currently running on: ${configurationService.serverName}")
        }

        String currentDomain = domainConfiguration.otherDomains.find { configurationService.serverName.contains(it) }

        if (currentDomain == null) {
            if (log.warnEnabled) {
                log.warn("It seems the application is hosted on an unknown server: ${configurationService.serverName}. The server domain must be listed in the application configuration!")
            }
            return
        }

        for (ChannelFeature channelFeature : channelFeatures) {
            String channelFeatureDomain = (domainConfiguration.prodDomains + domainConfiguration.otherDomains).find { channelFeature.loginUrl.contains(it) }
            if (channelFeatureDomain) {
                if (channelFeature.defaultChannel) {
                    channelFeature.loginUrl = "https://${currentDomain}/app?channelToken=oneclicklca"
                } else {
                    channelFeature.loginUrl = channelFeature.loginUrl.replace(channelFeatureDomain, currentDomain)
                }
            } else {
                if (log.warnEnabled) {
                    log.warn("Unknown domain within the channel feature login URL: ${channelFeature.loginUrl}")
                }
            }
        }
        ChannelFeature.withSession { session ->
            for (ChannelFeature channelFeature in channelFeatures) {
                channelFeature.save()
            }
            session.flush()
        }
    }

    /**
     * Get list of entity classes that are allowed in the channel
     * @param session
     * @param fetchFullObjects set to false if want to fetch a partial document from database
     * @return
     */
    List getEntityClassesForChannel(HttpSession session, boolean fetchFullObjects = false) {
        List parentEntityClasses = fetchFullObjects ? optimiResourceService.getParentEntityClasses() : optimiResourceService.getParentEntityClassesAsJSON()
        ChannelFeature channelFeature = getChannelFeature(session)

        if (channelFeature?.addableEntityClasses) {
            parentEntityClasses = parentEntityClasses?.findAll({channelFeature.addableEntityClasses.contains(it.resourceId) })
        }
        return parentEntityClasses
    }

    /**
     * Get the server url address for redirecting to external applications (per server)
     * As the local react application, cannot be ran on the same port, without extra setup or rules (apache).
     * A boolean check can be passed through to change the port for carbon designer
     * @param request
     * @param carbonDesigner3d
     * @return
     */
    String getServerUrl(HttpServletRequest request, Boolean isCarbonDesigner3D) {
        String serverUrl
        if (System.getProperty("islocalhost")) {
            serverUrl = "http://localhost:${isCarbonDesigner3D ? com.bionova.optimi.core.Constants.CD3DPORT : com.bionova.optimi.core.Constants.PORT}/"
        } else {
            serverUrl = "https://${request.getServerName()}/"
        }
        return serverUrl
    }

    List<Portfolio> getPortfolios(List<String> portfolioIds) {
        List<Portfolio> portfolios

        if (portfolioIds) {
            portfolios = Portfolio.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(portfolioIds)]])?.collect({it as Portfolio})
        }
        return portfolios
    }

    Boolean getHideEcommerceStatus(ChannelFeature channelFeature) {
        Boolean hide
        if (channelFeature?.token) {
            hide = getChannelFeatureByToken(channelFeature.token)?.hideEcommerce ?: false
        }
        return hide
    }
    User getCreator(String creatorId) {
        if (creatorId) {
            try {
                return userService?.getUserById(DomainObjectUtil.stringToObjectId(creatorId))
            } catch (Exception e) {
                return null
            }
        } else {
            return null
        }
    }
    User getLastUpdater(String lastUpdaterId) {
        if (lastUpdaterId) {
            try {
                return userService?.getUserById(DomainObjectUtil.stringToObjectId(lastUpdaterId))
            } catch (Exception e) {
                return null
            }
        } else {
            return null
        }
    }
}
