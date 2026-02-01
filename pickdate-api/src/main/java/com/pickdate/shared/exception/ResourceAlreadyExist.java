package com.pickdate.shared.exception;

import com.pickdate.shared.model.Property;
import lombok.Getter;


@Getter
public class ResourceAlreadyExist extends RuntimeException {

    private final Property<Object> property;
    private final String detail;

    public ResourceAlreadyExist(Property<Object> property, String detail) {
        detail = detail == null ? "" : detail;
        this.property = property;
        this.detail = detail;
    }
}
