package com.pickdate.poll.infrastructure;

import com.pickdate.poll.domain.LocationData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
class CreateLocationRequest {

    private double latitude;
    private double longitude;
    private String placeId;
    private String address;

    LocationData toLocationData() {
        return new LocationData(latitude, longitude, placeId, address);
    }
}
