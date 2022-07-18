package com.bionova.optimi.util

import groovy.transform.CompileStatic

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

@CompileStatic
class DateUtil {

    static LocalDate dateToLocalDate(Date from) {
        if (from) {
            return from.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        } else {
            return null
        }
    }

    static Date localDateToDate(LocalDate from) {
        if (from) {
            return Date.from(from.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
        } else {
            return null
        }
    }

    static LocalDateTime dateToLocalDatetime(Date from) {
        if (from) {
            return from.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
        } else {
            return null
        }
    }

    static Date localDateTimeToDate(LocalDateTime from) {
        if (from) {
            return Date.from(from.atZone(ZoneId.systemDefault()).toInstant())
        } else {
            return null
        }
    }
}
