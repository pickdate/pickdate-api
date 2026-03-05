package com.pickdate.poll.infrastructure;

import com.pickdate.bootstrap.domain.Identifier;
import com.pickdate.poll.domain.Poll;
import com.pickdate.poll.domain.PollRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
interface PollRepositoryJpa extends PollRepository, JpaRepository<Poll, Identifier> {
}
