package com.pickdate.poll.domain;

import com.pickdate.bootstrap.domain.Identifier;

import java.io.Serializable;
import java.util.Optional;


public interface PollRepository extends Serializable {

    Poll save(Poll poll);

    Optional<Poll> findById(Identifier id);

    void deleteById(Identifier pollId);

    void deleteAll();
}
