package com.pickdate.poll.domain;

import java.time.Instant;


public record OptionData(
        String optionId,
        Instant startAt,
        Instant endAt,
        boolean wholeDay
) {
}
