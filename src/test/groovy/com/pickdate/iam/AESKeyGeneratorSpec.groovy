package com.pickdate.iam

import spock.lang.Specification


class AESKeyGeneratorSpec extends Specification {

    def "should generate aes key"() {
        when:
        def key = AESKeyGenerator.generateKey()

        then:
        def instance = AESEncryptor.create(key)
        def encrypted = instance.encrypt("test")
        def decrypted = instance.decrypt(encrypted)

        and:
        decrypted == "test"
    }
}
