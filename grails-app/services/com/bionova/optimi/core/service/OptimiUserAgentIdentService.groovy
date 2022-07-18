package com.bionova.optimi.core.service

import com.bionova.optimi.core.util.LoggerUtil
import org.apache.commons.lang3.StringUtils
import org.geeks.browserdetection.ComparisonType
import org.geeks.browserdetection.VersionHelper
import org.grails.web.servlet.mvc.GrailsWebRequest
import org.grails.web.util.WebUtils

import ua_parser.Parser
import ua_parser.Client
import ua_parser.UserAgent
import ua_parser.OS

import javax.annotation.PostConstruct
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author Pasi-Markus Mäkelä
 */
class OptimiUserAgentIdentService {
    private static final String DOT = "."
    private static final String UNKNOWN = "Unknown"

    LoggerUtil loggerUtil
    FlashService flashService
    Parser parser

    @PostConstruct
    private void init() {
        this.parser = new Parser()
    }

    String getBrowserVersion() {
        try {
            UserAgent userAgent = getClient().userAgent

            return [userAgent.major, userAgent.minor, userAgent.patch].grep().join(DOT)
        } catch (Exception e) {
            loggerUtil.warn(log, "Could not resolve user agent. Perhaps caused by IE10: ", e)
            flashService.setErrorAlert("Could not resolve user agent. Perhaps caused by IE10: ${e.getMessage()}", true)

            return UNKNOWN
        }
    }

    public static final String CHROME = "chrome"
    public static final String FIREFOX = "firefox"
    public static final String SAFARI = "safari"
    public static final String OTHER = "other"
    public static final String OPERA = "opera"
    public static final String IE = "ie"
    public static final String EDGE = "edge"

    /**
     * Returns user-agent header value from thread-bound RequestContextHolder
     */
    String getUserAgentString() {
        getUserAgentString request
    }

    /**
     * Returns user-agent header value from the passed request
     */
    String getUserAgentString(request) {
        request.getHeader("user-agent")
    }

    private Client getClient() {
        String userAgentString = getUserAgentString() ?: StringUtils.EMPTY

        // fallback for users without user-agent header
        if (userAgentString == null) {
            log.warn "User agent header is not set."
            userAgentString = StringUtils.EMPTY
        }

        return parser.parse(userAgentString)
    }

    List<String> getLocales() {
        Enumeration<Locale> locales = getRequest().getLocales()
        List<String> localeStrings = []

        while (locales && locales.hasMoreElements()) {
            localeStrings.add(locales.nextElement().toString())
        }
        return localeStrings
    }

    String getAcceptedLanguages() {
        return getRequest().getHeader("Accept-Language")
    }

    Cookie[] getCookies() {
        return request.getCookies()
    }

    Integer getNumberOfCookies() {
        Integer numOfCookies = getCookies()?.size()
        return numOfCookies != null ? numOfCookies : 0
    }

    List<Cookie> getCurrentCookies() {
        List<Cookie> currentCookies = []

        getCookies()?.each { Cookie cookie ->
            currentCookies.add(cookie)
        }
        return currentCookies
    }

    boolean isChrome(ComparisonType comparisonType = null, String version = null) {
        isBrowser(CHROME, comparisonType, version)
    }

    boolean isFirefox(ComparisonType comparisonType = null, String version = null) {
        isBrowser(FIREFOX, comparisonType, version)
    }

    boolean isMsie(ComparisonType comparisonType = null, String version = null) {
        // why people use it?
        isBrowser(IE, comparisonType, version)
    }

    boolean isEdge(ComparisonType comparisonType = null, String version = null) {
        isBrowser(EDGE, comparisonType, version)
    }

    boolean isSafari(ComparisonType comparisonType = null, String version = null) {
        isBrowser(SAFARI, comparisonType, version)
    }

    boolean isOpera(ComparisonType comparisonType = null, String version = null) {
        isBrowser(OPERA, comparisonType, version)
    }

    /**
     * Returns true if browser is unknown
     */
    boolean isOther() {
        isBrowser(OTHER)
    }

    private boolean isBrowser(String browserForChecking, ComparisonType comparisonType = null, String versionForChecking = null) {
        UserAgent userAgent = getClient()?.userAgent

        // browser checking
        if (!userAgent?.family?.equalsIgnoreCase(browserForChecking)) {
            return false
        }

        // version checking
        if (versionForChecking) {
            if (!comparisonType) {
                throw new IllegalArgumentException("comparisonType must be specified")
            }

            String version = [userAgent.major, userAgent.minor, userAgent.patch].grep().join(DOT)

            if (version) {
                if (comparisonType == ComparisonType.EQUAL) {
                    return VersionHelper.equals(version, versionForChecking)
                }

                int compRes = VersionHelper.compare(version, versionForChecking)

                if (compRes == 1 && comparisonType == ComparisonType.GREATER) {
                    return true
                }

                if (compRes == -1 && comparisonType == ComparisonType.LOWER) {
                    return true
                }
            }

            return false
        }

        return true
    }

    /**
     * Returns the browser name.
     */
    String getBrowser() {
        getClient()?.userAgent?.family
    }

    String getOperatingSystem() {
        OS os = getClient()?.os

        if (os) {
            return os.family + (os.major ? (" " + os.major) : StringUtils.EMPTY)
        } else {
            return null
        }
    }

    private HttpServletRequest getRequest() {
        GrailsWebRequest webRequest = WebUtils.retrieveGrailsWebRequest()
        return webRequest.getNativeRequest(HttpServletRequest.class)
    }

    private HttpServletResponse getSession() {
        getRequest().getSession()
    }
}
