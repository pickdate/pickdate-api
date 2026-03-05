package com.pickdate.poll.infrastructure

import java.time.OffsetDateTime


trait PollApiTrait {

    private static String NEW_TITLE = "new title"
    private static String NEW_DESCRIPTION = "new description"

    String title() {
        NEW_TITLE
    }

    String description() {
        NEW_DESCRIPTION
    }

    CreateOptionRequest createOptionRequest() {
        new CreateOptionRequest(
                startAt: OffsetDateTime.parse("2995-08-01T10:00:00Z"),
                endAt: OffsetDateTime.parse("2995-08-01T11:00:00Z"),
                wholeDay: true
        )
    }

    RegisterParticipantRequest registerParticipantRequest() {
        new RegisterParticipantRequest(
                displayName: "john",
                email: "john@wick.com"
        )
    }

    CreatePollRequest createPollRequest() {
        new CreatePollRequest(
                title: NEW_TITLE,
                description: NEW_DESCRIPTION
        )
    }
}
