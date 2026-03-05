package com.pickdate.test.stub

import com.pickdate.bootstrap.domain.Identifier
import com.pickdate.poll.domain.Vote
import com.pickdate.poll.domain.VoteRepository

import java.util.concurrent.ConcurrentHashMap

class VoteRepositoryFake implements VoteRepository {

    Map<Vote.VoteId, Vote> map = new ConcurrentHashMap()

    @Override
    List<Vote> findByPollId(Identifier pollId) {
        map.values().findAll { it.pollId == pollId }
    }

    @Override
    Vote save(Vote vote) {
        map.put(vote.id, vote)
        return vote
    }

    @Override
    Optional<Vote> findById(Vote.VoteId voteId) {
        Optional.ofNullable(map.get(voteId))
    }
}
