package com.pickdate.poll.infrastructure;

import com.pickdate.bootstrap.domain.Identifier;
import com.pickdate.poll.domain.Vote;
import com.pickdate.poll.domain.Vote.VoteId;
import com.pickdate.poll.domain.VoteRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
interface VoteRepositoryJpa extends VoteRepository, JpaRepository<Vote, VoteId> {

    @NotNull
    @Override
    Optional<Vote> findById(@NotNull VoteId voteId);

    @Override
    List<Vote> findByPollId(Identifier pollId);

    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    Vote save(@NotNull Vote vote);
}
