package com.pickdate.poll.application;

import com.pickdate.bootstrap.domain.Identifier;
import com.pickdate.poll.domain.*;


public interface PollUseCase {

    PollData createPoll(Title title, Description description);

    PollData addOption(Identifier pollId, TimeRange timeRange, boolean wholeDay);

    PollData getPoll(Identifier id);

    void deletePoll(Identifier pollId);

    PollData registerParticipant(Identifier pollId, Participant participant);

    PollData addLocation(Identifier pollId, LocationData location);
}
