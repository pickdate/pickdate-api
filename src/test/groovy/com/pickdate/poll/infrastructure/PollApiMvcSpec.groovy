package com.pickdate.poll.infrastructure

import com.pickdate.bootstrap.domain.Property
import com.pickdate.bootstrap.exception.IllegalValueException
import com.pickdate.bootstrap.exception.InternalServerError
import com.pickdate.bootstrap.exception.NotFoundException
import com.pickdate.poll.application.PollUseCase
import com.pickdate.test.extension.JsonMapper
import com.pickdate.test.type.MvcSpec
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Execution
import spock.lang.Requires
import spock.util.mop.Use

import static com.pickdate.test.fixture.PollFixture.*
import static org.spockframework.runtime.model.parallel.ExecutionMode.SAME_THREAD
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print


@Use(JsonMapper)
@Execution(SAME_THREAD)
@Requires({ it.env['INCLUDE_SLOW_TESTS'] == 'true' })
class PollApiMvcSpec extends MvcSpec {

    @Autowired
    MockMvc mvc

    @SpringBean
    PollUseCase pollUseCase = Mock()

    @WithMockUser(authorities = "ROLE_USER")
    def "should be able to get poll"() {
        given:
        def pollId = ID
        def pollData = somePollData()

        when:
        def response = mvc.perform(get("/api/v1/polls/${pollId}"))
                .andDo(print())
                .andReturn()
                .getResponse()

        then:
        1 * pollUseCase.getPoll(_) >> pollData

        and:
        response.status == 200
    }

    @WithMockUser(authorities = "ROLE_USER")
    def "should be able to create poll"() {
        given:
        def req = createPollRequestJson()
        def pollData = somePollData()

        when:
        def response = mvc.perform(
                post("/api/v1/polls")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(req))
                .andDo(print())
                .andReturn()
                .getResponse()

        then:
        1 * pollUseCase.createPoll(_, _) >> pollData

        and:
        response.status == 201
    }

    @WithMockUser(authorities = "ROLE_USER")
    def "should return 404, when not found"() {
        given:
        def pollId = ID

        when:
        def response = mvc.perform(get("/api/v1/polls/${pollId}"))
                .andDo(print())
                .andReturn()
                .getResponse()

        then:
        1 * pollUseCase.getPoll(_) >> { throw new NotFoundException(new Property<>("id", pollId), "Poll not found") }

        and:
        response.status == 404

        and:
        def body = response.toMap()
        body.title == "Resource Not Found"
        body.status == 404
        body.detail == "Poll not found"
        body.instance.contains("/api/v1/polls")
        !body.traceId.isBlank()
    }

    @WithMockUser(authorities = "ROLE_USER")
    def "should return 500, when there was internal serwer error"() {
        given:
        def pollId = ID

        when:
        def response = mvc.perform(get("/api/v1/polls/${pollId}"))
                .andDo(print())
                .andReturn()
                .getResponse()

        then:
        1 * pollUseCase.getPoll(_) >> { throw new InternalServerError("ups... app stopped working") }

        and:
        response.status == 500

        and:
        def body = response.toMap()
        body.title == "Internal Server Error"
        body.status == 500
        body.detail == "ups... app stopped working"
        body.instance.contains("/api/v1/polls")
        !body.traceId.isBlank()
    }

    @WithMockUser(authorities = "ROLE_USER")
    def "should return 400, when value is invalid"() {
        given:
        def pollId = ID

        when:
        def response = mvc.perform(get("/api/v1/polls/${pollId}"))
                .andDo(print())
                .andReturn()
                .getResponse()

        then:
        1 * pollUseCase.getPoll(_) >> { throw new IllegalValueException(new Property<Object>("id", pollId), "Poll is invalid") }

        and:
        response.status == 400

        and:
        def body = response.toMap()
        body.title == "Validation Error"
        body.status == 400
        body.detail == "Poll is invalid"
        body.instance.contains("/api/v1/polls")
        !body.traceId.isBlank()

        and:
        with(body.invalidParams[0] as Map<String, String>) {
            name == "id"
            value == pollId
            reason == "Poll is invalid"
        }
    }
}
