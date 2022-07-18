package com.bionova.optimi.grails

import com.bionova.optimi.core.util.LoggerUtil
import org.grails.web.i18n.ParamsAwareLocaleChangeInterceptor
import org.grails.web.servlet.mvc.GrailsWebRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.propertyeditors.LocaleEditor
import org.springframework.web.servlet.DispatcherServlet
import org.springframework.web.servlet.support.RequestContextUtils

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author Pasi-Markus Mäkelä
 */
class OptimiParamsAwareLocaleChangeInterceptor extends ParamsAwareLocaleChangeInterceptor {

    LoggerUtil loggerUtil
    private static final Logger log = LoggerFactory.getLogger(OptimiParamsAwareLocaleChangeInterceptor)


    @Override
    boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        GrailsWebRequest webRequest = GrailsWebRequest.lookup(request)
        def params = webRequest.params
        def localeParam = params?.get(paramName)

        if (!localeParam) {
            return super.preHandle(request, response, handler)
        }

        try {
            // choose first if multiple specified
            if (localeParam.getClass().isArray()) {
                localeParam = ((Object[]) localeParam)[0]
            }
            def localeResolver = RequestContextUtils.getLocaleResolver(request)
            if (localeResolver == null) {
                localeResolver = this.localeResolver
                request.setAttribute(DispatcherServlet.LOCALE_RESOLVER_ATTRIBUTE, localeResolver)
            }
            def localeEditor = new LocaleEditor()
            localeEditor.setAsText localeParam?.toString()
            localeResolver?.setLocale request, response, (Locale) localeEditor.value
            return true
        } catch (Exception e) {
            loggerUtil.warn(log, "Error intercepting locale change: ${e.message}", e)
            return true
        }
    }
}

