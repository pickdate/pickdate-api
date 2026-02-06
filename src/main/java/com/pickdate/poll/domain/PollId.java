package com.pickdate.poll.domain;

import com.pickdate.shared.model.Value;
import com.pickdate.shared.validation.Assert;
import jakarta.annotation.Nonnull;


public record PollId(String id) implements Value<String> {

    public PollId {
        validate(id);
    }

    public static PollId of(String value) {
        return new PollId(value);
    }

    private static void validate(String value) {
        Assert.that("id", value)
                .isNotBlank("Id cannot be blank or null");
    }

    @Override
    public String value() {
        return id;
    }

    @Override
    public @Nonnull String toString() {
        return id == null ? "null" : id;
    }
}
