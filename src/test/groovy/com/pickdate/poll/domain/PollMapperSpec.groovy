package com.pickdate.poll.domain

import com.pickdate.test.extension.JsonMapper
import spock.lang.Specification
import spock.util.mop.Use

import static com.pickdate.poll.application.PollMapper.toPollData
import static com.pickdate.test.fixture.PollFixture.*

@Use(JsonMapper)
class PollMapperSpec extends Specification {

    def "should map to poll data"() {
        given:
        def poll = new Poll()
        poll.addOption(RANGE_08_01, WHOLE_DAY)
        poll.addOption(RANGE_08_02, WHOLE_DAY)
        poll.addOption(RANGE_08_03, WHOLE_DAY)
        poll.addOption(RANGE_08_04_14_15, NOT_WHOLE_DAY)
        poll.addOption(RANGE_08_04_15_16, NOT_WHOLE_DAY)
        poll.addOption(RANGE_08_04_16_17, NOT_WHOLE_DAY)
        poll.addParticipant(new Participant(DISPLAY_NAME))

        when:
        def data = toPollData(poll)

        then:
        data.options().size() == 6
        println(data.toJson())
    }
}
