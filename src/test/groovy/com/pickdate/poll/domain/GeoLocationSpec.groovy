package com.pickdate.poll.domain

import com.pickdate.shared.exception.IllegalValue
import spock.lang.Specification

class GeoLocationSpec extends Specification {

    def "should create location with lat/lng only"() {
        expect:
        GeoLocation.of(52.2297d, 21.0122d)
    }

    def "should throw when latitude out of range"() {
        when:
        GeoLocation.of(-91d, 0d)

        then:
        thrown(IllegalValue)

        when:
        GeoLocation.of(91d, 0d)

        then:
        thrown(IllegalValue)
    }

    def "should throw when longitude out of range"() {
        when:
        GeoLocation.of(0d, -181d)

        then:
        thrown(IllegalValue)

        when:
        GeoLocation.of(0d, 181d)

        then:
        thrown(IllegalValue)
    }

    def "should trim and validate placeId and address"() {
        when:
        def loc = GeoLocation.of(52.0d, 21.0d, "  ChIJN1t_tDeuEmsRUsoyG83frY4  ", "  Aleje Jerozolimskie  ")

        then:
        loc.placeId == "ChIJN1t_tDeuEmsRUsoyG83frY4"
        loc.address == "Aleje Jerozolimskie"

        when:
        GeoLocation.of(52.0d, 21.0d, "   ", null)

        then:
        thrown(IllegalValue)

        when:
        GeoLocation.of(52.0d, 21.0d, null, "  ")

        then:
        thrown(IllegalValue)
    }
}

