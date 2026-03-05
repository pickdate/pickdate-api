package com.pickdate.poll.domain

import com.pickdate.bootstrap.exception.IllegalValueException
import spock.lang.Specification

class DescriptionSpec extends Specification {

    def "should throw exception when description is null or blank"() {
        when:
        Description.of("")

        then:
        thrown(IllegalValueException)
    }

    def "should create value"() {
        given:
        def someDescription = "some description"

        when:
        def desc = Description.of(someDescription)

        then:
        desc.value == someDescription
    }
}
