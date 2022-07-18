package com.bionova.optimi.core.service

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.NmdElement
import com.bionova.optimi.core.domain.mongo.NmdUpdate
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.json.nmd.NmdDeactivateResources
import com.bionova.optimi.json.nmd.NmdElementElement
import com.bionova.optimi.json.nmd.NmdElementVersies
import com.bionova.optimi.json.nmd.NmdNewResources
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.bson.Document
import org.springframework.web.multipart.MultipartFile

class NmdElementService {
    def domainClassService
    def importMapperService
    def loggerUtil
    def flashService
    def stringUtilsService
    def nmdResourceService
    def nmdApiService
    def importUtilService

    private List<Document> activeElementsWithProjection = null

    def getNmdElements(Boolean findChildren = Boolean.FALSE, Boolean findParents = Boolean.FALSE) {
        List<NmdElement> nmdElements = []
        if (findChildren) {
            nmdElements = NmdElement.findAllByIsPartAndActive(true, true)
        } else if (findParents) {
            nmdElements = NmdElement.findAllByIsPartAndActive(null, true)
        } else {
            nmdElements = NmdElement.findAllByActive(true)
        }
        return nmdElements
    }
    def getNmdElementsAsDocument(Boolean findChildren = Boolean.FALSE,Boolean findParents = Boolean.FALSE) {
        List<Document> nmdElements = []
        if (findChildren) {
            nmdElements = NmdElement.collection.find([active: true, isPart: true], [name: 1, code: 1, elementId: 1, parentElementId: 1, unitId: 1]).toList()
        } else if (findParents) {
            nmdElements = NmdElement.collection.find([active: true, isPart: null], [name: 1, code: 1, elementId: 1, parentElementId: 1, unitId: 1]).toList()
        } else {
            nmdElements = NmdElement.collection.find([active: true], [name: 1, code: 1, elementId: 1, parentElementId: 1, unitId: 1]).toList()
        }
        return nmdElements
    }

    NmdElement getNmdElement(Integer elementId) {
        NmdElement nmdElement = null
        if (elementId) {
            nmdElement = NmdElement.findByElementIdAndActive(elementId, Boolean.TRUE)
        }
        return nmdElement
    }

    List<NmdElement> getNmdElementsByElementId(Integer elementId) {
        if (elementId == null) {
            return null
        }

        return NmdElement.findAllByElementIdAndActive(elementId, true)
    }

    NmdElement getNmdElement(String id) {
        NmdElement nmdElement = null
        if (id) {
            nmdElement = NmdElement.findByIdAndActive(DomainObjectUtil.stringToObjectId(id), Boolean.TRUE)
        }
        return nmdElement
    }

    def importNmdElementExcel(MultipartFile excelFile) {
        Map returnable = [:]
        List<String> persistingProperties = domainClassService.getPersistentPropertyNamesForDomainClass(NmdElement.class)
        List<String> mandatoryProperties = domainClassService.getMandatoryPropertyNamesForDomainClass(NmdElement.class)
        List<String> persistingDoubleProperties = domainClassService.getPersistentPropertyNamesForDomainClass(NmdElement.class, Double)
        List<String> booleanProperties = domainClassService.getPersistentPropertyNamesForDomainClass(NmdElement.class, Boolean)
        Map<String, List<Map<String, String>>> updateStringConfig = nmdResourceService.getUpdateStringOnImportNmd()
        Set<NmdElement> saveableElements = []
        try {
            Workbook workbook = WorkbookFactory.create(excelFile.inputStream)
            String fileName = excelFile.originalFilename
            Integer numberOfSheets = workbook?.getNumberOfSheets()

            log.info("number of sheets ${numberOfSheets}")

            if (numberOfSheets) {
                for (int i = 0; i < numberOfSheets; i++) {
                    Sheet sheet = workbook.getSheetAt(i)
                    int rowCount = sheet?.lastRowNum

                    if (rowCount && sheet && !sheet.sheetName.contains("@")) {
                        // handle headings
                        log.info("handling sheet ${sheet.sheetName}")
                        log.info("rowcount ${rowCount}")
                        Map<String, Integer> columnHeadings = importUtilService.getColumnHeadings(sheet.getRow(0), NmdElement.class)

                        Integer rowNro = 0
                        Row row
                        Iterator<Row> rowIterator = sheet.rowIterator()
                        while (rowIterator.hasNext()) {
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

                            String elementId = rowAsMap.get("elementId")

                            if (elementId && elementId != "null" && elementId.isNumber()) {
                                NmdElement nmdElement = new NmdElement()
                                NmdElement oldElement = getNmdElement(elementId.toInteger())

                                if(oldElement){
                                    oldElement.delete(flush: true)
                                }

                                // Note: please do not set type for the 'value' parameter here
                                // as it may be changed in the closure from 'String' to 'Integer' or 'Double'.
                                rowAsMap.each { String attr, value ->
                                    try {
                                        if (value == "NULL") {
                                            value = null
                                        }

                                        if (DomainObjectUtil.isNumericValue(value)) {
                                            if (persistingDoubleProperties.contains(attr)) {
                                                value = DomainObjectUtil.convertStringToDouble(value)
                                            } else {
                                                value = DomainObjectUtil.convertStringToDouble(value).toInteger()
                                            }
                                        }

                                        if (NmdElement.persistingIntegerListProperties.contains(attr) && value != null) {
                                            List<Integer> values = []
                                            (value as String).tokenize(",")?.each {
                                                if (it.isInteger()) {
                                                    values.add(it.toInteger())
                                                }
                                            }
                                            DomainObjectUtil.callSetterByAttributeName(attr, nmdElement, values)
                                        } else if (booleanProperties?.contains(attr)) {
                                            Boolean boolValue = value?.asBoolean()


                                            if (boolValue != null) {
                                                DomainObjectUtil.callSetterByAttributeName(attr, nmdElement, boolValue)
                                            }
                                        } else if (mandatoryProperties.contains(attr) && value != null) {
                                            DomainObjectUtil.callSetterByAttributeName(attr, nmdElement, value, Boolean.FALSE)
                                        } else if (persistingProperties.contains(attr) && value != null) {
                                            DomainObjectUtil.callSetterByAttributeName(attr, nmdElement, value, Boolean.TRUE)
                                        }
                                    } catch(Exception e){
                                        loggerUtil.warn(log, "ERROR: importNmdElementExcel cannot add attribute ${attr} value ${value} to element ${elementId}. Exception ${e.getMessage()}")
                                    }

                                }
                                populateUnitToElement(nmdElement)
                                if (nmdElement.validate()) {
                                    nmdElement.importSource = fileName
                                    saveableElements.add(nmdElement)
                                } else {
                                    if (returnable.get("error")) {
                                        String existing = returnable.get("error")
                                        returnable.put("error", "${existing}<br/>${nmdElement.getErrors()?.getAllErrors()}")
                                    } else {
                                        returnable.put("error", "${nmdElement.getErrors()?.getAllErrors()}")
                                    }
                                }
                            }
                            rowNro++
                        }
                    }
                }
            }

            if (saveableElements) {
                // need to save elements in 2 loops since parents need to populate types beforehand for the children to inherit from
                // loop for parent elements
                for (NmdElement element in saveableElements) {
                    if (element.parentElementIds) {
                        continue
                    }
                    saveElementToDbFromExcel(element, saveableElements, updateStringConfig, returnable)
                }
                // loop for child elements
                for (NmdElement element in saveableElements) {
                    if (!element.parentElementIds) {
                        continue
                    }
                    saveElementToDbFromExcel(element, saveableElements, updateStringConfig, returnable)
                }
                clearElementsWithProjection()
            }
        } catch (Exception e) {
            loggerUtil.error(log, "ERROR: importNmdElementExcel", e)
            returnable.put("error", "${e}")
        }
        return returnable
    }

    void saveElementToDbFromExcel(NmdElement element, Set<NmdElement> saveableElements, Map<String, List<Map<String, String>>> updateStringConfig, Map returnable) {
        extractCodeAndElementTypeForExcel(element, saveableElements)

        try {
            updateStringFieldsInNmdElement(updateStringConfig, element)
            element.save(failOnError: true)
        } catch (e) {
            loggerUtil.error(log, "ERROR: cannot save element while importing from excel", e)
            returnable.put("error", "${e}")
        }
    }

    String updateNewNmdElementByApi(NmdNewResources json, Map<String, List<Map<String, String>>> updateStringForNmdConstructionConfig, NmdUpdate updateRecord, Date nmdUpdateTime) {
        if (!json) {
            return ''
        }

        String updateString = ""
        try {
            List<NmdElementVersies> elementVersies = json.NMD_Element_Versies?.findAll { it != null }
            List<NmdElementElement> elementElementVersies = json.NMD_Element_Element_Versies?.findAll { it != null }
            if (elementVersies) {
                updateString = "<b>NEW NMD elements update:</b> <br />"
                int i = 1
                int size = elementVersies?.size()
                for (NmdElementVersies elementJson in elementVersies) {
                    Integer elementId = elementJson.Element_ID?.toInteger()
                    if (elementId == null) {
                        continue
                    }
                    NmdElement nmdElement = getNmdElement(elementId)
                    if (!nmdElement) {
                        nmdElement = new NmdElement()
                    }
                    nmdElement.nmdUpdateDate = nmdUpdateTime
                    nmdElement.elementId = elementId
                    nmdElement.name = elementJson.ElementNaam ?: "Undefined"
                    nmdElement.description = elementJson.FunctioneleBeschrijving
                    nmdElement.unitId = elementJson.FunctioneleEenheidID
                    nmdElement.isNlSfb = elementJson.IsNLsfB
                    nmdElement.isRaw = elementJson.IsRAW
                    nmdElement.additionalData = elementJson.Toelichting
                    nmdElement.isPart = elementJson.IsOnderdeel
                    nmdElement.cuasId = elementJson.CUAS_ID != null ? elementJson.CUAS_ID : null
                    nmdElement.active = Boolean.TRUE
                    nmdElement.isChapter = elementJson.IsHoofdstuk
                    nmdElement.mandatory = elementJson.Verplicht
                    nmdElement.isException = elementJson.IsUitzondering
                    nmdElement.isProcess = elementJson.IsProces
                    nmdElement.functions = elementJson.Functie
                    nmdElement.activationDate = elementJson.DatumActief
                    nmdElement.deactivationDate = elementJson.DatumInActief
                    nmdElement.importSource = Constants.NMD_3_API

                    extractParentElementIds(elementElementVersies, elementJson, nmdElement)
                    extractCodeAndElementTypeForApi(elementElementVersies, elementVersies, elementJson, nmdElement)
                    populateUnitToElement(nmdElement, updateRecord)

                    // we define in config some characters to be replaced in some nmd element attribute
                    updateStringFieldsInNmdElement(updateStringForNmdConstructionConfig, nmdElement)

                    if (nmdElement.validate()) {
                        if (nmdElement.id) {
                            nmdElement.merge(flush: true, failOnError: true)
                        } else {
                            nmdElement.save(flush: true, failOnError: true)
                        }
                        updateString = "${updateString} Element ${i}: ElementId ${nmdElement.elementId}, ID: ${nmdElement.id}"
                    } else {
                        updateRecord?.tempElementErrors = "${updateRecord.tempElementErrors ?: ''} Cannot save element ${elementId}: ${nmdElement.getErrors()?.getAllErrors()} <br />"
                    }
                    log.info("NMD Element update prog: ${i} / ${size}")
                    i++
                }
            }
        } catch (e) {
            loggerUtil.error(log, "Cannot get update nmd element from API due to : ${e.getMessage()}", e)
            flashService.setErrorAlert("Cannot get update nmd element from API due to : ${e.getMessage()}", Boolean.TRUE)
        }
        clearElementsWithProjection()

        updateRecord?.tempNewUpdates = "${updateRecord?.tempNewUpdates ?: ''}${updateString}"
        return updateString
    }

    String deactivateNmdElementByApi(NmdDeactivateResources json, NmdUpdate updateRecord = null) {
        if (!json) {
            return ''
        }

        String updateString = ''

        try {
            List<NmdElementVersies> elementVersies = json.NMD_Element_Versies?.findAll { it != null }
            if (elementVersies) {
                int i = 1
                int size = elementVersies.size()

                updateString = "<b>DEACTIVATE Element Update: </b> <br />"
                for (NmdElementVersies elementJson in elementVersies) {
                    Integer elementId = elementJson?.Element_ID
                    if (elementId == null) {
                        continue
                    }
                    // ideally there should be only 1 element active, but run a query for multiple just in case.
                    List<NmdElement> nmdElements = getNmdElementsByElementId(elementId)
                    if (nmdElements) {
                        for (NmdElement nmdElement in nmdElements) {
                            if (!nmdElement) {
                                continue
                            }
                            nmdElement.active = false
                            nmdElement.deactivationDate = elementJson?.DatumInActief
                            nmdElement.merge(flush: true, failOnError: true)
                            updateString = "${updateString} Element ${i}: ElementId ${nmdElement.elementId}, ID: ${nmdElement.id} <br />"
                        }
                    } else {
                        updateRecord?.tempDeactivateElementsNotFound = "${updateRecord?.tempDeactivateElementsNotFound ?: ''} ElementId: ${elementId} <br />"
                    }
                    log.info("NMD Element update prog: ${i} / ${size}")
                    i++
                }
            }
        } catch (e) {
            loggerUtil.error(log, "Cannot deactive nmd element from API due to : ${e.getMessage()}", e)
            flashService.setErrorAlert("Cannot deactive nmd element from API due to : ${e.getMessage()}", Boolean.TRUE)
        }

        updateRecord?.tempNewUpdates = "${updateRecord?.tempNewUpdates ?: ''}${updateString}"

        return updateString
    }

    String getOclUnitbyNmdUnitId(Integer unitId) {
        String unit = Constants.UNIT_UNIT
        if (unitId) {
            unit = nmdResourceService.getNmdUnitToOCL()?.get(unitId.toString()) ?: Constants.UNIT_UNIT
        }
        return unit
    }

    void updateStringFieldsInNmdElement(Map<String, List<Map<String, String>>> config, NmdElement element) {
        if (!element || !config) {
            return
        }
        stringUtilsService.updateStringInObject(config, element)
    }

    void extractParentElementIds(List<NmdElementElement> elementElementVersies, NmdElementVersies elementJson, NmdElement nmdElement) {
        if (!elementElementVersies || !elementJson || !nmdElement) {
            return
        }

        List<Integer> parentElementIds = getElementParentIdsFromJson(elementElementVersies, elementJson)

        if (parentElementIds) {
            nmdElement.parentElementIds = parentElementIds
        }
    }


    List<Integer> getElementParentIdsFromJson(List<NmdElementElement> elementElementVersies, NmdElementVersies elementJson) {
        return elementElementVersies?.findAll { it.KindElementID == elementJson.Element_ID }?.collect { it.OuderElementID }?.unique()
    }

    /**
     * Use for excel
     * @see #extractCodeAndElementTypeForApi
     * @param element
     * @param elements set of all elements including the current element that need to extract the elementType
     */
    void extractCodeAndElementTypeForExcel(NmdElement element, Set<NmdElement> elements) {
        if (!element || !elements) {
            return
        }

        String elementType = null
        boolean isChild = false
        if (element.parentElementIds) {
            isChild = true
            // get the element type from parents (normally just 1 parent, but can have multiple)
            Set<NmdElement> parentElements = elements.findAll { it.elementId in element.parentElementIds }
            List<String> parentElementTypes = parentElements ? parentElements.collect { it.elementType } : getElementsInDbWithProjection()?.collect { it.elementType } as List<String>
            elementType = getElementTypeFromParents(parentElementTypes)
        }

        String code = element.code
        putCodeAndElementTypeToNmdElement(code, isChild, elementType, element)
    }

    /**
     * Use for API
     * @see #extractCodeAndElementTypeForExcel
     * @param elementElementVersies
     * @param elementVersies
     * @param elementJson
     * @param nmdElement
     */
    void extractCodeAndElementTypeForApi(List<NmdElementElement> elementElementVersies, List<NmdElementVersies> elementVersies, NmdElementVersies elementJson, NmdElement nmdElement) {
        if (!nmdElement || !elementJson) {
            return
        }

        String elementType = null
        boolean isChild = false
        List<Integer> parentElementIds = nmdElement.parentElementIds ?: getElementParentIdsFromJson(elementElementVersies, elementJson)

        if (parentElementIds && elementVersies) {
            isChild = true
            // get the element type from parents (normally just 1 parent, but can have multiple)
            List<NmdElementVersies> parentJsons = elementVersies.findAll { it.Element_ID in parentElementIds }
            List<String> parentElementTypes = parentJsons ? parentJsons.collect { getElementTypeFromParentJson(it) } : getElementsInDbWithProjection()?.collect { it.elementType } as List<String>
            elementType = getElementTypeFromParents(parentElementTypes)
        }

        String code = elementJson.Code
        putCodeAndElementTypeToNmdElement(code, isChild, elementType, nmdElement)
    }

    /**
     * Currently this is used as a fallback while extracting the element type. This fallback should very rarely occur
     * @return
     */
    private List<Document> getElementsInDbWithProjection() {
        if (!activeElementsWithProjection) {
            activeElementsWithProjection = NmdElement.collection.find([active: true], [elementType: 1, elementId: 1])?.toList()
        }
        return activeElementsWithProjection
    }

    /**
     * Must run after the loops that (potentially) uses {@link #getElementsInDbWithProjection}
     */
    private void clearElementsWithProjection() {
        activeElementsWithProjection = null
    }

    /**
     * Check the element types of parents.
     * If all of them are the same then return the type
     * if not then return null
     * @param parentElementTypes * @return
     */
    String getElementTypeFromParents(List<String> parentElementTypes) {
        if (!parentElementTypes) {
            return null
        }

        Set<String> uniqueTypes = parentElementTypes.collect { it ? it.toLowerCase().trim() : null }?.toSet()

        if (uniqueTypes.size() > 1 || uniqueTypes.contains(null)) {
            return null
        }

        return uniqueTypes[0]
    }

    /**
     * Should not use this method to extract code and element type.
     * Should use method {@link #extractCodeAndElementTypeForExcel} or {@link #extractCodeAndElementTypeForApi} instead (one of them)
     * @param code
     * @param isChild
     * @param elementType
     * @param nmdElement
     */
    private void putCodeAndElementTypeToNmdElement(String code, Boolean isChild, String elementType, NmdElement nmdElement) {
        if (!isChild || !elementType) {
            // this element is parent, OR its parent doesn't have type, so get type of its code
            elementType = getElementTypeFromCode(code)
        }

        // strip out element type and ': ' from code
        if (elementType) {
            if (code) {
                code -= elementType
                code -= ': '
            }
            nmdElement.elementType = elementType
        }

        // clean code, no gww or b&u
        nmdElement.code = code
    }

    String getElementTypeFromParentJson(NmdElementVersies parentJson) {
        if (!parentJson || !parentJson.Code) {
            return null
        }

        return getElementTypeFromCode(parentJson.Code as String)
    }

    String getElementTypeFromCode(String code) {
        if (!code) {
            return null
        }

        List<String> elementTypes = nmdResourceService.getNmdElementTypes()
        if (elementTypes == null || elementTypes.isEmpty()) {
            return null
        }

        String codeLowerCase = code.toLowerCase()
        for (String elementType in elementTypes) {
            if (codeLowerCase.contains(elementType)) {
                return elementType
            }
        }

        return null
    }

    void populateUnitToElement(NmdElement element, NmdUpdate updateRecord = null) {
        if (!element) {
            return
        }

        if (element.unitId == null) {
            element.unit = Constants.UNKNOWN_UNIT
            return
        }

        Map<String, String> nmdUnitToOCL = nmdResourceService.getNmdUnitToOCL()
        if (nmdUnitToOCL?.containsKey(element.unitId?.toString())) {
            element.unit = nmdUnitToOCL?.get(element.unitId?.toString())
        } else {
            element.unit = Constants.UNKNOWN_UNIT
            if (updateRecord) {
                updateRecord.tempUnitErrors = "${updateRecord.tempUnitErrors ?: ''} Element ID: ${element.elementId} - unitId: ${element.unitId}"
                nmdApiService.addFaultyUnitId(updateRecord, element.unitId)
            }
        }
    }
}