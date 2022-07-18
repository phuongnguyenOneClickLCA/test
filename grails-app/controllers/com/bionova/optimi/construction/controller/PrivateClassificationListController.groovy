package com.bionova.optimi.construction.controller

import com.bionova.optimi.core.domain.mongo.Account
import com.bionova.optimi.core.domain.mongo.PrivateClassificationList
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.User
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.web.multipart.MultipartFile

class PrivateClassificationListController {

    def userService
    def accountService
    def queryService
    def privateClassificationListService
    def fileUtil
    def importMapperService
    def entityService
    def loggerUtil
    def flashService

    def privateClassifications() {

        User user = userService.getCurrentUser()

        if (user) {
            Account account
            String accountId = params.accountId
            Boolean editable = Boolean.FALSE
            PrivateClassificationList PCL

            if (accountId) {
                account = accountService.getAccount(accountId)
            } else {
                account = chainModel?.get("account")
            }

            if (account) {
                PCL = privateClassificationListService?.getPrivateClassificationListFromAccountId(accountId)

                if (user.internalUseRoles||(account && account.mainUserIds?.contains(user.id.toString()))) {
                    editable = Boolean.TRUE
                } else if (account && account.userIds?.contains(user.id.toString())) {
                    editable = Boolean.FALSE
                }
            }

            if (editable) {
                [account: account, editable: editable, privateClassificationList: PCL]
            } else {
                flash.fadeErrorAlert = "No rights to access organization ${account?.companyName} private classifications."
                redirect(controller: "main", action: "list")
            }
        } else {
            flash.fadeErrorAlert = "Error in accessing private classifications, please contact support"
            redirect(controller: "main", action: "list")
        }
    }

    def downloadTemplateFile() {
        boolean ok = true
        Query privateClassificationsQuery = queryService.getQueryByQueryId("privateClassificationsListsQuery", true)
        if (privateClassificationsQuery) {
            File templateFile = new File(privateClassificationsQuery?.template)
            if (templateFile && templateFile.exists() && templateFile.isFile()) {
                InputStream contentStream = null
                try {
                    response.setHeader("Content-disposition", "attachment; filename=${templateFile.name}")
                    response.setHeader("Content-Length", "${templateFile.size()}")
                    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                    contentStream = templateFile.newInputStream()
                    response.outputStream << contentStream
                    webRequest.renderView = false
                } catch (Exception e) {
                    ok = false
                    flashService.setErrorAlert(message(code: 'downloadTemplate.failed') as String)
                    loggerUtil.error(log, "Error in downloading template", e)
                    flashService.setErrorAlert("Error in downloading template $e.message", true)
                } finally {
                    response.outputStream.close()
                    if (contentStream != null) {
                        contentStream.close()
                    }
                }
            } else {
                ok = false
                String msg = message(code: 'downloadTemplate.failed') + ' ' + message(code: 'templateFile.notFound')
                flashService.setErrorAlert(msg)
                String msg2 = "Error in downloading template. No template file found for template file path in config: ${privateClassificationsQuery?.template}"
                loggerUtil.error(log, msg2)
                flashService.setErrorAlert(msg2, true)
            }
        }
        if (!ok) {
            redirect action: "privateClassifications", params: [accountId: params.accountId]
        }
    }

    def createNewClassificationList() {
        String name = params.name.replaceAll("[^\\w ]", "")
        String accountId = params.accountId
        Account account
        if (accountId) {
            account = accountService.getAccount(accountId)
        }
        if (!name) {
            flash.errorAlert = "Please enter a name for the private classification"
            redirect action: "privateClassifications", params: [accountId: accountId]
        }
        if (account) {
            String questionId = account.companyName.replaceAll("\\s","") +"_"+name.replaceAll("\\s","") +"_PC".toLowerCase()
            questionId = questionId.replaceAll("[^0-9a-zA-Z:,]+","")
            String entityFileId
            Map<String, String> answersAndAnswerIds = [:]
            MultipartFile file = fileUtil.getFileFromRequest(request)

            if (file) {
                //----- Parsing the spreadsheet and storing the data in map, will skip heading from template, autogenerate answerIds

                try {
                    String formattedName = name.replaceAll("[^0-9a-zA-Z:,]+","").toLowerCase()
                    answersAndAnswerIds.put(formattedName + "_" + "noclassification", "No classification") //Automatically adds in the no classification params
                    XSSFWorkbook book = new XSSFWorkbook(file.inputStream)
                    XSSFSheet sheet = book.getSheetAt(0)
                    Iterator rowIter = sheet.rowIterator()
                    int count = 0
                    while(rowIter.hasNext()) {
                        XSSFRow myRow = (XSSFRow) rowIter.next()
                        Iterator cellIterator = myRow.cellIterator()
                        while (cellIterator.hasNext() && count != 0) {
                            XSSFCell cell = (XSSFCell) cellIterator.next()
                            String answer = importMapperService.getCellValue(cell)
                            if (!answersAndAnswerIds.containsValue(answer)) {
                                String answerId = formattedName + "_" + answer.replaceAll("[^0-9a-zA-Z:,]+","").toLowerCase()
                                answersAndAnswerIds.put(answerId, answer)
                            }
                        }
                        count++
                    }

                    //----- Creating the new PCL with the data from above
                    PrivateClassificationList privateClassification = new PrivateClassificationList(name: name, questionId: questionId, answers: answersAndAnswerIds, accountId: accountId)

                    if (privateClassification && privateClassification.validate()) {
                        privateClassification.save(flush:true, failOnError:true)
                        flash.fadeSuccessAlert = "Successfully created private classification list: ${name}"
                        redirect action: "privateClassifications", params: [accountId: accountId]
                    } else {
                        flash.errorAlert = "Error in creating private classification list: ${name}"
                        redirect action: "privateClassifications", params: [accountId: accountId]
                    }
                } catch (Exception e) {
                    flash.errorAlert = "Unable to create classifications, make sure the file is an .xlsx file and is in the same format as the template."
                    log.warn("PRIVATE CLASSIFICATION EXCEPTION for account ${account?.companyName}: ${e}")
                    redirect action: "privateClassifications", params: [accountId: accountId]
                }
            }
        }
    }

    def downloadClassificationExcelFile() {
        String fileId = params.id
        if (fileId) {
            PrivateClassificationList plc = privateClassificationListService.getPrivateClassificationList(fileId)

            if (plc) {
                if (plc.linkedEntityFileId) {
                    entityService.removeEntityFile(plc.linkedEntityFileId)
                    plc.linkedEntityFileId = null
                    plc = plc.merge(flush: true)
                }

                Workbook wb = new XSSFWorkbook()
                Sheet sheet = wb.createSheet()
                int row = 0
                Row heading = sheet.createRow(row)
                Cell headingCell = heading.createCell(0)
                headingCell.setCellValue("Classification Answers")
                row++

                plc.answers?.each {
                    Row r = sheet.createRow(row)
                    Cell c = r.createCell(0)
                    c.setCellValue(it.value)
                    row++
                }

                if (wb) {
                    response.contentType = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
                    response.setHeader("Content-disposition", "attachment; filename=${plc.name?.replaceAll(" ", "_")}.xlsx")
                    OutputStream outputStream = response.getOutputStream()
                    wb.write(outputStream)
                    outputStream.flush()
                    outputStream.close()
                } else {
                    render(text: "Could not download Excel file.")
                }
            } else {
                render text: "File not found!"
            }
        } else {
            render text: "File not found!"
        }
    }
    def changeClassificationName() {
        String name = params.name
        String accountId = params.accountId
        if (accountId) {
            PrivateClassificationList PCL = privateClassificationListService?.getPrivateClassificationListFromAccountId(accountId)
            if (PCL && name) {
                //Modify name
                name = name.replaceAll("[^\\w ]", "")
                if (!name.equals(PCL.name)) {
                    log.info("Old name: ${PCL.name}")
                    PCL.name = name
                    log.info("New name: ${PCL.name}")
                    PCL.merge(flush: true, failOnError: true)
                    flash.successAlert = "Successfully updated PCL: ${PCL.name}"
                } else {
                    flash.fadeInfoAlert = "No changes made to: ${PCL.name}"
                }
                //redirect action: "privateClassifications", params: [accountId: accountId]
            } else {
                flash.errorAlert = "Please enter a name for the private classification"
                //redirect action: "privateClassifications", params: [accountId: accountId]
            }
        }
    }

    def uploadNewClassificationList() {
        // Update functionality, needs to get the PCL id, accountId as a params and excel file
        String name = params.name
        String accountId = params.accountId
        if (accountId) {
            MultipartFile file
            file = fileUtil.getFileFromRequest(request)
            if (file.isEmpty()) {
                flash.errorAlert = "Please choose a file to update the private classification"
                redirect action: "privateClassifications", params: [accountId: accountId]
            } else {
                PrivateClassificationList PCL = privateClassificationListService?.getPrivateClassificationListFromAccountId(accountId)
                if (PCL) {
                    try {
                        //Modify name
                        name = name.replaceAll("[^\\w ]", "")
                        //Changes the answers
                        if (PCL.answers && !file.isEmpty()) {
                            // if it does, delete/clear it,
                            log.info("Old answers: ${PCL.answers}")
                            PCL.answers.clear()

                            log.info("Empty answers: ${PCL.answers}")
                            if (PCL.answers.isEmpty()) {
                                name = name.replaceAll("[^0-9a-zA-Z:,]+","").toLowerCase()
                                PCL.answers.put(name + "_" + "noclassification", "No classification") //Automatically adds in the no classification params
                                XSSFWorkbook book = new XSSFWorkbook(file.inputStream);
                                XSSFSheet sheet = book.getSheetAt(0);
                                Iterator rowIter = sheet.rowIterator();
                                int count = 0

                                while (rowIter.hasNext()) {
                                    XSSFRow myRow = (XSSFRow) rowIter.next();
                                    Iterator cellIterator = myRow.cellIterator();
                                    while (cellIterator.hasNext() && count != 0) {
                                        XSSFCell cell = (XSSFCell) cellIterator.next();
                                        String answer = importMapperService.getCellValue(cell)
                                        if (!PCL.answers.containsValue(answer)) {
                                            String answerId = name + "_" + answer.replaceAll("[^0-9a-zA-Z:,]+","").toLowerCase()
                                            PCL.answers.put(answerId, answer)
                                            log.info("${PCL.answers}")
                                        }
                                    }
                                    count++
                                }
                            }
                        }

                        //must delete entityFile also
                        if (PCL.linkedEntityFileId) {
                            entityService.removeEntityFile(PCL.linkedEntityFileId)
                            PCL.linkedEntityFileId = ""
                        }
                        PCL.merge(flush:true, failOnError:true)
                        flash.successAlert = "Successfully updated PCL: ${PCL.name}"
                        redirect action: "privateClassifications", params: [accountId: accountId]
                        // then read and save data (get code from above), save new entityFileId
                    } catch (Exception e) {
                        flash.errorAlert = "Unable to create classifications, make sure the file is an .xlsx file and is in the same format as the template."
                        log.warn("PRIVATE CLASSIFICATION EXCEPTION for account ${accountId}: ${e}")
                        redirect action: "privateClassifications", params: [accountId: accountId]
                    }
                }
            }
        }
    }

    def deleteClassificationList() {
        PrivateClassificationList privateClassificationList
        String id = params.id
        if (id) {
            privateClassificationList = privateClassificationListService.getPrivateClassificationList(id)
            if (privateClassificationList) {
                if (privateClassificationList.linkedEntityFileId) {
                    entityService.removeEntityFile(privateClassificationList.linkedEntityFileId)
                }
                privateClassificationList.delete(flush: true, failOnError: true)
                flash.sucessAlert = "Successfully deleted PCL: Name here"
            }
        }
        redirect action: "privateClassifications", params: [accountId: params.accountId]
    }

}
