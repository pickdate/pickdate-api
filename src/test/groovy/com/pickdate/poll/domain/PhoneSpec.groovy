package com.pickdate.poll.domain

import com.pickdate.shared.exception.IllegalValue
import spock.lang.Specification

class PhoneSpec extends Specification {

    def "should throw exception when phone is null"() {
        when:
        Phone.of(null)

        then:
        thrown(IllegalValue)
    }

    def "should throw exception when phone is blank"() {
        when:
        Phone.of("")

        then:
        thrown(IllegalValue)
    }

    def "should throw exception when phone is too short"() {
        given:
        def phone = "123456"

        when:
        Phone.of(phone)

        then:
        thrown(IllegalValue)
    }

    def "should throw exception when phone is too long"() {
        given:
        def phone = ("1" * 33)

        when:
        Phone.of(phone)

        then:
        thrown(IllegalValue)
    }

    def "should throw exception when phone does not match regex"() {
        when:
        Phone.of("12A3456")

        then:
        thrown(IllegalValue)
    }

    def "should create a valid phone"() {
        expect:
        Phone.of("+48 123-456-789")
    }

    def "should trim whitespaces"() {
        expect:
        new Phone("  1234567  ").value() == "1234567"
    }
}
