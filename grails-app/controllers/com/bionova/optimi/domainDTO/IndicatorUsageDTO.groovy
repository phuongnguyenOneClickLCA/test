package com.bionova.optimi.domainDTO

import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.License
import grails.compiler.GrailsCompileStatic
import org.bson.Document

@GrailsCompileStatic
class IndicatorUsageDTO {

    Map<String, List<Document>> projectIdAndChildren
    Map<String, Map<String, List<License>>> projectIdAndLicenses
    List<Document> entities
    List<License> allLicenses
    Indicator indicator
    List<Document> users
    int totalIndicatorUsage
}
