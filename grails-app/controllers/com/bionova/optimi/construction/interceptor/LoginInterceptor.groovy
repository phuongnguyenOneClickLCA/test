package com.bionova.optimi.construction.interceptor

import com.bionova.optimi.construction.Constants
import com.bionova.optimi.core.domain.mongo.User

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession
import java.text.DateFormat
import java.text.SimpleDateFormat

class LoginInterceptor {

    def channelFeatureService
    def userService
    def loggerUtil
    def configurationService
    def localeResolverUtil
    def messageSource

    LoginInterceptor() {
        matchAll().excludes(controller: 'index', action: 'index').excludes(controller:'error')
    }

    boolean before() {
        def returnable = true

        if (session != null) {
            User user = userService.getCurrentUser()

            if (user) {
                if (user.getLoginCount() <= 1 && !session?.getAttribute("noPrompt")) {
                    session["showLoginPrompt"] = true
                } else {
                    session["showLoginPrompt"] = false
                }

                if (user.activationDeadline && !session["removeConfirmationPrompts"]) {

                    if (user.activationDeadline && user.activationDeadline <= new Date() && !request.forwardURI.contains("nonValidatedAccountHandler") && !request.forwardURI.contains("logout") && !request.forwardURI.contains("reactivateinside")) {
                        session.setAttribute("nonactiveAccount", "true")
                        redirect controller:'index', action:'nonValidatedAccountHandler'
                        returnable = false
                    }

                    if (!session["nonactiveAccount"]) {
                        Date userActivation = user.activationDeadline
                        session.setAttribute("username", "${user.username}")
                        String link = "/app/reactivateinside"
                        Locale locale = getLocale()
                        Object[] args = [userActivation.format("HH:mm - dd.MM.yyyy")?.toString(),link]
                        flash.userActivationError = getLocaleMessage("user_activation_warning", args, locale)
                    }
                }
            } else {
                // Kill sessions that did not log in faster, 300 seconds
                session.setMaxInactiveInterval(300)
            }
        }

        try {
            handleChannelFeature(session, request)
            handleApplicationParams(session)
        } catch (Exception e) {
            loggerUtil.warn(log, "Error in setting channelFeature to session", e)
        }
        return returnable
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }

    private static Object fromString(String serializedObject) {
        try {
            byte[] b = serializedObject.getBytes();
            ByteArrayInputStream bi = new ByteArrayInputStream(b);
            ObjectInputStream si = new ObjectInputStream(bi);
            User user = (User) si.readObject();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static String toString(Serializable object) throws IOException {
        String serializedObject = "";

        // serialize the object
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream so = new ObjectOutputStream(bo);
            so.writeObject(object);
            so.flush();
            serializedObject = bo.toString();
        } catch (Exception e) {
            System.out.println(e);
        }
        return serializedObject
    }

    private void handleChannelFeature(HttpSession session, HttpServletRequest request) {
        if (session) {
            def channelFeature = session?.getAttribute(Constants.SessionAttribute.CHANNEL_FEATURE.toString())
            def channelToken = session[Constants.SessionAttribute.CHANNEL_TOKEN.toString()]

            if (!channelToken) {
                channelToken = request.getParameter(Constants.SessionAttribute.CHANNEL_TOKEN.toString())
            }
            if (!channelFeature || (channelToken && channelFeature && !channelToken.equals(channelFeature.token))) {
                channelFeature = channelFeatureService.getChannelFeatureByToken(channelToken)
                session.setAttribute(Constants.SessionAttribute.CHANNEL_FEATURE.toString(), channelFeature)
            }
        }
    }
    private void handleApplicationParams(HttpSession session) {
        if (session) {
            String termsAndConditionsUrl = session?.getAttribute(Constants.ConfigName.TERMS_CONDITIONS_URL.toString())
            if (!termsAndConditionsUrl) {
                termsAndConditionsUrl = configurationService.getConfigurationValue(Constants.APPLICATION_ID, "termsAndConditionsUrl")
                session.setAttribute(Constants.ConfigName.TERMS_CONDITIONS_URL.toString(), termsAndConditionsUrl)
            }

        }
    }

    private Locale getLocale() {
        return localeResolverUtil.resolveLocale(session, request, response)
    }

    private getLocaleMessage(String key, Object[] args = null,  Locale locale) {
        String value = key
        try {
            value = messageSource.getMessage(value, args, locale)
        } catch (Exception e) {
            log.error("${e}")
        }
        return value
    }
}
