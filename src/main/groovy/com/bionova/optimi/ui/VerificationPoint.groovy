package com.bionova.optimi.ui

import groovy.transform.CompileStatic

@CompileStatic
class VerificationPoint implements Comparable {

    String pointName
    List<VPLocation> locations

    VerificationPoint() {
        pointName = new String()
        locations = new ArrayList<VPLocation>()
    }

    @Override
    int compareTo(Object obj) {
        if (obj && obj instanceof VerificationPoint) {
            if (this.pointName && obj.pointName) {
                return this.pointName <=> obj.pointName
            } else {
                return 1
            }
        } else {
            return 1
        }
    }
}
