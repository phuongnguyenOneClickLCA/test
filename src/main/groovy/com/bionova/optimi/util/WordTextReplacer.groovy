package com.bionova.optimi.util

import com.bionova.optimi.core.Constants
import groovy.transform.CompileStatic
import javassist.NotFoundException
import org.apache.commons.lang3.StringUtils
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.apache.poi.xwpf.usermodel.IBodyElement
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.apache.poi.xwpf.usermodel.XWPFFooter
import org.apache.poi.xwpf.usermodel.XWPFHeader
import org.apache.poi.xwpf.usermodel.XWPFParagraph
import org.apache.poi.xwpf.usermodel.XWPFRun
import org.apache.poi.xwpf.usermodel.XWPFTable
import org.apache.poi.xwpf.usermodel.XWPFTableCell
import org.apache.poi.xwpf.usermodel.XWPFTableRow

class WordTextReplacer {

    private static Log log = LogFactory.getLog(WordTextReplacer.class)

    @CompileStatic
    static void iterateThroughParagraphs(XWPFDocument doc, Map<String, String> fieldsForReport) {
        List<String> targetMarkers = fieldsForReport.keySet().toList()
        targetMarkers.each { String target ->
            for (XWPFParagraph paragraph : doc.getParagraphs()) {
                String paragraphText = paragraph.getText() ?: ''

                if (paragraphText && paragraphText.contains(target)) {
                    String replacedText = StringUtils.replace(paragraphText, target, fieldsForReport.get(target))
                    removeAllRuns(paragraph)
                    insertReplacementRuns(paragraph, replacedText)
                }
            }
        }
    }

    @CompileStatic
    private static void insertReplacementRuns(XWPFParagraph paragraph, String replacedText) {
        String[] replacementTextSplitOnCarriageReturn = StringUtils.split(replacedText, "\n")

        for (int j = 0; j < replacementTextSplitOnCarriageReturn.length; j++) {
            String part = replacementTextSplitOnCarriageReturn[j]
            XWPFRun newRun = paragraph.insertNewRun(j)
            newRun.setText(part)

            if (j + 1 < replacementTextSplitOnCarriageReturn.length) {
                newRun.addCarriageReturn()
            }
        }
    }

    @CompileStatic
    private static void removeAllRuns(XWPFParagraph paragraph) {
        int size = paragraph.getRuns().size()

        for (int i = 0; i < size; i++) {
            paragraph.removeRun(0)
        }
    }

    @CompileStatic
    static void iterateThroughTables(XWPFDocument doc, Map<String, String> fieldsForReport, List<String> resultMarkers, String reportGenerationRuleId = null) throws NotFoundException {
        for (XWPFTable tbl : doc.getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    List<String> targetMarkers = fieldsForReport.keySet().toList()
                    String cellText = cell.getText()?.trim()

                    if (StringUtils.isNotBlank(cellText)) {
                        Boolean targetFound = false
                        String replacement = ""

                        if (StringUtils.containsWhitespace(cellText)) {
                            cellText.split(' ').eachWithIndex { String txt, int i ->
                                if (targetMarkers.contains(txt)) {
                                    targetFound = true
                                    if (i == 0) {
                                        replacement = replacement + fieldsForReport.get(txt)
                                    } else {
                                        replacement = replacement + " " + fieldsForReport.get(txt)
                                    }
                                } else {
                                    if (i == 0) {
                                        replacement = replacement + txt
                                    } else {
                                        replacement = replacement + " " + txt
                                    }
                                }
                            }
                        } else if (targetMarkers.contains(cellText)) {
                            targetFound = true
                            replacement = fieldsForReport.get(cellText)
                        }

                        if (targetFound) {
                            removeParagraphs(cell)

                            // Format result cell font / size
                            if (resultMarkers?.contains(cellText)) {
                                XWPFParagraph paragraph = cell.addParagraph()
                                if (replacement.contains(Constants.Unicode.MINUS_SIGN.code)) {
                                    replacement = replacement.replace(Constants.Unicode.MINUS_SIGN.code, Constants.Unicode.HYPHEN.code)
                                }
                                setRun(paragraph.createRun(), Constants.FONT_FAMILY_WORD_TABLES, Constants.FONT_SIZE_WORD_TABLES, "000000", replacement, false, false)
                            } else if (replacement == Constants.SCOPE_INCLUDED || replacement == Constants.SCOPE_EXCLUDED) {
                                XWPFParagraph paragraph = cell.addParagraph()

                                String fontColor = Constants.EPD_HUB_REPORT_RULEID.equalsIgnoreCase(reportGenerationRuleId) ? Constants.FONT_COLOR_BLUE_HEX : Constants.FONT_COLOR_GREEN_HEX
                                setRun(paragraph.createRun(), Constants.FONT_FAMILY_WORD_TABLES, Constants.FONT_SIZE_WORD_TABLES, fontColor, replacement, true, false)

                            } else {
                                cell.setText(replacement)
                            }
                        }
                    } else {
                        for (XWPFParagraph paragraph : cell.getParagraphs()) {
                            iterateThroughRuns(paragraph, fieldsForReport);
                        }
                    }
                }
            }
        }
    }

    @CompileStatic
    private static void setRun(XWPFRun run, String fontFamily, int fontSize, String colorRGB, String text, boolean bold, boolean addBreak) {
        if (run) {
            run.setFontFamily(fontFamily)
            run.setFontSize(fontSize)
            run.setColor(colorRGB)
            run.setText(text)
            run.setBold(bold)
            if (addBreak) run.addBreak()
        }
    }

    @CompileStatic
    private static void removeParagraphs(XWPFTableCell tableCell) {
        try {
            int count = tableCell.getParagraphs().size();
            for (int i = count - 1; i >= 0; i--) {
                tableCell.removeParagraph(i);
            }
        } catch (e) {
            log.error("Error occurred in removeParagraphs: ${e}")
        }
    }

    @CompileStatic
    static void iterateThroughHeaders(XWPFDocument doc, Map<String, String> fieldsForReport) throws NotFoundException {
        try {
            for (XWPFHeader header : doc.getHeaderList()) {
                // hackish way to inject to footer, for some reason sometimes footer doesn't have paragraphs
                // therefore need to try looking into the bodyElement. (the bodyElement in headers are different from the one in footers. It is paragraphs)
                // not 100% sure if this will always work.
                if (header.getParagraphs()) {
                    for (XWPFParagraph paragraph : header.getParagraphs()) {
                        iterateThroughRuns(paragraph, fieldsForReport);
                    }
                } else {
                    for (IBodyElement paragraph : header.getBodyElements()) {
                        iterateThroughRuns(paragraph as XWPFParagraph, fieldsForReport);
                    }
                }
            }
        } catch (e) {
            log.error("Error occurred in iterateThroughHeaders: ${e}")
        }
    }

    static void iterateThroughFooters(XWPFDocument doc, Map<String, String> fieldsForReport) throws NotFoundException {
        try {
            for (XWPFFooter footer : doc.getFooterList()) {
                // hackish way to inject to footer, for some reason sometimes footer doesn't have paragraphs
                // therefore need to try looking into the bodyElement which is weird that it doesn't have the content in IBodyElement interface
                // not 100% sure if this will always work.
                if (footer.getParagraphs()) {
                    for (XWPFParagraph paragraph : footer.getParagraphs()) {
                        iterateThroughRuns(paragraph, fieldsForReport);
                    }
                } else {
                    for (IBodyElement bodyElement : footer.getBodyElements()) {
                        for (XWPFParagraph paragraph : bodyElement.content.bodyElements) {
                            iterateThroughRuns(paragraph, fieldsForReport);
                        }
                    }
                }
            }
        } catch (e) {
            log.error("Error occurred in iterateThroughFooters: ${e}")
        }
    }

    @CompileStatic
    private static void iterateThroughRuns(XWPFParagraph paragraph, Map<String, String> fieldsForReport) throws NotFoundException {
        List<XWPFRun> runs = paragraph.getRuns();

        if (runs != null) {
            List<String> targetMarkers = fieldsForReport.keySet().toList()
            int runsSize = runs.size();

            for (int index = 0; index < runsSize; index++) {
                XWPFRun currentRun = runs.get(index);
                String text = currentRun.getText(0);

                if (text != null && targetMarkers.contains(text)) {
                    String newText = currentRun.getText(0).replace(text, fieldsForReport.get(text))
                    currentRun.setText(newText, 0);
                    break
                }
            }
        }
    }
}
