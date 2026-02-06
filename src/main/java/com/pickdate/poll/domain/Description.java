package com.pickdate.poll.domain;

import com.pickdate.shared.model.Value;
import com.pickdate.shared.validation.Assert;
import jakarta.annotation.Nonnull;


public record Description(String value) implements Value<String> {

    public Description(String value) {
        this.value = value == null ? null : value.strip();
        validate(this.value);
    }

    public static Description of(String value) {
        return new Description(value);
    }

    private static void validate(String value) {
        Assert.that("description", value)
                .isNotBlank("description must not be null or blank");
    }

    @Override
    public @Nonnull String toString() {
        return value == null ? "null" : value;
    }
}
