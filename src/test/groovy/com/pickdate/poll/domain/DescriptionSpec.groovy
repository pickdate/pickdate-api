package com.pickdate.poll.domain

import com.pickdate.shared.exception.IllegalValue
import spock.lang.Specification

class DescriptionSpec extends Specification {

    def "should throw exception when description is null or blank"() {
        when:
        Description.of("")

        then:
        thrown(IllegalValue)
    }

    def "should create value"() {
        given:
        def someDescription = "some description"

        when:
        def desc = Description.of(someDescription)

        then:
        desc.value() == someDescription
    }
}
