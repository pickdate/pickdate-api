package com.pickdate.poll.domain;

import com.pickdate.shared.validation.Assert;

public record LocationData(
        double latitude,
        double longitude,
        String placeId,
        String address
) {

    public LocationData {
        placeId = normalize(placeId);
        address = normalize(address);
        validate(latitude, longitude);
    }

    private static void validate(double latitude, double longitude) {
        Assert.that("latitude", latitude)
                .isFinite("latitude must be a finite number")
                .isBetweenInclusive(-90.0, 90.0, "latitude must be in range [-90, 90]");
        Assert.that("longitude", longitude)
                .isFinite("longitude must be a finite number")
                .isBetweenInclusive(-180.0, 180.0, "longitude must be in range [-180, 180]");
    }

    private String normalize(String value) {
        return value == null ? null : value.strip();
    }
}
