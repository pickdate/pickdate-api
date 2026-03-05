package com.pickdate.poll.domain;

import java.util.List;


public record PollData(
        String id,
        String title,
        String description,
        List<ParticipantData> participants,
        List<OptionData> options
) {
}
