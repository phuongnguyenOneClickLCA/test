package com.bionova.optimi.util

import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.service.UserService
import com.bionova.optimi.core.util.SpringUtil
import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode
import org.grails.web.servlet.mvc.GrailsWebRequest
import org.grails.web.util.WebUtils

import javax.servlet.http.HttpSession
import java.text.DecimalFormat
import java.text.NumberFormat

/**
 * Created by antti on 4/13/16.
 */
@CompileStatic
class CalculateDifferenceUtil {
    @CompileStatic(TypeCheckingMode.SKIP)
    Double calculateDifference(Double originalNo, Double newNo) {
        Double difference

        if (originalNo && newNo) {
            if (originalNo > newNo) {
                difference = (originalNo - newNo) / originalNo * 100

                if (difference) {
                    difference *= -1
                }
            } else {
                difference = (newNo - originalNo) / originalNo * 100
            }
        }
        return difference
    }

    public DecimalFormat getDecimalFormatForDifference() {
        DecimalFormat decimalFormat
        UserService userService = SpringUtil.getBean("userService")
        User user = userService.getCurrentUser()

        if (user) {
            decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(userService.getUserLocale(user?.localeString))
        } else {
            decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(new Locale("fi"))
        }
        decimalFormat.applyPattern('###.#')
        return decimalFormat
    }

    private HttpSession getSession() {
        GrailsWebRequest webRequest = WebUtils.retrieveGrailsWebRequest()
        HttpSession session = webRequest.getSession()
        return session
    }

}
