package com.bionova.optimi.core.service

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.LocalizedLink
import com.bionova.optimi.core.util.DomainObjectUtil
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook

import java.util.regex.Matcher
import java.util.regex.Pattern
/**
 *
 */
class LocalizedLinkService {

    def domainClassService
    def importMapperService
    def localizationService

    private static boolean linksUpdated = false
    private static List<LocalizedLink> links

    def getLocalizedLinkByTranslationId(String translationId) {
        return getLinks()?.find({it.translationId == translationId})
    }

    def getTransformStringIdsToLinks(String original) {
        if (original && original.contains(Constants.LOCALIZED_LINK_IDENTIFIER)) {
            Pattern pattern = Pattern.compile(Constants.LOCALIZED_LINK_REGEX)
            Matcher matcher = pattern.matcher(original)
            while (matcher.find()) {
                String toReplace = matcher.group()
                def translationObject = getLocalizedLinkByTranslationId(toReplace.tokenize(Constants.LOCALIZED_LINK_DELIMITER)[1])

                if (translationObject) {
                    original = original.replace(toReplace, "<a href='${translationObject.linkURL}' " +
                            "target='_blank'>${getLocalizedName(translationObject)}</a>")
                } else {
                    original = original.replace(toReplace, "TRANSLATION LINK ${toReplace} MISSING!")
                }

            }
        }
        return original
    }

    def getTransformStringIdsToUrl(String original) {
        if (original?.contains(Constants.LOCALIZED_LINK_IDENTIFIER)) {
            Pattern pattern = Pattern.compile(Constants.LOCALIZED_LINK_REGEX)
            Matcher matcher = pattern.matcher(original)
            while (matcher.find()) {
                String toReplace = matcher.group()
                def translationObject = getLocalizedLinkByTranslationId(toReplace.tokenize(Constants.LOCALIZED_LINK_DELIMITER)[1])

                if (translationObject) {
                    original = original.replace(toReplace, "${translationObject.linkURL}")
                } else {
                    original = original.replace(toReplace, "TRANSLATION LINK ${toReplace} MISSING!")
                }

            }
        }
        return original
    }

    def createLocalizedLinkObjectsFromWorkbook(Workbook workbook) {
        Map returnable = [:]
        List<String> persistingProperties = domainClassService.getPersistentPropertyNamesForDomainClass(LocalizedLink.class)
        List<String> mandatoryProperties = domainClassService.getMandatoryPropertyNamesForDomainClass(LocalizedLink.class)

        try {
            LocalizedLink.list()?.each {
                it.delete(flush: true)
            }
            Integer numberOfSheets = workbook?.getNumberOfSheets()

            log.info("number of sheets ${numberOfSheets}")

            if (numberOfSheets) {
                for (int i = 0; i < numberOfSheets; i++) {
                    Map<String, Integer> columnHeadings = [:]
                    Sheet sheet = workbook.getSheetAt(i)
                    int rowCount = sheet?.lastRowNum
                    if (rowCount && sheet && !sheet.sheetName.contains("@")) {
                        // handle headings
                        log.info("handling sheet ${sheet.sheetName}")
                        log.info("rowcount ${rowCount}")
                        Row headingRow = sheet.getRow(0)
                        Iterator<Cell> cellIterator = headingRow.cellIterator()
                        while (cellIterator.hasNext()) {
                            Cell cell = cellIterator.next()
                            String heading = importMapperService.getCellValue(cell)

                            if (heading) {
                                columnHeadings.put(heading, cell.columnIndex)
                            }
                        }

                        Integer rowNro = 0
                        Row row
                        Iterator<Row> rowIterator = sheet.rowIterator()
                        while(rowIterator.hasNext()) {
                            Map<String, String> rowAsMap = [:]
                            row = rowIterator.next()

                            if (rowNro != 0) {
                                columnHeadings.each { String key, Integer value ->
                                    String cellValue = importMapperService.getCellValue(row.getCell(value))

                                    if (cellValue) {
                                        rowAsMap.put(key, cellValue)
                                    }
                                }
                            }

                            String translationId = rowAsMap.get("translationId")

                            if (translationId) {
                                LocalizedLink localizedLink = new LocalizedLink()

                                rowAsMap.each { String attr, value ->
                                    if (mandatoryProperties.contains(attr)) {
                                        DomainObjectUtil.callSetterByAttributeName(attr, localizedLink, value, Boolean.FALSE)
                                    } else if (persistingProperties.contains(attr)) {
                                        DomainObjectUtil.callSetterByAttributeName(attr, localizedLink, value, Boolean.TRUE)
                                    }
                                }

                                if (localizedLink.validate()) {
                                    localizedLink.save(flush: true, failOnError: true)
                                    linksUpdated = true

                                    if (returnable.get("saved")) {
                                        String existing = returnable.get("saved")
                                        returnable.put("saved", "${existing}<br/>${translationId}")
                                    } else {
                                        returnable.put("saved", "Saved localized links:<br/>${translationId}")
                                    }

                                } else {
                                    if (returnable.get("error")) {
                                        String existing = returnable.get("error")
                                        returnable.put("error", "${existing}<br/>${localizedLink.getErrors()?.getAllErrors()}")
                                    } else {
                                        returnable.put("error", "${localizedLink.getErrors()?.getAllErrors()}")
                                    }
                                }
                            }
                            rowNro++
                        }
                    }
                }
            }
        } catch (Exception e) {
            returnable.put("error", "${e}")
        }
        return returnable
    }

    List<LocalizedLink> getAllLinks() {
        return getLinks()
    }

    String getLocalizedName(LocalizedLink localizedLink) {
        if(!localizedLink) {
            return null
        }
       return localizationService.getLocalizedProperty(localizedLink, "name", "name")
    }

    private List<LocalizedLink> getLinks() {
        if (!links || isLinksUpdated()) {
            try {
                List e = LocalizedLink.list()

                if (e) {
                    links = new ArrayList<LocalizedLink>(e)
                }
            } catch (Exception e) {

            }
        }
        return links
    }

    private boolean isLinksUpdated() {
        if (linksUpdated) {
            linksUpdated = false
            return true
        } else {
            return false
        }
    }

}
