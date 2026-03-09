package com.pickdate.poll.infrastructure;

import com.pickdate.bootstrap.domain.DisplayName;
import com.pickdate.bootstrap.domain.Email;
import com.pickdate.poll.domain.Participant;
import com.pickdate.poll.domain.Phone;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
class RegisterParticipantRequest {

    @NotNull
    private String displayName;

    @Nullable
    private String email;

    @Nullable
    private String phone;

    DisplayName toDisplayName() {
        return DisplayName.of(displayName);
    }

    Email toEmail() {
        return Email.ofNullable(email);
    }

    Phone toPhone() {
        return Phone.ofNullable(phone);
    }

    public Participant toParticipant() {
        return new Participant(toDisplayName())
                .withEmail(toEmail())
                .withPhone(toPhone());
    }
}
