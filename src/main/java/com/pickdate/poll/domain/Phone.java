package com.pickdate.poll.domain;

import com.pickdate.shared.model.Value;
import com.pickdate.shared.validation.Assert;
import jakarta.annotation.Nonnull;

import java.util.regex.Pattern;


public record Phone(String value) implements Value<String> {

    private static final Pattern PHONE_REGEX =
            Pattern.compile("^[0-9 +\\-()]{7,50}$");

    public Phone(String value) {
        this.value = value == null ? null : value.strip();
        validate(this.value);
    }

    public static Phone of(String value) {
        return new Phone(value);
    }

    private static void validate(String value) {
        Assert.that("phone", value)
                .isNotBlank("phone must not be null or blank")
                .hasMinLength(7, "phone must be at least 7 characters long")
                .hasMaxLength(32, "phone must be at most 32 characters long")
                .matches(PHONE_REGEX, "phone may contain only digits, spaces, '+', '-', '(' or ')'");
    }

    @Override
    public @Nonnull String toString() {
        return value == null ? "null" : value;
    }
}
