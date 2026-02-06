package com.pickdate.poll.domain

import com.pickdate.shared.exception.IllegalValue
import spock.lang.Specification

class TimezoneSpec extends Specification {

    def "should throw exception when value is null or blank"() {
        when:
        new Timezone("")

        then:
        thrown(IllegalValue)
    }

    def "should throw exception when value is illegal"() {
        when:
        new Timezone("abc")

        then:
        thrown(IllegalValue)
    }

    def "should create timezone when value is correct"() {
        when:
        new Timezone(value)

        then:
        notThrown(IllegalValue)

        where:
        value            | _
        "UTC"            | _
        "Europe/Warsaw"  | _
        "America/Denver" | _
        "Etc/GMT+1"      | _
        "Etc/GMT-8"      | _
    }
}
