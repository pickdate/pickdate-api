package com.pickdate.poll.domain;


public record VoteData(
        String pollId,
        String participantId,
        String optionId,
        String availability
) {
}
