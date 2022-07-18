package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.transformation.Translatable
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class ReportGenerationRuleTemplate {

    String templateId
    String template
    @Translatable
    Map<String, String> name
}
