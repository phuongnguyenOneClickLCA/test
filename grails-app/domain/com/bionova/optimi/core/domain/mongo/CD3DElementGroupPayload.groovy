package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class CD3DElementGroupPayload implements Serializable {

    String elementGroupId
    Map<String, String> elementGroupName
    List<String> includedElements

}
