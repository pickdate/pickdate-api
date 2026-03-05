package com.pickdate.poll.application;

import com.pickdate.bootstrap.domain.Identifier;
import com.pickdate.poll.domain.Availability;


public record CastVoteCommand(
        Identifier pollId,
        Identifier participantId,
        Identifier OptionId,
        Availability vote
) {
}
