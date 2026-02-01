package com.pickdate

import com.pickdate.test.type.SpringBootSpec
import spock.lang.Requires

@Requires({ it.env['include-slow'] == 'true' })
class AppSpec extends SpringBootSpec {

    def contextLoads() {
        expect:
        appLoaded()
    }

    def appLoaded() {
        true
    }
}
