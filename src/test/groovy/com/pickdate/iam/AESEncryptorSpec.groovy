package com.pickdate.iam

import spock.lang.Specification


class AESEncryptorSpec extends Specification {

    def instance = AESEncryptor.create("DphZg/2Ef/C9fdInlA7yTGiCFvaDfvqv")

    def "should create new instance"() {
        expect: "Creating encryptor with a key"
        AESEncryptor.create("80A27ACFE36ABB23C187AA5C290E7A7C")
    }

    def "should encrypt data"() {
        expect: "Encrypting null returns null"
        instance.encrypt(null) == null

        when: "Encrypting a non-null value"
        def plain = "hello world"
        def encrypted = instance.encrypt(plain)

        then: "Encrypted value is a Base64 string and differs from the input"
        encrypted !== null
        encrypted != plain
    }

    def "should decrypt data"() {
        given:
        def plain = "super secret password"
        def encrypted = instance.encrypt(plain)

        when: "Decrypting previously encrypted value"
        def decrypted = instance.decrypt(encrypted)

        then: "We get the original text back"
        decrypted == plain
    }
}
