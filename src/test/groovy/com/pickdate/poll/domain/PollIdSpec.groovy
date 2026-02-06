package com.pickdate.poll.domain

import com.pickdate.shared.exception.IllegalValue
import spock.lang.Specification


class PollIdSpec extends Specification {

    def "should create PollId from non-blank string"() {
        when:
        def id = PollId.of("poll-123")

        then:
        id.value() == "poll-123"
    }

    def "should not create PollId from blank string"() {
        when:
        PollId.of("")

        then:
        thrown(IllegalValue)
    }

    def "should not create PollId from whitespaces only"() {
        when:
        PollId.of("   ")

        then:
        thrown(IllegalValue)
    }

    def "should not create PollId from null"() {
        when:
        PollId.of(null)

        then:
        thrown(IllegalValue)
    }
}
