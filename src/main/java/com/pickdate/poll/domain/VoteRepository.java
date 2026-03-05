package com.pickdate.poll.domain;

import com.pickdate.bootstrap.domain.Identifier;

import java.util.List;
import java.util.Optional;


public interface VoteRepository {

    List<Vote> findByPollId(Identifier pollId);

    Vote save(Vote vote);

    Optional<Vote> findById(Vote.VoteId voteId);
}
