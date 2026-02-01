package com.pickdate.shared.model;

import java.io.Serializable;


public interface Value<T> extends Serializable {

    static <T> T valueOf(Value<T> object) {
        return object == null ? null : object.value();
    }

    T value();
}
