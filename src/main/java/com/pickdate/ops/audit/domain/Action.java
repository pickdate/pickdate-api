package com.pickdate.ops.audit.domain;

import com.pickdate.bootstrap.domain.Value;
import com.pickdate.bootstrap.validation.Assert;
import jakarta.annotation.Nonnull;
import lombok.Getter;

import java.util.Objects;
import java.util.regex.Pattern;


public class Action implements Value<String> {

    private static final Pattern PATTERN = Pattern.compile("^[a-z]+(?:_[a-z]+)*$");

    @Getter
    private String value;

    Action() {
    }

    public Action(String value) {
        this.value = value;
        validate(this.value);
    }

    public static Action of(String value) {
        return new Action(value);
    }

    private static void validate(String value) {
        Assert.that("activity", value)
                .isNotBlank("activity must not be null or blank")
                .matches(PATTERN, "activity must contain only lowercase letters");
    }

    @Override
    public @Nonnull String toString() {
        return value == null ? "null" : value;
    }

    @Override
    public final boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Action action)) return false;
        return Objects.equals(value, action.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
