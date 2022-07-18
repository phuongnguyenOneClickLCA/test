package com.bionova.optimi.construction.controller.admin

import org.apache.poi.ss.usermodel.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest

class LocalizationFileController {

    def localizedLinkService

    def index() {
        flash.warningAlert = "Reminder: Please upload links first if you have new link translation ids. Otherwise the ids wont be replaced with links when you upload the actual localization excel!"
        [links: localizedLinkService.getAllLinks()]
    }

    def loadLocalizationExcel() {
        List<String> okLocales = []
        List<String> errorLocales = []

        try {
            if (request instanceof MultipartHttpServletRequest) {
                MultipartFile multipartFile = request.getFile("localizationExcel")

                if (multipartFile && !multipartFile.isEmpty()) {
                    Workbook workbook = WorkbookFactory.create(multipartFile.inputStream)
                    int numberOfSheets = workbook?.getNumberOfSheets()
                    Sheet sheet
                    String userHome = System.getProperty("user.home")

                    for (int i = 0; i < numberOfSheets; i++) {
                        sheet = workbook.getSheetAt(i)

                        if (sheet && !sheet.sheetName.startsWith("@")) {
                            Row firstRow = sheet.getRow(sheet.getFirstRowNum())
                            Iterator<Cell> cellIterator = firstRow.cellIterator()
                            Cell headerCell
                            List<String> localizationFileNames = []
                            Map<Integer, String> localeIndex = [:]

                            while (cellIterator.hasNext()) {
                                headerCell = cellIterator.next()

                                if (headerCell.cellType == CellType.STRING &&
                                        headerCell.getStringCellValue() && !"key".equalsIgnoreCase(headerCell.getStringCellValue()) && !headerCell.getStringCellValue()?.startsWith("@")) {
                                    String filePath = "${userHome}/localization/messages_${headerCell.getStringCellValue().toLowerCase()}.properties"
                                    localizationFileNames.add(filePath)
                                    localeIndex.put(headerCell.getColumnIndex(), filePath)
                                }
                            }

                            if (!localeIndex.isEmpty()) {
                                sheet.removeRow(firstRow)

                                localeIndex.each { Integer index, String filePath ->
                                    File localizationFile = new File(filePath)
                                    FileOutputStream fos = new FileOutputStream(localizationFile, false) // true to append
                                    String content = ""

                                    for (Row row in sheet) {
                                        String key = row.getCell(0)?.getStringCellValue()
                                        String value = row.getCell(index.intValue())?.getStringCellValue()
                                        value = localizedLinkService.getTransformStringIdsToLinks(value)

                                        if (key && value) {
                                            content = content + key + "=" + value + "\n"
                                        }
                                    }

                                    if (content) {
                                        fos.write(content.getBytes("UTF8"))
                                        fos.close()
                                        okLocales.add(filePath)
                                    } else {
                                        errorLocales.add(filePath)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (okLocales) {
                flash.fadeSuccessAlert = "Wrote successfully localization files: ${okLocales}"
            }

            if (errorLocales) {
                flash.errorAlert = "Error in writing these locales: ${errorLocales}"
            }
        } catch (Exception e) {
            flash.errorAlert = "${e}"
        }
        redirect action: "index"
    }

    def linkLocalizationExcel() {
        Map returned

        try {
            if (request instanceof MultipartHttpServletRequest) {
                MultipartFile multipartFile = request.getFile("localizationExcel")

                if (multipartFile && !multipartFile.isEmpty()) {
                    Workbook workbook = WorkbookFactory.create(multipartFile.inputStream)
                    returned = localizedLinkService.createLocalizedLinkObjectsFromWorkbook(workbook)
                }
            }

            if (returned?.get("saved")) {
                flash.successAlert = "${returned?.get("saved")}"
            }

            if (returned?.get("error")) {
                flash.errorAlert = "${returned?.get("error")}"
            }
        } catch (Exception e) {
            flash.errorAlert = "${e}"
        }
        redirect action: "index"
    }

}
