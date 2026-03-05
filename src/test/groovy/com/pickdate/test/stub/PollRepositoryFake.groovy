package com.pickdate.test.stub

import com.pickdate.bootstrap.domain.Identifier
import com.pickdate.poll.domain.Poll
import com.pickdate.poll.domain.PollRepository

import java.util.concurrent.ConcurrentHashMap


class PollRepositoryFake implements PollRepository {

    Map<Identifier, Poll> map = new ConcurrentHashMap<>()

    @Override
    Poll save(Poll poll) {
        map.put(poll.id, poll)
        return poll
    }

    @Override
    Optional<Poll> findById(Identifier id) {
        Optional.ofNullable(map.get(id))
    }

    @Override
    void deleteById(Identifier pollId) {
        map.remove(pollId)
    }

    void deleteAll() {
        map.clear()
    }
}
