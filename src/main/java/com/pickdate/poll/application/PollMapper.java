package com.pickdate.poll.application;

import com.pickdate.poll.domain.*;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static com.pickdate.bootstrap.domain.Value.valueOrNull;
import static java.util.Comparator.comparing;


class PollMapper {

    static PollData toPollData(Poll poll) {

        return new PollData(
                valueOrNull(poll.getId()),
                valueOrNull(poll.getTitle()),
                valueOrNull(poll.getDescription()),
                toParticipants(poll.getParticipants()),
                toOptionData(poll.getOptions())
        );
    }

    static List<ParticipantData> toParticipants(Set<Participant> participants) {
        return participants.stream()
                .map(Participant::toData)
                .sorted(Comparator.comparing(ParticipantData::name))
                .toList();
    }

    static List<OptionData> toOptionData(Set<Option> options) {
        return options.stream()
                .map(Option::toData)
                .sorted(comparing(OptionData::startAt))
                .toList();
    }
}
