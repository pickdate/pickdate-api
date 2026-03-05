package com.pickdate.iam.infrastructure

import com.pickdate.iam.application.UserUseCaseTestConfig
import org.springframework.data.domain.Pageable
import spock.lang.Specification

import static com.pickdate.test.fixture.UserFixture.SOME_ADMIN


class UserApiIntegrationSpec extends Specification {

    def userUseCase = UserUseCaseTestConfig.userUseCase()
    def userApi = new UserApi(userUseCase)

    def setupSpec() {
        UserUseCaseTestConfig.setupTestData()
    }

    def "should list all users"() {
        given:
        def pageable = Pageable.ofSize(2)

        when:
        def responseEntity = userApi.getAllUsers(pageable)

        then:
        responseEntity.body.content.find { it.email() == "admin@email.com" }
        responseEntity.body.content.find { it.email() == "user@email.com" }
    }

    def "should get user by id"() {
        given:
        def id = SOME_ADMIN.id.value

        when:
        def responseEntity = userApi.getById(id)

        then:
        responseEntity.body.email() == "admin@email.com"
        responseEntity.body.authorities() == ["ADMIN"]
    }

    def "should create a new user account"() {
        given:
        def email = "newemail@email.com"
        def pass = "superSecretPassword!"
        def request = new CreateUserRequest(email, pass)

        when:
        def responseEntity = userApi.create(request)

        then:
        responseEntity.body.email() == email
        responseEntity.body.authorities() == ["USER"]
        // id is not null
        responseEntity.body.id()
    }
}
