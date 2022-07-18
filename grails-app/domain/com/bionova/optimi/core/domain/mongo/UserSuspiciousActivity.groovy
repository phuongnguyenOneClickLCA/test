package com.bionova.optimi.core.domain.mongo

class UserSuspiciousActivity {

    // TODO Cant persist all information since will later run into mongodb max document size, need to rewrite
    List<Date> loggingInWhenSessionIsValid
    Integer loggingInWhenSessionIsValidCount

    Map<String, List<String>> physicallyImpossibleTravelSpeed
    Integer physicallyImpossibleTravelSpeedCount

    Map<String, List<String>> loggingInFromAnotherCountry
    Integer loggingInFromAnotherCountryCount

    Map<String, List<String>> differentBrowser
    Integer differentBrowserCount

    Map<String, String> suspiciousActivityClearedBy

    static transients = ['loggingInFromAnotherCountryScore', 'loggingInWhenSessionIsValidScore',
                         'physicallyImpossibleTravelSpeedScore', 'differentBrowserScore', 'totalScore']

    def getLoggingInWhenSessionIsValidScore() {
        if (loggingInWhenSessionIsValid) {
            return 3 * loggingInWhenSessionIsValid.size()
        } else {
            return 0
        }
    }

    def getPhysicallyImpossibleTravelSpeedScore() {
        if (physicallyImpossibleTravelSpeed) {
            return 5 * physicallyImpossibleTravelSpeed.size()
        } else {
            return 0
        }
    }

    def getLoggingInFromAnotherCountryScore() {
        if (loggingInFromAnotherCountry) {
            return loggingInFromAnotherCountry.size()
        } else {
            return 0
        }
    }

    def getDifferentBrowserScore() {
        if (differentBrowser) {
            return differentBrowser.size()
        } else {
            return 0
        }
    }

    def getTotalScore() {
        return loggingInWhenSessionIsValidScore + physicallyImpossibleTravelSpeedScore +
                loggingInFromAnotherCountryScore + differentBrowserScore
    }


}
