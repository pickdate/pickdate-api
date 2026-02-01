package com.pickdate.shared.exception;

import com.pickdate.shared.model.Property;
import lombok.Getter;

import java.util.List;


@Getter
public class IllegalValue extends RuntimeException {

    private final List<Property<?>> properties;
    private final String detail;

    public IllegalValue(List<Property<?>> properties, String detail) {
        detail = detail == null ? "" : detail;
        this.properties = properties;
        this.detail = detail;
    }

    public IllegalValue(Property<?> property, String detail) {
        this(List.of(property), detail);
    }
}
