package com.pickdate.poll.application;

import com.pickdate.bootstrap.domain.Identifier;
import com.pickdate.poll.domain.VoteData;

import java.util.List;


public interface VoteUseCase {

    void castVote(CastVoteCommand castVoteCommand);

    List<VoteData> getVotesBy(Identifier pollId);
}
