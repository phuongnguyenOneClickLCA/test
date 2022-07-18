package com.bionova.optimi.grails

import com.bionova.optimi.construction.Constants
import com.bionova.optimi.core.service.ChannelFeatureService
import grails.util.Environment
import grails.util.Holders
import grails.web.servlet.mvc.GrailsHttpSession
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.grails.web.servlet.mvc.GrailsWebRequest
import org.grails.web.util.WebUtils
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.lang.Nullable
import org.springframework.util.ObjectUtils
import org.springframework.context.NoSuchMessageException
import org.springframework.context.MessageSourceResolvable

import java.text.MessageFormat

/**
 * @author Pasi-Markus Mäkelä
 */
class OptimiMessageSource extends ReloadableResourceBundleMessageSource {

    private static Log log = LogFactory.getLog(OptimiMessageSource.class)
    private static
    final List ignoredMissingCodes = ["AbstractAccessDecisionManager.accessDenied", "AbstractUserDetailsAuthenticationProvider.onlySupports"]

    OptimiMessageSource() {
        super()
        setAlwaysUseMessageFormat(true)
        String[] baseNames = ["file:" + System.getProperty("user.home") + "/localization/messages", "file:" + System.getProperty("user.home") + "/localization/admin"] as String[]
        setBasenames(baseNames)
        setDefaultEncoding("UTF-8")
        setCacheSeconds(1)
    }

    private static ChannelFeatureService getChannelFeatureService() {
        return Holders.getApplicationContext().getBean("channelFeatureService")
    }

    @Override
    protected Object[] resolveArguments(Object[] args, Locale locale) {
        Object[] arguments = super.resolveArguments(args, locale)

        if (arguments != null && !arguments.toList().isEmpty()) {
            def returnable = []

            arguments.each {
                if (it == null) {
                    returnable.add("")
                } else {
                    returnable.add(it)
                }
            }
            return returnable.toArray()
        }
        return arguments
    }

    @Override
    protected String getMessageInternal(String code, Object[] args, Locale locale) {
        String message
        String originalMessage = super.getMessageInternal(code, args, locale)
        originalMessage == null ? "" : originalMessage

        if (code) {
            // String errors
            String channelToken = getChannelTokenFromSession()
            boolean foundFromChannel = false
            if (channelToken) {
                String tokenAndCode = channelToken + "." + code
                message = super.getMessageInternal(tokenAndCode, args, locale)

                if (message) {
                    foundFromChannel = true
                }
            }
            if (originalMessage == null && message == null && !ignoredMissingCodes.contains(code) && !foundFromChannel &&
                    !code.contains(".nullable") && !code.contains("admin.") &&
                    !code.contains("com.bionova.optimi.core.domain.mongo") && !code.contains("number.format")) {
                message = ""
                if (Environment.current == Environment.DEVELOPMENT) {
                    String errors = "Missing message for locale ${locale?.getLanguage()} and code ${code}. It's not found by any channelToken prefix either."
                    log.trace(errors)
                }
            }
        }

        if (originalMessage == null) {
            Locale eng = new Locale("en")
            originalMessage = super.getMessageInternal(code, args, eng)
        }

        return message ? message : originalMessage
    }

    final String getMessageSilently(String code, @Nullable Object[] args, @Nullable String defaultMessage, Locale locale) {
        String msg = super.getMessageInternal(code, args, locale)
        if (msg != null) {
            return msg
        }
        if (defaultMessage == null) {
            return getDefaultMessage(code)
        }
        return renderDefaultMessage(defaultMessage, args, locale)
    }

    final String getMessageSilently(String code, @Nullable Object[] args, Locale locale) throws NoSuchMessageException {
        String msg = super.getMessageInternal(code, args, locale)
        if (msg != null) {
            return msg
        }
        String fallback = getDefaultMessage(code)
        if (fallback != null) {
            return fallback
        }
        throw new NoSuchMessageException(code, locale)
    }

    final String getMessageSilently(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
        String[] codes = resolvable.getCodes()
        if (codes != null) {
            for (String code : codes) {
                String message = super.getMessageInternal(code, resolvable.getArguments(), locale)
                if (message != null) {
                    return message
                }
            }
        }
        String defaultMessage = getDefaultMessage(resolvable, locale)
        if (defaultMessage != null) {
            return defaultMessage
        }
        throw new NoSuchMessageException(!ObjectUtils.isEmpty(codes) ? codes[codes.length - 1] : "", locale)
    }

    @Override
    protected String resolveCodeWithoutArguments(String code, Locale locale) {
        String channelToken = getChannelTokenFromSession()
        String message

        if (channelToken) {
            String tokenAndCode = channelToken + "." + code
            message = super.resolveCodeWithoutArguments(tokenAndCode, locale)
        }

        /*
        if (!message) {
            List<String> allChannelTokens = getChannelFeatureService().getAllChannelTokens()

            if (allChannelTokens && allChannelTokens.size() > 1) {
                if (channelToken) {
                    allChannelTokens = allChannelTokens.remove(channelToken)
                }

                for (String token in allChannelTokens) {
                    String tokenAndCode = token + "." + code

                    if (super.resolveCodeWithoutArguments(tokenAndCode, locale) || super.resolveCodeWithoutArguments(tokenAndCode, new Locale("fi"))) {
                        message = ""
                        break
                    }
                }
            }
        } */

        if (message == null) {
            message = super.resolveCodeWithoutArguments(code, locale)
        }
        return message
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        MessageFormat messageFormat
        String channelToken = getChannelTokenFromSession()

        if (channelToken) {
            String tokenAndCode = channelToken + "." + code
            messageFormat = super.resolveCode(tokenAndCode, locale)
        }

        if (!messageFormat) {
            messageFormat = super.resolveCode(code, locale)
        }
        return messageFormat
    }

    private String getChannelTokenFromSession() {
        String channelToken

        try {
            GrailsWebRequest webRequest = WebUtils.retrieveGrailsWebRequest()
            GrailsHttpSession session = webRequest?.getSession()

            if (session) {
                channelToken = session?.getAttribute(Constants.SessionAttribute.CHANNEL_TOKEN.toString())
            }
        } catch (Exception e) {
            // Do nothing, session probably invalidated
        }
        return channelToken
    }
}
