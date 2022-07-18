package com.bionova.optimi.core.domain.mongo

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class RoleMigrationMappingSpec extends Specification implements DomainUnitTest<RoleMigrationMapping> {

    void 'targetRole cannot be null'() {
        when:
        domain.targetRole = null

        then:
        !domain.validate(['targetRole'])
        domain.errors['targetRole'].code == 'nullable'
    }

    void 'username cannot be null'() {
        when:
        domain.targetRole = null

        then:
        !domain.validate(['username'])
        domain.errors['username'].code == 'nullable'
    }

    void 'targetRole name cannot be blank'() {
        when:
        domain.targetRole = ''

        then:
        !domain.validate(['targetRole'])
    }

    void 'username name cannot be blank'() {
        when:
        domain.targetRole = ''

        then:
        !domain.validate(['username'])
    }

    def "username unique constraint"() {

        when: 'You instantiate a roleMapping with sourceRole which has been never used before'
        RoleMigrationMapping roleMapping = new RoleMigrationMapping(username: 'testUser@bionova.fi', targetRole: 'ROLE_ORG_ADMIN')

        then: 'roleMapping is valid instance'
        roleMapping.validate()

        and: 'we can save it'
        roleMapping.save()

        and: 'there is one additional roleMapping'
        RoleMigrationMapping.count() == old(RoleMigrationMapping.count()) + 1

        when: 'RoleMigrationMapping with the same username'
        RoleMigrationMapping roleMappingDuplicate = new RoleMigrationMapping(username: 'testUser@bionova.fi', targetRole: 'ROLE_SUPER_USER')

        then: 'the roleMappingDuplicate instance is not valid'
        !roleMappingDuplicate.validate(['username'])

        and: 'unique error code is populated'
        roleMappingDuplicate.errors['username']?.code == 'unique'

        and: 'trying to save fails too'
        !roleMappingDuplicate.save()

        and: 'no roleMapping has been added'
        RoleMigrationMapping.count() == old(RoleMigrationMapping.count())

        when: 'RoleMigrationMapping with the same sourceRole but different user'
        RoleMigrationMapping roleMappingForOtherUser = new RoleMigrationMapping(username: 'testUser2@bionova.fi', targetRole: 'ROLE_SUPER_USER')

        then: 'the roleMappingForOtherUser instance is valid'
        roleMappingForOtherUser.validate()

        and: 'we can save it'
        roleMappingForOtherUser.save()

        and: 'roleMapping has been added'
        RoleMigrationMapping.count() == old(RoleMigrationMapping.count()) + 1
    }
}
