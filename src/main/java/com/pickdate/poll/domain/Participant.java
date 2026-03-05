package com.pickdate.poll.domain;

import com.pickdate.bootstrap.domain.DisplayName;
import com.pickdate.bootstrap.domain.Email;
import com.pickdate.bootstrap.domain.Identifier;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

import static com.pickdate.bootstrap.domain.Value.valueOrNull;


@With
@Getter
@Entity
@Table(name = "participants")
@NoArgsConstructor
@AllArgsConstructor
public class Participant {

    @With
    @EmbeddedId
    private Identifier id = Identifier.generate();

    @Column(name = "displayName")
    private DisplayName displayName;

    @Column(name = "email")
    private Email email;

    @Column(name = "phone")
    private Phone phone;

    public Participant(DisplayName displayName) {
        this.id = Identifier.generate();
        this.displayName = displayName;
    }

    public Participant(DisplayName displayName, Email email, Phone phone) {
        this.displayName = displayName;
        this.email = email;
        this.phone = phone;
    }

    public ParticipantData toData() {
        return new ParticipantData(
                valueOrNull(displayName),
                valueOrNull(email),
                valueOrNull(phone)
        );
    }
}
