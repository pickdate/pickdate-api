package com.pickdate.shared.validation;


public interface Matcher<T> {

    boolean matches(T value);
}

