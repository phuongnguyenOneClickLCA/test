package com.bionova.optimi.grails

import groovy.transform.CompileStatic
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.grails.core.util.StopWatch

import javax.servlet.*
import javax.servlet.http.HttpServletResponse

import static grails.web.mime.MimeType.HTML

class TrimWhitespacesFilter implements Filter {
    private static Log log = LogFactory.getLog(TrimWhitespacesFilter.class)

    @Override
    @CompileStatic
    void doFilter(ServletRequest request, ServletResponse response,
                  FilterChain filterChain) throws IOException, ServletException {
        HtmlResponseWrapper responseWrapper = new HtmlResponseWrapper(
                (HttpServletResponse) response)
        filterChain.doFilter(request, responseWrapper)
        if (response.getContentType()?.contains(HTML.name)) {
            StopWatch watch = new StopWatch()
            watch.start("HTML whitespace filter")

            String content = responseWrapper.getCaptureAsString()
            String replacedContent = content.replaceAll("\\s{2,}<", " <")
            replacedContent = replacedContent.replaceAll("\n{2,}", "\n")
            response.getWriter().write(replacedContent)

            if(log.isTraceEnabled()) {
                if(content && replacedContent) {
                    log.trace("Shrincked from ${content.size()} to ${content.size()}")
                }
            }
            watch.stop()
            watch.complete()
            log.debug(watch.toString())
        }
    }

    @Override
    void init(FilterConfig arg0) throws ServletException {}

    @Override
    void destroy() {}
}
