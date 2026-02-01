package com.pickdate.shared.model;


public record Property<T>(String name, T value) {

    public static <T> Property<T> of(String name, T value) {
        return new Property<>(name, value);
    }
}
