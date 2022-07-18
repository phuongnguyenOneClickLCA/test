package com.bionova.optimi.core.domain.mongo

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import spock.lang.Unroll

class CarbonDesigner3DStructuralFrameSpec extends Specification implements DomainUnitTest<CarbonDesigner3DStructuralFrame> {

    void 'structuralFrameId cannot be null'() {
        when:
        domain.structuralFrameId = null

        then:
        !domain.validate(['structuralFrameId'])
        domain.errors['structuralFrameId'].code == 'nullable'
    }

    void 'columnsPresent cannot be null'() {
        when:
        domain.columnsPresent = null

        then:
        !domain.validate(['columnsPresent'])
        domain.errors['columnsPresent'].code == 'nullable'
    }

    void 'beamsPresent cannot be null'() {
        when:
        domain.beamsPresent = null

        then:
        !domain.validate(['beamsPresent'])
        domain.errors['beamsPresent'].code == 'nullable'
    }

    void 'secondaryBeamsFactor cannot be negative'() {
        when:
        domain.secondaryBeamsFactor = -5

        then:
        !domain.validate(['secondaryBeamsFactor'])
        domain.errors['secondaryBeamsFactor'].code == 'range.toosmall'
    }

    void 'secondaryBeamsFactor cannot be above 0.6'() {
        when:
        domain.secondaryBeamsFactor = 45

        then:
        !domain.validate(['secondaryBeamsFactor'])
        domain.errors['secondaryBeamsFactor'].code == 'range.toobig'
    }

    void 'secondaryBeamsFactor is within range'() {
        when:
        domain.secondaryBeamsFactor = 0.4

        then:
        domain.validate(['secondaryBeamsFactor'])
    }

    void 'shearWallsPercentage cannot be negative'() {
        when:
        domain.shearWallsPercentage = -3.45

        then:
        !domain.validate(['shearWallsPercentage'])
        domain.errors['shearWallsPercentage'].code == 'range.toosmall'
    }

    void 'shearWallsPercentage cannot be above 0.6'() {
        when:
        domain.shearWallsPercentage = 36

        then:
        !domain.validate(['shearWallsPercentage'])
        domain.errors['shearWallsPercentage'].code == 'range.toobig'
    }

    void 'shearWallsPercentage is within range'() {
        when:
        domain.shearWallsPercentage = 0.22

        then:
        domain.validate(['shearWallsPercentage'])
    }

    void 'loadBearingInternalWallShare cannot be negative'() {
        when:
        domain.loadBearingInternalWallShare = -3.45

        then:
        !domain.validate(['loadBearingInternalWallShare'])
        domain.errors['loadBearingInternalWallShare'].code == 'range.toosmall'
    }

    void 'loadBearingInternalWallShare cannot be above 100'() {
        when:
        domain.loadBearingInternalWallShare = 400

        then:
        !domain.validate(['loadBearingInternalWallShare'])
        domain.errors['loadBearingInternalWallShare'].code == 'range.toobig'
    }

    void 'loadBearingInternalWallShare is within range'() {
        when:
        domain.loadBearingInternalWallShare = value

        then:
        expected == domain.validate(['loadBearingInternalWallShare'])
        domain.errors['loadBearingInternalWallShare']?.code == expectedErrorCode

        where:
        value                  | expected | expectedErrorCode
        null                   |  true    | null
        0                      |  true    | null
        1                      |  true    | null
        0.2                    |  true    | null
        -5.3                   |  false   | "range.toosmall"
        101                    |  false   | "range.toobig"
    }
}
