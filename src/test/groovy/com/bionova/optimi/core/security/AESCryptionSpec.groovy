package com.bionova.optimi.core.security

import com.bionova.optimi.security.AESCryption
import spock.lang.Specification

class AESCryptionSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "encryption with random and decrypt with random"() {
        expect:
        String encrypted = AESCryption.encryptWithRandomString(data)
        assert encrypted != data
        assert AESCryption.decryptWithRandomString(encrypted) == data

        // where clause needs at least 2 parameters so adding dummy column.
        where:
        data                     | dummy
        '1.222'                  | 'dummy'
        '-2.222'                 | 'dummy'
        '0.333'                  | 'dummy'
        '32.7777'                | 'dummy'
        '12.2331321'             | 'dummy'
        '45.7343521'             | 'dummy'
        '355.333'                | 'dummy'
        '1000000000000000'       | 'dummy'
        '!2356713#&@#6*2'        | 'dummy'
        'SomeThingVerySecretive' | 'dummy'
    }

    void "encrypted text with random string should always be different for same data"() {
        expect:
        assert AESCryption.encryptWithRandomString(data) != AESCryption.encryptWithRandomString(data)

        // where clause needs at least 2 parameters so adding dummy column.
        where:
        data                     | dummy
        '1.222'                  | 'dummy'
        '-2.222'                 | 'dummy'
        '0.333'                  | 'dummy'
        '32.7777'                | 'dummy'
        '12.2331321'             | 'dummy'
        '45.7343521'             | 'dummy'
        '355.333'                | 'dummy'
        '1000000000000000'       | 'dummy'
        '!2356713#&@#6*2'        | 'dummy'
        'SomeThingVerySecretive' | 'dummy'
    }

    void "encrypted text without random string should always be SAME for same data"() {
        expect:
        assert AESCryption.encrypt(data) == AESCryption.encrypt(data)

        // where clause needs at least 2 parameters so adding dummy column.
        where:
        data                     | dummy
        '1.222'                  | 'dummy'
        '-2.222'                 | 'dummy'
        '0.333'                  | 'dummy'
        '32.7777'                | 'dummy'
        '12.2331321'             | 'dummy'
        '45.7343521'             | 'dummy'
        '355.333'                | 'dummy'
        '1000000000000000'       | 'dummy'
        '!2356713#&@#6*2'        | 'dummy'
        'SomeThingVerySecretive' | 'dummy'
    }
}