package com.pickdate.poll.infrastructure;

import com.pickdate.bootstrap.domain.Identifier;
import com.pickdate.poll.application.CastVoteCommand;
import com.pickdate.poll.domain.Availability;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
class CastVoteRequest {

    private String participantId;
    private String optionId;
    private String availability;

    CastVoteCommand toCommand(String pollId) {
        return new CastVoteCommand(
                Identifier.of(pollId),
                Identifier.of(participantId),
                Identifier.of(optionId),
                Availability.from(availability)
        );
    }
}
