package com.pickdate.test.type

import com.pickdate.test.config.TestConfig
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import spock.lang.Specification

@Import(TestConfig)
@SpringBootTest
class SpringBootSpec extends Specification {
}
