package com.bionova.optimi.core.util

import com.bionova.optimi.core.util.DomainObjectUtil
import spock.lang.Specification

class DomainObjectUtilSpec extends Specification {
    def setup() {
    }

    def cleanup() {
    }

    void "check isNumericValue method"() {
        given:
        boolean result1 = DomainObjectUtil.isNumericValue("1.2")
        boolean result2 = DomainObjectUtil.isNumericValue("1,2")
        boolean result3 = DomainObjectUtil.isNumericValue(".2")
        boolean result4 = DomainObjectUtil.isNumericValue(",2")
        boolean result5 = DomainObjectUtil.isNumericValue("f,2")
        boolean result6 = DomainObjectUtil.isNumericValue("")
        boolean result7 = DomainObjectUtil.isNumericValue(null)
        boolean result8 = DomainObjectUtil.isNumericValue("1.0.1.0")
        boolean result9 = DomainObjectUtil.isNumericValue("1,0,1,0")

        expect:
        result1 == true
        result2 == true
        result3 == true
        result4 == true
        result5 == false
        result6 == false
        result7 == false
        result8 == false
        result9 == false
    }

    void "check convertStringToDouble conversion"() {
        given:
        Double result1 = DomainObjectUtil.convertStringToDouble("1.2")
        Double result2 = DomainObjectUtil.convertStringToDouble("1,2")
        Double result3 = DomainObjectUtil.convertStringToDouble(".2")
        Double result4 = DomainObjectUtil.convertStringToDouble(",2")
        Double result5 = DomainObjectUtil.convertStringToDouble("f,2")
        Double result6 = DomainObjectUtil.convertStringToDouble("")
        Double result7 = DomainObjectUtil.convertStringToDouble(null)
        Double result8 = DomainObjectUtil.convertStringToDouble("1.0.1.0")
        Double result9 = DomainObjectUtil.convertStringToDouble("1,0,1,0")

        expect:
        result1 == 1.2D
        result2 == 1.2D
        result3 == 0.2D
        result4 == 0.2D
        result5 == 0D
        result6 == 0D
        result7 == 0D
        result8 == 0D
        result9 == 0D
    }
}
