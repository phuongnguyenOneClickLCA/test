package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.transformation.Translatable
import com.bionova.optimi.core.util.DomainObjectUtil

/**
 * @author Pasi-Markus Mäkelä
 */
class IndicatorReportItem implements Serializable {
    static mapWith = "mongo"
    String reportItemId
    String type // possible values are “text”, "textBold", “heading”, “table”, “image"
    @Translatable
    Map<String, String> text
    @Translatable
    Map<String, String> altText
    String textColor
    String tableSource // Possible values: "default”, “queryContent”, "invertedTable”, "normalTable", “specifiedTable”
    Boolean compareFeedback // if it's possible to compare calculationTotalResults to other child entities in result page
    Boolean useRuleShortNames
    List<String> rules // Lists all calculationRules whose calculationTotalResults are listed, used in invertedTable
    List<String> categories
    Map<String, List<String>> expandableCategories //this will generate expander to the category to show virtuals
    List<String> additionalQuestions
    Boolean collapsed
    List<IndicatorReportItemFilter> filters
    // ResultCategories which are listed. If none defined, all are listed. Used in invertedTable and normalTable.
    /*
       Lists sections which are then repeated as such on the result report. Example: "constructionProductGeneralData" : ["GeneralCompulsory", "GeneralOptional" ]
       The contents are rendered with all possible additionalQuestions.
    */
    Map<String, List> queryContent
    // "queryId": ["sectionId1", "sectionId"]. Each question answer found is own table row

    Map<String, String> rowColorCoding
    List tableRows

    /*
       ShowTotals. This is true if not given. When this is true, the table will be added with generated “Total” row and if false, total row is not shown. This works for invertedTable and defaultTable.
       For setting width for leftmost column (invertedTable only): "resultTableInvertedLeftmostColumnWidth" : 200
       showDenominators. Hides denominator scores
     */
    Map tableSettings

    /*
     "maxWidth": 100 , value gives width in pixels,
     "maxHeight": 100 , value gives height in pixels,
     "scaleBy" : "width",
     "useEntityDefaultImage": true/false (if no image for entity, is default icon used)

    */
    Map imageParams
    Boolean showQuestion
    Boolean showUnit
    Boolean dontRepeatQuestion

    ValueReference showReportItemIf

    String textFormatting // Possible values are "text", "bold", "columnHeading"

    IndicatorReportDenominator reportDenominator

    List<IndicatorReportItem> reportItems
    Boolean showEPDDownloadLink

    Boolean dontShowInCompare

    List<String> collapseItemIds //Result table, hides tables under this heading

    String licenseKey // checks license feature

    static transients = [
            'tableRowObjects'
    ]

    static embedded = ['reportDenominator', 'reportItems', 'showReportItemIf', 'filters']

    def getTableRowObjects() {
        List<ReportTableRow> rows = []

        if (tableRows) {
            tableRows.each {
                rows.add(new ReportTableRow(it))
            }
        }
        return rows
    }
}
