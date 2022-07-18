package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class ReportGenerationRuleContent {

    /*
                { "targetMarker": "REPLACE-NAME", "type" : "text",	"entity" : "parent", "queryId" : "basicQuery", "sectionId" : "basicQuerySection", "questionId" : "name" },

     */
    String targetMarker
    String resultCategoryId
    String calculationRuleId
    String attribute
    String type
    String entity
    String queryId
    String sectionId
    String questionId
    String source
    String additionalQuestionId
    Integer imageWidth
    Integer imageHeight
    List<String> uiIdentification
    List<String> filterByResultCategory
    Boolean exportTransportResourceInfo
    Boolean localizedHeaders

    /*
        Example for "type" : "imageFile"
        { "targetMarker": "REPLACE-EPD-PROGRAM-IMAGE", "type" : "imageFile",	"entity" : "child", 	"queryId" : "epdDefaults_preVerified", "sectionId" : "epdProgramme_PCR", "questionId" : "epdProgramm", "answerToImageFile" : { "rts" : "/var/www/static/images/some-image.jpeg" }}
     */
    Map<String,String> answerToImageFile
}
