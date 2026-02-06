package com.pickdate.shared.model;

import com.pickdate.shared.validation.Assert;
import jakarta.annotation.Nonnull;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;


public record Email(
        String value
) implements Value<String> {
    private static final int MIN_LENGTH = 4;
    private static final int MAX_LENGTH = 254;
    // https://regex101.com/r/JWNwl0/3
    private static final Pattern PATTERN = compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,63}$");

    public Email(String value) {
        this.value = value == null ? null : value.strip();
        validate(this.value);
    }

    public static void validate(String value) {
        Assert.that("email", value)
                .isNotBlank("Email cannot be null or blank")
                .hasMinLength(MIN_LENGTH, "Email must be at least " + MIN_LENGTH + " characters long")
                .hasMaxLength(MAX_LENGTH, "Email must be at most " + MAX_LENGTH + " characters long")
                .matches(PATTERN, "Invalid email format");
    }

    public static Email of(String value) {
        return new Email(value);
    }

    @Override
    public @Nonnull String toString() {
        return value == null ? "null" : value;
    }
}
