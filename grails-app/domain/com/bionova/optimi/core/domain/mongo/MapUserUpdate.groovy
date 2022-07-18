package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class MapUserUpdate implements Comparable {
    String userName
    Date update

    @Override
    int compareTo(Object obj) {
        if (obj instanceof MapUserUpdate && obj.update && this.update) {
            return this.update.before(obj.update) ? 1 : 0
        } else {
            return 0
        }
    }
    @Override
    String toString (){
        return userName + " - Update date: " + update
    }
}
