package com.pickdate.poll.domain;

import com.pickdate.shared.model.Identifier;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;


@Entity
@Table(name = "geo_locations")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GeoLocation {

    @EmbeddedId
    private final Identifier id = Identifier.generate();

    @Embedded
    private LocationData locationData;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    private GeoLocation(LocationData locationData) {
        this.locationData = locationData;
    }

    public static GeoLocation of(LocationData locationData) {
        return new GeoLocation(locationData);
    }

    public LocationData locationData() {
        return locationData;
    }
}
