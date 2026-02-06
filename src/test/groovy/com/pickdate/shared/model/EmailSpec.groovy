package com.pickdate.shared.model

import com.pickdate.shared.exception.IllegalValue
import spock.lang.Specification


class EmailSpec extends Specification {

    def "should throw exception when email is null"() {
        when:
        Email.of(null)

        then:
        thrown(IllegalValue)
    }

    def "should throw exception when email is blank"() {
        when:
        Email.of("")

        then:
        thrown(IllegalValue)
    }

    def "should throw exception when email is too short"() {
        given:
        def email = "a@a"

        when:
        Email.of(email)

        then:
        thrown(IllegalValue)
    }

    def "should throw exception when email is too long"() {
        given:
        def email = "a" * 255

        when:
        Email.of(email)

        then:
        thrown(IllegalValue)
    }

    def "should throw exception when email does not match regex"() {
        when:
        Email.of("invalid-email@")

        then:
        thrown(IllegalValue)
    }

    def "should create a valid email"() {
        expect:
        Email.of("test.email-1_2@example-domain.com")
    }

    def "should trim whitespaces"() {
        expect:
        new Email("test@example.com  ").value() == "test@example.com"
    }
}
