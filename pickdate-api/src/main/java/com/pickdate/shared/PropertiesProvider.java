package com.pickdate.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
public class PropertiesProvider<T> {

    @Getter
    private T configuration;

    public void override(T configuration) {
        this.configuration = configuration;
    }
}
