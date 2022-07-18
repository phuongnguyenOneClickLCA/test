package com.bionova.optimi.core.service
import wslite.soap.SOAPClient
import wslite.soap.SOAPFaultException
import wslite.soap.SOAPResponse


class CustomSoapService {

    def flashService

    def validateVat(String memberStateCode, String vatNumberCode) {
        Map<String, String> responseContent = [:]
        String url = 'http://ec.europa.eu/taxation_customs/vies/services/checkVatService'
        SOAPClient client = new SOAPClient("${url}.wsdl")

        SOAPResponse response = null

        try {
            response = client.send(SOAPAction: url) {
                body('xmlns': 'urn:ec.europa.eu:taxud:vies:services:checkVat:types') {
                    checkVat {
                        countryCode(memberStateCode)
                        vatNumber(vatNumberCode)
                    }
                }
            }
        } catch (SOAPFaultException sfex) {
            log.error("SOAP call failed for countryCode ${memberStateCode} and vatNumber ${vatNumberCode}: ", sfex)
            flashService.setErrorAlert("SOAP call failed for countryCode ${memberStateCode} " +
                    "and vatNumber ${vatNumberCode}: ${sfex.getMessage()}", true)
        }

        Date requestDate = new Date()
        if(response) {
            responseContent.put("validity", response.checkVatResponse.valid?.toString())
            responseContent.put("companyName", response.checkVatResponse.name?.toString())
        }
        responseContent.put("requestDate", requestDate.format('dd.MM.yyyy').toString())
        return responseContent
    }
}
