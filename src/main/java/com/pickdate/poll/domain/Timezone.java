package com.pickdate.poll.domain;

import com.pickdate.shared.model.Value;
import com.pickdate.shared.validation.Assert;
import jakarta.annotation.Nonnull;

import java.time.ZoneId;
import java.util.Set;


public record Timezone(
        String value
) implements Value<String> {

    private static final Set<String> TIMEZONES = ZoneId.getAvailableZoneIds();

    public Timezone(String value) {
        this.value = value == null ? null : value.strip();
        validate(this.value);
    }

    public static Timezone of(String value) {
        return new Timezone(value);
    }

    private void validate(String value) {
        Assert.that("timezone", value)
                .isNotBlank("timezone must not be blank or null")
                .matches(TIMEZONES::contains, "timezone must be one of: " + String.join(", ", TIMEZONES) + "\n");
    }

    @Override
    public @Nonnull String toString() {
        return value == null ? "null" : value;
    }
}
