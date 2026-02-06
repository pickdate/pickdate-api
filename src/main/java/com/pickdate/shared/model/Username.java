package com.pickdate.shared.model;

import com.pickdate.shared.validation.Assert;
import jakarta.annotation.Nonnull;

import java.util.regex.Pattern;


public record Username(String value) implements Value<String> {

    private static final Pattern pattern = Pattern.compile("^[0-9A-Za-z_-]+$");

    public Username(String value) {
        this.value = value == null ? null : value.strip();
        validate(this.value);
    }

    public static Username of(String value) {
        return new Username(value);
    }

    private static void validate(String value) {
        Assert.that("username", value)
                .isNotBlank("username must not be blank")
                .hasMinLength(3, "username must be at least 3 characters long")
                .hasMaxLength(32, "username must be at most 32 characters long")
                .matches(pattern, "username must contain only alphanumeric characters, '-', '_'");
    }

    @Override
    public @Nonnull String toString() {
        return value == null ? "null" : value;
    }
}
