package com.bionova.optimi.util


import com.bionova.optimi.core.util.LoggerUtil
import com.bionova.optimi.grails.OptimiMessageSource
import groovy.transform.CompileStatic
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest
import org.springframework.web.multipart.support.StandardServletMultipartResolver

import javax.servlet.http.HttpServletRequest

/**
 * @author Pasi-Markus Mäkelä
 */
class OptimiMultipartResolver extends StandardServletMultipartResolver {

    ErrorMessageUtil errorMessageUtil
    OptimiMessageSource messageSource
    LoggerUtil loggerUtil
    Log log = LogFactory.getLog(OptimiMultipartResolver.class)
    private static final Long maxUploadSize = 40 * 1024 * 1024

    @CompileStatic
    MultipartHttpServletRequest resolveMultipart(HttpServletRequest request) {
        MultipartHttpServletRequest multipartHttpServletRequest

        try {
            multipartHttpServletRequest = super.resolveMultipart(request)

            if (multipartHttpServletRequest) {
                Map<String, MultipartFile> fileMap = multipartHttpServletRequest.getFileMap()

                if (fileMap) {
                    for (String fileName in fileMap.keySet()) {
                        MultipartFile multipartFile = multipartHttpServletRequest.getFile(fileName)

                        if (multipartFile.size > maxUploadSize) {
                            throw new SizeLimitExceededException("", multipartFile.size, maxUploadSize.longValue())
                        }
                    }
                }
            }
            return multipartHttpServletRequest
        } catch (SizeLimitExceededException e) {
            loggerUtil.warn(log, e.getMessage())
            errorMessageUtil.setErrorMessage(messageSource.getMessage("sizeLimitExceededException",
                    [(e.getPermittedSize() / (1024 * 1024)).toString()].toArray(),
                    "File max size " + (e.getPermittedSize() / (1024 * 1024)).toString() + "MB exceeded",
                    LocaleContextHolder.getLocale()))
            return new DefaultMultipartHttpServletRequest(request, new LinkedMultiValueMap<String, MultipartFile>(),
                    multipartHttpServletRequest?.getParameterMap(), new LinkedHashMap<String, String>());
        } catch (Exception e) {
            loggerUtil.warn(log, e.getMessage())
            errorMessageUtil.setErrorMessage("Error in uploading file. Perhaps you pushed stop from browser or closed the browser.")
            return new DefaultMultipartHttpServletRequest(request, new LinkedMultiValueMap<String, MultipartFile>(),
                    multipartHttpServletRequest?.getParameterMap(), new LinkedHashMap<String, String>());
        }
    }
}
