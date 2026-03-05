package com.pickdate.poll.domain;

import com.pickdate.bootstrap.domain.Identifier;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;

import static com.pickdate.bootstrap.domain.Value.valueOrNull;
import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;


@Getter
@Entity
@Table(name = "votes")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Vote {

    @EmbeddedId
    private VoteId id;

    @Column(name = "poll_id")
    private Identifier pollId;

    @Enumerated(STRING)
    private Availability availability;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    public Vote with(VoteId id) {
        this.id = id;
        return this;
    }

    public Vote with(Identifier pollId) {
        this.pollId = pollId;
        return this;
    }

    public Vote with(Availability availability) {
        this.availability = availability;
        return this;
    }

    @Data
    @NoArgsConstructor(access = PROTECTED)
    @AllArgsConstructor
    public static class VoteId implements Serializable {

        @Column(name = "participant_id")
        private Identifier participantId;

        @Column(name = "option_id")
        private Identifier optionId;
    }

    public VoteData toData() {
        return new VoteData(
                valueOrNull(pollId),
                valueOrNull(id.participantId),
                valueOrNull(id.optionId),
                availability.name()
        );
    }
}
