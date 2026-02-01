package com.pickdate.shared.model;

import com.pickdate.shared.validation.Assert;
import jakarta.annotation.Nonnull;

import static java.util.UUID.randomUUID;


public record Identifier(String id) implements Value<String> {

    public Identifier(String id) {
        this.id = id == null ? null : id.strip();
        validate(id);
    }

    public static Identifier of(String value) {
        return new Identifier(value);
    }

    public static Identifier generate() {
        return new Identifier(randomUUID().toString());
    }

    private static void validate(String value) {
        Assert.that("id", value)
                .isNotBlank("id cannot be blank or null");
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
