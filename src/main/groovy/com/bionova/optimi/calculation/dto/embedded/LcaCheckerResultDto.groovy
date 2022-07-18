package com.bionova.optimi.calculation.dto.embedded

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class LcaCheckerResultDto {
    String letter
    String color
    Integer percentage

    Map<String, BigDecimal> resultByCheck
    List<String> passed
    List<String> acceptable
    List<String> failed
}
