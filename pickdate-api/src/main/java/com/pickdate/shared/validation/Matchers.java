package com.pickdate.shared.validation;

import java.util.Arrays;


public final class Matchers {

    private Matchers() {
    }

    public static Matcher<String> oneOf(Enum<?>... values) {
        String[] array = Arrays.stream(values).map(Enum::name).toArray(String[]::new);
        return new OneOfMatcher(array);
    }

    public static Matcher<String> oneOf(String... values) {
        return new OneOfMatcher(values);
    }
}

final class OneOfMatcher implements Matcher<String> {

    private final String[] values;

    public OneOfMatcher(String[] values) {
        this.values = values;
    }

    @Override
    public boolean matches(String value) {
        return Arrays.asList(values).contains(value);
    }
}
