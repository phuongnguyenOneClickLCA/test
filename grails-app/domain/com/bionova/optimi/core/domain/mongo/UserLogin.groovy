package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

import java.text.DateFormat
import java.text.SimpleDateFormat

@GrailsCompileStatic
class UserLogin {
    Date time
    String ipAddress
    String browserAndOs
    String browser
    UserLocation location

    static embedded = ['location']

    static constraints = {
        location nullable: true
    }

    String toString() {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
        return "Time: ${dateFormat.format(time)}, IPAddress: ${ipAddress}, BrowserAndOs: ${browserAndOs}, Browser: ${browser}, Location: ${location?.toString()}"
    }
}
