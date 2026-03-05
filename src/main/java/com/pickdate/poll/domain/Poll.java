package com.pickdate.poll.domain;

import com.pickdate.bootstrap.domain.Identifier;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;
import static lombok.AccessLevel.PROTECTED;


@Getter
@Entity
@Table(name = "polls")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Poll {

    @With
    @EmbeddedId
    private Identifier id = Identifier.generate();

    private Title title;

    private Description description;

    @OneToOne(cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private GeoLocation location;

    @OneToMany(cascade = ALL, orphanRemoval = true)
    @JoinColumn(
            name = "poll_id",
            referencedColumnName = "id",
            nullable = false
    )
    private Set<Option> options = new HashSet<>();

    @OneToMany(cascade = ALL, orphanRemoval = true)
    @JoinColumn(
            name = "poll_id",
            referencedColumnName = "id",
            nullable = false
    )
    private Set<Participant> participants = new HashSet<>();

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    public static Poll from(Title title, Description description) {
        var poll = new Poll();
        poll.title = title;
        poll.description = description;
        return poll;
    }

    public void addOption(TimeRange timeRange, boolean wholeDay) {
        var option = Option.from(timeRange, wholeDay);
        options.add(option);
    }

    public void addOption(TimeRange timeRange) {
        var option = Option.from(timeRange);
        options.add(option);
    }

    public void addOption(Option option) {
        options.add(option);
    }

    public void addParticipant(Participant participant) {
        participants.add(participant);
    }

    public void addLocation(GeoLocation geoLocation) {
        this.location = geoLocation;
    }
}
