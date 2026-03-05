package com.pickdate.test.fixture

import com.pickdate.bootstrap.domain.DisplayName
import com.pickdate.poll.domain.OptionData
import com.pickdate.poll.domain.ParticipantData
import com.pickdate.poll.domain.PollData
import com.pickdate.poll.domain.TimeRange

import java.time.Instant


class PollFixture {

    public static String ID = "f4569d37-2a79-4f8e-955a-7bb69fa58451"
    public static String TITLE = "some title"
    public static String DESCRIPTION = "some description"
    public static boolean WHOLE_DAY = true
    public static boolean NOT_WHOLE_DAY = false
    public static final DisplayName DISPLAY_NAME = DisplayName.of("John")

    public static List<ParticipantData> PARTICIPANTS = []

    public static TimeRange RANGE_08_01 = new TimeRange(
            Instant.parse("2025-08-01T00:00:00Z"),
            Instant.parse("2025-08-01T23:59:59Z")
    )
    public static TimeRange RANGE_08_02 = new TimeRange(
            Instant.parse("2025-08-02T00:00:00Z"),
            Instant.parse("2025-08-02T23:59:59Z")
    )
    public static TimeRange RANGE_08_03 = new TimeRange(
            Instant.parse("2025-08-03T00:00:00Z"),
            Instant.parse("2025-08-03T23:59:59Z")
    )
    public static TimeRange RANGE_08_04_14_15 = new TimeRange(
            Instant.parse("2025-08-04T14:00:00Z"),
            Instant.parse("2025-08-04T15:00:00Z")
    )
    public static TimeRange RANGE_08_04_15_16 = new TimeRange(
            Instant.parse("2025-08-04T15:00:00Z"),
            Instant.parse("2025-08-04T16:00:00Z")
    )
    public static TimeRange RANGE_08_04_16_17 = new TimeRange(
            Instant.parse("2025-08-04T16:00:00Z"),
            Instant.parse("2025-08-04T17:00:00Z")
    )

    public static List<OptionData> OPTIONS = [
            new OptionData(
                    ID,
                    Instant.parse("2025-08-01T00:00:00Z"),
                    Instant.parse("2025-08-01T23:59:59Z"),
                    WHOLE_DAY
            )
    ]

    static PollData somePollData() {
        new PollData(
                ID,
                TITLE,
                DESCRIPTION,
                PARTICIPANTS,
                OPTIONS
        )
    }

    static String createPollRequestJson() {
        """
            {
                "title": "$TITLE",
                "description": "$DESCRIPTION"
            }
        """
    }
}
