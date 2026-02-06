package com.pickdate.poll.domain;


record GeoLocationData(
        double latitude,
        double longitude,
        String placeId,
        String address
) {
}
