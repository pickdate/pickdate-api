package com.pickdate.poll.domain;

import com.pickdate.shared.model.Value;
import com.pickdate.shared.validation.Assert;
import jakarta.annotation.Nonnull;


public record Title(String value) implements Value<String> {

    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 255;

    public Title(String value) {
        this.value = value == null ? null : value.strip();
        validate(this.value);
    }

    public static Title of(String value) {
        return new Title(value);
    }

    public static void validate(String value) {
        Assert.that("title", value)
                .isNotBlank("title must not be blank")
                .hasMinLength(MIN_LENGTH, "title must be at least " + MIN_LENGTH + " characters long")
                .hasMaxLength(MAX_LENGTH, "title must be at most " + MAX_LENGTH + " characters long");
    }

    @Override
    public @Nonnull String toString() {
        return value == null ? "null" : value;
    }
}
