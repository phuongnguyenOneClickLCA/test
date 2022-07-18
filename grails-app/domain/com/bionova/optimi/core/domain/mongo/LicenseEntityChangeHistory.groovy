package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.util.DateUtil
import grails.compiler.GrailsCompileStatic
import groovy.transform.TypeCheckingMode

import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class LicenseEntityChangeHistory implements Serializable {
    static mapWith = "mongo"
    String entityId
    Date dateAdded
    Date dateRemoved
    String addedBy
    String removedBy

    static transients = ['anniversaryDate', 'addedByUser']

    transient userService

    Map<Integer, Integer> getAnniversaryDate() {
        Map<Integer, Integer> anniversary = [:]

        if (dateAdded) {
            LocalDate start = DateUtil.dateToLocalDate(dateAdded)
            LocalDate current = LocalDate.now()
            LocalDateTime startTime = DateUtil.dateToLocalDatetime(dateAdded)
            LocalDateTime currentTime = LocalDateTime.now()
            Period period = Period.between(start, current)

            int yearsBetween = currentTime.year - startTime.year - 1

            if (yearsBetween < 0) {
                yearsBetween = 0
            }
            Duration duration = Duration.between(startTime.plusYears(yearsBetween), currentTime)

            Integer nextYear = period.getYears() + 1
            int daysInYear = current.lengthOfYear()
            Integer inDays = daysInYear - duration.toDays().intValue()

            if (inDays < 0) {
                inDays = inDays + daysInYear
            }
            anniversary.put(nextYear, inDays)
        }
        return anniversary
    }

    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    User getAddedByUser() {
        return userService.getUserByUsername(addedBy)
    }
}
