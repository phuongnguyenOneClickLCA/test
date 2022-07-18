/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.construction.controller


import com.bionova.optimi.core.domain.mongo.Nmd3ProductenBijElement
import com.bionova.optimi.core.domain.mongo.Nmd3ProductenProfielWaarden
import com.bionova.optimi.core.domain.mongo.Nmd3ProfielsetsEnSchalingBijProduct2
import com.bionova.optimi.core.domain.mongo.Nmd3VolledigProductBijProduct
import com.bionova.optimi.core.domain.mongo.NmdUpdate
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.service.NmdApiService
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import org.grails.web.json.JSONObject

import java.text.SimpleDateFormat

/**
 * @author Pasi-Markus Mäkelä
 */
class NmdApiController {

    def nmdApiService
    def constructionService
    def optimiResourceService
    def flashService
    def nmdResourceService

    /* REL-304 no need for this end point any more
    def getEditModal() {
        String heading = ""
        String body = ""

        String resourceId = params.resourceId
        String rowId = params.rowId
        String nmdProfileSetString = params.nmdProfileSetString

        if (resourceId && rowId) {
            Resource resource = optimiResourceService.getResourceWithGorm(resourceId)

            if (resource && resource.nmdProductId) {
                heading = "<h1>${resource.nameNL}</h1>"
                try {
                    List<String> profileSets = []

                    if (nmdProfileSetString) {
                        if (nmdProfileSetString.indexOf(",") > -1) {
                            profileSets = nmdProfileSetString.tokenize(",")?.toList()
                        } else {
                            profileSets.add(nmdProfileSetString)
                        }
                    }

                    Map<Integer, Map<String, Integer>> thicknessesByProfileSet = [:]
                    Integer date
                    profileSets.each { String profileSet ->
                        List<Integer> profileSetIdX1X2 = profileSet.tokenize(":")?.toList()?.collect({it.toDouble().intValue()})

                        if (profileSetIdX1X2?.size() == 4) {
                            thicknessesByProfileSet.put((profileSetIdX1X2[0]), [X1: profileSetIdX1X2[1], X2: profileSetIdX1X2[2]])
                            date = profileSetIdX1X2[3]
                        } else {
                            // TODO: what to do when params are wrong? Could this happen?
                            log.error("NMD API: Error when opening the edit modal.. should not happen")
                        }
                    }

                    List<Nmd3ProfielsetsEnSchalingBijProduct2> profielsetsEnSchalingBijProduct2s = nmdApiService.getProfielsetsEnSchalingBijProduct2(resource.nmdProductId, date?.toString())

                    if (profielsetsEnSchalingBijProduct2s == null) {
                        throw new Exception("No response from NMD 3 API")
                    }

                    if (profielsetsEnSchalingBijProduct2s) {
                        Integer i = 1
                        body = "${body}<table class=\"table-striped table\"><thead><tr><th>Nr.</th><th>ProfileSet</th><th>SchalingsMaat dim 1</th><th>SchalingsMaat dim 2</th></tr></thead><tbody>"
                        profielsetsEnSchalingBijProduct2s.each { Nmd3ProfielsetsEnSchalingBijProduct2 profielsetsEnSchalingBijProduct2 ->
                            Boolean userEditable = profielsetsEnSchalingBijProduct2.getIsSchaalbaar()
                            Map<String, Integer> thicknessesForProfileSet = thicknessesByProfileSet.get(profielsetsEnSchalingBijProduct2.getProfielSetID())

                            String scalingRangeX1 = nmdApiService.getSchalingRangeTextX1(profielsetsEnSchalingBijProduct2)
                            String scalingRangeX2 = nmdApiService.getSchalingRangeTextX2(profielsetsEnSchalingBijProduct2)
                            body = "${body}<tr data-profileSetId=\"${profielsetsEnSchalingBijProduct2.getProfielSetID()}\" class=\"profileSet${resource.nmdProductId}\"><td>${i}</td><td ${userEditable ? "" : "colspan=\"3\""}>${profielsetsEnSchalingBijProduct2.getProfielSetNaam()}</td><td style=\"${userEditable ? "" : "display: none;"}\">SchalingsMaat dim 1 ${scalingRangeX1 ?: ""}: <input type\"text\" data-max=\"${profielsetsEnSchalingBijProduct2.getSchalingMaxX1() ?: ""}\" data-min=\"${profielsetsEnSchalingBijProduct2.getSchalingMinX1() ?: ""}\" class=\"nmd3X1\" value=\"${thicknessesForProfileSet?.get("X1") ?: profielsetsEnSchalingBijProduct2.getSchalingsMaat_X1() ? profielsetsEnSchalingBijProduct2.getSchalingsMaat_X1().intValue() : ""}\"/></td><td style=\"${userEditable ? "" : "display: none;"}\">SchalingsMaat dim 2 ${scalingRangeX2 ?: ""}: <input data-max=\"${profielsetsEnSchalingBijProduct2.getSchalingMaxX2() ?: ""}\" data-min=\"${profielsetsEnSchalingBijProduct2.getSchalingMinX2() ?: ""}\" type\"text\" class=\"nmd3X2\" value=\"${thicknessesForProfileSet?.get("X2") ?: profielsetsEnSchalingBijProduct2.getSchalingsMaat_X2() ? profielsetsEnSchalingBijProduct2.getSchalingsMaat_X2().intValue() : ""}\"/></td>"
                            i++
                        }
                        body = "${body}</tbody></table>"
                        body = "${body}<div style=\"width: 100%; text-align:center;\"><a href=\"javascript:\" onclick=\"updateNmdConstruction('${rowId}', '${resource.nmdProductId}', '${date ?: new SimpleDateFormat("yyyyMMdd").format(new Date())}')\" class=\"btn btn-primary\">Update construction</a></div>"
                    }
                } catch(Exception e) {
                    heading = "<h1>The NMD 3 Database is down, try again later.</h1>"
                    body = ""
                }
            }
        }
        render([heading: heading, body: body, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }
     */

    /* REL-304 deprecate nmd3ElementId >>> no need for this end point any more
    def getAddModal() {
        String heading = ""
        String body = ""

        Integer elementId = params.int("nmd3ElementID")
        String constructionId = params.constructionId
        String uniqueConstructionIdentifier = params.uniqueConstructionIdentifier
        String entityId = params.entityId
        String selectId = params.selectId
        String indicatorId = params.indicatorId
        String queryId = params.queryId
        String sectionId = params.sectionId
        String questionId = params.questionId
        String resourceTableId = params.resourceTableId
        String fieldName = params.fieldName

        if (elementId && constructionId) {
            def construction = constructionService.getConstructionAsDocument(constructionId, [mirrorResourceId: 1, nameNL: 1])
            String constructionName = construction?.nameNL
            String mirrorResourceId = construction?.mirrorResourceId

            if (constructionName) {
                heading = "<h1>${constructionName}</h1>"
            }
            String date = new SimpleDateFormat("yyyyMMdd").format(new Date())

            try {
                log.info("NMD3 API ADD MODAL: Callin productenBijElements with elementId: ${elementId}")
                List<Nmd3ProductenBijElement> allProductenBijElements = nmdApiService.getProductenBijElements(elementId, date)
                List<Nmd3ProductenBijElement> productenBijElements = allProductenBijElements?.findAll({it.getIsElementDekkend() && elementId.equals(it.getElementID())})
                List<Nmd3ProductenBijElement> nonTotalProductenBijElements = allProductenBijElements?.findAll({elementId.equals(it.getElementID()) && !it.getIsElementDekkend()})

                log.info("NMD3 API ADD MODAL: Got ${productenBijElements?.size()} total productenBijElements")
                log.info("NMD3 API ADD MODAL: Got ${nonTotalProductenBijElements?.size()} non total productenBijElements")

                if (allProductenBijElements == null) {
                    throw new Exception("No response from NMD 3 API")
                }

                log.info("NMD3 API ADD MODAL: Callin elementOnderdelens with elementId: ${elementId}")
                List<Nmd3ElementOnderdelen> elementOnderdelens = nmdApiService.getElementOnderdelens(elementId, date)
                log.info("NMD3 API ADD MODAL: Got ${elementOnderdelens?.size()} elementOnderdelens")

                if (elementOnderdelens == null) {
                    throw new Exception("No response from NMD 3 API")
                }


                Boolean totalProductsAvailable = productenBijElements ? true : false

                body = "${body}<table class=\"table-striped table\"><tbody>"
                Integer i = 1

                if (totalProductsAvailable) {
                    body = "${body}<tr><td colspan=\"3\"><strong>Totaal producten:</strong></td><td><strong>Geselecteerd</strong></td></tr>"

                    List<Integer> productIds = productenBijElements.collect({it.getProductID()})
                    log.info("NMD3 API ADD MODAL: Callin getProfielsetsEnSchalingBijProduct2 with ${productIds?.size()} productIds: ${productIds}")
                    List<Nmd3ProfielsetsEnSchalingBijProduct2> profielsetsEnSchalingBijProduct2s = []

                    productIds?.each { Integer productId ->
                        List<Nmd3ProfielsetsEnSchalingBijProduct2> profielsetsEnSchalingBijProduct2 = nmdApiService.getProfielsetsEnSchalingBijProduct2(productId, date)

                        if (profielsetsEnSchalingBijProduct2 == null) {
                            throw new Exception("No response from NMD 3 API")
                        } else if (!profielsetsEnSchalingBijProduct2.isEmpty()) {
                            profielsetsEnSchalingBijProduct2.each {
                                it.connectedProductID = productId
                                profielsetsEnSchalingBijProduct2s.add(it)
                            }
                        }
                    }
                    // getProfielsetsEnSchalingBijProduct2
                    log.info("NMD3 API ADD MODAL: Got ${profielsetsEnSchalingBijProduct2s?.size()} profielsetsEnSchalingBijProduct2s")

                    productenBijElements.each { Nmd3ProductenBijElement nmd3ProductenBijElement ->
                        Resource resource = nmdApiService.createNMDTotalResource(nmd3ProductenBijElement, date)

                        if (resource) {
                            body = "${body}<tr class=\"nmd3TotalRow\" data-productId=\"${nmd3ProductenBijElement.getProductID()}\" data-resourceId=\"${resource.resourceId}\" data-profileId=\"${resource.profileId}\"><td>${i}</td><td class=\"totalProductName\">${nmd3ProductenBijElement.getProductNaam()}</td><td>${nmd3ProductenBijElement.getAantalProfielSets()}</td><td><input style=\"width: 25px; !important; height: 17px; !important;\" type=\"checkbox\"${i == 1 ? ' checked=\"checked\" ' : ''} onchange=\"toggleNmdElements(this)\" class=\"nmd3TotalProductCheckbox\" value=\"true\" /></td></tr>"

                            List<Nmd3ProfielsetsEnSchalingBijProduct2> productsForThisTotalProduct = profielsetsEnSchalingBijProduct2s.findAll({nmd3ProductenBijElement.getProductID()?.equals(it.connectedProductID)})

                            if (productsForThisTotalProduct) {
                                productsForThisTotalProduct.each { Nmd3ProfielsetsEnSchalingBijProduct2 profielsetsEnSchalingBijProduct2 ->
                                    Boolean userEditable = profielsetsEnSchalingBijProduct2.getIsSchaalbaar()
                                    String scalingRangeX1 = nmdApiService.getSchalingRangeTextX1(profielsetsEnSchalingBijProduct2)
                                    String scalingRangeX2 = nmdApiService.getSchalingRangeTextX2(profielsetsEnSchalingBijProduct2)
                                    body = "${body}<tr data-profileSetId=\"${profielsetsEnSchalingBijProduct2.getProfielSetID()}\" class=\"profileSet${nmd3ProductenBijElement.getProductID()}\"><td><i style=\"font-size: 1.25em;\" class=\"fas fa-level-up-alt fa-rotate-90\"></i></td><td ${userEditable ? "" : "colspan=\"3\""}>${profielsetsEnSchalingBijProduct2.getProfielSetNaam()}</td><td style=\"${userEditable ? "" : "display: none;"}\">SchalingsMaat dim 1 ${scalingRangeX1 ?: ""}: <input type\"text\" data-max=\"${profielsetsEnSchalingBijProduct2.getSchalingMaxX1() ?: ""}\" data-min=\"${profielsetsEnSchalingBijProduct2.getSchalingMinX1() ?: ""}\" class=\"nmd3X1\" value=\"${profielsetsEnSchalingBijProduct2.getSchalingsMaat_X1() ? profielsetsEnSchalingBijProduct2.getSchalingsMaat_X1().intValue() : ""}\"/></td><td style=\"${userEditable ? "" : "display: none;"}\">SchalingsMaat dim 2 ${scalingRangeX2 ?: ""}: <input data-max=\"${profielsetsEnSchalingBijProduct2.getSchalingMaxX2() ?: ""}\" data-min=\"${profielsetsEnSchalingBijProduct2.getSchalingMinX2() ?: ""}\" type\"text\" class=\"nmd3X2\" value=\"${profielsetsEnSchalingBijProduct2.getSchalingsMaat_X2() ? profielsetsEnSchalingBijProduct2.getSchalingsMaat_X2().intValue() : ""}\"/></td>"
                                }
                            }
                            i++
                        }
                    }
                }
                i = 1

                if (nonTotalProductenBijElements) {
                    body = "${body}<tr class=\"nmd3ComponentHeader\"><td colspan=\"3\"><strong>Deelproducten:</strong></td><td><strong>Verwijderen</strong></td></tr>"
                    body = "${body}<tr class=\"nmd3ComponentSelect${totalProductsAvailable ? " satisfiedNmdRow" : ""}\"><td colspan=\"4\"> "
                    body = "${body}<select onchange=\"addNonTotalProductAndSatisfy(this)\" id=\"nonTotalSelector\" class=\"nonTotalSelector\">"
                    body = "${body}<option value=\"\" disabled selected>Selecteer deelproducten</option>"

                    nonTotalProductenBijElements.each { Nmd3ProductenBijElement productenBijElement ->
                        body = "${body}<option value=\"${productenBijElement.getProductID()}\" data-productId=\"${productenBijElement.getProductID()}\" >${productenBijElement.getProductNaam()}</option>"
                    }
                    body = "${body}</select></td></tr>"
                }

                if (elementOnderdelens) {
                    body = "${body}<tr class=\"nmd3ComponentHeader\"><td colspan=\"2\"><strong>Gedeeltelijke producten:</strong></td><td><strong>Bedekt door</strong></td><td></td></tr>"

                    List<Integer> bijElementIds = elementOnderdelens.collect({it.getElementID()})
                    List<Nmd3ProductenBijElement> allOnderdelenProductenBijElements = []

                    // TODO: Select for freeriders is useless?
                    /*bijElementIds.each { Integer elemId ->
                        if (elemId != null) {
                            log.info("NMD3 API ADD MODAL: Callin productenBijElements with elementId: ${elemId}")
                            List<Nmd3ProductenBijElement> onderdelenProductenBijElements = nmdApiService.getProductenBijElements(elemId, null, token)
                            log.info("NMD3 API ADD MODAL: Got ${onderdelenProductenBijElements?.size()} onderdelenProductenBijElements")

                            if (onderdelenProductenBijElements == null) {
                                throw new Exception("No response from NMD 3 API")
                            } else {
                                onderdelenProductenBijElements.each {
                                    it.connectedElementID = elemId
                                    allOnderdelenProductenBijElements.add(it)
                                }
                            }
                        }
                    } // NOTE there was a comment block ending here

                    elementOnderdelens.each { Nmd3ElementOnderdelen nmd3ElementOnderdelen ->
                        String CUAS = nmdApiService.getCUASFromElementOnderdelen(nmd3ElementOnderdelen)

                        body = "${body}<tr id=\"nmd3ComponentRow${nmd3ElementOnderdelen.getElementID()}\" class=\"nmd3ComponentRow${totalProductsAvailable ? " satisfiedNmdRow" : ""}\"><td>${i}</td><td>${CUAS ? "${CUAS}: ": ""}${nmd3ElementOnderdelen.getElementNaam()}${nmd3ElementOnderdelen.getVerplicht() ? " (*)" : ""}</td><td>"

                        if (totalProductsAvailable) {
                            body = "${body}<span class=\"coveredBy\"><span class=\"elementCoveringProducts\"><div class=\"elementCoveringProductTOTAL\">${productenBijElements.first().getProductNaam()}</div></span></span>"
                        } else {
                            body = "${body}<span class=\"coveredBy\">Niet gedekt</span>"
                        }
                        body = "${body}</td><td class=\"coveredByMultipleWarning\"></td></tr>"
                        i++


                        // TODO: Select for freeriders is useless?
                        /*List<Nmd3ProductenBijElement> onderdelenProductenBijElements = allOnderdelenProductenBijElements.findAll({nmd3ElementOnderdelen.getElementID()?.equals(it.getConnectedElementID())})

                        log.info("Bij elements: ${onderdelenProductenBijElements?.size()} for ElementId: ${nmd3ElementOnderdelen.getElementID()}, ownerElemId: ${nmd3ElementOnderdelen.getOuderID()}")

                        List<Integer> productIds = onderdelenProductenBijElements?.collect({it.getProductID()})

                        if (productIds) {
                            // Generate row only if even has product profiles.
                            body = "${body}<tr id=\"nmd3ComponentRow${nmd3ElementOnderdelen.getElementID()}\" class=\"nmd3ComponentRow${totalProductsAvailable ? " satisfiedNmdRow" : ""}\"><td>${i}</td><td>${CUAS ? "${CUAS}: ": ""}${nmd3ElementOnderdelen.getElementNaam()}${nmd3ElementOnderdelen.getVerplicht() ? " (*)" : ""}</td><td>"

                            if (onderdelenProductenBijElements) {
                                body = "${body}<select onchange=\"showLinkedProductRowAndSatisfy(this, '${nmd3ElementOnderdelen.getElementID()}', '${token}')\" class=\"kikkareselector\">"
                                body = "${body}<option value=\"\"></option>"

                                onderdelenProductenBijElements.each { Nmd3ProductenBijElement productenBijElement ->
                                    body = "${body}<option value=\"${productenBijElement.getProductID()}\" data-productId=\"${productenBijElement.getProductID()}\">${productenBijElement.getProductNaam()}</option>"
                                }
                                body = "${body}</select>"
                            }
                            body = "${body}</td><td></td></tr>"
                            i++
                        } // NOTE there was a comment block ending here
                    }
                }
                body = "${body}</tbody></table>"
                body = "${body}<div style=\"width: 100%; text-align:center;\"><a href=\"javascript:\" id=\"addNmdConstructionButton\" onclick=\"addNmdConstruction('${mirrorResourceId}', '${constructionId}','${uniqueConstructionIdentifier}','${entityId}','${selectId}','${indicatorId}','${queryId}','${sectionId}','${questionId}','${resourceTableId}','${fieldName}', '${date}')\" class=\"btn btn-primary\">Add construction</a></div>"
            } catch (Exception e) {
                heading = "<h1>The NMD 3 Database is down, try again later.</h1>"
                body = ""
            }
        }
        render([heading: heading, body: body, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }
 */
    @Deprecated
    def loadProfileSets() {
        String toRender = ""
        String elementId = params.elementId
        Integer productId = params.int("productId")
        String resourceId
        String profileId

        if (elementId && productId) {
            String date = new SimpleDateFormat("yyyyMMdd").format(new Date())
            List<Nmd3VolledigProductBijProduct> volledigProductBijProducts = nmdApiService.getVolledigProductBijProduct(productId, date)

            if (volledigProductBijProducts) {
                List<Integer> productIds = volledigProductBijProducts.collect({it.getProductID()})

                List<Nmd3ProductenProfielWaarden> productenProfielWaardens = nmdApiService.getProductenProfielWaardens(null, date, productIds)

                if (productenProfielWaardens) {
                    Boolean hasImpacts = productenProfielWaardens.find({Nmd3ProductenProfielWaarden p -> p.getProductID() == productId}) ? true : false

                    if (hasImpacts) {
                        productenProfielWaardens = productenProfielWaardens.findAll({Nmd3ProductenProfielWaarden p -> p.getProductID() == productId})
                    }

                    log.info("Here 2: ${productenProfielWaardens?.size()}, selected child hasImpacts: ${hasImpacts}")
                    productenProfielWaardens.each { Nmd3ProductenProfielWaarden productenProfielWaarden ->
                        Nmd3VolledigProductBijProduct volledigProductBijProduct = volledigProductBijProducts.find({it.getProductID() == productenProfielWaarden.getProductID()})

                        if (volledigProductBijProduct) {
                            Nmd3ProductenBijElement nmd3ProductenBijElement = nmdApiService.getProductenBijElements(volledigProductBijProduct.getElementID(), date)?.find({it.getProductID() == productenProfielWaarden.getProductID()})
                            log.info("Here 3")
                            if (nmd3ProductenBijElement) {
                                Resource resource = nmdApiService.createNMDTotalResource(nmd3ProductenBijElement, date)
                                resourceId = resource.resourceId
                                profileId = resource.profileId

                                log.info("Here 4")

                                List<Nmd3ProfielsetsEnSchalingBijProduct2> profielsetsEnSchalingBijProduct2s = nmdApiService.getProfielsetsEnSchalingBijProduct2(productenProfielWaarden.getProductID(), date)

                                if (profielsetsEnSchalingBijProduct2s) {
                                    profielsetsEnSchalingBijProduct2s.each { Nmd3ProfielsetsEnSchalingBijProduct2 profielsetsEnSchalingBijProduct2 ->
                                        Boolean userEditable = profielsetsEnSchalingBijProduct2.getIsSchaalbaar()
                                        String scalingRangeX1 = nmdApiService.getSchalingRangeTextX1(profielsetsEnSchalingBijProduct2)
                                        String scalingRangeX2 = nmdApiService.getSchalingRangeTextX2(profielsetsEnSchalingBijProduct2)
                                        toRender = "${toRender}<tr data-profileSetId=\"${profielsetsEnSchalingBijProduct2.getProfielSetID()}\" class=\"nmd3ComponentPartRow partialProfileSet${productenProfielWaarden.getProductID()} partialElementId${elementId}\"><td><i style=\"font-size: 1.25em;\" class=\"fas fa-level-up-alt fa-rotate-90\"></i></td><td ${userEditable ? "" : "colspan=\"3\""}>${profielsetsEnSchalingBijProduct2.getProfielSetNaam()}</td><td style=\"${userEditable ? "" : "display: none;"}\">SchalingsMaat dim 1 ${scalingRangeX1 ?: ""}: <input type\"text\" data-max=\"${profielsetsEnSchalingBijProduct2.getSchalingMaxX1() ?: ""}\" data-min=\"${profielsetsEnSchalingBijProduct2.getSchalingMinX1() ?: ""}\" class=\"nmd3X1\" value=\"${profielsetsEnSchalingBijProduct2.getSchalingsMaat_X1() ? profielsetsEnSchalingBijProduct2.getSchalingsMaat_X1().intValue() : ""}\"/></td><td style=\"${userEditable ? "" : "display: none;"}\">SchalingsMaat dim 2 ${scalingRangeX2 ?: ""}: <input data-max=\"${profielsetsEnSchalingBijProduct2.getSchalingMaxX2() ?: ""}\" data-min=\"${profielsetsEnSchalingBijProduct2.getSchalingMinX2() ?: ""}\" type\"text\" class=\"nmd3X2\" value=\"${profielsetsEnSchalingBijProduct2.getSchalingsMaat_X2() ? profielsetsEnSchalingBijProduct2.getSchalingsMaat_X2().intValue() : ""}\"/></td>"
                                    }
                                } else {
                                    toRender = "<tr class='nmd3NoProductsFound'><td></td><td colspan='3'>No products found for this element.</td></tr>"
                                }
                            } else {
                                toRender = "<tr class='nmd3NoProductsFound'><td></td><td colspan='3'>No products found for this element.</td></tr>"
                            }
                        } else {
                            toRender = "<tr class='nmd3NoProductsFound'><td></td><td colspan='3'>No products found for this element.</td></tr>"
                        }
                    }
                } else {
                    toRender = "<tr class='nmd3NoProductsFound'><td></td><td colspan='3'>No products found for this element.</td></tr>"
                }
            } else {
                toRender = "<tr class='nmd3NoProductsFound'><td></td><td colspan='3'>No products found for this element.</td></tr>"
            }





            /*List<Nmd3ProfielsetsEnSchalingBijProduct2> profielsetsEnSchalingBijProduct2s = nmdApiService.getProfielsetsEnSchalingBijProduct2(productId, date, token)

            if (profielsetsEnSchalingBijProduct2s) {
                profielsetsEnSchalingBijProduct2s.each { Nmd3ProfielsetsEnSchalingBijProduct2 profielsetsEnSchalingBijProduct2 ->
                    Boolean userEditable = profielsetsEnSchalingBijProduct2.getIsSchaalbaar()
                    String scalingRangeX1 = nmdApiService.getSchalingRangeTextX1(profielsetsEnSchalingBijProduct2)
                    String scalingRangeX2 = nmdApiService.getSchalingRangeTextX2(profielsetsEnSchalingBijProduct2)
                    toRender = "${toRender}<tr data-profileSetId=\"${profielsetsEnSchalingBijProduct2.getProfielSetID()}\" class=\"nmd3ComponentPartRow partialProfileSet${productId} partialElementId${elementId}\"><td><i style=\"font-size: 1.25em;\" class=\"fas fa-level-up-alt fa-rotate-90\"></i></td><td ${userEditable ? "" : "colspan=\"3\""}>${profielsetsEnSchalingBijProduct2.getProfielSetNaam()}</td><td style=\"${userEditable ? "" : "display: none;"}\">SchalingsMaat dim 1 ${scalingRangeX1 ?: ""}: <input type\"text\" data-max=\"${profielsetsEnSchalingBijProduct2.getSchalingMaxX1() ?: ""}\" data-min=\"${profielsetsEnSchalingBijProduct2.getSchalingMinX1() ?: ""}\" class=\"nmd3X1\" value=\"${profielsetsEnSchalingBijProduct2.getSchalingsMaat_X1() ? profielsetsEnSchalingBijProduct2.getSchalingsMaat_X1().intValue() : ""}\"/></td><td style=\"${userEditable ? "" : "display: none;"}\">SchalingsMaat dim 2 ${scalingRangeX2 ?: ""}: <input data-max=\"${profielsetsEnSchalingBijProduct2.getSchalingMaxX2() ?: ""}\" data-min=\"${profielsetsEnSchalingBijProduct2.getSchalingMinX2() ?: ""}\" type\"text\" class=\"nmd3X2\" value=\"${profielsetsEnSchalingBijProduct2.getSchalingsMaat_X2() ? profielsetsEnSchalingBijProduct2.getSchalingsMaat_X2().intValue() : ""}\"/></td>"
                }
            } else {
                toRender = "<tr class='nmd3NoProductsFound'><td></td><td colspan='3'>No products found for this element.</td></tr>"
            }*/
        }
        render([toRender: toRender, resourceId: resourceId, profileId: profileId] as JSON)
    }

    @Deprecated
    def loadPartialProfileSets() {
        String toRender = ""
        Integer productId = params.int("productId")
        List<Integer> coveredElementIds = []
        String coveredBy

        if (productId) {
            String date = new SimpleDateFormat("yyyyMMdd").format(new Date())
            List<Nmd3VolledigProductBijProduct> volledigProductBijProducts = nmdApiService.getVolledigProductBijProduct(productId, date)

            if (volledigProductBijProducts) {
                coveredElementIds = volledigProductBijProducts.collect({it.getElementID()})
                List<Integer> productIds = volledigProductBijProducts.collect({it.getProductID()})

                log.info("Here 1: ${volledigProductBijProducts?.size()}")

                List<Nmd3ProductenProfielWaarden> productenProfielWaardens = nmdApiService.getProductenProfielWaardens(null, date, productIds)

                if (productenProfielWaardens) {
                    log.info("Here 2: ${productenProfielWaardens?.size()}")

                    productenProfielWaardens.each { Nmd3ProductenProfielWaarden productenProfielWaarden ->
                        Nmd3VolledigProductBijProduct volledigProductBijProduct = volledigProductBijProducts.find({it.getProductID() == productenProfielWaarden.getProductID()})

                        if (volledigProductBijProduct) {
                            List<Nmd3ProfielsetsEnSchalingBijProduct2> profielsetsEnSchalingBijProduct2s = nmdApiService.getProfielsetsEnSchalingBijProduct2(productenProfielWaarden.getProductID(), date)?.findAll({it.getHoeveelheid() && it.getHoeveelheid() > 0})

                            if (profielsetsEnSchalingBijProduct2s) {
                                Resource resource = nmdApiService.createNMDTotalResource(null, date, volledigProductBijProduct)
                                toRender = "${toRender}<tr data-productId=\"${productenProfielWaarden.getProductID()}\" data-coveredElementIds=\"${coveredElementIds ? coveredElementIds.join(',') : ""}\" class=\"nmd3PartialComponentRow\" data-resourceId=\"${resource.resourceId}\" data-profileId=\"${resource.profileId}\"><td></td><td class='partialProductName' colspan='2'>${volledigProductBijProduct.getProductNaam()}</td><td><a href=\"javascript:\" onclick=\"deletePartialComponentRows(this, '${productenProfielWaarden.getProductID()}')\">Delete</td></td><tr>"

                                profielsetsEnSchalingBijProduct2s.each { Nmd3ProfielsetsEnSchalingBijProduct2 profielsetsEnSchalingBijProduct2 ->
                                    Boolean userEditable = profielsetsEnSchalingBijProduct2.getIsSchaalbaar()
                                    String scalingRangeX1 = nmdApiService.getSchalingRangeTextX1(profielsetsEnSchalingBijProduct2)
                                    String scalingRangeX2 = nmdApiService.getSchalingRangeTextX2(profielsetsEnSchalingBijProduct2)
                                    toRender = "${toRender}<tr data-profileSetId=\"${profielsetsEnSchalingBijProduct2.getProfielSetID()}\" class=\"nmd3PartialComponentPartRow partialComponentProfileSet${productenProfielWaarden.getProductID()} partialComponentElementId${volledigProductBijProduct.getElementID()}\"><td><i style=\"font-size: 1.25em;\" class=\"fas fa-level-up-alt fa-rotate-90\"></i></td><td ${userEditable ? "" : "colspan=\"3\""}>${profielsetsEnSchalingBijProduct2.getProfielSetNaam()}</td><td style=\"${userEditable ? "" : "display: none;"}\">SchalingsMaat dim 1 ${scalingRangeX1 ?: ""}: <input type\"text\" data-max=\"${profielsetsEnSchalingBijProduct2.getSchalingMaxX1() ?: ""}\" data-min=\"${profielsetsEnSchalingBijProduct2.getSchalingMinX1() ?: ""}\" class=\"nmd3X1\" value=\"${profielsetsEnSchalingBijProduct2.getSchalingsMaat_X1() ? profielsetsEnSchalingBijProduct2.getSchalingsMaat_X1().intValue() : ""}\"/></td><td style=\"${userEditable ? "" : "display: none;"}\">SchalingsMaat dim 2 ${scalingRangeX2 ?: ""}: <input data-max=\"${profielsetsEnSchalingBijProduct2.getSchalingMaxX2() ?: ""}\" data-min=\"${profielsetsEnSchalingBijProduct2.getSchalingMinX2() ?: ""}\" type\"text\" class=\"nmd3X2\" value=\"${profielsetsEnSchalingBijProduct2.getSchalingsMaat_X2() ? profielsetsEnSchalingBijProduct2.getSchalingsMaat_X2().intValue() : ""}\"/></td>"
                                }

                                if (coveredBy) {
                                    coveredBy = "${coveredBy}<br/><div class=\"elementCoveringProduct${productenProfielWaarden.getProductID()} margin-bottom-10\">${volledigProductBijProduct.getProductNaam()}</div>"
                                } else {
                                    coveredBy = "<span class=\"elementCoveringProducts\">"
                                    coveredBy = "${coveredBy}<div class=\"elementCoveringProduct${productenProfielWaarden.getProductID()} margin-bottom-10\">${volledigProductBijProduct.getProductNaam()}</div>"
                                }
                            }
                        }
                    }

                    if (coveredBy) {
                        coveredBy = "${coveredBy}</span>"
                    }
                } else {
                    toRender = "<tr class='nmd3NoProductsFound'><td></td><td colspan='3'>No products found for this element.</td></tr>"
                }
            } else {
                toRender = "<tr class='nmd3NoProductsFound'><td></td><td colspan='3'>No products found for this element.</td></tr>"
            }
        }
        if (!toRender) {
            toRender = "<tr class='nmd3NoProductsFound'><td></td><td colspan='3'>No products found for this element.</td></tr>"
        }
        log.info("Done!")
        render([toRender: toRender, coveredElementIds: coveredElementIds, coveredBy: coveredBy, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def getUpdateForDate(){
        String date = request.JSON.date

        JSONObject response = nmdApiService.getNMDUpdateByDate(date)
        render([response: response, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)

    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def triggerManualUpdate() {
        String startFromDate = params.date
        boolean startFromScratch = params.boolean('startFromScratch')
        if (startFromScratch) {
            startFromDate = NmdApiService.NMD_FALLBACK_DATE
        }
        String response = nmdApiService.getNMDDailyUpdate(startFromDate)
        render([output: response, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def clearNmdUpdate() {
        NmdUpdate.collection.deleteMany([_id: [$exists: true]])
        render([output: 'ok', (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def testNmdConfigRemoval() {
        String testString = request.JSON.testString
        String source = request.JSON.source
        String target = request.JSON.target
        render([output: testString?.replaceAll(source, target), (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }
}