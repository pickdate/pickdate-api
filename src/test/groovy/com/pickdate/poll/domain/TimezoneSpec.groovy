package com.pickdate.poll.domain

import com.pickdate.bootstrap.exception.IllegalValueException
import spock.lang.Specification

class TimezoneSpec extends Specification {

    def "should throw exception when value is null or blank"() {
        when:
        Timezone.of("")

        then:
        thrown(IllegalValueException)
    }

    def "should throw exception when value is illegal"() {
        when:
        Timezone.of("abc")

        then:
        thrown(IllegalValueException)
    }

    def "should create timezone when value is correct"() {
        when:
        Timezone.of(value)

        then:
        notThrown(IllegalValueException)

        where:
        value            | _
        "UTC"            | _
        "Europe/Warsaw"  | _
        "America/Denver" | _
        "Etc/GMT+1"      | _
        "Etc/GMT-8"      | _
    }
}
