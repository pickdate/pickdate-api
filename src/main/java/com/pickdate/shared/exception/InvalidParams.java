package com.pickdate.shared.exception;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.ArrayList;
import java.util.List;


@With
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvalidParams {

    private List<InvalidParam> items = new ArrayList<>();

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static InvalidParams fromJson(List<InvalidParam> items) {
        return new InvalidParams(items);
    }

    @JsonValue
    public List<InvalidParam> getItems() {
        return items;
    }
}
