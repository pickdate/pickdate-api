package com.pickdate.poll.domain;

import com.pickdate.shared.validation.Assert;

import java.time.Instant;


public record TimeRange(Instant startAt, Instant endAt) {

    public TimeRange {
        validate(startAt, endAt);
    }

    private static void validate(Instant startAt, Instant endAt) {
        Assert.that("startAt", startAt).isNotNull("Start date cannot be null");
        Assert.that("endAt", endAt).isNotNull("End date cannot be null");
        Assert.that("startAt", startAt)
                .isBefore(endAt, "Start date cannot be after end date");
    }

    public boolean isInThePast() {
        return endAt.isBefore(Instant.now());
    }
}
