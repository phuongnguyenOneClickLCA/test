package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.frenchTools.FrenchConstants
import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse
import grails.web.servlet.mvc.GrailsParameterMap
import org.grails.web.json.JSONObject

class Re2020ApiService {

    static final String DEMANDEURS_API_ENDPOINT = "https://mdegd.dimn-cstb.fr/api/demandeurs/"
    static final String DEMANDES_API_ENDPOINT = "https://mdegd.dimn-cstb.fr/api/demandes/"

    static final List<String> DEMANDEURS_PARAMS = ["nom", "prenom", "metier", "mail", "telephone", "entreprise"]
    static final List<String> DEMANDES_PARAMS = ["creationArgument", "creationIdentification", "creationLabel", "creationSource", "creationUf", "creationRankId", "creationRankName", "creationRankParent", "nomenclature"]

    transient loggerUtil

    /**
     * Prepares the API request to send to French RE2020 endpoint
     * in order to get a new user property idDemandeur which is required
     * to send the data request to RE2020
     * @param params : map from the HTML form
     * @return JSON object with properties msg and id_demandeur
     */
    JSONObject sendDemandeurRequest(GrailsParameterMap params, User user) {
        JSONObject response
        params.removeAll { !DEMANDEURS_PARAMS.contains(it.key) }

        try {
            response = callDemandeursApi(params)
            Integer id_demandeur = response?.get(FrenchConstants.ID_DEMANDEUR)
            if (id_demandeur) {
                user.idDemandeur = id_demandeur
                user.save(flush: true)
            }

        } catch (Exception e) {
            response = new JSONObject()
            response.put("msg": "Error when fetching API")
            loggerUtil.error(log, "Error in fetching INIES API response: " + e)
        }

        return response
    }

    /**
     * Sends API request to RE2020 Demandeur endpoint
     * @param params : filtered out map to be used as payload
     * @return JSON object
     */
    private JSONObject callDemandeursApi(GrailsParameterMap params) {

        RestBuilder rest = new RestBuilder()
        RestResponse demandeursIdResponse = rest.post(DEMANDEURS_API_ENDPOINT) {
            contentType("application/json")
            json {
                params
            }
        }

        return demandeursIdResponse?.json
    }

    /**
     * Prepares the API request to send to French RE2020 endpoint
     * in order to request INIES data
     * @param params : map from the HTML form
     * @return JSON object with properties msg and id_demandes
     */
    JSONObject sendDemandesRequest(GrailsParameterMap params, Integer idDemandeur) {
        JSONObject response
        params.removeAll { !DEMANDES_PARAMS.contains(it.key) }

        try {
            response = callDemandesApi(params, idDemandeur)
        } catch (Exception e) {
            response = new JSONObject()
            response.put("msg": "Error when fetching API")
            loggerUtil.error(log, "Error in fetching INIES API response: " + e)
        }

        return response
    }

    /**
     * Sends API request to RE2020 INIES Demandes endpoint
     * @param params : filtered out map to be used as payload
     * @return JSON object with request number
     */
    private JSONObject callDemandesApi(GrailsParameterMap params, Integer idDemandeur) {

        RestBuilder rest = new RestBuilder()
        RestResponse demandesIdResponse
        LinkedHashMap<String, Serializable> payload

        if ("true".equals(params.nomenclature)) {
            payload = [content     : [creationArgument      : params.creationArgument,
                                      creationIdentification: params.creationIdentification,
                                      creationLabel         : params.creationLabel,
                                      creationRank          : [
                                              creationIsNewRank: false,
                                              creationRankId   : params.creationRankId
                                      ],
                                      creationSource        : params.creationSource,
                                      creationUf            : params.creationUf],
                       id_demandeur: idDemandeur,
                       nature      : "Creation"]
        } else {
            payload = [content     : [creationArgument      : params.creationArgument,
                                      creationIdentification: params.creationIdentification,
                                      creationLabel         : params.creationLabel,
                                      creationRank          : [
                                              creationIsNewRank : true,
                                              creationRankName  : params.creationRankName,
                                              creationRankParent: params.creationRankParent
                                      ],
                                      creationSource        : params.creationSource,
                                      creationUf            : params.creationUf],
                       id_demandeur: idDemandeur,
                       nature      : "Creation"]
        }

        demandesIdResponse = rest.post(DEMANDES_API_ENDPOINT) {
            contentType("application/json")
            json {
                payload
            }
        }

        return demandesIdResponse?.json
    }

}
