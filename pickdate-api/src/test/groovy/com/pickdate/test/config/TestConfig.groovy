package com.pickdate.test.config

import com.pickdate.shared.encryption.Encryptor
import groovy.transform.CompileStatic
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import spock.mock.DetachedMockFactory


@CompileStatic
@TestConfiguration(proxyBeanMethods = false)
class TestConfig {

    DetachedMockFactory factory = new DetachedMockFactory()

    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> postgresContainer() {
        return new PostgreSQLContainer<>(DockerImageName.parse("postgres:17.5-alpine"))
    }

    @Bean
    @Primary
    Encryptor encoder() {
        Encryptor.noop()
    }
}
