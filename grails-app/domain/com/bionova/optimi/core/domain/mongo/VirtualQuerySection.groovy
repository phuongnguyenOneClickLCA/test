package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import groovy.transform.TypeCheckingMode

@GrailsCompileStatic
class VirtualQuerySection {

    String virtualSectionId
    Map<String, String> name

    /*
        "linkedSections" : [
          {
            "sectionId" : "basicQuerySection",
            "questionIds" : ["type", "grossSurface", "numberFloor", "certification", "frameType", "entityImage"]
            "hideHeadText" : true
          },
          {
            "sectionId" : "additionalInformation",
            "questionIds" : ["projectNumber", "investor", "yearOfConstruction", "functions", "technicalFunctionalReqs"]
           }
        ]
     */
    List<Map> linkedSections

    static constraints = {
        virtualSectionId nullable: true
        name nullable: true
        linkedSections nullable: true
    }

    // This method compile virtualSection in config to a 'real' query section
    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    QuerySection compileVirtualSection(List<QuerySection> querySections) {
        QuerySection compiledSection = null

        // hardcoded virtual sections do not have linkedSections
        if (linkedSections && querySections) {
            linkedSections.eachWithIndex { Map linkedSection, Integer index ->
                QuerySection copy = querySections.find({ linkedSection.sectionId == it.sectionId })?.createCopy()
                copy?.questions?.removeAll({ !linkedSection.questionIds?.contains(it.questionId) })
                copy?.hideHeadText = linkedSection.hideHeadText == 'true'

                if (copy.questions) {
                    // if there are many linked sections declared, they are all compiled into one query section in which the first linkedSection is the main and next ones are sub sections.
                    if (index == 0) {
                        // only the main section get the virtual name and id
                        copy.virtualSectionId = virtualSectionId
                        copy.virtualName = name
                        compiledSection = copy
                    } else {
                        if (!compiledSection?.sections) {
                            compiledSection?.sections = new ArrayList<QuerySection>()
                        }
                        copy.overrideMainSection = true // to make this subsection act as a main section
                        compiledSection?.sections?.add(copy)
                    }
                }
            }
        }

        if (!compiledSection) {
            compiledSection = new QuerySection()
            compiledSection.virtualSectionId = virtualSectionId
            compiledSection.virtualName = name
        }
        return compiledSection
    }
}
