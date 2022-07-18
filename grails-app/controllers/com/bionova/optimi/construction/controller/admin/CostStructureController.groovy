package com.bionova.optimi.construction.controller.admin

import com.bionova.optimi.core.domain.mongo.CostStructure
import com.bionova.optimi.core.domain.mongo.ResourceType
import org.springframework.web.multipart.MultipartHttpServletRequest

/**
 * @author Pasi-Markus Mäkelä
 */
class CostStructureController {

    def costStructureService
    def resourceTypeService

    def form() {
        List<CostStructure> costStructures = costStructureService.getCostStructures()
        List<ResourceType> activeSubTypesWithoutACostStructure = []

        if (costStructures) {
            List<String> costStructureSubTypes = costStructures.collect({ it.resourceSubType })

            if (costStructureSubTypes && !costStructureSubTypes.isEmpty()) {
                resourceTypeService.getActiveResourceTypes()?.findAll({ it.isSubType })?.each {
                    if (!costStructureSubTypes.contains(it.subType)) {
                        activeSubTypesWithoutACostStructure.add(it)
                    }
                }
            }
        }
        [costStructures: costStructures, activeSubTypesWithoutACostStructure: activeSubTypesWithoutACostStructure]
    }

    def upload() {
        if (request instanceof MultipartHttpServletRequest) {
            def excelFile = request.getFile("xlsFile")

            if (!excelFile || excelFile?.empty) {
                flash.fadeErrorAlert = g.message(code: "import.file.required")
                redirect action: "form"
            } else {
                try {
                    def mapWithInfo = costStructureService.importCostStructures(excelFile)
                    def file = mapWithInfo.get("file")
                    def sheets = mapWithInfo.get("sheets")
                    def okCostStructures = mapWithInfo.get("okCostStructures")
                    def errorCostStructures = mapWithInfo.get("errorCostStructures")
                    String errorMessage


                    if (okCostStructures) {
                        flash.successAlert = "Imported ${file}, ${sheets ? sheets.size() : 0} sheets, successfully imported ${okCostStructures.size()} costStructures, rejected ${errorCostStructures ? errorCostStructures.size() : 0} costStructures."
                    } else {
                        errorMessage = "Import rejected due to error(s) in the file"
                    }

                    if (mapWithInfo.get("errorMessage")) {
                        errorMessage = errorMessage ? errorMessage + "<br />" + mapWithInfo.get("errorMessage") : mapWithInfo.get("errorMessage")
                    }

                    if (mapWithInfo.get("warnMessage")) {
                        flash.warningAlert = mapWithInfo.get("warnMessage")
                    }

                    if (errorMessage) {
                        flash.errorAlert = errorMessage
                    }
                } catch (Exception e) {
                    flash.errorAlert = e.getMessage()
                }
                chain(action: "form")
            }
        } else {
            flash.fadeErrorAlert = "Cannot import excel file, because invalid request: ${request.class} (should be MultipartHttpServletRequest)."
            redirect action: "form"
        }
    }

    def remove() {
        String id = params.id
        Boolean deleteOk = Boolean.FALSE

        if (id) {
            deleteOk = costStructureService.deleteCostStructure(id)
        }

        if (deleteOk) {
            flash.fadeSuccessAlert = "CostStructure removed successfully"
        } else {
            flash.fadeErrorAlert = "Removing CostStructure failed. See logs"
        }
        redirect action: "form"
    }

}
