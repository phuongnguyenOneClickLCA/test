package com.bionova.optimi.core.taglib

import com.bionova.optimi.core.domain.mongo.ProjectTemplate
import com.bionova.optimi.core.service.ProjectTemplateService


class ProjectTemplateTagLib {
    static namespace = "projectTemplateRender"
    ProjectTemplateService projectTemplateService

    def getEntityTemplateName = { attrs ->
        out << projectTemplateService.getEntityTemplateName(attrs.template as ProjectTemplate)
    }
}
