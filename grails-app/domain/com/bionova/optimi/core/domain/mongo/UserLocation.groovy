package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class UserLocation {
    String countryCode
    String countryName
    String region
    String city
    String postalCode
    Float latitude
    Float longitude
    Integer areaCode

    static constraints = {
        countryCode nullable: true
        countryName nullable: true
        region nullable: true
        city nullable: true
        postalCode nullable: true
        latitude nullable: true
        longitude nullable: true
        areaCode nullable: true
    }

    String toString() {
        return "CountryName: ${countryName}, City: ${city}, PostalCode: ${postalCode}, Lat: ${latitude}, Lng: ${longitude}"
    }
}
